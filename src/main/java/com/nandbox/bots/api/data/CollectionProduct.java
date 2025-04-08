package com.nandbox.bots.api.data;

import net.minidev.json.JSONObject;
import java.util.List;
import java.util.stream.Collectors;

public class CollectionProduct {
    private long id;
    private Long businessChannelId;
    private Long appId;
    private String name;
    private String category;
    private Double price;
    private String status;
    private List<Image> image;

    public CollectionProduct() {}

    public CollectionProduct(JSONObject obj) {
        this.id = (long) obj.get("id");
        this.name = (String) obj.get("name");
        this.price = (Double) obj.get("price");
        this.status = (String) obj.get("status");
        this.category=(String) obj.get("category");
        this.businessChannelId = (Long) obj.get("business_channel_id");
        this.appId = (Long) obj.get("app_id");
        this.image = ((List<JSONObject>) obj.get("image")).stream().map(Image::new).collect(Collectors.toList());
    }

    public JSONObject toJsonObject() {
        JSONObject obj = new JSONObject();

        if (id != 0) obj.put("id", id);
        if (name != null) obj.put("name", name);
        if (price != null) obj.put("price", price);
        if (status != null) obj.put("status", status);
        if (image != null) obj.put("image", image.stream().map(Image::toJsonObject).collect(Collectors.toList()));

        return obj;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
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
