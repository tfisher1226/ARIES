package org.aries.jms.p2p;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import org.aries.jms.AbstractJmsTest;
import org.aries.jms.p2p.JmsMessageProcessor;
import org.aries.jms.p2p.JmsRequestInvoker;
import org.aries.jms.p2p.JmsRequestService;
import org.aries.util.ThreadUtil;


public abstract class AbstractRequestorIT extends AbstractJmsTest {

	protected JmsRequestInvoker client;

	protected JmsRequestService server;
	
	
    public void setUp() throws Exception {
        super.setUp();
        setupServer();
        setupClient();
    }
    
	public void tearDown() throws Exception {
        closeClient();
        closeServer();
        super.tearDown();
    }
	
	private void setupServer() throws Exception {
    	server = new JmsRequestService(context.getSessionAdapter2());
    	server.setConsumerDescripter(createConsumerDescripter("queueA"));
    	server.setProducerDescripter(createProducerDescripter("queueB"));
    	server.initialize();
	}

    private void setupClient() throws Exception {
    	client = new JmsRequestInvoker(context.getSessionAdapter());
		client.setProducerDescripter(createProducerDescripter("queueA"));
		client.setConsumerDescripter(createConsumerDescripter("queueB"));
    	client.initialize();
	}

    private void closeServer() throws Exception {
		server.close();
		server = null;
	}
    
    private void closeClient() throws Exception {
		client.close();
		client = null;
	}

	protected JmsMessageProcessor createEchoReturningProcessor() {
		return new JmsMessageProcessor() {
			public Message process(Message message) throws JMSException {
				//Assert.notNull(data);
				//Assert.equals("request", data);
				ObjectMessage request = (ObjectMessage) message;
				ObjectMessage response = server.createObjectMessage(request.getObject());
				ThreadUtil.sleep(500);
				return response;
			}
    	};
	}

	protected JmsMessageProcessor createTimeConsumingProcessor(final long milliseconds) {
		return new JmsMessageProcessor() {
			public Message process(Message message) throws JMSException {
				try {
					Thread.sleep(milliseconds);
				} catch (InterruptedException e) {
					//ignore
				}
				Message response = server.createObjectMessage();
				return response;
			}
    	};
	}

	protected JmsMessageProcessor createExceptionThrowingProcessor(final String text) {
		return new JmsMessageProcessor() {
			public Message process(Message message) throws JMSException {
				throw new JMSException(text);
			}
    	};
	}
    
}
