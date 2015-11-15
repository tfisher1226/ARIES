package org.aries.jms.xa;

import java.io.Serializable;

import javax.jms.Message;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;

import org.aries.Assert;
import org.aries.jms.AbstractJmsTest;
import org.aries.jms.JMSTestContext;
import org.aries.jms.JmsMessageConsumer;



public class AbstractJmsXATest extends AbstractJmsTest {

	protected JmsXAEndpointTestContext xaContext;
	
	
//	public AbstractXATestCase(String testName){
//		super(testName);
//	}

    public void setUp() throws Exception {
        super.setUp();
    	xaContext = new JmsXAEndpointTestContext();
    	xaContext.setUp();
    }
    
	public void tearDown() throws Exception {
		xaContext.tearDown();
		xaContext = null;
        super.tearDown();
    }
	
	
	public JmsXAConnectionAdapter getXAConnectionAdapter() {
		return xaContext.getXAConnectionAdapter();
	}
	
	public JmsXAConnectionAdapter getXAConnectionAdapter2() {
		return xaContext.getXAConnectionAdapter2();
	}
	
	public JmsXASessionAdapter getXASessionAdapter() {
		return xaContext.getXASessionAdapter();
	}
	
	public JmsXASessionAdapter getXASessionAdapter2() {
		return xaContext.getXASessionAdapter2();
	}
    

    protected void assertActive(TransactionManager transactionManager) throws SystemException {
    	assertActive(transactionManager.getTransaction());
	}

    protected void assertActive(Transaction transaction) throws SystemException {
		Assert.isTrue(transaction.getStatus() == Status.STATUS_ACTIVE, "Transaction not active");
	}

    protected void assertNotActive(TransactionManager transactionManager) throws SystemException {
    	assertNotActive(transactionManager.getTransaction());
	}

    protected void assertNotActive(Transaction transaction) throws SystemException {
		Assert.isTrue(transaction.getStatus() != Status.STATUS_ACTIVE, "Transaction should not be active");
	}

    protected void assertNoContext(TransactionManager transactionManager) throws SystemException {
    	assertNoContext(transactionManager.getTransaction());
	}

    protected void assertNoContext(Transaction transaction) throws SystemException {
		Assert.isNull(transaction, "Transaction context should not exist");
	}

    protected void assertCommitted(TransactionManager transactionManager) throws SystemException {
    	assertCommitted(transactionManager.getTransaction());
	}
    
    protected void assertCommitted(Transaction transaction) throws SystemException {
		Assert.isTrue(transaction.getStatus() == Status.STATUS_COMMITTED, "Transaction not committed");
    }
    
    protected void assertMarkedRollback(TransactionManager transactionManager) throws SystemException {
    	assertMarkedRollback(transactionManager.getTransaction());
	}

    protected void assertMarkedRollback(Transaction transaction) throws SystemException {
		Assert.isTrue(transaction.getStatus() == Status.STATUS_MARKED_ROLLBACK, "Transaction should be marked rollback");
	}

	protected void assertNoMessageReceived() throws Exception {
		assertNoMessageReceived(context.getConsumer());
	}

    protected void assertNoMessageReceived(JmsMessageConsumer consumer) throws Exception {
    	Message message = consumer.receive(1000);
    	Assert.isNull(message);
	}

	protected void assertMessageReceived(String data) throws Exception {
		assertMessageReceived(context.getConsumer(), data);
	}

    protected void assertMessageReceived(JmsMessageConsumer consumer, Serializable expectedMessage) throws Exception {
    	Message actualMessage = consumer.receive();
    	Assert.equals(expectedMessage, actualMessage);
	}
    
    protected void assertMessageCount(Long expectedCount) throws Exception {
    	Long messageCount = JMSTestContext.getMessageCount();
    	Assert.equals(expectedCount, messageCount);
	}

    protected void executeTransactionTermination(TransactionManager transactionManager, String terminationAction) throws Exception {
		assertActive(transactionManager);
    	if (terminationAction.equals("commit"))
    		transactionManager.commit();
    	if (terminationAction.equals("rollback"))
    		transactionManager.rollback();
    	if (terminationAction.equals("setRollbackOnly")) {
    		transactionManager.setRollbackOnly();
    		assertMarkedRollback(transactionManager);
    	} else {
    		assertNoContext(transactionManager);
    	}
    }
    
}
