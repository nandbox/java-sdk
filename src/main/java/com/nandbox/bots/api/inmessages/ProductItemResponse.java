package com.nandbox.bots.api.inmessages;
import com.nandbox.bots.api.data.ProductItem;
import net.minidev.json.JSONObject;

public class ProductItemResponse {

        private static final String KEY_DATA = "data";
    private static final String KEY_APP_ID = "app_id";
    private static final String KEY_REFERENCE= "reference";

        private ProductItem productItem;
    private String appId;
    private String reference;
        public ProductItemResponse() {}

        public ProductItemResponse(JSONObject obj) {
            this.appId =obj.get(KEY_APP_ID) != null
                    ? String.valueOf(obj.get(KEY_APP_ID))
                    : "0";
            this.reference =obj.get(KEY_REFERENCE) != null
                    ? String.valueOf(obj.get(KEY_REFERENCE))
                    : null;
            this.productItem = obj.get(KEY_DATA) != null ? new ProductItem((JSONObject) obj.get(KEY_DATA)) : null;
        }

        public JSONObject toJsonObject() {
            JSONObject obj = new JSONObject();

            if (productItem != null) {
                obj.put(KEY_DATA, productItem.toJsonObject());
            }
            if (appId!=null){
                obj.put(KEY_APP_ID,appId);
            }
            return obj;
        }

        // Getter and Setter for productItem
        public ProductItem getProductItem() {
            return productItem;
        }
    public String getAppId(){
        return appId;
    }
        public void setProductItem(ProductItem productItem) {
            this.productItem = productItem;
        }

    public String getReference() {
        return reference;
    }

}


