package org.aries.tx.client.handler;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.Text;
import javax.xml.ws.ProtocolException;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import common.tx.CoordinationConstants;
import common.tx.InstanceIdentifier;
import common.tx.model.Header;


public class InstanceIdentifierHandler implements SOAPHandler<SOAPMessageContext> {

	private static Set<QName> headers = Collections.singleton(CoordinationConstants.WSARJ_ELEMENT_INSTANCE_IDENTIFIER_QNAME);

	/**
	 * Gets the header blocks that can be processed by this Handler
	 * instance.
	 *
	 * @return Set of QNames of header blocks processed by this
	 *         handler instance. <code>QName</code> is the qualified
	 *         name of the outermost element of the Header block.
	 */
	public Set<QName> getHeaders() {
		return headers;
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
			return handleMessageOutbound(context);
		return handleInboundMessage(context);
	}

	/**
	 * check for an arjuna context attached to the message context and, if found, install its identifier as the value
	 * of a soap message header element
	 */
	protected boolean handleMessageOutbound(SOAPMessageContext soapMessageContext) throws ProtocolException {
		try {
			Header header = (Header) soapMessageContext.get(Header.HEADER_PROPERTY);
			
			if (header != null) {
				InstanceIdentifier instanceIdentifier = header.getInstanceIdentifier();
				// insert a header into the current message containing the instance identifier as a text element
				SOAPMessage soapMessage = soapMessageContext.getMessage();
				SOAPEnvelope soapEnvelope = soapMessage.getSOAPPart().getEnvelope();
				SOAPHeader soapHeader = soapEnvelope.getHeader();
				if (soapHeader == null)
					soapHeader = soapEnvelope.addHeader();
				SOAPHeaderElement headerElement = soapHeader.addHeaderElement(CoordinationConstants.WSARJ_ELEMENT_INSTANCE_IDENTIFIER_QNAME);
				if (instanceIdentifier != null)
					headerElement.setValue(instanceIdentifier.getInstanceIdentifier());
				headerElement.setMustUnderstand(true);
			}
		} catch (Exception e) {
			throw new ProtocolException(e);
		}

		return true;
	}

	/**
	 * check for an arjuna instance identifier element embedded in the soap message headesr and, if found, use it to
	 * label an arjuna context attached to the message context
	 */
	protected boolean handleInboundMessage(SOAPMessageContext context)  throws ProtocolException {
		try {
			SOAPMessage soapMessage = context.getMessage();
			SOAPEnvelope soapEnvelope = soapMessage.getSOAPPart().getEnvelope();
			SOAPHeader soapHeader = soapEnvelope.getHeader();
			@SuppressWarnings("unchecked") Iterator<SOAPHeaderElement> iterator = soapHeader.examineAllHeaderElements();
			while (iterator.hasNext()) {
				SOAPHeaderElement headerElement = iterator.next();
				if (CoordinationConstants.WSARJ_ELEMENT_INSTANCE_IDENTIFIER_QNAME.equals(headerElement.getElementQName())) {
					// found it - clear the must understand flag, retrieve the value and store an arjuna
					// context in the message context
					headerElement.setMustUnderstand(false);
					String identifierString = headerElement.getValue();
					if (identifierString != null) {
						Header header = Header.getOrCreateHeader(context);
						header.setInstanceIdentifier(new InstanceIdentifier(identifierString));
						//break;
					}
				}
				if (headerElement.getElementName().getLocalName().equals("SimpleHeader")) {
					Header header = Header.getOrCreateHeader(context);
					//String identifierString = headerElement.getValue();

					SOAPElement contextElement = (SOAPElement) headerElement.getFirstChild();
					//TODO we need to access CoordinationContext here or not?
					
					SOAPElement instanceIdElement = (SOAPElement) contextElement.getNextSibling();
					Text instanceIdText = (Text) instanceIdElement.getFirstChild();
					if (instanceIdText != null)
						header.setInstanceIdentifier(new InstanceIdentifier(instanceIdText.getData()));

					SOAPElement coordinatorIdElement = (SOAPElement) instanceIdElement.getNextSibling();
					Text coordinatorIdText = (Text) coordinatorIdElement.getFirstChild();
					if (coordinatorIdText != null)
						header.setCoordinatorId(coordinatorIdText.getData());

					SOAPElement participantIdElement = (SOAPElement) coordinatorIdElement.getNextSibling();
					Text participantIdText = (Text) participantIdElement.getFirstChild();
					if (participantIdText != null)
						header.setParticipantId(participantIdText.getData());

					SOAPElement correlationIdElement = (SOAPElement) participantIdElement.getNextSibling();
					Text correlationIdText = (Text) correlationIdElement.getFirstChild();
					if (correlationIdText != null)
						header.setCorrelationId(correlationIdText.getData());
				}
			}
		} catch (Exception se) {
			throw new ProtocolException(se);
		}

		return true;
	}

	/**
	 * This handler ignores faults but allows other handlers to deal with them.
	 * @param context the message context
	 * @return true to allow fault handling to continue
	 */
	public boolean handleFault(SOAPMessageContext context) {
		return true;
	}

	/**
	 * This handler ignores close messages.
	 * @param context the message context
	 */
	public void close(MessageContext context) {
	}

}
