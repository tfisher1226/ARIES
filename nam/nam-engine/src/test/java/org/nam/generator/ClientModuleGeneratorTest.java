package org.nam.generator;

import java.util.List;

import nam.client.ClientModuleGenerator;
import nam.model.Application;
import nam.model.Module;
import nam.model.ModuleType;
import nam.model.Project;
import nam.model.Service;
import nam.model.util.ApplicationUtil;
import nam.model.util.ExtensionsUtil;
import nam.model.util.ModuleFactory;
import nam.model.util.ModuleUtil;
import nam.model.util.ProjectUtil;

import org.apache.commons.io.FilenameUtils;
import org.aries.Assert;
import org.aries.util.FileUtil;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ClientModuleGeneratorTest extends AbstractModuleGeneratorTest {

	//private static String FILE_NAME = "bank-0.0.1.wsdl";
	private static String FILE_NAME = "/SimpleInvoke/SimpleInvoke.bpel";
	//private static String FILE_NAME = "AsyncEcho/async_echo.bpel";
	//private static String FILE_NAME = "OrderProcessing/OrderProcessing.bpel";
	//private static String FILE_NAME = "OrderManagement/OrderManagement.bpel";
	//private static String FILE_NAME = "LoanApproval/LoanApproval.bpel";

	private ClientModuleGenerator clientModuleGenerator;
	
	
	protected String getProjectName() {
		//return "simple_invoke";
		return FilenameUtils.getBaseName(FILE_NAME);
	}
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		clientModuleGenerator = new ClientModuleGenerator(context);
	}

	@After
	public void tearDown() throws Exception {
		clientModuleGenerator = null;
		super.tearDown();
	}
	
	@Test
	public void testGenerate() throws Exception {
		//Project project = getAriesModelForTest(getFilePath(FILE_NAME));
		String fileName = FileUtil.normalizePath(FILE_NAME);
		Project project = getAriesModelForTest(fileName);
		Assert.notNull(project, "Project must exist");
		Module module = ModuleFactory.createClientModule();
		clientModuleGenerator.initialize(project, module);
		clientModuleGenerator.generate(project, module);
	}

	protected Project getAriesModelForTest(String fileName) throws Exception {
		ResourceSet emfResourceSet = getEMFModelForTest(fileName);
		Assert.notNull(emfResourceSet, "ResourceSet must exist");
		//Project project = engine.getModelBuilder().buildProject(emfResourceSet);
		Project project = createProject(emfResourceSet);
		context.setProject(project);
		return project;
	}

	protected Project createProject(ResourceSet emfResourceSet) throws Exception {
		Project project = new Project();
		project.setExtensions(ExtensionsUtil.newExtensions());
		ProjectUtil.getNamespaces(project).addAll(engine.getModelBuilder().buildNamespaces(emfResourceSet.getPackageRegistry()));
		ProjectUtil.getNamespaces(project).addAll(engine.getModelBuilder().buildNamespaces(emfResourceSet.getResources()));
		Application application = createApplication(emfResourceSet);
		engine.getModelHelper().assureApplication(project, application);
		//ProjectUtil.getApplications(project).add(application);
		context.setProject(project);
		context.setApplication(application);
		return project;
	}
	
	protected Application createApplication(ResourceSet emfResourceSet) throws Exception {
		Application application = new Application();
		application.setName(context.getProjectNameCapped());
		application.setGroupId(context.getProjectGroupId());
		application.setArtifactId(context.getProjectName());
		application.setNamespace("http://"+context.getProjectDomain());
		createModulesAndServicesForTest(emfResourceSet, application);
		return application;
	}

	protected void createModulesAndServicesForTest(ResourceSet emfResourceSet, Application application) throws Exception {
		Module serviceModule = ModuleFactory.createModule(application, ModuleType.SERVICE);
		Module clientModule = ModuleFactory.createModule(application, ModuleType.CLIENT);
		ApplicationUtil.getModules(application).add(clientModule);
		ApplicationUtil.getModules(application).add(serviceModule);
	
		List<Service> services = engine.getModelBuilder().buildServices(emfResourceSet);
		ModuleUtil.getServices(serviceModule).addAll(services);
		ModuleUtil.getServices(clientModule).addAll(services);
	}

}
