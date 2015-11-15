package bookshop2.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.common.util.PersonNameUtil;
import org.aries.common.util.StreetAddressUtil;
import org.aries.util.BaseUtil;
import org.aries.util.Validator;

import bookshop2.OrderRequestMessage;
import bookshop2.Payment;


public class OrderRequestMessageUtil extends BaseUtil {
	
	public static boolean isEmpty(OrderRequestMessage orderRequestMessage) {
		if (orderRequestMessage == null)
			return true;
		boolean status = false;
		status |= OrderUtil.isEmpty(orderRequestMessage.getOrder());
		status |= PersonNameUtil.isEmpty(orderRequestMessage.getName());
		return status;
	}
	
	public static boolean isEmpty(Collection<OrderRequestMessage> orderRequestMessageList) {
		if (orderRequestMessageList == null  || orderRequestMessageList.size() == 0)
			return true;
		Iterator<OrderRequestMessage> iterator = orderRequestMessageList.iterator();
		while (iterator.hasNext()) {
			OrderRequestMessage orderRequestMessage = iterator.next();
			if (!isEmpty(orderRequestMessage))
				return false;
		}
		return true;
	}
	
	public static String toString(OrderRequestMessage orderRequestMessage) {
		if (isEmpty(orderRequestMessage))
			return "OrderRequestMessage: [uninitialized] "+orderRequestMessage.toString();
		String text = orderRequestMessage.toString();
		return text;
	}
	
	public static String toString(Collection<OrderRequestMessage> orderRequestMessageList) {
		if (isEmpty(orderRequestMessageList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<OrderRequestMessage> iterator = orderRequestMessageList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			OrderRequestMessage orderRequestMessage = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(orderRequestMessage);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static OrderRequestMessage create() {
		OrderRequestMessage orderRequestMessage = new OrderRequestMessage();
		orderRequestMessage.setOrder(OrderUtil.create());
		orderRequestMessage.setName(PersonNameUtil.create());
		orderRequestMessage.setAddress(StreetAddressUtil.create());
		initialize(orderRequestMessage);
		return orderRequestMessage;
	}
	
	public static void initialize(OrderRequestMessage orderRequestMessage) {
	}
	
	public static boolean validate(OrderRequestMessage orderRequestMessage) {
		if (orderRequestMessage == null)
			return false;
		Validator validator = Validator.getValidator();
		boolean orderIsEmpty = OrderUtil.isEmpty(orderRequestMessage.getOrder());
		boolean nameIsEmpty = PersonNameUtil.isEmpty(orderRequestMessage.getName());
		boolean addressIsEmpty = StreetAddressUtil.isEmpty(orderRequestMessage.getAddress());
		boolean paymentIsNull = orderRequestMessage.getPayment() == null;

		validator.isFalse(orderIsEmpty, "\"Order\" must be specified");
		validator.isFalse(nameIsEmpty, "\"Name\" must be specified");
		validator.notNull(paymentIsNull, "\"Payment\" must be specified");

		if (!orderIsEmpty) 
			OrderUtil.validate(orderRequestMessage.getOrder());
		if (!orderIsEmpty)
			PersonNameUtil.validate(orderRequestMessage.getName());
		if (!addressIsEmpty)
			StreetAddressUtil.validate(orderRequestMessage.getAddress());
		//TODO validate Payment
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<OrderRequestMessage> orderRequestMessageList) {
		Validator validator = Validator.getValidator();
		Iterator<OrderRequestMessage> iterator = orderRequestMessageList.iterator();
		while (iterator.hasNext()) {
			OrderRequestMessage orderRequestMessage = iterator.next();
			//TODO break or accumulate?
			validate(orderRequestMessage);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<OrderRequestMessage> orderRequestMessageList) {
		Collections.sort(orderRequestMessageList, createOrderRequestMessageComparator());
	}
	
	public static Collection<OrderRequestMessage> sortRecords(Collection<OrderRequestMessage> orderRequestMessageCollection) {
		List<OrderRequestMessage> list = new ArrayList<OrderRequestMessage>(orderRequestMessageCollection);
		Collections.sort(list, createOrderRequestMessageComparator());
		return list;
	}
	
	public static Comparator<OrderRequestMessage> createOrderRequestMessageComparator() {
		return new Comparator<OrderRequestMessage>() {
			public int compare(OrderRequestMessage orderRequestMessage1, OrderRequestMessage orderRequestMessage2) {
				int status = orderRequestMessage1.compareTo(orderRequestMessage2);
				return status;
			}
		};
	}
	
	public static OrderRequestMessage clone(OrderRequestMessage orderRequestMessage) {
		if (orderRequestMessage == null)
			return null;
		OrderRequestMessage clone = create();
		clone.setOrder(OrderUtil.clone(orderRequestMessage.getOrder()));
		clone.setName(PersonNameUtil.clone(orderRequestMessage.getName()));
		clone.setAddress(StreetAddressUtil.clone(orderRequestMessage.getAddress()));
		clone.setPayment(orderRequestMessage.getPayment());
		return clone;
	}

}
