package com.nandbox.bots.api.test;
import com.nandbox.bots.api.Nandbox;
import com.nandbox.bots.api.NandboxClient;
import com.nandbox.bots.api.data.Chat;
import com.nandbox.bots.api.data.User;
import com.nandbox.bots.api.inmessages.*;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

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
                JSONObject callBackData = chatMenuCallback.toJsonObject();
                String userId = (String)((JSONObject) callBackData.get("chat")).get("id");
                System.out.println(userId);
                String screenId = ((String) callBackData.get("menu_ref"));
                System.out.println(screenId);
                String cellId =((String) callBackData.get("button_callback"));
                System.out.println(cellId);
                JSONArray button_data =((JSONArray)callBackData.get("button_data"));
                JSONObject addJsonObject = new JSONObject();
                Random random = new Random();
                int randNumber = random.nextInt(1000);
                addJsonObject.put("button_callback",null);
                addJsonObject.put("button_id","buttond53jhdqvc");
                addJsonObject.put("button_value",randNumber + "testing new feature");
                addJsonObject.put("button_description",randNumber + "this is a description for the new feature");
                addJsonObject.put("button_db",1);
                button_data.add(addJsonObject);
                System.out.println(button_data);
                api.sendCellText(userId,screenId,cellId,button_data.toString(), 99555555220L);
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
        });


    }

    private static String getToken() throws IOException {
        Properties prop = new Properties();

        InputStream input = new FileInputStream("config.properties");
        prop.load(input);
        return prop.getProperty("Token");
    }
}