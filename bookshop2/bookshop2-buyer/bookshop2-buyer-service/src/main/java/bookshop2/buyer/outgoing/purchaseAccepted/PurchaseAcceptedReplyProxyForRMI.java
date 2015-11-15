package bookshop2.buyer.outgoing.purchaseAccepted;

import org.aries.bean.Proxy;
import org.aries.tx.service.rmi.RMIProxy;


public class PurchaseAcceptedReplyProxyForRMI extends RMIProxy implements Proxy<PurchaseAcceptedReply> {
	
	private PurchaseAcceptedReplyInterceptor purchaseAcceptedInterceptor;
	
	
	public PurchaseAcceptedReplyProxyForRMI(String serviceId, String host, int port) {
		super(serviceId, host, port);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		purchaseAcceptedInterceptor = new PurchaseAcceptedReplyInterceptor();
		purchaseAcceptedInterceptor.setProxy(this);
	}
	
	@Override
	public PurchaseAcceptedReply getDelegate() {
		return purchaseAcceptedInterceptor;
	}
	
}
