package bookshop2.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.aries.common.util.PersonNameUtil;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;

import bookshop2.Book;
import bookshop2.Order;
import bookshop2.OrderKey;


public class OrderUtil extends BaseUtil {
	
	public static boolean isEmpty(Order order) {
		if (order == null)
			return true;
		boolean status = false;
		status |= StringUtils.isEmpty(order.getTrackingNumber());
		status |= PersonNameUtil.isEmpty(order.getPersonName());
		status |= BookUtil.isEmpty(order.getBooks());
		return status;
	}
	
	public static boolean isEmpty(Collection<Order> orderList) {
		if (orderList == null  || orderList.size() == 0)
			return true;
		Iterator<Order> iterator = orderList.iterator();
		while (iterator.hasNext()) {
			Order order = iterator.next();
			if (!isEmpty(order))
				return false;
		}
		return true;
	}
	
	public static String toString(Order order) {
		if (isEmpty(order))
			return "Order: [uninitialized] "+order.toString();
		String text = order.toString();
		return text;
	}
	
	public static String toString(Collection<Order> orderList) {
		if (isEmpty(orderList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Order> iterator = orderList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Order order = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(order);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Order create() {
		Order order = new Order();
		order.setPersonName(PersonNameUtil.create());
		initialize(order);
		return order;
	}
	
	public static void initialize(Order order) {
		if (BookUtil.isEmpty(order.getBooks()))
			order.setBooks(new HashSet<Book>());
	}
	
	public static boolean validate(Order order) {
		if (order == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.isFalse(BookUtil.isEmpty(order.getBooks()), "At least one of \"Books\" must be specified");
		validator.notNull(order.getCount(), "\"Count\" must be specified");
		validator.notNull(order.getDateTime(), "\"DateTime\" must be specified");
		boolean personNameIsEmpty = PersonNameUtil.isEmpty(order.getPersonName());
		validator.isFalse(personNameIsEmpty, "\"PersonName\" must be specified");
		validator.notEmpty(order.getTrackingNumber(), "\"TrackingNumber\" must be specified");
		if (!personNameIsEmpty)
			PersonNameUtil.validate(order.getPersonName());
		BookUtil.validate(order.getBooks());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Order> orderList) {
		Validator validator = Validator.getValidator();
		Iterator<Order> iterator = orderList.iterator();
		while (iterator.hasNext()) {
			Order order = iterator.next();
			//TODO break or accumulate?
			validate(order);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Order> orderList) {
		Collections.sort(orderList, createOrderComparator());
	}
	
	public static Collection<Order> sortRecords(Collection<Order> orderCollection) {
		List<Order> list = new ArrayList<Order>(orderCollection);
		Collections.sort(list, createOrderComparator());
		return list;
	}

	public static void sortRecordsByTrackingNumber(List<Order> orderList) {
		Collections.sort(orderList, new Comparator<Order>() {
			public int compare(Order order1, Order order2) {
				String text1 = order1.getTrackingNumber();
				String text2 = order2.getTrackingNumber();
				return text1.compareTo(text2);
			}
		});
	}
	
	public static Comparator<Order> createOrderComparator() {
		return new Comparator<Order>() {
			public int compare(Order order1, Order order2) {
				int status = order1.compareTo(order2);
				return status;
			}
		};
	}
	
	public static Order clone(Order order) {
		if (order == null)
			return null;
		Order clone = create();
		clone.setId(ObjectUtil.clone(order.getId()));
		clone.setTrackingNumber(ObjectUtil.clone(order.getTrackingNumber()));
		clone.setPersonName(PersonNameUtil.clone(order.getPersonName()));
		clone.setCount(ObjectUtil.clone(order.getCount()));
		clone.setBooks(BookUtil.clone(order.getBooks()));
		clone.setDateTime(ObjectUtil.clone(order.getDateTime()));
		return clone;
	}
	
	public static OrderKey clone(OrderKey orderKey) {
		if (orderKey == null)
			return null;
		OrderKey clone = new OrderKey();
		clone.setPersonName(PersonNameUtil.clone(orderKey.getPersonName()));
		clone.setDateTime(ObjectUtil.clone(orderKey.getDateTime()));
		return clone;
	}
	
}
