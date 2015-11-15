package bookshop2.seller.incoming.orderBooks;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.message.Message;
import org.aries.message.MessageInterceptor;

import bookshop2.BooksAvailableMessage;


@Stateful
@LocalBean
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
public class BooksAvailableInterceptor extends MessageInterceptor<BooksAvailable> {

	private static final Log log = LogFactory.getLog(BooksAvailableInterceptor.class);
	
	@Inject
	protected BooksAvailableHandler booksAvailableHandler;
	

	@TransactionAttribute(REQUIRED)
	public Message booksAvailable(Message message) {
		try {
			BooksAvailableMessage booksAvailableMessage = message.getPart("booksAvailableMessage");
			booksAvailableHandler.booksAvailable(booksAvailableMessage);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}

		return message;
	}
	
}
