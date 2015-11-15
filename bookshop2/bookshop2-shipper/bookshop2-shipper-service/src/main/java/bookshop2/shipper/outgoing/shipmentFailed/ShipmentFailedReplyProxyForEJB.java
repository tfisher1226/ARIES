package bookshop2.shipper.outgoing.shipmentFailed;

import org.aries.bean.Proxy;
import org.aries.tx.service.ejb.EJBProxy;


public class ShipmentFailedReplyProxyForEJB extends EJBProxy implements Proxy<ShipmentFailedReply> {
	
	private ShipmentFailedReplyInterceptor shipmentFailedReplyInterceptor;
	
	
	public ShipmentFailedReplyProxyForEJB(String serviceId, String host, int port) {
		super(serviceId, host, port);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		shipmentFailedReplyInterceptor = new ShipmentFailedReplyInterceptor();
		shipmentFailedReplyInterceptor.setProxy(this);
	}
	
	@Override
	public ShipmentFailedReply getDelegate() {
		return shipmentFailedReplyInterceptor;
	}
	
}
