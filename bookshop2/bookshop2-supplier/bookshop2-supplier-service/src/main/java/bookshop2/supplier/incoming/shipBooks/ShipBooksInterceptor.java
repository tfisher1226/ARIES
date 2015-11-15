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

import bookshop2.ShipmentRequestMessage;


@Stateful
@LocalBean
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
public class ShipBooksInterceptor extends MessageInterceptor<ShipBooks> {
	
	private static final Log log = LogFactory.getLog(ShipBooksInterceptor.class);
	
	@Inject
	protected ShipBooksHandler shipBooksHandler;
	
	
	@TransactionAttribute(REQUIRED)
	public Message shipBooks(Message message) {
		try {
			ShipmentRequestMessage shipmentRequestMessage = message.getPart("shipmentRequestMessage");
			shipBooksHandler.shipBooks(shipmentRequestMessage);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
}
