package redhat.jee_migration_example.management.itemStoreManager;

import java.util.List;
import java.util.Map;

import org.aries.bean.Proxy;
import org.aries.tx.service.jms.JMSProxy;
import org.aries.util.ExceptionUtil;

import redhat.jee_migration_example.Item;


public class ItemStoreManagerProxyForJMS extends JMSProxy implements Proxy<ItemStoreManager> {
	
	private static final String DESTINATION = "/queue/public_redhat_item_store_manager_queue";
	
	private ItemStoreManagerInterceptor itemStoreManagerInterceptor;
	
	
	public ItemStoreManagerProxyForJMS(String serviceId) {
		super(serviceId);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		itemStoreManagerInterceptor = new ItemStoreManagerInterceptor();
		itemStoreManagerInterceptor.setProxy(this);
	}
	
	@Override
	public ItemStoreManager getDelegate() {
		return itemStoreManagerInterceptor;
	}
	
	public List<Item> getAllItemStore() {
		try {
			List<Item> itemStore = invoke("");
			return itemStore;
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	public Map<String, Item> getAllItemStoreAsMap() {
		try {
			Map<String, Item> itemStore = invoke("");
			return itemStore;
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	public Item getFromItemStoreById(Long itemId) {
		//Check.isValid("itemId", itemId);
		try {
			Item itemStore = invoke("itemId");
			return itemStore;
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	public Item getFromItemStoreByKey(String itemKey) {
		//Check.isValid("itemKey", itemKey);
		try {
			Item itemStore = invoke("itemKey");
			return itemStore;
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	public Long addToItemStore(Item item) {
		//Check.isValid("item", item);
		try {
			Long itemId = invoke("item");
			return itemId;
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	public List<Long> addToItemStoreAsList(List<Item> itemList) {
		//Check.isValid("itemList", itemList);
		try {
			List<Long> itemIds = invoke("itemList");
			return itemIds;
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	public List<Long> addToItemStoreAsMap(Map<String, Item> itemMap) {
		//Check.isValid("itemMap", itemMap);
		try {
			List<Long> itemIds = invoke("itemMap");
			return itemIds;
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	public void removeAllItemStore() {
		try {
			invoke("");
			log.info("#### [eventLogger-client]: RemoveAllItemStore request sent");
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	public void removeFromItemStore(Item item) {
		//Check.isValid("item", item);
		try {
			invoke(item);
			log.info("#### [eventLogger-client]: RemoveFromItemStore request sent");
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	public void removeFromItemStoreById(Long itemId) {
		//Check.isValid("itemId", itemId);
		try {
			invoke(itemId);
			log.info("#### [eventLogger-client]: RemoveFromItemStore request sent");
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	public void removeFromItemStoreByKey(String itemKey) {
		//Check.isValid("itemKey", itemKey);
		try {
			invoke(itemKey);
			log.info("#### [eventLogger-client]: RemoveFromItemStore request sent");
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	public void removeFromItemStoreAsList(List<Item> itemList) {
		//Check.isValid("itemList", itemList);
		try {
			invoke(itemList);
			log.info("#### [eventLogger-client]: RemoveFromItemStore request sent");
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
