package bookshop2.client.shipmentService;

import org.aries.bean.Proxy;
import org.aries.tx.service.jms.JMSProxy;


public class ShipmentServiceProxyForJMS extends JMSProxy implements Proxy<ShipmentService> {
	
	private ShipmentServiceInterceptor shipmentServiceInterceptor;
	
	
	public ShipmentServiceProxyForJMS(String serviceId) {
		super(serviceId);
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
