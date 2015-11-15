package bookshop2.client.receiptService;

import org.aries.bean.Proxy;
import org.aries.tx.service.jms.JMSProxy;


public class ReceiptServiceProxyForJMS extends JMSProxy implements Proxy<ReceiptService> {
	
	private ReceiptServiceInterceptor receiptServiceInterceptor;
	
	
	public ReceiptServiceProxyForJMS(String serviceId) {
		super(serviceId);
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
