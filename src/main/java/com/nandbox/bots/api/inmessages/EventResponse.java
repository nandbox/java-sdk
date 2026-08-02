package com.nandbox.bots.api.inmessages;

import java.util.ArrayList;
import java.util.List;

import com.nandbox.bots.api.util.Utils;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 * Reply to subscribeToEvent, unsubscribeFromEvent and listEventSubscriptions.
 *
 * <p>
 * {@link #getAck()} is the only success test: false means nothing changed and
 * {@link #getError()} says why. Common errors are 400 (missing app id, or an event the server
 * does not publish), 160017 (privilege missing for that event), 160024 (the account is not a
 * member of the app) and 160015 (unknown app).
 * </p>
 *
 * <p>
 * For a subscribe or unsubscribe reply, {@link #getEvents()} echoes what the request asked for.
 * For a list reply, it is the account's current subscriptions.
 * </p>
 */
public class EventResponse {

	private static final String KEY_METHOD = "method";
	private static final String KEY_EVENT = "event";
	private static final String KEY_EVENTS = "events";
	private static final String KEY_APP_ID = "app_id";
	private static final String KEY_ACCOUNT_ID = "account_id";
	private static final String KEY_ACK = "ack";
	private static final String KEY_ERROR = "error";
	private static final String KEY_REFERENCE = "reference";
	private static final String KEY_REF = "ref";

	private String method;
	private List<String> events = new ArrayList<String>();
	private String appId;
	private String accountId;
	private Boolean ack;
	private Integer error;
	private String reference;

	public EventResponse(JSONObject obj) {
		this.method = asString(obj.get(KEY_METHOD));
		this.appId = asString(obj.get(KEY_APP_ID));
		this.accountId = asString(obj.get(KEY_ACCOUNT_ID));

		Object ref = obj.get(KEY_REFERENCE);
		this.reference = asString(ref != null ? ref : obj.get(KEY_REF));

		if (obj.get(KEY_ACK) != null) {
			this.ack = Boolean.valueOf(String.valueOf(obj.get(KEY_ACK)));
		}
		if (obj.get(KEY_ERROR) != null) {
			this.error = Utils.getInteger(obj.get(KEY_ERROR));
		}

		Object list = obj.get(KEY_EVENTS);
		if (list instanceof JSONArray) {
			for (Object item : (JSONArray) list) {
				if (item != null) {
					this.events.add(String.valueOf(item));
				}
			}
		}
		// single event requests also echo "event"; keep the list authoritative either way
		String single = asString(obj.get(KEY_EVENT));
		if (single != null && !this.events.contains(single)) {
			this.events.add(single);
		}
	}

	private static String asString(Object value) {
		return value == null ? null : String.valueOf(value);
	}

	/** eventResponse or listEventSubscriptionsResponse. */
	public String getMethod() {
		return method;
	}

	/** Never null; empty when a list reply found no subscriptions. */
	public List<String> getEvents() {
		return events;
	}

	/** The first event, for the common single event request. Null when there are none. */
	public String getEvent() {
		return events.isEmpty() ? null : events.get(0);
	}

	public String getAppId() {
		return appId;
	}

	/** The account the subscription applies to, which is the caller unless one was set. */
	public String getAccountId() {
		return accountId;
	}

	/** True when the request took effect. False means look at {@link #getError()}. */
	public Boolean getAck() {
		return ack;
	}

	/** Server error code, set only when ack is false. */
	public Integer getError() {
		return error;
	}

	public String getReference() {
		return reference;
	}
}
