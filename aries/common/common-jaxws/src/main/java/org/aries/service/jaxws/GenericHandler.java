package org.aries.service.jaxws;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.MessageContext;


/**
 * A generic jaxws handler
 *
 */
public abstract class GenericHandler implements Handler {

	private String handlerName;

	public String getHandlerName() {
		return handlerName;
	}

	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}

	public boolean handleMessage(MessageContext msgContext) {
		Boolean outbound = (Boolean)msgContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (outbound == null)
			throw new IllegalStateException("Cannot obtain required property: " + MessageContext.MESSAGE_OUTBOUND_PROPERTY);

		return outbound ? handleOutbound(msgContext) : handleInbound(msgContext);
	}

	protected boolean handleOutbound(MessageContext msgContext) {
		return true;
	}

	protected boolean handleInbound(MessageContext msgContext) {
		return true;
	}

	public boolean handleFault(MessageContext messagecontext) {
		return true;
	}

	public void close(MessageContext messageContext) {
	}

	public String toString() {
		return (handlerName != null ? handlerName : super.toString());
	}

}
