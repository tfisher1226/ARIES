package redhat.jee_migration_example.management.itemStoreManager;

import java.util.List;
import java.util.Map;

import redhat.jee_migration_example.Item;


public interface ItemStoreManager {
	
	public String ID = "redhat.jee-migration-example.itemStoreManager";
	
	public List<Item> getAllItemStore();
	
	public Map<String, Item> getAllItemStoreAsMap();
	
	public Item getFromItemStoreById(Long itemId);
	
	public Item getFromItemStoreByKey(String itemKey);
	
	public Long addToItemStore(Item item);
	
	public List<Long> addToItemStoreAsList(List<Item> itemList);
	
	public List<Long> addToItemStoreAsMap(Map<String, Item> itemMap);
	
	public void removeAllItemStore();
	
	public void removeFromItemStore(Item item);
	
	public void removeFromItemStoreById(Long itemId);
	
	public void removeFromItemStoreByKey(String itemKey);
	
	public void removeFromItemStoreAsList(List<Item> itemList);
	
}
