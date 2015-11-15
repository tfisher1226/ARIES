package bookshop2.ui.orderRequestMessage;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import bookshop2.OrderRequestMessage;
import bookshop2.util.OrderRequestMessageUtil;


public class OrderRequestMessageListObject extends AbstractListObject<OrderRequestMessage> implements Comparable<OrderRequestMessageListObject>, Serializable {
	
	private OrderRequestMessage orderRequestMessage;
	
	
	public OrderRequestMessageListObject(OrderRequestMessage orderRequestMessage) {
		this.orderRequestMessage = orderRequestMessage;
	}

	
	public OrderRequestMessage getOrderRequestMessage() {
		return orderRequestMessage;
	}
	
	@Override
	public String getLabel() {
		return toString(orderRequestMessage);
	}
	
	@Override
	public String toString() {
		return toString(orderRequestMessage);
	}
	
	@Override
	public String toString(OrderRequestMessage orderRequestMessage) {
		return OrderRequestMessageUtil.toString(orderRequestMessage);
	}
	
	@Override
	public int compareTo(OrderRequestMessageListObject other) {
		String thisText = toString(this.orderRequestMessage);
		String otherText = toString(other.orderRequestMessage);
		if (thisText == null)
			return -1;
		if (otherText == null)
			return 1;
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		OrderRequestMessageListObject other = (OrderRequestMessageListObject) object;
		String thisId = this.orderRequestMessage.getCorrelationId();
		String otherId = other.orderRequestMessage.getCorrelationId();
		if (thisId == null)
			return false;
		if (otherId == null)
			return false;
		return thisId.equals(otherId);
	}

}
