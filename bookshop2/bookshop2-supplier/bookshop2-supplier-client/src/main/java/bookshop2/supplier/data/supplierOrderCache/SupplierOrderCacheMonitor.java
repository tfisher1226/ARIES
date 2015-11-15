package bookshop2.supplier.data.supplierOrderCache;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import bookshop2.Book;
import bookshop2.BookCriteria;
import bookshop2.BookKey;

import common.tx.management.AbstractMonitor;


public class SupplierOrderCacheMonitor extends AbstractMonitor {
	
	public SupplierOrderCacheMonitor(String host, int port) {
		setHost(host);
		setPort(port);
		getURL();
	}
	
	
	public String getMBeanName() {
		return SupplierOrderCacheManagerMBean.MBEAN_NAME;
	}
	
	public Class<?> getMBeanClass() {
		return SupplierOrderCacheManagerMBean.class;
	}
	
	public List<Book> getAllBooksInStock() throws Exception {
		return getList("getAllBooksInStock");
	}
	
	public Map<BookKey, Book> getAllBooksInStockAsMap() throws Exception {
		return getMap("getAllBooksInStockAsMap");
	}
	
	public Book getFromBooksInStock(Long bookId) throws Exception {
		return getObject("getFromBooksInStock", "java.lang.Long", bookId);
	}
	
	public Book getFromBooksInStock(BookKey bookKey) throws Exception {
		return getObject("getFromBooksInStock", "bookshop2.BookKey", bookKey);
	}
	
	public List<Book> getFromBooksInStock(Collection<Long> bookIds) throws Exception {
		return getList("getFromBooksInStock", "java.util.Collection", bookIds);
	}
	
	public Map<BookKey, Book> getFromBooksInStockAsMap(Collection<BookKey> bookKeys) throws Exception {
		return getMap("getFromBooksInStockAsMap", "java.util.Collection", bookKeys);
	}
	
	public List<Book> getFromBooksInStock(BookCriteria bookCriteria) throws Exception {
		return getList("getFromBooksInStock", "bookshop2.BookCriteria", bookCriteria);
	}
	
	public List<Book> getMatchingBooksInStock(Collection<Book> bookList) throws Exception {
		return getList("getMatchingBooksInStock", "java.util.Collection", bookList);
	}
	
	public Map<BookKey, Book> getMatchingBooksInStockAsMap(Collection<Book> bookList) throws Exception {
		return getMap("getMatchingBooksInStockAsMap", "java.util.Collection", bookList);
	}
	
	public void setBooksInStock(Map<BookKey, Book> bookMap) throws Exception {
		invoke("setBooksInStock", "java.util.Map", bookMap);
	}
	
	public void addToBooksInStock(Book book) throws Exception {
		invoke("addToBooksInStock", "bookshop2.Book", book);
	}
	
	public void addToBooksInStock(Collection<Book> bookList) throws Exception {
		invoke("addToBooksInStock", "java.util.Collection", bookList);
	}
	
	public void addToBooksInStock(Map<BookKey, Book> bookMap) throws Exception {
		invoke("addToBooksInStock", "java.util.Map", bookMap);
	}
	
	public void removeAllBooksInStock() throws Exception {
		invoke("removeAllBooksInStock");
	}
	
	public void removeFromBooksInStock(Book book) throws Exception {
		invoke("removeFromBooksInStock", "bookshop2.Book", book);
	}
	
	public void removeFromBooksInStock(Long bookId) throws Exception {
		invoke("removeFromBooksInStock", "java.lang.Long", bookId);
	}
	
	public void removeFromBooksInStock(BookKey bookKey) throws Exception {
		invoke("removeFromBooksInStock", "bookshop2.BookKey", bookKey);
	}
	
	public void removeFromBooksInStock(Collection<Book> bookList) throws Exception {
		invoke("removeFromBooksInStock", "java.util.Collection", bookList);
	}
	
	public void removeFromBooksInStock(BookCriteria bookCriteria) throws Exception {
		invoke("removeFromBooksInStock", "bookshop2.BookCriteria", bookCriteria);
	}
	
}
