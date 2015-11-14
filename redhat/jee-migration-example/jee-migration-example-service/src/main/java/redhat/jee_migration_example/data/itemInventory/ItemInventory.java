package redhat.jee_migration_example.data.itemInventory;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;

import org.aries.data.AbstractDataUnit;

import redhat.jee_migration_example.Item;
import redhat.jee_migration_example.ItemInventoryRepository;
import redhat.jee_migration_example.util.JeeMigrationExampleFixture;


@Stateful
@LocalBean
@ConcurrencyManagement(BEAN)
@TransactionAttribute(REQUIRED)
@TransactionManagement(CONTAINER)
public class ItemInventory extends AbstractDataUnit {
	
	private static final String UNIT_NAME = "ItemInventory";
	
	@Inject
	protected ItemInventoryRepository itemInventoryRepository;
	
	private Object mutex = new Object();
	
	
	public ItemInventory() {
		//nothing for now
	}
	
	
	@Override
	public String getUnitName() {
		return UNIT_NAME;
	}
	
	@PostConstruct
	protected void initialize() {
		//nothing for now
	}
	
	@Override
	public void clearContext() {
		itemInventoryRepository.clearContext();
	}
	
	public List<Item> getAllItemStore() {
		return itemInventoryRepository.getAllItemStoreRecords();
	}
	
	public Map<String, Item> getAllItemStoreAsMap() {
		Collection<Item> itemList = itemInventoryRepository.getAllItemStoreRecords();
		Map<String, Item> itemMap = JeeMigrationExampleFixture.createMap_Item(itemList);
		return itemMap;
	}
	
	public Item getFromItemStore(Long id) {
		return itemInventoryRepository.getItemStoreRecord(id);
	}

	public Item getFromItemStore(String key) {
		return itemInventoryRepository.getItemStoreRecord(key);
	}

//	public List<Item> getFromItemStore(Collection<Long> itemIds) {
//		return itemInventoryRepository.getItemStoreRecords(itemIds);
//	}

	public Long addToItemStore(String key, Item item) {
		return itemInventoryRepository.addItemStoreRecord(key, item);
	}
	
//	public List<Long> addToItemStore(Collection<Item> itemList) {
//		return itemInventoryRepository.addItemStoreRecords(itemList);
//	}
	
	public List<Long> addToItemStore(Map<String, Item> itemMap) {
		return itemInventoryRepository.addItemStoreRecords(itemMap);
	}
	
	public void removeAllItemStore() {
		itemInventoryRepository.removeAllItemStoreRecords();
	}
	
	public void removeFromItemStore(Long id) {
		itemInventoryRepository.removeItemStoreRecord(id);
	}
	
	public void removeFromItemStore(String key) {
		itemInventoryRepository.removeItemStoreRecord(key);
	}
	
//	public void removeFromItemStore(String key, Item item) {
//		itemInventoryRepository.removeItemStoreRecord(key, item);
//	}
	
	public void removeFromItemStore(Map<String, Item> itemMap) {
		itemInventoryRepository.removeItemStoreRecords(itemMap);
	}
	
}
