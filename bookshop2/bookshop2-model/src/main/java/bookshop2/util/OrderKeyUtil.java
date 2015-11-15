package bookshop2.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.common.util.PersonNameUtil;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;

import bookshop2.OrderKey;


public class OrderKeyUtil extends BaseUtil {
	
	public static boolean isEmpty(OrderKey orderKey) {
		if (orderKey == null)
			return true;
		boolean status = false;
		status |= PersonNameUtil.isEmpty(orderKey.getPersonName());
		return status;
	}
	
	public static boolean isEmpty(Collection<OrderKey> orderKeyList) {
		if (orderKeyList == null  || orderKeyList.size() == 0)
			return true;
		Iterator<OrderKey> iterator = orderKeyList.iterator();
		while (iterator.hasNext()) {
			OrderKey orderKey = iterator.next();
			if (!isEmpty(orderKey))
				return false;
		}
		return true;
	}
	
	public static String toString(OrderKey orderKey) {
		if (isEmpty(orderKey))
			return "OrderKey: [uninitialized] "+orderKey.toString();
		String text = orderKey.toString();
		return text;
	}
	
	public static String toString(Collection<OrderKey> orderKeyList) {
		if (isEmpty(orderKeyList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<OrderKey> iterator = orderKeyList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			OrderKey orderKey = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(orderKey);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static OrderKey create() {
		OrderKey orderKey = new OrderKey();
		initialize(orderKey);
		return orderKey;
	}
	
	public static void initialize(OrderKey orderKey) {
		//nothing for now
	}
	
	public static boolean validate(OrderKey orderKey) {
		if (orderKey == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.notNull(orderKey.getDateTime(), "\"DateTime\" must be specified");
		validator.isFalse(PersonNameUtil.isEmpty(orderKey.getPersonName()), "\"PersonName\" must be specified");
		PersonNameUtil.validate(orderKey.getPersonName());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<OrderKey> orderKeyList) {
		Validator validator = Validator.getValidator();
		Iterator<OrderKey> iterator = orderKeyList.iterator();
		while (iterator.hasNext()) {
			OrderKey orderKey = iterator.next();
			//TODO break or accumulate?
			validate(orderKey);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<OrderKey> orderKeyList) {
		Collections.sort(orderKeyList, createOrderKeyComparator());
	}
	
	public static Collection<OrderKey> sortRecords(Collection<OrderKey> orderKeyCollection) {
		List<OrderKey> list = new ArrayList<OrderKey>(orderKeyCollection);
		Collections.sort(list, createOrderKeyComparator());
		return list;
	}
	
	public static Comparator<OrderKey> createOrderKeyComparator() {
		return new Comparator<OrderKey>() {
			public int compare(OrderKey orderKey1, OrderKey orderKey2) {
				int status = orderKey1.compareTo(orderKey2);
				return status;
			}
		};
	}
	
	public static OrderKey clone(OrderKey orderKey) {
		if (orderKey == null)
			return null;
		OrderKey clone = create();
		clone.setPersonName(PersonNameUtil.clone(orderKey.getPersonName()));
		clone.setDateTime(ObjectUtil.clone(orderKey.getDateTime()));
		return clone;
	}
	
}
