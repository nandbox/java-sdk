package com.nandbox.bots.api.outmessages;

import net.minidev.json.JSONObject;

public class SetRecordOutMessage extends OutMessage{
    JSONObject doc;
    String tableName;
    String id;
    public SetRecordOutMessage(){
        this.method = OutMessageMethod.extensionSetDoc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public JSONObject getDoc() {
        return doc;
    }

    public void setDoc(JSONObject doc) {
        this.doc = doc;
    }
    public JSONObject toJsonObject() {
        JSONObject obj = super.toJsonObject();
        if (tableName != null) {
            obj.put("doc_type", tableName);
        }
        if (id != null) {
            obj.put("doc_id", id);
        }
        if (doc != null) {
            obj.put("doc", doc);
        }
        return obj;
    }
}
