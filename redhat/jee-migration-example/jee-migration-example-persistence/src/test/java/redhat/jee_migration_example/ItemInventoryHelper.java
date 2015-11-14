package redhat.jee_migration_example;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionAttributeType.REQUIRES_NEW;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.aries.Assert;
import org.aries.tx.DataLayerTestControl;
import org.aries.tx.DataModuleTestControl;
import org.aries.tx.TransactionTestControl;

import redhat.jee_migration_example.dao.ItemDao;
import redhat.jee_migration_example.dao.ItemDaoImpl;
import redhat.jee_migration_example.entity.ItemStoreEntity;
import redhat.jee_migration_example.map.ItemStoreMapper;
import redhat.jee_migration_example.util.JeeMigrationExampleEntityFixture;
import redhat.jee_migration_example.util.JeeMigrationExampleFixture;
import redhat.jee_migration_example.util.JeeMigrationExampleMapperFixture;

import common.jmx.client.JmxManager;
import common.jmx.client.JmxProxy;


@Stateful
@ConcurrencyManagement(BEAN)
@TransactionAttribute(REQUIRES_NEW)
public class ItemInventoryHelper {
	
	//@PersistenceContext(unitName = "itemInventory", type = EXTENDED)
	//protected EntityManager entityManager;
	
	private static EntityManagerFactory entityManagerFactory;
	
	private TransactionTestControl transactionControl;
	
	private DataLayerTestControl itemInventoryControl;
	
	private ItemDao<ItemStoreEntity> itemStoreDao;
	
	private EntityManager entityManager;
	
	private boolean runningInContainer;
	
	private JmxProxy jmxProxy;
	
	
	public ItemInventoryHelper() {
		jmxProxy = new JmxProxy();
	}
	
	
	public void setJmxProxy(JmxProxy jmxProxy) {
		this.jmxProxy = jmxProxy;
	}
	
	public void setJmxManager(JmxManager jmxManager) {
		jmxProxy.setJmxManager(jmxManager);
	}
	
	public void clearContext(String mbeanName) throws Exception {
		jmxProxy.call(mbeanName, "clearContext");
	}
	
	public <T> void refreshContext(T entity) throws Exception {
		entityManager.refresh(entity);
	}
	
	public <T> void refreshContext(Collection<T> entityList) throws Exception {
		Iterator<T> iterator = entityList.iterator();
		while (iterator.hasNext()) {
			T entity = iterator.next();
			entityManager.refresh(entity);
		}
	}
	
	public void initialize(TransactionTestControl transactionControl) throws Exception {
		this.transactionControl = transactionControl;
		assureEntityManagerFactory();
		runningInContainer = true;
		assureEntityManager();
		createDaoInstances();
	}
	
	public void initialize(DataLayerTestControl itemInventoryControl) throws Exception {
		this.transactionControl = itemInventoryControl.getTransactionTestControl();
		this.itemInventoryControl = itemInventoryControl;
		assureEntityManager();
		createDaoInstances();
	}
	
	public void initialize(DataModuleTestControl itemInventoryControl) throws Exception {
		this.transactionControl = itemInventoryControl.getTransactionTestControl();
		this.itemInventoryControl = itemInventoryControl;
		runningInContainer = true;
		createDaoInstances();
	}
	
	public void initializeAsClient(DataLayerTestControl itemInventoryControl) throws Exception {
		this.transactionControl = itemInventoryControl.getTransactionTestControl();
		this.itemInventoryControl = itemInventoryControl;
		entityManager = itemInventoryControl.setupEntityManager();
		createDaoInstances();
	}
	
	public void assureEntityManagerFactory() throws Exception {
		if (entityManagerFactory == null) {
			entityManagerFactory = Persistence.createEntityManagerFactory("itemInventory");
		}
	}
	
	public void resetEntityManagerFactory() throws Exception {
		entityManagerFactory = null;
		assureEntityManagerFactory();
	}
	
	public void assureEntityManager() throws Exception {
		if (entityManager == null) {
			if (runningInContainer) {
				entityManager = entityManagerFactory.createEntityManager();
			} else {
				entityManager = itemInventoryControl.createEntityManager();
			}
		}
	}
	
	protected void createDaoInstances() throws Exception {
		itemStoreDao = createItemStoreDao();
	}
	
	public ItemDao<ItemStoreEntity> createItemStoreDao() {
		ItemDao<ItemStoreEntity> dao = new ItemDaoImpl<ItemStoreEntity>();
		dao.setEntityClass(ItemStoreEntity.class);
		dao.setEntityManager(entityManager);
		dao.initialize("ItemStore");
		return dao;
	}
	
	public ItemStoreMapper getItemStoreMapper() {
		return JeeMigrationExampleMapperFixture.getItemStoreMapper();
	}
	
	public ItemStoreEntity createItemStoreEntity() {
		return JeeMigrationExampleEntityFixture.create_ItemStoreEntity();
	}
	
	public ItemStoreEntity createEmptyItemStoreEntity() {
		return JeeMigrationExampleEntityFixture.createEmpty_ItemStoreEntity();
	}
	
	public Collection<ItemStoreEntity> createItemStoreEntityList() {
		return JeeMigrationExampleEntityFixture.createList_ItemStoreEntity();
	}
	
	public String createItemStoreKey(Item item) {
		String itemKey = item.toString();
		return itemKey;
	}

	@TransactionAttribute(REQUIRES_NEW)
	public Collection<Item> getAllItemStore() throws Exception {
		preProcessExecution();
		Collection<ItemStoreEntity> entityList = itemStoreDao.getAllItemRecords();
		Collection<Item> itemList = getItemStoreMapper().toModelList(entityList);
		postProcessExecution();
		return itemList;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public Map<String, Item> getAllItemStoreAsMap() throws Exception {
		preProcessExecution();
		Collection<ItemStoreEntity> entityList = itemStoreDao.getAllItemRecords();
		Map<String, Item> itemMap = getItemStoreMapper().toModelMap(entityList);
		postProcessExecution();
		return itemMap;
	}
	
	@TransactionAttribute(REQUIRED)
	public Map<String, Item> getAllItemStoreAsMapIC() throws Exception {
		Collection<ItemStoreEntity> entityList = itemStoreDao.getAllItemRecords();
		Map<String, Item> itemMap = getItemStoreMapper().toModelMap(entityList);
		return itemMap;
	}
	
	@TransactionAttribute(REQUIRED)
	public Collection<Item> getAllItemStoreIC() throws Exception {
		Collection<ItemStoreEntity> entityList = itemStoreDao.getAllItemRecords();
		Collection<Item> itemList = getItemStoreMapper().toModelList(entityList);
		return itemList;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public Collection<ItemStoreEntity> getAllItemStoreRecords() throws Exception {
		preProcessExecution();
		Collection<ItemStoreEntity> entityList = itemStoreDao.getAllItemRecords();
		postProcessExecution();
		return entityList;
	}
	
	@TransactionAttribute(REQUIRED)
	public Collection<ItemStoreEntity> getAllItemStoreRecordsIC() throws Exception {
		Collection<ItemStoreEntity> entityList = itemStoreDao.getAllItemRecords();
		return entityList;
	}
	
//	@TransactionAttribute(REQUIRES_NEW)
//	public Collection<ItemStoreEntity> getAllItemStoreRecordsAsMap() throws Exception {
//		preProcessExecution();
//		Map<String, Item> itemMap = new HashMap<String, Item>();
//		Collection<ItemStoreEntity> entityList = itemStoreDao.getAllItemRecords();
//		postProcessExecution();
//		return entityList;
//	}
	
	@TransactionAttribute(REQUIRED)
	public Collection<ItemStoreEntity> getAllItemStoreRecordsAsMapIC() throws Exception {
		Collection<ItemStoreEntity> entityList = itemStoreDao.getAllItemRecords();
		return entityList;
	}

	@TransactionAttribute(REQUIRES_NEW)
	public Collection<ItemStoreEntity> getItemStoreRecordsByIds(Collection<Long> idList) throws Exception {
		preProcessExecution();
		Collection<ItemStoreEntity> entityList = new ArrayList<ItemStoreEntity>();
		Iterator<Long> iterator = idList.iterator();
		while (iterator.hasNext()) {
			Long id = iterator.next();
			ItemStoreEntity entity = itemStoreDao.getItemRecordById(id);
			entityList.add(entity);
		}
		postProcessExecution();
		return entityList;
	}

	@TransactionAttribute(REQUIRES_NEW)
	public Item getItemStoreById(Long id) throws Exception {
		preProcessExecution();
		ItemStoreEntity entity = itemStoreDao.getItemRecordById(id);
		Item item = getItemStoreMapper().toModel(entity);
		postProcessExecution();
		return item;
	}
	
	@TransactionAttribute(REQUIRED)
	public Item getItemStoreByIdIC(Long id) throws Exception {
		ItemStoreEntity entity = itemStoreDao.getItemRecordById(id);
		Item item = getItemStoreMapper().toModel(entity);
		return item;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public ItemStoreEntity getItemStoreRecordById(Long id) throws Exception {
		preProcessExecution();
		ItemStoreEntity entity = itemStoreDao.getItemRecordById(id);
		refreshContext(entity);
		postProcessExecution();
		return entity;
	}
	
	@TransactionAttribute(REQUIRED)
	public ItemStoreEntity getItemStoreRecordByIdIC(Long id) throws Exception {
		ItemStoreEntity entity = itemStoreDao.getItemRecordById(id);
		refreshContext(entity);
		return entity;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public Long addItemStore() throws Exception {
		preProcessExecution();
		Item item = JeeMigrationExampleFixture.create_Item();
		String key = JeeMigrationExampleFixture.create_ItemKey(item);
		ItemStoreEntity entity = getItemStoreMapper().toEntity(key, item);
		entity.setItemKey(createItemStoreKey(item));
		Long id = itemStoreDao.addItemRecord(entity);
		postProcessExecution();
		return id;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public Long addItemStore(String key, Item item) throws Exception {
		preProcessExecution();
		ItemStoreEntity entity = getItemStoreMapper().toEntity(key, item);
		assignItemStoreKey(entity, item);
		Long id = itemStoreDao.addItemRecord(entity);
		postProcessExecution();
		return id;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public Collection<Long> addItemStore(int count) throws Exception {
		preProcessExecution();
		Map<String, Item> itemMap = JeeMigrationExampleFixture.createMap_Item(count);
		Collection<ItemStoreEntity> entityList = getItemStoreMapper().toEntityList(itemMap);
		Collection<Long> idList = itemStoreDao.addItemRecords(entityList);
		postProcessExecution();
		return idList;
	}

	@TransactionAttribute(REQUIRES_NEW)
	public Long addItemStoreRecord(ItemStoreEntity entity) throws Exception {
		preProcessExecution();
		Long id = itemStoreDao.addItemRecord(entity);
		postProcessExecution();
		return id;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public Collection<Long> addItemStore(Map<String, Item> itemMap) throws Exception {
		preProcessExecution();
		Collection<Long> idList = addItemStoreIC(itemMap);
		postProcessExecution();
		return idList;
	}
	
	@TransactionAttribute(REQUIRED)
	public Collection<Long> addItemStoreIC(Map<String, Item> itemMap) throws Exception {
		Collection<ItemStoreEntity> entityList = getItemStoreMapper().toEntityList(itemMap);
		Collection<Long> idList = itemStoreDao.addItemRecords(entityList);
		return idList;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public Collection<Long> addItemStoreRecords(Collection<ItemStoreEntity> entityList) throws Exception {
		preProcessExecution();
		Collection<Long> idList = itemStoreDao.addItemRecords(entityList);
		postProcessExecution();
		return idList;
	}
	
	public void assignItemStoreKey(ItemStoreEntity entity, Item item) {
		entity.setItemKey(createItemStoreKey(item));
	}

	public void assignItemStoreKeys(Collection<ItemStoreEntity> entityList, Collection<Item> itemList) {
		Assert.equals(entityList.size(), itemList.size(), "ItemStore record count not correct");
		int size = entityList.size();
		Iterator<Item> iterator = itemList.iterator();
		Iterator<ItemStoreEntity> iterator2 = entityList.iterator();
		for (int i=0; i < size && iterator.hasNext(); i++) {
			Item item = iterator.next();
			ItemStoreEntity entity = iterator2.next();
			assignItemStoreKey(entity, item);
		}
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public void removeAllItemStore() throws Exception {
		preProcessExecution();
		itemStoreDao.removeAllItemRecords();
		postProcessExecution();
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public void removeItemStore(String key, Item item) throws Exception {
		preProcessExecution();
		ItemStoreEntity entity = getItemStoreMapper().toEntity(key, item);
		itemStoreDao.removeItemRecord(entity);
		postProcessExecution();
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public void removeItemStoreRecord(ItemStoreEntity entity) throws Exception {
		preProcessExecution();
		itemStoreDao.removeItemRecord(entity);
		postProcessExecution();
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public void removeItemStore(Map<String, Item> itemMap) throws Exception {
		preProcessExecution();
		Collection<ItemStoreEntity> entityList = getItemStoreMapper().toEntityList(itemMap);
		itemStoreDao.removeItemRecords(entityList);
		postProcessExecution();
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public void removeItemStoreRecord(Collection<ItemStoreEntity> entityList) throws Exception {
		preProcessExecution();
		itemStoreDao.removeItemRecords(entityList);
		postProcessExecution();
	}
	
	public void verifyItemStoreCount(int expectedCount) throws Exception {
		Collection<ItemStoreEntity> entityList = getAllItemStoreRecords();
		Assert.notNull(entityList, "ItemStore results should not be null");
		Assert.equals(entityList.size(), expectedCount, "ItemStore record count not correct");
	}
	
	public void verifyItemStoreCount(Collection<ItemStoreEntity> entityList, int expectedCount) throws Exception {
		Assert.notNull(entityList, "ItemStore records not found");
		if (expectedCount > 0)
			Assert.notNull(entityList, "ItemStore records should exist");
		Assert.equals(expectedCount, entityList.size(), "ItemStore record count not correct");
	}
	
	public void verifyItemStoreDoNotExist() throws Exception {
		verifyItemStoreCount(0);
	}
	
	public void verifyItemStoreDoNotExist(ItemStoreEntity entity) throws Exception {
		verifyItemStoreDoNotExist(entity.getId());
	}
	
	public void verifyItemStoreDoNotExist(Long id) throws Exception {
		ItemStoreEntity entity = getItemStoreRecordById(id);
		Assert.isNull(entity, "ItemStore record should not exist");
	}
	
	public void verifyItemStoreExists(ItemStoreEntity entity) throws Exception {
		ItemStoreEntity currentEntity = getItemStoreRecordById(entity.getId());
		Assert.notNull(currentEntity, "ItemStore record should exist");
		JeeMigrationExampleEntityFixture.assertSameItemStoreEntity(currentEntity, entity, true);
	}
	
	public void verifyItemStoreExists(Long id) throws Exception {
		ItemStoreEntity entity = getItemStoreRecordById(id);
		Assert.notNull(entity, "ItemStore record should exist");
	}
	
	public void verifyItemStoreListExists(Collection<Long> idList) throws Exception {
		Assert.notNull(idList, "ItemStore ids null");
		Iterator<Long> iterator = idList.iterator();
		while (iterator.hasNext()) {
			Long id = iterator.next();
			verifyItemStoreExists(id);
		}
	}
	
	public void verifyItemStoreNotExists(ItemStoreEntity entity) throws Exception {
		verifyItemStoreNotExists(entity.getId());
	}

	public void verifyItemStoreNotExists(Long id) throws Exception {
		//openEntityManager();
		ItemStoreEntity entity = itemStoreDao.getItemRecordById(id);
		Assert.isNull(entity, "ReservedBooks should not exist");
		//closeEntityManager();
	}

	
	public void assureRemoveAll() throws Exception {
		preProcessExecution();
		itemStoreDao.removeAllItemRecords();
		postProcessExecution();
	}

	public void assureDeleteReservedBooks(Long id) throws Exception {
		ItemStoreEntity bookOrders = itemStoreDao.getItemRecordById(id);
		assureDeleteReservedBooks(bookOrders);
	}

	public void assureDeleteReservedBooks(ItemStoreEntity entity) throws Exception {
		preProcessExecution();
		itemStoreDao.removeItemRecord(entity);
		postProcessExecution();
		verifyItemStoreNotExists(entity);
		//closeEntityManager();
	}

	
	
	protected void preProcessExecution() throws Exception {
		if (!runningInContainer) {
			if (transactionControl != null) {
				transactionControl.beginTransaction();
			}
		}
	}
	
	protected void postProcessExecution() throws Exception {
		postProcessExecution(true);
	}
	
	protected void postProcessExecution(boolean commitAndClose) throws Exception {
		if (!runningInContainer) {
			if (transactionControl != null && commitAndClose) {
				transactionControl.commitTransaction();
				transactionControl.clearTransactions();
			}
		}
	}

}
