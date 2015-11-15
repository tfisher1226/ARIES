package bookshop2.supplier.outgoing.booksUnavailable;

import org.aries.bean.Proxy;
import org.aries.message.Message;
import org.aries.message.MessageInterceptor;
import org.aries.util.ExceptionUtil;

import bookshop2.BooksUnavailableMessage;


public class BooksUnavailableReplyInterceptor extends MessageInterceptor<BooksUnavailableReply> implements BooksUnavailableReply {
	
	@Override
	public void booksUnavailable(BooksUnavailableMessage booksUnavailableMessage) {
		try {
			log.info("#### [supplier]: booksUnavailable() sending");
			Message message = createMessage("booksUnavailable");
			message.addPart("booksUnavailableMessage", booksUnavailableMessage);
			message.addPart("correlationId", booksUnavailableMessage.getCorrelationId());
			message.addPart("transactionId", booksUnavailableMessage.getTransactionId());
			Proxy<BooksUnavailableReply> proxy = getProxy();
			proxy.send(message);

		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
