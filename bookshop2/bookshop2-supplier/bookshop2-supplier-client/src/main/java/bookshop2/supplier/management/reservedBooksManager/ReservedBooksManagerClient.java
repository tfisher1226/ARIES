package bookshop2.supplier.management.reservedBooksManager;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.client.AbstractDelegate;
import org.aries.util.ExceptionUtil;

import bookshop2.Book;
import bookshop2.BookCriteria;
import bookshop2.BookKey;


public class ReservedBooksManagerClient extends AbstractDelegate implements ReservedBooksManager {
	
	private static final Log log = LogFactory.getLog(ReservedBooksManagerClient.class);
	
	
	@Override
	public String getDomain() {
		return "bookshop2.supplier";
	}
	
	@Override
	public String getServiceId() {
		return ReservedBooksManager.ID;
	}
	
	@SuppressWarnings("unchecked")
	public ReservedBooksManager getProxy() throws Exception {
		return getProxy(ReservedBooksManager.ID);
	}
	
	@Override
	public List<Book> getAllReservedBooks() {
		try {
			List<Book> reservedBooks = getProxy().getAllReservedBooks();
			return reservedBooks;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Map<BookKey, Book> getAllReservedBooksAsMap() {
		try {
			Map<BookKey, Book> reservedBooks = getProxy().getAllReservedBooksAsMap();
			return reservedBooks;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Book getFromReservedBooksById(Long bookId) {
		try {
			Book reservedBooks = getProxy().getFromReservedBooksById(bookId);
			return reservedBooks;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Book getFromReservedBooksByKey(BookKey bookKey) {
		try {
			Book reservedBooks = getProxy().getFromReservedBooksByKey(bookKey);
			return reservedBooks;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Book> getFromReservedBooksByIds(List<Long> bookIds) {
		try {
			List<Book> reservedBooks = getProxy().getFromReservedBooksByIds(bookIds);
			return reservedBooks;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Map<BookKey, Book> getFromReservedBooksByKeys(List<BookKey> bookKeys) {
		try {
			Map<BookKey, Book> reservedBooks = getProxy().getFromReservedBooksByKeys(bookKeys);
			return reservedBooks;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Book> getFromReservedBooksByCriteria(BookCriteria bookCriteria) {
		try {
			List<Book> reservedBooks = getProxy().getFromReservedBooksByCriteria(bookCriteria);
			return reservedBooks;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Book> getMatchingReservedBooks(List<Book> bookList) {
		try {
			List<Book> reservedBooks = getProxy().getMatchingReservedBooks(bookList);
			return reservedBooks;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Map<BookKey, Book> getMatchingReservedBooksAsMap(List<Book> bookList) {
		try {
			Map<BookKey, Book> reservedBooks = getProxy().getMatchingReservedBooksAsMap(bookList);
			return reservedBooks;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Long addToReservedBooks(Book book) {
		try {
			Long bookId = getProxy().addToReservedBooks(book);
			return bookId;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Long> addToReservedBooksAsList(List<Book> bookList) {
		try {
			List<Long> bookIds = getProxy().addToReservedBooksAsList(bookList);
			return bookIds;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Long> addToReservedBooksAsMap(Map<BookKey, Book> bookMap) {
		try {
			List<Long> bookIds = getProxy().addToReservedBooksAsMap(bookMap);
			return bookIds;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeAllReservedBooks() {
		try {
			getProxy().removeAllReservedBooks();
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeFromReservedBooks(Book book) {
		try {
			getProxy().removeFromReservedBooks(book);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeFromReservedBooksById(Long bookId) {
		try {
			getProxy().removeFromReservedBooksById(bookId);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeFromReservedBooksByKey(BookKey bookKey) {
		try {
			getProxy().removeFromReservedBooksByKey(bookKey);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeFromReservedBooksAsList(List<Book> bookList) {
		try {
			getProxy().removeFromReservedBooksAsList(bookList);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeFromReservedBooksByCriteria(BookCriteria bookCriteria) {
		try {
			getProxy().removeFromReservedBooksByCriteria(bookCriteria);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
