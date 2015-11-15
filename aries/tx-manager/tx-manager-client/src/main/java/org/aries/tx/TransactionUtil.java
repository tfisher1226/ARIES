package org.aries.tx;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.Status;
import javax.transaction.Synchronization;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.transaction.TransactionSynchronizationRegistry;
import javax.transaction.UserTransaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.arjuna.ats.arjuna.coordinator.BasicAction;
import com.arjuna.ats.internal.arjuna.thread.ThreadActionData;


public class TransactionUtil {

	public static final Log log = LogFactory.getLog(TransactionUtil.class);
	
	
	public static BasicAction currentAction() {
		BasicAction action = ThreadActionData.currentAction();
		return action;
	}

	public static BasicAction popAction() {
		BasicAction action = ThreadActionData.popAction();
		return action;
	}
	
	public static UserTransaction getUserTransaction() {
		try {
			InitialContext initialContext = new InitialContext();
			UserTransaction userTransaction = (UserTransaction) initialContext.lookup("java:jboss/UserTransaction");
			return userTransaction;
		} catch (NamingException e) {
			log.error("Error", e);
			return null;
		}
	}
	
	public static TransactionManager getTransactionManager() {
		try {
			InitialContext initialContext = new InitialContext();
			TransactionManager transactionManager = (TransactionManager) initialContext.lookup("java:jboss/TransactionManager");
			return transactionManager;
		} catch (NamingException e) {
			log.error("Error", e);
			return null;
		}
	}

	public static TransactionSynchronizationRegistry getTransactionSynchronizationRegistry() {
		try {
			InitialContext initialContext = new InitialContext();
			TransactionSynchronizationRegistry transactionSynchronizationRegistry = (TransactionSynchronizationRegistry) initialContext.lookup("java:jboss/TransactionSynchronizationRegistry");
			return transactionSynchronizationRegistry;
		} catch (NamingException e) {
			log.error("Error", e);
			return null;
		}
	}
	
	public static String getTransactionId() {
		String transactionId = getTransactionSynchronizationRegistry().getTransactionKey().toString();
		return transactionId;
	}
	
	public static String getTransactionIdOLD() throws Exception {
		String transactionId = ThreadActionData.currentAction().get_uid().toString();
		return transactionId;
	}
	
	public static Transaction getTransaction() {
		try {
			return getTransactionManager().getTransaction();
		} catch (Exception e) {
			log.error("Error", e);
			return null;
		}
	}
	
	
	public static void beginTransaction() throws Exception {
		beginTransaction(30);
	}
	
	public static void beginTransaction(int timeout) throws Exception {
		log.info("beginTransaction() started: "+timeout);
		TransactionManager transactionManager = getTransactionManager();
		transactionManager.setTransactionTimeout(timeout);
		transactionManager.begin();
		String transactionId = registerTransaction();
		log.info("beginTransaction() done: "+transactionId);
	}
	
	public static String registerTransaction() {
		try {
			Transaction transaction = getTransaction();
			String transactionId = registerTransaction(transaction);
			return transactionId;
		} catch (Exception e) {
			log.error("Error", e);
			return null;
		}
	}
	
	public static String registerTransaction(Transaction transaction) {
		try {
			String transactionId = getTransactionId();
			registerTransaction(transaction, transactionId);
			return transactionId;
		} catch (Exception e) {
			log.error("Error", e);
			return null;
		}
	}
	
	public static void registerTransaction(Transaction transaction, String transactionId) {
		TransactionRegistry.getInstance().registerTransaction(transactionId, transaction);
	}
	
	public static void unregisterTransaction(String transactionId) {
		TransactionRegistry.getInstance().removeTransaction(transactionId);
	}
	
	public static void commitTransaction() throws Exception {
		Transaction transaction = getTransaction();
		transaction.commit();
		ThreadActionData.popAction();
	}
	
	public static void rollbackTransaction() throws Exception {
		Transaction transaction = getTransaction();
		transaction.rollback();
	}
	
	public static void resetTransactionContext() throws Exception {
		ThreadActionData.popAction();		
	}

	public static String getStatus() throws Exception {
		return getStatus(getTransaction().getStatus());
	}

	public static String getStatus(Transaction transaction) throws Exception {
		return getStatus(transaction.getStatus());
	}
	
	public static String getStatus(int status) {
		switch (status) {
		case Status.STATUS_ACTIVE: 
			return ("ACTIVE");
		case Status.STATUS_MARKED_ROLLBACK: 
			return ("MARKED_ROLLBACK");
		case Status.STATUS_PREPARED: 
			return ("PREPARED");
		case Status.STATUS_COMMITTED: 
			return ("COMMITTED");
		case Status.STATUS_ROLLEDBACK: 
			return ("ROLLEDBACK");
		case Status.STATUS_UNKNOWN: 
			return ("UNKNOWN");
		case Status.STATUS_NO_TRANSACTION: 
			return ("NO_TRANSACTION");
		case Status.STATUS_PREPARING: 
			return ("PREPARING");
		case Status.STATUS_COMMITTING: 
			return ("COMMITTING");
		case Status.STATUS_ROLLING_BACK: 
			return ("ROLLING_BACK");
		default: 
			return ("Unrecognized: "+status);
		}
	}
	


	public static void registerSynchronization() throws Exception {
		Transaction transaction = getTransaction();
		String transactionId = getTransactionId();
		SynchronizationImpl synchronization = new SynchronizationImpl(transactionId);
		transaction.registerSynchronization(synchronization);
	}

	
    static class SynchronizationImpl implements Synchronization {
        private String transactionID;

        SynchronizationImpl(String  transactionID) {
            this.transactionID = transactionID;
        }

        public void beforeCompletion() {
        	//EJBClientConfiguration ejbClientConfiguration = EJBClientContext.requireCurrent().getEJBClientConfiguration();
        	//ClusterContext clusterContext = EJBClientContext.requireCurrent().getClusterContext("");

        	try {
        		TransactionManager transactionManager = getTransactionManager();
				Transaction transaction = transactionManager.getTransaction();
	        	//log.info(">>> beforeCompletion() transaction: "+transactionID+", "+transaction.getStatus());
	        	//System.out.println();
	        	
			} catch (Exception e) {
				e.printStackTrace();
			}
        }

        public void afterCompletion(int status) {
        	//log.info(">>> afterCompletion() transaction: "+transactionID+", "+status);
        	//System.out.println();
        }
    }
    
}
