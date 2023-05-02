package com.nandbox.bots.api.inmessages;

import com.nandbox.bots.api.data.User;
import com.nandbox.bots.api.data.WorkflowCell;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public class WorkflowDetails {
    private static final String KEY_WORKFLOW_DETAILS = "WorkflowDetails";
    private static final String KEY_WORKFLOW_CELL = "WorkflowCell";

    private WorkflowCell[] workflowCell;

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
        return obj;

    }

    public WorkflowCell[] getWorkflowCell() {
        return workflowCell;
    }

    public void setWorkflowCell(WorkflowCell[] workflowCell) {
        this.workflowCell = workflowCell;
    }
}
