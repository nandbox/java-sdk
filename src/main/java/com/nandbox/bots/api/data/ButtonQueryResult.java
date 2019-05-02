package com.nandbox.bots.api.data;

import net.minidev.json.JSONObject;

public class ButtonQueryResult {


	private static final String KEY_LATITUDE = "latitude";
	private static final String KEY_LONGITUDE = "longitude";
	private static final String KEY_CONTACT = "contact";
	
	private String latitude;
	private String longitude;
	private String  contact;
	
	public ButtonQueryResult(JSONObject obj) {

		this.latitude = String.valueOf(obj.get(KEY_LATITUDE));
		this.longitude = String.valueOf(obj.get(KEY_LONGITUDE));
		this.contact = String.valueOf((KEY_CONTACT));

	}

	public JSONObject toJsonObject() {

		JSONObject obj = new JSONObject();
		if (latitude != null)
			obj.put(KEY_LATITUDE, latitude);
		if (longitude != null)
			obj.put(KEY_LATITUDE, latitude);

		if (longitude != null)
			obj.put(KEY_LONGITUDE, longitude);

		if (contact != null)
			obj.put(KEY_CONTACT, contact);

		
		return obj;

	}
	
	
	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	/**
	 * @return the contact
	 */
	public String getContact() {
		return contact;
	}
	/**
	 * @param contact the contact to set
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}

	
}
