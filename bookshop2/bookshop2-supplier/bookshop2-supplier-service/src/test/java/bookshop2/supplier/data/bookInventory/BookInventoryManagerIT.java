package bookshop2.supplier.data.bookInventory;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transaction;

import org.aries.Assert;
import org.aries.common.AbstractTestCase;
import org.aries.runtime.RequestContext;
import org.aries.tx.BytemanHelper;
import org.aries.tx.BytemanRule;
import org.aries.tx.BytemanUtil;
import org.aries.tx.ServiceModuleTestControl;
import org.aries.tx.TransactionParticipantManager;
import org.aries.tx.TransactionTestControl;
import org.aries.tx.Transactional;
import org.aries.util.FieldUtil;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import bookshop2.Book;
import bookshop2.supplier.BookInventoryRepository;
import bookshop2.supplier.BookInventoryRepositoryImpl;
import bookshop2.supplier.SupplierContext;
import bookshop2.supplier.SupplierDataTestFixture;
import bookshop2.supplier.bean.ReservedBooksManager;
import bookshop2.supplier.bean.ReservedBooksManagerImpl;
import bookshop2.supplier.entity.ReservedBooksEntity;
import bookshop2.util.Bookshop2Fixture;


//@RunWith(BMUnitRunner.class)
//@RunWith(MockitoJUnitRunner.class)
public class BookInventoryManagerIT extends AbstractTestCase {

	private static TransactionTestControl transactionControl;

	private static ServiceModuleTestControl bookInventoryControl;

	private BookInventory bookInventory;
	
	private BookInventoryManager bookInventoryManager;

	private BookInventoryProcessor bookInventoryProcessor;

	private BookInventoryRepository bookInventoryRepository;

	private BookInventoryHelper bookInventoryHelper;
	
	private ReservedBooksManager reservedBooksManager;
	
	@Rule 
	public BytemanHelper byteman = BytemanHelper.create(BookInventoryManagerIT.class);
	
	
//	protected void setupByteman(String methodName) throws Exception {
//		BytemanUtil.loadScripts(getClass(), methodName);
//	}
//
//	protected void tearDownByteman(String methodName) throws Exception {
//		BytemanUtil.unloadScripts(getClass(), methodName);
//	}
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		try {
			BytemanUtil.initialize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		createTransactionControl();
		createBookInventoryControl();
	}

	protected static void createTransactionControl() throws Exception {
		transactionControl = new TransactionTestControl();
		transactionControl.setupTransactionManager();
	}
	
	protected static void createBookInventoryControl() throws Exception {
		bookInventoryControl = new ServiceModuleTestControl(transactionControl);
		bookInventoryControl.setDatabaseName("bookshop2_supplier_db");
		bookInventoryControl.setDataSourceName("bookshop2_supplier_ds");
		bookInventoryControl.setPersistenceUnitName("bookInventory");
		bookInventoryControl.setupDataLayer();
	}
	
	@AfterClass
	public static void afterCLass() throws Exception {
		transactionControl.tearDown();
		bookInventoryControl.tearDown();
	}
	
	@Before
	public void setUp() throws Exception {
		//super.setUp();
		transactionControl.setUp();
		bookInventoryControl.setUp();
		createBookInventoryHelper();
		createBookInventoryManager();
		bookInventoryControl.startServices();
	}

	@After
	public void tearDown() throws Exception {
		bookInventoryHelper = null;
		bookInventoryManager = null;
		bookInventoryControl.stopServices();
		bookInventoryControl.tearDown();
		transactionControl.tearDown();
		//super.tearDown();
	}

	
//	protected void openEntityManager() throws Exception {
//		FieldUtil.setFieldValue(reservedBooksManager, "entityManager", entityManager);
//	}

//	protected void closeEntityManager() throws Exception { 
//		bookInventoryControl.resetEntityManager();
//	}
	
	public RequestContext createRequestContext() {
		RequestContext requestContext = new RequestContext();
		return requestContext;
	}
	
//	public SupplierContext createSupplierContext() {
//		SupplierContext supplierContext = new SupplierContext();
//		return supplierContext;
//	}
	
	protected void createBookInventoryHelper() throws Exception {
		bookInventoryHelper = new BookInventoryHelper();
		EntityManager createEntityManager = bookInventoryControl.createEntityManager();
		FieldUtil.setFieldValue(bookInventoryHelper, "entityManager", createEntityManager);
		bookInventoryHelper.initialize(bookInventoryControl);
	}

	public void createBookInventoryManager() throws Exception {
		bookInventoryManager = new BookInventoryManager();
		FieldUtil.setFieldValue(bookInventoryManager, "requestContext", createRequestContext());
		FieldUtil.setFieldValue(bookInventoryManager, "bookInventory", createBookInventory());
		FieldUtil.setFieldValue(bookInventoryManager, "stateProcessor", createBookInventoryProcessor());
	}

	public BookInventory createBookInventory() throws Exception {
		bookInventory = new BookInventory();
		FieldUtil.setFieldValue(bookInventory, "bookInventoryRepository", createBookInventoryRepository());
		return bookInventory;
	}

	public BookInventoryProcessor createBookInventoryProcessor() throws Exception {
		bookInventoryProcessor = new BookInventoryProcessor();
		return bookInventoryProcessor;
	}
	
	public BookInventoryRepository createBookInventoryRepository() throws Exception {
		bookInventoryRepository = new BookInventoryRepositoryImpl();
		FieldUtil.setFieldValue(bookInventoryRepository, "reservedBooksManager", createReservedBooksManager());
		return bookInventoryRepository;
	}

	public ReservedBooksManager createReservedBooksManager() throws Exception {
		reservedBooksManager = new ReservedBooksManagerImpl();
		EntityManager entityManager = bookInventoryControl.createEntityManager();
		FieldUtil.setFieldValue(reservedBooksManager, "entityManager", entityManager);
		FieldUtil.setFieldValue(reservedBooksManager, "reservedBooksDao", SupplierDataTestFixture.createReservedBooksDao());
		FieldUtil.setFieldValue(reservedBooksManager, "reservedBooksMapper", SupplierDataTestFixture.getReservedBooksMapper());
		reservedBooksManager.initialize();		
		return reservedBooksManager;
	}
	
	protected ReservedBooksEntity createReservedBooksEntity() throws Exception {
		ReservedBooksEntity entity = bookInventoryHelper.createReservedBooksEntity();
		return entity;
	}
	
	protected Transactional enrollGlobalTransaction(BookInventoryManager stateManager, String transactionId) {
		Transactional transactional = bookInventoryControl.createMockTransactional(stateManager);
		enrollTransaction("ReserveBooks", transactionId, transactional);
		return transactional;
	}

	protected void enrollTransaction(String transactionName, String transactionId, Transactional transactional) {
		TransactionParticipantManager.getInstance().enrollTransaction(transactionName, transactionId, transactional);
	}


	@Test
	//@Ignore
	public void testReserveBooks_TxCommit() throws Exception {
		String testName = "testReserveBooks_TxCommit";
		
		bookInventoryHelper.assureRemoveAll();
		transactionControl.beginTransaction();

		List<Book> expectedReservedBooks = Bookshop2Fixture.createList_Book();

		try {
			Transaction transaction = transactionControl.getTransactionManager().getTransaction();
			bookInventoryManager.addToReservedBooks(expectedReservedBooks);
			transactionControl.commitTransaction();
			transactionControl.assertCommitted();

		} catch (Exception e) {
			e.printStackTrace();
			transactionControl.rollbackTransaction();
		}
		
		Bookshop2Fixture.assertSameBook(expectedReservedBooks, bookInventoryHelper.getAllReservedBooks());
		bookInventoryHelper.verifyReservedBooksCount(expectedReservedBooks.size());
	}

	@Test
	//@Ignore
	@BytemanRule(name = "rule1",
			targetClass = "BookInventoryManager",
			targetMethod = "addToReservedBooks",
			targetLocation = "AT ENTRY",
			action = "throw new java.lang.RuntimeException(\"error1\")")
	public void testReserveBooks_TxRollback() throws Exception {
		String testName = "testReserveBooks_TxRollback";
		
		bookInventoryHelper.assureRemoveAll();
		transactionControl.beginTransaction();
			
		List<Book> expectedReservedBooks = Bookshop2Fixture.createList_Book();

		try {
			Transaction transaction = transactionControl.getTransactionManager().getTransaction();
			bookInventoryManager.addToReservedBooks(expectedReservedBooks);
			fail("Exception should be thrown");
			
		} catch (Exception e) {
			Assert.equals(e.getMessage(), "error1");
			transactionControl.rollbackTransaction();
			transactionControl.assertRolledBack();
		}
		
		List<Book> bookList = bookInventoryHelper.getAllReservedBooks();
		Assert.isEmpty(bookList);
		bookInventoryHelper.verifyReservedBooksCount(0);
	}
	
	@Test
	@Ignore
	public void testReserveBooks_GlobalTxCommit() throws Exception {
		bookInventoryHelper.assureRemoveAll();

		String transactionId = transactionControl.beginGlobalTransaction();
		Transactional transactional = enrollGlobalTransaction(bookInventoryManager, transactionId);
		List<Book> expectedReservedBooks = Bookshop2Fixture.createList_Book();

		try {
			//bookInventoryManager.setTransactionId(transactionId);
			bookInventoryManager.addToReservedBooks(expectedReservedBooks);
			transactionControl.commitGlobalTransaction();
			transactionControl.assertCommitted(transactional);

		} catch (Exception e) {
			e.printStackTrace();
			transactionControl.rollbackGlobalTransaction();
			transactionControl.assertRolledBack(transactional);
		}
		
		//control.assertCommitted();
		Bookshop2Fixture.assertSameBook(expectedReservedBooks, bookInventoryManager.getAllReservedBooks());
		bookInventoryHelper.verifyReservedBooksCount(expectedReservedBooks.size());
	}

	@Test
	@Ignore
	@BytemanRule(name = "rule2",
			targetClass = "BookInventoryManager",
			targetMethod = "addToReservedBooks",
			targetLocation = "AT EXIT",
			action = "throw new java.lang.RuntimeException(\"error2\")"
	)
	public void testReserveBooks_GlobalTxRollback() throws Exception {
		bookInventoryHelper.assureRemoveAll();
		
		String transactionId = transactionControl.beginGlobalTransaction();
		Transactional transactional = enrollGlobalTransaction(bookInventoryManager, transactionId);
		List<Book> expectedReservedBooks = Bookshop2Fixture.createList_Book();

		try {
			//bookInventoryManager.setTransactionId(transactionId);
			bookInventoryManager.addToReservedBooks(expectedReservedBooks);
			fail("Exception should be thrown");
			
		} catch (Exception e) {
			Assert.equals(e.getMessage(), "error2");
			transactionControl.rollbackGlobalTransaction();
			transactionControl.assertRolledBack(transactional);
		}
		
		Assert.isEmpty(bookInventoryManager.getAllReservedBooks());
		bookInventoryHelper.verifyReservedBooksCount(0);
	}
	
	

//	protected Long assureAddReservedBooks() throws Exception {
//		BookEntity bookOrdersEntity = createBookEntity();
//		Long id = assureAddReservedBooks(bookOrdersEntity);
//		return id;
//	}
//
//	protected Long assureAddReservedBooks(BookEntity bookOrders) throws Exception {
//		openEntityManager();
//		beginTransaction();
//		Long id = reservedBooksDao.addReservedBooks(bookOrders);
//		commitTransaction();
//		verifyReservedBooksExists(id);
//		closeEntityManager();
//		return id;
//	}
//
//	protected void assureDeleteReservedBooks(Long id) throws Exception {
//		BookEntity bookOrders = reservedBooksDao.getReservedBooksById(id);
//		assureDeleteReservedBooks(bookOrders);
//	}
//
//	protected void assureDeleteReservedBooks(BookEntity bookOrders) throws Exception {
//		openEntityManager();
//		beginTransaction();
//		reservedBooksDao.deleteReservedBooks(bookOrders);
//		commitTransaction();
//		verifyReservedBooksNotExists(bookOrders);
//		closeEntityManager();
//	}
//	
//	
//	protected void verifyReservedBooksCount(int expectedCount) throws Exception {
//		List<BookEntity> reservedBooks = reservedBooksDao.getAllReservedBooksRecords();
//		verifyReservedBooksCount(reservedBooks, expectedCount);
//	}
//
//	protected void verifyReservedBooksCount(Collection<BookEntity> reservedBooks, int expectedCount) throws Exception {
//		Assert.notNull(reservedBooks, "Result not found");
//		if (expectedCount > 0)
//			Assert.notNull(reservedBooks, "ReservedBooks records should exist");
//		Assert.equals(expectedCount, reservedBooks.size(), "ReservedBooks record count not correct");
//	}
//
//	protected void verifyReservedBooksExists(BookEntity reservedBooks) throws Exception {
//		verifyReservedBooksExists(reservedBooks.getId());
//	}
//
//	protected void verifyReservedBooksExists(Long id) throws Exception {
//		BookEntity bookOrders = reservedBooksDao.getReservedBooksById(id);
//		Assert.notNull(bookOrders, "ReservedBooks should exist");
//		Assert.equals(bookOrders.getId(), id, "Ids should be same");
//	}
//
//	protected void verifyReservedBooksNotExists(BookEntity reservedBooks) throws Exception {
//		verifyReservedBooksNotExists(reservedBooks.getId());
//	}
//
//	protected void verifyReservedBooksNotExists(Long id) throws Exception {
//		BookEntity reservedBooks = reservedBooksDao.getReservedBooksById(id);
//		Assert.isNull(reservedBooks, "ReservedBooks should not exist");
//	}
	
}
