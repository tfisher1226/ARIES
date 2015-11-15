package org.aries.registry;

import java.util.List;
import java.util.Map;

import org.aries.service.registry.ServiceState;
import org.aries.transport.TransportType;


public interface ServiceStateRegistry {
	
	//public void initialize() throws Exception;
	
	public void refreshServiceState(ServiceState serviceState, TransportType transport);

	public List<ServiceState> getServiceStates(String context, String version, String service, TransportType transport);

	public Map<TransportType, List<ServiceState>> getServiceStates(String context, String version, String service);

}
