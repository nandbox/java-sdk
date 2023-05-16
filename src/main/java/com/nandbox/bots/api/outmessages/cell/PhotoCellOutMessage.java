/**
 * 
 */
package com.nandbox.bots.api.outmessages.cell;

import com.nandbox.bots.api.outmessages.OutMessage.OutMessageMethod;

import net.minidev.json.JSONObject;

/**
 * @author ameer
 *
 */
@Deprecated
public class PhotoCellOutMessage extends CellOutMessage {

	private static final String KEY_PHOTO = "photo";
	private String photo;

	public PhotoCellOutMessage() {
		this.method = OutMessageMethod.sendCellPhoto;
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
