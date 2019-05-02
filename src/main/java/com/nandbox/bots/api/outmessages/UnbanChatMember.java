package com.nandbox.bots.api.outmessages;

import net.minidev.json.JSONObject;

public class UnbanChatMember extends OutMessage {

	protected static final String KEY_USER_ID = "user_id";

	private String userId;

	public UnbanChatMember() {
		this.method = OutMessageMethod.unbanChatMember;
	}

	@Override
	public JSONObject toJsonObject() {
		JSONObject obj = super.toJsonObject();
		if (chatId != null) {
			obj.put(KEY_CHAT_ID, chatId);
		}

		if (userId != null) {
			obj.put(KEY_USER_ID, userId);
		}
		return obj;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
