package com.nandbox.bots.api.outmessages;

import java.util.ArrayList;
import java.util.List;

import com.nandbox.bots.api.data.Data;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 * @author Hossam
 *
 */
public class AddBlacklistPatternsOutMessage extends OutMessage {

	protected static final String KEY_DATA = "patterns";

	private List<Data> data = new ArrayList<>();

	public AddBlacklistPatternsOutMessage() {
		this.method = OutMessageMethod.addBlacklistPatterns;
	}

	@Override
	public JSONObject toJsonObject() {
		JSONObject obj = super.toJsonObject();
		if (data != null) {
			JSONArray patterns = new JSONArray();
			for (Data pattern : data) {
				if (pattern != null) {
					patterns.add(pattern.toJsonObject());
				}
			}
			obj.put(KEY_DATA, patterns);
		}

		return obj;
	}

	public List<Data> getData() {
		return data;
	}

	public void setData(List<Data> data) {
		this.data = data;
	}
}
