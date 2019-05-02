/**
 * 
 */
package com.nandbox.bots.api.inmessages;

import net.minidev.json.JSONObject;

/**
 * it represents Message Ack Object , the message Object is representing Server
 * Messages with method : message_ack
 * 
 * @author Ahmed A. El-Malatawy
 *
 */
public class PermanentUrl {

	private static final String KEY_FILE = "file";
	private static final String KEY_URL = "url";
	protected static final String KEY_PARAM1 = "param1";
	
	private String file;
	private String url;
	private String param1;	

	public PermanentUrl(JSONObject jsonObj) {

		this.setUrl(String.valueOf(jsonObj.get(KEY_URL)));
		this.setFile(String.valueOf(jsonObj.get(KEY_FILE)));
		this.setParam1(String.valueOf(jsonObj.get(KEY_PARAM1)));		
	}

	/**
	 * @return JSON Object of Message
	 */
	public JSONObject toJsonObject() {

		JSONObject obj = new JSONObject();

		if (url != null)
			obj.put(KEY_URL, url);
		if (file != null)
			obj.put(KEY_FILE, file);
		if (param1 != null)
			obj.put(KEY_PARAM1, param1);		
		return obj;
	}

	/**
	 * @return the file
	 */
	public String getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(String file) {
		this.file = file;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the param1
	 */
	public String getParam1() {
		return param1;
	}

	/**
	 * @param param1 the param1 to set
	 */
	public void setParam1(String param1) {
		this.param1 = param1;
	}
	
}
