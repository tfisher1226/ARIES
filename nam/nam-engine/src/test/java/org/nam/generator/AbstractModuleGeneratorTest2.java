package org.nam.generator;

import nam.model.Application;
import nam.model.Module;
import nam.model.ModuleType;
import nam.model.Project;
import nam.model.util.ApplicationUtil;
import nam.model.util.ProjectUtil;

import org.junit.After;
import org.junit.Before;


public class AbstractModuleGeneratorTest2 extends AbstractGeneratorTest {

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
	
	protected Module createModuleForTest(ModuleType moduleType) {
		switch (moduleType) {
		case MODEL: return createModelModuleForTest();
		case DATA: return createDataModuleForTest();
		case CLIENT: return createClientModuleForTest();
		case SERVICE: return createServiceModuleForTest();
		case VIEW: return createViewModuleForTest();
		case EAR: return createEARModuleForTest();
		default: return null;
		}
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
