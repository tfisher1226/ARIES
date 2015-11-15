package bookshop2.supplier.management.bookOrdersManager;

import java.util.List;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.Order;
import bookshop2.OrderCriteria;


@WebService(name = "bookOrdersManager", targetNamespace = "http://bookshop2/supplier")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface BookOrdersManager {
	
	public String ID = "bookshop2.supplier.bookOrdersManager";
	
	public List<Order> getAllBookOrders();
	
	public Order getFromBookOrdersById(Long orderId);
	
	public List<Order> getFromBookOrdersByIds(List<Long> orderIds);
	
	public List<Order> getFromBookOrdersByCriteria(OrderCriteria orderCriteria);
	
	public List<Order> getMatchingBookOrders(List<Order> orderList);
	
	public Long addToBookOrders(Order order);
	
	public List<Long> addToBookOrdersAsList(List<Order> orderList);
	
	public void removeAllBookOrders();
	
	public void removeFromBookOrders(Order order);
	
	public void removeFromBookOrdersById(Long orderId);
	
	public void removeFromBookOrdersAsList(List<Order> orderList);
	
	public void removeFromBookOrdersByCriteria(OrderCriteria orderCriteria);
	
}
