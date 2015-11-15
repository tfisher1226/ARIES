package org.nam.generator;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import nam.model.Application;
import nam.model.Module;
import nam.model.ModuleType;
import nam.model.Project;
import nam.model.Service;
import nam.model.util.ApplicationUtil;
import nam.model.util.ModuleFactory;
import nam.model.util.ModuleUtil;
import nam.model.util.ProjectUtil;

import org.aries.Assert;
import org.aries.util.properties.PropertyManager;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


public class BPELToAriesGeneratorTest extends AbstractGeneratorTest {

	private static String FILE_NAME = "SimpleInvoke.bpel";
	//private static String FILE_NAME = "process1-001.bpel";
	//private static String FILE_NAME = "bank-0.0.1.wsdl";
	//private static String FILE_NAME = "sgiusa-model-0.0.1.wsdl";
	
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
//		entityBeanBuilder = new EntityBeanBuilder(context);
//		entityBeanGenerator = new EntityBeanGenerator(context);
		PropertyManager.getInstance().addProperty("generateTableAnnotation", true);
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
//		entityBeanBuilder = null;
//		entityBeanGenerator = null;
	}

	@Test
	@Ignore
	public void testBuildFromBPEL() throws Exception {
		//Project project = getAriesModelForTest(FILE_NAME);
		List<URL> schemaResources = new ArrayList<URL>(); 
		schemaResources.add(getResourceForTest(FILE_NAME));
		ResourceSet emfResourceSet = new ResourceSetImpl();
		ResourceSet modelResourcesFromBPEL = engine.getEmfModelBuilder().buildFromBPEL(schemaResources);
		emfResourceSet.getPackageRegistry().putAll(modelResourcesFromBPEL.getPackageRegistry());
		emfResourceSet.getResources().addAll(modelResourcesFromBPEL.getResources());
		Project project = createProject(emfResourceSet);
		Assert.notNull(project);
	}

	protected Project createProject(ResourceSet emfResourceSet) throws Exception {
		Project project = new Project();
		ProjectUtil.getNamespaces(project).addAll(engine.getModelBuilder().buildNamespaces(emfResourceSet.getPackageRegistry()));
		//ProjectUtil.getNamespaces(project).addAll(buildNamespaces(emfResourceSet.getResources()));
		ProjectUtil.getApplications(project).add(createApplication(emfResourceSet));
		return project;
	}
	
	protected Application createApplication(ResourceSet emfResourceSet) throws Exception {
		//String applicationName = NameUtil.getSimpleNameFromPath(FILE_NAME);
		Application application = new Application();
		application.setName(context.getProjectNameCapped());
		application.setGroupId(context.getProjectGroupId());
		application.setArtifactId(context.getProjectName());
		application.setNamespace("http://"+context.getProjectDomain());
		
		Module serviceModule = ModuleFactory.createModule(application, ModuleType.SERVICE);
		Module clientModule = ModuleFactory.createModule(application, ModuleType.CLIENT);
		ApplicationUtil.getModules(application).add(clientModule);
		ApplicationUtil.getModules(application).add(serviceModule);
	
		List<Service> services = engine.getModelBuilder().buildServices(emfResourceSet);
		ModuleUtil.getServices(serviceModule).addAll(services);
		ModuleUtil.getServices(clientModule).addAll(services);
		return application;
	}
	
}
