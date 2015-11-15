package bookshop2.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.aries.Assert;
import org.aries.util.AbstractModelHelper;

import bookshop2.Book;
import bookshop2.BookCriteria;
import bookshop2.BookKey;
import bookshop2.Order;
import bookshop2.OrderCriteria;


public class Bookshop2Helper extends AbstractModelHelper {

	public static BookKey createBookKey(Book book) {
		BookKey bookKey = new BookKey();
		bookKey.setAuthor(book.getAuthor());
		bookKey.setTitle(book.getTitle());
		return bookKey;
	}

	public static Collection<BookKey> createBookKeys(Collection<Book> bookList) {
		List<BookKey> list = new ArrayList<BookKey>();
		Iterator<Book> iterator = bookList.iterator();
		while (iterator.hasNext()) {
			Book book = iterator.next();
			BookKey bookKey = createBookKey(book);
			list.add(bookKey);
		}
		return list;
	}

	public static List<Book> createBookList(Map<BookKey, Book> bookMap) {
		List<Book> bookList = new ArrayList<Book>();
		bookList.addAll(bookMap.values());
		return bookList;
	}
	
	public static Map<BookKey, Book> createBookMap(Collection<Book> bookList) {
		Map<BookKey, Book> map = new LinkedHashMap<BookKey, Book>();
		Iterator<Book> iterator = bookList.iterator();
		while (iterator.hasNext()) {
			Book book = iterator.next();
			BookKey bookKey = createBookKey(book);
			map.put(bookKey, book);
		}
		return map;
	}

	public static BookCriteria createBookCriteriaFromId(Long bookId) {
		BookCriteria bookCriteria = new BookCriteria();
		addBookIdToBookCriteria(bookCriteria, bookId);
		return bookCriteria;
	}
	
	public static BookCriteria createBookCriteriaFromIds(Collection<Long> bookIds) {
		BookCriteria bookCriteria = new BookCriteria();
		Iterator<Long> iterator = bookIds.iterator();
		while (iterator.hasNext()) {
			Long bookId = iterator.next();
			addBookIdToBookCriteria(bookCriteria, bookId);
		}
		return bookCriteria;
	}
	
	public static BookCriteria createBookCriteriaFromKey(BookKey bookKey) {
		BookCriteria bookCriteria = new BookCriteria();
		addBookKeyToBookCriteria(bookCriteria, bookKey);
		return bookCriteria;
	}
	
	public static BookCriteria createBookCriteriaFromKeys(Collection<BookKey> bookKeys) {
		BookCriteria bookCriteria = new BookCriteria();
		Iterator<BookKey> iterator = bookKeys.iterator();
		while (iterator.hasNext()) {
			BookKey bookKey = iterator.next();
			addBookKeyToBookCriteria(bookCriteria, bookKey);
		}
		return bookCriteria;
	}
	
	public static BookCriteria createBookCriteria(Book book) {
		BookKey bookKey = createBookKey(book);
		BookCriteria bookCriteria = new BookCriteria();
		addBookKeyToBookCriteria(bookCriteria, bookKey);
		return bookCriteria;
	}
	
	public static BookCriteria createBookCriteria(Collection<Book> bookList) {
		BookCriteria bookCriteria = new BookCriteria();
		Iterator<Book> iterator = bookList.iterator();
		while (iterator.hasNext()) {
			Book book = iterator.next();
			BookKey bookKey = createBookKey(book);
			addBookKeyToBookCriteria(bookCriteria, bookKey);
		}
		return bookCriteria;
	}

	public static void addBookIdToBookCriteria(BookCriteria bookCriteria, Long bookId) {
		bookCriteria.getIdList().add(bookId);
	}

	public static void addBookKeyToBookCriteria(BookCriteria bookCriteria, BookKey bookKey) {
		bookCriteria.addToFieldMap("author", bookKey.getAuthor());
		bookCriteria.addToFieldMap("title", bookKey.getTitle());
	}

	public static boolean isMatch(BookCriteria bookCriteria, Book book) {
		boolean status = 
			isExists(bookCriteria.getIdList(), book.getId()) &&
			isMatch(bookCriteria.getFieldValue("author"), book.getAuthor()) &&
			isMatch(bookCriteria.getFieldValue("title"), book.getTitle()) &&
			isExists(bookCriteria.getKeyList(), createBookKey(book));
		return status;
	}
	
	public static boolean isBooksEqual(Book book1, Book book2) {
		Assert.notNull(book1, "Book1 must be specified");
		Assert.notNull(book2, "Book2 must be specified");
		if (!book1.getAuthor().equals(book2.getAuthor()))
			return false;
		if (!book1.getTitle().equals(book2.getTitle()))
			return false;
		if (!book1.getPrice().equals(book2.getPrice()))
			return false;
		if (!book1.getQuantity().equals(book2.getQuantity()))
			return false;	
		return true;
	}

	
	/*
	 * Order related
	 */
	
	public static String createOrderKey(Order order) {
		return Long.toString(order.getId());
	}

//	public static String createOrderKey(Collection<Order> orderList) {
//		String orderKey = "";
//		Iterator<Order> iterator = orderList.iterator();
//		while (iterator.hasNext()) {
//			Order order = iterator.next();
//			orderKey += createOrderKey(order);
//		}
//		return orderKey;
//	}

	public static OrderCriteria createOrderCriteria(Collection<Order> orderList) {
		OrderCriteria orderCriteria = new OrderCriteria();
		Iterator<Order> iterator = orderList.iterator();
		while (iterator.hasNext()) {
			Order order = iterator.next();
			String orderKey = createOrderKey(order);
			addOrderKeyToOrderCriteria(orderCriteria, orderKey);
		}
		return orderCriteria;
	}
	
	public static OrderCriteria createOrderCriteriaFromId(Long orderId) {
		OrderCriteria orderCriteria = new OrderCriteria();
		addOrderIdToOrderCriteria(orderCriteria, orderId);
		return orderCriteria;
	}
	
	public static OrderCriteria createOrderCriteriaFromIds(Collection<Long> orderIds) {
		OrderCriteria orderCriteria = new OrderCriteria();
		Iterator<Long> iterator = orderIds.iterator();
		while (iterator.hasNext()) {
			Long orderId = iterator.next();
			addOrderIdToOrderCriteria(orderCriteria, orderId);
		}
		return orderCriteria;
	}

	public static boolean isMatch(OrderCriteria orderCriteria, Order order) {
		boolean status = 
			isMatch(orderCriteria.getIdList(), order.getId()) &&
			isMatch(orderCriteria.getKeyList(), createOrderKey(order));
		return status;
	}
	
	public static Set<Long> getOrderIds(Collection<Order> orderList) {
		Set<Long> orderIds = new HashSet<Long>();
		Iterator<Order> iterator = orderList.iterator();
		while (iterator.hasNext()) {
			Order order = iterator.next();
			orderIds.add(order.getId());
		}
		return orderIds;
	}

	public static void addOrderIdToOrderCriteria(OrderCriteria orderCriteria, Long orderId) {
		orderCriteria.addToIdList(orderId);
	}

	public static void addOrderKeyToOrderCriteria(OrderCriteria orderCriteria, String orderKey) {
		orderCriteria.addToKeyList(orderKey);
	}

}
