package nam.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nam.client.src.main.java.ClientClassBuilder;
import nam.client.src.main.java.ClientDelegateBuilder;
import nam.client.src.main.java.ClientInterceptorBuilder;
import nam.client.src.main.java.ClientInterfaceBuilder;
import nam.client.src.main.java.ClientServiceBuilder;
import nam.client.src.main.resources.maven.MavenFilterPropertiesFileBuilder;
import nam.client.src.main.resources.maven.MavenServerPropertiesFileBuilder;
import nam.client.src.main.resources.maven.MavenSettingsXMLFileBuilder;
import nam.client.src.test.resources.JMSNodePropertyFileBuilder;
import nam.client.src.test.resources.JNDINodePropertyFileBuilder;
import nam.client.src.test.resources.JNDIPropertiesFileBuilder;
import nam.model.Channel;
import nam.model.Invocation;
import nam.model.Module;
import nam.model.Process;
import nam.model.Project;
import nam.model.Service;
import nam.model.util.ModuleUtil;
import nam.service.src.main.java.ActionClassBuilder;
import nam.service.src.main.java.BootstrapInterceptorBuilder;
import nam.service.src.main.java.CacheUnitBuilder;
import nam.service.src.main.java.CacheUnitManagerBuilder;
import nam.service.src.main.java.CacheUnitStateBuilder;
import nam.service.src.main.java.CacheUnitStateProcessorBuilder;
import nam.service.src.main.java.DataUnitBuilder;
import nam.service.src.main.java.DataUnitManagerBuilder;
import nam.service.src.main.java.DataUnitStateBuilder;
import nam.service.src.main.java.DataUnitStateProcessorBuilder;
import nam.service.src.main.java.ExecutorClassBuilder;
import nam.service.src.main.java.MessageInterceptorBuilder;
import nam.service.src.main.java.ProcessClassBuilder;
import nam.service.src.main.java.ProcessContextBuilder;
import nam.service.src.main.java.ProcessDefaultsBuilder;
import nam.service.src.main.java.ServiceHandlerBuilder;
import nam.service.src.main.java.ServiceInterfaceBuilder;
import nam.service.src.main.java.ServiceLauncherBuilder;
import nam.service.src.main.java.ServiceListenerBuilder;
import nam.service.src.main.resources.DSConfigFileBuilder;
import nam.service.src.main.resources.EhcacheJGroupsXMLFileBuilder;
import nam.service.src.main.resources.HibernatePropertiesFileBuilder;
import nam.service.src.main.resources.JAXWSHandlersXMLFileBuilder;
import nam.service.src.main.resources.JAXWSPropertiesFileBuilder;
import nam.service.src.main.resources.JMSConfigFileBuilder;
import nam.service.src.main.resources.RuntimeChecksXMLFileBuilder;
import nam.service.src.main.resources.RuntimeDefaultsXMLFileBuilder;
import nam.service.src.main.resources.SeamComponentsXMLFileBuilder;
import nam.service.src.main.resources.SeamPropertiesFileBuilder;
import nam.service.src.main.webapp.METAINF.CDIBeansXMLFileBuilder;
import nam.service.src.main.webapp.METAINF.EJBJarXMLFileBuilder;
import nam.service.src.test.java.CacheUnitManagerCITBuilder;
import nam.service.src.test.java.DataUnitManagerCITBuilder;
import nam.service.src.test.java.ServiceClassMainBuilder;
import nam.service.src.test.java.ServiceHandlerUnitTestBuilder;
import nam.service.src.test.java.ServiceListenerForJAXWSUnitTestBuilder;
import nam.service.src.test.java.ServiceListenerForJMSCITBuilder;
import nam.service.src.test.java.ServiceListenerForJMSUnitTestBuilder;
import nam.service.src.test.java.ServiceListenerForRMIUnitTestBuilder;
import nam.service.src.test.java.ServiceTXLocalITBuilder;
import nam.service.src.test.resources.ArquillianLaunchFileBuilder;
import nam.service.src.test.resources.ArquillianXMLBuilder;
import aries.codegen.application.AbstractModuleBuilder;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelFile;
import aries.generation.model.ModelInterface;


public class ServiceModuleBuilder extends AbstractModuleBuilder {

	//main sources area
	private ServiceModuleHelper serviceModuleHelper;
	private ServiceInterfaceBuilder serviceInterfaceBuilder;
	private ServiceListenerBuilder serviceListenerBuilder;
	private ServiceLauncherBuilder serviceLauncherBuilder;
	//private ServiceManagerBuilder serviceManagerBuilder;
	private ClientInterfaceBuilder clientInterfaceBuilder;
	private ClientClassBuilder clientClassBuilder;
	//private ClientProxyBuilder clientProxyBuilder;
	private ClientDelegateBuilder clientDelegateBuilder;
	private ClientInterceptorBuilder clientInterceptorBuilder;
	private ClientServiceBuilder clientServiceBuilder;
	private ActionClassBuilder actionClassBuilder;
	private ProcessClassBuilder processClassBuilder;
	private ProcessContextBuilder processContextBuilder;
	private ProcessDefaultsBuilder processDefaultsBuilder;
	private ExecutorClassBuilder executorClassBuilder;
	//private ProcessStateObjectBuilder processStateObjectBuilder;
	//private ProcessStateManagerBuilder processStateManagerBuilder;
	//private ProcessStateProcessorBuilder processStateProcessorBuilder;
	private MessageInterceptorBuilder messageInterceptorBuilder;
	private ServiceHandlerBuilder serviceHandlerBuilder;
	private CacheUnitBuilder cacheUnitBuilder;
	private CacheUnitStateBuilder cacheUnitStateBuilder;
	private CacheUnitManagerBuilder cacheUnitManagerBuilder;
	private CacheUnitStateProcessorBuilder cacheUnitStateProcessorBuilder;
	private DataUnitBuilder dataUnitBuilder;
	private DataUnitStateBuilder dataUnitStateBuilder;
	private DataUnitManagerBuilder dataUnitManagerBuilder;
	private DataUnitStateProcessorBuilder dataUnitStateProcessorBuilder;
	private BootstrapInterceptorBuilder bootstrapInterceptorBuilder;

	//main resources area
	private DSConfigFileBuilder dsConfigFileBuilder;
	private JMSConfigFileBuilder jmsConfigFileBuilder;
	private JAXWSPropertiesFileBuilder jaxwsPropertiesFileBuilder;
	private JAXWSHandlersXMLFileBuilder jaxwsHandlersXMLFileBuilder;
	private HibernatePropertiesFileBuilder hibernatePropertiesFileBuilder;
	private EhcacheJGroupsXMLFileBuilder ehcacheJGroupsXMLFileBuilder;
	private RuntimeChecksXMLFileBuilder runtimeChecksXMLFileBuilder;
	private RuntimeDefaultsXMLFileBuilder runtimeDefaultsXMLFileBuilder;
	private SeamComponentsXMLFileBuilder seamComponentsXMLFileBuilder;
	private SeamPropertiesFileBuilder seamPropertiesFileBuilder;

	//main resources.META-INF area
	private EJBJarXMLFileBuilder ejbJarXMLBuilder;
	private CDIBeansXMLFileBuilder cdiBeansXMLBuilder;

	//main resources.maven area
	private MavenFilterPropertiesFileBuilder mavenFilterPropertiesFileBuilder;
	private MavenServerPropertiesFileBuilder mavenServerPropertiesFileBuilder;
	private MavenSettingsXMLFileBuilder mavenSettingsXMLFileBuilder;

	//test sources area
	private ServiceClassMainBuilder serviceClassMainBuilder;
	private ServiceListenerForJAXWSUnitTestBuilder serviceListenerForJAXWSUnitTestBuilder;
	private ServiceListenerForJMSUnitTestBuilder serviceListenerForJMSUnitTestBuilder;
	private ServiceListenerForRMIUnitTestBuilder serviceListenerForRMIUnitTestBuilder;
	private ServiceHandlerUnitTestBuilder serviceHandlerUnitTestBuilder;
	private ServiceTXLocalITBuilder serviceTXLocalITBuilder;
	private CacheUnitManagerCITBuilder cacheUnitManagerCITBuilder;
	private DataUnitManagerCITBuilder dataUnitManagerCITBuilder;
	private ServiceListenerForJMSCITBuilder serviceJMSListenerCITBuilder;

	//test resources area
	private ArquillianXMLBuilder arquillianXMLBuilder;
	private ArquillianLaunchFileBuilder arquillianLaunchFileBuilder;
	private JNDIPropertiesFileBuilder jndiPropertiesFileBuilder;
	private JNDINodePropertyFileBuilder jndiNodePropertyFileBuilder;
	private JMSNodePropertyFileBuilder jmsNodePropertyFileBuilder;
	
	
	public ServiceModuleBuilder(GenerationContext context) {
		//main sources area
		serviceModuleHelper = new ServiceModuleHelper(context);
		serviceInterfaceBuilder = new ServiceInterfaceBuilder(context);
		serviceListenerBuilder = new ServiceListenerBuilder(context);
		serviceLauncherBuilder = new ServiceLauncherBuilder(context);
		//serviceManagerBuilder = new ServiceManagerBuilder(context);
		clientInterfaceBuilder = new ClientInterfaceBuilder(context);
		clientClassBuilder = new ClientClassBuilder(context);
		//clientProxyBuilder = new ClientProxyBuilder(context);
		clientDelegateBuilder = new ClientDelegateBuilder(context);
		clientInterceptorBuilder = new ClientInterceptorBuilder(context);
		clientServiceBuilder = new ClientServiceBuilder(context);
		actionClassBuilder = new ActionClassBuilder(context);
		processClassBuilder = new ProcessClassBuilder(context);
		processContextBuilder = new ProcessContextBuilder(context);
		processDefaultsBuilder = new ProcessDefaultsBuilder(context);
		//processStateObjectBuilder = new ProcessStateObjectBuilder(context);
		//processStateManagerBuilder = new ProcessStateManagerBuilder(context);
		//processStateProcessorBuilder = new ProcessStateProcessorBuilder(context);
		executorClassBuilder = new ExecutorClassBuilder(context);
		messageInterceptorBuilder = new MessageInterceptorBuilder(context);
		serviceHandlerBuilder = new ServiceHandlerBuilder(context);
		cacheUnitBuilder = new CacheUnitBuilder(context);
		cacheUnitStateBuilder = new CacheUnitStateBuilder(context);
		cacheUnitManagerBuilder = new CacheUnitManagerBuilder(context);
		cacheUnitStateProcessorBuilder = new CacheUnitStateProcessorBuilder(context);
		dataUnitBuilder = new DataUnitBuilder(context);
		dataUnitStateBuilder = new DataUnitStateBuilder(context);
		dataUnitManagerBuilder = new DataUnitManagerBuilder(context);
		dataUnitStateProcessorBuilder = new DataUnitStateProcessorBuilder(context);
		bootstrapInterceptorBuilder = new BootstrapInterceptorBuilder(context);

		//main resources area
		dsConfigFileBuilder = new DSConfigFileBuilder(context);
		jmsConfigFileBuilder = new JMSConfigFileBuilder(context);
		jaxwsPropertiesFileBuilder = new JAXWSPropertiesFileBuilder(context);
		jaxwsHandlersXMLFileBuilder = new JAXWSHandlersXMLFileBuilder(context);
		hibernatePropertiesFileBuilder = new HibernatePropertiesFileBuilder(context);
		ehcacheJGroupsXMLFileBuilder = new EhcacheJGroupsXMLFileBuilder(context);
		runtimeChecksXMLFileBuilder = new RuntimeChecksXMLFileBuilder(context);
		runtimeDefaultsXMLFileBuilder = new RuntimeDefaultsXMLFileBuilder(context);
		seamPropertiesFileBuilder = new SeamPropertiesFileBuilder(context);
		seamComponentsXMLFileBuilder = new SeamComponentsXMLFileBuilder(context);
		ejbJarXMLBuilder = new EJBJarXMLFileBuilder(context);
		cdiBeansXMLBuilder = new CDIBeansXMLFileBuilder(context);

		//main resources.maven area
		mavenFilterPropertiesFileBuilder = new MavenFilterPropertiesFileBuilder(context);
		mavenServerPropertiesFileBuilder = new MavenServerPropertiesFileBuilder(context);
		mavenSettingsXMLFileBuilder = new MavenSettingsXMLFileBuilder(context);

		//test sources area
		serviceClassMainBuilder = new ServiceClassMainBuilder(context);
		serviceListenerForJAXWSUnitTestBuilder = new ServiceListenerForJAXWSUnitTestBuilder(context);
		serviceListenerForJMSUnitTestBuilder = new ServiceListenerForJMSUnitTestBuilder(context);
		serviceListenerForRMIUnitTestBuilder = new ServiceListenerForRMIUnitTestBuilder(context);
		serviceHandlerUnitTestBuilder = new ServiceHandlerUnitTestBuilder(context);
		serviceTXLocalITBuilder = new ServiceTXLocalITBuilder(context);
		cacheUnitManagerCITBuilder = new CacheUnitManagerCITBuilder(context);
		dataUnitManagerCITBuilder = new DataUnitManagerCITBuilder(context);
		serviceJMSListenerCITBuilder = new ServiceListenerForJMSCITBuilder(context);
		
		//test resources area
		arquillianXMLBuilder = new ArquillianXMLBuilder(context);
		arquillianLaunchFileBuilder = new ArquillianLaunchFileBuilder(context);
		jndiPropertiesFileBuilder = new JNDIPropertiesFileBuilder(context);
		jndiNodePropertyFileBuilder = new JNDINodePropertyFileBuilder(context);
		jmsNodePropertyFileBuilder = new JMSNodePropertyFileBuilder(context);
		this.context = context;
	}
	
	public void initialize(Project project) throws Exception {
		Set<Module> modules = ModuleUtil.getServiceModulesToGenerate(project);
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			initialize(project, module);
		}
	}

	public void initialize(Project project, Module module) throws Exception {
		serviceModuleHelper.initialize(project, module);
		context.initialize(project, module);
	}

	
	/*
	 * Build main sources (module-wide)
	 * --------------------------------
	 */

	public ModelClass buildBootstrapInterceptorClass(Module module) throws Exception {
		ModelClass modelClass = bootstrapInterceptorBuilder.buildClass(module);
		return modelClass;
	}

	
	/*
	 * Build main sources (service-specific)
	 * -------------------------------------
	 */

	public ModelInterface buildServiceInterface(Service service) throws Exception {
		ModelInterface modelInterface = serviceInterfaceBuilder.buildInterface(service);
		return modelInterface;
	}

	public List<ModelClass> buildServiceListenerClasses(Service service, Channel channel) throws Exception {
		List<ModelClass> modelClasses = serviceListenerBuilder.buildClasses(service, channel);
		return modelClasses;
	}

	public List<ModelClass> buildActionClasses(Service service) throws Exception {
		List<ModelClass> modelClasses = actionClassBuilder.buildClasses(service);
		return modelClasses;
	}

	public ModelClass buildServiceLauncherClass(Service service) throws Exception {
		ModelClass modelClass = serviceLauncherBuilder.build(service);
		return modelClass;
	}

//	public ModelClass buildServiceManagerClass(Service service) throws Exception {
//		ModelClass modelClass = serviceManagerBuilder.build(service);
//		return modelClass;
//	}

//	public ModelClass buildServiceStateObjectClass(Service service) throws Exception {
//		ModelClass modelClass = serviceStateObjectBuilder.build(service);
//		return modelClass;
//	}
//
//	public ModelClass buildServiceStateManagerClass(Service service) throws Exception {
//		ModelClass modelClass = serviceStateManagerBuilder.build(service);
//		return modelClass;
//	}
//
//	public ModelClass buildServiceStateProcessorClass(Service service) throws Exception {
//		ModelClass modelClass = serviceStateProcessorBuilder.build(service);
//		return modelClass;
//	}

	public ModelClass buildMessageInterceptorClass(Service service) throws Exception {
		ModelClass modelClass = messageInterceptorBuilder.buildClass(service);
		return modelClass;
	}
	
	public ModelInterface buildServiceHandlerInterface(Service service) throws Exception {
		ModelInterface modelInterface = serviceHandlerBuilder.buildInterface(service);
		return modelInterface;
	}
	
	public ModelClass buildServiceHandlerClass(Service service) throws Exception {
		ModelClass modelClass = serviceHandlerBuilder.buildClass(service);
		return modelClass;
	}

	
	/*
	 * Build main sources (service-specific) for outgoing callbacks
	 * ------------------------------------------------------------
	 */
	
	public ModelInterface buildClientInterface(Service service) throws Exception {
		ModelInterface modelInterface = clientInterfaceBuilder.buildInterface(service);
		return modelInterface;
	}

	public List<ModelClass> buildClientClasses(Service service) throws Exception {
		List<ModelClass> modelClasses = clientClassBuilder.buildClasses(service);
		return modelClasses;
	}

//	public ModelClass buildClientProxyClass(Service service) throws Exception {
//		ModelClass modelClass = clientProxyBuilder.build(service);
//		return modelClass;
//	}

	public ModelClass buildClientDelegateClass(Service service) throws Exception {
		ModelClass modelClass = clientDelegateBuilder.buildClass(service);
		return modelClass;
	}
	
	public ModelClass buildClientInterceptorClass(Service service) throws Exception {
		ModelClass modelClass = clientInterceptorBuilder.buildClass(service);
		return modelClass;
	}

	public ModelClass buildClientServiceClass(Service service) throws Exception {
		ModelClass modelClass = clientServiceBuilder.buildClass(service);
		return modelClass;
	}
	
	
	/*
	 * Build main sources (process-specific)
	 * -------------------------------------
	 */
	
	public ModelClass buildActionClass(Service service, Invocation action) throws Exception {
		ModelClass modelClass = actionClassBuilder.buildClass(service, action);
		return modelClass;
	}

	public ModelClass buildProcessClass(Process process) throws Exception {
		ModelClass modelClass = processClassBuilder.buildClass(process);
		return modelClass;
	}

	public ModelClass buildProcessContextClass(Process process) throws Exception {
		ModelClass modelClass = processContextBuilder.buildClass(process);
		return modelClass;
	}
	
	public ModelClass buildProcessDefaultsClass(Process process) throws Exception {
		ModelClass modelClass = processDefaultsBuilder.buildClass(process);
		return modelClass;
	}

//	public ModelClass buildProcessStateObjectClass(Process process) throws Exception {
//		ModelClass modelClass = processStateObjectBuilder.build(process);
//		return modelClass;
//	}

//	public ModelClass buildProcessStateManagerClass(Process process) throws Exception {
//		ModelClass modelClass = processStateManagerBuilder.build(process);
//		return modelClass;
//	}

//	public ModelClass buildProcessStateProcessorClass(Process process) throws Exception {
//		ModelClass modelClass = processStateProcessorBuilder.build(process);
//		return modelClass;
//	}

	public List<ModelInterface> buildCacheUnitInterfaces(Process process) throws Exception {
		List<ModelInterface> modelInterfaces = cacheUnitBuilder.buildInterfaces(process);
		return modelInterfaces;
	}
	
	public List<ModelClass> buildCacheUnitClasses(Process process) throws Exception {
		List<ModelClass> modelClasses = cacheUnitBuilder.buildClasses(process);
		return modelClasses;
	}
	
	public List<ModelClass> buildCacheUnitStateClasses(Process process) throws Exception {
		List<ModelClass> modelClasses = cacheUnitStateBuilder.buildClasses(process);
		return modelClasses;
	}

	public List<ModelClass> buildCacheUnitManagerClasses(Process process) throws Exception {
		List<ModelClass> modelClasses = cacheUnitManagerBuilder.buildClasses(process);
		return modelClasses;
	}

	public List<ModelClass> buildCacheUnitStateProcessorClasses(Process process) throws Exception {
		List<ModelClass> modelClasses = cacheUnitStateProcessorBuilder.buildClasses(process);
		return modelClasses;
	}
	
	public List<ModelClass> buildDataUnits(Process process) throws Exception {
		List<ModelClass> modelClasses = dataUnitBuilder.buildClasses(process);
		return modelClasses;
	}
	
	public List<ModelClass> buildDataUnitStateClasses(Process process) throws Exception {
		List<ModelClass> modelClasses = dataUnitStateBuilder.buildClasses(process);
		return modelClasses;
	}

	public List<ModelClass> buildDataUnitManagerClasses(Process process) throws Exception {
		List<ModelClass> modelClasses = dataUnitManagerBuilder.buildClasses(process);
		return modelClasses;
	}
	
	public List<ModelClass> buildDataUnitStateProcessorClasses(Process process) throws Exception {
		List<ModelClass> modelClasses = dataUnitStateProcessorBuilder.buildClasses(process);
		return modelClasses;
	}

	public List<ModelClass> buildExecutorClasses(Process process) throws Exception {
		List<ModelClass> modelClasses = executorClassBuilder.buildClasses(process);
		return modelClasses;
	}
	
	
	/*
	 * Build main resources
	 * --------------------
	 */
	
	public ModelFile buildJAXWSPropertiesFile() throws Exception {
		ModelFile modelFile = jaxwsPropertiesFileBuilder.buildFile();
		return modelFile;
	}

	public List<ModelFile> buildJAXWSHandlersXMLFiles() throws Exception {
		List<ModelFile> modelFiles = new ArrayList<ModelFile>();
		modelFiles.add(jaxwsHandlersXMLFileBuilder.buildOneWayFile());
		modelFiles.add(jaxwsHandlersXMLFileBuilder.buildTwoWayFile());
		return modelFiles;
	}

	public ModelFile buildHibernateProperties() throws Exception {
		ModelFile modelFile = hibernatePropertiesFileBuilder.buildFile();
		return modelFile;
	}
	
	public ModelFile buildEhcacheJGroupsXMLFile() throws Exception {
		ModelFile modelFile = ehcacheJGroupsXMLFileBuilder.buildFile();
		return modelFile;
	}

	public ModelFile buildRuntimeChecksXMLFile() throws Exception {
		ModelFile modelFile = runtimeChecksXMLFileBuilder.buildFile();
		return modelFile;
	}

	public ModelFile buildRuntimeDefaultsXMLFile() throws Exception {
		ModelFile modelFile = runtimeDefaultsXMLFileBuilder.buildFile();
		return modelFile;
	}

	public ModelFile buildSeamPropertiesFile() throws Exception {
		ModelFile modelFile = seamPropertiesFileBuilder.buildFile();
		return modelFile;
	}

	public ModelFile buildSeamComponentsXMLFile() throws Exception {
		ModelFile modelFile = seamComponentsXMLFileBuilder.buildFile();
		return modelFile;
	}


	/*
	 * Build main resources.META-INF
	 * -----------------------------
	 */

	public ModelFile buildEjbJarXMLFile() throws Exception {
		ModelFile modelFile = ejbJarXMLBuilder.buildFile();
		return modelFile;
	}

	public ModelFile buildCDIBeansXMLFile() throws Exception {
		ModelFile modelFile = cdiBeansXMLBuilder.buildFile();
		return modelFile;
	}


	/*
	 * Build main resources.maven
	 * --------------------------
	 */

	public ModelFile buildMavenFilterPropertiesFile() throws Exception {
		ModelFile modelFile = mavenFilterPropertiesFileBuilder.buildFile();
		return modelFile;
	}

	public ModelFile buildMavenServerPropertiesFile() throws Exception {
		ModelFile modelFile = mavenServerPropertiesFileBuilder.buildFile();
		return modelFile;
	}

	public ModelFile buildMavenSettingsXMLFile() throws Exception {
		ModelFile modelFile = mavenSettingsXMLFileBuilder.buildFile();
		return modelFile;
	}


	/*
	 * Build Test sources (service-specific)
	 * -------------------------------------
	 */
	
	public ModelInterface buildServiceInterfaceTest(Service service) throws Exception {
		ModelInterface modelInterface = serviceInterfaceBuilder.buildInterface(service);
		return modelInterface;
	}

	public ModelClass buildServiceClassMain(Service service) throws Exception {
		ModelClass modelClass = serviceClassMainBuilder.build(service);
		return modelClass;
	}

	public ModelClass buildServiceListenerForJAXWSTest(Service service) throws Exception {
		ModelClass modelClass = serviceListenerForJAXWSUnitTestBuilder.buildClass(service);
		return modelClass;
	}

	public ModelClass buildServiceListenerForJMSTest(Service service) throws Exception {
		ModelClass modelClass = serviceListenerForJMSUnitTestBuilder.buildClass(service);
		return modelClass;
	}
	
	public ModelClass buildServiceListenerForRMITest(Service service) throws Exception {
		ModelClass modelClass = serviceListenerForRMIUnitTestBuilder.buildClass(service);
		return modelClass;
	}

	public ModelClass buildServiceHandlerUnitTest(Service service) throws Exception {
		ModelClass modelClass = serviceHandlerUnitTestBuilder.build(service);
		return modelClass;
	}

	public ModelClass buildServiceTXLocalITTest(Service service) throws Exception {
		ModelClass modelClass = serviceTXLocalITBuilder.build(service);
		return modelClass;
	}
	
	public List<ModelClass> buildCacheUnitManagerCITClasses(Process process) throws Exception {
		List<ModelClass> modelClasses = cacheUnitManagerCITBuilder.buildClasses(process);
		return modelClasses;
	}
	
	public List<ModelClass> buildDataUnitManagerCITClasses(Process process) throws Exception {
		List<ModelClass> modelClasses = dataUnitManagerCITBuilder.buildClasses(process);
		return modelClasses;
	}
	
	public ModelClass buildServiceJMSListenerCITClass(Service service) throws Exception {
		ModelClass modelClass = serviceJMSListenerCITBuilder.buildClass(service);
		return modelClass;
	}
	
	
	/*
	 * Build test sources (process-specific)
	 * -------------------------------------
	 */

//	public ModelClass buildProcessClassTest(Process process) throws Exception {
//		ModelClass modelClass = processClassBuilder.buildTest(process);
//		return modelClass;
//	}

//	public ModelClass buildProcessDefaultsTest(Process process) throws Exception {
//		ModelClass modelClass = processDefaultsBuilder.buildTest(process);
//		return modelClass;
//	}

	
	/*
	 * Build test resources
	 * --------------------
	 */

	public List<ModelFile> buildTestDSConfigFiles() throws Exception {
		List<ModelFile> modelFiles = dsConfigFileBuilder.buildForTest();
		return modelFiles;
	}

	public List<ModelFile> buildTestJMSConfigFiles() throws Exception {
		List<ModelFile> modelFiles = jmsConfigFileBuilder.buildForTest();
		return modelFiles;
	}

	public ModelFile buildTestHibernateProperties() throws Exception {
		ModelFile modelFile = hibernatePropertiesFileBuilder.buildForTest();
		return modelFile;
	}
	
	public ModelFile buildTestComponentsXML() throws Exception {
		ModelFile modelFile = seamComponentsXMLFileBuilder.buildForTest();
		return modelFile;
	}
	
	public ModelFile buildJNDIPropertiesFile() throws Exception {
		ModelFile modelFile = jndiPropertiesFileBuilder.buildForTest();
		return modelFile;
	}

	public List<ModelFile> buildJNDINodePropertyFiles() throws Exception {
		List<ModelFile> modelFiles = jndiNodePropertyFileBuilder.buildForTest();
		return modelFiles;
	}
	
	public List<ModelFile> buildJMSNodePropertyFiles() throws Exception {
		List<ModelFile> modelFiles = jmsNodePropertyFileBuilder.buildForTest();
		return modelFiles;
	}

	public ModelFile buildArquillianXMLFile() throws Exception {
		ModelFile modelFile = arquillianXMLBuilder.buildFile();
		return modelFile;
	}
	
	public ModelFile buildArquillianLaunchFile() throws Exception {
		ModelFile modelFile = arquillianLaunchFileBuilder.buildFile();
		return modelFile;
	}
	
	public ModelFile buildTestMavenFilterPropertiesFile() throws Exception {
		ModelFile modelFile = mavenFilterPropertiesFileBuilder.buildForTest();
		return modelFile;
	}
	
	public ModelFile buildTestMavenServerPropertiesFile() throws Exception {
		ModelFile modelFile = mavenServerPropertiesFileBuilder.buildForTest();
		return modelFile;
	}
	
	public ModelFile buildTestMavenSettingsXMLFile() throws Exception {
		ModelFile modelFile = mavenSettingsXMLFileBuilder.buildForTest();
		return modelFile;
	}
		
}
