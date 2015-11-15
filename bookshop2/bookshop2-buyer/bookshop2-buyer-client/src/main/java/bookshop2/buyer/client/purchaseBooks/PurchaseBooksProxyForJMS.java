package bookshop2.buyer.client.purchaseBooks;

import org.aries.bean.Proxy;
import org.aries.tx.service.jms.JMSProxy;


public class PurchaseBooksProxyForJMS extends JMSProxy implements Proxy<PurchaseBooks> {

	private PurchaseBooksInterceptor purchaseBooksInterceptor;
	
	
	public PurchaseBooksProxyForJMS(String serviceId) {
		super(serviceId);
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
