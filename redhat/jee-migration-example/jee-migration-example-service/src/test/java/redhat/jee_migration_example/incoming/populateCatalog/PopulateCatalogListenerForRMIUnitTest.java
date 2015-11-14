package redhat.jee_migration_example.incoming.populateCatalog;

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

import redhat.jee_migration_example.EventLoggerContext;


@RunWith(MockitoJUnitRunner.class)
public class PopulateCatalogListenerForRMIUnitTest extends AbstractListenerForRMIUnitTest {
	
	private PopulateCatalogListenerForRMI fixture;
	
	private PopulateCatalogInterceptor mockPopulateCatalogInterceptor;
	
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
		mockPopulateCatalogInterceptor = mock(PopulateCatalogInterceptor.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("jee-migration-example-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		mockEventLoggerContext = null;
		mockPopulateCatalogInterceptor = null;
		fixture = null;
		super.tearDown();
	}
	
	protected PopulateCatalogListenerForRMI createFixture() throws Exception {
		fixture = new PopulateCatalogListenerForRMI();
		FieldUtil.setFieldValue(fixture, "eventLoggerContext", mockEventLoggerContext);
		FieldUtil.setFieldValue(fixture, "populateCatalogInterceptor", mockPopulateCatalogInterceptor);
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void testExecute_populateCatalog_success() throws Exception {
		runTestExecute_populateCatalog();
	}
	
	public void runTestExecute_populateCatalog() throws Exception {
		try {
			Message message = new Message();
			message.addPart(MessageConstants.PROPERTY_OPERATION_NAME, "populateCatalog");
			
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
			verify(mockPopulateCatalogInterceptor).populateCatalog(message);
	}
	
}
