package bookshop2.supplier.incoming.reserveBooks;

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
import javax.jms.MessageListener;
import javax.transaction.TransactionSynchronizationRegistry;

import org.aries.jms.util.MessageUtil;
import org.aries.tx.service.jms.AbstractJMSListener;
import org.aries.tx.service.jms.JMSListenerInterceptor;

import bookshop2.ReservationRequestMessage;
import bookshop2.supplier.SupplierContext;


public class ReserveBooksListenerForJMSGood extends AbstractJMSListener implements MessageListener {
	
	@Inject
	private SupplierContext supplierContext;
	
	@Resource
	private MessageDrivenContext messageDrivenContext;
	
	@Inject
	private ReserveBooksHandler reserveBooksHandler;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Override
	public String getModuleId() {
		return "bookshop2-supplier-service";
	}
	
	@Override
	public String getServiceId() {
		return ReserveBooks.ID;
	}
	
	@Override
	public Object getDelegate() {
		return reserveBooksHandler;
	}
	
	@Override
	@TransactionAttribute(REQUIRED)
	public void onMessage(javax.jms.Message jmsMessage) {
		if (!isInitialized())
			return;

		try {
			ReservationRequestMessage reservationRequestMessage = MessageUtil.getPart(jmsMessage, "reservationRequestMessage");
			
			//validate the message
			supplierContext.validate(reservationRequestMessage);
			
			//handle the message
			reserveBooksHandler.reserveBooks(reservationRequestMessage);

		} catch (Throwable e) {
			log.error(e);
			//TODO send this exception to "invalid" queue
			supplierContext.fire_ReserveBooks_incoming_request_aborted(e);
		}
	}

	
//	Object transactionKey = transactionSynchronizationRegistry.getTransactionKey();
//	//int transactionStatus = transactionSynchronizationRegistry.getTransactionStatus();
//	String transactionId = transactionKey.toString();

//	BasicAction currentAction = ThreadActionData.currentAction();
//	if (currentAction == null)
//		return;
//	String transactionId = ThreadActionData.currentAction().get_uid().toString();
	
//	//transactionSynchronizationRegistry.get
//	TransactionImple theTransaction = TransactionImple.getTransaction();
//	
//	try {
//		javax.transaction.TransactionManager transactionManager = com.arjuna.ats.jta.TransactionManager.transactionManager();
//		TransactionImple transaction = (TransactionImple) transactionManager.getTransaction();
//		if (transaction == null)
//			return;
//		
//		int status = transaction.getStatus();
//		//transactionId = TransactionManager.getTransactionId();
//	} catch (Exception e) {
//		e.printStackTrace();
//		return;
//	}
	

	/*
	public void onMessage(Message message) {
		Initializer initializer = BeanContext.get("bookshop2.supplier.initializer");
		if (initializer == null)
			return;

		Object transactionKey = transactionSynchronizationRegistry.getTransactionKey();
		int transactionStatus = transactionSynchronizationRegistry.getTransactionStatus();
		
		try {
			String correlationId = getCorrelationId(message);
			Map<String, Serializable> conversationState = SupplierOrderRegistry.getInstance().getConversationState(correlationId);
			if (conversationState == null) {
				conversationState = new HashMap<String, Serializable>();
				conversationState.put("correlationId", correlationId);
				conversationState.put("originatingChannel", "reserveBooks");
				conversationState.put("originatingTransport", "JMS");
				SupplierOrderRegistry.getInstance().registerConversationState(correlationId, conversationState);
			}

			reserveBooksTransactor.setCorrelationId(correlationId);
			ReservationRequestMessage reservationRequestMessage = getObjectFromMessage(message);
			//ReserveBooksHandlerImpl transactor = BeanContext.get(ReserveBooksHandlerImpl.class, correlationId);
			
			TaskInvokerFactory factory = BeanContext.get("org.aries.invokerFactory");
			TaskInvoker invoker = factory.createInvoker(reserveBooksTransactor);
			invoker.setMethodName("reserveBooks");
			invoker.addParameter(reservationRequestMessage);
			invoker.invoke();

		} catch (Exception e) {
			log.error(e);
			ExceptionUtil.rethrow(e);
		}
	}
	 */

}
