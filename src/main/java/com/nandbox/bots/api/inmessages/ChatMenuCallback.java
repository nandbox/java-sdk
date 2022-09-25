package com.nandbox.bots.api.inmessages;

import com.nandbox.bots.api.util.Utils;
import com.nandbox.bots.api.data.ButtonQueryResult;
import com.nandbox.bots.api.data.Chat;
import com.nandbox.bots.api.data.User;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 * * it represents ChatMenuCallback Object , the message Object is representing
 * Server Messages with method : chatMenuCallback
 * 
 * @author Hossam Mohamed
 *
 */
public class ChatMenuCallback {

	private static final String KEY_CHAT_MENU_CALL_BACK = "chatMenuCallback";
	private static final String KEY_DATE = "date";
	private static final String KEY_NEXT_MENU = "next_menu";
	private static final String KEY_METHOD = "method";
	private static final String KEY_BUTTON_CALLBACK = "button_callback";
	private static final String KEY_BUTTON_QUERY_RESULTS = "button_query_result";
	private static final String KEY_CHAT = "chat";
	private static final String KEY_FROM = "from";
	private static final String KEY_MENU_REF = "menu_ref";
	private static final String KEY_BUTTON_DATA= "button_data";

	private Long date;
	private String nextMenu;
	private String method;
	private User from;
	private Chat chat;
	private String buttonCallback;
	private String menuRef;
	private JSONArray buttonData;
	ButtonQueryResult buttonQueryResult;

	public ChatMenuCallback(JSONObject jsonObj) {

		//System.out.println("json " + jsonObj.toJSONString());
		JSONObject obj = (JSONObject) jsonObj.get(KEY_CHAT_MENU_CALL_BACK);

		User fromUser = new User((JSONObject) obj.get(KEY_FROM));
		this.chat = obj.get(KEY_CHAT) == null ? null : new Chat((JSONObject) obj.get(KEY_CHAT));
		ButtonQueryResult btnqueryResults = obj.get(KEY_BUTTON_QUERY_RESULTS) == null ? null
				: new ButtonQueryResult((JSONObject) obj.get(KEY_BUTTON_QUERY_RESULTS));
		this.method = String.valueOf(obj.get(KEY_METHOD));
		this.menuRef = String.valueOf(obj.get(KEY_MENU_REF));
		this.from = fromUser;
		this.buttonQueryResult = btnqueryResults;
		this.buttonCallback = String.valueOf(obj.get(KEY_BUTTON_CALLBACK));
		this.nextMenu = String.valueOf(obj.get(KEY_NEXT_MENU));
		this.date = Utils.getLong(obj.get(KEY_DATE));
		this.buttonData = (JSONArray) obj.get(KEY_BUTTON_DATA);

	}

	/**
	 * @return JSON Object of Message
	 */
	public JSONObject toJsonObject() {

		JSONObject obj = new JSONObject();

		if (date != null) {
			obj.put(KEY_DATE, date);
		}

		if (from != null) {
			obj.put(KEY_FROM, from.toJsonObject());
		}
		if (chat != null) {
			obj.put(KEY_CHAT, chat.toJsonObject());
		}
		if (method != null) {

			obj.put(KEY_METHOD, method);
		}
		if (menuRef != null)
			obj.put(KEY_MENU_REF, menuRef);

		if (buttonCallback != null)
			obj.put(KEY_BUTTON_CALLBACK, buttonCallback);

		if (buttonQueryResult != null)
			obj.put(KEY_BUTTON_QUERY_RESULTS, buttonQueryResult);

		if (nextMenu != null)
			obj.put(KEY_NEXT_MENU, nextMenu);

		if (buttonData!=null)
			obj.put(KEY_BUTTON_DATA,buttonData);
		//System.out.println("to " + obj.toJSONString());
		return obj;

	}

	/**
	 * @return the date
	 */
	public Long getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Long date) {
		this.date = date;
	}

	/**
	 * @return the nextMenu
	 */
	public String getNextMenu() {
		return nextMenu;
	}

	/**
	 * @param nextMenu
	 *            the nextMenu to set
	 */
	public void setNextMenu(String nextMenu) {
		this.nextMenu = nextMenu;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method
	 *            the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * @return the from
	 */
	public User getFrom() {
		return from;
	}

	/**
	 * @param from
	 *            the from to set
	 */
	public void setFrom(User from) {
		this.from = from;
	}

	/**
	 * @return the chat
	 */
	public Chat getChat() {
		return chat;
	}

	/**
	 * @param chat
	 *            the chat to set
	 */
	public void setChat(Chat chat) {
		this.chat = chat;
	}

	/**
	 * @return the buttonCallback
	 */
	public String getButtonCallback() {
		return buttonCallback;
	}

	/**
	 * @param buttonCallback
	 *            the buttonCallback to set
	 */
	public void setButtonCallback(String buttonCallback) {
		this.buttonCallback = buttonCallback;
	}

	/**
	 * @return the menuRef
	 */
	public String getMenuRef() {
		return menuRef;
	}

	/**
	 * @param menuRef
	 *            the menuRef to set
	 */
	public void setMenuRef(String menuRef) {
		this.menuRef = menuRef;
	}

	/**
	 * @return the buttonQueryResult
	 */
	public ButtonQueryResult getButtonQueryResult() {
		return buttonQueryResult;
	}

	/**
	 * @param buttonQueryResult
	 *            the buttonQueryResult to set
	 */
	public void setButtonQueryResult(ButtonQueryResult buttonQueryResult) {
		this.buttonQueryResult = buttonQueryResult;
	}

	public JSONArray getButtonData() {
		return buttonData;
	}

}
