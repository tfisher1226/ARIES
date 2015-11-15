package bookshop2.util;

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
import org.aries.common.util.CommonFixture;
import org.aries.event.util.EventUtil;
import org.aries.message.util.MessageUtil;
import org.aries.util.BaseFixture;

import bookshop2.Book;
import bookshop2.BookCriteria;
import bookshop2.BookKey;
import bookshop2.BooksAvailableMessage;
import bookshop2.BooksUnavailableMessage;
import bookshop2.Invoice;
import bookshop2.Order;
import bookshop2.OrderAcceptedMessage;
import bookshop2.OrderCriteria;
import bookshop2.OrderKey;
import bookshop2.OrderRejectedMessage;
import bookshop2.OrderRequestMessage;
import bookshop2.OrderResponseMessage;
import bookshop2.Payment;
import bookshop2.PurchaseAcceptedMessage;
import bookshop2.PurchaseCompleteEvent;
import bookshop2.PurchaseRejectedMessage;
import bookshop2.PurchaseRequestMessage;
import bookshop2.QueryRequestMessage;
import bookshop2.Receipt;
import bookshop2.ReservationRequestMessage;
import bookshop2.Shipment;
import bookshop2.ShipmentCompleteEvent;
import bookshop2.ShipmentCompleteMessage;
import bookshop2.ShipmentConfirmedEvent;
import bookshop2.ShipmentFailedMessage;
import bookshop2.ShipmentRequestMessage;
import bookshop2.ShipmentScheduledEvent;
import bookshop2.ShipmentScheduledMessage;


public class Bookshop2Fixture extends BaseFixture {
	
	private static long bookCount = 0L;
	
	private static long bookKeyCount = 0L;
	
	private static long bookCriteriaCount = 0L;
	
	private static long orderCount = 0L;
	
	private static long orderKeyCount = 0L;
	
	private static long orderCriteriaCount = 0L;
	
	private static long paymentCount = 0L;
	
	private static long invoiceCount = 0L;
	
	private static long receiptCount = 0L;
	
	private static long shipmentCount = 0L;
	
	private static long purchaseCompleteEventCount = 0L;
	
	private static long shipmentCompleteEventCount = 0L;
	
	private static long shipmentConfirmedEventCount = 0L;
	
	private static long shipmentScheduledEventCount = 0L;
	
	private static long orderRequestMessageCount = 0L;
	
	private static long orderResponseMessageCount = 0L;
	
	private static long orderAcceptedMessageCount = 0L;
	
	private static long orderRejectedMessageCount = 0L;
	
	private static long purchaseRequestMessageCount = 0L;
	
	private static long purchaseAcceptedMessageCount = 0L;
	
	private static long purchaseRejectedMessageCount = 0L;
	
	private static long queryRequestMessageCount = 0L;
	
	private static long reservationRequestMessageCount = 0L;
	
	private static long booksAvailableMessageCount = 0L;
	
	private static long booksUnavailableMessageCount = 0L;
	
	private static long shipmentRequestMessageCount = 0L;
	
	private static long shipmentScheduledMessageCount = 0L;
	
	private static long shipmentCompleteMessageCount = 0L;
	
	private static long shipmentFailedMessageCount = 0L;

	
	public static void reset() {
		bookCount = 0L;
		bookKeyCount = 0L;
		bookCriteriaCount = 0L;
		orderCount = 0L;
		orderKeyCount = 0L;
		orderCriteriaCount = 0L;
		paymentCount = 0L;
		invoiceCount = 0L;
		receiptCount = 0L;
		shipmentCount = 0L;
		purchaseCompleteEventCount = 0L;
		shipmentCompleteEventCount = 0L;
		shipmentConfirmedEventCount = 0L;
		shipmentScheduledEventCount = 0L;
		orderRequestMessageCount = 0L;
		orderResponseMessageCount = 0L;
		orderAcceptedMessageCount = 0L;
		orderRejectedMessageCount = 0L;
		purchaseRequestMessageCount = 0L;
		purchaseAcceptedMessageCount = 0L;
		purchaseRejectedMessageCount = 0L;
		queryRequestMessageCount = 0L;
		reservationRequestMessageCount = 0L;
		booksAvailableMessageCount = 0L;
		booksUnavailableMessageCount = 0L;
		shipmentRequestMessageCount = 0L;
		shipmentScheduledMessageCount = 0L;
		shipmentCompleteMessageCount = 0L;
		shipmentFailedMessageCount = 0L;
	}
	
	public static Book createEmpty_Book() {
		Book book = new Book();
		bookCount++;
		return book;
	}
	
	public static Book create_Book() {
		Book book = create_Book(1);
		return book;
	}
	
	public static Book create_Book(long index) {
		Book book = createEmpty_Book();
		long value = createValue(index, bookCount);
		book.setBarCode((long) value);
		book.setAuthor("dummyAuthor" + value);
		book.setTitle("dummyTitle" + value);
		book.setPrice((double) value);
		book.setQuantity((int) value);
		book.setId(value);
		return book;
	}
	
	public static List<Book> createEmptyList_Book() {
		return new ArrayList<Book>();
	}
	
	public static List<Book> createList_Book() {
		return createList_Book(2);
	}

	public static List<Book> createList_Book(int size) {
		return createList_Book(1, size);
	}
	
	public static List<Book> createList_Book(long index, int size) {
		List<Book> bookList = createEmptyList_Book();
		long limit = index + size;
		for (; index < limit; index++)
			bookList.add(create_Book(index));
		return bookList;
	}

	public static Set<Book> createEmptySet_Book() {
		return new HashSet<Book>();
	}
	
	public static Set<Book> createSet_Book() {
		return createSet_Book(2);
	}

	public static Set<Book> createSet_Book(int size) {
		return createSet_Book(1, size);
	}
	
	public static Set<Book> createSet_Book(long index, int size) {
		Set<Book> bookSet = createEmptySet_Book();
		long limit = index + size;
		for (; index < limit; index++)
			bookSet.add(create_Book(index));
		return bookSet;
	}

	public static Map<BookKey, Book> createEmptyMap_Book() {
		return new HashMap<BookKey, Book>();
	}
	
	public static Map<BookKey, Book> createMap_Book() {
		return createMap_Book(2);
	}
	
	public static Map<BookKey, Book> createMap_Book(int size) {
		Map<BookKey, Book> bookMap = createEmptyMap_Book();
		for (long index=1; index <= size; index++)
			bookMap.put(create_BookKey(index), create_Book(index));
		return bookMap;
	}
	
	public static Map<BookKey, Book> createMap_Book(BookKey bookKey, Book book) {
		Map<BookKey, Book> bookMap = new HashMap<BookKey, Book>();
		bookMap.put(bookKey, book);
		return bookMap;
	}
	
	public static BookKey createEmpty_BookKey() {
		BookKey bookKey = new BookKey();
		bookKeyCount++;
		return bookKey;
	}
	
	public static BookKey create_BookKey() {
		BookKey bookKey = create_BookKey(1);
		return bookKey;
	}
	
	public static BookKey create_BookKey(long index) {
		BookKey bookKey = createEmpty_BookKey();
		long value = createValue(index, bookKeyCount);
		bookKey.setAuthor("dummyAuthor" + value);
		bookKey.setTitle("dummyTitle" + value);
		return bookKey;
	}
	
	public static List<BookKey> createEmptyList_BookKey() {
		return new ArrayList<BookKey>();
	}
	
	public static List<BookKey> createList_BookKey() {
		return createList_BookKey(2);
	}
	
	public static List<BookKey> createList_BookKey(int size) {
		return createList_BookKey(1, size);
	}
	
	public static List<BookKey> createList_BookKey(long index, int size) {
		List<BookKey> bookKeyList = createEmptyList_BookKey();
		long limit = index + size;
		for (; index < limit; index++)
			bookKeyList.add(create_BookKey(index));
		return bookKeyList;
	}
	
	public static void assertBookExists(Collection<Book> bookList, Book book) {
		Assert.notNull(bookList, "Book list must be specified");
		Assert.notNull(book, "Book record must be specified");
		Iterator<Book> iterator = bookList.iterator();
		while (iterator.hasNext()) {
			Book book1 = iterator.next();
			if (book1.equals(book)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - Book should exist: "+book);
	}
	
	public static void assertBookCorrect(Book book) {
		long value = book.getId();
		assertObjectCorrect("BarCode", book.getBarCode(), value);
		assertObjectCorrect("Author", book.getAuthor(), value);
		assertObjectCorrect("Title", book.getTitle(), value);
		assertObjectCorrect("Price", book.getPrice(), value);
		assertObjectCorrect("Quantity", book.getQuantity(), value);
	}
	
	public static void assertBookCorrect(Collection<Book> bookList) {
		Assert.isTrue(bookList.size() == 2, "Book count not correct");
		Iterator<Book> iterator = bookList.iterator();
		while (iterator.hasNext()) {
			Book book = iterator.next();
			assertBookCorrect(book);
		}
	}
	
	public static void assertSameBook(Book book1, Book book2) {
		assertSameBook(book1, book2, false, "");
	}
	
	public static void assertSameBook(Book book1, Book book2, String message) {
		assertSameBook(book1, book2, false, message);
	}
	
	public static void assertSameBook(Book book1, Book book2, boolean checkIds) {
		assertSameBook(book1, book2, checkIds, "");
	}
	
	public static void assertSameBook(Book book1, Book book2, boolean checkIds, String message) {
		assertObjectExists("Book1", book1);
		assertObjectExists("Book2", book2);
		if (checkIds)
			assertObjectEquals("Id", book1.getId(), book2.getId(), message);
		assertObjectEquals("BarCode", book1.getBarCode(), book2.getBarCode(), message);
		assertObjectEquals("Author", book1.getAuthor(), book2.getAuthor(), message);
		assertObjectEquals("Title", book1.getTitle(), book2.getTitle(), message);
		assertObjectEquals("Price", book1.getPrice(), book2.getPrice(), message);
		assertObjectEquals("Quantity", book1.getQuantity(), book2.getQuantity(), message);
	}

	public static void assertSameBook(Collection<Book> bookList1, Collection<Book> bookList2) {
		assertSameBook(bookList1, bookList2, false, "");
	}
	
	public static void assertSameBook(Collection<Book> bookList1, Collection<Book> bookList2, String message) {
		assertSameBook(bookList1, bookList2, false, message);
	}
	
	public static void assertSameBook(Collection<Book> bookList1, Collection<Book> bookList2, boolean checkIds) {
		assertSameBook(bookList1, bookList2, checkIds, "");
	}
	
	public static void assertSameBook(Collection<Book> bookList1, Collection<Book> bookList2, boolean checkIds, String message) {
		Assert.notNull(bookList1, "Book list1 must be specified");
		Assert.notNull(bookList2, "Book list2 must be specified");
		Assert.equals(bookList1.size(), bookList2.size(), "Book count not equal");
		Collection<Book> sortedRecords1 = BookUtil.sortRecords(bookList1);
		Collection<Book> sortedRecords2 = BookUtil.sortRecords(bookList2);
		Iterator<Book> list1Iterator = sortedRecords1.iterator();
		Iterator<Book> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			Book book1 = list1Iterator.next();
			Book book2 = list2Iterator.next();
			assertSameBook(book1, book2, checkIds, message);
		}
	}
	
	public static void assertSameBook(Map<BookKey, Book> bookMap1, Map<BookKey, Book> bookMap2) {
		assertSameBook(bookMap1, bookMap2, "");
	}
	
	public static void assertSameBook(Map<BookKey, Book> bookMap1, Map<BookKey, Book> bookMap2, String message) {
		Assert.notNull(bookMap1, "Book map1 must be specified");
		Assert.notNull(bookMap2, "Book map2 must be specified");
		Assert.isTrue(bookMap1.size() == bookMap2.size(), "Book count not correct");
		Set<BookKey> keySet = bookMap1.keySet();
		Iterator<BookKey> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			BookKey bookKey = iterator.next();
			Book book1 = bookMap1.get(bookKey);
			Book book2 = bookMap2.get(bookKey);
			assertSameBook(book1, book2);
		}
	}
	
	public static Set<BookKey> createEmptySet_BookKey() {
		return new HashSet<BookKey>();
	}
	
	public static Set<BookKey> createSet_BookKey() {
		return createSet_BookKey(2);
	}
	
	public static Set<BookKey> createSet_BookKey(int size) {
		return createSet_BookKey(1, size);
	}
	
	public static Set<BookKey> createSet_BookKey(long index, int size) {
		Set<BookKey> bookKeySet = createEmptySet_BookKey();
		long limit = index + size;
		for (; index < limit; index++)
			bookKeySet.add(create_BookKey(index));
		return bookKeySet;
	}
	
	public static void assertBookKeyExists(Collection<BookKey> bookKeyList, BookKey bookKey) {
		Assert.notNull(bookKeyList, "BookKey list must be specified");
		Assert.notNull(bookKey, "BookKey record must be specified");
		Iterator<BookKey> iterator = bookKeyList.iterator();
		while (iterator.hasNext()) {
			BookKey bookKey1 = iterator.next();
			if (bookKey1.equals(bookKey)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - BookKey should exist: "+bookKey);
	}
	
	public static void assertBookKeyCorrect(BookKey bookKey) {
		assertObjectCorrect("Author", bookKey.getAuthor());
		assertObjectCorrect("Title", bookKey.getTitle());
	}
	
	public static void assertBookKeyCorrect(Collection<BookKey> bookKeyList) {
		Assert.isTrue(bookKeyList.size() == 2, "BookKey count not correct");
		Iterator<BookKey> iterator = bookKeyList.iterator();
		while (iterator.hasNext()) {
			BookKey bookKey = iterator.next();
			assertBookKeyCorrect(bookKey);
		}
	}
	
	public static void assertSameBookKey(BookKey bookKey1, BookKey bookKey2) {
		assertSameBookKey(bookKey1, bookKey2, "");
	}
	
	public static void assertSameBookKey(BookKey bookKey1, BookKey bookKey2, String message) {
		assertObjectExists("BookKey1", bookKey1);
		assertObjectExists("BookKey2", bookKey2);
		assertObjectEquals("Author", bookKey1.getAuthor(), bookKey2.getAuthor(), message);
		assertObjectEquals("Title", bookKey1.getTitle(), bookKey2.getTitle(), message);
	}
	
	public static void assertSameBookKey(Collection<BookKey> bookKeyList1, Collection<BookKey> bookKeyList2) {
		assertSameBookKey(bookKeyList1, bookKeyList2, "");
	}
	
	public static void assertSameBookKey(Collection<BookKey> bookKeyList1, Collection<BookKey> bookKeyList2, String message) {
		Assert.notNull(bookKeyList1, "BookKey list1 must be specified");
		Assert.notNull(bookKeyList2, "BookKey list2 must be specified");
		Assert.equals(bookKeyList1.size(), bookKeyList2.size(), "BookKey count not equal");
		Collection<BookKey> sortedRecords1 = BookKeyUtil.sortRecords(bookKeyList1);
		Collection<BookKey> sortedRecords2 = BookKeyUtil.sortRecords(bookKeyList2);
		Iterator<BookKey> list1Iterator = sortedRecords1.iterator();
		Iterator<BookKey> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			BookKey bookKey1 = list1Iterator.next();
			BookKey bookKey2 = list2Iterator.next();
			assertSameBookKey(bookKey1, bookKey2, message);
		}
	}
	
	public static BookCriteria createEmpty_BookCriteria() {
		BookCriteria bookCriteria = new BookCriteria();
		bookCriteriaCount++;
		return bookCriteria;
	}
	
	public static BookCriteria create_BookCriteria() {
		BookCriteria bookCriteria = create_BookCriteria(1);
		return bookCriteria;
	}
	
	public static BookCriteria create_BookCriteria(long index) {
		BookCriteria bookCriteria = createEmpty_BookCriteria();
		long value = createValue(index, bookCriteriaCount);
		bookCriteria.setAuthor("dummyAuthor" + value);
		bookCriteria.setTitle("dummyTitle" + value);
		bookCriteria.setPrice((double) value);
		bookCriteria.setQuantity((int) value);
		return bookCriteria;
	}
	
	public static List<BookCriteria> createEmptyList_BookCriteria() {
		return new ArrayList<BookCriteria>();
	}
	
	public static List<BookCriteria> createList_BookCriteria() {
		return createList_BookCriteria(2);
	}
	
	public static List<BookCriteria> createList_BookCriteria(int size) {
		return createList_BookCriteria(1, size);
	}
	
	public static List<BookCriteria> createList_BookCriteria(long index, int size) {
		List<BookCriteria> bookCriteriaList = createEmptyList_BookCriteria();
		long limit = index + size;
		for (; index < limit; index++)
			bookCriteriaList.add(create_BookCriteria(index));
		return bookCriteriaList;
	}
	
	public static Set<BookCriteria> createEmptySet_BookCriteria() {
		return new HashSet<BookCriteria>();
	}
	
	public static Set<BookCriteria> createSet_BookCriteria() {
		return createSet_BookCriteria(2);
	}
	
	public static Set<BookCriteria> createSet_BookCriteria(int size) {
		return createSet_BookCriteria(1, size);
	}
	
	public static Set<BookCriteria> createSet_BookCriteria(long index, int size) {
		Set<BookCriteria> bookCriteriaSet = createEmptySet_BookCriteria();
		long limit = index + size;
		for (; index < limit; index++)
			bookCriteriaSet.add(create_BookCriteria(index));
		return bookCriteriaSet;
	}
	
	public static void assertBookCriteriaExists(Collection<BookCriteria> bookCriteriaList, BookCriteria bookCriteria) {
		Assert.notNull(bookCriteriaList, "BookCriteria list must be specified");
		Assert.notNull(bookCriteria, "BookCriteria record must be specified");
		Iterator<BookCriteria> iterator = bookCriteriaList.iterator();
		while (iterator.hasNext()) {
			BookCriteria bookCriteria1 = iterator.next();
			if (bookCriteria1.equals(bookCriteria)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - BookCriteria should exist: "+bookCriteria);
	}
	
	public static void assertBookCriteriaCorrect(BookCriteria bookCriteria) {
		assertObjectCorrect("Author", bookCriteria.getAuthor());
		assertObjectCorrect("Title", bookCriteria.getTitle());
		assertObjectCorrect("Price", bookCriteria.getPrice());
		assertObjectCorrect("Quantity", bookCriteria.getQuantity());
	}
	
	public static void assertBookCriteriaCorrect(Collection<BookCriteria> bookCriteriaList) {
		Assert.isTrue(bookCriteriaList.size() == 2, "BookCriteria count not correct");
		Iterator<BookCriteria> iterator = bookCriteriaList.iterator();
		while (iterator.hasNext()) {
			BookCriteria bookCriteria = iterator.next();
			assertBookCriteriaCorrect(bookCriteria);
		}
	}
	
	public static Order createEmpty_Order() {
		Order order = new Order();
		orderCount++;
		return order;
	}
	
	public static Order create_Order() {
		Order order = create_Order(1);
		return order;
	}
	
	public static Order create_Order(long index) {
		Order order = createEmpty_Order();
		long value = createValue(index, orderCount);
		order.setTrackingNumber("dummyTrackingNumber" + value);
		order.setCount((int) value);
		order.setDateTime(new Date(value + 1000L));
		order.setPersonName(CommonFixture.create_PersonName(value));
		order.getBooks().addAll(createList_Book(2));
		order.setId(value);
		return order;
	}
	
	public static List<Order> createEmptyList_Order() {
		return new ArrayList<Order>();
	}
	
	public static List<Order> createList_Order() {
		return createList_Order(2);
	}
	
	public static List<Order> createList_Order(int size) {
		return createList_Order(1, size);
	}
	
	public static List<Order> createList_Order(long index, int size) {
		List<Order> orderList = createEmptyList_Order();
		long limit = index + size;
		for (; index < limit; index++)
			orderList.add(create_Order(index));
		return orderList;
	}
	
	public static Set<Order> createEmptySet_Order() {
		return new HashSet<Order>();
	}
	
	public static Set<Order> createSet_Order() {
		return createSet_Order(2);
	}
	
	public static Set<Order> createSet_Order(int size) {
		return createSet_Order(1, size);
	}
	
	public static Set<Order> createSet_Order(long index, int size) {
		Set<Order> orderSet = createEmptySet_Order();
		long limit = index + size;
		for (; index < limit; index++)
			orderSet.add(create_Order(index));
		return orderSet;
	}
	
	public static Map<OrderKey, Order> createEmptyMap_Order() {
		return new HashMap<OrderKey, Order>();
	}
	
	public static Map<OrderKey, Order> createMap_Order() {
		return createMap_Order(2);
	}
	
	public static Map<OrderKey, Order> createMap_Order(int size) {
		Map<OrderKey, Order> orderMap = createEmptyMap_Order();
		for (long index=1; index <= size; index++)
			orderMap.put(create_OrderKey(index), create_Order(index));
		return orderMap;
	}
	
	public static OrderKey createEmpty_OrderKey() {
		OrderKey orderKey = new OrderKey();
		orderKeyCount++;
		return orderKey;
	}
	
	public static OrderKey create_OrderKey() {
		OrderKey orderKey = create_OrderKey(1);
		return orderKey;
	}
	
	public static OrderKey create_OrderKey(long index) {
		OrderKey orderKey = createEmpty_OrderKey();
		long value = createValue(index, orderKeyCount);
		orderKey.setDateTime(new Date(value + 1000L));
		orderKey.setPersonName(CommonFixture.create_PersonName(value));
		return orderKey;
	}
	
	public static List<OrderKey> createEmptyList_OrderKey() {
		return new ArrayList<OrderKey>();
	}
	
	public static List<OrderKey> createList_OrderKey() {
		return createList_OrderKey(2);
	}
	
	public static List<OrderKey> createList_OrderKey(int size) {
		return createList_OrderKey(1, size);
	}
	
	public static List<OrderKey> createList_OrderKey(long index, int size) {
		List<OrderKey> orderKeyList = createEmptyList_OrderKey();
		long limit = index + size;
		for (; index < limit; index++)
			orderKeyList.add(create_OrderKey(index));
		return orderKeyList;
	}
	
	public static void assertOrderExists(Collection<Order> orderList, Order order) {
		Assert.notNull(orderList, "Order list must be specified");
		Assert.notNull(order, "Order record must be specified");
		Iterator<Order> iterator = orderList.iterator();
		while (iterator.hasNext()) {
			Order order1 = iterator.next();
			if (order1.equals(order)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - Order should exist: "+order);
	}
	
	public static void assertOrderCorrect(Order order) {
		long value = order.getId();
		assertObjectCorrect("TrackingNumber", order.getTrackingNumber(), value);
		assertObjectCorrect("Count", order.getCount(), value);
		assertObjectCorrect("DateTime", order.getDateTime(), value);
		CommonFixture.assertPersonNameCorrect(order.getPersonName());
		assertBookCorrect(order.getBooks());
	}

	public static void assertOrderCorrect(Collection<Order> orderList) {
		Assert.isTrue(orderList.size() == 2, "Order count not correct");
		Iterator<Order> iterator = orderList.iterator();
		while (iterator.hasNext()) {
			Order order = iterator.next();
			assertOrderCorrect(order);
		}
	}
	
	public static void assertSameOrder(Order order1, Order order2) {
		assertSameOrder(order1, order2, false, "");
	}

	public static void assertSameOrder(Order order1, Order order2, String message) {
		assertSameOrder(order1, order2, false, message);
	}
	
	public static void assertSameOrder(Order order1, Order order2, boolean checkIds) {
		assertSameOrder(order1, order2, checkIds, "");
	}
	
	public static void assertSameOrder(Order order1, Order order2, boolean checkIds, String message) {
		assertObjectExists("Order1", order1);
		assertObjectExists("Order2", order2);
		if (checkIds)
			assertObjectEquals("Id", order1.getId(), order2.getId(), message);
		assertObjectEquals("TrackingNumber", order1.getTrackingNumber(), order2.getTrackingNumber(), message);
		assertObjectEquals("Count", order1.getCount(), order2.getCount(), message);
		assertObjectEquals("DateTime", order1.getDateTime(), order2.getDateTime(), message);
		CommonFixture.assertSamePersonName(order1.getPersonName(), order2.getPersonName(), message);
		assertSameBook(order1.getBooks(), order2.getBooks(), message);
	}

	public static void assertSameOrder(Collection<Order> orderList1, Collection<Order> orderList2) {
		assertSameOrder(orderList1, orderList2, false, "");
	}

	public static void assertSameOrder(Collection<Order> orderList1, Collection<Order> orderList2, String message) {
		assertSameOrder(orderList1, orderList2, false, message);
	}
	
	public static void assertSameOrder(Collection<Order> orderList1, Collection<Order> orderList2, boolean checkIds) {
		assertSameOrder(orderList1, orderList2, checkIds, "");
	}

	public static void assertSameOrder(Collection<Order> orderList1, Collection<Order> orderList2, boolean checkIds, String message) {
		Assert.notNull(orderList1, "Order list1 must be specified");
		Assert.notNull(orderList2, "Order list2 must be specified");
		Assert.equals(orderList1.size(), orderList2.size(), "Order count not equal");
		Collection<Order> sortedRecords1 = OrderUtil.sortRecords(orderList1);
		Collection<Order> sortedRecords2 = OrderUtil.sortRecords(orderList2);
		Iterator<Order> list1Iterator = sortedRecords1.iterator();
		Iterator<Order> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			Order order1 = list1Iterator.next();
			Order order2 = list2Iterator.next();
			assertSameOrder(order1, order2, checkIds, message);
		}
	}

	public static void assertSameOrder(Map<OrderKey, Order> orderMap1, Map<OrderKey, Order> orderMap2) {
		assertSameOrder(orderMap1, orderMap2, "");
	}
	
	public static void assertSameOrder(Map<OrderKey, Order> orderMap1, Map<OrderKey, Order> orderMap2, String message) {
		Assert.notNull(orderMap1, "Order map1 must be specified");
		Assert.notNull(orderMap2, "Order map2 must be specified");
		Assert.isTrue(orderMap1.size() == orderMap2.size(), "Order count not correct");
		Set<OrderKey> keySet = orderMap1.keySet();
		Iterator<OrderKey> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			OrderKey orderKey = iterator.next();
			Order order1 = orderMap1.get(orderKey);
			Order order2 = orderMap2.get(orderKey);
			assertSameOrder(order1, order2);
		}
	}
	
	public static Set<OrderKey> createEmptySet_OrderKey() {
		return new HashSet<OrderKey>();
	}
	
	public static Set<OrderKey> createSet_OrderKey() {
		return createSet_OrderKey(2);
	}
	
	public static Set<OrderKey> createSet_OrderKey(int size) {
		return createSet_OrderKey(1, size);
	}
	
	public static Set<OrderKey> createSet_OrderKey(long index, int size) {
		Set<OrderKey> orderKeySet = createEmptySet_OrderKey();
		long limit = index + size;
		for (; index < limit; index++)
			orderKeySet.add(create_OrderKey(index));
		return orderKeySet;
	}
	
	public static void assertOrderKeyExists(Collection<OrderKey> orderKeyList, OrderKey orderKey) {
		Assert.notNull(orderKeyList, "OrderKey list must be specified");
		Assert.notNull(orderKey, "OrderKey record must be specified");
		Iterator<OrderKey> iterator = orderKeyList.iterator();
		while (iterator.hasNext()) {
			OrderKey orderKey1 = iterator.next();
			if (orderKey1.equals(orderKey)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - OrderKey should exist: "+orderKey);
	}
	
	public static void assertOrderKeyCorrect(OrderKey orderKey) {
		assertObjectCorrect("DateTime", orderKey.getDateTime());
		CommonFixture.assertPersonNameCorrect(orderKey.getPersonName());
	}
	
	public static void assertOrderKeyCorrect(Collection<OrderKey> orderKeyList) {
		Assert.isTrue(orderKeyList.size() == 2, "OrderKey count not correct");
		Iterator<OrderKey> iterator = orderKeyList.iterator();
		while (iterator.hasNext()) {
			OrderKey orderKey = iterator.next();
			assertOrderKeyCorrect(orderKey);
		}
	}
	
	public static void assertSameOrderKey(OrderKey orderKey1, OrderKey orderKey2) {
		assertSameOrderKey(orderKey1, orderKey2, "");
	}
	
	public static void assertSameOrderKey(OrderKey orderKey1, OrderKey orderKey2, String message) {
		assertObjectExists("OrderKey1", orderKey1);
		assertObjectExists("OrderKey2", orderKey2);
		assertObjectEquals("DateTime", orderKey1.getDateTime(), orderKey2.getDateTime(), message);
		CommonFixture.assertSamePersonName(orderKey1.getPersonName(), orderKey2.getPersonName(), message);
	}
	
	public static void assertSameOrderKey(Collection<OrderKey> orderKeyList1, Collection<OrderKey> orderKeyList2) {
		assertSameOrderKey(orderKeyList1, orderKeyList2, "");
	}
	
	public static void assertSameOrderKey(Collection<OrderKey> orderKeyList1, Collection<OrderKey> orderKeyList2, String message) {
		Assert.notNull(orderKeyList1, "OrderKey list1 must be specified");
		Assert.notNull(orderKeyList2, "OrderKey list2 must be specified");
		Assert.equals(orderKeyList1.size(), orderKeyList2.size(), "OrderKey count not equal");
		Collection<OrderKey> sortedRecords1 = OrderKeyUtil.sortRecords(orderKeyList1);
		Collection<OrderKey> sortedRecords2 = OrderKeyUtil.sortRecords(orderKeyList2);
		Iterator<OrderKey> list1Iterator = sortedRecords1.iterator();
		Iterator<OrderKey> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			OrderKey orderKey1 = list1Iterator.next();
			OrderKey orderKey2 = list2Iterator.next();
			assertSameOrderKey(orderKey1, orderKey2, message);
		}
	}
	
	public static OrderCriteria createEmpty_OrderCriteria() {
		OrderCriteria orderCriteria = new OrderCriteria();
		orderCriteriaCount++;
		return orderCriteria;
	}
	
	public static OrderCriteria create_OrderCriteria() {
		OrderCriteria orderCriteria = create_OrderCriteria(1);
		return orderCriteria;
	}
	
	public static OrderCriteria create_OrderCriteria(long index) {
		OrderCriteria orderCriteria = createEmpty_OrderCriteria();
		long value = createValue(index, orderCriteriaCount);
		orderCriteria.setCount((int) value);
		orderCriteria.getBooks().addAll(createList_Book(2));
		return orderCriteria;
	}
	
	public static List<OrderCriteria> createEmptyList_OrderCriteria() {
		return new ArrayList<OrderCriteria>();
	}
	
	public static List<OrderCriteria> createList_OrderCriteria() {
		return createList_OrderCriteria(2);
	}
	
	public static List<OrderCriteria> createList_OrderCriteria(int size) {
		return createList_OrderCriteria(1, size);
	}
	
	public static List<OrderCriteria> createList_OrderCriteria(long index, int size) {
		List<OrderCriteria> orderCriteriaList = createEmptyList_OrderCriteria();
		long limit = index + size;
		for (; index < limit; index++)
			orderCriteriaList.add(create_OrderCriteria(index));
		return orderCriteriaList;
	}
	
	public static Set<OrderCriteria> createEmptySet_OrderCriteria() {
		return new HashSet<OrderCriteria>();
	}
	
	public static Set<OrderCriteria> createSet_OrderCriteria() {
		return createSet_OrderCriteria(2);
	}
	
	public static Set<OrderCriteria> createSet_OrderCriteria(int size) {
		return createSet_OrderCriteria(1, size);
	}
	
	public static Set<OrderCriteria> createSet_OrderCriteria(long index, int size) {
		Set<OrderCriteria> orderCriteriaSet = createEmptySet_OrderCriteria();
		long limit = index + size;
		for (; index < limit; index++)
			orderCriteriaSet.add(create_OrderCriteria(index));
		return orderCriteriaSet;
	}
	
	public static void assertOrderCriteriaExists(Collection<OrderCriteria> orderCriteriaList, OrderCriteria orderCriteria) {
		Assert.notNull(orderCriteriaList, "OrderCriteria list must be specified");
		Assert.notNull(orderCriteria, "OrderCriteria record must be specified");
		Iterator<OrderCriteria> iterator = orderCriteriaList.iterator();
		while (iterator.hasNext()) {
			OrderCriteria orderCriteria1 = iterator.next();
			if (orderCriteria1.equals(orderCriteria)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - OrderCriteria should exist: "+orderCriteria);
	}
	
	public static void assertOrderCriteriaCorrect(OrderCriteria orderCriteria) {
		assertObjectCorrect("Count", orderCriteria.getCount());
		assertBookCorrect(orderCriteria.getBooks());
	}
	
	public static void assertOrderCriteriaCorrect(Collection<OrderCriteria> orderCriteriaList) {
		Assert.isTrue(orderCriteriaList.size() == 2, "OrderCriteria count not correct");
		Iterator<OrderCriteria> iterator = orderCriteriaList.iterator();
		while (iterator.hasNext()) {
			OrderCriteria orderCriteria = iterator.next();
			assertOrderCriteriaCorrect(orderCriteria);
		}
	}
	
	public static Payment createEmpty_Payment() {
		Payment payment = new Payment();
		paymentCount++;
		return payment;
	}
	
	public static Payment create_Payment() {
		Payment payment = create_Payment(1);
		return payment;
	}
	
	public static Payment create_Payment(long index) {
		Payment payment = createEmpty_Payment();
		long value = createValue(index, paymentCount);
		payment.setAmount((double) value);
		payment.setCurrency("dummyCurrency" + value);
		payment.setSalesTax((double) value);
		payment.setTotal((double) value);
		payment.setDateTime(new Date(value + 1000L));
		payment.setId(value);
		return payment;
	}
	
	public static List<Payment> createEmptyList_Payment() {
		return new ArrayList<Payment>();
	}
	
	public static List<Payment> createList_Payment() {
		return createList_Payment(2);
	}
	
	public static List<Payment> createList_Payment(int size) {
		return createList_Payment(1, size);
	}
	
	public static List<Payment> createList_Payment(long index, int size) {
		List<Payment> paymentList = createEmptyList_Payment();
		long limit = index + size;
		for (; index < limit; index++)
			paymentList.add(create_Payment(index));
		return paymentList;
	}
	
	public static Set<Payment> createEmptySet_Payment() {
		return new HashSet<Payment>();
	}
	
	public static Set<Payment> createSet_Payment() {
		return createSet_Payment(2);
	}
	
	public static Set<Payment> createSet_Payment(int size) {
		return createSet_Payment(1, size);
	}
	
	public static Set<Payment> createSet_Payment(long index, int size) {
		Set<Payment> paymentSet = createEmptySet_Payment();
		long limit = index + size;
		for (; index < limit; index++)
			paymentSet.add(create_Payment(index));
		return paymentSet;
	}
	
	public static void assertPaymentExists(Collection<Payment> paymentList, Payment payment) {
		Assert.notNull(paymentList, "Payment list must be specified");
		Assert.notNull(payment, "Payment record must be specified");
		Iterator<Payment> iterator = paymentList.iterator();
		while (iterator.hasNext()) {
			Payment payment1 = iterator.next();
			if (payment1.equals(payment)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - Payment should exist: "+payment);
	}
	
	public static void assertPaymentCorrect(Payment payment) {
		long value = payment.getId();
		assertObjectCorrect("Amount", payment.getAmount(), value);
		assertObjectCorrect("Currency", payment.getCurrency(), value);
		assertObjectCorrect("SalesTax", payment.getSalesTax(), value);
		assertObjectCorrect("Total", payment.getTotal(), value);
		assertObjectCorrect("DateTime", payment.getDateTime(), value);
	}
	
	public static void assertPaymentCorrect(Collection<Payment> paymentList) {
		Assert.isTrue(paymentList.size() == 2, "Payment count not correct");
		Iterator<Payment> iterator = paymentList.iterator();
		while (iterator.hasNext()) {
			Payment payment = iterator.next();
			assertPaymentCorrect(payment);
		}
	}
	
	public static void assertSamePayment(Payment payment1, Payment payment2) {
		assertSamePayment(payment1, payment2, false, "");
	}
	
	public static void assertSamePayment(Payment payment1, Payment payment2, String message) {
		assertSamePayment(payment1, payment2, false, message);
	}
	
	public static void assertSamePayment(Payment payment1, Payment payment2, boolean checkIds) {
		assertSamePayment(payment1, payment2, checkIds, "");
	}
	
	public static void assertSamePayment(Payment payment1, Payment payment2, boolean checkIds, String message) {
		assertObjectExists("Payment1", payment1);
		assertObjectExists("Payment2", payment2);
		if (checkIds)
			assertObjectEquals("Id", payment1.getId(), payment2.getId(), message);
		assertObjectEquals("Amount", payment1.getAmount(), payment2.getAmount(), message);
		assertObjectEquals("Currency", payment1.getCurrency(), payment2.getCurrency(), message);
		assertObjectEquals("SalesTax", payment1.getSalesTax(), payment2.getSalesTax(), message);
		assertObjectEquals("Total", payment1.getTotal(), payment2.getTotal(), message);
		assertObjectEquals("DateTime", payment1.getDateTime(), payment2.getDateTime(), message);
	}
	
	public static void assertSamePayment(Collection<Payment> paymentList1, Collection<Payment> paymentList2) {
		assertSamePayment(paymentList1, paymentList2, false, "");
	}
	
	public static void assertSamePayment(Collection<Payment> paymentList1, Collection<Payment> paymentList2, String message) {
		assertSamePayment(paymentList1, paymentList2, false, message);
	}
	
	public static void assertSamePayment(Collection<Payment> paymentList1, Collection<Payment> paymentList2, boolean checkIds) {
		assertSamePayment(paymentList1, paymentList2, checkIds, "");
	}
	
	public static void assertSamePayment(Collection<Payment> paymentList1, Collection<Payment> paymentList2, boolean checkIds, String message) {
		Assert.notNull(paymentList1, "Payment list1 must be specified");
		Assert.notNull(paymentList2, "Payment list2 must be specified");
		Assert.equals(paymentList1.size(), paymentList2.size(), "Payment count not equal");
		Collection<Payment> sortedRecords1 = PaymentUtil.sortRecords(paymentList1);
		Collection<Payment> sortedRecords2 = PaymentUtil.sortRecords(paymentList2);
		Iterator<Payment> list1Iterator = sortedRecords1.iterator();
		Iterator<Payment> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			Payment payment1 = list1Iterator.next();
			Payment payment2 = list2Iterator.next();
			assertSamePayment(payment1, payment2, checkIds, message);
		}
	}
	
	public static Invoice createEmpty_Invoice() {
		Invoice invoice = new Invoice();
		invoiceCount++;
		return invoice;
	}
	
	public static Invoice create_Invoice() {
		Invoice invoice = create_Invoice(1);
		return invoice;
	}
	
	public static Invoice create_Invoice(long index) {
		Invoice invoice = createEmpty_Invoice();
		long value = createValue(index, invoiceCount);
		invoice.setDateTime(new Date(value + 1000L));
		invoice.setOrder(create_Order(value));
		invoice.setPayment(create_Payment(value));
		invoice.setId(value);
		return invoice;
	}
	
	public static List<Invoice> createEmptyList_Invoice() {
		return new ArrayList<Invoice>();
	}
	
	public static List<Invoice> createList_Invoice() {
		return createList_Invoice(2);
	}
	
	public static List<Invoice> createList_Invoice(int size) {
		return createList_Invoice(1, size);
	}
	
	public static List<Invoice> createList_Invoice(long index, int size) {
		List<Invoice> invoiceList = createEmptyList_Invoice();
		long limit = index + size;
		for (; index < limit; index++)
			invoiceList.add(create_Invoice(index));
		return invoiceList;
	}
	
	public static Set<Invoice> createEmptySet_Invoice() {
		return new HashSet<Invoice>();
	}
	
	public static Set<Invoice> createSet_Invoice() {
		return createSet_Invoice(2);
	}
	
	public static Set<Invoice> createSet_Invoice(int size) {
		return createSet_Invoice(1, size);
	}
	
	public static Set<Invoice> createSet_Invoice(long index, int size) {
		Set<Invoice> invoiceSet = createEmptySet_Invoice();
		long limit = index + size;
		for (; index < limit; index++)
			invoiceSet.add(create_Invoice(index));
		return invoiceSet;
	}
	
	public static void assertInvoiceExists(Collection<Invoice> invoiceList, Invoice invoice) {
		Assert.notNull(invoiceList, "Invoice list must be specified");
		Assert.notNull(invoice, "Invoice record must be specified");
		Iterator<Invoice> iterator = invoiceList.iterator();
		while (iterator.hasNext()) {
			Invoice invoice1 = iterator.next();
			if (invoice1.equals(invoice)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - Invoice should exist: "+invoice);
	}
	
	public static void assertInvoiceCorrect(Invoice invoice) {
		long value = invoice.getId();
		assertObjectCorrect("DateTime", invoice.getDateTime(), value);
		assertOrderCorrect(invoice.getOrder());
		assertPaymentCorrect(invoice.getPayment());
	}
	
	public static void assertInvoiceCorrect(Collection<Invoice> invoiceList) {
		Assert.isTrue(invoiceList.size() == 2, "Invoice count not correct");
		Iterator<Invoice> iterator = invoiceList.iterator();
		while (iterator.hasNext()) {
			Invoice invoice = iterator.next();
			assertInvoiceCorrect(invoice);
		}
	}
	
	public static void assertSameInvoice(Invoice invoice1, Invoice invoice2) {
		assertSameInvoice(invoice1, invoice2, false, "");
	}
	
	public static void assertSameInvoice(Invoice invoice1, Invoice invoice2, String message) {
		assertSameInvoice(invoice1, invoice2, false, message);
	}
	
	public static void assertSameInvoice(Invoice invoice1, Invoice invoice2, boolean checkIds) {
		assertSameInvoice(invoice1, invoice2, checkIds, "");
	}
	
	public static void assertSameInvoice(Invoice invoice1, Invoice invoice2, boolean checkIds, String message) {
		assertObjectExists("Invoice1", invoice1);
		assertObjectExists("Invoice2", invoice2);
		if (checkIds)
			assertObjectEquals("Id", invoice1.getId(), invoice2.getId(), message);
		assertObjectEquals("DateTime", invoice1.getDateTime(), invoice2.getDateTime(), message);
		assertSameOrder(invoice1.getOrder(), invoice2.getOrder(), message);
		assertSamePayment(invoice1.getPayment(), invoice2.getPayment(), message);
	}
	
	public static void assertSameInvoice(Collection<Invoice> invoiceList1, Collection<Invoice> invoiceList2) {
		assertSameInvoice(invoiceList1, invoiceList2, false, "");
	}
	
	public static void assertSameInvoice(Collection<Invoice> invoiceList1, Collection<Invoice> invoiceList2, String message) {
		assertSameInvoice(invoiceList1, invoiceList2, false, message);
	}
	
	public static void assertSameInvoice(Collection<Invoice> invoiceList1, Collection<Invoice> invoiceList2, boolean checkIds) {
		assertSameInvoice(invoiceList1, invoiceList2, checkIds, "");
	}
	
	public static void assertSameInvoice(Collection<Invoice> invoiceList1, Collection<Invoice> invoiceList2, boolean checkIds, String message) {
		Assert.notNull(invoiceList1, "Invoice list1 must be specified");
		Assert.notNull(invoiceList2, "Invoice list2 must be specified");
		Assert.equals(invoiceList1.size(), invoiceList2.size(), "Invoice count not equal");
		Collection<Invoice> sortedRecords1 = InvoiceUtil.sortRecords(invoiceList1);
		Collection<Invoice> sortedRecords2 = InvoiceUtil.sortRecords(invoiceList2);
		Iterator<Invoice> list1Iterator = sortedRecords1.iterator();
		Iterator<Invoice> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			Invoice invoice1 = list1Iterator.next();
			Invoice invoice2 = list2Iterator.next();
			assertSameInvoice(invoice1, invoice2, checkIds, message);
		}
	}
	
	public static Receipt createEmpty_Receipt() {
		Receipt receipt = new Receipt();
		receiptCount++;
		return receipt;
	}
	
	public static Receipt create_Receipt() {
		Receipt receipt = create_Receipt(1);
		return receipt;
	}
	
	public static Receipt create_Receipt(long index) {
		Receipt receipt = createEmpty_Receipt();
		long value = createValue(index, receiptCount);
		receipt.setTotal((double) value);
		receipt.setDateTime(new Date(value + 1000L));
		receipt.setOrder(create_Order(value));
		receipt.setPayment(create_Payment(value));
		receipt.setId(value);
		return receipt;
	}
	
	public static List<Receipt> createEmptyList_Receipt() {
		return new ArrayList<Receipt>();
	}
	
	public static List<Receipt> createList_Receipt() {
		return createList_Receipt(2);
	}
	
	public static List<Receipt> createList_Receipt(int size) {
		return createList_Receipt(1, size);
	}
	
	public static List<Receipt> createList_Receipt(long index, int size) {
		List<Receipt> receiptList = createEmptyList_Receipt();
		long limit = index + size;
		for (; index < limit; index++)
			receiptList.add(create_Receipt(index));
		return receiptList;
	}
	
	public static Set<Receipt> createEmptySet_Receipt() {
		return new HashSet<Receipt>();
	}
	
	public static Set<Receipt> createSet_Receipt() {
		return createSet_Receipt(2);
	}
	
	public static Set<Receipt> createSet_Receipt(int size) {
		return createSet_Receipt(1, size);
	}
	
	public static Set<Receipt> createSet_Receipt(long index, int size) {
		Set<Receipt> receiptSet = createEmptySet_Receipt();
		long limit = index + size;
		for (; index < limit; index++)
			receiptSet.add(create_Receipt(index));
		return receiptSet;
	}
	
	public static void assertReceiptExists(Collection<Receipt> receiptList, Receipt receipt) {
		Assert.notNull(receiptList, "Receipt list must be specified");
		Assert.notNull(receipt, "Receipt record must be specified");
		Iterator<Receipt> iterator = receiptList.iterator();
		while (iterator.hasNext()) {
			Receipt receipt1 = iterator.next();
			if (receipt1.equals(receipt)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - Receipt should exist: "+receipt);
	}
	
	public static void assertReceiptCorrect(Receipt receipt) {
		long value = receipt.getId();
		assertObjectCorrect("Total", receipt.getTotal(), value);
		assertObjectCorrect("DateTime", receipt.getDateTime(), value);
		assertOrderCorrect(receipt.getOrder());
		assertPaymentCorrect(receipt.getPayment());
	}
	
	public static void assertReceiptCorrect(Collection<Receipt> receiptList) {
		Assert.isTrue(receiptList.size() == 2, "Receipt count not correct");
		Iterator<Receipt> iterator = receiptList.iterator();
		while (iterator.hasNext()) {
			Receipt receipt = iterator.next();
			assertReceiptCorrect(receipt);
		}
	}
	
	public static void assertSameReceipt(Receipt receipt1, Receipt receipt2) {
		assertSameReceipt(receipt1, receipt2, false, "");
	}
	
	public static void assertSameReceipt(Receipt receipt1, Receipt receipt2, String message) {
		assertSameReceipt(receipt1, receipt2, false, message);
	}
	
	public static void assertSameReceipt(Receipt receipt1, Receipt receipt2, boolean checkIds) {
		assertSameReceipt(receipt1, receipt2, checkIds, "");
	}
	
	public static void assertSameReceipt(Receipt receipt1, Receipt receipt2, boolean checkIds, String message) {
		assertObjectExists("Receipt1", receipt1);
		assertObjectExists("Receipt2", receipt2);
		if (checkIds)
			assertObjectEquals("Id", receipt1.getId(), receipt2.getId(), message);
		assertObjectEquals("Total", receipt1.getTotal(), receipt2.getTotal(), message);
		assertObjectEquals("DateTime", receipt1.getDateTime(), receipt2.getDateTime(), message);
		assertSameOrder(receipt1.getOrder(), receipt2.getOrder(), message);
		assertSamePayment(receipt1.getPayment(), receipt2.getPayment(), message);
	}
	
	public static void assertSameReceipt(Collection<Receipt> receiptList1, Collection<Receipt> receiptList2) {
		assertSameReceipt(receiptList1, receiptList2, false, "");
	}
	
	public static void assertSameReceipt(Collection<Receipt> receiptList1, Collection<Receipt> receiptList2, String message) {
		assertSameReceipt(receiptList1, receiptList2, false, message);
	}
	
	public static void assertSameReceipt(Collection<Receipt> receiptList1, Collection<Receipt> receiptList2, boolean checkIds) {
		assertSameReceipt(receiptList1, receiptList2, checkIds, "");
	}
	
	public static void assertSameReceipt(Collection<Receipt> receiptList1, Collection<Receipt> receiptList2, boolean checkIds, String message) {
		Assert.notNull(receiptList1, "Receipt list1 must be specified");
		Assert.notNull(receiptList2, "Receipt list2 must be specified");
		Assert.equals(receiptList1.size(), receiptList2.size(), "Receipt count not equal");
		Collection<Receipt> sortedRecords1 = ReceiptUtil.sortRecords(receiptList1);
		Collection<Receipt> sortedRecords2 = ReceiptUtil.sortRecords(receiptList2);
		Iterator<Receipt> list1Iterator = sortedRecords1.iterator();
		Iterator<Receipt> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			Receipt receipt1 = list1Iterator.next();
			Receipt receipt2 = list2Iterator.next();
			assertSameReceipt(receipt1, receipt2, checkIds, message);
		}
	}
	
	public static Shipment createEmpty_Shipment() {
		Shipment shipment = new Shipment();
		shipmentCount++;
		return shipment;
	}
	
	public static Shipment create_Shipment() {
		Shipment shipment = create_Shipment(1);
		return shipment;
	}
	
	public static Shipment create_Shipment(long index) {
		Shipment shipment = createEmpty_Shipment();
		long value = createValue(index, shipmentCount);
		shipment.setDate(new Date(value + 1000L));
		shipment.setTime(new Date(value + 1000L));
		shipment.setOrder(create_Order(value));
		shipment.setName(CommonFixture.create_PersonName(value));
		shipment.setAddress(CommonFixture.create_StreetAddress(value));
		shipment.setId(value);
		return shipment;
	}
	
	public static List<Shipment> createEmptyList_Shipment() {
		return new ArrayList<Shipment>();
	}
	
	public static List<Shipment> createList_Shipment() {
		return createList_Shipment(2);
	}
	
	public static List<Shipment> createList_Shipment(int size) {
		return createList_Shipment(1, size);
	}
	
	public static List<Shipment> createList_Shipment(long index, int size) {
		List<Shipment> shipmentList = createEmptyList_Shipment();
		long limit = index + size;
		for (; index < limit; index++)
			shipmentList.add(create_Shipment(index));
		return shipmentList;
	}
	
	public static Set<Shipment> createEmptySet_Shipment() {
		return new HashSet<Shipment>();
	}
	
	public static Set<Shipment> createSet_Shipment() {
		return createSet_Shipment(2);
	}
	
	public static Set<Shipment> createSet_Shipment(int size) {
		return createSet_Shipment(1, size);
	}
	
	public static Set<Shipment> createSet_Shipment(long index, int size) {
		Set<Shipment> shipmentSet = createEmptySet_Shipment();
		long limit = index + size;
		for (; index < limit; index++)
			shipmentSet.add(create_Shipment(index));
		return shipmentSet;
	}
	
	public static void assertShipmentExists(Collection<Shipment> shipmentList, Shipment shipment) {
		Assert.notNull(shipmentList, "Shipment list must be specified");
		Assert.notNull(shipment, "Shipment record must be specified");
		Iterator<Shipment> iterator = shipmentList.iterator();
		while (iterator.hasNext()) {
			Shipment shipment1 = iterator.next();
			if (shipment1.equals(shipment)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - Shipment should exist: "+shipment);
	}
	
	public static void assertShipmentCorrect(Shipment shipment) {
		long value = shipment.getId();
		assertObjectCorrect("Date", shipment.getDate(), value);
		assertObjectCorrect("Time", shipment.getTime(), value);
		assertOrderCorrect(shipment.getOrder());
		CommonFixture.assertPersonNameCorrect(shipment.getName());
		CommonFixture.assertStreetAddressCorrect(shipment.getAddress());
	}
	
	public static void assertShipmentCorrect(Collection<Shipment> shipmentList) {
		Assert.isTrue(shipmentList.size() == 2, "Shipment count not correct");
		Iterator<Shipment> iterator = shipmentList.iterator();
		while (iterator.hasNext()) {
			Shipment shipment = iterator.next();
			assertShipmentCorrect(shipment);
		}
	}
	
	public static void assertSameShipment(Shipment shipment1, Shipment shipment2) {
		assertSameShipment(shipment1, shipment2, false, "");
	}
	
	public static void assertSameShipment(Shipment shipment1, Shipment shipment2, String message) {
		assertSameShipment(shipment1, shipment2, false, message);
	}
	
	public static void assertSameShipment(Shipment shipment1, Shipment shipment2, boolean checkIds) {
		assertSameShipment(shipment1, shipment2, checkIds, "");
	}
	
	public static void assertSameShipment(Shipment shipment1, Shipment shipment2, boolean checkIds, String message) {
		assertObjectExists("Shipment1", shipment1);
		assertObjectExists("Shipment2", shipment2);
		if (checkIds)
			assertObjectEquals("Id", shipment1.getId(), shipment2.getId(), message);
		assertObjectEquals("Date", shipment1.getDate(), shipment2.getDate(), message);
		assertObjectEquals("Time", shipment1.getTime(), shipment2.getTime(), message);
		assertSameOrder(shipment1.getOrder(), shipment2.getOrder(), message);
		CommonFixture.assertSamePersonName(shipment1.getName(), shipment2.getName(), message);
		CommonFixture.assertSameStreetAddress(shipment1.getAddress(), shipment2.getAddress(), message);
	}
	
	public static void assertSameShipment(Collection<Shipment> shipmentList1, Collection<Shipment> shipmentList2) {
		assertSameShipment(shipmentList1, shipmentList2, false, "");
	}
	
	public static void assertSameShipment(Collection<Shipment> shipmentList1, Collection<Shipment> shipmentList2, String message) {
		assertSameShipment(shipmentList1, shipmentList2, false, message);
	}
	
	public static void assertSameShipment(Collection<Shipment> shipmentList1, Collection<Shipment> shipmentList2, boolean checkIds) {
		assertSameShipment(shipmentList1, shipmentList2, checkIds, "");
	}
	
	public static void assertSameShipment(Collection<Shipment> shipmentList1, Collection<Shipment> shipmentList2, boolean checkIds, String message) {
		Assert.notNull(shipmentList1, "Shipment list1 must be specified");
		Assert.notNull(shipmentList2, "Shipment list2 must be specified");
		Assert.equals(shipmentList1.size(), shipmentList2.size(), "Shipment count not equal");
		Collection<Shipment> sortedRecords1 = ShipmentUtil.sortRecords(shipmentList1);
		Collection<Shipment> sortedRecords2 = ShipmentUtil.sortRecords(shipmentList2);
		Iterator<Shipment> list1Iterator = sortedRecords1.iterator();
		Iterator<Shipment> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			Shipment shipment1 = list1Iterator.next();
			Shipment shipment2 = list2Iterator.next();
			assertSameShipment(shipment1, shipment2, checkIds, message);
		}
	}
	
	public static PurchaseCompleteEvent createEmpty_PurchaseCompleteEvent() {
		PurchaseCompleteEvent purchaseCompleteEvent = new PurchaseCompleteEvent();
		purchaseCompleteEventCount++;
		return purchaseCompleteEvent;
	}
	
	public static PurchaseCompleteEvent create_PurchaseCompleteEvent() {
		PurchaseCompleteEvent purchaseCompleteEvent = create_PurchaseCompleteEvent(1);
		return purchaseCompleteEvent;
	}
	
	public static PurchaseCompleteEvent create_PurchaseCompleteEvent(long index) {
		PurchaseCompleteEvent purchaseCompleteEvent = createEmpty_PurchaseCompleteEvent();
		long value = createValue(index, purchaseCompleteEventCount);
		purchaseCompleteEvent.setShipment(create_Shipment(value));
		purchaseCompleteEvent.setInvoice(create_Invoice(value));
		return purchaseCompleteEvent;
	}
	
	public static List<PurchaseCompleteEvent> createEmptyList_PurchaseCompleteEvent() {
		return new ArrayList<PurchaseCompleteEvent>();
	}
	
	public static List<PurchaseCompleteEvent> createList_PurchaseCompleteEvent() {
		return createList_PurchaseCompleteEvent(2);
	}
	
	public static List<PurchaseCompleteEvent> createList_PurchaseCompleteEvent(int size) {
		return createList_PurchaseCompleteEvent(1, size);
	}
	
	public static List<PurchaseCompleteEvent> createList_PurchaseCompleteEvent(long index, int size) {
		List<PurchaseCompleteEvent> purchaseCompleteEventList = createEmptyList_PurchaseCompleteEvent();
		long limit = index + size;
		for (; index < limit; index++)
			purchaseCompleteEventList.add(create_PurchaseCompleteEvent(index));
		return purchaseCompleteEventList;
	}
	
	public static Set<PurchaseCompleteEvent> createEmptySet_PurchaseCompleteEvent() {
		return new HashSet<PurchaseCompleteEvent>();
	}
	
	public static Set<PurchaseCompleteEvent> createSet_PurchaseCompleteEvent() {
		return createSet_PurchaseCompleteEvent(2);
	}
	
	public static Set<PurchaseCompleteEvent> createSet_PurchaseCompleteEvent(int size) {
		return createSet_PurchaseCompleteEvent(1, size);
	}
	
	public static Set<PurchaseCompleteEvent> createSet_PurchaseCompleteEvent(long index, int size) {
		Set<PurchaseCompleteEvent> purchaseCompleteEventSet = createEmptySet_PurchaseCompleteEvent();
		long limit = index + size;
		for (; index < limit; index++)
			purchaseCompleteEventSet.add(create_PurchaseCompleteEvent(index));
		return purchaseCompleteEventSet;
	}
	
	public static void assertPurchaseCompleteEventExists(Collection<PurchaseCompleteEvent> purchaseCompleteEventList, PurchaseCompleteEvent purchaseCompleteEvent) {
		Assert.notNull(purchaseCompleteEventList, "PurchaseCompleteEvent list must be specified");
		Assert.notNull(purchaseCompleteEvent, "PurchaseCompleteEvent record must be specified");
		Iterator<PurchaseCompleteEvent> iterator = purchaseCompleteEventList.iterator();
		while (iterator.hasNext()) {
			PurchaseCompleteEvent purchaseCompleteEvent1 = iterator.next();
			if (purchaseCompleteEvent1.equals(purchaseCompleteEvent)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - PurchaseCompleteEvent should exist: "+purchaseCompleteEvent);
	}
	
	public static void assertPurchaseCompleteEventCorrect(PurchaseCompleteEvent purchaseCompleteEvent) {
		assertShipmentCorrect(purchaseCompleteEvent.getShipment());
		assertInvoiceCorrect(purchaseCompleteEvent.getInvoice());
	}
	
	public static void assertPurchaseCompleteEventCorrect(Collection<PurchaseCompleteEvent> purchaseCompleteEventList) {
		Assert.isTrue(purchaseCompleteEventList.size() == 2, "PurchaseCompleteEvent count not correct");
		Iterator<PurchaseCompleteEvent> iterator = purchaseCompleteEventList.iterator();
		while (iterator.hasNext()) {
			PurchaseCompleteEvent purchaseCompleteEvent = iterator.next();
			assertPurchaseCompleteEventCorrect(purchaseCompleteEvent);
		}
	}
	
	public static void assertSamePurchaseCompleteEvent(PurchaseCompleteEvent purchaseCompleteEvent1, PurchaseCompleteEvent purchaseCompleteEvent2) {
		assertSamePurchaseCompleteEvent(purchaseCompleteEvent1, purchaseCompleteEvent2, "");
	}
	
	public static void assertSamePurchaseCompleteEvent(PurchaseCompleteEvent purchaseCompleteEvent1, PurchaseCompleteEvent purchaseCompleteEvent2, String message) {
		assertObjectExists("PurchaseCompleteEvent1", purchaseCompleteEvent1);
		assertObjectExists("PurchaseCompleteEvent2", purchaseCompleteEvent2);
		assertSameShipment(purchaseCompleteEvent1.getShipment(), purchaseCompleteEvent2.getShipment(), message);
		assertSameInvoice(purchaseCompleteEvent1.getInvoice(), purchaseCompleteEvent2.getInvoice(), message);
	}
	
	public static void assertSamePurchaseCompleteEvent(Collection<PurchaseCompleteEvent> purchaseCompleteEventList1, Collection<PurchaseCompleteEvent> purchaseCompleteEventList2) {
		assertSamePurchaseCompleteEvent(purchaseCompleteEventList1, purchaseCompleteEventList2, "");
	}
	
	public static void assertSamePurchaseCompleteEvent(Collection<PurchaseCompleteEvent> purchaseCompleteEventList1, Collection<PurchaseCompleteEvent> purchaseCompleteEventList2, String message) {
		Assert.notNull(purchaseCompleteEventList1, "PurchaseCompleteEvent list1 must be specified");
		Assert.notNull(purchaseCompleteEventList2, "PurchaseCompleteEvent list2 must be specified");
		Assert.equals(purchaseCompleteEventList1.size(), purchaseCompleteEventList2.size(), "PurchaseCompleteEvent count not equal");
		Collection<PurchaseCompleteEvent> sortedRecords1 = EventUtil.sortRecords(purchaseCompleteEventList1);
		Collection<PurchaseCompleteEvent> sortedRecords2 = EventUtil.sortRecords(purchaseCompleteEventList2);
		Iterator<PurchaseCompleteEvent> list1Iterator = sortedRecords1.iterator();
		Iterator<PurchaseCompleteEvent> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			PurchaseCompleteEvent purchaseCompleteEvent1 = list1Iterator.next();
			PurchaseCompleteEvent purchaseCompleteEvent2 = list2Iterator.next();
			assertSamePurchaseCompleteEvent(purchaseCompleteEvent1, purchaseCompleteEvent2, message);
		}
	}
	
	public static ShipmentCompleteEvent createEmpty_ShipmentCompleteEvent() {
		ShipmentCompleteEvent shipmentCompleteEvent = new ShipmentCompleteEvent();
		shipmentCompleteEventCount++;
		return shipmentCompleteEvent;
	}
	
	public static ShipmentCompleteEvent create_ShipmentCompleteEvent() {
		ShipmentCompleteEvent shipmentCompleteEvent = create_ShipmentCompleteEvent(1);
		return shipmentCompleteEvent;
	}
	
	public static ShipmentCompleteEvent create_ShipmentCompleteEvent(long index) {
		ShipmentCompleteEvent shipmentCompleteEvent = createEmpty_ShipmentCompleteEvent();
		long value = createValue(index, shipmentCompleteEventCount);
		shipmentCompleteEvent.setShipment(create_Shipment(value));
		shipmentCompleteEvent.setInvoice(create_Invoice(value));
		return shipmentCompleteEvent;
	}
	
	public static List<ShipmentCompleteEvent> createEmptyList_ShipmentCompleteEvent() {
		return new ArrayList<ShipmentCompleteEvent>();
	}
	
	public static List<ShipmentCompleteEvent> createList_ShipmentCompleteEvent() {
		return createList_ShipmentCompleteEvent(2);
	}
	
	public static List<ShipmentCompleteEvent> createList_ShipmentCompleteEvent(int size) {
		return createList_ShipmentCompleteEvent(1, size);
	}
	
	public static List<ShipmentCompleteEvent> createList_ShipmentCompleteEvent(long index, int size) {
		List<ShipmentCompleteEvent> shipmentCompleteEventList = createEmptyList_ShipmentCompleteEvent();
		long limit = index + size;
		for (; index < limit; index++)
			shipmentCompleteEventList.add(create_ShipmentCompleteEvent(index));
		return shipmentCompleteEventList;
	}
	
	public static Set<ShipmentCompleteEvent> createEmptySet_ShipmentCompleteEvent() {
		return new HashSet<ShipmentCompleteEvent>();
	}
	
	public static Set<ShipmentCompleteEvent> createSet_ShipmentCompleteEvent() {
		return createSet_ShipmentCompleteEvent(2);
	}
	
	public static Set<ShipmentCompleteEvent> createSet_ShipmentCompleteEvent(int size) {
		return createSet_ShipmentCompleteEvent(1, size);
	}
	
	public static Set<ShipmentCompleteEvent> createSet_ShipmentCompleteEvent(long index, int size) {
		Set<ShipmentCompleteEvent> shipmentCompleteEventSet = createEmptySet_ShipmentCompleteEvent();
		long limit = index + size;
		for (; index < limit; index++)
			shipmentCompleteEventSet.add(create_ShipmentCompleteEvent(index));
		return shipmentCompleteEventSet;
	}
	
	public static void assertShipmentCompleteEventExists(Collection<ShipmentCompleteEvent> shipmentCompleteEventList, ShipmentCompleteEvent shipmentCompleteEvent) {
		Assert.notNull(shipmentCompleteEventList, "ShipmentCompleteEvent list must be specified");
		Assert.notNull(shipmentCompleteEvent, "ShipmentCompleteEvent record must be specified");
		Iterator<ShipmentCompleteEvent> iterator = shipmentCompleteEventList.iterator();
		while (iterator.hasNext()) {
			ShipmentCompleteEvent shipmentCompleteEvent1 = iterator.next();
			if (shipmentCompleteEvent1.equals(shipmentCompleteEvent)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - ShipmentCompleteEvent should exist: "+shipmentCompleteEvent);
	}
	
	public static void assertShipmentCompleteEventCorrect(ShipmentCompleteEvent shipmentCompleteEvent) {
		assertShipmentCorrect(shipmentCompleteEvent.getShipment());
		assertInvoiceCorrect(shipmentCompleteEvent.getInvoice());
	}
	
	public static void assertShipmentCompleteEventCorrect(Collection<ShipmentCompleteEvent> shipmentCompleteEventList) {
		Assert.isTrue(shipmentCompleteEventList.size() == 2, "ShipmentCompleteEvent count not correct");
		Iterator<ShipmentCompleteEvent> iterator = shipmentCompleteEventList.iterator();
		while (iterator.hasNext()) {
			ShipmentCompleteEvent shipmentCompleteEvent = iterator.next();
			assertShipmentCompleteEventCorrect(shipmentCompleteEvent);
		}
	}
	
	public static void assertSameShipmentCompleteEvent(ShipmentCompleteEvent shipmentCompleteEvent1, ShipmentCompleteEvent shipmentCompleteEvent2) {
		assertSameShipmentCompleteEvent(shipmentCompleteEvent1, shipmentCompleteEvent2, "");
	}
	
	public static void assertSameShipmentCompleteEvent(ShipmentCompleteEvent shipmentCompleteEvent1, ShipmentCompleteEvent shipmentCompleteEvent2, String message) {
		assertObjectExists("ShipmentCompleteEvent1", shipmentCompleteEvent1);
		assertObjectExists("ShipmentCompleteEvent2", shipmentCompleteEvent2);
		assertSameShipment(shipmentCompleteEvent1.getShipment(), shipmentCompleteEvent2.getShipment(), message);
		assertSameInvoice(shipmentCompleteEvent1.getInvoice(), shipmentCompleteEvent2.getInvoice(), message);
	}
	
	public static void assertSameShipmentCompleteEvent(Collection<ShipmentCompleteEvent> shipmentCompleteEventList1, Collection<ShipmentCompleteEvent> shipmentCompleteEventList2) {
		assertSameShipmentCompleteEvent(shipmentCompleteEventList1, shipmentCompleteEventList2, "");
	}
	
	public static void assertSameShipmentCompleteEvent(Collection<ShipmentCompleteEvent> shipmentCompleteEventList1, Collection<ShipmentCompleteEvent> shipmentCompleteEventList2, String message) {
		Assert.notNull(shipmentCompleteEventList1, "ShipmentCompleteEvent list1 must be specified");
		Assert.notNull(shipmentCompleteEventList2, "ShipmentCompleteEvent list2 must be specified");
		Assert.equals(shipmentCompleteEventList1.size(), shipmentCompleteEventList2.size(), "ShipmentCompleteEvent count not equal");
		Collection<ShipmentCompleteEvent> sortedRecords1 = EventUtil.sortRecords(shipmentCompleteEventList1);
		Collection<ShipmentCompleteEvent> sortedRecords2 = EventUtil.sortRecords(shipmentCompleteEventList2);
		Iterator<ShipmentCompleteEvent> list1Iterator = sortedRecords1.iterator();
		Iterator<ShipmentCompleteEvent> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			ShipmentCompleteEvent shipmentCompleteEvent1 = list1Iterator.next();
			ShipmentCompleteEvent shipmentCompleteEvent2 = list2Iterator.next();
			assertSameShipmentCompleteEvent(shipmentCompleteEvent1, shipmentCompleteEvent2, message);
		}
	}
	
	public static ShipmentConfirmedEvent createEmpty_ShipmentConfirmedEvent() {
		ShipmentConfirmedEvent shipmentConfirmedEvent = new ShipmentConfirmedEvent();
		shipmentConfirmedEventCount++;
		return shipmentConfirmedEvent;
	}
	
	public static ShipmentConfirmedEvent create_ShipmentConfirmedEvent() {
		ShipmentConfirmedEvent shipmentConfirmedEvent = create_ShipmentConfirmedEvent(1);
		return shipmentConfirmedEvent;
	}
	
	public static ShipmentConfirmedEvent create_ShipmentConfirmedEvent(long index) {
		ShipmentConfirmedEvent shipmentConfirmedEvent = createEmpty_ShipmentConfirmedEvent();
		long value = createValue(index, shipmentConfirmedEventCount);
		shipmentConfirmedEvent.setShipment(create_Shipment(value));
		return shipmentConfirmedEvent;
	}
	
	public static List<ShipmentConfirmedEvent> createEmptyList_ShipmentConfirmedEvent() {
		return new ArrayList<ShipmentConfirmedEvent>();
	}
	
	public static List<ShipmentConfirmedEvent> createList_ShipmentConfirmedEvent() {
		return createList_ShipmentConfirmedEvent(2);
	}
	
	public static List<ShipmentConfirmedEvent> createList_ShipmentConfirmedEvent(int size) {
		return createList_ShipmentConfirmedEvent(1, size);
	}
	
	public static List<ShipmentConfirmedEvent> createList_ShipmentConfirmedEvent(long index, int size) {
		List<ShipmentConfirmedEvent> shipmentConfirmedEventList = createEmptyList_ShipmentConfirmedEvent();
		long limit = index + size;
		for (; index < limit; index++)
			shipmentConfirmedEventList.add(create_ShipmentConfirmedEvent(index));
		return shipmentConfirmedEventList;
	}

	public static Set<ShipmentConfirmedEvent> createEmptySet_ShipmentConfirmedEvent() {
		return new HashSet<ShipmentConfirmedEvent>();
	}
	
	public static Set<ShipmentConfirmedEvent> createSet_ShipmentConfirmedEvent() {
		return createSet_ShipmentConfirmedEvent(2);
	}
	
	public static Set<ShipmentConfirmedEvent> createSet_ShipmentConfirmedEvent(int size) {
		return createSet_ShipmentConfirmedEvent(1, size);
	}
	
	public static Set<ShipmentConfirmedEvent> createSet_ShipmentConfirmedEvent(long index, int size) {
		Set<ShipmentConfirmedEvent> shipmentConfirmedEventSet = createEmptySet_ShipmentConfirmedEvent();
		long limit = index + size;
		for (; index < limit; index++)
			shipmentConfirmedEventSet.add(create_ShipmentConfirmedEvent(index));
		return shipmentConfirmedEventSet;
	}
	
	public static void assertShipmentConfirmedEventExists(Collection<ShipmentConfirmedEvent> shipmentConfirmedEventList, ShipmentConfirmedEvent shipmentConfirmedEvent) {
		Assert.notNull(shipmentConfirmedEventList, "ShipmentConfirmedEvent list must be specified");
		Assert.notNull(shipmentConfirmedEvent, "ShipmentConfirmedEvent record must be specified");
		Iterator<ShipmentConfirmedEvent> iterator = shipmentConfirmedEventList.iterator();
		while (iterator.hasNext()) {
			ShipmentConfirmedEvent shipmentConfirmedEvent1 = iterator.next();
			if (shipmentConfirmedEvent1.equals(shipmentConfirmedEvent)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - ShipmentConfirmedEvent should exist: "+shipmentConfirmedEvent);
	}
	
	public static void assertShipmentConfirmedEventCorrect(ShipmentConfirmedEvent shipmentConfirmedEvent) {
		assertShipmentCorrect(shipmentConfirmedEvent.getShipment());
	}
	
	public static void assertShipmentConfirmedEventCorrect(Collection<ShipmentConfirmedEvent> shipmentConfirmedEventList) {
		Assert.isTrue(shipmentConfirmedEventList.size() == 2, "ShipmentConfirmedEvent count not correct");
		Iterator<ShipmentConfirmedEvent> iterator = shipmentConfirmedEventList.iterator();
		while (iterator.hasNext()) {
			ShipmentConfirmedEvent shipmentConfirmedEvent = iterator.next();
			assertShipmentConfirmedEventCorrect(shipmentConfirmedEvent);
		}
	}
	
	public static void assertSameShipmentConfirmedEvent(ShipmentConfirmedEvent shipmentConfirmedEvent1, ShipmentConfirmedEvent shipmentConfirmedEvent2) {
		assertSameShipmentConfirmedEvent(shipmentConfirmedEvent1, shipmentConfirmedEvent2, "");
	}
	
	public static void assertSameShipmentConfirmedEvent(ShipmentConfirmedEvent shipmentConfirmedEvent1, ShipmentConfirmedEvent shipmentConfirmedEvent2, String message) {
		assertObjectExists("ShipmentConfirmedEvent1", shipmentConfirmedEvent1);
		assertObjectExists("ShipmentConfirmedEvent2", shipmentConfirmedEvent2);
		assertSameShipment(shipmentConfirmedEvent1.getShipment(), shipmentConfirmedEvent2.getShipment(), message);
	}
	
	public static void assertSameShipmentConfirmedEvent(Collection<ShipmentConfirmedEvent> shipmentConfirmedEventList1, Collection<ShipmentConfirmedEvent> shipmentConfirmedEventList2) {
		assertSameShipmentConfirmedEvent(shipmentConfirmedEventList1, shipmentConfirmedEventList2, "");
	}
	
	public static void assertSameShipmentConfirmedEvent(Collection<ShipmentConfirmedEvent> shipmentConfirmedEventList1, Collection<ShipmentConfirmedEvent> shipmentConfirmedEventList2, String message) {
		Assert.notNull(shipmentConfirmedEventList1, "ShipmentConfirmedEvent list1 must be specified");
		Assert.notNull(shipmentConfirmedEventList2, "ShipmentConfirmedEvent list2 must be specified");
		Assert.equals(shipmentConfirmedEventList1.size(), shipmentConfirmedEventList2.size(), "ShipmentConfirmedEvent count not equal");
		Collection<ShipmentConfirmedEvent> sortedRecords1 = EventUtil.sortRecords(shipmentConfirmedEventList1);
		Collection<ShipmentConfirmedEvent> sortedRecords2 = EventUtil.sortRecords(shipmentConfirmedEventList2);
		Iterator<ShipmentConfirmedEvent> list1Iterator = sortedRecords1.iterator();
		Iterator<ShipmentConfirmedEvent> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			ShipmentConfirmedEvent shipmentConfirmedEvent1 = list1Iterator.next();
			ShipmentConfirmedEvent shipmentConfirmedEvent2 = list2Iterator.next();
			assertSameShipmentConfirmedEvent(shipmentConfirmedEvent1, shipmentConfirmedEvent2, message);
		}
	}
	
	public static ShipmentScheduledEvent createEmpty_ShipmentScheduledEvent() {
		ShipmentScheduledEvent shipmentScheduledEvent = new ShipmentScheduledEvent();
		shipmentScheduledEventCount++;
		return shipmentScheduledEvent;
	}
	
	public static ShipmentScheduledEvent create_ShipmentScheduledEvent() {
		ShipmentScheduledEvent shipmentScheduledEvent = create_ShipmentScheduledEvent(1);
		return shipmentScheduledEvent;
	}
	
	public static ShipmentScheduledEvent create_ShipmentScheduledEvent(long index) {
		ShipmentScheduledEvent shipmentScheduledEvent = createEmpty_ShipmentScheduledEvent();
		long value = createValue(index, shipmentScheduledEventCount);
		shipmentScheduledEvent.setShipment(create_Shipment(value));
		return shipmentScheduledEvent;
	}
	
	public static List<ShipmentScheduledEvent> createEmptyList_ShipmentScheduledEvent() {
		return new ArrayList<ShipmentScheduledEvent>();
	}
	
	public static List<ShipmentScheduledEvent> createList_ShipmentScheduledEvent() {
		return createList_ShipmentScheduledEvent(2);
	}
	
	public static List<ShipmentScheduledEvent> createList_ShipmentScheduledEvent(int size) {
		return createList_ShipmentScheduledEvent(1, size);
	}
	
	public static List<ShipmentScheduledEvent> createList_ShipmentScheduledEvent(long index, int size) {
		List<ShipmentScheduledEvent> shipmentScheduledEventList = createEmptyList_ShipmentScheduledEvent();
		long limit = index + size;
		for (; index < limit; index++)
			shipmentScheduledEventList.add(create_ShipmentScheduledEvent(index));
		return shipmentScheduledEventList;
	}
	
	public static Set<ShipmentScheduledEvent> createEmptySet_ShipmentScheduledEvent() {
		return new HashSet<ShipmentScheduledEvent>();
	}
	
	public static Set<ShipmentScheduledEvent> createSet_ShipmentScheduledEvent() {
		return createSet_ShipmentScheduledEvent(2);
	}
	
	public static Set<ShipmentScheduledEvent> createSet_ShipmentScheduledEvent(int size) {
		return createSet_ShipmentScheduledEvent(1, size);
	}
	
	public static Set<ShipmentScheduledEvent> createSet_ShipmentScheduledEvent(long index, int size) {
		Set<ShipmentScheduledEvent> shipmentScheduledEventSet = createEmptySet_ShipmentScheduledEvent();
		long limit = index + size;
		for (; index < limit; index++)
			shipmentScheduledEventSet.add(create_ShipmentScheduledEvent(index));
		return shipmentScheduledEventSet;
	}
	
	public static void assertShipmentScheduledEventExists(Collection<ShipmentScheduledEvent> shipmentScheduledEventList, ShipmentScheduledEvent shipmentScheduledEvent) {
		Assert.notNull(shipmentScheduledEventList, "ShipmentScheduledEvent list must be specified");
		Assert.notNull(shipmentScheduledEvent, "ShipmentScheduledEvent record must be specified");
		Iterator<ShipmentScheduledEvent> iterator = shipmentScheduledEventList.iterator();
		while (iterator.hasNext()) {
			ShipmentScheduledEvent shipmentScheduledEvent1 = iterator.next();
			if (shipmentScheduledEvent1.equals(shipmentScheduledEvent)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - ShipmentScheduledEvent should exist: "+shipmentScheduledEvent);
	}
	
	public static void assertShipmentScheduledEventCorrect(ShipmentScheduledEvent shipmentScheduledEvent) {
		assertShipmentCorrect(shipmentScheduledEvent.getShipment());
	}
	
	public static void assertShipmentScheduledEventCorrect(Collection<ShipmentScheduledEvent> shipmentScheduledEventList) {
		Assert.isTrue(shipmentScheduledEventList.size() == 2, "ShipmentScheduledEvent count not correct");
		Iterator<ShipmentScheduledEvent> iterator = shipmentScheduledEventList.iterator();
		while (iterator.hasNext()) {
			ShipmentScheduledEvent shipmentScheduledEvent = iterator.next();
			assertShipmentScheduledEventCorrect(shipmentScheduledEvent);
		}
	}
	
	public static void assertSameShipmentScheduledEvent(ShipmentScheduledEvent shipmentScheduledEvent1, ShipmentScheduledEvent shipmentScheduledEvent2) {
		assertSameShipmentScheduledEvent(shipmentScheduledEvent1, shipmentScheduledEvent2, "");
	}
	
	public static void assertSameShipmentScheduledEvent(ShipmentScheduledEvent shipmentScheduledEvent1, ShipmentScheduledEvent shipmentScheduledEvent2, String message) {
		assertObjectExists("ShipmentScheduledEvent1", shipmentScheduledEvent1);
		assertObjectExists("ShipmentScheduledEvent2", shipmentScheduledEvent2);
		assertSameShipment(shipmentScheduledEvent1.getShipment(), shipmentScheduledEvent2.getShipment(), message);
	}
	
	public static void assertSameShipmentScheduledEvent(Collection<ShipmentScheduledEvent> shipmentScheduledEventList1, Collection<ShipmentScheduledEvent> shipmentScheduledEventList2) {
		assertSameShipmentScheduledEvent(shipmentScheduledEventList1, shipmentScheduledEventList2, "");
	}
	
	public static void assertSameShipmentScheduledEvent(Collection<ShipmentScheduledEvent> shipmentScheduledEventList1, Collection<ShipmentScheduledEvent> shipmentScheduledEventList2, String message) {
		Assert.notNull(shipmentScheduledEventList1, "ShipmentScheduledEvent list1 must be specified");
		Assert.notNull(shipmentScheduledEventList2, "ShipmentScheduledEvent list2 must be specified");
		Assert.equals(shipmentScheduledEventList1.size(), shipmentScheduledEventList2.size(), "ShipmentScheduledEvent count not equal");
		Collection<ShipmentScheduledEvent> sortedRecords1 = EventUtil.sortRecords(shipmentScheduledEventList1);
		Collection<ShipmentScheduledEvent> sortedRecords2 = EventUtil.sortRecords(shipmentScheduledEventList2);
		Iterator<ShipmentScheduledEvent> list1Iterator = sortedRecords1.iterator();
		Iterator<ShipmentScheduledEvent> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			ShipmentScheduledEvent shipmentScheduledEvent1 = list1Iterator.next();
			ShipmentScheduledEvent shipmentScheduledEvent2 = list2Iterator.next();
			assertSameShipmentScheduledEvent(shipmentScheduledEvent1, shipmentScheduledEvent2, message);
		}
	}
	
	public static OrderRequestMessage createEmpty_OrderRequestMessage() {
		OrderRequestMessage orderRequestMessage = new OrderRequestMessage();
		orderRequestMessageCount++;
		return orderRequestMessage;
	}
	
	public static OrderRequestMessage create_OrderRequestMessage() {
		OrderRequestMessage orderRequestMessage = create_OrderRequestMessage(false, false);
		return orderRequestMessage;
	}
	
	public static OrderRequestMessage create_OrderRequestMessage(long index) {
		OrderRequestMessage orderRequestMessage = create_OrderRequestMessage(false, false, index);
		return orderRequestMessage;
	}
	
	public static OrderRequestMessage create_OrderRequestMessage(boolean cancel, boolean undo) {
		OrderRequestMessage orderRequestMessage = create_OrderRequestMessage(cancel, undo, 1);
		return orderRequestMessage;
	}
	
	public static OrderRequestMessage create_OrderRequestMessage(boolean cancel, boolean undo, long index) {
		OrderRequestMessage orderRequestMessage = createEmpty_OrderRequestMessage();
		long value = createValue(index, orderRequestMessageCount);
		orderRequestMessage.setOrder(create_Order(value));
		orderRequestMessage.setName(CommonFixture.create_PersonName(value));
		orderRequestMessage.setAddress(CommonFixture.create_StreetAddress(value));
		orderRequestMessage.setPayment(create_Payment(value));
		orderRequestMessage.setCancelRequest(cancel);
		orderRequestMessage.setUndoRequest(undo);
		return orderRequestMessage;
	}
	
	public static List<OrderRequestMessage> createEmptyList_OrderRequestMessage() {
		return new ArrayList<OrderRequestMessage>();
	}
	
	public static List<OrderRequestMessage> createList_OrderRequestMessage() {
		return createList_OrderRequestMessage(2);
	}
	
	public static List<OrderRequestMessage> createList_OrderRequestMessage(int size) {
		return createList_OrderRequestMessage(1, size);
	}
	
	public static List<OrderRequestMessage> createList_OrderRequestMessage(long index, int size) {
		List<OrderRequestMessage> orderRequestMessageList = createEmptyList_OrderRequestMessage();
		long limit = index + size;
		for (; index < limit; index++)
			orderRequestMessageList.add(create_OrderRequestMessage(index));
		return orderRequestMessageList;
	}
	
	public static Set<OrderRequestMessage> createEmptySet_OrderRequestMessage() {
		return new HashSet<OrderRequestMessage>();
	}
	
	public static Set<OrderRequestMessage> createSet_OrderRequestMessage() {
		return createSet_OrderRequestMessage(2);
	}
	
	public static Set<OrderRequestMessage> createSet_OrderRequestMessage(int size) {
		return createSet_OrderRequestMessage(1, size);
	}
	
	public static Set<OrderRequestMessage> createSet_OrderRequestMessage(long index, int size) {
		Set<OrderRequestMessage> orderRequestMessageSet = createEmptySet_OrderRequestMessage();
		long limit = index + size;
		for (; index < limit; index++)
			orderRequestMessageSet.add(create_OrderRequestMessage(index));
		return orderRequestMessageSet;
	}
	
	public static void assertOrderRequestMessageExists(Collection<OrderRequestMessage> orderRequestMessageList, OrderRequestMessage orderRequestMessage) {
		Assert.notNull(orderRequestMessageList, "OrderRequestMessage list must be specified");
		Assert.notNull(orderRequestMessage, "OrderRequestMessage record must be specified");
		Iterator<OrderRequestMessage> iterator = orderRequestMessageList.iterator();
		while (iterator.hasNext()) {
			OrderRequestMessage orderRequestMessage1 = iterator.next();
			if (orderRequestMessage1.equals(orderRequestMessage)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - OrderRequestMessage should exist: "+orderRequestMessage);
	}
	
	public static void assertOrderRequestMessageCorrect(OrderRequestMessage orderRequestMessage) {
		assertOrderCorrect(orderRequestMessage.getOrder());
		CommonFixture.assertPersonNameCorrect(orderRequestMessage.getName());
		CommonFixture.assertStreetAddressCorrect(orderRequestMessage.getAddress());
		assertPaymentCorrect(orderRequestMessage.getPayment());
	}
	
	public static void assertOrderRequestMessageCorrect(Collection<OrderRequestMessage> orderRequestMessageList) {
		Assert.isTrue(orderRequestMessageList.size() == 2, "OrderRequestMessage count not correct");
		Iterator<OrderRequestMessage> iterator = orderRequestMessageList.iterator();
		while (iterator.hasNext()) {
			OrderRequestMessage orderRequestMessage = iterator.next();
			assertOrderRequestMessageCorrect(orderRequestMessage);
		}
	}
	
	public static void assertSameOrderRequestMessage(OrderRequestMessage orderRequestMessage1, OrderRequestMessage orderRequestMessage2) {
		assertSameOrderRequestMessage(orderRequestMessage1, orderRequestMessage2, "");
	}
	
	public static void assertSameOrderRequestMessage(OrderRequestMessage orderRequestMessage1, OrderRequestMessage orderRequestMessage2, String message) {
		assertObjectExists("OrderRequestMessage1", orderRequestMessage1);
		assertObjectExists("OrderRequestMessage2", orderRequestMessage2);
		assertSameOrder(orderRequestMessage1.getOrder(), orderRequestMessage2.getOrder(), message);
		CommonFixture.assertSamePersonName(orderRequestMessage1.getName(), orderRequestMessage2.getName(), message);
		CommonFixture.assertSameStreetAddress(orderRequestMessage1.getAddress(), orderRequestMessage2.getAddress(), message);
		assertSamePayment(orderRequestMessage1.getPayment(), orderRequestMessage2.getPayment(), message);
	}
	
	public static void assertSameOrderRequestMessage(Collection<OrderRequestMessage> orderRequestMessageList1, Collection<OrderRequestMessage> orderRequestMessageList2) {
		assertSameOrderRequestMessage(orderRequestMessageList1, orderRequestMessageList2, "");
	}
	
	public static void assertSameOrderRequestMessage(Collection<OrderRequestMessage> orderRequestMessageList1, Collection<OrderRequestMessage> orderRequestMessageList2, String message) {
		Assert.notNull(orderRequestMessageList1, "OrderRequestMessage list1 must be specified");
		Assert.notNull(orderRequestMessageList2, "OrderRequestMessage list2 must be specified");
		Assert.equals(orderRequestMessageList1.size(), orderRequestMessageList2.size(), "OrderRequestMessage count not equal");
		Collection<OrderRequestMessage> sortedRecords1 = MessageUtil.sortRecords(orderRequestMessageList1);
		Collection<OrderRequestMessage> sortedRecords2 = MessageUtil.sortRecords(orderRequestMessageList2);
		Iterator<OrderRequestMessage> list1Iterator = sortedRecords1.iterator();
		Iterator<OrderRequestMessage> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			OrderRequestMessage orderRequestMessage1 = list1Iterator.next();
			OrderRequestMessage orderRequestMessage2 = list2Iterator.next();
			assertSameOrderRequestMessage(orderRequestMessage1, orderRequestMessage2, message);
		}
	}
	
	public static OrderResponseMessage createEmpty_OrderResponseMessage() {
		OrderResponseMessage orderResponseMessage = new OrderResponseMessage();
		orderResponseMessageCount++;
		return orderResponseMessage;
	}
	
	public static OrderResponseMessage create_OrderResponseMessage() {
		OrderResponseMessage orderResponseMessage = create_OrderResponseMessage(false, false);
		return orderResponseMessage;
	}
	
	public static OrderResponseMessage create_OrderResponseMessage(long index) {
		OrderResponseMessage orderResponseMessage = create_OrderResponseMessage(false, false, index);
		return orderResponseMessage;
	}
	
	public static OrderResponseMessage create_OrderResponseMessage(boolean cancel, boolean undo) {
		OrderResponseMessage orderResponseMessage = create_OrderResponseMessage(cancel, undo, 1);
		return orderResponseMessage;
	}
	
	public static OrderResponseMessage create_OrderResponseMessage(boolean cancel, boolean undo, long index) {
		OrderResponseMessage orderResponseMessage = createEmpty_OrderResponseMessage();
		long value = createValue(index, orderResponseMessageCount);
		orderResponseMessage.setOrder(create_Order(value));
		orderResponseMessage.getAvailableBooks().addAll(createList_Book(2));
		orderResponseMessage.getUnavailableBooks().addAll(createList_Book(2));
		orderResponseMessage.setCancelRequest(cancel);
		orderResponseMessage.setUndoRequest(undo);
		return orderResponseMessage;
	}
	
	public static List<OrderResponseMessage> createEmptyList_OrderResponseMessage() {
		return new ArrayList<OrderResponseMessage>();
	}
	
	public static List<OrderResponseMessage> createList_OrderResponseMessage() {
		return createList_OrderResponseMessage(2);
	}
	
	public static List<OrderResponseMessage> createList_OrderResponseMessage(int size) {
		return createList_OrderResponseMessage(1, size);
	}
	
	public static List<OrderResponseMessage> createList_OrderResponseMessage(long index, int size) {
		List<OrderResponseMessage> orderResponseMessageList = createEmptyList_OrderResponseMessage();
		long limit = index + size;
		for (; index < limit; index++)
			orderResponseMessageList.add(create_OrderResponseMessage(index));
		return orderResponseMessageList;
	}
	
	public static Set<OrderResponseMessage> createEmptySet_OrderResponseMessage() {
		return new HashSet<OrderResponseMessage>();
	}
	
	public static Set<OrderResponseMessage> createSet_OrderResponseMessage() {
		return createSet_OrderResponseMessage(2);
	}
	
	public static Set<OrderResponseMessage> createSet_OrderResponseMessage(int size) {
		return createSet_OrderResponseMessage(1, size);
	}
	
	public static Set<OrderResponseMessage> createSet_OrderResponseMessage(long index, int size) {
		Set<OrderResponseMessage> orderResponseMessageSet = createEmptySet_OrderResponseMessage();
		long limit = index + size;
		for (; index < limit; index++)
			orderResponseMessageSet.add(create_OrderResponseMessage(index));
		return orderResponseMessageSet;
	}
	
	public static void assertOrderResponseMessageExists(Collection<OrderResponseMessage> orderResponseMessageList, OrderResponseMessage orderResponseMessage) {
		Assert.notNull(orderResponseMessageList, "OrderResponseMessage list must be specified");
		Assert.notNull(orderResponseMessage, "OrderResponseMessage record must be specified");
		Iterator<OrderResponseMessage> iterator = orderResponseMessageList.iterator();
		while (iterator.hasNext()) {
			OrderResponseMessage orderResponseMessage1 = iterator.next();
			if (orderResponseMessage1.equals(orderResponseMessage)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - OrderResponseMessage should exist: "+orderResponseMessage);
	}
	
	public static void assertOrderResponseMessageCorrect(OrderResponseMessage orderResponseMessage) {
		assertOrderCorrect(orderResponseMessage.getOrder());
		assertBookCorrect(orderResponseMessage.getAvailableBooks());
		assertBookCorrect(orderResponseMessage.getUnavailableBooks());
	}
	
	public static void assertOrderResponseMessageCorrect(Collection<OrderResponseMessage> orderResponseMessageList) {
		Assert.isTrue(orderResponseMessageList.size() == 2, "OrderResponseMessage count not correct");
		Iterator<OrderResponseMessage> iterator = orderResponseMessageList.iterator();
		while (iterator.hasNext()) {
			OrderResponseMessage orderResponseMessage = iterator.next();
			assertOrderResponseMessageCorrect(orderResponseMessage);
		}
	}
	
	public static void assertSameOrderResponseMessage(OrderResponseMessage orderResponseMessage1, OrderResponseMessage orderResponseMessage2) {
		assertSameOrderResponseMessage(orderResponseMessage1, orderResponseMessage2, "");
	}
	
	public static void assertSameOrderResponseMessage(OrderResponseMessage orderResponseMessage1, OrderResponseMessage orderResponseMessage2, String message) {
		assertObjectExists("OrderResponseMessage1", orderResponseMessage1);
		assertObjectExists("OrderResponseMessage2", orderResponseMessage2);
		assertSameOrder(orderResponseMessage1.getOrder(), orderResponseMessage2.getOrder(), message);
		assertSameBook(orderResponseMessage1.getAvailableBooks(), orderResponseMessage2.getAvailableBooks(), message);
		assertSameBook(orderResponseMessage1.getUnavailableBooks(), orderResponseMessage2.getUnavailableBooks(), message);
	}
	
	public static void assertSameOrderResponseMessage(Collection<OrderResponseMessage> orderResponseMessageList1, Collection<OrderResponseMessage> orderResponseMessageList2) {
		assertSameOrderResponseMessage(orderResponseMessageList1, orderResponseMessageList2, "");
	}
	
	public static void assertSameOrderResponseMessage(Collection<OrderResponseMessage> orderResponseMessageList1, Collection<OrderResponseMessage> orderResponseMessageList2, String message) {
		Assert.notNull(orderResponseMessageList1, "OrderResponseMessage list1 must be specified");
		Assert.notNull(orderResponseMessageList2, "OrderResponseMessage list2 must be specified");
		Assert.equals(orderResponseMessageList1.size(), orderResponseMessageList2.size(), "OrderResponseMessage count not equal");
		Collection<OrderResponseMessage> sortedRecords1 = MessageUtil.sortRecords(orderResponseMessageList1);
		Collection<OrderResponseMessage> sortedRecords2 = MessageUtil.sortRecords(orderResponseMessageList2);
		Iterator<OrderResponseMessage> list1Iterator = sortedRecords1.iterator();
		Iterator<OrderResponseMessage> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			OrderResponseMessage orderResponseMessage1 = list1Iterator.next();
			OrderResponseMessage orderResponseMessage2 = list2Iterator.next();
			assertSameOrderResponseMessage(orderResponseMessage1, orderResponseMessage2, message);
		}
	}
	
	public static OrderAcceptedMessage createEmpty_OrderAcceptedMessage() {
		OrderAcceptedMessage orderAcceptedMessage = new OrderAcceptedMessage();
		orderAcceptedMessageCount++;
		return orderAcceptedMessage;
	}
	
	public static OrderAcceptedMessage create_OrderAcceptedMessage() {
		OrderAcceptedMessage orderAcceptedMessage = create_OrderAcceptedMessage(false, false);
		return orderAcceptedMessage;
	}
	
	public static OrderAcceptedMessage create_OrderAcceptedMessage(long index) {
		OrderAcceptedMessage orderAcceptedMessage = create_OrderAcceptedMessage(false, false, index);
		return orderAcceptedMessage;
	}
	
	public static OrderAcceptedMessage create_OrderAcceptedMessage(boolean cancel, boolean undo) {
		OrderAcceptedMessage orderAcceptedMessage = create_OrderAcceptedMessage(cancel, undo, 1);
		return orderAcceptedMessage;
	}
	
	public static OrderAcceptedMessage create_OrderAcceptedMessage(boolean cancel, boolean undo, long index) {
		OrderAcceptedMessage orderAcceptedMessage = createEmpty_OrderAcceptedMessage();
		long value = createValue(index, orderAcceptedMessageCount);
		orderAcceptedMessage.setOrder(create_Order(value));
		orderAcceptedMessage.setCancelRequest(cancel);
		orderAcceptedMessage.setUndoRequest(undo);
		return orderAcceptedMessage;
	}
	
	public static List<OrderAcceptedMessage> createEmptyList_OrderAcceptedMessage() {
		return new ArrayList<OrderAcceptedMessage>();
	}
	
	public static List<OrderAcceptedMessage> createList_OrderAcceptedMessage() {
		return createList_OrderAcceptedMessage(2);
	}
	
	public static List<OrderAcceptedMessage> createList_OrderAcceptedMessage(int size) {
		return createList_OrderAcceptedMessage(1, size);
	}
	
	public static List<OrderAcceptedMessage> createList_OrderAcceptedMessage(long index, int size) {
		List<OrderAcceptedMessage> orderAcceptedMessageList = createEmptyList_OrderAcceptedMessage();
		long limit = index + size;
		for (; index < limit; index++)
			orderAcceptedMessageList.add(create_OrderAcceptedMessage(index));
		return orderAcceptedMessageList;
	}
	
	public static Set<OrderAcceptedMessage> createEmptySet_OrderAcceptedMessage() {
		return new HashSet<OrderAcceptedMessage>();
	}
	
	public static Set<OrderAcceptedMessage> createSet_OrderAcceptedMessage() {
		return createSet_OrderAcceptedMessage(2);
	}
	
	public static Set<OrderAcceptedMessage> createSet_OrderAcceptedMessage(int size) {
		return createSet_OrderAcceptedMessage(1, size);
	}
	
	public static Set<OrderAcceptedMessage> createSet_OrderAcceptedMessage(long index, int size) {
		Set<OrderAcceptedMessage> orderAcceptedMessageSet = createEmptySet_OrderAcceptedMessage();
		long limit = index + size;
		for (; index < limit; index++)
			orderAcceptedMessageSet.add(create_OrderAcceptedMessage(index));
		return orderAcceptedMessageSet;
	}
	
	public static void assertOrderAcceptedMessageExists(Collection<OrderAcceptedMessage> orderAcceptedMessageList, OrderAcceptedMessage orderAcceptedMessage) {
		Assert.notNull(orderAcceptedMessageList, "OrderAcceptedMessage list must be specified");
		Assert.notNull(orderAcceptedMessage, "OrderAcceptedMessage record must be specified");
		Iterator<OrderAcceptedMessage> iterator = orderAcceptedMessageList.iterator();
		while (iterator.hasNext()) {
			OrderAcceptedMessage orderAcceptedMessage1 = iterator.next();
			if (orderAcceptedMessage1.equals(orderAcceptedMessage)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - OrderAcceptedMessage should exist: "+orderAcceptedMessage);
	}
	
	public static void assertOrderAcceptedMessageCorrect(OrderAcceptedMessage orderAcceptedMessage) {
		assertOrderCorrect(orderAcceptedMessage.getOrder());
	}
	
	public static void assertOrderAcceptedMessageCorrect(Collection<OrderAcceptedMessage> orderAcceptedMessageList) {
		Assert.isTrue(orderAcceptedMessageList.size() == 2, "OrderAcceptedMessage count not correct");
		Iterator<OrderAcceptedMessage> iterator = orderAcceptedMessageList.iterator();
		while (iterator.hasNext()) {
			OrderAcceptedMessage orderAcceptedMessage = iterator.next();
			assertOrderAcceptedMessageCorrect(orderAcceptedMessage);
		}
	}
	
	public static void assertSameOrderAcceptedMessage(OrderAcceptedMessage orderAcceptedMessage1, OrderAcceptedMessage orderAcceptedMessage2) {
		assertSameOrderAcceptedMessage(orderAcceptedMessage1, orderAcceptedMessage2, "");
	}
	
	public static void assertSameOrderAcceptedMessage(OrderAcceptedMessage orderAcceptedMessage1, OrderAcceptedMessage orderAcceptedMessage2, String message) {
		assertObjectExists("OrderAcceptedMessage1", orderAcceptedMessage1);
		assertObjectExists("OrderAcceptedMessage2", orderAcceptedMessage2);
		assertSameOrder(orderAcceptedMessage1.getOrder(), orderAcceptedMessage2.getOrder(), message);
	}
	
	public static void assertSameOrderAcceptedMessage(Collection<OrderAcceptedMessage> orderAcceptedMessageList1, Collection<OrderAcceptedMessage> orderAcceptedMessageList2) {
		assertSameOrderAcceptedMessage(orderAcceptedMessageList1, orderAcceptedMessageList2, "");
	}
	
	public static void assertSameOrderAcceptedMessage(Collection<OrderAcceptedMessage> orderAcceptedMessageList1, Collection<OrderAcceptedMessage> orderAcceptedMessageList2, String message) {
		Assert.notNull(orderAcceptedMessageList1, "OrderAcceptedMessage list1 must be specified");
		Assert.notNull(orderAcceptedMessageList2, "OrderAcceptedMessage list2 must be specified");
		Assert.equals(orderAcceptedMessageList1.size(), orderAcceptedMessageList2.size(), "OrderAcceptedMessage count not equal");
		Collection<OrderAcceptedMessage> sortedRecords1 = MessageUtil.sortRecords(orderAcceptedMessageList1);
		Collection<OrderAcceptedMessage> sortedRecords2 = MessageUtil.sortRecords(orderAcceptedMessageList2);
		Iterator<OrderAcceptedMessage> list1Iterator = sortedRecords1.iterator();
		Iterator<OrderAcceptedMessage> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			OrderAcceptedMessage orderAcceptedMessage1 = list1Iterator.next();
			OrderAcceptedMessage orderAcceptedMessage2 = list2Iterator.next();
			assertSameOrderAcceptedMessage(orderAcceptedMessage1, orderAcceptedMessage2, message);
		}
	}
	
	public static OrderRejectedMessage createEmpty_OrderRejectedMessage() {
		OrderRejectedMessage orderRejectedMessage = new OrderRejectedMessage();
		orderRejectedMessageCount++;
		return orderRejectedMessage;
	}
	
	public static OrderRejectedMessage create_OrderRejectedMessage() {
		OrderRejectedMessage orderRejectedMessage = create_OrderRejectedMessage(false, false);
		return orderRejectedMessage;
	}
	
	public static OrderRejectedMessage create_OrderRejectedMessage(long index) {
		OrderRejectedMessage orderRejectedMessage = create_OrderRejectedMessage(false, false, index);
		return orderRejectedMessage;
	}
	
	public static OrderRejectedMessage create_OrderRejectedMessage(boolean cancel, boolean undo) {
		OrderRejectedMessage orderRejectedMessage = create_OrderRejectedMessage(cancel, undo, 1);
		return orderRejectedMessage;
	}
	
	public static OrderRejectedMessage create_OrderRejectedMessage(boolean cancel, boolean undo, long index) {
		OrderRejectedMessage orderRejectedMessage = createEmpty_OrderRejectedMessage();
		long value = createValue(index, orderRejectedMessageCount);
		orderRejectedMessage.setReason("dummyReason" + value);
		orderRejectedMessage.setOrder(create_Order(value));
		orderRejectedMessage.setCancelRequest(cancel);
		orderRejectedMessage.setUndoRequest(undo);
		return orderRejectedMessage;
	}
	
	public static List<OrderRejectedMessage> createEmptyList_OrderRejectedMessage() {
		return new ArrayList<OrderRejectedMessage>();
	}
	
	public static List<OrderRejectedMessage> createList_OrderRejectedMessage() {
		return createList_OrderRejectedMessage(2);
	}
	
	public static List<OrderRejectedMessage> createList_OrderRejectedMessage(int size) {
		return createList_OrderRejectedMessage(1, size);
	}
	
	public static List<OrderRejectedMessage> createList_OrderRejectedMessage(long index, int size) {
		List<OrderRejectedMessage> orderRejectedMessageList = createEmptyList_OrderRejectedMessage();
		long limit = index + size;
		for (; index < limit; index++)
			orderRejectedMessageList.add(create_OrderRejectedMessage(index));
		return orderRejectedMessageList;
	}
	
	public static Set<OrderRejectedMessage> createEmptySet_OrderRejectedMessage() {
		return new HashSet<OrderRejectedMessage>();
	}
	
	public static Set<OrderRejectedMessage> createSet_OrderRejectedMessage() {
		return createSet_OrderRejectedMessage(2);
	}
	
	public static Set<OrderRejectedMessage> createSet_OrderRejectedMessage(int size) {
		return createSet_OrderRejectedMessage(1, size);
	}
	
	public static Set<OrderRejectedMessage> createSet_OrderRejectedMessage(long index, int size) {
		Set<OrderRejectedMessage> orderRejectedMessageSet = createEmptySet_OrderRejectedMessage();
		long limit = index + size;
		for (; index < limit; index++)
			orderRejectedMessageSet.add(create_OrderRejectedMessage(index));
		return orderRejectedMessageSet;
	}
	
	public static void assertOrderRejectedMessageExists(Collection<OrderRejectedMessage> orderRejectedMessageList, OrderRejectedMessage orderRejectedMessage) {
		Assert.notNull(orderRejectedMessageList, "OrderRejectedMessage list must be specified");
		Assert.notNull(orderRejectedMessage, "OrderRejectedMessage record must be specified");
		Iterator<OrderRejectedMessage> iterator = orderRejectedMessageList.iterator();
		while (iterator.hasNext()) {
			OrderRejectedMessage orderRejectedMessage1 = iterator.next();
			if (orderRejectedMessage1.equals(orderRejectedMessage)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - OrderRejectedMessage should exist: "+orderRejectedMessage);
	}
	
	public static void assertOrderRejectedMessageCorrect(OrderRejectedMessage orderRejectedMessage) {
		assertObjectCorrect("Reason", orderRejectedMessage.getReason());
		assertOrderCorrect(orderRejectedMessage.getOrder());
	}
	
	public static void assertOrderRejectedMessageCorrect(Collection<OrderRejectedMessage> orderRejectedMessageList) {
		Assert.isTrue(orderRejectedMessageList.size() == 2, "OrderRejectedMessage count not correct");
		Iterator<OrderRejectedMessage> iterator = orderRejectedMessageList.iterator();
		while (iterator.hasNext()) {
			OrderRejectedMessage orderRejectedMessage = iterator.next();
			assertOrderRejectedMessageCorrect(orderRejectedMessage);
		}
	}
	
	public static void assertSameOrderRejectedMessage(OrderRejectedMessage orderRejectedMessage1, OrderRejectedMessage orderRejectedMessage2) {
		assertSameOrderRejectedMessage(orderRejectedMessage1, orderRejectedMessage2, "");
	}
	
	public static void assertSameOrderRejectedMessage(OrderRejectedMessage orderRejectedMessage1, OrderRejectedMessage orderRejectedMessage2, String message) {
		assertObjectExists("OrderRejectedMessage1", orderRejectedMessage1);
		assertObjectExists("OrderRejectedMessage2", orderRejectedMessage2);
		assertObjectEquals("Reason", orderRejectedMessage1.getReason(), orderRejectedMessage2.getReason(), message);
		assertSameOrder(orderRejectedMessage1.getOrder(), orderRejectedMessage2.getOrder(), message);
	}
	
	public static void assertSameOrderRejectedMessage(Collection<OrderRejectedMessage> orderRejectedMessageList1, Collection<OrderRejectedMessage> orderRejectedMessageList2) {
		assertSameOrderRejectedMessage(orderRejectedMessageList1, orderRejectedMessageList2, "");
	}
	
	public static void assertSameOrderRejectedMessage(Collection<OrderRejectedMessage> orderRejectedMessageList1, Collection<OrderRejectedMessage> orderRejectedMessageList2, String message) {
		Assert.notNull(orderRejectedMessageList1, "OrderRejectedMessage list1 must be specified");
		Assert.notNull(orderRejectedMessageList2, "OrderRejectedMessage list2 must be specified");
		Assert.equals(orderRejectedMessageList1.size(), orderRejectedMessageList2.size(), "OrderRejectedMessage count not equal");
		Collection<OrderRejectedMessage> sortedRecords1 = MessageUtil.sortRecords(orderRejectedMessageList1);
		Collection<OrderRejectedMessage> sortedRecords2 = MessageUtil.sortRecords(orderRejectedMessageList2);
		Iterator<OrderRejectedMessage> list1Iterator = sortedRecords1.iterator();
		Iterator<OrderRejectedMessage> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			OrderRejectedMessage orderRejectedMessage1 = list1Iterator.next();
			OrderRejectedMessage orderRejectedMessage2 = list2Iterator.next();
			assertSameOrderRejectedMessage(orderRejectedMessage1, orderRejectedMessage2, message);
		}
	}

	public static PurchaseRequestMessage createEmpty_PurchaseRequestMessage() {
		PurchaseRequestMessage purchaseRequestMessage = new PurchaseRequestMessage();
		purchaseRequestMessageCount++;
		return purchaseRequestMessage;
	}
	
	public static PurchaseRequestMessage create_PurchaseRequestMessage() {
		PurchaseRequestMessage purchaseRequestMessage = create_PurchaseRequestMessage(false, false);
		return purchaseRequestMessage;
	}
	
	public static PurchaseRequestMessage create_PurchaseRequestMessage(long index) {
		PurchaseRequestMessage purchaseRequestMessage = create_PurchaseRequestMessage(false, false, index);
		return purchaseRequestMessage;
	}
	
	public static PurchaseRequestMessage create_PurchaseRequestMessage(boolean cancel, boolean undo) {
		PurchaseRequestMessage purchaseRequestMessage = create_PurchaseRequestMessage(cancel, undo, 1);
		return purchaseRequestMessage;
	}
	
	public static PurchaseRequestMessage create_PurchaseRequestMessage(boolean cancel, boolean undo, long index) {
		PurchaseRequestMessage purchaseRequestMessage = createEmpty_PurchaseRequestMessage();
		long value = createValue(index, purchaseRequestMessageCount);
		purchaseRequestMessage.setName(CommonFixture.create_PersonName(value));
		purchaseRequestMessage.setAddress(CommonFixture.create_StreetAddress(value));
		purchaseRequestMessage.setOrder(create_Order(value));
		purchaseRequestMessage.setPayment(create_Payment(value));
		purchaseRequestMessage.setCancelRequest(cancel);
		purchaseRequestMessage.setUndoRequest(undo);
		return purchaseRequestMessage;
	}
	
	public static List<PurchaseRequestMessage> createEmptyList_PurchaseRequestMessage() {
		return new ArrayList<PurchaseRequestMessage>();
	}
	
	public static List<PurchaseRequestMessage> createList_PurchaseRequestMessage() {
		return createList_PurchaseRequestMessage(2);
	}
	
	public static List<PurchaseRequestMessage> createList_PurchaseRequestMessage(int size) {
		return createList_PurchaseRequestMessage(1, size);
	}
	
	public static List<PurchaseRequestMessage> createList_PurchaseRequestMessage(long index, int size) {
		List<PurchaseRequestMessage> purchaseRequestMessageList = createEmptyList_PurchaseRequestMessage();
		long limit = index + size;
		for (; index < limit; index++)
			purchaseRequestMessageList.add(create_PurchaseRequestMessage(index));
		return purchaseRequestMessageList;
	}
	
	public static Set<PurchaseRequestMessage> createEmptySet_PurchaseRequestMessage() {
		return new HashSet<PurchaseRequestMessage>();
	}
	
	public static Set<PurchaseRequestMessage> createSet_PurchaseRequestMessage() {
		return createSet_PurchaseRequestMessage(2);
	}
	
	public static Set<PurchaseRequestMessage> createSet_PurchaseRequestMessage(int size) {
		return createSet_PurchaseRequestMessage(1, size);
	}
	
	public static Set<PurchaseRequestMessage> createSet_PurchaseRequestMessage(long index, int size) {
		Set<PurchaseRequestMessage> purchaseRequestMessageSet = createEmptySet_PurchaseRequestMessage();
		long limit = index + size;
		for (; index < limit; index++)
			purchaseRequestMessageSet.add(create_PurchaseRequestMessage(index));
		return purchaseRequestMessageSet;
	}
	
	public static void assertPurchaseRequestMessageExists(Collection<PurchaseRequestMessage> purchaseRequestMessageList, PurchaseRequestMessage purchaseRequestMessage) {
		Assert.notNull(purchaseRequestMessageList, "PurchaseRequestMessage list must be specified");
		Assert.notNull(purchaseRequestMessage, "PurchaseRequestMessage record must be specified");
		Iterator<PurchaseRequestMessage> iterator = purchaseRequestMessageList.iterator();
		while (iterator.hasNext()) {
			PurchaseRequestMessage purchaseRequestMessage1 = iterator.next();
			if (purchaseRequestMessage1.equals(purchaseRequestMessage)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - PurchaseRequestMessage should exist: "+purchaseRequestMessage);
	}
	
	public static void assertPurchaseRequestMessageCorrect(PurchaseRequestMessage purchaseRequestMessage) {
		CommonFixture.assertPersonNameCorrect(purchaseRequestMessage.getName());
		CommonFixture.assertStreetAddressCorrect(purchaseRequestMessage.getAddress());
		assertOrderCorrect(purchaseRequestMessage.getOrder());
		assertPaymentCorrect(purchaseRequestMessage.getPayment());
	}
	
	public static void assertPurchaseRequestMessageCorrect(Collection<PurchaseRequestMessage> purchaseRequestMessageList) {
		Assert.isTrue(purchaseRequestMessageList.size() == 2, "PurchaseRequestMessage count not correct");
		Iterator<PurchaseRequestMessage> iterator = purchaseRequestMessageList.iterator();
		while (iterator.hasNext()) {
			PurchaseRequestMessage purchaseRequestMessage = iterator.next();
			assertPurchaseRequestMessageCorrect(purchaseRequestMessage);
		}
	}
	
	public static void assertSamePurchaseRequestMessage(PurchaseRequestMessage purchaseRequestMessage1, PurchaseRequestMessage purchaseRequestMessage2) {
		assertSamePurchaseRequestMessage(purchaseRequestMessage1, purchaseRequestMessage2, "");
	}
	
	public static void assertSamePurchaseRequestMessage(PurchaseRequestMessage purchaseRequestMessage1, PurchaseRequestMessage purchaseRequestMessage2, String message) {
		assertObjectExists("PurchaseRequestMessage1", purchaseRequestMessage1);
		assertObjectExists("PurchaseRequestMessage2", purchaseRequestMessage2);
		CommonFixture.assertSamePersonName(purchaseRequestMessage1.getName(), purchaseRequestMessage2.getName(), message);
		CommonFixture.assertSameStreetAddress(purchaseRequestMessage1.getAddress(), purchaseRequestMessage2.getAddress(), message);
		assertSameOrder(purchaseRequestMessage1.getOrder(), purchaseRequestMessage2.getOrder(), message);
		assertSamePayment(purchaseRequestMessage1.getPayment(), purchaseRequestMessage2.getPayment(), message);
	}
	
	public static void assertSamePurchaseRequestMessage(Collection<PurchaseRequestMessage> purchaseRequestMessageList1, Collection<PurchaseRequestMessage> purchaseRequestMessageList2) {
		assertSamePurchaseRequestMessage(purchaseRequestMessageList1, purchaseRequestMessageList2, "");
	}
	
	public static void assertSamePurchaseRequestMessage(Collection<PurchaseRequestMessage> purchaseRequestMessageList1, Collection<PurchaseRequestMessage> purchaseRequestMessageList2, String message) {
		Assert.notNull(purchaseRequestMessageList1, "PurchaseRequestMessage list1 must be specified");
		Assert.notNull(purchaseRequestMessageList2, "PurchaseRequestMessage list2 must be specified");
		Assert.equals(purchaseRequestMessageList1.size(), purchaseRequestMessageList2.size(), "PurchaseRequestMessage count not equal");
		Collection<PurchaseRequestMessage> sortedRecords1 = MessageUtil.sortRecords(purchaseRequestMessageList1);
		Collection<PurchaseRequestMessage> sortedRecords2 = MessageUtil.sortRecords(purchaseRequestMessageList2);
		Iterator<PurchaseRequestMessage> list1Iterator = sortedRecords1.iterator();
		Iterator<PurchaseRequestMessage> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			PurchaseRequestMessage purchaseRequestMessage1 = list1Iterator.next();
			PurchaseRequestMessage purchaseRequestMessage2 = list2Iterator.next();
			assertSamePurchaseRequestMessage(purchaseRequestMessage1, purchaseRequestMessage2, message);
		}
	}
	
	public static PurchaseAcceptedMessage createEmpty_PurchaseAcceptedMessage() {
		PurchaseAcceptedMessage purchaseAcceptedMessage = new PurchaseAcceptedMessage();
		purchaseAcceptedMessageCount++;
		return purchaseAcceptedMessage;
	}
	
	public static PurchaseAcceptedMessage create_PurchaseAcceptedMessage() {
		PurchaseAcceptedMessage purchaseAcceptedMessage = create_PurchaseAcceptedMessage(false, false);
		return purchaseAcceptedMessage;
	}
	
	public static PurchaseAcceptedMessage create_PurchaseAcceptedMessage(long index) {
		PurchaseAcceptedMessage purchaseAcceptedMessage = create_PurchaseAcceptedMessage(false, false, index);
		return purchaseAcceptedMessage;
	}
	
	public static PurchaseAcceptedMessage create_PurchaseAcceptedMessage(boolean cancel, boolean undo) {
		PurchaseAcceptedMessage purchaseAcceptedMessage = create_PurchaseAcceptedMessage(cancel, undo, 1);
		return purchaseAcceptedMessage;
	}
	
	public static PurchaseAcceptedMessage create_PurchaseAcceptedMessage(boolean cancel, boolean undo, long index) {
		PurchaseAcceptedMessage purchaseAcceptedMessage = createEmpty_PurchaseAcceptedMessage();
		long value = createValue(index, purchaseAcceptedMessageCount);
		purchaseAcceptedMessage.setOrder(create_Order(value));
		purchaseAcceptedMessage.setPayment(create_Payment(value));
		purchaseAcceptedMessage.setInvoice(create_Invoice(value));
		purchaseAcceptedMessage.setCancelRequest(cancel);
		purchaseAcceptedMessage.setUndoRequest(undo);
		return purchaseAcceptedMessage;
	}
	
	public static List<PurchaseAcceptedMessage> createEmptyList_PurchaseAcceptedMessage() {
		return new ArrayList<PurchaseAcceptedMessage>();
	}
	
	public static List<PurchaseAcceptedMessage> createList_PurchaseAcceptedMessage() {
		return createList_PurchaseAcceptedMessage(2);
	}
	
	public static List<PurchaseAcceptedMessage> createList_PurchaseAcceptedMessage(int size) {
		return createList_PurchaseAcceptedMessage(1, size);
	}
	
	public static List<PurchaseAcceptedMessage> createList_PurchaseAcceptedMessage(long index, int size) {
		List<PurchaseAcceptedMessage> purchaseAcceptedMessageList = createEmptyList_PurchaseAcceptedMessage();
		long limit = index + size;
		for (; index < limit; index++)
			purchaseAcceptedMessageList.add(create_PurchaseAcceptedMessage(index));
		return purchaseAcceptedMessageList;
	}
	
	public static Set<PurchaseAcceptedMessage> createEmptySet_PurchaseAcceptedMessage() {
		return new HashSet<PurchaseAcceptedMessage>();
	}
	
	public static Set<PurchaseAcceptedMessage> createSet_PurchaseAcceptedMessage() {
		return createSet_PurchaseAcceptedMessage(2);
	}
	
	public static Set<PurchaseAcceptedMessage> createSet_PurchaseAcceptedMessage(int size) {
		return createSet_PurchaseAcceptedMessage(1, size);
	}
	
	public static Set<PurchaseAcceptedMessage> createSet_PurchaseAcceptedMessage(long index, int size) {
		Set<PurchaseAcceptedMessage> purchaseAcceptedMessageSet = createEmptySet_PurchaseAcceptedMessage();
		long limit = index + size;
		for (; index < limit; index++)
			purchaseAcceptedMessageSet.add(create_PurchaseAcceptedMessage(index));
		return purchaseAcceptedMessageSet;
	}
	
	public static void assertPurchaseAcceptedMessageExists(Collection<PurchaseAcceptedMessage> purchaseAcceptedMessageList, PurchaseAcceptedMessage purchaseAcceptedMessage) {
		Assert.notNull(purchaseAcceptedMessageList, "PurchaseAcceptedMessage list must be specified");
		Assert.notNull(purchaseAcceptedMessage, "PurchaseAcceptedMessage record must be specified");
		Iterator<PurchaseAcceptedMessage> iterator = purchaseAcceptedMessageList.iterator();
		while (iterator.hasNext()) {
			PurchaseAcceptedMessage purchaseAcceptedMessage1 = iterator.next();
			if (purchaseAcceptedMessage1.equals(purchaseAcceptedMessage)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - PurchaseAcceptedMessage should exist: "+purchaseAcceptedMessage);
	}
	
	public static void assertPurchaseAcceptedMessageCorrect(PurchaseAcceptedMessage purchaseAcceptedMessage) {
		assertOrderCorrect(purchaseAcceptedMessage.getOrder());
		assertPaymentCorrect(purchaseAcceptedMessage.getPayment());
		assertInvoiceCorrect(purchaseAcceptedMessage.getInvoice());
	}
	
	public static void assertPurchaseAcceptedMessageCorrect(Collection<PurchaseAcceptedMessage> purchaseAcceptedMessageList) {
		Assert.isTrue(purchaseAcceptedMessageList.size() == 2, "PurchaseAcceptedMessage count not correct");
		Iterator<PurchaseAcceptedMessage> iterator = purchaseAcceptedMessageList.iterator();
		while (iterator.hasNext()) {
			PurchaseAcceptedMessage purchaseAcceptedMessage = iterator.next();
			assertPurchaseAcceptedMessageCorrect(purchaseAcceptedMessage);
		}
	}
	
	public static void assertSamePurchaseAcceptedMessage(PurchaseAcceptedMessage purchaseAcceptedMessage1, PurchaseAcceptedMessage purchaseAcceptedMessage2) {
		assertSamePurchaseAcceptedMessage(purchaseAcceptedMessage1, purchaseAcceptedMessage2, "");
	}
	
	public static void assertSamePurchaseAcceptedMessage(PurchaseAcceptedMessage purchaseAcceptedMessage1, PurchaseAcceptedMessage purchaseAcceptedMessage2, String message) {
		assertObjectExists("PurchaseAcceptedMessage1", purchaseAcceptedMessage1);
		assertObjectExists("PurchaseAcceptedMessage2", purchaseAcceptedMessage2);
		assertSameOrder(purchaseAcceptedMessage1.getOrder(), purchaseAcceptedMessage2.getOrder(), message);
		assertSamePayment(purchaseAcceptedMessage1.getPayment(), purchaseAcceptedMessage2.getPayment(), message);
		assertSameInvoice(purchaseAcceptedMessage1.getInvoice(), purchaseAcceptedMessage2.getInvoice(), message);
	}
	
	public static void assertSamePurchaseAcceptedMessage(Collection<PurchaseAcceptedMessage> purchaseAcceptedMessageList1, Collection<PurchaseAcceptedMessage> purchaseAcceptedMessageList2) {
		assertSamePurchaseAcceptedMessage(purchaseAcceptedMessageList1, purchaseAcceptedMessageList2, "");
	}
	
	public static void assertSamePurchaseAcceptedMessage(Collection<PurchaseAcceptedMessage> purchaseAcceptedMessageList1, Collection<PurchaseAcceptedMessage> purchaseAcceptedMessageList2, String message) {
		Assert.notNull(purchaseAcceptedMessageList1, "PurchaseAcceptedMessage list1 must be specified");
		Assert.notNull(purchaseAcceptedMessageList2, "PurchaseAcceptedMessage list2 must be specified");
		Assert.equals(purchaseAcceptedMessageList1.size(), purchaseAcceptedMessageList2.size(), "PurchaseAcceptedMessage count not equal");
		Collection<PurchaseAcceptedMessage> sortedRecords1 = MessageUtil.sortRecords(purchaseAcceptedMessageList1);
		Collection<PurchaseAcceptedMessage> sortedRecords2 = MessageUtil.sortRecords(purchaseAcceptedMessageList2);
		Iterator<PurchaseAcceptedMessage> list1Iterator = sortedRecords1.iterator();
		Iterator<PurchaseAcceptedMessage> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			PurchaseAcceptedMessage purchaseAcceptedMessage1 = list1Iterator.next();
			PurchaseAcceptedMessage purchaseAcceptedMessage2 = list2Iterator.next();
			assertSamePurchaseAcceptedMessage(purchaseAcceptedMessage1, purchaseAcceptedMessage2, message);
		}
	}
	
	public static PurchaseRejectedMessage createEmpty_PurchaseRejectedMessage() {
		PurchaseRejectedMessage purchaseRejectedMessage = new PurchaseRejectedMessage();
		purchaseRejectedMessageCount++;
		return purchaseRejectedMessage;
	}
	
	public static PurchaseRejectedMessage create_PurchaseRejectedMessage() {
		PurchaseRejectedMessage purchaseRejectedMessage = create_PurchaseRejectedMessage(false, false);
		return purchaseRejectedMessage;
	}
	
	public static PurchaseRejectedMessage create_PurchaseRejectedMessage(long index) {
		PurchaseRejectedMessage purchaseRejectedMessage = create_PurchaseRejectedMessage(false, false, index);
		return purchaseRejectedMessage;
	}
	
	public static PurchaseRejectedMessage create_PurchaseRejectedMessage(boolean cancel, boolean undo) {
		PurchaseRejectedMessage purchaseRejectedMessage = create_PurchaseRejectedMessage(cancel, undo, 1);
		return purchaseRejectedMessage;
	}
	
	public static PurchaseRejectedMessage create_PurchaseRejectedMessage(boolean cancel, boolean undo, long index) {
		PurchaseRejectedMessage purchaseRejectedMessage = createEmpty_PurchaseRejectedMessage();
		long value = createValue(index, purchaseRejectedMessageCount);
		purchaseRejectedMessage.setReason("dummyReason" + value);
		purchaseRejectedMessage.setOrder(create_Order(value));
		purchaseRejectedMessage.setPayment(create_Payment(value));
		purchaseRejectedMessage.setCancelRequest(cancel);
		purchaseRejectedMessage.setUndoRequest(undo);
		return purchaseRejectedMessage;
	}
	
	public static List<PurchaseRejectedMessage> createEmptyList_PurchaseRejectedMessage() {
		return new ArrayList<PurchaseRejectedMessage>();
	}
	
	public static List<PurchaseRejectedMessage> createList_PurchaseRejectedMessage() {
		return createList_PurchaseRejectedMessage(2);
	}
	
	public static List<PurchaseRejectedMessage> createList_PurchaseRejectedMessage(int size) {
		return createList_PurchaseRejectedMessage(1, size);
	}
	
	public static List<PurchaseRejectedMessage> createList_PurchaseRejectedMessage(long index, int size) {
		List<PurchaseRejectedMessage> purchaseRejectedMessageList = createEmptyList_PurchaseRejectedMessage();
		long limit = index + size;
		for (; index < limit; index++)
			purchaseRejectedMessageList.add(create_PurchaseRejectedMessage(index));
		return purchaseRejectedMessageList;
	}
	
	public static Set<PurchaseRejectedMessage> createEmptySet_PurchaseRejectedMessage() {
		return new HashSet<PurchaseRejectedMessage>();
	}
	
	public static Set<PurchaseRejectedMessage> createSet_PurchaseRejectedMessage() {
		return createSet_PurchaseRejectedMessage(2);
	}
	
	public static Set<PurchaseRejectedMessage> createSet_PurchaseRejectedMessage(int size) {
		return createSet_PurchaseRejectedMessage(1, size);
	}
	
	public static Set<PurchaseRejectedMessage> createSet_PurchaseRejectedMessage(long index, int size) {
		Set<PurchaseRejectedMessage> purchaseRejectedMessageSet = createEmptySet_PurchaseRejectedMessage();
		long limit = index + size;
		for (; index < limit; index++)
			purchaseRejectedMessageSet.add(create_PurchaseRejectedMessage(index));
		return purchaseRejectedMessageSet;
	}
	
	public static void assertPurchaseRejectedMessageExists(Collection<PurchaseRejectedMessage> purchaseRejectedMessageList, PurchaseRejectedMessage purchaseRejectedMessage) {
		Assert.notNull(purchaseRejectedMessageList, "PurchaseRejectedMessage list must be specified");
		Assert.notNull(purchaseRejectedMessage, "PurchaseRejectedMessage record must be specified");
		Iterator<PurchaseRejectedMessage> iterator = purchaseRejectedMessageList.iterator();
		while (iterator.hasNext()) {
			PurchaseRejectedMessage purchaseRejectedMessage1 = iterator.next();
			if (purchaseRejectedMessage1.equals(purchaseRejectedMessage)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - PurchaseRejectedMessage should exist: "+purchaseRejectedMessage);
	}
	
	public static void assertPurchaseRejectedMessageCorrect(PurchaseRejectedMessage purchaseRejectedMessage) {
		assertObjectCorrect("Reason", purchaseRejectedMessage.getReason());
		assertOrderCorrect(purchaseRejectedMessage.getOrder());
		assertPaymentCorrect(purchaseRejectedMessage.getPayment());
	}
	
	public static void assertPurchaseRejectedMessageCorrect(Collection<PurchaseRejectedMessage> purchaseRejectedMessageList) {
		Assert.isTrue(purchaseRejectedMessageList.size() == 2, "PurchaseRejectedMessage count not correct");
		Iterator<PurchaseRejectedMessage> iterator = purchaseRejectedMessageList.iterator();
		while (iterator.hasNext()) {
			PurchaseRejectedMessage purchaseRejectedMessage = iterator.next();
			assertPurchaseRejectedMessageCorrect(purchaseRejectedMessage);
		}
	}
	
	public static void assertSamePurchaseRejectedMessage(PurchaseRejectedMessage purchaseRejectedMessage1, PurchaseRejectedMessage purchaseRejectedMessage2) {
		assertSamePurchaseRejectedMessage(purchaseRejectedMessage1, purchaseRejectedMessage2, "");
	}
	
	public static void assertSamePurchaseRejectedMessage(PurchaseRejectedMessage purchaseRejectedMessage1, PurchaseRejectedMessage purchaseRejectedMessage2, String message) {
		assertObjectExists("PurchaseRejectedMessage1", purchaseRejectedMessage1);
		assertObjectExists("PurchaseRejectedMessage2", purchaseRejectedMessage2);
		assertObjectEquals("Reason", purchaseRejectedMessage1.getReason(), purchaseRejectedMessage2.getReason(), message);
		assertSameOrder(purchaseRejectedMessage1.getOrder(), purchaseRejectedMessage2.getOrder(), message);
		assertSamePayment(purchaseRejectedMessage1.getPayment(), purchaseRejectedMessage2.getPayment(), message);
	}
	
	public static void assertSamePurchaseRejectedMessage(Collection<PurchaseRejectedMessage> purchaseRejectedMessageList1, Collection<PurchaseRejectedMessage> purchaseRejectedMessageList2) {
		assertSamePurchaseRejectedMessage(purchaseRejectedMessageList1, purchaseRejectedMessageList2, "");
	}
	
	public static void assertSamePurchaseRejectedMessage(Collection<PurchaseRejectedMessage> purchaseRejectedMessageList1, Collection<PurchaseRejectedMessage> purchaseRejectedMessageList2, String message) {
		Assert.notNull(purchaseRejectedMessageList1, "PurchaseRejectedMessage list1 must be specified");
		Assert.notNull(purchaseRejectedMessageList2, "PurchaseRejectedMessage list2 must be specified");
		Assert.equals(purchaseRejectedMessageList1.size(), purchaseRejectedMessageList2.size(), "PurchaseRejectedMessage count not equal");
		Collection<PurchaseRejectedMessage> sortedRecords1 = MessageUtil.sortRecords(purchaseRejectedMessageList1);
		Collection<PurchaseRejectedMessage> sortedRecords2 = MessageUtil.sortRecords(purchaseRejectedMessageList2);
		Iterator<PurchaseRejectedMessage> list1Iterator = sortedRecords1.iterator();
		Iterator<PurchaseRejectedMessage> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			PurchaseRejectedMessage purchaseRejectedMessage1 = list1Iterator.next();
			PurchaseRejectedMessage purchaseRejectedMessage2 = list2Iterator.next();
			assertSamePurchaseRejectedMessage(purchaseRejectedMessage1, purchaseRejectedMessage2, message);
		}
	}
	
	public static QueryRequestMessage createEmpty_QueryRequestMessage() {
		QueryRequestMessage queryRequestMessage = new QueryRequestMessage();
		queryRequestMessageCount++;
		return queryRequestMessage;
	}
	
	public static QueryRequestMessage create_QueryRequestMessage() {
		QueryRequestMessage queryRequestMessage = create_QueryRequestMessage(false, false);
		return queryRequestMessage;
	}
	
	public static QueryRequestMessage create_QueryRequestMessage(long index) {
		QueryRequestMessage queryRequestMessage = create_QueryRequestMessage(false, false, index);
		return queryRequestMessage;
	}
	
	public static QueryRequestMessage create_QueryRequestMessage(boolean cancel, boolean undo) {
		QueryRequestMessage queryRequestMessage = create_QueryRequestMessage(cancel, undo, 1);
		return queryRequestMessage;
	}
	
	public static QueryRequestMessage create_QueryRequestMessage(boolean cancel, boolean undo, long index) {
		QueryRequestMessage queryRequestMessage = createEmpty_QueryRequestMessage();
		long value = createValue(index, queryRequestMessageCount);
		queryRequestMessage.getBooks().addAll(createList_Book(2));
		queryRequestMessage.setCancelRequest(cancel);
		queryRequestMessage.setUndoRequest(undo);
		return queryRequestMessage;
	}
	
	public static List<QueryRequestMessage> createEmptyList_QueryRequestMessage() {
		return new ArrayList<QueryRequestMessage>();
	}
	
	public static List<QueryRequestMessage> createList_QueryRequestMessage() {
		return createList_QueryRequestMessage(2);
	}
	
	public static List<QueryRequestMessage> createList_QueryRequestMessage(int size) {
		return createList_QueryRequestMessage(1, size);
	}
	
	public static List<QueryRequestMessage> createList_QueryRequestMessage(long index, int size) {
		List<QueryRequestMessage> queryRequestMessageList = createEmptyList_QueryRequestMessage();
		long limit = index + size;
		for (; index < limit; index++)
			queryRequestMessageList.add(create_QueryRequestMessage(index));
		return queryRequestMessageList;
	}
	
	public static Set<QueryRequestMessage> createEmptySet_QueryRequestMessage() {
		return new HashSet<QueryRequestMessage>();
	}
	
	public static Set<QueryRequestMessage> createSet_QueryRequestMessage() {
		return createSet_QueryRequestMessage(2);
	}
	
	public static Set<QueryRequestMessage> createSet_QueryRequestMessage(int size) {
		return createSet_QueryRequestMessage(1, size);
	}
	
	public static Set<QueryRequestMessage> createSet_QueryRequestMessage(long index, int size) {
		Set<QueryRequestMessage> queryRequestMessageSet = createEmptySet_QueryRequestMessage();
		long limit = index + size;
		for (; index < limit; index++)
			queryRequestMessageSet.add(create_QueryRequestMessage(index));
		return queryRequestMessageSet;
	}
	
	public static void assertQueryRequestMessageExists(Collection<QueryRequestMessage> queryRequestMessageList, QueryRequestMessage queryRequestMessage) {
		Assert.notNull(queryRequestMessageList, "QueryRequestMessage list must be specified");
		Assert.notNull(queryRequestMessage, "QueryRequestMessage record must be specified");
		Iterator<QueryRequestMessage> iterator = queryRequestMessageList.iterator();
		while (iterator.hasNext()) {
			QueryRequestMessage queryRequestMessage1 = iterator.next();
			if (queryRequestMessage1.equals(queryRequestMessage)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - QueryRequestMessage should exist: "+queryRequestMessage);
	}
	
	public static void assertQueryRequestMessageCorrect(QueryRequestMessage queryRequestMessage) {
		assertBookCorrect(queryRequestMessage.getBooks());
	}
	
	public static void assertQueryRequestMessageCorrect(Collection<QueryRequestMessage> queryRequestMessageList) {
		Assert.isTrue(queryRequestMessageList.size() == 2, "QueryRequestMessage count not correct");
		Iterator<QueryRequestMessage> iterator = queryRequestMessageList.iterator();
		while (iterator.hasNext()) {
			QueryRequestMessage queryRequestMessage = iterator.next();
			assertQueryRequestMessageCorrect(queryRequestMessage);
		}
	}
	
	public static void assertSameQueryRequestMessage(QueryRequestMessage queryRequestMessage1, QueryRequestMessage queryRequestMessage2) {
		assertSameQueryRequestMessage(queryRequestMessage1, queryRequestMessage2, "");
	}
	
	public static void assertSameQueryRequestMessage(QueryRequestMessage queryRequestMessage1, QueryRequestMessage queryRequestMessage2, String message) {
		assertObjectExists("QueryRequestMessage1", queryRequestMessage1);
		assertObjectExists("QueryRequestMessage2", queryRequestMessage2);
		assertSameBook(queryRequestMessage1.getBooks(), queryRequestMessage2.getBooks(), message);
	}
	
	public static void assertSameQueryRequestMessage(Collection<QueryRequestMessage> queryRequestMessageList1, Collection<QueryRequestMessage> queryRequestMessageList2) {
		assertSameQueryRequestMessage(queryRequestMessageList1, queryRequestMessageList2, "");
	}
	
	public static void assertSameQueryRequestMessage(Collection<QueryRequestMessage> queryRequestMessageList1, Collection<QueryRequestMessage> queryRequestMessageList2, String message) {
		Assert.notNull(queryRequestMessageList1, "QueryRequestMessage list1 must be specified");
		Assert.notNull(queryRequestMessageList2, "QueryRequestMessage list2 must be specified");
		Assert.equals(queryRequestMessageList1.size(), queryRequestMessageList2.size(), "QueryRequestMessage count not equal");
		Collection<QueryRequestMessage> sortedRecords1 = MessageUtil.sortRecords(queryRequestMessageList1);
		Collection<QueryRequestMessage> sortedRecords2 = MessageUtil.sortRecords(queryRequestMessageList2);
		Iterator<QueryRequestMessage> list1Iterator = sortedRecords1.iterator();
		Iterator<QueryRequestMessage> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			QueryRequestMessage queryRequestMessage1 = list1Iterator.next();
			QueryRequestMessage queryRequestMessage2 = list2Iterator.next();
			assertSameQueryRequestMessage(queryRequestMessage1, queryRequestMessage2, message);
		}
	}
	
	public static ReservationRequestMessage createEmpty_ReservationRequestMessage() {
		ReservationRequestMessage reservationRequestMessage = new ReservationRequestMessage();
		reservationRequestMessageCount++;
		return reservationRequestMessage;
	}
	
	public static ReservationRequestMessage create_ReservationRequestMessage() {
		ReservationRequestMessage reservationRequestMessage = create_ReservationRequestMessage(false, false);
		return reservationRequestMessage;
	}
	
	public static ReservationRequestMessage create_ReservationRequestMessage(long index) {
		ReservationRequestMessage reservationRequestMessage = create_ReservationRequestMessage(false, false, index);
		return reservationRequestMessage;
	}
	
	public static ReservationRequestMessage create_ReservationRequestMessage(boolean cancel, boolean undo) {
		ReservationRequestMessage reservationRequestMessage = create_ReservationRequestMessage(cancel, undo, 1);
		return reservationRequestMessage;
	}
	
	public static ReservationRequestMessage create_ReservationRequestMessage(boolean cancel, boolean undo, long index) {
		ReservationRequestMessage reservationRequestMessage = createEmpty_ReservationRequestMessage();
		long value = createValue(index, reservationRequestMessageCount);
		reservationRequestMessage.getBooks().addAll(createList_Book(2));
		reservationRequestMessage.setCancelRequest(cancel);
		reservationRequestMessage.setUndoRequest(undo);
		return reservationRequestMessage;
	}
	
	public static List<ReservationRequestMessage> createEmptyList_ReservationRequestMessage() {
		return new ArrayList<ReservationRequestMessage>();
	}
	
	public static List<ReservationRequestMessage> createList_ReservationRequestMessage() {
		return createList_ReservationRequestMessage(2);
	}
	
	public static List<ReservationRequestMessage> createList_ReservationRequestMessage(int size) {
		return createList_ReservationRequestMessage(1, size);
	}
	
	public static List<ReservationRequestMessage> createList_ReservationRequestMessage(long index, int size) {
		List<ReservationRequestMessage> reservationRequestMessageList = createEmptyList_ReservationRequestMessage();
		long limit = index + size;
		for (; index < limit; index++)
			reservationRequestMessageList.add(create_ReservationRequestMessage(index));
		return reservationRequestMessageList;
	}
	
	public static Set<ReservationRequestMessage> createEmptySet_ReservationRequestMessage() {
		return new HashSet<ReservationRequestMessage>();
	}
	
	public static Set<ReservationRequestMessage> createSet_ReservationRequestMessage() {
		return createSet_ReservationRequestMessage(2);
	}
	
	public static Set<ReservationRequestMessage> createSet_ReservationRequestMessage(int size) {
		return createSet_ReservationRequestMessage(1, size);
	}
	
	public static Set<ReservationRequestMessage> createSet_ReservationRequestMessage(long index, int size) {
		Set<ReservationRequestMessage> reservationRequestMessageSet = createEmptySet_ReservationRequestMessage();
		long limit = index + size;
		for (; index < limit; index++)
			reservationRequestMessageSet.add(create_ReservationRequestMessage(index));
		return reservationRequestMessageSet;
	}
	
	public static void assertReservationRequestMessageExists(Collection<ReservationRequestMessage> reservationRequestMessageList, ReservationRequestMessage reservationRequestMessage) {
		Assert.notNull(reservationRequestMessageList, "ReservationRequestMessage list must be specified");
		Assert.notNull(reservationRequestMessage, "ReservationRequestMessage record must be specified");
		Iterator<ReservationRequestMessage> iterator = reservationRequestMessageList.iterator();
		while (iterator.hasNext()) {
			ReservationRequestMessage reservationRequestMessage1 = iterator.next();
			if (reservationRequestMessage1.equals(reservationRequestMessage)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - ReservationRequestMessage should exist: "+reservationRequestMessage);
	}
	
	public static void assertReservationRequestMessageCorrect(ReservationRequestMessage reservationRequestMessage) {
		assertBookCorrect(reservationRequestMessage.getBooks());
	}
	
	public static void assertReservationRequestMessageCorrect(Collection<ReservationRequestMessage> reservationRequestMessageList) {
		Assert.isTrue(reservationRequestMessageList.size() == 2, "ReservationRequestMessage count not correct");
		Iterator<ReservationRequestMessage> iterator = reservationRequestMessageList.iterator();
		while (iterator.hasNext()) {
			ReservationRequestMessage reservationRequestMessage = iterator.next();
			assertReservationRequestMessageCorrect(reservationRequestMessage);
		}
	}
	
	public static void assertSameReservationRequestMessage(ReservationRequestMessage reservationRequestMessage1, ReservationRequestMessage reservationRequestMessage2) {
		assertSameReservationRequestMessage(reservationRequestMessage1, reservationRequestMessage2, "");
	}
	
	public static void assertSameReservationRequestMessage(ReservationRequestMessage reservationRequestMessage1, ReservationRequestMessage reservationRequestMessage2, String message) {
		assertObjectExists("ReservationRequestMessage1", reservationRequestMessage1);
		assertObjectExists("ReservationRequestMessage2", reservationRequestMessage2);
		assertSameBook(reservationRequestMessage1.getBooks(), reservationRequestMessage2.getBooks(), message);
	}
	
	public static void assertSameReservationRequestMessage(Collection<ReservationRequestMessage> reservationRequestMessageList1, Collection<ReservationRequestMessage> reservationRequestMessageList2) {
		assertSameReservationRequestMessage(reservationRequestMessageList1, reservationRequestMessageList2, "");
	}
	
	public static void assertSameReservationRequestMessage(Collection<ReservationRequestMessage> reservationRequestMessageList1, Collection<ReservationRequestMessage> reservationRequestMessageList2, String message) {
		Assert.notNull(reservationRequestMessageList1, "ReservationRequestMessage list1 must be specified");
		Assert.notNull(reservationRequestMessageList2, "ReservationRequestMessage list2 must be specified");
		Assert.equals(reservationRequestMessageList1.size(), reservationRequestMessageList2.size(), "ReservationRequestMessage count not equal");
		Collection<ReservationRequestMessage> sortedRecords1 = MessageUtil.sortRecords(reservationRequestMessageList1);
		Collection<ReservationRequestMessage> sortedRecords2 = MessageUtil.sortRecords(reservationRequestMessageList2);
		Iterator<ReservationRequestMessage> list1Iterator = sortedRecords1.iterator();
		Iterator<ReservationRequestMessage> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			ReservationRequestMessage reservationRequestMessage1 = list1Iterator.next();
			ReservationRequestMessage reservationRequestMessage2 = list2Iterator.next();
			assertSameReservationRequestMessage(reservationRequestMessage1, reservationRequestMessage2, message);
		}
	}
	
	public static BooksAvailableMessage createEmpty_BooksAvailableMessage() {
		BooksAvailableMessage booksAvailableMessage = new BooksAvailableMessage();
		booksAvailableMessageCount++;
		return booksAvailableMessage;
	}
	
	public static BooksAvailableMessage create_BooksAvailableMessage() {
		BooksAvailableMessage booksAvailableMessage = create_BooksAvailableMessage(false, false);
		return booksAvailableMessage;
	}
	
	public static BooksAvailableMessage create_BooksAvailableMessage(long index) {
		BooksAvailableMessage booksAvailableMessage = create_BooksAvailableMessage(false, false, index);
		return booksAvailableMessage;
	}
	
	public static BooksAvailableMessage create_BooksAvailableMessage(boolean cancel, boolean undo) {
		BooksAvailableMessage booksAvailableMessage = create_BooksAvailableMessage(cancel, undo, 1);
		return booksAvailableMessage;
	}
	
	public static BooksAvailableMessage create_BooksAvailableMessage(boolean cancel, boolean undo, long index) {
		BooksAvailableMessage booksAvailableMessage = createEmpty_BooksAvailableMessage();
		long value = createValue(index, booksAvailableMessageCount);
		booksAvailableMessage.getBooks().addAll(createList_Book(2));
		booksAvailableMessage.setCancelRequest(cancel);
		booksAvailableMessage.setUndoRequest(undo);
		return booksAvailableMessage;
	}
	
	public static List<BooksAvailableMessage> createEmptyList_BooksAvailableMessage() {
		return new ArrayList<BooksAvailableMessage>();
	}
	
	public static List<BooksAvailableMessage> createList_BooksAvailableMessage() {
		return createList_BooksAvailableMessage(2);
	}
	
	public static List<BooksAvailableMessage> createList_BooksAvailableMessage(int size) {
		return createList_BooksAvailableMessage(1, size);
	}
	
	public static List<BooksAvailableMessage> createList_BooksAvailableMessage(long index, int size) {
		List<BooksAvailableMessage> booksAvailableMessageList = createEmptyList_BooksAvailableMessage();
		long limit = index + size;
		for (; index < limit; index++)
			booksAvailableMessageList.add(create_BooksAvailableMessage(index));
		return booksAvailableMessageList;
	}
	
	public static Set<BooksAvailableMessage> createEmptySet_BooksAvailableMessage() {
		return new HashSet<BooksAvailableMessage>();
	}
	
	public static Set<BooksAvailableMessage> createSet_BooksAvailableMessage() {
		return createSet_BooksAvailableMessage(2);
	}
	
	public static Set<BooksAvailableMessage> createSet_BooksAvailableMessage(int size) {
		return createSet_BooksAvailableMessage(1, size);
	}
	
	public static Set<BooksAvailableMessage> createSet_BooksAvailableMessage(long index, int size) {
		Set<BooksAvailableMessage> booksAvailableMessageSet = createEmptySet_BooksAvailableMessage();
		long limit = index + size;
		for (; index < limit; index++)
			booksAvailableMessageSet.add(create_BooksAvailableMessage(index));
		return booksAvailableMessageSet;
	}
	
	public static void assertBooksAvailableMessageExists(Collection<BooksAvailableMessage> booksAvailableMessageList, BooksAvailableMessage booksAvailableMessage) {
		Assert.notNull(booksAvailableMessageList, "BooksAvailableMessage list must be specified");
		Assert.notNull(booksAvailableMessage, "BooksAvailableMessage record must be specified");
		Iterator<BooksAvailableMessage> iterator = booksAvailableMessageList.iterator();
		while (iterator.hasNext()) {
			BooksAvailableMessage booksAvailableMessage1 = iterator.next();
			if (booksAvailableMessage1.equals(booksAvailableMessage)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - BooksAvailableMessage should exist: "+booksAvailableMessage);
	}
	
	public static void assertBooksAvailableMessageCorrect(BooksAvailableMessage booksAvailableMessage) {
		assertBookCorrect(booksAvailableMessage.getBooks());
	}
	
	public static void assertBooksAvailableMessageCorrect(Collection<BooksAvailableMessage> booksAvailableMessageList) {
		Assert.isTrue(booksAvailableMessageList.size() == 2, "BooksAvailableMessage count not correct");
		Iterator<BooksAvailableMessage> iterator = booksAvailableMessageList.iterator();
		while (iterator.hasNext()) {
			BooksAvailableMessage booksAvailableMessage = iterator.next();
			assertBooksAvailableMessageCorrect(booksAvailableMessage);
		}
	}
	
	public static void assertSameBooksAvailableMessage(BooksAvailableMessage booksAvailableMessage1, BooksAvailableMessage booksAvailableMessage2) {
		assertSameBooksAvailableMessage(booksAvailableMessage1, booksAvailableMessage2, "");
	}
	
	public static void assertSameBooksAvailableMessage(BooksAvailableMessage booksAvailableMessage1, BooksAvailableMessage booksAvailableMessage2, String message) {
		assertObjectExists("BooksAvailableMessage1", booksAvailableMessage1);
		assertObjectExists("BooksAvailableMessage2", booksAvailableMessage2);
		assertSameBook(booksAvailableMessage1.getBooks(), booksAvailableMessage2.getBooks(), message);
	}
	
	public static void assertSameBooksAvailableMessage(Collection<BooksAvailableMessage> booksAvailableMessageList1, Collection<BooksAvailableMessage> booksAvailableMessageList2) {
		assertSameBooksAvailableMessage(booksAvailableMessageList1, booksAvailableMessageList2, "");
	}
	
	public static void assertSameBooksAvailableMessage(Collection<BooksAvailableMessage> booksAvailableMessageList1, Collection<BooksAvailableMessage> booksAvailableMessageList2, String message) {
		Assert.notNull(booksAvailableMessageList1, "BooksAvailableMessage list1 must be specified");
		Assert.notNull(booksAvailableMessageList2, "BooksAvailableMessage list2 must be specified");
		Assert.equals(booksAvailableMessageList1.size(), booksAvailableMessageList2.size(), "BooksAvailableMessage count not equal");
		Collection<BooksAvailableMessage> sortedRecords1 = MessageUtil.sortRecords(booksAvailableMessageList1);
		Collection<BooksAvailableMessage> sortedRecords2 = MessageUtil.sortRecords(booksAvailableMessageList2);
		Iterator<BooksAvailableMessage> list1Iterator = sortedRecords1.iterator();
		Iterator<BooksAvailableMessage> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			BooksAvailableMessage booksAvailableMessage1 = list1Iterator.next();
			BooksAvailableMessage booksAvailableMessage2 = list2Iterator.next();
			assertSameBooksAvailableMessage(booksAvailableMessage1, booksAvailableMessage2, message);
		}
	}
	
	public static BooksUnavailableMessage createEmpty_BooksUnavailableMessage() {
		BooksUnavailableMessage booksUnavailableMessage = new BooksUnavailableMessage();
		booksUnavailableMessageCount++;
		return booksUnavailableMessage;
	}
	
	public static BooksUnavailableMessage create_BooksUnavailableMessage() {
		BooksUnavailableMessage booksUnavailableMessage = create_BooksUnavailableMessage(false, false);
		return booksUnavailableMessage;
	}
	
	public static BooksUnavailableMessage create_BooksUnavailableMessage(long index) {
		BooksUnavailableMessage booksUnavailableMessage = create_BooksUnavailableMessage(false, false, index);
		return booksUnavailableMessage;
	}
	
	public static BooksUnavailableMessage create_BooksUnavailableMessage(boolean cancel, boolean undo) {
		BooksUnavailableMessage booksUnavailableMessage = create_BooksUnavailableMessage(cancel, undo, 1);
		return booksUnavailableMessage;
	}
	
	public static BooksUnavailableMessage create_BooksUnavailableMessage(boolean cancel, boolean undo, long index) {
		BooksUnavailableMessage booksUnavailableMessage = createEmpty_BooksUnavailableMessage();
		long value = createValue(index, booksUnavailableMessageCount);
		booksUnavailableMessage.getBooks().addAll(createList_Book(2));
		booksUnavailableMessage.setCancelRequest(cancel);
		booksUnavailableMessage.setUndoRequest(undo);
		return booksUnavailableMessage;
	}
	
	public static List<BooksUnavailableMessage> createEmptyList_BooksUnavailableMessage() {
		return new ArrayList<BooksUnavailableMessage>();
	}
	
	public static List<BooksUnavailableMessage> createList_BooksUnavailableMessage() {
		return createList_BooksUnavailableMessage(2);
	}
	
	public static List<BooksUnavailableMessage> createList_BooksUnavailableMessage(int size) {
		return createList_BooksUnavailableMessage(1, size);
	}
	
	public static List<BooksUnavailableMessage> createList_BooksUnavailableMessage(long index, int size) {
		List<BooksUnavailableMessage> booksUnavailableMessageList = createEmptyList_BooksUnavailableMessage();
		long limit = index + size;
		for (; index < limit; index++)
			booksUnavailableMessageList.add(create_BooksUnavailableMessage(index));
		return booksUnavailableMessageList;
	}
	
	public static Set<BooksUnavailableMessage> createEmptySet_BooksUnavailableMessage() {
		return new HashSet<BooksUnavailableMessage>();
	}
	
	public static Set<BooksUnavailableMessage> createSet_BooksUnavailableMessage() {
		return createSet_BooksUnavailableMessage(2);
	}
	
	public static Set<BooksUnavailableMessage> createSet_BooksUnavailableMessage(int size) {
		return createSet_BooksUnavailableMessage(1, size);
	}
	
	public static Set<BooksUnavailableMessage> createSet_BooksUnavailableMessage(long index, int size) {
		Set<BooksUnavailableMessage> booksUnavailableMessageSet = createEmptySet_BooksUnavailableMessage();
		long limit = index + size;
		for (; index < limit; index++)
			booksUnavailableMessageSet.add(create_BooksUnavailableMessage(index));
		return booksUnavailableMessageSet;
	}
	
	public static void assertBooksUnavailableMessageExists(Collection<BooksUnavailableMessage> booksUnavailableMessageList, BooksUnavailableMessage booksUnavailableMessage) {
		Assert.notNull(booksUnavailableMessageList, "BooksUnavailableMessage list must be specified");
		Assert.notNull(booksUnavailableMessage, "BooksUnavailableMessage record must be specified");
		Iterator<BooksUnavailableMessage> iterator = booksUnavailableMessageList.iterator();
		while (iterator.hasNext()) {
			BooksUnavailableMessage booksUnavailableMessage1 = iterator.next();
			if (booksUnavailableMessage1.equals(booksUnavailableMessage)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - BooksUnavailableMessage should exist: "+booksUnavailableMessage);
	}
	
	public static void assertBooksUnavailableMessageCorrect(BooksUnavailableMessage booksUnavailableMessage) {
		assertBookCorrect(booksUnavailableMessage.getBooks());
	}
	
	public static void assertBooksUnavailableMessageCorrect(Collection<BooksUnavailableMessage> booksUnavailableMessageList) {
		Assert.isTrue(booksUnavailableMessageList.size() == 2, "BooksUnavailableMessage count not correct");
		Iterator<BooksUnavailableMessage> iterator = booksUnavailableMessageList.iterator();
		while (iterator.hasNext()) {
			BooksUnavailableMessage booksUnavailableMessage = iterator.next();
			assertBooksUnavailableMessageCorrect(booksUnavailableMessage);
		}
	}
	
	public static void assertSameBooksUnavailableMessage(BooksUnavailableMessage booksUnavailableMessage1, BooksUnavailableMessage booksUnavailableMessage2) {
		assertSameBooksUnavailableMessage(booksUnavailableMessage1, booksUnavailableMessage2, "");
	}
	
	public static void assertSameBooksUnavailableMessage(BooksUnavailableMessage booksUnavailableMessage1, BooksUnavailableMessage booksUnavailableMessage2, String message) {
		assertObjectExists("BooksUnavailableMessage1", booksUnavailableMessage1);
		assertObjectExists("BooksUnavailableMessage2", booksUnavailableMessage2);
		assertSameBook(booksUnavailableMessage1.getBooks(), booksUnavailableMessage2.getBooks(), message);
	}
	
	public static void assertSameBooksUnavailableMessage(Collection<BooksUnavailableMessage> booksUnavailableMessageList1, Collection<BooksUnavailableMessage> booksUnavailableMessageList2) {
		assertSameBooksUnavailableMessage(booksUnavailableMessageList1, booksUnavailableMessageList2, "");
	}
	
	public static void assertSameBooksUnavailableMessage(Collection<BooksUnavailableMessage> booksUnavailableMessageList1, Collection<BooksUnavailableMessage> booksUnavailableMessageList2, String message) {
		Assert.notNull(booksUnavailableMessageList1, "BooksUnavailableMessage list1 must be specified");
		Assert.notNull(booksUnavailableMessageList2, "BooksUnavailableMessage list2 must be specified");
		Assert.equals(booksUnavailableMessageList1.size(), booksUnavailableMessageList2.size(), "BooksUnavailableMessage count not equal");
		Collection<BooksUnavailableMessage> sortedRecords1 = MessageUtil.sortRecords(booksUnavailableMessageList1);
		Collection<BooksUnavailableMessage> sortedRecords2 = MessageUtil.sortRecords(booksUnavailableMessageList2);
		Iterator<BooksUnavailableMessage> list1Iterator = sortedRecords1.iterator();
		Iterator<BooksUnavailableMessage> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			BooksUnavailableMessage booksUnavailableMessage1 = list1Iterator.next();
			BooksUnavailableMessage booksUnavailableMessage2 = list2Iterator.next();
			assertSameBooksUnavailableMessage(booksUnavailableMessage1, booksUnavailableMessage2, message);
		}
	}
	
	public static ShipmentRequestMessage createEmpty_ShipmentRequestMessage() {
		ShipmentRequestMessage shipmentRequestMessage = new ShipmentRequestMessage();
		shipmentRequestMessageCount++;
		return shipmentRequestMessage;
	}
	
	public static ShipmentRequestMessage create_ShipmentRequestMessage() {
		ShipmentRequestMessage shipmentRequestMessage = create_ShipmentRequestMessage(false, false);
		return shipmentRequestMessage;
	}
	
	public static ShipmentRequestMessage create_ShipmentRequestMessage(long index) {
		ShipmentRequestMessage shipmentRequestMessage = create_ShipmentRequestMessage(false, false, index);
		return shipmentRequestMessage;
	}
	
	public static ShipmentRequestMessage create_ShipmentRequestMessage(boolean cancel, boolean undo) {
		ShipmentRequestMessage shipmentRequestMessage = create_ShipmentRequestMessage(cancel, undo, 1);
		return shipmentRequestMessage;
	}
	
	public static ShipmentRequestMessage create_ShipmentRequestMessage(boolean cancel, boolean undo, long index) {
		ShipmentRequestMessage shipmentRequestMessage = createEmpty_ShipmentRequestMessage();
		long value = createValue(index, shipmentRequestMessageCount);
		shipmentRequestMessage.setShipment(create_Shipment(value));
		shipmentRequestMessage.setPayment(create_Payment(value));
		shipmentRequestMessage.setCancelRequest(cancel);
		shipmentRequestMessage.setUndoRequest(undo);
		return shipmentRequestMessage;
	}
	
	public static List<ShipmentRequestMessage> createEmptyList_ShipmentRequestMessage() {
		return new ArrayList<ShipmentRequestMessage>();
	}
	
	public static List<ShipmentRequestMessage> createList_ShipmentRequestMessage() {
		return createList_ShipmentRequestMessage(2);
	}
	
	public static List<ShipmentRequestMessage> createList_ShipmentRequestMessage(int size) {
		return createList_ShipmentRequestMessage(1, size);
	}
	
	public static List<ShipmentRequestMessage> createList_ShipmentRequestMessage(long index, int size) {
		List<ShipmentRequestMessage> shipmentRequestMessageList = createEmptyList_ShipmentRequestMessage();
		long limit = index + size;
		for (; index < limit; index++)
			shipmentRequestMessageList.add(create_ShipmentRequestMessage(index));
		return shipmentRequestMessageList;
	}
	
	public static Set<ShipmentRequestMessage> createEmptySet_ShipmentRequestMessage() {
		return new HashSet<ShipmentRequestMessage>();
	}
	
	public static Set<ShipmentRequestMessage> createSet_ShipmentRequestMessage() {
		return createSet_ShipmentRequestMessage(2);
	}
	
	public static Set<ShipmentRequestMessage> createSet_ShipmentRequestMessage(int size) {
		return createSet_ShipmentRequestMessage(1, size);
	}
	
	public static Set<ShipmentRequestMessage> createSet_ShipmentRequestMessage(long index, int size) {
		Set<ShipmentRequestMessage> shipmentRequestMessageSet = createEmptySet_ShipmentRequestMessage();
		long limit = index + size;
		for (; index < limit; index++)
			shipmentRequestMessageSet.add(create_ShipmentRequestMessage(index));
		return shipmentRequestMessageSet;
	}
	
	public static void assertShipmentRequestMessageExists(Collection<ShipmentRequestMessage> shipmentRequestMessageList, ShipmentRequestMessage shipmentRequestMessage) {
		Assert.notNull(shipmentRequestMessageList, "ShipmentRequestMessage list must be specified");
		Assert.notNull(shipmentRequestMessage, "ShipmentRequestMessage record must be specified");
		Iterator<ShipmentRequestMessage> iterator = shipmentRequestMessageList.iterator();
		while (iterator.hasNext()) {
			ShipmentRequestMessage shipmentRequestMessage1 = iterator.next();
			if (shipmentRequestMessage1.equals(shipmentRequestMessage)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - ShipmentRequestMessage should exist: "+shipmentRequestMessage);
	}
	
	public static void assertShipmentRequestMessageCorrect(ShipmentRequestMessage shipmentRequestMessage) {
		assertShipmentCorrect(shipmentRequestMessage.getShipment());
		assertPaymentCorrect(shipmentRequestMessage.getPayment());
	}
	
	public static void assertShipmentRequestMessageCorrect(Collection<ShipmentRequestMessage> shipmentRequestMessageList) {
		Assert.isTrue(shipmentRequestMessageList.size() == 2, "ShipmentRequestMessage count not correct");
		Iterator<ShipmentRequestMessage> iterator = shipmentRequestMessageList.iterator();
		while (iterator.hasNext()) {
			ShipmentRequestMessage shipmentRequestMessage = iterator.next();
			assertShipmentRequestMessageCorrect(shipmentRequestMessage);
		}
	}
	
	public static void assertSameShipmentRequestMessage(ShipmentRequestMessage shipmentRequestMessage1, ShipmentRequestMessage shipmentRequestMessage2) {
		assertSameShipmentRequestMessage(shipmentRequestMessage1, shipmentRequestMessage2, "");
	}
	
	public static void assertSameShipmentRequestMessage(ShipmentRequestMessage shipmentRequestMessage1, ShipmentRequestMessage shipmentRequestMessage2, String message) {
		assertObjectExists("ShipmentRequestMessage1", shipmentRequestMessage1);
		assertObjectExists("ShipmentRequestMessage2", shipmentRequestMessage2);
		assertSameShipment(shipmentRequestMessage1.getShipment(), shipmentRequestMessage2.getShipment(), message);
		assertSamePayment(shipmentRequestMessage1.getPayment(), shipmentRequestMessage2.getPayment(), message);
	}
	
	public static void assertSameShipmentRequestMessage(Collection<ShipmentRequestMessage> shipmentRequestMessageList1, Collection<ShipmentRequestMessage> shipmentRequestMessageList2) {
		assertSameShipmentRequestMessage(shipmentRequestMessageList1, shipmentRequestMessageList2, "");
	}
	
	public static void assertSameShipmentRequestMessage(Collection<ShipmentRequestMessage> shipmentRequestMessageList1, Collection<ShipmentRequestMessage> shipmentRequestMessageList2, String message) {
		Assert.notNull(shipmentRequestMessageList1, "ShipmentRequestMessage list1 must be specified");
		Assert.notNull(shipmentRequestMessageList2, "ShipmentRequestMessage list2 must be specified");
		Assert.equals(shipmentRequestMessageList1.size(), shipmentRequestMessageList2.size(), "ShipmentRequestMessage count not equal");
		Collection<ShipmentRequestMessage> sortedRecords1 = MessageUtil.sortRecords(shipmentRequestMessageList1);
		Collection<ShipmentRequestMessage> sortedRecords2 = MessageUtil.sortRecords(shipmentRequestMessageList2);
		Iterator<ShipmentRequestMessage> list1Iterator = sortedRecords1.iterator();
		Iterator<ShipmentRequestMessage> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			ShipmentRequestMessage shipmentRequestMessage1 = list1Iterator.next();
			ShipmentRequestMessage shipmentRequestMessage2 = list2Iterator.next();
			assertSameShipmentRequestMessage(shipmentRequestMessage1, shipmentRequestMessage2, message);
		}
	}
	
	public static ShipmentScheduledMessage createEmpty_ShipmentScheduledMessage() {
		ShipmentScheduledMessage shipmentScheduledMessage = new ShipmentScheduledMessage();
		shipmentScheduledMessageCount++;
		return shipmentScheduledMessage;
	}
	
	public static ShipmentScheduledMessage create_ShipmentScheduledMessage() {
		ShipmentScheduledMessage shipmentScheduledMessage = create_ShipmentScheduledMessage(false, false);
		return shipmentScheduledMessage;
	}
	
	public static ShipmentScheduledMessage create_ShipmentScheduledMessage(long index) {
		ShipmentScheduledMessage shipmentScheduledMessage = create_ShipmentScheduledMessage(false, false, index);
		return shipmentScheduledMessage;
	}
	
	public static ShipmentScheduledMessage create_ShipmentScheduledMessage(boolean cancel, boolean undo) {
		ShipmentScheduledMessage shipmentScheduledMessage = create_ShipmentScheduledMessage(cancel, undo, 1);
		return shipmentScheduledMessage;
	}
	
	public static ShipmentScheduledMessage create_ShipmentScheduledMessage(boolean cancel, boolean undo, long index) {
		ShipmentScheduledMessage shipmentScheduledMessage = createEmpty_ShipmentScheduledMessage();
		long value = createValue(index, shipmentScheduledMessageCount);
		shipmentScheduledMessage.setShipment(create_Shipment(value));
		shipmentScheduledMessage.setCancelRequest(cancel);
		shipmentScheduledMessage.setUndoRequest(undo);
		return shipmentScheduledMessage;
	}
	
	public static List<ShipmentScheduledMessage> createEmptyList_ShipmentScheduledMessage() {
		return new ArrayList<ShipmentScheduledMessage>();
	}
	
	public static List<ShipmentScheduledMessage> createList_ShipmentScheduledMessage() {
		return createList_ShipmentScheduledMessage(2);
	}
	
	public static List<ShipmentScheduledMessage> createList_ShipmentScheduledMessage(int size) {
		return createList_ShipmentScheduledMessage(1, size);
	}
	
	public static List<ShipmentScheduledMessage> createList_ShipmentScheduledMessage(long index, int size) {
		List<ShipmentScheduledMessage> shipmentScheduledMessageList = createEmptyList_ShipmentScheduledMessage();
		long limit = index + size;
		for (; index < limit; index++)
			shipmentScheduledMessageList.add(create_ShipmentScheduledMessage(index));
		return shipmentScheduledMessageList;
	}
	
	public static Set<ShipmentScheduledMessage> createEmptySet_ShipmentScheduledMessage() {
		return new HashSet<ShipmentScheduledMessage>();
	}
	
	public static Set<ShipmentScheduledMessage> createSet_ShipmentScheduledMessage() {
		return createSet_ShipmentScheduledMessage(2);
	}
	
	public static Set<ShipmentScheduledMessage> createSet_ShipmentScheduledMessage(int size) {
		return createSet_ShipmentScheduledMessage(1, size);
	}
	
	public static Set<ShipmentScheduledMessage> createSet_ShipmentScheduledMessage(long index, int size) {
		Set<ShipmentScheduledMessage> shipmentScheduledMessageSet = createEmptySet_ShipmentScheduledMessage();
		long limit = index + size;
		for (; index < limit; index++)
			shipmentScheduledMessageSet.add(create_ShipmentScheduledMessage(index));
		return shipmentScheduledMessageSet;
	}
	
	public static void assertShipmentScheduledMessageExists(Collection<ShipmentScheduledMessage> shipmentScheduledMessageList, ShipmentScheduledMessage shipmentScheduledMessage) {
		Assert.notNull(shipmentScheduledMessageList, "ShipmentScheduledMessage list must be specified");
		Assert.notNull(shipmentScheduledMessage, "ShipmentScheduledMessage record must be specified");
		Iterator<ShipmentScheduledMessage> iterator = shipmentScheduledMessageList.iterator();
		while (iterator.hasNext()) {
			ShipmentScheduledMessage shipmentScheduledMessage1 = iterator.next();
			if (shipmentScheduledMessage1.equals(shipmentScheduledMessage)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - ShipmentScheduledMessage should exist: "+shipmentScheduledMessage);
	}
	
	public static void assertShipmentScheduledMessageCorrect(ShipmentScheduledMessage shipmentScheduledMessage) {
		assertShipmentCorrect(shipmentScheduledMessage.getShipment());
	}
	
	public static void assertShipmentScheduledMessageCorrect(Collection<ShipmentScheduledMessage> shipmentScheduledMessageList) {
		Assert.isTrue(shipmentScheduledMessageList.size() == 2, "ShipmentScheduledMessage count not correct");
		Iterator<ShipmentScheduledMessage> iterator = shipmentScheduledMessageList.iterator();
		while (iterator.hasNext()) {
			ShipmentScheduledMessage shipmentScheduledMessage = iterator.next();
			assertShipmentScheduledMessageCorrect(shipmentScheduledMessage);
		}
	}
	
	public static void assertSameShipmentScheduledMessage(ShipmentScheduledMessage shipmentScheduledMessage1, ShipmentScheduledMessage shipmentScheduledMessage2) {
		assertSameShipmentScheduledMessage(shipmentScheduledMessage1, shipmentScheduledMessage2, "");
	}
	
	public static void assertSameShipmentScheduledMessage(ShipmentScheduledMessage shipmentScheduledMessage1, ShipmentScheduledMessage shipmentScheduledMessage2, String message) {
		assertObjectExists("ShipmentScheduledMessage1", shipmentScheduledMessage1);
		assertObjectExists("ShipmentScheduledMessage2", shipmentScheduledMessage2);
		assertSameShipment(shipmentScheduledMessage1.getShipment(), shipmentScheduledMessage2.getShipment(), message);
	}
	
	public static void assertSameShipmentScheduledMessage(Collection<ShipmentScheduledMessage> shipmentScheduledMessageList1, Collection<ShipmentScheduledMessage> shipmentScheduledMessageList2) {
		assertSameShipmentScheduledMessage(shipmentScheduledMessageList1, shipmentScheduledMessageList2, "");
	}
	
	public static void assertSameShipmentScheduledMessage(Collection<ShipmentScheduledMessage> shipmentScheduledMessageList1, Collection<ShipmentScheduledMessage> shipmentScheduledMessageList2, String message) {
		Assert.notNull(shipmentScheduledMessageList1, "ShipmentScheduledMessage list1 must be specified");
		Assert.notNull(shipmentScheduledMessageList2, "ShipmentScheduledMessage list2 must be specified");
		Assert.equals(shipmentScheduledMessageList1.size(), shipmentScheduledMessageList2.size(), "ShipmentScheduledMessage count not equal");
		Collection<ShipmentScheduledMessage> sortedRecords1 = MessageUtil.sortRecords(shipmentScheduledMessageList1);
		Collection<ShipmentScheduledMessage> sortedRecords2 = MessageUtil.sortRecords(shipmentScheduledMessageList2);
		Iterator<ShipmentScheduledMessage> list1Iterator = sortedRecords1.iterator();
		Iterator<ShipmentScheduledMessage> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			ShipmentScheduledMessage shipmentScheduledMessage1 = list1Iterator.next();
			ShipmentScheduledMessage shipmentScheduledMessage2 = list2Iterator.next();
			assertSameShipmentScheduledMessage(shipmentScheduledMessage1, shipmentScheduledMessage2, message);
		}
	}
	
	public static ShipmentCompleteMessage createEmpty_ShipmentCompleteMessage() {
		ShipmentCompleteMessage shipmentCompleteMessage = new ShipmentCompleteMessage();
		shipmentCompleteMessageCount++;
		return shipmentCompleteMessage;
	}
	
	public static ShipmentCompleteMessage create_ShipmentCompleteMessage() {
		ShipmentCompleteMessage shipmentCompleteMessage = create_ShipmentCompleteMessage(false, false);
		return shipmentCompleteMessage;
	}
	
	public static ShipmentCompleteMessage create_ShipmentCompleteMessage(long index) {
		ShipmentCompleteMessage shipmentCompleteMessage = create_ShipmentCompleteMessage(false, false, index);
		return shipmentCompleteMessage;
	}
	
	public static ShipmentCompleteMessage create_ShipmentCompleteMessage(boolean cancel, boolean undo) {
		ShipmentCompleteMessage shipmentCompleteMessage = create_ShipmentCompleteMessage(cancel, undo, 1);
		return shipmentCompleteMessage;
	}
	
	public static ShipmentCompleteMessage create_ShipmentCompleteMessage(boolean cancel, boolean undo, long index) {
		ShipmentCompleteMessage shipmentCompleteMessage = createEmpty_ShipmentCompleteMessage();
		long value = createValue(index, shipmentCompleteMessageCount);
		shipmentCompleteMessage.setShipment(create_Shipment(value));
		shipmentCompleteMessage.setInvoice(create_Invoice(value));
		shipmentCompleteMessage.setCancelRequest(cancel);
		shipmentCompleteMessage.setUndoRequest(undo);
		return shipmentCompleteMessage;
	}
	
	public static List<ShipmentCompleteMessage> createEmptyList_ShipmentCompleteMessage() {
		return new ArrayList<ShipmentCompleteMessage>();
	}
	
	public static List<ShipmentCompleteMessage> createList_ShipmentCompleteMessage() {
		return createList_ShipmentCompleteMessage(2);
	}
	
	public static List<ShipmentCompleteMessage> createList_ShipmentCompleteMessage(int size) {
		return createList_ShipmentCompleteMessage(1, size);
	}
	
	public static List<ShipmentCompleteMessage> createList_ShipmentCompleteMessage(long index, int size) {
		List<ShipmentCompleteMessage> shipmentCompleteMessageList = createEmptyList_ShipmentCompleteMessage();
		long limit = index + size;
		for (; index < limit; index++)
			shipmentCompleteMessageList.add(create_ShipmentCompleteMessage(index));
		return shipmentCompleteMessageList;
	}
	
	public static Set<ShipmentCompleteMessage> createEmptySet_ShipmentCompleteMessage() {
		return new HashSet<ShipmentCompleteMessage>();
	}
	
	public static Set<ShipmentCompleteMessage> createSet_ShipmentCompleteMessage() {
		return createSet_ShipmentCompleteMessage(2);
	}
	
	public static Set<ShipmentCompleteMessage> createSet_ShipmentCompleteMessage(int size) {
		return createSet_ShipmentCompleteMessage(1, size);
	}
	
	public static Set<ShipmentCompleteMessage> createSet_ShipmentCompleteMessage(long index, int size) {
		Set<ShipmentCompleteMessage> shipmentCompleteMessageSet = createEmptySet_ShipmentCompleteMessage();
		long limit = index + size;
		for (; index < limit; index++)
			shipmentCompleteMessageSet.add(create_ShipmentCompleteMessage(index));
		return shipmentCompleteMessageSet;
	}
	
	public static void assertShipmentCompleteMessageExists(Collection<ShipmentCompleteMessage> shipmentCompleteMessageList, ShipmentCompleteMessage shipmentCompleteMessage) {
		Assert.notNull(shipmentCompleteMessageList, "ShipmentCompleteMessage list must be specified");
		Assert.notNull(shipmentCompleteMessage, "ShipmentCompleteMessage record must be specified");
		Iterator<ShipmentCompleteMessage> iterator = shipmentCompleteMessageList.iterator();
		while (iterator.hasNext()) {
			ShipmentCompleteMessage shipmentCompleteMessage1 = iterator.next();
			if (shipmentCompleteMessage1.equals(shipmentCompleteMessage)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - ShipmentCompleteMessage should exist: "+shipmentCompleteMessage);
	}
	
	public static void assertShipmentCompleteMessageCorrect(ShipmentCompleteMessage shipmentCompleteMessage) {
		assertShipmentCorrect(shipmentCompleteMessage.getShipment());
		assertInvoiceCorrect(shipmentCompleteMessage.getInvoice());
	}
	
	public static void assertShipmentCompleteMessageCorrect(Collection<ShipmentCompleteMessage> shipmentCompleteMessageList) {
		Assert.isTrue(shipmentCompleteMessageList.size() == 2, "ShipmentCompleteMessage count not correct");
		Iterator<ShipmentCompleteMessage> iterator = shipmentCompleteMessageList.iterator();
		while (iterator.hasNext()) {
			ShipmentCompleteMessage shipmentCompleteMessage = iterator.next();
			assertShipmentCompleteMessageCorrect(shipmentCompleteMessage);
		}
	}
	
	public static void assertSameShipmentCompleteMessage(ShipmentCompleteMessage shipmentCompleteMessage1, ShipmentCompleteMessage shipmentCompleteMessage2) {
		assertSameShipmentCompleteMessage(shipmentCompleteMessage1, shipmentCompleteMessage2, "");
	}
	
	public static void assertSameShipmentCompleteMessage(ShipmentCompleteMessage shipmentCompleteMessage1, ShipmentCompleteMessage shipmentCompleteMessage2, String message) {
		assertObjectExists("ShipmentCompleteMessage1", shipmentCompleteMessage1);
		assertObjectExists("ShipmentCompleteMessage2", shipmentCompleteMessage2);
		assertSameShipment(shipmentCompleteMessage1.getShipment(), shipmentCompleteMessage2.getShipment(), message);
		assertSameInvoice(shipmentCompleteMessage1.getInvoice(), shipmentCompleteMessage2.getInvoice(), message);
	}
	
	public static void assertSameShipmentCompleteMessage(Collection<ShipmentCompleteMessage> shipmentCompleteMessageList1, Collection<ShipmentCompleteMessage> shipmentCompleteMessageList2) {
		assertSameShipmentCompleteMessage(shipmentCompleteMessageList1, shipmentCompleteMessageList2, "");
	}
	
	public static void assertSameShipmentCompleteMessage(Collection<ShipmentCompleteMessage> shipmentCompleteMessageList1, Collection<ShipmentCompleteMessage> shipmentCompleteMessageList2, String message) {
		Assert.notNull(shipmentCompleteMessageList1, "ShipmentCompleteMessage list1 must be specified");
		Assert.notNull(shipmentCompleteMessageList2, "ShipmentCompleteMessage list2 must be specified");
		Assert.equals(shipmentCompleteMessageList1.size(), shipmentCompleteMessageList2.size(), "ShipmentCompleteMessage count not equal");
		Collection<ShipmentCompleteMessage> sortedRecords1 = MessageUtil.sortRecords(shipmentCompleteMessageList1);
		Collection<ShipmentCompleteMessage> sortedRecords2 = MessageUtil.sortRecords(shipmentCompleteMessageList2);
		Iterator<ShipmentCompleteMessage> list1Iterator = sortedRecords1.iterator();
		Iterator<ShipmentCompleteMessage> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			ShipmentCompleteMessage shipmentCompleteMessage1 = list1Iterator.next();
			ShipmentCompleteMessage shipmentCompleteMessage2 = list2Iterator.next();
			assertSameShipmentCompleteMessage(shipmentCompleteMessage1, shipmentCompleteMessage2, message);
		}
	}
	
	public static ShipmentFailedMessage createEmpty_ShipmentFailedMessage() {
		ShipmentFailedMessage shipmentFailedMessage = new ShipmentFailedMessage();
		shipmentFailedMessageCount++;
		return shipmentFailedMessage;
	}
	
	public static ShipmentFailedMessage create_ShipmentFailedMessage() {
		ShipmentFailedMessage shipmentFailedMessage = create_ShipmentFailedMessage(false, false);
		return shipmentFailedMessage;
	}
	
	public static ShipmentFailedMessage create_ShipmentFailedMessage(long index) {
		ShipmentFailedMessage shipmentFailedMessage = create_ShipmentFailedMessage(false, false, index);
		return shipmentFailedMessage;
	}
	
	public static ShipmentFailedMessage create_ShipmentFailedMessage(boolean cancel, boolean undo) {
		ShipmentFailedMessage shipmentFailedMessage = create_ShipmentFailedMessage(cancel, undo, 1);
		return shipmentFailedMessage;
	}
	
	public static ShipmentFailedMessage create_ShipmentFailedMessage(boolean cancel, boolean undo, long index) {
		ShipmentFailedMessage shipmentFailedMessage = createEmpty_ShipmentFailedMessage();
		long value = createValue(index, shipmentFailedMessageCount);
		shipmentFailedMessage.setReason("dummyReason" + value);
		shipmentFailedMessage.setShipment(create_Shipment(value));
		shipmentFailedMessage.setCancelRequest(cancel);
		shipmentFailedMessage.setUndoRequest(undo);
		return shipmentFailedMessage;
	}
	
	public static List<ShipmentFailedMessage> createEmptyList_ShipmentFailedMessage() {
		return new ArrayList<ShipmentFailedMessage>();
	}
	
	public static List<ShipmentFailedMessage> createList_ShipmentFailedMessage() {
		return createList_ShipmentFailedMessage(2);
	}
	
	public static List<ShipmentFailedMessage> createList_ShipmentFailedMessage(int size) {
		return createList_ShipmentFailedMessage(1, size);
	}
	
	public static List<ShipmentFailedMessage> createList_ShipmentFailedMessage(long index, int size) {
		List<ShipmentFailedMessage> shipmentFailedMessageList = createEmptyList_ShipmentFailedMessage();
		long limit = index + size;
		for (; index < limit; index++)
			shipmentFailedMessageList.add(create_ShipmentFailedMessage(index));
		return shipmentFailedMessageList;
	}
	
	public static Set<ShipmentFailedMessage> createEmptySet_ShipmentFailedMessage() {
		return new HashSet<ShipmentFailedMessage>();
	}
	
	public static Set<ShipmentFailedMessage> createSet_ShipmentFailedMessage() {
		return createSet_ShipmentFailedMessage(2);
	}
	
	public static Set<ShipmentFailedMessage> createSet_ShipmentFailedMessage(int size) {
		return createSet_ShipmentFailedMessage(1, size);
	}
	
	public static Set<ShipmentFailedMessage> createSet_ShipmentFailedMessage(long index, int size) {
		Set<ShipmentFailedMessage> shipmentFailedMessageSet = createEmptySet_ShipmentFailedMessage();
		long limit = index + size;
		for (; index < limit; index++)
			shipmentFailedMessageSet.add(create_ShipmentFailedMessage(index));
		return shipmentFailedMessageSet;
	}
	
	public static void assertShipmentFailedMessageExists(Collection<ShipmentFailedMessage> shipmentFailedMessageList, ShipmentFailedMessage shipmentFailedMessage) {
		Assert.notNull(shipmentFailedMessageList, "ShipmentFailedMessage list must be specified");
		Assert.notNull(shipmentFailedMessage, "ShipmentFailedMessage record must be specified");
		Iterator<ShipmentFailedMessage> iterator = shipmentFailedMessageList.iterator();
		while (iterator.hasNext()) {
			ShipmentFailedMessage shipmentFailedMessage1 = iterator.next();
			if (shipmentFailedMessage1.equals(shipmentFailedMessage)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - ShipmentFailedMessage should exist: "+shipmentFailedMessage);
	}
	
	public static void assertShipmentFailedMessageCorrect(ShipmentFailedMessage shipmentFailedMessage) {
		assertObjectCorrect("Reason", shipmentFailedMessage.getReason());
		assertShipmentCorrect(shipmentFailedMessage.getShipment());
	}
	
	public static void assertShipmentFailedMessageCorrect(Collection<ShipmentFailedMessage> shipmentFailedMessageList) {
		Assert.isTrue(shipmentFailedMessageList.size() == 2, "ShipmentFailedMessage count not correct");
		Iterator<ShipmentFailedMessage> iterator = shipmentFailedMessageList.iterator();
		while (iterator.hasNext()) {
			ShipmentFailedMessage shipmentFailedMessage = iterator.next();
			assertShipmentFailedMessageCorrect(shipmentFailedMessage);
		}
	}
	
	public static void assertSameShipmentFailedMessage(ShipmentFailedMessage shipmentFailedMessage1, ShipmentFailedMessage shipmentFailedMessage2) {
		assertSameShipmentFailedMessage(shipmentFailedMessage1, shipmentFailedMessage2, "");
	}
	
	public static void assertSameShipmentFailedMessage(ShipmentFailedMessage shipmentFailedMessage1, ShipmentFailedMessage shipmentFailedMessage2, String message) {
		assertObjectExists("ShipmentFailedMessage1", shipmentFailedMessage1);
		assertObjectExists("ShipmentFailedMessage2", shipmentFailedMessage2);
		assertObjectEquals("Reason", shipmentFailedMessage1.getReason(), shipmentFailedMessage2.getReason(), message);
		assertSameShipment(shipmentFailedMessage1.getShipment(), shipmentFailedMessage2.getShipment(), message);
	}
	
	public static void assertSameShipmentFailedMessage(Collection<ShipmentFailedMessage> shipmentFailedMessageList1, Collection<ShipmentFailedMessage> shipmentFailedMessageList2) {
		assertSameShipmentFailedMessage(shipmentFailedMessageList1, shipmentFailedMessageList2, "");
	}
	
	public static void assertSameShipmentFailedMessage(Collection<ShipmentFailedMessage> shipmentFailedMessageList1, Collection<ShipmentFailedMessage> shipmentFailedMessageList2, String message) {
		Assert.notNull(shipmentFailedMessageList1, "ShipmentFailedMessage list1 must be specified");
		Assert.notNull(shipmentFailedMessageList2, "ShipmentFailedMessage list2 must be specified");
		Assert.equals(shipmentFailedMessageList1.size(), shipmentFailedMessageList2.size(), "ShipmentFailedMessage count not equal");
		Collection<ShipmentFailedMessage> sortedRecords1 = MessageUtil.sortRecords(shipmentFailedMessageList1);
		Collection<ShipmentFailedMessage> sortedRecords2 = MessageUtil.sortRecords(shipmentFailedMessageList2);
		Iterator<ShipmentFailedMessage> list1Iterator = sortedRecords1.iterator();
		Iterator<ShipmentFailedMessage> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			ShipmentFailedMessage shipmentFailedMessage1 = list1Iterator.next();
			ShipmentFailedMessage shipmentFailedMessage2 = list2Iterator.next();
			assertSameShipmentFailedMessage(shipmentFailedMessage1, shipmentFailedMessage2, message);
		}
	}

	public static boolean containsBook(Collection<Book> bookList, Book book) {
		Iterator<Book> iterator = bookList.iterator();
		while (iterator.hasNext()) {
			Book book2 = iterator.next();
			try {
				assertSameBook(book2, book);
				return true;
			} catch (Throwable e) {
				continue;
			}
		}
		return false;
	}

}
