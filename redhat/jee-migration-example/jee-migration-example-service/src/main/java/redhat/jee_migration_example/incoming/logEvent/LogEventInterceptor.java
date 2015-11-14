package redhat.jee_migration_example.incoming.logEvent;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.message.Message;
import org.aries.message.MessageInterceptor;

import redhat.jee_migration_example.Event;


@Stateless
@LocalBean
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
public class LogEventInterceptor extends MessageInterceptor<LogEvent> {
	
	private static final Log log = LogFactory.getLog(LogEventInterceptor.class);
	
	@Inject
	protected LogEventHandler logEventHandler;
	
	
	@TransactionAttribute(REQUIRED)
	public Message logEvent(Message message) {
		try {
			Event event = message.getPart("event");
			logEventHandler.logEvent(event);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
}
