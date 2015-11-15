package aries.generation.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nam.model.Application;
import nam.model.Applications;
import nam.model.Domain;
import nam.model.Import;
import nam.model.Imports;
import nam.model.Information;
import nam.model.Module;
import nam.model.Modules;
import nam.model.Namespace;
import nam.model.Persistence;
import nam.model.Process;
import nam.model.Project;
import nam.model.Service;
import nam.model.Services;
import nam.model.util.InformationUtil;
import nam.model.util.ModuleFactory;
import nam.model.util.ModuleUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.PersistenceUtil;
import nam.model.util.ProjectUtil;
import nam.model.util.ServiceUtil;
import nam.model.util.ServicesUtil;

import org.aries.Assert;
import org.aries.util.FileUtil;
import org.aries.util.NameUtil;
import org.aries.util.properties.PropertyManager;
import org.eclipse.bpel.model.resource.BPELResource;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import aries.bpel.BPELRuntimeCache;
import aries.generation.AriesModelBuilder;
import aries.generation.AriesModelFactory;
import aries.generation.AriesModelHelper;
import aries.generation.AriesModelParser;
import aries.generation.AriesModelReader;
import aries.generation.EMFModelBuilder;


public abstract class AbstractGenerationEngine implements GenerationEngine {

	protected String inputFile;

	protected Project inputProject;

	protected String projectName;
	
	protected String templateName;

	protected GenerationContext context;
	
	protected AriesModelParser ariesModelParser;

	protected AriesModelBuilder ariesModelBuilder;

	protected AriesModelHelper ariesModelHelper;

	protected AriesModelFactory ariesModelFactory;

	protected AriesModelReader ariesModelReader;

	protected EMFModelBuilder emfModelBuilder;


	public AbstractGenerationEngine() {
		context = createGenerationContext();
		//context.setEngine(this);
	}

	public AbstractGenerationEngine(GenerationContext context) {
		this.context = context;
		context.setEngine(this);
	}

	public AriesModelParser getModelParser() {
		return ariesModelParser;
	}

	public AriesModelBuilder getModelBuilder() {
		return ariesModelBuilder;
	}

	public AriesModelHelper getModelHelper() {
		return ariesModelHelper;
	}

	public AriesModelFactory getModelFactory() {
		return ariesModelFactory;
	}

	public AriesModelReader getModelGenerator() {
		return ariesModelReader;
	}

	public EMFModelBuilder getEmfModelBuilder() {
		return emfModelBuilder;
	}

	
	public void initialize() throws Exception {
		ariesModelParser = new AriesModelParser(context);
		ariesModelFactory = new AriesModelFactory(context);
	
		ariesModelBuilder = new AriesModelBuilder(context);
		ariesModelBuilder.setFactory(ariesModelFactory);
		
		ariesModelReader = new AriesModelReader(context);
		ariesModelReader.setParser(ariesModelParser);
		ariesModelReader.setBuilder(ariesModelBuilder);
		ariesModelReader.setEngine(this);
		
		ariesModelHelper = new AriesModelHelper(context);
		ariesModelHelper.setFactory(ariesModelFactory);
		ariesModelHelper.setReader(ariesModelReader);
		ariesModelHelper.setBuilder(ariesModelBuilder);
		ariesModelHelper.setEngine(this);

		emfModelBuilder = new EMFModelBuilder(context);
		emfModelBuilder.setAriesModelHelper(ariesModelHelper);
		emfModelBuilder.setAriesModelParser(ariesModelParser);
		
		//TODO Take this out? it is not needed now when running inside the AS
		//TODO -tfisher 8/26/13
		PropertyManager.getInstance().initialize();
	}

	
	protected GenerationContext createGenerationContext() {
		projectName = "model2";
		templateName = "template1";
		context = new GenerationContext();
		context.setTargetWorkspace("../nam-generated");
		context.setTemplateWorkspace("..");
		context.setTemplateName(getTemplateName());
		context.setProjectName(getProjectName());
		context.setProjectPrefix(getProjectName());
		context.setProjectDomain(getProjectName());
		context.setDefaultProjectGroupId();
		context.setDefaultProjectVersion();
		return context;
	}
	
	public Project readFromFileSystem(File file) throws Exception {
		return readFromFileSystem(file.getAbsolutePath());
	}

	public Project readFromFileSystem(String fileName) throws Exception {
		return ariesModelReader.readProjectFromFileSystem(fileName, false);
	}

	public Project readFromClasspath(String fileName) throws Exception {
		return ariesModelReader.readProjectFromClasspath(fileName, false);
	}

	public Information readInformationModelFromFileSystem(String fileName) throws Exception {
		return ariesModelReader.readInformationModelFromFileSystem(fileName, false);
	}

	public Persistence readPersistenceModelFromFileSystem(String fileName) throws Exception {
		return ariesModelReader.readPersistenceModelFromFileSystem(fileName, false);
	}

	public void cleanup() throws Exception {
		ariesModelParser.cleanup();
		emfModelBuilder = null;
		ariesModelParser = null;
		ariesModelBuilder = null;
		ariesModelHelper = null;
		ariesModelFactory = null;
		ariesModelReader = null;
		context = null;
	}

	public GenerationContext getContext() {
		return context;
	}

	public void setContext(GenerationContext context) {
		this.context = context;
	}

	public String getModelLocation() {
		return context.getModelLocation();
	}

	public void setModelLocation(String modelLocation) {
		this.context.setModelLocation(modelLocation);
	}

	public String getRuntimeLocation() {
		return context.getRuntimeLocation();
	}

	public void setRuntimeLocation(String runtimeLocation) {
		this.context.setRuntimeLocation(runtimeLocation);
	}

	public String getWorkspaceLocation() {
		return context.getWorkspaceLocation();
	}

	public void setWorkspaceLocation(String workspaceLocation) {
		this.context.setWorkspaceLocation(workspaceLocation);
	}
	
	public String getInputFile() {
		return inputFile;
	}

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public Project getInputProject() {
		return inputProject;
	}

	public void setInputProject(Project inputProject) {
		this.inputProject = inputProject;
	}

	protected String getProjectName() {
		return projectName;
	}

	protected void setProjectName(String projectName) {
		this.projectName = projectName ;
	}

	protected String getProjectPrefix() {
		return getProjectName();
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

//	public String getProjectName() {
//		return projectName;
//	}
//
//	public void setProjectName(String projectName) {
//		this.projectName = projectName;
//	}

//	public String getProjectPrefix() {
//		return getProjectName();
//	}

//	protected GenerationContext createGenerationContext() throws Exception {
//		context = new GenerationContext();
//		//initializeContext();
//		return context;
//	}

//	protected URL getResourceFromClasspath(String fileName) throws Exception {
//		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//		URL url = classLoader.getResource(fileName);
//		return url; 
//	}
	
	public static URL getResourceFromClasspath(String fileName) throws Exception {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		//ClassLoader classLoader = getClass().getClassLoader();
		URL resource = classLoader.getResource(fileName);
		if (resource == null)
			resource = classLoader.getResource("/"+fileName);
		if (resource == null)
			resource = classLoader.getResource("./"+fileName);
		if (resource == null)
			resource = new URL("file://"+fileName);
		//if (resource == null)
		//	resource = new URL("file://"+System.getProperty("user.dir")+"/"+fileName);
		return resource; 
	}

	public static URL getResourceFromFileSystem(String fileName) throws Exception {
		if (!FileUtil.fileExists(fileName))
			throw new FileNotFoundException("File not found: "+fileName);
		File file = new File(fileName);
		fileName = file.getAbsolutePath();
		fileName = FileUtil.normalizePath(fileName);
		if (!fileName.startsWith("file://"))
			fileName = "file://" + fileName;
		URL url = new URL(fileName);
		return url; 
	}

	
//	protected Project getProjectFromFiles(String[] fileNames) throws Exception {
//		ResourceSet emfResourceSet = getEMFModel(fileNames);
//		Assert.notNull(emfResourceSet, "ResourceSet must exist");
//		Project project = ariesModelBuilder.buildProject(emfResourceSet);
//		context.setProject(project);
//		return project;
//	}

	protected Project createProjectFromClasspath(String fileName) throws Exception {
		ResourceSet emfResourceSet = getEMFModel(fileName);
		Assert.notNull(emfResourceSet, "ResourceSet must exist");
		Project project = ariesModelBuilder.buildProject(emfResourceSet);
		context.setProject(project);
		return project;
	}

	public List<Namespace> createNamespaces(String filePath) throws Exception {
		ResourceSet emfResourceSet = getEMFModel(filePath);
		Assert.notNull(emfResourceSet, "ResourceSet must exist");
		List<Namespace> namespaces = ariesModelBuilder.buildNamespaces(emfResourceSet);
		NamespaceUtil.setFilePath(namespaces, filePath);
		return namespaces;
	}
	
	public List<Namespace> createNamespaces(String[] fileNames) throws Exception {
		ResourceSet emfResourceSet = getEMFModel(fileNames);
		Assert.notNull(emfResourceSet, "ResourceSet must exist");
		List<Namespace> namespaces = ariesModelBuilder.buildNamespaces(emfResourceSet);
		return namespaces;
	}

	protected ResourceSet getEMFModel(Imports imports) throws Exception {
		if (imports == null)
			return new ResourceSetImpl();
		return getEMFModel(imports.getImports());
	}
	
	protected ResourceSet getEMFModel(List<Import> importList) throws Exception {
		//String runtimeLocation = context.getRuntimeLocation();
		ResourceSet emfResourceSet = new ResourceSetImpl();
		Iterator<Import> iterator = importList.iterator();
		while (iterator.hasNext()) {
			Import importObject = iterator.next();
			//String fileContext = runtimeLocation;
			//if (importObject.getDir() != null)
			//	fileContext = importObject.getDir(); 
			String fileName = importObject.getFile();
			String filePath = null;
			try {
				filePath = context.getFilePath(context.getCacheLocation() + "/model", null, fileName);
				//String filePath = context.getRuntimeFilePath(fileContext, fileName);
				//String fileContext = context.getRuntimeFileContext();
				ResourceSet emfModelRecources = getEMFModel(filePath);
				emfResourceSet.getResources().addAll(emfModelRecources.getResources());
				emfResourceSet.getPackageRegistry().putAll(emfModelRecources.getPackageRegistry());
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		return emfResourceSet;
	}

	protected ResourceSet getEMFModel(String[] fileNames) throws Exception {
		ResourceSet emfResourceSet = new ResourceSetImpl();
		Assert.notNull(fileNames, "Input file(s) must exist");
		Assert.isTrue(fileNames.length > 0, "Input file(s) must be specified");
		for (String fileName : fileNames) {
			ResourceSet emfModelRecources = getEMFModel(fileName);
			emfResourceSet.getResources().addAll(emfModelRecources.getResources());
			emfResourceSet.getPackageRegistry().putAll(emfModelRecources.getPackageRegistry());
		}
		return emfResourceSet;
	}

	//gets from classpath
	public ResourceSet getEMFModel(String fileName) throws Exception {
		if (fileName.endsWith("xsd"))
			return getEMFModelFromXSD(fileName);
		if (fileName.endsWith("wsdl"))
			return getEMFModelFromWSDL(fileName);
		if (fileName.endsWith("bpel"))
			return getEMFModelFromBPEL(fileName);
		if (fileName.endsWith("aries"))
			return getEMFModelFromARIES(fileName);
		//if (fileName.endsWith("ariel"))
		//	throw new Exception("Not yet supported");
		return new ResourceSetImpl();
	}
	
	protected ResourceSet getEMFModelFromXSD(String fileName) throws Exception {
		List<URL> schemaResources = new ArrayList<URL>(); 
		URL resource = getResourceFromClasspath(fileName);
		Assert.notNull(resource, "Resource not found: "+fileName);
		schemaResources.add(resource);
		ResourceSet emfResourceSet = getEMFModelFromXSD(schemaResources);
		return emfResourceSet;
	}

	protected ResourceSet getEMFModelFromXSD(List<URL> schemaResources) throws Exception {
		ResourceSet emfResourceSet = new ResourceSetImpl();
		ResourceSet modelResourcesFromXSD = emfModelBuilder.buildFromXSD(schemaResources);
		emfResourceSet.getPackageRegistry().putAll(modelResourcesFromXSD.getPackageRegistry());
		emfResourceSet.getResources().addAll(modelResourcesFromXSD.getResources());
		return emfResourceSet;
	}
	
	protected ResourceSet getEMFModelFromWSDL(String fileName) throws Exception {
		List<URL> schemaResources = new ArrayList<URL>();
		String fileContext = context.getRuntimeFileContext();
		String filePath = context.getRuntimeFilePath(fileContext, fileName);
		schemaResources.add(getResourceFromFileSystem(filePath));
		ResourceSet emfResourceSet = getEMFModelFromWSDL(schemaResources);
		return emfResourceSet;
	}

	protected ResourceSet getEMFModelFromWSDL(List<URL> schemaResources) throws Exception {
		ResourceSet emfResourceSet = new ResourceSetImpl();
		ResourceSet modelResourcesFromXSD = emfModelBuilder.buildFromXSD(schemaResources);
		ResourceSet modelResourcesFromWSDL = emfModelBuilder.buildFromWSDL(schemaResources);
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
		URL bpelResource = getResourceFromFileSystem(fileName);
		if (bpelResource == null) {
			String fileContext = context.getRuntimeFileContext();
			String filePath = context.getRuntimeFilePath(fileContext, fileName);
			bpelResource = getResourceFromFileSystem(filePath);
		}
		List<URL> schemaResources = new ArrayList<URL>();
		//schemaResources.add(getResourceFromClasspath(fileName));
		schemaResources.add(bpelResource);
		ResourceSet emfResourceSet = getEMFModelFromBPEL(schemaResources);
		return emfResourceSet;
	}
	
	protected ResourceSet getEMFModelFromBPEL(List<URL> schemaResources) throws Exception {
		ResourceSet emfResourceSet = new ResourceSetImpl();
		ResourceSet modelResourcesFromBPEL = emfModelBuilder.buildFromBPEL(schemaResources);
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
				org.eclipse.bpel.model.Process bpelProcess = bpelResource.getProcess();
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
	
	protected ResourceSet getEMFModelFromARIES(List<URL> schemaResources) throws Exception {
		ResourceSet emfResourceSet = new ResourceSetImpl();
		ResourceSet ePackagesFromARIES = emfModelBuilder.buildEPackagesFromARIES(schemaResources);
		emfResourceSet.getPackageRegistry().putAll(ePackagesFromARIES.getPackageRegistry());
		//TODO emfResourceSet.getResources().addAll(emfResourcesFromARIES.getResources());
		return emfResourceSet;
	}
	
	protected ResourceSet getEMFModelFromARIES(String fileName) throws Exception {
		List<URL> schemaResources = new ArrayList<URL>(); 
		String filePath = null;
		if (FileUtil.fileExists(fileName)) {
			filePath = fileName;
		} else {
			String fileContext = context.getRuntimeFileContext();
			filePath = context.getRuntimeFilePath(fileContext, fileName);
		}
		schemaResources.add(getResourceFromFileSystem(filePath));
		ResourceSet emfResourceSet = getEMFModelFromARIES(schemaResources);
		return emfResourceSet;
	}


//	@Override
//	public Project createInformationProjectFromFileSystem(String filePath) throws Exception {
//		return ariesModelGenerator.createInformationProjectFromFileSystem(filePath);
//	}

	
	public void initializeProject(Project project, String fileName) throws Exception {
		Assert.notNull(project, "Project must exist");
		integrateModelsFromARIESImports(project);
		context.initializeContext(project);
		initializeProjectFromEMFResources(project, fileName);
	}

	protected void integrateModelsFromARIESImports(Project project) throws Exception {
		Imports imports = project.getImports();
		integrateModelsFromARIESImports(project, imports.getImports());
	}

	protected void initializeProjectFromEMFResources(Project project, String fileName) throws Exception {
		ResourceSet emfResourcesFromProject = getEMFModel(fileName);
		List<Import> importedFiles = ProjectUtil.getImports(project);
		ResourceSet emfResourcesFromImports = initializeEMFFResourcesFromImportedProjects(importedFiles, fileName);
		initializeProjectFromEMFResources(project, emfResourcesFromProject, emfResourcesFromImports);
		//initializeProjectFromEMFResources(project, fileName);
	}
		
	protected ResourceSet initializeEMFFResourcesFromImportedProjects(List<Import> importedFiles, String fileName) throws Exception {
		ResourceSet emfResources = new ResourceSetImpl();
		if (importedFiles != null) {
			String folderName = NameUtil.getParentDirectoryName(fileName);
			context.setRuntimeFileContext(folderName);
			ResourceSet emfResourcesFromImports = getEMFModel(importedFiles);
			//List<Project> importedProjects = createProjectsFromImports(folderName, imports);
			//integrateModelsFromARIESImports(project, imports.getImports());
			addResources(emfResources, emfResourcesFromImports);
			//Iterator<Import> iterator = importedFiles.iterator();
			//while (iterator.hasNext()) {
			//	Import importedFile = iterator.next();
			//	emfResourcesFromImports = initializeEMFFResourcesFromImportedProjects(importedFile.getImports(), fileName);
			//	addResources(emfResources, emfResourcesFromImports);
			//}
		}
		return emfResources;
	}

	public static void addResources(ResourceSet toResources, ResourceSet fromResources) {
		toResources.getPackageRegistry().putAll(fromResources.getPackageRegistry());
		toResources.getResources().addAll(fromResources.getResources());
	}

	protected void integrateModelsFromARIESImports(Project project, Collection<Import> importList) throws Exception {
		Iterator<Import> iterator = importList.iterator();
		while (iterator.hasNext()) {
			Import importObject = iterator.next();
			String fileName = importObject.getFile();
			
			if (fileName.endsWith("aries")) {
				String directory = context.getRuntimeFileContext();
				String filePath = context.getFilePath(context.getCacheLocation() + "/model", null, fileName);
				//String filePath = context.getCacheFilePath(directory, fileName);
//				System.out.println(">>> "+context.getCacheLocation());
//				System.out.println(">>> "+directory);
//				System.out.println(">>> "+fileName);
//				System.out.println(">>> "+filePath);
				Object object = ariesModelParser.unmarshalFromFileSystem(filePath);
				
				if (object instanceof Project) {
					Project importedProject = (Project) object;
					ProjectUtil.getSubProjects(project).add(importedProject);

				} else if (object instanceof Applications) {

				} else if (object instanceof Application) {

				} else if (object instanceof Modules) {
					Modules importedModules = (Modules) object;
					//initialize specified structures: services, or persistence, or other 
					List<Service> serviceList = ModuleUtil.getServices(importedModules);
					//List<Domain> domainList = ModuleUtil.getDomains(importedModules);
					Module serviceModule = ModuleFactory.createServiceModule();
					if (serviceModule.getServices() == null)
						serviceModule.setServices(new Services());
					ServicesUtil.addServices(serviceModule.getServices(), serviceList);
					//serviceModule.getServices().getImportsAndDomainsAndServices().addAll(serviceList);
					//serviceModule.getServices().getImportsAndDomainsAndServices().addAll(domainList);
					Set<Import> importedFiles = ModuleUtil.getImports(importedModules);
					//recursively integrate imported objects first, then initialize the services
					integrateModelsFromARIESImports(project, importedFiles);
					
				} else if (object instanceof Module) {

				} else if (object instanceof Services) {
					Services importedServices = (Services) object;
					//initialize specified structures: domains and services 
					List<Service> serviceList = ServicesUtil.getServices(importedServices);
					List<Domain> domainList = ServicesUtil.getDomains(importedServices);
					Module serviceModule = ModuleFactory.createServiceModule();
					if (serviceModule.getServices() == null)
						serviceModule.setServices(new Services());
					ServicesUtil.addServices(serviceModule.getServices(), serviceList);
					ServicesUtil.addDomains(serviceModule.getServices(), domainList);
					//TODO serviceModule.setServices(importedServices);
					List<Import> importedFiles = ServicesUtil.getImports(importedServices);
					//recursively integrate imported objects first, then initialize the services
					integrateModelsFromARIESImports(project, importedFiles);

				} else if (object instanceof Service) {
					Service importedService = (Service) object;
					//initialize the domains and services 
					Module serviceModule = ModuleFactory.createServiceModule();
					if (serviceModule.getServices() == null)
						serviceModule.setServices(new Services());
					ServicesUtil.addService(serviceModule.getServices(), importedService);
					
				} else if (object instanceof Information) {
					Information importedInformation = (Information) object;
					ProjectUtil.addInformationBlock(project, importedInformation);
					importedInformation.setImported(true);
					List<Import> importedInformationImports = InformationUtil.getImports(importedInformation);
					integrateModelsFromARIESImports(project, importedInformationImports);
					//mergeNamespaces(project, importedInformation);

				} else if (object instanceof Persistence) {
					Persistence importedPersistence = (Persistence) object;
					ProjectUtil.addPersistenceBlock(project, importedPersistence);
					List<Import> importedImports = PersistenceUtil.getImports(importedPersistence);
					integrateModelsFromARIESImports(project, importedImports);
					//TODO?
					//List<Repository> repositories = PersistenceUtil.getRepositories(importedPersistence);
					//PersistenceUtil.addRepositories(projectPersistence, repositories);
					//List<Unit> units = PersistenceUtil.getUnits(importedPersistence);
					//PersistenceUtil.addUnits(projectPersistence, units);
				}
			}
		}
	}
	
//NOTUSED NOW
//	protected void mergeNamespaces(Project project, Information incomingInformation) {
//		Information projectInformation = ProjectUtil.getInformation(project);
//		List<Namespace> incomingNamespaces = InformationUtil.getNamespaces(incomingInformation);
//		Iterator<Namespace> iterator = incomingNamespaces.iterator();
//		while (iterator.hasNext()) {
//			Namespace incomingNamespace = iterator.next();
//			if (!isNamespaceExists(project, incomingNamespace)) {
//				InformationUtil.addNamespace(projectInformation, incomingNamespace);
//			}
//		}
//	}

	public static boolean isNamespaceExists(Project project, Namespace namespace) {
		List<Namespace> namespaces = ProjectUtil.getNamespaces(project);
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace projectNamespace = iterator.next();
			if (projectNamespace.getUri().equals(namespace.getUri())) {
				return true;
			}
		}
		return false;
	}

	protected void initializeProjectFromEMFResources(Project project, ResourceSet projectResources, ResourceSet importedResources) throws Exception {
		List<Namespace> definedNamespaces = ariesModelBuilder.buildNamespaces(projectResources.getPackageRegistry(), false);
		List<Namespace> importedNamespaces = ariesModelBuilder.buildNamespaces(importedResources.getPackageRegistry(), true);
		List<Namespace> importedNamespaces2 = ariesModelBuilder.buildNamespaces2(importedResources);
		Set<Namespace> namespaces = new HashSet<Namespace>();
		namespaces.addAll(ProjectUtil.getNamespaces(project));
		namespaces.addAll(definedNamespaces);
		namespaces.addAll(importedNamespaces);
		namespaces.addAll(importedNamespaces2);
		//ariesModelHelper.assureNamespaces(project);
		context.populateNamespaces(namespaces);
		ariesModelBuilder.populateCache(namespaces);
		
		//ariesModelHelper.assureApplications(project);
		//ariesModelHelper.assureModules(project);
		//ariesModelHelper.assureServices(project);
		//ariesModelHelper.assureProcesses(project);
		
		List<Service> services = ariesModelBuilder.buildServices(importedResources);
		Map<String, Service> emfServiceMap = ServiceUtil.createMap(services);

		//build process (if exists)
		Iterator<Resource> iterator = importedResources.getResources().iterator();
		while (iterator.hasNext()) {
			Resource resource = iterator.next();
			if (resource instanceof BPELResource) {
				BPELResource bpelResource = (BPELResource) resource;
				org.eclipse.bpel.model.Process bpelProcess = bpelResource.getProcess();
				Process process = ariesModelBuilder.buildProcessFromBPELProcess(bpelProcess);
				ServiceUtil.setProcess(services, process);
				context.setProcess(process);
				//only one possible for now
				break;
			}
		}
		
		Set<Module> modules = ProjectUtil.getServiceModules(project);
		Iterator<Module> moduleIterator = modules.iterator();
		while (moduleIterator.hasNext()) {
			Module module = moduleIterator.next();
		
			List<Service> serviceRefs = ModuleUtil.getServices(module);
			Iterator<Service> iterator2 = serviceRefs.iterator();
			while (iterator2.hasNext()) {
				Service service = iterator2.next();
				String key = service.getRef();
				if (key == null)
					key = service.getName();
				Assert.notNull(key, "Service reference key must exist");
				Service emfService = emfServiceMap.get(key);
				if (emfService != null) {
					//Assert.notNull(emfService, "Referenced service must exist");
					ServiceUtil.mergeService(service, emfService);
					emfServiceMap.remove(key);
				}
				context.addImportedService(service);
			}
		}
	}

}
