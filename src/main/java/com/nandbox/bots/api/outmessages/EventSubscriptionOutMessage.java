package com.nandbox.bots.api.outmessages;

import java.util.ArrayList;
import java.util.List;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 * Shared body of {@link SubscribeToEventOutMessage} and
 * {@link UnsubscribeFromEventOutMessage}. Both carry the same fields and differ only in method.
 *
 * <p>
 * An event is a stream of app activity - {@code product}, {@code chat}, {@code chatMember},
 * {@code content}, {@code order} - that the server pushes as an {@code eventMessage} to every
 * subscribed account.
 * </p>
 */
public abstract class EventSubscriptionOutMessage extends OutMessage {

	protected static final String KEY_EVENT = "event";
	protected static final String KEY_EVENTS = "events";
	protected static final String KEY_ACCOUNT_ID = "account_id";

	private List<String> events = new ArrayList<String>();
	private String accountId;

	@Override
	public JSONObject toJsonObject() {
		JSONObject obj = super.toJsonObject();
		if (!events.isEmpty()) {
			if (events.size() == 1) {
				obj.put(KEY_EVENT, events.get(0));
			}
			else {
				JSONArray array = new JSONArray();
				array.addAll(events);
				obj.put(KEY_EVENTS, array);
			}
		}
		if (accountId != null) {
			obj.put(KEY_ACCOUNT_ID, accountId);
		}
		return obj;
	}

	public List<String> getEvents() {
		return events;
	}

	/** Replaces the current selection. */
	public void setEvents(List<String> events) {
		this.events = events == null ? new ArrayList<String>() : new ArrayList<String>(events);
	}

	/** Convenience for the common single event case. */
	public void setEvent(String event) {
		this.events = new ArrayList<String>();
		addEvent(event);
	}

	public void addEvent(String event) {
		if (event != null && !events.contains(event)) {
			events.add(event);
		}
	}

	public String getAccountId() {
		return accountId;
	}

	/**
	 * Leave unset to act on the calling account. Setting another account requires the caller to
	 * be an admin of the app, and the server replies with a NoPrivilege error otherwise.
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
}
