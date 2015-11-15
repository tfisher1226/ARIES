package bookshop2.supplier.incoming.queryBooks;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.aries.Assert;
import org.aries.jms.client.JmsClient;
import org.aries.jms.util.MessageUtil;
import org.aries.tx.AbstractArquillianTest;
import org.aries.tx.AbstractJMSListenerArquillionTest;
import org.aries.tx.BytemanRule;
import org.aries.tx.CacheModuleTestControl;
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

import bookshop2.BooksAvailableMessage;
import bookshop2.BooksUnavailableMessage;
import bookshop2.QueryRequestMessage;
import bookshop2.supplier.SupplierTestEARBuilder;
import bookshop2.supplier.client.queryBooks.QueryBooks;
import bookshop2.supplier.client.queryBooks.QueryBooksProxyForJMS;
import bookshop2.supplier.data.supplierOrderCache.SupplierOrderCacheHelper;
import bookshop2.supplier.data.supplierOrderCache.SupplierOrderCacheProxy;
import bookshop2.supplier.outgoing.booksAvailable.BooksAvailableReply;
import bookshop2.supplier.outgoing.booksUnavailable.BooksUnavailableReply;
import bookshop2.util.Bookshop2Fixture;


@RunAsClient
@RunWith(Arquillian.class)
public class QueryBooksListenerForJMSCIT extends AbstractJMSListenerArquillionTest {
	
	private JmsClient queryBooksClient;
	
	private JmsClient localSupplierBooksAvailableHandler;
	
	private JmsClient localSupplierBooksUnavailableHandler;
	
	private QueryRequestMessage queryRequestMessage;
	
	private BooksAvailableMessage booksAvailableMessage;
	
	private BooksUnavailableMessage booksUnavailableMessage;
	
	private TransactionTestControl transactionTestControl;
	
	private SupplierOrderCacheHelper supplierOrderCacheHelper;
	
	private boolean booksAvailableReceived;
	
	private boolean booksUnavailableReceived;
	
	
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
		return QueryBooksListenerForJMSCIT.class;
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
	public String getServiceId() {
		return "bookshop2.supplier.queryBooks";
	}
	
	@Override
	public String getTargetArchive() {
		return SupplierTestEARBuilder.NAME;
	}
	
	@Override
	public String getTargetDestination() {
		return get_target_Supplier_QueryBooks_destination();
	}

	public String get_target_Supplier_QueryBooks_destination() {
		return getJNDINameForQueue("inventory_bookshop2_supplier_query_books_queue");
	}
	
	public String get_local_Supplier_BooksAvailable_destination() {
		return "test_message_domain_test_destination_queue_a";
	}
	
	public String get_local_Supplier_BooksUnavailable_destination() {
		return "test_message_domain_test_destination_queue_b";
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		startServer();
		createTransactionControl();
		createSupplierOrderCacheHelper();
		removeMessagesFromDestinations();
		Bookshop2Fixture.reset();
	}
	
	protected void createTransactionControl() throws Exception {
		transactionTestControl = new TransactionTestControl();
		transactionTestControl.setupTransactionManager();
	}
	
	public void createSupplierOrderCacheHelper() throws Exception {
		supplierOrderCacheHelper = new SupplierOrderCacheHelper();
		supplierOrderCacheHelper.setRunningAsClient(true);
		supplierOrderCacheHelper.setProxy(createSupplierOrderCacheProxy());
		supplierOrderCacheHelper.initializeAsClient(createSupplierOrderCacheControl());
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
	
	public QueryRequestMessage createQueryRequestMessage() {
		return createQueryRequestMessage(false, false);
	}
	
	public QueryRequestMessage createQueryRequestCancelMessage() {
		return createQueryRequestMessage(true, false);
	}
	
	public QueryRequestMessage createQueryRequestUndoMessage() {
		return createQueryRequestMessage(false, true);
	}
	
	public QueryRequestMessage createQueryRequestMessage(boolean cancel, boolean undo) {
		QueryRequestMessage message = Bookshop2Fixture.create_QueryRequestMessage(cancel, undo);
		message.addToReplyToDestinations("BooksAvailable", get_local_Supplier_BooksAvailable_destination());
		message.addToReplyToDestinations("BooksUnavailable", get_local_Supplier_BooksUnavailable_destination());
		initializeMessage(message);
		return message;
	}
	
	protected void registerNotificationListeners() throws Exception {
		addRequestNotificationListeners("Supplier_QueryBooks");
		addResponseNotificationListeners("Supplier_BooksAvailable");
		addResponseNotificationListeners("Supplier_BooksUnavailable");
		super.registerNotificationListeners();
	}
	
	@After
	public void tearDown() throws Exception {
		clearStructures();
		clearState();
		super.tearDown();
	}
	
	protected void clearStructures() throws Exception {
		queryBooksClient.reset();
		queryBooksClient = null;

		//local handlers for responses from target service
		localSupplierBooksAvailableHandler.reset();
		localSupplierBooksAvailableHandler = null;
		localSupplierBooksUnavailableHandler.reset();
		localSupplierBooksUnavailableHandler = null;
		super.clearStructures();
	}

	protected void clearState() throws Exception {
		queryRequestMessage = null;
		booksAvailableMessage = null;
		booksUnavailableMessage = null;
		booksAvailableReceived = false;
		booksUnavailableReceived = false;
		super.clearState();
	}
	
	protected void removeMessagesFromDestinations() throws Exception {
		removeMessagesFromQueue(getTargetArchive(), get_target_Supplier_QueryBooks_destination());
		removeMessagesFromQueue(getTargetArchive(), get_local_Supplier_BooksAvailable_destination());
		removeMessagesFromQueue(getTargetArchive(), get_local_Supplier_BooksUnavailable_destination());
	}
	
	@TargetsContainer("hornetQ01_local")
	@Deployment(name = "supplierEAR", order = 2)
	public static EnterpriseArchive createSupplierEAR() {
		SupplierTestEARBuilder builder = new SupplierTestEARBuilder();
		builder.setRunningAsClient(true);
		//builder.setIncludeWar(true);
		return builder.createEAR();
	}
	
	public void runAction_send_QueryBooks() throws Exception {
		runAction_send_QueryBooks(createQueryRequestMessage());
	}
	
	public void runAction_send_QueryBooks(QueryRequestMessage queryRequestMessage) throws Exception {
		this.queryRequestMessage = queryRequestMessage;
		
		// prepare the environment
		removeMessagesFromDestinations();
		
		// prepare context
		//beginTransaction();
		
		// prepare mocks
		registerForResult();
		
		// start local handlers for responses from target service
		localSupplierBooksAvailableHandler = start_local_Supplier_BooksAvailable_handler();
		localSupplierBooksUnavailableHandler = start_local_Supplier_BooksUnavailable_handler();

		// start fixture execution
		queryBooksClient = start_QueryBooks_client();
		queryBooksClient.send(queryRequestMessage, correlationId, null);
		
		// wait for completion result
		Object result = waitForCompletion();
		validateResult(result);
		
		// close context
		//commitTransaction();
		
		// validate state
		assertEmptyTargetDestination();
	}
	
	protected void runAction_send_QueryBooks_cancel() throws Exception {
		expectedEvent = "Supplier_QueryBooks_Request_Cancel_Done";
		registerForResult(expectedEvent);
		
		QueryRequestMessage message = createQueryRequestCancelMessage();
		queryBooksClient.send(message, correlationId, null);
		
		Object result = waitForCompletion();
		validateResult(result);
	}
	
	protected void runAction_send_QueryBooks_undo() throws Exception {
		expectedEvent = "Supplier_QueryBooks_Request_Undo_Done";
		registerForResult(expectedEvent);
		
		QueryRequestMessage message = createQueryRequestUndoMessage();
		queryBooksClient.send(message, correlationId, null);
		
		Object result = waitForCompletion();
		validateResult(result);
	}
	
	public Thread start_runAction_send_QueryBooks() throws Exception {
		Thread thread = new Thread() {
			public void run() {
				try {
					runAction_send_QueryBooks();
				} catch (Exception e) {
					errorMessage = e.getMessage();
				}
			}
		};
		thread.start();
		Thread.sleep(4000);
		return thread;
	}
	
	protected JmsClient start_QueryBooks_client() throws Exception {
		JmsClient client = new QueryBooksProxyForJMS(QueryBooks.ID);
		configureClient(client, getTargetDestination());
		client.setCreateTemporaryQueue(true);
		client.initialize();
		return client;
	}
	
	protected JmsClient start_local_Supplier_BooksAvailable_handler() throws Exception {
		JmsClient client = createClient(getJNDINameForQueue(get_local_Supplier_BooksAvailable_destination()));
		client.setMessageListener(create_local_Supplier_BooksAvailable_response_listener());
		client.initialize();
		return client;
	}
	
	protected JmsClient start_local_Supplier_BooksUnavailable_handler() throws Exception {
		JmsClient client = createClient(getJNDINameForQueue(get_local_Supplier_BooksUnavailable_destination()));
		client.setMessageListener(create_local_Supplier_BooksUnavailable_response_listener());
		client.initialize();
		return client;
	}
	
	protected MessageListener create_local_Supplier_BooksAvailable_response_listener() {
		return new MessageListener() {
			public void onMessage(Message message) {
				try {
					String jmsMessageID = message.getJMSMessageID();
					log.info("#### [test]: BooksAvailable: received: "+jmsMessageID);
					Object object = MessageUtil.getPart(message, "booksAvailableMessage");
					Assert.isTrue(object instanceof BooksAvailableMessage, "Payload type not correct");
					BooksAvailableMessage booksAvailableMessage = (BooksAvailableMessage) object;
					validateMessage(booksAvailableMessage);
				} catch (Throwable e) {
					errorMessage = ExceptionUtils.getRootCauseMessage(e);
					e.printStackTrace();
				} finally {
					if (expectedMessage != null && expectedMessage.equalsIgnoreCase("bookshop2.supplier.BooksAvailable"))
						expectedMessageResult.set(true);
					booksAvailableReceived = true;
				}
			}
		};
	}
	
	protected MessageListener create_local_Supplier_BooksUnavailable_response_listener() {
		return new MessageListener() {
			public void onMessage(Message message) {
				try {
					String jmsMessageID = message.getJMSMessageID();
					log.info("#### [test]: BooksUnavailable: received: "+jmsMessageID);
					Object object = MessageUtil.getPart(message, "booksUnavailableMessage");
					Assert.isTrue(object instanceof BooksUnavailableMessage, "Payload type not correct");
					BooksUnavailableMessage booksUnavailableMessage = (BooksUnavailableMessage) object;
					validateMessage(booksUnavailableMessage);
				} catch (Throwable e) {
					errorMessage = ExceptionUtils.getRootCauseMessage(e);
					e.printStackTrace();
				} finally {
					if (expectedMessage != null && expectedMessage.equalsIgnoreCase("bookshop2.supplier.BooksUnavailable"))
						expectedMessageResult.set(true);
					booksUnavailableReceived = true;
				}
			}
		};
	}
	
	protected void validateResult(Object result) throws Exception {
		if (result instanceof BooksAvailableMessage) {
			validateMessage((BooksAvailableMessage) result);
		} else if (result instanceof BooksUnavailableMessage) {
			validateMessage((BooksUnavailableMessage) result);
		} else if (result instanceof Throwable) {
			Throwable exception = (Throwable) result;
			if (exceptionMessage != null && !exception.getMessage().equals(exceptionMessage))
				errorMessage = "Unexpected exception message: "+exception.getMessage();
		} else if (result instanceof String) {
			String resultString = result.toString();
			if ((expectedError != null && !expectedError.equalsIgnoreCase(resultString)) &&
				(expectedEvent != null && !expectedEvent.equalsIgnoreCase(resultString)))
					errorMessage = "Unexpected message: "+result;
		} else {
			errorMessage = "Unrecognized result: "+result;
		}
	}
	
	protected void validateMessage(BooksAvailableMessage booksAvailableMessage) throws Exception {
		Assert.notNull(booksAvailableMessage, "Message must be specified");
		Assert.notNull(booksAvailableMessage.getBooks(), "Books not found");
		if (this.booksAvailableMessage != null) {
			Bookshop2Fixture.assertSameBook(this.booksAvailableMessage.getBooks(), booksAvailableMessage.getBooks(), "Books is unequal");
		}
	}

	protected void validateMessage(BooksUnavailableMessage booksUnavailableMessage) throws Exception {
		Assert.notNull(booksUnavailableMessage, "Message must be specified");
		Assert.notNull(booksUnavailableMessage.getBooks(), "Books not found");
		if (this.booksUnavailableMessage != null) {
			Bookshop2Fixture.assertSameBook(this.booksUnavailableMessage.getBooks(), booksUnavailableMessage.getBooks(), "Books is unequal");
		}
	}
	
	@Test
	//@Ignore
	@InSequence(value = 1)
	public void runTest_QueryBooks_from_BooksAvailable_BooksInStock_Size_GT__0() throws Exception {
		String testName = "runTest_QueryBooks_from_BooksAvailable_BooksInStock_Size_GT__0";
		log.info(testName+": started");

		registerNotificationListeners();
		
		// prepare test data
		supplierOrderCacheHelper.assureRemoveAll();
		supplierOrderCacheHelper.addToBooksInStock(2);
		Bookshop2Fixture.reset();
		
		// setup expectations
		expectedEvent = "Supplier_QueryBooks_Request_Done";
		expectedMessage = "bookshop2.supplier.BooksAvailable";

		// setup response "BooksAvailable"
		booksAvailableMessage = Bookshop2Fixture.create_BooksAvailableMessage();
		
		// execute action
		Bookshop2Fixture.reset();
		runAction_send_QueryBooks();
		
		// validate request initiation state
		Assert.isTrue(isFiredRequestReceived("Supplier_QueryBooks"));
		
		
		// validate successful callback state
		Assert.isTrue(isFiredResponseSent("Supplier_BooksAvailable"));
		Assert.isTrue(booksAvailableReceived);

		// validate non-existent callback state
		Assert.isFalse(isFiredResponseSent("Supplier_BooksUnavailable"));
		Assert.isFalse(booksUnavailableReceived);

		// validate request completion state
		Assert.isTrue(isFiredRequestHandled("Supplier_QueryBooks"));
		Assert.isTrue(isFiredRequestDone("Supplier_QueryBooks"));

		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 2)
	public void runTest_QueryBooks_from_BooksUnavailable_BooksInStock_Size_EQ__0() throws Exception {
		String testName = "runTest_QueryBooks_from_BooksUnavailable_BooksInStock_Size_EQ__0";
		log.info(testName+": started");

		registerNotificationListeners();

		// prepare test data
		supplierOrderCacheHelper.assureRemoveAll();
		
		// setup expectations
		expectedEvent = "Supplier_QueryBooks_Request_Done";
		expectedMessage = "bookshop2.supplier.BooksUnavailable";
		
		// setup response "BooksUnavailable"
		booksUnavailableMessage = Bookshop2Fixture.create_BooksUnavailableMessage();
		
		// execute action
		Bookshop2Fixture.reset();
		runAction_send_QueryBooks();
		
		// validate request initiation state
		Assert.isTrue(isFiredRequestReceived("Supplier_QueryBooks"));

		
		// validate successful callback state
		Assert.isTrue(isFiredResponseSent("Supplier_BooksUnavailable"));
		Assert.isTrue(booksUnavailableReceived);

		// validate non-existent callback state
		Assert.isFalse(isFiredResponseSent("Supplier_BooksAvailable"));
		Assert.isFalse(booksAvailableReceived);

		// validate request completion state
		Assert.isTrue(isFiredRequestHandled("Supplier_QueryBooks"));
		Assert.isTrue(isFiredRequestDone("Supplier_QueryBooks"));

		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	@Ignore
	@InSequence(value = 3)
	@BytemanRule(name = "rule3", 
			targetClass = "QueryBooksHandlerImpl", 
			targetMethod = "queryBooks", 
			targetLocation = "AT ENTRY", 
			action = "$0.timeout = 0")
	public void runTest_QueryBooks_timeout() throws Exception {
		String testName = "runTest_QueryBooks_timeout";
		log.info(testName+": started");
		
		setupByteman(testName);
		registerNotificationListeners();
		
		// setup expectations
		expectedEvent = "Supplier_QueryBooks_Incoming_Request_Aborted";
		//TODO expectedMessage = "bookshop2.supplier.QueryRequest";
		
		try {
			// execute action
			Bookshop2Fixture.reset();
			runAction_send_QueryBooks();
			
			// validate request initiation state
			Assert.isTrue(isFiredRequestReceived("Supplier_QueryBooks"));
			
			// validate request non-completion state
			Assert.isFalse(isFiredRequestHandled("Supplier_QueryBooks"));
			Assert.isFalse(isFiredRequestDone("Supplier_QueryBooks"));
			
			// validate incoming request aborted state
			Assert.isTrue(isFiredIncomingRequestAborted("Supplier_QueryBooks"));
			
			if (errorMessage != null)
				fail(errorMessage);
			
		} finally {
			tearDownByteman(testName);
		}
	}
	
	@Test
	@Ignore
	@InSequence(value = 4)
	@BytemanRule(name = "rule4", 
			targetClass = "SupplierProcess", 
			targetMethod = "queryBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"exception message\")")
	public void runTest_QueryBooks_exception() throws Exception {
		String testName = "runTest_QueryBooks_exception";
		log.info(testName+": started");
		
		setupByteman(testName);
		registerNotificationListeners();
		
		// setup expectations
		expectedEvent = "Supplier_QueryBooks_Incoming_Request_Aborted";
		expectedMessage = "bookshop2.supplier.QueryRequest";
		exceptionMessage = "exception message";
		
		try {
			// execute action
			Bookshop2Fixture.reset();
			runAction_send_QueryBooks();
			
			// validate request initiation state
			Assert.isTrue(isFiredRequestReceived("Supplier_QueryBooks"));
			
			// validate request non-completion state
			Assert.isFalse(isFiredRequestHandled("Supplier_QueryBooks"));
			Assert.isFalse(isFiredRequestDone("Supplier_QueryBooks"));
			
			// validate incoming request aborted state
			Assert.isTrue(isFiredIncomingRequestAborted("Supplier_QueryBooks"));
		
			if (errorMessage != null)
				fail(errorMessage);
			
		} finally {
			tearDownByteman(testName);
		}
	}
	
}
