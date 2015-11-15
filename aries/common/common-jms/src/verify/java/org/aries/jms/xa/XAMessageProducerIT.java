package org.aries.jms.xa;

import javax.jms.Message;
import javax.jms.TextMessage;
import javax.transaction.TransactionManager;

import org.aries.Assert;
import org.aries.jms.JMSEndpointTestFixture;
import org.aries.jms.JMSTestContext;
import org.aries.jms.JmsMessageConsumer;
import org.aries.jta.TransactionManagerFactory;


public class XAMessageProducerIT extends AbstractJmsXATest {

	private JmsXAMessageProducer producer;

	private JmsMessageConsumer consumer;


    public void setUp() throws Exception {
        super.setUp();
        JMSTestContext.expireMessages();
        JMSTestContext.removeMessages();
    	producer = JmsXAEndpointTestFixture.createXAProducer(getXASessionAdapter());
    	producer.setTransactionManager(TransactionManagerFactory.getTransactionManager());
    	consumer = JMSEndpointTestFixture.createConsumer(getSessionAdapter2());
    	producer.initialize();
    	consumer.initialize();
    }
    
	public void tearDown() throws Exception {
		consumer.close();
        producer.close();
        consumer = null;
        producer = null;
        super.tearDown();
    }

    public void testSend_WithoutExternalTransaction() throws Exception {
    	TransactionManager transactionManager = producer.getTransactionManager();
		assertNoContext(transactionManager);
    	String testData = "data";
		TextMessage testMessage = getXASessionAdapter().createTextMessage(testData);
    	producer.sendMessage(testMessage);
		assertNoContext(transactionManager);
    	Message actualMessage = consumer.receive(2000);
		assertNoContext(transactionManager);
		Assert.equals(testMessage, actualMessage);
    }

    public void testSend_WithinExternalTransaction_Commit() throws Exception {
    	String testData = "data";
    	executeWithinExternalTransaction(testData, "commit");
    	assertNoContext(producer.getTransactionManager());
    	Message actualData = consumer.receive(2000);
    	Assert.equals(testData, actualData);
    }
 
    public void testSend_WithinExternalTransaction_Rollback() throws Exception {
    	executeWithinExternalTransaction("data", "rollback");
    	assertNoContext(producer.getTransactionManager());
    	assertMessageCount(0);
    }

	public void testSend_WithinExternalTransaction_SetRollbackOnly() throws Exception {
		String testData = "data";
		executeWithinExternalTransaction(testData, "setRollbackOnly");
		TransactionManager transactionManager = producer.getTransactionManager();
		assertMarkedRollback(transactionManager);
		transactionManager.rollback();
		assertMessageCount(0);
    }

    public void executeWithinExternalTransaction(String testData, String terminationAction) throws Exception {
    	TransactionManager transactionManager = producer.getTransactionManager();
		assertNoContext(transactionManager);

		try {
	    	transactionManager.begin();
	    	assertActive(transactionManager);
	    	
			TextMessage message = getXASessionAdapter().createTextMessage(testData);
	    	producer.sendMessage(message);
	    	assertActive(transactionManager);
	    	if (terminationAction.equals("commit"))
	    		transactionManager.commit();
	    	if (terminationAction.equals("rollback"))
	    		transactionManager.rollback();
	    	if (terminationAction.equals("setRollbackOnly"))
	    		transactionManager.setRollbackOnly();
		} catch (Exception e) {
			fail(e.getMessage());
    	}
    }
    
}
