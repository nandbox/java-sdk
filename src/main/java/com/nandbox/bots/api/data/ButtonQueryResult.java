package com.nandbox.bots.api.data;

import net.minidev.json.JSONObject;

/**
 * ButtonQueryResult is a Java class that represents the result of a query made from a button press.
 * The class has three private instance variables of type String called latitude, longitude, and contact.
 * It also has three static final variables of type String called KEY_LATITUDE, KEY_LONGITUDE, and KEY_CONTACT.
 */
public class ButtonQueryResult {

	// Constants for JSON keys
	private static final String KEY_LATITUDE = "latitude";
	private static final String KEY_LONGITUDE = "longitude";
	private static final String KEY_CONTACT = "contact";

	// Instance variables
	private String latitude;
	private String longitude;
	private String  contact;

	/**
	 * Constructs a ButtonQueryResult object from a JSONObject.
	 * @param obj a JSONObject representing a button query result
	 */
	public ButtonQueryResult(JSONObject obj) {
		// Extract values from JSONObject and initialize instance variables
		this.latitude = String.valueOf(obj.get(KEY_LATITUDE));
		this.longitude = String.valueOf(obj.get(KEY_LONGITUDE));
		this.contact = String.valueOf((KEY_CONTACT));

	}

	/**
	 * Returns a JSONObject containing the values of latitude, longitude, and contact.
	 * @return a JSONObject representing a button query result
	 */
	public JSONObject toJsonObject() {
		// Create new JSONObject and add instance variable values if not null
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
	 * Returns the latitude instance variable.
	 * @return the latitude of the button query result
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * Sets the latitude instance variable.
	 * @param latitude the new latitude of the button query result
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * Returns the longitude instance variable.
	 * @return the longitude of the button query result
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * Sets the longitude instance variable.
	 * @param longitude the new longitude of the button query result
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * Returns the contact instance variable.
	 * @return the contact of the button query result
	 */
	public String getContact() {
		return contact;
	}

	/**
	 * Sets the contact instance variable.
	 * @param contact the new contact of the button query result
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}


}
