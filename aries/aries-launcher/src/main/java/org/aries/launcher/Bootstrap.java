package org.aries.launcher;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import nam.runtime.ProviderCache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.bean.ProxyLocator;
import org.aries.jaxb.JAXBSessionCache;
import org.aries.node.NamespaceContext;
import org.aries.registry.LinkStateRegistry;
import org.aries.registry.ProcessLocator;
import org.aries.registry.ProcessRegistry;
import org.aries.registry.ServiceLocator;
import org.aries.registry.ServiceProxyFactory;
import org.aries.registry.ServiceRegistry;
import org.aries.runtime.BeanContext;
import org.aries.service.ServiceFactoryImpl;
import org.aries.service.ServiceRepositoryImpl;
import org.aries.task.TaskExecutorFactory;
import org.aries.task.TaskInvokerFactory;
import org.aries.util.FileUtil;
import org.aries.util.ResourceUtil;
import org.aries.util.properties.PropertyInitializer;
import org.aries.util.properties.PropertyManager;


public class Bootstrap {

	private static Log log = LogFactory.getLog(Bootstrap.class);

	//public static Bootstrap INSTANCE = new Bootstrap();

	private static String DEFAULT_RUNTIME_LOCATION = System.getProperty("user.dir") + "/runtime";

	private static String DEFAULT_PROPERTY_LOCATION = System.getProperty("user.dir") + "/runtime/properties";

	private static String DEFAULT_MODEL_LOCATION = System.getProperty("user.dir") + "/runtime/model";

	private static String DEFAULT_CACHE_LOCATION = System.getProperty("user.dir") + "/runtime/cache";

	private static String DEFAULT_WORKING_LOCATION = ResourceUtil.getResource(".").getFile();

	private String propertyLocation = DEFAULT_PROPERTY_LOCATION;

	private String workingLocation = DEFAULT_WORKING_LOCATION;

	private String modelLocation = DEFAULT_MODEL_LOCATION;

	private String runtimeLocation = DEFAULT_RUNTIME_LOCATION;

	private String cacheLocation = DEFAULT_CACHE_LOCATION;

	private String domainName;

	private String moduleName;

	private String applicationHome;

	private AtomicBoolean initialized;

	private Object mutex;

	
	public Bootstrap() {
		mutex = new Object();
		initialized = new AtomicBoolean(false);
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getApplicationHome() {
		return applicationHome;
	}

	public void setApplicationHome(String applicationHome) {
		this.applicationHome = applicationHome;
	}

	public String getPropertyLocation() {
		return propertyLocation;
	}

	public void setPropertyLocation(String propertyLocation) {
		this.propertyLocation = propertyLocation;
	}

	public String getWorkingLocation() {
		return workingLocation;
	}

	public void setWorkingLocation(String workingLocation) {
		this.workingLocation = workingLocation;
	}

	public String getModelLocation() {
		return modelLocation;
	}

	public void setModelLocation(String modelLocation) {
		this.modelLocation = modelLocation;
	}

	public String getRuntimeLocation() {
		return runtimeLocation;
	}

	public void setRuntimeLocation(String runtimeLocation) {
		this.runtimeLocation = runtimeLocation;
	}

	public String getCacheLocation() {
		return cacheLocation;
	}

	public void setCacheLocation(String cacheLocation) {
		this.cacheLocation = cacheLocation;
	}
	

//	public void initialize() throws Exception {
//		synchronized (mutex) {
//			initialize();
//		}
//	}
	
	public void initialize(String applicationHome) throws Exception {
		synchronized (mutex) {
			this.applicationHome = applicationHome;
			initializePropertyManager();
			initializeFrameworkComponents();
		}
	}

	// TODO rename runtime location to tempFileLocation
	public void initializePropertyManager() throws Exception {
		// Establish working location
		if (workingLocation == null)
			workingLocation = DEFAULT_WORKING_LOCATION;
		workingLocation = FileUtil.normalizePath(workingLocation);
		log.info("Application working Location: " + workingLocation);

		// Establish application home
		Assert.notNull(applicationHome, "Application home location must be specified");
		applicationHome = FileUtil.normalizePath(applicationHome);
		log.info("Application home location: " + applicationHome);

		// Establish runtime location
		//if (runtimeLocation == null)
		runtimeLocation = applicationHome;
		runtimeLocation = FileUtil.normalizePath(runtimeLocation);
		log.info("Application runtime location: " + runtimeLocation);
		
		// Establish cache location
		//if (cacheLocation == null)
		cacheLocation = applicationHome + "/cache";
		cacheLocation = FileUtil.normalizePath(cacheLocation);
		log.info("Application cache location: " + cacheLocation);
		
		// Establish property location
		propertyLocation = establishGlobalPropertyLocation(applicationHome);
		log.info("Application runtime property Location: " + propertyLocation);
		propertyLocation = FileUtil.normalizePath(propertyLocation);
		
//		File sourceLocation = new File(runtimeLocation + "/model");
//		Set<String> subFolders = ImportUtil.getImportedFileFolders(sourceLocation);
//		Iterator<String> iterator = subFolders.iterator();
//		while (iterator.hasNext()) {
//			String subFolder = iterator.next();
//			PropertyInitializer propertyInitializer = new PropertyInitializer();
//			propertyInitializer.setRuntimeLocation(runtimeLocation);
//			propertyInitializer.setWorkingLocation(workingLocation);
//			propertyInitializer.setPropertyLocation(propertyLocation);
//			if (!subFolder.startsWith("/model"))
//				subFolder = "/model" + subFolder;
//			propertyInitializer.setSubFolder(subFolder);
//			propertyInitializer.initialize();
//
//		}
		
		//TODO establish source and target property locations per module
		PropertyInitializer propertyInitializer = new PropertyInitializer();
		propertyInitializer.setRuntimeLocation(runtimeLocation);
		propertyInitializer.setWorkingLocation(workingLocation);
		propertyInitializer.setPropertyLocation(propertyLocation);
		propertyInitializer.initialize();

		String propertyManagerKey = getModuleName() + ".propertyManager";
		PropertyManager propertyManager = BeanContext.get(propertyManagerKey);
		if (propertyManager == null)
			propertyManager = new PropertyManager();
		//BeanContext.set("org.aries.propertyManager", propertyManager);
		BeanContext.set(propertyManagerKey, propertyManager);
		propertyManager.setPropertyLocation(propertyLocation);
		propertyManager.initialize();

		Object object1 = propertyManager.get("bookshop2.shipper.transport");
		Object object2 = propertyManager.get("bookshop2.supplier.transport");
		System.out.println();
		
//		ServletContext servletContext = RuntimeContext.getInstance().getServletContext();
//		if (servletContext != null) {
//			propertyInitializer.initialize(servletContext);
//		} else {
//			propertyInitializer.initialize();
//		}
	}

	protected String establishGlobalPropertyLocation(String applicationHome) {
		String location = applicationHome + File.separator + "properties";
		if (FileUtil.directoryExists(location))
			return location;
		String location2 = applicationHome + File.separator + "conf";
		if (FileUtil.directoryExists(location2))
			return location2;
		throw new RuntimeException("Global property location not found: " + location);
	}

	public void initializeFrameworkComponents() throws Exception {
		if (!initialized.get()) {
			if (BeanContext.get(domainName + ".proxyLocator") == null)
				BeanContext.set(domainName + ".proxyLocator", new ProxyLocator());
			if (BeanContext.get(domainName + ".jaxbSessionCache") == null)
				BeanContext.set(domainName + ".jaxbSessionCache", new JAXBSessionCache(domainName));
			if (BeanContext.get(domainName + ".namespaceContext") == null)
				BeanContext.set(domainName + ".namespaceContext", new NamespaceContext(domainName));
			if (BeanContext.get("org.aries.processRegistry") == null)
				BeanContext.set("org.aries.processRegistry", new ProcessRegistry());
			if (BeanContext.get("org.aries.processLocator") == null)
				BeanContext.set("org.aries.processLocator", new ProcessLocator());
			if (BeanContext.get("org.aries.serviceFactory") == null)
				BeanContext.set("org.aries.serviceFactory", new ServiceFactoryImpl());
			if (BeanContext.get("org.aries.serviceRepository") == null)
				BeanContext.set("org.aries.serviceRepository", new ServiceRepositoryImpl());
			if (BeanContext.get("org.aries.serviceRegistry") == null)
				BeanContext.set("org.aries.serviceRegistry", new ServiceRegistry());
			if (BeanContext.get("org.aries.serviceLocator") == null)
				BeanContext.set("org.aries.serviceLocator", new ServiceLocator());
			if (BeanContext.get("org.aries.serviceProxyFactory") == null)
				BeanContext.set("org.aries.serviceProxyFactory", new ServiceProxyFactory());
			if (BeanContext.get("org.aries.linkStateRegistry") == null)
				BeanContext.set("org.aries.linkStateRegistry", new LinkStateRegistry());
			if (BeanContext.get("org.aries.executorService") == null)
				BeanContext.set("org.aries.executorService", Executors.newFixedThreadPool(10));
			TaskInvokerFactory taskInvokerFactory = new TaskInvokerFactory();
			TaskExecutorFactory taskExecutorFactory = new TaskExecutorFactory();
			taskInvokerFactory.setTaskInvokerClassName("org.aries.task.TaskInvokerImpl");
			taskExecutorFactory.setTaskExecutorClassName("common.tx.util.TaskExecutorImpl");
			if (BeanContext.get("org.aries.invokerFactory") == null)
				BeanContext.set("org.aries.invokerFactory", taskInvokerFactory);
			if (BeanContext.get("org.aries.executorFactory") == null)
				BeanContext.set("org.aries.executorFactory", taskExecutorFactory);
			if (BeanContext.get("org.aries.providerCache") == null)
				BeanContext.set("org.aries.providerCache", new ProviderCache());
			initialized.set(true);
		}
	}

}
