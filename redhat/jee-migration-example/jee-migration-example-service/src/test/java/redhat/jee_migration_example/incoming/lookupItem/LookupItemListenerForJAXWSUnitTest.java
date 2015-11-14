package redhat.jee_migration_example.incoming.lookupItem;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.aries.tx.AbstractListenerForJAXWSUnitTest;
import org.aries.util.FieldUtil;
import org.aries.validate.util.CheckpointManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import redhat.jee_migration_example.EventLoggerContext;


@RunWith(MockitoJUnitRunner.class)
public class LookupItemListenerForJAXWSUnitTest extends AbstractListenerForJAXWSUnitTest {
	
	private LookupItemListenerForJAXWS fixture;
	
	private LookupItemHandler mockLookupItemHandler;
	
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
		mockLookupItemHandler = mock(LookupItemHandler.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("jee-migration-example-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		mockEventLoggerContext = null;
		mockLookupItemHandler = null;
		fixture = null;
		super.tearDown();
	}
	
	protected LookupItemListenerForJAXWS createFixture() throws Exception {
		fixture = new LookupItemListenerForJAXWS();
		FieldUtil.setFieldValue(fixture, "eventLoggerContext", mockEventLoggerContext);
		FieldUtil.setFieldValue(fixture, "lookupItemHandler", mockLookupItemHandler);
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
			
			validateAfterInvocation(id);
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateAfterExecution();
		}
	}
	
	public void validateAfterInvocation(Long id) throws Exception {
		if (!isExpectedValidationError)
			verify(mockLookupItemHandler).lookupItem(id);
	}
	
}
