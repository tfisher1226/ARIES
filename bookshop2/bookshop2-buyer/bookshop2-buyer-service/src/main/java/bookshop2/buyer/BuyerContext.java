package bookshop2.buyer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.aries.common.AbstractMessage;
import org.aries.common.PersonName;
import org.aries.common.StreetAddress;
import org.aries.tx.ConversationContext;

import bookshop2.Invoice;
import bookshop2.Order;
import bookshop2.OrderAcceptedMessage;
import bookshop2.OrderRejectedMessage;
import bookshop2.OrderRequestMessage;
import bookshop2.Payment;
import bookshop2.PurchaseAcceptedMessage;
import bookshop2.PurchaseRejectedMessage;
import bookshop2.PurchaseRequestMessage;


//@Singleton
//@LocalBean
//@AccessTimeout(value = 60000)
//@ConcurrencyManagement(BEAN)
//@TransactionAttribute(REQUIRED)
//@TransactionManagement(CONTAINER)
public class BuyerContext extends ConversationContext {

	public static BuyerContext getInstance(String correlationId) {
		return getInstance(BuyerContext.class, correlationId);
	}	

	public static Object getReplyToDestination(AbstractMessage message, String replyToId) {
		return  getReplyToDestination(BuyerContext.class, message, replyToId);
	}

	
	private Order order;
	
	private PersonName name;

	private StreetAddress address;

	private Payment payment;

	private String reason;
	
	private Invoice invoice;

	private bookshop2.ObjectFactory bookshop2ObjectFactory;
	
	
	public BuyerContext() {
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

	public void setName(PersonName personName) {
		this.name = personName;
	}

	public StreetAddress getAddress() {
		return address;
	}

	public void setAddress(StreetAddress streetAddress) {
		this.address = streetAddress;
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

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	
	@Override
	public String getDomainId() {
		return "bookshop2.buyer";
	}
	
//	@Override
//	public String getConversationId() {
//		return "bookshop2.buyer.BuyerProcess";
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
	
	public String getDestination_Buyer_OrderAccepted() {
		return "public_bookshop2_buyer_order_accepted_queue";
	}

	public String getDestination_Buyer_OrderRejected() {
		return "public_bookshop2_buyer_order_rejected_queue";
	}
	
	public String getDestination_Buyer_PurchaseAccepted() {
		return "public_bookshop2_buyer_purchase_accepted_queue";
	}

	public String getDestination_Buyer_PurchaseRejected() {
		return "public_bookshop2_buyer_purchase_rejected_queue";
	}
	
	public void addReplyTo_Buyer_OrderAccepted(AbstractMessage message) {
		message.addToReplyToDestinations("OrderAccepted", getDestination_Buyer_OrderAccepted());
	}

	public void addReplyTo_Buyer_OrderRejected(AbstractMessage message) {
		message.addToReplyToDestinations("OrderRejected", getDestination_Buyer_OrderRejected());
	}

	public void addReplyTo_Buyer_PurchaseAccepted(AbstractMessage message) {
		message.addToReplyToDestinations("PurchaseAccepted", getDestination_Buyer_PurchaseAccepted());
	}

	public void addReplyTo_Buyer_PurchaseRejected(AbstractMessage message) {
		message.addToReplyToDestinations("PurchaseRejected", getDestination_Buyer_PurchaseRejected());
	}
	
	public OrderRequestMessage createOrderRequestMessage(String serviceId) {
		OrderRequestMessage message = bookshop2ObjectFactory.createOrderRequestMessage();
		initializeMessage(serviceId, message);
		//message.setOrder(order);
		//message.setName(name);
		//message.setAddress(address);
		//message.setPayment(payment);
		return message;
	}
	
	public OrderAcceptedMessage createOrderAcceptedMessage(String serviceId) {
		OrderAcceptedMessage message = bookshop2ObjectFactory.createOrderAcceptedMessage();
		initializeMessage(serviceId, message);
		//message.setOrder(order);
		return message;
	}
	
	public OrderRejectedMessage createOrderRejectedMessage(String serviceId) {
		OrderRejectedMessage message = bookshop2ObjectFactory.createOrderRejectedMessage();
		initializeMessage(serviceId, message);
		//message.setOrder(order);
		//message.setReason(reason);
		return message;
	}
	
	public PurchaseRequestMessage createPurchaseRequestMessage(String serviceId) {
		PurchaseRequestMessage message = bookshop2ObjectFactory.createPurchaseRequestMessage();
		initializeMessage(serviceId, message);
		//message.setName(name);
		//message.setAddress(address);
		//message.setOrder(order);
		//message.setPayment(payment);
		return message;
	}
	
	public PurchaseAcceptedMessage createPurchaseAcceptedMessage(String serviceId) {
		PurchaseAcceptedMessage message = bookshop2ObjectFactory.createPurchaseAcceptedMessage();
		initializeMessage(serviceId, message);
		//message.setOrder(order);
		//message.setPayment(payment);
		//message.setInvoice(invoice);
		return message;
	}
	
	public PurchaseRejectedMessage createPurchaseRejectedMessage(String serviceId) {
		PurchaseRejectedMessage message = bookshop2ObjectFactory.createPurchaseRejectedMessage();
		initializeMessage(serviceId, message);
		//message.setOrder(order);
		//message.setPayment(payment);
		//message.setReason(reason);
		return message;
	}
	
	public void initializeContext(String serviceId, OrderRequestMessage orderRequestMessage) {
		super.initializeContext(serviceId, orderRequestMessage);
		setOrder(orderRequestMessage.getOrder());
		setName(orderRequestMessage.getName());
		setAddress(orderRequestMessage.getAddress());
		setPayment(orderRequestMessage.getPayment());
	}
	
	public void initializeContext(OrderAcceptedMessage orderAcceptedMessage) {
		super.initializeContext("OrderAccepted", orderAcceptedMessage);
		setOrder(orderAcceptedMessage.getOrder());
	}
	
	public void initializeContext(OrderRejectedMessage orderRejectedMessage) {
		super.initializeContext("OrderRejected", orderRejectedMessage);
		setOrder(orderRejectedMessage.getOrder());
		setReason(orderRejectedMessage.getReason());
	}
	
	public void initializeContext(String serviceId, PurchaseRequestMessage purchaseRequestMessage) {
		super.initializeContext(serviceId, purchaseRequestMessage);
		setName(purchaseRequestMessage.getName());
		setAddress(purchaseRequestMessage.getAddress());
		setOrder(purchaseRequestMessage.getOrder());
		setPayment(purchaseRequestMessage.getPayment());
	}
	
	public void initializeContext(PurchaseAcceptedMessage purchaseAcceptedMessage) {
		super.initializeContext("PurchaseAccepted", purchaseAcceptedMessage);
		setOrder(purchaseAcceptedMessage.getOrder());
		setPayment(purchaseAcceptedMessage.getPayment());
		setInvoice(purchaseAcceptedMessage.getInvoice());
	}
	
	public void initializeContext(PurchaseRejectedMessage purchaseRejectedMessage) {
		super.initializeContext("PurchaseRejected", purchaseRejectedMessage);
		setOrder(purchaseRejectedMessage.getOrder());
		setPayment(purchaseRejectedMessage.getPayment());
		setReason(purchaseRejectedMessage.getReason());
	}
	
	public void validate(OrderRequestMessage orderRequestMessage) {
		validate("orderRequestMessage", orderRequestMessage);
		validateMessage(orderRequestMessage);
	}
	
	public void validate(OrderAcceptedMessage orderAcceptedMessage) {
		validate("orderAcceptedMessage", orderAcceptedMessage);
		validateMessage(orderAcceptedMessage);
	}

	public void validate(OrderRejectedMessage orderRejectedMessage) {
		validate("orderRejectedMessage", orderRejectedMessage);
		validateMessage(orderRejectedMessage);
	}

	public void validate(PurchaseRequestMessage purchaseRequestMessage) {
		validate("purchaseRequestMessage", purchaseRequestMessage);
		validateMessage(purchaseRequestMessage);
	}

	public void validate(PurchaseAcceptedMessage purchaseAcceptedMessage) {
		validate("purchaseAcceptedMessage", purchaseAcceptedMessage);
		validateMessage(purchaseAcceptedMessage);
	}

	public void validate(PurchaseRejectedMessage purchaseRejectedMessage) {
		validate("purchaseRejectedMessage", purchaseRejectedMessage);
		validateMessage(purchaseRejectedMessage);
	}

	public void fire_OrderBooks_request_received() {
		fireServiceCompleted("OrderBooks_Request_Received");
	}
	
	public void fire_OrderBooks_request_handled() {
		fireServiceCompleted("OrderBooks_Request_Handled");
	}
	
	public void fire_OrderBooks_request_done() {
		fireServiceCompleted("OrderBooks_Request_Done");
	}
	
	public void fire_OrderBooks_request_cancel_done() {
		fireServiceCompleted("OrderBooks_Request_Cancel_Done");
	}
	
	public void fire_OrderBooks_request_undo_done() {
		fireServiceCompleted("OrderBooks_Request_Undo_Done");
	}
	
	public void fire_OrderBooks_incoming_request_aborted(Throwable cause) {
		fireServiceAborted("OrderBooks_Incoming_Request_Aborted", cause);
	}
	
	public void fire_OrderBooks_incoming_request_aborted(String reason) {
		fireServiceAborted("OrderBooks_Incoming_Request_Aborted", reason);
	}
	
	public void fire_OrderBooks_error_sent() {
		fireServiceCompleted("OrderBooks_Error_Sent");
	}
	
	public void fire_OrderAccepted_response_sent() {
		fireServiceCompleted("OrderAccepted_Response_Sent");
	}
	
	public void fire_OrderAccepted_outgoing_response_aborted(Throwable cause) {
		fireServiceAborted("OrderAccepted_Outgoing_Response_Aborted", cause);
	}
	
	public void fire_OrderAccepted_outgoing_response_aborted(String reason) {
		fireServiceAborted("OrderAccepted_Outgoing_Response_Aborted", reason);
	}
	
	public void fire_OrderRejected_response_sent() {
		fireServiceCompleted("OrderRejected_Response_Sent");
	}
	
	public void fire_OrderRejected_outgoing_response_aborted(Throwable cause) {
		fireServiceAborted("OrderRejected_Outgoing_Response_Aborted", cause);
	}
	
	public void fire_OrderRejected_outgoing_response_aborted(String reason) {
		fireServiceAborted("OrderRejected_Outgoing_Response_Aborted", reason);
	}
	
	public void fire_Seller_OrderBooks_request_sent() {
		fireServiceCompleted("Seller_OrderBooks_Request_Sent");
	}
	
	public void fire_Seller_OrderBooks_outgoing_request_aborted(Throwable cause) {
		fireServiceAborted("Seller_OrderBooks_Outgoing_Request_Aborted", cause);
	}
	
	public void fire_Seller_OrderBooks_outgoing_request_aborted(String reason) {
		fireServiceAborted("Seller_OrderBooks_Outgoing_Request_Aborted", reason);
	}
	
	public void fire_Seller_OrderBooks_error_received() {
		fireServiceCompleted("Seller_OrderBooks_Error_Received");
	}
	
	public void fire_OrderAccepted_response_received() {
		fireServiceCompleted("OrderAccepted_Response_Received");
	}
	
	public void fire_OrderAccepted_response_handled() {
		fireServiceCompleted("OrderAccepted_Response_Handled");
	}
	
	public void fire_OrderAccepted_response_done() {
		fireServiceCompleted("OrderAccepted_Response_Done");
	}
	
	public void fire_OrderAccepted_incoming_response_aborted(Throwable cause) {
		fireServiceAborted("OrderAccepted_Incoming_Response_Aborted", cause);
	}
	
	public void fire_OrderAccepted_incoming_response_aborted(String reason) {
		fireServiceAborted("OrderAccepted_Incoming_Response_Aborted", reason);
	}
	
	public void fire_OrderRejected_response_received() {
		fireServiceCompleted("OrderRejected_Response_Received");
	}
	
	public void fire_OrderRejected_response_handled() {
		fireServiceCompleted("OrderRejected_Response_Handled");
	}
	
	public void fire_OrderRejected_response_done() {
		fireServiceCompleted("OrderRejected_Response_Done");
	}
	
	public void fire_OrderRejected_incoming_response_aborted(Throwable cause) {
		fireServiceAborted("OrderRejected_Incoming_Response_Aborted", cause);
	}
	
	public void fire_OrderRejected_incoming_response_aborted(String reason) {
		fireServiceAborted("OrderRejected_Incoming_Response_Aborted", reason);
	}
	
	public void fire_Seller_PurchaseBooks_request_sent() {
		fireServiceCompleted("Seller_PurchaseBooks_Request_Sent");
	}
	
	public void fire_Seller_PurchaseBooks_outgoing_request_aborted(Throwable cause) {
		fireServiceAborted("Seller_PurchaseBooks_Outgoing_Request_Aborted", cause);
	}
	
	public void fire_Seller_PurchaseBooks_outgoing_request_aborted(String reason) {
		fireServiceAborted("Seller_PurchaseBooks_Outgoing_Request_Aborted", reason);
	}
	
	public void fire_Seller_PurchaseBooks_error_received() {
		fireServiceCompleted("Seller_PurchaseBooks_Error_Received");
	}
	
	public void fire_PurchaseAccepted_response_received() {
		fireServiceCompleted("PurchaseAccepted_Response_Received");
	}
	
	public void fire_PurchaseAccepted_response_handled() {
		fireServiceCompleted("PurchaseAccepted_Response_Handled");
	}
	
	public void fire_PurchaseAccepted_response_done() {
		fireServiceCompleted("PurchaseAccepted_Response_Done");
	}
	
	public void fire_PurchaseAccepted_incoming_response_aborted(Throwable cause) {
		fireServiceAborted("PurchaseAccepted_Incoming_Response_Aborted", cause);
	}
	
	public void fire_PurchaseAccepted_incoming_response_aborted(String reason) {
		fireServiceAborted("PurchaseAccepted_Incoming_Response_Aborted", reason);
	}
	
	public void fire_PurchaseRejected_response_received() {
		fireServiceCompleted("PurchaseRejected_Response_Received");
	}
	
	public void fire_PurchaseRejected_response_handled() {
		fireServiceCompleted("PurchaseRejected_Response_Handled");
	}
	
	public void fire_PurchaseRejected_response_done() {
		fireServiceCompleted("PurchaseRejected_Response_Done");
	}
	
	public void fire_PurchaseRejected_incoming_response_aborted(Throwable cause) {
		fireServiceAborted("PurchaseRejected_Incoming_Response_Aborted", cause);
	}
	
	public void fire_PurchaseRejected_incoming_response_aborted(String reason) {
		fireServiceAborted("PurchaseRejected_Incoming_Response_Aborted", reason);
	}
	
	public void fire_PurchaseBooks_request_received() {
		fireServiceCompleted("PurchaseBooks_Request_Received");
	}
	
	public void fire_PurchaseBooks_request_handled() {
		fireServiceCompleted("PurchaseBooks_Request_Handled");
	}
	
	public void fire_PurchaseBooks_request_done() {
		fireServiceCompleted("PurchaseBooks_Request_Done");
	}
	
	public void fire_PurchaseBooks_request_cancel_done() {
		fireServiceCompleted("PurchaseBooks_Request_Cancel_Done");
	}
	
	public void fire_PurchaseBooks_request_undo_done() {
		fireServiceCompleted("PurchaseBooks_Request_Undo_Done");
	}
	
	public void fire_PurchaseBooks_incoming_request_aborted(Throwable cause) {
		fireServiceAborted("PurchaseBooks_Incoming_Request_Aborted", cause);
	}
	
	public void fire_PurchaseBooks_incoming_request_aborted(String reason) {
		fireServiceAborted("PurchaseBooks_Incoming_Request_Aborted", reason);
	}
	
	public void fire_PurchaseBooks_error_sent() {
		fireServiceCompleted("PurchaseBooks_Error_Sent");
	}
	
	public void fire_PurchaseAccepted_response_sent() {
		fireServiceCompleted("PurchaseAccepted_Response_Sent");
	}
	
	public void fire_PurchaseAccepted_outgoing_response_aborted(Throwable cause) {
		fireServiceAborted("PurchaseAccepted_Outgoing_Response_Aborted", cause);
	}
	
	public void fire_PurchaseAccepted_outgoing_response_aborted(String reason) {
		fireServiceAborted("PurchaseAccepted_Outgoing_Response_Aborted", reason);
	}
	
	public void fire_PurchaseRejected_response_sent() {
		fireServiceCompleted("PurchaseRejected_Response_Sent");
	}
	
	public void fire_PurchaseRejected_outgoing_response_aborted(Throwable cause) {
		fireServiceAborted("PurchaseRejected_Outgoing_Response_Aborted", cause);
	}
	
	public void fire_PurchaseRejected_outgoing_response_aborted(String reason) {
		fireServiceAborted("PurchaseRejected_Outgoing_Response_Aborted", reason);
	}

}
