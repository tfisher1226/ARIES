package bookshop2.seller.outgoing.orderRejected;

import org.aries.bean.Proxy;
import org.aries.message.Message;
import org.aries.message.MessageInterceptor;
import org.aries.util.ExceptionUtil;

import bookshop2.OrderRejectedMessage;


public class OrderRejectedReplyInterceptor extends MessageInterceptor<OrderRejectedReply> implements OrderRejectedReply {
	
	@Override
	public void orderRejected(OrderRejectedMessage orderRejectedMessage) {
		try {
			log.info("#### [seller]: orderRejected() sending");
			Message message = createMessage("orderRejected");
			message.addPart("orderRejectedMessage", orderRejectedMessage);
			message.addPart("correlationId", orderRejectedMessage.getCorrelationId());
			message.addPart("transactionId", orderRejectedMessage.getTransactionId());
			Proxy<OrderRejectedReply> proxy = getProxy();
			proxy.send(message);

		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
