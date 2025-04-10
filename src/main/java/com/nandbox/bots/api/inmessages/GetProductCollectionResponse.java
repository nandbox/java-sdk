package com.nandbox.bots.api.inmessages;

import com.nandbox.bots.api.data.CollectionProduct;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetProductCollectionResponse {
    private static final String KEY_PRODUCTS = "products";
    private static final String KEY_APP_ID = "app_id";
    private static final String KEY_REFERENCE = "reference";

    private List<CollectionProduct> collectionProducts;
    private String appId;
    private String reference;
    public GetProductCollectionResponse(JSONObject obj) {
        this.collectionProducts = new ArrayList<>();
        if (obj.containsKey(KEY_PRODUCTS)) {
            JSONArray productArray = (JSONArray) obj.get(KEY_PRODUCTS);
            for (Object item : productArray) {
                JSONObject productObj = (JSONObject) item;
                this.collectionProducts.add(new CollectionProduct(productObj));
            }
        }
        this.appId =obj.get(KEY_APP_ID) != null
                ? String.valueOf(obj.get(KEY_APP_ID))
                : "0";
        this.reference =obj.get(KEY_REFERENCE) != null
                ? String.valueOf(obj.get(KEY_REFERENCE))
                : null;
    }

    public JSONObject toJsonObject() {
        JSONObject obj = new JSONObject();
        JSONArray productsArray = new JSONArray();

        for (CollectionProduct product : collectionProducts) {
            productsArray.add(product.toJsonObject());
        }

        if (!collectionProducts.isEmpty()) {
            obj.put(KEY_PRODUCTS, productsArray);
        }
        if (appId!=null){
            obj.put(KEY_APP_ID,appId);
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
    public String getAppId(){
        return appId;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
