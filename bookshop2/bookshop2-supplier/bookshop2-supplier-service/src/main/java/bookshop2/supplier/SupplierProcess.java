package bookshop2.supplier;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.Collection;
import java.util.List;
import java.util.Set;

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

import org.aries.process.AbstractProcess;
import org.aries.process.ActionState;
import org.aries.process.TimeoutHandler;
import org.aries.util.ExceptionUtil;

import bookshop2.Book;
import bookshop2.BooksAvailableMessage;
import bookshop2.BooksUnavailableMessage;
import bookshop2.Order;
import bookshop2.Payment;
import bookshop2.QueryRequestMessage;
import bookshop2.ReservationRequestMessage;
import bookshop2.Shipment;
import bookshop2.ShipmentCompleteEvent;
import bookshop2.ShipmentCompleteMessage;
import bookshop2.ShipmentFailedMessage;
import bookshop2.ShipmentRequestMessage;
import bookshop2.ShipmentScheduledMessage;
import bookshop2.shipper.client.shipBooks.ShipBooks;
import bookshop2.supplier.data.bookInventory.BookInventoryManager;
import bookshop2.supplier.data.bookOrderLog.BookOrderLogManager;
import bookshop2.supplier.data.supplierOrderCache.SupplierOrderCacheManager;
import bookshop2.supplier.outgoing.booksAvailable.BooksAvailableReply;
import bookshop2.supplier.outgoing.booksUnavailable.BooksUnavailableReply;
import bookshop2.supplier.outgoing.shipmentComplete.ShipmentCompleteReply;
import bookshop2.supplier.outgoing.shipmentFailed.ShipmentFailedReply;
import bookshop2.supplier.outgoing.shipmentScheduled.ShipmentScheduledReply;

import common.jmx.MBeanUtil;


@Startup
@Singleton
@LocalBean
@AccessTimeout(value = 60000)
@ConcurrencyManagement(BEAN)
@TransactionAttribute(REQUIRED)
@TransactionManagement(CONTAINER)
public class SupplierProcess extends AbstractProcess implements SupplierProcessMBean {
	
	public static long DEFAULT_TIMEOUT = 60000L;
	
	@Inject
	protected BookInventoryManager bookInventoryManager;
	
	@Inject
	protected BookOrderLogManager bookOrderLogManager;
	
	@Inject
	protected SupplierOrderCacheManager supplierOrderCacheManager;
	
	@Inject
	private SupplierEventMulticaster supplierEventMulticaster;
	
	private long shipBooksTimeout = DEFAULT_TIMEOUT;
	
	
	@Override
	public String getDomainId() {
		return "bookshop2.supplier";
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
		supplierOrderCacheManager.updateState();
	}
	
	protected SupplierContext getContext(String correlationId) {
		return SupplierContext.getInstance(correlationId);
	}

	public void handle_QueryBooks_request(QueryRequestMessage message) {
		String serviceId = bookshop2.supplier.incoming.queryBooks.QueryBooks.ID;
		String correlationId = message.getCorrelationId();
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.initializeContext(serviceId, message);
		Set<Book> books = message.getBooks();
		List<Book> booksInStock = supplierOrderCacheManager.getMatchingBooksInStock(books);
		if (booksInStock.size() == 0) {
			reply_BooksUnavailable(correlationId);
		} else {
			reply_BooksAvailable(correlationId, booksInStock);
		}
		fire_QueryBooks_request_handled(correlationId);
		handle_QueryBooks_request_done(correlationId);
	}
	
	public void handle_QueryBooks_request_cancel(QueryRequestMessage message) {
		String serviceId = bookshop2.supplier.incoming.queryBooks.QueryBooks.ID;
		String correlationId = message.getCorrelationId();
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.initializeContext(serviceId, message);
		handle_QueryBooks_request_undo(message);
		fire_QueryBooks_request_cancel_done(correlationId);
	}
	
	public void handle_QueryBooks_request_undo(QueryRequestMessage message) {
		String correlationId = message.getCorrelationId();
		fire_QueryBooks_request_undo_done(correlationId);
	}
	
	public void handle_QueryBooks_request_exception(String correlationId, Throwable cause) {
		//TODO
		fire_QueryBooks_error_sent(correlationId);
		fire_QueryBooks_incoming_request_aborted(correlationId, cause);
	}
	
	public void handle_QueryBooks_request_timeout(String correlationId, String reason) {
		//TODO
		fire_QueryBooks_error_sent(correlationId);
		fire_QueryBooks_incoming_request_aborted(correlationId, reason);
	}
	
	public void handle_QueryBooks_request_done(String correlationId) {
		fire_QueryBooks_request_done(correlationId);
	}
	
	public void handle_ReserveBooks_request(ReservationRequestMessage message) {
		String serviceId = bookshop2.supplier.incoming.reserveBooks.ReserveBooks.ID;
		String correlationId = message.getCorrelationId();
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.initializeContext(serviceId, message);
		Set<Book> books = message.getBooks();
		supplierOrderCacheManager.removeFromBooksInStock(books);
		bookInventoryManager.addToReservedBooks(books);
		fire_ReserveBooks_request_handled(correlationId);
		handle_ReserveBooks_request_done(correlationId);
	}

	public void handle_ReserveBooks_request_cancel(ReservationRequestMessage message) {
		String serviceId = bookshop2.supplier.incoming.reserveBooks.ReserveBooks.ID;
		String correlationId = message.getCorrelationId();
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.initializeContext(serviceId, message);
		handle_ReserveBooks_request_undo(message);
		fire_ReserveBooks_request_cancel_done(correlationId);
	}
	
	public void handle_ReserveBooks_request_undo(ReservationRequestMessage message) {
		String correlationId = message.getCorrelationId();
		undo_SupplierOrderCache_removeFromBooksInStock(correlationId);
		fire_ReserveBooks_request_undo_done(correlationId);
	}
	
	public void handle_ReserveBooks_request_exception(String correlationId, Throwable cause) {
		//TODO
		fire_ReserveBooks_error_sent(correlationId);
		fire_ReserveBooks_incoming_request_aborted(correlationId, cause);
	}
	
	public void handle_ReserveBooks_request_timeout(String correlationId, String reason) {
		//TODO
		fire_ReserveBooks_error_sent(correlationId);
		fire_ReserveBooks_incoming_request_aborted(correlationId, reason);
	}
	
	public void handle_ReserveBooks_request_done(String correlationId) {
		fire_ReserveBooks_request_done(correlationId);
	}
	
	public void handle_ShipBooks_request(ShipmentRequestMessage message) {
		String serviceId = bookshop2.supplier.incoming.shipBooks.ShipBooks.ID;
		String correlationId = message.getCorrelationId();
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.initializeContext(serviceId, message);
		//TODO set timer for timeout
		send_Shipper_ShipBooks_request(correlationId);
		fire_ShipBooks_request_handled(correlationId);
	}
	
	public void handle_ShipBooks_request_cancel(ShipmentRequestMessage message) {
		String serviceId = bookshop2.supplier.incoming.shipBooks.ShipBooks.ID;
		String correlationId = message.getCorrelationId();
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.initializeContext(serviceId, message);
		cancel_Shipper_ShipBooks(correlationId);
		handle_ShipBooks_request_undo(message);
		fire_ShipBooks_request_cancel_done(correlationId);
	}
	
	public void handle_ShipBooks_request_undo(ShipmentRequestMessage message) {
		String correlationId = message.getCorrelationId();
		undo_Shipper_ShipBooks(correlationId);
		fire_ShipBooks_request_undo_done(correlationId);
	}
	
	public void handle_ShipBooks_request_exception(String correlationId, Throwable cause) {
		//TODO
		fire_ShipBooks_error_sent(correlationId);
		fire_ShipBooks_incoming_request_aborted(correlationId, cause);
	}
	
	public void handle_ShipBooks_request_timeout(String correlationId, String reason) {
		//TODO
		fire_ShipBooks_error_sent(correlationId);
		fire_ShipBooks_incoming_request_aborted(correlationId, reason);
	}
	
	public void handle_ShipBooks_request_done(String correlationId) {
		fire_ShipBooks_request_done(correlationId);
	}
	
	protected void handle_Shipper_ShipBooks_request_exception(String correlationId, Throwable cause) {
		reply_ShipmentFailed(correlationId, cause);
		fire_ShipBooks_outgoing_request_aborted(correlationId, cause);
		handle_ShipBooks_request_done(correlationId);
	}
	
	protected void handle_Shipper_ShipBooks_request_timeout(String correlationId, String reason) {
		fire_ShipBooks_outgoing_request_aborted(correlationId, reason);
	}
	
	public void handle_ShipmentScheduled_response(ShipmentScheduledMessage message) {
		String correlationId = message.getCorrelationId();
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.initializeContext(message);
		Shipment shipment = message.getShipment();
		reply_ShipmentScheduled(correlationId, shipment);
		handle_ShipmentScheduled_response_done(correlationId);
	}
	
	public void handle_ShipmentScheduled_response_done(String correlationId) {
		fire_ShipmentScheduled_response_done(correlationId);
	}
	
	public void handle_ShipmentComplete_response(ShipmentCompleteMessage message) {
		String correlationId = message.getCorrelationId();
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.initializeContext(message);
		Shipment shipment = message.getShipment();
		Order order = shipment.getOrder();
		bookOrderLogManager.addToBookOrders(order);
		post_ShipmentComplete(shipment);
		reply_ShipmentComplete(correlationId, shipment);
		handle_ShipmentComplete_response_done(correlationId);
		handle_ShipBooks_request_done(correlationId);
	}
	
	public void handle_ShipmentComplete_response_done(String correlationId) {
		fire_ShipmentComplete_response_done(correlationId);
	}
	
	public void handle_ShipmentFailed_response(ShipmentFailedMessage message) {
		String correlationId = message.getCorrelationId();
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.initializeContext(message);
		Shipment shipment = supplierContext.getShipment();
		reply_ShipmentFailed(correlationId, shipment);
		handle_ShipmentFailed_response_done(correlationId);
		handle_ShipBooks_request_done(correlationId);
	}
	
	public void handle_ShipmentFailed_response_done(String correlationId) {
		fire_ShipmentFailed_response_done(correlationId);
	}
	
	protected ShipmentRequestMessage createShipmentRequestMessage(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		String serviceId = bookshop2.supplier.incoming.shipBooks.ShipBooks.ID;
		ShipmentRequestMessage message = supplierContext.createShipmentRequestMessage(serviceId);
		supplierContext.addReplyTo_Supplier_ShipmentComplete(message);
		supplierContext.addReplyTo_Supplier_ShipmentFailed(message);
		supplierContext.addReplyTo_Supplier_ShipmentScheduled(message);
		return message;
	}
	
	protected void reply_BooksUnavailable(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		String serviceId = bookshop2.supplier.incoming.queryBooks.QueryBooks.ID;
		BooksUnavailableMessage message = supplierContext.createBooksUnavailableMessage(serviceId);
		reply_BooksUnavailable(message);
	}
	
	protected void reply_BooksUnavailable(BooksUnavailableMessage message) {
		BooksUnavailableReply replyTo = getReplyTo(BooksUnavailableReply.ID);
		replyTo.booksUnavailable(message);
		String correlationId = message.getCorrelationId();
		fire_BooksUnavailable_response_sent(correlationId);
	}

	protected void reply_BooksAvailable(String correlationId, Collection<Book> bookList) {
		SupplierContext supplierContext = getContext(correlationId);
		String serviceId = bookshop2.supplier.incoming.queryBooks.QueryBooks.ID;
		BooksAvailableMessage message = supplierContext.createBooksAvailableMessage(serviceId);
		message.setBooks(bookList);
		reply_BooksAvailable(message);
	}

	protected void reply_BooksAvailable(BooksAvailableMessage message) {
		BooksAvailableReply replyTo = getReplyTo(BooksAvailableReply.ID);
		replyTo.booksAvailable(message);
		String correlationId = message.getCorrelationId();
		fire_BooksAvailable_response_sent(correlationId);
	}

	protected void send_Shipper_ShipBooks_request(String correlationId) {
		ShipmentRequestMessage message = createShipmentRequestMessage(correlationId);
		send_Shipper_ShipBooks_request(message);
	}
	
	protected void send_Shipper_ShipBooks_request_cancel(String correlationId) {
		ShipmentRequestMessage message = createShipmentRequestMessage(correlationId);
		message.setCancelRequest(true);
		send_Shipper_ShipBooks_request(message);
	}
	
	protected void send_Shipper_ShipBooks_request_undo(String correlationId, Shipment shipment, Payment payment) {
		ShipmentRequestMessage message = createShipmentRequestMessage(correlationId);
		message.setShipment(shipment);
		message.setPayment(payment);
		message.setUndoRequest(true);
		send_Shipper_ShipBooks_request(message);
	}
	
	protected void send_Shipper_ShipBooks_request(ShipmentRequestMessage message) {
		String correlationId = message.getCorrelationId();
		Runnable timeoutHandler = create_Shipper_ShipBooks_request_timeoutHandler(correlationId);
		try {
			send_Shipper_ShipBooks_request(message, timeoutHandler);
		} catch (Throwable e) {
			e = ExceptionUtil.getRootCause(e);
			handle_Shipper_ShipBooks_request_exception(correlationId, e);
		}
	}
	
	protected void send_Shipper_ShipBooks_request(ShipmentRequestMessage message, Runnable timeoutHandler) {
		TimeoutHandler timeoutHelper = startTimeoutHandler(timeoutHandler, shipBooksTimeout);
		String correlationId = message.getCorrelationId();
		try {
			ShipBooks client = getClient(ShipBooks.ID);
			client.shipBooks(message);
			logState_Shipper_ShipBooks(message);
			fire_ShipBooks_request_sent(correlationId);
		} finally {
			timeoutHelper.reset();
		}
	}

	protected Runnable create_Shipper_ShipBooks_request_timeoutHandler(final String correlationId) {
		return new Runnable() {
			public void run() {
				shipBooksTimeout = DEFAULT_TIMEOUT;
				String reason = "ShipBooks timed-out";
				handle_Shipper_ShipBooks_request_timeout(correlationId, reason);
			}
		};
	}
	
	protected void reply_ShipmentScheduled(String correlationId, Shipment shipment) {
		SupplierContext supplierContext = getContext(correlationId);
		String serviceId = bookshop2.supplier.incoming.shipBooks.ShipBooks.ID;
		ShipmentScheduledMessage message = supplierContext.createShipmentScheduledMessage(serviceId);
		message.setShipment(shipment);
		reply_ShipmentScheduled(message);
	}
	
	protected void reply_ShipmentScheduled(ShipmentScheduledMessage message) {
		ShipmentScheduledReply client = getClient(ShipmentScheduledReply.ID);
		client.shipmentScheduled(message);
		String correlationId = message.getCorrelationId();
		fire_ShipmentScheduled_response_sent(correlationId);
	}
	
	protected void reply_ShipmentComplete(String correlationId, Shipment shipment) {
		SupplierContext supplierContext = getContext(correlationId);
		String serviceId = bookshop2.supplier.incoming.shipBooks.ShipBooks.ID;
		ShipmentCompleteMessage message = supplierContext.createShipmentCompleteMessage(serviceId);
		message.setShipment(shipment);
		reply_ShipmentComplete(message);
	}
	
	protected void reply_ShipmentComplete(ShipmentCompleteMessage message) {
		ShipmentCompleteReply client = getClient(ShipmentCompleteReply.ID);
		client.shipmentComplete(message);
		String correlationId = message.getCorrelationId();
		fire_ShipmentComplete_response_sent(correlationId);
	}
	
	protected void reply_ShipmentFailed(String correlationId, Shipment shipment) {
		SupplierContext supplierContext = getContext(correlationId);
		String serviceId = bookshop2.supplier.incoming.shipBooks.ShipBooks.ID;
		ShipmentFailedMessage message = supplierContext.createShipmentFailedMessage(serviceId);
		message.setShipment(shipment);
		reply_ShipmentFailed(message);
	}
	
	protected void reply_ShipmentFailed(ShipmentFailedMessage message) {
		ShipmentFailedReply client = getClient(ShipmentFailedReply.ID);
		client.shipmentFailed(message);
		String correlationId = message.getCorrelationId();
		fire_ShipmentFailed_response_sent(correlationId);
	}
	
	protected void reply_ShipmentFailed(String correlationId, Throwable cause) {
		SupplierContext supplierContext = getContext(correlationId);
		String serviceId = bookshop2.supplier.incoming.shipBooks.ShipBooks.ID;
		ShipmentFailedMessage message = supplierContext.createShipmentFailedMessage(serviceId);
		message.setReason(cause.getMessage());
		reply_ShipmentFailed(message);
	}
	
	protected void post_ShipmentComplete(Shipment shipment) {
		ShipmentCompleteEvent event = new ShipmentCompleteEvent();
		event.setShipment(shipment);
		post_ShipmentComplete(event);
	}
	
	protected void post_ShipmentComplete(ShipmentCompleteEvent event) {
		supplierEventMulticaster.fireEvent(event);
		String correlationId = event.getCorrelationId();
		fire_ShipmentComplete_event_posted(correlationId);
	}
	
	protected void cancel_Shipper_ShipBooks(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		String clientId = bookshop2.shipper.client.shipBooks.ShipBooks.ID;
		ActionState actionState = supplierContext.getActionState(correlationId, "shipBooks");
		if (actionState != null) {
			send_Shipper_ShipBooks_request_cancel(clientId);
		}
	}
	
	protected void undo_Shipper_ShipBooks(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		String clientId = bookshop2.shipper.client.shipBooks.ShipBooks.ID;
		ActionState actionState = supplierContext.getActionState(correlationId, "shipBooks");
		if (actionState != null) {
			Shipment shipment = actionState.getElement("shipment");
			Payment payment = actionState.getElement("payment");
			send_Shipper_ShipBooks_request_undo(clientId, shipment, payment);
		}
	}
	
	protected void undo_SupplierOrderCache_removeFromBooksInStock(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		ActionState actionState = supplierContext.getActionState(correlationId, "removeFromBooksInStock");
		if (actionState != null) {
			Set<Book> bookSet = actionState.getElement("bookSet");
			supplierOrderCacheManager.addToBooksInStock(bookSet);
		}
	}
	
	protected void logState_Shipper_ShipBooks(ShipmentRequestMessage message) {
		ActionState actionState = createActionState("shipBooks", message);
		actionState.addElement("shipment", message.getShipment());
		actionState.addElement("payment", message.getPayment());
		String correlationId = message.getCorrelationId();
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.logAction(actionState);
	}
	
	protected void fire_QueryBooks_request_received(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_QueryBooks_request_received();
	}
	
	protected void fire_QueryBooks_request_handled(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_QueryBooks_request_handled();
	}
	
	protected void fire_QueryBooks_request_done(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_QueryBooks_request_done();
	}
	
	protected void fire_QueryBooks_request_cancel_done(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_QueryBooks_request_cancel_done();
	}
	
	protected void fire_QueryBooks_request_undo_done(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_QueryBooks_request_undo_done();
	}
	
	protected void fire_QueryBooks_incoming_request_aborted(String correlationId, Throwable cause) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_QueryBooks_incoming_request_aborted(cause);
	}
	
	protected void fire_QueryBooks_incoming_request_aborted(String correlationId, String reason) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_QueryBooks_incoming_request_aborted(reason);
	}
	
	protected void fire_QueryBooks_error_sent(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_QueryBooks_error_sent();
	}
	
	protected void fire_BooksUnavailable_response_sent(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_BooksUnavailable_response_sent();
	}
	
	protected void fire_BooksUnavailable_outgoing_response_aborted(String correlationId, Throwable cause) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_BooksUnavailable_outgoing_response_aborted(cause);
	}
	
	protected void fire_BooksUnavailable_outgoing_response_aborted(String correlationId, String reason) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_BooksUnavailable_outgoing_response_aborted(reason);
	}
	
	protected void fire_BooksAvailable_response_sent(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_BooksAvailable_response_sent();
	}
	
	protected void fire_BooksAvailable_outgoing_response_aborted(String correlationId, Throwable cause) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_BooksAvailable_outgoing_response_aborted(cause);
	}
	
	protected void fire_BooksAvailable_outgoing_response_aborted(String correlationId, String reason) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_BooksAvailable_outgoing_response_aborted(reason);
	}
	
	protected void fire_ReserveBooks_request_received(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ReserveBooks_request_received();
	}
	
	protected void fire_ReserveBooks_request_handled(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ReserveBooks_request_handled();
	}
	
	protected void fire_ReserveBooks_request_done(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ReserveBooks_request_done();
	}
	
	protected void fire_ReserveBooks_request_cancel_done(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ReserveBooks_request_cancel_done();
	}
	
	protected void fire_ReserveBooks_request_undo_done(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ReserveBooks_request_undo_done();
	}
	
	protected void fire_ReserveBooks_incoming_request_aborted(String correlationId, Throwable cause) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ReserveBooks_incoming_request_aborted(cause);
	}
	
	protected void fire_ReserveBooks_incoming_request_aborted(String correlationId, String reason) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ReserveBooks_incoming_request_aborted(reason);
	}
	
	protected void fire_ReserveBooks_error_sent(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ReserveBooks_error_sent();
	}
	
	protected void fire_ShipBooks_request_received(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ShipBooks_request_received();
	}
	
	protected void fire_ShipBooks_request_handled(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ShipBooks_request_handled();
	}
	
	protected void fire_ShipBooks_request_done(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ShipBooks_request_done();
	}
	
	protected void fire_ShipBooks_request_cancel_done(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ShipBooks_request_cancel_done();
	}
	
	protected void fire_ShipBooks_request_undo_done(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ShipBooks_request_undo_done();
	}
	
	protected void fire_ShipBooks_incoming_request_aborted(String correlationId, Throwable cause) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ShipBooks_incoming_request_aborted(cause);
	}
	
	protected void fire_ShipBooks_incoming_request_aborted(String correlationId, String reason) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ShipBooks_incoming_request_aborted(reason);
	}
	
	protected void fire_ShipBooks_error_sent(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ShipBooks_error_sent();
	}
	
	protected void fire_ShipmentScheduled_response_sent(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ShipmentScheduled_response_sent();
	}
	
	protected void fire_ShipmentScheduled_outgoing_response_aborted(String correlationId, Throwable cause) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ShipmentScheduled_outgoing_response_aborted(cause);
	}
	
	protected void fire_ShipmentScheduled_outgoing_response_aborted(String correlationId, String reason) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ShipmentScheduled_outgoing_response_aborted(reason);
	}
	
	protected void fire_ShipmentComplete_response_sent(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ShipmentComplete_response_sent();
	}
	
	protected void fire_ShipmentComplete_outgoing_response_aborted(String correlationId, Throwable cause) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ShipmentComplete_outgoing_response_aborted(cause);
	}
	
	protected void fire_ShipmentComplete_outgoing_response_aborted(String correlationId, String reason) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ShipmentComplete_outgoing_response_aborted(reason);
	}
	
	protected void fire_ShipmentFailed_response_sent(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ShipmentFailed_response_sent();
	}
	
	protected void fire_ShipmentFailed_outgoing_response_aborted(String correlationId, Throwable cause) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ShipmentFailed_outgoing_response_aborted(cause);
	}
	
	protected void fire_ShipmentFailed_outgoing_response_aborted(String correlationId, String reason) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ShipmentFailed_outgoing_response_aborted(reason);
	}
	
	protected void fire_ShipBooks_request_sent(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ShipBooks_request_sent();
	}
	
	protected void fire_ShipBooks_outgoing_request_aborted(String correlationId, Throwable cause) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ShipBooks_outgoing_request_aborted(cause);
	}
	
	protected void fire_ShipBooks_outgoing_request_aborted(String correlationId, String reason) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ShipBooks_outgoing_request_aborted(reason);
	}
	
	protected void fire_ShipBooks_error_received(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ShipBooks_error_received();
	}
	
	protected void fire_ShipmentScheduled_response_received(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ShipmentScheduled_response_received();
	}
	
	protected void fire_ShipmentScheduled_response_handled(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ShipmentScheduled_response_handled();
	}
	
	protected void fire_ShipmentScheduled_response_done(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ShipmentScheduled_response_done();
	}
	
	protected void fire_ShipmentScheduled_incoming_response_aborted(String correlationId, Throwable cause) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ShipmentScheduled_incoming_response_aborted(cause);
	}
	
	protected void fire_ShipmentScheduled_incoming_response_aborted(String correlationId, String reason) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ShipmentScheduled_incoming_response_aborted(reason);
	}
	
	protected void fire_ShipmentComplete_response_received(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ShipmentComplete_response_received();
	}
	
	protected void fire_ShipmentComplete_response_handled(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ShipmentComplete_response_handled();
	}
	
	protected void fire_ShipmentComplete_response_done(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ShipmentComplete_response_done();
	}
	
	protected void fire_ShipmentComplete_incoming_response_aborted(String correlationId, Throwable cause) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ShipmentComplete_incoming_response_aborted(cause);
	}
	
	protected void fire_ShipmentComplete_incoming_response_aborted(String correlationId, String reason) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ShipmentComplete_incoming_response_aborted(reason);
	}
	
	protected void fire_ShipmentFailed_response_received(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ShipmentFailed_response_received();
	}
	
	protected void fire_ShipmentFailed_response_handled(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ShipmentFailed_response_handled();
	}
	
	protected void fire_ShipmentFailed_response_done(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ShipmentFailed_response_done();
	}
	
	protected void fire_ShipmentFailed_incoming_response_aborted(String correlationId, Throwable cause) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ShipmentFailed_incoming_response_aborted(cause);
	}
	
	protected void fire_ShipmentFailed_incoming_response_aborted(String correlationId, String reason) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ShipmentFailed_incoming_response_aborted(reason);
	}
	
	protected void fire_ShipmentComplete_event_posted(String correlationId) {
		SupplierContext supplierContext = getContext(correlationId);
		supplierContext.fire_ShipmentComplete_event_posted();
	}
	
}
