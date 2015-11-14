package redhat.jee_migration_example.data.itemInventory;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.management.MXBean;

import redhat.jee_migration_example.Item;


@MXBean
public interface ItemInventoryManagerMBean {
	
	public static final String MBEAN_NAME = "redhat.jee-migration-example.data:name=ItemInventoryManager";
	
	public void clearContext();
	
	public void updateState();
	
	public void commitState();
	
	public List<Item> getAllItemStore();
	
	public Map<String, Item> getAllItemStoreAsMap();
	
	public Item getFromItemStore(Long itemId);
	
	public Item getFromItemStore(String itemKey);
	
	//public List<Item> getFromItemStore(Collection<Long> itemIds);
	
	//public Long addToItemStore(Item item);
	
	public Long addToItemStore(String itemKey, Item item);
	
	//public List<Long> addToItemStore(Collection<Item> itemList);
	
	public List<Long> addToItemStore(Map<String, Item> itemMap);
	
	public void removeAllItemStore();
	
	//public void removeFromItemStore(Item item);
	
	public void removeFromItemStore(Long itemId);

	public void removeFromItemStore(String itemKey);

	//public void removeFromItemStore(Collection<Item> itemList);
	
	public void removeFromItemStore(Map<String, Item> itemMap);
	
}
