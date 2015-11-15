package bookshop2.seller.data.bookCache;

import static javax.ejb.ConcurrencyManagementType.BEAN;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import admin.User;

import bookshop2.Book;
import bookshop2.BookCriteria;
import bookshop2.util.Bookshop2Helper;

import common.tx.state.ServiceStateProcessor;


@Startup
@Singleton
@LocalBean
@ConcurrencyManagement(BEAN)
public class BookCacheProcessor implements ServiceStateProcessor<BookCacheState> {

	private static final Log log = LogFactory.getLog(BookCacheProcessor.class);

	private User pendingUser;

	private List<Book> pendingBookOrders = new ArrayList<Book>();

	private Set<Book> pendingAvailableBooks = new HashSet<Book>();

	private Set<Book> pendingUnavailableBooks = new HashSet<Book>();


	public BookCacheProcessor() {
		// nothing for now
	}

	
	public void resetState(BookCacheState state) {
		state.setUser(null);
		state.removeAllBookOrders();
		state.removeAllAvailableBooks();
		state.removeAllUnavailableBooks();
	}

	public boolean validateState(BookCacheState state) {
		//return pendingUser != null && pendingBookOrders != null && pendingAvailableBooks != null && pendingUnavailableBooks != null;
		return true;
	}

	public void updateState(BookCacheState state) {
		state.setUser(pendingUser);
		state.setBookOrders(pendingBookOrders);
		state.setAvailableBooks(pendingAvailableBooks);
		state.setUnavailableBooks(pendingUnavailableBooks);
	}

	public void processRequest() {
		//nothing for now
	}

	public User getPendingUser() {
		synchronized (pendingUser) {
			return pendingUser;
		}
	}

	public void setPendingUser(User user) {
		synchronized (pendingUser) {
			pendingUser = user;
		}
	}
	
	public List<Book> getAllPendingBookOrders() {
		synchronized (pendingBookOrders) {
			List<Book> bookList = new ArrayList<Book>(pendingBookOrders);
			return bookList;
		}
	}
	
	public Book getFromPendingBookOrders(Long bookId) {
		synchronized (pendingBookOrders) {
			Iterator<Book> iterator = pendingBookOrders.iterator();
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
	
	public List<Book> getFromPendingBookOrders(Collection<Long> bookIds) {
		return null;
	}
	
	public List<Book> getFromPendingBookOrders(BookCriteria bookCriteria) {
		synchronized (pendingBookOrders) {
			List<Book> bookList = new ArrayList<Book>();
			Iterator<Book> iterator = pendingBookOrders.iterator();
			while (iterator.hasNext()) {
				Book book = iterator.next();
				if (Bookshop2Helper.isMatch(bookCriteria, book)) {
					bookList.add(book);
				}
			}
			return bookList;
		}
	}
	
	public void setPendingBookOrders(Collection<Book> bookList) {
		synchronized (pendingBookOrders) {
			pendingBookOrders = new ArrayList<Book>(bookList);
		}
	}
	
	public void addToPendingBookOrders(Book book) {
		synchronized (pendingBookOrders) {
			pendingBookOrders.add(book);
		}
	}
	
	public void addToPendingBookOrders(Collection<Book> bookList) {
		synchronized (pendingBookOrders) {
			pendingBookOrders.addAll(bookList);
		}
	}
	
	public void removeAllPendingBookOrders() {
		synchronized (pendingBookOrders) {
			pendingBookOrders.clear();
		}
	}
	
	public void removeFromPendingBookOrders(Book book) {
		synchronized (pendingBookOrders) {
			pendingBookOrders.remove(book);
		}
		}
	
	public void removeFromPendingBookOrders(Long bookId) {
		//nothing for now
	}
	
	public void removeFromPendingBookOrders(Collection<Book> bookList) {
		synchronized (pendingBookOrders) {
			Iterator<Book> iterator = bookList.iterator();
			while (iterator.hasNext()) {
				Book book = iterator.next();
				pendingBookOrders.remove(book);
			}
		}
	}
	
	public void removeFromPendingBookOrders(BookCriteria bookCriteria) {
		//nothing for now
	}
	
	public Set<Book> getAllPendingAvailableBooks() {
		synchronized (pendingAvailableBooks) {
			Set<Book> bookSet = new HashSet<Book>(pendingAvailableBooks);
			return bookSet;
		}
	}
	
	public Book getFromPendingAvailableBooks(Long bookId) {
		synchronized (pendingAvailableBooks) {
			Iterator<Book> iterator = pendingAvailableBooks.iterator();
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
	
	public Set<Book> getFromPendingAvailableBooks(Collection<Long> bookIds) {
		return null;
	}
	
	public Set<Book> getFromPendingAvailableBooks(BookCriteria bookCriteria) {
		synchronized (pendingAvailableBooks) {
			Set<Book> bookSet = new HashSet<Book>();
			Iterator<Book> iterator = pendingAvailableBooks.iterator();
			while (iterator.hasNext()) {
				Book book = iterator.next();
				if (Bookshop2Helper.isMatch(bookCriteria, book)) {
					bookSet.add(book);
				}
			}
			return bookSet;
		}
	}
	
	public void setPendingAvailableBooks(Collection<Book> bookSet) {
		synchronized (pendingAvailableBooks) {
			pendingAvailableBooks = new HashSet<Book>(bookSet);
		}
	}
	
	public void addToPendingAvailableBooks(Book book) {
		synchronized (pendingAvailableBooks) {
			pendingAvailableBooks.add(book);
		}
	}
	
	public void addToPendingAvailableBooks(Collection<Book> bookSet) {
		synchronized (pendingAvailableBooks) {
			pendingAvailableBooks.addAll(bookSet);
		}
	}
	
	public void removeAllPendingAvailableBooks() {
		synchronized (pendingAvailableBooks) {
			pendingAvailableBooks.clear();
		}
	}
	
	public void removeFromPendingAvailableBooks(Book book) {
		synchronized (pendingAvailableBooks) {
			pendingAvailableBooks.remove(book);
		}
		}
	
	public void removeFromPendingAvailableBooks(Long bookId) {
		//nothing for now
	}
	
	public void removeFromPendingAvailableBooks(Collection<Book> bookSet) {
		synchronized (pendingAvailableBooks) {
			Iterator<Book> iterator = bookSet.iterator();
			while (iterator.hasNext()) {
				Book book = iterator.next();
				pendingAvailableBooks.remove(book);
			}
		}
	}
	
	public void removeFromPendingAvailableBooks(BookCriteria bookCriteria) {
		//nothing for now
	}
	
	public Set<Book> getAllPendingUnavailableBooks() {
		synchronized (pendingUnavailableBooks) {
			Set<Book> bookSet = new HashSet<Book>(pendingUnavailableBooks);
			return bookSet;
		}
	}
	
	public Book getFromPendingUnavailableBooks(Long bookId) {
		synchronized (pendingUnavailableBooks) {
			Iterator<Book> iterator = pendingUnavailableBooks.iterator();
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
	
	public Set<Book> getFromPendingUnavailableBooks(Collection<Long> bookIds) {
		return null;
	}
	
	public Set<Book> getFromPendingUnavailableBooks(BookCriteria bookCriteria) {
		synchronized (pendingUnavailableBooks) {
			Set<Book> bookSet = new HashSet<Book>();
			Iterator<Book> iterator = pendingUnavailableBooks.iterator();
			while (iterator.hasNext()) {
				Book book = iterator.next();
				if (Bookshop2Helper.isMatch(bookCriteria, book)) {
					bookSet.add(book);
				}
			}
			return bookSet;
		}
	}
	
	public void setPendingUnavailableBooks(Collection<Book> bookSet) {
		synchronized (pendingUnavailableBooks) {
			pendingUnavailableBooks = new HashSet<Book>(bookSet);
		}
	}
	
	public void addToPendingUnavailableBooks(Book book) {
		synchronized (pendingUnavailableBooks) {
			pendingUnavailableBooks.add(book);
		}
	}
	
	public void addToPendingUnavailableBooks(Collection<Book> bookSet) {
		synchronized (pendingUnavailableBooks) {
			pendingUnavailableBooks.addAll(bookSet);
		}
	}
	
	public void removeAllPendingUnavailableBooks() {
		synchronized (pendingUnavailableBooks) {
			pendingUnavailableBooks.clear();
		}
	}
	
	public void removeFromPendingUnavailableBooks(Book book) {
		synchronized (pendingUnavailableBooks) {
			pendingUnavailableBooks.remove(book);
		}
		}
	
	public void removeFromPendingUnavailableBooks(Long bookId) {
		//nothing for now
	}
	
	public void removeFromPendingUnavailableBooks(Collection<Book> bookSet) {
		synchronized (pendingUnavailableBooks) {
			Iterator<Book> iterator = bookSet.iterator();
			while (iterator.hasNext()) {
				Book book = iterator.next();
				pendingUnavailableBooks.remove(book);
			}
		}
	}
	
	public void removeFromPendingUnavailableBooks(BookCriteria bookCriteria) {
		//nothing for now
	}
	
}
