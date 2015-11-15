package bookshop2.supplier.management.reservedBooksManager;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.List;
import java.util.Map;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.message.Message;
import org.aries.message.MessageInterceptor;

import bookshop2.Book;
import bookshop2.BookCriteria;
import bookshop2.BookKey;


@Stateful
@LocalBean
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
public class ReservedBooksManagerInterceptor extends MessageInterceptor<ReservedBooksManager> {
	
	private static final Log log = LogFactory.getLog(ReservedBooksManagerInterceptor.class);
	
	@Inject
	protected ReservedBooksManagerHandler reservedBooksManagerHandler;
	
	
	@TransactionAttribute(REQUIRED)
	public Message getAllReservedBooks(Message message) {
		try {
			List<Book> reservedBooks = reservedBooksManagerHandler.getAllReservedBooks();
			Assert.notNull(reservedBooks, "ReservedBooks must exist");
			message.addPart("reservedBooks", reservedBooks);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message getAllReservedBooksAsMap(Message message) {
		try {
			Map<BookKey, Book> reservedBooks = reservedBooksManagerHandler.getAllReservedBooksAsMap();
			Assert.notNull(reservedBooks, "ReservedBooks must exist");
			message.addPart("reservedBooks", reservedBooks);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message getFromReservedBooksById(Message message) {
		try {
			Long bookId = message.getPart("bookId");
			Book reservedBooks = reservedBooksManagerHandler.getFromReservedBooks(bookId);
			Assert.notNull(reservedBooks, "ReservedBooks must exist");
			message.addPart("reservedBooks", reservedBooks);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message getFromReservedBooksByKey(Message message) {
		try {
			BookKey bookKey = message.getPart("bookKey");
			Book reservedBooks = reservedBooksManagerHandler.getFromReservedBooks(bookKey);
			Assert.notNull(reservedBooks, "ReservedBooks must exist");
			message.addPart("reservedBooks", reservedBooks);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message getFromReservedBooksByIds(Message message) {
		try {
			List<Long> bookIds = message.getPart("bookIds");
			List<Book> reservedBooks = reservedBooksManagerHandler.getFromReservedBooks(bookIds);
			Assert.notNull(reservedBooks, "ReservedBooks must exist");
			message.addPart("reservedBooks", reservedBooks);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message getFromReservedBooksByKeys(Message message) {
		try {
			List<BookKey> bookKeys = message.getPart("bookKeys");
			Map<BookKey, Book> reservedBooks = reservedBooksManagerHandler.getFromReservedBooksAsMap(bookKeys);
			Assert.notNull(reservedBooks, "ReservedBooks must exist");
			message.addPart("reservedBooks", reservedBooks);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message getFromReservedBooksByCriteria(Message message) {
		try {
			BookCriteria bookCriteria = message.getPart("bookCriteria");
			List<Book> reservedBooks = reservedBooksManagerHandler.getFromReservedBooks(bookCriteria);
			Assert.notNull(reservedBooks, "ReservedBooks must exist");
			message.addPart("reservedBooks", reservedBooks);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message getMatchingReservedBooks(Message message) {
		try {
			List<Book> bookList = message.getPart("bookList");
			List<Book> reservedBooks = reservedBooksManagerHandler.getMatchingReservedBooks(bookList);
			Assert.notNull(reservedBooks, "ReservedBooks must exist");
			message.addPart("reservedBooks", reservedBooks);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message getMatchingReservedBooksAsMap(Message message) {
		try {
			List<Book> bookList = message.getPart("bookList");
			Map<BookKey, Book> reservedBooks = reservedBooksManagerHandler.getMatchingReservedBooksAsMap(bookList);
			Assert.notNull(reservedBooks, "ReservedBooks must exist");
			message.addPart("reservedBooks", reservedBooks);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message addToReservedBooks(Message message) {
		try {
			Book book = message.getPart("book");
			Long bookId = reservedBooksManagerHandler.addToReservedBooks(book);
			Assert.notNull(bookId, "BookId must exist");
			message.addPart("bookId", bookId);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message addToReservedBooksAsList(Message message) {
		try {
			List<Book> bookList = message.getPart("bookList");
			List<Long> bookIds = reservedBooksManagerHandler.addToReservedBooks(bookList);
			Assert.notNull(bookIds, "BookIds must exist");
			message.addPart("bookIds", bookIds);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message addToReservedBooksAsMap(Message message) {
		try {
			Map<BookKey, Book> bookMap = message.getPart("bookMap");
			List<Long> bookIds = reservedBooksManagerHandler.addToReservedBooks(bookMap);
			Assert.notNull(bookIds, "BookIds must exist");
			message.addPart("bookIds", bookIds);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message removeAllReservedBooks(Message message) {
		try {
			reservedBooksManagerHandler.removeAllReservedBooks();
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message removeFromReservedBooks(Message message) {
		try {
			Book book = message.getPart("book");
			reservedBooksManagerHandler.removeFromReservedBooks(book);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message removeFromReservedBooksById(Message message) {
		try {
			Long bookId = message.getPart("bookId");
			reservedBooksManagerHandler.removeFromReservedBooks(bookId);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message removeFromReservedBooksByKey(Message message) {
		try {
			BookKey bookKey = message.getPart("bookKey");
			reservedBooksManagerHandler.removeFromReservedBooks(bookKey);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message removeFromReservedBooksAsList(Message message) {
		try {
			List<Book> bookList = message.getPart("bookList");
			reservedBooksManagerHandler.removeFromReservedBooks(bookList);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message removeFromReservedBooksByCriteria(Message message) {
		try {
			BookCriteria bookCriteria = message.getPart("bookCriteria");
			reservedBooksManagerHandler.removeFromReservedBooks(bookCriteria);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
}
