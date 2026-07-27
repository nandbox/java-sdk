package com.nandbox.bots.api.data;

import com.nandbox.bots.api.util.Utils;

import net.minidev.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CollectionProduct {

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PRICE = "price";
    private static final String KEY_STATUS = "status";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_BUSINESS_CHANNEL_ID = "business_channel_id";
    private static final String KEY_APP_ID = "app_id";
    private static final String KEY_IMAGE = "image";

    private long id;
    private Long businessChannelId;
    private String appId;
    private String name;
    private String category;
    private Double price;
    private String status;
    private List<Image> image;

    public CollectionProduct() {}

    public CollectionProduct(JSONObject obj) {
        // json-smart picks the narrowest numeric type that fits, so "price": 10
        // arrives as Integer/Long and "id": 5 as Integer. Direct casts to Double
        // and Long therefore threw ClassCastException on perfectly valid payloads.
        this.id = Utils.getLong(obj.get(KEY_ID));
        this.name = (String) obj.get(KEY_NAME);
        this.price = obj.get(KEY_PRICE) instanceof Number ? ((Number) obj.get(KEY_PRICE)).doubleValue() : null;
        this.status = (String) obj.get(KEY_STATUS);
        this.category=(String) obj.get(KEY_CATEGORY);
        this.businessChannelId = obj.get(KEY_BUSINESS_CHANNEL_ID) == null
                ? null : Utils.getLong(obj.get(KEY_BUSINESS_CHANNEL_ID));
        this.appId = (String) obj.get(KEY_APP_ID);
        this.image = new ArrayList<>();
        if (obj.get(KEY_IMAGE) instanceof List) {
            for (Object item : (List<?>) obj.get(KEY_IMAGE)) {
                if (item instanceof JSONObject) {
                    this.image.add(new Image((JSONObject) item));
                }
            }
        }
    }

    public JSONObject toJsonObject() {
        JSONObject obj = new JSONObject();

        if (id != 0) obj.put(KEY_ID, id);
        if (name != null) obj.put(KEY_NAME, name);
        if (price != null) obj.put(KEY_PRICE, price);
        if (status != null) obj.put(KEY_STATUS, status);
        if (image != null) obj.put(KEY_IMAGE, image.stream().map(Image::toJsonObject).collect(Collectors.toList()));
        // These three were parsed but never written back, so the object could not
        // round-trip through toJsonObject().
        if (category != null) obj.put(KEY_CATEGORY, category);
        if (businessChannelId != null) obj.put(KEY_BUSINESS_CHANNEL_ID, businessChannelId);
        if (appId != null) obj.put(KEY_APP_ID, appId);

        return obj;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Long getBusinessChannelId() {
        return businessChannelId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getStatus() {
        return status;
    }

    public void setBusinessChannelId(Long businessChannelId) {
        this.businessChannelId = businessChannelId;
    }
}
