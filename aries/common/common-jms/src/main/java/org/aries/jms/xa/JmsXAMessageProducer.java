package org.aries.jms.xa;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.naming.NamingException;

import org.aries.Assert;
import org.aries.jms.JmsMessageProducer;
import org.aries.jms.config.JmsProducerDescripter;


public class JmsXAMessageProducer extends AbstractXAEndpoint {
    
	private static final String MBEAN_NAME = "MessageEndpoint:name=XAProducer";
	
	protected JmsMessageProducer messageProducer;

	private boolean autoRecoveryEnabled; 
	
	
	public JmsXAMessageProducer(JmsXASessionAdapter sessionAdapter, JmsProducerDescripter specification) {
		super(sessionAdapter, specification);
	}

    public String getMBeanName() {
    	return MBEAN_NAME;
    }

	public JmsProducerDescripter getDescripter() {
		return (JmsProducerDescripter) endpointDescripter;
	}
	
	public boolean isAutoRecoveryEnabled() {
		return autoRecoveryEnabled;
	}

	public void setAutoRecoveryEnabled(boolean value) {
		autoRecoveryEnabled = value;
	}
	
	
	@Override
	protected void assureEndpoint() throws JMSException {
		messageProducer = new JmsMessageProducer(getXASessionAdapter(), getDescripter());
		//_producer.initialize();
	}

	
	public void initialize() throws NamingException, JMSException {
		synchronized (mutex) {
			super.initialize();
			messageProducer.initialize();
		}
	}
	
	public void validate() throws NamingException, JMSException {
		Assert.notNull(messageProducer);
		//Assert.isNotNull(_transactor);
		messageProducer.validate();
	}

	public void stop() throws JMSException {
		synchronized (mutex) {
			if (_thread != null) {
				_thread.interrupt();
				setStopped(false);
			}
		}
	}

    public void close() throws JMSException {
    	synchronized (mutex) {
    		if (messageProducer != null) {
    			messageProducer.close();
    			messageProducer = null;
    		}
    		super.close();
    	}
    }


	//does not return until published
    public void sendMessage(Message message) throws Exception {
    	sendMessage(message, null);
    }
    
	//does not return until published
	public void sendMessage(Message message, Destination destination) throws Exception {
		boolean localTransactionStarted = assureTransactionStarted();
		
		//TODO need this lock?
		synchronized (mutex) {
			validate();
			if (!isValid()) {
				processTransactionFailure(localTransactionStarted);
				return;
			}
			
			Exception exception = null;
			
			try {
				//does not return until published
	            if (log.isTraceEnabled())
	                log.trace("Sending message");

	            if (destination == null)
	            	messageProducer.send(message);
	            else messageProducer.send(message, destination);

	            if (log.isTraceEnabled())
	                log.trace("Sent message");
	            
			} catch (Exception e) {
				exception = e;
				if (isAutoRecoveryEnabled())
					process(e);
			}
			
			if (exception != null) {
				processTransactionFailure(localTransactionStarted);
				//TODO convert this to JMSException?
				//throw convert(exception);
				throw exception;
			}
			
			assureTransactionComplete(localTransactionStarted);
		}
    }

}
