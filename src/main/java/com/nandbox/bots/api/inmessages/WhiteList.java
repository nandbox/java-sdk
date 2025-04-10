package com.nandbox.bots.api.inmessages;

import com.nandbox.bots.api.data.Chat;
import com.nandbox.bots.api.data.SignupUser;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public class WhiteList {

	private static final String KEY_WHITELIST = "whitelist";
	private static final String KEY_EOP = "eop";
	private static final String KEY_USERS = "signups";
	private static final String KEY_APP_ID = "app_id";
	private static final String KEY_REFERENCE = "reference";

	private Boolean eop;
	private SignupUser[] users;
	private String appId;
	private String reference;
	public WhiteList(JSONObject jsonObj) {
		this.appId =jsonObj.get(KEY_APP_ID) != null
				?String.valueOf(jsonObj.get(KEY_APP_ID))
				: "0";
		this.eop = (Boolean) jsonObj.get(KEY_EOP);
		JSONArray usersArrayObj = (JSONArray) jsonObj.get(KEY_USERS);
		this.users = new SignupUser[usersArrayObj.size()];
		for (int i = 0; i < usersArrayObj.size(); i++) {
			users[i] = new SignupUser((JSONObject) usersArrayObj.get(i));
		}
		this.reference =jsonObj.get(KEY_REFERENCE) != null
				? String.valueOf(jsonObj.get(KEY_REFERENCE))
				: "0";

	}

	public JSONObject toJsonObject() {

		JSONObject obj = new JSONObject();
		
		if (users != null) {

			JSONArray usersArrayObjnew = new JSONArray();
			for (int i = 0; i < users.length; i++) {
				usersArrayObjnew.add(users[i].toJsonObject());
			}
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

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public void setEop(Boolean eop) {
		this.eop = eop;
	}


	public SignupUser[] getUsers() {
		return users;
	}
	public String getAppId(){
		return appId;
	}

	public void setUsers(SignupUser[] users) {
		this.users = users;
	}

}
