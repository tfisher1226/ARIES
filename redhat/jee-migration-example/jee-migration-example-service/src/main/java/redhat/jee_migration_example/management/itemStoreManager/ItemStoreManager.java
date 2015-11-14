package redhat.jee_migration_example.management.itemStoreManager;

import java.util.Collection;
import java.util.Map;

import redhat.jee_migration_example.Item;


public interface ItemStoreManager {
	
	public String ID = "redhat.jee-migration-example.itemStoreManager";
	
	public Collection<Item> getAllItemStore();
	
	public Item getFromItemStoreById(Long id);
	
	public Item getFromItemStoreByKey(String key);
	
	public Long addToItemStore(String key, Item item);
	
	public Collection<Long> addToItemStore(Map<String, Item> itemMap);
	
	public void removeAllItemStore();
	
	public void removeFromItemStore(Item item);
	
	public void removeFromItemStoreById(Long id);
	
	public void removeFromItemStoreByKey(String key);
	
	public void removeFromItemStore(Map<String, Item> itemMap);
	
}
