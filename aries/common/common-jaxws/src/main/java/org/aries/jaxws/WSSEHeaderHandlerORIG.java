package org.aries.jaxws;

import java.util.Set;

import javax.servlet.ServletRequest;
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


public class WSSEHeaderHandlerORIG implements SOAPHandler<SOAPMessageContext> {

	private static Log log = LogFactory.getLog(WSSEHeaderHandlerORIG.class);

	private String userName;

	private String password;

	private String systemId;

	private String correlationId;

	private boolean logHeaderEnabled;


	public WSSEHeaderHandlerORIG() {
		//nothin for now
	}

	public Set getHeaders() {
		// throw new UnsupportedOperationException("Not supported yet.");
		return null;
	}
	
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	/**
	 * Constructs custom soap header to support transmission of WSSE information.
	 */
	public boolean handleMessage(SOAPMessageContext messageContext) {
		Boolean outboundProperty = (Boolean) messageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

		//this to store local values passed to/from local services
		ServletRequest servletRequest = (ServletRequest) messageContext.get(MessageContext.SERVLET_REQUEST);

		//outgoing header processing
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

			//userName = (String) servletRequest.getAttribute("org.aries.userName");
			//password = (String) servletRequest.getAttribute("org.aries.password");
			//systemId = (String) servletRequest.getAttribute("org.aries.systemId");
			//correlationId = (String) servletRequest.getAttribute("org.aries.correlationId");

			try {
				SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
				SOAPHeader header = envelope.addHeader();

				SOAPElement securityElement = header.addChildElement("Security", "wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
				SOAPElement usernameTokenElement = securityElement.addChildElement("UsernameToken", "wsse");
				SOAPElement usernameElement = usernameTokenElement.addChildElement("Username", "wsse");
				usernameElement.addTextNode(userName);

				SOAPElement passwordElement = usernameTokenElement.addChildElement("Password", "wsse");
				passwordElement.setAttribute("Type", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");
				passwordElement.addTextNode(password);

				SOAPElement systemContextElement = header.addChildElement("systemContext", "urn", "urn:pnc.common.utils.v1.context");
				SOAPElement systemIdElement = systemContextElement.addChildElement("systemId");
				systemIdElement.addTextNode(systemId);

				SOAPElement correlationIdElement = systemContextElement.addChildElement("correlationId");
				correlationIdElement.addTextNode(correlationId);

				if (logHeaderEnabled) {
					logMessageContent(message);
				}
				
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return false;
			}

		} else {
			//incoming header processing
			if (servletRequest == null) {
				//coming back from Oneway call
				return true;
			}

			try {
				SOAPMessage message = messageContext.getMessage();
				SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
				SOAPHeader header = envelope.getHeader();
				
				SOAPElement securityElement = (SOAPElement) header.getFirstChild();
				SOAPElement usernameTokenElement = (SOAPElement) securityElement.getFirstChild();
				SOAPElement usernameElement = (SOAPElement) usernameTokenElement.getFirstChild();
				Text usernameText = (Text) usernameElement.getFirstChild();
				if (userName != null && userName.equals(usernameText))
					throw new RuntimeException("Invalid user: "+usernameText);
				
				SOAPElement passwordElement = (SOAPElement) usernameElement.getNextSibling();
				Text passwordText = (Text) passwordElement.getFirstChild();
				if (password != null && password.equals(passwordText)) {
					throw new RuntimeException("Invalid password for user: "+userName);
				}

				SOAPElement systemContextElement = (SOAPElement) securityElement.getNextSibling();
				SOAPElement systemIdElement = (SOAPElement) systemContextElement.getFirstChild();
				Text systemIdText = (Text) systemIdElement.getFirstChild();
				
				SOAPElement correlationIdElement = (SOAPElement) systemIdElement.getNextSibling();
				Text correlationIdText = (Text) correlationIdElement.getFirstChild();
				if (correlationIdText != null) {
					messageContext.put("correlationId", correlationId);
					messageContext.setScope("correlationId", Scope.APPLICATION);
				}

				if (servletRequest != null) {
					servletRequest.setAttribute("userName", usernameText.getData());
					servletRequest.setAttribute("password", passwordText.getData());
					servletRequest.setAttribute("systemId", systemIdText.getData());
					servletRequest.setAttribute("correlationId", correlationIdText.getData());
				}
				
				if (logHeaderEnabled) {
					logMessageContent(message);
				}
				
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return false;
			}
		}

		return true;
	}

	protected void logMessageContent(SOAPMessage message) throws Exception {
		log.debug("SOAP Request: " + JAXWSUtil.getMessageContentAsString(message));
	}

	public boolean handleFault(SOAPMessageContext context) {
		// throw new UnsupportedOperationException("Not supported yet.");
		String content = JAXWSUtil.getMessageContentAsString(context.getMessage());
		log.warn("SOAP Fault: " + content);
		return true;
	}

	public void close(MessageContext context) {
		// throw new UnsupportedOperationException("Not supported yet.");
	}

}
