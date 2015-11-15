package bookshop2.supplier.data.supplierOrderCache;

import java.util.Collection;
import java.util.List;

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
import bookshop2.supplier.SupplierTestEARBuilder;
import bookshop2.supplier.SupplierTestWARBuilder;
import bookshop2.util.Bookshop2Fixture;

import common.tx.state.AbstractStateManager;


@RunWith(Arquillian.class)
public class SupplierOrderCacheManagerCITGood extends AbstractCacheUnitArquillionTest {

//	@Rule 
//	public BytemanRule byteman = BytemanRule.create(SupplierOrderCacheManagerCIT.class);

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
		return SupplierOrderCacheManagerCITGood.class;
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
		transactionTestControl.setupTransactionManager();
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
		builder.addClass(SupplierOrderCacheManagerCITGood.class);
		WebArchive war = builder.create();
		return war;
	}
	
	@Test
	//@Ignore
	@InSequence(value = 1)
	public void runTest_BooksInStock_GetAllAsList_success() throws Exception {
		//nothing for now
	}
	
	@Test
	//@Ignore
	@InSequence(value = 2)
	public void runTest_BooksInStock_GetAllAsList_UserTransaction_commit() throws Exception {
		//nothing for now
	}
	
	@Test
	//@Ignore
	@InSequence(value = 3)
	@BytemanRule(name = "rule3", 
			targetClass = "SupplierOrderCacheManager", 
			targetMethod = "getAllBooksInStock", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error3\")")
	public void runTest_BooksInStock_GetAllAsList_manager_exception_rollback() throws Exception {
		//nothing for now
	}
	
	@Test
	//@Ignore
	@InSequence(value = 4)
	@BytemanRule(name = "rule4", 
			targetClass = "SupplierOrderCacheImpl", 
			targetMethod = "getAllBooksInStock", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error4\")")
	public void runTest_BooksInStock_GetAllAsList_cache_exception_rollback() throws Exception {
		//nothing for now
	}
	
	public void runTest_BooksInStock_GetAllAsList() throws Exception {
		runTest(new Runnable() {
			public void run() {
				supplierOrderCacheManager.getAllBooksInStock();
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 5)
	public void runTest_BooksInStock_GetMatchingAsMap_success() throws Exception {
		//nothing for now
	}
	
	@Test
	//@Ignore
	@InSequence(value = 6)
	public void runTest_BooksInStock_GetMatchingAsMap_UserTransaction_commit() throws Exception {
		//nothing for now
	}
	
	@Test
	//@Ignore
	@InSequence(value = 7)
	@BytemanRule(name = "rule7", 
			targetClass = "SupplierOrderCacheManager", 
			targetMethod = "getMatchingBooksInStockAsMap", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error7\")")
	public void runTest_BooksInStock_GetMatchingAsMap_manager_exception_rollback() throws Exception {
		//nothing for now
	}
	
	@Test
	//@Ignore
	@InSequence(value = 8)
	@BytemanRule(name = "rule8", 
			targetClass = "SupplierOrderCacheImpl", 
			targetMethod = "getMatchingBooksInStockAsMap", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error8\")")
	public void runTest_BooksInStock_GetMatchingAsMap_cache_exception_rollback() throws Exception {
		//nothing for now
	}
	
	public void runTest_BooksInStock_GetMatchingAsMap(final Collection<Book> bookList) throws Exception {
		runTest(new Runnable() {
			public void run() {
				supplierOrderCacheManager.getMatchingBooksInStockAsMap(bookList);
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 9)
	public void runTest_BooksInStock_AddAsList_success() throws Exception {
		String testName = "runTest_BooksInStock_AddAsList_success";
		log.info(testName+": started");
		
		// prepare test context
		supplierOrderCacheHelper.assureRemoveAll();
		
		// execute action
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book();
		runTest_BooksInStock_AddAsList(bookListToAdd);
		
		// validate cache state
		List<Book> bookList = supplierOrderCacheHelper.getAllBooksInStock();
		Bookshop2Fixture.assertSameBook(bookListToAdd, bookList);
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 10)
	public void runTest_BooksInStock_AddAsList_UserTransaction_commit() throws Exception {
		String testName = "runTest_BooksInStock_AddAsList_UserTransaction_commit";
		log.info(testName+": started");
		
		// prepare test context
		supplierOrderCacheHelper.assureRemoveAll();
		
		// setup expectations
		isUserTransaction = true;
		
		// execute action
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book();
		runTest_BooksInStock_AddAsList(bookListToAdd);
		
		// validate cache state
		List<Book> bookList = supplierOrderCacheHelper.getAllBooksInStock();
		Bookshop2Fixture.assertSameBook(bookListToAdd, bookList);
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 11)
	@BytemanRule(name = "rule11", 
			targetClass = "SupplierOrderCacheManager",
			targetMethod = "addToBooksInStock",
			targetLocation = "AT EXIT",
			action = "throw new org.aries.ApplicationFailure(\"error11\")")
	public void runTest_BooksInStock_AddAsList_manager_exception_rollback() throws Exception {
		String name = "runTest_BooksInStock_AddAsList_manager_exception_rollback";
		log.info(name+": started");
		
		// prepare test context
		setupByteman(name);
		supplierOrderCacheHelper.assureRemoveAll();
		
		// setup expectations
		isRollbackExpected = true;
		
		// execute action
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book();
		runTest_BooksInStock_AddAsList(bookListToAdd);
		
		// validate cache state
		List<Book> bookList = supplierOrderCacheHelper.getAllBooksInStock();
		Assert.isEmpty(bookList, "No BooksInStock records should exist");
		
		log.info(name+": done");
		if (errorMessage != null)
			fail(errorMessage);
		tearDownByteman(name);
		log.info(name+": done");
	}

	@Test
	//@Ignore
	@InSequence(value = 12)
	@BytemanRule(name = "rule12", 
			targetClass = "SupplierOrderCacheImpl",
			targetMethod = "addToBooksInStock",
			targetLocation = "AT EXIT",
			action = "throw new org.aries.ApplicationFailure(\"error12\")")
	//@Transactional(TransactionMode.ROLLBACK)
	public void runTest_BooksInStock_AddAsList_cache_exception_rollback() throws Exception {
		String testName = "runTest_BooksInStock_AddAsList_cache_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		supplierOrderCacheHelper.assureRemoveAll();
		
		// setup expectations
		isRollbackExpected = true;
		
		// execute action
		List<Book> books = Bookshop2Fixture.createList_Book();
		runTest_BooksInStock_AddAsList(books);
		
		// validate cache state
		//supplierOrderCacheHelper.verifyBooksInStockCount(0);
		List<Book> bookList = supplierOrderCacheHelper.getAllBooksInStock();
		Assert.isEmpty(bookList, "No BooksInStock records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runTest_BooksInStock_AddAsList(final Collection<Book> bookList) throws Exception {
		runTest(new Runnable() {
			public void run() {
				supplierOrderCacheManager.addToBooksInStock(bookList);
			}
		});
	}
	
	public void runTest(Runnable runnable) throws Exception {
		if (isTransactional()) {
			beginTransaction();
		}
		
		try {
			runnable.run();
			if (isRollbackExpected) {
				clearTransaction();
				errorMessage = "Exception should have been thrown";
			}
			if (isTransactional()) {
				commitTransaction();
			}

		} catch (Exception e) {
			if (isTransactional()) {
				rollbackTransaction();
			}
		}
	}

}
