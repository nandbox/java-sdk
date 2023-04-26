package com.nandbox.bots.api.data;

import net.minidev.json.JSONObject;

public class Data {

	private static final String KEY_PATTERN = "pattern";
	private static final String KEY_EXAMPLE = "example";
	private String pattern;
	private String example;

	public Data() {

	}

	/**
	 Creates a new Data object from a JSONObject.
	 @param obj the JSONObject to create the data from
	 */
	public Data(JSONObject obj) {
		this.pattern = (String) obj.get(KEY_PATTERN);
		this.example = (String) obj.get(KEY_EXAMPLE);

	}

	/**
	 Returns a JSONObject representation of the data.
	 @return the JSONObject representation of the data
	 */
	public JSONObject toJsonObject() {
		JSONObject obj = new JSONObject();

		if (pattern != null)
			obj.put(KEY_PATTERN, pattern);

		if (example != null)
			obj.put(KEY_EXAMPLE, example);

		return obj;

	}

	/**
	 Returns the example.
	 @return the example
	 */
	public String getExample() {
		return example;
	}

	/**
	 Sets the example.
	 @param example the example to set
	 */
	public void setExample(String example) {
		this.example = example;
	}

	/**
	 Returns the pattern.
	 @return the pattern
	 */
	public String getPattern() {
		return pattern;
	}

	/**
	 Sets the pattern.
	 @param pattern the pattern to set
	 */
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

}
