package bookshop2.supplier.incoming.reserveBooks;

import java.util.Set;

import javax.management.ObjectName;

import nam.model.Endpoint;
import nam.model.TransactionContext;
import nam.model.TransactionIsolationLevel;

import org.aries.Assert;
import org.aries.transport.TransportType;
import org.aries.tx.AbstractArquillianTest;
import org.aries.tx.AbstractJMSListenerArquillionTest;
import org.aries.tx.BytemanRule;
import org.aries.tx.CacheModuleTestControl;
import org.aries.tx.DataModuleTestControl;
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

import org.aries.ApplicationFailure;

import tx.manager.registry.ServiceRegistry;
import bookshop2.Book;
import bookshop2.ReservationRequestMessage;
import bookshop2.supplier.SupplierTestEARBuilder;
import bookshop2.supplier.client.reserveBooks.ReserveBooks;
import bookshop2.supplier.client.reserveBooks.ReserveBooksClient;
import bookshop2.supplier.client.reserveBooks.ReserveBooksProxyForJMS;
import bookshop2.supplier.data.bookInventory.BookInventoryHelper;
import bookshop2.supplier.data.bookInventory.BookInventoryManagerMBean;
import bookshop2.supplier.data.bookInventory.BookInventoryProxy;
import bookshop2.supplier.data.supplierOrderCache.SupplierOrderCacheHelper;
import bookshop2.supplier.data.supplierOrderCache.SupplierOrderCacheProxy;
import bookshop2.util.Bookshop2Fixture;
import common.tx.CoordinationConstants;


@RunAsClient
@RunWith(Arquillian.class)
public class ReserveBooksListenerForJMSCIT extends AbstractJMSListenerArquillionTest {
	
	private ReserveBooksClient reserveBooksClient;

	private ReservationRequestMessage reservationRequestMessage;
	
	private TransactionTestControl transactionTestControl;
	
	private SupplierOrderCacheHelper supplierOrderCacheHelper;

	private CacheModuleTestControl supplierOrderCacheControl;
	
	private BookInventoryHelper bookInventoryHelper;

	private DataModuleTestControl bookInventoryControl;
	
	
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
		return ReserveBooksListenerForJMSCIT.class;
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
	public String getModuleId() {
		return "bookshop2.supplier.service";
	}
	
	@Override
	public String getServiceId() {
		return "bookshop2.supplier.reserveBooks";
	}

	@Override
	public String getTargetArchive() {
		return SupplierTestEARBuilder.NAME;
	}
	
	@Override
	public String getTargetDestination() {
		return get_target_Supplier_ReserveBooks_destination();
	}

	public String get_target_Supplier_ReserveBooks_destination() {
		return getJNDINameForQueue("inventory_bookshop2_supplier_reserve_books_queue");
	}
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		startServer();
		createTransactionControl();
		createSupplierOrderCacheHelper();
		createBookInventoryHelper();
		create_ReservationRequestMessage();
		clearBookInventoryContext();
	}

	protected void createTransactionControl() throws Exception {
		transactionTestControl = new TransactionTestControl();
		transactionTestControl.setupTransactionManager();
	}
	
	public void createSupplierOrderCacheHelper() throws Exception {
		supplierOrderCacheHelper = new SupplierOrderCacheHelper();
		supplierOrderCacheHelper.setProxy(createSupplierOrderCacheProxy());
		supplierOrderCacheHelper.initializeAsClient(createSupplierOrderCacheControl());
	}
	
	protected SupplierOrderCacheProxy createSupplierOrderCacheProxy() throws Exception {
		SupplierOrderCacheProxy supplierOrderCacheProxy = new SupplierOrderCacheProxy();
		supplierOrderCacheProxy.setJmxManager(jmxManager);
		return supplierOrderCacheProxy;
	}

	protected CacheModuleTestControl createSupplierOrderCacheControl() throws Exception {
		supplierOrderCacheControl = new CacheModuleTestControl(transactionTestControl);
		supplierOrderCacheControl.setupCacheLayer();
		return supplierOrderCacheControl;
	}

	public void createBookInventoryHelper() throws Exception {
		bookInventoryHelper = new BookInventoryHelper();
		bookInventoryHelper.setJmxProxy(createBookInventoryProxy());
		bookInventoryHelper.initializeAsClient(createBookInventoryControl());
	}
	
	protected BookInventoryProxy createBookInventoryProxy() throws Exception {
		BookInventoryProxy bookInventoryProxy = new BookInventoryProxy();
		bookInventoryProxy.setJmxManager(jmxManager);
		return bookInventoryProxy;
	}
	
	protected DataModuleTestControl createBookInventoryControl() throws Exception {
		bookInventoryControl = new DataModuleTestControl(transactionTestControl);
		bookInventoryControl.setDatabaseName("bookshop2_supplier_db");
		bookInventoryControl.setDataSourceName("bookshop2_supplier_ds");
		bookInventoryControl.setPersistenceUnitName("bookInventory");
		bookInventoryControl.setupDataLayer();
		return bookInventoryControl;
	}
	
	public void clearBookInventoryContext() throws Exception {
		jmxProxy.call(BookInventoryManagerMBean.MBEAN_NAME, "clearContext");
	}
	
	public ReservationRequestMessage create_ReservationRequestMessage() {
		return create_ReservationRequestMessage(false, false);
	}
	
	public ReservationRequestMessage createReservationRequestCancelMessage() {
		return create_ReservationRequestMessage(true, false);
	}
	
	public ReservationRequestMessage createReservationRequestUndoMessage() {
		return create_ReservationRequestMessage(false, true);
	}
	
	public ReservationRequestMessage create_ReservationRequestMessage(boolean cancel, boolean undo) {
		reservationRequestMessage = Bookshop2Fixture.create_ReservationRequestMessage(cancel, undo);
		return initializeMessage(reservationRequestMessage);
	}
	
	protected void registerNotificationListeners() throws Exception {
		addRequestNotificationListeners("Supplier_ReserveBooks");
		super.registerNotificationListeners();
	}
	
	@After
	public void tearDown() throws Exception {
		transactionTestControl.tearDown();
		bookInventoryControl.tearDown();
		supplierOrderCacheControl.tearDown();
		//removeMessagesFromTargetDestination();
		clearStructures();
		clearState();
		super.tearDown();
	}
	
	protected void clearStructures() throws Exception {
		reserveBooksClient.reset();
		reserveBooksClient = null;
		super.clearStructures();
	}

	protected void clearState() throws Exception {
		reservationRequestMessage = null;
		super.clearState();
	}
	
	protected void removeMessagesFromDestinations() throws Exception {
		removeMessagesFromQueue(getTargetArchive(), get_target_Supplier_ReserveBooks_destination());
	}

	@TargetsContainer("hornetQ01_local")
	@Deployment(name = "supplierEAR", order = 2)
	public static EnterpriseArchive createSupplierEAR() {
		SupplierTestEARBuilder builder = new SupplierTestEARBuilder();
		builder.setIncludeWar(false);
		return builder.createEAR();
	}
	
	public void run_send_ReserveBooks() throws Exception {
		run_send_ReserveBooks(create_ReservationRequestMessage());
	}

	public void run_send_ReserveBooks(ReservationRequestMessage reservationRequestMessage) throws Exception {
		this.reservationRequestMessage = reservationRequestMessage;
		
		// prepare the environment
		removeMessagesFromDestinations();

		// prepare context
		//beginTransaction();
		
		// prepare mocks
		registerForResult();

		// start fixture execution
		reserveBooksClient = create_ReserveBooks_client();
		reserveBooksClient.reserveBooks(reservationRequestMessage);
		//reserveBooksClient.send(reservationRequestMessage, correlationId, null);
		
		// wait for completion result
		Object result = waitForCompletion();
		validateResult(result);
		
		// close context
		//commitTransaction();

		// validate state
		assertEmptyTargetDestination();
	
		// cleanup
		removeMessagesFromDestinations();
	}

	protected void run_send_ReserveBooks_cancel() throws Exception {
		expectedEvent = "Supplier_ReserveBooks_Request_Cancel_Done";
		registerForResult(expectedEvent);
		
		ReservationRequestMessage message = createReservationRequestCancelMessage();
		reserveBooksClient.reserveBooks(reservationRequestMessage);
		//reserveBooksClient.send(message, correlationId, null);
		
		Object result = waitForCompletion();
		validateResult(result);
	}
	
	protected void run_send_ReserveBooks_undo() throws Exception {
		expectedEvent = "Supplier_ReserveBooks_Request_Undo_Done";
		registerForResult(expectedEvent);
		
		ReservationRequestMessage message = createReservationRequestUndoMessage();
		reserveBooksClient.reserveBooks(reservationRequestMessage);
		//reserveBooksClient.send(message, correlationId, null);
		
		Object result = waitForCompletion();
		validateResult(result);
	}
	
	public Thread start_run_send_ReserveBooks() throws Exception {
		Thread thread = new Thread() {
			public void run() {
				try {
					run_send_ReserveBooks();
				} catch (Exception e) {
					errorMessage = e.getMessage();
				}
			}
		};
		thread.start();
		Thread.sleep(4000);
		return thread;
	}
	
	protected ReserveBooksClient create_ReserveBooks_client() throws Exception {
		ReserveBooksProxyForJMS delegate = createReserveBooksClientDelegate();
		ReserveBooksClient client = new ReserveBooksClient();
		client.setLocalDomain("bookshop2.supplier");
		client.setLocalModule("bookshop2.supplier.service");
		client.setTransportType(TransportType.JMS);
		client.setDelegate(delegate);
		return client;
	}

	protected ReserveBooksProxyForJMS createReserveBooksClientDelegate() throws Exception {
		ReserveBooksProxyForJMS delegate = new ReserveBooksProxyForJMS(ReserveBooks.ID);
		configureClient(delegate, getTargetDestination());
		registerJMSProxy(delegate, ReserveBooks.ID);
		delegate.setCorrelationId(correlationId);
		delegate.setTransactionId(transactionId);
		//TODO delegate.setTransactionContext(createTransactionContext());
		delegate.setCreateTemporaryQueue(true);
		return delegate;
	}

	
	protected void validateResult(Object result) {
		validateResult(result, exceptionClass, exceptionMessage);
	}
	
	protected void validateResult(Object result, Class<?> exceptionClass, String exceptionMessage) {
		if (result.getClass().equals(exceptionClass)) {
			Exception exception = (Exception) result;
			Assert.equals(exceptionMessage, exception.getMessage(), "Unexpected exception message: "+result);
		} else if (result instanceof Throwable) {
			Exception exception = (Exception) result;
			Assert.equals(exceptionMessage, exception.getMessage(), "Unexpected exception message: "+result);
		} else if (result instanceof String) {
			String resultString = result.toString();
			if ((expectedError != null && !expectedError.equalsIgnoreCase(resultString)) &&
				(expectedEvent != null && !expectedEvent.equalsIgnoreCase(resultString)))
					errorMessage = "Unexpected message: "+result;
		} else {
			errorMessage = "Unrecognized result: "+result;
		}
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
		expectedEvent = "Supplier_ReserveBooks_Request_Done";
		Set<Book> books = Bookshop2Fixture.createSet_Book(2);
		supplierOrderCacheHelper.assureAddBooksInStock(books);
		reservationRequestMessage.setBooks(books);
		run_send_ReserveBooks(reservationRequestMessage);
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
		expectedEvent = "Supplier_ReserveBooks_Request_Done";
		Set<Book> books = Bookshop2Fixture.createSet_Book(2);
		supplierOrderCacheHelper.assureAddBooksInStock(books);
		reservationRequestMessage.setBooks(books);
		run_send_ReserveBooks(reservationRequestMessage);
		bookInventoryHelper.verifyReservedBooksCount(2);
		supplierOrderCacheHelper.verifyBooksInStockCount(0);
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	@Ignore
	@InSequence(value = 3)
	public void testReserveBooks_NullCorrelationId_Rollback() throws Exception {
		String testName = "testReserveBooks_NullCorrelationId_Rollback";
		log.info(testName+": started");
		registerNotificationListeners();
		bookInventoryHelper.assureRemoveAll();
		supplierOrderCacheHelper.assureRemoveAll();
		expectedEvent = "Supplier_ReserveBooks_Incoming_Request_Aborted";
		Set<Book> books = Bookshop2Fixture.createSet_Book(2);
		supplierOrderCacheHelper.assureAddBooksInStock(books);
		reservationRequestMessage.setBooks(books);
		reservationRequestMessage.setCorrelationId(null);
		exceptionMessage = "CorrelationId null";
		isValidationErrorExpected = true;
		//isExceptionExpected = true;
		run_send_ReserveBooks(reservationRequestMessage);
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
		expectedEvent = "Supplier_ReserveBooks_Incoming_Request_Aborted";
		Set<Book> books = Bookshop2Fixture.createSet_Book(2);
		supplierOrderCacheHelper.assureAddBooksInStock(books);
		reservationRequestMessage.setBooks(books);
		reservationRequestMessage.setTransactionId(null);
		exceptionMessage = "TransactionId null";
		isValidationErrorExpected = true;
		//isExceptionExpected = true;
		run_send_ReserveBooks(reservationRequestMessage);
		bookInventoryHelper.verifyReservedBooksCount(0);
		supplierOrderCacheHelper.verifyBooksInStockCount(2);
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
		expectedEvent = "Supplier_ReserveBooks_Incoming_Request_Aborted";
		Set<Book> books = Bookshop2Fixture.createSet_Book(2);
		supplierOrderCacheHelper.assureAddBooksInStock(books);
		reservationRequestMessage.setBooks(null);
		exceptionMessage = "ReservationRequestMessage must include one or more Book(s)";
		isValidationErrorExpected = true;
		//isExceptionExpected = true;
		run_send_ReserveBooks(reservationRequestMessage);
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
		expectedEvent = "Supplier_ReserveBooks_Incoming_Request_Aborted";
		//addReservedBooksToBookInventory();
		Set<Book> bookSet = Bookshop2Fixture.createSet_Book(2);
		Set<Book> emptyBookSet = Bookshop2Fixture.createEmptySet_Book();
		supplierOrderCacheHelper.assureAddBooksInStock(bookSet);
		reservationRequestMessage.setBooks(emptyBookSet);
		exceptionMessage = "ReservationRequestMessage must include one or more Book(s)";
		isValidationErrorExpected = true;
		//isExceptionExpected = true;
		run_send_ReserveBooks(reservationRequestMessage);
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
			targetMethod = "handle_ReserveBooks_request_done",
			targetLocation = "AT ENTRY",
			action = "throw new org.aries.ApplicationFailure(\"error7\")")
	public void testReserveBooks_ServiceLayerException_Rollback() throws Exception {
		String testName = "testReserveBooks_ServiceLayerException_Rollback";
		log.info(testName+": started");
		setupByteman(testName);
		registerNotificationListeners();
		
		bookInventoryHelper.assureRemoveAll();
		supplierOrderCacheHelper.assureRemoveAll();
		expectedEvent = "Supplier_ReserveBooks_Incoming_Request_Aborted";
		
		Set<Book> books = Bookshop2Fixture.createSet_Book(2);
		supplierOrderCacheHelper.assureAddBooksInStock(books);
		reservationRequestMessage.setBooks(books);
		
		isValidationErrorExpected = true;
		exceptionClass = ApplicationFailure.class;
		exceptionMessage = "error7";
		
		run_send_ReserveBooks(reservationRequestMessage);
		
		bookInventoryHelper.verifyReservedBooksCount(0);
		supplierOrderCacheHelper.verifyBooksInStockCount(2);
		
		tearDownByteman(testName);
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 8)
	@BytemanRule(name = "rule8",
			targetClass = "ReservedBooksManagerImpl",
			targetMethod = "addReservedBooksRecords",
			targetLocation = "AT EXIT",
			action = "throw new org.aries.ApplicationFailure(\"error8\")")
	public void testReserveBooks_DataLayerException_Rollback() throws Exception {
		String testName = "testReserveBooks_DataLayerException_Rollback";
		log.info(testName+": started");
		setupByteman(testName);
		registerNotificationListeners();
		bookInventoryHelper.assureRemoveAll();
		supplierOrderCacheHelper.assureRemoveAll();
		expectedEvent = "Supplier_ReserveBooks_Incoming_Request_Aborted";
		Set<Book> books = Bookshop2Fixture.createSet_Book(2);
		supplierOrderCacheHelper.assureAddBooksInStock(books);
		reservationRequestMessage.setBooks(books);
		isValidationErrorExpected = true;
		exceptionClass = ApplicationFailure.class;
		exceptionMessage = "error8";
		run_send_ReserveBooks(reservationRequestMessage);
		bookInventoryHelper.verifyReservedBooksCount(0);
		supplierOrderCacheHelper.verifyBooksInStockCount(2);
		tearDownByteman(testName);
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
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
	


}
