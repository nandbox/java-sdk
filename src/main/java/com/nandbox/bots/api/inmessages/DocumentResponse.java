package com.nandbox.bots.api.inmessages;

import java.util.ArrayList;
import java.util.List;

import com.nandbox.bots.api.util.Utils;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 * Reply to any of the document store methods.
 *
 * Replaces ExtensionDocResponse. Two things changed beyond the naming:
 *
 * <ul>
 * <li>The list reply used to be parsed with {@code parse(String.valueOf(jsonArray))}, which
 * stringifies the array and re-parses it. Entries came back as strings rather than objects.
 * The server now sends real objects and this class reads them directly.</li>
 * <li>List replies carry the document id alongside each payload, so a caller no longer has to
 * store the id inside the document to know which one it is looking at.</li>
 * </ul>
 */
public class DocumentResponse {

	private static final String KEY_COLLECTION = "collection";
	private static final String KEY_DOCUMENT_ID = "document_id";
	private static final String KEY_DOCUMENT = "document";
	private static final String KEY_DOCUMENTS = "documents";
	private static final String KEY_REFERENCE = "reference";
	private static final String KEY_REF = "ref";
	private static final String KEY_APP_ID = "app_id";
	private static final String KEY_METHOD = "method";
	private static final String KEY_ACK = "ack";
	private static final String KEY_PAGE_NUMBER = "page_number";
	private static final String KEY_EOP = "eop";

	private String collection;
	private String documentId;
	private JSONObject document;
	private List<Entry> documents;
	private String reference;
	private String appId;
	private String method;
	private Integer ack;
	private Integer pageNumber;
	private Boolean endOfPages;

	public DocumentResponse(JSONObject obj) {
		this.collection = asString(obj.get(KEY_COLLECTION));
		this.documentId = asString(obj.get(KEY_DOCUMENT_ID));
		this.appId = asString(obj.get(KEY_APP_ID));
		this.method = asString(obj.get(KEY_METHOD));

		Object ref = obj.get(KEY_REFERENCE);
		this.reference = asString(ref != null ? ref : obj.get(KEY_REF));

		Object doc = obj.get(KEY_DOCUMENT);
		if (doc instanceof JSONObject) {
			this.document = (JSONObject) doc;
		}

		if (obj.get(KEY_ACK) != null) {
			this.ack = Utils.getInteger(obj.get(KEY_ACK));
		}
		if (obj.get(KEY_PAGE_NUMBER) != null) {
			this.pageNumber = Utils.getInteger(obj.get(KEY_PAGE_NUMBER));
		}
		if (obj.get(KEY_EOP) != null) {
			this.endOfPages = Boolean.valueOf(String.valueOf(obj.get(KEY_EOP)));
		}

		Object list = obj.get(KEY_DOCUMENTS);
		if (list instanceof JSONArray) {
			this.documents = new ArrayList<Entry>();
			for (Object item : (JSONArray) list) {
				if (item instanceof JSONObject) {
					JSONObject entry = (JSONObject) item;
					Object payload = entry.get(KEY_DOCUMENT);
					this.documents.add(new Entry(asString(entry.get(KEY_DOCUMENT_ID)),
							payload instanceof JSONObject ? (JSONObject) payload : null));
				}
			}
		}
	}

	private static String asString(Object value) {
		return value == null ? null : String.valueOf(value);
	}

	/** Collection the document or documents belong to. */
	public String getCollection() {
		return collection;
	}

	/** Set for get, set and delete replies; null for a list reply. */
	public String getDocumentId() {
		return documentId;
	}

	/** The document, for get and set replies. Null when the id was not found. */
	public JSONObject getDocument() {
		return document;
	}

	/** One page of documents, for a list reply. Null for the single-document methods. */
	public List<Entry> getDocuments() {
		return documents;
	}

	public String getReference() {
		return reference;
	}

	public String getAppId() {
		return appId;
	}

	public String getMethod() {
		return method;
	}

	/** Rows affected, for set and delete. Zero from a delete means nothing matched. */
	public Integer getAck() {
		return ack;
	}

	/** Page this reply represents, for a list reply. */
	public Integer getPageNumber() {
		return pageNumber;
	}

	/** True when there are no further pages. */
	public Boolean isEndOfPages() {
		return endOfPages;
	}

	/** One entry of a list reply: the document and the id it is stored under. */
	public static class Entry {

		private final String documentId;
		private final JSONObject document;

		public Entry(String documentId, JSONObject document) {
			this.documentId = documentId;
			this.document = document;
		}

		public String getDocumentId() {
			return documentId;
		}

		public JSONObject getDocument() {
			return document;
		}
	}
}
