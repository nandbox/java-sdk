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
import java.util.concurrent.TimeUnit;

import com.nandbox.bots.api.data.*;
import com.nandbox.bots.api.inmessages.*;
import com.nandbox.bots.api.outmessages.*;
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
	private static final String CONFIG_FILE = "config.properties";
	private static String BOT_ID = null;
	private static NandboxClient nandboxClient;
	private WebSocketClient webSocketClient;
	int closingCounter = 0;
	int timeOutCounter = 0;
	int connRefusedCounter = 0;
	private URI uri;
	static final String KEY_METHOD = "method";
	static final String KEY_ERROR = "error";
	public static final Logger log = Logger.getLogger(NandboxClient.class);
	Logger rootLogger = Logger.getRootLogger();



	public static Properties getConfigs() {
		Properties configs = new Properties();
		InputStream configIs;
		try {
			configIs = new FileInputStream(CONFIG_FILE);
			configs.load(configIs);
			configIs.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return configs;
	}




	@WebSocket(maxTextMessageSize = 100000)
	public class InternalWebSocket {
		private static final int NO_OF_RETRIES_IF_CONN_TO_SERVER_REFUSED = 20;
		private static final int NO_OF_RETRIES_IF_CONN_TIMEDOUT = 10;
		private static final int NO_OF_RETRIES_IF_CONN_CLOSED = 20;
		private static final String KEY_USER = "user";
		private static final String KEY_CHAT = "chat";
		private static final String KEY_NAME = "name";
		private static final String KEY_ID = "ID";

		Nandbox.Callback callback;
		Session session;
		String token;
		Nandbox.Api api;
		boolean authenticated = false;
		boolean echo = false;
		long lastMessage = 0;

		class PingThread extends Thread {
			boolean interrupted = false;

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
			//System.out.println("INTERNAL: ONCLOSE");
			//System.out.println("StatusCode = " + statusCode);
			//System.out.println("Reason : " + reason);
			NandboxClient.log.info("INTERNAL: ONCLOSE");
			NandboxClient.log.info("StatusCode = " + statusCode);
			NandboxClient.log.info("Reason : " + reason);

			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			//System.out.println("Date = " + dateFormat.format(date));
			NandboxClient.log.info("Date = " + dateFormat.format(date));


			authenticated = false;
			if (pingThread != null) {
				try {
					pingThread.interrupt();
				} catch (Exception e) {
					//System.err.println(e);
					NandboxClient.log.error(e);
				}
			}
			pingThread = null;

			callback.onClose();

			if ((statusCode == 1000 || statusCode == 1006 || statusCode == 1001 || statusCode == 1005)
					&& closingCounter < NO_OF_RETRIES_IF_CONN_CLOSED) {
				try {
					//System.out.println("Please wait 10 seconds for Reconnecting ");
					NandboxClient.log.info("Please wait 10 seconds for Reconnecting ");
					TimeUnit.SECONDS.sleep(10);
					closingCounter = closingCounter + 1;
					//System.out.println("Conenction Closing counter is  : " + closingCounter);
					NandboxClient.log.info("Conenction Closing counter is  : " + closingCounter);
				} catch (InterruptedException e1) {
					//e1.printStackTrace();
					NandboxClient.log.error(e1.getStackTrace());
					Thread.currentThread().interrupt();
				}
				stopWebSocketClient();
				try {
					reconnectWebSocketClient();
				} catch (Exception e) {
					//e.printStackTrace();
					NandboxClient.log.error(e.getStackTrace());
					Thread.currentThread().interrupt();
				}

			} else {
				//System.out.println("End nandbox client");
				NandboxClient.log.info("End nandbox client");
				System.exit(0);
			}
		}

		private void reconnectWebSocketClient() throws Exception {
			//System.out.println("Creating new webSocketClient");
			NandboxClient.log.info("Creating new webSocketClient");
			webSocketClient = new WebSocketClient(new SslContextFactory());
			webSocketClient.start();
			//System.out.println("webSocketClient started");
			//System.out.println("Getting NandboxClient Instance");
			NandboxClient.log.info("webSocketClient started");
			NandboxClient.log.info("Getting NandboxClient Instance");
			NandboxClient nandboxClient = NandboxClient.get();
			//System.out.println("Calling NandboxClient connect");
			NandboxClient.log.info("Calling NandboxClient connect");
			nandboxClient.connect(token, callback);
		}

		private void send(String s) {

			try {
				if (session != null && session.isOpen()) {
					session.getRemote().sendString(s);
				}
			} catch (IOException e) {
				//e.printStackTrace();
				NandboxClient.log.error(e.getStackTrace());
			} catch (Exception e) {
				//e.printStackTrace();
				NandboxClient.log.error(e.getStackTrace());
			}
		}

		public void stopWebSocketClient() {
			//System.out.println("Stopping Websocket client");
			NandboxClient.log.info("Stopping Websocket client");
			try {
				if (InternalWebSocket.this != null)
					InternalWebSocket.this.getSession().close();
			} catch (Exception e) {
				//System.out.println("Exception : " + e.getMessage() + " while closing websocket session");
				NandboxClient.log.error("Exception : " + e.getMessage() + " while closing websocket session");
			}
			try {
				if (webSocketClient != null) {
					webSocketClient.stop();
					webSocketClient.destroy();
					webSocketClient = null;
					//System.out.println("Websocket client stopped Successfully");
					NandboxClient.log.info("Websocket client stopped Successfully");
				}
			} catch (Exception e) {
				//System.out.println("Exception : " + e.getMessage() + " while stopping and destroying webSocketClient");
				NandboxClient.log.error("Exception : " + e.getMessage() + " while stopping and destroying webSocketClient");
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
					System.out.println(formatDate(new Date()) + ">>>>>> Sending Message :" + messageObj);
					NandboxClient.log.info(formatDate(new Date()) + ">>>>>> Sending Message :" + messageObj);
					InternalWebSocket.this.send(messageObj.toJSONString());
				}

				private void prepareOutMessage(OutMessage message, String chatId, Long reference,
						String replyToMessageId, String toUserId, Integer webPagePreview, Boolean disableNotification,
						String caption, Integer chatSettings, String tab) {

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

				}

				@Override
				public Long sendText(String chatId, String text) {
					Long reference = getUniqueId();
					sendText(chatId, text, reference);

					return reference;
				}

				@Override
				public Long sendTextWithBackground(String chatId, String text, String bgColor) {
					Long reference = getUniqueId();
					sendText(chatId, text, reference, null, null, null, null, null, bgColor, null);

					return reference;
				}

				@Override
				public void sendText(String chatId, String text, Long reference) {

					sendText(chatId, text, reference, null, null, null, null, null, null, null);
				}

				@Override
				public void sendText(String chatId, String text, Long reference, String replyToMessageId,
						String toUserId, Integer webPagePreview, Boolean disableNotification, Integer chatSettings,
						String bgColor, String tab) {
					TextOutMessage message = new TextOutMessage();
					prepareOutMessage(message, chatId, reference, replyToMessageId, toUserId, webPagePreview,
							disableNotification, null, chatSettings, tab);
					message.setMethod(OutMessageMethod.sendMessage);
					message.setText(text);
					message.setBgColor(bgColor);
					send(message);

				}

				@Override
				public Long sendPhoto(String chatId, String photoId, String caption) {

					Long reference = getUniqueId();

					sendPhoto(chatId, photoId, reference, caption);

					return reference;
				}

				@Override
				public void sendPhoto(String chatId, String photoId, Long reference, String caption) {

					sendPhoto(chatId, photoId, reference, null, null, null, null, caption, null, null);

				}

				@Override
				public void sendPhoto(String chatId, String photoFileId, Long reference, String replyToMessageId,
						String toUserId, Integer webPagePreview, Boolean disableNotification, String caption,
						Integer chatSettings, String tab) {

					PhotoOutMessage message = new PhotoOutMessage();
					prepareOutMessage(message, chatId, reference, replyToMessageId, toUserId, webPagePreview,
							disableNotification, caption, chatSettings, tab);
					message.setMethod(OutMessageMethod.sendPhoto);
					message.setPhoto(photoFileId);
					send(message);

				}

				@Override
				public Long sendContact(String chatId, String phoneNumber, String name) {
					Long reference = getUniqueId();
					sendContact(chatId, phoneNumber, name, reference);
					return reference;
				}

				@Override
				public void sendContact(String chatId, String phoneNumber, String name, Long reference) {

					sendContact(chatId, phoneNumber, name, reference, null, null, null, null, null, null);
				}

				@Override
				public void sendContact(String chatId, String phoneNumber, String name, Long reference,
						String replyToMessageId, String toUserId, Integer webPagePreview, Boolean disableNotification,
						Integer chatSettings, String tab) {

					ContactOutMessage contactOutMessage = new ContactOutMessage();
					prepareOutMessage(contactOutMessage, chatId, reference, replyToMessageId, toUserId, webPagePreview,
							disableNotification, null, chatSettings, tab);

					contactOutMessage.setMethod(OutMessageMethod.sendContact);
					contactOutMessage.setPhoneNumber(phoneNumber);
					contactOutMessage.setName(name);
					send(contactOutMessage);
				}

				@Override
				public Long sendVideo(String chatId, String videoId, String caption) {

					Long reference = getUniqueId();
					sendVideo(chatId, videoId, reference, caption);

					return reference;

				}

				@Override
				public void sendVideo(String chatId, String videoId, Long reference, String caption) {

					sendVideo(chatId, videoId, reference, null, null, null, null, caption, null, null);
				}

				@Override
				public void sendVideo(String chatId, String videoFileId, Long reference, String replyToMessageId,
						String toUserId, Integer webPagePreview, Boolean disableNotification, String caption,
						Integer chatSettings, String tab) {

					VideoOutMessage message = new VideoOutMessage();
					prepareOutMessage(message, chatId, reference, replyToMessageId, toUserId, webPagePreview,
							disableNotification, caption, chatSettings, tab);
					message.setMethod(OutMessageMethod.sendVideo);
					message.setVideo(videoFileId);
					send(message);

				}

				@Override
				public Long sendAudio(String chatId, String audioId, String caption) {

					Long reference = getUniqueId();
					sendAudio(chatId, audioId, reference, caption);

					return reference;

				}

				@Override
				public void sendAudio(String chatId, String audioFileId, Long reference, String caption) {

					sendAudio(chatId, audioFileId, reference, null, null, null, null, caption, null, null, null, null);
				}

				@Override
				public void sendAudio(String chatId, String audioFileId, Long reference, String replyToMessageId,
						String toUserId, Integer webPagePreview, Boolean disableNotification, String caption,
						String performer, String title, Integer chatSettings, String tab) {

					AudioOutMessage message = new AudioOutMessage();
					prepareOutMessage(message, chatId, reference, replyToMessageId, toUserId, webPagePreview,
							disableNotification, caption, chatSettings, tab);
					message.setMethod(OutMessageMethod.sendAudio);
					message.setPerformer(performer);
					message.setTitle(title);
					message.setAudio(audioFileId);
					send(message);

				}

				@Override
				public Long sendVoice(String chatId, String voiceFileId, String caption) {

					Long reference = getUniqueId();
					sendVoice(chatId, voiceFileId, reference, caption);
					return reference;
				}

				@Override
				public void sendVoice(String chatId, String voiceFileId, Long reference, String caption) {

					sendVoice(chatId, voiceFileId, reference, null, null, null, null, caption, null, null, null);
				}

				@Override
				public void sendVoice(String chatId, String voiceFileId, Long reference, String replyToMessageId,
						String toUserId, Integer webPagePreview, Boolean disableNotification, String caption, Long size,
						Integer chatSettings, String tab) {

					VoiceOutMessage message = new VoiceOutMessage();
					prepareOutMessage(message, chatId, reference, replyToMessageId, toUserId, webPagePreview,
							disableNotification, caption, chatSettings, tab);
					message.setMethod(OutMessageMethod.sendVoice);
					message.setSize(size);
					message.setVoice(voiceFileId);
					send(message);
				}

				@Override
				public Long sendDocument(String chatId, String documentFileId, String caption) {

					Long reference = getUniqueId();
					sendDocument(chatId, documentFileId, reference, caption);
					return reference;
				}

				@Override
				public void sendDocument(String chatId, String documentFileId, Long reference, String caption) {

					sendDocument(chatId, documentFileId, reference, null, null, null, null, caption, null, null, null, null);

				}

				@Override
				public void sendDocument(String chatId, String documentFileId, Long reference, String replyToMessageId,
						String toUserId, Integer webPagePreview, Boolean disableNotification, String caption,
						String name, Integer size, Integer chatSettings, String tab) {

					DocumentOutMessage message = new DocumentOutMessage();
					prepareOutMessage(message, chatId, reference, replyToMessageId, toUserId, webPagePreview,
							disableNotification, caption, chatSettings, tab);
					message.setMethod(OutMessageMethod.sendDocument);
					message.setDocument(documentFileId);
					message.setName(name);
					message.setSize(size);
					send(message);
				}

				@Override
				public Long sendlocation(String chatId, String latitude, String longitude) {

					Long reference = getUniqueId();
					sendlocation(chatId, latitude, longitude, reference);
					return reference;
				}

				@Override
				public void sendlocation(String chatId, String latitude, String longitude, Long reference) {
					sendlocation(chatId, latitude, longitude, reference, null, null, null, null, null, null, null, null);
				}

				@Override
				public void sendlocation(String chatId, String latitude, String longitude, Long reference,
						String replyToMessageId, String toUserId, Integer webPagePreview, Boolean disableNotification,
						String name, String details, Integer chatSettings, String tab) {

					LocationOutMessage message = new LocationOutMessage();
					prepareOutMessage(message, chatId, reference, replyToMessageId, toUserId, webPagePreview,
							disableNotification, null, chatSettings, tab);
					message.setMethod(OutMessageMethod.sendLocation);
					message.setName(name);
					message.setDetails(details);
					send(message);

				}

				@Override
				public Long sendGIF(String chatId, String gif, String caption) {

					Long reference = getUniqueId();

					sendPhoto(chatId, gif, reference, caption);

					return reference;
				}

				@Override
				public void sendGIF(String chatId, String gif, Long reference, String caption) {

					sendPhoto(chatId, gif, reference, null, null, null, null, caption, null, null);
				}

				@Override
				public void sendGIF(String chatId, String gif, Long reference, String replyToMessageId, String toUserId,
						Integer webPagePreview, Boolean disableNotification, String caption, Integer chatSettings, String tab) {

					PhotoOutMessage message = new PhotoOutMessage();
					prepareOutMessage(message, chatId, reference, replyToMessageId, toUserId, webPagePreview,
							disableNotification, caption, chatSettings, tab);
					message.setMethod(OutMessageMethod.sendPhoto);
					message.setPhoto(gif);
					send(message);

				}

				@Override
				public Long sendGIFVideo(String chatId, String gif, String caption) {

					Long reference = getUniqueId();
					sendVideo(chatId, gif, reference, caption);

					return reference;

				}

				@Override
				public void sendGIFVideo(String chatId, String gif, Long reference, String caption) {

					sendVideo(chatId, gif, reference, null, null, null, null, caption, null, null);
				}

				@Override
				public void sendGIFVideo(String chatId, String gif, Long reference, String replyToMessageId,
						String toUserId, Integer webPagePreview, Boolean disableNotification, String caption,
						Integer chatSettings, String tab) {

					VideoOutMessage message = new VideoOutMessage();
					prepareOutMessage(message, chatId, reference, replyToMessageId, toUserId, webPagePreview,
							disableNotification, caption, chatSettings, tab);
					message.setMethod(OutMessageMethod.sendVideo);
					message.setVideo(gif);
					send(message);

				}

				@Override
				public void updateMessage(String messageId, String text, String caption, String toUserId,
						String chatId, String tab) {

					UpdateOutMessage updateMessage = new UpdateOutMessage();

					updateMessage.setMessageId(messageId);
					updateMessage.setText(text);
					updateMessage.setCaption(caption);
					updateMessage.setToUserId(toUserId);
					updateMessage.setChatId(chatId);
					updateMessage.setTab(tab);

					send(updateMessage);

				}

				@Override
				public void updateTextMsg(String messageId, String text, String toUserId, String tab) {

					updateMessage(messageId, text, null, toUserId, null, tab);
				}

				@Override
				public void updateMediaCaption(String messageId, String caption, String toUserId, String tab) {

					updateMessage(messageId, null, caption, toUserId, null, tab);
				}

				@Override
				public void updateChatMsg(String messageId, String text, String chatId, String tab) {

					updateMessage(messageId, text, null, null, chatId, tab);
				}

				@Override
				public void updateChatMediaCaption(String messageId, String caption, String chatId, String tab) {

					updateMessage(messageId, null, caption, null, chatId, tab);
				}

				@Override
				public void getChatMember(String chatId, String userId) {
					GetChatMemberOutMessage getChatMemberOutMessage = new GetChatMemberOutMessage();
					getChatMemberOutMessage.setChatId(chatId);
					getChatMemberOutMessage.setUserId(userId);
					api.send(getChatMemberOutMessage);
				}

				@Override
				public void getUser(String userId) {
					GetUserOutMessage getUserOutMessage = new GetUserOutMessage();
					getUserOutMessage.setUserId(userId);
					api.send(getUserOutMessage);

				}

				@Override
				public void getChat(String chatId) {

					GetChatOutMessage chatOutMessage = new GetChatOutMessage();
					chatOutMessage.setChatId(chatId);
					api.send(chatOutMessage);
				}

				@Override
				public void getChatAdministrators(String chatId) {

					GetChatAdministratorsOutMessage getChatAdministratorsOutMessage = new GetChatAdministratorsOutMessage();
					getChatAdministratorsOutMessage.setChatId(chatId);
					api.send(getChatAdministratorsOutMessage);
				}

				@Override
				public void banChatMember(String chatId, String userId) {
					BanChatMemberOutMessage banChatMemberOutMessage = new BanChatMemberOutMessage();
					banChatMemberOutMessage.setChatId(chatId);
					banChatMemberOutMessage.setUserId(userId);
					api.send(banChatMemberOutMessage);
				}

				@Override
				public void addBlackList(String chatId, List<String> users) {

					AddBlackListOutMessage addBlackListOutMessage = new AddBlackListOutMessage();
					addBlackListOutMessage.setChatId(chatId);
					addBlackListOutMessage.setUsers(users);

					api.send(addBlackListOutMessage);
				}

				@Override
				public void addWhiteList(String chatId, List<WhiteListUser> whiteListUsers) {

					AddWhiteListOutMessage addWhiteistOutMessage = new AddWhiteListOutMessage();

					addWhiteistOutMessage.setChatId(chatId);
					addWhiteistOutMessage.setWhiteListUser(whiteListUsers);
					api.send(addWhiteistOutMessage);
				}

				/**
				 * @param userId
				 * @param screenId
				 * @param workflowCell
				 * @param reference
				 * @param disableNotification
				 */
				@Override
				public void setWorkflow(String userId, String screenId,String appId, List<WorkflowCell> workflowCell, Long reference, Boolean disableNotification) {
					SetWorkflowOutMessage setWorkflowOutMessage = new SetWorkflowOutMessage();
					setWorkflowOutMessage.setUserId(userId);
					setWorkflowOutMessage.setScreenId(screenId);
					setWorkflowOutMessage.setAppId(appId);
					setWorkflowOutMessage.setWorkflowCell(workflowCell);
					setWorkflowOutMessage.setReference(reference);
					setWorkflowOutMessage.setDisableNotification(disableNotification);

					send(setWorkflowOutMessage);

				}

				@Override
				public void deleteBlackList(String chatId, List<String> users) {

					DeleteBlackListOutMessage deleteBlackListOutMessage = new DeleteBlackListOutMessage();
					deleteBlackListOutMessage.setChatId(chatId);
					deleteBlackListOutMessage.setUsers(users);

					api.send(deleteBlackListOutMessage);
				}

				@Override
				public void deleteWhiteList(String chatId, List<String> users) {

					DeleteWhiteListOutMessage deleteWhiteListOutMessage = new DeleteWhiteListOutMessage();
					deleteWhiteListOutMessage.setChatId(chatId);
					deleteWhiteListOutMessage.setUsers(users);

					api.send(deleteWhiteListOutMessage);
				}

				@Override
				public void deleteBlackListPatterns(String chatId, List<String> pattern) {

					DeleteBlackListPatternsOutMessage deleteBlackListPatterns = new DeleteBlackListPatternsOutMessage();
					deleteBlackListPatterns.setChatId(chatId);
					deleteBlackListPatterns.setPattern(pattern);

					api.send(deleteBlackListPatterns);
				}

				@Override
				public void deleteWhiteListPatterns(String chatId, List<String> pattern) {

					DeleteWhiteListPatternsOutMessage deleteWhiteListPatterns = new DeleteWhiteListPatternsOutMessage();
					deleteWhiteListPatterns.setChatId(chatId);
					deleteWhiteListPatterns.setPattern(pattern);

					api.send(deleteWhiteListPatterns);
				}


				@Override
				public void addBlacklistPatterns(String chatId, List<Data> data) {

					AddBlacklistPatternsOutMessage addBlacklistPatternsOutMessage = new AddBlacklistPatternsOutMessage();
					addBlacklistPatternsOutMessage.setChatId(chatId);
					addBlacklistPatternsOutMessage.setData(data);
					api.send(addBlacklistPatternsOutMessage);
				}

				@Override
				public void addWhitelistPatterns(String chatId, List<Data> data) {

					AddWhitelistPatternsOutMessage addWhitelistPatternsOutMessage = new AddWhitelistPatternsOutMessage();
					addWhitelistPatternsOutMessage.setChatId(chatId);
					addWhitelistPatternsOutMessage.setData(data);
					api.send(addWhitelistPatternsOutMessage);
				}

				@Override
				public void unbanChatMember(String chatId, String userId) {
					UnbanChatMember unbanChatMember = new UnbanChatMember();
					unbanChatMember.setChatId(chatId);
					unbanChatMember.setUserId(userId);
					api.send(unbanChatMember);

				}

				@Override
				public void removeChatMember(String chatId, String userId) {

					RemoveChatMemberOutMessage removeChatMemberOutMessage = new RemoveChatMemberOutMessage();
					removeChatMemberOutMessage.setChatId(chatId);
					removeChatMemberOutMessage.setUserId(userId);
					api.send(removeChatMemberOutMessage);
				}

				@Override
				public void recallMessage(String chatId, String messageId, String toUserId, Long reference) {
					RecallOutMessage recallOutMessage = new RecallOutMessage();
					recallOutMessage.setChatId(chatId);
					recallOutMessage.setMessageId(messageId);
					recallOutMessage.setToUserId(toUserId);
					recallOutMessage.setReference(reference);
					api.send(recallOutMessage);
				}

				@Override
				public void setMyProifle(User user) {

					SetMyProfileOutMessage setMyProfileOutMessage = new SetMyProfileOutMessage();
					setMyProfileOutMessage.setUser(user);
					api.send(setMyProfileOutMessage);
				}

				@Override
				public void setChat(Chat chat) {
					SetChatOutMessage setChatOutMessage = new SetChatOutMessage();
					setChatOutMessage.setChat(chat);
					api.send(setChatOutMessage);

				}

				@Override
				public void getMyProfiles() {
					GetMyProfiles getMyProfiles = new GetMyProfiles();
					api.send(getMyProfiles);
				}

				@Override
				public void getBlackList(String chatId) {
					GetBlackListOutMessage getBlackListOutMessage = new GetBlackListOutMessage();
					getBlackListOutMessage.setChatId(chatId);
					api.send(getBlackListOutMessage);
				}

				@Override
				public void getWhiteList(String chatId) {
					GetWhiteListOutMessage getWhiteListOutMessage = new GetWhiteListOutMessage();
					getWhiteListOutMessage.setChatId(chatId);
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
				public void sendCellText(String userId, String screenId, String cellId, String text, Long reference) {
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
						Long reference) {
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
						Long reference) {
					VideoCellOutMessage videoMsg = new VideoCellOutMessage();
					videoMsg.setUserId(userId);
					videoMsg.setCellId(cellId);
					videoMsg.setScreenId(screenId);
					videoMsg.setVideo(videoFileId);
					videoMsg.setReference(reference);
					api.send(videoMsg);
				}

			};
			//System.err.println(authObject.toJSONString());
			NandboxClient.log.error(authObject.toJSONString());
			send(authObject.toJSONString());
		}

		@OnWebSocketMessage
		public void onUpdate(String msg) {
			User user;
			lastMessage = System.currentTimeMillis();
			//System.out.println("INTERNAL: ONMESSAGE");
			NandboxClient.log.info("INTERNAL: ONMESSAGE");
			JSONObject obj = (JSONObject) JSONValue.parse(msg);
			//System.out.println(formatDate(new Date()) + " >>>>>>>>> Update Obj : " + obj);
			NandboxClient.log.info(formatDate(new Date()) + " >>>>>>>>> Update Obj : " + obj);
			String method = (String) obj.get(KEY_METHOD);
			if (method != null) {
				//System.err.println("method: " + method);
				NandboxClient.log.info("method: " + method);
				switch (method) {
				case "TOKEN_AUTH_OK":
					System.out.println("Authenticated!");
					NandboxClient.log.info("Authenticated!");
					authenticated = true;
					BOT_ID = String.valueOf(obj.get(KEY_ID));
					System.err.println("====> Your Bot Id is : " + BOT_ID);
					System.err.println("====> Your Bot Name is : " + (String) obj.get(KEY_NAME));
					NandboxClient.log.info("====> Your Bot Id is : " + BOT_ID);
					NandboxClient.log.info("====> Your Bot Name is : " + (String) obj.get(KEY_NAME));
					if (pingThread != null) {
						try {
							pingThread.interrupt();
						} catch (Exception e) {
							//System.err.println(e);
							NandboxClient.log.error(e);
						}
					}
					pingThread = null;
					pingThread = new PingThread();
					pingThread.setName("PingThread");
					pingThread.start();
					callback.onConnect(api);
					return;
				case "message":
					IncomingMessage incomingMsg = new IncomingMessage(obj);
					callback.onReceive(incomingMsg);
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
				case "myProfile":
					user = new User((JSONObject) obj.get(KEY_USER));
					callback.onMyProfile(user);
					return;
				case "userDetails":
					user = new User((JSONObject) obj.get(KEY_USER));
					callback.onUserDetails(user);
					return;
				case "chatDetails":
					Chat chat = new Chat((JSONObject) obj.get(KEY_CHAT));
					callback.onChatDetails(chat);
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
				case "blacklist":
					BlackList blackList = new BlackList(obj);
					callback.onBlackList(blackList);
					return;
				case "whitelist":
					WhiteList whiteList = new WhiteList(obj);
					callback.onWhiteList(whiteList);
					return;
				case "permanentUrl":
					PermanentUrl permenantURL = new PermanentUrl(obj);
					callback.permanentUrl(permenantURL);
					return;
				case "workflowDetails":
					WorkflowDetails workflowDetails = new WorkflowDetails(obj);
					callback.onWorkflowDetails(workflowDetails);
					return;
				default:
					callback.onReceive(obj);
					return;
				}
			} else {
				String error = String.valueOf(obj.get(KEY_ERROR));
				//System.err.println("Error : " + error);
				NandboxClient.log.error("Error : " + error);
			}
		}

		@OnWebSocketError
		public void onError(Session session, Throwable cause) {
			//System.err.println("INTERNAL: ONERROR");
			NandboxClient.log.error("INTERNAL: ONERROR");
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			if (cause != null) {
				//System.err.println("Error due to : " + cause.getMessage() + " On : " + dateFormat.format(date));
				NandboxClient.log.error("Error due to : " + cause.getMessage() + " On : " + dateFormat.format(date));
				callback.onError();

				if (cause instanceof ConnectException && timeOutCounter < NO_OF_RETRIES_IF_CONN_TIMEDOUT) {

					try {
						//System.out.println(cause.getMessage() + " , Please wait 10 seconds for Reconnecting ");
						NandboxClient.log.info(cause.getMessage() + " , Please wait 10 seconds for Reconnecting ");
						stopWebSocketClient();
						TimeUnit.SECONDS.sleep(10);
						timeOutCounter++;
						//System.out.println("Connection Time out count is : " + timeOutCounter);
						NandboxClient.log.info("Connection Time out count is : " + timeOutCounter);
						reconnectWebSocketClient();
					} catch (Exception e1) {
						//e1.printStackTrace();
						NandboxClient.log.error(e1.getStackTrace());
						Thread.currentThread().interrupt();
					}

				} else if (cause instanceof SocketTimeoutException
						&& connRefusedCounter < NO_OF_RETRIES_IF_CONN_TO_SERVER_REFUSED) {
					try {
						//System.out.println(cause.getMessage() + ", Please wait 30 seconds for Reconnecting ");
						NandboxClient.log.info(cause.getMessage() + ", Please wait 30 seconds for Reconnecting ");
						stopWebSocketClient();
						TimeUnit.SECONDS.sleep(30);
						connRefusedCounter++;
						//System.out.println("Connection Refused Counter " + connRefusedCounter);
						NandboxClient.log.info("Connection Refused Counter " + connRefusedCounter);
						reconnectWebSocketClient();
					} catch (Exception e1) {
						//nots sure
						e1.printStackTrace();
						Thread.currentThread().interrupt();
					}

				}
			}
		}

		private Session getSession() {
			return session;
		}
	}

	private NandboxClient() throws Exception {
		setUri(new URI(getConfigs().getProperty("URI")));
		setLogger(getConfigs().getProperty("MaxLogSize"),getConfigs().getProperty("NumberOfLogFiles"),getConfigs().getProperty("LogLevel"),getConfigs().getProperty("LogPath"));
		webSocketClient = new WebSocketClient(new SslContextFactory());
		webSocketClient.start();

	}

	public static synchronized void init() throws Exception {
		if (nandboxClient != null)
			return;
		nandboxClient = new NandboxClient();
	}

	public static NandboxClient get() throws Exception {
		if (nandboxClient == null)
			init();

		return nandboxClient;
	}

	public void connect(String token, Nandbox.Callback callback) throws IOException {

		InternalWebSocket internalWebSocket = new InternalWebSocket(token, callback);

		webSocketClient.connect(internalWebSocket, uri, new ClientUpgradeRequest());

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
		System.out.println(level);
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

		PatternLayout layout = new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n");
		RollingFileAppender fileAppender = new RollingFileAppender(layout,path);
		fileAppender.setMaxBackupIndex(Integer.parseInt(numOfFiles));
		fileAppender.setMaxFileSize(maxSize);
		rootLogger.addAppender(fileAppender);
	}


}
