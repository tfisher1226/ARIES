package bookshop2.supplier.client.reserveBooks;

import org.aries.bean.Proxy;
import org.aries.tx.service.ejb.EJBProxy;


public class ReserveBooksProxyForEJB extends EJBProxy implements Proxy<ReserveBooks> {
	
	private ReserveBooksInterceptor reserveBooksInterceptor;
	
	
	public ReserveBooksProxyForEJB(String serviceId, String host, int port) {
		super(serviceId, host, port);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		reserveBooksInterceptor = new ReserveBooksInterceptor();
		reserveBooksInterceptor.setProxy(this);
	}
	
	@Override
	public ReserveBooks getDelegate() {
		return reserveBooksInterceptor;
	}
	
}
