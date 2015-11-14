package redhat.jee_migration_example.incoming.populateCatalog;

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

import redhat.jee_migration_example.EventLoggerContext;


@RunWith(MockitoJUnitRunner.class)
public class PopulateCatalogListenerForJMSUnitTest extends AbstractListenerForJMSUnitTest {
	
	private PopulateCatalogListenerForJMS fixture;
	
	private PopulateCatalogHandler mockPopulateCatalogHandler;
	
	private EventLoggerContext mockEventLoggerContext;
	
	
	@Override
	public String getServiceId() {
		return PopulateCatalog.ID;
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
		mockPopulateCatalogHandler = mock(PopulateCatalogHandler.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("jee-migration-example-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		mockEventLoggerContext = null;
		mockPopulateCatalogHandler = null;
		fixture = null;
		super.tearDown();
	}
	
	protected PopulateCatalogListenerForJMS createFixture() throws Exception {
		fixture = new PopulateCatalogListenerForJMS();
		FieldUtil.setFieldValue(fixture, "eventLoggerContext", mockEventLoggerContext);
		FieldUtil.setFieldValue(fixture, "populateCatalogHandler", mockPopulateCatalogHandler);
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void testExecute_populateCatalog_success() throws Exception {
		runTestExecute_populateCatalog();
	}
	
	public void runTestExecute_populateCatalog() throws Exception {
		setupBeforeInvocation();
		
		try {
			fixture = createFixture();
			fixture.onMessage(mockMessage);
			
			validateAfterInvocation();
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateAfterExecution();
		}
	}
	
	public void setupBeforeInvocation() throws Exception {
		when(mockMessage.getObject()).thenReturn(null);
	}
	
	public void validateAfterInvocation() throws Exception {
		if (!isExpectedValidationError)
			verify(mockPopulateCatalogHandler).populateCatalog();
	}
	
}
