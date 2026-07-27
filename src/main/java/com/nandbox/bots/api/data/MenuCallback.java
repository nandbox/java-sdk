package com.nandbox.bots.api.data;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.util.List;
import java.util.stream.Collectors;

import com.nandbox.bots.api.util.Utils;

public class MenuCallback {

    private String source;
    private String api_id;
    private String app_id;
    private Chat chat;
    private User from;
    private long date;
    private String menu_id;
    private String menu_group;
    private List<Cell> cells;
    public MenuCallback() {
    }
    /**
     * Returns {@code null} for an absent key rather than the literal text "null",
     * which is what String.valueOf(null) produces.
     */
    private static String asString(Object value) {
        return value == null ? null : String.valueOf(value);
    }

    public MenuCallback(JSONObject jsonObject) {
        this.menu_id = asString(jsonObject.get("menu_id"));
        this.source = asString(jsonObject.get("source"));
        this.api_id = asString(jsonObject.get("api_id"));
        this.app_id = asString(jsonObject.get("app_id"));
        this.chat = jsonObject.get("chat") != null ? new Chat((JSONObject) jsonObject.get("chat")) : null;
        this.from = jsonObject.get("from") != null ? new User((JSONObject) jsonObject.get("from")) : null;
        this.date = Utils.getLong(jsonObject.get("date"));
        if (jsonObject.get("cells") != null && jsonObject.get("cells") instanceof List) {
            List<?> cellObjs = (List<?>) jsonObject.get("cells");
            this.cells = cellObjs.stream().filter(o -> o instanceof JSONObject).map(o -> new Cell((JSONObject) o)).collect(Collectors.toList());
        }
        this.menu_group = jsonObject.get("menu_group") != null ? String.valueOf(jsonObject.get("menu_group")) : null;
    }

    public static class Cell {
        private String menu_id;
        private String cell_id;
        private String form;
        private String style;
        private String label;
        private String callback;
        private ValueType value_type;
        private List<CellValue> value;

        public Cell() {
        }
        public Cell(String menu_id, String cell_id, String form, String style, String label, ValueType value_type, List<CellValue> value,String callback) {
            this.menu_id = menu_id;
            this.cell_id = cell_id;
            this.form = form;
            this.style = style;
            this.label = label;
            this.value_type = value_type;
            this.value = value;
            this.callback=callback;
        }
        public Cell(JSONObject jsonObject) {
            this.menu_id = asString(jsonObject.get("menu_id"));
            this.cell_id = asString(jsonObject.get("cell_id"));
            this.form = asString(jsonObject.get("form"));
            this.style = asString(jsonObject.get("style"));
            this.label = asString(jsonObject.get("label"));
            Object valueTypeObj = jsonObject.get("value_type");

            if (valueTypeObj instanceof JSONObject) {
                Object data = ((JSONObject) valueTypeObj).get("data");
                this.value_type = data != null ? new ValueType(String.valueOf(data)) : null;
            } else if (valueTypeObj != null) {
                this.value_type = new ValueType(String.valueOf(valueTypeObj));
            } else {
                this.value_type = null;
            }
            Object valueObj = jsonObject.get("value");

            if (valueObj instanceof List) {
                this.value = ((List<?>) valueObj).stream()
                        .filter(o -> o instanceof JSONObject)
                        .map(o -> new CellValue((JSONObject) o))
                        .collect(Collectors.toList());

            } else if (valueObj instanceof JSONObject) {
                this.value = java.util.Collections.singletonList(new CellValue((JSONObject) valueObj));

            } else if (valueObj != null) {
                this.value = java.util.Collections.singletonList(
                        new CellValue(null, valueObj, null)
                );
            }
            this.callback = jsonObject.get("callback") != null ? String.valueOf(jsonObject.get("callback")) : null;
        }

        public List<CellValue> getValue() {
            return value;
        }
        public void setValue(List<CellValue> value) {
            this.value = value;
        }
        public String getMenu_id() {
            return menu_id;
        }

        public ValueType getValue_type() {
            return value_type;
        }
        public void setValue_type(ValueType value_type) {
            this.value_type = value_type;
        }

        public void setMenu_id(String menu_id) {
            this.menu_id = menu_id;
        }
        public String getCell_id() {
            return cell_id;
        }
        public void setCell_id(String cell_id) {
            this.cell_id = cell_id;
        }
        public String getForm() {
            return form;
        }
        public void setForm(String form) {
            this.form = form;
        }
        public String getStyle() {
            return style;
        }
        public void setStyle(String style) {
            this.style = style;
        }
        public String getLabel() {
            return label;
        }
        public void setLabel(String label) {
            this.label = label;
        }

        public String getCallback() {
            return callback;
        }
        public void setCallback(String callback) {
            this.callback = callback;
        }
    }

    public static class ValueType {
        private String data;
        public ValueType() {
        }
        public ValueType(String data) {
            this.data = data;
        }
        public String getData() {
            return data;
        }
        public void setData(String data) {
            this.data = data;
        }
    }

    public static class CellValue {
        private String id;
        private Object value;
        private String option_label; // optional, for multi_chip
        public CellValue() {
        }
        public CellValue(String id, Object value, String option_label) {
            this.id = id;
            this.value = value;
            this.option_label = option_label;
        }
        public CellValue(JSONObject jsonObject) {
            this.id = asString(jsonObject.get("id"));
            this.value = jsonObject.get("value");
            this.option_label = asString(jsonObject.get("option_label"));
        }
        public Object getValue() {
            return value;
        }
        public void setValue(Object value) {
            this.value = value;
        }
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getOption_label() {
            return option_label;
        }
        public void setOption_label(String option_label) {
            this.option_label = option_label;
        }


    }

    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String getApi_id() {
        return api_id;
    }
    public void setApi_id(String api_id) {
        this.api_id = api_id;
    }
    public String getApp_id() {
        return app_id;
    }
    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }
    public Chat getChat() {
        return chat;
    }
    public void setChat(Chat chat) {
        this.chat = chat;
    }
    public User getUser() {
        return from;
    }
    public void setUser(User user) {
        this.from = user;
    }
    public long getDate() {
        return date;
    }
    public void setDate(long date) {
        this.date = date;
    }
    public List<Cell> getCells() {
        return cells;
    }
    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

    public String getMenu_id() {
        return menu_id;
    }
    public void setMenu_id(String menu_id) {
        this.menu_id = menu_id;
    }

    /**
     * Alias for {@link #toJSON()}, matching the toJsonObject() name used by every
     * other class in this package so generic serialization can find it.
     */
    public JSONObject toJsonObject() {
        return toJSON();
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        // menu_id and menu_group were parsed but never written back.
        if (this.menu_id != null) {
            json.put("menu_id", this.menu_id);
        }
        if (this.menu_group != null) {
            json.put("menu_group", this.menu_group);
        }
        json.put("source", this.source);
        json.put("api_id", this.api_id);
        json.put("app_id", this.app_id);
        if (this.chat != null) {
            json.put("chat", this.chat.toJsonObject());
        }
        if (this.from != null) {
            json.put("from", this.from.toJsonObject());
        }
        json.put("date", this.date);
        if (this.cells != null) {
            JSONArray cellList = new JSONArray();
            this.cells.forEach(cell -> {
                JSONObject cellJson = new JSONObject();
                cellJson.put("menu_id", cell.getMenu_id());
                cellJson.put("cell_id", cell.getCell_id());
                cellJson.put("form", cell.getForm());
                cellJson.put("style", cell.getStyle());
                cellJson.put("label", cell.getLabel());
                // callback identifies the cell to the bot (Utils.getFieldsAndValues
                // keys on it) but was omitted from the serialized form.
                if (cell.getCallback() != null) {
                    cellJson.put("callback", cell.getCallback());
                }
                if (cell.getValue_type() != null) {
                    JSONObject valueTypeJson = new JSONObject();
                    valueTypeJson.put("data", cell.getValue_type().getData());
                    cellJson.put("value_type", valueTypeJson);
                }
                if (cell.getValue() != null) {
                    net.minidev.json.JSONArray valueList = new net.minidev.json.JSONArray();
                    cell.getValue().forEach(value -> {
                        JSONObject valueJson = new JSONObject();
                        valueJson.put("id", value.getId());
                        valueJson.put("value", value.getValue());
                        if (value.getOption_label() != null) {
                            valueJson.put("option_label", value.getOption_label());
                        }
                        valueList.add(valueJson);
                    });
                    cellJson.put("value", valueList);
                }
                cellList.add(cellJson);
            });
            json.put("cells", cellList);
        }
        return json;
    }

    public String getMenu_group() {
        return menu_group;
    }

    public void setMenu_group(String menu_group) {
        this.menu_group = menu_group;
    }
}
