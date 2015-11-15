package org.aries.jms;

import org.aries.jms.config.JmsConnectionDescripter;
import org.aries.jms.config.JmsSessionDescripter;
import org.aries.jndi.JndiContext;
import org.aries.jndi.JndiProxy;


public class JMSTestFixture {

	private static String DEFAULT_DESTINATION_NAME = "queueA";
	private static String DEFAULT_DESTINATION_TYPE = "Queue";

	//private static String DEFAULT_DESTINATION_NAME = "topicA";
	//private static String DEFAULT_DESTINATION_TYPE = "Topic";
	
    protected static JndiContext jndiContext;
    
    protected static String connectionFactory = "/ConnectionFactory";
    

	public static String getJndiHost() {
		return "localhost";
	}

	public static int getJndiPort() {
		return 1099;
	}

//	public static String getJndiConnectionUrl() {
//		return "jnp://localhost:1099";
//	}
//
//	public static String getJndiContextFactory() {
//		return "org.jnp.interfaces.NamingContextFactory";
//	}

	public static String getConnectionFactory() {
		return connectionFactory;
	}

	public static void setConnectionFactory(String connectionFactory) {
		JMSTestFixture.connectionFactory = connectionFactory;
	}

	public static String getDestinationName() {
		return DEFAULT_DESTINATION_NAME;
	}

	public static String getDestinationType() {
		return DEFAULT_DESTINATION_TYPE;
	}

	public static String getClientId() {
		return "clientId";
	}

	public static String getClientId2() {
		return "clientId2";
	}

	public static String getDurableSubscriberId() {
		return "durableSubscriberId";
	}

	public static String getDurableSubscriberId2() {
		return "durableSubscriberId2";
	}

	
	public static JndiContext getJNDIContext() {
		return jndiContext;
	}

	public static JndiContext createJNDIContext() {
    	JndiProxy jndiContext = new JndiProxy();
    	JMSTestFixture.jndiContext = jndiContext;
    	return jndiContext;
    }
	
	public static JndiContext createJNDIContext(String url, String factory) {
    	JndiProxy jndiContext = new JndiProxy();
    	jndiContext.setConnectionUrl(url);
    	jndiContext.setContextFactory(factory);
    	JMSTestFixture.jndiContext = jndiContext;
    	return jndiContext;
    }

    public static JmsConnectionDescripter createConnectionDescripter() {
    	return createConnectionDescripter(getClientId());
    }

    public static JmsConnectionDescripter createConnectionDescripter2() {
    	return createConnectionDescripter(getClientId2());
    }

    public static JmsConnectionDescripter createConnectionDescripter(String clientId) {
    	JmsConnectionDescripter connectionDescripter = new JmsConnectionDescripter();
    	connectionDescripter.setConnectionFactory(getConnectionFactory());
    	connectionDescripter.setClientId(clientId);
    	connectionDescripter.setUserName("guest");
    	connectionDescripter.setPassword("guest");
    	connectionDescripter.setJndiContext(getJNDIContext());
    	return connectionDescripter;
    }
    
    public static JmsSessionDescripter createSessionDescripter() {
    	JmsSessionDescripter sessionDescripter = new JmsSessionDescripter();
    	return sessionDescripter;
    }

    public static JmsSessionDescripter createSessionDescripter(JmsConnectionDescripter connectionDescripter) {
    	JmsSessionDescripter sessionDescripter = new JmsSessionDescripter();
    	sessionDescripter.setConnectionDescripter(connectionDescripter);
    	return sessionDescripter;
    }

    public static JmsConnectionAdapter createConnectionAdapter() throws Exception {
    	return createConnectionAdapter(getClientId());
    }
    
    public static JmsConnectionAdapter createConnectionAdapter2() throws Exception {
    	return createConnectionAdapter(getClientId2());
    }
    
    public static JmsConnectionAdapter createConnectionAdapter(String clientId) throws Exception {
    	JmsConnectionDescripter specification = createConnectionDescripter(clientId);
    	specification.setJndiContext(getJNDIContext());
		return createConnectionAdapter(specification);
    }

    public static JmsConnectionAdapter createConnectionAdapter(JmsConnectionDescripter specification) throws Exception {
    	JmsConnectionAdapter connectionAdapter = new JmsConnectionAdapter(specification);
		connectionAdapter.setJndiContext(getJNDIContext());
		return connectionAdapter;
    }

    public static JmsSessionAdapter createSessionAdapter() throws Exception {
    	return createSessionAdapter(getClientId());
    }
    
    public static JmsSessionAdapter createSessionAdapter(String clientId) throws Exception {
    	JmsConnectionAdapter connectionAdapter = createConnectionAdapter(clientId);
    	JmsSessionDescripter sessionDescripter = createSessionDescripter(connectionAdapter.getDescripter());
    	JmsSessionAdapter sessionAdapter = createSessionAdapter(sessionDescripter, connectionAdapter);
		return sessionAdapter;
	}

    public static JmsSessionAdapter createSessionAdapter(JmsSessionDescripter sessionDescripter) throws Exception {
    	JmsSessionAdapter sessionAdapter = new JmsSessionAdapter(sessionDescripter);
    	sessionAdapter.setJndiContext(getJNDIContext());
		return sessionAdapter;
    }

    public static JmsSessionAdapter createSessionAdapter(JmsConnectionAdapter connectionAdapter) throws Exception {
    	JmsSessionDescripter sessionDescripter = createSessionDescripter(connectionAdapter.getDescripter());
    	JmsSessionAdapter sessionAdapter = createSessionAdapter(sessionDescripter, connectionAdapter);
		return sessionAdapter;
	}

    public static JmsSessionAdapter createSessionAdapter(JmsSessionDescripter sessionDescripter, JmsConnectionAdapter connectionAdapter) throws Exception {
    	JmsSessionAdapter sessionAdapter = new JmsSessionAdapter(sessionDescripter);
    	sessionAdapter.setConnectionAdapter(connectionAdapter);
    	sessionAdapter.setJndiContext(getJNDIContext());
		return sessionAdapter;
	}

    public static JmsSessionAdapter createTransactedSessionAdapter(JmsConnectionAdapter connectionAdapter) throws Exception {
    	JmsSessionDescripter sessionDescripter = createSessionDescripter(connectionAdapter.getDescripter());
    	JmsSessionAdapter sessionAdapter = createSessionAdapter(sessionDescripter, connectionAdapter);
		sessionDescripter.setTransacted(true);
		return sessionAdapter;
	}

    public static JmsSessionAdapter prepareTransactedSessionAdapter(JmsConnectionAdapter connectionAdapter) throws Exception {
    	JmsSessionAdapter sessionAdapter = createTransactedSessionAdapter(connectionAdapter);
		sessionAdapter.initialize();
		return sessionAdapter;
	}

}
