package redhat.jee_migration_example.management.itemStoreManager;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.List;
import java.util.Map;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.message.Message;
import org.aries.message.MessageInterceptor;

import redhat.jee_migration_example.Item;


@Stateless
@LocalBean
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
public class ItemStoreManagerInterceptor extends MessageInterceptor<ItemStoreManager> {
	
	private static final Log log = LogFactory.getLog(ItemStoreManagerInterceptor.class);
	
	@Inject
	protected ItemStoreManagerHandler itemStoreManagerHandler;
	
	
	@TransactionAttribute(REQUIRED)
	public Message getAllItemStore(Message message) {
		try {
			List<Item> itemStore = itemStoreManagerHandler.getAllItemStore();
			Assert.notNull(itemStore, "ItemStore must exist");
			message.addPart("itemStore", itemStore);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message getFromItemStoreById(Message message) {
		try {
			Long itemId = message.getPart("itemId");
			Item itemStore = itemStoreManagerHandler.getFromItemStore(itemId);
			Assert.notNull(itemStore, "ItemStore must exist");
			message.addPart("itemStore", itemStore);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message getFromItemStoreByKey(Message message) {
		try {
			String itemKey = message.getPart("itemKey");
			Item itemStore = itemStoreManagerHandler.getFromItemStore(itemKey);
			Assert.notNull(itemStore, "ItemStore must exist");
			message.addPart("itemStore", itemStore);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message addToItemStore(Message message) {
		try {
			String key = message.getPart("key");
			Item item = message.getPart("item");
			Long itemId = itemStoreManagerHandler.addToItemStore(key, item);
			Assert.notNull(itemId, "ItemId must exist");
			message.addPart("itemId", itemId);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message addToItemStoreAsList(Message message) {
		try {
			Map<String, Item> itemMap = message.getPart("itemMap");
			List<Long> itemIds = itemStoreManagerHandler.addToItemStore(itemMap);
			Assert.notNull(itemIds, "ItemIds must exist");
			message.addPart("itemIds", itemIds);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message addToItemStoreAsMap(Message message) {
		try {
			Map<String, Item> itemMap = message.getPart("itemMap");
			List<Long> itemIds = itemStoreManagerHandler.addToItemStore(itemMap);
			Assert.notNull(itemIds, "ItemIds must exist");
			message.addPart("itemIds", itemIds);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message removeAllItemStore(Message message) {
		try {
			itemStoreManagerHandler.removeAllItemStore();
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message removeFromItemStore(Message message) {
		try {
			String key = message.getPart("key");
			Item item = message.getPart("item");
			itemStoreManagerHandler.removeFromItemStore(key, item);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message removeFromItemStoreById(Message message) {
		try {
			Long itemId = message.getPart("itemId");
			itemStoreManagerHandler.removeFromItemStore(itemId);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message removeFromItemStoreByKey(Message message) {
		try {
			String itemKey = message.getPart("itemKey");
			itemStoreManagerHandler.removeFromItemStore(itemKey);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message removeFromItemStoreAsList(Message message) {
		try {
			Map<String, Item> itemMap = message.getPart("itemMap");
			itemStoreManagerHandler.removeFromItemStore(itemMap);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
}
