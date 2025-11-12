package com.nandbox.bots.api.outmessages;


import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 * 
 * This class represents Output Message used to send Navigation Button
 * 
 * @author Hossam Mohamed
 *
 */
public class SetNavigationButtonOutMessage extends OutMessage {

	protected static final String KEY_NAVIGATION_BUTTONS = "navigation_button";
    protected static final String KEY_NAV_TYPE = "nav_type";
    protected static final String KEY_MENU_OPEN = "menu_open";

    private String navigation_button;
    private String type;
    private Integer isOpen = 0;

	public SetNavigationButtonOutMessage() {
		this.method = OutMessageMethod.setNavigationButton;
	}

	@Override
	public JSONObject toJsonObject() {

		JSONObject obj = super.toJsonObject();
		if (navigation_button != null) {

			obj.put(KEY_NAVIGATION_BUTTONS, navigation_button);
		}
        if (type!=null){
            obj.put(KEY_NAV_TYPE,type);
        }
        if (isOpen!=0){
            obj.put(KEY_MENU_OPEN,isOpen);
        }
		return obj;
	}

	public String getNavigation_button() {
		return navigation_button;
	}

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setNavigation_button(String navigation_button) {
		this.navigation_button = navigation_button;
	}
    public void setType(String type) {
        this.type = type;
    }



}
