package bookshop2.supplier.data.bookInventory;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import bookshop2.Book;
import bookshop2.BookCriteria;
import bookshop2.BookKey;
import bookshop2.util.Bookshop2Helper;

import common.tx.management.AbstractMonitor;


public class BookInventoryMonitor extends AbstractMonitor {
	
	public BookInventoryMonitor(String host, int port) {
		setHost(host);
		setPort(port);
		getURL();
	}
	
	
	public String getMBeanName() {
		return BookInventoryManagerMBean.MBEAN_NAME;
	}
	
	public Class<?> getMBeanClass() {
		return BookInventoryManagerMBean.class;
	}
	
	public List<Book> getAllReservedBooks() throws Exception {
		return getList("getAllReservedBooks");
	}
	
	public Map<BookKey, Book> getAllReservedBooksAsMap() throws Exception {
		return getMap("getAllReservedBooksAsMap");
	}
	
	public Book getFromReservedBooks(Long bookId) throws Exception {
		return getObject("getFromReservedBooks", "java.lang.Long", bookId);
	}
	
	public Book getFromReservedBooks(BookKey bookKey) throws Exception {
		return getObject("getFromReservedBooksByKeys", "java.lang.String", bookKey);
	}
	
	public List<Book> getFromReservedBooks(Collection<Long> bookIds) throws Exception {
		return getList("getFromReservedBooks", "java.util.Collection", bookIds);
	}
	
	public Map<BookKey, Book> getFromReservedBooksAsMap(Collection<BookKey> bookKeys) throws Exception {
		return getMap("getFromReservedBooksByKeys", "java.util.List", bookKeys);
	}
	
	public List<Book> getFromReservedBooks(BookCriteria bookCriteria) throws Exception {
		return getList("getFromReservedBooks", "bookshop2.BookCriteria", bookCriteria);
	}
	
	public List<Book> getMatchingReservedBooks(Collection<Book> bookList) throws Exception {
		return getList("getFromReservedBooksByKeys", "java.util.Collection", bookList);
	}
	
	public Map<BookKey, Book> getMatchingReservedBooksAsMap(Collection<Book> bookList) throws Exception {
		Collection<BookKey> bookKeys = Bookshop2Helper.createBookKeys(bookList);
		return getMap("getFromReservedBooksByKeys", "java.util.Collection", bookKeys);
	}
	
	public Long addToReservedBooks(Book book) throws Exception {
		return getObject("addToReservedBooks", "bookshop2.Book", book);
	}
	
	public List<Long> addToReservedBooks(Collection<Book> bookList) throws Exception {
		return getList("addToReservedBooks", "java.util.Collection", bookList);
	}
	
	public List<Long> addToReservedBooks(Map<BookKey, Book> bookMap) throws Exception {
		return getList("addToReservedBooks", "java.util.Map", bookMap);
	}
	
	public void removeAllReservedBooks() throws Exception {
		invoke("removeAllReservedBooks");
	}
	
	public void removeFromReservedBooks(Book book) throws Exception {
		invoke("removeFromReservedBooks", "bookshop2.Book", book);
	}
	
	public void removeFromReservedBooks(Long bookId) throws Exception {
		invoke("removeFromReservedBooks", "java.lang.Long", bookId);
	}
	
	public void removeFromReservedBooks(BookKey bookKey) throws Exception {
		invoke("removeFromReservedBooks", "java.lang.String", bookKey);
	}
	
	public void removeFromReservedBooks(Collection<Book> bookList) throws Exception {
		invoke("removeFromReservedBooks", "java.util.Collection", bookList);
	}
	
	public void removeFromReservedBooks(BookCriteria bookCriteria) throws Exception {
		invoke("removeFromReservedBooks", "bookshop2.BookCriteria", bookCriteria);
	}
	
}
