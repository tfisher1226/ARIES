package bookshop2.client.bookService;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.client.AbstractDelegate;
import org.aries.util.ExceptionUtil;

import bookshop2.Book;
import bookshop2.BookCriteria;


public class BookServiceClient extends AbstractDelegate implements BookService {
	
	private static final Log log = LogFactory.getLog(BookServiceClient.class);
	
	
	@Override
	public String getDomain() {
		return "bookshop2";
	}
	
	@Override
	public String getServiceId() {
		return BookService.ID;
	}
	
	@SuppressWarnings("unchecked")
	public BookService getProxy() throws Exception {
		return getProxy(BookService.ID);
	}
	
	@Override
	public List<Book> getAllBookRecords() {
		try {
			List<Book> result = getProxy().getAllBookRecords();
			return result;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Book getBookRecordById(Long id) {
		try {
			Book result = getProxy().getBookRecordById(id);
			return result;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Book> getBookRecordsByPage(int pageIndex, int pageSize) {
		try {
			List<Book> result = getProxy().getBookRecordsByPage(pageIndex, pageSize);
			return result;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Book> getBookRecordsByCriteria(BookCriteria bookCriteria) {
		try {
			List<Book> result = getProxy().getBookRecordsByCriteria(bookCriteria);
			return result;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Long addBookRecord(Book book) {
		try {
			Long result = getProxy().addBookRecord(book);
			return result;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void saveBookRecord(Book book) {
		try {
			getProxy().saveBookRecord(book);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeAllBookRecords() {
		try {
			getProxy().removeAllBookRecords();
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeBookRecord(Book book) {
		try {
			getProxy().removeBookRecord(book);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeBookRecords(BookCriteria bookCriteria) {
		try {
			getProxy().removeBookRecords(bookCriteria);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void importBookRecords() {
		try {
			getProxy().importBookRecords();
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
