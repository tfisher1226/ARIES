package org.aries.registry;

import java.util.Comparator;
import java.util.List;

import org.aries.Assert;
import org.aries.service.registry.ServiceState;


public class ServiceBalancer implements Comparator<ServiceState> {

    //assuming serviceStates exist
	public ServiceState selectService(List<ServiceState> serviceStates) {
		Assert.isTrue(serviceStates != null && serviceStates.size() > 0, "Service-states Must be specified");
		//TODO Collections.sort(serviceStates, this);
		ServiceState serviceState = serviceStates.get(0);
		return serviceState;
	}

	@Override
	public int compare(ServiceState serviceState1, ServiceState serviceState2) {
		if (serviceState1.getRequestsInProcess() < serviceState2.getRequestsInProcess())
			return 1;
		if (serviceState1.getRequestsInProcess() > serviceState2.getRequestsInProcess())
			return -1;
		if (serviceState1.getRequestsInQueue() < serviceState2.getRequestsInQueue())
			return 1;
		if (serviceState1.getRequestsInQueue() > serviceState2.getRequestsInQueue())
			return -1;
		return 0;
	}
	
}
