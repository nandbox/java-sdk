/**
 * 
 */
package com.nandbox.bots.api.outmessages;

import net.minidev.json.JSONObject;

/**
 * This class represents Output Message used to send Text Message .
 * 
 * @author Ahmed A. El-Malatawy
 *
 */
public class TextOutMessage extends OutMessage {

	private static final String KEY_TEXT = "text";
	protected static final String KEY_BG_COLOR = "bg_color";
	private String text;
	private String bgColor;

	public TextOutMessage() {
		this.method = OutMessageMethod.sendMessage;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	

	/**
	 * @return the bgColor
	 */
	public String getBgColor() {
		return bgColor;
	}

	/**
	 * @param bgColor the bgColor to set
	 */
	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	@Override
	public JSONObject toJsonObject() {
		JSONObject obj = super.toJsonObject();
		if (text != null) {
			obj.put(KEY_TEXT, text);
		}
		if (bgColor != null) {
			obj.put(KEY_BG_COLOR, bgColor);
		}
		return obj;
	}

}
