package com.nandbox.bots.api.outmessages;

import com.nandbox.bots.api.data.Chat;

import net.minidev.json.JSONObject;

/**
 * @author Hossam
 *
 */
public class SetChatOutMessage extends OutMessage {

	private static final String KEY_CHAT = "chat";

	private Chat chat;

	public SetChatOutMessage() {

		this.method = OutMessageMethod.setChat;

	}

	@Override
	public JSONObject toJsonObject() {
		JSONObject obj = super.toJsonObject();

		obj.put(KEY_CHAT, chat);
		return obj;
	}

	/**
	 * @return the chat
	 */
	public Chat getChat() {
		return chat;
	}

	/**
	 * @param chat
	 *            the chat to set
	 */
	public void setChat(Chat chat) {
		this.chat = chat;
	}

}
