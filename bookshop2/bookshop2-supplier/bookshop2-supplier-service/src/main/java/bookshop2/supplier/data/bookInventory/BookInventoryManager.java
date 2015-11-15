package bookshop2.supplier.data.bookInventory;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.transaction.TransactionSynchronizationRegistry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.runtime.RequestContext;

import bookshop2.Book;
import bookshop2.BookCriteria;
import bookshop2.BookKey;
import bookshop2.supplier.SupplierContext;
import bookshop2.util.Bookshop2Helper;

import common.jmx.MBeanUtil;
import common.tx.state.AbstractStateManager;


@Startup
@Stateful
@LocalBean
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
@TransactionAttribute(REQUIRED)
public class BookInventoryManager extends AbstractStateManager<BookInventoryState> implements BookInventoryManagerMBean {
	
	private static final Log log = LogFactory.getLog(BookInventoryManager.class);
	
	@Inject
	private RequestContext requestContext;
	
	@Inject
	private BookInventory bookInventory;
	
	@Inject
	private BookInventoryProcessor stateProcessor;

	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	public BookInventoryManager() {
		//nothing for now
	}
	
	
	public BookInventory getBookInventory() {
		return bookInventory;
	}
	
	public void setBookInventory(BookInventory bookInventory) {
		this.bookInventory = bookInventory;
	}
	
	public BookInventoryProcessor getStateProcessor() {
		return stateProcessor;
	}
	
	public void setStateProcessor(BookInventoryProcessor stateProcessor) {
		this.stateProcessor = stateProcessor;
	}
	
	@Override
	public String getName() {
		return "BookInventoryManager";
	}
	
	@PostConstruct
	public void registerWithJMX() {
		MBeanUtil.registerMBean(this, MBEAN_NAME);
	}
	
	@PreDestroy
	public void unregisterWithJMX() {
		MBeanUtil.unregisterMBean(MBEAN_NAME);
	}
	
	public BookInventoryState createState() {
		return new BookInventoryState();
	}
	
	public BookInventoryState resetState() {
		BookInventoryState state = createState();
		return state;
	}
	
	public void updateState() {
		//TODO if this fails then we need to mark global TX as rollback only
		updateState(stateProcessor);
	}

	public boolean saveState(BookInventoryState state) {
		try {
			bookInventory.addToReservedBooks(state.getAllReservedBooks());
			return true;
			
		} catch (Exception e) {
			return false;
		}
	}
	
	public void commitState() {
		stateProcessor.updateState(currentState);
	}
	
	@Override
	public void clearContext() {
		bookInventory.clearContext();
	}
	
	public SupplierContext getSupplierContext() {
		String correlationId = requestContext.getCorrelationId();
		return SupplierContext.getInstance(correlationId);
	}
	
	protected boolean isGlobalTransactionActive() {
		return getSupplierContext().isGlobalTransactionActive();
	}

	@Override
	public List<Book> getAllReservedBooks() {
		if (isGlobalTransactionActive())
			return currentState.getAllReservedBooks();
		return bookInventory.getAllReservedBooks();
	}
	
	@Override
	public Map<BookKey, Book> getAllReservedBooksAsMap() {
		if (isGlobalTransactionActive())
			return currentState.getAllReservedBooksAsMap();
		return bookInventory.getAllReservedBooksAsMap();
	}
	
	@Override
	public Book getFromReservedBooks(Long bookId) {
		if (isGlobalTransactionActive())
			return currentState.getFromReservedBooks(bookId);
		return bookInventory.getFromReservedBooks(bookId);
	}
	
	@Override
	public Book getFromReservedBooks(BookKey bookKey) {
		if (isGlobalTransactionActive())
			return currentState.getFromReservedBooks(bookKey);
		return bookInventory.getFromReservedBooks(bookKey);
	}
	
	@Override
	public List<Book> getFromReservedBooks(Collection<Long> bookIds) {
		if (isGlobalTransactionActive())
			return currentState.getFromReservedBooks(bookIds);
		return bookInventory.getFromReservedBooks(bookIds);
	}
	
	@Override
	public Map<BookKey, Book> getFromReservedBooksAsMap(Collection<BookKey> bookKeys) {
		if (isGlobalTransactionActive())
			return currentState.getFromReservedBooksAsMap(bookKeys);
		return bookInventory.getFromReservedBooksAsMap(bookKeys);
	}
	
	@Override
	public List<Book> getFromReservedBooks(BookCriteria bookCriteria) {
		if (isGlobalTransactionActive())
			return currentState.getFromReservedBooks(bookCriteria);
		return bookInventory.getFromReservedBooks(bookCriteria);
	}
	
	@Override
	public List<Book> getMatchingReservedBooks(Collection<Book> bookList) {
		if (isGlobalTransactionActive())
			return currentState.getMatchingReservedBooks(bookList);
		return bookInventory.getMatchingReservedBooks(bookList);
	}
	
	@Override
	public Map<BookKey, Book> getMatchingReservedBooksAsMap(Collection<Book> bookList) {
		Collection<BookKey> bookKeys = Bookshop2Helper.createBookKeys(bookList);
		if (isGlobalTransactionActive())
			return currentState.getFromReservedBooksAsMap(bookKeys);
		return bookInventory.getFromReservedBooksAsMap(bookKeys);
	}
	
	@Override
	public Long addToReservedBooks(Book book) {
		if (isGlobalTransactionActive()) {
			stateProcessor.addToPendingReservedBooks(book);
			return null;
		} else {
			return bookInventory.addToReservedBooks(book);
		}
	}

	@Override
	public List<Long> addToReservedBooks(Collection<Book> bookList) {
		if (isGlobalTransactionActive()) {
			Map<BookKey, Book> bookMap = Bookshop2Helper.createBookMap(bookList);
			stateProcessor.addToPendingReservedBooks(bookMap);
			return null;
		} else {
			return bookInventory.addToReservedBooks(bookList);
		}
	}

	@Override
	public List<Long> addToReservedBooks(Map<BookKey, Book> bookMap) {
		if (isGlobalTransactionActive()) {
			stateProcessor.addToPendingReservedBooks(bookMap);
			return null;
		} else {
			return bookInventory.addToReservedBooks(bookMap);
		}
	}
	
	@Override
	public void removeAllReservedBooks() {
		if (isGlobalTransactionActive())
			stateProcessor.removeAllPendingReservedBooks();
		else bookInventory.removeAllReservedBooks();
	}
	
	@Override
	public void removeFromReservedBooks(Book book) {
		if (isGlobalTransactionActive()) {
			BookKey bookKey = Bookshop2Helper.createBookKey(book);
			stateProcessor.removeFromPendingReservedBooks(bookKey);
		} else {
			bookInventory.removeFromReservedBooks(book);
		}
	}
	
	@Override
	public void removeFromReservedBooks(Long bookId) {
		if (isGlobalTransactionActive())
			stateProcessor.removeFromPendingReservedBooks(bookId);
		else bookInventory.removeFromReservedBooks(bookId);
	}
	
	@Override
	public void removeFromReservedBooks(BookKey bookKey) {
		if (isGlobalTransactionActive())
			stateProcessor.removeFromPendingReservedBooks(bookKey);
		else bookInventory.removeFromReservedBooks(bookKey);
	}
	
	@Override
	public void removeFromReservedBooks(Collection<Book> bookList) {
		if (isGlobalTransactionActive()) {
			stateProcessor.removeFromPendingReservedBooks(bookList);
		} else {
			bookInventory.removeFromReservedBooks(bookList);
		}
	}
	
	@Override
	public void removeFromReservedBooks(BookCriteria bookCriteria) {
		if (isGlobalTransactionActive()) {
			stateProcessor.removeFromPendingReservedBooks(bookCriteria);
		} else {
			bookInventory.removeFromReservedBooks(bookCriteria);
		}
	}
	
}
