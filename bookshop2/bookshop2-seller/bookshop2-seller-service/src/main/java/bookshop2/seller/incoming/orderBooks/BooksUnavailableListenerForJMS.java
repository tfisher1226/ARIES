package bookshop2.seller.incoming.orderBooks;

import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.transaction.TransactionSynchronizationRegistry;

import org.aries.jms.util.MessageUtil;
import org.aries.tx.service.jms.AbstractJMSListener;
import org.aries.tx.service.jms.JMSListenerInterceptor;

import bookshop2.BooksUnavailableMessage;
import bookshop2.seller.SellerContext;


@MessageDriven(name = "ProtectedBooksUnavailableListener", activationConfig = {
		@ActivationConfigProperty(propertyName = "clientId", propertyValue = "protected.bookshop2.seller.booksUnavailable"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/queue/protected_bookshop2_seller_books_unavailable_queue"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
		@ActivationConfigProperty(propertyName = "reconnectAttempts", propertyValue = "-1"),
		@ActivationConfigProperty(propertyName = "maxSession", propertyValue = "2")
})
@TransactionManagement(CONTAINER)
@Interceptors(JMSListenerInterceptor.class)
public class BooksUnavailableListenerForJMS extends AbstractJMSListener implements MessageListener {
	
	@Resource
	private MessageDrivenContext messageDrivenContext;
	
	@Inject
	private BooksUnavailableHandler booksUnavailableHandler;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Override
	public String getModuleId() {
		return "bookshop2-seller-service";
	}
	
	@Override
	public String getServiceId() {
		return BooksUnavailable.ID;
	}
	
	@Override
	public Object getDelegate() {
		return booksUnavailableHandler;
	}
	
	protected SellerContext getContext(Message message) {
		return getContext(getCorrelationId(message));
	}

	protected SellerContext getContext(String correlationId) {
		return SellerContext.getInstance(correlationId);
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public void onMessage(javax.jms.Message jmsMessage) {
		SellerContext sellerContext = getContext(jmsMessage);
		if (sellerContext == null)
			return;
		if (!isInitialized())
			return;
				
		try {
			BooksUnavailableMessage booksUnavailableMessage = MessageUtil.getPart(jmsMessage, "booksUnavailableMessage");
			
			//validate the message
			sellerContext.validate(booksUnavailableMessage);
			
			//handle the message
			booksUnavailableHandler.booksUnavailable(booksUnavailableMessage);
			
		} catch (Throwable e) {
			log.error(e);
			//TODO send this exception to "invalid" queue
			sellerContext.fire_OrderBooks_incoming_request_aborted(e);
		}
	}
	
}
