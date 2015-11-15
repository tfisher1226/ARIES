package org.aries.jndi;

import java.io.Serializable;
import java.util.Map;


@SuppressWarnings("serial")
public class JndiDescripter implements Serializable {

    private String connectionUrl;

    private String connectionFactory;

    private String contextFactory;

    private String securityPrinciple;

    private String securityCredentials;

    private Map<String, String> properties;

    
    public JndiDescripter() {
    	//nothing for now
    }
    
	public String getConnectionUrl() {
		return connectionUrl;
	}

	public void setConnectionUrl(String connectionUrl) {
		this.connectionUrl = connectionUrl;
	}

	public String getConnectionFactory() {
		return connectionFactory;
	}

	public void setConnectionFactory(String connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public String getContextFactory() {
		return contextFactory;
	}

	public void setContextFactory(String contextFactory) {
		this.contextFactory = contextFactory;
	}

	public String getSecurityPrinciple() {
		return securityPrinciple;
	}

	public void setSecurityPrinciple(String securityPrinciple) {
		this.securityPrinciple = securityPrinciple;
	}

	public String getSecurityCredentials() {
		return securityCredentials;
	}

	public void setSecurityCredentials(String securityCredentials) {
		this.securityCredentials = securityCredentials;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

}
