package redhat.jee_migration_example.management.itemManager;

import java.util.List;
import java.util.Map;

import redhat.jee_migration_example.Item;


public interface ItemManager {
	
	public String ID = "redhat.jee-migration-example.itemManager";
	
	public List<Item> getAllItem();
	
	public Map<String, Item> getAllItemAsMap();
	
	public Item getFromItemById(Long itemId);
	
	public Item getFromItemByKey(String itemKey);
	
	public List<Item> getFromItemByIds(List<Long> itemIds);
	
	public Map<String, Item> getFromItemByKeys(List<String> itemKeys);
	
	public List<Item> getMatchingItem(List<Item> itemList);
	
	public Map<String, Item> getMatchingItemAsMap(List<Item> itemList);
	
	public Long addToItem(Item item);
	
	public List<Long> addToItemAsList(List<Item> itemList);
	
	public List<Long> addToItemAsMap(Map<String, Item> itemMap);
	
	public void removeAllItem();
	
	public void removeFromItem(Item item);
	
	public void removeFromItemById(Long itemId);
	
	public void removeFromItemByKey(String itemKey);
	
	public void removeFromItemAsList(List<Item> itemList);
	
}
