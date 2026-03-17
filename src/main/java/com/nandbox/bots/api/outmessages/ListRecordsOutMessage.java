package com.nandbox.bots.api.outmessages;

import net.minidev.json.JSONObject;

public class ListRecordsOutMessage extends OutMessage{
    String tableName;
    public ListRecordsOutMessage(){
        this.method = OutMessageMethod.extensionListDoc;
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject obj = super.toJsonObject();
        if (tableName != null) {
            obj.put("doc_type", tableName);
        }
        return obj;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
