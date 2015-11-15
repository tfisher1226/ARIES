package org.aries.tx;

import javax.transaction.Transaction;
import javax.transaction.UserTransaction;

import org.aries.util.properties.PropertyManager;

import common.tx.management.TXManagerClientSideLauncher;
import common.tx.management.TXManagerServiceSideLauncher;
import common.tx.state.ServiceStateManager;


public class DataModuleTestControl extends DataLayerTestControl {

	private TXManagerClientSideLauncher txManagerClientSideLauncher;

	private TXManagerServiceSideLauncher txManagerServiceSideLauncher;

	private org.aries.tx.UserTransaction globalTransaction;

	
	public DataModuleTestControl(TransactionTestControl transactionTestControl) {
		super(transactionTestControl);
	}
	
	public void setUp() throws Exception {
		super.setUp();
		//TODO fix this location to come from config file
		String propertyLocation = System.getProperty("user.dir") + "/../../../tx-manager/runtime/properties";
		PropertyManager propertyManager = PropertyManager.getInstance();
		propertyManager.setPropertyLocation(propertyLocation);
		propertyManager.initialize();
		getUserTransaction();
		//startServices("localhost", 8080);
	}

	public void tearDown() throws Exception {
		globalTransaction = null;
		//stopServices();
		super.tearDown();
	}
	
	public UserTransaction getUserTransaction() {
		UserTransaction userTransaction = com.arjuna.ats.jta.UserTransaction.userTransaction();
		return userTransaction;
	}

	public org.aries.tx.UserTransaction getGlobalTransaction() {
		globalTransaction = org.aries.tx.UserTransactionFactory.getUserTransaction();
		return globalTransaction;
	}

//	public TransactionManager getTransactionManager() {
//		return new TransactionManagerImple();
//	}
//
//	public Transaction getTransaction() throws Exception {
//		Transaction transaction = getTransactionManager().getTransaction();
//		return transaction;
//	}


	public void startServices() throws Exception {
		startServices("localhost", 8080);
	}
	
	public void startServices(String host, int port) throws Exception {
		startClientSide(host, port);
		startServerSide(host, port);
	}

	public void startClientSide() throws Exception {
		startClientSide("localhost", 8080);
	}

	public void startClientSide(String host, int port) throws Exception {
		txManagerClientSideLauncher = new TXManagerClientSideLauncher();
		txManagerClientSideLauncher.startup(host, port);
	}

	public void startServerSide() throws Exception {
		startServerSide("localhost", 8080);
	}
	
	public void startServerSide(String host, int port) throws Exception {
		txManagerServiceSideLauncher = new TXManagerServiceSideLauncher();
		txManagerServiceSideLauncher.startup(host, port);
	}

	public void stopServices() throws Exception {
		stopClientSide();
		stopServerSide();
	}
	
	public void stopClientSide() throws Exception {
		txManagerClientSideLauncher.shutdown();
	}
	
	public void stopServerSide() throws Exception {
		txManagerServiceSideLauncher.shutdown();
	}
	
	

	
	public Transactional createMockTransactional(ServiceStateManager<?> stateManager) {
		return createMockTransactional(stateManager, globalTransaction.getTransactionId());
	}
	
	public Transactional createMockTransactional(ServiceStateManager<?> stateManager, String transactionId) {
		MockTransactional mockTransactional = new MockTransactional(stateManager.getName());
		mockTransactional.setServiceStateManager(stateManager);
		mockTransactional.setTransactonId(transactionId);
		return mockTransactional;
	}
	
	
//	/*
//	 * User transaction methods.
//	 */
//	
//	public String beginUserTransaction() {
//		//assertNoAction();
//		String transactionId = null;
//		try {
//			UserTransaction userTransaction = getUserTransaction();
//			userTransaction.setTransactionTimeout(TransactionTestControl.DEFAULT_TRANSACTION_TIMEOUT);
//			userTransaction.begin();
//			int status = userTransaction.getStatus();
//			transactionId = userTransaction.toString();
//		} catch (Exception e) {
//			throw ExceptionUtil.rewrap(e);
//		}
//		//assertActionRunning();
//		return transactionId;
//	}
//	
//	public void commitUserTransaction() {
//		//assertActionRunning();
//		try {
//			UserTransaction userTransaction = getUserTransaction();
//			int status = userTransaction.getStatus();
//			userTransaction.commit();
//			status = userTransaction.getStatus();
//			System.out.println();
//		} catch (Exception e) {$
//			throw ExceptionUtil.rewrap(e);
//		} finally {
//			//ThreadActionData.popAction();
//		}
//		//assertActionCommitted();
//	}
//
//	public void rollbackUserTransaction() {
//		//assertActionRunning();
//		try {
//			UserTransaction userTransaction = getUserTransaction();
//			int status = userTransaction.getStatus();
//			userTransaction.rollback();
//			status = userTransaction.getStatus();
//			System.out.println();
//		} catch (Exception e) {
//			throw ExceptionUtil.rewrap(e);
//		} finally {
//			//ThreadActionData.popAction();
//		}
//		//assertActionAborted();
//	}
	
	
//	/*
//	 * Global transaction methods.
//	 */
//	
//	public String beginGlobalTransaction() {
//		assertNoAction();
//		String transactionId = null;
//		try {
//			transactionId = globalTransaction.begin();
//		} catch (Exception e) {
//			throw ExceptionUtil.rewrap(e);
//		}
//		assertActionRunning();
//		return transactionId;
//	}
//
//	public void commitGlobalTransaction() {
//		assertActionRunning();
//		try {
//			globalTransaction.commit();
//		} catch (Exception e) {
//			throw ExceptionUtil.rewrap(e);
//		} finally {
//			//ThreadActionData.popAction();
//		}
//		assertActionCommitted();
//	}
//
//	public void rollbackGlobalTransaction() {
//		assertActionRunning();
//		try {
//			globalTransaction.rollback();
//		} catch (Exception e) {
//			throw ExceptionUtil.rewrap(e);
//		} finally {
//			//ThreadActionData.popAction();
//		}
//		assertActionAborted();
//	}
//
//	
//	public TransactionContext getTransactionContext() {
//		return TransactionContextManager.getInstance().getTransactionContext();
//	}
//	
//	public TransactionContext getTransactionContext(String transactionId) {
//		return TransactionContextManager.getInstance().getTransactionContext(transactionId);
//	}
//	
//	
//	public void assertNoAction() {
//		assertActionStatus(ActionStatus.NO_ACTION);
//	}
//
//	public void assertActionRunning() {
//		assertActionStatus(ActionStatus.RUNNING);
//	}
//
//	public void assertActionPrepared() {
//		assertActionStatus(ActionStatus.PREPARED);
//	}
//
//	public void assertActionCommitted() {
//		assertActionStatus(ActionStatus.COMMITTED);
//	}
//
//	public void assertActionAborted() {
//		assertActionStatus(ActionStatus.ABORTED);
//	}
//
//	public void assertActionInvalid() {
//		assertActionStatus(ActionStatus.INVALID);
//	}
//
//	public void assertActionStatus(int expectedStatus) {
//		org.aries.tx.UserTransaction globalTransaction = getGlobalTransaction();
//		Assert.equals(expectedStatus, globalTransaction.getStatus());
//		printActionStatus(globalTransaction);
//	}
//	
//
//	public void assertActive() throws Exception {
//		int status = transactionControl.getTransactionManager().getStatus();
//		Assert.equals(Status.STATUS_ACTIVE, status, "Status should be ACTIVE (0): "+status);
//	}
//	
//	public void assertActive(Transactional transactional) {
//		Assert.isInstanceOf(MockTransactional.class, transactional);
//		MockTransactional mockTransactional = (MockTransactional) transactional;
//		Assert.isTrue(mockTransactional.isActive(), "Mock Transactional should have been rolled-back");
//	}
//
//	public void assertPrepared(Transactional transactional) {
//		Assert.isInstanceOf(MockTransactional.class, transactional);
//		MockTransactional mockTransactional = (MockTransactional) transactional;
//		Assert.isTrue(mockTransactional.isPrepared(), "Mock Transactional should have been prepared");
//	}
//
//	public void assertCommitted() throws Exception {
//		int status = transactionControl.getTransactionManager().getStatus();
//		Assert.equals(Status.STATUS_COMMITTED, status, "Status should be COMMITTED (3): "+status);
//	}
//	
//	public void assertCommitted(Transactional transactional) {
//		Assert.isInstanceOf(MockTransactional.class, transactional);
//		MockTransactional mockTransactional = (MockTransactional) transactional;
//		Assert.isTrue(mockTransactional.isPrepared(), "Mock Transactional should have been prepared");
//		Assert.isTrue(mockTransactional.isCommitted(), "Mock Transactional should have been committed");
//	}
//
//	public void assertRolledBack() throws Exception {
//		int status = transactionControl.getTransactionManager().getStatus();
//		Assert.equals(Status.STATUS_ROLLEDBACK, status, "Status should be ROLLEDBACK (4): "+status);
//	}
//	
//	public void assertRolledBack(Transactional transactional) {
//		Assert.isInstanceOf(MockTransactional.class, transactional);
//		MockTransactional mockTransactional = (MockTransactional) transactional;
//		Assert.isTrue(mockTransactional.isRolledBack(), "Mock Transactional should have been rolled-back");
//	}

	
	public void printActionStatus() {
		printActionStatus(globalTransaction);
	}

	public void printActionStatus(org.aries.tx.UserTransaction userTransaction) {
		System.out.println("[Transaction Status] "+userTransaction.getState());
	}

	public void printTransactionStatus(Transaction transaction) throws Exception {
		System.out.println("[Transaction Status] "+TransactionUtil.getStatus(transaction));
	}

}
