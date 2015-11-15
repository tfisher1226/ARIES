package org.aries.jms;

import java.util.List;

import javax.jms.Message;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.jms.JMSTestFixture;
import org.aries.jms.JmsConnectionAdapter;
import org.aries.jms.JmsSessionAdapter;
import org.aries.jms.config.JmsConnectionDescripter;
import org.aries.jms.config.JmsSessionDescripter;
import org.aries.jms.util.JBossMQManager;


public abstract class AbstractJmsIT extends TestCase {

	protected Log log = LogFactory.getLog(getClass());
	
	protected JmsConnectionDescripter connectionDescripter;
	protected JmsConnectionAdapter connectionAdapter;
	protected JmsConnectionAdapter connectionAdapter2;
	protected JmsSessionDescripter sessionDescripter;
	protected JmsSessionAdapter sessionAdapter;
	protected JmsSessionAdapter sessionAdapter2;

	
	public AbstractJmsIT() {
		//nothing for now
	}
	
    public void setUp() throws Exception {
        super.setUp();
        assureJNDIContext();
        //assureMessagesRemoved();
    	assureConnectionDescripter();
    	assureConnectionAdapters();
    	assureSessionDescripter();
    	assureSessionAdapters();
    	assureInitialization();
    }
    
	public void tearDown() throws Exception {
        closeConnectionAdapters();
        closeSessionAdapters();
        super.tearDown();
    }

	public void setConnectionDescripter(JmsConnectionDescripter value) {
		connectionDescripter = value;
	}

	public void setConnectionAdapter(JmsConnectionAdapter value) {
		connectionAdapter = value;
	}

	public void setConnectionAdapter2(JmsConnectionAdapter value) {
		connectionAdapter2 = value;
	}

	public void setSessionDescripter(JmsSessionDescripter value) {
		sessionDescripter = value;
	}

	public void setSessionAdapter(JmsSessionAdapter value) {
		sessionAdapter = value;
	}

	public void setSessionAdapter2(JmsSessionAdapter value) {
		sessionAdapter2 = value;
	}

    protected void assureJNDIContext() {
    	String url = "localhost:1099";
    	String factory = "org.jnp.interfaces.NamingContextFactory";
    	JMSTestFixture.createJNDIContext(url, factory);
    }
    
    private void assureMessagesRemoved() throws Exception {
    	JBossMQManager manager = new JBossMQManager("localhost", 1099);
    	manager.initialize();
    	manager.removeTestMessages();
	}

    protected void assureConnectionDescripter() {
    	JmsConnectionDescripter connectionDescripter = JMSTestFixture.createConnectionDescripter("clientId");
    	connectionDescripter.setJndiContext(JMSTestFixture.getJNDIContext());
		setConnectionDescripter(connectionDescripter);
    }

    protected void assureSessionDescripter() {
    	JmsSessionDescripter sessionDescripter = JMSTestFixture.createSessionDescripter(connectionDescripter);
		setSessionDescripter(sessionDescripter);
    }

	protected void assureConnectionAdapters() throws Exception {
    	setConnectionAdapter(JMSTestFixture.createConnectionAdapter("clientId"));
    	setConnectionAdapter2(JMSTestFixture.createConnectionAdapter("clientId2"));
	}

    protected void assureSessionAdapters() throws Exception {
    	setSessionAdapter(JMSTestFixture.createSessionAdapter(connectionAdapter));
    	setSessionAdapter2(JMSTestFixture.createSessionAdapter(connectionAdapter2));
	}


    /*
     * Initialization methods
     */

    private void assureInitialization() throws Exception {
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


    /*
     * Supporting methods
     */

    public List<Message> listMessages() throws Exception {
    	JBossMQManager manager = new JBossMQManager("localhost", 1099);
    	manager.initialize();
    	List<Message> messages = manager.listMessages();
    	return messages;
	}

    /*
     * Teardown
     */

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
	
}
