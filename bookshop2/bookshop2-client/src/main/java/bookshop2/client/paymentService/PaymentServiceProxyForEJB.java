package bookshop2.client.paymentService;

import org.aries.bean.Proxy;
import org.aries.tx.service.ejb.EJBProxy;


public class PaymentServiceProxyForEJB extends EJBProxy implements Proxy<PaymentService> {
	
	private PaymentServiceInterceptor paymentServiceInterceptor;
	
	
	public PaymentServiceProxyForEJB(String serviceId, String host, int port) {
		super(serviceId, host, port);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		paymentServiceInterceptor = new PaymentServiceInterceptor();
		paymentServiceInterceptor.setProxy(this);
	}
	
	@Override
	public PaymentService getDelegate() {
		return paymentServiceInterceptor;
	}
	
}
