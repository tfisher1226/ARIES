package nam.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import nam.model.Enumeration;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;
import org.aries.util.Validator;


public class EnumerationUtil extends BaseUtil {
	
	public static Object getKey(Enumeration enumeration) {
		return enumeration.getName();
	}
	
	public static String getLabel(Enumeration enumeration) {
		return enumeration.getName();
	}
	
	public static boolean isEmpty(Enumeration enumeration) {
		if (enumeration == null)
			return true;
		boolean status = false;
		status |= LiteralUtil.isEmpty(enumeration.getLiterals());
		return status;
	}
	
	public static boolean isEmpty(Collection<Enumeration> enumerationList) {
		if (enumerationList == null  || enumerationList.size() == 0)
			return true;
		Iterator<Enumeration> iterator = enumerationList.iterator();
		while (iterator.hasNext()) {
			Enumeration enumeration = iterator.next();
			if (!isEmpty(enumeration))
				return false;
		}
		return true;
	}
	
	public static String toString(Enumeration enumeration) {
		if (isEmpty(enumeration))
			return "Enumeration: [uninitialized] "+enumeration.toString();
		String text = enumeration.toString();
		return text;
	}
	
	public static String toString(Collection<Enumeration> enumerationList) {
		if (isEmpty(enumerationList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Enumeration> iterator = enumerationList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Enumeration enumeration = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(enumeration);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Enumeration create() {
		Enumeration enumeration = new Enumeration();
		initialize(enumeration);
		return enumeration;
	}
	
	public static void initialize(Enumeration enumeration) {
		//nothing for now
	}
	
	public static boolean validate(Enumeration enumeration) {
		if (enumeration == null) 
			return false;
		Validator validator = Validator.getValidator();
		validator.isFalse(LiteralUtil.isEmpty(enumeration.getLiterals()), "At least one of \"Literals\" must be specified");
		LiteralUtil.validate(enumeration.getLiterals());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Enumeration> enumerationList) {
		Validator validator = Validator.getValidator();
		Iterator<Enumeration> iterator = enumerationList.iterator();
		while (iterator.hasNext()) {
			Enumeration enumeration = iterator.next();
			//TODO break or accumulate?
			validate(enumeration);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Enumeration> enumerationList) {
		Collections.sort(enumerationList, createEnumerationComparator());
	}
	
	public static Collection<Enumeration> sortRecords(Collection<Enumeration> enumerationCollection) {
		List<Enumeration> list = new ArrayList<Enumeration>(enumerationCollection);
		Collections.sort(list, createEnumerationComparator());
		return list;
	}
	
	public static Comparator<Enumeration> createEnumerationComparator() {
		return new Comparator<Enumeration>() {
			public int compare(Enumeration enumeration1, Enumeration enumeration2) {
				Object key1 = getKey(enumeration1);
				Object key2 = getKey(enumeration2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Enumeration clone(Enumeration enumeration) {
		if (enumeration == null)
			return null;
		Enumeration clone = create();
		clone.getLiterals().addAll(LiteralUtil.clone(enumeration.getLiterals()));
		clone.setDefault(enumeration.getDefault());
		return clone;
	}
	
}
