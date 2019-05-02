/**
 * 
 */
package com.nandbox.bots.api.outmessages;

import net.minidev.json.JSONObject;

/**
 * This class represents Output Message used to send Document file .
 * 
 * @author Ahmed A. El-Malatawy
 *
 */
public class DocumentOutMessage extends OutMessage {

	private static final String KEY_DOCUMENT = "document";
	private static final String KEY_NAME = "name";
	private static final String KEY_SIZE = "size";
	private String document;
	private String name;
	private Integer size;

	public DocumentOutMessage() {
		this.method = OutMessageMethod.sendDocument;
	}

	@Override
	public JSONObject toJsonObject() {
		JSONObject obj = super.toJsonObject();
		if (document != null) {
			obj.put(KEY_DOCUMENT, document);
		}
		if (name != null) {
			obj.put(KEY_NAME, name);
		}
		if (size != null) {
			obj.put(KEY_SIZE, size);
		}
		return obj;
	}

	/**
	 * @return the size
	 */
	public Integer getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(Integer size) {
		this.size = size;
	}

	/**
	 * @return the document
	 */
	public String getDocument() {
		return document;
	}

	/**
	 * @param document
	 *            the document to set
	 */
	public void setDocument(String document) {
		this.document = document;
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

}
