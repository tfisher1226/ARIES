package org.aries.client.jaxws;


import java.io.ByteArrayOutputStream;
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


public class JAXWSHeaderHandler implements SOAPHandler<SOAPMessageContext> {

	private static Log log = LogFactory.getLog(JAXWSHeaderHandler.class);

	private String userName;

	private String password;

	private String systemId;

	private Object correlationId;


	public JAXWSHeaderHandler(String userName, String password, String systemId, Object correlationId) {
		this.userName = userName;
		this.password = password;
		this.systemId = systemId;
		this.correlationId = correlationId;
	}

	public void setCorrelationId(Object correlationId) {
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
			
			messageContext.put("correlationId", correlationId);
			messageContext.setScope("correlationId", Scope.APPLICATION);
			
			try {
				SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
				SOAPHeader header = envelope.addHeader();

				SOAPElement securityEle = header.addChildElement("Security", "wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
				SOAPElement usernameTokenEle = securityEle.addChildElement("UsernameToken", "wsse");
				SOAPElement usernameEle = usernameTokenEle.addChildElement("Username", "wsse");
				usernameEle.addTextNode(userName);
				
				SOAPElement passwordEle = usernameTokenEle.addChildElement("Password", "wsse");
				passwordEle.setAttribute("Type", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");
				passwordEle.addTextNode(password);

				SOAPElement systemContextEle = header.addChildElement("systemContext", "urn", "urn:pnc.common.utils.v1.context");
				SOAPElement systemIdEle = systemContextEle.addChildElement("systemId");
				systemIdEle.addTextNode(systemId);
				
				SOAPElement correlationIdEle = systemContextEle.addChildElement("correlationId");
				correlationIdEle.addTextNode(correlationId.toString());

				// Print out the outbound SOAP message to log
				ByteArrayOutputStream soapReq = new ByteArrayOutputStream();
				message.writeTo(soapReq);
				log.debug("SOAP Request for Operation getOpportunitiesByUser " + new String(soapReq.toByteArray()));
				soapReq.close();
				
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			
		} else {
			try {
				// This handler does nothing with the response from the Web
				// Service so we just log the SOAP message.
				SOAPMessage message = messageContext.getMessage();
				SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
				SOAPHeader header = envelope.getHeader();
				
				SOAPElement securityElement = (SOAPElement) header.getFirstChild();
				SOAPElement usernameTokenElement = (SOAPElement) securityElement.getFirstChild();
				SOAPElement usernameElement = (SOAPElement) usernameTokenElement.getFirstChild();
				Text usernameText = (Text) usernameElement.getFirstChild();
				if (userName != null && userName.equals(usernameText)) {
					throw new RuntimeException("Invalid user: "+usernameText);
				}

				SOAPElement passwordElement = (SOAPElement) usernameElement.getNextSibling();
				Text passwordText = (Text) passwordElement.getFirstChild();
				if (password != null && password.equals(passwordText)) {
					throw new RuntimeException("Invalid password for user: "+userName);
				}

				SOAPElement systemContextElement = (SOAPElement) securityElement.getNextSibling();
				SOAPElement systemIdElement = (SOAPElement) systemContextElement.getFirstChild();
				SOAPElement correlationIdElement = (SOAPElement) systemIdElement.getNextSibling();
				Text correlationIdText = (Text) correlationIdElement.getFirstChild();
				if (correlationIdText != null) {
					messageContext.put("correlationId", correlationId);
					messageContext.setScope("correlationId", Scope.APPLICATION);
				}
				
				ByteArrayOutputStream soapRes = new ByteArrayOutputStream();
				message.writeTo(soapRes);
				log.debug("SOAP Response for Operation getOpportunitiesByUser " + new String(soapRes.toByteArray()));
				soapRes.close();
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
