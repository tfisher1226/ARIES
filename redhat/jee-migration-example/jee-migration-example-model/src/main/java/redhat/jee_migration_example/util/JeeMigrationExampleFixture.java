package redhat.jee_migration_example.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.aries.Assert;
import org.aries.util.BaseFixture;

import redhat.jee_migration_example.Event;
import redhat.jee_migration_example.Item;


public class JeeMigrationExampleFixture extends BaseFixture {
	
	private static long itemCount = 0L;
	
	private static long itemKeyCount = 0L;
	
	private static long eventCount = 0L;
	
	
	public static void reset() {
		itemCount = 0L;
		eventCount = 0L;
	}
	
	public static Item createEmpty_Item() {
		Item item = new Item();
		itemCount++;
		return item;
	}
	
	public static Item create_Item() {
		Item item = create_Item(1);
		return item;
	}
	
	public static Item create_Item(long index) {
		Item item = createEmpty_Item();
		long value = createValue(index, itemCount);
		item.setName("dummyName" + value);
		item.setId(value);
		return item;
	}
	
	public static List<Item> createEmptyList_Item() {
		return new ArrayList<Item>();
	}
	
	public static List<Item> createList_Item() {
		return createList_Item(2);
	}
	
	public static List<Item> createList_Item(int size) {
		return createList_Item(1, size);
	}
	
	public static List<Item> createList_Item(long index, int size) {
		List<Item> itemList = createEmptyList_Item();
		long limit = index + size;
		for (; index < limit; index++)
			itemList.add(create_Item(index));
		return itemList;
	}
	
	public static Set<Item> createEmptySet_Item() {
		return new HashSet<Item>();
	}
	
	public static Set<Item> createSet_Item() {
		return createSet_Item(2);
	}
	
	public static Set<Item> createSet_Item(int size) {
		return createSet_Item(1, size);
	}
	
	public static Set<Item> createSet_Item(long index, int size) {
		Set<Item> itemSet = createEmptySet_Item();
		long limit = index + size;
		for (; index < limit; index++)
			itemSet.add(create_Item(index));
		return itemSet;
	}
	
	public static Map<String, Item> createEmptyMap_Item() {
		return new HashMap<String, Item>();
	}
	
	public static Map<String, Item> createMap_Item() {
		return createMap_Item(2);
	}
	
	public static Map<String, Item> createMap_Item(int size) {
		Map<String, Item> bookMap = createEmptyMap_Item();
		for (long index=1; index <= size; index++)
			bookMap.put(create_ItemKey(index), create_Item(index));
		return bookMap;
	}
	
	public static Map<String, Item> createMap_Item(String itemKey, Item book) {
		Map<String, Item> bookMap = new HashMap<String, Item>();
		bookMap.put(itemKey, book);
		return bookMap;
	}

	public static Map<String, Item> createMap_Item(Collection<Item> itemList) {
		Map<String, Item> itemMap = new HashMap<String, Item>();
		Iterator<Item> iterator = itemList.iterator();
		for (long index=1; index <= itemList.size(); index++) {
			Item item = iterator.next();
			String itemKey = create_ItemKey(index);
			itemMap.put(itemKey, item);
		}
		return itemMap;
	}
	
	public static String create_ItemKey() {
		String itemKey = create_ItemKey(1);
		return itemKey;
	}
	
	public static String create_ItemKey(long index) {
		long value = createValue(index, itemKeyCount);
		String itemKey = "dummyItemKey" + value;
		return itemKey;
	}

	public static String create_ItemKey(Item item) {
		String itemKey = item.getName();
		return itemKey;
	}
	
	public static List<String> createEmptyList_ItemKey() {
		return new ArrayList<String>();
	}
	
	public static List<String> createList_ItemKey() {
		return createList_ItemKey(2);
	}
	
	public static List<String> createList_ItemKey(int size) {
		return createList_ItemKey(1, size);
	}
	
	public static List<String> createList_ItemKey(long index, int size) {
		List<String> itemKeyList = createEmptyList_ItemKey();
		long limit = index + size;
		for (; index < limit; index++)
			itemKeyList.add(create_ItemKey(index));
		return itemKeyList;
	}

	
	public static void assertItemExists(Collection<Item> itemList, Item item) {
		Assert.notNull(itemList, "Item list must be specified");
		Assert.notNull(item, "Item record must be specified");
		Iterator<Item> iterator = itemList.iterator();
		while (iterator.hasNext()) {
			Item item1 = iterator.next();
			if (item1.equals(item)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - Item should exist: "+item);
	}
	
	public static void assertItemCorrect(Item item) {
		long value = item.getId();
		assertObjectCorrect("Name", item.getName(), value);
	}
	
	public static void assertItemCorrect(Collection<Item> itemList) {
		Assert.isTrue(itemList.size() == 2, "Item count not correct");
		Iterator<Item> iterator = itemList.iterator();
		while (iterator.hasNext()) {
			Item item = iterator.next();
			assertItemCorrect(item);
		}
	}
	
	public static void assertSameItem(Item item1, Item item2) {
		assertSameItem(item1, item2, false, "");
	}
	
	public static void assertSameItem(Item item1, Item item2, String message) {
		assertSameItem(item1, item2, false, message);
	}
	
	public static void assertSameItem(Item item1, Item item2, boolean checkIds) {
		assertSameItem(item1, item2, checkIds, "");
	}
	
	public static void assertSameItem(Item item1, Item item2, boolean checkIds, String message) {
		assertObjectExists("Item1", item1);
		assertObjectExists("Item2", item2);
		if (checkIds)
			assertObjectEquals("Id", item1.getId(), item2.getId(), message);
		assertObjectEquals("Name", item1.getName(), item2.getName(), message);
	}
	
	public static void assertSameItem(Collection<Item> itemList1, Collection<Item> itemList2) {
		assertSameItem(itemList1, itemList2, false, "");
	}
	
	public static void assertSameItem(Collection<Item> itemList1, Collection<Item> itemList2, String message) {
		assertSameItem(itemList1, itemList2, false, message);
	}
	
	public static void assertSameItem(Collection<Item> itemList1, Collection<Item> itemList2, boolean checkIds) {
		assertSameItem(itemList1, itemList2, checkIds, "");
	}
	
	public static void assertSameItem(Collection<Item> itemList1, Collection<Item> itemList2, boolean checkIds, String message) {
		Assert.notNull(itemList1, "Item list1 must be specified");
		Assert.notNull(itemList2, "Item list2 must be specified");
		Assert.equals(itemList1.size(), itemList2.size(), "Item count not equal");
		Collection<Item> sortedRecords1 = ItemUtil.sortRecords(itemList1);
		Collection<Item> sortedRecords2 = ItemUtil.sortRecords(itemList2);
		Iterator<Item> list1Iterator = sortedRecords1.iterator();
		Iterator<Item> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			Item item1 = list1Iterator.next();
			Item item2 = list2Iterator.next();
			assertSameItem(item1, item2, checkIds, message);
		}
	}
	

	public static void assertSameItem(Map<String, Item> itemMap1, Map<String, Item> itemMap2) {
		assertSameItem(itemMap1, itemMap2, false, "");
	}
	
	public static void assertSameItem(Map<String, Item> itemMap1, Map<String, Item> itemMap2, String message) {
		assertSameItem(itemMap1, itemMap2, false, message);
	}
	
	public static void assertSameItem(Map<String, Item> itemMap1, Map<String, Item> itemMap2, boolean checkIds) {
		assertSameItem(itemMap1, itemMap2, checkIds, "");
	}
	
	public static void assertSameItem(Map<String, Item> itemMap1, Map<String, Item> itemMap2, boolean checkIds, String message) {
		Assert.notNull(itemMap1, "Item list1 must be specified");
		Assert.notNull(itemMap2, "Item list2 must be specified");
		Assert.equals(itemMap1.size(), itemMap2.size(), "Item count not equal");
		assertSameItem(itemMap1.values(), itemMap2.values(), checkIds, "");
	}
	
	public static Event createEmpty_Event() {
		Event event = new Event();
		eventCount++;
		return event;
	}
	
	public static Event create_Event() {
		Event event = create_Event(1);
		return event;
	}
	
	public static Event create_Event(long index) {
		Event event = createEmpty_Event();
		long value = createValue(index, eventCount);
		event.setDate(new Date(value + 1000L));
		event.setMessage("dummyMessage" + value);
		event.setId(value);
		return event;
	}
	
	public static List<Event> createEmptyList_Event() {
		return new ArrayList<Event>();
	}
	
	public static List<Event> createList_Event() {
		return createList_Event(2);
	}
	
	public static List<Event> createList_Event(int size) {
		return createList_Event(1, size);
	}
	
	public static List<Event> createList_Event(long index, int size) {
		List<Event> eventList = createEmptyList_Event();
		long limit = index + size;
		for (; index < limit; index++)
			eventList.add(create_Event(index));
		return eventList;
	}
	
	public static Set<Event> createEmptySet_Event() {
		return new HashSet<Event>();
	}
	
	public static Set<Event> createSet_Event() {
		return createSet_Event(2);
	}
	
	public static Set<Event> createSet_Event(int size) {
		return createSet_Event(1, size);
	}
	
	public static Set<Event> createSet_Event(long index, int size) {
		Set<Event> eventSet = createEmptySet_Event();
		long limit = index + size;
		for (; index < limit; index++)
			eventSet.add(create_Event(index));
		return eventSet;
	}
	
	public static void assertEventExists(Collection<Event> eventList, Event event) {
		Assert.notNull(eventList, "Event list must be specified");
		Assert.notNull(event, "Event record must be specified");
		Iterator<Event> iterator = eventList.iterator();
		while (iterator.hasNext()) {
			Event event1 = iterator.next();
			if (event1.equals(event)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - Event should exist: "+event);
	}
	
	public static void assertEventCorrect(Event event) {
		long value = event.getId();
		assertObjectCorrect("Date", event.getDate(), value);
		assertObjectCorrect("Message", event.getMessage(), value);
	}
	
	public static void assertEventCorrect(Collection<Event> eventList) {
		Assert.isTrue(eventList.size() == 2, "Event count not correct");
		Iterator<Event> iterator = eventList.iterator();
		while (iterator.hasNext()) {
			Event event = iterator.next();
			assertEventCorrect(event);
		}
	}
	
	public static void assertSameEvent(Event event1, Event event2) {
		assertSameEvent(event1, event2, false, "");
	}
	
	public static void assertSameEvent(Event event1, Event event2, String message) {
		assertSameEvent(event1, event2, false, message);
	}
	
	public static void assertSameEvent(Event event1, Event event2, boolean checkIds) {
		assertSameEvent(event1, event2, checkIds, "");
	}
	
	public static void assertSameEvent(Event event1, Event event2, boolean checkIds, String message) {
		assertObjectExists("Event1", event1);
		assertObjectExists("Event2", event2);
		if (checkIds)
			assertObjectEquals("Id", event1.getId(), event2.getId(), message);
		assertObjectEquals("Date", event1.getDate(), event2.getDate(), message);
		assertObjectEquals("Message", event1.getMessage(), event2.getMessage(), message);
	}
	
	public static void assertSameEvent(Collection<Event> eventList1, Collection<Event> eventList2) {
		assertSameEvent(eventList1, eventList2, false, "");
	}
	
	public static void assertSameEvent(Collection<Event> eventList1, Collection<Event> eventList2, String message) {
		assertSameEvent(eventList1, eventList2, false, message);
	}
	
	public static void assertSameEvent(Collection<Event> eventList1, Collection<Event> eventList2, boolean checkIds) {
		assertSameEvent(eventList1, eventList2, checkIds, "");
	}
	
	public static void assertSameEvent(Collection<Event> eventList1, Collection<Event> eventList2, boolean checkIds, String message) {
		Assert.notNull(eventList1, "Event list1 must be specified");
		Assert.notNull(eventList2, "Event list2 must be specified");
		Assert.equals(eventList1.size(), eventList2.size(), "Event count not equal");
		Collection<Event> sortedRecords1 = EventUtil.sortRecords(eventList1);
		Collection<Event> sortedRecords2 = EventUtil.sortRecords(eventList2);
		Iterator<Event> list1Iterator = sortedRecords1.iterator();
		Iterator<Event> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			Event event1 = list1Iterator.next();
			Event event2 = list2Iterator.next();
			assertSameEvent(event1, event2, checkIds, message);
		}
	}

}
