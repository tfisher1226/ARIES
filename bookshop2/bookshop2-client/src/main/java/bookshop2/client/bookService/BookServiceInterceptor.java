package bookshop2.client.bookService;

import java.util.List;

import org.aries.message.Message;
import org.aries.message.MessageInterceptor;
import org.aries.util.ExceptionUtil;

import bookshop2.Book;
import bookshop2.BookCriteria;


@SuppressWarnings("serial")
public class BookServiceInterceptor extends MessageInterceptor<BookService> implements BookService {
	
	@Override
	public List<Book> getAllBookRecords() {
		try {
			log.info("#### [admin]: getAllBookRecords() sending...");
			Message request = createMessage("getAllBookRecords");
			Message response = getProxy().invoke(request);
		List<Book> result = response.getPart("result");
			return result;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Book getBookRecordById(Long id) {
		try {
			log.info("#### [admin]: getBookRecordById() sending...");
			Message request = createMessage("getBookRecordById");
			request.addPart("id", id);
			Message response = getProxy().invoke(request);
		Book result = response.getPart("result");
			return result;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Book> getBookRecordsByPage(int pageIndex, int pageSize) {
		try {
			log.info("#### [admin]: getBookRecordsByPage() sending...");
			Message request = createMessage("getBookRecordsByPage");
			request.addPart("pageIndex", pageIndex);
			request.addPart("pageSize", pageSize);
			Message response = getProxy().invoke(request);
		List<Book> result = response.getPart("result");
			return result;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Book> getBookRecordsByCriteria(BookCriteria bookCriteria) {
		try {
			log.info("#### [admin]: getBookRecordsByCriteria() sending...");
			Message request = createMessage("getBookRecordsByCriteria");
			request.addPart("bookCriteria", bookCriteria);
			Message response = getProxy().invoke(request);
		List<Book> result = response.getPart("result");
			return result;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Long addBookRecord(Book book) {
		try {
			log.info("#### [admin]: addBookRecord() sending...");
			Message request = createMessage("addBookRecord");
			request.addPart("book", book);
			Message response = getProxy().invoke(request);
		Long result = response.getPart("result");
			return result;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void saveBookRecord(Book book) {
		try {
			log.info("#### [admin]: saveBookRecord() sending...");
			Message request = createMessage("saveBookRecord");
			request.addPart("book", book);
			getProxy().invoke(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeAllBookRecords() {
		try {
			log.info("#### [admin]: removeAllBookRecords() sending...");
			Message request = createMessage("removeAllBookRecords");
			getProxy().invoke(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeBookRecord(Book book) {
		try {
			log.info("#### [admin]: removeBookRecord() sending...");
			Message request = createMessage("removeBookRecord");
			request.addPart("book", book);
			getProxy().invoke(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeBookRecords(BookCriteria bookCriteria) {
		try {
			log.info("#### [admin]: removeBookRecords() sending...");
			Message request = createMessage("removeBookRecords");
			request.addPart("bookCriteria", bookCriteria);
			getProxy().invoke(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void importBookRecords() {
		try {
			log.info("#### [admin]: importBookRecords() sending...");
			Message request = createMessage("importBookRecords");
			getProxy().invoke(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
