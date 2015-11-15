package bookshop2.supplier.outgoing.shipmentFailed;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.client.AbstractDelegate;
import org.aries.util.ExceptionUtil;

import bookshop2.ShipmentFailedMessage;


public class ShipmentFailedReplyTo extends AbstractDelegate implements ShipmentFailedReply {
	
	private static final Log log = LogFactory.getLog(ShipmentFailedReplyTo.class);
	
	
	@Override
	public String getDomain() {
		return "bookshop2.supplier";
	}
	
	@Override
	public String getServiceId() {
		return ShipmentFailedReply.ID;
	}
	
	@SuppressWarnings("unchecked")
	public ShipmentFailedReply getProxy() throws Exception {
		return getProxy(ShipmentFailedReply.ID);
	}
	
	@Override
	public void shipmentFailed(ShipmentFailedMessage shipmentFailedMessage) {
		try {
			getProxy().shipmentFailed(shipmentFailedMessage);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
