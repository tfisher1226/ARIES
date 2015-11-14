package redhat.jee_migration_example.management.itemManager;

import java.util.List;
import java.util.Map;

import org.aries.message.Message;
import org.aries.message.MessageInterceptor;
import org.aries.util.ExceptionUtil;

import redhat.jee_migration_example.Item;


@SuppressWarnings("serial")
public class ItemManagerInterceptor extends MessageInterceptor<ItemManager> implements ItemManager {
	
	@Override
	public List<Item> getAllItem() {
		try {
			log.info("#### [eventLogger-client]: getAllItem() sending");
			Message request = createMessage("getAllItem");
			Message response = getProxy().invoke(request);
			List<Item> item = response.getPart("item");
			return item;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Map<String, Item> getAllItemAsMap() {
		try {
			log.info("#### [eventLogger-client]: getAllItemAsMap() sending");
			Message request = createMessage("getAllItemAsMap");
			Message response = getProxy().invoke(request);
			Map<String, Item> item = response.getPart("item");
			return item;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Item getFromItemById(Long itemId) {
		try {
			log.info("#### [eventLogger-client]: getFromItemById() sending");
			Message request = createMessage("getFromItemById");
			request.addPart("itemId", itemId);
			Message response = getProxy().invoke(request);
			Item item = response.getPart("item");
			return item;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Item getFromItemByKey(String itemKey) {
		try {
			log.info("#### [eventLogger-client]: getFromItemByKey() sending");
			Message request = createMessage("getFromItemByKey");
			request.addPart("itemKey", itemKey);
			Message response = getProxy().invoke(request);
			Item item = response.getPart("item");
			return item;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Item> getFromItemByIds(List<Long> itemIds) {
		try {
			log.info("#### [eventLogger-client]: getFromItemByIds() sending");
			Message request = createMessage("getFromItemByIds");
			request.addPart("itemIds", itemIds);
			Message response = getProxy().invoke(request);
			List<Item> item = response.getPart("item");
			return item;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Map<String, Item> getFromItemByKeys(List<String> itemKeys) {
		try {
			log.info("#### [eventLogger-client]: getFromItemByKeys() sending");
			Message request = createMessage("getFromItemByKeys");
			request.addPart("itemKeys", itemKeys);
			Message response = getProxy().invoke(request);
			Map<String, Item> item = response.getPart("item");
			return item;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Item> getMatchingItem(List<Item> itemList) {
		try {
			log.info("#### [eventLogger-client]: getMatchingItem() sending");
			Message request = createMessage("getMatchingItem");
			request.addPart("itemList", itemList);
			Message response = getProxy().invoke(request);
			List<Item> item = response.getPart("item");
			return item;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Map<String, Item> getMatchingItemAsMap(List<Item> itemList) {
		try {
			log.info("#### [eventLogger-client]: getMatchingItemAsMap() sending");
			Message request = createMessage("getMatchingItemAsMap");
			request.addPart("itemList", itemList);
			Message response = getProxy().invoke(request);
			Map<String, Item> item = response.getPart("item");
			return item;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Long addToItem(Item item) {
		try {
			log.info("#### [eventLogger-client]: addToItem() sending");
			Message request = createMessage("addToItem");
			request.addPart("item", item);
			Message response = getProxy().invoke(request);
			Long itemId = response.getPart("itemId");
			return itemId;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Long> addToItemAsList(List<Item> itemList) {
		try {
			log.info("#### [eventLogger-client]: addToItemAsList() sending");
			Message request = createMessage("addToItemAsList");
			request.addPart("itemList", itemList);
			Message response = getProxy().invoke(request);
			List<Long> itemIds = response.getPart("itemIds");
			return itemIds;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Long> addToItemAsMap(Map<String, Item> itemMap) {
		try {
			log.info("#### [eventLogger-client]: addToItemAsMap() sending");
			Message request = createMessage("addToItemAsMap");
			request.addPart("itemMap", itemMap);
			Message response = getProxy().invoke(request);
			List<Long> itemIds = response.getPart("itemIds");
			return itemIds;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeAllItem() {
		try {
			log.info("#### [eventLogger-client]: removeAllItem() sending");
			Message request = createMessage("removeAllItem");
			getProxy().send(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeFromItem(Item item) {
		try {
			log.info("#### [eventLogger-client]: removeFromItem() sending");
			Message request = createMessage("removeFromItem");
			request.addPart("item", item);
			getProxy().send(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeFromItemById(Long itemId) {
		try {
			log.info("#### [eventLogger-client]: removeFromItemById() sending");
			Message request = createMessage("removeFromItemById");
			request.addPart("itemId", itemId);
			getProxy().send(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeFromItemByKey(String itemKey) {
		try {
			log.info("#### [eventLogger-client]: removeFromItemByKey() sending");
			Message request = createMessage("removeFromItemByKey");
			request.addPart("itemKey", itemKey);
			getProxy().send(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeFromItemAsList(List<Item> itemList) {
		try {
			log.info("#### [eventLogger-client]: removeFromItemAsList() sending");
			Message request = createMessage("removeFromItemAsList");
			request.addPart("itemList", itemList);
			getProxy().send(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
