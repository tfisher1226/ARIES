package bookshop2.supplier.data.supplierOrderCache;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.management.MXBean;

import bookshop2.Book;
import bookshop2.BookCriteria;
import bookshop2.BookKey;


@MXBean
public interface SupplierOrderCacheManagerMBean {
	
	public static final String MBEAN_NAME = "bookshop2.supplier.cache:name=SupplierOrderCacheManager";
	
	public List<Book> getAllBooksInStock();
	
	public Map<BookKey, Book> getAllBooksInStockAsMap();
	
	public Book getFromBooksInStock(Long bookId);
	
	public Book getFromBooksInStock(BookKey bookKey);
	
	public List<Book> getFromBooksInStock(Collection<Long> bookIds);
	
	public Map<BookKey, Book> getFromBooksInStockAsMap(Collection<BookKey> bookKeys);
	
	public List<Book> getFromBooksInStock(BookCriteria bookCriteria);
	
	public List<Book> getMatchingBooksInStock(Collection<Book> bookList);
	
	public Map<BookKey, Book> getMatchingBooksInStockAsMap(Collection<Book> bookList);
	
	public void setBooksInStock(Map<BookKey, Book> bookMap);
	
	public void addToBooksInStock(Book book);
	
	public void addToBooksInStock(Collection<Book> bookList);
	
	public void addToBooksInStock(Map<BookKey, Book> bookMap);
	
	public void removeAllBooksInStock();
	
	public void removeFromBooksInStock(Book book);
	
	public void removeFromBooksInStock(Long bookId);
	
	public void removeFromBooksInStock(BookKey bookKey);
	
	public void removeFromBooksInStock(Collection<Book> bookList);
	
	public void removeFromBooksInStock(BookCriteria bookCriteria);

}
