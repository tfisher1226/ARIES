package redhat.jee_migration_example.data.itemInventory;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.transaction.TransactionSynchronizationRegistry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redhat.jee_migration_example.EventLoggerContext;
import redhat.jee_migration_example.Item;
import redhat.jee_migration_example.util.JeeMigrationExampleFixture;
import common.jmx.MBeanUtil;
import common.tx.state.AbstractStateManager;


@Startup
@Stateful
@LocalBean
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
@TransactionAttribute(REQUIRED)
public class ItemInventoryManager extends AbstractStateManager<ItemInventoryState> implements ItemInventoryManagerMBean {
	
	private static final Log log = LogFactory.getLog(ItemInventoryManager.class);
	
	@Inject
	private EventLoggerContext eventLoggerContext;
	
	@Inject
	private ItemInventory itemInventory;
	
	@Inject
	private ItemInventoryProcessor stateProcessor;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	public ItemInventoryManager() {
		//nothing for now
	}
	
	
	public ItemInventory getItemInventory() {
		return itemInventory;
	}
	
	public void setItemInventory(ItemInventory itemInventory) {
		this.itemInventory = itemInventory;
	}
	
	public ItemInventoryProcessor getStateProcessor() {
		return stateProcessor;
	}
	
	public void setStateProcessor(ItemInventoryProcessor stateProcessor) {
		this.stateProcessor = stateProcessor;
	}
	
	@Override
	public String getName() {
		return "ItemInventoryManager";
	}
	
	@PostConstruct
	public void registerWithJMX() {
		MBeanUtil.registerMBean(this, MBEAN_NAME);
	}
	
	@PreDestroy
	public void unregisterWithJMX() {
		MBeanUtil.unregisterMBean(MBEAN_NAME);
	}
	
	public ItemInventoryState createState() {
		return new ItemInventoryState();
	}
	
	public ItemInventoryState resetState() {
		ItemInventoryState state = createState();
		return state;
	}
	
	public void updateState() {
		//TODO if this fails then we need to mark global TX as rollback only
		updateState(stateProcessor);
	}
	
	public boolean saveState(ItemInventoryState state) {
		try {
			itemInventory.addToItemStore(state.getAllItemStoreAsMap());
			return true;
			
		} catch (Exception e) {
			return false;
		}
	}
	
	public void commitState() {
		stateProcessor.updateState(currentState);
	}
	
	@Override
	public void clearContext() {
		itemInventory.clearContext();
	}
	
	@Override
	public List<Item> getAllItemStore() {
		if (eventLoggerContext.isGlobalTransactionActive())
			return currentState.getAllItemStore();
		return itemInventory.getAllItemStore();
	}
	
	@Override
	public Map<String, Item> getAllItemStoreAsMap() {
		if (eventLoggerContext.isGlobalTransactionActive())
			return currentState.getAllItemStoreAsMap();
		return itemInventory.getAllItemStoreAsMap();
	}
	
	@Override
	public Item getFromItemStore(Long id) {
		if (eventLoggerContext.isGlobalTransactionActive())
			return currentState.getFromItemStore(id);
		return itemInventory.getFromItemStore(id);
	}
	
	@Override
	public Item getFromItemStore(String key) {
		if (eventLoggerContext.isGlobalTransactionActive())
			return currentState.getFromItemStore(key);
		return itemInventory.getFromItemStore(key);
	}
	
//	@Override
//	public List<Item> getFromItemStore(Collection<Long> itemIds) {
//		if (eventLoggerContext.isGlobalTransactionActive())
//			return currentState.getFromItemStore(itemIds);
//		return itemInventory.getFromItemStore(itemIds);
//	}
	
	@Override
	public Long addToItemStore(String key, Item item) {
		if (eventLoggerContext.isGlobalTransactionActive()) {
			stateProcessor.addToPendingItemStore(key, item);
			return null;
		} else {
			return itemInventory.addToItemStore(key, item);
		}
	}
	
//	@Override
//	public List<Long> addToItemStore(Collection<Item> itemList) {
//		if (eventLoggerContext.isGlobalTransactionActive()) {
//			Map<String, Item> itemMap = JeeMigrationExampleFixture.createMap_Item(itemList);
//			stateProcessor.addToPendingItemStore(itemMap);
//			return null;
//		} else {
//			return itemInventory.addToItemStore(itemList);
//		}
//	}
	
	@Override
	public List<Long> addToItemStore(Map<String, Item> itemMap) {
		if (eventLoggerContext.isGlobalTransactionActive()) {
			stateProcessor.addToPendingItemStore(itemMap);
			return null;
		} else {
			return itemInventory.addToItemStore(itemMap);
		}
	}
	
	@Override
	public void removeAllItemStore() {
		if (eventLoggerContext.isGlobalTransactionActive())
			stateProcessor.removeAllPendingItemStore();
		else itemInventory.removeAllItemStore();
	}
	
	@Override
	public void removeFromItemStore(Long id) {
		if (eventLoggerContext.isGlobalTransactionActive())
			stateProcessor.removeFromPendingItemStore(id);
		else itemInventory.removeFromItemStore(id);
	}
	
	@Override
	public void removeFromItemStore(String key) {
		if (eventLoggerContext.isGlobalTransactionActive())
			stateProcessor.removeFromPendingItemStore(key);
		else itemInventory.removeFromItemStore(key);
	}
	
	@Override
	public void removeFromItemStore(Map<String, Item> itemMap) {
		if (eventLoggerContext.isGlobalTransactionActive())
			stateProcessor.removeFromPendingItemStore(itemMap);
		else itemInventory.removeFromItemStore(itemMap);
	}
	
}
