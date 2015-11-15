package bookshop2.seller.outgoing.purchaseAccepted;

import org.aries.bean.Proxy;
import org.aries.tx.service.ejb.EJBProxy;


public class PurchaseAcceptedReplyProxyForEJB extends EJBProxy implements Proxy<PurchaseAcceptedReply> {
	
	private PurchaseAcceptedReplyInterceptor purchaseAcceptedReplyInterceptor;
	
	
	public PurchaseAcceptedReplyProxyForEJB(String serviceId, String host, int port) {
		super(serviceId, host, port);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		purchaseAcceptedReplyInterceptor = new PurchaseAcceptedReplyInterceptor();
		purchaseAcceptedReplyInterceptor.setProxy(this);
	}
	
	@Override
	public PurchaseAcceptedReply getDelegate() {
		return purchaseAcceptedReplyInterceptor;
	}
	
}
