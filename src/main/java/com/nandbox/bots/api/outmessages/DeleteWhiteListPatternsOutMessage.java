package com.nandbox.bots.api.outmessages;

import java.util.ArrayList;
import java.util.List;

import net.minidev.json.JSONObject;

public class DeleteWhiteListPatternsOutMessage extends OutMessage {

	protected static final String KEY_PATTERN = "patterns";
	private List<String> pattern = new ArrayList<>();

	public DeleteWhiteListPatternsOutMessage() {
		this.method = OutMessageMethod.removeWhitelistPatterns;
	}
	@Override
	public JSONObject toJsonObject() {
		JSONObject obj = super.toJsonObject();
		if (pattern != null) {
			obj.put(KEY_PATTERN, pattern);
		}

		return obj;
	}

	public List<String> getPattern() {
		return pattern;
	}

	public void setPattern(List<String> pattern) {
		this.pattern = pattern;
	}

}
