package bookshop2.supplier.management.bookOrdersManager;

import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.jms.Destination;
import javax.jms.MessageListener;
import javax.transaction.TransactionSynchronizationRegistry;

import org.aries.jms.util.MessageUtil;
import org.aries.message.Message;
import org.aries.tx.service.jms.AbstractJMSListener;
import org.aries.tx.service.jms.JMSListenerInterceptor;


@MessageDriven(name = "InventoryBookOrdersManagerListener", activationConfig = {
		@ActivationConfigProperty(propertyName = "clientId", propertyValue = "inventory.bookshop2.supplier.bookOrdersManager"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/queue/inventory_bookshop2_supplier_book_orders_manager_queue"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
		@ActivationConfigProperty(propertyName = "reconnectAttempts", propertyValue = "-1"),
		@ActivationConfigProperty(propertyName = "maxSession", propertyValue = "2")
})
@TransactionManagement(CONTAINER)
@Interceptors(JMSListenerInterceptor.class)
public class BookOrdersManagerListenerForJMS extends AbstractJMSListener implements MessageListener {
	
	@Resource
	private MessageDrivenContext messageDrivenContext;
	
	@Inject
	private BookOrdersManagerInterceptor bookOrdersManagerInterceptor;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Override
	public String getModuleId() {
		return "bookshop2-supplier-service";
	}
	
	@Override
	public String getServiceId() {
		return BookOrdersManager.ID;
	}
	
	@Override
	public Object getDelegate() {
		return bookOrdersManagerInterceptor;
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public void onMessage(javax.jms.Message jmsMessage) {
		if (!isInitialized())
			return;
		
		try {
			String correlationId = MessageUtil.getCorrelationIdFromMessage(jmsMessage);
			Serializable payload = MessageUtil.getPayloadFromMessage(jmsMessage);
			Serializable output = invoke(payload, correlationId, 0L);
			
			//send response (if any)
			if (output != null) {
				Destination jmsReplyTo = jmsMessage.getJMSReplyTo();
				sendResponse(jmsReplyTo, output, correlationId, transactionId);
			}
			
		} catch (Throwable e) {
			log.error(e);
			//TODO send this exception to "invalid" queue
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Serializable invoke(Serializable request, String correlationId, long timeout) {
		if (!isInitialized())
			return null;
		if (request instanceof String)
			return processAsText((String) request);
		if (request instanceof Message)
			return processAsBinary((Message) request);
		throw new RuntimeException("Unexpected payload type: "+request.getClass());
	}
	
}
