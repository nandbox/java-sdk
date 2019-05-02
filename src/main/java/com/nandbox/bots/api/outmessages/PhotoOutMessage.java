/**
 * 
 */
package com.nandbox.bots.api.outmessages;

import net.minidev.json.JSONObject;

/**
 * This class represents Output Message used to send Photo file .
 * 
 * @author Ahmed A. El-Malatawy
 *
 */
public class PhotoOutMessage extends OutMessage {

	private static final String KEY_PHOTO = "photo";
	private String photo;

	public PhotoOutMessage() {
		this.method = OutMessageMethod.sendPhoto;
	}

	/**
	 * @return the photo file Id
	 */
	public String getPhoto() {
		return photo;
	}

	/**
	 * @param photo
	 *            the photo file Id to set
	 */
	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Override
	public JSONObject toJsonObject() {
		JSONObject obj = super.toJsonObject();
		if (photo != null) {
			obj.put(KEY_PHOTO, photo);
		}
		return obj;
	}

}
