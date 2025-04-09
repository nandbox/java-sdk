package com.nandbox.bots.api.outmessages;


import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 * 
 * This class represents Output Message used to send Chat Menu
 * 
 * @author Hossam Mohamed
 *
 */
public class SetChatMenuOutMessage extends OutMessage {

	private static final String KEY_MENU = "menus";

	private JSONArray menus;

	public SetChatMenuOutMessage() {
		this.method = OutMessageMethod.setChatMenu;
	}

	@Override
	public JSONObject toJsonObject() {
		JSONObject obj = super.toJsonObject();
		if (menus != null) {
			obj.put(KEY_MENU, menus);
		}
		return obj;
	}

	/**
	 * @return the menus
	 */
	public JSONArray getMenus() {
		return menus;
	}

	/**
	 * @param menus
	 *            the menus to set
	 */
	public void setMenus(JSONArray menus) {
		this.menus = menus;
	}
}
