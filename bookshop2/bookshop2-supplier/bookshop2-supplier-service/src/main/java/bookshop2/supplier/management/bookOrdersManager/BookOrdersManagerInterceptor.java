package bookshop2.supplier.management.bookOrdersManager;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.List;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.message.Message;
import org.aries.message.MessageInterceptor;

import bookshop2.Order;
import bookshop2.OrderCriteria;


@Stateful
@LocalBean
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
public class BookOrdersManagerInterceptor extends MessageInterceptor<BookOrdersManager> {
	
	private static final Log log = LogFactory.getLog(BookOrdersManagerInterceptor.class);
	
	@Inject
	protected BookOrdersManagerHandler bookOrdersManagerHandler;
	
	
	@TransactionAttribute(REQUIRED)
	public Message getAllBookOrders(Message message) {
		try {
			List<Order> bookOrders = bookOrdersManagerHandler.getAllBookOrders();
			Assert.notNull(bookOrders, "BookOrders must exist");
			message.addPart("bookOrders", bookOrders);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message getFromBookOrdersById(Message message) {
		try {
			Long orderId = message.getPart("orderId");
			Order bookOrders = bookOrdersManagerHandler.getFromBookOrders(orderId);
			Assert.notNull(bookOrders, "BookOrders must exist");
			message.addPart("bookOrders", bookOrders);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message getFromBookOrdersByIds(Message message) {
		try {
			List<Long> orderIds = message.getPart("orderIds");
			List<Order> bookOrders = bookOrdersManagerHandler.getFromBookOrders(orderIds);
			Assert.notNull(bookOrders, "BookOrders must exist");
			message.addPart("bookOrders", bookOrders);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message getFromBookOrdersByCriteria(Message message) {
		try {
			OrderCriteria orderCriteria = message.getPart("orderCriteria");
			List<Order> bookOrders = bookOrdersManagerHandler.getFromBookOrders(orderCriteria);
			Assert.notNull(bookOrders, "BookOrders must exist");
			message.addPart("bookOrders", bookOrders);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message getMatchingBookOrders(Message message) {
		try {
			List<Order> orderList = message.getPart("orderList");
			List<Order> bookOrders = bookOrdersManagerHandler.getMatchingBookOrders(orderList);
			Assert.notNull(bookOrders, "BookOrders must exist");
			message.addPart("bookOrders", bookOrders);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message addToBookOrders(Message message) {
		try {
			Order order = message.getPart("order");
			Long orderId = bookOrdersManagerHandler.addToBookOrders(order);
			Assert.notNull(orderId, "OrderId must exist");
			message.addPart("orderId", orderId);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message addToBookOrdersAsList(Message message) {
		try {
			List<Order> orderList = message.getPart("orderList");
			List<Long> orderIds = bookOrdersManagerHandler.addToBookOrders(orderList);
			Assert.notNull(orderIds, "OrderIds must exist");
			message.addPart("orderIds", orderIds);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message removeAllBookOrders(Message message) {
		try {
			bookOrdersManagerHandler.removeAllBookOrders();
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message removeFromBookOrders(Message message) {
		try {
			Order order = message.getPart("order");
			bookOrdersManagerHandler.removeFromBookOrders(order);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message removeFromBookOrdersById(Message message) {
		try {
			Long orderId = message.getPart("orderId");
			bookOrdersManagerHandler.removeFromBookOrders(orderId);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message removeFromBookOrdersAsList(Message message) {
		try {
			List<Order> orderList = message.getPart("orderList");
			bookOrdersManagerHandler.removeFromBookOrders(orderList);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message removeFromBookOrdersByCriteria(Message message) {
		try {
			OrderCriteria orderCriteria = message.getPart("orderCriteria");
			bookOrdersManagerHandler.removeFromBookOrders(orderCriteria);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
}
