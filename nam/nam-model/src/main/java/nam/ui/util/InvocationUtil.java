package nam.ui.util;

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

import nam.model.util.ServiceUtil;
import nam.ui.Invocation;


public class InvocationUtil extends BaseUtil {
	
	public static Object getKey(Invocation invocation) {
		return invocation.getName();
	}
	
	public static String getLabel(Invocation invocation) {
		return invocation.getName();
	}
	
	public static boolean isEmpty(Invocation invocation) {
		if (invocation == null)
			return true;
		boolean status = false;
		status |= StringUtils.isEmpty(invocation.getName());
		status |= ServiceUtil.isEmpty(invocation.getService());
		return status;
	}
	
	public static boolean isEmpty(Collection<Invocation> invocationList) {
		if (invocationList == null  || invocationList.size() == 0)
			return true;
		Iterator<Invocation> iterator = invocationList.iterator();
		while (iterator.hasNext()) {
			Invocation invocation = iterator.next();
			if (!isEmpty(invocation))
				return false;
		}
		return true;
	}
	
	public static String toString(Invocation invocation) {
		if (isEmpty(invocation))
			return "Invocation: [uninitialized] "+invocation.toString();
		String text = invocation.toString();
		return text;
	}
	
	public static String toString(Collection<Invocation> invocationList) {
		if (isEmpty(invocationList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Invocation> iterator = invocationList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Invocation invocation = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(invocation);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Invocation create() {
		Invocation invocation = new Invocation();
		initialize(invocation);
		return invocation;
	}
	
	public static void initialize(Invocation invocation) {
		//nothing for now
	}
	
	public static boolean validate(Invocation invocation) {
		if (invocation == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.notEmpty(invocation.getName(), "\"Name\" must be specified");
		validator.isFalse(ServiceUtil.isEmpty(invocation.getService()), "\"Service\" must be specified");
		ServiceUtil.validate(invocation.getService());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Invocation> invocationList) {
		Validator validator = Validator.getValidator();
		Iterator<Invocation> iterator = invocationList.iterator();
		while (iterator.hasNext()) {
			Invocation invocation = iterator.next();
			//TODO break or accumulate?
			validate(invocation);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Invocation> invocationList) {
		Collections.sort(invocationList, createInvocationComparator());
	}
	
	public static Collection<Invocation> sortRecords(Collection<Invocation> invocationCollection) {
		List<Invocation> list = new ArrayList<Invocation>(invocationCollection);
		Collections.sort(list, createInvocationComparator());
		return list;
	}
	
	public static Comparator<Invocation> createInvocationComparator() {
		return new Comparator<Invocation>() {
			public int compare(Invocation invocation1, Invocation invocation2) {
				Object key1 = getKey(invocation1);
				Object key2 = getKey(invocation2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Invocation clone(Invocation invocation) {
		if (invocation == null)
			return null;
		Invocation clone = create();
		clone.setName(ObjectUtil.clone(invocation.getName()));
		clone.setService(ServiceUtil.clone(invocation.getService()));
		return clone;
	}
	
	public static List<Invocation> clone(List<Invocation> invocationList) {
		if (invocationList == null)
			return null;
		List<Invocation> newList = new ArrayList<Invocation>();
		Iterator<Invocation> iterator = invocationList.iterator();
		while (iterator.hasNext()) {
			Invocation invocation = iterator.next();
			Invocation clone = clone(invocation);
			newList.add(clone);
		}
		return newList;
	}
	
}
