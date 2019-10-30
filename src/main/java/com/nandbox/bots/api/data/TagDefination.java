package com.nandbox.bots.api.data;

import com.nandbox.bots.api.util.Utils;

import net.minidev.json.JSONObject;

public class TagDefination {

	private static final String KEY_NAME = "name";
	private static final String KEY_DESCRIPTION = "description";
	private static final String KEY_ID = "id";
	private static final String KEY_ISPRIVATE = "isPrivate";

	private String name;
	private String description;
	private String id;
	private Integer isPrivate;

	public TagDefination(JSONObject obj) {
		this.id = (String) obj.get(KEY_ID);
		this.name = (String) obj.get(KEY_NAME);
		this.description = (String) obj.get(KEY_DESCRIPTION);
		this.isPrivate = Utils.getInteger(obj.get(KEY_ISPRIVATE));

	}

	public JSONObject toJsonObject() {
		JSONObject obj = new JSONObject();

		if (id != null)
			obj.put(KEY_ID, id);

		if (name != null)
			obj.put(KEY_NAME, name);

		if (description != null)
			obj.put(KEY_DESCRIPTION, description);

		if (isPrivate != null)
			obj.put(KEY_ISPRIVATE, isPrivate);

		return obj;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getIsPrivate() {
		return isPrivate;
	}

	public void setIsPrivate(Integer isPrivate) {
		this.isPrivate = isPrivate;
	}
}
