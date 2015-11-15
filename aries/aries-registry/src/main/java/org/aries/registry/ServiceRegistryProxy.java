package org.aries.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.cache.AbstractCachePeer;
import org.aries.service.registry.ServiceKeyUtil;
import org.aries.service.registry.ServiceNameKey;
import org.aries.service.registry.ServiceState;
import org.aries.service.registry.ServiceStateKey;
import org.aries.transport.TransportType;


/**
 * Maps that exist here:
 * Map<ServiceName, Set<Transport>> availableServiceTransports
 * Map<ServiceId, List<ServiceState>> availableServiceInstances
 *  
 * @author tfisher
 */
public class ServiceRegistryProxy extends AbstractCachePeer implements ServiceStateRegistry {

	private static Log log = LogFactory.getLog(ServiceRegistryProxy.class);

	private static String CACHE_NAME = "serviceRegistry";

	private static String CACHE_PEER_NAME = "serviceRegistryManager";

	//private Map<ServiceStateKey, List<ServiceState>> serviceStates;
	
	private Map<ServiceStateKey, List<ServiceState>> transientCache = new HashMap<ServiceStateKey, List<ServiceState>>();
	
	private Object mutex = new Object();
	

	@Override
	protected String getCacheName() {
		return CACHE_NAME;
	}

	@Override
	protected String getCachePeerName() {
		return CACHE_PEER_NAME;
	}


	protected ServiceNameKey getServiceNameKey(ServiceState serviceState) {
		return ServiceKeyUtil.createServiceNameKey(serviceState);
	}

	public static ServiceNameKey getServiceNameKey(String context, String version, String service) {
		return ServiceKeyUtil.createServiceNameKey(context, version, service);
	}
	
	protected ServiceStateKey createServiceStateKey(ServiceState serviceState, TransportType transport) {
		return ServiceKeyUtil.createServiceStateKey(serviceState, transport);
	}

	protected ServiceStateKey createServiceStateKey(ServiceNameKey serviceNameKey, TransportType transport) {
		return ServiceKeyUtil.createServiceStateKey(serviceNameKey, transport);
	}

	public static ServiceStateKey createServiceStateKey(String context, String version, String service, TransportType transport) {
		return ServiceKeyUtil.createServiceStateKey(context, version, service, transport);
	}

	
	@Override
	public void refreshServiceState(ServiceState state, TransportType transport) {
		synchronized (mutex) {
			try {
				//acquireWriteLock();
				log.debug("started");
				
				//first, update service-states for specified transport
				Ehcache cache = getCacheManager().getEhcache(CACHE_NAME);
				ServiceNameKey serviceName = getServiceNameKey(state);
				Element element = cache.get(serviceName);
				if (element == null) {
					Map<ServiceNameKey, Set<TransportType>> serviceTransportMap = new HashMap<ServiceNameKey, Set<TransportType>>();
					element = new Element(serviceName, serviceTransportMap);
					Set<TransportType> transports = new HashSet<TransportType>();
					serviceTransportMap.put(serviceName, transports);
					transports.add(transport);
					cache.put(element);
				} else {
					@SuppressWarnings("unchecked") 
					Map<ServiceNameKey, Set<TransportType>> serviceTransportMap = 
						(Map<ServiceNameKey, Set<TransportType>>) element.getValue();
					Set<TransportType> transports = serviceTransportMap.get(serviceName);
					transports.add(transport);
					cache.put(element);
				}
				
				//next, update transports for specified service
				ServiceStateKey serviceStateKey = createServiceStateKey(state, transport);
				//ServiceTransportKey transportKey = new ServiceTransportKey(serviceId, transport);
				element = cache.get(serviceStateKey);
				if (element == null) {
					List<ServiceState> serviceStates = new ArrayList<ServiceState>();
					element = new Element(serviceStateKey, serviceStates);
					serviceStates.add(state);
					cache.put(element);
				} else {
					@SuppressWarnings("unchecked")
					List<ServiceState> serviceStates = (List<ServiceState>) element.getValue();
					serviceStates.add(state);
					cache.put(element);
				}
				log.debug("complete");
			} catch (Exception e) {
				log.debug("aborted", e);
			} finally {
				//releaseWriteLock();
			}
		}
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public Map<TransportType, List<ServiceState>> getServiceStates(String context, String version, String service) {
		Map<TransportType, List<ServiceState>> serviceStates = new HashMap<TransportType, List<ServiceState>>();
		
		synchronized (mutex) {
			try {
				//acquireReadLock();
				log.debug("started");
				
				//first, get list of available transports for specified service
				Ehcache cache = getCacheManager().getEhcache(CACHE_NAME);
				ServiceNameKey serviceName = getServiceNameKey(context, version, service);
				Map<ServiceNameKey, Set<TransportType>> availableServiceTransports = null;
				Element element = cache.get(serviceName);
				if (element == null) {
					//if currently empty, add empty list 
					availableServiceTransports = new HashMap<ServiceNameKey, Set<TransportType>>();
					element = new Element(serviceName, serviceStates);
					cache.put(element);
					
				} else {
					//if states do exist, accumulate available states for each existing transport  
					availableServiceTransports = (Map<ServiceNameKey, Set<TransportType>>) element.getValue();
					Set<TransportType> transports = availableServiceTransports.get(serviceName);
					if (transports != null) {
						Iterator<TransportType> iterator = transports.iterator();
						while (iterator.hasNext()) {
							TransportType transport = (TransportType) iterator.next();
							ServiceStateKey serviceStateKey = createServiceStateKey(context, version, service, transport);
							element = cache.get(serviceStateKey);
							Assert.notNull(element, "Service-states for service-instance-ID not found: "+serviceStateKey);
							List<ServiceState> states = (List<ServiceState>) element.getValue();
							serviceStates.put(transport, states);
						}
					}
				}
				
				//return results
				log.debug("complete");
				return serviceStates;
			
			} catch (Exception e) {
				log.debug("aborted", e);
				return null;
	
			} finally {
				//releaseReadLock();
			}
		}
	}
	

	@Override
	public List<ServiceState> getServiceStates(String context, String version, String service, TransportType transport) {
		synchronized (mutex) {
			try {
				//acquireReadLock();
				log.debug("started");
				ServiceStateKey serviceStateKey = createServiceStateKey(context, version, service, transport);
				List<ServiceState> serviceStates = null;

				log.debug("complete");
				return serviceStates;
			} catch (Exception e) {
				log.debug("aborted", e);
				return null;
			} finally {
				//releaseReadLock();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ServiceState> getServiceStates(ServiceStateKey serviceStateKey) {
		List<ServiceState> serviceStates = null;
		Ehcache cache = getCacheManager().getEhcache(CACHE_NAME);
		if (cache != null) {
			Element element = cache.get(serviceStateKey);
			if (element != null)
				serviceStates = (List<ServiceState>) element.getValue();
			return serviceStates;
		}

		transientCache.get(serviceStateKey);
		return serviceStates;
	}

}
