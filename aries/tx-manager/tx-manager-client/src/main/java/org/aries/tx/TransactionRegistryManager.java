package org.aries.tx;

import javax.transaction.Status;
import javax.transaction.TransactionManager;

import org.aries.Assert;


public class TransactionRegistryManager implements TransactionRegistryManagerMBean {

	private static TransactionRegistryManager instance = new TransactionRegistryManager();
	
	public static TransactionRegistryManager getInstance() {
		return instance;
	}

	public int getStatus() throws Exception {
		return getTransactionManager().getStatus();
	}

//	public int getStatus(String transactionId) throws Exception {
//		return getTransactionManager().get
//	}
	
	protected TransactionManager getTransactionManager() {
		return com.arjuna.ats.jta.TransactionManager.transactionManager();
	}

	public boolean isGlobalTransactionActive() {
		//return TransactionRegistry.getInstance().getTransaction() != null;
		return false;
	}

	public void assertActive(String correlationId) throws Exception {
		int status = getStatus();
		Assert.equals(Status.STATUS_ACTIVE, status, "Status should be ACTIVE (0): "+status);
	}
	
	public void assertCommitted(String correlationId) throws Exception {
		int status = getStatus();
		Assert.equals(Status.STATUS_COMMITTED, status, "Status should be COMMITTED (3): "+status);
	}
	
	public void assertRolledBack(String correlationId) throws Exception {
		TransactionRegistry.getInstance().getArchivedTransaction(correlationId);
		int status = getStatus();
		Assert.equals(Status.STATUS_ROLLEDBACK, status, "Status should be ROLLEDBACK (4): "+status);
	}

}
