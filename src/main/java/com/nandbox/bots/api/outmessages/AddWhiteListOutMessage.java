package com.nandbox.bots.api.outmessages;

import java.util.ArrayList;
import java.util.List;

import com.nandbox.bots.api.data.WhiteListUser;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 * @author Hossam
 *
 */
public class AddWhiteListOutMessage extends OutMessage {

	protected static final String KEY_USERS = "users";

	private List<WhiteListUser> whiteListUser = new ArrayList<>();

	// private WhiteListUser whiteListUser;
	public AddWhiteListOutMessage() {
		this.method = OutMessageMethod.addWhitelist;
	}

	@Override
	public JSONObject toJsonObject() {
		JSONObject obj = super.toJsonObject();
		JSONArray menusArrayObj = new JSONArray();
		for (int i = 0; i < whiteListUser.size(); i++) {
			menusArrayObj.add(whiteListUser.get(i).toJsonObject());
		}

		obj.put(KEY_USERS, menusArrayObj);

		return obj;
	}

	public List<WhiteListUser> getWhiteListUser() {
		return whiteListUser;
	}

	public void setWhiteListUser(List<WhiteListUser> whiteListUser) {
		this.whiteListUser = whiteListUser;
	}


}
