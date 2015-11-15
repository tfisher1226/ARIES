package bookshop2.supplier.management.bookOrdersManager;

import org.aries.bean.Proxy;
import org.aries.tx.service.jms.JMSProxy;


public class BookOrdersManagerProxyForJMS extends JMSProxy implements Proxy<BookOrdersManager> {
	
	private BookOrdersManagerInterceptor bookOrdersManagerInterceptor;
	
	
	public BookOrdersManagerProxyForJMS(String serviceId) {
		super(serviceId);
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
