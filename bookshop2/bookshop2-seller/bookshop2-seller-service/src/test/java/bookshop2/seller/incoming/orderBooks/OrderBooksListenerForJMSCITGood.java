package bookshop2.seller.incoming.orderBooks;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.aries.Assert;
import org.aries.jms.client.JmsClient;
import org.aries.jms.util.MessageUtil;
import org.aries.tx.AbstractArquillianTest;
import org.aries.tx.AbstractJMSListenerArquillionTest;
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
import org.junit.Test;
import org.junit.runner.RunWith;

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
import bookshop2.util.Bookshop2Fixture;


@RunAsClient
@RunWith(Arquillian.class)
public class OrderBooksListenerForJMSCITGood extends AbstractJMSListenerArquillionTest {
	
	private TransactionTestControl transactionTestControl;
	
	private JmsClient orderBooksClient;
	
	private JmsClient remoteQueryBooksHandler;
	
	private JmsClient remoteBooksAvailableSender;
		
	private JmsClient remoteBooksUnavailableSender;
	
	private JmsClient remoteReserveBooksHandler;
	
	private JmsClient localOrderAcceptedHandler;
	
	private JmsClient localOrderRejectedHandler;
	
	private OrderRequestMessage orderRequestMessage;

	private OrderAcceptedMessage orderAcceptedMessage;
	
	private OrderRejectedMessage orderRejectedMessage;
	
	private QueryRequestMessage queryRequestMessage;

	private BooksAvailableMessage booksAvailableMessage;

	private BooksUnavailableMessage booksUnavailableMessage;
	
	private ReservationRequestMessage reservationRequestMessage;
	
	private boolean queryRequestReceived;
	
	private boolean reservationRequestReceived;
	
	private boolean orderAcceptedReceived;

	private boolean orderRejectedReceived;

	private String orderRejectedReason;

	
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
		return OrderBooksListenerForJMSCITGood.class;
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
		return getJNDINameForQueue("protected_bookshop2_seller_order_books_queue");
	}

	public String getRemoteQueryBooksDestination() {
		return getJNDINameForQueue("inventory_bookshop2_supplier_query_books_queue");
	}

	public String getRemoteReserveBooksDestination() {
		return getJNDINameForQueue("inventory_bookshop2_supplier_reserve_books_queue");
	}
	
	public String getRemoteBooksAvailableDestination() {
		return getJNDINameForQueue("protected_bookshop2_seller_books_available_queue");
	}

	public String getRemoteBooksUnavailableDestination() {
		return getJNDINameForQueue("protected_bookshop2_seller_books_unavailable_queue");
	}

	public String getLocalOrderAcceptedDestination() {
		return "test_message_domain_test_destination_queue_a";
	}

	public String getLocalOrderRejectedDestination() {
		return "test_message_domain_test_destination_queue_b";
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		startServer();
		createTransactionControl();
		remoteBooksAvailableSender = createClient(getRemoteBooksAvailableDestination());
		remoteBooksUnavailableSender = createClient(getRemoteBooksUnavailableDestination());
	}
	
	protected void createTransactionControl() throws Exception {
		transactionTestControl = new TransactionTestControl();
		transactionTestControl.setupTransactionManager();
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
		message.addToReplyToDestinations("OrderAccepted", getLocalOrderAcceptedDestination());
		message.addToReplyToDestinations("OrderRejected", getLocalOrderRejectedDestination());
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
		addRequestNotificationListeners("OrderBooks");
		addResponseNotificationListeners("OrderAccepted");
		addResponseNotificationListeners("OrderRejected");
	}
	
	@After
	public void tearDown() throws Exception {
		clearStructures();
		clearState();
		super.tearDown();
	}
	
	protected void clearStructures() throws Exception {
		orderBooksClient.reset();
		orderBooksClient = null;

		//remote handler(s) for requests to remote (mocked) service(s)
		remoteQueryBooksHandler.reset();
		remoteQueryBooksHandler = null;
		remoteReserveBooksHandler.reset();
		remoteReserveBooksHandler = null;

		//remote clients for sending responses back from remote (mocked) service(s)
		remoteBooksAvailableSender.reset();
		remoteBooksAvailableSender = null;
		remoteBooksUnavailableSender.reset();
		remoteBooksUnavailableSender = null;

		//local handlers for responses from target service
		localOrderAcceptedHandler.reset();
		localOrderAcceptedHandler = null;
		localOrderRejectedHandler.reset();
		localOrderRejectedHandler = null;
		super.clearStructures();
	}

	protected void clearState() throws Exception {
		orderRequestMessage = null;
		orderAcceptedMessage = null;
		orderRejectedMessage = null;
		queryRequestMessage = null;
		booksAvailableMessage = null;
		booksUnavailableMessage = null;
		reservationRequestMessage = null;
		orderAcceptedReceived = false;
		orderRejectedReceived = false;
		queryRequestReceived = false;
		reservationRequestReceived = false;
		orderRejectedReason = null;
		super.clearState();
	}
	
	protected void removeMessagesFromDestinations() throws Exception {
		removeMessagesFromQueue(getTargetArchive(), getTargetDestination());
		removeMessagesFromQueue(getTargetArchive(), getRemoteQueryBooksDestination());
		removeMessagesFromQueue(getTargetArchive(), getRemoteBooksAvailableDestination());
		removeMessagesFromQueue(getTargetArchive(), getRemoteBooksUnavailableDestination());
		removeMessagesFromQueue(getTargetArchive(), getRemoteReserveBooksDestination());
		removeMessagesFromQueue(getTargetArchive(), getLocalOrderAcceptedDestination());
		removeMessagesFromQueue(getTargetArchive(), getLocalOrderRejectedDestination());
	}
	
	@TargetsContainer("hornetQ01_local")
	@Deployment(name = "sellerEAR", order = 2)
	public static EnterpriseArchive createTestEAR() {
		SellerTestEARBuilder builder = new SellerTestEARBuilder();
		builder.setIncludeWar(true);
		return builder.createEAR();
	}
	
	public void runTest_OrderBooks() throws Exception {
		runTest_OrderBooks(createOrderRequestMessage());
	}
	
	public void runTest_OrderBooks(OrderRequestMessage orderRequestMessage) throws Exception {
		
		// prepare the environment
		removeMessagesFromDestinations();
		
		// prepare context
		//beginTransaction();
		
		// prepare mocks
		registerForResult();
		
		// start handlers for calls to remote service(s)
		remoteQueryBooksHandler = startRemoteQueryBooksHandler();
		remoteReserveBooksHandler = startRemoteReserveBooksHandler();

		// start local handlers for responses from target service
		localOrderAcceptedHandler = startLocalOrderAcceptedHandler();
		localOrderRejectedHandler = startLocalOrderRejectedHandler();

		// start fixture execution
		orderBooksClient = startOrderBooksClient();
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
	
	protected JmsClient startOrderBooksClient() throws Exception {
		JmsClient client = new OrderBooksProxyForJMS(OrderBooks.ID);
		configureClient(client, getTargetDestination());
		client.setCreateTemporaryQueue(true);
		client.initialize();
		return client;
	}
	
	protected JmsClient startRemoteQueryBooksHandler() throws Exception {
		JmsClient client = createClient(getRemoteQueryBooksDestination());
		client.setMessageListener(createRemoteQueryBooksListener());
		client.initialize();
		return client;
	}
	
	protected JmsClient startRemoteReserveBooksHandler() throws Exception {
		JmsClient client = createClient(getRemoteReserveBooksDestination());
		client.setMessageListener(createRemoteReserveBooksListener());
		client.initialize();
		return client;
	}
	
	protected JmsClient startLocalOrderAcceptedHandler() throws Exception {
		JmsClient client = createClient(getJNDINameForQueue(getLocalOrderAcceptedDestination()));
		client.setMessageListener(createLocalOrderAcceptedListener());
		client.initialize();
		return client;
	}
	
	protected JmsClient startLocalOrderRejectedHandler() throws Exception {
		JmsClient client = createClient(getJNDINameForQueue(getLocalOrderRejectedDestination()));
		client.setMessageListener(createLocalOrderRejectedListener());
		client.initialize();
		return client;
	}
	
	protected MessageListener createRemoteQueryBooksListener() {
		return new MessageListener() {
			public void onMessage(Message message) {
				try {
					log.info("#### [test]: QueryBooks: received");
					Object object = MessageUtil.getPart(message, "queryRequestMessage");
					Assert.isTrue(object instanceof QueryRequestMessage, "Payload type not correct");
					queryRequestMessage = (QueryRequestMessage) object;
					validateMessage(queryRequestMessage);
					processMessage(queryRequestMessage);
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
		if (expectedCallback.equals("BooksAvailable")) {
			log.info("#### [test]: BooksAvailable: sending");
			remoteBooksAvailableSender.sendResponse(booksAvailableMessage);
		
		} else if (expectedCallback.equals("BooksUnavailable")) {
			log.info("#### [test]: BooksUnavailable: sending");
			remoteBooksUnavailableSender.sendResponse(booksUnavailableMessage);
		}
	}

	protected MessageListener createRemoteReserveBooksListener() {
		return new MessageListener() {
			public void onMessage(Message message) {
				try {
					log.info("#### [test]: ReserveBooks: received");
					Object object = MessageUtil.getPart(message, "reservationRequestMessage");
					Assert.isTrue(object instanceof ReservationRequestMessage, "Payload type not correct");
					reservationRequestMessage = (ReservationRequestMessage) object;
					validateMessage(reservationRequestMessage);
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
	
	protected MessageListener createLocalOrderAcceptedListener() {
		return new MessageListener() {
			public void onMessage(Message message) {
				try {
					log.info("#### [test]: OrderAccepted: received");
					Object object = MessageUtil.getPart(message, "orderAcceptedMessage");
					Assert.isTrue(object instanceof OrderAcceptedMessage, "Payload type not correct");
					orderAcceptedMessage = (OrderAcceptedMessage) object;
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
	
	protected MessageListener createLocalOrderRejectedListener() {
		return new MessageListener() {
			public void onMessage(Message message) {
				try {
					log.info("#### [test]: OrderRejected: received");
					Object object = MessageUtil.getPart(message, "orderRejectedMessage");
					Assert.isTrue(object instanceof OrderRejectedMessage, "Payload type not correct");
					orderRejectedMessage = (OrderRejectedMessage) object;
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
		Bookshop2Fixture.assertBookCorrect(queryRequestMessage.getBooks());
	}

	protected void validateMessage(ReservationRequestMessage reservationRequestMessage) throws Exception {
		Assert.notNull(reservationRequestMessage, "Message must be specified");
		Bookshop2Fixture.assertBookCorrect(reservationRequestMessage.getBooks());
	}
	
	protected void validateMessage(OrderAcceptedMessage orderAcceptedMessage) throws Exception {
		Assert.notNull(orderAcceptedMessage, "Message must be specified");
		Bookshop2Fixture.assertOrderCorrect(orderAcceptedMessage.getOrder());
	}
	
	protected void validateMessage(OrderRejectedMessage orderRejectedMessage) throws Exception {
		Assert.notNull(orderRejectedMessage, "Message must be specified");
		Bookshop2Fixture.assertOrderCorrect(orderRejectedMessage.getOrder());
		Assert.equals(orderRejectedMessage.getReason(), orderRejectedReason);
		//Bookshop2Helper.assertBookListEquals(list1, list2);
	}

	@Test
	//@Ignore
	@InSequence(value = 1)
	public void test_OrderBooks_OrderAccepted_From_Response() throws Exception {
		String testName = "test_OrderBooks_OrderAccepted_From_Response";
		log.info(testName+": started");
		
		registerNotificationListeners();
		expectedEvent = "bookshop2.seller.OrderBooks_Request_Done";
		expectedMessage = "bookshop2.seller.OrderAccepted";
		expectedCallback = "BooksAvailable";

		// setup mock response
		booksAvailableMessage = createBooksAvailableMessage();

		// execute test
		runTest_OrderBooks();
		
		//validate successful callback state
		Assert.isTrue(isFiredResponseSent("OrderAccepted"));
		Assert.isTrue(orderAcceptedReceived);

		//validate non-existant callback state
		Assert.isTrue(queryRequestReceived);
		Assert.isTrue(reservationRequestReceived);
		Assert.isFalse(orderRejectedReceived);
		
		//validate request completion state
		Assert.isTrue(isFiredRequestReceived("OrderBooks"));
		Assert.isTrue(isFiredRequestDone("OrderBooks"));
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 2)
	public void test_OrderBooks_OrderRejected_From_Response() throws Exception {
		String testName = "test_OrderBooks_OrderRejected_From_Response";
		log.info(testName+": started");
		
		registerNotificationListeners();
		expectedEvent = "bookshop2.seller.OrderBooks_Request_Done";
		expectedMessage = "bookshop2.seller.OrderRejected";
		orderRejectedReason = "Books Unavailable";
		expectedCallback = "BooksUnavailable";

		// setup mock response
		booksUnavailableMessage = createBooksUnavailableMessage();

		// execute test
		runTest_OrderBooks();
		
		//validate successful callback state
		Assert.isTrue(isFiredResponseSent("OrderRejected"));
		//Assert.isTrue(isFiredResponseDone("OrderRejected"));
		Assert.isTrue(orderRejectedReceived);
		
		//validate non-existant callback state
		Assert.isFalse(isFiredResponseSent("OrderAccepted"));
		Assert.isFalse(orderAcceptedReceived);
		
		//validate request completion state
		Assert.isTrue(isFiredRequestReceived("OrderBooks"));
		Assert.isTrue(isFiredRequestDone("OrderBooks"));

		//Assert.isTrue(queryBooksReceived);
		Assert.isFalse(reservationRequestReceived);

		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}

}
