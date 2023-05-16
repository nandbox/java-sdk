package com.nandbox.bots.api.outmessages.cell;

import com.nandbox.bots.api.outmessages.OutMessage;

import net.minidev.json.JSONObject;


/**
 * @author ameer
 *
 */
@Deprecated
public class CellOutMessage extends OutMessage {
	
	protected static final String KEY_USER_ID = "user_id";
	protected static final String KEY_SCREEN_ID = "screen_id";
	protected static final String KEY_CELL_ID = "cell_id";
	
	protected String userId;
	protected String screenId;
	protected String cellId;
	
	public JSONObject toJsonObject() {
		JSONObject obj = super.toJsonObject();
		
		if(userId != null)
			obj.put(KEY_USER_ID, userId);
		if(screenId != null)
			obj.put(KEY_SCREEN_ID, screenId);
		if(cellId != null)
			obj.put(KEY_CELL_ID, cellId);
		return obj;
	}
	
	
	
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the screenId
	 */
	public String getScreenId() {
		return screenId;
	}
	/**
	 * @param screenId the screenId to set
	 */
	public void setScreenId(String screenId) {
		this.screenId = screenId;
	}
	/**
	 * @return the cellId
	 */
	public String getCellId() {
		return cellId;
	}
	/**
	 * @param cellId the cellId to set
	 */
	public void setCellId(String cellId) {
		this.cellId = cellId;
	}

}
