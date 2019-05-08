package com.nandbox.bots.api.data;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public class Row {
	private static final String KEY_BUTTONS = "buttons";
	private static final String KEY_ROW_ORDER = "row_order";

	private Button[] buttons;
	private Integer rowOrder;

	public Row() {

	}
	
	public Row(Button[] buttons) {
		this.buttons = buttons;
	}
	
	public Row(Button button) {
		setButton(button);
	}

	public Row(JSONObject obj) {
		JSONArray buttonsArrayObj = (JSONArray) obj.get(KEY_BUTTONS);
		buttons = new Button[buttonsArrayObj.size()];
		for (int i = 0; i < buttonsArrayObj.size(); i++) {
			buttons[i] = new Button((JSONObject) buttonsArrayObj.get(i));
		}
		this.rowOrder = (Integer) obj.get(KEY_ROW_ORDER);
	}

	public JSONObject toJsonObject() {
		JSONObject obj = new JSONObject();

		if (rowOrder != null)
			obj.put(KEY_ROW_ORDER, rowOrder);

		if (buttons != null) {

			JSONArray buttonsArrayObjnew = new JSONArray();
			for (int i = 0; i < buttons.length; i++) {
				buttonsArrayObjnew.add(buttons[i].toJsonObject());
			}
			obj.put(KEY_BUTTONS, buttonsArrayObjnew);
		}

		return obj;

	}

	/**
	 * @return the buttons
	 */
	public Button[] getButtons() {
		return buttons;
	}

	/**
	 * @param buttons
	 *            the buttons to set
	 */
	public void setButtons(Button[] buttons) {
		this.buttons = buttons;
	}

	/**
	 * @return the rowOrder
	 */
	public Integer getRowOrder() {
		return rowOrder;
	}

	/**
	 * @param rowOrder
	 *            the rowOrder to set
	 */
	public void setRowOrder(Integer rowOrder) {
		this.rowOrder = rowOrder;
	}
	
	
	/**
	 * @param button
	 *            the buttons to set
	 */
	public void setButton(Button button) {
		this.buttons = new Button[] {button};
	}
}
