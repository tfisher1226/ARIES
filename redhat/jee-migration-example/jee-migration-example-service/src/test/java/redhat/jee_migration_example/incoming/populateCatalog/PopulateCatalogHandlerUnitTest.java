package redhat.jee_migration_example.incoming.populateCatalog;

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
public class PopulateCatalogHandlerUnitTest extends AbstractHandlerUnitTest {
	
	private PopulateCatalogHandlerImpl fixture;
	
	private EventLoggerContext mockEventLoggerContext;
	
	private EventLoggerProcess mockEventLoggerProcess;
	
	
	public String getName() {
		return "PopulateCatalog";
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
	
	protected PopulateCatalogHandlerImpl createFixture() throws Exception {
		fixture = new PopulateCatalogHandlerImpl();
		FieldUtil.setFieldValue(fixture, "eventLoggerContext", mockEventLoggerContext);
		FieldUtil.setFieldValue(fixture, "eventLoggerProcess", mockEventLoggerProcess);
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void testExecute_populateCatalog_success() throws Exception {
		runTestExecute_populateCatalog();
	}
	
	public void runTestExecute_populateCatalog() throws Exception {
		try {
			fixture = createFixture();
			fixture.populateCatalog();
			validateProcessInvocation();
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateProcessNotification();
			validateAfterExecution();
		}
	}
	
	protected void validateProcessInvocation() throws Exception {
		if (!isAbortExpected)
			verify(mockEventLoggerProcess).handle_PopulateCatalog_request();
	}
	
	protected void validateProcessNotification() throws Exception {
		//verify(mockEventLoggerProcess).firePopulateCatalogDone();
	}
	
	protected void validateAfterExecution() throws Exception {
		if (isAbortExpected)
			verify(mockEventLoggerProcess).handle_PopulateCatalog_request_exception(expectedException);
		super.validateAfterExecution();
	}
	
}
