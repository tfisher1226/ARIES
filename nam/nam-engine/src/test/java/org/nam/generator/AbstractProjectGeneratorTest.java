package org.nam.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nam.model.Application;
import nam.model.Import;
import nam.model.Imports;
import nam.model.Information;
import nam.model.Module;
import nam.model.ModuleType;
import nam.model.Namespace;
import nam.model.Project;
import nam.model.Service;
import nam.model.util.ApplicationUtil;
import nam.model.util.InformationUtil;
import nam.model.util.ModuleFactory;
import nam.model.util.ModuleUtil;
import nam.model.util.ProjectUtil;
import nam.model.util.ServiceUtil;

import org.aries.Assert;
import org.aries.util.NameUtil;
import org.aries.util.ObjectUtil;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.junit.After;
import org.junit.Before;


public abstract class AbstractProjectGeneratorTest extends AbstractGeneratorTest {

//	protected ProjectGeneratorHelper projectGeneratorHelper;

//	private JAXBReader jaxbReader;

	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		//projectGeneratorHelper = new ProjectGeneratorHelper(context);
//		createJAXBContext();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
		//projectGeneratorHelper = null;
//		jaxbReader = null;
	}
	
//	protected void createJAXBContext() throws Exception {
//		jaxbReader = new JAXBReaderImpl();
//		JAXBSessionCache jaxbSessionCache = new JAXBSessionCache("test");
////		jaxbSessionCache.addSchema("/schema/common/aries-common-1.0.xsd", org.aries.common.ObjectFactory.class);
////		jaxbSessionCache.addSchema("/schema/common/aries-message-1.0.xsd", org.aries.message.ObjectFactory.class);
////		jaxbSessionCache.addSchema("/schema/common/aries-validate-1.0.xsd", org.aries.validate.ObjectFactory.class);
////		jaxbSessionCache.addSchema("/schema/nam/nam-1.0.xsd", org.aries.nam.model.ObjectFactory.class);
//		
////		jaxbSessionCache.addSchema("/schema/nam/nam-common-1.0.xsd", org.aries.nam.model.ObjectFactory.class);
////		jaxbSessionCache.addSchema("/schema/nam/nam-security-1.0.xsd", org.aries.nam.model.ObjectFactory.class);
////		jaxbSessionCache.addSchema("/schema/nam/nam-execution-1.0.xsd", org.aries.nam.model.ObjectFactory.class);
////		jaxbSessionCache.addSchema("/schema/nam/nam-operation-1.0.xsd", org.aries.nam.model.ObjectFactory.class);
////		jaxbSessionCache.addSchema("/schema/nam/nam-messaging-1.0.xsd", org.aries.nam.model.ObjectFactory.class);
////		jaxbSessionCache.addSchema("/schema/nam/nam-information-1.0.xsd", org.aries.nam.model.ObjectFactory.class);
////		jaxbSessionCache.addSchema("/schema/nam/nam-persistence-1.0.xsd", org.aries.nam.model.ObjectFactory.class);
////		jaxbSessionCache.addSchema("/schema/nam/nam-application-1.0.xsd", org.aries.nam.model.ObjectFactory.class);
////		jaxbSessionCache.addSchema("/schema/nam/nam-workspace-1.0.xsd", org.aries.nam.model.ObjectFactory.class);
//		jaxbReader.setSchema(jaxbSessionCache.getSchema());
//		jaxbReader.setPackagesToLoad(jaxbSessionCache.getPackagesToLoad());
//		//jaxbReader.setClasses(jaxbSessionCache.getClassesToLoad());\
//		jaxbReader.addListener(createJAXBUnmarshalListener());
//		jaxbReader.initialize();
//	}
	
//	protected Listener createJAXBUnmarshalListener() {
//		return new Listener() {
//			public void afterUnmarshal(Object object1, Object object2) {
//				if (object1 instanceof Field) {
//					Field field = (Field) object1;
//					String type = field.getType();
//					if (type != null) {
//						int indexOf = type.indexOf(":");
//						if (indexOf != -1) {
//							String prefix = type.substring(0, indexOf);
//							String namespaceUri = jaxbReader.getNamespaceURI(prefix);
////							if (prefix.equals("common") && namespaceUri == null)
////								System.out.println();
//							if (namespaceUri != null) {
//								Assert.notNull(namespaceUri, "Namespace URI not found for prefix: "+prefix);
//								context.populateNamespaceUri(prefix, namespaceUri);
//							}
//						}
//					}
//				}
//			}
//		};
//	}
	
	
	protected Project createProjectFromFileSystem(String fileName) throws Exception {
		Project project = engine.getModelParser().unmarshalFromFileSystem(fileName);
		initializeProject(project, fileName);
		return project;
	}
	
	protected Project createProjectFromClasspath(String fileName) throws Exception {
		Project project = engine.getModelParser().unmarshalFromClasspath(fileName);
		initializeProject(project, fileName);
		return project;
	}
	
	protected void initializeProject(Project project, String fileName) throws Exception {
		Assert.notNull(project, "Project must exist");
		Assert.notNull(project.getName(), "Project name must exist");
		//Assert.notNull(project.getSource(), "Project source must exist");

		//setProjectName(project.getName());
		String folderName = NameUtil.getParentDirectoryName(fileName);
		ResourceSet emfResourceSet = getEMFModelFromImports(folderName, project.getImports());
		//ResourceSet emfResourceSet = getEMFModelFromFile(folderName+"/"+project.getSource());
		Assert.notNull(emfResourceSet, "ResourceSet must exist");
		//Project project = createProject(emfResourceSet);
		initializeProject(project, emfResourceSet);
		
		List<Project> importedProjects = createProjectsFromImports(folderName, project.getImports());
		ProjectUtil.getSubProjects(project).addAll(importedProjects);
		initializeContext(project);
	}

	protected List<Project> createProjectsFromImports(String folderName, Imports imports) throws Exception {
		List<Project> importProjects = new ArrayList<Project>();
		List<Import> importList = imports.getImports();
		Iterator<Import> iterator = importList.iterator();
		while (iterator.hasNext()) {
			Import importObject = iterator.next();
			String fileName = importObject.getFile();
			if (fileName.endsWith("aries")) {
				Project importedProject = createProjectFromClasspath(folderName+"/"+fileName);
				importProjects.add(importedProject);
			}
		}
		return importProjects;
	}

	protected Project getAriesModelForTest(String fileName) throws Exception {
		//URL resource = getClass().getClassLoader().getResource(".");
		//String filePath = resource.getPath() + File.separator + fileName;
		//File file = new File(filePath);
		
		//Project project = jaxbReader.unmarshalFromFileSystem(fileName);
		Project project = engine.getModelParser().unmarshalFromClasspath(fileName);
		Assert.notNull(project, "Project must exist");
		Assert.notNull(project.getName(), "Project name must exist");
		//Assert.notNull(project.getSource(), "Project source must exist");
		context.setProject(project);
		
		//setProjectName(project.getName());
		String folderName = NameUtil.getParentDirectoryName(fileName);
		ResourceSet emfResourceSet = getEMFModelFromImports(folderName, project.getImports());
		//ResourceSet emfResourceSet = getEMFModelForTest(folderName+"/"+project.getSource());
		Assert.notNull(emfResourceSet, "ResourceSet must exist");
		//Project project = createProject(emfResourceSet);
		initializeProject(project, emfResourceSet);
		
		List<Project> importedModels = getAriesModelsForTest(folderName, project.getImports());
		ProjectUtil.getSubProjects(project).addAll(importedModels);
		context.setProject(project);
		return project;
	}

	protected List<Project> getAriesModelsForTest(String folderName, Imports imports) throws Exception {
		List<Project> importProjects = new ArrayList<Project>();
		List<Import> importList = imports.getImports();
		Iterator<Import> iterator = importList.iterator();
		while (iterator.hasNext()) {
			Import importObject = iterator.next();
			String fileName = importObject.getFile();
			if (fileName.endsWith("aries")) {
				Project importedProject = getAriesModelForTest(folderName+"/"+fileName);
				importProjects.add(importedProject);
			}
		}
		return importProjects;
	}
	
	protected void initializeProject(Project project, ResourceSet emfResourceSet) throws Exception {
		List<Namespace> projectNamespaces = engine.getModelBuilder().buildNamespaces(emfResourceSet.getPackageRegistry());
		Information informationBlock = InformationUtil.newInformation();
		informationBlock.getNamespaces().addAll(projectNamespaces);
		ProjectUtil.addInformationBlock(project, informationBlock);
		context.populateNamespaces(projectNamespaces);
		
		engine.getModelHelper().assureProject(project);
		engine.getModelHelper().assureApplications(project);
		engine.getModelHelper().assureModules(project);
		
		Collection<Application> applications = ProjectUtil.getApplications(project);
		Iterator<Application> iterator2 = applications.iterator();
		while (iterator2.hasNext()) {
			Application application = iterator2.next();
			engine.getModelBuilder().initializeApplicationFromResources(application, emfResourceSet.getResources());
		}

		List<Service> services = engine.getModelBuilder().buildServices(emfResourceSet);
		Map<String, Service> emfServiceMap = ServiceUtil.createMap(services);

		Set<Module> modules = ProjectUtil.getServiceModules(project);
		Iterator<Module> moduleIterator = modules.iterator();
		while (moduleIterator.hasNext()) {
			Module module = moduleIterator.next();
		
			List<Service> serviceRefs = ModuleUtil.getServices(module);
			Iterator<Service> iterator = serviceRefs.iterator();
			while (iterator.hasNext()) {
				Service service = iterator.next();
				String name = service.getName();
				Assert.notNull(name, "Service name must exist");
				Service emfService = emfServiceMap.get(name);
				Assert.notNull(emfService, "Referenced service must exist");

				service = ObjectUtil.cloneObject(emfService);
				String portType = "{" + service.getNamespace() + "}" + name;
				service.setPortType(portType);
				context.addImportedService(service);
				emfServiceMap.remove(name);
			}
		}

		engine.getModelHelper().assureServices(project);
		engine.getModelHelper().assureProcesses(project);
	}
	
	public void assureServices(Project project) {
		Collection<Service> services = ProjectUtil.getServices(project);
		assureServices(services);

//		List<Process> processes = context.getProcesses();
//		Iterator<Process> iterator = processes.iterator();
//		while (iterator.hasNext()) {
//			Process process = iterator.next();
//			Application application = ProjectUtil.getApplication(project);
//			List<Service> services = ApplicationUtil.getServices(application);
//			assureServices(services);
//		}
	}

	public void assureServices(Collection<Service> services) {
		Iterator<Service> iterator = services.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			Assert.notNull(service.getRef(), "Service reference name must exist");
		}
	}
	
	protected Project createProject(ResourceSet emfResourceSet) throws Exception {
		Project project = new Project();
		ProjectUtil.getNamespaces(project).addAll(engine.getModelBuilder().buildNamespaces(emfResourceSet.getPackageRegistry()));
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
	
		List<Service> services = engine.getModelBuilder().buildServices(emfResourceSet);
		ModuleUtil.getServices(serviceModule).addAll(services);
		ModuleUtil.getServices(clientModule).addAll(services);
		return application;
	}

}
