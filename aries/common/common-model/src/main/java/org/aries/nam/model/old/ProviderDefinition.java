package org.aries.nam.model.old;

import java.util.Map;


public class ProviderDefinition implements ProviderDescripter {

	private static final long serialVersionUID = 1L;
	
    public String type;

    public String name;

    private String connectionUrl;

    private String contextFactory;

    private String securityPrinciple;

    private String securityCredentials;

    private Map<String, String> properties;

    
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getConnectionUrl() {
		return connectionUrl;
	}

	public void setConnectionUrl(String connectionUrl) {
		this.connectionUrl = connectionUrl;
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

