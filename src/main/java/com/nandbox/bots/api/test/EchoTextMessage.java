package com.nandbox.bots.api.test;

import com.nandbox.bots.api.Nandbox;
import com.nandbox.bots.api.Nandbox.Api;
import com.nandbox.bots.api.NandboxClient;
import com.nandbox.bots.api.data.*;
import com.nandbox.bots.api.inmessages.*;

import net.minidev.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EchoTextMessage {

	public static final String TOKEN = "90091783822039252:0:DMDzxIveNFaGbI48D4I14NqGdx5WcH";
	//90090684265505180 => channel Helloooo
	//90090684293000559 => main app
	//90089584801743482 => basyone (user)
	//90089584778203966 => hesham22yy (user)
	//90089584759479548 => hamed (user)

	public static void main(String[] args) throws Exception {
		NandboxClient client = NandboxClient.get();
		client.connect(TOKEN, new Nandbox.Callback() {
			Nandbox.Api api = null;
			@Override
			public void onConnect(Api api) {
				System.out.println("Authenticated");
				this.api = api;

			}

			@Override
			public void onReceive(IncomingMessage incomingMsg) {
				if (incomingMsg.isTextMsg()) {
					String chatId = incomingMsg.getChat().getId(); // get your chat Id
					String text = incomingMsg.getText(); // get your text message
					api.sendText(chatId, text,incomingMsg.getAppId());
				}
			}

			@Override
			public void onReceive(JSONObject obj) {
				System.out.println(obj.toJSONString());
			}

			@Override
			public void onClose() {

			}

			@Override
			public void onError() {

			}

			@Override
			public void onChatMenuCallBack(ChatMenuCallback chatMenuCallback) {

			}

			@Override
			public void onMessagAckCallback(MessageAck msgAck) {

			}

			@Override
			public void onUserJoinedBot(User user) {

			}

			@Override
			public void onChatMember(ChatMember chatMember) {
				System.out.println(chatMember.toJsonObject());
			}

			@Override
			public void onChatAdministrators(ChatAdministrators chatAdministrators) {
				System.out.println(chatAdministrators.toJsonObject());
			}

			@Override
			public void userStartedBot(User user) {

			}

			@Override
			public void onMyProfile(User user) {
				System.out.println(user.toJsonObject());
			}

			@Override
			public void onProductDetail(ProductItem productItem) {
				System.out.println(productItem.toJsonObject());
			}

			@Override
			public void onCollectionProduct(List<CollectionProduct> collectionProduct) {

			}



			@Override
			public void listCollectionItemResponse(ListCollectionItemResponse collections) {
				System.out.println(collections.toJsonObject());
			}


			@Override
			public void onUserDetails(User user,Long appId) {
				System.out.println(user.toJsonObject());
			}

			@Override
			public void userStoppedBot(User user) {

			}

			@Override
			public void userLeftBot(User user) {

			}

			@Override
			public void onInlineMessageCallback(InlineMessageCallback inlineMsgCallback) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void permanentUrl(PermanentUrl permenantUrl) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onChatDetails(Chat chat,Long appId) {
				System.out.println(chat.toJsonObject());
			}

			@Override
			public void onInlineSearh(InlineSearch inlineSearch) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onBlackListPattern(Pattern blackListPattern) {
				System.out.println(blackListPattern.toJson());
			}

			@Override
			public void onWhiteListPattern(Pattern pattern) {
				System.out.println(pattern.toJson());
			}

			@Override
			public void onBlackList(BlackList blackList) {
				// TODO Auto-generated method stub\
				System.out.println(blackList.toJsonObject());
				
			}

			@Override
			public void onDeleteBlackList(WhiteList_ak blackList) {
				System.out.println(blackList.toJsonObject());
			}

			@Override
			public void onWhiteList(WhiteList whiteList) {
				// TODO Auto-generated method stub
				System.out.println(whiteList.toJsonObject());
				
			}

			@Override
			public void onDeleteWhiteList(WhiteList_ak whiteList) {
				System.out.println(whiteList.toJsonObject());
			}

			@Override
			public void onScheduleMessage(IncomingMessage incomingScheduleMsg) {
				
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


		});
	}
}