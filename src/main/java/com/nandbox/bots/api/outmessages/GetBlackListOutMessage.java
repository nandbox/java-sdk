package com.nandbox.bots.api.outmessages;

import net.minidev.json.JSONObject;

/**
 * @author Hossam
 *
 */
public class GetBlackListOutMessage extends OutMessage {
	protected static final String PAGE_SIZE = "page_size";

	private String pageSize;

	public GetBlackListOutMessage() {
		this.method = OutMessageMethod.getBlacklistUsers;
	}

	@Override
	public JSONObject toJsonObject() {
		JSONObject obj = super.toJsonObject();
		if (pageSize != null) {
			obj.put(PAGE_SIZE, pageSize);
		}
		return obj;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
}
