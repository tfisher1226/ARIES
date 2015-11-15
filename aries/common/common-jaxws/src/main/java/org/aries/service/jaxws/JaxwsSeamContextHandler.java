package org.aries.service.jaxws;

import java.util.HashSet;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class JaxwsSeamContextHandler implements SOAPHandler<SOAPMessageContext> {

	private static Log log = LogFactory.getLog(JaxwsSeamContextHandler.class);


	/**
	 * Gets the header blocks that can be processed by this Handler instance.
	 */
	public Set<QName> getHeaders() {
		Set<QName> headerSet = new HashSet<QName>();
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
	public boolean handleMessage(SOAPMessageContext soapMessageContext) {
		Boolean outbound = (Boolean) soapMessageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (outbound == null)
			throw new IllegalStateException("Outbound property not found");
		if (outbound) 
			return handleOutboundMessage(soapMessageContext);
		return handleInboundMessage(soapMessageContext);
	}

	/**
	 * Process the tx context header that is attached to the received message.
	 * @param messageContext The current message context.
	 * @return true
	 */
	protected boolean handleInboundMessage(SOAPMessageContext soapMessageContext) {
		try {
			//TODO uncomment this to support Seam
			//if (Lifecycle.isApplicationInitialized())
			//	Lifecycle.beginCall();
			return true;
		} catch (Throwable e) {
			log.error(e);
			return false;
		}
	}


	/**
	 * Tidy up the Transaction/Thread association before response is returned to the client.
	 * @param messageContext The current message context.
	 * @return true
	 */
	protected boolean handleOutboundMessage(SOAPMessageContext soapMessageContext) {
		try {
			return true;
		} catch (Throwable e) {
			log.error(e);
			return false;
		}
	}

	/**
	 * Tidy up the Transaction/Thread association before faults are thrown back to the client.
	 * @param messageContext
	 * @return true
	 */
	public boolean handleFault(SOAPMessageContext soapMessageContext) {
		try {
			return true;
		} catch (Throwable e) {
			log.error(e);
			return false;
		}
	}

	public void close(MessageContext messageContext) {
	}
	
}
