package org.aries.tx;

import javax.transaction.Transaction;


public abstract class AbstractJMSClientArquillionTXTest extends AbstractJMSClientArquillionTest {

	protected String getTransactionId() throws Exception {
		return TransactionUtil.getTransactionId();
	}
	
	protected Transaction getTransaction() throws Exception {
		return TransactionUtil.getTransaction();
	}

//	protected Transaction getTransaction(String transactionId) throws Exception {
//		return TransactionUtil.getTransaction(transactionId);
//	}

	protected void beginTransaction() throws Exception {
		TransactionUtil.beginTransaction();
	}
	
	protected void beginTransaction(int timeout) throws Exception {
		TransactionUtil.beginTransaction(timeout);
	}
	
	protected String registerTransaction() throws Exception {
		return TransactionUtil.registerTransaction();
	}
	
	protected String registerTransaction(Transaction transaction) throws Exception {
		return TransactionUtil.registerTransaction(transaction);
	}
	
	protected void registerTransaction(Transaction transaction, String transactionId) throws Exception {
		TransactionUtil.registerTransaction(transaction, transactionId);
	}
	
	protected void commitTransaction() throws Exception {
		TransactionUtil.commitTransaction();
	}
	
	protected void rollbackTransaction() throws Exception {
		TransactionUtil.rollbackTransaction();
	}
	
	protected void resetTransactionContext() throws Exception {
		TransactionUtil.resetTransactionContext();		
	}

	
}
