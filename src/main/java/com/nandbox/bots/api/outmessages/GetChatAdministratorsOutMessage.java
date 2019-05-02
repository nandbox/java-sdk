package com.nandbox.bots.api.outmessages;

import net.minidev.json.JSONObject;

public class GetChatAdministratorsOutMessage extends OutMessage {

	private static final String KEY_CHAT_ID = "chat_id";

	public GetChatAdministratorsOutMessage() {
		this.method = OutMessageMethod.getChatAdministrators;
	}

	@Override
	public JSONObject toJsonObject() {
		JSONObject obj = super.toJsonObject();
		if (chatId != null) {
			obj.put(KEY_CHAT_ID, chatId);
		}
		return obj;
	}
}
