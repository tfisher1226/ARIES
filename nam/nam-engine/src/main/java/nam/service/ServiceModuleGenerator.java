package nam.service;

import java.util.Iterator;
import java.util.List;

import nam.client.src.main.java.ClientClassGenerator;
import nam.client.src.main.java.ClientDelegateGenerator;
import nam.client.src.main.java.ClientInterceptorGenerator;
import nam.client.src.main.java.ClientInterfaceGenerator;
import nam.client.src.main.java.ClientServiceGenerator;
import nam.client.src.main.resources.maven.MavenFilterPropertiesFileGenerator;
import nam.client.src.main.resources.maven.MavenServerPropertiesFileGenerator;
import nam.client.src.main.resources.maven.MavenSettingsXMLFileGenerator;
import nam.client.src.test.resources.JMSNodePropertyFileGenerator;
import nam.client.src.test.resources.JNDINodePropertyFileGenerator;
import nam.client.src.test.resources.JNDIPropertiesFileGenerator;
import nam.model.Callback;
import nam.model.Channel;
import nam.model.Execution;
import nam.model.Invocation;
import nam.model.Module;
import nam.model.ModuleType;
import nam.model.Process;
import nam.model.Project;
import nam.model.Service;
import nam.model.util.ModuleUtil;
import nam.model.util.ServiceUtil;
import nam.service.src.main.java.ActionClassGenerator;
import nam.service.src.main.java.BootstrapInterceptorGenerator;
import nam.service.src.main.java.CacheUnitGenerator;
import nam.service.src.main.java.CacheUnitManagerGenerator;
import nam.service.src.main.java.CacheUnitStateGenerator;
import nam.service.src.main.java.CacheUnitStateProcessorGenerator;
import nam.service.src.main.java.DataUnitGenerator;
import nam.service.src.main.java.DataUnitManagerGenerator;
import nam.service.src.main.java.DataUnitStateGenerator;
import nam.service.src.main.java.DataUnitStateProcessorGenerator;
import nam.service.src.main.java.ExecutorClassGenerator;
import nam.service.src.main.java.MessageInterceptorGenerator;
import nam.service.src.main.java.ProcessClassGenerator;
import nam.service.src.main.java.ProcessContextGenerator;
import nam.service.src.main.java.ProcessDefaultsGenerator;
import nam.service.src.main.java.ServiceHandlerGenerator;
import nam.service.src.main.java.ServiceInterfaceGenerator;
import nam.service.src.main.java.ServiceLauncherGenerator;
import nam.service.src.main.java.ServiceListenerGenerator;
import nam.service.src.main.resources.DSConfigFileGenerator;
import nam.service.src.main.resources.EhcacheJGroupsXMLFileGenerator;
import nam.service.src.main.resources.HibernatePropertiesFileGenerator;
import nam.service.src.main.resources.JAXWSHandlersXMLFileGenerator;
import nam.service.src.main.resources.JAXWSPropertiesFileGenerator;
import nam.service.src.main.resources.JMSConfigFileGenerator;
import nam.service.src.main.resources.RuntimeChecksXMLFileGenerator;
import nam.service.src.main.resources.RuntimeDefaultsXMLFileGenerator;
import nam.service.src.main.resources.SeamComponentsXMLFileGenerator;
import nam.service.src.main.resources.SeamPropertiesFileGenerator;
import nam.service.src.main.webapp.METAINF.CDIBeansXMLFileGenerator;
import nam.service.src.main.webapp.METAINF.EJBJarXMLFileGenerator;
import nam.service.src.test.java.CacheUnitManagerCITGenerator;
import nam.service.src.test.java.DataUnitManagerCITGenerator;
import nam.service.src.test.java.ServiceClassMainGenerator;
import nam.service.src.test.java.ServiceHandlerUnitTestGenerator;
import nam.service.src.test.java.ServiceListenerForJAXWSUnitTestGenerator;
import nam.service.src.test.java.ServiceListenerForJMSCITGenerator;
import nam.service.src.test.java.ServiceListenerForJMSUnitTestGenerator;
import nam.service.src.test.java.ServiceListenerForRMIUnitTestGenerator;
import nam.service.src.test.java.ServiceTXLocalITGenerator;
import nam.service.src.test.resources.ArquillianLaunchFileGenerator;
import nam.service.src.test.resources.ArquillianXMLGenerator;

import org.apache.tools.ant.types.FilterSet;

import aries.codegen.application.AbstractModuleGenerator;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;


public class ServiceModuleGenerator extends AbstractModuleGenerator {

	//source area
	private ServiceModuleBuilder serviceModuleBuilder; 
	private ServiceProjectGenerator serviceProjectGenerator;
	
	//main sources area
	private ServiceInterfaceGenerator serviceInterfaceGenerator;
	private ServiceListenerGenerator serviceListenerClassGenerator;
	private ClientInterfaceGenerator clientInterfaceGenerator;
	private ClientClassGenerator clientClassGenerator;
	//private ClientProxyGenerator clientProxyGenerator;
	private ClientDelegateGenerator clientDelegateGenerator;
	private ClientInterceptorGenerator clientInterceptorGenerator;
	private ClientServiceGenerator clientServiceGenerator;
	private ActionClassGenerator actionClassGenerator;
	private ProcessClassGenerator processClassGenerator;
	private ProcessContextGenerator processContextGenerator;
	private ProcessDefaultsGenerator processDefaultsGenerator;
	private ExecutorClassGenerator executorClassGenerator;
	private ServiceLauncherGenerator serviceLauncherGenerator;
	//private ServiceManagerGenerator serviceManagerGenerator;
	//private ServiceStateObjectGenerator serviceStateObjectGenerator;
	//private ServiceStateManagerGenerator serviceStateManagerGenerator;
	//private ServiceStateProcessorGenerator serviceStateProcessorGenerator;
	private MessageInterceptorGenerator messageInterceptorGenerator;
	private ServiceHandlerGenerator serviceHandlerGenerator;
	private CacheUnitGenerator cacheUnitGenerator;
	private CacheUnitStateGenerator cacheUnitStateGenerator;
	private CacheUnitManagerGenerator cacheUnitManagerGenerator;
	private CacheUnitStateProcessorGenerator cacheUnitStateProcessorGenerator;
	private DataUnitGenerator dataUnitGenerator;
	private DataUnitStateGenerator dataUnitStateGenerator;
	private DataUnitManagerGenerator dataUnitManagerGenerator;
	private DataUnitStateProcessorGenerator dataUnitStateProcessorGenerator;
	private BootstrapInterceptorGenerator bootstrapInterceptorGenerator;

	//main resources area
	private DSConfigFileGenerator dsConfigFileGenerator;
	private JMSConfigFileGenerator jmsConfigFileGenerator;
	private JAXWSPropertiesFileGenerator jaxwsPropertiesFileGenerator;
	private JAXWSHandlersXMLFileGenerator jaxwsHandlersXMLFileGenerator;
	private HibernatePropertiesFileGenerator hibernatePropertiesGenerator;
	private EhcacheJGroupsXMLFileGenerator ehcacheJGroupsXMLFileGenerator;
	private RuntimeChecksXMLFileGenerator runtimeChecksXMLFileGenerator;
	private RuntimeDefaultsXMLFileGenerator runtimeDefaultsXMLFileGenerator;
	private SeamPropertiesFileGenerator seamPropertiesFileGenerator;
	private SeamComponentsXMLFileGenerator seamComponentsXMLFileGenerator;
	private EJBJarXMLFileGenerator ejbJarXMLFileGenerator;
	private CDIBeansXMLFileGenerator cdiBeansXMLFileGenerator;

	//main resources.maven area
	private MavenFilterPropertiesFileGenerator mavenFilterPropertiesFileGenerator;
	private MavenServerPropertiesFileGenerator mavenServerPropertiesFileGenerator;
	private MavenSettingsXMLFileGenerator mavenSettingsXMLFileGenerator;

	//test sources area
	private ServiceClassMainGenerator serviceClassMainGenerator;
	private ServiceListenerForJAXWSUnitTestGenerator serviceListenerForJAXWSUnitTestGenerator;
	private ServiceListenerForJMSUnitTestGenerator serviceListenerForJMSUnitTestGenerator;
	private ServiceListenerForRMIUnitTestGenerator serviceListenerForRMIUnitTestGenerator;
	private ServiceHandlerUnitTestGenerator serviceHandlerUnitTestGenerator;
	private ServiceTXLocalITGenerator serviceTXLocalITGenerator;
	private CacheUnitManagerCITGenerator cacheUnitManagerCITGenerator;
	private DataUnitManagerCITGenerator dataUnitManagerCITGenerator;
	private ServiceListenerForJMSCITGenerator serviceJMSListenerCITGenerator;

	//test resources area
	private ArquillianXMLGenerator arquillianXMLGenerator;
	private ArquillianLaunchFileGenerator arquillianLaunchFileGenerator;
	private JNDIPropertiesFileGenerator jndiPropertiesFileGenerator;
	private JNDINodePropertyFileGenerator jndiNodePropertyFileGenerator;
	private JMSNodePropertyFileGenerator jmsNodePropertyFileGenerator;

	//test resources.maven area
	private MavenFilterPropertiesFileGenerator testMavenFilterPropertiesFileGenerator;
	private MavenServerPropertiesFileGenerator testMavenServerPropertiesFileGenerator;
	private MavenSettingsXMLFileGenerator testMavenSettingsXMLFileGenerator;

	private int moduleGeneratedCount;
	
	
	public ServiceModuleGenerator(GenerationContext context) {
		serviceModuleBuilder = new ServiceModuleBuilder(context);
		serviceProjectGenerator = new ServiceProjectGenerator(context);
		
		//main sources
		serviceInterfaceGenerator = new ServiceInterfaceGenerator(context);
		serviceListenerClassGenerator = new ServiceListenerGenerator(context);
		clientInterfaceGenerator = new ClientInterfaceGenerator(context);
		clientClassGenerator = new ClientClassGenerator(context);
		//clientProxyGenerator = new ClientProxyGenerator(context);
		clientDelegateGenerator = new ClientDelegateGenerator(context);
		clientInterceptorGenerator = new ClientInterceptorGenerator(context);
		clientServiceGenerator = new ClientServiceGenerator(context);
		actionClassGenerator = new ActionClassGenerator(context);
		processClassGenerator = new ProcessClassGenerator(context);
		processContextGenerator = new ProcessContextGenerator(context);
		processDefaultsGenerator = new ProcessDefaultsGenerator(context);
		executorClassGenerator = new ExecutorClassGenerator(context);
		serviceLauncherGenerator = new ServiceLauncherGenerator(context);
		//serviceManagerGenerator = new ServiceManagerGenerator(context);
		//serviceStateObjectGenerator = new ServiceStateObjectGenerator(context);
		//serviceStateManagerGenerator = new ServiceStateManagerGenerator(context);
		//serviceStateProcessorGenerator = new ServiceStateProcessorGenerator(context);
		messageInterceptorGenerator = new MessageInterceptorGenerator(context);
		serviceHandlerGenerator = new ServiceHandlerGenerator(context);
		cacheUnitGenerator = new CacheUnitGenerator(context);
		cacheUnitStateGenerator = new CacheUnitStateGenerator(context);
		cacheUnitManagerGenerator = new CacheUnitManagerGenerator(context);
		cacheUnitStateProcessorGenerator = new CacheUnitStateProcessorGenerator(context);
		dataUnitGenerator = new DataUnitGenerator(context);
		dataUnitStateGenerator = new DataUnitStateGenerator(context);
		dataUnitManagerGenerator = new DataUnitManagerGenerator(context);
		dataUnitStateProcessorGenerator = new DataUnitStateProcessorGenerator(context);
		bootstrapInterceptorGenerator = new BootstrapInterceptorGenerator(context);

		//test sources
		serviceClassMainGenerator = new ServiceClassMainGenerator(context);
		serviceListenerForJAXWSUnitTestGenerator = new ServiceListenerForJAXWSUnitTestGenerator(context);
		serviceListenerForJMSUnitTestGenerator = new ServiceListenerForJMSUnitTestGenerator(context);
		serviceListenerForRMIUnitTestGenerator = new ServiceListenerForRMIUnitTestGenerator(context);
		serviceHandlerUnitTestGenerator = new ServiceHandlerUnitTestGenerator(context);
		serviceTXLocalITGenerator = new ServiceTXLocalITGenerator(context);
		cacheUnitManagerCITGenerator = new CacheUnitManagerCITGenerator(context);
		dataUnitManagerCITGenerator = new DataUnitManagerCITGenerator(context);
		serviceJMSListenerCITGenerator = new ServiceListenerForJMSCITGenerator(context);

		//main resources
		dsConfigFileGenerator = new DSConfigFileGenerator(context);
		jmsConfigFileGenerator = new JMSConfigFileGenerator(context);
		jndiPropertiesFileGenerator = new JNDIPropertiesFileGenerator(context);
		jaxwsPropertiesFileGenerator = new JAXWSPropertiesFileGenerator(context);
		jaxwsHandlersXMLFileGenerator = new JAXWSHandlersXMLFileGenerator(context);
		hibernatePropertiesGenerator = new HibernatePropertiesFileGenerator(context);
		ehcacheJGroupsXMLFileGenerator = new EhcacheJGroupsXMLFileGenerator(context);
		runtimeChecksXMLFileGenerator = new RuntimeChecksXMLFileGenerator(context);
		runtimeDefaultsXMLFileGenerator = new RuntimeDefaultsXMLFileGenerator(context);
		seamPropertiesFileGenerator = new SeamPropertiesFileGenerator(context);
		seamComponentsXMLFileGenerator = new SeamComponentsXMLFileGenerator(context);
		ejbJarXMLFileGenerator = new EJBJarXMLFileGenerator(context);
		cdiBeansXMLFileGenerator = new CDIBeansXMLFileGenerator(context);

		//main resources.maven area
		mavenFilterPropertiesFileGenerator = new MavenFilterPropertiesFileGenerator(context);
		mavenServerPropertiesFileGenerator = new MavenServerPropertiesFileGenerator(context);
		mavenSettingsXMLFileGenerator = new MavenSettingsXMLFileGenerator(context);

		//test resources area
		jndiPropertiesFileGenerator = new JNDIPropertiesFileGenerator(context);
		jndiNodePropertyFileGenerator = new JNDINodePropertyFileGenerator(context);
		jmsNodePropertyFileGenerator = new JMSNodePropertyFileGenerator(context);
		arquillianXMLGenerator = new ArquillianXMLGenerator(context);
		arquillianLaunchFileGenerator = new ArquillianLaunchFileGenerator(context);

		//test resources.maven area
		testMavenFilterPropertiesFileGenerator = new MavenFilterPropertiesFileGenerator(context);
		testMavenServerPropertiesFileGenerator = new MavenServerPropertiesFileGenerator(context);
		testMavenSettingsXMLFileGenerator = new MavenSettingsXMLFileGenerator(context);

		this.context = context;
	}

//	public boolean canGenerate() {
//		context.canGenerate("service", values);
//	}
	
	public void initialize(Project project, Module module) throws Exception {
		serviceModuleBuilder.initialize(project, module);
	}
	
	public void generate(Project project, Module module) throws Exception {
		if (context.canGenerateService()) {
			System.out.println("\nGenerating SERVICE-module: "+ModuleUtil.getModuleId(module));
			if (context.canGenerate("project"))
				serviceProjectGenerator.generate(project, module);
			if (context.canGenerate("sources"))
				generateMainSources(project, module);
			if (context.canGenerate("tests"))
				generateTestSources(project, module);
		}
	}
	
	protected void generateMainSources(Project project, Module module) throws Exception {
		context.initializeContext(module);
		List<Service> services = ModuleUtil.getServices(module);
		List<Process> processes = ModuleUtil.getProcesses(module);
		if (context.canGenerate("project"))
			generateFiles(project, services);
		generateSourcesForModule(project, module);
		generateSourcesForServices(project, services);
		generateSourcesForProcesses(project, processes);
		generateMainResources(project, module);
		moduleGeneratedCount++;
	}
	
	protected void generateMainResources(Project project, Module module) throws Exception {
		if (module.getType() == ModuleType.SERVICE) {
//			if (module.getName().equals("bookshop2-supplier-service"))
//				System.out.println();

			jaxwsPropertiesFileGenerator.generateFile(serviceModuleBuilder.buildJAXWSPropertiesFile());
			jaxwsHandlersXMLFileGenerator.generateFiles(serviceModuleBuilder.buildJAXWSHandlersXMLFiles());
			if (ModuleUtil.getCaches(module).size() > 0)
				ehcacheJGroupsXMLFileGenerator.generateFile(serviceModuleBuilder.buildEhcacheJGroupsXMLFile());
			runtimeChecksXMLFileGenerator.generateFile(serviceModuleBuilder.buildRuntimeChecksXMLFile());
			runtimeDefaultsXMLFileGenerator.generateFile(serviceModuleBuilder.buildRuntimeDefaultsXMLFile());
			
			switch (context.getServiceLayerBeanType()) {
			case SEAM:
				seamComponentsXMLFileGenerator.generateFile(serviceModuleBuilder.buildSeamComponentsXMLFile());
				seamPropertiesFileGenerator.generateFile(serviceModuleBuilder.buildSeamPropertiesFile());
			}
			
			ejbJarXMLFileGenerator.generateFile(serviceModuleBuilder.buildEjbJarXMLFile());
			cdiBeansXMLFileGenerator.generateFile(serviceModuleBuilder.buildCDIBeansXMLFile());
			mavenFilterPropertiesFileGenerator.generateFile(serviceModuleBuilder.buildMavenFilterPropertiesFile());
			mavenServerPropertiesFileGenerator.generateFile(serviceModuleBuilder.buildMavenServerPropertiesFile());
		}
	}

	protected void generateFiles(Project project, List<Service> services) throws Exception {
		if (context.isOptionLimitToSingleModule() && moduleGeneratedCount == 0) {
			FilterSet filterSet = createFilterSet("-service");
			generateFile("src/main/resources", "log4j.xml", filterSet);
			//generateFile("src/main/resources", "components.properties", filterSet);
			//generateFile("src/main/resources", "handler-chain.xml", filterSet);
		}
	}
	
	protected void generateTestResources(Project project, Module module) throws Exception {
		if (module.getType() == ModuleType.SERVICE) {
			dsConfigFileGenerator.generateFiles(serviceModuleBuilder.buildTestDSConfigFiles());
			jmsConfigFileGenerator.generateFiles(serviceModuleBuilder.buildTestJMSConfigFiles());
			hibernatePropertiesGenerator.generateFile(serviceModuleBuilder.buildTestHibernateProperties());

			jndiPropertiesFileGenerator.generateFile(serviceModuleBuilder.buildJNDIPropertiesFile());
			jndiNodePropertyFileGenerator.generateFiles(serviceModuleBuilder.buildJNDINodePropertyFiles());
			jmsNodePropertyFileGenerator.generateFiles(serviceModuleBuilder.buildJMSNodePropertyFiles());

			arquillianXMLGenerator.generateFile(serviceModuleBuilder.buildArquillianXMLFile());
			arquillianLaunchFileGenerator.generateFile(serviceModuleBuilder.buildArquillianLaunchFile());
			testMavenSettingsXMLFileGenerator.generateFile(serviceModuleBuilder.buildTestMavenSettingsXMLFile());
			testMavenFilterPropertiesFileGenerator.generateFile(serviceModuleBuilder.buildTestMavenFilterPropertiesFile());
			testMavenServerPropertiesFileGenerator.generateFile(serviceModuleBuilder.buildTestMavenServerPropertiesFile());

			switch (context.getServiceLayerBeanType()) {
			case SEAM:
				seamComponentsXMLFileGenerator.generateFile(serviceModuleBuilder.buildTestComponentsXML());
				break;
			}
		}
	}
	
	protected void generateSourcesForModule(Project project, Module module) throws Exception {
		bootstrapInterceptorGenerator.generateClass(serviceModuleBuilder.buildBootstrapInterceptorClass(module));
	}
	
	protected void generateSourcesForServices(Project project, List<? extends Service> services) throws Exception {
		Iterator<? extends Service> iterator = services.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			if (service instanceof Callback == false)
				context.setService(service);
			generateSourcesForServices(project, service);
			generateSourcesForServices(project, ServiceUtil.getIncomingCallbacks(service));
		}
	}
	
	protected void generateSourcesForServices(Project project, Service service) throws Exception {
		List<Channel> channels = ServiceUtil.getChannels(service);
		Iterator<Channel> iterator = channels.iterator();
		while (iterator.hasNext()) {
			Channel channel = iterator.next();
			generateSourcesForServices(project, service, channel);
			generateSourcesForServices(project, ServiceUtil.getIncomingCallbacks(service));
		}
	}
	

	protected void generateSourcesForServices(Project project, Service service, Channel channel) throws Exception {
		generateSourcesForService(service, channel);
	}
	
	protected void generateSourcesForService(Service service, Channel channel) throws Exception {
		context.setOptionGenerateTests(true);
			
//		ModelInterface serviceInterface = serviceModuleBuilder.buildServiceInterface(service);
//		ModelClass serviceClass = serviceModuleBuilder.buildServiceClass(service);
//		//ModelClass actionClass = serviceModuleBuilder.buildActionClass(service);
//		ModelClass serviceLauncherClass = serviceModuleBuilder.buildServiceLauncherClass(service);
//		//ModelClass serviceManagerClass = serviceModuleBuilder.buildServiceManagerClass(service);
//		//ModelClass serviceStateObjectClass = serviceModuleBuilder.buildServiceStateObjectClass(service);
//		//ModelClass serviceStateManagerClass = serviceModuleBuilder.buildServiceStateManagerClass(service);
//		//ModelClass serviceStateProcessorClass = serviceModuleBuilder.buildServiceStateProcessorClass(service);
//		ModelClass serviceTransactorClass = serviceModuleBuilder.buildServiceTransactorClass(service);
			
		serviceLauncherGenerator.generateClass(serviceModuleBuilder.buildServiceLauncherClass(service));
		serviceInterfaceGenerator.generateInterface(serviceModuleBuilder.buildServiceInterface(service));
		serviceListenerClassGenerator.generateClasses(serviceModuleBuilder.buildServiceListenerClasses(service, channel));
		
		if (context.isEnabled("generateMessageLevelTransport")) {
			actionClassGenerator.generateClasses(serviceModuleBuilder.buildActionClasses(service));
		}
		
		//serviceManagerGenerator.generate(serviceModuleBuilder.buildServiceManagerClass(service));
		//serviceStateObjectGenerator.generate(serviceModuleBuilder.buildServiceStateObjectClass(service));
		//serviceStateManagerGenerator.generate(serviceModuleBuilder.buildServiceStateManagerClass(service));
		//serviceStateProcessorGenerator.generate(serviceModuleBuilder.buildServiceStateProcessorClass(service));
		messageInterceptorGenerator.generateClass(serviceModuleBuilder.buildMessageInterceptorClass(service));
		serviceHandlerGenerator.generateInterface(serviceModuleBuilder.buildServiceHandlerInterface(service));
		serviceHandlerGenerator.generateClass(serviceModuleBuilder.buildServiceHandlerClass(service));
		
		List<Callback> outgoingCallbacks = ServiceUtil.getOutgoingCallbacks(service);
		Iterator<Callback> iterator = outgoingCallbacks.iterator();
		while (iterator.hasNext()) {
			Callback callback = iterator.next();
			clientInterfaceGenerator.generateInterface(serviceModuleBuilder.buildClientInterface(callback));
			clientClassGenerator.generateClasses(serviceModuleBuilder.buildClientClasses(callback));
			//clientProxyGenerator.generateClass(serviceModuleBuilder.buildClientProxyClass(callback));
			clientDelegateGenerator.generateClass(serviceModuleBuilder.buildClientDelegateClass(callback));
			clientInterceptorGenerator.generateClass(serviceModuleBuilder.buildClientInterceptorClass(callback));
			clientServiceGenerator.generateClass(serviceModuleBuilder.buildClientServiceClass(callback));
		}

		if (context.canGenerate("service", "sources", "bpel")) {
			List<Execution> executions = ServiceUtil.getExecutions(service);
			Iterator<Execution> iterator2 = executions.iterator();
			while (iterator2.hasNext()) {
				Execution execution = (Execution) iterator2.next();
				generateSourcesForService(service, execution);
			}
		}
	}

	protected void generateSourcesForService(Service service, Execution execution) throws Exception {
		List<Invocation> actions = execution.getInvocations();
		Iterator<Invocation> iterator = actions.iterator();
		while (iterator.hasNext()) {
			Invocation action = (Invocation) iterator.next();
			generateSourcesForService(service, action);
		}
	}
	
	protected void generateSourcesForService(Service service, Invocation action) throws Exception {
		if (context.canGenerate("service", "sources", "bpel")) {
			ModelClass actionClass = serviceModuleBuilder.buildActionClass(service, action);
			actionClassGenerator.generateClass(actionClass);
		}
	}

	protected void generateSourcesForProcesses(Project project, List<Process> processes) throws Exception {
		if (context.canGenerate("service", "sources", "bpel")) {
			//TODO
		}
		if (context.canGenerate("service", "sources")) {
			Iterator<Process> iterator = processes.iterator();
			while (iterator.hasNext()) {
				Process process = iterator.next();
				context.setProcess(process);
				generateSourcesForProcess(process);
			}
		}
	}
	
	protected void generateSourcesForProcess(Process process) throws Exception {
		processClassGenerator.generateClass(serviceModuleBuilder.buildProcessClass(process));
		processContextGenerator.generateClass(serviceModuleBuilder.buildProcessContextClass(process));
		processDefaultsGenerator.generateClass(serviceModuleBuilder.buildProcessDefaultsClass(process));
		cacheUnitGenerator.generateInterfaces(serviceModuleBuilder.buildCacheUnitInterfaces(process));
		cacheUnitGenerator.generateClasses(serviceModuleBuilder.buildCacheUnitClasses(process));
		cacheUnitStateGenerator.generateClasses(serviceModuleBuilder.buildCacheUnitStateClasses(process));
		cacheUnitManagerGenerator.generateClasses(serviceModuleBuilder.buildCacheUnitManagerClasses(process));
		cacheUnitStateProcessorGenerator.generateClasses(serviceModuleBuilder.buildCacheUnitStateProcessorClasses(process));
		dataUnitGenerator.generateClasses(serviceModuleBuilder.buildDataUnits(process));
		dataUnitStateGenerator.generateClasses(serviceModuleBuilder.buildDataUnitStateClasses(process));
		dataUnitManagerGenerator.generateClasses(serviceModuleBuilder.buildDataUnitManagerClasses(process));
		dataUnitStateProcessorGenerator.generateClasses(serviceModuleBuilder.buildDataUnitStateProcessorClasses(process));
		executorClassGenerator.generateClasses(serviceModuleBuilder.buildExecutorClasses(process));
	}
	

	protected void generateTestSources(Project project, Module module) throws Exception {
		List<Service> services = ModuleUtil.getServices(module);
		List<Process> processes = ModuleUtil.getProcesses(module);
		generateTestSourcesForServices(project, services);
		generateTestSourcesForProcesses(project, processes);
		generateTestResources(project, module);
	}
	
	protected void generateTestSourcesForServices(Project project, List<? extends Service> services) throws Exception {
		Iterator<? extends Service> iterator = services.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			if (ServiceUtil.isOutgoingCallback(service))
				continue;
			if (service instanceof Callback == false)
				context.setService(service);
			generateTestSourcesForServices(project, service);
			generateTestSourcesForServices(project, ServiceUtil.getCallbacks(service));
		}
	}

	protected void generateTestSourcesForServices(Project project, Service service) throws Exception {
		generateTestsForService(service);
	}
	
	protected void generateTestsForService(Service service) throws Exception {
		if (service.getClassName().endsWith("Manager"))
			//skip Managers for now
			return;
		
		context.setOptionGenerateTests(false);
		serviceClassMainGenerator.generateTest(serviceModuleBuilder.buildServiceClassMain(service));
		serviceListenerForJAXWSUnitTestGenerator.generateTest(serviceModuleBuilder.buildServiceListenerForJAXWSTest(service));
		serviceListenerForJMSUnitTestGenerator.generateTest(serviceModuleBuilder.buildServiceListenerForJMSTest(service));
		serviceListenerForRMIUnitTestGenerator.generateTest(serviceModuleBuilder.buildServiceListenerForRMITest(service));
		serviceHandlerUnitTestGenerator.generateTest(serviceModuleBuilder.buildServiceHandlerUnitTest(service));
		//serviceTXLocalITGenerator.generateTest(serviceModuleBuilder.buildServiceTXLocalITTest(service));
		if (ServiceUtil.getDefaultOperation(service) != null && service instanceof Callback == false) {
			serviceJMSListenerCITGenerator.generateTest(serviceModuleBuilder.buildServiceJMSListenerCITClass(service));
		}
	}
	
	protected void generateTestSourcesForProcesses(Project project, List<Process> processes) throws Exception {
		Iterator<Process> iterator = processes.iterator();
		while (iterator.hasNext()) {
			Process process = iterator.next();
			generateTestSourcesForProcess(process);
		}
	}
	
	protected void generateTestSourcesForProcess(Process process) throws Exception {
		context.setOptionGenerateTests(false);
		//processClassGenerator.generateTest(serviceModuleBuilder.buildProcessClassTest(process));
		cacheUnitManagerCITGenerator.generateTests(serviceModuleBuilder.buildCacheUnitManagerCITClasses(process));
		dataUnitManagerCITGenerator.generateTests(serviceModuleBuilder.buildDataUnitManagerCITClasses(process));
	}
	
}
