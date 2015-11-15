package bookshop2.seller.data.bookCache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;

import admin.User;

import bookshop2.Book;
import bookshop2.BookCriteria;
import bookshop2.util.Bookshop2Helper;

import common.tx.state.ServiceState;


public class BookCacheState extends ServiceState {

	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(BookCacheState.class);

	public static final String LATEST_STATE_FILENAME = "Book_CurrentState";

	public static final String SHADOW_STATE_FILENAME = "Book_ShadowState";

	private User user;

	private List<Book> bookOrders;

	private Set<Book> availableBooks;

	private Set<Book> unavailableBooks;


	public BookCacheState() {
		this.user = new User();
		this.bookOrders = new ArrayList<Book>();
		this.availableBooks = new HashSet<Book>();
		this.unavailableBooks = new HashSet<Book>();
	}

	public BookCacheState(BookCacheState parent) {
		//super(parent);
		this.user = parent.getUser();
		this.bookOrders = new ArrayList<Book>(parent.getAllBookOrders());
		this.availableBooks = new HashSet<Book>(parent.getAllAvailableBooks());
		this.unavailableBooks = new HashSet<Book>(parent.getAllUnavailableBooks());
	}


	public BookCacheState createState() {
		return new BookCacheState();
	}

	public BookCacheState resetState() {
		BookCacheState state = createState();
		return state;
	}

	@SuppressWarnings("unchecked")
	public BookCacheState getDerivedState() {
		return new BookCacheState(this);
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Book> getAllBookOrders() {
		synchronized (bookOrders) {
			return bookOrders;
		}
	}
	
	public Book getFromBookOrders(Long bookId) {
		synchronized (bookOrders) {
			BookCriteria bookCriteria = Bookshop2Helper.createBookCriteriaFromId(bookId);
			List<Book> bookList = getFromBookOrders(bookCriteria);
			Assert.isTrue(bookList.size() <= 1, "Multiple records found for ID: "+bookId);
			if (bookList.size() == 1)
				return bookList.get(0);
			return null;
		}
	}
	
	public List<Book> getFromBookOrders(Collection<Long> bookIds) {
		synchronized (bookOrders) {
			List<Book> bookList = new ArrayList<Book>(bookIds.size());
			Iterator<Long> iterator = bookIds.iterator();
			while (iterator.hasNext()) {
				Long bookId = iterator.next();
				Book book = getFromBookOrders(bookId);
				if (book != null) {
					bookList.add(book);
				}
			}
			return bookList;
		}
	}
	
	public List<Book> getFromBookOrders(BookCriteria bookCriteria) {
		synchronized (bookOrders) {
			List<Book> bookList = new ArrayList<Book>();
			Iterator<Book> iterator = bookOrders.iterator();
			while (iterator.hasNext()) {
				Book book = iterator.next();
				if (Bookshop2Helper.isMatch(bookCriteria, book)) {
					bookList.add(book);
				}
			}
			return bookList;
		}
	}

	public List<Book> getMatchingBookOrders(Collection<Book> bookList) {
		synchronized (bookOrders) {
			List<Book> matchingBookList = new ArrayList<Book>(bookList.size());
			Iterator<Book> iterator = bookList.iterator();
			while (iterator.hasNext()) {
				Book book = iterator.next();
				if (bookOrders.contains(book)) {
					matchingBookList.add(book);
				}
			}
			return matchingBookList;
		}
	}
	
	public void setBookOrders(Collection<Book> bookList) {
		synchronized (bookOrders) {
			bookOrders = new ArrayList<Book>(bookList);
		}
	}
	
	public void addToBookOrders(Book book) {
		synchronized (bookOrders) {
			bookOrders.add(book);
		}
	}
	
	public void addToBookOrders(Collection<Book> bookList) {
		synchronized (bookOrders) {
			bookOrders.addAll(bookList);
		}
	}

	public void removeAllBookOrders() {
		synchronized (bookOrders) {
			bookOrders.clear();
		}
	}

	public void removeFromBookOrders(Book book) {
		synchronized (bookOrders) {
			bookOrders.remove(book);
		}
	}

	public void removeFromBookOrders(Long bookId) {
		synchronized (bookOrders) {
			BookCriteria bookCriteria = Bookshop2Helper.createBookCriteriaFromId(bookId);
			List<Book> bookList = getFromBookOrders(bookCriteria);
			Assert.isTrue(bookList.size() <= 1, "Multiple records found for ID: "+bookId);
			if (bookList.size() == 1) {
				Book book = bookList.get(0);
				removeFromBookOrders(book);
			}
		}
	}
	
	public void removeFromBookOrders(Collection<Book> bookList) {
		synchronized (bookOrders) {
			bookOrders.clear();
		}
	}
	
	public void removeFromBookOrders(BookCriteria bookCriteria) {
		synchronized (bookOrders) {
			List<Book> bookList = getFromBookOrders(bookCriteria);
			removeFromBookOrders(bookList);
		}
	}

	public Set<Book> getAllAvailableBooks() {
		synchronized (availableBooks) {
			return availableBooks;
		}
	}
	
	public Book getFromAvailableBooks(Long bookId) {
		synchronized (availableBooks) {
			BookCriteria bookCriteria = Bookshop2Helper.createBookCriteriaFromId(bookId);
			Set<Book> bookSet = getFromAvailableBooks(bookCriteria);
			Assert.isTrue(bookSet.size() <= 1, "Multiple records found for ID: "+bookId);
			if (bookSet.size() == 1)
				return bookSet.iterator().next();
			return null;
		}
	}
	
	public Set<Book> getFromAvailableBooks(Collection<Long> bookIds) {
		synchronized (availableBooks) {
			Set<Book> bookSet = new HashSet<Book>(bookIds.size());
			Iterator<Long> iterator = bookIds.iterator();
			while (iterator.hasNext()) {
				Long bookId = iterator.next();
				Book book = getFromAvailableBooks(bookId);
				if (book != null) {
					bookSet.add(book);
				}
			}
			return bookSet;
		}
	}
	
	public Set<Book> getFromAvailableBooks(BookCriteria bookCriteria) {
		synchronized (availableBooks) {
			Set<Book> bookSet = new HashSet<Book>();
			Iterator<Book> iterator = availableBooks.iterator();
			while (iterator.hasNext()) {
				Book book = iterator.next();
				if (Bookshop2Helper.isMatch(bookCriteria, book)) {
					bookSet.add(book);
				}
			}
			return bookSet;
		}
	}

	public Set<Book> getMatchingAvailableBooks(Collection<Book> bookSet) {
		synchronized (availableBooks) {
			Set<Book> matchingBookSet = new HashSet<Book>(bookSet.size());
			Iterator<Book> iterator = bookSet.iterator();
			while (iterator.hasNext()) {
				Book book = iterator.next();
				if (availableBooks.contains(book)) {
					matchingBookSet.add(book);
				}
			}
			return matchingBookSet;
		}
	}
	
	public void setAvailableBooks(Collection<Book> bookSet) {
		synchronized (availableBooks) {
			availableBooks = new HashSet<Book>(bookSet);
		}
	}
	
	public void addToAvailableBooks(Book book) {
		synchronized (availableBooks) {
			availableBooks.add(book);
		}
	}

	public void addToAvailableBooks(Collection<Book> bookSet) {
		synchronized (availableBooks) {
			availableBooks.addAll(bookSet);
		}
	}

	public void removeAllAvailableBooks() {
		synchronized (availableBooks) {
			availableBooks.clear();
		}
	}
	
	public void removeFromAvailableBooks(Book book) {
		synchronized (availableBooks) {
			availableBooks.remove(book);
		}
	}

	public void removeFromAvailableBooks(Long bookId) {
		synchronized (availableBooks) {
			BookCriteria bookCriteria = Bookshop2Helper.createBookCriteriaFromId(bookId);
			Set<Book> bookSet = getFromAvailableBooks(bookCriteria);
			Assert.isTrue(bookSet.size() <= 1, "Multiple records found for ID: "+bookId);
			if (bookSet.size() == 1) {
				Book book = bookSet.iterator().next();
				removeFromAvailableBooks(book);
			}
		}
	}
	
	public void removeFromAvailableBooks(Collection<Book> bookSet) {
		synchronized (availableBooks) {
			availableBooks.clear();
		}
	}

	public void removeFromAvailableBooks(BookCriteria bookCriteria) {
		synchronized (availableBooks) {
			Set<Book> bookSet = getFromAvailableBooks(bookCriteria);
			removeFromAvailableBooks(bookSet);
		}
	}
	
	public Set<Book> getAllUnavailableBooks() {
		synchronized (unavailableBooks) {
			return unavailableBooks;
		}
	}
	
	public Book getFromUnavailableBooks(Long bookId) {
		synchronized (unavailableBooks) {
			BookCriteria bookCriteria = Bookshop2Helper.createBookCriteriaFromId(bookId);
			Set<Book> bookSet = getFromUnavailableBooks(bookCriteria);
			Assert.isTrue(bookSet.size() <= 1, "Multiple records found for ID: "+bookId);
			if (bookSet.size() == 1)
				return bookSet.iterator().next();
			return null;
		}
	}
	
	public Set<Book> getFromUnavailableBooks(Collection<Long> bookIds) {
		synchronized (unavailableBooks) {
			Set<Book> bookSet = new HashSet<Book>(bookIds.size());
			Iterator<Long> iterator = bookIds.iterator();
			while (iterator.hasNext()) {
				Long bookId = iterator.next();
				Book book = getFromUnavailableBooks(bookId);
				if (book != null) {
					bookSet.add(book);
				}
			}
			return bookSet;
		}
	}
	
	public Set<Book> getFromUnavailableBooks(BookCriteria bookCriteria) {
		synchronized (unavailableBooks) {
			Set<Book> bookSet = new HashSet<Book>();
			Iterator<Book> iterator = unavailableBooks.iterator();
			while (iterator.hasNext()) {
				Book book = iterator.next();
				if (Bookshop2Helper.isMatch(bookCriteria, book)) {
					bookSet.add(book);
				}
			}
			return bookSet;
		}
	}

	public Set<Book> getMatchingUnavailableBooks(Collection<Book> bookSet) {
		synchronized (unavailableBooks) {
			Set<Book> matchingBookSet = new HashSet<Book>(bookSet.size());
			Iterator<Book> iterator = bookSet.iterator();
			while (iterator.hasNext()) {
				Book book = iterator.next();
				if (unavailableBooks.contains(book)) {
					matchingBookSet.add(book);
				}
			}
			return matchingBookSet;
		}
	}
	
	public void setUnavailableBooks(Collection<Book> bookSet) {
		synchronized (unavailableBooks) {
			unavailableBooks = new HashSet<Book>(bookSet);
		}
	}
	
	public void addToUnavailableBooks(Book book) {
		synchronized (unavailableBooks) {
			unavailableBooks.add(book);
		}
	}

	public void addToUnavailableBooks(Collection<Book> bookSet) {
		synchronized (unavailableBooks) {
			unavailableBooks.addAll(bookSet);
		}
	}

	public void removeAllUnavailableBooks() {
		synchronized (unavailableBooks) {
			unavailableBooks.clear();
		}
	}

	public void removeFromUnavailableBooks(Book book) {
		synchronized (unavailableBooks) {
			unavailableBooks.remove(book);
		}
	}
	
	public void removeFromUnavailableBooks(Long bookId) {
		synchronized (unavailableBooks) {
			BookCriteria bookCriteria = Bookshop2Helper.createBookCriteriaFromId(bookId);
			Set<Book> bookSet = getFromUnavailableBooks(bookCriteria);
			Assert.isTrue(bookSet.size() <= 1, "Multiple records found for ID: "+bookId);
			if (bookSet.size() == 1) {
				Book book = bookSet.iterator().next();						
				removeFromUnavailableBooks(book);
			}
		}
	}
	
	public void removeFromUnavailableBooks(Collection<Book> bookSet) {
		synchronized (unavailableBooks) {
			unavailableBooks.clear();
		}
	}
	
	public void removeFromUnavailableBooks(BookCriteria bookCriteria) {
		synchronized (unavailableBooks) {
			Set<Book> bookSet = getFromUnavailableBooks(bookCriteria);
			removeFromUnavailableBooks(bookSet);
		}
	}

}
