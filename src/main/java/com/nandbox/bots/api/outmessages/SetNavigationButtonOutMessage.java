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

	protected static final String KEY_NAVIGATION_BUTTONS = "navigation_buttons";

	private JSONArray navigationButtons;

	public SetNavigationButtonOutMessage() {
		this.method = OutMessageMethod.setNavigationButton;
	}

	@Override
	public JSONObject toJsonObject() {

		JSONObject obj = super.toJsonObject();
		if (navigationButtons != null) {

			obj.put(KEY_NAVIGATION_BUTTONS, navigationButtons);
		}
		return obj;
	}

	public JSONArray getNavigationButtons() {
		return navigationButtons;
	}

	public void setNavigationButtons(JSONArray navigationButtons) {
		this.navigationButtons = navigationButtons;
	}


}
