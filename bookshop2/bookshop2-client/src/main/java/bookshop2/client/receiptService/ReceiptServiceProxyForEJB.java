package bookshop2.client.receiptService;

import org.aries.bean.Proxy;
import org.aries.tx.service.ejb.EJBProxy;


public class ReceiptServiceProxyForEJB extends EJBProxy implements Proxy<ReceiptService> {
	
	private ReceiptServiceInterceptor receiptServiceInterceptor;
	
	
	public ReceiptServiceProxyForEJB(String serviceId, String host, int port) {
		super(serviceId, host, port);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		receiptServiceInterceptor = new ReceiptServiceInterceptor();
		receiptServiceInterceptor.setProxy(this);
	}
	
	@Override
	public ReceiptService getDelegate() {
		return receiptServiceInterceptor;
	}
	
}
