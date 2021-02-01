package com.nandbox.bots.api.outmessages;
import net.minidev.json.JSONObject;


public class CancelScheduledOutMessage extends OutMessage {
	
	protected static final String KEY_MESSAGE_ID = "message_id";
	
	private String messageId;
	
	public CancelScheduledOutMessage() {
		this.method = OutMessageMethod.cancelMessageSchedule;
	}
	
	public JSONObject toJsonObject() {
		JSONObject obj = super.toJsonObject();
		if (messageId != null) {
			obj.put(KEY_MESSAGE_ID, messageId);
		}
		return obj;
	}
	

	public String getMessageId() {
		return messageId;
	}


	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

}
