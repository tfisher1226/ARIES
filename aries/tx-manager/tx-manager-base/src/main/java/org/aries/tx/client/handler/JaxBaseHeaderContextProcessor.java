package org.aries.tx.client.handler;

import java.util.Iterator;

import javax.xml.soap.Name;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.tx.TransactionContext;
import org.aries.tx.TransactionContextImpl;
import org.aries.tx.TransactionContextManager;
import org.aries.tx.util.SOAPUtil;

import common.tx.CoordinationConstants;
import common.tx.model.context.CoordinationContextType;


/**
 * Common base class for classes used to perform
 * WS-Transaction context manipulation on SOAP messages.
 *
 */
class JaxBaseHeaderContextProcessor {

	private static Log log = LogFactory.getLog(JaxBaseHeaderContextProcessor.class);

	private String transactionId;

	private String coordinatorId;

	private String participantId;

	private Object correlationId;

	
    public JaxBaseHeaderContextProcessor() {
    	//do nothing for now
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getCoordinatorId() {
		return coordinatorId;
	}

	public void setCoordinatorId(String coordinatorId) {
		this.coordinatorId = coordinatorId;
	}

	public String getParticipantId() {
		return participantId;
	}

	public void setParticipantId(String participantId) {
		this.participantId = participantId;
	}

	public Object getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(Object correlationId) {
		this.correlationId = correlationId;
	}

	public boolean handleOutboundMessage(final SOAPMessage soapMessage) {
		if (soapMessage == null) {
			return true;
		}

		try {
			/*
			 * There should either be an Atomic Transaction *or* a Business Activity
			 * associated with the thread.
			 */
			
//			TransactionManager transactionManager = TransactionManagerFactory.getTransactionManager();
//			if (transactionManager == null) {
//				return true;
//			}
//			
//			CoordinationContextType coordinationContext = null;
//			if (transactionManager != null) {
//				TransactionContextImpl transactionContext = (TransactionContextImpl) transactionManager.currentTransaction();
//				coordinationContext = transactionContext != null ? transactionContext.getCoordinationContextType() : null;
//			}

			TransactionContextImpl transactionContext = (TransactionContextImpl) TransactionContextManager.getInstance().getTransactionContext();
			CoordinationContextType coordinationContext = transactionContext != null ? transactionContext.getCoordinationContextType() : null;
			
			SOAPEnvelope envelope = soapMessage.getSOAPPart().getEnvelope();
			SOAPHeader header = envelope.getHeader();
			if (header == null)
				header = envelope.addHeader();

			String transactionId = null;
			if (getTransactionId() != null) {
				transactionId = getTransactionId();
			}
			
			SOAPElement simpleHeaderElement = header.addChildElement("SimpleHeader", "simpleHeader", "simpleHeader:www.aries.org");
			SOAPElement contextElement = simpleHeaderElement.addChildElement("Context");
			SOAPElement transactionIdElement = simpleHeaderElement.addChildElement("TransactionId");
			SOAPElement coordinatorIdElement = simpleHeaderElement.addChildElement("CoordinatorId");
			SOAPElement participantIdElement = simpleHeaderElement.addChildElement("ParticipantId");
			SOAPElement correlationIdElement = simpleHeaderElement.addChildElement("CorrelationId");
			
			if (coordinationContext != null) {
				if (transactionId == null)
					transactionId = coordinationContext.getIdentifier().getValue();
				
				//TMP FOR NOW
				if (transactionId.startsWith("urn:"))
					transactionId = transactionId.substring(4);
				
				contextElement.addNamespaceDeclaration(CoordinationConstants.WSCOOR_PREFIX, CoordinationConstants.WSCOOR_NAMESPACE);
				//TODO contextElement.setMustUnderstand(true);
				CoordinationContextHelper.serialise(coordinationContext, contextElement);
			}
			
			if (transactionId != null)
				transactionIdElement.addTextNode(transactionId);

			if (coordinatorId != null)
				coordinatorIdElement.addTextNode(coordinatorId);

			if (participantId != null)
				participantIdElement.addTextNode(participantId);

			if (correlationId != null)
				correlationIdElement.addTextNode(correlationId.toString());

		} catch (Throwable e) {
			log.error("Error", e);
		}

		return true;
	}

	/**
	 * Resume the current transaction.
	 *
	 */
	protected void resumeTransaction(final SOAPMessage soapMessage) {
		if (soapMessage != null) {
			try {
				SOAPEnvelope soapEnvelope = soapMessage.getSOAPPart().getEnvelope();
				SOAPHeaderElement soapHeaderElement = getHeaderElement(soapEnvelope, CoordinationConstants.WSCOOR_NAMESPACE, CoordinationConstants.WSCOOR_ELEMENT_COORDINATION_CONTEXT);

				if (soapHeaderElement != null) {
					CoordinationContextType coordinationContext = CoordinationContextHelper.deserialise(soapHeaderElement);
					if (coordinationContext != null) {
						String coordinationType = coordinationContext.getCoordinationType();
						if (CoordinationConstants.WSAT_PROTOCOL.equals(coordinationType)) {
							TransactionContext transactionContext = new TransactionContextImpl(coordinationContext);
							TransactionContextManager.getInstance().resume(transactionContext);

							//TransactionManager transactionManager = TransactionManager.getTransactionManager();
							//transactionManager.resume(transactionContext);

						//} else if (BusinessActivityConstants.WSBA_PROTOCOL_ATOMIC_OUTCOME.equals(coordinationType)) {
						//	TransactionContext txContext = new TransactionContextImpl(coordinationContext);
						//	BusinessActivityManagerFactory.businessActivityManager().resume(txContext);
							
						} else {
							log.error("Unknown context type: "+coordinationType);
						}
					}
				}

			} catch (Throwable e) {
				log.error("Error", e);
			}
		}
	}

	/**
	 * Retrieve the first header matching the uri and name.
	 * @param soapEnvelope The soap envelope containing the header.
	 * @param uri The uri of the header element.
	 * @param name The name of the header element.
	 * @return The header element or null if not found.
	 */
	private SOAPHeaderElement getHeaderElement(final SOAPEnvelope soapEnvelope, final String uri, final String name) throws SOAPException {
		final SOAPHeader soapHeader = soapEnvelope.getHeader();
		if (soapHeader != null) {
			Iterator headerIter = SOAPUtil.getChildElements(soapHeader);
			while (headerIter.hasNext()) {
				SOAPHeaderElement current = (SOAPHeaderElement)headerIter.next();
				Name currentName = current.getElementName();
				if ((currentName != null) &&
					match(name, currentName.getLocalName()) &&
					match(uri, currentName.getURI())) {
					return current;
				}
			}
		}
		return null;
	}

	/**
	 * Do the two references match?
	 * @param lhs The first reference.
	 * @param rhs The second reference.
	 * @return true if the references are both null or if they are equal.
	 */
	private boolean match(final Object lhs, final Object rhs) {
		if (lhs == null)
			return (rhs == null);
		return lhs.equals(rhs);
	}
}
