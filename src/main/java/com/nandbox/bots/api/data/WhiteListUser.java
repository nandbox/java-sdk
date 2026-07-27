package com.nandbox.bots.api.data;

import java.util.ArrayList;
import java.util.List;

import net.minidev.json.JSONObject;

public class WhiteListUser  {

	// private static final String KEY_Users = "id";
	// private static final String KEY_ID = "id";
	private static final String KEY_SIGNUP_USER = "signup_id";
	private static final String KEY_TAGS = "tags";

	private String signupUser;
	private ArrayList<String> tags = new ArrayList<>();

	public WhiteListUser() {
	}
	
	
	public WhiteListUser(JSONObject obj) {
		this.signupUser = (String) obj.get(KEY_SIGNUP_USER);
		// Copy element by element: the incoming JSONArray is an ArrayList<Object>,
		// so casting it straight to ArrayList<String> would allow non-String
		// elements through, and an absent key would null out the field.
		this.tags = new ArrayList<>();
		Object tagsValue = obj.get(KEY_TAGS);
		if (tagsValue instanceof List) {
			for (Object tag : (List<?>) tagsValue) {
				if (tag != null) {
					this.tags.add(String.valueOf(tag));
				}
			}
		}
	}

	public JSONObject toJsonObject() {
		JSONObject obj = new JSONObject();

		if (signupUser != null)
			obj.put(KEY_SIGNUP_USER, signupUser);

		if (tags != null && !tags.isEmpty()) {
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
