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
public class VideoCellOutMessage extends CellOutMessage {

	private static final String KEY_VIDEO = "video";
	private String video;

	public VideoCellOutMessage() {
		this.method = OutMessageMethod.sendCellVideo;
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
