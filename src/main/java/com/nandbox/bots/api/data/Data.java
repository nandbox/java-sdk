package com.nandbox.bots.api.data;

import net.minidev.json.JSONObject;

public class Data {

	private static final String KEY_PATTERN = "pattern";
	private static final String KEY_EXAMPLE = "example";
	private String pattern;
	private String example;

	public Data() {

	}

	public Data(JSONObject obj) {
		this.pattern = (String) obj.get(KEY_PATTERN);
		this.example = (String) obj.get(KEY_EXAMPLE);

	}

	public JSONObject toJsonObject() {
		JSONObject obj = new JSONObject();

		if (pattern != null)
			obj.put(KEY_PATTERN, pattern);

		if (example != null)
			obj.put(KEY_EXAMPLE, example);

		return obj;

	}

	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

}
