package com.nandbox.bots.api.outmessages;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public class UpdateMenuCell extends OutMessage {
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_MENU_ID = "menu_id";
    private static final String KEY_CELLS = "cells";
    private static final String KEY_APP_ID = "app_id";
    private static final String KEY_DISABLE_NOTIFICATION = "disable_notification";

    private String userId;
    private String appId;
    private String menuId;
    private JSONArray cells = new JSONArray();
    private Boolean disableNotification;

    public UpdateMenuCell() {
        this.method = OutMessageMethod.updateMenuCell;
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject obj = super.toJsonObject();


        obj.put(KEY_CELLS, cells);
        obj.put(KEY_USER_ID, userId);
        obj.put(KEY_MENU_ID, menuId);
        obj.put(KEY_APP_ID, appId);

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

    @Override
    public Boolean getDisableNotification() {
        return disableNotification;
    }

    @Override
    public void setDisableNotification(Boolean disableNotification) {
        this.disableNotification = disableNotification;
    }
}
