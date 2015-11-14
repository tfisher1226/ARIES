package redhat.jee_migration_example.management.itemStoreManager;

import java.util.List;
import java.util.Map;

import org.aries.message.Message;
import org.aries.message.MessageInterceptor;
import org.aries.util.ExceptionUtil;

import redhat.jee_migration_example.Item;


@SuppressWarnings("serial")
public class ItemStoreManagerInterceptor extends MessageInterceptor<ItemStoreManager> implements ItemStoreManager {
	
	@Override
	public List<Item> getAllItemStore() {
		try {
			log.info("#### [eventLogger-client]: getAllItemStore() sending");
			Message request = createMessage("getAllItemStore");
			Message response = getProxy().invoke(request);
			List<Item> itemStore = response.getPart("itemStore");
			return itemStore;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Map<String, Item> getAllItemStoreAsMap() {
		try {
			log.info("#### [eventLogger-client]: getAllItemStoreAsMap() sending");
			Message request = createMessage("getAllItemStoreAsMap");
			Message response = getProxy().invoke(request);
			Map<String, Item> itemStore = response.getPart("itemStore");
			return itemStore;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Item getFromItemStoreById(Long itemId) {
		try {
			log.info("#### [eventLogger-client]: getFromItemStoreById() sending");
			Message request = createMessage("getFromItemStoreById");
			request.addPart("itemId", itemId);
			Message response = getProxy().invoke(request);
			Item itemStore = response.getPart("itemStore");
			return itemStore;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Item getFromItemStoreByKey(String itemKey) {
		try {
			log.info("#### [eventLogger-client]: getFromItemStoreByKey() sending");
			Message request = createMessage("getFromItemStoreByKey");
			request.addPart("itemKey", itemKey);
			Message response = getProxy().invoke(request);
			Item itemStore = response.getPart("itemStore");
			return itemStore;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Long addToItemStore(Item item) {
		try {
			log.info("#### [eventLogger-client]: addToItemStore() sending");
			Message request = createMessage("addToItemStore");
			request.addPart("item", item);
			Message response = getProxy().invoke(request);
			Long itemId = response.getPart("itemId");
			return itemId;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Long> addToItemStoreAsList(List<Item> itemList) {
		try {
			log.info("#### [eventLogger-client]: addToItemStoreAsList() sending");
			Message request = createMessage("addToItemStoreAsList");
			request.addPart("itemList", itemList);
			Message response = getProxy().invoke(request);
			List<Long> itemIds = response.getPart("itemIds");
			return itemIds;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Long> addToItemStoreAsMap(Map<String, Item> itemMap) {
		try {
			log.info("#### [eventLogger-client]: addToItemStoreAsMap() sending");
			Message request = createMessage("addToItemStoreAsMap");
			request.addPart("itemMap", itemMap);
			Message response = getProxy().invoke(request);
			List<Long> itemIds = response.getPart("itemIds");
			return itemIds;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeAllItemStore() {
		try {
			log.info("#### [eventLogger-client]: removeAllItemStore() sending");
			Message request = createMessage("removeAllItemStore");
			getProxy().send(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeFromItemStore(Item item) {
		try {
			log.info("#### [eventLogger-client]: removeFromItemStore() sending");
			Message request = createMessage("removeFromItemStore");
			request.addPart("item", item);
			getProxy().send(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeFromItemStoreById(Long itemId) {
		try {
			log.info("#### [eventLogger-client]: removeFromItemStoreById() sending");
			Message request = createMessage("removeFromItemStoreById");
			request.addPart("itemId", itemId);
			getProxy().send(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeFromItemStoreByKey(String itemKey) {
		try {
			log.info("#### [eventLogger-client]: removeFromItemStoreByKey() sending");
			Message request = createMessage("removeFromItemStoreByKey");
			request.addPart("itemKey", itemKey);
			getProxy().send(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeFromItemStoreAsList(List<Item> itemList) {
		try {
			log.info("#### [eventLogger-client]: removeFromItemStoreAsList() sending");
			Message request = createMessage("removeFromItemStoreAsList");
			request.addPart("itemList", itemList);
			getProxy().send(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
