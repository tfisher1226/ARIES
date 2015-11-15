package aries.generation.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import nam.PomModuleGenerator;
import nam.ProjectLevelHelper;
import nam.client.ClientModuleGenerator;
import nam.data.DataModuleGenerator;
import nam.ear.EARModuleGenerator;
import nam.model.Application;
import nam.model.Import;
import nam.model.Imports;
import nam.model.ModelModuleGenerator;
import nam.model.Module;
import nam.model.ModuleType;
import nam.model.Namespace;
import nam.model.Project;
import nam.model.Service;
import nam.model.util.ApplicationUtil;
import nam.model.util.ModuleFactory;
import nam.model.util.ModuleUtil;
import nam.model.util.ProjectUtil;
import nam.service.ServiceModuleGenerator;
import nam.ui.ViewModuleGenerator;

import org.apache.commons.io.FileUtils;
import org.aries.Assert;
import org.aries.util.FileUtil;
import org.aries.util.NameUtil;
import org.eclipse.emf.ecore.resource.ResourceSet;



public class GeneratorEngine extends AbstractGenerationEngine {

	private PomModuleGenerator pomModuleGenerator;
	private EARModuleGenerator earModuleGenerator;
	private ModelModuleGenerator modelModuleGenerator;
	private DataModuleGenerator dataModuleGenerator;
	private ClientModuleGenerator clientModuleGenerator;
	private ServiceModuleGenerator serviceModuleGenerator;
	private ViewModuleGenerator viewModuleGenerator;

	
	public GeneratorEngine() {
		//does nothing
	}
	
	public GeneratorEngine(GenerationContext context) {
		super(context);
	}
	
	public void initialize() throws Exception {
		super.initialize();
		pomModuleGenerator = new PomModuleGenerator(context);
		earModuleGenerator = new EARModuleGenerator(context);
		modelModuleGenerator = new ModelModuleGenerator(context);
		dataModuleGenerator = new DataModuleGenerator(context);
		clientModuleGenerator = new ClientModuleGenerator(context);
		serviceModuleGenerator = new ServiceModuleGenerator(context);
		viewModuleGenerator = new ViewModuleGenerator(context);
	}

//	protected GenerationContext createGenerationContext() throws Exception {
//		context = getGenerationContextForTest();
//		//context.setTemplateWorkspace("..");
//		//context.setTargetHome(".");
//		return context;
//	}
	
	public void cleanup() throws Exception {
		pomModuleGenerator = null;
		earModuleGenerator = null;
		modelModuleGenerator = null;
		dataModuleGenerator = null;
		clientModuleGenerator = null;
		serviceModuleGenerator = null;
		viewModuleGenerator = null;
		super.cleanup();
	}

	public void generate() throws Exception {
		//TODO manage this selection elsewhere?
		//TODO unify by using both below at same time
		if (inputProject == null && inputFile != null)
			inputProject = readFromFileSystem(inputFile);
		if (inputProject != null)
			generate(inputProject);
	}

	public void generate(Project project) throws Exception {
		/*
		 *  First, remove all previously generated model files.
		 *  This process will produce all new model files for the target location: 
		 *  targetDirectory: workspaceLocation + "/" + project.getName() + "/model"
		 */
		String workspaceLocation = context.getWorkspaceLocation();
		FileUtil.removeDirectory(workspaceLocation + "/" + project.getName() + "/model");
		
		Assert.notNull(project, "Project must exist");
		context.setProject(project);
		configureProject(project);
		generateProject(project);

		/*
		//generate sub-models
		List<Project> subProjects = ProjectUtil.getSubProjects(project);
		Iterator<Project> iterator = subProjects.iterator();
		while (iterator.hasNext()) {
			Project subProject = iterator.next();
			context.setProject(subProject);
			configureProject(subProject);
			generateProject(subProject);
		}
		*/

		copySchemaFiles(project);
	}

	protected void generateProject(Project project) throws Exception {
		context.setProject(project);
		context.setApplication(null);
		generateProjectModules(project);
		generateApplicationModules(project);
	}
	
	protected void copySchemaFiles(Project project) throws Exception {
		String workspaceLocation = getWorkspaceLocation();
		String cacheLocation = context.getCacheLocation();
		//String runtimeLocation = getRuntimeLocation();
		//String modelLocation = getModelLocation();
		Map<String, String> schemaFiles = new TreeMap<String, String>();
		
//		List<Module> modelModules = ModuleUtil.getModelModules(project);
//		Iterator<Module> moduleIterator = modelModules.iterator();
//		while (moduleIterator.hasNext()) {
//			Module module = moduleIterator.next();
//			String namespaceUri = module.getNamespace();
//			Namespace namespace = context.getNamespaceByUri(namespaceUri);
//			Assert.notNull(namespace, "Namespace not found: "+namespaceUri);
//			String xsdFileName = NamespaceUtil.getXSDFileName(namespace);
//			String ariesFileName = NamespaceUtil.getARIESFileName(namespace);
//			String prefix = namespace.getPrefix();
//			
//			String targetPath = context.getTargetPath(projectName);
//			String parentDirectory = targetPath + "/src/main/model/" + prefix;
//			parentDirectory = FileUtil.normalizePath(parentDirectory);
//			schemaFiles.put(xsdFileName, parentDirectory);
//			schemaFiles.put(ariesFileName, parentDirectory);
//		}

		List<Import> imports = ProjectUtil.getImportsSorted(project);
		Iterator<Import> iterator = imports.iterator();
		while (iterator.hasNext()) {
			Import importedFile = iterator.next();
			String fileName = FileUtil.normalizePath(importedFile.getFile());
			//String parentDirectory = FileUtil.normalizePath(importedFile.getDir());

			boolean foundIt = false;
			String filePath = null;
			//String projectName = context.getProject().getName();
			//String parentDirectory = context.getTargetWorkspace() + "/" + projectName + "/model";
			//parentDirectory = FileUtil.normalizePath(parentDirectory);
			//if (FileUtil.fileExists(parentDirectory + "/" + fileName)) {
			//	schemaFiles.put(fileName, parentDirectory);
			//	foundIt = true;
			//}
			
			// This first condition "assumes" fileName contains fileContext AND fileName
			String parentDirectory = FileUtil.normalizePath(cacheLocation + "/model");
			filePath = FileUtil.normalizePath(parentDirectory + "/" + fileName);
			if (!foundIt && FileUtil.fileExists(filePath)) {
				//fileName = FileUtil.getFileName(filePath);
				schemaFiles.put(fileName, parentDirectory);
				foundIt = true;
			}

			// This condition is for fileNames that ALSO contain fileContext
			filePath = FileUtil.normalizePath(importedFile.getDir() + "/model/" + fileName);
			if (!foundIt && FileUtil.fileExists(filePath)) {
				parentDirectory = FileUtil.getDirectory(filePath);
				fileName = FileUtil.getFileName(filePath);
				schemaFiles.put(fileName, parentDirectory);
				foundIt = true;
			}

			// This condition is for fileNames that do NOT contain fileContext
			filePath = FileUtil.normalizePath(importedFile.getDir() + "/" + fileName);
			if (!foundIt && FileUtil.fileExists(filePath)) {
				parentDirectory = FileUtil.normalizePath(cacheLocation + "/model"); 
				fileName = filePath.replace(parentDirectory, "");
				schemaFiles.put(fileName, parentDirectory);
				foundIt = true;
			}

			if (!foundIt)
				throw new FileNotFoundException("File not found");

			//a corresponding XSD file "may" exist
			if (fileName.endsWith("aries")) {
				fileName = fileName.replace(".aries", ".xsd");
				filePath = FileUtil.normalizePath(parentDirectory + "/" + fileName);
				File file = new File(filePath);
				if (file.exists()) {
					//parentDirectory = FileUtil.getDirectory(filePath);
					//fileName = FileUtil.getFileName(filePath);
					schemaFiles.put(fileName, parentDirectory);
				}
			}
		}
		
		System.out.println("");
		System.out.println("Generating Model Schema: "+project.getName());

		String targetProject = project.getName();
		Set<Module> modules = ProjectUtil.getAllModules(project);
		if (modules.size() == 1) {
			targetProject = project.getName() + "/" + modules.iterator().next().getArtifactId();
			//targetProject = project.getName() + "/project/" + modules.iterator().next().getArtifactId();
		}
		
		Iterator<String> fileIterator = schemaFiles.keySet().iterator();
		while (fileIterator.hasNext()) {
			String fileName = fileIterator.next();
			
			//OLD
			String parentDirectory = null;
			String modelFilePath = null;
//			try {
//				parentDirectory = FileUtil.normalizePath(context.getCacheLocation() + "/model");
//				modelFilePath = context.getFilePath(parentDirectory, null, fileName);
//			} catch (FileNotFoundException e) {
//				parentDirectory = schemaFiles.get(fileName);
//				modelFilePath = context.getFilePath(parentDirectory, null, fileName);
//			}

			parentDirectory = schemaFiles.get(fileName);
			modelFilePath = context.getFilePath(parentDirectory, null, fileName);

			//String parentDirectory = schemaFiles.get(fileName);
			//String modelFilePath = context.getFilePath(parentDirectory, null, fileName);
			
			File modelFile = new File(modelFilePath);

			//String fileContext = importedFile.getDir().replace(context.getRuntimeLocation(), "");
			//String modelFileContext = context.getModelLocation() + fileContext;
			//String modelFilePath = context.getModelFilePath(modelFileContext, fileName);
			//File modelFile = new File(modelFilePath);
			
			//String relativeFileContext = parentDirectory;
			//String relativeFilePath = modelFilePath;
			//if (fileName.startsWith("/")) {
			//	relativeFileContext = "/" + NameUtil.getParentDirectoryName(fileName);
			//	relativeFilePath = fileName;
			//}

			String relativeFilePath = modelFilePath.replace(parentDirectory, "");
			String relativeFileContext = NameUtil.getParentDirectoryName(relativeFilePath);
			
			//String relativeFileContext = "/" + NameUtil.getParentDirectoryName(fileName);
			//String relativeFilePath = fileName;
			
			File targetDirectory = new File(workspaceLocation + "/" + targetProject + "/model/" + relativeFileContext);
			File targetFile = new File(workspaceLocation + "/" + targetProject + "/model" + relativeFilePath);
			
			//File targetDirectory = new File(workspaceLocation + "/" + targetProject + "/src/main/resources/model/" + relativeFileContext);
			//File targetFile = new File(workspaceLocation + "/" + targetProject + "/src/main/resources/model" + relativeFilePath);
			
			try {
				/*
				 * We will NOT "copy" and "overwrite" a file that already exists.
				 * A file may already exist, if and only if it was just freshly generated.
				 * And in this case, we avoid "overwriting" it with some potentially older version.
				 * In future, if we wanted, we could have a property that says "overwrite all".
				 */
				if (!targetFile.exists() || context.canOverwriteAll()) {
					System.out.println("Generating: "+targetFile.getAbsolutePath());
					FileUtils.copyFileToDirectory(modelFile, targetDirectory);
				}
			} catch (Exception e) {
				// This should never happen.
				e.printStackTrace();
			}
		}
	}

	protected void generateProjectModules(Project project) throws Exception {
		Set<Module> modules = ProjectUtil.getProjectModules(project);
		generate(project, modules);
	}

	protected void generateApplicationModules(Project project) throws Exception {
		Collection<Application> applications = ProjectUtil.getApplications(project);
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			String name = application.getName();
			if (context.canGenerateApplication(name)) {
				Collection<Module> modules = ApplicationUtil.getModules(application);
				context.setApplication(application);
				generate(project, modules);
			}
		}
	}

	protected void generate(Project project, Collection<Module> modules) throws Exception {
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = (Module) iterator.next();
			generate(project, module);
		}
	}
	
	protected void generate(Project project, Module module) throws Exception {
		context.initialize(project, module);

		if (context.isOptionLimitToSingleModule()) {
			switch (module.getType()) {
			case VIEW: viewModuleGenerator.execute(project, module); break;
			case WAR: earModuleGenerator.execute(project, module); break;
			case EAR: earModuleGenerator.execute(project, module); break;
			}

		} else {
			switch (module.getType()) {
			case POM: pomModuleGenerator.execute(project, module); break;
			case MODEL: modelModuleGenerator.execute(project, module); break;
			case CLIENT: clientModuleGenerator.execute(project, module); break;
			case SERVICE: serviceModuleGenerator.execute(project, module); break;
			case DATA: dataModuleGenerator.execute(project, module); break;
			case VIEW: viewModuleGenerator.execute(project, module); break;
			case EAR: earModuleGenerator.execute(project, module); break;
			}
		}
	}

	public void generateModelLayer() throws Exception {
		generateDataLayer(inputFile);
	}
	
	public void generateModelLayer(File file) throws Exception {
		generateModelLayer(file.getAbsolutePath());
	}
	
	public void generateModelLayer(String fileName) throws Exception {
		super.initialize();

		ariesModelParser.createJAXBContext();
		modelModuleGenerator = new ModelModuleGenerator(context);
		Project project = ariesModelReader.readInformationProjectFromFileSystem(fileName);
		
//		Application application = new Application();
//		application.setGroupId(context.getProjectGroupId());
//		application.setArtifactId(context.getProjectName());
//		application.setVersion(context.getProjectVersion());
//		context.setApplication(application);

//		Namespace namespace = context.getNamespaceByPrefix(context.getProjectPrefix());
//		module.setNamespace(namespace.getUri());

		List<Namespace> namespaces = ProjectUtil.getNamespaces(project);
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			if (namespace.getImported())
				continue;
			
			Module module = ModuleFactory.createModelModule();
			String packageName = ProjectLevelHelper.getPackageName(namespace.getUri());
			String domainName = NameUtil.getDomainFromNamespace(namespace.getUri());
			
			//TODO do not use any defaults from "context"
			module.setName(namespace.getName());
			module.setGroupId(domainName);
			module.setArtifactId(namespace.getName());
			module.setVersion(namespace.getVersion());
			module.setNamespace(namespace.getUri());
			ProjectUtil.getProjectModules(project).add(module);
			modelModuleGenerator.execute(project, module);
		}

		//ProjectUtil.getApplications(project).add(application);
		//ApplicationUtil.getModules(application).add(module);
	}
	
	public void generateDataLayer() throws Exception {
		generateDataLayer(inputFile);
	}
	
	public void generateDataLayer(File file) throws Exception {
		generateDataLayer(file.getAbsolutePath());
	}

	public void generateDataLayerFromXSD(String fileName) throws Exception {
		ResourceSet emfResourceSet = getEMFModelFromXSD(fileName);
		Project project = ariesModelBuilder.buildProject(emfResourceSet);
		//System.out.println();
	}
	
	public void generateDataLayerFromARIES(String fileName) throws Exception {
		ResourceSet emfResourceSet = getEMFModelFromARIES(fileName);
		Project project = ariesModelBuilder.buildProject(emfResourceSet);
		//System.out.println();
	}
	
	public void generateDataLayer(String fileName) throws Exception {
		super.initialize();
		
		ariesModelParser.createJAXBContext();
		dataModuleGenerator = new DataModuleGenerator(context);
		Project project = ariesModelReader.readPersistenceProjectFromFileSystem(fileName);
		//context.setProject(project);
		
		Application application = new Application();
		application.setGroupId(context.getProjectGroupId());
		application.setArtifactId(context.getProjectName());
		context.setApplication(application);
		
		Module module = ModuleFactory.createDataModule();
		module.setGroupId(context.getProjectGroupId());
		module.setArtifactId(context.getProjectName());
		context.setModule(module);

		ApplicationUtil.getModules(application).add(module);
		ProjectUtil.getApplications(project).add(application);
		dataModuleGenerator.execute(project, module);
	}

	public void generateViewLayer(String fileName) throws Exception {
		super.initialize();
		
		ariesModelParser.createJAXBContext();
		viewModuleGenerator = new ViewModuleGenerator(context);
		Project project = ariesModelReader.readInformationProjectFromFileSystem(fileName);
		//context.setProject(project);
		
		Application application = new Application();
		application.setGroupId(context.getProjectGroupId());
		application.setArtifactId(context.getProjectName());
		context.setApplication(application);
		
		Module module = ModuleFactory.createViewModule();
		module.setGroupId(context.getProjectGroupId());
		module.setArtifactId(context.getProjectName());
		//TODO make this select correct information blocks
		//TODO module.setInformation(project.getInformation());
		context.setModule(module);

		ApplicationUtil.getModules(application).add(module);
		ProjectUtil.getApplications(project).add(application);
		viewModuleGenerator.execute(project, module);
	}

	
	protected void configureProject(Project project) {
		Collection<Application> applications = ProjectUtil.getApplications(project);
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			configureApplication(project, application);
		}
	}

	protected void configureApplication(Project project, Application application) {
		if (application != null) {
			String artifactId = application.getArtifactId();
			if (context.canGenerateApplication(artifactId)) {
				context.setApplication(application);
				Set<Module> modules = ProjectUtil.getApplicationModules(project);
				if (modules.size() == 1) {
					context.setOptionLimitToSingleModule(true);
					//Module module = modules.iterator().next();
					//module.setType(ModuleType.ALL);
				}
			}
		}
	}

	public String saveProjectToXML(Project project) throws Exception {
		String xml = ariesModelParser.marshal(project);
		return xml;
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

	protected List<Project> createProjectsFromImports(String folderName, Imports imports) throws Exception {
		List<Project> importProjects = new ArrayList<Project>();
		List<Import> importList = imports.getImports();
		Iterator<Import> iterator = importList.iterator();
		while (iterator.hasNext()) {
			Import importObject = iterator.next();
			String fileName = importObject.getFile();
			if (fileName.endsWith("aries")) {
				Project importedProject = ariesModelReader.readProjectFromFileSystem(folderName + File.separator + fileName);
				//Project importedProject = createProjectFromClasspath("model/runtime/"+fileName);
				importProjects.add(importedProject);
			}
		}
		return importProjects;
	}
	
//	protected void initializeProject(Project project, ResourceSet projectResources, ResourceSet importedResources) throws Exception {
//		List<Namespace> definedNamespaces = ariesModelBuilder.buildNamespaces(projectResources.getPackageRegistry(), false);
//		List<Namespace> importedNamespaces = ariesModelBuilder.buildNamespaces(importedResources.getPackageRegistry(), true);
//		List<Namespace> namespaces = ProjectUtil.getNamespaces(project);
//		namespaces.addAll(definedNamespaces);
//		namespaces.addAll(importedNamespaces);
//		ariesModelHelper.assureNamespaces(project);
//		ariesModelBuilder.populateCache(namespaces);
//
//		//ProjectUtil.getApplications(project).add(createApplication(emfResourceSet));
//		Application application = ProjectUtil.getApplication(project);
//		if (application == null)
//			return;
//		
//		ariesModelHelper.assureApplication(project);
//		ariesModelHelper.assureModules(project);
//		ariesModelHelper.assureServices(project);
//		ariesModelHelper.assureProcesses(project);
//		
//		List<Service> services = ariesModelBuilder.buildServices(projectResources);
//		Map<String, Service> emfServiceMap = ServiceUtil.createMap(services);
//
//		List<Module> modules = ApplicationUtil.getServiceModules(application);
//		Iterator<Module> moduleIterator = modules.iterator();
//		while (moduleIterator.hasNext()) {
//			Module module = moduleIterator.next();
//		
//			List<Service> serviceRefs = ModuleUtil.getServices(module);
//			Iterator<Service> iterator = serviceRefs.iterator();
//			while (iterator.hasNext()) {
//				Service service = iterator.next();
//				String key = service.getRef();
//				if (key == null)
//					key = service.getName();
//				Assert.notNull(key, "Service reference key must exist");
//				Service emfService = emfServiceMap.get(key);
//				if (emfService != null) {
//					//Assert.notNull(emfService, "Referenced service must exist");
//					ServiceUtil.mergeService(service, emfService);
//					emfServiceMap.remove(key);
//				}
//				context.addServiceToCache(service);
//			}
//		}
//	}
	
//	public void assureServices(Project project) {
//		Application application = context.getApplication();
//		List<Service> services = ApplicationUtil.getServices(application);
//		assureServices(services);
//
////		List<Process> processes = context.getProcesses();
////		Iterator<Process> iterator = processes.iterator();
////		while (iterator.hasNext()) {
////			Process process = iterator.next();
////			Application application = ProjectUtil.getApplication(project);
////			List<Service> services = ApplicationUtil.getServices(application);
////			assureServices(services);
////		}
//	}

//	public void assureServices(List<Service> services) {
//		Iterator<Service> iterator = services.iterator();
//		while (iterator.hasNext()) {
//			Service service = iterator.next();
//			Assert.notNull(service.getRef(), "Service reference name must exist");
//		}
//	}
	
	protected Project createProject(ResourceSet emfResourceSet) throws Exception {
		Project project = new Project();
		ProjectUtil.getNamespaces(project).addAll(ariesModelBuilder.buildNamespaces(emfResourceSet.getPackageRegistry()));
		//ProjectUtil.getNamespaces(project).addAll(buildNamespaces(emfResourceSet.getResources()));
		ProjectUtil.getApplications(project).add(createApplication(emfResourceSet));
		return project;
	}
	
	protected Application createApplication(ResourceSet emfResourceSet) throws Exception {
		Application application = new Application();
		application.setName(context.getProjectNameCapped());
		application.setGroupId(context.getProjectGroupId());
		application.setArtifactId(context.getProjectName());
		application.setNamespace("http://"+context.getProjectDomain());
		
		Module serviceModule = ModuleFactory.createModule(application, ModuleType.SERVICE);
		Module clientModule = ModuleFactory.createModule(application, ModuleType.CLIENT);
		ApplicationUtil.getModules(application).add(serviceModule);
		ApplicationUtil.getModules(application).add(clientModule);
	
		List<Service> services = ariesModelBuilder.buildServices(emfResourceSet);
		ModuleUtil.getServices(serviceModule).addAll(services);
		ModuleUtil.getServices(clientModule).addAll(services);
		return application;
	}

}
