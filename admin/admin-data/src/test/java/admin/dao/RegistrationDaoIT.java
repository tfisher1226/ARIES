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

import admin.entity.RegistrationEntity;
import admin.entity.UserEntity;


@RunWith(MockitoJUnitRunner.class)
public class RegistrationDaoIT extends AbstractDataIT {
	
	private RegistrationDaoImpl fixture;
	
	private AdminDaoITHelper helper;
	
	private UserEntity container;
	
	
	@BeforeClass
	public static void beforeCLass() throws Exception {
		createDataSource("admin_db", "root", "");
		createTransactionManager();
		createNamingServiceContext("admin_ds");
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
	
	protected RegistrationDaoImpl createFixture() throws Exception {
		fixture = new RegistrationDaoImpl();
		fixture.setEntityManager(entityManager);
		return fixture;
	}
	
	protected RegistrationEntity createRegistrationEntity() throws Exception {
		RegistrationEntity registrationEntity = helper.createRegistrationEntity();
		return registrationEntity;
	}
	
	protected void openEntityManager() throws Exception {
		entityManager = createEntityManager();
		fixture.setEntityManager(entityManager);
	}
	
	protected void closeEntityManager() {
		if (entityManager.isOpen())
			entityManager.close();
		fixture.setEntityManager(entityManager);
	}
	
	@Test
	public void testGetElementList() throws Exception {
		// prepare environment
		assureDeleteAll();
		assureAddContainer();
		assureAddRegistration();
		
		// prepare context
		openEntityManager();
		
		//execute test
		List<RegistrationEntity> records = fixture.getAllRegistrationRecords();
		
		// close context
		closeEntityManager();
		
		//validate results
		openEntityManager();
		verifyRegistrationCount(records, 1);
		closeEntityManager();
	}
	
	@Test
	public void testGetRegistrationById() throws Exception {
		// prepare environment
		assureDeleteAll();
		assureAddContainer();
		Long id = assureAddRegistration();
		
		// prepare context
		openEntityManager();
		
		// execute test
		RegistrationEntity registration = fixture.getRegistrationRecordById(id);
		
		// close context
		closeEntityManager();
		
		// validate results
		Assert.notNull(registration, "Registration should exist");
		Assert.equals(registration.getId(), id, "Id should be correct");
	}
	
	@Test
	public void testGetRegistrationById_Null() throws Exception {
		// prepare environment
		assureDeleteAll();
		assureAddContainer();
		assureAddRegistration();
		
		try {
			// prepare context
			openEntityManager();
		
			// execute test
			fixture.getRegistrationRecordById(null);
			fail("Exception should have been thrown");
		
		} catch (AssertionError e) {
			Assert.exception(e, "Id must be specified");
		
		} finally {
			// close context
			closeEntityManager();
		}
	}
	
	@Test
	public void testAddRegistration_Commit() throws Exception {
		// prepare environment
		assureDeleteAll();
		assureAddContainer();
		
		// prepare context
		openEntityManager();
		beginTransaction();
		
		//execute test
		RegistrationEntity registration = createRegistrationEntity();
		Long id = fixture.addRegistrationRecord(registration);
		Assert.notNull(id, "Id should exist");
		
		// close context
		commitTransaction();
		closeEntityManager();
		
		// validate results
		openEntityManager();
		verifyRegistrationCount(1);
		verifyRegistrationExists(id);
		closeEntityManager();
	}
	
	@Test
	public void testAddRegistration_Rollback() throws Exception {
		// prepare environment
		assureDeleteAll();
		assureAddContainer();
		
		// prepare context
		openEntityManager();
		beginTransaction();
		
		//execute test
		RegistrationEntity registration = createRegistrationEntity();
		Long id = fixture.addRegistrationRecord(registration);
		Assert.notNull(id, "Id should exist");
		
		// close context
		rollbackTransaction();
		closeEntityManager();
		
		// validate results
		openEntityManager();
		verifyRegistrationCount(0);
		closeEntityManager();
	}
	
	protected Long assureAddContainer() throws Exception {
		container = helper.createUserEntity();
		Long id = assureAddContainer(container);
		container.setId(id);
		return id;
	}
	
	protected Long assureAddContainer(UserEntity userEntity) throws Exception {
		Long id = helper.persistUserEntity(userEntity);
		return id;
	}
	
	protected Long assureAddRegistration() throws Exception {
		RegistrationEntity registrationEntity = createRegistrationEntity();
		Long id = assureAddRegistration(registrationEntity);
		return id;
	}
	
	protected Long assureAddRegistration(RegistrationEntity registration) throws Exception {
		openEntityManager();
		beginTransaction();
		Long id = fixture.addRegistrationRecord(registration);
		commitTransaction();
		verifyRegistrationExists(id);
		closeEntityManager();
		return id;
	}
	
	protected void assureDeleteRegistration(Long id) throws Exception {
		RegistrationEntity registration = fixture.getRegistrationRecordById(id);
		assureDeleteRegistration(registration);
	}
	
	protected void assureDeleteRegistration(RegistrationEntity registration) throws Exception {
		openEntityManager();
		beginTransaction();
		fixture.removeRegistrationRecord(registration);
		commitTransaction();
		verifyRegistrationNotExists(registration);
		closeEntityManager();
	}
	
	protected void verifyRegistrationCount(int expectedCount) throws Exception {
		List<RegistrationEntity> records = fixture.getAllRegistrationRecords();
		verifyRegistrationCount(records, expectedCount);
	}
	
	protected void verifyRegistrationCount(List<RegistrationEntity> records, int expectedCount) throws Exception {
		Assert.notNull(records, "Result not found");
		if (expectedCount > 0)
			Assert.notNull(records, "Registration records should exist");
		Assert.equals(records.size(), expectedCount, "Registration record count not correct");
	}
	
	protected void verifyRegistrationExists(RegistrationEntity registration) throws Exception {
		verifyRegistrationExists(registration.getId());
	}
	
	protected void verifyRegistrationExists(Long id) throws Exception {
		RegistrationEntity registration = fixture.getRegistrationRecordById(id);
		Assert.notNull(registration, "Registration should exist");
		Assert.equals(registration.getId(), id, "Ids should be same");
	}
	
	protected void verifyRegistrationNotExists(RegistrationEntity registration) throws Exception {
		verifyRegistrationNotExists(registration.getId());
	}
	
	protected void verifyRegistrationNotExists(Long id) throws Exception {
		RegistrationEntity registration = fixture.getRegistrationRecordById(id);
		Assert.isNull(registration, "Registration should not exist");
	}
	
}
