package com.nandbox.bots.api.data;

import net.minidev.json.JSONObject;

public class PaymentRequest {
    private static final String KEY_ORDER_ID = "order_id";
    private static final String KEY_MERCHANT_NAME = "merchant_name";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_CURRENCY = "currency";
    private static final String KEY_PAYLOAD = "payload";
    private static final String KEY_ACCOUNT_ID = "account_id";
    private static final String KEY_SECRET = "secret";
    private static final String KEY_APP_ID = "app_id";
    private static final String Key_PROVIDER_ID = "provider_id";
    private static final String KEY_CONFIG = "config";
    private static final String KEY_DEBIT_AMOUNT_CENTS = "debit_amount_cents";
    private String orderId;
    private String merchantName;
    private double amount;
    private String currency;
    private JSONObject payload;
    private long accountId;
    private String secret;
    private String appId;
    private JSONObject config;
    private String providerId;
    private  long debitAmountCents;



    public PaymentRequest(JSONObject obj){
        System.out.println("PaymentRequest JSON: " + obj.toJSONString());
        if (obj.containsKey(KEY_ORDER_ID)){
            this.orderId = (String) obj.get(KEY_ORDER_ID);
        }
        if (obj.containsKey(KEY_MERCHANT_NAME)){
            this.merchantName = (String) obj.get(KEY_MERCHANT_NAME);
        }
        if (obj.containsKey(KEY_AMOUNT)){
            this.amount = ((Number) obj.get(KEY_AMOUNT)).doubleValue();
        }
        if (obj.containsKey(KEY_CURRENCY)){
            this.currency = (String) obj.get(KEY_CURRENCY);
        }
        if (obj.containsKey(KEY_PAYLOAD)){
            this.payload = (JSONObject) obj.get(KEY_PAYLOAD);
        }
        if (obj.containsKey(KEY_ACCOUNT_ID)){
            this.accountId = ((Number) obj.get(KEY_ACCOUNT_ID)).longValue();
        }
        if (obj.containsKey(KEY_SECRET)){
            this.secret = (String) obj.get(KEY_SECRET);
        }
        if (obj.containsKey(KEY_APP_ID)){
            this.appId = obj.get(KEY_APP_ID).toString();
        }
        if (obj.containsKey(Key_PROVIDER_ID)){
            this.providerId = obj.get(Key_PROVIDER_ID).toString();
        }
        if (obj.containsKey(KEY_CONFIG)) {
            this.config = (JSONObject) obj.get(KEY_CONFIG);
        }
        if (obj.containsKey(KEY_DEBIT_AMOUNT_CENTS)) {
            this.debitAmountCents = ((Number) obj.get(KEY_DEBIT_AMOUNT_CENTS)).longValue();
        }


    }

    public String getProviderId() {
        return providerId;
    }

    public static String getKey_PROVIDER_ID() {
        return Key_PROVIDER_ID;
    }

    public long getDebitAmountCents() {
        return debitAmountCents;
    }

    public void setDebitAmountCents(long debitAmountCents) {
        this.debitAmountCents = debitAmountCents;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public JSONObject getConfig() {
        return config;
    }

    public void setConfig(JSONObject config) {
        this.config = config;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public long getAccountId() {
        return accountId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public JSONObject getPayload() {
        return payload;
    }

    public void setPayload(JSONObject payload) {
        this.payload = payload;
    }
    public JSONObject toJson(){
        JSONObject obj = new JSONObject();
        obj.put(KEY_ORDER_ID, orderId);
        obj.put(KEY_MERCHANT_NAME, merchantName);
        obj.put(KEY_AMOUNT, amount);
        obj.put(KEY_CURRENCY, currency);
        obj.put(KEY_PAYLOAD, payload);
        return obj;
    }
}
