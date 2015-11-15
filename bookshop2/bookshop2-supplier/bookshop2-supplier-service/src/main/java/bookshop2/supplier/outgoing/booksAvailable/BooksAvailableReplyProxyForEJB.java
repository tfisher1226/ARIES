package bookshop2.supplier.outgoing.booksAvailable;

import org.aries.bean.Proxy;
import org.aries.tx.service.ejb.EJBProxy;


public class BooksAvailableReplyProxyForEJB extends EJBProxy implements Proxy<BooksAvailableReply> {
	
	private BooksAvailableReplyInterceptor booksAvailableInterceptor;
	
	
	public BooksAvailableReplyProxyForEJB(String serviceId, String host, int port) {
		super(serviceId, host, port);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		booksAvailableInterceptor = new BooksAvailableReplyInterceptor();
		booksAvailableInterceptor.setProxy(this);
	}
	
	@Override
	public BooksAvailableReply getDelegate() {
		return booksAvailableInterceptor;
	}
	
}
