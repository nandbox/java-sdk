package com.nandbox.bots.api.inmessages;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.util.Arrays;

public class List_ak {

    private static final String KEY_WHITELIST = "whitelist";
    private static final String KEY_EOP = "eop";
    private static final String KEY_USERS = "signups";
    private static final String KEY_APP_ID = "app_id";
    private static final String KEY_REFERENCE = "reference";

    private Boolean eop;
    private String[] users;
    private Long appId;
    private Long reference;
    public List_ak(JSONObject jsonObj) {
        this.appId =jsonObj.get(KEY_APP_ID) != null
                ? Long.parseLong(String.valueOf(jsonObj.get(KEY_APP_ID)))
                : 0;
        this.eop = (Boolean) jsonObj.get(KEY_EOP);
        JSONArray usersArrayObj = (JSONArray) jsonObj.get(KEY_USERS);
        this.users = new String[usersArrayObj.size()];
        for (int i = 0; i < usersArrayObj.size(); i++) {
            users[i] =(String) usersArrayObj.get(i);
        }
        this.reference =jsonObj.get(KEY_REFERENCE) != null
                ? Long.parseLong(String.valueOf(jsonObj.get(KEY_REFERENCE)))
                : 0l;

    }

    public JSONObject toJsonObject() {

        JSONObject obj = new JSONObject();

        if (users != null) {

            JSONArray usersArrayObjnew = new JSONArray();
            usersArrayObjnew.addAll(Arrays.asList(users));
            obj.put(KEY_USERS, usersArrayObjnew);
        }


        if (eop != null) {
            obj.put(KEY_EOP, eop);
        }
        if (appId!=null){
            obj.put(KEY_APP_ID,appId);
        }
        if (reference!=null){
            obj.put(KEY_REFERENCE,reference);
        }
        return obj;

    }

    public Boolean getEop() {
        return eop;
    }

    public Long getReference() {
        return reference;
    }

    public void setReference(Long reference) {
        this.reference = reference;
    }

    public void setEop(Boolean eop) {
        this.eop = eop;
    }


    public String[] getUsers() {
        return users;
    }
    public Long getAppId(){
        return appId;
    }

    public void setUsers(String[] users) {
        this.users = users;
    }

}
