package com.nandbox.bots.api.outmessages;

import com.nandbox.bots.api.data.Button;

import net.minidev.json.JSONObject;

/**
 * 
 * This class represents Output Message used to send Navigation Button
 * 
 * @author Hossam Mohamed
 *
 */
public class SetAdminNavigationButtonOutMessage extends OutMessage {

	protected static final String KEY_NAVIGATION_BUTTON = "navigation_button";

	private Button navigationButton;


	
	public SetAdminNavigationButtonOutMessage() {
		this.method = OutMessageMethod.setAdminNavigationButton;
	}
	@Override
	public JSONObject toJsonObject() {
		JSONObject obj = super.toJsonObject();
		if (navigationButton != null) {
			obj.put(KEY_NAVIGATION_BUTTON, navigationButton.toJsonObject());
		}
		return obj;
	}

	/**
	 * @return the navigationButton
	 */

	public Button getNavigationButton() {
		return navigationButton;
	}

	/**
	 * @param navigationButton
	 *            the navigationButton to set
	 */
	public void setNavigationButton(Button navigationButton) {
		this.navigationButton = navigationButton;
	}

}
