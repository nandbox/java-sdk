package com.nandbox.bots.api.data;

import net.minidev.json.JSONObject;

/**
 * This class represents incoming Message used to get Location Message .
 * 
 * @author Hossam
 *
 */
public class Location {

	private static final String KEY_NAME = "name";
	private static final String KEY_DETAILS = "details";
	private static final String KEY_LONGITUDE = "longitude";
	private static final String KEY_LATITUDE = "latitude";

	private String longitude;
	private String latitude;
	private String name;
	private String details;

	public Location(JSONObject obj) {
		this.name = String.valueOf(obj.get(KEY_NAME));
		this.details = String.valueOf(obj.get(KEY_DETAILS));
		this.longitude = String.valueOf(obj.get(KEY_LONGITUDE));
		this.latitude = String.valueOf(obj.get(KEY_LATITUDE));
	}

	public JSONObject toJsonObject() {
		JSONObject obj = new JSONObject();
		if (name != null)
			obj.put(KEY_NAME, name);
		if (details != null)
			obj.put(KEY_DETAILS, details);
		if (longitude != null)
			obj.put(KEY_LONGITUDE, longitude);
		if (latitude != null)
			obj.put(KEY_LATITUDE, latitude);
		return obj;

	}

	@Override
	public String toString() {
		StringBuilder outStrBuf = new StringBuilder();
		outStrBuf.append("{\n");
		if (name != null)
			outStrBuf.append("\"" + KEY_NAME + "\":\"" + name + "\n");
		if (details != null)
			outStrBuf.append("\"" + KEY_DETAILS + "\":\"" + details + "\n");
		if (latitude != null)
			outStrBuf.append("\"" + KEY_LATITUDE + "\":\"" + latitude + "\n");
		if (longitude != null)
			outStrBuf.append("\"" + KEY_LONGITUDE + "\":\"" + longitude + "\n");

		outStrBuf.append("}");
		return outStrBuf.toString();
	}

	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
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
