package admin.dao;

import java.util.List;

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

import admin.dao.RoleDaoImpl;
import admin.entity.RoleEntity;


@RunWith(MockitoJUnitRunner.class)
public class RoleDaoIT extends AbstractDataIT {

	private RoleDaoImpl<RoleEntity> fixture;

	private AdminDaoITHelper helper;


	@BeforeClass
	public static void beforeCLass() throws Exception {
		createDataSource("admin_db", "root", "");
		createTransactionManager();
		createNamingServiceContext("java:/admin_ds");
		setupEntityManagerFactory("admin");
	}

	@AfterClass
	public static void afterCLass() throws Exception {
		teardownEntityManagerFactory();
		teardownEntityManager();
	}

	@Before
	public void setUp() throws Exception {
		testDataSource.setTransactionProvider(TransactionRegistry.getInstance());
		helper = new AdminDaoITHelper(createEntityManager());
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

	protected RoleDaoImpl<RoleEntity> createFixture() throws Exception {
		fixture = new RoleDaoImpl<RoleEntity>();
		fixture.setEntityClass(RoleEntity.class);
		fixture.setEntityManager(entityManager);
		return fixture;
	}

	protected RoleEntity createRoleEntity() throws Exception {
		RoleEntity roleEntity = helper.createRoleEntity();
		return roleEntity;
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
		assureAddRole();
		
		// prepare context
		openEntityManager();
		
		//execute test
		List<RoleEntity> records = fixture.getAllRoleRecords();
		
		// close context
		closeEntityManager();
		
		//validate results
		openEntityManager();
		verifyRoleCount(records, 1);
		closeEntityManager();
	}

	@Test
	public void testGetRoleById() throws Exception {
		// prepare environment
		assureDeleteAll();
		Long id = assureAddRole();
		
		// prepare context
		openEntityManager();
		
		// execute test
		RoleEntity role = fixture.getRoleRecordById(id);
		
		// close context
		closeEntityManager();
		
		// validate results
		Assert.notNull(role, "Role should exist");
		Assert.equals(role.getId(), id, "Id should be correct");
	}

	@Test
	public void testGetRoleById_Null() throws Exception {
		// prepare environment
		assureDeleteAll();
		assureAddRole();
		
		try {
			// prepare context
			openEntityManager();
		
			// execute test
			fixture.getRoleRecordById(null);
			fail("Exception should have been thrown");
		
		} catch (AssertionError e) {
			Assert.exception(e, "Id must be specified");
		
		} finally {
			// close context
			closeEntityManager();
		}
	}

	@Test
	public void testGetRoleByName() throws Exception {
		// prepare environment
		assureDeleteAll();
		Long id = assureAddRole();
		
		// prepare context
		openEntityManager();
		
		// execute test
		String name = "role";
		RoleEntity role = fixture.getRoleRecordByName(name);
		
		// close context
		closeEntityManager();
		
		// validate results
		Assert.notNull(role, "Role should exist");
		Assert.equals(role.getName(), name, "Name should be correct");
	}

	@Test
	public void testGetRoleRecordsByRoleType_Null() throws Exception {
		// prepare environment
		assureDeleteAll();
		assureAddRole();
		
		try {
			// prepare context
			openEntityManager();
		
			// execute test
			fixture.getRoleRecordsByRoleType(null);
			fail("Exception should have been thrown");
		
		} catch (AssertionError e) {
			Assert.exception(e, "RoleType must be specified");
		
		} finally {
			// close context
			closeEntityManager();
		}
	}

	@Test
	public void testAddRole_Commit() throws Exception {
		// prepare environment
		assureDeleteAll();
		
		// prepare context
		openEntityManager();
		beginTransaction();
		
		//execute test
		RoleEntity role = createRoleEntity();
		Long id = fixture.addRoleRecord(role);
		Assert.notNull(id, "Id should exist");
		
		// close context
		commitTransaction();
		closeEntityManager();
		
		// validate results
		openEntityManager();
		verifyRoleCount(1);
		verifyRoleExists(id);
		closeEntityManager();
	}

	@Test
	public void testAddRole_Rollback() throws Exception {
		// prepare environment
		assureDeleteAll();
		
		// prepare context
		openEntityManager();
		beginTransaction();
		
		//execute test
		RoleEntity role = createRoleEntity();
		Long id = fixture.addRoleRecord(role);
		Assert.notNull(id, "Id should exist");
		
		// close context
		rollbackTransaction();
		closeEntityManager();
		
		// validate results
		openEntityManager();
		verifyRoleCount(0);
		closeEntityManager();
	}

	protected Long assureAddRole() throws Exception {
		RoleEntity roleEntity = createRoleEntity();
		Long id = assureAddRole(roleEntity);
		return id;
	}

	protected Long assureAddRole(RoleEntity role) throws Exception {
		openEntityManager();
		beginTransaction();
		Long id = fixture.addRoleRecord(role);
		commitTransaction();
		verifyRoleExists(id);
		closeEntityManager();
		return id;
	}

	protected void assureDeleteRole(Long id) throws Exception {
		RoleEntity role = fixture.getRoleRecordById(id);
		assureDeleteRole(role);
	}

	protected void assureDeleteRole(RoleEntity role) throws Exception {
		openEntityManager();
		beginTransaction();
		fixture.removeRoleRecord(role);
		commitTransaction();
		verifyRoleNotExists(role);
		closeEntityManager();
	}

	protected void verifyRoleCount(int expectedCount) throws Exception {
		List<RoleEntity> records = fixture.getAllRoleRecords();
		verifyRoleCount(records, expectedCount);
	}

	protected void verifyRoleCount(List<RoleEntity> records, int expectedCount) throws Exception {
		Assert.notNull(records, "Result not found");
		if (expectedCount > 0)
			Assert.notNull(records, "Role records should exist");
		Assert.equals(records.size(), expectedCount, "Role record count not correct");
	}

	protected void verifyRoleExists(RoleEntity role) throws Exception {
		verifyRoleExists(role.getId());
	}

	protected void verifyRoleExists(Long id) throws Exception {
		RoleEntity role = fixture.getRoleRecordById(id);
		Assert.notNull(role, "Role should exist");
		Assert.equals(role.getId(), id, "Ids should be same");
	}

	protected void verifyRoleNotExists(RoleEntity role) throws Exception {
		verifyRoleNotExists(role.getId());
	}

	protected void verifyRoleNotExists(Long id) throws Exception {
		RoleEntity role = fixture.getRoleRecordById(id);
		Assert.isNull(role, "Role should not exist");
	}

}
