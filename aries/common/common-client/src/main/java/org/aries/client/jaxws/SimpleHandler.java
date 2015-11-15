package org.aries.client.jaxws;

import java.util.Set;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.Text;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.MessageContext.Scope;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class SimpleHandler implements SOAPHandler<SOAPMessageContext> {

	private static Log log = LogFactory.getLog(SimpleHandler.class);

	private String userName;

	private String password;

	private String systemId;

	private String correlationId;


	public SimpleHandler() {
		System.out.println();
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public boolean handleMessage(SOAPMessageContext messageContext) {
		// constructing custom soap header
		Boolean outboundProperty = (Boolean) messageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

		if (outboundProperty.booleanValue()) {
			SOAPMessage message = messageContext.getMessage();

			if (userName == null)
				userName = "PLACEHOLDER";
			if (password == null)
				password = "PLACEHOLDER";
			if (systemId == null)
				systemId = "PLACEHOLDER";
			if (correlationId == null)
				correlationId = "PLACEHOLDER";
			
			try {
				SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
				SOAPHeader header = envelope.addHeader();
				SOAPElement element = header.addChildElement("Security", "wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
				element.addTextNode(correlationId);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}

		} else {
			try {
				SOAPMessage message = messageContext.getMessage();
				SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
				SOAPHeader header = envelope.getHeader();
				SOAPElement element = (SOAPElement) header.getFirstChild();
				Text correlationIdText = (Text) element.getFirstChild();
				if (correlationIdText != null) {
					messageContext.put("correlationId", correlationId);
					messageContext.setScope("correlationId", Scope.APPLICATION);
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		return outboundProperty;
	}

	public Set getHeaders() {
		// throw new UnsupportedOperationException("Not supported yet.");
		return null;
	}

	public boolean handleFault(SOAPMessageContext context) {
		// throw new UnsupportedOperationException("Not supported yet.");
		return true;
	}

	public void close(MessageContext context) {
		// throw new UnsupportedOperationException("Not supported yet.");
	}

}
