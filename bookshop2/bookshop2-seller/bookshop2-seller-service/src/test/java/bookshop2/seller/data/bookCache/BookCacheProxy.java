package bookshop2.seller.data.bookCache;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateful;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import bookshop2.Book;
import bookshop2.BookKey;

import common.jmx.client.JmxProxy;


@Stateful
public class BookCacheProxy extends JmxProxy {

	private static final Log log = LogFactory.getLog(BookCacheProxy.class);
	
	private String mbean = BookCacheManagerMBean.MBEAN_NAME;

	
	/*
	 * BookOrders related
	 */
	
	public List<Book> getAllBookOrders() {
		return getList(mbean, "getAllBookOrders");
	}

	public Book getFromBookOrders(Long bookId) {
		return getObject(mbean, "getFromBookOrders", new String[] { "java.lang.Long" }, new Object[] { bookId });
	}

	public Book getFromBookOrders(BookKey bookKey) {
		return getObject(mbean, "getFromBookOrders", new String[] { "bookshop2.BookKey" }, new Object[] { bookKey });
	}

	public void addToBookOrders(Book book) {
		call(mbean, "addToBookOrders", new String[] { "bookshop2.Book" }, new Object[] { book });
	}

	public void addToBookOrders(Collection<Book> bookCollection) {
		call(mbean, "addToBookOrders", new String[] { "java.util.Collection" }, new Object[] { bookCollection });
	}

	public void clearBookCache() {
		removeAllBookOrders();
	}
	
	public void removeAllBookOrders() {
		call(mbean, "removeAllBookOrders");
	}

	public void removeFromBookOrders(BookKey bookKey) {
		call(mbean, "removeFromBookOrders", new String[] { "bookshop2.BookKey" }, new Object[] { bookKey });
	}

	
	/*
	 * AvailableBooks related
	 */
	
	public Set<Book> getAllAvailableBooks() {
		return getObject(mbean, "getAllAvailableBooks");
	}

	public Book getFromAvailableBooks(Long bookId) {
		return getObject(mbean, "getFromAvailableBooks", new String[] { "java.lang.Long" }, new Object[] { bookId });
	}
	
	public void addToAvailableBooks(Book book) {
		call(mbean, "addToAvailableBooks", new String[] { "bookshop2.Book" }, new Object[] { book });
	}

	public void addToAvailableBooks(Collection<Book> bookCollection) {
		call(mbean, "addToAvailableBooks", new String[] { "java.util.Collection" }, new Object[] { bookCollection });
	}
	
	public void removeAllAvailableBooks() {
		call(mbean, "removeAllAvailableBooks");
	}

	
	/*
	 * UnavailableBooks related
	 */
	
	public Set<Book> getAllUnavailableBooks() {
		return getObject(mbean, "getAllUnavailableBooks");
	}

	public Book getFromUnavailableBooks(Long bookId) {
		return getObject(mbean, "getFromUnavailableBooks", new String[] { "java.lang.Long" }, new Object[] { bookId });
	}
	
	public void addToUnavailableBooks(Book book) {
		call(mbean, "addToUnavailableBooks", new String[] { "bookshop2.Book" }, new Object[] { book });
	}

	public void addToUnavailableBooks(Collection<Book> bookCollection) {
		call(mbean, "addToUnavailableBooks", new String[] { "java.util.Collection" }, new Object[] { bookCollection });
	}
	
	public void removeAllUnavailableBooks() {
		call(mbean, "removeAllUnavailableBooks");
	}

}
