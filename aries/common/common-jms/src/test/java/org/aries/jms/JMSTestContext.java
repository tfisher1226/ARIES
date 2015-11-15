package org.aries.jms;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.jms.config.JmsConnectionDescripter;
import org.aries.jms.config.JmsSessionDescripter;
import org.aries.jms.util.JBossMQManager;
import org.aries.jms.util.JBossMQProxy;


public class JMSTestContext {

	protected Log log = LogFactory.getLog(getClass());
	
	protected JmsConnectionDescripter connectionDescripter;
	
	protected JmsConnectionAdapter connectionAdapter;
	
	protected JmsConnectionAdapter connectionAdapter2;
	
	protected JmsSessionDescripter sessionDescripter;
	
	protected JmsSessionAdapter sessionAdapter;
	
	protected JmsSessionAdapter sessionAdapter2;

	
	public JMSTestContext() {
		//nothing for now
	}
	
    public void setUp() throws Exception {
        assureJndiContext();
        //assureMessagesRemoved();
    	createConnectionDescripter();
    	createConnectionAdapters();
    	createSessionDescripter();
    	createSessionAdapters();
    	initialize();
    }
    
	public void tearDown() throws Exception {
        closeConnectionAdapters();
        closeSessionAdapters();
    }

	public JmsConnectionDescripter getConnectionDescripter() {
		return connectionDescripter;
	}

	public void setConnectionDescripter(JmsConnectionDescripter connectionDescripter) {
		this.connectionDescripter = connectionDescripter;
	}

	public JmsConnectionAdapter getConnectionAdapter() {
		return connectionAdapter;
	}
	
	public void setConnectionAdapter(JmsConnectionAdapter connectionAdapter) {
		this.connectionAdapter = connectionAdapter;
	}

	public JmsConnectionAdapter getConnectionAdapter2() {
		return connectionAdapter2;
	}
	
	public void setConnectionAdapter2(JmsConnectionAdapter connectionAdapter2) {
		this.connectionAdapter2 = connectionAdapter2;
	}

	public JmsSessionDescripter getSessionDescripter() {
		return sessionDescripter;
	}

	public void setSessionDescripter(JmsSessionDescripter sessionDescripter) {
		this.sessionDescripter = sessionDescripter;
	}

	public JmsSessionAdapter getSessionAdapter() {
		return sessionAdapter;
	}
	
	public void setSessionAdapter(JmsSessionAdapter sessionAdapter) {
		this.sessionAdapter = sessionAdapter;
	}

	public JmsSessionAdapter getSessionAdapter2() {
		return sessionAdapter2;
	}
	
	public void setSessionAdapter2(JmsSessionAdapter sessionAdapter2) {
		this.sessionAdapter2 = sessionAdapter2;
	}

    protected void assureJndiContext() {
    	JMSTestFixture.createJNDIContext();
    }
    
    protected void assureMessagesRemoved() throws Exception {
    	String jndiHost = JMSTestFixture.getJndiHost();
		int jndiPort = JMSTestFixture.getJndiPort();
		JBossMQManager manager = new JBossMQManager(jndiHost, jndiPort);
    	manager.initialize();
    	manager.removeTestMessages();
	}

    protected void createConnectionDescripter() {
		JmsConnectionDescripter connectionDescripter = JMSTestFixture.createConnectionDescripter();
    	connectionDescripter.setJndiContext(JMSTestFixture.getJNDIContext());
    	connectionDescripter.setUserName("guest");
    	connectionDescripter.setPassword("guest");
		setConnectionDescripter(connectionDescripter);
    }

    protected void createSessionDescripter() {
    	JmsSessionDescripter sessionDescripter = JMSTestFixture.createSessionDescripter(connectionDescripter);
		setSessionDescripter(sessionDescripter);
    }

	protected void createConnectionAdapters() throws Exception {
    	setConnectionAdapter(JMSTestFixture.createConnectionAdapter());
    	setConnectionAdapter2(JMSTestFixture.createConnectionAdapter2());
	}

    protected void createSessionAdapters() throws Exception {
    	setSessionAdapter(JMSTestFixture.createSessionAdapter(connectionAdapter));
    	setSessionAdapter2(JMSTestFixture.createSessionAdapter(connectionAdapter2));
	}


    private void initialize() throws Exception {
    	initializeConnectionAdapters();
    	initializeSessionAdapters();
    	startConnections();
    }

	public void initializeConnectionAdapters() throws Exception {
		connectionAdapter.initialize();
		connectionAdapter2.initialize();
	}

	public void initializeSessionAdapters() throws Exception {
		sessionAdapter.initialize();
		sessionAdapter2.initialize();
	}

	public void startConnections() throws Exception {
		connectionAdapter.start();
		connectionAdapter2.start();
	}

	public void closeConnectionAdapters() throws Exception {
		connectionAdapter.close();
		connectionAdapter2.close();
    	connectionAdapter = null;
    	connectionAdapter2 = null;
	}

	public void closeSessionAdapters() throws Exception {
		sessionAdapter.close();
		sessionAdapter2.close();
    	sessionAdapter = null;
    	sessionAdapter2 = null;
	}
	

    /*
     * Supporting methods
     */

    public List listMessages() throws Exception {
    	JBossMQManager manager = new JBossMQManager(JMSTestFixture.getJndiHost(), JMSTestFixture.getJndiPort());
    	manager.initialize();
    	List messages = manager.listMessages();
    	return messages;
	}

	public static Long getMessageCount() throws Exception {
		return getMessageCount(JMSTestFixture.getDestinationType(), JMSTestFixture.getDestinationName());
	}
	
	public static Long getMessageCount(String destinationType, String destinationName) throws Exception {
    	JBossMQProxy proxy = new JBossMQProxy(JMSTestFixture.getJndiHost(), JMSTestFixture.getJndiPort());
		Long messageCount = proxy.countMessages("name=\""+destinationName+"\",type="+destinationType);
		return messageCount;
	}

	public static void expireMessages() throws Exception {
		expireMessages(JMSTestFixture.getDestinationType(), JMSTestFixture.getDestinationName());
	}
	
	public static void expireMessages(String destinationType, String destinationName) throws Exception {
    	JBossMQProxy proxy = new JBossMQProxy(JMSTestFixture.getJndiHost(), JMSTestFixture.getJndiPort());
		proxy.expireMessages("name=\""+destinationName+"\",type="+destinationType);
	}

	public static void removeMessages() throws Exception {
		removeMessages(JMSTestFixture.getDestinationType(), JMSTestFixture.getDestinationName());
	}
	
	public static void removeMessages(String destinationType, String destinationName) throws Exception {
    	JBossMQProxy proxy = new JBossMQProxy(JMSTestFixture.getJndiHost(), JMSTestFixture.getJndiPort());
		proxy.removeMessages("name=\""+destinationName+"\",type="+destinationType);
	}

}
