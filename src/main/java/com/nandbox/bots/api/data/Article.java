package com.nandbox.bots.api.data;

import com.nandbox.bots.api.util.Utils;

import net.minidev.json.JSONObject;

/**
 * This class represents incoming Message used to get Article Message .
 * 
 * @author Hossam Mohamed
 *
 */
public class Article {

	public static final String KEY_ID = "id";
	public static final String KEY_WIDTH = "width";
	public static final String KEY_HEIGHT = "height";
	public static final String KEY_SIZE = "size";
	public static final String KEY_THUMBNAIL = "thumbnail";

	// private static final String KEY_URL = "url";
	// private static final String KEY_TITLE = "title";
	// private static final String KEY_DESCRIPTION = "description";
	// private static final String KEY_PHOTO = "photo";
	// private static final String KEY_PHOTO_URL = "photo_url";

	// private String url;
	// private String title;
	// private String description;
	// private String photo;
	// private String photoUrl;
	private String id;
	private Integer width;
	private Integer height;
	private Long size;
	private Thumbnail thumbnail;

	public Article(JSONObject obj) {

		this.id = String.valueOf(obj.get(KEY_ID));
		this.width = Utils.getInteger(obj.get(KEY_WIDTH));
		this.height = Utils.getInteger(obj.get(KEY_HEIGHT));
		this.size = Utils.getLong(obj.get(KEY_SIZE));
		this.thumbnail = obj.get(KEY_THUMBNAIL) != null ? new Thumbnail((JSONObject) obj.get(KEY_THUMBNAIL)) : null;
		// this.url = String.valueOf(obj.get(KEY_URL));
		// this.title = String.valueOf(obj.get(KEY_TITLE));
		// this.description = String.valueOf(obj.get(KEY_DESCRIPTION));
		// this.photo = String.valueOf(obj.get(KEY_PHOTO));
		// this.photoUrl = String.valueOf(obj.get(KEY_PHOTO_URL));
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

}
