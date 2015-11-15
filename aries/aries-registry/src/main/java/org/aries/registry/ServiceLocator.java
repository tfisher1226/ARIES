package org.aries.registry;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import nam.model.Channel;
import nam.model.Interactor;
import nam.model.Role2;
import nam.model.Sender;
import nam.model.Service;
import nam.model.util.ChannelUtil;
import nam.model.util.MessagingUtil;
import nam.model.util.ServiceUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.nam.model.old.OperationDescripter;
import org.aries.runtime.BeanContext;
import org.aries.service.ServiceProxy;
import org.aries.service.ServiceRepository;
import org.aries.service.registry.ServiceKeyUtil;
import org.aries.service.registry.ServiceProxyKey;
import org.aries.service.registry.ServiceState;
import org.aries.transport.TransportType;
import org.aries.util.ReflectionUtil;


//@Startup
//@Scope(ScopeType.APPLICATION)
//@Name("org.aries.serviceLocator")

//@BypassInterceptors
@SuppressWarnings("serial")
public class ServiceLocator implements Serializable {

	private static Log log = LogFactory.getLog(ServiceLocator.class);
	
	@Inject
	private ServiceBalancer serviceBalancer;

	@Inject
	private ServiceProxyFactory serviceProxyFactory;

	private Map<ServiceProxyKey, ServiceProxy> proxyCache = new HashMap<ServiceProxyKey, ServiceProxy>();


	public ServiceLocator() {
		//do nothing
	}
	
//	public void setTransport(Transport transport) {
//		this.transport = transport;
//	}
//
//	public void setTransport(String transport) {
//		this.transport = Transport.getTransport(transport);
//	}

	public ServiceRepository getServiceRepository() {
		return BeanContext.get("org.aries.serviceRepository");
	}
	
	public ServiceRegistry getServiceRegistry() {
		return BeanContext.get("org.aries.serviceRegistry");
	}

	public LinkStateRegistry getLinkStateRegistry() {
		return BeanContext.get("org.aries.linkStateRegistry");
	}
	
    protected OperationDescripter getOperation() {
    	Method callingMethod = ReflectionUtil.getCallingMethod();
		OperationDescripter operationDescripter = ServiceRegistryMapper.createOperationDescripter(callingMethod);
		return operationDescripter;
	}
    
	//TODO THIS IS TMP FOR NOW - revisit the entire API again
    public synchronized ServiceProxy lookup(String serviceId, String version) {
    	ServiceProxy serviceProxy = lookup(null, version, serviceId, getOperation(), (TransportType) null);  
    	return serviceProxy;
	}

	//TODO THIS IS TMP FOR NOW - revisit the entire API again
    public synchronized ServiceProxy lookup(String serviceId, String version, TransportType transport) {
    	ServiceProxy serviceProxy = lookup(null, version, serviceId, getOperation(), transport); 
    	return serviceProxy;
	}

    public synchronized ServiceProxy lookup(String serviceId, String version, String PLACEHOLDER, TransportType transport) {
    	ServiceProxy serviceProxy = lookup(null, version, serviceId, getOperation(), transport); 
    	return serviceProxy;
	}

    public synchronized ServiceProxy lookup(String context, String version, String serviceId, String PLACEHOLDER, TransportType transport) {
    	ServiceProxy serviceProxy = lookup(context, version, serviceId, getOperation(), transport);
    	return serviceProxy;
	}

	public synchronized ServiceProxy lookup(String context, String version, String serviceId, String PLACEHOLDER, String transport) {
    	return lookup(context, version, serviceId, getOperation(), TransportType.valueOf(transport));
    }

	public synchronized ServiceProxy lookup(String context, String version, String serviceId, OperationDescripter operation, String transport) {
    	return lookup(context, version, serviceId, operation, TransportType.valueOf(transport));
    }

    @SuppressWarnings("unused")
	public synchronized ServiceProxy lookup(String context, String version, String serviceId, OperationDescripter operation, TransportType transport) {
    	ServiceRepository serviceRepository = getServiceRepository();
    	ServiceRegistry serviceRegistry = getServiceRegistry();
    	LinkStateRegistry linkStateRegistry = getLinkStateRegistry();
    	
		//Assert.notNull(context, "Context must be specified");
    	Assert.notNull(version, "Version must be specified");
    	Assert.notNull(serviceId, "ServiceID must be specified");
    	Assert.notNull(operation, "Operation must be specified");

    	try {
	    	ServiceProxy serviceProxy = null;
			logLocateServiceProxyStarted(serviceId, operation, transport);
			
			Object policy = null;
			if (policy != null) {
	    		//TODO get transport from policy configuration
	        	Map<TransportType, List<ServiceState>> serviceStates = serviceRegistry.getServiceStates(context, version, serviceId);
	    		Assert.isTrue(serviceStates != null && serviceStates.size() > 0, "Service not available: "+serviceId);
	    		//TODO Assert.isTrue(serviceStates != null && serviceStates.size() > 0, "Service not available: "+context+"/"+service);
	    		serviceProxy = getServiceProxy(serviceStates, context, serviceId, operation);
	    		
			} else {
				//if (transport == null)
				//	transport = Transport.WS;
				
				if (transport == null) {
					log.info("********************** "+serviceRepository);
					log.info("********************** "+serviceRepository.getServiceDescripters().size()+" services available");
		        	//linkStateRegistry.getLinkState(name, type);
					Service service = (Service) serviceRepository.getServiceDescripter(serviceId);
					log.info("********************** "+serviceId+", "+service);
					Interactor listener = ServiceUtil.getListeners(service).get(0);
					String roleName = listener.getRole();
					
					//TODO - this should not be null
					//TODO - this should be "assured" at startup
					if (listener.getLink() == null) {
						String channelName = listener.getChannel();
						Channel channel = MessagingUtil.getChannelByName(service, channelName);
						Assert.notNull(channel, "Channel not found: "+channelName);
						Sender sender = ChannelUtil.getSenderByName(channel, roleName);
						if (sender == null)
							sender = ChannelUtil.getSenderByName(channel, "*");
						Assert.notNull(sender, "Sender not found: channel="+channelName+", role="+roleName);
						listener.setLink(sender.getLink());
					}
					
					log.info("********************** "+listener);
					Role2 role = linkStateRegistry.getRole(listener.getLink(), roleName);
					Assert.notNull(role, "Link/role not found for service: "+serviceId);
					String defaultTransport = role.getDefaultTransport();
					log.info("********************** "+defaultTransport);
					if (defaultTransport != null)
						transport = TransportType.valueOf(defaultTransport.toUpperCase());
				}
				
				if (transport != null) {
					serviceProxy = attemptLookup(context, version, serviceId, operation, transport);
					
				} else {
					//resort to best effort lookup
					if (serviceProxy == null)
						serviceProxy = attemptLookup(context, version, serviceId, operation, TransportType.RMI);
					if (serviceProxy == null)
						serviceProxy = attemptLookup(context, version, serviceId, operation, TransportType.EJB);
					if (serviceProxy == null)
						serviceProxy = attemptLookup(context, version, serviceId, operation, TransportType.HTTP);
					if (serviceProxy == null)
						serviceProxy = attemptLookup(context, version, serviceId, operation, TransportType.JMS);
				}
			}
			
			Assert.notNull(serviceProxy, "Service not available: "+serviceId+"/"+version+"/"+transport);
	    	logLocateServiceProxyComplete(serviceId, operation, transport);
			return serviceProxy;
			
    	} catch (Exception e) {
	    	logLocateServiceProxyAborted(serviceId, operation, transport, e);
	    	throw new RuntimeException(e);
    	}
    }

	public ServiceProxy attemptLookup(String context, String version, String serviceId, OperationDescripter operation, TransportType transport) throws Exception {
		List<ServiceState> serviceStates = getServiceRegistry().getServiceStates(context, version, serviceId, transport);
		if (serviceStates != null && serviceStates.size() > 0) {
			ServiceProxy serviceProxy = getServiceProxy(serviceStates, operation, transport);
			return serviceProxy;
		}
		return null;
	}
	
	protected ServiceProxy getServiceProxy(Map<TransportType, List<ServiceState>> serviceStatesByTransport, String context, String serviceId, OperationDescripter operation) throws Exception {
		List<TransportType> transports = getTransportsByPriority(context, serviceId);
		Iterator<TransportType> iterator = transports.iterator();
		while (iterator.hasNext()) {
			TransportType transport = (TransportType) iterator.next();
			List<ServiceState> serviceStates = serviceStatesByTransport.get(transport);
    		if (serviceStates != null && serviceStates.size() > 0) {
    			ServiceProxy serviceProxy = getServiceProxy(serviceStates, operation, transport);
    			return serviceProxy;
    		}
		}
		
		return null;
	}

    protected List<TransportType> getTransportsByPriority(String context, String service) {
    	List<TransportType> transports = new ArrayList<TransportType>();
		//TODO apply transport selection rules here
    	//TODO externalize these rules into drools
    	//TODO and make them dynamically loadable
    	transports.add(TransportType.JMS);
    	transports.add(TransportType.RMI);
    	transports.add(TransportType.HTTP);
		return transports;
	}

	//assuming serviceStates exist
	//assuming transport not null because already checked in public method
	protected ServiceProxy getServiceProxy(List<ServiceState> serviceStates, OperationDescripter operation, TransportType transport) throws Exception {
		//TODO apply load balancing scheme here
		ServiceState serviceState = selectService(serviceStates);
		ServiceProxy serviceProxy = getServiceProxy(serviceState, operation, transport);
		return serviceProxy;
	}

	//assuming service states exist
	protected ServiceState selectService(List<ServiceState> serviceStates) {
		ServiceState serviceState = null;
		if (serviceBalancer != null)
			serviceState = serviceBalancer.selectService(serviceStates);
		else serviceState = serviceStates.get(0);
		return serviceState;
	}

	protected ServiceProxy getServiceProxy(ServiceState serviceState, OperationDescripter operation, TransportType transport) throws Exception {
		synchronized (proxyCache) {
			ServiceProxyKey serviceProxyKey = ServiceKeyUtil.createServiceProxyKey(serviceState, operation, transport);
			ServiceProxy serviceProxy = proxyCache.get(serviceProxyKey);
			if (serviceProxy == null) {
				if (serviceProxyFactory == null)
					serviceProxyFactory = BeanContext.get("org.aries.serviceProxyFactory");
				
				log.info("CreateServiceProxy started: "+serviceState.getServiceURL()+", "+operation.getName()+", "+transport);
				serviceProxy = serviceProxyFactory.createServiceProxy(serviceState, operation, transport);
				log.info("CreateServiceProxy complete: "+serviceState.getServiceURL()+", "+operation.getName()+", "+transport);
				proxyCache.put(serviceProxyKey, serviceProxy);
			}
			return serviceProxy;
		}
	}

	
	protected void logLocateServiceProxyStarted(String serviceId, OperationDescripter operation, TransportType transport) {
		//EventLog.getInstance().trace("LocateServiceProxy Started: "+serviceId+", "+operation.getName()+", "+transport);
		log.info("LocateServiceProxy Started: "+serviceId+", "+operation.getName()+", "+transport);
	}

	protected void logLocateServiceProxyComplete(String serviceId, OperationDescripter operation, TransportType transport) {
		//EventLog.getInstance().trace("LocateServiceProxy Complete: "+serviceId+", "+operation.getName()+", "+transport);
		log.info("LocateServiceProxy Complete: "+serviceId+", "+operation.getName()+", "+transport);
	}

	protected void logLocateServiceProxyAborted(String serviceId, OperationDescripter operation, TransportType transport, Exception e) {
		//EventLog.getInstance().trace("LocateServiceProxy aborted: "+serviceId+", "+operation.getName()+", "+transport+", "+e.getMessage());
		log.info("LocateServiceProxy aborted: "+serviceId+", "+operation.getName()+", "+transport+", "+e.getMessage());
	}

}
