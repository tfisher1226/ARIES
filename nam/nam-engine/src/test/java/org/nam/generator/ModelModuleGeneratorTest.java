package org.nam.generator;

import java.util.List;

import nam.model.Application;
import nam.model.ModelModuleGenerator;
import nam.model.Module;
import nam.model.ModuleType;
import nam.model.Project;
import nam.model.Service;
import nam.model.util.ApplicationUtil;
import nam.model.util.InformationUtil;
import nam.model.util.ModuleFactory;
import nam.model.util.ModuleUtil;

import org.apache.commons.io.FilenameUtils;
import org.aries.Assert;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ModelModuleGeneratorTest extends AbstractModuleGeneratorTest {

	//private static String FILE_NAME = "aries-common-0.0.1.xsd";
	//private static String FILE_NAME = "sgiusa-0.0.1.xsd";
	//private static String FILE_NAME = "bank-0.0.1.xsd";
	private static String FILE_NAME = "SimpleInvoke/SimpleInvoke.bpel";
	//private static String FILE_NAME = "AsyncEcho/async_echo.bpel";
	//private static String FILE_NAME = "OrderProcessing/OrderProcessing.bpel";
	//private static String FILE_NAME = "OrderManagement/OrderManagement.bpel";
	//private static String FILE_NAME = "LoanApproval/LoanApproval.bpel";

	private ModelModuleGenerator modelModuleGenerator;

	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		modelModuleGenerator = new ModelModuleGenerator(context);
	}

	@After
	public void tearDown() throws Exception {
		modelModuleGenerator = null;
		super.tearDown();
	}

	protected String getProjectName() {
		return FilenameUtils.getBaseName(FILE_NAME);
	}
	
	@Test
	public void testGenerate() throws Exception {
		String[] files = new String[] {FILE_NAME};
		Project project = getAriesModelForTest(files);
		Assert.notNull(project, "Project must exist");
		Module module = ModuleFactory.createClientModule();
		module.setInformation(InformationUtil.newInformation());
		context.initializeContext(module);
		modelModuleGenerator.initialize(project, module);
		modelModuleGenerator.generate(project, module);
	}
	
	protected Project getAriesModelForTest(String[] fileNames) throws Exception {
		ResourceSet emfResourceSet = getEMFModelForTest(fileNames);
		Assert.notNull(emfResourceSet, "ResourceSet must exist");
		Project project = createProjectForTest(emfResourceSet);
		context.setProject(project);
		return project;
	}
	
	protected void createModulesAndServicesForTest(ResourceSet emfResourceSet, Application application) throws Exception {
		Module serviceModule = ModuleFactory.createModule(application, ModuleType.MODEL);
		ApplicationUtil.getModules(application).add(serviceModule);
		List<Service> services = engine.getModelBuilder().buildServices(emfResourceSet);
		ModuleUtil.getServices(serviceModule).addAll(services);
	}

}
