package bookshop2.supplier.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.aries.Assert;
import org.aries.common.entity.PersonNameEntity;
import org.aries.common.util.CommonEntityFixture;
import org.aries.util.BaseFixture;

import bookshop2.BookKey;
import bookshop2.OrderKey;
import bookshop2.supplier.entity.BookOrdersBookEntity;
import bookshop2.supplier.entity.BookOrdersEntity;
import bookshop2.supplier.entity.ReservedBooksEntity;


public class SupplierEntityFixture extends BaseFixture {

	private static int counter = 0;
	
	
	public static ReservedBooksEntity createEmpty_ReservedBooksEntity() {
		ReservedBooksEntity reservedBooksEntity = new ReservedBooksEntity();
		return reservedBooksEntity;
	}
	
	public static ReservedBooksEntity create_ReservedBooksEntity() {
		ReservedBooksEntity reservedBooksEntity = create_ReservedBooksEntity(1);
		return reservedBooksEntity;
	}
	
	public static ReservedBooksEntity create_ReservedBooksEntity(long index) {
		ReservedBooksEntity reservedBooksEntity = createEmpty_ReservedBooksEntity();
		reservedBooksEntity.setBarCode(1L + (long) index);
		reservedBooksEntity.setAuthor("dummyAuthor" + index);
		reservedBooksEntity.setTitle("dummyTitle" + index);
		reservedBooksEntity.setPrice(1.0D + (double) index);
		reservedBooksEntity.setQuantity(1 + (int) index);
		reservedBooksEntity.setId(10L * index);
		return reservedBooksEntity;
	}
	
	public static List<ReservedBooksEntity> createEmptyList_ReservedBooksEntity() {
		return new ArrayList<ReservedBooksEntity>();
	}
	
	public static List<ReservedBooksEntity> createList_ReservedBooksEntity() {
		return createList_ReservedBooksEntity(2);
	}
	
	public static List<ReservedBooksEntity> createList_ReservedBooksEntity(int size) {
		return createList_ReservedBooksEntity(1, size);
	}
	
	public static List<ReservedBooksEntity> createList_ReservedBooksEntity(long index, int size) {
		List<ReservedBooksEntity> reservedBooksEntityList = createEmptyList_ReservedBooksEntity();
		long limit = index + size;
		for (; index < limit; index++)
			reservedBooksEntityList.add(create_ReservedBooksEntity(index));
		return reservedBooksEntityList;
	}
	
	public static Set<ReservedBooksEntity> createEmptySet_ReservedBooksEntity() {
		return new HashSet<ReservedBooksEntity>();
	}
	
	public static Set<ReservedBooksEntity> createSet_ReservedBooksEntity() {
		return createSet_ReservedBooksEntity(2);
	}
	
	public static Set<ReservedBooksEntity> createSet_ReservedBooksEntity(int size) {
		return createSet_ReservedBooksEntity(1, size);
	}
	
	public static Set<ReservedBooksEntity> createSet_ReservedBooksEntity(long index, int size) {
		Set<ReservedBooksEntity> reservedBooksEntitySet = createEmptySet_ReservedBooksEntity();
		long limit = index + size;
		for (; index < limit; index++)
			reservedBooksEntitySet.add(create_ReservedBooksEntity(index));
		return reservedBooksEntitySet;
	}
	
	public static void assertReservedBooksEntityExists(Collection<ReservedBooksEntity> reservedBooksEntityList, ReservedBooksEntity reservedBooksEntity) {
		Assert.notNull(reservedBooksEntityList, "ReservedBooksEntity list must be specified");
		Assert.notNull(reservedBooksEntity, "ReservedBooksEntity record must be specified");
		Iterator<ReservedBooksEntity> iterator = reservedBooksEntityList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			ReservedBooksEntity reservedBooksEntity1 = iterator.next();
			if (reservedBooksEntity1.equals(reservedBooksEntity)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - ReservedBooksEntity should exist: "+reservedBooksEntity);
	}
	
	public static void assertReservedBooksEntityCorrect(ReservedBooksEntity reservedBooksEntity) {
		long index = reservedBooksEntity.getId() / 10L;
		assertObjectCorrect("BarCode", reservedBooksEntity.getBarCode(), index);
		assertObjectCorrect("Author", reservedBooksEntity.getAuthor(), index);
		assertObjectCorrect("Title", reservedBooksEntity.getTitle(), index);
		assertObjectCorrect("Price", reservedBooksEntity.getPrice(), index);
		assertObjectCorrect("Quantity", reservedBooksEntity.getQuantity(), index);
	}
	
	public static void assertReservedBooksEntityCorrect(Collection<ReservedBooksEntity> reservedBooksEntityList) {
		Assert.isTrue(reservedBooksEntityList.size() == 2, "ReservedBooksEntity count not correct");
		Iterator<ReservedBooksEntity> iterator = reservedBooksEntityList.iterator();
		while (iterator.hasNext()) {
			ReservedBooksEntity reservedBooksEntity = iterator.next();
			assertReservedBooksEntityCorrect(reservedBooksEntity);
		}
	}
	
	public static void assertSameReservedBooksEntity(ReservedBooksEntity reservedBooksEntity1, ReservedBooksEntity reservedBooksEntity2) {
		assertSameReservedBooksEntity(reservedBooksEntity1, reservedBooksEntity2, false, "");
	}
	
	public static void assertSameReservedBooksEntity(ReservedBooksEntity reservedBooksEntity1, ReservedBooksEntity reservedBooksEntity2, String message) {
		assertSameReservedBooksEntity(reservedBooksEntity1, reservedBooksEntity2, false, message);
	}
	
	public static void assertSameReservedBooksEntity(ReservedBooksEntity reservedBooksEntity1, ReservedBooksEntity reservedBooksEntity2, boolean checkIds) {
		assertSameReservedBooksEntity(reservedBooksEntity1, reservedBooksEntity2, checkIds, "");
	}
	
	public static void assertSameReservedBooksEntity(ReservedBooksEntity reservedBooksEntity1, ReservedBooksEntity reservedBooksEntity2, boolean checkIds, String message) {
		assertObjectExists("ReservedBooksEntity1", reservedBooksEntity1);
		assertObjectExists("ReservedBooksEntity2", reservedBooksEntity2);
		if (checkIds)
			assertObjectEquals("Id", reservedBooksEntity1.getId(), reservedBooksEntity2.getId(), message);
		assertObjectEquals("BarCode", reservedBooksEntity1.getBarCode(), reservedBooksEntity2.getBarCode(), message);
		assertObjectEquals("Author", reservedBooksEntity1.getAuthor(), reservedBooksEntity2.getAuthor(), message);
		assertObjectEquals("Title", reservedBooksEntity1.getTitle(), reservedBooksEntity2.getTitle(), message);
		assertObjectEquals("Price", reservedBooksEntity1.getPrice(), reservedBooksEntity2.getPrice(), message);
		assertObjectEquals("Quantity", reservedBooksEntity1.getQuantity(), reservedBooksEntity2.getQuantity(), message);
	}
	
	public static void assertSameReservedBooksEntity(Collection<ReservedBooksEntity> reservedBooksEntityList1, Collection<ReservedBooksEntity> reservedBooksEntityList2) {
		assertSameReservedBooksEntity(reservedBooksEntityList1, reservedBooksEntityList2, false, "");
	}
	
	public static void assertSameReservedBooksEntity(Collection<ReservedBooksEntity> reservedBooksEntityList1, Collection<ReservedBooksEntity> reservedBooksEntityList2, String message) {
		assertSameReservedBooksEntity(reservedBooksEntityList1, reservedBooksEntityList2, "");
	}
	
	public static void assertSameReservedBooksEntity(Collection<ReservedBooksEntity> reservedBooksEntityList1, Collection<ReservedBooksEntity> reservedBooksEntityList2, boolean checkIds) {
		assertSameReservedBooksEntity(reservedBooksEntityList1, reservedBooksEntityList2, checkIds, "");
	}
	
	public static void assertSameReservedBooksEntity(Collection<ReservedBooksEntity> reservedBooksEntityList1, Collection<ReservedBooksEntity> reservedBooksEntityList2, boolean checkIds, String message) {
		Assert.notNull(reservedBooksEntityList1, "ReservedBooksEntity list1 must be specified");
		Assert.notNull(reservedBooksEntityList2, "ReservedBooksEntity list2 must be specified");
		Assert.equals(reservedBooksEntityList1.size(), reservedBooksEntityList2.size(), "ReservedBooksEntity count not equal");
		Iterator<ReservedBooksEntity> iterator1 = reservedBooksEntityList1.iterator();
		while (iterator1.hasNext()) {
			ReservedBooksEntity reservedBooksEntity1 = iterator1.next();
			Iterator<ReservedBooksEntity> iterator2 = reservedBooksEntityList2.iterator();
			boolean isFound = false;
			while (iterator2.hasNext()) {
				ReservedBooksEntity reservedBooksEntity2 = iterator2.next();
				if (reservedBooksEntity1.getId().equals(reservedBooksEntity2.getId())) {
					assertSameReservedBooksEntity(reservedBooksEntity1, reservedBooksEntity2, checkIds, message);
					isFound = true;
					break;
				}
			}
			
			//if (!isFound)
			//	System.out.println();
			Assert.isTrue(isFound, "Book record not found: "+reservedBooksEntity1);
		}
	}
	
	public static void assertSameReservedBooksEntity(Map<BookKey, ReservedBooksEntity> reservedBooksEntityMap1, Map<BookKey, ReservedBooksEntity> reservedBooksEntityMap2) {
		assertSameReservedBooksEntity(reservedBooksEntityMap1, reservedBooksEntityMap2, "");
	}
	
	public static void assertSameReservedBooksEntity(Map<BookKey, ReservedBooksEntity> reservedBooksEntityMap1, Map<BookKey, ReservedBooksEntity> reservedBooksEntityMap2, String message) {
		Assert.notNull(reservedBooksEntityMap1, "ReservedBooksEntity map1 must be specified");
		Assert.notNull(reservedBooksEntityMap2, "ReservedBooksEntity map2 must be specified");
		Assert.isTrue(reservedBooksEntityMap1.size() == reservedBooksEntityMap2.size(), "ReservedBooksEntity count not correct");
		Set<BookKey> keySet = reservedBooksEntityMap1.keySet();
		Iterator<BookKey> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			BookKey bookKey = iterator.next();
			ReservedBooksEntity reservedBooksEntity1 = reservedBooksEntityMap1.get(bookKey);
			ReservedBooksEntity reservedBooksEntity2 = reservedBooksEntityMap2.get(bookKey);
			assertSameReservedBooksEntity(reservedBooksEntity1, reservedBooksEntity2);
		}
	}
	
	public static BookOrdersEntity createEmpty_BookOrdersEntity() {
		BookOrdersEntity bookOrdersEntity = new BookOrdersEntity();
		return bookOrdersEntity;
	}
	
	public static BookOrdersEntity create_BookOrdersEntity() {
		BookOrdersEntity bookOrdersEntity = create_BookOrdersEntity(1);
		return bookOrdersEntity;
	}
	
	public static BookOrdersEntity create_BookOrdersEntity(long index) {
		BookOrdersEntity bookOrdersEntity = createEmpty_BookOrdersEntity();
		bookOrdersEntity.setTrackingNumber("dummyTrackingNumber" + index + counter++);
		bookOrdersEntity.setCount(1 + (int) index);
		bookOrdersEntity.setDateTime(new Date(1000L + index));
		bookOrdersEntity.setPersonName(CommonEntityFixture.create_PersonNameEntity(index));
		bookOrdersEntity.getBooks().addAll(createList_BookOrdersBookEntity(bookOrdersEntity, 2));
		bookOrdersEntity.setId(10L * index);
		return bookOrdersEntity;
	}
	
	public static List<BookOrdersEntity> createEmptyList_BookOrdersEntity() {
		return new ArrayList<BookOrdersEntity>();
	}
	
	public static List<BookOrdersEntity> createList_BookOrdersEntity() {
		return createList_BookOrdersEntity(2);
	}
	
	public static List<BookOrdersEntity> createList_BookOrdersEntity(int size) {
		return createList_BookOrdersEntity(1, size);
	}
	
	public static List<BookOrdersEntity> createList_BookOrdersEntity(long index, int size) {
		List<BookOrdersEntity> bookOrdersEntityList = createEmptyList_BookOrdersEntity();
		long limit = index + size;
		for (; index < limit; index++)
			bookOrdersEntityList.add(create_BookOrdersEntity(index));
		return bookOrdersEntityList;
	}
	
	public static Set<BookOrdersEntity> createEmptySet_BookOrdersEntity() {
		return new HashSet<BookOrdersEntity>();
	}
	
	public static Set<BookOrdersEntity> createSet_BookOrdersEntity() {
		return createSet_BookOrdersEntity(2);
	}
	
	public static Set<BookOrdersEntity> createSet_BookOrdersEntity(int size) {
		return createSet_BookOrdersEntity(1, size);
	}
	
	public static Set<BookOrdersEntity> createSet_BookOrdersEntity(long index, int size) {
		Set<BookOrdersEntity> bookOrdersEntitySet = createEmptySet_BookOrdersEntity();
		long limit = index + size;
		for (; index < limit; index++)
			bookOrdersEntitySet.add(create_BookOrdersEntity(index));
		return bookOrdersEntitySet;
	}
	
	public static void assertBookOrdersEntityExists(Collection<BookOrdersEntity> bookOrdersEntityList, BookOrdersEntity bookOrdersEntity) {
		Assert.notNull(bookOrdersEntityList, "BookOrdersEntity list must be specified");
		Assert.notNull(bookOrdersEntity, "BookOrdersEntity record must be specified");
		Iterator<BookOrdersEntity> iterator = bookOrdersEntityList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			BookOrdersEntity bookOrdersEntity1 = iterator.next();
			if (bookOrdersEntity1.equals(bookOrdersEntity)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - BookOrdersEntity should exist: "+bookOrdersEntity);
	}
	
	public static void assertBookOrdersEntityCorrect(BookOrdersEntity bookOrdersEntity) {
		long index = bookOrdersEntity.getId() / 10L;
		assertObjectCorrect("TrackingNumber", bookOrdersEntity.getTrackingNumber(), index);
		assertObjectCorrect("Count", bookOrdersEntity.getCount(), index);
		assertObjectCorrect("DateTime", bookOrdersEntity.getDateTime(), index);
		CommonEntityFixture.assertPersonNameEntityCorrect(bookOrdersEntity.getPersonName());
		assertBookOrdersBookEntityCorrect(bookOrdersEntity.getBooks());
	}
	
	public static void assertBookOrdersEntityCorrect(Collection<BookOrdersEntity> bookOrdersEntityList) {
		Assert.isTrue(bookOrdersEntityList.size() == 2, "BookOrdersEntity count not correct");
		Iterator<BookOrdersEntity> iterator = bookOrdersEntityList.iterator();
		while (iterator.hasNext()) {
			BookOrdersEntity bookOrdersEntity = iterator.next();
			assertBookOrdersEntityCorrect(bookOrdersEntity);
		}
	}
	
	public static void assertSameBookOrdersEntity(BookOrdersEntity bookOrdersEntity1, BookOrdersEntity bookOrdersEntity2) {
		assertSameBookOrdersEntity(bookOrdersEntity1, bookOrdersEntity2, false, "");
	}
	
	public static void assertSameBookOrdersEntity(BookOrdersEntity bookOrdersEntity1, BookOrdersEntity bookOrdersEntity2, String message) {
		assertSameBookOrdersEntity(bookOrdersEntity1, bookOrdersEntity2, false, message);
	}
	
	public static void assertSameBookOrdersEntity(BookOrdersEntity bookOrdersEntity1, BookOrdersEntity bookOrdersEntity2, boolean checkIds) {
		assertSameBookOrdersEntity(bookOrdersEntity1, bookOrdersEntity2, checkIds, "");
	}
	
	public static void assertSameBookOrdersEntity(BookOrdersEntity bookOrdersEntity1, BookOrdersEntity bookOrdersEntity2, boolean checkIds, String message) {
		assertObjectExists("BookOrdersEntity1", bookOrdersEntity1);
		assertObjectExists("BookOrdersEntity2", bookOrdersEntity2);
		if (checkIds)
			assertObjectEquals("Id", bookOrdersEntity1.getId(), bookOrdersEntity2.getId(), message);
		assertObjectEquals("TrackingNumber", bookOrdersEntity1.getTrackingNumber(), bookOrdersEntity2.getTrackingNumber(), message);
		assertObjectEquals("Count", bookOrdersEntity1.getCount(), bookOrdersEntity2.getCount(), message);
		assertObjectEquals("DateTime", bookOrdersEntity1.getDateTime(), bookOrdersEntity2.getDateTime(), message);
		CommonEntityFixture.assertSamePersonNameEntity(bookOrdersEntity1.getPersonName(), bookOrdersEntity2.getPersonName(), checkIds, message);
		assertSameBookOrdersBookEntity(bookOrdersEntity1.getBooks(), bookOrdersEntity2.getBooks(), checkIds, message);
	}
	
	public static void assertSameBookOrdersEntity(Collection<BookOrdersEntity> bookOrdersEntityList1, Collection<BookOrdersEntity> bookOrdersEntityList2) {
		assertSameBookOrdersEntity(bookOrdersEntityList1, bookOrdersEntityList2, false, "");
	}
	
	public static void assertSameBookOrdersEntity(Collection<BookOrdersEntity> bookOrdersEntityList1, Collection<BookOrdersEntity> bookOrdersEntityList2, String message) {
		assertSameBookOrdersEntity(bookOrdersEntityList1, bookOrdersEntityList2, "");
	}
	
	public static void assertSameBookOrdersEntity(Collection<BookOrdersEntity> bookOrdersEntityList1, Collection<BookOrdersEntity> bookOrdersEntityList2, boolean checkIds) {
		assertSameBookOrdersEntity(bookOrdersEntityList1, bookOrdersEntityList2, checkIds, "");
	}
	
	public static void assertSameBookOrdersEntity(Collection<BookOrdersEntity> bookOrdersEntityList1, Collection<BookOrdersEntity> bookOrdersEntityList2, boolean checkIds, String message) {
		Assert.notNull(bookOrdersEntityList1, "BookOrdersEntity list1 must be specified");
		Assert.notNull(bookOrdersEntityList2, "BookOrdersEntity list2 must be specified");
		Assert.equals(bookOrdersEntityList1.size(), bookOrdersEntityList2.size(), "BookOrdersEntity count not equal");
		Iterator<BookOrdersEntity> iterator1 = bookOrdersEntityList1.iterator();
		while (iterator1.hasNext()) {
			BookOrdersEntity bookOrdersEntity1 = iterator1.next();
			Iterator<BookOrdersEntity> iterator2 = bookOrdersEntityList2.iterator();
			boolean isFound = false;
			while (iterator2.hasNext()) {
				BookOrdersEntity bookOrdersEntity2 = iterator2.next();
				if (bookOrdersEntity1.getId().equals(bookOrdersEntity2.getId())) {
					assertSameBookOrdersEntity(bookOrdersEntity1, bookOrdersEntity2, checkIds, message);
					isFound = true;
					break;
				}
			}
			
			//if (!isFound)
			//	System.out.println();
			Assert.isTrue(isFound, "Order record not found: "+bookOrdersEntity1);
		}
	}
	
	public static void assertSameBookOrdersEntity(Map<OrderKey, BookOrdersEntity> bookOrdersEntityMap1, Map<OrderKey, BookOrdersEntity> bookOrdersEntityMap2) {
		assertSameBookOrdersEntity(bookOrdersEntityMap1, bookOrdersEntityMap2, "");
	}
	
	public static void assertSameBookOrdersEntity(Map<OrderKey, BookOrdersEntity> bookOrdersEntityMap1, Map<OrderKey, BookOrdersEntity> bookOrdersEntityMap2, String message) {
		Assert.notNull(bookOrdersEntityMap1, "BookOrdersEntity map1 must be specified");
		Assert.notNull(bookOrdersEntityMap2, "BookOrdersEntity map2 must be specified");
		Assert.isTrue(bookOrdersEntityMap1.size() == bookOrdersEntityMap2.size(), "BookOrdersEntity count not correct");
		Set<OrderKey> keySet = bookOrdersEntityMap1.keySet();
		Iterator<OrderKey> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			OrderKey orderKey = iterator.next();
			BookOrdersEntity bookOrdersEntity1 = bookOrdersEntityMap1.get(orderKey);
			BookOrdersEntity bookOrdersEntity2 = bookOrdersEntityMap2.get(orderKey);
			assertSameBookOrdersEntity(bookOrdersEntity1, bookOrdersEntity2);
		}
	}
	
	public static PersonNameEntity createEmpty_PersonNameEntity() {
		PersonNameEntity personNameEntity = new PersonNameEntity();
		return personNameEntity;
	}
	
	public static PersonNameEntity create_PersonNameEntity() {
		PersonNameEntity personNameEntity = create_PersonNameEntity(1);
		return personNameEntity;
	}
	
	public static PersonNameEntity create_PersonNameEntity(long index) {
		PersonNameEntity personNameEntity = createEmpty_PersonNameEntity();
		personNameEntity.setLastName("dummyLastName" + index);
		personNameEntity.setFirstName("dummyFirstName" + index);
		personNameEntity.setMiddleInitial("dummyMiddleInitial" + index);
		personNameEntity.setId(11L * index);
		return personNameEntity;
	}
	
	public static List<PersonNameEntity> createEmptyList_PersonNameEntity() {
		return new ArrayList<PersonNameEntity>();
	}
	
	public static List<PersonNameEntity> createList_PersonNameEntity() {
		return createList_PersonNameEntity(2);
	}
	
	public static List<PersonNameEntity> createList_PersonNameEntity(int size) {
		return createList_PersonNameEntity(1, size);
	}
	
	public static List<PersonNameEntity> createList_PersonNameEntity(long index, int size) {
		List<PersonNameEntity> personNameEntityList = createEmptyList_PersonNameEntity();
		long limit = index + size;
		for (; index < limit; index++)
			personNameEntityList.add(create_PersonNameEntity(index));
		return personNameEntityList;
	}
	
	public static Set<PersonNameEntity> createEmptySet_PersonNameEntity() {
		return new HashSet<PersonNameEntity>();
	}
	
	public static Set<PersonNameEntity> createSet_PersonNameEntity() {
		return createSet_PersonNameEntity(2);
	}
	
	public static Set<PersonNameEntity> createSet_PersonNameEntity(int size) {
		return createSet_PersonNameEntity(1, size);
	}
	
	public static Set<PersonNameEntity> createSet_PersonNameEntity(long index, int size) {
		Set<PersonNameEntity> personNameEntitySet = createEmptySet_PersonNameEntity();
		long limit = index + size;
		for (; index < limit; index++)
			personNameEntitySet.add(create_PersonNameEntity(index));
		return personNameEntitySet;
	}
	
	public static void assertPersonNameEntityExists(Collection<PersonNameEntity> personNameEntityList, PersonNameEntity personNameEntity) {
		Assert.notNull(personNameEntityList, "PersonNameEntity list must be specified");
		Assert.notNull(personNameEntity, "PersonNameEntity record must be specified");
		Iterator<PersonNameEntity> iterator = personNameEntityList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			PersonNameEntity personNameEntity1 = iterator.next();
			if (personNameEntity1.equals(personNameEntity)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - PersonNameEntity should exist: "+personNameEntity);
	}
	
	public static void assertPersonNameEntityCorrect(PersonNameEntity personNameEntity) {
		long index = personNameEntity.getId() / 10L;
		assertObjectCorrect("LastName", personNameEntity.getLastName(), index);
		assertObjectCorrect("FirstName", personNameEntity.getFirstName(), index);
		assertObjectCorrect("MiddleInitial", personNameEntity.getMiddleInitial(), index);
	}
	
	public static void assertPersonNameEntityCorrect(Collection<PersonNameEntity> personNameEntityList) {
		Assert.isTrue(personNameEntityList.size() == 2, "PersonNameEntity count not correct");
		Iterator<PersonNameEntity> iterator = personNameEntityList.iterator();
		while (iterator.hasNext()) {
			PersonNameEntity personNameEntity = iterator.next();
			assertPersonNameEntityCorrect(personNameEntity);
		}
	}
	
	public static void assertSamePersonNameEntity(PersonNameEntity personNameEntity1, PersonNameEntity personNameEntity2) {
		assertSamePersonNameEntity(personNameEntity1, personNameEntity2, false, "");
	}
	
	public static void assertSamePersonNameEntity(PersonNameEntity personNameEntity1, PersonNameEntity personNameEntity2, String message) {
		assertSamePersonNameEntity(personNameEntity1, personNameEntity2, false, message);
	}
	
	public static void assertSamePersonNameEntity(PersonNameEntity personNameEntity1, PersonNameEntity personNameEntity2, boolean checkIds) {
		assertSamePersonNameEntity(personNameEntity1, personNameEntity2, checkIds, "");
	}
	
	public static void assertSamePersonNameEntity(PersonNameEntity personNameEntity1, PersonNameEntity personNameEntity2, boolean checkIds, String message) {
		assertObjectExists("PersonNameEntity1", personNameEntity1);
		assertObjectExists("PersonNameEntity2", personNameEntity2);
		if (checkIds)
			assertObjectEquals("Id", personNameEntity1.getId(), personNameEntity2.getId(), message);
		assertObjectEquals("LastName", personNameEntity1.getLastName(), personNameEntity2.getLastName(), message);
		assertObjectEquals("FirstName", personNameEntity1.getFirstName(), personNameEntity2.getFirstName(), message);
		assertObjectEquals("MiddleInitial", personNameEntity1.getMiddleInitial(), personNameEntity2.getMiddleInitial(), message);
	}
	
	public static void assertSamePersonNameEntity(Collection<PersonNameEntity> personNameEntityList1, Collection<PersonNameEntity> personNameEntityList2) {
		assertSamePersonNameEntity(personNameEntityList1, personNameEntityList2, false, "");
	}
	
	public static void assertSamePersonNameEntity(Collection<PersonNameEntity> personNameEntityList1, Collection<PersonNameEntity> personNameEntityList2, String message) {
		assertSamePersonNameEntity(personNameEntityList1, personNameEntityList2, "");
	}
	
	public static void assertSamePersonNameEntity(Collection<PersonNameEntity> personNameEntityList1, Collection<PersonNameEntity> personNameEntityList2, boolean checkIds) {
		assertSamePersonNameEntity(personNameEntityList1, personNameEntityList2, checkIds, "");
	}
	
	public static void assertSamePersonNameEntity(Collection<PersonNameEntity> personNameEntityList1, Collection<PersonNameEntity> personNameEntityList2, boolean checkIds, String message) {
		Assert.notNull(personNameEntityList1, "PersonNameEntity list1 must be specified");
		Assert.notNull(personNameEntityList2, "PersonNameEntity list2 must be specified");
		Assert.equals(personNameEntityList1.size(), personNameEntityList2.size(), "PersonNameEntity count not equal");
		Iterator<PersonNameEntity> iterator1 = personNameEntityList1.iterator();
		while (iterator1.hasNext()) {
			PersonNameEntity personNameEntity1 = iterator1.next();
			Iterator<PersonNameEntity> iterator2 = personNameEntityList2.iterator();
			boolean isFound = false;
			while (iterator2.hasNext()) {
				PersonNameEntity personNameEntity2 = iterator2.next();
				if (personNameEntity1.getId().equals(personNameEntity2.getId())) {
					assertSamePersonNameEntity(personNameEntity1, personNameEntity2, checkIds, message);
					isFound = true;
					break;
				}
			}
			
			//if (!isFound)
			//	System.out.println();
			Assert.isTrue(isFound, "PersonName record not found: "+personNameEntity1);
		}
	}
	
	public static BookOrdersBookEntity createEmpty_BookOrdersBookEntity() {
		BookOrdersBookEntity bookOrdersBookEntity = new BookOrdersBookEntity();
		return bookOrdersBookEntity;
	}
	
	public static BookOrdersBookEntity create_BookOrdersBookEntity(BookOrdersEntity bookOrdersEntity) {
		BookOrdersBookEntity bookOrdersBookEntity = create_BookOrdersBookEntity(bookOrdersEntity, 1);
		return bookOrdersBookEntity;
	}
	
	public static BookOrdersBookEntity create_BookOrdersBookEntity(BookOrdersEntity bookOrdersEntity, long index) {
		BookOrdersBookEntity bookOrdersBookEntity = createEmpty_BookOrdersBookEntity();
		bookOrdersBookEntity.setOrder(bookOrdersEntity);
		bookOrdersBookEntity.setBarCode(1L + (long) index + counter++);
		bookOrdersBookEntity.setAuthor("dummyAuthor" + index);
		bookOrdersBookEntity.setTitle("dummyTitle" + index);
		bookOrdersBookEntity.setPrice(1.0D + (double) index);
		bookOrdersBookEntity.setQuantity(1 + (int) index);
		bookOrdersBookEntity.setId(12L * index);
		return bookOrdersBookEntity;
	}
	
	public static List<BookOrdersBookEntity> createEmptyList_BookOrdersBookEntity() {
		return new ArrayList<BookOrdersBookEntity>();
	}
	
	public static List<BookOrdersBookEntity> createList_BookOrdersBookEntity(BookOrdersEntity bookOrdersEntity) {
		return createList_BookOrdersBookEntity(bookOrdersEntity, 2);
	}
	
	public static List<BookOrdersBookEntity> createList_BookOrdersBookEntity(BookOrdersEntity bookOrdersEntity, int size) {
		return createList_BookOrdersBookEntity(bookOrdersEntity, 1, size);
	}
	
	public static List<BookOrdersBookEntity> createList_BookOrdersBookEntity(BookOrdersEntity bookOrdersEntity, long index, int size) {
		List<BookOrdersBookEntity> bookOrdersBookEntityList = createEmptyList_BookOrdersBookEntity();
		long limit = index + size;
		for (; index < limit; index++)
			bookOrdersBookEntityList.add(create_BookOrdersBookEntity(bookOrdersEntity, index));
		return bookOrdersBookEntityList;
	}
	
	public static Set<BookOrdersBookEntity> createEmptySet_BookOrdersBookEntity() {
		return new HashSet<BookOrdersBookEntity>();
	}
	
	public static Set<BookOrdersBookEntity> createSet_BookOrdersBookEntity(BookOrdersEntity bookOrdersEntity) {
		return createSet_BookOrdersBookEntity(bookOrdersEntity, 2);
	}
	
	public static Set<BookOrdersBookEntity> createSet_BookOrdersBookEntity(BookOrdersEntity bookOrdersEntity, int size) {
		return createSet_BookOrdersBookEntity(bookOrdersEntity, 1, size);
	}
	
	public static Set<BookOrdersBookEntity> createSet_BookOrdersBookEntity(BookOrdersEntity bookOrdersEntity, long index, int size) {
		Set<BookOrdersBookEntity> bookOrdersBookEntitySet = createEmptySet_BookOrdersBookEntity();
		long limit = index + size;
		for (; index < limit; index++)
			bookOrdersBookEntitySet.add(create_BookOrdersBookEntity(bookOrdersEntity, index));
		return bookOrdersBookEntitySet;
	}
	
	public static void assertBookOrdersBookEntityExists(Collection<BookOrdersBookEntity> bookOrdersBookEntityList, BookOrdersBookEntity bookOrdersBookEntity) {
		Assert.notNull(bookOrdersBookEntityList, "BookOrdersBookEntity list must be specified");
		Assert.notNull(bookOrdersBookEntity, "BookOrdersBookEntity record must be specified");
		Iterator<BookOrdersBookEntity> iterator = bookOrdersBookEntityList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			BookOrdersBookEntity bookOrdersBookEntity1 = iterator.next();
			if (bookOrdersBookEntity1.equals(bookOrdersBookEntity)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - BookOrdersBookEntity should exist: "+bookOrdersBookEntity);
	}
	
	public static void assertBookOrdersBookEntityCorrect(BookOrdersBookEntity bookOrdersBookEntity) {
		long index = bookOrdersBookEntity.getId() / 10L;
		assertObjectCorrect("BarCode", bookOrdersBookEntity.getBarCode(), index);
		assertObjectCorrect("Author", bookOrdersBookEntity.getAuthor(), index);
		assertObjectCorrect("Title", bookOrdersBookEntity.getTitle(), index);
		assertObjectCorrect("Price", bookOrdersBookEntity.getPrice(), index);
		assertObjectCorrect("Quantity", bookOrdersBookEntity.getQuantity(), index);
	}
	
	public static void assertBookOrdersBookEntityCorrect(Collection<BookOrdersBookEntity> bookOrdersBookEntityList) {
		Assert.isTrue(bookOrdersBookEntityList.size() == 2, "BookOrdersBookEntity count not correct");
		Iterator<BookOrdersBookEntity> iterator = bookOrdersBookEntityList.iterator();
		while (iterator.hasNext()) {
			BookOrdersBookEntity bookOrdersBookEntity = iterator.next();
			assertBookOrdersBookEntityCorrect(bookOrdersBookEntity);
		}
	}
	
	public static void assertSameBookOrdersBookEntity(BookOrdersBookEntity bookOrdersBookEntity1, BookOrdersBookEntity bookOrdersBookEntity2) {
		assertSameBookOrdersBookEntity(bookOrdersBookEntity1, bookOrdersBookEntity2, false, "");
	}
	
	public static void assertSameBookOrdersBookEntity(BookOrdersBookEntity bookOrdersBookEntity1, BookOrdersBookEntity bookOrdersBookEntity2, String message) {
		assertSameBookOrdersBookEntity(bookOrdersBookEntity1, bookOrdersBookEntity2, false, message);
	}
	
	public static void assertSameBookOrdersBookEntity(BookOrdersBookEntity bookOrdersBookEntity1, BookOrdersBookEntity bookOrdersBookEntity2, boolean checkIds) {
		assertSameBookOrdersBookEntity(bookOrdersBookEntity1, bookOrdersBookEntity2, checkIds, "");
	}
	
	public static void assertSameBookOrdersBookEntity(BookOrdersBookEntity bookOrdersBookEntity1, BookOrdersBookEntity bookOrdersBookEntity2, boolean checkIds, String message) {
		assertObjectExists("BookOrdersBookEntity1", bookOrdersBookEntity1);
		assertObjectExists("BookOrdersBookEntity2", bookOrdersBookEntity2);
		if (checkIds)
			assertObjectEquals("Id", bookOrdersBookEntity1.getId(), bookOrdersBookEntity2.getId(), message);
		assertObjectEquals("BarCode", bookOrdersBookEntity1.getBarCode(), bookOrdersBookEntity2.getBarCode(), message);
		assertObjectEquals("Author", bookOrdersBookEntity1.getAuthor(), bookOrdersBookEntity2.getAuthor(), message);
		assertObjectEquals("Title", bookOrdersBookEntity1.getTitle(), bookOrdersBookEntity2.getTitle(), message);
		assertObjectEquals("Price", bookOrdersBookEntity1.getPrice(), bookOrdersBookEntity2.getPrice(), message);
		assertObjectEquals("Quantity", bookOrdersBookEntity1.getQuantity(), bookOrdersBookEntity2.getQuantity(), message);
		//assertSameBookOrdersEntity(bookOrdersBookEntity1.getOrder(), bookOrdersBookEntity2.getOrder(), checkIds, message);
	}
	
	public static void assertSameBookOrdersBookEntity(Collection<BookOrdersBookEntity> bookOrdersBookEntityList1, Collection<BookOrdersBookEntity> bookOrdersBookEntityList2) {
		assertSameBookOrdersBookEntity(bookOrdersBookEntityList1, bookOrdersBookEntityList2, false, "");
	}
	
	public static void assertSameBookOrdersBookEntity(Collection<BookOrdersBookEntity> bookOrdersBookEntityList1, Collection<BookOrdersBookEntity> bookOrdersBookEntityList2, String message) {
		assertSameBookOrdersBookEntity(bookOrdersBookEntityList1, bookOrdersBookEntityList2, "");
	}
	
	public static void assertSameBookOrdersBookEntity(Collection<BookOrdersBookEntity> bookOrdersBookEntityList1, Collection<BookOrdersBookEntity> bookOrdersBookEntityList2, boolean checkIds) {
		assertSameBookOrdersBookEntity(bookOrdersBookEntityList1, bookOrdersBookEntityList2, checkIds, "");
	}
	
	public static void assertSameBookOrdersBookEntity(Collection<BookOrdersBookEntity> bookOrdersBookEntityList1, Collection<BookOrdersBookEntity> bookOrdersBookEntityList2, boolean checkIds, String message) {
		Assert.notNull(bookOrdersBookEntityList1, "BookOrdersBookEntity list1 must be specified");
		Assert.notNull(bookOrdersBookEntityList2, "BookOrdersBookEntity list2 must be specified");
		Assert.equals(bookOrdersBookEntityList1.size(), bookOrdersBookEntityList2.size(), "BookOrdersBookEntity count not equal");
		Iterator<BookOrdersBookEntity> iterator1 = bookOrdersBookEntityList1.iterator();
		while (iterator1.hasNext()) {
			BookOrdersBookEntity bookOrdersBookEntity1 = iterator1.next();
			Iterator<BookOrdersBookEntity> iterator2 = bookOrdersBookEntityList2.iterator();
			boolean isFound = false;
			while (iterator2.hasNext()) {
				BookOrdersBookEntity bookOrdersBookEntity2 = iterator2.next();
				try {
					assertSameBookOrdersBookEntity(bookOrdersBookEntity1, bookOrdersBookEntity2, checkIds, message);
					isFound = true;
					break;
				} catch (Exception e) {
					//ignore
				}
			}
			
			//if (!isFound)
			//	System.out.println();
			Assert.isTrue(isFound, "BookOrdersBook record not found: "+bookOrdersBookEntity1);
		}
	}
	
}
