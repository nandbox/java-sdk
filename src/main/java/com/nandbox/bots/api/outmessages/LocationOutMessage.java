/**
 * 
 */
package com.nandbox.bots.api.outmessages;

import net.minidev.json.JSONObject;

/**
 * This class represents Output Message used to send Location Message .
 * 
 * @author Ahmed A. El-Malatawy
 *
 */
public class LocationOutMessage extends OutMessage {

	private static final String KEY_NAME = "name";
	private static final String KEY_DETAILS = "details";
	private static final String KEY_LONGITUDE = "longitude";
	private static final String KEY_LATITUDE = "latitude";

	private String longitude;
	private String latitude;
	private String name;
	private String details;

	public LocationOutMessage() {
		this.method = OutMessageMethod.sendLocation;
	}

	@Override
	public JSONObject toJsonObject() {
		JSONObject obj = super.toJsonObject();
		if (name != null) {
			obj.put(KEY_NAME, name);
		}
		if (details != null) {
			obj.put(KEY_DETAILS, details);
		}

		if (latitude != null) {
			obj.put(KEY_LATITUDE, latitude);
		}

		if (longitude != null) {
			obj.put(KEY_LONGITUDE, longitude);
		}
		return obj;
	}

	/**
	 * @return the longitude number
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude number to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the latitude number
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude number to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the details
	 */
	public String getDetails() {
		return details;
	}

	/**
	 * @param details
	 *            the details to set
	 */
	public void setDetails(String details) {
		this.details = details;
	}
}
