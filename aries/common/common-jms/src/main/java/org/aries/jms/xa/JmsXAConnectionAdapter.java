package org.aries.jms.xa;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.XAConnection;
import javax.jms.XAConnectionFactory;
import javax.naming.NamingException;

import org.aries.jms.JmsConnectionAdapter;
import org.aries.jms.config.JmsConnectionDescripter;


public class JmsXAConnectionAdapter extends JmsConnectionAdapter {

	private static final String MBEAN_NAME = "Messaging:name=XAConnectionAdapter";
	
    private XAConnection _xaConnection;


	public JmsXAConnectionAdapter(JmsConnectionDescripter specification) {
    	super(specification);
    }

    public String getMBeanName() {
    	return MBEAN_NAME;
    }
    
	public XAConnection getXAConnection() {
        return _xaConnection;
    }

	void setXAConnection(XAConnection value) {
		_xaConnection = value;
	}
	
	@Override
    protected Connection createConnection() throws NamingException, JMSException  {
		XAConnectionFactory factory = getXAConnectionFactory(connectionDescripter.getConnectionFactory());
        String clientId = connectionDescripter.getClientId();
        String userName = connectionDescripter.getUserName();
		String password = connectionDescripter.getPassword();
        Connection connection = null;
		if (userName == null || password == null)
        	connection = factory.createXAConnection();
        else connection = factory.createXAConnection(userName, password);
		if (clientId != null)
        	connection.setClientID(clientId);
        connection.setExceptionListener(connectionListener);
        return connection;
    }

    protected XAConnectionFactory getXAConnectionFactory(String factoryName) throws NamingException {
    	XAConnectionFactory factory = (XAConnectionFactory) getConnectionFactory(factoryName);
        return factory;
    }


}
