package org.aries.service.registry;

import java.io.Serializable;

import org.aries.Assert;


@SuppressWarnings("serial")
public class ServiceNameKey implements Serializable {

	private String context;
	
	private String version;

	private String serviceId;
	
	
	public ServiceNameKey(String context, String version, String service) {
		//Assert.notNull(context, "Context must be specified");
		Assert.notNull(version, "Version must be specified");
		Assert.notNull(service, "Service must be specified");
		//this.context = context;
		this.version = version;
		this.serviceId = service;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

    @Override
	public int hashCode() {
		return serviceId.hashCode();
	}

    @Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		ServiceNameKey other = (ServiceNameKey) object;
		//if (!context.equals(other.context))
		//	return false;
		if (!version.equals(other.version))
			return false;
		if (!serviceId.equals(other.serviceId))
			return false;
		return true;
	}
	
    @Override
	public String toString() {
		return serviceId+"/"+version;
		//TODO return context+"/"+version+"/"+service;
	}
    
}
