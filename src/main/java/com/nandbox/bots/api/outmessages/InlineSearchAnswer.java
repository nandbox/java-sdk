package com.nandbox.bots.api.outmessages;

import java.util.ArrayList;

import com.nandbox.bots.api.data.Result;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 * @author Hossam Mohamed
 *
 */
public class InlineSearchAnswer extends OutMessage {

	private static final String KEY_RESULTS = "results";
	private static final String KEY_SEARCH_ID = "search_id";
	private static final String KEY_NEXT_OFFSET = "next_offset";	

	private ArrayList<Result> results;
	private Integer searchId;
	private String nextOffset;

	public InlineSearchAnswer() {

		this.method = OutMessageMethod.inlineSearchAnswer;
	}

	@Override
	public JSONObject toJsonObject() {
		JSONObject obj = super.toJsonObject();
		if (results != null) {
			JSONArray resultArrayObj = new JSONArray();
			for (int i = 0; i < results.size(); i++) {
				resultArrayObj.add(results.get(i).toJsonObject());
			}
			obj.put(KEY_RESULTS, resultArrayObj);
		}

		if (searchId != null) {
			obj.put(KEY_SEARCH_ID, searchId);
		}
		
		if (nextOffset != null) {
			obj.put(KEY_NEXT_OFFSET, nextOffset);
		}

		return obj;
	}

	/**
	 * @return the results
	 */
	public ArrayList<Result> getResults() {
		return results;
	}

	/**
	 * @param results
	 *            the results to set
	 */
	public void setResults(ArrayList<Result> results) {
		this.results = results;
	}

	/**
	 * @return the searchId
	 */
	public Integer getSearchId() {
		return searchId;
	}

	/**
	 * @param searchId
	 *            the searchId to set
	 */
	public void setSearchId(Integer searchId) {
		this.searchId = searchId;
	}

	/**
	 * @return the nextOffset
	 */
	public String getNextOffset() {
		return nextOffset;
	}

	/**
	 * @param nextOffset the nextOffset to set
	 */
	public void setNextOffset(String nextOffset) {
		this.nextOffset = nextOffset;
	}

}
