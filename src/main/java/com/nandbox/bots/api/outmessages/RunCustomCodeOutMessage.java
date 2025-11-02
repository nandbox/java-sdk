package com.nandbox.bots.api.outmessages;

import net.minidev.json.JSONObject;

public class RunCustomCodeOutMessage extends OutMessage{
    private static final String KEY_USER_ID = "user_id";
    public static final String KEY_DATA="data";
    Long userId;
    JSONObject data;

    public RunCustomCodeOutMessage(){
        this.method = OutMessageMethod.runCustomCode;
    }
    public JSONObject toJsonObject() {
        JSONObject obj = super.toJsonObject();
        if (userId != null) {
            obj.put(KEY_USER_ID, userId);
        }
        if (data!=null){
            obj.put(KEY_DATA,data);
        }
        return obj;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public JSONObject getData() {
        return data;
    }

    public Long getUserId() {
        return userId;
    }
}

