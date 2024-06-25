package com.nandbox.bots.api.outmessages;

import net.minidev.json.JSONObject;

public class GetCollectionProductOutMessage extends OutMessage {
    private String id;

    public GetCollectionProductOutMessage() {
        super();
        this.method = OutMessageMethod.getCollectionProduct;
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject obj = super.toJsonObject();

        if (id != null) obj.put("id", id);

        return obj;
    }

    // Getter and Setter for id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
