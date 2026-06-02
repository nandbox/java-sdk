package com.nandbox.bots.api.outmessages;

import com.nandbox.bots.api.NandboxClient.*;
import net.minidev.json.JSONObject;

public class SendUserNotificationOutMessage extends OutMessage{
    String KEY_TYPE = "type";
    String KEY_TITLE = "title";
    String KEY_MESSAGE = "message";
    String KEY_ACCOUNT_ID = "account_id";
    String title;
    String message;
    NotificationType type;
    long accountId;

    @Override
    public JSONObject toJsonObject() {
        JSONObject obj = super.toJsonObject();
        if (type != null) {
            obj.put(KEY_TYPE, type.toString());
        }else {
            obj.put(KEY_TYPE, NotificationType.Push.toString());
        }
        if (title != null) {
            obj.put(KEY_TITLE, title);
        }
        if (message != null) {
            obj.put(KEY_MESSAGE, message);
        }
        if (accountId != 0) {
            obj.put(KEY_ACCOUNT_ID, accountId);
        }
        return obj;

    }

    public SendUserNotificationOutMessage(){
        this.method = OutMessageMethod.sendUserNotification;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationType getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
}
