package com.nandbox.bots.api.data;

import com.nandbox.bots.api.util.Utils;

import net.minidev.json.JSONObject;

/**
 * @author Hossam Mohamed
 *
 */
public class Result {

	private static final String KEY_ID = "id";
	private static final String KEY_CAPTION = "caption";
	private static final String KEY_TITLE = "title";
	private static final String KEY_DESCRIPTION = "description";
	private static final String KEY_URL = "url";
	private static final String KEY_TYPE = "type";
	private static final String KEY_THUMB_URL = "thumb_url";
	private static final String KEY_WIDTH = "width";
	private static final String KEY_HEIGHT = "height";
	

	private String id;
	private String caption;
	private String title;
	private String description;
	private String url;
	private String thumbUrl;
	private String type;
	private Long width;
	private Long height;
	

	public Result() {
	}

	public Result(JSONObject obj) {
		this.id = (String) obj.get(KEY_ID);
		this.caption = (String) obj.get(KEY_CAPTION);
		this.title = (String) obj.get(KEY_TITLE);
		this.description = (String) obj.get(KEY_DESCRIPTION);
		this.url = (String) obj.get(KEY_URL);		
		this.type = (String) obj.get(KEY_TYPE);
		this.thumbUrl = (String) obj.get(KEY_THUMB_URL);
		this.id = (String) obj.get(KEY_ID);
		this.width = Utils.getLong(obj.get(KEY_WIDTH));
		this.height = Utils.getLong(obj.get(KEY_HEIGHT));		
	}

	public JSONObject toJsonObject() {
		JSONObject obj = new JSONObject();

		if (id != null)
			obj.put(KEY_ID, id);
		if (caption != null)
			obj.put(KEY_CAPTION, caption);
		if (title != null)
			obj.put(KEY_TITLE, title);
		if (description != null)
			obj.put(KEY_DESCRIPTION, description);
		if (url != null)
			obj.put(KEY_URL, url);
		if (type != null)
			obj.put(KEY_TYPE, type);
		if (thumbUrl != null)
			obj.put(KEY_THUMB_URL, thumbUrl);	
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
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the caption
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * @param caption the caption to set
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the thumbUrl
	 */
	public String getThumbUrl() {
		return thumbUrl;
	}

	/**
	 * @param thumbUrl the thumbUrl to set
	 */
	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

	/**
	 * @return the width
	 */
	public Long getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(Long width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public Long getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(Long height) {
		this.height = height;
	}
}
