/**
 * 
 */
package com.nandbox.bots.api.data;


import com.nandbox.bots.api.util.Utils;

import net.minidev.json.JSONObject;

/**
 * This class represents incoming Message used to get Audio Message .
 * 
 * @author Ahmed A. El-Malatawy
 *
 */
/**

 The Audio class represents an audio file and contains information about the audio file such as its ID, title, performer,

 size and duration.
 */
public class Audio {

	/**

	 The key used to represent the ID of the audio file in JSON objects.
	 */
	public static final String KEY_ID = "id";
	/**

	 The key used to represent the title of the audio file in JSON objects.
	 */
	public static final String KEY_TITLE = "title";
	/**

	 The key used to represent the performer of the audio file in JSON objects.
	 */
	public static final String KEY_PERFORMER = "performer";
	/**

	 The key used to represent the size of the audio file in JSON objects.
	 */
	public static final String KEY_SIZE = "size";
	/**

	 The key used to represent the duration of the audio file in JSON objects.
	 */
	public static final String KEY_DURATION = "duration";
	/**

	 The ID of the audio file.
	 */
	private String id;
	/**

	 The title of the audio file.
	 */
	private String title;
	/**

	 The performer of the audio file.
	 */
	private String performer;
	/**

	 The size of the audio file in bytes.
	 */
	private Long size;
	/**

	 The duration of the audio file in seconds.
	 */
	private Integer duration;
	/**

	 Constructs an Audio object from a JSONObject.
	 @param obj the JSONObject from which to construct the Audio object.
	 */
	public Audio(JSONObject obj) {
		this.id = String.valueOf(obj.get(KEY_ID));
		this.title = String.valueOf(obj.get(KEY_TITLE));
		this.performer = String.valueOf(obj.get(KEY_PERFORMER));
		this.size = Utils.getLong(obj.get(KEY_SIZE));
		this.duration = Utils.getInteger(obj.get(KEY_DURATION));
	}
	/**

	 Returns a JSONObject representing the Audio object.

	 @return a JSONObject representing the Audio object.
	 */
	public JSONObject toJsonObject() {
		JSONObject obj = new JSONObject();

		if (id != null)
			obj.put(KEY_ID, id);

		if (title != null)
			obj.put(KEY_TITLE, title);

		if (performer != null)
			obj.put(KEY_PERFORMER, performer);

		if (size != null)
			obj.put(KEY_SIZE, size);

		if (duration != null)
			obj.put(KEY_DURATION, duration);

		return obj;

	}

	/**

	 Returns the ID of the audio file.
	 @return the ID of the audio file.
	 */
	public String getId() {
		return id;
	}

	/**

	 Sets the ID of the audio file.
	 @param id the ID of the audio file to be set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**

	 Returns the size of the audio file in bytes.
	 @return the size of the audio file in bytes.
	 */
	public Long getSize() {
		return size;
	}

	/**

	 Sets the size of the audio file in bytes.
	 @param size the size of the audio file to be set.
	 */
	public void setSize(Long size) {
		this.size = size;
	}

	/**

	 Returns the duration of the audio file in seconds.
	 @return the duration of the audio file in seconds.
	 */
	public Integer getDuration() {
		return duration;
	}

	/**

	 Sets the duration of the audio file in seconds.
	 @param duration the duration of the audio file to be set.
	 */
	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	/**

	 Returns the title of the audio file.
	 @return the title of the audio file.
	 */
	public String getTitle() {
		return title;
	}

	/**

	 Sets the title of the audio file.
	 @param title the title of the audio file to be set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**

	 Returns the performer name of the audio file.
	 @return the performer name of the audio file.
	 */
	public String getPerformer() {
		return performer;
	}

	/**

	 Sets the performer name of the audio file.
	 @param performer the performer name of the audio file to be set.
	 */
	public void setPerformer(String performer) {
		this.performer = performer;
	}

}
