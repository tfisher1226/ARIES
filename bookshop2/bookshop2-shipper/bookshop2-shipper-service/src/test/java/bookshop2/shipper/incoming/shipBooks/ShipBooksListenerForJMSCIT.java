package bookshop2.shipper.incoming.shipBooks;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.aries.Assert;
import org.aries.common.AbstractEvent;
import org.aries.event.multicaster.EventMulticasterProxy;
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

import bookshop2.ShipmentCompleteMessage;
import bookshop2.ShipmentConfirmedEvent;
import bookshop2.ShipmentFailedMessage;
import bookshop2.ShipmentRequestMessage;
import bookshop2.ShipmentScheduledEvent;
import bookshop2.ShipmentScheduledMessage;
import bookshop2.shipper.ShipperTestEARBuilder;
import bookshop2.shipper.client.shipBooks.ShipBooks;
import bookshop2.shipper.client.shipBooks.ShipBooksProxyForJMS;
import bookshop2.shipper.event.ShipperEventReceiverMBean;
import bookshop2.util.Bookshop2Fixture;
import bookshop2.util.Bookshop2Helper;


@RunAsClient
@RunWith(Arquillian.class)
public class ShipBooksListenerForJMSCIT extends AbstractJMSListenerArquillionTest {
	
	private JmsClient shipBooksClient;
	
	private JmsClient localShipperShipmentCompleteHandler;
	
	private JmsClient localShipperShipmentFailedHandler;
	
	private JmsClient localShipperShipmentScheduledHandler;
	
	private ShipmentRequestMessage shipmentRequestMessage;
	
	private ShipmentCompleteMessage shipmentCompleteMessage;
	
	private ShipmentFailedMessage shipmentFailedMessage;
	
	private ShipmentScheduledMessage shipmentScheduledMessage;
	
	private TransactionTestControl transactionTestControl;
	
	private ShipmentConfirmedEvent shipmentConfirmedEvent;

	private ShipmentScheduledEvent shipmentScheduledEvent;

	private EventMulticasterProxy eventMulticasterProxy;
	
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
		return "bookshop2.shipper";
	}
	
	@Override
	public String getServiceId() {
		return "bookshop2.shipper.shipBooks";
	}
	
	@Override
	public String getTargetArchive() {
		return ShipperTestEARBuilder.NAME;
	}
	
	@Override
	public String getTargetDestination() {
		return get_target_Shipper_ShipBooks_destination();
	}
	
	public String get_target_Shipper_ShipBooks_destination() {
		return getJNDINameForQueue("shipment_bookshop2_shipper_ship_books_queue");
	}
	
	public String get_local_Shipper_ShipmentComplete_destination() {
		return "test_message_domain_test_destination_queue_a";
	}

	public String get_local_Shipper_ShipmentFailed_destination() {
		return "test_message_domain_test_destination_queue_b";
	}
	
	public String get_local_Shipper_ShipmentScheduled_destination() {
		return "test_message_domain_test_destination_queue_c";
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		startServer();
		createTransactionControl();
		createEventMulticasterProxy();
		Bookshop2Fixture.reset();
	}
	
	protected void createTransactionControl() throws Exception {
		transactionTestControl = new TransactionTestControl();
		transactionTestControl.setupTransactionManager();
	}
	
	protected void createEventMulticasterProxy() throws Exception {
		eventMulticasterProxy = new EventMulticasterProxy();
		eventMulticasterProxy.setJmxManager(jmxManager);
		eventMulticasterProxy.setMBeanName(ShipperEventReceiverMBean.MBEAN_NAME);
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
		message.addToReplyToDestinations("ShipmentComplete", get_local_Shipper_ShipmentComplete_destination());
		message.addToReplyToDestinations("ShipmentFailed", get_local_Shipper_ShipmentFailed_destination());
		message.addToReplyToDestinations("ShipmentScheduled", get_local_Shipper_ShipmentScheduled_destination());
		initializeMessage(message);
		return message;
	}
	
	public void createShipmentConfirmedEvent() {
		shipmentConfirmedEvent = Bookshop2Fixture.create_ShipmentConfirmedEvent();
		initializeEvent(shipmentConfirmedEvent);
	}

	public void createShipmentScheduledEvent() {
		shipmentScheduledEvent = Bookshop2Fixture.create_ShipmentScheduledEvent();
		initializeEvent(shipmentScheduledEvent);
	}
	
	protected void registerNotificationListeners() throws Exception {
		addRequestNotificationListeners("Shipper_ShipBooks");
		addResponseNotificationListeners("Shipper_ShipmentComplete");
		addResponseNotificationListeners("Shipper_ShipmentFailed");
		addResponseNotificationListeners("Shipper_ShipmentScheduled");
		addProcessNotificationListeners("Shipper_ShipmentConfirmed");
		addProcessNotificationListeners("Shipper_ShipmentScheduled");
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
		shipBooksClient.reset();
		shipBooksClient = null;
		
		//local handlers for responses from target service
		localShipperShipmentCompleteHandler.reset();
		localShipperShipmentCompleteHandler = null;
		localShipperShipmentFailedHandler.reset();
		localShipperShipmentFailedHandler = null;
		localShipperShipmentScheduledHandler.reset();
		localShipperShipmentScheduledHandler = null;
		super.clearStructures();
	}

	protected void clearState() throws Exception {
		shipmentRequestMessage = null;
		shipmentCompleteMessage = null;
		shipmentFailedMessage = null;
		shipmentScheduledMessage = null;
		shipmentConfirmedEvent = null;
		shipmentScheduledEvent = null;
		shipmentCompleteReceived = false;
		shipmentFailedReceived = false;
		shipmentScheduledReceived = false;
		super.clearState();
	}
	
	protected void removeMessagesFromDestinations() throws Exception {
		removeMessagesFromQueue(getTargetArchive(), get_target_Shipper_ShipBooks_destination());
		removeMessagesFromQueue(getTargetArchive(), get_local_Shipper_ShipmentComplete_destination());
		removeMessagesFromQueue(getTargetArchive(), get_local_Shipper_ShipmentFailed_destination());
		removeMessagesFromQueue(getTargetArchive(), get_local_Shipper_ShipmentScheduled_destination());
	}
	
	@TargetsContainer("hornetQ01_local")
	@Deployment(name = "shipperEAR", order = 2)
	public static EnterpriseArchive createShipperEAR() {
		ShipperTestEARBuilder builder = new ShipperTestEARBuilder();
		builder.setRunningAsClient(true);
		//builder.setIncludeWar(true);
		return builder.createEAR();
	}
	
	protected boolean eventsExist() {
		return shipmentConfirmedEvent != null ||
			shipmentScheduledEvent != null;
	}

	protected void fireEvents() throws Exception {
		if (shipmentConfirmedEvent != null)
			fireEvent(shipmentConfirmedEvent);
		if (shipmentScheduledEvent != null)
			fireEvent(shipmentScheduledEvent);
	}

	protected void fireEvent(AbstractEvent event) throws Exception {
		eventMulticasterProxy.dispatchEvent(event);
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
		
		// start local handlers for responses from target service
		localShipperShipmentCompleteHandler = start_local_Shipper_ShipmentComplete_handler();
		localShipperShipmentFailedHandler = start_local_Shipper_ShipmentFailed_handler();
		localShipperShipmentScheduledHandler = start_local_Shipper_ShipmentScheduled_handler();
		
		// start fixture execution
		shipBooksClient = start_ShipBooks_client();
		shipBooksClient.send(shipmentRequestMessage, correlationId, null);
		
		// fire events if needed
		if (eventsExist()) {
			Thread.sleep(2000);
			fireEvents();
		}
		
		// wait for completion result
		Object result = waitForCompletion();
		validateResult(result);
		
		// close context
		//commitTransaction();
		
		// validate state
		assertEmptyTargetDestination();
	}
	
	protected void runAction_send_ShipBooks_cancel() throws Exception {
		expectedEvent = "Shipper_ShipBooks_Request_Cancel_Done";
		registerForResult(expectedEvent);
		
		ShipmentRequestMessage message = createShipmentRequestCancelMessage();
		shipBooksClient.send(message, correlationId, null);
		
		Object result = waitForCompletion();
		validateResult(result);
	}
	
	protected void runAction_send_ShipBooks_undo() throws Exception {
		expectedEvent = "Shipper_ShipBooks_Request_Undo_Done";
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
	
	protected JmsClient start_local_Shipper_ShipmentComplete_handler() throws Exception {
		JmsClient client = createClient(getJNDINameForQueue(get_local_Shipper_ShipmentComplete_destination()));
		client.setMessageListener(create_local_Shipper_ShipmentComplete_response_listener());
		client.initialize();
		return client;
	}
	
	protected JmsClient start_local_Shipper_ShipmentFailed_handler() throws Exception {
		JmsClient client = createClient(getJNDINameForQueue(get_local_Shipper_ShipmentFailed_destination()));
		client.setMessageListener(create_local_Shipper_ShipmentFailed_response_listener());
		client.initialize();
		return client;
	}
	
	protected JmsClient start_local_Shipper_ShipmentScheduled_handler() throws Exception {
		JmsClient client = createClient(getJNDINameForQueue(get_local_Shipper_ShipmentScheduled_destination()));
		client.setMessageListener(create_local_Shipper_ShipmentScheduled_response_listener());
		client.initialize();
		return client;
	}
	
	protected MessageListener create_local_Shipper_ShipmentComplete_response_listener() {
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
					if (expectedMessage != null && expectedMessage.equalsIgnoreCase("bookshop2.shipper.ShipmentComplete"))
						expectedMessageResult.set(true);
					shipmentCompleteReceived = true;
				}
			}
		};
	}
	
	protected MessageListener create_local_Shipper_ShipmentFailed_response_listener() {
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
					if (expectedMessage != null && expectedMessage.equalsIgnoreCase("bookshop2.shipper.ShipmentFailed"))
						expectedMessageResult.set(true);
					shipmentFailedReceived = true;
				}
			}
		};
	}
	
	protected MessageListener create_local_Shipper_ShipmentScheduled_response_listener() {
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
					if (expectedMessage != null && expectedMessage.equalsIgnoreCase("bookshop2.shipper.ShipmentScheduled"))
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
	public void runTest_ShipBooks_ShipmentConfirmed() throws Exception {
		String testName = "runTest_ShipBooks_ShipmentConfirmed";
		log.info(testName+": started");
		
		registerNotificationListeners();
		createShipmentConfirmedEvent();
		
		expectedEvent = "Shipper_ShipmentConfirmed_Process_Complete";
		expectedMessage = "bookshop2.shipper.ShipmentComplete";
		
		// execute action
		Bookshop2Fixture.reset();
		runAction_send_ShipBooks();
		
		// validate successful callback state
		Assert.isTrue(isFiredResponseSent("Shipper_ShipmentComplete"));
		Assert.isTrue(shipmentCompleteReceived);
		
		// validate non-existent callback state
		Assert.isFalse(isFiredResponseSent("Shipper_ShipmentFailed"));
		Assert.isFalse(isFiredResponseSent("Shipper_ShipmentScheduled"));
		Assert.isFalse(shipmentFailedReceived);
		Assert.isFalse(shipmentScheduledReceived);

		// validate process completion state
		Assert.isTrue(isFiredProcessComplete("Shipper_ShipmentConfirmed"));
		Assert.isFalse(isFiredProcessComplete("Shipper_ShipmentScheduled"));

		// validate request completion state
		Assert.isTrue(isFiredRequestHandled("Shipper_ShipBooks"));
		Assert.isTrue(isFiredRequestDone("Shipper_ShipBooks"));

		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 2)
	public void runTest_ShipBooks_ShipmentConfirmed_cancel() throws Exception {
		String testName = "runTest_ShipBooks_ShipmentConfirmed_cancel";
		log.info(testName+": started");
		
		registerNotificationListeners();
		
		expectedEvent = "Shipper_ShipmentConfirmed_Process_Aborted";
		expectedError = "ShipmentConfirmed execution cancelled";
		
		// execute action
		Thread thread = start_runAction_send_ShipBooks();
		ShipmentRequestMessage message = createShipmentRequestCancelMessage();
		shipBooksClient.send(message, correlationId, null);
		thread.join();
		
		// validate non-existent callback state
		Assert.isFalse(isFiredResponseSent("Shipper_ShipmentComplete"));
		Assert.isFalse(isFiredResponseSent("Shipper_ShipmentFailed"));
		Assert.isFalse(isFiredResponseSent("Shipper_ShipmentScheduled"));
		Assert.isFalse(shipmentCompleteReceived);
		Assert.isFalse(shipmentFailedReceived);
		Assert.isFalse(shipmentScheduledReceived);

		// validate process aborted state
		Assert.isTrue(isFiredProcessAborted("Shipper_ShipmentConfirmed"));
		Assert.isTrue(isFiredProcessAborted("Shipper_ShipmentScheduled"));

		// validate process non-completion state
		Assert.isFalse(isFiredProcessComplete("Shipper_ShipmentConfirmed"));
		Assert.isFalse(isFiredProcessComplete("Shipper_ShipmentScheduled"));
		
		// validate request cancellation state
		Assert.isTrue(isFiredRequestCancelled("Shipper_ShipBooks"));
		Assert.isTrue(isFiredRequestRolledBack("Shipper_ShipBooks"));

		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 3)
	@BytemanRule(name = "rule3",
		targetClass = "ShipperProcess",
		targetMethod = "handle_ShipmentConfirmed_event",
		targetLocation = "AT ENTRY",
			action = "throw new org.aries.ApplicationFailure(\"exception message\")")
	public void runTest_ShipBooks_ShipmentConfirmed_exception() throws Exception {
		String testName = "runTest_ShipBooks_ShipmentConfirmed_exception";
		log.info(testName+": started");
		
		setupByteman(testName);
		registerNotificationListeners();
		createShipmentConfirmedEvent();
		
		expectedEvent = "Shipper_ShipBooks_Request_Done";
		expectedMessage = "bookshop2.shipper.ShipmentFailed";
		shipmentFailedReason = "exception message";
		
		//execute test
		runAction_send_ShipBooks();
		
		// validate failure callback state
		Assert.isTrue(isFiredResponseSent("Shipper_ShipmentFailed"));
		Assert.isTrue(shipmentFailedReceived);

		// validate non-existent callback state
		Assert.isFalse(isFiredResponseSent("Shipper_ShipmentComplete"));
		Assert.isFalse(isFiredResponseSent("Shipper_ShipmentScheduled"));
		Assert.isFalse(shipmentCompleteReceived);
		Assert.isFalse(shipmentScheduledReceived);

		// validate process aborted state
		Assert.isTrue(isFiredProcessAborted("Shipper_ShipmentConfirmed"));
		Assert.isFalse(isFiredProcessAborted("Shipper_ShipmentScheduled"));

		// validate request completion state
		Assert.isTrue(isFiredRequestHandled("Shipper_ShipBooks"));
		Assert.isTrue(isFiredRequestDone("Shipper_ShipBooks"));

		//cleanup
		tearDownByteman(testName);
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 4)
	@BytemanRule(name = "rule4",
		targetClass = "ShipmentConfirmedExecutor",
		targetMethod = "register",
		targetLocation = "AT ENTRY",
		action = "$0.timeout = 0")
	public void runTest_ShipBooks_ShipmentConfirmed_timeout() throws Exception {
		String testName = "runTest_ShipBooks_ShipmentConfirmed_timeout";
		log.info(testName+": started");

		setupByteman(testName);
		registerNotificationListeners();

		expectedEvent = "Shipper_ShipBooks_Request_Done";
		expectedError = "ShipmentConfirmed execution timed-out";
		shipmentFailedReason = "ShipmentConfirmed timed-out";

		//execute test
		runAction_send_ShipBooks();

		// validate failure callback state
		Assert.isTrue(isFiredResponseSent("Shipper_ShipmentFailed"));
		Assert.isTrue(shipmentFailedReceived);

		// validate non-existent callback state
		Assert.isFalse(isFiredResponseSent("Shipper_ShipmentComplete"));
		Assert.isFalse(isFiredResponseSent("Shipper_ShipmentScheduled"));
		Assert.isFalse(shipmentCompleteReceived);
		Assert.isFalse(shipmentScheduledReceived);

		// validate process aborted state
		Assert.isTrue(isFiredProcessAborted("Shipper_ShipmentConfirmed"));
		Assert.isFalse(isFiredProcessAborted("Shipper_ShipmentScheduled"));

		// validate request completion state
		Assert.isTrue(isFiredRequestHandled("Shipper_ShipBooks"));
		Assert.isTrue(isFiredRequestDone("Shipper_ShipBooks"));
		
		//cleanup
		tearDownByteman(testName);
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 5)
	public void runTest_ShipBooks_ShipmentScheduled() throws Exception {
		String testName = "runTest_ShipBooks_ShipmentScheduled";
		log.info(testName+": started");
		
		registerNotificationListeners();
		createShipmentScheduledEvent();
		
		expectedEvent = "Shipper_ShipmentScheduled_Process_Complete";
		expectedMessage = "bookshop2.shipper.ShipmentScheduled";
		
		// execute action
		Bookshop2Fixture.reset();
		runAction_send_ShipBooks();

		// validate successful callback state
		Assert.isTrue(isFiredResponseSent("Shipper_ShipmentScheduled"));
		Assert.isTrue(shipmentScheduledReceived);
		
		// validate non-existent callback state
		Assert.isFalse(isFiredResponseSent("Shipper_ShipmentComplete"));
		Assert.isFalse(isFiredResponseSent("Shipper_ShipmentFailed"));
		Assert.isFalse(shipmentCompleteReceived);
		Assert.isFalse(shipmentFailedReceived);

		// validate process completion state
		Assert.isFalse(isFiredProcessComplete("Shipper_ShipmentConfirmed"));
		Assert.isTrue(isFiredProcessComplete("Shipper_ShipmentScheduled"));

		// validate request completion state
		Assert.isTrue(isFiredRequestHandled("Shipper_ShipBooks"));
		Assert.isFalse(isFiredRequestDone("Shipper_ShipBooks"));
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 6)
	public void runTest_ShipBooks_ShipmentScheduled_cancel() throws Exception {
		String testName = "runTest_ShipBooks_ShipmentScheduled_cancel";
		log.info(testName+": started");
		
		registerNotificationListeners();
		
		expectedEvent = "Shipper_ShipmentScheduled_Process_Aborted";
		expectedError = "ShipmentScheduled execution cancelled";
		
		// execute action
		Thread thread = start_runAction_send_ShipBooks();
		ShipmentRequestMessage message = createShipmentRequestCancelMessage();
		shipBooksClient.send(message, correlationId, null);
		thread.join();
		
		// validate non-existent callback state
		Assert.isFalse(isFiredResponseSent("Shipper_ShipmentComplete"));
		Assert.isFalse(isFiredResponseSent("Shipper_ShipmentFailed"));
		Assert.isFalse(isFiredResponseSent("Shipper_ShipmentScheduled"));
		Assert.isFalse(shipmentCompleteReceived);
		Assert.isFalse(shipmentFailedReceived);
		Assert.isFalse(shipmentScheduledReceived);

		// validate process aborted state
		Assert.isTrue(isFiredProcessAborted("Shipper_ShipmentConfirmed"));
		Assert.isTrue(isFiredProcessAborted("Shipper_ShipmentScheduled"));
		
		// validate process non-completion state
		Assert.isFalse(isFiredProcessComplete("Shipper_ShipmentConfirmed"));
		Assert.isFalse(isFiredProcessComplete("Shipper_ShipmentScheduled"));
		
		// validate request cancellation state
		Assert.isTrue(isFiredRequestCancelled("Shipper_ShipBooks"));
		Assert.isTrue(isFiredRequestRolledBack("Shipper_ShipBooks"));
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 7)
	@BytemanRule(name = "rule7",
		targetClass = "ShipperProcess",
		targetMethod = "handle_ShipmentScheduled_event",
		targetLocation = "AT ENTRY",
			action = "throw new org.aries.ApplicationFailure(\"exception message\")")
	public void runTest_ShipBooks_ShipmentScheduled_exception() throws Exception {
		String testName = "runTest_ShipBooks_ShipmentScheduled_exception";
		log.info(testName+": started");
		
		setupByteman(testName);
		registerNotificationListeners();
		createShipmentScheduledEvent();
		
		expectedEvent = "Shipper_ShipBooks_Request_Done";
		expectedMessage = "bookshop2.shipper.ShipmentFailed";
		shipmentFailedReason = "exception message";
		
		//execute test
		runAction_send_ShipBooks();
		
		// validate failure callback state
		Assert.isTrue(isFiredResponseSent("Shipper_ShipmentFailed"));
		Assert.isTrue(shipmentFailedReceived);

		// validate non-existent callback state
		Assert.isFalse(isFiredResponseSent("Shipper_ShipmentComplete"));
		Assert.isFalse(isFiredResponseSent("Shipper_ShipmentScheduled"));
		Assert.isFalse(shipmentCompleteReceived);
		Assert.isFalse(shipmentScheduledReceived);

		// validate process aborted state
		Assert.isFalse(isFiredProcessAborted("Shipper_ShipmentConfirmed"));
		Assert.isTrue(isFiredProcessAborted("Shipper_ShipmentScheduled"));

		// validate request completion state
		Assert.isTrue(isFiredRequestHandled("Shipper_ShipBooks"));
		Assert.isTrue(isFiredRequestDone("Shipper_ShipBooks"));

		//cleanup
		tearDownByteman(testName);
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 8)
	@BytemanRule(name = "rule8",
		targetClass = "ShipmentScheduledExecutor",
		targetMethod = "register",
		targetLocation = "AT ENTRY",
		action = "$0.timeout = 0")
	public void runTest_ShipBooks_ShipmentScheduled_timeout() throws Exception {
		String testName = "runTest_ShipBooks_ShipmentScheduled_timeout";
		log.info(testName+": started");
		
		setupByteman(testName);
		registerNotificationListeners();
		
		expectedEvent = "Shipper_ShipBooks_Request_Done";
		expectedError = "ShipmentScheduled execution timed-out";
		shipmentFailedReason = "ShipmentScheduled timed-out";

		//execute test
		runAction_send_ShipBooks();

		// validate failure callback state
		Assert.isTrue(isFiredResponseSent("Shipper_ShipmentFailed"));
		Assert.isTrue(shipmentFailedReceived);
		
		// validate non-existent callback state
		Assert.isFalse(isFiredResponseSent("Shipper_ShipmentComplete"));
		Assert.isFalse(isFiredResponseSent("Shipper_ShipmentScheduled"));
		Assert.isFalse(shipmentCompleteReceived);
		Assert.isFalse(shipmentScheduledReceived);

		// validate process aborted state
		Assert.isFalse(isFiredProcessAborted("Shipper_ShipmentConfirmed"));
		Assert.isTrue(isFiredProcessAborted("Shipper_ShipmentScheduled"));
		
		// validate request completion state
		Assert.isTrue(isFiredRequestHandled("Shipper_ShipBooks"));
		Assert.isTrue(isFiredRequestDone("Shipper_ShipBooks"));

		//cleanup
		tearDownByteman(testName);
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	@Ignore
	@InSequence(value = 9)
	@BytemanRule(name = "rule9", 
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
		expectedEvent = "Shipper_ShipBooks_Incoming_Request_Aborted";
		//TODO expectedMessage = "bookshop2.shipper.ShipmentRequest";
		
		// execute action
		Bookshop2Fixture.reset();
		runAction_send_ShipBooks();
		
		// validate request initiation state
		Assert.isTrue(isFiredRequestReceived("Shipper_ShipBooks"));
		
		// validate request non-completion state
		Assert.isFalse(isFiredRequestHandled("Shipper_ShipBooks"));
		Assert.isFalse(isFiredRequestDone("Shipper_ShipBooks"));
		
		// validate incoming request aborted state
		Assert.isTrue(isFiredIncomingRequestAborted("Shipper_ShipBooks"));
		
		// cleanup
		tearDownByteman(testName);
		removeMessagesFromDestinations();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	@Ignore
	@InSequence(value = 10)
	@BytemanRule(name = "rule10", 
			targetClass = "ShipperProcess", 
			targetMethod = "shipBooks", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"exception message\")")
	public void runTest_ShipBooks_exception() throws Exception {
		String testName = "runTest_ShipBooks_exception";
		log.info(testName+": started");
		
		setupByteman(testName);
		registerNotificationListeners();
		
		// setup expectations
		expectedEvent = "Shipper_ShipBooks_Incoming_Request_Aborted";
		expectedMessage = "bookshop2.shipper.ShipmentRequest";
		exceptionMessage = "exception message";
		
		// execute action
		Bookshop2Fixture.reset();
		runAction_send_ShipBooks();
		
		// validate request initiation state
		Assert.isTrue(isFiredRequestReceived("Shipper_ShipBooks"));
		
		// validate request non-completion state
		Assert.isFalse(isFiredRequestHandled("Shipper_ShipBooks"));
		Assert.isFalse(isFiredRequestDone("Shipper_ShipBooks"));
		
		// validate incoming request aborted state
		Assert.isTrue(isFiredIncomingRequestAborted("Shipper_ShipBooks"));
		
		// cleanup
		tearDownByteman(testName);
		removeMessagesFromDestinations();
		
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	

//	@Test
//	//@Ignore
//	@InSequence(value = 11)
//	public void test_ShipBooks_ShipmentComplete() throws Exception {
//		String testName = "test_ShipBooks_ShipmentComplete";
//		log.info(testName+": started");
//		registerNotificationListeners();
//		createShipmentScheduledEvent();
//		createShipmentConfirmedEvent();
//		//expectedEvent = "bookshop2.shipper.ShipBooks_Request_Done";
//		expectedMessage = "bookshop2.shipper.ShipmentComplete";
//		
//		//execution started
//		runTest_ShipBooks();
//		Thread.sleep(2000);
//		
//		//execution finished
//		Assert.isTrue(shipmentScheduledReceived);
//		Assert.isTrue(shipmentCompleteReceived);
//		Assert.isFalse(shipmentFailedReceived);
//		Assert.isTrue(isFiredRequestDone("ShipBooks"));
//		Assert.isTrue(isFiredResponseSent("ShipmentScheduled"));
//		Assert.isTrue(isFiredResponseSent("ShipmentComplete"));
//		Assert.isFalse(isFiredResponseSent("ShipmentFailed"));
//		Assert.isTrue(isFiredProcessComplete("ShipmentScheduled"));
//		Assert.isTrue(isFiredProcessComplete("ShipmentConfirmed"));
//		log.info(testName+": done");
//		if (errorMessage != null)
//			fail(errorMessage);
//	}
//	
//	@Test
//	//@Ignore
//	@InSequence(value = 14)
//	public void test_ShipBooks_Cancel() throws Exception {
//		String testName = "test_ShipBooks_Cancel";
//		log.info(testName+": started");
//		registerNotificationListeners();
//		
//		//expectedError = "ShipmentScheduled execution cancelled";
//		expectedEvent = "bookshop2.shipper.ShipBooks_Cancel_Request_Done";
//
//		//execution started
//		Thread thread = startRunTest_ShipBooks();
//		clearState();
//		ShipmentRequestMessage message = createShipmentRequestCancelMessage();
//		shipBooksClient.send(message, correlationId, null);
//		thread.join();
//		
//		//validation
//		Assert.isFalse(shipmentScheduledReceived);
//		Assert.isFalse(shipmentCompleteReceived);
//		Assert.isFalse(shipmentFailedReceived);
//		Assert.isTrue(isFiredRequestCancelled("ShipBooks"));
//		Assert.isTrue(isFiredRequestRolledBack("ShipBooks"));
//		Assert.isFalse(isFiredResponseSent("ShipmentComplete"));
//		Assert.isFalse(isFiredResponseSent("ShipmentFailed"));
//		Assert.isTrue(isFiredProcessAborted("ShipmentScheduled"));
//		Assert.isTrue(isFiredProcessAborted("ShipmentConfirmed"));
//		log.info(testName+": done");
//		if (errorMessage != null)
//			fail(errorMessage);
//	}
//	
//	@Test
//	//@Ignore
//	@InSequence(value = 15)
//	public void test_ShipBooks_Undo() throws Exception {
//		String testName = "test_ShipBooks_Undo";
//		log.info(testName+": started");
//		registerNotificationListeners();
//
//		//expectedError = "ShipmentScheduled execution cancelled";
//		//execution started
//		createShipmentScheduledEvent();
//		createShipmentConfirmedEvent();
//		runTest_ShipBooks();
//		//execution finished
//		
//		Assert.isTrue(shipmentScheduledReceived);
//		Assert.isTrue(shipmentCompleteReceived);
//		Assert.isFalse(shipmentFailedReceived);
//		Assert.isFalse(isFiredRequestCancelled("ShipBooks"));
//		Assert.isFalse(isFiredRequestRolledBack("ShipBooks"));
//		Assert.isTrue(isFiredResponseSent("ShipmentScheduled"));
//		Assert.isTrue(isFiredResponseSent("ShipmentComplete"));
//		Assert.isTrue(isFiredProcessComplete("ShipmentScheduled"));
//		Assert.isTrue(isFiredProcessComplete("ShipmentConfirmed"));
//
//		//execution started
//		clearState();
//		expectedEvent = "bookshop2.shipper.ShipBooks_Undo_Request_Done";
//		registerForResult();
//		ShipmentRequestMessage message = createShipmentRequestUndoMessage();
//		shipBooksClient.send(message, correlationId, null);
//		Object result = waitForCompletion();
//		validateResult(result);
//		//execution finished
//
//		//validation
//		Assert.isFalse(shipmentScheduledReceived);
//		Assert.isFalse(shipmentCompleteReceived);
//		Assert.isFalse(shipmentFailedReceived);
//		Assert.isTrue(isFiredRequestDone("ShipBooks"));
//		Assert.isFalse(isFiredRequestCancelled("ShipBooks"));
//		Assert.isTrue(isFiredRequestRolledBack("ShipBooks"));
//		log.info(testName+": done");
//		if (errorMessage != null)
//			fail(errorMessage);
//	}
//	
//	@Test
//	@Ignore
//	@InSequence(value = 12)
//	@BMRule(name = "rule6",
//		targetClass = "ShipperProcess",
//		targetMethod = "waitForShipmentConfirmation",
//		targetLocation = "AT EXIT",
//		action = "throw new java.lang.RuntimeException(\"exception message\")")
//	public void test_ShipBooks_ShipmentFailed() throws Exception {
//		String testName = "test_ShipBooks_ShipmentFailed";
//		log.info(testName+": started");
//		registerNotificationListeners();
//		setupByteman(testName);
//		shipmentFailedReason = "exception message";
//		//execution started
//		runTest_ShipBooks();
//		//execution finished
//		Assert.isTrue(shipmentScheduledReceived);
//		Assert.isFalse(shipmentCompleteReceived);
//		Assert.isTrue(shipmentFailedReceived);
//		Assert.isTrue(isFiredRequestDone("ShipBooks"));
//		Assert.isTrue(isFiredResponseSent("ShipmentScheduled"));
//		Assert.isFalse(isFiredResponseSent("ShipmentComplete"));
//		Assert.isTrue(isFiredResponseSent("ShipmentFailed"));
//		tearDownByteman(testName);
//		log.info(testName+": done");
//		if (errorMessage != null)
//			fail(errorMessage);
//	}

}
