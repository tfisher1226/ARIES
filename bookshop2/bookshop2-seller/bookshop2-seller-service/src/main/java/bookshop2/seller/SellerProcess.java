package bookshop2.seller;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.Date;
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

import org.aries.common.PersonName;
import org.aries.common.StreetAddress;
import org.aries.process.AbstractProcess;
import org.aries.process.ActionState;
import org.aries.process.TimeoutHandler;
import org.aries.runtime.RequestContext;
import org.aries.util.ExceptionUtil;

import bookshop2.Book;
import bookshop2.BooksAvailableMessage;
import bookshop2.BooksUnavailableMessage;
import bookshop2.Invoice;
import bookshop2.Order;
import bookshop2.OrderAcceptedMessage;
import bookshop2.OrderRejectedMessage;
import bookshop2.OrderRequestMessage;
import bookshop2.Payment;
import bookshop2.PurchaseAcceptedMessage;
import bookshop2.PurchaseCompleteEvent;
import bookshop2.PurchaseRejectedMessage;
import bookshop2.PurchaseRequestMessage;
import bookshop2.QueryRequestMessage;
import bookshop2.ReservationAbortedException;
import bookshop2.ReservationRequestMessage;
import bookshop2.Shipment;
import bookshop2.ShipmentCompleteMessage;
import bookshop2.ShipmentFailedMessage;
import bookshop2.ShipmentRequestMessage;
import bookshop2.ShipmentScheduledMessage;
import bookshop2.seller.data.bookCache.BookCacheManager;
import bookshop2.seller.event.SellerEventMulticaster;
import bookshop2.seller.outgoing.orderAccepted.OrderAcceptedReply;
import bookshop2.seller.outgoing.orderRejected.OrderRejectedReply;
import bookshop2.seller.outgoing.purchaseAccepted.PurchaseAcceptedReply;
import bookshop2.seller.outgoing.purchaseRejected.PurchaseRejectedReply;
import bookshop2.supplier.client.queryBooks.QueryBooks;
import bookshop2.supplier.client.reserveBooks.ReserveBooks;
import bookshop2.supplier.client.shipBooks.ShipBooks;

import common.jmx.MBeanUtil;


@Startup
@Singleton
@LocalBean
@AccessTimeout(value = 60000)
@ConcurrencyManagement(BEAN)
@TransactionAttribute(REQUIRED)
@TransactionManagement(CONTAINER)
public class SellerProcess extends AbstractProcess implements SellerProcessMBean {

	public static long DEFAULT_TIMEOUT = 60000L;
	
	@Inject
	private RequestContext requestContext;
	
	@Inject
	protected BookCacheManager bookCacheManager;

	@Inject
	private SellerEventMulticaster sellerEventMulticaster;

	private bookshop2.ObjectFactory bookshop2ObjectFactory;
	
	private long queryBooksTimeout = DEFAULT_TIMEOUT;
	
	private long reserveBooksTimeout = DEFAULT_TIMEOUT;
	
	private long shipBooksTimeout = DEFAULT_TIMEOUT;
	
	
	public SellerProcess() {
		bookshop2ObjectFactory = new bookshop2.ObjectFactory();
	}
	
	
	@Override
	public String getDomainId() {
		return "bookshop2.seller";
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
		bookCacheManager.updateState();
	}
	
	protected SellerContext getContext(String correlationId) {
		SellerContext sellerContext = SellerContext.getInstance(correlationId);
		return sellerContext;
	}
	
//	Map<String, Thread> activeThreads = new HashMap<String, Thread>();

	//public void handle_OrderBooks_request(OrderRequestMessage message) throws SellerException {
	public void handle_OrderBooks_request(OrderRequestMessage message) {
		String serviceId = bookshop2.seller.incoming.orderBooks.OrderBooks.ID;
		//activeThreads.put(message.getCorrelationId(), Thread.currentThread());
		String correlationId = message.getCorrelationId();
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.initializeContext(serviceId, message);
		Order order = message.getOrder();
		Set<Book> books = order.getBooks();
		//TODO set timer for timeout
		send_Supplier_QueryBooks_request(correlationId, books);
		fire_OrderBooks_request_handled(correlationId);
	}
	
//	public void handle_OrderBooks_request_cancel() {
//		OrderRequestMessage message = sellerContext.createOrderRequestMessage();
//		//message.setCancelRequest(true);
//		handle_OrderBooks_request_cancel(message);
//	}
	
	public void handle_OrderBooks_request_cancel(OrderRequestMessage message) {
		String serviceId = bookshop2.seller.incoming.orderBooks.OrderBooks.ID;
		String correlationId = message.getCorrelationId();
//		Thread thread = activeThreads.get(correlationId);
//		if (thread != null)
//			thread.interrupt();
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.initializeContext(serviceId, message);
		cancel_Supplier_QueryBooks(correlationId);
		cancel_Supplier_ReserveBooks(correlationId);
		handle_OrderBooks_request_undo(message);
		fire_OrderBooks_request_cancel_done(correlationId);
	}
	
	public void handle_OrderBooks_request_undo(OrderRequestMessage message) {
		String correlationId = message.getCorrelationId();
		undo_Supplier_QueryBooks(correlationId);
		undo_Supplier_ReserveBooks(correlationId);
		undo_BookCache_addToAvailableBooks(correlationId);
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
	
	protected void handle_Supplier_QueryBooks_request_exception(String correlationId, Throwable cause) {
		SellerContext sellerContext = getContext(correlationId);
		Order order = sellerContext.getOrder();
		reply_OrderRejected(correlationId, order, cause);
		fire_QueryBooks_outgoing_request_aborted(correlationId, cause);
		fire_OrderBooks_request_handled(correlationId);
		//TODO take this out from here:
		handle_OrderBooks_request_done(correlationId);
	}
	
	protected void handle_Supplier_QueryBooks_request_timeout(String correlationId, String reason) {
		SellerContext sellerContext = getContext(correlationId);
		Order order = sellerContext.getOrder();
		reply_OrderRejected(correlationId, order, reason);
		fire_QueryBooks_outgoing_request_aborted(correlationId, reason);
		fire_OrderBooks_request_handled(correlationId);
		//TODO take this out from here:
		handle_OrderBooks_request_done(correlationId);
	}
	
	public void handle_BooksAvailable_response(BooksAvailableMessage message) {
		String correlationId = message.getCorrelationId();
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.initializeContext(message);
		Set<Book> books = sellerContext.getBooks();
		Order order = sellerContext.getOrder();
		bookCacheManager.addToAvailableBooks(books);
		send_Supplier_ReserveBooks_request(correlationId, books);
		order.setBooks(books);
		reply_OrderAccepted(correlationId, order);
		handle_BooksAvailable_response_done(correlationId);
		fire_OrderBooks_request_handled(correlationId);
		handle_OrderBooks_request_done(correlationId);
	}
	
	public void handle_BooksAvailable_response_done(String correlationId) {
		fire_BooksAvailable_response_done(correlationId);
	}
	
	protected void handle_Supplier_ReserveBooks_request_exception(String correlationId, Throwable cause) {
		fire_ReserveBooks_outgoing_request_aborted(correlationId, cause);
	}
	
	protected void handle_Supplier_ReserveBooks_request_timeout(String correlationId, String reason) {
		fire_ReserveBooks_outgoing_request_aborted(correlationId, reason);
	}
	
	public void handle_BooksUnavailable_response(BooksUnavailableMessage message) {
		String correlationId = message.getCorrelationId();
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.initializeContext(message);
		Set<Book> books = sellerContext.getBooks();
		Order order = sellerContext.getOrder();
		order.setBooks(books);
		String reason = "Books Unavailable";
		reply_OrderRejected(correlationId, order, reason);
		handle_BooksUnavailable_response_done(correlationId);
		fire_OrderBooks_request_handled(correlationId);
		handle_OrderBooks_request_done(correlationId);
	}
	
	public void handle_BooksUnavailable_response_done(String correlationId) {
		fire_BooksUnavailable_response_done(correlationId);
	}
	
	public void handle_PurchaseBooks_request(PurchaseRequestMessage message) {
		String serviceId = bookshop2.seller.incoming.purchaseBooks.PurchaseBooks.ID;
		String correlationId = message.getCorrelationId();
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.initializeContext(serviceId, message);
		Order order = sellerContext.getOrder();
		PersonName name = sellerContext.getName();
		StreetAddress address = sellerContext.getAddress();
		Set<Book> requestedBooks = order.getBooks();
		Payment payment = message.getPayment();
		Set<Book> availableBooks = bookCacheManager.getMatchingAvailableBooks(requestedBooks);
		if (availableBooks.size() == 0) {
			String reason = "No books available";
			reply_PurchaseRejected(correlationId, reason);
			fire_PurchaseBooks_request_handled(correlationId);
			handle_PurchaseBooks_request_done(correlationId);
			return;
		}
		List<Book> bookOrders = bookCacheManager.getAllBookOrders();
		if (bookOrders.size() < 4) {
			bookCacheManager.removeFromAvailableBooks(availableBooks);
			Invoice invoice = bookshop2ObjectFactory.createInvoice();
			invoice.setOrder(order);
			invoice.setPayment(payment);
			Date dateTime = new Date();
			invoice.setDateTime(dateTime);
			sellerContext.setInvoice(invoice);
			Shipment shipment = bookshop2ObjectFactory.createShipment();
			shipment.setOrder(order);
			shipment.setTime(dateTime);
			shipment.setDate(dateTime);
			shipment.setName(name);
			shipment.setAddress(address);
			sellerContext.setShipment(shipment);
			//TODO set timer for timeout
			send_Supplier_ShipBooks_request(correlationId, shipment, payment);
		}
		fire_PurchaseBooks_request_handled(correlationId);
	}
	
	public void handle_PurchaseBooks_request_cancel(PurchaseRequestMessage message) {
		String serviceId = bookshop2.seller.incoming.purchaseBooks.PurchaseBooks.ID;
		String correlationId = message.getCorrelationId();
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.initializeContext(serviceId, message);
		cancel_Supplier_ShipBooks(correlationId);
		handle_PurchaseBooks_request_undo(message);
		fire_PurchaseBooks_request_cancel_done(correlationId);
	}
	
	public void handle_PurchaseBooks_request_undo(PurchaseRequestMessage message) {
		String correlationId = message.getCorrelationId();
		undo_Supplier_ShipBooks(correlationId);
		undo_BookCache_removeFromAvailableBooks(correlationId);
		undo_BookCache_addToBookOrders(correlationId);
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
	
	protected void handle_Supplier_ShipBooks_request_exception(String correlationId, Throwable cause) {
		reply_PurchaseRejected(correlationId, cause);
		fire_ShipBooks_outgoing_request_aborted(correlationId, cause);
		fire_PurchaseBooks_request_handled(correlationId);
		handle_PurchaseBooks_request_done(correlationId);
	}
	
	protected void handle_Supplier_ShipBooks_request_timeout(String correlationId, String reason) {
		fire_ShipBooks_outgoing_request_aborted(correlationId, reason);
	}
	
	public void handle_ShipmentScheduled_response(ShipmentScheduledMessage message) {
		String serviceId = bookshop2.supplier.client.shipBooks.ShipBooks.ID;
		String correlationId = message.getCorrelationId();
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.initializeContext(message);
		Shipment shipment = sellerContext.getShipment();
		bookCacheManager.addToBookOrders(shipment.getOrder().getBooks());
		handle_ShipmentScheduled_response_done(correlationId);
	}
	
	public void handle_ShipmentScheduled_response_done(String correlationId) {
		fire_ShipmentScheduled_response_done(correlationId);
	}
	
	public void handle_ShipmentComplete_response(ShipmentCompleteMessage message) {
		//String responseId = bookshop2.seller.incoming.purchaseBooks.ShipmentComplete.ID;
		String correlationId = message.getCorrelationId();
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.initializeContext(message);
		reply_PurchaseAccepted(correlationId);
		post_PurchaseComplete();
		handle_ShipmentComplete_response_done(correlationId);
		fire_PurchaseBooks_request_handled(correlationId);
		handle_PurchaseBooks_request_done(correlationId);
	}
	
	public void handle_ShipmentComplete_response_done(String correlationId) {
		fire_ShipmentComplete_response_done(correlationId);
	}
	
	public void handle_ShipmentFailed_response(ShipmentFailedMessage message) {
		//String serviceId = bookshop2.supplier.client.shipBooks.ShipBooks.ID;
		String correlationId = message.getCorrelationId();
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.initializeContext(message);
		reply_PurchaseRejected(correlationId);
		handle_ShipmentFailed_response_done(correlationId);
		fire_PurchaseBooks_request_handled(correlationId);
		handle_PurchaseBooks_request_done(correlationId);
	}
	
	public void handle_ShipmentFailed_response_done(String correlationId) {
		fire_ShipmentFailed_response_done(correlationId);
	}

	protected QueryRequestMessage createQueryRequestMessage(String correlationId) {
		String serviceId = bookshop2.supplier.client.queryBooks.QueryBooks.ID;
		SellerContext sellerContext = getContext(correlationId);
		QueryRequestMessage message = sellerContext.createQueryRequestMessage(serviceId);
		sellerContext.addReplyTo_Seller_BooksAvailable(message);
		sellerContext.addReplyTo_Seller_BooksUnavailable(message);
		return message;
	}
	
	protected ReservationRequestMessage createReservationRequestMessage(String correlationId) {
		String serviceId = bookshop2.supplier.client.reserveBooks.ReserveBooks.ID;
		SellerContext sellerContext = getContext(correlationId);
		ReservationRequestMessage message = sellerContext.createReservationRequestMessage(serviceId);
		return message;
	}
	
	protected ShipmentRequestMessage createShipmentRequestMessage(String correlationId) {
		String serviceId = bookshop2.supplier.client.shipBooks.ShipBooks.ID;
		SellerContext sellerContext = getContext(correlationId);
		ShipmentRequestMessage message = sellerContext.createShipmentRequestMessage(serviceId);
		sellerContext.addReplyTo_Seller_ShipmentComplete(message);
		sellerContext.addReplyTo_Seller_ShipmentFailed(message);
		sellerContext.addReplyTo_Seller_ShipmentScheduled(message);
		return message;
	}
	
	protected void send_Supplier_QueryBooks_request(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		Set<Book> books = sellerContext.getBooks();
		send_Supplier_QueryBooks_request(correlationId, books);
	}
	
	protected void send_Supplier_QueryBooks_request(String correlationId, Set<Book> books) {
		QueryRequestMessage message = createQueryRequestMessage(correlationId);
		message.setBooks(books);
		send_Supplier_QueryBooks_request(message);
	}

	protected void send_Supplier_QueryBooks_request_cancel(String correlationId) {
		QueryRequestMessage message = createQueryRequestMessage(correlationId);
		message.setCancelRequest(true);
		send_Supplier_QueryBooks_request(message);
	}
	
	protected void send_Supplier_QueryBooks_request_undo(String correlationId, Set<Book> books) {
		QueryRequestMessage message = createQueryRequestMessage(correlationId);
		message.setBooks(books);
		message.setUndoRequest(true);
		send_Supplier_QueryBooks_request(message);
	}
		
	protected void send_Supplier_QueryBooks_request(QueryRequestMessage message) {
		String correlationId = message.getCorrelationId();
		Runnable timeoutHandler = create_Supplier_QueryBooks_request_timeoutHandler(correlationId);
		try {
			send_Supplier_QueryBooks_request(message, timeoutHandler);
		} catch (Throwable e) {
			e = ExceptionUtil.getRootCause(e);
			handle_Supplier_QueryBooks_request_exception(correlationId, e);
		}
	}

	protected void send_Supplier_QueryBooks_request(QueryRequestMessage message, Runnable timeoutHandler) {
		TimeoutHandler timeoutHelper = startTimeoutHandler(timeoutHandler, queryBooksTimeout);
		String correlationId = message.getCorrelationId();
		try {
			QueryBooks client = getClient(QueryBooks.ID);
			client.queryBooks(message);
			logState_Supplier_QueryBooks(message);
			fire_QueryBooks_request_sent(correlationId);
		} finally {
			timeoutHelper.reset();
		}
	}
	
	protected Runnable create_Supplier_QueryBooks_request_timeoutHandler(final String correlationId) {
		return new Runnable() {
			public void run() {
				queryBooksTimeout = DEFAULT_TIMEOUT;
				String reason = "QueryBooks timed-out";
				handle_Supplier_QueryBooks_request_timeout(correlationId, reason);
			}
		};
	}
	
	protected void reply_OrderAccepted(String correlationId, Order order) {
		SellerContext sellerContext = getContext(correlationId);
		String serviceId = bookshop2.seller.incoming.orderBooks.OrderBooks.ID;
		OrderAcceptedMessage message = sellerContext.createOrderAcceptedMessage(serviceId);
		message.setOrder(order);
		reply_OrderAccepted(message);
	}
	
	protected void reply_OrderAccepted(OrderAcceptedMessage message) {
		OrderAcceptedReply replyTo = getReplyTo(OrderAcceptedReply.ID);
		replyTo.orderAccepted(message);
		String correlationId = message.getCorrelationId();
		fire_OrderAccepted_response_sent(correlationId);
	}
	
	protected void reply_OrderRejected(String correlationId, Order order, String reason) {
		SellerContext sellerContext = getContext(correlationId);
		String serviceId = bookshop2.seller.incoming.orderBooks.OrderBooks.ID;
		OrderRejectedMessage message = sellerContext.createOrderRejectedMessage(serviceId);
		message.setOrder(order);
		message.setReason(reason);
		reply_OrderRejected(message);
	}

	protected void reply_OrderRejected(OrderRejectedMessage message) {
		OrderRejectedReply replyTo = getReplyTo(OrderRejectedReply.ID);
		replyTo.orderRejected(message);
		String correlationId = message.getCorrelationId();
		fire_OrderRejected_response_sent(correlationId);
	}

	protected void reply_OrderRejected(String correlationId, Order order, Throwable cause) {
		SellerContext sellerContext = getContext(correlationId);
		String serviceId = bookshop2.seller.incoming.orderBooks.OrderBooks.ID;
		OrderRejectedMessage message = sellerContext.createOrderRejectedMessage(serviceId);
		message.setOrder(order);
		message.setReason(cause.getMessage());
		reply_OrderRejected(message);
	}
	
	protected void send_Supplier_ReserveBooks_request(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		Set<Book> books = sellerContext.getBooks();
		send_Supplier_ReserveBooks_request(correlationId, books);
	}
	
	protected void send_Supplier_ReserveBooks_request(String correlationId, Set<Book> books) {
		ReservationRequestMessage message = createReservationRequestMessage(correlationId);
		message.setBooks(books);
		send_Supplier_ReserveBooks_request(message);
	}

	protected void send_Supplier_ReserveBooks_request_cancel(String correlationId) {
		ReservationRequestMessage message = createReservationRequestMessage(correlationId);
		message.setCancelRequest(true);
		send_Supplier_ReserveBooks_request(message);
	}
	
	protected void send_Supplier_ReserveBooks_request_undo(String correlationId, Set<Book> books) {
		ReservationRequestMessage message = createReservationRequestMessage(correlationId);
		message.setBooks(books);
		message.setUndoRequest(true);
		send_Supplier_ReserveBooks_request(message);
	}
	
	protected void send_Supplier_ReserveBooks_request(ReservationRequestMessage message) {
		String correlationId = message.getCorrelationId();		
		Runnable timeoutHandler = create_Supplier_ReserveBooks_request_timeoutHandler(correlationId);
		try {
			send_Supplier_ReserveBooks_request(message, timeoutHandler);
		} catch (ReservationAbortedException e) {
			handle_Supplier_ReserveBooks_request_exception(correlationId, e);
		} catch (Throwable e) {
			e = ExceptionUtil.getRootCause(e);
			handle_Supplier_ReserveBooks_request_exception(correlationId, e);
		}
	}
	
	protected void send_Supplier_ReserveBooks_request(ReservationRequestMessage message, Runnable timeoutHandler) throws ReservationAbortedException {
		TimeoutHandler timeoutHelper = startTimeoutHandler(timeoutHandler, reserveBooksTimeout);
		String correlationId = message.getCorrelationId();
		try {
			ReserveBooks client = getClient(ReserveBooks.ID);
			client.reserveBooks(message);
			logState_Supplier_ReserveBooks(message);
			fire_ReserveBooks_request_sent(correlationId);
		} finally {
			timeoutHelper.reset();
		}
	}
	
	protected Runnable create_Supplier_ReserveBooks_request_timeoutHandler(final String correlationId) {
		return new Runnable() {
			public void run() {
				reserveBooksTimeout = DEFAULT_TIMEOUT;
				String reason = "ReserveBooks timed-out";
				handle_Supplier_ReserveBooks_request_timeout(correlationId, reason);
			}
		};
	}
	
	protected void reply_PurchaseRejected(String correlationId, String reason) {
		SellerContext sellerContext = getContext(correlationId);
		String serviceId = bookshop2.seller.incoming.purchaseBooks.PurchaseBooks.ID;
		PurchaseRejectedMessage message = sellerContext.createPurchaseRejectedMessage(serviceId);
		message.setReason(reason);
		reply_PurchaseRejected(message);
	}
	
	protected void reply_PurchaseRejected(PurchaseRejectedMessage message) {
		PurchaseRejectedReply client = getClient(PurchaseRejectedReply.ID);
		client.purchaseRejected(message);
		String correlationId = message.getCorrelationId();
		fire_PurchaseRejected_response_sent(correlationId);
	}
	
	protected void send_Supplier_ShipBooks_request(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		Shipment shipment = sellerContext.getShipment();
		Payment payment = sellerContext.getPayment();
		send_Supplier_ShipBooks_request(correlationId, shipment, payment);
	}
	
	protected void send_Supplier_ShipBooks_request(String correlationId, Shipment shipment, Payment payment) {
		ShipmentRequestMessage message = createShipmentRequestMessage(correlationId);
		message.setShipment(shipment);
		message.setPayment(payment);
		send_Supplier_ShipBooks_request(message);
	}
	
	protected void send_Supplier_ShipBooks_request_cancel(String correlationId) {
		ShipmentRequestMessage message = createShipmentRequestMessage(correlationId);
		message.setCancelRequest(true);
		send_Supplier_ShipBooks_request(message);
	}
	
	protected void send_Supplier_ShipBooks_request_undo(String correlationId, Shipment shipment, Payment payment) {
		ShipmentRequestMessage message = createShipmentRequestMessage(correlationId);
		message.setShipment(shipment);
		message.setPayment(payment);
		message.setUndoRequest(true);
		send_Supplier_ShipBooks_request(message);
	}
	
	protected void send_Supplier_ShipBooks_request(ShipmentRequestMessage message) {
		String correlationId = message.getCorrelationId();
		Runnable timeoutHandler = create_Supplier_ShipBooks_request_timeoutHandler(correlationId);
		try {
			send_Supplier_ShipBooks_request(message, timeoutHandler);
		} catch (Throwable e) {
			e = ExceptionUtil.getRootCause(e);
			handle_Supplier_ShipBooks_request_exception(correlationId, e);
		}
	}
	
	protected void send_Supplier_ShipBooks_request(ShipmentRequestMessage message, Runnable timeoutHandler) {
		TimeoutHandler timeoutHelper = startTimeoutHandler(timeoutHandler, shipBooksTimeout);
		String correlationId = message.getCorrelationId();
		try {
			ShipBooks client = getClient(ShipBooks.ID);
			client.shipBooks(message);
			logState_Supplier_ShipBooks(message);
			fire_ShipBooks_request_sent(correlationId);
		} finally {
			timeoutHelper.reset();
		}
	}
	
	protected Runnable create_Supplier_ShipBooks_request_timeoutHandler(final String correlationId) {
		return new Runnable() {
			public void run() {
				shipBooksTimeout = DEFAULT_TIMEOUT;
				String reason = "ShipBooks timed-out";
				handle_Supplier_ShipBooks_request_timeout(correlationId, reason);
			}
		};
	}
	
	protected void reply_PurchaseAccepted(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		String serviceId = bookshop2.seller.incoming.purchaseBooks.PurchaseBooks.ID;
		PurchaseAcceptedMessage message = sellerContext.createPurchaseAcceptedMessage(serviceId);
		reply_PurchaseAccepted(message);
	}
	
	protected void reply_PurchaseAccepted(PurchaseAcceptedMessage message) {
		PurchaseAcceptedReply client = getClient(PurchaseAcceptedReply.ID);
		client.purchaseAccepted(message);
		String correlationId = message.getCorrelationId();
		fire_PurchaseAccepted_response_sent(correlationId);
	}
	
	protected void reply_PurchaseRejected(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		String serviceId = bookshop2.seller.incoming.purchaseBooks.PurchaseBooks.ID;
		PurchaseRejectedMessage message = sellerContext.createPurchaseRejectedMessage(serviceId);
		reply_PurchaseRejected(message);
	}

	protected void reply_PurchaseRejected(String correlationId, Throwable cause) {
		SellerContext sellerContext = getContext(correlationId);
		String serviceId = bookshop2.seller.incoming.purchaseBooks.PurchaseBooks.ID;
		PurchaseRejectedMessage message = sellerContext.createPurchaseRejectedMessage(serviceId);
		message.setReason(cause.getMessage());
		reply_PurchaseRejected(message);
	}
	
	protected void post_PurchaseComplete() {
		PurchaseCompleteEvent event = new PurchaseCompleteEvent();
		post_PurchaseComplete(event);
	}
	
	protected void post_PurchaseComplete(PurchaseCompleteEvent event) {
		sellerEventMulticaster.fireEvent(event);
		String correlationId = event.getCorrelationId();
		fire_PurchaseComplete_event_posted(correlationId);
	}
	
	protected void cancel_Supplier_QueryBooks(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		ActionState actionState = sellerContext.getActionState(correlationId, "queryBooks");
		if (actionState != null) {
			send_Supplier_QueryBooks_request_cancel(correlationId);
		}
	}

	protected void cancel_Supplier_ReserveBooks(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		ActionState actionState = sellerContext.getActionState(correlationId, "reserveBooks");
		if (actionState != null) {
			send_Supplier_ReserveBooks_request_cancel(correlationId);
		}
	}
	
	protected void cancel_Supplier_ShipBooks(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		ActionState actionState = sellerContext.getActionState(correlationId, "shipBooks");
		if (actionState != null) {
			send_Supplier_ShipBooks_request_cancel(correlationId);
		}
	}
	
	protected void undo_BookCache_addToAvailableBooks(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		ActionState actionState = sellerContext.getActionState(correlationId, "addToAvailableBooks");
		if (actionState != null) {
			Set<Book> bookSet = actionState.getElement("bookSet");
			bookCacheManager.removeFromAvailableBooks(bookSet);
		}
	}
	
	protected void undo_BookCache_addToBookOrders(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		ActionState actionState = sellerContext.getActionState(correlationId, "addToBookOrders");
		if (actionState != null) {
			Set<Book> bookSet = actionState.getElement("bookSet");
			bookCacheManager.removeFromBookOrders(bookSet);
		}
	}
	
	protected void undo_BookCache_removeFromAvailableBooks(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		ActionState actionState = sellerContext.getActionState(correlationId, "removeFromAvailableBooks");
		if (actionState != null) {
			Set<Book> bookSet = actionState.getElement("bookSet");
			bookCacheManager.addToAvailableBooks(bookSet);
		}
	}
	
	protected void undo_Supplier_QueryBooks(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		ActionState actionState = sellerContext.getActionState(correlationId, "queryBooks");
		if (actionState != null) {
			Set<Book> bookSet = actionState.getElement("bookSet");
			send_Supplier_QueryBooks_request_undo(correlationId, bookSet);
		}
	}

	protected void undo_Supplier_ReserveBooks(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		ActionState actionState = sellerContext.getActionState(correlationId, "reserveBooks");
		if (actionState != null) {
			Set<Book> bookSet = actionState.getElement("bookSet");
			send_Supplier_ReserveBooks_request_undo(correlationId, bookSet);
		}
	}
	
	protected void undo_Supplier_ShipBooks(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		ActionState actionState = sellerContext.getActionState(correlationId, "shipBooks");
		if (actionState != null) {
			Shipment shipment = actionState.getElement("shipment");
			Payment payment = actionState.getElement("payment");
			send_Supplier_ShipBooks_request_undo(correlationId, shipment, payment);
		}
	}
	
	protected void logState_Supplier_QueryBooks(QueryRequestMessage message) {
		String correlationId = message.getCorrelationId();
		SellerContext sellerContext = getContext(correlationId);
		ActionState action = createActionState("queryBooks", message);
		action.addElement("bookSet", message.getBooks());
		sellerContext.logAction(action);
	}

	protected void logState_Supplier_ReserveBooks(ReservationRequestMessage message) {
		String correlationId = message.getCorrelationId();
		SellerContext sellerContext = getContext(correlationId);
		ActionState action = createActionState("reserveBooks", message);
		action.addElement("bookSet", message.getBooks());
		sellerContext.logAction(action);
	}

	protected void logState_Supplier_ShipBooks(ShipmentRequestMessage message) {
		String correlationId = message.getCorrelationId();
		SellerContext sellerContext = getContext(correlationId);
		ActionState action = createActionState("shipBooks", message);
		action.addElement("shipment", message.getShipment());
		action.addElement("payment", message.getPayment());
		sellerContext.logAction(action);
	}
	
	protected void fire_OrderBooks_request_received(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_OrderBooks_request_received();
	}
	
	protected void fire_OrderBooks_request_handled(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_OrderBooks_request_handled();
	}
	
	protected void fire_OrderBooks_request_done(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_OrderBooks_request_done();
	}
	
	protected void fire_OrderBooks_request_cancel_done(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_OrderBooks_request_cancel_done();
	}
	
	protected void fire_OrderBooks_request_undo_done(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_OrderBooks_request_undo_done();
	}
	
	protected void fire_OrderBooks_incoming_request_aborted(String correlationId, Throwable cause) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_OrderBooks_incoming_request_aborted(cause);
	}
	
	protected void fire_OrderBooks_incoming_request_aborted(String correlationId, String reason) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_OrderBooks_incoming_request_aborted(reason);
	}
	
	protected void fire_OrderBooks_error_sent(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_OrderBooks_error_sent();
	}
	
	protected void fire_OrderAccepted_response_sent(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_OrderAccepted_response_sent();
	}
	
	protected void fire_OrderAccepted_outgoing_response_aborted(String correlationId, Throwable cause) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_OrderAccepted_outgoing_response_aborted(cause);
	}
	
	protected void fire_OrderAccepted_outgoing_response_aborted(String correlationId, String reason) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_OrderAccepted_outgoing_response_aborted(reason);
	}
	
	protected void fire_OrderRejected_response_sent(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_OrderRejected_response_sent();
	}
	
	protected void fire_OrderRejected_outgoing_response_aborted(String correlationId, Throwable cause) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_OrderRejected_outgoing_response_aborted(cause);
	}
	
	protected void fire_OrderRejected_outgoing_response_aborted(String correlationId, String reason) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_OrderRejected_outgoing_response_aborted(reason);
	}
	
	protected void fire_QueryBooks_request_sent(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_QueryBooks_request_sent();
	}
	
	protected void fire_QueryBooks_outgoing_request_aborted(String correlationId, Throwable cause) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_QueryBooks_outgoing_request_aborted(cause);
	}
	
	protected void fire_QueryBooks_outgoing_request_aborted(String correlationId, String reason) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_QueryBooks_outgoing_request_aborted(reason);
	}
	
	protected void fire_QueryBooks_error_received(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_QueryBooks_error_received();
	}
	
	protected void fire_BooksAvailable_response_received(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_BooksAvailable_response_received();
	}
	
	protected void fire_BooksAvailable_response_handled(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_BooksAvailable_response_handled();
	}
	
	protected void fire_BooksAvailable_response_done(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_BooksAvailable_response_done();
	}
	
	protected void fire_BooksAvailable_incoming_response_aborted(String correlationId, Throwable cause) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_BooksAvailable_incoming_response_aborted(cause);
	}
	
	protected void fire_BooksAvailable_incoming_response_aborted(String correlationId, String reason) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_BooksAvailable_incoming_response_aborted(reason);
	}
	
	protected void fire_BooksUnavailable_response_received(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_BooksUnavailable_response_received();
	}
	
	protected void fire_BooksUnavailable_response_handled(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_BooksUnavailable_response_handled();
	}
	
	protected void fire_BooksUnavailable_response_done(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_BooksUnavailable_response_done();
	}
	
	protected void fire_BooksUnavailable_incoming_response_aborted(String correlationId, Throwable cause) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_BooksUnavailable_incoming_response_aborted(cause);
	}
	
	protected void fire_BooksUnavailable_incoming_response_aborted(String correlationId, String reason) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_BooksUnavailable_incoming_response_aborted(reason);
	}
	
	protected void fire_ReserveBooks_request_sent(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_ReserveBooks_request_sent();
	}
	
	protected void fire_ReserveBooks_outgoing_request_aborted(String correlationId, Throwable cause) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_ReserveBooks_outgoing_request_aborted(cause);
	}
	
	protected void fire_ReserveBooks_outgoing_request_aborted(String correlationId, String reason) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_ReserveBooks_outgoing_request_aborted(reason);
	}
	
	protected void fire_ReserveBooks_error_received(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_ReserveBooks_error_received();
	}
	
	protected void fire_PurchaseBooks_request_received(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_PurchaseBooks_request_received();
	}
	
	protected void fire_PurchaseBooks_request_handled(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_PurchaseBooks_request_handled();
	}
	
	protected void fire_PurchaseBooks_request_done(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_PurchaseBooks_request_done();
	}
	
	protected void fire_PurchaseBooks_request_cancel_done(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_PurchaseBooks_request_cancel_done();
	}
	
	protected void fire_PurchaseBooks_request_undo_done(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_PurchaseBooks_request_undo_done();
	}
	
	protected void fire_PurchaseBooks_incoming_request_aborted(String correlationId, Throwable cause) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_PurchaseBooks_incoming_request_aborted(cause);
	}
	
	protected void fire_PurchaseBooks_incoming_request_aborted(String correlationId, String reason) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_PurchaseBooks_incoming_request_aborted(reason);
	}
	
	protected void fire_PurchaseBooks_error_sent(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_PurchaseBooks_error_sent();
	}
	
	protected void fire_PurchaseAccepted_response_sent(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_PurchaseAccepted_response_sent();
	}
	
	protected void fire_PurchaseAccepted_outgoing_response_aborted(String correlationId, Throwable cause) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_PurchaseAccepted_outgoing_response_aborted(cause);
	}
	
	protected void fire_PurchaseAccepted_outgoing_response_aborted(String correlationId, String reason) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_PurchaseAccepted_outgoing_response_aborted(reason);
	}
	
	protected void fire_PurchaseRejected_response_sent(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_PurchaseRejected_response_sent();
	}
	
	protected void fire_PurchaseRejected_outgoing_response_aborted(String correlationId, Throwable cause) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_PurchaseRejected_outgoing_response_aborted(cause);
	}
	
	protected void fire_PurchaseRejected_outgoing_response_aborted(String correlationId, String reason) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_PurchaseRejected_outgoing_response_aborted(reason);
	}
	
	protected void fire_ShipBooks_request_sent(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_ShipBooks_request_sent();
	}
	
	protected void fire_ShipBooks_outgoing_request_aborted(String correlationId, Throwable cause) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_ShipBooks_outgoing_request_aborted(cause);
	}
	
	protected void fire_ShipBooks_outgoing_request_aborted(String correlationId, String reason) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_ShipBooks_outgoing_request_aborted(reason);
	}
	
	protected void fire_ShipBooks_error_received(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_ShipBooks_error_received();
	}
	
	protected void fire_ShipmentScheduled_response_received(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_ShipmentScheduled_response_received();
	}
	
	protected void fire_ShipmentScheduled_response_handled(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_ShipmentScheduled_response_handled();
	}
	
	protected void fire_ShipmentScheduled_response_done(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_ShipmentScheduled_response_done();
	}
	
	protected void fire_ShipmentScheduled_incoming_response_aborted(String correlationId, Throwable cause) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_ShipmentScheduled_incoming_response_aborted(cause);
	}
	
	protected void fire_ShipmentScheduled_incoming_response_aborted(String correlationId, String reason) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_ShipmentScheduled_incoming_response_aborted(reason);
	}
	
	protected void fire_ShipmentComplete_response_received(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_ShipmentComplete_response_received();
	}
	
	protected void fire_ShipmentComplete_response_handled(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_ShipmentComplete_response_handled();
	}
	
	protected void fire_ShipmentComplete_response_done(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_ShipmentComplete_response_done();
	}
	
	protected void fire_ShipmentComplete_incoming_response_aborted(String correlationId, Throwable cause) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_ShipmentComplete_incoming_response_aborted(cause);
	}
	
	protected void fire_ShipmentComplete_incoming_response_aborted(String correlationId, String reason) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_ShipmentComplete_incoming_response_aborted(reason);
	}
	
	protected void fire_ShipmentFailed_response_received(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_ShipmentFailed_response_received();
	}
	
	protected void fire_ShipmentFailed_response_handled(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_ShipmentFailed_response_handled();
	}
	
	protected void fire_ShipmentFailed_response_done(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_ShipmentFailed_response_done();
	}
	
	protected void fire_ShipmentFailed_incoming_response_aborted(String correlationId, Throwable cause) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_ShipmentFailed_incoming_response_aborted(cause);
	}
	
	protected void fire_ShipmentFailed_incoming_response_aborted(String correlationId, String reason) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_ShipmentFailed_incoming_response_aborted(reason);
	}

	protected void fire_PurchaseComplete_event_posted(String correlationId) {
		SellerContext sellerContext = getContext(correlationId);
		sellerContext.fire_PurchaseComplete_event_posted();
	}
	
}
