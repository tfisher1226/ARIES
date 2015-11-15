package bookshop2.supplier.client.queryBooks;

import org.aries.bean.Proxy;
import org.aries.tx.service.jms.JMSProxy;


public class QueryBooksProxyForJMS extends JMSProxy implements Proxy<QueryBooks> {

	private QueryBooksInterceptor queryBooksInterceptor;
	
	
	public QueryBooksProxyForJMS(String serviceId) {
		super(serviceId);
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
