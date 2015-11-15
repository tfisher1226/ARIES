package bookshop2.seller.data.bookCache;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import org.aries.Assert;
import org.aries.tx.AbstractArquillianTest;
import org.aries.tx.AbstractCacheUnitArquillionTest;
import org.aries.tx.BytemanRule;
import org.aries.tx.CacheModuleTestControl;
import org.aries.tx.TransactionTestControl;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import bookshop2.Book;
import bookshop2.seller.SellerTestEARBuilder;
import bookshop2.seller.SellerTestWARBuilder;
import bookshop2.util.Bookshop2Fixture;
import bookshop2.util.Bookshop2Helper;

import common.tx.state.AbstractStateManager;


@RunWith(Arquillian.class)
public class BookCacheManagerCIT extends AbstractCacheUnitArquillionTest {

//	@Rule 
//	public BytemanRule byteman = BytemanRule.create(BookCacheManagerCIT.class);

	@Inject
	private BookCacheManager bookCacheManager;

	@Inject
	private BookCacheHelper bookCacheHelper;

	
	@BeforeClass
	public static void beforeClass() throws Exception {
		AbstractArquillianTest.beforeClass();
	}
	
	@AfterClass
	public static void afterClass() throws Exception {
		AbstractArquillianTest.afterClass();
	}
	
	@Override
	public Class<?> getTestClass() {
		return BookCacheManagerCIT.class;
	}
	
	@Override
	public String getServerName() {
		return "hornetQ01_local";
	}
	
	@Override
	public String getDomainId() {
		return "bookshop2.seller";
	}
	
	@Override
	public String getTargetArchive() {
		return SellerTestEARBuilder.NAME;
	}
	
	@Override
	public AbstractStateManager<?> getStateManager() {
		return bookCacheManager;
	}

	@Before
	public void setUp() throws Exception {
		if (!isServerStarted()) {
			startServer();
		} else {
			super.setUp();
			createTransactionControl();
			initializeBookCacheHelper();
		}
	}

	protected void createTransactionControl() throws Exception {
		transactionTestControl = new TransactionTestControl();
		transactionTestControl.assureTransactionManager();
	}
	
	protected void initializeBookCacheHelper() throws Exception {
		bookCacheHelper.setProxy(createBookCacheProxy());
		bookCacheHelper.initialize(createBookCacheControl());
	}
	
	protected BookCacheProxy createBookCacheProxy() throws Exception {
		BookCacheProxy bookCacheProxy = new BookCacheProxy();
		bookCacheProxy.setJmxManager(jmxManager);
		return bookCacheProxy;
	}
	
	protected CacheModuleTestControl createBookCacheControl() throws Exception {
		CacheModuleTestControl bookCacheControl = new CacheModuleTestControl(transactionTestControl);
		bookCacheControl.setupCacheLayer();
		return bookCacheControl;
	}
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
		clearTransaction();
	}
	
	@TargetsContainer("hornetQ01_local")
	@Deployment(name = "sellerEAR", order = 1)
	public static EnterpriseArchive createSellerEAR() {
		SellerTestEARBuilder builder = new SellerTestEARBuilder();
		builder.addAsModule(createSellerWAR(), "seller");
		EnterpriseArchive ear = builder.createEAR();
		return ear;
	}

	public static WebArchive createSellerWAR() {
		SellerTestWARBuilder builder = new SellerTestWARBuilder("test.war");
		builder.addClass(BookCacheManagerCIT.class);
		WebArchive war = builder.create();
		return war;
	}

	@Test
	//@Ignore
	@InSequence(value = 1)
	public void runTest_BookOrders_GetAllAsList_success() throws Exception {
		String testName = "runTest_BookOrders_GetAllAsList_success";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// prepare test data
		int bookCount = 4;
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book(bookCount);
		bookCacheHelper.addToBookOrders(bookListToAdd);
		
		// execute action
		List<Book> bookList = runAction_BookOrders_GetAllAsList();

		// validate cache state
		Assert.equals(bookCount, bookList.size(), "BookOrders record count should be correct");
		Bookshop2Fixture.assertSameBook(bookListToAdd, bookList, "BookOrders records should be equal");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 2)
	public void runTest_BookOrders_GetAllAsList_tx_commit() throws Exception {
		String testName = "runTest_BookOrders_GetAllAsList_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();

		// prepare test data
		int bookCount = 4;
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book(bookCount);
		bookCacheHelper.addToBookOrders(bookListToAdd);
		
		// execute action
		List<Book> bookList = runAction_BookOrders_GetAllAsList();

		// validate intermediate cache state
		Assert.isEmpty(bookList, "No BookOrders records should exist");
		
		// commit transaction
		commitJTATransaction();

		// execute action again
		bookList = runAction_BookOrders_GetAllAsList();

		// validate cache state
		Assert.equals(bookCount, bookList.size(), "BookOrders record count should be correct");
		Bookshop2Fixture.assertSameBook(bookListToAdd, bookList, "BookOrders records should be equal");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 3)
	public void runTest_BookOrders_GetAllAsList_tx_rollback() throws Exception {
		String testName = "runTest_BookOrders_GetAllAsList_tx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();
		
		// prepare test data
		int bookCount = 4;
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book(bookCount);
		bookCacheHelper.addToBookOrders(bookListToAdd);
		
		// execute action
		List<Book> bookList = runAction_BookOrders_GetAllAsList();

		// validate intermediate cache state
		Assert.isEmpty(bookList, "No BookOrders records should exist");
		
		// rollback transaction
		rollbackJTATransaction();

		// execute action again
		bookList = runAction_BookOrders_GetAllAsList();

		// validate cache state
		Assert.isEmpty(bookList, "No BookOrders records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 4)
	public void runTest_BookOrders_GetAllAsList_utx_commit() throws Exception {
		String testName = "runTest_BookOrders_GetAllAsList_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();
		
		// prepare test data
		int bookCount = 4;
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book(bookCount);
		bookCacheHelper.addToBookOrders(bookListToAdd);
		
		// execute action
		List<Book> bookList = runAction_BookOrders_GetAllAsList();

		// validate intermediate cache state
		Assert.isEmpty(bookList, "No BookOrders records should exist");
		
		// commit transaction
		commitUserTransaction();

		// execute action again
		bookList = runAction_BookOrders_GetAllAsList();

		// validate cache state
		Assert.equals(bookCount, bookList.size(), "BookOrders record count should be correct");
		Bookshop2Fixture.assertSameBook(bookListToAdd, bookList, "BookOrders records should be equal");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 5)
	public void runTest_BookOrders_GetAllAsList_utx_rollback() throws Exception {
		String testName = "runTest_BookOrders_GetAllAsList_utx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();

		// prepare test data
		int bookCount = 4;
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book(bookCount);
		bookCacheHelper.addToBookOrders(bookListToAdd);
		
		// execute action
		List<Book> bookList = runAction_BookOrders_GetAllAsList();

		// validate intermediate cache state
		Assert.isEmpty(bookList, "No BookOrders records should exist");
		
		// rollback transaction
		rollbackUserTransaction();

		// execute action again
		bookList = runAction_BookOrders_GetAllAsList();

		// validate cache state
		Assert.isEmpty(bookList, "No BookOrders records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public <T> T runAction_BookOrders_GetAllAsList() throws Exception {
		return runAction(new Callable<T>() {
			@SuppressWarnings("unchecked")
			public T call() {
				return (T) bookCacheManager.getAllBookOrders();
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 6)
	public void runTest_BookOrders_GetMatchingAsList_success() throws Exception {
		String testName = "runTest_BookOrders_GetMatchingAsList_success";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 7)
	public void runTest_BookOrders_GetMatchingAsList_tx_commit() throws Exception {
		String testName = "runTest_BookOrders_GetMatchingAsList_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();
		
		
		// commit transaction
		commitJTATransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 8)
	public void runTest_BookOrders_GetMatchingAsList_tx_rollback() throws Exception {
		String testName = "runTest_BookOrders_GetMatchingAsList_tx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();
		
		
		// rollback transaction
		rollbackJTATransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 9)
	public void runTest_BookOrders_GetMatchingAsList_utx_commit() throws Exception {
		String testName = "runTest_BookOrders_GetMatchingAsList_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();
		
		
		// commit transaction
		commitUserTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 10)
	public void runTest_BookOrders_GetMatchingAsList_utx_rollback() throws Exception {
		String testName = "runTest_BookOrders_GetMatchingAsList_utx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();
		
		
		// rollback transaction
		rollbackUserTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public <T> T runAction_BookOrders_GetMatchingAsList(final Collection<Book> bookList) throws Exception {
		return runAction(new Callable<T>() {
			@SuppressWarnings("unchecked")
			public T call() {
				return (T) bookCacheManager.getMatchingBookOrders(bookList);
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 11)
	public void runTest_BookOrders_AddAsItem_success() throws Exception {
		String testName = "runTest_BookOrders_AddAsItem_success";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// prepare test data
		Book bookToAdd = Bookshop2Fixture.create_Book();
		
		// execute action
		runAction_BookOrders_AddAsItem(bookToAdd);
		
		// validate cache state
		List<Book> bookList = bookCacheHelper.getAllBookOrders();
		Assert.equals(1, bookList.size(), "Only 1 BookOrders record should exist");
		Bookshop2Fixture.assertSameBook(bookToAdd, bookList.iterator().next());
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 12)
	public void runTest_BookOrders_AddAsItem_tx_commit() throws Exception {
		String testName = "runTest_BookOrders_AddAsItem_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();

		// prepare test data
		Book bookToAdd = Bookshop2Fixture.create_Book();
		
		// execute action
		runAction_BookOrders_AddAsItem(bookToAdd);
		
		// commit transaction
		commitJTATransaction();

		// validate cache state
		List<Book> bookList = bookCacheHelper.getAllBookOrders();
		Assert.equals(1, bookList.size(), "Only 1 BookOrders record should exist");
		Bookshop2Fixture.assertSameBook(bookToAdd, bookList.iterator().next());
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 13)
	public void runTest_BookOrders_AddAsItem_utx_commit() throws Exception {
		String testName = "runTest_BookOrders_AddAsItem_utx_commit";
		log.info(testName+": started");

		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();

		// prepare test data
		Book bookToAdd = Bookshop2Fixture.create_Book();
		
		// execute action
		runAction_BookOrders_AddAsItem(bookToAdd);
		
		// commit transaction
		commitUserTransaction();
		
		// validate cache state
		List<Book> bookList = bookCacheHelper.getAllBookOrders();
		Assert.equals(1, bookList.size(), "Only 1 BookOrders record should exist");
		Bookshop2Fixture.assertSameBook(bookToAdd, bookList.iterator().next());

		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 14)
	@BytemanRule(name = "rule14", 
			targetClass = "BookCacheManager",
			targetMethod = "addToBookOrders",
			targetLocation = "AT EXIT",
			action = "throw new org.aries.ApplicationFailure(\"error14\")")
	public void runTest_BookOrders_AddAsItem_manager_exception_rollback() throws Exception {
		String testName = "runTest_BookOrders_AddAsItem_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookCacheHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;
				
		// prepare test data
		Book bookToAdd = Bookshop2Fixture.create_Book();
		
		try {
		// execute action
			runAction_BookOrders_AddAsItem(bookToAdd);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error14");
		
			// validate cache state
			List<Book> bookList = bookCacheHelper.getAllBookOrders();
			Assert.isEmpty(bookList, "No BookOrders records should exist");

		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}

		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}

	@Test
	//@Ignore
	@InSequence(value = 15)
	@BytemanRule(name = "rule15", 
			targetClass = "BookCacheImpl",
			targetMethod = "addToBookOrders",
			targetLocation = "AT EXIT",
			action = "throw new org.aries.ApplicationFailure(\"error15\")")
	public void runTest_BookOrders_AddAsItem_cache_exception_rollback() throws Exception {
		String testName = "runTest_BookOrders_AddAsItem_cache_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookCacheHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;
						
		// prepare test data
		Book bookToAdd = Bookshop2Fixture.create_Book();
		
		try {
		// execute action
			runAction_BookOrders_AddAsItem(bookToAdd);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error15");
		
			// validate cache state
			List<Book> bookList = bookCacheHelper.getAllBookOrders();
			Assert.isEmpty(bookList, "No BookOrders records should exist");
		
		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
				
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runAction_BookOrders_AddAsItem(final Book book) throws Exception {
		runAction(new Runnable() {
			public void run() {
				bookCacheManager.addToBookOrders(book);
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 16)
	public void runTest_BookOrders_AddAsList_success() throws Exception {
		String testName = "runTest_BookOrders_AddAsList_success";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// prepare test data
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book();
		
		// execute action
		runAction_BookOrders_AddAsList(bookListToAdd);
		
		// validate cache state
		List<Book> bookList = bookCacheHelper.getAllBookOrders();
		Bookshop2Fixture.assertSameBook(bookListToAdd, bookList);
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 17)
	public void runTest_BookOrders_AddAsList_tx_commit() throws Exception {
		String testName = "runTest_BookOrders_AddAsList_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();

		// prepare test data
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book();
		
		// execute action
		runAction_BookOrders_AddAsList(bookListToAdd);
		
		// commit transaction
		commitJTATransaction();

		// validate cache state
		List<Book> bookList = bookCacheHelper.getAllBookOrders();
		Bookshop2Fixture.assertSameBook(bookListToAdd, bookList);
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 18)
	public void runTest_BookOrders_AddAsList_utx_commit() throws Exception {
		String testName = "runTest_BookOrders_AddAsList_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();

		// prepare test data
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book();
		
		// execute action
		runAction_BookOrders_AddAsList(bookListToAdd);
		
		// commit transaction
		commitUserTransaction();
		
		// validate cache state
		List<Book> bookList = bookCacheHelper.getAllBookOrders();
		Bookshop2Fixture.assertSameBook(bookListToAdd, bookList);
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 19)
	@BytemanRule(name = "rule19", 
			targetClass = "BookCacheManager", 
			targetMethod = "addToBookOrders", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error19\")")
	public void runTest_BookOrders_AddAsList_manager_exception_rollback() throws Exception {
		String testName = "runTest_BookOrders_AddAsList_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookCacheHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;
		
		// prepare test data
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book();
		
		try {
		// execute action
			runAction_BookOrders_AddAsList(bookListToAdd);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error19");
		
			// validate cache state
			List<Book> bookList = bookCacheHelper.getAllBookOrders();
			Assert.isEmpty(bookList, "No BookOrders records should exist");
		
		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 20)
	@BytemanRule(name = "rule20", 
			targetClass = "BookCacheImpl", 
			targetMethod = "addToBookOrders", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error20\")")
	public void runTest_BookOrders_AddAsList_cache_exception_rollback() throws Exception {
		String testName = "runTest_BookOrders_AddAsList_cache_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookCacheHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;
		
		// prepare test data
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book();
		
		try {
		// execute action
			runAction_BookOrders_AddAsList(bookListToAdd);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error20");
		
			// validate cache state
			List<Book> bookList = bookCacheHelper.getAllBookOrders();
			Assert.isEmpty(bookList, "No BookOrders records should exist");
		
		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runAction_BookOrders_AddAsList(final Collection<Book> bookList) throws Exception {
		runAction(new Runnable() {
			public void run() {
				bookCacheManager.addToBookOrders(bookList);
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 21)
	public void runTest_BookOrders_RemoveAll_success() throws Exception {
		String testName = "runTest_BookOrders_RemoveAll_success";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// prepare test data
		int bookCount = 4;
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book(bookCount);
		bookCacheHelper.addToBookOrders(bookListToAdd);
		
		// execute action
		runAction_BookOrders_RemoveAll();

		// validate cache state
		List<Book> bookList = bookCacheHelper.getAllBookOrders();
		Assert.isEmpty(bookList, "No BookOrders records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 22)
	public void runTest_BookOrders_RemoveAll_tx_commit() throws Exception {
		String testName = "runTest_BookOrders_RemoveAll_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();

		// prepare test data
		int bookCount = 4;
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book(bookCount);
		bookCacheHelper.addToBookOrders(bookListToAdd);
		
		// begin transaction
		beginJTATransaction();
		
		// execute action
		runAction_BookOrders_RemoveAll();
		
		// commit transaction
		commitJTATransaction();

		// validate cache state
		List<Book> bookList = bookCacheHelper.getAllBookOrders();
		Assert.isEmpty(bookList, "No BookOrders records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 23)
	public void runTest_BookOrders_RemoveAll_utx_commit() throws Exception {
		String testName = "runTest_BookOrders_RemoveAll_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// prepare test data
		int bookCount = 4;
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book(bookCount);
		bookCacheHelper.addToBookOrders(bookListToAdd);
		
		// begin transaction
		beginUserTransaction();
		
		// execute action
		runAction_BookOrders_RemoveAll();
		
		// commit transaction
		commitUserTransaction();
		
		// validate cache state
		List<Book> bookList = bookCacheHelper.getAllBookOrders();
		Assert.isEmpty(bookList, "No BookOrders records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 24)
	@BytemanRule(name = "rule24", 
			targetClass = "BookCacheManager", 
			targetMethod = "removeAllBookOrders", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error24\")")
	public void runTest_BookOrders_RemoveAll_manager_exception_rollback() throws Exception {
		String testName = "runTest_BookOrders_RemoveAll_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		setupByteman(testName);
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		int bookCount = 4;
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book(bookCount);
		bookCacheHelper.addToBookOrders(bookListToAdd);
		List<Book> bookListToRemove = bookCacheHelper.getAllBookOrders();
		Assert.equals(bookCount, bookListToRemove.size(), "The BookOrders records should exist");
		
		try {
			// execute action
			runAction_BookOrders_RemoveAll();
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error24");

			// validate cache state
			List<Book> bookList = bookCacheHelper.getAllBookOrders();
			Bookshop2Fixture.assertSameBook(bookListToRemove, bookList);
		
		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 25)
	@BytemanRule(name = "rule25", 
			targetClass = "BookCacheImpl", 
			targetMethod = "removeAllBookOrders", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error25\")")
	public void runTest_BookOrders_RemoveAll_cache_exception_rollback() throws Exception {
		String testName = "runTest_BookOrders_RemoveAll_cache_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		setupByteman(testName);
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		int bookCount = 4;
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book(bookCount);
		bookCacheHelper.addToBookOrders(bookListToAdd);
		List<Book> bookListToRemove = bookCacheHelper.getAllBookOrders();
		Assert.equals(bookCount, bookListToRemove.size(), "The BookOrders records should exist");
		
		try {
			// execute action
			runAction_BookOrders_RemoveAll();
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error25");

			// validate cache state
			List<Book> bookList = bookCacheHelper.getAllBookOrders();
			Bookshop2Fixture.assertSameBook(bookListToRemove, bookList);
		
		} finally {
		// cleanup test context
		tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runAction_BookOrders_RemoveAll() throws Exception {
		runAction(new Runnable() {
			public void run() {
				bookCacheManager.removeAllBookOrders();
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 26)
	public void runTest_BookOrders_RemoveAsItem_success() throws Exception {
		String testName = "runTest_BookOrders_RemoveAsItem_success";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		Book book = Bookshop2Fixture.create_Book();
		bookCacheHelper.addToBookOrders(book);
		
		// prepare test data
		Book bookToRemove = bookCacheHelper.getFromBookOrders(book.getId());
		Assert.notNull(bookToRemove, "The BookOrders record should exist");
		
		// execute action
		runAction_BookOrders_RemoveAsItem(bookToRemove);
		
		// validate cache state
		book = bookCacheHelper.getFromBookOrders(book.getId());
		Assert.isNull(book, "The BookOrders record should be removed");
		List<Book> bookListRemaining = bookCacheHelper.getAllBookOrders();
		Assert.isEmpty(bookListRemaining, "No BookOrders records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 27)
	public void runTest_BookOrders_RemoveAsItem_tx_commit() throws Exception {
		String testName = "runTest_BookOrders_RemoveAsItem_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		Book book = Bookshop2Fixture.create_Book();
		bookCacheHelper.addToBookOrders(book);
		
		// begin transaction
		beginJTATransaction();

		// prepare test data
		Book bookToRemove = bookCacheHelper.getFromBookOrders(book.getId());
		Assert.notNull(bookToRemove, "The BookOrders record should exist");
		
		// execute action
		runAction_BookOrders_RemoveAsItem(bookToRemove);
		
		// commit transaction
		commitJTATransaction();

		// validate cache state
		book = bookCacheHelper.getFromBookOrders(book.getId());
		Assert.isNull(book, "The BookOrders record should be removed");
		List<Book> bookListRemaining = bookCacheHelper.getAllBookOrders();
		Assert.isEmpty(bookListRemaining, "No BookOrders records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 28)
	public void runTest_BookOrders_RemoveAsItem_utx_commit() throws Exception {
		String testName = "runTest_BookOrders_RemoveAsItem_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		Book book = Bookshop2Fixture.create_Book();
		bookCacheHelper.addToBookOrders(book);
		
		// begin transaction
		beginUserTransaction();

		// prepare test data
		Book bookToRemove = bookCacheHelper.getFromBookOrders(book.getId());
		Assert.notNull(bookToRemove, "The BookOrders record should exist");
		
		// execute action
		runAction_BookOrders_RemoveAsItem(bookToRemove);
		
		// commit transaction
		commitUserTransaction();
		
		// validate cache state
		book = bookCacheHelper.getFromBookOrders(book.getId());
		Assert.isNull(book, "The BookOrders record should be removed");
		List<Book> bookListRemaining = bookCacheHelper.getAllBookOrders();
		Assert.isEmpty(bookListRemaining, "No BookOrders records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 29)
	@BytemanRule(name = "rule29", 
			targetClass = "BookCacheManager", 
			targetMethod = "removeFromBookOrders", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error29\")")
	public void runTest_BookOrders_RemoveAsItem_manager_exception_rollback() throws Exception {
		String testName = "runTest_BookOrders_RemoveAsItem_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookCacheHelper.assureRemoveAll();
		Book book = Bookshop2Fixture.create_Book();
		bookCacheHelper.addToBookOrders(book);
		
		// setup expectations
		isExceptionExpected = true;
		
		// prepare test data
		Book bookToRemove = bookCacheHelper.getFromBookOrders(book.getId());
		Assert.notNull(bookToRemove, "The BookOrders record should exist");
		
		try {
		// execute action
			runAction_BookOrders_RemoveAsItem(bookToRemove);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error29");
		
			// validate cache state
			book = bookCacheHelper.getFromBookOrders(book.getId());
			Assert.notNull(book, "The BookOrders record should still exist");
			List<Book> bookList = bookCacheHelper.getAllBookOrders();
			Assert.equals(1, bookList.size(), "Only 1 BookOrders record should exist");
		
		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 30)
	@BytemanRule(name = "rule30", 
			targetClass = "BookCacheImpl", 
			targetMethod = "removeFromBookOrders", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error30\")")
	public void runTest_BookOrders_RemoveAsItem_cache_exception_rollback() throws Exception {
		String testName = "runTest_BookOrders_RemoveAsItem_cache_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookCacheHelper.assureRemoveAll();
		Book book = Bookshop2Fixture.create_Book();
		bookCacheHelper.addToBookOrders(book);
		
		// setup expectations
		isExceptionExpected = true;
		
		// prepare test data
		Book bookToRemove = bookCacheHelper.getFromBookOrders(book.getId());
		Assert.notNull(bookToRemove, "The BookOrders record should exist");
		
		try {
		// execute action
			runAction_BookOrders_RemoveAsItem(bookToRemove);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error30");
		
			// validate cache state
			book = bookCacheHelper.getFromBookOrders(book.getId());
			Assert.notNull(book, "The BookOrders record should still exist");
			List<Book> bookList = bookCacheHelper.getAllBookOrders();
			Assert.equals(1, bookList.size(), "Only 1 BookOrders record should exist");
		
		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runAction_BookOrders_RemoveAsItem(final Book book) throws Exception {
		runAction(new Runnable() {
			public void run() {
				bookCacheManager.removeFromBookOrders(book);
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 31)
	public void runTest_BookOrders_RemoveAsList_success() throws Exception {
		String testName = "runTest_BookOrders_RemoveAsList_success";
		log.info(testName+": started");
		
		// prepare test context
		int bookCount = 4;
		bookCacheHelper.assureRemoveAll();
		List<Book> bookList = Bookshop2Fixture.createList_Book(bookCount);
		bookCacheHelper.addToBookOrders(bookList);
		
		// prepare test data
		List<Book> bookListToRemove = bookCacheHelper.getAllBookOrders();
		Assert.equals(bookCount, bookListToRemove.size(), "The BookOrders records should exist");
		
		// execute action
		runAction_BookOrders_RemoveAsList(bookListToRemove);
		
		// validate cache state
		List<Book> bookListRemaining = bookCacheHelper.getAllBookOrders();
		Assert.isEmpty(bookListRemaining, "No BookOrders records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 32)
	public void runTest_BookOrders_RemoveAsList_tx_commit() throws Exception {
		String testName = "runTest_BookOrders_RemoveAsList_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		int bookCount = 4;
		bookCacheHelper.assureRemoveAll();
		List<Book> bookList = Bookshop2Fixture.createList_Book(bookCount);
		bookCacheHelper.addToBookOrders(bookList);
		
		// begin transaction
		beginJTATransaction();

		// prepare test data
		List<Book> bookListToRemove = bookCacheHelper.getAllBookOrders();
		Assert.equals(bookCount, bookListToRemove.size(), "The BookOrders records should exist");
		
		// execute action
		runAction_BookOrders_RemoveAsList(bookListToRemove);
		
		// commit transaction
		commitJTATransaction();

		// validate cache state
		List<Book> bookListRemaining = bookCacheHelper.getAllBookOrders();
		Assert.isEmpty(bookListRemaining, "No BookOrders records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 33)
	public void runTest_BookOrders_RemoveAsList_utx_commit() throws Exception {
		String testName = "runTest_BookOrders_RemoveAsList_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		int bookCount = 4;
		bookCacheHelper.assureRemoveAll();
		List<Book> bookList = Bookshop2Fixture.createList_Book(bookCount);
		bookCacheHelper.addToBookOrders(bookList);
		
		// begin transaction
		beginUserTransaction();

		// prepare test data
		List<Book> bookListToRemove = bookCacheHelper.getAllBookOrders();
		Assert.equals(bookCount, bookListToRemove.size(), "The BookOrders records should exist");
		
		// execute action
		runAction_BookOrders_RemoveAsList(bookListToRemove);
		
		// commit transaction
		commitUserTransaction();
		
		// validate cache state
		List<Book> bookListRemaining = bookCacheHelper.getAllBookOrders();
		Assert.isEmpty(bookListRemaining, "No BookOrders records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 34)
	@BytemanRule(name = "rule34", 
			targetClass = "BookCacheManager", 
			targetMethod = "removeFromBookOrders", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error34\")")
	public void runTest_BookOrders_RemoveAsList_manager_exception_rollback() throws Exception {
		String testName = "runTest_BookOrders_RemoveAsList_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		int bookCount = 4;
		bookCacheHelper.assureRemoveAll();
		List<Book> bookList = Bookshop2Fixture.createList_Book(bookCount);
		bookCacheHelper.addToBookOrders(bookList);
		
		// setup expectations
		isExceptionExpected = true;
		
		// prepare test data
		List<Book> bookListToRemove = bookCacheHelper.getAllBookOrders();
		Assert.equals(bookCount, bookListToRemove.size(), "The BookOrders records should exist");
		
		try {
		// execute action
			runAction_BookOrders_RemoveAsList(bookListToRemove);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error34");
		
			// validate cache state
			List<Book> bookListRemaining = bookCacheHelper.getAllBookOrders();
			Assert.equals(bookCount, bookListRemaining.size(), "The BookOrders records should still exist");
			Bookshop2Fixture.assertSameBook(bookListRemaining, bookListToRemove);
		
		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 35)
	@BytemanRule(name = "rule35", 
			targetClass = "BookCacheImpl", 
			targetMethod = "removeFromBookOrders", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error35\")")
	public void runTest_BookOrders_RemoveAsList_cache_exception_rollback() throws Exception {
		String testName = "runTest_BookOrders_RemoveAsList_cache_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		int bookCount = 4;
		bookCacheHelper.assureRemoveAll();
		List<Book> bookList = Bookshop2Fixture.createList_Book(bookCount);
		bookCacheHelper.addToBookOrders(bookList);
		
		// setup expectations
		isExceptionExpected = true;
		
		// prepare test data
		List<Book> bookListToRemove = bookCacheHelper.getAllBookOrders();
		Assert.equals(bookCount, bookListToRemove.size(), "The BookOrders records should exist");
		
		try {
		// execute action
			runAction_BookOrders_RemoveAsList(bookListToRemove);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error35");
		
			// validate cache state
			List<Book> bookListRemaining = bookCacheHelper.getAllBookOrders();
			Assert.equals(bookCount, bookListRemaining.size(), "The BookOrders records should still exist");
			Bookshop2Fixture.assertSameBook(bookListRemaining, bookListToRemove);
		
		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runAction_BookOrders_RemoveAsList(final Collection<Book> bookList) throws Exception {
		runAction(new Runnable() {
			public void run() {
				bookCacheManager.removeFromBookOrders(bookList);
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 36)
	public void runTest_AvailableBooks_GetAllAsList_success() throws Exception {
		String testName = "runTest_AvailableBooks_GetAllAsList_success";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// prepare test data
		int bookCount = 4;
		Set<Book> bookSetToAdd = Bookshop2Fixture.createSet_Book(bookCount);
		bookCacheHelper.addToAvailableBooks(bookSetToAdd);
		
		// execute action
		Set<Book> bookSet = runAction_AvailableBooks_GetAllAsList();

		// validate cache state
		Assert.equals(bookCount, bookSet.size(), "AvailableBooks record count should be correct");
		Bookshop2Fixture.assertSameBook(bookSetToAdd, bookSet, "AvailableBooks records should be equal");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 37)
	public void runTest_AvailableBooks_GetAllAsList_tx_commit() throws Exception {
		String testName = "runTest_AvailableBooks_GetAllAsList_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();

		// prepare test data
		int bookCount = 4;
		Set<Book> bookSetToAdd = Bookshop2Fixture.createSet_Book(bookCount);
		bookCacheHelper.addToAvailableBooks(bookSetToAdd);
		
		// execute action
		Set<Book> bookSet = runAction_AvailableBooks_GetAllAsList();

		// validate intermediate cache state
		Assert.isEmpty(bookSet, "No AvailableBooks records should exist");
		
		// commit transaction
		commitJTATransaction();

		// execute action again
		bookSet = runAction_AvailableBooks_GetAllAsList();

		// validate cache state
		Assert.equals(bookCount, bookSet.size(), "AvailableBooks record count should be correct");
		Bookshop2Fixture.assertSameBook(bookSetToAdd, bookSet, "AvailableBooks records should be equal");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 38)
	public void runTest_AvailableBooks_GetAllAsList_tx_rollback() throws Exception {
		String testName = "runTest_AvailableBooks_GetAllAsList_tx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();
		
		// prepare test data
		int bookCount = 4;
		Set<Book> bookSetToAdd = Bookshop2Fixture.createSet_Book(bookCount);
		bookCacheHelper.addToAvailableBooks(bookSetToAdd);
		
		// execute action
		Set<Book> bookSet = runAction_AvailableBooks_GetAllAsList();

		// validate intermediate cache state
		Assert.isEmpty(bookSet, "No AvailableBooks records should exist");
		
		// rollback transaction
		rollbackJTATransaction();

		// execute action again
		bookSet = runAction_AvailableBooks_GetAllAsList();

		// validate cache state
		Assert.isEmpty(bookSet, "No AvailableBooks records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 39)
	public void runTest_AvailableBooks_GetAllAsList_utx_commit() throws Exception {
		String testName = "runTest_AvailableBooks_GetAllAsList_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();
		
		// prepare test data
		int bookCount = 4;
		Set<Book> bookSetToAdd = Bookshop2Fixture.createSet_Book(bookCount);
		bookCacheHelper.addToAvailableBooks(bookSetToAdd);
		
		// execute action
		Set<Book> bookSet = runAction_AvailableBooks_GetAllAsList();

		// validate intermediate cache state
		Assert.isEmpty(bookSet, "No AvailableBooks records should exist");
		
		// commit transaction
		commitUserTransaction();

		// execute action again
		bookSet = runAction_AvailableBooks_GetAllAsList();

		// validate cache state
		Assert.equals(bookCount, bookSet.size(), "AvailableBooks record count should be correct");
		Bookshop2Fixture.assertSameBook(bookSetToAdd, bookSet, "AvailableBooks records should be equal");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 40)
	public void runTest_AvailableBooks_GetAllAsList_utx_rollback() throws Exception {
		String testName = "runTest_AvailableBooks_GetAllAsList_utx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();

		// prepare test data
		int bookCount = 4;
		Set<Book> bookSetToAdd = Bookshop2Fixture.createSet_Book(bookCount);
		bookCacheHelper.addToAvailableBooks(bookSetToAdd);
		
		// execute action
		Set<Book> bookSet = runAction_AvailableBooks_GetAllAsList();

		// validate intermediate cache state
		Assert.isEmpty(bookSet, "No AvailableBooks records should exist");
		
		// rollback transaction
		rollbackUserTransaction();

		// execute action again
		bookSet = runAction_AvailableBooks_GetAllAsList();

		// validate cache state
		Assert.isEmpty(bookSet, "No AvailableBooks records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public <T> T runAction_AvailableBooks_GetAllAsList() throws Exception {
		return runAction(new Callable<T>() {
			@SuppressWarnings("unchecked")
			public T call() {
				return (T) bookCacheManager.getAllAvailableBooks();
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 41)
	public void runTest_AvailableBooks_GetMatchingAsList_success() throws Exception {
		String testName = "runTest_AvailableBooks_GetMatchingAsList_success";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 42)
	public void runTest_AvailableBooks_GetMatchingAsList_tx_commit() throws Exception {
		String testName = "runTest_AvailableBooks_GetMatchingAsList_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();
		
		
		// commit transaction
		commitJTATransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 43)
	public void runTest_AvailableBooks_GetMatchingAsList_tx_rollback() throws Exception {
		String testName = "runTest_AvailableBooks_GetMatchingAsList_tx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();
		
		
		// rollback transaction
		rollbackJTATransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 44)
	public void runTest_AvailableBooks_GetMatchingAsList_utx_commit() throws Exception {
		String testName = "runTest_AvailableBooks_GetMatchingAsList_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();
		
		
		// commit transaction
		commitUserTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 45)
	public void runTest_AvailableBooks_GetMatchingAsList_utx_rollback() throws Exception {
		String testName = "runTest_AvailableBooks_GetMatchingAsList_utx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();
		
		
		// rollback transaction
		rollbackUserTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public <T> T runAction_AvailableBooks_GetMatchingAsList(final Collection<Book> bookSet) throws Exception {
		return runAction(new Callable<T>() {
			@SuppressWarnings("unchecked")
			public T call() {
				return (T) bookCacheManager.getMatchingAvailableBooks(bookSet);
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 46)
	public void runTest_AvailableBooks_AddAsItem_success() throws Exception {
		String testName = "runTest_AvailableBooks_AddAsItem_success";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();

		// prepare test data
		Book bookToAdd = Bookshop2Fixture.create_Book();
		
		// execute action
		runAction_AvailableBooks_AddAsItem(bookToAdd);
		
		// validate cache state
		Set<Book> bookSet = bookCacheHelper.getAllAvailableBooks();
		Assert.equals(1, bookSet.size(), "Only 1 AvailableBooks record should exist");
		Bookshop2Fixture.assertSameBook(bookToAdd, bookSet.iterator().next());
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 47)
	public void runTest_AvailableBooks_AddAsItem_tx_commit() throws Exception {
		String testName = "runTest_AvailableBooks_AddAsItem_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();

		// prepare test data
		Book bookToAdd = Bookshop2Fixture.create_Book();
		
		// execute action
		runAction_AvailableBooks_AddAsItem(bookToAdd);
		
		// commit transaction
		commitJTATransaction();

		// validate cache state
		Set<Book> bookSet = bookCacheHelper.getAllAvailableBooks();
		Assert.equals(1, bookSet.size(), "Only 1 AvailableBooks record should exist");
		Bookshop2Fixture.assertSameBook(bookToAdd, bookSet.iterator().next());
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 48)
	public void runTest_AvailableBooks_AddAsItem_utx_commit() throws Exception {
		String testName = "runTest_AvailableBooks_AddAsItem_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();

		// prepare test data
		Book bookToAdd = Bookshop2Fixture.create_Book();
		
		// execute action
		runAction_AvailableBooks_AddAsItem(bookToAdd);
		
		// commit transaction
		commitUserTransaction();
		
		// validate cache state
		Set<Book> bookSet = bookCacheHelper.getAllAvailableBooks();
		Assert.equals(1, bookSet.size(), "Only 1 AvailableBooks record should exist");
		Bookshop2Fixture.assertSameBook(bookToAdd, bookSet.iterator().next());
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 49)
	@BytemanRule(name = "rule49", 
			targetClass = "BookCacheManager", 
			targetMethod = "addToAvailableBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error49\")")
	public void runTest_AvailableBooks_AddAsItem_manager_exception_rollback() throws Exception {
		String testName = "runTest_AvailableBooks_AddAsItem_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookCacheHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		Book bookToAdd = Bookshop2Fixture.create_Book();
		
		try {
		// execute action
			runAction_AvailableBooks_AddAsItem(bookToAdd);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error49");
		
			// validate cache state
			Set<Book> bookSet = bookCacheHelper.getAllAvailableBooks();
			Assert.isEmpty(bookSet, "No AvailableBooks records should exist");
		
		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 50)
	@BytemanRule(name = "rule50", 
			targetClass = "BookCacheImpl", 
			targetMethod = "addToAvailableBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error50\")")
	public void runTest_AvailableBooks_AddAsItem_cache_exception_rollback() throws Exception {
		String testName = "runTest_AvailableBooks_AddAsItem_cache_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookCacheHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		Book bookToAdd = Bookshop2Fixture.create_Book();
		
		try {
		// execute action
			runAction_AvailableBooks_AddAsItem(bookToAdd);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error50");
		
			// validate cache state
			Set<Book> bookSet = bookCacheHelper.getAllAvailableBooks();
			Assert.isEmpty(bookSet, "No AvailableBooks records should exist");
		
		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runAction_AvailableBooks_AddAsItem(final Book book) throws Exception {
		runAction(new Runnable() {
			public void run() {
				bookCacheManager.addToAvailableBooks(book);
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 51)
	public void runTest_AvailableBooks_AddAsList_success() throws Exception {
		String testName = "runTest_AvailableBooks_AddAsList_success";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();

		// prepare test data
		Set<Book> bookSetToAdd = Bookshop2Fixture.createSet_Book();
		
		// execute action
		runAction_AvailableBooks_AddAsList(bookSetToAdd);
		
		// validate cache state
		Set<Book> bookSet = bookCacheHelper.getAllAvailableBooks();
		Bookshop2Fixture.assertSameBook(bookSetToAdd, bookSet);
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 52)
	public void runTest_AvailableBooks_AddAsList_tx_commit() throws Exception {
		String testName = "runTest_AvailableBooks_AddAsList_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();

		// prepare test data
		Set<Book> bookSetToAdd = Bookshop2Fixture.createSet_Book();
		
		// execute action
		runAction_AvailableBooks_AddAsList(bookSetToAdd);
		
		// commit transaction
		commitJTATransaction();

		// validate cache state
		Set<Book> bookSet = bookCacheHelper.getAllAvailableBooks();
		Bookshop2Fixture.assertSameBook(bookSetToAdd, bookSet);
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 53)
	public void runTest_AvailableBooks_AddAsList_utx_commit() throws Exception {
		String testName = "runTest_AvailableBooks_AddAsList_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();

		// prepare test data
		Set<Book> bookSetToAdd = Bookshop2Fixture.createSet_Book();
		
		// execute action
		runAction_AvailableBooks_AddAsList(bookSetToAdd);
		
		// commit transaction
		commitUserTransaction();
		
		// validate cache state
		Set<Book> bookSet = bookCacheHelper.getAllAvailableBooks();
		Bookshop2Fixture.assertSameBook(bookSetToAdd, bookSet);
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 54)
	@BytemanRule(name = "rule54", 
			targetClass = "BookCacheManager", 
			targetMethod = "addToAvailableBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error54\")")
	public void runTest_AvailableBooks_AddAsList_manager_exception_rollback() throws Exception {
		String testName = "runTest_AvailableBooks_AddAsList_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookCacheHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		Set<Book> bookSetToAdd = Bookshop2Fixture.createSet_Book();
		
		try {
			// execute action
			runAction_AvailableBooks_AddAsList(bookSetToAdd);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error54");
		
			// validate cache state
			Set<Book> bookSet = bookCacheHelper.getAllAvailableBooks();
			Assert.isEmpty(bookSet, "No AvailableBooks records should exist");
		
		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 55)
	@BytemanRule(name = "rule55", 
			targetClass = "BookCacheImpl", 
			targetMethod = "addToAvailableBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error55\")")
	public void runTest_AvailableBooks_AddAsList_cache_exception_rollback() throws Exception {
		String testName = "runTest_AvailableBooks_AddAsList_cache_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookCacheHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		Set<Book> bookSetToAdd = Bookshop2Fixture.createSet_Book();
		
		try {
		// execute action
			runAction_AvailableBooks_AddAsList(bookSetToAdd);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error55");
		
			// validate cache state
			Set<Book> bookSet = bookCacheHelper.getAllAvailableBooks();
			Assert.isEmpty(bookSet, "No AvailableBooks records should exist");
		
		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runAction_AvailableBooks_AddAsList(final Collection<Book> bookSet) throws Exception {
		runAction(new Runnable() {
			public void run() {
				bookCacheManager.addToAvailableBooks(bookSet);
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 56)
	public void runTest_AvailableBooks_RemoveAll_success() throws Exception {
		String testName = "runTest_AvailableBooks_RemoveAll_success";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// prepare test data
		int bookCount = 4;
		Set<Book> bookSetToAdd = Bookshop2Fixture.createSet_Book(bookCount);
		bookCacheHelper.addToAvailableBooks(bookSetToAdd);
		
		// execute action
		runAction_AvailableBooks_RemoveAll();

		// validate cache state
		Set<Book> bookSet = bookCacheHelper.getAllAvailableBooks();
		Assert.isEmpty(bookSet, "No AvailableBooks records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 57)
	public void runTest_AvailableBooks_RemoveAll_tx_commit() throws Exception {
		String testName = "runTest_AvailableBooks_RemoveAll_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();

		// prepare test data
		int bookCount = 4;
		Set<Book> bookSetToAdd = Bookshop2Fixture.createSet_Book(bookCount);
		bookCacheHelper.addToAvailableBooks(bookSetToAdd);
		
		// begin transaction
		beginJTATransaction();
		
		// execute action
		runAction_AvailableBooks_RemoveAll();
		
		// commit transaction
		commitJTATransaction();

		// validate cache state
		Set<Book> bookSet = bookCacheHelper.getAllAvailableBooks();
		Assert.isEmpty(bookSet, "No AvailableBooks records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 58)
	public void runTest_AvailableBooks_RemoveAll_utx_commit() throws Exception {
		String testName = "runTest_AvailableBooks_RemoveAll_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// prepare test data
		int bookCount = 4;
		Set<Book> bookSetToAdd = Bookshop2Fixture.createSet_Book(bookCount);
		bookCacheHelper.addToAvailableBooks(bookSetToAdd);
		
		// begin transaction
		beginUserTransaction();
		
		// execute action
		runAction_AvailableBooks_RemoveAll();
		
		// commit transaction
		commitUserTransaction();
		
		// validate cache state
		Set<Book> bookSet = bookCacheHelper.getAllAvailableBooks();
		Assert.isEmpty(bookSet, "No AvailableBooks records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 59)
	@BytemanRule(name = "rule59", 
			targetClass = "BookCacheManager", 
			targetMethod = "removeAllAvailableBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error59\")")
	public void runTest_AvailableBooks_RemoveAll_manager_exception_rollback() throws Exception {
		String testName = "runTest_AvailableBooks_RemoveAll_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		setupByteman(testName);
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		int bookCount = 4;
		Set<Book> bookSetToAdd = Bookshop2Fixture.createSet_Book(bookCount);
		bookCacheHelper.addToAvailableBooks(bookSetToAdd);
		Set<Book> bookSetToRemove = bookCacheHelper.getAllAvailableBooks();
		Assert.equals(bookCount, bookSetToRemove.size(), "The AvailableBooks records should exist");
		
		try {
			// execute action
			runAction_AvailableBooks_RemoveAll();
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error59");

			// validate cache state
			Set<Book> bookSet = bookCacheHelper.getAllAvailableBooks();
			Bookshop2Fixture.assertSameBook(bookSetToRemove, bookSet);
		
		} finally {
		// cleanup test context
		tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 60)
	@BytemanRule(name = "rule60", 
			targetClass = "BookCacheImpl", 
			targetMethod = "removeAllAvailableBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error60\")")
	public void runTest_AvailableBooks_RemoveAll_cache_exception_rollback() throws Exception {
		String testName = "runTest_AvailableBooks_RemoveAll_cache_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		setupByteman(testName);
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		int bookCount = 4;
		Set<Book> bookSetToAdd = Bookshop2Fixture.createSet_Book(bookCount);
		bookCacheHelper.addToAvailableBooks(bookSetToAdd);
		Set<Book> bookSetToRemove = bookCacheHelper.getAllAvailableBooks();
		Assert.equals(bookCount, bookSetToRemove.size(), "The AvailableBooks records should exist");
		
		try {
			// execute action
			runAction_AvailableBooks_RemoveAll();
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error60");

			// validate cache state
			Set<Book> bookSet = bookCacheHelper.getAllAvailableBooks();
			Bookshop2Fixture.assertSameBook(bookSetToRemove, bookSet);
		
		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runAction_AvailableBooks_RemoveAll() throws Exception {
		runAction(new Runnable() {
			public void run() {
				bookCacheManager.removeAllAvailableBooks();
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 61)
	public void runTest_AvailableBooks_RemoveAsItem_success() throws Exception {
		String testName = "runTest_AvailableBooks_RemoveAsItem_success";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		Book book = Bookshop2Fixture.create_Book();
		bookCacheHelper.addToAvailableBooks(book);

		// prepare test data
		Book bookToRemove = bookCacheHelper.getFromAvailableBooks(book.getId());
		Assert.notNull(bookToRemove, "The AvailableBooks record should exist");
		
		// execute action
		runAction_AvailableBooks_RemoveAsItem(bookToRemove);
		
		// validate cache state
		book = bookCacheHelper.getFromAvailableBooks(book.getId());
		Assert.isNull(book, "The AvailableBooks record should be removed");
		Set<Book> bookSetRemaining = bookCacheHelper.getAllAvailableBooks();
		Assert.isEmpty(bookSetRemaining, "No AvailableBooks records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 62)
	public void runTest_AvailableBooks_RemoveAsItem_tx_commit() throws Exception {
		String testName = "runTest_AvailableBooks_RemoveAsItem_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		Book book = Bookshop2Fixture.create_Book();
		bookCacheHelper.addToAvailableBooks(book);
		
		// begin transaction
		beginJTATransaction();

		// prepare test data
		Book bookToRemove = bookCacheHelper.getFromAvailableBooks(book.getId());
		Assert.notNull(bookToRemove, "The AvailableBooks record should exist");
		
		// execute action
		runAction_AvailableBooks_RemoveAsItem(bookToRemove);
		
		// commit transaction
		commitJTATransaction();

		// validate cache state
		book = bookCacheHelper.getFromAvailableBooks(book.getId());
		Assert.isNull(book, "The AvailableBooks record should be removed");
		Set<Book> bookSetRemaining = bookCacheHelper.getAllAvailableBooks();
		Assert.isEmpty(bookSetRemaining, "No AvailableBooks records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 63)
	public void runTest_AvailableBooks_RemoveAsItem_utx_commit() throws Exception {
		String testName = "runTest_AvailableBooks_RemoveAsItem_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		Book book = Bookshop2Fixture.create_Book();
		bookCacheHelper.addToAvailableBooks(book);
		
		// begin transaction
		beginUserTransaction();

		// prepare test data
		Book bookToRemove = bookCacheHelper.getFromAvailableBooks(book.getId());
		Assert.notNull(bookToRemove, "The AvailableBooks record should exist");
		
		// execute action
		runAction_AvailableBooks_RemoveAsItem(bookToRemove);
		
		// commit transaction
		commitUserTransaction();
		
		// validate cache state
		book = bookCacheHelper.getFromAvailableBooks(book.getId());
		Assert.isNull(book, "The AvailableBooks record should be removed");
		Set<Book> bookSetRemaining = bookCacheHelper.getAllAvailableBooks();
		Assert.isEmpty(bookSetRemaining, "No AvailableBooks records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 64)
	@BytemanRule(name = "rule64", 
			targetClass = "BookCacheManager", 
			targetMethod = "removeFromAvailableBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error64\")")
	public void runTest_AvailableBooks_RemoveAsItem_manager_exception_rollback() throws Exception {
		String testName = "runTest_AvailableBooks_RemoveAsItem_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookCacheHelper.assureRemoveAll();
		Book book = Bookshop2Fixture.create_Book();
		bookCacheHelper.addToAvailableBooks(book);
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		Book bookToRemove = bookCacheHelper.getFromAvailableBooks(book.getId());
		Assert.notNull(bookToRemove, "The AvailableBooks record should exist");
		
		try {
			// execute action
			runAction_AvailableBooks_RemoveAsItem(bookToRemove);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error64");

			// validate cache state
			book = bookCacheHelper.getFromAvailableBooks(book.getId());
			Assert.notNull(book, "The AvailableBooks record should still exist");
			Set<Book> bookSet = bookCacheHelper.getAllAvailableBooks();
			Assert.equals(1, bookSet.size(), "Only 1 AvailableBooks record should exist");

		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 65)
	@BytemanRule(name = "rule65", 
			targetClass = "BookCacheImpl", 
			targetMethod = "removeFromAvailableBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error65\")")
	public void runTest_AvailableBooks_RemoveAsItem_cache_exception_rollback() throws Exception {
		String testName = "runTest_AvailableBooks_RemoveAsItem_cache_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookCacheHelper.assureRemoveAll();
		Book book = Bookshop2Fixture.create_Book();
		bookCacheHelper.addToAvailableBooks(book);
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		Book bookToRemove = bookCacheHelper.getFromAvailableBooks(book.getId());
		Assert.notNull(bookToRemove, "The AvailableBooks record should exist");
		
		try {
			// execute action
			runAction_AvailableBooks_RemoveAsItem(bookToRemove);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error65");

			// validate cache state
			book = bookCacheHelper.getFromAvailableBooks(book.getId());
			Assert.notNull(book, "The AvailableBooks record should still exist");
			Set<Book> bookSet = bookCacheHelper.getAllAvailableBooks();
			Assert.equals(1, bookSet.size(), "Only 1 AvailableBooks record should exist");

		} finally {
			// cleanup test context
			tearDownByteman(testName);
	}
	
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runAction_AvailableBooks_RemoveAsItem(final Book book) throws Exception {
		runAction(new Runnable() {
			public void run() {
				bookCacheManager.removeFromAvailableBooks(book);
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 66)
	public void runTest_AvailableBooks_RemoveAsList_success() throws Exception {
		String testName = "runTest_AvailableBooks_RemoveAsList_success";
		log.info(testName+": started");
		
		// prepare test context
		int bookCount = 4;
		bookCacheHelper.assureRemoveAll();
		List<Book> bookList = Bookshop2Fixture.createList_Book(bookCount);
		bookCacheHelper.addToAvailableBooks(bookList);

		// prepare test data
		Set<Book> bookSetToRemove = bookCacheHelper.getAllAvailableBooks();
		Assert.equals(bookCount, bookSetToRemove.size(), "The AvailableBooks records should exist");
		
		// execute action
		runAction_AvailableBooks_RemoveAsList(bookSetToRemove);

		// validate cache state
		Set<Book> bookSetRemaining = bookCacheHelper.getAllAvailableBooks();
		Assert.isEmpty(bookSetRemaining, "No AvailableBooks records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 67)
	public void runTest_AvailableBooks_RemoveAsList_tx_commit() throws Exception {
		String testName = "runTest_AvailableBooks_RemoveAsList_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		int bookCount = 4;
		bookCacheHelper.assureRemoveAll();
		List<Book> bookList = Bookshop2Fixture.createList_Book(bookCount);
		bookCacheHelper.addToAvailableBooks(bookList);
		
		// begin transaction
		beginJTATransaction();

		// prepare test data
		Set<Book> bookSetToRemove = bookCacheHelper.getAllAvailableBooks();
		Assert.equals(bookCount, bookSetToRemove.size(), "The AvailableBooks records should exist");
		
		// execute action
		runAction_AvailableBooks_RemoveAsList(bookSetToRemove);
		
		// commit transaction
		commitJTATransaction();

		// validate cache state
		Set<Book> bookSetRemaining = bookCacheHelper.getAllAvailableBooks();
		Assert.isEmpty(bookSetRemaining, "No AvailableBooks records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 68)
	public void runTest_AvailableBooks_RemoveAsList_utx_commit() throws Exception {
		String testName = "runTest_AvailableBooks_RemoveAsList_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		int bookCount = 4;
		bookCacheHelper.assureRemoveAll();
		List<Book> bookList = Bookshop2Fixture.createList_Book(bookCount);
		bookCacheHelper.addToAvailableBooks(bookList);
		
		// begin transaction
		beginUserTransaction();

		// prepare test data
		Set<Book> bookSetToRemove = bookCacheHelper.getAllAvailableBooks();
		Assert.equals(bookCount, bookSetToRemove.size(), "The AvailableBooks records should exist");
		
		// execute action
		runAction_AvailableBooks_RemoveAsList(bookSetToRemove);
		
		// commit transaction
		commitUserTransaction();

		// validate cache state
		Set<Book> bookSetRemaining = bookCacheHelper.getAllAvailableBooks();
		Assert.isEmpty(bookSetRemaining, "No AvailableBooks records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 69)
	@BytemanRule(name = "rule69", 
			targetClass = "BookCacheManager", 
			targetMethod = "removeFromAvailableBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error69\")")
	public void runTest_AvailableBooks_RemoveAsList_manager_exception_rollback() throws Exception {
		String testName = "runTest_AvailableBooks_RemoveAsList_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		int bookCount = 4;
		bookCacheHelper.assureRemoveAll();
		List<Book> bookList = Bookshop2Fixture.createList_Book(bookCount);
		bookCacheHelper.addToAvailableBooks(bookList);
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		Set<Book> bookSetToRemove = bookCacheHelper.getAllAvailableBooks();
		Assert.equals(bookCount, bookSetToRemove.size(), "The AvailableBooks records should exist");
		
		try {
			// execute action
			runAction_AvailableBooks_RemoveAsList(bookSetToRemove);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error69");

			// validate cache state
			Set<Book> bookSetRemaining = bookCacheHelper.getAllAvailableBooks();
			Assert.equals(bookCount, bookSetRemaining.size(), "The AvailableBooks records should still exist");
			Bookshop2Fixture.assertSameBook(bookSetRemaining, bookSetToRemove);

		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 70)
	@BytemanRule(name = "rule70", 
			targetClass = "BookCacheImpl", 
			targetMethod = "removeFromAvailableBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error70\")")
	public void runTest_AvailableBooks_RemoveAsList_cache_exception_rollback() throws Exception {
		String testName = "runTest_AvailableBooks_RemoveAsList_cache_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		int bookCount = 4;
		bookCacheHelper.assureRemoveAll();
		List<Book> bookList = Bookshop2Fixture.createList_Book(bookCount);
		bookCacheHelper.addToAvailableBooks(bookList);
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		Set<Book> bookSetToRemove = bookCacheHelper.getAllAvailableBooks();
		Assert.equals(bookCount, bookSetToRemove.size(), "The AvailableBooks records should exist");
		
		try {
			// execute action
			runAction_AvailableBooks_RemoveAsList(bookSetToRemove);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error70");

			// validate cache state
			Set<Book> bookSetRemaining = bookCacheHelper.getAllAvailableBooks();
			Assert.equals(bookCount, bookSetRemaining.size(), "The AvailableBooks records should still exist");
			Bookshop2Fixture.assertSameBook(bookSetRemaining, bookSetToRemove);

		} finally {
			// cleanup test context
			tearDownByteman(testName);
	}
	
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runAction_AvailableBooks_RemoveAsList(final Collection<Book> bookSet) throws Exception {
		runAction(new Runnable() {
			public void run() {
				bookCacheManager.removeFromAvailableBooks(bookSet);
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 71)
	public void runTest_UnavailableBooks_GetAllAsList_success() throws Exception {
		String testName = "runTest_UnavailableBooks_GetAllAsList_success";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();

		// prepare test data
		int bookCount = 4;
		Set<Book> bookSetToAdd = Bookshop2Fixture.createSet_Book(bookCount);
		bookCacheHelper.addToUnavailableBooks(bookSetToAdd);
		
		// execute action
		Set<Book> bookSet = runAction_UnavailableBooks_GetAllAsList();

		// validate cache state
		Assert.equals(bookCount, bookSet.size(), "UnavailableBooks record count should be correct");
		Bookshop2Fixture.assertSameBook(bookSetToAdd, bookSet, "UnavailableBooks records should be equal");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 72)
	public void runTest_UnavailableBooks_GetAllAsList_tx_commit() throws Exception {
		String testName = "runTest_UnavailableBooks_GetAllAsList_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();

		// prepare test data
		int bookCount = 4;
		Set<Book> bookSetToAdd = Bookshop2Fixture.createSet_Book(bookCount);
		bookCacheHelper.addToUnavailableBooks(bookSetToAdd);
		
		// execute action
		Set<Book> bookSet = runAction_UnavailableBooks_GetAllAsList();

		// validate intermediate cache state
		Assert.isEmpty(bookSet, "No UnavailableBooks records should exist");
		
		// commit transaction
		commitJTATransaction();

		// execute action again
		bookSet = runAction_UnavailableBooks_GetAllAsList();

		// validate cache state
		Assert.equals(bookCount, bookSet.size(), "UnavailableBooks record count should be correct");
		Bookshop2Fixture.assertSameBook(bookSetToAdd, bookSet, "UnavailableBooks records should be equal");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 73)
	public void runTest_UnavailableBooks_GetAllAsList_tx_rollback() throws Exception {
		String testName = "runTest_UnavailableBooks_GetAllAsList_tx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();

		// prepare test data
		int bookCount = 4;
		Set<Book> bookSetToAdd = Bookshop2Fixture.createSet_Book(bookCount);
		bookCacheHelper.addToUnavailableBooks(bookSetToAdd);
		
		// execute action
		Set<Book> bookSet = runAction_UnavailableBooks_GetAllAsList();

		// validate intermediate cache state
		Assert.isEmpty(bookSet, "No UnavailableBooks records should exist");
		
		// rollback transaction
		rollbackJTATransaction();

		// execute action again
		bookSet = runAction_UnavailableBooks_GetAllAsList();

		// validate cache state
		Assert.isEmpty(bookSet, "No UnavailableBooks records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 74)
	public void runTest_UnavailableBooks_GetAllAsList_utx_commit() throws Exception {
		String testName = "runTest_UnavailableBooks_GetAllAsList_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();

		// prepare test data
		int bookCount = 4;
		Set<Book> bookSetToAdd = Bookshop2Fixture.createSet_Book(bookCount);
		bookCacheHelper.addToUnavailableBooks(bookSetToAdd);
		
		// execute action
		Set<Book> bookSet = runAction_UnavailableBooks_GetAllAsList();

		// validate intermediate cache state
		Assert.isEmpty(bookSet, "No UnavailableBooks records should exist");
		
		// commit transaction
		commitUserTransaction();

		// execute action again
		bookSet = runAction_UnavailableBooks_GetAllAsList();

		// validate cache state
		Assert.equals(bookCount, bookSet.size(), "UnavailableBooks record count should be correct");
		Bookshop2Fixture.assertSameBook(bookSetToAdd, bookSet, "UnavailableBooks records should be equal");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 75)
	public void runTest_UnavailableBooks_GetAllAsList_utx_rollback() throws Exception {
		String testName = "runTest_UnavailableBooks_GetAllAsList_utx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();

		// prepare test data
		int bookCount = 4;
		Set<Book> bookSetToAdd = Bookshop2Fixture.createSet_Book(bookCount);
		bookCacheHelper.addToUnavailableBooks(bookSetToAdd);
		
		// execute action
		Set<Book> bookSet = runAction_UnavailableBooks_GetAllAsList();

		// validate intermediate cache state
		Assert.isEmpty(bookSet, "No UnavailableBooks records should exist");
		
		// rollback transaction
		rollbackUserTransaction();

		// execute action again
		bookSet = runAction_UnavailableBooks_GetAllAsList();

		// validate cache state
		Assert.isEmpty(bookSet, "No UnavailableBooks records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public <T> T runAction_UnavailableBooks_GetAllAsList() throws Exception {
		return runAction(new Callable<T>() {
			@SuppressWarnings("unchecked")
			public T call() {
				return (T) bookCacheManager.getAllUnavailableBooks();
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 76)
	public void runTest_UnavailableBooks_GetMatchingAsList_success() throws Exception {
		String testName = "runTest_UnavailableBooks_GetMatchingAsList_success";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 77)
	public void runTest_UnavailableBooks_GetMatchingAsList_tx_commit() throws Exception {
		String testName = "runTest_UnavailableBooks_GetMatchingAsList_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();
		
		
		// commit transaction
		commitJTATransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 78)
	public void runTest_UnavailableBooks_GetMatchingAsList_tx_rollback() throws Exception {
		String testName = "runTest_UnavailableBooks_GetMatchingAsList_tx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();
		
		
		// rollback transaction
		rollbackJTATransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 79)
	public void runTest_UnavailableBooks_GetMatchingAsList_utx_commit() throws Exception {
		String testName = "runTest_UnavailableBooks_GetMatchingAsList_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();
		
		
		// commit transaction
		commitUserTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 80)
	public void runTest_UnavailableBooks_GetMatchingAsList_utx_rollback() throws Exception {
		String testName = "runTest_UnavailableBooks_GetMatchingAsList_utx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();
		
		
		// rollback transaction
		rollbackUserTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public <T> T runAction_UnavailableBooks_GetMatchingAsList(final Collection<Book> bookSet) throws Exception {
		return runAction(new Callable<T>() {
			@SuppressWarnings("unchecked")
			public T call() {
				return (T) bookCacheManager.getMatchingUnavailableBooks(bookSet);
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 81)
	public void runTest_UnavailableBooks_AddAsItem_success() throws Exception {
		String testName = "runTest_UnavailableBooks_AddAsItem_success";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();

		// prepare test data
		Book bookToAdd = Bookshop2Fixture.create_Book();
		
		// execute action
		runAction_UnavailableBooks_AddAsItem(bookToAdd);

		// validate cache state
		Set<Book> bookSet = bookCacheHelper.getAllUnavailableBooks();
		Assert.equals(1, bookSet.size(), "Only 1 UnavailableBooks record should exist");
		Bookshop2Fixture.assertSameBook(bookToAdd, bookSet.iterator().next());
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 82)
	public void runTest_UnavailableBooks_AddAsItem_tx_commit() throws Exception {
		String testName = "runTest_UnavailableBooks_AddAsItem_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();

		// prepare test data
		Book bookToAdd = Bookshop2Fixture.create_Book();
		
		// execute action
		runAction_UnavailableBooks_AddAsItem(bookToAdd);
		
		// commit transaction
		commitJTATransaction();

		// validate cache state
		Set<Book> bookSet = bookCacheHelper.getAllUnavailableBooks();
		Assert.equals(1, bookSet.size(), "Only 1 UnavailableBooks record should exist");
		Bookshop2Fixture.assertSameBook(bookToAdd, bookSet.iterator().next());
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 83)
	public void runTest_UnavailableBooks_AddAsItem_utx_commit() throws Exception {
		String testName = "runTest_UnavailableBooks_AddAsItem_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();

		// prepare test data
		Book bookToAdd = Bookshop2Fixture.create_Book();
		
		// execute action
		runAction_UnavailableBooks_AddAsItem(bookToAdd);
		
		// commit transaction
		commitUserTransaction();

		// validate cache state
		Set<Book> bookSet = bookCacheHelper.getAllUnavailableBooks();
		Assert.equals(1, bookSet.size(), "Only 1 UnavailableBooks record should exist");
		Bookshop2Fixture.assertSameBook(bookToAdd, bookSet.iterator().next());
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 84)
	@BytemanRule(name = "rule84", 
			targetClass = "BookCacheManager", 
			targetMethod = "addToUnavailableBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error84\")")
	public void runTest_UnavailableBooks_AddAsItem_manager_exception_rollback() throws Exception {
		String testName = "runTest_UnavailableBooks_AddAsItem_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookCacheHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		Book bookToAdd = Bookshop2Fixture.create_Book();
		
		try {
			// execute action
			runAction_UnavailableBooks_AddAsItem(bookToAdd);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error84");

			// validate cache state
			Set<Book> bookSet = bookCacheHelper.getAllUnavailableBooks();
			Assert.isEmpty(bookSet, "No UnavailableBooks records should exist");

		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 85)
	@BytemanRule(name = "rule85", 
			targetClass = "BookCacheImpl", 
			targetMethod = "addToUnavailableBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error85\")")
	public void runTest_UnavailableBooks_AddAsItem_cache_exception_rollback() throws Exception {
		String testName = "runTest_UnavailableBooks_AddAsItem_cache_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookCacheHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		Book bookToAdd = Bookshop2Fixture.create_Book();
		
		try {
			// execute action
			runAction_UnavailableBooks_AddAsItem(bookToAdd);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error85");

			// validate cache state
			Set<Book> bookSet = bookCacheHelper.getAllUnavailableBooks();
			Assert.isEmpty(bookSet, "No UnavailableBooks records should exist");

		} finally {
			// cleanup test context
			tearDownByteman(testName);
	}
	
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runAction_UnavailableBooks_AddAsItem(final Book book) throws Exception {
		runAction(new Runnable() {
			public void run() {
				bookCacheManager.addToUnavailableBooks(book);
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 86)
	public void runTest_UnavailableBooks_AddAsList_success() throws Exception {
		String testName = "runTest_UnavailableBooks_AddAsList_success";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();

		// prepare test data
		Set<Book> bookSetToAdd = Bookshop2Fixture.createSet_Book();
		
		// execute action
		runAction_UnavailableBooks_AddAsList(bookSetToAdd);

		// validate cache state
		Set<Book> bookSet = bookCacheHelper.getAllUnavailableBooks();
		Bookshop2Fixture.assertSameBook(bookSetToAdd, bookSet);
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 87)
	public void runTest_UnavailableBooks_AddAsList_tx_commit() throws Exception {
		String testName = "runTest_UnavailableBooks_AddAsList_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();

		// prepare test data
		Set<Book> bookSetToAdd = Bookshop2Fixture.createSet_Book();
		
		// execute action
		runAction_UnavailableBooks_AddAsList(bookSetToAdd);
		
		// commit transaction
		commitJTATransaction();

		// validate cache state
		Set<Book> bookSet = bookCacheHelper.getAllUnavailableBooks();
		Bookshop2Fixture.assertSameBook(bookSetToAdd, bookSet);
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 88)
	public void runTest_UnavailableBooks_AddAsList_utx_commit() throws Exception {
		String testName = "runTest_UnavailableBooks_AddAsList_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();

		// prepare test data
		Set<Book> bookSetToAdd = Bookshop2Fixture.createSet_Book();
		
		// execute action
		runAction_UnavailableBooks_AddAsList(bookSetToAdd);
		
		// commit transaction
		commitUserTransaction();

		// validate cache state
		Set<Book> bookSet = bookCacheHelper.getAllUnavailableBooks();
		Bookshop2Fixture.assertSameBook(bookSetToAdd, bookSet);
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 89)
	@BytemanRule(name = "rule89", 
			targetClass = "BookCacheManager", 
			targetMethod = "addToUnavailableBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error89\")")
	public void runTest_UnavailableBooks_AddAsList_manager_exception_rollback() throws Exception {
		String testName = "runTest_UnavailableBooks_AddAsList_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookCacheHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		Set<Book> bookSetToAdd = Bookshop2Fixture.createSet_Book();
		
		try {
			// execute action
			runAction_UnavailableBooks_AddAsList(bookSetToAdd);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error89");

			// validate cache state
			Set<Book> bookSet = bookCacheHelper.getAllUnavailableBooks();
			Assert.isEmpty(bookSet, "No UnavailableBooks records should exist");

		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 90)
	@BytemanRule(name = "rule90", 
			targetClass = "BookCacheImpl", 
			targetMethod = "addToUnavailableBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error90\")")
	public void runTest_UnavailableBooks_AddAsList_cache_exception_rollback() throws Exception {
		String testName = "runTest_UnavailableBooks_AddAsList_cache_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookCacheHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		Set<Book> bookSetToAdd = Bookshop2Fixture.createSet_Book();
		
		try {
			// execute action
			runAction_UnavailableBooks_AddAsList(bookSetToAdd);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error90");

			// validate cache state
			Set<Book> bookSet = bookCacheHelper.getAllUnavailableBooks();
			Assert.isEmpty(bookSet, "No UnavailableBooks records should exist");

		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runAction_UnavailableBooks_AddAsList(final Collection<Book> bookSet) throws Exception {
		runAction(new Runnable() {
			public void run() {
				bookCacheManager.addToUnavailableBooks(bookSet);
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 91)
	public void runTest_UnavailableBooks_RemoveAll_success() throws Exception {
		String testName = "runTest_UnavailableBooks_RemoveAll_success";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();

		// prepare test data
		int bookCount = 4;
		Set<Book> bookSetToAdd = Bookshop2Fixture.createSet_Book(bookCount);
		bookCacheHelper.addToUnavailableBooks(bookSetToAdd);
		
		// execute action
		runAction_UnavailableBooks_RemoveAll();

		// validate cache state
		Set<Book> bookSet = bookCacheHelper.getAllUnavailableBooks();
		Assert.isEmpty(bookSet, "No UnavailableBooks records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 92)
	public void runTest_UnavailableBooks_RemoveAll_tx_commit() throws Exception {
		String testName = "runTest_UnavailableBooks_RemoveAll_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();

		// prepare test data
		int bookCount = 4;
		Set<Book> bookSetToAdd = Bookshop2Fixture.createSet_Book(bookCount);
		bookCacheHelper.addToUnavailableBooks(bookSetToAdd);
		
		// begin transaction
		beginJTATransaction();
		
		// execute action
		runAction_UnavailableBooks_RemoveAll();
		
		// commit transaction
		commitJTATransaction();

		// validate cache state
		Set<Book> bookSet = bookCacheHelper.getAllUnavailableBooks();
		Assert.isEmpty(bookSet, "No UnavailableBooks records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 93)
	public void runTest_UnavailableBooks_RemoveAll_utx_commit() throws Exception {
		String testName = "runTest_UnavailableBooks_RemoveAll_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();

		// prepare test data
		int bookCount = 4;
		Set<Book> bookSetToAdd = Bookshop2Fixture.createSet_Book(bookCount);
		bookCacheHelper.addToUnavailableBooks(bookSetToAdd);
		
		// begin transaction
		beginUserTransaction();
		
		// execute action
		runAction_UnavailableBooks_RemoveAll();
		
		// commit transaction
		commitUserTransaction();

		// validate cache state
		Set<Book> bookSet = bookCacheHelper.getAllUnavailableBooks();
		Assert.isEmpty(bookSet, "No UnavailableBooks records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 94)
	@BytemanRule(name = "rule94", 
			targetClass = "BookCacheManager", 
			targetMethod = "removeAllUnavailableBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error94\")")
	public void runTest_UnavailableBooks_RemoveAll_manager_exception_rollback() throws Exception {
		String testName = "runTest_UnavailableBooks_RemoveAll_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		setupByteman(testName);
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		int bookCount = 4;
		Set<Book> bookSetToAdd = Bookshop2Fixture.createSet_Book(bookCount);
		bookCacheHelper.addToUnavailableBooks(bookSetToAdd);
		Set<Book> bookSetToRemove = bookCacheHelper.getAllUnavailableBooks();
		Assert.equals(bookCount, bookSetToRemove.size(), "The UnavailableBooks records should exist");
		
		try {
			// execute action
			runAction_UnavailableBooks_RemoveAll();
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error94");

			// validate cache state
			Set<Book> bookSet = bookCacheHelper.getAllUnavailableBooks();
			Bookshop2Fixture.assertSameBook(bookSetToRemove, bookSet);

		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 95)
	@BytemanRule(name = "rule95", 
			targetClass = "BookCacheImpl", 
			targetMethod = "removeAllUnavailableBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error95\")")
	public void runTest_UnavailableBooks_RemoveAll_cache_exception_rollback() throws Exception {
		String testName = "runTest_UnavailableBooks_RemoveAll_cache_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		setupByteman(testName);
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		int bookCount = 4;
		Set<Book> bookSetToAdd = Bookshop2Fixture.createSet_Book(bookCount);
		bookCacheHelper.addToUnavailableBooks(bookSetToAdd);
		Set<Book> bookSetToRemove = bookCacheHelper.getAllUnavailableBooks();
		Assert.equals(bookCount, bookSetToRemove.size(), "The UnavailableBooks records should exist");
		
		try {
			// execute action
			runAction_UnavailableBooks_RemoveAll();
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error95");

			// validate cache state
			Set<Book> bookSet = bookCacheHelper.getAllUnavailableBooks();
			Bookshop2Fixture.assertSameBook(bookSetToRemove, bookSet);

		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runAction_UnavailableBooks_RemoveAll() throws Exception {
		runAction(new Runnable() {
			public void run() {
				bookCacheManager.removeAllUnavailableBooks();
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 96)
	public void runTest_UnavailableBooks_RemoveAsItem_success() throws Exception {
		String testName = "runTest_UnavailableBooks_RemoveAsItem_success";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		Book book = Bookshop2Fixture.create_Book();
		bookCacheHelper.addToUnavailableBooks(book);

		// prepare test data
		Book bookToRemove = bookCacheHelper.getFromUnavailableBooks(book.getId());
		Assert.notNull(bookToRemove, "The UnavailableBooks record should exist");
		
		// execute action
		runAction_UnavailableBooks_RemoveAsItem(bookToRemove);

		// validate cache state
		book = bookCacheHelper.getFromUnavailableBooks(book.getId());
		Assert.isNull(book, "The UnavailableBooks record should be removed");
		Set<Book> bookSetRemaining = bookCacheHelper.getAllUnavailableBooks();
		Assert.isEmpty(bookSetRemaining, "No UnavailableBooks records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 97)
	public void runTest_UnavailableBooks_RemoveAsItem_tx_commit() throws Exception {
		String testName = "runTest_UnavailableBooks_RemoveAsItem_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		Book book = Bookshop2Fixture.create_Book();
		bookCacheHelper.addToUnavailableBooks(book);
		
		// begin transaction
		beginJTATransaction();

		// prepare test data
		Book bookToRemove = bookCacheHelper.getFromUnavailableBooks(book.getId());
		Assert.notNull(bookToRemove, "The UnavailableBooks record should exist");
		
		// execute action
		runAction_UnavailableBooks_RemoveAsItem(bookToRemove);
		
		// commit transaction
		commitJTATransaction();

		// validate cache state
		book = bookCacheHelper.getFromUnavailableBooks(book.getId());
		Assert.isNull(book, "The UnavailableBooks record should be removed");
		Set<Book> bookSetRemaining = bookCacheHelper.getAllUnavailableBooks();
		Assert.isEmpty(bookSetRemaining, "No UnavailableBooks records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 98)
	public void runTest_UnavailableBooks_RemoveAsItem_utx_commit() throws Exception {
		String testName = "runTest_UnavailableBooks_RemoveAsItem_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookCacheHelper.assureRemoveAll();
		Book book = Bookshop2Fixture.create_Book();
		bookCacheHelper.addToUnavailableBooks(book);
		
		// begin transaction
		beginUserTransaction();

		// prepare test data
		Book bookToRemove = bookCacheHelper.getFromUnavailableBooks(book.getId());
		Assert.notNull(bookToRemove, "The UnavailableBooks record should exist");
		
		// execute action
		runAction_UnavailableBooks_RemoveAsItem(bookToRemove);
		
		// commit transaction
		commitUserTransaction();

		// validate cache state
		book = bookCacheHelper.getFromUnavailableBooks(book.getId());
		Assert.isNull(book, "The UnavailableBooks record should be removed");
		Set<Book> bookSetRemaining = bookCacheHelper.getAllUnavailableBooks();
		Assert.isEmpty(bookSetRemaining, "No UnavailableBooks records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 99)
	@BytemanRule(name = "rule99", 
			targetClass = "BookCacheManager", 
			targetMethod = "removeFromUnavailableBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error99\")")
	public void runTest_UnavailableBooks_RemoveAsItem_manager_exception_rollback() throws Exception {
		String testName = "runTest_UnavailableBooks_RemoveAsItem_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookCacheHelper.assureRemoveAll();
		Book book = Bookshop2Fixture.create_Book();
		bookCacheHelper.addToUnavailableBooks(book);
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		Book bookToRemove = bookCacheHelper.getFromUnavailableBooks(book.getId());
		Assert.notNull(bookToRemove, "The UnavailableBooks record should exist");
		
		try {
			// execute action
			runAction_UnavailableBooks_RemoveAsItem(bookToRemove);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error99");

			// validate cache state
			book = bookCacheHelper.getFromUnavailableBooks(book.getId());
			Assert.notNull(book, "The UnavailableBooks record should still exist");
			Set<Book> bookSet = bookCacheHelper.getAllUnavailableBooks();
			Assert.equals(1, bookSet.size(), "Only 1 UnavailableBooks record should exist");

		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 100)
	@BytemanRule(name = "rule100", 
			targetClass = "BookCacheImpl", 
			targetMethod = "removeFromUnavailableBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error100\")")
	public void runTest_UnavailableBooks_RemoveAsItem_cache_exception_rollback() throws Exception {
		String testName = "runTest_UnavailableBooks_RemoveAsItem_cache_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookCacheHelper.assureRemoveAll();
		Book book = Bookshop2Fixture.create_Book();
		bookCacheHelper.addToUnavailableBooks(book);
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		Book bookToRemove = bookCacheHelper.getFromUnavailableBooks(book.getId());
		Assert.notNull(bookToRemove, "The UnavailableBooks record should exist");
		
		try {
			// execute action
			runAction_UnavailableBooks_RemoveAsItem(bookToRemove);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error100");

			// validate cache state
			book = bookCacheHelper.getFromUnavailableBooks(book.getId());
			Assert.notNull(book, "The UnavailableBooks record should still exist");
			Set<Book> bookSet = bookCacheHelper.getAllUnavailableBooks();
			Assert.equals(1, bookSet.size(), "Only 1 UnavailableBooks record should exist");

		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runAction_UnavailableBooks_RemoveAsItem(final Book book) throws Exception {
		runAction(new Runnable() {
			public void run() {
				bookCacheManager.removeFromUnavailableBooks(book);
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 101)
	public void runTest_UnavailableBooks_RemoveAsList_success() throws Exception {
		String testName = "runTest_UnavailableBooks_RemoveAsList_success";
		log.info(testName+": started");
		
		// prepare test context
		int bookCount = 4;
		bookCacheHelper.assureRemoveAll();
		List<Book> bookList = Bookshop2Fixture.createList_Book(bookCount);
		bookCacheHelper.addToUnavailableBooks(bookList);

		// prepare test data
		Set<Book> bookSetToRemove = bookCacheHelper.getAllUnavailableBooks();
		Assert.equals(bookCount, bookSetToRemove.size(), "The UnavailableBooks records should exist");
		
		// execute action
		runAction_UnavailableBooks_RemoveAsList(bookSetToRemove);

		// validate cache state
		Set<Book> bookSetRemaining = bookCacheHelper.getAllUnavailableBooks();
		Assert.isEmpty(bookSetRemaining, "No UnavailableBooks records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 102)
	public void runTest_UnavailableBooks_RemoveAsList_tx_commit() throws Exception {
		String testName = "runTest_UnavailableBooks_RemoveAsList_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		int bookCount = 4;
		bookCacheHelper.assureRemoveAll();
		List<Book> bookList = Bookshop2Fixture.createList_Book(bookCount);
		bookCacheHelper.addToUnavailableBooks(bookList);
		
		// begin transaction
		beginJTATransaction();

		// prepare test data
		Set<Book> bookSetToRemove = bookCacheHelper.getAllUnavailableBooks();
		Assert.equals(bookCount, bookSetToRemove.size(), "The UnavailableBooks records should exist");
		
		// execute action
		runAction_UnavailableBooks_RemoveAsList(bookSetToRemove);
		
		// commit transaction
		commitJTATransaction();

		// validate cache state
		Set<Book> bookSetRemaining = bookCacheHelper.getAllUnavailableBooks();
		Assert.isEmpty(bookSetRemaining, "No UnavailableBooks records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 103)
	public void runTest_UnavailableBooks_RemoveAsList_utx_commit() throws Exception {
		String testName = "runTest_UnavailableBooks_RemoveAsList_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		int bookCount = 4;
		bookCacheHelper.assureRemoveAll();
		List<Book> bookList = Bookshop2Fixture.createList_Book(bookCount);
		bookCacheHelper.addToUnavailableBooks(bookList);
		
		// begin transaction
		beginUserTransaction();

		// prepare test data
		Set<Book> bookSetToRemove = bookCacheHelper.getAllUnavailableBooks();
		Assert.equals(bookCount, bookSetToRemove.size(), "The UnavailableBooks records should exist");
		
		// execute action
		runAction_UnavailableBooks_RemoveAsList(bookSetToRemove);
		
		// commit transaction
		commitUserTransaction();

		// validate cache state
		Set<Book> bookSetRemaining = bookCacheHelper.getAllUnavailableBooks();
		Assert.isEmpty(bookSetRemaining, "No UnavailableBooks records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 104)
	@BytemanRule(name = "rule104", 
			targetClass = "BookCacheManager", 
			targetMethod = "removeFromUnavailableBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error104\")")
	public void runTest_UnavailableBooks_RemoveAsList_manager_exception_rollback() throws Exception {
		String testName = "runTest_UnavailableBooks_RemoveAsList_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		int bookCount = 4;
		bookCacheHelper.assureRemoveAll();
		List<Book> bookList = Bookshop2Fixture.createList_Book(bookCount);
		bookCacheHelper.addToUnavailableBooks(bookList);
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		Set<Book> bookSetToRemove = bookCacheHelper.getAllUnavailableBooks();
		Assert.equals(bookCount, bookSetToRemove.size(), "The UnavailableBooks records should exist");
		
		try {
			// execute action
			runAction_UnavailableBooks_RemoveAsList(bookSetToRemove);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error104");

			// validate cache state
			Set<Book> bookSetRemaining = bookCacheHelper.getAllUnavailableBooks();
			Assert.equals(bookCount, bookSetRemaining.size(), "The UnavailableBooks records should still exist");
			Bookshop2Fixture.assertSameBook(bookSetRemaining, bookSetToRemove);

		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 105)
	@BytemanRule(name = "rule105", 
			targetClass = "BookCacheImpl", 
			targetMethod = "removeFromUnavailableBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error105\")")
	public void runTest_UnavailableBooks_RemoveAsList_cache_exception_rollback() throws Exception {
		String testName = "runTest_UnavailableBooks_RemoveAsList_cache_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		int bookCount = 4;
		bookCacheHelper.assureRemoveAll();
		List<Book> bookList = Bookshop2Fixture.createList_Book(bookCount);
		bookCacheHelper.addToUnavailableBooks(bookList);
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		Set<Book> bookSetToRemove = bookCacheHelper.getAllUnavailableBooks();
		Assert.equals(bookCount, bookSetToRemove.size(), "The UnavailableBooks records should exist");
		
		try {
			// execute action
			runAction_UnavailableBooks_RemoveAsList(bookSetToRemove);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error105");

			// validate cache state
			Set<Book> bookSetRemaining = bookCacheHelper.getAllUnavailableBooks();
			Assert.equals(bookCount, bookSetRemaining.size(), "The UnavailableBooks records should still exist");
			Bookshop2Fixture.assertSameBook(bookSetRemaining, bookSetToRemove);

		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runAction_UnavailableBooks_RemoveAsList(final Collection<Book> bookSet) throws Exception {
		runAction(new Runnable() {
			public void run() {
				bookCacheManager.removeFromUnavailableBooks(bookSet);
			}
		});
	}
	
	public void runAction(Runnable runnable) throws Exception {
		try {
			runnable.run();
			if (isExceptionExpected) {
			if (isTransactional())
					clearTransaction();
				errorMessage = "Exception should have been thrown";
			}

		} catch (Exception e) {
			throw e;
			}
		}
	
	public <T> T runAction(Callable<T> callable) throws Exception {
		try {
			T object = callable.call();
			if (isExceptionExpected) {
				if (isTransactional())
					clearTransaction();
				errorMessage = "Exception should have been thrown";
			}
				return object;
				
		} catch (Exception e) {
			throw e;
		}
	}

}
