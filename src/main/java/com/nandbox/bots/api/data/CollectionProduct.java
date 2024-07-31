package com.nandbox.bots.api.data;

import net.minidev.json.JSONObject;
import java.util.List;
import java.util.stream.Collectors;

public class CollectionProduct {
    private long id;
    private String name;
    private Double price;
    private String status;
    private List<Image> image;

    public CollectionProduct() {}

    public CollectionProduct(JSONObject obj) {
        this.id = (long) obj.get("id");
        this.name = (String) obj.get("name");
        this.price = (Double) obj.get("price");
        this.status = (String) obj.get("status");
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


}
