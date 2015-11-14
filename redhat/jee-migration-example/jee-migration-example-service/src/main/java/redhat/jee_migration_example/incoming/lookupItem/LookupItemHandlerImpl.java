package redhat.jee_migration_example.incoming.lookupItem;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import javax.annotation.Resource;
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

import redhat.jee_migration_example.EventLoggerContext;
import redhat.jee_migration_example.EventLoggerProcess;
import redhat.jee_migration_example.Item;


@Stateful
@Local(LookupItemHandler.class)
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
@Interceptors(ServiceHandlerInterceptor.class)
public class LookupItemHandlerImpl extends AbstractServiceHandler implements LookupItemHandler {
	
	private static final Log log = LogFactory.getLog(LookupItemHandlerImpl.class);
	
	@Inject
	private EventLoggerContext eventLoggerContext;
	
	@Inject
	private EventLoggerProcess eventLoggerProcess;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	public String getName() {
		return "LookupItem";
	}
	
	public String getDomain() {
		return "redhat";
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public Item lookupItem(Long id) {
		log.info("#### [eventLogger]: LookupItem request received");
		
		try {
			eventLoggerContext.validate(id);
			eventLoggerContext.fire_LookupItem_request_received();
			Item item = eventLoggerProcess.handle_LookupItem_request(id);
			return item;
		
		} catch (Throwable e) {
			log.error("Error", e);
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			eventLoggerProcess.handle_LookupItem_request_exception(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
