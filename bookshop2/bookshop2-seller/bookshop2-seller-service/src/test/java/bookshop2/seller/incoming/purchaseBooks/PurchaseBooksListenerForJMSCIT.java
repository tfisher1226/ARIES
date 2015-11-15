package bookshop2.seller.incoming.purchaseBooks;

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

import bookshop2.PurchaseAcceptedMessage;
import bookshop2.PurchaseRejectedMessage;
import bookshop2.PurchaseRequestMessage;
import bookshop2.ShipmentCompleteMessage;
import bookshop2.ShipmentFailedMessage;
import bookshop2.ShipmentRequestMessage;
import bookshop2.ShipmentScheduledMessage;
import bookshop2.seller.SellerTestEARBuilder;
import bookshop2.seller.client.purchaseBooks.PurchaseBooks;
import bookshop2.seller.client.purchaseBooks.PurchaseBooksProxyForJMS;
import bookshop2.seller.data.bookCache.BookCacheHelper;
import bookshop2.seller.data.bookCache.BookCacheProxy;
import bookshop2.seller.outgoing.purchaseAccepted.PurchaseAcceptedReply;
import bookshop2.seller.outgoing.purchaseRejected.PurchaseRejectedReply;
import bookshop2.util.Bookshop2Fixture;


@RunAsClient
@RunWith(Arquillian.class)
public class PurchaseBooksListenerForJMSCIT extends AbstractJMSListenerArquillionTest {
	
	private JmsClient purchaseBooksClient;
	
	private JmsClient remoteSupplierShipBooksHandler;
	
	private JmsClient remoteSupplierShipmentCompleteSender;
	
	private JmsClient remoteSupplierShipmentFailedSender;
	
	private JmsClient remoteSupplierShipmentScheduledSender;
	
	private JmsClient localSellerPurchaseAcceptedHandler;
	
	private JmsClient localSellerPurchaseRejectedHandler;
	
	private PurchaseRequestMessage purchaseRequestMessage;

	private PurchaseAcceptedMessage purchaseAcceptedMessage;
	
	private PurchaseRejectedMessage purchaseRejectedMessage;
	
	private ShipmentRequestMessage supplierShipmentRequestMessage;

	private ShipmentCompleteMessage supplierShipmentCompleteMessage;
	
	private ShipmentFailedMessage supplierShipmentFailedMessage;
	
	private ShipmentScheduledMessage supplierShipmentScheduledMessage;
	
	private TransactionTestControl transactionTestControl;
	
	private BookCacheHelper bookCacheHelper;
	
	private boolean shipmentRequestReceived;

	private boolean purchaseAcceptedReceived;

	private boolean purchaseRejectedReceived;
	

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
		return PurchaseBooksListenerForJMSCIT.class;
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
		return get_target_Seller_PurchaseBooks_destination();
	}
	
	public String get_target_Seller_PurchaseBooks_destination() {
		return getJNDINameForQueue("protected_bookshop2_seller_purchase_books_queue");
	}

	public String get_remote_Supplier_ShipBooks_destination() {
		return getJNDINameForQueue("inventory_bookshop2_supplier_ship_books_queue");
	}

	public String get_remote_Seller_ShipmentComplete_destination() {
		return getJNDINameForQueue("protected_bookshop2_seller_shipment_complete_queue");
	}

	public String get_remote_Seller_ShipmentFailed_destination() {
		return getJNDINameForQueue("protected_bookshop2_seller_shipment_failed_queue");
	}

	public String get_remote_Seller_ShipmentScheduled_destination() {
		return getJNDINameForQueue("protected_bookshop2_seller_shipment_scheduled_queue");
	}

	public String get_local_Seller_PurchaseAccepted_destination() {
		return "test_message_domain_test_destination_queue_a";
	}

	public String get_local_Seller_PurchaseRejected_destination() {
		return "test_message_domain_test_destination_queue_b";
	}
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		startServer();
		createTransactionControl();
		createBookCacheHelper();
		remoteSupplierShipmentCompleteSender = createClient(get_remote_Seller_ShipmentComplete_destination());
		remoteSupplierShipmentFailedSender = createClient(get_remote_Seller_ShipmentFailed_destination());
		remoteSupplierShipmentScheduledSender = createClient(get_remote_Seller_ShipmentScheduled_destination());
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
		message.addToReplyToDestinations("PurchaseAccepted", get_local_Seller_PurchaseAccepted_destination());
		message.addToReplyToDestinations("PurchaseRejected", get_local_Seller_PurchaseRejected_destination());
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
		addRequestNotificationListeners("Seller_PurchaseBooks");
		addResponseNotificationListeners("Seller_PurchaseAccepted");
		addResponseNotificationListeners("Seller_PurchaseRejected");
		addRequestNotificationListeners("Supplier_ShipBooks");
		addResponseNotificationListeners("Supplier_ShipmentComplete");
		addResponseNotificationListeners("Supplier_ShipmentFailed");
		addResponseNotificationListeners("Supplier_ShipmentScheduled");
		super.registerNotificationListeners();
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
		remoteSupplierShipBooksHandler.reset();
		remoteSupplierShipBooksHandler = null;

		//remote clients for sending responses back from remote (mocked) service(s)
		remoteSupplierShipmentCompleteSender.reset();
		remoteSupplierShipmentCompleteSender = null;
		remoteSupplierShipmentFailedSender.reset();
		remoteSupplierShipmentFailedSender = null;
		remoteSupplierShipmentScheduledSender.reset();
		remoteSupplierShipmentScheduledSender = null;

		//local handlers for responses from target service
		localSellerPurchaseAcceptedHandler.reset();
		localSellerPurchaseAcceptedHandler = null;
		localSellerPurchaseRejectedHandler.reset();
		localSellerPurchaseRejectedHandler = null;
		super.clearStructures();
	}

	protected void clearState() throws Exception {
		purchaseRequestMessage = null;
		purchaseAcceptedMessage = null;
		purchaseRejectedMessage = null;
		supplierShipmentRequestMessage = null;
		shipmentRequestReceived = false;
		purchaseAcceptedReceived = false;
		purchaseRejectedReceived = false;
		super.clearState();
	}
	
	protected void removeMessagesFromDestinations() throws Exception {
		removeMessagesFromQueue(getTargetArchive(), get_target_Seller_PurchaseBooks_destination());
		removeMessagesFromQueue(getTargetArchive(), get_local_Seller_PurchaseAccepted_destination());
		removeMessagesFromQueue(getTargetArchive(), get_local_Seller_PurchaseRejected_destination());
		removeMessagesFromQueue(getTargetArchive(), get_remote_Supplier_ShipBooks_destination());
		removeMessagesFromQueue(getTargetArchive(), get_remote_Seller_ShipmentComplete_destination());
		removeMessagesFromQueue(getTargetArchive(), get_remote_Seller_ShipmentFailed_destination());
		removeMessagesFromQueue(getTargetArchive(), get_remote_Seller_ShipmentScheduled_destination());
	}
	
	@TargetsContainer("hornetQ01_local")
	@Deployment(name = "sellerEAR", order = 2)
	public static EnterpriseArchive createSellerEAR() {
		SellerTestEARBuilder builder = new SellerTestEARBuilder();
		builder.setRunningAsClient(true);
		return builder.createEAR();
	}
	
	public void runAction_send_PurchaseBooks() throws Exception {
		runAction_send_PurchaseBooks(createPurchaseRequestMessage());
	}
	
	public void runAction_send_PurchaseBooks(PurchaseRequestMessage purchaseRequestMessage) throws Exception {
		this.purchaseRequestMessage = purchaseRequestMessage;
		
		// prepare the environment
		removeMessagesFromDestinations();
		
		// prepare context
		//beginTransaction();
		
		// prepare mocks
		registerForResult();
		
		// start handlers for calls to remote service(s)
		remoteSupplierShipBooksHandler = start_remote_Supplier_ShipBooks_handler();
		
		// start local handlers for responses from target service
		localSellerPurchaseAcceptedHandler = start_local_Seller_PurchaseAccepted_handler();
		localSellerPurchaseRejectedHandler = start_local_Seller_PurchaseRejected_handler();
		
		// start fixture execution
		purchaseBooksClient = start_PurchaseBooks_client();
		purchaseBooksClient.send(purchaseRequestMessage, correlationId, null);
		
		// wait for completion result
		Object result = waitForCompletion();
		validateResult(result);
		
		// close context
		//commitTransaction();
		
		// validate state
		assertEmptyTargetDestination();
	}
	
	protected void runAction_send_PurchaseBooks_cancel() throws Exception {
		expectedEvent = "Seller_PurchaseBooks_Request_Cancel_Done";
		registerForResult(expectedEvent);
		
		PurchaseRequestMessage message = createPurchaseRequestCancelMessage();
		purchaseBooksClient.send(message, correlationId, null);
		
		Object result = waitForCompletion();
		validateResult(result);
	}
	
	protected void runAction_send_PurchaseBooks_undo() throws Exception {
		expectedEvent = "Seller_PurchaseBooks_Request_Undo_Done";
		registerForResult(expectedEvent);
		
		PurchaseRequestMessage message = createPurchaseRequestUndoMessage();
		purchaseBooksClient.send(message, correlationId, null);
		
		Object result = waitForCompletion();
		validateResult(result);
	}
	
	public Thread start_runAction_send_PurchaseBooks() throws Exception {
		Thread thread = new Thread() {
			public void run() {
				try {
					runAction_send_PurchaseBooks();
				} catch (Exception e) {
					errorMessage = e.getMessage();
				}
			}
		};
		thread.start();
		Thread.sleep(4000);
		return thread;
	}
	
	protected JmsClient start_PurchaseBooks_client() throws Exception {
		JmsClient client = new PurchaseBooksProxyForJMS(PurchaseBooks.ID);
		configureClient(client, getTargetDestination());
		client.setCreateTemporaryQueue(true);
		client.initialize();
		return client;
	}
	
	protected JmsClient start_remote_Supplier_ShipBooks_handler() throws Exception {
		JmsClient client = createClient(get_remote_Supplier_ShipBooks_destination());
		client.setMessageListener(create_remote_Supplier_ShipBooks_message_listener());
		client.initialize();
		return client;
	}
	
	protected JmsClient start_local_Seller_PurchaseAccepted_handler() throws Exception {
		JmsClient client = createClient(getJNDINameForQueue(get_local_Seller_PurchaseAccepted_destination()));
		client.setMessageListener(create_local_Seller_PurchaseAccepted_response_listener());
		client.initialize();
		return client;
	}
	
	protected JmsClient start_local_Seller_PurchaseRejected_handler() throws Exception {
		JmsClient client = createClient(getJNDINameForQueue(get_local_Seller_PurchaseRejected_destination()));
		client.setMessageListener(create_local_Seller_PurchaseRejected_response_listener());
		client.initialize();
		return client;
	}
	
	protected MessageListener create_remote_Supplier_ShipBooks_message_listener() {
		return new MessageListener() {
			public void onMessage(Message message) {
				try {
					String jmsMessageID = message.getJMSMessageID();
					log.info("#### [test]: ShipBooks: received: "+jmsMessageID);
					Object object = MessageUtil.getPart(message, "shipmentRequestMessage");
					Assert.isTrue(object instanceof ShipmentRequestMessage, "Payload type not correct");
					ShipmentRequestMessage shipmentRequestMessage = (ShipmentRequestMessage) object;
					switch (getActionFromMessage(shipmentRequestMessage)) {
					case CANCEL:
					case UNDO:
						break;
					default:
						validateMessage(supplierShipmentRequestMessage);
						processMessage(supplierShipmentRequestMessage);
						break;
					}
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
	
	protected void processMessage(ShipmentRequestMessage shipmentRequestMessage) throws Exception {
		if (expectedCallback == null)
			return;
		
		if (expectedCallback.equals("ShipmentComplete")) {
			log.info("#### [test]: ShipmentComplete: sending");
			initializeMessage(supplierShipmentCompleteMessage, shipmentRequestMessage);
			remoteSupplierShipmentCompleteSender.sendResponse(supplierShipmentCompleteMessage);
		
		} else if (expectedCallback.equals("ShipmentFailed")) {
			log.info("#### [test]: ShipmentFailed: sending");
			initializeMessage(supplierShipmentFailedMessage, shipmentRequestMessage);
			remoteSupplierShipmentFailedSender.sendResponse(supplierShipmentFailedMessage);
		
		} else if (expectedCallback.equals("ShipmentScheduled")) {
			log.info("#### [test]: ShipmentScheduled: sending");
			initializeMessage(supplierShipmentScheduledMessage, shipmentRequestMessage);
			remoteSupplierShipmentScheduledSender.sendResponse(supplierShipmentScheduledMessage);
		}
	}

	protected MessageListener create_local_Seller_PurchaseAccepted_response_listener() {
		return new MessageListener() {
			public void onMessage(Message message) {
				try {
					String jmsMessageID = message.getJMSMessageID();
					log.info("#### [test]: PurchaseAccepted: received: "+jmsMessageID);
					Object object = MessageUtil.getPart(message, "purchaseAcceptedMessage");
					Assert.isTrue(object instanceof PurchaseAcceptedMessage, "Payload type not correct");
					PurchaseAcceptedMessage purchaseAcceptedMessage = (PurchaseAcceptedMessage) object;
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
	
	protected MessageListener create_local_Seller_PurchaseRejected_response_listener() {
		return new MessageListener() {
			public void onMessage(Message message) {
				try {
					String jmsMessageID = message.getJMSMessageID();
					log.info("#### [test]: PurchaseRejected: received: "+jmsMessageID);
					Object object = MessageUtil.getPart(message, "purchaseRejectedMessage");
					Assert.isTrue(object instanceof PurchaseRejectedMessage, "Payload type not correct");
					PurchaseRejectedMessage purchaseRejectedMessage = (PurchaseRejectedMessage) object;
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
	
	protected void validateMessage(ShipmentRequestMessage shipmentRequestMessage) throws Exception {
		Assert.notNull(shipmentRequestMessage, "Message must be specified");
		Assert.notNull(shipmentRequestMessage.getShipment(), "Shipment not found");
		Assert.notNull(shipmentRequestMessage.getPayment(), "Payment not found");
		if (this.supplierShipmentRequestMessage != null) {
			Bookshop2Fixture.assertSameShipment(this.supplierShipmentRequestMessage.getShipment(), shipmentRequestMessage.getShipment(), "Shipment field is unequal");
			Bookshop2Fixture.assertSamePayment(this.supplierShipmentRequestMessage.getPayment(), shipmentRequestMessage.getPayment(), "Payment field is unequal");
		}
	}
	
	protected void validateMessage(PurchaseAcceptedMessage purchaseAcceptedMessage) throws Exception {
		Assert.notNull(purchaseAcceptedMessage, "Message must be specified");
		Assert.notNull(purchaseAcceptedMessage.getOrder(), "Order not found");
		Assert.notNull(purchaseAcceptedMessage.getPayment(), "Payment not found");
		Assert.notNull(purchaseAcceptedMessage.getInvoice(), "Invoice not found");
		if (this.purchaseAcceptedMessage != null) {
			Bookshop2Fixture.assertSameOrder(this.purchaseAcceptedMessage.getOrder(), purchaseAcceptedMessage.getOrder(), "Order field is unequal");
			Bookshop2Fixture.assertSamePayment(this.purchaseAcceptedMessage.getPayment(), purchaseAcceptedMessage.getPayment(), "Payment field is unequal");
			Bookshop2Fixture.assertSameInvoice(this.purchaseAcceptedMessage.getInvoice(), purchaseAcceptedMessage.getInvoice(), "Invoice field is unequal");
		}
	}
	
	protected void validateMessage(PurchaseRejectedMessage purchaseRejectedMessage) throws Exception {
		Assert.notNull(purchaseRejectedMessage, "Message must be specified");
		Assert.notNull(purchaseRejectedMessage.getOrder(), "Order not found");
		Assert.notNull(purchaseRejectedMessage.getPayment(), "Payment not found");
		if (this.purchaseRejectedMessage != null) {
			Assert.equals(this.purchaseRejectedMessage.getReason(), purchaseRejectedMessage.getReason(), "Reason field is unequal");
			Bookshop2Fixture.assertSameOrder(this.purchaseRejectedMessage.getOrder(), purchaseRejectedMessage.getOrder(), "Order field is unequal");
			Bookshop2Fixture.assertSamePayment(this.purchaseRejectedMessage.getPayment(), purchaseRejectedMessage.getPayment(), "Payment field is unequal");
		}
	}

	@Test
	//@Ignore
	@InSequence(value = 1)
	@BytemanRule(name = "rule1", 
		targetClass = "SellerFactory", 
		targetMethod = "createShipment", 
		targetLocation = "AT ENTRY", 
		//condition = "true",
		helper = "org.aries.byteman.BytemanHelper",
		action = "return createShipment()")
	public void runTest_PurchaseBooks_PurchaseAccepted_from_ShipBooks_ShipmentComplete() throws Exception {
		String testName = "runTest_PurchaseBooks_PurchaseAccepted_from_ShipBooks_ShipmentComplete";
		log.info(testName+": started");
		
		registerNotificationListeners();
		
		// prepare test data
		bookCacheHelper.assureRemoveAll();
		bookCacheHelper.addToAvailableBooks(2);
		bookCacheHelper.addToBookOrders(6);
		Bookshop2Fixture.reset();
				
		// setup expectations
		expectedEvent = "Seller_PurchaseBooks_Request_Done";
		expectedMessage = "bookshop2.seller.PurchaseAccepted";

		// setup expected callback response
		expectedCallback = "ShipmentComplete";
		supplierShipmentCompleteMessage = createShipmentCompleteMessage();
		
		// setup mock response "supplier.shipBooks"
		purchaseAcceptedMessage = Bookshop2Fixture.create_PurchaseAcceptedMessage();
		
		// execute action
		Bookshop2Fixture.reset();
		runAction_send_PurchaseBooks();
		
		// validate request initiation state
		Assert.isTrue(isFiredRequestReceived("Seller_PurchaseBooks"));
		
		// validate remote ShipBooks interaction
		Assert.isTrue(isFiredRequestSent("Supplier_ShipBooks"));
		Assert.isTrue(isFiredResponseDone("Supplier_ShipmentComplete"));
		Assert.isTrue(shipmentRequestReceived);
		
		// validate successful callback state
		Assert.isTrue(isFiredResponseSent("Seller_PurchaseAccepted"));
		Assert.isTrue(purchaseAcceptedReceived);

		// validate non-existent callback state
		Assert.isFalse(isFiredResponseSent("Seller_PurchaseRejected"));
		Assert.isFalse(purchaseRejectedReceived);
		
		// validate request completion state
		Assert.isTrue(isFiredRequestHandled("Seller_PurchaseBooks"));
		Assert.isTrue(isFiredRequestDone("Seller_PurchaseBooks"));
		
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 2)
	public void runTest_PurchaseBooks_PurchaseAccepted_from_ShipBooks_ShipmentComplete_cancel() throws Exception {
		String testName = "runTest_PurchaseBooks_PurchaseAccepted_from_ShipBooks_ShipmentComplete_cancel";
		log.info(testName+": started");
		
		registerNotificationListeners();
		
		// prepare test data
		bookCacheHelper.assureRemoveAll();
		bookCacheHelper.addToAvailableBooks(2);
		bookCacheHelper.addToBookOrders(6);
		Bookshop2Fixture.reset();
		
		// setup expectations
		expectedEvent = "Seller_PurchaseBooks_Request_Done";
		expectedMessage = "bookshop2.seller.PurchaseAccepted";
		
		// setup mock response
		expectedCallback = "ShipmentComplete";
		supplierShipmentCompleteMessage = createShipmentCompleteMessage();
		purchaseAcceptedMessage = Bookshop2Fixture.create_PurchaseAcceptedMessage();
		
		// execute action
		Bookshop2Fixture.reset();
		runAction_send_PurchaseBooks();
		
		// clear message queues
		removeMessagesFromDestinations();
		
		// execute "cancel" of action
		Bookshop2Fixture.reset();
		runAction_send_PurchaseBooks_cancel();
		
		// validate request initiation state
		Assert.isTrue(isFiredRequestReceived("Seller_PurchaseBooks"));
		
		// validate remote ShipBooks interaction
		Assert.isTrue(isFiredRequestSent("Supplier_ShipBooks"));
		Assert.isTrue(isFiredResponseDone("Supplier_ShipmentComplete"));
		Assert.isTrue(shipmentRequestReceived);
		
		// validate successful callback state
		Assert.isTrue(isFiredResponseSent("Seller_PurchaseAccepted"));
		Assert.isTrue(purchaseAcceptedReceived);
		
		// validate non-existent callback state
		Assert.isFalse(isFiredResponseSent("Seller_PurchaseRejected"));
		Assert.isFalse(purchaseRejectedReceived);
		
		// validate request cancellation state
		Assert.isTrue(isFiredRequestCancelled("Seller_PurchaseBooks"));
		Assert.isTrue(isFiredRequestRolledBack("Seller_PurchaseBooks"));
		
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 3)
	public void runTest_PurchaseBooks_PurchaseAccepted_from_ShipBooks_ShipmentComplete_undo() throws Exception {
		String testName = "runTest_PurchaseBooks_PurchaseAccepted_from_ShipBooks_ShipmentComplete_undo";
		log.info(testName+": started");
		
		registerNotificationListeners();
		
		// prepare test data
		bookCacheHelper.assureRemoveAll();
		bookCacheHelper.addToAvailableBooks(2);
		bookCacheHelper.addToBookOrders(6);
		Bookshop2Fixture.reset();
		
		// setup expectations
		expectedEvent = "Seller_PurchaseBooks_Request_Done";
		expectedMessage = "bookshop2.seller.PurchaseAccepted";
		
		// setup mock response
		expectedCallback = "ShipmentComplete";
		supplierShipmentCompleteMessage = createShipmentCompleteMessage();
		purchaseAcceptedMessage = Bookshop2Fixture.create_PurchaseAcceptedMessage();
		
		// execute action
		Bookshop2Fixture.reset();
		runAction_send_PurchaseBooks();
		
		removeMessagesFromDestinations();
		
		// execute "undo" of action
		runAction_send_PurchaseBooks_undo();
		
		// validate request initiation state
		Assert.isTrue(isFiredRequestReceived("Seller_PurchaseBooks"));
		
		// validate remote ShipBooks interaction
		Assert.isTrue(isFiredRequestSent("Supplier_ShipBooks"));
		Assert.isTrue(isFiredResponseDone("Supplier_ShipmentComplete"));
		Assert.isTrue(shipmentRequestReceived);
		
		// validate successful callback state
		Assert.isTrue(isFiredResponseSent("Seller_PurchaseAccepted"));
		Assert.isTrue(purchaseAcceptedReceived);
		
		// validate non-existent callback state
		Assert.isFalse(isFiredResponseSent("Seller_PurchaseRejected"));
		Assert.isFalse(purchaseRejectedReceived);
		
		// validate request rolledback state
		Assert.isTrue(isFiredRequestRolledBack("Seller_PurchaseBooks"));
		
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 4)
	@BytemanRule(name = "rule4", 
		targetClass = "BookCacheManager", 
		targetMethod = "getMatchingAvailableBooks", 
		targetLocation = "AT ENTRY", 
		helper = "org.aries.byteman.BytemanHelper",
		action = "return createEmptyBook_Set()")
	public void runTest_PurchaseBooks_from_PurchaseRejected_AvailableBooks_Size_EQ__0() throws Exception {
		String testName = "runTest_PurchaseBooks_from_PurchaseRejected_AvailableBooks_Size_EQ__0";
		log.info(testName+": started");
		
		registerNotificationListeners();

		// prepare test data
		bookCacheHelper.assureRemoveAll();
		
		// setup expectations
		expectedEvent = "Seller_PurchaseBooks_Request_Done";
		expectedMessage = "bookshop2.seller.PurchaseRejected";
		
		// setup response "PurchaseRejected"
		purchaseRejectedMessage = Bookshop2Fixture.create_PurchaseRejectedMessage();
		purchaseRejectedMessage.setReason("No books available");
		
		// execute action
		Bookshop2Fixture.reset();
		runAction_send_PurchaseBooks();
		
		// validate request initiation state
		Assert.isTrue(isFiredRequestReceived("Seller_PurchaseBooks"));
		
		// validate remote ShipBooks interaction
		Assert.isFalse(isFiredRequestSent("Supplier_ShipBooks"));
		Assert.isFalse(shipmentRequestReceived);
		
		// validate successful callback state
		Assert.isTrue(isFiredResponseSent("Seller_PurchaseRejected"));
		Assert.isTrue(purchaseRejectedReceived);
		
		// validate non-existent callback state
		Assert.isFalse(isFiredResponseSent("Seller_PurchaseAccepted"));
		Assert.isFalse(purchaseAcceptedReceived);
		
		// validate request completion state
		Assert.isTrue(isFiredRequestHandled("Seller_PurchaseBooks"));
		Assert.isTrue(isFiredRequestDone("Seller_PurchaseBooks"));
		
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 5)
	public void runTest_PurchaseBooks_PurchaseRejected_from_ShipBooks_ShipmentFailed() throws Exception {
		String testName = "runTest_PurchaseBooks_PurchaseRejected_from_ShipBooks_ShipmentFailed";
		log.info(testName+": started");
		
		registerNotificationListeners();
		
		// prepare test data
		bookCacheHelper.assureRemoveAll();
		bookCacheHelper.addToAvailableBooks(2);
		bookCacheHelper.addToBookOrders(6);
		Bookshop2Fixture.reset();
		
		// setup expectations
		expectedEvent = "Seller_PurchaseBooks_Request_Done";
		expectedMessage = "bookshop2.seller.PurchaseRejected";
		
		// setup expected callback response
		expectedCallback = "ShipmentFailed";
		supplierShipmentFailedMessage = createShipmentFailedMessage();
		supplierShipmentFailedMessage.setReason("Shipment Failed");
		
		// setup mock response "supplier.shipBooks"
		purchaseRejectedMessage = Bookshop2Fixture.create_PurchaseRejectedMessage();
		purchaseRejectedMessage.setReason("Shipment Failed");
		
		// execute action
		Bookshop2Fixture.reset();
		runAction_send_PurchaseBooks();
		
		// validate request initiation state
		Assert.isTrue(isFiredRequestReceived("Seller_PurchaseBooks"));
		
		// validate remote ShipBooks interaction
		Assert.isTrue(isFiredRequestSent("Supplier_ShipBooks"));
		Assert.isTrue(isFiredResponseDone("Supplier_ShipmentFailed"));
		Assert.isTrue(shipmentRequestReceived);

		// validate successful callback state
		Assert.isTrue(isFiredResponseSent("Seller_PurchaseRejected"));
		Assert.isTrue(purchaseRejectedReceived);

		// validate non-existent callback state
		Assert.isFalse(isFiredResponseSent("Seller_PurchaseAccepted"));
		Assert.isFalse(purchaseAcceptedReceived);

		// validate request completion state
		Assert.isTrue(isFiredRequestHandled("Seller_PurchaseBooks"));
		Assert.isTrue(isFiredRequestDone("Seller_PurchaseBooks"));
		
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 6)
	public void runTest_PurchaseBooks_PurchaseRejected_from_ShipBooks_ShipmentFailed_cancel() throws Exception {
		String testName = "runTest_PurchaseBooks_PurchaseRejected_from_ShipBooks_ShipmentFailed_cancel";
		log.info(testName+": started");
		
		registerNotificationListeners();
		
		// prepare test data
		bookCacheHelper.assureRemoveAll();
		bookCacheHelper.addToAvailableBooks(2);
		bookCacheHelper.addToBookOrders(6);
		Bookshop2Fixture.reset();
		
		// setup expectations
		expectedEvent = "Seller_PurchaseBooks_Request_Done";
		expectedMessage = "bookshop2.seller.PurchaseRejected";
		
		// setup mock response
		expectedCallback = "ShipmentFailed";
		supplierShipmentFailedMessage = createShipmentFailedMessage();
		supplierShipmentFailedMessage.setReason("Shipment Failed");
		purchaseRejectedMessage = Bookshop2Fixture.create_PurchaseRejectedMessage();
		purchaseRejectedMessage.setReason("Shipment Failed");

		// execute action
		Bookshop2Fixture.reset();
		runAction_send_PurchaseBooks();
		
		// clear message queues
		removeMessagesFromDestinations();
		
		// execute "cancel" of action
		Bookshop2Fixture.reset();
		runAction_send_PurchaseBooks_cancel();
		
		// validate request initiation state
		Assert.isTrue(isFiredRequestReceived("Seller_PurchaseBooks"));
		
		// validate remote ShipBooks interaction
		Assert.isTrue(isFiredRequestSent("Supplier_ShipBooks"));
		Assert.isTrue(isFiredResponseDone("Supplier_ShipmentFailed"));
		Assert.isTrue(shipmentRequestReceived);
		
		// validate successful callback state
		Assert.isTrue(isFiredResponseSent("Seller_PurchaseRejected"));
		Assert.isTrue(purchaseRejectedReceived);
		
		// validate non-existent callback state
		Assert.isFalse(isFiredResponseSent("Seller_PurchaseAccepted"));
		Assert.isFalse(purchaseAcceptedReceived);
		
		// validate request cancellation state
		Assert.isTrue(isFiredRequestCancelled("Seller_PurchaseBooks"));
		Assert.isTrue(isFiredRequestRolledBack("Seller_PurchaseBooks"));
		
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 7)
	public void runTest_PurchaseBooks_PurchaseRejected_from_ShipBooks_ShipmentFailed_undo() throws Exception {
		String testName = "runTest_PurchaseBooks_PurchaseRejected_from_ShipBooks_ShipmentFailed_undo";
		log.info(testName+": started");
		
		registerNotificationListeners();
		
		// prepare test data
		bookCacheHelper.assureRemoveAll();
		bookCacheHelper.addToAvailableBooks(2);
		bookCacheHelper.addToBookOrders(6);
		Bookshop2Fixture.reset();
		
		// setup expectations
		expectedEvent = "Seller_PurchaseBooks_Request_Done";
		expectedMessage = "bookshop2.seller.PurchaseRejected";
		
		// setup mock response
		expectedCallback = "ShipmentFailed";
		supplierShipmentFailedMessage = createShipmentFailedMessage();
		supplierShipmentFailedMessage.setReason("Shipment Failed");
		purchaseRejectedMessage = Bookshop2Fixture.create_PurchaseRejectedMessage();
		purchaseRejectedMessage.setReason("Shipment Failed");
		
		// execute action
		Bookshop2Fixture.reset();
		runAction_send_PurchaseBooks();
		
		removeMessagesFromDestinations();
		
		// execute "undo" of action
		runAction_send_PurchaseBooks_undo();
		
		// validate request initiation state
		Assert.isTrue(isFiredRequestReceived("Seller_PurchaseBooks"));
		
		// validate remote ShipBooks interaction
		Assert.isTrue(isFiredRequestSent("Supplier_ShipBooks"));
		Assert.isTrue(isFiredResponseDone("Supplier_ShipmentFailed"));
		Assert.isTrue(shipmentRequestReceived);
		
		// validate successful callback state
		Assert.isTrue(isFiredResponseSent("Seller_PurchaseRejected"));
		Assert.isTrue(purchaseRejectedReceived);
		
		// validate non-existent callback state
		Assert.isFalse(isFiredResponseSent("Seller_PurchaseAccepted"));
		Assert.isFalse(purchaseAcceptedReceived);
		
		// validate request rolledback state
		Assert.isTrue(isFiredRequestRolledBack("Seller_PurchaseBooks"));
		
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 8)
	@BytemanRule(name = "rule8", 
			targetClass = "SellerProcess", 
			targetMethod = "fire_ShipBooks_request_sent", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"exception message\")")
	public void runTest_PurchaseBooks_PurchaseRejected_from_ShipBooks_exception() throws Exception {
		String testName = "runTest_PurchaseBooks_PurchaseRejected_from_ShipBooks_exception";
		log.info(testName+": started");
		
		setupByteman(testName);
		registerNotificationListeners();
		
		// prepare test data
		bookCacheHelper.assureRemoveAll();
		bookCacheHelper.addToAvailableBooks(2);
		bookCacheHelper.addToBookOrders(6);
		Bookshop2Fixture.reset();
		
		// setup expectations
		expectedEvent = "Supplier_ShipBooks_Outgoing_Request_Aborted";
		expectedMessage = "bookshop2.seller.PurchaseRejected";
		exceptionMessage = "exception message";
		
		// setup mock response
		purchaseRejectedMessage = Bookshop2Fixture.create_PurchaseRejectedMessage();
		purchaseRejectedMessage.setReason("exception message");
		
		try {
		// execute action
			Bookshop2Fixture.reset();
			runAction_send_PurchaseBooks();
		
			// validate request initiation state
			Assert.isTrue(isFiredRequestReceived("Seller_PurchaseBooks"));
			
			// validate remote ShipBooks interaction
			Assert.isTrue(isFiredRequestSent("Supplier_ShipBooks"));
			Assert.isTrue(shipmentRequestReceived);
			
			// validate successful callback state
			Assert.isTrue(isFiredResponseSent("Seller_PurchaseRejected"));
			Assert.isTrue(purchaseRejectedReceived);
			
			// validate non-existent callback state
			Assert.isFalse(isFiredResponseSent("Seller_PurchaseAccepted"));
			Assert.isFalse(purchaseAcceptedReceived);
			
			// validate outgoing request aborted state
			Assert.isTrue(isFiredOutgoingRequestAborted("Supplier_ShipBooks"));
			
			// validate request completion state
			Assert.isTrue(isFiredRequestHandled("Seller_PurchaseBooks"));
			Assert.isTrue(isFiredRequestDone("Seller_PurchaseBooks"));
			
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
			targetClass = "PurchaseBooksHandlerImpl", 
			targetMethod = "purchaseBooks", 
			targetLocation = "AT ENTRY", 
			action = "$0.timeout = 0")
	public void runTest_PurchaseBooks_timeout() throws Exception {
		String testName = "runTest_PurchaseBooks_timeout";
		log.info(testName+": started");
		
		setupByteman(testName);
		registerNotificationListeners();
		
		// setup expectations
		expectedEvent = "Seller_PurchaseBooks_Incoming_Request_Aborted";
		//TODO expectedMessage = "bookshop2.seller.PurchaseRequest";
		
		try {
		// execute action
			Bookshop2Fixture.reset();
			runAction_send_PurchaseBooks();
		
			// validate request initiation state
			Assert.isTrue(isFiredRequestReceived("Seller_PurchaseBooks"));
			
			// validate request non-completion state
			Assert.isFalse(isFiredRequestHandled("Seller_PurchaseBooks"));
			Assert.isFalse(isFiredRequestDone("Seller_PurchaseBooks"));
			
			// validate incoming request aborted state
			Assert.isTrue(isFiredIncomingRequestAborted("Seller_PurchaseBooks"));
			
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
			targetMethod = "purchaseBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"exception message\")")
	public void runTest_PurchaseBooks_exception() throws Exception {
		String testName = "runTest_PurchaseBooks_exception";
		log.info(testName+": started");
		
		setupByteman(testName);
		registerNotificationListeners();
		
		// setup expectations
		expectedEvent = "Seller_PurchaseBooks_Incoming_Request_Aborted";
		expectedMessage = "bookshop2.seller.PurchaseRequest";
		exceptionMessage = "exception message";
		
		try {
		// execute action
			Bookshop2Fixture.reset();
			runAction_send_PurchaseBooks();
		
			// validate request initiation state
			Assert.isTrue(isFiredRequestReceived("Seller_PurchaseBooks"));
		
			// validate request non-completion state
			Assert.isFalse(isFiredRequestHandled("Seller_PurchaseBooks"));
			Assert.isFalse(isFiredRequestDone("Seller_PurchaseBooks"));
			
			// validate incoming request aborted state
			Assert.isTrue(isFiredIncomingRequestAborted("Seller_PurchaseBooks"));
			
			if (errorMessage != null)
				fail(errorMessage);
			
		} finally {
			tearDownByteman(testName);
		}
	}
	
}
