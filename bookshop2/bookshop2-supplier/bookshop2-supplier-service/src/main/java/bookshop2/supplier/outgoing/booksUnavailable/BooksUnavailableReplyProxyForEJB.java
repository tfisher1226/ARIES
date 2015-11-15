package bookshop2.supplier.outgoing.booksUnavailable;

import org.aries.bean.Proxy;
import org.aries.tx.service.ejb.EJBProxy;


public class BooksUnavailableReplyProxyForEJB extends EJBProxy implements Proxy<BooksUnavailableReply> {
	
	private BooksUnavailableReplyInterceptor booksUnavailableInterceptor;
	
	
	public BooksUnavailableReplyProxyForEJB(String serviceId, String host, int port) {
		super(serviceId, host, port);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		booksUnavailableInterceptor = new BooksUnavailableReplyInterceptor();
		booksUnavailableInterceptor.setProxy(this);
	}
	
	@Override
	public BooksUnavailableReply getDelegate() {
		return booksUnavailableInterceptor;
	}
	
}
