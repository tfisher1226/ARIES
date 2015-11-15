package bookshop2.supplier.client.shipBooks;

import org.aries.bean.Proxy;
import org.aries.tx.service.rmi.RMIProxy;


public class ShipBooksProxyForRMI extends RMIProxy implements Proxy<ShipBooks> {
	
	private ShipBooksInterceptor shipBooksInterceptor;
	
	
	public ShipBooksProxyForRMI(String serviceId, String host, int port) {
		super(serviceId, host, port);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		shipBooksInterceptor = new ShipBooksInterceptor();
		shipBooksInterceptor.setProxy(this);
	}
	
	@Override
	public ShipBooks getDelegate() {
		return shipBooksInterceptor;
	}
	
}
