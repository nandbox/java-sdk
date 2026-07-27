package com.nandbox.bots.api.outmessages;

import net.minidev.json.JSONObject;

/**
 * Stores a document in a collection, creating it or replacing it in place.
 *
 * Replaces SetRecordOutMessage. The "table" vocabulary it used implied columns and a schema that
 * the document store does not have.
 */
public class SetDocumentOutMessage extends OutMessage {

	protected static final String KEY_COLLECTION = "collection";
	protected static final String KEY_DOCUMENT_ID = "document_id";
	protected static final String KEY_DOCUMENT = "document";

	private String collection;
	private String documentId;
	private JSONObject document;

	public SetDocumentOutMessage() {
		this.method = OutMessageMethod.setDocument;
	}

	@Override
	public JSONObject toJsonObject() {
		JSONObject obj = super.toJsonObject();
		if (collection != null) {
			obj.put(KEY_COLLECTION, collection);
		}
		if (documentId != null) {
			obj.put(KEY_DOCUMENT_ID, documentId);
		}
		if (document != null) {
			obj.put(KEY_DOCUMENT, document);
		}
		return obj;
	}

	public String getCollection() {
		return collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public JSONObject getDocument() {
		return document;
	}

	public void setDocument(JSONObject document) {
		this.document = document;
	}
}
