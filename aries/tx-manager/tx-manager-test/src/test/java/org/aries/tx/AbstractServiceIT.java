package org.aries.tx;

import javax.transaction.Transaction;

import org.aries.Assert;
import org.aries.util.ExceptionUtil;
import org.aries.util.properties.PropertyManager;

import com.arjuna.ats.arjuna.coordinator.ActionStatus;
import com.arjuna.ats.internal.arjuna.thread.ThreadActionData;
import common.tx.management.TXManagerClientSideLauncher;
import common.tx.management.TXManagerServiceSideLauncher;
import common.tx.state.ServiceStateManager;


public abstract class AbstractServiceIT extends AbstractDataIT {

	protected abstract String getFixtureName();
	
	private TXManagerClientSideLauncher txManagerClientSideLauncher;

	private TXManagerServiceSideLauncher txManagerServiceSideLauncher;

	private UserTransaction userTransaction;

	
	public void setUp() throws Exception {
		super.setUp();
		String propertyLocation = System.getProperty("user.dir") + "/../../tx-manager/runtime/properties";
		PropertyManager propertyManager = new PropertyManager();
		propertyManager.setPropertyLocation(propertyLocation);
		propertyManager.initialize();
		getUserTransaction();
		startServices("localhost", 8080);
	}

	public void tearDown() throws Exception {
		userTransaction = null;
		stopServices();
		super.tearDown();
	}
	
	
	protected UserTransaction getUserTransaction() {
		userTransaction = UserTransactionFactory.getUserTransaction();
		return userTransaction;
	}

//	protected TransactionManager getTransactionManager() {
//		return new TransactionManagerImple();
//	}
//
//	protected Transaction getTransaction() throws Exception {
//		Transaction transaction = getTransactionManager().getTransaction();
//		return transaction;
//	}


	protected void startServices(String host, int port) {
		txManagerClientSideLauncher = new TXManagerClientSideLauncher();
		txManagerServiceSideLauncher = new TXManagerServiceSideLauncher();
		txManagerClientSideLauncher.startup(host, port);
		txManagerServiceSideLauncher.startup(host, port);
	}

	protected void stopServices() {
		txManagerClientSideLauncher.shutdown();
		txManagerServiceSideLauncher.shutdown();
	}

	
	protected Transactional createMockTransactional(ServiceStateManager<?> stateManager) {
		return createMockTransactional(stateManager, userTransaction.getTransactionId());
	}
	
	protected Transactional createMockTransactional(ServiceStateManager<?> stateManager, String transactionId) {
		MockTransactional mockTransactional = new MockTransactional(stateManager.getName());
		mockTransactional.setServiceStateManager(stateManager);
		mockTransactional.setTransactonId(transactionId);
		return mockTransactional;
	}
	

	protected void beginUserTransaction() {
		assertNoTransaction();
		try {
			userTransaction.begin();
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
		assertTransactionRunning();
	}

	protected void commitUserTransaction() {
		assertTransactionRunning();
		try {
			userTransaction.commit();
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		} finally {
			ThreadActionData.popAction();
		}
		assertTransactionCommitted();
	}

	protected void rollbackUserTransaction() {
		assertTransactionRunning();
		try {
			userTransaction.rollback();
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		} finally {
			ThreadActionData.popAction();
		}
		assertTransactionAborted();
	}

	
	protected void assertNoTransaction() {
		assertTransactionStatus(ActionStatus.NO_ACTION);
	}

	protected void assertTransactionRunning() {
		assertTransactionStatus(ActionStatus.RUNNING);
	}

	protected void assertTransactionPrepared() {
		assertTransactionStatus(ActionStatus.PREPARED);
	}

	protected void assertTransactionCommitted() {
		assertTransactionStatus(ActionStatus.COMMITTED);
	}

	protected void assertTransactionAborted() {
		assertTransactionStatus(ActionStatus.ABORTED);
	}

	protected void assertTransactionInvalid() {
		assertTransactionStatus(ActionStatus.INVALID);
	}

	protected void assertTransactionStatus(int expectedStatus) {
		Assert.equals(expectedStatus, userTransaction.getStatus());
		printTransactionStatus(userTransaction);
	}
	

	protected void assertPrepared(Transactional transactional) {
		Assert.isInstanceOf(MockTransactional.class, transactional);
		MockTransactional mockTransactional = (MockTransactional) transactional;
		Assert.isTrue(mockTransactional.isPrepared(), "Mock Transactional should have been prepared");
	}
	
	protected void assertCommitted(Transactional transactional) {
		Assert.isInstanceOf(MockTransactional.class, transactional);
		MockTransactional mockTransactional = (MockTransactional) transactional;
		Assert.isTrue(mockTransactional.isPrepared(), "Mock Transactional should have been prepared");
		Assert.isTrue(mockTransactional.isCommitted(), "Mock Transactional should have been committed");
	}

	protected void assertRolledBack(Transactional transactional) {
		Assert.isInstanceOf(MockTransactional.class, transactional);
		MockTransactional mockTransactional = (MockTransactional) transactional;
		Assert.isTrue(mockTransactional.isRolledBack(), "Mock Transactional should have been rolled-back");
	}

	
	protected void printTransactionStatus() {
		printTransactionStatus(userTransaction);
	}

	protected void printTransactionStatus(UserTransaction userTransaction) {
		System.out.println("[Transaction Status] "+userTransaction.getState());
	}

	protected void printStatus(Transaction transaction) throws Exception {
		System.out.println("[Transaction Status] "+TransactionUtil.getStatus(transaction));
	}
	
}
