package com.nandbox.bots.api.outmessages;

import net.minidev.json.JSONObject;

public class RecallOutMessage extends OutMessage {

	protected static final String KEY_MESSAGE_ID = "message_id";
	protected static final String KEY_FROM_USER_ID = "from_user_id";

	private String messageId;
	private String fromUserId;

	public RecallOutMessage() {
		this.method = OutMessageMethod.recallMessage;
	}

	@Override
	public JSONObject toJsonObject() {
		JSONObject obj = super.toJsonObject();
		if (messageId != null) {
			obj.put(KEY_MESSAGE_ID, messageId);
		}
		if (fromUserId != null) {
			obj.put(KEY_FROM_USER_ID, fromUserId);
		}
		return obj;
	}

	/**
	 * @return the messageId
	 */
	public String getMessageId() {
		return messageId;
	}

	/**
	 * @param messageId
	 *            the messageId to set
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	/**
	 * @return the fromUserId
	 */
	public String getFromUserId() {
		return fromUserId;
	}

	/**
	 * @param fromUserId
	 *            the fromUserId to set
	 */
	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

}
