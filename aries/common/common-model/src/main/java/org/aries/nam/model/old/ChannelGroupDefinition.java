package org.aries.nam.model.old;

import java.util.HashMap;
import java.util.Map;


public class ChannelGroupDefinition implements ChannelGroupDescripter {

	private static final long serialVersionUID = 1L;
	
	public String domain;

	public String type;
	
	public String transferMode;
	
	private String providerName;
	
	private String connectionFactory;
	
	private Map<String, ChannelDescripter> channels = new HashMap<String, ChannelDescripter>();

	
	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getConnectionFactory() {
		return connectionFactory;
	}

	public void setConnectionFactory(String connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public Map getChannelDescripters() {
		return channels;
	}

	public ChannelDescripter getChannelDescripter(String name) {
		return (ChannelDescripter)channels.get(name);
	}

	public void addChannelDescripter(ChannelDescripter descripter) {
		ChannelDefinition definition = (ChannelDefinition)descripter;
		definition.initialize(domain, type, transferMode, providerName, connectionFactory);
		channels.put(descripter.getName(), descripter);
	}

	public int hashCode() {
		return providerName.hashCode();
	}

	public boolean equals(Object object) {
		if (getClass().isAssignableFrom(object.getClass())) {
			ChannelGroupDefinition other = (ChannelGroupDefinition)object;
			if (!other.getDomain().equals(getDomain()))
				return false;
			if (!other.getProviderName().equals(getProviderName()))
				return false;
		}
		return true;
	}

	public String toString() {
		return (new StringBuilder()).append(getDomain()).append(":").append(getProviderName()).toString();
	}
}
