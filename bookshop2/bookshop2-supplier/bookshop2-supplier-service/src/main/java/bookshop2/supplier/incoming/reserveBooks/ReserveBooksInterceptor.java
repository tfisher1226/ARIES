package bookshop2.supplier.incoming.reserveBooks;

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

import bookshop2.ReservationRequestMessage;


@Stateful
@LocalBean
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
public class ReserveBooksInterceptor extends MessageInterceptor<ReserveBooks> {
	
	private static final Log log = LogFactory.getLog(ReserveBooksInterceptor.class);
	
	@Inject
	protected ReserveBooksHandler reserveBooksHandler;
	
	
	@TransactionAttribute(REQUIRED)
	public Message reserveBooks(Message message) {
		try {
			ReservationRequestMessage reservationRequestMessage = message.getPart("reservationRequestMessage");
			reserveBooksHandler.reserveBooks(reservationRequestMessage);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
}
