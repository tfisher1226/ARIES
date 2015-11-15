package bookshop2.seller.outgoing.orderRejected;

import org.aries.bean.Proxy;
import org.aries.tx.service.rmi.RMIProxy;


public class OrderRejectedReplyProxyForRMI extends RMIProxy implements Proxy<OrderRejectedReply> {
	
	private OrderRejectedReplyInterceptor orderRejectedReplyInterceptor;
	
	
	public OrderRejectedReplyProxyForRMI(String serviceId, String host, int port) {
		super(serviceId, host, port);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		orderRejectedReplyInterceptor = new OrderRejectedReplyInterceptor();
		orderRejectedReplyInterceptor.setProxy(this);
	}
	
	@Override
	public OrderRejectedReply getDelegate() {
		return orderRejectedReplyInterceptor;
	}
	
}
