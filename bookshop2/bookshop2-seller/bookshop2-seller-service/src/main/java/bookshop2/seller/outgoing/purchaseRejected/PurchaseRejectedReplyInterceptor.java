package bookshop2.seller.outgoing.purchaseRejected;

import org.aries.bean.Proxy;
import org.aries.message.Message;
import org.aries.message.MessageInterceptor;
import org.aries.util.ExceptionUtil;

import bookshop2.PurchaseRejectedMessage;


public class PurchaseRejectedReplyInterceptor extends MessageInterceptor<PurchaseRejectedReply> implements PurchaseRejectedReply {
	
	@Override
	public void purchaseRejected(PurchaseRejectedMessage purchaseRejectedMessage) {
		try {
			log.info("#### [seller]: purchaseRejected() sending");
			Message message = createMessage("purchaseRejected");
			message.addPart("purchaseRejectedMessage", purchaseRejectedMessage);
			message.addPart("correlationId", purchaseRejectedMessage.getCorrelationId());
			message.addPart("transactionId", purchaseRejectedMessage.getTransactionId());
			Proxy<PurchaseRejectedReply> proxy = getProxy();
			proxy.send(message);

		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
