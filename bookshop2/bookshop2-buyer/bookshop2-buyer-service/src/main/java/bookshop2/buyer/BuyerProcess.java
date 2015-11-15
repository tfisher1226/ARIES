package bookshop2.buyer;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.AccessTimeout;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;

import org.aries.common.PersonName;
import org.aries.common.StreetAddress;
import org.aries.process.AbstractProcess;
import org.aries.process.ActionState;
import org.aries.process.TimeoutHandler;
import org.aries.runtime.RequestContext;
import org.aries.util.ExceptionUtil;

import bookshop2.Order;
import bookshop2.OrderAcceptedMessage;
import bookshop2.OrderRejectedMessage;
import bookshop2.OrderRequestMessage;
import bookshop2.Payment;
import bookshop2.PurchaseAcceptedMessage;
import bookshop2.PurchaseRejectedMessage;
import bookshop2.PurchaseRequestMessage;
import bookshop2.buyer.outgoing.orderAccepted.OrderAcceptedReply;
import bookshop2.buyer.outgoing.orderRejected.OrderRejectedReply;
import bookshop2.buyer.outgoing.purchaseAccepted.PurchaseAcceptedReply;
import bookshop2.buyer.outgoing.purchaseRejected.PurchaseRejectedReply;
import bookshop2.seller.client.orderBooks.OrderBooks;
import bookshop2.seller.client.purchaseBooks.PurchaseBooks;

import common.jmx.MBeanUtil;


@Startup
@Singleton
@LocalBean
@AccessTimeout(value = 60000)
@ConcurrencyManagement(BEAN)
@TransactionAttribute(REQUIRED)
@TransactionManagement(CONTAINER)
public class BuyerProcess extends AbstractProcess implements BuyerProcessMBean {

	public static long DEFAULT_TIMEOUT = 60000L;
	
	@Inject
	private RequestContext requestContext;

	private long orderBooksTimeout = DEFAULT_TIMEOUT;
	
	private long purchaseBooksTimeout = DEFAULT_TIMEOUT;
	

	@Override
	public String getDomainId() {
		return "bookshop2.buyer";
	}
	
	@PostConstruct
	protected void postConstruct() {
		registerWithJMX();
	}
	
	@PreDestroy
	protected void preDestroy() {
		unregisterWithJMX();
	}
	
	protected void registerWithJMX() {
		MBeanUtil.registerMBean(this, MBEAN_NAME);
	}
	
	protected void unregisterWithJMX() {
		MBeanUtil.unregisterMBean(MBEAN_NAME);
	}
	
	protected void updateState() {
		//nothing for now
	}
	
	protected BuyerContext getBuyerContext() {
		return BuyerContext.getInstance(requestContext.getCorrelationId());
	}
	
	protected BuyerContext getContext(String correlationId) {
		return BuyerContext.getInstance(correlationId);
	}
	
//	protected BuyerContext initializeContext(AbstractMessage message) {
//		BuyerContext buyerContext = getContext(message.getCorrelationId());
//		buyerContext.initializeContext(message);
//		return buyerContext;
//	}
	
	
	public void handle_OrderBooks_request(OrderRequestMessage message) {
		String serviceId = bookshop2.buyer.incoming.orderBooks.OrderBooks.ID;
		String correlationId = message.getCorrelationId();
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.initializeContext(serviceId, message);
		//TODO set timer for timeout
		send_Seller_OrderBooks_request(message);
		fire_OrderBooks_request_handled(correlationId);
	}

	public void handle_OrderBooks_request_cancel(OrderRequestMessage message) {
		String serviceId = bookshop2.buyer.incoming.orderBooks.OrderBooks.ID;
		String correlationId = message.getCorrelationId();
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.initializeContext(serviceId, message);
		cancel_Seller_OrderBooks(correlationId);
		cancel_Seller_PurchaseBooks(correlationId);
		handle_OrderBooks_request_undo(message);
		fire_OrderBooks_request_cancel_done(correlationId);
	}
	
	public void handle_OrderBooks_request_undo(OrderRequestMessage message) {
		String correlationId = message.getCorrelationId();
		undo_Seller_OrderBooks(correlationId);
		undo_Seller_PurchaseBooks(correlationId);
		fire_OrderBooks_request_undo_done(correlationId);
	}
	
	public void handle_OrderBooks_request_exception(String correlationId, Throwable cause) {
		//TODO
		fire_OrderBooks_error_sent(correlationId);
		fire_OrderBooks_incoming_request_aborted(correlationId, cause);
	}
	
	public void handle_OrderBooks_request_timeout(String correlationId, String reason) {
		//TODO
		fire_OrderBooks_error_sent(correlationId);
		fire_OrderBooks_incoming_request_aborted(correlationId, reason);
	}
	
	public void handle_OrderBooks_request_done(String correlationId) {
		fire_OrderBooks_request_done(correlationId);
	}

	protected void handle_Seller_OrderBooks_request_exception(String correlationId, Throwable cause) {
		fire_Seller_OrderBooks_outgoing_request_aborted(correlationId, cause);
		log.error("Seller.OrderBooks request exception:", cause);
	}
	
	protected void handle_Seller_OrderBooks_request_timeout(String correlationId, String reason) {
		fire_Seller_OrderBooks_outgoing_request_aborted(correlationId, reason);
	}
	
	public void handle_OrderAccepted_response(OrderAcceptedMessage message) {
		String serviceId = bookshop2.buyer.incoming.orderBooks.OrderBooks.ID;
		String correlationId = message.getCorrelationId();
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.initializeContext(message);
		buyerContext.initializeMessage(serviceId, message);
		Payment payment = buyerContext.getPayment(); 
		if (payment != null) {
			//TODO set timer for timeout
			send_Seller_PurchaseBooks_request(correlationId, payment);
		}
		reply_OrderAccepted(correlationId);
		handle_OrderAccepted_response_done(correlationId);
		handle_OrderBooks_request_done(correlationId);
	}

	public void handle_OrderAccepted_response_done(String correlationId) {
		fire_OrderAccepted_response_done(correlationId);
	}

	protected void handle_Seller_PurchaseBooks_request_exception(String correlationId, Throwable cause) {
		fire_Seller_PurchaseBooks_outgoing_request_aborted(correlationId, cause);
	}
	
	protected void handle_Seller_PurchaseBooks_request_timeout(String correlationId, String reason) {
		fire_Seller_PurchaseBooks_outgoing_request_aborted(correlationId, reason);
	}
	
	public void handle_OrderRejected_response(OrderRejectedMessage message) {
		String serviceId = bookshop2.buyer.incoming.orderBooks.OrderBooks.ID;
		String correlationId = message.getCorrelationId();
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.initializeContext(message);
		buyerContext.initializeMessage(serviceId, message);
		reply_OrderRejected(message);
		handle_OrderRejected_response_done(correlationId);
		handle_OrderBooks_request_done(correlationId);
	}

	public void handle_OrderRejected_response_done(String correlationId) {
		fire_OrderRejected_response_done(correlationId);
	}
	
	public void handle_PurchaseBooks_request(PurchaseRequestMessage message) {
		String serviceId = bookshop2.buyer.incoming.orderBooks.OrderBooks.ID;
		String correlationId = message.getCorrelationId();
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.initializeContext(serviceId, message);
		//TODO set timer for timeout
		send_Seller_PurchaseBooks_request(correlationId);
		fire_PurchaseBooks_request_handled(correlationId);
	}

	public void handle_PurchaseBooks_request_cancel(PurchaseRequestMessage message) {
		String serviceId = bookshop2.buyer.incoming.orderBooks.OrderBooks.ID;
		String correlationId = message.getCorrelationId();
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.initializeContext(serviceId, message);
		cancel_Seller_PurchaseBooks(correlationId);
		handle_PurchaseBooks_request_undo(message);
		fire_PurchaseBooks_request_cancel_done(correlationId);
	}
	
	public void handle_PurchaseBooks_request_undo(PurchaseRequestMessage message) {
		String correlationId = message.getCorrelationId();
		undo_Seller_PurchaseBooks(correlationId);
		fire_PurchaseBooks_request_undo_done(correlationId);
	}
	
	public void handle_PurchaseBooks_request_exception(String correlationId, Throwable cause) {
		//TODO
		fire_PurchaseBooks_error_sent(correlationId);
		fire_PurchaseBooks_incoming_request_aborted(correlationId, cause);
	}
	
	public void handle_PurchaseBooks_request_timeout(String correlationId, String reason) {
		//TODO
		fire_PurchaseBooks_error_sent(correlationId);
		fire_PurchaseBooks_incoming_request_aborted(correlationId, reason);
	}
	
	public void handle_PurchaseBooks_request_done(String correlationId) {
		fire_PurchaseBooks_request_done(correlationId);
	}
	
	public void handle_PurchaseAccepted_response(PurchaseAcceptedMessage message) {
		String serviceId = bookshop2.buyer.incoming.orderBooks.OrderBooks.ID;
		String correlationId = message.getCorrelationId();
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.initializeContext(message);
		buyerContext.initializeMessage(serviceId, message);
		reply_PurchaseAccepted(message);
		handle_PurchaseAccepted_response_done(correlationId);
		handle_PurchaseBooks_request_done(correlationId);
	}

	public void handle_PurchaseAccepted_response_done(String correlationId) {
		fire_PurchaseAccepted_response_done(correlationId);
	}
	
	public void handle_PurchaseRejected_response(PurchaseRejectedMessage message) {
		String serviceId = bookshop2.buyer.incoming.orderBooks.OrderBooks.ID;
		String correlationId = message.getCorrelationId();
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.initializeContext(message);
		buyerContext.initializeMessage(serviceId, message);
		reply_PurchaseRejected(message);
		handle_PurchaseRejected_response_done(correlationId);
		handle_PurchaseBooks_request_done(correlationId);
	}

	public void handle_PurchaseRejected_response_done(String correlationId) {
		fire_PurchaseRejected_response_done(correlationId);
	}
	
	protected OrderRequestMessage createOrderRequestMessage(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		String serviceId = bookshop2.buyer.incoming.orderBooks.OrderBooks.ID;
		OrderRequestMessage message = buyerContext.createOrderRequestMessage(serviceId);
		buyerContext.addReplyTo_Buyer_OrderAccepted(message);
		buyerContext.addReplyTo_Buyer_OrderRejected(message);
		return message;
	}
	
	protected PurchaseRequestMessage createPurchaseRequestMessage(String correlationId) {
		String serviceId = bookshop2.buyer.incoming.orderBooks.OrderBooks.ID;
		BuyerContext buyerContext = getContext(correlationId);
		PurchaseRequestMessage message = buyerContext.createPurchaseRequestMessage(serviceId);
		buyerContext.addReplyTo_Buyer_PurchaseAccepted(message);
		buyerContext.addReplyTo_Buyer_PurchaseRejected(message);
		return message;
	}
	
//	protected void send_Seller_OrderBooks_request() {
//		OrderRequestMessage message = createOrderRequestMessage();
//		send_Seller_OrderBooks_request(message);
//	}
	
	protected void send_Seller_OrderBooks_request_cancel(String correlationId) {
		OrderRequestMessage message = createOrderRequestMessage(correlationId);
		message.setCancelRequest(true);
		send_Seller_OrderBooks_request(message);
	}
	
	protected void send_Seller_OrderBooks_request_undo(String correlationId, Order order, PersonName name, StreetAddress address, Payment payment) {
		OrderRequestMessage message = createOrderRequestMessage(correlationId);
		message.setOrder(order);
		message.setName(name);
		message.setAddress(address);
		message.setPayment(payment);
		message.setUndoRequest(true);
		send_Seller_OrderBooks_request(message);
	}

	protected void send_Seller_OrderBooks_request(OrderRequestMessage message) {
		String correlationId = message.getCorrelationId();
		BuyerContext buyerContext = getContext(correlationId);
		try {
			buyerContext.addReplyTo_Buyer_OrderAccepted(message);
			buyerContext.addReplyTo_Buyer_OrderRejected(message);
			Runnable timeoutHandler = create_Seller_OrderBooks_request_timeoutHandler(correlationId);
			send_Seller_OrderBooks_request(message, timeoutHandler);
		} catch (Throwable e) {
			e = ExceptionUtil.getRootCause(e);
			handle_Seller_OrderBooks_request_exception(correlationId, e);
		}
	}
	
	protected void send_Seller_OrderBooks_request(OrderRequestMessage message, Runnable timeoutHandler) {
		String correlationId = message.getCorrelationId();
		TimeoutHandler timeoutHelper = startTimeoutHandler(timeoutHandler, orderBooksTimeout);
		try {
			OrderBooks client = getClient(OrderBooks.ID);
			client.orderBooks(message);
			logState_Seller_OrderBooks(message);
			fire_Seller_OrderBooks_request_sent(correlationId);
		} finally {
			timeoutHelper.reset();
		}
	}
	
	protected Runnable create_Seller_OrderBooks_request_timeoutHandler(final String correlationId) {
		return new Runnable() {
			public void run() {
				orderBooksTimeout = DEFAULT_TIMEOUT;
				String reason = "OrderBooks timed-out";
				handle_Seller_OrderBooks_request_timeout(correlationId, reason);
			}
		};
	}
	
	protected void reply_OrderAccepted(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		String serviceId = bookshop2.buyer.incoming.orderBooks.OrderBooks.ID;
		OrderAcceptedMessage message = buyerContext.createOrderAcceptedMessage(serviceId);
		reply_OrderAccepted(message);
	}

	protected void reply_OrderAccepted(OrderAcceptedMessage message) {
		OrderAcceptedReply replyTo = getReplyTo(OrderAcceptedReply.ID);
		replyTo.orderAccepted(message);
		String correlationId = message.getCorrelationId();
		fire_OrderAccepted_response_sent(correlationId);
	}
	
	protected void reply_OrderRejected(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		String serviceId = bookshop2.buyer.incoming.orderBooks.OrderBooks.ID;
		OrderRejectedMessage message = buyerContext.createOrderRejectedMessage(serviceId);
		reply_OrderRejected(message);
	}

	protected void reply_OrderRejected(OrderRejectedMessage message) {
		OrderRejectedReply replyTo = getReplyTo(OrderRejectedReply.ID);
		replyTo.orderRejected(message);
		String correlationId = message.getCorrelationId();
		fire_OrderRejected_response_sent(correlationId);
	}
	
	protected void send_Seller_PurchaseBooks_request(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		Payment payment = buyerContext.getPayment();
		send_Seller_PurchaseBooks_request(correlationId, payment);
	}
	
	protected void send_Seller_PurchaseBooks_request(String correlationId, Payment payment) {
		PurchaseRequestMessage message = createPurchaseRequestMessage(correlationId);
		message.setPayment(payment);
		send_Seller_PurchaseBooks_request(message);
	}
	
	protected void send_Seller_PurchaseBooks_request_cancel(String correlationId) {
		PurchaseRequestMessage message = createPurchaseRequestMessage(correlationId);
		message.setCancelRequest(true);
		send_Seller_PurchaseBooks_request(message);
	}
	
	protected void send_Seller_PurchaseBooks_request_undo(String correlationId, PersonName name, StreetAddress address, Order order, Payment payment) {
		PurchaseRequestMessage message = createPurchaseRequestMessage(correlationId);
		message.setName(name);
		message.setAddress(address);
		message.setOrder(order);
		message.setPayment(payment);
		message.setUndoRequest(true);
		send_Seller_PurchaseBooks_request(message);
	}
	
	protected void send_Seller_PurchaseBooks_request(PurchaseRequestMessage message) {
		String correlationId = message.getCorrelationId();
		Runnable timeoutHandler = create_Seller_PurchaseBooks_request_timeoutHandler(correlationId);
		try {
			send_Seller_PurchaseBooks_request(message, timeoutHandler);
		} catch (Throwable e) {
			e = ExceptionUtil.getRootCause(e);
			handle_Seller_PurchaseBooks_request_exception(correlationId, e);
		}
	}
	
	protected void send_Seller_PurchaseBooks_request(PurchaseRequestMessage message, Runnable timeoutHandler) {
		TimeoutHandler timeoutHelper = startTimeoutHandler(timeoutHandler, purchaseBooksTimeout);
		try {
		PurchaseBooks client = getClient(PurchaseBooks.ID);
		client.purchaseBooks(message);
			logState_Seller_PurchaseBooks(message);
			String correlationId = message.getCorrelationId();
			fire_Seller_PurchaseBooks_request_sent(correlationId);
		} finally {
			timeoutHelper.reset();
		}
	}

	protected Runnable create_Seller_PurchaseBooks_request_timeoutHandler(final String correlationId) {
		return new Runnable() {
			public void run() {
				purchaseBooksTimeout = DEFAULT_TIMEOUT;
				String reason = "PurchaseBooks timed-out";
				handle_Seller_PurchaseBooks_request_timeout(correlationId, reason);
			}
		};
	}
	
	protected void reply_PurchaseAccepted(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		String serviceId = bookshop2.buyer.incoming.orderBooks.OrderBooks.ID;
		PurchaseAcceptedMessage message = buyerContext.createPurchaseAcceptedMessage(serviceId);
		reply_PurchaseAccepted(message);
	}
	
	protected void reply_PurchaseAccepted(PurchaseAcceptedMessage message) {
		PurchaseAcceptedReply client = getClient(PurchaseAcceptedReply.ID);
		client.purchaseAccepted(message);
		String correlationId = message.getCorrelationId();
		fire_PurchaseAccepted_response_sent(correlationId);
	}
	
	protected void reply_PurchaseRejected(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		String serviceId = bookshop2.buyer.incoming.orderBooks.OrderBooks.ID;
		PurchaseRejectedMessage message = buyerContext.createPurchaseRejectedMessage(serviceId);
		reply_PurchaseRejected(message);
	}

	protected void reply_PurchaseRejected(PurchaseRejectedMessage message) {
		PurchaseRejectedReply client = getClient(PurchaseRejectedReply.ID);
		client.purchaseRejected(message);
		String correlationId = message.getCorrelationId();
		fire_PurchaseRejected_response_sent(correlationId);
	}
	
	protected void cancel_Seller_OrderBooks(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		ActionState actionState = buyerContext.getActionState(correlationId, "orderBooks");
		if (actionState != null) {
			send_Seller_OrderBooks_request_cancel(correlationId);
		}
	}
	
	protected void cancel_Seller_PurchaseBooks(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		ActionState actionState = buyerContext.getActionState(correlationId, "purchaseBooks");
		if (actionState != null) {
			send_Seller_PurchaseBooks_request_cancel(correlationId);
		}
	}
	
	protected void undo_Seller_OrderBooks(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		ActionState actionState = buyerContext.getActionState(correlationId, "orderBooks");
		if (actionState != null) {
			Order order = actionState.getElement("order");
			PersonName personName = actionState.getElement("personName");
			StreetAddress streetAddress = actionState.getElement("streetAddress");
			Payment payment = actionState.getElement("payment");
			send_Seller_OrderBooks_request_undo(correlationId, order, personName, streetAddress, payment);
		}
	}
	
	protected void undo_Seller_PurchaseBooks(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		ActionState actionState = buyerContext.getActionState(correlationId, "purchaseBooks");
		if (actionState != null) {
			PersonName personName = actionState.getElement("personName");
			StreetAddress streetAddress = actionState.getElement("streetAddress");
			Order order = actionState.getElement("order");
			Payment payment = actionState.getElement("payment");
			send_Seller_PurchaseBooks_request_undo(correlationId, personName, streetAddress, order, payment);
		}
	}
	
	protected void logState_Seller_OrderBooks(OrderRequestMessage message) {
		BuyerContext buyerContext = getContext(message.getCorrelationId());
		ActionState action = createActionState("orderBooks", message);
		action.addElement("order", message.getOrder());
		action.addElement("personName", message.getName());
		action.addElement("streetAddress", message.getAddress());
		action.addElement("payment", message.getPayment());
		buyerContext.logAction(action);
	}
	
	protected void logState_Seller_PurchaseBooks(PurchaseRequestMessage message) {
		BuyerContext buyerContext = getContext(message.getCorrelationId());
		ActionState action = createActionState("purchaseBooks", message);
		action.addElement("personName", message.getName());
		action.addElement("streetAddress", message.getAddress());
		action.addElement("order", message.getOrder());
		action.addElement("payment", message.getPayment());
		buyerContext.logAction(action);
	}
	
	protected void fire_OrderBooks_request_received(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_OrderBooks_request_received();
	}
	
	protected void fire_OrderBooks_request_handled(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_OrderBooks_request_handled();
	}
	
	protected void fire_OrderBooks_request_done(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_OrderBooks_request_done();
	}
	
	protected void fire_OrderBooks_request_cancel_done(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_OrderBooks_request_cancel_done();
	}
	
	protected void fire_OrderBooks_request_undo_done(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_OrderBooks_request_undo_done();
	}
	
	protected void fire_OrderBooks_incoming_request_aborted(String correlationId, Throwable cause) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_OrderBooks_incoming_request_aborted(cause);
	}
	
	protected void fire_OrderBooks_incoming_request_aborted(String correlationId, String reason) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_OrderBooks_incoming_request_aborted(reason);
	}
	
	protected void fire_OrderBooks_error_sent(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_OrderBooks_error_sent();
	}
	
	protected void fire_OrderAccepted_response_sent(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_OrderAccepted_response_sent();
	}
	
	protected void fire_OrderAccepted_outgoing_response_aborted(String correlationId, Throwable cause) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_OrderAccepted_outgoing_response_aborted(cause);
	}
	
	protected void fire_OrderAccepted_outgoing_response_aborted(String correlationId, String reason) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_OrderAccepted_outgoing_response_aborted(reason);
	}
	
	protected void fire_OrderRejected_response_sent(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_OrderRejected_response_sent();
	}
	
	protected void fire_OrderRejected_outgoing_response_aborted(String correlationId, Throwable cause) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_OrderRejected_outgoing_response_aborted(cause);
	}
	
	protected void fire_OrderRejected_outgoing_response_aborted(String correlationId, String reason) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_OrderRejected_outgoing_response_aborted(reason);
	}
	
	protected void fire_Seller_OrderBooks_request_sent(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_Seller_OrderBooks_request_sent();
	}
	
	protected void fire_Seller_OrderBooks_outgoing_request_aborted(String correlationId, Throwable cause) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_Seller_OrderBooks_outgoing_request_aborted(cause);
	}
	
	protected void fire_Seller_OrderBooks_outgoing_request_aborted(String correlationId, String reason) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_Seller_OrderBooks_outgoing_request_aborted(reason);
	}
	
	protected void fire_Seller_OrderBooks_error_received(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_Seller_OrderBooks_error_received();
	}
	
	protected void fire_OrderAccepted_response_received(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_OrderAccepted_response_received();
	}
	
	protected void fire_OrderAccepted_response_handled(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_OrderAccepted_response_handled();
	}
	
	protected void fire_OrderAccepted_response_done(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_OrderAccepted_response_done();
	}
	
	protected void fire_OrderAccepted_incoming_response_aborted(String correlationId, Throwable cause) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_OrderAccepted_incoming_response_aborted(cause);
	}
	
	protected void fire_OrderAccepted_incoming_response_aborted(String correlationId, String reason) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_OrderAccepted_incoming_response_aborted(reason);
	}
	
	protected void fire_PurchaseAccepted_response_received(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_PurchaseAccepted_response_received();
	}
	
	protected void fire_PurchaseAccepted_response_handled(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_PurchaseAccepted_response_handled();
	}
	
	protected void fire_PurchaseAccepted_response_done(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_PurchaseAccepted_response_done();
	}
	
	protected void fire_PurchaseAccepted_incoming_response_aborted(String correlationId, Throwable cause) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_PurchaseAccepted_incoming_response_aborted(cause);
	}
	
	protected void fire_PurchaseAccepted_incoming_response_aborted(String correlationId, String reason) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_PurchaseAccepted_incoming_response_aborted(reason);
	}
	
	protected void fire_PurchaseRejected_response_received(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_PurchaseRejected_response_received();
	}
	
	protected void fire_PurchaseRejected_response_handled(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_PurchaseRejected_response_handled();
	}
	
	protected void fire_PurchaseRejected_response_done(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_PurchaseRejected_response_done();
	}
	
	protected void fire_PurchaseRejected_incoming_response_aborted(String correlationId, Throwable cause) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_PurchaseRejected_incoming_response_aborted(cause);
	}
	
	protected void fire_PurchaseRejected_incoming_response_aborted(String correlationId, String reason) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_PurchaseRejected_incoming_response_aborted(reason);
	}
	
	protected void fire_OrderRejected_response_received(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_OrderRejected_response_received();
	}
	
	protected void fire_OrderRejected_response_handled(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_OrderRejected_response_handled();
	}
	
	protected void fire_OrderRejected_response_done(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_OrderRejected_response_done();
	}
	
	protected void fire_OrderRejected_incoming_response_aborted(String correlationId, Throwable cause) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_OrderRejected_incoming_response_aborted(cause);
	}
	
	protected void fire_OrderRejected_incoming_response_aborted(String correlationId, String reason) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_OrderRejected_incoming_response_aborted(reason);
	}
	
	protected void fire_Seller_PurchaseBooks_request_sent(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_Seller_PurchaseBooks_request_sent();
	}
	
	protected void fire_Seller_PurchaseBooks_outgoing_request_aborted(String correlationId, Throwable cause) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_Seller_PurchaseBooks_outgoing_request_aborted(cause);
	}
	
	protected void fire_Seller_PurchaseBooks_outgoing_request_aborted(String correlationId, String reason) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_Seller_PurchaseBooks_outgoing_request_aborted(reason);
	}
	
	protected void fire_Seller_PurchaseBooks_error_received(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_Seller_PurchaseBooks_error_received();
	}
	
	protected void fire_PurchaseBooks_request_received(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_PurchaseBooks_request_received();
	}
	
	protected void fire_PurchaseBooks_request_handled(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_PurchaseBooks_request_handled();
	}
	
	protected void fire_PurchaseBooks_request_done(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_PurchaseBooks_request_done();
	}
	
	protected void fire_PurchaseBooks_request_cancel_done(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_PurchaseBooks_request_cancel_done();
	}
	
	protected void fire_PurchaseBooks_request_undo_done(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_PurchaseBooks_request_undo_done();
	}
	
	protected void fire_PurchaseBooks_incoming_request_aborted(String correlationId, Throwable cause) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_PurchaseBooks_incoming_request_aborted(cause);
	}
	
	protected void fire_PurchaseBooks_incoming_request_aborted(String correlationId, String reason) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_PurchaseBooks_incoming_request_aborted(reason);
	}
	
	protected void fire_PurchaseBooks_error_sent(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_PurchaseBooks_error_sent();
	}
	
	protected void fire_PurchaseAccepted_response_sent(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_PurchaseAccepted_response_sent();
	}
	
	protected void fire_PurchaseAccepted_outgoing_response_aborted(String correlationId, Throwable cause) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_PurchaseAccepted_outgoing_response_aborted(cause);
	}
	
	protected void fire_PurchaseAccepted_outgoing_response_aborted(String correlationId, String reason) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_PurchaseAccepted_outgoing_response_aborted(reason);
	}
	
	protected void fire_PurchaseRejected_response_sent(String correlationId) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_PurchaseRejected_response_sent();
	}
	
	protected void fire_PurchaseRejected_outgoing_response_aborted(String correlationId, Throwable cause) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_PurchaseRejected_outgoing_response_aborted(cause);
	}
	
	protected void fire_PurchaseRejected_outgoing_response_aborted(String correlationId, String reason) {
		BuyerContext buyerContext = getContext(correlationId);
		buyerContext.fire_PurchaseRejected_outgoing_response_aborted(reason);
	}
	
}
