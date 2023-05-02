package com.nandbox.bots.api.outmessages;

import com.nandbox.bots.api.data.WorkflowCell;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SetWorkflowOutMessage extends OutMessage {
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_SCREEN_ID = "screen_id";
    private static final String KEY_WORKFLOW_CELL = "workflow_cell";
    private static final String KEY_APP_ID = "app_id";
    private static final String KEY_DISABLE_NOTIFICATION = "disable_notification";

    private String userId;
    private String appId;
    private String screenId;
    private List<WorkflowCell> workflowCell = new ArrayList<>();
    private Boolean disableNotification;

    public SetWorkflowOutMessage() {
        this.method = OutMessageMethod.setWorkflow;
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject obj = super.toJsonObject();
        JSONArray workflowCells = new JSONArray();

        for (int i = 0; i < workflowCell.size(); i++) {
            workflowCells.add(workflowCell.get(i).toJsonObject());
        }
        obj.put(KEY_WORKFLOW_CELL, workflowCells);
        obj.put(KEY_USER_ID, userId);
        obj.put(KEY_SCREEN_ID, screenId);
        obj.put(KEY_APP_ID, appId);
        obj.put(KEY_DISABLE_NOTIFICATION, disableNotification);

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

    public String getScreenId() {
        return screenId;
    }

    public void setScreenId(String screenId) {
        this.screenId = screenId;
    }

    public List<WorkflowCell> getWorkflowCell() {
        return workflowCell;
    }

    public void setWorkflowCell(List<WorkflowCell> workflowCell) {
        this.workflowCell = workflowCell;
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
