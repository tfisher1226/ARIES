package bookshop2.supplier.data.bookOrderLog;

import java.util.List;

import javax.persistence.EntityManager;

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

import bookshop2.Order;
import bookshop2.supplier.BookOrderLogRepository;
import bookshop2.supplier.BookOrderLogRepositoryImpl;
import bookshop2.supplier.SupplierContext;
import bookshop2.supplier.SupplierDataTestFixture;
import bookshop2.supplier.bean.BookOrdersManager;
import bookshop2.supplier.bean.BookOrdersManagerImpl;
import bookshop2.supplier.entity.BookOrdersEntity;
import bookshop2.util.Bookshop2Fixture;


//@RunWith(MockitoJUnitRunner.class)
public class BookOrderLogManagerIT extends AbstractTestCase {

	private static TransactionTestControl transactionControl;

	private static ServiceModuleTestControl bookOrderLogControl;
	
	private BookOrderLog bookOrderLog;
	
	private BookOrderLogHelper bookOrderLogHelper;
	
	private BookOrderLogManager bookOrderLogManager;
	
	private BookOrderLogRepository bookOrderLogRepository;
	
	private BookOrdersManager bookOrdersManager;
	
	@Rule 
	public BytemanHelper byteman = BytemanHelper.create(BookOrderLogManagerIT.class);
	

	@BeforeClass
	public static void beforeClass() throws Exception {
		BytemanUtil.initialize();
		createTransactionControl();
		createBookInventoryControl();
	}

	protected static void createTransactionControl() throws Exception {
		transactionControl = new TransactionTestControl();
		transactionControl.setupTransactionManager();
	}
	
	protected static void createBookInventoryControl() throws Exception {
		bookOrderLogControl = new ServiceModuleTestControl(transactionControl);
		bookOrderLogControl.setDatabaseName("bookshop2_supplier_db");
		bookOrderLogControl.setDataSourceName("bookshop2_supplier_ds");
		bookOrderLogControl.setPersistenceUnitName("bookOrderLog");
		bookOrderLogControl.setupDataLayer();
	}
	
	@AfterClass
	public static void afterCLass() throws Exception {
		transactionControl.tearDown();
		bookOrderLogControl.tearDown();
	}
	
	@Before
	public void setUp() throws Exception {
		transactionControl.setUp();
		bookOrderLogControl.setUp();
		createBookOrderLogManager();
		createBookOrderLogHelper();
		bookOrderLogControl.startServices();
	}

	@After
	public void tearDown() throws Exception {
		bookOrderLogHelper = null;
		bookOrderLogManager = null;
		bookOrderLogControl.stopServices();
		bookOrderLogControl.tearDown();
		transactionControl.tearDown();
	}

//	protected void openEntityManager() throws Exception {
//	FieldUtil.setFieldValue(bookOrdersDao, "em", bookOrderLogControl.createEntityManager());
//}

//	protected void closeEntityManager() throws Exception { 
//		bookOrderLogControl.resetEntityManager();
//	}
	
//	public SupplierContext createSupplierContext() {
//		SupplierContext supplierContext = new SupplierContext();
//		return supplierContext;
//	}
	
	public RequestContext createRequestContext() {
		RequestContext requestContext = new RequestContext();
		return requestContext;
	}
	
	protected void createBookOrderLogHelper() throws Exception {
		bookOrderLogHelper = new BookOrderLogHelper();
		EntityManager createEntityManager = bookOrderLogControl.createEntityManager();
		FieldUtil.setFieldValue(bookOrderLogHelper, "entityManager", createEntityManager);
		bookOrderLogHelper.initialize(bookOrderLogControl);
	}
	
	public void createBookOrderLogManager() throws Exception {
		bookOrderLogManager = new BookOrderLogManager();
		FieldUtil.setFieldValue(bookOrderLogManager, "requestContext", createRequestContext());
		FieldUtil.setFieldValue(bookOrderLogManager, "bookOrderLog", createBookOrderLog());
	}
	
	public BookOrderLog createBookOrderLog() throws Exception {
		bookOrderLog = new BookOrderLog();
		FieldUtil.setFieldValue(bookOrderLog, "bookOrderLogRepository", createBookOrderLogRepository());
		return bookOrderLog;
	}
	
	public BookOrderLogRepository createBookOrderLogRepository() throws Exception {
		bookOrderLogRepository = new BookOrderLogRepositoryImpl();
		FieldUtil.setFieldValue(bookOrderLogRepository, "bookOrdersManager", createBooksOrdersManager());
		return bookOrderLogRepository;
	}
	
	public BookOrdersManager createBooksOrdersManager() throws Exception {
		bookOrdersManager = new BookOrdersManagerImpl();
		EntityManager entityManager = bookOrderLogControl.createEntityManager();
		FieldUtil.setFieldValue(bookOrdersManager, "entityManager", entityManager);
		FieldUtil.setFieldValue(bookOrdersManager, "bookOrdersDao", SupplierDataTestFixture.createBookOrdersDao());
		FieldUtil.setFieldValue(bookOrdersManager, "bookOrdersMapper", SupplierDataTestFixture.getBookOrdersMapper());
		bookOrdersManager.initialize();	
		return bookOrdersManager;
	}
	
	protected BookOrdersEntity createBookOrdersEntity() throws Exception {
		BookOrdersEntity entity = bookOrderLogHelper.createBookOrdersEntity();
		return entity;
	}

	
	@Test
	//@Ignore
	public void testReserveBooks_TxCommit() throws Exception {
		String testName = "testReserveBooks_TxCommit";
		log.info(testName+": started");
		
		bookOrderLogHelper.assureRemoveAll();
		transactionControl.beginTransaction();

		List<Order> expectedBookOrders = Bookshop2Fixture.createList_Order();

		try {
			bookOrderLogManager.addToBookOrders(expectedBookOrders);
			transactionControl.commitTransaction();
			transactionControl.assertCommitted();

		} catch (Exception e) {
			e.printStackTrace();
			transactionControl.rollbackTransaction();
		}
		
		Bookshop2Fixture.assertSameOrder(expectedBookOrders, bookOrderLogManager.getAllBookOrders());
		bookOrderLogHelper.verifyBookOrdersCount(expectedBookOrders.size());
		log.info(testName+": done");
	}
	
	@Test
	//@Ignore
	@BytemanRule(name = "rule1",
			targetClass = "BookOrderLogManager",
			targetMethod = "addToBookOrders",
			targetLocation = "AT EXIT",
			action = "throw new java.lang.RuntimeException(\"error1\")"
	)
	public void testReserveBooks_TxRollback() throws Exception {
		String testName = "testReserveBooks_TxRollback";
		log.info(testName+": started");
		
		bookOrderLogHelper.assureRemoveAll();
		transactionControl.beginTransaction();
			
		List<Order> expectedBookOrders = Bookshop2Fixture.createList_Order();

		try {
			bookOrderLogManager.addToBookOrders(expectedBookOrders);
			fail("Exception should be thrown");
			
		} catch (Exception e) {
			Assert.equals(e.getMessage(), "error1");
			transactionControl.rollbackTransaction();
			transactionControl.assertRolledBack();
		}
		
		Assert.isEmpty(bookOrderLogManager.getAllBookOrders());
		bookOrderLogHelper.verifyBookOrdersCount(0);
		log.info(testName+": done");
	}
	
	@Test
	@Ignore
	public void testAddReservedBooks_GlobalTxCommit() throws Exception {
		bookOrderLogControl.assureDeleteAll();
		
		String transactionId = transactionControl.beginGlobalTransaction();
		Transactional transactional = transactionControl.createMockTransactional(bookOrderLogManager);
		TransactionParticipantManager.getInstance().enroll(transactional, transactionId);

		List<Order> expectedBookOrders = Bookshop2Fixture.createList_Order();

		try {
			//bookOrderLogManager.setTransactionId(transactionId);
			bookOrderLogManager.addToBookOrders(expectedBookOrders);
			bookOrderLogManager.updateState();
			transactionControl.commitGlobalTransaction();
			transactionControl.assertCommitted(transactional);

		} catch (Exception e) {
			e.printStackTrace();
			transactionControl.rollbackGlobalTransaction();
			transactionControl.assertRolledBack(transactional);
		}
		
		Bookshop2Fixture.assertSameOrder(expectedBookOrders, bookOrderLogManager.getAllBookOrders());
		bookOrderLogHelper.verifyBookOrdersCount(1);
	}

	@Test
	@Ignore
	@BytemanRule(name = "rule2",
		targetClass = "BookInventoryManager",
		targetMethod = "addToReservedBooks",
		targetLocation = "AT EXIT",
		action = "throw new java.lang.RuntimeException(\"error2\")")
	public void testReserveBooks_GlobalTxRollback() throws Exception {
		try {
			bookOrderLogControl.assureDeleteAll();
			
			String transactionId = transactionControl.beginGlobalTransaction();
			Transactional transactional = transactionControl.createMockTransactional(bookOrderLogManager);
			TransactionParticipantManager.getInstance().enroll(transactional, transactionId);

			List<Order> expectedBookOrders = Bookshop2Fixture.createList_Order();

			try {
				bookOrderLogManager.addToBookOrders(expectedBookOrders);
				bookOrderLogManager.updateState();
				transactionControl.rollbackGlobalTransaction();
				transactionControl.assertRolledBack(transactional);

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			Assert.isEmpty(bookOrderLogManager.getAllBookOrders());
			transactionControl.assertRolledBack(transactional);
			bookOrderLogHelper.verifyBookOrdersCount(0);
			
		} catch (Exception e) {
			e.printStackTrace();
			transactionControl.rollbackGlobalTransaction();
		}
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
