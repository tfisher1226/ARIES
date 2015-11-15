package bookshop2.supplier.data.bookOrderLog;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;

import org.aries.Assert;
import org.aries.data.AbstractDataUnit;

import bookshop2.Order;
import bookshop2.OrderCriteria;
import bookshop2.supplier.BookOrderLogRepository;
import bookshop2.util.Bookshop2Helper;


@Stateful
@LocalBean
@ConcurrencyManagement(BEAN)
@TransactionAttribute(REQUIRED)
@TransactionManagement(CONTAINER)
public class BookOrderLog extends AbstractDataUnit {
	
	private static final String UNIT_NAME = "BookOrderLog";
	
	@Inject
	protected BookOrderLogRepository bookOrderLogRepository;
	
	private Object mutex = new Object();
	
	
	public BookOrderLog() {
		//nothing for now
	}
	
	
	@Override
	public String getUnitName() {
		return UNIT_NAME;
	}
	
	@PostConstruct
	protected void initialize() {
		//nothing for now
	}
	
	@Override
	public void clearContext() {
		bookOrderLogRepository.clearContext();
	}
	
	public List<Order> getAllBookOrders() {
		return bookOrderLogRepository.getAllBookOrdersRecords();
	}
	
	public Order getFromBookOrders(Long orderId) {
		OrderCriteria orderCriteria = Bookshop2Helper.createOrderCriteriaFromId(orderId);
		List<Order> orderList = bookOrderLogRepository.getBookOrdersRecords(orderCriteria);
		Assert.isTrue(orderList.size() <= 1, "Unexpected number of results");
		if (orderList.size() == 1)
			return orderList.get(0);
		return null;
	}
	
	public List<Order> getFromBookOrders(Collection<Long> orderIds) {
		OrderCriteria orderCriteria = Bookshop2Helper.createOrderCriteriaFromIds(orderIds);
		return bookOrderLogRepository.getBookOrdersRecords(orderCriteria);
	}
	
	public List<Order> getFromBookOrders(OrderCriteria orderCriteria) {
		return bookOrderLogRepository.getBookOrdersRecords(orderCriteria);
	}
	
	public List<Order> getMatchingBookOrders(Collection<Order> orderList) {
		Collection<Long> orderIds = Bookshop2Helper.getOrderIds(orderList);
		OrderCriteria orderCriteria = new OrderCriteria();
		orderCriteria.getIdList().addAll(orderIds);
		return getFromBookOrders(orderCriteria);
	}
	
	public Long addToBookOrders(Order order) {
		return bookOrderLogRepository.addBookOrdersRecord(order);
	}
	
	public List<Long> addToBookOrders(Collection<Order> orderList) {
		return bookOrderLogRepository.addBookOrdersRecords(orderList);
	}
	
	public void removeAllBookOrders() {
		bookOrderLogRepository.removeAllBookOrdersRecords();
	}
	
	public void removeFromBookOrders(Order order) {
		bookOrderLogRepository.removeBookOrdersRecord(order);
	}
	
	public void removeFromBookOrders(Long orderId) {
		OrderCriteria orderCriteria = Bookshop2Helper.createOrderCriteriaFromId(orderId);
		bookOrderLogRepository.removeBookOrdersRecords(orderCriteria);
	}
	
	public void removeFromBookOrders(Collection<Order> orderList) {
		bookOrderLogRepository.removeBookOrdersRecords(orderList);
	}
	
	public void removeFromBookOrders(OrderCriteria orderCriteria) {
		bookOrderLogRepository.removeBookOrdersRecords(orderCriteria);
	}
	
}
