package org.nam.generator;

import java.util.Iterator;
import java.util.Set;

import nam.data.DataModuleGenerator;
import nam.model.Application;
import nam.model.Module;
import nam.model.ModuleType;
import nam.model.Project;
import nam.model.util.ApplicationUtil;
import nam.model.util.ModuleFactory;
import nam.model.util.ProjectUtil;

import org.aries.Assert;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class DataModuleGeneratorTest extends AbstractModuleGeneratorTest {

	//private static String FILE_NAME = "aries-common-0.0.1.xsd";
	//private static String FILE_NAME = "sgiusa-0.0.1.xsd";
	private static String FILE_NAME = "SimpleInvoke/SimpleInvoke.bpel";
	
	private DataModuleGenerator dataModuleGenerator;

	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		//context.setModule(createDataModule());
		dataModuleGenerator = new DataModuleGenerator(context);
	}

	@After
	public void tearDown() throws Exception {
		dataModuleGenerator = null;
		super.tearDown();
	}

	@Test
	public void testGenerate() throws Exception {
		//String[] files = new String[] {FILE_NAME};
		Project project = getAriesModelForTest(FILE_NAME);
		Assert.notNull(project, "Project must exist");
		Set<Module> allModules = ProjectUtil.getAllModules(project);
		Iterator<Module> iterator = allModules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			dataModuleGenerator.initialize(project, module);
			dataModuleGenerator.generate(project, module);
		}
	}

	protected void createModulesAndServicesForTest(ResourceSet emfResourceSet, Application application) throws Exception {
		Module dataModule = ModuleFactory.createModule(application, ModuleType.DATA);
		ApplicationUtil.getModules(application).add(dataModule);
	}

}
