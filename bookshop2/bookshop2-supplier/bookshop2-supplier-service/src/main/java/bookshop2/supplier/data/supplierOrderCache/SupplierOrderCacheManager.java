package bookshop2.supplier.data.supplierOrderCache;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.process.ActionState;
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
public class SupplierOrderCacheManager extends AbstractStateManager<SupplierOrderCacheState> implements SupplierOrderCacheManagerMBean {
	
	private static final Log log = LogFactory.getLog(SupplierOrderCacheManager.class);
	
	//@Inject
	//private RequestContext requestContext;
	
	@Inject
	private SupplierOrderCache supplierOrderCache;
	
	@Inject
	private SupplierOrderCacheProcessor supplierOrderCacheProcessor;
	
	
	public SupplierOrderCacheManager() {
		//nothing for now
	}
	
	
	public SupplierOrderCache getSupplierOrderCache() {
		return supplierOrderCache;
	}
	
	public void setSupplierOrderCache(SupplierOrderCache supplierOrderCache) {
		this.supplierOrderCache = supplierOrderCache;
	}
	
	public SupplierOrderCacheProcessor getSupplierOrderCacheProcessor() {
		return supplierOrderCacheProcessor;
	}
	
	public void setSupplierOrderCacheProcessor(SupplierOrderCacheProcessor supplierOrderCacheProcessor) {
		this.supplierOrderCacheProcessor = supplierOrderCacheProcessor;
	}
	
	@Override
	public String getName() {
		return "SupplierOrderCacheManager";
	}
	
	@PostConstruct
	public void registerWithJMX() {
		MBeanUtil.registerMBean(this, MBEAN_NAME);
	}
	
	@PreDestroy
	public void unregisterWithJMX() {
		MBeanUtil.unregisterMBean(MBEAN_NAME);
	}
	
	public SupplierOrderCacheState createState() {
		return new SupplierOrderCacheState();
	}
	
	public SupplierOrderCacheState resetState() {
		SupplierOrderCacheState state = createState();
		return state;
	}
	
	public void updateState() {
		//TODO if this fails then we need to mark global TX as rollback only
		updateState(supplierOrderCacheProcessor);
	}
	
	public boolean saveState(SupplierOrderCacheState state) {
		try {
			state.addToBooksInStock(state.getAllBooksInStockAsMap());
			return true;
			
		} catch (Throwable e) {
			return false;
		}
	}
	
	public void commitState() {
		supplierOrderCacheProcessor.updateState(currentState);
	}
	
	public SupplierContext getSupplierContext() {
		//String correlationId = requestContext.getCorrelationId();
		return SupplierContext.getInstance(null);
	}
	
	protected boolean isGlobalTransactionActive() {
		return getSupplierContext().isGlobalTransactionActive();
	}

	@Override
	public List<Book> getAllBooksInStock() {
		logState_getAllBooksInStock();
		if (isGlobalTransactionActive())
			return currentState.getAllBooksInStock();
		return supplierOrderCache.getAllBooksInStock();
	}
	
	@Override
	public Map<BookKey, Book> getAllBooksInStockAsMap() {
		logState_getAllBooksInStockAsMap();
		if (isGlobalTransactionActive())
			return currentState.getAllBooksInStockAsMap();
		return supplierOrderCache.getAllBooksInStockAsMap();
	}
	
	@Override
	public Book getFromBooksInStock(Long bookId) {
		logState_getFromBooksInStock(bookId);
		if (isGlobalTransactionActive())
			return currentState.getFromBooksInStock(bookId);
		return supplierOrderCache.getFromBooksInStock(bookId);
	}
	
	@Override
	public Book getFromBooksInStock(BookKey bookKey) {
		logState_getFromBooksInStock(bookKey);
		if (isGlobalTransactionActive())
			return currentState.getFromBooksInStock(bookKey);
		return supplierOrderCache.getFromBooksInStock(bookKey);
	}
	
	@Override
	public List<Book> getFromBooksInStock(Collection<Long> bookIds) {
		logState_getFromBooksInStock(bookIds);
		if (isGlobalTransactionActive())
			return currentState.getFromBooksInStock(bookIds);
		return supplierOrderCache.getFromBooksInStock(bookIds);
	}
	
	@Override
	public Map<BookKey, Book> getFromBooksInStockAsMap(Collection<BookKey> bookKeys) {
		logState_getFromBooksInStockAsMap(bookKeys);
		if (isGlobalTransactionActive())
			return currentState.getFromBooksInStockAsMap(bookKeys);
		return supplierOrderCache.getFromBooksInStockAsMap(bookKeys);
	}
	
	@Override
	public List<Book> getFromBooksInStock(BookCriteria bookCriteria) {
		logState_getFromBooksInStock(bookCriteria);
		if (isGlobalTransactionActive())
			return currentState.getFromBooksInStock(bookCriteria);
		return supplierOrderCache.getFromBooksInStock(bookCriteria);
	}
	
	@Override
	public List<Book> getMatchingBooksInStock(Collection<Book> bookList) {
		logState_getMatchingBooksInStock(bookList);
		if (isGlobalTransactionActive())
			return currentState.getMatchingBooksInStock(bookList);
		return supplierOrderCache.getMatchingBooksInStock(bookList);
	}
	
	@Override
	public Map<BookKey, Book> getMatchingBooksInStockAsMap(Collection<Book> bookList) {
		logState_getMatchingBooksInStockAsMap(bookList);
		if (isGlobalTransactionActive())
			return currentState.getMatchingBooksInStockAsMap(bookList);
		return supplierOrderCache.getMatchingBooksInStockAsMap(bookList);
	}
	
	@Override
	public void setBooksInStock(Map<BookKey, Book> bookMap) {
		logState_setBooksInStock(bookMap);
		if (isGlobalTransactionActive())
			supplierOrderCacheProcessor.setPendingBooksInStock(bookMap);
		else supplierOrderCache.setBooksInStock(bookMap);
	}
	
	@Override
	public void addToBooksInStock(Book book) {
		logState_addToBooksInStock(book);
		if (isGlobalTransactionActive())
			supplierOrderCacheProcessor.addToPendingBooksInStock(book);
		else supplierOrderCache.addToBooksInStock(book);
	}
	
	@Override
	public void addToBooksInStock(Collection<Book> bookList) {
		Map<BookKey, Book> bookMap = Bookshop2Helper.createBookMap(bookList);
		logState_addToBooksInStock(bookMap);
		addToBooksInStock(bookMap);
	}
	
	@Override
	public void addToBooksInStock(Map<BookKey, Book> bookMap) {
		logState_addToBooksInStock(bookMap);
		if (isGlobalTransactionActive())
			supplierOrderCacheProcessor.addToPendingBooksInStock(bookMap);
		else supplierOrderCache.addToBooksInStock(bookMap);
	}
	
	@Override
	public void removeAllBooksInStock() {
		logState_removeAllBooksInStock();
		if (isGlobalTransactionActive())
			supplierOrderCacheProcessor.removeAllPendingBooksInStock();
		else supplierOrderCache.removeAllBooksInStock();
	}
	
	@Override
	public void removeFromBooksInStock(Book book) {
		BookKey bookKey = Bookshop2Helper.createBookKey(book);
		logState_removeFromBooksInStock(bookKey);
		if (isGlobalTransactionActive())
			supplierOrderCacheProcessor.removeFromPendingBooksInStock(bookKey);
		else supplierOrderCache.removeFromBooksInStock(bookKey);
	}
	
	@Override
	public void removeFromBooksInStock(Long bookId) {
		logState_removeFromBooksInStock(bookId);
		if (isGlobalTransactionActive())
			supplierOrderCacheProcessor.removeFromPendingBooksInStock(bookId);
		else supplierOrderCache.removeFromBooksInStock(bookId);
	}
	
	@Override
	public void removeFromBooksInStock(BookKey bookKey) {
		logState_removeFromBooksInStock(bookKey);
		if (isGlobalTransactionActive())
			supplierOrderCacheProcessor.removeFromPendingBooksInStock(bookKey);
		else supplierOrderCache.removeFromBooksInStock(bookKey);
	}
	
	@Override
	public void removeFromBooksInStock(Collection<Book> bookList) {
		logState_removeFromBooksInStock(bookList);
		if (isGlobalTransactionActive())
			supplierOrderCacheProcessor.removeFromPendingBooksInStock(bookList);
		else supplierOrderCache.removeFromBooksInStock(bookList);
	}
	
	@Override
	public void removeFromBooksInStock(BookCriteria bookCriteria) {
		logState_removeFromBooksInStock(bookCriteria);
		if (isGlobalTransactionActive())
			supplierOrderCacheProcessor.removeFromPendingBooksInStock(bookCriteria);
		else supplierOrderCache.removeFromBooksInStock(bookCriteria);
	}
	
	public void logState_getAllBooksInStock() {
		ActionState action = new ActionState();
		action.setActionId("getAllBooksInStock");
		getSupplierContext().logAction(action);
	}
	
	public void logState_getAllBooksInStockAsMap() {
		ActionState action = new ActionState();
		action.setActionId("getAllBooksInStockAsMap");
		getSupplierContext().logAction(action);
	}
	
	public void logState_getFromBooksInStock(Long bookId) {
		ActionState action = new ActionState();
		action.setActionId("getFromBooksInStock");
		action.addElement("bookId", bookId);
		getSupplierContext().logAction(action);
	}
	
	public void logState_getFromBooksInStock(BookKey bookKey) {
		ActionState action = new ActionState();
		action.setActionId("getFromBooksInStock");
		action.addElement("bookKey", bookKey);
		getSupplierContext().logAction(action);
	}
	
	public void logState_getFromBooksInStock(Collection<Long> bookIds) {
		ActionState action = new ActionState();
		action.setActionId("getFromBooksInStock");
		action.addElement("bookIds", bookIds);
		getSupplierContext().logAction(action);
	}
	
	public void logState_getFromBooksInStockAsMap(Collection<BookKey> bookKeys) {
		ActionState action = new ActionState();
		action.setActionId("getFromBooksInStockAsMap");
		action.addElement("bookKeys", bookKeys);
		getSupplierContext().logAction(action);
	}
	
	public void logState_getFromBooksInStock(BookCriteria bookCriteria) {
		ActionState action = new ActionState();
		action.setActionId("getFromBooksInStock");
		action.addElement("bookCriteria", bookCriteria);
		getSupplierContext().logAction(action);
	}
	
	public void logState_getMatchingBooksInStock(Collection<Book> bookList) {
		ActionState action = new ActionState();
		action.setActionId("getMatchingBooksInStock");
		action.addElement("bookList", bookList);
		getSupplierContext().logAction(action);
	}
	
	public void logState_getMatchingBooksInStockAsMap(Collection<Book> bookList) {
		ActionState action = new ActionState();
		action.setActionId("getMatchingBooksInStockAsMap");
		action.addElement("bookList", bookList);
		getSupplierContext().logAction(action);
	}
	
	public void logState_setBooksInStock(Map<BookKey, Book> bookMap) {
		ActionState action = new ActionState();
		action.setActionId("setBooksInStock");
		action.addElement("bookMap", bookMap);
		getSupplierContext().logAction(action);
	}
	
	public void logState_addToBooksInStock(Book book) {
		ActionState action = new ActionState();
		action.setActionId("addToBooksInStock");
		action.addElement("book", book);
		getSupplierContext().logAction(action);
	}
	
	public void logState_addToBooksInStock(Map<BookKey, Book> bookMap) {
		ActionState action = new ActionState();
		action.setActionId("addToBooksInStock");
		action.addElement("bookMap", bookMap);
		getSupplierContext().logAction(action);
	}
	
	public void logState_removeAllBooksInStock() {
		ActionState action = new ActionState();
		action.setActionId("removeAllBooksInStock");
		getSupplierContext().logAction(action);
	}
	
	public void logState_removeFromBooksInStock(BookKey bookKey) {
		ActionState action = new ActionState();
		action.setActionId("removeFromBooksInStock");
		action.addElement("bookKey", bookKey);
		getSupplierContext().logAction(action);
	}
	
	public void logState_removeFromBooksInStock(Long bookId) {
		ActionState action = new ActionState();
		action.setActionId("removeFromBooksInStock");
		action.addElement("bookId", bookId);
		getSupplierContext().logAction(action);
	}
	
	public void logState_removeFromBooksInStock(Collection<Book> bookList) {
		ActionState action = new ActionState();
		action.setActionId("removeFromBooksInStock");
		action.addElement("bookList", bookList);
		getSupplierContext().logAction(action);
	}
	
	public void logState_removeFromBooksInStock(BookCriteria bookCriteria) {
		ActionState action = new ActionState();
		action.setActionId("removeFromBooksInStock");
		action.addElement("bookCriteria", bookCriteria);
		getSupplierContext().logAction(action);
	}
	
}
