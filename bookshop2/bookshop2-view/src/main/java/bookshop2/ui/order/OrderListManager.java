package bookshop2.ui.order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractTabListManager;

import bookshop2.Order;
import bookshop2.util.OrderUtil;


@SessionScoped
@Named("orderListManager")
public class OrderListManager extends AbstractTabListManager<Order, OrderListObject> implements Serializable {
	
	@Override
	public String getModule() {
		return "OrderList";
	}

	@Override
	public String getTitle() {
		return "Order List";
	}

	@Override
	public Object getRecordId(Order order) {
		return order.getId();
	}

	@Override
	public String getRecordName(Order order) {
		return OrderUtil.toString(order);
	}

	@Override
	protected Class<Order> getRecordClass() {
		return Order.class;
	}

	@Override
	protected Order getRecord(OrderListObject rowObject) {
		return rowObject.getOrder();
	}
	
	public void addRecord(Order order) {
		if (recordList == null)
			recordList = new ArrayList<Order>();
		if (recordList.contains(order))
			recordList.remove(order);
		recordList.add(order);
		refreshModel(recordList);
	}
	
	@Override
	protected OrderListObject createRowObject(Order order) {
		return new OrderListObject(order);
	}

	protected OrderHelper getOrderHelper() {
		return BeanContext.getFromSession("orderHelper");
	}
	
	protected OrderInfoManager getOrderInfoManager() {
		return BeanContext.getFromSession("orderInfoManager");
	}
	
	@Override
	public void reset() {
		refresh();
	}
	
	@Override
	public void initialize() {
		if (recordList != null)
			initialize(recordList);
	}
	
	@Override
	public void refreshModel() {
		refreshModel(recordList);
	}
	
	public void editOrder() {
		editOrder(selectedRecordId);
	}
	
	public void editOrder(String recordId) {
		editOrder(Long.parseLong(recordId));
	}
	
	public void editOrder(Long recordId) {
		Order order = recordByIdMap.get(recordId);
		editOrder(order);
	}
	
	public void editOrder(Order order) {
		getOrderInfoManager().editOrder(order);
	}
	
	//SEAM @Observer("org.aries.removeOrder")
	public void removeOrder() {
		removeOrder(selectedRecordId);
	}
	
	public void removeOrder(String recordId) {
		removeOrder(Long.parseLong(recordId));
	}
	
	public void removeOrder(Long recordId) {
		Order order = recordByIdMap.get(recordId);
		removeOrder(order);
	}
	
	public void removeOrder(Order order) {
			try {
				clearSelection();
				refresh();
			
			} catch (Exception e) {
			handleException(e);
		}
	}
	
	//SEAM @Observer("org.aries.cancelOrder")
	public void cancelOrder() {
		if (selectedRecord == null)
			return;
		try {
			BeanContext.removeFromSession("order");
			Long id = selectedRecord.getId();
			initialize(recordByIdMap.values());
			
		} catch (Exception e) {
			handleException(e);
		}
	}

	public boolean validateOrder(Collection<Order> orderList) {
		return OrderUtil.validate(orderList);
	}

}
