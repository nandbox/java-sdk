package com.nandbox.bots.api.data;

import net.minidev.json.JSONObject;

/**
 * @author Ahmed A. El-Malatawy
 *
 */
public class Button {

	private static final String KEY_BUTTON_SPAN = "button_span";
	private static final String KEY_BUTTON_ORDER = "button_order";
	private static final String KEY_BUTTON_TEXT_COLOR = "button_textcolor";
	private static final String KEY_BUTTON_BG_COLOR = "button_bgcolor";
	private static final String KEY_BUTTON_CALLBACK = "button_callback";
	private static final String KEY_BUTTON_LABEL = "button_label";
	private static final String KEY_BUTTON_URL = "button_url";
	private static final String KEY_BUTTON_QUERY = "button_query";
	private static final String KEY_NEXT_MENU = "next_menu";
	private static final String KEY_CHAT = "chat";
	private static final String KEY_BUTTON_ICON = "button_icon";
	private static final String KEY_BUTTON_ICON_BG_COLOR = "button_icon_bgcolor";
	public static final String BUTTON_QUERY_LOCATION = "location";
	public static final String BUTTON_QUERY_CONTACT = "contact";
	public static final String KEY_BUTTON_NAV_TYPE = "nav_type";

	private Integer buttonSpan;
	private Integer buttonOrder;
	private String buttonTextColor;
	private String buttonBgColor;
	private String buttonCallBack;
	private String buttonLabel;
	private String buttonURL;
	private String buttonQuery;
	private String nextMenu;
	private String chat;
	private String buttonIcon;
	private String buttonIconBgColor;
	private String navType;

	public Button() {

	}

	public Button(String buttonLable) {
		this.buttonLabel = buttonLable;
	}

	/**
	 * @param obj
	 *            json object to be used to compose User object
	 */
	public Button(JSONObject obj) {
		this.buttonOrder = (Integer) obj.get(KEY_BUTTON_ORDER);
		this.buttonSpan = (Integer) obj.get(KEY_BUTTON_SPAN);
		this.buttonTextColor = (String) obj.get(KEY_BUTTON_TEXT_COLOR);
		this.buttonBgColor = (String) obj.get(KEY_BUTTON_BG_COLOR);
		this.buttonCallBack = (String) obj.get(KEY_BUTTON_CALLBACK);
		this.buttonLabel = (String) obj.get(KEY_BUTTON_LABEL);
		this.buttonURL = (String) obj.get(KEY_BUTTON_URL);
		this.buttonQuery = (String) obj.get(KEY_BUTTON_QUERY);
		this.nextMenu = (String) obj.get(KEY_NEXT_MENU);
		this.chat = (String) obj.get(KEY_CHAT);
		this.buttonIcon = (String) obj.get(KEY_BUTTON_ICON);
		this.buttonIconBgColor = (String) obj.get(KEY_BUTTON_ICON_BG_COLOR);
		this.navType = (String) obj.get(KEY_BUTTON_NAV_TYPE);

	}

	public JSONObject toJsonObject() {
		JSONObject obj = new JSONObject();

		if (buttonOrder != null)
			obj.put(KEY_BUTTON_ORDER, buttonOrder);
		if (buttonSpan != null)
			obj.put(KEY_BUTTON_SPAN, buttonSpan);
		if (buttonLabel != null)
			obj.put(KEY_BUTTON_LABEL, buttonLabel);
		if (buttonCallBack != null)
			obj.put(KEY_BUTTON_CALLBACK, buttonCallBack);
		if (buttonURL != null)
			obj.put(KEY_BUTTON_URL, buttonURL);
		if (buttonBgColor != null)
			obj.put(KEY_BUTTON_BG_COLOR, buttonBgColor);
		if (buttonTextColor != null)
			obj.put(KEY_BUTTON_TEXT_COLOR, buttonTextColor);
		if (buttonIcon != null)
			obj.put(KEY_BUTTON_ICON, buttonIcon);
		if (buttonIconBgColor != null)
			obj.put(KEY_BUTTON_ICON_BG_COLOR, buttonIconBgColor);
		if (buttonQuery != null)
			obj.put(KEY_BUTTON_QUERY, buttonQuery);

		if (nextMenu != null) {
			obj.put(KEY_NEXT_MENU, nextMenu);
		}

		if (chat != null) {
			obj.put(KEY_CHAT, chat);
		}

		if (navType != null) {
			obj.put(KEY_BUTTON_NAV_TYPE, navType);
		}

		return obj;

	}

	/**
	 * @return the buttonOrder
	 */
	public Integer getButtonOrder() {
		return buttonOrder;
	}

	/**
	 * @param buttonOrder
	 *            the buttonOrder to set
	 */
	public void setButtonOrder(Integer buttonOrder) {
		this.buttonOrder = buttonOrder;
	}

	/**
	 * @return the buttonSpan
	 */
	public Integer getButtonSpan() {
		return buttonSpan;
	}

	/**
	 * @param buttonSpan
	 *            the buttonSpan to set
	 */
	public void setButtonSpan(Integer buttonSpan) {
		this.buttonSpan = buttonSpan;
	}

	/**
	 * @return the buttonTextColor
	 */
	public String getButtonTextColor() {
		return buttonTextColor;
	}

	/**
	 * @param buttonTextColor
	 *            the buttonTextColor to set
	 */
	public void setButtonTextColor(String buttonTextColor) {
		this.buttonTextColor = buttonTextColor;
	}

	/**
	 * @return the buttonBgColor
	 */
	public String getButtonBgColor() {
		return buttonBgColor;
	}

	/**
	 * @param buttonBgColor
	 *            the buttonBgColor to set
	 */
	public void setButtonBgColor(String buttonBgColor) {
		this.buttonBgColor = buttonBgColor;
	}

	/**
	 * @return the buttonCallBack
	 */
	public String getButtonCallBack() {
		return buttonCallBack;
	}

	/**
	 * @param buttonCallBack
	 *            the buttonCallBack to set
	 */
	public void setButtonCallBack(String buttonCallBack) {
		this.buttonCallBack = buttonCallBack;
	}

	/**
	 * @return the buttonLabel
	 */
	public String getButtonLabel() {
		return buttonLabel;
	}

	/**
	 * @param buttonLabel
	 *            the buttonLabel to set
	 */
	public void setButtonLabel(String buttonLabel) {
		this.buttonLabel = buttonLabel;
	}

	/**
	 * @return the buttonURL
	 */
	public String getButtonURL() {
		return buttonURL;
	}

	/**
	 * @param buttonURL
	 *            the buttonURL to set
	 */
	public void setButtonURL(String buttonURL) {
		this.buttonURL = buttonURL;
	}

	/**
	 * @return the buttonQuery
	 */
	public String getButtonQuery() {
		return buttonQuery;
	}

	/**
	 * @param buttonQuery
	 *            the buttonQuery to set
	 */
	public void setButtonQuery(String buttonQuery) {
		this.buttonQuery = buttonQuery;
	}

	/**
	 * @return the nextMenuRef
	 */
	public String getNextMenu() {
		return nextMenu;
	}

	/**
	 * @param nextMenu
	 *            the nextMenuRef to set
	 */
	public void setNextMenu(String nextMenu) {
		this.nextMenu = nextMenu;
	}

	/**
	 * @return the chat
	 */
	public String getChat() {
		return chat;
	}

	/**
	 * @param chat
	 *            the chat to set
	 */
	public void setChat(String chat) {
		this.chat = chat;
	}

	/**
	 * @return the buttonIcon
	 */
	public String getButtonIcon() {
		return buttonIcon;
	}

	/**
	 * @param buttonIcon
	 *            the buttonIcon to set
	 */
	public void setButtonIcon(String buttonIcon) {
		this.buttonIcon = buttonIcon;
	}

	/**
	 * @return the buttonIconBgColor
	 */
	public String getButtonIconBgColor() {
		return buttonIconBgColor;
	}

	/**
	 * @param buttonIconBgColor
	 *            the buttonIconBgColor to set
	 */
	public void setButtonIconBgColor(String buttonIconBgColor) {
		this.buttonIconBgColor = buttonIconBgColor;
	}

	public String getNavType() {
		return navType;
	}

	public void setNavType(String navType) {
		this.navType = navType;
	}
}
