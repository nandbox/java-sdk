package com.nandbox.bots.api.data;

import com.nandbox.bots.api.util.Utils;

import net.minidev.json.JSONObject;

public class TextFile {

	public static final String KEY_SIZE = "size";
	public static final String KEY_ID = "id";

	private Long size;
	private String id;

	public TextFile(JSONObject obj) {

		this.size = Utils.getLong(obj.get(KEY_SIZE));
		this.id = String.valueOf(obj.get(KEY_ID));

	}

	public JSONObject toJsonObject() {
		JSONObject obj = new JSONObject();

		if (size != null)
			obj.put(KEY_SIZE, size);

		if (id != null)
			obj.put(KEY_ID, id);

		return obj;

	}

	/**
	 * @return the size
	 */
	public Long getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(Long size) {
		this.size = size;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
}
