package bookshop2.supplier.management.bookOrdersManager;

import static javax.ejb.TransactionAttributeType.REQUIRED;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.transaction.TransactionSynchronizationRegistry;
import javax.xml.ws.WebServiceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.tx.module.Bootstrapper;
import org.aries.util.ExceptionUtil;

import bookshop2.Order;
import bookshop2.OrderCriteria;


@Remote(BookOrdersManager.class)
@Stateless(name = "BookOrdersManager")
@WebService(name = "bookOrdersManager", serviceName = "bookOrdersManagerService", portName = "bookOrdersManager", targetNamespace = "http://bookshop2/supplier")
@HandlerChain(file = "/jaxws-handlers-service-oneway.xml")
public class BookOrdersManagerListenerForJAXWS implements BookOrdersManager {
	
	private static final Log log = LogFactory.getLog(BookOrdersManagerListenerForJAXWS.class);
	
	@Inject
	private BookOrdersManagerHandler bookOrdersManagerHandler;
	
	@Resource
	private WebServiceContext webServiceContext;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Override
	@WebMethod(operationName = "getAllBookOrders")
	@WebResult(name = "orderList")
	@TransactionAttribute(REQUIRED)
	public List<Order> getAllBookOrders() {
		if (!Bootstrapper.isInitialized("bookshop2-supplier-service"))
			return null;
		
		try {
			List<Order> resultList = bookOrdersManagerHandler.getAllBookOrders();
			Assert.notNull(resultList, "getAllBookOrders() result must exist");
			return resultList;
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod(operationName = "getFromBookOrdersById")
	@WebResult(name = "order")
	@TransactionAttribute(REQUIRED)
	public Order getFromBookOrdersById(Long orderId) {
		if (!Bootstrapper.isInitialized("bookshop2.supplier.service"))
			return null;
		
		try {
			Order result = bookOrdersManagerHandler.getFromBookOrders(orderId);
			return result;
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod(operationName = "getFromBookOrdersByIds")
	@WebResult(name = "orderList")
	@TransactionAttribute(REQUIRED)
	public List<Order> getFromBookOrdersByIds(List<Long> orderIds) {
		if (!Bootstrapper.isInitialized("bookshop2.supplier.service"))
			return null;
		
		try {
			List<Order> resultList = bookOrdersManagerHandler.getFromBookOrders(orderIds);
			Assert.notNull(resultList, "getFromBookOrders() result must exist");
			return resultList;
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod(operationName = "getFromBookOrdersByCriteria")
	@WebResult(name = "orderList")
	@TransactionAttribute(REQUIRED)
	public List<Order> getFromBookOrdersByCriteria(OrderCriteria orderCriteria) {
		if (!Bootstrapper.isInitialized("bookshop2.supplier.service"))
			return null;
		
		try {
			List<Order> resultList = bookOrdersManagerHandler.getFromBookOrders(orderCriteria);
			Assert.notNull(resultList, "getFromBookOrders() result must exist");
			return resultList;
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod(operationName = "getMatchingBookOrders")
	@WebResult(name = "orderList")
	@TransactionAttribute(REQUIRED)
	public List<Order> getMatchingBookOrders(List<Order> orderList) {
		if (!Bootstrapper.isInitialized("bookshop2.supplier.service"))
			return null;
		
		try {
			List<Order> resultList = bookOrdersManagerHandler.getMatchingBookOrders(orderList);
			Assert.notNull(resultList, "getMatchingBookOrders() result must exist");
			return resultList;
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod(operationName = "addToBookOrders")
	@WebResult(name = "orderId")
	@TransactionAttribute(REQUIRED)
	public Long addToBookOrders(Order order) {
		if (!Bootstrapper.isInitialized("bookshop2.supplier.service"))
			return null;
		
		try {
			Long result = bookOrdersManagerHandler.addToBookOrders(order);
			return result;
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod(operationName = "addToBookOrdersAsList")
	@WebResult(name = "orderIdsList")
	@TransactionAttribute(REQUIRED)
	public List<Long> addToBookOrdersAsList(List<Order> orderList) {
		if (!Bootstrapper.isInitialized("bookshop2.supplier.service"))
			return null;
		
		try {
			List<Long> resultList = bookOrdersManagerHandler.addToBookOrders(orderList);
			Assert.notNull(resultList, "addToBookOrders() result must exist");
			return resultList;
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod(operationName = "removeAllBookOrders")
	@TransactionAttribute(REQUIRED)
	public void removeAllBookOrders() {
		if (!Bootstrapper.isInitialized("bookshop2.supplier.service"))
			return;
		
		try {
			bookOrdersManagerHandler.removeAllBookOrders();
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod(operationName = "removeFromBookOrders")
	@TransactionAttribute(REQUIRED)
	public void removeFromBookOrders(Order order) {
		if (!Bootstrapper.isInitialized("bookshop2.supplier.service"))
			return;
		
		try {
			bookOrdersManagerHandler.removeFromBookOrders(order);
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod(operationName = "removeFromBookOrdersById")
	@TransactionAttribute(REQUIRED)
	public void removeFromBookOrdersById(Long orderId) {
		if (!Bootstrapper.isInitialized("bookshop2.supplier.service"))
			return;
		
		try {
			bookOrdersManagerHandler.removeFromBookOrders(orderId);
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod(operationName = "removeFromBookOrdersAsList")
	@TransactionAttribute(REQUIRED)
	public void removeFromBookOrdersAsList(List<Order> orderList) {
		if (!Bootstrapper.isInitialized("bookshop2.supplier.service"))
			return;
		
		try {
			bookOrdersManagerHandler.removeFromBookOrders(orderList);

		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod(operationName = "removeFromBookOrdersByCriteria")
	@TransactionAttribute(REQUIRED)
	public void removeFromBookOrdersByCriteria(OrderCriteria orderCriteria) {
		if (!Bootstrapper.isInitialized("bookshop2.supplier.service"))
			return;
		
		try {
			bookOrdersManagerHandler.removeFromBookOrders(orderCriteria);
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
