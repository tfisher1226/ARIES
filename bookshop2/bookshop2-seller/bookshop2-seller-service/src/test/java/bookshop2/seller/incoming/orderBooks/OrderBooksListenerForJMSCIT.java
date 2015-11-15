package bookshop2.seller.incoming.orderBooks;

import java.util.Set;

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

import bookshop2.Book;
import bookshop2.BooksAvailableMessage;
import bookshop2.BooksUnavailableMessage;
import bookshop2.OrderAcceptedMessage;
import bookshop2.OrderRejectedMessage;
import bookshop2.OrderRequestMessage;
import bookshop2.QueryRequestMessage;
import bookshop2.ReservationRequestMessage;
import bookshop2.seller.SellerTestEARBuilder;
import bookshop2.seller.client.orderBooks.OrderBooks;
import bookshop2.seller.client.orderBooks.OrderBooksProxyForJMS;
import bookshop2.seller.data.bookCache.BookCacheHelper;
import bookshop2.seller.data.bookCache.BookCacheProxy;
import bookshop2.util.Bookshop2Fixture;
import bookshop2.util.Bookshop2Helper;


@RunAsClient
@RunWith(Arquillian.class)
public class OrderBooksListenerForJMSCIT extends AbstractJMSListenerArquillionTest {
	
	private JmsClient orderBooksClient;
	
	private JmsClient remoteSupplierQueryBooksHandler;
	
	private JmsClient remoteSupplierBooksAvailableSender;
		
	private JmsClient remoteSupplierBooksUnavailableSender;
	
	private JmsClient remoteSupplierReserveBooksHandler;
	
	private JmsClient localSellerOrderAcceptedHandler;
	
	private JmsClient localSellerOrderRejectedHandler;
	
	private OrderRequestMessage orderRequestMessage;

	private OrderAcceptedMessage orderAcceptedMessage;
	
	private OrderRejectedMessage orderRejectedMessage;
	
	private QueryRequestMessage supplierQueryRequestMessage;

	private BooksAvailableMessage supplierBooksAvailableMessage;

	private BooksUnavailableMessage supplierBooksUnavailableMessage;
	
	private ReservationRequestMessage supplierReservationRequestMessage;
	
	private TransactionTestControl transactionTestControl;
	
	private BookCacheHelper bookCacheHelper;
	
	private boolean queryRequestReceived;
	
	private boolean reservationRequestReceived;
	
	private boolean orderAcceptedReceived;

	private boolean orderRejectedReceived;

	
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
		return OrderBooksListenerForJMSCIT.class;
	}
	
	@Override
	public String getServerName() {
		return "hornetQ01_local";
	}

	@Override
	public String getDomainId() {
		return "bookshop2.seller";
	}
	
	@Override
	public String getServiceId() {
		return "bookshop2.seller.orderBooks";
	}
	
	@Override
	public String getTargetArchive() {
		return SellerTestEARBuilder.NAME;
	}
	
	@Override
	public String getTargetDestination() {
		return get_target_Seller_OrderBooks_destination();
	}

	public String get_target_Seller_OrderBooks_destination() {
		return getJNDINameForQueue("protected_bookshop2_seller_order_books_queue");
	}

	public String get_remote_Supplier_QueryBooks_destination() {
		return getJNDINameForQueue("inventory_bookshop2_supplier_query_books_queue");
	}

	public String get_remote_Supplier_ReserveBooks_destination() {
		return getJNDINameForQueue("inventory_bookshop2_supplier_reserve_books_queue");
	}
	
	public String get_remote_Seller_BooksAvailable_destination() {
		return getJNDINameForQueue("protected_bookshop2_seller_books_available_queue");
	}

	public String get_remote_Seller_BooksUnavailable_destination() {
		return getJNDINameForQueue("protected_bookshop2_seller_books_unavailable_queue");
	}

	public String get_local_Seller_OrderAccepted_destination() {
		return "test_message_domain_test_destination_queue_a";
	}

	public String get_local_Seller_OrderRejected_destination() {
		return "test_message_domain_test_destination_queue_b";
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		startServer();
		createTransactionControl();
		createBookCacheHelper();
		remoteSupplierBooksAvailableSender = createClient(get_remote_Seller_BooksAvailable_destination());
		remoteSupplierBooksUnavailableSender = createClient(get_remote_Seller_BooksUnavailable_destination());
		Bookshop2Fixture.reset();
	}
	
	protected void createTransactionControl() throws Exception {
		transactionTestControl = new TransactionTestControl();
		transactionTestControl.setupTransactionManager();
	}
	
	public void createBookCacheHelper() throws Exception {
		bookCacheHelper = new BookCacheHelper();
		bookCacheHelper.setRunningAsClient(true);
		bookCacheHelper.setProxy(createBookCacheProxy());
		bookCacheHelper.initializeAsClient(createBookCacheControl());
	}
	
	protected BookCacheProxy createBookCacheProxy() throws Exception {
		BookCacheProxy bookCacheProxy = new BookCacheProxy();
		bookCacheProxy.setJmxManager(jmxManager);
		return bookCacheProxy;
	}
	
	protected CacheModuleTestControl createBookCacheControl() throws Exception {
		CacheModuleTestControl bookCacheControl = new CacheModuleTestControl(transactionTestControl);
		bookCacheControl.setupCacheLayer();
		return bookCacheControl;
	}
	
	public OrderRequestMessage createOrderRequestMessage() {
		return createOrderRequestMessage(false, false);
	}
	
	public OrderRequestMessage createOrderRequestCancelMessage() {
		return createOrderRequestMessage(true, false);
	}
	
	public OrderRequestMessage createOrderRequestUndoMessage() {
		return createOrderRequestMessage(false, true);
	}
	
	public OrderRequestMessage createOrderRequestMessage(boolean cancel, boolean undo) {
		OrderRequestMessage message = Bookshop2Fixture.create_OrderRequestMessage(cancel, undo);
		message.addToReplyToDestinations("OrderAccepted", get_local_Seller_OrderAccepted_destination());
		message.addToReplyToDestinations("OrderRejected", get_local_Seller_OrderRejected_destination());
		initializeMessage(message);
		return message;
	}
	
	public BooksAvailableMessage createBooksAvailableMessage() {
		BooksAvailableMessage message = Bookshop2Fixture.create_BooksAvailableMessage();
		initializeMessage(message);
		return message;
	}

	public BooksUnavailableMessage createBooksUnavailableMessage() {
		BooksUnavailableMessage message = Bookshop2Fixture.create_BooksUnavailableMessage();
		initializeMessage(message);
		return message;
	}
	
	protected void registerNotificationListeners() throws Exception {
		addRequestNotificationListeners("Seller_OrderBooks");
		addResponseNotificationListeners("Seller_OrderAccepted");
		addResponseNotificationListeners("Seller_OrderRejected");
		addRequestNotificationListeners("Supplier_QueryBooks");
		addResponseNotificationListeners("Supplier_BooksAvailable");
		addResponseNotificationListeners("Supplier_BooksUnavailable");
		addRequestNotificationListeners("Supplier_ReserveBooks");
		super.registerNotificationListeners();
	}
	
	@After
	public void tearDown() throws Exception {
		removeMessagesFromDestinations();
		clearStructures();
		clearState();
		super.tearDown();
	}
	
	protected void clearStructures() throws Exception {
		orderBooksClient.reset();
		orderBooksClient = null;

		//remote handler(s) for requests to remote (mocked) service(s)
		remoteSupplierQueryBooksHandler.reset();
		remoteSupplierQueryBooksHandler = null;
		remoteSupplierReserveBooksHandler.reset();
		remoteSupplierReserveBooksHandler = null;

		//remote clients for sending responses back from remote (mocked) service(s)
		remoteSupplierBooksAvailableSender.reset();
		remoteSupplierBooksAvailableSender = null;
		remoteSupplierBooksUnavailableSender.reset();
		remoteSupplierBooksUnavailableSender = null;

		//local handlers for responses from target service
		localSellerOrderAcceptedHandler.reset();
		localSellerOrderAcceptedHandler = null;
		localSellerOrderRejectedHandler.reset();
		localSellerOrderRejectedHandler = null;
		super.clearStructures();
	}

	protected void clearState() throws Exception {
		orderRequestMessage = null;
		orderAcceptedMessage = null;
		orderRejectedMessage = null;
		supplierQueryRequestMessage = null;
		supplierReservationRequestMessage = null;
		queryRequestReceived = false;
		reservationRequestReceived = false;
		orderAcceptedReceived = false;
		orderRejectedReceived = false;
		super.clearState();
	}
	
	protected void removeMessagesFromDestinations() throws Exception {
		removeMessagesFromQueue(getTargetArchive(), get_target_Seller_OrderBooks_destination());
		removeMessagesFromQueue(getTargetArchive(), get_local_Seller_OrderAccepted_destination());
		removeMessagesFromQueue(getTargetArchive(), get_local_Seller_OrderRejected_destination());
		removeMessagesFromQueue(getTargetArchive(), get_remote_Supplier_QueryBooks_destination());
		removeMessagesFromQueue(getTargetArchive(), get_remote_Seller_BooksAvailable_destination());
		removeMessagesFromQueue(getTargetArchive(), get_remote_Seller_BooksUnavailable_destination());
		removeMessagesFromQueue(getTargetArchive(), get_remote_Supplier_ReserveBooks_destination());
	}
	
	@TargetsContainer("hornetQ01_local")
	@Deployment(name = "sellerEAR", order = 2)
	public static EnterpriseArchive createSellerEAR() {
		SellerTestEARBuilder builder = new SellerTestEARBuilder();
		builder.setRunningAsClient(true);
		builder.setIncludeWar(true);
		return builder.createEAR();
	}
	
	public void runAction_send_OrderBooks() throws Exception {
		runAction_send_OrderBooks(createOrderRequestMessage());
	}
	
	public void runAction_send_OrderBooks(OrderRequestMessage orderRequestMessage) throws Exception {
		this.orderRequestMessage = orderRequestMessage;
		
		// prepare the environment
		removeMessagesFromDestinations();
		
		// prepare context
		//beginTransaction();
		
		// prepare mocks
		registerForResult();
		
		// start handlers for calls to remote service(s)
		remoteSupplierQueryBooksHandler = start_remote_Supplier_QueryBooks_handler();
		remoteSupplierReserveBooksHandler = start_remote_Supplier_ReserveBooks_handler();

		// start local handlers for responses from target service
		localSellerOrderAcceptedHandler = start_local_Seller_OrderAccepted_handler();
		localSellerOrderRejectedHandler = start_local_Seller_OrderRejected_handler();

		// start fixture execution
		orderBooksClient = start_OrderBooks_client();
		orderBooksClient.send(orderRequestMessage, correlationId, null);
		
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
	
	protected void runAction_send_OrderBooks_cancel() throws Exception {
		expectedEvent = "Seller_OrderBooks_Request_Cancel_Done";
		registerForResult(expectedEvent);

		OrderRequestMessage message = createOrderRequestCancelMessage();
		orderBooksClient.send(message, correlationId, null);

		Object result = waitForCompletion();
		validateResult(result);
	}

	protected void runAction_send_OrderBooks_undo() throws Exception {
		expectedEvent = "Seller_OrderBooks_Request_Undo_Done";
		registerForResult(expectedEvent);

		OrderRequestMessage message = createOrderRequestUndoMessage();
		orderBooksClient.send(message, correlationId, null);
		
		Object result = waitForCompletion();
		validateResult(result);
	}

	public Thread start_runAction_send_OrderBooks() throws Exception {
		Thread thread = new Thread() {
			public void run() {
				try {
					runAction_send_OrderBooks();
				} catch (Exception e) {
					errorMessage = e.getMessage();
				}
			}
		};
		thread.start();
		Thread.sleep(4000);
		return thread;
	}
	
	protected JmsClient start_OrderBooks_client() throws Exception {
		JmsClient client = new OrderBooksProxyForJMS(OrderBooks.ID);
		configureClient(client, getTargetDestination());
		client.setCreateTemporaryQueue(true);
		client.initialize();
		return client;
	}
	
	protected JmsClient start_remote_Supplier_QueryBooks_handler() throws Exception {
		JmsClient client = createClient(get_remote_Supplier_QueryBooks_destination());
		client.setMessageListener(create_remote_Supplier_QueryBooks_message_listener());
		client.initialize();
		return client;
	}
	
	protected JmsClient start_remote_Supplier_ReserveBooks_handler() throws Exception {
		JmsClient client = createClient(get_remote_Supplier_ReserveBooks_destination());
		client.setMessageListener(create_remote_Supplier_ReserveBooks_message_listener());
		client.initialize();
		return client;
	}
	
	protected JmsClient start_local_Seller_OrderAccepted_handler() throws Exception {
		JmsClient client = createClient(getJNDINameForQueue(get_local_Seller_OrderAccepted_destination()));
		client.setMessageListener(create_local_Seller_OrderAccepted_response_listener());
		client.initialize();
		return client;
	}
	
	protected JmsClient start_local_Seller_OrderRejected_handler() throws Exception {
		JmsClient client = createClient(getJNDINameForQueue(get_local_Seller_OrderRejected_destination()));
		client.setMessageListener(create_local_Seller_OrderRejected_response_listener());
		client.initialize();
		return client;
	}
	
	protected MessageListener create_remote_Supplier_QueryBooks_message_listener() {
		return new MessageListener() {
			public void onMessage(Message message) {
				try {
					String jmsMessageID = message.getJMSMessageID();
					log.info("#### [test]: QueryBooks: received: "+jmsMessageID);
					Object object = MessageUtil.getPart(message, "queryRequestMessage");
					Assert.isTrue(object instanceof QueryRequestMessage, "Payload type not correct");
					QueryRequestMessage supplierQueryRequestMessage = (QueryRequestMessage) object;
					switch (getActionFromMessage(supplierQueryRequestMessage)) {
					case CANCEL:
					case UNDO:
						break;
					default:
						validateMessage(supplierQueryRequestMessage);
						processMessage(supplierQueryRequestMessage);
						break;
					}
				} catch (Throwable e) {
					errorMessage = ExceptionUtils.getRootCauseMessage(e);
					e.printStackTrace();
				} finally {
					if (expectedMessage != null && expectedMessage.equalsIgnoreCase("bookshop2.supplier.QueryBooks"))
						expectedMessageResult.set(true);
					queryRequestReceived = true;
				}
			}
		};
	}
	
	protected void processMessage(QueryRequestMessage queryRequestMessage) throws Exception {
		if (expectedCallback == null)
			return;
		
		if (expectedCallback.equals("BooksAvailable")) {
			log.info("#### [test]: BooksAvailable: sending");
			initializeMessage(supplierBooksAvailableMessage, queryRequestMessage);
			remoteSupplierBooksAvailableSender.sendResponse(supplierBooksAvailableMessage);
		
		} else if (expectedCallback.equals("BooksUnavailable")) {
			log.info("#### [test]: BooksUnavailable: sending");
			initializeMessage(supplierBooksUnavailableMessage, queryRequestMessage);
			remoteSupplierBooksUnavailableSender.sendResponse(supplierBooksUnavailableMessage);
		}
	}

	protected MessageListener create_remote_Supplier_ReserveBooks_message_listener() {
		return new MessageListener() {
			public void onMessage(Message message) {
				try {
					String jmsMessageID = message.getJMSMessageID();
					log.info("#### [test]: ReserveBooks: received: "+jmsMessageID);
					Object object = MessageUtil.getPart(message, "reservationRequestMessage");
					Assert.isTrue(object instanceof ReservationRequestMessage, "Payload type not correct");
					ReservationRequestMessage supplierReservationRequestMessage = (ReservationRequestMessage) object;
					switch (getActionFromMessage(supplierReservationRequestMessage)) {
					case CANCEL:
					case UNDO:
						break;
					default:
						validateMessage(supplierReservationRequestMessage);
						break;
					}
				} catch (Throwable e) {
					errorMessage = ExceptionUtils.getRootCauseMessage(e);
					e.printStackTrace();
				} finally {
					if (expectedMessage != null && expectedMessage.equalsIgnoreCase("bookshop2.supplier.ReserveBooks"))
						expectedMessageResult.set(true);
						reservationRequestReceived = true;
				}
			}
		};
	}
	
	protected MessageListener create_local_Seller_OrderAccepted_response_listener() {
		return new MessageListener() {
			public void onMessage(Message message) {
				try {
					String jmsMessageID = message.getJMSMessageID();
					log.info("#### [test]: OrderAccepted: received: "+jmsMessageID);
					Object object = MessageUtil.getPart(message, "orderAcceptedMessage");
					Assert.isTrue(object instanceof OrderAcceptedMessage, "Payload type not correct");
					OrderAcceptedMessage orderAcceptedMessage = (OrderAcceptedMessage) object;
					validateMessage(orderAcceptedMessage);
				} catch (Throwable e) {
					errorMessage = ExceptionUtils.getRootCauseMessage(e);
					e.printStackTrace();
				} finally {
					if (expectedMessage != null && expectedMessage.equalsIgnoreCase("bookshop2.seller.OrderAccepted"))
						expectedMessageResult.set(true);
					orderAcceptedReceived = true;
				}
			}
		};
	}
	
	protected MessageListener create_local_Seller_OrderRejected_response_listener() {
		return new MessageListener() {
			public void onMessage(Message message) {
				try {
					String jmsMessageID = message.getJMSMessageID();
					log.info("#### [test]: OrderRejected: received: "+jmsMessageID);
					Object object = MessageUtil.getPart(message, "orderRejectedMessage");
					Assert.isTrue(object instanceof OrderRejectedMessage, "Payload type not correct");
					OrderRejectedMessage orderRejectedMessage = (OrderRejectedMessage) object;
					validateMessage(orderRejectedMessage);
				} catch (Throwable e) {
					errorMessage = ExceptionUtils.getRootCauseMessage(e);
					e.printStackTrace();
				} finally {
					if (expectedMessage != null && expectedMessage.equalsIgnoreCase("bookshop2.seller.OrderRejected"))
						expectedMessageResult.set(true);
					orderRejectedReceived = true;
				}
			}
		};
	}

	protected void validateResult(Object result) throws Exception {
		if (result instanceof OrderAcceptedMessage) {
			validateMessage((OrderAcceptedMessage) result);
		} else if (result instanceof OrderRejectedMessage) {
			validateMessage((OrderRejectedMessage) result);
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
	
	protected void validateMessage(QueryRequestMessage queryRequestMessage) throws Exception {
		Assert.notNull(queryRequestMessage, "Message must be specified");
		Assert.notNull(queryRequestMessage.getBooks(), "Books not found");
		if (this.supplierQueryRequestMessage != null) {
			Bookshop2Fixture.assertSameBook(this.supplierQueryRequestMessage.getBooks(), queryRequestMessage.getBooks(), "Books field is unequal");
		}
	}

	protected void validateMessage(ReservationRequestMessage reservationRequestMessage) throws Exception {
		Assert.notNull(reservationRequestMessage, "Message must be specified");
		Assert.notNull(reservationRequestMessage.getBooks(), "Books not found");
		if (this.supplierReservationRequestMessage != null) {
			Bookshop2Fixture.assertSameBook(this.supplierReservationRequestMessage.getBooks(), reservationRequestMessage.getBooks(), "Books field is unequal");
		}
	}
	
	protected void validateMessage(OrderAcceptedMessage orderAcceptedMessage) throws Exception {
		Assert.notNull(orderAcceptedMessage, "Message must be specified");
		Assert.notNull(orderAcceptedMessage.getOrder(), "Order not found");
		if (this.orderAcceptedMessage != null) {
			Bookshop2Fixture.assertSameOrder(this.orderAcceptedMessage.getOrder(), orderAcceptedMessage.getOrder(), "Order field is unequal");
		}
	}
	
	protected void validateMessage(OrderRejectedMessage orderRejectedMessage) throws Exception {
		Assert.notNull(orderRejectedMessage, "Message must be specified");
		Assert.notNull(orderRejectedMessage.getOrder(), "Order not found");
		if (this.orderRejectedMessage != null) {
			Assert.equals(this.orderRejectedMessage.getReason(), orderRejectedMessage.getReason(), "Reason field is unequal");
			Bookshop2Fixture.assertSameOrder(this.orderRejectedMessage.getOrder(), orderRejectedMessage.getOrder(), "Order field is unequal");
		}
	}

	@Test
	//@Ignore
	@InSequence(value = 1)
	public void runTest_OrderBooks_OrderAccepted_from_QueryBooks_BooksAvailable() throws Exception {
		String testName = "runTest_OrderBooks_OrderAccepted_from_QueryBooks_BooksAvailable";
		log.info(testName+": started");
		
		registerNotificationListeners();
		
		// prepare test data
		bookCacheHelper.assureRemoveAll();
		
		// setup expectations
		expectedEvent = "Seller_OrderBooks_Request_Done";
		expectedMessage = "bookshop2.seller.OrderAccepted";
		orderRequestMessage = createOrderRequestMessage();
		
		// setup expected callback response
		expectedCallback = "BooksAvailable";
		supplierBooksAvailableMessage = createBooksAvailableMessage();
		
		// setup mock response "supplier.queryBooks"
		Bookshop2Fixture.reset();
		orderAcceptedMessage = Bookshop2Fixture.create_OrderAcceptedMessage();
		orderAcceptedMessage.getOrder().setBooks(supplierBooksAvailableMessage.getBooks());		

		// execute action
		Bookshop2Fixture.reset();
		runAction_send_OrderBooks(orderRequestMessage);

		// validate request initiation state
		Assert.isTrue(isFiredRequestReceived("Seller_OrderBooks"));

		// validate remote QueryBooks interaction
		Assert.isTrue(isFiredRequestSent("Supplier_QueryBooks"));
		Assert.isTrue(isFiredResponseDone("Supplier_BooksAvailable"));
		Assert.isTrue(queryRequestReceived);

		// validate remote ReserveBooks interaction
		Assert.isTrue(isFiredRequestSent("Supplier_ReserveBooks"));
		Assert.isTrue(reservationRequestReceived);
		
		// validate successful callback state
		Assert.isTrue(isFiredResponseSent("Seller_OrderAccepted"));
		Assert.isTrue(orderAcceptedReceived);

		// validate non-existent callback state
		Assert.isFalse(isFiredResponseSent("Seller_OrderRejected"));
		Assert.isFalse(orderRejectedReceived);
		
		// validate request completion state
		Assert.isTrue(isFiredRequestHandled("Seller_OrderBooks"));
		Assert.isTrue(isFiredRequestDone("Seller_OrderBooks"));
		
		// validate bookCache.addToAvailableBooks state
		Set<Book> bookSet = bookCacheHelper.getAllAvailableBooks();
		Set<Book> bookSetToAdd = supplierBooksAvailableMessage.getBooks();
		Bookshop2Fixture.assertSameBook(bookSetToAdd, bookSet);
		bookCacheHelper.verifyAvailableBooksCount(bookSetToAdd.size());
		
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 2)
	public void runTest_OrderBooks_OrderAccepted_from_QueryBooks_BooksAvailable_cancel() throws Exception {
		String testName = "runTest_OrderBooks_OrderAccepted_from_QueryBooks_BooksAvailable_cancel";
		log.info(testName+": started");
		
		registerNotificationListeners();
		
		// prepare test data
		bookCacheHelper.assureRemoveAll();
		
		// setup expectations
		expectedEvent = "Seller_OrderBooks_Request_Done";
		expectedMessage = "bookshop2.seller.OrderAccepted";
		
		// setup mock response
		expectedCallback = "BooksAvailable";
		supplierBooksAvailableMessage = createBooksAvailableMessage();
		orderAcceptedMessage = Bookshop2Fixture.create_OrderAcceptedMessage();
		
		// execute action
		Bookshop2Fixture.reset();
		runAction_send_OrderBooks();

		// clear message queues
		removeMessagesFromDestinations();

		// execute "cancel" of action
		runAction_send_OrderBooks_cancel();
		
		// validate request initiation state
		Assert.isTrue(isFiredRequestReceived("Seller_OrderBooks"));

		// validate remote QueryBooks interaction
		Assert.isTrue(isFiredRequestSent("Supplier_QueryBooks"));
		Assert.isTrue(isFiredResponseDone("Supplier_BooksAvailable"));
		Assert.isTrue(queryRequestReceived);

		// validate remote ReserveBooks interaction
		Assert.isTrue(isFiredRequestSent("Supplier_ReserveBooks"));	
		Assert.isTrue(reservationRequestReceived);
		
		// validate successful callback state
		Assert.isTrue(isFiredResponseSent("Seller_OrderAccepted"));
		Assert.isTrue(orderAcceptedReceived);

		// validate non-existent callback state
		Assert.isFalse(isFiredResponseSent("Seller_OrderRejected"));
		Assert.isFalse(orderRejectedReceived);
		
		// validate request cancellation state
		Assert.isTrue(isFiredRequestCancelled("Seller_OrderBooks"));
		Assert.isTrue(isFiredRequestRolledBack("Seller_OrderBooks"));

		// validate empty BookCache state
		bookCacheHelper.verifyAvailableBooksCount(0);

		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 3)
	public void runTest_OrderBooks_OrderAccepted_from_QueryBooks_BooksAvailable_undo() throws Exception {
		String testName = "runTest_OrderBooks_OrderAccepted_from_QueryBooks_BooksAvailable_undo";
		log.info(testName+": started");
		
		registerNotificationListeners();
		
		// prepare test data
		bookCacheHelper.assureRemoveAll();
		
		// setup expectations
		expectedEvent = "Seller_OrderBooks_Request_Done";
		expectedMessage = "bookshop2.seller.OrderAccepted";
		
		// setup mock response
		expectedCallback = "BooksAvailable";
		supplierBooksAvailableMessage = createBooksAvailableMessage();
		orderAcceptedMessage = Bookshop2Fixture.create_OrderAcceptedMessage();
		
		// execute action
		runAction_send_OrderBooks();
		
		removeMessagesFromDestinations();

		// execute "undo" of action
		runAction_send_OrderBooks_undo();
		
		// validate request initiation state
		Assert.isTrue(isFiredRequestReceived("Seller_OrderBooks"));

		// validate remote QueryBooks interaction
		Assert.isTrue(isFiredRequestSent("Supplier_QueryBooks"));
		Assert.isTrue(isFiredResponseDone("Supplier_BooksAvailable"));
		Assert.isTrue(queryRequestReceived);

		// validate remote ReserveBooks interaction
		Assert.isTrue(isFiredRequestSent("Supplier_ReserveBooks"));	
		Assert.isTrue(reservationRequestReceived);
		
		// validate successful callback state
		Assert.isTrue(isFiredResponseSent("Seller_OrderAccepted"));
		Assert.isTrue(orderAcceptedReceived);

		// validate non-existent callback state
		Assert.isFalse(isFiredResponseSent("Seller_OrderRejected"));
		Assert.isFalse(orderRejectedReceived);
		
		// validate request rolledback state
		Assert.isTrue(isFiredRequestRolledBack("Seller_OrderBooks"));

		// validate empty BookCache state
		bookCacheHelper.verifyAvailableBooksCount(0);
		
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 4)
	public void runTest_OrderBooks_OrderRejected_from_QueryBooks_BooksUnavailable() throws Exception {
		String testName = "runTest_OrderBooks_OrderRejected_from_QueryBooks_BooksUnavailable";
		log.info(testName+": started");
		
		registerNotificationListeners();
		
		// prepare test data
		bookCacheHelper.assureRemoveAll();
		
		// setup expectations
		expectedEvent = "Seller_OrderBooks_Request_Done";
		expectedMessage = "bookshop2.seller.OrderRejected";

		// setup expected callback response
		expectedCallback = "BooksUnavailable";
		supplierBooksUnavailableMessage = createBooksUnavailableMessage();
		
		// setup mock response "supplier.queryBooks"
		orderRejectedMessage = Bookshop2Fixture.create_OrderRejectedMessage();
		orderRejectedMessage.setReason("Books Unavailable");

		// execute action
		Bookshop2Fixture.reset();
		runAction_send_OrderBooks();
		
		// validate request initiation state
		Assert.isTrue(isFiredRequestReceived("Seller_OrderBooks"));

		// validate remote QueryBooks interaction
		Assert.isTrue(isFiredRequestSent("Supplier_QueryBooks"));
		Assert.isTrue(isFiredResponseDone("Supplier_BooksUnavailable"));
		Assert.isTrue(queryRequestReceived);

		// validate remote ReserveBooks interaction
		Assert.isFalse(isFiredRequestSent("Supplier_ReserveBooks"));	
		Assert.isFalse(reservationRequestReceived);

		// validate successful callback state
		Assert.isTrue(isFiredResponseSent("Seller_OrderRejected"));
		Assert.isTrue(orderRejectedReceived);
		
		// validate non-existent callback state
		Assert.isFalse(isFiredResponseSent("Seller_OrderAccepted"));
		Assert.isFalse(orderAcceptedReceived);
		
		// validate request completion state
		Assert.isTrue(isFiredRequestHandled("Seller_OrderBooks"));
		Assert.isTrue(isFiredRequestDone("Seller_OrderBooks"));

		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 5)
	public void runTest_OrderBooks_OrderRejected_from_QueryBooks_BooksUnavailable_cancel() throws Exception {
		String testName = "runTest_OrderBooks_OrderRejected_from_QueryBooks_BooksUnavailable_cancel";
		log.info(testName+": started");
		
		registerNotificationListeners();
		
		// prepare test data
		bookCacheHelper.assureRemoveAll();
		
		// setup expectations
		expectedEvent = "Seller_OrderBooks_Request_Done";
		expectedMessage = "bookshop2.seller.OrderRejected";
		
		// setup mock response
		expectedCallback = "BooksUnavailable";
		supplierBooksUnavailableMessage = createBooksUnavailableMessage();
		orderRejectedMessage = Bookshop2Fixture.create_OrderRejectedMessage();
		orderRejectedMessage.setReason("Books Unavailable");
		
		// execute action
		Bookshop2Fixture.reset();
		runAction_send_OrderBooks();

		// clear message queues
		removeMessagesFromDestinations();

		// execute "cancel" of action
		runAction_send_OrderBooks_cancel();
		
		// validate request initiation state
		Assert.isTrue(isFiredRequestReceived("Seller_OrderBooks"));

		// validate remote QueryBooks interaction
		Assert.isTrue(isFiredRequestSent("Supplier_QueryBooks"));
		Assert.isTrue(isFiredResponseDone("Supplier_BooksUnavailable"));
		Assert.isTrue(queryRequestReceived);

		// validate remote ReserveBooks interaction
		Assert.isFalse(isFiredRequestSent("Supplier_ReserveBooks"));
		Assert.isFalse(reservationRequestReceived);
		
		// validate successful callback state
		Assert.isTrue(isFiredResponseSent("Seller_OrderRejected"));
		Assert.isTrue(orderRejectedReceived);
		
		// validate non-existent callback state
		Assert.isFalse(isFiredResponseSent("Seller_OrderAccepted"));
		Assert.isFalse(orderAcceptedReceived);

		// validate request cancellation state
		Assert.isTrue(isFiredRequestCancelled("Seller_OrderBooks"));
		Assert.isTrue(isFiredRequestRolledBack("Seller_OrderBooks"));

		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 6)
	public void runTest_OrderBooks_OrderRejected_from_QueryBooks_BooksUnavailable_undo() throws Exception {
		String testName = "runTest_OrderBooks_OrderRejected_from_QueryBooks_BooksUnavailable_undo";
		log.info(testName+": started");
		
		registerNotificationListeners();
		
		// prepare test data
		bookCacheHelper.assureRemoveAll();
		
		// setup expectations
		expectedEvent = "Seller_OrderBooks_Request_Done";
		expectedMessage = "bookshop2.seller.OrderRejected";
		
		// setup mock response
		expectedCallback = "BooksUnavailable";
		supplierBooksUnavailableMessage = createBooksUnavailableMessage();
		orderRejectedMessage = Bookshop2Fixture.create_OrderRejectedMessage();
		orderRejectedMessage.setReason("Books Unavailable");
		
		// execute action
		Bookshop2Fixture.reset();
		runAction_send_OrderBooks();
		
		removeMessagesFromDestinations();

		// execute "undo" of action
		runAction_send_OrderBooks_undo();
		
		// validate request initiation state
		Assert.isTrue(isFiredRequestReceived("Seller_OrderBooks"));

		// validate remote QueryBooks interaction
		Assert.isTrue(isFiredRequestSent("Supplier_QueryBooks"));
		Assert.isTrue(isFiredResponseDone("Supplier_BooksUnavailable"));
		Assert.isTrue(queryRequestReceived);

		// validate remote ReserveBooks interaction
		Assert.isFalse(isFiredRequestSent("Supplier_ReserveBooks"));	
		Assert.isFalse(reservationRequestReceived);
		
		// validate successful callback state
		Assert.isTrue(isFiredResponseSent("Seller_OrderRejected"));
		Assert.isTrue(orderRejectedReceived);
		
		// validate non-existent callback state
		Assert.isFalse(isFiredResponseSent("Seller_OrderAccepted"));
		Assert.isFalse(orderAcceptedReceived);

		// validate request rolledback state
		Assert.isTrue(isFiredRequestRolledBack("Seller_OrderBooks"));

		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 7)
	@BytemanRule(name = "rule7",
		targetClass = "SellerProcess",
		targetMethod = "send_Supplier_QueryBooks_request",
		targetLocation = "AT ENTRY",
		action = "$0.queryBooksTimeout = 0")
	public void runTest_OrderBooks_OrderRejected_from_QueryBooks_timeout() throws Exception {
		String testName = "runTest_OrderBooks_OrderRejected_from_QueryBooks_timeout";
		log.info(testName+": started");
		
		setupByteman(testName);
		registerNotificationListeners();
		
		// prepare test data
		bookCacheHelper.assureRemoveAll();
		
		// setup expectations
		expectedEvent = "Supplier_QueryBooks_Outgoing_Request_Aborted";
		expectedMessage = "bookshop2.seller.OrderRejected";
		
		// setup mock response
		orderRejectedMessage = Bookshop2Fixture.create_OrderRejectedMessage();
		orderRejectedMessage.setReason("QueryBooks timed-out");
		
		try {
		// execute action
			Bookshop2Fixture.reset();
			runAction_send_OrderBooks();
		
			// validate request initiation state
			Assert.isTrue(isFiredRequestReceived("Seller_OrderBooks"));
			
			// validate remote QueryBooks interaction
			Assert.isTrue(isFiredRequestSent("Supplier_QueryBooks"));
			Assert.isTrue(queryRequestReceived);
			
			// validate remote ReserveBooks interaction
			Assert.isFalse(isFiredRequestSent("Supplier_ReserveBooks"));
			Assert.isFalse(reservationRequestReceived);
			
			// validate successful callback state
			Assert.isTrue(isFiredResponseSent("Seller_OrderRejected"));
			Assert.isTrue(orderRejectedReceived);
			
			// validate non-existent callback state
			Assert.isFalse(isFiredResponseSent("Seller_OrderAccepted"));
			Assert.isFalse(orderAcceptedReceived);
			
			// validate outgoing request aborted state
			Assert.isTrue(isFiredOutgoingRequestAborted("Supplier_QueryBooks"));
			
			// validate request completion state
			Assert.isTrue(isFiredRequestHandled("Seller_OrderBooks"));
			Assert.isTrue(isFiredRequestDone("Seller_OrderBooks"));
		
			if (errorMessage != null)
				fail(errorMessage);
			
		} finally {
			tearDownByteman(testName);
		}
	}
	
	@Test
	//@Ignore
	@InSequence(value = 8)
	@BytemanRule(name = "rule8",
		targetClass = "SellerProcess",
		targetMethod = "fire_QueryBooks_request_sent",
		targetLocation = "AT EXIT",
		action = "throw new java.lang.RuntimeException(\"exception message\")")
	public void runTest_OrderBooks_OrderRejected_from_QueryBooks_exception() throws Exception {
		String testName = "runTest_OrderBooks_OrderRejected_from_QueryBooks_exception";
		log.info(testName+": started");
		
		setupByteman(testName);
		registerNotificationListeners();
		
		// prepare test data
		bookCacheHelper.assureRemoveAll();
		
		// setup expectations
		expectedEvent = "Supplier_QueryBooks_Outgoing_Request_Aborted";
		expectedMessage = "bookshop2.seller.OrderRejected";
		exceptionMessage = "exception message";
		
		// setup mock response
		orderRejectedMessage = Bookshop2Fixture.create_OrderRejectedMessage();
		orderRejectedMessage.setReason("exception message");
		
		try {
		// execute action
			Bookshop2Fixture.reset();
			runAction_send_OrderBooks();
		
			// validate request initiation state
			Assert.isTrue(isFiredRequestReceived("Seller_OrderBooks"));
			
			// validate remote QueryBooks interaction
			Assert.isTrue(isFiredRequestSent("Supplier_QueryBooks"));
			Assert.isTrue(queryRequestReceived);
			
			// validate remote ReserveBooks interaction
			Assert.isFalse(isFiredRequestSent("Supplier_ReserveBooks"));	
			Assert.isFalse(reservationRequestReceived);
			
			// validate successful callback state
			Assert.isTrue(isFiredResponseSent("Seller_OrderRejected"));
			Assert.isTrue(orderRejectedReceived);
			
			// validate non-existent callback state
			Assert.isFalse(isFiredResponseSent("Seller_OrderAccepted"));
			Assert.isFalse(orderAcceptedReceived);
			
			// validate outgoing request aborted state
			Assert.isTrue(isFiredOutgoingRequestAborted("Supplier_QueryBooks"));
			
			// validate request completion state
			Assert.isTrue(isFiredRequestHandled("Seller_OrderBooks"));
			Assert.isTrue(isFiredRequestDone("Seller_OrderBooks"));

			if (errorMessage != null)
				fail(errorMessage);
			
		} finally {
			tearDownByteman(testName);
		}
	}
	
	@Test
	@Ignore
	@InSequence(value = 9)
	@BytemanRule(name = "rule9",
		targetClass = "OrderBooksHandlerImpl",
		targetMethod = "orderBooks",
		targetLocation = "AT ENTRY",
		action = "$0.timeout = 0")
	public void runTest_OrderBooks_timeout() throws Exception {
		String testName = "runTest_OrderBooks_timeout";
		log.info(testName+": started");
		
		setupByteman(testName);
		registerNotificationListeners();
		
		// setup expectations
		expectedEvent = "Seller_OrderBooks_Incoming_Request_Aborted";
		//TODO expectedMessage = "bookshop2.seller.OrderRequest";
		
		try {
			// execute action
			Bookshop2Fixture.reset();
			runAction_send_OrderBooks();
		
			// validate request initiation state
			Assert.isTrue(isFiredRequestReceived("Seller_OrderBooks"));
			
			// validate request non-completion state
			Assert.isFalse(isFiredRequestHandled("Seller_OrderBooks"));
			Assert.isFalse(isFiredRequestDone("Seller_OrderBooks"));
			
			// validate incoming request aborted state
			Assert.isTrue(isFiredIncomingRequestAborted("Seller_OrderBooks"));
			
			if (errorMessage != null)
				fail(errorMessage);
			
		} finally {
			tearDownByteman(testName);
		}
	}
	
	@Test
	@Ignore
	@InSequence(value = 10)
	@BytemanRule(name = "rule10", 
			targetClass = "SellerProcess", 
			targetMethod = "orderBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"exception message\")")
	public void runTest_OrderBooks_exception() throws Exception {
		String testName = "runTest_OrderBooks_exception";
		log.info(testName+": started");
		
		setupByteman(testName);
		registerNotificationListeners();
		
		// setup expectations
		expectedEvent = "Seller_OrderBooks_Incoming_Request_Aborted";
		expectedMessage = "bookshop2.seller.OrderRequest";
		exceptionMessage = "exception message";
		
		try {
		// execute action
			Bookshop2Fixture.reset();
			runAction_send_OrderBooks();
		
			// validate request initiation state
			Assert.isTrue(isFiredRequestReceived("Seller_OrderBooks"));
			
			// validate request non-completion state
			Assert.isFalse(isFiredRequestHandled("Seller_OrderBooks"));
			Assert.isFalse(isFiredRequestDone("Seller_OrderBooks"));
			
			// validate incoming request aborted state
			Assert.isTrue(isFiredIncomingRequestAborted("Seller_OrderBooks"));
			
			if (errorMessage != null)
				fail(errorMessage);
			
		} finally {
			tearDownByteman(testName);
		}
	}
	
}
