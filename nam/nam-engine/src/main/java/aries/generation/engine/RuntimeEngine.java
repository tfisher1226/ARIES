package aries.generation.engine;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nam.model.Import;
import nam.model.Module;
import nam.model.Namespace;
import nam.model.Project;
import nam.model.Service;
import nam.model.util.ModuleUtil;
import nam.model.util.ProjectUtil;
import nam.model.util.ServiceUtil;

import org.aries.Assert;
import org.eclipse.emf.ecore.resource.ResourceSet;


public class RuntimeEngine extends AbstractGenerationEngine {

	public RuntimeEngine() throws Exception {
	}
	
//	public String getPropertyLocation() {
//		return propertyLocation;
//	}
//	
//	public void setPropertyLocation(String propertyLocation) {
//		this.propertyLocation = propertyLocation;
//	}
//
//	public String getWorkingLocation() {
//		return workingLocation;
//	}
//
//	public void setWorkingLocation(String workingLocation) {
//		this.workingLocation = workingLocation;
//	}
//
//	public String getRuntimeLocation() {
//		return runtimeLocation;
//	}
//
//	public void setRuntimeLocation(String runtimeLocation) {
//		this.runtimeLocation = runtimeLocation;
//	}
//
//	public String getApplicationHome() {
//		return applicationHome;
//	}
//	
//	public void setApplicationHome(String applicationHome) {
//		this.applicationHome = applicationHome;
//	}

//	protected JAXBReader createJAXBReader() throws Exception {
//		org.aries.common.init.Bootstrap.initialize();
//		org.aries.runtime.init.Bootstrap.initialize();
//		org.aries.message.init.Bootstrap.initialize();
//		org.aries.nam.process.init.Bootstrap.initialize();
//		org.aries.nam.model.old.init.Bootstrap.initialize();
//		JAXBSessionCache.INSTANCE.addSchema("/schema/aries-common-0.0.1.xsd");
//		JAXBSessionCache.INSTANCE.addSchema("/schema/aries-runtime-0.0.1.xsd");
//		JAXBSessionCache.INSTANCE.addSchema("/schema/aries-message-0.0.1.xsd");
//		JAXBSessionCache.INSTANCE.addSchema("/schema/process-0.0.1.xsd");
//		JAXBSessionCache.INSTANCE.addSchema("/schema/model-0.0.1.xsd");
//		JAXBReader jaxbReader = new JAXBReaderImpl();
//		jaxbReader.setClasses(TypeMap.INSTANCE.getClasses());
//		jaxbReader.setSchema(JAXBSessionCache.INSTANCE.getSchema());
//		//jaxb.setRootClass(Message.class);
//		return jaxbReader;
//	}

//	protected Project getAriesModel(String[] fileNames) throws Exception {
//		ResourceSet emfResourceSet = getEMFModel(fileNames);
//		Assert.notNull(emfResourceSet, "ResourceSet must exist");
//		Project project = ariesModelBuilder.buildProject(emfResourceSet);
//		context.setProject(project);
//		return project;
//	}

//	protected Project getAriesModel(String fileName) throws Exception {
//		ResourceSet emfResourceSet = getEMFModel(fileName);
//		Assert.notNull(emfResourceSet, "ResourceSet must exist");
//		Project project = ariesModelBuilder.buildProject(emfResourceSet);
//		context.setProject(project);
//		return project;
//	}

	protected Project getAriesModel(String[] fileNames) throws Exception {
		ResourceSet emfResourceSet = getEMFModel(fileNames);
		Assert.notNull(emfResourceSet, "ResourceSet must exist");
		Project project = ariesModelBuilder.buildProject(emfResourceSet);
		context.setProject(project);
		return project;
	}

	protected Project getAriesModel(String fileName) throws Exception {
		if (!fileName.endsWith("aries")) {
			//single-application approach
			ResourceSet emfResourceSet = getEMFModel(fileName);
			Project project = ariesModelBuilder.buildProject(emfResourceSet);
			Assert.notNull(project, "Project must exist");
			//Assert.notNull(project.getName(), "Project name must exist");
			context.setProject(project);
			return project;
			
		} else {
//			initializeJAXBReader();
			//single-application approach
			String fileContext = context.getRuntimeFileContext();
			String filePath = context.getRuntimeFilePath(fileContext, fileName);
			Project project = ariesModelParser.unmarshalFromFileSystem(filePath);
			Assert.notNull(project, "Project must exist");
			Assert.notNull(project.getName(), "Project name must exist");
			//Assert.notNull(project.getSource(), "Project source must exist");
			context.setProject(project);
			setProjectName(project.getName());
			//String folderName = NameUtil.getParentDirectoryName(filePath);
			ResourceSet emfResourceSet = getEMFModel(project.getImports());
			Assert.notNull(emfResourceSet, "ResourceSet must exist");
			//Project project = createProject(emfResourceSet);
			initializeProject(project, emfResourceSet);
			return project;
		}
	}
	
//	protected void initializeJAXBReader() throws Exception {
//		if (jaxbReader == null) {
//			NamespaceContext namespaceContext = BeanContext.get("org.aries.namespaceContext");
//			if (namespaceContext == null) {
//				namespaceContext = new NamespaceContext();
//				BeanContext.set("org.aries.namespaceContext", namespaceContext);
//			}
//			jaxbReader = namespaceContext.createJAXBReader();
//			jaxbReader.addListener(createJAXBUnmarshalListener());
//			jaxbReader.initialize();
//		}
//	}
	
	public void initializeProject(Project project, String fileName) throws Exception {
		Assert.notNull(project, "Project must exist");
		//Assert.notNull(project.getName(), "Project name must exist");

		integrateModelsFromARIESImports(project);
		
		context.initializeContext(project);
		ResourceSet emfResourcesFromProject = getEMFModel(fileName);
		List<Import> importedFiles = ProjectUtil.getImports(project);
		ResourceSet emfResourcesFromImports = initializeEMFFResourcesFromImportedProjects(importedFiles, fileName);
		initializeProjectFromEMFResources(project, emfResourcesFromProject, emfResourcesFromImports);

		//ResourceSet emfResources = new ResourceSetImpl();
		//addResources(emfResources, emfResourcesFromImports);
		//addResources(emfResources, emfResourcesFromProject);
	}

	protected void initializeProject(Project project, ResourceSet emfResourceSet) throws Exception {
		//ResourceSet emfResourcesFromImports = getEMFModel(ProjectUtil.getImports(project));
		//List<Namespace> namespaceRefs = ariesModelBuilder.buildNamespaceRefs(emfResourceSet.getPackageRegistry());
		List<Namespace> namespaces = ariesModelBuilder.buildNamespaces(emfResourceSet.getPackageRegistry());
		//TODO merge namespaces here - no potential duplicates
		ProjectUtil.getNamespaces(project).addAll(namespaces);
		//TODO namespace settings: assert (at Application) and assure (at Service) 
		ariesModelHelper.assureExtensions(project);
		ariesModelHelper.assureInformationBlocks(project);
		ariesModelHelper.assureMessagingBlocks(project);
		ariesModelHelper.assurePersistenceBlocks(project);
		ariesModelHelper.assureNamespaces(ProjectUtil.getNamespaces(project));
		ariesModelHelper.assureApplications(project);
		ariesModelHelper.assureModules(project);
		ariesModelHelper.assureServices(project);
		
		//Application application = ProjectUtil.getApplication(project);
		List<Service> services = ariesModelBuilder.buildServices(emfResourceSet);
		Map<String, Service> emfServiceMap = ServiceUtil.createMap(services);

		Set<Module> modules = ProjectUtil.getServiceModules(project);
		Iterator<Module> moduleIterator = modules.iterator();
		while (moduleIterator.hasNext()) {
			Module module = moduleIterator.next();
		
			List<Service> serviceRefs = ModuleUtil.getServices(module);
			Iterator<Service> iterator = serviceRefs.iterator();
			while (iterator.hasNext()) {
				Service service = iterator.next();
				String ref = service.getRef();
				if (ref != null) {
					Service emfService = emfServiceMap.get(ref);
					Assert.notNull(emfService, "Referenced service must exist");
					ServiceUtil.mergeService(service, emfService);
					service.getNamespaces().addAll(namespaces);
					emfServiceMap.remove(ref);
				} else {
					String name = service.getName();
					Assert.notNull(name, "Service name must exist");
					Service emfService = emfServiceMap.get(name);
					if (emfService != null)
						ServiceUtil.mergeService(service, emfService);
					//Assert.notNull(emfService, "Named service must exist");
					service.getNamespaces().addAll(namespaces);
					emfServiceMap.remove(name);
				}
			}
		}
	}

//	protected ResourceSet getEMFModel(Imports imports) throws Exception {
//		return getEMFModel(".", imports);
//	}
//	
//	protected ResourceSet getEMFModel(List<Import> imports) throws Exception {
//		return getEMFModel(".", imports);
//	}
//	
//	protected ResourceSet getEMFModel(String directory, Imports imports) throws Exception {
//		return getEMFModel(directory, imports.getImports());
//	}
//	
//	protected ResourceSet getEMFModel(String directory, List<Import> imports) throws Exception {
//		ResourceSet emfResourceSet = new ResourceSetImpl();
//		Iterator<Import> iterator = imports.iterator();
//		while (iterator.hasNext()) {
//			Import importObject = iterator.next();
//			String fileName = importObject.getFile();
//			ResourceSet emfModelRecources = getEMFModel(directory, fileName);
//			emfResourceSet.getResources().addAll(emfModelRecources.getResources());
//			emfResourceSet.getPackageRegistry().putAll(emfModelRecources.getPackageRegistry());
//		}
//		return emfResourceSet;
//	}

//	protected ResourceSet getEMFModel(String directory, String[] fileNames) throws Exception {
//		ResourceSet emfResourceSet = new ResourceSetImpl();
//		for (String fileName : fileNames) {
//			ResourceSet emfModelRecources = getEMFModel(directory, fileName);
//			emfResourceSet.getResources().addAll(emfModelRecources.getResources());
//			emfResourceSet.getPackageRegistry().putAll(emfModelRecources.getPackageRegistry());
//		}
//		return emfResourceSet;
//	}
//
//	protected ResourceSet getEMFModel(String directory, String fileName) throws Exception {
//		if (fileName.endsWith("xsd"))
//			return getEMFModelFromXSD(directory, fileName);
//		if (fileName.endsWith("wsdl"))
//			return getEMFModelFromWSDL(directory, fileName);
//		if (fileName.endsWith("bpel"))
//			return getEMFModelFromBPEL(directory, fileName);
//		if (fileName.endsWith("aries"))
//			return getEMFModelFromARIES(directory, fileName);
//		return new ResourceSetImpl();
//	}

//	protected ResourceSet getEMFModelFromBPEL(String directory, String fileName) throws Exception {
//		List<URL> schemaResources = new ArrayList<URL>(); 
//		String filePath = directory + File.separator + fileName;
//		//schemaResources.add(getResourceFromClasspath(fileName));
//		schemaResources.add(getResourceFromFileSystem(filePath));
//		ResourceSet emfResourceSet = getEMFModelFromBPEL(schemaResources);
//		return emfResourceSet;
//	}
//	
//	protected ResourceSet getEMFModelFromBPEL(List<URL> schemaResources) throws Exception {
//		ResourceSet emfResourceSet = new ResourceSetImpl();
//		ResourceSet modelResourcesFromBPEL = emfModelBuilder.buildFromBPEL(schemaResources);
//		emfResourceSet.getPackageRegistry().putAll(modelResourcesFromBPEL.getPackageRegistry());
//		//BPELResource bpelResource = (BPELResource) modelResourcesFromBPEL.getResources().get(0);
//		//emfResourceSet.getResources().add(bpelResource);
//		EList<Resource> resources = modelResourcesFromBPEL.getResources();
//		resources = new BasicEList<Resource>(resources);
//		Iterator<Resource> iterator = resources.iterator();
//		while (iterator.hasNext()) {
//			Resource resource = iterator.next();
//			emfResourceSet.getResources().add(resource);
//			if (resource instanceof BPELResource) {
//				BPELResource bpelResource = (BPELResource) resource;
//				Process bpelProcess = bpelResource.getProcess();
//				//TODO resolve best way to store things at runtime
//				BPELRuntimeCache.INSTANCE.addProcess(bpelProcess);
//				//context.addProcess(bpelProcess);
//			}
//		}
//		return emfResourceSet;
//	}
	
//	protected ResourceSet getEMFModelFromARIES(String directory, String fileName) throws Exception {
//		List<URL> schemaResources = new ArrayList<URL>(); 
//		String filePath = directory + File.separator + fileName;
//		schemaResources.add(getResourceFromFileSystem(filePath));
//		ResourceSet emfResourceSet = getEMFModelFromARIES(schemaResources);
//		return emfResourceSet;
//	}
	
//	protected ResourceSet getEMFModelFromARIES(List<URL> schemaResources) throws Exception {
//		ResourceSet emfResourceSet = new ResourceSetImpl();
//		ResourceSet modelResourcesFromAries = emfModelBuilder.buildFromARIES(schemaResources);
//		emfResourceSet.getPackageRegistry().putAll(modelResourcesFromAries.getPackageRegistry());
//		emfResourceSet.getResources().addAll(modelResourcesFromAries.getResources());
//		return emfResourceSet;
//	}
	
//	protected List<Namespace> getAriesModel(String[] fileNames) throws Exception {
//		ResourceSet emfResourceSet = getEMFModel(fileNames);
//		Assert.notNull(emfResourceSet, "ResourceSet must exist");
//		List<Namespace> namespaces = ariesModelBuilder.buildNamespaces(emfResourceSet);
//		return namespaces;
//	}

//	protected List<Namespace> getAriesNamespaces(String[] fileNames) throws Exception {
//		ResourceSet emfResourceSet = getEMFModel(fileNames);
//		Assert.notNull(emfResourceSet, "ResourceSet must exist");
//		List<Namespace> namespaces = ariesModelBuilder.buildNamespaces(emfResourceSet);
//		return namespaces;
//	}


//	protected URL getResourceFromFileSystem(String fileName) throws Exception {
//		if (!FileUtil.fileExists(fileName))
//			throw new FileNotFoundException("File not found: "+fileName);
//		File file = new File(fileName);
//		fileName = file.getAbsolutePath();
//		fileName = FileUtil.normalizePath(fileName);
//		if (!fileName.startsWith("file://"))
//			fileName = "file://" + fileName;
//		URL url = new URL(fileName);
//		return url; 
//	}

}
