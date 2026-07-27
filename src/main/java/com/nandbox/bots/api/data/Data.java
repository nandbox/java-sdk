package com.nandbox.bots.api.data;

import java.util.ArrayList;
import java.util.List;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public class Data {

	private static final String KEY_PATTERN = "pattern";
	private static final String KEY_ID = "id";
	private static final String KEY_EXAMPLE = "example";
	private static final String KEY_TAGS = "tags";

	private String pattern;
	private String id;
	private String example;
	// ApiAddWhitelistPatterns reads "tags" off each pattern, but this class never
	// carried the field, so tags could not be assigned through the SDK.
	private List<String> tags;


	public Data() {

	}

	public Data(JSONObject obj) {
		this.pattern = (String) obj.get(KEY_PATTERN);
		this.id = (String) obj.get(KEY_ID);
		this.example = (String) obj.get(KEY_EXAMPLE);
		Object rawTags = obj.get(KEY_TAGS);
		if (rawTags instanceof JSONArray) {
			this.tags = new ArrayList<>();
			for (Object tag : (JSONArray) rawTags) {
				if (tag != null)
					this.tags.add(String.valueOf(tag));
			}
		}
	}

	public JSONObject toJsonObject() {
		JSONObject obj = new JSONObject();

		if (pattern != null)
			obj.put(KEY_PATTERN, pattern);

		if (id != null)
			obj.put(KEY_ID, id);
		if (example!=null)
			obj.put(KEY_EXAMPLE,example);
		if (tags != null) {
			JSONArray tagArray = new JSONArray();
			tagArray.addAll(tags);
			obj.put(KEY_TAGS, tagArray);
		}
		return obj;

	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	public String getExample(){
		return this.example;
	}

	public void setExample(String example) {
		this.example = example;
	}
}
