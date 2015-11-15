package bookshop2.supplier.dao;

import java.util.Collection;
import java.util.List;

import org.aries.common.dao.Dao;

import bookshop2.BookCriteria;
import bookshop2.supplier.entity.AbstractBookEntity;


public interface BookDao<T extends AbstractBookEntity> extends Dao<T> {
	
	public T getBookRecordById(Long id);
	
	public T getBookRecordByBarCode(Long barCode);
	
	public T getBookRecordByCriteria(BookCriteria bookCriteria);
	
	public List<T> getAllBookRecords();
	
	public List<T> getBookRecordsByPage(int pageIndex, int pageSize);
	
	public List<T> getBookRecordsByCriteria(BookCriteria bookCriteria);
	
	public Long addBookRecord(T bookRecord);
	
	public List<Long> addBookRecords(Collection<T> bookRecords);
	
	public void saveBookRecord(T bookRecord);
	
	public void saveBookRecords(Collection<T> bookRecords);
	
	public void removeAllBookRecords();
	
	public void removeBookRecord(T bookRecord);
	
	public void removeBookRecords(Collection<T> bookRecords);

	public void removeBookRecords(BookCriteria bookCriteria);
	
}
