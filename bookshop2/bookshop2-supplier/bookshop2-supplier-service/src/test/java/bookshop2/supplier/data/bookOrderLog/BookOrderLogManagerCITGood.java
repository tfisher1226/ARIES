package bookshop2.supplier.data.bookOrderLog;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transaction;

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
import org.junit.Test;
import org.junit.runner.RunWith;

import com.arjuna.ats.arjuna.coordinator.BasicAction;
import com.arjuna.ats.internal.arjuna.thread.ThreadActionData;

import bookshop2.Order;
import bookshop2.supplier.SupplierTestEARBuilder;
import bookshop2.supplier.SupplierTestWARBuilder;
import bookshop2.supplier.data.bookInventory.TransactionalCaller;
import bookshop2.util.Bookshop2Fixture;
import common.tx.state.AbstractStateManager;


@RunWith(Arquillian.class)
public class BookOrderLogManagerCITGood extends AbstractDataUnitArquillionTest {
	
	@Inject
	private BookOrderLogManager bookOrderLogManager;

	@Inject
	private BookOrderLogHelper bookOrderLogHelper;
	
	@Inject
	private TransactionalCaller transactionalCaller;

	
	public BookOrderLogManagerCITGood() {
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
		return BookOrderLogManagerCITGood.class;
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
		return bookOrderLogManager;
	}
	
	@Before
	public void setUp() throws Exception {
		if (!isServerStarted()) {
			startServer();
		} else {
			super.setUp();
			createTransactionControl();
			initializeBookOrderLogHelper();
			clearBookOrderLogContext();
		}
	}
	
	protected void createTransactionControl() throws Exception {
		transactionTestControl = new TransactionTestControl();
		transactionTestControl.assureTransactionManager();
	}
	
	protected void initializeBookOrderLogHelper() throws Exception {
		bookOrderLogHelper.setJmxManager(jmxManager);
		bookOrderLogHelper.initialize(transactionTestControl);
	}
	
	protected void clearBookOrderLogContext() throws Exception {
		bookOrderLogHelper.clearContext(BookOrderLogManager.MBEAN_NAME);
	}
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
		clearTransaction();
		clearBookOrderLogContext();
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
		builder.addClass(BookOrderLogManagerCITGood.class);
		WebArchive war = builder.create();
		return war;
	}
	
	@Test
	//@Ignore
	@InSequence(value = 43)
	public void runTest_BookOrders_GetAllAsList_success() throws Exception {
		//nothing for now
	}
	
	@Test
	//@Ignore
	@InSequence(value = 44)
	public void runTest_BookOrders_GetAllAsList_utx_commit() throws Exception {
		//nothing for now
	}
	
	@Test
	//@Ignore
	@InSequence(value = 45)
	public void runTest_BookOrders_GetAllAsList_utx_rollback() throws Exception {
		//nothing for now
	}
	
	@Test
	//@Ignore
	@InSequence(value = 46)
	@BytemanRule(name = "rule46", 
			targetClass = "BookOrderLogManager", 
			targetMethod = "getAllBookOrders", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error46\")")
	public void runTest_BookOrders_GetAllAsList_manager_exception_rollback() throws Exception {
		//nothing for now
	}
	
	@Test
	//@Ignore
	@InSequence(value = 47)
	@BytemanRule(name = "rule47", 
			targetClass = "BookOrderLog", 
			targetMethod = "getAllBookOrders", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error47\")")
	public void runTest_BookOrders_GetAllAsList_interface_exception_rollback() throws Exception {
		//nothing for now
	}
	
	@Test
	//@Ignore
	@InSequence(value = 48)
	@BytemanRule(name = "rule48", 
			targetClass = "BookOrderLogRepositoryImpl", 
			targetMethod = "getAllBookOrdersRecords", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error48\")")
	public void runTest_BookOrders_GetAllAsList_repository_exception_rollback() throws Exception {
		//nothing for now
	}
	
	public void runTest_BookOrders_GetAllAsList() throws Exception {
		runTest(new Runnable() {
			public void run() {
				bookOrderLogManager.getAllBookOrders();
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 49)
	public void runTest_BookOrders_GetMatchingAsList_success() throws Exception {
		//nothing for now
	}
	
	@Test
	//@Ignore
	@InSequence(value = 50)
	public void runTest_BookOrders_GetMatchingAsList_utx_commit() throws Exception {
		//nothing for now
	}
	
	@Test
	//@Ignore
	@InSequence(value = 51)
	public void runTest_BookOrders_GetMatchingAsList_utx_rollback() throws Exception {
		//nothing for now
	}
	
	@Test
	//@Ignore
	@InSequence(value = 52)
	@BytemanRule(name = "rule52", 
			targetClass = "BookOrderLogManager", 
			targetMethod = "getMatchingBookOrders", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error52\")")
	public void runTest_BookOrders_GetMatchingAsList_manager_exception_rollback() throws Exception {
		//nothing for now
	}
	
	@Test
	//@Ignore
	@InSequence(value = 53)
	@BytemanRule(name = "rule53", 
			targetClass = "BookOrderLog", 
			targetMethod = "getMatchingBookOrders", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error53\")")
	public void runTest_BookOrders_GetMatchingAsList_interface_exception_rollback() throws Exception {
		//nothing for now
	}
	
	@Test
	//@Ignore
	@InSequence(value = 54)
	@BytemanRule(name = "rule54", 
			targetClass = "BookOrderLogRepositoryImpl", 
			targetMethod = "getMatchingBookOrdersRecords", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error54\")")
	public void runTest_BookOrders_GetMatchingAsList_repository_exception_rollback() throws Exception {
		//nothing for now
	}
	
	public void runTest_BookOrders_GetMatchingAsList(final Collection<Order> orderList) throws Exception {
		runTest(new Runnable() {
			public void run() {
				bookOrderLogManager.getMatchingBookOrders(orderList);
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 55)
	public void runTest_BookOrders_AddAsItem_success() throws Exception {
		String testName = "runTest_BookOrders_AddAsItem_success";
		log.info(testName+": started");
		
		// prepare test context
		bookOrderLogHelper.assureRemoveAll();
		
		// execute test action
		Order orderToAdd = Bookshop2Fixture.create_Order();
		runTest_BookOrders_AddAsItem(orderToAdd);
		
		// validate persistent state
		List<Order> orderList = bookOrderLogHelper.getAllBookOrders();
		Assert.equals(1, orderList.size(), "Only 1 BookOrders record should exist");
		Bookshop2Fixture.assertSameOrder(orderToAdd, orderList.get(0));
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 56)
	public void runTest_BookOrders_AddAsItem_utx_commit() throws Exception {
		String testName = "runTest_BookOrders_AddAsItem_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookOrderLogHelper.assureRemoveAll();
		
		// setup expectations
		isUserTransaction = true;
		
		// execute test action
		Order orderToAdd = Bookshop2Fixture.create_Order();
		runTest_BookOrders_AddAsItem(orderToAdd);
		
		// validate persistent state
		List<Order> orderList = bookOrderLogHelper.getAllBookOrders();
		Assert.equals(1, orderList.size(), "Only 1 BookOrders record should exist");
		Bookshop2Fixture.assertSameOrder(orderToAdd, orderList.get(0));
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 57)
	public void runTest_BookOrders_AddAsItem_utx_rollback() throws Exception {
		String testName = "runTest_BookOrders_AddAsItem_utx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookOrderLogHelper.assureRemoveAll();
		
		// setup expectations
		isUserTransaction = true;
		
		// execute test action
		Order orderToAdd = Bookshop2Fixture.create_Order();
		runTest_BookOrders_AddAsItem(orderToAdd);
		
		// validate persistent state
		List<Order> orderList = bookOrderLogHelper.getAllBookOrders();
		Assert.equals(1, orderList.size(), "Only 1 BookOrders record should exist");
		Bookshop2Fixture.assertSameOrder(orderToAdd, orderList.get(0));
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 58)
	@BytemanRule(name = "rule58", 
			targetClass = "BookOrderLogManager", 
			targetMethod = "addToBookOrders", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error58\")")
	public void runTest_BookOrders_AddAsItem_manager_exception_rollback() throws Exception {
		String testName = "runTest_BookOrders_AddAsItem_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookOrderLogHelper.assureRemoveAll();
		
		// setup expectations
		isRollbackExpected = true;
		
		try {
			// execute test action
			Order orderToAdd = Bookshop2Fixture.create_Order();
			runTest_BookOrders_AddAsItem(orderToAdd);
			
			// validate persistent state
			List<Order> orderList = bookOrderLogHelper.getAllBookOrders();
			Assert.isEmpty(orderList, "No BookOrders records should exist");
		
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
	@InSequence(value = 59)
	@BytemanRule(name = "rule59", 
			targetClass = "BookOrderLog", 
			targetMethod = "addToBookOrders", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error59\")")
	public void runTest_BookOrders_AddAsItem_interface_exception_rollback() throws Exception {
		String testName = "runTest_BookOrders_AddAsItem_interface_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookOrderLogHelper.assureRemoveAll();
		
		// setup expectations
		isRollbackExpected = true;
		
		try {
			// execute test action
			Order orderToAdd = Bookshop2Fixture.create_Order();
			runTest_BookOrders_AddAsItem(orderToAdd);
			
			// validate persistent state
			List<Order> orderList = bookOrderLogHelper.getAllBookOrders();
			Assert.isEmpty(orderList, "No BookOrders records should exist");

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
			targetClass = "BookOrderLogRepositoryImpl", 
			targetMethod = "addBookOrdersRecord", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error60\")")
	public void runTest_BookOrders_AddAsItem_repository_exception_rollback() throws Exception {
		String testName = "runTest_BookOrders_AddAsItem_repository_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookOrderLogHelper.assureRemoveAll();
		
		// setup expectations
		isRollbackExpected = true;
		
		try {
			// execute test action
			Order orderToAdd = Bookshop2Fixture.create_Order();
			runTest_BookOrders_AddAsItem(orderToAdd);
			
			// validate persistent state
			List<Order> orderList = bookOrderLogHelper.getAllBookOrders();
			Assert.isEmpty(orderList, "No BookOrders records should exist");
		
		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
			
			log.info(testName+": done");
			if (errorMessage != null)
				fail(errorMessage);
		}
	
	public void runTest_BookOrders_AddAsItem(final Order order) throws Exception {
		runTest(new Runnable() {
			public void run() {
				bookOrderLogManager.addToBookOrders(order);
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 61)
	public void runTest_BookOrders_AddAsList_success() throws Exception {
		String testName = "runTest_BookOrders_AddAsList_success";
		log.info(testName+": started");
		
		// prepare test context
		bookOrderLogHelper.assureRemoveAll();
		
		// execute test action
		List<Order> orderListToAdd = Bookshop2Fixture.createList_Order();
		runTest_BookOrders_AddAsList(orderListToAdd);
		
		// validate persistent state
		List<Order> orderList = bookOrderLogHelper.getAllBookOrders();
		Bookshop2Fixture.assertSameOrder(orderListToAdd, orderList);
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 62)
	public void runTest_BookOrders_AddAsList_utx_commit() throws Exception {
		String testName = "runTest_BookOrders_AddAsList_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookOrderLogHelper.assureRemoveAll();
		
		// setup expectations
		isUserTransaction = true;
		
		// execute test action
		List<Order> orderListToAdd = Bookshop2Fixture.createList_Order();
		runTest_BookOrders_AddAsList(orderListToAdd);
		
		// validate persistent state
		List<Order> orderList = bookOrderLogHelper.getAllBookOrders();
		Bookshop2Fixture.assertSameOrder(orderListToAdd, orderList);
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 63)
	public void runTest_BookOrders_AddAsList_utx_rollback() throws Exception {
		String testName = "runTest_BookOrders_AddAsList_utx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookOrderLogHelper.assureRemoveAll();
		
		// setup expectations
		//isUserTransaction = true;
		transactionTestControl.beginUserTransaction();
		
		try {
			// execute test action
			List<Order> orderListToAdd = Bookshop2Fixture.createList_Order();
			runTest_BookOrders_AddAsList(orderListToAdd);
	
			transactionTestControl.rollbackUserTransaction();
			//transactionTestControl.assertRolledBack();
	
			// validate persistent state
			List<Order> orderList = bookOrderLogHelper.getAllBookOrders();
			Assert.isEmpty(orderList, "No BookOrders records should exist");

		} finally {
			transactionTestControl.clearTransaction();
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 64)
	@BytemanRule(name = "rule64", 
			targetClass = "BookOrderLogManager",
			targetMethod = "addToBookOrders",
			targetLocation = "AT EXIT",
			action = "throw new org.aries.ApplicationFailure(\"error64\")")
	public void runTest_BookOrders_AddAsList_manager_exception_rollback() throws Exception {
		String testName = "runTest_BookOrders_AddAsList_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookOrderLogHelper.assureRemoveAll();
		
		// setup expectations
		isRollbackExpected = true;
		
		try {
			// execute test action
			List<Order> orderListToAdd = Bookshop2Fixture.createList_Order();
			runTest_BookOrders_AddAsList(orderListToAdd);
			
			// validate persistent state
			List<Order> orderList = bookOrderLogHelper.getAllBookOrders();
			Assert.isEmpty(orderList, "No BookOrders records should exist");

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
			targetClass = "BookOrderLog", 
			targetMethod = "addToBookOrders", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error65\")")
	public void runTest_BookOrders_AddAsList_interface_exception_rollback() throws Exception {
		String testName = "runTest_BookOrders_AddAsList_interface_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookOrderLogHelper.assureRemoveAll();

		// setup expectations
		isRollbackExpected = true;

		try {
			// execute test action
			List<Order> orderListToAdd = Bookshop2Fixture.createList_Order();
			runTest_BookOrders_AddAsList(orderListToAdd);

			// validate persistent state
			List<Order> orderList = bookOrderLogHelper.getAllBookOrders();
			Assert.isEmpty(orderList, "No BookOrders records should exist");

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
	@InSequence(value = 66)
	@BytemanRule(name = "rule66", 
			targetClass = "BookOrderLogRepositoryImpl",
			targetMethod = "addBookOrdersRecords",
			targetLocation = "AT EXIT",
			action = "throw new org.aries.ApplicationFailure(\"error66\")")
	public void runTest_BookOrders_AddAsList_repository_exception_rollback() throws Exception {
		String testName = "runTest_BookOrders_AddAsList_repository_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookOrderLogHelper.assureRemoveAll();
		
		// setup expectations
		isRollbackExpected = true;
		
		try {
			// execute test action
			List<Order> orderListToAdd = Bookshop2Fixture.createList_Order();
			runTest_BookOrders_AddAsList(orderListToAdd);

			// validate persistent state
			List<Order> orderList = bookOrderLogHelper.getAllBookOrders();
			Assert.isEmpty(orderList, "No BookOrders records should exist");

		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
			
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runTest_BookOrders_AddAsList(final Collection<Order> orderList) throws Exception {
		runTest(new Runnable() {
			public void run() {
				bookOrderLogManager.addToBookOrders(orderList);
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 67)
	public void runTest_BookOrders_RemoveAll_success() throws Exception {
		//nothing for now
	}
	
	@Test
	//@Ignore
	@InSequence(value = 68)
	public void runTest_BookOrders_RemoveAll_utx_commit() throws Exception {
		//nothing for now
	}
	
	@Test
	//@Ignore
	@InSequence(value = 69)
	public void runTest_BookOrders_RemoveAll_utx_rollback() throws Exception {
		//nothing for now
	}
	
	@Test
	//@Ignore
	@InSequence(value = 70)
	@BytemanRule(name = "rule70", 
			targetClass = "BookOrderLogManager", 
			targetMethod = "removeAllBookOrders", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error70\")")
	public void runTest_BookOrders_RemoveAll_manager_exception_rollback() throws Exception {
		//nothing for now
	}
	
	@Test
	//@Ignore
	@InSequence(value = 71)
	@BytemanRule(name = "rule71", 
			targetClass = "BookOrderLog", 
			targetMethod = "removeAllBookOrders", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error71\")")
	public void runTest_BookOrders_RemoveAll_interface_exception_rollback() throws Exception {
		//nothing for now
	}
	
	@Test
	//@Ignore
	@InSequence(value = 72)
	@BytemanRule(name = "rule72", 
			targetClass = "BookOrderLogRepositoryImpl", 
			targetMethod = "removeAllBookOrdersRecords", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error72\")")
	public void runTest_BookOrders_RemoveAll_repository_exception_rollback() throws Exception {
		//nothing for now
	}
	
	public void runTest_BookOrders_RemoveAll() throws Exception {
		runTest(new Runnable() {
			public void run() {
				bookOrderLogManager.removeAllBookOrders();
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 73)
	public void runTest_BookOrders_RemoveAsItem_success() throws Exception {
		String testName = "runTest_BookOrders_RemoveAsItem_success";
		log.info(testName+": started");
		
		// prepare test context
		bookOrderLogHelper.assureRemoveAll();
		Long id = bookOrderLogHelper.addBookOrders();
		
		// execute test action
		Order orderToRemove = bookOrderLogHelper.getBookOrdersById(id);
		Assert.notNull(orderToRemove, "The BookOrders record should exist");
		runTest_BookOrders_RemoveAsItem(orderToRemove);

		// validate persistent state
		Order orderRemoved = bookOrderLogHelper.getBookOrdersById(id);
		Assert.isNull(orderRemoved, "The BookOrders record should be removed");
		List<Order> orderListRemaining = bookOrderLogHelper.getAllBookOrders();
		Assert.isEmpty(orderListRemaining, "No BookOrders records should exist");
			
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 74)
	public void runTest_BookOrders_RemoveAsItem_utx_commit() throws Exception {
		String testName = "runTest_BookOrders_RemoveAsItem_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookOrderLogHelper.assureRemoveAll();
		Long id = bookOrderLogHelper.addBookOrders();
		
		// setup expectations
		isUserTransaction = true;
		transactionTestControl.beginTransaction();
		transactionTestControl.assertActive();
		
		// execute test action
		Order orderToRemove = bookOrderLogHelper.getBookOrdersById(id);
		Assert.notNull(orderToRemove, "The BookOrders record should exist");
		runTest_BookOrders_RemoveAsItem(orderToRemove);

		transactionTestControl.commitTransaction();
		transactionTestControl.assertCommitted();

		// validate persistent state
		Order orderRemoved = bookOrderLogHelper.getBookOrdersById(id);
		Assert.isNull(orderRemoved, "The BookOrders record should be removed");
		List<Order> orderListRemaining = bookOrderLogHelper.getAllBookOrders();
		Assert.isEmpty(orderListRemaining, "No BookOrders records should exist");
			
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 75)
	public void runTest_BookOrders_RemoveAsItem_utx_rollback() throws Exception {
		String testName = "runTest_BookOrders_RemoveAsItem_utx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookOrderLogHelper.assureRemoveAll();
		Long id = bookOrderLogHelper.addBookOrders();

		transactionTestControl.beginTransaction();
		transactionTestControl.assertActive();

		try {
			// execute test action
			Order orderToRemove = bookOrderLogHelper.getBookOrdersById(id);
			Assert.notNull(orderToRemove, "The BookOrders record should exist");
			runTest_BookOrders_RemoveAsItem(orderToRemove);
	
			transactionTestControl.rollbackTransaction();
			transactionTestControl.assertRolledBack();
	
			// validate persistent state
			Order orderRemaining = bookOrderLogHelper.getBookOrdersById(id);
			Assert.notNull(orderRemaining, "The BookOrders record should still exist");
			List<Order> orderListRemaining = bookOrderLogHelper.getAllBookOrders();
			Assert.equals(orderListRemaining.size(), 1, "Count of existing BookOrders records should be correct");
			
		} finally {
			transactionTestControl.clearTransaction();
		}

		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
//	@Test
//	//@Ignore
//	@InSequence(value = 76)
//	@BytemanRule(name = "rule76", 
//			targetClass = "BookOrderLogManager", 
//			targetMethod = "removeFromBookOrders", 
//			targetLocation = "AT EXIT", 
//			action = "throw new org.aries.ApplicationFailure(\"error76\")")
//	public void runTest_BookOrders_RemoveAsItem_manager_exception_rollback() throws Exception {
//		String testName = "runTest_BookOrders_RemoveAsItem_manager_exception_rollback";
//		log.info(testName+": started");
//		
//		// prepare test context
//		setupByteman(testName);
//		bookOrderLogHelper.assureRemoveAll();
//		
//		// setup expectations
//		isRollbackExpected = true;
//		
//		try {
//			// execute test action
//			Order orderToRemove = bookOrderLogHelper.getFromBookOrders(order.getId());
//			Assert.notNull(orderToRemove, "The BookOrders record should exist");
//			runTest_BookOrders_RemoveAsItem(orderToRemove);
//
//			// validate persistent state
//			order = bookOrderLogHelper.getFromBookOrders(order.getId());
//			Assert.notNull(order, "The BookOrders record should still exist");
//			List<Order> orderList = bookOrderLogHelper.getAllBookOrders();
//			Assert.equals(1, orderList.size(), "Only 1 BookOrders record should exist");
//			
//		} finally {
//			// cleanup test context
//			tearDownByteman(testName);
//		}
//		
//		log.info(testName+": done");
//		if (errorMessage != null)
//			fail(errorMessage);
//	}
//	
//	@Test
//	//@Ignore
//	@InSequence(value = 77)
//	@BytemanRule(name = "rule77", 
//			targetClass = "BookOrderLog", 
//			targetMethod = "removeFromBookOrders", 
//			targetLocation = "AT EXIT", 
//			action = "throw new org.aries.ApplicationFailure(\"error77\")")
//	public void runTest_BookOrders_RemoveAsItem_interface_exception_rollback() throws Exception {
//		String testName = "runTest_BookOrders_RemoveAsItem_interface_exception_rollback";
//		log.info(testName+": started");
//		
//		// prepare test context
//		setupByteman(testName);
//		bookOrderLogHelper.assureRemoveAll();
//		
//		// setup expectations
//		isRollbackExpected = true;
//		
//		try {
//			// execute test action
//			Order orderToRemove = bookOrderLogHelper.getFromBookOrders(order.getId());
//			Assert.notNull(orderToRemove, "The BookOrders record should exist");
//			runTest_BookOrders_RemoveAsItem(orderToRemove);
//
//			// validate persistent state
//			order = bookOrderLogHelper.getFromBookOrders(order.getId());
//			Assert.notNull(order, "The BookOrders record should still exist");
//			List<Order> orderList = bookOrderLogHelper.getAllBookOrders();
//			Assert.equals(1, orderList.size(), "Only 1 BookOrders record should exist");
//			
//		} finally {
//			// cleanup test context
//			tearDownByteman(testName);
//		}
//		
//		log.info(testName+": done");
//		if (errorMessage != null)
//			fail(errorMessage);
//	}
//	
//	@Test
//	//@Ignore
//	@InSequence(value = 78)
//	@BytemanRule(name = "rule78", 
//			targetClass = "BookOrderLogRepositoryImpl", 
//			targetMethod = "removeBookOrdersRecord", 
//			targetLocation = "AT EXIT", 
//			action = "throw new org.aries.ApplicationFailure(\"error78\")")
//	public void runTest_BookOrders_RemoveAsItem_repository_exception_rollback() throws Exception {
//		String testName = "runTest_BookOrders_RemoveAsItem_repository_exception_rollback";
//		log.info(testName+": started");
//		
//		// prepare test context
//		setupByteman(testName);
//		bookOrderLogHelper.assureRemoveAll();
//		
//		// setup expectations
//		isRollbackExpected = true;
//		
//		try {
//			// execute test action
//			Order orderToRemove = bookOrderLogHelper.getFromBookOrders(order.getId());
//			Assert.notNull(orderToRemove, "The BookOrders record should exist");
//			runTest_BookOrders_RemoveAsItem(orderToRemove);
//
//			// validate persistent state
//			order = bookOrderLogHelper.getFromBookOrders(order.getId());
//			Assert.notNull(order, "The BookOrders record should still exist");
//			List<Order> orderList = bookOrderLogHelper.getAllBookOrders();
//			Assert.equals(1, orderList.size(), "Only 1 BookOrders record should exist");
//			
//		} finally {
//			// cleanup test context
//			tearDownByteman(testName);
//		}
//		
//		log.info(testName+": done");
//		if (errorMessage != null)
//			fail(errorMessage);
//	}
	
	public void runTest_BookOrders_RemoveAsItem(final Order order) throws Exception {
		runTest2(new Runnable() {
			public void run() {
				bookOrderLogManager.removeFromBookOrders(order);
			}
		});
	}
	
//	@Test
//	//@Ignore
//	@InSequence(value = 79)
//	public void runTest_BookOrders_RemoveAsList_success() throws Exception {
//		String testName = "runTest_BookOrders_RemoveAsList_success";
//		log.info(testName+": started");
//		
//		// prepare test context
//		bookOrderLogHelper.assureRemoveAll();
//		
//		// execute test action
//		List<Order> orderListToRemove = bookOrderLogHelper.getAllBookOrders();
//		Assert.equals(orderCount, orderListToRemove.size(), "The BookOrders records should exist");
//		runTest_BookOrders_RemoveAsList(orderListToRemove);
//
//		// validate persistent state
//		List<Order> orderListRemaining = bookOrderLogHelper.getAllBookOrders();
//		Assert.isEmpty(orderListRemaining, "No BookOrders records should exist");
//			
//		log.info(testName+": done");
//		if (errorMessage != null)
//			fail(errorMessage);
//	}
//	
//	@Test
//	//@Ignore
//	@InSequence(value = 80)
//	public void runTest_BookOrders_RemoveAsList_utx_commit() throws Exception {
//		String testName = "runTest_BookOrders_RemoveAsList_utx_commit";
//		log.info(testName+": started");
//		
//		// prepare test context
//		bookOrderLogHelper.assureRemoveAll();
//		
//		// setup expectations
//		isUserTransaction = true;
//		
//		// execute test action
//		List<Order> orderListToRemove = bookOrderLogHelper.getAllBookOrders();
//		Assert.equals(orderCount, orderListToRemove.size(), "The BookOrders records should exist");
//		runTest_BookOrders_RemoveAsList(orderListToRemove);
//
//		// validate persistent state
//		List<Order> orderListRemaining = bookOrderLogHelper.getAllBookOrders();
//		Assert.isEmpty(orderListRemaining, "No BookOrders records should exist");
//			
//		log.info(testName+": done");
//		if (errorMessage != null)
//			fail(errorMessage);
//	}
//	
//	@Test
//	//@Ignore
//	@InSequence(value = 81)
//	public void runTest_BookOrders_RemoveAsList_utx_rollback() throws Exception {
//		String testName = "runTest_BookOrders_RemoveAsList_utx_rollback";
//		log.info(testName+": started");
//		
//		// prepare test context
//		bookOrderLogHelper.assureRemoveAll();
//		
//		// setup expectations
//		isUserTransaction = true;
//		
//		// execute test action
//		List<Order> orderListToRemove = bookOrderLogHelper.getAllBookOrders();
//		Assert.equals(orderCount, orderListToRemove.size(), "The BookOrders records should exist");
//		runTest_BookOrders_RemoveAsList(orderListToRemove);
//
//		// validate persistent state
//		List<Order> orderListRemaining = bookOrderLogHelper.getAllBookOrders();
//		Assert.isEmpty(orderListRemaining, "No BookOrders records should exist");
//			
//		log.info(testName+": done");
//		if (errorMessage != null)
//			fail(errorMessage);
//	}
//	
//	@Test
//	//@Ignore
//	@InSequence(value = 82)
//	@BytemanRule(name = "rule82", 
//			targetClass = "BookOrderLogManager", 
//			targetMethod = "removeFromBookOrders", 
//			targetLocation = "AT EXIT", 
//			action = "throw new org.aries.ApplicationFailure(\"error82\")")
//	public void runTest_BookOrders_RemoveAsList_manager_exception_rollback() throws Exception {
//		String testName = "runTest_BookOrders_RemoveAsList_manager_exception_rollback";
//		log.info(testName+": started");
//		
//		// prepare test context
//		setupByteman(testName);
//		bookOrderLogHelper.assureRemoveAll();
//		
//		// setup expectations
//		isRollbackExpected = true;
//		
//		try {
//			// execute test action
//			List<Order> orderListToRemove = bookOrderLogHelper.getAllBookOrders();
//			Assert.equals(orderCount, orderListToRemove.size(), "The BookOrders records should exist");
//			runTest_BookOrders_RemoveAsList(orderListToRemove);
//
//			// validate persistent state
//			List<Order> orderListRemaining = bookOrderLogHelper.getAllBookOrders();
//			Assert.equals(bookCount, orderListRemaining.size(), "The BookOrders records should still exist");
//			Bookshop2Fixture.assertSameOrder(orderListRemaining, orderList);
//			
//		} finally {
//			// cleanup test context
//			tearDownByteman(testName);
//		}
//		
//		log.info(testName+": done");
//		if (errorMessage != null)
//			fail(errorMessage);
//	}
//	
//	@Test
//	//@Ignore
//	@InSequence(value = 83)
//	@BytemanRule(name = "rule83", 
//			targetClass = "BookOrderLog", 
//			targetMethod = "removeFromBookOrders", 
//			targetLocation = "AT EXIT", 
//			action = "throw new org.aries.ApplicationFailure(\"error83\")")
//	public void runTest_BookOrders_RemoveAsList_interface_exception_rollback() throws Exception {
//		String testName = "runTest_BookOrders_RemoveAsList_interface_exception_rollback";
//		log.info(testName+": started");
//		
//		// prepare test context
//		setupByteman(testName);
//		bookOrderLogHelper.assureRemoveAll();
//		
//		// setup expectations
//		isRollbackExpected = true;
//		
//		try {
//			// execute test action
//			List<Order> orderListToRemove = bookOrderLogHelper.getAllBookOrders();
//			Assert.equals(orderCount, orderListToRemove.size(), "The BookOrders records should exist");
//			runTest_BookOrders_RemoveAsList(orderListToRemove);
//
//			// validate persistent state
//			List<Order> orderListRemaining = bookOrderLogHelper.getAllBookOrders();
//			Assert.equals(bookCount, orderListRemaining.size(), "The BookOrders records should still exist");
//			Bookshop2Fixture.assertSameOrder(orderListRemaining, orderList);
//			
//		} finally {
//			// cleanup test context
//			tearDownByteman(testName);
//		}
//		
//		log.info(testName+": done");
//		if (errorMessage != null)
//			fail(errorMessage);
//	}
//	
//	@Test
//	//@Ignore
//	@InSequence(value = 84)
//	@BytemanRule(name = "rule84", 
//			targetClass = "BookOrderLogRepositoryImpl", 
//			targetMethod = "removeBookOrdersRecords", 
//			targetLocation = "AT EXIT", 
//			action = "throw new org.aries.ApplicationFailure(\"error84\")")
//	public void runTest_BookOrders_RemoveAsList_repository_exception_rollback() throws Exception {
//		String testName = "runTest_BookOrders_RemoveAsList_repository_exception_rollback";
//		log.info(testName+": started");
//		
//		// prepare test context
//		setupByteman(testName);
//		bookOrderLogHelper.assureRemoveAll();
//		
//		// setup expectations
//		isRollbackExpected = true;
//		
//		try {
//			// execute test action
//			List<Order> orderListToRemove = bookOrderLogHelper.getAllBookOrders();
//			Assert.equals(orderCount, orderListToRemove.size(), "The BookOrders records should exist");
//			runTest_BookOrders_RemoveAsList(orderListToRemove);
//
//			// validate persistent state
//			List<Order> orderListRemaining = bookOrderLogHelper.getAllBookOrders();
//			Assert.equals(bookCount, orderListRemaining.size(), "The BookOrders records should still exist");
//			Bookshop2Fixture.assertSameOrder(orderListRemaining, orderList);
//			
//		} finally {
//			// cleanup test context
//			tearDownByteman(testName);
//		}
//		
//		log.info(testName+": done");
//		if (errorMessage != null)
//			fail(errorMessage);
//	}
	
	public void runTest_BookOrders_RemoveAsList(final Collection<Order> orderList) throws Exception {
		runTest(new Runnable() {
			public void run() {
				bookOrderLogManager.removeFromBookOrders(orderList);
			}
		});
	}
	
	public void runTest(Runnable runnable) throws Exception {
		if (isTransactional()) {
			beginTransaction();
		}
		
		//Transactional transactional = control.createMockTransactional(fixture);
		//TransactionParticipantManager.getInstance().enrollTransaction("", transactionId, bookOrderLogManager);

		try {
			runnable.run();
			if (isRollbackExpected) {
				if (isTransactional())
					clearTransaction();
					//rollbackTransaction();
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

	public void runTest2(Runnable runnable) throws Exception {
		try {
			runnable.run();
			if (isRollbackExpected) {
				errorMessage = "Exception should have been thrown";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
