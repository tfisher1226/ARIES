package nam.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import nam.model.Timeout;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class TimeoutUtil extends BaseUtil {
	
	public static Object getKey(Timeout timeout) {
		return timeout.getName() + " " + timeout.getValue(); 
	}

	public static String getLabel(Timeout timeout) {
		return timeout.getName() + " " + timeout.getValue(); 
	}

	public static String getLabel(Collection<Timeout> timeoutList) {
		return null;
	}
	
	public static boolean isEmpty(Timeout timeout) {
		if (timeout == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Timeout> timeoutList) {
		if (timeoutList == null  || timeoutList.size() == 0)
			return true;
		Iterator<Timeout> iterator = timeoutList.iterator();
		while (iterator.hasNext()) {
			Timeout timeout = iterator.next();
			if (!isEmpty(timeout))
				return false;
		}
		return true;
	}
	
	public static String toString(Timeout timeout) {
		if (isEmpty(timeout))
			return "Timeout: [uninitialized] "+timeout.toString();
		String text = timeout.toString();
		return text;
	}
	
	public static String toString(Collection<Timeout> timeoutList) {
		if (isEmpty(timeoutList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Timeout> iterator = timeoutList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Timeout timeout = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(timeout);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Timeout create() {
		Timeout timeout = new Timeout();
		initialize(timeout);
		return timeout;
	}
	
	public static void initialize(Timeout timeout) {
		//nothing for now
	}
	
	public static boolean validate(Timeout timeout) {
		if (timeout == null)
			return false;
		Validator validator = Validator.getValidator();
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Timeout> timeoutList) {
		Validator validator = Validator.getValidator();
		Iterator<Timeout> iterator = timeoutList.iterator();
		while (iterator.hasNext()) {
			Timeout timeout = iterator.next();
			//TODO break or accumulate?
			validate(timeout);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Timeout> timeoutList) {
		Collections.sort(timeoutList, createTimeoutComparator());
	}
	
	public static Collection<Timeout> sortRecords(Collection<Timeout> timeoutCollection) {
		List<Timeout> list = new ArrayList<Timeout>(timeoutCollection);
		Collections.sort(list, createTimeoutComparator());
		return list;
	}
	
	public static Comparator<Timeout> createTimeoutComparator() {
		return new Comparator<Timeout>() {
			public int compare(Timeout timeout1, Timeout timeout2) {
				Object key1 = getKey(timeout1);
				Object key2 = getKey(timeout2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Timeout clone(Timeout timeout) {
		if (timeout == null)
			return null;
		Timeout clone = create();
		clone.setValue(ObjectUtil.clone(timeout.getValue()));
		clone.setName(ObjectUtil.clone(timeout.getName()));
		return clone;
	}
	
	public static List<Timeout> clone(List<Timeout> timeoutList) {
		if (timeoutList == null)
			return null;
		List<Timeout> newList = new ArrayList<Timeout>();
		Iterator<Timeout> iterator = timeoutList.iterator();
		while (iterator.hasNext()) {
			Timeout timeout = iterator.next();
			Timeout clone = clone(timeout);
			newList.add(clone);
		}
		return newList;
	}
	
}
