package com.nandbox.bots.api.data;

import net.minidev.json.JSONObject;

/**
 * This class represents incoming Message used to get Contact Message .
 * 
 * @author Hossam
 *
 */

/**
 * Represents a contact with a name and a phone number.
 */
public class Contact {

	private static final String KEY_NAME = "name";
	private static final String KEY_PHONE_NUMBER = "phone_number";

	private String name;
	private String phoneNumber;

	/**
	 * Creates a new Contact object from a JSONObject.
	 *
	 * @param obj the JSONObject to create the contact from
	 */
	public Contact(JSONObject obj) {
		this.name = String.valueOf(obj.get(KEY_NAME));
		this.phoneNumber = String.valueOf(obj.get(KEY_PHONE_NUMBER));
	}

	/**
	 * Returns a JSONObject representation of the contact.
	 *
	 * @return the JSONObject representation of the contact
	 */
	public JSONObject toJsonObject() {
		JSONObject obj = new JSONObject();
		if (name != null)
			obj.put(KEY_NAME, name);
		if (phoneNumber != null)
			obj.put(KEY_PHONE_NUMBER, phoneNumber);
		return obj;

	}

	/**
	 * Returns a JSON-formatted string representation of the contact.
	 *
	 * @return the JSON-formatted string representation of the contact
	 */
	@Override
	public String toString() {
		StringBuilder  outStrBuf = new StringBuilder();
		outStrBuf.append("{\n");
		if (name != null)
			outStrBuf.append("\"" + KEY_NAME + "\":\"" + name + "\n");
		if (phoneNumber != null)
			outStrBuf.append("\"" + KEY_PHONE_NUMBER + "\":\"" + phoneNumber + "\n");

		outStrBuf.append("}");
		return outStrBuf.toString();
	}

	/**
	 * Returns the name of the contact.
	 *
	 * @return the name of the contact
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the contact.
	 *
	 * @param name the name to set for the contact
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the phone number of the contact.
	 *
	 * @return the phone number of the contact
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * Sets the phone number of the contact.
	 *
	 * @param phoneNumber the phone number to set for the contact
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
