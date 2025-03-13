/**
 * 
 */
package com.nandbox.bots.api.inmessages;

import com.nandbox.bots.api.util.Utils;

import net.minidev.json.JSONObject;

/**
 * it represents Message Ack Object , the message Object is representing Server
 * Messages with method : message_ack
 * 
 * @author Ahmed A. El-Malatawy
 *
 */
public class MessageAck {

	private static final String KEY_ACK = "ack";
	private static final String KEY_MESSAGE_ID = "message_id";
	private static final String KEY_DATE = "date";
	private static final String KEY_REFERENCE = "reference";

	private String messageId;
	private Long date;
	private String reference;

	public MessageAck(JSONObject jsonObj) {

		JSONObject obj = (JSONObject) jsonObj.get(KEY_ACK);

		this.messageId = String.valueOf(obj.get(KEY_MESSAGE_ID));
		this.reference = String.valueOf(obj.get(KEY_REFERENCE));
		this.date = Utils.getLong(obj.get(KEY_DATE));
	}

	/**
	 * @return JSON Object of Message
	 */
	public JSONObject toJsonObject() {

		JSONObject obj = new JSONObject();

		if (date != null) {
			obj.put(KEY_DATE, date);
		}
		if (messageId != null) {

			obj.put(KEY_MESSAGE_ID, messageId);
		}
		if (reference != null)
			obj.put(KEY_REFERENCE, reference);

		//System.out.println("to " + obj.toJSONString());
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
	 * @return the date
	 */
	public Long getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Long date) {
		this.date = date;
	}

	/**
	 * @return the reference ( Unique local identifier for the target chat/user)
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * @param reference
	 *            the reference to set ( Unique local identifier for the target
	 *            chat/user)
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}
}
