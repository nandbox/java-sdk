/**
 * 
 */
package com.nandbox.bots.api.outmessages;

import net.minidev.json.JSONObject;

/**
 * This class represents Output Message used to send Gif file either its in Gif
 * image format or Gif video format .
 * 
 * @author Ahmed A. El-Malatawy
 *
 */
public class GifOutMessage extends OutMessage {

	private static final String KEY_PHOTO = "photo";
	private static final String KEY_VIDEO = "video";
	private String gif;
	private GifType gifType = GifType.PHOTO;

	public enum GifType {
		PHOTO, VIDEO
	}

	/**
	 * @param gifType
	 *            it should be photo or video based on the type of Gif need to be
	 *            send , its default is photo
	 */
	public GifOutMessage(GifType gifType) {
		this.gifType = gifType;
		switch (this.gifType) {
		case PHOTO:
			this.method = OutMessageMethod.sendPhoto;
			break;
		case VIDEO:
			this.method = OutMessageMethod.sendVideo;
			break;
		default:
			this.method = OutMessageMethod.sendPhoto;
		}
	}

	@Override
	public JSONObject toJsonObject() {
		JSONObject obj = super.toJsonObject();
		if (gif != null) {
			switch (gifType) {
			case PHOTO:
				obj.put(KEY_PHOTO, gif);
				break;
			case VIDEO:
				obj.put(KEY_VIDEO, gif);
				break;
			default:
				obj.put(KEY_PHOTO, gif);
			}
		}
		return obj;
	}

	/**
	 * @return the gif
	 */
	public String getGif() {
		return gif;
	}

	/**
	 * @param gif
	 *            the gif to set
	 */
	public void setGif(String gif) {
		this.gif = gif;
	}

}
