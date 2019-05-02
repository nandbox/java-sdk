/**
 * 
 */
package com.nandbox.bots.api.outmessages;

import net.minidev.json.JSONObject;

/**
 * This class represents Output Message used to send Video file .
 * 
 * @author Ahmed A. El-Malatawy
 *
 */
public class VideoOutMessage extends OutMessage {

	private static final String KEY_VIDEO = "video";
	private String video;

	public VideoOutMessage() {
		this.method = OutMessageMethod.sendVideo;
	}

	@Override
	public JSONObject toJsonObject() {
		JSONObject obj = super.toJsonObject();
		if (video != null) {
			obj.put(KEY_VIDEO, video);
		}
		return obj;
	}

	/**
	 * @return the video
	 */
	public String getVideo() {
		return video;
	}

	/**
	 * @param video
	 *            the video to set
	 */
	public void setVideo(String video) {
		this.video = video;
	}

}
