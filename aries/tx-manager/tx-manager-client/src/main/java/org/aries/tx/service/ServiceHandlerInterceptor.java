package org.aries.tx.service;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.SessionContext;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.transaction.TransactionSynchronizationRegistry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.common.AbstractMessage;
import org.aries.tx.TransactionRegistry;
import org.aries.tx.TransactionUtil;
import org.aries.tx.UserTransaction;
import org.aries.tx.UserTransactionFactory;
import org.aries.tx.service.jms.AbstractInterceptor;
import org.aries.util.ExceptionUtil;

import com.arjuna.ats.internal.arjuna.thread.ThreadActionData;
import com.arjuna.ats.jta.common.jtaPropertyManager;


public class ServiceHandlerInterceptor extends AbstractInterceptor implements Serializable {

	private static Log log = LogFactory.getLog(ServiceHandlerInterceptor.class);
	
	@Resource
	private SessionContext sessionContext;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	@Inject 
	private Event<EJBContext> progressListeners;
	
	
	public String getTransactionId() {
		return transactionSynchronizationRegistry.getTransactionKey().toString();
	}
	
	protected Transaction getTransaction() {
		try {
			return getTransactionManager().getTransaction();
		} catch (Exception e) {
			log.error("Error", e);
			return null;
		}
	}
	
	protected TransactionManager getTransactionManager() {
		try {
			InitialContext initialContext = new InitialContext();
			TransactionManager transactionManager = (TransactionManager) initialContext.lookup("java:jboss/TransactionManager");
			return transactionManager;
		} catch (NamingException e) {
			log.error("Error", e);
			return null;
		}
	}

	@AroundInvoke
	public Object aroundInvoke(InvocationContext invocationContext) {
		//InterceptorContext interceptorContext = (InterceptorContext) invocationContext;
		//Map<String, Object> contextData = invocationContext.getContextData();
		Assert.notNull(invocationContext, "Invocation-context must exist");
		Assert.notNull(sessionContext, "Session context must exist");
		//Assert.notNull(messageDrivenContext, "Message driven context must exist");
		
		Object[] parameters = invocationContext.getParameters();
		if (parameters.length == 1 && parameters[0] instanceof AbstractMessage) {
			AbstractMessage message = (AbstractMessage) parameters[0];
			transactionSynchronizationRegistry.putResource("correlationId", message.getCorrelationId());
			transactionSynchronizationRegistry.putResource("transactionId", message.getTransactionId());
			transactionSynchronizationRegistry.putResource("replyToDestination", message.getReplyToDestinations());
		}
		//Object transactionKey = transactionSynchronizationRegistry.getTransactionKey();
		//String transactionIdFromContainer = transactionKey.toString();
		
		try {
			//reset this again here in case somehow it became unset...
			jtaPropertyManager.getJTAEnvironmentBean().setSupportSubtransactions(true);
			
			//TransactionManager transactionManager = getTransactionManager();
			//String transactionID = getTransactionId();
			//int status = transactionManager.getStatus();
			//log.info(">>>>>>>>>>>>>>>>> "+getTransactionId()+", "+status);

			//Transaction transaction = transactionManager.getTransaction();
			//TransactionUtil.registerTransaction(transaction, reservationRequestMessage.getCorrelationId());

			TransactionUtil.registerSynchronization();
			
			//SynchronizationImpl synchronization = new SynchronizationImpl(transactionID);
			//transaction.registerSynchronization(synchronization);
			
			//conversation.begin();
			//Message message = getMessageFromContext(invocationContext);
			//String correlationId = MessageUtil.getCorrelationIdFromMessage(message);
			//String transactionId = MessageUtil.getTransactionIdFromMessage(message);
			//Object transactionContext = MessageUtil.getTransactionContextFromMessage(message);
			
			//String correlationId = getCorrelationIdFromContext(invocationContext);
			//String transactionId = getTransactionIdFromContext(invocationContext);
			//org.aries.nam.model.old.TransactionContext transactionContext = getTransactionContext(invocationContext);
			
			//if (transactionId != null) {
			//	TransactionContext managementContext = createManagementContext(invocationContext);
			//	TransactionContextManager.getInstance().start(managementContext, transactionId);
			//	TransactionContextManager.getInstance().start(managementContext);
			//	beforeInvocation(transactionId);
			//}
			
			//Map<String, Serializable> conversationState = ConversationRegistry.getInstance().getConversationState(correlationId);
			//conversationState.put(MessageConstants.PROPERTY_CORRELATION_ID, correlationId);
			//conversationState.put(MessageConstants.PROPERTY_TRANSACTION_ID, transactionId);
			
			//transactionSynchronizationRegistry.putResource(MessageConstants.PROPERTY_CORRELATION_ID, correlationId);
			//transactionSynchronizationRegistry.putResource(MessageConstants.PROPERTY_TRANSACTION_ID, transactionId);
			//transactionSynchronizationRegistry.putResource(MessageConstants.PROPERTY_TRANSACTION_CONTEXT, correlationId);

			//generateEvent(invocationContext);
			
			Object value = invocationContext.proceed();
			//afterInvocation(transactionId);
			//commitTransaction();
			return value;
			
		} catch (Exception e) {
			//abortTransaction();
			throw ExceptionUtil.rewrap(e);
			
		} finally {
			//afterInvocation(transactionId);
			progressListeners.fire(sessionContext);
		}
	}

	
	protected boolean beforeInvocation(String transactionId) {
		try {
//			javax.transaction.TransactionManager transactionManager = TransactionManager.transactionManager();
//			transactionManager.resume(transaction);
//			boolean transactionExists = transaction != null;
//			if (!transactionExists) {
//				transactionManager.suspend();
//				transactionManager.begin();
//				Transaction childTransaction = transactionManager.getTransaction();
//				transactionId = ThreadActionData.currentAction().get_uid().toString();
//				TransactionRegistry.getInstance().registerTransaction(transactionId, childTransaction);
//			}
			return true;
			
		} catch (Throwable e) {
			log.error(e);
			return false;
		}
	}
	
	protected boolean beforeInvocationTEMP(String transactionId) {
		try {
			Transaction transaction = TransactionRegistry.getInstance().getTransaction(transactionId);
			TransactionManager transactionManager = getTransactionManager();
			transactionManager.resume(transaction);
			boolean transactionExists = transaction != null;
			if (!transactionExists) {
				transactionManager.suspend();
				transactionManager.begin();
				Transaction childTransaction = transactionManager.getTransaction();
				transactionId = ThreadActionData.currentAction().get_uid().toString();
				TransactionRegistry.getInstance().registerTransaction(transactionId, childTransaction);
			}
			return true;
			
		} catch (Throwable e) {
			log.error(e);
			return false;
		}
	}
	
	/**
	 * Tidy up the Transaction/Thread association before control is returned to the caller.
	 * @param messageContext The current message context.
	 * @return true
	 */
	protected boolean afterInvocation(String transactionId) {
		try {
			Transaction transaction = TransactionRegistry.getInstance().getTransaction(transactionId);
			TransactionManager transactionManager = getTransactionManager();
			boolean transactionExists = transaction != null;
			if (transactionExists) {
				transactionManager.commit();
				transactionManager.resume(transaction);
			}
			//suspendTransaction();
			return true;

		} catch (Throwable e) {
			log.error(e);
			return false;
		}
	}
	
	//@AroundInvoke
	public Object call(InvocationContext invocationContext) {
		beginTransaction();

		try {
			Object value = invocationContext.proceed();
			commitTransaction();
			return value;
			
		} catch (Exception e) {
			abortTransaction();
			throw ExceptionUtil.rewrap(e);
			
		} finally {
			progressListeners.fire(sessionContext);
		}
	}
	
	
	protected void beginTransaction() {
		try {
			UserTransaction userTransaction = UserTransactionFactory.getUserTransaction();
			userTransaction.begin();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void commitTransaction() {
		try {
			UserTransaction userTransaction = UserTransactionFactory.getUserTransaction();
			userTransaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void rollbackTransaction() {
		try {
			UserTransaction userTransaction = UserTransactionFactory.getUserTransaction();
			userTransaction.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void abortTransaction() {
		try {
			UserTransaction userTransaction = UserTransactionFactory.getUserTransaction();
			userTransaction.setRollbackOnly();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
