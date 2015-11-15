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

import admin.dao.SkinDaoImpl;
import admin.entity.SkinEntity;


@RunWith(MockitoJUnitRunner.class)
public class SkinDaoIT extends AbstractDataIT {

	private SkinDaoImpl<SkinEntity> fixture;

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

	protected SkinDaoImpl<SkinEntity> createFixture() throws Exception {
		fixture = new SkinDaoImpl<SkinEntity>();
		fixture.setEntityClass(SkinEntity.class);
		fixture.setEntityManager(entityManager);
		return fixture;
	}

	protected SkinEntity createSkinEntity() throws Exception {
		SkinEntity skinEntity = helper.createSkinEntity();
		return skinEntity;
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
		assureAddSkin();
		
		// prepare context
		openEntityManager();
		
		//execute test
		List<SkinEntity> records = fixture.getAllSkinRecords();
		
		// close context
		closeEntityManager();
		
		//validate results
		openEntityManager();
		verifySkinCount(records, 1);
		closeEntityManager();
	}

	@Test
	public void testGetSkinById() throws Exception {
		// prepare environment
		assureDeleteAll();
		Long id = assureAddSkin();
		
		// prepare context
		openEntityManager();
		
		// execute test
		SkinEntity skin = fixture.getSkinRecordById(id);
		
		// close context
		closeEntityManager();
		
		// validate results
		Assert.notNull(skin, "Skin should exist");
		Assert.equals(skin.getId(), id, "Id should be correct");
	}

	@Test
	public void testGetSkinById_Null() throws Exception {
		// prepare environment
		assureDeleteAll();
		assureAddSkin();
		
		try {
			// prepare context
			openEntityManager();
		
			// execute test
			fixture.getSkinRecordById(null);
			fail("Exception should have been thrown");
		
		} catch (AssertionError e) {
			Assert.exception(e, "Id must be specified");
		
		} finally {
			// close context
			closeEntityManager();
		}
	}

	@Test
	public void testGetSkinByName() throws Exception {
		// prepare environment
		assureDeleteAll();
		Long id = assureAddSkin();
		
		// prepare context
		openEntityManager();
		
		// execute test
		int index = 0;
		String name = "dummyName" + index;
		SkinEntity skin = fixture.getSkinRecordByName(name);
		
		// close context
		closeEntityManager();
		
		// validate results
		Assert.notNull(skin, "Skin should exist");
		Assert.equals(skin.getName(), name, "Name should be correct");
	}

	@Test
	public void testGetSkinByName_Null() throws Exception {
		// prepare environment
		assureDeleteAll();
		assureAddSkin();
		
		try {
			// prepare context
			openEntityManager();
		
			// execute test
			fixture.getSkinRecordByName(null);
			fail("Exception should have been thrown");
		
		} catch (AssertionError e) {
			Assert.exception(e, "Name must be specified");
		
		} finally {
			// close context
			closeEntityManager();
		}
	}

	@Test
	public void testAddSkin_Commit() throws Exception {
		// prepare environment
		assureDeleteAll();
		
		// prepare context
		openEntityManager();
		beginTransaction();
		
		//execute test
		SkinEntity skin = createSkinEntity();
		Long id = fixture.addSkinRecord(skin);
		Assert.notNull(id, "Id should exist");
		
		// close context
		commitTransaction();
		closeEntityManager();
		
		// validate results
		openEntityManager();
		verifySkinCount(1);
		verifySkinExists(id);
		closeEntityManager();
	}

	@Test
	public void testAddSkin_Rollback() throws Exception {
		// prepare environment
		assureDeleteAll();
		
		// prepare context
		openEntityManager();
		beginTransaction();
		
		//execute test
		SkinEntity skin = createSkinEntity();
		Long id = fixture.addSkinRecord(skin);
		Assert.notNull(id, "Id should exist");
		
		// close context
		rollbackTransaction();
		closeEntityManager();
		
		// validate results
		openEntityManager();
		verifySkinCount(0);
		closeEntityManager();
	}

	protected Long assureAddSkin() throws Exception {
		SkinEntity skinEntity = createSkinEntity();
		Long id = assureAddSkin(skinEntity);
		return id;
	}

	protected Long assureAddSkin(SkinEntity skin) throws Exception {
		openEntityManager();
		beginTransaction();
		Long id = fixture.addSkinRecord(skin);
		commitTransaction();
		verifySkinExists(id);
		closeEntityManager();
		return id;
	}

	protected void assureDeleteSkin(Long id) throws Exception {
		SkinEntity skin = fixture.getSkinRecordById(id);
		assureDeleteSkin(skin);
	}

	protected void assureDeleteSkin(SkinEntity skin) throws Exception {
		openEntityManager();
		beginTransaction();
		fixture.removeSkinRecord(skin);
		commitTransaction();
		verifySkinNotExists(skin);
		closeEntityManager();
	}

	protected void verifySkinCount(int expectedCount) throws Exception {
		List<SkinEntity> records = fixture.getAllSkinRecords();
		verifySkinCount(records, expectedCount);
	}

	protected void verifySkinCount(List<SkinEntity> records, int expectedCount) throws Exception {
		Assert.notNull(records, "Result not found");
		if (expectedCount > 0)
			Assert.notNull(records, "Skin records should exist");
		Assert.equals(records.size(), expectedCount, "Skin record count not correct");
	}

	protected void verifySkinExists(SkinEntity skin) throws Exception {
		verifySkinExists(skin.getId());
	}

	protected void verifySkinExists(Long id) throws Exception {
		SkinEntity skin = fixture.getSkinRecordById(id);
		Assert.notNull(skin, "Skin should exist");
		Assert.equals(skin.getId(), id, "Ids should be same");
	}

	protected void verifySkinNotExists(SkinEntity skin) throws Exception {
		verifySkinNotExists(skin.getId());
	}

	protected void verifySkinNotExists(Long id) throws Exception {
		SkinEntity skin = fixture.getSkinRecordById(id);
		Assert.isNull(skin, "Skin should not exist");
	}

}
