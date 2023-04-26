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

	/**
	 Creates a new Document object from a JSONObject.
	 @param obj the JSONObject to create the document from
	 */
	public Document(JSONObject obj) {
		this.id = String.valueOf(obj.get(KEY_ID));
		this.name = String.valueOf(obj.get(KEY_NAME));
		this.size = Utils.getLong(obj.get(KEY_SIZE));

	}

	/**
	 Returns a JSONObject representation of the document.
	 @return the JSONObject representation of the document
	 */
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

	/**
	 Returns a string representation of the document in JSON format.
	 @return a string representation of the document in JSON format
	 */
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
	 * Returns the size of the document.
	 *
	 * @return the size of the document
	 */
	public Long getSize() {
		return size;
	}

	/**
	 * Sets the size of the document.
	 *
	 * @param size the size to set
	 */
	public void setSize(Long size) {
		this.size = size;
	}

	/**
	 * Returns the ID of the document.
	 *
	 * @return the ID of the document
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the ID of the document.
	 *
	 * @param id the ID to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the name of the document.
	 *
	 * @return the name of the document
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the document.
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
