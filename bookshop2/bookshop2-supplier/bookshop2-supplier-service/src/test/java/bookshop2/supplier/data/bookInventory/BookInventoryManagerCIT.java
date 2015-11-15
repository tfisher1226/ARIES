package bookshop2.supplier.data.bookInventory;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.aries.Assert;
import org.aries.tx.AbstractArquillianTest;
import org.aries.tx.AbstractDataUnitArquillionTest;
import org.aries.tx.BytemanRule;
import org.aries.tx.TransactionTestControl;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import bookshop2.Book;
import bookshop2.supplier.SupplierTestEARBuilder;
import bookshop2.supplier.SupplierTestJARBuilder;
import bookshop2.supplier.SupplierTestWARBuilder;
import bookshop2.util.Bookshop2Fixture;

import common.tx.state.AbstractStateManager;


@RunWith(Arquillian.class)
public class BookInventoryManagerCIT extends AbstractDataUnitArquillionTest {

	@Inject
	private BookInventoryManager bookInventoryManager;
	
	@Inject
	private BookInventoryHelper bookInventoryHelper;
	

	public BookInventoryManagerCIT() {
		//nothing for now
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
	public Class<?> getTestClass() {
		return BookInventoryManagerCIT.class;
	}
	
	@Override
	public String getTargetArchive() {
		return SupplierTestEARBuilder.NAME;
	}

	@Override
	public AbstractStateManager<?> getStateManager() {
		return bookInventoryManager;
	}
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		AbstractArquillianTest.beforeClass();
	}
	
	@AfterClass
	public static void afterClass() throws Exception {
		AbstractArquillianTest.afterClass();
	}
	
	@Before
	public void setUp() throws Exception {
		if (!isServerStarted()) {
			startServer();
		} else {
			super.setUp();
			createTransactionControl();
			initializeBookInventoryHelper();
			clearBookInventoryContext();
		}
	}
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
		clearTransaction();
		clearBookInventoryContext();
	}
	
	@TargetsContainer("hornetQ01_local")
	@Deployment(name = "supplierEAR", order = 1)
	public static EnterpriseArchive createSupplierEAR() {
		SupplierTestEARBuilder builder = new SupplierTestEARBuilder();
		builder.addAsModule(createSupplierWAR(), "supplier");
		//builder.addAsModule(createSupplierJAR());
		EnterpriseArchive ear = builder.createEAR();
		return ear;
	}
	
	public static JavaArchive createSupplierJAR() {
		SupplierTestJARBuilder builder = new SupplierTestJARBuilder("test.jar");
		builder.addClass(BookInventoryManagerCIT.class);
		builder.addClass(Tmp.class);
		JavaArchive jar = builder.create();
		return jar;
	}

	public static WebArchive createSupplierWAR() {
		SupplierTestWARBuilder builder = new SupplierTestWARBuilder("test.war");
		builder.addClass(BookInventoryManagerCIT.class);
		builder.addClass(Tmp.class);
		WebArchive war = builder.create();
		return war;
	}

	protected void createTransactionControl() throws Exception {
		transactionTestControl = new TransactionTestControl();
		transactionTestControl.assureTransactionManager();
	}
	
	protected void initializeBookInventoryHelper() throws Exception {
		bookInventoryHelper.setJmxManager(jmxManager);
		bookInventoryHelper.initialize(transactionTestControl);
	}
	
	protected void clearBookInventoryContext() throws Exception {
		bookInventoryHelper.clearContext(BookInventoryManager.MBEAN_NAME);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 1)
	public void runTest_ReservedBooks_GetAllAsList_success() throws Exception {
		String testName = "runTest_ReservedBooks_GetAllAsList_success";
		log.info(testName+": started");
		
		// prepare test context
		bookInventoryHelper.assureRemoveAll();
		
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 2)
	public void runTest_ReservedBooks_GetAllAsList_tx_commit() throws Exception {
		String testName = "runTest_ReservedBooks_GetAllAsList_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookInventoryHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();
		
		
		// commit transaction
		commitJTATransaction();
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 3)
	public void runTest_ReservedBooks_GetAllAsList_tx_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_GetAllAsList_tx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookInventoryHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();
		
		
		// rollback transaction
		rollbackJTATransaction();
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 4)
	public void runTest_ReservedBooks_GetAllAsList_utx_commit() throws Exception {
		String testName = "runTest_ReservedBooks_GetAllAsList_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookInventoryHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();
		
		
		// commit transaction
		commitUserTransaction();
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 5)
	public void runTest_ReservedBooks_GetAllAsList_utx_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_GetAllAsList_utx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookInventoryHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();
		
		
		// rollback transaction
		rollbackUserTransaction();
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 6)
	@BytemanRule(name = "rule6", 
			targetClass = "BookInventoryManager", 
			targetMethod = "getAllReservedBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error6\")")
	public void runTest_ReservedBooks_GetAllAsList_manager_exception_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_GetAllAsList_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookInventoryHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;
		
		try {

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
	@InSequence(value = 7)
	@BytemanRule(name = "rule7", 
			targetClass = "BookInventory", 
			targetMethod = "getAllReservedBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error7\")")
	public void runTest_ReservedBooks_GetAllAsList_interface_exception_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_GetAllAsList_interface_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookInventoryHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;
		
		try {

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
	@InSequence(value = 8)
	@BytemanRule(name = "rule8", 
			targetClass = "BookInventoryRepositoryImpl", 
			targetMethod = "getAllReservedBooksRecords", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error8\")")
	public void runTest_ReservedBooks_GetAllAsList_repository_exception_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_GetAllAsList_repository_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookInventoryHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;
		
		try {

		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runTest_ReservedBooks_GetAllAsList() throws Exception {
		runTest(new Runnable() {
			public void run() {
				bookInventoryManager.getAllReservedBooks();
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 9)
	public void runTest_ReservedBooks_GetMatchingAsMap_success() throws Exception {
		String testName = "runTest_ReservedBooks_GetMatchingAsMap_success";
		log.info(testName+": started");
		
		// prepare test context
		bookInventoryHelper.assureRemoveAll();
		
		// execute test action

		// validate persistent state
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 10)
	public void runTest_ReservedBooks_GetMatchingAsMap_tx_commit() throws Exception {
		String testName = "runTest_ReservedBooks_GetMatchingAsMap_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookInventoryHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();
		
		// execute test action
		
		// commit transaction
		commitJTATransaction();

		// validate persistent state
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 11)
	public void runTest_ReservedBooks_GetMatchingAsMap_tx_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_GetMatchingAsMap_tx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookInventoryHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();
		
		// execute test action
		
		// rollback transaction
		rollbackJTATransaction();

		// validate persistent state
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 12)
	public void runTest_ReservedBooks_GetMatchingAsMap_utx_commit() throws Exception {
		String testName = "runTest_ReservedBooks_GetMatchingAsMap_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookInventoryHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();
		
		// execute test action
		
		// commit transaction
		commitUserTransaction();

		// validate persistent state
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 13)
	public void runTest_ReservedBooks_GetMatchingAsMap_utx_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_GetMatchingAsMap_utx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookInventoryHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();
		
		// execute test action
		
		// rollback transaction
		rollbackUserTransaction();

		// validate persistent state
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 14)
	@BytemanRule(name = "rule14", 
			targetClass = "BookInventoryManager", 
			targetMethod = "getMatchingReservedBooksAsMap", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error14\")")
	public void runTest_ReservedBooks_GetMatchingAsMap_manager_exception_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_GetMatchingAsMap_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookInventoryHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;
		
		try {
			// execute test action

			// validate persistent state

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
			targetClass = "BookInventory", 
			targetMethod = "getMatchingReservedBooksAsMap", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error15\")")
	public void runTest_ReservedBooks_GetMatchingAsMap_interface_exception_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_GetMatchingAsMap_interface_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookInventoryHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;
		
		try {
			// execute test action

			// validate persistent state

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
	@InSequence(value = 16)
	@BytemanRule(name = "rule16", 
			targetClass = "BookInventoryRepositoryImpl", 
			targetMethod = "getMatchingReservedBooksAsMapRecords", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error16\")")
	public void runTest_ReservedBooks_GetMatchingAsMap_repository_exception_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_GetMatchingAsMap_repository_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookInventoryHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;
		
		try {
			// execute test action

			// validate persistent state

		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runTest_ReservedBooks_GetMatchingAsMap(final Collection<Book> bookList) throws Exception {
		runTest(new Runnable() {
			public void run() {
				bookInventoryManager.getMatchingReservedBooksAsMap(bookList);
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 17)
	public void runTest_ReservedBooks_AddAsItem_success() throws Exception {
		String testName = "runTest_ReservedBooks_AddAsItem_success";
		log.info(testName+": started");
		
		// prepare test context
		bookInventoryHelper.assureRemoveAll();
		
		// prepare test data
		Book bookToAdd = Bookshop2Fixture.create_Book();
		
		// execute test action
		runTest_ReservedBooks_AddAsItem(bookToAdd);

		// validate persistent state
		List<Book> bookList = bookInventoryHelper.getAllReservedBooks();
		Assert.equals(1, bookList.size(), "Only 1 ReservedBooks record should exist");
		Bookshop2Fixture.assertSameBook(bookToAdd, bookList.get(0));
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 18)
	public void runTest_ReservedBooks_AddAsItem_tx_commit() throws Exception {
		String testName = "runTest_ReservedBooks_AddAsItem_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookInventoryHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();
		
		// prepare test data
		Book bookToAdd = Bookshop2Fixture.create_Book();
		
		// execute test action
		runTest_ReservedBooks_AddAsItem(bookToAdd);
		
		// validate intermediate persistent state
		//List<Book> bookList = bookInventoryHelper.getAllReservedBooksIC();
		//Assert.isEmpty(bookList, "ReservedBooks should be empty");
		verifyReservedBooksRecordExistsIC(bookToAdd);
		
		// commit transaction
		commitJTATransaction();
		
		// validate persistent state
		//verifyReservedBooksRecordExists(bookToAdd);
		List<Book> bookList = bookInventoryHelper.getAllReservedBooks();
		Assert.equals(1, bookList.size(), "Only 1 ReservedBooks record should exist");
		Bookshop2Fixture.assertSameBook(bookToAdd, bookList.get(0));
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 19)
	public void runTest_ReservedBooks_AddAsItem_tx_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_AddAsItem_tx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookInventoryHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();
		
		// prepare test data
		Book bookToAdd = Bookshop2Fixture.create_Book();
		
		// execute test action
		runTest_ReservedBooks_AddAsItem(bookToAdd);

		// validate intermediate persistent state
		verifyReservedBooksRecordExistsIC(bookToAdd);

		// rollback transaction
		rollbackJTATransaction();
		
		// validate persistent state
		//verifyNoReservedBooksRecordsExist();
		List<Book> bookList = bookInventoryHelper.getAllReservedBooks();
		Assert.isEmpty(bookList, "No ReservedBooks records should exist");
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 20)
	public void runTest_ReservedBooks_AddAsItem_utx_commit() throws Exception {
		String testName = "runTest_ReservedBooks_AddAsItem_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookInventoryHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();
		
		// prepare test data
		Book bookToAdd = Bookshop2Fixture.create_Book();
		
		// execute test action
		runTest_ReservedBooks_AddAsItem(bookToAdd);
				
		// validate intermediate persistent state
		verifyReservedBooksRecordExistsIC(bookToAdd);

		// commit transaction
		commitUserTransaction();
		
		// validate persistent state
		//verifyReservedBooksRecordExists(bookToAdd);
		List<Book> bookList = bookInventoryHelper.getAllReservedBooks();
		Assert.equals(1, bookList.size(), "Only 1 ReservedBooks record should exist");
		Bookshop2Fixture.assertSameBook(bookToAdd, bookList.get(0));
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 21)
	public void runTest_ReservedBooks_AddAsItem_utx_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_AddAsItem_utx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookInventoryHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();
		
		// prepare test data
		Book bookToAdd = Bookshop2Fixture.create_Book();
		
		// execute test action
		runTest_ReservedBooks_AddAsItem(bookToAdd);

		// validate intermediate persistent state
		//TODO somehow access the backing store here to validate that it has been populated
		//List<Book> bookList = bookInventoryHelper.getAllReservedBooksIC();
		//Assert.isEmpty(bookList, "ReservedBooks should be empty");
		//verifyReservedBooksRecordExistsIC(bookToAdd);
		verifyReservedBooksRecordExistsIC(bookToAdd);
		//verifyNoReservedBooksRecordsExist();

		// rollback transaction
		rollbackUserTransaction();
		
		// validate persistent state
		//verifyNoReservedBooksRecordsExist();
		List<Book> bookList = bookInventoryHelper.getAllReservedBooks();
		Assert.isEmpty(bookList, "No ReservedBooks records should exist");
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 22)
	@BytemanRule(name = "rule22", 
			targetClass = "BookInventoryManager", 
			targetMethod = "addToReservedBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error22\")")
	public void runTest_ReservedBooks_AddAsItem_manager_exception_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_AddAsItem_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookInventoryHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;
		
		// prepare test data
		Book bookToAdd = Bookshop2Fixture.create_Book();
		
		try {
			// execute test action
			runTest_ReservedBooks_AddAsItem(bookToAdd);
			fail("Exception should have been thrown");
			
		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error22");
			
			// validate persistent state
			List<Book> bookList = bookInventoryHelper.getAllReservedBooks();
			Assert.isEmpty(bookList, "No ReservedBooks records should exist");
		
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
	@InSequence(value = 23)
	@BytemanRule(name = "rule23", 
			targetClass = "BookInventory", 
			targetMethod = "addToReservedBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error23\")")
	public void runTest_ReservedBooks_AddAsItem_interface_exception_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_AddAsItem_interface_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookInventoryHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;
		
		// prepare test data
		Book bookToAdd = Bookshop2Fixture.create_Book();
		
		try {
			// execute test action
			runTest_ReservedBooks_AddAsItem(bookToAdd);
			fail("Exception should have been thrown");
			
		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error23");
			
			// validate persistent state
			List<Book> bookList = bookInventoryHelper.getAllReservedBooks();
			Assert.isEmpty(bookList, "No ReservedBooks records should exist");

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
	@InSequence(value = 24)
	@BytemanRule(name = "rule24", 
			targetClass = "BookInventoryRepositoryImpl", 
			targetMethod = "addReservedBooksRecord", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error24\")")
	public void runTest_ReservedBooks_AddAsItem_repository_exception_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_AddAsItem_repository_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookInventoryHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;
		
		// prepare test data
		Book bookToAdd = Bookshop2Fixture.create_Book();
		
		try {
			// execute test action
			runTest_ReservedBooks_AddAsItem(bookToAdd);
			fail("Exception should have been thrown");
			
		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error24");

			// validate persistent state
			List<Book> bookList = bookInventoryHelper.getAllReservedBooks();
			Assert.isEmpty(bookList, "No ReservedBooks records should exist");
			
		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}

		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runTest_ReservedBooks_AddAsItem(final Book book) throws Exception {
		runTest(new Runnable() {
			public void run() {
				bookInventoryManager.addToReservedBooks(book);
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 25)
	public void runTest_ReservedBooks_AddAsList_success() throws Exception {
		String testName = "runTest_ReservedBooks_AddAsList_success";
		log.info(testName+": started");
		
		// prepare test context
		bookInventoryHelper.assureRemoveAll();
		
		// prepare test data
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book();
		
		// execute test action
		runTest_ReservedBooks_AddAsList(bookListToAdd);
		
		// validate persistent state
		List<Book> bookList = bookInventoryHelper.getAllReservedBooks();
		Bookshop2Fixture.assertSameBook(bookListToAdd, bookList);
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 26)
	public void runTest_ReservedBooks_AddAsList_tx_commit() throws Exception {
		String testName = "runTest_ReservedBooks_AddAsList_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookInventoryHelper.assureRemoveAll();

		// begin transaction
		beginJTATransaction();
		
		// prepare test data
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book();
		
		// execute test action
		runTest_ReservedBooks_AddAsList(bookListToAdd);
		
		verifyReservedBooksRecordsExistIC(bookListToAdd);
			
		// commit transaction
		commitJTATransaction();
		
		// validate persistent state
		//verifyReservedBooksRecordsExist(bookSetToAdd);
		List<Book> bookList = bookInventoryHelper.getAllReservedBooks();
		Bookshop2Fixture.assertSameBook(bookListToAdd, bookList);
		clearTransaction();

		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 27)
	public void runTest_ReservedBooks_AddAsList_tx_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_AddAsList_tx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookInventoryHelper.assureRemoveAll();

		// begin transaction
		beginJTATransaction();
		
		// prepare test data
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book();
		
		// execute test action
		runTest_ReservedBooks_AddAsList(bookListToAdd);
				
		// validate intermediate persistent state
		verifyReservedBooksRecordsExistIC(bookListToAdd);

		// rollback transaction
		rollbackJTATransaction();

		// validate persistent state
		List<Book> bookList = bookInventoryHelper.getAllReservedBooks();
		Assert.isEmpty(bookList, "No ReservedBooks records should exist");
		clearTransaction();

		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 28)
	public void runTest_ReservedBooks_AddAsList_utx_commit() throws Exception {
		String testName = "runTest_ReservedBooks_AddAsList_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookInventoryHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();
		
		// prepare test data
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book();
		
		// execute test action
		runTest_ReservedBooks_AddAsList(bookListToAdd);
		
		// commit transaction
		commitUserTransaction();

		// validate persistent state
		List<Book> bookList = bookInventoryHelper.getAllReservedBooks();
		Bookshop2Fixture.assertSameBook(bookListToAdd, bookList);
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 29)
	public void runTest_ReservedBooks_AddAsList_utx_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_AddAsList_utx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookInventoryHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();
		
		// prepare test data
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book();
		
		// execute test action
		runTest_ReservedBooks_AddAsList(bookListToAdd);
		
		// rollback transaction
		rollbackUserTransaction();

		// validate persistent state
		List<Book> bookList = bookInventoryHelper.getAllReservedBooks();
		Assert.isEmpty(bookList, "No ReservedBooks records should exist");
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 30)
	@BytemanRule(name = "rule30", 
			targetClass = "BookInventoryManager", 
			targetMethod = "addToReservedBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error30\")")
	public void runTest_ReservedBooks_AddAsList_manager_exception_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_AddAsList_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookInventoryHelper.assureRemoveAll();

		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book();
		
		try {
			// execute test action
			runTest_ReservedBooks_AddAsList(bookListToAdd);
			fail("Exception should have been thrown");
		
		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error30");

			// validate persistent state
			List<Book> bookList = bookInventoryHelper.getAllReservedBooks();
			Assert.isEmpty(bookList, "No ReservedBooks records should exist");
			
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
	@InSequence(value = 31)
	@BytemanRule(name = "rule31", 
			targetClass = "BookInventory", 
			targetMethod = "addToReservedBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error31\")")
	public void runTest_ReservedBooks_AddAsList_interface_exception_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_AddAsList_interface_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookInventoryHelper.assureRemoveAll();

		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book();
		
		try {
			// execute test action
			runTest_ReservedBooks_AddAsList(bookListToAdd);
			fail("Exception should have been thrown");
			
		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error31");

			// validate persistent state
			List<Book> bookList = bookInventoryHelper.getAllReservedBooks();
			Assert.isEmpty(bookList, "No ReservedBooks records should exist");
			
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
	@InSequence(value = 32)
	@BytemanRule(name = "rule32", 
			targetClass = "BookInventoryRepositoryImpl", 
			targetMethod = "addReservedBooksRecords", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error32\")")
	public void runTest_ReservedBooks_AddAsList_repository_exception_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_AddAsList_repository_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookInventoryHelper.assureRemoveAll();

		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		List<Book> bookListToAdd = Bookshop2Fixture.createList_Book();
		
		try {
			// execute test action
			runTest_ReservedBooks_AddAsList(bookListToAdd);
			fail("Exception should have been thrown");
			
		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error32");

			// validate persistent state
			List<Book> bookList = bookInventoryHelper.getAllReservedBooks();
			Assert.isEmpty(bookList, "No ReservedBooks records should exist");
			
		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}

		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runTest_ReservedBooks_AddAsList(final Collection<Book> bookList) throws Exception {
		runTest(new Runnable() {
			public void run() {
				bookInventoryManager.addToReservedBooks(bookList);
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 33)
	public void runTest_ReservedBooks_RemoveAll_success() throws Exception {
		String testName = "runTest_ReservedBooks_RemoveAll_success";
		log.info(testName+": started");
		
		// prepare test context
		bookInventoryHelper.assureRemoveAll();
		
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 34)
	public void runTest_ReservedBooks_RemoveAll_tx_commit() throws Exception {
		String testName = "runTest_ReservedBooks_RemoveAll_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookInventoryHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();
		
		
		// commit transaction
		commitJTATransaction();
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 35)
	public void runTest_ReservedBooks_RemoveAll_tx_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_RemoveAll_tx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookInventoryHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();
		
		
		// rollback transaction
		rollbackJTATransaction();
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 36)
	public void runTest_ReservedBooks_RemoveAll_utx_commit() throws Exception {
		String testName = "runTest_ReservedBooks_RemoveAll_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookInventoryHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();
		
		
		// commit transaction
		commitUserTransaction();
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 37)
	public void runTest_ReservedBooks_RemoveAll_utx_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_RemoveAll_utx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookInventoryHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();
		
		
		// rollback transaction
		rollbackUserTransaction();
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 38)
	@BytemanRule(name = "rule38", 
			targetClass = "BookInventoryManager", 
			targetMethod = "removeAllReservedBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error38\")")
	public void runTest_ReservedBooks_RemoveAll_manager_exception_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_RemoveAll_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookInventoryHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;
		
		try {

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
	@InSequence(value = 39)
	@BytemanRule(name = "rule39", 
			targetClass = "BookInventory", 
			targetMethod = "removeAllReservedBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error39\")")
	public void runTest_ReservedBooks_RemoveAll_interface_exception_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_RemoveAll_interface_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookInventoryHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;
		
		try {

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
	@InSequence(value = 40)
	@BytemanRule(name = "rule40", 
			targetClass = "BookInventoryRepositoryImpl", 
			targetMethod = "removeAllReservedBooksRecords", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error40\")")
	public void runTest_ReservedBooks_RemoveAll_repository_exception_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_RemoveAll_repository_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookInventoryHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;
		
		try {

		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runTest_ReservedBooks_RemoveAll() throws Exception {
		runTest(new Runnable() {
			public void run() {
				bookInventoryManager.removeAllReservedBooks();
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 41)
	public void runTest_ReservedBooks_RemoveAsItem_success() throws Exception {
		String testName = "runTest_ReservedBooks_RemoveAsItem_success";
		log.info(testName+": started");
		
		// prepare test context
		bookInventoryHelper.assureRemoveAll();
		Long id = bookInventoryHelper.addReservedBooks();
		
		// prepare test data
		Book bookToRemove = bookInventoryHelper.getReservedBooksById(id);
		Assert.notNull(bookToRemove, "The ReservedBooks record should exist");
		
		// execute test action
		runTest_ReservedBooks_RemoveAsItem(bookToRemove);

		// validate persistent state
		Book bookRemoved = bookInventoryHelper.getReservedBooksById(id);
		Assert.isNull(bookRemoved, "The ReservedBooks record should be removed");
		List<Book> bookListRemaining = bookInventoryHelper.getAllReservedBooks();
		Assert.isEmpty(bookListRemaining, "No ReservedBooks records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 42)
	public void runTest_ReservedBooks_RemoveAsItem_tx_commit() throws Exception {
		String testName = "runTest_ReservedBooks_RemoveAsItem_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookInventoryHelper.assureRemoveAll();
		Long id = bookInventoryHelper.addReservedBooks();
		
		// begin transaction
		beginJTATransaction();
		
		// prepare test data
		Book bookToRemove = bookInventoryHelper.getReservedBooksById(id);
		Assert.notNull(bookToRemove, "The ReservedBooks record should exist");
		
		// execute test action
		runTest_ReservedBooks_RemoveAsItem(bookToRemove);
		
		// commit transaction
		commitJTATransaction();

		// validate persistent state
		Book bookRemoved = bookInventoryHelper.getReservedBooksById(id);
		Assert.isNull(bookRemoved, "The ReservedBooks record should be removed");
		List<Book> bookListRemaining = bookInventoryHelper.getAllReservedBooks();
		Assert.isEmpty(bookListRemaining, "No ReservedBooks records should exist");
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 43)
	public void runTest_ReservedBooks_RemoveAsItem_tx_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_RemoveAsItem_tx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookInventoryHelper.assureRemoveAll();
		Long id = bookInventoryHelper.addReservedBooks();
		
		// begin transaction
		beginJTATransaction();
		
		// prepare test data
		Book bookToRemove = bookInventoryHelper.getReservedBooksById(id);
		Assert.notNull(bookToRemove, "The ReservedBooks record should exist");
		
		// execute test action
		runTest_ReservedBooks_RemoveAsItem(bookToRemove);
		
		// rollback transaction
		rollbackJTATransaction();

		// validate persistent state
		Book bookNotRemoved = bookInventoryHelper.getReservedBooksById(id);
		Assert.notNull(bookNotRemoved, "The ReservedBooks record should still exist");
		List<Book> bookListRemaining = bookInventoryHelper.getAllReservedBooks();
		Assert.equals(1, bookListRemaining.size(), "Only 1 ReservedBooks record should exist");
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 44)
	public void runTest_ReservedBooks_RemoveAsItem_utx_commit() throws Exception {
		String testName = "runTest_ReservedBooks_RemoveAsItem_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookInventoryHelper.assureRemoveAll();
		Long id = bookInventoryHelper.addReservedBooks();
		
		// begin transaction
		beginUserTransaction();
		
		// prepare test data
		Book bookToRemove = bookInventoryHelper.getReservedBooksById(id);
		Assert.notNull(bookToRemove, "The ReservedBooks record should exist");
		
		// execute test action
		runTest_ReservedBooks_RemoveAsItem(bookToRemove);
		
		// commit transaction
		commitUserTransaction();

		// validate persistent state
		Book bookRemoved = bookInventoryHelper.getReservedBooksById(id);
		Assert.isNull(bookRemoved, "The ReservedBooks record should be removed");
		List<Book> bookListRemaining = bookInventoryHelper.getAllReservedBooks();
		Assert.isEmpty(bookListRemaining, "No ReservedBooks records should exist");
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 45)
	public void runTest_ReservedBooks_RemoveAsItem_utx_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_RemoveAsItem_utx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookInventoryHelper.assureRemoveAll();
		Long id = bookInventoryHelper.addReservedBooks();
		
		// begin transaction
		beginUserTransaction();
		
		// prepare test data
		Book bookToRemove = bookInventoryHelper.getReservedBooksById(id);
		Assert.notNull(bookToRemove, "The ReservedBooks record should exist");
		
		// execute test action
		runTest_ReservedBooks_RemoveAsItem(bookToRemove);
		
		// rollback transaction
		rollbackUserTransaction();

		// validate persistent state
		Book bookNotRemoved = bookInventoryHelper.getReservedBooksById(id);
		Assert.notNull(bookNotRemoved, "The ReservedBooks record should still exist");
		List<Book> bookListRemaining = bookInventoryHelper.getAllReservedBooks();
		Assert.equals(1, bookListRemaining.size(), "Only 1 ReservedBooks record should exist");
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 46)
	@BytemanRule(name = "rule46", 
			targetClass = "BookInventoryManager", 
			targetMethod = "removeFromReservedBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error46\")")
	public void runTest_ReservedBooks_RemoveAsItem_manager_exception_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_RemoveAsItem_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookInventoryHelper.assureRemoveAll();
		Long id = bookInventoryHelper.addReservedBooks();
		
		// setup expectations
		isExceptionExpected = true;
		
		// prepare test data
		Book bookToRemove = bookInventoryHelper.getReservedBooksById(id);
		Assert.notNull(bookToRemove, "The ReservedBooks record should exist");
		
		try {
			// execute test action
			runTest_ReservedBooks_RemoveAsItem(bookToRemove);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error46");

			// validate persistent state
			Book bookNotRemoved = bookInventoryHelper.getReservedBooksById(id);
			Assert.notNull(bookNotRemoved, "The ReservedBooks record should still exist");
			List<Book> bookList = bookInventoryHelper.getAllReservedBooks();
			Assert.equals(1, bookList.size(), "Only 1 ReservedBooks record should exist");

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
	@InSequence(value = 47)
	@BytemanRule(name = "rule47", 
			targetClass = "BookInventory", 
			targetMethod = "removeFromReservedBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error47\")")
	public void runTest_ReservedBooks_RemoveAsItem_interface_exception_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_RemoveAsItem_interface_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookInventoryHelper.assureRemoveAll();
		Long id = bookInventoryHelper.addReservedBooks();
		
		// setup expectations
		isExceptionExpected = true;
		
		// prepare test data
		Book bookToRemove = bookInventoryHelper.getReservedBooksById(id);
		Assert.notNull(bookToRemove, "The ReservedBooks record should exist");
		
		try {
			// execute test action
			runTest_ReservedBooks_RemoveAsItem(bookToRemove);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error47");

			// validate persistent state
			Book bookNotRemoved = bookInventoryHelper.getReservedBooksById(id);
			Assert.notNull(bookNotRemoved, "The ReservedBooks record should still exist");
			List<Book> bookList = bookInventoryHelper.getAllReservedBooks();
			Assert.equals(1, bookList.size(), "Only 1 ReservedBooks record should exist");

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
	@InSequence(value = 48)
	@BytemanRule(name = "rule48", 
			targetClass = "BookInventoryRepositoryImpl", 
			targetMethod = "removeReservedBooksRecord", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error48\")")
	public void runTest_ReservedBooks_RemoveAsItem_repository_exception_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_RemoveAsItem_repository_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookInventoryHelper.assureRemoveAll();
		Long id = bookInventoryHelper.addReservedBooks();
		
		// setup expectations
		isExceptionExpected = true;
		
		// prepare test data
		Book bookToRemove = bookInventoryHelper.getReservedBooksById(id);
		Assert.notNull(bookToRemove, "The ReservedBooks record should exist");
		
		try {
			// execute test action
			runTest_ReservedBooks_RemoveAsItem(bookToRemove);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error48");

			// validate persistent state
			Book bookNotRemoved = bookInventoryHelper.getReservedBooksById(id);
			Assert.notNull(bookNotRemoved, "The ReservedBooks record should still exist");
			List<Book> bookList = bookInventoryHelper.getAllReservedBooks();
			Assert.equals(1, bookList.size(), "Only 1 ReservedBooks record should exist");

		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runTest_ReservedBooks_RemoveAsItem(final Book book) throws Exception {
		runTest(new Runnable() {
			public void run() {
				bookInventoryManager.removeFromReservedBooks(book);
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 49)
	public void runTest_ReservedBooks_RemoveAsList_success() throws Exception {
		String testName = "runTest_ReservedBooks_RemoveAsList_success";
		log.info(testName+": started");
		
		// prepare test context
		int reservedBooksCount = 2;
		bookInventoryHelper.assureRemoveAll();
		bookInventoryHelper.addReservedBooks(reservedBooksCount);
		
		// prepare test data
		List<Book> bookListToRemove = bookInventoryHelper.getAllReservedBooks();
		Assert.equals(reservedBooksCount, bookListToRemove.size(), "The ReservedBooks records should exist");
		
		// execute test action
		runTest_ReservedBooks_RemoveAsList(bookListToRemove);

		// validate persistent state
		List<Book> bookListRemaining = bookInventoryHelper.getAllReservedBooks();
		Assert.isEmpty(bookListRemaining, "No ReservedBooks records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 50)
	public void runTest_ReservedBooks_RemoveAsList_tx_commit() throws Exception {
		String testName = "runTest_ReservedBooks_RemoveAsList_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		int reservedBooksCount = 2;
		bookInventoryHelper.assureRemoveAll();
		bookInventoryHelper.addReservedBooks(reservedBooksCount);
		
		// begin transaction
		beginJTATransaction();
		
		// prepare test data
		List<Book> bookListToRemove = bookInventoryHelper.getAllReservedBooks();
		Assert.equals(reservedBooksCount, bookListToRemove.size(), "The ReservedBooks records should exist");
		
		// execute test action
		runTest_ReservedBooks_RemoveAsList(bookListToRemove);
		
		// commit transaction
		commitJTATransaction();

		// validate persistent state
		List<Book> bookListRemaining = bookInventoryHelper.getAllReservedBooks();
		Assert.isEmpty(bookListRemaining, "No ReservedBooks records should exist");
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 51)
	public void runTest_ReservedBooks_RemoveAsList_tx_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_RemoveAsList_tx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		int reservedBooksCount = 2;
		bookInventoryHelper.assureRemoveAll();
		bookInventoryHelper.addReservedBooks(reservedBooksCount);
		
		// begin transaction
		beginJTATransaction();
		
		// prepare test data
		List<Book> bookListToRemove = bookInventoryHelper.getAllReservedBooks();
		Assert.equals(reservedBooksCount, bookListToRemove.size(), "The ReservedBooks records should exist");
		
		// execute test action
		runTest_ReservedBooks_RemoveAsList(bookListToRemove);
		
		// rollback transaction
		rollbackJTATransaction();

		// validate persistent state
		List<Book> bookListRemaining = bookInventoryHelper.getAllReservedBooks();
		Assert.equals(reservedBooksCount, bookListRemaining.size(), "The ReservedBooks records should still exist");
		Bookshop2Fixture.assertSameBook(bookListRemaining, bookListToRemove);
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 52)
	public void runTest_ReservedBooks_RemoveAsList_utx_commit() throws Exception {
		String testName = "runTest_ReservedBooks_RemoveAsList_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		int reservedBooksCount = 2;
		bookInventoryHelper.assureRemoveAll();
		bookInventoryHelper.addReservedBooks(reservedBooksCount);
		
		// begin transaction
		beginUserTransaction();
		
		// prepare test data
		List<Book> bookListToRemove = bookInventoryHelper.getAllReservedBooks();
		Assert.equals(reservedBooksCount, bookListToRemove.size(), "The ReservedBooks records should exist");
		
		// execute test action
		runTest_ReservedBooks_RemoveAsList(bookListToRemove);
		
		// commit transaction
		commitUserTransaction();

		// validate persistent state
		List<Book> bookListRemaining = bookInventoryHelper.getAllReservedBooks();
		Assert.isEmpty(bookListRemaining, "No ReservedBooks records should exist");
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 53)
	public void runTest_ReservedBooks_RemoveAsList_utx_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_RemoveAsList_utx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		int reservedBooksCount = 2;
		bookInventoryHelper.assureRemoveAll();
		bookInventoryHelper.addReservedBooks(reservedBooksCount);
		
		// begin transaction
		beginUserTransaction();
		
		// prepare test data
		List<Book> bookListToRemove = bookInventoryHelper.getAllReservedBooks();
		Assert.equals(reservedBooksCount, bookListToRemove.size(), "The ReservedBooks records should exist");
		
		// execute test action
		runTest_ReservedBooks_RemoveAsList(bookListToRemove);
		
		// rollback transaction
		rollbackUserTransaction();

		// validate persistent state
		List<Book> bookListRemaining = bookInventoryHelper.getAllReservedBooks();
		Assert.equals(reservedBooksCount, bookListRemaining.size(), "The ReservedBooks records should still exist");
		Bookshop2Fixture.assertSameBook(bookListRemaining, bookListToRemove);
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 54)
	@BytemanRule(name = "rule54", 
			targetClass = "BookInventoryManager", 
			targetMethod = "removeFromReservedBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error54\")")
	public void runTest_ReservedBooks_RemoveAsList_manager_exception_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_RemoveAsList_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		int reservedBooksCount = 2;
		bookInventoryHelper.assureRemoveAll();
		bookInventoryHelper.addReservedBooks(reservedBooksCount);
		
		// setup expectations
		isExceptionExpected = true;
		
		// prepare test data
		List<Book> bookListToRemove = bookInventoryHelper.getAllReservedBooks();
		Assert.equals(reservedBooksCount, bookListToRemove.size(), "The ReservedBooks records should exist");
				
		try {
			// execute test action
			runTest_ReservedBooks_RemoveAsList(bookListToRemove);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error54");

			// validate persistent state
			List<Book> bookListRemaining = bookInventoryHelper.getAllReservedBooks();
			Assert.equals(reservedBooksCount, bookListRemaining.size(), "The ReservedBooks records should still exist");
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
	@InSequence(value = 55)
	@BytemanRule(name = "rule55", 
			targetClass = "BookInventory", 
			targetMethod = "removeFromReservedBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error55\")")
	public void runTest_ReservedBooks_RemoveAsList_interface_exception_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_RemoveAsList_interface_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		int reservedBooksCount = 2;
		bookInventoryHelper.assureRemoveAll();
		bookInventoryHelper.addReservedBooks(reservedBooksCount);
		
		// setup expectations
		isExceptionExpected = true;
		
		// prepare test data
		List<Book> bookListToRemove = bookInventoryHelper.getAllReservedBooks();
		Assert.equals(reservedBooksCount, bookListToRemove.size(), "The ReservedBooks records should exist");
		
		try {
			// execute test action
			runTest_ReservedBooks_RemoveAsList(bookListToRemove);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error55");

			// validate persistent state
			List<Book> bookListRemaining = bookInventoryHelper.getAllReservedBooks();
			Assert.equals(reservedBooksCount, bookListRemaining.size(), "The ReservedBooks records should still exist");
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
	@InSequence(value = 56)
	@BytemanRule(name = "rule56", 
			targetClass = "BookInventoryRepositoryImpl", 
			targetMethod = "removeReservedBooksRecords", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error56\")")
	public void runTest_ReservedBooks_RemoveAsList_repository_exception_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_RemoveAsList_repository_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		int reservedBooksCount = 2;
		bookInventoryHelper.assureRemoveAll();
		bookInventoryHelper.addReservedBooks(reservedBooksCount);
		
		// setup expectations
		isExceptionExpected = true;
		
		// prepare test data
		List<Book> bookListToRemove = bookInventoryHelper.getAllReservedBooks();
		Assert.equals(reservedBooksCount, bookListToRemove.size(), "The ReservedBooks records should exist");
		
		try {
			// execute test action
			runTest_ReservedBooks_RemoveAsList(bookListToRemove);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error56");

			// validate persistent state
			List<Book> bookListRemaining = bookInventoryHelper.getAllReservedBooks();
			Assert.equals(reservedBooksCount, bookListRemaining.size(), "The ReservedBooks records should still exist");
			Bookshop2Fixture.assertSameBook(bookListRemaining, bookListToRemove);

		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runTest_ReservedBooks_RemoveAsList(final Collection<Book> bookList) throws Exception {
		runTest(new Runnable() {
			public void run() {
				bookInventoryManager.removeFromReservedBooks(bookList);
			}
		});
	}
	
	public void runTest(Runnable runnable) throws Exception {
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

	
	public void verifyNoReservedBooksRecordsExist() throws Exception {
		List<Book> allBookList = bookInventoryHelper.getAllReservedBooks();
		verifyNoReservedBooksRecordsExist(allBookList);
	}

	public void verifyNoReservedBooksRecordsExistIC() throws Exception {
		List<Book> allBookList = bookInventoryHelper.getAllReservedBooksIC();
		verifyNoReservedBooksRecordsExist(allBookList);
	}

	public void verifyNoReservedBooksRecordsExist(List<Book> bookList) throws Exception {
		Assert.isEmpty(bookList, "ReservedBooks should be empty");
	}

	public void verifyReservedBooksRecordExists(Book book) throws Exception {
		List<Book> allBookList = bookInventoryHelper.getAllReservedBooks();
		verifyReservedBooksRecordExists(allBookList, book);
	}
	
	public void verifyReservedBooksRecordExistsIC(Book book) throws Exception {
		List<Book> allBookList = bookInventoryHelper.getAllReservedBooksIC();
		verifyReservedBooksRecordExists(allBookList, book);
	}
	
	public void verifyReservedBooksRecordExists(Collection<Book> bookList, Book book) throws Exception {
		Assert.isTrue(!bookList.isEmpty(), "No ReservedBooks records exist");
		//Assert.isTrue(bookList.contains(book), "ReservedBooks record not found: "+book);
		Assert.isTrue(bookList.size() == 1, "Only 1 ReservedBooks record should exist");
		Bookshop2Fixture.assertSameBook(book, bookList.iterator().next());
	}

	public void verifyReservedBooksRecordsExist(Collection<Book> bookList) throws Exception {
		List<Book> allBookList = bookInventoryHelper.getAllReservedBooks();
		verifyReservedBooksRecordsExist(allBookList, bookList);
	}
	
	public void verifyReservedBooksRecordsExistIC(Collection<Book> bookList) throws Exception {
		List<Book> allBookList = bookInventoryHelper.getAllReservedBooksIC();
		verifyReservedBooksRecordsExist(allBookList, bookList);
	}

	public void verifyReservedBooksRecordsExist(Collection<Book> bookList, Collection<Book> bookList2) throws Exception {
		Assert.isTrue(!bookList.isEmpty(), "No ReservedBooks records exist");
//		Iterator<Book> iterator = bookList2.iterator();
//		while (iterator.hasNext()) {
//			Book book = iterator.next();
//			Assert.isTrue(bookList.contains(book), "ReservedBooks record not found: "+book);
//		}
		Assert.equals(bookList.size(), bookList2.size(), "Unexpected ReservedBooks record counts");
		Bookshop2Fixture.assertSameBook(bookList, bookList2);
	}

}
