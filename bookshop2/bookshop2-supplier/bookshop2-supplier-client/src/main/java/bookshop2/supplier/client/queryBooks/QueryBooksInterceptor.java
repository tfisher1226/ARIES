package bookshop2.supplier.client.queryBooks;

import org.aries.bean.Proxy;
import org.aries.message.Message;
import org.aries.message.MessageInterceptor;
import org.aries.util.ExceptionUtil;

import bookshop2.QueryRequestMessage;


public class QueryBooksInterceptor extends MessageInterceptor<QueryBooks> implements QueryBooks {
	
	@Override
	public void queryBooks(QueryRequestMessage queryRequestMessage) {
		try {
			log.info("#### [supplier-client]: queryBooks() sending");
			Message message = createMessage("queryBooks");
			message.addPart("queryRequestMessage", queryRequestMessage);
			message.addPart("correlationId", queryRequestMessage.getCorrelationId());
			message.addPart("transactionId", queryRequestMessage.getTransactionId());
			Proxy<QueryBooks> proxy = getProxy();
			proxy.send(message);

		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
