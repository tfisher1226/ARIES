package bookshop2.supplier.data.bookInventory;

import static javax.ejb.ConcurrencyManagementType.BEAN;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import bookshop2.Book;
import bookshop2.BookCriteria;
import bookshop2.BookKey;
import bookshop2.util.Bookshop2Helper;

import common.tx.state.ServiceStateProcessor;


@Startup
@Singleton
@LocalBean
@ConcurrencyManagement(BEAN)
public class BookInventoryProcessor implements ServiceStateProcessor<BookInventoryState> {
	
	private static final Log log = LogFactory.getLog(BookInventoryProcessor.class);
	
	private Map<BookKey, Book> pendingReservedBooks = new LinkedHashMap<BookKey, Book>();
	
	
	public BookInventoryProcessor() {
		// nothing for now
	}
	
	
	public void resetState(BookInventoryState state) {
		state.removeAllReservedBooks();
	}
	
	public boolean validateState(BookInventoryState state) {
		return pendingReservedBooks != null;
	}
	
	public void updateState(BookInventoryState state) {
		state.addToReservedBooks(pendingReservedBooks);
	}
	
	public void processRequest() {
		//nothing for now
	}
	
	public List<Book> getAllPendingReservedBooks() {
		synchronized (pendingReservedBooks) {
			List<Book> bookList = new ArrayList<Book>(pendingReservedBooks.values());
			return bookList;
		}
	}
	
	public Map<BookKey, Book> getAllPendingReservedBooksAsMap() {
		synchronized (pendingReservedBooks) {
			Map<BookKey, Book> bookMap = new HashMap<BookKey, Book>(pendingReservedBooks);
			return bookMap;
		}
	}
	
	public Book getFromPendingReservedBooks(Long bookId) {
		synchronized (pendingReservedBooks) {
			Iterator<Book> iterator = pendingReservedBooks.values().iterator();
			while (iterator.hasNext()) {
				Book book = iterator.next();
				Long id = book.getId();
				if (id != null && id.equals(bookId)) {
					return book;
				}
			}
			return null;
		}
	}
	
	public List<Book> getFromPendingReservedBooks(Collection<Long> bookIds) {
		return null;
	}
	
	public Book getFromPendingReservedBooks(BookKey bookKey) {
		synchronized (pendingReservedBooks) {
			return pendingReservedBooks.get(bookKey);
		}
	}
	
	public Map<BookKey, Book> getFromPendingReservedBooksAsMap(Collection<BookKey> bookKeys) {
		synchronized (pendingReservedBooks) {
			return pendingReservedBooks;
		}
	}
	
	public List<Book> getFromPendingReservedBooks(BookCriteria bookCriteria) {
		synchronized (pendingReservedBooks) {
			List<Book> list = new ArrayList<Book>();
			Iterator<Book> iterator = pendingReservedBooks.values().iterator();
			while (iterator.hasNext()) {
				Book book = iterator.next();
				if (Bookshop2Helper.isMatch(bookCriteria, book)) {
					list.add(book);
				}
			}
			return list;
		}
	}
	
	public void setPendingReservedBooks(Map<BookKey, Book> bookMap) {
		synchronized (pendingReservedBooks) {
			pendingReservedBooks = new LinkedHashMap<BookKey, Book>(bookMap);
	}
	}
	
	public void addToPendingReservedBooks(Book book) {
		synchronized (pendingReservedBooks) {
			BookKey bookKey = Bookshop2Helper.createBookKey(book);
			pendingReservedBooks.put(bookKey, book);
		}
	}
	
	public void addToPendingReservedBooks(Collection<Book> bookList) {
		synchronized (pendingReservedBooks) {
			Map<BookKey, Book> bookMap = Bookshop2Helper.createBookMap(bookList);
			pendingReservedBooks.putAll(bookMap);
		}
	}
	
	public void addToPendingReservedBooks(Map<BookKey, Book> bookMap) {
		synchronized (pendingReservedBooks) {
			pendingReservedBooks.putAll(bookMap);
		}
	}
	
	public void removeAllPendingReservedBooks() {
		synchronized (pendingReservedBooks) {
			pendingReservedBooks.clear();
		}
	}
	
	public void removeFromPendingReservedBooks(Book book) {
		synchronized (pendingReservedBooks) {
			BookKey bookKey = Bookshop2Helper.createBookKey(book);
			pendingReservedBooks.remove(bookKey);
		}
	}
	
	public void removeFromPendingReservedBooks(Long bookId) {
		synchronized (pendingReservedBooks) {
			Book book = getFromPendingReservedBooks(bookId);
			if (book != null) {
				removeFromPendingReservedBooks(book);
			}
		}
	}
	
	public void removeFromPendingReservedBooks(BookKey bookKey) {
		synchronized (pendingReservedBooks) {
			pendingReservedBooks.remove(bookKey);
		}
	}
	
	public void removeFromPendingReservedBooks(Collection<Book> bookList) {
		synchronized (pendingReservedBooks) {
			Collection<BookKey> bookKeys = Bookshop2Helper.createBookKeys(bookList);
			Iterator<BookKey> iterator = bookKeys.iterator();
			while (iterator.hasNext()) {
				BookKey key = iterator.next();
				pendingReservedBooks.remove(key);
			}
		}
	}
	
	public void removeFromPendingReservedBooks(BookCriteria bookCriteria) {
		synchronized (pendingReservedBooks) {
			List<Book> bookList = getFromPendingReservedBooks(bookCriteria);
			removeFromPendingReservedBooks(bookList);
		}
	}
	
}
