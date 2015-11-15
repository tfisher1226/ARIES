package bookshop2.supplier.incoming.queryBooks;

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

import bookshop2.QueryRequestMessage;


@Stateful
@LocalBean
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
public class QueryBooksInterceptor extends MessageInterceptor<QueryBooks> {
	
	private static final Log log = LogFactory.getLog(QueryBooksInterceptor.class);
	
	@Inject
	protected QueryBooksHandler queryBooksHandler;
	
	
	@TransactionAttribute(REQUIRED)
	public Message queryBooks(Message message) {
		try {
			QueryRequestMessage queryRequestMessage = message.getPart("queryRequestMessage");
			queryBooksHandler.queryBooks(queryRequestMessage);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
}
