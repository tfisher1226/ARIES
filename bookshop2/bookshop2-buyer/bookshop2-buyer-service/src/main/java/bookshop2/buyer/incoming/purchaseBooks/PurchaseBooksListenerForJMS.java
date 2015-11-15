package bookshop2.buyer.incoming.purchaseBooks;

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

import bookshop2.PurchaseRequestMessage;
import bookshop2.buyer.BuyerContext;


@MessageDriven(name = "PublicPurchaseBooksListener", activationConfig = {
		@ActivationConfigProperty(propertyName = "clientId", propertyValue = "public.bookshop2.buyer.purchaseBooks"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/queue/public_bookshop2_buyer_purchase_books_queue"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
		@ActivationConfigProperty(propertyName = "reconnectAttempts", propertyValue = "-1"),
		@ActivationConfigProperty(propertyName = "maxSession", propertyValue = "2")
})
@TransactionManagement(CONTAINER)
@Interceptors(JMSListenerInterceptor.class)
public class PurchaseBooksListenerForJMS extends AbstractJMSListener implements MessageListener {
	
	@Resource
	private MessageDrivenContext messageDrivenContext;
	
	@Inject
	private PurchaseBooksHandler purchaseBooksHandler;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Override
	public String getModuleId() {
		return "bookshop2-buyer-service";
	}
	
	@Override
	public String getServiceId() {
		return PurchaseBooks.ID;
	}
	
	@Override
	public Object getDelegate() {
		return purchaseBooksHandler;
	}
	
	protected BuyerContext getContext(Message message) {
		return getContext(getCorrelationId(message));
	}

	protected BuyerContext getContext(String correlationId) {
		return BuyerContext.getInstance(correlationId);
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public void onMessage(javax.jms.Message jmsMessage) {
		BuyerContext buyerContext = getContext(jmsMessage);
		if (buyerContext == null)
			return;
		if (!isInitialized())
			return;
		
		try {
			PurchaseRequestMessage purchaseRequestMessage = MessageUtil.getPart(jmsMessage, "purchaseRequestMessage");
			
			//validate the message
			buyerContext.validate(purchaseRequestMessage);
			
			//handle the message
			purchaseBooksHandler.purchaseBooks(purchaseRequestMessage);
			
		} catch (Throwable e) {
			log.error(e);
			//TODO send this exception to "invalid" queue
			buyerContext.fire_PurchaseBooks_incoming_request_aborted(e);
		}
	}
	
}
