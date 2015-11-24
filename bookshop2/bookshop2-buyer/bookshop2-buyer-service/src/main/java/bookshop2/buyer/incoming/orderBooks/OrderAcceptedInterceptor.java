package bookshop2.buyer.incoming.orderBooks;

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

import bookshop2.OrderAcceptedMessage;


@Stateful
@LocalBean
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
public class OrderAcceptedInterceptor extends MessageInterceptor<OrderAccepted> {
	
	private static final Log log = LogFactory.getLog(OrderAcceptedInterceptor.class);
	
	@Inject
	protected OrderAcceptedHandler orderAcceptedHandler;
	
	
	@TransactionAttribute(REQUIRED)
	public Message orderAccepted(Message message) {
		try {
			OrderAcceptedMessage orderAcceptedMessage = message.getPart("orderAcceptedMessage");
			orderAcceptedHandler.orderAccepted(orderAcceptedMessage);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
}