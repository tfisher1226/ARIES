package bookshop2.supplier.data.supplierOrderCache;

import static javax.ejb.ConcurrencyManagementType.BEAN;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
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
public class SupplierOrderCacheProcessor implements ServiceStateProcessor<SupplierOrderCacheState> {
	
	private static final Log log = LogFactory.getLog(SupplierOrderCacheProcessor.class);
	
	private Map<BookKey, Book> pendingBooksInStock = new HashMap<BookKey, Book>();
	
	
	public SupplierOrderCacheProcessor() {
		// nothing for now
	}
	
	
	public void resetState(SupplierOrderCacheState state) {
		state.removeAllBooksInStock();
	}
	
	public boolean validateState(SupplierOrderCacheState state) {
		//return pendingBooksInStock != null;
		return true;
	}
	
	public void updateState(SupplierOrderCacheState state) {
		state.addToBooksInStock(pendingBooksInStock);
	}
	
	public void processRequest() {
		//nothing for now
	}
	
	public List<Book> getAllPendingBooksInStock() {
		synchronized (pendingBooksInStock) {
			List<Book> bookList = new ArrayList<Book>(pendingBooksInStock.values());
			return bookList;
		}
	}
	
	public Map<BookKey, Book> getAllPendingBooksInStockAsMap() {
		synchronized (pendingBooksInStock) {
			Map<BookKey, Book> bookMap = new HashMap<BookKey, Book>(pendingBooksInStock);
			return bookMap;
		}
	}
	
	public Book getFromPendingBooksInStock(Long bookId) {
		synchronized (pendingBooksInStock) {
			Iterator<Book> iterator = pendingBooksInStock.values().iterator();
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
	
	public List<Book> getFromPendingBooksInStock(Collection<Long> bookIds) {
		return null;
	}
	
	public Book getFromPendingBooksInStock(BookKey bookKey) {
		synchronized (pendingBooksInStock) {
			return pendingBooksInStock.get(bookKey);
		}
	}
	
	public Map<BookKey, Book> getFromPendingBooksInStockAsMap(Collection<BookKey> bookKeys) {
		synchronized (pendingBooksInStock) {
			return pendingBooksInStock;
		}
	}
	
	public List<Book> getFromPendingBooksInStock(BookCriteria bookCriteria) {
		synchronized (pendingBooksInStock) {
			List<Book> bookList = new ArrayList<Book>();
			Iterator<Book> iterator = pendingBooksInStock.values().iterator();
			while (iterator.hasNext()) {
				Book book = iterator.next();
				if (Bookshop2Helper.isMatch(bookCriteria, book)) {
					bookList.add(book);
				}
			}
			return bookList;
		}
	}
	
	public void setPendingBooksInStock(Map<BookKey, Book> bookMap) {
		synchronized (pendingBooksInStock) {
			pendingBooksInStock = new HashMap<BookKey, Book>(bookMap);
		}
	}
	
	public void addToPendingBooksInStock(Book book) {
		synchronized (pendingBooksInStock) {
			BookKey bookKey = Bookshop2Helper.createBookKey(book);
			pendingBooksInStock.put(bookKey, book);
		}
	}
	
	public void addToPendingBooksInStock(Collection<Book> bookList) {
		synchronized (pendingBooksInStock) {
			Map<BookKey, Book> bookMap = Bookshop2Helper.createBookMap(bookList);
			pendingBooksInStock.putAll(bookMap);
		}
	}
	
	public void addToPendingBooksInStock(Map<BookKey, Book> bookMap) {
		synchronized (pendingBooksInStock) {
			pendingBooksInStock.putAll(bookMap);
		}
	}
	
	public void removeAllPendingBooksInStock() {
		synchronized (pendingBooksInStock) {
			pendingBooksInStock.clear();
		}
	}
	
	public void removeFromPendingBooksInStock(Book book) {
		synchronized (pendingBooksInStock) {
			pendingBooksInStock.remove(book);
		}
	}
	
	public void removeFromPendingBooksInStock(Long bookId) {
		//nothing for now
	}
	
	public void removeFromPendingBooksInStock(BookKey bookKey) {
		synchronized (pendingBooksInStock) {
			pendingBooksInStock.remove(bookKey);
		}
	}
	
	public void removeFromPendingBooksInStock(Collection<Book> bookList) {
		synchronized (pendingBooksInStock) {
			Collection<BookKey> bookKeys = Bookshop2Helper.createBookKeys(bookList);
			Iterator<BookKey> iterator = bookKeys.iterator();
			while (iterator.hasNext()) {
				BookKey key = iterator.next();
				pendingBooksInStock.remove(key);
			}
		}
	}
	
	public void removeFromPendingBooksInStock(BookCriteria bookCriteria) {
		//nothing for now
	}
	
}
