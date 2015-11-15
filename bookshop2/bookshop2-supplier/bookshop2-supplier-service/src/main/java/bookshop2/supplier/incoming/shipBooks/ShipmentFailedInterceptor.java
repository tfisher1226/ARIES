package bookshop2.supplier.incoming.shipBooks;

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

import bookshop2.ShipmentFailedMessage;


@Stateful
@LocalBean
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
public class ShipmentFailedInterceptor extends MessageInterceptor<ShipmentFailed> {
	
	private static final Log log = LogFactory.getLog(ShipmentFailedInterceptor.class);
	
	@Inject
	protected ShipmentFailedHandler shipmentFailedHandler;
	
	
	@TransactionAttribute(REQUIRED)
	public Message shipmentFailed(Message message) {
		try {
			ShipmentFailedMessage shipmentFailedMessage = message.getPart("shipmentFailedMessage");
			shipmentFailedHandler.shipmentFailed(shipmentFailedMessage);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
}
