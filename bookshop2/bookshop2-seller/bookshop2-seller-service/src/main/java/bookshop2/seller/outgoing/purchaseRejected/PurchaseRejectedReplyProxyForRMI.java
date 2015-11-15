package bookshop2.seller.outgoing.purchaseRejected;

import org.aries.bean.Proxy;
import org.aries.tx.service.rmi.RMIProxy;


public class PurchaseRejectedReplyProxyForRMI extends RMIProxy implements Proxy<PurchaseRejectedReply> {
	
	private PurchaseRejectedReplyInterceptor purchaseRejectedReplyInterceptor;
	
	
	public PurchaseRejectedReplyProxyForRMI(String serviceId, String host, int port) {
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
