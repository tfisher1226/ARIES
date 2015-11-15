package bookshop2.ui.orderRequestMessage;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.Assert;
import org.aries.common.PersonName;
import org.aries.common.StreetAddress;
import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractRecordManager;
import org.aries.ui.event.Updated;
import org.aries.ui.manager.PersonNameManager;
import org.aries.ui.manager.StreetAddressManager;
import org.aries.util.Validator;

import bookshop2.Order;
import bookshop2.OrderRequestMessage;
import bookshop2.ui.order.OrderInfoManager;
import bookshop2.util.Bookshop2Fixture;
import bookshop2.util.OrderRequestMessageUtil;


@SessionScoped
@Named("orderRequestMessageInfoManager")
public class OrderRequestMessageInfoManager extends AbstractRecordManager<OrderRequestMessage> implements Serializable {

	private OrderInfoManager orderInfoManager;

	private PersonNameManager personNameManager;
	
	private StreetAddressManager streetAddressManager;
	
	@Inject
	@Updated
	private Event<OrderRequestMessage> updatedEvent;
	
	
	public OrderRequestMessageInfoManager() {
		setInstanceName("orderRequestMessage");
		OrderRequestMessage orderRequestMessage = create();
		initialize(orderRequestMessage);
	}

	
	public OrderRequestMessage getOrderRequestMessage() {
		return getRecord();
	}
	
	@Override
	public Class<OrderRequestMessage> getRecordClass() {
		return OrderRequestMessage.class;
	}
	
	@Override
	public boolean isEmpty(OrderRequestMessage orderRequestMessage) {
		return getOrderRequestMessageHelper().isEmpty(orderRequestMessage);
	}
	
	@Override
	public String toString(OrderRequestMessage orderRequestMessage) {
		return getOrderRequestMessageHelper().toString(orderRequestMessage);
	}
	
	protected OrderRequestMessageHelper getOrderRequestMessageHelper() {
		return BeanContext.getFromSession("orderRequestMessageHelper");
	}
	
	protected OrderRequestMessageListManager getOrderRequestMessageListManager() {
		return BeanContext.getFromSession("orderRequestMessageListManager");
	}
	
//	protected OrderService getOrderService() {
//		return BeanContext.getFromSession(OrderService.ID);
//	}
	
	public void initialize(OrderRequestMessage orderRequestMessage) {
		OrderRequestMessageUtil.initialize(orderRequestMessage);
		initializeOrderInfoManager(orderRequestMessage);
		initializePersonNameManager(orderRequestMessage);
		initializeStreetAddressManager(orderRequestMessage);
		initializeOutjectedState(orderRequestMessage);
		setContext("orderRequestMessage", orderRequestMessage);
	}
	
	protected void initializeOutjectedState(OrderRequestMessage orderRequestMessage) {
		outjectTo("order", orderRequestMessage.getOrder());
		outjectTo("name", orderRequestMessage.getName());
		outjectTo("address", orderRequestMessage.getAddress());
		outject(instanceName, orderRequestMessage);
	}

	protected void initializeOrderInfoManager(OrderRequestMessage orderRequestMessage) {
		orderInfoManager = BeanContext.getFromSession("orderInfoManager");
		orderInfoManager.setContext("OrderRequestMessage", toString(orderRequestMessage));
		orderInfoManager.setRecord(orderRequestMessage.getOrder());
		orderInfoManager.initialize();
	}

	protected void initializePersonNameManager(OrderRequestMessage orderRequestMessage) {
		personNameManager = BeanContext.getFromSession("personNameManager");
		personNameManager.setContext("OrderRequestMessage", toString(orderRequestMessage));
		personNameManager.setRecord(orderRequestMessage.getName());
		personNameManager.initialize();
	}
	
	protected void initializeStreetAddressManager(OrderRequestMessage orderRequestMessage) {
		streetAddressManager = BeanContext.getFromSession("streetAddressManager");
		streetAddressManager.setContext("OrderRequestMessage", toString(orderRequestMessage));
		streetAddressManager.setRecord(orderRequestMessage.getAddress());
		streetAddressManager.initialize();
	}
	
	//SEAM @Observer("orderEntered")
	public void handleOrderEntered(@Observes @Updated Order order) {
		OrderRequestMessage orderRequestMessage = getOrderRequestMessage();
		orderRequestMessage.setOrder(order);
		outjectTo("order", order);
	}

	//SEAM @Observer("orderPersonNameEntered")
	public void handleNameEntered(@Observes @Updated PersonName personName) {
		OrderRequestMessage orderRequestMessage = getOrderRequestMessage();
		orderRequestMessage.setName(personName);
		outjectTo("name", personName);
	}
	
	//SEAM @Observer("orderStreetAddressEntered")
	public void handleAddressEntered(@Observes @Updated StreetAddress streetAddress) {
		OrderRequestMessage orderRequestMessage = getOrderRequestMessage();
		orderRequestMessage.setAddress(streetAddress);
		outjectTo("address", streetAddress);
	}
	
	public void populate() {
		try {
			OrderRequestMessage orderRequestMessage = Bookshop2Fixture.create_OrderRequestMessage();
			initialize(orderRequestMessage);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void activate() {
		initializeContext();
		OrderRequestMessage orderRequestMessage = BeanContext.getFromSession(getInstanceId());
		if (orderRequestMessage == null)
			newOrderRequestMessage();
		else editOrderRequestMessage(orderRequestMessage);
	}
	
	//SEAM @Begin
	public void newOrderRequestMessage() {
		try {
			OrderRequestMessage orderRequestMessage = create();
			initialize(orderRequestMessage);
			show();
		} catch (Exception e) {
			handleException(e);
		}
	}

	@Override
	public OrderRequestMessage create() {
		OrderRequestMessage orderRequestMessage = OrderRequestMessageUtil.create();
		return orderRequestMessage;
	}

	@Override
	public OrderRequestMessage clone(OrderRequestMessage orderRequestMessage) {
		orderRequestMessage = OrderRequestMessageUtil.clone(orderRequestMessage);
		return orderRequestMessage;
	}

	//SEAM @Begin
	public void editOrderRequestMessage(OrderRequestMessage orderRequestMessage) {
		try {
			orderRequestMessage = clone(orderRequestMessage);
			initialize(orderRequestMessage);
			show();
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	//SEAM @Observer("org.aries.saveOrder")
	public void saveOrderRequestMessage() {
		setModule("OrderRequestMessageDialog");
		OrderRequestMessage orderRequestMessage = getOrderRequestMessage();
		enrichOrderRequestMessage(orderRequestMessage);
		if (validate(orderRequestMessage)) {
			//if (isImmediate())
			//	processOrderRequestMessage(orderRequestMessage);
			outject("orderRequestMessage", orderRequestMessage);
			updatedEvent.fire(getOrderRequestMessage());
		}
	}

//	public void processOrderRequestMessage(OrderRequestMessage orderRequestMessage) {
//		Long id = orderRequestMessage.getId();
//		if (id != null) {
//			saveOrderRequestMessage(orderRequestMessage);
//		} else {
//			addOrderRequestMessage(orderRequestMessage);
//		}
//	}
	
	public void saveOrderRequestMessage(OrderRequestMessage orderRequestMessage) {
		try {
			if (isImmediate())
				getOrderRequestMessageListManager().addRecord(orderRequestMessage);
			//TODO getOrderService().saveOrder(order);
			raiseEvent("org.aries.refreshOrderList");
			raiseEvent(actionEvent);
			
		} catch (Exception e) {
			handleException(e);
		}
	}

	public void addOrderRequestMessage(OrderRequestMessage orderRequestMessage) {
		try {
			Long id = 1L;
			//TODO Long id = getOrderService().addOrder(order);
			Assert.notNull(id, "OrderRequestMessage Id should not be null");
			raiseEvent("org.aries.refreshOrderRequestMessageList");
			raiseEvent(actionEvent);
			
		} catch (Exception e) {
			handleException(e);
		}
	}

	public void enrichOrderRequestMessage(OrderRequestMessage orderRequestMessage) {
		//nothing for now
	}
	
	public boolean validate(OrderRequestMessage orderRequestMessage) {
		Validator validator = getValidator();
		boolean isValid = OrderRequestMessageUtil.validate(orderRequestMessage);
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
}
