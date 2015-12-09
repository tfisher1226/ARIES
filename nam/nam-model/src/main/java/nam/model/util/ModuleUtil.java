package nam.model.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import nam.model.Application;
import nam.model.Cache;
import nam.model.Callback;
import nam.model.Component;
import nam.model.Domain;
import nam.model.Element;
import nam.model.Import;
import nam.model.Information;
import nam.model.Interactor;
import nam.model.Interactors;
import nam.model.Link;
import nam.model.Module;
import nam.model.ModuleType;
import nam.model.Modules;
import nam.model.Namespace;
import nam.model.Persistence;
import nam.model.Process;
import nam.model.Processes;
import nam.model.Project;
import nam.model.Provider;
import nam.model.Repository;
import nam.model.Resource;
import nam.model.Resources;
import nam.model.Service;
import nam.model.Services;
import nam.model.Unit;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.Assert;
import org.aries.util.BaseUtil;
import org.aries.util.NameUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class ModuleUtil extends BaseUtil {

	public static Object getKey(Module module) {
		return module.getGroupId() + ":" + module.getArtifactId();
	}
	
	public static String getLabel(Module module) {
		return module.getName();
	}
	
	public static boolean isEmpty(Module module) {
		if (module == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Module> moduleList) {
		if (moduleList == null  || moduleList.size() == 0)
			return true;
		Iterator<Module> iterator = moduleList.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			if (!isEmpty(module))
				return false;
		}
		return true;
	}
	
	public static String toString(Module module) {
		if (isEmpty(module))
			return "Module: [uninitialized] "+module.toString();
		String text = module.toString();
		return text;
	}
	
	public static String toString(Collection<Module> moduleList) {
		if (isEmpty(moduleList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Module> iterator = moduleList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Module module = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(module);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Module create() {
		Module module = new Module();
		initialize(module);
		return module;
	}
	
	public static void initialize(Module module) {
		//nothing for now
	}
	
	public static boolean validate(Module module) {
		if (module == null)
			return false;
		Validator validator = Validator.getValidator();
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Module> moduleList) {
		Validator validator = Validator.getValidator();
		Iterator<Module> iterator = moduleList.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			//TODO break or accumulate?
			validate(module);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Modules modules) {
		boolean isValid = validate(getModules(modules));
		return isValid;
	}

	public static void sortRecords(List<Module> moduleList) {
		Collections.sort(moduleList, createModuleComparator());
	}
	
	public static Collection<Module> sortRecords(Collection<Module> moduleCollection) {
		List<Module> list = new ArrayList<Module>(moduleCollection);
		Collections.sort(list, createModuleComparator());
		return list;
	}
	
	public static Comparator<Module> createModuleComparator() {
		return new Comparator<Module>() {
			public int compare(Module module1, Module module2) {
				String name1 = module1.getName();
				String name2 = module2.getName();
				int status = name1.compareTo(name2);
				return status;
			}
		};
	}
	
	public static Module clone(Module module) {
		if (module == null)
			return null;
		Module clone = create();
		clone.setType(module.getType());
		clone.setLevel(module.getLevel());
		clone.setName(ObjectUtil.clone(module.getName()));
		clone.setLabel(ObjectUtil.clone(module.getLabel()));
		clone.setGroupId(ObjectUtil.clone(module.getGroupId()));
		clone.setArtifactId(ObjectUtil.clone(module.getArtifactId()));
		clone.setVersion(ObjectUtil.clone(module.getVersion()));
		clone.setNamespace(ObjectUtil.clone(module.getNamespace()));
		clone.setPackaging(module.getPackaging());
		clone.setDescription(ObjectUtil.clone(module.getDescription()));
		clone.setCreator(ObjectUtil.clone(module.getCreator()));
		clone.setCreationDate(ObjectUtil.clone(module.getCreationDate()));
		clone.setLastUpdate(ObjectUtil.clone(module.getLastUpdate()));
		return clone;
	}

	public static Collection<Module> clone(Collection<Module> moduleList) {
		if (moduleList == null)
			return null;
		List<Module> newList = new ArrayList<Module>();
		Iterator<Module> iterator = moduleList.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			Module clone = clone(module);
			newList.add(clone);
		}
		return newList;
	}

	public static Modules clone(Modules modules) {
		Collection<Module> list = clone(getModules(modules));
		Modules clone = new Modules();
		addModules(clone, list);
		return clone;
	}
	

	public static Set<Import> getImports(Modules modules) {
		return getObjectSet(modules, Import.class);
	}

	public static Set<Module> getModules(Modules modules) {
		return getObjectSet(modules, Module.class);
	}
	
	public static Set<Module> getModules(Modules modules, ModuleType moduleType) {
		Set<Module> modulesByType = new HashSet<Module>();
		Set<Module> moduleSet = getObjectSet(modules, Module.class);
		Iterator<Module> iterator = moduleSet.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			if (module.getType().equals(moduleType)) {
				modulesByType.add(module);
			}
		}
		return modulesByType;
	}

	public static void clearModules(Modules modules) {
		Set<Module> values = getModules(modules);
		Iterator<Module> iterator = values.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			modules.getImportsAndModules().remove(module);
		}
	}

	public static void addModules(Project project, Collection<Module> moduleList) {
		addModules(project.getModules(), moduleList);
	}
	
	public static void addModules(Modules modules, Collection<Module> moduleList) {
		Iterator<Module> iterator = moduleList.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			addModule(modules, module);
		}
	}
	
	public static void addModule(Modules modules, Module module) {
		if (module != null && !modules.getImportsAndModules().contains(module))
			modules.getImportsAndModules().add(module);
	}
	
	public static boolean removeModule(Modules modules, Module module) {
		return modules.getImportsAndModules().remove(module);
	}
	
	public static <T> List<T> getObjectList(Modules modules, Class<?> objectClass) {
		List<Serializable> objects = modules.getImportsAndModules();
		return ListUtil.getObjectList(objects, objectClass);
	}
	
	public static <T> Set<T> getObjectSet(Modules modules, Class<?> objectClass) {
		List<Serializable> objects = modules.getImportsAndModules();
		return ListUtil.getObjectSet(objects, objectClass);
	}
	
	public static <T> T getObject(Modules modules, Class<?> objectClass) {
		List<Serializable> objects = modules.getImportsAndModules();
		return ListUtil.getObject(objects, objectClass);
	}
	
	public static String getModuleId(Module module) {
		//String domain = module.getGroupId();
		String name = module.getArtifactId();
		if (name != null) {
			//domain = domain.replace("-",  ".");
			name = name.replace("-",  ".");
			//return domain + "." + name;
			return name;
		}
		if (module.getRef() != null)
			return module.getRef();
		return null;
	}
	
	
	public ModuleUtil() {
	}
	
	public static Module getClientModule(Modules modules) {
		return getClientModule(getModules(modules));
	}

	public static Module getClientModule(Collection<Module> modules) {
		return getModule(modules, ModuleType.CLIENT);
	}

	public static Set<Module> getClientModules(Project project) {
		return getAllModules(project, ModuleType.CLIENT);
	}

	public static Module getDataModule(Modules modules) {
		return getDataModule(getModules(modules));
	}

	public static Module getDataModule(Collection<Module> modules) {
		return getModule(modules, ModuleType.DATA);
	}

	public static Set<Module> getDataModules(Project project) {
		return getAllModules(project, ModuleType.DATA);
	}

	public static Module getModelModule(Modules modules) {
		return getModelModule(getModules(modules));
	}

	public static Module getModelModule(Collection<Module> modules) {
		return getModule(modules, ModuleType.MODEL);
	}

	public static Set<Module> getModelModules(Project project) {
		return getAllModules(project, ModuleType.MODEL);
	}

	public static Module getServiceModule(Modules modules) {
		return getServiceModule(getModules(modules));
	}

	public static Module getServiceModule(Collection<Module> modules) {
		return getModule(modules, ModuleType.SERVICE);
	}

	public static Set<Module> getServiceModules(Project project) {
		return getAllModules(project, ModuleType.SERVICE);
	}

	public static List<Module> getServiceModules(Collection<Project> projects) {
		return getAllModules(projects, ModuleType.SERVICE);
	}

	public static Module getViewModule(Modules modules) {
		return getViewModule(getModules(modules));
	}

	public static Module getViewModule(Collection<Module> modules) {
		return getModule(modules, ModuleType.VIEW);
	}

	public static Set<Module> getViewModules(Project project) {
		return getAllModules(project, ModuleType.VIEW);
	}

	public static Module getEARModule(Collection<Module> modules) {
		return getModule(modules, ModuleType.EAR);
	}

	public static Module getEARModule(Project project) {
		return getModule(getAllModules(project), ModuleType.EAR);
	}


	public static Module getModule(Project project, String groupId, String artifactId) {
		Set<Module> allModules = getAllModules(project);
		Iterator<Module> iterator = allModules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			if (module.getGroupId().equals(groupId) && module.getArtifactId().equals(artifactId)) {
				return module;
			}
		}
		return null;
	}
	
	public static Module getServiceModule(List<Project> projects, Service service) {
		List<Module> serviceModules = getAllModules(projects, ModuleType.SERVICE);
		Iterator<Module> iterator = serviceModules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			List<Service> services = getServices(module);
			if (ServiceUtil.containsService(services, service))
				return module;
		}
		return null;
	}

	public static List<Module> getAllModules(Collection<Project> projects, ModuleType moduleType) {
		List<Module> modules = new ArrayList<Module>();  
		Iterator<Project> iterator = projects.iterator();
		while (iterator.hasNext()) {
			Project project = iterator.next();
			modules.addAll(getAllModules(project, moduleType));
		}
		return modules;
	}

	public static Set<Module> getAllModules(Project project) {
		Set<Module> modules = new LinkedHashSet<Module>();  
		modules.addAll(ProjectUtil.getProjectModules(project));
		modules.addAll(ProjectUtil.getApplicationModules(project));
		List<Project> subProjects = ProjectUtil.getSubProjects(project);
		Iterator<Project> iterator = subProjects.iterator();
		while (iterator.hasNext()) {
			Project subProject = iterator.next();
			modules.addAll(getAllModules(subProject));
		}
		return modules;
	}

	public static Set<Module> getAllModules(Project project, ModuleType moduleType) {
		Set<Module> modules = new LinkedHashSet<Module>();  
		List<Project> subProjects = ProjectUtil.getSubProjects(project);
		Iterator<Project> iterator = subProjects.iterator();
		while (iterator.hasNext()) {
			Project subProject = iterator.next();
			modules.addAll(getAllModules(subProject, moduleType));
		}
		List<Module> localModules = new ArrayList<Module>();  
		localModules.addAll(ProjectUtil.getProjectModules(project, moduleType));
		localModules.addAll(ProjectUtil.getApplicationModules(project, moduleType));
		sortModules(localModules);
		modules.addAll(localModules);
		return modules;
	}
	
	public static void sortModules(List<Module> modules) {
		Collections.sort(modules, new Comparator<Module>() {
			public int compare(Module module1, Module module2) {
				return module1.getName().compareTo(module2.getName());
			}
		});
	}
	
	public static Module getModule(Collection<Module> modules, ModuleType moduleType) {
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			if (module.getType().equals(moduleType))
				return module;
		}
		return null;
	}

	public static List<Resource> getResources(Module module) {
		Resources resources = module.getResources();
		if (resources == null) {
			resources = new Resources();
			module.setResources(resources);
		}
		return resources.getResources();
	}

//	public static List<Domain> getDomains(Modules modules) {
//		return ServicesUtil.getDomains(getServices(modules));
//	}

	public static List<Domain> getDomains(Modules modules) {
		return getDomains(ModuleUtil.getModules(modules));
	}

	public static List<Domain> getDomains(Collection<Module> modules) {
		List<Domain> list = new ArrayList<Domain>();
		Set<Domain> set = new LinkedHashSet<Domain>();
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			List<Domain> domains = getDomains(module);
			if (domains != null)
				set.addAll(domains);
		}
		list.addAll(set);
		return list;
	}

	public static List<Domain> getDomains(Module module) {
		List<Domain> list = new ArrayList<Domain>();
		Services services = module.getServices();
		if (services != null) {
			List<Domain> domains = ServicesUtil.getDomains(services);
			list.addAll(domains);
		}
		return list;
	}

	public static void addDomain(Module module, Domain domain) {
		ServicesUtil.addDomain(module.getServices(), domain);
	}
	
	public static boolean removeDomain(Module module, Domain domain) {
		return ServicesUtil.removeDomain(module.getServices(), domain);
	}
	
	
	public static void addServices(Module module, List<Service> services) {
		ServicesUtil.addServices(module.getServices(), services);
	}

	public static List<Service> getServices(Modules modules) {
		return getServices(ModuleUtil.getModules(modules));
	}
	
	public static List<Service> getServices(Collection<Module> modules) {
		Set<Service> serviceSet = new LinkedHashSet<Service>();
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			List<Service> services = getServices(module);
			serviceSet.addAll(services);
		}
		List<Service> services = new ArrayList<Service>();
		services.addAll(serviceSet);
		return services;
	}

	public static List<Service> getServices(Module module) {
		return ServicesUtil.getServices(module.getServices());
	}
	
	public static void addService(Module module, Service service) {
		ServicesUtil.addService(module.getServices(), service);
	}
	
	public static boolean removeService(Module module, Service service) {
		return ServicesUtil.removeService(module.getServices(), service);
	}

	public static List<Callback> getCallbacks(Collection<Module> modules) {
		Set<Callback> callbackSet = new LinkedHashSet<Callback>();
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			callbackSet.addAll(getCallbacks(module));
		}
		List<Callback> callbacks = new ArrayList<Callback>();
		callbacks.addAll(callbackSet);
		return callbacks;
	}
	
	public static List<Callback> getCallbacks(Module module) {
		List<Callback> serviceList = new ArrayList<Callback>();
		Services services = module.getServices();
		if (services != null)
			serviceList.addAll(ServicesUtil.getCallbacks(services));
		return serviceList;
		
//		List<Service> serviceList = new ArrayList<Service>();
//		List<Services> servicesList = module.getServices();
//		if (servicesList.size() == 0)
//			return serviceList;
//		Iterator<Services> iterator = servicesList.iterator();
//		while (iterator.hasNext()) {
//			Services services = iterator.next();
//			if (services == null) {
//				services = new Services();
//				module.setServices(services);
//			}
//		}
//		return services.getServices();

	}

	public static List<Callback> getOutgoingCallbacks(Module module) {
		List<Service> services = getServices(module);
		return ServiceUtil.getOutgoingCallbacks(services);
	}
	
	public static List<Callback> getIncomingCallbacks(Module module) {
		List<Service> services = getServices(module);
		return ServiceUtil.getIncomingCallbacks(services);
	}
	
	public static void addProcess(Module module, Process process) {
		Processes processes = module.getProcesses();
		if (processes == null) {
			processes = new Processes();
			module.setProcesses(processes);
		}
		processes.getProcesses().add(process);
	}
	
	//TODO make this more reliable - merge the groups into one
	public static List<Process> getProcesses(Module module) {
		List<Process> list = new ArrayList<Process>();
		Set<String> set = new LinkedHashSet<String>();
		
		Processes processes = module.getProcesses();
		if (processes != null)
			list.addAll(processes.getProcesses());
		Iterator<Process> processIterator = list.iterator();
		while (processIterator.hasNext()) {
			Process process = processIterator.next();
			set.add(process.getName());
		}
		
		List<Service> services = getServices(module);
		Iterator<Service> iterator = services.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			Process process = service.getProcess();
			if (process != null && process.getName() != null && !set.contains(process.getName())) {
				list.add(process);
				set.add(process.getName());
			}
		}
		return list;
	}
	
	public static List<Information> getInformationBlocks(Collection<Module> modules) {
		List<Information> list = new ArrayList<Information>();
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			Information information = getInformationBlock(module);
			if (information != null) {
				list.add(information);
			}
		}
		return list;
	}

	public static Information getInformationBlock(Module module) {
		Information information = module.getInformation();
//		if (information == null) {
//			information = new Information();
//			module.setInformation(information);
//		}
		return information;
	}

	public static List<Persistence> getPersistenceBlocks(Collection<Module> modules) {
		List<Persistence> list = new ArrayList<Persistence>();
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			Persistence persistence = getPersistenceBlock(module);
			if (persistence != null) {
				list.add(persistence);
			}
		}
		return list;
	}
	
	public static Persistence getPersistenceBlock(Module module) {
		Persistence persistence = module.getPersistence();
//		if (persistence == null) {
//			persistence = new Persistence();
//			module.setPersistence(persistence);
//		}
		return persistence;
	}

	public static List<Repository> getRepositories(Module module) {
		Persistence persistence = getPersistenceBlock(module);
		return PersistenceUtil.getRepositories(persistence);
	}

	public static Unit getUnit(Module module, String name) {
		Persistence persistence = getPersistenceBlock(module);
		Iterator<Unit> iterator = PersistenceUtil.getUnits(persistence).iterator();
		while (iterator.hasNext()) {
			Unit unit = iterator.next();
			if (unit.getName().equals(name))
				return unit;
		}
		return null;
	}

	public static Collection<Unit> getUnits(Module module) {
		Persistence persistence = getPersistenceBlock(module);
		return PersistenceUtil.getUnits(persistence);
	}
	
	public static Cache getCache(Module module, String name) {
		Persistence persistence = getPersistenceBlock(module);
		List<Cache> caches = PersistenceUtil.getCaches(persistence);
		Iterator<Cache> iterator = caches.iterator();
		while (iterator.hasNext()) {
			Cache cache = iterator.next();
			if (cache.getName().equals(name))
				return cache;
		}
		return null;
	}

	public static List<Cache> getCaches(Module module) {
		List<Cache> cacheUnits = new ArrayList<Cache>();
		Persistence persistence = getPersistenceBlock(module);
		cacheUnits.addAll(PersistenceUtil.getCaches(persistence));
		
		Processes processes = module.getProcesses();
		if (processes != null)
			cacheUnits.addAll(ProcessUtil.getCacheUnits(processes));
		return cacheUnits;
	}
	
	
	public static List<Namespace> getNamespaces(Modules modules) {
		List<Namespace> namespaces = new ArrayList<Namespace>();
		Collection<Module> list = getModules(modules);
		Iterator<Module> iterator = list.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			namespaces.addAll(getNamespaces(module));
		}
		return namespaces;
	}

	public static List<Namespace> getNamespaces(Module module) {
		List<Namespace> namespaces = new ArrayList<Namespace>(); 
		Information information = module.getInformation();
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
	
	public static List<Element> getElements(Module module) {
		List<Namespace> namespaces = getNamespaces(module);
		List<Element> elements = NamespaceUtil.getElements(namespaces);
		return elements;
	}
	
	public static Collection<Element> getElements(Collection<Module> modules) {
		Collection<Element> elements = new ArrayList<Element>();
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			elements.addAll(getElements(module));
		}
		return elements;
	}
	
	public static Collection<Component> getComponents(Collection<Module> modules) {
		return getComponents(modules, null);
	}
	
	public static Collection<Component> getComponents(Collection<Module> modules, String componentType) {
		Collection<Component> components = new ArrayList<Component>();
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = (Module) iterator.next();
			components.addAll(getComponents(module, componentType));
		}
		return components;
	}

	public static Collection<Component> getComponents(Module application) {
		return getComponents(application, null);
	}
	
	public static Collection<Component> getComponents(Module module, String componentType) {
		List<Component> components = new ArrayList<Component>();

		if (componentType != null) {
			if (componentType.equals("cache") && module.getType() == ModuleType.SERVICE) {
				module.getComponents();
			}
		}
		
		Information information = module.getInformation();
		if (information != null) {
			Collection<Namespace> localNamespaces = InformationUtil.getNamespaces(information);
			Iterator<Namespace> iterator = localNamespaces.iterator();
//			while (iterator.hasNext()) {
//				Namespace namespace = iterator.next();
//			}
		}
			
		return components;
	}
	
	public static List<Link> getSendLinks(Module module) {
		List<Link> links = new ArrayList<Link>();
		Interactors interactors = module.getInteractors();
		if (interactors != null)
			links.addAll(LinkUtil.createSendLinksFromInteractors(interactors));
		return links;
	}

	public static List<Link> getReceiveLinks(Module module) {
		List<Link> links = new ArrayList<Link>();
		Interactors interactors = module.getInteractors();
		if (interactors != null)
			links.addAll(LinkUtil.createReceiveLinksFromInteractors(interactors));
		return links;
	}
	
//	public static List<Link> getLinks(Module module) {
//		Links links = module.getLinks();
//		if (links == null) {
//			links = new Links();
//			module.setLinks(links);
//		}
//		return links.getLinks();
//	}

	public static Module getUnifiedModuleToGenerate(Project project) {
		Set<Module> modules = ProjectUtil.getServiceModules(project);
		Assert.isTrue(modules.size() > 0, "Service module nnot found");
		//Assert.isTrue(modules.size() == 1, "Supporting only 1 Unified-module per Application for now");
		//TODO just take the first one for now
		Module module = modules.iterator().next();
		return module;
	}

//	public static Module getModelModuleToGenerate(Project project) {
//		List<Module> modules = ProjectUtil.getModelModules(project);
//		Assert.isTrue(modules.size() <= 1, "Supporting only 1 Model-module per Application for now");
//		if (modules.size() == 0)
//			modules = ProjectUtil.getServiceModules(project);
//		Module module = modules.get(0);
//		//Assert.isTrue(modules.size() <= 1, "Expecting 1 module at this point");
//		//Assert.isTrue(module.getType() == ModuleType.ALL, "Single module must have correct type");
//		return module;
//	}
	
	public static Set<Module> getClientModulesToGenerate(Project project) {
		Set<Module> modules = new LinkedHashSet<Module>();
		modules.addAll(ProjectUtil.getClientModules(project));
		//Assert.isTrue(modules.size() <= 1, "Supporting only 1 Client-module per Application for now");
		if (modules.size() == 0)
			modules = ProjectUtil.getServiceModules(project);
		//Assert.isTrue(modules.size() <= 1, "Expecting 1 module at this point");
		return modules;
	}
	
	public static Set<Module> getServiceModulesToGenerate(Project project) {
		Set<Module> modules = new LinkedHashSet<Module>();
		modules.addAll(ProjectUtil.getServiceModules(project));
		if (modules.size() == 0)
			modules.addAll(ProjectUtil.getApplicationModules(project));
		//Assert.isTrue(modules.size() <= 1, "Expecting 1 module at this point");
		return modules;
	}

	public static void addInteractors(Module module, List<Interactor> interactors) {
		Interactors parent = module.getInteractors();
		if (parent == null) {
			parent = new Interactors();
			module.setInteractors(parent);
		}
		parent.getInteractors().addAll(interactors);
	}

	public static String getFileNamePrefix(Module module) {
		String prefix = NameUtil.splitStringUsingDashes(module.getName());
		return prefix.toLowerCase();
	}

	public static String getFileNameExtension(Module module) {
		switch (module.getType()) {
		case MODEL:
		case CLIENT:
		case SERVICE:
		case DATA:
			return "jar";
		case EAR:
			return "ear";
		case WAR:
		case VIEW:
			return "war";
		case POM:
		case TEST:
		default:
			return null;
		}
	}

	
	public static Collection<Provider> getProviders(Collection<Project> projectList, Collection<Module> modules) {
		List<Provider> providers = new ArrayList<Provider>();
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			Collection<Provider> list = getProviders(projectList, module);
			//TODO prevent duplicates
			providers.addAll(list);
		}
		return providers;
	}
	
	public static Collection<Provider> getProviders(Collection<Project> projectList, Module module) {
		Project project = getProject(projectList, module);
		Assert.notNull(project, "Project not found for module: "+module.getName());
		Collection<Provider> providerList = new ArrayList<Provider>();
		providerList.addAll(MessagingUtil.getProviders(ProjectUtil.getMessagingBlocks(project)));
		providerList.addAll(PersistenceUtil.getProviders(ProjectUtil.getPersistenceBlocks(project)));
		return providerList;
	}

	public static Project getProject(Collection<Project> projectList, Module module) {
		Iterator<Project> iterator = projectList.iterator();
		while (iterator.hasNext()) {
			Project project = iterator.next();
			Collection<Module> moduleList = ProjectUtil.getModules(projectList);
			if (moduleList.contains(module)) {
				return project;
			}
		}
		return null;
	}
	
}
