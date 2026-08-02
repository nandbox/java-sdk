package com.nandbox.bots.api.outmessages;

import net.minidev.json.JSONObject;

/**
 * Lists the events an account is currently subscribed to in an app, so a client can reconcile
 * after a reconnect instead of blindly re-subscribing.
 *
 * <pre>
 * ListEventSubscriptionsOutMessage msg = new ListEventSubscriptionsOutMessage();
 * msg.setApp_id("1234");
 * </pre>
 *
 * The reply is a {@code listEventSubscriptionsResponse}, delivered to
 * {@code Callback.onEventResponse(EventResponse)} with the events in
 * {@link com.nandbox.bots.api.inmessages.EventResponse#getEvents()}.
 */
public class ListEventSubscriptionsOutMessage extends OutMessage {

	protected static final String KEY_ACCOUNT_ID = "account_id";

	private String accountId;

	public ListEventSubscriptionsOutMessage() {
		this.method = OutMessageMethod.listEventSubscriptions;
	}

	@Override
	public JSONObject toJsonObject() {
		JSONObject obj = super.toJsonObject();
		if (accountId != null) {
			obj.put(KEY_ACCOUNT_ID, accountId);
		}
		return obj;
	}

	public String getAccountId() {
		return accountId;
	}

	/** Leave unset to list your own subscriptions. Another account requires app admin. */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
}
