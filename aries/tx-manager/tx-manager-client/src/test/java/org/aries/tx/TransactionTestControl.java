package org.aries.tx;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.transaction.Status;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.common.TestXADataSource;
import org.aries.util.ExceptionUtil;
import org.aries.util.properties.PropertyManager;

import com.arjuna.ats.arjuna.coordinator.ActionStatus;
import com.arjuna.ats.arjuna.coordinator.BasicAction;
import com.arjuna.ats.internal.arjuna.thread.ThreadActionData;
import com.arjuna.ats.jta.common.jtaPropertyManager;
import common.tx.state.ServiceStateManager;


public class TransactionTestControl {
	
	public static final Log log = LogFactory.getLog(TransactionTestControl.class);

	public static final int DEFAULT_TRANSACTION_TIMEOUT = 300;

	protected TransactionManager transactionManager;

	protected List<TestXADataSource> transactionParticipants;
	
	private org.aries.tx.UserTransaction globalTransaction;

	
	public TransactionTestControl() {
		transactionParticipants = new ArrayList<TestXADataSource>();
	}
	
	public void setUp() throws Exception {
		//super.setUp();
		//TODO fix this location to come from config file
		String propertyLocation = System.getProperty("user.dir") + "/../../../tx-manager/runtime/properties";
		PropertyManager propertyManager = PropertyManager.getInstance();
		propertyManager.setPropertyLocation(propertyLocation);
		propertyManager.initialize();
		getUserTransaction();
		//startServices("localhost", 8080);
	}

	protected String getTransactionId() throws Exception {
		BasicAction currentAction = ThreadActionData.currentAction();
		if (currentAction != null) {
			String transactionId = currentAction.get_uid().toString();
			return transactionId;
		}
		return null;
	}
	
//	protected String getTransactionId() throws Exception {
//		String transactionId = ThreadActionData.currentAction().get_uid().toString();
//		return transactionId;
//	}

	public Transaction getTransaction() throws Exception {
		Transaction transaction = transactionManager.getTransaction();
		//Transaction transaction = getTransaction(getTransactionId());
		return transaction;
	}

//	protected Transaction getTransaction(String transactionId) throws Exception {
//		Transaction transaction = TransactionRegistry.getInstance().getTransaction(transactionId);
//		return transaction;
//	}
	
	public TransactionManager getTransactionManager() {
		return transactionManager;
	}
	
	public void assureTransactionManager() throws Exception {
		Context context = new InitialContext();
		//TransactionManagerDelegate transactionManager = (TransactionManagerDelegate) context.lookup("java:jboss/TransactionManager");
		transactionManager = (TransactionManager) context.lookup("java:jboss/TransactionManager");
		//transactionManager = com.arjuna.ats.jta.TransactionManager.transactionManager();
		jtaPropertyManager.getJTAEnvironmentBean().setSupportSubtransactions(true);
	}
	
	public void setupTransactionManager() throws Exception {
		jtaPropertyManager.getJTAEnvironmentBean().setSupportSubtransactions(true);
		transactionManager = com.arjuna.ats.jta.TransactionManager.transactionManager();
		//transactionManager = TransactionManagerFactory.getTransactionManager();
		//transactionManager = new TransactionManagerImple();
		
        try {
            Context context = new InitialContext();
            context.rebind("javax.transaction.TransactionManager", transactionManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public void tearDown() throws Exception {
		globalTransaction = null;
		clearTransactions();
		clearParticipants();
		closeConnections();
		//stopServices();
		//super.tearDown();
	}

	public void tearDownTransactionManager() throws Exception {
		transactionManager = null;
		clearTransactions();
	}

	public void clearTransaction() throws Exception {
		ThreadActionData.popAction();
		clearParticipants();
	}
	
	public void clearTransactions() throws Exception {
		ThreadActionData.popAction();
		clearParticipants();
	}
	
	public void purgeTransactions() throws Exception {
		ThreadActionData.purgeActions();
		clearParticipants();
	}
	
	public void closeConnections() throws Exception {
		Iterator<TestXADataSource> iterator = transactionParticipants.iterator();
		while (iterator.hasNext()) {
			TestXADataSource dataSource = iterator.next();
			dataSource.close();
		}
	}


	public void addParticipant(TestXADataSource testDataSource) {
		transactionParticipants.add(testDataSource);
	}

	protected void enableParticipants(String transactionId) {
		Iterator<TestXADataSource> iterator = transactionParticipants.iterator();
		while (iterator.hasNext()) {
			TestXADataSource participant = iterator.next();
			participant.setTransactionId(transactionId);
		}
	}

	public void clearParticipants() throws Exception {
		Iterator<TestXADataSource> iterator = transactionParticipants.iterator();
		while (iterator.hasNext()) {
			TestXADataSource participant = iterator.next();
			participant.clear();
		}
	}
	

	public UserTransaction getUserTransaction() {
		UserTransaction userTransaction = com.arjuna.ats.jta.UserTransaction.userTransaction();
		//UserTransaction userTransaction = TransactionUtil.getUserTransaction();
		return userTransaction;
	}

	public org.aries.tx.UserTransaction getGlobalTransaction() {
		globalTransaction = org.aries.tx.UserTransactionFactory.getUserTransaction();
		if (globalTransaction == null)
			System.out.println("BREAK");
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

	
	public Transactional createMockTransactional(ServiceStateManager<?> stateManager) {
		return createMockTransactional(stateManager, globalTransaction.getTransactionId());
	}
	
	public Transactional createMockTransactional(ServiceStateManager<?> stateManager, String transactionId) {
		MockTransactional mockTransactional = new MockTransactional(stateManager.getName());
		mockTransactional.setServiceStateManager(stateManager);
		mockTransactional.setTransactonId(transactionId);
		return mockTransactional;
	}
	

	public String beginTransaction() throws Exception {
		return beginTransaction(DEFAULT_TRANSACTION_TIMEOUT);
	}

	public String beginTransaction(int timeout) throws Exception {
//		BasicAction currentAction = ThreadActionData.currentAction();
		Transaction transaction = transactionManager.getTransaction();
//		if (transaction != null) {
//			//transaction.setRollbackOnly();
//			//transaction.rollback();
//			ThreadActionData.purgeActions();
//		}
		transactionManager.setTransactionTimeout(timeout);
		transactionManager.begin();
		//String transactionId = getTransactionId();
		String transactionId = registerTransaction();
		enableParticipants(transactionId);
		return transactionId;
	}

	public String registerTransaction() throws Exception {
		Transaction transaction = transactionManager.getTransaction();
		String transactionId = registerTransaction(transaction);
		return transactionId;
	}

	public String registerTransaction(Transaction transaction) throws Exception {
		String transactionId = getTransactionId();
		registerTransaction(transaction, transactionId);
		return transactionId;
	}

	public void registerTransaction(Transaction transaction, String transactionId) throws Exception {
		TransactionRegistry.getInstance().registerTransaction(transactionId, transaction);
	}

	public void commitTransaction() throws Exception {
		Transaction transaction = getTransaction();
		if (transaction == null) {
			log.warn("Transaction does not exist");
		} else {
			if (transaction.getStatus() != Status.STATUS_ACTIVE) {
				log.warn("Transaction not in active state");
			} else {
				transaction.commit();
				clearParticipants();
			}
		}
	}
	
	public void rollbackTransaction() throws Exception {
		Transaction transaction = getTransaction();
		if (transaction == null) {
			log.warn("Transaction does not exist");
		} else {
			transaction.rollback();
			clearParticipants();
		}
	}
	

	/*
	 * User transaction methods.
	 */
	
	public String beginUserTransaction() {
		//assertNoAction();
		String transactionId = null;
		try {
			UserTransaction userTransaction = getUserTransaction();
			userTransaction.setTransactionTimeout(DEFAULT_TRANSACTION_TIMEOUT);
			userTransaction.begin();
			int status = userTransaction.getStatus();
			//transactionId = userTransaction.toString();
			transactionId = registerTransaction();
			enableParticipants(transactionId);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
		//assertActionRunning();
		return transactionId;
	}
	
	public void commitUserTransaction() {
		//assertActionRunning();
		try {
			UserTransaction userTransaction = getUserTransaction();
			int status = userTransaction.getStatus();
			userTransaction.commit();
			status = userTransaction.getStatus();
			//System.out.println();
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		} finally {
			//ThreadActionData.popAction();
		}
		//assertActionCommitted();
	}

	public void rollbackUserTransaction() {
		//assertActionRunning();
		try {
			UserTransaction userTransaction = getUserTransaction();
			int status = userTransaction.getStatus();
			userTransaction.rollback();
			status = userTransaction.getStatus();
			//System.out.println();
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		} finally {
			//ThreadActionData.popAction();
		}
		//assertActionAborted();
	}
	
	
	/*
	 * Global transaction methods.
	 */
	
	public String beginGlobalTransaction() {
		assertNoAction();
		String transactionId = null;
		try {
			transactionId = globalTransaction.begin();
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
		assertActionRunning();
		return transactionId;
	}

	public void commitGlobalTransaction() {
		assertActionRunning();
		try {
			globalTransaction.commit();
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		} finally {
			//ThreadActionData.popAction();
		}
		assertActionCommitted();
	}

	public void rollbackGlobalTransaction() {
		assertActionRunning();
		try {
			globalTransaction.rollback();
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		} finally {
			//ThreadActionData.popAction();
		}
		assertActionAborted();
	}

	
	public TransactionContext getTransactionContext() {
		return TransactionContextManager.getInstance().getTransactionContext();
	}
	
	public TransactionContext getTransactionContext(String transactionId) {
		return TransactionContextManager.getInstance().getTransactionContext(transactionId);
	}
	
	
	public void assertNoAction() {
		assertActionStatus(ActionStatus.NO_ACTION);
	}

	public void assertActionRunning() {
		assertActionStatus(ActionStatus.RUNNING);
	}

	public void assertActionPrepared() {
		assertActionStatus(ActionStatus.PREPARED);
	}

	public void assertActionCommitted() {
		assertActionStatus(ActionStatus.COMMITTED);
	}

	public void assertActionAborted() {
		assertActionStatus(ActionStatus.ABORTED);
	}

	public void assertActionInvalid() {
		assertActionStatus(ActionStatus.INVALID);
	}

	public void assertActionStatus(int expectedStatus) {
		org.aries.tx.UserTransaction globalTransaction = getGlobalTransaction();
		Assert.equals(expectedStatus, globalTransaction.getStatus());
		printActionStatus(globalTransaction);
	}
	

	public void assertActive() throws Exception {
		int status = getTransactionManager().getStatus();
		//int status = com.arjuna.ats.jta.TransactionManager.transactionManager().getStatus();
		Assert.equals(Status.STATUS_ACTIVE, status, "Status should be ACTIVE (0): "+status);
	}
	
	public void assertActive(Transactional transactional) {
		Assert.isInstanceOf(MockTransactional.class, transactional);
		MockTransactional mockTransactional = (MockTransactional) transactional;
		Assert.isTrue(mockTransactional.isActive(), "Mock Transactional should have been rolled-back");
	}

	public void assertPrepared(Transactional transactional) {
		Assert.isInstanceOf(MockTransactional.class, transactional);
		MockTransactional mockTransactional = (MockTransactional) transactional;
		Assert.isTrue(mockTransactional.isPrepared(), "Mock Transactional should have been prepared");
	}

	public void assertCommitted() throws Exception {
		int status = getTransactionManager().getStatus();
		//int status = com.arjuna.ats.jta.TransactionManager.transactionManager().getStatus();
		Assert.equals(Status.STATUS_COMMITTED, status, "Status should be COMMITTED (3): "+status);
	}
	
	public void assertCommitted(Transactional transactional) {
		Assert.isInstanceOf(MockTransactional.class, transactional);
		MockTransactional mockTransactional = (MockTransactional) transactional;
		Assert.isTrue(mockTransactional.isPrepared(), "Mock Transactional should have been prepared");
		Assert.isTrue(mockTransactional.isCommitted(), "Mock Transactional should have been committed");
	}

	public void assertRolledBack() throws Exception {
		int status = getTransactionManager().getStatus();
		//int status = com.arjuna.ats.jta.TransactionManager.transactionManager().getStatus();
		Assert.equals(Status.STATUS_ROLLEDBACK, status, "Status should be ROLLEDBACK (4): "+status);
	}
	
	public void assertRolledBack(Transactional transactional) {
		Assert.isInstanceOf(MockTransactional.class, transactional);
		MockTransactional mockTransactional = (MockTransactional) transactional;
		Assert.isTrue(mockTransactional.isRolledBack(), "Mock Transactional should have been rolled-back");
	}

	
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
