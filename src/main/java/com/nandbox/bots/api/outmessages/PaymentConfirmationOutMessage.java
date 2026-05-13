package com.nandbox.bots.api.outmessages;

import net.minidev.json.JSONObject;

public class PaymentConfirmationOutMessage extends OutMessage {
    public static String KEY_ORDER_ID = "order_id";
    public static String KEY_PAYLOAD = "providerResponse";
    public static String KEY_SECRET = "secret";
    public static String KEY_CURRENCY = "currency";
    public static String KEY_TOTAL_AMOUNT = "total_amount";
    public static String KEY_ACCOUNT_ID = "account_id";
    public static String KEY_STATUS = "status";
    public static String Key_DEBIT_AMOUNT_CENTS = "debit_amount_cents";
    private String orderId;
    private JSONObject providerResponse;
    private String secret;
    private String currency;
    double totalAmount;
    private long accountId;
    private String status;
    private long debitAmountCents;

    public String getStatus() {
        return status;
    }

    public void setDebitAmountCents(long debitAmountCents) {
        this.debitAmountCents = debitAmountCents;
    }

    public long getDebitAmountCents() {
        return debitAmountCents;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PaymentConfirmationOutMessage() {
        this.method = OutMessageMethod.submitPaymentResult;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }



    public void setProviderResponse(JSONObject payload) {
        this.providerResponse = payload;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getCurrency() {
        return currency;
    }

    public String getOrderId() {
        return orderId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getSecret() {
        return secret;
    }



    public JSONObject getProviderResponse() {
        return providerResponse;
    }


    @Override
    public JSONObject toJsonObject() {
        JSONObject obj = super.toJsonObject();
        if (orderId != null) {
            obj.put(KEY_ORDER_ID, orderId);
        }
        if (providerResponse != null) {
            obj.put(KEY_PAYLOAD, providerResponse);
        }
        if (secret != null) {
            obj.put(KEY_SECRET, secret);
        }
        if (currency != null) {
            obj.put(KEY_CURRENCY, currency);
        }
        if (totalAmount != 0) {
            obj.put(KEY_TOTAL_AMOUNT, totalAmount);
        }
        if (accountId != 0) {
            obj.put(KEY_ACCOUNT_ID, accountId);
        }
        if (status != null) {
            obj.put(KEY_STATUS, status);
        }
        if (debitAmountCents != 0) {
            obj.put(Key_DEBIT_AMOUNT_CENTS, debitAmountCents);
        }
        return obj;

    }
}
