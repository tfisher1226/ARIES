package redhat.jee_migration_example.data.itemInventory;

import static javax.ejb.ConcurrencyManagementType.BEAN;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redhat.jee_migration_example.Item;
import redhat.jee_migration_example.util.JeeMigrationExampleHelper;

import common.tx.state.ServiceStateProcessor;


@Startup
@Singleton
@LocalBean
@ConcurrencyManagement(BEAN)
public class ItemInventoryProcessor implements ServiceStateProcessor<ItemInventoryState> {
	
	private static final Log log = LogFactory.getLog(ItemInventoryProcessor.class);
	
	private Map<String, Item> pendingItemStore = new LinkedHashMap<String, Item>();
	
	
	public ItemInventoryProcessor() {
		// nothing for now
	}
	
	
	public void resetState(ItemInventoryState state) {
		state.removeAllItemStore();
	}
	
	public boolean validateState(ItemInventoryState state) {
		return pendingItemStore != null;
	}
	
	public void updateState(ItemInventoryState state) {
		state.addToItemStore(pendingItemStore);
	}
	
	public void processRequest() {
		//nothing for now
	}
	
	public List<Item> getAllPendingItemStore() {
		synchronized (pendingItemStore) {
			List<Item> itemList = new ArrayList<Item>(pendingItemStore.values());
			return itemList;
		}
	}
	
	public Map<String, Item> getAllPendingItemStoreAsMap() {
		synchronized (pendingItemStore) {
			Map<String, Item> itemMap = new HashMap<String, Item>(pendingItemStore);
			return itemMap;
		}
	}
	
	public Item getFromPendingItemStore(Long id) {
		synchronized (pendingItemStore) {
			Iterator<Item> iterator = pendingItemStore.values().iterator();
			while (iterator.hasNext()) {
				Item item = iterator.next();
				Long id2 = item.getId();
				if (id2 != null && id2.equals(id)) {
					return item;
				}
			}
			return null;
		}
	}
	
	public Item getFromPendingItemStore(String key) {
		synchronized (pendingItemStore) {
			return pendingItemStore.get(key);
		}
	}
	
	public void setPendingItemStore(Map<String, Item> itemMap) {
		synchronized (pendingItemStore) {
			pendingItemStore = new LinkedHashMap<String, Item>(itemMap);
		}
	}
	
	public void addToPendingItemStore(String key, Item item) {
		synchronized (pendingItemStore) {
			//String itemKey = JeeMigrationExampleHelper.getItemKey(item);
			pendingItemStore.put(key, item);
		}
	}
	
//	public void addToPendingItemStore(Collection<Item> itemList) {
//		synchronized (pendingItemStore) {
//			Map<String, Item> itemMap = JeeMigrationExampleHelper.createItemMap(itemList);
//			pendingItemStore.putAll(itemMap);
//		}
//	}
	
	public void addToPendingItemStore(Map<String, Item> itemMap) {
		synchronized (pendingItemStore) {
			pendingItemStore.putAll(itemMap);
		}
	}
	
	public void removeAllPendingItemStore() {
		synchronized (pendingItemStore) {
			pendingItemStore.clear();
		}
	}
	
	public void removeFromPendingItemStore(Item item) {
		synchronized (pendingItemStore) {
			String itemKey = JeeMigrationExampleHelper.getItemKey(item);
			pendingItemStore.remove(itemKey);
		}
	}
	
	public void removeFromPendingItemStore(Long itemId) {
		synchronized (pendingItemStore) {
			Item item = getFromPendingItemStore(itemId);
			if (item != null) {
				String itemKey = JeeMigrationExampleHelper.getItemKey(item);
				removeFromPendingItemStore(itemKey);
			}
		}
	}
	
	public void removeFromPendingItemStore(String itemKey) {
		synchronized (pendingItemStore) {
			pendingItemStore.remove(itemKey);
		}
	}
	
	public void removeFromPendingItemStore(Map<String, Item> itemMap) {
		synchronized (pendingItemStore) {
			Collection<String> itemKeys = itemMap.keySet();
			Iterator<String> iterator = itemKeys.iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				pendingItemStore.remove(key);
			}
		}
	}
	
}
