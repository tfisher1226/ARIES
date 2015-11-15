package bookshop2.shipper.outgoing.shipmentFailed;

import org.aries.bean.Proxy;
import org.aries.message.Message;
import org.aries.message.MessageInterceptor;
import org.aries.util.ExceptionUtil;

import bookshop2.ShipmentFailedMessage;


public class ShipmentFailedReplyInterceptor extends MessageInterceptor<ShipmentFailedReply> implements ShipmentFailedReply {
	
	@Override
	public void shipmentFailed(ShipmentFailedMessage shipmentFailedMessage) {
		try {
			log.info("#### [shipper]: shipmentFailed() sending");
			Message message = createMessage("shipmentFailed");
			message.addPart("shipmentFailedMessage", shipmentFailedMessage);
			message.addPart("correlationId", shipmentFailedMessage.getCorrelationId());
			message.addPart("transactionId", shipmentFailedMessage.getTransactionId());
			Proxy<ShipmentFailedReply> proxy = getProxy();
			proxy.send(message);

		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
