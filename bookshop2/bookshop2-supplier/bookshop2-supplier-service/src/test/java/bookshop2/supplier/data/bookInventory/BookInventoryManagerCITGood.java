package bookshop2.supplier.data.bookInventory;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import bookshop2.Book;
import bookshop2.supplier.SupplierTestEARBuilder;
import bookshop2.supplier.SupplierTestWARBuilder;
import bookshop2.util.Bookshop2Fixture;

import common.tx.state.AbstractStateManager;


@RunWith(Arquillian.class)
public class BookInventoryManagerCITGood extends AbstractDataUnitArquillionTest {
	
	@Inject
	private BookInventoryManager bookInventoryManager;
	
	@Inject
	private BookInventoryHelper bookInventoryHelper;
	
	@Inject
	private TransactionalCaller transactionalCaller;

//	@Inject
//	private ReservedBooks_AddAsItem_Invoker reservedBooks_AddAsItem_invoker;
//
//	@Inject
//	private ReservedBooks_AddAsList_Invoker reservedBooks_AddAsList_invoker;


	public BookInventoryManagerCITGood() {
		//nothing for now
	}

	
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
		return BookInventoryManagerCITGood.class;
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
		return bookInventoryManager;
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
	
	protected void createTransactionControl() throws Exception {
		transactionTestControl = new TransactionTestControl();
		transactionTestControl.assureTransactionManager();
	}
	
	protected void initializeBookInventoryHelper() throws Exception {
		bookInventoryHelper.setJmxManager(jmxManager);
		bookInventoryHelper.initialize(transactionTestControl);
	}
	
	protected void clearBookInventoryContext() throws Exception {
		bookInventoryHelper.clearContext(BookInventoryManagerMBean.MBEAN_NAME);
	}
	
	@After
	public void tearDown() throws Exception {
		transactionTestControl.clearTransactions();
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
		builder.addClass(BookInventoryManagerCITGood.class);
		WebArchive war = builder.create();
		return war;
	}
	
	@Test
	//@Ignore
	@InSequence(value = 1)
	public void runTest_ReservedBooks_GetAllAsList_success() throws Exception {
		//nothing for now
	}
	
	@Test
	//@Ignore
	@InSequence(value = 2)
	public void runTest_ReservedBooks_GetAllAsList_utx_commit() throws Exception {
		//nothing for now
	}
	
	@Test
	//@Ignore
	@InSequence(value = 3)
	public void runTest_ReservedBooks_GetAllAsList_utx_rollback() throws Exception {
		//nothing for now
	}
	
	@Test
	//@Ignore
	@InSequence(value = 4)
	@BytemanRule(name = "rule4", 
			targetClass = "BookInventoryManager", 
			targetMethod = "getAllReservedBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error4\")")
	public void runTest_ReservedBooks_GetAllAsList_manager_exception_rollback() throws Exception {
		//nothing for now
	}
	
	@Test
	//@Ignore
	@InSequence(value = 5)
	@BytemanRule(name = "rule5", 
			targetClass = "BookInventory", 
			targetMethod = "getAllReservedBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error5\")")
	public void runTest_ReservedBooks_GetAllAsList_interface_exception_rollback() throws Exception {
		//nothing for now
	}
	
	@Test
	//@Ignore
	@InSequence(value = 6)
	@BytemanRule(name = "rule6", 
			targetClass = "BookInventoryRepositoryImpl", 
			targetMethod = "getAllReservedBooksRecords", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error6\")")
	public void runTest_ReservedBooks_GetAllAsList_repository_exception_rollback() throws Exception {
		//nothing for now
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
	@InSequence(value = 7)
	public void runTest_ReservedBooks_GetMatchingAsMap_success() throws Exception {
		//nothing for now
	}
	
	@Test
	//@Ignore
	@InSequence(value = 8)
	public void runTest_ReservedBooks_GetMatchingAsMap_utx_commit() throws Exception {
		//nothing for now
	}
	
	@Test
	//@Ignore
	@InSequence(value = 9)
	public void runTest_ReservedBooks_GetMatchingAsMap_utx_rollback() throws Exception {
		//nothing for now
	}
	
	@Test
	//@Ignore
	@InSequence(value = 10)
	@BytemanRule(name = "rule10", 
			targetClass = "BookInventoryManager", 
			targetMethod = "getMatchingReservedBooksAsMap", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error10\")")
	public void runTest_ReservedBooks_GetMatchingAsMap_manager_exception_rollback() throws Exception {
		//nothing for now
	}
	
	@Test
	//@Ignore
	@InSequence(value = 11)
	@BytemanRule(name = "rule11", 
			targetClass = "BookInventory", 
			targetMethod = "getMatchingReservedBooksAsMap", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error11\")")
	public void runTest_ReservedBooks_GetMatchingAsMap_interface_exception_rollback() throws Exception {
		//nothing for now
	}
	
	@Test
	//@Ignore
	@InSequence(value = 12)
	@BytemanRule(name = "rule12", 
			targetClass = "BookInventoryRepositoryImpl", 
			targetMethod = "getMatchingReservedBooksAsMapRecords", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error12\")")
	public void runTest_ReservedBooks_GetMatchingAsMap_repository_exception_rollback() throws Exception {
		//nothing for now
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
	@InSequence(value = 13)
	public void runTest_ReservedBooks_AddAsItem_success() throws Exception {
		String testName = "runTest_ReservedBooks_AddAsItem_success";
		log.info(testName+": started");
		
		// prepare test context
		bookInventoryHelper.assureRemoveAll();
		
		// execute action
		Book bookToAdd = Bookshop2Fixture.create_Book();
		//reservedBooks_AddAsItem_invoker.runAction(bookToAdd);
		Runnable runnable = createAction_ReservedBooks_AddAsItem(bookToAdd);
		transactionalCaller.runAction(runnable);
		
		// validate cache state
		List<Book> bookList = bookInventoryHelper.getAllReservedBooks();
		Assert.equals(1, bookList.size(), "Only 1 ReservedBooks record should exist");
		Bookshop2Fixture.assertSameBook(bookToAdd, bookList.get(0));
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 27)
	public void runTest_ReservedBooks_AddAsItem_tx_commit() throws Exception {
		String testName = "runTest_ReservedBooks_AddAsItem_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookInventoryHelper.assureRemoveAll();
		
		// setup expectations
		transactionTestControl.beginTransaction();
		
		// execute action
		//Transaction transaction = transactionTestControl.getTransactionManager().getTransaction();
		Book bookToAdd = Bookshop2Fixture.create_Book();
		runAction_ReservedBooks_AddAsItem(bookToAdd);
		
		// validate intermediate persistent state
		//List<Book> bookList = bookInventoryHelper.getAllReservedBooksIC();
		//Assert.isEmpty(bookList, "ReservedBooks should be empty");
		verifyReservedBooksRecordExistsIC(bookToAdd);
		
		transactionTestControl.commitTransaction();
		//transactionTestControl.clearTransactions();
		//currentAction = ThreadActionData.currentAction();
		//ThreadActionData.popAction();
		
		// validate persistent state
		verifyReservedBooksRecordExists(bookToAdd);
		//List<Book> bookList2 = bookInventoryHelper.getAllReservedBooks();
		//Assert.equals(1, bookList2.size(), "Only 1 ReservedBooks record should exist");
		//Bookshop2Fixture.assertSameBook(bookToAdd, bookList2.get(0));
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 28)
	public void runTest_ReservedBooks_AddAsItem_tx_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_AddAsItem_tx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookInventoryHelper.assureRemoveAll();
		
		// setup expectations
		transactionTestControl.beginTransaction();
		
		// execute action
		//Transaction transaction = transactionTestControl.getTransactionManager().getTransaction();
		Book bookToAdd = Bookshop2Fixture.create_Book();
		runAction_ReservedBooks_AddAsItem(bookToAdd);

		// validate intermediate persistent state
		//TODO somehow access the backing store here to validate that it has been populated
		//List<Book> bookList = bookInventoryHelper.getAllReservedBooksIC();
		//Assert.isEmpty(bookList, "ReservedBooks should be empty");
		//verifyReservedBooksRecordExistsIC(bookToAdd);
		//BasicAction currentAction = ThreadActionData.currentAction();
		verifyReservedBooksRecordExistsIC(bookToAdd);
		//verifyNoReservedBooksRecordsExist();

		transactionTestControl.rollbackTransaction();
		//transactionTestControl.clearTransactions();
		//currentAction = ThreadActionData.currentAction();
		//ThreadActionData.popAction();
		
		// validate persistent state
		//List<Book> bookList2 = bookInventoryHelper.getAllReservedBooks();
		//Assert.isEmpty(bookList2, "ReservedBooks should be empty");
		verifyNoReservedBooksRecordsExist();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 29)
	public void runTest_ReservedBooks_AddAsItem_utx_commit() throws Exception {
		String testName = "runTest_ReservedBooks_AddAsItem_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookInventoryHelper.assureRemoveAll();
		
		// setup expectations
		transactionTestControl.beginUserTransaction();
		
		// execute action
		//Transaction transaction = transactionTestControl.getTransactionManager().getTransaction();
		Book bookToAdd = Bookshop2Fixture.create_Book();
		runAction_ReservedBooks_AddAsItem(bookToAdd);
		
		// validate intermediate persistent state
		//List<Book> bookList = bookInventoryHelper.getAllReservedBooksIC();
		//Assert.isEmpty(bookList, "ReservedBooks should be empty");
		verifyReservedBooksRecordExistsIC(bookToAdd);
		//verifyNoReservedBooksRecordsExist();

		transactionTestControl.commitUserTransaction();
		//transactionTestControl.clearTransactions();
		//currentAction = ThreadActionData.currentAction();
		//ThreadActionData.popAction();
		
		// validate persistent state
		verifyReservedBooksRecordExists(bookToAdd);
		//List<Book> bookList2 = bookInventoryHelper.getAllReservedBooks();
		//Assert.equals(1, bookList2.size(), "Only 1 ReservedBooks record should exist");
		//Bookshop2Fixture.assertSameBook(bookToAdd, bookList2.get(0));
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 30)
	public void runTest_ReservedBooks_AddAsItem_utx_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_AddAsItem_utx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookInventoryHelper.assureRemoveAll();
		
		// setup expectations
		//transactionTestControl.clearTransactions();
		transactionTestControl.beginUserTransaction();
		
		// execute action
		//Transaction transaction = transactionTestControl.getTransactionManager().getTransaction();
		Book bookToAdd = Bookshop2Fixture.create_Book();
		runAction_ReservedBooks_AddAsItem(bookToAdd);

		// validate intermediate persistent state
		//TODO somehow access the backing store here to validate that it has been populated
		//List<Book> bookList = bookInventoryHelper.getAllReservedBooksIC();
		//Assert.isEmpty(bookList, "ReservedBooks should be empty");
		//verifyReservedBooksRecordExistsIC(bookToAdd);
		verifyReservedBooksRecordExistsIC(bookToAdd);
		//verifyNoReservedBooksRecordsExist();

		transactionTestControl.rollbackUserTransaction();
		//transactionTestControl.clearTransactions();
		
		// validate persistent state
		//List<Book> bookList2 = bookInventoryHelper.getAllReservedBooks();
		//Assert.isEmpty(bookList2, "ReservedBooks should be empty");
		verifyNoReservedBooksRecordsExist();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 31)
	@BytemanRule(name = "rule31", 
			targetClass = "BookInventoryManager", 
			targetMethod = "addToReservedBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error31\")")
	public void runTest_ReservedBooks_AddAsItem_manager_exception_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_AddAsItem_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookInventoryHelper.assureRemoveAll();
		
		// setup expectations
		isRollbackExpected = true;
		
		try {
			// execute action
			Book bookToAdd = Bookshop2Fixture.create_Book();
			//reservedBooks_AddAsItem_invoker.runAction(bookToAdd);
			Runnable runnable = createAction_ReservedBooks_AddAsItem(bookToAdd);
			transactionalCaller.runAction(runnable);
			fail("Exception should have been thrown");
			
		} catch (Exception e) {
			Assert.exception(e, "error31");
			Assert.exception(e, org.aries.ApplicationFailure.class);
			
		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}

		// validate cache state
		List<Book> bookList = bookInventoryHelper.getAllReservedBooks();
		Assert.isEmpty(bookList, "No ReservedBooks records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 32)
	@BytemanRule(name = "rule32", 
			targetClass = "BookInventory", 
			targetMethod = "addToReservedBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error32\")")
	public void runTest_ReservedBooks_AddAsItem_interface_exception_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_AddAsItem_interface_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookInventoryHelper.assureRemoveAll();
		
		// setup expectations
		isRollbackExpected = true;
		
		try {
			// execute action
			Book bookToAdd = Bookshop2Fixture.create_Book();
			//reservedBooks_AddAsItem_invoker.runAction(bookToAdd);
			Runnable runnable = createAction_ReservedBooks_AddAsItem(bookToAdd);
			transactionalCaller.runAction(runnable);
			fail("Exception should have been thrown");
			
		} catch (Exception e) {
			Assert.exception(e, "error32");
			Assert.exception(e, org.aries.ApplicationFailure.class);
			
		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		// validate cache state
		List<Book> bookList = bookInventoryHelper.getAllReservedBooks();
		Assert.isEmpty(bookList, "No ReservedBooks records should exist");

		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 33)
	@BytemanRule(name = "rule33", 
			targetClass = "BookInventoryRepositoryImpl", 
			targetMethod = "addReservedBooksRecord", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error33\")")
	public void runTest_ReservedBooks_AddAsItem_repository_exception_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_AddAsItem_repository_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookInventoryHelper.assureRemoveAll();
		
		// setup expectations
		isRollbackExpected = true;
		
		try {
			// execute action
			Book bookToAdd = Bookshop2Fixture.create_Book();
			//reservedBooks_AddAsItem_invoker.runAction(bookToAdd);
			Runnable runnable = createAction_ReservedBooks_AddAsItem(bookToAdd);
			transactionalCaller.runAction(runnable);
			fail("Exception should have been thrown");
			
		} catch (Exception e) {
			Assert.exception(e, "error33");
			Assert.exception(e, org.aries.ApplicationFailure.class);
			
		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}

		// validate cache state
		List<Book> bookList = bookInventoryHelper.getAllReservedBooks();
		Assert.isEmpty(bookList, "No ReservedBooks records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
//	@Stateful
//	@TransactionAttribute(REQUIRED)
//	public class ReservedBooks_AddAsItem_Invoker {
//		
//		public ReservedBooks_AddAsItem_Invoker() {
//		}
//
//		public void runAction(Book book) throws Exception {
//			runAction_ReservedBooks_AddAsItem(book);
//		}
//	}
	
	public Runnable createAction_ReservedBooks_AddAsItem(final Book book) throws Exception {
		return new Runnable() {
			public void run() {
				//if (isTransactional())
				bookInventoryManager.addToReservedBooks(book);
			}
		};
	}
	
	public void runAction_ReservedBooks_AddAsItem(final Book book) throws Exception {
		runTest(new Runnable() {
			public void run() {
				//if (isTransactional())
				bookInventoryManager.addToReservedBooks(book);
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 11)
	public void runTest_ReservedBooks_AddAsList_success() throws Exception {
		String testName = "runTest_ReservedBooks_AddAsList_success";
		log.info(testName+": started");
		
		// prepare test context
		bookInventoryHelper.assureRemoveAll();
		
		// execute action
		Set<Book> bookSetToAdd = Bookshop2Fixture.createSet_Book();
		//reservedBooks_AddAsList_invoker.runAction(bookSetToAdd);
		Runnable runnable = createAction_ReservedBooks_AddAsList(bookSetToAdd);
		transactionalCaller.runAction(runnable);
		
		// validate persistent state
		List<Book> bookList = bookInventoryHelper.getAllReservedBooks();
		Bookshop2Fixture.assertSameBook(bookSetToAdd, bookList);
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 12)
	public void runTest_ReservedBooks_AddAsList_tx_commit() throws Exception {
		String testName = "runTest_ReservedBooks_AddAsList_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookInventoryHelper.assureRemoveAll();

		transactionTestControl.beginTransaction();
		
		// execute action
		//Transaction transaction = transactionTestControl.getTransactionManager().getTransaction();
		Set<Book> bookSetToAdd = Bookshop2Fixture.createSet_Book();
		runTest_ReservedBooks_AddAsList(bookSetToAdd);
		
		// validate intermediate persistent state
		//List<Book> bookList = bookInventoryHelper.getAllReservedBooksIC();
		//Assert.isEmpty(bookList, "ReservedBooks should be empty");
		//verifyReservedBooksRecordsExistIC(bookSetToAdd);
		//verifyNoReservedBooksRecordsExist();
		//BasicAction currentAction = ThreadActionData.currentAction();
		verifyReservedBooksRecordsExistIC(bookSetToAdd);
		//verifyNoReservedBooksRecordsExist();
			
		transactionTestControl.commitTransaction();
		//transactionTestControl.clearTransactions();
		//currentAction = ThreadActionData.currentAction();
		//ThreadActionData.popAction();
		
		// validate persistent state
		//List<Book> bookList2 = bookInventoryHelper.getAllReservedBooks();
		//Bookshop2Fixture.assertSameBook(bookSetToAdd, bookList2);
		verifyReservedBooksRecordsExist(bookSetToAdd);

		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 13)
	public void runTest_ReservedBooks_AddAsList_tx_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_AddAsList_tx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookInventoryHelper.assureRemoveAll();

		transactionTestControl.beginTransaction();
		
		// execute action
		//Transaction transaction = transactionTestControl.getTransactionManager().getTransaction();
		Set<Book> bookSetToAdd = Bookshop2Fixture.createSet_Book();
		runTest_ReservedBooks_AddAsList(bookSetToAdd);
		
		// validate intermediate persistent state
		//List<Book> bookList = bookInventoryHelper.getAllReservedBooksIC();
		//Assert.isEmpty(bookList, "ReservedBooks should be empty");
		//verifyReservedBooksRecordsExistIC(bookSetToAdd);
		//BasicAction currentAction = ThreadActionData.currentAction();
		verifyReservedBooksRecordsExistIC(bookSetToAdd);
		
		transactionTestControl.rollbackTransaction();
		//transactionTestControl.clearTransactions();
		//currentAction = ThreadActionData.currentAction();
		//ThreadActionData.popAction();

		// validate persistent state
		//List<Book> bookList2 = bookInventoryHelper.getAllReservedBooks();
		//Assert.isEmpty(bookList2, "ReservedBooks should be empty");
		verifyNoReservedBooksRecordsExist();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}

	@Rule
	public ExpectedException managerException = ExpectedException.none();
	
	@Test
	//@Ignore
	@InSequence(value = 14)
	@BytemanRule(name = "rule14", 
			targetClass = "BookInventoryManager", 
			targetMethod = "addToReservedBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error14\")")
	public void runTest_ReservedBooks_AddAsList_manager_exception_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_AddAsList_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookInventoryHelper.assureRemoveAll();

		// setup expectations
		isRollbackExpected = true;
		//managerException.expect(org.aries.ApplicationFailure.class);
		//managerException.expectMessage("\"error14\"");

		try {
			// execute action
			Set<Book> bookSetToAdd = Bookshop2Fixture.createSet_Book();
			//reservedBooks_AddAsList_invoker.runAction(bookSetToAdd);
			Runnable runnable = createAction_ReservedBooks_AddAsList(bookSetToAdd);
			transactionalCaller.runAction(runnable);
			fail("Exception should have been thrown");
		
		} catch (Exception e) {
			Assert.exception(e, "error14");
			Assert.exception(e, org.aries.ApplicationFailure.class);
			
		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}

		// validate persistent state
		List<Book> bookList = bookInventoryHelper.getAllReservedBooks();
		Assert.isEmpty(bookList, "ReservedBooks should be empty");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 15)
	@BytemanRule(name = "rule15", 
			targetClass = "BookInventory", 
			targetMethod = "addToReservedBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error15\")")
	public void runTest_ReservedBooks_AddAsList_interface_exception_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_AddAsList_interface_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookInventoryHelper.assureRemoveAll();

		// setup expectations
		isRollbackExpected = true;

		try {
			// execute action
			Set<Book> bookSetToAdd = Bookshop2Fixture.createSet_Book();
			//reservedBooks_AddAsList_invoker.runAction(bookSetToAdd);
			Runnable runnable = createAction_ReservedBooks_AddAsList(bookSetToAdd);
			transactionalCaller.runAction(runnable);
			fail("Exception should have been thrown");
			
		} catch (Exception e) {
			Assert.exception(e, "error15");
			Assert.exception(e, org.aries.ApplicationFailure.class);
			
		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}

		// validate persistent state
		List<Book> bookList = bookInventoryHelper.getAllReservedBooks();
		Assert.isEmpty(bookList, "ReservedBooks should be empty");

		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 16)
	@BytemanRule(name = "rule16", 
			targetClass = "BookInventoryRepositoryImpl", 
			targetMethod = "addReservedBooksRecords", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error16\")")
	public void runTest_ReservedBooks_AddAsList_repository_exception_rollback() throws Exception {
		String testName = "runTest_ReservedBooks_AddAsList_repository_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookInventoryHelper.assureRemoveAll();

		// setup expectations
		isRollbackExpected = true;

		try {
			// execute action
			Set<Book> bookSetToAdd = Bookshop2Fixture.createSet_Book();
			//reservedBooks_AddAsList_invoker.runAction(bookSetToAdd);
			Runnable runnable = createAction_ReservedBooks_AddAsList(bookSetToAdd);
			transactionalCaller.runAction(runnable);
			fail("Exception should have been thrown");
			
		} catch (Exception e) {
			Assert.exception(e, "error16");
			Assert.exception(e, org.aries.ApplicationFailure.class);
			
		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}

		// validate persistent state
		List<Book> bookList = bookInventoryHelper.getAllReservedBooks();
		Assert.isEmpty(bookList, "ReservedBooks should be empty");

		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
//	@Stateful
//	@TransactionAttribute(REQUIRED)
//	public class ReservedBooks_AddAsList_Invoker {
//		
//		public ReservedBooks_AddAsList_Invoker() {
//		}
//
//		public void runAction(Collection<Book> bookList) throws Exception {
//			runTest_ReservedBooks_AddAsList(bookList);
//		}
//	}
	
	public Runnable createAction_ReservedBooks_AddAsList(final Collection<Book> bookList) throws Exception {
		return new Runnable() {
			public void run() {
				//if (isTransactional())
				bookInventoryManager.addToReservedBooks(bookList);
			}
		};
	}
	
	public void runTest_ReservedBooks_AddAsList(final Collection<Book> bookList) throws Exception {
		runTest(new Runnable() {
			public void run() {
				bookInventoryManager.addToReservedBooks(bookList);
			}
		});
	}
	
	public void runTest(Runnable runnable) throws Exception {
		//if (isTransactional())
		//	beginTransaction();
		
		try {
			runnable.run();
			if (isRollbackExpected)
				fail("Exception should have been thrown");
			//if (isTransactional())
			//	commitTransaction();
			
		} catch (Exception e) {
			//if (isTransactional())
			//	rollbackTransaction();
		}
	}

//	protected Callable createAddToReservedBooksCallable(final Set<Book> books) {
//		return new Callable() {
//
//			@Override
//			public Object call() throws Exception {
//				return null;
//			}
//		};		
//	}
	
//	public void runTest_AddToReservedBooks(Set<Book> books) throws Exception {
//		if (isTransactional())
//			beginTransaction();
//		
//		try {
//			bookInventoryManager.addToReservedBooks(books);
//			if (isRollbackExpected)
//				fail("Exception should have been thrown");
//			if (isTransactional())
//				commitTransaction();
//
//		} catch (Exception e) {
//			if (isTransactional())
//				rollbackTransaction();
//		}
//	}

	
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
		Iterator<Book> iterator = bookList2.iterator();
//		while (iterator.hasNext()) {
//			Book book = iterator.next();
//			Assert.isTrue(bookList.contains(book), "ReservedBooks record not found: "+book);
//		}
		Assert.equals(bookList.size(), bookList2.size(), "Unexpected ReservedBooks record counts");
		Bookshop2Fixture.assertSameBook(bookList, bookList2);
	}



}
