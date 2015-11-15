package bookshop2.client.receiptService;

import org.aries.bean.Proxy;
import org.aries.tx.service.rmi.RMIProxy;


public class ReceiptServiceProxyForRMI extends RMIProxy implements Proxy<ReceiptService> {
	
	private ReceiptServiceInterceptor receiptServiceInterceptor;
	
	
	public ReceiptServiceProxyForRMI(String serviceId, String host, int port) {
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
