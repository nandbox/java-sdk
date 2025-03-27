package com.nandbox.bots.api.inmessages;

import com.nandbox.bots.api.data.Chat;
import com.nandbox.bots.api.data.SignupUser;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public class BlackList {

	private static final String KEY_BLACKLIST = "blacklist";
	private static final String KEY_EOP = "eop";
	private static final String KEY_USERS = "signups";
	private static final String KEY_APP_ID = "app_id";
	private static final String KEY_REFERENCE = "reference";

	private Boolean eop;
	// private Users users;
	// private ArrayList <Users> arrayUser = new ArrayList<>();
	private SignupUser[] users;
	private Long appId;
	private Long reference;

	public BlackList(JSONObject obj) {


		this.eop = (Boolean) obj.get(KEY_EOP);

		this.appId =obj.get(KEY_APP_ID) != null
				? Long.parseLong(String.valueOf(obj.get(KEY_APP_ID)))
				: 0;;
		JSONArray usersArrayObj = (JSONArray) obj.get(KEY_USERS);
		this.users = new SignupUser[usersArrayObj.size()];
		for (int i = 0; i < usersArrayObj.size(); i++) {
			users[i] = new SignupUser((JSONObject) usersArrayObj.get(i));
		}
		this.reference =obj.get(KEY_REFERENCE) != null
				? Long.parseLong(String.valueOf(obj.get(KEY_REFERENCE)))
				: 0l;

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
		if (appId!=null){
			obj.put(KEY_APP_ID,appId);
		}

		if (eop != null) {
			obj.put(KEY_EOP, eop);
		}
		if (reference!=null){
			obj.put(KEY_REFERENCE,reference);
		}
		return obj;

	}

	public Boolean getEop() {
		return eop;
	}

	public void setEop(Boolean eop) {
		this.eop = eop;
	}

	public Long getAppId(){
		return appId;
	}

	public Long getReference() {
		return reference;
	}

	public void setReference(Long reference) {
		this.reference = reference;
	}
	public SignupUser[] getUsers() {
		return users;
	}

	public void setUsers(SignupUser[] users) {
		this.users = users;
	}

}
