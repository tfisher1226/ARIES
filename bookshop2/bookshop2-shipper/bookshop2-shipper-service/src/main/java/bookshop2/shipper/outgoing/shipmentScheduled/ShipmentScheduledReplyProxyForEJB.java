package bookshop2.shipper.outgoing.shipmentScheduled;

import org.aries.bean.Proxy;
import org.aries.tx.service.ejb.EJBProxy;


public class ShipmentScheduledReplyProxyForEJB extends EJBProxy implements Proxy<ShipmentScheduledReply> {
	
	private ShipmentScheduledReplyInterceptor shipmentScheduledReplyInterceptor;
	
	
	public ShipmentScheduledReplyProxyForEJB(String serviceId, String host, int port) {
		super(serviceId, host, port);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		shipmentScheduledReplyInterceptor = new ShipmentScheduledReplyInterceptor();
		shipmentScheduledReplyInterceptor.setProxy(this);
	}
	
	@Override
	public ShipmentScheduledReply getDelegate() {
		return shipmentScheduledReplyInterceptor;
	}
	
}
