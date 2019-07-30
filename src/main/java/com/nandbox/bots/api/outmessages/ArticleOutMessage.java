package com.nandbox.bots.api.outmessages;

import net.minidev.json.JSONObject;

/**
 * This class represents Output Message used to send Voice file .
 * 
 * @author Hossam Mohamed
 *
 */

public class ArticleOutMessage extends OutMessage {

	private static final String KEY_URL = "url";
	private static final String KEY_title = "title";
	private static final String KEY_description = "description";
	private static final String KEY_photo = "photo";
	private static final String KEY_photo_url = "photo_url";

	private String url;
	private String title;
	private String description;
	private String photo;
	private String photoUrl;

	public ArticleOutMessage() {
		this.method = OutMessageMethod.sendArticle;
	}

	@Override
	public JSONObject toJsonObject() {
		JSONObject obj = super.toJsonObject();

		if (url != null) {
			obj.put(KEY_URL, url);
		}
		if (title != null) {
			obj.put(KEY_title, title);
		}

		if (description != null) {
			obj.put(KEY_description, description);
		}

		if (photo != null) {
			obj.put(KEY_photo, photo);
		}

		if (photoUrl != null) {
			obj.put(KEY_photo_url, photoUrl);
		}
		return obj;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

}
