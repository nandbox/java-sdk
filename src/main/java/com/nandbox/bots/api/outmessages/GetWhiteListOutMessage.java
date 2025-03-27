package com.nandbox.bots.api.outmessages;

import net.minidev.json.JSONObject;

/**
 * @author Hossam
 *
 */
public class GetWhiteListOutMessage extends OutMessage {

	protected static final String PAGE_SIZE = "page_size";

	private String pageSize;

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public GetWhiteListOutMessage() {
		this.method = OutMessageMethod.getWhitelistUsers;
	}

	@Override
	public JSONObject toJsonObject() {
		JSONObject obj = super.toJsonObject();
		if (pageSize != null) {
			obj.put(PAGE_SIZE, pageSize);
		}
		return obj;
	}

}
