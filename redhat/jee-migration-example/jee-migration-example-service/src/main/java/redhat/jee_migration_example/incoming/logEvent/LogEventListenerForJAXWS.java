package redhat.jee_migration_example.incoming.logEvent;

import static javax.ejb.TransactionAttributeType.REQUIRED;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.jws.HandlerChain;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.transaction.TransactionSynchronizationRegistry;
import javax.xml.ws.WebServiceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.tx.module.Bootstrapper;
import org.aries.util.ExceptionUtil;

import redhat.jee_migration_example.Event;
import redhat.jee_migration_example.EventLoggerContext;


@Remote(LogEvent.class)
@Stateless(name = "LogEvent")
@WebService(name = "logEvent", serviceName = "logEventService", portName = "logEvent", targetNamespace = "http://www.redhat.com/jee-migration-example")
@HandlerChain(file = "/jaxws-handlers-service-oneway.xml")
public class LogEventListenerForJAXWS implements LogEvent {
	
	private static final Log log = LogFactory.getLog(LogEventListenerForJAXWS.class);
	
	@Inject
	private EventLoggerContext eventLoggerContext;
	
	@Inject
	private LogEventHandler logEventHandler;
	
	@Resource
	private WebServiceContext webServiceContext;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Oneway
	@Override
	@WebMethod
	@TransactionAttribute(REQUIRED)
	public void logEvent(Event event) {
		if (!Bootstrapper.isInitialized("jee-migration-example-service"))
			return;
		
		try {
			eventLoggerContext.validate(event);
			logEventHandler.logEvent(event);
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
