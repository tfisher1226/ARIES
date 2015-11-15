package bookshop2.client.paymentService;

import org.aries.bean.Proxy;
import org.aries.tx.service.jms.JMSProxy;


public class PaymentServiceProxyForJMS extends JMSProxy implements Proxy<PaymentService> {
	
	private PaymentServiceInterceptor paymentServiceInterceptor;
	
	
	public PaymentServiceProxyForJMS(String serviceId) {
		super(serviceId);
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
