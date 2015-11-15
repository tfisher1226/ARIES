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
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.transaction.TransactionSynchronizationRegistry;

import org.aries.jms.util.MessageUtil;
import org.aries.runtime.BeanContext;
import org.aries.tx.service.jms.AbstractJMSListener;
import org.aries.tx.service.jms.JMSListenerInterceptor;

import bookshop2.OrderRequestMessage;
import bookshop2.buyer.BuyerContext;
import bookshop2.buyer.outgoing.orderAccepted.OrderAcceptedReply;
import bookshop2.buyer.outgoing.orderRejected.OrderRejectedReply;


@MessageDriven(name = "PublicOrderBooksListener", activationConfig = {
		@ActivationConfigProperty(propertyName = "clientId", propertyValue = "public.bookshop2.buyer.orderBooks"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/queue/public_bookshop2_buyer_order_books_queue"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
		@ActivationConfigProperty(propertyName = "reconnectAttempts", propertyValue = "-1"),
		@ActivationConfigProperty(propertyName = "maxSession", propertyValue = "2")
})
@TransactionManagement(CONTAINER)
@Interceptors(JMSListenerInterceptor.class)
public class OrderBooksListenerForJMS extends AbstractJMSListener implements MessageListener {
	
	@Resource
	private MessageDrivenContext messageDrivenContext;
	
	@Inject
	private OrderBooksHandler orderBooksHandler;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Override
	public String getModuleId() {
		return "bookshop2-buyer-service";
	}
	
	@Override
	public String getServiceId() {
		return OrderBooks.ID;
	}
	
	@Override
	public Object getDelegate() {
		return orderBooksHandler;
	}
	
	protected BuyerContext initializeContext(Message message) {
		String correlationId = getCorrelationId(message);
		BuyerContext context = getContext(correlationId);
		Destination jmsReplyTo = MessageUtil.getJMSReplyTo(message);
		context.addReplyToDestination("OrderAccepted", correlationId, jmsReplyTo);
		context.addReplyToDestination("OrderRejected", correlationId, jmsReplyTo);
		return context;
	}

	protected BuyerContext getContext(String correlationId) {
		return BuyerContext.getInstance(correlationId);
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public void onMessage(Message jmsMessage) {
		BuyerContext buyerContext = initializeContext(jmsMessage);
		if (buyerContext == null)
			//TODO add invalid
			return;
		if (!isInitialized())
			//TODO add invalid
			return;
		
		try {
			OrderRequestMessage orderRequestMessage = MessageUtil.getPart(jmsMessage, "orderRequestMessage");
			
			//validate the message
			buyerContext.validate(orderRequestMessage);
			
			//handle the message
			orderBooksHandler.orderBooks(orderRequestMessage);
			
		} catch (Throwable e) {
			log.error(e);
			//TODO send this exception to "invalid" queue
			buyerContext.fire_OrderBooks_incoming_request_aborted(e);
			//orderBooksHandler = getOrderBooksHandler();
		}
	}
	
	public OrderBooksHandler getOrderBooksHandler() {
		return BeanContext.getFromJNDI(OrderBooksHandler.class, "OrderBooksHandler");
	}
	
}
