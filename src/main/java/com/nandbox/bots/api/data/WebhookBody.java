package com.nandbox.bots.api.data;

import net.minidev.json.JSONObject;

public class WebhookBody {
    // These were mutable instance fields, so every WebhookBody carried its own
    // writable copy of what are really compile-time constants.
    private static final String KEY_REF = "ref";
    private static final String KEY_APP_ID = "app_id";
    private static final String KEY_METHOD = "method";

    private String ref;
    private String appId;
    private JSONObject body;
    private String method;

    public WebhookBody(JSONObject obj){
        // Work on a copy: the constructor used to strip these keys out of the
        // caller's JSONObject and then alias it as the body.
        JSONObject payload = new JSONObject(obj);
        if (payload.containsKey(KEY_REF))  {
            this.ref = String.valueOf(payload.remove(KEY_REF));
        }
        if (payload.containsKey(KEY_APP_ID)) {
            this.appId = String.valueOf(payload.remove(KEY_APP_ID));
        }
        if (payload.containsKey(KEY_METHOD)) {
            this.method = String.valueOf(payload.remove(KEY_METHOD));
        }
        this.body = payload;
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

    /**
     * @return the webhook method, previously parsed but not exposed
     */
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
