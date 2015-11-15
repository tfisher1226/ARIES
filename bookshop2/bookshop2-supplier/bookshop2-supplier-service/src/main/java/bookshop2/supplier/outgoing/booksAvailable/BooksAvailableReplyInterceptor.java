package bookshop2.supplier.outgoing.booksAvailable;

import org.aries.bean.Proxy;
import org.aries.message.Message;
import org.aries.message.MessageInterceptor;
import org.aries.util.ExceptionUtil;

import bookshop2.BooksAvailableMessage;


public class BooksAvailableReplyInterceptor extends MessageInterceptor<BooksAvailableReply> implements BooksAvailableReply {
	
	@Override
	public void booksAvailable(BooksAvailableMessage booksAvailableMessage) {
		try {
			log.info("#### [supplier]: booksAvailable() sending");
			Message message = createMessage("booksAvailable");
			message.addPart("booksAvailableMessage", booksAvailableMessage);
			message.addPart("correlationId", booksAvailableMessage.getCorrelationId());
			message.addPart("transactionId", booksAvailableMessage.getTransactionId());
			Proxy<BooksAvailableReply> proxy = getProxy();
			proxy.send(message);

		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
