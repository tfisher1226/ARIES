package bookshop2.supplier.client.reserveBooks;

import org.aries.bean.Proxy;
import org.aries.tx.service.rmi.RMIProxy;


public class ReserveBooksProxyForRMI extends RMIProxy implements Proxy<ReserveBooks> {
	
	private ReserveBooksInterceptor reserveBooksInterceptor;
	
	
	public ReserveBooksProxyForRMI(String serviceId, String host, int port) {
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
