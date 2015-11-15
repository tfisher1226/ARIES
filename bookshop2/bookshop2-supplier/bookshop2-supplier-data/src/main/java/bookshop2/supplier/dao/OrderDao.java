package bookshop2.supplier.dao;

import java.util.Collection;
import java.util.List;

import org.aries.common.dao.Dao;

import bookshop2.OrderCriteria;
import bookshop2.supplier.entity.AbstractOrderEntity;


public interface OrderDao<T extends AbstractOrderEntity> extends Dao<T> {
	
	public T getOrderRecordById(Long id);
	
	public T getOrderRecordByTrackingNumber(String trackingNumber);
	
	public T getOrderRecordByCriteria(OrderCriteria orderCriteria);
	
	public List<T> getAllOrderRecords();
	
	public List<T> getOrderRecordsByPage(int pageIndex, int pageSize);
	
	public List<T> getOrderRecordsByCriteria(OrderCriteria orderCriteria);
	
	public Long addOrderRecord(T orderRecord);
	
	public List<Long> addOrderRecords(Collection<T> orderRecords);
	
	public void saveOrderRecord(T orderRecord);
	
	public void saveOrderRecords(Collection<T> orderRecords);
	
	public void removeAllOrderRecords();
	
	public void removeOrderRecord(T orderRecord);
	
	public void removeOrderRecords(Collection<T> orderRecords);
	
	public void removeOrderRecords(OrderCriteria orderCriteria);
	
}
