package bookshop2.seller.incoming.orderBooks;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.message.Message;
import org.aries.message.MessageInterceptor;

import bookshop2.BooksUnavailableMessage;


@Stateful
@LocalBean
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
public class BooksUnavailableInterceptor extends MessageInterceptor<BooksUnavailable> {
	
	private static final Log log = LogFactory.getLog(BooksUnavailableInterceptor.class);
	
	@Inject
	protected BooksUnavailableHandler booksUnavailableHandler;
	
	
	@TransactionAttribute(REQUIRED)
	public Message booksUnavailable(Message message) {
		try {
			BooksUnavailableMessage booksUnavailableMessage = message.getPart("booksUnavailableMessage");
			booksUnavailableHandler.booksUnavailable(booksUnavailableMessage);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
}
