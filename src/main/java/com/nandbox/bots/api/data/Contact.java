package com.nandbox.bots.api.data;

import net.minidev.json.JSONObject;

/**
 * This class represents incoming Message used to get Contact Message .
 * 
 * @author Hossam
 *
 */
public class Contact {

	private static final String KEY_NAME = "name";
	private static final String KEY_PHONE_NUMBER = "phone_number";

	private String name;
	private String phoneNumber;

	public Contact(JSONObject obj) {
		this.name = String.valueOf(obj.get(KEY_NAME));
		this.phoneNumber = String.valueOf(obj.get(KEY_PHONE_NUMBER));
	}

	public JSONObject toJsonObject() {
		JSONObject obj = new JSONObject();
		if (name != null)
			obj.put(KEY_NAME, name);
		if (phoneNumber != null)
			obj.put(KEY_PHONE_NUMBER, phoneNumber);
		return obj;

	}

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
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber
	 *            the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
