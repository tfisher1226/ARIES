package bookshop2.buyer.client.purchaseBooks;

import org.aries.bean.Proxy;
import org.aries.tx.service.ejb.EJBProxy;


public class PurchaseBooksProxyForEJB extends EJBProxy implements Proxy<PurchaseBooks> {
	
	private PurchaseBooksInterceptor purchaseBooksInterceptor;
	
	
	public PurchaseBooksProxyForEJB(String serviceId, String host, int port) {
		super(serviceId, host, port);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		purchaseBooksInterceptor = new PurchaseBooksInterceptor();
		purchaseBooksInterceptor.setProxy(this);
	}
	
	@Override
	public PurchaseBooks getDelegate() {
		return purchaseBooksInterceptor;
	}
	
}
