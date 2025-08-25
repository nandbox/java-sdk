package com.nandbox.bots.api.test;

import java.util.List;
import java.util.Random;

import com.nandbox.bots.api.Nandbox;
import com.nandbox.bots.api.NandboxClient;
import com.nandbox.bots.api.data.*;
import com.nandbox.bots.api.inmessages.*;
import com.nandbox.bots.api.util.Utils;
import net.minidev.json.JSONObject;

/**
 * Test Class
 * 
 * @author Hossam Mohamed
 *
 */
public class SendWebLinksBot {
	public static final String TOKEN = "<PUT your token here >";
	private static final Integer CHAT_SETTINGS = 1;
	private static final Integer FROM_ADMIN = 1;
	private static final String TEXT = "text";
	private static final String CONTACT = "Contact";

	public static void main(String[] args) throws Exception {
		NandboxClient client = NandboxClient.get();
		client.connect(TOKEN, new Nandbox.Callback() {
			@Override
			public void onWhiteListPattern(Pattern pattern) {

			}

			@Override
			public void onBlackListPattern(Pattern pattern) {

			}

			@Override
			public void onDeleteBlackList(List_ak blackList) {

			}

			Nandbox.Api api = null;

			@Override
			public void onConnect(Nandbox.Api api) {
				System.out.println("ONCONNECT");
				this.api = api;
			}

			@Override
			public void onReceive(JSONObject obj) {

			}

			@Override
			public void onClose() {
				System.out.println("ONCLOSE");
			}

			@Override
			public void onError() {
				System.out.println("ONERROR");

			}

			@Override
			public void onReceive(IncomingMessage incomingMsg) {

				try {
					if (incomingMsg.getChat().getType().equals(CONTACT)
							|| (CHAT_SETTINGS == incomingMsg.getChatSettings()
									&& FROM_ADMIN == incomingMsg.isFromAdmin())) {
						String chatId = incomingMsg.getChat().getId();
						String incomingText = incomingMsg.getText();
						if (TEXT.equals(incomingMsg.getType())) {

							if (!incomingText.contains("&") || !incomingText.contains("=")) {

								api.sendText(chatId,
										"Invalid command , command format : webview=<WEB_VIEW_FLAG from 1 to 4>&link=<ANY WEB LINK>",incomingMsg.getAppId());

							} else {
								String[] data = incomingText.split("&");
								int msgsCount = Integer.valueOf(data[0].split("=")[1]);
								String format = data[1].split("=")[1];
								if ("larg".equalsIgnoreCase(format))
								{
									for (int i = 0; i < msgsCount; i++) {
										api.sendText(chatId, String.valueOf(i+1), Utils.getUniqueId(), null, null, null, null, null, getRendomColorString(), null,null,incomingMsg.getAppId());
										Thread.sleep(500);
									}
								}
								else
								{
									for (int i = 0; i < msgsCount; i++) {
										api.sendText(chatId, String.valueOf(i+1), Utils.getUniqueId(), null, null, null, null, null, null, null,null,incomingMsg.getAppId());
									}
								}
								

							}

						}
					}
				} catch (Exception excp) {
					excp.printStackTrace();
					api.sendText(incomingMsg.getChat().getId(),
							"Invalid command , command format : webview=<WEB_VIEW_FLAG from 1 to 4>&link=<ANY WEB LINK>",incomingMsg.getAppId());
				}
			}

			@Override
			public void onChatMenuCallBack(ChatMenuCallback chatMenuCallback) {

				System.out.println("ChatMenuCB : " + chatMenuCallback.toJsonObject());

				String chatId = chatMenuCallback.getChat().getId();

				if (chatMenuCallback.getButtonCallback().equals("optionCB")) {
					api.sendText(chatId, "Coming soon",chatMenuCallback.getAppId());
				}

				if (chatMenuCallback.getButtonCallback().equals("mainCB")) {
					api.sendText(chatId, "Main menu",chatMenuCallback.getAppId());
				}

				if (chatMenuCallback.getButtonCallback().equals("funnyCB")) {
					api.sendText(chatId, "Why are frogs always so happy? They eat what ever bugs them",chatMenuCallback.getAppId());
				}

			}

			@Override
			public void onChatMember(ChatMember chatMember) {
				System.out.println("getType >>" + chatMember.getType());
				System.out.println("getStatus >>" + chatMember.getStatus());
				System.out.println("getChat().getId() >>" + chatMember.getChat().getId());
				System.out.println("getUser().getId() >>" + chatMember.getUser().getId());
				System.out.println("getMemberSince >>" + chatMember.getMemberSince());
			}

			@Override
			public void onMyProfile(User myprofile) {
				System.out.println("getUser id " + myprofile.getId());
				System.out.println("getName " + myprofile.getName());
				System.out.println("getTerminal " + myprofile.getTerminal());
				System.out.println("getProfile " + myprofile.getProfile());
				if (myprofile.getPhoto() != null) {
					System.out.println("Photo().getId " + myprofile.getPhoto().getId());
				}

			}

			@Override
			public void onProductDetail(ProductItemResponse productItem) {

			}

			@Override
			public void onCollectionProduct(GetProductCollectionResponse collectionProduct) {

			}


			@Override
			public void listCollectionItemResponse(ListCollectionItemResponse collections) {

			}


			@Override
			public void onChatAdministrators(ChatAdministrators chatAdministrators) {
				System.out.println("ChatAdministrators " + chatAdministrators.getChat().getId());

				for (int i = 0; i < chatAdministrators.getAdministrators().length; i++) {

					System.out.println("admin user id number" + (1 + i) + " >>>"
							+ chatAdministrators.getAdministrators()[i].getId());

				}
			}

			@Override
			public void onUserDetails(User user,String appId) {

				System.out.println("USER ID = " + user.getId());
			}

			@Override
			public void onUserJoinedBot(User user) {

				System.out.println("User ID =" + user.getId());

			}

			@Override
			public void userStartedBot(User user) {

				System.out.println("User ID = " + user.getId());

			}

			@Override
			public void userStoppedBot(User user) {

				System.out.println("User ID =" + user.getId());

			}

			@Override
			public void userLeftBot(User user) {
				System.out.println("User ID =" + user.getId());

			}

			@Override
			public void onInlineMessageCallback(InlineMessageCallback inlineMsgCallback) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onMessagAckCallback(MessageAck msgAck) {
				// TODO Auto-generated method stub

			}

			@Override
			public void permanentUrl(PermanentUrl permenantUrl) {
				
			}

			@Override
			public void onChatDetails(Chat chat,String appId) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onInlineSearh(InlineSearch inlineSearch) {
				// TODO Auto-generated method stub
				
			}
			
			private String getRendomColorString()
			{
				Random random = new Random();
		        // create a big random number - maximum is ffffff (hex) = 16777215 (dez)
		        int nextInt = random.nextInt(0xffffff + 1);

		        // format it as hexadecimal string (with hashtag and leading zeros)
		       return String.format("#%06x", nextInt);

			}

			@Override
			public void onBlackList(BlackList blackList) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onWhiteList(WhiteList blackList) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onDeleteWhiteList(List_ak whiteList) {

			}

			@Override
			public void onScheduleMessage(IncomingMessage incomingScheduleMsg) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onWorkflowDetails(WorkflowDetails workflowDetails) {

			}

			/**
			 * @param chat
			 */
			@Override
			public void onCreateChat(Chat chat) {

			}

			@Override
			public void onMenuCallBack(MenuCallback menuCallback) {

			}

		});

	}

}
