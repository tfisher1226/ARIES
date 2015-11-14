package redhat.jee_migration_example.incoming.logEvent;

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

import redhat.jee_migration_example.Event;
import redhat.jee_migration_example.EventLoggerContext;


@MessageDriven(name = "PublicLogEventListener", activationConfig = {
		@ActivationConfigProperty(propertyName = "clientId", propertyValue = "public.redhat.logEvent"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/queue/public_redhat_log_event_queue"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
		@ActivationConfigProperty(propertyName = "reconnectAttempts", propertyValue = "-1"),
		@ActivationConfigProperty(propertyName = "maxSession", propertyValue = "2")
})
@TransactionManagement(CONTAINER)
@Interceptors(JMSListenerInterceptor.class)
public class LogEventListenerForJMS extends AbstractJMSListener implements MessageListener {
	
	@Inject
	private EventLoggerContext eventLoggerContext;
	
	@Resource
	private MessageDrivenContext messageDrivenContext;
	
	@Inject
	private LogEventHandler logEventHandler;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Override
	public String getModuleId() {
		return "jee-migration-example-service";
	}
	
	@Override
	public String getServiceId() {
		return LogEvent.ID;
	}
	
	@Override
	public Object getDelegate() {
		return logEventHandler;
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public void onMessage(javax.jms.Message jmsMessage) {
		if (!isInitialized())
			return;
		
		try {
			Event event = MessageUtil.getPart(jmsMessage, "event");
			
			//validate the message
			eventLoggerContext.initializeContext(jmsMessage);
			eventLoggerContext.validate(event);
			
			//handle the message
			logEventHandler.logEvent(event);
			
		} catch (Throwable e) {
			log.error(e);
			//TODO send this exception to "invalid" queue
			eventLoggerContext.fire_LogEvent_incoming_request_aborted(e);
		}
	}
	
}
