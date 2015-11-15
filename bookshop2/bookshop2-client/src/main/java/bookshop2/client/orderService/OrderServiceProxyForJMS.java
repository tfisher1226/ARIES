package bookshop2.client.orderService;

import org.aries.bean.Proxy;
import org.aries.tx.service.jms.JMSProxy;


public class OrderServiceProxyForJMS extends JMSProxy implements Proxy<OrderService> {
	
	private OrderServiceInterceptor orderServiceInterceptor;
	
	
	public OrderServiceProxyForJMS(String serviceId) {
		super(serviceId);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		orderServiceInterceptor = new OrderServiceInterceptor();
		orderServiceInterceptor.setProxy(this);
	}
	
	@Override
	public OrderService getDelegate() {
		return orderServiceInterceptor;
	}
	
}
