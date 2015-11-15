package bookshop2.shipper;

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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import bookshop2.Invoice;
import bookshop2.Shipment;
import bookshop2.ShipmentCompleteEvent;
import bookshop2.ShipmentCompleteMessage;
import bookshop2.ShipmentConfirmedEvent;
import bookshop2.ShipmentFailedMessage;
import bookshop2.ShipmentRequestMessage;
import bookshop2.ShipmentScheduledEvent;
import bookshop2.ShipmentScheduledMessage;
import bookshop2.shipper.client.shipBooks.ShipBooks;
import bookshop2.shipper.client.shipBooks.ShipBooksProxyForJMS;
import bookshop2.shipper.event.ShipperEventReceiverMBean;
import bookshop2.shipper.outgoing.shipmentComplete.ShipmentCompleteReply;
import bookshop2.shipper.outgoing.shipmentFailed.ShipmentFailedReply;
import bookshop2.shipper.outgoing.shipmentScheduled.ShipmentScheduledReply;
import bookshop2.util.Bookshop2Fixture;


@RunAsClient
@RunWith(Arquillian.class)
public class ShipBooksJMSListenerCIT extends AbstractJMSListenerArquillionTest {
	
	private TransactionTestControl transactionTestControl;
	
	private EventMulticasterProxy eventMulticasterProxy;
	
	private JmsClient shipBooksClient;
	
	private JmsClient shipmentCompleteHandler;
	
	private JmsClient shipmentScheduledHandler;
	
	private JmsClient shipmentFailedHandler;
	
	private ShipmentRequestMessage shipmentRequestMessage;

	private ShipmentScheduledMessage shipmentScheduledMessage;
	
	private ShipmentCompleteMessage shipmentCompleteMessage;

	private ShipmentFailedMessage shipmentFailedMessage;

	private ShipmentScheduledEvent shipmentScheduledEvent;

	private ShipmentConfirmedEvent shipmentConfirmedEvent;

	private ShipmentCompleteEvent shipmentCompleteEvent;
	
	private boolean shipmentScheduledReceived;

	private boolean shipmentCompleteReceived;

	private boolean shipmentFailedReceived;

	private Object shipmentFailedReason;

	
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
		return "bookshop2.shipper.ShipBooks";
	}
	
	@Override
	public String getTargetArchive() {
		return ShipperTestEARBuilder.NAME;
	}
	
	@Override
	public String getTargetDestination() {
		return getJNDINameForQueue("shipment_bookshop2_shipper_ship_books_queue");
	}
	
	public String getLocalShipmentScheduledDestination() {
		return "test_message_domain_test_destination_queue_a";
	}

	public String getLocalShipmentCompleteDestination() {
		return "test_message_domain_test_destination_queue_b";
	}

	public String getLocalShipmentFailedDestination() {
		return "test_message_domain_test_destination_queue_c";
	}
	
	@Override
	public Class<?> getTestClass() {
		return ShipBooksJMSListenerCIT.class;
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
		createShipmentRequestMessage();
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
		clearStructures();
		clearState();
		super.tearDown();
	}
	
	protected void clearStructures() throws Exception {
		shipBooksClient.reset();
		shipBooksClient = null;
		shipmentScheduledHandler.reset();
		shipmentScheduledHandler = null;
		shipmentCompleteHandler.reset();
		shipmentCompleteHandler = null;
		shipmentFailedHandler.reset();
		shipmentFailedHandler = null;
		super.clearStructures();
	}

	protected void clearState() throws Exception {
		shipmentRequestMessage = null;
		shipmentScheduledMessage = null;
		shipmentScheduledEvent = null;
		shipmentConfirmedEvent = null;
		shipmentCompleteEvent = null;
		shipmentScheduledReceived = false;
		shipmentCompleteMessage = null;
		shipmentCompleteReceived = false;
		shipmentFailedMessage = null;
		shipmentFailedReceived = false;
		super.clearState();
	}
	
//	@Deployment(name = "txManagerEAR", order = 1)
//	@TargetsContainer("hornetQ01_local")
//	public static EnterpriseArchive createTXManagerEAR() {
//		TXManagerTestEARBuilder builder = new TXManagerTestEARBuilder();
//		return builder.createEAR();
//	}
	
	@TargetsContainer("hornetQ01_local")
	@Deployment(name = "shipperEAR", order = 3)
	public static EnterpriseArchive createShipperEAR() {
		ShipperTestEARBuilder builder = new ShipperTestEARBuilder();
		builder.setIncludeWar(true);
		return builder.createEAR();
	}
	
	public void createShipmentRequestMessage() {
		createShipmentRequestMessage(false, false);
	}
	
	public void createShipmentRequestCancelMessage() {
		createShipmentRequestMessage(true, false);
	}
	
	public void createShipmentRequestUndoMessage() {
		createShipmentRequestMessage(false, true);
	}
	
	public void createShipmentRequestMessage(boolean cancel, boolean undo) {
		shipmentRequestMessage = Bookshop2Fixture.create_ShipmentRequestMessage(cancel, undo);
		shipmentRequestMessage.addToReplyToDestinations("ShipmentScheduled", getLocalShipmentScheduledDestination());
		shipmentRequestMessage.addToReplyToDestinations("ShipmentComplete", getLocalShipmentCompleteDestination());
		shipmentRequestMessage.addToReplyToDestinations("ShipmentFailed", getLocalShipmentFailedDestination());
		initializeMessage(shipmentRequestMessage);
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
	//@Ignore
	@InSequence(value = 1)
	public void testShipBooks_ShipmentScheduled() throws Exception {
		String testName = "testShipBooks_ShipmentScheduled";
		log.info(testName+": started");
		registerNotificationListeners();
		createShipmentScheduledEvent();
		expectedEvent = "bookshop2.shipper.ShipmentScheduled_Process_Complete";
		expectedMessage = "bookshop2.shipper.ShipmentScheduled";
		//execution started
		runTest_ShipBooks();
		//execution finished
		Assert.isTrue(shipmentScheduledReceived);
		Assert.isFalse(shipmentCompleteReceived);
		Assert.isFalse(shipmentFailedReceived);
		Assert.isFalse(isFiredRequestDone("ShipBooks"));
		Assert.isTrue(isFiredResponseSent("ShipmentScheduled"));
		Assert.isFalse(isFiredResponseSent("ShipmentComplete"));
		Assert.isFalse(isFiredResponseSent("ShipmentFailed"));
		Assert.isTrue(isFiredProcessComplete("ShipmentScheduled"));
		Assert.isFalse(isFiredProcessComplete("ShipmentConfirmed"));
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 2)
	public void test_ShipBooks_ShipmentScheduled_Cancel() throws Exception {
		String testName = "test_ShipBooks_ShipmentScheduled_Cancel";
		log.info(testName+": started");
		registerNotificationListeners();
		expectedError = "ShipmentScheduled execution cancelled";
		expectedEvent = "bookshop2.shipper.ShipmentScheduled_Process_Aborted";
		Thread thread = startTest_ShipBooks();
		//pause briefly 
		Thread.sleep(2000);
		sendRequest_ShipBooks_Cancel();
		thread.join();
		//execution started
		//runTest_shipBooks();
		//execution finished
		Assert.isFalse(shipmentScheduledReceived);
		Assert.isFalse(shipmentCompleteReceived);
		Assert.isFalse(shipmentFailedReceived);
		Assert.isTrue(isFiredRequestCancelled("ShipBooks"));
		Assert.isTrue(isFiredRequestRolledBack("ShipBooks"));
		Assert.isFalse(isFiredResponseSent("ShipmentComplete"));
		Assert.isFalse(isFiredResponseSent("ShipmentFailed"));
		Assert.isTrue(isFiredProcessAborted("ShipmentScheduled"));
		Assert.isTrue(isFiredProcessAborted("ShipmentConfirmed"));
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 3)
	@BytemanRule(name = "rule3",
		targetClass = "ShipmentScheduledExecutor",
		targetMethod = "register",
		targetLocation = "AT ENTRY",
		action = "$0.timeout = 0")
	public void test_ShipBooks_ShipmentScheduled_Timeout() throws Exception {
		String testName = "test_ShipBooks_ShipmentScheduled_Timeout";
		log.info(testName+": started");
		setupByteman(testName);
		registerNotificationListeners();
		expectedEvent = "bookshop2.shipper.ShipBooks_Request_Done";
		expectedMessage = "bookshop2.shipper.ShipmentFailed";
		expectedError = "ShipmentScheduled timed-out";
		shipmentFailedReason = "ShipmentScheduled timed-out";
		//execution started
		runTest_ShipBooks();
		//execution finished
		Assert.isFalse(shipmentScheduledReceived);
		Assert.isFalse(shipmentCompleteReceived);
		Assert.isTrue(shipmentFailedReceived);
		Assert.isTrue(isFiredProcessAborted("ShipmentScheduled"));
		Assert.isFalse(isFiredResponseSent("ShipmentScheduled"));
		Assert.isFalse(isFiredResponseSent("ShipmentComplete"));
		Assert.isTrue(isFiredResponseSent("ShipmentFailed"));
		Assert.isTrue(isFiredRequestDone("ShipBooks"));
		tearDownByteman(testName);
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 4)
	@BytemanRule(name = "rule4",
		targetClass = "ShipperProcess",
		targetMethod = "handle_ShipmentScheduled_event",
		targetLocation = "AT ENTRY",
		action = "throw new java.lang.RuntimeException(\"exception message\")")
	public void test_ShipBooks_ShipmentScheduled_Exception() throws Exception {
		String testName = "test_ShipBooks_ShipmentScheduled_Exception";
		log.info(testName+": started");
		setupByteman(testName);
		registerNotificationListeners();
		createShipmentScheduledEvent();
		expectedEvent = "bookshop2.shipper.ShipBooks_Request_Done";
		expectedMessage = "bookshop2.shipper.ShipmentFailed";
		shipmentFailedReason = "exception message";
		//execution started
		runTest_ShipBooks();
		//execution finished
		Assert.isFalse(shipmentScheduledReceived);
		Assert.isFalse(shipmentCompleteReceived);
		Assert.isTrue(shipmentFailedReceived);
		Assert.isTrue(isFiredRequestDone("ShipBooks"));
		Assert.isTrue(isFiredProcessAborted("ShipmentScheduled"));
		Assert.isFalse(isFiredResponseSent("ShipmentScheduled"));
		Assert.isFalse(isFiredResponseSent("ShipmentComplete"));
		Assert.isTrue(isFiredResponseSent("ShipmentFailed"));
		tearDownByteman(testName);
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 5)
	public void testShipBooks_ShipmentComplete() throws Exception {
		String testName = "testShipBooks_ShipmentComplete";
		log.info(testName+": started");
		registerNotificationListeners();
		createShipmentConfirmedEvent();
		expectedEvent = "bookshop2.shipper.ShipmentConfirmed_Process_Complete";
		expectedMessage = "bookshop2.shipper.ShipmentComplete";
		//execution started
		runTest_ShipBooks();
		//execution finished
		Assert.isFalse(shipmentScheduledReceived);
		Assert.isTrue(shipmentCompleteReceived);
		Assert.isFalse(shipmentFailedReceived);
		Assert.isTrue(isFiredRequestDone("ShipBooks"));
		Assert.isFalse(isFiredResponseSent("ShipmentScheduled"));
		Assert.isTrue(isFiredResponseSent("ShipmentComplete"));
		Assert.isFalse(isFiredResponseSent("ShipmentFailed"));
		Assert.isFalse(isFiredProcessComplete("ShipmentScheduled"));
		Assert.isTrue(isFiredProcessComplete("ShipmentConfirmed"));
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 6)
	public void test_ShipBooks_ShipmentConfirmed_Cancel() throws Exception {
		String testName = "test_ShipBooks_ShipmentConfirmed_Cancel";
		log.info(testName+": started");
		registerNotificationListeners();
		expectedError = "ShipmentConfirmed execution cancelled";
		expectedEvent = "bookshop2.shipper.ShipmentConfirmed_Process_Aborted";
		Thread thread = startTest_ShipBooks();
		//pause briefly 
		Thread.sleep(2000);
		sendRequest_ShipBooks_Cancel();
		thread.join();
		//execution started
		//runTest_shipBooks();
		//execution finished
		Assert.isFalse(shipmentScheduledReceived);
		Assert.isFalse(shipmentCompleteReceived);
		Assert.isFalse(shipmentFailedReceived);
		Assert.isTrue(isFiredRequestCancelled("ShipBooks"));
		Assert.isTrue(isFiredRequestRolledBack("ShipBooks"));
		Assert.isFalse(isFiredResponseSent("ShipmentScheduled"));
		Assert.isFalse(isFiredResponseSent("ShipmentComplete"));
		Assert.isFalse(isFiredResponseSent("ShipmentFailed"));
		Assert.isTrue(isFiredProcessAborted("ShipmentConfirmed"));
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 7)
	@BytemanRule(name = "rule7",
		targetClass = "ShipmentConfirmedExecutor",
		targetMethod = "register",
		targetLocation = "AT ENTRY",
		action = "$0.timeout = 0")
	public void test_ShipBooks_ShipmentConfirmed_Timeout() throws Exception {
		String testName = "test_ShipBooks_ShipmentConfirmed_Timeout";
		log.info(testName+": started");
		setupByteman(testName);
		registerNotificationListeners();
		expectedError = "ShipmentConfirmed timed-out";
		shipmentFailedReason = "ShipmentConfirmed timed-out";
		//execution started
		runTest_ShipBooks();
		//execution finished
		Assert.isFalse(shipmentScheduledReceived);
		Assert.isFalse(shipmentCompleteReceived);
		Assert.isTrue(shipmentFailedReceived);
		Assert.isTrue(isFiredRequestDone("ShipBooks"));
		Assert.isFalse(isFiredResponseSent("ShipmentScheduled"));
		Assert.isFalse(isFiredResponseSent("ShipmentComplete"));
		Assert.isTrue(isFiredResponseSent("ShipmentFailed"));
		Assert.isTrue(isFiredProcessAborted("ShipmentConfirmed"));
		tearDownByteman(testName);
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 8)
	@BytemanRule(name = "rule8",
		targetClass = "ShipperProcess",
		targetMethod = "handle_ShipmentConfirmed_event",
		targetLocation = "AT ENTRY",
		action = "throw new java.lang.RuntimeException(\"exception message\")")
	public void test_ShipBooks_ShipmentConfirmed_Exception() throws Exception {
		String testName = "test_ShipBooks_ShipmentConfirmed_Exception";
		log.info(testName+": started");
		setupByteman(testName);
		registerNotificationListeners();
		createShipmentConfirmedEvent();
		expectedMessage = "bookshop2.shipper.ShipmentFailed";
		shipmentFailedReason = "exception message";
		//execution started
		runTest_ShipBooks();
		//execution finished
		Assert.isFalse(shipmentScheduledReceived);
		Assert.isFalse(shipmentCompleteReceived);
		Assert.isTrue(shipmentFailedReceived);
		Assert.isTrue(isFiredRequestDone("ShipBooks"));
		Assert.isFalse(isFiredResponseSent("ShipmentScheduled"));
		Assert.isFalse(isFiredResponseSent("ShipmentComplete"));
		Assert.isTrue(isFiredResponseSent("ShipmentFailed"));
		Assert.isFalse(isFiredProcessAborted("ShipmentScheduled"));
		Assert.isTrue(isFiredProcessAborted("ShipmentConfirmed"));
		tearDownByteman(testName);
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 11)
	public void test_ShipBooks_ShipmentComplete() throws Exception {
		String testName = "test_ShipBooks_ShipmentComplete";
		log.info(testName+": started");
		registerNotificationListeners();
		createShipmentScheduledEvent();
		createShipmentConfirmedEvent();
		//expectedEvent = "bookshop2.shipper.ShipBooks_Request_Done";
		expectedMessage = "bookshop2.shipper.ShipmentComplete";
		//execution started
		runTest_ShipBooks();
		//execution finished
		Assert.isTrue(shipmentScheduledReceived);
		Assert.isTrue(shipmentCompleteReceived);
		Assert.isFalse(shipmentFailedReceived);
		Assert.isTrue(isFiredRequestDone("ShipBooks"));
		Assert.isTrue(isFiredResponseSent("ShipmentScheduled"));
		Assert.isTrue(isFiredResponseSent("ShipmentComplete"));
		Assert.isFalse(isFiredResponseSent("ShipmentFailed"));
		Assert.isTrue(isFiredProcessComplete("ShipmentScheduled"));
		Assert.isTrue(isFiredProcessComplete("ShipmentConfirmed"));
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 14)
	public void test_ShipBooks_Cancel() throws Exception {
		String testName = "test_ShipBooks_Cancel";
		log.info(testName+": started");
		registerNotificationListeners();
		
		//expectedError = "ShipmentScheduled execution cancelled";
		expectedEvent = "bookshop2.shipper.ShipBooks_Cancel_Request_Done";

		//execution started
		Thread thread = startTest_ShipBooks();
		//pause briefly 
		Thread.sleep(2000);
		clearState();
		sendRequest_ShipBooks_Cancel();
		thread.join();
		//runTest_shipBooks();
		//execution finished
		
		//validation
		Assert.isFalse(shipmentScheduledReceived);
		Assert.isFalse(shipmentCompleteReceived);
		Assert.isFalse(shipmentFailedReceived);
		Assert.isTrue(isFiredRequestCancelled("ShipBooks"));
		Assert.isTrue(isFiredRequestRolledBack("ShipBooks"));
		Assert.isFalse(isFiredResponseSent("ShipmentComplete"));
		Assert.isFalse(isFiredResponseSent("ShipmentFailed"));
		Assert.isTrue(isFiredProcessAborted("ShipmentScheduled"));
		Assert.isTrue(isFiredProcessAborted("ShipmentConfirmed"));
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 15)
	public void test_ShipBooks_Undo() throws Exception {
		String testName = "test_ShipBooks_Undo";
		log.info(testName+": started");
		registerNotificationListeners();

		//expectedError = "ShipmentScheduled execution cancelled";
		//execution started
		createShipmentScheduledEvent();
		createShipmentConfirmedEvent();
		runTest_ShipBooks();
		//execution finished
		
		Assert.isTrue(shipmentScheduledReceived);
		Assert.isTrue(shipmentCompleteReceived);
		Assert.isFalse(shipmentFailedReceived);
		Assert.isFalse(isFiredRequestCancelled("ShipBooks"));
		Assert.isFalse(isFiredRequestRolledBack("ShipBooks"));
		Assert.isTrue(isFiredResponseSent("ShipmentScheduled"));
		Assert.isTrue(isFiredResponseSent("ShipmentComplete"));
		Assert.isTrue(isFiredProcessComplete("ShipmentScheduled"));
		Assert.isTrue(isFiredProcessComplete("ShipmentConfirmed"));

		//execution started
		clearState();
		expectedEvent = "bookshop2.shipper.ShipBooks_Undo_Request_Done";
		registerForResult();
		sendRequest_ShipBooks_Undo();
		Object result = waitForCompletion();
		validateResult(result);
		//execution finished

		//validation
		Assert.isFalse(shipmentScheduledReceived);
		Assert.isFalse(shipmentCompleteReceived);
		Assert.isFalse(shipmentFailedReceived);
		Assert.isTrue(isFiredRequestDone("ShipBooks"));
		Assert.isFalse(isFiredRequestCancelled("ShipBooks"));
		Assert.isTrue(isFiredRequestRolledBack("ShipBooks"));
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	@Ignore
	@InSequence(value = 12)
	@BytemanRule(name = "rule6",
		targetClass = "ShipperProcess",
		targetMethod = "waitForShipmentConfirmation",
		targetLocation = "AT EXIT",
		action = "throw new java.lang.RuntimeException(\"exception message\")")
	public void test_ShipBooks_ShipmentFailed() throws Exception {
		String testName = "test_ShipBooks_ShipmentFailed";
		log.info(testName+": started");
		registerNotificationListeners();
		setupByteman(testName);
		shipmentFailedReason = "exception message";
		//execution started
		runTest_ShipBooks();
		//execution finished
		Assert.isTrue(shipmentScheduledReceived);
		Assert.isFalse(shipmentCompleteReceived);
		Assert.isTrue(shipmentFailedReceived);
		Assert.isTrue(isFiredRequestDone("ShipBooks"));
		Assert.isTrue(isFiredResponseSent("ShipmentScheduled"));
		Assert.isFalse(isFiredResponseSent("ShipmentComplete"));
		Assert.isTrue(isFiredResponseSent("ShipmentFailed"));
		tearDownByteman(testName);
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public Thread startTest_ShipBooks() throws Exception {
		Thread thread = new Thread() {
			public void run() {
				try {
					runTest_ShipBooks();
				} catch (Exception e) {
					errorMessage = e.getMessage();
				}
			}
		};
		thread.start();
		return thread;
	}
	
	public void runTest_ShipBooks() throws Exception {
		// prepare the environment
		removeMessagesFromDestinations();
		
		// prepare context
		//beginTransaction();
		
		// prepare mocks
		registerForResult();
		
		// prepare endpoints
		shipmentScheduledHandler = startShipmentScheduledHandler();
		shipmentCompleteHandler = startShipmentCompleteHandler();
		shipmentFailedHandler = startShipmentFailedHandler();
		shipBooksClient = startShipBooksClient();
		sendRequest_ShipBooks();
		
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

	protected void runTest_ShipBooks_Undo() throws Exception {
		createShipmentScheduledEvent();
		createShipmentConfirmedEvent();
		runTest_ShipBooks();
		sendRequest_ShipBooks_Undo();
		Object result = waitForCompletion();
		validateResult(result);
	}

	protected void sendRequest_ShipBooks() throws Exception {
		shipBooksClient.send(shipmentRequestMessage, correlationId, null);
	}

	protected void sendRequest_ShipBooks_Cancel() throws Exception {
		createShipmentRequestCancelMessage();
		sendRequest_ShipBooks();
	}

	protected void sendRequest_ShipBooks_Undo() throws Exception {
		createShipmentRequestUndoMessage();
		sendRequest_ShipBooks();
	}

	protected boolean eventsExist() {
		return shipmentScheduledEvent != null || 
			shipmentConfirmedEvent != null || 
			shipmentCompleteEvent != null;
	}

	protected void fireEvents() throws Exception {
		Thread.sleep(2000);
		if (shipmentScheduledEvent != null)
			fireEvent(shipmentScheduledEvent);
		if (shipmentConfirmedEvent != null)
			fireEvent(shipmentConfirmedEvent);
		if (shipmentCompleteEvent != null)
			fireEvent(shipmentCompleteEvent);
	}

	protected void fireEvent(AbstractEvent event) throws Exception {
		eventMulticasterProxy.dispatchEvent(event);
	}
	
	protected void registerNotificationListeners() throws Exception {
		addRequestNotificationListeners("ShipBooks");
		addResponseNotificationListeners("ShipmentScheduled");
		addResponseNotificationListeners("ShipmentComplete");
		addResponseNotificationListeners("ShipmentFailed");
		addProcessNotificationListeners("ShipmentScheduled");
		addProcessNotificationListeners("ShipmentConfirmed");
	}
	
	protected void removeMessagesFromDestinations() throws Exception {
		removeMessagesFromQueue(getTargetArchive(), getTargetDestination());
		removeMessagesFromQueue(getTargetArchive(), getLocalShipmentCompleteDestination());
		removeMessagesFromQueue(getTargetArchive(), getLocalShipmentScheduledDestination());
		removeMessagesFromQueue(getTargetArchive(), getLocalShipmentFailedDestination());
	}
	
	protected JmsClient startShipBooksClient() throws Exception {
		JmsClient client = new ShipBooksProxyForJMS(ShipBooks.ID);
		configureClient(client, getTargetDestination());
		client.setCreateTemporaryQueue(true);
		client.initialize();
		return client;
	}
	
	protected JmsClient startShipmentScheduledHandler() throws Exception {
		JmsClient client = createClient(getJNDINameForQueue(getLocalShipmentScheduledDestination()));
		client.setMessageListener(createShipmentScheduledResponseListener());
		client.initialize();
		return client;
	}
	
	protected JmsClient startShipmentCompleteHandler() throws Exception {
		JmsClient client = createClient(getJNDINameForQueue(getLocalShipmentCompleteDestination()));
		client.setMessageListener(createShipmentCompleteResponseListener());
		client.initialize();
		return client;
	}
	
	protected JmsClient startShipmentFailedHandler() throws Exception {
		JmsClient client = createClient(getJNDINameForQueue(getLocalShipmentFailedDestination()));
		client.setMessageListener(createShipmentFailedResponseListener());
		client.initialize();
		return client;
	}
	
	protected MessageListener createShipmentScheduledResponseListener() {
		return new MessageListener() {
			public void onMessage(Message message) {
				try {
					log.info("#### [test]: ShipmentScheduled received");
					Object object = getObjectFromMessage(message);
					Assert.notNull(object, "Payload not found");
					Assert.isTrue(object instanceof ShipmentScheduledMessage, "Payload type not correct");
					shipmentScheduledMessage = (ShipmentScheduledMessage) object;
					validate(shipmentScheduledMessage);
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
	
	protected MessageListener createShipmentCompleteResponseListener() {
		return new MessageListener() {
			public void onMessage(Message message) {
				try {
					log.info("#### [test]: ShipmentComplete received");
					Object object = getObjectFromMessage(message);
					Assert.notNull(object, "Payload not found");
					Assert.isTrue(object instanceof ShipmentCompleteMessage, "Payload type not correct");
					shipmentCompleteMessage = (ShipmentCompleteMessage) object;
					validate(shipmentCompleteMessage);
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
	
	protected MessageListener createShipmentFailedResponseListener() {
		return new MessageListener() {
			public void onMessage(Message message) {
				try {
					log.info("#### [test]: ShipmentFailed received");
					Object object = getObjectFromMessage(message);
					Assert.notNull(object, "Payload not found");
					Assert.isTrue(object instanceof ShipmentFailedMessage, "Payload type not correct");
					shipmentFailedMessage = (ShipmentFailedMessage) object;
					validate(shipmentFailedMessage);
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
	
	protected void validateResult(Object result) {
		if (result instanceof ShipmentScheduledMessage) {
			validate((ShipmentScheduledMessage) result);
		} else if (result instanceof ShipmentCompleteMessage) {
			validate((ShipmentCompleteMessage) result);
		} else if (result instanceof ShipmentFailedMessage) {
			validate((ShipmentFailedMessage) result);
		} else if (result instanceof String) {
			String resultString = result.toString();
			if ((expectedError != null && !expectedError.equalsIgnoreCase(resultString)) &&
				(expectedEvent != null && !expectedEvent.equalsIgnoreCase(resultString)))
					errorMessage = "Unexpected message: "+result;
		} else {
			errorMessage = "Unrecognized result: "+result;
		}
	}
	
	protected void validate(ShipmentCompleteMessage shipmentCompleteMessage) {
		Assert.notNull(shipmentCompleteMessage, "Message must be specified");
		Shipment expectedShipment = shipmentRequestMessage.getShipment();
		//TODO Invoice expectedInvoice = shipmentRequestMessage.getInvoice();
		Shipment actualShipment = shipmentCompleteMessage.getShipment();
		Invoice actualInvoice = shipmentCompleteMessage.getInvoice();
		Bookshop2Fixture.assertShipmentCorrect(actualShipment);
		Bookshop2Fixture.assertInvoiceCorrect(actualInvoice);
		Bookshop2Fixture.assertSameShipment(expectedShipment, actualShipment);
		//TODO Bookshop2Fixture.assertInvoiceEquals(expectedInvoice, actualInvoice);
	}
	
	protected void validate(ShipmentScheduledMessage shipmentScheduledMessage) {
		Assert.notNull(shipmentScheduledMessage, "Message must be specified");
		Shipment expectedShipment = shipmentRequestMessage.getShipment();
		Shipment actualShipment = shipmentScheduledMessage.getShipment();
		Bookshop2Fixture.assertShipmentCorrect(actualShipment);
		Bookshop2Fixture.assertSameShipment(expectedShipment, actualShipment);
	}

	protected void validate(ShipmentFailedMessage shipmentFailedMessage) {
		Assert.notNull(shipmentFailedMessage, "Message must be specified");
		Bookshop2Fixture.assertShipmentCorrect(shipmentFailedMessage.getShipment());
		Assert.equals(shipmentFailedMessage.getReason(), shipmentFailedReason);
	}
	
}
