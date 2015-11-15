package bookshop2.supplier;

import java.util.List;
import java.util.Set;

import javax.management.ObjectName;

import nam.model.Endpoint;
import nam.model.TransactionContext;
import nam.model.TransactionIsolationLevel;

import org.aries.Assert;
import org.aries.tx.AbstractArquillianTest;
import org.aries.tx.AbstractJMSListenerArquillionTest;
import org.aries.tx.BytemanRule;
import org.aries.tx.CacheModuleTestControl;
import org.aries.tx.DataModuleTestControl;
import org.aries.tx.ServiceModuleTestControl;
import org.aries.tx.TransactionRegistryManagerMBean;
import org.aries.tx.TransactionTestControl;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
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
import bookshop2.supplier.client.reserveBooks.ReserveBooks;
import bookshop2.supplier.client.reserveBooks.ReserveBooksClient;
import bookshop2.supplier.client.reserveBooks.ReserveBooksProxyForJMS;
import bookshop2.supplier.data.bookInventory.BookInventoryHelper;
import bookshop2.supplier.data.bookInventory.BookInventoryManagerMBean;
import bookshop2.supplier.data.supplierOrderCache.SupplierOrderCacheHelper;
import bookshop2.supplier.data.supplierOrderCache.SupplierOrderCacheProxy;
import bookshop2.util.Bookshop2Fixture;
import common.tx.CoordinationConstants;


@RunAsClient
@RunWith(Arquillian.class)
public class ReserveBooksJMSListenerCIT extends AbstractJMSListenerArquillionTest {
	
	private TransactionTestControl transactionTestControl;
	
	private BookInventoryHelper bookInventoryHelper;

	private DataModuleTestControl bookInventoryTestControl;
	
	private SupplierOrderCacheHelper supplierOrderCacheHelper;

	private SupplierOrderCacheProxy supplierOrderCacheProxy;
	
	private CacheModuleTestControl supplierOrderCacheControl;
	
	private ReserveBooksClient reserveBooksClient;

	private ReservationRequestMessage reservationRequestMessage;

	
	@Override
	public String getServerName() {
		return "hornetQ01_local";
	}
	
	@Override
	public String getDomainId() {
		return "bookshop2.supplier";
	}
	
	@Override
	public String getServiceId() {
		return "bookshop2.supplier.ReserveBooks";
	}

	@Override
	public String getTargetArchive() {
		return SupplierTestEARBuilder.NAME;
	}
	
	@Override
	public String getTargetDestination() {
		return getJNDINameForQueue("inventory_bookshop2_supplier_reserve_books_queue");
	}
	
	@Override
	public Class<?> getTestClass() {
		return ReserveBooksJMSListenerCIT.class;
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
		super.setUp();
		startServer();
		createTransactionControl();
		createServiceLayerTestControl();
		createSupplierOrderCacheTestControl();
		createSupplierOrderCacheHelper();
		createBookInventoryHelper();
		createReservationRequestMessage();
		clearBookInventoryContext();
	}

	protected void createTransactionControl() throws Exception {
		transactionTestControl = new TransactionTestControl();
		transactionTestControl.setupTransactionManager();
	}
	
	protected void createServiceLayerTestControl() throws Exception {
		bookInventoryTestControl = new DataModuleTestControl(transactionTestControl);
		bookInventoryTestControl.setDatabaseName("bookshop2_supplier_db");
		bookInventoryTestControl.setDataSourceName("bookshop2_supplier_ds");
		bookInventoryTestControl.setPersistenceUnitName("bookInventory");
		bookInventoryTestControl.setupDataLayer();
	}

	protected void createSupplierOrderCacheTestControl() throws Exception {
		supplierOrderCacheControl = new CacheModuleTestControl(transactionTestControl);
		supplierOrderCacheControl.setupCacheLayer();
	}

	public void clearBookInventoryContext() throws Exception {
		jmxProxy.call(BookInventoryManagerMBean.MBEAN_NAME, "clearContext");
	}
	
	@After
	public void tearDown() throws Exception {
		transactionTestControl.tearDown();
		bookInventoryTestControl.tearDown();
		supplierOrderCacheControl.tearDown();
		//removeMessagesFromTargetDestination();
		clearStructures();
		clearState();
		super.tearDown();
	}
	
	protected void clearStructures() throws Exception {
		reserveBooksClient.reset();
		reserveBooksClient = null;
		reservationRequestMessage = null;
	}

	protected void clearState() throws Exception {
		super.clearState();
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
		builder.setIncludeWar(true);
		return builder.createEAR();
	}
	
	protected void createBookInventoryHelper() throws Exception {
		bookInventoryHelper = new BookInventoryHelper();
		bookInventoryHelper.setJmxManager(jmxManager);
		bookInventoryHelper.initializeAsClient(bookInventoryTestControl);
	}
	
	public void createSupplierOrderCacheHelper() throws Exception {
		supplierOrderCacheHelper = new SupplierOrderCacheHelper();
		supplierOrderCacheHelper.setProxy(createSupplierOrderCacheProxy());
		supplierOrderCacheHelper.initializeAsClient(supplierOrderCacheControl);
	}
	
	public SupplierOrderCacheProxy createSupplierOrderCacheProxy() throws Exception {
		supplierOrderCacheProxy = new SupplierOrderCacheProxy();
		supplierOrderCacheProxy.setJmxManager(jmxManager);
		return supplierOrderCacheProxy;
	}
	
	public ReservationRequestMessage createReservationRequestMessage() {
		return createReservationRequestMessage(false, false);
	}
	
	public ReservationRequestMessage createReservationRequestCancelMessage() {
		return createReservationRequestMessage(true, false);
	}
	
	public ReservationRequestMessage createReservationRequestUndoMessage() {
		return createReservationRequestMessage(false, true);
	}
	
	public ReservationRequestMessage createReservationRequestMessage(boolean cancel, boolean undo) {
		reservationRequestMessage = Bookshop2Fixture.create_ReservationRequestMessage(cancel, undo);
		return initializeMessage(reservationRequestMessage);
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
		runTest();
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
		bookInventoryHelper.assureRemoveAll();
		supplierOrderCacheHelper.assureRemoveAll();
		expectedEvent = "bookshop2.supplier.ReserveBooks_Incoming_Request_Aborted";
		Set<Book> books = Bookshop2Fixture.createSet_Book(2);
		supplierOrderCacheHelper.assureAddBooksInStock(books);
		reservationRequestMessage.setBooks(books);
		reservationRequestMessage.setCorrelationId(null);
		exceptionMessage = "CorrelationId null";
		isValidationErrorExpected = true;
		//isExceptionExpected = true;
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
		bookInventoryHelper.assureRemoveAll();
		supplierOrderCacheHelper.assureRemoveAll();
		reservationRequestMessage.setTransactionId(null);
		isValidationErrorExpected = true;
		//isExceptionExpected = true;
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
		bookInventoryHelper.assureRemoveAll();
		supplierOrderCacheHelper.assureRemoveAll();
		expectedEvent = "bookshop2.supplier.ReserveBooks_Incoming_Request_Aborted";
		List<Book> books = Bookshop2Fixture.createList_Book(2);
		supplierOrderCacheHelper.assureAddBooksInStock(books);
		reservationRequestMessage.setBooks(null);
		exceptionMessage = "ReservationRequestMessage must include one or more books(s)";
		isValidationErrorExpected = true;
		//isExceptionExpected = true;
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
		bookInventoryHelper.assureRemoveAll();
		supplierOrderCacheHelper.assureRemoveAll();
		expectedEvent = "bookshop2.supplier.ReserveBooks_Incoming_Request_Aborted";
		//addReservedBooksToBookInventory();
		Set<Book> bookList = Bookshop2Fixture.createSet_Book(2);
		Set<Book> emptyBookList = Bookshop2Fixture.createEmptySet_Book();
		supplierOrderCacheHelper.assureAddBooksInStock(bookList);
		reservationRequestMessage.setBooks(emptyBookList);
		exceptionMessage = "ReservationRequestMessage must include one or more books(s)";
		isValidationErrorExpected = true;
		//isExceptionExpected = true;
		runTest();
		bookInventoryHelper.verifyReservedBooksCount(0);
		supplierOrderCacheHelper.verifyBooksInStockCount(2);
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}

	@Test
	//@Ignore
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
		bookInventoryHelper.assureRemoveAll();
		supplierOrderCacheHelper.assureRemoveAll();
		expectedEvent = "bookshop2.supplier.ReserveBooks_Incoming_Request_Aborted";
		Set<Book> books = Bookshop2Fixture.createSet_Book(2);
		supplierOrderCacheHelper.assureAddBooksInStock(books);
		reservationRequestMessage.setBooks(books);
		isValidationErrorExpected = true;
		exceptionClass = RuntimeException.class;
		exceptionMessage = "error7";
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
		bookInventoryHelper.assureRemoveAll();
		supplierOrderCacheHelper.assureRemoveAll();
		expectedEvent = "bookshop2.supplier.ReserveBooks_Incoming_Request_Aborted";
		Set<Book> books = Bookshop2Fixture.createSet_Book(2);
		supplierOrderCacheHelper.assureAddBooksInStock(books);
		reservationRequestMessage.setBooks(books);
		isValidationErrorExpected = true;
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
		removeMessagesFromDestinations();

		// prepare context
		//transactionId = control.beginUserTransaction();
		
		// prepare mocks
		registerForResult();

		try {
			// start fixture execution
			reserveBooksClient = createReserveBooksClient();
			sendRequest_ReserveBooks();
			
		} catch (ReservationAbortedException e) {
			if (isExceptionExpected) {
				errorMessage = e.getMessage();
				if (exceptionMessage != null)
					Assert.equals(e.getMessage(), exceptionMessage);
			}
			
		} catch (Exception e) {
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

		// validate the environment
		assertEmptyTargetDestination();
		removeMessagesFromDestinations();
	}
	
	protected void sendRequest_ReserveBooks() throws Exception {
		reserveBooksClient.reserveBooks(reservationRequestMessage);
		//reserveBooksClient.send(reservationRequestMessage, correlationId, null);
	}

	protected void sendRequest_ReserveBooks_Cancel() throws Exception {
		reservationRequestMessage = createReservationRequestCancelMessage();
		sendRequest_ReserveBooks();
	}

	protected void sendRequest_ReserveBooks_Undo() throws Exception {
		reservationRequestMessage = createReservationRequestUndoMessage();
		sendRequest_ReserveBooks();
	}
	
	protected void registerNotificationListeners() throws Exception {
		addRequestNotificationListeners("ReserveBooks");
	}
	
	protected void removeMessagesFromDestinations() throws Exception {
		removeMessagesFromQueue(getTargetArchive(), getTargetDestination());
	}

	protected ReserveBooksClient createReserveBooksClient() throws Exception {
		ReserveBooksProxyForJMS delegate = createReserveBooksClientDelegate();
		ReserveBooksClient client = new ReserveBooksClient();
		client.setDelegate(delegate);
		return client;
	}

	protected ReserveBooksProxyForJMS createReserveBooksClientDelegate() throws Exception {
		ReserveBooksProxyForJMS delegate = new ReserveBooksProxyForJMS(ReserveBooks.ID);
		configureClient(delegate, getTargetDestination());
		delegate.setCorrelationId(correlationId);
		delegate.setTransactionId(transactionId);
		delegate.setTransactionContext(createTransactionContext());
		delegate.setCreateTemporaryQueue(true);
		return delegate;
	}

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
		List<Book> books = Bookshop2Fixture.createList_Book();
		addReservedBooksToBookInventory(books);
	}
	
	protected void addReservedBooksToBookInventory(List<Book> books) throws Exception {
		ObjectName objectName = new ObjectName(BookInventoryManagerMBean.MBEAN_NAME);
		Object[] parameters = { books };
		String[] signature = { "java.util.List" };
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
			Assert.equals(exception.getMessage(), exceptionMessage, "Unexpected exception message: "+result);
		} else if (result instanceof Throwable) {
			Exception exception = (Exception) result;
			Assert.equals(exception.getMessage(), exceptionMessage, "Unexpected exception message: "+result);
		} else if (result instanceof String) {
			String resultString = result.toString();
			if ((expectedError != null && !expectedError.equalsIgnoreCase(resultString)) &&
				(expectedEvent != null && !expectedEvent.equalsIgnoreCase(resultString)))
					errorMessage = "Unexpected message: "+result;
		} else {
			errorMessage = "Unrecognized result: "+result;
		}
	}

}
