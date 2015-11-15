package bookshop2.buyer.outgoing.orderRejected;

import org.aries.bean.Proxy;
import org.aries.tx.service.ejb.EJBProxy;


public class OrderRejectedReplyProxyForEJB extends EJBProxy implements Proxy<OrderRejectedReply> {
	
	private OrderRejectedReplyInterceptor orderRejectedInterceptor;
	
	
	public OrderRejectedReplyProxyForEJB(String serviceId, String host, int port) {
		super(serviceId, host, port);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		orderRejectedInterceptor = new OrderRejectedReplyInterceptor();
		orderRejectedInterceptor.setProxy(this);
	}
	
	@Override
	public OrderRejectedReply getDelegate() {
		return orderRejectedInterceptor;
	}
	
}
