package org.aries.jms.p2p;

import java.util.concurrent.TimeoutException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import org.aries.Assert;
import org.aries.jms.JMSTestContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class JmsRequestInvokerIT extends AbstractRequestorIT {
	
    @Before
    public void setUp() throws Exception {
        super.setUp();
        JMSTestContext.expireMessages("Queue", "queueA");
        JMSTestContext.expireMessages("Queue", "queueB");
        JMSTestContext.removeMessages("Queue", "queueA");
        JMSTestContext.removeMessages("Queue", "queueB");
    }
    
    @After
	public void tearDown() throws Exception {
        super.tearDown();
    }
	
    @Test
    public void testInvoke() throws Exception {
    	server.setMessageProcessor(createEchoReturningProcessor());
    	assureInvoke("data");
    }

    @Test
    public void testInvoke_Timeout() throws Exception {
    	server.setMessageProcessor(createTimeConsumingProcessor(1000));
    	try {
    		client.setReceiveWaitTime(500);
    		Message request = client.createObjectMessage("data");
        	client.invoke(request);
    		fail("Exception should be thrown");
    	} catch (Throwable e) {
    		Assert.isInstanceOf(TimeoutException.class, e);
    	}
    }

	@Test
    public void testInvoke_Exception() throws Exception {
    	final String message = "test exception";
    	server.setMessageProcessor(createExceptionThrowingProcessor(message));
    	try {
    		Message request = client.createObjectMessage("data");
        	client.invoke(request);
    		fail("Exception should be thrown");
    	} catch (Throwable e) {
    		Assert.equals(e.getMessage(), message);
    	}
    }

    @Test
    @Ignore
    public void testInvoke_ConnectionClosed() throws Exception {
    	server.setMessageProcessor(createEchoReturningProcessor());
    	String payload = "data";
    	getConnectionAdapter().close();
		Message request = client.createObjectMessage(payload);
    	Message response = client.invoke(request);
    	Assert.notNull(response, "Response should exist");
		Assert.isTrue(response instanceof ObjectMessage);
		ObjectMessage objectMessage = (ObjectMessage) response;
		Assert.equals(payload, objectMessage.getObject());
    }

    @Test
    @Ignore
    public void testInvoke_SessionClosed() throws Exception {
    	String payload = "data";
    	server.setMessageProcessor(createEchoReturningProcessor());
		Message request = client.createObjectMessage(payload);
    	getSessionAdapter().close();
    	Message response = client.invoke(request);
    	Assert.notNull(response, "Response should exist");
		Assert.isTrue(response instanceof ObjectMessage);
		ObjectMessage objectMessage = (ObjectMessage) response;
		Assert.equals(payload, objectMessage.getObject());
    }

    @Test
    public void testInvoke_MultipleConcurrentTimes() throws Exception {
    	server.setMessageProcessor(createEchoReturningProcessor());
    	assureInvoke("data1");
    	assureInvoke("data2");
    	assureInvoke("data3");
    }

	protected void assureInvoke(String payload) throws Exception, JMSException {
		Message request = client.createObjectMessage(payload);
    	Message response = client.invoke(request);
    	Assert.notNull(response, "Response should exist");
		Assert.isTrue(response instanceof ObjectMessage);
		ObjectMessage objectMessage = (ObjectMessage) response;
		Assert.equals(payload, objectMessage.getObject());
	}

}
