package com.nandbox.bots.api.outmessages;

import com.nandbox.bots.api.data.User;

import net.minidev.json.JSONObject;

/**
 * @author Hossam
 *
 */
public class SetMyProfileOutMessage extends OutMessage {
	private static final String KEY_USER = "user";

	private User user;

	public SetMyProfileOutMessage() {
		this.method = OutMessageMethod.setMyProfile;
	}

	@Override
	public JSONObject toJsonObject() {
		JSONObject obj = super.toJsonObject();

		obj.put(KEY_USER, user);
		return obj;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}


}
