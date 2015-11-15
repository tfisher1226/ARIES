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
import org.aries.process.ActionState;
import org.aries.tx.TransactionHelper;

import bookshop2.Book;
import bookshop2.BookCriteria;
import bookshop2.BookKey;
import bookshop2.supplier.SupplierContext;
import bookshop2.util.Bookshop2Helper;
import common.jmx.MBeanUtil;
import common.tx.state.AbstractStateManager;


public class BookInventoryManagerGood extends AbstractStateManager<BookInventoryState> implements BookInventoryManagerMBean {
	
	private static final Log log = LogFactory.getLog(BookInventoryManagerGood.class);
	
	@Inject
	private SupplierContext supplierContext;
	
	@Inject
	private BookInventory bookInventory;
	
	@Inject
	private BookInventoryProcessor stateProcessor;

	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	public BookInventoryManagerGood() {
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
		if (transactionSynchronizationRegistry != null) {
			Object transactionKey = transactionSynchronizationRegistry.getTransactionKey();
			//int transactionStatus = transactionSynchronizationRegistry.getTransactionStatus();
			if (transactionKey == null)
				System.out.println();
			if (transactionKey != null) {
				String transactionId = transactionKey.toString();
				execute(stateProcessor, transactionId);
			}
		}
	}
	
	protected void printTransactionId() {
		Object transactionKey = transactionSynchronizationRegistry.getTransactionKey();
		//int transactionStatus = transactionSynchronizationRegistry.getTransactionStatus();
		if (transactionKey == null)
			System.out.println();
		if (transactionKey != null) {
			String transactionId = transactionKey.toString();
			System.out.println("FIXTURE>>>>>>>>>>>>>> transactionId = "+transactionId);
		}
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
	
	@Override
	public List<Book> getAllReservedBooks() {
		if (supplierContext.isGlobalTransactionActive())
			return currentState.getAllReservedBooks();
		return bookInventory.getAllReservedBooks();
	}
	
	@Override
	public Map<BookKey, Book> getAllReservedBooksAsMap() {
		if (supplierContext.isGlobalTransactionActive())
			return currentState.getAllReservedBooksAsMap();
		return bookInventory.getAllReservedBooksAsMap();
	}
	
	@Override
	public Book getFromReservedBooks(Long bookId) {
		if (supplierContext.isGlobalTransactionActive())
			return currentState.getFromReservedBooks(bookId);
		return bookInventory.getFromReservedBooks(bookId);
	}
	
	@Override
	public Book getFromReservedBooks(BookKey bookKey) {
		if (supplierContext.isGlobalTransactionActive())
			return currentState.getFromReservedBooks(bookKey);
		return bookInventory.getFromReservedBooks(bookKey);
	}
	
	@Override
	public List<Book> getFromReservedBooks(Collection<Long> bookIds) {
		if (supplierContext.isGlobalTransactionActive())
			return currentState.getFromReservedBooks(bookIds);
		return bookInventory.getFromReservedBooks(bookIds);
	}
	
	@Override
	public Map<BookKey, Book> getFromReservedBooksAsMap(Collection<BookKey> bookKeys) {
		if (supplierContext.isGlobalTransactionActive())
			return currentState.getFromReservedBooksAsMap(bookKeys);
		return bookInventory.getFromReservedBooksAsMap(bookKeys);
	}
	
	@Override
	public List<Book> getFromReservedBooks(BookCriteria bookCriteria) {
		if (supplierContext.isGlobalTransactionActive())
			return currentState.getFromReservedBooks(bookCriteria);
		return bookInventory.getFromReservedBooks(bookCriteria);
	}
	
	@Override
	public List<Book> getMatchingReservedBooks(Collection<Book> bookList) {
		if (supplierContext.isGlobalTransactionActive())
			return currentState.getMatchingReservedBooks(bookList);
		return bookInventory.getMatchingReservedBooks(bookList);
	}
	
	@Override
	public Map<BookKey, Book> getMatchingReservedBooksAsMap(Collection<Book> bookList) {
		Collection<BookKey> bookKeys = Bookshop2Helper.createBookKeys(bookList);
		if (supplierContext.isGlobalTransactionActive())
			return currentState.getFromReservedBooksAsMap(bookKeys);
		return bookInventory.getFromReservedBooksAsMap(bookKeys);
	}
	
//	@Override
//	@TransactionAttribute(REQUIRES_NEW)
//	public Long addToReservedBooks(Book book) {
//		return addToReservedBooksIC(book);
//	}
//
//	@TransactionAttribute(REQUIRED)
//	public Long addToReservedBooksIC(Book book) {
//		if (supplierContext.isGlobalTransactionActive()) {
//			stateProcessor.addToPendingReservedBooks(book);
//			return null;
//		} else {
//			return bookInventory.addToReservedBooks(book);
//		}
//	}

	@Override
	public Long addToReservedBooks(Book book) {
		if (supplierContext.isGlobalTransactionActive()) {
			stateProcessor.addToPendingReservedBooks(book);
			return null;
		} else {
			return bookInventory.addToReservedBooks(book);
		}
	}

//	@Override
//	@TransactionAttribute(REQUIRES_NEW)
//	public List<Long> addToReservedBooks(Collection<Book> bookList) {
//		return addToReservedBooksIC(bookList);
//	}
//
//	@TransactionAttribute(REQUIRED)
//	public List<Long> addToReservedBooksIC(Collection<Book> bookList) {
//		if (supplierContext.isGlobalTransactionActive()) {
//			Map<BookKey, Book> bookMap = Bookshop2Helper.createBookMap(bookList);
//			stateProcessor.addToPendingReservedBooks(bookMap);
//			return null;
//		} else {
//			return bookInventory.addToReservedBooks(bookList);
//		}
//	}

	@Override
	public List<Long> addToReservedBooks(Collection<Book> bookList) {
		if (supplierContext.isGlobalTransactionActive()) {
			Map<BookKey, Book> bookMap = Bookshop2Helper.createBookMap(bookList);
			stateProcessor.addToPendingReservedBooks(bookMap);
			return null;
		} else {
			return bookInventory.addToReservedBooks(bookList);
		}
	}

	@Override
	public List<Long> addToReservedBooks(Map<BookKey, Book> bookMap) {
		if (supplierContext.isGlobalTransactionActive()) {
			stateProcessor.addToPendingReservedBooks(bookMap);
			return null;
		} else {
			return bookInventory.addToReservedBooks(bookMap);
		}
	}
	
	@Override
	public void removeAllReservedBooks() {
		if (supplierContext.isGlobalTransactionActive())
			stateProcessor.removeAllPendingReservedBooks();
		else bookInventory.removeAllReservedBooks();
	}
	
	@Override
	public void removeFromReservedBooks(Book book) {
		if (supplierContext.isGlobalTransactionActive()) {
			BookKey bookKey = Bookshop2Helper.createBookKey(book);
			stateProcessor.removeFromPendingReservedBooks(bookKey);
		} else {
			bookInventory.removeFromReservedBooks(book);
		}
	}
	
	@Override
	public void removeFromReservedBooks(Long bookId) {
		if (supplierContext.isGlobalTransactionActive())
			stateProcessor.removeFromPendingReservedBooks(bookId);
		else bookInventory.removeFromReservedBooks(bookId);
	}
	
	@Override
	public void removeFromReservedBooks(BookKey bookKey) {
		if (supplierContext.isGlobalTransactionActive())
			stateProcessor.removeFromPendingReservedBooks(bookKey);
		else bookInventory.removeFromReservedBooks(bookKey);
	}
	
	@Override
	public void removeFromReservedBooks(Collection<Book> bookList) {
		if (supplierContext.isGlobalTransactionActive()) {
			stateProcessor.removeFromPendingReservedBooks(bookList);
		} else {
			bookInventory.removeFromReservedBooks(bookList);
		}
	}
	
	@Override
	public void removeFromReservedBooks(BookCriteria bookCriteria) {
		if (supplierContext.isGlobalTransactionActive()) {
			stateProcessor.removeFromPendingReservedBooks(bookCriteria);
		} else {
			bookInventory.removeFromReservedBooks(bookCriteria);
		}
	}
	
//	@Override
//	public Map<String, Book> getAllReservedBooksAsMap() {
//		if (supplierContext.isGlobalTransactionActive())
//			return bookInventory.getAllReservedBooksAsMap();
//		return currentState.getReservedBooks();
//	}
//	
//	public List<Book> getReservedBooks() {
//		//return Bookshop2Helper.createBookList(currentState.getReservedBooks());
//		return stateRepository.getAllReservedBooksRecords();
//	}
//
//	public List<Book> getPendingReservedBooks() {
//		return Bookshop2Helper.createBookList(stateProcessor.getPendingReservedBooks());
//	}
//
//	public List<Book> getPreparedReservedBooks() {
//		return isLocked() ? Bookshop2Helper.createBookList(getPreparedState().getReservedBooks()) : null;
//	}
//	
//	public void setReservedBooks(List<Book> bookList) {
//		stateProcessor.setPendingReservedBooks(Bookshop2Helper.createBookMap(bookList));
//	}
//
//	public void unsetReservedBooks() {
//		stateProcessor.setPendingReservedBooks(null);
//	}
//
//	public void addToReservedBooks(List<Book> bookList) {
//		try {
//			printTransactionId();
//			stateRepository.addReservedBooksRecords(bookList);
//			printTransactionId();
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//	}
//
//	public void addToReservedBooks(Map<Object, Book> bookMap) {
//		if (isTransacted()) {
//			if (isIsolated()) {
//				stateProcessor.addPendingReservedBooks(bookMap);
//			} else {
//				//currentState.getReservedBooks().putAll(bookMap);
//			}
//			execute(stateProcessor, transactionId);
//		} else {
//			saveReservedBooks(bookMap);
//		}
//	}
//	
//	public void removeFromReservedBooks(List<Book> bookList) {
//		removeFromReservedBooks(Bookshop2Helper.createBookMap(bookList));
//	}
//	
//	public void removeFromReservedBooks(Map<Object, Book> bookMap) {
//		//stateProcessor.removePendingReservedBooks(reservedBooks);
//		stateRepository.removeReservedBooksRecords(bookMap);
//	}
//
//	public void clearReservedBooks() {
//		stateProcessor.clearPendingReservedBooks();
//	}
	
	
	public boolean flushChanges() {
		TransactionHelper transactionHelper = new TransactionHelper();
		transactionHelper.open();

//		Xid xid = null;
//		Transaction transaction = null;
//		Transaction subTransaction = null;
//		
//		try {
//			transaction = TransactionManager.transactionManager().suspend();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		try {
//			xid = XATxConverter.getXid(new Uid(), false, BridgeDurableParticipant.XARESOURCE_FORMAT_ID);
//			subTransaction = SubordinationManager.getTransactionImporter().importTransaction(xid);
//			TransactionManager.transactionManager().resume(subTransaction);
//			TransactionRegistry.getInstance().registerTransaction("current", subTransaction);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		BookInventoryState state = getCurrentState();
		List<Book> reservedBooks = state.getAllReservedBooks();
		bookInventory.addToReservedBooks(reservedBooks);
		
//		try {
//			SubordinationManager.getXATerminator().commit(xid, false);
//		} catch (XAException e) {
//			e.printStackTrace();
//		}
//		
//		try {
//			TransactionManager.transactionManager().resume(transaction);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		transactionHelper.close();
		return true;
	}

}
