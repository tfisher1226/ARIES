package bookshop2.shipper.outgoing.shipmentComplete;

import org.aries.bean.Proxy;
import org.aries.tx.service.ejb.EJBProxy;


public class ShipmentCompleteReplyProxyForEJB extends EJBProxy implements Proxy<ShipmentCompleteReply> {
	
	private ShipmentCompleteReplyInterceptor shipmentCompleteReplyInterceptor;
	
	
	public ShipmentCompleteReplyProxyForEJB(String serviceId, String host, int port) {
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
