package bookshop2.seller.data.bookCache;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import admin.User;

import bookshop2.Book;
import bookshop2.BookCriteria;

import common.tx.management.AbstractMonitor;


public class BookCacheMonitor extends AbstractMonitor {

	public BookCacheMonitor(String host, int port) {
		setHost(host);
		setPort(port);
		getURL();
	}


	public String getMBeanName() {
		return BookCacheManagerMBean.MBEAN_NAME;
	}

	public Class<?> getMBeanClass() {
		return BookCacheManagerMBean.class;
	}

	public User getUser() throws Exception {
		return getObject("getUser");
	}

	public void setUser(User user) throws Exception {
		invoke("setUser", "admin.User", user);
	}

	public List<Book> getAllBookOrders() throws Exception {
		return getList("getAllBookOrders");
	}
	
	public Book getFromBookOrders(Long bookId) throws Exception {
		return getObject("getFromBookOrders", "java.lang.Long", bookId);
	}
	
	public List<Book> getFromBookOrders(Collection<Long> bookIds) throws Exception {
		return getList("getFromBookOrders", "java.util.Collection", bookIds);
	}
	
	public List<Book> getFromBookOrders(BookCriteria bookCriteria) throws Exception {
		return getList("getFromBookOrders", "bookshop2.BookCriteria", bookCriteria);
	}
	
	public List<Book> getMatchingBookOrders(Collection<Book> bookList) throws Exception {
		return getList("getMatchingBookOrders", "java.util.Collection", bookList);
	}
	
	public void setBookOrders(Collection<Book> bookList) throws Exception {
		invoke("setBookOrders", "java.util.Collection", bookList);
	}
	
	public void addToBookOrders(Book book) throws Exception {
		invoke("addToBookOrders", "bookshop2.Book", book);
	}
	
	public void addToBookOrders(Collection<Book> bookList) throws Exception {
		invoke("addToBookOrders", "java.util.Collection", bookList);
	}
	
	public void removeAllBookOrders() throws Exception {
		invoke("removeAllBookOrders");
	}
	
	public void removeFromBookOrders(Book book) throws Exception {
		invoke("removeFromBookOrders", "bookshop2.Book", book);
	}
	
	public void removeFromBookOrders(Long bookId) throws Exception {
		invoke("removeFromBookOrders", "java.lang.Long", bookId);
	}
	
	public void removeFromBookOrders(Collection<Book> bookList) throws Exception {
		invoke("removeFromBookOrders", "java.util.Collection", bookList);
	}
	
	public void removeFromBookOrders(BookCriteria bookCriteria) throws Exception {
		invoke("removeFromBookOrders", "bookshop2.BookCriteria", bookCriteria);
	}
	
	public Set<Book> getAllAvailableBooks() throws Exception {
		return getSet("getAllAvailableBooks");
	}
	
	public Book getFromAvailableBooks(Long bookId) throws Exception {
		return getObject("getFromAvailableBooks", "java.lang.Long", bookId);
	}
	
	public Set<Book> getFromAvailableBooks(Collection<Long> bookIds) throws Exception {
		return getSet("getFromAvailableBooks", "java.util.Collection", bookIds);
	}

	public Set<Book> getFromAvailableBooks(BookCriteria bookCriteria) throws Exception {
		return getSet("getFromAvailableBooks", "bookshop2.BookCriteria", bookCriteria);
	}
	
	public Set<Book> getMatchingAvailableBooks(Collection<Book> bookSet) throws Exception {
		return getSet("getMatchingAvailableBooks", "java.util.Collection", bookSet);
	}
	
	public void setAvailableBooks(Collection<Book> bookSet) throws Exception {
		invoke("setAvailableBooks", "java.util.Collection", bookSet);
	}
	
	public void addToAvailableBooks(Book book) throws Exception {
		invoke("addToAvailableBooks", "bookshop2.Book", book);
	}
	
	public void addToAvailableBooks(Collection<Book> bookSet) throws Exception {
		invoke("addToAvailableBooks", "java.util.Collection", bookSet);
	}
	
	public void removeAllAvailableBooks() throws Exception {
		invoke("removeAllAvailableBooks");
	}
	
	public void removeFromAvailableBooks(Book book) throws Exception {
		invoke("removeFromAvailableBooks", "bookshop2.Book", book);
	}
	
	public void removeFromAvailableBooks(Long bookId) throws Exception {
		invoke("removeFromAvailableBooks", "java.lang.Long", bookId);
	}
	
	public void removeFromAvailableBooks(Collection<Book> bookSet) throws Exception {
		invoke("removeFromAvailableBooks", "java.util.Collection", bookSet);
	}
	
	public void removeFromAvailableBooks(BookCriteria bookCriteria) throws Exception {
		invoke("removeFromAvailableBooks", "bookshop2.BookCriteria", bookCriteria);
	}
	
	public Set<Book> getAllUnavailableBooks() throws Exception {
		return getSet("getAllUnavailableBooks");
	}

	public Book getFromUnavailableBooks(Long bookId) throws Exception {
		return getObject("getFromUnavailableBooks", "java.lang.Long", bookId);
	}
	
	public Set<Book> getFromUnavailableBooks(Collection<Long> bookIds) throws Exception {
		return getSet("getFromUnavailableBooks", "java.util.Collection", bookIds);
	}
	
	public Set<Book> getFromUnavailableBooks(BookCriteria bookCriteria) throws Exception {
		return getSet("getFromUnavailableBooks", "bookshop2.BookCriteria", bookCriteria);
	}
	
	public Set<Book> getMatchingUnavailableBooks(Collection<Book> bookSet) throws Exception {
		return getSet("getMatchingUnavailableBooks", "java.util.Collection", bookSet);
	}
	
	public void setUnavailableBooks(Collection<Book> bookSet) throws Exception {
		invoke("setUnavailableBooks", "java.util.Collection", bookSet);
	}
	
	public void addToUnavailableBooks(Book book) throws Exception {
		invoke("addToUnavailableBooks", "bookshop2.Book", book);
	}
	
	public void addToUnavailableBooks(Collection<Book> bookSet) throws Exception {
		invoke("addToUnavailableBooks", "java.util.Collection", bookSet);
	}
	
	public void removeAllUnavailableBooks() throws Exception {
		invoke("removeAllUnavailableBooks");
	}
	
	public void removeFromUnavailableBooks(Book book) throws Exception {
		invoke("removeFromUnavailableBooks", "bookshop2.Book", book);
	}
	
	public void removeFromUnavailableBooks(Long bookId) throws Exception {
		invoke("removeFromUnavailableBooks", "java.lang.Long", bookId);
	}
	
	public void removeFromUnavailableBooks(Collection<Book> bookSet) throws Exception {
		invoke("removeFromUnavailableBooks", "java.util.Collection", bookSet);
	}
	
	public void removeFromUnavailableBooks(BookCriteria bookCriteria) throws Exception {
		invoke("removeFromUnavailableBooks", "bookshop2.BookCriteria", bookCriteria);
	}
	
}
