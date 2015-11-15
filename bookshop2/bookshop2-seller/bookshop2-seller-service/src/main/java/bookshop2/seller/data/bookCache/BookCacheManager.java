package bookshop2.seller.data.bookCache;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.Collection;
import java.util.List;
import java.util.Set;

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

import admin.User;
import bookshop2.Book;
import bookshop2.BookCriteria;
import bookshop2.seller.SellerContext;

import common.jmx.MBeanUtil;
import common.tx.state.AbstractStateManager;


@Startup
@Stateful
@LocalBean
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
@TransactionAttribute(REQUIRED)
public class BookCacheManager extends AbstractStateManager<BookCacheState> implements BookCacheManagerMBean {

	private static final Log log = LogFactory.getLog(BookCacheManager.class);

	@Inject
	private BookCache bookCache;
	
	@Inject
	private BookCacheProcessor bookCacheProcessor;

	@Inject
	private RequestContext requestContext;
	

	public BookCacheManager() {
		//nothing for now
	}


	public BookCache getBookCache() {
		return bookCache;
	}

	public void setBookCache(BookCache bookCache) {
		this.bookCache = bookCache;
	}

	public BookCacheProcessor getBookCacheProcessor() {
		return bookCacheProcessor;
	}

	public void setBookCacheProcessor(BookCacheProcessor bookCacheProcessor) {
		this.bookCacheProcessor = bookCacheProcessor;
	}

	@Override
	public String getName() {
		return "BookCacheManager";
	}
	
	@PostConstruct
	public void registerWithJMX() {
		MBeanUtil.registerMBean(this, MBEAN_NAME);
	}
	
	@PreDestroy
	public void unregisterWithJMX() {
		MBeanUtil.unregisterMBean(MBEAN_NAME);
	}
	
	public BookCacheState createState() {
		return new BookCacheState();
	}

	public BookCacheState resetState() {
		BookCacheState state = createState();
		return state;
	}

	public void updateState() {
		//TODO if this fails then we need to mark global TX as rollback only
		updateState(bookCacheProcessor);
	}

	public boolean saveState(BookCacheState state) {
		try {
			state.setUser(state.getUser());
			state.addToBookOrders(state.getAllBookOrders());
			state.addToAvailableBooks(state.getAllAvailableBooks());
			state.addToUnavailableBooks(state.getAllUnavailableBooks());
			return true;
			
		} catch (Throwable e) {
			return false;
		}
	}
	
	public void commitState() {
		bookCacheProcessor.updateState(currentState);
	}

	public SellerContext getSellerContext() {
		String correlationId = requestContext.getCorrelationId();
		return SellerContext.getInstance(correlationId);
	}
	
	protected boolean isGlobalTransactionActive() {
		return getSellerContext().isGlobalTransactionActive();
	}
	
	@Override
	public User getUser() {
		logState_getUser();
		if (isGlobalTransactionActive())
			return currentState.getUser();
		return bookCache.getUser();
	}
	
	@Override
	public void setUser(User user) {
		logState_setUser(user);
		if (isGlobalTransactionActive())
			bookCacheProcessor.setPendingUser(user);
		else bookCache.setUser(user);
	}
	
	@Override
	public List<Book> getAllBookOrders() {
		logState_getAllBookOrders();
		if (isGlobalTransactionActive())
			return currentState.getAllBookOrders();
		return bookCache.getAllBookOrders();
	}
	
	@Override
	public Book getFromBookOrders(Long bookId) {
		logState_getFromBookOrders(bookId);
		if (isGlobalTransactionActive())
			return currentState.getFromBookOrders(bookId);
		return bookCache.getFromBookOrders(bookId);
	}
	
	@Override
	public List<Book> getFromBookOrders(Collection<Long> bookIds) {
		logState_getFromBookOrders(bookIds);
		if (isGlobalTransactionActive())
			return currentState.getFromBookOrders(bookIds);
		return bookCache.getFromBookOrders(bookIds);
	}
	
	@Override
	public List<Book> getFromBookOrders(BookCriteria bookCriteria) {
		logState_getFromBookOrders(bookCriteria);
		if (isGlobalTransactionActive())
			return currentState.getFromBookOrders(bookCriteria);
		return bookCache.getFromBookOrders(bookCriteria);
	}
	
	@Override
	public List<Book> getMatchingBookOrders(Collection<Book> bookList) {
		logState_getMatchingBookOrders(bookList);
		if (isGlobalTransactionActive())
			return currentState.getMatchingBookOrders(bookList);
		return bookCache.getMatchingBookOrders(bookList);
	}
	
	@Override
	public void setBookOrders(Collection<Book> bookList) {
		logState_setBookOrders(bookList);
		if (isGlobalTransactionActive())
			bookCacheProcessor.setPendingBookOrders(bookList);
		else bookCache.setBookOrders(bookList);
	}
	
	@Override
	public void addToBookOrders(Book book) {
		logState_addToBookOrders(book);
		if (isGlobalTransactionActive())
			bookCacheProcessor.addToPendingBookOrders(book);
		else bookCache.addToBookOrders(book);
	}
	
	@Override
	public void addToBookOrders(Collection<Book> bookList) {
		logState_addToBookOrders(bookList);
		if (isGlobalTransactionActive())
			bookCacheProcessor.addToPendingBookOrders(bookList);
		else bookCache.addToBookOrders(bookList);
	}

	@Override
	public void removeAllBookOrders() {
		logState_removeAllBookOrders();
		if (isGlobalTransactionActive())
			bookCacheProcessor.removeAllPendingBookOrders();
		else bookCache.removeAllBookOrders();
	}
	
	@Override
	public void removeFromBookOrders(Book book) {
		logState_removeFromBookOrders(book);
		if (isGlobalTransactionActive())
			bookCacheProcessor.removeFromPendingBookOrders(book);
		else bookCache.removeFromBookOrders(book);
	}
	
	@Override
	public void removeFromBookOrders(Long bookId) {
		logState_removeFromBookOrders(bookId);
		if (isGlobalTransactionActive())
			bookCacheProcessor.removeFromPendingBookOrders(bookId);
		else bookCache.removeFromBookOrders(bookId);
	}
	
	@Override
	public void removeFromBookOrders(Collection<Book> bookList) {
		logState_removeFromBookOrders(bookList);
		if (isGlobalTransactionActive())
			bookCacheProcessor.removeFromPendingBookOrders(bookList);
		else bookCache.removeFromBookOrders(bookList);
	}
	
	@Override
	public void removeFromBookOrders(BookCriteria bookCriteria) {
		logState_removeFromBookOrders(bookCriteria);
		if (isGlobalTransactionActive())
			bookCacheProcessor.removeFromPendingBookOrders(bookCriteria);
		else bookCache.removeFromBookOrders(bookCriteria);
	}
	
	@Override
	public Set<Book> getAllAvailableBooks() {
		logState_getAllAvailableBooks();
		if (isGlobalTransactionActive())
			return currentState.getAllAvailableBooks();
		return bookCache.getAllAvailableBooks();
	}
	
	@Override
	public Book getFromAvailableBooks(Long bookId) {
		logState_getFromAvailableBooks(bookId);
		if (isGlobalTransactionActive())
			return currentState.getFromAvailableBooks(bookId);
		return bookCache.getFromAvailableBooks(bookId);
	}
	
	@Override
	public Set<Book> getFromAvailableBooks(Collection<Long> bookIds) {
		logState_getFromAvailableBooks(bookIds);
		if (isGlobalTransactionActive())
			return currentState.getFromAvailableBooks(bookIds);
		return bookCache.getFromAvailableBooks(bookIds);
	}
	
	@Override
	public Set<Book> getFromAvailableBooks(BookCriteria bookCriteria) {
		logState_getFromAvailableBooks(bookCriteria);
		if (isGlobalTransactionActive())
			return currentState.getFromAvailableBooks(bookCriteria);
		return bookCache.getFromAvailableBooks(bookCriteria);
	}
	
	@Override
	public Set<Book> getMatchingAvailableBooks(Collection<Book> bookSet) {
		logState_getMatchingAvailableBooks(bookSet);
		if (isGlobalTransactionActive())
			return currentState.getMatchingAvailableBooks(bookSet);
		return bookCache.getMatchingAvailableBooks(bookSet);
	}
	
	@Override
	public void setAvailableBooks(Collection<Book> bookSet) {
		logState_setAvailableBooks(bookSet);
		if (isGlobalTransactionActive())
			bookCacheProcessor.setPendingAvailableBooks(bookSet);
		else bookCache.setAvailableBooks(bookSet);
	}
	
	@Override
	public void addToAvailableBooks(Book book) {
		logState_addToAvailableBooks(book);
		if (isGlobalTransactionActive())
			bookCacheProcessor.addToPendingAvailableBooks(book);
		else bookCache.addToAvailableBooks(book);
	}
	
	@Override
	public void addToAvailableBooks(Collection<Book> bookSet) {
		logState_addToAvailableBooks(bookSet);
		if (isGlobalTransactionActive())
			bookCacheProcessor.addToPendingAvailableBooks(bookSet);
		else bookCache.addToAvailableBooks(bookSet);
	}

	@Override
	public void removeAllAvailableBooks() {
		logState_removeAllAvailableBooks();
		if (isGlobalTransactionActive())
			bookCacheProcessor.removeAllPendingAvailableBooks();
		else bookCache.removeAllAvailableBooks();
	}
	
	@Override
	public void removeFromAvailableBooks(Book book) {
		logState_removeFromAvailableBooks(book);
		if (isGlobalTransactionActive())
			bookCacheProcessor.removeFromPendingAvailableBooks(book);
		else bookCache.removeFromAvailableBooks(book);
	}
	
	@Override
	public void removeFromAvailableBooks(Long bookId) {
		logState_removeFromAvailableBooks(bookId);
		if (isGlobalTransactionActive())
			bookCacheProcessor.removeFromPendingAvailableBooks(bookId);
		else bookCache.removeFromAvailableBooks(bookId);
	}
	
	@Override
	public void removeFromAvailableBooks(Collection<Book> bookSet) {
		logState_removeFromAvailableBooks(bookSet);
		if (isGlobalTransactionActive())
			bookCacheProcessor.removeFromPendingAvailableBooks(bookSet);
		else bookCache.removeFromAvailableBooks(bookSet);
	}
	
	@Override
	public void removeFromAvailableBooks(BookCriteria bookCriteria) {
		logState_removeFromAvailableBooks(bookCriteria);
		if (isGlobalTransactionActive())
			bookCacheProcessor.removeFromPendingAvailableBooks(bookCriteria);
		else bookCache.removeFromAvailableBooks(bookCriteria);
	}
	
	@Override
	public Set<Book> getAllUnavailableBooks() {
		logState_getAllUnavailableBooks();
		if (isGlobalTransactionActive())
			return currentState.getAllUnavailableBooks();
		return bookCache.getAllUnavailableBooks();
	}
	
	@Override
	public Book getFromUnavailableBooks(Long bookId) {
		logState_getFromUnavailableBooks(bookId);
		if (isGlobalTransactionActive())
			return currentState.getFromUnavailableBooks(bookId);
		return bookCache.getFromUnavailableBooks(bookId);
	}
	
	@Override
	public Set<Book> getFromUnavailableBooks(Collection<Long> bookIds) {
		logState_getFromUnavailableBooks(bookIds);
		if (isGlobalTransactionActive())
			return currentState.getFromUnavailableBooks(bookIds);
		return bookCache.getFromUnavailableBooks(bookIds);
	}
	
	@Override
	public Set<Book> getFromUnavailableBooks(BookCriteria bookCriteria) {
		logState_getFromUnavailableBooks(bookCriteria);
		if (isGlobalTransactionActive())
			return currentState.getFromUnavailableBooks(bookCriteria);
		return bookCache.getFromUnavailableBooks(bookCriteria);
	}
	
	@Override
	public Set<Book> getMatchingUnavailableBooks(Collection<Book> bookSet) {
		logState_getMatchingUnavailableBooks(bookSet);
		if (isGlobalTransactionActive())
			return currentState.getMatchingUnavailableBooks(bookSet);
		return bookCache.getMatchingUnavailableBooks(bookSet);
	}
	
	@Override
	public void setUnavailableBooks(Collection<Book> bookSet) {
		logState_setUnavailableBooks(bookSet);
		if (isGlobalTransactionActive())
			bookCacheProcessor.setPendingUnavailableBooks(bookSet);
		else bookCache.setUnavailableBooks(bookSet);
	}
	
	@Override
	public void addToUnavailableBooks(Book book) {
		logState_addToUnavailableBooks(book);
		if (isGlobalTransactionActive())
			bookCacheProcessor.addToPendingUnavailableBooks(book);
		else bookCache.addToUnavailableBooks(book);
	}
	
	@Override
	public void addToUnavailableBooks(Collection<Book> bookSet) {
		logState_addToUnavailableBooks(bookSet);
		if (isGlobalTransactionActive())
			bookCacheProcessor.addToPendingUnavailableBooks(bookSet);
		else bookCache.addToUnavailableBooks(bookSet);
	}

	@Override
	public void removeAllUnavailableBooks() {
		logState_removeAllUnavailableBooks();
		if (isGlobalTransactionActive())
			bookCacheProcessor.removeAllPendingUnavailableBooks();
		else bookCache.removeAllUnavailableBooks();
	}
	
	@Override
	public void removeFromUnavailableBooks(Book book) {
		logState_removeFromUnavailableBooks(book);
		if (isGlobalTransactionActive())
			bookCacheProcessor.removeFromPendingUnavailableBooks(book);
		else bookCache.removeFromUnavailableBooks(book);
	}
	
	@Override
	public void removeFromUnavailableBooks(Long bookId) {
		logState_removeFromUnavailableBooks(bookId);
		if (isGlobalTransactionActive())
			bookCacheProcessor.removeFromPendingUnavailableBooks(bookId);
		else bookCache.removeFromUnavailableBooks(bookId);
	}
	
	@Override
	public void removeFromUnavailableBooks(Collection<Book> bookSet) {
		logState_removeFromUnavailableBooks(bookSet);
		if (isGlobalTransactionActive())
			bookCacheProcessor.removeFromPendingUnavailableBooks(bookSet);
		else bookCache.removeFromUnavailableBooks(bookSet);
	}
	
	@Override
	public void removeFromUnavailableBooks(BookCriteria bookCriteria) {
		logState_removeFromUnavailableBooks(bookCriteria);
		if (isGlobalTransactionActive())
			bookCacheProcessor.removeFromPendingUnavailableBooks(bookCriteria);
		else bookCache.removeFromUnavailableBooks(bookCriteria);
	}

	public void logState_getUser() {
		ActionState action = new ActionState();
		action.setActionId("getUser");
		getSellerContext().logAction(action);
	}
	
	public void logState_setUser(User user) {
		ActionState action = new ActionState();
		action.setActionId("setUser");
		action.addElement("user", user);
		getSellerContext().logAction(action);
	}
	
	public void logState_getAllBookOrders() {
		ActionState action = new ActionState();
		action.setActionId("getAllBookOrders");
		getSellerContext().logAction(action);
	}
	
	public void logState_getFromBookOrders(Long bookId) {
		ActionState action = new ActionState();
		action.setActionId("getFromBookOrders");
		action.addElement("bookId", bookId);
		getSellerContext().logAction(action);
	}
	
	public void logState_getFromBookOrders(Collection<Long> bookIds) {
		ActionState action = new ActionState();
		action.setActionId("getFromBookOrders");
		action.addElement("bookIds", bookIds);
		getSellerContext().logAction(action);
	}
	
	public void logState_getFromBookOrders(BookCriteria bookCriteria) {
		ActionState action = new ActionState();
		action.setActionId("getFromBookOrders");
		action.addElement("bookCriteria", bookCriteria);
		getSellerContext().logAction(action);
	}
	
	public void logState_getMatchingBookOrders(Collection<Book> bookList) {
		ActionState action = new ActionState();
		action.setActionId("getMatchingBookOrders");
		action.addElement("bookList", bookList);
		getSellerContext().logAction(action);
	}
	
	public void logState_setBookOrders(Collection<Book> bookList) {
		ActionState action = new ActionState();
		action.setActionId("setBookOrders");
		action.addElement("bookList", bookList);
		getSellerContext().logAction(action);
	}
	
	public void logState_addToBookOrders(Book book) {
		ActionState action = new ActionState();
		action.setActionId("addToBookOrders");
		action.addElement("book", book);
		getSellerContext().logAction(action);
	}
	
	public void logState_addToBookOrders(Collection<Book> bookList) {
		ActionState action = new ActionState();
		action.setActionId("addToBookOrders");
		action.addElement("bookList", bookList);
		getSellerContext().logAction(action);
	}
	
	public void logState_removeAllBookOrders() {
		ActionState action = new ActionState();
		action.setActionId("removeAllBookOrders");
		getSellerContext().logAction(action);
	}
	
	public void logState_removeFromBookOrders(Book book) {
		ActionState action = new ActionState();
		action.setActionId("removeFromBookOrders");
		action.addElement("book", book);
		getSellerContext().logAction(action);
	}
	
	public void logState_removeFromBookOrders(Long bookId) {
		ActionState action = new ActionState();
		action.setActionId("removeFromBookOrders");
		action.addElement("bookId", bookId);
		getSellerContext().logAction(action);
	}
	
	public void logState_removeFromBookOrders(Collection<Book> bookList) {
		ActionState action = new ActionState();
		action.setActionId("removeFromBookOrders");
		action.addElement("bookList", bookList);
		getSellerContext().logAction(action);
	}
	
	public void logState_removeFromBookOrders(BookCriteria bookCriteria) {
		ActionState action = new ActionState();
		action.setActionId("removeFromBookOrders");
		action.addElement("bookCriteria", bookCriteria);
		getSellerContext().logAction(action);
	}
	
	public void logState_getAllAvailableBooks() {
		ActionState action = new ActionState();
		action.setActionId("getAllAvailableBooks");
		getSellerContext().logAction(action);
	}
	
	public void logState_getFromAvailableBooks(Long bookId) {
		ActionState action = new ActionState();
		action.setActionId("getFromAvailableBooks");
		action.addElement("bookId", bookId);
		getSellerContext().logAction(action);
	}
	
	public void logState_getFromAvailableBooks(Collection<Long> bookIds) {
		ActionState action = new ActionState();
		action.setActionId("getFromAvailableBooks");
		action.addElement("bookIds", bookIds);
		getSellerContext().logAction(action);
	}
	
	public void logState_getFromAvailableBooks(BookCriteria bookCriteria) {
		ActionState action = new ActionState();
		action.setActionId("getFromAvailableBooks");
		action.addElement("bookCriteria", bookCriteria);
		getSellerContext().logAction(action);
	}
	
	public void logState_getMatchingAvailableBooks(Collection<Book> bookSet) {
		ActionState action = new ActionState();
		action.setActionId("getMatchingAvailableBooks");
		action.addElement("bookSet", bookSet);
		getSellerContext().logAction(action);
	}
	
	public void logState_setAvailableBooks(Collection<Book> bookSet) {
		ActionState action = new ActionState();
		action.setActionId("setAvailableBooks");
		action.addElement("bookSet", bookSet);
		getSellerContext().logAction(action);
	}
	
	public void logState_addToAvailableBooks(Book book) {
		ActionState action = new ActionState();
		action.setActionId("addToAvailableBooks");
		action.addElement("book", book);
		getSellerContext().logAction(action);
	}
	
	public void logState_addToAvailableBooks(Collection<Book> bookSet) {
		ActionState action = new ActionState();
		action.setActionId("addToAvailableBooks");
		action.addElement("bookSet", bookSet);
		getSellerContext().logAction(action);
	}
	
	public void logState_removeAllAvailableBooks() {
		ActionState action = new ActionState();
		action.setActionId("removeAllAvailableBooks");
		getSellerContext().logAction(action);
	}
	
	public void logState_removeFromAvailableBooks(Book book) {
		ActionState action = new ActionState();
		action.setActionId("removeFromAvailableBooks");
		action.addElement("book", book);
		getSellerContext().logAction(action);
	}
	
	public void logState_removeFromAvailableBooks(Long bookId) {
		ActionState action = new ActionState();
		action.setActionId("removeFromAvailableBooks");
		action.addElement("bookId", bookId);
		getSellerContext().logAction(action);
	}
	
	public void logState_removeFromAvailableBooks(Collection<Book> bookSet) {
		ActionState action = new ActionState();
		action.setActionId("removeFromAvailableBooks");
		action.addElement("bookSet", bookSet);
		getSellerContext().logAction(action);
	}
	
	public void logState_removeFromAvailableBooks(BookCriteria bookCriteria) {
		ActionState action = new ActionState();
		action.setActionId("removeFromAvailableBooks");
		action.addElement("bookCriteria", bookCriteria);
		getSellerContext().logAction(action);
	}
	
	public void logState_getAllUnavailableBooks() {
		ActionState action = new ActionState();
		action.setActionId("getAllUnavailableBooks");
		getSellerContext().logAction(action);
	}
	
	public void logState_getFromUnavailableBooks(Long bookId) {
		ActionState action = new ActionState();
		action.setActionId("getFromUnavailableBooks");
		action.addElement("bookId", bookId);
		getSellerContext().logAction(action);
	}
	
	public void logState_getFromUnavailableBooks(Collection<Long> bookIds) {
		ActionState action = new ActionState();
		action.setActionId("getFromUnavailableBooks");
		action.addElement("bookIds", bookIds);
		getSellerContext().logAction(action);
	}
	
	public void logState_getFromUnavailableBooks(BookCriteria bookCriteria) {
		ActionState action = new ActionState();
		action.setActionId("getFromUnavailableBooks");
		action.addElement("bookCriteria", bookCriteria);
		getSellerContext().logAction(action);
	}
	
	public void logState_getMatchingUnavailableBooks(Collection<Book> bookSet) {
		ActionState action = new ActionState();
		action.setActionId("getMatchingUnavailableBooks");
		action.addElement("bookSet", bookSet);
		getSellerContext().logAction(action);
	}
	
	public void logState_setUnavailableBooks(Collection<Book> bookSet) {
		ActionState action = new ActionState();
		action.setActionId("setUnavailableBooks");
		action.addElement("bookSet", bookSet);
		getSellerContext().logAction(action);
	}
	
	public void logState_addToUnavailableBooks(Book book) {
		ActionState action = new ActionState();
		action.setActionId("addToUnavailableBooks");
		action.addElement("book", book);
		getSellerContext().logAction(action);
	}
	
	public void logState_addToUnavailableBooks(Collection<Book> bookSet) {
		ActionState action = new ActionState();
		action.setActionId("addToUnavailableBooks");
		action.addElement("bookSet", bookSet);
		getSellerContext().logAction(action);
	}
	
	public void logState_removeAllUnavailableBooks() {
		ActionState action = new ActionState();
		action.setActionId("removeAllUnavailableBooks");
		getSellerContext().logAction(action);
	}
	
	public void logState_removeFromUnavailableBooks(Book book) {
		ActionState action = new ActionState();
		action.setActionId("removeFromUnavailableBooks");
		action.addElement("book", book);
		getSellerContext().logAction(action);
	}
	
	public void logState_removeFromUnavailableBooks(Long bookId) {
		ActionState action = new ActionState();
		action.setActionId("removeFromUnavailableBooks");
		action.addElement("bookId", bookId);
		getSellerContext().logAction(action);
	}
	
	public void logState_removeFromUnavailableBooks(Collection<Book> bookSet) {
		ActionState action = new ActionState();
		action.setActionId("removeFromUnavailableBooks");
		action.addElement("bookSet", bookSet);
		getSellerContext().logAction(action);
	}
	
	public void logState_removeFromUnavailableBooks(BookCriteria bookCriteria) {
		ActionState action = new ActionState();
		action.setActionId("removeFromUnavailableBooks");
		action.addElement("bookCriteria", bookCriteria);
		getSellerContext().logAction(action);
	}
	
}
