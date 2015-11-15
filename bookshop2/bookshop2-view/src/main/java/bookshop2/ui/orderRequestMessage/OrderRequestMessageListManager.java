package bookshop2.ui.orderRequestMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractTabListManager;

import bookshop2.Order;
import bookshop2.OrderRequestMessage;
import bookshop2.util.OrderRequestMessageUtil;
import bookshop2.util.OrderUtil;


@SessionScoped
@Named("orderRequestMessageListManager")
public class OrderRequestMessageListManager extends AbstractTabListManager<OrderRequestMessage, OrderRequestMessageListObject> implements Serializable {
	
	@Override
	public String getModule() {
		return "OrderRequestMessageList";
	}

	@Override
	public String getTitle() {
		return "OrderRequestMessage List";
	}

	@Override
	public String getRecordId(OrderRequestMessage orderRequestMessage) {
		return orderRequestMessage.getCorrelationId();
	}

	@Override
	public String getRecordName(OrderRequestMessage orderRequestMessage) {
		return OrderRequestMessageUtil.toString(orderRequestMessage);
	}

	@Override
	protected Class<OrderRequestMessage> getRecordClass() {
		return OrderRequestMessage.class;
	}

	@Override
	protected OrderRequestMessage getRecord(OrderRequestMessageListObject rowObject) {
		return rowObject.getOrderRequestMessage();
	}
	
	public void addRecord(OrderRequestMessage orderRequestMessage) {
		if (recordList == null)
			recordList = new ArrayList<OrderRequestMessage>();
		if (recordList.contains(orderRequestMessage))
			recordList.remove(orderRequestMessage);
		recordList.add(orderRequestMessage);
		refreshModel(recordList);
	}
	
	@Override
	protected OrderRequestMessageListObject createRowObject(OrderRequestMessage orderRequestMessage) {
		return new OrderRequestMessageListObject(orderRequestMessage);
	}

	protected OrderRequestMessageHelper getOrderRequestMessageHelper() {
		return BeanContext.getFromSession("orderRequestMessageHelper");
	}
	
	protected OrderRequestMessageInfoManager getOrderRequestMessageInfoManager() {
		return BeanContext.getFromSession("orderRequestMessageInfoManager");
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
		editOrderRequestMessage(selectedRecordId);
	}
	
	public void editOrderRequestMessage(String recordId) {
		editOrderRequestMessage(Long.parseLong(recordId));
	}
	
	public void editOrderRequestMessage(Long recordId) {
		OrderRequestMessage orderRequestMessage = recordByIdMap.get(recordId);
		editOrderRequestMessage(orderRequestMessage);
	}
	
	public void editOrderRequestMessage(OrderRequestMessage orderRequestMessage) {
		getOrderRequestMessageInfoManager().editOrderRequestMessage(orderRequestMessage);
	}
	
	//SEAM @Observer("org.aries.removeOrder")
	public void removeOrder() {
		removeOrder(selectedRecordId);
	}
	
	public void removeOrder(String recordId) {
		OrderRequestMessage orderRequestMessage = recordByIdMap.get(recordId);
		removeOrder(orderRequestMessage);
	}
	
	public void removeOrder(OrderRequestMessage orderRequestMessage) {
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
			String id = selectedRecord.getCorrelationId();
			initialize(recordByIdMap.values());
			
		} catch (Exception e) {
			handleException(e);
		}
	}

	public boolean validateOrder(Collection<Order> orderList) {
		return OrderUtil.validate(orderList);
	}

}
