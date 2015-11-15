package org.aries.tx.service.jms;

import javax.xml.ws.wsaddressing.W3CEndpointReference;
import javax.xml.ws.wsaddressing.W3CEndpointReferenceBuilder;

import nam.model.Endpoint;
import nam.model.TransactionContext;

import org.aries.tx.TransactionIsolationLevel;

import tx.manager.registry.ServiceRegistry;
import common.tx.InstanceIdentifier;
import common.tx.model.context.CoordinationContextType;
import common.tx.model.context.Expires;


public abstract class AbstractInterceptor {

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

}
