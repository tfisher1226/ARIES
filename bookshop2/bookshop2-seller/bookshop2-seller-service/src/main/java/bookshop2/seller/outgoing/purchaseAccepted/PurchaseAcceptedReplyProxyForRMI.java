package bookshop2.seller.outgoing.purchaseAccepted;

import org.aries.bean.Proxy;
import org.aries.tx.service.rmi.RMIProxy;


public class PurchaseAcceptedReplyProxyForRMI extends RMIProxy implements Proxy<PurchaseAcceptedReply> {
	
	private PurchaseAcceptedReplyInterceptor purchaseAcceptedReplyInterceptor;
	
	
	public PurchaseAcceptedReplyProxyForRMI(String serviceId, String host, int port) {
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
