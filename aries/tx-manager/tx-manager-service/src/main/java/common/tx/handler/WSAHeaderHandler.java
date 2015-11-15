package common.tx.handler;

import java.util.Set;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.Text;
import javax.xml.ws.ProtocolException;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.MessageContext.Scope;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.jaxws.JAXWSUtil;

import common.tx.InstanceIdentifier;


public class WSAHeaderHandler implements SOAPHandler<SOAPMessageContext> {

	private static Log log = LogFactory.getLog(WSAHeaderHandler.class);

	private String userName;

	private String password;

	private String systemId;

	private String correlationId;

	private InstanceIdentifier instanceId;

	private boolean logHeaderEnabled;


	public WSAHeaderHandler() {
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
	 * Handle an outgoing message by inserting any current arjuna context attached to the context into the message
	 * headers and handle an incoming message by retrieving the context from the headers and attaching it to the
	 * context,
	 *
	 * @param context the message context.
	 * @return Always return true
	 * @throws RuntimeException               Causes the JAX-WS runtime to cease
	 *                                        handler processing and generate a fault.
	 * @throws javax.xml.ws.ProtocolException Causes the JAX-WS runtime to switch to
	 *                                        fault message processing.
	 */
	public boolean handleMessage(SOAPMessageContext context) throws ProtocolException {
		boolean outbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (outbound)
			return handleOutboundMessage(context);
		return handleInboundMessage(context);
	}
	
	/**
	 * Constructs custom soap header to support transmission of WSSE information.
	 */
	public boolean handleOutboundMessage(SOAPMessageContext messageContext) {
		//this to store local values passed to/from local services
		//ServletRequest servletRequest = (ServletRequest) messageContext.get(MessageContext.SERVLET_REQUEST);

		//outgoing header processing
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

			if (logHeaderEnabled)
				logMessageContent(message);
			return true;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}
	
	/**
	 * Constructs custom soap header to support transmission of WSSE information.
	 */
	public boolean handleInboundMessage(SOAPMessageContext messageContext) {
		//incoming header processing
//		if (servletRequest == null) {
//			//coming back from Oneway call
//			return true;
//		}

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
				messageContext.put("correlationId", correlationIdText.getData());
				messageContext.setScope("correlationId", Scope.APPLICATION);

				//RuntimeContext runtimeContext = RuntimeContextRegistry.getRuntimeContext();
				//runtimeContext.setCorrelationId(correlationIdText.getData());
			}
			
//			if (servletRequest != null) {
//				servletRequest.setAttribute("userName", usernameText.getData());
//				servletRequest.setAttribute("password", passwordText.getData());
//				servletRequest.setAttribute("systemId", systemIdText.getData());
//				if (correlationIdText != null) {
//					servletRequest.setAttribute("correlationId", correlationIdText.getData());
//				}
//			}
			
			if (logHeaderEnabled)
				logMessageContent(message);
			return true;
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
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
