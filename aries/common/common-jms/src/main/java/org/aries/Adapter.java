package org.aries;

import org.aries.jndi.JndiContext;


public interface Adapter extends Executable {

    public JndiContext getJndiContext();
    
    public void setJndiContext(JndiContext value);

    public void addAdapterListener(AdapterListener listener);
    
    public void removeAdapterListener(AdapterListener listener);
    
}
