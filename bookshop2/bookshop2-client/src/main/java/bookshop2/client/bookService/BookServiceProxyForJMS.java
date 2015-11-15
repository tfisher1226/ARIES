package bookshop2.client.bookService;

import org.aries.bean.Proxy;
import org.aries.tx.service.jms.JMSProxy;


public class BookServiceProxyForJMS extends JMSProxy implements Proxy<BookService> {
	
	private BookServiceInterceptor bookServiceInterceptor;
	
	
	public BookServiceProxyForJMS(String serviceId) {
		super(serviceId);
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
