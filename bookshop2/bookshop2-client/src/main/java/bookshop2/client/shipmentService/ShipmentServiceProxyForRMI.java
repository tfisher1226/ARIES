package bookshop2.client.shipmentService;

import org.aries.bean.Proxy;
import org.aries.tx.service.rmi.RMIProxy;


public class ShipmentServiceProxyForRMI extends RMIProxy implements Proxy<ShipmentService> {
	
	private ShipmentServiceInterceptor shipmentServiceInterceptor;
	
	
	public ShipmentServiceProxyForRMI(String serviceId, String host, int port) {
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
