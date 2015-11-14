package redhat.jee_migration_example.management.itemStoreManager;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.transaction.TransactionSynchronizationRegistry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.tx.service.AbstractServiceHandler;
import org.aries.tx.service.ServiceHandlerInterceptor;
import org.aries.util.ExceptionUtil;

import redhat.jee_migration_example.Item;
import redhat.jee_migration_example.ItemInventoryRepository;


@Stateful
@Local(ItemStoreManagerHandler.class)
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
@Interceptors(ServiceHandlerInterceptor.class)
public class ItemStoreManagerHandlerImpl extends AbstractServiceHandler implements ItemStoreManagerHandler {
	
	private static final Log log = LogFactory.getLog(ItemStoreManagerHandlerImpl.class);
	
	@Inject
	protected ItemInventoryRepository itemInventoryRepository;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	public String getName() {
		return "ItemStoreManager";
	}
	
	public String getDomain() {
		return "redhat";
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public List<Item> getAllItemStore() {
		log.info("#### [eventLogger]: ItemStoreManager request received");
		
		try {
			List<Item> resultList = itemInventoryRepository.getAllItemStoreRecords();
			return resultList;
		
		} catch (Throwable e) {
			log.error("Error", e);
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public Item getFromItemStore(Long itemId) {
		log.info("#### [eventLogger]: ItemStoreManager request received");
		
		try {
			Assert.notNull(itemId, "ItemId must be specified");
			Item result = itemInventoryRepository.getItemStoreRecord(itemId);
			return result;
		
		} catch (Throwable e) {
			log.error("Error", e);
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public Item getFromItemStore(String itemKey) {
		log.info("#### [eventLogger]: ItemStoreManager request received");
		
		try {
			Assert.notNull(itemKey, "ItemKey must be specified");
			Item result = itemInventoryRepository.getItemStoreRecord(itemKey);
			return result;
		
		} catch (Throwable e) {
			log.error("Error", e);
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public Long addToItemStore(String key, Item item) {
		log.info("#### [eventLogger]: ItemStoreManager request received");
		
		try {
			Assert.notNull(item, "Item must be specified");
			Long result = itemInventoryRepository.addItemStoreRecord(key, item);
			return result;
		
		} catch (Throwable e) {
			log.error("Error", e);
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
		}
	}
	
//	@Override
//	@TransactionAttribute(REQUIRED)
//	public List<Long> addToItemStore(List<Item> itemList) {
//		log.info("#### [eventLogger]: ItemStoreManager request received");
//		
//		try {
//			Assert.notNull(itemList, "ItemList must be specified");
//			List<Long> resultList = itemInventoryRepository.addItemStoreRecords(itemList);
//			return resultList;
//		
//		} catch (Throwable e) {
//			log.error("Error", e);
//			e = ExceptionUtil.getRootCause(e);
//			transactionSynchronizationRegistry.setRollbackOnly();
//			throw ExceptionUtil.rewrap(e);
//		}
//	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public List<Long> addToItemStore(Map<String, Item> itemMap) {
		log.info("#### [eventLogger]: ItemStoreManager request received");
		
		try {
			Assert.notNull(itemMap, "ItemStore must be specified");
			List<Long> idList = itemInventoryRepository.addItemStoreRecords(itemMap);
			return idList;
		
		} catch (Throwable e) {
			log.error("Error", e);
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public void removeAllItemStore() {
		log.info("#### [eventLogger]: ItemStoreManager request received");
		
		try {
			itemInventoryRepository.removeAllItemStoreRecords();
		
		} catch (Throwable e) {
			log.error("Error", e);
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public void removeFromItemStore(Long itemId) {
		log.info("#### [eventLogger]: ItemStoreManager request received");
		
		try {
			Assert.notNull(itemId, "ItemId must be specified");
			itemInventoryRepository.removeItemStoreRecord(itemId);
		
		} catch (Throwable e) {
			log.error("Error", e);
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public void removeFromItemStore(String itemKey) {
		log.info("#### [eventLogger]: ItemStoreManager request received");
		
		try {
			Assert.notNull(itemKey, "ItemKey must be specified");
			itemInventoryRepository.removeItemStoreRecord(itemKey);
		
		} catch (Throwable e) {
			log.error("Error", e);
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public void removeFromItemStore(String key, Item item) {
		log.info("#### [eventLogger]: ItemStoreManager request received");
		
		try {
			Assert.notNull(item, "Item must be specified");
			itemInventoryRepository.removeItemStoreRecord(key, item);
		
		} catch (Throwable e) {
			log.error("Error", e);
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public void removeFromItemStore(Map<String, Item> itemMap) {
		log.info("#### [eventLogger]: ItemStoreManager request received");
		
		try {
			Assert.notNull(itemMap, "ItemMap must be specified");
			itemInventoryRepository.removeItemStoreRecords(itemMap);
		
		} catch (Throwable e) {
			log.error("Error", e);
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
