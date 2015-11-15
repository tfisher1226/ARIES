package bookshop2.shipper;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.aries.tx.ConversationContext;

import bookshop2.Invoice;
import bookshop2.Payment;
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
public class ShipperContext extends ConversationContext {
	
	public static ShipperContext getInstance(String correlationId) {
		return getInstance(ShipperContext.class, correlationId);
	}	

	
	private Shipment shipment;
	
	private Payment payment;
	
	private String reason;
	
	private Invoice invoice;
	
	private bookshop2.ObjectFactory bookshop2ObjectFactory;
	
	
	public ShipperContext() {
		bookshop2ObjectFactory = new bookshop2.ObjectFactory();
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
		return "bookshop2.shipper";
	}
	
//	@Override
//	public String serviceId {
//		return "bookshop2.shipper.ShipperProcess";
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
	
	public ShipmentRequestMessage createShipmentRequestMessage(String serviceId) {
		ShipmentRequestMessage message = bookshop2ObjectFactory.createShipmentRequestMessage();
		initializeMessage(serviceId, message);
		message.setShipment(shipment);
		message.setPayment(payment);
		return message;
	}
	
	public ShipmentFailedMessage createShipmentFailedMessage(String serviceId) {
		ShipmentFailedMessage message = bookshop2ObjectFactory.createShipmentFailedMessage();
		initializeMessage(serviceId, message);
		message.setShipment(shipment);
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
	
	public void initializeContext(String serviceId, ShipmentRequestMessage shipmentRequestMessage) {
		super.initializeContext(serviceId, shipmentRequestMessage);
		setShipment(shipmentRequestMessage.getShipment());
		setPayment(shipmentRequestMessage.getPayment());
	}

	public void validate(ShipmentRequestMessage shipmentRequestMessage) {
		validate("shipmentRequestMessage", shipmentRequestMessage);
		validateMessage(shipmentRequestMessage);
	}

	public void fire_ShipBooks_request_received() {
		fireServiceCompleted("Shipper_ShipBooks_Request_Received");
	}
	
	public void fire_ShipBooks_request_handled() {
		fireServiceCompleted("Shipper_ShipBooks_Request_Handled");
	}
	
	public void fire_ShipBooks_request_done() {
		fireServiceCompleted("Shipper_ShipBooks_Request_Done");
	}
	
	public void fire_ShipBooks_request_cancel_done() {
		fireServiceCompleted("Shipper_ShipBooks_Request_Cancel_Done");
	}
	
	public void fire_ShipBooks_request_undo_done() {
		fireServiceCompleted("Shipper_ShipBooks_Request_Undo_Done");
	}
	
	public void fire_ShipBooks_incoming_request_aborted(Throwable cause) {
		fireServiceAborted("Shipper_ShipBooks_Incoming_Request_Aborted", cause);
	}
	
	public void fire_ShipBooks_incoming_request_aborted(String reason) {
		fireServiceAborted("Shipper_ShipBooks_Incoming_Request_Aborted", reason);
	}
	
	public void fire_ShipBooks_error_sent() {
		fireServiceCompleted("Shipper_ShipBooks_Error_Sent");
	}
	
	public void fire_ShipmentFailed_response_sent() {
		fireServiceCompleted("Shipper_ShipmentFailed_Response_Sent");
	}
	
	public void fire_ShipmentFailed_outgoing_response_aborted(Throwable cause) {
		fireServiceAborted("Shipper_ShipmentFailed_Outgoing_Response_Aborted", cause);
	}
	
	public void fire_ShipmentFailed_outgoing_response_aborted(String reason) {
		fireServiceAborted("Shipper_ShipmentFailed_Outgoing_Response_Aborted", reason);
	}
	
	public void fire_ShipmentScheduled_response_sent() {
		fireServiceCompleted("Shipper_ShipmentScheduled_Response_Sent");
	}
	
	public void fire_ShipmentScheduled_outgoing_response_aborted(Throwable cause) {
		fireServiceAborted("Shipper_ShipmentScheduled_Outgoing_Response_Aborted", cause);
	}
	
	public void fire_ShipmentScheduled_outgoing_response_aborted(String reason) {
		fireServiceAborted("Shipper_ShipmentScheduled_Outgoing_Response_Aborted", reason);
	}
	
	public void fire_ShipmentComplete_response_sent() {
		fireServiceCompleted("Shipper_ShipmentComplete_Response_Sent");
	}
	
	public void fire_ShipmentComplete_outgoing_response_aborted(Throwable cause) {
		fireServiceAborted("Shipper_ShipmentComplete_Outgoing_Response_Aborted", cause);
	}
	
	public void fire_ShipmentComplete_outgoing_response_aborted(String reason) {
		fireServiceAborted("Shipper_ShipmentComplete_Outgoing_Response_Aborted", reason);
	}
	
	public void fire_ShipmentConfirmed_event_received() {
		fireServiceCompleted("Shipper_ShipmentConfirmed_Event_Received");
	}
	
	public void fire_ShipmentConfirmed_process_complete() {
		fireServiceCompleted("Shipper_ShipmentConfirmed_Process_Complete");
	}
	
	public void fire_ShipmentConfirmed_process_aborted(Throwable cause) {
		fireServiceAborted("Shipper_ShipmentConfirmed_Process_Aborted", cause);
	}
	
	public void fire_ShipmentConfirmed_process_aborted(String reason) {
		fireServiceAborted("Shipper_ShipmentConfirmed_Process_Aborted", reason);
	}
	
	public void fire_ShipmentScheduled_event_received() {
		fireServiceCompleted("Shipper_ShipmentScheduled_Event_Received");
	}
	
	public void fire_ShipmentScheduled_process_complete() {
		fireServiceCompleted("Shipper_ShipmentScheduled_Process_Complete");
	}
	
	public void fire_ShipmentScheduled_process_aborted(Throwable cause) {
		fireServiceAborted("Shipper_ShipmentScheduled_Process_Aborted", cause);
	}
	
	public void fire_ShipmentScheduled_process_aborted(String reason) {
		fireServiceAborted("Shipper_ShipmentScheduled_Process_Aborted", reason);
	}

}
