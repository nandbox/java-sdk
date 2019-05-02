/**
 * 
 */
package com.nandbox.bots.api.data;

import com.nandbox.bots.api.util.Utils;

import net.minidev.json.JSONObject;

/**
 * This class represents incoming Message used to get Thumbnail Message .
 * 
 * @author Ahmed A. El-Malatawy
 *
 */
public class Thumbnail {

	public static final String KEY_ID = "id";
	public static final String KEY_WIDTH = "width";
	public static final String KEY_HEIGHT = "height";

	private String id;
	private Integer width;
	private Integer height;

	/**
	 * @param obj
	 *            JSON object holding Thumbnail fields
	 */
	public Thumbnail(JSONObject obj) {
		this.id = String.valueOf(obj.get(KEY_ID));
		this.width = Utils.getInteger(obj.get(KEY_WIDTH));
		this.height = Utils.getInteger(obj.get(KEY_HEIGHT));
	}

	public JSONObject toJsonObject() {
		JSONObject obj = new JSONObject();

		if (id != null)
			obj.put(KEY_ID, id);

		if (width != null)
			obj.put(KEY_WIDTH, width);

		if (height != null)
			obj.put(KEY_HEIGHT, height);

		return obj;

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
	 * @return the width
	 */
	public Integer getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(Integer width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public Integer getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(Integer height) {
		this.height = height;
	}

}
