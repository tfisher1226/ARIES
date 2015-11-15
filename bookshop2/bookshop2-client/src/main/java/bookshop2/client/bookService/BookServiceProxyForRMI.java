package bookshop2.client.bookService;

import org.aries.bean.Proxy;
import org.aries.tx.service.rmi.RMIProxy;


public class BookServiceProxyForRMI extends RMIProxy implements Proxy<BookService> {
	
	private BookServiceInterceptor bookServiceInterceptor;
	
	
	public BookServiceProxyForRMI(String serviceId, String host, int port) {
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
