/**
 * 
 */
package com.nandbox.bots.api.data;

import com.nandbox.bots.api.util.Utils;

import net.minidev.json.JSONObject;

/**
 * * This class represents incoming Message used to get Voice Message .
 * 
 * @author Ahmed A. El-Malatawy
 *
 */
public class Voice {

	public static final String KEY_ID = "id";
	public static final String KEY_DURATION = "duration";
	public static final String KEY_SIZE = "size";

	private String id;
	private Integer duration;
	private Long size;

	public Voice(JSONObject obj) {
		this.id = String.valueOf(obj.get(KEY_ID));
		this.setDuration(Utils.getInteger(obj.get(KEY_DURATION)));
		this.size = Utils.getLong(obj.get(KEY_SIZE));
	}

	public JSONObject toJsonObject() {
		JSONObject obj = new JSONObject();

		if (id != null)
			obj.put(KEY_ID, id);

		if (duration != null)
			obj.put(KEY_DURATION, duration);

		if (size != null)
			obj.put(KEY_SIZE, size);

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
	 * @return the duration
	 */
	public Integer getDuration() {
		return duration;
	}

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(Integer duration) {
		this.duration = duration;
	}

}
