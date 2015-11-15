package bookshop2.supplier.management.bookOrdersManager;

import org.aries.bean.Proxy;
import org.aries.tx.service.ejb.EJBProxy;


public class BookOrdersManagerProxyForEJB extends EJBProxy implements Proxy<BookOrdersManager> {
	
	private BookOrdersManagerInterceptor bookOrdersManagerInterceptor;
	
	
	public BookOrdersManagerProxyForEJB(String serviceId, String host, int port) {
		super(serviceId, host, port);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		bookOrdersManagerInterceptor = new BookOrdersManagerInterceptor();
		bookOrdersManagerInterceptor.setProxy(this);
	}
	
	@Override
	public BookOrdersManager getDelegate() {
		return bookOrdersManagerInterceptor;
	}
	
}
