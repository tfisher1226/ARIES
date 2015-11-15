package bookshop2.ui.order;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.Assert;
import org.aries.common.PersonName;
import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractRecordManager;
import org.aries.ui.event.Updated;
import org.aries.ui.manager.PersonNameManager;
import org.aries.util.CollectionUtil;
import org.aries.util.Validator;

import bookshop2.Book;
import bookshop2.Order;
import bookshop2.ui.book.BookInfoManager;
import bookshop2.ui.book.BookListManager;
import bookshop2.util.Bookshop2Fixture;
import bookshop2.util.OrderUtil;


@SessionScoped
@Named("orderInfoManager")
public class OrderInfoManager extends AbstractRecordManager<Order> implements Serializable {

	private BookInfoManager bookInfoManager;
	
	private BookListManager bookListManager;

	private PersonNameManager personNameManager;
	
	@Inject
	@Updated
	private Event<Order> updatedEvent;
	
	
	public OrderInfoManager() {
		setInstanceName("order");
		//Order order = create();
		//initialize(order);
	}

	
	public Order getOrder() {
		return getRecord();
	}
	
	@Override
	public Class<Order> getRecordClass() {
		return Order.class;
	}
	
	@Override
	public boolean isEmpty(Order order) {
		return getOrderHelper().isEmpty(order);
	}
	
	@Override
	public String toString(Order order) {
		return getOrderHelper().toString(order);
	}
	
	protected OrderHelper getOrderHelper() {
		return BeanContext.getFromSession("orderHelper");
	}
	
//	protected OrderService getOrderService() {
//		return BeanContext.getFromSession(OrderService.ID);
//	}
	
	protected OrderListManager getOrderListManager() {
		return BeanContext.getFromSession("orderListManager");
	}
	
	public void initialize(Order order) {
		OrderUtil.initialize(order);
		initializePersonNameManager(order);
		initializeBookInfoManager(order);
		initializeBookListManager(order);
		initializeOutjectedState(order);
		setContext("order", order);
	}
	
	protected void initializeOutjectedState(Order order) {
		outjectTo("personName", order.getPersonName());
		outjectTo("books", order.getBooks());
		outject(instanceName, order);
	}

	protected void initializePersonNameManager(Order order) {
		personNameManager = BeanContext.getFromSession("personNameManager");
		personNameManager.setContext("Order", toString(order));
		personNameManager.initialize();
	}
	
	protected void initializeBookInfoManager(Order order) {
		bookInfoManager = BeanContext.getFromSession("bookInfoManager");
		bookInfoManager.setContext("Order", toString(order));
		bookInfoManager.initialize();
	}

	protected void initializeBookListManager(Order order) {
		bookListManager = BeanContext.getFromSession("bookListManager");
		bookListManager.setContext("Order", toString(order));
		bookListManager.setRecordList(order.getBooks()); 
		bookListManager.initialize();
	}

	//SEAM @Observer("orderPersonNameEntered")
	public void handleNameEntered(@Observes @Updated PersonName personName) {
		Order order = getOrder();
		order.setPersonName(personName);
		outjectTo("personName", personName);
	}

	//SEAM @Observer("orderBooksEntered")
	public void handleBookEntered(@Observes @Updated Book book) {
		Order order = getOrder();
		//Book book = bookInfoManager.getBook();
		CollectionUtil.add(order.getBooks(), book);
		bookListManager.initialize(order.getBooks());
	}

	public void populate() {
		try {
			Order order = Bookshop2Fixture.create_Order(100);
			initialize(order);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void activate() {
		try {
			initializeContext();
			Order order = BeanContext.getFromSession(getInstanceId());
			if (order == null)
				newOrder();
			else editOrder(order);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	@Override
	public Order create() {
		Order order = OrderUtil.create();
		return order;
	}

	@Override
	public Order clone(Order order) {
		setOriginalRecord(order);
		order = OrderUtil.clone(order);
		return order;
	}

	//SEAM @Begin
	public void newOrder() {
		try {
			Order order = create();
			initialize(order);
			show();
		} catch (Exception e) {
			handleException(e);
		}
	}

	//SEAM @Begin
	public void editOrder(Order order) {
		try {
			order = clone(order);
			initialize(order);
			show();
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	//SEAM @Observer("org.aries.saveOrder")
	public void saveOrder() {
		setModule("OrderDialog");
		Order order = getOrder();
		enrichOrder(order);
		if (validate(order)) {
			if (isImmediate())
				processOrder(order);
			outject("order", order);
			//raiseEvent(actionEvent);
			updatedEvent.fire(order);
		}
	}

	public void processOrder(Order order) {
		Long id = order.getId();
		if (id != null) {
			saveOrder(order);
		} else {
			addOrder(order);
		}
	}
	
	public void saveOrder(Order order) {
		try {
			if (isImmediate())
				getOrderListManager().addRecord(order);
			raiseEvent("org.aries.refreshOrderList");
			raiseEvent(actionEvent);
			
		} catch (Exception e) {
			handleException(e);
		}
	}

	public void addOrder(Order order) {
		try {
			Long id = 1L;
			//TODO Long id = getOrderService().addOrder(order);
			Assert.notNull(id, "Order Id should not be null");
			raiseEvent("org.aries.refreshOrderList");
			raiseEvent(actionEvent);
			
		} catch (Exception e) {
			handleException(e);
		}
	}

	public void enrichOrder(Order order) {
		//nothing for now
	}
	
	public boolean validate(Order order) {
		Validator validator = getValidator();
		boolean isValid = OrderUtil.validate(order);
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
}
