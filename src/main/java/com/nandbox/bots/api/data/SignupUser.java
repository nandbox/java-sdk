package com.nandbox.bots.api.data;

import net.minidev.json.JSONObject;

public class SignupUser {

	private static final String KEY_ID = "id";
	private static final String KEY_SIGNUP_USER = "signup_id";

	private String id;
	private String signupUser;

	public SignupUser(JSONObject obj) {
		this.id = (String) obj.get(KEY_ID);
		this.signupUser = (String) obj.get(KEY_SIGNUP_USER);

	}
	public SignupUser() {
	}

	public JSONObject toJsonObject() {
		JSONObject obj = new JSONObject();

		if (id != null)
			obj.put(KEY_ID, id);

		if (signupUser != null)
			obj.put(KEY_SIGNUP_USER, signupUser);

		return obj;

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSignupUser() {
		return signupUser;
	}

	public void setSignupUser(String signupUser) {
		this.signupUser = signupUser;
	}

}
