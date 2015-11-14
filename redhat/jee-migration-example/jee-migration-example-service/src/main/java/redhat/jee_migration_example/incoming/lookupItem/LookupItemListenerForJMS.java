package redhat.jee_migration_example.incoming.lookupItem;

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

import redhat.jee_migration_example.EventLoggerContext;
import redhat.jee_migration_example.Item;


@MessageDriven(name = "publicLookupItemListener", activationConfig = {
		@ActivationConfigProperty(propertyName = "clientId", propertyValue = "public.redhat.lookupItem"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/queue/public_redhat_lookup_item_queue"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
		@ActivationConfigProperty(propertyName = "reconnectAttempts", propertyValue = "-1"),
		@ActivationConfigProperty(propertyName = "maxSession", propertyValue = "2")
})
@TransactionManagement(CONTAINER)
@Interceptors(JMSListenerInterceptor.class)
public class LookupItemListenerForJMS extends AbstractJMSListener implements MessageListener {
	
	@Inject
	private EventLoggerContext eventLoggerContext;
	
	@Resource
	private MessageDrivenContext messageDrivenContext;
	
	@Inject
	private LookupItemHandler lookupItemHandler;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Override
	public String getModuleId() {
		return "jee-migration-example-service";
	}
	
	@Override
	public String getServiceId() {
		return LookupItem.ID;
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public void onMessage(javax.jms.Message jmsMessage) {
		if (!isInitialized())
			return;
		
		try {
			Long id = MessageUtil.getPart(jmsMessage, "id");
			
			//validate the request
			eventLoggerContext.initializeContext(jmsMessage);
			eventLoggerContext.validate(id);
			
			//handle the request
			Item item = lookupItemHandler.lookupItem(id);
			
			//send response
			Destination jmsReplyTo = jmsMessage.getJMSReplyTo();
			sendResponse(jmsReplyTo, item);
			
		} catch (Throwable e) {
			log.error(e);
			//TODO send this exception to "invalid" queue
		}
	}
	
}
