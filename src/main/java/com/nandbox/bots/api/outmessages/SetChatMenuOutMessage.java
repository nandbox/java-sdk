package com.nandbox.bots.api.outmessages;

import com.nandbox.bots.api.data.Menu;

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

	private Menu[] menus;

	public SetChatMenuOutMessage() {
		this.method = OutMessageMethod.setChatMenu;
	}

	@Override
	public JSONObject toJsonObject() {
		JSONObject obj = super.toJsonObject();
		if (menus != null) {
			JSONArray menusArrayObj = new JSONArray();
			for (int i = 0; i < menus.length; i++) {
				menusArrayObj.add(menus[i].toJsonObject());
			}

			obj.put(KEY_MENU, menusArrayObj);
		}
		return obj;
	}

	/**
	 * @return the menus
	 */
	public Menu[] getMenus() {
		return menus;
	}

	/**
	 * @param menus
	 *            the menus to set
	 */
	public void setMenus(Menu[] menus) {
		this.menus = menus;
	}
}
