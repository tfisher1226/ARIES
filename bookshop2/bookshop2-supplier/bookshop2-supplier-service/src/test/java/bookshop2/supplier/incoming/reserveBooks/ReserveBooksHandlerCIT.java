package bookshop2.supplier.incoming.reserveBooks;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.management.ObjectName;

import nam.model.Endpoint;
import nam.model.TransactionContext;
import nam.model.TransactionIsolationLevel;

import org.aries.Assert;
import org.aries.tx.AbstractArquillianTest;
import org.aries.tx.AbstractServiceSideArquillionTest;
import org.aries.tx.BytemanRule;
import org.aries.tx.CacheModuleTestControl;
import org.aries.tx.DataModuleTestControl;
import org.aries.tx.ServiceModuleTestControl;
import org.aries.tx.TransactionRegistryManagerMBean;
import org.aries.tx.TransactionTestControl;
import org.aries.util.concurrent.FutureResult;
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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import tx.manager.registry.ServiceRegistry;
import bookshop2.Book;
import bookshop2.ReservationAbortedException;
import bookshop2.ReservationRequestMessage;
import bookshop2.supplier.SupplierTestEARBuilder;
import bookshop2.supplier.SupplierTestWARBuilder;
import bookshop2.supplier.data.bookInventory.BookInventoryHelper;
import bookshop2.supplier.data.bookInventory.BookInventoryManager;
import bookshop2.supplier.data.bookInventory.BookInventoryManagerMBean;
import bookshop2.supplier.data.supplierOrderCache.SupplierOrderCacheHelper;
import bookshop2.supplier.data.supplierOrderCache.SupplierOrderCacheProxy;
import bookshop2.util.Bookshop2Fixture;
import common.tx.CoordinationConstants;
import common.tx.state.AbstractStateManager;


@RunWith(Arquillian.class)
public class ReserveBooksHandlerCIT extends AbstractServiceSideArquillionTest {

	@Inject
	private SupplierOrderCacheHelper supplierOrderCacheHelper;

	//@Inject
	//private SupplierOrderCacheProxy supplierOrderCacheProxy;
	
	private CacheModuleTestControl supplierOrderCacheControl;

	@Inject
	private BookInventoryHelper bookInventoryHelper;

//	@Inject
//	private BookInventoryProxy bookInventoryProxy;

//	private DataModuleTestControl bookInventoryControl;

	@Inject
	private ReserveBooksHandler reserveBooksHandler;

	private ReservationRequestMessage reservationRequestMessage;

	
	@Override
	public String getServerName() {
		return "hornetQ01_local";
	}
	
	@Override
	public String getDomainId() {
		return "bookshop2.supplier";
	}

	//@Override
	public String getServiceId() {
		return "Supplier_ReserveBooks";
	}

	@Override
	public Class<?> getTestClass() {
		return ReserveBooksHandlerCIT.class;
	}
	
	@Override
	public String getTargetArchive() {
		return SupplierTestEARBuilder.NAME;
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
			//createBookInventoryControl();
			initializeBookInventoryHelper();
			createSupplierOrderCacheControl();
			createSupplierOrderCacheHelper();
			create_ReservationRequestMessage();
			clearBookInventoryContext();
		}
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
		reserveBooksHandler = null;
		reservationRequestMessage = null;
		transactionTestControl.tearDown();
		//bookInventoryControl.tearDown();
		supplierOrderCacheControl.tearDown();
	}
	
//	@TargetsContainer("hornetQ01_local")
//	@Deployment(name = "txManagerEAR", order = 1)
//	public static EnterpriseArchive createTXManagerEAR() {
//		TXManagerTestEARBuilder builder = new TXManagerTestEARBuilder();
//		return builder.createEAR();
//	}
	
	@TargetsContainer("hornetQ01_local")
	@Deployment(name = "supplierEAR", order = 2)
	public static EnterpriseArchive createTestEAR() {
		SupplierTestEARBuilder builder = new SupplierTestEARBuilder();
		builder.addAsModule(createTestWAR(), "supplier");
		EnterpriseArchive ear = builder.createEAR();
		return ear;
	}
	
	public static WebArchive createTestWAR() {
		SupplierTestWARBuilder builder = new SupplierTestWARBuilder("test.war");
		builder.addClass(ReserveBooksHandlerCIT.class);
		WebArchive war = builder.create();
		return war;
	}
	
	protected void createTransactionControl() throws Exception {
		transactionTestControl = new TransactionTestControl();
		transactionTestControl.assureTransactionManager();
	}
	
	public void createSupplierOrderCacheHelper() throws Exception {
		//supplierOrderCacheHelper = new SupplierOrderCacheHelper();
		//supplierOrderCacheHelper.setProxy(createSupplierOrderCacheProxy());
		supplierOrderCacheHelper.initialize(supplierOrderCacheControl);
	}
	
//	public SupplierOrderCacheProxy createSupplierOrderCacheProxy() throws Exception {
//		supplierOrderCacheProxy = new SupplierOrderCacheProxy();
//		supplierOrderCacheProxy.setJmxManager(jmxManager);
//		return supplierOrderCacheProxy;
//	}

	protected CacheModuleTestControl createSupplierOrderCacheControl() throws Exception {
		supplierOrderCacheControl = new CacheModuleTestControl(transactionTestControl);
		supplierOrderCacheControl.setupCacheLayer();
		return supplierOrderCacheControl;
	}
	
//	protected void createBookInventoryHelper() throws Exception {
//		//bookInventoryHelper = new BookInventoryHelper();
//		//bookInventoryHelper.setJmxProxy(createBookInventoryProxy());
//		//bookInventoryHelper.initializeAsClient(createBookInventoryControl());
//		bookInventoryHelper.initialize(bookInventoryControl);
//	}

	protected void initializeBookInventoryHelper() throws Exception {
		bookInventoryHelper.setJmxManager(jmxManager);
		bookInventoryHelper.initialize(transactionTestControl);
	}
	
//	protected BookInventoryProxy createBookInventoryProxy() throws Exception {
//		bookInventoryProxy = new BookInventoryProxy();
//		bookInventoryProxy.setJmxManager(jmxManager);
//		return bookInventoryProxy;
//	}

//	protected DataModuleTestControl createBookInventoryControl() throws Exception {
//		bookInventoryControl = new ServiceModuleTestControl(transactionTestControl);
//		bookInventoryControl = new DataModuleTestControl(transactionTestControl);
//		bookInventoryControl.setDatabaseName("bookshop2_supplier_db");
//		bookInventoryControl.setDataSourceName("bookshop2_supplier_ds");
//		bookInventoryControl.setPersistenceUnitName("bookInventory");
//		bookInventoryControl.setupDataLayer();
//		return bookInventoryControl;
//	}

	public void clearBookInventoryContext() throws Exception {
		jmxProxy.call(BookInventoryManagerMBean.MBEAN_NAME, "clearContext");
	}
	

	public void create_ReservationRequestMessage() {
		reservationRequestMessage = Bookshop2Fixture.create_ReservationRequestMessage();
		reservationRequestMessage.setCorrelationId(correlationId);
		reservationRequestMessage.setTransactionId(transactionId);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 1)
	//@Transactional(TransactionMode.ROLLBACK)
	public void testReserveBooks_Commit() throws Exception {
		String testName = "testReserveBooks_BooksAvailable_Commit";
		log.info(testName+": started");
		registerNotificationListeners();
		bookInventoryHelper.assureRemoveAll();
		Set<Book> books = Bookshop2Fixture.createSet_Book(2);
		supplierOrderCacheHelper.assureAddBooksInStock(books);
		reservationRequestMessage.setBooks(books);
		runTest();
		bookInventoryHelper.verifyReservedBooksCount(2);
		supplierOrderCacheHelper.verifyBooksInStockCount(0);
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 2)
	//@Transactional(TransactionMode.ROLLBACK)
	public void testReserveBooks_Commit2() throws Exception {
		String testName = "testReserveBooks_BooksAvailable_Commit2";
		log.info(testName+": started");
		registerNotificationListeners();
		bookInventoryHelper.assureRemoveAll();
		Set<Book> books = Bookshop2Fixture.createSet_Book(2);
		supplierOrderCacheHelper.assureAddBooksInStock(books);
		reservationRequestMessage.setBooks(books);
		List<Book> allReservedBooks = bookInventoryHelper.getAllReservedBooks();
		runTest();
		allReservedBooks = bookInventoryHelper.getAllReservedBooks();
		bookInventoryHelper.verifyReservedBooksCount(2);
		supplierOrderCacheHelper.verifyBooksInStockCount(0);
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 3)
	public void testReserveBooks_NullCorrelationId_Rollback() throws Exception {
		String testName = "testReserveBooks_NullCorrelationId_Rollback";
		log.info(testName+": started");
		registerNotificationListeners();
		bookInventoryHelper.assureRemoveAll();
		supplierOrderCacheHelper.assureRemoveAll();
		Set<Book> books = Bookshop2Fixture.createSet_Book(2);
		supplierOrderCacheHelper.assureAddBooksInStock(books);
		reservationRequestMessage.setBooks(books);
		reservationRequestMessage.setCorrelationId(null);
		exceptionMessage = "CorrelationId null";
		expectedEvent = "Supplier_ReserveBooks_Incoming_Request_Aborted";
		runTest();
		bookInventoryHelper.verifyReservedBooksCount(0);
		supplierOrderCacheHelper.verifyBooksInStockCount(2);
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}

	@Test
	@Ignore
	@InSequence(value = 4)
	public void testReserveBooks_NullTransactionId_Rollback() throws Exception {
		String testName = "testReserveBooks_BooksAvailable_NullTransactionId_Rollback";
		log.info(testName+": started");
		registerNotificationListeners();
		bookInventoryHelper.assureRemoveAll();
		supplierOrderCacheHelper.assureRemoveAll();
		reservationRequestMessage.setTransactionId(null);
		expectedEvent = "Supplier_ReserveBooks_Incoming_Request_Aborted";
		isExceptionExpected = true;
		runTest();
		bookInventoryHelper.verifyReservedBooksCount(0);
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 5)
	public void testReserveBooks_NullBooks_Rollback() throws Exception {
		String testName = "testReserveBooks_NullCorrelationId_Rollback";
		log.info(testName+": started");
		registerNotificationListeners();
		bookInventoryHelper.assureRemoveAll();
		supplierOrderCacheHelper.assureRemoveAll();
		Set<Book> books = Bookshop2Fixture.createSet_Book(2);
		supplierOrderCacheHelper.assureAddBooksInStock(books);
		reservationRequestMessage.setBooks(null);
		exceptionMessage = "ReservationRequestMessage must include one or more Book(s)";
		expectedEvent = "Supplier_ReserveBooks_Incoming_Request_Aborted";
		isExceptionExpected = true;
		runTest();
		bookInventoryHelper.verifyReservedBooksCount(0);
		supplierOrderCacheHelper.verifyBooksInStockCount(2);
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 6)
	public void testReserveBooks_EmptyBooks_Rollback() throws Exception {
		String testName = "testReserveBooks_EmptyBooks_Rollback";
		log.info(testName+": started");
		registerNotificationListeners();
		bookInventoryHelper.assureRemoveAll();
		supplierOrderCacheHelper.assureRemoveAll();
		//addReservedBooksToBookInventory();
		Set<Book> bookSet = Bookshop2Fixture.createSet_Book(2);
		Set<Book> emptyBookSet = Bookshop2Fixture.createEmptySet_Book();
		supplierOrderCacheHelper.assureAddBooksInStock(bookSet);
		reservationRequestMessage.setBooks(emptyBookSet);
		exceptionMessage = "ReservationRequestMessage must include one or more Book(s)";
		expectedEvent = "Supplier_ReserveBooks_Incoming_Request_Aborted";
		isExceptionExpected = true;
		runTest();
		bookInventoryHelper.verifyReservedBooksCount(0);
		supplierOrderCacheHelper.verifyBooksInStockCount(2);
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}

	@Test
	@InSequence(value = 7)
	@BytemanRule(name = "rule7",
			targetClass = "SupplierProcess",
			targetMethod = "handle_ReserveBooks_request",
			targetLocation = "AT EXIT",
			action = "throw new java.lang.RuntimeException(\"error7\")")
	public void testReserveBooks_ServiceLayerException_Rollback() throws Exception {
		String testName = "testReserveBooks_ServiceLayerException_Rollback";
		log.info(testName+": started");
		setupByteman(testName);
		registerNotificationListeners();
		bookInventoryHelper.assureRemoveAll();
		supplierOrderCacheHelper.assureRemoveAll();
		Set<Book> books = Bookshop2Fixture.createSet_Book(2);
		supplierOrderCacheHelper.assureAddBooksInStock(books);
		reservationRequestMessage.setBooks(books);
		exceptionClass = RuntimeException.class;
		exceptionMessage = "error7";
		expectedEvent = "Supplier_ReserveBooks_Incoming_Request_Aborted";
		runTest();
		bookInventoryHelper.verifyReservedBooksCount(0);
		supplierOrderCacheHelper.verifyBooksInStockCount(2);
		tearDownByteman(testName);
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	@InSequence(value = 8)
	@BytemanRule(name = "rule8",
			targetClass = "ReservedBooksManagerImpl",
			targetMethod = "addReservedBooksRecords",
			targetLocation = "AT EXIT",
			action = "throw new java.lang.RuntimeException(\"error8\")")
	public void testReserveBooks_DataLayerException_Rollback() throws Exception {
		String testName = "testReserveBooks_DataLayerException_Rollback";
		log.info(testName+": started");
		setupByteman(testName);
		registerNotificationListeners();
		bookInventoryHelper.assureRemoveAll();
		supplierOrderCacheHelper.assureRemoveAll();
		Set<Book> books = Bookshop2Fixture.createSet_Book(2);
		supplierOrderCacheHelper.assureAddBooksInStock(books);
		reservationRequestMessage.setBooks(books);
		expectedEvent = "Supplier_ReserveBooks_Incoming_Request_Aborted";
		exceptionClass = RuntimeException.class;
		exceptionMessage = "error8";
		runTest();
		bookInventoryHelper.verifyReservedBooksCount(0);
		supplierOrderCacheHelper.verifyBooksInStockCount(2);
		tearDownByteman(testName);
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	
	public void runTest() throws Exception {
		if (exceptionClass != null || exceptionMessage != null)
			isExceptionExpected = true;

		// Prepare fixture execution
		//removeMessagesFromDestinations();
		//removeMessagesFromTargetDestination();

		// prepare context
		//transactionId = control.beginUserTransaction();
		
		// prepare mocks
		
		registerForResult();

		try {
			// start fixture execution
			reserveBooksHandler.reserveBooks(reservationRequestMessage);
			
		} catch (ReservationAbortedException e) {
			if (isExceptionExpected) {
				errorMessage = e.getMessage();
				if (exceptionMessage != null)
					Assert.equals(e.getMessage(), exceptionMessage);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			if (isExceptionExpected) {
				errorMessage = e.getMessage();
				if (exceptionMessage != null)
					Assert.equals(e.getMessage(), exceptionMessage);
			}
		}
		
		// wait for result
		Object result = waitForCompletion();
		validateResult(result);
		
		// close context
		//control.commitUserTransaction();

		//assertEmptyTargetDestination();
		//removeMessagesFromTargetDestination();
	}

	protected void registerNotificationListeners() throws Exception {
		addRequestNotificationListeners("Supplier_ReserveBooks");
		super.registerNotificationListeners();
	}
	
	protected FutureResult<Object> registerForResult() throws Exception {
		if (expectedEvent == null)
			expectedEvent = getServiceId() + "_Request_Done";
		return registerForResult(expectedEvent);
	}
	

//	protected void removeMessagesFromDestinations() throws Exception {
//		removeMessagesFromQueue(getTargetArchive(), getTargetDestination());
//	}

	protected TransactionContext createTransactionContext() {
		TransactionContext transactionContext = new TransactionContext();
		transactionContext.setCorrelationId(correlationId);
		transactionContext.setTransactionId(transactionId);
		transactionContext.setExpiration(600000L);
		transactionContext.setIsolationLevel(TransactionIsolationLevel.READ_COMMITTED);
		transactionContext.setRegistrationService(createRegistrationEndpoint());
		return transactionContext;
	}

	protected Endpoint createRegistrationEndpoint() {
		Endpoint endpoint = new Endpoint();
		endpoint.setInstanceId(transactionId);
		endpoint.setServiceName(CoordinationConstants.REGISTRATION_SERVICE_QNAME);
		endpoint.setEndpointName(CoordinationConstants.REGISTRATION_ENDPOINT_QNAME);
		endpoint.setEndpointUri(ServiceRegistry.getInstance().getServiceURI(CoordinationConstants.REGISTRATION_SERVICE_NAME));
		return endpoint;
	}

	protected void addReservedBooksToBookInventory() throws Exception {
		Set<Book> books = Bookshop2Fixture.createSet_Book();
		addReservedBooksToBookInventory(books);
	}
	
	protected void addReservedBooksToBookInventory(Set<Book> books) throws Exception {
		ObjectName objectName = new ObjectName(BookInventoryManagerMBean.MBEAN_NAME);
		Object[] parameters = { books };
		String[] signature = { "java.util.Set" };
		jmxManager.invoke(objectName, "addToReservedBooks", parameters, signature);
		jmxManager.invoke(objectName, "updateState");
	}
	
	protected void assertRolledBack() throws Exception {
		
	}
	
	protected int getTransactionStatus() throws Exception {
		ObjectName objectName = new ObjectName(TransactionRegistryManagerMBean.MBEAN_NAME);
		Object result = jmxManager.invoke(objectName, "getStatus");
		if (result != null && result instanceof Integer)
			return ((Integer) result).intValue();
		return -1;
	}
	
	
	protected void validateResult(Object result) {
		validateResult(result, exceptionClass, exceptionMessage);
	}
	
	protected void validateResult(Object result, Class<?> exceptionClass, String exceptionMessage) {
		if (result.getClass().equals(exceptionClass)) {
			Exception exception = (Exception) result;
			Assert.startsWith(exception.getMessage(), exceptionMessage);
			System.out.println(">>>>>>>> "+exception.getMessage());
		} else if (result instanceof Throwable) {
			Exception exception = (Exception) result;
			Assert.startsWith(exception.getMessage(), exceptionMessage);
			System.out.println(">>>>>>>> "+exception.getMessage());
		} else if (result instanceof String) {
			if (!result.toString().startsWith(getServiceId()))
				errorMessage = "Unexpected serviceId: "+result;
		} else {
			errorMessage = "Unrecognized result: "+result;
		}
	}

	@Override
	public AbstractStateManager<?> getStateManager() {
		// TODO Auto-generated method stub
		return null;
	}

}
