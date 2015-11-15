package org.aries.tx;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.ObjectName;

import junit.framework.AssertionFailedError;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.types.FilterSetCollection;
import org.apache.tools.ant.util.FileUtils;
import org.aries.Assert;
import org.aries.client.ProxyManager;
import org.aries.common.AbstractEvent;
import org.aries.common.AbstractMessage;
import org.aries.jms.client.JmsManager;
import org.aries.notification.ServiceEventDispatcher;
import org.aries.notification.ServiceEventNotification;
import org.aries.runtime.BeanContext;
import org.aries.test.ArquillianConfiguration;
import org.aries.test.MavenUtil;
import org.aries.util.ResourceUtil;
import org.aries.util.concurrent.FutureResult;
import org.jboss.arquillian.container.test.api.ContainerController;
import org.jboss.arquillian.container.test.api.Deployer;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.junit.Rule;
import org.junit.rules.TestName;

import common.jmx.client.JmxManager;
import common.jmx.client.JmxProxy;


public abstract class AbstractArquillianTest {

	public abstract String getServerName();
	
	public abstract Class<?> getTestClass();

	
	protected static String REPOSITORY_HOME = "c:/Users/tfisher/.m2/repository/";

	protected static String JBOSS_HOME = "c:/software/jboss-as-7.1.1.Final-node5";
	
	protected static ArquillianConfiguration configuration;

	protected static long testCaseIndex;

	protected static JmxProxy jmxProxy;

	protected static JmxManager jmxManager;

	protected static JmsManager jmsManager;

	
	protected final Log log = LogFactory.getLog(getClass());
	
	@Rule 
	public TestName testName;
	
	@ArquillianResource
	protected Deployer deployer;
	
	@ArquillianResource
	protected ContainerController controller;

//	@ArquillianResource
//	private Instance<DeploymentScenario> deploymentScenario;
	
//	@Inject
//	private Instance<DeploymentContext> deploymentContext;
//	 
//	@Inject
//	private Instance<DeploymentScenario> deploymentScenario;
//	
//	@Inject  
//	private Instance<ArquillianDescriptor> descriptor;  

	private String configurationHome;

	protected boolean isJtaTransaction;

	protected boolean isUserTransaction;

	protected boolean isGlobalTransaction;

	protected boolean isRollbackExpected;

	protected boolean isExceptionExpected;

	protected boolean isValidationErrorExpected;
	
	protected String correlationId;
	
	protected String transactionId;
	
	protected Class<?> exceptionClass;

	protected String exceptionMessage;

	protected String errorMessage;

	protected boolean isLocal;

	protected String expectedError;

	protected String expectedEvent;

	protected String expectedMessage;

	protected String expectedCallback;
	
	protected Throwable expectedException;
	
	protected FutureResult<Object> expectedMessageResult;

	protected FutureResult<Object> expectedEventResult;
	
	private Map<String, Map<String, Notification>> notificationStore;

//	private Map<Object, NotificationListener> notificationListenerStore;
	
	private Map<Object, FutureResult<Object>> notificationListenerMap;
	
	private NotificationListener notificationListener;
	
	
	public static void beforeClass() throws Exception {
		BytemanUtil.initialize();
	}

//	public static void beforeClass(boolean isLocal) throws Exception {
//		AbstractArquillianTest.isLocal = isLocal;
//		beforeClass();
//	}

	public static void afterClass() throws Exception {
	}

	protected boolean isTransactional() {
		return isJtaTransaction || isUserTransaction || isGlobalTransaction;
	}

	protected boolean isServerStarted() throws Exception {
		return isServerStarted(getServerName());
	}
	
	protected boolean isServerStarted(String instanceId) throws Exception {
		return controller.isStarted(instanceId);
	}

	protected boolean startClientSide() throws Exception {
		return true;
	}

	protected boolean startServerSide() throws Exception {
		return startServer();
	}

	protected boolean startServer() throws Exception {
		return startServer(getServerName());
	}

	protected boolean startServer(String instanceId) throws Exception {
		try {
			if (!isServerStarted(instanceId)) {
				initializeTest(instanceId);
				
				//DeploymentContext deploymentContext2 = deploymentContext.get();
				//ArquillianDescriptor arquillianDescriptor = descriptor.get();
				//DeploymentScenario deploymentScenario2 = deployment.get();
				//DeploymentScenario deploymentScenario2 = deploymentScenario.get();
				//InputStream deployment = deployer.getDeployment("supplierEAR");
				//deployer.deploy("supplierEAR");
				
				//deployer.deploy("supplierEAR");
				controller.start(instanceId);
				return true;
			}
			return false;
		} catch (Throwable e) {
			log.error("Error", e);
			return false;
		}
	}
	
	protected void stopServer(String instanceId) throws Exception {
		controller.kill(instanceId);
		controller.stop(instanceId);
		deployer.undeploy("supplierEAR");
	}


	protected void setConfigurationHome(String configurationHome) {
		this.configurationHome = configurationHome;
	}

	public void addFileToDeployment(String fileName) throws Exception {
		String filePath = configurationHome + "/" + fileName;
		File file = new File(filePath); 
		String sourceFilePath = file.getAbsolutePath();
		String targetLocation = JBOSS_HOME + "/standalone/deployments/";
		String targetFilePath = targetLocation + file.getName();
		FilterSetCollection filters = createPropertyFilterSetCollection();
		FileUtils.getFileUtils().copyFile(sourceFilePath, targetFilePath, filters, true);
	}

	protected void addEarToDeployment(String groupId, String artifactId, String version) throws Exception {
		File earFile = MavenUtil.getEnterpriseArchiveFile(groupId, artifactId, version);
		String sourceFilePath = earFile.getAbsolutePath();
		String targetLocation = JBOSS_HOME + "/standalone/deployments/";
		String targetFilePath = targetLocation + earFile.getName();
		FilterSetCollection filters = createPropertyFilterSetCollection();
		FileUtils.getFileUtils().copyFile(sourceFilePath, targetFilePath, filters, true);
	}

	public void setUp() throws Exception {
		testCaseIndex++;
		testName = new TestName();
		log.info("[test"+testCaseIndex+"]: started");
		//log.info(testName.getMethodName()+"[test"+testCaseIndex+"]: started");
		BeanContext.clear();
		initializeTest();
		clearState();
	}
	
	protected void initializeTest() throws Exception {
		initializeTest(getServerName());
	}
	
	protected void initializeTest(String instanceId) throws Exception {
		correlationId = "test"+testCaseIndex+"_correlationId";
		transactionId = "test"+testCaseIndex+"_transactionId";

		String jbossConfigurationHome = "server/jboss7";
		String jbossConfigurationFile = "standalone-hornetq-dedicated1.xml";
		String jbossConfigurationFilePath = jbossConfigurationHome + "/" + jbossConfigurationFile;
		URL jbossConfigurationURL = ResourceUtil.getResource(jbossConfigurationFilePath);
		String sourceFilePath = jbossConfigurationURL.getPath();
		String targetLocation = JBOSS_HOME + "/standalone/configuration/";
		String targetFilePath = targetLocation + jbossConfigurationFile;

		//save content of classpath URL to tmp output file
		InputStream resourceAsStream = ResourceUtil.getResourceAsStream(jbossConfigurationFilePath);
		String tmpFilePath = System.getProperty("java.io.tmpdir") + "/" + jbossConfigurationFile;
		FileOutputStream fileOutputStream = new FileOutputStream(tmpFilePath);
		IOUtils.copy(resourceAsStream, fileOutputStream);
		
		//copy from tmp file to target file using filters
		FilterSetCollection filters = createPropertyFilterSetCollection();
		FileUtils.getFileUtils().copyFile(tmpFilePath, targetFilePath, filters, true);
		
		configuration = new ArquillianConfiguration();
		if (isLocal) {
			configuration.setJndiPropertyFileName("provider/hornetq/node1-jndi-local.properties");
			configuration.setJmsPropertyFileName("provider/hornetq/node1-jms-remote.properties");
		} else {
			configuration.setJndiPropertyFileName("provider/hornetq/node1-jndi-remote.properties");
			configuration.setJmsPropertyFileName("provider/hornetq/node1-jms-remote.properties");
		}
		configuration.initialize();
		
		assertNotNull(configuration.getBindAddress());
		assertNotNull(configuration.getManagementPort());
		assertNotNull(configuration.getLocalConnectionFactoryName());
		assertNotNull(configuration.getRemoteConnectionFactoryName());
		assertNotNull(configuration.getXAConnectionFactoryName());
		assertNotNull(configuration.getInitialContextProperties());
		
		jmxManager = new JmxManager();
		jmxManager.setUsername(configuration.getUsername());
		jmxManager.setPassword(configuration.getPassword());
		jmxManager.setBindAddress(configuration.getBindAddress());
		jmxManager.setManagementPort(configuration.getManagementPort());
		jmxManager.setJndiProperties(configuration.getInitialContextProperties());
		jmxManager.setLocal(isLocal);
		jmxManager.initialize();
		
		jmxProxy = new JmxProxy();
		jmxProxy.setJmxManager(jmxManager);

		jmsManager = new JmsManager(jmxManager);
		jmsManager.initialize();

		//CheckpointManager.addConfiguration("buyer-checks.xml");
		//MBeanServer mbeanServer = MBeanServerFactory.createMBeanServer();
		//MBeanUtil.setMBeanServer(mbeanServer);
		initializeStructures();
	}

	protected void initializeStructures() throws Exception {
		notificationStore = new HashMap<String, Map<String, Notification>>();
		//notificationListenerStore = new HashMap<Object, NotificationListener>();
		notificationListenerMap = new HashMap<Object, FutureResult<Object>>();
		expectedEventResult = new FutureResult<Object>();
		expectedMessageResult = new FutureResult<Object>();
	}

	public void tearDown() throws Exception {
		//MBeanUtil.unregisterMBeans();
		//BeanContext.clear();
		//fixture = null;
		//clearStructures();
		//clearState();
		log.info("[test"+testCaseIndex+"]: done");
		//log.info(testName.getMethodName()+"[test"+testCaseIndex+"]: done");
	}
	
	protected void clearStructures() throws Exception {
		removeAllNotificationListeners();
		initializeStructures();
	}

	protected void clearState() throws Exception {
		isJtaTransaction = false;
		isUserTransaction = false;
		isGlobalTransaction = false;
		isRollbackExpected = false;
		isExceptionExpected = false;
		isValidationErrorExpected = false;
		exceptionClass = Throwable.class;
		exceptionMessage = "exception message";
		errorMessage = null;
		expectedError = null;
		expectedEvent = null;
		expectedMessage = null;
		expectedCallback = null;
		expectedException = null;
	}

	protected void setupByteman(String methodName) throws Exception {
		BytemanUtil.loadScripts(getClass(), methodName);
		log.info("[test"+testCaseIndex+"]: setupByteman("+methodName+") done");
	}

	protected void tearDownByteman(String methodName) throws Exception {
		BytemanUtil.unloadScripts(getClass(), methodName);
		log.info("[test"+testCaseIndex+"]: tearDownByteman("+methodName+") done");
	}
	
	protected FilterSetCollection createPropertyFilterSetCollection() {
		//String defaultPropertyFile = PropertyManager.getInstance().getDefaultPropertyFile();
		//String serverPropertyFile = PropertyManager.getInstance().getServerPropertyFile();
		//FilterSet filterSet1 = createPropertyFilterSetFromFile(defaultPropertyFile);
		//FilterSet filterSet2 = createPropertyFilterSetFromFile(serverPropertyFile);
		FilterSetCollection filters = new FilterSetCollection();
		//filters.addFilterSet(filterSet1);
		//filters.addFilterSet(filterSet2);
		return filters;
	}

	
//	//@Deployment
//	//@TargetsContainer("hornetQ01_local")
//	public static Archive<?> createTestArchiveOLD() {
//		log.info("createTestArchive() started");
//		  
//		JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "test.jar");
//		jar.addPackage("bookshop");
//		jar.addPackage("bookshop.buyer.client");
//		jar.addPackage("org.aries");
//		jar.addPackage("org.aries.runtime");
//		jar.addPackage("org.aries.validate.util");
//		jar.addPackage("org.aries.jaxb");
//		jar.addPackage("org.jboss.as.jmx");
//		jar.addPackage("org.jboss.as.server.jmx");
//		jar.addPackage("org.jboss.shrinkwrap.api");
//		jar.addClass(ArquillianConfiguration.class);
//		jar.addClass(AbstractArquillianTest.class);
//		jar.addClass(AbstractJMSListenerArquillionTest.class);
//		jar.addClass(OrderBooksJMSListenerArquillianIT.class);
//		//jar.addPackage("org.hornetq.jms.client");
//		jar.addPackage("org.aries.jms.client");
//		jar.addPackage("org.xml.sax.helpers");
//		jar.addPackage("common.jmx.client");
//		jar.addAsResource("provider/hornetq/node1-jndi-local.properties");
//		jar.addAsResource("provider/hornetq/node1-jms-local.properties");
//		//jar.addClass(AbstractClientJMSArquillionTest.class);
//		//jar.addClass(OrderBooksClientJMS.class);
//		//jar.addClass(OrderBooksClientJMSArquillianTXTest.class);
//		//jar.addClass(OrderBooks.class);
//		//jar.addClass(OrderRequestMessage.class);
//		//jar.addAsResource("hornetq-jms3.xml");
//		//jar.addAsResource("jms-ds.xml");
//		//jar.addAsResource("jms-ra.xml");
//		
//		WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war");
//		war.addClass(ArquillianConfiguration.class);
//		war.addClass(AbstractArquillianTest.class);
//		war.addClass(AbstractJMSListenerArquillionTest.class);
//		war.addClass(OrderBooksJMSListenerArquillianIT.class);
//		//war.addPackage("org.hornetq.jms.client");
//		war.addPackage("org.aries.jms.client");
//		war.addPackage("common.jmx.client");
//		
//		MavenResolverSystem resolverSystem = Maven.configureResolver().fromClassloaderResource("settings.xml");
//		MavenStrategyStage resolver = resolverSystem.resolve("org.aries.pom:aries-platform-service-layer:pom:0.0.1-SNAPSHOT");
//		File[] resolvedArtifacts = resolver.withMavenCentralRepo(false).withTransitivity().asFile();
//		//File[] resolvedArtifacts = resolver.withMavenCentralRepo(false).using(strategy).asFile();
//		//File[] resolvedArtifacts = resolver.withMavenCentralRepo(false).withTransitivity().asFile();
//
//		EnterpriseArchive ear = ShrinkWrap.create(EnterpriseArchive.class, "test.ear");
//		ear.addAsLibraries(resolvedArtifacts);
//		//ear.addAsLibrary(projectJar);
//		ear.addAsLibrary(jar);
//		ear.addAsLibrary(war);
//		//ear.setApplicationXML(ResourceUtil.getResource("application/META-INF/application.xml"));
//		ear.addAsApplicationResource(ResourceUtil.getResource("application/META-INF/jboss-deployment-structure.xml"), "/META-INF/jboss-deployment-structure.xml");
//		log.info("createTestArchive() done");
//		return ear;
//	}

	public static String getPomFileName(String groupId, String artifactId, String version) {
		String groupIdPath = groupId.replace(".", File.separator);
		return REPOSITORY_HOME + groupIdPath + "/" + artifactId + "/" + version + "/" + artifactId + "-" + version + ".pom";
	}


//	protected static StringAsset createApplicationXml() {
//		ApplicationDescriptor descriptor = Descriptors.create(ApplicationDescriptor.class);
//		descriptor.version("6");
//		//descriptor.securityRole(roleName, description);
//		descriptor.displayName("bookshop2-buyer");
//		
//		descriptor.javaModule("bookshop2-model-0.0.1-SNAPSHOT.jar");
//		descriptor.javaModule("bookshop2-buyer-client-0.0.1-SNAPSHOT.jar");
//		descriptor.ejbModule("bookshop2-buyer-service-0.0.1-SNAPSHOT.jar");
//		descriptor.javaModule("bookshop2-buyer-service-test-0.0.1-SNAPSHOT.jar");
//		descriptor.webModule("bookshop2-buyer-view-0.0.1-SNAPSHOT.war", "bookshop-buyer");
//		//descriptor.javaModule("bookshop2-seller-model-0.0.1-SNAPSHOT.jar");
//		//descriptor.javaModule("bookshop2-seller-client-0.0.1-SNAPSHOT.jar");
//		//descriptor.ejbModule("bookshop2-seller-service-0.0.1-SNAPSHOT.jar");
//
//		//descriptor.ejbModule("bookshop2-buyer-service-cdi.jar");
//		descriptor.javaModule("admin-model-0.0.1-SNAPSHOT.jar");
//		descriptor.javaModule("admin-client-0.0.1-SNAPSHOT.jar");
//		descriptor.javaModule("admin-data-0.0.1-SNAPSHOT.jar");
//		descriptor.ejbModule("admin-service-0.0.1-SNAPSHOT.jar");
//		
//		descriptor.ejbModule("jboss-seam-2.3.0.Final.jar");
//		String text = descriptor.exportAsString();
//		StringAsset asset = new StringAsset(text);
//		return asset;
//	}
	

//	protected <T extends AbstractMessage> T initializeMessage(T message, String replyToDestination) {
//		message.setReplyToDestination(replyToDestination);
//		message.setCorrelationId(correlationId);
//		message.setTransactionId(transactionId);
//		return message;
//	}
	

	protected <T extends AbstractMessage> T initializeMessage(T message, T message2) {
		message.setCorrelationId(message2.getCorrelationId());
		message.setTransactionId(message2.getTransactionId());
		message.setReplyToDestinations(message2.getReplyToDestinations());
		return message;
	}
	
	protected <T extends AbstractMessage> T initializeMessage(T message) {
		message.setCorrelationId(correlationId);
		message.setTransactionId(transactionId);
		return message;
	}

	protected <T extends AbstractEvent> T initializeEvent(T event) {
		event.setCorrelationId(correlationId);
		event.setTransactionId(transactionId);
		return event;
	}


	//	protected void addMessageNotificationListeners(String serviceId) throws Exception {
	//	addMessageNotificationListeners2(getDomainId() + "." + serviceId);
	//}
	
	protected void addMessageNotificationListeners(String serviceId) throws Exception {
		addNotificationListener(serviceId + "_Message_Sent");
		addNotificationListener(serviceId + "_Message_Received");
		addNotificationListener(serviceId + "_Message_Dispatch_Done");
		addNotificationListener(serviceId + "_Message_Dispatch_Aborted");
	}
	
	//protected void addRequestNotificationListeners(String serviceId) throws Exception {
	//	addRequestNotificationListeners2(getDomainId() + "." + serviceId);
	//}
	//
	//protected void addRequestNotificationListeners(String applicationId, String serviceId) throws Exception {
	//	addRequestNotificationListeners2(getDomainId() + "." + applicationId + "_" + serviceId);
	//}
	
	protected void addRequestNotificationListeners(String serviceId) throws Exception {
		addNotificationListener(serviceId + "_Request_Sent");
		addNotificationListener(serviceId + "_Request_Received");
		addNotificationListener(serviceId + "_Request_Handled");
		addNotificationListener(serviceId + "_Request_Done");
		addNotificationListener(serviceId + "_Request_Cancel_Done");
		addNotificationListener(serviceId + "_Request_Undo_Done");
		addNotificationListener(serviceId + "_Incoming_Request_Aborted");
		addNotificationListener(serviceId + "_Outgoing_Request_Aborted");
	}
	
	//protected void addResponseNotificationListeners(String callbackId) throws Exception {
	//	addResponseNotificationListeners2(getDomainId() + "." + callbackId);
	//}
	//
	//protected void addResponseNotificationListeners(String applicationId, String serviceId) throws Exception {
	//	addResponseNotificationListeners2(getDomainId() + "." + applicationId + "_" + serviceId);
	//}
	
	protected void addResponseNotificationListeners(String callbackId) throws Exception {
		addNotificationListener(callbackId + "_Response_Sent");
		addNotificationListener(callbackId + "_Response_Handled");
		addNotificationListener(callbackId + "_Response_Received");
		addNotificationListener(callbackId + "_Response_Done");
		addNotificationListener(callbackId + "_Incoming_Response_Aborted");
		addNotificationListener(callbackId + "_Outgoing_Response_Aborted");
	}
	
	//protected void addProcessNotificationListeners(String eventId) throws Exception {
	//	addProcessNotificationListeners2(getDomainId() + "." + eventId);
	//}
	
	protected void addProcessNotificationListeners(String eventId) throws Exception {
		addNotificationListener(eventId + "_Process_Complete");
		addNotificationListener(eventId + "_Process_Aborted");
	}
	
	//protected void removeMessageNotificationListeners(String serviceId) throws Exception {
	//	removeNotificationListener(getDomainId() + "." + serviceId + ".MessageComplete");
	//	removeNotificationListener(getDomainId() + "." + serviceId + ".MessageAborted");
	//}
	//
	//protected void removeRequestNotificationListeners(String serviceId) throws Exception {
	//	removeNotificationListener(getDomainId() + "." + serviceId + ".RequestComplete");
	//	removeNotificationListener(getDomainId() + "." + serviceId + ".RequestAborted");
	//}
	//
	//protected void removeResponseNotificationListeners(String callbackId) throws Exception {
	//	removeNotificationListener(getDomainId() + "." + callbackId + ".ResponseComplete");
	//	removeNotificationListener(getDomainId() + "." + callbackId + ".ResponseAborted");
	//}
	//
	//protected void removeProcessNotificationListeners(String eventId) throws Exception {
	//	removeNotificationListener(getDomainId() + "." + eventId + ".ProcessComplete");
	//	removeNotificationListener(getDomainId() + "." + eventId + ".ProcessAborted");
	//}
	
	
	//protected void addNotificationListener(String typeId) throws Exception {
	//	super.addNotificationListener(getDomainId() + "." + typeId);
	//}


	protected void registerMockClient(String className, String clientId) throws Exception {
		Object[] parameters = { className, clientId };
		String[] signature = { "java.lang.String", "java.lang.String" };
		ObjectName objectName = new ObjectName(ProxyManager.MBEAN_NAME);
		jmxManager.invoke(objectName, "addClient", parameters, signature);
	}

	protected FutureResult<Object> registerForResponse(String serviceId) throws Exception {
		if (expectedEvent == null)
			expectedEvent = serviceId + "_Request_Done";
		return registerForResult(expectedEvent);
	}
	
	protected FutureResult<Object> registerForResult(Object notificationId) throws Exception {
		expectedMessage = null;
		expectedMessageResult = new FutureResult<Object>();
		expectedEventResult = new FutureResult<Object>();
		addNotificationListener(notificationId, expectedEventResult);
		return expectedEventResult;
	}
	
	protected Object waitForCompletion() throws Exception {
		Object userData = null;
		
		if (expectedEvent != null) {
			System.out.println("#### [test] Waiting for expected event: "+expectedEvent);
//			if (expectedEventResult == null)
//				System.out.println();
			Notification notification = (Notification) expectedEventResult.get(Integer.MAX_VALUE);
			userData = notification.getUserData();
		}
		
		if (expectedMessage != null) {
			System.out.println("#### [test] Waiting for expected message: "+expectedMessage);
			Boolean messageReceived = (Boolean) expectedMessageResult.get();
			Assert.isTrue(messageReceived, "Expected message not received");
		}
		
		System.out.println("#### [test] Waiting for completion: done");
		System.out.flush();
		
		//pause just briefly
		Thread.sleep(1000);
		return userData;
	}
	
	protected Object waitForEvent(String notificationId) throws Exception {
		System.out.println(">>> Waiting for event: "+notificationId);
		FutureResult<Object> futureResult = new FutureResult<Object>();
		addNotificationListener(notificationId, futureResult);
		Notification notification = (Notification) futureResult.get(Integer.MAX_VALUE);
		Object userData = notification.getUserData();
		return userData;
	}
	
	protected Object waitForCompletion(FutureResult<Object> futureResult) throws Exception {
		Notification notification = (Notification) futureResult.get(Integer.MAX_VALUE);
		Object userData = notification.getUserData();
		//pause just briefly
		Thread.sleep(1000);
		return userData;
	}

//	protected void addClientInvocationListener() throws Exception {
//		NotificationListener notificationListener = createClientInvocationListener();
//		ObjectName objectName = new ObjectName(ClientInvokedDispatcher.MBEAN_NAME);
//		jmxManager.addNotificationListener(objectName, notificationListener);
//	}
//	
//	protected NotificationListener createClientInvocationListener() {
//		return new NotificationListener() {
//			public void handleNotification(Notification notification, Object handback) {
//				System.out.println();
//			}
//		};
//	}
//	
//	protected void addServiceInvocationListener(String serviceId, FutureResult<Object> futureResult) throws Exception {
//		NotificationListener notificationListener = createServiceInvocationListener(serviceId, futureResult);
//		ObjectName objectName = new ObjectName(ServiceInvokedDispatcher.MBEAN_NAME);
//		jmxManager.addNotificationListener(objectName, notificationListener);
//	}
//
//	protected NotificationListener createServiceInvocationListener(final String serviceId, final FutureResult<Object> futureResult) {
//		return new NotificationListener() {
//			public void handleNotification(Notification notification, Object handback) {
//				//Object userData = notification.getUserData();
//				if (notification instanceof ServiceInvokedNotification) {
//					ServiceInvokedNotification serviceInvokedNotification = (ServiceInvokedNotification) notification;
//					if (serviceInvokedNotification.getServiceId().equalsIgnoreCase(serviceId)) {
//						recordNotification(correlationId, serviceId, notification);
//						futureResult.set(notification);
//					}
//				}
//			}
//		};
//	}
//	
//	protected void addServiceCompletedListener(String serviceId) throws Exception {
//		NotificationListener notificationListener = createServiceCompletedListener(serviceId);
//		ObjectName objectName = new ObjectName(ServiceCompletedDispatcher.MBEAN_NAME);
//		jmxManager.addNotificationListener(objectName, notificationListener);
//	}
//
//	protected void addServiceCompletedListener(String serviceId, FutureResult<Object> futureResult) throws Exception {
//		NotificationListener notificationListener = createServiceCompletedListener(serviceId, futureResult);
//		ObjectName objectName = new ObjectName(ServiceCompletedDispatcher.MBEAN_NAME);
//		jmxManager.addNotificationListener(objectName, notificationListener);
//	}
//
//	protected NotificationListener createServiceCompletedListener(final String serviceId) {
//		return new NotificationListener() {
//			public void handleNotification(Notification notification, Object handback) {
//				//Object userData = notification.getUserData();
//				if (notification instanceof ServiceCompletedNotification) {
//					ServiceCompletedNotification serviceCompletedNotification = (ServiceCompletedNotification) notification;
//					
//					System.out.println("%%%%%%%%%%% "+serviceCompletedNotification.getServiceId() + ", " +serviceId);
//					if (serviceCompletedNotification.getServiceId().equalsIgnoreCase(serviceId)) {
//						recordNotification(correlationId, serviceId, notification);
//					}
//				}
//			}
//		};
//	}
//
//	protected NotificationListener createServiceCompletedListener(final String serviceId, final FutureResult<Object> futureResult) {
//		return new NotificationListener() {
//			public void handleNotification(Notification notification, Object handback) {
//				//Object userData = notification.getUserData();
//				if (notification instanceof ServiceCompletedNotification) {
//					ServiceCompletedNotification serviceCompletedNotification = (ServiceCompletedNotification) notification;
//					if (serviceCompletedNotification.getServiceId().equalsIgnoreCase(serviceId)) {
//						recordNotification(correlationId, serviceId, notification);
//						futureResult.set(notification);
//					}
//				}
//			}
//		};
//	}
//
//	protected void addServiceAbortedListener(String serviceId, FutureResult<Object> futureResult) throws Exception {
//		NotificationListener notificationListener = createServiceAbortedListener(serviceId, futureResult);
//		ObjectName objectName = new ObjectName(ServiceAbortedDispatcher.MBEAN_NAME);
//		jmxManager.addNotificationListener(objectName, notificationListener);
//	}
//
//	protected NotificationListener createServiceAbortedListener(final String serviceId, final FutureResult<Object> futureResult) {
//		return new NotificationListener() {
//			public void handleNotification(Notification notification, Object handback) {
//				//Object userData = notification.getUserData();
//				if (notification instanceof ServiceAbortedNotification) {
//					ServiceAbortedNotification serviceAbortedNotification = (ServiceAbortedNotification) notification;
//					if (serviceAbortedNotification.getServiceId().equalsIgnoreCase(serviceId)) {
//						recordNotification(correlationId, serviceId, notification);
//						futureResult.set(notification);
//					}
//				}
//			}
//		};
//	}

	protected void addNotificationListener(String notificationId) throws Exception {
		addNotificationListener(notificationId, null);
	}
	
	protected void addNotificationListener(Object notificationId, FutureResult<Object> futureResult) throws Exception {
		notificationListenerMap.put(notificationId, futureResult);
	}
	
	protected void registerNotificationListeners() throws Exception {
		if (notificationListener != null)
			throw new Exception("NotificationListener already initialized");
		ObjectName objectName = new ObjectName(ServiceEventDispatcher.MBEAN_NAME);
		notificationListener = createNotificationListener();
		jmxManager.addNotificationListener(objectName, notificationListener);
		System.out.println("JMX Listener["+this.hashCode()+"]: registered");

//		Set<Object> keySet = notificationListenerMap.keySet();
//		Iterator<Object> iterator = keySet.iterator();
//		while (iterator.hasNext()) {
//			Object notificationId = iterator.next();
//			FutureResult<Object> futureResult = notificationListenerMap.get(notificationId);
//			createNotificationListener(notificationId, futureResult);
//		}
	}

	protected NotificationListener createNotificationListener() {
		return new NotificationListener() {
			public void handleNotification(Notification notification, Object handback) {
				//Object userData = notification.getUserData();
				if (notification instanceof ServiceEventNotification) {
					ServiceEventNotification serviceEventNotification = (ServiceEventNotification) notification;
					String notificationId = serviceEventNotification.getEventId();
					System.out.println("JMX Listener["+this.hashCode()+"] type["+notificationId+"]: received");
					if (notificationId.equals("Supplier_ReserveBooks_Incoming_Request_Aborted"))
						System.out.println();
					recordNotification(correlationId, notificationId, notification);
					FutureResult<Object> futureResult = notificationListenerMap.get(notificationId);
					if (futureResult != null)
						futureResult.set(notification);
					System.out.println("JMX Listener["+this.hashCode()+"] type["+notificationId+"]: dispatched");
				}
			}
		};
	}
	
	protected void removeAllNotificationListeners() throws Exception {
		if (notificationListener == null)
			throw new Exception("NotificationListener not initialized");
		ObjectName objectName = new ObjectName(ServiceEventDispatcher.MBEAN_NAME);
		jmxManager.removeNotificationListener(objectName, notificationListener);
		notificationListenerMap.clear();
	}
	
//	protected void addNotificationListener(Object notificationId, FutureResult<Object> futureResult) throws Exception {
//		if (notificationListenerStore.containsKey(notificationId))
//			removeNotificationListener(notificationId);
//		ObjectName objectName = new ObjectName(ServiceEventDispatcher.MBEAN_NAME);
//		NotificationListener notificationListener = createNotificationListener(notificationId, futureResult);
//		jmxManager.addNotificationListener(objectName, notificationListener);
//		notificationListenerStore.put(notificationId, notificationListener);
//		if (futureResult == null)
//			System.out.println("JMX Listener["+this.hashCode()+"] type["+notificationId+"]: registered");
//		else System.out.println("JMX Listener["+this.hashCode()+"] type["+notificationId+"]: registered: "+futureResult);
//	}
//	
//	protected NotificationListener createNotificationListener(final Object notificationId, final FutureResult<Object> futureResult) {
//		return new NotificationListener() {
//			public void handleNotification(Notification notification, Object handback) {
//				//Object userData = notification.getUserData();
//				if (notification instanceof ServiceEventNotification) {
//					ServiceEventNotification serviceEventNotification = (ServiceEventNotification) notification;
//					String actualEventId = serviceEventNotification.getEventId();
//					String expectedEventId = notificationId.toString();
//					
//					//System.out.println("JMX Listener["+this.hashCode()+"] type["+expectedEventId+"]: received: "+actualEventId);
//					if (actualEventId.equalsIgnoreCase(expectedEventId)) {
//						recordNotification(correlationId, actualEventId, notification);
//						if (futureResult != null)
//							futureResult.set(notification);
//						System.out.println("JMX Listener["+this.hashCode()+"] type["+expectedEventId+"]: dispatched");
//					}
//				}
//			}
//		};
//	}
	
//	protected void removeAllNotificationListeners() throws Exception {
//		Set<Object> keySet = notificationListenerStore.keySet();
//		Iterator<Object> iterator = keySet.iterator();
//		while (iterator.hasNext()) {
//			Object notificationId = iterator.next();
//			removeNotificationListener(notificationId);
//		}
//		notificationListenerStore.clear();
//	}
//	
//	protected void removeNotificationListener(Object notificationId) throws Exception {
//		NotificationListener notificationListener = notificationListenerStore.get(notificationId);
//		if (notificationListener != null) {
//			ObjectName objectName = new ObjectName(ServiceEventDispatcher.MBEAN_NAME);
//			jmxManager.removeNotificationListener(objectName, notificationListener);
//		}
//	}
	
//	protected boolean isNotificationExist(String notificationId) {
//		return isNotificationExist(correlationId, notificationId);
//	}
	
	protected boolean isNotificationExist(String correlationId, String serviceId) {
		Map<String, Notification> map = notificationStore.get(correlationId);
		if (map != null)
			return map.containsKey(serviceId);
		return false;
	}
	
	protected void recordNotification(String correlationId, String serviceId, Notification notification) {
		Map<String, Notification> map = notificationStore.get(correlationId);
		if (map == null) {
			map = new HashMap<String, Notification>();
			notificationStore.put(correlationId, map);
		}
		map.put(serviceId, notification);
	}

	protected void exportArchiveToZipFile(Archive<?> archive) throws IOException {
        String fileName = "target/" + archive.getName() + ".zip";
		OutputStream outputStream = new FileOutputStream(fileName);
        ZipExporter zipExporter = archive.as(ZipExporter.class);
        zipExporter.exportTo(outputStream);
        outputStream.close();
    }
	
	protected void fail(String message) {
		try {
			junit.framework.Assert.fail(message);
		} catch (AssertionFailedError e) {
			log.warn("AssertionFailure::" + e.toString(), e);
			throw e;
		}
	}
	
}
