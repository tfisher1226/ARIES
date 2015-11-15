package org.aries.tx.module;

import java.lang.management.ManagementFactory;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.TransactionManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.client.ProxyManager;
import org.aries.jaxb.JAXBSessionCache;
import org.aries.launcher.Bootstrap;
import org.aries.launcher.Launcher;
import org.aries.notification.NotificationDispatcher;
import org.aries.notification.ServiceEventDispatcher;
import org.aries.runtime.BeanContext;
import org.aries.runtime.Initializer;
import org.aries.util.NameUtil;
import org.aries.util.properties.PropertyManager;
import org.jboss.arquillian.testenricher.cdi.container.CDIExtension;

import com.arjuna.ats.jta.common.jtaPropertyManager;

import common.jmx.MBeanUtil;


public abstract class Bootstrapper implements Initializer {

	protected Log log = LogFactory.getLog(getClass());
	
	public abstract String getDomain();

	public abstract String getApplication();

	public abstract String getModule();
	
	private static Object mutex = new Object();
	
	private AtomicBoolean initialized = new AtomicBoolean(false);

	
	public static boolean isInitialized(String moduleId) {
		Initializer initializer = BeanContext.get(moduleId + ".initializer");
		if (initializer != null)
			return true;
		return false;
	}
	
	
	@Override
	public boolean isInitialized() {
		return initialized.get();
	}

	protected void setInitialized(boolean initialized) {
		this.initialized.set(initialized);
	}
	
	public void initializeAsServiceModule() throws Exception {
		initialize(true);
	}

	public void initializeAsClientModule() throws Exception {
		initialize(false);
	}

//	protected void initialize() throws Exception {
//		initialize(true);
//	}
	
	protected void initialize(boolean isServiceSide) throws Exception {
		//synchronized (mutex) {
			//ConversationRegistry.initialize();
		
			//BeanManager beanManager = CDIExtension.getBeanManager();
			//BeanContext.setBeanManager(beanManager);
		
			MBeanUtil.setMBeanServer(ManagementFactory.getPlatformMBeanServer());
			MBeanUtil.registerMBean(new ProxyManager(), ProxyManager.MBEAN_NAME);
			MBeanUtil.registerMBean(new ServiceEventDispatcher(), ServiceEventDispatcher.MBEAN_NAME);
			//MBeanUtil.registerMBean(new ClientInvokedDispatcher(), ClientInvokedDispatcher.MBEAN_NAME);
			//MBeanUtil.registerMBean(new ServiceInvokedDispatcher(), ServiceInvokedDispatcher.MBEAN_NAME);
			//MBeanUtil.registerMBean(new ServiceCompletedDispatcher(), ServiceCompletedDispatcher.MBEAN_NAME);
			//MBeanUtil.registerMBean(new ServiceAbortedDispatcher(), ServiceAbortedDispatcher.MBEAN_NAME);
			
			jtaPropertyManager.getJTAEnvironmentBean().setSupportSubtransactions(true);
			
			String propertyPrefix = getPropertyPrefix();
			String propertyName = propertyPrefix + ".application_runtime_home";
			String runtimeHome = System.getProperty(propertyName);
			Assert.notNull(runtimeHome, "External property not found: "+propertyName);
			
			Bootstrap bootstrapHelper = new Bootstrap();
			BeanContext.set(getModule() + ".bootstrapper", bootstrapHelper);
			bootstrapHelper.setWorkingLocation(runtimeHome);
			bootstrapHelper.setRuntimeLocation(runtimeHome);
			bootstrapHelper.setPropertyLocation(runtimeHome + "/properties");
			bootstrapHelper.setModelLocation(runtimeHome + "/model");
			bootstrapHelper.setCacheLocation(runtimeHome + "/cache");
			bootstrapHelper.setDomainName(getDomain());
			bootstrapHelper.setModuleName(getModule());
			bootstrapHelper.initialize(runtimeHome);
	
			initializeModel(isServiceSide, propertyPrefix);
			
			BeanContext.set(getDomain() + ".notificationDispatcher", new NotificationDispatcher());
			BeanContext.set(getModule() + ".initializer", this);
			initialized.set(true);
		//}
	}

	protected void initializeModel(boolean isServiceSide, String propertyPrefix) throws Exception {
		try {
	        String applicationServiceHost = getProperty(propertyPrefix + ".application_service_host");
	        String applicationServicePort = getProperty(propertyPrefix + ".application_service_port");
	        String applicationDescriptorFile = getProperty(propertyPrefix + ".application_model_file");
			Launcher launcher = new Launcher(applicationServiceHost, applicationServicePort);
			launcher.initialize(applicationDescriptorFile, getDomain(), getApplication(), getModule(), isServiceSide);
			launcher.launch();
		} catch (Exception e) {
			log.error("Error", e);
		}
	}

	protected String getRuntimeHome() {
		String propertyPrefix = getPropertyPrefix();
		String propertyName = propertyPrefix + ".application_runtime_home";
		String runtimeHome = System.getProperty(propertyName);
		Assert.notNull(runtimeHome, "External property not found: "+propertyName);
		return runtimeHome;
	}

	protected String getPropertyPrefix() {
		String propertyPrefix = "";
		if (!getDomain().endsWith(getApplication()))
			propertyPrefix = getApplication() + ".";
		propertyPrefix += getDomain();
		return propertyPrefix;
	}

	protected String getProperty(String name) {
		String propertyManagerKey = getModule() + ".propertyManager";
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
	
	protected JAXBSessionCache getJAXBSessionCache() {
		JAXBSessionCache jaxbSessionCache = BeanContext.get(getDomain() + ".jaxbSessionCache");
		if (jaxbSessionCache == null) {
			jaxbSessionCache = new JAXBSessionCache(getDomain());
			BeanContext.set(getDomain() + ".jaxbSessionCache", jaxbSessionCache);
		}
		return jaxbSessionCache;
	}
	
	protected void loadSchema(String schemaFileName, Class<?> objectFactoryClass) throws Exception {
		String schemaFilePath = NameUtil.normalizePathToUnix("file://" + getRuntimeHome() + schemaFileName);
		getJAXBSessionCache().addSchema(schemaFilePath, objectFactoryClass);
	}

	protected void getSchema() throws Exception {
		getJAXBSessionCache().getSchema();
	}
	
	public static TransactionManager getTransactionManager() {
		try {
			InitialContext initialContext = new InitialContext();
			TransactionManager transactionManager = (TransactionManager) initialContext.lookup("java:jboss/TransactionManager");
			
			if (transactionManager == null)
				//This will be null when called from outside the container
				transactionManager = com.arjuna.ats.jta.TransactionManager.transactionManager();
			
			//TransactionManager transactionManager = com.arjuna.ats.jta.TransactionManager.transactionManager();
			return transactionManager;
		} catch (NamingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
