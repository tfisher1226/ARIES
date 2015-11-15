package bookshop2.client.orderService;

import java.util.List;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.Order;
import bookshop2.OrderCriteria;


@WebService(name = "orderService", targetNamespace = "http://bookshop2")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface OrderService {
	
	public String ID = "bookshop2.orderService";
	
	public List<Order> getAllOrderRecords();
	
	public Order getOrderRecordById(Long id);
	
	public List<Order> getOrderRecordsByPage(int pageIndex, int pageSize);
	
	public List<Order> getOrderRecordsByCriteria(OrderCriteria orderCriteria);
	
	public Long addOrderRecord(Order order);
	
	public void saveOrderRecord(Order order);
	
	public void removeAllOrderRecords();
	
	public void removeOrderRecord(Order order);
	
	public void removeOrderRecords(OrderCriteria orderCriteria);
	
	public void importOrderRecords();
	
}
