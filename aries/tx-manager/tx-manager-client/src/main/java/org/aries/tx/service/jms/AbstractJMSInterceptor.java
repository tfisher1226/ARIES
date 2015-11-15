package org.aries.tx.service.jms;

import javax.interceptor.InvocationContext;
import javax.jms.Message;
import javax.xml.ws.wsaddressing.W3CEndpointReference;
import javax.xml.ws.wsaddressing.W3CEndpointReferenceBuilder;

import nam.model.Endpoint;
import nam.model.TransactionContext;

import org.aries.Assert;
import org.aries.client.AbstractEndpoint;
import org.aries.jms.util.MessageUtil;
import org.aries.tx.TransactionIsolationLevel;

import tx.manager.registry.ServiceRegistry;
import common.tx.InstanceIdentifier;
import common.tx.model.context.CoordinationContextType;
import common.tx.model.context.Expires;


public abstract class AbstractJMSInterceptor extends AbstractEndpoint {

	protected Message getMessageFromContext(InvocationContext invocationContext) throws Exception {
		Object[] parameters = invocationContext.getParameters();
		Assert.notNull(parameters, "Parameters must exist");
		Assert.isLength(parameters, 1);
		Assert.isInstanceOf(Message.class, parameters[0]);
		Message message = (Message) parameters[0];
		return message;
	}

	protected String getCorrelationIdFromContext(InvocationContext invocationContext) throws Exception {
		Message message = getMessageFromContext(invocationContext);
		String correlationId = MessageUtil.getCorrelationIdFromMessageOLD(message);
		return correlationId;
	}

	protected String getTransactionIdFromContext(InvocationContext invocationContext) throws Exception {
		Message message = getMessageFromContext(invocationContext);
		String transactionId = MessageUtil.getTransactionIdFromMessageOLD(message);
		return transactionId;
	}

	protected TransactionContext getTransactionContext(InvocationContext invocationContext) throws Exception {
		Message message = getMessageFromContext(invocationContext);
		return getTransactionContext(message);
	}

	protected TransactionContext getTransactionContext(Message jmsMessage) throws Exception {
		org.aries.message.Message message = MessageUtil.getObjectFromMessage(jmsMessage);
		Assert.notNull(message, "Object not found in message");
		TransactionContext transactionContext  = message.getPart("TransactionContext");
		Assert.notNull(transactionContext, "TransactionContext not found");
		Assert.isInstanceOf(TransactionContext.class, transactionContext);
		return transactionContext;
	}
	
	protected String getRegistrationServiceUri(TransactionContext transactionContext) {
		Endpoint registrationService = transactionContext.getRegistrationService();
		return getServiceUri(registrationService);
	}
	
	protected String getServiceUri(Endpoint endpoint) {
		String serviceName = endpoint.getServiceName().getLocalPart();
		String uri = ServiceRegistry.getInstance().getServiceURI(serviceName);
		return uri;
	}

	protected String getServiceUri(String serviceName) {
		String uri = ServiceRegistry.getInstance().getServiceURI(serviceName);
		return uri;
	}

	protected TransactionIsolationLevel getTransactionIsolationLevel(TransactionContext transactionContext) {
		switch (transactionContext.getIsolationLevel()) {
		case READ_UNCOMMITTED: return TransactionIsolationLevel.TRANSACTION_READ_UNCOMMITTED;
		case READ_COMMITTED: return TransactionIsolationLevel.TRANSACTION_READ_COMMITTED;
		case REPEATABLE_READ: return TransactionIsolationLevel.TRANSACTION_REPEATABLE_READ;
		case SERIALIZABLE: return TransactionIsolationLevel.TRANSACTION_SERIALIZABLE;
		}
		return null;
	}

	protected org.aries.tx.TransactionContext createManagementContext(InvocationContext invocationContext) throws Exception {
		TransactionContext transactionContext = getTransactionContext(invocationContext);
		CoordinationContextType coordinationContextType = createCoordinationContextType(transactionContext);
		org.aries.tx.TransactionContextImpl managementContext = new org.aries.tx.TransactionContextImpl(coordinationContextType);
		managementContext.setTransactionIsolationLevel(getTransactionIsolationLevel(transactionContext));
		managementContext.setCorrelationId(getCorrelationIdFromContext(invocationContext));
		return managementContext;
	}

	protected CoordinationContextType createCoordinationContextType(TransactionContext transactionContext) {
		CoordinationContextType coordinationContextType = new CoordinationContextType();
		coordinationContextType.setIdentifier(createInstanceIdentifier(transactionContext));
		coordinationContextType.setCoordinationType(getRegistrationServiceUri(transactionContext));
		coordinationContextType.setRegistrationService(createW3CEndpointReference(transactionContext));
		coordinationContextType.setExpires(createExpires(transactionContext));
		return coordinationContextType;
	}

	protected CoordinationContextType.Identifier createInstanceIdentifier(TransactionContext transactionContext) {
		return createInstanceIdentifier(transactionContext.getTransactionId());
	}

	protected CoordinationContextType.Identifier createInstanceIdentifier(String instanceId) {
		CoordinationContextType.Identifier identifier = new CoordinationContextType.Identifier();
		identifier.setValue(instanceId);
		return identifier;
	}

	protected W3CEndpointReference createW3CEndpointReference(TransactionContext transactionContext) {
		String transactionId = transactionContext.getTransactionId();
		Endpoint endpoint = transactionContext.getRegistrationService();
		W3CEndpointReferenceBuilder builder = new W3CEndpointReferenceBuilder();
		builder.serviceName(endpoint.getServiceName());
		builder.endpointName(endpoint.getEndpointName());
		builder.address(endpoint.getEndpointUri());
		builder.referenceParameter(InstanceIdentifier.createInstanceIdentifierElement(transactionId));
		W3CEndpointReference w3cEndpoint = builder.build();
		return w3cEndpoint;
	}

	protected Expires createExpires(TransactionContext transactionContext) {
		return createExpires(transactionContext.getExpiration());
	}

	protected Expires createExpires(long value) {
		Expires expires = new Expires();
		expires.setValue(value);
		return expires;
	}

	
//	protected void generateEvent(InvocationContext invocationContext) throws Exception {
//		Message message = getMessageFromContext(invocationContext);
//		String correlationId = MessageUtil.getCorrelationIdFromMessageOLD(message);
//		String transactionId = MessageUtil.getTransactionIdFromMessageOLD(message);
//		String methodName = invocationContext.getMethod().getName();
//		
//		if (correlationId != null) {
//			Map<String, Serializable> conversationState = ConversationRegistry.getInstance().getConversationState(correlationId);
//			if (conversationState == null) {
//				conversationState = new HashMap<String, Serializable>();
//				conversationState.put("correlationId", correlationId);
//				conversationState.put("transactionId", transactionId);
//				conversationState.put("methodName", methodName);
//				//conversationState.put("originatingChannel", "reserveBooks");
//				//conversationState.put("originatingTransport", "JMS");
//				ConversationRegistry.getInstance().registerConversationState(correlationId, conversationState);
//				
//				conversationState = new HashMap<String, Serializable>();
//				conversationState.put("correlationId", correlationId);
//				conversationState.put("originatingChannel", "orderBooks");
//				conversationState.put("originatingTransport", "JMS");
//				Destination jmsReplyTo = message.getJMSReplyTo();
//				HornetQQueue hornetQQueue = (HornetQQueue) jmsReplyTo; 
//				Assert.notNull(jmsReplyTo, "JMSReplyTo must be specified");
//				conversationState.put("replyToDestination", (Serializable) jmsReplyTo);
//				
//				Event event = new Event();
//				event.setTimestamp(new Date());
//				event.setEventSeverity(EventSeverity.EVENT);
//				event.setDescription("OrderBooks received message");
//				event.setMessage("Received: orderRequestMessage");
//				event.setSource("bookshop.buyer");
//				event.setObject("orderBooks");
//				event.setUserId("buyer");
//				conversationState.put("event", event);
//				ConversationRegistry.getInstance().registerConversationState(correlationId, conversationState);
//
//			}
//		}
//	}


}
