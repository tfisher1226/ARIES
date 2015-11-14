package redhat.jee_migration_example.management.itemManager;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.aries.bean.Proxy;
import org.aries.tx.service.jms.JMSProxy;
import org.aries.util.ExceptionUtil;

import redhat.jee_migration_example.Item;


public class ItemManagerProxyForJMS extends JMSProxy implements Proxy<ItemManager> {
	
	private static final String DESTINATION = "/queue/public_redhat_item_manager_queue";
	
	private ItemManagerInterceptor itemManagerInterceptor;
	
	
	public ItemManagerProxyForJMS(String serviceId) {
		super(serviceId);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		itemManagerInterceptor = new ItemManagerInterceptor();
		itemManagerInterceptor.setProxy(this);
	}
	
	@Override
	public ItemManager getDelegate() {
		return itemManagerInterceptor;
	}
	
	public List<Item> getAllItem() {
		try {
			List<Item> item = invoke("");
			return item;
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	public Map<String, Item> getAllItemAsMap() {
		try {
			Map<String, Item> item = invoke("");
			return item;
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	public Item getFromItemById(Long itemId) {
		//Check.isValid("itemId", itemId);
		try {
			Item item = invoke("itemId");
			return item;
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	public Item getFromItemByKey(String itemKey) {
		//Check.isValid("itemKey", itemKey);
		try {
			Item item = invoke("itemKey");
			return item;
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	public List<Item> getFromItemByIds(List<Long> itemIds) {
		//Check.isValid("itemIds", itemIds);
		try {
			List<Item> item = invoke("itemIds");
			return item;
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	public Map<String, Item> getFromItemByKeys(List<String> itemKeys) {
		//Check.isValid("itemKeys", itemKeys);
		try {
			Map<String, Item> item = invoke("itemKeys");
			return item;
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	public List<Item> getMatchingItem(List<Item> itemList) {
		//Check.isValid("itemList", itemList);
		try {
			List<Item> item = invoke("itemList");
			return item;
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	public Map<String, Item> getMatchingItemAsMap(List<Item> itemList) {
		//Check.isValid("itemList", itemList);
		try {
			Map<String, Item> item = invoke("itemList");
			return item;
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	public Long addToItem(Item item) {
		//Check.isValid("item", item);
		try {
			Long itemId = invoke("item");
			return itemId;
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	public List<Long> addToItemAsList(List<Item> itemList) {
		//Check.isValid("itemList", itemList);
		try {
			List<Long> itemIds = invoke("itemList");
			return itemIds;
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	public List<Long> addToItemAsMap(Map<String, Item> itemMap) {
		//Check.isValid("itemMap", itemMap);
		try {
			List<Long> itemIds = invoke("itemMap");
			return itemIds;
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	public void removeFromItem(Item item) {
		//Check.isValid("item", item);
		try {
			invoke(item);
			log.info("#### [eventLogger-client]: RemoveFromItem request sent");
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	public void removeFromItemById(Long itemId) {
		//Check.isValid("itemId", itemId);
		try {
			invoke(itemId);
			log.info("#### [eventLogger-client]: RemoveFromItem request sent");
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	public void removeFromItemByKey(String itemKey) {
		//Check.isValid("itemKey", itemKey);
		try {
			invoke(itemKey);
			log.info("#### [eventLogger-client]: RemoveFromItem request sent");
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	public void removeFromItemAsList(Collection<Item> itemList) {
		//Check.isValid("itemList", itemList);
		try {
			invoke(itemList);
			log.info("#### [eventLogger-client]: RemoveFromItem request sent");
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
