package bookshop2.supplier.management.reservedBooksManager;

import java.util.List;
import java.util.Map;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.Book;
import bookshop2.BookCriteria;
import bookshop2.BookKey;


@WebService(name = "reservedBooksManager", targetNamespace = "http://bookshop2/supplier")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ReservedBooksManager {
	
	public String ID = "bookshop2.supplier.reservedBooksManager";
	
	public List<Book> getAllReservedBooks();
	
	public Map<BookKey, Book> getAllReservedBooksAsMap();
	
	public Book getFromReservedBooksById(Long bookId);
	
	public Book getFromReservedBooksByKey(BookKey bookKey);
	
	public List<Book> getFromReservedBooksByIds(List<Long> bookIds);
	
	public Map<BookKey, Book> getFromReservedBooksByKeys(List<BookKey> bookKeys);
	
	public List<Book> getFromReservedBooksByCriteria(BookCriteria bookCriteria);
	
	public List<Book> getMatchingReservedBooks(List<Book> bookList);
	
	public Map<BookKey, Book> getMatchingReservedBooksAsMap(List<Book> bookList);
	
	public Long addToReservedBooks(Book book);

	public List<Long> addToReservedBooksAsList(List<Book> bookList);
	
	public List<Long> addToReservedBooksAsMap(Map<BookKey, Book> bookMap);
	
	public void removeAllReservedBooks();
	
	public void removeFromReservedBooks(Book book);
	
	public void removeFromReservedBooksById(Long bookId);
	
	public void removeFromReservedBooksByKey(BookKey bookKey);
	
	public void removeFromReservedBooksAsList(List<Book> bookList);
	
	public void removeFromReservedBooksByCriteria(BookCriteria bookCriteria);
	
}
