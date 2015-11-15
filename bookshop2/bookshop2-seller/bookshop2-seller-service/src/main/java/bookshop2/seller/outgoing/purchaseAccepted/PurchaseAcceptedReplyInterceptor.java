package bookshop2.seller.outgoing.purchaseAccepted;

import org.aries.bean.Proxy;
import org.aries.message.Message;
import org.aries.message.MessageInterceptor;
import org.aries.util.ExceptionUtil;

import bookshop2.PurchaseAcceptedMessage;


public class PurchaseAcceptedReplyInterceptor extends MessageInterceptor<PurchaseAcceptedReply> implements PurchaseAcceptedReply {
	
	@Override
	public void purchaseAccepted(PurchaseAcceptedMessage purchaseAcceptedMessage) {
		try {
			log.info("#### [seller]: purchaseAccepted() sending");
			Message message = createMessage("purchaseAccepted");
			message.addPart("purchaseAcceptedMessage", purchaseAcceptedMessage);
			message.addPart("correlationId", purchaseAcceptedMessage.getCorrelationId());
			message.addPart("transactionId", purchaseAcceptedMessage.getTransactionId());
			Proxy<PurchaseAcceptedReply> proxy = getProxy();
			proxy.send(message);

		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
