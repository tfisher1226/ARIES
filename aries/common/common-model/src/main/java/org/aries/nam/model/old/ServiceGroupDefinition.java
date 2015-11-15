package org.aries.nam.model.old;

import java.util.HashMap;
import java.util.Map;


public class ServiceGroupDefinition implements ServiceGroupDescripter {

	private static final long serialVersionUID = 1L;
	
	private String groupName;
	
	private Map<String, ServiceDescripter> services = new HashMap<String, ServiceDescripter>();

	
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Map<String, ServiceDescripter> getServiceDescripters() {
		return services;
	}

	public void addServiceDescripter(ServiceDescripter descripter) {
		descripter.initialize(getGroupName());
		services.put(descripter.getServiceId(), descripter);
	}

	public ServiceDescripter getServiceDescripterByServiceId(String serviceId) {
		return (ServiceDescripter)services.get(serviceId);
	}

	public int hashCode() {
		return groupName.hashCode();
	}

	public boolean equals(Object object) {
		if (getClass().isAssignableFrom(object.getClass())) {
			ServiceGroupDefinition other = (ServiceGroupDefinition)object;
			if (!other.getGroupName().equals(getGroupName()))
				return false;
		}
		return true;
	}

	public String toString() {
		return getGroupName();
	}
}
