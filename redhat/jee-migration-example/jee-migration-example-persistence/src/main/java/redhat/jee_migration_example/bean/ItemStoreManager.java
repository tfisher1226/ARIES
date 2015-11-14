package redhat.jee_migration_example.bean;

import java.util.List;
import java.util.Map;

import redhat.jee_migration_example.Item;


public interface ItemStoreManager {
	
	public void initialize();
	
	public void clearContext();
	
	public List<Item> getAllItemStoreRecords();
	
	public Item getItemStoreRecord(Long id);
	
	public Item getItemStoreRecord(String key);
	
	public Long addItemStoreRecord(String key, Item item);
	
	public List<Long> addItemStoreRecords(Map<String, Item> itemList);
	
	public void saveItemStoreRecord(String key, Item item);
	
	public void saveItemStoreRecords(Map<String, Item> itemList);
	
	public void removeAllItemStoreRecords();
	
	public void removeItemStoreRecord(Long id);
	
	public void removeItemStoreRecord(String key);
	
	public void removeItemStoreRecord(String key, Item item);
	
	public void removeItemStoreRecords(Map<String, Item> itemList);
	
	public void importItemStoreRecords();
	
}
