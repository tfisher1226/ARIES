package bookshop2.buyer.incoming.purchaseBooks;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import org.aries.message.Message;
import org.aries.runtime.BeanContext;
import org.aries.tx.service.rmi.AbstractRMIListener;
import org.aries.util.ExceptionUtil;

import bookshop2.PurchaseRejectedMessage;
import bookshop2.buyer.BuyerContext;


public class PurchaseRejectedListenerForRMI extends AbstractRMIListener implements Remote {
	
	private BuyerContext buyerContext;
	
	private PurchaseRejectedInterceptor purchaseRejectedInterceptor;
	
	
	public PurchaseRejectedListenerForRMI() throws RemoteException {
		registerAsRMIService();
	}
	
	
	@Override
	public void initialize() throws Exception {
		registerAsRMIService();
	}
	
	@Override
	public String getModuleId() {
		return "bookshop2-buyer-service";
	}
	
	@Override
	public String getServiceId() {
		return PurchaseRejected.ID;
	}
	
	@Override
	public BuyerContext getContext() {
		if (buyerContext == null || resetRequested)
			buyerContext = BeanContext.getFromJNDI(BuyerContext.class, "BuyerContext");
		return buyerContext;
	}
	
	@Override
	public Object getDelegate() {
		if (purchaseRejectedInterceptor == null || resetRequested)
			purchaseRejectedInterceptor = BeanContext.getFromJNDI(PurchaseRejectedInterceptor.class, "PurchaseRejectedInterceptor");
		return purchaseRejectedInterceptor;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Serializable invoke(Serializable request, String correlationId, long timeout) throws RemoteException {
			if (!isInitialized())
				return null;
		
		try {
			if (request instanceof String)
				return processAsText((String) request);
			if (request instanceof Message)
				return processAsBinary((Message) request);
			throw new Exception("Unexpected payload type: "+request.getClass());
		
		} catch (Throwable e) {
			Throwable cause = ExceptionUtil.getRootCause(e);
			throw new RemoteException(cause.getMessage(), cause);
		}
	}
	
	@Override
	protected void validate(Message message) {
		PurchaseRejectedMessage purchaseRejectedMessage = message.getPart("purchaseRejectedMessage");
		buyerContext.validate(purchaseRejectedMessage);
	}
	
}
