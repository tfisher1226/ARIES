package nam.dao;

import java.util.List;

import nam.entity.WorkspaceEntity;

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
public class WorkspaceDaoIT extends AbstractDataIT {

	private WorkspaceDaoImpl<WorkspaceEntity> fixture;

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

	protected WorkspaceDaoImpl<WorkspaceEntity> createFixture() throws Exception {
		fixture = new WorkspaceDaoImpl<WorkspaceEntity>();
		fixture.setEntityClass(WorkspaceEntity.class);
		fixture.setEntityManager(entityManager);
		return fixture;
	}

	protected WorkspaceEntity createWorkspaceEntity() throws Exception {
		WorkspaceEntity workspaceEntity = helper.createWorkspaceEntity();
		return workspaceEntity;
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
		assureAddWorkspace();
		
		// prepare context
		openEntityManager();
		
		//execute test
		List<WorkspaceEntity> records = fixture.getAllWorkspaceRecords();
		
		// close context
		closeEntityManager();
		
		//validate results
		openEntityManager();
		verifyWorkspaceCount(records, 1);
		closeEntityManager();
	}

	@Test
	public void testGetWorkspaceById() throws Exception {
		// prepare environment
		assureDeleteAll();
		Long id = assureAddWorkspace();
		
		// prepare context
		openEntityManager();
		
		// execute test
		WorkspaceEntity workspace = fixture.getWorkspaceRecordById(id);
		
		// close context
		closeEntityManager();
		
		// validate results
		Assert.notNull(workspace, "Workspace should exist");
		Assert.equals(workspace.getId(), id, "Id should be correct");
	}

	@Test
	public void testGetWorkspaceById_Null() throws Exception {
		// prepare environment
		assureDeleteAll();
		assureAddWorkspace();
		
		try {
			// prepare context
			openEntityManager();
		
			// execute test
			fixture.getWorkspaceRecordById(null);
			fail("Exception should have been thrown");
		
		} catch (AssertionError e) {
			Assert.exception(e, "Id must be specified");
		
		} finally {
			// close context
			closeEntityManager();
		}
	}

	@Test
	public void testGetWorkspaceByName() throws Exception {
		// prepare environment
		assureDeleteAll();
		Long id = assureAddWorkspace();
		
		// prepare context
		openEntityManager();
		
		// execute test
		int index = 0;
		String name = "dummyName" + index;
		WorkspaceEntity workspace = fixture.getWorkspaceRecordByName(name);		
		// close context
		closeEntityManager();
		
		// validate results
		Assert.notNull(workspace, "Workspace should exist");
		Assert.equals(workspace.getName(), name, "Name should be correct");
	}

	@Test
	public void testGetWorkspaceByName_Null() throws Exception {
		// prepare environment
		assureDeleteAll();
		assureAddWorkspace();
		
		try {
			// prepare context
			openEntityManager();
		
			// execute test
			fixture.getWorkspaceRecordByName(null);
			fail("Exception should have been thrown");
		
		} catch (AssertionError e) {
			Assert.exception(e, "Name must be specified");
		
		} finally {
			// close context
			closeEntityManager();
		}
	}

	@Test
	public void testAddWorkspace_Commit() throws Exception {
		// prepare environment
		assureDeleteAll();
		
		// prepare context
		openEntityManager();
		beginTransaction();
		
		//execute test
		WorkspaceEntity workspace = createWorkspaceEntity();
		Long id = fixture.addWorkspaceRecord(workspace);
		Assert.notNull(id, "Id should exist");
		
		// close context
		commitTransaction();
		closeEntityManager();
		
		// validate results
		openEntityManager();
		verifyWorkspaceCount(1);
		verifyWorkspaceExists(id);
		closeEntityManager();
	}

	@Test
	public void testAddWorkspace_Rollback() throws Exception {
		// prepare environment
		assureDeleteAll();
		
		// prepare context
		openEntityManager();
		beginTransaction();
		
		//execute test
		WorkspaceEntity workspace = createWorkspaceEntity();
		Long id = fixture.addWorkspaceRecord(workspace);
		Assert.notNull(id, "Id should exist");
		
		// close context
		rollbackTransaction();
		closeEntityManager();
		
		// validate results
		openEntityManager();
		verifyWorkspaceCount(0);
		closeEntityManager();
	}

	protected Long assureAddWorkspace() throws Exception {
		WorkspaceEntity workspaceEntity = createWorkspaceEntity();
		Long id = assureAddWorkspace(workspaceEntity);
		return id;
	}

	protected Long assureAddWorkspace(WorkspaceEntity workspace) throws Exception {
		openEntityManager();
		beginTransaction();
		Long id = fixture.addWorkspaceRecord(workspace);
		commitTransaction();
		verifyWorkspaceExists(id);
		closeEntityManager();
		return id;
	}

	protected void assureDeleteWorkspace(Long id) throws Exception {
		WorkspaceEntity workspace = fixture.getWorkspaceRecordById(id);
		assureDeleteWorkspace(workspace);
	}

	protected void assureDeleteWorkspace(WorkspaceEntity workspace) throws Exception {
		openEntityManager();
		beginTransaction();
		fixture.removeWorkspaceRecord(workspace);
		commitTransaction();
		verifyWorkspaceNotExists(workspace);
		closeEntityManager();
	}

	protected void verifyWorkspaceCount(int expectedCount) throws Exception {
		List<WorkspaceEntity> records = fixture.getAllWorkspaceRecords();
		verifyWorkspaceCount(records, expectedCount);
	}

	protected void verifyWorkspaceCount(List<WorkspaceEntity> records, int expectedCount) throws Exception {
		Assert.notNull(records, "Result not found");
		if (expectedCount > 0)
			Assert.notNull(records, "Workspace records should exist");
		Assert.equals(records.size(), expectedCount, "Workspace record count not correct");
	}

	protected void verifyWorkspaceExists(WorkspaceEntity workspace) throws Exception {
		verifyWorkspaceExists(workspace.getId());
	}

	protected void verifyWorkspaceExists(Long id) throws Exception {
		WorkspaceEntity workspace = fixture.getWorkspaceRecordById(id);
		Assert.notNull(workspace, "Workspace should exist");
		Assert.equals(workspace.getId(), id, "Ids should be same");
	}

	protected void verifyWorkspaceNotExists(WorkspaceEntity workspace) throws Exception {
		verifyWorkspaceNotExists(workspace.getId());
	}

	protected void verifyWorkspaceNotExists(Long id) throws Exception {
		WorkspaceEntity workspace = fixture.getWorkspaceRecordById(id);
		Assert.isNull(workspace, "Workspace should not exist");
	}

}
