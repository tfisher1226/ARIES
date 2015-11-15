package bookshop2.supplier.data.bookOrderLog;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.transaction.TransactionSynchronizationRegistry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.process.ActionState;
import org.aries.runtime.RequestContext;

import bookshop2.Order;
import bookshop2.OrderCriteria;
import bookshop2.supplier.SupplierContext;
import bookshop2.util.Bookshop2Helper;
import common.jmx.MBeanUtil;
import common.tx.state.AbstractStateManager;


@Startup
@Stateful
@LocalBean
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
@TransactionAttribute(REQUIRED)
public class BookOrderLogManager extends AbstractStateManager<BookOrderLogState> implements BookOrderLogManagerMBean {
	
	private static final Log log = LogFactory.getLog(BookOrderLogManager.class);
	
	@Inject
	private RequestContext requestContext;

	@Inject
	private BookOrderLog bookOrderLog;
	
	@Inject
	private BookOrderLogProcessor stateProcessor;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	public BookOrderLogManager() {
		//nothing for now
	}
	
	
	public BookOrderLog getBookOrderLog() {
		return bookOrderLog;
	}
	
	public void setBookOrderLog(BookOrderLog bookOrderLog) {
		this.bookOrderLog = bookOrderLog;
	}
	
	public BookOrderLogProcessor getStateProcessor() {
		return stateProcessor;
	}
	
	public void setStateProcessor(BookOrderLogProcessor stateProcessor) {
		this.stateProcessor = stateProcessor;
	}
	
	@Override
	public String getName() {
		return "BookOrderLogManager";
	}
	
	@PostConstruct
	public void registerWithJMX() {
		MBeanUtil.registerMBean(this, MBEAN_NAME);
	}
	
	@PreDestroy
	public void unregisterWithJMX() {
		MBeanUtil.unregisterMBean(MBEAN_NAME);
	}
	
	public BookOrderLogState createState() {
		return new BookOrderLogState();
	}
	
	public BookOrderLogState resetState() {
		BookOrderLogState state = createState();
		return state;
	}
	
	public void updateState() {
		//TODO if this fails then we need to mark global TX as rollback only
		updateState(stateProcessor);
	}
	
	public boolean saveState(BookOrderLogState state) {
		try {
			bookOrderLog.addToBookOrders(state.getAllBookOrders());
			return true;
			
		} catch (Exception e) {
			return false;
		}
	}
	
	public void commitState() {
		stateProcessor.updateState(currentState);
	}
	
	@Override
	public void clearContext() {
		bookOrderLog.clearContext();
	}
	
	public SupplierContext getSupplierContext() {
		String correlationId = requestContext.getCorrelationId();
		return SupplierContext.getInstance(correlationId);
	}
	
	protected boolean isGlobalTransactionActive() {
		return getSupplierContext().isGlobalTransactionActive();
	}

	@Override
	public List<Order> getAllBookOrders() {
		if (isGlobalTransactionActive())
			return currentState.getAllBookOrders();
		return bookOrderLog.getAllBookOrders();
	}
	
	@Override
	public Order getFromBookOrders(Long orderId) {
		if (isGlobalTransactionActive())
			return currentState.getFromBookOrders(orderId);
		return bookOrderLog.getFromBookOrders(orderId);
	}
	
	@Override
	public List<Order> getFromBookOrders(Collection<Long> orderIds) {
		if (isGlobalTransactionActive())
			return currentState.getFromBookOrders(orderIds);
		return bookOrderLog.getFromBookOrders(orderIds);
	}
	
	@Override
	public List<Order> getFromBookOrders(OrderCriteria orderCriteria) {
		if (isGlobalTransactionActive())
			return currentState.getFromBookOrders(orderCriteria);
		return bookOrderLog.getFromBookOrders(orderCriteria);
	}
	
	@Override
	public List<Order> getMatchingBookOrders(Collection<Order> orderList) {
		Collection<Long> orderIds = Bookshop2Helper.getOrderIds(orderList);
		OrderCriteria orderCriteria = new OrderCriteria();
		orderCriteria.addToIdList(orderIds);
		if (isGlobalTransactionActive())
			return currentState.getMatchingBookOrders(orderList);
		return bookOrderLog.getMatchingBookOrders(orderList);
	}
	
	@Override
	public Long addToBookOrders(Order order) {
		if (isGlobalTransactionActive()) {
			stateProcessor.addToPendingBookOrders(order);
			return null;
		} else {
			return bookOrderLog.addToBookOrders(order);
		}
	}
	
	@Override
	public List<Long> addToBookOrders(Collection<Order> orderList) {
		if (isGlobalTransactionActive()) {
			stateProcessor.addToPendingBookOrders(orderList);
			return null;
		} else {
			return bookOrderLog.addToBookOrders(orderList);
		}
	}
	
	@Override
	public void removeAllBookOrders() {
		if (isGlobalTransactionActive())
			stateProcessor.removeAllPendingBookOrders();
		else bookOrderLog.removeAllBookOrders();
	}
	
	@Override
	public void removeFromBookOrders(Order order) {
		if (isGlobalTransactionActive()) {
			stateProcessor.removeFromPendingBookOrders(order);
		} else {
			bookOrderLog.removeFromBookOrders(order);
		}
	}
	
	@Override
	public void removeFromBookOrders(Long orderId) {
		if (isGlobalTransactionActive())
			stateProcessor.removeFromPendingBookOrders(orderId);
		else bookOrderLog.removeFromBookOrders(orderId);
	}
	
	@Override
	public void removeFromBookOrders(Collection<Order> orderList) {
		if (isGlobalTransactionActive()) {
			stateProcessor.removeFromPendingBookOrders(orderList);
		} else {
			bookOrderLog.removeFromBookOrders(orderList);
		}
	}
	
	@Override
	public void removeFromBookOrders(OrderCriteria orderCriteria) {
		if (isGlobalTransactionActive()) {
			stateProcessor.removeFromPendingBookOrders(orderCriteria);
		} else {
			bookOrderLog.removeFromBookOrders(orderCriteria);
		}
	}
	
}
