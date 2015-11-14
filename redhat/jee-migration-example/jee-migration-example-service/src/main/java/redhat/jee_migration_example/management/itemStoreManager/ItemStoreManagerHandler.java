package redhat.jee_migration_example.management.itemStoreManager;

import java.util.List;
import java.util.Map;

import org.aries.tx.Transactional;

import redhat.jee_migration_example.Item;


public interface ItemStoreManagerHandler extends Transactional {
	
	public List<Item> getAllItemStore();
	
	public Item getFromItemStore(Long id);
	
	public Item getFromItemStore(String key);
	
	public Long addToItemStore(String key, Item item);
	
	public List<Long> addToItemStore(Map<String, Item> itemMap);
	
	public void removeAllItemStore();
	
	public void removeFromItemStore(Long id);
	
	public void removeFromItemStore(String key);
	
	public void removeFromItemStore(String key, Item item);
	
	public void removeFromItemStore(Map<String, Item> itemMap);
	
}
