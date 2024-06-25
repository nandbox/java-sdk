package com.nandbox.bots.api.data;

import net.minidev.json.JSONObject;
import java.util.List;
import java.util.stream.Collectors;

public class CollectionProduct {
    private String id;
    private String name;
    private Double price;
    private String status;
    private List<Image> image;

    public CollectionProduct() {}

    public CollectionProduct(JSONObject obj) {
        this.id = (String) obj.get("id");
        this.name = (String) obj.get("name");
        this.price = (Double) obj.get("price");
        this.status = (String) obj.get("status");
        this.image = ((List<JSONObject>) obj.get("image")).stream().map(Image::new).collect(Collectors.toList());
    }

    public JSONObject toJsonObject() {
        JSONObject obj = new JSONObject();

        if (id != null) obj.put("id", id);
        if (name != null) obj.put("name", name);
        if (price != null) obj.put("price", price);
        if (status != null) obj.put("status", status);
        if (image != null) obj.put("image", image.stream().map(Image::toJsonObject).collect(Collectors.toList()));

        return obj;
    }

    public static class Image {
        private static final String KEY_WIDTH = "width";
        private static final String KEY_URL = "url";
        private static final String KEY_HEIGHT = "height";

        private Integer width;
        private String url;
        private Integer height;

        public Image(JSONObject img) {
            this.width = (Integer) img.get(KEY_WIDTH);
            this.url = (String) img.get(KEY_URL);
            this.height = (Integer) img.get(KEY_HEIGHT);
        }

        public JSONObject toJsonObject() {
            JSONObject obj = new JSONObject();
            if (width != null) obj.put(KEY_WIDTH, width);
            if (url != null) obj.put(KEY_URL, url);
            if (height != null) obj.put(KEY_HEIGHT, height);
            return obj;
        }
    }
}
