package redhat.jee_migration_example.incoming.lookupItem;

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


@Stateless
@LocalBean
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
public class LookupItemInterceptor extends MessageInterceptor<LookupItem> {
	
	private static final Log log = LogFactory.getLog(LookupItemInterceptor.class);
	
	@Inject
	protected LookupItemHandler lookupItemHandler;
	
	
	@TransactionAttribute(REQUIRED)
	public Message lookupItem(Message message) {
		try {
			Long id = message.getPart("id");
			lookupItemHandler.lookupItem(id);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
}
