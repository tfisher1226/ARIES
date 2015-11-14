package redhat.jee_migration_example.data.itemInventory;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import common.tx.management.AbstractMonitor;

import redhat.jee_migration_example.Item;


public class ItemInventoryMonitor extends AbstractMonitor {
	
	public ItemInventoryMonitor(String host, int port) {
		setHost(host);
		setPort(port);
		getURL();
	}
	
	
	public String getMBeanName() {
		return ItemInventoryManagerMBean.MBEAN_NAME;
	}
	
	public Class<?> getMBeanClass() {
		return ItemInventoryManagerMBean.class;
	}
	
	public List<Item> getAllItemStore() throws Exception {
		return getList("getAllItemStore");
	}
	
	public Map<String, Item> getAllItemStoreAsMap() throws Exception {
		return getMap("getAllItemStoreAsMap");
	}
	
	public Item getFromItemStore(Long itemId) throws Exception {
		return getObject("getFromItemStore", "java.lang.Long", itemId);
	}
	
	public List<Item> getFromItemStore(Collection<Long> itemIds) throws Exception {
		return getList("getFromItemStore", "java.util.Collection", itemIds);
	}
	
	public Long addToItemStore(Item item) throws Exception {
		return getObject("addToItemStore", "redhat.jee_migration_example.Item", item);
	}
	
	public List<Long> addToItemStore(Collection<Item> itemList) throws Exception {
		return getList("addToItemStore", "java.util.Collection", itemList);
	}
	
	public List<Long> addToItemStore(Map<String, Item> itemMap) throws Exception {
		return getList("addToItemStore", "java.util.Map", itemMap);
	}
	
	public void removeAllItemStore() throws Exception {
		invoke("removeAllItemStore");
	}
	
	public void removeFromItemStore(Item item) throws Exception {
		invoke("removeFromItemStore", "redhat.jee_migration_example.Item", item);
	}
	
	public void removeFromItemStore(Long itemId) throws Exception {
		invoke("removeFromItemStore", "java.lang.Long", itemId);
	}
	
	public void removeFromItemStore(Collection<Item> itemList) throws Exception {
		invoke("removeFromItemStore", "java.util.Collection", itemList);
	}
	
}
