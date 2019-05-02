/**
 * 
 */
package com.nandbox.bots.api.data;

import com.nandbox.bots.api.util.Utils;

import net.minidev.json.JSONObject;

/**
 * This class represents incoming Message used to get Video Message .
 * 
 * @author Ahmed A. El-Malatawy
 *
 */
public class Video {

	public static final String KEY_ID = "id";
	public static final String KEY_WIDTH = "width";
	public static final String KEY_HEIGHT = "height";
	public static final String KEY_SIZE = "size";
	public static final String KEY_THUMBNAIL = "thumbnail";
	public static final String KEY_DURATION = "duration";

	private String id;
	private Integer width;
	private Integer height;
	private Long size;
	private Thumbnail thumbnail;
	private Integer duration;

	public Video(JSONObject obj) {
		this.id = String.valueOf(obj.get(KEY_ID));
		this.width = Utils.getInteger(obj.get(KEY_WIDTH));
		this.height = Utils.getInteger(obj.get(KEY_HEIGHT));
		this.size = Utils.getLong(obj.get(KEY_SIZE));
		this.thumbnail = obj.get(KEY_THUMBNAIL) != null
				? new Thumbnail((JSONObject) obj.get(KEY_THUMBNAIL))
				: null;
		this.setDuration(Utils.getInteger(obj.get(KEY_DURATION)));

	}

	public JSONObject toJsonObject() {
		JSONObject obj = new JSONObject();

		if (id != null)
			obj.put(KEY_ID, id);

		if (width != null)
			obj.put(KEY_WIDTH, width);

		if (height != null)
			obj.put(KEY_HEIGHT, height);

		if (size != null)
			obj.put(KEY_SIZE, size);

		if (thumbnail != null)
			obj.put(KEY_THUMBNAIL, thumbnail);

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
	 * @return the thumbnail
	 */
	public Thumbnail getThumbnail() {
		return thumbnail;
	}

	/**
	 * @param thumbnail
	 *            the thumbnail to set
	 */
	public void setThumbnail(Thumbnail thumbnail) {
		this.thumbnail = thumbnail;
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
