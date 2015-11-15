package bookshop2.buyer.outgoing.purchaseRejected;

import org.aries.bean.Proxy;
import org.aries.tx.service.rmi.RMIProxy;


public class PurchaseRejectedReplyProxyForRMI extends RMIProxy implements Proxy<PurchaseRejectedReply> {
	
	private PurchaseRejectedReplyInterceptor purchaseRejectedInterceptor;
	
	
	public PurchaseRejectedReplyProxyForRMI(String serviceId, String host, int port) {
		super(serviceId, host, port);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		purchaseRejectedInterceptor = new PurchaseRejectedReplyInterceptor();
		purchaseRejectedInterceptor.setProxy(this);
	}
	
	@Override
	public PurchaseRejectedReply getDelegate() {
		return purchaseRejectedInterceptor;
	}
	
}
