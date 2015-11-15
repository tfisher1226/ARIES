package bookshop2.buyer.outgoing.purchaseAccepted;

import org.aries.bean.Proxy;
import org.aries.tx.service.ejb.EJBProxy;


public class PurchaseAcceptedReplyProxyForEJB extends EJBProxy implements Proxy<PurchaseAcceptedReply> {
	
	private PurchaseAcceptedReplyInterceptor purchaseAcceptedInterceptor;
	
	
	public PurchaseAcceptedReplyProxyForEJB(String serviceId, String host, int port) {
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
