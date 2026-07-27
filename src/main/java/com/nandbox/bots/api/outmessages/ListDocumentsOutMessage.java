package com.nandbox.bots.api.outmessages;

import net.minidev.json.JSONObject;

/**
 * Lists documents in a collection, optionally filtered, sorted and paged.
 *
 * Replaces ListRecordsOutMessage, which could only fetch an entire collection in one unbounded
 * response.
 *
 * <pre>
 * ListDocumentsOutMessage msg = new ListDocumentsOutMessage();
 * msg.setCollection("orders");
 *
 * JSONObject filter = new JSONObject();
 * filter.put("status", "shipped");            // bare value means equality
 * JSONObject total = new JSONObject();
 * total.put("$gte", 100);
 * filter.put("total", total);
 * msg.setFilter(filter);
 *
 * JSONObject sort = new JSONObject();
 * sort.put("created_at", -1);                 // -1 descending, 1 ascending
 * msg.setSort(sort);
 *
 * msg.setPageSize(50);
 * msg.setPageNumber(0);
 * </pre>
 *
 * Supported operators: {@code $eq $ne $gt $gte $lt $lte $in $nin $exists $contains $like}.
 */
public class ListDocumentsOutMessage extends OutMessage {

	protected static final String KEY_COLLECTION = "collection";
	protected static final String KEY_FILTER = "filter";
	protected static final String KEY_SORT = "sort";
	protected static final String KEY_PAGE_SIZE = "page_size";
	protected static final String KEY_PAGE_NUMBER = "page_number";

	private String collection;
	private JSONObject filter;
	private JSONObject sort;
	private Integer pageSize;
	private Integer pageNumber;

	public ListDocumentsOutMessage() {
		this.method = OutMessageMethod.listDocuments;
	}

	@Override
	public JSONObject toJsonObject() {
		JSONObject obj = super.toJsonObject();
		if (collection != null) {
			obj.put(KEY_COLLECTION, collection);
		}
		if (filter != null) {
			obj.put(KEY_FILTER, filter);
		}
		if (sort != null) {
			obj.put(KEY_SORT, sort);
		}
		if (pageSize != null) {
			obj.put(KEY_PAGE_SIZE, pageSize);
		}
		if (pageNumber != null) {
			obj.put(KEY_PAGE_NUMBER, pageNumber);
		}
		return obj;
	}

	public String getCollection() {
		return collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public JSONObject getFilter() {
		return filter;
	}

	public void setFilter(JSONObject filter) {
		this.filter = filter;
	}

	public JSONObject getSort() {
		return sort;
	}

	public void setSort(JSONObject sort) {
		this.sort = sort;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	/** Server default is 50 and the hard ceiling is 200; larger values are clamped. */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	/** Zero-based. */
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
}
