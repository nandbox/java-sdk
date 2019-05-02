/**
 * 
 */
package com.nandbox.bots.api.outmessages;

import net.minidev.json.JSONObject;

/**
 * This class represents Output Message used to send Voice file .
 * 
 * @author Ahmed A. El-Malatawy
 *
 */
public class VoiceOutMessage extends OutMessage {

	private static final String KEY_VOICE = "voice";
	private static final String KEY_SIZE = "size";

	private String voice;
	private Long size;

	public VoiceOutMessage() {
		this.method = OutMessageMethod.sendVoice;
	}

	@Override
	public JSONObject toJsonObject() {
		JSONObject obj = super.toJsonObject();
		if (getVoice() != null) {
			obj.put(KEY_VOICE, getVoice());
		}
		if (getSize() != null) {
			obj.put(KEY_SIZE, getSize());
		}

		return obj;
	}

	/**
	 * @return the voice
	 */
	public String getVoice() {
		return voice;
	}

	/**
	 * @param voice
	 *            the voice to set
	 */
	public void setVoice(String voice) {
		this.voice = voice;
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

}
