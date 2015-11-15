package bookshop2.supplier.client.queryBooks;

import org.aries.bean.Proxy;
import org.aries.tx.service.ejb.EJBProxy;


public class QueryBooksProxyForEJB extends EJBProxy implements Proxy<QueryBooks> {
	
	private QueryBooksInterceptor queryBooksInterceptor;
	
	
	public QueryBooksProxyForEJB(String serviceId, String host, int port) {
		super(serviceId, host, port);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		queryBooksInterceptor = new QueryBooksInterceptor();
		queryBooksInterceptor.setProxy(this);
	}
	
	@Override
	public QueryBooks getDelegate() {
		return queryBooksInterceptor;
	}
	
}
