package nam.projectService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import nam.NamRepository;
import nam.model.Project;
import nam.model.util.ModelFixture;
import nam.model.util.ModelHelper;
import nam.projectService.ProjectServiceHandlerImpl;

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
public class ProjectServiceHandlerUnitTest extends AbstractHandlerUnitTest {
	
	private ProjectServiceHandlerImpl fixture;
	
	private NamRepository mockNamRepository;
	
	
	public String getName() {
		return "ProjectService";
	}
	
	public String getDomain() {
		return "org.aries";
	}
	
	public Transactional getFixture() {
		return fixture;
	}
	
	@Before
	public void setUp() throws Exception {
		mockNamRepository = mock(NamRepository.class);
		CheckpointManager.addConfiguration("nam-service-checks.xml");
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		BeanContext.clear();
		mockNamRepository = null;
		fixture = null;
		super.tearDown();
	}
	
	protected ProjectServiceHandlerImpl createFixture() throws Exception {
		fixture = new ProjectServiceHandlerImpl();
		fixture.namRepository = mockNamRepository;
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void test_getAllProjectRecords_Success() throws Exception {
		List<Project> expectedProjectRecords = ModelFixture.createProject_List();
		when(mockNamRepository.getAllProjectRecords()).thenReturn(expectedProjectRecords);

		fixture = createFixture();
		List<Project> actualProjectRecords = fixture.getProjectList();
		//TODO ModelHelper.assertProjectRecordListEquals(expectedProjectRecords, actualProjectRecords);
	}
	
}
