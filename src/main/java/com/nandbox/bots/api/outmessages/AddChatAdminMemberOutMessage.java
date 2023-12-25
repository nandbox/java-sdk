package com.nandbox.bots.api.outmessages;

import net.minidev.json.JSONObject;

public class AddChatAdminMemberOutMessage extends OutMessage{
    private static final String KEY_CHAT_ID = "chat_id";
    protected static final String KEY_USER_ID = "user_id";

    private Long chatId;
    private Long userId;
    public AddChatAdminMemberOutMessage() {
        this.method = OutMessageMethod.addChatAdmin;
    }
    public void setChatId(long chatId) {
        this.chatId = chatId;
    }
    public void setUserId(long userId) { this.userId = userId; }

    @Override
    public JSONObject toJsonObject(){
        JSONObject obj = super.toJsonObject();
        if (chatId != null) {
            obj.put(KEY_CHAT_ID, chatId);
        }
        if (userId != null) {
            obj.put(KEY_USER_ID, userId);
        }
        return obj;
    }
}
