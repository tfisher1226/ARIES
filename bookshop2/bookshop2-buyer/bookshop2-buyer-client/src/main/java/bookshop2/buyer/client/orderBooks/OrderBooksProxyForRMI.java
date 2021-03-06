package bookshop2.buyer.client.orderBooks;

import org.aries.bean.Proxy;
import org.aries.tx.service.rmi.RMIProxy;


public class OrderBooksProxyForRMI extends RMIProxy implements Proxy<OrderBooks> {
	
	private OrderBooksInterceptor orderBooksInterceptor;
	
	
	public OrderBooksProxyForRMI(String serviceId, String host, int port) {
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
