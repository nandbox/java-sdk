package com.nandbox.bots.api.outmessages;

import net.minidev.json.JSONObject;

public class GetReordOutMessage extends OutMessage{
    String id ;
    String tableName;
    public GetReordOutMessage(){
        this.method = OutMessageMethod.extensionGetDoc;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
