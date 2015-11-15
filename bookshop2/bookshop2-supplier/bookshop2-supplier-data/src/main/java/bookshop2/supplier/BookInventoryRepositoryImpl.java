package bookshop2.supplier;

import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.inject.Inject;

import org.aries.Assert;
import org.aries.data.AbstractRepository;
import org.aries.util.ExceptionUtil;

import bookshop2.Book;
import bookshop2.BookCriteria;
import bookshop2.BookKey;
import bookshop2.supplier.bean.ReservedBooksManager;


@Stateful
@Local(BookInventoryRepository.class)
public class BookInventoryRepositoryImpl extends AbstractRepository implements BookInventoryRepository {
	
	@Inject
	protected ReservedBooksManager reservedBooksManager;
	
	
	public ReservedBooksManager getReservedBooksManager() {
		return reservedBooksManager;
	}
	
	public void setReservedBooksManager(ReservedBooksManager reservedBooksManager) {
		this.reservedBooksManager = reservedBooksManager;
	}
	
	@Override
	public void clearContext() {
		try {
			reservedBooksManager.clearContext();
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Book> getAllReservedBooksRecords() {
		try {
			List<Book> records = reservedBooksManager.getAllReservedBooksRecords();
			Assert.notNull(records, "ReservedBooks record list null");
			return records;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public Book getReservedBooksRecord(Long id) {
		try {
			Assert.notNull(id, "Id must be specified");
			Book record = reservedBooksManager.getReservedBooksRecord(id);
			return record;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public Book getReservedBooksRecord(BookKey key) {
		try {
			Assert.notNull(key, "Key must be specified");
			Book record = reservedBooksManager.getReservedBooksRecord(key);
			return record;
			
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public List<Book> getReservedBooksRecords(int pageIndex, int pageSize) {
		try {
			Assert.notNull(pageIndex, "Page-index must be specified");
			Assert.notNull(pageSize, "Page-size must be specified");
			List<Book> records = reservedBooksManager.getReservedBooksRecords(pageIndex, pageSize);
			Assert.notNull(records, "ReservedBooks record list null");
			return records;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public List<Book> getReservedBooksRecords(BookCriteria bookCriteria) {
		try {
			Assert.notNull(bookCriteria, "Book record criteria must be specified");
			List<Book> records = reservedBooksManager.getReservedBooksRecords(bookCriteria);
			Assert.notNull(records, "ReservedBooks record list null");
			return records;
			
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public Long addReservedBooksRecord(Book book) {
		try {
			Assert.notNull(book, "ReservedBooks record must be specified");
			Long id = reservedBooksManager.addReservedBooksRecord(book);
			Assert.notNull(id, "Id must exist");
			return id;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public List<Long> addReservedBooksRecords(Collection<Book> bookList) {
		try {
			Assert.notNull(bookList, "ReservedBooks record list must be specified");
			List<Long> idList = reservedBooksManager.addReservedBooksRecords(bookList);
			Assert.notNull(idList, "Id list must exist");
			Assert.isTrue(idList.size() == bookList.size(), "Id count must be correct");
			return idList;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void saveReservedBooksRecord(Book book) {
		try {
			Assert.notNull(book, "ReservedBooks record must be specified");
			reservedBooksManager.saveReservedBooksRecord(book);
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void saveReservedBooksRecords(Collection<Book> bookList) {
		try {
			Assert.notNull(bookList, "ReservedBooks record list must be specified");
			reservedBooksManager.saveReservedBooksRecords(bookList);
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void removeAllReservedBooksRecords() {
		try {
			reservedBooksManager.removeAllReservedBooksRecords();
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void removeReservedBooksRecord(Book book) {
		try {
			Assert.notNull(book, "ReservedBooks record must be specified");
			reservedBooksManager.removeReservedBooksRecord(book);
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void removeReservedBooksRecord(Long id) {
		try {
			Assert.notNull(id, "ReservedBooks record ID must be specified");
			reservedBooksManager.removeReservedBooksRecord(id);
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void removeReservedBooksRecord(BookKey key) {
		try {
			Assert.notNull(key, "ReservedBooks record key must be specified");
			reservedBooksManager.removeReservedBooksRecord(key);
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void removeReservedBooksRecords(Collection<Book> bookList) {
		try {
			Assert.notNull(bookList, "ReservedBooks record list must be specified");
			reservedBooksManager.removeReservedBooksRecords(bookList);
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void removeReservedBooksRecords(BookCriteria bookCriteria) {
		try {
			Assert.notNull(bookCriteria, "Book record criteria must be specified");
			reservedBooksManager.removeReservedBooksRecords(bookCriteria);
			
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void importReservedBooksRecords() {
		try {
			reservedBooksManager.importReservedBooksRecords();
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
}
