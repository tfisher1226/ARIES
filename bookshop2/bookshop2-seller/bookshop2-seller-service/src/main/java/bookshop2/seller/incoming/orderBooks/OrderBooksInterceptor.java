package bookshop2.seller.incoming.orderBooks;

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

import bookshop2.OrderRequestMessage;


@Stateful
@LocalBean
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
public class OrderBooksInterceptor extends MessageInterceptor<OrderBooks> {
	
	private static final Log log = LogFactory.getLog(OrderBooksInterceptor.class);
	
	@Inject
	protected OrderBooksHandler orderBooksHandler;
	
	
	@TransactionAttribute(REQUIRED)
	public Message orderBooks(Message message) {
		try {
			OrderRequestMessage orderRequestMessage = message.getPart("orderRequestMessage");
			orderBooksHandler.orderBooks(orderRequestMessage);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
}
