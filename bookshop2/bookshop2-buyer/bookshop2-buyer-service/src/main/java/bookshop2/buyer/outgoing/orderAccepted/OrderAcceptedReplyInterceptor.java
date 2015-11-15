package bookshop2.buyer.outgoing.orderAccepted;

import org.aries.bean.Proxy;
import org.aries.message.Message;
import org.aries.message.MessageInterceptor;
import org.aries.util.ExceptionUtil;

import bookshop2.OrderAcceptedMessage;


public class OrderAcceptedReplyInterceptor extends MessageInterceptor<OrderAcceptedReply> implements OrderAcceptedReply {
	
	@Override
	public void orderAccepted(OrderAcceptedMessage orderAcceptedMessage) {
		try {
			log.info("#### [buyer]: orderAccepted() sending");
			Message message = createMessage("orderAccepted");
			//message.addPart("orderAcceptedMessage", orderAcceptedMessage);
			message.addPart("response", orderAcceptedMessage);
			message.addPart("correlationId", orderAcceptedMessage.getCorrelationId());
			message.addPart("transactionId", orderAcceptedMessage.getTransactionId());
			Proxy<OrderAcceptedReply> proxy = getProxy();
			proxy.send(message);

		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
