package com.nandbox.bots.api.outmessages;

import net.minidev.json.JSONObject;

public class GetProductItemOutMessage extends OutMessage{
    private String id;

    public GetProductItemOutMessage(){this.method = OutMessageMethod.getProductItem;}
    @Override
    public JSONObject toJsonObject() {
        JSONObject obj = super.toJsonObject();
        if (id != null) {
            obj.put("id", id);
        }
        return obj;
    }
    /**
     * @return the productId
     */
    public String getProductId() {
        return id;
    }

    /**
     * @param productId
     *            the productId to set
     */
    public void setProductId(String productId) {
        this.id = productId;
    }
}
