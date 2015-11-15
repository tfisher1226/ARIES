package bookshop2.supplier.outgoing.shipmentScheduled;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.client.AbstractDelegate;
import org.aries.util.ExceptionUtil;

import bookshop2.ShipmentScheduledMessage;


public class ShipmentScheduledReplyTo extends AbstractDelegate implements ShipmentScheduledReply {
	
	private static final Log log = LogFactory.getLog(ShipmentScheduledReplyTo.class);
	
	
	@Override
	public String getDomain() {
		return "bookshop2.supplier";
	}
	
	@Override
	public String getServiceId() {
		return ShipmentScheduledReply.ID;
	}
	
	@SuppressWarnings("unchecked")
	public ShipmentScheduledReply getProxy() throws Exception {
		return getProxy(ShipmentScheduledReply.ID);
	}
	
	@Override
	public void shipmentScheduled(ShipmentScheduledMessage shipmentScheduledMessage) {
		try {
			getProxy().shipmentScheduled(shipmentScheduledMessage);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
