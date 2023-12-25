package com.nandbox.bots.api.outmessages;

import net.minidev.json.JSONObject;

public class SetWorkflowActionOutMessage extends OutMessage{
    protected static final String KEY_USER_ID = "user_id";
    private static final String KEY_SCREEN_ID = "screen_id";
    private static final String KEY_NEXT_SCREEN = "next_screen";
    private static final String KEY_REFERENCE = "reference";
    protected static final String KEY_VAPP_ID = "vapp_id";
    private String userId;
    private String vappId;
    private String screenId;
    private String nextScreen;
    private Long reference;

    public SetWorkflowActionOutMessage() {
        this.method = OutMessageMethod.setWorkflowAction;
    }

    public void setUserId(String userId) { this.userId = userId; }
    public void setVappId(String vappId) { this.vappId = vappId; }
    public void setReference(long reference) { this.reference = reference; }
    public void setScreenId(String screenId) { this.screenId = screenId; }
    public void setNextScreen(String nextScreen) { this.nextScreen = nextScreen; }

    @Override
    public JSONObject toJsonObject(){
        JSONObject obj = super.toJsonObject();

        if (userId != null) {
            obj.put(KEY_USER_ID, userId);
        }
        if(vappId!= null){
            obj.put(KEY_VAPP_ID, vappId);
        }
        if (screenId != null) {
            obj.put(KEY_SCREEN_ID, screenId);
        }
        if (nextScreen != null) {
            obj.put(KEY_NEXT_SCREEN, nextScreen);
        }
        if (reference != null) {
            obj.put(KEY_REFERENCE, reference);
        }
        return obj;
    }

}
