/**
 * 
 */
package com.nandbox.bots.api.data;


import com.nandbox.bots.api.util.Utils;

import net.minidev.json.JSONObject;

/**
 * This class represents incoming Message used to get Audio Message .
 * 
 * @author Ahmed A. El-Malatawy
 *
 */
public class Audio {

	public static final String KEY_ID = "id";
	public static final String KEY_TITLE = "title";
	public static final String KEY_PERFORMER = "performer";
	public static final String KEY_SIZE = "size";

	public static final String KEY_DURATION = "duration";

	private String id;
	private String title;
	private String performer;
	private Long size;
	private Integer duration;

	public Audio(JSONObject obj) {
		this.id = String.valueOf(obj.get(KEY_ID));
		this.title = String.valueOf(obj.get(KEY_TITLE));
		this.performer = String.valueOf(obj.get(KEY_PERFORMER));
		this.size = Utils.getLong(obj.get(KEY_SIZE));
		this.duration = Utils.getInteger(obj.get(KEY_DURATION));
	}

	public JSONObject toJsonObject() {
		JSONObject obj = new JSONObject();

		if (id != null)
			obj.put(KEY_ID, id);

		if (title != null)
			obj.put(KEY_TITLE, title);

		if (performer != null)
			obj.put(KEY_PERFORMER, performer);

		if (size != null)
			obj.put(KEY_SIZE, size);

		if (duration != null)
			obj.put(KEY_DURATION, duration);

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

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the performer name
	 */
	public String getPerformer() {
		return performer;
	}

	/**
	 * @param performer
	 *            the performer name to set
	 */
	public void setPerformer(String performer) {
		this.performer = performer;
	}

}
