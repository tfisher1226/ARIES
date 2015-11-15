package bookshop2.seller;

import java.util.Collection;
import java.util.Set;

import org.aries.common.AbstractMessage;
import org.aries.common.PersonName;
import org.aries.common.StreetAddress;
import org.aries.tx.ConversationContext;

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
import bookshop2.PurchaseRejectedMessage;
import bookshop2.PurchaseRequestMessage;
import bookshop2.QueryRequestMessage;
import bookshop2.ReservationRequestMessage;
import bookshop2.Shipment;
import bookshop2.ShipmentCompleteMessage;
import bookshop2.ShipmentFailedMessage;
import bookshop2.ShipmentRequestMessage;
import bookshop2.ShipmentScheduledMessage;


//@Singleton
//@LocalBean
//@AccessTimeout(value = 60000)
//@ConcurrencyManagement(BEAN)
//@TransactionAttribute(REQUIRED)
//@TransactionManagement(CONTAINER)
public class SellerContext extends ConversationContext {

	public static SellerContext getInstance(String correlationId) {
		return getInstance(SellerContext.class, correlationId);
	}	

	
	private Order order;

	private PersonName name;

	private StreetAddress address;

	private Payment payment;

	private String reason;

	private Set<Book> books;
	
	private Invoice invoice;
	
	private Shipment shipment;
	
	private bookshop2.ObjectFactory bookshop2ObjectFactory;

	
	public SellerContext() {
		bookshop2ObjectFactory = new bookshop2.ObjectFactory();
	}
	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	public PersonName getName() {
		return name;
	}

	public void setName(PersonName name) {
		this.name = name;
	}

	public StreetAddress getAddress() {
		return address;
	}

	public void setAddress(StreetAddress address) {
		this.address = address;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	
	public String getReason() {
		return reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public Set<Book> getBooks() {
		return books;
	}
	
	public void setBooks(Set<Book> books) {
		this.books = books;
	}
	
	public void addToBooks(Book book) {
		if (book != null ) {
			synchronized (this.books) {
				this.books.add(book);
			}
		}
	}

	public void addToBooks(Collection<Book> bookCollection) {
		if (bookCollection != null && !bookCollection.isEmpty()) {
			synchronized (this.books) {
				this.books.addAll(bookCollection);
			}
		}
	}

	public void removeFromBooks(Book book) {
		if (book != null ) {
			synchronized (this.books) {
				this.books.remove(book);
			}
		}
	}

	public void removeFromBooks(Collection<Book> bookCollection) {
		if (bookCollection != null ) {
			synchronized (this.books) {
				this.books.removeAll(bookCollection);
			}
		}
	}

	public void clearBooks() {
		synchronized (books) {
			books.clear();
		}
	}
	
	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	
	public Shipment getShipment() {
		return shipment;
	}
	
	public void setShipment(Shipment shipment) {
		this.shipment = shipment;
	}
	
	@Override
	public String getDomainId() {
		return "bookshop2.seller";
	}
	
//	@Override
//	public String serviceId {
//		return "bookshop2.seller.SellerProcess";
//	}
	
	
//	@PostConstruct
//	protected void initialize() {
//		registerWithJMX();
//		bookshop2ObjectFactory = new bookshop2.ObjectFactory();
//	}
//	
//	@PreDestroy
//	protected void close() {
//		unregisterWithJMX();
//	}
	
	public String getDestination_Seller_BooksAvailable() {
		return "protected_bookshop2_seller_books_available_queue";
	}
	
	public String getDestination_Seller_BooksUnavailable() {
		return "protected_bookshop2_seller_books_unavailable_queue";
	}
	
	public String getDestination_Seller_ShipmentFailed() {
		return "protected_bookshop2_seller_shipment_failed_queue";
	}
	
	public String getDestination_Seller_ShipmentComplete() {
		return "protected_bookshop2_seller_shipment_complete_queue";
	}
	
	public String getDestination_Seller_ShipmentScheduled() {
		return "protected_bookshop2_seller_shipment_scheduled_queue";
	}
	
	public void addReplyTo_Seller_BooksAvailable(AbstractMessage message) {
		message.addToReplyToDestinations("BooksAvailable", getDestination_Seller_BooksAvailable());
	}
	
	public void addReplyTo_Seller_BooksUnavailable(AbstractMessage message) {
		message.addToReplyToDestinations("BooksUnavailable", getDestination_Seller_BooksUnavailable());
	}
	
	public void addReplyTo_Seller_ShipmentFailed(AbstractMessage message) {
		message.addToReplyToDestinations("ShipmentFailed", getDestination_Seller_ShipmentFailed());
	}
	
	public void addReplyTo_Seller_ShipmentComplete(AbstractMessage message) {
		message.addToReplyToDestinations("ShipmentComplete", getDestination_Seller_ShipmentComplete());
	}
	
	public void addReplyTo_Seller_ShipmentScheduled(AbstractMessage message) {
		message.addToReplyToDestinations("ShipmentScheduled", getDestination_Seller_ShipmentScheduled());
	}
	
	public OrderRequestMessage createOrderRequestMessage(String serviceId) {
		OrderRequestMessage message = bookshop2ObjectFactory.createOrderRequestMessage();
		initializeMessage(serviceId, message);
		message.setOrder(order);
		message.setName(name);
		message.setAddress(address);
		message.setPayment(payment);
		return message;
	}
	
	public QueryRequestMessage createQueryRequestMessage(String serviceId) {
		QueryRequestMessage message = bookshop2ObjectFactory.createQueryRequestMessage();
		initializeMessage(serviceId, message);
		message.setBooks(books);
		return message;
	}
	
	public OrderAcceptedMessage createOrderAcceptedMessage(String serviceId) {
		OrderAcceptedMessage message = bookshop2ObjectFactory.createOrderAcceptedMessage();
		initializeMessage(serviceId, message);
		message.setOrder(order);
		return message;
	}
	
	public OrderRejectedMessage createOrderRejectedMessage(String serviceId) {
		OrderRejectedMessage message = bookshop2ObjectFactory.createOrderRejectedMessage();
		initializeMessage(serviceId, message);
		message.setOrder(order);
		message.setReason(reason);
		return message;
	}
	
	public BooksAvailableMessage createBooksAvailableMessage(String serviceId) {
		BooksAvailableMessage message = bookshop2ObjectFactory.createBooksAvailableMessage();
		initializeMessage(serviceId, message);
		message.setBooks(books);
		return message;
	}
	
	public ReservationRequestMessage createReservationRequestMessage(String serviceId) {
		ReservationRequestMessage message = bookshop2ObjectFactory.createReservationRequestMessage();
		initializeMessage(serviceId, message);
		message.setBooks(books);
		return message;
	}

	public BooksUnavailableMessage createBooksUnavailableMessage(String serviceId) {
		BooksUnavailableMessage message = bookshop2ObjectFactory.createBooksUnavailableMessage();
		initializeMessage(serviceId, message);
		message.setBooks(books);
		return message;
	}
	
	public PurchaseRequestMessage createPurchaseRequestMessage(String serviceId) {
		PurchaseRequestMessage message = bookshop2ObjectFactory.createPurchaseRequestMessage();
		initializeMessage(serviceId, message);
		message.setName(name);
		message.setAddress(address);
		message.setOrder(order);
		message.setPayment(payment);
		return message;
	}
	
	public ShipmentRequestMessage createShipmentRequestMessage(String serviceId) {
		ShipmentRequestMessage message = bookshop2ObjectFactory.createShipmentRequestMessage();
		initializeMessage(serviceId, message);
		message.setShipment(shipment);
		message.setPayment(payment);
		return message;
	}
	
	public PurchaseAcceptedMessage createPurchaseAcceptedMessage(String serviceId) {
		PurchaseAcceptedMessage message = bookshop2ObjectFactory.createPurchaseAcceptedMessage();
		initializeMessage(serviceId, message);
		message.setOrder(order);
		message.setPayment(payment);
		message.setInvoice(invoice);
		return message;
	}
	
	public PurchaseRejectedMessage createPurchaseRejectedMessage(String serviceId) {
		PurchaseRejectedMessage message = bookshop2ObjectFactory.createPurchaseRejectedMessage();
		initializeMessage(serviceId, message);
		message.setOrder(order);
		message.setPayment(payment);
		message.setReason(reason);
		return message;
	}
	
	public ShipmentScheduledMessage createShipmentScheduledMessage(String serviceId) {
		ShipmentScheduledMessage message = bookshop2ObjectFactory.createShipmentScheduledMessage();
		initializeMessage(serviceId, message);
		message.setShipment(shipment);
		return message;
	}
	
	public ShipmentCompleteMessage createShipmentCompleteMessage(String serviceId) {
		ShipmentCompleteMessage message = bookshop2ObjectFactory.createShipmentCompleteMessage();
		initializeMessage(serviceId, message);
		message.setShipment(shipment);
		message.setInvoice(invoice);
		return message;
	}
	
	public ShipmentFailedMessage createShipmentFailedMessage(String serviceId) {
		ShipmentFailedMessage message = bookshop2ObjectFactory.createShipmentFailedMessage();
		initializeMessage(serviceId, message);
		message.setShipment(shipment);
		message.setReason(reason);
		return message;
	}
	
	public void initializeContext(String serviceId, OrderRequestMessage orderRequestMessage) {
		super.initializeContext(serviceId, orderRequestMessage);
		setOrder(orderRequestMessage.getOrder());
		setName(orderRequestMessage.getName());
		setAddress(orderRequestMessage.getAddress());
		setPayment(orderRequestMessage.getPayment());
	}
	
	public void initializeContext(BooksAvailableMessage booksAvailableMessage) {
		super.initializeContext("BooksAvailable", booksAvailableMessage);
		setBooks(booksAvailableMessage.getBooks());
	}
	
	public void initializeContext(BooksUnavailableMessage booksUnavailableMessage) {
		super.initializeContext("BooksUnavailable", booksUnavailableMessage);
		setBooks(booksUnavailableMessage.getBooks());
	}
	
	public void initializeContext(String serviceId, PurchaseRequestMessage purchaseRequestMessage) {
		super.initializeContext(serviceId, purchaseRequestMessage);
		setName(purchaseRequestMessage.getName());
		setAddress(purchaseRequestMessage.getAddress());
		setOrder(purchaseRequestMessage.getOrder());
		setPayment(purchaseRequestMessage.getPayment());
	}
	
	public void initializeContext(ShipmentScheduledMessage shipmentScheduledMessage) {
		super.initializeContext("ShipmentScheduled", shipmentScheduledMessage);
		setShipment(shipmentScheduledMessage.getShipment());
	}
	
	public void initializeContext(ShipmentCompleteMessage shipmentCompleteMessage) {
		super.initializeContext("ShipmentComplete", shipmentCompleteMessage);
		setShipment(shipmentCompleteMessage.getShipment());
		setInvoice(shipmentCompleteMessage.getInvoice());
	}
	
	public void initializeContext(ShipmentFailedMessage shipmentFailedMessage) {
		super.initializeContext("ShipmentFailed", shipmentFailedMessage);
		setShipment(shipmentFailedMessage.getShipment());
		setReason(shipmentFailedMessage.getReason());
	}

	public void validate(OrderRequestMessage orderRequestMessage) {
		validate("orderRequestMessage", orderRequestMessage);
		validateMessage(orderRequestMessage);
	}
	
	public void validate(BooksAvailableMessage booksAvailableMessage) {
		validate("booksAvailableMessage", booksAvailableMessage);
		validateMessage(booksAvailableMessage);
	}

	public void validate(BooksUnavailableMessage booksUnavailableMessage) {
		validate("booksUnavailableMessage", booksUnavailableMessage);
		validateMessage(booksUnavailableMessage);
	}

	public void validate(PurchaseRequestMessage purchaseRequestMessage) {
		validate("purchaseRequestMessage", purchaseRequestMessage);
		validateMessage(purchaseRequestMessage);
	}

	public void validate(ShipmentScheduledMessage shipmentScheduledMessage) {
		validate("shipmentScheduledMessage", shipmentScheduledMessage);
		validateMessage(shipmentScheduledMessage);
	}

	public void validate(ShipmentCompleteMessage shipmentCompleteMessage) {
		validate("shipmentCompleteMessage", shipmentCompleteMessage);
		validateMessage(shipmentCompleteMessage);
	}

	public void validate(ShipmentFailedMessage shipmentFailedMessage) {
		validate("shipmentFailedMessage", shipmentFailedMessage);
		validateMessage(shipmentFailedMessage);
	}

	public void fire_OrderBooks_request_received() {
		fireServiceCompleted("Seller_OrderBooks_Request_Received");
	}
	
	public void fire_OrderBooks_request_handled() {
		fireServiceCompleted("Seller_OrderBooks_Request_Handled");
	}
	
	public void fire_OrderBooks_request_done() {
		fireServiceCompleted("Seller_OrderBooks_Request_Done");
	}
	
	public void fire_OrderBooks_request_cancel_done() {
		fireServiceCompleted("Seller_OrderBooks_Request_Cancel_Done");
	}
	
	public void fire_OrderBooks_request_undo_done() {
		fireServiceCompleted("Seller_OrderBooks_Request_Undo_Done");
	}
	
	public void fire_OrderBooks_incoming_request_aborted(Throwable cause) {
		fireServiceAborted("Seller_OrderBooks_Incoming_Request_Aborted", cause);
	}
	
	public void fire_OrderBooks_incoming_request_aborted(String reason) {
		fireServiceAborted("Seller_OrderBooks_Incoming_Request_Aborted", reason);
	}
	
	public void fire_OrderBooks_error_sent() {
		fireServiceCompleted("Seller_OrderBooks_Error_Sent");
	}

	public void fire_OrderAccepted_response_sent() {
		fireServiceCompleted("Seller_OrderAccepted_Response_Sent");
	}
	
	public void fire_OrderAccepted_outgoing_response_aborted(Throwable cause) {
		fireServiceAborted("Seller_OrderAccepted_Outgoing_Response_Aborted", cause);
	}
	
	public void fire_OrderAccepted_outgoing_response_aborted(String reason) {
		fireServiceAborted("Seller_OrderAccepted_Outgoing_Response_Aborted", reason);
	}
	
	public void fire_OrderRejected_response_sent() {
		fireServiceCompleted("Seller_OrderRejected_Response_Sent");
	}
	
	public void fire_OrderRejected_outgoing_response_aborted(Throwable cause) {
		fireServiceAborted("Seller_OrderRejected_Outgoing_Response_Aborted", cause);
	}
	
	public void fire_OrderRejected_outgoing_response_aborted(String reason) {
		fireServiceAborted("Seller_OrderRejected_Outgoing_Response_Aborted", reason);
	}
	
	public void fire_QueryBooks_request_sent() {
		fireServiceCompleted("Supplier_QueryBooks_Request_Sent");
	}
	
	public void fire_QueryBooks_outgoing_request_aborted(Throwable cause) {
		fireServiceAborted("Supplier_QueryBooks_Outgoing_Request_Aborted", cause);
	}
	
	public void fire_QueryBooks_outgoing_request_aborted(String reason) {
		fireServiceAborted("Supplier_QueryBooks_Outgoing_Request_Aborted", reason);
	}
	
	public void fire_QueryBooks_error_received() {
		fireServiceCompleted("Supplier_QueryBooks_Error_Received");
	}
	
	public void fire_BooksUnavailable_response_received() {
		fireServiceCompleted("Supplier_BooksUnavailable_Response_Received");
	}
	
	public void fire_BooksUnavailable_response_handled() {
		fireServiceCompleted("Supplier_BooksUnavailable_Response_Handled");
	}
	
	public void fire_BooksUnavailable_response_done() {
		fireServiceCompleted("Supplier_BooksUnavailable_Response_Done");
	}
	
	public void fire_BooksUnavailable_incoming_response_aborted(Throwable cause) {
		fireServiceAborted("Supplier_BooksUnavailable_Incoming_Response_Aborted", cause);
	}
	
	public void fire_BooksUnavailable_incoming_response_aborted(String reason) {
		fireServiceAborted("Supplier_BooksUnavailable_Incoming_Response_Aborted", reason);
	}
	
	public void fire_BooksAvailable_response_received() {
		fireServiceCompleted("Supplier_BooksAvailable_Response_Received");
	}
	
	public void fire_BooksAvailable_response_handled() {
		fireServiceCompleted("Supplier_BooksAvailable_Response_Handled");
	}
	
	public void fire_BooksAvailable_response_done() {
		fireServiceCompleted("Supplier_BooksAvailable_Response_Done");
	}
	
	public void fire_BooksAvailable_incoming_response_aborted(Throwable cause) {
		fireServiceAborted("Supplier_BooksAvailable_Incoming_Response_Aborted", cause);
	}
	
	public void fire_BooksAvailable_incoming_response_aborted(String reason) {
		fireServiceAborted("Supplier_BooksAvailable_Incoming_Response_Aborted", reason);
	}
	
	public void fire_ReserveBooks_request_sent() {
		fireServiceCompleted("Supplier_ReserveBooks_Request_Sent");
	}
	
	public void fire_ReserveBooks_outgoing_request_aborted(Throwable cause) {
		fireServiceAborted("Supplier_ReserveBooks_Outgoing_Request_Aborted", cause);
	}
	
	public void fire_ReserveBooks_outgoing_request_aborted(String reason) {
		fireServiceAborted("Supplier_ReserveBooks_Outgoing_Request_Aborted", reason);
	}
	
	public void fire_ReserveBooks_error_received() {
		fireServiceCompleted("Supplier_ReserveBooks_Error_Received");
	}
	
	public void fire_PurchaseBooks_request_received() {
		fireServiceCompleted("Seller_PurchaseBooks_Request_Received");
	}
	
	public void fire_PurchaseBooks_request_handled() {
		fireServiceCompleted("Seller_PurchaseBooks_Request_Handled");
	}
	
	public void fire_PurchaseBooks_request_done() {
		fireServiceCompleted("Seller_PurchaseBooks_Request_Done");
	}
	
	public void fire_PurchaseBooks_request_cancel_done() {
		fireServiceCompleted("Seller_PurchaseBooks_Request_Cancel_Done");
	}
	
	public void fire_PurchaseBooks_request_undo_done() {
		fireServiceCompleted("Seller_PurchaseBooks_Request_Undo_Done");
	}
	
	public void fire_PurchaseBooks_incoming_request_aborted(Throwable cause) {
		fireServiceAborted("Seller_PurchaseBooks_Incoming_Request_Aborted", cause);
	}
	
	public void fire_PurchaseBooks_incoming_request_aborted(String reason) {
		fireServiceAborted("Seller_PurchaseBooks_Incoming_Request_Aborted", reason);
	}
	
	public void fire_PurchaseBooks_error_sent() {
		fireServiceCompleted("Seller_PurchaseBooks_Error_Sent");
	}

	public void fire_PurchaseAccepted_response_sent() {
		fireServiceCompleted("Seller_PurchaseAccepted_Response_Sent");
	}
	
	public void fire_PurchaseAccepted_outgoing_response_aborted(Throwable cause) {
		fireServiceAborted("Seller_PurchaseAccepted_Outgoing_Response_Aborted", cause);
	}
	
	public void fire_PurchaseAccepted_outgoing_response_aborted(String reason) {
		fireServiceAborted("Seller_PurchaseAccepted_Outgoing_Response_Aborted", reason);
	}
	
	public void fire_PurchaseRejected_response_sent() {
		fireServiceCompleted("Seller_PurchaseRejected_Response_Sent");
	}
	
	public void fire_PurchaseRejected_outgoing_response_aborted(Throwable cause) {
		fireServiceAborted("Seller_PurchaseRejected_Outgoing_Response_Aborted", cause);
	}
	
	public void fire_PurchaseRejected_outgoing_response_aborted(String reason) {
		fireServiceAborted("Seller_PurchaseRejected_Outgoing_Response_Aborted", reason);
	}
	
	public void fire_ShipBooks_request_sent() {
		fireServiceCompleted("Supplier_ShipBooks_Request_Sent");
	}
	
	public void fire_ShipBooks_outgoing_request_aborted(Throwable cause) {
		fireServiceAborted("Supplier_ShipBooks_Outgoing_Request_Aborted", cause);
	}
	
	public void fire_ShipBooks_outgoing_request_aborted(String reason) {
		fireServiceAborted("Supplier_ShipBooks_Outgoing_Request_Aborted", reason);
	}
	
	public void fire_ShipBooks_error_received() {
		fireServiceCompleted("Supplier_ShipBooks_Error_Received");
	}
	
	public void fire_ShipmentScheduled_response_received() {
		fireServiceCompleted("Supplier_ShipmentScheduled_Response_Received");
	}
	
	public void fire_ShipmentScheduled_response_handled() {
		fireServiceCompleted("Supplier_ShipmentScheduled_Response_Handled");
	}
	
	public void fire_ShipmentScheduled_response_done() {
		fireServiceCompleted("Supplier_ShipmentScheduled_Response_Done");
	}
	
	public void fire_ShipmentScheduled_incoming_response_aborted(Throwable cause) {
		fireServiceAborted("Supplier_ShipmentScheduled_Incoming_Response_Aborted", cause);
	}
	
	public void fire_ShipmentScheduled_incoming_response_aborted(String reason) {
		fireServiceAborted("Supplier_ShipmentScheduled_Incoming_Response_Aborted", reason);
	}
	
	public void fire_ShipmentComplete_response_received() {
		fireServiceCompleted("Supplier_ShipmentComplete_Response_Received");
	}
	
	public void fire_ShipmentComplete_response_handled() {
		fireServiceCompleted("Supplier_ShipmentComplete_Response_Handled");
	}
	
	public void fire_ShipmentComplete_response_done() {
		fireServiceCompleted("Supplier_ShipmentComplete_Response_Done");
	}
	
	public void fire_ShipmentComplete_incoming_response_aborted(Throwable cause) {
		fireServiceAborted("Supplier_ShipmentComplete_Incoming_Response_Aborted", cause);
	}
	
	public void fire_ShipmentComplete_incoming_response_aborted(String reason) {
		fireServiceAborted("Supplier_ShipmentComplete_Incoming_Response_Aborted", reason);
	}
	
	public void fire_ShipmentFailed_response_received() {
		fireServiceCompleted("Supplier_ShipmentFailed_Response_Received");
	}
	
	public void fire_ShipmentFailed_response_handled() {
		fireServiceCompleted("Supplier_ShipmentFailed_Response_Handled");
	}
	
	public void fire_ShipmentFailed_response_done() {
		fireServiceCompleted("Supplier_ShipmentFailed_Response_Done");
	}
	
	public void fire_ShipmentFailed_incoming_response_aborted(Throwable cause) {
		fireServiceAborted("Supplier_ShipmentFailed_Incoming_Response_Aborted", cause);
	}
	
	public void fire_ShipmentFailed_incoming_response_aborted(String reason) {
		fireServiceAborted("Supplier_ShipmentFailed_Incoming_Response_Aborted", reason);
	}

	public void fire_PurchaseComplete_event_posted() {
		fireServiceCompleted("Seller_PurchaseComplete_Event_Posted");
	}
	
}
