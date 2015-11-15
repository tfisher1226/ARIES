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

import bookshop2.BookCriteria;


public class BookCriteriaUtil extends BaseUtil {
	
	public static boolean isEmpty(BookCriteria bookCriteria) {
		if (bookCriteria == null)
			return true;
		boolean status = false;
		status |= StringUtils.isEmpty(bookCriteria.getAuthor());
		status |= StringUtils.isEmpty(bookCriteria.getTitle());
		return status;
	}
	
	public static boolean isEmpty(Collection<BookCriteria> bookCriteriaList) {
		if (bookCriteriaList == null  || bookCriteriaList.size() == 0)
			return true;
		Iterator<BookCriteria> iterator = bookCriteriaList.iterator();
		while (iterator.hasNext()) {
			BookCriteria bookCriteria = iterator.next();
			if (!isEmpty(bookCriteria))
				return false;
		}
		return true;
	}
	
	public static String toString(BookCriteria bookCriteria) {
		if (isEmpty(bookCriteria))
			return "BookCriteria: [uninitialized] "+bookCriteria.toString();
		String text = bookCriteria.toString();
		return text;
	}
	
	public static String toString(Collection<BookCriteria> bookCriteriaList) {
		if (isEmpty(bookCriteriaList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<BookCriteria> iterator = bookCriteriaList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			BookCriteria bookCriteria = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(bookCriteria);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static BookCriteria create() {
		BookCriteria bookCriteria = new BookCriteria();
		initialize(bookCriteria);
		return bookCriteria;
	}
	
	public static void initialize(BookCriteria bookCriteria) {
		//nothing for now
	}
	
	public static boolean validate(BookCriteria bookCriteria) {
		if (bookCriteria == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.notEmpty(bookCriteria.getAuthor(), "\"Author\" must be specified");
		validator.notNull(bookCriteria.getPrice(), "\"Price\" must be specified");
		validator.notEmpty(bookCriteria.getTitle(), "\"Title\" must be specified");
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<BookCriteria> bookCriteriaList) {
		Validator validator = Validator.getValidator();
		Iterator<BookCriteria> iterator = bookCriteriaList.iterator();
		while (iterator.hasNext()) {
			BookCriteria bookCriteria = iterator.next();
			//TODO break or accumulate?
			validate(bookCriteria);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static BookCriteria clone(BookCriteria bookCriteria) {
		if (bookCriteria == null)
			return null;
		BookCriteria clone = create();
		clone.setAuthor(ObjectUtil.clone(bookCriteria.getAuthor()));
		clone.setTitle(ObjectUtil.clone(bookCriteria.getTitle()));
		clone.setPrice(ObjectUtil.clone(bookCriteria.getPrice()));
		clone.setQuantity(ObjectUtil.clone(bookCriteria.getQuantity()));
		return clone;
	}
	
}
