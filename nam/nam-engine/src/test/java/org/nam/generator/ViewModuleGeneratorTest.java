package org.nam.generator;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nam.model.Application;
import nam.model.Module;
import nam.model.ModuleType;
import nam.model.Project;
import nam.model.Service;
import nam.model.util.ApplicationUtil;
import nam.model.util.ModuleFactory;
import nam.model.util.ModuleUtil;
import nam.model.util.ProjectUtil;
import nam.ui.ViewModuleGenerator;

import org.aries.Assert;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ViewModuleGeneratorTest extends AbstractModuleGeneratorTest {

	//private static String FILE_NAME1 = "aries-common-0.0.1.xsd";
	//private static String FILE_NAME2 = "sgiusa-0.0.1.xsd";
	private static String FILE_NAME = "SimpleInvoke/SimpleInvoke.bpel";
	
	private ViewModuleGenerator uiModuleGenerator;

	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		uiModuleGenerator = new ViewModuleGenerator(context);
	}

	@After
	public void tearDown() throws Exception {
		uiModuleGenerator = null;
		super.tearDown();
	}

	@Test
	public void testGenerate() throws Exception {
		//String[] files = new String[] {FILE_NAME1, FILE_NAME2};
		//Project project = getAriesModelForTest(files);
		Project project = getAriesModelForTest(FILE_NAME);
		Assert.notNull(project, "Project must exist");
		Set<Module> allModules = ProjectUtil.getAllModules(project);
		Iterator<Module> iterator = allModules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			if (module.getType() == ModuleType.VIEW) {
				uiModuleGenerator.initialize(project, module);
				uiModuleGenerator.generate(project, module);
			}
		}
	}
	
	protected void createModulesAndServicesForTest(ResourceSet emfResourceSet, Application application) throws Exception {
		Module clientModule = ModuleFactory.createModule(application, ModuleType.CLIENT);
		Module viewModule = ModuleFactory.createModule(application, ModuleType.VIEW);
		ApplicationUtil.getModules(application).add(clientModule);
		ApplicationUtil.getModules(application).add(viewModule);
		List<Service> services = engine.getModelBuilder().buildServices(emfResourceSet);
		ModuleUtil.getServices(clientModule).addAll(services);
	}

}
