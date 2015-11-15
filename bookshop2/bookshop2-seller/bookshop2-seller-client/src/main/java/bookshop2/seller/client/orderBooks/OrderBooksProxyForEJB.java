package bookshop2.seller.client.orderBooks;

import org.aries.bean.Proxy;
import org.aries.tx.service.ejb.EJBProxy;


public class OrderBooksProxyForEJB extends EJBProxy implements Proxy<OrderBooks> {
	
	private OrderBooksInterceptor orderBooksInterceptor;
	
	
	public OrderBooksProxyForEJB(String serviceId, String host, int port) {
		super(serviceId, host, port);
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
