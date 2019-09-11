package com.nandbox.bots.api.data;

import java.util.ArrayList;

import net.minidev.json.JSONObject;

public class WhiteListUser  {

	// private static final String KEY_Users = "id";
	// private static final String KEY_ID = "id";
	private static final String KEY_SIGNUP_USER = "signup_user";
	private static final String KEY_TAGS = "tags";

	private String signupUser;
	private ArrayList<String> tags = new ArrayList<>();

	public WhiteListUser() {
	}
	
	
	@SuppressWarnings("unchecked")
	public WhiteListUser(JSONObject obj) {
		this.signupUser = (String) obj.get(KEY_SIGNUP_USER);
		this.tags = (ArrayList<String>) obj.get(KEY_TAGS);

	}

	public JSONObject toJsonObject() {
		JSONObject obj = new JSONObject();

		if (signupUser != null)
			obj.put(KEY_SIGNUP_USER, signupUser);

		if (!tags.isEmpty()) {
			obj.put(KEY_TAGS, tags);
		}

		return obj;

	}

	public String getSignupUser() {
		return signupUser;
	}

	public void setSignupUser(String signupUser) {
		this.signupUser = signupUser;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

}
