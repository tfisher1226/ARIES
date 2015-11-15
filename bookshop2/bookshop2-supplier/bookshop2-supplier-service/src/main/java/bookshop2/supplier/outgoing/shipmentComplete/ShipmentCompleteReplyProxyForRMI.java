package bookshop2.supplier.outgoing.shipmentComplete;

import org.aries.bean.Proxy;
import org.aries.tx.service.rmi.RMIProxy;


public class ShipmentCompleteReplyProxyForRMI extends RMIProxy implements Proxy<ShipmentCompleteReply> {
	
	private ShipmentCompleteReplyInterceptor shipmentCompleteReplyInterceptor;
	
	
	public ShipmentCompleteReplyProxyForRMI(String serviceId, String host, int port) {
		super(serviceId, host, port);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		shipmentCompleteReplyInterceptor = new ShipmentCompleteReplyInterceptor();
		shipmentCompleteReplyInterceptor.setProxy(this);
	}
	
	@Override
	public ShipmentCompleteReply getDelegate() {
		return shipmentCompleteReplyInterceptor;
	}
	
}
