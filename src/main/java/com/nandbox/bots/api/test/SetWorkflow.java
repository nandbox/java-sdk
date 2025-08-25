package com.nandbox.bots.api.test;

import com.nandbox.bots.api.Nandbox;
import com.nandbox.bots.api.NandboxClient;
import com.nandbox.bots.api.data.*;
import com.nandbox.bots.api.inmessages.*;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SetWorkflow {
    public static final String TOKEN = "90091783834098773:0:o8P0KrCJWttBRHsOBTMiNTZQ184Z2l";
    public static void main(String[] args) throws Exception {
        NandboxClient client = NandboxClient.get();
        client.connect(TOKEN, new Nandbox.Callback() {
            Nandbox.Api api = null;

            @Override
            public void onConnect(Nandbox.Api api) {
                System.out.println("Authenticated");
                this.api = api;
            }

            @Override
            public void onReceive(IncomingMessage incomingMsg) {

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
                String userId = chatMenuCallback.getChat().getId();
                String screenId = chatMenuCallback.getMenuRef();
                String appId = chatMenuCallback.getAppId();
                String btnCallback = chatMenuCallback.getButtonCallback();
                System.out.println("APP ID:-" + appId + "\n" + "USER ID:-" + userId + "\n" + "SCREEN ID:-" + screenId + "\n" + "BUTTON CALLBACK:-" + btnCallback + "\n" );

                WorkflowCell cell = new WorkflowCell();
                cell.setLabel("FROM SDK YAY");
                cell.setSubLabel("java sdk yay");
                cell.setBgColor("#ff0000");
                cell.setLabelColor("#ffffff");
                cell.setSubLabelColor("#ffffff");
                cell.setCallBack("button10");
                cell.setCellId("button10");

                List<WorkflowCell> arr = new ArrayList<>();
                arr.add(cell);

                api.updateMenuCell(userId,screenId, appId,new JSONArray(),"123456789",false);
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
            public void onProductDetail(ProductItemResponse productItem) {

            }

            @Override
            public void onCollectionProduct(GetProductCollectionResponse collectionProduct) {

            }




            @Override
            public void listCollectionItemResponse(ListCollectionItemResponse collections) {

            }

            @Override
            public void onUserDetails(User user,String appId) {

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
            public void onChatDetails(Chat chat,String appId) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onInlineSearh(InlineSearch inlineSearch) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onBlackListPattern(Pattern pattern) {

            }

            @Override
            public void onWhiteListPattern(Pattern pattern) {

            }

            @Override
            public void onBlackList(BlackList blackList) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onDeleteBlackList(List_ak blackList) {

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

            }

            @Override
            public void onWorkflowDetails(WorkflowDetails workflowDetails) {

            }

			@Override
			public void onCreateChat(Chat chat) {
				// TODO Auto-generated method stub
				
			}

            @Override
            public void onMenuCallBack(MenuCallback menuCallback) {

            }


        });
    }
}
