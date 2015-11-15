package bookshop2.supplier.data.supplierOrderCache;

import java.util.Collection;
import java.util.List;
import java.util.Map;
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
import bookshop2.BookKey;
import bookshop2.supplier.SupplierTestEARBuilder;
import bookshop2.supplier.SupplierTestWARBuilder;
import bookshop2.util.Bookshop2Fixture;
import bookshop2.util.Bookshop2Helper;

import common.tx.state.AbstractStateManager;


@RunWith(Arquillian.class)
public class SupplierOrderCacheManagerCIT extends AbstractCacheUnitArquillionTest {

	@Inject
	private SupplierOrderCacheManager supplierOrderCacheManager;

	@Inject
	private SupplierOrderCacheHelper supplierOrderCacheHelper;
	
	
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
		return SupplierOrderCacheManagerCIT.class;
	}

	@Override
	public String getServerName() {
		return "hornetQ01_local";
	}

	@Override
	public String getDomainId() {
		return "bookshop2.supplier";
	}

	@Override
	public String getTargetArchive() {
		return SupplierTestEARBuilder.NAME;
	}

	@Override
	public AbstractStateManager<?> getStateManager() {
		return supplierOrderCacheManager;
	}

	@Before
	public void setUp() throws Exception {
		if (!isServerStarted()) {
			startServer();
		} else {
			super.setUp();
			createTransactionControl();
			initializeSupplierOrderCacheHelper();
		}
	}

	protected void createTransactionControl() throws Exception {
		transactionTestControl = new TransactionTestControl();
		transactionTestControl.assureTransactionManager();
	}
	
	protected void initializeSupplierOrderCacheHelper() throws Exception {
		supplierOrderCacheHelper.setProxy(createSupplierOrderCacheProxy());
		supplierOrderCacheHelper.initialize(createSupplierOrderCacheControl());
	}
	
	protected SupplierOrderCacheProxy createSupplierOrderCacheProxy() throws Exception {
		SupplierOrderCacheProxy supplierOrderCacheProxy = new SupplierOrderCacheProxy();
		supplierOrderCacheProxy.setJmxManager(jmxManager);
		return supplierOrderCacheProxy;
	}
	
	protected CacheModuleTestControl createSupplierOrderCacheControl() throws Exception {
		CacheModuleTestControl supplierOrderCacheControl = new CacheModuleTestControl(transactionTestControl);
		supplierOrderCacheControl.setupCacheLayer();
		return supplierOrderCacheControl;
	}
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
		clearTransaction();
	}
	
	@TargetsContainer("hornetQ01_local")
	@Deployment(name = "supplierEAR", order = 1)
	public static EnterpriseArchive createSupplierEAR() {
		SupplierTestEARBuilder builder = new SupplierTestEARBuilder();
		builder.addAsModule(createSupplierWAR(), "supplier");
		EnterpriseArchive ear = builder.createEAR();
		return ear;
	}

	public static WebArchive createSupplierWAR() {
		SupplierTestWARBuilder builder = new SupplierTestWARBuilder("test.war");
		builder.addClass(SupplierOrderCacheManagerCIT.class);
		WebArchive war = builder.create();
		return war;
	}
	
	@Test
	//@Ignore
	@InSequence(value = 1)
	public void runTest_BooksInStock_GetAllAsList_success() throws Exception {
		String testName = "runTest_BooksInStock_GetAllAsList_success";
		log.info(testName+": started");
		
		// prepare test context
		supplierOrderCacheHelper.assureRemoveAll();
		
		// prepare test data
		int bookCount = 4;
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book(bookCount);
		supplierOrderCacheHelper.addToBooksInStock(bookListToAdd);
		
		// execute action
		List<Book> bookList = runAction_BooksInStock_GetAllAsList();
		
		// validate cache state
		Assert.equals(bookCount, bookList.size(), "BooksInStock record count should be correct");
		Bookshop2Fixture.assertSameBook(bookListToAdd, bookList, "BooksInStock records should be equal");
				
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 2)
	public void runTest_BooksInStock_GetAllAsList_tx_commit() throws Exception {
		String testName = "runTest_BooksInStock_GetAllAsList_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		supplierOrderCacheHelper.addToBooksInStock();		
		supplierOrderCacheHelper.assureRemoveAll();
		List<Book> allBooksInStock = supplierOrderCacheHelper.getAllBooksInStock();
		
		// begin transaction
		beginJTATransaction();
		
		// prepare test data
		int bookCount = 4;
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book(bookCount);
		supplierOrderCacheHelper.addToBooksInStock(bookListToAdd);

		// execute action
		List<Book> bookList = runAction_BooksInStock_GetAllAsList();
		
		// validate intermediate cache state
		Assert.isEmpty(bookList, "No BooksInStock records should exist");
					
		// commit transaction
		commitJTATransaction();
		
		// execute action again
		bookList = runAction_BooksInStock_GetAllAsList();

		// validate cache state
		Assert.equals(bookCount, bookList.size(), "BooksInStock record count should be correct");
		Bookshop2Fixture.assertSameBook(bookListToAdd, bookList, "BooksInStock records should be equal");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 3)
	public void runTest_BooksInStock_GetAllAsList_tx_rollback() throws Exception {
		String testName = "runTest_BooksInStock_GetAllAsList_tx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		supplierOrderCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();

		// prepare test data
		int bookCount = 4;
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book(bookCount);
		supplierOrderCacheHelper.addToBooksInStock(bookListToAdd);

		// execute action
		List<Book> bookList = runAction_BooksInStock_GetAllAsList();
		
		// validate intermediate cache state
		Assert.isEmpty(bookList, "No BooksInStock records should exist");
					
		// rollback transaction
		rollbackJTATransaction();
		
		// execute action again
		bookList = runAction_BooksInStock_GetAllAsList();
		
		// validate cache state
		Assert.isEmpty(bookList, "No BooksInStock records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 4)
	public void runTest_BooksInStock_GetAllAsList_utx_commit() throws Exception {
		String testName = "runTest_BooksInStock_GetAllAsList_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		supplierOrderCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();
		
		// prepare test data
		int bookCount = 4;
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book(bookCount);
		supplierOrderCacheHelper.addToBooksInStock(bookListToAdd);

		// execute action
		List<Book> bookList = runAction_BooksInStock_GetAllAsList();
		
		// validate intermediate cache state
		Assert.isEmpty(bookList, "No BooksInStock records should exist");
					
		// commit transaction
		commitUserTransaction();
		
		// execute action again
		bookList = runAction_BooksInStock_GetAllAsList();

		// validate cache state
		Assert.equals(bookCount, bookList.size(), "BooksInStock record count should be correct");
		Bookshop2Fixture.assertSameBook(bookListToAdd, bookList, "BooksInStock records should be equal");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 5)
	public void runTest_BooksInStock_GetAllAsList_utx_rollback() throws Exception {
		String testName = "runTest_BooksInStock_GetAllAsList_utx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		supplierOrderCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();
		
		// prepare test data
		int bookCount = 4;
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book(bookCount);
		supplierOrderCacheHelper.addToBooksInStock(bookListToAdd);

		// execute action
		List<Book> bookList = runAction_BooksInStock_GetAllAsList();
		
		// validate intermediate cache state
		Assert.isEmpty(bookList, "No BooksInStock records should exist");
					
		// rollback transaction
		rollbackUserTransaction();
		
		// execute action again
		bookList = runAction_BooksInStock_GetAllAsList();
		
		// validate cache state
		Assert.isEmpty(bookList, "No BooksInStock records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}

	public <T> T runAction_BooksInStock_GetAllAsList() throws Exception {
		return runAction(new Callable<T>() {
			@SuppressWarnings("unchecked")
			public T call() {
				return (T) supplierOrderCacheManager.getAllBooksInStock();
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 6)
	public void runTest_BooksInStock_GetMatchingAsMap_success() throws Exception {
		String testName = "runTest_BooksInStock_GetMatchingAsMap_success";
		log.info(testName+": started");
		
		// prepare test context
		supplierOrderCacheHelper.assureRemoveAll();
		
		// prepare unmatching data
		Book bookToAdd = Bookshop2Fixture.create_Book();
		supplierOrderCacheHelper.addToBooksInStock(bookToAdd);

		// prepare matching data
		int bookCount = 4;
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book(bookCount);
		supplierOrderCacheHelper.addToBooksInStock(bookListToAdd);
		
		// execute action
		Map<BookKey, Book> bookMap = runAction_BooksInStock_GetMatchingAsMap(bookListToAdd);
		
		// validate cache state
		Assert.equals(bookCount, bookMap.size(), "BooksInStock record count should be correct");
		Bookshop2Fixture.assertSameBook(bookListToAdd, bookMap.values(), "BooksInStock records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 7)
	public void runTest_BooksInStock_GetMatchingAsMap_tx_commit() throws Exception {
		String testName = "runTest_BooksInStock_GetMatchingAsMap_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		supplierOrderCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();
		
		// prepare unmatching data
		Book bookToAdd = Bookshop2Fixture.create_Book();
		supplierOrderCacheHelper.addToBooksInStock(bookToAdd);

		// prepare matching data
		int bookCount = 4;
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book(bookCount);
		supplierOrderCacheHelper.addToBooksInStock(bookListToAdd);
		
		// execute action
		Map<BookKey, Book> bookMap = runAction_BooksInStock_GetMatchingAsMap(bookListToAdd);
		
		// validate intermediate cache state
		Assert.isEmpty(bookMap.values(), "No BooksInStock records should exist");
					
		// commit transaction
		commitJTATransaction();
		
		// execute action again
		bookMap = runAction_BooksInStock_GetMatchingAsMap(bookListToAdd);

		// validate cache state
		Assert.equals(bookCount, bookMap.size(), "BooksInStock record count should be correct");
		Bookshop2Fixture.assertSameBook(bookListToAdd, bookMap.values(), "BooksInStock records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 8)
	public void runTest_BooksInStock_GetMatchingAsMap_tx_rollback() throws Exception {
		String testName = "runTest_BooksInStock_GetMatchingAsMap_tx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		supplierOrderCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();
		
		// prepare unmatching data
		Book bookToAdd = Bookshop2Fixture.create_Book();
		supplierOrderCacheHelper.addToBooksInStock(bookToAdd);

		// prepare matching data
		int bookCount = 4;
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book(bookCount);
		supplierOrderCacheHelper.addToBooksInStock(bookListToAdd);
		
		// execute action
		Map<BookKey, Book> bookMap = runAction_BooksInStock_GetMatchingAsMap(bookListToAdd);
		
		// validate intermediate cache state
		Assert.isEmpty(bookMap.values(), "No BooksInStock records should exist");
					
		// rollback transaction
		rollbackJTATransaction();
		
		// execute action again
		bookMap = runAction_BooksInStock_GetMatchingAsMap(bookListToAdd);

		// validate cache state
		Assert.isEmpty(bookMap.values(), "No BooksInStock records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 9)
	public void runTest_BooksInStock_GetMatchingAsMap_utx_commit() throws Exception {
		String testName = "runTest_BooksInStock_GetMatchingAsMap_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		supplierOrderCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();
		
		// prepare unmatching data
		Book bookToAdd = Bookshop2Fixture.create_Book();
		supplierOrderCacheHelper.addToBooksInStock(bookToAdd);

		// prepare matching data
		int bookCount = 4;
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book(bookCount);
		supplierOrderCacheHelper.addToBooksInStock(bookListToAdd);
		
		// execute action
		Map<BookKey, Book> bookMap = runAction_BooksInStock_GetMatchingAsMap(bookListToAdd);
		
		// validate intermediate cache state
		Assert.isEmpty(bookMap.values(), "No BooksInStock records should exist");
					
		// commit transaction
		commitUserTransaction();
		
		// execute action again
		bookMap = runAction_BooksInStock_GetMatchingAsMap(bookListToAdd);

		// validate cache state
		Assert.equals(bookCount, bookMap.size(), "BooksInStock record count should be correct");
		Bookshop2Fixture.assertSameBook(bookListToAdd, bookMap.values(), "BooksInStock records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}

	@Test
	//@Ignore
	@InSequence(value = 10)
	public void runTest_BooksInStock_GetMatchingAsMap_utx_rollback() throws Exception {
		String testName = "runTest_BooksInStock_GetMatchingAsMap_utx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		supplierOrderCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();
		
		// prepare unmatching data
		Book bookToAdd = Bookshop2Fixture.create_Book();
		supplierOrderCacheHelper.addToBooksInStock(bookToAdd);

		// prepare matching data
		int bookCount = 4;
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book(bookCount);
		supplierOrderCacheHelper.addToBooksInStock(bookListToAdd);
		
		// execute action
		Map<BookKey, Book> bookMap = runAction_BooksInStock_GetMatchingAsMap(bookListToAdd);
		
		// validate intermediate cache state
		Assert.isEmpty(bookMap.values(), "No BooksInStock records should exist");
					
		// rollback transaction
		rollbackUserTransaction();
		
		// execute action again
		bookMap = runAction_BooksInStock_GetMatchingAsMap(bookListToAdd);

		// validate cache state
		Assert.isEmpty(bookMap.values(), "No BooksInStock records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public <T> T runAction_BooksInStock_GetMatchingAsMap(final Collection<Book> bookList) throws Exception {
		return runAction(new Callable<T>() {
			@SuppressWarnings("unchecked")
			public T call() {
				return (T) supplierOrderCacheManager.getMatchingBooksInStockAsMap(bookList);
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 11)
	public void runTest_BooksInStock_AddAsItem_success() throws Exception {
		String testName = "runTest_BooksInStock_AddAsItem_success";
		log.info(testName+": started");
		
		// prepare test context
		supplierOrderCacheHelper.assureRemoveAll();
		
		// prepare test data
		Book bookToAdd = Bookshop2Fixture.create_Book();
		
		// execute action
		runAction_BooksInStock_AddAsItem(bookToAdd);
		
		// validate cache state
		List<Book> bookList = supplierOrderCacheHelper.getAllBooksInStock();
		Assert.equals(1, bookList.size(), "Only 1 BooksInStock record should exist");
		Bookshop2Fixture.assertSameBook(bookToAdd, bookList.iterator().next());
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 12)
	public void runTest_BooksInStock_AddAsItem_tx_commit() throws Exception {
		String testName = "runTest_BooksInStock_AddAsItem_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		supplierOrderCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();

		// prepare test data
		Book bookToAdd = Bookshop2Fixture.create_Book();
		
		// execute action
		runAction_BooksInStock_AddAsItem(bookToAdd);
		
		// commit transaction
		commitJTATransaction();
		
		// validate cache state
		List<Book> bookList = supplierOrderCacheHelper.getAllBooksInStock();
		Assert.equals(1, bookList.size(), "Only 1 BooksInStock record should exist");
		Bookshop2Fixture.assertSameBook(bookToAdd, bookList.iterator().next());
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 13)
	public void runTest_BooksInStock_AddAsItem_utx_commit() throws Exception {
		String testName = "runTest_BooksInStock_AddAsItem_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		supplierOrderCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();

		// prepare test data
		Book bookToAdd = Bookshop2Fixture.create_Book();
		
		// execute action
		runAction_BooksInStock_AddAsItem(bookToAdd);
		
		// commit transaction
		commitUserTransaction();
		
		// validate cache state
		List<Book> bookList = supplierOrderCacheHelper.getAllBooksInStock();
		Assert.equals(1, bookList.size(), "Only 1 BooksInStock record should exist");
		Bookshop2Fixture.assertSameBook(bookToAdd, bookList.iterator().next());
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 14)
	@BytemanRule(name = "rule14", 
			targetClass = "SupplierOrderCacheManager", 
			targetMethod = "addToBooksInStock", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error14\")")
	public void runTest_BooksInStock_AddAsItem_manager_exception_rollback() throws Exception {
		String testName = "runTest_BooksInStock_AddAsItem_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		supplierOrderCacheHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;
		
		// prepare test data
		Book bookToAdd = Bookshop2Fixture.create_Book();
		
		try {
			// execute action
			runAction_BooksInStock_AddAsItem(bookToAdd);
			fail("Exception should have been thrown");
			
		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error14");
			
			// validate cache state
			List<Book> bookList = supplierOrderCacheHelper.getAllBooksInStock();
			Assert.isEmpty(bookList, "No BooksInStock records should exist");
		
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
			targetClass = "SupplierOrderCacheImpl", 
			targetMethod = "addToBooksInStock", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error15\")")
	public void runTest_BooksInStock_AddAsItem_cache_exception_rollback() throws Exception {
		String testName = "runTest_BooksInStock_AddAsItem_cache_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		supplierOrderCacheHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;
		
		// prepare test data
		Book bookToAdd = Bookshop2Fixture.create_Book();
		
		try {
		// execute action
			runAction_BooksInStock_AddAsItem(bookToAdd);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error15");
			
			// validate cache state
			List<Book> bookList = supplierOrderCacheHelper.getAllBooksInStock();
			Assert.isEmpty(bookList, "No BooksInStock records should exist");
			
		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runAction_BooksInStock_AddAsItem(final Book book) throws Exception {
		runAction(new Runnable() {
			public void run() {
				supplierOrderCacheManager.addToBooksInStock(book);
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 16)
	public void runTest_BooksInStock_AddAsList_success() throws Exception {
		String testName = "runTest_BooksInStock_AddAsList_success";
		log.info(testName+": started");
		
		// prepare test context
		supplierOrderCacheHelper.assureRemoveAll();
		
		// prepare test data
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book();
		
		// execute action
		runAction_BooksInStock_AddAsList(bookListToAdd);
		
		// validate cache state
		List<Book> bookList = supplierOrderCacheHelper.getAllBooksInStock();
		Bookshop2Fixture.assertSameBook(bookListToAdd, bookList);
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 17)
	public void runTest_BooksInStock_AddAsList_tx_commit() throws Exception {
		String testName = "runTest_BooksInStock_AddAsList_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		supplierOrderCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();

		// prepare test data
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book();
		
		// execute action
		runAction_BooksInStock_AddAsList(bookListToAdd);
		
		// commit transaction
		commitJTATransaction();
		
		// validate cache state
		List<Book> bookList = supplierOrderCacheHelper.getAllBooksInStock();
		Bookshop2Fixture.assertSameBook(bookListToAdd, bookList);
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 18)
	public void runTest_BooksInStock_AddAsList_utx_commit() throws Exception {
		String testName = "runTest_BooksInStock_AddAsList_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		supplierOrderCacheHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();

		// prepare test data
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book();
		
		// execute action
		runAction_BooksInStock_AddAsList(bookListToAdd);
		
		// commit transaction
		commitUserTransaction();
		
		// validate cache state
		List<Book> bookList = supplierOrderCacheHelper.getAllBooksInStock();
		Bookshop2Fixture.assertSameBook(bookListToAdd, bookList);
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 19)
	@BytemanRule(name = "rule19", 
			targetClass = "SupplierOrderCacheManager",
			targetMethod = "addToBooksInStock",
			targetLocation = "AT EXIT",
			action = "throw new org.aries.ApplicationFailure(\"error19\")")
	public void runTest_BooksInStock_AddAsList_manager_exception_rollback() throws Exception {
		String testName = "runTest_BooksInStock_AddAsList_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		supplierOrderCacheHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;
		
		// prepare test data
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book();
		
		try {
			// execute action
			runAction_BooksInStock_AddAsList(bookListToAdd);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error19");
			
			// validate cache state
			List<Book> bookList = supplierOrderCacheHelper.getAllBooksInStock();
			Assert.isEmpty(bookList, "No BooksInStock records should exist");
			
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
			targetClass = "SupplierOrderCacheImpl",
			targetMethod = "addToBooksInStock",
			targetLocation = "AT EXIT",
			action = "throw new org.aries.ApplicationFailure(\"error20\")")
	public void runTest_BooksInStock_AddAsList_cache_exception_rollback() throws Exception {
		String testName = "runTest_BooksInStock_AddAsList_cache_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		supplierOrderCacheHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;
		
		// prepare test data
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book();
		
		try {
			// execute action
			runAction_BooksInStock_AddAsList(bookListToAdd);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error20");
			
			// validate cache state
			List<Book> bookList = supplierOrderCacheHelper.getAllBooksInStock();
			Assert.isEmpty(bookList, "No BooksInStock records should exist");
			
		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runAction_BooksInStock_AddAsList(final Collection<Book> bookList) throws Exception {
		runAction(new Runnable() {
			public void run() {
				supplierOrderCacheManager.addToBooksInStock(bookList);
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 21)
	public void runTest_BooksInStock_RemoveAll_success() throws Exception {
		String testName = "runTest_BooksInStock_RemoveAll_success";
		log.info(testName+": started");
		
		// prepare test context
		supplierOrderCacheHelper.assureRemoveAll();

		// prepare test data
		int bookCount = 4;
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book(bookCount);
		supplierOrderCacheHelper.addToBooksInStock(bookListToAdd);
		
		// execute action
		runAction_BooksInStock_RemoveAll();
		
		// validate cache state
		List<Book> bookList = supplierOrderCacheHelper.getAllBooksInStock();
		Assert.isEmpty(bookList, "No BooksInStock records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 22)
	public void runTest_BooksInStock_RemoveAll_tx_commit() throws Exception {
		String testName = "runTest_BooksInStock_RemoveAll_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		supplierOrderCacheHelper.assureRemoveAll();
		
		// prepare test data
		int bookCount = 4;
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book(bookCount);
		supplierOrderCacheHelper.addToBooksInStock(bookListToAdd);
		
		// begin transaction
		beginJTATransaction();
		
		// execute action
		runAction_BooksInStock_RemoveAll();
		
		// commit transaction
		commitJTATransaction();
		
		// validate cache state
		List<Book> bookList = supplierOrderCacheHelper.getAllBooksInStock();
		Assert.isEmpty(bookList, "No BooksInStock records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 23)
	public void runTest_BooksInStock_RemoveAll_utx_commit() throws Exception {
		String testName = "runTest_BooksInStock_RemoveAll_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		supplierOrderCacheHelper.assureRemoveAll();
		
		// prepare test data
		int bookCount = 4;
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book(bookCount);
		supplierOrderCacheHelper.addToBooksInStock(bookListToAdd);
		
		// begin transaction
		beginUserTransaction();
		
		// execute action
		runAction_BooksInStock_RemoveAll();
		
		// commit transaction
		commitUserTransaction();
		
		// validate cache state
		List<Book> bookList = supplierOrderCacheHelper.getAllBooksInStock();
		Assert.isEmpty(bookList, "No BooksInStock records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 24)
	@BytemanRule(name = "rule24", 
			targetClass = "SupplierOrderCacheManager", 
			targetMethod = "removeAllBooksInStock", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error24\")")
	public void runTest_BooksInStock_RemoveAll_manager_exception_rollback() throws Exception {
		String testName = "runTest_BooksInStock_RemoveAll_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		supplierOrderCacheHelper.assureRemoveAll();
		setupByteman(testName);
		
		// setup expectations
		isExceptionExpected = true;
		
		// prepare test data
		int bookCount = 4;
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book(bookCount);
		supplierOrderCacheHelper.addToBooksInStock(bookListToAdd);
		List<Book> bookListToRemove = supplierOrderCacheHelper.getAllBooksInStock();
		Assert.equals(bookCount, bookListToRemove.size(), "The BooksInStock records should exist");
		
		try {
			// execute action
			runAction_BooksInStock_RemoveAll();
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error24");

			// validate cache state
			List<Book> bookList = supplierOrderCacheHelper.getAllBooksInStock();
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
			targetClass = "SupplierOrderCacheImpl", 
			targetMethod = "removeAllBooksInStock", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error25\")")
	public void runTest_BooksInStock_RemoveAll_cache_exception_rollback() throws Exception {
		String testName = "runTest_BooksInStock_RemoveAll_cache_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		supplierOrderCacheHelper.assureRemoveAll();
		setupByteman(testName);

		// setup expectations
		isExceptionExpected = true;
		
		// prepare test data
		int bookCount = 4;
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book(bookCount);
		supplierOrderCacheHelper.addToBooksInStock(bookListToAdd);
		List<Book> bookListToRemove = supplierOrderCacheHelper.getAllBooksInStock();
		Assert.equals(bookCount, bookListToRemove.size(), "The BooksInStock records should exist");
		
		try {
			// execute action
			runAction_BooksInStock_RemoveAll();
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error25");

			// validate cache state
			List<Book> bookList = supplierOrderCacheHelper.getAllBooksInStock();
			Bookshop2Fixture.assertSameBook(bookListToRemove, bookList);

		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runAction_BooksInStock_RemoveAll() throws Exception {
		runAction(new Runnable() {
			public void run() {
				supplierOrderCacheManager.removeAllBooksInStock();
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 26)
	public void runTest_BooksInStock_RemoveAsItem_success() throws Exception {
		String testName = "runTest_BooksInStock_RemoveAsItem_success";
		log.info(testName+": started");
		
		// prepare test context
		supplierOrderCacheHelper.assureRemoveAll();
		Book book = Bookshop2Fixture.create_Book();
		BookKey bookKey = Bookshop2Helper.createBookKey(book);
		supplierOrderCacheHelper.addToBooksInStock(bookKey, book);
		
		// prepare test data
		Book bookToRemove = supplierOrderCacheHelper.getFromBooksInStock(bookKey);
		Assert.notNull(bookToRemove, "The BooksInStock record should exist");
		
		// execute action
		runAction_BooksInStock_RemoveAsItem(bookToRemove);
		
		// validate cache state
		book = supplierOrderCacheHelper.getFromBooksInStock(bookKey);
		Assert.isNull(book, "The BooksInStock record should be removed");
		List<Book> bookListRemaining = supplierOrderCacheHelper.getAllBooksInStock();
		Assert.isEmpty(bookListRemaining, "No BooksInStock records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 27)
	public void runTest_BooksInStock_RemoveAsItem_tx_commit() throws Exception {
		String testName = "runTest_BooksInStock_RemoveAsItem_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		supplierOrderCacheHelper.assureRemoveAll();
		Book book = Bookshop2Fixture.create_Book();
		BookKey bookKey = Bookshop2Helper.createBookKey(book);
		supplierOrderCacheHelper.addToBooksInStock(bookKey, book);
		
		// begin transaction
		beginJTATransaction();

		// prepare test data
		Book bookToRemove = supplierOrderCacheHelper.getFromBooksInStock(bookKey);
		Assert.notNull(bookToRemove, "The BooksInStock record should exist");
		
		// execute action
		runAction_BooksInStock_RemoveAsItem(bookToRemove);
		
		// commit transaction
		commitJTATransaction();
		
		// validate cache state
		book = supplierOrderCacheHelper.getFromBooksInStock(bookKey);
		Assert.isNull(book, "The BooksInStock record should be removed");
		List<Book> bookListRemaining = supplierOrderCacheHelper.getAllBooksInStock();
		Assert.isEmpty(bookListRemaining, "No BooksInStock records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 28)
	public void runTest_BooksInStock_RemoveAsItem_utx_commit() throws Exception {
		String testName = "runTest_BooksInStock_RemoveAsItem_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		supplierOrderCacheHelper.assureRemoveAll();
		Book book = Bookshop2Fixture.create_Book();
		BookKey bookKey = Bookshop2Helper.createBookKey(book);
		supplierOrderCacheHelper.addToBooksInStock(bookKey, book);
		
		// begin transaction
		beginUserTransaction();

		// prepare test data
		Book bookToRemove = supplierOrderCacheHelper.getFromBooksInStock(bookKey);
		Assert.notNull(bookToRemove, "The BooksInStock record should exist");
		
		// execute action
		runAction_BooksInStock_RemoveAsItem(bookToRemove);
		
		// commit transaction
		commitUserTransaction();
		
		// validate cache state
		book = supplierOrderCacheHelper.getFromBooksInStock(bookKey);
		Assert.isNull(book, "The BooksInStock record should be removed");
		List<Book> bookListRemaining = supplierOrderCacheHelper.getAllBooksInStock();
		Assert.isEmpty(bookListRemaining, "No BooksInStock records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 29)
	@BytemanRule(name = "rule29", 
			targetClass = "SupplierOrderCacheManager", 
			targetMethod = "removeFromBooksInStock", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error29\")")
	public void runTest_BooksInStock_RemoveAsItem_manager_exception_rollback() throws Exception {
		String testName = "runTest_BooksInStock_RemoveAsItem_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		supplierOrderCacheHelper.assureRemoveAll();
		Book book = Bookshop2Fixture.create_Book();
		BookKey bookKey = Bookshop2Helper.createBookKey(book);
		supplierOrderCacheHelper.addToBooksInStock(bookKey, book);
		
		// setup expectations
		isExceptionExpected = true;
		
		// prepare test data
		Book bookToRemove = supplierOrderCacheHelper.getFromBooksInStock(bookKey);
		Assert.notNull(bookToRemove, "The BooksInStock record should exist");
		
		try {
			// execute action
			runAction_BooksInStock_RemoveAsItem(bookToRemove);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error29");
			
			// validate cache state
			book = supplierOrderCacheHelper.getFromBooksInStock(bookKey);
			Assert.notNull(book, "The BooksInStock record should still exist");
			List<Book> bookList = supplierOrderCacheHelper.getAllBooksInStock();
			Assert.equals(1, bookList.size(), "Only 1 BooksInStock record should exist");
		
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
			targetClass = "SupplierOrderCacheImpl", 
			targetMethod = "removeFromBooksInStock", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error30\")")
	public void runTest_BooksInStock_RemoveAsItem_cache_exception_rollback() throws Exception {
		String testName = "runTest_BooksInStock_RemoveAsItem_cache_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		supplierOrderCacheHelper.assureRemoveAll();
		Book book = Bookshop2Fixture.create_Book();
		BookKey bookKey = Bookshop2Helper.createBookKey(book);
		supplierOrderCacheHelper.addToBooksInStock(bookKey, book);
		
		// setup expectations
		isExceptionExpected = true;
		
		// prepare test data
		Book bookToRemove = supplierOrderCacheHelper.getFromBooksInStock(bookKey);
		Assert.notNull(bookToRemove, "The BooksInStock record should exist");
		
		try {
			// execute action
			runAction_BooksInStock_RemoveAsItem(bookToRemove);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error30");
			
			// validate cache state
			book = supplierOrderCacheHelper.getFromBooksInStock(bookKey);
			Assert.notNull(book, "The BooksInStock record should still exist");
			List<Book> bookList = supplierOrderCacheHelper.getAllBooksInStock();
			Assert.equals(1, bookList.size(), "Only 1 BooksInStock record should exist");
		
		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runAction_BooksInStock_RemoveAsItem(final Book book) throws Exception {
		runAction(new Runnable() {
			public void run() {
				supplierOrderCacheManager.removeFromBooksInStock(book);
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 31)
	public void runTest_BooksInStock_RemoveAsList_success() throws Exception {
		String testName = "runTest_BooksInStock_RemoveAsList_success";
		log.info(testName+": started");
		
		// prepare test context
		int bookCount = 4;
		supplierOrderCacheHelper.assureRemoveAll();
		List<Book> bookList = Bookshop2Fixture.createList_Book(bookCount);
		supplierOrderCacheHelper.addToBooksInStock(bookList);
		
		// prepare test data
		List<Book> bookListToRemove = supplierOrderCacheHelper.getAllBooksInStock();
		Assert.equals(bookCount, bookListToRemove.size(), "The BooksInStock records should exist");
		
		// execute action
		runAction_BooksInStock_RemoveAsList(bookListToRemove);
		
		// validate cache state
		List<Book> bookListRemaining = supplierOrderCacheHelper.getAllBooksInStock();
		Assert.isEmpty(bookListRemaining, "No BooksInStock records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 32)
	public void runTest_BooksInStock_RemoveAsList_tx_commit() throws Exception {
		String testName = "runTest_BooksInStock_RemoveAsList_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		int bookCount = 4;
		supplierOrderCacheHelper.assureRemoveAll();
		List<Book> bookList = Bookshop2Fixture.createList_Book(bookCount);
		supplierOrderCacheHelper.addToBooksInStock(bookList);
		
		// begin transaction
		beginJTATransaction();

		// prepare test data
		List<Book> bookListToRemove = supplierOrderCacheHelper.getAllBooksInStock();
		Assert.equals(bookCount, bookListToRemove.size(), "The BooksInStock records should exist");
		
		// execute action
		runAction_BooksInStock_RemoveAsList(bookListToRemove);
		
		// commit transaction
		commitJTATransaction();
		
		// validate cache state
		List<Book> bookListRemaining = supplierOrderCacheHelper.getAllBooksInStock();
		Assert.isEmpty(bookListRemaining, "No BooksInStock records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 33)
	public void runTest_BooksInStock_RemoveAsList_utx_commit() throws Exception {
		String testName = "runTest_BooksInStock_RemoveAsList_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		int bookCount = 4;
		supplierOrderCacheHelper.assureRemoveAll();
		List<Book> bookList = Bookshop2Fixture.createList_Book(bookCount);
		supplierOrderCacheHelper.addToBooksInStock(bookList);
		
		// begin transaction
		beginUserTransaction();

		// prepare test data
		List<Book> bookListToRemove = supplierOrderCacheHelper.getAllBooksInStock();
		Assert.equals(bookCount, bookListToRemove.size(), "The BooksInStock records should exist");
		
		// execute action
		runAction_BooksInStock_RemoveAsList(bookListToRemove);
		
		// commit transaction
		commitUserTransaction();
		
		// validate cache state
		List<Book> bookListRemaining = supplierOrderCacheHelper.getAllBooksInStock();
		Assert.isEmpty(bookListRemaining, "No BooksInStock records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 34)
	@BytemanRule(name = "rule34", 
			targetClass = "SupplierOrderCacheManager", 
			targetMethod = "removeFromBooksInStock", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error34\")")
	public void runTest_BooksInStock_RemoveAsList_manager_exception_rollback() throws Exception {
		String testName = "runTest_BooksInStock_RemoveAsList_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		int bookCount = 4;
		supplierOrderCacheHelper.assureRemoveAll();
		List<Book> bookList = Bookshop2Fixture.createList_Book(bookCount);
		supplierOrderCacheHelper.addToBooksInStock(bookList);
		
		// setup expectations
		isExceptionExpected = true;
		
		// prepare test data
		List<Book> bookListToRemove = supplierOrderCacheHelper.getAllBooksInStock();
		Assert.equals(bookCount, bookListToRemove.size(), "The BooksInStock records should exist");
		
		try {
			// execute action
			runAction_BooksInStock_RemoveAsList(bookListToRemove);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error34");
			
			// validate cache state
			List<Book> bookListRemaining = supplierOrderCacheHelper.getAllBooksInStock();
			Assert.equals(bookCount, bookListRemaining.size(), "The BooksInStock records should still exist");
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
			targetClass = "SupplierOrderCacheImpl", 
			targetMethod = "removeFromBooksInStock", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error35\")")
	public void runTest_BooksInStock_RemoveAsList_cache_exception_rollback() throws Exception {
		String testName = "runTest_BooksInStock_RemoveAsList_cache_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		int bookCount = 4;
		supplierOrderCacheHelper.assureRemoveAll();
		List<Book> bookList = Bookshop2Fixture.createList_Book(bookCount);
		supplierOrderCacheHelper.addToBooksInStock(bookList);
		
		// setup expectations
		isExceptionExpected = true;
		
		// prepare test data
		List<Book> bookListToRemove = supplierOrderCacheHelper.getAllBooksInStock();
		Assert.equals(bookCount, bookListToRemove.size(), "The BooksInStock records should exist");
		
		try {
		// execute action
			runAction_BooksInStock_RemoveAsList(bookListToRemove);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error35");
		
		// validate cache state
		List<Book> bookListRemaining = supplierOrderCacheHelper.getAllBooksInStock();
		Assert.equals(bookCount, bookListRemaining.size(), "The BooksInStock records should still exist");
			Bookshop2Fixture.assertSameBook(bookListRemaining, bookListToRemove);
		
		} finally {
		// cleanup test context
		tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runAction_BooksInStock_RemoveAsList(final Collection<Book> bookList) throws Exception {
		runAction(new Runnable() {
			public void run() {
				supplierOrderCacheManager.removeFromBooksInStock(bookList);
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
