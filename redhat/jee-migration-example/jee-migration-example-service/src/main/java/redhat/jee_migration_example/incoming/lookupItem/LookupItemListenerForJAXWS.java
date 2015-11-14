package redhat.jee_migration_example.incoming.lookupItem;

import static javax.ejb.TransactionAttributeType.REQUIRED;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.transaction.TransactionSynchronizationRegistry;
import javax.xml.ws.WebServiceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.tx.module.Bootstrapper;
import org.aries.util.ExceptionUtil;

import redhat.jee_migration_example.EventLoggerContext;


@Remote(LookupItem.class)
@Stateless(name = "LookupItem")
@WebService(name = "lookupItem", serviceName = "lookupItemService", portName = "lookupItem", targetNamespace = "http://www.redhat.com/jee-migration-example")
@HandlerChain(file = "/jaxws-handlers-service-oneway.xml")
public class LookupItemListenerForJAXWS implements LookupItem {
	
	private static final Log log = LogFactory.getLog(LookupItemListenerForJAXWS.class);
	
	@Inject
	private EventLoggerContext eventLoggerContext;
	
	@Inject
	private LookupItemHandler lookupItemHandler;
	
	@Resource
	private WebServiceContext webServiceContext;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Override
	@WebMethod
	@TransactionAttribute(REQUIRED)
	public void lookupItem(Long id) {
		if (!Bootstrapper.isInitialized("jee-migration-example-service"))
			return;
		
		try {
			eventLoggerContext.validate(id);
			lookupItemHandler.lookupItem(id);
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
