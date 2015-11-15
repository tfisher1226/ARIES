package bookshop2.buyer.incoming.orderBooks;

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

import bookshop2.OrderAcceptedMessage;
import bookshop2.buyer.BuyerContext;


@MessageDriven(name = "PublicOrderAcceptedListener", activationConfig = {
		@ActivationConfigProperty(propertyName = "clientId", propertyValue = "public.bookshop2.buyer.orderAccepted"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/queue/public_bookshop2_buyer_order_accepted_queue"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
		@ActivationConfigProperty(propertyName = "reconnectAttempts", propertyValue = "-1"),
		@ActivationConfigProperty(propertyName = "maxSession", propertyValue = "2")
})
@TransactionManagement(CONTAINER)
@Interceptors(JMSListenerInterceptor.class)
public class OrderAcceptedListenerForJMS extends AbstractJMSListener implements MessageListener {
	
	@Resource
	private MessageDrivenContext messageDrivenContext;
	
	@Inject
	private OrderAcceptedHandler orderAcceptedHandler;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Override
	public String getModuleId() {
		return "bookshop2-buyer-service";
	}
	
	@Override
	public String getServiceId() {
		return OrderAccepted.ID;
	}
	
	@Override
	public Object getDelegate() {
		return orderAcceptedHandler;
	}
	
	protected BuyerContext getContext(Message message) {
		return getContext(getCorrelationId(message));
	}

	protected BuyerContext getContext(String correlationId) {
		return BuyerContext.getInstance(correlationId);
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public void onMessage(Message jmsMessage) {
		BuyerContext buyerContext = getContext(jmsMessage);
		if (buyerContext == null)
			return;
		if (!isInitialized())
			return;
		
		try {
			OrderAcceptedMessage orderAcceptedMessage = MessageUtil.getPart(jmsMessage, "orderAcceptedMessage");
			
			//validate the message
			buyerContext.validate(orderAcceptedMessage);
			
			//handle the message
			orderAcceptedHandler.orderAccepted(orderAcceptedMessage);
			
		} catch (Throwable e) {
			log.error(e);
			//TODO send this exception to "invalid" queue
			buyerContext.fire_OrderBooks_incoming_request_aborted(e);
		}
	}
	
}
