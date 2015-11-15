package bookshop2.shipper.incoming.shipBooks;

import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.transaction.TransactionSynchronizationRegistry;

import org.aries.jms.util.MessageUtil;
import org.aries.tx.service.jms.AbstractJMSListener;
import org.aries.tx.service.jms.JMSListenerInterceptor;

import bookshop2.ShipmentRequestMessage;
import bookshop2.shipper.ShipperContext;


@MessageDriven(name = "ShipmentShipBooksListener", activationConfig = {
		@ActivationConfigProperty(propertyName = "clientId", propertyValue = "shipment.bookshop2.shipper.shipBooks"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/queue/shipment_bookshop2_shipper_ship_books_queue"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
		@ActivationConfigProperty(propertyName = "reconnectAttempts", propertyValue = "-1"),
		@ActivationConfigProperty(propertyName = "maxSession", propertyValue = "2")
})
@TransactionManagement(CONTAINER)
@Interceptors(JMSListenerInterceptor.class)
public class ShipBooksListenerForJMS extends AbstractJMSListener implements MessageListener {
	
	@Resource
	private MessageDrivenContext messageDrivenContext;
	
	@Inject
	private ShipBooksHandler shipBooksHandler;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Override
	public String getModuleId() {
		return "bookshop2-shipper-service";
	}
	
	@Override
	public String getServiceId() {
		return ShipBooks.ID;
	}
	
	@Override
	public Object getDelegate() {
		return shipBooksHandler;
	}
	
	protected ShipperContext getContext(Message message) {
		return getContext(getCorrelationId(message));
	}

	protected ShipperContext getContext(String correlationId) {
		return ShipperContext.getInstance(correlationId);
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public void onMessage(javax.jms.Message jmsMessage) {
		ShipperContext shipperContext = getContext(jmsMessage);
		if (shipperContext == null)
			return;
		if (!isInitialized())
			return;
		
		try {
			ShipmentRequestMessage shipmentRequestMessage = MessageUtil.getPart(jmsMessage, "shipmentRequestMessage");
			
			//validate the message
			shipperContext.validate(shipmentRequestMessage);
			
			//handle the message
			shipBooksHandler.shipBooks(shipmentRequestMessage);
			
		} catch (Throwable e) {
			log.error(e);
			//TODO send this exception to "invalid" queue
			shipperContext.fire_ShipBooks_incoming_request_aborted(e);
		}
	}
	
}
