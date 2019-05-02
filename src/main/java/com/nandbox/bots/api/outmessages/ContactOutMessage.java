/**
 * 
 */
package com.nandbox.bots.api.outmessages;

import net.minidev.json.JSONObject;

/**
 * 
 * This class represents Output Message used to send Contact Message .
 * 
 * @author Ahmed A. El-Malatawy
 *
 */
public class ContactOutMessage extends OutMessage {

	private static final String KEY_NAME = "name";
	private static final String KEY_PHONE_NUMBER = "phone_number";

	private String name;
	private String phoneNumber;

	public ContactOutMessage() {
		this.method = OutMessageMethod.sendContact;
	}

	@Override
	public JSONObject toJsonObject() {
		JSONObject obj = super.toJsonObject();
		if (name != null) {
			obj.put(KEY_NAME, name);
		}
		if (phoneNumber != null) {
			obj.put(KEY_PHONE_NUMBER, phoneNumber);
		}
		return obj;
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
