package bookshop2.seller.data.bookCache;

import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.TransactionManager;

import org.aries.Assert;
import org.aries.tx.CacheModuleTestControl;
import org.aries.tx.TransactionTestControl;

import bookshop2.Book;
import bookshop2.BookKey;
import bookshop2.util.Bookshop2Fixture;
import bookshop2.util.Bookshop2Helper;

import com.arjuna.ats.jta.common.jtaPropertyManager;


@Stateful
public class BookCacheHelper {

	private boolean runningInContainer;
	
	private TransactionTestControl transactionTestControl;

	private CacheModuleTestControl cacheLayerTestControl;

	@Inject
	private BookCacheProxy bookCacheProxy;
	
	private boolean runningAsClient;


	public void setProxy(BookCacheProxy bookCacheProxy) {
		this.bookCacheProxy = bookCacheProxy;
	}

//	public void setJmxManager(JmxManager jmxManager) {
//		this.bookCacheProxy.setJmxManager(jmxManager);
//	}
	
	public boolean isRunningAsClient() {
		return runningAsClient;
	}

	public void setRunningAsClient(boolean runningAsClient) {
		this.runningAsClient = runningAsClient;
	}
	
	public void initialize(CacheModuleTestControl cacheLayerTestControl) throws Exception {
		initializeAsClient(cacheLayerTestControl);
		runningInContainer = true;
	}

	public void initializeAsClient(CacheModuleTestControl cacheLayerTestControl) throws Exception {
		initialize2(cacheLayerTestControl);
		//createTransactionControl();
		populateInitialContext();
//		createSupplierOrderCache();
		runningAsClient = true;
	}
	
	protected void initialize2(CacheModuleTestControl cacheLayerTestControl) throws Exception {
		this.transactionTestControl = cacheLayerTestControl.getTransactionTestControl();
		this.cacheLayerTestControl = cacheLayerTestControl;
	}
	
//	public void initialize(TransactionTestControl control) throws Exception {
//		this.control = control;
//		runningInContainer = true;
//	}

	//TransactionManager inside of InitialContext will be null when called from outside the container - we need to populate it.
	public void populateInitialContext() {
		try {
			InitialContext initialContext = new InitialContext();
			TransactionManager transactionManager = jtaPropertyManager.getJTAEnvironmentBean().getTransactionManager();
			initialContext.rebind("java:jboss/TransactionManager", transactionManager);

		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	
	/*
	 * BookOrders verification
	 */
	
//	protected void verifyBookOrdersCount(int expectedCount) throws Exception {
//		List<BookOrdersEntity> records = BookOrdersDao.getAllBooksList();
//		verifyBookOrdersCount(records, expectedCount);
//	}
//
//	protected void verifyBookOrdersCount(List<BookOrdersEntity> records, int expectedCount) throws Exception {
//		Assert.notNull(records, "Result not found");
//		if (expectedCount > 0)
//			Assert.notNull(records, "BookOrders records should exist");
//		Assert.equals(records.size(), expectedCount, "BookOrders record count not correct");
//	}
//
//	protected void verifyBookOrdersExists(BookEntity BookOrders) throws Exception {
//		verifyBookOrdersExists(BookOrders.getId());
//	}
//
//	protected void verifyBookOrdersExists(Long id) throws Exception {
//		BookOrdersEntity BookOrders = BookOrdersDao.getBookById(id);
//		Assert.notNull(BookOrders, "BookOrders should exist");
//		Assert.equals(BookOrders.getId(), id, "Ids should be same");
//	}
//
//	protected void verifyBookOrdersNotExists(BookEntity BookOrders) throws Exception {
//		verifyBookOrdersNotExists(BookOrders.getId());
//	}
//
//	protected void verifyBookOrdersNotExists(Long id) throws Exception {
//		BookOrdersEntity BookOrders = BookOrdersDao.getBookById(id);
//		Assert.isNull(BookOrders, "BookOrders should not exist");
//	}
	
	
	/*
	 * BookOrders management
	 */

	public void clearBookCache() throws Exception {
		bookCacheProxy.clearBookCache();
	}
	
	public void addToBookOrders() throws Exception {
		List<Book> books = Bookshop2Fixture.createList_Book();
		addToBookOrders(books);
	}

	public void addToBookOrders(int recordCount) throws Exception {
		List<Book> books = Bookshop2Fixture.createList_Book(recordCount);
		addToBookOrders(books);
	}

	public void addToBookOrders(Book book) throws Exception {
		bookCacheProxy.addToBookOrders(book);
	}
	
	public void addToBookOrders(List<Book> books) throws Exception {
		bookCacheProxy.addToBookOrders(books);
	}
	
	public void removeAllBookOrders() throws Exception {
		bookCacheProxy.removeAllBookOrders();
	}
	
	
	/*
	 * BookOrders direct access
	 */
	
	public List<Book> getAllBookOrders() throws Exception {
		//openEntityManager();
		//transactionTestControl.beginTransaction();
		List<Book> bookList = bookCacheProxy.getAllBookOrders();
		//transactionTestControl.commitTransaction();
		//transactionTestControl.clearTransactions();
		//closeEntityManager();
		return bookList;
	}
	
	public Book getFromBookOrders(Long id) throws Exception {
		Book book = bookCacheProxy.getFromBookOrders(id);
		return book;
	}
	
	public void assureAddBookOrders() throws Exception {
		assureAddBookOrders(true);
	}
	
	public void assureAddBookOrders(boolean commitAndClose) throws Exception {
		List<Book> bookList = Bookshop2Fixture.createList_Book();
		assureAddBookOrders(bookList, commitAndClose);
	}

	public void assureAddBookOrders(List<Book> bookList) throws Exception {
		assureAddBookOrders(bookList, true);
	}
	
	public void assureAddBookOrders(List<Book> bookList, boolean commitAndClose) throws Exception {
		Iterator<Book> iterator = bookList.iterator();
		while (iterator.hasNext()) {
			Book book = iterator.next();
			assureAddBookOrders(book, commitAndClose);
		}
	}

	public void assureAddBookOrders(Book book, boolean commitAndClose) throws Exception {
		//openEntityManager();
		transactionTestControl.beginTransaction();
		BookKey bookKey = Bookshop2Helper.createBookKey(book);
		bookCacheProxy.addToBookOrders(book);
		if (commitAndClose) {
			transactionTestControl.commitTransaction();
		}
		verifyBookOrdersExists(bookKey);
		if (commitAndClose) {
			//closeEntityManager();
		}
	}

	public void assureAddBookOrdersInProgress() throws Exception {
		//final List<Long> holder = new ArrayList<Long>();
		final Object mutex = new Object();
		Thread thread = new Thread(new Runnable() {
			public void run() {
				try {
					cacheLayerTestControl.resetContext();
					assureAddBookOrders(false);
					//holder.add(id);
					synchronized (mutex) {
						mutex.notifyAll();
					}
					//block forever for now
					Thread.sleep(Integer.MAX_VALUE);
				} catch (Exception e) {
					fail(e.getMessage());
				}
			}
		});
		thread.start();
		//thread.join();
		synchronized (mutex) {
			mutex.wait();
		}

		//cleanup
		cacheLayerTestControl.resetContext();
		//return holder.get(0);
	}
	
	public void assureRemoveAll() throws Exception {
		//openEntityManager();
		//transactionTestControl.beginTransaction();
		bookCacheProxy.removeAllBookOrders();
		bookCacheProxy.removeAllAvailableBooks();
		bookCacheProxy.removeAllUnavailableBooks();
		//transactionTestControl.commitTransaction();
		//transactionTestControl.clearTransactions();
		//closeEntityManager();
	}
	
	public void assureDeleteBookOrders(BookKey bookKey) throws Exception {
		Book book = bookCacheProxy.getFromBookOrders(bookKey);
		assureDeleteBookOrders(book);
	}

	public void assureDeleteBookOrders(Book book) throws Exception {
		//openEntityManager();
		//transactionTestControl.beginTransaction();
		BookKey bookKey = Bookshop2Helper.createBookKey(book);
		bookCacheProxy.removeFromBookOrders(bookKey);
		transactionTestControl.commitTransaction();
		transactionTestControl.clearTransactions();
		verifyBookOrdersNotExists(bookKey);
		//closeEntityManager();
	}
	

	/*
	 * BookOrders verification
	 */

	public void verifyBookOrdersCount(int expectedCount) throws Exception {
		//openEntityManager();
		List<Book> bookList = bookCacheProxy.getAllBookOrders();
		verifyBookOrdersCount(bookList, expectedCount);
		//closeEntityManager();
	}

	public void verifyBookOrdersCount(Collection<Book> bookList, int expectedCount) throws Exception {
		Assert.notNull(bookList, "Result not found");
		if (expectedCount > 0)
			Assert.notNull(bookList, "BookOrders records should exist");
		Assert.equals(expectedCount, bookList.size(), "BookOrders record count not correct");
	}

	public void verifyBookOrdersExists(Book book) throws Exception {
		BookKey bookKey = Bookshop2Helper.createBookKey(book);
		verifyBookOrdersExists(bookKey);
	}

	public void verifyBookOrdersExists(BookKey bookKey) throws Exception {
		//openEntityManager();
		Book book = bookCacheProxy.getFromBookOrders(bookKey);
		Assert.notNull(book, "Book should exist");
		BookKey existingBookKey = Bookshop2Helper.createBookKey(book);
		Assert.equals(existingBookKey, bookKey, "Book keys should be same");
		//closeEntityManager();
	}

//	protected void verifyBookOrdersExists(List<Long> ids) throws Exception {
//		Iterator<Long> iterator = ids.iterator();
//		while (iterator.hasNext()) {
//			Long id = iterator.next();
//			verifyBookOrdersExists(id);
//		}
//	}
	
	public void verifyBookOrdersNotExists(Book book) throws Exception {
		BookKey bookKey = Bookshop2Helper.createBookKey(book);
		verifyBookOrdersNotExists(bookKey);
	}

	public void verifyBookOrdersNotExists(BookKey bookKey) throws Exception {
		//openEntityManager();
		Book book = bookCacheProxy.getFromBookOrders(bookKey);
		Assert.isNull(book, "Book should not exist");
		//closeEntityManager();
	}

	
	/*
	 * AvailableBooks direct access
	 */
	
	public Set<Book> getAllAvailableBooks() {
		Set<Book> bookSet = bookCacheProxy.getAllAvailableBooks();
		return bookSet;
	}

	public Book getFromAvailableBooks(Long id) throws Exception {
		Book book = bookCacheProxy.getFromAvailableBooks(id);
		return book;
	}
	
	public void addToAvailableBooks() throws Exception {
		List<Book> books = Bookshop2Fixture.createList_Book();
		addToAvailableBooks(books);
	}

	public void addToAvailableBooks(int recordCount) throws Exception {
		List<Book> books = Bookshop2Fixture.createList_Book(recordCount);
		addToAvailableBooks(books);
	}

	public void addToAvailableBooks(Book book) throws Exception {
		bookCacheProxy.addToAvailableBooks(book);
	}
	
	public void addToAvailableBooks(Collection<Book> books) throws Exception {
		bookCacheProxy.addToAvailableBooks(books);
	}

	public void verifyAvailableBooksCount(int expectedCount) throws Exception {
		Set<Book> bookSet = bookCacheProxy.getAllAvailableBooks();
		verifyAvailableBooksCount(bookSet, expectedCount);
	}

	public void verifyAvailableBooksCount(Collection<Book> bookList, int expectedCount) throws Exception {
		Assert.notNull(bookList, "Result not found");
		if (expectedCount > 0)
			Assert.notNull(bookList, "AvailableBooks records should exist");
		Assert.equals(expectedCount, bookList.size(), "AvailableBooks record count not correct");
	}
	
	
	/*
	 * UnavailableBooks direct access
	 */
	
	public Set<Book> getAllUnavailableBooks() {
		Set<Book> bookSet = bookCacheProxy.getAllUnavailableBooks();
		return bookSet;
	}

	public Book getFromUnavailableBooks(Long id) throws Exception {
		Book book = bookCacheProxy.getFromUnavailableBooks(id);
		return book;
	}
	
	public void addToUnavailableBooks() throws Exception {
		List<Book> books = Bookshop2Fixture.createList_Book();
		addToUnavailableBooks(books);
	}

	public void addToUnavailableBooks(int recordCount) throws Exception {
		List<Book> books = Bookshop2Fixture.createList_Book(recordCount);
		addToUnavailableBooks(books);
	}

	public void addToUnavailableBooks(Book book) throws Exception {
		bookCacheProxy.addToUnavailableBooks(book);
	}

	public void addToUnavailableBooks(Collection<Book> books) throws Exception {
		bookCacheProxy.addToUnavailableBooks(books);
	}

	public void verifyUnavailableBooksCount(int expectedCount) throws Exception {
		Set<Book> bookSet = bookCacheProxy.getAllUnavailableBooks();
		verifyUnavailableBooksCount(bookSet, expectedCount);
	}

	public void verifyUnavailableBooksCount(Collection<Book> bookList, int expectedCount) throws Exception {
		Assert.notNull(bookList, "Result not found");
		if (expectedCount > 0)
			Assert.notNull(bookList, "UnavailableBooks records should exist");
		Assert.equals(expectedCount, bookList.size(), "UnavailableBooks record count not correct");
	}

}