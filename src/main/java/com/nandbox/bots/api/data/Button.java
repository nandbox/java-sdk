package com.nandbox.bots.api.data;

import net.minidev.json.JSONObject;

/**
 * @author Ahmed A. El-Malatawy
 *
 */
// This is a Java class called "Button" that appears to be used for creating UI buttons.
public class Button {

	// These are constants used as keys for JSON objects.
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

	// These are private fields representing the properties of a button.
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

	// This is the default constructor for the Button class.
	public Button() {

	}

	// This is a constructor that takes a buttonLabel parameter.
	public Button(String buttonLable) {
		this.buttonLabel = buttonLable;
	}

	/**
	 * This is a constructor that takes a JSONObject parameter, which used to create a Button object from a JSON object.
	 * @param obj The JSON object to be used to compose a Button object.
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

	/**
	 * This method converts a Button object to a JSONObject.
	 * @return A JSONObject representing the Button object.
	 */
	public JSONObject toJsonObject() {
		JSONObject obj = new JSONObject();

		// If a field is not null, add it to the JSONObject.
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

		// If nextMenu is not null, add it to the JSONObject.
		if (nextMenu != null) {
			obj.put(KEY_NEXT_MENU, nextMenu);
		}

		// If chat is not null, add it to the JSONObject.
		if (chat != null) {
			obj.put(KEY_CHAT, chat);
		}

		// If navType is not null, add it to the JSONObject.
		if (navType != null) {
			obj.put(KEY_BUTTON_NAV_TYPE, navType);
		}

		return obj;

	}

	/**
	 * This method returns the label of the button.
	 * @return The label of the button.
	 */
	public Integer getButtonOrder() {
		return buttonOrder;
	}

	/**
	 * This method sets the label of the button.
	 * @param buttonLabel The new label of the button.
	 */
	public void setButtonOrder(Integer buttonOrder) {
		this.buttonOrder = buttonOrder;
	}

	/**
	 * This method returns the span associated with the button.
	 * @return The span associated with the button.
	 */
	public Integer getButtonSpan() {
		return buttonSpan;
	}

	/**
	 * This method sets the span associated with the button.
	 * @param buttonSpan The new span associated with the button.
	 */
	public void setButtonSpan(Integer buttonSpan) {
		this.buttonSpan = buttonSpan;
	}

	/**
	 * This method returns the text Color associated with the button.
	 * @return The text Color associated with the button.
	 */
	public String getButtonTextColor() {
		return buttonTextColor;
	}

	/**
	 * This method sets the text Color associated with the button.
	 * @param buttonTextColor The new text Color associated with the button.
	 */
	public void setButtonTextColor(String buttonTextColor) {
		this.buttonTextColor = buttonTextColor;
	}

	/**
	 * This method returns the background color of the button.
	 * @return The background color of the button.
	 */
	public String getButtonBgColor() {
		return buttonBgColor;
	}

	/**
	 * This method sets the background color of the button.
	 * @param buttonBgColor The new background color of the button.
	 */
	public void setButtonBgColor(String buttonBgColor) {
		this.buttonBgColor = buttonBgColor;
	}

	/**
	 * This method returns the callBack of the button.
	 * @return The callBack of the button.
	 */
	public String getButtonCallBack() {
		return buttonCallBack;
	}

	/**
	 * This method sets the callBack of the button.
	 * @param buttonCallBack The new callBack of the button.
	 */
	public void setButtonCallBack(String buttonCallBack) {
		this.buttonCallBack = buttonCallBack;
	}

	/**
	 * This method returns the label associated with the button.
	 * @return The label associated with the button.
	 */
	public String getButtonLabel() {
		return buttonLabel;
	}

	/**
	 * This method sets the label associated with the button.
	 * @param buttonLabel The new label associated with the button.
	 */
	public void setButtonLabel(String buttonLabel) {
		this.buttonLabel = buttonLabel;
	}

	/**
	 * This method returns the url of the button.
	 * @return The url of the button.
	 */
	public String getButtonURL() {
		return buttonURL;
	}

	/**
	 * This method sets the url of the button.
	 * @param buttonURL The url of the button.
	 */
	public void setButtonURL(String buttonURL) {
		this.buttonURL = buttonURL;
	}

	/**
	 * This method returns the query of the button.
	 * @return The query of the button.
	 */
	public String getButtonQuery() {
		return buttonQuery;
	}

	/**
	 * This method sets the query of the button.
	 * @param buttonQuery The query of the button.
	 */
	public void setButtonQuery(String buttonQuery) {
		this.buttonQuery = buttonQuery;
	}

	/**
	 * This method returns the next menu associated with the button.
	 * @return The next menu associated with the button.
	 */
	public String getNextMenu() {
		return nextMenu;
	}

	/**
	 * This method sets the next menu associated with the button.
	 * @param nextMenu The new next menu associated with the button.
	 */
	public void setNextMenu(String nextMenu) {
		this.nextMenu = nextMenu;
	}

	/**
	 * This method returns the chat associated with the button.
	 * @return The chat associated with the button.
	 */
	public String getChat() {
		return chat;
	}

	/**
	 * This method sets the chat associated with the button.
	 * @param chat The new chat associated with the button.
	 */
	public void setChat(String chat) {
		this.chat = chat;
	}

	/**
	 * This method returns the icon associated with the button.
	 * @return The icon associated with the button.
	 */
	public String getButtonIcon() {
		return buttonIcon;
	}

	/**
	 * This method sets the icon associated with the button.
	 * @param buttonIcon The new icon associated with the button.
	 */
	public void setButtonIcon(String buttonIcon) {
		this.buttonIcon = buttonIcon;
	}

	/**
	 * This method returns the background color of the icon associated with the button.
	 * @return The background color of the icon associated with the button.
	 */
	public String getButtonIconBgColor() {
		return buttonIconBgColor;
	}

	/**
	 * This method set the background color of the icon associated with the button.
	 * @param buttonIconBgColor
	 *
	 */
	public void setButtonIconBgColor(String buttonIconBgColor) {
		this.buttonIconBgColor = buttonIconBgColor;
	}

	/**
	 * This method returns the nav type.
	 * @return The nav type.
	 */
	public String getNavType() {
		return navType;
	}

	/**
	 * This method set the nav type.
	 * @param navType
	 */
	public void setNavType(String navType) {
		this.navType = navType;
	}
}
