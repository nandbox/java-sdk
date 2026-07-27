package com.nandbox.bots.api;

import static com.nandbox.bots.api.util.Utils.formatDate;
import static com.nandbox.bots.api.util.Utils.getUniqueId;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.nandbox.bots.api.data.*;
import com.nandbox.bots.api.inmessages.*;
import com.nandbox.bots.api.outmessages.*;
import net.minidev.json.JSONArray;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import com.nandbox.bots.api.outmessages.OutMessage.OutMessageMethod;
import com.nandbox.bots.api.outmessages.cell.PhotoCellOutMessage;
import com.nandbox.bots.api.outmessages.cell.TextCellOutMessage;
import com.nandbox.bots.api.outmessages.cell.VideoCellOutMessage;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

/**
 * NandboxClient Class
 *
 * @author Ahmed A. El-Malatawy
 *
 */
public class NandboxClient {
    public static enum Status {
        Processing,
        Success,
        Rejected
    }
    public static enum NotificationType{
        SMS,
        Email,
        Push,
    }
	private static final String CONFIG_FILE = "config.properties";
	// Declared before any static initialiser that logs, so that logging during
	// class initialisation (e.g. a missing config file) cannot hit a null logger.
	public static final Logger log = Logger.getLogger(NandboxClient.class);
	private static volatile String BOT_ID = null;
	private static NandboxClient nandboxClient;
	private volatile WebSocketClient webSocketClient;
	private static final Properties configs = getConfigs();
	private static final int CORE_POOL_SIZE = getIntConfig("corePoolSize", 10);
	private static final int MAX_POOL_SIZE = getIntConfig("maximumPoolSize", 10);
	private static final long KEEP_ALIVE_TIME = getIntConfig("keepAliveTime", 500);
	private static final ThreadPoolExecutor messageThreadPool =
			new ThreadPoolExecutor(
					CORE_POOL_SIZE, // corePoolSize
					MAX_POOL_SIZE, // maximumPoolSize
					KEEP_ALIVE_TIME, TimeUnit.SECONDS,
					new LinkedBlockingQueue<Runnable>(),
					new MessageThreadFactory()
			);
	volatile int closingCounter = 0;
	volatile int timeOutCounter = 0;
	volatile int connRefusedCounter = 0;
	private URI uri;
	static final String KEY_METHOD = "method";
	static final String KEY_ERROR = "error";
	Logger rootLogger = Logger.getRootLogger();

	/**
	 * Names the message-dispatch threads so they can be identified in a thread dump.
	 */
	private static final class MessageThreadFactory implements ThreadFactory {
		private final AtomicInteger counter = new AtomicInteger();

		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, "nandbox-message-" + counter.incrementAndGet());
		}
	}

	/**
	 * Reads a numeric property, falling back to {@code defaultValue} when the
	 * property is absent or not a valid number.
	 */
	private static int getIntConfig(String key, int defaultValue) {
		String value = configs.getProperty(key);
		if (value == null) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(value.trim());
		} catch (NumberFormatException e) {
			log.warn("Invalid value '" + value + "' for config '" + key + "', using " + defaultValue);
			return defaultValue;
		}
	}



	public static Properties getConfigs() {
		Properties configs = new Properties();
		try (InputStream configIs = new FileInputStream(CONFIG_FILE)) {
			configs.load(configIs);
		} catch (IOException e) {
			log.error("Unable to read " + CONFIG_FILE, e);
		}
		return configs;
	}




	@WebSocket(maxTextMessageSize = 100000)
	public class InternalWebSocket {
		private static final int NO_OF_RETRIES_IF_CONN_TO_SERVER_REFUSED = 9999999;
		private static final int NO_OF_RETRIES_IF_CONN_TIMEDOUT = 9999999;
		private static final int NO_OF_RETRIES_IF_CONN_CLOSED = 9999999;
		private static final String KEY_USER = "user";
		private static final String KEY_CHAT = "chat";
		private static final String KEY_NAME = "name";
		private static final String KEY_ID = "ID";
		private static final String KEY_REFERENCE = "reference";
		private static final String KEY_APP_ID = "app_id";


		Nandbox.Callback callback;
		volatile Session session;
		String token;
		volatile Nandbox.Api api;
		volatile boolean authenticated = false;
		boolean echo = false;
		// Written by the message-dispatch threads, read by the ping thread.
		volatile long lastMessage = 0;

		class PingThread extends Thread {
			volatile boolean interrupted = false;

			@Override
			public void interrupt() {
				interrupted = true;
				super.interrupt();
			}

			@Override
			public void run() {
				while (true) {
					try {
						if (System.currentTimeMillis() - lastMessage > 60000 && session != null && session.isOpen()) {
							JSONObject obj = new JSONObject();
							obj.put(KEY_METHOD, "PING");
							InternalWebSocket.this.send(obj.toJSONString());
						}
					} catch (Exception e) {
						//System.err.println(e);
						NandboxClient.log.error(e);
					}
					if (interrupted)
						return;
					try {
						Thread.sleep(30000);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						return;
					}
				}
			}
		}

		Thread pingThread = null;

		InternalWebSocket(String token, Nandbox.Callback callback) {
			this.token = token;
			this.callback = callback;
		}

		@OnWebSocketClose
		public void onClose(int statusCode, String reason) {
			NandboxClient.log.info("INTERNAL: ONCLOSE, statusCode = " + statusCode + ", reason : " + reason);

			authenticated = false;
			stopPingThread();

			callback.onClose();

			if ((statusCode == 1000 || statusCode == 1006 || statusCode == 1001 || statusCode == 1005)
					&& closingCounter < NO_OF_RETRIES_IF_CONN_CLOSED) {
				try {
					NandboxClient.log.info("Please wait 10 seconds for Reconnecting ");
					TimeUnit.SECONDS.sleep(10);
					closingCounter = closingCounter + 1;
					NandboxClient.log.info("Connection closing counter is  : " + closingCounter);
				} catch (InterruptedException e1) {
					// Restore the interrupt flag and abandon the reconnect attempt.
					Thread.currentThread().interrupt();
					NandboxClient.log.warn("Interrupted while waiting to reconnect", e1);
					return;
				}
				stopWebSocketClient();
				try {
					reconnectWebSocketClient();
				} catch (Exception e) {
					NandboxClient.log.error("Failed to reconnect the websocket client", e);
				}

			} else {
				// A library must not terminate the host JVM. Stop reconnecting and let
				// the application decide what to do via Callback.onClose().
				NandboxClient.log.warn("Not reconnecting after close with statusCode " + statusCode
						+ " (retries used: " + closingCounter + "). The nandbox client is now idle.");
				stopWebSocketClient();
			}
		}

		/**
		 * Interrupts the ping thread and waits briefly for it to finish. Never blocks
		 * the calling websocket thread indefinitely.
		 */
		private void stopPingThread() {
			Thread current = pingThread;
			if (current != null && current.isAlive()) {
				try {
					current.interrupt();
					current.join(TimeUnit.SECONDS.toMillis(5));
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					NandboxClient.log.warn("Interrupted while stopping pingThread", e);
				}
			}
			pingThread = null;
		}

		private void reconnectWebSocketClient() throws Exception {
			NandboxClient.log.info("Creating new webSocketClient");
			webSocketClient = new WebSocketClient(new SslContextFactory());
			webSocketClient.start();
			NandboxClient.log.info("webSocketClient started, calling NandboxClient connect");
			NandboxClient.get().connect(token, callback);
		}

		private void send(String s) {
			try {
				Session current = session;
				if (current != null && current.isOpen()) {
					current.getRemote().sendString(s);
				} else {
					NandboxClient.log.warn("Dropping outgoing message, websocket session is not open");
				}
			} catch (Exception e) {
				NandboxClient.log.error("Failed to send message over the websocket", e);
			}
		}

		public void stopWebSocketClient() {
			NandboxClient.log.info("Stopping Websocket client");
			try {
				Session current = getSession();
				if (current != null)
					current.close();
			} catch (Exception e) {
				NandboxClient.log.error("Exception while closing websocket session", e);
			}
			try {
				WebSocketClient client = webSocketClient;
				if (client != null) {
					client.stop();
					client.destroy();
					webSocketClient = null;
					NandboxClient.log.info("Websocket client stopped Successfully");
				}
			} catch (Exception e) {
				NandboxClient.log.error("Exception while stopping and destroying webSocketClient", e);
			}

		}

		@OnWebSocketConnect
		public void onConnect(Session session) {
			this.session = session;
			//System.out.println("INTERNAL: ONCONNECT");
			NandboxClient.log.info("INTERNAL: ONCONNECT");

			JSONObject authObject = new JSONObject();
			authObject.put(KEY_METHOD, "TOKEN_AUTH");
			authObject.put("token", token);
			authObject.put("rem", true);

			api = new Nandbox.Api() {

				@Override
				public void send(OutMessage message) {
					JSONObject messageObj = message.toJsonObject();
					NandboxClient.log.info(formatDate(new Date()) + ">>>>>> Sending Message :" + messageObj);
					InternalWebSocket.this.send(messageObj.toJSONString());
				}

				private void prepareOutMessage(OutMessage message, String chatId, String reference,
						String replyToMessageId, String toUserId, Integer webPagePreview, Boolean disableNotification,
						String caption, Integer chatSettings, String tab,String[] tags,String appId) {
					message.setApp_id(appId);
					message.setChatId(chatId);
					message.setReference(reference);

					if (toUserId != null) {
						message.setToUserId(toUserId);
					}
					if (replyToMessageId != null) {
						message.setReplyToMessageId(replyToMessageId);
					}
					if (webPagePreview != null) {
						message.setWebPagePreview((webPagePreview));
					}

					if (disableNotification != null) {
						message.setDisableNotification(disableNotification);
					}

					if (caption != null) {
						message.setCaption(caption);
					}

					if (chatSettings != null) {
						message.setChatSettings(chatSettings);
					}

					if (tab != null) {
						message.setTab(tab);
					}
					if (tags!=null){
						message.setTag(tags);
					}

				}

				@Override
				public Long sendText(String chatId, String text,String appId) {
					String reference = getUniqueId();
					sendText(chatId, text, reference,appId);

					return Long.valueOf(reference);
				}

				@Override
				public Long sendTextWithBackground(String chatId, String text, String bgColor,String appId) {
					String reference = getUniqueId();
					sendText(chatId, text, reference, null, null, null, null, null, bgColor, null,null,appId);

					return Long.valueOf(reference);
				}

				@Override
				public void sendText(String chatId, String text, String reference,String appId) {

					sendText(chatId, text, reference, null, null, null, null, null, null, null,null,appId);
				}

				@Override
				public void sendText(String chatId, String text, String reference, String replyToMessageId,
						String toUserId, Integer webPagePreview, Boolean disableNotification, Integer chatSettings,
						String bgColor, String tab,String[] tags,String appId) {
					TextOutMessage message = new TextOutMessage();
					prepareOutMessage(message, chatId, reference, replyToMessageId, toUserId, webPagePreview,
							disableNotification, null, chatSettings, tab,tags,appId);
					message.setMethod(OutMessageMethod.sendMessage);
					message.setText(text);
					message.setBgColor(bgColor);
					send(message);

				}

				@Override
				public Long sendPhoto(String chatId, String photoId, String caption,String appId) {

					String reference = getUniqueId();

					sendPhoto(chatId, photoId, reference, caption,appId);

					return Long.valueOf(reference);
				}



				@Override
				public void sendPhoto(String chatId, String photoId, String reference, String caption,String appId) {

					sendPhoto(chatId, photoId, reference, null, null, null, null, caption, null, null,null,appId);

				}

				@Override
				public void sendPhoto(String chatId, String photoFileId, String reference, String replyToMessageId,
						String toUserId, Integer webPagePreview, Boolean disableNotification, String caption,
						Integer chatSettings, String tab,String[] tags,String appId) {

					PhotoOutMessage message = new PhotoOutMessage();
					prepareOutMessage(message, chatId, reference, replyToMessageId, toUserId, webPagePreview,
							disableNotification, caption, chatSettings, tab,tags,appId);
					message.setMethod(OutMessageMethod.sendPhoto);
					message.setPhoto(photoFileId);
					send(message);

				}

				@Override
				public Long sendContact(String chatId, String phoneNumber, String name,String appId) {
					String reference = getUniqueId();
					sendContact(chatId, phoneNumber, name, reference, appId);
					return Long.valueOf(reference);
				}

				@Override
				public void sendContact(String chatId, String phoneNumber, String name, String reference,String appId) {

					sendContact(chatId, phoneNumber, name, reference, null, null, null, null, null, null,null,appId);
				}

				@Override
				public void sendContact(String chatId, String phoneNumber, String name, String reference,
						String replyToMessageId, String toUserId, Integer webPagePreview, Boolean disableNotification,
						Integer chatSettings, String tab,String[] tags,String appId) {

					ContactOutMessage contactOutMessage = new ContactOutMessage();
					prepareOutMessage(contactOutMessage, chatId, reference, replyToMessageId, toUserId, webPagePreview,
							disableNotification, null, chatSettings, tab,tags,appId);

					contactOutMessage.setMethod(OutMessageMethod.sendContact);
					contactOutMessage.setPhoneNumber(phoneNumber);
					contactOutMessage.setName(name);
					send(contactOutMessage);
				}

				@Override
				public Long sendVideo(String chatId, String videoId, String caption,String appId) {

					String reference = getUniqueId();
					sendVideo(chatId, videoId, reference, caption,appId);

					return Long.valueOf(reference);

				}

				@Override
				public void sendVideo(String chatId, String videoId, String reference, String caption,String appId) {

					sendVideo(chatId, videoId, reference, null, null, null, null, caption, null, null,null,appId);
				}

				@Override
				public void sendVideo(String chatId, String videoFileId, String reference, String replyToMessageId,
						String toUserId, Integer webPagePreview, Boolean disableNotification, String caption,
						Integer chatSettings, String tab,String[] tags,String appId) {

					VideoOutMessage message = new VideoOutMessage();
					prepareOutMessage(message, chatId, reference, replyToMessageId, toUserId, webPagePreview,
							disableNotification, caption, chatSettings, tab,tags,appId);
					message.setMethod(OutMessageMethod.sendVideo);
					message.setVideo(videoFileId);
					send(message);

				}

				@Override
				public Long sendAudio(String chatId, String audioId, String caption,String appId) {

					String reference = getUniqueId();
					sendAudio(chatId, audioId, reference, caption,appId);

					return Long.valueOf(reference);

				}

				@Override
				public void sendAudio(String chatId, String audioFileId, String reference, String caption,String appId) {

					sendAudio(chatId, audioFileId, reference, null, null, null, null, caption, null, null, null, null,null,appId);
				}

				@Override
				public void sendAudio(String chatId, String audioFileId, String reference, String replyToMessageId,
						String toUserId, Integer webPagePreview, Boolean disableNotification, String caption,
						String performer, String title, Integer chatSettings, String tab,String[] tags,String appId) {

					AudioOutMessage message = new AudioOutMessage();
					prepareOutMessage(message, chatId, reference, replyToMessageId, toUserId, webPagePreview,
							disableNotification, caption, chatSettings, tab,tags,appId);
					message.setMethod(OutMessageMethod.sendAudio);
					message.setPerformer(performer);
					message.setTitle(title);
					message.setAudio(audioFileId);
					send(message);

				}

				@Override
				public Long sendVoice(String chatId, String voiceFileId, String caption,String appId) {

					String reference = getUniqueId();
					sendVoice(chatId, voiceFileId, reference, caption,appId);
					return Long.valueOf(reference);
				}

				@Override
				public void sendVoice(String chatId, String voiceFileId, String reference, String caption,String appId) {

					sendVoice(chatId, voiceFileId, reference, null, null, null, null, caption, null, null, null,null,appId);
				}

				@Override
				public void sendVoice(String chatId, String voiceFileId, String reference, String replyToMessageId,
						String toUserId, Integer webPagePreview, Boolean disableNotification, String caption, Long size,
						Integer chatSettings, String tab,String[] tags,String appId) {

					VoiceOutMessage message = new VoiceOutMessage();
					prepareOutMessage(message, chatId, reference, replyToMessageId, toUserId, webPagePreview,
							disableNotification, caption, chatSettings, tab,tags,appId);
					message.setMethod(OutMessageMethod.sendVoice);
					message.setSize(size);
					message.setVoice(voiceFileId);
					send(message);
				}

				@Override
				public Long sendDocument(String chatId, String documentFileId, String caption,String appId) {

					String reference = getUniqueId();
					sendDocument(chatId, documentFileId, reference, caption, appId);
					return Long.valueOf(reference);
				}

				@Override
				public void sendDocument(String chatId, String documentFileId, String reference, String caption,String appId) {

					sendDocument(chatId, documentFileId, reference, null, null, null, null, caption, null, null, null, null,null,appId);

				}

				@Override
				public void sendDocument(String chatId, String documentFileId, String reference, String replyToMessageId,
						String toUserId, Integer webPagePreview, Boolean disableNotification, String caption,
						String name, Integer size, Integer chatSettings, String tab,String[] tags,String appId) {

					DocumentOutMessage message = new DocumentOutMessage();
					prepareOutMessage(message, chatId, reference, replyToMessageId, toUserId, webPagePreview,
							disableNotification, caption, chatSettings, tab,tags,appId);
					message.setMethod(OutMessageMethod.sendDocument);
					message.setDocument(documentFileId);
					message.setName(name);
					message.setSize(size);
					send(message);
				}

				@Override
				public Long sendlocation(String chatId, String latitude, String longitude,String appId) {

					String reference = getUniqueId();
					sendlocation(chatId, latitude, longitude, reference,appId);
					return Long.valueOf(reference);
				}

				@Override
				public void sendlocation(String chatId, String latitude, String longitude, String reference,String appId) {
					sendlocation(chatId, latitude, longitude, reference, null, null, null, null, null, null, null, null,null,appId);
				}

				@Override
				public void sendlocation(String chatId, String latitude, String longitude, String reference,
						String replyToMessageId, String toUserId, Integer webPagePreview, Boolean disableNotification,
						String name, String details, Integer chatSettings, String tab,String[] tags,String appId) {

					LocationOutMessage message = new LocationOutMessage();
					prepareOutMessage(message, chatId, reference, replyToMessageId, toUserId, webPagePreview,
							disableNotification, null, chatSettings, tab,tags,appId);
					message.setMethod(OutMessageMethod.sendLocation);
					message.setLatitude(latitude);
					message.setLongitude(longitude);
					message.setName(name);
					message.setDetails(details);
					send(message);

				}

				@Override
				public Long sendGIF(String chatId, String gif, String caption,String appId) {

					String reference = getUniqueId();

					sendPhoto(chatId, gif, reference, caption,appId);

					return Long.valueOf(reference);
				}

				@Override
				public void sendGIF(String chatId, String gif, String reference, String caption,String appId) {

					sendPhoto(chatId, gif, reference, null, null, null, null, caption, null, null,null,appId);
				}

				@Override
				public void sendGIF(String chatId, String gif, String reference, String replyToMessageId, String toUserId,
						Integer webPagePreview, Boolean disableNotification, String caption, Integer chatSettings, String tab,String[] tags,String appId) {

					PhotoOutMessage message = new PhotoOutMessage();
					prepareOutMessage(message, chatId, reference, replyToMessageId, toUserId, webPagePreview,
							disableNotification, caption, chatSettings, tab,tags,appId);
					message.setMethod(OutMessageMethod.sendPhoto);
					message.setPhoto(gif);
					send(message);

				}

				@Override
				public Long sendGIFVideo(String chatId, String gif, String caption,String appId) {

					String reference = getUniqueId();
					sendVideo(chatId, gif, reference, caption,appId);

					return Long.valueOf(reference);

				}

				@Override
				public void sendGIFVideo(String chatId, String gif, String reference, String caption,String appId) {

					sendVideo(chatId, gif, reference, null, null, null, null, caption, null, null,null,appId);
				}

				@Override
				public void sendGIFVideo(String chatId, String gif, String reference, String replyToMessageId,
						String toUserId, Integer webPagePreview, Boolean disableNotification, String caption,
						Integer chatSettings, String tab,String[] tags,String appId) {

					VideoOutMessage message = new VideoOutMessage();
					prepareOutMessage(message, chatId, reference, replyToMessageId, toUserId, webPagePreview,
							disableNotification, caption, chatSettings, tab,tags,appId);
					message.setMethod(OutMessageMethod.sendVideo);
					message.setVideo(gif);
					send(message);

				}

				@Override
				public void updateMessage(String messageId, String text, String caption, String toUserId,
						String chatId, String appId) {

					UpdateOutMessage updateMessage = new UpdateOutMessage();
					updateMessage.setApp_id(appId);
					updateMessage.setMessageId(messageId);
					updateMessage.setText(text);
					updateMessage.setCaption(caption);
					updateMessage.setToUserId(toUserId);
					updateMessage.setChatId(chatId);

					send(updateMessage);

				}

				@Override
				public void getCollectionProduct(String collectionProductId,String appId,String reference) {
					GetCollectionProductOutMessage collectionProductOutMessage = new GetCollectionProductOutMessage();
					collectionProductOutMessage.setId(collectionProductId);
					collectionProductOutMessage.setApp_id(appId);
					collectionProductOutMessage.setReference(reference);
					api.send(collectionProductOutMessage);
				}

				@Override
				public void updateTextMsg(String messageId, String text, String toUserId,String appId) {

					updateMessage(messageId, text, null, toUserId, null, appId);
				}

				@Override
				public void updateMediaCaption(String messageId, String caption, String toUserId,String appId) {

					updateMessage(messageId, null, caption, toUserId, null, appId);
				}

				@Override
				public void updateChatMsg(String messageId, String text, String chatId, String appId) {

					updateMessage(messageId, text, null, null, chatId, appId);
				}

				@Override
				public void updateChatMediaCaption(String messageId, String caption, String chatId, String appId) {

					updateMessage(messageId, null, caption, null, chatId,appId);
				}

				@Override
				public void getChatMember(String chatId, String userId,String appId,String reference) {
					GetChatMemberOutMessage getChatMemberOutMessage = new GetChatMemberOutMessage();
					getChatMemberOutMessage.setChatId(chatId);
					getChatMemberOutMessage.setUserId(userId);
					getChatMemberOutMessage.setApp_id(appId);
					getChatMemberOutMessage.setReference(reference);
					api.send(getChatMemberOutMessage);
				}

				@Override
				public void getUser(String userId,String appId,String reference) {
					GetUserOutMessage getUserOutMessage = new GetUserOutMessage();
					getUserOutMessage.setUserId(userId);
					getUserOutMessage.setApp_id(appId);
                    getUserOutMessage.setChatId(appId);
					getUserOutMessage.setReference(reference);
					api.send(getUserOutMessage);

				}

				@Override
				public void getChat(String chatId,String appId,String reference) {
					GetChatOutMessage chatOutMessage = new GetChatOutMessage();
					chatOutMessage.setChatId(chatId);
					chatOutMessage.setApp_id(appId);
					chatOutMessage.setReference(reference);
					api.send(chatOutMessage);
				}
				@Override
				public void getProductDetail(String productId,String appId,String reference) {
					GetProductItemOutMessage getProductItemOutMessage = new GetProductItemOutMessage();
					getProductItemOutMessage.setProductId(productId);
					getProductItemOutMessage.setApp_id(appId);
					getProductItemOutMessage.setRef(reference);
					api.send(getProductItemOutMessage);
				}

				@Override
				public void listCollectionItem(String appId,String reference) {
					ListCollectionItemOutMessage listCollectionItemOutMessage = new ListCollectionItemOutMessage();
					listCollectionItemOutMessage.setApp_id(appId);
					listCollectionItemOutMessage.setRef(reference);
					api.send(listCollectionItemOutMessage);
				}

				@Override
				public void getChatAdministrators(String chatId,String appId,String reference) {
					GetChatAdministratorsOutMessage getChatAdministratorsOutMessage = new GetChatAdministratorsOutMessage();
					getChatAdministratorsOutMessage.setChatId(chatId);
					getChatAdministratorsOutMessage.setApp_id(appId);
					getChatAdministratorsOutMessage.setReference(reference);
					api.send(getChatAdministratorsOutMessage);
				}

				@Override
				public void banChatMember(String chatId, String userId,String appId,String reference) {
					BanChatMemberOutMessage banChatMemberOutMessage = new BanChatMemberOutMessage();
					banChatMemberOutMessage.setChatId(chatId);
					banChatMemberOutMessage.setUserId(userId);
					banChatMemberOutMessage.setApp_id(appId);
					banChatMemberOutMessage.setReference(reference);
					api.send(banChatMemberOutMessage);
				}

				@Override
				public void addBlackList( List<String> users,String appId,String reference) {

					AddBlackListOutMessage addBlackListOutMessage = new AddBlackListOutMessage();
					addBlackListOutMessage.setReference(reference);
					addBlackListOutMessage.setUsers(users);
					addBlackListOutMessage.setApp_id(appId);
					api.send(addBlackListOutMessage);
				}

				/**
				 * @param chatId
				 * @param userId
				 */
				@Override
				public void addChatMember(long chatId, long userId,String appId) {
					AddChatMemberOutMessage addChatMemberOutMessage = new AddChatMemberOutMessage();
					addChatMemberOutMessage.setChatId(chatId);
					addChatMemberOutMessage.setUserId(userId);
					addChatMemberOutMessage.setApp_id(appId);
					api.send(addChatMemberOutMessage);
				}

				/**
				 * @param chatId
				 * @param userId
				 */
				@Override
				public void addChatAdminMember(long chatId, long userId,String appId) {
					AddChatAdminMemberOutMessage addChatAdminMemberOutMessage = new AddChatAdminMemberOutMessage();
					addChatAdminMemberOutMessage.setChatId(chatId);
					addChatAdminMemberOutMessage.setUserId(userId);
					addChatAdminMemberOutMessage.setApp_id(appId);
					api.send(addChatAdminMemberOutMessage);
				}

                @Override
                public void submitPaymentResult(String chatId, long accountId, String orderId, JSONObject providerResponse, String secret, String currency, double totalAmount, String appId, Status status, long debitAmountCents) {
                    PaymentConfirmationOutMessage paymentConfirmationOutMessage = new PaymentConfirmationOutMessage();
                    paymentConfirmationOutMessage.setOrderId(orderId);
                    paymentConfirmationOutMessage.setChatId(chatId);
                    paymentConfirmationOutMessage.setAccountId(accountId);
                    paymentConfirmationOutMessage.setProviderResponse(providerResponse);
                    paymentConfirmationOutMessage.setSecret(secret);
                    paymentConfirmationOutMessage.setCurrency(currency);
                    paymentConfirmationOutMessage.setTotalAmount(totalAmount);
                    paymentConfirmationOutMessage.setStatus(status.toString());
                    paymentConfirmationOutMessage.setApp_id(appId);
                    paymentConfirmationOutMessage.setDebitAmountCents(debitAmountCents);
                    api.send(paymentConfirmationOutMessage);
                }

                @Override
                public void sendNotification(long userId, NotificationType notificationType, String title, String message, String appId) {
                    SendUserNotificationOutMessage sendNotificationOutMessage = new SendUserNotificationOutMessage();
                    sendNotificationOutMessage.setAccountId(userId);
                    sendNotificationOutMessage.setType(notificationType);
                    sendNotificationOutMessage.setTitle(title);
                    sendNotificationOutMessage.setMessage(message);
                    sendNotificationOutMessage.setApp_id(appId);
                    api.send(sendNotificationOutMessage);
                }

                @Override
				public void addWhiteList(List<WhiteListUser> whiteListUsers,String appId,String reference) {

					AddWhiteListOutMessage addWhiteistOutMessage = new AddWhiteListOutMessage();

					addWhiteistOutMessage.setReference(reference);
					addWhiteistOutMessage.setWhiteListUser(whiteListUsers);
					addWhiteistOutMessage.setApp_id(appId);
					api.send(addWhiteistOutMessage);
				}

				/**
				 * @param userId
				 * @param menuId
				 * @param cells
				 * @param reference
				 * @param disableNotification
				 */
				@Override
				public void updateMenuCell(String userId, String menuId, String appId, JSONArray cells, String reference, Boolean disableNotification) {
					UpdateMenuCell setWorkflowOutMessage = new UpdateMenuCell();
					setWorkflowOutMessage.setUserId(userId);
					setWorkflowOutMessage.setMenuId(menuId);
					setWorkflowOutMessage.setAppId(appId);
					setWorkflowOutMessage.setCells(cells);
					setWorkflowOutMessage.setReference(reference);
					setWorkflowOutMessage.setDisableNotification(disableNotification);

					api.send(setWorkflowOutMessage);

				}

				/**
				 * @param userId
				 * @param vappId
				 * @param screenId
				 * @param nextScreen
				 * @param reference
				 */
				@Override
				public void setWorkflowAction(String userId,String vappId, String screenId, String nextScreen, String reference,String appId) {
					SetWorkflowActionOutMessage setWorkflowActionOutMessage = new SetWorkflowActionOutMessage();
					setWorkflowActionOutMessage.setUserId(userId);
					setWorkflowActionOutMessage.setVappId(vappId);
					setWorkflowActionOutMessage.setScreenId(screenId);
					setWorkflowActionOutMessage.setNextScreen(nextScreen);
					setWorkflowActionOutMessage.setReference(reference);
					setWorkflowActionOutMessage.setApp_id(appId);
					api.send(setWorkflowActionOutMessage);
				}

				/**
				 * @param type
				 * @param title
				 * @param isPublic
				 */
				@Override
				public void createChat(String type,String title, int isPublic,String reference,String appId) {
					CreateChatOutMessage createChatOutMessage = new CreateChatOutMessage();
					createChatOutMessage.setType(type);
					createChatOutMessage.setTitle(title);
					createChatOutMessage.setIsPublic(isPublic);
					createChatOutMessage.setReference(reference);
					createChatOutMessage.setApp_id(appId);

					api.send(createChatOutMessage);
				}

				@Override
				public void deleteBlackList( List<String> users,String appId,String reference) {

					DeleteBlackListOutMessage deleteBlackListOutMessage = new DeleteBlackListOutMessage();
					deleteBlackListOutMessage.setReference(reference);
					deleteBlackListOutMessage.setUsers(users);
					deleteBlackListOutMessage.setApp_id(appId);
					api.send(deleteBlackListOutMessage);
				}

				@Override
				public void deleteWhiteList( List<String> users,String appId,String reference) {

					DeleteWhiteListOutMessage deleteWhiteListOutMessage = new DeleteWhiteListOutMessage();
					deleteWhiteListOutMessage.setReference(reference);
					deleteWhiteListOutMessage.setUsers(users);
					deleteWhiteListOutMessage.setApp_id(appId);
					api.send(deleteWhiteListOutMessage);
				}

				@Override
				public void deleteBlackListPatterns(String chatId, List<String> pattern,String appId,String reference) {

					DeleteBlackListPatternsOutMessage deleteBlackListPatterns = new DeleteBlackListPatternsOutMessage();
					deleteBlackListPatterns.setChatId(chatId);
					deleteBlackListPatterns.setReference(reference);
					deleteBlackListPatterns.setPattern(pattern);
					deleteBlackListPatterns.setApp_id(appId);
					api.send(deleteBlackListPatterns);
				}

				@Override
				public void deleteWhiteListPatterns(String chatId, List<String> pattern,String appId,String reference) {

					DeleteWhiteListPatternsOutMessage deleteWhiteListPatterns = new DeleteWhiteListPatternsOutMessage();
					deleteWhiteListPatterns.setChatId(chatId);
					deleteWhiteListPatterns.setReference(reference);
					deleteWhiteListPatterns.setPattern(pattern);
					deleteWhiteListPatterns.setApp_id(appId);
					api.send(deleteWhiteListPatterns);
				}


				@Override
				public void addBlacklistPatterns(String chatId, List<Data> data,String appId,String reference) {

					AddBlacklistPatternsOutMessage addBlacklistPatternsOutMessage = new AddBlacklistPatternsOutMessage();
					addBlacklistPatternsOutMessage.setChatId(chatId);
					addBlacklistPatternsOutMessage.setReference(reference);
					addBlacklistPatternsOutMessage.setData(data);
					addBlacklistPatternsOutMessage.setApp_id(appId);
					api.send(addBlacklistPatternsOutMessage);
				}

				@Override
				public void addWhitelistPatterns(String chatId, List<Data> data,String appId,String reference) {

					AddWhitelistPatternsOutMessage addWhitelistPatternsOutMessage = new AddWhitelistPatternsOutMessage();
					addWhitelistPatternsOutMessage.setChatId(chatId);
					addWhitelistPatternsOutMessage.setReference(reference);
					addWhitelistPatternsOutMessage.setData(data);
					addWhitelistPatternsOutMessage.setApp_id(appId);
					api.send(addWhitelistPatternsOutMessage);
				}

				@Override
				public void unbanChatMember(String chatId, String userId,String appId,String reference) {
					UnbanChatMember unbanChatMember = new UnbanChatMember();
					unbanChatMember.setChatId(chatId);
					unbanChatMember.setUserId(userId);
					unbanChatMember.setApp_id(appId);
					unbanChatMember.setReference(reference);
					api.send(unbanChatMember);

				}

				@Override
				public void removeChatMember(String chatId, String userId,String appId,String reference) {

					RemoveChatMemberOutMessage removeChatMemberOutMessage = new RemoveChatMemberOutMessage();
					removeChatMemberOutMessage.setChatId(chatId);
					removeChatMemberOutMessage.setUserId(userId);
					removeChatMemberOutMessage.setApp_id(appId);
					removeChatMemberOutMessage.setReference(reference);
					api.send(removeChatMemberOutMessage);
				}

				@Override
				public void recallMessage(String chatId, String messageId, String toUserId, String reference,String appId) {
					RecallOutMessage recallOutMessage = new RecallOutMessage();
					recallOutMessage.setApp_id(appId);
					recallOutMessage.setChatId(chatId);
					recallOutMessage.setMessageId(messageId);
					recallOutMessage.setToUserId(toUserId);
					recallOutMessage.setReference(reference);
					api.send(recallOutMessage);
				}

				@Override
				public void setMyProfile(User user,String reference) {

					SetMyProfileOutMessage setMyProfileOutMessage = new SetMyProfileOutMessage();
					setMyProfileOutMessage.setUser(user);
					setMyProfileOutMessage.setReference(reference);
					api.send(setMyProfileOutMessage);
				}

				@Override
				public void setChat(Chat chat,String appId,String reference) {
					SetChatOutMessage setChatOutMessage = new SetChatOutMessage();
					setChatOutMessage.setChat(chat);
					setChatOutMessage.setApp_id(appId);
					setChatOutMessage.setReference(reference);
					api.send(setChatOutMessage);

				}

				@Override
				public void getMyProfiles(String reference) {
					GetMyProfiles getMyProfiles = new GetMyProfiles();
					getMyProfiles.setReference(reference);
					api.send(getMyProfiles);
				}

				@Override
				public void getBlackList(String appId,String reference) {
					GetBlackListOutMessage getBlackListOutMessage = new GetBlackListOutMessage();
					getBlackListOutMessage.setReference(reference);
					getBlackListOutMessage.setApp_id(appId);
					api.send(getBlackListOutMessage);
				}

				@Override
				public void getWhiteList(String appId,String reference) {
					GetWhiteListOutMessage getWhiteListOutMessage = new GetWhiteListOutMessage();
					getWhiteListOutMessage.setApp_id(appId);
					getWhiteListOutMessage.setReference(reference);
					api.send(getWhiteListOutMessage);
				}

				@Override
				public void generatePermanentUrl(String file, String param1) {
					GeneratePermanentUrl generatePermanentUrl = new GeneratePermanentUrl();
					generatePermanentUrl.setFile(file);
					generatePermanentUrl.setParam1(param1);
					api.send(generatePermanentUrl);

				}

				@Override
				@Deprecated
				public void sendCellText(String userId, String screenId, String cellId, String text, String reference) {
					TextCellOutMessage textMsg = new TextCellOutMessage();
					textMsg.setUserId(userId);
					textMsg.setCellId(cellId);
					textMsg.setScreenId(screenId);
					textMsg.setText(text);
					textMsg.setReference(reference);
					api.send(textMsg);
				}

				@Override
				@Deprecated
				public void sendCellPhoto(String userId, String screenId, String cellId, String photoFileId,
						String reference) {
					PhotoCellOutMessage photoMsg = new PhotoCellOutMessage();
					photoMsg.setUserId(userId);
					photoMsg.setCellId(cellId);
					photoMsg.setScreenId(screenId);
					photoMsg.setPhoto(photoFileId);
					photoMsg.setReference(reference);
					api.send(photoMsg);
				}

				@Override
				@Deprecated
				public void sendCellVideo(String userId, String screenId, String cellId, String videoFileId,
						String reference) {
					VideoCellOutMessage videoMsg = new VideoCellOutMessage();
					videoMsg.setUserId(userId);
					videoMsg.setCellId(cellId);
					videoMsg.setScreenId(screenId);
					videoMsg.setVideo(videoFileId);
					videoMsg.setReference(reference);
					api.send(videoMsg);
				}
//                @Override
//                public void runCustomCode(long userId,JSONObject data,String appId){
//                        RunCustomCodeOutMessage runCustomCodeOutMessage = new RunCustomCodeOutMessage();
//                        runCustomCodeOutMessage.setUserId(userId);
//                        runCustomCodeOutMessage.setApp_id(appId);
//                        runCustomCodeOutMessage.setData(data);
//                        api.send(runCustomCodeOutMessage);
//
//                }

			};
			// The auth payload carries the bot token; never log its contents.
			NandboxClient.log.info("Sending TOKEN_AUTH");
			send(authObject.toJSONString());
		}

		@OnWebSocketMessage
		public void onUpdate(String msg) {
			messageThreadPool.execute(()->{
				try {
					dispatch(msg);
				} catch (Exception e) {
					// A failure while parsing or while inside a user callback must not
					// silently kill the worker task without a trace.
					NandboxClient.log.error("Error while handling incoming message", e);
				}
			});

		}

		private void dispatch(String msg) {
			User user;
			String appId;
			lastMessage = System.currentTimeMillis();
			NandboxClient.log.info("INTERNAL: ONMESSAGE");
			Object parsed = JSONValue.parse(msg);
			if (!(parsed instanceof JSONObject)) {
				NandboxClient.log.warn("Ignoring non-object websocket payload: " + msg);
				return;
			}
			JSONObject obj = (JSONObject) parsed;
			NandboxClient.log.info(formatDate(new Date()) + " >>>>>>>>> Update Obj : " + obj);
			String method = (String) obj.get(KEY_METHOD);
			if (method != null) {
					NandboxClient.log.info("method: " + method);
					switch (method) {
						case "TOKEN_AUTH_OK":
							authenticated = true;
							BOT_ID = String.valueOf(obj.get(KEY_ID));
							NandboxClient.log.info("Authenticated! Bot Id is : " + BOT_ID
									+ ", Bot Name is : " + obj.get(KEY_NAME));
							stopPingThread();
							pingThread = new PingThread();
							pingThread.setName("PingThread");
							pingThread.start();
							callback.onConnect(api);
							return;
						case "message":
							IncomingMessage incomingMsg = new IncomingMessage(obj);
							callback.onReceive(incomingMsg);
							return;
						case "getProductItemResponse":
							ProductItemResponse productItem = new ProductItemResponse(obj);
							callback.onProductDetail(productItem);
							return;
						case "scheduledMessage":
							IncomingMessage incomingScheduleMsg = new IncomingMessage(obj);
							callback.onScheduleMessage(incomingScheduleMsg);
							return;
						case "chatMenuCallback":
							ChatMenuCallback chatMenuCallback = new ChatMenuCallback(obj);
							callback.onChatMenuCallBack(chatMenuCallback);
							return;
						case "inlineMessageCallback":
							InlineMessageCallback inlineMsgCallback = new InlineMessageCallback(obj);
							callback.onInlineMessageCallback(inlineMsgCallback);
							return;
						case "inlineSearch":
							InlineSearch inlineSearch = new InlineSearch(obj);
							callback.onInlineSearh(inlineSearch);
							return;
						case "getCollectionProductResponse":
							GetProductCollectionResponse getProductCollectionResponse = new GetProductCollectionResponse(obj);
							callback.onCollectionProduct(getProductCollectionResponse);
							return;
						case "messageAck":
							MessageAck msgAck = new MessageAck(obj);
							callback.onMessagAckCallback(msgAck);
							return;
						case "userJoinedBot":
							user = new User((JSONObject) obj.get(KEY_USER));
							callback.onUserJoinedBot(user);
							return;
						case "chatMember":
							ChatMember chatMember = new ChatMember(obj);
							callback.onChatMember(chatMember);
							return;
						case "createChatAck":
							Chat chatObj = new Chat((JSONObject) obj.get(KEY_CHAT));
							chatObj.setReference((String) obj.get(KEY_REFERENCE));
							callback.onCreateChat(chatObj);
							return;
						case "myProfile":
							user = new User((JSONObject) obj.get(KEY_USER));

							callback.onMyProfile(user);
							return;
						case "userDetails":
							user = new User((JSONObject) obj.get(KEY_USER));
							appId = String.valueOf(obj.get(KEY_APP_ID));

							callback.onUserDetails(user,appId);
							return;
						case "listCollectionsResponse":
							ListCollectionItemResponse listCollectionItemResponse = new ListCollectionItemResponse(obj);
							callback.listCollectionItemResponse(listCollectionItemResponse);
							return ;
						case "chatDetails":
							Chat chat = new Chat((JSONObject) obj.get(KEY_CHAT));
							appId = String.valueOf(obj.get(KEY_APP_ID));
							callback.onChatDetails(chat,appId);
							return;
						case "chatAdministrators":
							ChatAdministrators chatAdministrators = new ChatAdministrators(obj);
							callback.onChatAdministrators(chatAdministrators);
							return;
						case "userStartedBot":
							user = new User((JSONObject) obj.get(KEY_USER));
							callback.userStartedBot(user);
							return;
						case "userStoppedBot":
							user = new User((JSONObject) obj.get(KEY_USER));
							callback.userStoppedBot(user);
							return;
						case "userLeftBot":
							user = new User((JSONObject) obj.get(KEY_USER));
							callback.userLeftBot(user);
							return;
						case "addBlacklistPatterns_ack":
						// removeBlacklistPatterns_ack is what the server actually sends
						// (ApiRemoveBlacklistPatterns); the delete* spelling is kept only
						// for backward compatibility.
						case "removeBlacklistPatterns_ack":
						case "deleteBlacklistPatterns_ack":
							Pattern blackListpattern = new Pattern(obj);
							callback.onBlackListPattern(blackListpattern);
							return;
						case "removeWhitelistPatterns_ack":
						case "deleteWhitelistPatterns_ack":
						case "addWhitelistPatterns_ack":
							Pattern deletedWhiteListpattern = new Pattern(obj);
							callback.onWhiteListPattern(deletedWhiteListpattern);
							return;
						case "removeFromBlacklist_ack":
							List_ak blackListAk=new List_ak(obj);
							callback.onDeleteBlackList(blackListAk);
							return;
						case "addToBlacklist_ack":
						case "getBlacklistUsersResponse":
						case "blacklist":
							BlackList blackList = new BlackList(obj);
							callback.onBlackList(blackList);
							return;
						case "removeFromWhitelist_ack":
							List_ak whiteListAk=new List_ak(obj);
							callback.onDeleteWhiteList(whiteListAk);
							return;
						case "addToWhitelist_ack":
						case "getWhitelistUsersResponse":
						case "whitelist":
							WhiteList whiteList = new WhiteList(obj);
							callback.onWhiteList(whiteList);
							return;
						case "permanentUrl":
							PermanentUrl permenantURL = new PermanentUrl(obj);
							callback.permanentUrl(permenantURL);
							return;
						case "workflowCell":
							WorkflowDetails workflowDetails = new WorkflowDetails(obj);
							callback.onWorkflowDetails(workflowDetails);
							return;
						case "menuCallback":
							MenuCallback menuCallback = new MenuCallback(obj);
							callback.onMenuCallBack(menuCallback);
							return;
                        case "extensionSetDocResponse":
                        case "extensionGetDocResponse":
                        case "extensionDeleteDocResponse":
                        case "extensionListDocResponse":

                            String type = null;

                            switch (method) {
                                case "extensionSetDocResponse":
                                    type = "insert";
                                    break;
                                case "extensionGetDocResponse":
                                    type = "get";
                                    break;
                                case "extensionDeleteDocResponse":
                                    type = "delete";
                                    break;
                                case "extensionListDocResponse":
                                    type = "list";
                                    break;
                            }

                            obj.put("method", type);
                            ExtensionDocResponse extensionDocResponse = new ExtensionDocResponse(obj);
                            callback.onExtensionDocResponse(extensionDocResponse);
                            return;

                        case "paymentAuthorizationRequest":
                            PaymentRequest paymentRequest = new PaymentRequest(obj);
                            callback.onPaymentAuthorizationRequest(paymentRequest);
                            return;
                        case "WebhookEvent":
                            WebhookBody webhookEvent = new WebhookBody(obj);
                            callback.onWebhookEvent(webhookEvent);
                            return;
						default:
							callback.onReceive(obj);
							return;
					}
				} else {
					String error = String.valueOf(obj.get(KEY_ERROR));
					NandboxClient.log.error("Error : " + error);
				}
		}

		@OnWebSocketError
		public void onError(Session session, Throwable cause) {
			if (cause == null) {
				NandboxClient.log.error("INTERNAL: ONERROR with no cause");
				return;
			}
			NandboxClient.log.error("INTERNAL: ONERROR", cause);
			callback.onError();

			if (cause instanceof ConnectException && timeOutCounter < NO_OF_RETRIES_IF_CONN_TIMEDOUT) {
				reconnectAfter(10, ++timeOutCounter, "Connection time out count is : ");
			} else if (cause instanceof SocketTimeoutException
					&& connRefusedCounter < NO_OF_RETRIES_IF_CONN_TO_SERVER_REFUSED) {
				reconnectAfter(30, ++connRefusedCounter, "Connection refused counter : ");
			}
		}

		/**
		 * Closes the current client, waits {@code delaySeconds}, then reconnects.
		 */
		private void reconnectAfter(int delaySeconds, int attempt, String counterMessage) {
			try {
				NandboxClient.log.info("Please wait " + delaySeconds + " seconds for Reconnecting ");
				stopWebSocketClient();
				TimeUnit.SECONDS.sleep(delaySeconds);
				NandboxClient.log.info(counterMessage + attempt);
				reconnectWebSocketClient();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				NandboxClient.log.warn("Interrupted while waiting to reconnect", e);
			} catch (Exception e) {
				NandboxClient.log.error("Failed to reconnect the websocket client", e);
			}
		}

		private Session getSession() {
			return session;
		}
	}

	private NandboxClient() throws Exception {
		String configuredUri = configs.getProperty("URI");
		if (configuredUri == null) {
			throw new IllegalStateException(
					"Missing required property 'URI' in " + CONFIG_FILE + " (expected wss://<SERVER>:<PORT>/nandbox/api/)");
		}
		setUri(new URI(configuredUri));
		setLogger(configs.getProperty("MaxLogSize"), configs.getProperty("NumberOfLogFiles"),
				configs.getProperty("LogLevel"), configs.getProperty("LogPath"));
		webSocketClient = new WebSocketClient(new SslContextFactory());
		webSocketClient.start();

	}

	public static synchronized void init() throws Exception {
		if (nandboxClient != null)
			return;
		nandboxClient = new NandboxClient();
	}

	public static synchronized NandboxClient get() throws Exception {
		if (nandboxClient == null)
			init();

		return nandboxClient;
	}

	public void connect(String token, Nandbox.Callback callback) throws IOException {

		InternalWebSocket internalWebSocket = new InternalWebSocket(token, callback);

		WebSocketClient client = webSocketClient;
		if (client == null) {
			throw new IOException("The websocket client has been stopped; cannot connect.");
		}
		client.connect(internalWebSocket, uri, new ClientUpgradeRequest());

	}

	public URI getUri() {
		return uri;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}

	public static String getBotId() {
		return BOT_ID;
	}

	public void setLogger(String maxSize,String numOfFiles,String level,String path) throws IOException
	{
		if(level == null)
			level = "Info";
		if(maxSize == null)
			maxSize = "5kb";
		if(numOfFiles == null)
			numOfFiles = "5";
		if(path == null)
			path ="logsInfo";

		if(level.equalsIgnoreCase("Debug"))
		{
			this.rootLogger.setLevel(Level.DEBUG);
		}
		else if(level.equalsIgnoreCase("Info"))
		{
			this.rootLogger.setLevel(Level.INFO);
		}
		else if(level.equalsIgnoreCase("Warn"))
		{
			this.rootLogger.setLevel(Level.WARN);
		}
		else if(level.equalsIgnoreCase("Error"))
		{
			this.rootLogger.setLevel(Level.ERROR);
		}
		else if(level.equalsIgnoreCase("Fatal"))
		{
			this.rootLogger.setLevel(Level.FATAL);
		}
		else if(level.equalsIgnoreCase("Trace"))
		{
			this.rootLogger.setLevel(Level.TRACE);
		}

		int maxBackupIndex;
		try {
			maxBackupIndex = Integer.parseInt(numOfFiles.trim());
		} catch (NumberFormatException e) {
			log.warn("Invalid NumberOfLogFiles '" + numOfFiles + "', using 5");
			maxBackupIndex = 5;
		}

		PatternLayout layout = new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n");
		RollingFileAppender fileAppender = new RollingFileAppender(layout,path);
		fileAppender.setMaxBackupIndex(maxBackupIndex);
		fileAppender.setMaxFileSize(maxSize);
		rootLogger.addAppender(fileAppender);
	}


}
