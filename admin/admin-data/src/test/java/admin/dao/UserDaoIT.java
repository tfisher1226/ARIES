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

import admin.dao.UserDaoImpl;
import admin.entity.UserEntity;


@RunWith(MockitoJUnitRunner.class)
public class UserDaoIT extends AbstractDataIT {

	private UserDaoImpl<UserEntity> fixture;

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

	protected UserDaoImpl<UserEntity> createFixture() throws Exception {
		fixture = new UserDaoImpl<UserEntity>();
		fixture.setEntityClass(UserEntity.class);
		fixture.setEntityManager(entityManager);
		return fixture;
	}

	protected UserEntity createUserEntity() throws Exception {
		UserEntity userEntity = helper.createUserEntity();
		return userEntity;
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
		assureAddUser();
		
		// prepare context
		openEntityManager();
		
		//execute test
		List<UserEntity> records = fixture.getAllUserRecords();
		
		// close context
		closeEntityManager();
		
		//validate results
		openEntityManager();
		verifyUserCount(records, 1);
		closeEntityManager();
	}

	@Test
	public void testGetUserById() throws Exception {
		// prepare environment
		assureDeleteAll();
		Long id = assureAddUser();
		
		// prepare context
		openEntityManager();
		
		// execute test
		UserEntity user = fixture.getUserRecordById(id);
		
		// close context
		closeEntityManager();
		
		// validate results
		Assert.notNull(user, "User should exist");
		Assert.equals(user.getId(), id, "Id should be correct");
	}

	@Test
	public void testGetUserById_Null() throws Exception {
		// prepare environment
		assureDeleteAll();
		assureAddUser();
		
		try {
			// prepare context
			openEntityManager();
		
			// execute test
			fixture.getUserRecordById(null);
			fail("Exception should have been thrown");
		
		} catch (AssertionError e) {
			Assert.exception(e, "Id must be specified");
		
		} finally {
			// close context
			closeEntityManager();
		}
	}

	@Test
	public void testGetUserByUserName() throws Exception {
		// prepare environment
		assureDeleteAll();
		Long id = assureAddUser();
		
		// prepare context
		openEntityManager();
		
		// execute test
		int index = 0;
		String userName = "dummyUserName" + index;
		UserEntity user = fixture.getUserRecordByUserName(userName);
		
		// close context
		closeEntityManager();
		
		// validate results
		Assert.notNull(user, "User should exist");
		Assert.equals(user.getUserName(), userName, "UserName should be correct");
	}

	@Test
	public void testGetUserByUserName_Null() throws Exception {
		// prepare environment
		assureDeleteAll();
		assureAddUser();
		
		try {
			// prepare context
			openEntityManager();
		
			// execute test
			fixture.getUserRecordByUserName(null);
			fail("Exception should have been thrown");
		
		} catch (AssertionError e) {
			Assert.exception(e, "UserName must be specified");
		
		} finally {
			// close context
			closeEntityManager();
		}
	}

	@Test
	public void testAddUser_Commit() throws Exception {
		// prepare environment
		assureDeleteAll();
		
		// prepare context
		openEntityManager();
		beginTransaction();
		
		//execute test
		UserEntity user = createUserEntity();
		Long id = fixture.addUserRecord(user);
		Assert.notNull(id, "Id should exist");
		
		// close context
		commitTransaction();
		closeEntityManager();
		
		// validate results
		openEntityManager();
		verifyUserCount(1);
		verifyUserExists(id);
		closeEntityManager();
	}

	@Test
	public void testAddUser_Rollback() throws Exception {
		// prepare environment
		assureDeleteAll();
		
		// prepare context
		openEntityManager();
		beginTransaction();
		
		//execute test
		UserEntity user = createUserEntity();
		Long id = fixture.addUserRecord(user);
		Assert.notNull(id, "Id should exist");
		
		// close context
		rollbackTransaction();
		closeEntityManager();
		
		// validate results
		openEntityManager();
		verifyUserCount(0);
		closeEntityManager();
	}

	protected Long assureAddUser() throws Exception {
		UserEntity userEntity = createUserEntity();
		Long id = assureAddUser(userEntity);
		return id;
	}

	protected Long assureAddUser(UserEntity user) throws Exception {
		openEntityManager();
		beginTransaction();
		Long id = fixture.addUserRecord(user);
		commitTransaction();
		verifyUserExists(id);
		closeEntityManager();
		return id;
	}

	protected void assureDeleteUser(Long id) throws Exception {
		UserEntity user = fixture.getUserRecordById(id);
		assureDeleteUser(user);
	}

	protected void assureDeleteUser(UserEntity user) throws Exception {
		openEntityManager();
		beginTransaction();
		fixture.removeUserRecord(user);
		commitTransaction();
		verifyUserNotExists(user);
		closeEntityManager();
	}

	protected void verifyUserCount(int expectedCount) throws Exception {
		List<UserEntity> records = fixture.getAllUserRecords();
		verifyUserCount(records, expectedCount);
	}

	protected void verifyUserCount(List<UserEntity> records, int expectedCount) throws Exception {
		Assert.notNull(records, "Result not found");
		if (expectedCount > 0)
			Assert.notNull(records, "User records should exist");
		Assert.equals(records.size(), expectedCount, "User record count not correct");
	}

	protected void verifyUserExists(UserEntity user) throws Exception {
		verifyUserExists(user.getId());
	}

	protected void verifyUserExists(Long id) throws Exception {
		UserEntity user = fixture.getUserRecordById(id);
		Assert.notNull(user, "User should exist");
		Assert.equals(user.getId(), id, "Ids should be same");
	}

	protected void verifyUserNotExists(UserEntity user) throws Exception {
		verifyUserNotExists(user.getId());
	}

	protected void verifyUserNotExists(Long id) throws Exception {
		UserEntity user = fixture.getUserRecordById(id);
		Assert.isNull(user, "User should not exist");
	}

}
