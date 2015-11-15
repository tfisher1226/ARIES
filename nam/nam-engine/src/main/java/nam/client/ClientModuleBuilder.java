package nam.client;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nam.client.src.main.java.BootstrapInterceptorBuilder;
import nam.client.src.main.java.CacheUnitManagerMBeanBuilder;
import nam.client.src.main.java.CacheUnitMonitorBuilder;
import nam.client.src.main.java.ClientClassBuilder;
import nam.client.src.main.java.ClientDelegateBuilder;
import nam.client.src.main.java.ClientInterceptorBuilder;
import nam.client.src.main.java.ClientInterfaceBuilder;
import nam.client.src.main.java.ClientServiceBuilder;
import nam.client.src.main.java.DataUnitManagerMBeanBuilder;
import nam.client.src.main.java.DataUnitMonitorBuilder;
import nam.client.src.main.resources.METAINF.BeansXMLBuilder;
import nam.client.src.main.resources.maven.MavenFilterPropertiesFileBuilder;
import nam.client.src.main.resources.maven.MavenServerPropertiesFileBuilder;
import nam.client.src.main.resources.maven.MavenSettingsXMLFileBuilder;
import nam.client.src.test.java.ClientProxyMainBuilder;
import nam.client.src.test.java.ClientProxyTestBuilder;
import nam.client.src.test.java.TestEARFactoryBuilder;
import nam.client.src.test.resources.JMSNodePropertyFileBuilder;
import nam.client.src.test.resources.JNDINodePropertyFileBuilder;
import nam.client.src.test.resources.JNDIPropertiesFileBuilder;
import nam.model.Application;
import nam.model.Module;
import nam.model.Process;
import nam.model.Project;
import nam.model.Service;
import nam.model.util.ModuleUtil;
import nam.service.src.main.resources.RuntimeChecksXMLFileBuilder;
import aries.codegen.application.AbstractModuleBuilder;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelFile;
import aries.generation.model.ModelInterface;


public class ClientModuleBuilder extends AbstractModuleBuilder {

	//source area
	private ClientModuleHelper clientModuleHelper;
	private ClientInterfaceBuilder clientInterfaceBuilder;
	private ClientClassBuilder clientClassBuilder;
	private ClientDelegateBuilder clientDelegateBuilder;
	private ClientServiceBuilder clientServiceBuilder;
	private ClientInterceptorBuilder clientInterceptorBuilder;
	//private ClientProxyBuilder clientProxyBuilder;
	//private ServiceMBeanBuilder serviceMBeanBuilder;
	//private ServiceStateManagerMBeanBuilder serviceStateManagerMBeanBuilder;
	//private ProcessStateManagerMBeanBuilder processStateManagerMBeanBuilder;
	//private ProcessStateMonitorBuilder processStateMonitorBuilder;
	private CacheUnitManagerMBeanBuilder cacheUnitManagerMBeanBuilder;
	private CacheUnitMonitorBuilder cacheUnitMonitorBuilder;
	private DataUnitManagerMBeanBuilder dataUnitManagerMBeanBuilder;
	private DataUnitMonitorBuilder dataUnitMonitorBuilder;
	private BootstrapInterceptorBuilder bootstrapInterceptorBuilder;

	//main resources area
	private RuntimeChecksXMLFileBuilder runtimeChecksXMLFileBuilder;

	//main resources.META-INF area
	private BeansXMLBuilder beansXMLBuilder;
	
	//main resources.maven area
	private MavenFilterPropertiesFileBuilder mavenFilterPropertiesFileBuilder;
	private MavenServerPropertiesFileBuilder mavenServerPropertiesFileBuilder;
	private MavenSettingsXMLFileBuilder mavenSettingsXMLFileBuilder;
	
	//test area
	private ClientProxyMainBuilder clientProxyMainBuilder;
	private ClientProxyTestBuilder clientProxyTestBuilder;
	private TestEARFactoryBuilder testEARFactoryBuilder;

	//test resources area
	private JNDIPropertiesFileBuilder jndiPropertiesFileBuilder;
	private JNDINodePropertyFileBuilder jndiNodePropertyFileBuilder;
	private JMSNodePropertyFileBuilder jmsNodePropertyFileBuilder;

	
	public ClientModuleBuilder(GenerationContext context) {
		clientModuleHelper = new ClientModuleHelper(context);
		clientInterfaceBuilder = new ClientInterfaceBuilder(context);
		clientClassBuilder = new ClientClassBuilder(context);
		clientDelegateBuilder = new ClientDelegateBuilder(context);
		clientServiceBuilder = new ClientServiceBuilder(context);
		clientInterceptorBuilder = new ClientInterceptorBuilder(context);
		//clientProxyBuilder = new ClientProxyBuilder(context);
		//serviceMBeanBuilder = new ServiceMBeanBuilder(context);
		//serviceStateManagerMBeanBuilder = new ServiceStateManagerMBeanBuilder(context);
		//processStateManagerMBeanBuilder = new ProcessStateManagerMBeanBuilder(context);
		//processStateMonitorBuilder = new ProcessStateMonitorBuilder(context);
		cacheUnitManagerMBeanBuilder = new CacheUnitManagerMBeanBuilder(context);
		cacheUnitMonitorBuilder = new CacheUnitMonitorBuilder(context);
		dataUnitManagerMBeanBuilder = new DataUnitManagerMBeanBuilder(context);
		dataUnitMonitorBuilder = new DataUnitMonitorBuilder(context);
		bootstrapInterceptorBuilder = new BootstrapInterceptorBuilder(context);
		
		//main resources area
		runtimeChecksXMLFileBuilder = new RuntimeChecksXMLFileBuilder(context);
		
		//main resources.META-INF area
		beansXMLBuilder = new BeansXMLBuilder(context);
		
		//main resources.maven area
		mavenFilterPropertiesFileBuilder = new MavenFilterPropertiesFileBuilder(context);
		mavenServerPropertiesFileBuilder = new MavenServerPropertiesFileBuilder(context);
		mavenSettingsXMLFileBuilder = new MavenSettingsXMLFileBuilder(context);

		//test Sources area
		clientProxyMainBuilder = new ClientProxyMainBuilder(context);
		clientProxyTestBuilder = new ClientProxyTestBuilder(context);
		testEARFactoryBuilder = new TestEARFactoryBuilder(context);

		//test resources area
		jndiPropertiesFileBuilder = new JNDIPropertiesFileBuilder(context);
		jndiNodePropertyFileBuilder = new JNDINodePropertyFileBuilder(context);
		jmsNodePropertyFileBuilder = new JMSNodePropertyFileBuilder(context);
		this.context = context;
	}
	
	public void initialize(Project project) throws Exception {
		//ModuleFactory moduleFactory = new ModuleFactory();
		//Module module = moduleFactory.createModule(ModuleType.CLIENT);
		Set<Module> modules = ModuleUtil.getClientModulesToGenerate(project);
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			initialize(project, module);
		}
	}
	
	public void initialize(Project project, Module module) throws Exception {
		clientModuleHelper.initialize(project, module);
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
	 * Build Sources (service-specific)
	 * --------------------------------
	 */

	public ModelInterface buildClientInterface(Service service) throws Exception {
		ModelInterface modelInterface = clientInterfaceBuilder.buildInterface(service);
		return modelInterface;
	}

	public List<ModelClass> buildClientClasses(Service service) throws Exception {
		List<ModelClass> modelClasses = clientClassBuilder.buildClasses(service);
		return modelClasses;
	}

	public ModelClass buildClientDelegateClass(Service service) throws Exception {
		ModelClass modelClass = clientDelegateBuilder.buildClass(service);
		return modelClass;
	}

	public ModelClass buildClientServiceClass(Service service) throws Exception {
		ModelClass modelClass = clientServiceBuilder.buildClass(service);
		return modelClass;
	}
	
	public ModelClass buildClientInterceptorClass(Service service) throws Exception {
		ModelClass modelClass = clientInterceptorBuilder.buildClass(service);
		return modelClass;
	}

//	public ModelClass buildClientProxyClass(Service service) throws Exception {
//		ModelClass modelClass = clientProxyBuilder.build(service);
//		return modelClass;
//}

//	public ModelInterface buildServiceManagerMBean(Service service) throws Exception {
//		ModelInterface modelInterface = serviceManagerMBeanBuilder.build(service);
//		return modelInterface;
//	}
//
//	public ModelInterface buildServiceStateManagerMBean(Service service) throws Exception {
//		ModelInterface modelInterface = serviceStateManagerMBeanBuilder.build(service);
//		return modelInterface;
//	}

	/*
	 * Build Sources (process-specific)
	 * --------------------------------
	 */

//	public ModelInterface buildProcessStateManagerMBean(Process process) throws Exception {
//		ModelInterface modelInterface = processStateManagerMBeanBuilder.build(process);
//		return modelInterface;
//	}

//	public ModelClass buildProcessStateMonitor(Process process) throws Exception {
//		ModelClass modelClass = processStateMonitorBuilder.build(process);
//		return modelClass;
//	}

	public List<ModelInterface> buildCacheUnitManagerMBeans(Process process) throws Exception {
		List<ModelInterface> modelInterfaces = cacheUnitManagerMBeanBuilder.buildInterfaces(process);
		return modelInterfaces;
	}

	public List<ModelClass> buildCacheUnitStateMonitors(Process process) throws Exception {
		List<ModelClass> modelClasses = cacheUnitMonitorBuilder.buildClasses(process);
		return modelClasses;
	}
	
	public List<ModelInterface> buildDataUnitManagerMBeans(Process process) throws Exception {
		List<ModelInterface> modelInterfaces = dataUnitManagerMBeanBuilder.buildInterfaces(process);
		return modelInterfaces;
	}

	public List<ModelClass> buildDataUnitStateMonitors(Process process) throws Exception {
		List<ModelClass> modelClasses = dataUnitMonitorBuilder.buildClasses(process);
		return modelClasses;
	}
	
	
	/*
	 * Build main resources
	 * --------------------
	 */
	
	
	/*
	 * Build test sources (application-specific)
	 * -----------------------------------------
	 */
	
	public ModelClass buildTestEARFactoryBuilder(Application application) throws Exception {
		ModelClass modelClass = testEARFactoryBuilder.build(application);
		return modelClass;
	}
	

	/*
	 * Build test sources (service-specific)
	 * -------------------------------------
	 */
	
	public ModelInterface buildServiceInterfaceTest(Service service) throws Exception {
		ModelInterface modelInterface = clientInterfaceBuilder.buildInterface(service);
		return modelInterface;
	}

//	public ModelClass buildServiceFaultTest(Service service) throws Exception {
//		ModelClass modelClass = serviceFaultTestBuilder.build(service);
//		return modelClass;
//	}

	public ModelClass buildServiceProxyMain(Service service) throws Exception {
		ModelClass modelClass = clientProxyMainBuilder.build(service);
		return modelClass;
	}

	public ModelClass buildServiceProxyTest(Service service) throws Exception {
		ModelClass modelClass = clientProxyTestBuilder.build(service);
		return modelClass;
	}
	
	
	/*
	 * Build main resources
	 * --------------------
	 */
	
	public ModelFile buildRuntimeChecksXMLFile() throws Exception {
		ModelFile modelFile = runtimeChecksXMLFileBuilder.buildFile();
		return modelFile;
	}
	
	
	/*
	 * Build main resources.META-INF
	 * -----------------------------
	 */
	
	public ModelFile buildBeansXMLFile() throws Exception {
		ModelFile modelFile = beansXMLBuilder.buildFile();
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
	 * Build Tests (process-specific)
	 * ------------------------------
	 */

	
	/*
	 * Build test resources
	 * --------------------
	 */

	public ModelFile buildJNDIPropertiesFile() throws Exception {
		ModelFile modelFile = jndiPropertiesFileBuilder.buildFile();
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
	
	
	/*
	 * Build test resources.maven
	 * --------------------------
	 */
	
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
