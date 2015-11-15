package bookshop2.ui.order;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import bookshop2.Order;
import bookshop2.util.OrderUtil;


public class OrderListObject extends AbstractListObject<Order> implements Comparable<OrderListObject>, Serializable {
	
	private Order order;
	
	
	public OrderListObject(Order order) {
		this.order = order;
	}

	
	public Order getOrder() {
		return order;
	}
	
	@Override
	public String getLabel() {
		return toString(order);
	}
	
	@Override
	public String toString() {
		return toString(order);
	}
	
	@Override
	public String toString(Order order) {
		return OrderUtil.toString(order);
	}
	
	@Override
	public int compareTo(OrderListObject other) {
		String thisText = toString(this.order);
		String otherText = toString(other.order);
		if (thisText == null)
			return -1;
		if (otherText == null)
			return 1;
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		OrderListObject other = (OrderListObject) object;
		Long thisId = this.order.getId();
		Long otherId = other.order.getId();
		if (thisId == null)
			return false;
		if (otherId == null)
			return false;
		return thisId.equals(otherId);
	}

}
