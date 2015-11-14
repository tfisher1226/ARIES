package redhat.jee_migration_example.incoming.lookupItem;

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

import redhat.jee_migration_example.EventLoggerContext;
import redhat.jee_migration_example.EventLoggerProcess;


@RunWith(MockitoJUnitRunner.class)
public class LookupItemHandlerUnitTest extends AbstractHandlerUnitTest {
	
	private LookupItemHandlerImpl fixture;
	
	private EventLoggerContext mockEventLoggerContext;
	
	private EventLoggerProcess mockEventLoggerProcess;
	
	
	public String getName() {
		return "LookupItem";
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
	
	protected LookupItemHandlerImpl createFixture() throws Exception {
		fixture = new LookupItemHandlerImpl();
		FieldUtil.setFieldValue(fixture, "eventLoggerContext", mockEventLoggerContext);
		FieldUtil.setFieldValue(fixture, "eventLoggerProcess", mockEventLoggerProcess);
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void testExecute_lookupItem_success() throws Exception {
		Long id = new Long(1L);
		runTestExecute_lookupItem(id);
	}
	
	public void runTestExecute_lookupItem(Long id) throws Exception {
		try {
			fixture = createFixture();
			fixture.lookupItem(id);
			validateProcessInvocation(id);
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateProcessNotification();
			validateAfterExecution();
		}
	}
	
	protected void validateProcessInvocation(Long id) throws Exception {
		if (!isAbortExpected)
			verify(mockEventLoggerProcess).handle_LookupItem_request(id);
	}
	
	protected void validateProcessNotification() throws Exception {
		//verify(mockEventLoggerProcess).fireLookupItemDone();
	}
	
	protected void validateAfterExecution() throws Exception {
		if (isAbortExpected)
			verify(mockEventLoggerProcess).handle_LookupItem_request_exception(expectedException);
		super.validateAfterExecution();
	}
	
}
