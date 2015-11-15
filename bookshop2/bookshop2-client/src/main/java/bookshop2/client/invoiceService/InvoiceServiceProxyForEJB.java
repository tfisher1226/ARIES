package bookshop2.client.invoiceService;

import org.aries.bean.Proxy;
import org.aries.tx.service.ejb.EJBProxy;


public class InvoiceServiceProxyForEJB extends EJBProxy implements Proxy<InvoiceService> {
	
	private InvoiceServiceInterceptor invoiceServiceInterceptor;
	
	
	public InvoiceServiceProxyForEJB(String serviceId, String host, int port) {
		super(serviceId, host, port);
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
