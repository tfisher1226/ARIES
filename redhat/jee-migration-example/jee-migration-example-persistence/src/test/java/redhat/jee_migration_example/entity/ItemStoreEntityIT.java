package redhat.jee_migration_example.entity;

import static org.junit.Assert.fail;
import static org.junit.runners.MethodSorters.NAME_ASCENDING;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.aries.Assert;
import org.aries.tx.DataLayerTestControl;
import org.aries.tx.TransactionTestControl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import redhat.jee_migration_example.ItemInventoryHelper;
import redhat.jee_migration_example.util.JeeMigrationExampleEntityFixture;


@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(NAME_ASCENDING)
public class ItemStoreEntityIT {
	
	private static TransactionTestControl transactionControl;
	
	private static DataLayerTestControl itemInventoryControl;
	
	private ItemInventoryHelper itemInventoryHelper;
	
	private ItemStoreEntity itemStoreEntity;
	
	private EntityManager entityManager;
	
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		createTransactionControl();
		createItemInventoryControl();
	}
	
	@AfterClass
	public static void afterCLass() throws Exception {
		transactionControl.tearDownTransactionManager();
		itemInventoryControl.tearDownPersistenceUnit();
	}
	
	protected static void createTransactionControl() throws Exception {
		transactionControl = new TransactionTestControl();
		transactionControl.setupTransactionManager();
	}
	
	protected static void createItemInventoryControl() throws Exception {
		itemInventoryControl = new DataLayerTestControl(transactionControl);
		itemInventoryControl.setDatabaseName("redhat_jee_migration_example_db");
		itemInventoryControl.setDataSourceName("redhat_jee_migration_example");
		itemInventoryControl.setPersistenceUnitName("itemInventory");
		itemInventoryControl.setupDataLayer();
	}
	
	@Before
	public void setUp() throws Exception {
		itemInventoryControl.setUp();
		createItemInventoryHelper();
		createEntityManager();
		removeAll();
	}
	
	@After
	public void tearDown() throws Exception {
		entityManager.close();
		transactionControl.tearDown();
		itemInventoryControl.tearDown();
		itemInventoryHelper = null;
		itemStoreEntity = null;
	}
	
	protected void createItemInventoryHelper() throws Exception {
		itemInventoryHelper = new ItemInventoryHelper();
		itemInventoryHelper.initialize(itemInventoryControl);
	}
	
	protected void createEntityManager() throws Exception {
		entityManager = itemInventoryControl.createEntityManager();
	}
	
	protected ItemStoreEntity createItemStoreEntity() {
		return itemInventoryHelper.createItemStoreEntity();
	}
	
	protected ItemStoreEntity createEmptyItemStoreEntity() {
		return itemInventoryHelper.createEmptyItemStoreEntity();
	}
	
	@Test
	//@Ignore
	public void runTest_ItemStore_AddAsItem() throws Exception {
		itemStoreEntity = createItemStoreEntity();
		
		try {
			openTransaction();
			itemStoreEntity = entityManager.merge(itemStoreEntity);
			entityManager.flush();
			commitTransaction();
			
			// validate results
			Long id = itemStoreEntity.getId();
			ItemStoreEntity entity = entityManager.find(ItemStoreEntity.class, id);
			Assert.equals(entity, itemStoreEntity, "ItemStore records should be equal");
			
		} catch (Exception e) {
			Assert.exception(e, "Exception should not occur");
			
		} finally {
			commitTransaction();
		}
	}
	
	@Test
	//@Ignore
	public void runTest_ItemStore_AddAsItem_constraint_Id_null() throws Exception {
		itemStoreEntity = createItemStoreEntity();
		itemStoreEntity.setId(null);
		
		try {
			openTransaction();
			itemStoreEntity = entityManager.merge(itemStoreEntity);
			//entityManager.refresh(itemStoreEntity);
			//fail("Exception should have occured");
			
		} finally {
			commitTransaction();
		}

		//No exception, Id is not required
		Assert.notNull(itemStoreEntity.getId(), "ItemStore record Id should be exist");
	}
	
	@Test
	//@Ignore
	public void runTest_ItemStore_AddAsItem_exception_entityNotManaged() throws Exception {
		itemStoreEntity = createItemStoreEntity();
		
		try {
			openTransaction();
			entityManager.refresh(itemStoreEntity);
			fail("Exception should have occured");
			
		} catch (Exception e) {
			Assert.exception(e, "Entity not managed");
			
		} finally {
			commitTransaction();
		}
	}
	
	@Test
	//@Ignore
	public void runTest_ItemStore_AddAsItem_exception_detachedEntity() throws Exception {
		itemStoreEntity = createItemStoreEntity();
		
		try {
			openTransaction();
			entityManager.persist(itemStoreEntity);
			fail("Exception should have occured");
			
		} catch (Exception e) {
			Assert.exception(e, "detached entity passed to persist");
			
		} finally {
			commitTransaction();
		}
	}
	
	@Test
	//@Ignore
	public void runTest_ItemStore_SaveAsItem() throws Exception {
		// prepare environment 
		itemStoreEntity = createItemStoreEntity();
		Long id = addItemStoreRecord(itemStoreEntity);
		ItemStoreEntity entity = getItemStoreRecord(id);
		//Assert.equals(itemStoreEntity, entity, "ItemStore records should be equal");
		JeeMigrationExampleEntityFixture.assertSameItemStoreEntity(itemStoreEntity, entity);
		
		try {
			// modify record
			entity.setName("newName");
			
			// save record
			openTransaction();
			itemStoreEntity = entityManager.merge(entity);
			entityManager.flush();
			
		} finally {
			commitTransaction();
		}
		
		// validate results
		ItemStoreEntity entity2 = getItemStoreRecord(id);
		Assert.equals(entity, entity2, "ItemStore records should be equal");
	}
	
	@Test
	//@Ignore
	public void runTest_ItemStore_SaveAsItem_constraint_Id_null() throws Exception {
		// prepare environment 
		itemStoreEntity = createItemStoreEntity();
		Long id = addItemStoreRecord(itemStoreEntity);
		
		try {
			// lookup record
			ItemStoreEntity entity = getItemStoreRecord(id);
			JeeMigrationExampleEntityFixture.assertSameItemStoreEntity(itemStoreEntity, entity);
			
			// modify record
			entity.setId(null);
			
			// save record
			openTransaction();
			entityManager.merge(entity);
			entityManager.flush();
			fail("Exception should have occured");
			
		} catch (Exception e) {
			Assert.exception(e, "identifier of an instance");
			Assert.exception(e, "was altered from "+id+" to null");
			//Assert.exception(e, "Column 'id' cannot be null");
			
		} finally {
			rollbackTransaction();
		}
		
		// validate results
		ItemStoreEntity entity = getItemStoreRecord(id);
		Assert.notNull(entity.getId(), "ItemStore Id should exist");
	}
	
	@Test
	//@Ignore
	public void runTest_ItemStore_SaveAsItem_constraint_Id_cannotUpdate() throws Exception {
		// prepare environment 
		itemStoreEntity = createItemStoreEntity();
		Long id = addItemStoreRecord(itemStoreEntity);
		ItemStoreEntity entity = getItemStoreRecord(id);
		//Assert.equals(itemStoreEntity, entity, "ItemStore records should be equal");
		JeeMigrationExampleEntityFixture.assertSameItemStoreEntity(itemStoreEntity, entity);
		
		try {
			// modify record
			entity.setId(111L);
			
			// save record
			openTransaction();
			itemStoreEntity = entityManager.merge(entity);
			entityManager.flush();
			
		} catch (Exception e) {
			Assert.exception(e, "identifier of an instance");
			Assert.exception(e, "was altered from "+id+" to 111");
			
		} finally {
			commitTransaction();
		}
		
		// validate results
		ItemStoreEntity entity2 = getItemStoreRecord(id);
		Assert.equals(entity, entity2, "ItemStore records should be equal");
	}
	
	@Test
	//@Ignore
	public void runTest_ItemStore_SaveAsItem_exception_entityNotManaged() throws Exception {
		// prepare environment 
		itemStoreEntity = createItemStoreEntity();
		addItemStoreRecord(itemStoreEntity);
		entityManager.clear();
		
		try {
			// execute action
			openTransaction();
			entityManager.refresh(itemStoreEntity);
			fail("Exception should have occured");
			
		} catch (Exception e) {
			Assert.exception(e, "Entity not managed");
			
		} finally {
			rollbackTransaction();
		}
	}
	
	@Test
	//@Ignore
	public void runTest_ItemStore_SaveAsItem_exception_detachedEntity() throws Exception {
		// prepare environment 
		itemStoreEntity = createItemStoreEntity();
		Long id = addItemStoreRecord(itemStoreEntity);
		entityManager.detach(itemStoreEntity);
		
		try {
			// execute action
			openTransaction();
			entityManager.persist(itemStoreEntity);
			fail("Exception should have occured");
			
		} catch (Exception e) {
			Assert.exception(e, "detached entity passed to persist");
			
		} finally {
			rollbackTransaction();
		}
	}
	
	@Test
	//@Ignore
	public void runTest_ItemStore_RemoveAsItem() throws Exception {
		// prepare environment 
		openTransaction();
		itemStoreEntity = createItemStoreEntity();
		itemStoreEntity = entityManager.merge(itemStoreEntity);
		entityManager.persist(itemStoreEntity);
		commitTransaction();
		
		try {
			// lookup record
			Long id = itemStoreEntity.getId();
			ItemStoreEntity entity = entityManager.find(ItemStoreEntity.class, id);
			Assert.equals(itemStoreEntity, entity, "ItemStore records should be equal");
			
			// remove record
			openTransaction();
			entityManager.remove(itemStoreEntity);
			entityManager.flush();
			commitTransaction();
			
			// verify results
			entity = entityManager.find(ItemStoreEntity.class, id);
			Assert.isNull(entity, "ItemStore record should be removed");
			Assert.isTrue(getAllItemStoreRecords().size() == 0, "No ItemStore record should exist");
			
		} catch (Exception e) {
			fail("Exception should not occur: "+e.getMessage());
			
		} finally {
			commitTransaction();
		}
	}
	
	@Test
	//@Ignore
	public void runTest_ItemStore_RemoveAsList() throws Exception {
		// prepare environment 
		openTransaction();
		itemStoreEntity = createItemStoreEntity();
		itemStoreEntity = entityManager.merge(itemStoreEntity);
		entityManager.persist(itemStoreEntity);
		commitTransaction();
		
		try {
			// lookup record
			Long id = itemStoreEntity.getId();
			ItemStoreEntity entity = entityManager.find(ItemStoreEntity.class, id);
			Assert.equals(itemStoreEntity, entity, "ItemStore records should be equal");
			
			// remove record
			openTransaction();
			entityManager.remove(itemStoreEntity);
			entityManager.flush();
			commitTransaction();
			
			// verify results
			entity = entityManager.find(ItemStoreEntity.class, id);
			Assert.isNull(entity, "ItemStore record should be removed");
			Assert.isTrue(getAllItemStoreRecords().size() == 0, "No ItemStore record should exist");
			
		} catch (Exception e) {
			fail("Exception should not occur: "+e.getMessage());
			
		} finally {
			commitTransaction();
		}
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
	
	protected void commitTransaction() throws Exception {
		transactionControl.commitTransaction();
		transactionControl.clearTransactions();
	}
	
	protected void rollbackTransaction() throws Exception {
		transactionControl.rollbackTransaction();
		transactionControl.clearTransactions();
	}

	protected void clearTransaction() throws Exception {
		transactionControl.clearTransactions();
	}
	
	
	
	protected Collection<ItemStoreEntity> getAllItemStoreRecords() {
		return getAllRecords("ItemStore");
	}
	
	protected <T> Collection<T> getAllRecords(String tableName) {
		Query query = entityManager.createQuery("select x FROM "+tableName+" x");
		@SuppressWarnings("unchecked") Collection<T> resultList = (Collection<T>) query.getResultList();
		return resultList;
	}
	
	protected ItemStoreEntity getItemStoreRecord(Long id) {
		//BookOrdersEntity entity = entityManager.find(BookOrdersEntity.class, id, LockModeType.NONE);
		ItemStoreEntity entity = entityManager.find(ItemStoreEntity.class, id);
		return entity;
	}
	
	protected Long addItemStoreRecord(ItemStoreEntity entity) throws Exception {
		try {
			entity = saveItemStoreRecord(entity);
			entityManager.refresh(entity);
			Long id = entity.getId();
			return id;
		} finally {
			commitTransaction();
		}
	}

	protected ItemStoreEntity saveItemStoreRecord(ItemStoreEntity entity) throws Exception {
		try {
			openTransaction();
			entity = entityManager.merge(entity);
			//entityManager.refresh(entity);
			entityManager.flush();
			return entity;
		} finally {
			commitTransaction();
		}
	}

	
	protected void removeAll() throws Exception {
		try {
			openTransaction();
			
			Query query = entityManager.createNamedQuery("getAllItemStoreRecords");
			@SuppressWarnings("unchecked") List<ItemStoreEntity> records = query.getResultList();
			
			Iterator<ItemStoreEntity> iterator = records.iterator();
			while (iterator.hasNext()) {
				ItemStoreEntity record = iterator.next();
				record = entityManager.merge(record);
				entityManager.remove(record);
			}
			
		} finally {
			closeTransaction();
		}
	}
	
}
