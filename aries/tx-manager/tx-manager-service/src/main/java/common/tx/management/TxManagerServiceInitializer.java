package common.tx.management;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.aries.Assert;
import org.aries.launcher.Bootstrap;
import org.aries.runtime.BeanContext;
import org.aries.runtime.Initializer;
import org.aries.util.properties.PropertyManager;


@Startup
@Singleton
@LocalBean
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class TxManagerServiceInitializer implements Initializer, Serializable {

	private AtomicBoolean initialized = new AtomicBoolean(false);
	
	@Override
	public boolean isInitialized() {
		return initialized.get();
	}
	
	protected String getDomain() {
		return "tx.manager";
	}

	protected String getModule() {
		return "tx.manager.service";
	}

	@PostConstruct
	public void initialize() throws Exception {
		//ConversationRegistry.initialize();
		//CheckpointManager.addConfiguration("buyer-checks.xml");
		
		//MBeanUtil.setMBeanServer(ManagementFactory.getPlatformMBeanServer());
		//MBeanUtil.registerMBean(new ProxyManager(), ProxyManager.MBEAN_NAME);
		//MBeanUtil.registerMBean(new ClientInvokedDispatcher(), ClientInvokedDispatcher.MBEAN_NAME);
		//MBeanUtil.registerMBean(new ServiceInvokedDispatcher(), ServiceInvokedDispatcher.MBEAN_NAME);
		//MBeanUtil.registerMBean(new ServiceCompletedDispatcher(), ServiceCompletedDispatcher.MBEAN_NAME);
		
		String propertyName = "tx-manager.runtime_home";
		String runtimeHome = System.getProperty(propertyName);
		Assert.notNull(runtimeHome, "External property not found: "+propertyName);
		Bootstrap bootstrapper = new Bootstrap();
		BeanContext.set(getModule() + ".bootstrapper", bootstrapper);
		bootstrapper.setWorkingLocation(runtimeHome);
		bootstrapper.setRuntimeLocation(runtimeHome);
		bootstrapper.setPropertyLocation(runtimeHome + "/properties");
		bootstrapper.setModelLocation(runtimeHome + "/model");
		bootstrapper.setCacheLocation(runtimeHome + "/cache");
		bootstrapper.setDomainName(getDomain());
		bootstrapper.setModuleName(getModule());
		bootstrapper.initialize(runtimeHome);

		String hostName = getProperty("tx-manager.host_name");
        String httpPort = getProperty("tx-manager.http_port");
        //String applicationDescriptorFile = getProperty("bookshop.buyer.application_model_file");
        
		//Launcher launcher = new Launcher(applicationServiceHost, applicationServicePort);
		//launcher.initialize(applicationDescriptorFile, "buyer");
		//launcher.launch();
		
        TXManagerServiceSideLauncher txManagerServiceSideLauncher = new TXManagerServiceSideLauncher();
		txManagerServiceSideLauncher.register(hostName, httpPort);
		
		//BeanContext.set("bookshop.buyer.notificationDispatcher", new NotificationDispatcher());
		//BeanContext.set("bookshop.buyer.initializer", this);
		initialized.set(true);
	}

	protected String getProperty(String name) {
		String propertyManagerKey = getModule() + ".propertyManager";
		PropertyManager propertyManager = BeanContext.get(propertyManagerKey);
        String value = propertyManager.get(name);
        Assert.notNull(value, "Property not found: "+name);
		return value;
	}
	
}
