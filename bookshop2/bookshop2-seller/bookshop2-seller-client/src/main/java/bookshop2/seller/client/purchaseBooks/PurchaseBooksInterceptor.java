package bookshop2.seller.client.purchaseBooks;

import org.aries.bean.Proxy;
import org.aries.message.Message;
import org.aries.message.MessageInterceptor;
import org.aries.util.ExceptionUtil;

import bookshop2.PurchaseRequestMessage;


public class PurchaseBooksInterceptor extends MessageInterceptor<PurchaseBooks> implements PurchaseBooks {
	
	@Override
	public void purchaseBooks(PurchaseRequestMessage purchaseRequestMessage) {
		try {
			log.info("#### [seller]: purchaseBooks() sending");
			Message message = createMessage("purchaseBooks");
			message.addPart("purchaseRequestMessage", purchaseRequestMessage);
			message.addPart("correlationId", purchaseRequestMessage.getCorrelationId());
			message.addPart("transactionId", purchaseRequestMessage.getTransactionId());
			Proxy<PurchaseBooks> proxy = getProxy();
			proxy.send(message);

		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
