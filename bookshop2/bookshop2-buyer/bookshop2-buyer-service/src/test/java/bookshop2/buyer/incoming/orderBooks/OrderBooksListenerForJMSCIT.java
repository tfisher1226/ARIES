package bookshop2.buyer.incoming.orderBooks;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.aries.Assert;
import org.aries.common.util.CommonFixture;
import org.aries.jms.client.JmsClient;
import org.aries.jms.util.MessageUtil;
import org.aries.tx.AbstractArquillianTest;
import org.aries.tx.AbstractJMSListenerArquillionTest;
import org.aries.tx.BytemanRule;
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

import bookshop2.OrderAcceptedMessage;
import bookshop2.OrderRejectedMessage;
import bookshop2.OrderRequestMessage;
import bookshop2.PurchaseAcceptedMessage;
import bookshop2.PurchaseRejectedMessage;
import bookshop2.PurchaseRequestMessage;
import bookshop2.buyer.BuyerTestEARBuilder;
import bookshop2.seller.client.orderBooks.OrderBooksProxyForJMS;
import bookshop2.util.Bookshop2Fixture;


@RunAsClient
@RunWith(Arquillian.class)
public class OrderBooksListenerForJMSCIT extends AbstractJMSListenerArquillionTest {
	
	private TransactionTestControl transactionTestControl;
	
	private JmsClient orderBooksClient;
	
	private JmsClient localBuyerOrderAcceptedHandler;
	
	private JmsClient localBuyerOrderRejectedHandler;

	private JmsClient remoteSellerOrderBooksHandler;
	
	private JmsClient remoteSellerOrderAcceptedSender;

	private JmsClient remoteSellerOrderRejectedSender;
	
	private JmsClient remoteSellerPurchaseBooksHandler;
	
	private JmsClient remoteSellerPurchaseAcceptedSender;
	
	private JmsClient remoteSellerPurchaseRejectedSender;
	
	private OrderRequestMessage orderRequestMessage;

	private OrderAcceptedMessage orderAcceptedMessage;

	private OrderRejectedMessage orderRejectedMessage;
	
	private PurchaseRequestMessage purchaseRequestMessage;
	
	private PurchaseAcceptedMessage purchaseAcceptedMessage;
	
	private PurchaseRejectedMessage purchaseRejectedMessage;
	
	private boolean orderRequestReceived;
	
	private boolean purchaseRequestReceived;
	
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
		return "bookshop2.buyer";
	}
	
	@Override
	public String getServiceId() {
		return "bookshop2.buyer.orderBooks";
	}
	
	@Override
	public String getTargetArchive() {
		return BuyerTestEARBuilder.NAME;
	}
	
	@Override
	public String getTargetDestination() {
		return getJNDINameForQueue("public_bookshop2_buyer_order_books_queue");
	}

	public String get_remote_Seller_OrderBooks_destination() {
		return getJNDINameForQueue("protected_bookshop2_seller_order_books_queue");
	}
	
	public String get_remote_Seller_PurchaseBooks_destination() {
		return getJNDINameForQueue("protected_bookshop2_seller_purchase_books_queue");
	}
	
	public String get_remote_Buyer_OrderAccepted_destination() {
		return getJNDINameForQueue("public_bookshop2_buyer_order_accepted_queue");
	}

	public String get_remote_Buyer_OrderRejected_destination() {
		return getJNDINameForQueue("public_bookshop2_buyer_order_rejected_queue");
	}

	public String get_remote_Buyer_PurchaseAccepted_destination() {
		return getJNDINameForQueue("public_bookshop2_buyer_purchase_accepted_queue");
	}

	public String get_remote_Buyer_PurchaseRejected_destination() {
		return getJNDINameForQueue("public_bookshop2_buyer_purchase_rejected_queue");
	}

	public String get_local_Buyer_OrderAccepted_destination() {
		return "test_message_domain_test_destination_queue_a";
	}

	public String get_local_Buyer_OrderRejected_destination() {
		return "test_message_domain_test_destination_queue_b";
	}
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		startServer();
		createTransactionControl();
		remoteSellerOrderAcceptedSender = createClient(get_remote_Buyer_OrderAccepted_destination());
		remoteSellerOrderRejectedSender = createClient(get_remote_Buyer_OrderRejected_destination());
		remoteSellerPurchaseAcceptedSender = createClient(get_remote_Buyer_PurchaseAccepted_destination());
		remoteSellerPurchaseRejectedSender = createClient(get_remote_Buyer_PurchaseRejected_destination());
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
		message.addToReplyToDestinations("OrderAccepted", get_local_Buyer_OrderAccepted_destination());
		message.addToReplyToDestinations("OrderRejected", get_local_Buyer_OrderRejected_destination());
		initializeMessage(message);
		return message;
	}
	
	public OrderAcceptedMessage createOrderAcceptedMessage() {
		OrderAcceptedMessage message = Bookshop2Fixture.create_OrderAcceptedMessage();
		initializeMessage(message);
		return message;
	}

	public OrderRejectedMessage createOrderRejectedMessage() {
		OrderRejectedMessage message = Bookshop2Fixture.create_OrderRejectedMessage();
		initializeMessage(message);
		return message;
	}
	
	public PurchaseAcceptedMessage createPurchaseAcceptedMessage() {
		PurchaseAcceptedMessage message = Bookshop2Fixture.create_PurchaseAcceptedMessage();
		initializeMessage(message);
		return message;
	}
	
	public PurchaseRejectedMessage createPurchaseRejectedMessage() {
		PurchaseRejectedMessage message = Bookshop2Fixture.create_PurchaseRejectedMessage();
		initializeMessage(message);
		return message;
	}
	
	protected void registerNotificationListeners() throws Exception {
		addRequestNotificationListeners("bookshop2.buyer.OrderBooks");
		addResponseNotificationListeners("bookshop2.buyer.OrderAccepted");
		addResponseNotificationListeners("bookshop2.buyer.OrderRejected");
		addRequestNotificationListeners("bookshop2.seller.OrderBooks");
		addResponseNotificationListeners("bookshop2.seller.OrderAccepted");
		addResponseNotificationListeners("bookshop2.seller.OrderRejected");
		addRequestNotificationListeners("bookshop2.seller.PurchaseBooks");
		addResponseNotificationListeners("bookshop2.seller.PurchaseAccepted");
		addResponseNotificationListeners("bookshop2.seller.PurchaseRejected");
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
		remoteSellerOrderBooksHandler.reset();
		remoteSellerOrderBooksHandler = null;
		remoteSellerPurchaseBooksHandler.reset();
		remoteSellerPurchaseBooksHandler = null;
		
		//remote clients for sending responses back from remote (mocked) service(s)
		remoteSellerOrderAcceptedSender.reset();
		remoteSellerOrderAcceptedSender = null;
		remoteSellerOrderRejectedSender.reset();
		remoteSellerOrderRejectedSender = null;
		
		//remote clients for sending responses back from remote (mocked) service(s)
		remoteSellerPurchaseAcceptedSender.reset();
		remoteSellerPurchaseAcceptedSender = null;
		remoteSellerPurchaseRejectedSender.reset();
		remoteSellerPurchaseRejectedSender = null;
		
		//local handlers for responses from target service
		localBuyerOrderAcceptedHandler.reset();
		localBuyerOrderAcceptedHandler = null;
		localBuyerOrderRejectedHandler.reset();
		localBuyerOrderRejectedHandler = null;
		super.clearStructures();
	}

	protected void clearState() throws Exception {
		orderRequestMessage = null;
		orderAcceptedMessage = null;
		orderRejectedMessage = null;
		orderRequestMessage = null;
		orderAcceptedMessage = null;
		orderRejectedMessage = null;
		purchaseRequestMessage = null;
		purchaseAcceptedMessage = null;
		purchaseRejectedMessage = null;
		orderRequestReceived = false;
		purchaseRequestReceived = false;
		orderAcceptedReceived = false;
		orderRejectedReceived = false;
		super.clearState();
	}
	
	protected void removeMessagesFromDestinations() throws Exception {
		removeMessagesFromQueue(getTargetArchive(), getTargetDestination());
		removeMessagesFromQueue(getTargetArchive(), get_local_Buyer_OrderAccepted_destination());
		removeMessagesFromQueue(getTargetArchive(), get_local_Buyer_OrderRejected_destination());
		removeMessagesFromQueue(getTargetArchive(), get_remote_Seller_OrderBooks_destination());
		removeMessagesFromQueue(getTargetArchive(), get_remote_Buyer_OrderAccepted_destination());
		removeMessagesFromQueue(getTargetArchive(), get_remote_Buyer_OrderRejected_destination());
		removeMessagesFromQueue(getTargetArchive(), get_remote_Seller_PurchaseBooks_destination());
		removeMessagesFromQueue(getTargetArchive(), get_remote_Buyer_PurchaseAccepted_destination());
		removeMessagesFromQueue(getTargetArchive(), get_remote_Buyer_PurchaseRejected_destination());
	}
	
	@TargetsContainer("hornetQ01_local")
	@Deployment(name = "buyerEAR", order = 2)
	public static EnterpriseArchive createBuyerEAR() {
		BuyerTestEARBuilder builder = new BuyerTestEARBuilder();
		builder.setIncludeWar(true);
		return builder.createEAR();
	}
	
	public void run_send_OrderBooks() throws Exception {
		run_send_OrderBooks(createOrderRequestMessage());
	}
	
	public void run_send_OrderBooks(OrderRequestMessage orderRequestMessage) throws Exception {
		
		// prepare the environment
		removeMessagesFromDestinations();
		
		// prepare context
		//beginTransaction();
		
		// prepare mocks
		registerForResult();
		
		// start handlers for calls to remote service(s)
		remoteSellerOrderBooksHandler = start_remote_Seller_OrderBooks_handler();
		remoteSellerPurchaseBooksHandler = start_remote_Seller_PurchaseBooks_handler();

		// start local handlers for responses from target service
		localBuyerOrderAcceptedHandler = start_local_Buyer_OrderAccepted_handler();
		localBuyerOrderRejectedHandler = start_local_Buyer_OrderRejected_handler();

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
	
	protected void run_send_OrderBooks_cancel() throws Exception {
		expectedEvent = "bookshop2.buyer.OrderBooks_Request_Cancel_Done";
		registerForResult(expectedEvent);
		
		OrderRequestMessage message = createOrderRequestCancelMessage();
		orderBooksClient.send(message, correlationId, null);
		
		Object result = waitForCompletion();
		validateResult(result);
	}
	
	protected void run_send_OrderBooks_undo() throws Exception {
		expectedEvent = "bookshop2.buyer.OrderBooks_Request_Undo_Done";
		registerForResult(expectedEvent);
		
		OrderRequestMessage message = createOrderRequestUndoMessage();
		orderBooksClient.send(message, correlationId, null);
		
		Object result = waitForCompletion();
		validateResult(result);
	}

	public Thread start_run_send_OrderBooks() throws Exception {
		Thread thread = new Thread() {
			public void run() {
				try {
					run_send_OrderBooks();
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
	
	protected JmsClient start_remote_Seller_OrderBooks_handler() throws Exception {
		JmsClient client = createClient(get_remote_Seller_OrderBooks_destination());
		client.setMessageListener(create_remote_Seller_OrderBooks_message_listener());
		client.initialize();
		return client;
	}
	
	protected JmsClient start_remote_Seller_PurchaseBooks_handler() throws Exception {
		JmsClient client = createClient(get_remote_Seller_PurchaseBooks_destination());
		client.setMessageListener(create_remote_Seller_PurchaseBooks_message_listener());
		client.initialize();
		return client;
	}
	
	protected JmsClient start_local_Buyer_OrderAccepted_handler() throws Exception {
		JmsClient client = createClient(getJNDINameForQueue(get_local_Buyer_OrderAccepted_destination()));
		client.setMessageListener(create_local_Buyer_OrderAccepted_response_listener());
		client.initialize();
		return client;
	}
	
	protected JmsClient start_local_Buyer_OrderRejected_handler() throws Exception {
		JmsClient client = createClient(getJNDINameForQueue(get_local_Buyer_OrderRejected_destination()));
		client.setMessageListener(create_local_Buyer_OrderRejected_response_listener());
		client.initialize();
		return client;
	}
	
	protected MessageListener create_remote_Seller_OrderBooks_message_listener() {
		return new MessageListener() {
			public void onMessage(Message message) {
				try {
					String jmsMessageID = message.getJMSMessageID();
					log.info("#### [test]: OrderBooks: received: "+jmsMessageID);
					Object object = MessageUtil.getPart(message, "orderRequestMessage");
					Assert.isTrue(object instanceof OrderRequestMessage, "Payload type not correct");
					OrderRequestMessage orderRequestMessage = (OrderRequestMessage) object;
					switch (getActionFromMessage(orderRequestMessage)) {
					case CANCEL:
					case UNDO:
						break;
					default:
						validateMessage(orderRequestMessage);
						processMessage(orderRequestMessage);
						break;
					}
				} catch (Throwable e) {
					errorMessage = ExceptionUtils.getRootCauseMessage(e);
					e.printStackTrace();
				} finally {
					if (expectedMessage != null && expectedMessage.equalsIgnoreCase("bookshop2.seller.OrderBooks"))
						expectedMessageResult.set(true);
					orderRequestReceived = true;
				}
			}
		};
	}
	
	protected void processMessage(OrderRequestMessage orderRequestMessage) throws Exception {
		if (expectedCallback == null)
			return;
			
		if (expectedCallback.equals("OrderAccepted")) {
			log.info("#### [test]: OrderAccepted: sending");
			initializeMessage(orderAcceptedMessage, orderRequestMessage);
			remoteSellerOrderAcceptedSender.sendResponse(orderAcceptedMessage);
		
		} else if (expectedCallback.equals("OrderRejected")) {
			log.info("#### [test]: OrderRejected: sending");
			initializeMessage(orderRejectedMessage, orderRequestMessage);
			remoteSellerOrderRejectedSender.sendResponse(orderRejectedMessage);
		}
	}
	
	protected MessageListener create_remote_Seller_PurchaseBooks_message_listener() {
		return new MessageListener() {
			public void onMessage(Message message) {
				try {
					String jmsMessageID = message.getJMSMessageID();
					log.info("#### [test]: PurchaseBooks: received: "+jmsMessageID);
					Object object = MessageUtil.getPart(message, "purchaseRequestMessage");
					Assert.isTrue(object instanceof PurchaseRequestMessage, "Payload type not correct");
					PurchaseRequestMessage purchaseRequestMessage = (PurchaseRequestMessage) object;
					switch (getActionFromMessage(purchaseRequestMessage)) {
					case CANCEL:
					case UNDO:
						break;
					default:
						validateMessage(purchaseRequestMessage);
						processMessage(purchaseRequestMessage);
						break;
					}
				} catch (Throwable e) {
					errorMessage = ExceptionUtils.getRootCauseMessage(e);
					e.printStackTrace();
				} finally {
					if (expectedMessage != null && expectedMessage.equalsIgnoreCase("bookshop2.seller.PurchaseBooks"))
						expectedMessageResult.set(true);
						purchaseRequestReceived = true;
				}
			}
		};
	}
	
	protected void processMessage(PurchaseRequestMessage purchaseRequestMessage) throws Exception {
		if (expectedCallback == null)
			return;
		
		if (expectedCallback.equals("PurchaseAccepted")) {
			log.info("#### [test]: PurchaseAccepted: sending");
			initializeMessage(purchaseAcceptedMessage, purchaseRequestMessage);
			remoteSellerPurchaseAcceptedSender.sendResponse(purchaseAcceptedMessage);
		
		} else if (expectedCallback.equals("PurchaseRejected")) {
			log.info("#### [test]: PurchaseRejected: sending");
			initializeMessage(purchaseRejectedMessage, purchaseRequestMessage);
			remoteSellerPurchaseRejectedSender.sendResponse(purchaseRejectedMessage);
		}
	}
	
	protected MessageListener create_local_Buyer_OrderAccepted_response_listener() {
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
					if (expectedMessage != null && expectedMessage.equalsIgnoreCase("bookshop2.buyer.OrderAccepted"))
						expectedMessageResult.set(true);
					orderAcceptedReceived = true;
				}
			}
		};
	}
	
	protected MessageListener create_local_Buyer_OrderRejected_response_listener() {
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
					if (expectedMessage != null && expectedMessage.equalsIgnoreCase("bookshop2.buyer.OrderRejected"))
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
	
	protected void validateMessage(OrderRequestMessage orderRequestMessage) throws Exception {
		Assert.notNull(orderRequestMessage, "Message must be specified");
		//Bookshop2Fixture.assertOrderCorrect(orderRequestMessage.getOrder());
		//CommonFixture.assertPersonNameCorrect(orderRequestMessage.getName());
		//CommonFixture.assertStreetAddressCorrect(orderRequestMessage.getAddress());
		//Bookshop2Fixture.assertPaymentCorrect(orderRequestMessage.getPayment());
		if (this.orderRequestMessage != null) {
			Bookshop2Fixture.assertSameOrder(this.orderRequestMessage.getOrder(), orderRequestMessage.getOrder(), "Order field is unequal");
			CommonFixture.assertSamePersonName(this.orderRequestMessage.getName(), orderRequestMessage.getName(), "Name field is unequal");
			CommonFixture.assertSameStreetAddress(this.orderRequestMessage.getAddress(), orderRequestMessage.getAddress(), "Address field is unequal");
			Bookshop2Fixture.assertSamePayment(this.orderRequestMessage.getPayment(), orderRequestMessage.getPayment(), "Payment field is unequal");
		}
	}
	
	protected void validateMessage(PurchaseRequestMessage purchaseRequestMessage) throws Exception {
		Assert.notNull(purchaseRequestMessage, "Message must be specified");
		//CommonFixture.assertPersonNameCorrect(purchaseRequestMessage.getName());
		//CommonFixture.assertStreetAddressCorrect(purchaseRequestMessage.getAddress());
		//Bookshop2Fixture.assertOrderCorrect(purchaseRequestMessage.getOrder());
		//Bookshop2Fixture.assertPaymentCorrect(purchaseRequestMessage.getPayment());
		if (this.purchaseRequestMessage != null) {
			CommonFixture.assertSamePersonName(this.purchaseRequestMessage.getName(), purchaseRequestMessage.getName(), "Name field is unequal");
			CommonFixture.assertSameStreetAddress(this.purchaseRequestMessage.getAddress(), purchaseRequestMessage.getAddress(), "Address field is unequal");
			Bookshop2Fixture.assertSameOrder(this.purchaseRequestMessage.getOrder(), purchaseRequestMessage.getOrder(), "Order field is unequal");
			Bookshop2Fixture.assertSamePayment(this.purchaseRequestMessage.getPayment(), purchaseRequestMessage.getPayment(), "Payment field is unequal");
		}
	}
	
	protected void validateMessage(OrderAcceptedMessage orderAcceptedMessage) throws Exception {
		Assert.notNull(orderAcceptedMessage, "Message must be specified");
		//Bookshop2Fixture.assertOrderCorrect(orderAcceptedMessage.getOrder());
		if (this.orderAcceptedMessage != null) {
			Bookshop2Fixture.assertSameOrder(this.orderAcceptedMessage.getOrder(), orderAcceptedMessage.getOrder(), "Order field is unequal");
		}
	}
	
	protected void validateMessage(OrderRejectedMessage orderRejectedMessage) throws Exception {
		Assert.notNull(orderRejectedMessage, "Message must be specified");
		//Bookshop2Fixture.assertOrderCorrect(orderRejectedMessage.getOrder());
		if (this.orderRejectedMessage != null) {
			Assert.equals(this.orderRejectedMessage.getReason(), orderRejectedMessage.getReason(), "Reason field is unequal");
			Bookshop2Fixture.assertSameOrder(this.orderRejectedMessage.getOrder(), orderRejectedMessage.getOrder(), "Order field is unequal");
		}
	}
	
	@Test
	//@Ignore
	@InSequence(value = 1)
	public void runTest_OrderBooks_OrderAccepted_from_OrderBooks_OrderAccepted() throws Exception {
		String testName = "runTest_OrderBooks_OrderAccepted_from_OrderBooks_OrderAccepted";
		log.info(testName+": started");
		
		registerNotificationListeners();
		
		// prepare test data
		
		// setup expectations
		expectedEvent = "bookshop2.buyer.OrderBooks_Request_Done";
		expectedMessage = "bookshop2.buyer.OrderAccepted";
		
		// setup mock response
		expectedCallback = "OrderAccepted";
		orderAcceptedMessage = createOrderAcceptedMessage();
		orderAcceptedMessage = Bookshop2Fixture.create_OrderAcceptedMessage();
		
		// execute action
		run_send_OrderBooks();
		
		// validate request initiation state
		Assert.isTrue(isFiredRequestReceived("OrderBooks"));
		
		// validate remote OrderBooks interaction
		Assert.isTrue(isFiredRequestSent("OrderBooks"));
		Assert.isTrue(isFiredResponseDone("OrderAccepted"));
		Assert.isTrue(orderRequestReceived);
		
		// validate remote PurchaseBooks interaction
		Assert.isTrue(isFiredRequestSent("PurchaseBooks"));
		Assert.isTrue(purchaseRequestReceived);
		
		// validate successful callback state
		Assert.isTrue(isFiredResponseSent("OrderAccepted"));
		Assert.isTrue(orderAcceptedReceived);
		
		// validate non-existent callback state
		Assert.isFalse(isFiredResponseSent("OrderRejected"));
		Assert.isFalse(orderRejectedReceived);
		
		// validate request completion state
		Assert.isTrue(isFiredRequestDone("OrderBooks"));
		
		// cleanup
		removeMessagesFromDestinations();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 2)
	public void runTest_OrderBooks_OrderAccepted_from_OrderBooks_OrderAccepted_cancel() throws Exception {
		String testName = "runTest_OrderBooks_OrderAccepted_from_OrderBooks_OrderAccepted_cancel";
		log.info(testName+": started");
		
		registerNotificationListeners();
		
		// prepare test data
		
		// setup expectations
		expectedEvent = "bookshop2.buyer.OrderBooks_Request_Done";
		expectedMessage = "bookshop2.buyer.OrderAccepted";
		
		// setup mock response
		expectedCallback = "OrderAccepted";
		orderAcceptedMessage = createOrderAcceptedMessage();
		orderAcceptedMessage = Bookshop2Fixture.create_OrderAcceptedMessage();
		
		// execute action
		run_send_OrderBooks();
		
		removeMessagesFromDestinations();
		
		// execute "cancel" of action
		run_send_OrderBooks_cancel();
		
		// validate request initiation state
		Assert.isTrue(isFiredRequestReceived("OrderBooks"));
		
		// validate remote OrderBooks interaction
		Assert.isTrue(isFiredRequestSent("OrderBooks"));
		Assert.isTrue(isFiredResponseDone("OrderAccepted"));
		Assert.isTrue(orderRequestReceived);
		
		// validate remote PurchaseBooks interaction
		Assert.isTrue(isFiredRequestSent("PurchaseBooks"));
		Assert.isTrue(purchaseRequestReceived);
		
		// validate successful callback state
		Assert.isTrue(isFiredResponseSent("OrderAccepted"));
		Assert.isTrue(orderAcceptedReceived);
		
		// validate non-existent callback state
		Assert.isFalse(isFiredResponseSent("OrderRejected"));
		Assert.isFalse(orderRejectedReceived);
		
		// validate request cancellation state
		Assert.isTrue(isFiredRequestCancelled("OrderBooks"));
		Assert.isTrue(isFiredRequestRolledBack("OrderBooks"));
		
		// cleanup
		removeMessagesFromDestinations();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 3)
	public void runTest_OrderBooks_OrderAccepted_from_OrderBooks_OrderAccepted_undo() throws Exception {
		String testName = "runTest_OrderBooks_OrderAccepted_from_OrderBooks_OrderAccepted_undo";
		log.info(testName+": started");
		
		registerNotificationListeners();
		
		// prepare test data
		
		// setup expectations
		expectedEvent = "bookshop2.buyer.OrderBooks_Request_Done";
		expectedMessage = "bookshop2.buyer.OrderAccepted";
		
		// setup mock response
		expectedCallback = "OrderAccepted";
		orderAcceptedMessage = createOrderAcceptedMessage();
		orderAcceptedMessage = Bookshop2Fixture.create_OrderAcceptedMessage();
		
		// execute action
		run_send_OrderBooks();
		
		removeMessagesFromDestinations();
		
		// execute "undo" of action
		run_send_OrderBooks_undo();
		
		// validate request initiation state
		Assert.isTrue(isFiredRequestReceived("OrderBooks"));
		
		// validate remote OrderBooks interaction
		Assert.isTrue(isFiredRequestSent("OrderBooks"));
		Assert.isTrue(isFiredResponseDone("OrderAccepted"));
		Assert.isTrue(orderRequestReceived);
		
		// validate remote PurchaseBooks interaction
		Assert.isTrue(isFiredRequestSent("PurchaseBooks"));
		Assert.isTrue(purchaseRequestReceived);
		
		// validate successful callback state
		Assert.isTrue(isFiredResponseSent("OrderAccepted"));
		Assert.isTrue(orderAcceptedReceived);
		
		// validate non-existent callback state
		Assert.isFalse(isFiredResponseSent("OrderRejected"));
		Assert.isFalse(orderRejectedReceived);
		
		// validate request rolledback state
		Assert.isTrue(isFiredRequestRolledBack("OrderBooks"));
		
		// cleanup
		removeMessagesFromDestinations();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 4)
	public void runTest_OrderBooks_OrderRejected_from_OrderBooks_OrderRejected() throws Exception {
		String testName = "runTest_OrderBooks_OrderRejected_from_OrderBooks_OrderRejected";
		log.info(testName+": started");
		
		registerNotificationListeners();
		
		// prepare test data
		
		// setup expectations
		expectedEvent = "bookshop2.buyer.OrderBooks_Request_Done";
		expectedMessage = "bookshop2.buyer.OrderRejected";
		
		// setup mock response
		expectedCallback = "OrderRejected";
		orderRejectedMessage = createOrderRejectedMessage();
		orderRejectedMessage.setReason("Order Rejected");
		orderRejectedMessage = Bookshop2Fixture.create_OrderRejectedMessage();
		orderRejectedMessage.setReason("Order Rejected");
		
		// execute action
		run_send_OrderBooks();
		
		// validate request initiation state
		Assert.isTrue(isFiredRequestReceived("OrderBooks"));
		
		// validate remote OrderBooks interaction
		Assert.isTrue(isFiredRequestSent("OrderBooks"));
		Assert.isTrue(isFiredResponseDone("OrderRejected"));
		Assert.isTrue(orderRequestReceived);
		
		// validate remote PurchaseBooks interaction
		Assert.isFalse(isFiredRequestSent("PurchaseBooks"));
		Assert.isFalse(purchaseRequestReceived);
		
		// validate successful callback state
		Assert.isTrue(isFiredResponseSent("OrderRejected"));
		Assert.isTrue(orderRejectedReceived);
		
		// validate non-existent callback state
		Assert.isFalse(isFiredResponseSent("OrderAccepted"));
		Assert.isFalse(orderAcceptedReceived);
		
		// validate request completion state
		Assert.isTrue(isFiredRequestDone("OrderBooks"));
		
		// cleanup
		removeMessagesFromDestinations();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 5)
	public void runTest_OrderBooks_OrderRejected_from_OrderBooks_OrderRejected_cancel() throws Exception {
		String testName = "runTest_OrderBooks_OrderRejected_from_OrderBooks_OrderRejected_cancel";
		log.info(testName+": started");
		
		registerNotificationListeners();
		
		// prepare test data
		
		// setup expectations
		expectedEvent = "bookshop2.buyer.OrderBooks_Request_Done";
		expectedMessage = "bookshop2.buyer.OrderRejected";
		
		// setup mock response
		expectedCallback = "OrderRejected";
		orderRejectedMessage = createOrderRejectedMessage();
		orderRejectedMessage.setReason("Order Rejected");
		orderRejectedMessage = Bookshop2Fixture.create_OrderRejectedMessage();
		orderRejectedMessage.setReason("Order Rejected");
		
		// execute action
		run_send_OrderBooks();
		
		removeMessagesFromDestinations();
		
		// execute "cancel" of action
		run_send_OrderBooks_cancel();
		
		// validate request initiation state
		Assert.isTrue(isFiredRequestReceived("OrderBooks"));
		
		// validate remote OrderBooks interaction
		Assert.isTrue(isFiredRequestSent("OrderBooks"));
		Assert.isTrue(isFiredResponseDone("OrderRejected"));
		Assert.isTrue(orderRequestReceived);
		
		// validate remote PurchaseBooks interaction
		Assert.isFalse(isFiredRequestSent("PurchaseBooks"));
		Assert.isFalse(purchaseRequestReceived);
		
		// validate successful callback state
		Assert.isTrue(isFiredResponseSent("OrderRejected"));
		Assert.isTrue(orderRejectedReceived);
		
		// validate non-existent callback state
		Assert.isFalse(isFiredResponseSent("OrderAccepted"));
		Assert.isFalse(orderAcceptedReceived);
		
		// validate request cancellation state
		Assert.isTrue(isFiredRequestCancelled("OrderBooks"));
		Assert.isTrue(isFiredRequestRolledBack("OrderBooks"));
		
		// cleanup
		removeMessagesFromDestinations();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 6)
	public void runTest_OrderBooks_OrderRejected_from_OrderBooks_OrderRejected_undo() throws Exception {
		String testName = "runTest_OrderBooks_OrderRejected_from_OrderBooks_OrderRejected_undo";
		log.info(testName+": started");
		
		registerNotificationListeners();
		
		// prepare test data
		
		// setup expectations
		expectedEvent = "bookshop2.buyer.OrderBooks_Request_Done";
		expectedMessage = "bookshop2.buyer.OrderRejected";
		
		// setup mock response
		expectedCallback = "OrderRejected";
		orderRejectedMessage = createOrderRejectedMessage();
		orderRejectedMessage.setReason("Order Rejected");
		orderRejectedMessage = Bookshop2Fixture.create_OrderRejectedMessage();
		orderRejectedMessage.setReason("Order Rejected");
		
		// execute action
		run_send_OrderBooks();
		
		removeMessagesFromDestinations();
		
		// execute "undo" of action
		run_send_OrderBooks_undo();
		
		// validate request initiation state
		Assert.isTrue(isFiredRequestReceived("OrderBooks"));
		
		// validate remote OrderBooks interaction
		Assert.isTrue(isFiredRequestSent("OrderBooks"));
		Assert.isTrue(isFiredResponseDone("OrderRejected"));
		Assert.isTrue(orderRequestReceived);
		
		// validate remote PurchaseBooks interaction
		Assert.isFalse(isFiredRequestSent("PurchaseBooks"));
		Assert.isFalse(purchaseRequestReceived);
		
		// validate successful callback state
		Assert.isTrue(isFiredResponseSent("OrderRejected"));
		Assert.isTrue(orderRejectedReceived);
		
		// validate non-existent callback state
		Assert.isFalse(isFiredResponseSent("OrderAccepted"));
		Assert.isFalse(orderAcceptedReceived);
		
		// validate request rolledback state
		Assert.isTrue(isFiredRequestRolledBack("OrderBooks"));
		
		// cleanup
		removeMessagesFromDestinations();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	@Ignore
	@InSequence(value = 7)
	@BytemanRule(name = "rule7", 
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
		expectedEvent = "bookshop2.buyer.OrderBooks_Incoming_Request_Aborted";
		//TODO expectedMessage = "bookshop2.buyer.OrderRequest";
		
		// execute action
		run_send_OrderBooks();
		
		// validate request initiation state
		Assert.isTrue(isFiredRequestReceived("OrderBooks"));
		
		// validate request non-completion state
		Assert.isFalse(isFiredRequestHandled("OrderBooks"));
		Assert.isFalse(isFiredRequestDone("OrderBooks"));
		
		// validate incoming request aborted state
		Assert.isTrue(isFiredIncomingRequestAborted("OrderBooks"));
		
		// cleanup
		tearDownByteman(testName);
		removeMessagesFromDestinations();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	@Ignore
	@InSequence(value = 8)
	@BytemanRule(name = "rule8", 
			targetClass = "BuyerProcess", 
			targetMethod = "orderBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new java.lang.RuntimeException(\"exception message\")")
	public void runTest_OrderBooks_exception() throws Exception {
		String testName = "runTest_OrderBooks_exception";
		log.info(testName+": started");
		
		setupByteman(testName);
		registerNotificationListeners();
		
		// setup expectations
		expectedEvent = "bookshop2.buyer.OrderBooks_Incoming_Request_Aborted";
		expectedMessage = "bookshop2.buyer.OrderRequest";
		exceptionMessage = "exception message";
		
		// execute action
		run_send_OrderBooks();
		
		// validate request initiation state
		Assert.isTrue(isFiredRequestReceived("OrderBooks"));
		
		// validate request non-completion state
		Assert.isFalse(isFiredRequestHandled("OrderBooks"));
		Assert.isFalse(isFiredRequestDone("OrderBooks"));
		
		// validate incoming request aborted state
		Assert.isTrue(isFiredIncomingRequestAborted("OrderBooks"));
		
		// cleanup
		tearDownByteman(testName);
		removeMessagesFromDestinations();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}

}
