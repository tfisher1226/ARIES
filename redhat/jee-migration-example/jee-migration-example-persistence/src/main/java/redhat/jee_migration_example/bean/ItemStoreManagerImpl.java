package redhat.jee_migration_example.bean;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.persistence.PersistenceContextType.EXTENDED;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.enterprise.inject.New;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.TransactionSynchronizationRegistry;

import org.aries.Assert;

import redhat.jee_migration_example.Item;
import redhat.jee_migration_example.dao.ItemDao;
import redhat.jee_migration_example.dao.ItemDaoImpl;
import redhat.jee_migration_example.entity.ItemStoreEntity;
import redhat.jee_migration_example.map.ItemStoreMapper;
import redhat.jee_migration_example.process.ItemImporter;


@Stateful
@Local(ItemStoreManager.class)
@ConcurrencyManagement(BEAN)
public class ItemStoreManagerImpl implements ItemStoreManager {
	
	@PersistenceContext(unitName = "itemInventory", type = EXTENDED)
	protected EntityManager entityManager;
	
	@Inject
	protected ItemImporter itemStoreImporter;
	
	@Inject
	protected ItemStoreMapper itemStoreMapper;
	
	@Inject
	@New(ItemDaoImpl.class)
	protected ItemDao<ItemStoreEntity> itemStoreDao;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Override
	public void clearContext() {
		entityManager.clear();
	}
	
	@PostConstruct
	public void initialize() {
		itemStoreDao.initialize("ItemStore");
		itemStoreDao.setEntityManager(entityManager);
		itemStoreDao.setEntityClass(ItemStoreEntity.class);
	}
	
	@Override
	public List<Item> getAllItemStoreRecords() {
		List<ItemStoreEntity> entityList = itemStoreDao.getAllItemRecords();
		List<Item> modelList = itemStoreMapper.toModelList(entityList);
		return modelList;
	}
	
	@Override
	public Item getItemStoreRecord(Long id) {
		Assert.notNull(id, "ID must be specified");
		ItemStoreEntity entity = itemStoreDao.getItemRecordById(id);
		Item model = itemStoreMapper.toModel(entity);
		return model;
	}
	
	@Override
	public Item getItemStoreRecord(String key) {
		Assert.notNull(key, "Key must exist");
		ItemStoreEntity entity = itemStoreDao.getItemRecordByKey(key);
		Item model = itemStoreMapper.toModel(entity);
		return model;
	}
	
	@Override
	public Long addItemStoreRecord(String key, Item item) {
		Assert.notNull(item, "Item record must be specified");
		ItemStoreEntity entity = itemStoreMapper.toEntity(key, item);
		Long id = itemStoreDao.addItemRecord(entity);
		Assert.notNull(id, "Id must exist");
		return id;
	}
	
//	@Override
//	public List<Long> addItemStoreRecords(Collection<Item> itemList) {
//		Assert.notNull(itemList, "Item record map must be specified");
//		List<ItemStoreEntity> entityList = itemStoreMapper.toEntityList(itemList);
//		List<Long> idList = itemStoreDao.addItemRecords(entityList);
//		Assert.notNull(idList, "IdList must exist");
//		Assert.equals(idList.size(), itemList.size(), "Id count not correct");
//		return idList;
//	}
	
	@Override
	public List<Long> addItemStoreRecords(Map<String, Item> itemMap) {
		Assert.notNull(itemMap, "Item record map must be specified");
		Collection<ItemStoreEntity> entityList = itemStoreMapper.toEntityList(itemMap);
		List<Long> idList = itemStoreDao.addItemRecords(entityList);
		Assert.notNull(idList, "IdList must exist");
		Assert.equals(idList.size(), itemMap.size(), "Id count not correct");
		return idList;
	}
	
	@Override
	public void saveItemStoreRecord(String key, Item item) {
		Assert.notNull(item, "Item record must be specified");
		ItemStoreEntity entity = itemStoreMapper.toEntity(key, item);
		itemStoreDao.saveItemRecord(entity);
	}
	
	@Override
	public void saveItemStoreRecords(Map<String, Item> itemMap) {
		Assert.notNull(itemMap, "Item record map must be specified");
		Collection<ItemStoreEntity> entityList = itemStoreMapper.toEntityList(itemMap);
		itemStoreDao.saveItemRecords(entityList);
	}
	
	@Override
	public void removeAllItemStoreRecords() {
		itemStoreDao.removeAllItemRecords();
	}
	
	@Override
	public void removeItemStoreRecord(String key, Item item) {
		Assert.notNull(item, "Item record must be specified");
		ItemStoreEntity entity = itemStoreMapper.toEntity(key, item);
		itemStoreDao.removeItemRecord(entity);
	}
	
	@Override
	public void removeItemStoreRecord(Long id) {
		Assert.notNull(id, "Item record ID must be specified");
		ItemStoreEntity entity = itemStoreDao.getItemRecordById(id);
		itemStoreDao.removeItemRecord(entity);
	}
	
	@Override
	public void removeItemStoreRecord(String key) {
		Assert.notNull(key, "Item record key must be specified");
		itemStoreDao.removeItemRecordByKey(key);
	}
	
	@Override
	public void removeItemStoreRecords(Map<String, Item> itemMap) {
		Assert.notNull(itemMap, "Item record map must be specified");
		Collection<ItemStoreEntity> entityList = itemStoreMapper.toEntityList(itemMap);
		itemStoreDao.removeItemRecords(entityList);
	}
	
	@Override
	public void importItemStoreRecords() {
		itemStoreImporter.importItemRecords();
	}
	
}
