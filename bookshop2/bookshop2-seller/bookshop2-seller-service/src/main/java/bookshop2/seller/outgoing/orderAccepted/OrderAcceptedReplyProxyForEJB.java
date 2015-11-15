package bookshop2.seller.outgoing.orderAccepted;

import org.aries.bean.Proxy;
import org.aries.tx.service.ejb.EJBProxy;


public class OrderAcceptedReplyProxyForEJB extends EJBProxy implements Proxy<OrderAcceptedReply> {
	
	private OrderAcceptedReplyInterceptor orderAcceptedReplyInterceptor;
	
	
	public OrderAcceptedReplyProxyForEJB(String serviceId, String host, int port) {
		super(serviceId, host, port);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		orderAcceptedReplyInterceptor = new OrderAcceptedReplyInterceptor();
		orderAcceptedReplyInterceptor.setProxy(this);
	}
	
	@Override
	public OrderAcceptedReply getDelegate() {
		return orderAcceptedReplyInterceptor;
	}
	
}
