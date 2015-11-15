package bookshop2.client.orderService;

import org.aries.bean.Proxy;
import org.aries.tx.service.ejb.EJBProxy;


public class OrderServiceProxyForEJB extends EJBProxy implements Proxy<OrderService> {
	
	private OrderServiceInterceptor orderServiceInterceptor;
	
	
	public OrderServiceProxyForEJB(String serviceId, String host, int port) {
		super(serviceId, host, port);
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
