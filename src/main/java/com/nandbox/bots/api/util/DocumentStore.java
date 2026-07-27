package com.nandbox.bots.api.util;

import com.nandbox.bots.api.Nandbox;
import com.nandbox.bots.api.outmessages.DeleteDocumentOutMessage;
import com.nandbox.bots.api.outmessages.GetDocumentOutMessage;
import com.nandbox.bots.api.outmessages.ListDocumentsOutMessage;
import com.nandbox.bots.api.outmessages.SetDocumentOutMessage;

import net.minidev.json.JSONObject;

/**
 * Per-bot storage for JSON documents, grouped into collections.
 *
 * Replaces DatabaseService. Besides the naming, the argument order is now consistent: every method
 * takes {@code (api, collection, documentId, ...)}. The old class took
 * {@code set(api, object, tableName, id)} but {@code get(api, id, tableName)}, so the two
 * middle arguments swapped between calls with no compiler error to catch it.
 *
 * Every method replies through {@code Nandbox.Callback.onDocumentResponse}.
 */
public class DocumentStore {

	private static DocumentStore instance;

	public static DocumentStore getInstance() {
		if (instance == null) {
			instance = new DocumentStore();
		}
		return instance;
	}

	/**
	 * Creates the document, or replaces it entirely if the id already exists. There is no partial
	 * update: whatever is passed becomes the stored document.
	 */
	public void setDocument(Nandbox.Api api, String collection, String documentId, JSONObject document,
			String reference) {
		SetDocumentOutMessage outMessage = new SetDocumentOutMessage();
		outMessage.setCollection(collection);
		outMessage.setDocumentId(documentId);
		outMessage.setDocument(document);
		outMessage.setRef(reference);
		api.send(outMessage);
	}

	/** Fetches one document. The reply carries a null document when the id does not exist. */
	public void getDocument(Nandbox.Api api, String collection, String documentId, String reference) {
		GetDocumentOutMessage outMessage = new GetDocumentOutMessage();
		outMessage.setCollection(collection);
		outMessage.setDocumentId(documentId);
		outMessage.setRef(reference);
		api.send(outMessage);
	}

	/** Deletes one document. The reply's ack is 0 when nothing matched. */
	public void deleteDocument(Nandbox.Api api, String collection, String documentId, String reference) {
		DeleteDocumentOutMessage outMessage = new DeleteDocumentOutMessage();
		outMessage.setCollection(collection);
		outMessage.setDocumentId(documentId);
		outMessage.setRef(reference);
		api.send(outMessage);
	}

	/** First page of a collection, unfiltered, at the server's default page size. */
	public void listDocuments(Nandbox.Api api, String collection, String reference) {
		listDocuments(api, collection, null, null, null, null, reference);
	}

	/**
	 * Lists documents, optionally filtered, sorted and paged. Any of filter, sort, pageSize and
	 * pageNumber may be null.
	 *
	 * <pre>
	 * JSONObject filter = new JSONObject();
	 * filter.put("status", "shipped");
	 * DocumentStore.getInstance().listDocuments(api, "orders", filter, null, 50, 0, reference);
	 * </pre>
	 *
	 * Operators: {@code $eq $ne $gt $gte $lt $lte $in $nin $exists $contains $like}. A bare value
	 * means equality. Filtering scans the collection, so keep pages small.
	 */
	public void listDocuments(Nandbox.Api api, String collection, JSONObject filter, JSONObject sort,
			Integer pageSize, Integer pageNumber, String reference) {
		ListDocumentsOutMessage outMessage = new ListDocumentsOutMessage();
		outMessage.setCollection(collection);
		outMessage.setFilter(filter);
		outMessage.setSort(sort);
		outMessage.setPageSize(pageSize);
		outMessage.setPageNumber(pageNumber);
		outMessage.setRef(reference);
		api.send(outMessage);
	}
}
