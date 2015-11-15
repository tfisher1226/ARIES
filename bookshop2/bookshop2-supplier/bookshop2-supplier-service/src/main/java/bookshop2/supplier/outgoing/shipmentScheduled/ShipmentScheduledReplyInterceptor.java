package bookshop2.supplier.outgoing.shipmentScheduled;

import org.aries.bean.Proxy;
import org.aries.message.Message;
import org.aries.message.MessageInterceptor;
import org.aries.util.ExceptionUtil;

import bookshop2.ShipmentScheduledMessage;


public class ShipmentScheduledReplyInterceptor extends MessageInterceptor<ShipmentScheduledReply> implements ShipmentScheduledReply {
	
	@Override
	public void shipmentScheduled(ShipmentScheduledMessage shipmentScheduledMessage) {
		try {
			log.info("#### [supplier]: shipmentScheduled() sending");
			Message message = createMessage("shipmentScheduled");
			message.addPart("shipmentScheduledMessage", shipmentScheduledMessage);
			message.addPart("correlationId", shipmentScheduledMessage.getCorrelationId());
			message.addPart("transactionId", shipmentScheduledMessage.getTransactionId());
			Proxy<ShipmentScheduledReply> proxy = getProxy();
			proxy.send(message);
			
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
