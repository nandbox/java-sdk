package com.nandbox.bots.api.test;

import com.nandbox.bots.api.Nandbox;
import com.nandbox.bots.api.Nandbox.Api;
import com.nandbox.bots.api.NandboxClient;
import com.nandbox.bots.api.data.Chat;
import com.nandbox.bots.api.data.User;
import com.nandbox.bots.api.inmessages.*;

import net.minidev.json.JSONObject;

public class TestCellMessage {

	public static final String TOKEN = "90091783898177134:0:ILUxIQfEK2bVrF4OxGnFU3JG8IwcrW";

	public static void main(String[] args) throws Exception {
		 
		   NandboxClient client = NandboxClient.get();
		   client.connect(TOKEN, new Nandbox.Callback() {
			   
		   Nandbox.Api api = null;
			   
			@Override
			public void onConnect(Api api) {
				System.out.println("ONCONNECT");
				this.api = api;
			}
	
			@Override
			public void onReceive(IncomingMessage incomingMsg) {
				if(incomingMsg.isTextMsg() && "send cell".equalsIgnoreCase(incomingMsg.getText())) {
					String userId = incomingMsg.getFrom().getId();
					String screenId = "menu1";
					String cellId = "buttonwsoeowkgf";
					long reference = 99555555220L;
					String text = "Testing Cells Feature";
					
					api.sendCellText(userId, screenId, cellId, text, reference);
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
			public void onInlineMessageCallback(InlineMessageCallback inlineMsgCallback) {
				
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
			public void onUserDetails(User user) {
				
			}
	
			@Override
			public void userStoppedBot(User user) {
				
			}
	
			@Override
			public void userLeftBot(User user) {
				
			}
	
			@Override
			public void permanentUrl(PermanentUrl permenantUrl) {
				
			}
	
			@Override
			public void onChatDetails(Chat chat) {
				
			}
	
			@Override
			public void onInlineSearh(InlineSearch inlineSearch) {
				
			}
	
			@Override
			public void onBlackList(BlackList blackList) {
				
			}
	
			@Override
			public void onWhiteList(WhiteList whiteList) {
				
			}
	
			@Override
			public void onScheduleMessage(IncomingMessage incomingScheduleMsg) {
				
			}

			   @Override
			   public void onWorkflowDetails(WorkflowDetails workflowDetails) {

			   }


		   });

	}
}
