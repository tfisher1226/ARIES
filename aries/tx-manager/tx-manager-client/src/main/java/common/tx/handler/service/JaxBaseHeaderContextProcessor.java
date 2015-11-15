package common.tx.handler.service;

import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.Text;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.tx.TransactionContext;
import org.aries.tx.TransactionContextImpl;
import org.aries.tx.TransactionManager;
import org.aries.tx.client.handler.CoordinationContextHelper;
import org.aries.tx.message.MessageUtil;
import org.aries.tx.util.SOAPUtil;

import common.tx.CoordinationConstants;
import common.tx.InstanceIdentifier;
import common.tx.model.Header;
import common.tx.model.context.CoordinationContextType;


/**
 * Common base class for classes used to perform
 * WS-Transaction context manipulation on SOAP messages.
 */
public class JaxBaseHeaderContextProcessor {

	private static Log log = LogFactory.getLog(JaxBaseHeaderContextProcessor.class);


	/**
	 * Process the tx context header that is attached to the received message.
	 * @param installSubordinateTx true if a subordinate transaction should be interposed and false
	 * if the handler should just resume the incoming transaction. currently only works for AT
	 * transactions but will eventually be extended to work for BA transactions too.
	 */
	protected boolean handleInboundMessage(SOAPMessageContext soapMessageContext, boolean installSubordinateTx) {
		SOAPMessage soapMessage = soapMessageContext.getMessage();

		if (soapMessage != null) {
			try {
				SOAPEnvelope soapEnvelope = soapMessage.getSOAPPart().getEnvelope();
				SOAPHeader soapHeader = soapEnvelope.getHeader();

				@SuppressWarnings("unchecked") Iterator<SOAPHeaderElement> iterator = soapHeader.examineAllHeaderElements();
				while (iterator.hasNext()) {
					SOAPHeaderElement soapHeaderElement = iterator.next();
					Name soapHeaderElementName = soapHeaderElement.getElementName();
					QName soapHeaderElementQName = soapHeaderElement.getElementQName();
					String soapHeaderElementLocalName = soapHeaderElementName.getLocalName();
					
					if (CoordinationConstants.WSARJ_ELEMENT_INSTANCE_IDENTIFIER_QNAME.equals(soapHeaderElementQName)) {
						// found it - clear the must understand flag, retrieve the value and store an arjuna
						// context in the message context
						soapHeaderElement.setMustUnderstand(false);
						String identifierString = soapHeaderElement.getValue();
						if (identifierString != null) {
							Header header = Header.getOrCreateHeader(soapMessageContext);
							header.setInstanceIdentifier(new InstanceIdentifier(identifierString));
							break;
						}
					}
					
					if (soapHeaderElementName != null &&
						match(CoordinationConstants.WSCOOR_ELEMENT_COORDINATION_CONTEXT, soapHeaderElementName.getLocalName()) &&
						match(CoordinationConstants.WSCOOR_NAMESPACE, soapHeaderElementName.getURI())) {
						
						CoordinationContextType coordinationContext = CoordinationContextHelper.deserialise(soapHeaderElement);
						String coordinationType = coordinationContext.getCoordinationType();
						if (CoordinationConstants.WSAT_PROTOCOL.equals(coordinationType)) {
							clearMustUnderstand(soapHeader, soapHeaderElement);
							TransactionContext transactionContext = new TransactionContextImpl(coordinationContext);
							if (installSubordinateTx)
								transactionContext = SubordinateTransactionImportClient.importContext(coordinationContext);
							TransactionManager transactionManager = TransactionManager.getTransactionManager();
							transactionManager.resume(transactionContext);

						//} else if (BusinessActivityConstants.WSBA_PROTOCOL_ATOMIC_OUTCOME.equals(coordinationType)) {
						//	// interposition is not yet implemented for business activities
						//	clearMustUnderstand(soapHeader, soapHeaderElement);
						//	TransactionContext transactionContext = new TransactionContextImpl(cc);
						//	if (installSubordinateTx)
						//		transactionContext = com.arjuna.mwlabs.wst11.ba.SubordinateImporter.importContext(cc);
						//	BusinessActivityManagerFactory.businessActivityManager().resume(transactionContext);

						} else {
							log.error("Unknown context type: "+coordinationType);
						}

					}

//					if (soapHeaderElement.getElementName().getLocalName().equals("SimpleHeader")) {
//						SOAPElement contextElement = (SOAPElement) soapHeaderElement.getFirstChild();
//						SOAPElement instanceIdElement = (SOAPElement) contextElement.getNextSibling();
//
//						CoordinationContextType coordinationContext = CoordinationContextHelper.deserialise(contextElement);
//						if (coordinationContext != null) {
//							String coordinationType = coordinationContext.getCoordinationType();
//							if (CoordinationConstants.WSAT_PROTOCOL.equals(coordinationType)) {
//								clearMustUnderstand(soapHeader, soapHeaderElement);
//								TransactionContext transactionContext = new TransactionContextImpl(coordinationContext);
//								if (installSubordinateTx)
//									transactionContext = SubordinateTransactionImportClient.importContext(coordinationContext);
//								TransactionManager.getTransactionManager().resume(transactionContext);
//							}
//						}
//						
//						Text instanceIdText = (Text) instanceIdElement.getFirstChild();
//						if (instanceIdText != null) {
//							Header header = Header.getOrCreateHeader(soapMessageContext);
//							header.setInstanceIdentifier(new InstanceIdentifier(instanceIdText.getData()));
//							break;
//						}
//					}

					if (soapHeaderElementLocalName.equals("aries.property.transactionId")) {
					}

					if (soapHeaderElementLocalName.equals("aries.property.coordinationId")) {
					}

					if (soapHeaderElementLocalName.equals("aries.property.participantId")) {
					}

					if (soapHeaderElementLocalName.equals("aries.property.correlationId")) {
						SOAPElement correlationIdElement = (SOAPElement) soapHeaderElement.getFirstChild();
						MessageUtil.getCorrelationId(soapMessageContext);
					}

					if (soapHeaderElementLocalName.equals("SimpleHeader")) {
						Header header = Header.getOrCreateHeader(soapMessageContext);

						//TODO we need to access CoordinationContext here or not?
						SOAPElement contextElement = (SOAPElement) soapHeaderElement.getFirstChild();
						CoordinationContextType coordinationContext = CoordinationContextHelper.deserialise(contextElement);
						if (coordinationContext != null) {
							String coordinationType = coordinationContext.getCoordinationType();
							if (CoordinationConstants.WSAT_PROTOCOL.equals(coordinationType)) {
								clearMustUnderstand(soapHeader, soapHeaderElement);
								TransactionContext transactionContext = new TransactionContextImpl(coordinationContext);
								if (installSubordinateTx)
									transactionContext = SubordinateTransactionImportClient.importContext(coordinationContext);
								TransactionManager.getTransactionManager().resume(transactionContext);
							}
						}
						
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
						
//						HandlerUtil.getTransactionId(soapMessageContext);
//						HandlerUtil.getCoordinationId(soapMessageContext);
//						HandlerUtil.getParticipantId(soapMessageContext);
						
						if (correlationIdText != null)
							MessageUtil.putCorrelationId(soapMessageContext, correlationIdText.getData());

						if (instanceIdText != null)
							MessageUtil.putTransactionId(soapMessageContext, instanceIdText.getData());

					}
				}

			} catch (Throwable e) {
				log.error("Error", e);
			}
		}

		return true;
	}
	

	/**
	 * Suspend the current transaction.
	 */
	protected void suspendTransaction() {
		try {
			/*
			 * There should either be an Atomic Transaction *or* a Business Activity
			 * associated with the thread.
			 */
			TransactionManager transactionManager = TransactionManager.getTransactionManager();
			transactionManager.suspend();
			
			//BusinessActivityManager businessActivityManager = BusinessActivityManagerFactory.businessActivityManager();
			//if (businessActivityManager != null) {
			//	businessActivityManager.suspend();
			//}
			
		} catch (Throwable e) {
			log.error("Error", e);
		}
	}

	/**
	 * Clear the soap MustUnderstand.
	 * @param soapHeader The SOAP header.
	 * @param soapHeaderElement The SOAP header element.
	 */
	protected void clearMustUnderstand(final SOAPHeader soapHeader, final SOAPHeaderElement soapHeaderElement) throws SOAPException {
		final Name headerName = soapHeader.getElementName();
		final SOAPFactory factory = SOAPFactory.newInstance();
		final Name attributeName = factory.createName("mustUnderstand", headerName.getPrefix(), headerName.getURI());
		soapHeaderElement.removeAttribute(attributeName);
	}

	protected SOAPHeaderElement getHeaderElement(final SOAPHeader soapHeader, final String uri, final String name) throws SOAPException {
		if (soapHeader != null) {
			final Iterator headerIter = SOAPUtil.getChildElements(soapHeader);
			while(headerIter.hasNext()) {
				final SOAPHeaderElement current = (SOAPHeaderElement)headerIter.next();
				final Name currentName = current.getElementName();
				if (currentName != null &&
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
	protected boolean match(final Object lhs, final Object rhs) {
		if (lhs == null) {
			return (rhs == null);
		} else {
			return lhs.equals(rhs);
		}
	}

}