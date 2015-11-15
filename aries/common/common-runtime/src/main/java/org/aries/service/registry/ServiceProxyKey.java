package org.aries.service.registry;

import java.io.Serializable;

import org.aries.transport.TransportType;

import org.aries.Assert;


@SuppressWarnings("serial")
public class ServiceProxyKey implements Serializable {

	//private String context;
	
	private String version;

	private String serviceId;
	
	private String operation;
	
	private TransportType transport;

	
	public ServiceProxyKey(String context, String version, String serviceId, String operation, TransportType transport) {
		//Assert.notNull(context, "Context must be specified");
		Assert.notNull(version, "Version must be specified");
		Assert.notNull(serviceId, "ServiceId must be specified");
		Assert.notNull(operation, "NperationName must be specified");
		Assert.notNull(transport, "Transport must be specified");
		//this.context = context;
		this.version = version;
		this.serviceId = serviceId;
		this.operation = operation;
		this.transport = transport;
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
		ServiceProxyKey other = (ServiceProxyKey) object;
		//if (!context.equals(other.context))
		//	return false;
		if (!version.equals(other.version))
			return false;
		if (!serviceId.equals(other.serviceId))
			return false;
		if (!operation.equals(other.operation))
			return false;
		if (!transport.equals(other.transport))
			return false;
		return true;
	}
	
    @Override
	public String toString() {
    	StringBuffer buf = new StringBuffer();
    	//buf.append("/"+context);
    	buf.append("/"+version);
    	buf.append("/"+serviceId);
    	buf.append("/"+operation);
    	buf.append("/"+transport);
		return buf.toString();
	}

}
