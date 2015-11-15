package org.aries.tx;

import common.tx.model.context.CoordinationContextType;


public class TransactionContextImpl implements TransactionContext {
	
	private Object correlationId;

	private CoordinationContextType coordinationContextType;
	
	private TransactionIsolationLevel transactionIsolationLevel;

	
	public TransactionContextImpl() {
		//nothing for now
	}
	
	public TransactionContextImpl(CoordinationContextType coordinationContext) {
		this.coordinationContextType = coordinationContext;
	}

	public CoordinationContextType getCoordinationContextType() {
		return coordinationContextType;
	}

//	@Override
//	public int getStatus() {
//		return 0;
//	}
//
//	@Override
//	public String getState() {
//		return null;
//	}

	@Override
	public boolean isValid() {
		return coordinationContextType != null ;
	}

	//TODO
	@Override
    public boolean isSecure() {
        if (isValid()) {
            //CoordinationContextType coordinationContextType = context;
            //W3CEndpointReference epref = coordinationContextType.getRegistrationService();
            //NativeEndpointReference nativeRef = EndpointHelper.transform(NativeEndpointReference.class, epref);
            //String address = nativeRef.getAddress();
            //return address.startsWith("https");
        }
        return false;
    }

	@Override
	public final String getTransactionId() {
	    final String value = coordinationContextType.getIdentifier().getValue();
	    if (value != null && value.startsWith("urn:"))
	    	return value.substring(4);
	    return value ;
	}

	@Override
	public Object getCorrelationId() {
	    return correlationId;
	}

	public void setCorrelationId(Object correlationId) {
	    this.correlationId = correlationId;
	}

	@Override
	public TransactionIsolationLevel getTransactionIsolationLevel() {
	    return transactionIsolationLevel;
	}

	public void setTransactionIsolationLevel(TransactionIsolationLevel transactionIsolationLevel) {
	    this.transactionIsolationLevel = transactionIsolationLevel;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object instanceof TransactionContextImpl) {
			TransactionContextImpl other = (TransactionContextImpl) object;
			return other.getCoordinationContextType().equals(coordinationContextType);
		}
		return false;
	}

	@Override
	public String toString() {
		return coordinationContextType.toString();
	}

}