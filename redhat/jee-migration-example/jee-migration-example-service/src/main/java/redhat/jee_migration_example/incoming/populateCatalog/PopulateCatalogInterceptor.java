package redhat.jee_migration_example.incoming.populateCatalog;

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
public class PopulateCatalogInterceptor extends MessageInterceptor<PopulateCatalog> {
	
	private static final Log log = LogFactory.getLog(PopulateCatalogInterceptor.class);
	
	@Inject
	protected PopulateCatalogHandler populateCatalogHandler;
	
	
	@TransactionAttribute(REQUIRED)
	public Message populateCatalog(Message message) {
		try {
			populateCatalogHandler.populateCatalog();
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
}
