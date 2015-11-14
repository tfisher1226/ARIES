package redhat.jee_migration_example.management.itemManager;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.client.AbstractDelegate;
import org.aries.util.ExceptionUtil;

import redhat.jee_migration_example.Item;


public class ItemManagerClient extends AbstractDelegate implements ItemManager {
	
	private static final Log log = LogFactory.getLog(ItemManagerClient.class);
	
	
	@Override
	public String getDomain() {
		return "redhat";
	}
	
	@Override
	public String getServiceId() {
		return ItemManager.ID;
	}
	
	@SuppressWarnings("unchecked")
	public ItemManager getProxy() throws Exception {
		return getProxy(ItemManager.ID);
	}
	
	@Override
	public List<Item> getAllItem() {
		try {
			List<Item> item = getProxy().getAllItem();
			return item;
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Map<String, Item> getAllItemAsMap() {
		try {
			Map<String, Item> item = getProxy().getAllItemAsMap();
			return item;
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Item getFromItemById(Long itemId) {
		try {
			Item item = getProxy().getFromItemById(itemId);
			return item;
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Item getFromItemByKey(String itemKey) {
		try {
			Item item = getProxy().getFromItemByKey(itemKey);
			return item;
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Item> getFromItemByIds(List<Long> itemIds) {
		try {
			List<Item> item = getProxy().getFromItemByIds(itemIds);
			return item;
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Map<String, Item> getFromItemByKeys(List<String> itemKeys) {
		try {
			Map<String, Item> item = getProxy().getFromItemByKeys(itemKeys);
			return item;
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Item> getMatchingItem(List<Item> itemList) {
		try {
			List<Item> item = getProxy().getMatchingItem(itemList);
			return item;
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Map<String, Item> getMatchingItemAsMap(List<Item> itemList) {
		try {
			Map<String, Item> item = getProxy().getMatchingItemAsMap(itemList);
			return item;
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Long addToItem(Item item) {
		try {
			Long itemId = getProxy().addToItem(item);
			return itemId;
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Long> addToItemAsList(List<Item> itemList) {
		try {
			List<Long> itemIds = getProxy().addToItemAsList(itemList);
			return itemIds;
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Long> addToItemAsMap(Map<String, Item> itemMap) {
		try {
			List<Long> itemIds = getProxy().addToItemAsMap(itemMap);
			return itemIds;
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeAllItem() {
		try {
			getProxy().removeAllItem();
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeFromItem(Item item) {
		try {
			getProxy().removeFromItem(item);
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeFromItemById(Long itemId) {
		try {
			getProxy().removeFromItemById(itemId);
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeFromItemByKey(String itemKey) {
		try {
			getProxy().removeFromItemByKey(itemKey);
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeFromItemAsList(List<Item> itemList) {
		try {
			getProxy().removeFromItemAsList(itemList);
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
