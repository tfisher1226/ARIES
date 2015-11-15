package bookshop2.supplier.outgoing.shipmentScheduled;

import org.aries.bean.Proxy;
import org.aries.tx.service.rmi.RMIProxy;


public class ShipmentScheduledReplyProxyForRMI extends RMIProxy implements Proxy<ShipmentScheduledReply> {
	
	private ShipmentScheduledReplyInterceptor shipmentScheduledReplyInterceptor;
	
	
	public ShipmentScheduledReplyProxyForRMI(String serviceId, String host, int port) {
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
