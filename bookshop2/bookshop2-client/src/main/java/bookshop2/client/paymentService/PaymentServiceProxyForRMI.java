package bookshop2.client.paymentService;

import org.aries.bean.Proxy;
import org.aries.tx.service.rmi.RMIProxy;


public class PaymentServiceProxyForRMI extends RMIProxy implements Proxy<PaymentService> {
	
	private PaymentServiceInterceptor paymentServiceInterceptor;
	
	
	public PaymentServiceProxyForRMI(String serviceId, String host, int port) {
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
