package org.aries.jms.xa;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.jms.JMSTestContext;
import org.aries.jms.config.JmsConnectionDescripter;
import org.aries.jms.config.JmsSessionDescripter;


public class JmsXATestContext extends JMSTestContext {

	protected Log log = LogFactory.getLog(getClass());
	
	protected JmsConnectionDescripter connectionDescripter;
	
	protected JmsXAConnectionAdapter xaConnectionAdapter;
	
	protected JmsXAConnectionAdapter xaConnectionAdapter2;
	
	protected JmsSessionDescripter sessionDescripter;
	
	protected JmsXASessionAdapter xaSessionAdapter;
	
	protected JmsXASessionAdapter xaSessionAdapter2;

	
	public JmsXATestContext() {
		//nothing for now
	}
	
    public void setUp() throws Exception {
        super.setUp();
    }
    
	public void tearDown() throws Exception {
        super.tearDown();
    }

	public JmsXAConnectionAdapter getXAConnectionAdapter() {
		return xaConnectionAdapter;
	}
	
	public JmsXAConnectionAdapter getXAConnectionAdapter2() {
		return xaConnectionAdapter2;
	}
	
	public JmsXASessionAdapter getXASessionAdapter() {
		return xaSessionAdapter;
	}
	
	public JmsXASessionAdapter getXASessionAdapter2() {
		return xaSessionAdapter2;
	}
	
    protected void createConnectionSpecification() {
    	JmsConnectionDescripter specification = JmsXATestFixture.createConnectionDescripter("xaClientId");
		setConnectionDescripter(specification);
    }

    protected void createSessionSpecification() {
    	setSessionDescripter(JmsXATestFixture.createSessionDescripter(connectionDescripter));
    }

	protected void createConnectionAdapters() throws Exception {
		xaConnectionAdapter = JmsXATestFixture.createXAConnectionAdapter("xaClientId");
		xaConnectionAdapter2 = JmsXATestFixture.createXAConnectionAdapter("xaClientId2");
		setConnectionAdapter(xaConnectionAdapter);
		setConnectionAdapter2(xaConnectionAdapter2);
	}

    protected void createSessionAdapters() throws Exception {
    	xaSessionAdapter = JmsXATestFixture.createXASessionAdapter(xaConnectionAdapter);
    	xaSessionAdapter2 = JmsXATestFixture.createXASessionAdapter(xaConnectionAdapter2);
		setSessionAdapter(xaSessionAdapter);
		setSessionAdapter2(xaSessionAdapter2);
	}

}
