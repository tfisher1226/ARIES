package bookshop2.supplier.data.bookOrderLog;

import java.util.Collection;
import java.util.List;

import bookshop2.Order;
import bookshop2.OrderCriteria;

import common.tx.management.AbstractMonitor;


public class BookOrderLogMonitor extends AbstractMonitor {
	
	public BookOrderLogMonitor(String host, int port) {
		setHost(host);
		setPort(port);
		getURL();
	}
	
	
	public String getMBeanName() {
		return BookOrderLogManagerMBean.MBEAN_NAME;
	}
	
	public Class<?> getMBeanClass() {
		return BookOrderLogManagerMBean.class;
	}
	
	public List<Order> getAllBookOrders() throws Exception {
		return getList("getAllBookOrders");
	}
	
	public Order getFromBookOrders(Long orderId) throws Exception {
		return getObject("getFromBookOrders", "java.lang.Long", orderId);
	}
	
	public List<Order> getFromBookOrders(Collection<Long> orderIds) throws Exception {
		return getList("getFromBookOrders", "java.util.Collection", orderIds);
	}
	
	public List<Order> getFromBookOrders(OrderCriteria orderCriteria) throws Exception {
		return getList("getFromBookOrders", "bookshop2.OrderCriteria", orderCriteria);
	}
	
	public List<Order> getMatchingBookOrders(Collection<Order> orderList) throws Exception {
		return getList("getFromBookOrdersByKeys", "java.util.Collection", orderList);
	}
	
	public Long addToBookOrders(Order order) throws Exception {
		return getObject("addToBookOrders", "bookshop2.Order", order);
	}
	
	public List<Long> addToBookOrders(Collection<Order> orderList) throws Exception {
		return getList("addToBookOrders", "java.util.Collection", orderList);
	}
	
	public void removeAllBookOrders() throws Exception {
		invoke("removeAllBookOrders");
	}
	
	public void removeFromBookOrders(Order order) throws Exception {
		invoke("removeFromBookOrders", "bookshop2.Order", order);
	}
	
	public void removeFromBookOrders(Long orderId) throws Exception {
		invoke("removeFromBookOrders", "java.lang.Long", orderId);
	}
	
	public void removeFromBookOrders(Collection<Order> orderList) throws Exception {
		invoke("removeFromBookOrders", "java.util.Collection", orderList);
	}
	
	public void removeFromBookOrders(OrderCriteria orderCriteria) throws Exception {
		invoke("removeFromBookOrders", "bookshop2.OrderCriteria", orderCriteria);
	}
	
}
