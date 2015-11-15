package bookshop2.supplier.entity;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

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

import bookshop2.supplier.util.SupplierEntityFixture;


@RunWith(MockitoJUnitRunner.class)
public class ReservedBooksEntityIT {

	private static TransactionTestControl transactionControl;
	
	private static DataLayerTestControl bookInventoryControl;

	private ReservedBooksEntity reservedBooksEntity;
	
	private EntityManager entityManager;


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
		bookInventoryControl = new DataLayerTestControl(transactionControl);
		bookInventoryControl.setDatabaseName("bookshop2_supplier_db");
		bookInventoryControl.setDataSourceName("bookshop2_supplier_ds");
		bookInventoryControl.setPersistenceUnitName("bookInventory");
		bookInventoryControl.setupDataLayer();
	}
	
	@AfterClass
	public static void afterCLass() throws Exception {
		transactionControl.tearDownTransactionManager();
		bookInventoryControl.tearDownPersistenceUnit();
	}

	@Before
	public void setUp() throws Exception {
		bookInventoryControl.setUp();
		createEntityManager();
		//createHelper();
		removeAll();
	}

	@After
	public void tearDown() throws Exception {
		entityManager.close();
		transactionControl.tearDown();
		bookInventoryControl.tearDown();
		reservedBooksEntity = null;
	}
	
	protected void createEntityManager() throws Exception {
		entityManager = bookInventoryControl.createEntityManager();
	}
	
	protected ReservedBooksEntity createReservedBooksEntity() {
		return SupplierEntityFixture.create_ReservedBooksEntity();
	}
	
	protected ReservedBooksEntity createEmptyReservedBooksEntity() {
		return SupplierEntityFixture.createEmpty_ReservedBooksEntity();
	}
	
	@Test
	@Ignore
	public void testGetUserByName() throws Exception {
		//what to do here?
	}

	@Test
	@Ignore
	public void testSaveUser() throws Exception {
		EntityManager entityManager = bookInventoryControl.createEntityManager();
		ReservedBooksEntity user = entityManager.find(ReservedBooksEntity.class, 1L);
		//RoleEntity role = entityManager.find(RoleEntity.class, 1L);
		//user.getRoles().add(role);
		entityManager.persist(user);
		System.out.println();
	}
	
	protected void openTransaction() throws Exception {
		transactionControl.beginTransaction();
		entityManager.joinTransaction();
	}

	protected void closeTransaction() throws Exception {
		transactionControl.commitTransaction();
		transactionControl.clearTransactions();
		//entityManager.close();
	}
	
	protected void removeAll() throws Exception {
		try {
			openTransaction();
			
			Query query = entityManager.createNamedQuery("getAllReservedBooksRecords");
			@SuppressWarnings("unchecked") List<ReservedBooksEntity> records = query.getResultList();
			
			Iterator<ReservedBooksEntity> iterator = records.iterator();
			while (iterator.hasNext()) {
				ReservedBooksEntity record = iterator.next();
				record = entityManager.merge(record);
				entityManager.remove(record);
			}
			
		} finally {
			closeTransaction();
		}
	}
	
}
