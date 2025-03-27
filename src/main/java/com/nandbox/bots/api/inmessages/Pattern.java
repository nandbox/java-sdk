package com.nandbox.bots.api.inmessages;

import com.nandbox.bots.api.data.Data;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Pattern {
    protected static final String KEY_DATA = "data";
    private static final String KEY_CHATID = "chat_id";
    private static final String KEY_APP_ID = "app_id";
    private static final String KEY_REFERENCE = "reference";
    private List<Data> data = new ArrayList<>();
    private Long appId;
    private Long chatId;
    private Long reference;
    public Pattern(JSONObject obj) {
        this.appId =obj.get(KEY_APP_ID) != null
                ? Long.parseLong(String.valueOf(obj.get(KEY_APP_ID)))
                : 0;
        this.chatId =obj.get(KEY_CHATID) != null
                ? Long.parseLong(String.valueOf(obj.get(KEY_CHATID)))
                : 0;
        JSONArray usersArrayObj = (JSONArray) obj.get(KEY_DATA);
        this.data =new ArrayList<>();
        for (int i = 0 ; i<usersArrayObj.size();i++){
            this.data.add(new Data((JSONObject) usersArrayObj.get(i)));
        }
        this.reference =obj.get(KEY_REFERENCE) != null
                ? Long.parseLong(String.valueOf(obj.get(KEY_REFERENCE)))
                : 0l;

    }
    public JSONObject toJson(){
        JSONObject obj = new JSONObject();
        if (data!=null){
            JSONArray arr = new JSONArray();
            for (int i = 0 ; i<data.size();i++){
                arr.add(data.get(i).toJsonObject());
            }
            obj.put(KEY_DATA,arr);
        }
        if (appId!=null){
            obj.put(KEY_APP_ID,appId);
        }
        if (reference!=null){
            obj.put(KEY_REFERENCE,reference);
        }
        if (chatId!=null){
            obj.put(KEY_CHATID,chatId);
        }
        return obj;
    }
}
