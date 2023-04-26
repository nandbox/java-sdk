/**
 * 
 */
package com.nandbox.bots.api.data;

import com.nandbox.bots.api.util.Utils;

import net.minidev.json.JSONObject;

/**
 * This class represents incoming Message used to get Gif Message .
 * 
 * @author Ahmed A. El-Malatawy
 *
 */

/**
 * Represents a GIF image with an ID, width, height, size, and thumbnail.
 */
public class Gif {

	public static final String KEY_ID = "id";
	public static final String KEY_WIDTH = "width";
	public static final String KEY_HEIGHT = "height";
	public static final String KEY_SIZE = "size";
	public static final String KEY_THUMBNAIL = "thumbnail";

	private String id;
	private Integer width;
	private Integer height;
	private Long size;
	private Thumbnail thumbnail;

	/**
	 * Creates a new Gif object from a JSONObject.
	 *
	 * @param obj the JSONObject to create the GIF image from
	 */
	public Gif(JSONObject obj) {
		this.id = String.valueOf(obj.get(KEY_ID));
		this.width = Utils.getInteger(obj.get(KEY_WIDTH));
		this.height = Utils.getInteger(obj.get(KEY_HEIGHT));
		this.size = Utils.getLong(obj.get(KEY_SIZE));
		this.thumbnail = obj.get(KEY_THUMBNAIL) != null
				? new Thumbnail((JSONObject) obj.get(KEY_THUMBNAIL))
				: null;

	}

	/**
	 * Returns a JSONObject representation of the GIF image.
	 *
	 * @return the JSONObject representation of the GIF image
	 */
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

		return obj;

	}

	/**
	 * Returns the ID of the GIF image.
	 *
	 * @return the ID of the GIF image
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the ID of the GIF image.
	 *
	 * @param id the ID to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the width of the GIF image.
	 *
	 * @return the width of the GIF image
	 */
	public Integer getWidth() {
		return width;
	}

	/**
	 * Sets the width of the GIF image.
	 *
	 * @param width the width to set
	 */
	public void setWidth(Integer width) {
		this.width = width;
	}

	/**
	 * Returns the height of the GIF image.
	 *
	 * @return the height of the GIF image
	 */
	public Integer getHeight() {
		return height;
	}

	/**
	 * Sets the height of the GIF image.
	 *
	 * @param height the height to set
	 */
	public void setHeight(Integer height) {
		this.height = height;
	}

	/**
	 * Returns the size of the GIF image.
	 *
	 * @return the size of the GIF image
	 */
	public Long getSize() {
		return size;
	}

	/**
	 * Sets the size of the GIF image.
	 *
	 * @param size the size to set
	 */
	public void setSize(Long size) {
		this.size = size;
	}

	/**
	 * Returns the thumbnail of the GIF image.
	 *
	 * @return the thumbnail of the GIF image
	 */
	public Thumbnail getThumbnail() {
		return thumbnail;
	}

	/**
	 * Sets the thumbnail of the GIF image.
	 *
	 * @param thumbnail the thumbnail to set
	 */
	public void setThumbnail(Thumbnail thumbnail) {
		this.thumbnail = thumbnail;
	}

}
