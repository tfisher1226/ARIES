package bookshop2.client.bookService;

import org.aries.bean.Proxy;
import org.aries.tx.service.ejb.EJBProxy;


public class BookServiceProxyForEJB extends EJBProxy implements Proxy<BookService> {
	
	private BookServiceInterceptor bookServiceInterceptor;
	
	
	public BookServiceProxyForEJB(String serviceId, String host, int port) {
		super(serviceId, host, port);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		bookServiceInterceptor = new BookServiceInterceptor();
		bookServiceInterceptor.setProxy(this);
	}
	
	@Override
	public BookService getDelegate() {
		return bookServiceInterceptor;
	}
	
}
