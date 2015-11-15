package bookshop2.client.shipmentService;

import org.aries.bean.Proxy;
import org.aries.tx.service.ejb.EJBProxy;


public class ShipmentServiceProxyForEJB extends EJBProxy implements Proxy<ShipmentService> {
	
	private ShipmentServiceInterceptor shipmentServiceInterceptor;
	
	
	public ShipmentServiceProxyForEJB(String serviceId, String host, int port) {
		super(serviceId, host, port);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		shipmentServiceInterceptor = new ShipmentServiceInterceptor();
		shipmentServiceInterceptor.setProxy(this);
	}
	
	@Override
	public ShipmentService getDelegate() {
		return shipmentServiceInterceptor;
	}
	
}
