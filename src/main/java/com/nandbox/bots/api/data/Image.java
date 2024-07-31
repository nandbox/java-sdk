package com.nandbox.bots.api.data;

import net.minidev.json.JSONObject;

public class Image {
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
