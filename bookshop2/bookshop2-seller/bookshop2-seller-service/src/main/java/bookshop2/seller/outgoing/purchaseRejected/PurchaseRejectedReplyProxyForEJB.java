package bookshop2.seller.outgoing.purchaseRejected;

import org.aries.bean.Proxy;
import org.aries.tx.service.ejb.EJBProxy;


public class PurchaseRejectedReplyProxyForEJB extends EJBProxy implements Proxy<PurchaseRejectedReply> {
	
	private PurchaseRejectedReplyInterceptor purchaseRejectedReplyInterceptor;
	
	
	public PurchaseRejectedReplyProxyForEJB(String serviceId, String host, int port) {
		super(serviceId, host, port);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		purchaseRejectedReplyInterceptor = new PurchaseRejectedReplyInterceptor();
		purchaseRejectedReplyInterceptor.setProxy(this);
	}
	
	@Override
	public PurchaseRejectedReply getDelegate() {
		return purchaseRejectedReplyInterceptor;
	}
	
}
