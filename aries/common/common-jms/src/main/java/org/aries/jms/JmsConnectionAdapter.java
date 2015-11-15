package org.aries.jms;

import java.util.Collection;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.ConnectionMetaData;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.naming.CommunicationException;
import javax.naming.NamingException;
import javax.naming.NoInitialContextException;

import org.apache.commons.lang.StringUtils;
import org.aries.AbstractAdapter;
import org.aries.jms.config.JmsConnectionDescripter;
import org.aries.jndi.JndiContext;
import org.aries.jndi.JndiProxy;
import org.aries.util.ThreadUtil;


public class JmsConnectionAdapter extends AbstractAdapter {

	private static final String MBEAN_NAME = "Messaging:name=ConnectionAdapter";
	
    protected JmsConnectionDescripter connectionDescripter;
    
    protected JmsConnectionListener connectionListener;

	//private SessionPool sessionPool;
	
    private Connection connection;

    private Object mutex;


	public JmsConnectionAdapter(JmsConnectionDescripter connectionDescripter) {
    	setJndiContext(connectionDescripter.getJndiContext());
    	this.connectionDescripter = connectionDescripter;
    	this.connectionListener = createConnectionListener();
        //sessionPool = createSessionPool();
    	mutex = new Object();
    }

    public String getMBeanName() {
    	return MBEAN_NAME;
    }
    
	public Connection getConnection() {
        return connection;
    }

	void setConnection(Connection connection) {
		this.connection = connection;
	}

	public JmsConnectionDescripter getDescripter() {
        return connectionDescripter;
    }

	public void setDescripter(JmsConnectionDescripter connectionDescripter) {
        this.connectionDescripter = connectionDescripter;
    }

	public ConnectionMetaData getConnectionMetaData() throws JMSException {
		return connection.getMetaData();
	}

    public Collection<ExceptionListener> getExceptionListeners() {
        return connectionListener.getExceptionListeners();
    }

    public void addExceptionListener(ExceptionListener listener) {
    	connectionListener.addExceptionListener(listener);
    }

    public void propagateException(JMSException exception) {
        connectionListener.onException(exception);
    }

//	public SessionPool createSessionPool() {
//		SessionPool pool = new SessionPool(this);
//		return pool;
//	}

    public JndiContext getJndiContext() {
    	if (super.getJndiContext() == null)
    		super.setJndiContext(new JndiProxy());
        return super.getJndiContext();
    }

	
    public void initialize() throws JMSException {
    	synchronized (mutex) {
    		if (isInitialized())
    			throw new JMSException("Already initialized");
	        Thread thread = Thread.currentThread();
	        while (!thread.isInterrupted()) {
	            try {
	                // Lookup factory create connection
	                connection = createConnection();
		        	setClosed(false);
	    			setInitialized(true);
	                break;
	                
	            } catch (CommunicationException e) {
	                String message = "Failed to create connection with: "+connectionDescripter.getConnectionFactory();
	                log.warn(message);
	            } catch (NoInitialContextException e) {
	            	log.error(e);
	                throw new JMSException("Cannot connect: "+e.getMessage());
	            } catch (NamingException e) {
	                log.warn(e.getMessage());
	            } catch (Exception e) {
	            	log.error(e);
	                throw new JMSException("Unexpected problem: "+e.getMessage());
	    		}
	        }
	    }
    }
    
    protected Connection createConnection() throws NamingException, JMSException  {
        ConnectionFactory factory = getConnectionFactory(connectionDescripter.getConnectionFactory());
        String clientId = connectionDescripter.getClientId();
        String userName = connectionDescripter.getUserName();
		String password = connectionDescripter.getPassword();
        Connection connection = null;
        
    	try {
			if (!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(password))
				connection = factory.createConnection(userName, password);
			else connection = factory.createConnection();
			if (clientId != null)
	        	connection.setClientID(clientId);
	        connection.setExceptionListener(connectionListener);
	        log.info("Connection established: "+toString(connection));
	        return connection;
	        
    	} catch (JMSException e) {
    		if (connection != null)
    			connection.close();
    		throw e;
    	}
    }

	protected ConnectionFactory getConnectionFactory(String factoryName) throws NamingException {
    	ConnectionFactory factory = (ConnectionFactory) getConnectionFactoryObject(factoryName);
        return factory;
    }
    
    public Object getConnectionFactoryObject(String name) throws NamingException {
    	Object object = instantiateConnectionFactory(name);
    	if (object == null)
    		object = getJndiContext().lookupObject(name);
    	return object;
    }

    protected Object instantiateConnectionFactory(String name) {
    	try {
    		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    		//ClassLoader classLoader = getClass().getClassLoader();
    		Class<?> classObject = classLoader.loadClass(name);
    		Object object = classObject.newInstance();
    		return object;
    	} catch (Exception e) {
    		//ignore this
    		return null;
    	}
    }
    
    protected JmsConnectionListener createConnectionListener() {
    	JmsConnectionListener listener = new JmsConnectionListener();
    	listener.addExceptionListener(createExceptionListener());
		return listener;
	}

	protected ExceptionListener createExceptionListener() {
		return new ExceptionListener() {
			public void onException(JMSException exception) {
				log.error("Received Exception: "+exception.getMessage());
				startReset();
			}
		};
	}

	public void start() throws JMSException {
    	synchronized (mutex) {
			if (connection == null || !isInitialized())
				throw new JMSException("Not initialized");
			try {
				if (!isStarted()) {
					connection.start();
					setClosed(false);
					setStarted(true);
				}
			} catch (Throwable t) {
				log.debug("Error starting connection: "+t);
			}
		}
	}
	
	protected void startReset() {
		ThreadUtil.execute(new Runnable() {
			public void run() {
				try {
					reset();
				} catch (Exception e) {
					log.error(e);
				}
			}
		});
	}
	
	public void reset() throws JMSException {
    	synchronized (mutex) {
    		close();
    		initialize();
    		start();
    		eventMulticaster.reset();
		}
	}
	
    public void close() throws JMSException {
    	synchronized (mutex) {
    		try {
    			setClosing(true);
    			if (connection != null) {
			        connection.close();
			        connection = null;
			        eventMulticaster.closed();
			    }
    		} catch (Throwable e) {
    			log.debug("Error releasing connection", e);
    		} finally {
    			setClosing(false);
    			setClosed(true);
    			setInitialized(false);
    		}
	    }
    }

    public static String toString(Connection connection) throws JMSException {
    	StringBuffer buf = new StringBuffer();
    	ConnectionMetaData metaData = connection.getMetaData();
		buf.append("JMSVersion="+metaData.getJMSVersion()+", ");
    	buf.append("JMSProviderName="+metaData.getJMSProviderName()+", ");
    	buf.append("JMSProviderVersion="+metaData.getProviderVersion()+", ");
		buf.append("JMSClientID="+connection.getClientID());
		//TODO Enumeration propertyNames = metaData.getJMSXPropertyNames();
    	return buf.toString();
	}

    public String toString() {
		return connectionDescripter.toString();     	
    }
    
}
