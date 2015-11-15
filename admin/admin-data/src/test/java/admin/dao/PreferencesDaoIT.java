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

import admin.dao.PreferencesDaoImpl;
import admin.entity.PreferencesEntity;
import admin.entity.RoleEntity;
import admin.entity.UserEntity;


@RunWith(MockitoJUnitRunner.class)
public class PreferencesDaoIT extends AbstractDataIT {

	private PreferencesDaoImpl<PreferencesEntity> fixture;

	private AdminDaoITHelper helper;

	private UserEntity container;


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

	protected PreferencesDaoImpl<PreferencesEntity> createFixture() throws Exception {
		fixture = new PreferencesDaoImpl<PreferencesEntity>();
		fixture.setEntityClass(PreferencesEntity.class);
		fixture.setEntityManager(entityManager);
		return fixture;
	}

	protected PreferencesEntity createPreferencesEntity() throws Exception {
		PreferencesEntity preferencesEntity = helper.createPreferencesEntity(container);
		return preferencesEntity;
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
		assureAddPreferences();
		
		// prepare context
		openEntityManager();
		
		//execute test
		List<PreferencesEntity> records = fixture.getAllPreferencesRecords();
		
		// close context
		closeEntityManager();
		
		//validate results
		openEntityManager();
		verifyPreferencesCount(records, 1);
		closeEntityManager();
	}

	@Test
	public void testGetPreferencesById() throws Exception {
		// prepare environment
		assureDeleteAll();
		assureAddContainer();
		Long id = assureAddPreferences();
		
		// prepare context
		openEntityManager();
		
		// execute test
		PreferencesEntity preferences = fixture.getPreferencesRecordById(id);
		
		// close context
		closeEntityManager();
		
		// validate results
		Assert.notNull(preferences, "Preferences should exist");
		Assert.equals(preferences.getId(), id, "Id should be correct");
	}

	@Test
	public void testGetPreferencesById_Null() throws Exception {
		// prepare environment
		assureDeleteAll();
		assureAddContainer();
		assureAddPreferences();
		
		try {
			// prepare context
			openEntityManager();
		
			// execute test
			fixture.getPreferencesRecordById(null);
			fail("Exception should have been thrown");
		
		} catch (AssertionError e) {
			Assert.exception(e, "Id must be specified");
		
		} finally {
			// close context
			closeEntityManager();
		}
	}

	@Test
	public void testAddPreferences_Commit() throws Exception {
		// prepare environment
		assureDeleteAll();
		assureAddContainer();
		
		// prepare context
		openEntityManager();
		beginTransaction();
		
		//execute test
		PreferencesEntity preferences = createPreferencesEntity();
		Long id = fixture.addPreferencesRecord(preferences);
		Assert.notNull(id, "Id should exist");
		
		// close context
		commitTransaction();
		closeEntityManager();
		
		// validate results
		openEntityManager();
		verifyPreferencesCount(1);
		verifyPreferencesExists(id);
		closeEntityManager();
	}

	@Test
	public void testAddPreferences_Rollback() throws Exception {
		// prepare environment
		assureDeleteAll();
		assureAddContainer();
		
		// prepare context
		openEntityManager();
		beginTransaction();
		
		//execute test
		PreferencesEntity preferences = createPreferencesEntity();
		Long id = fixture.addPreferencesRecord(preferences);
		Assert.notNull(id, "Id should exist");
		
		// close context
		rollbackTransaction();
		closeEntityManager();
		
		// validate results
		openEntityManager();
		verifyPreferencesCount(0);
		closeEntityManager();
	}

	protected Long assureAddContainer() throws Exception {
		container = helper.createUserEntity();
		container.setPreferences(null);
		Long id = assureAddContainer(container);
		container.setId(id);
		return id;
	}

	protected Long assureAddContainer(UserEntity userEntity) throws Exception {
		Long id = helper.persistUserEntity(userEntity);
		return id;
	}

	protected Long assureAddPreferences() throws Exception {
		PreferencesEntity preferencesEntity = createPreferencesEntity();
		Long id = assureAddPreferences(preferencesEntity);
		return id;
	}

	protected Long assureAddPreferences(PreferencesEntity preferences) throws Exception {
		openEntityManager();
		beginTransaction();
		Long id = fixture.addPreferencesRecord(preferences);
		commitTransaction();
		verifyPreferencesExists(id);
		closeEntityManager();
		return id;
	}

	protected void assureDeletePreferences(Long id) throws Exception {
		PreferencesEntity preferences = fixture.getPreferencesRecordById(id);
		assureDeletePreferences(preferences);
	}

	protected void assureDeletePreferences(PreferencesEntity preferences) throws Exception {
		openEntityManager();
		beginTransaction();
		fixture.removePreferencesRecord(preferences);
		commitTransaction();
		verifyPreferencesNotExists(preferences);
		closeEntityManager();
	}

	protected void verifyPreferencesCount(int expectedCount) throws Exception {
		List<PreferencesEntity> records = fixture.getAllPreferencesRecords();
		verifyPreferencesCount(records, expectedCount);
	}

	protected void verifyPreferencesCount(List<PreferencesEntity> records, int expectedCount) throws Exception {
		Assert.notNull(records, "Result not found");
		if (expectedCount > 0)
			Assert.notNull(records, "Preferences records should exist");
		Assert.equals(records.size(), expectedCount, "Preferences record count not correct");
	}

	protected void verifyPreferencesExists(PreferencesEntity preferences) throws Exception {
		verifyPreferencesExists(preferences.getId());
	}

	protected void verifyPreferencesExists(Long id) throws Exception {
		PreferencesEntity preferences = fixture.getPreferencesRecordById(id);
		Assert.notNull(preferences, "Preferences should exist");
		Assert.equals(preferences.getId(), id, "Ids should be same");
	}

	protected void verifyPreferencesNotExists(PreferencesEntity preferences) throws Exception {
		verifyPreferencesNotExists(preferences.getId());
	}

	protected void verifyPreferencesNotExists(Long id) throws Exception {
		PreferencesEntity preferences = fixture.getPreferencesRecordById(id);
		Assert.isNull(preferences, "Preferences should not exist");
	}

}
