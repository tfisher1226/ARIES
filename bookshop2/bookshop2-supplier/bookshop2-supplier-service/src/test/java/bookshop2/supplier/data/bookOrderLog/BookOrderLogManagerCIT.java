package bookshop2.supplier.data.bookOrderLog;

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
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import bookshop2.Order;
import bookshop2.supplier.SupplierTestEARBuilder;
import bookshop2.supplier.SupplierTestWARBuilder;
import bookshop2.util.Bookshop2Fixture;

import common.tx.state.AbstractStateManager;


@RunWith(Arquillian.class)
public class BookOrderLogManagerCIT extends AbstractDataUnitArquillionTest {
	
	@Inject
	private BookOrderLogManager bookOrderLogManager;

	@Inject
	private BookOrderLogHelper bookOrderLogHelper;
	
	
	public BookOrderLogManagerCIT() {
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
		return BookOrderLogManagerCIT.class;
	}
	
	@Override
	public String getTargetArchive() {
		return SupplierTestEARBuilder.NAME;
	}

	@Override
	public AbstractStateManager<?> getStateManager() {
		return bookOrderLogManager;
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
			initializeBookOrderLogHelper();
			clearBookOrderLogContext();
		}
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
		builder.addClass(BookOrderLogManagerCIT.class);
		WebArchive war = builder.create();
		return war;
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
	
	@Test
	//@Ignore
	@InSequence(value = 1)
	public void runTest_BookOrders_GetAllAsList_success() throws Exception {
		String testName = "runTest_BookOrders_GetAllAsList_success";
		log.info(testName+": started");
		
		// prepare test context
		bookOrderLogHelper.assureRemoveAll();
		
		
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
		bookOrderLogHelper.assureRemoveAll();
		
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
	public void runTest_BookOrders_GetAllAsList_tx_rollback() throws Exception {
		String testName = "runTest_BookOrders_GetAllAsList_tx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookOrderLogHelper.assureRemoveAll();
		
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
	public void runTest_BookOrders_GetAllAsList_utx_commit() throws Exception {
		String testName = "runTest_BookOrders_GetAllAsList_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookOrderLogHelper.assureRemoveAll();
		
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
	public void runTest_BookOrders_GetAllAsList_utx_rollback() throws Exception {
		String testName = "runTest_BookOrders_GetAllAsList_utx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookOrderLogHelper.assureRemoveAll();
		
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
			targetClass = "BookOrderLogManager", 
			targetMethod = "getAllBookOrders", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error6\")")
	public void runTest_BookOrders_GetAllAsList_manager_exception_rollback() throws Exception {
		String testName = "runTest_BookOrders_GetAllAsList_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookOrderLogHelper.assureRemoveAll();
		
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
			targetClass = "BookOrderLog", 
			targetMethod = "getAllBookOrders", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error7\")")
	public void runTest_BookOrders_GetAllAsList_interface_exception_rollback() throws Exception {
		String testName = "runTest_BookOrders_GetAllAsList_interface_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookOrderLogHelper.assureRemoveAll();
		
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
			targetClass = "BookOrderLogRepositoryImpl", 
			targetMethod = "getAllBookOrdersRecords", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error8\")")
	public void runTest_BookOrders_GetAllAsList_repository_exception_rollback() throws Exception {
		String testName = "runTest_BookOrders_GetAllAsList_repository_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookOrderLogHelper.assureRemoveAll();
		
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
	
	public void runTest_BookOrders_GetAllAsList() throws Exception {
		runTest(new Runnable() {
			public void run() {
				bookOrderLogManager.getAllBookOrders();
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 9)
	public void runTest_BookOrders_GetMatchingAsList_success() throws Exception {
		String testName = "runTest_BookOrders_GetMatchingAsList_success";
		log.info(testName+": started");
		
		// prepare test context
		bookOrderLogHelper.assureRemoveAll();
		
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 10)
	public void runTest_BookOrders_GetMatchingAsList_tx_commit() throws Exception {
		String testName = "runTest_BookOrders_GetMatchingAsList_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookOrderLogHelper.assureRemoveAll();
		
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
	@InSequence(value = 11)
	public void runTest_BookOrders_GetMatchingAsList_tx_rollback() throws Exception {
		String testName = "runTest_BookOrders_GetMatchingAsList_tx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookOrderLogHelper.assureRemoveAll();
		
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
	@InSequence(value = 12)
	public void runTest_BookOrders_GetMatchingAsList_utx_commit() throws Exception {
		String testName = "runTest_BookOrders_GetMatchingAsList_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookOrderLogHelper.assureRemoveAll();
		
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
	@InSequence(value = 13)
	public void runTest_BookOrders_GetMatchingAsList_utx_rollback() throws Exception {
		String testName = "runTest_BookOrders_GetMatchingAsList_utx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookOrderLogHelper.assureRemoveAll();
		
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
	@InSequence(value = 14)
	@BytemanRule(name = "rule14", 
			targetClass = "BookOrderLogManager", 
			targetMethod = "getMatchingBookOrders", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error14\")")
	public void runTest_BookOrders_GetMatchingAsList_manager_exception_rollback() throws Exception {
		String testName = "runTest_BookOrders_GetMatchingAsList_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookOrderLogHelper.assureRemoveAll();
		
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
	@InSequence(value = 15)
	@BytemanRule(name = "rule15", 
			targetClass = "BookOrderLog", 
			targetMethod = "getMatchingBookOrders", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error15\")")
	public void runTest_BookOrders_GetMatchingAsList_interface_exception_rollback() throws Exception {
		String testName = "runTest_BookOrders_GetMatchingAsList_interface_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookOrderLogHelper.assureRemoveAll();
		
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
	@InSequence(value = 16)
	@BytemanRule(name = "rule16", 
			targetClass = "BookOrderLogRepositoryImpl", 
			targetMethod = "getMatchingBookOrdersRecords", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error16\")")
	public void runTest_BookOrders_GetMatchingAsList_repository_exception_rollback() throws Exception {
		String testName = "runTest_BookOrders_GetMatchingAsList_repository_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookOrderLogHelper.assureRemoveAll();
		
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
	
	public void runTest_BookOrders_GetMatchingAsList(final Collection<Order> orderList) throws Exception {
		runTest(new Runnable() {
			public void run() {
				bookOrderLogManager.getMatchingBookOrders(orderList);
			}
		});
	}
	
	@Test
	//@Ignore
	@InSequence(value = 17)
	public void runTest_BookOrders_AddAsItem_success() throws Exception {
		String testName = "runTest_BookOrders_AddAsItem_success";
		log.info(testName+": started");
		
		// prepare test context
		bookOrderLogHelper.assureRemoveAll();
		
		// prepare test data
		Order orderToAdd = Bookshop2Fixture.create_Order();
		
		// execute test action
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
	@InSequence(value = 18)
	public void runTest_BookOrders_AddAsItem_tx_commit() throws Exception {
		String testName = "runTest_BookOrders_AddAsItem_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookOrderLogHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();
		
		// prepare test data
		Order orderToAdd = Bookshop2Fixture.create_Order();
		
		// execute test action
		runTest_BookOrders_AddAsItem(orderToAdd);
		
		// commit transaction
		commitJTATransaction();

		// validate persistent state
		List<Order> orderList = bookOrderLogHelper.getAllBookOrders();
		Assert.equals(1, orderList.size(), "Only 1 BookOrders record should exist");
		Bookshop2Fixture.assertSameOrder(orderToAdd, orderList.get(0));
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 19)
	public void runTest_BookOrders_AddAsItem_tx_rollback() throws Exception {
		String testName = "runTest_BookOrders_AddAsItem_tx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookOrderLogHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();
		
		// prepare test data
		Order orderToAdd = Bookshop2Fixture.create_Order();
		
		// execute test action
		runTest_BookOrders_AddAsItem(orderToAdd);
		
		// rollback transaction
		rollbackJTATransaction();

		// validate persistent state
		List<Order> orderList = bookOrderLogHelper.getAllBookOrders();
		Assert.isEmpty(orderList, "No BookOrders records should exist");
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 20)
	public void runTest_BookOrders_AddAsItem_utx_commit() throws Exception {
		String testName = "runTest_BookOrders_AddAsItem_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookOrderLogHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();
		
		// prepare test data
		Order orderToAdd = Bookshop2Fixture.create_Order();
		
		// execute test action
		runTest_BookOrders_AddAsItem(orderToAdd);
		
		// commit transaction
		commitUserTransaction();

		// validate persistent state
		List<Order> orderList = bookOrderLogHelper.getAllBookOrders();
		Assert.equals(1, orderList.size(), "Only 1 BookOrders record should exist");
		Bookshop2Fixture.assertSameOrder(orderToAdd, orderList.get(0));
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 21)
	public void runTest_BookOrders_AddAsItem_utx_rollback() throws Exception {
		String testName = "runTest_BookOrders_AddAsItem_utx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookOrderLogHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();
		
		// prepare test data
		Order orderToAdd = Bookshop2Fixture.create_Order();
		
		// execute test action
		runTest_BookOrders_AddAsItem(orderToAdd);
		
		// rollback transaction
		rollbackUserTransaction();

		// validate persistent state
		List<Order> orderList = bookOrderLogHelper.getAllBookOrders();
		Assert.isEmpty(orderList, "No BookOrders records should exist");
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 22)
	@BytemanRule(name = "rule22", 
			targetClass = "BookOrderLogManager", 
			targetMethod = "addToBookOrders", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error22\")")
	public void runTest_BookOrders_AddAsItem_manager_exception_rollback() throws Exception {
		String testName = "runTest_BookOrders_AddAsItem_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookOrderLogHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;
		
		// prepare test data
		Order orderToAdd = Bookshop2Fixture.create_Order();
		
		try {
			// execute test action
			runTest_BookOrders_AddAsItem(orderToAdd);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error22");
			
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
	@InSequence(value = 23)
	@BytemanRule(name = "rule23", 
			targetClass = "BookOrderLog", 
			targetMethod = "addToBookOrders", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error23\")")
	public void runTest_BookOrders_AddAsItem_interface_exception_rollback() throws Exception {
		String testName = "runTest_BookOrders_AddAsItem_interface_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookOrderLogHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		Order orderToAdd = Bookshop2Fixture.create_Order();
		
		try {
			// execute test action
			runTest_BookOrders_AddAsItem(orderToAdd);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error23");
			
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
	@InSequence(value = 24)
	@BytemanRule(name = "rule24", 
			targetClass = "BookOrderLogRepositoryImpl", 
			targetMethod = "addBookOrdersRecord", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error24\")")
	public void runTest_BookOrders_AddAsItem_repository_exception_rollback() throws Exception {
		String testName = "runTest_BookOrders_AddAsItem_repository_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookOrderLogHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		Order orderToAdd = Bookshop2Fixture.create_Order();
		
		try {
			// execute test action
			runTest_BookOrders_AddAsItem(orderToAdd);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error24");
			
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
	@InSequence(value = 25)
	public void runTest_BookOrders_AddAsList_success() throws Exception {
		String testName = "runTest_BookOrders_AddAsList_success";
		log.info(testName+": started");
		
		// prepare test context
		bookOrderLogHelper.assureRemoveAll();
		
		// prepare test data
		List<Order> orderListToAdd = Bookshop2Fixture.createList_Order();
		
		// execute test action
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
	@InSequence(value = 26)
	public void runTest_BookOrders_AddAsList_tx_commit() throws Exception {
		String testName = "runTest_BookOrders_AddAsList_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookOrderLogHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();
		
		// prepare test data
		List<Order> orderListToAdd = Bookshop2Fixture.createList_Order();
		
		// execute test action
		runTest_BookOrders_AddAsList(orderListToAdd);
		
		// commit transaction
		commitJTATransaction();

		// validate persistent state
		List<Order> orderList = bookOrderLogHelper.getAllBookOrders();
		Bookshop2Fixture.assertSameOrder(orderListToAdd, orderList);
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 27)
	public void runTest_BookOrders_AddAsList_tx_rollback() throws Exception {
		String testName = "runTest_BookOrders_AddAsList_tx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookOrderLogHelper.assureRemoveAll();
		
		// begin transaction
		beginJTATransaction();
		
		// prepare test data
		List<Order> orderListToAdd = Bookshop2Fixture.createList_Order();
		
		// execute test action
		runTest_BookOrders_AddAsList(orderListToAdd);
		
		// rollback transaction
		rollbackJTATransaction();

		// validate persistent state
		List<Order> orderList = bookOrderLogHelper.getAllBookOrders();
		Assert.isEmpty(orderList, "No BookOrders records should exist");
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 28)
	public void runTest_BookOrders_AddAsList_utx_commit() throws Exception {
		String testName = "runTest_BookOrders_AddAsList_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookOrderLogHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();
		
		// prepare test data
		List<Order> orderListToAdd = Bookshop2Fixture.createList_Order();
		
		// execute test action
		runTest_BookOrders_AddAsList(orderListToAdd);
		
		// commit transaction
		commitUserTransaction();

		// validate persistent state
		List<Order> orderList = bookOrderLogHelper.getAllBookOrders();
		Bookshop2Fixture.assertSameOrder(orderListToAdd, orderList);
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 29)
	public void runTest_BookOrders_AddAsList_utx_rollback() throws Exception {
		String testName = "runTest_BookOrders_AddAsList_utx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookOrderLogHelper.assureRemoveAll();
		
		// begin transaction
		beginUserTransaction();
		
		// prepare test data
		List<Order> orderListToAdd = Bookshop2Fixture.createList_Order();
		
		// execute test action
		runTest_BookOrders_AddAsList(orderListToAdd);
	
		// rollback transaction
		rollbackUserTransaction();
	
		// validate persistent state
		List<Order> orderList = bookOrderLogHelper.getAllBookOrders();
		Assert.isEmpty(orderList, "No BookOrders records should exist");
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 30)
	@BytemanRule(name = "rule30", 
			targetClass = "BookOrderLogManager",
			targetMethod = "addToBookOrders",
			targetLocation = "AT EXIT",
			action = "throw new org.aries.ApplicationFailure(\"error30\")")
	public void runTest_BookOrders_AddAsList_manager_exception_rollback() throws Exception {
		String testName = "runTest_BookOrders_AddAsList_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookOrderLogHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		List<Order> orderListToAdd = Bookshop2Fixture.createList_Order();
		
		try {
			// execute test action
			runTest_BookOrders_AddAsList(orderListToAdd);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error30");
			
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
	@InSequence(value = 31)
	@BytemanRule(name = "rule31", 
			targetClass = "BookOrderLog", 
			targetMethod = "addToBookOrders", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error31\")")
	public void runTest_BookOrders_AddAsList_interface_exception_rollback() throws Exception {
		String testName = "runTest_BookOrders_AddAsList_interface_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookOrderLogHelper.assureRemoveAll();

		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		List<Order> orderListToAdd = Bookshop2Fixture.createList_Order();

		try {
			// execute test action
			runTest_BookOrders_AddAsList(orderListToAdd);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error31");

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
	@InSequence(value = 32)
	@BytemanRule(name = "rule32", 
			targetClass = "BookOrderLogRepositoryImpl",
			targetMethod = "addBookOrdersRecords",
			targetLocation = "AT EXIT",
			action = "throw new org.aries.ApplicationFailure(\"error32\")")
	public void runTest_BookOrders_AddAsList_repository_exception_rollback() throws Exception {
		String testName = "runTest_BookOrders_AddAsList_repository_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookOrderLogHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		List<Order> orderListToAdd = Bookshop2Fixture.createList_Order();
		
		try {
			// execute test action
			runTest_BookOrders_AddAsList(orderListToAdd);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error32");

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
	@InSequence(value = 33)
	public void runTest_BookOrders_RemoveAll_success() throws Exception {
		String testName = "runTest_BookOrders_RemoveAll_success";
		log.info(testName+": started");
		
		// prepare test context
		bookOrderLogHelper.assureRemoveAll();
		
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 34)
	public void runTest_BookOrders_RemoveAll_tx_commit() throws Exception {
		String testName = "runTest_BookOrders_RemoveAll_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookOrderLogHelper.assureRemoveAll();
		
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
	public void runTest_BookOrders_RemoveAll_tx_rollback() throws Exception {
		String testName = "runTest_BookOrders_RemoveAll_tx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookOrderLogHelper.assureRemoveAll();
		
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
	public void runTest_BookOrders_RemoveAll_utx_commit() throws Exception {
		String testName = "runTest_BookOrders_RemoveAll_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookOrderLogHelper.assureRemoveAll();
		
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
	public void runTest_BookOrders_RemoveAll_utx_rollback() throws Exception {
		String testName = "runTest_BookOrders_RemoveAll_utx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookOrderLogHelper.assureRemoveAll();
		
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
			targetClass = "BookOrderLogManager", 
			targetMethod = "removeAllBookOrders", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error38\")")
	public void runTest_BookOrders_RemoveAll_manager_exception_rollback() throws Exception {
		String testName = "runTest_BookOrders_RemoveAll_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookOrderLogHelper.assureRemoveAll();
		
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
			targetClass = "BookOrderLog", 
			targetMethod = "removeAllBookOrders", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error39\")")
	public void runTest_BookOrders_RemoveAll_interface_exception_rollback() throws Exception {
		String testName = "runTest_BookOrders_RemoveAll_interface_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookOrderLogHelper.assureRemoveAll();
		
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
			targetClass = "BookOrderLogRepositoryImpl", 
			targetMethod = "removeAllBookOrdersRecords", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error40\")")
	public void runTest_BookOrders_RemoveAll_repository_exception_rollback() throws Exception {
		String testName = "runTest_BookOrders_RemoveAll_repository_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookOrderLogHelper.assureRemoveAll();
		
		// setup expectations
		isExceptionExpected = true;
		
		try {
			//fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error40");

		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
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
	@InSequence(value = 41)
	public void runTest_BookOrders_RemoveAsItem_success() throws Exception {
		String testName = "runTest_BookOrders_RemoveAsItem_success";
		log.info(testName+": started");
		
		// prepare test context
		bookOrderLogHelper.assureRemoveAll();
		Long id = bookOrderLogHelper.addBookOrders();
		
		// prepare test data
		Order orderToRemove = bookOrderLogHelper.getBookOrdersById(id);
		Assert.notNull(orderToRemove, "The BookOrders record should exist");
		
		// execute test action
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
	@InSequence(value = 42)
	public void runTest_BookOrders_RemoveAsItem_tx_commit() throws Exception {
		String testName = "runTest_BookOrders_RemoveAsItem_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookOrderLogHelper.assureRemoveAll();
		Long id = bookOrderLogHelper.addBookOrders();
		
		// begin transaction
		beginJTATransaction();
		
		// prepare test data
		Order orderToRemove = bookOrderLogHelper.getBookOrdersById(id);
		Assert.notNull(orderToRemove, "The BookOrders record should exist");
		
		// execute test action
		runTest_BookOrders_RemoveAsItem(orderToRemove);
		
		// commit transaction
		commitJTATransaction();

		// validate persistent state
		Order orderRemoved = bookOrderLogHelper.getBookOrdersById(id);
		Assert.isNull(orderRemoved, "The BookOrders record should be removed");
		List<Order> orderListRemaining = bookOrderLogHelper.getAllBookOrders();
		Assert.isEmpty(orderListRemaining, "No BookOrders records should exist");
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 43)
	public void runTest_BookOrders_RemoveAsItem_tx_rollback() throws Exception {
		String testName = "runTest_BookOrders_RemoveAsItem_tx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookOrderLogHelper.assureRemoveAll();
		Long id = bookOrderLogHelper.addBookOrders();
		
		// begin transaction
		beginJTATransaction();
		
		// prepare test data
		Order orderToRemove = bookOrderLogHelper.getBookOrdersById(id);
		Assert.notNull(orderToRemove, "The BookOrders record should exist");
		
		// execute test action
		runTest_BookOrders_RemoveAsItem(orderToRemove);
		
		// rollback transaction
		rollbackJTATransaction();

		// validate persistent state
		Order orderNotRemoved = bookOrderLogHelper.getBookOrdersById(id);
		Assert.notNull(orderNotRemoved, "The BookOrders record should still exist");
		List<Order> orderListRemaining = bookOrderLogHelper.getAllBookOrders();
		Assert.equals(1, orderListRemaining.size(), "Only 1 BookOrders record should exist");
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 44)
	public void runTest_BookOrders_RemoveAsItem_utx_commit() throws Exception {
		String testName = "runTest_BookOrders_RemoveAsItem_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		bookOrderLogHelper.assureRemoveAll();
		Long id = bookOrderLogHelper.addBookOrders();
		
		// begin transaction
		beginUserTransaction();
		
		// prepare test data
		Order orderToRemove = bookOrderLogHelper.getBookOrdersById(id);
		Assert.notNull(orderToRemove, "The BookOrders record should exist");
		
		// execute test action
		runTest_BookOrders_RemoveAsItem(orderToRemove);

		// commit transaction
		commitUserTransaction();

		// validate persistent state
		Order orderRemoved = bookOrderLogHelper.getBookOrdersById(id);
		Assert.isNull(orderRemoved, "The BookOrders record should be removed");
		List<Order> orderListRemaining = bookOrderLogHelper.getAllBookOrders();
		Assert.isEmpty(orderListRemaining, "No BookOrders records should exist");
		clearTransaction();
			
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 45)
	public void runTest_BookOrders_RemoveAsItem_utx_rollback() throws Exception {
		String testName = "runTest_BookOrders_RemoveAsItem_utx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		bookOrderLogHelper.assureRemoveAll();
		Long id = bookOrderLogHelper.addBookOrders();

		// begin transaction
		beginUserTransaction();

		// prepare test data
		Order orderToRemove = bookOrderLogHelper.getBookOrdersById(id);
		Assert.notNull(orderToRemove, "The BookOrders record should exist");
		
		// execute test action
		runTest_BookOrders_RemoveAsItem(orderToRemove);
	
		// rollback transaction
		rollbackUserTransaction();
	
		// validate persistent state
		Order orderNotRemoved = bookOrderLogHelper.getBookOrdersById(id);
		Assert.notNull(orderNotRemoved, "The BookOrders record should still exist");
		List<Order> orderListRemaining = bookOrderLogHelper.getAllBookOrders();
		Assert.equals(1, orderListRemaining.size(), "Only 1 BookOrders record should exist");
		clearTransaction();

		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 46)
	@BytemanRule(name = "rule46", 
			targetClass = "BookOrderLogManager", 
			targetMethod = "removeFromBookOrders", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error46\")")
	public void runTest_BookOrders_RemoveAsItem_manager_exception_rollback() throws Exception {
		String testName = "runTest_BookOrders_RemoveAsItem_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookOrderLogHelper.assureRemoveAll();
		Long id = bookOrderLogHelper.addBookOrders();
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		Order orderToRemove = bookOrderLogHelper.getBookOrdersById(id);
		Assert.notNull(orderToRemove, "The BookOrders record should exist");
		
		try {
			// execute test action
			runTest_BookOrders_RemoveAsItem(orderToRemove);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error46");
			
			// validate persistent state
			Order orderNotRemoved = bookOrderLogHelper.getBookOrdersById(id);
			Assert.notNull(orderNotRemoved, "The BookOrders record should still exist");
			List<Order> orderList = bookOrderLogHelper.getAllBookOrders();
			Assert.equals(1, orderList.size(), "Only 1 BookOrders record should exist");

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
			targetClass = "BookOrderLog", 
			targetMethod = "removeFromBookOrders", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error47\")")
	public void runTest_BookOrders_RemoveAsItem_interface_exception_rollback() throws Exception {
		String testName = "runTest_BookOrders_RemoveAsItem_interface_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookOrderLogHelper.assureRemoveAll();
		Long id = bookOrderLogHelper.addBookOrders();
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		Order orderToRemove = bookOrderLogHelper.getBookOrdersById(id);
		Assert.notNull(orderToRemove, "The BookOrders record should exist");
		
		try {
			// execute test action
			runTest_BookOrders_RemoveAsItem(orderToRemove);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error47");

			// validate persistent state
			Order orderNotRemoved = bookOrderLogHelper.getBookOrdersById(id);
			Assert.notNull(orderNotRemoved, "The BookOrders record should still exist");
			List<Order> orderList = bookOrderLogHelper.getAllBookOrders();
			Assert.equals(1, orderList.size(), "Only 1 BookOrders record should exist");

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
			targetClass = "BookOrderLogRepositoryImpl", 
			targetMethod = "removeBookOrdersRecord", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error48\")")
	public void runTest_BookOrders_RemoveAsItem_repository_exception_rollback() throws Exception {
		String testName = "runTest_BookOrders_RemoveAsItem_repository_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		bookOrderLogHelper.assureRemoveAll();
		Long id = bookOrderLogHelper.addBookOrders();
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		Order orderToRemove = bookOrderLogHelper.getBookOrdersById(id);
		Assert.notNull(orderToRemove, "The BookOrders record should exist");
	
		try {
			// execute test action
			runTest_BookOrders_RemoveAsItem(orderToRemove);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error48");

			// validate persistent state
			Order orderNotRemoved = bookOrderLogHelper.getBookOrdersById(id);
			Assert.notNull(orderNotRemoved, "The BookOrders record should still exist");
			List<Order> orderList = bookOrderLogHelper.getAllBookOrders();
			Assert.equals(1, orderList.size(), "Only 1 BookOrders record should exist");

		} finally {
			// cleanup test context
			tearDownByteman(testName);
		}
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runTest_BookOrders_RemoveAsItem(final Order order) throws Exception {
		runTest(new Runnable() {
			public void run() {
			bookOrderLogManager.removeFromBookOrders(order);
			}
		});
	}
			
	@Test
	//@Ignore
	@InSequence(value = 49)
	public void runTest_BookOrders_RemoveAsList_success() throws Exception {
		String testName = "runTest_BookOrders_RemoveAsList_success";
		log.info(testName+": started");
		
		// prepare test context
		int bookOrdersCount = 2;
		bookOrderLogHelper.assureRemoveAll();
		bookOrderLogHelper.addBookOrders(bookOrdersCount);
		
		// prepare test data
		List<Order> orderListToRemove = bookOrderLogHelper.getAllBookOrders();
		Assert.equals(bookOrdersCount, orderListToRemove.size(), "The BookOrders records should exist");
		
		// execute test action
		runTest_BookOrders_RemoveAsList(orderListToRemove);

		// validate persistent state
		List<Order> orderListRemaining = bookOrderLogHelper.getAllBookOrders();
		Assert.isEmpty(orderListRemaining, "No BookOrders records should exist");
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 50)
	public void runTest_BookOrders_RemoveAsList_tx_commit() throws Exception {
		String testName = "runTest_BookOrders_RemoveAsList_tx_commit";
		log.info(testName+": started");
		
		// prepare test context
		int bookOrdersCount = 2;
		bookOrderLogHelper.assureRemoveAll();
		bookOrderLogHelper.addBookOrders(bookOrdersCount);
		
		// begin transaction
		beginJTATransaction();
		
		// prepare test data
		List<Order> orderListToRemove = bookOrderLogHelper.getAllBookOrders();
		Assert.equals(bookOrdersCount, orderListToRemove.size(), "The BookOrders records should exist");
		
		// execute test action
		runTest_BookOrders_RemoveAsList(orderListToRemove);
		
		// commit transaction
		commitJTATransaction();

		// validate persistent state
		List<Order> orderListRemaining = bookOrderLogHelper.getAllBookOrders();
		Assert.isEmpty(orderListRemaining, "No BookOrders records should exist");
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 51)
	public void runTest_BookOrders_RemoveAsList_tx_rollback() throws Exception {
		String testName = "runTest_BookOrders_RemoveAsList_tx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		int bookOrdersCount = 2;
		bookOrderLogHelper.assureRemoveAll();
		bookOrderLogHelper.addBookOrders(bookOrdersCount);
		
		// begin transaction
		beginJTATransaction();
		
		// prepare test data
		List<Order> orderListToRemove = bookOrderLogHelper.getAllBookOrders();
		Assert.equals(bookOrdersCount, orderListToRemove.size(), "The BookOrders records should exist");
		
		// execute test action
		runTest_BookOrders_RemoveAsList(orderListToRemove);
		
		// rollback transaction
		rollbackJTATransaction();

		// validate persistent state
		List<Order> orderListRemaining = bookOrderLogHelper.getAllBookOrders();
		Assert.equals(bookOrdersCount, orderListRemaining.size(), "The BookOrders records should still exist");
		Bookshop2Fixture.assertSameOrder(orderListRemaining, orderListToRemove);
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 52)
	public void runTest_BookOrders_RemoveAsList_utx_commit() throws Exception {
		String testName = "runTest_BookOrders_RemoveAsList_utx_commit";
		log.info(testName+": started");
		
		// prepare test context
		int bookOrdersCount = 2;
		bookOrderLogHelper.assureRemoveAll();
		bookOrderLogHelper.addBookOrders(bookOrdersCount);
		
		// begin transaction
		beginUserTransaction();
		
		// prepare test data
		List<Order> orderListToRemove = bookOrderLogHelper.getAllBookOrders();
		Assert.equals(bookOrdersCount, orderListToRemove.size(), "The BookOrders records should exist");
		
		// execute test action
		runTest_BookOrders_RemoveAsList(orderListToRemove);
		
		// commit transaction
		commitUserTransaction();

		// validate persistent state
		List<Order> orderListRemaining = bookOrderLogHelper.getAllBookOrders();
		Assert.isEmpty(orderListRemaining, "No BookOrders records should exist");
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 53)
	public void runTest_BookOrders_RemoveAsList_utx_rollback() throws Exception {
		String testName = "runTest_BookOrders_RemoveAsList_utx_rollback";
		log.info(testName+": started");
		
		// prepare test context
		int bookOrdersCount = 2;
		bookOrderLogHelper.assureRemoveAll();
		bookOrderLogHelper.addBookOrders(bookOrdersCount);
		
		// begin transaction
		beginUserTransaction();
		
		// prepare test data
		List<Order> orderListToRemove = bookOrderLogHelper.getAllBookOrders();
		Assert.equals(bookOrdersCount, orderListToRemove.size(), "The BookOrders records should exist");
		
		// execute test action
		runTest_BookOrders_RemoveAsList(orderListToRemove);
		
		// rollback transaction
		rollbackUserTransaction();

		// validate persistent state
		List<Order> orderListRemaining = bookOrderLogHelper.getAllBookOrders();
		Assert.equals(bookOrdersCount, orderListRemaining.size(), "The BookOrders records should still exist");
		Bookshop2Fixture.assertSameOrder(orderListRemaining, orderListToRemove);
		clearTransaction();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 54)
	@BytemanRule(name = "rule54", 
			targetClass = "BookOrderLogManager", 
			targetMethod = "removeFromBookOrders", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error54\")")
	public void runTest_BookOrders_RemoveAsList_manager_exception_rollback() throws Exception {
		String testName = "runTest_BookOrders_RemoveAsList_manager_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		int bookOrdersCount = 2;
		bookOrderLogHelper.assureRemoveAll();
		bookOrderLogHelper.addBookOrders(bookOrdersCount);
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		List<Order> orderListToRemove = bookOrderLogHelper.getAllBookOrders();
		Assert.equals(bookOrdersCount, orderListToRemove.size(), "The BookOrders records should exist");
		
		try {
			// execute test action
			runTest_BookOrders_RemoveAsList(orderListToRemove);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error54");

			// validate persistent state
			List<Order> orderListRemaining = bookOrderLogHelper.getAllBookOrders();
			Assert.equals(bookOrdersCount, orderListRemaining.size(), "The BookOrders records should still exist");
			Bookshop2Fixture.assertSameOrder(orderListRemaining, orderListToRemove);

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
			targetClass = "BookOrderLog", 
			targetMethod = "removeFromBookOrders", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error55\")")
	public void runTest_BookOrders_RemoveAsList_interface_exception_rollback() throws Exception {
		String testName = "runTest_BookOrders_RemoveAsList_interface_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		int bookOrdersCount = 2;
		bookOrderLogHelper.assureRemoveAll();
		bookOrderLogHelper.addBookOrders(bookOrdersCount);
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		List<Order> orderListToRemove = bookOrderLogHelper.getAllBookOrders();
		Assert.equals(bookOrdersCount, orderListToRemove.size(), "The BookOrders records should exist");
		
		try {
			// execute test action
			runTest_BookOrders_RemoveAsList(orderListToRemove);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error55");

			// validate persistent state
			List<Order> orderListRemaining = bookOrderLogHelper.getAllBookOrders();
			Assert.equals(bookOrdersCount, orderListRemaining.size(), "The BookOrders records should still exist");
			Bookshop2Fixture.assertSameOrder(orderListRemaining, orderListToRemove);

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
			targetClass = "BookOrderLogRepositoryImpl", 
			targetMethod = "removeBookOrdersRecords", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"error56\")")
	public void runTest_BookOrders_RemoveAsList_repository_exception_rollback() throws Exception {
		String testName = "runTest_BookOrders_RemoveAsList_repository_exception_rollback";
		log.info(testName+": started");
		
		// prepare test context
		setupByteman(testName);
		int bookOrdersCount = 2;
		bookOrderLogHelper.assureRemoveAll();
		bookOrderLogHelper.addBookOrders(bookOrdersCount);
		
		// setup expectations
		isExceptionExpected = true;

		// prepare test data
		List<Order> orderListToRemove = bookOrderLogHelper.getAllBookOrders();
		Assert.equals(bookOrdersCount, orderListToRemove.size(), "The BookOrders records should exist");
		
		try {
			// execute test action
			runTest_BookOrders_RemoveAsList(orderListToRemove);
			fail("Exception should have been thrown");

		} catch (Exception e) {
			Assert.exception(e, org.aries.ApplicationFailure.class);
			Assert.exception(e, "error56");

			// validate persistent state
			List<Order> orderListRemaining = bookOrderLogHelper.getAllBookOrders();
			Assert.equals(bookOrdersCount, orderListRemaining.size(), "The BookOrders records should still exist");
			Bookshop2Fixture.assertSameOrder(orderListRemaining, orderListToRemove);

		} finally {
			// cleanup test context
			tearDownByteman(testName);
	}
	
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runTest_BookOrders_RemoveAsList(final Collection<Order> orderList) throws Exception {
		runTest(new Runnable() {
			public void run() {
				bookOrderLogManager.removeFromBookOrders(orderList);
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

}
