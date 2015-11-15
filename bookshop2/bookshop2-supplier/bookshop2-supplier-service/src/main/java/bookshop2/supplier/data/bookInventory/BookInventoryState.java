package bookshop2.supplier.data.bookInventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import bookshop2.Book;
import bookshop2.BookCriteria;
import bookshop2.BookKey;
import bookshop2.util.Bookshop2Helper;

import common.tx.state.ServiceState;


public class BookInventoryState extends ServiceState {
	
	private static final long serialVersionUID = 1L;
	
	private static final Log log = LogFactory.getLog(BookInventoryState.class);
	
	public static final String LATEST_STATE_FILENAME = "BookInventory_CurrentState";
	
	public static final String SHADOW_STATE_FILENAME = "BookInventory_ShadowState";
	
	private Map<BookKey, Book> reservedBooks;

	
	public BookInventoryState() {
		this.reservedBooks = new LinkedHashMap<BookKey, Book>();
	}
	
	public BookInventoryState(BookInventoryState parent) {
		super(parent);
		this.reservedBooks = new LinkedHashMap<BookKey, Book>(parent.getAllReservedBooksAsMap());
	}
	
	
	public BookInventoryState createState() {
		return new BookInventoryState();
	}
	
	public BookInventoryState resetState() {
		BookInventoryState state = createState();
		return state;
	}
	
	@SuppressWarnings("unchecked")
	public BookInventoryState getDerivedState() {
		return new BookInventoryState(this);
	}
	
	public List<Book> getAllReservedBooks() {
		synchronized (reservedBooks) {
			List<Book> bookList = new ArrayList<Book>(reservedBooks.values());
			return bookList;
		}
	}
	
	public Map<BookKey, Book> getAllReservedBooksAsMap() {
		synchronized (reservedBooks) {
			return new HashMap<BookKey, Book>(reservedBooks);
		}
	}
	
	public Book getFromReservedBooks(Long bookId) {
		synchronized (reservedBooks) {
			Iterator<Book> iterator = reservedBooks.values().iterator();
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
	
	public Book getFromReservedBooks(BookKey bookKey) {
		synchronized (reservedBooks) {
			return reservedBooks.get(bookKey);
		}
	}
	
	public List<Book> getFromReservedBooks(Collection<Long> bookIds) {
		synchronized (reservedBooks) {
			List<Book> list = new ArrayList<Book>(bookIds.size());
			Iterator<Long> iterator = bookIds.iterator();
			while (iterator.hasNext()) {
				Long bookId = iterator.next();
				Book book = getFromReservedBooks(bookId);
				if (book != null) {
					list.add(book);
			}
			}
			return list;
		}
	}
	
	public Map<BookKey, Book> getFromReservedBooksAsMap(Collection<BookKey> bookKeys) {
		synchronized (reservedBooks) {
			Map<BookKey, Book> bookMap = new HashMap<BookKey, Book>(bookKeys.size());
			Iterator<BookKey> iterator = bookKeys.iterator();
			while (iterator.hasNext()) {
				BookKey bookKey = iterator.next();
				Book book = reservedBooks.get(bookKey);
				bookMap.put(bookKey, book);
			}
			return bookMap;
		}
	}
	
	public List<Book> getFromReservedBooks(BookCriteria bookCriteria) {
		synchronized (reservedBooks) {
			List<Book> list = new ArrayList<Book>();
			Iterator<Book> iterator = reservedBooks.values().iterator();
			while (iterator.hasNext()) {
				Book book = iterator.next();
				if (Bookshop2Helper.isMatch(bookCriteria, book)) {
					list.add(book);
				}
			}
			return list;
		}
	}
	
	public List<Book> getMatchingReservedBooks(Collection<Book> bookList) {
		synchronized (reservedBooks) {
			List<Book> matchingBookList = new ArrayList<Book>(bookList.size());
			Iterator<Book> iterator = bookList.iterator();
			while (iterator.hasNext()) {
				Book book = iterator.next();
				BookKey bookKey = Bookshop2Helper.createBookKey(book);
				Book matchingBook = reservedBooks.get(bookKey);
				if (matchingBook != null)
					matchingBookList.add(matchingBook);
			}
			return matchingBookList;
		}
	}
	
	public Map<BookKey, Book> getMatchingReservedBooksAsMap(Collection<Book> bookList) {
		synchronized (reservedBooks) {
			Map<BookKey, Book> matchingBookMap = new LinkedHashMap<BookKey, Book>();
			Iterator<Book> iterator = bookList.iterator();
			while (iterator.hasNext()) {
				Book book = iterator.next();
				BookKey bookKey = Bookshop2Helper.createBookKey(book);
				Book matchingBook = reservedBooks.get(bookKey);
				if (matchingBook != null)
					matchingBookMap.put(bookKey, matchingBook);
			}
			return matchingBookMap;
		}
	}
	
	public void addToReservedBooks(Book book) {
		synchronized (reservedBooks) {
			BookKey bookKey = Bookshop2Helper.createBookKey(book);
			reservedBooks.put(bookKey, book);
		}
	}
	
	public void addToReservedBooks(Collection<Book> bookList) {
		Map<BookKey, Book> bookMap = Bookshop2Helper.createBookMap(bookList);
		addToReservedBooks(bookMap);
	}
	
	public void addToReservedBooks(Map<BookKey, Book> bookMap) {
		synchronized (reservedBooks) {
			reservedBooks.putAll(bookMap);
		}
	}
	
	public void removeAllReservedBooks() {
		synchronized (reservedBooks) {
			reservedBooks.clear();
		}
	}
	
	public void removeFromReservedBooks(Book book) {
		synchronized (reservedBooks) {
			BookKey bookKey = Bookshop2Helper.createBookKey(book);
			reservedBooks.remove(bookKey);
		}
	}
	
	public void removeFromReservedBooks(Long bookId) {
		synchronized (reservedBooks) {
			Book book = getFromReservedBooks(bookId);
			if (book != null) {
				removeFromReservedBooks(book);
			}
		}
	}
	
	public void removeFromReservedBooks(BookKey bookKey) {
		synchronized (reservedBooks) {
			reservedBooks.remove(bookKey);
		}
	}
	
	public void removeFromReservedBooks(Collection<Book> bookList) {
		synchronized (reservedBooks) {
			Iterator<Book> iterator = bookList.iterator();
			while (iterator.hasNext()) {
				Book book = iterator.next();
				removeFromReservedBooks(book);
			}
		}
	}
	
	public void removeFromReservedBooks(BookCriteria bookCriteria) {
		synchronized (reservedBooks) {
			List<Book> bookList = getFromReservedBooks(bookCriteria);
			removeFromReservedBooks(bookList);
		}
	}
	
}
