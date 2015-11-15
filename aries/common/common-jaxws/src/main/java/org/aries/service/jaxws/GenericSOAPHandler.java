package org.aries.service.jaxws;

import java.util.HashSet;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;


/**
 * A generic jaxws soap handler
 *
 */
public abstract class GenericSOAPHandler<C extends LogicalMessageContext> extends GenericHandler implements SOAPHandler {

	// The header blocks that can be processed by this Handler instance
	private Set<QName> headers = new HashSet<QName>();

	/** 
	 * Gets the header blocks that can be processed by this Handler instance.
	 */
	public Set<QName> getHeaders() {
		return headers;
	}

	/** 
	 * Sets the header blocks that can be processed by this Handler instance.
	 */
	public void setHeaders(Set<QName> headers) {
		this.headers = headers;
	}

}
