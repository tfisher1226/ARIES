package bookshop2.supplier.incoming.shipBooks;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.aries.Assert;
import org.aries.jms.client.JmsClient;
import org.aries.jms.util.MessageUtil;
import org.aries.tx.AbstractArquillianTest;
import org.aries.tx.AbstractJMSListenerArquillionTest;
import org.aries.tx.BytemanRule;
import org.aries.tx.DataModuleTestControl;
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

import bookshop2.ShipmentCompleteMessage;
import bookshop2.ShipmentFailedMessage;
import bookshop2.ShipmentRequestMessage;
import bookshop2.ShipmentScheduledMessage;
import bookshop2.supplier.SupplierTestEARBuilder;
import bookshop2.supplier.client.shipBooks.ShipBooks;
import bookshop2.supplier.client.shipBooks.ShipBooksProxyForJMS;
import bookshop2.supplier.data.bookOrderLog.BookOrderLogHelper;
import bookshop2.supplier.data.bookOrderLog.BookOrderLogManager;
import bookshop2.supplier.outgoing.shipmentComplete.ShipmentCompleteReply;
import bookshop2.supplier.outgoing.shipmentFailed.ShipmentFailedReply;
import bookshop2.supplier.outgoing.shipmentScheduled.ShipmentScheduledReply;
import bookshop2.util.Bookshop2Fixture;


@RunAsClient
@RunWith(Arquillian.class)
public class ShipBooksListenerForJMSCIT extends AbstractJMSListenerArquillionTest {
	
	private JmsClient shipBooksClient;
	
	private JmsClient remoteShipperShipBooksHandler;
	
	private JmsClient remoteShipperShipmentCompleteSender;
	
	private JmsClient remoteShipperShipmentFailedSender;
	
	private JmsClient remoteShipperShipmentScheduledSender;
	
	private JmsClient localSupplierShipmentCompleteHandler;

	private JmsClient localSupplierShipmentFailedHandler;
	
	private JmsClient localSupplierShipmentScheduledHandler;
	
	private ShipmentRequestMessage shipmentRequestMessage;

	private ShipmentCompleteMessage shipmentCompleteMessage;

	private ShipmentFailedMessage shipmentFailedMessage;
	
	private ShipmentScheduledMessage shipmentScheduledMessage;
	
	private ShipmentRequestMessage shipperShipmentRequestMessage;
	
	private ShipmentCompleteMessage shipperShipmentCompleteMessage;
	
	private ShipmentFailedMessage shipperShipmentFailedMessage;
	
	private ShipmentScheduledMessage shipperShipmentScheduledMessage;
	
	private TransactionTestControl transactionTestControl;
	
	private BookOrderLogHelper bookOrderLogHelper;
	
	private boolean shipmentRequestReceived;
	
	private boolean shipmentCompleteReceived;

	private boolean shipmentFailedReceived;

	private boolean shipmentScheduledReceived;

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
		return ShipBooksListenerForJMSCIT.class;
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
		return "bookshop2.supplier.shipBooks";
	}
	
	@Override
	public String getTargetArchive() {
		return SupplierTestEARBuilder.NAME;
	}
	
	@Override
	public String getTargetDestination() {
		return get_target_Supplier_ShipBooks_destination();
	}
	
	public String get_target_Supplier_ShipBooks_destination() {
		return getJNDINameForQueue("inventory_bookshop2_supplier_ship_books_queue");
	}
	
	public String get_remote_Shipper_ShipBooks_destination() {
		return getJNDINameForQueue("shipment_bookshop2_shipper_ship_books_queue");
	}

	public String get_remote_Supplier_ShipmentComplete_destination() {
		return getJNDINameForQueue("inventory_bookshop2_supplier_shipment_complete_queue");
	}
	
	public String get_remote_Supplier_ShipmentFailed_destination() {
		return getJNDINameForQueue("inventory_bookshop2_supplier_shipment_failed_queue");
	}
	
	public String get_remote_Supplier_ShipmentScheduled_destination() {
		return getJNDINameForQueue("inventory_bookshop2_supplier_shipment_scheduled_queue");
	}

	public String get_local_Supplier_ShipmentComplete_destination() {
		return "test_message_domain_test_destination_queue_a";
	}

	public String get_local_Supplier_ShipmentFailed_destination() {
		return "test_message_domain_test_destination_queue_b";
	}
	
	public String get_local_Supplier_ShipmentScheduled_destination() {
		return "test_message_domain_test_destination_queue_c";
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		startServer();
		createTransactionControl();
		createBookOrderLogHelper();
		clearBookOrderLogContext();
		//removeMessagesFromDestinations();
		remoteShipperShipmentCompleteSender = createClient(get_remote_Supplier_ShipmentComplete_destination());
		remoteShipperShipmentFailedSender = createClient(get_remote_Supplier_ShipmentFailed_destination());
		remoteShipperShipmentScheduledSender = createClient(get_remote_Supplier_ShipmentScheduled_destination());
		Bookshop2Fixture.reset();
	}
	
	protected void createTransactionControl() throws Exception {
		transactionTestControl = new TransactionTestControl();
		transactionTestControl.setupTransactionManager();
	}
	
	public void createBookOrderLogHelper() throws Exception {
		bookOrderLogHelper = new BookOrderLogHelper();
		bookOrderLogHelper.setJmxProxy(jmxProxy);
		bookOrderLogHelper.setJmxManager(jmxManager);
		bookOrderLogHelper.initializeAsClient(createBookOrderLogControl());
	}
	
	protected DataModuleTestControl createBookOrderLogControl() throws Exception {
		DataModuleTestControl bookOrderLogControl = new DataModuleTestControl(transactionTestControl);
		bookOrderLogControl.setDatabaseName("bookshop2_supplier_db");
		bookOrderLogControl.setDataSourceName("bookshop2_supplier_ds");
		bookOrderLogControl.setPersistenceUnitName("bookOrderLog");
		bookOrderLogControl.setupDataLayer();
		return bookOrderLogControl;
	}
	
	protected void clearBookOrderLogContext() throws Exception {
		bookOrderLogHelper.clearContext(BookOrderLogManager.MBEAN_NAME);
	}
	
	public ShipmentRequestMessage createShipmentRequestMessage() {
		return createShipmentRequestMessage(false, false);
	}
	
	public ShipmentRequestMessage createShipmentRequestCancelMessage() {
		return createShipmentRequestMessage(true, false);
	}
	
	public ShipmentRequestMessage createShipmentRequestUndoMessage() {
		return createShipmentRequestMessage(false, true);
	}
	
	public ShipmentRequestMessage createShipmentRequestMessage(boolean cancel, boolean undo) {
		ShipmentRequestMessage message = Bookshop2Fixture.create_ShipmentRequestMessage(cancel, undo);
		message.addToReplyToDestinations("ShipmentComplete", get_local_Supplier_ShipmentComplete_destination());
		message.addToReplyToDestinations("ShipmentFailed", get_local_Supplier_ShipmentFailed_destination());
		message.addToReplyToDestinations("ShipmentScheduled", get_local_Supplier_ShipmentScheduled_destination());
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
		addRequestNotificationListeners("Supplier_ShipBooks");
		addResponseNotificationListeners("Supplier_ShipmentComplete");
		addResponseNotificationListeners("Supplier_ShipmentFailed");
		addResponseNotificationListeners("Supplier_ShipmentScheduled");
		addRequestNotificationListeners("Shipper_ShipBooks");
		addResponseNotificationListeners("Shipper_ShipmentComplete");
		addResponseNotificationListeners("Shipper_ShipmentFailed");
		addResponseNotificationListeners("Shipper_ShipmentScheduled");
		super.registerNotificationListeners();
	}
	
	@After
	public void tearDown() throws Exception {
		clearStructures();
		clearState();
		super.tearDown();
	}
	
	protected void clearStructures() throws Exception {
		shipBooksClient.reset();
		shipBooksClient = null;
		
		//remote handler(s) for requests to remote (mocked) service(s)
		remoteShipperShipBooksHandler.reset();
		remoteShipperShipBooksHandler = null;
		
		//remote clients for sending responses back from remote (mocked) service(s)
		remoteShipperShipmentCompleteSender.reset();
		remoteShipperShipmentCompleteSender = null;
		remoteShipperShipmentFailedSender.reset();
		remoteShipperShipmentFailedSender = null;
		remoteShipperShipmentScheduledSender.reset();
		remoteShipperShipmentScheduledSender = null;

		//local handlers for responses from target service
		localSupplierShipmentCompleteHandler.reset();
		localSupplierShipmentCompleteHandler = null;
		localSupplierShipmentFailedHandler.reset();
		localSupplierShipmentFailedHandler = null;
		localSupplierShipmentScheduledHandler.reset();
		localSupplierShipmentScheduledHandler = null;
		super.clearStructures();
	}
	
	protected void clearState() throws Exception {
		shipmentRequestMessage = null;
		shipmentCompleteMessage = null;
		shipmentFailedMessage = null;
		shipmentScheduledMessage = null;
		shipmentRequestReceived = false;
		shipmentCompleteReceived = false;
		shipmentFailedReceived = false;
		shipmentScheduledReceived = false;
		super.clearState();
	}
	
	protected void removeMessagesFromDestinations() throws Exception {
		removeMessagesFromQueue(getTargetArchive(), get_target_Supplier_ShipBooks_destination());
		removeMessagesFromQueue(getTargetArchive(), get_local_Supplier_ShipmentComplete_destination());
		removeMessagesFromQueue(getTargetArchive(), get_local_Supplier_ShipmentFailed_destination());
		removeMessagesFromQueue(getTargetArchive(), get_local_Supplier_ShipmentScheduled_destination());
		removeMessagesFromQueue(getTargetArchive(), get_remote_Shipper_ShipBooks_destination());
		removeMessagesFromQueue(getTargetArchive(), get_remote_Supplier_ShipmentComplete_destination());
		removeMessagesFromQueue(getTargetArchive(), get_remote_Supplier_ShipmentFailed_destination());
		removeMessagesFromQueue(getTargetArchive(), get_remote_Supplier_ShipmentScheduled_destination());
	}
	
	@TargetsContainer("hornetQ01_local")
	@Deployment(name = "supplierEAR", order = 2)
	public static EnterpriseArchive createSupplierEAR() {
		SupplierTestEARBuilder builder = new SupplierTestEARBuilder();
		builder.setRunningAsClient(true);
		return builder.createEAR();
	}
	
	public void runAction_send_ShipBooks() throws Exception {
		runAction_send_ShipBooks(createShipmentRequestMessage());
	}
	
	public void runAction_send_ShipBooks(ShipmentRequestMessage shipmentRequestMessage) throws Exception {
		this.shipmentRequestMessage = shipmentRequestMessage;
		
		// prepare the environment
		removeMessagesFromDestinations();
		
		// prepare context
		//beginTransaction();
		
		// prepare mocks
		registerForResult();
		
		// start handlers for calls to remote service(s)
		remoteShipperShipBooksHandler = start_remote_Shipper_ShipBooks_handler();
		
		// start local handlers for responses from target service
		localSupplierShipmentCompleteHandler = start_local_Supplier_ShipmentComplete_handler();
		localSupplierShipmentFailedHandler = start_local_Supplier_ShipmentFailed_handler();
		localSupplierShipmentScheduledHandler = start_local_Supplier_ShipmentScheduled_handler();
		
		// start fixture execution
		shipBooksClient = start_ShipBooks_client();
		shipBooksClient.send(shipmentRequestMessage, correlationId, null);
		
		// wait for completion result
		Object result = waitForCompletion();
		validateResult(result);
		
		// close context
		//commitTransaction();
		
		// validate state
		assertEmptyTargetDestination();
		
		removeMessagesFromDestinations();
	}

	protected void runAction_send_ShipBooks_cancel() throws Exception {
		expectedEvent = "Supplier_ShipBooks_Request_Cancel_Done";
		registerForResult(expectedEvent);
		
		ShipmentRequestMessage message = createShipmentRequestCancelMessage();
		shipBooksClient.send(message, correlationId, null);
		
		Object result = waitForCompletion();
		validateResult(result);
	}
	
	protected void runAction_send_ShipBooks_undo() throws Exception {
		expectedEvent = "Supplier_ShipBooks_Request_Undo_Done";
		registerForResult(expectedEvent);
		
		ShipmentRequestMessage message = createShipmentRequestUndoMessage();
		shipBooksClient.send(message, correlationId, null);
		
		Object result = waitForCompletion();
		validateResult(result);
	}
	
	public Thread start_runAction_send_ShipBooks() throws Exception {
		Thread thread = new Thread() {
			public void run() {
				try {
					runAction_send_ShipBooks();
				} catch (Exception e) {
					errorMessage = e.getMessage();
				}
			}
		};
		thread.start();
		Thread.sleep(4000);
		return thread;
	}
	
	protected JmsClient start_ShipBooks_client() throws Exception {
		JmsClient client = new ShipBooksProxyForJMS(ShipBooks.ID);
		configureClient(client, getTargetDestination());
		client.setCreateTemporaryQueue(true);
		client.initialize();
		return client;
	}
	
	protected JmsClient start_remote_Shipper_ShipBooks_handler() throws Exception {
		JmsClient client = createClient(get_remote_Shipper_ShipBooks_destination());
		client.setMessageListener(create_remote_Shipper_ShipBooks_message_listener());
		client.initialize();
		return client;
	}
	
	protected JmsClient start_local_Supplier_ShipmentComplete_handler() throws Exception {
		JmsClient client = createClient(getJNDINameForQueue(get_local_Supplier_ShipmentComplete_destination()));
		client.setMessageListener(create_local_Supplier_ShipmentComplete_response_listener());
		client.initialize();
		return client;
	}
	
	protected JmsClient start_local_Supplier_ShipmentFailed_handler() throws Exception {
		JmsClient client = createClient(getJNDINameForQueue(get_local_Supplier_ShipmentFailed_destination()));
		client.setMessageListener(create_local_Supplier_ShipmentFailed_response_listener());
		client.initialize();
		return client;
	}
	
	protected JmsClient start_local_Supplier_ShipmentScheduled_handler() throws Exception {
		JmsClient client = createClient(getJNDINameForQueue(get_local_Supplier_ShipmentScheduled_destination()));
		client.setMessageListener(create_local_Supplier_ShipmentScheduled_response_listener());
		client.initialize();
		return client;
	}
	
	protected MessageListener create_remote_Shipper_ShipBooks_message_listener() {
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
						validateMessage(shipmentRequestMessage);
						processMessage(shipmentRequestMessage);
						break;
					}
				} catch (Throwable e) {
					errorMessage = ExceptionUtils.getRootCauseMessage(e);
					e.printStackTrace();
				} finally {
					if (expectedMessage != null && expectedMessage.equalsIgnoreCase("bookshop2.shipper.ShipBooks"))
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
			initializeMessage(shipmentCompleteMessage, shipmentRequestMessage);
			remoteShipperShipmentCompleteSender.sendResponse(shipmentCompleteMessage);
		
		} else if (expectedCallback.equals("ShipmentFailed")) {
			log.info("#### [test]: ShipmentFailed: sending");
			initializeMessage(shipmentFailedMessage, shipmentRequestMessage);
			remoteShipperShipmentFailedSender.sendResponse(shipmentFailedMessage);
		
		} else if (expectedCallback.equals("ShipmentScheduled")) {
			log.info("#### [test]: ShipmentScheduled: sending");
			initializeMessage(shipmentScheduledMessage, shipmentRequestMessage);
			remoteShipperShipmentScheduledSender.sendResponse(shipmentScheduledMessage);
		}
	}

	protected MessageListener create_local_Supplier_ShipmentComplete_response_listener() {
		return new MessageListener() {
			public void onMessage(Message message) {
				try {
					String jmsMessageID = message.getJMSMessageID();
					log.info("#### [test]: ShipmentComplete: received: "+jmsMessageID);
					Object object = MessageUtil.getPart(message, "shipmentCompleteMessage");
					Assert.isTrue(object instanceof ShipmentCompleteMessage, "Payload type not correct");
					ShipmentCompleteMessage shipmentCompleteMessage = (ShipmentCompleteMessage) object;
					validateMessage(shipmentCompleteMessage);
				} catch (Throwable e) {
					errorMessage = ExceptionUtils.getRootCauseMessage(e);
					e.printStackTrace();
				} finally {
					if (expectedMessage != null && expectedMessage.equalsIgnoreCase("bookshop2.supplier.ShipmentComplete"))
						expectedMessageResult.set(true);
					shipmentCompleteReceived = true;
				}
			}
		};
	}
	
	protected MessageListener create_local_Supplier_ShipmentFailed_response_listener() {
		return new MessageListener() {
			public void onMessage(Message message) {
				try {
					String jmsMessageID = message.getJMSMessageID();
					log.info("#### [test]: ShipmentFailed: received: "+jmsMessageID);
					Object object = MessageUtil.getPart(message, "shipmentFailedMessage");
					Assert.isTrue(object instanceof ShipmentFailedMessage, "Payload type not correct");
					ShipmentFailedMessage shipmentFailedMessage = (ShipmentFailedMessage) object;
					validateMessage(shipmentFailedMessage);
				} catch (Throwable e) {
					errorMessage = ExceptionUtils.getRootCauseMessage(e);
					e.printStackTrace();
				} finally {
					if (expectedMessage != null && expectedMessage.equalsIgnoreCase("bookshop2.supplier.ShipmentFailed"))
						expectedMessageResult.set(true);
					shipmentFailedReceived = true;
				}
			}
		};
	}
	
	protected MessageListener create_local_Supplier_ShipmentScheduled_response_listener() {
		return new MessageListener() {
			public void onMessage(Message message) {
				try {
					String jmsMessageID = message.getJMSMessageID();
					log.info("#### [test]: ShipmentScheduled: received: "+jmsMessageID);
					Object object = MessageUtil.getPart(message, "shipmentScheduledMessage");
					Assert.isTrue(object instanceof ShipmentScheduledMessage, "Payload type not correct");
					ShipmentScheduledMessage shipmentScheduledMessage = (ShipmentScheduledMessage) object;
					validateMessage(shipmentScheduledMessage);
				} catch (Throwable e) {
					errorMessage = ExceptionUtils.getRootCauseMessage(e);
					e.printStackTrace();
				} finally {
					if (expectedMessage != null && expectedMessage.equalsIgnoreCase("bookshop2.supplier.ShipmentScheduled"))
						expectedMessageResult.set(true);
					shipmentScheduledReceived = true;
				}
			}
		};
	}
	
	protected void validateResult(Object result) throws Exception {
		if (result instanceof ShipmentCompleteMessage) {
			validateMessage((ShipmentCompleteMessage) result);
		} else if (result instanceof ShipmentFailedMessage) {
			validateMessage((ShipmentFailedMessage) result);
		} else if (result instanceof ShipmentScheduledMessage) {
			validateMessage((ShipmentScheduledMessage) result);
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
		if (this.shipmentRequestMessage != null) {
			Bookshop2Fixture.assertSameShipment(this.shipmentRequestMessage.getShipment(), shipmentRequestMessage.getShipment(), "Shipment is unequal");
			Bookshop2Fixture.assertSamePayment(this.shipmentRequestMessage.getPayment(), shipmentRequestMessage.getPayment(), "Payment is unequal");
		}
	}
	
	protected void validateMessage(ShipmentCompleteMessage shipmentCompleteMessage) throws Exception {
		Assert.notNull(shipmentCompleteMessage, "Message must be specified");
		Assert.notNull(shipmentCompleteMessage.getShipment(), "Shipment not found");
		Assert.notNull(shipmentCompleteMessage.getInvoice(), "Invoice not found");
		if (this.shipmentCompleteMessage != null) {
			Bookshop2Fixture.assertSameShipment(this.shipmentCompleteMessage.getShipment(), shipmentCompleteMessage.getShipment(), "Shipment is unequal");
			Bookshop2Fixture.assertSameInvoice(this.shipmentCompleteMessage.getInvoice(), shipmentCompleteMessage.getInvoice(), "Invoice is unequal");
		}
	}
	
	protected void validateMessage(ShipmentFailedMessage shipmentFailedMessage) throws Exception {
		Assert.notNull(shipmentFailedMessage, "Message must be specified");
		Assert.notNull(shipmentFailedMessage.getShipment(), "Shipment not found");
		if (this.shipmentFailedMessage != null) {
			Assert.equals(this.shipmentFailedMessage.getReason(), shipmentFailedMessage.getReason(), "Reason is unequal");
			Bookshop2Fixture.assertSameShipment(this.shipmentFailedMessage.getShipment(), shipmentFailedMessage.getShipment(), "Shipment is unequal");
		}
	}
	
	protected void validateMessage(ShipmentScheduledMessage shipmentScheduledMessage) throws Exception {
		Assert.notNull(shipmentScheduledMessage, "Message must be specified");
		Assert.notNull(shipmentScheduledMessage.getShipment(), "Shipment not found");
		if (this.shipmentScheduledMessage != null) {
			Bookshop2Fixture.assertSameShipment(this.shipmentScheduledMessage.getShipment(), shipmentScheduledMessage.getShipment(), "Shipment is unequal");
		}
	}
	
	@Test
	//@Ignore
	@InSequence(value = 1)
	public void runTest_ShipBooks_ShipmentComplete_from_ShipBooks_ShipmentComplete() throws Exception {
		String testName = "runTest_ShipBooks_ShipmentComplete_from_ShipBooks_ShipmentComplete";
		log.info(testName+": started");
		
		registerNotificationListeners();
		
		// prepare test data
		bookOrderLogHelper.assureRemoveAll();
		
		// setup expectations
		expectedEvent = "Supplier_ShipBooks_Request_Done";
		expectedMessage = "bookshop2.supplier.ShipmentComplete";
		
		// setup expected callback response
		expectedCallback = "ShipmentComplete";
		shipmentCompleteMessage = createShipmentCompleteMessage();
		
		// setup mock response "shipper.shipBooks"
		shipperShipmentCompleteMessage = Bookshop2Fixture.create_ShipmentCompleteMessage();
		
		// execute action
		Bookshop2Fixture.reset();
		runAction_send_ShipBooks();
		
		// validate request initiation state
		Assert.isTrue(isFiredRequestReceived("Supplier_ShipBooks"));
		
		// validate remote ShipBooks interaction
		Assert.isTrue(isFiredRequestSent("Shipper_ShipBooks"));
		Assert.isTrue(isFiredResponseDone("Shipper_ShipmentComplete"));
		Assert.isTrue(shipmentRequestReceived);
		
		// validate successful callback state
		Assert.isTrue(isFiredResponseSent("Supplier_ShipmentComplete"));
		Assert.isTrue(shipmentCompleteReceived);

		// validate non-existent callback state
		Assert.isFalse(isFiredResponseSent("Supplier_ShipmentFailed"));
		Assert.isFalse(isFiredResponseSent("Supplier_ShipmentScheduled"));
		Assert.isFalse(shipmentFailedReceived);
		Assert.isFalse(shipmentScheduledReceived);

		// validate request completion state
		Assert.isTrue(isFiredRequestHandled("Supplier_ShipBooks"));
		Assert.isTrue(isFiredRequestDone("Supplier_ShipBooks"));
		
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 2)
	public void runTest_ShipBooks_ShipmentComplete_from_ShipBooks_ShipmentComplete_cancel() throws Exception {
		String testName = "runTest_ShipBooks_ShipmentComplete_from_ShipBooks_ShipmentComplete_cancel";
		log.info(testName+": started");
		
		registerNotificationListeners();
		
		// prepare test data
		bookOrderLogHelper.assureRemoveAll();
		
		// setup expectations
		expectedEvent = "Shipper_ShipmentComplete_Response_Done";
		expectedMessage = "bookshop2.supplier.ShipmentComplete";
		
		// setup mock response
		expectedCallback = "ShipmentComplete";
		shipmentCompleteMessage = createShipmentCompleteMessage();
		shipmentCompleteMessage = Bookshop2Fixture.create_ShipmentCompleteMessage();
		
		// execute action
		Bookshop2Fixture.reset();
		runAction_send_ShipBooks();
		
		// clear message queues
		removeMessagesFromDestinations();
		
		// execute "cancel" of action
		Bookshop2Fixture.reset();
		runAction_send_ShipBooks_cancel();
		
		// validate request initiation state
		Assert.isTrue(isFiredRequestReceived("Supplier_ShipBooks"));
		
		// validate remote ShipBooks interaction
		Assert.isTrue(isFiredRequestSent("Shipper_ShipBooks"));
		Assert.isTrue(isFiredResponseDone("Shipper_ShipmentComplete"));
		Assert.isTrue(shipmentRequestReceived);
		
		// validate successful callback state
		Assert.isTrue(isFiredResponseSent("Supplier_ShipmentComplete"));
		Assert.isTrue(shipmentCompleteReceived);
		
		// validate non-existent callback state
		Assert.isFalse(isFiredResponseSent("Supplier_ShipmentFailed"));
		Assert.isFalse(isFiredResponseSent("Supplier_ShipmentScheduled"));
		Assert.isFalse(shipmentFailedReceived);
		Assert.isFalse(shipmentScheduledReceived);
		
		// validate request cancellation state
		Assert.isTrue(isFiredRequestCancelled("Supplier_ShipBooks"));
		Assert.isTrue(isFiredRequestRolledBack("Supplier_ShipBooks"));
		
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 3)
	public void runTest_ShipBooks_ShipmentComplete_from_ShipBooks_ShipmentComplete_undo() throws Exception {
		String testName = "runTest_ShipBooks_ShipmentComplete_from_ShipBooks_ShipmentComplete_undo";
		log.info(testName+": started");
		
		registerNotificationListeners();
		
		// prepare test data
		bookOrderLogHelper.assureRemoveAll();
		
		// setup expectations
		expectedEvent = "Shipper_ShipmentComplete_Response_Done";
		expectedMessage = "bookshop2.supplier.ShipmentComplete";
		
		// setup mock response
		expectedCallback = "ShipmentComplete";
		shipmentCompleteMessage = createShipmentCompleteMessage();
		shipmentCompleteMessage = Bookshop2Fixture.create_ShipmentCompleteMessage();
		
		// execute action
		Bookshop2Fixture.reset();
		runAction_send_ShipBooks();
		
		removeMessagesFromDestinations();
		
		// execute "undo" of action
		runAction_send_ShipBooks_undo();
		
		// validate request initiation state
		Assert.isTrue(isFiredRequestReceived("Supplier_ShipBooks"));
		
		// validate remote ShipBooks interaction
		Assert.isTrue(isFiredRequestSent("Shipper_ShipBooks"));
		Assert.isTrue(isFiredResponseDone("Shipper_ShipmentComplete"));
		Assert.isTrue(shipmentRequestReceived);
		
		// validate successful callback state
		Assert.isTrue(isFiredResponseSent("Supplier_ShipmentComplete"));
		Assert.isTrue(shipmentCompleteReceived);
		
		// validate non-existent callback state
		Assert.isFalse(isFiredResponseSent("Supplier_ShipmentFailed"));
		Assert.isFalse(isFiredResponseSent("Supplier_ShipmentScheduled"));
		Assert.isFalse(shipmentFailedReceived);
		Assert.isFalse(shipmentScheduledReceived);
		
		// validate request rolledback state
		Assert.isTrue(isFiredRequestRolledBack("Supplier_ShipBooks"));
		
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 4)
	public void runTest_ShipBooks_ShipmentFailed_from_ShipBooks_ShipmentFailed() throws Exception {
		String testName = "runTest_ShipBooks_ShipmentFailed_from_ShipBooks_ShipmentFailed";
		log.info(testName+": started");
		
		registerNotificationListeners();
		
		// prepare test data
		bookOrderLogHelper.assureRemoveAll();
		
		// setup expectations
		expectedEvent = "Supplier_ShipBooks_Request_Done";
		expectedMessage = "bookshop2.supplier.ShipmentFailed";
		
		// setup expected callback response
		expectedCallback = "ShipmentFailed";
		shipmentFailedMessage = createShipmentFailedMessage();
		shipmentFailedMessage.setReason("Shipment Failed");
		
		// setup mock response "shipper.shipBooks"
		shipperShipmentFailedMessage = Bookshop2Fixture.create_ShipmentFailedMessage();
		shipperShipmentFailedMessage.setReason("Shipment Failed");
		
		// execute action
		Bookshop2Fixture.reset();
		runAction_send_ShipBooks();
		
		// validate request initiation state
		Assert.isTrue(isFiredRequestReceived("Supplier_ShipBooks"));
		
		// validate remote ShipBooks interaction
		Assert.isTrue(isFiredRequestSent("Shipper_ShipBooks"));
		Assert.isTrue(isFiredResponseDone("Shipper_ShipmentFailed"));
		Assert.isTrue(shipmentRequestReceived);
		
		// validate successful callback state
		Assert.isTrue(isFiredResponseSent("Supplier_ShipmentFailed"));
		Assert.isTrue(shipmentFailedReceived);
		
		// validate non-existent callback state
		Assert.isFalse(isFiredResponseSent("Supplier_ShipmentComplete"));
		Assert.isFalse(isFiredResponseSent("Supplier_ShipmentScheduled"));
		Assert.isFalse(shipmentCompleteReceived);
		Assert.isFalse(shipmentScheduledReceived);

		// validate request completion state
		Assert.isTrue(isFiredRequestHandled("Supplier_ShipBooks"));
		Assert.isTrue(isFiredRequestDone("Supplier_ShipBooks"));
		
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 5)
	public void runTest_ShipBooks_ShipmentFailed_from_ShipBooks_ShipmentFailed_cancel() throws Exception {
		String testName = "runTest_ShipBooks_ShipmentFailed_from_ShipBooks_ShipmentFailed_cancel";
		log.info(testName+": started");
		
		registerNotificationListeners();
		
		// prepare test data
		bookOrderLogHelper.assureRemoveAll();
		
		// setup expectations
		expectedEvent = "Shipper_ShipmentFailed_Response_Done";
		expectedMessage = "bookshop2.supplier.ShipmentFailed";
		
		// setup mock response
		expectedCallback = "ShipmentFailed";
		shipmentFailedMessage = createShipmentFailedMessage();
		shipmentFailedMessage.setReason("Shipment Failed");
		shipmentFailedMessage = Bookshop2Fixture.create_ShipmentFailedMessage();
		shipmentFailedMessage.setReason("Shipment Failed");
		
		// execute action
		Bookshop2Fixture.reset();
		runAction_send_ShipBooks();
		
		// clear message queues
		removeMessagesFromDestinations();
		
		// execute "cancel" of action
		Bookshop2Fixture.reset();
		runAction_send_ShipBooks_cancel();
		
		// validate request initiation state
		Assert.isTrue(isFiredRequestReceived("Supplier_ShipBooks"));
		
		// validate remote ShipBooks interaction
		Assert.isTrue(isFiredRequestSent("Shipper_ShipBooks"));
		Assert.isTrue(isFiredResponseDone("Shipper_ShipmentFailed"));
		Assert.isTrue(shipmentRequestReceived);
		
		// validate successful callback state
		Assert.isTrue(isFiredResponseSent("Supplier_ShipmentFailed"));
		Assert.isTrue(shipmentFailedReceived);
		
		// validate non-existent callback state
		Assert.isFalse(isFiredResponseSent("Supplier_ShipmentComplete"));
		Assert.isFalse(isFiredResponseSent("Supplier_ShipmentScheduled"));
		Assert.isFalse(shipmentCompleteReceived);
		Assert.isFalse(shipmentScheduledReceived);
		
		// validate request cancellation state
		Assert.isTrue(isFiredRequestCancelled("Supplier_ShipBooks"));
		Assert.isTrue(isFiredRequestRolledBack("Supplier_ShipBooks"));
		
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 6)
	public void runTest_ShipBooks_ShipmentFailed_from_ShipBooks_ShipmentFailed_undo() throws Exception {
		String testName = "runTest_ShipBooks_ShipmentFailed_from_ShipBooks_ShipmentFailed_undo";
		log.info(testName+": started");
		
		registerNotificationListeners();
		
		// prepare test data
		bookOrderLogHelper.assureRemoveAll();
		
		// setup expectations
		expectedEvent = "Shipper_ShipmentFailed_Response_Done";
		expectedMessage = "bookshop2.supplier.ShipmentFailed";
		
		// setup mock response
		expectedCallback = "ShipmentFailed";
		shipmentFailedMessage = createShipmentFailedMessage();
		shipmentFailedMessage.setReason("Shipment Failed");
		shipmentFailedMessage = Bookshop2Fixture.create_ShipmentFailedMessage();
		shipmentFailedMessage.setReason("Shipment Failed");
		
		// execute action
		Bookshop2Fixture.reset();
		runAction_send_ShipBooks();
		
		removeMessagesFromDestinations();
		
		// execute "undo" of action
		runAction_send_ShipBooks_undo();
		
		// validate request initiation state
		Assert.isTrue(isFiredRequestReceived("Supplier_ShipBooks"));
		
		// validate remote ShipBooks interaction
		Assert.isTrue(isFiredRequestSent("Shipper_ShipBooks"));
		Assert.isTrue(isFiredResponseDone("Shipper_ShipmentFailed"));
		Assert.isTrue(shipmentRequestReceived);
		
		// validate successful callback state
		Assert.isTrue(isFiredResponseSent("Supplier_ShipmentFailed"));
		Assert.isTrue(shipmentFailedReceived);
		
		// validate non-existent callback state
		Assert.isFalse(isFiredResponseSent("Supplier_ShipmentComplete"));
		Assert.isFalse(isFiredResponseSent("Supplier_ShipmentScheduled"));
		Assert.isFalse(shipmentCompleteReceived);
		Assert.isFalse(shipmentScheduledReceived);
		
		// validate request rolledback state
		Assert.isTrue(isFiredRequestRolledBack("Supplier_ShipBooks"));
		
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 7)
	@BytemanRule(name = "rule7", 
			targetClass = "SupplierProcess", 
			targetMethod = "fire_ShipBooks_request_sent", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"exception message\")")
	public void runTest_ShipBooks_ShipmentFailed_from_ShipBooks_exception() throws Exception {
		String testName = "runTest_ShipBooks_ShipmentFailed_from_ShipBooks_exception";
		log.info(testName+": started");
		
		setupByteman(testName);
		registerNotificationListeners();
		
		// prepare test data
		bookOrderLogHelper.assureRemoveAll();
		
		// setup expectations
		expectedEvent = "Shipper_ShipBooks_Outgoing_Request_Aborted";
		expectedMessage = "bookshop2.supplier.ShipmentFailed";
		exceptionMessage = "exception message";
		
		// setup mock response
		shipmentFailedMessage = Bookshop2Fixture.create_ShipmentFailedMessage();
		shipmentFailedMessage.setReason("exception message");
		
		try {
			// execute action
			Bookshop2Fixture.reset();
			runAction_send_ShipBooks();
			
			// validate request initiation state
			Assert.isTrue(isFiredRequestReceived("Supplier_ShipBooks"));
			
			// validate remote ShipBooks interaction
			Assert.isTrue(isFiredRequestSent("Shipper_ShipBooks"));
			Assert.isTrue(shipmentRequestReceived);
			
			// validate successful callback state
			Assert.isTrue(isFiredResponseSent("Supplier_ShipmentFailed"));
			Assert.isTrue(shipmentFailedReceived);
			
			// validate non-existent callback state
			Assert.isFalse(isFiredResponseSent("Supplier_ShipmentComplete"));
			Assert.isFalse(isFiredResponseSent("Supplier_ShipmentScheduled"));
			Assert.isFalse(shipmentCompleteReceived);
			Assert.isFalse(shipmentScheduledReceived);
			
			// validate outgoing request aborted state
			Assert.isTrue(isFiredOutgoingRequestAborted("Shipper_ShipBooks"));
			
			// validate request completion state
			Assert.isTrue(isFiredRequestHandled("Supplier_ShipBooks"));
			Assert.isTrue(isFiredRequestDone("Supplier_ShipBooks"));

			if (errorMessage != null)
				fail(errorMessage);

		} finally {
			tearDownByteman(testName);
		}
	}
	
	@Test
	//@Ignore
	@InSequence(value = 8)
	public void runTest_ShipBooks_ShipmentScheduled_from_ShipBooks_ShipmentScheduled() throws Exception {
		String testName = "runTest_ShipBooks_ShipmentScheduled_from_ShipBooks_ShipmentScheduled";
		log.info(testName+": started");
		
		registerNotificationListeners();
		
		// prepare test data
		bookOrderLogHelper.assureRemoveAll();
		
		// setup expectations
		expectedEvent = "Shipper_ShipmentScheduled_Response_Done";
		expectedMessage = "bookshop2.supplier.ShipmentScheduled";
		
		// setup expected callback response
		expectedCallback = "ShipmentScheduled";
		shipmentScheduledMessage = createShipmentScheduledMessage();
		
		// setup mock response "shipper.shipBooks"
		shipperShipmentScheduledMessage = Bookshop2Fixture.create_ShipmentScheduledMessage();
		
		// execute action
		Bookshop2Fixture.reset();
		runAction_send_ShipBooks();
		
		// validate request initiation state
		Assert.isTrue(isFiredRequestReceived("Supplier_ShipBooks"));
		
		// validate remote ShipBooks interaction
		Assert.isTrue(isFiredRequestSent("Shipper_ShipBooks"));
		Assert.isTrue(isFiredResponseDone("Shipper_ShipmentScheduled"));
		Assert.isTrue(shipmentRequestReceived);
		
		// validate successful callback state
		Assert.isTrue(isFiredResponseSent("Supplier_ShipmentScheduled"));
		Assert.isTrue(shipmentScheduledReceived);
		
		// validate non-existent callback state
		Assert.isFalse(isFiredResponseSent("Supplier_ShipmentComplete"));
		Assert.isFalse(isFiredResponseSent("Supplier_ShipmentFailed"));
		Assert.isFalse(shipmentCompleteReceived);
		Assert.isFalse(shipmentFailedReceived);

		// validate request completion state
		Assert.isTrue(isFiredRequestHandled("Supplier_ShipBooks"));
		Assert.isFalse(isFiredRequestDone("Supplier_ShipBooks"));
		
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 9)
	public void runTest_ShipBooks_ShipmentScheduled_from_ShipBooks_ShipmentScheduled_cancel() throws Exception {
		String testName = "runTest_ShipBooks_ShipmentScheduled_from_ShipBooks_ShipmentScheduled_cancel";
		log.info(testName+": started");
		
		registerNotificationListeners();
		
		// prepare test data
		bookOrderLogHelper.assureRemoveAll();
		
		// setup expectations
		expectedEvent = "Shipper_ShipmentScheduled_Response_Done";
		expectedMessage = "bookshop2.supplier.ShipmentScheduled";
		
		// setup mock response
		expectedCallback = "ShipmentScheduled";
		shipmentScheduledMessage = createShipmentScheduledMessage();
		shipmentScheduledMessage = Bookshop2Fixture.create_ShipmentScheduledMessage();
		
		// execute action
		Bookshop2Fixture.reset();
		runAction_send_ShipBooks();
		
		// clear message queues
		removeMessagesFromDestinations();
		
		// execute "cancel" of action
		Bookshop2Fixture.reset();
		runAction_send_ShipBooks_cancel();
		
		// validate request initiation state
		Assert.isTrue(isFiredRequestReceived("Supplier_ShipBooks"));
		
		// validate remote ShipBooks interaction
		Assert.isTrue(isFiredRequestSent("Shipper_ShipBooks"));
		Assert.isTrue(isFiredResponseDone("Shipper_ShipmentScheduled"));
		Assert.isTrue(shipmentRequestReceived);
		
		// validate successful callback state
		Assert.isTrue(isFiredResponseSent("Supplier_ShipmentScheduled"));
		Assert.isTrue(shipmentScheduledReceived);
		
		// validate non-existent callback state
		Assert.isFalse(isFiredResponseSent("Supplier_ShipmentComplete"));
		Assert.isFalse(isFiredResponseSent("Supplier_ShipmentFailed"));
		Assert.isFalse(shipmentCompleteReceived);
		Assert.isFalse(shipmentFailedReceived);
		
		// validate request cancellation state
		Assert.isTrue(isFiredRequestCancelled("Supplier_ShipBooks"));
		Assert.isTrue(isFiredRequestRolledBack("Supplier_ShipBooks"));
		
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 10)
	public void runTest_ShipBooks_ShipmentScheduled_from_ShipBooks_ShipmentScheduled_undo() throws Exception {
		String testName = "runTest_ShipBooks_ShipmentScheduled_from_ShipBooks_ShipmentScheduled_undo";
		log.info(testName+": started");
		
		registerNotificationListeners();
		
		// prepare test data
		bookOrderLogHelper.assureRemoveAll();
		
		// setup expectations
		expectedEvent = "Shipper_ShipmentScheduled_Response_Done";
		expectedMessage = "bookshop2.supplier.ShipmentScheduled";
		
		// setup mock response
		expectedCallback = "ShipmentScheduled";
		shipmentScheduledMessage = createShipmentScheduledMessage();
		shipmentScheduledMessage = Bookshop2Fixture.create_ShipmentScheduledMessage();
		
		// execute action
		Bookshop2Fixture.reset();
		runAction_send_ShipBooks();
		
		removeMessagesFromDestinations();
		
		// execute "undo" of action
		runAction_send_ShipBooks_undo();
		
		// validate request initiation state
		Assert.isTrue(isFiredRequestReceived("Supplier_ShipBooks"));
		
		// validate remote ShipBooks interaction
		Assert.isTrue(isFiredRequestSent("Shipper_ShipBooks"));
		Assert.isTrue(isFiredResponseDone("Shipper_ShipmentScheduled"));
		Assert.isTrue(shipmentRequestReceived);
		
		// validate successful callback state
		Assert.isTrue(isFiredResponseSent("Supplier_ShipmentScheduled"));
		Assert.isTrue(shipmentScheduledReceived);
		
		// validate non-existent callback state
		Assert.isFalse(isFiredResponseSent("Supplier_ShipmentComplete"));
		Assert.isFalse(isFiredResponseSent("Supplier_ShipmentFailed"));
		Assert.isFalse(shipmentCompleteReceived);
		Assert.isFalse(shipmentFailedReceived);
		
		// validate request rolledback state
		Assert.isTrue(isFiredRequestRolledBack("Supplier_ShipBooks"));
		
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	@Ignore
	@InSequence(value = 11)
	@BytemanRule(name = "rule11", 
			targetClass = "ShipBooksHandlerImpl", 
			targetMethod = "shipBooks", 
			targetLocation = "AT ENTRY", 
			action = "$0.timeout = 0")
	public void runTest_ShipBooks_timeout() throws Exception {
		String testName = "runTest_ShipBooks_timeout";
		log.info(testName+": started");
		
		setupByteman(testName);
		registerNotificationListeners();
		
		// setup expectations
		expectedEvent = "Supplier_ShipBooks_Incoming_Request_Aborted";
		//TODO expectedMessage = "bookshop2.supplier.ShipmentRequest";
		
		try {
			// execute action
			Bookshop2Fixture.reset();
			runAction_send_ShipBooks();

			// validate request initiation state
			Assert.isTrue(isFiredRequestReceived("Supplier_ShipBooks"));
			
			// validate request non-completion state
			Assert.isFalse(isFiredRequestHandled("Supplier_ShipBooks"));
			Assert.isFalse(isFiredRequestDone("Supplier_ShipBooks"));
			
			// validate incoming request aborted state
			Assert.isTrue(isFiredIncomingRequestAborted("Supplier_ShipBooks"));
			
			if (errorMessage != null)
				fail(errorMessage);
			
		} finally {
			tearDownByteman(testName);
		}
	}
	
	@Test
	@Ignore
	@InSequence(value = 12)
	@BytemanRule(name = "rule12", 
			targetClass = "SupplierProcess", 
			targetMethod = "shipBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"exception message\")")
	public void runTest_ShipBooks_exception() throws Exception {
		String testName = "runTest_ShipBooks_exception";
		log.info(testName+": started");
		
		setupByteman(testName);
		registerNotificationListeners();
		
		// setup expectations
		expectedEvent = "Supplier_ShipBooks_Incoming_Request_Aborted";
		expectedMessage = "bookshop2.supplier.ShipmentRequest";
		exceptionMessage = "exception message";
		
		try {
			// execute action
			Bookshop2Fixture.reset();
			runAction_send_ShipBooks();

			// validate request initiation state
			Assert.isTrue(isFiredRequestReceived("Supplier_ShipBooks"));
			
			// validate request non-completion state
			Assert.isFalse(isFiredRequestHandled("Supplier_ShipBooks"));
			Assert.isFalse(isFiredRequestDone("Supplier_ShipBooks"));
			
			// validate incoming request aborted state
			Assert.isTrue(isFiredIncomingRequestAborted("Supplier_ShipBooks"));
			
			if (errorMessage != null)
				fail(errorMessage);
			
		} finally {
			tearDownByteman(testName);
		}
	}
	
}
