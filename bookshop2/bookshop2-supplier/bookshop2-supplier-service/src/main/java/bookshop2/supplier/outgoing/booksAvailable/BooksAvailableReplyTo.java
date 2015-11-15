package bookshop2.supplier.outgoing.booksAvailable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.client.AbstractDelegate;
import org.aries.util.ExceptionUtil;

import bookshop2.BooksAvailableMessage;


public class BooksAvailableReplyTo extends AbstractDelegate implements BooksAvailableReply {

	private static final Log log = LogFactory.getLog(BooksAvailableReplyTo.class);


	@Override
	public String getDomain() {
		return "bookshop2.supplier";
	}
	
	@Override
	public String getServiceId() {
		return BooksAvailableReply.ID;
	}

	@SuppressWarnings("unchecked")
	public BooksAvailableReply getProxy() throws Exception {
		return getProxy(BooksAvailableReply.ID);
	}

	@Override
	public void booksAvailable(BooksAvailableMessage booksAvailableMessage) {
		try {
			getProxy().booksAvailable(booksAvailableMessage);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}

}
