package bookshop2.supplier.outgoing.booksAvailable;

import org.aries.bean.Proxy;
import org.aries.tx.service.rmi.RMIProxy;


public class BooksAvailableReplyProxyForRMI extends RMIProxy implements Proxy<BooksAvailableReply> {
	
	private BooksAvailableReplyInterceptor booksAvailableInterceptor;
	
	
	public BooksAvailableReplyProxyForRMI(String serviceId, String host, int port) {
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
