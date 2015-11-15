package org.aries.tx;

import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.util.ExceptionUtil;

import com.arjuna.ats.arjuna.coordinator.BasicAction;
import com.arjuna.ats.internal.arjuna.thread.ThreadActionData;


public class TransactionHelper {

	//TODO make this configurable on a per tx basis
	private static final int DEFAULT_TIMEOUT = 300; //300 seconds for now

	private static Log log = LogFactory.getLog(TransactionHelper.class);
	
	private Transaction transaction;
	
	private boolean localTxCreated;
	
	
	public static TransactionManager getTransactionManager() {
		return com.arjuna.ats.jta.TransactionManager.transactionManager();
	}
	
	public static boolean isActive() {
		BasicAction action = ThreadActionData.currentAction();
		return action != null;
	}
	
	public static boolean isActive(Transaction transaction) throws SystemException {
		return transaction != null && transaction.getStatus() == 4;
	}
	
	/**
	 * Process the tx context header that is attached to the received message.
	 * @throws Exception
	 */
	public void open() {
		BasicAction action = ThreadActionData.currentAction();
		//TODO process what to do if/when no tx is active  
		if (action == null) {
			try {
				TransactionManager transactionManager = getTransactionManager();
				//Transaction transaction2 = transactionManager.getTransaction();
				//int status = transactionManager.getTransaction().getStatus();
				transactionManager.setTransactionTimeout(DEFAULT_TIMEOUT);
				transactionManager.begin();
				Transaction transaction = transactionManager.getTransaction();
				//TODO TransactionRegistry.getInstance().registerTransaction("current", transaction);
				action = ThreadActionData.currentAction();
				localTxCreated = true;
			} catch (Exception e) {
				//TODO handle this according to policy
			}
		}		
		if (action != null) {
			String transactionId = action.get_uid().toString();
			open(transactionId);
		}
	}
	
	/**
	 * Process the tx context header that is attached to the received message.
	 * @param transactionId The current transaction Id.
	 * @throws RuntimeException
	 */
	public void open(String transactionId) {
		TransactionManager transactionManager = getTransactionManager();

//		Transaction transaction = transactionManager.getTransaction();
//		BasicAction currentAction = ThreadActionData.currentAction();
//		ThreadActionData.pushAction(currentAction.getImple().getImplHandle());

		try {
			transaction = transactionManager.getTransaction();
			boolean transactionExists = transaction != null;
			//boolean transactionExists = false;
			if (!transactionExists) {
				transactionManager.suspend();
				transactionManager.begin();
				Transaction childTransaction = transactionManager.getTransaction();
				String childTransactionId = ThreadActionData.currentAction().get_uid().toString();
				//TODO TransactionRegistry.getInstance().registerTransaction(childTransactionId, childTransaction);
			}
			
//			transactionManager.begin();
//			Transaction transaction = transactionManager.getTransaction();
//			transactionExists = TransactionUtil.isActive(transaction);
//			if (transactionExists || true) {
//				entityManager.joinTransaction();
//			}

		} catch (Throwable e) {
			log.error(e);
			ExceptionUtil.rethrow(e);
		}
	}
	
	/**
	 * Tidy up the Transaction/Thread association before response is returned to the client.
	 * @param transactionId The current transaction Id.
	 * @throws RuntimeException
	 */
	public void close() {
		try {
			//Transaction transaction = ConversationRegistry.getInstance().getTransaction(transactionId);
			TransactionManager transactionManager = getTransactionManager();
			boolean transactionExists = transaction != null;
			if (!transactionExists || localTxCreated) {
				Transaction transaction2 = transactionManager.getTransaction();
				transactionManager.commit();
				transactionManager.resume(transaction);
				ThreadActionData.popAction();
			}
			//suspendTransaction();

		} catch (Throwable e) {
			log.error(e);
			ExceptionUtil.rethrow(e);
		}
	}

	/**
	 * Tidy up the Transaction/Thread association before faults are thrown back to the client.
	 * @param exception
	 * @return true
	 */
	public void fault(Throwable exception) {
		try {
			//Transaction transaction = ConversationRegistry.getInstance().getTransaction(transactionId);
			TransactionManager transactionManager = getTransactionManager();
			boolean transactionExists = transaction != null;
			if (!transactionExists) {
				try {
					transactionManager.rollback();
					transactionManager.resume(transaction);
				} catch (Exception e2) {
					log.error(e2);
				}
			}
			//suspendTransaction();

		} catch (Throwable e) {
			log.error(e);
			ExceptionUtil.rethrow(e);
		}
	}
	
//	public void printTransactionId() {
//		if (transactionSynchronizationRegistry != null) {
//			Object transactionKey = transactionSynchronizationRegistry.getTransactionKey();
//			//int transactionStatus = transactionSynchronizationRegistry.getTransactionStatus();
//			if (transactionKey == null)
//				System.out.println();
//			if (transactionKey != null) {
//				String transactionId = transactionKey.toString();
//				System.out.println("MANAGER>>>>>>>>>>>>>> transactionId = "+transactionId);
//			}
//		}
//	}
	
}
