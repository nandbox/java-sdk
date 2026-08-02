package com.nandbox.bots.api.inmessages;

import net.minidev.json.JSONObject;

/**
 * A change on an event the account is subscribed to.
 *
 * <p>
 * The payload is deliberately left as a raw {@link JSONObject}. The server decides per event
 * which keys survive its filter, and that set changes without a client release - so modelling
 * the payload as fixed fields would silently drop whatever was added and break on whatever was
 * removed. Read what you need out of {@link #getBody()} and tolerate a missing key.
 * </p>
 *
 * <pre>
 * public void onEventMessage(EventMessage event) {
 *     if ("product".equals(event.getEvent())) {
 *         Object id = event.getBody().get("id");
 *         Object price = event.getBody().get("price");   // may be absent
 *     }
 * }
 * </pre>
 *
 * <p>
 * {@link #getBody()} is the whole message, so {@code method}, {@code event} and {@code app_id}
 * are in there too alongside the payload keys.
 * </p>
 */
public class EventMessage {

	private static final String KEY_METHOD = "method";
	private static final String KEY_EVENT = "event";
	private static final String KEY_APP_ID = "app_id";

	private String method;
	private String event;
	private String appId;
	private JSONObject body;

	public EventMessage(JSONObject obj) {
		this.body = obj == null ? new JSONObject() : obj;
		this.method = asString(this.body.get(KEY_METHOD));
		this.event = asString(this.body.get(KEY_EVENT));
		this.appId = asString(this.body.get(KEY_APP_ID));
	}

	private static String asString(Object value) {
		return value == null ? null : String.valueOf(value);
	}

	public String getMethod() {
		return method;
	}

	/** Which event fired: product, chat, chatMember, content or order. */
	public String getEvent() {
		return event;
	}

	public String getAppId() {
		return appId;
	}

	/** The message as received. Its keys vary by event and by the server side filter. */
	public JSONObject getBody() {
		return body;
	}

	/** Convenience for a single key, null when the filter did not include it. */
	public Object get(String key) {
		return body.get(key);
	}
}
