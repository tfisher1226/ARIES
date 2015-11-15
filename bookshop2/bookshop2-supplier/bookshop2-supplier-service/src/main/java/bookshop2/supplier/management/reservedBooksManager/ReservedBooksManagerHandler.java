package bookshop2.supplier.management.reservedBooksManager;

import java.util.List;
import java.util.Map;

import org.aries.tx.Transactional;

import bookshop2.Book;
import bookshop2.BookCriteria;
import bookshop2.BookKey;


public interface ReservedBooksManagerHandler extends Transactional {
	
	public List<Book> getAllReservedBooks();
	
	public Map<BookKey, Book> getAllReservedBooksAsMap();
	
	public Book getFromReservedBooks(Long bookId);
	
	public Book getFromReservedBooks(BookKey bookKey);
	
	public List<Book> getFromReservedBooks(List<Long> bookIds);
	
	public Map<BookKey, Book> getFromReservedBooksAsMap(List<BookKey> bookKeys);
	
	public List<Book> getFromReservedBooks(BookCriteria bookCriteria);
	
	public List<Book> getMatchingReservedBooks(List<Book> bookList);
	
	public Map<BookKey, Book> getMatchingReservedBooksAsMap(List<Book> bookList);
	
	public Long addToReservedBooks(Book book);
	
	public List<Long> addToReservedBooks(List<Book> bookList);
	
	public List<Long> addToReservedBooks(Map<BookKey, Book> bookMap);
	
	public void removeAllReservedBooks();
	
	public void removeFromReservedBooks(Book book);
	
	public void removeFromReservedBooks(Long bookId);
	
	public void removeFromReservedBooks(BookKey bookKey);
	
	public void removeFromReservedBooks(List<Book> bookList);
	
	public void removeFromReservedBooks(BookCriteria bookCriteria);
	
}
