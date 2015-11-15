package bookshop2.supplier;

import java.util.Collection;
import java.util.List;

import bookshop2.Book;
import bookshop2.BookCriteria;
import bookshop2.BookKey;


public interface BookInventoryRepository {
	
	public void clearContext();
	
	public List<Book> getAllReservedBooksRecords();
	
	public Book getReservedBooksRecord(Long id);
	
	public Book getReservedBooksRecord(BookKey key);
	
	public List<Book> getReservedBooksRecords(int pageIndex, int pageSize);
	
	public List<Book> getReservedBooksRecords(BookCriteria bookCriteria);
	
	public Long addReservedBooksRecord(Book book);
	
	public List<Long> addReservedBooksRecords(Collection<Book> bookList);
	
	public void saveReservedBooksRecord(Book book);
	
	public void saveReservedBooksRecords(Collection<Book> bookList);
	
	public void removeAllReservedBooksRecords();
	
	public void removeReservedBooksRecord(Book book);
	
	public void removeReservedBooksRecord(Long id);
	
	public void removeReservedBooksRecord(BookKey key);
	
	public void removeReservedBooksRecords(Collection<Book> bookList);
	
	public void removeReservedBooksRecords(BookCriteria bookCriteria);
	
	public void importReservedBooksRecords();

}
