package com.nandbox.bots.api.outmessages;

import net.minidev.json.JSONObject;

public class CreateChatOutMessage extends OutMessage {

    private static final String KEY_CHAT = "chat";
    private static final String KEY_TYPE = "type";
    private static final String KEY_TITLE = "title";
    private static final String KEY_IS_PUBLIC = "isPublic";
    private static final String KEY_TIMEZONE = "timezone";

    private String type;
    private String title;
    private Integer isPublic;

    public CreateChatOutMessage() {
        this.method = OutMessageMethod.createChat;
    }

    public void setType(String type) {
        this.type=type;
    }
    public void setTitle(String title) { this.title=title; }
    public void setIsPublic(int isPublic) { this.isPublic=isPublic; }

    @Override
    public JSONObject toJsonObject(){
        // reference is held and serialized by OutMessage; this class no longer
        // shadows it, so the inherited getReference() reports the real value.
        JSONObject obj = super.toJsonObject();
        JSONObject chat = new JSONObject();
        if ("Group".equals(type)) {
            chat.put(KEY_TYPE,"Group");
            chat.put(KEY_IS_PUBLIC,isPublic);
            chat.put(KEY_TIMEZONE,"Africa/Cairo");
            chat.put(KEY_TITLE,title);
        }

        obj.put(KEY_CHAT,chat);
        return obj;
    }
}
