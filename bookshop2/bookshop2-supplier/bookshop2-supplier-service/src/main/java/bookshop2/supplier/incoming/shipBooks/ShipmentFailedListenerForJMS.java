package bookshop2.supplier.incoming.shipBooks;

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

import bookshop2.ShipmentFailedMessage;
import bookshop2.supplier.SupplierContext;


@MessageDriven(name = "InventoryShipmentFailedListener", activationConfig = {
		@ActivationConfigProperty(propertyName = "clientId", propertyValue = "inventory.bookshop2.supplier.shipmentFailed"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/queue/inventory_bookshop2_supplier_shipment_failed_queue"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
		@ActivationConfigProperty(propertyName = "reconnectAttempts", propertyValue = "-1"),
		@ActivationConfigProperty(propertyName = "maxSession", propertyValue = "2")
})
@TransactionManagement(CONTAINER)
@Interceptors(JMSListenerInterceptor.class)
public class ShipmentFailedListenerForJMS extends AbstractJMSListener implements MessageListener {
	
	@Resource
	private MessageDrivenContext messageDrivenContext;
	
	@Inject
	private ShipmentFailedHandler shipmentFailedHandler;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Override
	public String getModuleId() {
		return "bookshop2-supplier-service";
	}
	
	@Override
	public String getServiceId() {
		return ShipmentFailed.ID;
	}
	
	@Override
	public Object getDelegate() {
		return shipmentFailedHandler;
	}
	
	protected SupplierContext getContext(Message message) {
		return getContext(getCorrelationId(message));
	}

	protected SupplierContext getContext(String correlationId) {
		return SupplierContext.getInstance(correlationId);
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public void onMessage(javax.jms.Message jmsMessage) {
		SupplierContext supplierContext = getContext(jmsMessage);
		if (supplierContext == null)
			return;
		if (!isInitialized())
			return;
		
		try {
			ShipmentFailedMessage shipmentFailedMessage = MessageUtil.getPart(jmsMessage, "shipmentFailedMessage");
			
			//validate the message
			supplierContext.validate(shipmentFailedMessage);
			
			//handle the message
			shipmentFailedHandler.shipmentFailed(shipmentFailedMessage);
			
		} catch (Throwable e) {
			log.error(e);
			//TODO send this exception to "invalid" queue
			supplierContext.fire_ShipBooks_incoming_request_aborted(e);
		}
	}
	
}
