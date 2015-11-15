package bookshop2.seller.data.bookCache;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.management.MXBean;

import admin.User;

import bookshop2.Book;
import bookshop2.BookCriteria;


@MXBean
public interface BookCacheManagerMBean {

	public static final String MBEAN_NAME = "bookshop2.seller.cache:name=BookCacheManager";
	
	public User getUser();

	public void setUser(User user);

	public List<Book> getAllBookOrders();

	public Book getFromBookOrders(Long bookId);
	
	public List<Book> getFromBookOrders(Collection<Long> bookIds);
	
	public List<Book> getFromBookOrders(BookCriteria bookCriteria);
	
	public List<Book> getMatchingBookOrders(Collection<Book> bookList);
	
	public void setBookOrders(Collection<Book> bookList);
	
	public void addToBookOrders(Book book);
	
	public void addToBookOrders(Collection<Book> bookList);
	
	public void removeAllBookOrders();
	
	public void removeFromBookOrders(Book book);
	
	public void removeFromBookOrders(Long bookId);
	
	public void removeFromBookOrders(Collection<Book> bookList);
	
	public void removeFromBookOrders(BookCriteria bookCriteria);
	
	public Set<Book> getAllAvailableBooks();
	
	public Book getFromAvailableBooks(Long bookId);
	
	public Set<Book> getFromAvailableBooks(Collection<Long> bookIds);
	
	public Set<Book> getFromAvailableBooks(BookCriteria bookCriteria);
	
	public Set<Book> getMatchingAvailableBooks(Collection<Book> bookSet);
	
	public void setAvailableBooks(Collection<Book> bookSet);
	
	public void addToAvailableBooks(Book book);
	
	public void addToAvailableBooks(Collection<Book> bookSet);
	
	public void removeAllAvailableBooks();
	
	public void removeFromAvailableBooks(Book book);
	
	public void removeFromAvailableBooks(Long bookId);
	
	public void removeFromAvailableBooks(Collection<Book> bookSet);
	
	public void removeFromAvailableBooks(BookCriteria bookCriteria);
	
	public Set<Book> getAllUnavailableBooks();
	
	public Book getFromUnavailableBooks(Long bookId);
	
	public Set<Book> getFromUnavailableBooks(Collection<Long> bookIds);
	
	public Set<Book> getFromUnavailableBooks(BookCriteria bookCriteria);
	
	public Set<Book> getMatchingUnavailableBooks(Collection<Book> bookSet);
	
	public void setUnavailableBooks(Collection<Book> bookSet);

	public void addToUnavailableBooks(Book book);
	
	public void addToUnavailableBooks(Collection<Book> bookSet);
	
	public void removeAllUnavailableBooks();
	
	public void removeFromUnavailableBooks(Book book);
	
	public void removeFromUnavailableBooks(Long bookId);
	
	public void removeFromUnavailableBooks(Collection<Book> bookSet);
	
	public void removeFromUnavailableBooks(BookCriteria bookCriteria);

}
