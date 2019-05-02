package com.nandbox.bots.api;

import static com.nandbox.bots.api.util.Utils.getUniqueId;
import static com.nandbox.bots.api.util.Utils.formatDate;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import com.nandbox.bots.api.data.Chat;
import com.nandbox.bots.api.data.User;
import com.nandbox.bots.api.inmessages.ChatAdministrators;
import com.nandbox.bots.api.inmessages.ChatMember;
import com.nandbox.bots.api.inmessages.ChatMenuCallback;
import com.nandbox.bots.api.inmessages.IncomingMessage;
import com.nandbox.bots.api.inmessages.InlineMessageCallback;
import com.nandbox.bots.api.inmessages.InlineSearch;
import com.nandbox.bots.api.inmessages.MessageAck;
import com.nandbox.bots.api.inmessages.PermanentUrl;
import com.nandbox.bots.api.outmessages.AudioOutMessage;
import com.nandbox.bots.api.outmessages.BanChatMemberOutMessage;
import com.nandbox.bots.api.outmessages.ContactOutMessage;
import com.nandbox.bots.api.outmessages.DocumentOutMessage;
import com.nandbox.bots.api.outmessages.GeneratePermanentUrl;
import com.nandbox.bots.api.outmessages.GetChatAdministratorsOutMessage;
import com.nandbox.bots.api.outmessages.GetChatMemberOutMessage;
import com.nandbox.bots.api.outmessages.GetChatOutMessage;
import com.nandbox.bots.api.outmessages.GetMyProfiles;
import com.nandbox.bots.api.outmessages.GetUserOutMessage;
import com.nandbox.bots.api.outmessages.LocationOutMessage;
import com.nandbox.bots.api.outmessages.OutMessage;
import com.nandbox.bots.api.outmessages.PhotoOutMessage;
import com.nandbox.bots.api.outmessages.RecallOutMessage;
import com.nandbox.bots.api.outmessages.RemoveChatMemberOutMessage;
import com.nandbox.bots.api.outmessages.SetChatOutMessage;
import com.nandbox.bots.api.outmessages.SetMyProfileOutMessage;
import com.nandbox.bots.api.outmessages.TextOutMessage;
import com.nandbox.bots.api.outmessages.UnbanChatMember;
import com.nandbox.bots.api.outmessages.UpdateOutMessage;
import com.nandbox.bots.api.outmessages.VideoOutMessage;
import com.nandbox.bots.api.outmessages.VoiceOutMessage;
import com.nandbox.bots.api.outmessages.OutMessage.OutMessageMethod;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

/**
 * NandboxClient Class
 * 
 * @author Hossam
 *
 */
public class NandboxClient {
	private static final String CONFIG_FILE = "config.properties";
	private static String BOT_ID = null ;
	private static NandboxClient nandboxClient;
	private WebSocketClient webSocketClient;
	int closingCounter = 0;
	int timeOutCounter = 0;
	int connRefusedCounter = 0;
	private URI uri;
	static final String KEY_METHOD = "method";
	static final String KEY_ERROR = "error";
	

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
		private static final int NO_OF_RETRIES_IF_CONN_CLOSED = 10;
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
						System.err.println(e);
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
			System.out.println("INTERNAL: ONCLOSE");
			System.out.println("StatusCode = " + statusCode);
			System.out.println("Reason : " + reason);

			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			System.out.println("Date = " + dateFormat.format(date));

			authenticated = false;
			if (pingThread != null) {
				try {
					pingThread.interrupt();
				} catch (Exception e) {
					System.err.println(e);
				}
			}
			pingThread = null;

			callback.onClose();

			if ((statusCode == 1000 || statusCode == 1006 || statusCode == 1001 || statusCode == 1005)
					&& closingCounter < NO_OF_RETRIES_IF_CONN_CLOSED) {
				try {

					System.out.println("Please wait 10 seconds for Reconnecting ");
					TimeUnit.SECONDS.sleep(10);
					closingCounter = closingCounter + 1;
					System.out.println("Conenction Closing counter is  : " + closingCounter);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
					Thread.currentThread().interrupt();
				}
				stopWebSocketClient();
				try {
					reconnectWebSocketClient();
				} catch (Exception e) {
					e.printStackTrace();
					Thread.currentThread().interrupt();
				}

			} else {
				System.out.println("End nandbox client");
				System.exit(0);
			}
		}

		private void reconnectWebSocketClient() throws Exception {
			System.out.println("Creating new webSocketClient");
			webSocketClient = new WebSocketClient(new SslContextFactory());
			webSocketClient.start();
			System.out.println("webSocketClient started");
			System.out.println("Getting NandboxClient Instance");
			NandboxClient nandboxClient = NandboxClient.get();
			System.out.println("Calling NandboxClient connect");
			nandboxClient.connect(token, callback);
		}

		private void send(String s) {

			try {
				if (session != null && session.isOpen()) {
					session.getRemote().sendString(s);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void stopWebSocketClient() {
			System.out.println("Stopping Websocket client");
			try {
				if (InternalWebSocket.this != null)
					InternalWebSocket.this.getSession().close();
			} catch (Exception e) {
				System.out.println("Exception : " + e.getMessage() + " while closing websocket session");
			}
			try {
				if (webSocketClient != null) {
					webSocketClient.stop();
					webSocketClient.destroy();
					webSocketClient = null;
					System.out.println("Websocket client stopped Successfully");
				}
			} catch (Exception e) {
				System.out.println("Exception : " + e.getMessage() + " while stopping and destroying webSocketClient");
			}

		}

		@OnWebSocketConnect
		public void onConnect(Session session) {
			this.session = session;
			System.out.println("INTERNAL: ONCONNECT");

			JSONObject authObject = new JSONObject();
			authObject.put(KEY_METHOD, "TOKEN_AUTH");
			authObject.put("token", token);
			authObject.put("rem", true);

			api = new Nandbox.Api() {

				@Override
				public void send(OutMessage message) {
					JSONObject messageObj = message.toJsonObject();
					System.err.println(formatDate(new Date()) + ">>>>>> Sending Message :" + messageObj);
					InternalWebSocket.this.send(messageObj.toJSONString());
				}

				private void prepareOutMessage(OutMessage message, String chatId, Long reference,
						String replyToMessageId, String toUserId, Integer webPagePreview, Boolean disableNotification,
						String caption, Integer chatSettings) {

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

				}

				@Override
				public Long sendText(String chatId, String text) {
					Long reference = getUniqueId();
					sendText(chatId, text, reference);

					return reference;
				}
				
				@Override
				public Long sendTextWithBackground(String chatId, String text,String bgColor) {
					Long reference = getUniqueId();
					sendText(chatId, text, reference,null,null,null,null,null,bgColor);

					return reference;
				}
				@Override
				public void sendText(String chatId, String text, Long reference) {

					sendText(chatId, text, reference, null, null, null, null, null,null);
				}

				@Override
				public void sendText(String chatId, String text, Long reference, String replyToMessageId,
						String toUserId, Integer webPagePreview, Boolean disableNotification, Integer chatSettings,String bgColor) {
					TextOutMessage message = new TextOutMessage();
					prepareOutMessage(message, chatId, reference, replyToMessageId, toUserId, webPagePreview,
							disableNotification, null, chatSettings);
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

					sendPhoto(chatId, photoId, reference, null, null, null, null, caption, null);

				}

				@Override
				public void sendPhoto(String chatId, String photoFileId, Long reference, String replyToMessageId,
						String toUserId, Integer webPagePreview, Boolean disableNotification, String caption,
						Integer chatSettings) {

					PhotoOutMessage message = new PhotoOutMessage();
					prepareOutMessage(message, chatId, reference, replyToMessageId, toUserId, webPagePreview,
							disableNotification, caption, chatSettings);
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

					sendContact(chatId, phoneNumber, name, reference, null, null, null, null, null);
				}

				@Override
				public void sendContact(String chatId, String phoneNumber, String name, Long reference,
						String replyToMessageId, String toUserId, Integer webPagePreview, Boolean disableNotification,
						Integer chatSettings) {

					ContactOutMessage contactOutMessage = new ContactOutMessage();
					prepareOutMessage(contactOutMessage, chatId, reference, replyToMessageId, toUserId, webPagePreview,
							disableNotification, null, chatSettings);

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

					sendVideo(chatId, videoId, reference, null, null, null, null, caption, null);
				}

				@Override
				public void sendVideo(String chatId, String videoFileId, Long reference, String replyToMessageId,
						String toUserId, Integer webPagePreview, Boolean disableNotification, String caption,
						Integer chatSettings) {

					VideoOutMessage message = new VideoOutMessage();
					prepareOutMessage(message, chatId, reference, replyToMessageId, toUserId, webPagePreview,
							disableNotification, caption, chatSettings);
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

					sendAudio(chatId, audioFileId, reference, null, null, null, null, caption, null, null, null);
				}

				@Override
				public void sendAudio(String chatId, String audioFileId, Long reference, String replyToMessageId,
						String toUserId, Integer webPagePreview, Boolean disableNotification, String caption,
						String performer, String title, Integer chatSettings) {

					AudioOutMessage message = new AudioOutMessage();
					prepareOutMessage(message, chatId, reference, replyToMessageId, toUserId, webPagePreview,
							disableNotification, caption, chatSettings);
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

					sendVoice(chatId, voiceFileId, reference, null, null, null, null, caption, null, null);
				}

				@Override
				public void sendVoice(String chatId, String voiceFileId, Long reference, String replyToMessageId,
						String toUserId, Integer webPagePreview, Boolean disableNotification, String caption, Long size,
						Integer chatSettings) {

					VoiceOutMessage message = new VoiceOutMessage();
					prepareOutMessage(message, chatId, reference, replyToMessageId, toUserId, webPagePreview,
							disableNotification, caption, chatSettings);
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

					sendDocument(chatId, documentFileId, reference, null, null, null, null, caption, null, null, null);

				}

				@Override
				public void sendDocument(String chatId, String documentFileId, Long reference, String replyToMessageId,
						String toUserId, Integer webPagePreview, Boolean disableNotification, String caption,
						String name, Integer size, Integer chatSettings) {

					DocumentOutMessage message = new DocumentOutMessage();
					prepareOutMessage(message, chatId, reference, replyToMessageId, toUserId, webPagePreview,
							disableNotification, caption, chatSettings);
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
					sendlocation(chatId, latitude, longitude, reference, null, null, null, null, null, null, null);
				}

				@Override
				public void sendlocation(String chatId, String latitude, String longitude, Long reference,
						String replyToMessageId, String toUserId, Integer webPagePreview, Boolean disableNotification,
						String name, String details, Integer chatSettings) {

					LocationOutMessage message = new LocationOutMessage();
					prepareOutMessage(message, chatId, reference, replyToMessageId, toUserId, webPagePreview,
							disableNotification, null, chatSettings);
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

					sendPhoto(chatId, gif, reference, null, null, null, null, caption, null);
				}

				@Override
				public void sendGIF(String chatId, String gif, Long reference, String replyToMessageId, String toUserId,
						Integer webPagePreview, Boolean disableNotification, String caption, Integer chatSettings) {

					PhotoOutMessage message = new PhotoOutMessage();
					prepareOutMessage(message, chatId, reference, replyToMessageId, toUserId, webPagePreview,
							disableNotification, caption, chatSettings);
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

					sendVideo(chatId, gif, reference, null, null, null, null, caption, null);
				}

				@Override
				public void sendGIFVideo(String chatId, String gif, Long reference, String replyToMessageId,
						String toUserId, Integer webPagePreview, Boolean disableNotification, String caption,
						Integer chatSettings) {

					VideoOutMessage message = new VideoOutMessage();
					prepareOutMessage(message, chatId, reference, replyToMessageId, toUserId, webPagePreview,
							disableNotification, caption, chatSettings);
					message.setMethod(OutMessageMethod.sendVideo);
					message.setVideo(gif);
					send(message);

				}

				@Override
				public void updateMessage(String messageId, String text, String caption, String toUserId,
						String chatId) {

					UpdateOutMessage updateMessage = new UpdateOutMessage();

					updateMessage.setMessageId(messageId);
					updateMessage.setText(text);
					updateMessage.setCaption(caption);
					updateMessage.setToUserId(toUserId);
					updateMessage.setChatId(chatId);

					send(updateMessage);

				}

				@Override
				public void updateTextMsg(String messageId, String text, String toUserId) {

					updateMessage(messageId, text, null, toUserId, null);
				}

				@Override
				public void updateMediaCaption(String messageId, String caption, String toUserId) {

					updateMessage(messageId, null, caption, toUserId, null);
				}

				@Override
				public void updateChatMsg(String messageId, String text, String chatId) {

					updateMessage(messageId, text, null, null, chatId);
				}

				@Override
				public void updateChatMediaCaption(String messageId, String caption, String chatId) {

					updateMessage(messageId, null, caption, null, chatId);
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
				public void generatePermanentUrl(String file, String param1) {
					GeneratePermanentUrl generatePermanentUrl = new GeneratePermanentUrl();
					generatePermanentUrl.setFile(file);
					generatePermanentUrl.setParam1(param1);
					api.send(generatePermanentUrl);

				}

			};
			System.err.println(authObject.toJSONString());
			send(authObject.toJSONString());
		}

		@OnWebSocketMessage
		public void onUpdate(String msg) {
			User user;
			lastMessage = System.currentTimeMillis();
			System.out.println("INTERNAL: ONMESSAGE");
			JSONObject obj = (JSONObject) JSONValue.parse(msg);
			System.out.println(formatDate(new Date()) + " >>>>>>>>> Update Obj : " + obj);
			String method = (String) obj.get(KEY_METHOD);
			if (method != null) {
				System.err.println("method: " + method);
				switch (method) {
				case "TOKEN_AUTH_OK":
					System.out.println("Authenticated!");
					authenticated = true;
					BOT_ID = String.valueOf(obj.get(KEY_ID));
					System.err.println("====> Your Bot Id is : " + BOT_ID);
					System.err.println("====> Your Bot Name is : " + (String) obj.get(KEY_NAME));
					if (pingThread != null) {
						try {
							pingThread.interrupt();
						} catch (Exception e) {
							System.err.println(e);
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
				case "permanentUrl":
					PermanentUrl permenantURL = new PermanentUrl(obj);
					callback.permanentUrl(permenantURL);
					return;
				default:
					callback.onReceive(obj);
					return;
				}
			} else {
				String error = String.valueOf(obj.get(KEY_ERROR));
				System.err.println("Error : " + error);
			}
		}

		@OnWebSocketError
		public void onError(Session session, Throwable cause) {
			System.err.println("INTERNAL: ONERROR");
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			if (cause != null) {
				System.err.println("Error due to : " + cause.getMessage() + " On : " + dateFormat.format(date));
				callback.onError();

				if (cause instanceof ConnectException && timeOutCounter < NO_OF_RETRIES_IF_CONN_TIMEDOUT) {

					try {

						System.out.println(cause.getMessage() + " , Please wait 10 seconds for Reconnecting ");
						stopWebSocketClient();
						TimeUnit.SECONDS.sleep(10);
						timeOutCounter++;
						System.out.println("Connection Time out count is : " + timeOutCounter);
						reconnectWebSocketClient();
					} catch (Exception e1) {
						e1.printStackTrace();
						Thread.currentThread().interrupt();
					}

				} else if (cause instanceof SocketTimeoutException
						&& connRefusedCounter < NO_OF_RETRIES_IF_CONN_TO_SERVER_REFUSED) {
					try {

						System.out.println(cause.getMessage() + ", Please wait 30 seconds for Reconnecting ");
						stopWebSocketClient();
						TimeUnit.SECONDS.sleep(30);
						connRefusedCounter++;
						System.out.println("Connection Refused Counter " + connRefusedCounter);
						reconnectWebSocketClient();
					} catch (Exception e1) {
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

	public static String getBotId()
	{
		return BOT_ID;
	}
}
