package org.aries.tx.service;

import java.net.BindException;

import nam.model.HttpTransport;
import nam.model.Role2;
import nam.model.Service;
import nam.model.TransportType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.launcher.ServiceLauncher;
import org.aries.launcher.jaxws.JAXWSLauncher;
import org.aries.service.ServiceFactory;
import org.aries.service.ServiceFactoryImpl;
import org.aries.tx.util.EndpointDescriptor;
import org.aries.util.ExceptionUtil;

import tx.manager.registry.ServiceRegistry;


public abstract class AbstractServiceInitializer {

	public abstract void initialize(String hostName, int httpPort);
	
	protected Log log = LogFactory.getLog(getClass());
	
	private ServiceLauncher launcher;

	
	public Runnable createStartupRunner(final String hostName, final int httpPort) {
		return new Runnable() {
			public void run() {
				try {
					initialize(hostName, httpPort);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		};
	}

	public void initialize(EndpointDescriptor descriptor, boolean launch) {
		if (launch) {
			launch(descriptor);
		} else {
			register(descriptor);
		}
	}

	//TODO specify what exactly needs to be "set" in the given endpoint
	public String register(EndpointDescriptor endpoint) {
		//WSCEnvironmentBean wscEnvironmentBean = XTSPropertyManager.getWSCEnvironmentBean();
		//String bindAddress = wscEnvironmentBean.getBindAddress11();
		//int bindPort = wscEnvironmentBean.getBindPort11();
		//int secureBindPort = wscEnvironmentBean.getBindPortSecure11();

		String bindAddress = null;
		if (bindAddress == null)
			bindAddress = endpoint.getBindAddress();

		int bindPort = 0;
		if (bindPort == 0)
			bindPort = endpoint.getBindPort();

		int secureBindPort = 0;
		if (secureBindPort == 0)
			secureBindPort = endpoint.getBindPortSSL();

		String serviceName = endpoint.getServiceName();
		String serviceContext = endpoint.getContext();
		if (serviceContext == null)
			serviceContext = "/" + serviceName;
		String portTypeName = endpoint.getPortTypeName();

		String baseUri = "http://" +  bindAddress + ":" + bindPort;
		String uri = baseUri + serviceContext + "/" + serviceName + "/" + portTypeName;

		String secureBaseUri = "https://" + bindAddress + ":" + secureBindPort;
		String secureUri = secureBaseUri + serviceContext + "/" + serviceName + "/" + portTypeName;

		ServiceRegistry serviceRegistry = ServiceRegistry.getInstance();
		serviceRegistry.registerServiceProvider(bindAddress, new Integer(bindPort), serviceName, uri);
		//TODO serviceRegistry.registerSecureServiceProvider(serviceName, secureUri);
		log.info("SERVICE REGISTERED: "+uri);
		return uri;
	}		
		
	//TODO specify what exactly needs to be "set" in the given endpoint
	public ServiceLauncher launch(EndpointDescriptor endpoint) {
		String uri = register(endpoint);

		//TODO give Service: serviceName and portTypeName
		Service service = new Service();
		service.setName(endpoint.getPortTypeName());
		service.setDescription(endpoint.getDescription());
		service.setNamespace(endpoint.getNamespace());
		service.setPackageName(endpoint.getPackageName());
		service.setInterfaceName(endpoint.getInterfaceClass());
		service.setClassName(endpoint.getImplementationClass());
		//TODO Do we need to "assure" the service here?
		
		launcher = launchAsWebService(service, uri);
		log.info("SERVICE READY: "+uri);
		log.info("Launched: "+uri);
		return launcher;
	}
	
    protected ServiceLauncher launchAsWebService(Service service, String uri) {
		HttpTransport httpTransport = createHttpTransport(service.getName(), uri);
		Role2 role = createRole(httpTransport);
		JAXWSLauncher launcher = null;
		
		try {
			String serviceId = "";
			if (service.getDomain() != null)
				serviceId = service.getDomain() + ".";
			serviceId += service.getName();
			String className = service.getClassName();
			ServiceFactory serviceFactory = new ServiceFactoryImpl();
			Object serviceInstance = serviceFactory.createServiceInstance(serviceId, className);
			launcher = new JAXWSLauncher(httpTransport);
			launcher.setAddress(uri);
			launcher.setServiceId(serviceId);
			launcher.setInstance(serviceInstance);
			launcher.setChannel(role);
			launcher.launch();
			return launcher;
			
		} catch (Throwable e) {
			//TODO handle these properly
			Exception cause = ExceptionUtil.getRootCause(e);
			if (cause instanceof BindException && cause.getMessage().startsWith("Address already in use"))
				return launcher;
			log.error("Error", e);
			return launcher;
		}
	}

	protected Role2 createRole(HttpTransport httpTransport) {
    	Role2 role = new Role2();
		role.setName("service");
		role.getTransports().add(httpTransport);
		return role;
	}

	protected HttpTransport createHttpTransport(String name, String uri) {
		HttpTransport httpTransport = new HttpTransport();
		httpTransport.setName(name);
		httpTransport.setUrl(uri);
		httpTransport.setType(TransportType.HTTP);
		return httpTransport;
	}

    public void shutdown() {
    	//add JMX shutdown command here
    	launcher.shutdown();
    }

}
