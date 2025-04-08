package com.nandbox.bots.api.inmessages;

import com.nandbox.bots.api.data.Category;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListCollectionItemResponse {
    private static final String KEY_COLLECTIONS = "collections";
    private static final String KEY_CATEGORIES = "categories";

    private static final String KEY_APP_ID = "app_id";
    private static final String KEY_REF = "ref";
    private static final String KEY_MAIN_GROUP_ID = "main_group_id";
    private static final String KEY_BUSINESS_CHANNEL_ID = "business_channel_id";

    private List<Category> categories;
    private Long appId;
    private Long reference;
    private Long bussinessChannelId;

    public ListCollectionItemResponse(JSONObject obj) {
        this.categories = new ArrayList<>();
        if (obj.containsKey(KEY_COLLECTIONS)) {
            JSONArray categoryArray = (JSONArray) obj.get(KEY_COLLECTIONS);
            for (Object item : categoryArray) {
                JSONObject categoryObj = (JSONObject) item;
                this.categories.add(new Category(categoryObj));
            }
        }

        this.appId =obj.get(KEY_APP_ID) != null
                ? Long.valueOf(Long.parseLong(String.valueOf(obj.get(KEY_APP_ID))))
                :obj.get(KEY_MAIN_GROUP_ID) !=null?  Long.parseLong(String.valueOf(obj.get(KEY_MAIN_GROUP_ID))):null;
        this.bussinessChannelId = obj.get(KEY_BUSINESS_CHANNEL_ID)!=null?Long.valueOf(Long.parseLong(String.valueOf(obj.get(KEY_BUSINESS_CHANNEL_ID)))) :this.appId;
                this.reference =obj.get(KEY_REF) != null
                ? Long.parseLong(String.valueOf(obj.get(KEY_REF)))
                : null;

    }

    public JSONObject toJsonObject() {
        JSONObject obj = new JSONObject();
        JSONArray categoriesArray = new JSONArray();

        for (Category category : this.categories) {
            categoriesArray.add(category.toJsonObject());
        }
        if (this.reference!=null){
            obj.put(KEY_REF,reference);
        }
        if (!this.categories.isEmpty()) {
            obj.put(KEY_CATEGORIES, categoriesArray);
        }
        if (appId!=null){
            obj.put(KEY_APP_ID,appId);
        }
        if (bussinessChannelId!=null){
            obj.put(KEY_BUSINESS_CHANNEL_ID,bussinessChannelId);
        }
        return obj;
    }

    // Getters and setters
    public List<Category> getCategories() {
        return categories;
    }

    public Long getReference() {
        return reference;
    }

    public Long getBussinessChannelId() {
        return bussinessChannelId;
    }

    public void setBussinessChannelId(Long bussinessChannelId) {
        this.bussinessChannelId = bussinessChannelId;
    }

    public void setReference(Long reference) {
        this.reference = reference;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
    public Long getAppId(){
        return appId;
    }
}
