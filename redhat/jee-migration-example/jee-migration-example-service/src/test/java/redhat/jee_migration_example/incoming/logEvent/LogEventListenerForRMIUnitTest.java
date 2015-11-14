package redhat.jee_migration_example.incoming.logEvent;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.aries.message.Message;
import org.aries.message.util.MessageConstants;
import org.aries.tx.AbstractListenerForRMIUnitTest;
import org.aries.util.FieldUtil;
import org.aries.validate.util.CheckpointManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import redhat.jee_migration_example.Event;
import redhat.jee_migration_example.EventLoggerContext;
import redhat.jee_migration_example.util.JeeMigrationExampleFixture;


@RunWith(MockitoJUnitRunner.class)
public class LogEventListenerForRMIUnitTest extends AbstractListenerForRMIUnitTest {
	
	private LogEventListenerForRMI fixture;
	
	private LogEventInterceptor mockLogEventInterceptor;
	
	private EventLoggerContext mockEventLoggerContext;
	
	
	@Override
	public String getServiceId() {
		return LogEvent.ID;
	}
	
	@Override
	public String getDomain() {
		return "redhat";
	}
	
	@Override
	public String getModule() {
		return "jee-migration-example-service";
	}
	
	public EventLoggerContext getMockServiceContext() {
		return mockEventLoggerContext;
	}
	
	@Before
	public void setUp() throws Exception {
		mockEventLoggerContext = new EventLoggerContext();
		mockLogEventInterceptor = mock(LogEventInterceptor.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("jee-migration-example-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		mockEventLoggerContext = null;
		mockLogEventInterceptor = null;
		fixture = null;
		super.tearDown();
	}
	
	protected LogEventListenerForRMI createFixture() throws Exception {
		fixture = new LogEventListenerForRMI();
		FieldUtil.setFieldValue(fixture, "eventLoggerContext", mockEventLoggerContext);
		FieldUtil.setFieldValue(fixture, "logEventInterceptor", mockLogEventInterceptor);
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void testExecute_logEvent_success() throws Exception {
		Event event = JeeMigrationExampleFixture.create_Event();
		runTestExecute_logEvent(event);
	}
	
	@Test
	public void testExecute_logEvent_nullEvent() throws Exception {
		setupForExpectedAssertionFailure("Event");
		isExpectedValidationError = true;
		runTestExecute_logEvent(null);
	}
	
	@Test
	public void testExecute_logEvent_emptyEvent() throws Exception {
		setupForExpectedAssertionFailure("Event");
		Event event = JeeMigrationExampleFixture.createEmpty_Event();
		isExpectedValidationError = true;
		runTestExecute_logEvent(event);
	}
	
	@Test
	public void testExecute_logEvent_nullEventDate() throws Exception {
		setupForExpectedAssertionFailure("Event/date");
		Event event = JeeMigrationExampleFixture.create_Event();
		event.setDate(null);
		isExpectedValidationError = true;
		runTestExecute_logEvent(event);
	}
	
	@Test
	public void testExecute_logEvent_nullEventMessage() throws Exception {
		setupForExpectedAssertionFailure("Event/message");
		Event event = JeeMigrationExampleFixture.create_Event();
		event.setMessage(null);
		isExpectedValidationError = true;
		runTestExecute_logEvent(event);
	}
	
	@Test
	public void testExecute_logEvent_emptyEventMessage() throws Exception {
		setupForExpectedAssertionFailure("Event/message");
		Event event = JeeMigrationExampleFixture.create_Event();
		event.setMessage("");
		isExpectedValidationError = true;
		runTestExecute_logEvent(event);
	}
	
	public void runTestExecute_logEvent(Event event) throws Exception {
		try {
			Message message = new Message();
			message.addPart("event",  event);
			message.addPart(MessageConstants.PROPERTY_OPERATION_NAME, "logEvent");
			
			fixture = createFixture();
			fixture.invoke(message, correlationId, 60000L);
			
			validateAfterInvocation(message);
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateAfterExecution();
		}
	}
	
	public void validateAfterInvocation(Message message) throws Exception {
		if (!isExpectedValidationError)
			verify(mockLogEventInterceptor).logEvent(message);
	}
	
}
