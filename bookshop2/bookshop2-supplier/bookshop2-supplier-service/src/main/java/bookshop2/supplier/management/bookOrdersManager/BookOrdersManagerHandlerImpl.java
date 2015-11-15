package bookshop2.supplier.management.bookOrdersManager;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.transaction.TransactionSynchronizationRegistry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.process.TimeoutHandler;
import org.aries.runtime.RequestContext;
import org.aries.tx.service.AbstractServiceHandler;
import org.aries.tx.service.ServiceHandlerInterceptor;
import org.aries.util.ExceptionUtil;

import bookshop2.Order;
import bookshop2.OrderCriteria;
import bookshop2.supplier.BookOrderLogRepository;
import bookshop2.util.Bookshop2Helper;


@Stateful
@Local(BookOrdersManagerHandler.class)
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
@Interceptors(ServiceHandlerInterceptor.class)
public class BookOrdersManagerHandlerImpl extends AbstractServiceHandler implements BookOrdersManagerHandler {
	
	private static final Log log = LogFactory.getLog(BookOrdersManagerHandlerImpl.class);
	
	@Inject
	private RequestContext requestContext;
	
	@Inject
	protected BookOrderLogRepository bookOrderLogRepository;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	public String getName() {
		return "BookOrdersManager";
	}
	
	public String getDomain() {
		return "bookshop2.supplier";
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public List<Order> getAllBookOrders() {
		log.info("#### [supplier]: BookOrdersManager request received");
		TimeoutHandler timeoutHandler = null;
		
		try {
			List<Order> resultList = bookOrderLogRepository.getAllBookOrdersRecords();
			return resultList;
		
		} catch (Throwable e) {
			log.error("Error", e);
			if (timeoutHandler != null)
				timeoutHandler.reset();
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
			
		} finally {
			timeout = DEFAULT_TIMEOUT;
		}
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public Order getFromBookOrders(Long orderId) {
		log.info("#### [supplier]: BookOrdersManager request received");
		TimeoutHandler timeoutHandler = null;
		
		try {
			Assert.notNull(orderId, "OrderId must be specified");
			Order result = bookOrderLogRepository.getBookOrdersRecord(orderId);
			return result;
		
		} catch (Throwable e) {
			log.error("Error", e);
			if (timeoutHandler != null)
				timeoutHandler.reset();
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
			
		} finally {
			timeout = DEFAULT_TIMEOUT;
		}
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public List<Order> getFromBookOrders(List<Long> orderIds) {
		log.info("#### [supplier]: BookOrdersManager request received");
		TimeoutHandler timeoutHandler = null;
		
		try {
			Assert.notNull(orderIds, "OrderIds must be specified");
			OrderCriteria orderCriteria = Bookshop2Helper.createOrderCriteriaFromIds(orderIds);		
			List<Order> resultList = bookOrderLogRepository.getBookOrdersRecords(orderCriteria);
			Assert.notNull(resultList, "Results not found for Order Ids: "+orderIds);
			return resultList;
		
		} catch (Throwable e) {
			log.error("Error", e);
			if (timeoutHandler != null)
				timeoutHandler.reset();
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
			
		} finally {
			timeout = DEFAULT_TIMEOUT;
		}
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public List<Order> getFromBookOrders(OrderCriteria orderCriteria) {
		log.info("#### [supplier]: BookOrdersManager request received");
		TimeoutHandler timeoutHandler = null;
		
		try {
			Assert.notNull(orderCriteria, "OrderCriteria must be specified");
			List<Order> resultList = bookOrderLogRepository.getBookOrdersRecords(orderCriteria);
			Assert.notNull(resultList, "Results not found for Order criteria: "+orderCriteria);
			return resultList;
		
		} catch (Throwable e) {
			log.error("Error", e);
			if (timeoutHandler != null)
				timeoutHandler.reset();
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
			
		} finally {
			timeout = DEFAULT_TIMEOUT;
		}
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public List<Order> getMatchingBookOrders(List<Order> orderList) {
		log.info("#### [supplier]: BookOrdersManager request received");
		TimeoutHandler timeoutHandler = null;
		
		try {
			Assert.notNull(orderList, "OrderList must be specified");
			OrderCriteria orderCriteria = Bookshop2Helper.createOrderCriteria(orderList);
			List<Order> resultList = bookOrderLogRepository.getBookOrdersRecords(orderCriteria);
			Assert.notNull(resultList, "No matching results found for Order list: "+orderList);
			return resultList;
		
		} catch (Throwable e) {
			log.error("Error", e);
			if (timeoutHandler != null)
				timeoutHandler.reset();
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
			
		} finally {
			timeout = DEFAULT_TIMEOUT;
		}
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public Long addToBookOrders(Order order) {
		log.info("#### [supplier]: BookOrdersManager request received");
		TimeoutHandler timeoutHandler = null;
		
		try {
			Assert.notNull(order, "Order must be specified");
			Long result = bookOrderLogRepository.addBookOrdersRecord(order);
			return result;
		
		} catch (Throwable e) {
			log.error("Error", e);
			if (timeoutHandler != null)
				timeoutHandler.reset();
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
			
		} finally {
			timeout = DEFAULT_TIMEOUT;
		}
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public List<Long> addToBookOrders(List<Order> orderList) {
		log.info("#### [supplier]: BookOrdersManager request received");
		TimeoutHandler timeoutHandler = null;
		
		try {
			Assert.notNull(orderList, "OrderList must be specified");
			List<Long> resultList = bookOrderLogRepository.addBookOrdersRecords(orderList);
			return resultList;
		
		} catch (Throwable e) {
			log.error("Error", e);
			if (timeoutHandler != null)
				timeoutHandler.reset();
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
			
		} finally {
			timeout = DEFAULT_TIMEOUT;
		}
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public void removeAllBookOrders() {
		log.info("#### [supplier]: BookOrdersManager request received");
		TimeoutHandler timeoutHandler = null;
		
		try {
			bookOrderLogRepository.removeAllBookOrdersRecords();
		
		} catch (Throwable e) {
			log.error("Error", e);
			if (timeoutHandler != null)
				timeoutHandler.reset();
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
			
		} finally {
			timeout = DEFAULT_TIMEOUT;
		}
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public void removeFromBookOrders(Order order) {
		log.info("#### [supplier]: BookOrdersManager request received");
		TimeoutHandler timeoutHandler = null;
		
		try {
			Assert.notNull(order, "Order must be specified");
			bookOrderLogRepository.removeBookOrdersRecord(order);
		
		} catch (Throwable e) {
			log.error("Error", e);
			if (timeoutHandler != null)
				timeoutHandler.reset();
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
			
		} finally {
			timeout = DEFAULT_TIMEOUT;
		}
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public void removeFromBookOrders(Long orderId) {
		log.info("#### [supplier]: BookOrdersManager request received");
		TimeoutHandler timeoutHandler = null;
		
		try {
			Assert.notNull(orderId, "OrderId must be specified");
			bookOrderLogRepository.removeBookOrdersRecord(orderId);
		
		} catch (Throwable e) {
			log.error("Error", e);
			if (timeoutHandler != null)
				timeoutHandler.reset();
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
			
		} finally {
			timeout = DEFAULT_TIMEOUT;
		}
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public void removeFromBookOrders(List<Order> orderList) {
		log.info("#### [supplier]: BookOrdersManager request received");
		TimeoutHandler timeoutHandler = null;
		
		try {
			Assert.notNull(orderList, "OrderList must be specified");
			bookOrderLogRepository.removeBookOrdersRecords(orderList);
		
		} catch (Throwable e) {
			log.error("Error", e);
			if (timeoutHandler != null)
				timeoutHandler.reset();
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
			
		} finally {
			timeout = DEFAULT_TIMEOUT;
		}
	}

	@Override
	@TransactionAttribute(REQUIRED)
	public void removeFromBookOrders(OrderCriteria orderCriteria) {
		log.info("#### [supplier]: BookOrdersManager request received");
		TimeoutHandler timeoutHandler = null;
		
		try {
			Assert.notNull(orderCriteria, "OrderCriteria must be specified");
			bookOrderLogRepository.removeBookOrdersRecords(orderCriteria);
		
		} catch (Throwable e) {
			log.error("Error", e);
			if (timeoutHandler != null)
				timeoutHandler.reset();
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			throw ExceptionUtil.rewrap(e);
			
		} finally {
			timeout = DEFAULT_TIMEOUT;
		}
	}

}
