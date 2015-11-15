package nam.client;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nam.client.src.main.java.BootstrapInterceptorGenerator;
import nam.client.src.main.java.CacheUnitManagerMBeanGenerator;
import nam.client.src.main.java.CacheUnitMonitorGenerator;
import nam.client.src.main.java.ClientClassGenerator;
import nam.client.src.main.java.ClientDelegateGenerator;
import nam.client.src.main.java.ClientInterceptorGenerator;
import nam.client.src.main.java.ClientInterfaceGenerator;
import nam.client.src.main.java.ClientServiceGenerator;
import nam.client.src.main.java.DataUnitManagerMBeanGenerator;
import nam.client.src.main.java.DataUnitMonitorGenerator;
import nam.client.src.main.resources.METAINF.BeansXMLGenerator;
import nam.client.src.main.resources.maven.MavenFilterPropertiesFileGenerator;
import nam.client.src.main.resources.maven.MavenServerPropertiesFileGenerator;
import nam.client.src.main.resources.maven.MavenSettingsXMLFileGenerator;
import nam.client.src.test.java.ClientProxyMainGenerator;
import nam.client.src.test.java.ClientProxyTestGenerator;
import nam.client.src.test.java.TestEARFactoryGenerator;
import nam.client.src.test.resources.JMSNodePropertyFileGenerator;
import nam.client.src.test.resources.JNDINodePropertyFileGenerator;
import nam.client.src.test.resources.JNDIPropertiesFileGenerator;
import nam.model.Application;
import nam.model.Callback;
import nam.model.Module;
import nam.model.ModuleType;
import nam.model.Process;
import nam.model.Project;
import nam.model.Service;
import nam.model.util.ModuleUtil;
import nam.model.util.ServiceUtil;
import nam.service.src.main.resources.RuntimeChecksXMLFileGenerator;
import aries.codegen.application.AbstractModuleGenerator;
import aries.generation.engine.GenerationContext;


public class ClientModuleGenerator extends AbstractModuleGenerator {

	private Set<Application> applicationsVisited;
	

	//source area
	private ClientModuleBuilder clientModuleBuilder; 
	private ClientProjectGenerator clientProjectGenerator;
	//private ServiceFaultGenerator serviceFaultGenerator;
	private ClientInterfaceGenerator clientInterfaceGenerator;
	private ClientClassGenerator clientClassGenerator;
	private ClientDelegateGenerator clientDelegateGenerator;
	private ClientServiceGenerator clientServiceGenerator;
	private ClientInterceptorGenerator clientInterceptorGenerator;
	//private ClientProxyGenerator clientProxyGenerator;
	//private ServiceManagerMBeanGenerator serviceManagerMBeanGenerator;
	//private ServiceStateManagerMBeanGenerator serviceStateManagerMBeanGenerator;
	//private ProcessStateManagerMBeanGenerator processStateManagerMBeanGenerator;
	//private ProcessStateMonitorGenerator processStateMonitorGenerator;
	private CacheUnitManagerMBeanGenerator cacheUnitManagerMBeanGenerator;
	private CacheUnitMonitorGenerator cacheUnitMonitorGenerator;
	private DataUnitManagerMBeanGenerator dataUnitManagerMBeanGenerator;
	private DataUnitMonitorGenerator dataUnitMonitorGenerator;
	private BootstrapInterceptorGenerator bootstrapInterceptorGenerator;

	//main resources area
	private RuntimeChecksXMLFileGenerator runtimeChecksXMLFileGenerator;
	
	//main resources.META-INF area
	private BeansXMLGenerator beansXMLGenerator;
	
	//main resources.maven area
	private MavenFilterPropertiesFileGenerator mavenFilterPropertiesFileGenerator;
	private MavenServerPropertiesFileGenerator mavenServerPropertiesFileGenerator;
	//private MavenSettingsXMLFileGenerator mavenSettingsXMLFileGenerator;
	
	//test sources area
	private ClientProxyMainGenerator clientProxyMainGenerator;
	private ClientProxyTestGenerator clientProxyTestGenerator;
	private TestEARFactoryGenerator testEARFactoryGenerator;

	//test resources area
	private JNDIPropertiesFileGenerator jndiPropertiesFileGenerator;
	private JNDINodePropertyFileGenerator jndiNodePropertyFileGenerator;
	private JMSNodePropertyFileGenerator jmsNodePropertyFileGenerator;
	
	//test resources.maven area
	private MavenFilterPropertiesFileGenerator testMavenFilterPropertiesFileGenerator;
	private MavenServerPropertiesFileGenerator testMavenServerPropertiesFileGenerator;
	private MavenSettingsXMLFileGenerator testMavenSettingsXMLFileGenerator;

	
	public ClientModuleGenerator(GenerationContext context) {
		applicationsVisited = new HashSet<Application>();
		clientModuleBuilder = new ClientModuleBuilder(context);
		clientProjectGenerator = new ClientProjectGenerator(context);
		//serviceFaultGenerator = new ServiceFaultGenerator(context);
		clientInterfaceGenerator = new ClientInterfaceGenerator(context);
		clientClassGenerator = new ClientClassGenerator(context);
		clientDelegateGenerator = new ClientDelegateGenerator(context);
		clientServiceGenerator = new ClientServiceGenerator(context);
		clientInterceptorGenerator = new ClientInterceptorGenerator(context);
		//clientProxyGenerator = new ClientProxyGenerator(context);
		//serviceManagerMBeanGenerator = new ServiceManagerMBeanGenerator(context);
		//serviceStateManagerMBeanGenerator = new ServiceStateManagerMBeanGenerator(context);
		//processStateManagerMBeanGenerator = new ProcessStateManagerMBeanGenerator(context);
		//processStateMonitorGenerator = new ProcessStateMonitorGenerator(context);
		cacheUnitManagerMBeanGenerator = new CacheUnitManagerMBeanGenerator(context);
		cacheUnitMonitorGenerator = new CacheUnitMonitorGenerator(context);
		dataUnitManagerMBeanGenerator = new DataUnitManagerMBeanGenerator(context);
		dataUnitMonitorGenerator = new DataUnitMonitorGenerator(context);
		bootstrapInterceptorGenerator = new BootstrapInterceptorGenerator(context);
		
		//main resources
		runtimeChecksXMLFileGenerator = new RuntimeChecksXMLFileGenerator(context);
		
		//main resources.META-INF
		beansXMLGenerator = new BeansXMLGenerator(context);

		//main resources.maven
		mavenFilterPropertiesFileGenerator = new MavenFilterPropertiesFileGenerator(context);
		mavenServerPropertiesFileGenerator = new MavenServerPropertiesFileGenerator(context);
		//mavenSettingsXMLFileGenerator = new MavenSettingsXMLFileGenerator(context);

		//test sources area
		clientProxyMainGenerator = new ClientProxyMainGenerator(context);
		clientProxyTestGenerator = new ClientProxyTestGenerator(context);
		testEARFactoryGenerator = new TestEARFactoryGenerator(context);

		//test resources area
		jndiPropertiesFileGenerator = new JNDIPropertiesFileGenerator(context);
		jndiNodePropertyFileGenerator = new JNDINodePropertyFileGenerator(context);
		jmsNodePropertyFileGenerator = new JMSNodePropertyFileGenerator(context);
		
		//test resources.maven area
		testMavenFilterPropertiesFileGenerator = new MavenFilterPropertiesFileGenerator(context);
		testMavenServerPropertiesFileGenerator = new MavenServerPropertiesFileGenerator(context);
		testMavenSettingsXMLFileGenerator = new MavenSettingsXMLFileGenerator(context);

		this.context = context;
	}

	public void initialize(Project project, Module module) throws Exception {
		clientModuleBuilder.initialize(project, module);
		//context.initializeContext(ModuleType.CLIENT);
	}
	
	public void generate(Project project, Module module) throws Exception {
		if (context.canGenerateClient()) {
			System.out.println("\nGenerating CLIENT-module: "+ModuleUtil.getModuleId(module));
			if (context.canGenerate("project"))
				clientProjectGenerator.generate(project, module);
			if (context.canGenerate("sources")) {
				generateSources(module);
				generateMainResources(project, module);
			}
			generateModule(project, module);
			if (context.canGenerate("tests"))
				generateTestReources(project, module);
			
			Application application = context.getApplication();
			//String applicationName = application.getName();
			if (!applicationsVisited.contains(application)) {
				applicationsVisited.add(application);
				if (context.canGenerate("tests"))
					generateTestSources(project, application);
			}
		}
	}

	protected void generateModule(Project project, Module module) throws Exception {
		List<Service> services = ModuleUtil.getServices(module);
		List<Process> processes = ModuleUtil.getProcesses(module);
		generateModule(project, module, services);
		generateProcesses(project, processes);
	}

	protected void generateModule(Project project, Module module, List<? extends Service> services) throws Exception {
		Iterator<? extends Service> iterator = services.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			if (service instanceof Callback == false)
				context.setService(service);
			generateModule(project, module, service);
			generateModule(project, module, ServiceUtil.getIncomingCallbacks(service));
		}
	}
	
	protected void generateModule(Project project, Module module, Service service) throws Exception {
		if (module.getType() == ModuleType.CLIENT && service instanceof Callback)
			return;
		if (context.canGenerate("sources"))
			generateSources(service);
		if (context.canGenerate("tests"))
			generateTestSources(service);
	}

//	protected void generateModuleORIG(Module module) throws Exception {
//		Map<String, PartnerLink> linkMap = context.getPartnerLinkMap(); 
//		Map<String, PartnerLinkType> linkTypeMap = context.getPartnerLinkTypeMap(); 
//		List<Link> links = ModuleUtil.getSendLinks(module);
//		Iterator<Link> iterator = links.iterator();
//		while (iterator.hasNext()) {
//			Link link = iterator.next();
//			//String linkType = link.getType();
//			String roleName = link.getRole();
//			PartnerLink partnerLink = linkMap.get(link.getName());
//			PartnerLinkType partnerLinkType = partnerLink.getPartnerLinkType();
//			Assert.notNull(partnerLinkType, "PartnerLinkType must exist");
//			PortType portType = context.getPortType(partnerLinkType, roleName);
//			Assert.notNull(portType, "PortType must exist");
//			Service service = context.getServiceByPortType(portType);
//			Assert.notNull(service, "Service must exist");
//			generateSources(service);
//			generateTests(service);
//		}
//	}
	
	protected void generateSources(Module module) throws Exception {
		bootstrapInterceptorGenerator.generateClass(clientModuleBuilder.buildBootstrapInterceptorClass(module));
	}

	protected void generateSources(Service service) throws Exception {
		context.setOptionGenerateTests(false);
		//serviceFaultGenerator.generateClasses(clientModuleBuilder.buildServiceFaults(service));
		clientInterfaceGenerator.generateInterface(clientModuleBuilder.buildClientInterface(service));
		clientClassGenerator.generateClasses(clientModuleBuilder.buildClientClasses(service));
		clientDelegateGenerator.generateClass(clientModuleBuilder.buildClientDelegateClass(service));
		clientServiceGenerator.generateClass(clientModuleBuilder.buildClientServiceClass(service));
		clientInterceptorGenerator.generateClass(clientModuleBuilder.buildClientInterceptorClass(service));
		//clientProxyGenerator.generateClass(clientModuleBuilder.buildClientProxyClass(service));
		//serviceManagerMBeanGenerator.generate(clientModuleBuilder.buildServiceManagerMBean(service));
		//serviceStateManagerMBeanGenerator.generate(clientModuleBuilder.buildServiceStateManagerMBean(service));
	}

	protected void generateProcesses(Project project, List<Process> processes) throws Exception {
		if (context.canGenerate("service", "sources")) {
			Iterator<Process> iterator = processes.iterator();
			while (iterator.hasNext()) {
				Process process = iterator.next();
				context.setProcess(process);
				//processStateManagerMBeanGenerator.generate(clientModuleBuilder.buildProcessStateManagerMBean(process));
				//processStateMonitorGenerator.generate(clientModuleBuilder.buildProcessStateMonitor(process));
				cacheUnitManagerMBeanGenerator.generateInterfaces(clientModuleBuilder.buildCacheUnitManagerMBeans(process));
				cacheUnitMonitorGenerator.generateClasses(clientModuleBuilder.buildCacheUnitStateMonitors(process));
				dataUnitManagerMBeanGenerator.generateInterfaces(clientModuleBuilder.buildDataUnitManagerMBeans(process));
				dataUnitMonitorGenerator.generateClasses(clientModuleBuilder.buildDataUnitStateMonitors(process));
			}
		}
	}

	protected void generateMainResources(Project project, Module module) throws Exception {
		runtimeChecksXMLFileGenerator.generateFile(clientModuleBuilder.buildRuntimeChecksXMLFile());
		beansXMLGenerator.generateFile(clientModuleBuilder.buildBeansXMLFile());
		mavenFilterPropertiesFileGenerator.generateFile(clientModuleBuilder.buildMavenFilterPropertiesFile());
		mavenServerPropertiesFileGenerator.generateFile(clientModuleBuilder.buildMavenServerPropertiesFile());
	}

	protected void generateTestSources(Project project, Application application) throws Exception {
		testEARFactoryGenerator.generateTest(clientModuleBuilder.buildTestEARFactoryBuilder(application));
	}
	
	protected void generateTestSources(Service service) throws Exception {
		//ModelClass serviceProxyMain = clientModuleBuilder.buildServiceProxyMain(service);
		//ModelClass serviceProxyTest = clientModuleBuilder.buildServiceProxyTest(service);
		//serviceProxyMainGenerator.generate(serviceProxyMain);
		//serviceProxyTestGenerator.generate(serviceProxyTest);
	}
	
	protected void generateTestReources(Project project, Module module) throws Exception {
		jndiPropertiesFileGenerator.generateFile(clientModuleBuilder.buildJNDIPropertiesFile());
		jndiNodePropertyFileGenerator.generateFiles(clientModuleBuilder.buildJNDINodePropertyFiles());
		jmsNodePropertyFileGenerator.generateFiles(clientModuleBuilder.buildJMSNodePropertyFiles());
		testMavenFilterPropertiesFileGenerator.generateFile(clientModuleBuilder.buildTestMavenFilterPropertiesFile());
		testMavenServerPropertiesFileGenerator.generateFile(clientModuleBuilder.buildTestMavenServerPropertiesFile());
		//testMavenSettingsXMLFileGenerator.generateFile(clientModuleBuilder.buildTestMavenSettingsXMLFile());
	}

	
}
