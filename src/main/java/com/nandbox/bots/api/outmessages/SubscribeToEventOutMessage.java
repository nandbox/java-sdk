package com.nandbox.bots.api.outmessages;

/**
 * Subscribes an account to one or more events of an app.
 *
 * <pre>
 * SubscribeToEventOutMessage msg = new SubscribeToEventOutMessage();
 * msg.setApp_id("1234");
 * msg.setEvent("product");            // or setEvents(Arrays.asList("product", "order"))
 * </pre>
 *
 * The reply is an {@code eventResponse}, delivered to
 * {@code Callback.onEventResponse(EventResponse)}. Subscribing twice is harmless. From then on
 * every matching change arrives as an {@code eventMessage}.
 *
 * <p>
 * The account has to be a member of the app, and events with privileges attached
 * ({@code product}, {@code chat}, {@code chatMember}, {@code order}) also require the matching
 * privilege, otherwise the reply carries an error and ack false.
 * </p>
 */
public class SubscribeToEventOutMessage extends EventSubscriptionOutMessage {

	public SubscribeToEventOutMessage() {
		this.method = OutMessageMethod.subscribeToEvent;
	}
}
