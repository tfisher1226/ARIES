package bookshop2.supplier.data.supplierOrderCache;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
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
@ConcurrencyManagement(BEAN)
@TransactionAttribute(REQUIRED)
public class SupplierOrderCacheHelper {

	private boolean runningInContainer;
	
	private TransactionTestControl transactionTestControl;

	private CacheModuleTestControl cacheLayerTestControl;

	@Inject
	private SupplierOrderCache supplierOrderCache;

//	private SupplierOrderCacheImpl supplierOrderCache;

	private boolean runningAsClient;


	public void setProxy(SupplierOrderCache supplierOrderCache) {
		this.supplierOrderCache = supplierOrderCache;
	}

//	public void setJmxManager(JmxManager jmxManager) {
//		supplierOrderCache.setJmxManager(jmxManager);
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

	
//	public SupplierOrderCache createSupplierOrderCache() throws Exception {
//		supplierOrderCache = new SupplierOrderCacheImpl();
//		supplierOrderCache.initialize();
//		return supplierOrderCache;
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
	 * BooksInStock verification
	 */
	
//	protected void verifyBooksInStockCount(int expectedCount) throws Exception {
//		List<BooksInStockEntity> records = BooksInStockDao.getAllBooksList();
//		verifyBooksInStockCount(records, expectedCount);
//	}
//
//	protected void verifyBooksInStockCount(List<BooksInStockEntity> records, int expectedCount) throws Exception {
//		Assert.notNull(records, "Result not found");
//		if (expectedCount > 0)
//			Assert.notNull(records, "BooksInStock records should exist");
//		Assert.equals(records.size(), expectedCount, "BooksInStock record count not correct");
//	}
//
//	protected void verifyBooksInStockExists(BookEntity BooksInStock) throws Exception {
//		verifyBooksInStockExists(BooksInStock.getId());
//	}
//
//	protected void verifyBooksInStockExists(Long id) throws Exception {
//		BooksInStockEntity BooksInStock = BooksInStockDao.getBookById(id);
//		Assert.notNull(BooksInStock, "BooksInStock should exist");
//		Assert.equals(BooksInStock.getId(), id, "Ids should be same");
//	}
//
//	protected void verifyBooksInStockNotExists(BookEntity BooksInStock) throws Exception {
//		verifyBooksInStockNotExists(BooksInStock.getId());
//	}
//
//	protected void verifyBooksInStockNotExists(Long id) throws Exception {
//		BooksInStockEntity BooksInStock = BooksInStockDao.getBookById(id);
//		Assert.isNull(BooksInStock, "BooksInStock should not exist");
//	}
	
	
	/*
	 * BooksInStock management
	 */

	public void clearSupplierOrderCache() throws Exception {
		supplierOrderCache.removeAllBooksInStock();
	}

	public Collection<Book> addBookListToSupplierOrderCache() throws Exception {
		Set<Book> books = Bookshop2Fixture.createSet_Book();
		addBookListToSupplierOrderCache(books);
		return books;
	}

	public void addBookListToSupplierOrderCache(Collection<Book> books) throws Exception {
		supplierOrderCache.addToBooksInStock(Bookshop2Helper.createBookMap(books));
	}
	
	public void removeAllBooksInStock() throws Exception {
		//if (!runningAsClient)
			supplierOrderCache.removeAllBooksInStock();
		//else supplierOrderCache.removeAllBooksInStock();
	}

	public void addToBooksInStock() throws Exception {
		addToBooksInStock(2);
	}

	public void addToBooksInStock(int recordCount) throws Exception {
		Map<BookKey, Book> bookMap = Bookshop2Fixture.createMap_Book(recordCount);
		addToBooksInStock(bookMap);
	}

	public void addToBooksInStock(Book book) throws Exception {
		BookKey bookKey = Bookshop2Helper.createBookKey(book);
		addToBooksInStock(bookKey, book);
	}
	
	public void addToBooksInStock(BookKey bookKey, Book book) throws Exception {
		Map<BookKey, Book> bookMap = Bookshop2Fixture.createMap_Book(bookKey, book);
		supplierOrderCache.addToBooksInStock(bookMap);
	}

	public void addToBooksInStock(Collection<Book> bookList) throws Exception {
		Map<BookKey, Book> bookMap = Bookshop2Helper.createBookMap(bookList);
		supplierOrderCache.addToBooksInStock(bookMap);
	}
	
	public void addToBooksInStock(Map<BookKey, Book> bookMap) throws Exception {
		//if (!runningAsClient)
			supplierOrderCache.addToBooksInStock(bookMap);
		//else supplierOrderCache.addToBooksInStock(bookMap);
		//transactionTestControl.beginTransaction();
		//transactionTestControl.commitTransaction();
	}
	
	
	/*
	 * BooksInStock direct access
	 */
	
	public List<Book> getAllBooksInStock() throws Exception {
		//openEntityManager();
		//transactionTestControl.beginTransaction();
		List<Book> bookList = supplierOrderCache.getAllBooksInStock();
		//transactionTestControl.commitTransaction();
		//transactionTestControl.clearTransactions();
		//closeEntityManager();
		return bookList;
	}
	
	public Book getFromBooksInStock(BookKey bookKey) throws Exception {
		Book book = supplierOrderCache.getFromBooksInStock(bookKey);
		return book;
	}
	
	public void assureAddBooksInStock() throws Exception {
		assureAddBooksInStock(true);
	}
	
	public void assureAddBooksInStock(boolean commitAndClose) throws Exception {
		List<Book> bookList = Bookshop2Fixture.createList_Book();
		assureAddBooksInStock(bookList, commitAndClose);
	}

	public void assureAddBooksInStock(Collection<Book> bookList) throws Exception {
		assureAddBooksInStock(bookList, true);
	}
	
	public void assureAddBooksInStock(Collection<Book> bookList, boolean commitAndClose) throws Exception {
		Iterator<Book> iterator = bookList.iterator();
		while (iterator.hasNext()) {
			Book book = iterator.next();
			assureAddBooksInStock(book, commitAndClose);
		}
	}

	public void assureAddBooksInStock(Book book, boolean commitAndClose) throws Exception {
		//openEntityManager();
		//transactionTestControl.beginTransaction();
		BookKey bookKey = Bookshop2Helper.createBookKey(book);
		Map<BookKey, Book> bookMap = Bookshop2Fixture.createMap_Book(bookKey, book);
		supplierOrderCache.addToBooksInStock(bookMap);
		//if (commitAndClose) {
		//	transactionTestControl.commitTransaction();
		//	transactionTestControl.clearTransaction();
		//}
		//verifyBooksInStockExists(bookKey);
		if (commitAndClose) {
			//closeEntityManager();
		}
	}

	public void assureAddBooksInStockInProgress() throws Exception {
		//final List<Long> holder = new ArrayList<Long>();
		final Object mutex = new Object();
		Thread thread = new Thread(new Runnable() {
			public void run() {
				try {
					cacheLayerTestControl.resetContext();
					assureAddBooksInStock(false);
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
		//supplierOrderCache.removeAllBooksInStock();
		supplierOrderCache.removeAllBooksInStock();
		//transactionTestControl.commitTransaction();
		//transactionTestControl.clearTransaction();
		//closeEntityManager();
	}
	
	public void assureDeleteBooksInStock(BookKey bookKey) throws Exception {
		Book book = supplierOrderCache.getFromBooksInStock(bookKey);
		assureDeleteBooksInStock(book);
	}

	public void assureDeleteBooksInStock(Book book) throws Exception {
		//openEntityManager();
		//transactionTestControl.beginTransaction();
		BookKey bookKey = Bookshop2Helper.createBookKey(book);
		supplierOrderCache.removeFromBooksInStock(bookKey);
		//transactionTestControl.commitTransaction();
		//transactionTestControl.clearTransactions();
		verifyBooksInStockNotExists(bookKey);
		//closeEntityManager();
	}
	

	/*
	 * BooksInStock verification
	 */

	public void verifyBooksInStockCount(int expectedCount) throws Exception {
		//openEntityManager();
		List<Book> bookList = supplierOrderCache.getAllBooksInStock();
		verifyBooksInStockCount(bookList, expectedCount);
		//closeEntityManager();
	}

	public void verifyBooksInStockCount(Collection<Book> bookList, int expectedCount) throws Exception {
		Assert.notNull(bookList, "Result not found");
		if (expectedCount > 0)
			Assert.notNull(bookList, "BooksInStock records should exist");
		Assert.equals(expectedCount, bookList.size(), "BooksInStock record count not correct");
	}

	public void verifyBooksInStockExists(Book book) throws Exception {
		BookKey bookKey = Bookshop2Helper.createBookKey(book);
		verifyBooksInStockExists(bookKey);
	}

	public void verifyBooksInStockExists(BookKey bookKey) throws Exception {
		//openEntityManager();
		Book book = supplierOrderCache.getFromBooksInStock(bookKey);
		Assert.notNull(book, "Book should exist");
		BookKey existingBookKey = Bookshop2Helper.createBookKey(book);
		Assert.equals(existingBookKey, bookKey, "Book keys should be same");
		//closeEntityManager();
	}

//	protected void verifyBooksInStockExists(List<Long> ids) throws Exception {
//		Iterator<Long> iterator = ids.iterator();
//		while (iterator.hasNext()) {
//			Long id = iterator.next();
//			verifyBooksInStockExists(id);
//		}
//	}
	
	public void verifyBooksInStockNotExists(Book book) throws Exception {
		BookKey bookKey = Bookshop2Helper.createBookKey(book);
		verifyBooksInStockNotExists(bookKey);
	}

	public void verifyBooksInStockNotExists(BookKey bookKey) throws Exception {
		//openEntityManager();
		Book book = supplierOrderCache.getFromBooksInStock(bookKey);
		Assert.isNull(book, "Book should not exist");
		//closeEntityManager();
	}

}