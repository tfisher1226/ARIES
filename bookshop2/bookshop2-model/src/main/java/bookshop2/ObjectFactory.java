package bookshop2;

import javax.xml.bind.annotation.XmlRegistry;


@XmlRegistry
public class ObjectFactory {
	
	public ObjectFactory() {
		//nothing for now
	}
	
	
	public Book createBook() {
		return new Book();
	}
	
	public BookCriteria createBookCriteria() {
		return new BookCriteria();
	}
	
	public BookKey createBookKey() {
		return new BookKey();
	}
	
	public BooksAvailableMessage createBooksAvailableMessage() {
		return new BooksAvailableMessage();
	}
	
	public BooksUnavailableMessage createBooksUnavailableMessage() {
		return new BooksUnavailableMessage();
	}
	
	public Invoice createInvoice() {
		return new Invoice();
	}
	
	public Order createOrder() {
		return new Order();
	}
	
	public OrderAcceptedMessage createOrderAcceptedMessage() {
		return new OrderAcceptedMessage();
	}
	
	public OrderCriteria createOrderCriteria() {
		return new OrderCriteria();
	}
	
	public OrderKey createOrderKey() {
		return new OrderKey();
	}
	
	public OrderRejectedMessage createOrderRejectedMessage() {
		return new OrderRejectedMessage();
	}
	
	public OrderRequestMessage createOrderRequestMessage() {
		return new OrderRequestMessage();
	}
	
	public OrderResponseMessage createOrderResponseMessage() {
		return new OrderResponseMessage();
	}
	
	public Payment createPayment() {
		return new Payment();
	}
	
	public PurchaseAcceptedMessage createPurchaseAcceptedMessage() {
		return new PurchaseAcceptedMessage();
	}
	
	public PurchaseCompleteEvent createPurchaseCompleteEvent() {
		return new PurchaseCompleteEvent();
	}
	
	public PurchaseRejectedMessage createPurchaseRejectedMessage() {
		return new PurchaseRejectedMessage();
	}
	
	public PurchaseRequestMessage createPurchaseRequestMessage() {
		return new PurchaseRequestMessage();
	}
	
	public QueryRequestMessage createQueryRequestMessage() {
		return new QueryRequestMessage();
	}
	
	public Receipt createReceipt() {
		return new Receipt();
	}
	
	public ReservationRequestMessage createReservationRequestMessage() {
		return new ReservationRequestMessage();
	}
	
	public Shipment createShipment() {
		return new Shipment();
	}
	
	public ShipmentCompleteEvent createShipmentCompleteEvent() {
		return new ShipmentCompleteEvent();
	}
	
	public ShipmentCompleteMessage createShipmentCompleteMessage() {
		return new ShipmentCompleteMessage();
	}
	
	public ShipmentConfirmedEvent createShipmentConfirmedEvent() {
		return new ShipmentConfirmedEvent();
	}
	
	public ShipmentFailedMessage createShipmentFailedMessage() {
		return new ShipmentFailedMessage();
	}
	
	public ShipmentRequestMessage createShipmentRequestMessage() {
		return new ShipmentRequestMessage();
	}
	
	public ShipmentScheduledEvent createShipmentScheduledEvent() {
		return new ShipmentScheduledEvent();
	}
	
	public ShipmentScheduledMessage createShipmentScheduledMessage() {
		return new ShipmentScheduledMessage();
	}
	
}
