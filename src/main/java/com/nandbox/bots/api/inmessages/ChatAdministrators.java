package com.nandbox.bots.api.inmessages;

import com.nandbox.bots.api.data.Chat;
import com.nandbox.bots.api.data.User;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public class ChatAdministrators {

	private static final String KEY_CHAT_ADMINISTRATORS = "chatAdministrators";
	private static final String KEY_ADMINISTRATORS = "administrators";
	private static final String KEY_CHAT = "chat";
	private static final String KEY_APP_ID = "app_id";

	private User[] administrators;
	private Chat chat;
	private String appId;
	public ChatAdministrators(JSONObject jsonObj) {
		JSONObject obj = (JSONObject) jsonObj.get(KEY_CHAT_ADMINISTRATORS);
		this.chat = obj.get(KEY_CHAT) == null ? null : new Chat((JSONObject) obj.get(KEY_CHAT));
		this.appId =jsonObj.get(KEY_APP_ID) != null
				? String.valueOf(jsonObj.get(KEY_APP_ID))
				:"0";
		JSONArray adminArrayObj = (JSONArray) obj.get(KEY_ADMINISTRATORS);
		if (adminArrayObj != null) {

			User[] admin = new User[adminArrayObj.size()];
			for (int i = 0; i < adminArrayObj.size(); i++) {
				admin[i] = new User((JSONObject) adminArrayObj.get(i));
			}

			this.administrators = admin;
		}

	}

	public JSONObject toJsonObject() {

		JSONObject obj = new JSONObject();
		if (administrators != null) {
			JSONArray adminsArrayObjnew = new JSONArray();
			for (int i = 0; i < administrators.length; i++) {
				adminsArrayObjnew.add(administrators[i].toJsonObject());
			}
			obj.put(KEY_ADMINISTRATORS, adminsArrayObjnew);
		}
		if (appId!=null){
			obj.put(KEY_APP_ID,appId);
		}
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
	public String getAppId(){
		return appId;
	}
	/**
	 * @return the administrators
	 */
	public User[] getAdministrators() {
		return administrators;
	}

	/**
	 * @param administrators
	 *            the administrators to set
	 */
	public void setAdministrators(User[] administrators) {
		this.administrators = administrators;
	}
}
