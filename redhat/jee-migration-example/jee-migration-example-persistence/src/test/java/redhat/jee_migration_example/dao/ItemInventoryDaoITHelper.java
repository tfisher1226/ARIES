package redhat.jee_migration_example.dao;

import javax.persistence.EntityManager;

import redhat.jee_migration_example.Item;
import redhat.jee_migration_example.entity.ItemStoreEntity;
import redhat.jee_migration_example.util.JeeMigrationExampleFixture;
import redhat.jee_migration_example.util.JeeMigrationExampleMapperFixture;


public class ItemInventoryDaoITHelper {
	
	private ItemDao<ItemStoreEntity> itemDao;
	
	
	public ItemInventoryDaoITHelper(EntityManager entityManager) {
		itemDao = createItemDao(entityManager);
	}
	
	
	public ItemDao<ItemStoreEntity> createItemDao(EntityManager entityManager) {
		ItemDaoImpl<ItemStoreEntity> itemDao = new ItemDaoImpl<ItemStoreEntity>();
		itemDao.setEntityManager(entityManager);
		return itemDao;
	}
	
	public ItemStoreEntity createItemStoreEntity() throws Exception {
		Item item = JeeMigrationExampleFixture.create_Item();
		String key = JeeMigrationExampleFixture.create_ItemKey(item);
		ItemStoreEntity itemStoreEntity = JeeMigrationExampleMapperFixture.getItemStoreMapper().toEntity(key, item);
		return itemStoreEntity;
	}
	
	public ItemStoreEntity createAndPersistItemStoreEntity() throws Exception {
		ItemStoreEntity itemStoreEntity = createItemStoreEntity();
		Long id = persistItemStoreEntity(itemStoreEntity);
		itemStoreEntity.setId(id);
		return itemStoreEntity;
	}
	
	public Long persistItemStoreEntity(ItemStoreEntity itemStoreEntity) throws Exception {
		Long id = itemDao.addItemRecord(itemStoreEntity);
		return id;
	}
	
}
