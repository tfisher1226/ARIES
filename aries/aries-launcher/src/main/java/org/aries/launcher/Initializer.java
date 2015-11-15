package org.aries.launcher;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.naming.InitialContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;

import nam.ProjectLevelHelper;
import nam.client.ClientLayerHelper;
import nam.model.Application;
import nam.model.Callback;
import nam.model.Channel;
import nam.model.EjbTransport;
import nam.model.Element;
import nam.model.HttpTransport;
import nam.model.Interactor;
import nam.model.JmsTransport;
import nam.model.Link;
import nam.model.Listener;
import nam.model.Module;
import nam.model.ModuleType;
import nam.model.Namespace;
import nam.model.Operation;
import nam.model.Process;
import nam.model.Project;
import nam.model.Provider;
import nam.model.Receiver;
import nam.model.RmiTransport;
import nam.model.Role2;
import nam.model.Sender;
import nam.model.Service;
import nam.model.Transport;
import nam.model.Type;
import nam.model.util.ApplicationUtil;
import nam.model.util.ChannelUtil;
import nam.model.util.LinkUtil;
import nam.model.util.MessagingUtil;
import nam.model.util.ModuleUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.ProjectUtil;
import nam.model.util.ServiceUtil;
import nam.model.util.TypeUtil;
import nam.runtime.ProviderCache;
import nam.service.ServiceLayerHelper;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.ProjectBuilder;
import org.aries.bean.ProxyLocator;
import org.aries.client.AbstractDelegate;
import org.aries.ejb.EJBEndpointContext;
import org.aries.ejb.EJBEndpointMap;
import org.aries.jndi.JndiContext;
import org.aries.jndi.JndiDescripter;
import org.aries.jndi.JndiProxy;
import org.aries.nam.model.old.ApplicationProfile;
import org.aries.nam.model.old.ChannelDescripter;
import org.aries.nam.model.old.NamespaceDefinition;
import org.aries.nam.model.old.OperationDescripter;
import org.aries.nam.model.old.ProcessDefinition;
import org.aries.node.NamespaceContext;
import org.aries.registry.LinkStateRegistry;
import org.aries.registry.ServiceRegistry;
import org.aries.registry.ServiceRegistryMapper;
import org.aries.rmi.RMIEndpointContext;
import org.aries.runtime.BeanContext;
import org.aries.service.ServiceFactory;
import org.aries.service.ServiceRepository;
import org.aries.service.registry.ServiceState;
import org.aries.transport.TransferMode;
import org.aries.transport.TransportType;
import org.aries.util.ClassUtil;
import org.aries.util.FieldUtil;
import org.aries.util.NameUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.properties.PropertyManager;
import org.eclipse.bpel.model.PartnerLink;
import org.eclipse.bpel.model.partnerlinktype.PartnerLinkType;
import org.eclipse.wst.wsdl.PortType;

import aries.codegen.util.JMSUtil;
import aries.generation.engine.GenerationContext;

import com.sun.xml.bind.v2.runtime.JAXBContextImpl;
import com.sun.xml.bind.v2.runtime.JaxBeanInfo;


public class Initializer {

	private static Log log = LogFactory.getLog(Initializer.class);
	
	protected static String USER_DIR = System.getProperty("user.dir");

	protected static String MODEL_LOCATION = USER_DIR + "/src/main/resources";
	
	protected static String PROPERTY_LOCATION = "c:/workspace/ARIES/aries/properties";
	
	//protected static String PROPERTY_LOCATION = USER_DIR + "/properties";

	protected static String WORKSPACE_LOCATION = "c:/workspace/ARIES";
	
	//protected static String WORKSPACE_LOCATION = FilenameUtils.getFullPath(USER_DIR);
	
	protected static String RUNTIME_LOCATION = USER_DIR + "/target/runtime";
	
	
	private String host;

	private int port;

	private String domainId;
	
	private String applicationId;
	
	private String moduleId;

	private GenerationContext context;


	public Initializer() throws Exception {
		//default do nothing
	}
	
	public Initializer(String host, String port) throws Exception {
		this(host, Integer.parseInt(port));
	}

	public Initializer(String host, int port) throws Exception {
		this.host = host;
		this.port = port;
	}
	
	//This is used for loading models at runtime - generating at runtime
	public Initializer(String domainId, String applicationId, String moduleId) {
		this.domainId = domainId;
		this.applicationId = applicationId;
		this.moduleId = moduleId;
	}


	
//	public void initializePropertyManager() throws Exception {
//		Assert.notNull(applicationHome, "Application runtime location must be specified");
//		applicationHome = normalizePath(applicationHome);
//		propertyLocation = applicationHome + File.separator + "properties";
//		runtimeLocation = applicationHome + File.separator + ".runtime";
//		//propertyLocation = normalizePath(propertyLocation);
//		//runtimeLocation = normalizePath(runtimeLocation);
//		workingLocation = normalizePath(workingLocation);
//
//		BeanContext.set("org.aries.propertyManager", new PropertyManager());
//		BeanContext.set("org.aries.propertyInitializer", new PropertyInitializer());
//		PropertyManager propertyManager = BeanContext.get("org.aries.propertyManager");
//		PropertyInitializer propertyInitializer = BeanContext.get("org.aries.propertyInitializer");
//		propertyManager.setGlobalPropertyLocation(propertyLocation);
//		propertyInitializer.setRuntimeLocation(runtimeLocation);
//		propertyInitializer.setWorkingLocation(workingLocation);
//		propertyManager.initialize();
//		propertyInitializer.initialize();
//	}
	
//	public static String normalizePath(String path) {
//		String fileSeparator = System.getProperty("file.separator");
//		String newPath = path.replace("/", fileSeparator);
//		if (newPath.substring(0, 1).equals(fileSeparator))
//			newPath = newPath.substring(1);
//		return newPath;
//	}

//	public void initializeFrameworkComponents() throws Exception {
//		BeanContext.set("org.aries.proxyLocator", new ProxyLocator());
//		BeanContext.set("org.aries.processRegistry", new ProcessRegistry());
//		BeanContext.set("org.aries.processLocator", new ProcessLocator());
//		BeanContext.set("org.aries.namespaceContext", new NamespaceContext());
//		BeanContext.set("org.aries.serviceFactory", new ServiceFactoryImpl());
//		BeanContext.set("org.aries.serviceRepository", new ServiceRepositoryImpl());
//		BeanContext.set("org.aries.serviceRegistry", new ServiceRegistry());
//		BeanContext.set("org.aries.serviceLocator", new ServiceLocator());
//		BeanContext.set("org.aries.serviceProxyFactory", new ServiceProxyFactory());
//		BeanContext.set("org.aries.linkStateRegistry", new LinkStateRegistry());
//		BeanContext.set("org.aries.executorService", Executors.newFixedThreadPool(10));
//		TaskExecutorFactory taskExecutorFactory = new TaskExecutorFactory();
//		taskExecutorFactory.setTaskExecutorClassName("common.tx.util.TaskExecutorImpl");
//		BeanContext.set("org.aries.executorFactory", taskExecutorFactory);
//    	BeanContext.set("org.aries.providerCache", new ProviderCache());
//	}

	public GenerationContext getContext() {
		return context;
	}

	public String getDomainId() {
		return domainId;
	}

	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	
	protected boolean isClientModuleType() {
		return getModuleType(moduleId) == ModuleType.CLIENT;
	}

	protected boolean isServiceModuleType() {
		return getModuleType(moduleId) == ModuleType.SERVICE;
	}

	protected ModuleType getModuleType(String moduleId) {
		Module module = context.getModuleById(moduleId);
		if (module == null)
			System.out.println();
		return module.getType();
	}
	
	public Collection<Project> initializeClientSide(String fileName) throws Exception {
		synchronized (Initializer.class) {
			Collection<Project> projects = initializeModel(fileName, false);
			return projects;
		}
	}
	
//	public Collection<Project> initializeClientSide(String fileName, String applicationId) throws Exception {
//		setApplicationId(applicationId);
//		return initializeClientSide(fileName);
//	}
	
	public Collection<Project> initializeServiceSide(String fileName) throws Exception {
		synchronized (Initializer.class) {
			Collection<Project> projects = initializeModel(fileName, true);
			return projects;
		}
	}
	
//	public List<Project> initializeServiceSide(String fileName, String applicationId) throws Exception {
//		setApplicationId(applicationId);
//		return initializeServiceSide(fileName);
//	}
	
	protected GenerationContext createContext() {
		GenerationContext context = new GenerationContext();
		//GenerationContext.INSTANCE = context;
		context.setModelLocation(MODEL_LOCATION);
		//context.setPropertyLocation(PROPERTY_LOCATION);
		
		String bootstrapperKey = getModuleId() + ".bootstrapper";
		Bootstrap bootstrapper = BeanContext.get(bootstrapperKey);
		if (bootstrapper == null) {
			bootstrapper = new Bootstrap();
			BeanContext.set(bootstrapperKey, bootstrapper);
		}
		context.setPropertyLocation(bootstrapper.getPropertyLocation());
		context.setWorkspaceLocation(bootstrapper.getWorkingLocation());
		context.setModelLocation(bootstrapper.getModelLocation());
		context.setRuntimeLocation(bootstrapper.getRuntimeLocation());
		context.setCacheLocation(bootstrapper.getCacheLocation());
		
		//NOTUSED
		//ServletContext servletContext = RuntimeContext.getInstance().getServletContext();
		//if (servletContext != null) {
		//	context.setModelLocation(servletContext.getRealPath("/") + "WEB-INF/classes/model");
		//}
		
		context.setProperty("generateServiceTransport", "JAXWS");
		context.setProperty("useQualifiedContextNames");
		context.setProperty("addApplicationToGroupId");
		//context.setProperty("generateServicePerOperation");
		//context.setProperty("generateMessageLevelTransport");
    	//context.setTargetWorkspace("../../ARIES_GENERATED");
    	//context.setTargetWorkspace("C:/workspace/ARIES");
    	//context.setTargetWorkspace("..");
    	context.setTemplateWorkspace("..");
    	context.setTemplateName("template1");
		context.addSubset("project");
		context.addSubset("service");
		context.addSubset("model");
		context.addSubset("client");
		context.addSubset("data");
		context.addSubset("view");
		context.addSubset("ear");
		return context;
	}

	public Collection<Project> initializeModel(String fileName) throws Exception {
		Collection<Project> projects = initializeModel(fileName, false);
		return projects;
	}
	
	public Collection<Project> initializeModel(String fileName, boolean isServerSide) throws Exception {
		synchronized (Initializer.class) {
			context = createContext();
			ProjectBuilder projectBuilder = new ProjectBuilder(context);
			projectBuilder.initialize(fileName);
			
			List<Project> projects = null;
			if (fileName.endsWith("ariel"))
				projects = projectBuilder.buildProjectsFromARIEL(fileName);
			if (fileName.endsWith("aries"))
				projects = projectBuilder.buildProjectsFromARIES(fileName);
			Assert.notNull(projects, "Unexpected file type: "+fileName);
			
			Iterator<Project> iterator = projects.iterator();
			while (iterator.hasNext()) {
				Project project = iterator.next();
				context.setProject(project);
				
		    	//1. initialize all structures for each modeled application
				Collection<Namespace> namespaces = ProjectUtil.getNamespaces(project);
				Collection<Application> applications = ProjectUtil.getApplications(project);
				Iterator<Application> applicationIterator = applications.iterator();
				while (applicationIterator.hasNext()) {
					Application application = applicationIterator.next();
					context.setApplication(application);
					if (applicationId == null) {
						initializeApplication(application, isServerSide);
					} else if (applicationId.equalsIgnoreCase(application.getName())) {
						initializeApplication(application, isServerSide);
					}
				}
	
				//2. initialize shared service registry
				initializeInfrastructureCache(project);
				
				//3. initialize shared service registry
				initializeLinkStateRegistery(project);
			}
			
			String contextId = domainId + "." + applicationId + "." + moduleId;
			BeanContext.set(contextId + ".context", context);
			BeanContext.set(contextId + ".projects", projects);
			return projects;
		}
	}	

	public void initializeApplication(Application application, boolean isServerSide) throws Exception {
		if (isServerSide)
			System.out.println("Initializing service-side: application="+applicationId+", module="+moduleId);
		else System.out.println("Initializing client-side: application="+applicationId+", module="+moduleId);

	    //1. initialize model context
		//initializeModelContext(application);

		if (isServerSide) {
		    //2. initialize service repository for caching instances of service proxies
			initializeServiceRepository(application);
		}
		
	    //3. initialize service repository for caching instances of service proxies
		initializeServiceContext(application);

		//4. initialize proxies for external calls
		initializeInvocationProxies(application);

		if (isServerSide) {
			//5. initialize proxies for external calls
			initializeTerminatingClients(application);
		}
		
		//6. create service message listeners
		//initializeServiceListeners(applicationProfile);

		//7. create application state instance
		//initializeApplicationState(applicationProfile);
	}

//	protected void initializeModelContext(Application application) {
//		//TODO put things here on an as-needed basis
//		Set<Module> modules = ApplicationUtil.getModules(application);
//		Iterator<Module> iterator = modules.iterator();
//		while (iterator.hasNext()) {
//			Module module = iterator.next();
//			context.addModuleByType(module.getType(), module);
//		}
//	}

	protected void initializeServiceRepository(Application application) throws Exception {
		Collection<Service> services = ApplicationUtil.getServices(application);
		Iterator<Service> iterator = services.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			initializeServiceRepository(service);
		}
	}

	protected void initializeServiceRepository(Service service) throws Exception {
		//TODO work out how components like these get created when seam is not used:
    	ServiceRepository serviceRepository = BeanContext.get("org.aries.serviceRepository");
		ServiceFactory serviceFactory = BeanContext.get("org.aries.serviceFactory");
		String serviceId = service.getDomain() + "." + service.getName();
		String className = service.getClassName();
		if (className != null) {
			Object serviceInstance = null;
			String qualifiedName = service.getPackageName() + "." + service.getInterfaceName();
			if (ClassUtil.classExists(className) && !ClassUtil.isInterface(className)) {
				serviceInstance = serviceFactory.createServiceInstance(serviceId, className);
			} else if (ClassUtil.classExists(qualifiedName + "Impl")) {
				serviceInstance = serviceFactory.createServiceInstance(serviceId, qualifiedName + "Impl");
			} else if (ClassUtil.classExists(qualifiedName + "ListenerForRMI")) {
				serviceInstance = serviceFactory.createServiceInstance(serviceId, qualifiedName + "ListenerForRMI");
				
				//int portNumber = Integer.parseInt(getProperty("aries.port.rmi"));
				//Remote serviceStub = (Remote) RMIStubFactory.createStub(serviceObject, portNumber);
				//RMIServiceRegistry.register(portNumber, serviceId, serviceStub);
				
				//String portNumber = getProperty("aries.port.rmi");
				//Remote stub = UnicastRemoteObject.exportObject((Remote) serviceInstance, Integer.parseInt(portNumber));
				//RMIServiceRegistry.register(Integer.parseInt(portNumber), serviceId, stub);
				////Naming.rebind(serviceId, (Remote) serviceInstance);
			}
			
			if (serviceInstance != null) {
				serviceRepository.addServiceInstance(serviceId, service, serviceInstance);
				//BeanContext.set(serviceId, serviceInstance);
			} else {
				//log.error("Problem to create service instance: "+serviceId);
			}
		}
		List<Operation> operations = ServiceUtil.getOperations(service);
		initializeServiceRepository(service, operations);
	}
	
	//NOTUSED
	protected void createServiceInstance(Service service, String qualifiedName) throws Exception {
		if (ClassUtil.classExists(qualifiedName)) {
			String serviceId = service.getDomain() + "." + service.getName();
	    	ServiceRepository serviceRepository = BeanContext.get("org.aries.serviceRepository");
			ServiceFactory serviceFactory = BeanContext.get("org.aries.serviceFactory");
			Object serviceInstance = serviceFactory.createServiceInstance(serviceId, qualifiedName);
			serviceRepository.addServiceInstance(serviceId, service, serviceInstance);
			BeanContext.set(serviceId, serviceInstance);
		}
	}

	protected void initializeServiceRepository(Service service, List<Operation> operations) throws Exception {
		Iterator<Operation> operationIterator = operations.iterator();
		while (operationIterator.hasNext()) {
			Operation operation = operationIterator.next();
			initializeServiceRepository(service, operation);
		}
	}

	protected void initializeServiceRepository(Service service, Operation operation) throws Exception {
		boolean status = false;
		if (status == false)
			status = initializeServiceRepository(service, operation, NameUtil.getPackageNameFromNamespace(service.getNamespace()));
		if (status == false)
			status = initializeServiceRepository(service, operation, service.getDomain());
		if (status == false) {
			//String serviceId = ServiceUtil.getServiceId(service);
			//log.info("Error initializing service repository: "+serviceId);
		}
	}

	protected boolean initializeServiceRepository(Service service, Operation operation, String packageName) throws Exception {
		String operationName = operation.getName();
		String serviceDomain = service.getDomain();
		String serviceName = NameUtil.uncapName(service.getName());
		String servicePackagePart = NameUtil.trimFromEnd(serviceName, "Service");
		String className = packageName + "." + servicePackagePart + "." + NameUtil.capName(operationName);
		String serviceId = serviceDomain + "." + serviceName;
		if (!serviceName.toLowerCase().endsWith(operationName.toLowerCase()))
			serviceId += "." + operationName;
				
		log.info("Initializing service repository: serviceId="+serviceId+", operation="+operationName);
		ServiceRepository serviceRepository = BeanContext.get("org.aries.serviceRepository");
		ServiceFactory serviceFactory = BeanContext.get("org.aries.serviceFactory");

		//log.info("Looking for service implementation class for: "+className);
		//log.info("Service class "+className+" exists: "+ClassUtil.classExists(className));
		//log.info("Service class "+className+"Action exists: "+ClassUtil.classExists(className+"Action"));
		//log.info("Service interface "+className+" exists: "+ClassUtil.isInterface(className));
		//log.info("Service class "+className+"Impl exists: "+ClassUtil.classExists(className+"Impl"));

		Object serviceInstance = null;
		if (ClassUtil.classExists(className) && !ClassUtil.isInterface(className)) {
			serviceInstance = serviceFactory.createServiceInstance(serviceId, className);
		} else if (ClassUtil.classExists(className+"Action")) {
			serviceInstance = serviceFactory.createServiceInstance(serviceId, className+"Action");
		} else if (ClassUtil.classExists(className+"Impl")) {
			serviceInstance = serviceFactory.createServiceInstance(serviceId, className+"Impl");
		}
		
		if (serviceInstance == null) {
			//log.warn("Unrecognized service: "+serviceName+", Service class not found for: "+className);
			return false;
		}
		
		log.info("Adding service instance: serviceId="+serviceId+", operation="+operationName);
		serviceRepository.addServiceInstance(serviceId, service, serviceInstance);
		BeanContext.set(serviceId, serviceInstance);
		return true;
	}
	
	protected void initializeServiceContext(Application application) throws Exception {
		List<Namespace> namespaces = ApplicationUtil.getNamespaces(application);
		Collection<Service> services = ApplicationUtil.getServices(application);
		Iterator<Service> iterator = services.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			NamespaceContext namespaceContext = BeanContext.get(service.getDomain() + ".namespaceContext");
			namespaceContext.initialize(service, namespaces);
			//namespaceContext.initialize(service);
		}
	}

	//create invocation proxies for the outgoing links of each service
	protected void initializeInvocationProxies(Application application) throws Exception {
		//initializeInvocationProxiesForIncomingLinks(ApplicationUtil.getServices(application));
		initializeInvocationProxiesForOutgoingLinks(ApplicationUtil.getServices(application));
		if (isServiceModuleType()) {
			initializeInvocationProxiesForOutgoingLinks2(ApplicationUtil.getServiceModules(application));
		}
	}
	
	protected void initializeInvocationProxiesForIncomingLinks(List<Service> services) {
		Iterator<Service> iterator = services.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			if (service instanceof Callback == false)
				context.setService(service);
			try {
				initializeInvocationProxiesForIncomingLinks(service);
				//initializeInvocationProxiesForOutgoingLinks(service);
			} catch (Exception e) {
				log.error("Error", e);
			}
		}
	}
	
	//create an invocation proxy for each incoming link
	protected void initializeInvocationProxiesForIncomingLinks(Service service) throws Exception {
		//Project project = context.getProject();
		//Messaging messaging = project.getMessaging();
		//List<Transport> transports = MessagingUtil.getTransports(messaging, service);
		
		List<Channel> channels = ServiceUtil.getChannels(service);
		initializeInvocationProxiesForChannels(service, channels);
		
		Project project = context.getProject();		
		Iterator<Channel> iterator = channels.iterator();
		while (iterator.hasNext()) {
			Channel channel = (Channel) iterator.next();
			List<Sender> senders = ChannelUtil.getSenders(channel);
			Iterator<Sender> iterator2 = senders.iterator();
			while (iterator2.hasNext()) {
				Sender sender = iterator2.next();
				String linkName = sender.getLink();
				Link link = MessagingUtil.getLinkByName(project, linkName);
				List<Transport> transports = LinkUtil.getTransports(link);
				//initializeInvocationProxiesForTransports(service, channel, sender, transports);
			}
		}
		
		List<Callback> callbacks = ServiceUtil.getIncomingCallbacks(service);
		Iterator<Callback> iterator2 = callbacks.iterator();
		while (iterator2.hasNext()) {
			Callback callback = iterator2.next();
			initializeInvocationProxiesForIncomingLinks(callback);
		}
	}
	
	protected void initializeInvocationProxiesForOutgoingLinks(Collection<Service> services) throws Exception {
		Iterator<Service> iterator = services.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			if (service instanceof Callback == false)
				context.setService(service);
			initializeInvocationProxiesForOutgoingLinks(service);
		}
	}
	
	protected void initializeInvocationProxiesForOutgoingLinks2(Collection<Module> serviceModules) throws Exception {
		Iterator<Module> iterator = serviceModules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			initializeInvocationProxiesForOutgoingLinks(module);
		}
	}

	protected void initializeInvocationProxiesForOutgoingLinks(Module module) throws Exception {
		Collection<Service> remoteServices = ServiceUtil.getRemoteServices(context.getProject(), module);
		Iterator<Service> iterator = remoteServices.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			if (service instanceof Callback == false)
				context.setService(service);
			List<Channel> channels = ServiceUtil.getChannels(service);
			initializeInvocationProxiesForChannels(service, channels);
		}
	}

	//create an invocation proxy for each outging link
	protected void initializeInvocationProxiesForOutgoingLinks(Service service) throws Exception {
		//Map<String, PartnerLinkType> linkTypeMap = context.getPartnerLinkTypeMap();
		List<PortType> portTypes = new ArrayList<PortType>();
		//portTypes.addAll(getIncomingPortTypes(service));
		portTypes.addAll(getOutgoingPortTypes(service));
		if (portTypes.size() > 0) {
			initializeInvocationProxiesUsingPortTypes(service, portTypes);

		} else {
//			List<Channel> channels = ServiceUtil.getChannels(service);
//			initializeInvocationProxiesForChannels(service, channels);
			
//			Project project = context.getProject();
//			Collection<Service> remoteServices = ServiceUtil.getInvokedServices(project, service);
//			Iterator<Service> iterator = remoteServices.iterator();
//			while (iterator.hasNext()) {
//				Service remoteService = iterator.next();
//				if (ServiceUtil.getServiceId(service).equals("bookshop.supplier.queryBooks"))
//					System.out.println();
//				List<Channel> channels = ServiceUtil.getChannels(remoteService);
//				initializeInvocationProxiesForChannels(remoteService, channels);
//			}
			
			if (isClientModuleType()) {
				List<Channel> channels = ServiceUtil.getChannels(service);
				initializeInvocationProxiesForChannels(service, channels);
			}
			
			if (isServiceModuleType()) {
				List<Callback> callbacks = ServiceUtil.getOutgoingCallbacks(service);
				Iterator<Callback> iterator2 = callbacks.iterator();
				while (iterator2.hasNext()) {
					Callback callback = iterator2.next();
					//Service receivingService = callback.getReceivingService();
					List<Channel> channels = ServiceUtil.getChannels(service);
					initializeInvocationProxiesForChannels(callback, channels);
				}
			}
		}
	}
	
	protected void initializeInvocationProxiesUsingPortTypes(Service service, List<PortType> portTypes) throws Exception {
		List<Channel> channels = ServiceUtil.getChannels(service);
		Iterator<Channel> iterator = channels.iterator();
		while (iterator.hasNext()) {
			Channel channel = iterator.next();
			initializeInvocationProxiesUsingPortTypes(service, channel, portTypes);
		}
	}

	protected void initializeInvocationProxiesUsingPortTypes(Service service, Channel channel, List<PortType> portTypes) throws Exception {
		String serviceId = ServiceLayerHelper.getServiceId(service);
		Iterator<PortType> iterator = portTypes.iterator();
		while (iterator.hasNext()) {
			PortType portType = iterator.next();
			QName portTypeQName = portType.getQName();
			//String portTypeLocalPart = portTypeQName.getLocalPart();
			//String portTypeNamespace = portTypeQName.getNamespaceURI();
			String portTypeFromQName = TypeUtil.getTypeFromQName(portTypeQName);
			String packageName = TypeUtil.getPackageName(portTypeFromQName);
			String interfaceName = TypeUtil.getClassName(portTypeFromQName);
			String className = packageName + "." + interfaceName + TransportType.HTTP + "Client";
			String proxyKey = packageName + "." + serviceId + "." + portTypeQName.getLocalPart();
			Transport transport = new Transport();
			transport.setType(nam.model.TransportType.HTTP);
			initializeInvocationProxy(service, channel, transport, proxyKey, className);
		}
	}

	//create an invocation proxy for each outging link
	protected void initializeInvocationProxiesForChannels(Service service, List<Channel> channels) throws Exception {
		Iterator<Channel> iterator = channels.iterator();
		while (iterator.hasNext()) {
			Channel channel = iterator.next();
			initializeInvocationProxiesForChannel(service, channel);
		}
	}
	
	protected void initializeInvocationProxiesForChannel(Service service, Channel channel) throws Exception {
		Project project = context.getProject();
		List<Receiver> receivers = ChannelUtil.getReceivers(channel);
		Iterator<Receiver> iterator = receivers.iterator();
		while (iterator.hasNext()) {
			Receiver receiver = iterator.next();
			String linkName = receiver.getLink();
			Link link = MessagingUtil.getLinkByName(project, linkName);
			List<Transport> transports = LinkUtil.getTransports(link);
			initializeInvocationProxiesForTransports(service, channel, transports);
		}
	}

	//create an invocation proxy for each outging link
	protected void initializeInvocationProxiesForTransports(Service service, Channel channel, List<Transport> transports) throws Exception {
//		if (ServiceUtil.getServiceId(service).equals("bookshop2.supplier.queryBooks"))
//			System.out.println("+++"+service.getName());
//		if (ServiceUtil.getServiceId(service).equals("bookshop2.supplier.reserveBooks"))
//			System.out.println("+++"+service.getName());
		
		Service ownerService = context.getService();

		Iterator<Transport> iterator = transports.iterator();
		while (iterator.hasNext()) {
			Transport transport = iterator.next();
			nam.model.TransportType transportType = transport.getType();
			boolean status = false;
			if (status == false)
				//status = initializeInvocationProxiesForTransport(service, channel, receiver, transport, service.getPackageName());
				status = initializeInvocationProxiesForTransport(service, channel, transport, ClientLayerHelper.getClientPackageName(service));
			if (status == false)
				status = initializeInvocationProxiesForTransport(service, channel, transport, ProjectLevelHelper.getPackageName(service.getNamespace()));
			if (status == false)
				status = initializeInvocationProxiesForTransport(service, channel, transport, service.getDomain());
			if (status == false)
				status = initializeInvocationProxiesUsingOperations(service, channel, transport, ServiceUtil.getOperations(service));
			if (status == false)
				log.warn("Cannot create "+transportType+" client for service: "+ServiceLayerHelper.getServiceId(service));
				//throw new Exception("Cannot create client for service: "+ServiceUtil.getServiceId(service));
		}
	}

	protected boolean initializeInvocationProxiesForTransport(Service service, Channel channel, Transport transport, String packageName) throws Exception {
		Service ownerService = context.getService();
		String serviceName = NameUtil.capName(service.getName());
		String baseName = packageName + "." + serviceName;
		//String proxyKey = channel.getName() + "." + serviceId;
		Application application = context.getApplication();
		String applicationPart = application.getArtifactId().replace("-", ".");
		String proxyKey = ownerService.getDomain() + "." + applicationPart + "." + service.getName();
		if (applicationPart.startsWith(ownerService.getDomain()))
			proxyKey = applicationPart + "." + service.getName();
		//proxyKey = proxyKey.toLowerCase();
		if (service instanceof Callback) {
			baseName += "Reply";
			proxyKey += "Reply";
		}

		AbstractDelegate clientInstance = null;
		//String clientPackageName = ClientLayerHelper.getClientPackageName(service);
		//String clientClassName = packageName + "." + serviceName;
		if (service instanceof Callback)
			clientInstance = createDelegate(packageName + "." + serviceName + "ReplyTo");
		if (clientInstance == null)
			clientInstance = createDelegate(packageName + "." + serviceName + "Client");
		if (clientInstance == null)
			return false;

		clientInstance.setLocalModule(moduleId);
		clientInstance.setLocalDomain(domainId);
		BeanContext.set(proxyKey, clientInstance);
		return initializeInvocationProxy(service, channel, transport, proxyKey, baseName);
	}

	protected AbstractDelegate createDelegate(String qualifiedName) {
		try {
			AbstractDelegate delegate = (AbstractDelegate) createObject(qualifiedName);
			return delegate;
		} catch (Exception e) {
			//ignore this
			return null;
		}
	}
	
	protected boolean initializeInvocationProxiesUsingOperations(Service service, Channel channel, Transport transport, List<Operation> operations) throws Exception {
		String serviceId = ServiceLayerHelper.getServiceId(service);
		String serviceDomain = service.getDomain();
		//String serviceName = service.getName();
		String packageName = serviceDomain;

		boolean clientCreated = false;
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			String operationId = NameUtil.capName(operation.getName());
			String baseName = packageName + "." + operationId;
			String proxyKey = serviceId;
			if (!serviceId.toLowerCase().endsWith(operationId.toLowerCase()))
				proxyKey += "." + operationId;
			
			if (service.getName().equalsIgnoreCase("userManager"))
				System.out.println();
			
			String serviceName = NameUtil.capName(service.getName());
			String clientClassName = packageName + "." + serviceName + "Client";
			AbstractDelegate clientInstance = (AbstractDelegate) createObject(clientClassName);
			clientInstance.setLocalModule(moduleId);
			clientInstance.setLocalDomain(domainId);
			BeanContext.set(proxyKey, clientInstance);

			boolean status = false;
			if (status == false)
				status = initializeInvocationProxy(service, channel, transport, proxyKey, ClientLayerHelper.getClientPackageName(service) + "." + operationId);
			if (status == false)
				status = initializeInvocationProxy(service, channel, transport, proxyKey, service.getDomain() + "." + operationId);
			//if (status == false)
			//	throw new Exception("Cannot create client: "+proxyKey);
			if (status == true)
				clientCreated = true;
		}
		
		return clientCreated;
	}

	protected boolean initializeInvocationProxy(Service service, Channel channel, Transport transport, String proxyKey, String baseName) throws Exception {
		switch (transport.getType()) {
		case RMI: return initializeInvocationProxyRMI(service, channel, transport, proxyKey, baseName);
		case EJB: return initializeInvocationProxyEJB(service, channel, transport, proxyKey, baseName);
		case HTTP: return initializeInvocationProxyJAXWS(service, channel, transport, proxyKey, baseName);
		case JMS: return initializeInvocationProxyJMS(service, channel, transport, proxyKey, baseName);
		default: return false;
		}
	}
	
	protected boolean initializeInvocationProxyRMI(Service service, Channel channel, Transport transport, String proxyKey, String baseName) throws Exception {
		String className = initializeClassName(baseName, "RMI");
		if (className == null)
			return false;

		//assuming these have already been verified to exist
        String hostName = getPrefixedProperty("application_service_host");
        String portNumber = getProperty("aries.port.rmi");
        
		Class<?>[] parameterTypes = new Class<?>[] {String.class, String.class, int.class};
		Object[] parameterValues = new Object[] {proxyKey, hostName, Integer.parseInt(portNumber)};
		Object proxyInstance = createObject(className, parameterTypes, parameterValues);
		if (proxyInstance == null)
			return false;
		
		log.info("Created client (RMI): "+proxyKey);			
		String proxyLocatorKey = getDomainId() + ".proxyLocator";
		ProxyLocator proxyLocator = BeanContext.get(proxyLocatorKey);
		String transportType = transport.getType().toString().toUpperCase();
		proxyLocator.add(proxyKey, proxyInstance, transportType);
		return true;
	}
	
	protected boolean initializeInvocationProxyEJB(Service service, Channel channel, Transport transport, String proxyKey, String baseName) throws Exception {
		String className = initializeClassName(baseName, "EJB");
		if (className == null)
			return false;

		//assuming these have already been verified to exist
        String hostName = getPrefixedProperty("application_service_host");
        String portNumber = getPrefixedProperty("application_service_port");
        
		Class<?>[] parameterTypes = new Class<?>[] {String.class, String.class, int.class};
		Object[] parameterValues = new Object[] {proxyKey, hostName, Integer.parseInt(portNumber)};
		Object proxyInstance = createObject(className, parameterTypes, parameterValues);
		if (proxyInstance == null)
			return false;
		
//		Object proxyInstance = createObject(className);
//		if (proxyInstance == null)
//			return false;
		
		InitialContext initialContext = new InitialContext();
		List<Project> projects = context.getProjectList();
	    Module module = ModuleUtil.getServiceModule(projects, service);

	    String applicationName = (String) initialContext.lookup("java:app/AppName");
	    String moduleName = module.getName() + "-" + module.getVersion();
	    String beanName = service.getInterfaceName() + "ListenerFor" + transport.getType().name() + "";
	    
		FieldUtil.setFieldValue(proxyInstance, "applicationName", applicationName);		
		FieldUtil.setFieldValue(proxyInstance, "moduleName", moduleName);		
		FieldUtil.setFieldValue(proxyInstance, "beanName", beanName);		
	    
		//ServletContext servletContext = RuntimeContext.getInstance().getServletContext();
		//Object context2 = FacesContext.getCurrentInstance().getExternalContext().getContext();
		//Project project = context.getProject();
		//FieldUtil.setFieldValue(proxyInstance, "userName", "");
		//servletContext.getAttribute(name)
		
		log.info("Created client (EJB): "+proxyKey);
		String proxyLocatorKey = getDomainId() + ".proxyLocator";
		ProxyLocator proxyLocator = BeanContext.get(proxyLocatorKey);
		String transportType = transport.getType().toString().toUpperCase();
		proxyLocator.add(proxyKey, proxyInstance, transportType);
		return true;
	}
	
	protected boolean initializeInvocationProxyJAXWS(Service service, Channel channel, Transport transport, String proxyKey, String baseName) throws Exception {
		String className = initializeClassName(baseName, "JAXWS");
		if (className == null)
			return false;
		
		//assuming these have already been verified to exist
        String hostName = getPrefixedProperty("application_service_host");
        String portNumber = getPrefixedProperty("application_service_port");
        
		Class<?>[] parameterTypes = new Class<?>[] {String.class, int.class};
		Object[] parameterValues = new Object[] {hostName, Integer.parseInt(portNumber)};
		Object proxyInstance = createObject(className, parameterTypes, parameterValues);
		if (proxyInstance == null)
			return false;
		
		log.info("Created client (JAXWS): "+proxyKey);			
		String proxyLocatorKey = getDomainId() + ".proxyLocator";
		ProxyLocator proxyLocator = BeanContext.get(proxyLocatorKey);
		String transportType = transport.getType().toString().toUpperCase();
		proxyLocator.add(proxyKey, proxyInstance, transportType);
		return true;
	}
	
	protected boolean initializeInvocationProxyJMS(Service service, Channel channel, Transport transport, String proxyKey, String baseName) throws Exception {
		String className = initializeClassName(baseName, "JMS");
		if (className == null || className.endsWith("Proxy"))
			return false;
		
		//assuming these have already been verified to exist
        //String hostName = getPrefixedProperty("application_service_host");
        //String portNumber = getPrefixedProperty("application_service_port");
        
		Class<?>[] parameterTypes = new Class<?>[] {String.class};
		Object[] parameterValues = new Object[] {proxyKey};
		Object proxyInstance = createObject(className, parameterTypes, parameterValues);
		if (proxyInstance == null)
			return false;
		
//		Object proxyInstance = createObject(className);
//		if (proxyInstance == null)
//			return false;
		
		log.info("Created client (JMS): "+proxyKey);
		Project project = context.getProject();
		JmsTransport jmsTransport = (JmsTransport) transport;
		Provider provider = MessagingUtil.getProviderByName(project, jmsTransport.getProvider());
		
//		StringBuffer destinationName2 = new StringBuffer();
//		destinationName2.append(channel.getName() + "_");
//		destinationName2.append(project.getName() + "_");
//		//destinationName.append(receiver.getName() + "_");
//		destinationName2.append(context.getApplication().getName() + "_");
//		String simpleName = NameUtil.getSimpleName(proxyKey);
//		simpleName = NameUtil.toUnderscoredLowercase(simpleName);
//		destinationName2.append(simpleName + "_");
//		destinationName2.append("queue");

		String destinationName = null;
		if (service instanceof Callback) {
			String domain = service.getDomain();
			List<Channel> channels = ServiceUtil.getChannels(service);
			if (channels.size() != 1)
				System.out.println();
			Assert.isTrue(channels.size() == 1, "Assuming only 1 incoming channel per service now");
			destinationName = JMSUtil.getDestinationName(domain, service.getName(), channels.get(0));
			destinationName += "_queue";
		} else {
			String domain = context.getService().getDomain();
			destinationName = JMSUtil.getDestinationName(domain, service.getName(), channel);
			destinationName += "_queue";
		}
		
		String simpleName = NameUtil.getSimpleName(proxyKey);
//		if (destinationName.contains("purchase_books"))
//			System.out.println();
//		if (destinationName.contains("purchase_accepted"))
//			System.out.println();
//		if (destinationName.contains("shipment_complete"))
//			System.out.println();
//		if (destinationName.contains("books_available"))
//			System.out.println();
		
		if (provider != null) {
			String jmsUserName = provider.getJndiContext().getUserName();
			String jmsPassword = provider.getJndiContext().getPassword();
			String jmsDestination = "queue/" + destinationName.toString().toLowerCase();
			String jmsConnectionFactory = jmsTransport.getConnectionFactory();
			if (jmsConnectionFactory == null)
				jmsConnectionFactory = "/ConnectionFactory";

			String jndiProviderUrl = provider.getJndiContext().getConnectionUrl();
			String jndiFactoryClass = provider.getJndiContext().getContextFactory();
			String jndiFactoryUrl = provider.getJndiContext().getContextPackages();
			String jndiPrincipal = provider.getJndiContext().getUserName();
			String jndiCredentials = provider.getJndiContext().getPassword();

//			PropertyManager propertyManager = BeanContext.get(getModuleId() + ".propertyManager");
//			String clientJmsUserName = propertyManager.get(proxyKey + ".username");
//			String clientJmsPassword = propertyManager.get(proxyKey + ".password");
//			String clientJmsDestination = propertyManager.get(proxyKey + ".destination");
//			String clientJmsConnectionFactory = propertyManager.get(proxyKey + ".connectionFactory");
//			String clientJndiProviderUrl = propertyManager.get("java.naming.provider.url");
//			String clientJndiFactoryUrl = propertyManager.get("java.naming.factory.url.pkgs");
//			String clientJndiFactoryClass = propertyManager.get("java.naming.factory.initial");
//			String clientJndiPrincipal = propertyManager.get("java.naming.security.principal");
//			String clientJndiCredentials = propertyManager.get("java.naming.security.credentials");
//
//			if (clientJmsUserName != null) jmsUserName = clientJmsUserName;
//			if (clientJmsPassword != null) jmsPassword = clientJmsPassword;
//			if (clientJmsDestination != null) jmsDestination = clientJmsDestination;
//			if (clientJmsConnectionFactory != null) jmsConnectionFactory = clientJmsConnectionFactory;
//			if (clientJndiProviderUrl != null) jndiProviderUrl = clientJndiProviderUrl;
//			if (clientJndiFactoryUrl != null) jndiFactoryUrl = clientJndiFactoryUrl;
//			if (clientJndiFactoryClass != null) jndiFactoryClass = clientJndiFactoryClass;
//			if (clientJndiPrincipal != null) jndiPrincipal = clientJndiPrincipal;
//			if (clientJndiCredentials != null) jndiCredentials = clientJndiCredentials;

			Properties initialContextProperties = new Properties();
			initialContextProperties.put("java.naming.provider.url", jndiProviderUrl);
			initialContextProperties.put("java.naming.factory.url.pkgs", jndiFactoryUrl);
			initialContextProperties.put("java.naming.factory.initial", jndiFactoryClass);
			initialContextProperties.put("java.naming.security.principal", jndiPrincipal);
			initialContextProperties.put("java.naming.security.credentials", jndiCredentials);

			FieldUtil.setFieldValue(proxyInstance, "userName", jmsUserName);
			FieldUtil.setFieldValue(proxyInstance, "password", jmsPassword);
			FieldUtil.setFieldValue(proxyInstance, "destinationName", jmsDestination);
			FieldUtil.setFieldValue(proxyInstance, "connectionFactoryName", jmsConnectionFactory);
			FieldUtil.setFieldValue(proxyInstance, "initialContextProperties", initialContextProperties);

		} else {
			PropertyManager propertyManager = BeanContext.get(getModuleId() + ".propertyManager");
			String jmsUserName = propertyManager.get(proxyKey + ".username");
			String jmsPassword = propertyManager.get(proxyKey + ".password");
			String jmsDestination = propertyManager.get(proxyKey + ".destination");
			String jmsConnectionFactory = propertyManager.get(proxyKey + ".connectionFactory");
			if (jmsConnectionFactory == null)
				jmsConnectionFactory = "/ConnectionFactory";

			String jndiProviderUrl = propertyManager.get("java.naming.provider.url");
			String jndiFactoryUrl = propertyManager.get("java.naming.factory.url.pkgs");
			String jndiFactoryClass = propertyManager.get("java.naming.factory.initial");
			String jndiPrincipal = propertyManager.get("java.naming.security.principal");
			String jndiCredentials = propertyManager.get("java.naming.security.credentials");

			Properties initialContextProperties = new Properties();
			initialContextProperties.put("java.naming.provider.url", jndiProviderUrl);
			initialContextProperties.put("java.naming.factory.url.pkgs", jndiFactoryUrl);
			initialContextProperties.put("java.naming.factory.initial", jndiFactoryClass);
			initialContextProperties.put("java.naming.security.principal", jndiPrincipal);
			initialContextProperties.put("java.naming.security.credentials", jndiCredentials);

			FieldUtil.setFieldValue(proxyInstance, "userName", jmsUserName);
			FieldUtil.setFieldValue(proxyInstance, "password", jmsPassword);
			FieldUtil.setFieldValue(proxyInstance, "destinationName", jmsDestination);
			FieldUtil.setFieldValue(proxyInstance, "connectionFactoryName", jmsConnectionFactory);
			FieldUtil.setFieldValue(proxyInstance, "initialContextProperties", initialContextProperties);
		}

		//TODO establish exact key for proxy cache
		String proxyLocatorKey = getDomainId() + ".proxyLocator";
		ProxyLocator proxyLocator = BeanContext.get(proxyLocatorKey);
		String transportType = transport.getType().toString().toUpperCase();
		proxyLocator.add(proxyKey, proxyInstance, transportType);
		return true;
	}
	
	protected String initializeClassName(String className, String transport) {
		if (ClassUtil.classExists(className) && !ClassUtil.isInterface(className)) {
			return className;
		} else if (ClassUtil.classExists(className + "ProxyFor"+transport+"")) {
			return className + "ProxyFor" + transport + "";
		} else if (ClassUtil.classExists(className + "Sender"+transport+"Impl")) {
			return className + "Sender" + transport + "Impl";
		} else if (ClassUtil.classExists(className + "Proxy")) {
			return className + "Proxy";
		} else {
			log.error("Class not found for client: "+className);
			return null;
		}
	}
	
	protected Collection<? extends PortType> getIncomingPortTypes(Service service) {
		List<PortType> portTypes = new ArrayList<PortType>();
		Map<String, PartnerLink> linkMap = context.getPartnerLinkMap(); 
		
		List<Listener> listeners = ServiceUtil.getListeners(service);
		Iterator<Listener> iterator = listeners.iterator();
		while (iterator.hasNext()) {
			Listener listener = iterator.next();
			String channelName = listener.getChannel();
			Channel channel = context.getChannelByName(channelName);
			
			List<Receiver> receivers = ChannelUtil.getReceivers(channel);
			Iterator<Receiver> receiverIterator = receivers.iterator();
			while (receiverIterator.hasNext()) {
				Receiver receiver = receiverIterator.next();
				String receiverLink = receiver.getLink();
//				if (StringUtils.isEmpty(receiverLink))
//					receiverLink = channel.getReceiveLink();
				Assert.notNull(receiverLink, "Link not found for receiver: "+receiver.getName());
				String linkName = listener.getLink();
				String roleName = receiver.getName();
				PartnerLink partnerLink = linkMap.get(linkName);
				PartnerLinkType partnerLinkType = partnerLink.getPartnerLinkType();
				Assert.notNull(partnerLinkType, "PartnerLinkType must exist");
				PortType portType = context.getPortType(partnerLinkType, roleName);
				portTypes.add(portType);
			}

//			List<Sender> senders = ChannelUtil.getSenders(channel);
//			Iterator<Sender> senderIterator = senders.iterator();
//			while (senderIterator.hasNext()) {
//				Sender sender = senderIterator.next();
//				String sendLink = sender.getLink();
//				if (StringUtils.isEmpty(sendLink))
//					sendLink = channel.getSendLink();
//				Assert.notNull(sendLink, "Link not found for sender: "+sender.getName());
//				String linkName = listener.getLink();
//				String roleName = sender.getName();
//				PartnerLink partnerLink = linkMap.get(linkName);
//				PartnerLinkType partnerLinkType = partnerLink.getPartnerLinkType();
//				Assert.notNull(partnerLinkType, "PartnerLinkType must exist");
//				PortType portType = context.getPortType(partnerLinkType, roleName);
//				portTypes.add(portType);
//			}
		}
		return portTypes;
	}

	protected Collection<? extends PortType> getOutgoingPortTypes(Service service) {
		Set<PortType> portTypes = new HashSet<PortType>();
		portTypes.addAll(getPortTypesFromOutgoingInteractors(ServiceUtil.getSenders(service)));
		portTypes.addAll(getPortTypesFromOutgoingInteractors(ServiceUtil.getInvokers(service)));
		return portTypes;
	}

//	protected Set<PortType> getPortTypesFromInvokers(Service service) {
//		Map<String, PartnerLink> linkMap = context.getPartnerLinkMap(); 
//		Set<PortType> portTypes = new HashSet<PortType>();
//		List<Invoker> invokers = service.getInvokers();
//		Iterator<Invoker> iterator = invokers.iterator();
//		while (iterator.hasNext()) {
//			Invoker link = iterator.next();
//			//String linkType = link.getType();
//			String roleName = link.getRole();
//			PartnerLink partnerLink = linkMap.get(link.getName());
//			PartnerLinkType partnerLinkType = partnerLink.getPartnerLinkType();
//			Assert.notNull(partnerLinkType, "PartnerLinkType must exist");
//			PortType portType = context.getPortType(partnerLinkType, roleName);
//			portTypes.add(portType);
//		}
//		return portTypes;
//	}
//
//	protected Set<PortType> getPortTypesFromSenders(Service service) {
//		Map<String, PartnerLink> linkMap = context.getPartnerLinkMap(); 
//		Set<PortType> portTypes = new HashSet<PortType>();
//		List<Sender> senders = service.getSenders();
//		Iterator<Sender> iterator = senders.iterator();
//		while (iterator.hasNext()) {
//			Sender link = iterator.next();
//			//String linkType = link.getType();
//			String roleName = link.getRole();
//			PartnerLink partnerLink = linkMap.get(link.getName());
//			PartnerLinkType partnerLinkType = partnerLink.getPartnerLinkType();
//			Assert.notNull(partnerLinkType, "PartnerLinkType must exist");
//			PortType portType = context.getPortType(partnerLinkType, roleName);
//			portTypes.add(portType);
//		}
//		return portTypes;
//	}

	protected Set<PortType> getPortTypesFromOutgoingInteractors(List<? extends Interactor> interactors) {
		Map<String, PartnerLink> linkMap = context.getPartnerLinkMap(); 
		Set<PortType> portTypes = new HashSet<PortType>();
		Iterator<? extends Interactor> iterator = interactors.iterator();
		while (iterator.hasNext()) {
			Interactor interactor = iterator.next();
			switch (interactor.getInteraction()) {
			case INVOKE:
			case SEND:
			case REPLY:
				
				String channelName = interactor.getChannel();
				Channel channel = context.getChannelByName(channelName);
				//Assert.notNull(channel, "Channel not found: "+channelName);
				//TODO resolve this:
				if (channel != null) {
					List<Sender> senders = ChannelUtil.getSenders(channel);
					Iterator<Sender> senderIterator = senders.iterator();
					while (senderIterator.hasNext()) {
						Sender sender = senderIterator.next();
						String receiverLink = sender.getLink();
//						if (StringUtils.isEmpty(receiverLink))
//							receiverLink = channel.getReceiveLink();
						Assert.notNull(receiverLink, "Link not found for sender: "+sender.getName());
						String linkName = interactor.getLink();
						String roleName = sender.getName();
	
						//String linkType = link.getType();
						PartnerLink partnerLink = linkMap.get(interactor.getLink());
						PartnerLinkType partnerLinkType = partnerLink.getPartnerLinkType();
						Assert.notNull(partnerLinkType, "PartnerLinkType must exist");
						PortType portType = context.getPortType(partnerLinkType, roleName);
						portTypes.add(portType);
					}
				}
			}
		}
		return portTypes;
	}

	protected void initializeTerminatingClients(Application application) throws Exception {
		Collection<Module> serviceModules = ApplicationUtil.getServiceModules(application);
		Iterator<Module> iterator = serviceModules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			List<Service> services = ModuleUtil.getServices(module);
			initializeTerminatingClients(application, services);
		}
	}

	protected void initializeTerminatingClients(Application application, List<Service> services) throws Exception {
		Set<String> initializedProcesses = new HashSet<String>();
		Iterator<Service> iterator = services.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			Process process = service.getProcess();
			if (process != null) {
				if (!initializedProcesses.contains(process.getName())) {
					initializeTerminatingClients(application, service);
					initializedProcesses.add(process.getName());
				}
			}
		}
	}
	
	protected void initializeTerminatingClients(Application application, Service service) throws Exception {
		String proxyKey = context.getProject().getName() + "." + application.getName() + ".responder";
		List<Channel> channels = ServiceUtil.getChannels(service);
		Iterator<Channel> iterator = channels.iterator();
		while (iterator.hasNext()) {
			Channel channel = iterator.next();
			initializeTerminatingClients(proxyKey, channel);
		}
	}

	protected void initializeTerminatingClients(String proxyKey, Channel channel) throws Exception {
		Project project = context.getProject();
		List<Sender> senders = ChannelUtil.getSenders(channel);
		Iterator<Sender> iterator = senders.iterator();
		while (iterator.hasNext()) {
			Sender sender = iterator.next();
			if (sender.getName().equals("*")) {
				String linkName = sender.getLink();
//				if (linkName == null)
//					linkName = channel.getReceiveLink();
				Assert.notNull(linkName, "Link name not found");
				Link link = MessagingUtil.getLinkByName(project, linkName);
				List<Transport> transports = LinkUtil.getTransports(link);
				initializeTerminatingClients(proxyKey, transports);
			}
		}
	}

	protected void initializeTerminatingClients(String proxyKey, List<Transport> transports) throws Exception {
		Iterator<Transport> iterator = transports.iterator();
		while (iterator.hasNext()) {
			Transport transport = iterator.next();
			initializeTerminatingClients(proxyKey, transport);
		}
	}

	protected void initializeTerminatingClients(String proxyKey, Transport transport) throws Exception {
		Project project = context.getProject();
		switch (transport.getType()) {
		case JMS:
			JmsTransport jmsTransport = (JmsTransport) transport;
			//TODO get this className directly from the Transport
			String className = "org.aries.jms.client.JmsClient";
			Object proxyInstance = createObject(className);
			Assert.notNull(proxyInstance, "Cannot create client: "+className);
			Provider provider = MessagingUtil.getProviderByName(project, jmsTransport.getProvider());
			
			String jmsUserName = provider.getJndiContext().getUserName();
			String jmsPassword = provider.getJndiContext().getPassword();
			String jmsConnectionFactory = jmsTransport.getConnectionFactory();
			if (jmsConnectionFactory == null)
				jmsConnectionFactory = "/ConnectionFactory";

			String jndiProviderUrl = provider.getJndiContext().getConnectionUrl();
			String jndiFactoryClass = provider.getJndiContext().getContextFactory();
			String jndiFactoryUrl = provider.getJndiContext().getContextPackages();
			String jndiPrincipal = provider.getJndiContext().getUserName();
			String jndiCredentials = provider.getJndiContext().getPassword();

			Properties initialContextProperties = new Properties();
			initialContextProperties.put("java.naming.provider.url", jndiProviderUrl);
			initialContextProperties.put("java.naming.factory.url.pkgs", jndiFactoryUrl);
			initialContextProperties.put("java.naming.factory.initial", jndiFactoryClass);
			initialContextProperties.put("java.naming.security.principal", jndiPrincipal);
			initialContextProperties.put("java.naming.security.credentials", jndiCredentials);

			FieldUtil.setFieldValue(proxyInstance, "userName", jmsUserName);
			FieldUtil.setFieldValue(proxyInstance, "password", jmsPassword);
			FieldUtil.setFieldValue(proxyInstance, "connectionFactoryName", jmsConnectionFactory);
			FieldUtil.setFieldValue(proxyInstance, "initialContextProperties", initialContextProperties);
			
			ProxyLocator proxyLocator = BeanContext.get(domainId + ".proxyLocator");
			String transportType = transport.getType().toString().toUpperCase();
			proxyLocator.add(proxyKey, proxyInstance, transportType);
		}
	}

	public void initializeService(Service service, String serviceId) {
		Object serviceInstance = BeanContext.get(serviceId);
		Assert.notNull(serviceInstance, "Service instance not found: "+serviceId);
		Class<?> serviceClass = serviceInstance.getClass();
		//String className = serviceClass.getCanonicalName();
		
		try {
			//get service ID method
			//Method method = ReflectionUtil.findMethod(serviceClass, "getServiceId");
			//Assert.notNull(method, "Method getServiceId() not found in: "+className);

			//get service ID
			//String serviceId = (String) method.invoke(serviceInstance, (Object[]) null);
			//Assert.notNull(serviceId, "ServiceId null in: "+className);
			
			//get service descripter
			//ServiceDefinition serviceDescripter = (ServiceDefinition) ApplicationModel.getServiceDescripter(serviceId);
			//Assert.notNull(serviceDescripter, "ServiceDescripter not found for: "+className);
			
			//add service to service-repository
			//ServiceRepositoryOLD serviceRepository = BeanContext.get("org.aries.serviceRepository");
			//serviceRepository.addServiceInstance(serviceId, serviceInstance);
			
			//web-service? enrich the service descripter 
			Annotation[] annotations = serviceClass.getAnnotations();
			for (Annotation annotation : annotations) {
				Class<?> annotationType = annotation.annotationType();
				if (annotationType.equals(WebService.class)) {
					//WebService webServiceAnnotation = (WebService) annotation;
					//serviceDescripter.setServiceQName(webServiceAnnotation.serviceName());		
					//serviceDescripter.setPortQName(webServiceAnnotation.portName());		
					//serviceDescripter.setTargetNamespace(webServiceAnnotation.targetNamespace());		
					break;
				}
			}

//			//verify the service method exists
//			ProcessDefinition processDescripter = (ProcessDefinition) serviceDescripter.getProcessDescripters().get(0);
//			String methodName = processDescripter.getProcessName();
//			//String methodName = serviceDescripter.getMethodName();
//			if (methodName != null) {
//				Method serviceMethod = ReflectionUtil.findMethod(serviceClass, methodName);
//				Assert.notNull(serviceMethod, "Service method \""+methodName+"\" not found in: "+className);
//				
//				//String returnType = getLocalPartName(serviceMethod.getReturnType());
//				//String[] parameterTypes = getLocalPartNames(serviceMethod.getParameterTypes());
//				//processDescripter.setResultType(returnType);
//				//processDescripter.setParameterTypes(parameterTypes);
//			}
			
//			//verify the service operations exist
//			List<Operation> operations = ServiceUtil.getOperations(service);
//			Iterator<Operation> iterator = operations.iterator();
//			while (iterator.hasNext()) {
//				Operation operation = iterator.next();
//				String operationName = operation.getName();
//				Method method = ReflectionUtil.findMethod(serviceClass, operationName);
//				//Assert.notNull(method, "Service method \""+operationName+"\" not found in: "+serviceClass.getCanonicalName());
//				if (method == null)
//					continue;
//
//				Class<?> returnType = method.getReturnType();
//				if (returnType != null) {
//					Result result = ResultUtil.createResult(returnType);
//					Assert.notNull(result.getType(), "ResultType for method \""+operationName+"\" not found: "+returnType.getCanonicalName());
//					operation.setResult(result);
//				}
//				
//				Class<?>[] parameterTypes = method.getParameterTypes();
//				for (int i = 0; i < parameterTypes.length; i++) {
//					Class<?> parameterType = parameterTypes[i];
//					Parameter parameter = ParameterUtil.createParameter(parameterType);
//					Assert.notNull(parameter.getType(), "ParameterType for method \""+operationName+"\" not found: "+parameterType.getCanonicalName());
//					operation.getParameters().add(parameter);
//				}
//			}
			
			//add service to service-registry
			//ApplicationProfile applicationProfile = ApplicationModel.getApplicationProfile();
			initializeServiceRegistery(service, serviceId);

			//launch each specified service listener
			//initializeServiceListeners(applicationProfile, serviceDescripter);
			
			//return the new descripter
			//return serviceDescripter;
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

//	protected Class<?> getClassObject(String typeName) throws JAXBException {
//		QName qname = new QName(typeName);
//		JAXBContextImpl context = null; //(JAXBContextImpl) JAXBContext.newInstance(classObject);
//		JaxBeanInfo<?> beanInfo = context.getGlobalType(qname);
//		Assert.notNull(beanInfo, "JAX BeanInfo not found: "+typeName);
//		beanInfo.get
//		Collection<QName> typeNames = beanInfo.getTypeNames();
//		Assert.notNull(beanInfo, "JAX TypeNames not found: "+classObject.getName());
//		Assert.isTrue(typeNames.size() == 1, "Too many JAX TypeNames: "+classObject.getName());
//		QName qname = typeNames.iterator().next();
//		return qname.getLocalPart();
//	}

	protected String getLocalPartName(Class<?> classObject) throws JAXBException {
		JAXBContextImpl context = (JAXBContextImpl) JAXBContext.newInstance(classObject);
		JaxBeanInfo<?> beanInfo = context.getBeanInfo(classObject);
		Assert.notNull(beanInfo, "JAX BeanInfo not found: "+classObject.getName());
		Collection<QName> typeNames = beanInfo.getTypeNames();
		Assert.notNull(beanInfo, "JAX TypeNames not found: "+classObject.getName());
		Assert.isTrue(typeNames.size() == 1, "Too many JAX TypeNames: "+classObject.getName());
		QName qname = typeNames.iterator().next();
		return qname.getLocalPart();
	}

	protected String[] getLocalPartNames(Class<?>[] parameterTypes) throws JAXBException {
		String[] partNames = new String[parameterTypes.length];
		for (int i=0; i < parameterTypes.length; i++) {
			Class<?> parameterType = parameterTypes[i];
			String partName = getLocalPartName(parameterType);
			partNames[i] = partName;
		}
		return partNames;
	}

	
//	protected void initializeServiceRegistery(ApplicationProfile applicationProfile) {
//    	Collection<ServiceDescripter> serviceDescripters = applicationProfile.getServiceDescriptersAsList();
//    	Iterator<ServiceDescripter> iterator = serviceDescripters.iterator();
//    	while (iterator.hasNext()) {
//			ServiceDescripter serviceDescripter = iterator.next();
//			initializeServiceRegistery(applicationProfile, serviceDescripter);
//		}
//	}

	
	protected void initializeServiceRegistery(Service service, String serviceId) throws Exception {
		ServiceRegistry serviceRegistry = BeanContext.get("org.aries.serviceRegistry");
		//String context = "/"+applicationProfile.getName();
		//String group = service.getGroupName();

		List<Listener> listeners = ServiceUtil.getListeners(service);
		Iterator<Listener> iterator = listeners.iterator();
		while (iterator.hasNext()) {
			Interactor listener = iterator.next();
			
			String channelName = listener.getChannel();
			Channel channel = context.getChannelByName(channelName);
			Assert.notNull(channel, "Channel not found: "+channelName);
			
			List<Receiver> receivers = ChannelUtil.getReceivers(channel);
			Iterator<Receiver> receiverIterator = receivers.iterator();
			while (receiverIterator.hasNext()) {
				Receiver receiver = receiverIterator.next();
				String receiverLink = receiver.getLink();
//				if (StringUtils.isEmpty(receiverLink))
//					receiverLink = channel.getReceiveLink();
				Assert.notNull(receiverLink, "Link not found for receiver: "+receiver.getName());
				String listenerLinkName = receiver.getLink();
				String listenerRoleName = listener.getRole();
				Link listenerLink = null;
				Role2 listenerRole = null;

//				List<Link> links = MessagingUtil.getLinks(context.getProject());
//				Iterator<Link> linkIterator = links.iterator();
//				while (linkIterator.hasNext()) {
//					Link link = linkIterator.next();
//					if (link.getName().equals(listenerLinkName)) {
//						listenerLink = link;
//						break;
//					}
//				}
				
				listenerLink = MessagingUtil.getLinkByName(context.getProject(), listenerLinkName);
//				if (listenerLink == null)
//					System.out.println();
				Assert.notNull(listenerLink, "Listener link not found");
				List<Role2> roles = LinkUtil.getRoles(listenerLink);
				Iterator<Role2> roleIterator = roles.iterator();
				while (roleIterator.hasNext()) {
					Role2 role = roleIterator.next();
					if (role.getName().equals(listenerRoleName)) {
						listenerRole = role;
						break;
					}
				}

				Assert.notNull(listenerRole, "Listener role not found");
				//String defaultTransport = listenerRole.getDefaultTransport();
				//Assert.notNull(transport, "Transport not found for role: "+listenerRole.getName());
				
				List<Transport> transportsFromLink = LinkUtil.getTransports(listenerLink);
				Iterator<Transport> transportIterator = transportsFromLink.iterator();
				while (transportIterator.hasNext()) {
					Transport transport = transportIterator.next();
					String transportRef = transport.getRef();
					if (transportRef != null)
						transport = MessagingUtil.getTransportByName(context.getProject(), transportRef);
					Assert.notNull(transport, "Transport reference not found: "+transportRef);
					String transportTypeString = transport.getType().toString();
					
					TransportType transportType = TransportType.fromValue(transportTypeString);  
					//TransportUtil.getTransportType(transport);
					ServiceState serviceState = createServiceState(service, serviceId);
					initializeServiceState(listenerLink, listenerRole, serviceState, transport);
					serviceRegistry.refreshServiceState(serviceState, transportType);
					log.info("Service state initialized: ("+transportType+") "+serviceState);
					transport.setType(nam.model.TransportType.valueOf(transportType.toString()));
				}
			}
			

//			RmiTransport rmiTransport = listenerRole.getRmiTransport();
//			EjbTransport ejbTransport = listenerRole.getEjbTransport();
//			JmsTransport jmsTransport = listenerRole.getJmsTransport();
//			HttpTransport httpTransport = listenerRole.getHttpTransport();
//
//			if (rmiTransport != null) {
//				ServiceState serviceState = createServiceState(service, serviceId, "rmi");
//				initializeServiceStateForRMI(listenerLink, listenerRole, serviceState, rmiTransport);
//				serviceRegistry.refreshServiceState(serviceState, TransportType.RMI);
//			}
//
//			if (ejbTransport != null) {
//				ServiceState serviceState = createServiceState(service, serviceId, "ejb");
//				initializeServiceStateForEJB(listenerLink, listenerRole, serviceState, ejbTransport);
//				serviceRegistry.refreshServiceState(serviceState, TransportType.EJB);
//			}
//
//			if (jmsTransport != null) {
//				ServiceState serviceState = createServiceState(service, serviceId, "jms");
//				initializeServiceStateForJMS(listenerLink, listenerRole, serviceState, jmsTransport);
//				serviceRegistry.refreshServiceState(serviceState, TransportType.JMS);
//			}
//			
//			if (httpTransport != null) {
//				ServiceState serviceState = createServiceState(service, serviceId, "ws");
//				initializeServiceStateForHTTP(listenerLink, listenerRole, serviceState, httpTransport);
//				serviceRegistry.refreshServiceState(serviceState, TransportType.HTTP);
//			}
			
//			List<EjbTransport> ejbTransports = listenerRole.getEjbTransports();
//			Iterator<EjbTransport> ejbTransportIterator = ejbTransports.iterator();
//			while (ejbTransportIterator.hasNext()) {
//				EjbTransport ejbTransport = ejbTransportIterator.next();
//				ServiceState serviceState = createServiceState(service, serviceInstance, "ejb");
//				initializeServiceStateForEJB(listenerLink, listenerRole, serviceState, ejbTransport);
//				serviceRegistry.refreshServiceState(serviceState, Transport.EJB);
//			}

//			List<JmsTransport> jmsTransports = listenerRole.getJmsTransports();
//			Iterator<JmsTransport> jmsTransportIterator = jmsTransports.iterator();
//			while (jmsTransportIterator.hasNext()) {
//				JmsTransport jmsTransport = jmsTransportIterator.next();
//				ServiceState serviceState = createServiceState(service, serviceInstance, "jms");
//				initializeServiceStateForJMS(listenerLink, listenerRole, serviceState, jmsTransport);
//				serviceRegistry.refreshServiceState(serviceState, Transport.JMS);
//			}

//			List<HttpTransport> httpTransports = listenerRole.getHttpTransports();
//			Iterator<HttpTransport> httpTransportIterator = httpTransports.iterator();
//			while (httpTransportIterator.hasNext()) {
//				HttpTransport httpTransport = httpTransportIterator.next();
//				ServiceState serviceState = createServiceState(service, serviceInstance, "ws");
//				serviceRegistry.refreshServiceState(serviceState, Transport.WS);
//			}

		}
	}

	protected ServiceState createServiceState(Service service, String serviceId) throws Exception {
		String serviceURL = ServiceUtil.getServiceURL(host, port, service);
		String version = service.getVersion();

		ServiceState serviceState = new ServiceState();
		//serviceState.setContext(context);
		serviceState.setVersion(version);
		serviceState.setServiceId(serviceId);
		serviceState.setServiceURL(serviceURL);
		//serviceState.setName(group+"."+serviceId);
		serviceState.setDomain(service.getDomain());
		serviceState.setName(service.getName());
		serviceState.setInterfaceName(service.getInterfaceName());
		serviceState.setServiceQName(service.getName()+"Service");
		//serviceState.setPortQName(service.getName()+"Port");
		serviceState.setPortQName(service.getName());
		serviceState.setTargetNamespace(service.getNamespace());

		//add each namespace descriptor
		List<Namespace> references = service.getNamespaces();
		Iterator<Namespace> iterator = references.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			NamespaceDefinition namespaceDescripter = new NamespaceDefinition();
			namespaceDescripter.setUri(namespace.getUri());
			namespaceDescripter.setPrefix(namespace.getPrefix());
			namespaceDescripter.setFilename(namespace.getSchema());
			serviceState.addNamespaceDescripter(namespaceDescripter);
			
			List<Type> types = NamespaceUtil.getTypes(namespace);
			Iterator<Type> typeIterator = types.iterator();
			while (typeIterator.hasNext()) {
				Type type = typeIterator.next();
				String typeName = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace.getUri(), type.getName());
				namespaceDescripter.getTypes().add(typeName);
			}

			List<Element> elements = NamespaceUtil.getElements(namespace);
			Iterator<Element> elementIterator = elements.iterator();
			while (elementIterator.hasNext()) {
				Element element = elementIterator.next();
				namespaceDescripter.getTypes().add(element.getType());	
			}
		}

		NamespaceContext namespaceContext = BeanContext.get(service.getDomain() + ".namespaceContext");
		namespaceContext.initialize(serviceState);

		//add each operation signature descriptor 
		List<OperationDescripter> operationDescripters = ServiceRegistryMapper.createOperationDescripters(service);
		Iterator<OperationDescripter> operationDescripterIterator = operationDescripters.iterator();
		while (operationDescripterIterator.hasNext()) {
			OperationDescripter operationDescripter = operationDescripterIterator.next();
			serviceState.addOperationDescripter(operationDescripter);
		}
		
		//add each process signature descripter
		Object serviceInstance = BeanContext.get(serviceId);
		Assert.notNull(serviceInstance, "Service instance not found: "+serviceId);
		Method[] methods = serviceInstance.getClass().getMethods();
		for (Method method: methods) {
			Annotation[] annotations = method.getDeclaredAnnotations();
			ProcessDefinition process = new ProcessDefinition();
			for (Annotation annotation: annotations) {
				if (annotation instanceof WebMethod) {
					WebMethod webMethod = (WebMethod) annotation;
					String processName = webMethod.operationName();
					if (StringUtils.isEmpty(processName))
						processName = method.getName();
					process.setProcessName(processName);
					serviceState.addProcessDescripter(process);
					continue;
				}
				if (annotation instanceof WebResult) {
					WebResult webResult = (WebResult) annotation;
					process.setResultName(webResult.name());
					continue;
				}
			}
		}
		
		//List<ProcessDescripter> processDescripters = serviceDescripter.getProcessDescripters();
		//ProcessDefinition processDescripter = (ProcessDefinition) processDescripters.get(0);
		////processDescripter.setResultType("");
		////processDescripter.setParameterTypes(new String[] {});
		//serviceState.addProcessDescripter(processDescripter);
		//initializeServiceState(serviceState);
		return serviceState;
	}
	

//	protected void initializeServiceState(ServiceState serviceState) {
//		String channelType = channelDescripter.getType();
//		String host = channelDescripter.getHost();
//		String port = channelDescripter.getPort();
//		if (StringUtils.isEmpty(host))
//			host = "localhost";
//		serviceState.setHost(host);
//		if (port == null)
//			port = propertyManager.get("aries.port.http");
//		if (port != null)
//			serviceState.setPort(Integer.parseInt(port));
//		switch (Transport.getTransport(channelType)) {
//		case WS: initializeServiceStateForJAXWS(channelDescripter, serviceState); break;
//		case JMS: initializeServiceStateForJMS(applicationProfile, channelDescripter, serviceState); break;
//		case RMI: initializeServiceStateForRMI(applicationProfile, channelDescripter, serviceState); break;
//		}
//	}

	protected void initializeServiceState(Link listenerLink, Role2 listenerRole, ServiceState serviceState, Transport transport) {
		if (transport instanceof RmiTransport)
			initializeServiceStateForRMI(listenerLink, listenerRole, serviceState, (RmiTransport) transport); 
		else if (transport instanceof EjbTransport)
			initializeServiceStateForEJB(listenerLink, listenerRole, serviceState, (EjbTransport) transport); 
		else if (transport instanceof HttpTransport)
			initializeServiceStateForHTTP(listenerLink, listenerRole, serviceState, (HttpTransport) transport); 
		else if (transport instanceof JmsTransport)
			initializeServiceStateForJMS(listenerLink, listenerRole, serviceState, (JmsTransport) transport); 
	}

	/*
	 * RMI ServiceState
	 * ----------------
	 */

	protected void initializeServiceStateForRMI(Link listenerLink, Role2 listenerRole, ServiceState serviceState, RmiTransport rmiTransport) {
		String host = rmiTransport.getHost();
		Integer port = rmiTransport.getPort();
		if (host == null)
			host = "localhost";
		serviceState.setHost(host);
		serviceState.setPort(port);
		serviceState.setDefaultTransport(TransportType.RMI.name());
		serviceState.setTransferMode(TransferMode.BINARY);
		
		try {
			String serviceId = serviceState.getServiceId();
			List<OperationDescripter> operationDescripters = serviceState.getOperationDescripters();
			Iterator<OperationDescripter> iterator = operationDescripters.iterator();
			while (iterator.hasNext()) {
				OperationDescripter operationDescripter = iterator.next();
				RMIEndpointContext endpointContext = new RMIEndpointContext("localhost", port, serviceId);
				endpointContext.setServiceState(serviceState);
				endpointContext.setOperationDescripter(operationDescripter);
				//EJBEndpointMap.INSTANCE.addEndpointContext(serviceId, endpointContext);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/*
	 * EJB ServiceState
	 * ----------------
	 */

	protected void initializeServiceStateForEJB(Link listenerLink, Role2 listenerRole, ServiceState serviceState, EjbTransport ejbTransport) {
		String providerName = ejbTransport.getProvider();
		String jndiName = ejbTransport.getJndiName();

		if (providerName != null) {
			Provider provider = context.getProviderByName(providerName);
			if (provider != null) {
				JndiDescripter jndiDescripter = new JndiDescripter();
				jndiDescripter.setConnectionUrl(provider.getJndiContext().getConnectionUrl());
				jndiDescripter.setContextFactory(provider.getJndiContext().getContextFactory());
				jndiDescripter.setSecurityPrinciple(provider.getJndiContext().getUserName());
				jndiDescripter.setSecurityCredentials(provider.getJndiContext().getPassword());
				//TODO jndiDescripter.setProperties(provider.getProperties());
				serviceState.setJndiDescripter(jndiDescripter);
			}
		}
		
		serviceState.setProviderName(providerName);
		serviceState.setJndiName(jndiName);
		serviceState.setDefaultTransport(TransportType.EJB.name());
		serviceState.setTransferMode(TransferMode.BINARY);
		
		try {
			JndiDescripter jndiDescripter = serviceState.getJndiDescripter();
			JndiContext jndiContext = null;
			if (jndiDescripter != null)
				jndiContext = createJndiContext(jndiDescripter);

			List<OperationDescripter> operationDescripters = serviceState.getOperationDescripters();
			Iterator<OperationDescripter> iterator = operationDescripters.iterator();
			while (iterator.hasNext()) {
				OperationDescripter operationDescripter = iterator.next();
				EJBEndpointContext endpointContext = new EJBEndpointContext();
				endpointContext.setServiceState(serviceState);
				endpointContext.setOperationDescripter(operationDescripter);
				endpointContext.setJndiContext(jndiContext);
				endpointContext.setJndiName(jndiName);
				
				String serviceId = serviceState.getServiceId();
				EJBEndpointMap.INSTANCE.addEndpointContext(serviceId, endpointContext);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public JndiContext createJndiContext(JndiDescripter jndiDescripter) {
		String contextFactory = jndiDescripter.getContextFactory();
		String connectionUrl = jndiDescripter.getConnectionUrl();
		JndiContext jndiContext = createJndiContext(connectionUrl, contextFactory);
		return jndiContext;
	}

	public JndiContext createJndiContext(String connectionUrl, String contextFactory) {
		JndiProxy jndiContext = new JndiProxy();
		jndiContext.setConnectionUrl(connectionUrl);
		jndiContext.setContextFactory(contextFactory);
		return jndiContext;
	}

	
	/*
	 * JMS ServiceState
	 * ----------------
	 */
	
	protected void initializeServiceStateForJMS(Link listenerLink, Role2 listenerRole, ServiceState serviceState, JmsTransport jmsTransport) {
		String providerName = jmsTransport.getProvider();
		String destinationName = jmsTransport.getDestination();
		//String outboundQueue = channelDescripter.getOutboundQueue();
		//String exceptionQueue = channelDescripter.getExceptionQueue();
		String connectionFactory = jmsTransport.getConnectionFactory();

		if (providerName != null) {
			Provider provider = context.getProviderByName(providerName);
			JndiDescripter jndiDescripter = new JndiDescripter();
			jndiDescripter.setConnectionUrl(provider.getJndiContext().getConnectionUrl());
			jndiDescripter.setContextFactory(provider.getJndiContext().getContextFactory());
			jndiDescripter.setSecurityPrinciple(provider.getJndiContext().getUserName());
			jndiDescripter.setSecurityCredentials(provider.getJndiContext().getPassword());
			//TODO jndiDescripter.setProperties(provider.getProperties());
			serviceState.setJndiDescripter(jndiDescripter);
		}
		
		serviceState.setProviderName(providerName);
		serviceState.setDestinationName(destinationName);
		//serviceState.setOutboundQueue(outboundQueue);
		//serviceState.setExceptionQueue(exceptionQueue);
		serviceState.setConnectionFactory(connectionFactory);
		
		serviceState.setDefaultTransport(listenerRole.getDefaultTransport());
		serviceState.setDesiredTransport(TransportType.JMS.name());
		//TODO serviceState.setTransferMode(jmsTransport.getTransferMode());
	}
	

	/*
	 * JAXWS ServiceState
	 * ------------------
	 */

	protected void initializeServiceStateForHTTP(Link listenerLink, Role2 listenerRole, ServiceState serviceState, HttpTransport httpTransport) {
		serviceState.setDefaultTransport(listenerRole.getDefaultTransport());
		serviceState.setDesiredTransport(TransportType.HTTP.name());
		//TODO serviceState.setTransferMode(jmsTransport.getTransferMode());
	}

	protected void initializeServiceStateForJAXWS_OLD(ChannelDescripter channelDescripter, ServiceState serviceState) {
		String serviceId = serviceState.getServiceId();

		ServiceRepository serviceRepository = BeanContext.get("org.aries.serviceRepository");
		Object instance = serviceRepository.getServiceInstance(serviceId);
		Assert.notNull(instance, "Service not found: "+serviceId);

		Method[] methods = instance.getClass().getMethods();
		for (Method method: methods) {
			Annotation[] annotations = method.getDeclaredAnnotations();
			ProcessDefinition process = new ProcessDefinition();
			for (Annotation annotation: annotations) {
				if (annotation instanceof WebMethod) {
					WebMethod webMethod = (WebMethod) annotation;
					String processName = webMethod.operationName();
					if (StringUtils.isEmpty(processName))
						processName = method.getName();
					process.setProcessName(processName);
					serviceState.addProcessDescripter(process);
					continue;
				}
				if (annotation instanceof WebResult) {
					WebResult webResult = (WebResult) annotation;
					process.setResultName(webResult.name());
					continue;
				}
			}
		}

		//List<ProcessDescripter> processes = serviceState.getProcessDescripters();
		//Assert.isTrue(processes.size() > 0, "No process found: "+serviceId);

		String interfaceName = null;
		Class<?>[] interfaces = instance.getClass().getInterfaces();
		for (Class<?> interfaceObject: interfaces) {
			Annotation[] annotations = interfaceObject.getDeclaredAnnotations();
			for (Annotation annotation: annotations) {
				if (annotation instanceof WebService) {
					interfaceName = interfaceObject.getCanonicalName();
				}
			}
		}

		if (interfaceName == null)
			interfaceName = instance.getClass().getCanonicalName();
		//Assert.notNull(interfaceName, "Interface not found: "+name);
		serviceState.setInterfaceName(interfaceName);

//		String namespace = null;
//		Annotation[] annotations = instance.getClass().getDeclaredAnnotations();
//		for (Annotation annotation: annotations) {
//			if (annotation instanceof WebService) {
//				WebService webServiceAnnotation = (WebService) annotation;
//				namespace = webServiceAnnotation.targetNamespace();
//				if (namespace != null)
//					break;
//			}
//		}
		
		Assert.notNull(serviceState.getServiceQName(), "ServiceQName not found: "+serviceId);
		Assert.notNull(serviceState.getPortQName(), "PortQName not found: "+serviceId);
		Assert.notNull(serviceState.getTargetNamespace(), "TargetNamespace not found: "+serviceId);
	}


	/*
	 * RMI ServiceState
	 * ----------------
	 */

	protected void initializeServiceStateForRMI(ApplicationProfile applicationProfile, ChannelDescripter channelDescripter, ServiceState serviceState) {
		String port = channelDescripter.getPort();
		if (StringUtils.isEmpty(port)) {
			PropertyManager propertyManager = BeanContext.get(getModuleId() + ".propertyManager");
			port = propertyManager.get("aries.port.rmi");
		}
		serviceState.setPort(Integer.parseInt(port));
		ProcessDefinition process = new ProcessDefinition();
		process.setProcessName("process");
		serviceState.addProcessDescripter(process);
	}


//	protected void initializeServiceListeners(ApplicationProfile applicationProfile) {
//    	Collection<ServiceDescripter> serviceDescripters = applicationProfile.getServiceDescriptersAsList();
//    	Iterator<ServiceDescripter> iterator = serviceDescripters.iterator();
//    	while (iterator.hasNext()) {
//			ServiceDescripter serviceDescripter = iterator.next();
//			initializeServiceListener(applicationProfile, serviceDescripter);
//		}
//	}

//	protected void initializeServiceListeners(ApplicationProfile applicationProfile, ServiceDescripter serviceDescripter) {
//		Iterator<ListenerDescripter> iterator = serviceDescripter.getListenerDescripters().iterator();
//		while (iterator.hasNext()) {
//			String serviceId = serviceDescripter.getServiceId();
//			ListenerDescripter listenerDescripter = (ListenerDescripter) iterator.next();
//			ChannelDescripter channelDescripter = applicationProfile.getChannelDescripterByName(listenerDescripter.getListenerName());
//			String channelType = channelDescripter.getType().toUpperCase();
//			String providerName = channelDescripter.getProviderName();
//			ProviderDescripter providerDescripter = applicationProfile.getProviderDescripterByName(providerName);
//
//			//TODO use a switch on ChannelType...
//			if (channelType.equals(ChannelType.JMS.name())) {
//				createServiceListenerForJMS(serviceDescripter, channelDescripter, providerDescripter);
//			} else if (channelType.equals(ChannelType.RMI.name())) {
//				createServiceListenerForRMI(serviceDescripter, channelDescripter);
//			} else if (channelType.equals(ChannelType.JAXWS.name())) {
//				createServiceListenerForJAXWS(applicationProfile, serviceDescripter);
//			}
//		}
//	}

//	protected void createServiceListenerForJMS(ServiceDescripter serviceDescripter, ChannelDescripter channelDescripter, ProviderDescripter providerDescripter) {
//		JMSLauncher launcher = new JMSLauncher();
//		launcher.setServiceDescripter(serviceDescripter);
//		launcher.setProviderDescripter(providerDescripter);
//		launcher.setChannelDescripter(channelDescripter);
//		launcher.launch();
//	}
//
//	protected void createServiceListenerForRMI(ServiceDescripter serviceDescripter, ChannelDescripter channelDescripter) {
//		RMILauncher launcher = new RMILauncher();
//		launcher.setServiceDescripter(serviceDescripter);
//		launcher.setChannelDescripter(channelDescripter);
//		launcher.launch();
//	}

//	public Class<?>[] getClassesToBeScanned() throws Exception {
//		String defaultPackage = ApplicationModel.getApplicationProfile().getDefaultPackage();
//		Class<?>[] classObjects = PackageUtil.getClasses(defaultPackage);
//		return classObjects;
//	}	
		

	protected void initializeInfrastructureCache(Project project) {
    	ProviderCache providerCache = BeanContext.get("org.aries.providerCache");
    	List<Provider> providers = ProjectUtil.getProviders(project);
    	Iterator<Provider> iterator = providers.iterator();
    	while (iterator.hasNext()) {
			Provider provider = iterator.next();
			providerCache.addProvider(provider);
		}
	}

	
	protected void initializeLinkStateRegistery(Project project) {
		LinkStateRegistry linkStateRegistry = BeanContext.get("org.aries.linkStateRegistry");
		
		List<Link> links = MessagingUtil.getLinks(project);
		Iterator<Link> iterator = links.iterator();
		while (iterator.hasNext()) {
			Link link = iterator.next();
			linkStateRegistry.putLink(link);
			
			List<Role2> roles = LinkUtil.getRoles(link);
			Iterator<Role2> roleIterator = roles.iterator();
			while (roleIterator.hasNext()) {
				Role2 role = roleIterator.next();
				linkStateRegistry.putRole(link, role);
			}
		}
	}

	

	protected Object createObject(String className) throws Exception {
		Class<?>[] parameterTypes = new Class<?>[] {};
		Object[] parameterValues = new Object[] {};
		return createObject(className, parameterTypes, parameterValues);
	}
	
	protected Object createObject(String className, Class<?>[] parameterTypes, Object[] parameterValues) throws Exception {
		Object object = null;
		try {
			object = ObjectUtil.loadObject(className, parameterTypes, parameterValues);
		} catch (Exception e) {
			object = null;
		}
		
		if (object == null) {
			object = ObjectUtil.loadObject(className);
		}
		
		if (object == null)
			log.error("Cannot create object for class: "+className);
			//throw new Exception("Cannot create object for class: "+className);
		return object;
	}
	
	protected String getPrefixedProperty(String name) {
		String prefix = getDomainId();
		if (!prefix.endsWith(getApplicationId()))
			prefix += "." + getApplicationId();
		return getProperty(prefix + "." + name);
	}
	
	protected String getProperty(String name) {
		String propertyManagerKey = getModuleId() + ".propertyManager";
		PropertyManager propertyManager = BeanContext.get(propertyManagerKey);
//		if (propertyManager == null)
//			System.out.println();
        String value = propertyManager.get(name);
		if (value == null)
			value = System.getProperty(name);
//		if (value == null)
//			System.out.println();
        Assert.notNull(value, "Property not found: "+name);
		return value;
	}
	
	
}
