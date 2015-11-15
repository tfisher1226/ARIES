package bookshop2.supplier.data.bookInventory;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.management.MXBean;

import bookshop2.Book;
import bookshop2.BookCriteria;
import bookshop2.BookKey;


@MXBean
public interface BookInventoryManagerMBean {
	
	public static final String MBEAN_NAME = "bookshop2.supplier.data:name=BookInventoryManager";
	
	public void clearContext();
	
	public void updateState();
	
	public void commitState();
	
	public List<Book> getAllReservedBooks();
	
	public Map<BookKey, Book> getAllReservedBooksAsMap();
	
	public Book getFromReservedBooks(Long bookId);
	
	public Book getFromReservedBooks(BookKey bookKey);
	
	public List<Book> getFromReservedBooks(Collection<Long> bookIds);
	
	public Map<BookKey, Book> getFromReservedBooksAsMap(Collection<BookKey> bookKeys);
	
	public List<Book> getFromReservedBooks(BookCriteria bookCriteria);
	
	public List<Book> getMatchingReservedBooks(Collection<Book> bookList);
	
	public Map<BookKey, Book> getMatchingReservedBooksAsMap(Collection<Book> bookList);
	
	public Long addToReservedBooks(Book book);
	
	public List<Long> addToReservedBooks(Collection<Book> bookList);
	
	public List<Long> addToReservedBooks(Map<BookKey, Book> bookMap);
	
	public void removeAllReservedBooks();
	
	public void removeFromReservedBooks(Book book);
	
	public void removeFromReservedBooks(Long bookId);
	
	public void removeFromReservedBooks(BookKey bookKey);
	
	public void removeFromReservedBooks(Collection<Book> bookList);
	
	public void removeFromReservedBooks(BookCriteria bookCriteria);
	
}
