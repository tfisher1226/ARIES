package org.aries.launcher;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nam.model.Application;
import nam.model.Channel;
import nam.model.HttpTransport;
import nam.model.Interactor;
import nam.model.JmsTransport;
import nam.model.Listener;
import nam.model.Operation;
import nam.model.Project;
import nam.model.Provider;
import nam.model.RmiTransport;
import nam.model.Role2;
import nam.model.Service;
import nam.model.Transport;
import nam.model.util.ApplicationUtil;
import nam.model.util.MessagingUtil;
import nam.model.util.ProjectUtil;
import nam.model.util.ServiceUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.launcher.jaxws.JAXWSLauncher;
import org.aries.launcher.jms.JMSLauncher;
import org.aries.launcher.rmi.RMILauncher;
import org.aries.registry.ServiceRegistry;
import org.aries.runtime.BeanContext;

import aries.generation.engine.GenerationContext;


public class Launcher {

	private static Log log = LogFactory.getLog(Launcher.class);

	private GenerationContext context;
	
	private Initializer initializer;
	
	private Collection<Project> projects;

	private Map<String, RMILauncher> rmiLaunchers;

	private Map<String, JMSLauncher> jmsLaunchers;

	private Map<String, JAXWSLauncher> httpLaunchers;
	
	private String address;

	private String moduleId;
	

	public Launcher(String host, String port) throws Exception {
		this(host, Integer.parseInt(port));
	}
	
	public Launcher(String host, int port) throws Exception {
		rmiLaunchers = new HashMap<String, RMILauncher>();
		jmsLaunchers = new HashMap<String, JMSLauncher>();
		httpLaunchers = new HashMap<String, JAXWSLauncher>();
		initializer = new Initializer(host, port);
		address = "http://"+host+":"+port;
	}

	public String getModuleName() {
		return moduleId;
	}

//	public String getWorkingLocation() {
//		return initializer.getWorkingLocation();
//	}
//
//	public void setWorkingLocation(String workingLocation) {
//		initializer.setWorkingLocation(workingLocation);
//	}
//
//	public String getPropertyLocation() {
//		return initializer.getPropertyLocation();
//	}
//
//	public void setPropertyLocation(String propertyLocation) {
//		initializer.setPropertyLocation(propertyLocation);
//	}
//
//	public String getRuntimeHome() {
//		return initializer.getApplicationHome();
//	}
//
//	public void setRuntimeHome(String runtimeHome) {
//		Bootstrap.INSTANCE.setApplicationHome(runtimeHome);
//		initializer.setApplicationHome(runtimeHome);
//	}

//	public void initialize(String sourceFile) throws Exception {
//		initialize(sourceFile, null);
//	}
	
	public void initialize(String sourceFile, String domainId, String applicationId, String moduleId, boolean isServiceSide) throws Exception {
		if (sourceFile != null)
			log.info("Initializing using: "+sourceFile);
		initializer.setDomainId(domainId);
		initializer.setApplicationId(applicationId);
		initializer.setModuleId(moduleId);
		this.moduleId = moduleId;
		if (sourceFile != null)
			projects = initializer.initializeModel(sourceFile, isServiceSide);
		context = initializer.getContext();
	}

	public void launch() throws Exception {
		if (projects != null) {
			Iterator<Project> iterator = projects.iterator();
			while (iterator.hasNext()) {
				Project project = iterator.next();
				launchProject(project);
			}
		}
	}

	public void launchProject(Project project) throws Exception {
		Collection<Application> applications = ProjectUtil.getApplications(project);
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			launchApplication(application);
			log.info("APPLICATION READY: "+application.getName());
		}
	}

	public void launchApplication(Application application) throws Exception {
		Collection<Service> services = ApplicationUtil.getServices(application);
		Iterator<Service> iterator = services.iterator(); 
		while (iterator.hasNext()) {
			Service service = iterator.next();
			String serviceName = service.getName();
			String domainName = service.getDomain();
			String serviceId = domainName + "." + serviceName;
			
//			String qualifiedName = service.getPackageName() + "." + service.getInterfaceName();
//			if (ClassUtil.classExists(qualifiedName + "ListenerForRMI")) {
//				ServiceFactory serviceFactory = BeanContext.get("org.aries.serviceFactory");
//				Object serviceInstance = serviceFactory.createServiceInstance(serviceId, qualifiedName + "ListenerForRMI");
//				String portNumber = initializer.getProperty("aries.port.rmi");      
//				Remote stub = UnicastRemoteObject.exportObject((Remote) serviceInstance, Integer.parseInt(portNumber));
//				RMIServiceRegistry.register(Integer.parseInt(portNumber), serviceId, stub);
//			}
			
			//TODO add this when ready for service states
			//TODO this below needs a serviceInctance to exist
			//TODO initializer.initializeServiceRegistery(service, serviceId);
			
//			Object serviceInstance = BeanContext.get(serviceId);
//			if (serviceInstance != null) {
//				initializer.initializeService(service, serviceId);
//				List<Listener> listeners = ServiceUtil.getListeners(service);
//				launchService(serviceName, serviceId, serviceInstance, listeners);
//			}
//			List<Listener> listeners = ServiceUtil.getListeners(service);
//			launchServices(service, listeners);
		}
	}

	public void launchServices(Service service, List<Listener> listeners) throws Exception {
		List<Operation> operations = ServiceUtil.getOperations(service);		
		Iterator<Operation> iterator = operations.iterator();
		String serviceName = service.getName();
		String domainName = service.getDomain();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			String operationName = operation.getName();
			String serviceId = domainName + "." + serviceName + "." + operationName;
			Object serviceInstance = BeanContext.get(serviceId);
			if (serviceInstance != null) {
				initializer.initializeService(service, serviceId);
				launchService(operationName, serviceId, serviceInstance, listeners);
			}
		}
	}
	
	public void launchService(String serviceName, String serviceId, Object serviceInstance, List<Listener> listeners) throws Exception {
		Project project = context.getProject();
		Iterator<Listener> iterator = listeners.iterator();
		while (iterator.hasNext()) {
			Interactor listener = iterator.next();
			String roleName = listener.getRole();
			
			String channelName = listener.getChannel();
			Channel channel = context.getChannelByName(channelName);
			Role2 role = MessagingUtil.getRole(project, channel, roleName);
			Assert.notNull(role, "Role not found");

			//TODO Launch these ONLY if NOT running in app server
			//TODO make this optional based on configuration (i.e. when designer wants this type of architecture..)
			Collection<RmiTransport> rmiTransports = MessagingUtil.getRMITransports(project, channel, role.getName());
			//launchService(serviceId, serviceInstance, role, rmiTransports);
			Iterator<RmiTransport> iterator2 = rmiTransports.iterator();
			while (iterator2.hasNext()) {
				RmiTransport rmiTransport = iterator2.next();
				//System.out.println();
			}

			//TODO Launch these ONLY if NOT running in app server
			//Collection<HttpTransport> httpTransports = MessagingUtil.getHTTPTransports(project, channel, role.getName());
			//Collection<JmsTransport> jmsTransports = MessagingUtil.getJMSTransports(project, channel, role.getName());
			//launchService(serviceId, serviceInstance, role, httpTransports);
			//launchService(serviceId, serviceInstance, role, jmsTransports);
			
			
//			List<Receiver> receivers = ChannelUtil.getReceivers(channel);
//			Iterator<Receiver> receiverIterator = receivers.iterator();
//			while (receiverIterator.hasNext()) {
//				Receiver receiver = receiverIterator.next();
//				String receiverLink = receiver.getLink();
//				if (StringUtils.isEmpty(receiverLink))
//					receiverLink = channel.getReceiveLink();
//				Assert.notNull(receiverLink, "Link not found for receiver: "+receiver.getName());
//				String listenerLinkName = receiver.getLink();
//				String listenerRoleName = role;
//				Link listenerLink = null;
//				Role listenerRole = null;
//				
//				List<Link> links = MessagingUtil.getLinks(context.getProject());
//				Iterator<Link> linkIterator = links.iterator();
//				while (linkIterator.hasNext()) {
//					Link link = linkIterator.next();
//					if (link.getName().equals(listenerLinkName)) {
//						listenerLink = link;
//						break;
//					}
//				}
//				
//				Assert.notNull(listenerLink, "Listener link not found");
//				List<Role> roles = LinkUtil.getRoles(listenerLink);
//				Iterator<Role> roleIterator = roles.iterator();
//				while (roleIterator.hasNext()) {
//					Role role = roleIterator.next();
//					if (role.getName().equals(listenerRoleName)) {
//						listenerRole = role;
//						break;
//					}
//				}
//				
//				Assert.notNull(listenerRole, "Listener role not found");
//				//String defaultTransport = listenerRole.getDefaultTransport();
//				//Assert.notNull(transport, "Transport not found for role: "+listenerRole.getName());
//
//				List<Transport> transportsFromLink = LinkUtil.getTransports(listenerLink);
//				Iterator<Transport> transportIterator = transportsFromLink.iterator();
//				while (transportIterator.hasNext()) {
//					Transport transport = transportIterator.next();
//					String transportRef = transport.getRef();
//					if (transportRef != null)
//						transport = MessagingUtil.getTransportByName(context.getProject(), transportRef);
//					Assert.notNull(transport, "Transport reference not found: "+transportRef);
//					TransportType transportType = TransportUtil.getTransportType(transport);
//				
//					switch (transportType) {
//					case RMI:
//						RmiTransport rmiTransport = (RmiTransport) transport;
//						RMILauncher launcher = launchAsRMIService(rmiTransport, serviceId, serviceInstance, listenerRole);
//						rmiLaunchers.put(serviceId, launcher);
//						break;
//	
//					case EJB: 
//						break;
//						
//					case HTTP: 
//						HttpTransport httpTransport = (HttpTransport) transport;
//						httpTransport.setUrl(address+"/"+serviceName+"Service/"+serviceName);
//						//httpTransport.setUrl(address+"/tx-service/"+service.getName()+"Service/"+service.getName());
//						JAXWSLauncher jaxwsLauncher = launchAsWebService(httpTransport, serviceId, serviceInstance, listenerRole);
//						httpLaunchers.put(serviceId, jaxwsLauncher);
//						break;
//						
//					case JMS: 
//						JmsTransport jmsTransport = (JmsTransport) transport;
//						JMSLauncher jmsLauncher = launchUsingJMS(jmsTransport, serviceId, serviceInstance, listenerRole);
//						jmsLaunchers.put(serviceId, jmsLauncher);
//						break;
//					}
//				}
//			}
		}
	}

	public <T extends Transport> void launchService(String serviceId, Object serviceInstance, Role2 role, Collection<T> transports) throws Exception {
		Iterator<T> iterator = transports.iterator();
		while (iterator.hasNext()) {
			T transport = iterator.next();
			switch (transport.getType()) {
			case RMI: launchAsRMIService((RmiTransport) transport, serviceId, serviceInstance, role); break;
			case HTTP: launchAsJAXWSService((HttpTransport) transport, serviceId, serviceInstance, role); break;
			case JMS: launchAsJMSService((JmsTransport) transport, serviceId, serviceInstance, role); break;
			}
		}
	}

	public RMILauncher launchAsRMIService(RmiTransport rmiTransport, String serviceId, Object instance, Role2 role) throws Exception {
		try {
			RMILauncher launcher = new RMILauncher(rmiTransport);
			rmiLaunchers.put(serviceId, launcher);
			launcher.setServiceId(serviceId);
			//launcher.setAddress(rmiTransport.getUrl());
			launcher.setPort(rmiTransport.getPort());
			launcher.setServiceInstance(instance);
			//launcher.setChannel(role);
			launcher.launch();
			return launcher;
			
		} catch (Exception e) {
			log.error("Error", e);
		}
		return null;
	}
	
	public JAXWSLauncher launchAsJAXWSService(HttpTransport httpTransport, String serviceId, Object instance, Role2 role) throws Exception {
		try {
			JAXWSLauncher launcher = new JAXWSLauncher(httpTransport);
			launcher.setAddress(httpTransport.getUrl());
			launcher.setServiceId(serviceId);
			launcher.setInstance(instance);
			launcher.setChannel(role);
			launcher.launch();
			return launcher;
			
		} catch (Exception e) {
			log.error("Error", e);
		}
		return null;
	}

	public JMSLauncher launchAsJMSService(JmsTransport transport, String serviceId, Object instance, Role2 role) throws Exception {
		Provider provider = context.getProviderByName(transport.getProvider());
		try {
			JMSLauncher launcher = new JMSLauncher(transport);
			launcher.setServiceId(serviceId);
			launcher.setProvider(provider);
			launcher.setChannel(role);
			launcher.launch();
			return launcher;
			
		} catch (Exception e) {
			log.error("Error", e);
		}
		return null;
	}

	public void logStatus() throws Exception {
		ServiceRegistry serviceRegistry = (ServiceRegistry) BeanContext.get("org.aries.serviceRegistry");
		serviceRegistry.printServiceStates();
	}

	public void shutdown() {
		shutdownJAXWSAdapters();
		shutdownJMSAdapters();
		shutdownBeans();
	}

	protected void shutdownJAXWSAdapters() {
		Iterator<JAXWSLauncher> iterator = httpLaunchers.values().iterator();
		while (iterator.hasNext()) {
			JAXWSLauncher launcher = iterator.next();
			try {
				launcher.shutdown();
			} catch (Throwable e) {
				log.error("Cannot shutdown JAXWS web-service: "+launcher.getServiceId(), e);
			}
		}
	}

	protected void shutdownJMSAdapters() {
		Iterator<JMSLauncher> iterator = jmsLaunchers.values().iterator();
		while (iterator.hasNext()) {
			JMSLauncher launcher = iterator.next();
			try {
				launcher.shutdown();
			} catch (Throwable e) {
				log.error("Cannot shutdown JMS service: "+launcher.getServiceId(), e);
			}
		}
	}

	protected void shutdownBeans() {
		//ServiceRegistry serviceRegistry = BeanContext.get("org.aries.serviceRegistry");
		//ProcessRegistry processRegistry = BeanContext.get("org.aries.processRegistry");
		//if (serviceRegistry != null)
		//	serviceRegistry.shutdown();
		//if (processRegistry != null)
		//	processRegistry.shutdown();
	}

}
