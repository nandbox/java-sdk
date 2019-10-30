package com.nandbox.bots.api.outmessages;

import com.nandbox.bots.api.data.Button;

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

	private Button[] navigationButtons;

	public SetNavigationButtonOutMessage() {
		this.method = OutMessageMethod.setNavigationButton;
	}

	@Override
	public JSONObject toJsonObject() {

		JSONObject obj = super.toJsonObject();
		if (navigationButtons != null) {
			JSONArray navBtnArrayObj = new JSONArray();
			for (int i = 0; i < navigationButtons.length; i++) {
				navBtnArrayObj.add(navigationButtons[i].toJsonObject());
			}

			obj.put(KEY_NAVIGATION_BUTTONS, navBtnArrayObj);
		}
		return obj;
	}

	public Button[] getNavigationButtons() {
		return navigationButtons;
	}

	public void setNavigationButtons(Button[] navigationButtons) {
		this.navigationButtons = navigationButtons;
	}


}
