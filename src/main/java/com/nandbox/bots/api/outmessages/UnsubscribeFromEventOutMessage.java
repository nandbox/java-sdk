package com.nandbox.bots.api.outmessages;

/**
 * Stops delivery of one or more events to an account.
 *
 * <pre>
 * UnsubscribeFromEventOutMessage msg = new UnsubscribeFromEventOutMessage();
 * msg.setApp_id("1234");
 * msg.setEvent("product");
 * </pre>
 *
 * The reply is an {@code eventResponse}. Unsubscribing from something you are not subscribed to
 * still acks. Unsubscribing yourself never requires a privilege.
 */
public class UnsubscribeFromEventOutMessage extends EventSubscriptionOutMessage {

	public UnsubscribeFromEventOutMessage() {
		this.method = OutMessageMethod.unsubscribeFromEvent;
	}
}
