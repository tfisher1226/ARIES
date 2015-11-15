package bookshop2.supplier.data.bookOrderLog;

import java.util.Collection;
import java.util.List;

import javax.management.MXBean;

import bookshop2.Order;
import bookshop2.OrderCriteria;


@MXBean
public interface BookOrderLogManagerMBean {
	
	public static final String MBEAN_NAME = "bookshop2.supplier.data:name=BookOrderLogManager";
	
	public void clearContext();
	
	public void updateState();
	
	public void commitState();
	
	public List<Order> getAllBookOrders();
	
	public Order getFromBookOrders(Long orderId);
	
	public List<Order> getFromBookOrders(Collection<Long> orderIds);
	
	public List<Order> getFromBookOrders(OrderCriteria orderCriteria);
	
	public List<Order> getMatchingBookOrders(Collection<Order> orderList);
	
	public Long addToBookOrders(Order order);
	
	public List<Long> addToBookOrders(Collection<Order> orderList);
	
	public void removeAllBookOrders();
	
	public void removeFromBookOrders(Order order);
	
	public void removeFromBookOrders(Long orderId);
	
	public void removeFromBookOrders(Collection<Order> orderList);

	public void removeFromBookOrders(OrderCriteria orderCriteria);
	
}
