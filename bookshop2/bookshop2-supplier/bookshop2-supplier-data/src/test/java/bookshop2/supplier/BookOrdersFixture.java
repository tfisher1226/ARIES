package bookshop2.supplier;

import java.util.Collection;
import java.util.Iterator;

import org.aries.Assert;
import org.aries.common.entity.PersonNameEntity;
import org.aries.util.BaseFixture;

import bookshop2.Book;
import bookshop2.supplier.entity.BookOrdersEntity;
import bookshop2.supplier.entity.BookOrdersBookEntity;


public class BookOrdersFixture extends BaseFixture {

	
	public static void assertSameBookOrders(BookOrdersEntity order1, BookOrdersEntity order2) {
		assertSameBookOrders(order1, order2, "");
	}

	public static void assertSameBookOrders(BookOrdersEntity order1, BookOrdersEntity order2, String message) {
		assertSameBookOrders(order1, order2, false, message);
	}
	
	public static void assertSameBookOrders(BookOrdersEntity order1, BookOrdersEntity order2, boolean skipIds) {
		assertSameBookOrders(order1, order2, skipIds, "");
	}
	
	public static void assertSameBookOrders(BookOrdersEntity order1, BookOrdersEntity order2, boolean skipIds, String message) {
		BaseFixture.assertObjectExists("Order1", order1);
		BaseFixture.assertObjectExists("Order2", order2);
		BaseFixture.assertObjectEquals("Count", order1.getCount(), order2.getCount(), message);
		BaseFixture.assertObjectEquals("DateTime", order1.getDateTime(), order2.getDateTime(), message);
		assertSamePersonName(order1.getPersonName(), order2.getPersonName(), skipIds, message);
		assertSameOrderBook(order1.getBooks(), order2.getBooks(), skipIds, message);
	}

	public static void assertSameBookOrders(Collection<BookOrdersEntity> orderList1, Collection<BookOrdersEntity> orderList2) {
		assertSameBookOrders(orderList1, orderList2, "");
	}

	public static void assertSameBookOrders(Collection<BookOrdersEntity> orderList1, Collection<BookOrdersEntity> orderList2, String message) {
		assertSameBookOrders(orderList1, orderList2, false, message);
	}
	
	public static void assertSameBookOrders(Collection<BookOrdersEntity> orderList1, Collection<BookOrdersEntity> orderList2, boolean skipIds) {
		assertSameBookOrders(orderList1, orderList2, skipIds, "");
	}
	
	public static void assertSameBookOrders(Collection<BookOrdersEntity> orderList1, Collection<BookOrdersEntity> orderList2, boolean skipIds, String message) {
		BaseFixture.assertObjectExists("OrderList1", orderList1);
		BaseFixture.assertObjectExists("OrderList2", orderList2);
		Assert.equals(orderList1.size(), orderList2.size(), "BookOrders record lists should have same");
		Iterator<BookOrdersEntity> iterator1 = orderList1.iterator();
		while (iterator1.hasNext()) {
			BookOrdersEntity record1 = iterator1.next();
			Iterator<BookOrdersEntity> iterator2 = orderList2.iterator();
			boolean isFound = false;
			while (iterator2.hasNext()) {
				BookOrdersEntity record2 = iterator2.next();
				if (skipIds || record1.getId().equals(record2.getId())) {
					try {
						assertSameBookOrders(record1, record2, skipIds, message);
						isFound = true;
						break;
					} catch (Exception e) {
						continue;
					}
				}
			}
			
			//if (!isFound)
			//	System.out.println();
			Assert.isTrue(isFound, "BookOrders record not found: "+record1);
		}
	}
	
	public static void assertSamePersonName(PersonNameEntity personName1, PersonNameEntity personName2) {
		assertSamePersonName(personName1, personName2, false, "");
	}
	
	public static void assertSamePersonName(PersonNameEntity personName1, PersonNameEntity personName2, boolean skipIds) {
		assertSamePersonName(personName1, personName2, skipIds, "");
	}
	
	public static void assertSamePersonName(PersonNameEntity personName1, PersonNameEntity personName2, boolean skipIds, String message) {
		assertObjectExists("PersonName1", personName1);
		assertObjectExists("PersonName2", personName2);
		assertObjectEquals("LastName", personName1.getLastName(), personName2.getLastName(), message);
		assertObjectEquals("FirstName", personName1.getFirstName(), personName2.getFirstName(), message);
		assertObjectEquals("MiddleInitial", personName1.getMiddleInitial(), personName2.getMiddleInitial(), message);
	}

	
	
	public static void assertOrderBookExists(Collection<BookOrdersBookEntity> bookList, Book book) {
		Assert.notNull(bookList, "Book list must be specified");
		Assert.notNull(book, "Book record must be specified");
		Iterator<BookOrdersBookEntity> iterator = bookList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			BookOrdersBookEntity book1 = iterator.next();
			if (book1.equals(book)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - Book should exist: "+book);
	}
	
	public static void assertOrderBookCorrect(BookOrdersBookEntity book) {
		long index = book.getId() / 10L;
		assertObjectCorrect("Author", book.getAuthor(), index);
		assertObjectCorrect("Title", book.getTitle(), index);
		assertObjectCorrect("Price", book.getPrice(), index);
		assertObjectCorrect("Quantity", book.getQuantity(), index);
	}

	public static void assertOrderBookCorrect(Collection<BookOrdersBookEntity> bookList) {
		Assert.isTrue(bookList.size() == 2, "Book count not correct");
		Iterator<BookOrdersBookEntity> iterator = bookList.iterator();
		while (iterator.hasNext()) {
			BookOrdersBookEntity book = iterator.next();
			assertOrderBookCorrect(book);
		}
	}
	
	public static void assertSameOrderBook(BookOrdersBookEntity book1, BookOrdersBookEntity book2) {
		assertSameOrderBook(book1, book2, false, "");
	}

	public static void assertSameOrderBook(BookOrdersBookEntity book1, BookOrdersBookEntity book2, boolean skipIds) {
		assertSameOrderBook(book1, book2, skipIds, "");
	}

	public static void assertSameOrderBook(BookOrdersBookEntity book1, BookOrdersBookEntity book2, boolean skipIds, String message) {
		assertObjectExists("Book1", book1);
		assertObjectExists("Book2", book2);
		if (!skipIds)
			assertObjectEquals("Id", book1.getId(), book2.getId(), message);
		assertObjectEquals("Author", book1.getAuthor(), book2.getAuthor(), message);
		assertObjectEquals("Title", book1.getTitle(), book2.getTitle(), message);
		assertObjectEquals("Price", book1.getPrice(), book2.getPrice(), message);
		assertObjectEquals("Quantity", book1.getQuantity(), book2.getQuantity(), message);
	}

	public static void assertSameOrderBook(Collection<BookOrdersBookEntity> bookList1, Collection<BookOrdersBookEntity> bookList2) {
		assertSameOrderBook(bookList1, bookList2, false, "");
	}
	
	public static void assertSameOrderBook(Collection<BookOrdersBookEntity> bookList1, Collection<BookOrdersBookEntity> bookList2, boolean skipIds) {
		assertSameOrderBook(bookList1, bookList2, skipIds, "");
	}
	
	public static void assertSameOrderBook(Collection<BookOrdersBookEntity> bookList1, Collection<BookOrdersBookEntity> bookList2, boolean skipIds, String message) {
		Assert.notNull(bookList1, "Book list1 must be specified");
		Assert.notNull(bookList2, "Book list2 must be specified");
		Assert.equals(bookList1.size(), bookList2.size(), "Book count not equal");
		Iterator<BookOrdersBookEntity> iterator1 = bookList1.iterator();
		while (iterator1.hasNext()) {
			BookOrdersBookEntity book1 = iterator1.next();
			Iterator<BookOrdersBookEntity> iterator2 = bookList2.iterator();
			boolean isFound = false;
			while (iterator2.hasNext()) {
				BookOrdersBookEntity book2 = iterator2.next();
				if (skipIds || book1.getId().equals(book2.getId())) {
					try {
						assertSameOrderBook(book1, book2, skipIds, message);
						isFound = true;
						break;
					} catch (Exception e) {
						continue;
					}
				}
			}
			
			//if (!isFound)
			//	System.out.println();
			Assert.isTrue(isFound, "BookOrders record not found: "+book1);
		}
	}

}
