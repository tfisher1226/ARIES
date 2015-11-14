package redhat.jee_migration_example;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.inject.Inject;

import org.aries.Assert;
import org.aries.data.AbstractRepository;
import org.aries.util.ExceptionUtil;

import redhat.jee_migration_example.bean.ItemStoreManager;


@Stateful
@Local(ItemInventoryRepository.class)
public class ItemInventoryRepositoryImpl extends AbstractRepository implements ItemInventoryRepository {
	
	@Inject
	protected ItemStoreManager itemStoreManager;
	
	
	public ItemStoreManager getItemStoreManager() {
		return itemStoreManager;
	}
	
	public void setItemStoreManager(ItemStoreManager itemStoreManager) {
		this.itemStoreManager = itemStoreManager;
	}
	
	@Override
	public void clearContext() {
		try {
			itemStoreManager.clearContext();
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Item> getAllItemStoreRecords() {
		try {
			List<Item> records = itemStoreManager.getAllItemStoreRecords();
			Assert.notNull(records, "ItemStore record list null");
			return records;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public Item getItemStoreRecord(Long id) {
		try {
			Assert.notNull(id, "Id must be specified");
			Item record = itemStoreManager.getItemStoreRecord(id);
			return record;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public Item getItemStoreRecord(String key) {
		try {
			Assert.notNull(key, "Key must be specified");
			Item record = itemStoreManager.getItemStoreRecord(key);
			return record;
			
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public Long addItemStoreRecord(String key, Item item) {
		try {
			Assert.notNull(key, "ItemStore record key must be specified");
			Assert.notNull(item, "ItemStore record must be specified");
			Long id = itemStoreManager.addItemStoreRecord(key, item);
			Assert.notNull(id, "Id must exist");
			return id;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public List<Long> addItemStoreRecords(Map<String, Item> itemMap) {
		try {
			Assert.notNull(itemMap, "ItemStore record list must be specified");
			List<Long> idList = itemStoreManager.addItemStoreRecords(itemMap);
			Assert.notNull(idList, "Id list must exist");
			Assert.isTrue(idList.size() == itemMap.size(), "Id count must be correct");
			return idList;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void saveItemStoreRecord(String key, Item item) {
		try {
			Assert.notNull(item, "ItemStore record must be specified");
			itemStoreManager.saveItemStoreRecord(key, item);
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void saveItemStoreRecords(Map<String, Item> itemList) {
		try {
			Assert.notNull(itemList, "ItemStore record list must be specified");
			itemStoreManager.saveItemStoreRecords(itemList);
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void removeAllItemStoreRecords() {
		try {
			itemStoreManager.removeAllItemStoreRecords();
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void removeItemStoreRecord(Long id) {
		try {
			Assert.notNull(id, "ItemStore record ID must be specified");
			itemStoreManager.removeItemStoreRecord(id);
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void removeItemStoreRecord(String key) {
		try {
			Assert.notNull(key, "ItemStore record key must be specified");
			itemStoreManager.removeItemStoreRecord(key);
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void removeItemStoreRecord(String key, Item item) {
		try {
			Assert.notNull(item, "ItemStore record must be specified");
			itemStoreManager.removeItemStoreRecord(key, item);
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void removeItemStoreRecords(Map<String, Item> itemList) {
		try {
			Assert.notNull(itemList, "ItemStore record list must be specified");
			itemStoreManager.removeItemStoreRecords(itemList);
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void importItemStoreRecords() {
		try {
			itemStoreManager.importItemStoreRecords();
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
}
