package com.nandbox.bots.api.outmessages;


import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 * 
 * This class represents Output Message used to send Navigation Button
 * 
 * @author Hossam Mohamed
 *
 */
public class SetNavigationButtonOutMessage extends OutMessage {

	protected static final String KEY_NAVIGATION_BUTTONS = "navigation_button";

	private String navigation_button;

	public SetNavigationButtonOutMessage() {
		this.method = OutMessageMethod.setNavigationButton;
	}

	@Override
	public JSONObject toJsonObject() {

		JSONObject obj = super.toJsonObject();
		if (navigation_button != null) {

			obj.put(KEY_NAVIGATION_BUTTONS, navigation_button);
		}
		return obj;
	}

	public String getNavigation_button() {
		return navigation_button;
	}

	public void setNavigation_button(String navigation_button) {
		this.navigation_button = navigation_button;
	}


}
