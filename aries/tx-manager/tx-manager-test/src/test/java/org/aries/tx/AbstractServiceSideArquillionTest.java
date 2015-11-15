package org.aries.tx;

import common.tx.state.AbstractStateManager;


public abstract class AbstractServiceSideArquillionTest extends AbstractArquillianTest {
	
	public abstract String getDomainId();
	
	public abstract String getTargetArchive();
	
	public abstract AbstractStateManager<?> getStateManager();

	protected static TransactionTestControl transactionTestControl;

	
	public void setUp() throws Exception {
		isLocal = true;
		super.setUp();
	}
	
	public void tearDown() throws Exception {
		transactionTestControl.clearTransactions();
		super.tearDown();
	}

	protected Transactional enrollGlobalTransaction(AbstractStateManager<?> stateManager, String transactionId) {
		Transactional transactional = transactionTestControl.createMockTransactional(stateManager);
		enrollTransaction("ReservedBooks", transactionId, transactional);
		return transactional;
	}

	protected void enrollTransaction(String transactionName, String transactionId, Transactional transactional) {
		TransactionParticipantManager.getInstance().enrollTransaction(transactionName, transactionId, transactional);
	}
	
	
	protected void beginTransaction() throws Exception {
		if (isJtaTransaction) {
			transactionTestControl.beginTransaction();
			transactionTestControl.assertActive();
			
		} else if (isUserTransaction) {
			transactionTestControl.beginUserTransaction();

		} else if (isGlobalTransaction) {
			transactionId = transactionTestControl.beginGlobalTransaction();
			enrollGlobalTransaction(getStateManager(), transactionId);
			transactionTestControl.assertActive(getStateManager());
			transactionTestControl.assertActionRunning();
		}
	}
	
	protected void beginJTATransaction() throws Exception {
		transactionTestControl.beginTransaction();
		transactionTestControl.assertActive();
	}
	
	protected void beginUserTransaction() throws Exception {
		transactionTestControl.beginUserTransaction();
	}
	
	protected void beginGlobalTransaction() throws Exception {
		transactionId = transactionTestControl.beginGlobalTransaction();
		enrollGlobalTransaction(getStateManager(), transactionId);
		transactionTestControl.assertActive(getStateManager());
		transactionTestControl.assertActionRunning();
	}
	
	protected void commitTransaction() throws Exception {
		if (isJtaTransaction) {
			transactionTestControl.commitTransaction();
			transactionTestControl.assertCommitted();
			transactionTestControl.clearTransactions();

		} else if (isUserTransaction) {
			transactionTestControl.commitUserTransaction();

		} else if (isGlobalTransaction) {
			transactionTestControl.commitGlobalTransaction();
			transactionTestControl.assertCommitted(getStateManager());
			transactionTestControl.assertActionCommitted();
		}
	}
	
	protected void commitJTATransaction() throws Exception {
		transactionTestControl.commitTransaction();
		transactionTestControl.assertCommitted();
		transactionTestControl.clearTransactions();
	}
	
	protected void commitUserTransaction() throws Exception {
		transactionTestControl.commitUserTransaction();
	}
	
	protected void commitGlobalTransaction() throws Exception {
		transactionTestControl.commitGlobalTransaction();
		transactionTestControl.assertCommitted(getStateManager());
		transactionTestControl.assertActionCommitted();
	}

	protected void rollbackTransaction() throws Exception {
		if (isJtaTransaction) {
			transactionTestControl.rollbackTransaction();
			transactionTestControl.assertRolledBack();
			transactionTestControl.clearTransactions();
			
		} else if (isUserTransaction) {
			transactionTestControl.rollbackUserTransaction();

		} else if (isGlobalTransaction) {
			transactionTestControl.rollbackGlobalTransaction();
			transactionTestControl.assertRolledBack(getStateManager());
			transactionTestControl.assertActionAborted();
		}
	}

	protected void rollbackJTATransaction() throws Exception {
		transactionTestControl.rollbackTransaction();
		transactionTestControl.assertRolledBack();
		transactionTestControl.clearTransactions();
	}
	
	protected void rollbackUserTransaction() throws Exception {
		transactionTestControl.rollbackUserTransaction();
	}
	
	protected void rollbackGlobalTransaction() throws Exception {
		transactionTestControl.rollbackGlobalTransaction();
		transactionTestControl.assertRolledBack(getStateManager());
		transactionTestControl.assertActionAborted();
	}
	
	
	protected void clearTransaction() throws Exception {
		transactionTestControl.clearTransactions();
	}

}
