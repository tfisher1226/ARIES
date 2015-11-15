package org.aries;

import org.aries.jndi.JndiContext;


//look at org.jboss.jms.jndi.JNDIProviderAdapter
public abstract class AbstractAdapter extends AbstractExecutable implements Adapter {

	protected AdapterMulticaster eventMulticaster;

    protected JndiContext jndiContext;


    public AbstractAdapter() {
    	construct();
    }

    private void construct() {
		eventMulticaster = createEventMulticaster();
    }
    
    public JndiContext getJndiContext() {
        return jndiContext;
    }

    public void setJndiContext(JndiContext value) {
        jndiContext = value;
    }
    
    protected AdapterMulticaster createEventMulticaster() {
    	AdapterMulticaster listener = new AdapterMulticaster();
		return listener;
	}
    
    public void addAdapterListener(AdapterListener listener) {
    	eventMulticaster.addListener(listener);
    }

    public void removeAdapterListener(AdapterListener listener) {
    	eventMulticaster.removeListener(listener);
    }

}
