package org.nam.generator;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nam.ProjectLevelHelper;
import nam.client.ClientLayerHelper;
import nam.data.DataLayerFactory;
import nam.data.DataLayerHelper;
import nam.model.Import;
import nam.model.Imports;
import nam.model.Namespace;
import nam.model.Project;
import nam.service.ServiceLayerHelper;

import org.aries.Assert;
import org.aries.util.properties.PropertyManager;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.resource.BPELResource;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.After;
import org.junit.Before;

import aries.bpel.BPELRuntimeCache;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.engine.GeneratorEngine;
import aries.generation.model.AnnotationUtil;


public class AbstractGeneratorTest {

	//protected String projectName;
	
	//protected String templateName;

	protected GenerationContext context;

	protected GeneratorEngine engine;
	
//	protected AriesModelFactory ariesModelFactory;
//
//	protected AriesModelBuilder ariesModelBuilder;
//
//	protected AriesModelParser ariesModelParser;
//
//	protected AriesModelReader ariesModelReader;
//
//	protected AriesModelHelper ariesModelHelper;
//
//	protected EMFModelBuilder emfModelBuilder;


	@Before
	public void setUp() throws Exception {
		setUp(getGenerationContextForTest());
	}

	public void setUp(GenerationContext context) throws Exception {
		this.context = context;
		context.setTemplateWorkspace("../../aries");

		ProjectLevelHelper.context = context;
		DataLayerFactory.context = context;
		DataLayerHelper.context = context;
		ServiceLayerHelper.context = context;
		ClientLayerHelper.context = context;
		AnnotationUtil.context = context;
		CodeUtil.context = context;

		PropertyManager.getInstance().setPropertyLocation("../runtime/properties");
		PropertyManager.getInstance().initialize();

		engine = new GeneratorEngine(context);
		engine.initialize();
		
//		ariesModelFactory = new AriesModelFactory(context);
//		ariesModelParser = new AriesModelParser(context);
//		
//		ariesModelBuilder = new AriesModelBuilder(context);
//		ariesModelBuilder.setFactory(ariesModelFactory);
//		
//		ariesModelReader = new AriesModelReader(context);
//		ariesModelReader.setParser(ariesModelParser);
//		ariesModelReader.setBuilder(ariesModelBuilder);
//		
//		ariesModelHelper = new AriesModelHelper(context);
//		ariesModelHelper.setFactory(ariesModelFactory);
//		ariesModelHelper.setReader(ariesModelReader);
//		ariesModelHelper.setBuilder(ariesModelBuilder);
//		
//		emfModelBuilder = new EMFModelBuilder(context);
//		emfModelBuilder.setAriesModelHelper(ariesModelHelper);
//		emfModelBuilder.setAriesModelParser(ariesModelParser);
	}

	@After
	public void tearDown() throws Exception {
		context = null;
//		emfModelBuilder = null;
//		ariesModelBuilder = null;
//		ariesModelFactory = null;
//		ariesModelReader = null;
//		ariesModelHelper = null;
	}

//	protected String getTemplateName() {
//		return templateName;
//	}
//
//	protected void setTemplateName(String templateName) {
//		this.templateName = templateName;
//	}
//
//	protected String getProjectName() {
//		return projectName;
//	}
//
//	protected void setProjectName(String projectName) {
//		this.projectName = projectName;
//	}
//
//	protected String getProjectPrefix() {
//		return getProjectName();
//	}
//
//	protected String getProjectDomain() {
//		return getProjectName()+".org";
//	}
//
//	protected String getProjectGroupId() {
//		return "org."+getProjectName();
//	}
//
//	protected String getProjectVersion() {
//		return "0.0.1-SNAPSHOT";
//	}

//	protected GenerationContext createGenerationContext() throws Exception {
//		return new GenerationContext();
//	}
	
//	protected GenerationContext createGenerationContext() throws Exception {
//		context = getGenerationContextForTest();
//		return context;
//	}

	protected GenerationContext getGenerationContextForTest() throws Exception {
		context = new GenerationContext();
		context.setTargetWorkspace("../nam-generated");
		context.setTargetHome("../nam-generated");
		context.setTemplateWorkspace("..");
		context.setTemplateName("template1");
		
		String projectName = "project1";
		context.setProjectName(projectName);
		context.setProjectPrefix(projectName);
		context.setProjectDomain("org.aries");
		context.setProjectGroupId("org.aries");
		context.setProjectVersion("0.0.1-SNAPSHOT");
		
		AnnotationUtil.context = context;
		CodeUtil.context = context;
		return context;
	}

	protected void initializeContext(Project project) {
		context.setProjectName(project.getName());
		context.setProject(project);
	}

	protected URL getResourceForTest(String fileName) throws Exception {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL resource = classLoader.getResource(fileName);
		if (resource == null) {
			resource = new URL("file://" + fileName);
		}
		return resource; 
	}

	protected Project getAriesModelForTest(String[] fileNames) throws Exception {
		ResourceSet emfResourceSet = getEMFModelForTest(fileNames);
		Assert.notNull(emfResourceSet, "ResourceSet must exist");
		Project project = engine.getModelBuilder().buildProject(emfResourceSet);
		context.setProject(project);
		return project;
	}

	protected Project createProjectFromClasspath(String fileName) throws Exception {
		ResourceSet emfResourceSet = getEMFModelForTest(fileName);
		Assert.notNull(emfResourceSet, "ResourceSet must exist");
		Project project = engine.getModelBuilder().buildProject(emfResourceSet);
		context.setProject(project);
		return project;
	}

	protected List<Namespace> getAriesNamespacesForTest(String[] fileNames) throws Exception {
		ResourceSet emfResourceSet = getEMFModelForTest(fileNames);
		Assert.notNull(emfResourceSet, "ResourceSet must exist");
		List<Namespace> namespaces = engine.getModelBuilder().buildNamespaces(emfResourceSet);
		return namespaces;
	}

	protected ResourceSet getEMFModelForTest(Imports imports) throws Exception {
		ResourceSet emfResourceSet = getEMFModelFromImports(".", imports);
		return emfResourceSet;
	}
	
	protected ResourceSet getEMFModelFromImports(String directory, Imports imports) throws Exception {
		ResourceSet emfResourceSet = new ResourceSetImpl();
		List<Import> importList = imports.getImports();
		Iterator<Import> iterator = importList.iterator();
		while (iterator.hasNext()) {
			Import importObject = iterator.next();
			String fileName = directory + File.separator + importObject.getFile();
			ResourceSet emfModelRecources = getEMFModelForTest(fileName);
			if (emfModelRecources != null) {
				emfResourceSet.getResources().addAll(emfModelRecources.getResources());
				emfResourceSet.getPackageRegistry().putAll(emfModelRecources.getPackageRegistry());
			}
		}
		return emfResourceSet;
	}
	
	protected ResourceSet getEMFModelForTest(String[] fileNames) throws Exception {
		ResourceSet emfResourceSet = new ResourceSetImpl();
		Assert.notNull(fileNames, "Input file(s) must exist");
		Assert.isTrue(fileNames.length > 0, "Input file(s) must be specified");
		for (String fileName : fileNames) {
			ResourceSet emfModelRecources = getEMFModelForTest(fileName);
			emfResourceSet.getResources().addAll(emfModelRecources.getResources());
			emfResourceSet.getPackageRegistry().putAll(emfModelRecources.getPackageRegistry());
		}
		return emfResourceSet;
	}

	protected ResourceSet getEMFModelForTest(String fileName) throws Exception {
		if (fileName.endsWith("xsd"))
			return getEMFModelFromXSD(fileName);
		if (fileName.endsWith("wsdl"))
			return getEMFModelFromWSDL(fileName);
		if (fileName.endsWith("bpel"))
			return getEMFModelFromBPEL(fileName);
		return null;
	}
	
	protected ResourceSet getEMFModelFromXSD(String fileName) throws Exception {
		List<URL> schemaResources = new ArrayList<URL>(); 
		schemaResources.add(getResourceForTest(fileName));
		ResourceSet emfResourceSet = getEMFModelFromXSD(schemaResources);
		return emfResourceSet;
	}
	
	protected ResourceSet getEMFModelFromXSD(List<URL> schemaResources) throws Exception {
		ResourceSet emfResourceSet = new ResourceSetImpl();
		ResourceSet modelResourcesFromXSD = engine.getEmfModelBuilder().buildFromXSD(schemaResources);
		emfResourceSet.getPackageRegistry().putAll(modelResourcesFromXSD.getPackageRegistry());
		emfResourceSet.getResources().addAll(modelResourcesFromXSD.getResources());
		return emfResourceSet;
	}

	protected ResourceSet getEMFModelFromWSDL(String fileName) throws Exception {
		List<URL> schemaResources = new ArrayList<URL>(); 
		schemaResources.add(getResourceForTest(fileName));
		ResourceSet emfResourceSet = getEMFModelFromWSDL(schemaResources);
		return emfResourceSet;
	}
	
	protected ResourceSet getEMFModelFromWSDL(List<URL> schemaResources) throws Exception {
		ResourceSet emfResourceSet = new ResourceSetImpl();
		ResourceSet modelResourcesFromXSD = engine.getEmfModelBuilder().buildFromXSD(schemaResources);
		ResourceSet modelResourcesFromWSDL = engine.getEmfModelBuilder().buildFromWSDL(schemaResources);
		//ResourceSet modelResourcesFromBPEL = emfModelBuilder.buildFromBPEL(schemaResources);
		emfResourceSet.getPackageRegistry().putAll(modelResourcesFromXSD.getPackageRegistry());
		emfResourceSet.getPackageRegistry().putAll(modelResourcesFromWSDL.getPackageRegistry());
		//emfResourceSet.getPackageRegistry().putAll(modelResourcesFromBPEL.getPackageRegistry());
		emfResourceSet.getResources().addAll(modelResourcesFromXSD.getResources());
		emfResourceSet.getResources().addAll(modelResourcesFromWSDL.getResources());
		//emfResourceSet.getResources().addAll(modelResourcesFromBPEL.getResources());
		return emfResourceSet;
	}

	protected ResourceSet getEMFModelFromBPEL(String fileName) throws Exception {
		List<URL> schemaResources = new ArrayList<URL>(); 
		URL resource = getResourceForTest(fileName);
		schemaResources.add(resource);
		ResourceSet emfResourceSet = getEMFModelFromBPEL(schemaResources);
		return emfResourceSet;
	}
	
	protected ResourceSet getEMFModelFromBPEL(List<URL> schemaResources) throws Exception {
		
		ClassLoader classLoader = this.getClass().getClassLoader();
		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		Thread.currentThread().setContextClassLoader(classLoader);
		
		ResourceSet emfResourceSet = new ResourceSetImpl();
		ResourceSet modelResourcesFromBPEL = engine.getEmfModelBuilder().buildFromBPEL(schemaResources);
		emfResourceSet.getPackageRegistry().putAll(modelResourcesFromBPEL.getPackageRegistry());
		//BPELResource bpelResource = (BPELResource) modelResourcesFromBPEL.getResources().get(0);
		//emfResourceSet.getResources().add(bpelResource);
		EList<Resource> resources = modelResourcesFromBPEL.getResources();
		resources = new BasicEList<Resource>(resources);
		Iterator<Resource> iterator = resources.iterator();
		while (iterator.hasNext()) {
			Resource resource = iterator.next();
			emfResourceSet.getResources().add(resource);
			if (resource instanceof BPELResource) {
				BPELResource bpelResource = (BPELResource) resource;
				Process bpelProcess = bpelResource.getProcess();
				//TODO resolve best way to store things at runtime
				BPELRuntimeCache.INSTANCE.addProcess(bpelProcess);
				//context.addProcess(bpelProcess);
			}
		}
		return emfResourceSet;
	}
	
//	protected List<Namespace> getAriesModelForTest(String[] fileNames) throws Exception {
//		ResourceSet emfResourceSet = getEMFModelForTest(fileNames);
//		Assert.notNull(emfResourceSet, "ResourceSet must exist");
//		List<Namespace> namespaces = ariesModelBuilder.buildNamespaces(emfResourceSet);
//		return namespaces;
//	}



	protected String getFilePath(String fileName) {
		URL resource = getClass().getClassLoader().getResource(".");
		String filePath = resource.getPath() + File.separator + fileName;
		filePath = normalizePath(filePath);
		return filePath;
	}
	
	public static String normalizePath(String path) {
		String fileSeparator = System.getProperty("file.separator");
		String newPath = path.replace("/", fileSeparator);
		if (newPath.substring(0, 1).equals(fileSeparator))
			newPath = newPath.substring(1);
		return newPath;
	}

}
