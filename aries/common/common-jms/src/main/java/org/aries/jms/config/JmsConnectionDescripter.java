package org.aries.jms.config;

import org.aries.jndi.JndiContext;


public class JmsConnectionDescripter {

    private String _factoryName;

    public String _clientId;
    
	private String _userName;

	private String _password;

	private JndiContext _jndiContext;

	
    public JmsConnectionDescripter() {
    	//nothing for now
    }

    public String getConnectionFactory() {
        return _factoryName;
    }

    public void setConnectionFactory(String value) {
    	_factoryName = value;
    }
    
    public String getClientId() {
        return _clientId;
    }

    public void setClientId(String value) {
        _clientId = value;
    }
    
    public String getUserName() {
        return _userName;
    }

    public void setUserName(String value) {
        _userName = value;
    }
    
    public String getPassword() {
        return _password;
    }

    public void setPassword(String value) {
        _password = value;
    }
    
    public JndiContext getJndiContext() {
        return _jndiContext;
    }

    public void setJndiContext(JndiContext value) {
    	_jndiContext = value;
    }
    
    public String toString() {
    	StringBuffer label = new StringBuffer();
    	label.append("UserName="+getUserName()+",");
    	label.append("ClientId="+getClientId()+",");
    	label.append("ConnectionFactory="+getConnectionFactory());
    	JndiContext jndiContext = getJndiContext();
    	if (jndiContext != null)
        	label.append(jndiContext);
		return "[ConnectionSpecification:"+label.toString()+"]";     	
    }

    public String toStringJNDIContext() {
    	StringBuffer label = new StringBuffer();
    	JndiContext jndiContext = getJndiContext();
    	label.append("UserName="+jndiContext.getUserName()+",");
    	label.append("ConnectionUrl="+jndiContext.getConnectionUrl()+",");
    	label.append("ContextFactory="+jndiContext.getContextFactory());
		return "[JNDIContext:"+label.toString()+"]";     	
    }

}
