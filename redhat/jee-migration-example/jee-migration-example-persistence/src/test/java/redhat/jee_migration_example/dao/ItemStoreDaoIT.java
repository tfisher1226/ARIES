package redhat.jee_migration_example.dao;

import static org.junit.Assert.fail;
import static org.junit.runners.MethodSorters.NAME_ASCENDING;

import java.util.Collection;

import javax.persistence.EntityManager;

import org.aries.Assert;
import org.aries.AssertionFailure;
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
import redhat.jee_migration_example.entity.ItemStoreEntity;
import redhat.jee_migration_example.util.JeeMigrationExampleEntityFixture;


@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(NAME_ASCENDING)
public class ItemStoreDaoIT {
	
	private static TransactionTestControl transactionControl;
	
	private static DataLayerTestControl itemInventoryControl;
	
	private ItemInventoryHelper itemInventoryHelper;
	
	private ItemDao<ItemStoreEntity> itemStoreDao;
	
	
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
		createItemStoreDao();
	}
	
	@After
	public void tearDown() throws Exception {
		transactionControl.tearDown();
		itemInventoryControl.tearDown();
		itemInventoryHelper = null;
		itemStoreDao = null;
	}
	
	protected void createItemInventoryHelper() throws Exception {
		itemInventoryHelper = new ItemInventoryHelper();
		itemInventoryHelper.initialize(itemInventoryControl);
	}
	
	protected void createItemStoreDao() throws Exception {
		itemStoreDao = itemInventoryHelper.createItemStoreDao();
		itemStoreDao.setEntityManager(createEntityManager());
	}
	
	protected EntityManager createEntityManager() throws Exception {
		return itemInventoryControl.createEntityManager();
	}
	
	protected ItemStoreEntity createItemStoreEntity() {
		return itemInventoryHelper.createItemStoreEntity();
	}
	
	protected ItemStoreEntity createEmptyItemStoreEntity() {
		return itemInventoryHelper.createEmptyItemStoreEntity();
	}
	
	@Test
	public void runTest_ItemStore_GetAllRecords() throws Exception {
		// prepare environment
		itemInventoryHelper.assureRemoveAll();
		itemInventoryHelper.addItemStore();
		
		// execute action
		Collection<ItemStoreEntity> records = itemStoreDao.getAllItemRecords();
		
		//validate results
		itemInventoryHelper.verifyItemStoreCount(records, 1);
	}
	
	@Test
	public void runTest_ItemStore_GetRecordById() throws Exception {
		// prepare environment
		itemInventoryHelper.assureRemoveAll();
		Long id = itemInventoryHelper.addItemStore();
		
		// execute test
		ItemStoreEntity itemStore = itemStoreDao.getItemRecordById(id);
		
		// validate results
		Assert.notNull(itemStore, "ItemStore should exist");
		Assert.equals(itemStore.getId(), id, "Id should be correct");
	}
	
	@Test
	public void runTest_ItemStore_GetRecordById_null() throws Exception {
		// prepare environment
		itemInventoryHelper.assureRemoveAll();
		itemInventoryHelper.addItemStore();
		
		try {
			// execute test
			itemStoreDao.getItemRecordById(null);
			fail("Exception should have been thrown");
		
		} catch (Exception e) {
			assertAssertionFailure(e, "Id must be specified");
		}
	}
	
	@Test
	public void runTest_ItemStore_AddAsItem() throws Exception {
		// prepare environment
		itemInventoryHelper.assureRemoveAll();
		
		// execute action
		ItemStoreEntity itemStore = createItemStoreEntity();
		Long id = itemStoreDao.addItemRecord(itemStore);
		Assert.notNull(id, "Id should exist");
		
		// validate results
		itemInventoryHelper.verifyItemStoreCount(1);
		itemInventoryHelper.verifyItemStoreExists(id);
	}
	
	@Test
	public void runTest_ItemStore_AddAsItem_commit() throws Exception {
		// prepare environment
		itemInventoryHelper.assureRemoveAll();
		
		// prepare context
		transactionControl.beginTransaction();
		
		// execute action
		ItemStoreEntity itemStore = createItemStoreEntity();
		Long id = itemStoreDao.addItemRecord(itemStore);
		Assert.notNull(id, "Id should exist");
		
		// close context
		transactionControl.commitTransaction();
		
		// validate results
		itemInventoryHelper.verifyItemStoreCount(1);
		itemInventoryHelper.verifyItemStoreExists(id);
	}
	
	@Test
	public void runTest_ItemStore_AddAsItem_rollback() throws Exception {
		// prepare environment
		itemInventoryHelper.assureRemoveAll();
		
		// prepare context
		transactionControl.beginTransaction();
		
		// execute action
		ItemStoreEntity itemStore = createItemStoreEntity();
		Long id = itemStoreDao.addItemRecord(itemStore);
		Assert.notNull(id, "Id should exist");
		
		// close context
		transactionControl.rollbackTransaction();
		
		// validate results
		itemInventoryHelper.verifyItemStoreCount(0);
	}
	
	@Test
	public void runTest_ItemStore_AddAsItem_utx_commit() throws Exception {
		// prepare environment
		itemInventoryHelper.assureRemoveAll();
		
		// prepare context
		transactionControl.beginUserTransaction();
		
		// execute action
		ItemStoreEntity itemStore = createItemStoreEntity();
		Long id = itemStoreDao.addItemRecord(itemStore);
		Assert.notNull(id, "Id should exist");
		
		// close context
		transactionControl.commitUserTransaction();
		
		// validate results
		itemInventoryHelper.verifyItemStoreCount(1);
		itemInventoryHelper.verifyItemStoreExists(id);
	}
	
	@Test
	public void runTest_ItemStore_AddAsItem_utx_rollback() throws Exception {
		// prepare environment
		itemInventoryHelper.assureRemoveAll();
		
		// prepare context
		transactionControl.beginUserTransaction();
		
		// execute action
		ItemStoreEntity itemStore = createItemStoreEntity();
		Long id = itemStoreDao.addItemRecord(itemStore);
		Assert.notNull(id, "Id should exist");
		
		// close context
		transactionControl.rollbackUserTransaction();
		
		// validate results
		itemInventoryHelper.verifyItemStoreCount(0);
	}
	
	@Test
	public void runTest_ItemStore_AddAsItem_exception_id_null() throws Exception {
		// prepare environment
		itemInventoryHelper.assureRemoveAll();
		
		// prepare context
		transactionControl.beginTransaction();
		
		// execute action
		ItemStoreEntity itemStoreEntity = createItemStoreEntity();
		itemStoreEntity.setId(null);
		itemStoreDao.addItemRecord(itemStoreEntity);
		
		// close context
		transactionControl.commitTransaction();
		
		// validate results
		itemInventoryHelper.verifyItemStoreCount(1);
		//No exception, Id is not required
		Assert.notNull(itemStoreEntity.getId(), "ItemStore record Id should be exist");
	}
	
	@Test
	public void runTest_ItemStore_AddAsItem_exception_name_null() throws Exception {
		// prepare environment
		itemInventoryHelper.assureRemoveAll();
		
		// prepare context
		transactionControl.beginTransaction();
		
		try {
			// execute action
			ItemStoreEntity itemStore = createItemStoreEntity();
			itemStore.setName(null);
			itemStoreDao.addItemRecord(itemStore);
			fail("Exception should have been thrown");
		
		} catch (Exception e) {
			assertConstraintViolation(e, "Column 'name' cannot be null");
		}
		
		// close context
		transactionControl.rollbackTransaction();
		
		// validate results
		itemInventoryHelper.verifyItemStoreCount(0);
	}
	
	@Test
	public void runTest_ItemStore_AddAsList() throws Exception {
		// prepare environment
		itemInventoryHelper.assureRemoveAll();
		
		// execute action
		Collection<ItemStoreEntity> itemStoreList = itemInventoryHelper.createItemStoreEntityList();
		Collection<Long> idList = itemStoreDao.addItemRecords(itemStoreList);
		Assert.notNull(idList, "Id list should exist");
		Assert.equals(idList.size(), itemStoreList.size(), "Id list should have correct length");
		
		// validate results
		itemInventoryHelper.verifyItemStoreCount(idList.size());
		itemInventoryHelper.verifyItemStoreListExists(idList);
		Collection<ItemStoreEntity> itemStoreList2 = itemInventoryHelper.getItemStoreRecordsByIds(idList);
		JeeMigrationExampleEntityFixture.assertSameItemStoreEntity(itemStoreList, itemStoreList2, true);
	}
	
	@Test
	public void runTest_ItemStore_AddAsList_commit() throws Exception {
		// prepare environment
		itemInventoryHelper.assureRemoveAll();
		
		// prepare context
		transactionControl.beginTransaction();
		
		// execute action
		Collection<ItemStoreEntity> itemStoreList = itemInventoryHelper.createItemStoreEntityList();
		Collection<Long> idList = itemStoreDao.addItemRecords(itemStoreList);
		Assert.notNull(idList, "Id list should exist");
		Assert.equals(idList.size(), itemStoreList.size(), "Id list should have correct length");
		
		// close context
		transactionControl.commitTransaction();
		
		// validate results
		itemInventoryHelper.verifyItemStoreCount(idList.size());
		itemInventoryHelper.verifyItemStoreListExists(idList);
		Collection<ItemStoreEntity> itemStoreList2 = itemInventoryHelper.getItemStoreRecordsByIds(idList);
		JeeMigrationExampleEntityFixture.assertSameItemStoreEntity(itemStoreList, itemStoreList2, true);
	}
	
	@Test
	public void runTest_ItemStore_AddAsList_rollback() throws Exception {
		// prepare environment
		itemInventoryHelper.assureRemoveAll();
		
		// prepare context
		transactionControl.beginTransaction();
		
		// execute action
		Collection<ItemStoreEntity> itemStoreList = itemInventoryHelper.createItemStoreEntityList();
		Collection<Long> idList = itemStoreDao.addItemRecords(itemStoreList);
		Assert.notNull(idList, "Id list should exist");
		Assert.equals(idList.size(), itemStoreList.size(), "Id list should have correct length");
		
		// close context
		transactionControl.rollbackTransaction();
		
		// validate results
		itemInventoryHelper.verifyItemStoreCount(0);
	}
	
	@Test
	public void runTest_ItemStore_AddAsList_utx_commit() throws Exception {
		// prepare environment
		itemInventoryHelper.assureRemoveAll();
		
		// prepare context
		transactionControl.beginUserTransaction();
		
		// execute action
		Collection<ItemStoreEntity> itemStoreList = itemInventoryHelper.createItemStoreEntityList();
		Collection<Long> idList = itemStoreDao.addItemRecords(itemStoreList);
		Assert.notNull(idList, "Id list should exist");
		Assert.equals(idList.size(), itemStoreList.size(), "Id list should have correct length");
		
		// close context
		transactionControl.commitUserTransaction();
		
		// validate results
		itemInventoryHelper.verifyItemStoreCount(idList.size());
		itemInventoryHelper.verifyItemStoreListExists(idList);
		Collection<ItemStoreEntity> itemStoreList2 = itemInventoryHelper.getItemStoreRecordsByIds(idList);
		JeeMigrationExampleEntityFixture.assertSameItemStoreEntity(itemStoreList, itemStoreList2, true);
	}
	
	@Test
	public void runTest_ItemStore_AddAsList_utx_rollback() throws Exception {
		// prepare environment
		itemInventoryHelper.assureRemoveAll();
		
		// prepare context
		transactionControl.beginUserTransaction();
		
		// execute action
		Collection<ItemStoreEntity> itemStoreList = itemInventoryHelper.createItemStoreEntityList();
		Collection<Long> idList = itemStoreDao.addItemRecords(itemStoreList);
		Assert.notNull(idList, "Id list should exist");
		Assert.equals(idList.size(), itemStoreList.size(), "Id list should have correct length");
		
		// close context
		transactionControl.rollbackUserTransaction();
		
		// validate results
		itemInventoryHelper.verifyItemStoreCount(0);
	}
	
	@Test
	public void runTest_ItemStore_SaveAsItem() throws Exception {
		// prepare environment
		itemInventoryHelper.assureRemoveAll();
		Long id = itemInventoryHelper.addItemStore();
		
		// read prepared record
		ItemStoreEntity itemStoreToSave = itemStoreDao.getItemRecordById(id);
		Assert.notNull(itemStoreToSave, "ItemStore record should exist");
		
		// modify and save record
		itemStoreToSave.setName("dummyName111");
		itemStoreDao.saveItemRecord(itemStoreToSave);
		
		// validate results
		ItemStoreEntity itemStore = itemInventoryHelper.getItemStoreRecordById(id);
		Assert.equals(itemStore.getName(), "dummyName111", "ItemStore name field not correct");
		JeeMigrationExampleEntityFixture.assertSameItemStoreEntity(itemStore, itemStoreToSave);
	}
	
	@Test
	public void runTest_ItemStore_SaveAsItem_commit() throws Exception {
		// prepare environment
		itemInventoryHelper.assureRemoveAll();
		Long id = itemInventoryHelper.addItemStore();
		
		// prepare context
		transactionControl.beginTransaction();
		
		// read prepared record
		ItemStoreEntity itemStoreToSave = itemStoreDao.getItemRecordById(id);
		Assert.notNull(itemStoreToSave, "ItemStore record should exist");
		
		// modify and save record
		itemStoreToSave.setName("dummyName111");
		itemStoreDao.saveItemRecord(itemStoreToSave);
		
		// close context
		transactionControl.commitTransaction();
		
		// validate results
		ItemStoreEntity itemStore = itemInventoryHelper.getItemStoreRecordById(id);
		Assert.equals(itemStore.getName(), "dummyName111", "ItemStore name field not correct");
		JeeMigrationExampleEntityFixture.assertSameItemStoreEntity(itemStore, itemStoreToSave);
	}
	
	@Test
	public void runTest_ItemStore_SaveAsItem_rollback() throws Exception {
		// prepare environment
		itemInventoryHelper.assureRemoveAll();
		ItemStoreEntity itemStoreToAdd = createItemStoreEntity();
		Long id = itemInventoryHelper.addItemStoreRecord(itemStoreToAdd);
		
		// prepare context
		transactionControl.beginTransaction();
		
		// read prepared record
		ItemStoreEntity itemStoreToSave = itemStoreDao.getItemRecordById(id);
		Assert.notNull(itemStoreToSave, "ItemStore record should exist");
		
		// modify and save record
		itemStoreToSave.setId(111L);
		itemStoreDao.saveItemRecord(itemStoreToSave);
		
		// close context
		transactionControl.rollbackTransaction();
		
		// validate results
		ItemStoreEntity itemStore = itemInventoryHelper.getItemStoreRecordById(id);
		JeeMigrationExampleEntityFixture.assertSameItemStoreEntity(itemStore, itemStoreToAdd, true);
	}
	
	@Test
	public void runTest_ItemStore_SaveAsItem_utx_commit() throws Exception {
		// prepare environment
		itemInventoryHelper.assureRemoveAll();
		Long id = itemInventoryHelper.addItemStore();
		
		// prepare context
		transactionControl.beginUserTransaction();
		
		// read prepared record
		ItemStoreEntity itemStoreToSave = itemStoreDao.getItemRecordById(id);
		Assert.notNull(itemStoreToSave, "ItemStore record should exist");
		
		// modify and save record
		itemStoreToSave.setName("dummyName111");
		itemStoreDao.saveItemRecord(itemStoreToSave);
		
		// close context
		transactionControl.commitUserTransaction();
		
		// validate results
		ItemStoreEntity itemStore = itemInventoryHelper.getItemStoreRecordById(id);
		Assert.equals(itemStore.getName(), "dummyName111", "ItemStore name field not correct");
		JeeMigrationExampleEntityFixture.assertSameItemStoreEntity(itemStore, itemStoreToSave);
	}
	
	@Test
	public void runTest_ItemStore_SaveAsItem_utx_rollback() throws Exception {
		// prepare environment
		itemInventoryHelper.assureRemoveAll();
		ItemStoreEntity itemStoreToAdd = createItemStoreEntity();
		Long id = itemInventoryHelper.addItemStoreRecord(itemStoreToAdd);
		
		// prepare context
		transactionControl.beginUserTransaction();
		
		// read prepared record
		ItemStoreEntity itemStoreToSave = itemStoreDao.getItemRecordById(id);
		Assert.notNull(itemStoreToSave, "ItemStore record should exist");
		
		// modify and save record
		itemStoreToSave.setId(111L);
		itemStoreDao.saveItemRecord(itemStoreToSave);
		
		// close context
		transactionControl.rollbackUserTransaction();
		
		// validate results
		ItemStoreEntity itemStore = itemInventoryHelper.getItemStoreRecordById(id);
		JeeMigrationExampleEntityFixture.assertSameItemStoreEntity(itemStore, itemStoreToAdd, true);
	}
	
	@Test
	public void runTest_ItemStore_RemoveAll() throws Exception {
		// prepare environment
		itemInventoryHelper.assureRemoveAll();
		itemInventoryHelper.addItemStore();
		
		// execute action
		itemStoreDao.removeAllItemRecords();
		
		// validate results
		itemInventoryHelper.verifyItemStoreCount(0);
	}
	
	@Test
	public void runTest_ItemStore_RemoveAll_commit() throws Exception {
		// prepare environment
		itemInventoryHelper.assureRemoveAll();
		itemInventoryHelper.addItemStore();
		
		// prepare context
		transactionControl.beginTransaction();
		
		// execute action
		itemStoreDao.removeAllItemRecords();
		
		// close context
		transactionControl.commitTransaction();
		
		// validate results
		itemInventoryHelper.verifyItemStoreCount(0);
	}
	
	@Test
	public void runTest_ItemStore_RemoveAll_rollback() throws Exception {
		// prepare environment
		itemInventoryHelper.assureRemoveAll();
		itemInventoryHelper.addItemStore();
		
		// prepare context
		transactionControl.beginTransaction();
		
		// execute action
		itemStoreDao.removeAllItemRecords();
		
		// close context
		transactionControl.rollbackTransaction();
		
		// validate results
		itemInventoryHelper.verifyItemStoreCount(1);
	}
	
	@Test
	public void runTest_ItemStore_RemoveAll_utx_commit() throws Exception {
		// prepare environment
		itemInventoryHelper.assureRemoveAll();
		itemInventoryHelper.addItemStore();
		
		// prepare context
		transactionControl.beginUserTransaction();
		
		// execute action
		itemStoreDao.removeAllItemRecords();
		
		// close context
		transactionControl.commitUserTransaction();
		
		// validate results
		itemInventoryHelper.verifyItemStoreCount(0);
	}
	
	@Test
	public void runTest_ItemStore_RemoveAll_utx_rollback() throws Exception {
		// prepare environment
		itemInventoryHelper.assureRemoveAll();
		itemInventoryHelper.addItemStore();
		
		// prepare context
		transactionControl.beginUserTransaction();
		
		// execute action
		itemStoreDao.removeAllItemRecords();
		
		// close context
		transactionControl.rollbackUserTransaction();
		
		// validate results
		itemInventoryHelper.verifyItemStoreCount(1);
	}
	
	@Test
	public void runTest_ItemStore_RemoveAsItem() throws Exception {
		// prepare environment
		itemInventoryHelper.assureRemoveAll();
		Long id = itemInventoryHelper.addItemStore();
		Assert.notNull(id, "Id should exist");
		
		// execute action
		ItemStoreEntity itemStore = itemStoreDao.getItemRecordById(id);
		Assert.notNull(itemStore, "ItemStore record should exist");
		itemStoreDao.removeItemRecord(itemStore);
		
		// validate results
		itemInventoryHelper.verifyItemStoreCount(0);
	}
	
	@Test
	public void runTest_ItemStore_RemoveAsItem_commit() throws Exception {
		// prepare environment
		itemInventoryHelper.assureRemoveAll();
		Long id = itemInventoryHelper.addItemStore();
		Assert.notNull(id, "Id should exist");
		
		// prepare context
		transactionControl.beginTransaction();
		
		// execute action
		ItemStoreEntity itemStore = itemStoreDao.getItemRecordById(id);
		Assert.notNull(itemStore, "ItemStore record should exist");
		itemStoreDao.removeItemRecord(itemStore);
		
		// close context
		transactionControl.commitTransaction();
		
		// validate results
		itemInventoryHelper.verifyItemStoreCount(0);
	}
	
	@Test
	public void runTest_ItemStore_RemoveAsItem_rollback() throws Exception {
		// prepare environment
		itemInventoryHelper.assureRemoveAll();
		Long id = itemInventoryHelper.addItemStore();
		Assert.notNull(id, "Id should exist");
		
		// prepare context
		transactionControl.beginTransaction();
		
		// execute action
		ItemStoreEntity itemStore = itemStoreDao.getItemRecordById(id);
		Assert.notNull(itemStore, "ItemStore record should exist");
		itemStoreDao.removeItemRecord(itemStore);
		
		// close context
		transactionControl.rollbackTransaction();
		
		// validate results
		itemInventoryHelper.verifyItemStoreCount(1);
	}
	
	@Test
	public void runTest_ItemStore_RemoveAsItem_utx_commit() throws Exception {
		// prepare environment
		itemInventoryHelper.assureRemoveAll();
		Long id = itemInventoryHelper.addItemStore();
		Assert.notNull(id, "Id should exist");
		
		// prepare context
		transactionControl.beginUserTransaction();
		
		// execute action
		ItemStoreEntity itemStore = itemStoreDao.getItemRecordById(id);
		Assert.notNull(itemStore, "ItemStore record should exist");
		itemStoreDao.removeItemRecord(itemStore);
		
		// close context
		transactionControl.commitUserTransaction();
		
		// validate results
		itemInventoryHelper.verifyItemStoreCount(0);
	}
	
	@Test
	public void runTest_ItemStore_RemoveAsItem_utx_rollback() throws Exception {
		// prepare environment
		itemInventoryHelper.assureRemoveAll();
		Long id = itemInventoryHelper.addItemStore();
		Assert.notNull(id, "Id should exist");
		
		// prepare context
		transactionControl.beginUserTransaction();
		
		// execute action
		ItemStoreEntity itemStore = itemStoreDao.getItemRecordById(id);
		Assert.notNull(itemStore, "ItemStore record should exist");
		itemStoreDao.removeItemRecord(itemStore);
		
		// close context
		transactionControl.rollbackUserTransaction();
		
		// validate results
		itemInventoryHelper.verifyItemStoreCount(1);
	}
	
	@Test
	public void runTest_ItemStore_RemoveAsList() throws Exception {
		// prepare environment
		itemInventoryHelper.assureRemoveAll();
		Collection<ItemStoreEntity> itemStoreList = itemInventoryHelper.createItemStoreEntityList();
		Collection<Long> idList = itemInventoryHelper.addItemStoreRecords(itemStoreList);
		Assert.notNull(idList, "Id list should exist");
		
		// execute action
		itemStoreDao.removeItemRecords(itemStoreList);
		
		// validate results
		itemInventoryHelper.verifyItemStoreCount(0);
	}
	
	@Test
	public void runTest_ItemStore_RemoveAsList_commit() throws Exception {
		// prepare environment
		itemInventoryHelper.assureRemoveAll();
		Collection<ItemStoreEntity> itemStoreList = itemInventoryHelper.createItemStoreEntityList();
		Collection<Long> idList = itemInventoryHelper.addItemStoreRecords(itemStoreList);
		Assert.notNull(idList, "Id list should exist");
		
		// prepare context
		transactionControl.beginTransaction();
		
		// execute action
		itemStoreDao.removeItemRecords(itemStoreList);
		
		// close context
		transactionControl.commitTransaction();
		
		// validate results
		itemInventoryHelper.verifyItemStoreCount(0);
	}
	
	@Test
	public void runTest_ItemStore_RemoveAsList_rollback() throws Exception {
		// prepare environment
		itemInventoryHelper.assureRemoveAll();
		Collection<ItemStoreEntity> itemStoreList = itemInventoryHelper.createItemStoreEntityList();
		Collection<Long> idList = itemInventoryHelper.addItemStoreRecords(itemStoreList);
		Assert.notNull(idList, "Id list should exist");
		
		// prepare context
		transactionControl.beginTransaction();
		
		// execute action
		itemStoreDao.removeItemRecords(itemStoreList);
		
		// close context
		transactionControl.rollbackTransaction();
		
		// validate results
		itemInventoryHelper.verifyItemStoreListExists(idList);
		itemInventoryHelper.verifyItemStoreCount(idList.size());
		Collection<ItemStoreEntity> itemStoreList2 = itemInventoryHelper.getItemStoreRecordsByIds(idList);
		JeeMigrationExampleEntityFixture.assertSameItemStoreEntity(itemStoreList, itemStoreList2, true);
	}
	
	@Test
	public void runTest_ItemStore_RemoveAsList_utx_commit() throws Exception {
		// prepare environment
		itemInventoryHelper.assureRemoveAll();
		Collection<ItemStoreEntity> itemStoreList = itemInventoryHelper.createItemStoreEntityList();
		Collection<Long> idList = itemInventoryHelper.addItemStoreRecords(itemStoreList);
		Assert.notNull(idList, "Id list should exist");
		
		// prepare context
		transactionControl.beginUserTransaction();
		
		// execute action
		itemStoreDao.removeItemRecords(itemStoreList);
		
		// close context
		transactionControl.commitUserTransaction();
		
		// validate results
		itemInventoryHelper.verifyItemStoreCount(0);
	}
	
	@Test
	public void runTest_ItemStore_RemoveAsList_utx_rollback() throws Exception {
		// prepare environment
		itemInventoryHelper.assureRemoveAll();
		Collection<ItemStoreEntity> itemStoreList = itemInventoryHelper.createItemStoreEntityList();
		Collection<Long> idList = itemInventoryHelper.addItemStoreRecords(itemStoreList);
		Assert.notNull(idList, "Id list should exist");
		
		// prepare context
		transactionControl.beginUserTransaction();
		
		// execute action
		itemStoreDao.removeItemRecords(itemStoreList);
		
		// close context
		transactionControl.rollbackUserTransaction();
		
		// validate results
		itemInventoryHelper.verifyItemStoreListExists(idList);
		itemInventoryHelper.verifyItemStoreCount(idList.size());
		Collection<ItemStoreEntity> itemStoreList2 = itemInventoryHelper.getItemStoreRecordsByIds(idList);
		JeeMigrationExampleEntityFixture.assertSameItemStoreEntity(itemStoreList, itemStoreList2, true);
	}
	
//	protected Long addItemStore() throws Exception {
//		ItemStoreEntity itemStoreEntity = createItemStoreEntity();
//		Long id = addItemStore(itemStoreEntity);
//		return id;
//	}
//	
//	protected Long addItemStore(ItemStoreEntity itemStore) throws Exception {
//		openEntityManager();
//		beginTransaction();
//		Long id = itemStoreDao.addItemStoreRecord(itemStore);
//		commitTransaction();
//		verifyItemStoreExists(id);
//		closeEntityManager();
//		return id;
//	}
	
//	protected void assureDeleteItemStore(Long id) throws Exception {
//		ItemStoreEntity itemStore = itemStoreDao.getItemRecordById(id);
//		assureDeleteItemStore(itemStore);
//	}
	
//	protected void assureDeleteItemStore(ItemStoreEntity itemStore) throws Exception {
//		openEntityManager();
//		beginTransaction();
//		itemStoreDao.removeItemStoreRecord(itemStore);
//		commitTransaction();
//		verifyItemStoreNotExists(itemStore);
//		closeEntityManager();
//	}
	
	protected void verifyItemStoreCount(int expectedCount) throws Exception {
		Collection<ItemStoreEntity> records = itemStoreDao.getAllItemRecords();
		verifyItemStoreCount(records, expectedCount);
	}
	
	protected void verifyItemStoreCount(Collection<ItemStoreEntity> records, int expectedCount) throws Exception {
		Assert.notNull(records, "Result not found");
		if (expectedCount > 0)
			Assert.notNull(records, "ItemStore records should exist");
		Assert.equals(records.size(), expectedCount, "ItemStore record count not correct");
	}
	
	protected void verifyItemStoreExists(ItemStoreEntity itemStore) throws Exception {
		verifyItemStoreExists(itemStore.getId());
	}
	
	protected void verifyItemStoreExists(Long id) throws Exception {
		ItemStoreEntity itemStore = itemStoreDao.getItemRecordById(id);
		Assert.notNull(itemStore, "ItemStore should exist");
		Assert.equals(itemStore.getId(), id, "Ids should be same");
	}
	
	protected void verifyItemStoreNotExists(ItemStoreEntity itemStore) throws Exception {
		verifyItemStoreNotExists(itemStore.getId());
	}
	
	protected void verifyItemStoreNotExists(Long id) throws Exception {
		ItemStoreEntity itemStore = itemStoreDao.getItemRecordById(id);
		Assert.isNull(itemStore, "ItemStore should not exist");
	}

	protected void assertAssertionFailure(Exception e, String message) {
		Assert.exception(e, AssertionFailure.class, message);
	}

	protected void assertConstraintViolation(Exception e, String message) {
		Assert.exception(e, org.hibernate.exception.ConstraintViolationException.class, message);
		Assert.exception(e.getCause(), com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException.class, message);
	}
	
}
