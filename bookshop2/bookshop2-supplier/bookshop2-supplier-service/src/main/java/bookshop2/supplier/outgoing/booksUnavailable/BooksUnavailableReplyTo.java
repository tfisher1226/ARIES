package bookshop2.supplier.outgoing.booksUnavailable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.client.AbstractDelegate;
import org.aries.util.ExceptionUtil;

import bookshop2.BooksUnavailableMessage;


public class BooksUnavailableReplyTo extends AbstractDelegate implements BooksUnavailableReply {

	private static final Log log = LogFactory.getLog(BooksUnavailableReplyTo.class);


	@Override
	public String getDomain() {
		return "bookshop2.supplier";
	}
	
	@Override
	public String getServiceId() {
		return BooksUnavailableReply.ID;
	}

	@SuppressWarnings("unchecked")
	public BooksUnavailableReply getProxy() throws Exception {
		return getProxy(BooksUnavailableReply.ID);
	}

	@Override
	public void booksUnavailable(BooksUnavailableMessage booksUnavailableMessage) {
		try {
			getProxy().booksUnavailable(booksUnavailableMessage);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}

}
