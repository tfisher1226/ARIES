package bookshop2.supplier.data.bookOrderLog;

import static javax.ejb.ConcurrencyManagementType.BEAN;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import bookshop2.Order;
import bookshop2.OrderCriteria;
import bookshop2.util.Bookshop2Helper;

import common.tx.state.ServiceStateProcessor;


@Startup
@Singleton
@LocalBean
@ConcurrencyManagement(BEAN)
public class BookOrderLogProcessor implements ServiceStateProcessor<BookOrderLogState> {
	
	private static final Log log = LogFactory.getLog(BookOrderLogProcessor.class);
	
	private List<Order> pendingBookOrders = new ArrayList<Order>();
	
	
	public BookOrderLogProcessor() {
		// nothing for now
	}
	
	
	public void resetState(BookOrderLogState state) {
		state.removeAllBookOrders();
	}
	
	public boolean validateState(BookOrderLogState state) {
		return pendingBookOrders != null;
	}
	
	public void updateState(BookOrderLogState state) {
		state.addToBookOrders(pendingBookOrders);
	}
	
	public void processRequest() {
		//nothing for now
	}
	
	public List<Order> getAllPendingBookOrders() {
		synchronized (pendingBookOrders) {
			List<Order> orderList = new ArrayList<Order>(pendingBookOrders);
			return orderList;
		}
	}
	
	public Order getFromPendingBookOrders(Long orderId) {
		synchronized (pendingBookOrders) {
			Iterator<Order> iterator = pendingBookOrders.iterator();
			while (iterator.hasNext()) {
				Order order = iterator.next();
				Long id = order.getId();
				if (id != null && id.equals(orderId)) {
					return order;
				}
			}
			return null;
		}
	}
	
	public List<Order> getFromPendingBookOrders(Collection<Long> orderIds) {
		return null;
	}
	
	public List<Order> getFromPendingBookOrders(OrderCriteria orderCriteria) {
		synchronized (pendingBookOrders) {
			List<Order> list = new ArrayList<Order>();
			Iterator<Order> iterator = pendingBookOrders.iterator();
			while (iterator.hasNext()) {
				Order order = iterator.next();
				if (Bookshop2Helper.isMatch(orderCriteria, order)) {
					list.add(order);
				}
			}
			return list;
		}
	}
	
	public void setPendingBookOrders(Collection<Order> orderList) {
		synchronized (pendingBookOrders) {
			pendingBookOrders = new ArrayList<Order>(orderList);
		}
	}
	
	public void addToPendingBookOrders(Order order) {
		synchronized (pendingBookOrders) {
			pendingBookOrders.add(order);
		}
	}
	
	public void addToPendingBookOrders(Collection<Order> orderList) {
		synchronized (pendingBookOrders) {
			pendingBookOrders.addAll(orderList);
		}
	}
	
	public void removeAllPendingBookOrders() {
		synchronized (pendingBookOrders) {
			pendingBookOrders.clear();
		}
	}
	
	public void removeFromPendingBookOrders(Order order) {
		synchronized (pendingBookOrders) {
			pendingBookOrders.remove(order);
		}
	}
	
	public void removeFromPendingBookOrders(Long orderId) {
		synchronized (pendingBookOrders) {
			Order order = getFromPendingBookOrders(orderId);
			if (order != null) {
				removeFromPendingBookOrders(order);
			}
		}
	}
	
	public void removeFromPendingBookOrders(Collection<Order> orderList) {
		synchronized (pendingBookOrders) {
			pendingBookOrders.removeAll(orderList);
		}
	}
	
	public void removeFromPendingBookOrders(OrderCriteria orderCriteria) {
		synchronized (pendingBookOrders) {
			List<Order> orderList = getFromPendingBookOrders(orderCriteria);
			removeFromPendingBookOrders(orderList);
		}
	}
	
}
