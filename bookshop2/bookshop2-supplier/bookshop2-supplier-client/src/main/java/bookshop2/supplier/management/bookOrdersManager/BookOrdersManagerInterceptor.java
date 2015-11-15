package bookshop2.supplier.management.bookOrdersManager;

import java.util.List;

import org.aries.message.Message;
import org.aries.message.MessageInterceptor;
import org.aries.util.ExceptionUtil;

import bookshop2.Order;
import bookshop2.OrderCriteria;


@SuppressWarnings("serial")
public class BookOrdersManagerInterceptor extends MessageInterceptor<BookOrdersManager> implements BookOrdersManager {
	
	@Override
	public List<Order> getAllBookOrders() {
		try {
			log.info("#### [supplier-client]: getAllBookOrders() sending");
			Message request = createMessage("getAllBookOrders");
			Message response = getProxy().invoke(request);
		List<Order> bookOrders = response.getPart("bookOrders");
			return bookOrders;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Order getFromBookOrdersById(Long orderId) {
		try {
			log.info("#### [supplier-client]: getFromBookOrdersById() sending");
			Message request = createMessage("getFromBookOrdersById");
			request.addPart("orderId", orderId);
			Message response = getProxy().invoke(request);
			Order bookOrders = response.getPart("bookOrders");
			return bookOrders;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Order> getFromBookOrdersByIds(List<Long> orderIds) {
		try {
			log.info("#### [supplier-client]: getFromBookOrdersByIds() sending");
			Message request = createMessage("getFromBookOrdersByIds");
			request.addPart("orderIds", orderIds);
			Message response = getProxy().invoke(request);
			List<Order> bookOrders = response.getPart("bookOrders");
			return bookOrders;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Order> getFromBookOrdersByCriteria(OrderCriteria orderCriteria) {
		try {
			log.info("#### [supplier-client]: getFromBookOrdersByCriteria() sending");
			Message request = createMessage("getFromBookOrdersByCriteria");
			request.addPart("orderCriteria", orderCriteria);
			Message response = getProxy().invoke(request);
		List<Order> bookOrders = response.getPart("bookOrders");
			return bookOrders;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Order> getMatchingBookOrders(List<Order> orderList) {
		try {
			log.info("#### [supplier-client]: getMatchingBookOrders() sending");
			Message request = createMessage("getMatchingBookOrders");
			request.addPart("orderList", orderList);
			Message response = getProxy().invoke(request);
		List<Order> bookOrders = response.getPart("bookOrders");
			return bookOrders;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Long addToBookOrders(Order order) {
		try {
			log.info("#### [supplier-client]: addToBookOrders() sending");
			Message request = createMessage("addToBookOrders");
			request.addPart("order", order);
			Message response = getProxy().invoke(request);
			Long orderId = response.getPart("orderId");
			return orderId;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Long> addToBookOrdersAsList(List<Order> orderList) {
		try {
			log.info("#### [supplier-client]: addToBookOrdersAsList() sending");
			Message request = createMessage("addToBookOrdersAsList");
			request.addPart("orderList", orderList);
			Message response = getProxy().invoke(request);
			List<Long> orderIds = response.getPart("orderIds");
			return orderIds;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeAllBookOrders() {
		try {
			log.info("#### [supplier-client]: removeAllBookOrders() sending");
			Message request = createMessage("removeAllBookOrders");
			getProxy().send(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeFromBookOrders(Order order) {
		try {
			log.info("#### [supplier-client]: removeFromBookOrders() sending");
			Message request = createMessage("removeFromBookOrders");
			request.addPart("order", order);
			getProxy().send(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeFromBookOrdersById(Long orderId) {
		try {
			log.info("#### [supplier-client]: removeFromBookOrdersById() sending");
			Message request = createMessage("removeFromBookOrdersById");
			request.addPart("orderId", orderId);
			getProxy().send(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeFromBookOrdersAsList(List<Order> orderList) {
		try {
			log.info("#### [supplier-client]: removeFromBookOrdersAsList() sending");
			Message request = createMessage("removeFromBookOrdersAsList");
			request.addPart("orderList", orderList);
			getProxy().send(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeFromBookOrdersByCriteria(OrderCriteria orderCriteria) {
		try {
			log.info("#### [supplier-client]: removeFromBookOrdersByCriteria() sending");
			Message request = createMessage("removeFromBookOrdersByCriteria");
			request.addPart("orderCriteria", orderCriteria);
			getProxy().send(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
