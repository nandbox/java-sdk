package com.nandbox.bots.api.outmessages;

import net.minidev.json.JSONObject;

/**
 * Deletes one document by its id.
 *
 * Replaces DeleteRecordOutMessage.
 */
public class DeleteDocumentOutMessage extends OutMessage {

	protected static final String KEY_COLLECTION = "collection";
	protected static final String KEY_DOCUMENT_ID = "document_id";

	private String collection;
	private String documentId;

	public DeleteDocumentOutMessage() {
		this.method = OutMessageMethod.deleteDocument;
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
}
