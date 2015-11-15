package bookshop2.client.invoiceService;

import org.aries.bean.Proxy;
import org.aries.tx.service.jms.JMSProxy;


public class InvoiceServiceProxyForJMS extends JMSProxy implements Proxy<InvoiceService> {
	
	private InvoiceServiceInterceptor invoiceServiceInterceptor;
	
	
	public InvoiceServiceProxyForJMS(String serviceId) {
		super(serviceId);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		invoiceServiceInterceptor = new InvoiceServiceInterceptor();
		invoiceServiceInterceptor.setProxy(this);
	}
	
	@Override
	public InvoiceService getDelegate() {
		return invoiceServiceInterceptor;
	}
	
}
