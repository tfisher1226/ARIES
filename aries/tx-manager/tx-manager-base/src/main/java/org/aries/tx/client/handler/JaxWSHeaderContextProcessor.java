package org.aries.tx.client.handler;

import java.util.HashSet;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import common.tx.CoordinationConstants;

/**
 * The class is used to perform WS-Transaction context insertion
 * and extraction for application level SOAP messages using JaxWS.
 * This is the client side version.
 */
public class JaxWSHeaderContextProcessor extends JaxBaseHeaderContextProcessor implements SOAPHandler<SOAPMessageContext> {

	public JaxWSHeaderContextProcessor() {
		//nothing for now
	}

	public JaxWSHeaderContextProcessor(String transactionId) {
		setTransactionId(transactionId);
	}

	/**
	 * Process a message. Determines if it is inbound or outbound and dispatches accordingly.
	 *
	 * @param msgContext
	 * @return true
	 */
	public boolean handleMessage(SOAPMessageContext msgContext) {
		Boolean outbound = (Boolean)msgContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (outbound == null)
			throw new IllegalStateException("Cannot obtain required property: " + MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (outbound) 
			return handleOutbound(msgContext);
		return handleInbound(msgContext);
	}

	/**
	 * Tidy up the Transaction/Thread association.
	 *
	 * @param messageContext
	 * @return true
	 */
	public boolean handleFault(SOAPMessageContext messageContext) {
		SOAPMessageContext soapMessageContext = (SOAPMessageContext)messageContext;
		SOAPMessage soapMessage = soapMessageContext.getMessage();
		resumeTransaction(soapMessage);
		return true;
	}

	public void close(MessageContext messageContext) {
	}

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
	 * Tidy up the Transaction/Thread association before control is returned to the user.
	 *
	 * @param messageContext
	 * @return true
	 */
	protected boolean handleInbound(SOAPMessageContext messageContext) {
		SOAPMessage soapMessage = messageContext.getMessage();
		resumeTransaction(soapMessage);
		return true;
	}

	/**
	 * Process the tx thread context and attach serialized version as msg header
	 *
	 * @param messageContext
	 * @return true
	 */
	protected boolean handleOutbound(SOAPMessageContext messageContext) {
		SOAPMessage soapMessage = messageContext.getMessage();
		return handleOutboundMessage(soapMessage);
	}
}