package bookshop2.seller.incoming.purchaseBooks;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.aries.Assert;
import org.aries.jms.client.JmsClient;
import org.aries.jms.util.MessageUtil;
import org.aries.tx.AbstractArquillianTest;
import org.aries.tx.AbstractJMSListenerArquillionTest;
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
import org.junit.Test;
import org.junit.runner.RunWith;

import bookshop2.Invoice;
import bookshop2.Payment;
import bookshop2.PurchaseAcceptedMessage;
import bookshop2.PurchaseRejectedMessage;
import bookshop2.PurchaseRequestMessage;
import bookshop2.Shipment;
import bookshop2.ShipmentCompleteMessage;
import bookshop2.ShipmentFailedMessage;
import bookshop2.ShipmentRequestMessage;
import bookshop2.ShipmentScheduledMessage;
import bookshop2.seller.SellerTestEARBuilder;
import bookshop2.seller.client.purchaseBooks.PurchaseBooks;
import bookshop2.seller.client.purchaseBooks.PurchaseBooksProxyForJMS;
import bookshop2.seller.data.bookCache.BookCacheHelper;
import bookshop2.seller.data.bookCache.BookCacheProxy;
import bookshop2.util.Bookshop2Fixture;


@RunAsClient
@RunWith(Arquillian.class)
public class PurchaseBooksListenerForJMSCITGood extends AbstractJMSListenerArquillionTest {
	
	private TransactionTestControl transactionTestControl;
	
	private CacheModuleTestControl bookCacheControl;
	
	private BookCacheProxy bookCacheProxy;
	
	private BookCacheHelper bookCacheHelper;
	
	private JmsClient purchaseBooksClient;
	
	private JmsClient remoteShipBooksHandler;
	
	private JmsClient remoteShipmentCompleteSender;
	
	private JmsClient remoteShipmentFailedSender;
	
	private JmsClient remoteShipmentScheduledSender;
	
	private JmsClient localPurchaseAcceptedHandler;
	
	private JmsClient localPurchaseRejectedHandler;
	
	private PurchaseRequestMessage purchaseRequestMessage;

	private PurchaseAcceptedMessage purchaseAcceptedMessage;
	
	private PurchaseRejectedMessage purchaseRejectedMessage;
	
	private ShipmentRequestMessage shipmentRequestMessage;

	private ShipmentCompleteMessage shipmentCompleteMessage;
	
	private ShipmentFailedMessage shipmentFailedMessage;
	
	private ShipmentScheduledMessage shipmentScheduledMessage;
	
	private boolean shipmentRequestReceived;

	private boolean purchaseAcceptedReceived;

	private boolean purchaseRejectedReceived;
	
	private String shipmentFailedReason;

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
		return PurchaseBooksListenerForJMSCITGood.class;
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
		return "bookshop2.seller.purchaseBooks";
	}
	
	@Override
	public String getTargetArchive() {
		return SellerTestEARBuilder.NAME;
	}
	
	@Override
	public String getTargetDestination() {
		return getJNDINameForQueue("protected_bookshop2_seller_purchase_books_queue");
	}

	public String getRemoteShipBooksDestination() {
		return getJNDINameForQueue("inventory_bookshop2_supplier_ship_books_queue");
	}

	public String getRemoteShipmentCompleteDestination() {
		return getJNDINameForQueue("protected_bookshop2_seller_shipment_complete_queue");
	}

	public String getRemoteShipmentFailedDestination() {
		return getJNDINameForQueue("protected_bookshop2_seller_shipment_failed_queue");
	}

	public String getRemoteShipmentScheduledDestination() {
		return getJNDINameForQueue("protected_bookshop2_seller_shipment_scheduled_queue");
	}

	public String getLocalPurchaseAcceptedDestination() {
		return "test_message_domain_test_destination_queue_a";
	}

	public String getLocalPurchaseRejectedDestination() {
		return "test_message_domain_test_destination_queue_b";
	}
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		startServer();
		createTransactionControl();
		createBookCacheControl();
		createBookCacheHelper();
		remoteShipmentCompleteSender = createClient(getRemoteShipmentCompleteDestination());
		remoteShipmentFailedSender = createClient(getRemoteShipmentFailedDestination());
		remoteShipmentScheduledSender = createClient(getRemoteShipmentScheduledDestination());
	}
	
	protected void createTransactionControl() throws Exception {
		transactionTestControl = new TransactionTestControl();
		transactionTestControl.setupTransactionManager();
	}
	
	public void createBookCacheControl() throws Exception {
		bookCacheControl = new CacheModuleTestControl(transactionTestControl);
		bookCacheControl.setupCacheLayer();
	}
	
	public void createBookCacheProxy() throws Exception {
		bookCacheProxy = new BookCacheProxy();
		bookCacheProxy.setJmxManager(jmxManager);
	}
	
	public void createBookCacheHelper() throws Exception {
		bookCacheHelper = new BookCacheHelper();
		bookCacheHelper.setProxy(bookCacheProxy);
		bookCacheHelper.initialize(bookCacheControl);
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
		PurchaseRequestMessage message = Bookshop2Fixture.create_PurchaseRequestMessage(cancel, undo);
		message.addToReplyToDestinations("PurchaseAccepted", getLocalPurchaseAcceptedDestination());
		message.addToReplyToDestinations("PurchaseRejected", getLocalPurchaseRejectedDestination());
		initializeMessage(message);
		return message;
	}
	
	public ShipmentCompleteMessage createShipmentCompleteMessage() {
		ShipmentCompleteMessage message = Bookshop2Fixture.create_ShipmentCompleteMessage();
		initializeMessage(message);
		return message;
	}
	
	public ShipmentFailedMessage createShipmentFailedMessage() {
		ShipmentFailedMessage message = Bookshop2Fixture.create_ShipmentFailedMessage();
		initializeMessage(message);
		return message;
	}
	
	public ShipmentScheduledMessage createShipmentScheduledMessage() {
		ShipmentScheduledMessage message = Bookshop2Fixture.create_ShipmentScheduledMessage();
		initializeMessage(message);
		return message;
	}
	
	protected void registerNotificationListeners() throws Exception {
		addRequestNotificationListeners("PurchaseBooks");
		addResponseNotificationListeners("PurchaseAccepted");
		addResponseNotificationListeners("PurchaseRejected");
	}
	
	@After
	public void tearDown() throws Exception {
		clearStructures();
		clearState();
		super.tearDown();
	}
	
	protected void clearStructures() throws Exception {
		purchaseBooksClient.reset();
		purchaseBooksClient = null;

		//remote handler(s) for requests to remote (mocked) service(s)
		remoteShipBooksHandler.reset();
		remoteShipBooksHandler = null;

		//remote clients for sending responses back from remote (mocked) service(s)
		remoteShipmentCompleteSender.reset();
		remoteShipmentCompleteSender = null;
		remoteShipmentFailedSender.reset();
		remoteShipmentFailedSender = null;
		remoteShipmentScheduledSender.reset();
		remoteShipmentScheduledSender = null;

		//local handlers for responses from target service
		localPurchaseAcceptedHandler.reset();
		localPurchaseAcceptedHandler = null;
		localPurchaseRejectedHandler.reset();
		localPurchaseRejectedHandler = null;
		super.clearStructures();
	}

	protected void clearState() throws Exception {
		purchaseRequestMessage = null;
		purchaseAcceptedMessage = null;
		purchaseRejectedMessage = null;
		shipmentRequestMessage = null;
		shipmentCompleteMessage = null;
		shipmentFailedMessage = null;
		shipmentScheduledMessage = null;
		purchaseAcceptedReceived = false;
		purchaseRejectedReceived = false;
		shipmentRequestReceived = false;
		shipmentFailedReason = null;
		super.clearState();
	}
	
	protected void removeMessagesFromDestinations() throws Exception {
		removeMessagesFromQueue(getTargetArchive(), getTargetDestination());
		removeMessagesFromQueue(getTargetArchive(), getRemoteShipBooksDestination());
		removeMessagesFromQueue(getTargetArchive(), getRemoteShipmentCompleteDestination());
		removeMessagesFromQueue(getTargetArchive(), getRemoteShipmentFailedDestination());
		removeMessagesFromQueue(getTargetArchive(), getRemoteShipmentScheduledDestination());
		removeMessagesFromQueue(getTargetArchive(), getLocalPurchaseAcceptedDestination());
		removeMessagesFromQueue(getTargetArchive(), getLocalPurchaseRejectedDestination());
	}
	
	@TargetsContainer("hornetQ01_local")
	@Deployment(name = "sellerEAR", order = 2)
	public static EnterpriseArchive createTestEAR() {
		SellerTestEARBuilder builder = new SellerTestEARBuilder();
		builder.setIncludeWar(true);
		return builder.createEAR();
	}
	
	public void runTest_PurchaseBooks() throws Exception {
		runTest_PurchaseBooks(createPurchaseRequestMessage());
	}
	
	public void runTest_PurchaseBooks(PurchaseRequestMessage purchaseRequestMessage) throws Exception {
		
		// prepare the environment
		removeMessagesFromDestinations();
		
		// prepare context
		//beginTransaction();
		
		// prepare mocks
		registerForResult();
		
		// start handlers for calls to remote service(s)
		remoteShipBooksHandler = startRemoteShipBooksHandler();
		
		// start local handlers for responses from target service
		localPurchaseAcceptedHandler = startLocalPurchaseAcceptedHandler();
		localPurchaseRejectedHandler = startLocalPurchaseRejectedHandler();
		
		// start fixture execution
		purchaseBooksClient = startPurchaseBooksClient();
		purchaseBooksClient.send(purchaseRequestMessage, correlationId, null);
		
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
	
//	protected void sendRequest_PurchaseBooks() throws Exception {
//		purchaseBooksClient.send(purchaseRequestMessage, correlationId, null);
//	}
//
//	protected void sendRequest_PurchaseBooks_Cancel() throws Exception {
//		purchaseRequestMessage = createPurchaseRequestCancelMessage();
//		sendRequest_PurchaseBooks();
//	}
//
//	protected void sendRequest_PurchaseBooks_Undo() throws Exception {
//		purchaseRequestMessage = createPurchaseRequestUndoMessage();
//		sendRequest_PurchaseBooks();
//	}
	
	protected JmsClient startPurchaseBooksClient() throws Exception {
		JmsClient client = new PurchaseBooksProxyForJMS(PurchaseBooks.ID);
		configureClient(client, getTargetDestination());
		client.setCreateTemporaryQueue(true);
		client.initialize();
		return client;
	}
	
	protected JmsClient startRemoteShipBooksHandler() throws Exception {
		JmsClient client = createClient(getRemoteShipBooksDestination());
		client.setMessageListener(createRemoteShipBooksListener());
		client.initialize();
		return client;
	}
	
	protected JmsClient startLocalPurchaseAcceptedHandler() throws Exception {
		JmsClient client = createClient(getJNDINameForQueue(getLocalPurchaseAcceptedDestination()));
		client.setMessageListener(createLocalPurchaseAcceptedListener());
		client.initialize();
		return client;
	}
	
	protected JmsClient startLocalPurchaseRejectedHandler() throws Exception {
		JmsClient client = createClient(getJNDINameForQueue(getLocalPurchaseRejectedDestination()));
		client.setMessageListener(createLocalPurchaseRejectedListener());
		client.initialize();
		return client;
	}
	
	protected MessageListener createRemoteShipBooksListener() {
		return new MessageListener() {
			public void onMessage(Message message) {
				try {
					log.info("#### [test]: ShipBooks: received");
					Object object = MessageUtil.getPart(message, "shipmentRequestMessage");
					Assert.isTrue(object instanceof ShipmentRequestMessage, "Payload type not correct");
					shipmentRequestMessage = (ShipmentRequestMessage) object;
					validateMessage(shipmentRequestMessage);
					process(shipmentRequestMessage);
				} catch (Throwable e) {
					errorMessage = ExceptionUtils.getRootCauseMessage(e);
					e.printStackTrace();
				} finally {
					if (expectedMessage != null && expectedMessage.equalsIgnoreCase("bookshop2.supplier.ShipBooks"))
						expectedMessageResult.set(true);
						shipmentRequestReceived = true;
				}
			}
		};
	}
	
	protected void process(ShipmentRequestMessage shipmentRequestMessage) {
		Shipment shipment = shipmentRequestMessage.getShipment();
		Payment payment = shipmentRequestMessage.getPayment();
		if (!isExceptionExpected) {
			sendShipmentScheduledMessage(shipment);
			Invoice invoice = Bookshop2Fixture.create_Invoice();
			sendShipmentCompleteMessage(shipment, invoice);
		} else {
			String reason = shipmentFailedReason;
			sendShipmentFailedMessage(shipment, reason);
		}
	}

	protected void sendShipmentScheduledMessage(Shipment shipment) {
		shipmentScheduledMessage = createShipmentScheduledMessage();
		shipmentScheduledMessage.setShipment(shipment);
		remoteShipmentScheduledSender.sendResponse(shipmentScheduledMessage);
	}
	
	protected void sendShipmentCompleteMessage(Shipment shipment, Invoice invoice) {
		shipmentCompleteMessage = createShipmentCompleteMessage();
		shipmentCompleteMessage.setShipment(shipment);
		shipmentCompleteMessage.setInvoice(invoice);
		remoteShipmentCompleteSender.sendResponse(shipmentCompleteMessage);
	}
	
	protected void sendShipmentFailedMessage(Shipment shipment, String reason) {
		shipmentFailedMessage = createShipmentFailedMessage();
		shipmentFailedMessage.setShipment(shipment);
		shipmentFailedMessage.setReason(reason);
		remoteShipmentFailedSender.sendResponse(shipmentFailedMessage);
	}
	

	protected MessageListener createLocalPurchaseAcceptedListener() {
		return new MessageListener() {
			public void onMessage(Message message) {
				try {
					log.info("#### [test]: PurchaseAccepted: received");
					Object object = MessageUtil.getPart(message, "purchaseAcceptedMessage");
					Assert.isTrue(object instanceof PurchaseAcceptedMessage, "Payload type not correct");
					purchaseAcceptedMessage = (PurchaseAcceptedMessage) object;
					validateMessage(purchaseAcceptedMessage);
				} catch (Throwable e) {
					errorMessage = ExceptionUtils.getRootCauseMessage(e);
					e.printStackTrace();
				} finally {
					if (expectedMessage != null && expectedMessage.equalsIgnoreCase("bookshop2.seller.PurchaseAccepted"))
						expectedMessageResult.set(true);
					purchaseAcceptedReceived = true;
				}
			}
		};
	}
	
	protected MessageListener createLocalPurchaseRejectedListener() {
		return new MessageListener() {
			public void onMessage(Message message) {
				try {
					log.info("#### [test]: PurchaseRejected: received");
					Object object = MessageUtil.getPart(message, "purchaseRejectedMessage");
					Assert.isTrue(object instanceof PurchaseRejectedMessage, "Payload type not correct");
					purchaseRejectedMessage = (PurchaseRejectedMessage) object;
					validateMessage(purchaseRejectedMessage);
				} catch (Throwable e) {
					errorMessage = ExceptionUtils.getRootCauseMessage(e);
					e.printStackTrace();
				} finally {
					if (expectedMessage != null && expectedMessage.equalsIgnoreCase("bookshop2.seller.PurchaseRejected"))
						expectedMessageResult.set(true);
					purchaseRejectedReceived = true;
				}
			}
		};
	}

	protected void validateResult(Object result) throws Exception {
		if (result instanceof PurchaseAcceptedMessage) {
			validateMessage((PurchaseAcceptedMessage) result);
		} else if (result instanceof PurchaseRejectedMessage) {
			validateMessage((PurchaseRejectedMessage) result);
		} else if (result instanceof String) {
			String resultString = result.toString();
			if ((expectedError != null && !expectedError.equalsIgnoreCase(resultString)) &&
				(expectedEvent != null && !expectedEvent.equalsIgnoreCase(resultString)))
					errorMessage = "Unexpected message: "+result;
		} else {
			errorMessage = "Unrecognized result: "+result;
		}
	}
	
	protected void validateMessage(ShipmentRequestMessage shipmentRequestMessage) throws Exception {
		Assert.notNull(shipmentRequestMessage, "Message must be specified");
		Bookshop2Fixture.assertShipmentCorrect(shipmentRequestMessage.getShipment());
		Bookshop2Fixture.assertPaymentCorrect(shipmentRequestMessage.getPayment());
	}
	
	protected void validateMessage(PurchaseAcceptedMessage purchaseAcceptedMessage) throws Exception {
		Assert.notNull(purchaseAcceptedMessage, "Message must be specified");
		Bookshop2Fixture.assertOrderCorrect(purchaseAcceptedMessage.getOrder());
		Bookshop2Fixture.assertPaymentCorrect(purchaseAcceptedMessage.getPayment());
		Bookshop2Fixture.assertInvoiceCorrect(purchaseAcceptedMessage.getInvoice());
	}
	
	protected void validateMessage(PurchaseRejectedMessage purchaseRejectedMessage) throws Exception {
		Assert.notNull(purchaseRejectedMessage, "Message must be specified");
		Bookshop2Fixture.assertOrderCorrect(purchaseRejectedMessage.getOrder());
		Bookshop2Fixture.assertPaymentCorrect(purchaseRejectedMessage.getPayment());
		Assert.equals(purchaseRejectedMessage.getReason(), shipmentFailedReason);
	}

	@Test
	@InSequence(value = 1)
	public void test_PurchaseBooks_PurchaseAccepted_From_Response() throws Exception {
		String testName = "test_PurchaseBooks_PurchaseAccepted_From_Response";
		log.info(testName+": started");
		
		registerNotificationListeners();
		expectedEvent = "bookshop2.seller.PurchaseBooks_Request_Done";
		expectedMessage = "bookshop2.seller.PurchaseAccepted";

		// execute test
		runTest_PurchaseBooks();
		
		//validate successful callback state
		Assert.isTrue(isFiredResponseSent("PurchaseAccepted"));
		Assert.isTrue(purchaseAcceptedReceived);

		//validate non-existant callback state
		Assert.isFalse(isFiredResponseSent("PurchaseRejected"));
		Assert.isFalse(purchaseRejectedReceived);
		
		//validate request completion state
		Assert.isTrue(isFiredRequestDone("PurchaseBooks"));
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 2)
	public void test_PurchaseBooks_PurchaseRejected_From_Response() throws Exception {
		String testName = "test_PurchaseBooks_PurchaseRejected_From_Response";
		log.info(testName+": started");
		
		registerNotificationListeners();
		expectedEvent = "bookshop2.seller.PurchaseBooks_Request_Done";
		expectedMessage = "bookshop2.seller.PurchaseRejected";
		shipmentFailedReason = "shipment failed";
		isExceptionExpected = true;
		
		// execute test
		runTest_PurchaseBooks();

		//validate successful callback state
		Assert.isTrue(isFiredResponseSent("PurchaseRejected"));
		Assert.isTrue(purchaseRejectedReceived);

		//validate non-existant callback state
		Assert.isFalse(isFiredResponseSent("PurchaseAccepted"));
		Assert.isFalse(purchaseAcceptedReceived);

		//validate request completion state
		Assert.isTrue(isFiredRequestDone("PurchaseBooks"));
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
}
