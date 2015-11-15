package nam.dao;

import java.util.List;

import nam.dao.ProjectDaoImpl;
import nam.entity.ProjectEntity;

import org.aries.Assert;
import org.aries.tx.AbstractDataIT;
import org.aries.tx.TransactionRegistry;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class ProjectDaoIT extends AbstractDataIT {

	private ProjectDaoImpl<ProjectEntity> fixture;

	private NamDaoITHelper helper;


	@BeforeClass
	public static void beforeCLass() throws Exception {
		createDataSource("nam_db", "root", "");
		createTransactionManager();
		createNamingServiceContext("java:/nam_ds");
		setupEntityManagerFactory("nam");
	}

	@AfterClass
	public static void afterCLass() throws Exception {
		teardownEntityManagerFactory();
		teardownEntityManager();
	}

	@Before
	public void setUp() throws Exception {
		testDataSource.setTransactionProvider(TransactionRegistry.getInstance());
		helper = new NamDaoITHelper(createEntityManager());
		fixture = createFixture();
		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		resetTransactionContext();
		teardownEntityManager();
		testDataSource.clear();
		fixture = null;
		super.tearDown();
	}

	protected ProjectDaoImpl<ProjectEntity> createFixture() throws Exception {
		fixture = new ProjectDaoImpl<ProjectEntity>();
		fixture.setEntityClass(ProjectEntity.class);
		fixture.setEntityManager(entityManager);
		return fixture;
	}

	protected ProjectEntity createProjectEntity() throws Exception {
		ProjectEntity projectEntity = helper.createProjectEntity();
		return projectEntity;
	}

	protected void openEntityManager() throws Exception {
		entityManager = createEntityManager();
		fixture.setEntityManager(entityManager);
	}

	protected void closeEntityManager() throws Exception {
		if (entityManager.isOpen())
			entityManager.close();
		fixture.setEntityManager(null);
	}

	@Test
	public void testGetElementList() throws Exception {
		// prepare environment
		assureDeleteAll();
		assureAddProject();
		
		// prepare context
		openEntityManager();
		
		//execute test
		List<ProjectEntity> records = fixture.getAllProjectRecords();
		
		// close context
		closeEntityManager();
		
		//validate results
		openEntityManager();
		verifyProjectCount(records, 1);
		closeEntityManager();
	}

	@Test
	public void testGetProjectById() throws Exception {
		// prepare environment
		assureDeleteAll();
		Long id = assureAddProject();
		
		// prepare context
		openEntityManager();
		
		// execute test
		ProjectEntity project = fixture.getProjectRecordById(id);
		
		// close context
		closeEntityManager();
		
		// validate results
		Assert.notNull(project, "Project should exist");
		Assert.equals(project.getId(), id, "Id should be correct");
	}

	@Test
	public void testGetProjectById_Null() throws Exception {
		// prepare environment
		assureDeleteAll();
		assureAddProject();
		
		try {
			// prepare context
			openEntityManager();
		
			// execute test
			fixture.getProjectRecordById(null);
			fail("Exception should have been thrown");
		
		} catch (AssertionError e) {
			Assert.exception(e, "Id must be specified");
		
		} finally {
			// close context
			closeEntityManager();
		}
	}

	@Test
	public void testGetProjectByName() throws Exception {
		// prepare environment
		assureDeleteAll();
		Long id = assureAddProject();
		
		// prepare context
		openEntityManager();
		
		// execute test
		String name = "project";
		ProjectEntity project = fixture.getProjectRecordByName(name);
		
		// close context
		closeEntityManager();
		
		// validate results
		Assert.notNull(project, "Project should exist");
		Assert.equals(project.getName(), name, "Name should be correct");
	}

	@Test
	public void testGetProjectRecordsByOwner_Null() throws Exception {
		// prepare environment
		assureDeleteAll();
		assureAddProject();
		
		try {
			// prepare context
			openEntityManager();
		
			// execute test
			fixture.getProjectRecordsByOwner(null);
			fail("Exception should have been thrown");
		
		} catch (AssertionError e) {
			Assert.exception(e, "Owner must be specified");
		
		} finally {
			// close context
			closeEntityManager();
		}
	}

	@Test
	public void testAddProject_Commit() throws Exception {
		// prepare environment
		assureDeleteAll();
		
		// prepare context
		openEntityManager();
		beginTransaction();
		
		//execute test
		ProjectEntity project = createProjectEntity();
		Long id = fixture.addProjectRecord(project);
		Assert.notNull(id, "Id should exist");
		
		// close context
		commitTransaction();
		closeEntityManager();
		
		// validate results
		openEntityManager();
		verifyProjectCount(1);
		verifyProjectExists(id);
		closeEntityManager();
	}

	@Test
	public void testAddProject_Rollback() throws Exception {
		// prepare environment
		assureDeleteAll();
		
		// prepare context
		openEntityManager();
		beginTransaction();
		
		//execute test
		ProjectEntity project = createProjectEntity();
		Long id = fixture.addProjectRecord(project);
		Assert.notNull(id, "Id should exist");
		
		// close context
		rollbackTransaction();
		closeEntityManager();
		
		// validate results
		openEntityManager();
		verifyProjectCount(0);
		closeEntityManager();
	}

	protected Long assureAddProject() throws Exception {
		ProjectEntity projectEntity = createProjectEntity();
		Long id = assureAddProject(projectEntity);
		return id;
	}

	protected Long assureAddProject(ProjectEntity project) throws Exception {
		openEntityManager();
		beginTransaction();
		Long id = fixture.addProjectRecord(project);
		commitTransaction();
		verifyProjectExists(id);
		closeEntityManager();
		return id;
	}

	protected void assureDeleteProject(Long id) throws Exception {
		ProjectEntity project = fixture.getProjectRecordById(id);
		assureDeleteProject(project);
	}

	protected void assureDeleteProject(ProjectEntity project) throws Exception {
		openEntityManager();
		beginTransaction();
		fixture.removeProjectRecord(project);
		commitTransaction();
		verifyProjectNotExists(project);
		closeEntityManager();
	}

	protected void verifyProjectCount(int expectedCount) throws Exception {
		List<ProjectEntity> records = fixture.getAllProjectRecords();
		verifyProjectCount(records, expectedCount);
	}

	protected void verifyProjectCount(List<ProjectEntity> records, int expectedCount) throws Exception {
		Assert.notNull(records, "Result not found");
		if (expectedCount > 0)
			Assert.notNull(records, "Project records should exist");
		Assert.equals(records.size(), expectedCount, "Project record count not correct");
	}

	protected void verifyProjectExists(ProjectEntity project) throws Exception {
		verifyProjectExists(project.getId());
	}

	protected void verifyProjectExists(Long id) throws Exception {
		ProjectEntity project = fixture.getProjectRecordById(id);
		Assert.notNull(project, "Project should exist");
		Assert.equals(project.getId(), id, "Ids should be same");
	}

	protected void verifyProjectNotExists(ProjectEntity project) throws Exception {
		verifyProjectNotExists(project.getId());
	}

	protected void verifyProjectNotExists(Long id) throws Exception {
		ProjectEntity project = fixture.getProjectRecordById(id);
		Assert.isNull(project, "Project should not exist");
	}

}
