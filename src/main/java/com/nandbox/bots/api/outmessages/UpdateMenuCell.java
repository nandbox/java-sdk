package com.nandbox.bots.api.outmessages;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public class UpdateMenuCell extends OutMessage {
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_MENU_ID = "menu_id";
    private static final String KEY_CELLS = "cells";
    private static final String KEY_APP_ID = "app_id";

    private String userId;
    private String appId;
    private String menuId;
    private JSONArray cells = new JSONArray();

    public UpdateMenuCell() {
        this.method = OutMessageMethod.updateMenuCell;
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject obj = super.toJsonObject();


        if (cells != null) {
            obj.put(KEY_CELLS, cells);
        }
        if (userId != null) {
            obj.put(KEY_USER_ID, userId);
        }
        if (menuId != null) {
            obj.put(KEY_MENU_ID, menuId);
        }
        // Only override the app_id written by super.toJsonObject() when this
        // subclass actually carries one.
        if (appId != null) {
            obj.put(KEY_APP_ID, appId);
        }

        return obj;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public JSONArray getCells() {
        return cells;
    }

    public void setCells(JSONArray cells) {
        this.cells = cells;
    }
}
