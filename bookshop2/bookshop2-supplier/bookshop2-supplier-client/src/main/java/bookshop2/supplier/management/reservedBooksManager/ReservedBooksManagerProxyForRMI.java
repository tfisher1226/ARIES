package bookshop2.supplier.management.reservedBooksManager;

import org.aries.bean.Proxy;
import org.aries.tx.service.rmi.RMIProxy;


public class ReservedBooksManagerProxyForRMI extends RMIProxy implements Proxy<ReservedBooksManager> {
	
	private ReservedBooksManagerInterceptor reservedBooksManagerInterceptor;
	
	
	public ReservedBooksManagerProxyForRMI(String serviceId, String host, int port) {
		super(serviceId, host, port);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		reservedBooksManagerInterceptor = new ReservedBooksManagerInterceptor();
		reservedBooksManagerInterceptor.setProxy(this);
	}
	
	@Override
	public ReservedBooksManager getDelegate() {
		return reservedBooksManagerInterceptor;
	}
	
}
