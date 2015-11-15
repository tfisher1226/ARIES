package bookshop2.buyer.outgoing.purchaseRejected;

import org.aries.bean.Proxy;
import org.aries.tx.service.ejb.EJBProxy;


public class PurchaseRejectedReplyProxyForEJB extends EJBProxy implements Proxy<PurchaseRejectedReply> {
	
	private PurchaseRejectedReplyInterceptor purchaseRejectedInterceptor;
	
	
	public PurchaseRejectedReplyProxyForEJB(String serviceId, String host, int port) {
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
