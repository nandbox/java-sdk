package com.nandbox.bots.api.outmessages;

import net.minidev.json.JSONObject;

/**
 * This class represents Output Message used to Update Message
 * 
 * @author Hossam Mohamed
 *
 */
public class UpdateOutMessage extends OutMessage {

	private static final String KEY_MESSAGE_ID = "message_id";
	private static final String KEY_TEXT = "text";

	private String messageId;
	private String text;

	public UpdateOutMessage() {
		this.method = OutMessageMethod.updateMessage;
	}

	@Override
	public JSONObject toJsonObject() {
		JSONObject obj = super.toJsonObject();

		if (getMessageId() != null) {
			obj.put(KEY_MESSAGE_ID, getMessageId());
		}

		if (getText() != null) {
			obj.put(KEY_TEXT, getText());
		}

		if (getCaption() != null) {
			obj.put(KEY_CAPTION, getCaption());
		}

		if (getToUserId() != null) {
			obj.put(KEY_TO_USER_ID, getToUserId());
		}

		if (getChatId() != null) {
			obj.put(KEY_CHAT_ID, getChatId());
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
	 * @return the message
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setText(String text) {
		this.text = text;
	}

}