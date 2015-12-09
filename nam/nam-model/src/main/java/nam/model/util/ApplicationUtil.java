package nam.model.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nam.model.Application;
import nam.model.Applications;
import nam.model.Cache;
import nam.model.Callback;
import nam.model.Channel;
import nam.model.Channels;
import nam.model.Component;
import nam.model.Components;
import nam.model.Dependencies;
import nam.model.Dependency;
import nam.model.Direction;
import nam.model.Element;
import nam.model.Import;
import nam.model.Information;
import nam.model.Messaging;
import nam.model.Module;
import nam.model.ModuleType;
import nam.model.Modules;
import nam.model.Namespace;
import nam.model.Process;
import nam.model.Processes;
import nam.model.Project;
import nam.model.ProjectNames;
import nam.model.Properties;
import nam.model.Property;
import nam.model.Provider;
import nam.model.Service;
import nam.model.Services;
import nam.ui.UserInterface;
import nam.ui.UserInterfaces;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.Assert;
import org.aries.util.BaseUtil;
import org.aries.util.NameUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class ApplicationUtil extends BaseUtil {

	public static String getKey(Application application) {
		return application.getGroupId() + "." + application.getArtifactId();
	}
	
	public static String getLabel(Application application) {
		return NameUtil.capName(application.getName());
	}
	
	public static boolean isEmpty(Application application) {
		if (application == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Application> applicationList) {
		if (applicationList == null  || applicationList.size() == 0)
			return true;
		Iterator<Application> iterator = applicationList.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			if (!isEmpty(application))
				return false;
		}
		return true;
	}
	
	public static String toLabel(Application application) {
		return application.getName();
	}
	
	public static String toString(Application application) {
		if (isEmpty(application))
			return "Application: [uninitialized] "+application.toString();
		String text = application.toString();
		return text;
	}
	
	public static String toString(Collection<Application> applicationList) {
		if (isEmpty(applicationList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Application> iterator = applicationList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Application application = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(application);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Application create() {
		Application application = new Application();
		initialize(application);
		return application;
	}
	
	public static void initialize(Application application) {
		Date now = new Date();
		application.setCreationDate(now);
		application.setLastUpdate(now);
	}
	
	public static boolean validate(Application application) {
		if (application == null)
			return false;
		Validator validator = Validator.getValidator();
		ModuleUtil.validate(ModuleUtil.getModules(application.getModules()));
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Application> applicationList) {
		Validator validator = Validator.getValidator();
		Iterator<Application> iterator = applicationList.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			//TODO break or accumulate?
			validate(application);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Applications applications) {
		boolean isValid = validate(getApplications(applications));
		return isValid;
	}
	
	public static void sortRecords(List<Application> applicationList) {
		Collections.sort(applicationList, createApplicationComparator());
	}
	
	public static Collection<Application> sortRecords(Collection<Application> applicationCollection) {
		List<Application> list = new ArrayList<Application>(applicationCollection);
		Collections.sort(list, createApplicationComparator());
		return list;
	}
	
	public static Comparator<Application> createApplicationComparator() {
		return new Comparator<Application>() {
			public int compare(Application application1, Application application2) {
				String name1 = application1.getName();
				String name2 = application2.getName();
				int status = name1.compareTo(name2);
				return status;
			}
		};
	}
	
	public static Application clone(Application application) {
		if (application == null)
			return null;
		Application clone = create();
		clone.setName(ObjectUtil.clone(application.getName()));
		clone.setLabel(ObjectUtil.clone(application.getLabel()));
		clone.setGroupId(ObjectUtil.clone(application.getGroupId()));
		clone.setArtifactId(ObjectUtil.clone(application.getArtifactId()));
		clone.setVersion(ObjectUtil.clone(application.getVersion()));
		clone.setContextRoot(ObjectUtil.clone(application.getContextRoot()));
		clone.setNamespace(ObjectUtil.clone(application.getNamespace()));
		clone.setDescription(ObjectUtil.clone(application.getDescription()));
		clone.setCreator(ObjectUtil.clone(application.getCreator()));
		clone.setCreationDate(ObjectUtil.clone(application.getCreationDate()));
		clone.setLastUpdate(ObjectUtil.clone(application.getLastUpdate()));
		return clone;
	}

	public static Collection<Application> clone(Collection<Application> applicationList) {
		if (applicationList == null)
			return null;
		List<Application> newList = new ArrayList<Application>();
		Iterator<Application> iterator = applicationList.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			Application clone = clone(application);
			newList.add(clone);
		}
		return newList;
	}
	
	public static Applications clone(Applications applications) {
		Collection<Application> list = clone(getApplications(applications));
		Applications clone = new Applications();
		addApplications(clone, list);
		return clone;
	}
	
	
	public static Collection<Application> getApplications(Applications applications) {
		return getObjectList(applications, Application.class);
	}
	
	public static boolean containsApplication(Applications applications, Application application) {
		Collection<Application> list = getApplications(applications);
		if (list.contains(application))
			return true;
		Iterator<Application> iterator = list.iterator();
		String applicationName = application.getName();
		String applicationNamespace = application.getNamespace();
		while (iterator.hasNext()) {
			Application currentApplication = iterator.next();
			if (currentApplication.getName().equals(applicationName) &&
				currentApplication.getNamespace().equals(applicationNamespace))
					return true;
		}
		return false;
	}
	
	public static void addApplication(Applications applications, Application application) {
		if (!containsApplication(applications, application))
			applications.getImportsAndApplications().add(application);
	}
	
	public static void addApplications(Applications applications, Collection<Application> applicationList) {
		Iterator<Application> iterator = applicationList.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			addApplication(applications, application);
		}
	}

	public static void removeApplications(Applications applications, Applications applicationsToRemove) {
		Collection<Application> applicationListToRemove = getApplications(applicationsToRemove);
		Iterator<Application> iterator = applicationListToRemove.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			removeApplication(applications, application);
		}
	}
	
	public static boolean removeApplication(Applications applications, Application application) {
		if (containsApplication(applications, application))
			return applications.getImportsAndApplications().remove(application);
		return false;
	}
	
	public static List<Import> getImports(Applications applications) {
		return getObjectList(applications, Import.class);
	}

	public static void addProjectName(Application application, String projectName) {
		if (projectName != null) {
			getProjectNames(application).add(projectName);
		}
	}
	
	public static void addProjectNames(Application application, Collection<String> projectNames) {
		Iterator<String> iterator = projectNames.iterator();
		while (iterator.hasNext()) {
			String projectName = iterator.next();
			addProjectName(application, projectName);
		}
	}
	
	public static List<String> getProjectNames(Application application) {
		ProjectNames projectNames = application.getProjectNames();
		if (projectNames == null) {
			projectNames = new ProjectNames();
			application.setProjectNames(projectNames);
		}
		return projectNames.getProjectNames();
	}

	public static <T> T getObjectById(Applications applications, Class<?> objectClass, String name) {
		List<Object> objectList = getObjectList(applications, objectClass);
		return ListUtil.getObjectByValue(objectList, objectClass, "getId", name);
	}
	
	public static <T> T getObjectByName(Applications applications, Class<?> objectClass, String name) {
		List<Object> objectList = getObjectList(applications, objectClass);
		return ListUtil.getObjectByValue(objectList, objectClass, "getName", name);
	}
	
	public static <T> List<T> getObjectList(Applications applications, Class<?> objectClass) {
		List<Serializable> objects = applications.getImportsAndApplications();
		return ListUtil.getObjectList(objects, objectClass);
	}
	
	public static <T> T getObject(Applications applications, Class<?> objectClass) {
		List<Serializable> objects = applications.getImportsAndApplications();
		return ListUtil.getObject(objects, objectClass);
	}
	
	public static void addDependencies(Application application, List<Dependency> dependencies) {
		getDependencies(application).addAll(dependencies);
	}
	
	public static List<Dependency> getDependencies(Application application) {
		Dependencies dependencies = application.getDependencies();
		if (dependencies == null) {
			dependencies = new Dependencies();
			application.setDependencies(dependencies);
		}
		return dependencies.getDependencies();
	}

	public static List<Namespace> getNamespaces(Applications applications) {
		List<Namespace> namespaces = new ArrayList<Namespace>();
		Collection<Application> list = getApplications(applications);
		Iterator<Application> iterator = list.iterator();
		while (iterator.hasNext()) {
			Application application = (Application) iterator.next();
			namespaces.addAll(getNamespaces(application));
		}
		return namespaces;
	}
	
	public static Collection<Namespace> getNamespaces(Collection<Application> applications) {
		Collection<Namespace> namespaces = new ArrayList<Namespace>();
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = (Application) iterator.next();
			namespaces.addAll(getNamespaces(application));
		}
		return namespaces;
	}

	public static List<Namespace> getNamespaces(Application application) {
		List<Namespace> namespaces = new ArrayList<Namespace>(); 
		Information information = application.getInformation();
		if (information != null) {
			Collection<Namespace> localNamespaces = InformationUtil.getNamespaces(information);
			Iterator<Namespace> iterator = localNamespaces.iterator();
			while (iterator.hasNext()) {
				Namespace namespace = iterator.next();
				NamespaceUtil.addNamespace(namespaces, namespace);
				NamespaceUtil.addImportedNamespaces(namespaces, namespace);
			}
		}
		return namespaces;
	}

	public static Collection<Element> getElements(Collection<Application> applications) {
		Collection<Element> elements = new ArrayList<Element>();
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			elements.addAll(getElements(application));
		}
		return elements;
	}
	
	public static Collection<Element> getElements(Application application) {
		Collection<Namespace> namespaces = getNamespaces(application);
		Collection<Element> elements = NamespaceUtil.getElements(namespaces);
		return elements;
	}

	public static Collection<Component> getComponents(Collection<Application> applications) {
		return getComponents(applications, null);
	}
	
	public static Collection<Component> getComponents(Collection<Application> applications, String componentType) {
		Collection<Component> components = new ArrayList<Component>();
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = (Application) iterator.next();
			components.addAll(getComponents(application, componentType));
		}
		return components;
	}

	public static Collection<Component> getComponents(Application application) {
		return getComponents(application, null);
	}
	
	public static Collection<Component> getComponents(Application application, String componentType) {
		Set<Component> components = new HashSet<Component>();

		if (componentType != null) {
			if (componentType.equals("cache")) {
				Collection<Module> serviceModules = getServiceModules(application);
				Iterator<Module> iterator = serviceModules.iterator();
				while (iterator.hasNext()) {
					Module module = iterator.next();
					module.getComponents();
				}
			}
		}
		
		Information information = application.getInformation();
		if (information != null) {
			Collection<Namespace> localNamespaces = InformationUtil.getNamespaces(information);
			Iterator<Namespace> iterator = localNamespaces.iterator();
//			while (iterator.hasNext()) {
//				Namespace namespace = iterator.next();
//			}
		}
			
		if (componentType == null) {
			Collection<Module> serviceModules = getServiceModules(application);
			Iterator<Module> iterator = serviceModules.iterator();
			while (iterator.hasNext()) {
				Module module = iterator.next();
				//ComponentUtil.getComponents();
				Components componentParent = module.getComponents();
				if (componentParent != null) {
					components.addAll(componentParent.getComponents());
				}
				Collection<Cache> cacheComponents = ModuleUtil.getCaches(module);
				//TODO components.addAll(cacheComponents);
			}
			
			Collection<Service> services = getServices(application);
			Iterator<Service> iterator2 = services.iterator();
			while (iterator2.hasNext()) {
				Service service = iterator2.next();
				Process process = service.getProcess();
				if (process != null) {
					components.add(process);
				}
			}
			
		}
		
		return components;
	}
	
	public static void addChannel(Application application, Channel channel) {
		if (channel != null) {
			getChannels(application).add(channel);
		}
	}
	
	public static void addChannels(Application application, Collection<Channel> channels) {
		Iterator<Channel> iterator = channels.iterator();
		while (iterator.hasNext()) {
			Channel channel = iterator.next();
			addChannel(application, channel);
		}
	}
	
	public static List<Channel> getChannels(Application application) {
		Channels channels = application.getChannels();
		if (channels == null) {
			channels = new Channels();
			application.setChannels(channels);
		}
		return channels.getChannels();
	}

	public static Collection<Module> getModules(Collection<Application> applicationList) {
		Collection<Module> modules = new HashSet<Module>();
		Iterator<Application> iterator = applicationList.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			modules.addAll(getModules(application));
		}
		return modules;
	}

	public static Collection<Module> getModules(Application application) {
		Modules modules = application.getModules();
		if (modules == null) {
			modules = new Modules();
			application.setModules(modules);
		}
		return ModuleUtil.getModules(modules);
	}

	public static void addModule(Application application, Module module) {
		if (module != null) {
			ModuleUtil.addModule(application.getModules(), module);
		}
	}

	public static void addModules(Application application, Modules modules) {
		addModules(application, ModuleUtil.getModules(modules));
	}
	
	public static void addModules(Application application, Collection<Module> modules) {
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			addModule(application, module);
		}
	}

	public static boolean removeModule(Application application, Module module) {
		return ModuleUtil.removeModule(application.getModules(), module);
	}

	public static Callback getIncomingCallbackByName(Application application, String callbackName) {
		return getCallbackByName(application, callbackName, Direction.IN);
	}
	
	public static Callback getOutgoingCallbackByName(Application application, String callbackName) {
		return getCallbackByName(application, callbackName, Direction.OUT);
	}
	
	public static Callback getCallbackByName(Application application, String callbackName, Direction direction) {
		Collection<Callback> callbacks = getCallbacks(application, direction);
		Iterator<Callback> iterator = callbacks.iterator();
		while (iterator.hasNext()) {
			Callback callback = iterator.next();
			if (callback.getName().equalsIgnoreCase(callbackName)) {
				return callback;
			}
		}
		return null;
	}

	public static Collection<Callback> getIncomingCallbacks(Application application, String callbackName) {
		Collection<Callback> callbacks = getCallbacks(application, Direction.IN);
		return callbacks;
	}
	
	public static Collection<Callback> getOutgoingCallbacks(Application application, String callbackName) {
		Collection<Callback> callbacks = getCallbacks(application, Direction.OUT);
		return callbacks;
	}
	

	public static Service getServiceByName(Application application, String serviceName) {
		if (application == null)
			return null;
		if (serviceName == null)
			return null;
//		if (serviceName.equalsIgnoreCase("orderAccepted"))
//			System.out.println();
		Collection<Service> services = getServices(application);
		Iterator<Service> iterator = services.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			if (service.getName().equalsIgnoreCase(serviceName)) {
				return service;
			}
		}
		return null;
	}
	
	public static Collection<Service> getServices(Collection<Application> applicationList) {
		Set<Service> services = new HashSet<Service>();
		Iterator<Application> iterator = applicationList.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			services.addAll(getServices(application));
		}
		return services;
	}
	
	public static Collection<Service> getServices(Application application) {
		Map<String, Service> map = new HashMap<String, Service>();
		Services services = application.getServices();
		if (services == null) {
			services = new Services();
			application.setServices(services);
		}
		List<Service> serviceList = ServicesUtil.getServices(services);
		Iterator<Service> iterator = serviceList.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			String serviceId = service.getDomain() + "." + service.getName();
			map.put(serviceId, service);
		}
		Collection<Module> serviceModules = getServiceModules(application);
		serviceList.addAll(ModuleUtil.getServices(serviceModules));
		iterator = serviceList.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			String serviceId = service.getDomain() + "." + service.getName();
//TODO
//			if (!map.containsKey(serviceId))
//				services.getServices().add(service);
		}
		return serviceList;
	}

	public static Collection<Service> getServicesSorted(Application application) {
		List<Service> services = new ArrayList<>(getServices(application));
		Collections.sort(services, new Comparator<Service>() {
			public int compare(Service service1, Service service2) {
				return service1.getName().compareTo(service2.getName());
			}
		});
		return services;
	}
	
	public static Collection<Service> getRemoteServices(List<Project> projects, Application application) {
		Collection<Service> remoteServices = new HashSet<Service>();
		Collection<Service> services = ApplicationUtil.getServices(application);
		Iterator<Service> iterator = services.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			remoteServices.addAll(ServiceUtil.getRemoteServices(projects, service));
		}
		return remoteServices;
	}
	
	public static void addService(Application application, Service service) {
		ServicesUtil.addService(application.getServices(), service);
	}
	
	public static boolean removeService(Application application, Service service) {
		return ServicesUtil.removeService(application.getServices(), service);
	}
	
	public static Collection<Callback> getCallbacks(Application application, Direction direction) {
		Map<String, Callback> map = new HashMap<String, Callback>();
		Services services = application.getServices();
		if (services == null) {
			services = new Services();
			application.setServices(services);
		}
		List<Callback> list = ServicesUtil.getCallbacks(services);
		Iterator<Callback> iterator = list.iterator();
		while (iterator.hasNext()) {
			Callback callback = iterator.next();
			if (callback.getDirection() == direction) {
				String callbackId = callback.getDomain() + "." + callback.getName();
				map.put(callbackId, callback);
			}
		}
		Collection<Module> serviceModules = getServiceModules(application);
		list = ModuleUtil.getCallbacks(serviceModules);
		iterator = list.iterator();
		while (iterator.hasNext()) {
			Callback callback = iterator.next();
			if (callback.getDirection() == direction) {
				String callbackId = callback.getDomain() + "." + callback.getName();
				map.put(callbackId, callback);
			}
//TODO
//			if (!map.containsKey(serviceId))
//				services.getServices().add(service);
		}
		
		List<Callback> callbacks = new ArrayList<Callback>();
		callbacks.addAll(map.values());
		return callbacks;
	}
	
	public static List<Property> getProperties(Application application) {
		Properties properties = application.getProperties();
		if (properties == null) {
			properties = new Properties();
			application.setProperties(properties);
		}
		return properties.getProperties();
	}

	
	public static Module getEarModule(Application application) {
		Collection<Module> modules = getModules(application, ModuleType.EAR);
		Assert.isTrue(modules.size() <= 1, "Only one EAR allowed per application: "+application.getName());
		if (modules.size() == 1)
			return modules.iterator().next();
		return null;
	}

	public static Collection<Module> getPomModules(Application application) {
		return getModules(application, ModuleType.POM);
	}

	public static Collection<Module> getModelModules(Application application) {
		return getModules(application, ModuleType.MODEL);
	}

	public static Collection<Module> getClientModules(Application application) {
		return getModules(application, ModuleType.CLIENT);
	}

	public static Collection<Module> getServiceModules(Application application) {
		return getModules(application, ModuleType.SERVICE);
	}

	public static Map<String, Module> getServiceModulesAsMap(Application application) {
		return getModulesAsMap(application, ModuleType.SERVICE);
	}

	public static Collection<Module> getDataModules(Application application) {
		return getModules(application, ModuleType.DATA);
	}

	public static Collection<Module> getViewModules(Application application) {
		return getModules(application, ModuleType.VIEW);
	}

	public static Set<Module> getModules(Project project, ModuleType moduleType) {
		Set<Module> list = new HashSet<Module>();
		Collection<Application> applications = ProjectUtil.getApplications(project);
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = (Application) iterator.next();
			list.addAll(getModules(application, moduleType));
		}
		return list;
	}
	
	public static Collection<Module> getModules(Application application, ModuleType moduleType) {
		Collection<Module> list = new HashSet<Module>();
		Collection<Module> modules = getModules(application);
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
//			if (moduleType == ModuleType.CLIENT)
//				System.out.println();
			if (module.getType() == moduleType) {
				list.add(module);
			}
		}
		return list;
	}
	
	public static Map<String, Module> getModulesAsMap(Project project, ModuleType moduleType) {
		Map<String, Module> map = new HashMap<String, Module>();
		Collection<Application> applications = ProjectUtil.getApplications(project);
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = (Application) iterator.next();
			map.putAll(getModulesAsMap(application, moduleType));
		}
		return map;
	}

	public static Map<String, Module> getModulesAsMap(Application application, ModuleType moduleType) {
		Map<String, Module> map = new HashMap<String, Module>();
		Collection<Module> modules = getModules(application);
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			if (module.getType() == moduleType) {
				map.put(module.getName(), module);
			}
		}
		return map;
	}

	public static Map<String, Module> getModulesAsMap(Project project) {
		Map<String, Module> map = new HashMap<String, Module>();
		Collection<Application> applications = ProjectUtil.getApplications(project);
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			map.putAll(getModulesAsMap(application));
		}
		return map;
	}
	
	public static Map<String, Module> getModulesAsMap(Application application) {
		Map<String, Module> map = new HashMap<String, Module>();
		Collection<Module> modules = getModules(application);
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			map.put(module.getName(), module);
		}
		return map;
	}

	
	public static List<Process> getProcesses(Application application) {
		Processes processes = application.getProcesses();
		if (processes != null) {
			List<Process> list = processes.getProcesses();
			return list;
		}
		return null;
	}
	
	public static void addProcess(Application application, Process process) {
		Processes processes = application.getProcesses();
		if (processes == null) {
			processes = new Processes();
			application.setProcesses(processes);
		}
		List<Process> list = processes.getProcesses();
		list.add(process);
	}

	public static Information getInformation(Application application) {
		return application.getInformation();
	}

	
	
	public static Collection<UserInterface> getUserInterfaces(Application application) {
		UserInterfaces userInterfaces = application.getUserInterfaces();
		return userInterfaces.getUserInterfaces();
	}

	public static void addUserInterface(Application application, UserInterface userInterface) {
		Collection<UserInterface> list = getUserInterfaces(application);
		synchronized (list) {
			if (!list.contains(userInterface))
				list.add(userInterface);
		}
	}

	public static boolean removeUserInterface(Application application, UserInterface userInterface) {
		Collection<UserInterface> list = getUserInterfaces(application);
		synchronized (list) {
			if (!list.contains(userInterface))
				return list.remove(userInterface);
		}
		return false;
	}

	public static Collection<Provider> getProviders(Collection<Project> projectList, Collection<Application> applications) {
		Set<Provider> providers = new HashSet<Provider>();
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			Collection<Provider> list = getProviders(projectList, application);
			//TODO prevent duplicates
			providers.addAll(list);
		}
		return providers;
	}
	
	public static Collection<Provider> getProviders(Collection<Project> projectList, Application application) {
		Project project = getProject(projectList, application);
		Assert.notNull(project, "Project not found for application: "+application.getName());
		Collection<Provider> providerList = new ArrayList<Provider>();
		providerList.addAll(MessagingUtil.getProviders(ProjectUtil.getMessagingBlocks(project)));
		providerList.addAll(PersistenceUtil.getProviders(ProjectUtil.getPersistenceBlocks(project)));
		return providerList;
	}

	public static Project getProject(Collection<Project> projectList, Application application) {
		Iterator<Project> iterator = projectList.iterator();
		while (iterator.hasNext()) {
			Project project = iterator.next();
			Collection<Application> applicationList = ProjectUtil.getApplications(projectList);
			if (applicationList.contains(application)) {
				return project;
			}
		}
		return null;
	}
	
}
