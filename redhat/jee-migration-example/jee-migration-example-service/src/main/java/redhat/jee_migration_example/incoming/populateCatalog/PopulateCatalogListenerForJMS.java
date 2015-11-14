package redhat.jee_migration_example.incoming.populateCatalog;

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
import javax.jms.MessageListener;
import javax.transaction.TransactionSynchronizationRegistry;

import org.aries.jms.util.MessageUtil;
import org.aries.tx.service.jms.AbstractJMSListener;
import org.aries.tx.service.jms.JMSListenerInterceptor;

import redhat.jee_migration_example.EventLoggerContext;


@MessageDriven(name = "PublicPopulateCatalogListener", activationConfig = {
		@ActivationConfigProperty(propertyName = "clientId", propertyValue = "public.redhat.populateCatalog"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/queue/public_redhat_populate_catalog_queue"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
		@ActivationConfigProperty(propertyName = "reconnectAttempts", propertyValue = "-1"),
		@ActivationConfigProperty(propertyName = "maxSession", propertyValue = "2")
})
@TransactionManagement(CONTAINER)
@Interceptors(JMSListenerInterceptor.class)
public class PopulateCatalogListenerForJMS extends AbstractJMSListener implements MessageListener {
	
	@Inject
	private EventLoggerContext eventLoggerContext;
	
	@Resource
	private MessageDrivenContext messageDrivenContext;
	
	@Inject
	private PopulateCatalogHandler populateCatalogHandler;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Override
	public String getModuleId() {
		return "jee-migration-example-service";
	}
	
	@Override
	public String getServiceId() {
		return PopulateCatalog.ID;
	}
	
	@Override
	public Object getDelegate() {
		return populateCatalogHandler;
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public void onMessage(javax.jms.Message jmsMessage) {
		if (!isInitialized())
			return;
		
		try {
			
			//validate the message
			eventLoggerContext.initializeContext(jmsMessage);
			eventLoggerContext.validate();
			
			//handle the message
			populateCatalogHandler.populateCatalog();
			
		} catch (Throwable e) {
			log.error(e);
			//TODO send this exception to "invalid" queue
			eventLoggerContext.fire_PopulateCatalog_incoming_request_aborted(e);
		}
	}
	
}
