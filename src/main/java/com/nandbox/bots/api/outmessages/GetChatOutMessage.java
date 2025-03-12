package com.nandbox.bots.api.outmessages;

import net.minidev.json.JSONObject;

public class GetChatOutMessage extends OutMessage {

	public GetChatOutMessage() {
		this.method = OutMessageMethod.getChat;
	}

	@Override
	public JSONObject toJsonObject() {
		JSONObject obj = super.toJsonObject();
		if (chatId != null) {
			obj.put(KEY_CHAT_ID, chatId);
		}
		System.out.println(obj.toJSONString());
		return obj;
	}

}
