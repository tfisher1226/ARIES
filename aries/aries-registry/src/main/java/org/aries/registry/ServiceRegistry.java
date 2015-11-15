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
//@BypassInterceptors
public class ServiceRegistry extends AbstractCachePeer {

	//private static Log log = LogFactory.getLog(ServiceRegistry.class);

	private static String CACHE_PEER_NAME = "SharedStateCacheManager";

	private static String CACHE_NAME = "ServiceStateRegistry";

	//private Map<ServiceStateKey, List<ServiceState>> serviceStates;
	
	//private Map<ServiceStateKey, List<ServiceState>> transientCache = new HashMap();
	
	private Object mutex = new Object();
	
	private Ehcache cache;
	

	public ServiceRegistry() {
		//this(false);
		initialize("registry-ehcache-jgroups-client.xml");
	}

//	public ServiceRegistry(boolean bootstrap) {
//		if (bootstrap)
//			initialize("registry-ehcache-jgroups-client.xml");
//		else initialize("registry-ehcache-jgroups-client.xml");
//	}

//	public ServiceRegistry(String filePath) {
//		initialize(filePath);
//	}

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
	
	protected ServiceStateKey createServiceStateKey(ServiceState serviceState, TransportType transportType) {
		return ServiceKeyUtil.createServiceStateKey(serviceState, transportType);
	}

	protected ServiceStateKey createServiceStateKey(ServiceNameKey serviceNameKey, TransportType transportType) {
		return ServiceKeyUtil.createServiceStateKey(serviceNameKey, transportType);
	}

	public static ServiceStateKey createServiceStateKey(String context, String version, String service, TransportType transportType) {
		return ServiceKeyUtil.createServiceStateKey(context, version, service, transportType);
	}


	public void printServiceStates() {
		List<ServiceState> serviceStates = getServiceStates();
		if (serviceStates.size() == 0) {
			//log.info("Available Services: NONE");
			System.out.println("Available Services: NONE");
		} else {
			//log.info("Available Services: ");
			System.out.println("Available Services: "+serviceStates.size());
			Iterator<ServiceState> iterator = serviceStates.iterator();
			while (iterator.hasNext()) {
				ServiceState serviceState = (ServiceState) iterator.next();
				//log.info("Service: "+serviceState.toString());
				System.out.println("Service: "+serviceState.toString());
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void refreshServiceState(ServiceState state, TransportType transportType) {
		synchronized (mutex) {
			try {
				//acquireWriteLock();
				logRefreshServiceStateStarted(state, transportType);
				
				//first, update service-states for specified transport
				Ehcache cache = getCache(CACHE_NAME);
				ServiceNameKey serviceName = getServiceNameKey(state);
				//if (serviceName.toString().contains("AsyncEchoCaller"))
				//	System.out.println();
				Element element = cache.get(serviceName);
				if (element == null) {
					Map<ServiceNameKey, Set<TransportType>> serviceTransportMap = new HashMap<ServiceNameKey, Set<TransportType>>();
					//System.out.println("-->"+serviceName);
					element = new Element(serviceName, serviceTransportMap);
					Set<TransportType> transports = new HashSet<TransportType>();
					serviceTransportMap.put(serviceName, transports);
					transports.add(transportType);
					cache.put(element);
				} else {
					//if (serviceName.toString().contains("AsyncEchoCaller"))
					//	System.out.println();
					@SuppressWarnings("unchecked") 
					Map<ServiceNameKey, Set<TransportType>> serviceTransportMap = (Map<ServiceNameKey, Set<TransportType>>) element.getValue();
					Set<TransportType> transports = serviceTransportMap.get(serviceName);
					//System.out.println(">>>"+serviceName);
					//if (transports == null)
					//	System.out.println();
					transports.add(transportType);
					cache.put(element);
				}
				
				List<ServiceState> serviceStates = null;
				//next, update transports for specified service
				ServiceStateKey serviceStateKey = createServiceStateKey(state, transportType);
				//ServiceTransportKey transportKey = new ServiceTransportKey(serviceId, transport);
				element = cache.get(serviceStateKey);
				if (element == null) {
					serviceStates = new ArrayList<ServiceState>();
					element = new Element(serviceStateKey, serviceStates);
				} else
					serviceStates = (List<ServiceState>) element.getValue();
				serviceStates.add(state);
				cache.put(element);

				//List keys = cache.getKeys();
				logRefreshServiceStateComplete(state, transportType);

			} catch (Exception e) {
				e.printStackTrace();
				logRefreshServiceStateAborted(state, transportType, e);
				
			} finally {
				//releaseWriteLock();
			}
		}
	}

	public ServiceState getServiceState(String context, String version, String service, TransportType transport) {
		Map<TransportType, List<ServiceState>> serviceStates = getServiceStates(context, version, service);
		List<ServiceState> list = serviceStates.get(transport);
		if (list.size() > 0)
			return list.get(0);
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	public Map<TransportType, List<ServiceState>> getServiceStates(String context, String version, String service) {
		Map<TransportType, List<ServiceState>> serviceStates = new HashMap<TransportType, List<ServiceState>>();
		
		synchronized (mutex) {
			try {
				//acquireReadLock();
				//log.debug("started");
				logGetServiceStatesStarted(service);
				
				//first, get list of available transports for specified service
				Ehcache cache = getCache(CACHE_NAME);
				ServiceNameKey serviceName = getServiceNameKey(context, version, "org.aries.userService");
				//if (serviceName.toString().contains("AsyncEchoCaller"))
				//	System.out.println();
				Map<ServiceNameKey, Set<TransportType>> availableServiceTransports = null;
				List keys = cache.getKeys();
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
							ServiceStateKey serviceStateKey = createServiceStateKey(context, version, "org.aries.userService", transport);
							element = cache.get(serviceStateKey);
							Assert.notNull(element, "Service-states for service-instance-ID not found: "+serviceStateKey);
							List<ServiceState> states = (List<ServiceState>) element.getValue();
							serviceStates.put(transport, states);
						}
					}
				}
				
				//return results
				logGetServiceStatesComplete(service);
				//log.debug("complete");
				return serviceStates;
			
			} catch (Exception e) {
				logGetServiceStatesAborted(service, e);
				//log.debug("aborted", e);
				return null;
	
			} finally {
				//releaseReadLock();
			}
		}
	}
	

	public List<ServiceState> getServiceStates(String context, String version, String service, TransportType transport) {
		synchronized (mutex) {
			try {
				//acquireReadLock();
				logGetServiceStatesStarted(service);
				//ServiceStateKey serviceStateKey = createServiceStateKey(context, version, service, transport);
				Map<TransportType, List<ServiceState>> serviceStateMap = getServiceStates(context, version, service);
				List<ServiceState> serviceStates = serviceStateMap.get(transport);
				logGetServiceStatesComplete(service);
				return serviceStates;
			} catch (Exception e) {
				logGetServiceStatesAborted(service, e);
				return null;
			} finally {
				//releaseReadLock();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ServiceState> getServiceStates(ServiceStateKey serviceStateKey) {
		List<ServiceState> serviceStates = null;
		Ehcache cache = getCache(CACHE_NAME);
		if (cache != null) {
			Element element = cache.get(serviceStateKey);
			if (element != null)
				serviceStates = (List<ServiceState>) element.getValue();
			return serviceStates;
		}

		//transientCache.get(serviceStateKey);
		return serviceStates;
	}

	@SuppressWarnings("unchecked")
	public List<ServiceState> getServiceStates() {
		List<ServiceState> serviceStates = new ArrayList<ServiceState>();
		Ehcache cache = getCache(CACHE_NAME);
		if (cache != null) {
			List<?> keys = cache.getKeys();
			Iterator<?> iterator = keys.iterator();
			while (iterator.hasNext()) {
				Object key = iterator.next();
				if (key instanceof ServiceStateKey) {
					ServiceStateKey serviceStateKey = (ServiceStateKey) key;
					Element element = cache.get(serviceStateKey);
					if (element != null) {
						List<ServiceState> list = (List<ServiceState>) element.getValue();
						serviceStates.addAll(list);
					}
				}
			}
		}

		//transientCache.get(serviceStateKey);
		return serviceStates;
	}

	
	protected void logGetServiceStatesStarted(String serviceId) {
		//EventLog.getInstance().trace("GetServiceStates Started: "+serviceId);
		log.info("GetServiceStates Started: "+serviceId);
	}

	protected void logGetServiceStatesComplete(String serviceId) {
		//EventLog.getInstance().trace("GetServiceStates Complete: "+serviceId);
		log.info("GetServiceStates Complete: "+serviceId);
	}

	protected void logGetServiceStatesAborted(String serviceId, Exception e) {
		//EventLog.getInstance().trace("GetServiceStates Aborted: "+serviceId+", "+e.getMessage());
		log.info("GetServiceStates Aborted: "+serviceId+", "+e.getMessage());
	}

	protected void logRefreshServiceStateStarted(ServiceState state, TransportType transport) {
		//EventLog.getInstance().trace("RefreshServiceState Started: "+state.getServiceId()+", "+transport);
		log.info("RefreshServiceState Started: "+state.getServiceId()+", "+transport);
	}

	protected void logRefreshServiceStateComplete(ServiceState state, TransportType transport) {
		//EventLog.getInstance().trace("RefreshServiceState Complete: "+state.getServiceId()+", "+transport);
		log.info("RefreshServiceState Complete: "+state.getServiceId()+", "+transport);
	}

	protected void logRefreshServiceStateAborted(ServiceState state, TransportType transport, Exception e) {
		//EventLog.getInstance().trace("RefreshServiceState Aborted: "+state.getServiceId()+", "+transport+", "+e.getMessage());
		log.info("RefreshServiceState Aborted: "+state.getServiceId()+", "+transport+", "+e.getMessage());
	}

}
