package com.nandbox.bots.api.outmessages.cell;

import com.nandbox.bots.api.outmessages.OutMessage.OutMessageMethod;

import net.minidev.json.JSONObject;

/**
 * @author ameer
 *
 */
public class TextCellOutMessage extends CellOutMessage {
	
	private static final String KEY_TEXT = "text";
	
	private String text;
	
	public TextCellOutMessage() {
		this.method = OutMessageMethod.sendCellMessage;
	}
	
	public JSONObject toJsonObject() {
		JSONObject obj = super.toJsonObject();
		
		if(text != null)
			obj.put(KEY_TEXT, userId);

		return obj;
	}
	

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

}
