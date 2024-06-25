package com.nandbox.bots.api.inmessages;

import com.nandbox.bots.api.data.CollectionProduct;
import net.minidev.json.JSONObject;

public class CollectionProductResponse {
    private CollectionProduct collectionProduct;

    public CollectionProductResponse() {}

    public CollectionProductResponse(JSONObject obj) {
        this.collectionProduct = new CollectionProduct((JSONObject) obj.get("collectionProduct"));
    }

    public JSONObject toJsonObject() {
        JSONObject obj = new JSONObject();

        if (collectionProduct != null) obj.put("collectionProduct", collectionProduct.toJsonObject());

        return obj;
    }

    // Getter and Setter for collectionProduct
    public CollectionProduct getCollectionProduct() {
        return collectionProduct;
    }

    public void setCollectionProduct(CollectionProduct collectionProduct) {
        this.collectionProduct = collectionProduct;
    }
}
