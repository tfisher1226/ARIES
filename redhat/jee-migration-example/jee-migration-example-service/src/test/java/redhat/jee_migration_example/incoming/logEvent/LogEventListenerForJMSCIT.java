package redhat.jee_migration_example.incoming.logEvent;

import org.aries.Assert;
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

import redhat.jee_migration_example.Event;
import redhat.jee_migration_example.EventLoggerTestEARBuilder;
import redhat.jee_migration_example.Item;
import redhat.jee_migration_example.client.logEvent.LogEvent;
import redhat.jee_migration_example.client.logEvent.LogEventProxyForJMS;
import redhat.jee_migration_example.util.JeeMigrationExampleFixture;


@RunAsClient
@RunWith(Arquillian.class)
public class LogEventListenerForJMSCIT extends AbstractJMSListenerArquillionTest {
	
	private JmsClient logEventClient;
	
	private Event event;
	
	private TransactionTestControl transactionTestControl;
	
	
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
		return LogEventListenerForJMSCIT.class;
	}
	
	@Override
	public String getServerName() {
		return "hornetQ01_local";
	}
	
	@Override
	public String getDomainId() {
		return "redhat";
	}
	
	@Override
	public String getServiceId() {
		return "redhat.jee-migration-example.logEvent";
	}
	
	@Override
	public String getTargetArchive() {
		return EventLoggerTestEARBuilder.NAME;
	}
	
	@Override
	public String getTargetDestination() {
		return get_target_JeeMigrationExample_LogEvent_destination();
	}
	
	public String get_target_JeeMigrationExample_LogEvent_destination() {
		return getJNDINameForQueue("public_redhat_log_event_queue");
	}
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		startServer();
		createTransactionControl();
		removeMessagesFromDestinations();
		JeeMigrationExampleFixture.reset();
	}
	
	protected void createTransactionControl() throws Exception {
		transactionTestControl = new TransactionTestControl();
		transactionTestControl.setupTransactionManager();
	}
	
	public Event createEvent() {
		Event event = JeeMigrationExampleFixture.create_Event();
		return event;
	}
	
	protected void registerNotificationListeners() throws Exception {
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
		logEventClient.reset();
		logEventClient = null;
		super.clearStructures();
	}
	
	protected void clearState() throws Exception {
		event = null;
		super.clearState();
	}
	
	protected void removeMessagesFromDestinations() throws Exception {
		removeMessagesFromQueue(getTargetArchive(), get_target_JeeMigrationExample_LogEvent_destination());
	}
	
	@TargetsContainer("hornetQ01_local")
	@Deployment(name = "eventLoggerEAR", order = 2)
	public static EnterpriseArchive createEventLoggerEAR() {
		EventLoggerTestEARBuilder builder = new EventLoggerTestEARBuilder();
		builder.setRunningAsClient(true);
		return builder.createEAR();
	}
	
	public void runAction_send_LogEvent() throws Exception {
		runAction_send_LogEvent(createEvent());
	}
	
	public void runAction_send_LogEvent(Event event) throws Exception {
		this.event = event;
		
		// prepare the environment
		removeMessagesFromDestinations();
		
		// prepare context
		//beginTransaction();
		
		// prepare mocks
		registerForResult();
		
		// start fixture execution
		logEventClient = start_LogEvent_client();
		logEventClient.send(event, correlationId, null);
		
		// wait for completion result
		Object result = waitForCompletion();
		validateResult(result);
		
		// close context
		//commitTransaction();
		
		// validate state
		assertEmptyTargetDestination();
	}
	
	public Thread start_runAction_send_LogEvent() throws Exception {
		Thread thread = new Thread() {
			public void run() {
				try {
					runAction_send_LogEvent();
				} catch (Exception e) {
					errorMessage = e.getMessage();
				}
			}
		};
		thread.start();
		Thread.sleep(4000);
		return thread;
	}
	
	protected JmsClient start_LogEvent_client() throws Exception {
		JmsClient client = new LogEventProxyForJMS(LogEvent.ID);
		configureClient(client, getTargetDestination());
		client.setCreateTemporaryQueue(true);
		client.initialize();
		return client;
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
	
	protected void validateResult(Item item) throws Exception {
		Assert.notNull(item);
		//TODO 
	}
	
	@Test
	//@Ignore
	@InSequence(value = 1)
	@BytemanRule(name = "rule1", 
			targetClass = "LogEventHandlerImpl", 
			targetMethod = "logEvent", 
			targetLocation = "AT ENTRY", 
			action = "$0.timeout = 0")
	public void runTest_LogEvent_timeout() throws Exception {
		String testName = "runTest_LogEvent_timeout";
		log.info(testName+": started");
		
		setupByteman(testName);
		registerNotificationListeners();
		
		// setup expectations
		expectedEvent = "JeeMigrationExample_LogEvent_Incoming_Request_Aborted";
		//TODO expectedMessage = "redhat.jee-migration-example.Event";
		
		try {
			// execute action
			JeeMigrationExampleFixture.reset();
			runAction_send_LogEvent();
			
			// validate request initiation state
			Assert.isTrue(isFiredRequestReceived("Jee-migration-example_LogEvent"));
			
			// validate request non-completion state
			Assert.isFalse(isFiredRequestHandled("Jee-migration-example_LogEvent"));
			Assert.isFalse(isFiredRequestDone("Jee-migration-example_LogEvent"));
			
			// validate incoming request aborted state
			Assert.isTrue(isFiredIncomingRequestAborted("Jee-migration-example_LogEvent"));
			
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
			targetMethod = "logEvent", 
			targetLocation = "AT EXIT", 
			action = "throw new org.aries.ApplicationFailure(\"exception message\")")
	public void runTest_LogEvent_exception() throws Exception {
		String testName = "runTest_LogEvent_exception";
		log.info(testName+": started");
		
		setupByteman(testName);
		registerNotificationListeners();
		
		// setup expectations
		expectedEvent = "JeeMigrationExample_LogEvent_Incoming_Request_Aborted";
		expectedMessage = "redhat.jee-migration-example.Event";
		exceptionMessage = "exception message";
		
		try {
			// execute action
			JeeMigrationExampleFixture.reset();
			runAction_send_LogEvent();

			// validate request initiation state
			Assert.isTrue(isFiredRequestReceived("Jee-migration-example_LogEvent"));

			// validate request non-completion state
			Assert.isFalse(isFiredRequestHandled("Jee-migration-example_LogEvent"));
			Assert.isFalse(isFiredRequestDone("Jee-migration-example_LogEvent"));

			// validate incoming request aborted state
			Assert.isTrue(isFiredIncomingRequestAborted("Jee-migration-example_LogEvent"));
			
			if (errorMessage != null)
				fail(errorMessage);
			
		} finally {
			tearDownByteman(testName);
		}
	}
	
}
