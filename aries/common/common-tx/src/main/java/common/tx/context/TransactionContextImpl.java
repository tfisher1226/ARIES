package common.tx.context;

import common.tx.model.context.CoordinationContextType;


public class TransactionContextImpl implements TransactionContext {
	
	private final CoordinationContextType context;

	
	public TransactionContextImpl(CoordinationContextType context) {
		this.context = context;
	}

//	public TransactionContextImple(Context context) {
//		_context = context;
//	}

	public CoordinationContextType getCoordinationContext() {
		return context;
	}

	public boolean valid() {
		return context != null ;
	}

	//TODO
    public boolean isSecure() {
        if (valid()) {
            //CoordinationContextType coordinationContextType = context;
            //W3CEndpointReference epref = coordinationContextType.getRegistrationService();
            //NativeEndpointReference nativeRef = EndpointHelper.transform(NativeEndpointReference.class, epref);
            //String address = nativeRef.getAddress();
            //return address.startsWith("https");
        }
        return false;
    }

	public final String identifier() {
	    final String value = context.getIdentifier().getValue();
	    if (value != null && value.startsWith("urn:"))
	    	return value.substring(4);
	    return value ;
	}

	public boolean equals(Object object) {
		if (object instanceof TransactionContextImpl) {
			TransactionContextImpl other = (TransactionContextImpl) object;
			return other.equals(context);
		}
		return false;
	}

	public String toString() {
		return context.toString();
	}

}