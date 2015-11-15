package bookshop2.supplier.management.reservedBooksManager;

import java.util.List;
import java.util.Map;

import org.aries.message.Message;
import org.aries.message.MessageInterceptor;
import org.aries.util.ExceptionUtil;

import bookshop2.Book;
import bookshop2.BookCriteria;
import bookshop2.BookKey;


@SuppressWarnings("serial")
public class ReservedBooksManagerInterceptor extends MessageInterceptor<ReservedBooksManager> implements ReservedBooksManager {
	
	@Override
	public List<Book> getAllReservedBooks() {
		try {
			log.info("#### [supplier-client]: getAllReservedBooks() sending");
			Message request = createMessage("getAllReservedBooks");
			Message response = getProxy().invoke(request);
			List<Book> reservedBooks = response.getPart("reservedBooks");
			return reservedBooks;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Map<BookKey, Book> getAllReservedBooksAsMap() {
		try {
			log.info("#### [supplier-client]: getAllReservedBooksAsMap() sending");
			Message request = createMessage("getAllReservedBooksAsMap");
			Message response = getProxy().invoke(request);
			Map<BookKey, Book> reservedBooks = response.getPart("reservedBooks");
			return reservedBooks;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Book getFromReservedBooksById(Long bookId) {
		try {
			log.info("#### [supplier-client]: getFromReservedBooksById() sending");
			Message request = createMessage("getFromReservedBooksById");
			request.addPart("bookId", bookId);
			Message response = getProxy().invoke(request);
		Book reservedBooks = response.getPart("reservedBooks");
			return reservedBooks;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Book getFromReservedBooksByKey(BookKey bookKey) {
		try {
			log.info("#### [supplier-client]: getFromReservedBooksByKey() sending");
			Message request = createMessage("getFromReservedBooksByKey");
			request.addPart("bookKey", bookKey);
			Message response = getProxy().invoke(request);
			Book reservedBooks = response.getPart("reservedBooks");
			return reservedBooks;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Book> getFromReservedBooksByIds(List<Long> bookIds) {
		try {
			log.info("#### [supplier-client]: getFromReservedBooksByIds() sending");
			Message request = createMessage("getFromReservedBooksByIds");
			request.addPart("bookIds", bookIds);
			Message response = getProxy().invoke(request);
			List<Book> reservedBooks = response.getPart("reservedBooks");
			return reservedBooks;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Map<BookKey, Book> getFromReservedBooksByKeys(List<BookKey> bookKeys) {
		try {
			log.info("#### [supplier-client]: getFromReservedBooksByKeys() sending");
			Message request = createMessage("getFromReservedBooksByKeys");
			request.addPart("bookKeys", bookKeys);
			Message response = getProxy().invoke(request);
			Map<BookKey, Book> reservedBooks = response.getPart("reservedBooks");
			return reservedBooks;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Book> getFromReservedBooksByCriteria(BookCriteria bookCriteria) {
		try {
			log.info("#### [supplier-client]: getFromReservedBooksByCriteria() sending");
			Message request = createMessage("getFromReservedBooksByCriteria");
			request.addPart("bookCriteria", bookCriteria);
			Message response = getProxy().invoke(request);
		List<Book> reservedBooks = response.getPart("reservedBooks");
			return reservedBooks;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Book> getMatchingReservedBooks(List<Book> bookList) {
		try {
			log.info("#### [supplier-client]: getMatchingReservedBooks() sending");
			Message request = createMessage("getMatchingReservedBooks");
			request.addPart("bookList", bookList);
			Message response = getProxy().invoke(request);
			List<Book> reservedBooks = response.getPart("reservedBooks");
			return reservedBooks;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Map<BookKey, Book> getMatchingReservedBooksAsMap(List<Book> bookList) {
		try {
			log.info("#### [supplier-client]: getMatchingReservedBooksAsMap() sending");
			Message request = createMessage("getMatchingReservedBooksAsMap");
			request.addPart("bookList", bookList);
			Message response = getProxy().invoke(request);
			Map<BookKey, Book> reservedBooks = response.getPart("reservedBooks");
			return reservedBooks;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Long addToReservedBooks(Book book) {
		try {
			log.info("#### [supplier-client]: addToReservedBooks() sending");
			Message request = createMessage("addToReservedBooks");
			request.addPart("book", book);
			Message response = getProxy().invoke(request);
			Long bookId = response.getPart("bookId");
			return bookId;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Long> addToReservedBooksAsList(List<Book> bookList) {
		try {
			log.info("#### [supplier-client]: addToReservedBooksAsList() sending");
			Message request = createMessage("addToReservedBooksAsList");
			request.addPart("bookList", bookList);
			Message response = getProxy().invoke(request);
			List<Long> bookIds = response.getPart("bookIds");
			return bookIds;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Long> addToReservedBooksAsMap(Map<BookKey, Book> bookMap) {
		try {
			log.info("#### [supplier-client]: addToReservedBooksAsMap() sending");
			Message request = createMessage("addToReservedBooksAsMap");
			request.addPart("bookMap", bookMap);
			Message response = getProxy().invoke(request);
			List<Long> bookIds = response.getPart("bookIds");
			return bookIds;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeAllReservedBooks() {
		try {
			log.info("#### [supplier-client]: removeAllReservedBooks() sending");
			Message request = createMessage("removeAllReservedBooks");
			getProxy().send(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeFromReservedBooks(Book book) {
		try {
			log.info("#### [supplier-client]: removeFromReservedBooks() sending");
			Message request = createMessage("removeFromReservedBooks");
			request.addPart("book", book);
			getProxy().send(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeFromReservedBooksById(Long bookId) {
		try {
			log.info("#### [supplier-client]: removeFromReservedBooksById() sending");
			Message request = createMessage("removeFromReservedBooksById");
			request.addPart("bookId", bookId);
			getProxy().send(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeFromReservedBooksByKey(BookKey bookKey) {
		try {
			log.info("#### [supplier-client]: removeFromReservedBooksByKey() sending");
			Message request = createMessage("removeFromReservedBooksByKey");
			request.addPart("bookKey", bookKey);
			getProxy().send(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeFromReservedBooksAsList(List<Book> bookList) {
		try {
			log.info("#### [supplier-client]: removeFromReservedBooksAsList() sending");
			Message request = createMessage("removeFromReservedBooksAsList");
			request.addPart("bookList", bookList);
			getProxy().send(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeFromReservedBooksByCriteria(BookCriteria bookCriteria) {
		try {
			log.info("#### [supplier-client]: removeFromReservedBooksByCriteria() sending");
			Message request = createMessage("removeFromReservedBooksByCriteria");
			request.addPart("bookCriteria", bookCriteria);
			getProxy().send(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
