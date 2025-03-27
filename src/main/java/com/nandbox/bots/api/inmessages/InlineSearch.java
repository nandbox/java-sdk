package com.nandbox.bots.api.inmessages;

import com.nandbox.bots.api.util.Utils;
import com.nandbox.bots.api.data.Chat;
import com.nandbox.bots.api.data.User;

import net.minidev.json.JSONObject;

/**
 * * it represents InlineSearch Object , the message Object is representing
 * Server Messages with method : inlineSearch
 * 
 * @author Ahmed A. El-Malatawy
 *
 */
public class InlineSearch {

	private static final String KEY_INLINE_SEARCH = "inlineSearch";
	private static final String KEY_DATE = "date";
	private static final String KEY_METHOD = "method";
	private static final String KEY_CHAT = "chat";
	private static final String KEY_FROM = "from";
	private static final String KEY_SEARCH_ID = "search_id";	
	private static final String KEY_OFFSET = "offset";
	private static final String KEY_KEYWORDS = "keywords";
	private static final String KEY_APP_ID = "app_id";



	private Long date;
	private String method;
	private User from;
	private Chat chat;
	private Integer searchId;
	private String offset;
	private String keywords;
	private Long appId;
	public InlineSearch(JSONObject jsonObj) {

		//System.out.println("json " + jsonObj.toJSONString());
		JSONObject obj = (JSONObject) jsonObj.get(KEY_INLINE_SEARCH);

		User fromUser = new User((JSONObject) obj.get(KEY_FROM));
		this.chat = obj.get(KEY_CHAT) == null ? null : new Chat((JSONObject) obj.get(KEY_CHAT));
		this.method = String.valueOf(obj.get(KEY_METHOD));
		this.from = fromUser;
		this.date = Utils.getLong(obj.get(KEY_DATE));
		this.searchId = Utils.getInteger(obj.get(KEY_SEARCH_ID));
		this.offset = (String) obj.get(KEY_OFFSET);
		this.keywords = String.valueOf(obj.get(KEY_KEYWORDS));
		this.appId =jsonObj.get(KEY_APP_ID) != null
				? Long.parseLong(String.valueOf(jsonObj.get(KEY_APP_ID)))
				: 0;;
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
		if (searchId != null) {

			obj.put(KEY_SEARCH_ID, searchId);
		}	
		if (offset != null) {

			obj.put(KEY_OFFSET, offset);
		}	
		if (keywords != null) {

			obj.put(KEY_KEYWORDS, keywords);
		}
		if (appId!=null){
			obj.put(KEY_APP_ID,appId);
		}
		//System.out.println("to " + obj.toJSONString());
		return obj;

	}

	/**
	 * @return the date
	 */
	public Long getDate() {
		return date;
	}
	public Long getAppId(){
		return appId;
	}
	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Long date) {
		this.date = date;
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
	 * @return the searchId
	 */
	public Integer getSearchId() {
		return searchId;
	}

	/**
	 * @param searchId the searchId to set
	 */
	public void setSearchId(Integer searchId) {
		this.searchId = searchId;
	}

	/**
	 * @return the offset
	 */
	public String getOffset() {
		return offset;
	}

	/**
	 * @param offset the offset to set
	 */
	public void setOffset(String offset) {
		this.offset = offset;
	}

	/**
	 * @return the keywords
	 */
	public String getKeywords() {
		return keywords;
	}

	/**
	 * @param keywords the keywords to set
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
}
