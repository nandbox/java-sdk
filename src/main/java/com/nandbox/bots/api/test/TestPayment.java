package com.nandbox.bots.api.test;

import com.nandbox.bots.api.Nandbox;
import com.nandbox.bots.api.Nandbox.Api;
import com.nandbox.bots.api.NandboxClient;
import com.nandbox.bots.api.data.*;
import com.nandbox.bots.api.inmessages.*;

import net.minidev.json.JSONObject;

public class TestPayment {

    public static final String TOKEN = "90091783786595892:0:hS94nKOJdehimpGeX7MjONJ1MQ9gSR";
    public static final String appId = "90090684312609408";

    public static void main(String[] args) throws Exception {
        NandboxClient client = NandboxClient.get();
        client.connect(TOKEN, new Nandbox.Callback() {
            Nandbox.Api api = null;
            @Override
            public void onConnect(Api api) {
                this.api = api;
            }

            @Override
            public void onReceive(IncomingMessage incomingMsg) {

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
            }

            @Override
            public void onChatAdministrators(ChatAdministrators chatAdministrators) {
            }

            @Override
            public void userStartedBot(User user) {

            }

            @Override
            public void onMyProfile(User user) {
                System.out.println(user.toJsonObject());
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

            }

            @Override
            public void permanentUrl(PermanentUrl permenantUrl) {

            }

            @Override
            public void onChatDetails(Chat chat,String appId) {
                System.out.println(chat.toJsonObject());
            }

            @Override
            public void onInlineSearh(InlineSearch inlineSearch) {

            }

            @Override
            public void onBlackListPattern(Pattern blackListPattern) {
                System.out.println(blackListPattern.toJson());
            }

            @Override
            public void onWhiteListPattern(Pattern pattern) {
            }

            @Override
            public void onBlackList(BlackList blackList) {


            }

            @Override
            public void onDeleteBlackList(List_ak blackList) {
            }

            @Override
            public void onWhiteList(WhiteList whiteList) {

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

            /**
             * @param chat
             */
            @Override
            public void onCreateChat(Chat chat) {

            }

            @Override
            public void onMenuCallBack(MenuCallback menuCallback) {

            }

            @Override
            public void onExtensionDocResponse(ExtensionDocResponse extensionDocResponse) {
            }

            @Override
            public void onPaymentAuthorizationRequest(PaymentRequest paymentRequest) {
                System.out.println(paymentRequest.toJson());
                String orderId = paymentRequest.getOrderId();
                String secret = paymentRequest.getSecret();
                double amount = paymentRequest.getAmount();
                String currency = paymentRequest.getCurrency();
                long accountId = paymentRequest.getAccountId();
                JSONObject payload = paymentRequest.getPaymentMethod();
                long debitAmountCents = paymentRequest.getDebitAmountCents();
                System.out.println(debitAmountCents);
                api.submitPaymentResult(appId,accountId,orderId,payload,secret,currency,amount,appId, NandboxClient.Status.Success,debitAmountCents);
            }


        });
    }
}