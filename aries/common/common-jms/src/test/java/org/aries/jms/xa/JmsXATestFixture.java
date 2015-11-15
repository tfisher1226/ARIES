package org.aries.jms.xa;

import org.aries.jms.JMSTestFixture;
import org.aries.jms.JmsConnectionAdapter;
import org.aries.jms.config.JmsConnectionDescripter;
import org.aries.jms.config.JmsSessionDescripter;


public class JmsXATestFixture extends JMSTestFixture {

    public static JmsConnectionDescripter createConnectionDescripter(String clientId) {
    	JmsConnectionDescripter connectionDescripter = JMSTestFixture.createConnectionDescripter(clientId);
    	connectionDescripter.setConnectionFactory("XAConnectionFactory");
    	return connectionDescripter;
    }
    
    public static JmsXAConnectionAdapter createXAConnectionAdapter(String clientId) throws Exception {
    	JmsConnectionDescripter specification = createConnectionDescripter(clientId);
		return createXAConnectionAdapter(specification);
    }

    public static JmsXAConnectionAdapter createXAConnectionAdapter(JmsConnectionDescripter specification) throws Exception {
		JmsXAConnectionAdapter connectionAdapter = new JmsXAConnectionAdapter(specification);
		connectionAdapter.setJndiContext(jndiContext);
		return connectionAdapter;
    }

    public static JmsXASessionAdapter createXASessionAdapter(JmsSessionDescripter sessionDescripter) throws Exception {
    	JmsXASessionAdapter sessionAdapter = new JmsXASessionAdapter(sessionDescripter);
    	sessionAdapter.setJndiContext(jndiContext);
		return sessionAdapter;
    }

    public static JmsXASessionAdapter createXASessionAdapter(JmsConnectionAdapter connectionAdapter) throws Exception {
    	JmsSessionDescripter sessionDescripter = createSessionDescripter(connectionAdapter.getDescripter());
    	JmsXASessionAdapter sessionAdapter = createXASessionAdapter(sessionDescripter, connectionAdapter);
		return sessionAdapter;
	}

    public static JmsXASessionAdapter createXASessionAdapter(JmsSessionDescripter sessionDescripter, JmsConnectionAdapter connectionAdapter) throws Exception {
    	JmsXASessionAdapter sessionAdapter = new JmsXASessionAdapter(sessionDescripter);
    	sessionAdapter.setConnectionAdapter(connectionAdapter);
    	sessionAdapter.setJndiContext(jndiContext);
		return sessionAdapter;
	}

}
