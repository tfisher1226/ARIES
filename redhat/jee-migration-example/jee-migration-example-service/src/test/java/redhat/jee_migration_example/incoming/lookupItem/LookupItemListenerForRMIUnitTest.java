package redhat.jee_migration_example.incoming.lookupItem;

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
public class LookupItemListenerForRMIUnitTest extends AbstractListenerForRMIUnitTest {
	
	private LookupItemListenerForRMI fixture;
	
	private LookupItemInterceptor mockLookupItemInterceptor;
	
	private EventLoggerContext mockEventLoggerContext;
	
	
	@Override
	public String getServiceId() {
		return LookupItem.ID;
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
		mockLookupItemInterceptor = mock(LookupItemInterceptor.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("jee-migration-example-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		mockEventLoggerContext = null;
		mockLookupItemInterceptor = null;
		fixture = null;
		super.tearDown();
	}
	
	protected LookupItemListenerForRMI createFixture() throws Exception {
		fixture = new LookupItemListenerForRMI();
		FieldUtil.setFieldValue(fixture, "eventLoggerContext", mockEventLoggerContext);
		FieldUtil.setFieldValue(fixture, "lookupItemInterceptor", mockLookupItemInterceptor);
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
			Message message = new Message();
			message.addPart("id",  id);
			message.addPart(MessageConstants.PROPERTY_OPERATION_NAME, "lookupItem");
			
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
			verify(mockLookupItemInterceptor).lookupItem(message);
	}
	
}
