package com.nandbox.bots.api.data;

import com.nandbox.bots.api.util.Utils;

import net.minidev.json.JSONObject;

/**
 * This class represents incoming Message used to get Article Message .
 * 
 * @author Hossam Mohamed
 *
 */

/**
 * Represents an article object with various attributes such as id, width, height, size, and thumbnail.
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

	/**
	 * Constructs an Article object using a JSONObject.
	 * @param obj The JSONObject to use for initialization.
	 */
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
	/**
	 * Returns a JSONObject representation of the article object.
	 * @return The JSONObject representation of the article object.
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

	// Getters and setters

	/**
	 * Returns the article ID.
	 * @return The article ID.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the article ID.
	 * @param id The article ID to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the width of the article.
	 * @return The width of the article.
	 */
	public Integer getWidth() {
		return width;
	}

	/**
	 * Sets the width of the article.
	 * @param width The width of the article to set.
	 */
	public void setWidth(Integer width) {
		this.width = width;
	}

	/**
	 * Returns the height of the article.
	 * @return The height of the article.
	 */
	public Integer getHeight() {
		return height;
	}

	/**
	 * Sets the height of the article.
	 * @param height The height of the article to set.
	 */
	public void setHeight(Integer height) {
		this.height = height;
	}

	/**
	 * Returns the size of the article.
	 * @return The size of the article.
	 */
	public Long getSize() {
		return size;
	}

	/**
	 * Sets the size of the article.
	 * @param size The size of the article to set.
	 */
	public void setSize(Long size) {
		this.size = size;
	}

	/**
	 * Returns the thumbnail of the article.
	 * @return The thumbnail of the article.
	 */
	public Thumbnail getThumbnail() {
		return thumbnail;
	}

	/**
	 * Sets the thumbnail of the article.
	 * @param thumbnail The thumbnail of the article to set.
	 */
	public void setThumbnail(Thumbnail thumbnail) {
		this.thumbnail = thumbnail;
	}

}
