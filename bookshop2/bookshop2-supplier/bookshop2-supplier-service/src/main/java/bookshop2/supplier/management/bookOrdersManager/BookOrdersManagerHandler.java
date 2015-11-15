package bookshop2.supplier.management.bookOrdersManager;

import java.util.List;

import org.aries.tx.Transactional;

import bookshop2.Order;
import bookshop2.OrderCriteria;


public interface BookOrdersManagerHandler extends Transactional {
	
	public List<Order> getAllBookOrders();
	
	public Order getFromBookOrders(Long orderId);
	
	public List<Order> getFromBookOrders(List<Long> orderIds);
	
	public List<Order> getFromBookOrders(OrderCriteria orderCriteria);
	
	public List<Order> getMatchingBookOrders(List<Order> orderList);
	
	public Long addToBookOrders(Order order);
	
	public List<Long> addToBookOrders(List<Order> orderList);
	
	public void removeAllBookOrders();
	
	public void removeFromBookOrders(Order order);
	
	public void removeFromBookOrders(Long orderId);
	
	public void removeFromBookOrders(List<Order> orderList);
	
	public void removeFromBookOrders(OrderCriteria orderCriteria);
	
}
