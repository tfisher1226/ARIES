package bookshop2.supplier.data.supplierOrderCache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;

import bookshop2.Book;
import bookshop2.BookCriteria;
import bookshop2.BookKey;
import bookshop2.util.Bookshop2Helper;

import common.tx.state.ServiceState;


public class SupplierOrderCacheState extends ServiceState {
	
	private static final long serialVersionUID = 1L;
	
	private static final Log log = LogFactory.getLog(SupplierOrderCacheState.class);
	
	public static final String LATEST_STATE_FILENAME = "SupplierOrder_CurrentState";
	
	public static final String SHADOW_STATE_FILENAME = "SupplierOrder_ShadowState";
	
	private Map<BookKey, Book> booksInStock;
	
	
	public SupplierOrderCacheState() {
		this.booksInStock = new LinkedHashMap<BookKey, Book>();
	}
	
	public SupplierOrderCacheState(SupplierOrderCacheState parent) {
		//super(parent);
		this.booksInStock = new LinkedHashMap<BookKey, Book>(parent.getAllBooksInStockAsMap());
	}
	
	
	public SupplierOrderCacheState createState() {
		return new SupplierOrderCacheState();
	}
	
	public SupplierOrderCacheState resetState() {
		SupplierOrderCacheState state = createState();
		return state;
	}
	
	@SuppressWarnings("unchecked")
	public SupplierOrderCacheState getDerivedState() {
		return new SupplierOrderCacheState(this);
	}
	
	public List<Book> getAllBooksInStock() {
		synchronized (booksInStock) {
			List<Book> bookList = new ArrayList<Book>(booksInStock.values());
			return bookList;
		}
	}
	
	public Map<BookKey, Book> getAllBooksInStockAsMap() {
		synchronized (booksInStock) {
			Map<BookKey, Book> bookMap = new HashMap<BookKey, Book>(booksInStock);
			return bookMap;
		}
	}
	
	public Book getFromBooksInStock(Long bookId) {
		synchronized (booksInStock) {
			BookCriteria bookCriteria = Bookshop2Helper.createBookCriteriaFromId(bookId);
			List<Book> bookList = getFromBooksInStock(bookCriteria);
			Assert.isTrue(bookList.size() <= 1, "Multiple records found for ID: "+bookId);
			if (bookList.size() == 1)
				return bookList.get(0);
			return null;
		}
	}
	
	public Book getFromBooksInStock(BookKey bookKey) {
		synchronized (booksInStock) {
			return booksInStock.get(bookKey);
		}
	}
	
	public List<Book> getFromBooksInStock(Collection<Long> bookIds) {
		synchronized (booksInStock) {
			List<Book> bookList = new ArrayList<Book>(bookIds.size());
			Iterator<Long> iterator = bookIds.iterator();
			while (iterator.hasNext()) {
				Long bookId = iterator.next();
				Book book = getFromBooksInStock(bookId);
				if (book != null) {
					bookList.add(book);
				}
			}
			return bookList;
		}
	}
	
	public Map<BookKey, Book> getFromBooksInStockAsMap(Collection<BookKey> bookKeys) {
		synchronized (booksInStock) {
			Map<BookKey, Book> bookMap = new HashMap<BookKey, Book>(bookKeys.size());
			Iterator<BookKey> iterator = bookKeys.iterator();
			while (iterator.hasNext()) {
				BookKey bookKey = iterator.next();
				Book book = booksInStock.get(bookKey);
				bookMap.put(bookKey, book);
			}
			return bookMap;
		}
	}
	
	public List<Book> getFromBooksInStock(BookCriteria bookCriteria) {
		synchronized (booksInStock) {
			List<Book> bookList = new ArrayList<Book>();
			Iterator<Book> iterator = booksInStock.values().iterator();
			while (iterator.hasNext()) {
				Book book = iterator.next();
				if (Bookshop2Helper.isMatch(bookCriteria, book)) {
					bookList.add(book);
				}
			}
			return bookList;
		}
	}
	
	public List<Book> getMatchingBooksInStock(Collection<Book> bookList) {
		synchronized (booksInStock) {
			List<Book> matchingBookList = new ArrayList<Book>(bookList.size());
			Iterator<Book> iterator = bookList.iterator();
			while (iterator.hasNext()) {
				Book book = iterator.next();
				BookKey bookKey = Bookshop2Helper.createBookKey(book);
				Book matchingBook = booksInStock.get(bookKey);
				if (matchingBook != null)
					matchingBookList.add(matchingBook);
			}
			return matchingBookList;
		}
	}
	
	public Map<BookKey, Book> getMatchingBooksInStockAsMap(Collection<Book> bookList) {
		synchronized (booksInStock) {
			Map<BookKey, Book> map = new HashMap<BookKey, Book>();
			Iterator<Book> iterator = bookList.iterator();
			while (iterator.hasNext()) {
				Book book = iterator.next();
				BookKey bookKey = Bookshop2Helper.createBookKey(book);
				Book matchingBook = booksInStock.get(bookKey);
				if (matchingBook != null)
					map.put(bookKey, matchingBook);
			}
			return map;
		}
	}
	
	public void setBooksInStock(Map<BookKey, Book> bookMap) {
		synchronized (booksInStock) {
			booksInStock = new LinkedHashMap<BookKey, Book>(bookMap);
		}
	}
	
	public void addToBooksInStock(Book book) {
		synchronized (booksInStock) {
			BookKey bookKey = Bookshop2Helper.createBookKey(book);
			booksInStock.put(bookKey, book);
		}
	}
	
	public void addToBooksInStock(Collection<Book> bookList) {
		Map<BookKey, Book> bookMap = Bookshop2Helper.createBookMap(bookList);
		addToBooksInStock(bookMap);
	}
	
	public void addToBooksInStock(Map<BookKey, Book> bookMap) {
		synchronized (booksInStock) {
			booksInStock.putAll(bookMap);
		}
	}
	
	public void removeAllBooksInStock() {
		synchronized (booksInStock) {
			booksInStock.clear();
		}
	}
	
	public void removeFromBooksInStock(Book book) {
		synchronized (booksInStock) {
			BookKey bookKey = Bookshop2Helper.createBookKey(book);
			booksInStock.remove(bookKey);
		}
	}
	
	public void removeFromBooksInStock(Long bookId) {
		synchronized (booksInStock) {
			BookCriteria bookCriteria = Bookshop2Helper.createBookCriteriaFromId(bookId);
			List<Book> bookList = getFromBooksInStock(bookCriteria);
			Assert.isTrue(bookList.size() <= 1, "Multiple records found for ID: "+bookId);
			if (bookList.size() == 1) {
				Book book = bookList.get(0);
				removeFromBooksInStock(book);
			}
		}
	}
	
	public void removeFromBooksInStock(BookKey bookKey) {
		synchronized (booksInStock) {
			booksInStock.remove(bookKey);
		}
	}
	
	public void removeFromBooksInStock(Collection<Book> bookList) {
		synchronized (booksInStock) {
			Collection<BookKey> bookKeys = Bookshop2Helper.createBookKeys(bookList);
			Iterator<BookKey> iterator = bookKeys.iterator();
			while (iterator.hasNext()) {
				BookKey bookKey = iterator.next();
				booksInStock.remove(bookKey);
			}
		}
	}
	
	public void removeFromBooksInStock(BookCriteria bookCriteria) {
		synchronized (booksInStock) {
			List<Book> bookList = getFromBooksInStock(bookCriteria);
			removeFromBooksInStock(bookList);
		}
	}
	
}
