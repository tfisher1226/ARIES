package org.aries.nam.model.old;

import java.io.Serializable;
import java.util.Map;


public interface ServiceGroupDescripter extends Serializable {

	public String getGroupName();

	public Map<String, ServiceDescripter> getServiceDescripters();

	public void addServiceDescripter(ServiceDescripter serviceDescripter);

	public ServiceDescripter getServiceDescripterByServiceId(String serviceId);
}
