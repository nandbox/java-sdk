package com.nandbox.bots.api.inmessages;

import com.nandbox.bots.api.data.Chat;
import com.nandbox.bots.api.data.Photo;

import net.minidev.json.JSONObject;

public class ChatDetails {
	private static final String KEY_CHAT = "chat";

	private Chat chat;

	public ChatDetails(JSONObject obj) {

		this.chat = new Chat((JSONObject) obj.get(KEY_CHAT));

	}

	public JSONObject toJsonObject() {

		JSONObject obj = new JSONObject();

		if (chat != null) {
			obj.put(KEY_CHAT, chat.toJsonObject());
		}

		return obj;

	}

	/**
	 * @return the Chat Id
	 */
	public String getChatId() {
		return chat.getId();
	}

	/**
	 * @param id
	 *            the Chat Id to set
	 */
	public void setChatId(String id) {
		this.chat.setId(id);
	}

	/**
	 * @return the Chat name
	 */
	public String getChatName() {
		return chat.getName();
	}

	/**
	 * @param name
	 *            the Chat name to set
	 */
	public void setChatName(String name) {
		this.chat.setName(name);
	}

	/**
	 * @return the chat version
	 */
	public String getChatVersion() {
		return chat.getVersion();
	}

	/**
	 * @param version
	 *            the chat version to set
	 */
	public void setChatVersion(String version) {
		this.chat.setVersion(version);
	}

	/**
	 * @return the chat photo
	 */
	public Photo getChatPhoto() {
		return chat.getPhoto();
	}

	/**
	 * @param photo
	 *            the user profile photo to set
	 */
	public void setChatPhoto(Photo photo) {
		this.chat.setPhoto(photo);
	}

	/**
	 * @return the chat
	 */
	public Chat getChat() {
		return chat;
	}

	/**
	 * @param chat the chat to set
	 */
	public void setChat(Chat chat) {
		this.chat = chat;
	}
}
