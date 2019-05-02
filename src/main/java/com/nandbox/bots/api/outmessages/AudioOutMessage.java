/**
 * 
 */
package com.nandbox.bots.api.outmessages;

import net.minidev.json.JSONObject;

/**
 * This class represents Output Message used to send Audio file .
 * 
 * @author Ahmed A. El-Malatawy
 *
 */

public class AudioOutMessage extends OutMessage {

	private static final String KEY_AUDIO = "audio";
	private static final String KEY_PERFORMER = "performer";
	private static final String KEY_TITLE = "title";

	private String audio;
	private String performer;
	private String title;

	public AudioOutMessage() {
		this.method = OutMessageMethod.sendAudio;
	}

	@Override
	public JSONObject toJsonObject() {
		JSONObject obj = super.toJsonObject();
		if (audio != null) {
			obj.put(KEY_AUDIO, audio);
		}
		if (performer != null) {
			obj.put(KEY_PERFORMER, performer);
		}

		if (title != null) {
			obj.put(KEY_TITLE, title);
		}
		return obj;
	}

	/**
	 * @return the audio
	 */
	public String getAudio() {
		return audio;
	}

	/**
	 * @param audio
	 *            the audio to set
	 */
	public void setAudio(String audio) {
		this.audio = audio;
	}

	/**
	 * @return the name of performer
	 */
	public String getPerformer() {
		return performer;
	}

	/**
	 * @param performer
	 *            the performer name to set
	 */
	public void setPerformer(String performer) {
		this.performer = performer;
	}

	/**
	 * @return the audio title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the audio title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

}
