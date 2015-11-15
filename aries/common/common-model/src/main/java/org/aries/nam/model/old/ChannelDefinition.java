package org.aries.nam.model.old;


public class ChannelDefinition implements ChannelDescripter {

	private static final long serialVersionUID = 1L;
	
	public String type;

	public String domain;
	
	public String name;
	
	public String host;
	
	public String port;
	
	public String transferMode;
	
	public String providerName;
	
	public String destinationName;
	
	private String connectionFactory;

	
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

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getTransferMode() {
		return transferMode;
	}

	public void setTransferMode(String transferMode) {
		this.transferMode = transferMode;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public String getConnectionFactory() {
		return connectionFactory;
	}

	public void setConnectionFactory(String connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public void initialize(String domain, String type, String transferMode, String providerName, String connectionFactory) {
		if (domain != null && getDomain() == null)
			setDomain(domain);
		if (type != null && getType() == null)
			setType(type);
		if (transferMode != null && getTransferMode() == null)
			setTransferMode(transferMode);
		if (providerName != null && getProviderName() == null)
			setProviderName(providerName);
		if (connectionFactory != null && getConnectionFactory() == null)
			setConnectionFactory(connectionFactory);
		StringBuilder text = new StringBuilder();
		domain = getDomain();
		if (domain != null) {
			text = text.append(domain);
			text = text.append(".");
			text = text.append(name);
			name = text.toString();
		}
	}

	public int hashCode() {
		return name.hashCode();
	}

	public boolean equals(Object object) {
		if (getClass().isAssignableFrom(object.getClass())) {
			ChannelDefinition other = (ChannelDefinition)object;
			if (!other.getName().equals(getName()))
				return false;
		}
		return true;
	}

	public String toString() {
		return name;
	}
}
