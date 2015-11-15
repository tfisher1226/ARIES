package bookshop2.buyer.incoming.orderBooks;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.aries.Assert;
import org.aries.jms.client.JmsClient;
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

import bookshop2.Order;
import bookshop2.OrderAcceptedMessage;
import bookshop2.OrderRejectedMessage;
import bookshop2.OrderRequestMessage;
import bookshop2.buyer.BuyerTestEARBuilder;
import bookshop2.buyer.outgoing.orderAccepted.OrderAcceptedReply;
import bookshop2.buyer.outgoing.orderRejected.OrderRejectedReply;
import bookshop2.seller.client.orderBooks.OrderBooksProxyForJMS;
import bookshop2.util.Bookshop2Fixture;


@RunAsClient
@RunWith(Arquillian.class)
public class OrderBooksJMSListenerCIT extends AbstractJMSListenerArquillionTest {
	
	private TransactionTestControl transactionTestControl;
	
	private JmsClient orderBooksClient;
	
	/** Remote OrderBooks request handler */
	private JmsClient remoteOrderBooksHandler;
	
	/** Local OrderAccepted response handler */
	private JmsClient orderAcceptedHandler;
	
	/** Local OrderRejected response handler */
	private JmsClient orderRejectedHandler;
	
	private JmsClient remoteOrderRejectedSender;
	
	private JmsClient remoteOrderAcceptedSender;
	
	/** Remote OrderBooks request listener */
	private MessageListener remoteOrderBooksListener;

	/** Local OrderAccepted response listener */
	private MessageListener orderAcceptedListener;
	
	/** Local OrderRejected response listener */
	private MessageListener orderRejectedListener;
	
	private OrderRequestMessage orderRequestMessage;

	private OrderAcceptedMessage orderAcceptedMessage;

	private OrderRejectedMessage orderRejectedMessage;
	
	protected boolean orderBooksReceived;
	
	protected boolean orderAcceptedReceived;

	protected boolean orderRejectedReceived;

	protected String orderRejectedReason;

	
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
		return "bookshop2.buyer.OrderBooks";
	}
	
	@Override
	public String getTargetArchive() {
		return BuyerTestEARBuilder.NAME;
	}
	
	@Override
	public String getTargetDestination() {
		return getJNDINameForQueue("public_bookshop2_buyer_order_books_queue");
	}

	//outgoing response back to caller
	public String getRemoteOrderAcceptedDestination() {
		return getJNDINameForQueue("public_bookshop2_buyer_order_accepted_queue");
	}

	//outgoing response back to caller
	public String getRemoteOrderRejectedDestination() {
		return getJNDINameForQueue("public_bookshop2_buyer_order_rejected_queue");
	}

	//outgoing call to remote service 
	public String getRemoteOrderBooksDestination() {
		return getJNDINameForQueue("protected_bookshop2_seller_order_books_queue");
	}

	//outgoing response back to caller
	public String getLocalOrderAcceptedDestination() {
		return "test_message_domain_test_destination_queue_a";
	}

	//outgoing response back to caller
	public String getLocalOrderRejectedDestination() {
		return "test_message_domain_test_destination_queue_b";
	}

	@Override
	public Class<?> getTestClass() {
		return OrderBooksJMSListenerCIT.class;
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
		orderRequestMessage = createOrderRequestMessage();
		orderAcceptedListener = createOrderAcceptedResponseListener();
		orderRejectedListener = createOrderRejectedResponseListener();
		remoteOrderBooksListener = createRemoteOrderBooksRequestListener();
		remoteOrderAcceptedSender = createClient(getRemoteOrderAcceptedDestination());
		remoteOrderRejectedSender = createClient(getRemoteOrderRejectedDestination());
	}
	
	protected void createTransactionControl() throws Exception {
		transactionTestControl = new TransactionTestControl();
		transactionTestControl.setupTransactionManager();
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
		orderAcceptedHandler.reset();
		orderAcceptedHandler = null;
		orderAcceptedListener = null;
		orderRejectedHandler.reset();
		orderRejectedHandler = null;
		orderRejectedListener = null;
		remoteOrderBooksHandler.reset();
		remoteOrderBooksHandler = null;
		remoteOrderAcceptedSender.reset();
		remoteOrderAcceptedSender = null;
		remoteOrderRejectedSender.reset();
		remoteOrderRejectedSender = null;
	}

	protected void clearState() throws Exception {
		orderRequestMessage = null;
		orderBooksReceived = false;
		orderAcceptedMessage = null;
		orderAcceptedReceived = false;
		orderRejectedMessage = null;
		orderRejectedReceived = false;
		orderRejectedReason = null;
		super.clearState();
	}
	
//	@TargetsContainer("hornetQ01_local")
//	@Deployment(name = "txManagerEAR", order = 1)
//	public static EnterpriseArchive createTXManagerEAR() {
//		TXManagerTestEARBuilder builder = new TXManagerTestEARBuilder();
//		return builder.createEAR();
//	}
	
	@TargetsContainer("hornetQ01_local")
	@Deployment(name = "buyerEAR", order = 2)
	public static EnterpriseArchive createBuyerEAR() {
		BuyerTestEARBuilder builder = new BuyerTestEARBuilder();
		builder.setIncludeWar(true);
		return builder.createEAR();
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
		OrderRequestMessage orderRequestMessage = Bookshop2Fixture.create_OrderRequestMessage(cancel, undo);
		orderRequestMessage.addToReplyToDestinations("OrderAccepted", getLocalOrderAcceptedDestination());
		orderRequestMessage.addToReplyToDestinations("OrderRejected", getLocalOrderRejectedDestination());
		return initializeMessage(orderRequestMessage);
	}
	
	public OrderAcceptedMessage createOrderAcceptedMessage() {
		return initializeMessage(Bookshop2Fixture.create_OrderAcceptedMessage());
	}

	public OrderRejectedMessage createOrderRejectedMessage() {
		return initializeMessage(Bookshop2Fixture.create_OrderRejectedMessage());
	}
	
	@Test
	@InSequence(value = 1)
	public void testOrderBooks_OrderAccepted() throws Exception {
		String testName = "testOrderBooks_OrderAccepted";
		log.info(testName+": started");
		registerNotificationListeners();
		//execution started
		runTest_orderBooks();
		//execution finished
		Assert.isTrue(orderBooksReceived);
		Assert.isTrue(orderAcceptedReceived);
		Assert.isFalse(orderRejectedReceived);
		Assert.isTrue(isFiredRequestDone("OrderBooks"));
		Assert.isTrue(isFiredResponseSent("OrderAccepted"));
		Assert.isFalse(isFiredResponseSendAborted("OrderRejected"));
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 2)
	public void testOrderBooks_OrderRejected() throws Exception {
		String testName = "testOrderBooks_OrderRejected";
		log.info(testName+": started");
		registerNotificationListeners();
		orderRejectedReason = "order rejected";
		isExceptionExpected = true;
		//execution started
		runTest_orderBooks();
		//execution finished
		Assert.isTrue(orderBooksReceived);
		Assert.isFalse(orderAcceptedReceived);
		Assert.isTrue(orderRejectedReceived);
		Assert.isTrue(isFiredRequestDone("OrderBooks"));
		Assert.isFalse(isFiredResponseSent("OrderAccepted"));
		Assert.isTrue(isFiredResponseSent("OrderRejected"));
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runTest_orderBooks() throws Exception {
		// prepare the environment
		removeMessagesFromDestinations();
		
		// prepare context
		//beginTransaction();
		
		// prepare mocks
		registerForResult();
		
		// start fixture execution
		remoteOrderBooksHandler = startRemoteOrderBooksRequestHandler(remoteOrderBooksListener);
		orderAcceptedHandler = startLocalOrderAcceptedResponseHandler(orderAcceptedListener);
		orderRejectedHandler = startLocalOrderRejectedResponseHandler(orderRejectedListener);
		orderBooksClient = startOrderBooksClient();
		sendRequest_OrderBooks();
		
		// wait for result
		Object result = waitForCompletion();
		validateResult(result);
		
		// close context
		//commitTransaction();
		
		// validate the environment
		assertEmptyTargetDestination();
		removeMessagesFromDestinations();
	}
	
	protected void sendRequest_OrderBooks() throws Exception {
		orderBooksClient.send(orderRequestMessage, correlationId, null);
	}

	protected void sendRequest_OrderBooks_Cancel() throws Exception {
		orderRequestMessage = createOrderRequestCancelMessage();
		sendRequest_OrderBooks();
	}

	protected void sendRequest_OrderBooks_Undo() throws Exception {
		orderRequestMessage = createOrderRequestUndoMessage();
		sendRequest_OrderBooks();
	}
	
	protected void registerNotificationListeners() throws Exception {
		addRequestNotificationListeners("OrderBooks");
		addResponseNotificationListeners("OrderAccepted");
		addResponseNotificationListeners("OrderRejected");
	}
	
	protected void removeMessagesFromDestinations() throws Exception {
		removeMessagesFromQueue(getTargetArchive(), getTargetDestination());
		removeMessagesFromQueue(getTargetArchive(), getRemoteOrderBooksDestination());
		removeMessagesFromQueue(getTargetArchive(), getLocalOrderAcceptedDestination());
		removeMessagesFromQueue(getTargetArchive(), getLocalOrderRejectedDestination());
	}
	
	protected JmsClient startRemoteOrderBooksRequestHandler(MessageListener messageListener) throws Exception {
		JmsClient client = createClient(getRemoteOrderBooksDestination());
		client.setMessageListener(messageListener);
		client.initialize();
		return client;
	}
	
	protected JmsClient startLocalOrderAcceptedResponseHandler(MessageListener messageListener) throws Exception {
		JmsClient client = createClient(getJNDINameForQueue(getLocalOrderAcceptedDestination()));
		client.setMessageListener(messageListener);
		client.initialize();
		return client;
	}
	
	protected JmsClient startLocalOrderRejectedResponseHandler(MessageListener messageListener) throws Exception {
		JmsClient client = createClient(getJNDINameForQueue(getLocalOrderRejectedDestination()));
		client.setMessageListener(messageListener);
		client.initialize();
		return client;
	}
	
	protected JmsClient startOrderBooksClient() throws Exception {
		JmsClient client = new OrderBooksProxyForJMS(OrderBooks.ID);
		configureClient(client, getTargetDestination());
		client.setCreateTemporaryQueue(true);
		client.initialize();
		return client;
	}
	
	protected MessageListener createRemoteOrderBooksRequestListener() {
		return new MessageListener() {
			public void onMessage(Message message) {
				try {
					Object object = getObjectFromMessage(message);
					Assert.notNull(object, "Payload not found");
					Assert.isTrue(object instanceof OrderRequestMessage, "Payload type not correct");
					OrderRequestMessage orderRequestMessage = (OrderRequestMessage) object;
					validate(orderRequestMessage);
					process(orderRequestMessage);
				} catch (Throwable e) {
					errorMessage = ExceptionUtils.getRootCauseMessage(e);
				} finally {
					orderBooksReceived = true;
				}
			}
		};
	}
	
	protected void process(OrderRequestMessage orderRequestMessage) {
		Order order = orderRequestMessage.getOrder();
		if (!isExceptionExpected) {
			sendOrderAcceptedMessage(order);
			
		} else {
			String reason = orderRejectedReason;
			sendOrderRejectedMessage(order, reason);
		}
	}

	protected void sendOrderAcceptedMessage(Order order) {
		orderAcceptedMessage = createOrderAcceptedMessage();
		orderAcceptedMessage.setOrder(order);
		remoteOrderAcceptedSender.sendResponse(orderAcceptedMessage);
	}
	
	protected void sendOrderRejectedMessage(Order order, String reason) {
		orderRejectedMessage = createOrderRejectedMessage();
		orderRejectedMessage.setOrder(order);
		orderRejectedMessage.setReason(reason);
		remoteOrderRejectedSender.sendResponse(orderRejectedMessage);
	}
	
	protected MessageListener createOrderAcceptedResponseListener() {
		return new MessageListener() {
			public void onMessage(Message message) {
				try {
					Object object = getObjectFromMessage(message);
					Assert.notNull(object, "Payload not found");
					Assert.isTrue(object instanceof OrderAcceptedMessage, "Payload type not correct");
					OrderAcceptedMessage orderAcceptedMessage = (OrderAcceptedMessage) object;
					validate(orderAcceptedMessage);
				} catch (Throwable e) {
					errorMessage = ExceptionUtils.getRootCauseMessage(e);
				} finally {
					if (expectedMessage != null && expectedMessage.equalsIgnoreCase("bookshop2.buyer.OrderAccepted"))
						expectedMessageResult.set(true);
					orderAcceptedReceived = true;
				}
			}
		};
	}
	
	protected MessageListener createOrderRejectedResponseListener() {
		return new MessageListener() {
			public void onMessage(Message message) {
				try {
					Object object = getObjectFromMessage(message);
					Assert.notNull(object, "Payload not found");
					Assert.isTrue(object instanceof OrderRejectedMessage, "Payload type not correct");
					OrderRejectedMessage orderRejectedMessage = (OrderRejectedMessage) object;
					validate(orderRejectedMessage);
				} catch (Throwable e) {
					errorMessage = ExceptionUtils.getRootCauseMessage(e);
				} finally {
					if (expectedMessage != null && expectedMessage.equalsIgnoreCase("bookshop2.buyer.OrderRejected"))
						expectedMessageResult.set(true);
					orderRejectedReceived = true;
				}
			}
		};
	}

	protected void validateResult(Object result) {
		if (result instanceof OrderAcceptedMessage) {
			validate((OrderAcceptedMessage) result);
		} else if (result instanceof OrderRejectedMessage) {
			validate((OrderRejectedMessage) result);
		} else if (result instanceof String) {
			String resultString = result.toString();
			if ((expectedError != null && !expectedError.equalsIgnoreCase(resultString)) &&
				(expectedEvent != null && !expectedEvent.equalsIgnoreCase(resultString)))
					errorMessage = "Unexpected message: "+result;
		} else {
			errorMessage = "Unrecognized result: "+result;
		}
	}
	
	protected void validate(OrderRequestMessage orderRequestMessage) {
		Assert.notNull(orderRequestMessage, "Message must be specified");
		Bookshop2Fixture.assertOrderCorrect(orderRequestMessage.getOrder());
	}
	
	protected void validate(OrderAcceptedMessage orderAcceptedMessage) {
		Assert.notNull(orderAcceptedMessage, "Message must be specified");
		Bookshop2Fixture.assertOrderCorrect(orderAcceptedMessage.getOrder());
	}
	
	protected void validate(OrderRejectedMessage orderRejectedMessage) {
		Assert.notNull(orderRejectedMessage, "Message must be specified");
		Bookshop2Fixture.assertOrderCorrect(orderRejectedMessage.getOrder());
		Assert.equals(orderRejectedMessage.getReason(), orderRejectedReason);
	}

}
