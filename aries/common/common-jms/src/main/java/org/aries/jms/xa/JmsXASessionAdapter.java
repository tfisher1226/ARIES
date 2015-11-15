package org.aries.jms.xa;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.XAConnection;
import javax.jms.XASession;
import javax.transaction.xa.XAResource;

import org.aries.Assert;
import org.aries.jms.JmsSessionAdapter;
import org.aries.jms.config.JmsSessionDescripter;


public class JmsXASessionAdapter extends JmsSessionAdapter {

	private static final String MBEAN_NAME = "Messaging:name=XASessionAdapter";
	
	private XASession _xaSession;

	//private XAResource _xaResource;


	public JmsXASessionAdapter() {
		this(new JmsSessionDescripter());
	}
	
	public JmsXASessionAdapter(JmsSessionDescripter specification) {
		super(specification);
		specification.setTransacted(true);
	}

    public String getMBeanName() {
    	return MBEAN_NAME;
    }
    
	public XASession getXASession() {
		return _xaSession;
	}

	public void setXASession(XASession value) {
		_xaSession = value;
	}

	public XAResource getXAResource() {
		return _xaSession.getXAResource();
	}
	
	public JmsXAConnectionAdapter getXAConnectionAdapter() {
        return (JmsXAConnectionAdapter) getConnectionAdapter();
    }

//	@Override
//    protected JmsConnectionAdapterImpl createConnectionAdapter() throws JMSException {
//    	JmsXAConnectionAdapterImpl xaConnectionAdapter = new JmsXAConnectionAdapterImpl(_specification.getConnectionDescripter());
//    	xaConnectionAdapter.setJndiContext(_jndiContext);
//    	return xaConnectionAdapter;
//	}
	
	@Override
    protected void assureSession() throws JMSException {
    	Connection connection = connectionAdapter.getConnection();
    	Assert.isInstanceOf(XAConnection.class, connection);
    	_xaSession = ((XAConnection) connection).createXASession();
    	//_xaResource = _xaSession.getXAResource();
    	setSession(_xaSession.getSession());
    }

	@Override
	public void closeSession() throws JMSException {
    	synchronized (mutex) {
    		try {
    			if (_xaSession != null)
    				_xaSession.close();
    		} catch (Throwable e) {
    			log.debug("Error releasing session", e);
    		}
	    }
	}

}
