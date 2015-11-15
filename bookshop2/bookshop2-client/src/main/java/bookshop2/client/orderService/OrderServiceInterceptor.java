package bookshop2.client.orderService;

import java.util.List;

import org.aries.message.Message;
import org.aries.message.MessageInterceptor;
import org.aries.util.ExceptionUtil;

import bookshop2.Order;
import bookshop2.OrderCriteria;


@SuppressWarnings("serial")
public class OrderServiceInterceptor extends MessageInterceptor<OrderService> implements OrderService {
	
	@Override
	public List<Order> getAllOrderRecords() {
		try {
			log.info("#### [admin]: getAllOrderRecords() sending...");
			Message request = createMessage("getAllOrderRecords");
			Message response = getProxy().invoke(request);
		List<Order> result = response.getPart("result");
			return result;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Order getOrderRecordById(Long id) {
		try {
			log.info("#### [admin]: getOrderRecordById() sending...");
			Message request = createMessage("getOrderRecordById");
			request.addPart("id", id);
			Message response = getProxy().invoke(request);
		Order result = response.getPart("result");
			return result;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Order> getOrderRecordsByPage(int pageIndex, int pageSize) {
		try {
			log.info("#### [admin]: getOrderRecordsByPage() sending...");
			Message request = createMessage("getOrderRecordsByPage");
			request.addPart("pageIndex", pageIndex);
			request.addPart("pageSize", pageSize);
			Message response = getProxy().invoke(request);
		List<Order> result = response.getPart("result");
			return result;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Order> getOrderRecordsByCriteria(OrderCriteria orderCriteria) {
		try {
			log.info("#### [admin]: getOrderRecordsByCriteria() sending...");
			Message request = createMessage("getOrderRecordsByCriteria");
			request.addPart("orderCriteria", orderCriteria);
			Message response = getProxy().invoke(request);
		List<Order> result = response.getPart("result");
			return result;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Long addOrderRecord(Order order) {
		try {
			log.info("#### [admin]: addOrderRecord() sending...");
			Message request = createMessage("addOrderRecord");
			request.addPart("order", order);
			Message response = getProxy().invoke(request);
		Long result = response.getPart("result");
			return result;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void saveOrderRecord(Order order) {
		try {
			log.info("#### [admin]: saveOrderRecord() sending...");
			Message request = createMessage("saveOrderRecord");
			request.addPart("order", order);
			getProxy().invoke(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeAllOrderRecords() {
		try {
			log.info("#### [admin]: removeAllOrderRecords() sending...");
			Message request = createMessage("removeAllOrderRecords");
			getProxy().invoke(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeOrderRecord(Order order) {
		try {
			log.info("#### [admin]: removeOrderRecord() sending...");
			Message request = createMessage("removeOrderRecord");
			request.addPart("order", order);
			getProxy().invoke(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeOrderRecords(OrderCriteria orderCriteria) {
		try {
			log.info("#### [admin]: removeOrderRecords() sending...");
			Message request = createMessage("removeOrderRecords");
			request.addPart("orderCriteria", orderCriteria);
			getProxy().invoke(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void importOrderRecords() {
		try {
			log.info("#### [admin]: importOrderRecords() sending...");
			Message request = createMessage("importOrderRecords");
			getProxy().invoke(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
