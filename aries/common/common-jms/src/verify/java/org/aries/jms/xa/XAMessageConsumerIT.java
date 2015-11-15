package org.aries.jms.xa;

import java.io.Serializable;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.transaction.TransactionManager;

import org.aries.Assert;
import org.aries.jms.JMSEndpointTestFixture;
import org.aries.jms.JMSTestContext;
import org.aries.jms.JmsMessageProducer;
import org.aries.jms.util.MessageUtil;
import org.aries.jta.TransactionManagerFactory;


public class XAMessageConsumerIT extends AbstractJmsXATest {

	private JmsXAMessageConsumer consumer;

	private JmsMessageProducer producer;

	private TransactionManager transactionManager;

	
//    public static Test suite( ) {
//    	TestSuite suite = new ActiveTestSuite( );
//    	suite.addTest(new XAMessageConsumerRuntimeTest("testReceive_Synchronous_WithinTransaction"));
//    	suite.addTest(new XAMessageConsumerRuntimeTest("testReceive_Synchronous_WithoutTransaction"));
//    	suite.addTest(new XAMessageConsumerRuntimeTest("testReceive_Asynchronous"));
//    	return suite;
//    }

   
    public void setUp() throws Exception {
        super.setUp();
        JMSTestContext.expireMessages();
        JMSTestContext.removeMessages();
    	consumer = JmsXAEndpointTestFixture.createXAConsumer(getXASessionAdapter());
    	producer = JMSEndpointTestFixture.createProducer(getSessionAdapter2());
    	transactionManager = TransactionManagerFactory.getTransactionManager();
		consumer.setTransactionManager(transactionManager);
    	consumer.initialize();
    	producer.initialize();
    }
    
	public void tearDown() throws Exception {
		consumer.close();
        producer.close();
        consumer = null;
        producer = null;
        super.tearDown();
    	try {
        	//force termination of any lingering transaction state
    		//transactionManager.rollback();
    	} catch (Throwable e) {
    		//ignore this
    	}
    }

//	protected JmsXAMessageConsumer createConsumer() throws Exception {
//    	JndiContext jndiContext = JMSTestFixture.getJNDIContext();
//		JmsXATransportFactory endpointFactory = new JmsXATransportFactory();
//		JmsXAMessageConsumer consumer = endpointFactory.createXAConsumer(getXASessionAdapter());
//		consumer.setJndiContext(jndiContext);
//		return consumer; 
//    }

    public void testReceive_Synchronous_WithinExternalTransaction_Commit() throws Exception {
    	executeReceiveWithinExternalTransaction("data", "commit");
    	assertNoContext(consumer.getTransactionManager());
    	assertMessageCount(0);
    }

    public void testReceive_Synchronous_WithinExternalTransaction_Rollback() throws Exception {
    	executeReceiveWithinExternalTransaction("data", "rollback");
    	assertNoContext(consumer.getTransactionManager());
    	assertMessageCount(1);
    }
    
//    @Ignore
//    public void testReceive_Synchronous_WithinExternalTransaction_SetRollbackOnly() throws Exception {
//    	executeReceiveWithinExternalTransaction("data", "setRollbackOnly");
//    	TransactionManager transactionManager = consumer.getTransactionManager();
//		assertMarkedRollback(transactionManager);
//    	transactionManager.rollback();
//		assertMessageCount(1);
//    }
    
    public void testReceive_Synchronous_WithoutExternalTransaction() throws Exception {
    	String testData = "data";
    	TransactionManager transactionManager = consumer.getTransactionManager();
		TextMessage message = producer.createTextMessage(testData);
    	producer.send(message);
		assertNoContext(transactionManager);
    	Serializable actualData = consumer.receive(2000);
		assertNoContext(transactionManager);
		Assert.equals(testData, actualData);
    }

    public void testReceive_Asynchronous_WithinExternalTransaction() throws Exception {
    	TransactionManager transactionManager = consumer.getTransactionManager();
		assertNoContext(transactionManager);
		
    	try {
	    	transactionManager.begin();
	    	assertActive(transactionManager);
	    	
	    	final String testData = "data";
	    	consumer.setMessageListener(new MessageListener() {
				public void onMessage(Message message) {
					try {
						Assert.notNull(message);
						Serializable data = MessageUtil.extractPayload(message);
						Assert.notNull(data);
						Assert.equals(testData, data);
				    	consumer.stop();
					} catch (Exception e) {
						fail("Should not happen");
					}
				}
	    	});
	    	consumer.start();

	    	TextMessage message = producer.createTextMessage(testData);
	    	producer.send(message);
	    	consumer.join();
	    	
	    	assertActive(transactionManager);
			transactionManager.commit();
			assertNoContext(transactionManager);
    	} catch (Exception e) {
    		fail(e.getMessage());
    	}
    }
    
	public void testReceive_Asynchronous_WithoutExternalTransaction() throws Exception {
    	final String testData = "data";
    	consumer.setMessageListener(new MessageListener() {
			public void onMessage(Message message) {
				try {
					Assert.notNull(message);
					Serializable data = MessageUtil.extractPayload(message);
					Assert.notNull(data);
					Assert.equals(testData, data);
			    	consumer.stop();
				} catch (Exception e) {
					fail("Should not happen");
				}
			}
    	});
    	consumer.start();

    	TextMessage message = producer.createTextMessage(testData);
    	producer.send(message);
    	consumer.join();
    }
    
    protected void executeReceiveWithinExternalTransaction(String testData, String terminationAction) throws Exception {
    	TransactionManager transactionManager = consumer.getTransactionManager();
		assertNoContext(transactionManager);

		try {
	    	transactionManager.begin();
	    	assertActive(transactionManager);
	    	TextMessage message = producer.createTextMessage(testData);
	    	producer.send(message);
	    	Serializable actualData = consumer.receive(2000);
	    	Assert.equals(testData, actualData);
			executeTransactionTermination(transactionManager, terminationAction);
    	} catch (Exception e) {
			fail(e.getMessage());
    	}
    }
    
}
