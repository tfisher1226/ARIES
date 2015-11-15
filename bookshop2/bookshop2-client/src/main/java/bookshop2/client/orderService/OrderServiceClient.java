package bookshop2.client.orderService;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.client.AbstractDelegate;
import org.aries.util.ExceptionUtil;

import bookshop2.Order;
import bookshop2.OrderCriteria;


public class OrderServiceClient extends AbstractDelegate implements OrderService {
	
	private static final Log log = LogFactory.getLog(OrderServiceClient.class);
	
	
	@Override
	public String getDomain() {
		return "bookshop2";
	}
	
	@Override
	public String getServiceId() {
		return OrderService.ID;
	}
	
	@SuppressWarnings("unchecked")
	public OrderService getProxy() throws Exception {
		return getProxy(OrderService.ID);
	}
	
	@Override
	public List<Order> getAllOrderRecords() {
		try {
			List<Order> result = getProxy().getAllOrderRecords();
			return result;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Order getOrderRecordById(Long id) {
		try {
			Order result = getProxy().getOrderRecordById(id);
			return result;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Order> getOrderRecordsByPage(int pageIndex, int pageSize) {
		try {
			List<Order> result = getProxy().getOrderRecordsByPage(pageIndex, pageSize);
			return result;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Order> getOrderRecordsByCriteria(OrderCriteria orderCriteria) {
		try {
			List<Order> result = getProxy().getOrderRecordsByCriteria(orderCriteria);
			return result;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Long addOrderRecord(Order order) {
		try {
			Long result = getProxy().addOrderRecord(order);
			return result;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void saveOrderRecord(Order order) {
		try {
			getProxy().saveOrderRecord(order);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeAllOrderRecords() {
		try {
			getProxy().removeAllOrderRecords();
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeOrderRecord(Order order) {
		try {
			getProxy().removeOrderRecord(order);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeOrderRecords(OrderCriteria orderCriteria) {
		try {
			getProxy().removeOrderRecords(orderCriteria);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void importOrderRecords() {
		try {
			getProxy().importOrderRecords();
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
