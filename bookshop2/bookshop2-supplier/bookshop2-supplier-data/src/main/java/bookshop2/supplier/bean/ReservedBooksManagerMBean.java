package bookshop2.supplier.bean;

import java.util.Collection;
import java.util.List;

import javax.management.MXBean;

import bookshop2.Book;
import bookshop2.BookCriteria;


@MXBean
public interface ReservedBooksManagerMBean {
	
	public static final String MBEAN_NAME = "bookshop2.supplier.data:name=ReservedBooksManager";
	
	public void clearContext();
	
	public List<Book> getAllReservedBooksRecords();
	
	public Book getReservedBooksRecordById(Long id);
	
	public List<Book> getReservedBooksRecordsByPage(int pageIndex, int pageSize);
	
	public List<Book> getReservedBooksRecordsByCriteria(BookCriteria bookCriteria);
	
	public Long addReservedBooksRecord(Book book);
	
	public List<Long> addReservedBooksRecords(Collection<Book> bookList);
	
	public void saveReservedBooksRecord(Book book);
	
	public void saveReservedBooksRecords(Collection<Book> bookList);
	
	public void removeAllReservedBooksRecords();
	
	public void removeReservedBooksRecord(Book book);
	
	public void removeReservedBooksRecords(Collection<Book> bookList);
	
	public void removeReservedBooksRecords(BookCriteria bookCriteria);
	
	public void importReservedBooksRecords();
	
}
