package bookshop2.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;

import bookshop2.Book;
import bookshop2.BookKey;


public class BookUtil extends BaseUtil {
	
	public static boolean isEmpty(Book book) {
		if (book == null)
			return true;
		boolean status = false;
		status |= StringUtils.isEmpty(book.getAuthor());
		status |= StringUtils.isEmpty(book.getTitle());
		return status;
	}
	
	public static boolean isEmpty(Collection<Book> bookList) {
		if (bookList == null  || bookList.size() == 0)
			return true;
		Iterator<Book> iterator = bookList.iterator();
		while (iterator.hasNext()) {
			Book book = iterator.next();
			if (!isEmpty(book))
				return false;
		}
		return true;
	}
	
	public static String toString(Book book) {
		if (isEmpty(book))
			return "Book: [uninitialized] "+book.toString();
		String text = book.toString();
		return text;
	}
	
	public static String toString(Collection<Book> bookList) {
		if (isEmpty(bookList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Book> iterator = bookList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Book book = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(book);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Book create() {
		Book book = new Book();
		initialize(book);
		return book;
	}
	
	public static void initialize(Book book) {
		//nothing for now
	}
	
	public static boolean validate(Book book) {
		if (book == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.notEmpty(book.getAuthor(), "\"Author\" must be specified");
		validator.notNull(book.getBarCode(), "\"BarCode\" must be specified");
		validator.notNull(book.getPrice(), "\"Price\" must be specified");
		validator.notEmpty(book.getTitle(), "\"Title\" must be specified");
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Book> bookList) {
		Validator validator = Validator.getValidator();
		Iterator<Book> iterator = bookList.iterator();
		while (iterator.hasNext()) {
			Book book = iterator.next();
			//TODO break or accumulate?
			validate(book);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Book> bookList) {
		Collections.sort(bookList, createBookComparator());
	}
	
	public static Collection<Book> sortRecords(Collection<Book> bookCollection) {
		List<Book> list = new ArrayList<Book>(bookCollection);
		Collections.sort(list, createBookComparator());
		return list;
	}

	public static void sortRecordsByBarCode(List<Book> bookList) {
		Collections.sort(bookList, new Comparator<Book>() {
			public int compare(Book book1, Book book2) {
				Long barCode1 = book1.getBarCode();
				Long barCode2 = book2.getBarCode();
				if (barCode1 == null && barCode2 == null)
					return 0;
				if (barCode1 == null)
					return -1;
				if (barCode2 == null)
					return 1;
				return barCode1.compareTo(barCode2);
			}
		});
	}
	
	public static Comparator<Book> createBookComparator() {
		return new Comparator<Book>() {
			public int compare(Book book1, Book book2) {
				int status = book1.compareTo(book2);
				return status;
			}
		};
	}
	
	public static Book clone(Book book) {
		if (book == null)
			return null;
		Book clone = create();
		clone.setId(ObjectUtil.clone(book.getId()));
		clone.setBarCode(ObjectUtil.clone(book.getBarCode()));
		clone.setAuthor(ObjectUtil.clone(book.getAuthor()));
		clone.setTitle(ObjectUtil.clone(book.getTitle()));
		clone.setPrice(ObjectUtil.clone(book.getPrice()));
		clone.setQuantity(ObjectUtil.clone(book.getQuantity()));
		return clone;
	}
	
	public static List<Book> clone(List<Book> bookList) {
		if (bookList == null)
			return null;
		List<Book> newList = new ArrayList<Book>();
		Iterator<Book> iterator = bookList.iterator();
		while (iterator.hasNext()) {
			Book book = iterator.next();
			Book clone = clone(book);
			newList.add(clone);
		}
		return newList;
	}
	
	public static Set<Book> clone(Set<Book> bookSet) {
		if (bookSet == null)
			return null;
		Set<Book> newSet = new HashSet<Book>();
		Iterator<Book> iterator = bookSet.iterator();
		while (iterator.hasNext()) {
			Book book = iterator.next();
			Book clone = clone(book);
			newSet.add(clone);
		}
		return newSet;
	}
	
	public static Map<BookKey, Book> clone(Map<BookKey, Book> bookMap) {
		if (bookMap == null)
			return null;
		Map<BookKey, Book> newMap = new HashMap<BookKey, Book>();
		Iterator<BookKey> iterator = bookMap.keySet().iterator();
		while (iterator.hasNext()) {
			BookKey key = iterator.next();
			BookKey keyClone = clone(key);
			Book book = bookMap.get(key);
			Book clone = clone(book);
			newMap.put(keyClone, clone);
		}
		return newMap;
	}

	public static BookKey clone(BookKey bookKey) {
		if (bookKey == null)
			return null;
		BookKey clone = new BookKey();
		clone.setAuthor(ObjectUtil.clone(bookKey.getAuthor()));
		clone.setTitle(ObjectUtil.clone(bookKey.getTitle()));
		return clone;
	}
	
}
