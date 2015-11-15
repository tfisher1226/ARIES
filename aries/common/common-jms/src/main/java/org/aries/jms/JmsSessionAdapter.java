package org.aries.jms;

import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.NamingException;

import org.aries.AbstractAdapter;
import org.aries.AdapterListener;
import org.aries.Assert;
import org.aries.jms.config.JmsConsumerDescripter;
import org.aries.jms.config.JmsProducerDescripter;
import org.aries.jms.config.JmsSessionDescripter;
import org.aries.transport.TransferMode;


public class JmsSessionAdapter extends AbstractAdapter {

	private static final String MBEAN_NAME = "Messaging:name=SessionAdapter";
	
	protected JmsSessionDescripter sessionDescripter;

	protected JmsConnectionAdapter connectionAdapter;
	
	protected AdapterListener connectionListener;

	protected TransferMode transferMode;
	
	protected Session session;

	protected Object mutex;
	

	public JmsSessionAdapter() {
		this(new JmsSessionDescripter());
	}
	
	public JmsSessionAdapter(JmsSessionDescripter sessionDescripter) {
		this.sessionDescripter = sessionDescripter;
		construct();
	}
	
	private void construct() {
		setJndiContext(sessionDescripter.getJndiContext());
		connectionListener = createConnectionListener();
		mutex = new Object();
	}

    public String getMBeanName() {
    	return MBEAN_NAME;
    }
    
	public Session getSession() {
		return session;
	}
	
	protected void setSession(Session value) {
		session = value;
	}
	
	public JmsSessionDescripter getDescripter() {
        return sessionDescripter;
    }

	public void setDescripter(JmsSessionDescripter value) {
        sessionDescripter = value;
    }

	public JmsConnectionAdapter getConnectionAdapter() {
        return connectionAdapter;
    }

    public void setConnectionAdapter(JmsConnectionAdapter value) {
    	connectionAdapter = value;
    	setJndiContext(value.getJndiContext());
    }

	public TransferMode getTransferMode() {
		return transferMode;
	}
	
	public void setTransferMode(TransferMode transferMode) {
		this.transferMode = transferMode;
	}

	public void setTransferMode(String transferMode) {
		this.transferMode = TransferMode.valueOf(transferMode);
	}

    
	protected AdapterListener createConnectionListener() {
		return new AdapterListener() {
			@Override
			public void closed() {
				//nothing by default
			}

			@Override
			public void initialized() {
				//nothing by default
			}

			@Override
			public void reset() {
			    try {
			    	if (!isClosing())
			    		eventMulticaster.reset();
			    } catch (Throwable e) {
			    	log.error(e);
			    }
			}
		};
	}
	

    public void initialize() throws JMSException {
    	synchronized (mutex) {
    		if (isInitialized())
    			throw new JMSException("Already initialized");
	        try {
	        	assureConnection();
	        	assureSession();
	        	setClosed(false);
	        	setInitialized(true);
	        	log.info("Initialized: "+this);
	        } catch (Throwable e) {
	        	log.error(e);
	        	throw new JMSException("Unexpected problem: "+e.getMessage());
	        }
	    }
    }
	
	protected void assertInitialized() throws Exception {
		if (connectionAdapter == null || session == null || !isInitialized())
			throw new Exception("Not initialized");
	}

	protected void assertStarted() throws Exception {
		if (connectionAdapter == null || session == null || !isStarted())
			throw new Exception("Not started");
	}

    protected void assureConnection() throws Exception {
    	connectionAdapter.addAdapterListener(connectionListener);
    	if (!connectionAdapter.isInitialized())
        	connectionAdapter.initialize();
	}
    
    protected void assureSession() throws JMSException {
    	Connection connection = connectionAdapter.getConnection();
    	Assert.isInstanceOf(Connection.class, connection);
    	boolean transacted = sessionDescripter.isTransacted();
    	int acknowledgeMode = sessionDescripter.getAcknowledgeMode();
    	session = connection.createSession(transacted, acknowledgeMode);
    	log.info("Created session: "+sessionDescripter.toString());
    	eventMulticaster.initialized();
    }


	public boolean isValid() {
		return isInitialized() && isStarted() && !isClosed() && !isClosing();
	}

	public void invalidate() {
		setInitialized(false);
	}

	public boolean isStarted() {
		return connectionAdapter != null && connectionAdapter.isStarted();
	}

	public boolean isInitialized() {
		return connectionAdapter != null && connectionAdapter.isInitialized() && super.isInitialized();
	}

	public boolean isClosing() {
		return super.isClosing() || connectionAdapter.isClosing();
	}

	public boolean isClosed() {
		return super.isClosed() && connectionAdapter.isClosed();
	}
	
	public void start() throws JMSException {
		Assert.notNull(connectionAdapter, "Connection not found");
		connectionAdapter.start();
	}

	public void stop() throws Exception {
		Assert.notNull(connectionAdapter, "Connection not found");
		connectionAdapter.stop();
	}

	public void reset() throws JMSException {
    	synchronized (mutex) {
    		close();
			//if (connectionAdapter != null && connectionAdapter.isClosed()) {
			if (connectionAdapter != null)
				//TODO change this to assureReset()
				connectionAdapter.reset();
			if (!isValid())
				initialize();
    	}
	}
	
	public void close() throws JMSException {
    	synchronized (mutex) {
    		try {
    			setClosing(true);
    			closeSession();
    			if (connectionAdapter != null)
    				//TODO change this to assureClosed()
    				connectionAdapter.close();
    		} catch (Throwable e) {
    			log.debug("Error releasing session", e);
    		} finally {
    			setClosing(false);
    			setClosed(true);
    			setInitialized(false);
    		}
	    }
	}

	protected void closeSession() throws Exception {
    	synchronized (mutex) {
    		try {
    			if (session != null) {
    				session.close();
    				session = null;
    				eventMulticaster.closed();
    			}
    		} catch (Throwable e) {
    			log.debug("Error releasing session", e);
    		}
	    }
	}


    public void commit() throws Exception {
    	synchronized (mutex) {
    		assertStarted();
    		try {
	            if (session.getTransacted()) {
	                session.commit();
	            }
	        } catch (JMSException e) {
	            log.error(e);
	            throw new Exception("Problem with commit(): "+e.getMessage());
	        }
	    }
    }
    
    public void rollback() throws Exception {
    	synchronized (mutex) {
    		assertStarted();
	        try {
	            if (session.getTransacted()) {
	                session.rollback();
	            }
	        } catch (JMSException e) {
	            log.error(e);
	            throw new Exception("Problem with rollback(): "+e.getMessage());
	        }
	    }
    }    

    
	public MessageProducer createProducer(JmsProducerDescripter producerDescripter) throws NamingException, JMSException {
		Destination destination = lookupDestination(producerDescripter.getDestinationName());
		MessageProducer messageProducer = createProducer(producerDescripter, destination);
		return messageProducer;
	}

	public MessageProducer createProducer(JmsProducerDescripter producerDescripter, Destination destination) throws JMSException {
		MessageProducer messageProducer = JmsMessageEndpointFactory.createMessageProducer(session, producerDescripter, destination);
		return messageProducer;
	}

	public MessageConsumer createConsumer(JmsConsumerDescripter Descripter) throws NamingException, JMSException {
		Destination destination = lookupDestination(Descripter.getDestinationName());
		MessageConsumer messageConsumer = createConsumer(Descripter, destination);
		return messageConsumer;
	}

	public MessageConsumer createConsumer(JmsConsumerDescripter Descripter, Destination destination) throws JMSException {
		MessageConsumer messageConsumer = JmsMessageEndpointFactory.createMessageConsumer(session, Descripter, destination);
		return messageConsumer;
	}

    protected Destination lookupDestination(String destinationName) throws NamingException {
        return (Destination) getJndiContext().lookupObject(destinationName);
    }
    
	
    public TextMessage createTextMessage(String data) throws JMSException {
    	if (session == null)
    		reset();
    	Assert.notNull(session);
    	TextMessage message = null;
    	try {
    		message = session.createTextMessage();
    	} catch (IllegalStateException e) {
    		log.error(e.getMessage());
    		//closing
    	} catch (Exception e) {
    		log.error(e);
    	}
    	if (message == null) {
    		reset();
    		message = session.createTextMessage();
    	}
    	message.setText(data);
    	return message;
    }
    
	public ObjectMessage createObjectMessage(Serializable data) throws JMSException {
    	if (session == null)
    		reset();
    	Assert.notNull(session);
    	ObjectMessage message = null;
    	try {
    		message = session.createObjectMessage();
    	} catch (IllegalStateException e) {
    		log.error(e.getMessage());
    		//closing
    	} catch (Exception e) {
    		log.error(e);
    	}
    	if (message == null) {
    		reset();
    		message = session.createObjectMessage();
    	}
    	message.setObject(data);
    	return message;
    }

	public Destination createReplyTo() throws JMSException {
    	if (session == null)
    		reset();
    	Assert.notNull(session);
		Destination replyto = null;
		try {
			replyto = session.createTemporaryQueue();
		} catch (Throwable e) {
    		log.error(e.getMessage());
		}
    	if (replyto == null) {
    		reset();
			replyto = session.createTemporaryQueue();
    	}
		return replyto;
	}

	public TextMessage createTextMessage() throws JMSException {
    	Assert.notNull(session, "Session must exist");
    	return session.createTextMessage();
    }

	public ObjectMessage createObjectMessage() throws JMSException {
    	Assert.notNull(session, "Session must exist");
		return session.createObjectMessage();
	}

	public MapMessage createMapMessage() throws JMSException {
    	Assert.notNull(session, "Session must exist");
    	return session.createMapMessage();
    }

    public String toString() {
		return sessionDescripter.toString();     	
    }

}

