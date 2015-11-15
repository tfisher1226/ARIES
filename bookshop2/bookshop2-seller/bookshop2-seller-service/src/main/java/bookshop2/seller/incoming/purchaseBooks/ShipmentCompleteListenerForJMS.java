package bookshop2.seller.incoming.purchaseBooks;

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

import bookshop2.ShipmentCompleteMessage;
import bookshop2.seller.SellerContext;


@MessageDriven(name = "ProtectedShipmentCompleteListener", activationConfig = {
		@ActivationConfigProperty(propertyName = "clientId", propertyValue = "protected.bookshop2.seller.shipmentComplete"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/queue/protected_bookshop2_seller_shipment_complete_queue"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
		@ActivationConfigProperty(propertyName = "reconnectAttempts", propertyValue = "-1"),
		@ActivationConfigProperty(propertyName = "maxSession", propertyValue = "2")
})
@TransactionManagement(CONTAINER)
@Interceptors(JMSListenerInterceptor.class)
public class ShipmentCompleteListenerForJMS extends AbstractJMSListener implements MessageListener {
	
	@Resource
	private MessageDrivenContext messageDrivenContext;
	
	@Inject
	private ShipmentCompleteHandler shipmentCompleteHandler;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Override
	public String getModuleId() {
		return "bookshop2-seller-service";
	}
	
	@Override
	public String getServiceId() {
		return ShipmentComplete.ID;
	}
	
	@Override
	public Object getDelegate() {
		return shipmentCompleteHandler;
	}
	
	protected SellerContext getContext(Message message) {
		return getContext(getCorrelationId(message));
	}

	protected SellerContext getContext(String correlationId) {
		return SellerContext.getInstance(correlationId);
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public void onMessage(javax.jms.Message jmsMessage) {
		SellerContext sellerContext = getContext(jmsMessage);
		if (sellerContext == null)
			return;
		if (!isInitialized())
			return;
		
		try {
			ShipmentCompleteMessage shipmentCompleteMessage = MessageUtil.getPart(jmsMessage, "shipmentCompleteMessage");
			
			//validate the message
			sellerContext.validate(shipmentCompleteMessage);
			
			//handle the message
			shipmentCompleteHandler.shipmentComplete(shipmentCompleteMessage);
			
		} catch (Throwable e) {
			log.error(e);
			//TODO send this exception to "invalid" queue
			sellerContext.fire_PurchaseBooks_incoming_request_aborted(e);
		}
	}
	
}
