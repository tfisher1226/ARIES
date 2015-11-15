package bookshop2.seller.incoming.purchaseBooks;

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

import bookshop2.ShipmentScheduledMessage;


@Stateful
@LocalBean
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
public class ShipmentScheduledInterceptor extends MessageInterceptor<ShipmentScheduled> {
	
	private static final Log log = LogFactory.getLog(ShipmentScheduledInterceptor.class);
	
	@Inject
	protected ShipmentScheduledHandler shipmentScheduledHandler;
	
	
	@TransactionAttribute(REQUIRED)
	public Message shipmentScheduled(Message message) {
		try {
			ShipmentScheduledMessage shipmentScheduledMessage = message.getPart("shipmentScheduledMessage");
			shipmentScheduledHandler.shipmentScheduled(shipmentScheduledMessage);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
}
