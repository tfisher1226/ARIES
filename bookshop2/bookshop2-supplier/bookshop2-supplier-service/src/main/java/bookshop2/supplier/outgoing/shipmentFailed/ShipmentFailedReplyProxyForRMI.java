package bookshop2.supplier.outgoing.shipmentFailed;

import org.aries.bean.Proxy;
import org.aries.tx.service.rmi.RMIProxy;


public class ShipmentFailedReplyProxyForRMI extends RMIProxy implements Proxy<ShipmentFailedReply> {
	
	private ShipmentFailedReplyInterceptor shipmentFailedReplyInterceptor;
	
	
	public ShipmentFailedReplyProxyForRMI(String serviceId, String host, int port) {
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
