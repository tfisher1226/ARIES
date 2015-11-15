package bookshop2.buyer.incoming.purchaseBooks;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.message.Message;
import org.aries.message.MessageInterceptor;

import bookshop2.PurchaseRejectedMessage;


@Stateful
@LocalBean
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
public class PurchaseRejectedInterceptor extends MessageInterceptor<PurchaseRejected> {
	
	private static final Log log = LogFactory.getLog(PurchaseRejectedInterceptor.class);
	
	@Inject
	protected PurchaseRejectedHandler purchaseRejectedHandler;
	
	
	@TransactionAttribute(REQUIRED)
	public Message purchaseRejected(Message message) {
		try {
			PurchaseRejectedMessage purchaseRejectedMessage = message.getPart("purchaseRejectedMessage");
			purchaseRejectedHandler.purchaseRejected(purchaseRejectedMessage);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
}
