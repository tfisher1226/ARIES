package bookshop2.supplier;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.aries.runtime.BeanContext;
import org.aries.tx.DataLayerTestControl;
import org.aries.tx.TransactionTestControl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import bookshop2.supplier.process.BookOrderLogRecordImporter;


@RunWith(MockitoJUnitRunner.class)
public class BookInventoryImportIT {

	private static TransactionTestControl transactionControl;
	
	private static DataLayerTestControl dataLayerControl;
	
	protected static EntityManager entityManagerNew;

	protected static EntityManager entityManagerOld;

	private BookOrderLogRecordImporter fixture;
	

	
	@BeforeClass
	public static void beforeClass() throws Exception {
		createTransactionControl();
		createDataLayerControl();
	}

	protected static void createTransactionControl() throws Exception {
		transactionControl = new TransactionTestControl();
		transactionControl.setupTransactionManager();
	}

	protected static void createDataLayerControl() throws Exception {
		dataLayerControl = new DataLayerTestControl(transactionControl);
		dataLayerControl.setUsername("manager");
		dataLayerControl.setPassword("manager");
		dataLayerControl.setupDataSource("SgiusaTestDB", "SgiusaTestDS");
		EntityManagerFactory emfNew = dataLayerControl.createEntityManagerFactory("SgiusaTestDS");
		EntityManagerFactory emfOld = dataLayerControl.createEntityManagerFactory("SgiusaTestOrigDS");
		entityManagerNew = emfNew.createEntityManager();
		entityManagerOld = emfOld.createEntityManager();
	}
	
//	protected EntityManager getEntityManager() {
//		if (entityManager == null) {
//			EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("SgiusaOrigDS");
//			entityManager = entityManagerFactory.createEntityManager();
//		}
//		return entityManager;
//	}

	@AfterClass
	public static void afterClass() throws Exception {
		dataLayerControl.closeEntityManager(entityManagerNew);
		dataLayerControl.closeEntityManager(entityManagerOld);
	}

	@Before
	public void setUp() throws Exception {
		dataLayerControl.setUp();
		fixture = createFixture();
	}

	@After
	public void tearDown() throws Exception {
		fixture = null;
		dataLayerControl.tearDown();
	}
	
	protected BookOrderLogRecordImporter createFixture() {
		fixture = new BookOrderLogRecordImporter();
		//fixture = getBean("memberImporter");
		//fixture.entityManager = entityManagerOld;
		return fixture;
	}

	public static <T> T getBean(String name) {
		//@SuppressWarnings("unchecked") T instance = (T) Contexts.lookupInStatefulContexts(name);
		@SuppressWarnings("unchecked") T instance = BeanContext.getFromSession(name);
		return instance;
	}

	@Test
	@Ignore
	public void testNothing() throws Exception {
	}

	@Test
	@Ignore
	public void testImportMembers() throws Exception {
		String queryText = "select x from PersonTable x where x.firstName='David' and x.lastName='Osuna'";
		//fixture.importMemberRecords(queryText);
	}

}
