package com.nandbox.bots.api.data;

import net.minidev.json.JSONObject;

public class WebhookBody {
    String KEY_REF = "ref";
    String KEY_APP_ID = "app_id";
    String KEY_METHOD = "method";

    String ref;
    String appId;
    JSONObject body;
    String method;
    public WebhookBody(JSONObject obj){
        if (obj.containsKey(KEY_REF))  {
            this.ref = String.valueOf(obj.remove(KEY_REF));
        }
        if (obj.containsKey(KEY_APP_ID)) {
            this.appId = String.valueOf(obj.remove(KEY_APP_ID));
        }
        if (obj.containsKey(KEY_METHOD)) {
            this.method = String.valueOf(obj.remove(KEY_METHOD));
        }
        this.body = obj;
    }
    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setBody(JSONObject body) {
        this.body = body;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getRef() {
        return ref;
    }

    public String getAppId() {
        return appId;
    }

    public JSONObject getBody() {
        return body;
    }
}
