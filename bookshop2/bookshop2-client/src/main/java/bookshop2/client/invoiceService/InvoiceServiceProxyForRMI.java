package bookshop2.client.invoiceService;

import org.aries.bean.Proxy;
import org.aries.tx.service.rmi.RMIProxy;


public class InvoiceServiceProxyForRMI extends RMIProxy implements Proxy<InvoiceService> {
	
	private InvoiceServiceInterceptor invoiceServiceInterceptor;
	
	
	public InvoiceServiceProxyForRMI(String serviceId, String host, int port) {
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
