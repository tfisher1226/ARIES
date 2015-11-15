package org.nam.generator;

import java.util.Collection;
import java.util.List;

import nam.model.Application;
import nam.model.Module;
import nam.model.ModuleLevel;
import nam.model.ModuleType;
import nam.model.Project;
import nam.model.Service;
import nam.model.util.ApplicationUtil;
import nam.model.util.ExtensionsUtil;
import nam.model.util.ModuleFactory;
import nam.model.util.ModuleUtil;
import nam.model.util.ProjectUtil;

import org.aries.Assert;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.junit.After;
import org.junit.Before;


public class AbstractModuleGeneratorTest extends AbstractGeneratorTest {

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	
	protected void initializeContextForTest(Module module) {
		initializeContextForTest(createApplicationForTest(), module);
	}

	protected void initializeContextForTest(Application application, Module module) {
		initializeContextForTest(createProjectForTest(), application, module);
	}
	
	protected void initializeContextForTest(Project project, Application application, Module module) {
		ProjectUtil.getApplications(project).add(application);
		ApplicationUtil.getModules(application).add(module);
		context.setProject(project);
		context.setApplication(application);
		context.initializeContext(module);
	}
	
	
	protected Project getAriesModelForTest(String fileName) throws Exception {
		ResourceSet emfResourceSet = getEMFModelForTest(fileName);
		Assert.notNull(emfResourceSet, "ResourceSet must exist");
		//Project project = ariesModelBuilder.buildProject(emfResourceSet);
		Project project = createProjectForTest(emfResourceSet);
		context.setTemplateHome("template1");
		context.setProject(project);
		return project;
	}

	protected Project createProjectForTest(ResourceSet emfResourceSet) throws Exception {
		Project project = new Project();
		project.setExtensions(ExtensionsUtil.newExtensions());
		ProjectUtil.getNamespaces(project).addAll(engine.getModelBuilder().buildNamespaces(emfResourceSet.getPackageRegistry()));
		ProjectUtil.getNamespaces(project).addAll(engine.getModelBuilder().buildNamespaces(emfResourceSet.getResources()));
		ProjectUtil.getApplications(project).add(createApplicationForTest(emfResourceSet));
		project.setName("sample1");
		project.setVersion("1");
		return project;
	}
	
	protected Application createApplicationForTest(ResourceSet emfResourceSet) throws Exception {
		Application application = new Application();
		application.setName(context.getProjectNameCapped());
		application.setGroupId(context.getProjectGroupId());
		application.setArtifactId(context.getProjectName());
		application.setNamespace("http://"+context.getProjectDomain());
		createModulesAndServicesForTest(emfResourceSet, application);
		context.setApplication(application);
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


	protected Project createProjectForTest() {
		Project project = new Project();
		project.setName(context.getProjectName());
		return project;
	}
	
	protected Application createApplicationForTest() {
		Application application = new Application();
		application.setName(context.getProjectNameCapped());
		application.setGroupId(context.getProjectGroupId());
		application.setArtifactId(context.getProjectName());
		application.setNamespace("http://"+context.getProjectDomain());
		return application;
	}
	
	protected Module createModuleForTest(ModuleType moduleType) throws Exception {
		switch (moduleType) {
		case POM: return createPomModuleForTest();
		case MODEL: return createModelModuleForTest();
		case DATA: return createDataModuleForTest();
		case CLIENT: return createClientModuleForTest();
		case SERVICE: return createServiceModuleForTest();
		case VIEW: return createViewModuleForTest();
		case EAR: return createEARModuleForTest();
		default: return null;
		}
	}
	
	protected Module createPomModuleForTest() throws Exception {
		Application application = context.getApplication();
		Collection<Module> modules = ApplicationUtil.getModules(application);
		Module module = ModuleFactory.createPomModule();
		module.setLevel(ModuleLevel.PROJECT_LEVEL);
		modules.add(module);
		return module;
	}

	protected Module createDataModuleForTest() {
		Module module = createModuleForTest(ModuleType.DATA, "data");
		return module;
	}

	protected Module createModelModuleForTest() {
		Module module = createModuleForTest(ModuleType.MODEL, "model");
		return module;
	}
	
	protected Module createClientModuleForTest() {
		Module module = createModuleForTest(ModuleType.CLIENT, "client");
		return module;
	}
	
	protected Module createServiceModuleForTest() {
		Module module = createModuleForTest(ModuleType.SERVICE, "service");
		return module;
	}
	
	protected Module createViewModuleForTest() {
		Module module = createModuleForTest(ModuleType.VIEW, "view");
		return module;
	}

	protected Module createEARModuleForTest() {
		Module module = createModuleForTest(ModuleType.EAR, "ear");
		return module;
	}

	protected Module createModuleForTest(ModuleType moduleType, String artifactExt) {
		Module module = new Module();
		module.setType(moduleType);
		module.setGroupId(context.getProjectGroupId());
		module.setArtifactId(context.getProjectName()+"-"+artifactExt);
		return module;
	}
	
}
