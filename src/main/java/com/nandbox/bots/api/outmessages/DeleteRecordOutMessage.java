package com.nandbox.bots.api.outmessages;

import net.minidev.json.JSONObject;

public class DeleteRecordOutMessage extends OutMessage{
    String id;
    String tableName;
    public DeleteRecordOutMessage(){
        this.method = OutMessageMethod.extensionDeleteDoc;
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject obj = super.toJsonObject();
        if (id != null) {
            obj.put("doc_id", id);
        }
        if (tableName != null) {
            obj.put("doc_type", tableName);
        }
        return obj;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
