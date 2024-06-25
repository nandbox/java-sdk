package com.nandbox.bots.api.inmessages;
import com.nandbox.bots.api.data.ProductItem;
import net.minidev.json.JSONObject;

public class ProductItemResponse {

        private static final String KEY_PRODUCT_ITEM = "productItem";

        private ProductItem productItem;

        public ProductItemResponse() {}

        public ProductItemResponse(JSONObject obj) {
            this.productItem = obj.get(KEY_PRODUCT_ITEM) != null ? new ProductItem((JSONObject) obj.get(KEY_PRODUCT_ITEM)) : null;
        }

        public JSONObject toJsonObject() {
            JSONObject obj = new JSONObject();

            if (productItem != null) {
                obj.put(KEY_PRODUCT_ITEM, productItem.toJsonObject());
            }

            return obj;
        }

        // Getter and Setter for productItem
        public ProductItem getProductItem() {
            return productItem;
        }

        public void setProductItem(ProductItem productItem) {
            this.productItem = productItem;
        }
    }


