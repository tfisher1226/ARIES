package org.aries.data;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;

import org.aries.util.ExceptionUtil;

import com.arjuna.ats.internal.arjuna.thread.ThreadActionData;


public abstract class AbstractRepository {
	
	public Transaction getTransaction() throws SystemException {
		return getTransactionManager().getTransaction();
	}

//	public TransactionManager getTransactionManager() {
//		return com.arjuna.ats.jta.TransactionManager.transactionManager();
//	}
	
	public TransactionManager getTransactionManager() {
		try {
			InitialContext initialContext = new InitialContext();
			TransactionManager transactionManager = (TransactionManager) initialContext.lookup("java:jboss/TransactionManager");
			
			if (transactionManager == null)
				//This will be null when called from outside the container
				transactionManager = com.arjuna.ats.jta.TransactionManager.transactionManager();
			
			//TransactionManager transactionManager = com.arjuna.ats.jta.TransactionManager.transactionManager();
			return transactionManager;
		} catch (NamingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String beginTransaction() {
		//assertNoTransaction();
		String transactionId = null;
		try {
			TransactionManager tm = getTransactionManager();
			tm.setTransactionTimeout(120);
			tm.begin();
			Transaction transaction = tm.getTransaction();
			transactionId = transaction.toString();
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
		//assertTransactionRunning();
		return transactionId;
	}

	public void commitTransaction() {
		//assertTransactionRunning();
		try {
			TransactionManager tm = getTransactionManager();
			tm.commit();
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		} finally {
			ThreadActionData.popAction();
		}
		//assertTransactionCommitted();
	}

	public void rollbackTransaction() {
		//assertTransactionRunning();
		try {
			TransactionManager tm = getTransactionManager();
			tm.rollback();
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		} finally {
			ThreadActionData.popAction();
		}
		//assertTransactionAborted();
	}
	
}
