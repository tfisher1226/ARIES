package redhat.jee_migration_example.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.aries.Assert;
import org.aries.util.BaseFixture;

import redhat.jee_migration_example.entity.ItemStoreEntity;


public class JeeMigrationExampleEntityFixture extends BaseFixture {
	
	public static ItemStoreEntity createEmpty_ItemStoreEntity() {
		ItemStoreEntity itemStoreEntity = new ItemStoreEntity();
		return itemStoreEntity;
	}
	
	public static ItemStoreEntity create_ItemStoreEntity() {
		ItemStoreEntity itemStoreEntity = create_ItemStoreEntity(1);
		return itemStoreEntity;
	}
	
	public static ItemStoreEntity create_ItemStoreEntity(long index) {
		ItemStoreEntity itemStoreEntity = createEmpty_ItemStoreEntity();
		itemStoreEntity.setName("dummyName" + index);
		itemStoreEntity.setItemKey("dummyKey" + index);
		itemStoreEntity.setId(10L * index);
		return itemStoreEntity;
	}
	
	public static List<ItemStoreEntity> createEmptyList_ItemStoreEntity() {
		return new ArrayList<ItemStoreEntity>();
	}
	
	public static List<ItemStoreEntity> createList_ItemStoreEntity() {
		return createList_ItemStoreEntity(2);
	}
	
	public static List<ItemStoreEntity> createList_ItemStoreEntity(int size) {
		return createList_ItemStoreEntity(1, size);
	}
	
	public static List<ItemStoreEntity> createList_ItemStoreEntity(long index, int size) {
		List<ItemStoreEntity> itemStoreEntityList = createEmptyList_ItemStoreEntity();
		long limit = index + size;
		for (; index < limit; index++)
			itemStoreEntityList.add(create_ItemStoreEntity(index));
		return itemStoreEntityList;
	}
	
	public static Set<ItemStoreEntity> createEmptySet_ItemStoreEntity() {
		return new HashSet<ItemStoreEntity>();
	}
	
	public static Set<ItemStoreEntity> createSet_ItemStoreEntity() {
		return createSet_ItemStoreEntity(2);
	}
	
	public static Set<ItemStoreEntity> createSet_ItemStoreEntity(int size) {
		return createSet_ItemStoreEntity(1, size);
	}
	
	public static Set<ItemStoreEntity> createSet_ItemStoreEntity(long index, int size) {
		Set<ItemStoreEntity> itemStoreEntitySet = createEmptySet_ItemStoreEntity();
		long limit = index + size;
		for (; index < limit; index++)
			itemStoreEntitySet.add(create_ItemStoreEntity(index));
		return itemStoreEntitySet;
	}
	
	public static void assertItemStoreEntityExists(Collection<ItemStoreEntity> itemStoreEntityList, ItemStoreEntity itemStoreEntity) {
		Assert.notNull(itemStoreEntityList, "ItemStoreEntity list must be specified");
		Assert.notNull(itemStoreEntity, "ItemStoreEntity record must be specified");
		Iterator<ItemStoreEntity> iterator = itemStoreEntityList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			ItemStoreEntity itemStoreEntity1 = iterator.next();
			if (itemStoreEntity1.equals(itemStoreEntity)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - ItemStoreEntity should exist: "+itemStoreEntity);
	}
	
	public static void assertItemStoreEntityCorrect(ItemStoreEntity itemStoreEntity) {
		long index = itemStoreEntity.getId() / 10L;
		assertObjectCorrect("Name", itemStoreEntity.getName(), index);
	}
	
	public static void assertItemStoreEntityCorrect(Collection<ItemStoreEntity> itemStoreEntityList) {
		Assert.isTrue(itemStoreEntityList.size() == 2, "ItemStoreEntity count not correct");
		Iterator<ItemStoreEntity> iterator = itemStoreEntityList.iterator();
		while (iterator.hasNext()) {
			ItemStoreEntity itemStoreEntity = iterator.next();
			assertItemStoreEntityCorrect(itemStoreEntity);
		}
	}
	
	public static void assertSameItemStoreEntity(ItemStoreEntity itemStoreEntity1, ItemStoreEntity itemStoreEntity2) {
		assertSameItemStoreEntity(itemStoreEntity1, itemStoreEntity2, false, "");
	}
	
	public static void assertSameItemStoreEntity(ItemStoreEntity itemStoreEntity1, ItemStoreEntity itemStoreEntity2, String message) {
		assertSameItemStoreEntity(itemStoreEntity1, itemStoreEntity2, false, message);
	}
	
	public static void assertSameItemStoreEntity(ItemStoreEntity itemStoreEntity1, ItemStoreEntity itemStoreEntity2, boolean checkIds) {
		assertSameItemStoreEntity(itemStoreEntity1, itemStoreEntity2, checkIds, "");
	}
	
	public static void assertSameItemStoreEntity(ItemStoreEntity itemStoreEntity1, ItemStoreEntity itemStoreEntity2, boolean checkIds, String message) {
		assertObjectExists("ItemStoreEntity1", itemStoreEntity1);
		assertObjectExists("ItemStoreEntity2", itemStoreEntity2);
		if (checkIds)
			assertObjectEquals("Id", itemStoreEntity1.getId(), itemStoreEntity2.getId(), message);
		assertObjectEquals("Name", itemStoreEntity1.getName(), itemStoreEntity2.getName(), message);
	}
	
	public static void assertSameItemStoreEntity(Collection<ItemStoreEntity> itemStoreEntityList1, Collection<ItemStoreEntity> itemStoreEntityList2) {
		assertSameItemStoreEntity(itemStoreEntityList1, itemStoreEntityList2, false, "");
	}
	
	public static void assertSameItemStoreEntity(Collection<ItemStoreEntity> itemStoreEntityList1, Collection<ItemStoreEntity> itemStoreEntityList2, String message) {
		assertSameItemStoreEntity(itemStoreEntityList1, itemStoreEntityList2, "");
	}
	
	public static void assertSameItemStoreEntity(Collection<ItemStoreEntity> itemStoreEntityList1, Collection<ItemStoreEntity> itemStoreEntityList2, boolean checkIds) {
		assertSameItemStoreEntity(itemStoreEntityList1, itemStoreEntityList2, checkIds, "");
	}
	
	public static void assertSameItemStoreEntity(Collection<ItemStoreEntity> itemStoreEntityList1, Collection<ItemStoreEntity> itemStoreEntityList2, boolean checkIds, String message) {
		Assert.notNull(itemStoreEntityList1, "ItemStoreEntity list1 must be specified");
		Assert.notNull(itemStoreEntityList2, "ItemStoreEntity list2 must be specified");
		Assert.equals(itemStoreEntityList1.size(), itemStoreEntityList2.size(), "ItemStoreEntity count not equal");
		Iterator<ItemStoreEntity> iterator1 = itemStoreEntityList1.iterator();
		while (iterator1.hasNext()) {
			ItemStoreEntity itemStoreEntity1 = iterator1.next();
			Iterator<ItemStoreEntity> iterator2 = itemStoreEntityList2.iterator();
			boolean isFound = false;
			while (iterator2.hasNext()) {
				ItemStoreEntity itemStoreEntity2 = iterator2.next();
				if (itemStoreEntity1.getId().equals(itemStoreEntity2.getId())) {
					assertSameItemStoreEntity(itemStoreEntity1, itemStoreEntity2, checkIds, message);
					isFound = true;
					break;
				}
			}
			
			//if (!isFound)
			//	System.out.println();
			Assert.isTrue(isFound, "Item record not found: "+itemStoreEntity1);
		}
	}

}
