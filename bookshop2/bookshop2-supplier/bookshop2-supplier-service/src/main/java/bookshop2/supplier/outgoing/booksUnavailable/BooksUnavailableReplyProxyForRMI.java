package bookshop2.supplier.outgoing.booksUnavailable;

import org.aries.bean.Proxy;
import org.aries.tx.service.rmi.RMIProxy;


public class BooksUnavailableReplyProxyForRMI extends RMIProxy implements Proxy<BooksUnavailableReply> {
	
	private BooksUnavailableReplyInterceptor booksUnavailableInterceptor;
	
	
	public BooksUnavailableReplyProxyForRMI(String serviceId, String host, int port) {
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
