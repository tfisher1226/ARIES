package bookshop2.shipper.client.shipBooks;

import org.aries.bean.Proxy;
import org.aries.message.Message;
import org.aries.message.MessageInterceptor;
import org.aries.util.ExceptionUtil;

import bookshop2.ShipmentRequestMessage;


public class ShipBooksInterceptor extends MessageInterceptor<ShipBooks> implements ShipBooks {
	
	@Override
	public void shipBooks(ShipmentRequestMessage shipmentRequestMessage) {
		try {
			log.info("#### [shipper-client]: shipBooks() sending");
			Message message = createMessage("shipBooks");
			message.addPart("shipmentRequestMessage", shipmentRequestMessage);
			message.addPart("correlationId", shipmentRequestMessage.getCorrelationId());
			message.addPart("transactionId", shipmentRequestMessage.getTransactionId());
			Proxy<ShipBooks> proxy = getProxy();
			proxy.send(message);

		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
