package com.nandbox.bots.api.data;

import com.nandbox.bots.api.util.Utils;

import net.minidev.json.JSONObject;

/**
 * This class represents incoming Message used to get Document Message .
 * 
 * @author Hossam
 *
 */
public class Document {

	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_SIZE = "size";

	private String id;
	private String name;
	private Long size;

	public Document(JSONObject obj) {
		this.id = String.valueOf(obj.get(KEY_ID));
		this.name = String.valueOf(obj.get(KEY_NAME));
		this.size = Utils.getLong(obj.get(KEY_SIZE));

	}

	public JSONObject toJsonObject() {
		JSONObject obj = new JSONObject();
		if (id != null)
			obj.put(KEY_ID, id);
		if (name != null)
			obj.put(KEY_NAME, name);
		if (size != null)
			obj.put(KEY_SIZE, size);
		return obj;

	}

	@Override
	public String toString() {
		StringBuilder  outStrBuf = new StringBuilder ();
		outStrBuf.append("{\n");
		if (id != null)
			outStrBuf.append("\"" + KEY_ID + "\":\"" + id + "\n");
		if (name != null)
			outStrBuf.append("\"" + KEY_NAME + "\":\"" + name + "\n");
		if (size != null)
			outStrBuf.append("\"" + KEY_SIZE + "\":\"" + size + "\n");
		outStrBuf.append("}");
		return outStrBuf.toString();
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

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
