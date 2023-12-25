package com.nandbox.bots.api.test;
import com.nandbox.bots.api.Nandbox;
import com.nandbox.bots.api.NandboxClient;
import com.nandbox.bots.api.data.Chat;
import com.nandbox.bots.api.data.User;
import com.nandbox.bots.api.inmessages.*;
import net.minidev.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {
    public static String TOKEN;

    public static void main(String[] args) throws Exception {

        NandboxClient client = NandboxClient.get();
        TOKEN = getToken();
        client.connect(TOKEN, new Nandbox.Callback() {
            Nandbox.Api api = null;
            @Override
            public void onConnect(Nandbox.Api api) {
                System.out.println("Connected Successfully");
                this.api=api;
            }

            @Override
            public void onReceive(IncomingMessage incomingMessage) {
                System.out.println("Inside OnReceive fn");
                System.out.println(incomingMessage);
            }

            @Override
            public void onReceive(JSONObject jsonObject) {
                System.out.println("Inside OnReceiveJsonObject fn");
            }

            @Override
            public void onClose() {

            }

            @Override
            public void onError() {

            }

            @Override
            public void onChatMenuCallBack(ChatMenuCallback chatMenuCallback) {
                System.out.println("Inside onChatMenuCallBack fn");
                System.out.println(chatMenuCallback.getButtonData());
                String chatId = chatMenuCallback.getChat().getId(); // get your chat Id
//                String text = chatMenuCallback.getText(); // get your text message
                api.sendText(chatId, chatMenuCallback.getButtonData().toJSONString()); // Sending message back as an Echo
            }

            @Override
            public void onInlineMessageCallback(InlineMessageCallback inlineMessageCallback) {

            }

            @Override
            public void onMessagAckCallback(MessageAck messageAck) {

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
            public void permanentUrl(PermanentUrl permanentUrl) {

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
            public void onScheduleMessage(IncomingMessage incomingMessage) {

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

    private static String getToken() throws IOException {
        Properties prop = new Properties();

        InputStream input = new FileInputStream("config.properties");
        prop.load(input);
        return prop.getProperty("Token");
    }
}