/**
 * 
 */
package com.nandbox.bots.api.inmessages;

import com.nandbox.bots.api.util.Utils;
import com.nandbox.bots.api.data.ButtonQueryResult;
import com.nandbox.bots.api.data.Chat;
import com.nandbox.bots.api.data.User;

import net.minidev.json.JSONObject;

/**
 * it represents inlineMessageCallback Object , the message Object is
 * representing Server Messages with method : inlineMessageCallback
 * 
 * @author Ahmed A. El-Malatawy
 *
 */
public class InlineMessageCallback {

	private static final String KEY_INLINE_MESSAGE_CALLBACK = "inlineMessageCallback";
	private static final String KEY_MESSAGE_ID = "message_id";
	private static final String KEY_MENU_REF = "menu_ref";	
	private static final String KEY_DATE = "date";
	private static final String KEY_FROM = "from";
	private static final String KEY_CHAT = "chat";
	private static final String KEY_REFERENCE = "reference";
	private static final String KEY_BUTTON_CALLBACK = "button_callback";
	private static final String KEY_BUTTON_QUERY_RESULTS = "button_query_result";

	private String messageId;
	private String menuRef;	
	private Long date;
	private String reference;
	private User from;
	private Chat chat;
	private String buttonCallback;
	ButtonQueryResult buttonQueryResult;

	public InlineMessageCallback(JSONObject jsonObj) {

		JSONObject obj = (JSONObject) jsonObj.get(KEY_INLINE_MESSAGE_CALLBACK);

		User fromUser = new User((JSONObject) obj.get(KEY_FROM));
		this.chat = obj.get(KEY_CHAT) == null ? null : new Chat((JSONObject) obj.get(KEY_CHAT));
		ButtonQueryResult btnqueryResults = obj.get(KEY_BUTTON_QUERY_RESULTS) == null ? null
				: new ButtonQueryResult((JSONObject) obj.get(KEY_BUTTON_QUERY_RESULTS));
		this.messageId = String.valueOf(obj.get(KEY_MESSAGE_ID));
		this.menuRef = (String)obj.get(KEY_MENU_REF);		
		this.reference = String.valueOf(obj.get(KEY_REFERENCE));
		this.from = fromUser;
		this.buttonQueryResult = btnqueryResults;
		this.buttonCallback = String.valueOf(obj.get(KEY_BUTTON_CALLBACK));

		this.date = Utils.getLong(obj.get(KEY_DATE));
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
		if (messageId != null) {

			obj.put(KEY_MESSAGE_ID, messageId);
		}
		
		if (menuRef != null) {

			obj.put(KEY_MENU_REF, menuRef);
		}
		
		
		if (reference != null)
			obj.put(KEY_REFERENCE, reference);

		if (buttonCallback != null)
			obj.put(KEY_BUTTON_CALLBACK, buttonCallback);

		if (buttonQueryResult != null)
			obj.put(KEY_BUTTON_QUERY_RESULTS, buttonQueryResult);

		return obj;

	}

	/**
	 * @return the messageId
	 */
	public String getMessageId() {
		return messageId;
	}

	/**
	 * @param messageId
	 *            the messageId to set
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
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
	 * @return the reference ( Unique local identifier for the target chat/user)
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * @param reference
	 *            the reference to set ( Unique local identifier for the target
	 *            chat/user)
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * @return the from (User type)
	 */
	public User getFrom() {
		return from;
	}

	/**
	 * @param from
	 *            the from to set (User type)
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
	 * @param menuRef the menuRef to set
	 */
	public void setMenuRef(String menuRef) {
		this.menuRef = menuRef;
	}
}
