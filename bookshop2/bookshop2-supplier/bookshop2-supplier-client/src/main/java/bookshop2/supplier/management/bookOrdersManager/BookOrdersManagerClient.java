package bookshop2.supplier.management.bookOrdersManager;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.client.AbstractDelegate;
import org.aries.util.ExceptionUtil;

import bookshop2.Order;
import bookshop2.OrderCriteria;


public class BookOrdersManagerClient extends AbstractDelegate implements BookOrdersManager {
	
	private static final Log log = LogFactory.getLog(BookOrdersManagerClient.class);
	
	
	@Override
	public String getDomain() {
		return "bookshop2.supplier";
	}
	
	@Override
	public String getServiceId() {
		return BookOrdersManager.ID;
	}
	
	@SuppressWarnings("unchecked")
	public BookOrdersManager getProxy() throws Exception {
		return getProxy(BookOrdersManager.ID);
	}
	
	@Override
	public List<Order> getAllBookOrders() {
		try {
			List<Order> bookOrders = getProxy().getAllBookOrders();
			return bookOrders;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Order getFromBookOrdersById(Long orderId) {
		try {
			Order bookOrders = getProxy().getFromBookOrdersById(orderId);
			return bookOrders;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Order> getFromBookOrdersByIds(List<Long> orderIds) {
		try {
			List<Order> bookOrders = getProxy().getFromBookOrdersByIds(orderIds);
			return bookOrders;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Order> getFromBookOrdersByCriteria(OrderCriteria orderCriteria) {
		try {
			List<Order> bookOrders = getProxy().getFromBookOrdersByCriteria(orderCriteria);
			return bookOrders;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Order> getMatchingBookOrders(List<Order> orderList) {
		try {
			List<Order> bookOrders = getProxy().getMatchingBookOrders(orderList);
			return bookOrders;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Long addToBookOrders(Order order) {
		try {
			Long orderId = getProxy().addToBookOrders(order);
			return orderId;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Long> addToBookOrdersAsList(List<Order> orderList) {
		try {
			List<Long> orderIds = getProxy().addToBookOrdersAsList(orderList);
			return orderIds;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeAllBookOrders() {
		try {
			getProxy().removeAllBookOrders();
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeFromBookOrders(Order order) {
		try {
			getProxy().removeFromBookOrders(order);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeFromBookOrdersById(Long orderId) {
		try {
			getProxy().removeFromBookOrdersById(orderId);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeFromBookOrdersAsList(List<Order> orderList) {
		try {
			getProxy().removeFromBookOrdersAsList(orderList);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeFromBookOrdersByCriteria(OrderCriteria orderCriteria) {
		try {
			getProxy().removeFromBookOrdersByCriteria(orderCriteria);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
