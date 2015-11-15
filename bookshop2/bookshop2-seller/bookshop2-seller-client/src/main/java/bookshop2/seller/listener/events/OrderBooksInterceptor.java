package bookshop2.seller.listener.events;

import org.aries.bean.Proxy;
import org.aries.message.Message;
import org.aries.message.MessageInterceptor;
import org.aries.util.ExceptionUtil;

import bookshop2.OrderRequestMessage;


public class OrderBooksInterceptor extends MessageInterceptor<OrderBooks> implements OrderBooks {
	
	@Override
	public void orderBooks(OrderRequestMessage orderRequestMessage) {
		try {
			log.info("#### [seller]: orderBooks() sending");
			Message message = createMessage("orderBooks");
			message.addPart("orderRequestMessage", orderRequestMessage);
			message.addPart("correlationId", orderRequestMessage.getCorrelationId());
			message.addPart("transactionId", orderRequestMessage.getTransactionId());
			Proxy<OrderBooks> proxy = getProxy();
			proxy.send(message);

		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}

}
