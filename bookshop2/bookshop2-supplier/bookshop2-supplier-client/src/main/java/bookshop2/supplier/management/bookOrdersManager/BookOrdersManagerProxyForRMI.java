package bookshop2.supplier.management.bookOrdersManager;

import org.aries.bean.Proxy;
import org.aries.tx.service.rmi.RMIProxy;


public class BookOrdersManagerProxyForRMI extends RMIProxy implements Proxy<BookOrdersManager> {
	
	private BookOrdersManagerInterceptor bookOrdersManagerInterceptor;
	
	
	public BookOrdersManagerProxyForRMI(String serviceId, String host, int port) {
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
