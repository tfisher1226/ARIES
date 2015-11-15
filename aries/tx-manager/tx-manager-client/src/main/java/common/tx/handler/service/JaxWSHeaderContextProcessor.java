package common.tx.handler.service;

import java.util.HashSet;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import common.tx.CoordinationConstants;


/**
 * The class is used to perform WS-Transaction context insertion
 * and extraction for application level SOAP messages using JaxWS.
 * This is the server side version.
 */
public class JaxWSHeaderContextProcessor extends JaxBaseHeaderContextProcessor implements SOAPHandler<SOAPMessageContext> {
	
	/**
	 * Gets the header blocks that can be processed by this Handler instance.
	 */
	public Set<QName> getHeaders() {
		Set<QName> headerSet = new HashSet<QName>();
		headerSet.add(new QName(CoordinationConstants.WSCOOR_NAMESPACE, CoordinationConstants.WSCOOR_ELEMENT_COORDINATION_CONTEXT));
		return headerSet;
	}

	/**
	 * Sets the header blocks that can be processed by this Handler instance.
	 * Note: this impl ignores this function's args as the values are hardcoded.
	 */
	public void setHeaders(Set headers) {
	}

	/**
	 * Process a message. Determines if it is inbound or outbound and dispatches accordingly.
	 * @param msgContext
	 * @return true
	 */
	public boolean handleMessage(SOAPMessageContext msgContext) {
		Boolean outbound = (Boolean) msgContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (outbound == null)
			throw new IllegalStateException("Outbound property not found");
		if (outbound) 
			return handleOutboundMessage(msgContext);
		return handleInboundMessage(msgContext);
	}

	/**
	 * Process the tx context header that is attached to the received message.
	 * @param messageContext The current message context.
	 * @return true
	 */
	protected boolean handleInboundMessage(SOAPMessageContext messageContext) {
		return handleInboundMessage(messageContext, false);
	}


	/**
	 * Tidy up the Transaction/Thread association before response is returned to the client.
	 * @param messageContext The current message context.
	 * @return true
	 */
	protected boolean handleOutboundMessage(SOAPMessageContext messageContext) {
		suspendTransaction() ;
		return true;
	}

	/**
	 * Tidy up the Transaction/Thread association before faults are thrown back to the client.
	 *
	 * @param messageContext
	 * @return true
	 */
	public boolean handleFault(SOAPMessageContext messageContext) {
		suspendTransaction() ;
		return true;
	}

	public void close(MessageContext messageContext) {
	}

}
