package com.nandbox.bots.api.inmessages;

import com.nandbox.bots.api.data.CollectionProduct;
import com.nandbox.bots.api.data.ProductItem;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetProductCollectionResponse {
    private static final String KEY_DATA = "data";
    private List<CollectionProduct> collectionProducts;

    public GetProductCollectionResponse(JSONObject obj) {
        this.collectionProducts = new ArrayList<>();
        if (obj.containsKey(KEY_DATA)) {
            JSONArray productArray = (JSONArray) obj.get(KEY_DATA);
            for (Object item : productArray) {
                JSONObject productObj = (JSONObject) item;
                this.collectionProducts.add(new CollectionProduct(productObj));
            }
        }
    }

    public JSONObject toJsonObject() {
        JSONObject obj = new JSONObject();
        JSONArray productsArray = new JSONArray();

        for (CollectionProduct product : collectionProducts) {
            productsArray.add(product.toJsonObject());
        }

        if (!collectionProducts.isEmpty()) {
            obj.put(KEY_DATA, productsArray);
        }

        return obj;
    }

    // Getters and setters
    public List<CollectionProduct> getCollectionProducts() {
        return collectionProducts;
    }

    public void setCollectionProducts(List<CollectionProduct> collectionProducts) {
        this.collectionProducts = collectionProducts;
    }
}
