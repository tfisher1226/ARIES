package redhat.jee_migration_example.incoming.logEvent;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.aries.tx.AbstractListenerForJMSUnitTest;
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
public class LogEventListenerForJMSUnitTest extends AbstractListenerForJMSUnitTest {
	
	private LogEventListenerForJMS fixture;
	
	private LogEventHandler mockLogEventHandler;
	
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
		mockLogEventHandler = mock(LogEventHandler.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("jee-migration-example-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		mockEventLoggerContext = null;
		mockLogEventHandler = null;
		fixture = null;
		super.tearDown();
	}
	
	protected LogEventListenerForJMS createFixture() throws Exception {
		fixture = new LogEventListenerForJMS();
		FieldUtil.setFieldValue(fixture, "eventLoggerContext", mockEventLoggerContext);
		FieldUtil.setFieldValue(fixture, "logEventHandler", mockLogEventHandler);
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
		setupBeforeInvocation(event);
		
		try {
			fixture = createFixture();
			fixture.onMessage(mockMessage);
			
			validateAfterInvocation(event);
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateAfterExecution();
		}
	}
	
	public void setupBeforeInvocation(Event event) throws Exception {
		when(mockMessage.getObject()).thenReturn(event);
	}
	
	public void validateAfterInvocation(Event event) throws Exception {
		if (!isExpectedValidationError)
			verify(mockLogEventHandler).logEvent(event);
	}
	
}
