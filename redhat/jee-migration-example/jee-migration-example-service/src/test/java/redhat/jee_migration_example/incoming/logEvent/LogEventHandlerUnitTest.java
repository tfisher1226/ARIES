package redhat.jee_migration_example.incoming.logEvent;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.aries.runtime.BeanContext;
import org.aries.tx.AbstractHandlerUnitTest;
import org.aries.tx.Transactional;
import org.aries.util.FieldUtil;
import org.aries.validate.util.CheckpointManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import redhat.jee_migration_example.Event;
import redhat.jee_migration_example.EventLoggerContext;
import redhat.jee_migration_example.EventLoggerProcess;
import redhat.jee_migration_example.util.JeeMigrationExampleFixture;


@RunWith(MockitoJUnitRunner.class)
public class LogEventHandlerUnitTest extends AbstractHandlerUnitTest {
	
	private LogEventHandlerImpl fixture;
	
	private EventLoggerContext mockEventLoggerContext;
	
	private EventLoggerProcess mockEventLoggerProcess;
	
	
	public String getName() {
		return "LogEvent";
	}
	
	public String getDomain() {
		return "redhat";
	}
	
	public Transactional getFixture() {
		return fixture;
	}
	
	public EventLoggerContext getMockServiceContext() {
		return mockEventLoggerContext;
	}
	
	public EventLoggerProcess getMockServiceProcess() {
		return mockEventLoggerProcess;
	}
	
	@Before
	public void setUp() throws Exception {
		mockEventLoggerContext = new EventLoggerContext();
		mockEventLoggerProcess = mock(EventLoggerProcess.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("jee-migration-example-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		BeanContext.clear();
		mockEventLoggerContext = null;
		mockEventLoggerProcess = null;
		fixture = null;
		super.tearDown();
	}
	
	protected LogEventHandlerImpl createFixture() throws Exception {
		fixture = new LogEventHandlerImpl();
		FieldUtil.setFieldValue(fixture, "eventLoggerContext", mockEventLoggerContext);
		FieldUtil.setFieldValue(fixture, "eventLoggerProcess", mockEventLoggerProcess);
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
		addExpectedServiceAbortedException("Event must be specified");
		isAbortExpected = true;
		runTestExecute_logEvent(null);
	}
	
	@Test
	public void testExecute_logEvent_emptyEvent() throws Exception {
		addExpectedServiceAbortedException("Event/date must be specified");
		Event event = JeeMigrationExampleFixture.createEmpty_Event();
		isAbortExpected = true;
		runTestExecute_logEvent(event);
	}
	
	@Test
	public void testExecute_logEvent_nullEventDate() throws Exception {
		addExpectedServiceAbortedException("Event/date must be specified");
		Event event = JeeMigrationExampleFixture.create_Event();
		event.setDate(null);
		isAbortExpected = true;
		runTestExecute_logEvent(event);
	}
	
	@Test
	public void testExecute_logEvent_nullEventMessage() throws Exception {
		addExpectedServiceAbortedException("Event/message must be specified");
		Event event = JeeMigrationExampleFixture.create_Event();
		event.setMessage(null);
		isAbortExpected = true;
		runTestExecute_logEvent(event);
	}
	
	@Test
	public void testExecute_logEvent_emptyEventMessage() throws Exception {
		addExpectedServiceAbortedException("Event/message must be specified");
		Event event = JeeMigrationExampleFixture.create_Event();
		event.setMessage("");
		isAbortExpected = true;
		runTestExecute_logEvent(event);
	}
	
	public void runTestExecute_logEvent(Event event) throws Exception {
		try {
			fixture = createFixture();
			fixture.logEvent(event);
			validateProcessInvocation(event);
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateProcessNotification();
			validateAfterExecution();
		}
	}
	
	protected void validateProcessInvocation(Event event) throws Exception {
		if (!isAbortExpected)
			verify(mockEventLoggerProcess).handle_LogEvent_request(event);
	}
	
	protected void validateProcessNotification() throws Exception {
		//verify(mockEventLoggerProcess).fireLogEventDone();
	}
	
	protected void validateAfterExecution() throws Exception {
		if (isAbortExpected)
			verify(mockEventLoggerProcess).handle_LogEvent_request_exception(expectedException);
		super.validateAfterExecution();
	}
	
}