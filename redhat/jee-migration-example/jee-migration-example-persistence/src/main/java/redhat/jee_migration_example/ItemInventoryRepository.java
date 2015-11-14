package redhat.jee_migration_example;

import java.util.List;
import java.util.Map;


public interface ItemInventoryRepository {
	
	public void clearContext();
	
	public List<Item> getAllItemStoreRecords();
	
	public Item getItemStoreRecord(Long id);
	
	public Item getItemStoreRecord(String key);
	
	public Long addItemStoreRecord(String key, Item item);
	
	public List<Long> addItemStoreRecords(Map<String, Item> itemMap);

	public void saveItemStoreRecord(String key, Item item);
	
	public void saveItemStoreRecords(Map<String, Item> itemMap);
	
	public void removeAllItemStoreRecords();
	
	public void removeItemStoreRecord(Long id);
	
	public void removeItemStoreRecord(String key);
	
	public void removeItemStoreRecord(String key, Item item);
	
	public void removeItemStoreRecords(Map<String, Item> itemMap);
	
	public void importItemStoreRecords();
	
}
