package bookshop2.shipper.outgoing.shipmentComplete;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.client.AbstractDelegate;
import org.aries.util.ExceptionUtil;

import bookshop2.ShipmentCompleteMessage;


public class ShipmentCompleteReplyTo extends AbstractDelegate implements ShipmentCompleteReply {
	
	private static final Log log = LogFactory.getLog(ShipmentCompleteReplyTo.class);
	
	
	@Override
	public String getDomain() {
		return "bookshop2.shipper";
	}
	
	@Override
	public String getServiceId() {
		return ShipmentCompleteReply.ID;
	}
	
	@SuppressWarnings("unchecked")
	public ShipmentCompleteReply getProxy() throws Exception {
		return getProxy(ShipmentCompleteReply.ID);
	}
	
	@Override
	public void shipmentComplete(ShipmentCompleteMessage shipmentCompleteMessage) {
		try {
			getProxy().shipmentComplete(shipmentCompleteMessage);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
