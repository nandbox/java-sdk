package com.nandbox.bots.api.inmessages;

import java.util.ArrayList;
import java.util.List;

import com.nandbox.bots.api.data.Chat;
import com.nandbox.bots.api.data.User;
import com.nandbox.bots.api.util.Utils;

import net.minidev.json.JSONObject;

/**
 * Object represents a chat member user returned in getChatMember banChatMember,
 * unbanChatMember, removeChatMember and when user join or leaves the chat.
 * 
 * @author Hossam Mohamed
 *
 */
public class ChatMember {

	private static final String KEY_CHAT_MEMBER = "chatMember";
	private static final String KEY_USER = "user";
	private static final String KEY_CHAT = "chat";
	private static final String KEY_TYPE = "type";
	private static final String KEY_MEMBER_SINCE = "member_since";
	private static final String KEY_STATUS = "status";
	private static final String KEY_TAGS = "tags";
	private static final String KEY_ACCOUNT_TYPE = "account_type";
	private static final String KEY_MSISDN = "msisdn";

	private User user;
	private Chat chat;
	private String type;
	private Long memberSince;
	private String status;
	private List<String> tags = new ArrayList<>();
	private String accountType;
	private String msisdn;
	
	@SuppressWarnings("unchecked")
	public ChatMember(JSONObject jsonObj) {

		JSONObject obj = (JSONObject) jsonObj.get(KEY_CHAT_MEMBER);
		this.user = obj.get(KEY_USER) == null ? null : new User((JSONObject) obj.get(KEY_USER));
		this.chat = obj.get(KEY_CHAT) == null ? null : new Chat((JSONObject) obj.get(KEY_CHAT));

		this.type = (String) obj.get(KEY_TYPE);
		this.memberSince = Utils.getLong(obj.get(KEY_MEMBER_SINCE));
		this.status = (String) obj.get(KEY_STATUS);
		this.tags = (ArrayList<String>) obj.get(KEY_TAGS);
		this.accountType = (String) obj.get(KEY_ACCOUNT_TYPE);
		this.msisdn = (String) obj.get(KEY_MSISDN);
//		this.tags = (String) obj.get(KEY_TAGS);
		



	}

	public JSONObject toJsonObject() {

		JSONObject obj = new JSONObject();
		if (tags != null) {
			obj.put(KEY_TAGS, tags);
		}

		if (user != null) {
			obj.put(KEY_USER, user.toJsonObject());
		}
		if (chat != null) {
			obj.put(KEY_CHAT, chat.toJsonObject());
		}
		if (type != null) {
			obj.put(KEY_TYPE, type);
		}
		if (memberSince != null) {
			obj.put(KEY_MEMBER_SINCE, memberSince);
		}
		if (status != null) {
			obj.put(KEY_STATUS, status);
		}

		if (accountType != null) {
			obj.put(KEY_ACCOUNT_TYPE, accountType);
		}
		
		if (msisdn != null) {
			obj.put(KEY_MSISDN, msisdn);
		}
		return obj;

	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the memberSince
	 */
	public Long getMemberSince() {
		return memberSince;
	}

	/**
	 * @param memberSince
	 *            the memberSince to set
	 */
	public void setMemberSince(Long memberSince) {
		this.memberSince = memberSince;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
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

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

}