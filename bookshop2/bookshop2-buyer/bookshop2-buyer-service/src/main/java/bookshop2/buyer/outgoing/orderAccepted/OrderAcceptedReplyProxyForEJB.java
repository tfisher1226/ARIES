package bookshop2.buyer.outgoing.orderAccepted;

import org.aries.bean.Proxy;
import org.aries.tx.service.ejb.EJBProxy;


public class OrderAcceptedReplyProxyForEJB extends EJBProxy implements Proxy<OrderAcceptedReply> {
	
	private OrderAcceptedReplyInterceptor orderAcceptedInterceptor;
	
	
	public OrderAcceptedReplyProxyForEJB(String serviceId, String host, int port) {
		super(serviceId, host, port);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		orderAcceptedInterceptor = new OrderAcceptedReplyInterceptor();
		orderAcceptedInterceptor.setProxy(this);
	}
	
	@Override
	public OrderAcceptedReply getDelegate() {
		return orderAcceptedInterceptor;
	}
	
}
