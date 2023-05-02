package com.nandbox.bots.api.data;

import net.minidev.json.JSONObject;

/**
 * @author Samy El-Hafeiz
 */
public class WorkflowCell {
    private static final String KEY_CELL_ID = "cell_id";
    private static final String KEY_CALLBACK = "callback";
    private static final String KEY_API_ID = "api_id";
    private static final String KEY_CACHE = "cache";
    private static final String KEY_NEXT_SCREEN = "next_screen";
    private static final String KEY_URL = "url";
    private static final String KEY_BG_COLOR = "bg_color";
    private static final String KEY_LABEL = "label";
    private static final String KEY_LABEL_COLOR = "label_color";
    private static final String KEY_SUBLABEL = "sublabel";
    private static final String KEY_SUBLABEL_COLOR = "sublabel_color";
    private static final String KEY_HINT = "hint";
    private static final String KEY_VALUE = "value";

    private String cellId;
    private String callBack;
    private Long apiId;
    private Boolean cache;
    private String nextScreen;
    private String url;
    private String bgColor;
    private String label;
    private String labelColor;
    private String subLabel;
    private String subLabelColor;
    private String hint;
    private String value;

    public WorkflowCell() {
    }

    public WorkflowCell(JSONObject obj) {
        this.cellId = (String) obj.get(KEY_CELL_ID);
        this.callBack = (String) obj.get(KEY_CALLBACK);
        this.apiId = (Long) obj.get(KEY_API_ID);
        this.cache = (Boolean) obj.get(KEY_CACHE);
        this.nextScreen = (String) obj.get(KEY_NEXT_SCREEN);
        this.url = (String) obj.get(KEY_URL);
        this.bgColor = (String) obj.get(KEY_BG_COLOR);
        this.label = (String) obj.get(KEY_LABEL);
        this.labelColor = (String) obj.get(KEY_LABEL_COLOR);
        this.subLabel = (String) obj.get(KEY_SUBLABEL);
        this.subLabelColor = (String) obj.get(KEY_SUBLABEL_COLOR);
        this.hint = (String) obj.get(KEY_HINT);
        this.value = (String) obj.get(KEY_VALUE);

    }

    public JSONObject toJsonObject() {
        JSONObject obj = new JSONObject();
        if (this.cellId != null) {
            obj.put(KEY_CELL_ID, this.cellId);
        }
        if (this.callBack != null) {
            obj.put(KEY_CALLBACK, this.callBack);
        }
        if (this.apiId != null) {
            obj.put(KEY_API_ID, this.apiId);
        }
        if (this.cache != null) {
            obj.put(KEY_CACHE, this.cache);
        }
        if (this.nextScreen != null) {
            obj.put(KEY_NEXT_SCREEN, this.nextScreen);
        }
        if (this.url != null) {
            obj.put(KEY_URL, this.url);
        }
        if (this.bgColor != null) {
            obj.put(KEY_BG_COLOR, this.bgColor);
        }
        if (this.label != null) {
            obj.put(KEY_LABEL, this.label);
        }
        if (this.labelColor != null) {
            obj.put(KEY_LABEL_COLOR, this.labelColor);
        }
        if (this.subLabel != null) {
            obj.put(KEY_SUBLABEL, this.subLabel);
        }
        if (this.subLabelColor != null) {
            obj.put(KEY_SUBLABEL_COLOR, this.subLabelColor);
        }
        if (this.hint != null) {
            obj.put(KEY_HINT, this.hint);
        }
        if (this.value != null) {
            obj.put(KEY_VALUE, this.value);
        }
        return obj;
    }

    public String getCellId() {
        return cellId;
    }

    public void setCellId(String cellId) {
        this.cellId = cellId;
    }

    public String getCallBack() {
        return callBack;
    }

    public void setCallBack(String callBack) {
        this.callBack = callBack;
    }

    public Long getApiId() {
        return apiId;
    }

    public void setApiId(Long apiId) {
        this.apiId = apiId;
    }

    public Boolean getCache() {
        return cache;
    }

    public void setCache(Boolean cache) {
        this.cache = cache;
    }

    public String getNextScreen() {
        return nextScreen;
    }

    public void setNextScreen(String nextScreen) {
        this.nextScreen = nextScreen;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabelColor() {
        return labelColor;
    }

    public void setLabelColor(String labelColor) {
        this.labelColor = labelColor;
    }

    public String getSubLabel() {
        return subLabel;
    }

    public void setSubLabel(String subLabel) {
        this.subLabel = subLabel;
    }

    public String getSubLabelColor() {
        return subLabelColor;
    }

    public void setSubLabelColor(String subLabelColor) {
        this.subLabelColor = subLabelColor;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
