package bookshop2.shipper.client.shipBooks;

import org.aries.bean.Proxy;
import org.aries.tx.service.jms.JMSProxy;


public class ShipBooksProxyForJMS extends JMSProxy implements Proxy<ShipBooks> {
	
	private ShipBooksInterceptor shipBooksInterceptor;
	
	
	public ShipBooksProxyForJMS(String serviceId) {
		super(serviceId);
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
