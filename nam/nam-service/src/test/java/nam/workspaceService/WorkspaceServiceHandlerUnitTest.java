package nam.workspaceService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import nam.NamRepository;
import nam.model.Workspace;
import nam.model.util.ModelFixture;
import nam.model.util.ModelHelper;

import org.aries.jaxb.JAXBSessionCache;
import org.aries.runtime.BeanContext;
import org.aries.tx.AbstractHandlerUnitTest;
import org.aries.tx.Transactional;
import org.aries.validate.util.CheckpointManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class WorkspaceServiceHandlerUnitTest extends AbstractHandlerUnitTest {
	
	private WorkspaceServiceHandlerImpl fixture;
	
	private NamRepository mockNamRepository;
	
	
	public String getName() {
		return "WorkspaceService";
	}
	
	public String getDomain() {
		return "nam-service";
	}
	
	public Transactional getFixture() {
		return fixture;
	}
	
	@Before
	public void setUp() throws Exception {
		mockNamRepository = mock(NamRepository.class);
		CheckpointManager.addConfiguration("nam-service-checks.xml");
		JAXBSessionCache jaxbSessionCache = new JAXBSessionCache(getDomain());
		CheckpointManager.setJAXBSessionCache(jaxbSessionCache);
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		BeanContext.clear();
		mockNamRepository = null;
		fixture = null;
		super.tearDown();
	}
	
	protected WorkspaceServiceHandlerImpl createFixture() throws Exception {
		fixture = new WorkspaceServiceHandlerImpl();
		fixture.namRepository = mockNamRepository;
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void test_getWorkspaceList_Success() throws Exception {
		List<Workspace> expectedWorkspaceRecords = ModelFixture.createWorkspace_List();
		when(mockNamRepository.getAllWorkspaceRecords()).thenReturn(expectedWorkspaceRecords);

		try {
			fixture = createFixture();
			List<Workspace> actualWorkspaceRecords = fixture.getWorkspaceList();
			//TODO ModelHelper.assertWorkspaceRecordListEquals(expectedWorkspaceRecords, actualWorkspaceRecords);
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateAfterExecution();
		}
	}
	
}
