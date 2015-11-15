package bookshop2.supplier;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.aries.Assert;
import org.aries.common.AbstractEvent;
import org.aries.event.multicaster.EventMulticasterProxy;
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

import bookshop2.Invoice;
import bookshop2.Shipment;
import bookshop2.ShipmentCompleteEvent;
import bookshop2.ShipmentCompleteMessage;
import bookshop2.ShipmentConfirmedEvent;
import bookshop2.ShipmentFailedMessage;
import bookshop2.ShipmentRequestMessage;
import bookshop2.ShipmentScheduledEvent;
import bookshop2.ShipmentScheduledMessage;
import bookshop2.shipper.ShipperTestEARBuilder;
import bookshop2.shipper.event.ShipperEventReceiverMBean;
import bookshop2.shipper.outgoing.shipmentComplete.ShipmentCompleteReply;
import bookshop2.shipper.outgoing.shipmentFailed.ShipmentFailedReply;
import bookshop2.shipper.outgoing.shipmentScheduled.ShipmentScheduledReply;
import bookshop2.supplier.client.shipBooks.ShipBooks;
import bookshop2.supplier.client.shipBooks.ShipBooksProxyForJMS;
import bookshop2.util.Bookshop2Fixture;


@RunAsClient
@RunWith(Arquillian.class)
public class ShipBooksJMSListenerCIT extends AbstractJMSListenerArquillionTest {
	
	private TransactionTestControl transactionTestControl;
	
	private EventMulticasterProxy eventMulticasterProxy;
	
	private JmsClient shipBooksClient;
	
	private JmsClient shipmentScheduledHandler;
	
	private JmsClient shipmentCompleteHandler;
	
	private JmsClient shipmentFailedHandler;
	
	private ShipmentRequestMessage shipmentRequestMessage;

	private ShipmentScheduledMessage shipmentScheduledMessage;

	private ShipmentCompleteMessage shipmentCompleteMessage;

	private ShipmentFailedMessage shipmentFailedMessage;

	private ShipmentScheduledEvent shipmentScheduledEvent;

	private ShipmentConfirmedEvent shipmentConfirmedEvent;

	private ShipmentCompleteEvent shipmentCompleteEvent;
	
	protected boolean shipmentScheduledReceived;

	protected boolean shipmentCompleteReceived;

	protected boolean shipmentFailedReceived;

	private Object shipmentFailedReason;
	
	
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
		return getJNDINameForQueue("inventory_bookshop2_supplier_ship_books_queue");
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
		shipBooksClient.reset();
		shipBooksClient = null;
		shipmentScheduledHandler.reset();
		shipmentScheduledHandler = null;
		shipmentCompleteHandler.reset();
		shipmentCompleteHandler = null;
		shipmentFailedHandler.reset();
		shipmentFailedHandler = null;
		shipmentRequestMessage = null;
		shipmentScheduledMessage = null;
		shipmentCompleteMessage = null;
		shipmentFailedMessage = null;
		shipmentScheduledEvent = null;
		shipmentConfirmedEvent = null;
		shipmentCompleteEvent = null;
		shipmentScheduledReceived = false;
		shipmentCompleteReceived = false;
		shipmentFailedReceived = false;
		super.tearDown();
	}
	
//	@Deployment(name = "txManagerEAR", order = 1)
//	@TargetsContainer("hornetQ01_local")
//	public static EnterpriseArchive createTXManagerEAR() {
//		TXManagerTestEARBuilder builder = new TXManagerTestEARBuilder();
//		return builder.createEAR();
//	}
	
	@TargetsContainer("hornetQ01_local")
	@Deployment(name = "supplierEAR", order = 2)
	public static EnterpriseArchive createSupplierEAR() {
		SupplierTestEARBuilder builder = new SupplierTestEARBuilder();
		builder.setIncludeWar(true);
		return builder.createEAR();
	}
	
	@TargetsContainer("hornetQ01_local")
	@Deployment(name = "shipperEAR", order = 3)
	public static EnterpriseArchive createShipperEAR() {
		ShipperTestEARBuilder builder = new ShipperTestEARBuilder();
		builder.setIncludeWar(true);
		builder.setDeployJms(false);
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
	@InSequence(value = 1)
	public void testShipBooks_ShipmentScheduled() throws Exception {
		String testName = "testShipBooks_ShipmentScheduled";
		log.info(testName+": started");
		registerNotificationListeners();
		createShipmentScheduledEvent();
		expectedMessage = "bookshop2.supplier.ShipmentScheduled";
		expectedEvent = "bookshop2.supplier.ShipmentScheduled_Response_Sent";
		//execution started
		runTest_shipBooks();
		//execution finished
		Assert.isTrue(shipmentScheduledReceived);
		Assert.isFalse(shipmentCompleteReceived);
		Assert.isFalse(shipmentFailedReceived);
		Assert.isFalse(isFiredRequestDone("ShipBooks"));
		Assert.isTrue(isFiredResponseSent("ShipmentScheduled"));
		Assert.isFalse(isFiredResponseSent("ShipmentComplete"));
		Assert.isFalse(isFiredResponseSent("ShipmentFailed"));
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	@InSequence(value = 2)
	public void testShipBooks_ShipmentComplete() throws Exception {
		String testName = "testShipBooks_ShipmentComplete";
		log.info(testName+": started");
		registerNotificationListeners();
		createShipmentConfirmedEvent();
		expectedMessage = "bookshop2.supplier.ShipmentComplete";
		expectedEvent = "bookshop2.supplier.ShipBooks_Request_Done";
		//execution started
		runTest_shipBooks();
		//execution finished
		Assert.isFalse(shipmentScheduledReceived);
		Assert.isTrue(shipmentCompleteReceived);
		Assert.isFalse(shipmentFailedReceived);
		Assert.isTrue(isFiredRequestDone("ShipBooks"));
		Assert.isFalse(isFiredResponseSent("ShipmentScheduled"));
		Assert.isTrue(isFiredResponseSent("ShipmentComplete"));
		Assert.isFalse(isFiredResponseSent("ShipmentFailed"));
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	@InSequence(value = 3)
	public void testShipBooks_Success() throws Exception {
		String testName = "testShipBooks_Success";
		log.info(testName+": started");
		registerNotificationListeners();
		createShipmentScheduledEvent();
		createShipmentConfirmedEvent();
		expectedMessage = "bookshop2.supplier.ShipmentComplete";
		expectedEvent = "bookshop2.supplier.ShipBooks_Request_Done";
		//execution started
		runTest_shipBooks();
		//execution finished
		Assert.isTrue(shipmentScheduledReceived);
		Assert.isTrue(shipmentCompleteReceived);
		Assert.isFalse(shipmentFailedReceived);
		Assert.isTrue(isFiredRequestDone("ShipBooks"));
		Assert.isTrue(isFiredResponseSent("ShipmentScheduled"));
		Assert.isTrue(isFiredResponseSent("ShipmentComplete"));
		Assert.isFalse(isFiredResponseSent("ShipmentFailed"));
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runTest_shipBooks() throws Exception {
		// prepare the environment
		removeMessagesFromDestinations();
		
		// prepare context
		//beginTransaction();
		
		// prepare mocks
		registerForResult();
		
		// prepare endpoints
		shipBooksClient = startShipBooksClient();
		shipmentScheduledHandler = startShipmentScheduledHandler();
		shipmentCompleteHandler = startShipmentCompleteHandler();
		shipmentFailedHandler = startShipmentFailedHandler();
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
	
//	protected MessageListener createResponseListener() {
//		return new MessageListener() {
//			public void onMessage(Message message) {
//				try {
//					Object object = getObjectFromMessage(message);
//					Assert.notNull(object, "Payload not found");
//					validateResult(object);
//				} catch (Throwable e) {
//					errorMessage = ExceptionUtils.getRootCauseMessage(e);
//				}
//			}
//		};
//	}
	
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
				} finally {
					if (expectedMessage != null && expectedMessage.equalsIgnoreCase("bookshop2.supplier.ShipmentScheduled"))
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
				} finally {
					if (expectedMessage != null && expectedMessage.equalsIgnoreCase("bookshop2.supplier.ShipmentComplete"))
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
				} finally {
					if (expectedMessage != null && expectedMessage.equalsIgnoreCase("bookshop2.supplier.ShipmentFailed"))
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
	
	protected void validate(ShipmentScheduledMessage shipmentScheduledMessage) {
		Assert.notNull(shipmentScheduledMessage, "Message must be specified");
		Shipment expectedShipment = shipmentRequestMessage.getShipment();
		Shipment actualShipment = shipmentScheduledMessage.getShipment();
		Bookshop2Fixture.assertShipmentCorrect(actualShipment);
		Bookshop2Fixture.assertSameShipment(expectedShipment, actualShipment);
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
	
	protected void validate(ShipmentFailedMessage shipmentFailedMessage) {
		Assert.notNull(shipmentFailedMessage, "Message must be specified");
		Bookshop2Fixture.assertShipmentCorrect(shipmentFailedMessage.getShipment());
		Assert.equals(shipmentFailedMessage.getReason(), shipmentFailedReason);
	}
	
}
