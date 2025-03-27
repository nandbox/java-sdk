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
	private static final String KEY_APP_ID = "app_id";


	private String file;
	private String url;
	private String param1;
	private Long appId;

	public PermanentUrl(JSONObject jsonObj) {

		this.setUrl(String.valueOf(jsonObj.get(KEY_URL)));
		this.setFile(String.valueOf(jsonObj.get(KEY_FILE)));
		this.setParam1(String.valueOf(jsonObj.get(KEY_PARAM1)));
		this.appId =jsonObj.get(KEY_APP_ID) != null
				? Long.parseLong(String.valueOf(jsonObj.get(KEY_APP_ID)))
				: 0;;
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
		if (appId!=null){
			obj.put(KEY_APP_ID,appId);
		}
		return obj;
	}

	/**
	 * @return the file
	 */
	public String getFile() {
		return file;
	}
	public Long getAppId(){
		return appId;
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
