package redhat.jee_migration_example.data.itemInventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redhat.jee_migration_example.Item;
import redhat.jee_migration_example.util.JeeMigrationExampleHelper;

import common.tx.state.ServiceState;


public class ItemInventoryState extends ServiceState {
	
	private static final long serialVersionUID = 1L;
	
	private static final Log log = LogFactory.getLog(ItemInventoryState.class);
	
	public static final String LATEST_STATE_FILENAME = "ItemInventory_CurrentState";
	
	public static final String SHADOW_STATE_FILENAME = "ItemInventory_ShadowState";
	
	private Map<String, Item> itemStore;
	
	
	public ItemInventoryState() {
		this.itemStore = new LinkedHashMap<String, Item>();
	}
	
	public ItemInventoryState(ItemInventoryState parent) {
		super(parent);
		this.itemStore = new LinkedHashMap<String, Item>(parent.getAllItemStoreAsMap());
	}
	
	
	public ItemInventoryState createState() {
		return new ItemInventoryState();
	}
	
	public ItemInventoryState resetState() {
		ItemInventoryState state = createState();
		return state;
	}
	
	@SuppressWarnings("unchecked")
	public ItemInventoryState getDerivedState() {
		return new ItemInventoryState(this);
	}
	
	public List<Item> getAllItemStore() {
		synchronized (itemStore) {
			List<Item> itemList = new ArrayList<Item>(itemStore.values());
			return itemList;
		}
	}
	
	public Map<String, Item> getAllItemStoreAsMap() {
		synchronized (itemStore) {
			return new HashMap<String, Item>(itemStore);
		}
	}
	
	public Item getFromItemStore(Long id) {
		synchronized (itemStore) {
			Iterator<Item> iterator = itemStore.values().iterator();
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
	
	public Item getFromItemStore(String key) {
		synchronized (itemStore) {
			Iterator<String> iterator = itemStore.keySet().iterator();
			while (iterator.hasNext()) {
				String key2 = iterator.next();
				if (key2 != null && key2.equals(key)) {
					return itemStore.get(key);
				}
			}
			return null;
		}
	}
	
//	public List<Item> getFromItemStore(Collection<Long> itemIds) {
//		synchronized (itemStore) {
//			List<Item> list = new ArrayList<Item>(itemIds.size());
//			Iterator<Long> iterator = itemIds.iterator();
//			while (iterator.hasNext()) {
//				Long itemId = iterator.next();
//				Item item = getFromItemStore(itemId);
//				if (item != null) {
//					list.add(item);
//				}
//			}
//			return list;
//		}
//	}
	
	public void addToItemStore(Item item) {
		synchronized (itemStore) {
			String itemKey = JeeMigrationExampleHelper.getItemKey(item);
			itemStore.put(itemKey, item);
		}
	}
	
//	public void addToItemStore(Collection<Item> itemList) {
//		Map<String, Item> itemMap = JeeMigrationExampleHelper.createItemMap(itemList);
//		addToItemStore(itemMap);
//	}
	
	public void addToItemStore(Map<String, Item> itemMap) {
		synchronized (itemStore) {
			itemStore.putAll(itemMap);
		}
	}
	
	public void removeAllItemStore() {
		synchronized (itemStore) {
			itemStore.clear();
		}
	}
	
	public void removeFromItemStore(Item item) {
		synchronized (itemStore) {
			String itemKey = JeeMigrationExampleHelper.getItemKey(item);
			itemStore.remove(itemKey);
		}
	}
	
	public void removeFromItemStore(Long itemId) {
		synchronized (itemStore) {
			Item item = getFromItemStore(itemId);
			if (item != null) {
				removeFromItemStore(item);
			}
		}
	}
	
	public void removeFromItemStore(Collection<Item> itemList) {
		synchronized (itemStore) {
			Iterator<Item> iterator = itemList.iterator();
			while (iterator.hasNext()) {
				Item item = iterator.next();
				removeFromItemStore(item);
			}
		}
	}
	
}
