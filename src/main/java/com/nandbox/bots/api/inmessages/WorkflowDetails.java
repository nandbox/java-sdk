package com.nandbox.bots.api.inmessages;

import com.nandbox.bots.api.data.User;
import com.nandbox.bots.api.data.WorkflowCell;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public class WorkflowDetails {
    private static final String KEY_WORKFLOW_DETAILS = "WorkflowDetails";
    private static final String KEY_WORKFLOW_CELL = "WorkflowCell";
    private static final String KEY_REFERENCE = "reference";
    private static final String KEY_SCREEN_ID = "screen_id";

    protected static final String KEY_USER_ID = "user_id";
    protected static final String KEY_VAPP_ID = "vapp_id";

    private WorkflowCell[] workflowCell;
    private String screenId;
    private String userId;
    private String vappId;
    private String reference;

    public WorkflowDetails(JSONObject jsonObj) {
        JSONObject obj = (JSONObject) jsonObj.get(KEY_WORKFLOW_DETAILS);

        JSONArray workflowCellArrayObj = (JSONArray) obj.get(KEY_WORKFLOW_CELL);
        if (workflowCellArrayObj != null) {

            WorkflowCell[] workflowCell = new WorkflowCell[workflowCellArrayObj.size()];
            for (int i = 0; i < workflowCellArrayObj.size(); i++) {
                workflowCell[i] = new WorkflowCell((JSONObject) workflowCellArrayObj.get(i));
            }

            this.workflowCell = workflowCell;
        }
        userId = (String) jsonObj.get(KEY_USER_ID);
        screenId = (String) jsonObj.get(KEY_SCREEN_ID);
        vappId = (String) jsonObj.get(KEY_VAPP_ID);
        reference = (String) jsonObj.get(KEY_REFERENCE);
    }

    public JSONObject toJsonObject() {

        JSONObject obj = new JSONObject();

        if (workflowCell != null) {
            JSONArray workflowCellArrayObj = new JSONArray();
            for (int i = 0; i < workflowCell.length; i++) {
                workflowCellArrayObj.add(workflowCell[i].toJsonObject());
            }
            obj.put(KEY_WORKFLOW_CELL, workflowCellArrayObj);
        }
        if(userId!= null){
            obj.put(KEY_USER_ID, userId);
        }
        if(screenId!= null){
            obj.put(KEY_SCREEN_ID, screenId);
        }
        if(vappId!= null){
            obj.put(KEY_VAPP_ID, vappId);
        }
        if(reference!= null){
            obj.put(KEY_REFERENCE, reference);
        }
        return obj;

    }

    public WorkflowCell[] getWorkflowCell() {
        return workflowCell;
    }

    public void setWorkflowCell(WorkflowCell[] workflowCell) {
        this.workflowCell = workflowCell;
    }
}
