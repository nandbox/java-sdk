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

	public static final String TOKEN = "90091783913582166:0:nssXmKkPvYiBG8VCvcz4yYfr9auCID";

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
					api.sendText(chatId, text); // Sending message back as an Echo
				}
			}

			@Override
			public void onReceive(JSONObject obj) {

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

			}

			@Override
			public void onChatAdministrators(ChatAdministrators chatAdministrators) {

			}

			@Override
			public void userStartedBot(User user) {

			}

			@Override
			public void onMyProfile(User user) {

			}

			@Override
			public void onProductItem(ProductItem productItem) {

			}

			@Override
			public void onCollectionProduct(CollectionProduct collectionProduct) {

			}

			@Override
			public void listCollectionItemResponse(List<Category> collections) {

			}


			@Override
			public void onUserDetails(User user) {

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
			public void onChatDetails(Chat chat) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onInlineSearh(InlineSearch inlineSearch) {
				// TODO Auto-generated method stub
				
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