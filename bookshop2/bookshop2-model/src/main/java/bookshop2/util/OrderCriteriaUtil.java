package bookshop2.util;

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

import bookshop2.OrderCriteria;


public class OrderCriteriaUtil extends BaseUtil {
	
	public static boolean isEmpty(OrderCriteria orderCriteria) {
		if (orderCriteria == null)
			return true;
		boolean status = false;
		status |= BookUtil.isEmpty(orderCriteria.getBooks());
		return status;
	}
	
	public static boolean isEmpty(Collection<OrderCriteria> orderCriteriaList) {
		if (orderCriteriaList == null  || orderCriteriaList.size() == 0)
			return true;
		Iterator<OrderCriteria> iterator = orderCriteriaList.iterator();
		while (iterator.hasNext()) {
			OrderCriteria orderCriteria = iterator.next();
			if (!isEmpty(orderCriteria))
				return false;
		}
		return true;
	}
	
	public static String toString(OrderCriteria orderCriteria) {
		if (isEmpty(orderCriteria))
			return "OrderCriteria: [uninitialized] "+orderCriteria.toString();
		String text = orderCriteria.toString();
		return text;
	}
	
	public static String toString(Collection<OrderCriteria> orderCriteriaList) {
		if (isEmpty(orderCriteriaList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<OrderCriteria> iterator = orderCriteriaList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			OrderCriteria orderCriteria = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(orderCriteria);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static OrderCriteria create() {
		OrderCriteria orderCriteria = new OrderCriteria();
		initialize(orderCriteria);
		return orderCriteria;
	}
	
	public static void initialize(OrderCriteria orderCriteria) {
		//nothing for now
	}
	
	public static boolean validate(OrderCriteria orderCriteria) {
		if (orderCriteria == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.isFalse(BookUtil.isEmpty(orderCriteria.getBooks()), "At least one of \"Books\" must be specified");
		validator.notNull(orderCriteria.getCount(), "\"Count\" must be specified");
		BookUtil.validate(orderCriteria.getBooks());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<OrderCriteria> orderCriteriaList) {
		Validator validator = Validator.getValidator();
		Iterator<OrderCriteria> iterator = orderCriteriaList.iterator();
		while (iterator.hasNext()) {
			OrderCriteria orderCriteria = iterator.next();
			//TODO break or accumulate?
			validate(orderCriteria);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static OrderCriteria clone(OrderCriteria orderCriteria) {
		if (orderCriteria == null)
			return null;
		OrderCriteria clone = create();
		clone.setCount(ObjectUtil.clone(orderCriteria.getCount()));
		clone.setBooks(BookUtil.clone(orderCriteria.getBooks()));
		return clone;
	}
	
}
