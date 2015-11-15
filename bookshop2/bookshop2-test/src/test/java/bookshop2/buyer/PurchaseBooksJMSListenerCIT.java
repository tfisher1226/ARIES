package bookshop2.buyer;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.aries.Assert;
import org.aries.common.AbstractEvent;
import org.aries.event.multicaster.EventMulticasterProxy;
import org.aries.jms.client.JmsClient;
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
import org.junit.Test;
import org.junit.runner.RunWith;

import bookshop2.PurchaseAcceptedMessage;
import bookshop2.PurchaseRejectedMessage;
import bookshop2.PurchaseRequestMessage;
import bookshop2.ShipmentCompleteEvent;
import bookshop2.ShipmentConfirmedEvent;
import bookshop2.ShipmentScheduledEvent;
import bookshop2.buyer.client.purchaseBooks.PurchaseBooks;
import bookshop2.buyer.client.purchaseBooks.PurchaseBooksProxyForJMS;
import bookshop2.buyer.outgoing.purchaseAccepted.PurchaseAcceptedReply;
import bookshop2.buyer.outgoing.purchaseRejected.PurchaseRejectedReply;
import bookshop2.seller.SellerTestEARBuilder;
import bookshop2.shipper.ShipperTestEARBuilder;
import bookshop2.shipper.event.ShipperEventReceiverMBean;
import bookshop2.supplier.SupplierTestEARBuilder;
import bookshop2.util.Bookshop2Fixture;


@RunAsClient
@RunWith(Arquillian.class)
public class PurchaseBooksJMSListenerCIT extends AbstractJMSListenerArquillionTest {
	
	private TransactionTestControl transactionTestControl;
	
	private EventMulticasterProxy eventMulticasterProxy;

	private JmsClient purchaseBooksClient;
	
	private JmsClient purchaseAcceptedHandler;
	
	private JmsClient purchaseRejectedHandler;
	
	private MessageListener purchaseAcceptedListener;
	
	private MessageListener purchaseRejectedListener;
	
	private PurchaseRequestMessage purchaseRequestMessage;

	private PurchaseRejectedMessage purchaseRejectedMessage;

	private PurchaseAcceptedMessage purchaseAcceptedMessage;
	
	private ShipmentScheduledEvent shipmentScheduledEvent;

	private ShipmentConfirmedEvent shipmentConfirmedEvent;

	private ShipmentCompleteEvent shipmentCompleteEvent;
	
	private boolean purchaseRejectedReceived;

	private boolean purchaseAcceptedReceived;

	private String purchaseRejectedReason;

	
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
		return "bookshop2.buyer.PurchaseBooks";
	}
	
	@Override
	public String getTargetArchive() {
		return BuyerTestEARBuilder.NAME;
	}
	
	@Override
	public String getTargetDestination() {
		return getJNDINameForQueue("public_bookshop2_buyer_purchase_books_queue");
	}

	//outgoing response back to caller
	public String getLocalPurchaseAcceptedDestination() {
		return "test_message_domain_test_destination_queue_a";
	}

	//outgoing response back to caller
	public String getLocalPurchaseRejectedDestination() {
		return "test_message_domain_test_destination_queue_b";
	}

	@Override
	public Class<?> getTestClass() {
		return PurchaseBooksJMSListenerCIT.class;
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
		createEventMulticasterProxy();
		purchaseRequestMessage = createPurchaseRequestMessage();
		purchaseRejectedListener = createPurchaseRejectedResponseListener();
		purchaseAcceptedListener = createPurchaseAcceptedResponseListener();
	}
	
	protected void createTransactionControl() throws Exception {
		transactionTestControl = new TransactionTestControl();
		transactionTestControl.setupTransactionManager();
	}
	
	public EventMulticasterProxy createEventMulticasterProxy() throws Exception {
		eventMulticasterProxy = new EventMulticasterProxy();
		eventMulticasterProxy.setJmxManager(jmxManager);
		eventMulticasterProxy.setMBeanName(ShipperEventReceiverMBean.MBEAN_NAME);
		return eventMulticasterProxy;
	}
	
	@After
	public void tearDown() throws Exception {
		purchaseBooksClient.reset();
		purchaseBooksClient = null;
		purchaseAcceptedHandler.reset();
		purchaseAcceptedHandler = null;
		purchaseAcceptedListener = null;
		purchaseRejectedHandler.reset();
		purchaseRejectedHandler = null;
		purchaseRejectedListener = null;
		purchaseRequestMessage = null;
		purchaseAcceptedMessage = null;
		purchaseRejectedMessage = null;
		purchaseRejectedReceived = false;
		purchaseAcceptedReceived = false;
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

	public PurchaseRequestMessage createPurchaseRequestMessage() {
		return createPurchaseRequestMessage(false, false);
	}
	
	public PurchaseRequestMessage createPurchaseRequestCancelMessage() {
		return createPurchaseRequestMessage(true, false);
	}
	
	public PurchaseRequestMessage createPurchaseRequestUndoMessage() {
		return createPurchaseRequestMessage(false, true);
	}
	
	public PurchaseRequestMessage createPurchaseRequestMessage(boolean cancel, boolean undo) {
		PurchaseRequestMessage purchaseRequestMessage = Bookshop2Fixture.create_PurchaseRequestMessage(cancel, undo);
		purchaseRequestMessage.addToReplyToDestinations("PurchaseAccepted", getLocalPurchaseAcceptedDestination());
		purchaseRequestMessage.addToReplyToDestinations("PurchaseRejected", getLocalPurchaseRejectedDestination());
		return initializeMessage(purchaseRequestMessage);
	}

	public void createShipmentScheduledEvent() {
		shipmentScheduledEvent = Bookshop2Fixture.create_ShipmentScheduledEvent();
		initializeEvent(shipmentScheduledEvent);
	}
	
	public void createShipmentConfirmedEvent() {
		shipmentConfirmedEvent = Bookshop2Fixture.create_ShipmentConfirmedEvent();
		initializeEvent(shipmentConfirmedEvent);
	}
	
	public void createShipmentCompleteEvent() {
		shipmentCompleteEvent = Bookshop2Fixture.create_ShipmentCompleteEvent();
		initializeEvent(shipmentCompleteEvent);
	}
	
	@Test
	@InSequence(value = 1)
	public void testPurchaseBooks_PurchaseAccepted() throws Exception {
		String testName = "testPurchaseBooks_PurchaseAccepted";
		log.info(testName+": started");
		registerNotificationListeners();
		createShipmentScheduledEvent();
		createShipmentConfirmedEvent();
		//execution started
		runTest_PurchaseBooks();
		//execution finished
		Assert.isTrue(purchaseAcceptedReceived);
		Assert.isFalse(purchaseRejectedReceived);
		Assert.isTrue(isFiredRequestDone("PurchaseBooks"));
		Assert.isTrue(isFiredResponseSent("PurchaseAccepted"));
		Assert.isFalse(isFiredResponseSent("PurchaseRejected"));
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 2)
	@BytemanRule(name = "rule2",
			targetClass = "ShipmentScheduledExecutor",
			targetMethod = "register",
			targetLocation = "AT ENTRY",
			action = "$0.timeout = 0")
	public void testPurchaseBooks_PurchaseRejected() throws Exception {
		String testName = "testPurchaseBooks_PurchaseRejected";
		log.info(testName+": started");
		setupByteman(testName);
		registerNotificationListeners();
		expectedError = "ShipmentScheduled timed-out";
		purchaseRejectedReason = "ShipmentScheduled timed-out";
		//execution started
		runTest_PurchaseBooks();
		//execution finished
		Assert.isFalse(purchaseAcceptedReceived);
		Assert.isTrue(purchaseRejectedReceived);
		Assert.isTrue(isFiredRequestDone("PurchaseBooks"));
		Assert.isFalse(isFiredResponseSent("PurchaseAccepted"));
		Assert.isTrue(isFiredResponseSent("PurchaseRejected"));
		tearDownByteman(testName);
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runTest_PurchaseBooks() throws Exception {
		// prepare the environment
		removeMessagesFromDestinations();
		
		// prepare context
		//beginTransaction();
		
		// prepare mocks
		registerForResult();
		
		// start fixture execution
		purchaseBooksClient = startPurchaseBooksClient();
		purchaseAcceptedHandler = startLocalPurchaseAcceptedResponseHandler(purchaseAcceptedListener);
		purchaseRejectedHandler = startLocalPurchaseRejectedResponseHandler(purchaseRejectedListener);
		sendRequest_PurchaseBooks();
		
		if (eventsExist())
			fireEvents();
		
		// wait for result
		Object result = waitForCompletion();
		validateResult(result);
		
		// close context
		//commitTransaction();
		
		// validate the environment
		assertEmptyTargetDestination();
		removeMessagesFromDestinations();
	}
	
	protected void sendRequest_PurchaseBooks() throws Exception {
		purchaseBooksClient.send(purchaseRequestMessage, correlationId, null);
	}

	protected void sendRequest_PurchaseBooks_Cancel() throws Exception {
		purchaseRequestMessage = createPurchaseRequestCancelMessage();
		sendRequest_PurchaseBooks();
	}

	protected void sendRequest_PurchaseBooks_Undo() throws Exception {
		purchaseRequestMessage = createPurchaseRequestUndoMessage();
		sendRequest_PurchaseBooks();
	}
	
	protected boolean eventsExist() {
		return shipmentScheduledEvent != null || 
			shipmentConfirmedEvent != null || 
			shipmentCompleteEvent != null;
	}

	protected void fireEvents() {
		Thread t = new Thread(new Runnable() {
			public void run() {
				try {
					waitForEvent("bookshop2.shipper.ShipBooks_Request_Received");
					if (shipmentScheduledEvent != null)
						fireEvent(shipmentScheduledEvent);
					if (shipmentConfirmedEvent != null)
						fireEvent(shipmentConfirmedEvent);
					if (shipmentCompleteEvent != null)
						fireEvent(shipmentCompleteEvent);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		t.start();
	}

	protected void fireEvent(AbstractEvent event) throws Exception {
		eventMulticasterProxy.dispatchEvent(event);
	}
	
	protected void registerNotificationListeners() throws Exception {
		addRequestNotificationListeners("PurchaseBooks");
		addResponseNotificationListeners("PurchaseAccepted");
		addResponseNotificationListeners("PurchaseRejected");
	}

	protected void removeMessagesFromDestinations() throws Exception {
		removeMessagesFromQueue(getTargetArchive(), getTargetDestination());
		removeMessagesFromQueue(getTargetArchive(), getLocalPurchaseAcceptedDestination());
		removeMessagesFromQueue(getTargetArchive(), getLocalPurchaseRejectedDestination());
	}
	
	protected JmsClient startPurchaseBooksClient() throws Exception {
		JmsClient client = new PurchaseBooksProxyForJMS(PurchaseBooks.ID);
		configureClient(client, getTargetDestination());
		//client.setMessageListener(responseListener);
		client.setCreateTemporaryQueue(true);
		client.initialize();
		return client;
	}
	
	protected JmsClient startLocalPurchaseAcceptedResponseHandler(MessageListener messageListener) throws Exception {
		JmsClient client = createClient(getJNDINameForQueue(getLocalPurchaseAcceptedDestination()));
		client.setMessageListener(messageListener);
		client.initialize();
		return client;
	}
	
	protected JmsClient startLocalPurchaseRejectedResponseHandler(MessageListener messageListener) throws Exception {
		JmsClient client = createClient(getJNDINameForQueue(getLocalPurchaseRejectedDestination()));
		client.setMessageListener(messageListener);
		client.initialize();
		return client;
	}
	
	protected MessageListener createPurchaseAcceptedResponseListener() {
		return new MessageListener() {
			public void onMessage(Message message) {
				try {
					log.info("#### [test]: PurchaseAccepted received");
					Object object = getObjectFromMessage(message);
					Assert.notNull(object, "Payload not found");
					Assert.isTrue(object instanceof PurchaseAcceptedMessage, "Payload type not correct");
					purchaseAcceptedMessage = (PurchaseAcceptedMessage) object;
					validate(purchaseAcceptedMessage);
				} catch (Throwable e) {
					errorMessage = ExceptionUtils.getRootCauseMessage(e);
				} finally {
					if (expectedMessage != null && expectedMessage.equalsIgnoreCase("bookshop2.buyer.PurchaseAccepted"))
						expectedMessageResult.set(true);
					purchaseAcceptedReceived = true;
				}
			}
		};
	}
	
	protected MessageListener createPurchaseRejectedResponseListener() {
		return new MessageListener() {
			public void onMessage(Message message) {
				try {
					log.info("#### [test]: PurchaseRejected received");
					Object object = getObjectFromMessage(message);
					Assert.notNull(object, "Payload not found");
					Assert.isTrue(object instanceof PurchaseRejectedMessage, "Payload type not correct");
					purchaseRejectedMessage = (PurchaseRejectedMessage) object;
					validate(purchaseRejectedMessage);
				} catch (Throwable e) {
					errorMessage = ExceptionUtils.getRootCauseMessage(e);
				} finally {
					if (expectedMessage != null && expectedMessage.equalsIgnoreCase("bookshop2.buyer.PurchaseRejected"))
						expectedMessageResult.set(true);
					purchaseRejectedReceived = true;
				}
			}
		};
	}

	protected void validateResult(Object result) {
		if (result instanceof PurchaseAcceptedMessage) {
			validate((PurchaseAcceptedMessage) result);
		} else if (result instanceof PurchaseRejectedMessage) {
			validate((PurchaseRejectedMessage) result);
		} else if (result instanceof String) {
			String resultString = result.toString();
			if ((expectedError != null && !expectedError.equalsIgnoreCase(resultString)) &&
				(expectedEvent != null && !expectedEvent.equalsIgnoreCase(resultString)))
					errorMessage = "Unexpected message: "+result;
		} else {
			errorMessage = "Unrecognized result: "+result;
		}
	}
	
	protected void validate(PurchaseAcceptedMessage purchaseAcceptedMessage) {
		Assert.notNull(purchaseAcceptedMessage, "Message must be specified");
		Bookshop2Fixture.assertOrderCorrect(purchaseAcceptedMessage.getOrder());
		Bookshop2Fixture.assertPaymentCorrect(purchaseAcceptedMessage.getPayment());
		Bookshop2Fixture.assertInvoiceCorrect(purchaseAcceptedMessage.getInvoice());
	}
	
	protected void validate(PurchaseRejectedMessage purchaseRejectedMessage) {
		Assert.notNull(purchaseRejectedMessage, "Message must be specified");
		Bookshop2Fixture.assertOrderCorrect(purchaseRejectedMessage.getOrder());
		Bookshop2Fixture.assertPaymentCorrect(purchaseRejectedMessage.getPayment());
		Assert.equals(purchaseRejectedMessage.getReason(), purchaseRejectedReason);
	}

}
