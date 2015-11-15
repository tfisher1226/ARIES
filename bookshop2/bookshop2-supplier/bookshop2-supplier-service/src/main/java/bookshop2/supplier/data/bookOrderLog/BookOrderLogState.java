package bookshop2.supplier.data.bookOrderLog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import bookshop2.Order;
import bookshop2.OrderCriteria;
import bookshop2.util.Bookshop2Helper;

import common.tx.state.ServiceState;


public class BookOrderLogState extends ServiceState {
	
	private static final long serialVersionUID = 1L;
	
	private static final Log log = LogFactory.getLog(BookOrderLogState.class);
	
	public static final String LATEST_STATE_FILENAME = "BookOrderLog_CurrentState";
	
	public static final String SHADOW_STATE_FILENAME = "BookOrderLog_ShadowState";
	
	private List<Order> bookOrders;
	
	
	public BookOrderLogState() {
		this.bookOrders = new ArrayList<Order>();
	}
	
	public BookOrderLogState(BookOrderLogState parent) {
		super(parent);
		this.bookOrders = new ArrayList<Order>(parent.getAllBookOrders());
	}
	
	
	public BookOrderLogState createState() {
		return new BookOrderLogState();
	}
	
	public BookOrderLogState resetState() {
		BookOrderLogState state = createState();
		return state;
	}
	
	@SuppressWarnings("unchecked")
	public BookOrderLogState getDerivedState() {
		return new BookOrderLogState(this);
	}
	
	public List<Order> getAllBookOrders() {
		synchronized (bookOrders) {
			List<Order> orderList = new ArrayList<Order>(bookOrders);
			return orderList;
		}
	}
	
	public Order getFromBookOrders(Long orderId) {
		synchronized (bookOrders) {
			Iterator<Order> iterator = bookOrders.iterator();
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
	
	public List<Order> getFromBookOrders(Collection<Long> orderIds) {
		synchronized (bookOrders) {
			List<Order> list = new ArrayList<Order>(orderIds.size());
			Iterator<Long> iterator = orderIds.iterator();
			while (iterator.hasNext()) {
				Long orderId = iterator.next();
				Order order = getFromBookOrders(orderId);
				if (order != null) {
					list.add(order);
				}
			}
			return list;
		}
	}
	
	public List<Order> getFromBookOrders(OrderCriteria orderCriteria) {
		synchronized (bookOrders) {
			List<Order> list = new ArrayList<Order>();
			Iterator<Order> iterator = bookOrders.iterator();
			while (iterator.hasNext()) {
				Order order = iterator.next();
				if (Bookshop2Helper.isMatch(orderCriteria, order)) {
					list.add(order);
				}
			}
			return list;
		}
	}
	
	public List<Order> getMatchingBookOrders(Collection<Order> orderList) {
		synchronized (bookOrders) {
			List<Order> matchingOrderList = new ArrayList<Order>(orderList.size());
			Iterator<Order> iterator = orderList.iterator();
			while (iterator.hasNext()) {
				Order order = iterator.next();
				if (bookOrders.contains(order))
					matchingOrderList.add(order);
			}
			return matchingOrderList;
		}
	}
	
	public void addToBookOrders(Order order) {
		synchronized (bookOrders) {
			bookOrders.add(order);
		}
	}
	
	public void addToBookOrders(Collection<Order> orderList) {
		synchronized (bookOrders) {
			bookOrders.addAll(orderList);
		}
	}
	
	public void removeAllBookOrders() {
		synchronized (bookOrders) {
			bookOrders.clear();
		}
	}
	
	public void removeFromBookOrders(Order order) {
		synchronized (bookOrders) {
			bookOrders.remove(order);
		}
	}
	
	public void removeFromBookOrders(Long orderId) {
		synchronized (bookOrders) {
			Order order = getFromBookOrders(orderId);
			if (order != null) {
				removeFromBookOrders(order);
			}
		}
	}
	
	public void removeFromBookOrders(Collection<Order> orderList) {
		synchronized (bookOrders) {
			Iterator<Order> iterator = orderList.iterator();
			while (iterator.hasNext()) {
				Order order = iterator.next();
				removeFromBookOrders(order);
		}
	}
	}
	
	public void removeFromBookOrders(OrderCriteria orderCriteria) {
		synchronized (bookOrders) {
			List<Order> orderList = getFromBookOrders(orderCriteria);
			removeFromBookOrders(orderList);
		}
	}
	
}
