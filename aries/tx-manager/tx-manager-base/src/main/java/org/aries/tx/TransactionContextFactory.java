package org.aries.tx;

import common.tx.CoordinationConstants;
import common.tx.model.context.CoordinationContext;
import common.tx.model.context.CoordinationContextType;
import common.tx.model.context.Expires;


public class TransactionContextFactory {

	public static String DEFAULT_COORDINATION_TYPE = CoordinationConstants.WSAT_PROTOCOL;

	public static Long DEFAULT_TIMEOUT = 300L;
	
	public static TransactionContext createTransactionContext(String transactionId) {
		return createTransactionContext(transactionId, DEFAULT_TIMEOUT);
	}
	
	public static TransactionContext createTransactionContext(String transactionId, Long timeout) {
		CoordinationContext coordinationContext = createCoordinationContext(transactionId, timeout);
		TransactionContext transactionContext = new TransactionContextImpl(coordinationContext);
		return transactionContext;
	}

	public static CoordinationContext createCoordinationContext(String transactionId, Long timeout) {
		CoordinationContext coordinationContext = createCoordinationContext(transactionId, DEFAULT_COORDINATION_TYPE, timeout);
		return coordinationContext;
	}
	
	public static CoordinationContext createCoordinationContext(String transactionId, String coordinationType, Long timeout) {
		CoordinationContext coordinationContext = new CoordinationContext();
		coordinationContext.setCoordinationType(coordinationType);
		CoordinationContextType.Identifier identifier = new CoordinationContextType.Identifier();
		identifier.setValue("urn:" + transactionId);
		coordinationContext.setIdentifier(identifier);
		if (timeout != null && timeout.longValue() > 0) {
			Expires expiresInstance = new Expires();
			expiresInstance.setValue(timeout);
			coordinationContext.setExpires(expiresInstance);
		}
		return coordinationContext;
	}
}
