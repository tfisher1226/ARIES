package bookshop2.supplier;

import java.util.Collection;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.aries.common.AbstractMessage;
import org.aries.tx.ConversationContext;

import bookshop2.Book;
import bookshop2.BooksAvailableMessage;
import bookshop2.BooksUnavailableMessage;
import bookshop2.Invoice;
import bookshop2.Payment;
import bookshop2.QueryRequestMessage;
import bookshop2.ReservationRequestMessage;
import bookshop2.Shipment;
import bookshop2.ShipmentCompleteMessage;
import bookshop2.ShipmentFailedMessage;
import bookshop2.ShipmentRequestMessage;
import bookshop2.ShipmentScheduledMessage;
import bookshop2.supplier.incoming.shipBooks.ShipmentComplete;
import bookshop2.supplier.incoming.shipBooks.ShipmentFailed;
import bookshop2.supplier.incoming.shipBooks.ShipmentScheduled;


//@Singleton
//@LocalBean
//@AccessTimeout(value = 60000)
//@ConcurrencyManagement(BEAN)
//@TransactionAttribute(REQUIRED)
//@TransactionManagement(CONTAINER)
public class SupplierContext extends ConversationContext {

	public static SupplierContext getInstance(String correlationId) {
		return getInstance(SupplierContext.class, correlationId);
	}	

	
	private Set<Book> books;
	
	private Shipment shipment;
	
	private Payment payment;
	
	private Invoice invoice;
	
	private String reason;
	
	private bookshop2.ObjectFactory bookshop2ObjectFactory;
	
	
	public SupplierContext() {
		bookshop2ObjectFactory = new bookshop2.ObjectFactory();
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
	
	public Shipment getShipment() {
		return shipment;
	}
	
	public void setShipment(Shipment shipment) {
		this.shipment = shipment;
	}
	
	public Payment getPayment() {
		return payment;
	}
	
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	
	public Invoice getInvoice() {
		return invoice;
	}
	
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	
	public String getReason() {
		return reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	@Override
	public String getDomainId() {
		return "bookshop2.supplier";
	}
	
//	@Override
//	public String serviceId {
//		return "bookshop2.supplier.SupplierProcess";
//	}
	
	@PostConstruct
	protected void initialize() {
		registerWithJMX();
		bookshop2ObjectFactory = new bookshop2.ObjectFactory();
	}
	
	@PreDestroy
	protected void close() {
		unregisterWithJMX();
	}
	
	public String getDestination_Supplier_ShipmentFailed() {
		return "inventory_bookshop2_supplier_shipment_failed_queue";
	}
	
	public String getDestination_Supplier_ShipmentComplete() {
		return "inventory_bookshop2_supplier_shipment_complete_queue";
	}
	
	public String getDestination_Supplier_ShipmentScheduled() {
		return "inventory_bookshop2_supplier_shipment_scheduled_queue";
	}

	public void addReplyTo_Supplier_ShipmentFailed(AbstractMessage message) {
		message.addToReplyToDestinations("ShipmentFailed", getDestination_Supplier_ShipmentFailed());
	}
	
	public void addReplyTo_Supplier_ShipmentComplete(AbstractMessage message) {
		message.addToReplyToDestinations("ShipmentComplete", getDestination_Supplier_ShipmentComplete());
	}
	
	public void addReplyTo_Supplier_ShipmentScheduled(AbstractMessage message) {
		message.addToReplyToDestinations("ShipmentScheduled", getDestination_Supplier_ShipmentScheduled());
	}
	
	public QueryRequestMessage createQueryRequestMessage(String serviceId) {
		QueryRequestMessage message = bookshop2ObjectFactory.createQueryRequestMessage();
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
	
	public ShipmentRequestMessage createShipmentRequestMessage(String serviceId) {
		ShipmentRequestMessage message = bookshop2ObjectFactory.createShipmentRequestMessage();
		initializeMessage(serviceId, message);
		message.setShipment(shipment);
		message.setPayment(payment);
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
	
	public void initializeContext(String serviceId, QueryRequestMessage queryRequestMessage) {
		super.initializeContext(serviceId, queryRequestMessage);
		setBooks(queryRequestMessage.getBooks());
	}
	
	public void initializeContext(String serviceId, ReservationRequestMessage reservationRequestMessage) {
		super.initializeContext(serviceId, reservationRequestMessage);
		setBooks(reservationRequestMessage.getBooks());
	}
	
	public void initializeContext(String serviceId, ShipmentRequestMessage shipmentRequestMessage) {
		super.initializeContext(serviceId, shipmentRequestMessage);
		setShipment(shipmentRequestMessage.getShipment());
		setPayment(shipmentRequestMessage.getPayment());
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
	
	public void validate(QueryRequestMessage queryRequestMessage) {
		validate("queryRequestMessage", queryRequestMessage);
		validateMessage(queryRequestMessage);
	}

	public void validate(ReservationRequestMessage reservationRequestMessage) {
		validate("reservationRequestMessage", reservationRequestMessage);
		validateMessage(reservationRequestMessage);
	}
	
	public void validate(ShipmentRequestMessage shipmentRequestMessage) {
		validate("shipmentRequestMessage", shipmentRequestMessage);
		validateMessage(shipmentRequestMessage);
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

	public void fire_QueryBooks_request_received() {
		fireServiceCompleted("Supplier_QueryBooks_Request_Received");
	}
	
	public void fire_QueryBooks_request_handled() {
		fireServiceCompleted("Supplier_QueryBooks_Request_Handled");
	}
	
	public void fire_QueryBooks_request_done() {
		fireServiceCompleted("Supplier_QueryBooks_Request_Done");
	}
	
	public void fire_QueryBooks_request_cancel_done() {
		fireServiceCompleted("Supplier_QueryBooks_Request_Cancel_Done");
	}
	
	public void fire_QueryBooks_request_undo_done() {
		fireServiceCompleted("Supplier_QueryBooks_Request_Undo_Done");
	}
	
	public void fire_QueryBooks_incoming_request_aborted(Throwable cause) {
		fireServiceAborted("Supplier_QueryBooks_Incoming_Request_Aborted", cause);
	}
	
	public void fire_QueryBooks_incoming_request_aborted(String reason) {
		fireServiceAborted("Supplier_QueryBooks_Incoming_Request_Aborted", reason);
	}
	
	public void fire_QueryBooks_error_sent() {
		fireServiceCompleted("Supplier_QueryBooks_Error_Sent");
	}
	
	public void fire_BooksUnavailable_response_sent() {
		fireServiceCompleted("Supplier_BooksUnavailable_Response_Sent");
	}
	
	public void fire_BooksUnavailable_outgoing_response_aborted(Throwable cause) {
		fireServiceAborted("Supplier_BooksUnavailable_Outgoing_Response_Aborted", cause);
	}
	
	public void fire_BooksUnavailable_outgoing_response_aborted(String reason) {
		fireServiceAborted("Supplier_BooksUnavailable_Outgoing_Response_Aborted", reason);
	}
	
	public void fire_BooksAvailable_response_sent() {
		fireServiceCompleted("Supplier_BooksAvailable_Response_Sent");
	}
	
	public void fire_BooksAvailable_outgoing_response_aborted(Throwable cause) {
		fireServiceAborted("Supplier_BooksAvailable_Outgoing_Response_Aborted", cause);
	}
	
	public void fire_BooksAvailable_outgoing_response_aborted(String reason) {
		fireServiceAborted("Supplier_BooksAvailable_Outgoing_Response_Aborted", reason);
	}
	
	public void fire_ReserveBooks_request_received() {
		fireServiceCompleted("Supplier_ReserveBooks_Request_Received");
	}
	
	public void fire_ReserveBooks_request_handled() {
		fireServiceCompleted("Supplier_ReserveBooks_Request_Handled");
	}
	
	public void fire_ReserveBooks_request_done() {
		fireServiceCompleted("Supplier_ReserveBooks_Request_Done");
	}
	
	public void fire_ReserveBooks_request_cancel_done() {
		fireServiceCompleted("Supplier_ReserveBooks_Request_Cancel_Done");
	}
	
	public void fire_ReserveBooks_request_undo_done() {
		fireServiceCompleted("Supplier_ReserveBooks_Request_Undo_Done");
	}
	
	public void fire_ReserveBooks_incoming_request_aborted(Throwable cause) {
		fireServiceAborted("Supplier_ReserveBooks_Incoming_Request_Aborted", cause);
	}
	
	public void fire_ReserveBooks_incoming_request_aborted(String reason) {
		fireServiceAborted("Supplier_ReserveBooks_Incoming_Request_Aborted", reason);
	}
	
	public void fire_ReserveBooks_error_sent() {
		fireServiceCompleted("Supplier_ReserveBooks_Error_Sent");
	}
	
	public void fire_ShipBooks_request_received() {
		fireServiceCompleted("Supplier_ShipBooks_Request_Received");
	}
	
	public void fire_ShipBooks_request_handled() {
		fireServiceCompleted("Supplier_ShipBooks_Request_Handled");
	}
	
	public void fire_ShipBooks_request_done() {
		fireServiceCompleted("Supplier_ShipBooks_Request_Done");
	}
	
	public void fire_ShipBooks_request_cancel_done() {
		fireServiceCompleted("Supplier_ShipBooks_Request_Cancel_Done");
	}
	
	public void fire_ShipBooks_request_undo_done() {
		fireServiceCompleted("Supplier_ShipBooks_Request_Undo_Done");
	}
	
	public void fire_ShipBooks_incoming_request_aborted(Throwable cause) {
		fireServiceAborted("Supplier_ShipBooks_Incoming_Request_Aborted", cause);
	}
	
	public void fire_ShipBooks_incoming_request_aborted(String reason) {
		fireServiceAborted("Supplier_ShipBooks_Incoming_Request_Aborted", reason);
	}
	
	public void fire_ShipBooks_error_sent() {
		fireServiceCompleted("Supplier_ShipBooks_Error_Sent");
	}
	
	public void fire_ShipmentScheduled_response_sent() {
		fireServiceCompleted("Supplier_ShipmentScheduled_Response_Sent");
	}
	
	public void fire_ShipmentScheduled_outgoing_response_aborted(Throwable cause) {
		fireServiceAborted("Supplier_ShipmentScheduled_Outgoing_Response_Aborted", cause);
	}
	
	public void fire_ShipmentScheduled_outgoing_response_aborted(String reason) {
		fireServiceAborted("Supplier_ShipmentScheduled_Outgoing_Response_Aborted", reason);
	}
	
	public void fire_ShipmentComplete_response_sent() {
		fireServiceCompleted("Supplier_ShipmentComplete_Response_Sent");
	}
	
	public void fire_ShipmentComplete_outgoing_response_aborted(Throwable cause) {
		fireServiceAborted("Supplier_ShipmentComplete_Outgoing_Response_Aborted", cause);
	}
	
	public void fire_ShipmentComplete_outgoing_response_aborted(String reason) {
		fireServiceAborted("Supplier_ShipmentComplete_Outgoing_Response_Aborted", reason);
	}
	
	public void fire_ShipmentFailed_response_sent() {
		fireServiceCompleted("Supplier_ShipmentFailed_Response_Sent");
	}
	
	public void fire_ShipmentFailed_outgoing_response_aborted(Throwable cause) {
		fireServiceAborted("Supplier_ShipmentFailed_Outgoing_Response_Aborted", cause);
	}
	
	public void fire_ShipmentFailed_outgoing_response_aborted(String reason) {
		fireServiceAborted("Supplier_ShipmentFailed_Outgoing_Response_Aborted", reason);
	}
	
	public void fire_ShipBooks_request_sent() {
		fireServiceCompleted("Shipper_ShipBooks_Request_Sent");
	}
	
	public void fire_ShipBooks_outgoing_request_aborted(Throwable cause) {
		fireServiceAborted("Shipper_ShipBooks_Outgoing_Request_Aborted", cause);
	}
	
	public void fire_ShipBooks_outgoing_request_aborted(String reason) {
		fireServiceAborted("Shipper_ShipBooks_Outgoing_Request_Aborted", reason);
	}
	
	public void fire_ShipBooks_error_received() {
		fireServiceCompleted("Shipper_ShipBooks_Error_Received");
	}
	
	public void fire_ShipmentFailed_response_received() {
		fireServiceCompleted("Shipper_ShipmentFailed_Response_Received");
	}
	
	public void fire_ShipmentFailed_response_handled() {
		fireServiceCompleted("Shipper_ShipmentFailed_Response_Handled");
	}
	
	public void fire_ShipmentFailed_response_done() {
		fireServiceCompleted("Shipper_ShipmentFailed_Response_Done");
	}
	
	public void fire_ShipmentFailed_incoming_response_aborted(Throwable cause) {
		fireServiceAborted("Shipper_ShipmentFailed_Incoming_Response_Aborted", cause);
	}
	
	public void fire_ShipmentFailed_incoming_response_aborted(String reason) {
		fireServiceAborted("Shipper_ShipmentFailed_Incoming_Response_Aborted", reason);
	}
	
	public void fire_ShipmentScheduled_response_received() {
		fireServiceCompleted("Shipper_ShipmentScheduled_Response_Received");
	}
	
	public void fire_ShipmentScheduled_response_handled() {
		fireServiceCompleted("Shipper_ShipmentScheduled_Response_Handled");
	}
	
	public void fire_ShipmentScheduled_response_done() {
		fireServiceCompleted("Shipper_ShipmentScheduled_Response_Done");
	}
	
	public void fire_ShipmentScheduled_incoming_response_aborted(Throwable cause) {
		fireServiceAborted("Shipper_ShipmentScheduled_Incoming_Response_Aborted", cause);
	}
	
	public void fire_ShipmentScheduled_incoming_response_aborted(String reason) {
		fireServiceAborted("Shipper_ShipmentScheduled_Incoming_Response_Aborted", reason);
	}
	
	public void fire_ShipmentComplete_response_received() {
		fireServiceCompleted("Shipper_ShipmentComplete_Response_Received");
	}
	
	public void fire_ShipmentComplete_response_handled() {
		fireServiceCompleted("Shipper_ShipmentComplete_Response_Handled");
	}
	
	public void fire_ShipmentComplete_response_done() {
		fireServiceCompleted("Shipper_ShipmentComplete_Response_Done");
	}
	
	public void fire_ShipmentComplete_incoming_response_aborted(Throwable cause) {
		fireServiceAborted("Shipper_ShipmentComplete_Incoming_Response_Aborted", cause);
	}
	
	public void fire_ShipmentComplete_incoming_response_aborted(String reason) {
		fireServiceAborted("Shipper_ShipmentComplete_Incoming_Response_Aborted", reason);
	}
	
	public void fire_ShipmentComplete_event_posted() {
		fireServiceCompleted("Supplier_ShipmentComplete_Event_Posted");
	}
	
}
