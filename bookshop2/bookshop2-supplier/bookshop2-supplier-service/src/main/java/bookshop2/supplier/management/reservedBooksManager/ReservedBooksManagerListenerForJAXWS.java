package bookshop2.supplier.management.reservedBooksManager;

import static javax.ejb.TransactionAttributeType.REQUIRED;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.transaction.TransactionSynchronizationRegistry;
import javax.xml.ws.WebServiceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.tx.module.Bootstrapper;
import org.aries.util.ExceptionUtil;

import bookshop2.Book;
import bookshop2.BookCriteria;
import bookshop2.BookKey;


@Remote(ReservedBooksManager.class)
@Stateless(name = "ReservedBooksManager")
@WebService(name = "reservedBooksManager", serviceName = "reservedBooksManagerService", portName = "reservedBooksManager", targetNamespace = "http://bookshop2/supplier")
@HandlerChain(file = "/jaxws-handlers-service-oneway.xml")
public class ReservedBooksManagerListenerForJAXWS implements ReservedBooksManager {
	
	private static final Log log = LogFactory.getLog(ReservedBooksManagerListenerForJAXWS.class);
	
	@Inject
	private ReservedBooksManagerHandler reservedBooksManagerHandler;
	
	@Resource
	private WebServiceContext webServiceContext;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Override
	@WebMethod(operationName = "getAllReservedBooks")
	@WebResult(name = "bookList")
	@TransactionAttribute(REQUIRED)
	public List<Book> getAllReservedBooks() {
		if (!Bootstrapper.isInitialized("bookshop2-supplier-service"))
			return null;
		
		try {
			List<Book> resultList = reservedBooksManagerHandler.getAllReservedBooks();
			Assert.notNull(resultList, "getAllReservedBooks() result must exist");
			return resultList;
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod(operationName = "getAllReservedBooksAsMap")
	@WebResult(name = "bookMap")
	@TransactionAttribute(REQUIRED)
	public Map<BookKey, Book> getAllReservedBooksAsMap() {
		if (!Bootstrapper.isInitialized("bookshop2.supplier.service"))
			return null;
		
		try {
			Map<BookKey, Book> resultMap = reservedBooksManagerHandler.getAllReservedBooksAsMap();
			Assert.notNull(resultMap, "getAllReservedBooksAsMap() result must exist");
			return resultMap;
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod(operationName = "getFromReservedBooksById")
	@WebResult(name = "book")
	@TransactionAttribute(REQUIRED)
	public Book getFromReservedBooksById(Long bookId) {
		if (!Bootstrapper.isInitialized("bookshop2.supplier.service"))
			return null;
		
		try {
			Book result = reservedBooksManagerHandler.getFromReservedBooks(bookId);
			return result;
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod(operationName = "getFromReservedBooksByKey")
	@WebResult(name = "book")
	@TransactionAttribute(REQUIRED)
	public Book getFromReservedBooksByKey(BookKey bookKey) {
		if (!Bootstrapper.isInitialized("bookshop2.supplier.service"))
			return null;
		
		try {
			Book result = reservedBooksManagerHandler.getFromReservedBooks(bookKey);
			return result;
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod(operationName = "getFromReservedBooksByIds")
	@WebResult(name = "bookList")
	@TransactionAttribute(REQUIRED)
	public List<Book> getFromReservedBooksByIds(List<Long> bookIds) {
		if (!Bootstrapper.isInitialized("bookshop2.supplier.service"))
			return null;
		
		try {
			List<Book> resultList = reservedBooksManagerHandler.getFromReservedBooks(bookIds);
			Assert.notNull(resultList, "getFromReservedBooks() result must exist");
			return resultList;
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod(operationName = "getFromReservedBooksByKeys")
	@WebResult(name = "bookMap")
	@TransactionAttribute(REQUIRED)
	public Map<BookKey, Book> getFromReservedBooksByKeys(List<BookKey> bookKeys) {
		if (!Bootstrapper.isInitialized("bookshop2.supplier.service"))
			return null;
		
		try {
			Map<BookKey, Book> resultMap = reservedBooksManagerHandler.getFromReservedBooksAsMap(bookKeys);
			Assert.notNull(resultMap, "getFromReservedBooksAsMap() result must exist");
			return resultMap;
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod(operationName = "getFromReservedBooksByCriteria")
	@WebResult(name = "bookList")
	@TransactionAttribute(REQUIRED)
	public List<Book> getFromReservedBooksByCriteria(BookCriteria bookCriteria) {
		if (!Bootstrapper.isInitialized("bookshop2.supplier.service"))
			return null;
		
		try {
			List<Book> resultList = reservedBooksManagerHandler.getFromReservedBooks(bookCriteria);
			Assert.notNull(resultList, "getFromReservedBooks() result must exist");
			return resultList;
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod(operationName = "getMatchingReservedBooks")
	@WebResult(name = "bookList")
	@TransactionAttribute(REQUIRED)
	public List<Book> getMatchingReservedBooks(List<Book> bookList) {
		if (!Bootstrapper.isInitialized("bookshop2.supplier.service"))
			return null;
		
		try {
			List<Book> resultList = reservedBooksManagerHandler.getMatchingReservedBooks(bookList);
			Assert.notNull(resultList, "getMatchingReservedBooks() result must exist");
			return resultList;
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod(operationName = "getMatchingReservedBooksAsMap")
	@WebResult(name = "bookMap")
	@TransactionAttribute(REQUIRED)
	public Map<BookKey, Book> getMatchingReservedBooksAsMap(List<Book> bookList) {
		if (!Bootstrapper.isInitialized("bookshop2.supplier.service"))
			return null;
		
		try {
			Map<BookKey, Book> resultMap = reservedBooksManagerHandler.getMatchingReservedBooksAsMap(bookList);
			Assert.notNull(resultMap, "getMatchingReservedBooksAsMap() result must exist");
			return resultMap;
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod(operationName = "addToReservedBooks")
	@WebResult(name = "bookId")
	@TransactionAttribute(REQUIRED)
	public Long addToReservedBooks(Book book) {
		if (!Bootstrapper.isInitialized("bookshop2.supplier.service"))
			return null;
		
		try {
			Long result = reservedBooksManagerHandler.addToReservedBooks(book);
			return result;
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod(operationName = "addToReservedBooksAsList")
	@WebResult(name = "bookIdsList")
	@TransactionAttribute(REQUIRED)
	public List<Long> addToReservedBooksAsList(List<Book> bookList) {
		if (!Bootstrapper.isInitialized("bookshop2.supplier.service"))
			return null;
		
		try {
			List<Long> resultList = reservedBooksManagerHandler.addToReservedBooks(bookList);
			Assert.notNull(resultList, "addToReservedBooks() result must exist");
			return resultList;
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod(operationName = "addToReservedBooksAsMap")
	@WebResult(name = "bookIdsList")
	@TransactionAttribute(REQUIRED)
	public List<Long> addToReservedBooksAsMap(Map<BookKey, Book> bookMap) {
		if (!Bootstrapper.isInitialized("bookshop2.supplier.service"))
			return null;
		
		try {
			List<Long> resultList = reservedBooksManagerHandler.addToReservedBooks(bookMap);
			Assert.notNull(resultList, "addToReservedBooks() result must exist");
			return resultList;
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod(operationName = "removeAllReservedBooks")
	@TransactionAttribute(REQUIRED)
	public void removeAllReservedBooks() {
		if (!Bootstrapper.isInitialized("bookshop2.supplier.service"))
			return;
		
		try {
			reservedBooksManagerHandler.removeAllReservedBooks();
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod(operationName = "removeFromReservedBooks")
	@TransactionAttribute(REQUIRED)
	public void removeFromReservedBooks(Book book) {
		if (!Bootstrapper.isInitialized("bookshop2.supplier.service"))
			return;
		
		try {
			reservedBooksManagerHandler.removeFromReservedBooks(book);
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod(operationName = "removeFromReservedBooksById")
	@TransactionAttribute(REQUIRED)
	public void removeFromReservedBooksById(Long bookId) {
		if (!Bootstrapper.isInitialized("bookshop2.supplier.service"))
			return;
		
		try {
			reservedBooksManagerHandler.removeFromReservedBooks(bookId);
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod(operationName = "removeFromReservedBooksByKey")
	@TransactionAttribute(REQUIRED)
	public void removeFromReservedBooksByKey(BookKey bookKey) {
		if (!Bootstrapper.isInitialized("bookshop2.supplier.service"))
			return;
		
		try {
			reservedBooksManagerHandler.removeFromReservedBooks(bookKey);
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod(operationName = "removeFromReservedBooksAsList")
	@TransactionAttribute(REQUIRED)
	public void removeFromReservedBooksAsList(List<Book> bookList) {
		if (!Bootstrapper.isInitialized("bookshop2.supplier.service"))
			return;
		
		try {
			reservedBooksManagerHandler.removeFromReservedBooks(bookList);
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod(operationName = "removeFromReservedBooksByCriteria")
	@TransactionAttribute(REQUIRED)
	public void removeFromReservedBooksByCriteria(BookCriteria bookCriteria) {
		if (!Bootstrapper.isInitialized("bookshop2.supplier.service"))
			return;
		
		try {
			reservedBooksManagerHandler.removeFromReservedBooks(bookCriteria);
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
