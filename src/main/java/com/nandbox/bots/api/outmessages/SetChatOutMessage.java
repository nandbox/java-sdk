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

		// Serialize through Chat.toJsonObject() so the protocol's snake_case keys
		// are used. Putting the POJO directly makes json-smart derive keys from the
		// getter names instead (memberCount, languageCode, inviteLink, ...).
		if (chat != null) {
			obj.put(KEY_CHAT, chat.toJsonObject());
		}
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
