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

import admin.dao.PermissionDaoImpl;
import admin.entity.PermissionEntity;
import admin.entity.RoleEntity;


@RunWith(MockitoJUnitRunner.class)
public class PermissionDaoIT extends AbstractDataIT {

	private PermissionDaoImpl<PermissionEntity> fixture;

	private AdminDaoITHelper helper;

	private RoleEntity container;


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

	protected PermissionDaoImpl<PermissionEntity> createFixture() throws Exception {
		fixture = new PermissionDaoImpl<PermissionEntity>();
		fixture.setEntityClass(PermissionEntity.class);
		fixture.setEntityManager(entityManager);
		return fixture;
	}

	protected PermissionEntity createPermissionEntity() throws Exception {
		//PermissionEntity permissionEntity = helper.createPermissionEntity(container);
		PermissionEntity permissionEntity = helper.createPermissionEntity();
		return permissionEntity;
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
		assureAddContainer();
		assureAddPermission();
		
		// prepare context
		openEntityManager();
		
		//execute test
		List<PermissionEntity> records = fixture.getAllPermissionRecords();
		
		// close context
		closeEntityManager();
		
		//validate results
		openEntityManager();
		verifyPermissionCount(records, 1);
		closeEntityManager();
	}

	@Test
	public void testGetPermissionById() throws Exception {
		// prepare environment
		assureDeleteAll();
		assureAddContainer();
		Long id = assureAddPermission();
		
		// prepare context
		openEntityManager();
		
		// execute test
		PermissionEntity permission = fixture.getPermissionRecordById(id);
		
		// close context
		closeEntityManager();
		
		// validate results
		Assert.notNull(permission, "Permission should exist");
		Assert.equals(permission.getId(), id, "Id should be correct");
	}

	@Test
	public void testGetPermissionById_Null() throws Exception {
		// prepare environment
		assureDeleteAll();
		assureAddContainer();
		assureAddPermission();
		
		try {
			// prepare context
			openEntityManager();
		
			// execute test
			fixture.getPermissionRecordById(null);
			fail("Exception should have been thrown");
		
		} catch (AssertionError e) {
			Assert.exception(e, "Id must be specified");
		
		} finally {
			// close context
			closeEntityManager();
		}
	}

	@Test
	public void testAddPermission_Commit() throws Exception {
		// prepare environment
		assureDeleteAll();
		assureAddContainer();
		
		// prepare context
		openEntityManager();
		beginTransaction();
		
		//execute test
		PermissionEntity permission = createPermissionEntity();
		Long id = fixture.addPermissionRecord(permission);
		Assert.notNull(id, "Id should exist");
		
		// close context
		commitTransaction();
		closeEntityManager();
		
		// validate results
		openEntityManager();
		verifyPermissionCount(1);
		verifyPermissionExists(id);
		closeEntityManager();
	}

	@Test
	public void testAddPermission_Rollback() throws Exception {
		// prepare environment
		assureDeleteAll();
		assureAddContainer();
		
		// prepare context
		openEntityManager();
		beginTransaction();
		
		//execute test
		PermissionEntity permission = createPermissionEntity();
		Long id = fixture.addPermissionRecord(permission);
		Assert.notNull(id, "Id should exist");
		
		// close context
		rollbackTransaction();
		closeEntityManager();
		
		// validate results
		openEntityManager();
		verifyPermissionCount(0);
		closeEntityManager();
	}

	protected Long assureAddContainer() throws Exception {
		container = helper.createRoleEntity();
		container.setPermissions(null);
		Long id = assureAddContainer(container);
		container.setId(id);
		return id;
	}

	protected Long assureAddContainer(RoleEntity roleEntity) throws Exception {
		Long id = helper.persistRoleEntity(roleEntity);
		return id;
	}

	protected Long assureAddPermission() throws Exception {
		PermissionEntity permissionEntity = createPermissionEntity();
		Long id = assureAddPermission(permissionEntity);
		return id;
	}

	protected Long assureAddPermission(PermissionEntity permission) throws Exception {
		openEntityManager();
		beginTransaction();
		Long id = fixture.addPermissionRecord(permission);
		commitTransaction();
		verifyPermissionExists(id);
		closeEntityManager();
		return id;
	}

	protected void assureDeletePermission(Long id) throws Exception {
		PermissionEntity permission = fixture.getPermissionRecordById(id);
		assureDeletePermission(permission);
	}

	protected void assureDeletePermission(PermissionEntity permission) throws Exception {
		openEntityManager();
		beginTransaction();
		fixture.removePermissionRecord(permission);
		commitTransaction();
		verifyPermissionNotExists(permission);
		closeEntityManager();
	}

	protected void verifyPermissionCount(int expectedCount) throws Exception {
		List<PermissionEntity> records = fixture.getAllPermissionRecords();
		verifyPermissionCount(records, expectedCount);
	}

	protected void verifyPermissionCount(List<PermissionEntity> records, int expectedCount) throws Exception {
		Assert.notNull(records, "Result not found");
		if (expectedCount > 0)
			Assert.notNull(records, "Permission records should exist");
		Assert.equals(records.size(), expectedCount, "Permission record count not correct");
	}

	protected void verifyPermissionExists(PermissionEntity permission) throws Exception {
		verifyPermissionExists(permission.getId());
	}

	protected void verifyPermissionExists(Long id) throws Exception {
		PermissionEntity permission = fixture.getPermissionRecordById(id);
		Assert.notNull(permission, "Permission should exist");
		Assert.equals(permission.getId(), id, "Ids should be same");
	}

	protected void verifyPermissionNotExists(PermissionEntity permission) throws Exception {
		verifyPermissionNotExists(permission.getId());
	}

	protected void verifyPermissionNotExists(Long id) throws Exception {
		PermissionEntity permission = fixture.getPermissionRecordById(id);
		Assert.isNull(permission, "Permission should not exist");
	}

}
