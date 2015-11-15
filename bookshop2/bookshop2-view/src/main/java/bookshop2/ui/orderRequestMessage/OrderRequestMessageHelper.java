package bookshop2.ui.orderRequestMessage;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.common.util.PersonNameUtil;
import org.aries.ui.manager.AbstractElementHelper;

import bookshop2.OrderRequestMessage;
import bookshop2.util.OrderRequestMessageUtil;


@SessionScoped
@Named("orderRequestMessageHelper")
public class OrderRequestMessageHelper extends AbstractElementHelper<OrderRequestMessage> implements Serializable {

	@Override
	public boolean isEmpty(OrderRequestMessage order) {
		return OrderRequestMessageUtil.isEmpty(order);
	}
	
	@Override
	public String toString(OrderRequestMessage order) {
		return PersonNameUtil.toPersonNameString(order.getName());
	}
	
	@Override
	public String toString(Collection<OrderRequestMessage> orderList) {
		return OrderRequestMessageUtil.toString(orderList);
	}
	
	@Override
	public boolean validate(OrderRequestMessage order) {
		return OrderRequestMessageUtil.validate(order);
	}

	@Override
	public boolean validate(Collection<OrderRequestMessage> orderList) {
		return OrderRequestMessageUtil.validate(orderList);
	}
	
}
