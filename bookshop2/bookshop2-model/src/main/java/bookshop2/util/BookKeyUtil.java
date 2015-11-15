package bookshop2.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;

import bookshop2.BookKey;


public class BookKeyUtil extends BaseUtil {
	
	public static boolean isEmpty(BookKey bookKey) {
		if (bookKey == null)
			return true;
		boolean status = false;
		status |= StringUtils.isEmpty(bookKey.getAuthor());
		status |= StringUtils.isEmpty(bookKey.getTitle());
		return status;
	}
	
	public static boolean isEmpty(Collection<BookKey> bookKeyList) {
		if (bookKeyList == null  || bookKeyList.size() == 0)
			return true;
		Iterator<BookKey> iterator = bookKeyList.iterator();
		while (iterator.hasNext()) {
			BookKey bookKey = iterator.next();
			if (!isEmpty(bookKey))
				return false;
		}
		return true;
	}
	
	public static String toString(BookKey bookKey) {
		if (isEmpty(bookKey))
			return "BookKey: [uninitialized] "+bookKey.toString();
		String text = bookKey.toString();
		return text;
	}
	
	public static String toString(Collection<BookKey> bookKeyList) {
		if (isEmpty(bookKeyList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<BookKey> iterator = bookKeyList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			BookKey bookKey = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(bookKey);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static BookKey create() {
		BookKey bookKey = new BookKey();
		initialize(bookKey);
		return bookKey;
	}
	
	public static void initialize(BookKey bookKey) {
		//nothing for now
	}
	
	public static boolean validate(BookKey bookKey) {
		if (bookKey == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.notEmpty(bookKey.getAuthor(), "\"Author\" must be specified");
		validator.notEmpty(bookKey.getTitle(), "\"Title\" must be specified");
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<BookKey> bookKeyList) {
		Validator validator = Validator.getValidator();
		Iterator<BookKey> iterator = bookKeyList.iterator();
		while (iterator.hasNext()) {
			BookKey bookKey = iterator.next();
			//TODO break or accumulate?
			validate(bookKey);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<BookKey> bookKeyList) {
		Collections.sort(bookKeyList, createBookKeyComparator());
	}
	
	public static Collection<BookKey> sortRecords(Collection<BookKey> bookKeyCollection) {
		List<BookKey> list = new ArrayList<BookKey>(bookKeyCollection);
		Collections.sort(list, createBookKeyComparator());
		return list;
	}
	
	public static Comparator<BookKey> createBookKeyComparator() {
		return new Comparator<BookKey>() {
			public int compare(BookKey bookKey1, BookKey bookKey2) {
				int status = bookKey1.compareTo(bookKey2);
				return status;
			}
		};
	}
	
	public static BookKey clone(BookKey bookKey) {
		if (bookKey == null)
			return null;
		BookKey clone = create();
		clone.setAuthor(ObjectUtil.clone(bookKey.getAuthor()));
		clone.setTitle(ObjectUtil.clone(bookKey.getTitle()));
		return clone;
	}
	
}
