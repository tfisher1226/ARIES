package bookshop2.supplier.management.reservedBooksManager;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.transaction.TransactionSynchronizationRegistry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.process.TimeoutHandler;
import org.aries.runtime.RequestContext;
import org.aries.tx.service.AbstractServiceHandler;
import org.aries.tx.service.ServiceHandlerInterceptor;
import org.aries.util.ExceptionUtil;

import bookshop2.Book;
import bookshop2.BookCriteria;
import bookshop2.BookKey;
import bookshop2.supplier.BookInventoryRepository;
import bookshop2.util.Bookshop2Helper;


@Stateful
@Local(ReservedBooksManagerHandler.class)
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
@Interceptors(ServiceHandlerInterceptor.class)
public class ReservedBooksManagerHandlerImpl extends AbstractServiceHandler implements ReservedBooksManagerHandler {
	
	private static final Log log = LogFactory.getLog(ReservedBooksManagerHandlerImpl.class);
	
	@Inject
	private RequestContext requestContext;
	
	@Inject
	protected BookInventoryRepository bookInventoryRepository;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	public String getName() {
		return "ReservedBooksManager";
	}
	
	public String getDomain() {
		return "bookshop2.supplier";
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public List<Book> getAllReservedBooks() {
		log.info("#### [supplier]: ReservedBooksManager request received");
		TimeoutHandler timeoutHandler = null;
		
		try {
			List<Book> resultList = bookInventoryRepository.getAllReservedBooksRecords();
			return resultList;
		
		} catch (Throwable e) {
			log.error("Error", e);
			if (timeoutHandler != null)
				timeoutHandler.reset();
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
			
		} finally {
			timeout = DEFAULT_TIMEOUT;
		}
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public Map<BookKey, Book> getAllReservedBooksAsMap() {
		log.info("#### [supplier]: ReservedBooksManager request received");
		TimeoutHandler timeoutHandler = null;
		
		try {
			List<Book> resultList = bookInventoryRepository.getAllReservedBooksRecords();
			Map<BookKey, Book> resultMap = Bookshop2Helper.createBookMap(resultList);
			return resultMap;
		
		} catch (Throwable e) {
			log.error("Error", e);
			if (timeoutHandler != null)
				timeoutHandler.reset();
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
			
		} finally {
			timeout = DEFAULT_TIMEOUT;
		}
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public Book getFromReservedBooks(Long bookId) {
		log.info("#### [supplier]: ReservedBooksManager request received");
		TimeoutHandler timeoutHandler = null;
		
		try {
			Assert.notNull(bookId, "BookId must be specified");
			Book result = bookInventoryRepository.getReservedBooksRecord(bookId);
			return result;
		
		} catch (Throwable e) {
			log.error("Error", e);
			if (timeoutHandler != null)
				timeoutHandler.reset();
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
			
		} finally {
			timeout = DEFAULT_TIMEOUT;
		}
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public Book getFromReservedBooks(BookKey bookKey) {
		log.info("#### [supplier]: ReservedBooksManager request received");
		TimeoutHandler timeoutHandler = null;
		
		try {
			Assert.notNull(bookKey, "BookKey must be specified");
			BookCriteria bookCriteria = Bookshop2Helper.createBookCriteriaFromKey(bookKey);
			List<Book> resultList = bookInventoryRepository.getReservedBooksRecords(bookCriteria);
			Assert.isTrue(resultList.size() <= 1, "Multiple ReservedBooks records found for key: "+bookKey);
			if (resultList.size() == 1) {
				Book result = resultList.get(0);
			return result;
			}
			return null;
		
		} catch (Throwable e) {
			log.error("Error", e);
			if (timeoutHandler != null)
				timeoutHandler.reset();
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
			
		} finally {
			timeout = DEFAULT_TIMEOUT;
		}
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public List<Book> getFromReservedBooks(List<Long> bookIds) {
		log.info("#### [supplier]: ReservedBooksManager request received");
		TimeoutHandler timeoutHandler = null;
		
		try {
			Assert.notNull(bookIds, "BookIds must be specified");
			BookCriteria bookCriteria = Bookshop2Helper.createBookCriteriaFromIds(bookIds);
			List<Book> resultList = bookInventoryRepository.getReservedBooksRecords(bookCriteria);
			Assert.notNull(resultList, "Results not found for Book Ids: "+bookIds);
			return resultList;
		
		} catch (Throwable e) {
			log.error("Error", e);
			if (timeoutHandler != null)
				timeoutHandler.reset();
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
			
		} finally {
			timeout = DEFAULT_TIMEOUT;
		}
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public Map<BookKey, Book> getFromReservedBooksAsMap(List<BookKey> bookKeys) {
		log.info("#### [supplier]: ReservedBooksManager request received");
		TimeoutHandler timeoutHandler = null;
		
		try {
			Assert.notNull(bookKeys, "BookKeys must be specified");
			BookCriteria bookCriteria = Bookshop2Helper.createBookCriteriaFromKeys(bookKeys);
			List<Book> resultList = bookInventoryRepository.getReservedBooksRecords(bookCriteria);
			Assert.notNull(resultList, "Results not found for Book keys: "+bookKeys);
			Map<BookKey, Book> resultMap = Bookshop2Helper.createBookMap(resultList);
			return resultMap;
		
		} catch (Throwable e) {
			log.error("Error", e);
			if (timeoutHandler != null)
				timeoutHandler.reset();
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
			
		} finally {
			timeout = DEFAULT_TIMEOUT;
		}
	}

	@Override
	@TransactionAttribute(REQUIRED)
	public List<Book> getFromReservedBooks(BookCriteria bookCriteria) {
		log.info("#### [supplier]: ReservedBooksManager request received");
		TimeoutHandler timeoutHandler = null;
		
		try {
			Assert.notNull(bookCriteria, "BookCriteria must be specified");
			List<Book> resultList = bookInventoryRepository.getReservedBooksRecords(bookCriteria);
			Assert.notNull(resultList, "Results not found for Book criteria: "+bookCriteria);
			return resultList;
		
		} catch (Throwable e) {
			log.error("Error", e);
			if (timeoutHandler != null)
				timeoutHandler.reset();
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
			
		} finally {
			timeout = DEFAULT_TIMEOUT;
		}
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public List<Book> getMatchingReservedBooks(List<Book> bookList) {
		log.info("#### [supplier]: ReservedBooksManager request received");
		TimeoutHandler timeoutHandler = null;
		
		try {
			Assert.notNull(bookList, "BookList must be specified");
			BookCriteria bookCriteria = Bookshop2Helper.createBookCriteria(bookList);
			List<Book> resultList = bookInventoryRepository.getReservedBooksRecords(bookCriteria);
			Assert.notNull(resultList, "No matching results found for Book list: "+bookList);
			return resultList;
		
		} catch (Throwable e) {
			log.error("Error", e);
			if (timeoutHandler != null)
				timeoutHandler.reset();
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
			
		} finally {
			timeout = DEFAULT_TIMEOUT;
		}
	}

	@Override
	@TransactionAttribute(REQUIRED)
	public Map<BookKey, Book> getMatchingReservedBooksAsMap(List<Book> bookList) {
		log.info("#### [supplier]: ReservedBooksManager request received");
		TimeoutHandler timeoutHandler = null;
		
		try {
			Assert.notNull(bookList, "BookList must be specified");
			List<Book> resultList = getMatchingReservedBooks(bookList);
			Assert.notNull(resultList, "No matching results found for Book list: "+bookList);
			Map<BookKey, Book> resultMap = Bookshop2Helper.createBookMap(resultList);
			return resultMap;
		
		} catch (Throwable e) {
			log.error("Error", e);
			if (timeoutHandler != null)
				timeoutHandler.reset();
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
			
		} finally {
			timeout = DEFAULT_TIMEOUT;
		}
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public Long addToReservedBooks(Book book) {
		log.info("#### [supplier]: ReservedBooksManager request received");
		TimeoutHandler timeoutHandler = null;
		
		try {
			Assert.notNull(book, "Book must be specified");
			Long result = bookInventoryRepository.addReservedBooksRecord(book);
			return result;
		
		} catch (Throwable e) {
			log.error("Error", e);
			if (timeoutHandler != null)
				timeoutHandler.reset();
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
			
		} finally {
			timeout = DEFAULT_TIMEOUT;
		}
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public List<Long> addToReservedBooks(List<Book> bookList) {
		log.info("#### [supplier]: ReservedBooksManager request received");
		TimeoutHandler timeoutHandler = null;
		
		try {
			Assert.notNull(bookList, "BookList must be specified");
			List<Long> resultList = bookInventoryRepository.addReservedBooksRecords(bookList);
			return resultList;
		
		} catch (Throwable e) {
			log.error("Error", e);
			if (timeoutHandler != null)
				timeoutHandler.reset();
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
			
		} finally {
			timeout = DEFAULT_TIMEOUT;
		}
	}

	@Override
	@TransactionAttribute(REQUIRED)
	public List<Long> addToReservedBooks(Map<BookKey, Book> bookMap) {
		log.info("#### [supplier]: ReservedBooksManager request received");
		TimeoutHandler timeoutHandler = null;
		
		try {
			Assert.notNull(bookMap, "BookMap must be specified");
			List<Long> resultList = bookInventoryRepository.addReservedBooksRecords(bookMap.values());
			return resultList;
		
		} catch (Throwable e) {
			log.error("Error", e);
			if (timeoutHandler != null)
				timeoutHandler.reset();
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
			
		} finally {
			timeout = DEFAULT_TIMEOUT;
		}
	}

	@Override
	@TransactionAttribute(REQUIRED)
	public void removeAllReservedBooks() {
		log.info("#### [supplier]: ReservedBooksManager request received");
		TimeoutHandler timeoutHandler = null;
		
		try {
			bookInventoryRepository.removeAllReservedBooksRecords();
		
		} catch (Throwable e) {
			log.error("Error", e);
			if (timeoutHandler != null)
				timeoutHandler.reset();
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
			
		} finally {
			timeout = DEFAULT_TIMEOUT;
		}
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public void removeFromReservedBooks(Book book) {
		log.info("#### [supplier]: ReservedBooksManager request received");
		TimeoutHandler timeoutHandler = null;
		
		try {
			Assert.notNull(book, "Book must be specified");
			bookInventoryRepository.removeReservedBooksRecord(book);
		
		} catch (Throwable e) {
			log.error("Error", e);
			if (timeoutHandler != null)
				timeoutHandler.reset();
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
			
		} finally {
			timeout = DEFAULT_TIMEOUT;
		}
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public void removeFromReservedBooks(Long bookId) {
		log.info("#### [supplier]: ReservedBooksManager request received");
		TimeoutHandler timeoutHandler = null;
		
		try {
			Assert.notNull(bookId, "BookId must be specified");
			bookInventoryRepository.removeReservedBooksRecord(bookId);
		
		} catch (Throwable e) {
			log.error("Error", e);
			if (timeoutHandler != null)
				timeoutHandler.reset();
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
			
		} finally {
			timeout = DEFAULT_TIMEOUT;
		}
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public void removeFromReservedBooks(BookKey bookKey) {
		log.info("#### [supplier]: ReservedBooksManager request received");
		TimeoutHandler timeoutHandler = null;
		
		try {
			Assert.notNull(bookKey, "BookKey must be specified");
			BookCriteria bookCriteria = Bookshop2Helper.createBookCriteriaFromKey(bookKey);
			bookInventoryRepository.removeReservedBooksRecords(bookCriteria);
		
		} catch (Throwable e) {
			log.error("Error", e);
			if (timeoutHandler != null)
				timeoutHandler.reset();
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
			
		} finally {
			timeout = DEFAULT_TIMEOUT;
		}
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public void removeFromReservedBooks(List<Book> bookList) {
		log.info("#### [supplier]: ReservedBooksManager request received");
		TimeoutHandler timeoutHandler = null;
		
		try {
			Assert.notNull(bookList, "BookList must be specified");
			bookInventoryRepository.removeReservedBooksRecords(bookList);
		
		} catch (Throwable e) {
			log.error("Error", e);
			if (timeoutHandler != null)
				timeoutHandler.reset();
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
			
		} finally {
			timeout = DEFAULT_TIMEOUT;
		}
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public void removeFromReservedBooks(BookCriteria bookCriteria) {
		log.info("#### [supplier]: ReservedBooksManager request received");
		TimeoutHandler timeoutHandler = null;
		
		try {
			Assert.notNull(bookCriteria, "BookCriteria must be specified");
			bookInventoryRepository.removeReservedBooksRecords(bookCriteria);
		
		} catch (Throwable e) {
			log.error("Error", e);
			if (timeoutHandler != null)
				timeoutHandler.reset();
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
			
		} finally {
			timeout = DEFAULT_TIMEOUT;
		}
	}

}
