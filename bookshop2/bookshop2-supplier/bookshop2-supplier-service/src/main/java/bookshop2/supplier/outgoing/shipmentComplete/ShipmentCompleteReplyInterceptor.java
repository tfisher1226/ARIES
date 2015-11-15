package bookshop2.supplier.outgoing.shipmentComplete;

import org.aries.bean.Proxy;
import org.aries.message.Message;
import org.aries.message.MessageInterceptor;
import org.aries.util.ExceptionUtil;

import bookshop2.ShipmentCompleteMessage;


public class ShipmentCompleteReplyInterceptor extends MessageInterceptor<ShipmentCompleteReply> implements ShipmentCompleteReply {
	
	@Override
	public void shipmentComplete(ShipmentCompleteMessage shipmentCompleteMessage) {
		try {
			log.info("#### [supplier]: shipmentComplete() sending");
			Message message = createMessage("shipmentComplete");
			message.addPart("shipmentCompleteMessage", shipmentCompleteMessage);
			message.addPart("correlationId", shipmentCompleteMessage.getCorrelationId());
			message.addPart("transactionId", shipmentCompleteMessage.getTransactionId());
			Proxy<ShipmentCompleteReply> proxy = getProxy();
			proxy.send(message);

		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
