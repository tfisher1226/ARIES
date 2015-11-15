package bookshop2.buyer;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.aries.Assert;
import org.aries.jms.client.JmsClient;
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

import bookshop2.OrderAcceptedMessage;
import bookshop2.OrderRejectedMessage;
import bookshop2.OrderRequestMessage;
import bookshop2.buyer.client.orderBooks.OrderBooks;
import bookshop2.buyer.client.orderBooks.OrderBooksProxyForJMS;
import bookshop2.seller.SellerTestEARBuilder;
import bookshop2.shipper.ShipperTestEARBuilder;
import bookshop2.supplier.SupplierTestEARBuilder;
import bookshop2.supplier.data.supplierOrderCache.SupplierOrderCacheHelper;
import bookshop2.supplier.data.supplierOrderCache.SupplierOrderCacheProxy;
import bookshop2.util.Bookshop2Fixture;


@RunAsClient
@RunWith(Arquillian.class)
public class OrderBooksJMSListenerCIT extends AbstractJMSListenerArquillionTest {
	
	private TransactionTestControl transactionTestControl;
	
	private CacheModuleTestControl supplierOrderCacheControl;

	private SupplierOrderCacheHelper supplierOrderCacheHelper;

	private SupplierOrderCacheProxy supplierOrderCacheProxy;
	
	private JmsClient orderBooksClient;
	
	private JmsClient orderAcceptedHandler;
	
	private JmsClient orderRejectedHandler;
	
	private MessageListener orderAcceptedListener;
	
	private MessageListener orderRejectedListener;
	
	private OrderRequestMessage orderRequestMessage;

	private OrderRejectedMessage orderRejectedMessage;

	private OrderAcceptedMessage orderAcceptedMessage;
	
	protected boolean orderAcceptedReceived;

	protected boolean orderRejectedReceived;

	private Object orderRejectedReason;

	
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
		createSupplierOrderCacheControl();
		createSupplierOrderCacheHelper();
		orderRequestMessage = createOrderRequestMessage();
		orderRejectedListener = createOrderRejectedResponseListener();
		orderAcceptedListener = createOrderAcceptedResponseListener();
	}
	
	protected void createTransactionControl() throws Exception {
		transactionTestControl = new TransactionTestControl();
		transactionTestControl.setupTransactionManager();
	}
	
	protected void createSupplierOrderCacheControl() throws Exception {
		supplierOrderCacheControl = new CacheModuleTestControl(transactionTestControl);
		supplierOrderCacheControl.setupCacheLayer();
	}
	
	@After
	public void tearDown() throws Exception {
		orderBooksClient.reset();
		orderBooksClient = null;
		orderAcceptedHandler.reset();
		orderAcceptedHandler = null;
		orderAcceptedListener = null;
		orderRejectedHandler.reset();
		orderRejectedHandler = null;
		orderRejectedListener = null;
		orderRequestMessage = null;
		orderAcceptedMessage = null;
		orderRejectedMessage = null;
		orderRejectedReceived = false;
		orderAcceptedReceived = false;
		super.tearDown();
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
	
	@TargetsContainer("hornetQ01_local")
	@Deployment(name = "sellerEAR", order = 3)
	public static EnterpriseArchive createSellerEAR() {
		SellerTestEARBuilder builder = new SellerTestEARBuilder();
		builder.setIncludeWar(true);
		builder.setDeployJms(false);
		return builder.createEAR();
	}
	
	@TargetsContainer("hornetQ01_local")
	@Deployment(name = "supplierEAR", order = 4)
	public static EnterpriseArchive createSupplierEAR() {
		SupplierTestEARBuilder builder = new SupplierTestEARBuilder();
		builder.setIncludeWar(true);
		builder.setDeployJms(false);
		return builder.createEAR();
	}

	@TargetsContainer("hornetQ01_local")
	@Deployment(name = "shipperEAR", order = 5)
	public static EnterpriseArchive createShipperEAR() {
		ShipperTestEARBuilder builder = new ShipperTestEARBuilder();
		builder.setIncludeWar(true);
		builder.setDeployJms(false);
		return builder.createEAR();
	}

	public void createSupplierOrderCacheHelper() throws Exception {
		supplierOrderCacheHelper = new SupplierOrderCacheHelper();
		supplierOrderCacheHelper.setProxy(createSupplierOrderCacheProxy());
		supplierOrderCacheHelper.initialize(supplierOrderCacheControl);
	}

	public SupplierOrderCacheProxy createSupplierOrderCacheProxy() throws Exception {
		supplierOrderCacheProxy = new SupplierOrderCacheProxy();
		supplierOrderCacheProxy.setJmxManager(jmxManager);
		return supplierOrderCacheProxy;
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

	@Test
	@InSequence(value = 1)
	public void testOrderBooks_OrderAccepted() throws Exception {
		String testName = "testOrderBooks_OrderAccepted";
		log.info(testName+": started");
		registerNotificationListeners();
		supplierOrderCacheHelper.clearSupplierOrderCache();
		supplierOrderCacheHelper.addBookListToSupplierOrderCache();
		//execution started
		runTest_queryBooks();
		//execution finished
		Assert.isTrue(orderAcceptedReceived);
		Assert.isFalse(orderRejectedReceived);
		Assert.isTrue(isFiredRequestDone("OrderBooks"));
		Assert.isTrue(isFiredResponseSent("OrderAccepted"));
		Assert.isFalse(isFiredResponseSent("OrderRejected"));
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	@InSequence(value = 2)
	public void testOrderBooks_OrderRejected_BooksUnavailable() throws Exception {
		String testName = "testOrderBooks_OrderRejected_BooksUnavailable";
		log.info(testName+": started");
		registerNotificationListeners();
		supplierOrderCacheHelper.clearSupplierOrderCache();
		orderRejectedReason = "Books Unavailable";
		//execution started
		runTest_queryBooks();
		//execution finished
		Assert.isFalse(orderAcceptedReceived);
		Assert.isTrue(orderRejectedReceived);
		Assert.isTrue(isFiredRequestDone("OrderBooks"));
		Assert.isFalse(isFiredResponseSent("OrderAccepted"));
		Assert.isTrue(isFiredResponseSent("OrderRejected"));
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	@Ignore
	@InSequence(value = 3)
	@BytemanRule(name = "rule3",
			targetClass = "SupplierProcess",
			targetMethod = "handle_QueryBooks_request",
			targetLocation = "AT EXIT",
			action = "throw new java.lang.RuntimeException(\"exception message\")")
	public void testOrderBooks_OrderRejected_Exception_QueryBooks() throws Exception {
		String testName = "testOrderBooks_OrderRejected_Exception_QueryBooks";
		log.info(testName+": started");
		setupByteman(testName);
		registerNotificationListeners();
		supplierOrderCacheHelper.clearSupplierOrderCache();
		orderRejectedReason = "Books Unavailable";
		//execution started
		runTest_queryBooks();
		//execution finished
		Assert.isFalse(orderAcceptedReceived);
		Assert.isTrue(orderRejectedReceived);
		Assert.isTrue(isFiredRequestDone("OrderBooks"));
		Assert.isFalse(isFiredResponseSent("OrderAccepted"));
		Assert.isTrue(isFiredResponseSent("OrderRejected"));
		tearDownByteman(testName);
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runTest_queryBooks() throws Exception {
		// prepare the environment
		removeMessagesFromDestinations();
		
		// prepare context
		//beginTransaction();
		
		// prepare mocks
		registerForResult();
		
		// start fixture execution
		orderBooksClient = startOrderBooksClient();
		orderAcceptedHandler = startLocalOrderAcceptedResponseHandler(orderAcceptedListener);
		orderRejectedHandler = startLocalOrderRejectedResponseHandler(orderRejectedListener);
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
		removeMessagesFromQueue(getTargetArchive(), getLocalOrderAcceptedDestination());
		removeMessagesFromQueue(getTargetArchive(), getLocalOrderRejectedDestination());
	}
	
	protected JmsClient startOrderBooksClient() throws Exception {
		JmsClient client = new OrderBooksProxyForJMS(OrderBooks.ID);
		configureClient(client, getTargetDestination());
		client.setCreateTemporaryQueue(true);
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
	
	protected MessageListener createOrderAcceptedResponseListener() {
		return new MessageListener() {
			public void onMessage(Message message) {
				try {
					log.info("#### [test]: OrderAccepted received");
					Object object = getObjectFromMessage(message);
					Assert.notNull(object, "Payload not found");
					Assert.isTrue(object instanceof OrderAcceptedMessage, "Payload type not correct");
					orderAcceptedMessage = (OrderAcceptedMessage) object;
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
					log.info("#### [test]: OrderRejected received");
					Object object = getObjectFromMessage(message);
					Assert.notNull(object, "Payload not found");
					Assert.isTrue(object instanceof OrderRejectedMessage, "Payload type not correct");
					orderRejectedMessage = (OrderRejectedMessage) object;
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
