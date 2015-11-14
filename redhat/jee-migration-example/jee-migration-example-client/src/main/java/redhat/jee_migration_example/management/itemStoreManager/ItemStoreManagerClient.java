package redhat.jee_migration_example.management.itemStoreManager;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.client.AbstractDelegate;
import org.aries.util.ExceptionUtil;

import redhat.jee_migration_example.Item;


public class ItemStoreManagerClient extends AbstractDelegate implements ItemStoreManager {
	
	private static final Log log = LogFactory.getLog(ItemStoreManagerClient.class);
	
	
	@Override
	public String getDomain() {
		return "redhat";
	}
	
	@Override
	public String getServiceId() {
		return ItemStoreManager.ID;
	}
	
	@SuppressWarnings("unchecked")
	public ItemStoreManager getProxy() throws Exception {
		return getProxy(ItemStoreManager.ID);
	}
	
	@Override
	public List<Item> getAllItemStore() {
		try {
			List<Item> itemStore = getProxy().getAllItemStore();
			return itemStore;
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Map<String, Item> getAllItemStoreAsMap() {
		try {
			Map<String, Item> itemStore = getProxy().getAllItemStoreAsMap();
			return itemStore;
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Item getFromItemStoreById(Long itemId) {
		try {
			Item itemStore = getProxy().getFromItemStoreById(itemId);
			return itemStore;
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Item getFromItemStoreByKey(String itemKey) {
		try {
			Item itemStore = getProxy().getFromItemStoreByKey(itemKey);
			return itemStore;
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Long addToItemStore(Item item) {
		try {
			Long itemId = getProxy().addToItemStore(item);
			return itemId;
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Long> addToItemStoreAsList(List<Item> itemList) {
		try {
			List<Long> itemIds = getProxy().addToItemStoreAsList(itemList);
			return itemIds;
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Long> addToItemStoreAsMap(Map<String, Item> itemMap) {
		try {
			List<Long> itemIds = getProxy().addToItemStoreAsMap(itemMap);
			return itemIds;
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeAllItemStore() {
		try {
			getProxy().removeAllItemStore();
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeFromItemStore(Item item) {
		try {
			getProxy().removeFromItemStore(item);
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeFromItemStoreById(Long itemId) {
		try {
			getProxy().removeFromItemStoreById(itemId);
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeFromItemStoreByKey(String itemKey) {
		try {
			getProxy().removeFromItemStoreByKey(itemKey);
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeFromItemStoreAsList(List<Item> itemList) {
		try {
			getProxy().removeFromItemStoreAsList(itemList);
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
