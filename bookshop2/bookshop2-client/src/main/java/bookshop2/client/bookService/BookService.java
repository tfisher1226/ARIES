package bookshop2.client.bookService;

import java.util.List;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.Book;
import bookshop2.BookCriteria;


@WebService(name = "bookService", targetNamespace = "http://bookshop2")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface BookService {
	
	public String ID = "bookshop2.bookService";
	
	public List<Book> getAllBookRecords();
	
	public Book getBookRecordById(Long id);
	
	public List<Book> getBookRecordsByPage(int pageIndex, int pageSize);
	
	public List<Book> getBookRecordsByCriteria(BookCriteria bookCriteria);
	
	public Long addBookRecord(Book book);
	
	public void saveBookRecord(Book book);
	
	public void removeAllBookRecords();
	
	public void removeBookRecord(Book book);
	
	public void removeBookRecords(BookCriteria bookCriteria);
	
	public void importBookRecords();
	
}
