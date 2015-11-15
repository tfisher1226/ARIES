package nam.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;

import nam.model.Transacted;


public class TransactedUtil extends BaseUtil {
	
	public static String getKey(Transacted transacted) {
		return transacted.toString();
	}

	public static String getLabel(Transacted transacted) {
		return transacted.getScope()+" - "+transacted.getUse();
	}

	
	public static boolean getLabel(Collection<Transacted> transactedList) {
		if (transactedList == null  || transactedList.size() == 0)
			return true;
		Iterator<Transacted> iterator = transactedList.iterator();
		while (iterator.hasNext()) {
			Transacted transacted = iterator.next();
			if (!isEmpty(transacted))
				return false;
		}
		return true;
	}
	
	public static boolean isEmpty(Transacted transacted) {
		if (transacted == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Transacted> transactedList) {
		if (transactedList == null  || transactedList.size() == 0)
			return true;
		Iterator<Transacted> iterator = transactedList.iterator();
		while (iterator.hasNext()) {
			Transacted transacted = iterator.next();
			if (!isEmpty(transacted))
				return false;
		}
		return true;
	}
	
	public static String toString(Transacted transacted) {
		if (isEmpty(transacted))
			return "Transacted: [uninitialized] "+transacted.toString();
		String text = transacted.toString();
		return text;
	}
	
	public static String toString(Collection<Transacted> transactedList) {
		if (isEmpty(transactedList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Transacted> iterator = transactedList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Transacted transacted = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(transacted);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Transacted create() {
		Transacted transacted = new Transacted();
		initialize(transacted);
		return transacted;
	}
	
	public static void initialize(Transacted transacted) {
		if (transacted.getLocal() == null)
			transacted.setLocal(false);
	}
	
	public static boolean validate(Transacted transacted) {
		if (transacted == null)
			return false;
		Validator validator = Validator.getValidator();
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Transacted> transactedList) {
		Validator validator = Validator.getValidator();
		Iterator<Transacted> iterator = transactedList.iterator();
		while (iterator.hasNext()) {
			Transacted transacted = iterator.next();
			//TODO break or accumulate?
			validate(transacted);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Transacted> transactedList) {
		Collections.sort(transactedList, createTransactedComparator());
	}
	
	public static Collection<Transacted> sortRecords(Collection<Transacted> transactedCollection) {
		List<Transacted> list = new ArrayList<Transacted>(transactedCollection);
		Collections.sort(list, createTransactedComparator());
		return list;
	}
	
	public static Comparator<Transacted> createTransactedComparator() {
		return new Comparator<Transacted>() {
			public int compare(Transacted transacted1, Transacted transacted2) {
				Object key1 = getKey(transacted1);
				Object key2 = getKey(transacted2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Transacted clone(Transacted transacted) {
		if (transacted == null)
			return null;
		Transacted clone = create();
		clone.setLocal(ObjectUtil.clone(transacted.getLocal()));
		clone.setUse(transacted.getUse());
		clone.setScope(transacted.getScope());
		return clone;
	}
	
}
