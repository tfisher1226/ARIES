package bookshop2.shipper.client.shipBooks;

import org.aries.bean.Proxy;
import org.aries.tx.service.ejb.EJBProxy;


public class ShipBooksProxyForEJB extends EJBProxy implements Proxy<ShipBooks> {
	
	private ShipBooksInterceptor shipBooksInterceptor;
	
	
	public ShipBooksProxyForEJB(String serviceId, String host, int port) {
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
