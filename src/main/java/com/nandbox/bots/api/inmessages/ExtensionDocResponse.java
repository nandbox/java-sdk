
package com.nandbox.bots.api.inmessages;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

public class ExtensionDocResponse {
    String id;
    String tableName;
    JSONObject doc;
    String ref;
    String appId;
    String method;
    public ExtensionDocResponse(JSONObject obj) {
        if (obj.containsKey("doc_id")) {
            this.id = (String) obj.get("doc_id");
        }
        if (obj.containsKey("doc_type")) {
            this.tableName = (String) obj.get("doc_type");
        }
        if (obj.containsKey("doc")) {
            try{
                this.doc = (JSONObject) (new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE)).parse(String.valueOf(obj.get("doc")));
            } catch (Exception e) {
                this.doc = new JSONObject();
            }
        }
        if (obj.containsKey("ref")) {
            this.ref = String.valueOf( obj.get("ref"));
        }
        if (obj.containsKey("app_id")) {
            this.appId = String.valueOf( obj.get("app_id"));
        }
        if (obj.containsKey("method")) {
            this.method = String.valueOf(obj.get("method"));
        }
    }
    public String getTableName() {
        return tableName;
    }

    public String getId() {
        return id;
    }

    public String getAppId() {
        return appId;
    }

    public JSONObject getDoc() {
        return doc;
    }

    public String getRef() {
        return ref;
    }

    public String getMethod() {
        return method;
    }
}
