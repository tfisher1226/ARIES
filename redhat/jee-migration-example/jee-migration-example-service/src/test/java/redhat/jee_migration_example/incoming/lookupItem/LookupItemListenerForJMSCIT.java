package redhat.jee_migration_example.incoming.lookupItem;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.aries.Assert;
import org.aries.jms.client.JmsClient;
import org.aries.jms.util.MessageUtil;
import org.aries.message.util.MessageConstants;
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

import redhat.jee_migration_example.Event;
import redhat.jee_migration_example.EventLoggerTestEARBuilder;
import redhat.jee_migration_example.Item;
import redhat.jee_migration_example.client.lookupItem.LookupItem;
import redhat.jee_migration_example.client.lookupItem.LookupItemProxyForJMS;


@RunAsClient
@RunWith(Arquillian.class)
public class LookupItemListenerForJMSCIT extends AbstractJMSListenerArquillionTest {
	
	private JmsClient lookupItemClient;
	
	private JmsClient remoteJeeMigrationExampleLogEventHandler;
	
	private JmsClient localJeeMigrationExampleItemHandler;
	
	private Long longObject;
	
	private Event jeeMigrationExampleEvent;
	
	private TransactionTestControl transactionTestControl;
	
	private boolean eventReceived;

	private Event event;
	
	
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
		return LookupItemListenerForJMSCIT.class;
	}
	
	@Override
	public String getServerName() {
		return "hornetQ01_local";
	}
	
	@Override
	public String getDomainId() {
		return "redhat.jee-migration-example";
	}
	
	@Override
	public String getServiceId() {
		return "redhat.jee-migration-example.lookupItem";
	}
	
	@Override
	public String getTargetArchive() {
		return EventLoggerTestEARBuilder.NAME;
	}
	
	@Override
	public String getTargetDestination() {
		return get_target_JeeMigrationExample_LookupItem_destination();
	}
	
	public String get_target_JeeMigrationExample_LookupItem_destination() {
		return getJNDINameForQueue("public_redhat_lookup_item_queue");
	}
	
	public String get_remote_JeeMigrationExample_LogEvent_destination() {
		return getJNDINameForQueue("public_redhat_log_event_queue");
	}
	
	public String get_local_JeeMigrationExample_Item_destination() {
		return "test_message_domain_test_destination_queue_a";
	}
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		startServer();
		createTransactionControl();
		removeMessagesFromDestinations();
		//TODO XMLSchemaFixture.reset();
	}
	
	protected void createTransactionControl() throws Exception {
		transactionTestControl = new TransactionTestControl();
		transactionTestControl.setupTransactionManager();
	}
	
	public Long createLong(long value) {
		return new Long(value);
	}
	
	protected void registerNotificationListeners() throws Exception {
		addRequestNotificationListeners("JeeMigrationExample_LookupItem");
		addResponseNotificationListeners("JeeMigrationExample_Item");
		addRequestNotificationListeners("JeeMigrationExample_LogEvent");
		super.registerNotificationListeners();
	}
	
	@After
	public void tearDown() throws Exception {
		clearStructures();
		clearState();
		super.tearDown();
	}
	
	protected void clearStructures() throws Exception {
		lookupItemClient.reset();
		lookupItemClient = null;
		
		//remote handler(s) for requests to remote (mocked) service(s)
		remoteJeeMigrationExampleLogEventHandler.reset();
		remoteJeeMigrationExampleLogEventHandler = null;
		
		//local handlers for responses from target service
		localJeeMigrationExampleItemHandler.reset();
		localJeeMigrationExampleItemHandler = null;
		super.clearStructures();
	}
	
	protected void clearState() throws Exception {
		eventReceived = false;
		super.clearState();
	}
	
	protected void removeMessagesFromDestinations() throws Exception {
		removeMessagesFromQueue(getTargetArchive(), get_target_JeeMigrationExample_LookupItem_destination());
		removeMessagesFromQueue(getTargetArchive(), get_local_JeeMigrationExample_Item_destination());
		removeMessagesFromQueue(getTargetArchive(), get_remote_JeeMigrationExample_LogEvent_destination());
	}
	
	@TargetsContainer("hornetQ01_local")
	@Deployment(name = "eventLoggerEAR", order = 2)
	public static EnterpriseArchive createEventLoggerEAR() {
		EventLoggerTestEARBuilder builder = new EventLoggerTestEARBuilder();
		builder.setRunningAsClient(true);
		return builder.createEAR();
	}
	
	public void runAction_send_LookupItem() throws Exception {
		runAction_send_LookupItem(new Long(0L));
	}
	
	public void runAction_send_LookupItem(Long id) throws Exception {
		//this.id = id;
		
		// prepare the environment
		removeMessagesFromDestinations();
		
		// prepare context
		//beginTransaction();
		
		// prepare mocks
		registerForResult();
		
		// start handlers for calls to remote service(s)
		remoteJeeMigrationExampleLogEventHandler = start_remote_JeeMigrationExample_LogEvent_handler();
		
		// start local handlers for responses from target service
		localJeeMigrationExampleItemHandler = start_local_JeeMigrationExample_Item_handler();
		
		org.aries.message.Message message = new org.aries.message.Message();
		message.addPart("id", id);
		message.addPart(MessageConstants.PROPERTY_OPERATION_NAME, "lookupItem");
		
		// start fixture execution
		lookupItemClient = start_LookupItem_client();
		lookupItemClient.send(message, correlationId, null);
		
		// wait for completion result
		Object result = waitForCompletion();
		validateResult(result);
		
		// close context
		//commitTransaction();
		
		// validate state
		assertEmptyTargetDestination();
	}
	
	public Thread start_runAction_send_LookupItem() throws Exception {
		Thread thread = new Thread() {
			public void run() {
				try {
					runAction_send_LookupItem();
				} catch (Exception e) {
					errorMessage = e.getMessage();
				}
			}
		};
		thread.start();
		Thread.sleep(4000);
		return thread;
	}
	
	protected JmsClient start_LookupItem_client() throws Exception {
		JmsClient client = new LookupItemProxyForJMS(LookupItem.ID);
		configureClient(client, getTargetDestination());
		client.setCreateTemporaryQueue(true);
		client.initialize();
		return client;
	}
	
	protected JmsClient start_remote_JeeMigrationExample_LogEvent_handler() throws Exception {
		JmsClient client = createClient(get_remote_JeeMigrationExample_LogEvent_destination());
		client.setMessageListener(create_remote_JeeMigrationExample_LogEvent_message_listener());
		client.initialize();
		return client;
	}
	
	protected JmsClient start_local_JeeMigrationExample_Item_handler() throws Exception {
		JmsClient client = createClient(getJNDINameForQueue(get_local_JeeMigrationExample_Item_destination()));
		client.setMessageListener(create_local_JeeMigrationExample_Item_response_listener());
		client.initialize();
		return client;
	}
	
	protected MessageListener create_remote_JeeMigrationExample_LogEvent_message_listener() {
		return new MessageListener() {
			public void onMessage(Message message) {
				try {
					String jmsMessageID = message.getJMSMessageID();
					log.info("#### [test]: LogEvent: received: "+jmsMessageID);
					Object object = MessageUtil.getPart(message, "event");
					Assert.isTrue(object instanceof Event, "Payload type not correct");
					Event event = (Event) object;
					validateMessage(event);

				} catch (Throwable e) {
					errorMessage = ExceptionUtils.getRootCauseMessage(e);
					e.printStackTrace();
				
				} finally {
					if (expectedMessage != null && expectedMessage.equalsIgnoreCase("redhat.jee-migration-example.LogEvent"))
						expectedMessageResult.set(true);
						eventReceived = true;
				}
			}
		};
	}
	
	protected MessageListener create_local_JeeMigrationExample_Item_response_listener() {
		return new MessageListener() {
			public void onMessage(Message message) {
				try {
					String jmsMessageID = message.getJMSMessageID();
					log.info("#### [test]: Item: received: "+jmsMessageID);
					System.out.println("XXX TODO XXX");
					
				} catch (Throwable e) {
					errorMessage = ExceptionUtils.getRootCauseMessage(e);
					e.printStackTrace();
				} finally {
				}
			}
		};
	}
	
	protected void validateResult(Object result) throws Exception {
		if (result instanceof Item) {
			validateResult((Item) result);
			
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
	
	protected void validateMessage(Event event) throws Exception {
		Assert.notNull(event, "Message must be specified");
		if (this.event != null) {
			Assert.equals(this.event.getDate(), event.getDate(), "Date field is unequal");
			Assert.equals(this.event.getMessage(), event.getMessage(), "Message field is unequal");
		}
	}
	
	@Test
	//@Ignore
	@InSequence(value = 1)
	@BytemanRule(name = "rule1", 
			targetClass = "LookupItemHandlerImpl", 
			targetMethod = "lookupItem", 
			targetLocation = "AT ENTRY", 
			action = "$0.timeout = 0")
	public void runTest_LookupItem_timeout() throws Exception {
		String testName = "runTest_LookupItem_timeout";
		log.info(testName+": started");
		
		setupByteman(testName);
		registerNotificationListeners();
		
		// setup expectations
		expectedEvent = "JeeMigrationExample_LookupItem_Incoming_Request_Aborted";
		//TODO expectedMessage = "redhat.jee-migration-example.Long";
		
		try {
			// execute action
			//TODO XMLSchemaFixture.reset();
			runAction_send_LookupItem();

			// validate request initiation state
			Assert.isTrue(isFiredRequestReceived("JeeMigrationExample_LookupItem"));

			// validate request non-completion state
			Assert.isFalse(isFiredRequestHandled("JeeMigrationExample_LookupItem"));
			Assert.isFalse(isFiredRequestDone("JeeMigrationExample_LookupItem"));

			// validate incoming request aborted state
			Assert.isTrue(isFiredIncomingRequestAborted("JeeMigrationExample_LookupItem"));
			
			if (errorMessage != null)
				fail(errorMessage);
			
		} finally {
			tearDownByteman(testName);
		}
	}
	
	@Test
	//@Ignore
	@InSequence(value = 2)
	@BytemanRule(name = "rule2", 
			targetClass = "EventLoggerProcess", 
			targetMethod = "lookupItem", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"exception message\")")
	public void runTest_LookupItem_exception() throws Exception {
		String testName = "runTest_LookupItem_exception";
		log.info(testName+": started");
		
		setupByteman(testName);
		registerNotificationListeners();
		
		// setup expectations
		expectedEvent = "JeeMigrationExample_LookupItem_Incoming_Request_Aborted";
		expectedMessage = "redhat.jee-migration-example.Long";
		exceptionMessage = "exception message";
		
		try {
			// execute action
			//TODO XMLSchemaFixture.reset();
			runAction_send_LookupItem();

			// validate request initiation state
			Assert.isTrue(isFiredRequestReceived("JeeMigrationExample_LookupItem"));

			// validate request non-completion state
			Assert.isFalse(isFiredRequestHandled("JeeMigrationExample_LookupItem"));
			Assert.isFalse(isFiredRequestDone("JeeMigrationExample_LookupItem"));

			// validate incoming request aborted state
			Assert.isTrue(isFiredIncomingRequestAborted("JeeMigrationExample_LookupItem"));
			
			if (errorMessage != null)
				fail(errorMessage);
			
		} finally {
			tearDownByteman(testName);
		}
	}
	
}
