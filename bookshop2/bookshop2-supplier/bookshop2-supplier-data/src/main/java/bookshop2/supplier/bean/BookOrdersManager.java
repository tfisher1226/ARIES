package bookshop2.supplier.bean;

import java.util.Collection;
import java.util.List;

import bookshop2.Order;
import bookshop2.OrderCriteria;


public interface BookOrdersManager {
	
	public void initialize();
	
	public void clearContext();
	
	public List<Order> getAllBookOrdersRecords();
	
	public Order getBookOrdersRecord(Long id);
	
	public Order getBookOrdersRecordByTrackingNumber(String trackingNumber);
	
	public List<Order> getBookOrdersRecords(int pageIndex, int pageSize);
	
	public List<Order> getBookOrdersRecords(OrderCriteria orderCriteria);
	
	public Long addBookOrdersRecord(Order order);
	
	public List<Long> addBookOrdersRecords(Collection<Order> orderList);
	
	public void saveBookOrdersRecord(Order order);
	
	public void saveBookOrdersRecords(Collection<Order> orderList);
	
	public void removeAllBookOrdersRecords();
	
	public void removeBookOrdersRecord(Order order);
	
	public void removeBookOrdersRecord(Long id);
	
	public void removeBookOrdersRecords(Collection<Order> orderList);
	
	public void removeBookOrdersRecords(OrderCriteria orderCriteria);
	
	public void importBookOrdersRecords();
	
}
