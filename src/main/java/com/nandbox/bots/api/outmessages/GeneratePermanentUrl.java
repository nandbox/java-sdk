package com.nandbox.bots.api.outmessages;

import net.minidev.json.JSONObject;

public class GeneratePermanentUrl extends OutMessage {

	protected static final String KEY_FILE = "file";
	protected static final String KEY_PARAM1 = "param1";
	private  String file;
	private  String param1;	
	
	public GeneratePermanentUrl() {
		this.method = OutMessageMethod.generatePermanentUrl;
	}

	@Override
	public JSONObject toJsonObject() {
		JSONObject obj = super.toJsonObject();
		if (file != null) {
			obj.put(KEY_FILE, getFile());
		}
		if (param1 != null) {
			obj.put(KEY_PARAM1, getParam1());
		}
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
	 * @return the generic parameter used as a reference  
	 */
	public String getParam1() {
		return param1;
	}

	/**
	 * @param param1 Generic parameter to set any reference 
	 */
	public void setParam1(String param1) {
		this.param1 = param1;
	}

}
