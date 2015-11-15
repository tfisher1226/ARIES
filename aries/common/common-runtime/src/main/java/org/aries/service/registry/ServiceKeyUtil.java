package org.aries.service.registry;

import org.aries.nam.model.old.OperationDescripter;
import org.aries.transport.TransportType;


public class ServiceKeyUtil {

	public static ServiceNameKey createServiceNameKey(ServiceState serviceState) {
		ServiceNameKey key = createServiceNameKey(
			serviceState.getContext(),
			serviceState.getVersion(),
			serviceState.getServiceId());
		return key;
	}

	public static ServiceNameKey createServiceNameKey(String context, String version, String service) {
		ServiceNameKey key = new ServiceNameKey(context, version, service);
		return key;
	}

	public static ServiceStateKey createServiceStateKey(ServiceState state, TransportType transport) {
		ServiceStateKey key = createServiceStateKey(
			state.getContext(), 
			state.getVersion(), 
			state.getServiceId(),
			transport);
		return key;
	}

	public static ServiceStateKey createServiceStateKey(ServiceNameKey serviceNameKey, TransportType transport) {
		ServiceStateKey key = createServiceStateKey(
			serviceNameKey.getContext(), 
			serviceNameKey.getVersion(), 
			serviceNameKey.getServiceId(), 
			transport);
		return key;
	}

	public static ServiceStateKey createServiceStateKey(String context, String version, String service, TransportType transport) {
		ServiceStateKey key = new ServiceStateKey(context, version, service, transport);
		return key;
	}

	public static ServiceProxyKey createServiceProxyKey(ServiceState state, OperationDescripter operation, TransportType transport) {
		ServiceProxyKey key = createServiceProxyKey(
			state.getContext(), 
			state.getVersion(), 
			state.getServiceId(),
			operation,
			transport);
		return key;
	}

	public static ServiceProxyKey createServiceProxyKey(ServiceNameKey serviceNameKey, OperationDescripter operation, TransportType transport) {
		ServiceProxyKey key = createServiceProxyKey(
			serviceNameKey.getContext(), 
			serviceNameKey.getVersion(), 
			serviceNameKey.getServiceId(), 
			operation,
			transport);
		return key;
	}

	public static ServiceProxyKey createServiceProxyKey(String context, String version, String serviceId, OperationDescripter operation, TransportType transport) {
		ServiceProxyKey key = new ServiceProxyKey(context, version, serviceId, operation.getName(), transport);
		return key;
	}

}
