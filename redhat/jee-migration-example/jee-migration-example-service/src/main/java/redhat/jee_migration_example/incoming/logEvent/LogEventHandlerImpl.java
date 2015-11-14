package redhat.jee_migration_example.incoming.logEvent;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import javax.annotation.Resource;
import javax.ejb.AccessTimeout;
import javax.ejb.Asynchronous;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.transaction.TransactionSynchronizationRegistry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.tx.service.AbstractServiceHandler;
import org.aries.tx.service.ServiceHandlerInterceptor;
import org.aries.util.ExceptionUtil;

import redhat.jee_migration_example.Event;
import redhat.jee_migration_example.EventLoggerContext;
import redhat.jee_migration_example.EventLoggerProcess;


@Stateful
@Local(LogEventHandler.class)
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
@Interceptors(ServiceHandlerInterceptor.class)
public class LogEventHandlerImpl extends AbstractServiceHandler implements LogEventHandler {
	
	private static final Log log = LogFactory.getLog(LogEventHandlerImpl.class);
	
	@Inject
	private EventLoggerContext eventLoggerContext;
	
	@Inject
	private EventLoggerProcess eventLoggerProcess;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	public String getName() {
		return "LogEvent";
	}
	
	public String getDomain() {
		return "redhat";
	}
	
	public boolean prepare(String transactionId) {
		return true;
	}
	
	public void commit(String transactionId) {
		//nothing for now
	}
	
	public void rollback(String transactionId) {
		//nothing for now
	}
	
	@Override
	@Asynchronous
	@AccessTimeout(value = 60000)
	@TransactionAttribute(REQUIRED)
	public void logEvent(Event event) {
		log.info("#### [eventLogger]: LogEvent request received");
		
		try {
			eventLoggerContext.validate(event);
			eventLoggerContext.fire_LogEvent_request_received();
			eventLoggerProcess.handle_LogEvent_request(event);
		
		} catch (Throwable e) {
			log.error("Error", e);
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			eventLoggerProcess.handle_LogEvent_request_exception(e);
		}
	}
	
}
