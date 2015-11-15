package bookshop2.client.orderService;

import org.aries.bean.Proxy;
import org.aries.tx.service.rmi.RMIProxy;


public class OrderServiceProxyForRMI extends RMIProxy implements Proxy<OrderService> {
	
	private OrderServiceInterceptor orderServiceInterceptor;
	
	
	public OrderServiceProxyForRMI(String serviceId, String host, int port) {
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
