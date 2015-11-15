package bookshop2.buyer.incoming.purchaseBooks;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.message.Message;
import org.aries.message.MessageInterceptor;

import bookshop2.PurchaseRequestMessage;


@Stateful
@LocalBean
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
public class PurchaseBooksInterceptor extends MessageInterceptor<PurchaseBooks> {
	
	private static final Log log = LogFactory.getLog(PurchaseBooksInterceptor.class);
	
	@Inject
	protected PurchaseBooksHandler purchaseBooksHandler;
	
	
	@TransactionAttribute(REQUIRED)
	public Message purchaseBooks(Message message) {
		try {
			PurchaseRequestMessage purchaseRequestMessage = message.getPart("purchaseRequestMessage");
			purchaseBooksHandler.purchaseBooks(purchaseRequestMessage);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
}
