package nam.model.util;

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

import nam.model.Literal;


public class LiteralUtil extends BaseUtil {
	
	public static Object getKey(Literal literal) {
		return literal.getName();
	}
	
	public static String getLabel(Literal literal) {
		return literal.getName();
	}
	
	public static boolean isEmpty(Literal literal) {
		if (literal == null)
			return true;
		boolean status = false;
		status |= StringUtils.isEmpty(literal.getName());
		status |= StringUtils.isEmpty(literal.getLabel());
		return status;
	}
	
	public static boolean isEmpty(Collection<Literal> literalList) {
		if (literalList == null  || literalList.size() == 0)
			return true;
		Iterator<Literal> iterator = literalList.iterator();
		while (iterator.hasNext()) {
			Literal literal = iterator.next();
			if (!isEmpty(literal))
				return false;
		}
		return true;
	}
	
	public static String toString(Literal literal) {
		if (isEmpty(literal))
			return "Literal: [uninitialized] "+literal.toString();
		String text = literal.toString();
		return text;
	}
	
	public static String toString(Collection<Literal> literalList) {
		if (isEmpty(literalList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Literal> iterator = literalList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Literal literal = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(literal);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Literal create() {
		Literal literal = new Literal();
		initialize(literal);
		return literal;
	}
	
	public static void initialize(Literal literal) {
		//nothing for now
	}
	
	public static boolean validate(Literal literal) {
		if (literal == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.notEmpty(literal.getLabel(), "\"Label\" must be specified");
		validator.notEmpty(literal.getName(), "\"Name\" must be specified");
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Literal> literalList) {
		Validator validator = Validator.getValidator();
		Iterator<Literal> iterator = literalList.iterator();
		while (iterator.hasNext()) {
			Literal literal = iterator.next();
			//TODO break or accumulate?
			validate(literal);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Literal> literalList) {
		Collections.sort(literalList, createLiteralComparator());
	}
	
	public static Collection<Literal> sortRecords(Collection<Literal> literalCollection) {
		List<Literal> list = new ArrayList<Literal>(literalCollection);
		Collections.sort(list, createLiteralComparator());
		return list;
	}
	
	public static Comparator<Literal> createLiteralComparator() {
		return new Comparator<Literal>() {
			public int compare(Literal literal1, Literal literal2) {
				Object key1 = getKey(literal1);
				Object key2 = getKey(literal2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}

	public static Literal clone(String value) {
		Literal literal = new Literal();
		literal.setName(value);
		literal.setLabel(value);
		return literal;
	}
	
	public static Literal clone(Literal literal) {
		if (literal == null)
			return null;
		Literal clone = create();
		clone.setName(ObjectUtil.clone(literal.getName()));
		clone.setLabel(ObjectUtil.clone(literal.getLabel()));
		clone.setValue(ObjectUtil.clone(literal.getValue()));
		return clone;
	}
	
	public static List<Literal> clone(List<Literal> literalList) {
		if (literalList == null)
			return null;
		List<Literal> newList = new ArrayList<Literal>();
		Iterator<Literal> iterator = literalList.iterator();
		while (iterator.hasNext()) {
			Literal literal = iterator.next();
			Literal clone = clone(literal);
			newList.add(clone);
		}
		return newList;
	}
	
}
