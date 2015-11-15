package nam.model.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nam.model.Application;
import nam.model.Applications;
import nam.model.Callback;
import nam.model.Command;
import nam.model.CommandName;
import nam.model.Configuration;
import nam.model.Configurations;
import nam.model.Element;
import nam.model.Extensions;
import nam.model.Group;
import nam.model.Groups;
import nam.model.Import;
import nam.model.Imports;
import nam.model.Information;
import nam.model.Messaging;
import nam.model.Module;
import nam.model.ModuleType;
import nam.model.Modules;
import nam.model.Namespace;
import nam.model.Network;
import nam.model.Operation;
import nam.model.Persistence;
import nam.model.Process;
import nam.model.Project;
import nam.model.Projects;
import nam.model.Provider;
import nam.model.Providers;
import nam.model.Service;
import nam.model.statement.ReplyCommand;
import nam.ui.View;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.NameUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class ProjectUtil {

	private static Project project;
	
	public static Project getProject() {
		return project;
	}

	public static void setProject(Project project) {
		ProjectUtil.project = project;
	}

	public static Object getKey(Project project) {
		return project.getNamespace()+":"+project.getName();
	}

	public static String getLabel(Project project) {
		return project.getName();
	}

	public static boolean isEmpty(Project project) {
		if (project == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Project> projectList) {
		if (projectList == null  || projectList.size() == 0)
			return true;
		Iterator<Project> iterator = projectList.iterator();
		while (iterator.hasNext()) {
			Project project = iterator.next();
			if (!isEmpty(project))
				return false;
		}
		return true;
	}
	
	public static String toString(Project project) {
		if (isEmpty(project))
			return "Project: [uninitialized] "+project.toString();
		String text = project.toString();
		return text;
	}
	
	public static String toString(Collection<Project> projectList) {
		if (isEmpty(projectList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Project> iterator = projectList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Project project = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(project);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Project create() {
		Project project = new Project();
		initialize(project);
		return project;
	}
	
	public static void initialize(Project project) {
		Date now = new Date();
		project.setCreationDate(now);
		project.setLastUpdate(now);
	}
	
	public static boolean validate(Project project) {
		if (project == null)
			return false;
		Validator validator = Validator.getValidator();
		ApplicationUtil.validate(project.getApplications());
		ModuleUtil.validate(project.getModules());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Project> projectList) {
		Validator validator = Validator.getValidator();
		Iterator<Project> iterator = projectList.iterator();
		while (iterator.hasNext()) {
			Project project = iterator.next();
			//TODO break or accumulate?
			validate(project);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Project> projectList) {
		Collections.sort(projectList, createProjectComparator());
	}
	
	public static Collection<Project> sortRecords(Collection<Project> projectCollection) {
		List<Project> list = new ArrayList<Project>(projectCollection);
		Collections.sort(list, createProjectComparator());
		return list;
	}
	
	public static Comparator<Project> createProjectComparator() {
		return new Comparator<Project>() {
			public int compare(Project project1, Project project2) {
				String name1 = project1.getName();
				String name2 = project2.getName();
				int status = name1.compareTo(name2);
				return status;
			}
		};
	}
	
	public static Project clone(Project project) {
		if (project == null)
			return null;
		Project clone = create();
		clone.setName(ObjectUtil.clone(project.getName()));
		clone.setLabel(ObjectUtil.clone(project.getLabel()));
		clone.setDomain(ObjectUtil.clone(project.getDomain()));
		clone.setNamespace(ObjectUtil.clone(project.getNamespace()));
		clone.setVersion(ObjectUtil.clone(project.getVersion()));
		clone.setDescription(ObjectUtil.clone(project.getDescription()));
		clone.setOwner(ObjectUtil.clone(project.getOwner()));
		clone.setModules(ModuleUtil.clone(project.getModules()));
		clone.setApplications(ApplicationUtil.clone(project.getApplications()));
		clone.setCreationDate(ObjectUtil.clone(project.getCreationDate()));
		clone.setLastUpdate(ObjectUtil.clone(project.getLastUpdate()));
		return clone;
	}


	public static List<Import> getImports(Project project) {
		Imports imports = getImportsInternal(project);
		List<Import> objects = imports.getImports();
		return objects;
	}

	public static List<Import> getImportsSorted(Project project) {
		List<Import> imports = getImports(project);
		Collections.sort(imports, new Comparator<Import>() {
			public int compare(Import object1, Import object2) {
				return object1.getFile().compareTo(object2.getFile());
			}
		});
		return imports;
	}

	public static Imports getImportsInternal(Project project) {
		Imports imports = project.getImports();
		if (imports == null) {
			imports = new Imports();
			project.setImports(imports);
		}
		return imports;
	}
	
	public static boolean containsImport(List<Import> imports, Import model) {
		if (imports.contains(model))
			return true;
		Iterator<Import> iterator = imports.iterator();
		while (iterator.hasNext()) {
			Import importedFile = iterator.next();
			if (importedFile.getFile().contains(model.getFile()))
				return true;
			if (model.getFile().contains(importedFile.getFile()))
				return true;
		}
		return false;
	}
	
	public static void addImports(Project project, List<Import> importedFiles) {
		Iterator<Import> iterator = importedFiles.iterator();
		while (iterator.hasNext()) {
			Import importedFile = iterator.next();
			addImport(project, importedFile);
		}
	}
	
	public static void addImport(Project project, Import importedFile) {
		Imports imports = getImportsInternal(project);
		List<Import> importedFiles = imports.getImports();
		Iterator<Import> iterator = importedFiles.iterator();
		boolean alreadyExists = false;
		while (iterator.hasNext()) {
			Import currentlyImportedFile = iterator.next();
			if (currentlyImportedFile.getFile().equals(importedFile.getFile())) {
				alreadyExists = true;
				break;
			}
		}
		if (!alreadyExists)
			importedFiles.add(importedFile);
	}
	
	public static List<Project> getSubProjects(Project project) {
		Projects projects = project.getSubProjects();
		if (projects == null) {
			projects = new Projects();
			project.setSubProjects(projects);
		}
		return projects.getProjects();
	}

	public static Set<Module> getProjectModules(Project project) {
		Modules modules = project.getModules();
		if (modules == null) {
			modules = new Modules();
			project.setModules(modules);
		}
		return ModuleUtil.getModules(modules);
	}

	public static Set<Module> getProjectModules(Project project, ModuleType moduleType) {
		Set<Module> list = new HashSet<Module>();
		Set<Module> modules = getProjectModules(project);
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			if (module.getType() == moduleType) {
				list.add(module);
			}
		}
		return list;
	}

	public static Module getProjectModule(Project project, String name) {
		Set<Module> projectModules = getProjectModules(project);
		Iterator<Module> iterator = projectModules.iterator();
		while (iterator.hasNext()) {
			Module module = (Module) iterator.next();
			if (module.getName().equals(name))
				return module;
		}
		return null;
	}

	public static void addModule(Project project, Module module) {
		ModuleUtil.addModule(project.getModules(), module);
	}

	public static void addModules(Project project, Collection<Module> modules) {
		ModuleUtil.addModules(project.getModules(), modules);
	}

	public static void addModule(Application application, Module module) {
		ModuleUtil.addModule(application.getModules(), module);
	}

	public static void addModules(Application application, Collection<Module> modules) {
		ModuleUtil.addModules(application.getModules(), modules);
	}

	public static boolean removeModule(Project project, Module module) {
		return ModuleUtil.removeModule(project.getModules(), module);
	}

	public static boolean removeModule(Application application, Module module) {
		return ModuleUtil.removeModule(application.getModules(), module);
	}

	public static Collection<Module> getDeployableModules(Project project, boolean isMain, boolean isHosted) {
		Set<Module> modules = getProjectModules(project);
		return getDeployableModules(modules, isMain, isHosted);
	}
	
	public static Collection<Module> getDeployableModules(Application application, boolean isMain, boolean isHosted) {
		Collection<Module> modules = ApplicationUtil.getModules(application);
		return getDeployableModules(modules, isMain, isHosted);
	}
	
	/**
	 * Determines set of deployable modules (i.e. for inclusion in EAR).
	 * @param project The project from which modules are are processed.
	 * @param isHosted Indicates the application is "included/hosted" as opposed to being "referenced".
	 * @return set of deployable modules (i.e. for inclusion in EAR).
	 */
	public static Collection<Module> getDeployableModules(Collection<Module> modules, boolean isMain, boolean isHosted) {
		Set<Module> list = new LinkedHashSet<Module>();
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			if (module.getType() == ModuleType.POM)
				continue;
			if (module.getType() == ModuleType.EAR)
				continue;
			if (!isMain) {
				if (module.getType() == ModuleType.VIEW)
					continue;
			}
			if (!isHosted) {
				if (module.getType() == ModuleType.SERVICE)
					continue;
				if (module.getType() == ModuleType.DATA)
					continue;
			}
			list.add(module);
		}
		return list;
	}
	
	public static List<Namespace> getNamespaces(Project project) {
		List<Namespace> list = new ArrayList<Namespace>();
		Collection<Information> informationBlocks = getInformationBlocks(project);
		Iterator<Information> iterator = informationBlocks.iterator();
		while (iterator.hasNext()) {
			Information information = iterator.next();
			List<Namespace> namespaces = InformationUtil.getNamespaces(information);
			Iterator<Namespace> iterator2 = namespaces.iterator();
			while (iterator2.hasNext()) {
				Namespace namespace = iterator2.next();
				NamespaceUtil.addNamespace(list, namespace);
				NamespaceUtil.addImportedNamespaces(list, namespace);
			}
		}
		return list;
	}

	public static Collection<Namespace> getNamespaces(Collection<Project> projects) {
		Collection<Namespace> namespaces = new ArrayList<Namespace>();
		Iterator<Project> iterator = projects.iterator();
		while (iterator.hasNext()) {
			Project project = (Project) iterator.next();
			namespaces.addAll(getNamespaces(project));
		}
		return namespaces;
	}

	public static Collection<Element> getElements(Collection<Project> projects) {
		Collection<Element> elements = new ArrayList<Element>();
		Iterator<Project> iterator = projects.iterator();
		while (iterator.hasNext()) {
			Project project = iterator.next();
			elements.addAll(getElements(project));
		}
		return elements;
	}
	
	public static Collection<Element> getElements(Project project) {
		Collection<Namespace> namespaces = getNamespaces(project);
		Collection<Element> elements = NamespaceUtil.getElements(namespaces);
		return elements;
	}

	public static void addInformationBlock(Project project, Information newBlock) {
//		if (newBlock.getName() == null)
//			System.out.println();
		String newBlockName = newBlock.getName();
		Collection<Information> blocks = getInformationBlocks(project);
		Iterator<Information> iterator = blocks.iterator();
		Information existingBlock = null;
		boolean found = false;
		while (iterator.hasNext()) {
			Information block = iterator.next();
			if (block == newBlock)
				return;
//			if (block == null)
//				System.out.println();
//			if (block.getName() == null)
//				System.out.println();
			if (block.getName().equalsIgnoreCase(newBlockName)) {
				existingBlock = block;
				found = true;
				break;
			}
		}
		if (!found) {
			Extensions extensions = project.getExtensions();
			extensions.getInformationsAndMessagingsAndPersistences().add(newBlock);
		} else {
			InformationUtil.mergeNamespaces(existingBlock, newBlock);
		}
	}
	
	public static List<Information> getAllInformationBlocks(Project project) {
		List<Information> informationBlocks = new ArrayList<Information>();
		informationBlocks.addAll(getInformationBlocks(project));
		List<Project> subProjects = getSubProjects(project);
		Iterator<Project> iterator = subProjects.iterator();
		while (iterator.hasNext()) {
			Project subProject = iterator.next();
			informationBlocks.addAll(getInformationBlocks(subProject));
		}
		return informationBlocks;
	}
	
	public static Information getInformationBlock(Project project) {
		Collection<Information> informationBlocks = getInformationBlocks(project);
		Iterator<Information> iterator = informationBlocks.iterator();
		String uri = project.getNamespace();
		while (iterator.hasNext()) {
			Information informationBlock = iterator.next();
			Iterator<Namespace> iterator2 = informationBlock.getNamespaces().iterator();
			while (iterator2.hasNext()) {
				Namespace namespace = iterator2.next();
				if (namespace.getUri().equals(uri)) {
					return informationBlock;
				}
			}
		}
		return null;
	}
	
	public static Collection<Information> getInformationBlocks(Project project) {
		return getObjectList(project, Information.class);
	}

	public static Collection<Information> getInformationBlocks(Collection<Project> projectList) {
		List<Information> list = new ArrayList<Information>();
		Iterator<Project> iterator = projectList.iterator();
		while (iterator.hasNext()) {
			Project project = iterator.next();
			list.addAll(getInformationBlocks(project));
		}
		return list;
	}

	public static Set<Information> getInformationBlocksFromModules(Project project) {
		Set<Information> informationSets = new LinkedHashSet<Information>();
		Set<Module> modules = ProjectUtil.getModelModules(project);
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			Information information = module.getInformation();
			if (information != null)
				informationSets.add(information);
		}
		return informationSets;
	}

	public static Information getInformationBlock(Project project, Namespace namespace) {
		List<Information> allInformationBlocks = getAllInformationBlocks(project);
		Iterator<Information> iterator = allInformationBlocks.iterator();
		while (iterator.hasNext()) {
			Information informationBlock = iterator.next();
			if (informationBlock.getNamespaces().contains(namespace))
				return informationBlock;
		}
		return null;
	}

	public static <T> List<T> getObjectList(Project project, Class<?> objectClass) {
		return getObjectList(project.getExtensions(), objectClass);
	}
	
	public static <T> List<T> getObjectList(Extensions extensions, Class<?> objectClass) {
		List<Serializable> objects = extensions.getInformationsAndMessagingsAndPersistences();
		return ListUtil.getObjectList(objects, objectClass);
	}

	public static <T> T getObject(Project project, Class<?> objectClass) {
		return getObject(project.getExtensions(), objectClass);
	}
	
	public static <T> T getObject(Extensions extensions, Class<?> objectClass) {
		List<Serializable> objects = extensions.getInformationsAndMessagingsAndPersistences();
		return ListUtil.getObject(objects, objectClass);
	}
	

	public static void addConfigurations(Project project, List<Configuration> configurations) {
		Iterator<Configuration> iterator = configurations.iterator();
		while (iterator.hasNext()) {
			Configuration configuration = iterator.next();
			addConfiguration(project, configuration);
		}
	}
	
	public static void addConfiguration(Project project, Configuration configuration) {
		Configurations configurations = getConfigurationParent(project);
		ConfigurationUtil.addConfiguration(configurations, configuration);
	}

	public static List<Configuration> getConfigurations(Project model) {
		Configurations configurations = model.getConfigurations();
		if (configurations == null) {
			configurations = new Configurations();
			model.setConfigurations(configurations);
		}
		return configurations.getConfigurations();
	}
	
	public static Configurations getConfigurationParent(Project project) {
		Configurations configurations = project.getConfigurations();
		if (configurations == null) {
			configurations = new Configurations();
			project.setConfigurations(configurations);
		}
		return configurations;
	}

	public static Group getGroupByName(List<Project> projects, String name) {
		Iterator<Project> iterator = projects.iterator();
		while (iterator.hasNext()) {
			Project project = iterator.next();
			Group group = getGroupByName(project, name);
			if (group != null)
				return group;
		}
		return null;
	}
	
	public static Group getGroupByName(Project project, String name) {
		List<Group> groups = getGroups(project);
		Iterator<Group> iterator = groups.iterator();
		while (iterator.hasNext()) {
			Group group = iterator.next();
			if (group.getName().equalsIgnoreCase(name)) {
				return group;
			}
		}
		return null;
	}
	

	public static boolean containsProject(Collection<Project> projectList, Project project) {
		return projectList.contains(project);
	}
	
	public static void addProject(Collection<Project> projectList, Project project) {
		if (!containsProject(projectList, project)) {
			projectList.add(project);
		}
	}

	public static void addProjects(Collection<Project> projectList, Collection<Project> projectsToAdd) {
		Iterator<Project> iterator = projectsToAdd.iterator();
		while (iterator.hasNext()) {
			Project project = iterator.next();
			addProject(projectList, project);
		}
	}

	public static void addSubProjects(Project project, List<Project> subProjects) {
		Projects projects = project.getSubProjects();
		if (projects == null) {
			projects = new Projects();
			project.setSubProjects(projects);
		}
		Iterator<Project> iterator = subProjects.iterator();
		while (iterator.hasNext()) {
			Project subProject = iterator.next();
			if (project == subProject)
				continue;
			if (projects.getProjects().contains(subProject))
				continue;
			projects.getProjects().add(subProject);
		}
	}
	
	public static boolean removeProject(List<Project> projectList, Project project) {
		if (containsProject(projectList, project))
			return projectList.remove(project);
		return false;
	}
	
//	public static Application getApplication(Project project) {
//		List<Application> applications = getApplications(project);
//		return applications.size() > 0 ? applications.get(0) : null;
//	}
	
	public static void addApplications(Project project, Collection<Application> applications) {
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			addApplication(project, application);
		}
	}
	
	public static void addApplication(Project project, Application application) {
		Applications applications = getApplicationParent(project);
		ApplicationUtil.addApplication(applications, application);
	}
	
	public static boolean removeApplication(Collection<Project> projectList, Application application) {
		Iterator<Project> iterator = projectList.iterator();
		while (iterator.hasNext()) {
			Project project = iterator.next();
			if (removeApplication(project, application))
				return true;
		}
		return false;
	}
	
	public static boolean removeApplication(Project project, Application application) {
		Applications applications = getApplicationParent(project);
		return ApplicationUtil.removeApplication(applications, application);
	}

	public static void removeApplications(Project project) {
		Applications applications = getApplicationParent(project);
		ApplicationUtil.removeApplications(applications, applications);
	}

	public static Collection<Application> getApplications(Collection<Project> projectList) {
		Collection<Application> applications = new ArrayList<Application>();
		if (projectList != null) {
			Iterator<Project> iterator = projectList.iterator();
			while (iterator.hasNext()) {
				Project project = iterator.next();
				applications.addAll(getApplications(project));
			}
		}
		return applications;
	}
	
	public static Collection<Application> getApplications(Project project) {
		Applications applications = getApplicationParent(project);
		return ApplicationUtil.getApplications(applications);
	}
	
	public static Collection<Network> getNetworks(Project project) {
		return NetworkUtil.getNetworks(project);
	}

	public static void addNetwork(Project project, Network network) {
		NetworkUtil.addNetwork(project.getNetworks(), network);
	}

	public static boolean removeNetwork(Project project, Network network) {
		return NetworkUtil.removeNetwork(project.getNetworks(), network);
	}

	public static Applications getApplicationParent(Project project) {
		Applications applications = project.getApplications();
		if (applications == null) {
			applications = new Applications();
			project.setApplications(applications);
		}
		return applications;
	}

	public static Collection<Application> getApplicationsSorted(Project project) {
		List<Application> applications = new ArrayList<Application>(getApplications(project));
		Collections.sort(applications, new Comparator<Application>() {
			public int compare(Application application1, Application application2) {
				return application1.getName().compareTo(application2.getName());
			}
		});
		return applications;
	}

	public static Set<Module> getApplicationModules(Project project) {
		Set<Module> modules = new HashSet<Module>();
		Collection<Application> applications = getApplications(project);
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = (Application) iterator.next();
			modules.addAll(ApplicationUtil.getModules(application));
		}
		return modules;
	}

	public static Application getApplicationByName(List<Project> projects, String name) {
		Iterator<Project> iterator = projects.iterator();
		while (iterator.hasNext()) {
			Project project = iterator.next();
			Application application = getApplicationByName(project, name);
			if (application != null) {
				return application;
			}
		}
		return null;
	}
	
	public static Application getApplicationByName(Project project, String name) {
		Collection<Application> applications = getApplications(project);
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = (Application) iterator.next();
			if (application.getName().equalsIgnoreCase(name)) {
				return application;
			}
		}
		Group group = ProjectUtil.getGroupByName(project, name);
		//Assert.notNull(remoteGroup, "Remote Group not found: " + targetName);
		if (group != null) {
			//applications = group.getApplications();
			//TODO for now just take the first application - since it is homogenous
			List<Application> applicationList = group.getApplications();
			if (!applicationList.isEmpty()) {
				Application application = applicationList.get(0);
				return application;
			}
		}
		return null;
	}

	public static Collection<Application> getApplicationsForGroupId(Collection<Project> projects, String groupId) {
		Collection<Application> applications = new ArrayList<Application>();
		Collection<Application> currentApplications = getApplications(projects);
		Iterator<Application> iterator = currentApplications.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			if (application.getGroupId().equalsIgnoreCase(groupId)) {
				applications.add(application);
			}
		}
		return applications;
	}
	
	public static Application getApplicationByNamespace(Project project, String namespace) {
		Collection<Application> applications = getApplications(project);
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = (Application) iterator.next();
			if (application.getNamespace().equalsIgnoreCase(namespace)) {
				return application;
			}
		}
		return null;
	}
	
	public static Application getApplicationByArtifactId(Project project, String artifactId) {
		Collection<Application> applications = getApplications(project);
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = (Application) iterator.next();
			if (application.getArtifactId().equalsIgnoreCase(artifactId)) {
				return application;
			}
		}
		return null;
	}

	public static Collection<Service> getServices(Project project) {
		Set<Service> services = new HashSet<Service>();
		Collection<Application> applications = getApplications(project);
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			services.addAll(ApplicationUtil.getServices(application));
		}
		return services;
	}
	
	public static Collection<Service> getServices(Collection<Project> projectList) {
		Set<Service> services = new HashSet<Service>();
		Iterator<Project> iterator = projectList.iterator();
		while (iterator.hasNext()) {
			Project project = iterator.next();
			services.addAll(getServices(project));
		}
		return services;
	}
	
	public static Collection<Service> getServicesSorted(Project project) {
		Set<Service> services = new HashSet<Service>();
		Collection<Application> applications = getApplicationsSorted(project);
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			services.addAll(ApplicationUtil.getServicesSorted(application));
		}
		return services;
	}

	public static Service getServiceByName(Project project, String serviceName) {
		Collection<Service> services = getServices(project);
		Iterator<Service> iterator = services.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			if (service.getName().equalsIgnoreCase(serviceName))
				return service;
		}
		return null;
	}
	
	public static Service getServiceByName(List<Project> projects, String serviceName) {
		Iterator<Project> iterator = projects.iterator();
		while (iterator.hasNext()) {
			Project project = iterator.next();
			Service service = getServiceByName(project, serviceName);
			if (service != null)
				return service;
		}
		return null;
	}
	
	public static Service getServiceByName(List<Project> projects, String applicationName, String serviceName) {
		Iterator<Project> iterator = projects.iterator();
		while (iterator.hasNext()) {
			Project project = iterator.next();
			Service service = getServiceByName(project, applicationName, serviceName);
			if (service != null)
				return service;
		}
		return null;
	}
	
	public static Service getServiceByName(Project project, String applicationName, String serviceName) {
//		if (applicationName.equals("PLACEHOLDER"))
//			System.out.println();
		Collection<Application> applications = getApplications(project);
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			String name = application.getName();
			if (applicationName.equalsIgnoreCase(name) || applicationName.equalsIgnoreCase("PLACEHOLDER")) {
				Service service = ApplicationUtil.getServiceByName(application, serviceName);
				if (service != null)
					return service;
				Callback callback = ApplicationUtil.getIncomingCallbackByName(application, serviceName);
				if (callback != null)
					return callback;
				callback = ApplicationUtil.getOutgoingCallbackByName(application, serviceName);
				if (callback != null)
					return callback;
			}
		}
		return null;
	}
	
	public static Callback getIncomingCallbackByName(List<Project> projects, String callbackName) {
		Iterator<Project> iterator = projects.iterator();
		while (iterator.hasNext()) {
			Project project = iterator.next();
			Callback callback = getIncomingCallbackByName(project, callbackName);
			if (callback != null)
				return callback;
		}
		return null;
	}

	public static Callback getIncomingCallbackByName(Project project, String callbackName) {
		Collection<Application> applications = getApplications(project);
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			Callback callback = ApplicationUtil.getIncomingCallbackByName(application, callbackName);
			if (callback != null)
				return callback;
		}
		return null;
	}
	
	public static Callback getOutgoingCallbackByName(List<Project> projects, String callbackName) {
		Iterator<Project> iterator = projects.iterator();
		while (iterator.hasNext()) {
			Project project = iterator.next();
			Callback callback = getOutgoingCallbackByName(project, callbackName);
			if (callback != null)
				return callback;
		}
		return null;
	}
	
	public static Callback getOutgoingCallbackByName(Project project, String callbackName) {
		Collection<Application> applications = getApplications(project);
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			Callback callback = ApplicationUtil.getOutgoingCallbackByName(application, callbackName);
			if (callback != null)
				return callback;
		}
		return null;
	}

	
	/*
	 * Remote Endpoints
	 * ----------------
	 */
	
	public static List<Service> getRemoteEndpoints(Project project, Service service) {
		List<Service> remoteEndpoints = new ArrayList<Service>();
		if (ServiceUtil.isStateful(service)) {
			Process process = service.getProcess();
			//String serviceName = NameUtil.capName(service.getName());
			String operationName = service.getName();
			//if (service instanceof Callback)
			//	operationName = "response" + serviceName;
			//else operationName = "request" + serviceName;
			Operation operation = ProcessUtil.getOperation(process, operationName);
			if (operation != null)
				remoteEndpoints.addAll(getRemoteEndpoints(project, service, operation));
		}
		return remoteEndpoints;
	}

	public static List<Service> getRemoteEndpoints(Project project, Service service, Operation operation) {
		return getRemoteEndpoints(project, service, operation.getCommands());
	}
	
	public static List<Service> getRemoteEndpoints(Project project, Service service, List<Command> commands) {
		List<Service> remoteEndpoints = new ArrayList<Service>();
		Iterator<Command> iterator = commands.iterator();
		while (iterator.hasNext()) {
			Command command = iterator.next();
			String commandText = command.getText();
			if (command.getName() == CommandName.SEND) {
				String remoteName = commandText;
				Service remoteService = ProjectUtil.getServiceByName(project, remoteName);
				remoteEndpoints.add(remoteService);

			} else if (command.getName() == CommandName.INVOKE) {
				String remoteName = NameUtil.getSimpleName(commandText);
				Service remoteService = ProjectUtil.getServiceByName(project, remoteName);
				remoteEndpoints.add(remoteService);
					
			} else if (command.getName() == CommandName.REPLY) {
				ReplyCommand replyCommand = (ReplyCommand) command;
				String remoteName = replyCommand.getMessageName();
				if (remoteName.endsWith("Message"))
					remoteName = remoteName.substring(0, remoteName.length() - 7);
				Callback callback = ProjectUtil.getOutgoingCallbackByName(project, remoteName);
				remoteEndpoints.add(callback);
			}
			List<Service> serviceList = getRemoteEndpoints(project, service, command.getCommands());
			remoteEndpoints.addAll(serviceList);
		}
		return remoteEndpoints;
	}
	
	

	public static Collection<Module> getModules(Collection<Project> projectList) {
		Set<Module> moduleList = new HashSet<Module>();
		Iterator<Project> iterator = projectList.iterator();
		while (iterator.hasNext()) {
			Project project = iterator.next();
			moduleList.addAll(ProjectUtil.getModules(project));
		}
		return moduleList;
	}
	
	public static Set<Module> getModules(Project project) {
		Modules modules = getModuleParent(project);
		return ModuleUtil.getModules(modules);
	}

	public static Modules getModuleParent(Project project) {
		Modules modules = project.getModules();
		if (modules == null) {
			modules = new Modules();
			project.setModules(modules);
		}
		return modules;
	}

	public static Set<Module> getModelModules(Project project) {
		return getAllModules(project, ModuleType.MODEL);
	}

	public static Set<Module> getClientModules(Project project) {
		return getAllModules(project, ModuleType.CLIENT);
	}

	public static Set<Module> getServiceModules(Project project) {
		return getAllModules(project, ModuleType.SERVICE);
	}

	public static Set<Module> getDataModules(Project project) {
		return getAllModules(project, ModuleType.DATA);
	}

	public static Set<Module> getViewModules(Project project) {
		return getAllModules(project, ModuleType.VIEW);
	}

	public static Set<Module> getEARModules(Project project) {
		return getApplicationModules(project, ModuleType.EAR);
	}

	public static Module getPomModule(Project project) {
		Set<Module> pomModules = getPomModules(project);
		if (pomModules.size() > 0)
			return pomModules.iterator().next();
		return null;
	}
	
	public static Set<Module> getPomModules(Project project) {
		return getApplicationModules(project, ModuleType.POM);
	}

	public static Set<Module> getAllModules(Project project) {
		return ModuleUtil.getAllModules(project);
	}
	
	public static Set<Module> getAllModules(Project project, ModuleType moduleType) {
		return ModuleUtil.getAllModules(project, moduleType);
	}

	public static Set<Module> getApplicationModules(Project project, ModuleType moduleType) {
		return ApplicationUtil.getModules(project, moduleType);
	}

	public static Map<String, Module> getModulesAsMap(Project project, ModuleType moduleType) {
		return ApplicationUtil.getModulesAsMap(project, moduleType);
	}
	
	public static Map<String, Module> getModulesAsMap(Project project) {
		return ApplicationUtil.getModulesAsMap(project);
	}

	public static Module getModule(Project project, String name) {
		Module module = getProjectModule(project, name);
		if (module == null)
			module = getModulesAsMap(project).get(name);
		return module;
	}

	public static Module getModelModule(Project project, Namespace namespace) {
		Set<Module> modelModules = getModelModules(project);
		Iterator<Module> iterator = modelModules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			if (module.getNamespace().equals(namespace.getUri()))
				return module;
		}
		return null;
	}

	public static Module getDataModule(Project project, Namespace namespace) {
		Set<Module> modelModules = getDataModules(project);
		Iterator<Module> iterator = modelModules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			if (module.getNamespace().equals(namespace.getUri()))
				return module;
		}
		return null;
	}
	
	
	public static boolean containsGroup(Groups groups, Group group) {
		return getGroups(groups).contains(group);
	}
	
	public static void addGroups(Project project, List<Group> groups) {
		Iterator<Group> iterator = groups.iterator();
		while (iterator.hasNext()) {
			Group group = iterator.next();
			addGroup(project, group);
		}
	}
	
	public static void addGroup(Project project, Group group) {
		Groups groups = getGroupsObject(project);
		if (!containsGroup(groups, group))
			groups.getGroups().add(group);
	}
	
	public static Groups getGroupsObject(Project project) {
		Groups groups = project.getGroups();
		if (groups == null) {
			groups = new Groups();
			project.setGroups(groups);
		}
		return groups;
	}
	
	public static List<Group> getGroups(Project project) {
		Groups groups = project.getGroups();
		if (groups == null) {
			groups = new Groups();
			project.setGroups(groups);
		}
		return groups.getGroups();
	}

	public static List<Group> getGroups(Groups groups) {
		return groups.getGroups();
	}
	
	
	public static void addViewBlock(Project project, View newView) {
		if (newView != null) {
			String newViewName = newView.getName();
			List<View> viewBlocks = getViewBlocks(project);
			Iterator<View> iterator = viewBlocks.iterator();
			boolean viewBlockExists = false;
			while (iterator.hasNext()) {
				View viewBlock = iterator.next();
				if (viewBlock.getName().equalsIgnoreCase(newViewName)) {
					viewBlockExists = true;
					//TODO do any merge here?
					break;
				}
			}
			if (!viewBlockExists) {
				Extensions extensions = project.getExtensions();
				extensions.getInformationsAndMessagingsAndPersistences().add(newView);
			}
		}
	}
	
	public static List<View> getViewBlocks(Collection<Project> projects) {
		List<View> viewBlocks = new ArrayList<View>();
		Iterator<Project> iterator = projects.iterator();
		while (iterator.hasNext()) {
			Project project = iterator.next();
			List<View> viewBlocksForProject = getViewBlocks(project);
			viewBlocks.addAll(viewBlocksForProject);
		}
		return viewBlocks;
	}

	public static List<View> getViewBlocks(Project project) {
		return getObjectList(project, View.class);
	}
	
	
	public static void addMessagingBlock(Project project, Messaging newMessaging) {
		if (newMessaging != null) {
			String newMessagingName = newMessaging.getName();
			List<Messaging> messagingBlocks = getMessagingBlocks(project);
			Iterator<Messaging> iterator = messagingBlocks.iterator();
			boolean messagingExists = false;
			while (iterator.hasNext()) {
				Messaging messagingBlock = iterator.next();
				if (messagingBlock.getName().equalsIgnoreCase(newMessagingName)) {
					messagingExists = true;
					//TODO do any merge here?
					break;
				}
			}
			if (!messagingExists) {
				Extensions extensions = project.getExtensions();
				extensions.getInformationsAndMessagingsAndPersistences().add(newMessaging);
			}
		}
	}
	
	public static List<Messaging> getMessagingBlocks(Collection<Project> projects) {
		List<Messaging> messagingBlocks = new ArrayList<Messaging>();
		Iterator<Project> iterator = projects.iterator();
		while (iterator.hasNext()) {
			Project project = iterator.next();
			List<Messaging> messagingBlocksForProject = getMessagingBlocks(project);
			messagingBlocks.addAll(messagingBlocksForProject);
		}
		return messagingBlocks;
	}

	public static List<Messaging> getMessagingBlocks(Project project) {
		return getObjectList(project, Messaging.class);
	}

//	public static List<Messaging> getMessagingBlocksFromModules(Project project) {
//		List<Messaging> messagingSets = new ArrayList<Messaging>();
//		List<Module> modules = ProjectUtil.getServiceModules(project);
//		Iterator<Module> iterator = modules.iterator();
//		while (iterator.hasNext()) {
//			Module module = iterator.next();
//			Messaging messaging = module.getMessaging();
//			if (messaging != null)
//				messagingSets.add(messaging);
//		}
//		return messagingSets;
//	}
	
//	public static Messaging getMessaging(Project project) {
//		Messaging messaging = project.getMessaging();
//		if (messaging == null) {
//			messaging = MessagingUtil.newMessaging();
//			project.setMessaging(messaging);
//		}
//		return messaging;
//	}

//	public static List<Provider> getMessagingProviders(Project model) {
//		Messaging messaging = model.getMessaging();
//		if (messaging == null)
//			messaging = MessagingUtil.newMessaging();
//		return MessagingUtil.getProviders(messaging);
//	}
	
//	//TODO - merge invokers of this with the above method
//	public static Information getInformation(Project project) {
//		Information information = project.getInformation();
//		if (information == null) {
//			information = new Information();
//			project.setInformation(information);
//		}
//		return information;
//	}
	
	public static void addPersistenceBlock(Project project, Persistence newPersistence) {
		if (newPersistence != null) {
			String newPersistenceName = newPersistence.getName();
			List<Persistence> blocks = getPersistenceBlocks(project);
			Iterator<Persistence> iterator = blocks.iterator();
			boolean persistenceExists = false;
			while (iterator.hasNext()) {
				Persistence block = iterator.next();
				if (block == newPersistence)
					return;
				if (block.getName().equalsIgnoreCase(newPersistenceName)) {
					//TODO do any merge here?
					persistenceExists = true;
					break;
				}
			}
			if (!persistenceExists) {
				Extensions extensions = project.getExtensions();
				extensions.getInformationsAndMessagingsAndPersistences().add(newPersistence);
			}
		}
	}
	
	public static List<Persistence> getPersistenceBlocks(Project project) {
		return getObjectList(project, Persistence.class);
	}

	public static List<Persistence> getPersistenceBlocksFromModules(Project project) {
		List<Persistence> persistenceSets = new ArrayList<Persistence>();
		Set<Module> modules = ProjectUtil.getDataModules(project);
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			Persistence persistence = module.getPersistence();
			if (persistence != null)
				persistenceSets.add(persistence);
		}
		return persistenceSets;
	}
	
	public static List<Persistence> getAllPersistenceBlocks(Project project) {
		List<Persistence> persistenceBlocks = new ArrayList<Persistence>();
		persistenceBlocks.addAll(getPersistenceBlocks(project));
		List<Project> subProjects = getSubProjects(project);
		Iterator<Project> iterator = subProjects.iterator();
		while (iterator.hasNext()) {
			Project subProject = iterator.next();
			persistenceBlocks.addAll(getPersistenceBlocks(subProject));
		}
		return persistenceBlocks;
	}
	
	public static Persistence getPersistenceBlockByName(Project project, String name) {
		List<Persistence> allPersistenceBlocks = getAllPersistenceBlocks(project);
		Iterator<Persistence> iterator = allPersistenceBlocks.iterator();
		while (iterator.hasNext()) {
			Persistence persistenceBlock = iterator.next();
			String blockName = persistenceBlock.getName();
			if (blockName != null && blockName.equals(name))
				return persistenceBlock;
		}
		return null;
	}

	public static Persistence getPersistenceBlockByNamespace(Project project, String namespace) {
		List<Persistence> allPersistenceBlocks = getAllPersistenceBlocks(project);
		Iterator<Persistence> iterator = allPersistenceBlocks.iterator();
		while (iterator.hasNext()) {
			Persistence persistenceBlock = iterator.next();
			String blockNamespace = persistenceBlock.getNamespace();
			if (blockNamespace != null && blockNamespace.equals(namespace))
				return persistenceBlock;
		}
		return null;
	}

	
//	public static Persistence getPersistence(Project project) {
//		Persistence persistence = project.getPersistence();
//		if (persistence == null) {
//			persistence = new Persistence();
//			project.setPersistence(persistence);
//		}
//		return persistence;
//	}

	public static List<Provider> getProviders(Project project) {
		Providers providers = project.getProviders();
		if (providers == null) {
			providers = new Providers();
			project.setProviders(providers);
		}
		return providers.getProviders();
	}

//	public static void mergeProjects(Project project, List<Project> importedProjects) {
//		Iterator<Project> iterator = importedProjects.iterator();
//		while (iterator.hasNext()) {
//			Project importedProject = iterator.next();
//			mergeProjects(project, importedProject);
//		}
//	}
//
//	public static void mergeProjects(Project project, Project importedProject) {
//	}

	public static String getBaseName(String name, String extension) {
		String baseName = null;
		if (name.endsWith(extension))
			baseName = name.substring(0, name.indexOf(extension));
		baseName = NameUtil.capName(baseName);
		return baseName;
	}
	
	public static String getBaseName(String className) {
		String baseName = null;
		if (className.endsWith("Callback"))
			baseName = className.substring(0, className.indexOf("Callback"));
		if (className.endsWith("Process"))
			baseName = className.substring(0, className.indexOf("Process"));
		if (className.endsWith("Receiver"))
			baseName = className.substring(0, className.indexOf("Receiver"));
		if (className.endsWith("Sender"))
			baseName = className.substring(0, className.indexOf("Sender"));
		if (className.endsWith("Service"))
			baseName = className.substring(0, className.indexOf("Service"));
		if (baseName != null)
			baseName = NameUtil.capName(baseName);
		if (baseName != null)
			return baseName;
		return NameUtil.capName(className);
	}

	public static boolean isHomeApplication(Project project, Application application) {
		return application.getGroupId().equals(project.getDomain()) && 
				application.getArtifactId().equals(project.getName());
	}

}
