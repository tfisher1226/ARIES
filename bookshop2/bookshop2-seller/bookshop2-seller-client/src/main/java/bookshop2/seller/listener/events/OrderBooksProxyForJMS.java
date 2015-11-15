package bookshop2.seller.listener.events;

import org.aries.bean.Proxy;
import org.aries.tx.service.jms.JMSProxy;


public class OrderBooksProxyForJMS extends JMSProxy implements Proxy<OrderBooks> {
	
	private OrderBooksInterceptor orderBooksInterceptor;
	
	
	public OrderBooksProxyForJMS(String serviceId) {
		super(serviceId);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		orderBooksInterceptor = new OrderBooksInterceptor();
		orderBooksInterceptor.setProxy(this);
	}
	
	@Override
	public OrderBooks getDelegate() {
		return orderBooksInterceptor;
	}
	
}
