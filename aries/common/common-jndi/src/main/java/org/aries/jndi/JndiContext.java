package org.aries.jndi;

import java.util.Map;

import javax.naming.NamingException;


public interface JndiContext {

    public String getUserName();
    
    public String getPassword();
    
    public String getConnectionUrl();

    public void setConnectionUrl(String value);
    
    public String getContextFactory();

    public void setContextFactory(String value);
    
    public Map getProperties();

    public boolean isServiceChangeNeeded();

    public void setServiceChangeNeeded(boolean value);

    public Object lookupObject(String name) throws NamingException;

    //public Object lookupFactory(String name) throws Exception;

	public void registerObject(String key, Object value) throws NamingException;

	public void unregisterObject(String key) throws NamingException;

}
