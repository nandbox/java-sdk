package com.nandbox.bots.api.outmessages;


import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 * This abstract class is the superclass of all classes representing Output
 * messages .
 * 
 * 
 * @author Ahmed A. El-Malatawy
 *
 */
public abstract class OutMessage {

	public enum OutMessageMethod {

		sendMessage, sendPhoto, sendVideo, sendCellMessage, sendCellPhoto, sendCellVideo, sendAudio, sendVoice,
		sendLocation, sendGif, sendDocument, sendContact, editMessage, updateMessage, setChatMenu, setNavigationButton,
		inlineSearchAnswer, setMyProfile, getUserDetail, getChat, getChatAdministrators, getChatMember, banChatMember,
		unbanChatMember, removeChatMember, setChat, recallMessage, getMyProfiles, generatePermanentUrl, sendArticle,
		getBlacklistUsers, getWhitelistUsers, addToBlacklist, addToWhitelist, removeFromBlacklist, addBlacklistPatterns,
		removeBlacklistPatterns, addWhitelistPatterns, removeWhitelistPatterns, removeFromWhitelist, setAdminChatMenu,
		setAdminNavigationButton, cancelMessageSchedule, updateMenuCell,addChatMember,createChat,addChatAdmin,setWorkflowAction
		,getCollectionProduct, listCollections, getProductDetail

	}

	public static final int WEB_PREVIEW_DISABLE = 1;
	public static final int WEB_PREVIEW_HIDE_LINK = 2;
	public static final int WEB_PREVIEW_INSTANCE_VIEW = 3;
	public static final int WEB_PREVIEW_INSTANCE_WITHOUT_LINK = 4;

	protected static final String KEY_METHOD = "method";
	protected static final String KEY_CHAT_ID = "chat_id";
	protected static final String KEY_APP_ID = "app_id";
	protected static final String KEY_REFERENCE = "reference";
	protected static final String KEY_REF = "ref";
	protected static final String KEY_TO_USER_ID = "to_user_id";
	protected static final String KEY_REPLAY_TO_MESSAGE_ID = "reply_to_message_id";
	protected static final String KEY_WEB_PAGE_PREVIEW = "web_page_preview";
	protected static final String KEY_DISABLE_NOTIFICATION = "disable_notification";
	protected static final String KEY_CAPTION = "caption";
	protected static final String KEY_ECHO = "echo";
	protected static final String KEY_MENU_REF = "menu_ref";
	protected static final String KEY_INLINE_MENU = "inline_menu";
	protected static final String KEY_CHAT_SETTINGS = "chat_settings";
	protected static final String KEY_STYLE = "style";
	protected static final String KEY_SCHEDULE_DATE = "schedule_date";
	protected static final String KEY_TAB = "tab";
	protected static final String KEY_TAG = "tags";

	protected OutMessageMethod method;
	protected String chatId;
	protected String reference;
	protected String ref;

	protected String app_id;
	protected String toUserId;
	protected String replyToMessageId;
	protected Integer webPagePreview;
	protected Boolean disableNotification;
	protected String caption;
	private Integer echo;
	private String menuRef;
	private JSONArray inlineMenus;
	private Integer chatSettings;
	private Long scheduleDate;
	private String tab;
	private String[] tag;


	public JSONObject toJsonObject() {

		JSONObject obj = new JSONObject();

		if (method != null) {
			obj.put(KEY_METHOD, method);
		}

		if (chatId != null) {
			obj.put(KEY_CHAT_ID, chatId);
		}

		if (reference != null) {
			obj.put(KEY_REFERENCE, reference);
		}
		if (ref != null) {
			obj.put(KEY_REF, ref);
		}

		if (toUserId != null) {
			obj.put(KEY_TO_USER_ID, toUserId);
		}

		if (replyToMessageId != null) {
			obj.put(KEY_REPLAY_TO_MESSAGE_ID, replyToMessageId);
		}

		if (webPagePreview != null) {
			obj.put(KEY_WEB_PAGE_PREVIEW, webPagePreview);
		}

		if (disableNotification != null) {
			obj.put(KEY_DISABLE_NOTIFICATION, disableNotification);
		}

		if (caption != null) {
			obj.put(KEY_CAPTION, caption);
		}

		if (echo != null) {
			obj.put(KEY_ECHO, echo);
		}

		if (menuRef != null) {
			obj.put(KEY_MENU_REF, menuRef);
		}
		if (app_id != null) {
			obj.put(KEY_APP_ID, app_id);
		}
		if (inlineMenus != null) {
			obj.put(KEY_INLINE_MENU, inlineMenus);
		}

		if (chatSettings != null) {
			obj.put(KEY_CHAT_SETTINGS, chatSettings);
		}

		if (scheduleDate != null) {
			obj.put(KEY_SCHEDULE_DATE, scheduleDate);
		}
		
		if (tab != null) {
			obj.put(KEY_TAB, tab);
		}
		if (tag!=null&&tag.length>0){
			obj.put(KEY_TAG,tag);
		}
		return obj;
	}

	/**
	 * @return the webPagePreview
	 */
	public Integer getWebPagePreview() {
		return webPagePreview;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String[] getTag() {
		return tag;
	}

	public void setTag(String[] tag) {
		this.tag = tag;
	}


	public String getRef() {
		return ref;
	}

	/**
	 * @param webPagePreview
	 *            the webPagePreview to set
	 */

	public void setWebPagePreview(Integer webPagePreview) {
		this.webPagePreview = webPagePreview;
	}

	/**
	 * @return the method
	 */
	public OutMessageMethod getMethod() {
		return method;
	}

	/**
	 * @param method
	 *            the method to set
	 */
	public void setMethod(OutMessageMethod method) {
		this.method = method;
	}

	/**
	 * @return the chatId
	 */
	public String getChatId() {
		return chatId;
	}

	/**
	 * @param chatId
	 *            the chatId to set
	 */
	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

	/**
	 * @return the reference
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * @param reference
	 *            the reference to set
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * @return the toUserId
	 */
	public String getToUserId() {
		return toUserId;
	}
	/**
	 * @return the app_Id
	 */
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		if (app_id!=null){
			this. app_id=app_id;

		}else {
			this.app_id="0";
		}
	}
	/**
	 * @param toUserId
	 *            the toUserId to set
	 */
	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	/**
	 * @return the replyToMessageId
	 */
	public String getReplyToMessageId() {
		return replyToMessageId;
	}

	/**
	 * @param replyToMessageId
	 *            the replyToMessageId to set Disables link previews for links in
	 *            this message
	 */
	public void setReplyToMessageId(String replyToMessageId) {
		this.replyToMessageId = replyToMessageId;
	}

	/**
	 * @return the disableNotification
	 */
	public Boolean getDisableNotification() {
		return disableNotification;
	}

	/**
	 * @param disableNotification
	 *            the disableNotification to Sends the message silently. Users will
	 *            receive a notification with no sound.
	 */
	public void setDisableNotification(Boolean disableNotification) {
		this.disableNotification = disableNotification;
	}

	/**
	 * @return the caption
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * @param caption
	 *            the caption to set
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}

	/**
	 * @return the echo
	 */
	public Integer getEcho() {
		return echo;
	}

	/**
	 * @param echo
	 *            the echo to set
	 */
	public void setEcho(Integer echo) {
		this.echo = echo;
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
	 * @return the inlineMenu
	 */
	public JSONArray getInlineMenu() {
		return inlineMenus;
	}

	/**
	 * @param inlineMenu
	 *            the inlineMenu to set
	 */
	public void setInlineMenu(JSONArray inlineMenu) {
		this.inlineMenus = inlineMenu;
	}

	/**
	 * @return the chatSettings
	 */
	public Integer getChatSettings() {
		return chatSettings;
	}

	/**
	 * @param chatSettings
	 *            the chatSettings to set
	 */
	public void setChatSettings(Integer chatSettings) {
		this.chatSettings = chatSettings;
	}

	
	/**
	 * @return scheduleDate
	 */
	public Long getScheduleDate() {
		return scheduleDate;
	}

	/**
	 * @param scheduleDate
	 *            (Milliseconds)
	 */
	public void setScheduleDate(Long scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public String getTab() {
		return tab;
	}

	
	/**
	 * @param tab tab id to set
	 *            
	 */
	public void setTab(String tab) {
		this.tab = tab;
	}


}
