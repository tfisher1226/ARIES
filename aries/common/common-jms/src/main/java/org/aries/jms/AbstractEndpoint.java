package org.aries.jms;

import java.io.Serializable;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.NamingException;

import org.aries.AbstractAdapter;
import org.aries.AdapterListener;
import org.aries.Assert;
import org.aries.jms.config.JmsEndpointDescripter;
import org.aries.jms.util.ExceptionUtil;
import org.aries.jndi.JndiContext;


public abstract class AbstractEndpoint extends AbstractAdapter {

	protected JmsSessionAdapter sessionAdapter;
	
	protected AdapterListener sessionListener;

	protected JmsEndpointDescripter endpointDescripter;

	protected Destination destination;

	protected Destination replyto;

	protected boolean forceResetRequested; 

	protected boolean forceStopRequested; 

	protected Object mutex;


    protected abstract void assureEndpoint() throws JMSException, NamingException;
    

	public AbstractEndpoint() {
	}
	
	public AbstractEndpoint(JmsSessionAdapter sessionAdapter, JmsEndpointDescripter descriptor) {
    	Assert.notNull(descriptor, "Descripter must be specified");
    	Assert.notNull(sessionAdapter, "Session-adapter must be specified");
		this.sessionAdapter = sessionAdapter;
		construct(descriptor);
	}

	private void construct(JmsEndpointDescripter specification) {
		setJndiContext(specification.getJndiContext());
		setDestination(specification.getDestination());
		setReplyto(specification.getReplyto());
		endpointDescripter = specification;
		construct();
	}
	
	private void construct() {
		sessionListener = createSessionListener();
        mutex = new Object();
	}

    public Session getSession() {
        return sessionAdapter.getSession();
    }
    
    public JmsSessionAdapter getSessionAdapter() {
        return sessionAdapter;
    }

//    public void setSessionAdapter(JmsSessionAdapter value) {
//    	if (value != null)
//    		_jndiContext = value.getJndiContext();
//    	_sessionAdapter = value;
//    }

    public JndiContext getJndiContext() {
        return getSessionAdapter().getJndiContext();
    }
    
    
	public boolean isInitialized() {
		return getSessionAdapter() != null && getSessionAdapter().isInitialized() && super.isInitialized();
	}

//	public boolean isStarted() {
//		return getConnectionAdapter() != null && getConnectionAdapter().isStarted();
//	}
	
	public boolean isClosing() {
		return super.isClosing() || (getSessionAdapter() != null && getSessionAdapter().isClosing()); 
	}

	public boolean isClosed() {
		return super.isClosed() || (getSessionAdapter() != null && getSessionAdapter().isClosed()); 
	}

    
	public void start() throws Exception {
		throw new UnsupportedOperationException();
	}
	
	public void stop() throws Exception {
		throw new UnsupportedOperationException();
	}

	
	protected AdapterListener createSessionListener() {
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
			    	if (!isClosing()) {
			    		close();
			    		initialize();
			    	}
			    } catch (Throwable e) {
			    	log.error(e);
			    }
			}
		};
	}

    public void initialize() throws NamingException, JMSException {
    	synchronized (mutex) {
	        while (!Thread.currentThread().isInterrupted() && !isClosing() /*&& !isClosed()*/) {
		        try {
		    		assureSession();
		    		assureDestination();
		        	assureEndpoint();
		        	setClosed(false);
		        	setInitialized(true);
		        	break;
		                
		        } catch (Exception e) {
		        	log.error(e);
		        	//TODO should we throw JMSException here?
		        	throw new JMSException("Unexpected problem: "+e.getMessage());
		        }
		    }
	    }
    }

	protected void assertInitialized() throws JMSException {
		if (!isInitialized())
			//TODO should we throw JMSException here?
			throw new JMSException("Not initialized");
	}

	protected void assertStarted() throws JMSException {
		if (!isStarted())
			//TODO should we throw JMSException here?
			throw new JMSException("Not started");
	}

    protected void assureSession() throws JMSException {
		sessionAdapter.addAdapterListener(sessionListener);
    	if (!sessionAdapter.isInitialized()) {
    		sessionAdapter.initialize();
    	}
	}

	public Destination getDestination() {
		return destination;
	}

	public void setDestination(Destination value) {
		destination = value;
		invalidate();
	}

    protected void assureDestination() throws JMSException, NamingException {
    	String destinationName = endpointDescripter.getDestinationName();
    	if (destination == null && destinationName != null)
    		destination = createDestination();
    }

    protected Destination createDestination() throws JMSException, NamingException {
    	String destinationName = endpointDescripter.getDestinationName();
    	Assert.notNull(destinationName, "Destination-name must be specified");
    	Destination destination = lookupDestination(destinationName);
    	return destination;
    }

    protected Destination lookupDestination(String destinationName) throws NamingException {
        return (Destination) getJndiContext().lookupObject(destinationName);
    }

	public Destination getReplyto() {
		return replyto;
	}

	public void setReplyto(Destination value) {
		replyto = value;
	}

    public TextMessage createTextMessage(String data) throws JMSException {
    	return sessionAdapter.createTextMessage(data);
    }
    
    public ObjectMessage createObjectMessage(Serializable data) throws JMSException {
    	return sessionAdapter.createObjectMessage(data);
    }
    
	public boolean isValid() {
		return isInitialized() && !isClosing() && !isClosed() && getSessionAdapter().isValid();
	}

	public void invalidate() {
		setInitialized(false);
	}

	public void validate() throws NamingException, JMSException {
		if (!isValid()) {
			reset();
		}
	}
    
	public void reset() throws NamingException, JMSException {
    	synchronized (mutex) {
    		close();
			//if (sessionAdapter != null && sessionAdapter.isClosed())
			if (sessionAdapter != null)
				sessionAdapter.reset();
			if (!isValid())
				initialize();
    	}
	}

    public void close() throws JMSException {
		setClosing(true);
    	synchronized (mutex) {
    		try {
	    		destination = null;
	    		if (sessionListener != null)
	    			sessionAdapter.removeAdapterListener(sessionListener);
	    		//sessionListener = null;
    		} finally {
				setClosing(false);
    			setClosed(true);
    			setInitialized(false);
    		}
    	}
    }
    
    protected void process(Throwable e) {
    	propagateException(e);
	    log.error(e.getMessage());
	    try {
		    reset();
	    } catch (Throwable t) {
	    	log.error(t);
	    }
	}

	protected void propagateException(Throwable exception) {
		//TODO complete this
		//getConnectionAdapter().propagateException(convert(exception));
	}

	protected JMSException convert(Throwable e) {
		return ExceptionUtil.getAs(e);
	}

}
