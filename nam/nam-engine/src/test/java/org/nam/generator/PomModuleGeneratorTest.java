package org.nam.generator;

import java.util.Collection;

import nam.PomModuleGenerator;
import nam.model.Application;
import nam.model.Module;
import nam.model.ModuleType;
import nam.model.Project;
import nam.model.util.ApplicationUtil;
import nam.model.util.ModuleFactory;

import org.apache.commons.io.FilenameUtils;
import org.aries.Assert;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class PomModuleGeneratorTest extends AbstractModuleGeneratorTest {

	//private static String FILE_NAME1 = "aries-common-0.0.1.xsd";
	//private static String FILE_NAME2 = "sgiusa-0.0.1.xsd";
	private static String FILE_NAME = "SimpleInvoke/SimpleInvoke.bpel";
	//private static String FILE_NAME = "AsyncEcho/async_echo.bpel";
	
	private PomModuleGenerator pomModuleGenerator;

	
	protected String getProjectName() {
		//return "simple_invoke";
		return FilenameUtils.getBaseName(FILE_NAME);
	}
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		pomModuleGenerator = new PomModuleGenerator(context);
	}

	@After
	public void tearDown() throws Exception {
		pomModuleGenerator = null;
		super.tearDown();
	}

	@Test
	public void testGenerate() throws Exception {
		Project project = getAriesModelForTest(FILE_NAME);
		Assert.notNull(project, "Project must exist");
		Module module = createModuleForTest(ModuleType.POM);
		Assert.notNull(module, "Module must exist");
		pomModuleGenerator.initialize(project, module);
		addStubbedModulesForTest(project, module);
		pomModuleGenerator.generate(project, module);
	}

	protected void createModulesAndServicesForTest(ResourceSet emfResourceSet, Application application) throws Exception {
		Module pomModule = ModuleFactory.createModule(application, ModuleType.POM);
		ApplicationUtil.getModules(application).add(pomModule);
	}
	
	protected void addStubbedModulesForTest(Project project, Module module) throws Exception {
		Application application = context.getApplication();
		Collection<Module> modules = ApplicationUtil.getModules(application);
		modules.add(ModuleFactory.createModule(application, ModuleType.MODEL));
		modules.add(ModuleFactory.createModule(application, ModuleType.CLIENT));
		modules.add(ModuleFactory.createModule(application, ModuleType.SERVICE));
		//modules.add(ModuleFactory.createModule(application, ModuleType.DATA));
		//modules.add(ModuleFactory.createModule(application, ModuleType.VIEW));
	}

}
