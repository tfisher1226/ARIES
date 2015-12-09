package aries.generation;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nam.client.ClientLayerHelper;
import nam.data.DataLayerHelper;
import nam.model.Adapter;
import nam.model.Application;
import nam.model.Applications;
import nam.model.Attribute;
import nam.model.Cache;
import nam.model.Callback;
import nam.model.Channel;
import nam.model.Channels;
import nam.model.Condition;
import nam.model.ContainedBy;
import nam.model.Criteria;
import nam.model.Domain;
import nam.model.EjbTransport;
import nam.model.Element;
import nam.model.Elements;
import nam.model.Enumeration;
import nam.model.Field;
import nam.model.Grouping;
import nam.model.HttpTransport;
import nam.model.Id;
import nam.model.Import;
import nam.model.In;
import nam.model.Include;
import nam.model.Information;
import nam.model.Interactor;
import nam.model.Invoker;
import nam.model.Item;
import nam.model.JmsTransport;
import nam.model.Like;
import nam.model.Link;
import nam.model.ListItem;
import nam.model.Listener;
import nam.model.Literal;
import nam.model.MapItem;
import nam.model.Messaging;
import nam.model.ModelLayerHelper;
import nam.model.Module;
import nam.model.ModuleLevel;
import nam.model.ModuleType;
import nam.model.Modules;
import nam.model.Namespace;
import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Persistence;
import nam.model.Process;
import nam.model.ProcessState;
import nam.model.Processes;
import nam.model.Project;
import nam.model.Properties;
import nam.model.Property;
import nam.model.Query;
import nam.model.Receiver;
import nam.model.Reference;
import nam.model.ReferencedBy;
import nam.model.Result;
import nam.model.RmiTransport;
import nam.model.Secret;
import nam.model.Sender;
import nam.model.Service;
import nam.model.Services;
import nam.model.SetItem;
import nam.model.Source;
import nam.model.Transacted;
import nam.model.TransactionScope;
import nam.model.TransactionUsage;
import nam.model.Transport;
import nam.model.TransportType;
import nam.model.Type;
import nam.model.Unit;
import nam.model.util.ApplicationUtil;
import nam.model.util.ChannelUtil;
import nam.model.util.DomainUtil;
import nam.model.util.ElementUtil;
import nam.model.util.ExtensionsUtil;
import nam.model.util.FieldUtil;
import nam.model.util.InformationUtil;
import nam.model.util.LinkUtil;
import nam.model.util.ListenerUtil;
import nam.model.util.MessagingUtil;
import nam.model.util.ModuleFactory;
import nam.model.util.ModuleUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.OperationUtil;
import nam.model.util.PersistenceUtil;
import nam.model.util.ProjectUtil;
import nam.model.util.PropertyUtil;
import nam.model.util.QueryUtil;
import nam.model.util.ServiceUtil;
import nam.model.util.ServicesUtil;
import nam.model.util.TypeUtil;
import nam.model.util.UnitUtil;
import nam.model.util.ViewUtil;
import nam.service.ServiceLayerHelper;
import nam.ui.View;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.util.ClassUtil;
import org.aries.util.FileUtil;
import org.aries.util.NameUtil;
import org.aries.util.ObjectUtil;
import org.eclipse.emf.codegen.util.CodeGenUtil;

import aries.bpel.BPELRuntimeCache;
import aries.generation.engine.GenerationContext;
import aries.generation.engine.GenerationEngine;


public class AriesModelHelper {

	private static Log log = LogFactory.getLog(AriesModelHelper.class);
	
	protected GenerationContext context;

	protected GenerationEngine engine;
	
	protected AriesModelFactory factory;

	protected AriesModelReader reader;

	protected AriesModelBuilder builder;

	private Map<String, Enumeration> enumCache;

	private boolean enforceChecks = false;
	
	
	public AriesModelHelper(GenerationContext context) {
		enumCache = new HashMap<String, Enumeration>();		
		this.context = context;
	}

	public GenerationContext getContext() {
		return context;
	}

	public void setContext(GenerationContext context) {
		this.context = context;
	}

	public void setEngine(GenerationEngine engine) {
		this.engine = engine;
	}

	public void setFactory(AriesModelFactory factory) {
		this.factory = factory;
	}

	public void setReader(AriesModelReader reader) {
		this.reader = reader;
	}

	public void setBuilder(AriesModelBuilder builder) {
		this.builder = builder;
	}

	public void assureProject(Project project) throws Exception {
		assureProjectImports(project);
		if (project.getDomain() == null)
			project.setDomain(context.getProjectDomain());
		//assureProjectNamespace(project, String namespaceRef);
		assureExtensions(project);
		assureInformationBlocks(project);
		assurePersistenceBlocks(project);
		assureMessagingBlocks(project);
	}

	public void assureProjectImports(Project project) throws Exception {
		assureImports(ProjectUtil.getImports(project));
	}
	
	public void assureImports(List<Import> imports) throws Exception {
		Iterator<Import> iterator = imports.iterator();
		while (iterator.hasNext()) {
			Import model = iterator.next();
			assureImport(model);
		}
	}
	
	public void assureImport(Import model) throws Exception {
		String fileName = model.getFile();
		String directory = context.getRuntimeFileContext();
		//String filePath = context.getRuntimeFilePath(directory, fileName);
		if (model.getDir() == null) {
			if (fileName.startsWith("/"))
				directory = FileUtil.normalizePath(context.getCacheLocation() + "/model");
			model.setDir(directory);
		}
	}
	
	public Namespace assureProjectNamespace(Project project) {
		return assureProjectNamespace(project, project.getNamespace());
	}
	
	public Namespace assureProjectNamespace(Project project, String namespaceRef) {
		if (namespaceRef == null)
			namespaceRef = project.getNamespace();
		if (namespaceRef == null)
			namespaceRef = "http://"+project.getName();
		Namespace namespace = getNamespace(namespaceRef);
		if (namespace == null) {
			namespaceRef = project.getName();
			namespace = getNamespace(namespaceRef);
		}
		if (namespace == null) {
			namespace = new Namespace();
			namespace.setUri(NameUtil.getNamespaceURIFromPackageName(project.getDomain()));
			namespace.setName(NameUtil.getDomainFromNamespace(namespace.getUri()));
			namespace.setPrefix(getProjectNamespaceName(project));
		}
		
		//assuming by namespace is NEVER null here
		Assert.notNull(namespace, "Namespace not found: "+namespaceRef);
		project.setNamespace(namespace.getUri());
		return namespace;
	}
	
	public Namespace getNamespace(String namespaceRef) {
		Namespace namespace = context.getNamespaceByUri(namespaceRef);
		if (namespace == null)
			namespace = context.getNamespaceByPrefix(namespaceRef);
		if (namespace == null)
			namespace = context.getNamespaceByName(namespaceRef);
		return namespace;
	}

	public String getProjectNamespaceName(Project project) {
		String name = project.getNamespace();
		if (name != null) {
			if (name.startsWith("http://"))
				name = name.substring(7);
			return name;
		}
		name = project.getName();
		return name;
	}
	
	public String getApplicationNamespaceUri(Project project, String applicationName) {
		String namespaceUri = "http://"+getProjectNamespaceName(project)+"/"+applicationName;
		return namespaceUri;
	}
	
	public void assureApplications(Project project) throws Exception {
		Applications applications = project.getApplications();
		if (applications != null)
			assureApplications(applications);
	}
	
	public void assureApplications(Applications applications) throws Exception {
		Collection<Application> list = ApplicationUtil.getApplications(applications);
		Iterator<Application> iterator = list.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			context.setApplication(application);
			Project project = context.getProject();
			assureApplication(project, application);
		}
	}
	
	public void assureApplication(Project project, Application application) throws Exception {
		Collection<Application> applications = ProjectUtil.getApplications(project);
		//TODO take this condition out?
		if (application == null)
			application = factory.createApplication();
		if (!applications.contains(application))
			ProjectUtil.addApplication(project, application);
		
		assureExtensions(project, application);
		if (application.getContextRoot() == null)
			application.setContextRoot(application.getName());
		if (application.getServices() == null)
			application.setServices(ServicesUtil.newServices());
		if (application.getChannels() == null) {
			Channels newChannels = ChannelUtil.newChannels();
			newChannels.getChannels().addAll(MessagingUtil.getChannels(project));
			application.setChannels(newChannels);
		}
//		if (application.getInformation() == null)
//			application.setInformation(InformationUtil.newInformation());
//		if (application.getPersistence() == null)
//			application.setPersistence(PersistenceUtil.newPersistence());
		if (application.getNamespace() == null)
			application.setNamespace(project.getNamespace());
		if (application.getNamespace() == null)
			application.setNamespace("http://"+project.getDomain()+"/"+application.getName());
			//application.setNamespace("http://"+application.getName());
		//060215 assureModules(project, application);

		Namespace applicationNamespace = context.getNamespaceByUri(application.getNamespace());
		Information informationBlock = application.getInformation();
		if (informationBlock == null) {
			informationBlock = ProjectUtil.getInformationBlock(project, applicationNamespace);
			application.setInformation(informationBlock);
		}
		
		if (informationBlock != null)
			InformationUtil.addNamespace(informationBlock, applicationNamespace);
		//Set<Namespace> namespaceSet = getNamespacesForApplication(project, application);
		//namespaces.addAll(namespaceSet);
		
		assureModules(project, application);
	}

	protected Set<Namespace> getNamespacesForApplication(Project project, Application application) throws Exception {
		Set<Namespace> namespaceSet = new HashSet<Namespace>();
		Set<Module> modelModules = ProjectUtil.getModelModules(project);
		Iterator<Module> iterator = modelModules.iterator();
		while (iterator.hasNext()) {
			Module module = (Module) iterator.next();
			Information informationBlock = module.getInformation();
			namespaceSet.addAll(informationBlock.getNamespaces());
			assureNamespaces(informationBlock.getNamespaces());
		}
		Namespace applicationNamespace = context.getNamespaceByUri(application.getNamespace());
		namespaceSet.add(applicationNamespace);
		assureNamespace(applicationNamespace);
//		if (application.getName().endsWith("supplier"))
//			System.out.println();
		return namespaceSet;
	}

	public void assureModules(Project project) throws Exception {
		assureModules(project, ProjectUtil.getProjectModules(project));
		Collection<Application> applications = ProjectUtil.getApplications(project);
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			context.setApplication(application);
			assureModules(project, application);
		}
	}
	
	public void assureModules(Project project, Application application) throws Exception {
		Collection<Module> modules = ApplicationUtil.getModules(application);
		assureModules(project, modules);
	}
	
	public void assureModules(Project project, ModuleType moduleType) throws Exception {
		Collection<Application> applications = ProjectUtil.getApplications(project);
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			assureModules(project, application, moduleType);
		}
	}
	
	public void assureModules(Project project, Application application, ModuleType moduleType) throws Exception {
		Collection<Module> modules = ApplicationUtil.getModules(application, moduleType);
		if (modules == null || modules.size() == 0) {
			Module module = factory.buildModule(moduleType);
			ApplicationUtil.getModules(application).add(module);
		}
		assureModules(project, modules);
	}

	public void assureModules(Project project, Collection<Module> modules) throws Exception {
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			assureModule(project, module);
		}
	}

	public void assureModule(Project project, Module module) throws Exception {
		Application application = context.getApplication();
		assureModule(project, application, module);

//		Collection<Application> applications = ProjectUtil.getApplications(project);
//		Iterator<Application> iterator = applications.iterator();
//		context.setModule(module);
//		while (iterator.hasNext()) {
//			Application application = iterator.next();
//			assureModule(project, application, module);
//		}
	}
	
	//application may be null for certain project modules - so far not a problem..
	public void assureModule(Project project, Application application, Module module) throws Exception {
		if (module.getRef() != null) {
			Module importedModule = context.getImportedModuleByName(module.getRef());
			Assert.notNull(importedModule, "Referenced module not found: "+ModuleUtil.getModuleId(module));
			ProjectUtil.removeModule(project, module);
			module = ObjectUtil.cloneObject(importedModule);
			ProjectUtil.addModule(project, module);
		}

//		if (module.getName().equals("bookshop2-supplier-view"))
//			System.out.println();
		
		context.setModule(module);
		assureModuleNamespace(project, application, module);

		if (module.getGroupId() == null && application != null)
			module.setGroupId(application.getGroupId());
		if (module.getGroupId() == null)
			module.setGroupId(project.getDomain());

		if (module.getArtifactId() == null) {
			if (module.getName() != null) {
				module.setArtifactId(module.getName());
			} else if (application != null) {
				module.setArtifactId(ModuleFactory.getDefaultArtifactId(application, module.getType()));
			}
		}
		
		if (module.getName() == null) {
			module.setName(module.getArtifactId());
		}
		
		if (module.getVersion() == null && application != null)
			module.setVersion(application.getVersion());
		if (module.getVersion() == null)
			module.setVersion(project.getVersion());
		if (module.getVersion() == null)
			module.setVersion("0.0.1-SNAPSHOT");
		
		if (module.getLevel() == null && application != null)
			module.setLevel(ModuleLevel.APPLICATION_LEVEL);
		if (module.getLevel() == null)
			module.setLevel(ModuleLevel.PROJECT_LEVEL);

		//List<Module> modules = ApplicationUtil.getModules(application);
		//if (!modules.contains(module))
		//	modules.add(module);
		
		switch (module.getType()) {
		case MODEL: assureModelModule(project, application, module); break;
		case CLIENT: assureClientModule(project, application, module); break;
		case SERVICE: assureServiceModule(project, application, module); break;
		case DATA: assureDataModule(project, application, module); break;
		case VIEW: assureViewModule(project, application, module); break;
		case EAR: assureEarModule(project, application, module); break;
		case POM: assurePomModule(project, application, module); break;
		}
	}
	
	public void assureModuleNamespace(Project project, Application application, Module module) throws Exception {
		if (module.getNamespace() == null && application != null)
			module.setNamespace(application.getNamespace());
		if (module.getNamespace() == null)
			module.setNamespace(project.getNamespace());
		Assert.notNull(module.getNamespace(), "Unable to assign namespace for module: "+ModuleUtil.getModuleId(module));
	}
	
	public void assureModelModule(Project project, Application application, Module module) throws Exception {
		Information information = module.getInformation();
		Assert.notNull(information, "Information-block must be specified");
		ExtensionsUtil.addExtension(project, information);
		assureNamespaces(project);
//		Namespace namespace = context.getNamespaceByUri(module.getNamespace());
//		if (namespace != null && module.getType() == ModuleType.MODEL)
//			context.addModuleByNamespace(namespace, module);
	}

	public void assureClientModule(Project project, Application application, Module module) throws Exception {
	}

	public void assureServiceModule(Project project, Application application, Module module) throws Exception {
		Namespace namespace = context.getNamespaceByUri(module.getNamespace());
		Information information = ProjectUtil.getInformationBlock(project, namespace);
		module.setInformation(information);
		assureDomains(application, module);
		assureProcesses(application, module);
		assureServices(application, module);
	}

	public void assureDataModule(Project project, Application application, Module module) throws Exception {
		Persistence persistence = module.getPersistence();
		Assert.notNull(persistence, "Persistence-block must be specified");
		ExtensionsUtil.addExtension(project, persistence);
		if (persistence.getName() == null)
			persistence.setName(module.getName());
		if (persistence.getDomain() == null)
			persistence.setDomain(module.getGroupId());
		
		assureNamespaces(project);
		//TODO? assurePersistence(ProjectUtil.getPersistence(project));
		assurePersistenceBlock(module, ModuleUtil.getPersistenceBlock(module));
	}

	public void assureViewModule(Project project, Application application, Module module) throws Exception {
		assureViewModuleInformation(project, application, module);
		assureViewModuleView(project, application, module);
	}
	
	public void assureViewModuleInformation(Project project, Application application, Module module) throws Exception {
		Information information = module.getInformation();
		if (information.getRef() != null) {
			String ref = information.getRef();
			Information referencedBlock = context.findInformationBlockByName(ref);
			if (enforceChecks)
				Assert.notNull(referencedBlock, "Information not found: "+ref);
			if (referencedBlock != null) {
				module.setInformation(referencedBlock);
				information = referencedBlock;
			}
		}
		assureInformationBlock(project, information);
	}
	
	public void assureViewModuleView(Project project, Application application, Module module) throws Exception {
		View view = module.getView();
		if (view == null)
			return;
		if (view.getRef() != null) {
			String ref = view.getRef();
			View referencedBlock = context.findViewBlockByName(ref);
			if (enforceChecks)
				Assert.notNull(referencedBlock, "View not found: "+ref);
			if (referencedBlock != null) {
				module.setView(referencedBlock);
				view = referencedBlock;
			}
		}
		assureViewBlock(view);
	}

	public void assureEarModule(Project project, Application application, Module module) throws Exception {
	}

	public void assurePomModule(Project project, Application application, Module module) throws Exception {
	}

	public void assureDomains(Project project) throws Exception {
		Collection<Application> applications = ProjectUtil.getApplications(project);
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			assureDomains(application);
		}
	}

	public void assureDomains(Application application) throws Exception {
		assureDomains(application, ApplicationUtil.getClientModules(application));
		assureDomains(application, ApplicationUtil.getServiceModules(application));
	}

	public void assureDomains(Application application, Collection<Module> modules) throws Exception {
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			assureDomains(application, module);
		}
	}

	public void assureDomains(Application application, Module module) throws Exception {
		List<Domain> domains = ModuleUtil.getDomains(module);
		assureDomains(module, domains);
	}


	public void assureDomains(Module module, List<Domain> domains) throws Exception {
		Iterator<Domain> iterator = domains.iterator();
		while (iterator.hasNext()) {
			Domain domain = iterator.next();
			assureDomain(module, domain);
		}
	}

	public void assureDomain(Module module, Domain domain) throws Exception {
		Namespace namespace = context.getNamespaceByUri(module.getNamespace());
		//Assert.notNull(namespace, "Namespace not found for module: "+module.getName());
		if (namespace != null) {
			domain.setNamespace(namespace);
			assureNamespace(namespace);
		}
	}
	
	public void assureServicesOLD(Project project) {
//		BPELResource bpelResource = context.getBpelResource();
//		if (bpelResource != null) {
//			Process process = bpelResource.getProcess();
//			Application application = ProjectUtil.getApplication(project);
//			List<Service> services = ApplicationUtil.getServices(application);
//			Iterator<Service> iterator = services.iterator();
//			while (iterator.hasNext()) {
//				Service service = iterator.next();
//				Assert.notNull(service.getRef(), "Service reference name must exist");
//			}
//		}
	}

	public void assureServices(Project project) throws Exception {
		Collection<Application> applications = ProjectUtil.getApplications(project);
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			assureServices(project, application);
		}
	}
	
	public void assureServices(Project project, Application application) throws Exception {
		Collection<Module> modules = ApplicationUtil.getModules(application);
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			assureServices(application, module);
		}
	}

	public void assureServices(Application application, Module module) throws Exception {
		assureServices(application, module, module.getServices());
	}

	public void assureServices(Services services) throws Exception {
		assureImports(ServicesUtil.getImports(services));
		assureServices(null, null, services);
	}

	public void assureServices(Application application, Module module, Services services) throws Exception {
		//Namespace namespaceByUri = context.getNamespaceByUri(application.getNamespace());
		if (services != null) {
			List<Domain> domains = ServicesUtil.getDomains(services);
			Iterator<Domain> iterator = domains.iterator();
			while (iterator.hasNext()) {
				Domain domain = iterator.next();
				String namespace = null;
				Namespace domainNamespace = domain.getNamespace();
				if (domainNamespace != null) {
					namespace = domainNamespace.getUri();
					if (namespace == null) {
						Namespace namespaceByName = context.getNamespaceByName(domainNamespace.getName());
						domain.setNamespace(namespaceByName);
					}
				}
				if (namespace != null && namespace.equals(application.getNamespace()))
					assureServicesExist(application, module, domain);
				assureServices(application, module, domain);
			}
			
			List<Service> serviceList = ServicesUtil.getServices(services);
			Iterator<Service> iterator2 = serviceList.iterator();
			while (iterator2.hasNext()) {
				Service service = iterator2.next();
				assureService(application, module, null, service);
			}
		}
	}
	
	private void assureServicesExist(Application application, Module module, Domain domain) throws Exception {
		//TODO We need to decide what to do here... create these on the fly? or not
		//TODO builder.buildServicesForElements(application, module, domain);	
	}

	public void assureServices(Application application, Module module, Domain domain) throws Exception {
		Assert.notNull(module, "Module must be specified");
		String baseName = getBaseName(application, module, domain);
		List<Service> servicesList = DomainUtil.getServices(domain);
		Iterator<Service> iterator = servicesList.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			assureService(application, module, domain, service);
			String processName = ServiceLayerHelper.getProcessName(module, domain, baseName);
			Process process = getProcessByName(module, processName);
			if (process == null)
				process = context.getProcessByName(processName);
			if (process == null)
				process = context.getProcessByName(processName);
			//Assert.notNull(process, "Process not found: "+processName);
			if (process != null)
				service.setProcess(process);
		}
	}

	protected String getBaseName(Application application, Module module, Domain domain) {
		String baseName = null;
		if (domain != null) {
			baseName = NameUtil.capName(domain.getName());
		}
		if (module != null)
			baseName = NameUtil.capName(module.getName());
		if (application != null)
			baseName = NameUtil.capName(application.getName());
		baseName = NameUtil.toCamelCase(baseName);
		return baseName;
	}

	public void assureService(Application application, Module module, Domain domain, Service service) throws Exception {
		if (service.getRef() != null) {
			Service importedService = context.getImportedServiceByName(service.getRef());
			Assert.notNull(importedService, "Referenced service not found: "+ServiceLayerHelper.getServiceId(service));
			ModuleUtil.removeService(module, service);
			//ServiceUtil.mergeService(service, importedService);
			service = ObjectUtil.cloneObject(importedService);
			ModuleUtil.addService(module, service);
		}

		context.setService(service);
		assureServiceNamespace(application, module, domain, service);

		if (domain != null) {
			if (service.getVersion() == null)
				service.setVersion(domain.getVersion());
			if (service.getDomain() == null)
				service.setDomain(domain.getName());
		}
		
		if (module != null) {
			if (service.getVersion() == null)
				service.setVersion(module.getVersion());
		}
	
		if (application != null) {
			if (service.getDomain() == null)
				service.setDomain(application.getGroupId());
		}
		
		if (service.getDomain() == null)
			service.setDomain(ServiceLayerHelper.getServiceDomainName(service));

		if (service.getElement() != null) {
			Namespace namespace = context.getNamespaceByUri(service.getNamespace());
			service.setElement(assureType(namespace, service.getElement()));
			
//			if (service.getName().equalsIgnoreCase("orderBooks"))
//				System.out.println();
			
			boolean isSynchronous = ServiceUtil.isSynchronous(service);
			ServiceUtil.setSynchronous(service, isSynchronous);
		}
		
		if (service.getPackageName() == null)
			service.setPackageName(ServiceLayerHelper.getServicePackageName(service));
		if (service.getInterfaceName() == null)
			service.setInterfaceName(ServiceLayerHelper.getServiceInterfaceName(service));
		if (service.getClassName() == null)
			service.setClassName(ServiceLayerHelper.getServiceClassName(service));
		
		List<Operation> operations = ServiceUtil.getOperations(service);
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			assureOperation(service, operation);
		}
		
		//TODO -Do we need / want this?
		//List<Interactor> interactors = service.getInteractors();
		//Iterator<Interactor> iterator3 = interactors.iterator();
		//while (iterator3.hasNext()) {
		//	Interactor interactor = iterator3.next();
		//	if (interactor.getInteraction() == null) {
		//		interactor.setInteraction(Interaction.LISTEN);
		//	}
		//}
		
		if (module != null) {
			Process process = service.getProcess();
			if (process != null && process.getRef() != null) {
				String processRef = process.getRef();
				Processes processes = module.getProcesses();
				Iterator<Process> iterator2 = processes.getProcesses().iterator();
				while (iterator2.hasNext()) {
					Process referencedProcess = iterator2.next();
					if (referencedProcess.getName().equals(processRef))
						service.setProcess(referencedProcess);
				}
			}
		
			if (module.getPersistence() == null && domain != null) {
				List<Persistence> persistenceBlocks = DomainUtil.getPersistenceBlocks(domain);
				if (persistenceBlocks.size() > 0) {
					//TODO make modules have more than one PersistenceBlocks?
					module.setPersistence(persistenceBlocks.get(0));
				}
			}
			
			//TODO -- do we need to resolve "ref" here? should already be done
			Collection<Cache> cacheUnits = ServiceUtil.getCacheUnitReferences(service);
			Iterator<Cache> iterator2 = cacheUnits.iterator();
			while (iterator2.hasNext()) {
				Cache cacheUnit = iterator2.next();
				if (cacheUnit.getRef() != null) {
					String cacheRef = cacheUnit.getRef();
//					List<Cache> caches = PersistenceUtil.getCaches(module.getPersistence());
//					Iterator<Cache> iterator3 = caches.iterator();
//					while (iterator3.hasNext()) {
//						Cache referencedCache = iterator3.next();
//						if (referencedCache.getName().equals(cacheRef))
//							service.setCache(referencedCache);
//					}
				}
			}
		}
		
		//TODO make this totally configurable
		Transacted transacted = service.getTransacted();
		if (transacted == null) {
			transacted = new Transacted();
			transacted.setLocal(false);
			transacted.setScope(TransactionScope.CONVERSATION);
			transacted.setUse(TransactionUsage.REQUIRED);
			service.setTransacted(transacted);
		}
		
		assureInteractors(service);
		assureListeners(service);
		assureChannels(service);
		assureCallbacks(service);
		context.addImportedService(service);
	}
	
	public void assureServiceNamespace(Application application, Module module, Domain domain, Service service) throws Exception {
		if (domain != null && service.getNamespace() == null)
			service.setNamespace(domain.getNamespace().getUri());
		if (module != null && service.getNamespace() == null)
			service.setNamespace(module.getNamespace());
		if (application != null && service.getNamespace() == null)
			service.setNamespace(application.getNamespace());
		Assert.notNull(service.getNamespace(), "Unable to assign namespace for service: "+ServiceLayerHelper.getServiceId(service));
	}

	public void assureInteractors(Service service) {
		List<Interactor> interactors = ServiceUtil.getInteractors(service);
		Iterator<Interactor> iterator = interactors.iterator();
		while (iterator.hasNext()) {
			Interactor interactor = iterator.next();
			assureInteractors(service, interactor);
		}
	}

	/*
	 * At this point, 
	 * the channelName SHOULD currently hold the value of the remoteServiceName,
	 * and we need to convert that remoteServiceName to the remoteChannelName,
	 * and reset interactor's channel name.
	 */
	public void assureInteractors(Service service, Interactor interactor) {
		List<Project> projects = context.getProjectList();
		String channelName = interactor.getChannel();
		Channel channel = MessagingUtil.getChannelByName(projects, channelName);
		if (channel != null) {
			ServiceUtil.addChannel(service, channel);
			
		} else {
			String remoteServiceName = channelName;
			Service remoteService = ProjectUtil.getServiceByName(projects, remoteServiceName);
			
			//if null, remoteService will be set in a subsequent pass
			if (remoteService != null) {
				Assert.notNull(remoteService, "Remote service not found: "+remoteServiceName);
				List<Channel> remoteChannels = ServiceUtil.getChannels(remoteService);
				Iterator<Channel> iterator = remoteChannels.iterator();
				while (iterator.hasNext()) {
					Channel remoteChannel = iterator.next();
					//Channel remoteChannel = MessagingUtil.getChannelByName(projects, name);
					Assert.notNull(remoteChannel, "Remote channel not found: "+remoteServiceName);
					ServiceUtil.addChannel(remoteService, remoteChannel);//TODO need this here?
					String remoteChannelName = remoteChannel.getName();
					interactor.setChannel(remoteChannelName);
				}
			}
		}
		//if null, channelName will be set in a subsequent pass
		//channelName = interactor.getChannel();
		//Assert.notNull(channelName, "Remote channel name null");
	}

	public void assureListeners(Service service) throws Exception {
		List<Listener> listeners = ServiceUtil.getListeners(service);
		Iterator<Listener> iterator = listeners.iterator();
		while (iterator.hasNext()) {
			Listener listener = iterator.next();
			
			/*
			 * TODO properly resolve "ref"
			 * For now: all we need to do
			 * is just make name equal to ref.
			 */
			String ref = listener.getRef();
			if (!StringUtils.isEmpty(ref)) {
				List<Project> projectList = context.getProjectList();
				Listener listenerByName = MessagingUtil.getListenerByName(projectList, ref);
//				if (listenerByName == null)
//					System.out.println();
				Assert.notNull(listenerByName, "Listener not found: "+ref);
				ListenerUtil.mergeListeners(listener, listenerByName);
			}
			
			String channelName = listener.getChannel();
			Channel channel = context.getChannelByName(channelName);
			if (channel != null) {
				Assert.notNull(channel, "Channel not found: "+channelName);
				ServiceUtil.addChannel(service, channel);
				assureChannel(service, channel);
			}
		}
	}
	
	public void assureChannels(Service service) throws Exception {
		List<Channel> channels = new ArrayList<Channel>();
		channels.addAll(ServiceUtil.getChannels(service));
		if (channels.isEmpty()) {
			List<Listener> listeners = ServiceUtil.getListeners(service);
			Iterator<Listener> iterator = listeners.iterator();
			while (iterator.hasNext()) {
				Listener listener = iterator.next();
				/*
				 * TODO properly resolve "ref"
				 * For now: all we need to do
				 * is just make name equal to ref.
				 */
				String ref = listener.getRef();
				if (!StringUtils.isEmpty(ref)) {
					List<Project> projectList = context.getProjectList();
					Listener listenerByName = MessagingUtil.getListenerByName(projectList, ref);
//					if (listenerByName == null)
//						System.out.println();
					Assert.notNull(listenerByName, "Listener not found: "+ref);
					ListenerUtil.mergeListeners(listener, listenerByName);
				}
				
				String channelName = listener.getChannel();
				Channel channel = context.getChannelByName(channelName);
				if (channel == null)
					System.out.println();
				Assert.notNull(channel, "Channel not found: "+channelName);
				ServiceUtil.addChannel(service, channel);
				channels.add(channel);
			}
		}
		Iterator<Channel> iterator = channels.iterator();
		while (iterator.hasNext()) {
			Channel channel = iterator.next();
			assureChannel(service, channel);
		}
	}
	
	public void assureChannel(Service service, Channel channel) throws Exception {
		String channelName = channel.getName();
		Project project = context.getProject();
		Channel referencedChannel = MessagingUtil.getChannelByName(project, channelName);
		if (referencedChannel != null) {
			Assert.notNull(referencedChannel, "Channel not found in global namespace: "+channelName);
			assureChannelSenders(channel, ChannelUtil.getSenders(referencedChannel));
			assureChannelReceivers(channel, ChannelUtil.getReceivers(referencedChannel));
			assureChannelInvokers(channel, ChannelUtil.getInvokers(referencedChannel));
		}
	}

	//TODO make sure this merging will prevent Duplicates
	public void assureChannelSenders(Channel channel, List<Sender> senders) throws Exception {
		Iterator<Sender> iterator = senders.iterator();
		while (iterator.hasNext()) {
			Sender sender = iterator.next();
			String senderName = sender.getName();
			String senderLink = sender.getLink();
			String senderRole = sender.getRole();
			Sender referencedSender = null;
			
			if (senderName != null)
				referencedSender = ChannelUtil.getSenderByName(channel, senderName);
			if (referencedSender == null)
				referencedSender = ChannelUtil.getSenderByLinkAndRole(channel, senderLink, senderRole);
			if (referencedSender == null) {
				ChannelUtil.addSender(channel, sender);
			}
		}
	}

	//TODO make sure this merging will prevent Duplicates
	public void assureChannelReceivers(Channel channel, List<Receiver> receivers) throws Exception {
		Iterator<Receiver> iterator = receivers.iterator();
		while (iterator.hasNext()) {
			Receiver receiver = iterator.next();
			String receiverName = receiver.getName();
			String receiverLink = receiver.getLink();
			String receiverRole = receiver.getRole();
			Receiver referencedReceiver = null;
			
			if (receiverName != null)
				referencedReceiver = ChannelUtil.getReceiverByName(channel, receiverName);
			if (referencedReceiver == null)
				referencedReceiver = ChannelUtil.getReceiverByLinkAndRole(channel, receiverLink, receiverRole);
			if (referencedReceiver == null) {
				ChannelUtil.addReceiver(channel, receiver);
			}
		}
	}

	//TODO make sure this merging will prevent Duplicates
	public void assureChannelInvokers(Channel channel, List<Invoker> invokers) throws Exception {
		Iterator<Invoker> iterator = invokers.iterator();
		while (iterator.hasNext()) {
			Invoker invoker = iterator.next();
			Invoker referencedInvoker = ChannelUtil.getInvokerByName(channel, invoker.getName());
			if (referencedInvoker == null) {
				ChannelUtil.addInvoker(channel, invoker);
			}
		}
	}
	
	//TODO make sure this merging will prevent Duplicates
	public void assureChannelManagerInteractors(Channel channel, List<String> clientNames) throws Exception {
		assureChannelSenderInteractors(channel, clientNames);
		assureChannelReceiverInteractors(channel, clientNames);
		assureChannelInvokerInteractors(channel, clientNames);
	}

	//TODO make sure this merging will prevent Duplicates
	public void assureChannelSenderInteractors(Channel channel, List<String> clientNames) throws Exception {
		Iterator<String> iterator = clientNames.iterator();
		while (iterator.hasNext()) {
			String name = iterator.next();
			Sender sender = ChannelUtil.getSenderByName(channel, name);
			if (sender == null) {
				sender = new Sender();
				sender.setName(name);
				ChannelUtil.addSender(channel, sender);
			}
		}
	}

	//TODO make sure this merging will prevent Duplicates
	public void assureChannelReceiverInteractors(Channel channel, List<String> receiverNames) throws Exception {
		Iterator<String> iterator = receiverNames.iterator();
		while (iterator.hasNext()) {
			String name = iterator.next();
			Receiver receiver = ChannelUtil.getReceiverByName(channel, name);
			if (receiver == null) {
				receiver = new Receiver();
				receiver.setName(name);
				ChannelUtil.addReceiver(channel, receiver);
			}
		}
	}

	//TODO make sure this merging will prevent Duplicates
	public void assureChannelInvokerInteractors(Channel channel, List<String> invokerNames) throws Exception {
		Iterator<String> iterator = invokerNames.iterator();
		while (iterator.hasNext()) {
			String name = iterator.next();
			Invoker invoker = ChannelUtil.getInvokerByName(channel, name);
			if (invoker == null) {
				invoker = new Invoker();
				invoker.setName(name);
				ChannelUtil.addInvoker(channel, invoker);
			}
		}
	}

	
	public void assureCallbacks(Service service) {
		assureIncomingCallbacks(service);
		assureOutgoingCallbacks(service);
	}
	
	public void assureIncomingCallbacks(Service service) {
		List<Callback> callbacks = ServiceUtil.getIncomingCallbacks(service);
		Iterator<Callback> iterator = callbacks.iterator();
		while (iterator.hasNext()) {
			Callback callback = iterator.next();
			assureIncomingCallback(callback);
		}
	}

	public void assureIncomingCallback(Callback callback) {
		List<Channel> channels = ServiceUtil.getChannels(callback);
		Iterator<Channel> iterator = channels.iterator();
		while (iterator.hasNext()) {
			Channel channel = iterator.next();
			assureListeners(callback, channel);
		}
	}

	public void assureOutgoingCallbacks(Service service) {
		List<Callback> callbacks = ServiceUtil.getOutgoingCallbacks(service);
		Iterator<Callback> iterator = callbacks.iterator();
		while (iterator.hasNext()) {
			Callback callback = iterator.next();
			assureOutgoingCallback(callback);
		}
	}

	public void assureOutgoingCallback(Callback callback) {
		String callbackName = callback.getName();
		Service service = context.getService();
		//gets the incoming callback
		Service service2 = context.getImportedServiceByName(callbackName);
		Assert.notNull(service, "Callback service not found: "+callbackName);
		String namespaceUri = service.getNamespace();
		
		callback.setNamespace(service.getNamespace());
		callback.setDomain(service.getDomain());
		callback.setPackageName(ClientLayerHelper.getClientPackageName(service));
		callback.setInterfaceName(ClientLayerHelper.getClientInterfaceName(callback));
		callback.setClassName(ClientLayerHelper.getClientClassName(callback));
		//callback.setName(uncappedName+"Callback");
		//callback.setClassName(cappedName+"CallbackImpl");
		//callback.setInterfaceName(cappedName+"Callback");
		if (service instanceof Callback)
			callback.setReceivingService(((Callback) service).getReceivingService());
		callback.setElement(org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespaceUri , callbackName));
		List<Channel> channels = ServiceUtil.getChannels(service);
		ServiceUtil.addChannels(callback, channels);
		callback.setProcess(service.getProcess());
		
		Iterator<Channel> iterator = channels.iterator();
		while (iterator.hasNext()) {
			Channel channel = iterator.next();
			assureListeners(callback, channel);
		}
	}

	public void assureListeners(Callback callback, Channel channel) {
		List<Receiver> receivers = ChannelUtil.getReceivers(channel);
		Iterator<Receiver> iterator = receivers.iterator();
		while (iterator.hasNext()) {
			Receiver receiver = iterator.next();
			assureListener(callback, receiver);
		}
	}

	public void assureListener(Callback callback, Receiver receiver) {
		if (!ServiceUtil.hasListener(callback, receiver)) {
			Listener listener = new Listener();
			listener.setName(receiver.getChannel());
			listener.setChannel(receiver.getChannel());
			listener.setLink(receiver.getLink());
			listener.setInvalid(receiver.getInvalid());
			listener.setRole(receiver.getName());
			ServiceUtil.addListener(callback, listener);
		}
	}

	
	//assuming service type, if it exists, has already been established/processed
	public void assureOperation(Service service, Operation operation) {
		if (service.getElement() != null) {
			String elementType = service.getElement();
			Element element = context.getElementByType(elementType);
			//Assert.notNull(element, "Element not found for type: "+type);
			if (element != null) {
				/*
				 * TODO properly resolve "ref"
				 * For now: all we need to do
				 * is just make name equal to ref.
				 */
				String ref = operation.getRef();
				if (!StringUtils.isEmpty(ref))
					operation.setName(ref);
				assureOperation(operation, service, element);
			}
		}
	}

	protected boolean assureOperation(Operation operation, Service service, Element element) {
		Namespace namespace = context.getNamespaceByUri(service.getNamespace());
		if (assureOperation_ImportElements(operation, element))
			return true;
		if (assureOperation_GetAllElements(operation, element))
			return true;
		if (assureOperation_GetElementsByField(operation, element))
			return true;
		if (assureOperation_GetElementsByPage(operation, element))
			return true;
		if (assureOperation_GetElementsByCriteria(namespace, operation, element))
			return true;
		if (assureOperation_GetElementById(operation, element))
			return true;
		if (assureOperation_GetElementByField(operation, element))
			return true;
		if (assureOperation_AddElement(operation, element))
			return true;
		if (assureOperation_MoveElement(operation, element))
			return true;
		if (assureOperation_SaveElement(operation, element))
			return true;
		if (assureOperation_DeleteElement(operation, element))
			return true;
		return false;
	}
	
	protected boolean assureOperation_ImportElements(Operation operation, Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String operationName = operation.getName();
		Assert.notNull(operationName, "operationName not found");
		if (operationName != null && operationName.equals("import"+elementClassName+"s")) {
			//operation.setName("import"+elementClassName+"Records");
			return true;
		}
		return false;
	}

	protected boolean assureOperation_GetAllElements(Operation operation, Element element) {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String operationName = operation.getName();
		Assert.notNull(operationName, "operationName not found");
		if (operationName.equals("get"+elementClassName+"List")) {
			//operation.setName("getAll"+elementClassName+"Records");
			operation.addToResults(createResult("list", elementClassName, elementName));
			return true;
		}
		return false;
	}
	
	protected boolean assureOperation_GetElementsByField(Operation operation, Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String operationName = operation.getName();
		Assert.notNull(operationName, "operationName not found");
		
		List<Field> fields = ElementUtil.getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			//if (field instanceof Reference && FieldUtil.isManyToOne((Reference) field)) {
				String fieldName = ModelLayerHelper.getFieldNameCapped(field);
				
//				if (operationName.equals("getOrganizationListByPermissionList"))
//					System.out.println();

				fieldName = NameUtil.trimFromEnd(fieldName, "List");
				fieldName = NameUtil.trimFromEnd(fieldName, "Id");
				if (StringUtils.isEmpty(fieldName))
					continue;
				if (operationName.startsWith("get"+elementClassName+"ListBy"+fieldName)) {
					if (assureOperation_GetElementsByField(operation, element, field)) {
						return true;
					}
				}
			//}
		}
		return false;
	}
	
	protected boolean assureOperation_GetElementsByField(Operation operation, Element element, Field field) {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
//		String fieldName = ModelLayerHelper.getFieldNameUncapped(field);
//		String fieldClassName = ModelLayerHelper.getFieldClassName(field);
//		String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
//		String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
		String operationName = operation.getName();
		
//		if (operationName.equals("getOrganizationListByPermissionList"))
//			System.out.println();
		
//		boolean paramIsElementIdList = operationName.endsWith("IdList");
//		boolean paramIsElementList = operationName.endsWith("List") && !paramIsElementIdList;
//		boolean paramIsElementId = operationName.endsWith("Id");
//		boolean paramIsElement = !paramIsElementIdList && !paramIsElementList && !paramIsElementId;
//		
//		String construct = "item";
//		String parameterName = fieldNameUncapped;
//		String parameterClassName = fieldClassName;
//		String fieldClauseName = fieldNameCapped;
////		if (!ClassUtil.isJavaPrimitiveType(fieldClassName) && !ClassUtil.isJavaDefaultType(fieldClassName)) {
////			if (fieldName.endsWith("List") || operationName.endsWith("List")) {
////				fieldClauseName = NameUtil.assureEndsWith(fieldClauseName, "List");
////				parameterName = NameUtil.assureEndsWith(parameterName, "List");
////				parameterClassName = "Long";
////				construct = "list";
////				
////			} else {
////				fieldClauseName = NameUtil.assureEndsWith(fieldClauseName, "Id");
////				parameterClassName = "Long";
////			}
////			
////		} else {
//			if (paramIsElementIdList) {
//				fieldClauseName = NameUtil.assureEndsWith(fieldClauseName, "List");
//				parameterName = NameUtil.assureEndsWith(parameterName, "List");
//				parameterClassName = "List<"+fieldClassName+">";
//				parameterClassName = fieldClassName;
//				construct = "list";
//				
//			} else if (paramIsElementList) {
//				fieldClauseName = NameUtil.trimFromEnd(fieldClauseName, "List");
//				fieldClauseName = NameUtil.trimFromEnd(fieldClauseName, "Id");
//				Element fieldElement = context.getElementByName(fieldClauseName);
//				String fieldElementClassName = TypeUtil.getClassName(fieldElement.getType());
//				fieldClauseName = NameUtil.assureEndsWith(fieldClauseName, "List");
//				parameterName = NameUtil.trimFromEnd(parameterName, "List");
//				parameterName = NameUtil.trimFromEnd(parameterName, "Id");
//				parameterName = NameUtil.assureEndsWith(parameterName, "List");
//				parameterClassName = fieldElementClassName;
//				construct = "list";
//
//			} else if (paramIsElementId) {
//				fieldClauseName = NameUtil.assureEndsWith(fieldClauseName, "Id");
//				parameterName = NameUtil.assureEndsWith(parameterName, "Id");
//				parameterClassName = "Long";
//
//			} else if (paramIsElement) {
//				fieldClauseName = NameUtil.trimFromEnd(fieldClauseName, "Id");
//				parameterName = NameUtil.trimFromEnd(parameterName, "Id");
//				parameterClassName = fieldClassName;
//			}
//			
//			if (fieldName.endsWith("List") || operationName.endsWith("List")) {
//			}
////		}
		
		String fieldClauseName = getFieldClauseName(operationName, field);
		Parameter parameter = createParameter(operationName, field);
		
		Assert.notNull(operationName, "operationName not found");
		if (operationName.equals("get"+elementClassName+"ListBy"+fieldClauseName)) {
			operation.addToResults(createResult("list", elementClassName, elementName));
			OperationUtil.addParameter(operation, parameter);
			return true;
		}
		return false;
	}
	
	protected String getFieldClauseName(String operationName, Field field) {
		String fieldClauseName = ModelLayerHelper.getFieldNameCapped(field);
		
		boolean paramIsElementIdList = operationName.endsWith("IdList");
		boolean paramIsElementList = operationName.endsWith("List") && !paramIsElementIdList;
		boolean paramIsElementId = operationName.endsWith("Id");
		boolean paramIsElement = !paramIsElementIdList && !paramIsElementList && !paramIsElementId;
		
		if (paramIsElementIdList) {
			fieldClauseName = NameUtil.assureEndsWith(fieldClauseName, "List");
			
		} else if (paramIsElementList) {
			fieldClauseName = NameUtil.trimFromEnd(fieldClauseName, "List");
			fieldClauseName = NameUtil.trimFromEnd(fieldClauseName, "Id");
			fieldClauseName = NameUtil.assureEndsWith(fieldClauseName, "List");

		} else if (paramIsElementId) {
			fieldClauseName = NameUtil.assureEndsWith(fieldClauseName, "Id");

		} else if (paramIsElement) {
			fieldClauseName = NameUtil.trimFromEnd(fieldClauseName, "Id");
		}
		
		return fieldClauseName;
	}

	protected Parameter createParameter(String operationName, Field field) {
		String fieldClassName = ModelLayerHelper.getFieldClassName(field);
		String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
		String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
		
		boolean paramIsElementIdList = operationName.endsWith("IdList");
		boolean paramIsElementList = operationName.endsWith("List") && !paramIsElementIdList;
		boolean paramIsElementId = operationName.endsWith("Id");
		boolean paramIsElement = !paramIsElementIdList && !paramIsElementList && !paramIsElementId;
		
		String construct = "item";
		String parameterName = fieldNameUncapped;
		String parameterClassName = fieldClassName;
		
//		if (!ClassUtil.isJavaPrimitiveType(fieldClassName) && !ClassUtil.isJavaDefaultType(fieldClassName)) {
//			if (fieldName.endsWith("List") || operationName.endsWith("List")) {
//				fieldClauseName = NameUtil.assureEndsWith(fieldClauseName, "List");
//				parameterName = NameUtil.assureEndsWith(parameterName, "List");
//				parameterClassName = "Long";
//				construct = "list";
//				
//			} else {
//				fieldClauseName = NameUtil.assureEndsWith(fieldClauseName, "Id");
//				parameterClassName = "Long";
//			}
//			
//		} else {
		
		if (paramIsElementIdList) {
			parameterName = NameUtil.assureEndsWith(parameterName, "List");
			parameterClassName = "List<"+fieldClassName+">";
			parameterClassName = fieldClassName;
			construct = "list";
			
		} else if (paramIsElementList) {
			fieldNameCapped = NameUtil.trimFromEnd(fieldNameCapped, "List");
			fieldNameCapped = NameUtil.trimFromEnd(fieldNameCapped, "Id");
			Element fieldElement = context.getElementByName(fieldNameCapped);
			String fieldElementClassName = TypeUtil.getClassName(fieldElement.getType());
			parameterName = NameUtil.trimFromEnd(parameterName, "List");
			parameterName = NameUtil.trimFromEnd(parameterName, "Id");
			parameterName = NameUtil.assureEndsWith(parameterName, "List");
			parameterClassName = fieldElementClassName;
			construct = "list";

		} else if (paramIsElementId) {
			parameterName = NameUtil.assureEndsWith(parameterName, "Id");
			parameterClassName = "Long";

		} else if (paramIsElement) {
			parameterName = NameUtil.trimFromEnd(parameterName, "Id");
			parameterClassName = fieldClassName;
		}
			
		Parameter parameter = createParameter(construct , parameterClassName, parameterName);
		return parameter;
	}


	protected boolean assureOperation_GetElementsByPage(Operation operation, Element element) {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String operationName = operation.getName();

		Assert.notNull(operationName, "operationName not found");
		if (operationName.equals("get"+elementClassName+"ListByPage")) {
			OperationUtil.addParameter(operation, createParameter("int", "pageIndex"));
			OperationUtil.addParameter(operation, createParameter("int", "pageSize"));
			operation.addToResults(createResult("list", elementClassName, elementName));
			return true;
		}
		return false;
	}
	
	protected boolean assureOperation_GetElementsByCriteria(Namespace namespace, Operation operation, Element element) {
		Collection<Query> queriesForElement = NamespaceUtil.getQueriesForElement(namespace, element);
		Iterator<Query> iterator = queriesForElement.iterator();
		while (iterator.hasNext()) {
			Query query = iterator.next();
			if (assureOperation_GetElementsByCriteria(operation, element, query)) {
				return true;
			}
		}
		return false;
	}

	protected boolean assureOperation_GetElementsByCriteria(Operation operation, Element element, Query query) {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String queryClassName = DataLayerHelper.getQueryClassName(element);
		String operationName = operation.getName();

		Assert.notNull(operationName, "operationName not found");
		if (operationName.equals("get"+elementClassName+"ListBy"+queryClassName)) {
			Parameter parameter = createParameter(elementClassName+"Criteria", elementName+"Criteria");
			Result result = createResult("list", elementClassName, elementName);
			OperationUtil.addParameter(operation, parameter);
			operation.addToResults(result);
			return true;
		}
		return false;
	}

	protected boolean assureOperation_GetElementById(Operation operation, Element element) {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String operationName = operation.getName();

		if (operationName.equals("get"+elementClassName+"ById")) {
			Field field = context.findFieldByName(element, "id");
			Assert.notNull(field, "Field 'Id' not found for: "+element.getType());
			String fieldClassName = ModelLayerHelper.getFieldClassName(field);
			
			OperationUtil.addParameter(operation, createParameter(fieldClassName, "id"));
			operation.addToResults(createResult(elementClassName, elementName));
			return true;
		}
		return false;
	}
	
	protected boolean assureOperation_GetElementByField(Operation operation, Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String operationName = operation.getName();
		Assert.notNull(operationName, "operationName not found");
		
//		if (operationName.equals("getOrganizationByPermissionList"))
//			System.out.println(operationName);

		List<Field> fields = ElementUtil.getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			//if (field instanceof Reference && FieldUtil.isManyToOne((Reference) field)) {
				String fieldName = ModelLayerHelper.getFieldNameCapped(field);
				
				fieldName = NameUtil.trimFromEnd(fieldName, "List");
				fieldName = NameUtil.trimFromEnd(fieldName, "Id");
				if (StringUtils.isEmpty(fieldName))
					continue;
				if (operationName.startsWith("get"+elementClassName+"By"+fieldName)) {
					if (assureOperation_GetElementByField(operation, element, field)) {
						return true;
					}
				}
			//}
		}
		
//		List<Field> uniqueFields = ElementUtil.getUniqueFields(element);
//		Iterator<Field> iterator = uniqueFields.iterator();
//		while (iterator.hasNext()) {
//			Field field = iterator.next();
//			if (assureOperation_GetElementByField(operation, element, field))
//				return true;
//		}
		
		return false;
	}
	
	protected boolean assureOperation_GetElementByField(Operation operation, Element element, Field field) {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
//		String fieldClassName = ModelLayerHelper.getFieldClassName(field);
//		String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
//		String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
		String operationName = operation.getName();
		Assert.notNull(operationName, "operationName not found");
		
//		String parameterClassName = fieldClassName;
//		if (!ClassUtil.isJavaPrimitiveType(fieldClassName) && !ClassUtil.isJavaDefaultType(fieldClassName)) {
//			fieldNameCapped = NameUtil.assureEndsWith(fieldNameCapped, "Id");
//			fieldNameUncapped = NameUtil.assureEndsWith(fieldNameUncapped, "Id");
//			parameterClassName = "Long";
//		}
//		
//		if (operationName.equals("get"+elementClassName+"By"+fieldNameCapped)) {
//						OperationUtil.addParameter(operation, createParameter(parameterClassName, fieldNameUncapped));
//			operation.setResult(createResult(elementClassName, elementName));
//			return true;
//		}
		
		String fieldClauseName = getFieldClauseName(operationName, field);
		Parameter parameter = createParameter(operationName, field);
		
		if (operationName.equals("get"+elementClassName+"By"+fieldClauseName)) {
			operation.addToResults(createResult(elementClassName, elementName));
			OperationUtil.addParameter(operation, parameter);
			return true;
		}
		
		return false;
	}
	
	protected boolean assureOperation_AddElement(Operation operation, Element element) {
		boolean twoWaySelfReferencing = ElementUtil.isTwoWaySelfReferencing(element);
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String operationName = operation.getName();
		
		Assert.notNull(operationName, "operationName not found");
		if (operationName.equals("add"+elementClassName)) {
			if (twoWaySelfReferencing)
				OperationUtil.addParameter(operation, createParameter("Long", "parentId"));
			OperationUtil.addParameter(operation, createParameter(elementClassName, elementName));
			operation.addToResults(createResult("Long", "id"));
			return true;
		}
		return false;
	}
	
	protected boolean assureOperation_MoveElement(Operation operation, Element element) {
		boolean twoWaySelfReferencing = ElementUtil.isTwoWaySelfReferencing(element);
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String operationName = operation.getName();
		
		if (operationName.equals("move"+elementClassName) && twoWaySelfReferencing) {
			OperationUtil.addParameter(operation, createParameter("Long", elementName+"Id"));
			OperationUtil.addParameter(operation, createParameter("Long", "parentId"));
			return true;
		}
		return false;
	}
	
	protected boolean assureOperation_SaveElement(Operation operation, Element element) {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String operationName = operation.getName();
		
		Assert.notNull(operationName, "operationName not found");
		if (operationName.equals("save"+elementClassName)) {
			OperationUtil.addParameter(operation, createParameter(elementClassName, elementName));
			return true;
		}
		return false;
	}
	
	protected boolean assureOperation_DeleteElement(Operation operation, Element element) {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String operationName = operation.getName();
		
		Assert.notNull(operationName, "operationName not found");
		if (operationName.equals("delete"+elementClassName)) {
			OperationUtil.addParameter(operation, createParameter(elementClassName, elementName));
			return true;
		}
		return false;
	}

	public Result createResult(String resultClassName, String resultName) {
		return createResult("item", resultClassName, resultName);
	}

	public Result createResult(String construct, String resultClassName, String resultName) {
		String resulType = getTypeFromClassName(resultClassName);
		Result result = new Result();
		result.setConstruct(construct);
		result.setType(resulType);
		if (construct.equals("list"))
			resultName += "List";
		result.setName(resultName);
		return result;
	}

	public String getTypeFromClassName(String className) {
		String typeName = null;
		String simpleName = NameUtil.getSimpleName(className);
		if (ClassUtil.isJavaDefaultType(simpleName) || simpleName.equals("void"))
			typeName = org.aries.util.TypeUtil.getTypeFromDefaultType(simpleName);
		if (typeName == null) {
			Element element = context.getElementByName(className.toLowerCase());
			if (element != null) {
				typeName = element.getType();
			} else {
				Enumeration enumeration = context.getEnumerationByName(className.toLowerCase());
				if (enumeration != null)
					typeName = enumeration.getType();
			}
//			if (typeName == null)
//				System.out.println();
		}
		return typeName;
	}

	public Parameter createParameter(String parameterClassName, String parameterName) {
		return createParameter("item", parameterClassName, parameterName);
	}
	
	public Parameter createParameter(String construct, String parameterClassName, String parameterName) {
//		if (parameterClassName.equals("int"))
//			System.out.println();
		String parameterType = getTypeFromClassName(parameterClassName);
		Parameter parameter = new Parameter();
		parameter.setConstruct(construct);
		parameter.setType(parameterType);
		parameter.setName(parameterName);
		return parameter;
	}

	public void assureProcesses(Project project) throws Exception {
		Collection<Application> applications = ProjectUtil.getApplications(project);
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			assureProcesses(project, application);
		}
	}
	
	public void assureProcesses(Project project, Application application) throws Exception {
		Collection<Module> modules = ApplicationUtil.getModules(application);
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			if (module.getType() == ModuleType.SERVICE) {
				assureProcesses(application, module, ModuleUtil.getDomains(module));
				assureProcesses(application, ModuleUtil.getProcesses(module));
			}
		}
	}

	public void assureProcesses(Application application, Module module) throws Exception {
		assureProcesses(application, module, ModuleUtil.getDomains(module));
	}
	
	public void assureProcesses(Application application, Module module, List<Domain> domains) throws Exception {
		Iterator<Domain> iterator = domains.iterator();
		while (iterator.hasNext()) {
			Domain domain = iterator.next();
			String baseName = getBaseName(application, module, domain);
			String processName = ServiceLayerHelper.getProcessName(domains, domain, baseName);
			
			//TODO?
			//if (!containsProcess(module, processName)) {
			//	Transacted transacted = new Transacted();
			//	//TODO configure this
			//	transacted.setLocal(true);
			//	Process process = builder.buildProcess(baseName, domain.getNamespace().getUri(), transacted);
			//	ModuleUtil.addProcess(module, process);
			//}
		}
	}
	
	public static boolean containsProcess(Module module, String processName) {
		Process process = getProcessByName(module, processName);
		return process != null;
	}

	public static Process getProcessByName(Module module, String processName) {
		List<Process> processes = ModuleUtil.getProcesses(module);
		Iterator<Process> iterator = processes.iterator();
		while (iterator.hasNext()) {
			Process process = iterator.next();
			String name = ServiceLayerHelper.getProcessName(process);
			if (name.equalsIgnoreCase(processName))
				return process;
		}
		return null;
	}
	
	public void assureProcesses(Application application, List<Process> processes) throws Exception {
		Iterator<Process> iterator = processes.iterator();
		while (iterator.hasNext()) {
			Process process = iterator.next();
			//ProcessUtil.initializeProcess(process);
			if (process.getState() == null) {
				ProcessState processState = new ProcessState();
				process.setState(processState);
			}
			String processName = ServiceLayerHelper.getProcessName(process);
			org.eclipse.bpel.model.Process bpelProcess = BPELRuntimeCache.INSTANCE.getProcessByName(processName);
//			Process modelProcess = context.getProcessByName(processName);
//			if (modelProcess == null) {
//				modelProcess = new Process();
//				modelProcess.setObject(bpelProcess);
//				modelProcess.setName(processName);
//			}
			process.setObject(bpelProcess);
			//TODO we need to add this check??
			//Assert.notNull(process.getObject(), "Process not found: "+processName);
		}
	}
	
	
	/*
	 * Extensions
	 * ----------
	 */
	
	public void assureExtensions(Project project) throws Exception {
		if (project.getExtensions() == null)
			project.setExtensions(ExtensionsUtil.newExtensions());
		Collection<Application> applications = ProjectUtil.getApplications(project);
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			assureExtensions(project, application);
		}
	}
	
	public void assureExtensions(Project project, Application application) throws Exception {
		assureInformationBlocks(project, application);
		assurePersistenceBlocks(project, application);
	}

	/*
	 * Information LAYER
	 * -----------------
	 */

	public void assureInformationBlocks(Project project) throws Exception {
		Collection<Information> informationBlocks = ProjectUtil.getInformationBlocks(project);
//		if (informationBlocks.size() == 0)
//			ProjectUtil.addInformationBlock(project, InformationUtil.newInformation());
		informationBlocks = ProjectUtil.getInformationBlocks(project);
		assureInformationBlocks(project, informationBlocks);
	}
	
	public void assureInformationBlocks(Project project, Collection<Information> informationBlocks) throws Exception {
		Iterator<Information> iterator = informationBlocks.iterator();
		while (iterator.hasNext()) {
			Information information = iterator.next();
			assureInformationBlock(project, information);
		}
	}

	public void assureInformationBlocks(Project project, Application application) throws Exception {
		Information information = application.getInformation();
		if (information != null) {
			assureInformationBlock(project, information);
		}
	}

	public void assureInformationBlock(Project project, Information information) throws Exception {
		if (information.getRef() != null) {
			String ref = information.getRef();
			Information referencedBlock = context.findInformationBlockByName(ref);
			Assert.notNull(referencedBlock, "Information not found: "+ref);
			ExtensionsUtil.mergeInformationBlocks(information, referencedBlock);
		}
		assureImports(InformationUtil.getImports(information));
		assureNamespaceImports(information);
		assureNamespaces(information);
	}

	public void assureNamespaces(Project project) throws Exception {
		assureNamespaceImports2(ProjectUtil.getInformationBlocks(project));
		assureNamespaces(ProjectUtil.getNamespaces(project));
	}

	public void assureNamespaces(Information model) throws Exception {
		Collection<Namespace> namespaces = InformationUtil.getNamespaces(model);
		assureNamespaces(model, namespaces);
		assureNamespaces(namespaces);
	}

	public void assureNamespaces(Information model, Collection<Namespace> namespaces) throws Exception {
		Collection<Namespace> importedNamespaces = NamespaceUtil.getImportedNamespaces(model);
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
//			if (importedNamespaces.size() >= 3)
//				System.out.println();
//			if (model.get)
			
			//NamespaceUtil.addImportedNamespaces(namespace, importedNamespaces);
			assureNamespace(namespace);
		}
	}

	public void assureNamespaces(Persistence model) throws Exception {
		Collection<Namespace> namespaces = PersistenceUtil.getNamespaces(model);
		Namespace namespace = null;
		if (namespace == null && model.getNamespace() != null) {
			namespace = context.getNamespaceByUri(model.getNamespace());
		}
		if (namespace == null && model.getName() != null) {
			namespace = context.getNamespaceByName(model.getName());
		}
//		if (namespace == null) {
//			namespace = new Namespace();
//			Project project = context.getProject();
//			Application application = context.getApplication();
//			namespace.setName(model.getName());
//			namespace.setPrefix(application.getName());
//			namespace.setUri(application.getNamespace());
//			context.populateNamespace(namespace);
//		}
		if (namespace != null) {
			namespaces.add(namespace);
			model.setNamespace(namespace.getUri());
		}
		assureNamespaces(model, namespaces);
		assureNamespaces(namespaces);
	}
	
	public void assureNamespaces(Persistence model, Collection<Namespace> namespaces) throws Exception {
		Collection<Namespace> importedNamespaces = NamespaceUtil.getImportedNamespaces(model);
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
//			if (importedNamespaces.size() >= 3)
//				System.out.println();
//			if (model.get)
			NamespaceUtil.addImportedNamespaces(namespace, importedNamespaces);
			assureNamespace(namespace);
		}
	}
	
//	public Collection<Namespace> getImportedNamespaces(Persistence model) {
//		Set<Namespace> importedNamespaces = new HashSet<Namespace>();
//		List<Import> imports = model.getImports();
//		Iterator<Import> iterator = imports.iterator();
//		while (iterator.hasNext()) {
//			Import container = iterator.next();
//			Object object = container.getObject();
//			if (object instanceof Persistence) {
//				Persistence block = (Persistence) object;
//				Namespace namespace = context.getNamespaceByUri(block.getNamespace());
//				importedNamespaces.add(namespace);
//			}
//		}
//		return importedNamespaces;
//	}
	
	public void assureNamespaces(Collection<Namespace> namespaces) throws Exception {
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			assureNamespace(namespace);
		}
	}
	
	//Absolutely process Enumerations first. 
	//Their cache needs to be populated before Elements are processed.
	public void assureNamespace(Namespace namespace) throws Exception {
		//URI and either prefix or name must exist
		Assert.notNull(namespace.getUri(), "Namespace URI must be specified");
		Assert.isTrue(namespace.getPrefix() != null || namespace.getName() != null, "Either prefix or name must exist for namespace: "+namespace.getUri());
		if (namespace.getPrefix() == null)
			namespace.setPrefix(namespace.getName());
		if (namespace.getName() == null)
			namespace.setName(namespace.getPrefix());
		
		if (namespace.getUri() == null) {
			Assert.notNull(namespace.getName(), "Namespace name must be specified: "+namespace.getPrefix());
			Namespace cachedNamespace = context.getNamespaceByName(namespace.getName());
			Assert.notNull(cachedNamespace, "Namespace not found in cache: "+namespace.getName());
			namespace.setUri(cachedNamespace.getUri());
		}

//		if (namespace.getName().contains("bookshop2-shipper"))
//			System.out.println();
//		if (namespace.getName().equals("bookshop2"))
//			System.out.println();
		context.populateNamespace(namespace);
		populateElements(namespace);
		populateEnumerations(namespace);
		assureTypes(namespace);
		assureQueries(namespace);
		//context.populateTypes(namespace);
		
		Properties properties = NamespaceUtil.getProperties(namespace);
		PropertyUtil.applyDefault(properties, "generate-schema", true);
	}

	public void populateElements(Collection<Namespace> namespaces) throws Exception {
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			populateElements(namespace);
		}
	}
	
	public void populateElements(Namespace namespace) throws Exception {
		List<Type> types = NamespaceUtil.getTypes(namespace);
		Iterator<Type> iterator = types.iterator();
		while (iterator.hasNext()) {
			Type type = iterator.next();
			if (type instanceof Element) {
				Element element = (Element) type;
//				String elementType = element.getType();
//				String elementExtends = element.getExtends();
				assureType(namespace, element);
				assureExtends(namespace, element);
				assureItems(namespace, element);
				
//				if (elementType == null) {
//					String localPart = NameUtil.uncapName(element.getName());
//					element.setType("{"+namespace.getUri()+"}"+localPart);
//				}
//				if (elementExtends != null) {
//					String nsPrefix = namespace.getPrefix();
//					if (elementExtends.contains(":"))
//						nsPrefix = NameUtil.getPrefixFromXSDType(elementExtends);
//					Namespace superTypeNamespace = context.getNamespaceByPrefix(nsPrefix);
//					Assert.notNull(superTypeNamespace, "Super-type namespace not found for prefix: "+nsPrefix);
//					String localPart = NameUtil.uncapName(elementExtends);					
//					element.setExtends("{"+superTypeNamespace.getUri()+"}"+localPart);
//				}
				context.populateElement(element);
			}
		}
	}

	public void populateEnumerations(Namespace namespace) throws Exception {
		List<Type> types = NamespaceUtil.getTypes(namespace);
		Iterator<Type> iterator = types.iterator();
		while (iterator.hasNext()) {
			Type type = iterator.next();
			if (type instanceof Enumeration) {
				Enumeration enumeration = (Enumeration) type;
				if (enumeration.getType() == null) {
					String localPart = NameUtil.uncapName(enumeration.getName());
					enumeration.setType("{"+namespace.getUri()+"}"+localPart);
				}
				context.populateEnumeration(enumeration);
			}
		}
	}
	
	public void assureTypes(Namespace namespace) throws Exception {
		List<Type> types = NamespaceUtil.getTypes(namespace);
		Iterator<Type> iterator = types.iterator();
		while (iterator.hasNext()) {
			Type type = iterator.next();
			if (type instanceof Enumeration)
				assureEnumeration(namespace, (Enumeration) type);
			if (type instanceof Element)
				assureElement(namespace, (Element) type);
		}
	}

	public void assureElements(Namespace namespace) throws Exception {
		List<Element> elements = NamespaceUtil.getElements(namespace);
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			assureElement(namespace, element);
		}
	}

	public void assureElement(Namespace namespace, Element element) throws Exception {
		if (element.getStructure() == null)
			element.setStructure("item");
		assureType(namespace, element);
		assureExtends(namespace, element);
		assureItems(namespace, element);
		context.populateElement(element);
		convertItems(namespace, element);
		//assureIdAttribute(namespace, element);
		assureAttributes(namespace, element);
		assureReferences(namespace, element);
		//System.out.println(">>>"+element.getType());
//		if (element.getName().equalsIgnoreCase("userInterface"))
//			System.out.println(">>>"+element.getType());
	}

	public void assureType(Namespace namespace, Type type) throws Exception {
//		if (namespace.getName().startsWith("book") && type.getName().startsWith("OrderReq"))
//			System.out.println();
		if (type.getType() == null) {
			String typeName = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace.getUri(), type.getName());
			type.setType(typeName);
		} else {
			String typeName = type.getType();
			if (typeName.startsWith("{") == false) {
				typeName = getTypeFromXSDType(typeName);
				type.setType(typeName);
			}
		}
	}
	
	public void assureExtends(Namespace namespace, Element element) throws Exception {
		String extendsType = element.getExtends();
		if (extendsType != null) {
			if (extendsType.startsWith("{") == false)
				extendsType = getTypeFromXSDType(extendsType);
			element.setExtends(extendsType);
		}
	}

	public void assureType(Namespace namespace, Field field) throws Exception {
		String type = field.getType();
//		System.out.println(">>>"+type);
//		if (type != null && type.toLowerCase().contains("role"))
//			System.out.println();
		if (type == null) {
			type = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace.getUri(), field.getName());
		} else if (type.contains(":") == false) {
			//TODO handle these special cases in a better way - And externalize!
			//is this a common Java class?
			if (ClassUtil.classExists(type))
				type = TypeUtil.getTypeFromJavaType(type);
			else type = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace.getUri(), type);
		} else if (type.startsWith("{") == false) {
			type = getTypeFromXSDType(type);
		}
		field.setType(type);
		
		String key = field.getKey();
		if (key != null) {
			if (key.contains(":") == false) {
				key = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace.getUri(), key);
			} else if (key.startsWith("{") == false) {
				key = getTypeFromXSDType(key);
			}
			field.setKey(key);
		}
		
		//this flag is very important - this is how we identify that a field type is a simple type.
		field.setSimple(isSimple(field));
	}
	
	//this flag is very important - this is how we identify that a field type is a simple type.
	protected Boolean isSimple(Field field) {
		String type = field.getType();
		String className = TypeUtil.getClassName(type);
		String javaName = TypeUtil.getJavaName(type);
		if (CodeGenUtil.isJavaDefaultType(className))
			return true;
		Enumeration enumeration = enumCache.get(type);
		if (enumeration != null)
			return true;
		if (javaName.equals("java.util.Date"))
			return true;
		return null;
	}

	public void assureItems(Namespace namespace, Element element) throws Exception {
		List<Field> items = consolodateItems(element);
		Iterator<Field> iterator = items.iterator();
		while (iterator.hasNext()) {
			Field item = iterator.next();
			assureItem(namespace, element, item);
		}
	}

	public List<Field> assureItemsIncludeId(List<Field> items) {
		// TODO Auto-generated method stub
		return null;
	}

	public void assureItem(Namespace namespace, Element element, Field item) throws Exception {
		Assert.notNull(item.getName(), "Name must be specified");
		assureType(namespace, item);
		if (item.getChangeable() == null)
			item.setChangeable(true);
		if (item.getColumn() == null)
			item.setColumn(NameUtil.toUnderscoredLowercase(item.getName()));
		if (item instanceof Id) {
			item.setStructure("item");
		} else if (item instanceof Secret) {
			item.setStructure("item");
		} else if (item instanceof ListItem) {
			item.setStructure("list");
		} else if (item instanceof SetItem) {
			item.setStructure("set");
		} else if (item instanceof MapItem) {
			item.setStructure("map");
		} else {
			item.setStructure("item");
		}
	}
	
	//TODO check for duplicates and generate warnings if found
	public void convertItems(Namespace namespace, Element element) {
		convertItemsToAttributesAndReferences(element);
	}

	protected void convertItemsToAttributesAndReferences(Element element) {
		List<Serializable> items = element.getIdsAndItemsAndSecrets();
		Iterator<Serializable> iterator = items.iterator();
		while (iterator.hasNext()) {
			Serializable object = iterator.next();
			if (object instanceof Field) {
				convertItemToAttributesAndReferences(element, (Field) object);
			} else if (object instanceof Grouping) {
				convertItemsToAttributesAndReferences(element, (Grouping) object);
			}
			
//			if (object instanceof Item) {
//				Item item = (Item) object;
//				convertItemToAttributesAndReferences(element, item);
//			}
//			if (object instanceof ListItem) {
//				ListItem listItem = (ListItem) object;
//				if (ItemUtil.isAttribute(listItem))
//					element.getAttributesAndReferencesAndGroups().add(convertItemToAttribute(listItem));
//				else element.getAttributesAndReferencesAndGroups().add(convertItemToReference(listItem));
//			}
//			if (object instanceof SetItem) {
//				SetItem setItem = (SetItem) object;
//				element.getAttributesAndReferencesAndGroups().add(convertItemToReference(setItem));
//			}
//			if (object instanceof MapItem) {
//				MapItem mapItem = (MapItem) object;
//				element.getAttributesAndReferencesAndGroups().add(convertItemToReference(mapItem));
//			}
//			if (object instanceof Grouping) {
//				Grouping group = (Grouping) object;
//				convertItemsToAttributesAndReferences(element, group);
//			}
		}
	}
	
	protected void convertItemToAttributesAndReferences(Element element, Field field) {
//		if (field.getType().endsWith("familyRelation"))
//			System.out.println();
//		if (FieldUtil.isInverse(field) && field.getType().endsWith("user"))
//			System.out.println();
		if (!ElementUtil.isFieldExists(element, field)) {
			if (isAttribute(field)) {
				ElementUtil.addField(element, convertItemToAttribute(field));
			} else {
				ElementUtil.addField(element, convertItemToReference(field));
			}
		}
	}
	
	protected void convertItemsToAttributesAndReferences(Element element, Grouping group) {
		if (!ElementUtil.isGroupExists(element, group)) {
			Grouping permanentGroup = new Grouping();
			permanentGroup.setName(group.getName());
			ElementUtil.addGroup(element, permanentGroup);
			List<Field> fields = group.getIdsAndItemsAndSecrets();
			Iterator<Field> iterator = fields.iterator();
			while (iterator.hasNext()) {
				Field field = iterator.next();
				if (!ElementUtil.isFieldExists(permanentGroup, field)) {
					if (isAttribute(field)) {
						permanentGroup.getFields().add(convertItemToAttribute(field));
					} else {
						permanentGroup.getFields().add(convertItemToReference(field));
					}
				}
			}
		}
	}
	
	public boolean isAttribute(Field field) {
		if (FieldUtil.isBaseType(field))
			return true;
		if (context.getEnumerationByType(field.getType()) != null)
			return true;
		return false;
	}
	
	protected Attribute convertItemToAttribute(Field field) {
		Attribute attribute = null;
		if (field instanceof Id)
			attribute = new Id();
		else attribute = new Attribute();
		attribute.setName(field.getName());
		attribute.setType(field.getType());
		attribute.setId(FieldUtil.isId(field));
		attribute.setSimple(FieldUtil.isSimple(field));
		attribute.setUnique(FieldUtil.isUnique(field));
		attribute.setIndexed(FieldUtil.isIndexed(field));
		attribute.setRequired(FieldUtil.isRequired(field));
		attribute.setNullable(FieldUtil.isNillable(field));
		attribute.setTransient(FieldUtil.isTransient(field));
		attribute.setVolatile(FieldUtil.isVolatile(field));
		attribute.setChangeable(FieldUtil.isChangeable(field));
		attribute.setUnsettable(FieldUtil.isUnsettable(field));
		attribute.setUseForEquals(FieldUtil.isUseForEquals(field));
		attribute.setUseForLabel(FieldUtil.isUseForLabel(field));
		attribute.setManaged(FieldUtil.isManaged(field));
		attribute.setEnact(field.getEnact());
		attribute.setColumn(field.getColumn());
		attribute.setDefault(field.getDefault());
		attribute.setDerived(false);
		attribute.setOrdered(false);
		
		if (FieldUtil.isRequired(field)) {
			attribute.setMinOccurs(1);
		} else { 
			attribute.setMinOccurs(0);
		}
		
		if (field instanceof Id) {
			Id id = (Id) field;
			attribute.setStructure("item");
			attribute.setMaxOccurs(1);
			((Id) attribute).setSource(id.getSource());
			((Id) attribute).setInitialValue(id.getInitialValue());
			((Id) attribute).setAllocationSize(id.getAllocationSize());

		} else if (field instanceof Item) {
			attribute.setStructure("item");
			attribute.setMaxOccurs(1);

		} else if (field instanceof Secret) {
			attribute.setStructure("item");
			attribute.setMaxOccurs(1);
			attribute.setHash(field.getHash());
			
		} else if (field instanceof ListItem) {
			attribute.setStructure("list");
			attribute.setMaxOccurs(-1);
			
		} else if (field instanceof SetItem) {
			attribute.setStructure("set");
			attribute.setMaxOccurs(-1);
			
		} else if (field instanceof MapItem) {
			attribute.setStructure("map");
			attribute.setMaxOccurs(-1);
			attribute.setKey(field.getKey());
		}

		attribute.setCascade(field.getCascade());
		attribute.setFetch(field.getFetch());
		return attribute;
	}

	protected Reference convertItemToReference(Field field) {
//		if (field.getName().equalsIgnoreCase("permissions"))
//			System.out.println();
		
		Reference reference = new Reference();
		reference.setName(field.getName());
		reference.setType(field.getType());
		reference.setSimple(FieldUtil.isSimple(field));
		reference.setUnique(FieldUtil.isUnique(field));
		reference.setIndexed(FieldUtil.isIndexed(field));
		reference.setRequired(FieldUtil.isRequired(field));
		reference.setNullable(FieldUtil.isNillable(field));
		reference.setTransient(FieldUtil.isTransient(field));
		reference.setVolatile(FieldUtil.isVolatile(field));
		reference.setChangeable(FieldUtil.isChangeable(field));
		reference.setUnsettable(FieldUtil.isUnsettable(field));
		reference.setTwoWay(FieldUtil.isTwoWay(field));
		reference.setInverse(FieldUtil.isInverse(field));
		reference.setContained(FieldUtil.isContained(field));
		reference.setManaged(FieldUtil.isManaged(field));
		reference.setDerived(FieldUtil.isDerived(field));
		reference.setOrdered(FieldUtil.isOrdered(field));
		reference.setUseForEquals(FieldUtil.isUseForEquals(field));
		reference.setUseForLabel(FieldUtil.isUseForLabel(field));
		reference.setEnact(field.getEnact());
		reference.setColumn(field.getColumn());
		reference.setDefault(field.getDefault());
		
		if (FieldUtil.isRequired(field)) {
			reference.setMinOccurs(1);
		} else { 
			reference.setMinOccurs(0);
		}
		
//		if (field.getType().endsWith("user"))
//			System.out.println();
//		if (FieldUtil.isInverse(field) && field.getType().endsWith("user"))
//			System.out.println();
		
		//if (reference.getName().toLowerCase().startsWith("emailbox"))
		//	System.out.println();
		
		if (field instanceof Id) {
			//TODO this should not happen I think
			//TODO Id should always be attribute
			Id id = (Id) field;
			reference.setMaxOccurs(1);

		} else if (field instanceof Item) {
			Item item = (Item) field;
			reference.setStructure("item");
			reference.setMaxOccurs(1);
			reference.setRelation(item.getRelation());
			reference.setBinding(item.getBinding());
			if (reference.getMappedBy() == null)
				reference.setMappedBy(item.getMappedBy());

		} else if (field instanceof Secret) {
			reference.setStructure("item");
			reference.setMaxOccurs(1);

		} else if (field instanceof ListItem) {
			ListItem listItem = (ListItem) field;
			reference.setStructure("list");
			reference.setMaxOccurs(-1);
			reference.setRelation(listItem.getRelation());
			reference.setBinding(listItem.getBinding());
			if (reference.getMappedBy() == null)
				reference.setMappedBy(listItem.getMappedBy());

			List<String> itemTypes = listItem.getItemTypes();
			Iterator<String> iterator = itemTypes.iterator();
			while (iterator.hasNext()) {
				String itemType = iterator.next();
				reference.getItemTypes().add(itemType);
			}

		} else if (field instanceof SetItem) {
			SetItem setItem = (SetItem) field;
			reference.setStructure("set");
			reference.setMaxOccurs(-1);
			reference.setRelation(setItem.getRelation());
			reference.setBinding(setItem.getBinding());
			if (reference.getMappedBy() == null)
				reference.setMappedBy(setItem.getMappedBy());

			List<String> itemTypes = setItem.getItemTypes();
			Iterator<String> iterator = itemTypes.iterator();
			while (iterator.hasNext()) {
				String itemType = iterator.next();
				reference.getItemTypes().add(itemType);
			}

		} else if (field instanceof MapItem) {
			MapItem mapItem = (MapItem) field;
			reference.setStructure("map");
			reference.setMaxOccurs(-1);
			reference.setKey(mapItem.getKey());
			reference.setRelation(mapItem.getRelation());
			reference.setBinding(mapItem.getBinding());
			if (reference.getMappedBy() == null)
				reference.setMappedBy(mapItem.getMappedBy());
		}
		
		if (field instanceof ListItem && field instanceof SetItem) {
		}
		
		reference.setCascade(field.getCascade());
		reference.setFetch(field.getFetch());
		return reference;
	}

	//UNUSED
	//TODO check for duplicates and generate warnings if found
	protected List<Field> consolodateItems(Element element) {
		List<Field> items = new ArrayList<Field>();
		List<Serializable> objects = element.getIdsAndItemsAndSecrets();
		Iterator<Serializable> iterator = objects.iterator();
		while (iterator.hasNext()) {
			Serializable object = iterator.next();
			if (object instanceof Field) {
				items.add((Field) object);
			} else if (object instanceof Grouping) {
				Grouping group = (Grouping) object;
				items.addAll(group.getIdsAndItemsAndSecrets());
			}
		}
		//TODO add option for sorting
		//Collections.sort(items, createItemComparator());
		return items;
	}
	
	protected Comparator<Item> createItemComparator() {
		return new Comparator<Item>() {
			public int compare(Item item1, Item item2) {
				return item1.getName().compareTo(item2.getName());
			}
		};
	}
	
	public void assureIdAttribute(Namespace namespace, Element element) throws Exception {
		List<Attribute> attributes = ElementUtil.getAttributes(element);
		if (element.getTransient())
			return;
		if (FieldUtil.isIdExist(attributes))
			return;
		if (!ElementUtil.isUserDefined(element))
			return;
//		String name = element.getName();
//		if (name.endsWith("Criteria"))
//			return;
//		if (name.endsWith("Event"))
//			return;
//		if (name.endsWith("Exception"))
//			return;
//		if (name.endsWith("Key"))
//			return;
//		if (name.endsWith("Message"))
//			return;
		Id id = FieldUtil.createId();
		ElementUtil.addField(element, id, 0);
	}
	
	public void assureAttributes(Namespace namespace, Element element) throws Exception {
		List<Attribute> attributes = ElementUtil.getAttributes(element);
		Iterator<Attribute> iterator = attributes.iterator();
		while (iterator.hasNext()) {
			Attribute attribute = iterator.next();
			assureAttribute(namespace, element, attribute);
		}
	}
	
	public void assureAttribute(Namespace namespace, Element element, Attribute attribute) throws Exception {
		//nothing for now
	}

	public void assureReferences(Namespace namespace, Element element) throws Exception {
		List<Reference> references = ElementUtil.getReferences(element);
		Iterator<Reference> iterator = references.iterator();
		int inverseFieldCount = 0;
		while (iterator.hasNext()) {
			Reference reference = iterator.next();
			assureReference(namespace, element, reference);
			if (FieldUtil.isInverse(reference))
				inverseFieldCount++;
		}
		Assert.isTrue(inverseFieldCount <= 1, "At most only one inverse field can exist in an Element");
	}
	
	public void assureReference(Namespace namespace, Element element, Reference reference) throws Exception {
		String referenceType = reference.getType();
		String elementType = element.getType();
//		String className = TypeUtil.getClassName(elementType);
//		if (className.equals("Organization"))
//			System.out.println();
//		if (className.equals("Organization") && referenceType.endsWith("organization"))
//			System.out.println();
		if (referenceType.equals(elementType)) {
			reference.setTwoWay(true);
		}
	}
	
	public String getTypeFromXSDType(String type) {
		String nsPrefix = NameUtil.getPrefixFromXSDType(type);
		if (StringUtils.isEmpty(nsPrefix))
			return type;
		String localPart = NameUtil.getLocalNameFromXSDType(type);
		String namespaceUri = context.getNamespaceURIByPrefix(nsPrefix);
		Assert.notNull(namespaceUri, "Namespace not found for prefix: "+nsPrefix);
		type = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespaceUri, localPart);
		return type;
	}

	public void assureEnums(Namespace namespace) throws Exception {
		List<Enumeration> enumerations = NamespaceUtil.getEnumerations(namespace);
		Iterator<Enumeration> iterator = enumerations.iterator();
		while (iterator.hasNext()) {
			Enumeration enumeration = iterator.next();
			assureEnumeration(namespace, enumeration);
		}
	}

	public void assureEnumeration(Namespace namespace, Enumeration enumeration) throws Exception {
		assureType(namespace, enumeration);
		assureLiterals(namespace, enumeration);
		enumCache.put(enumeration.getType(), enumeration);
		context.populateEnumeration(enumeration);
	}

	public void assureLiterals(Namespace namespace, Enumeration enumeration) throws Exception {
		List<Literal> literals = enumeration.getLiterals();
		Iterator<Literal> iterator = literals.iterator();
		while (iterator.hasNext()) {
			Literal literal = iterator.next();
			assureLiteral(enumeration, literal);
		}
	}

	public void assureLiteral(Enumeration enumeration, Literal literal) throws Exception {
		if (literal.getValue() == null) {
			int indexOf = enumeration.getLiterals().indexOf(literal);
			literal.setValue(indexOf);
		}
	}
	
	/*
	 * Query-specific
	 */
	
	public void assureQueries(Namespace namespace) throws Exception {
		List<Query> types = NamespaceUtil.getQueries(namespace);
		Iterator<Query> iterator = types.iterator();
		while (iterator.hasNext()) {
			Query query = iterator.next();
			assureQuery(namespace, query);
		}
	}
	
	public void assureQuery(Namespace namespace, Query query) throws Exception {
		Assert.notEmpty(query.getFrom(), "From element not found in Query");
		query.setFrom(assureType(namespace, query.getFrom()));
		Condition condition = QueryUtil.getCondition(query);
		Criteria criteria = QueryUtil.getCriteria(query);
		if (condition != null)
			assureQuery(namespace, query, condition.getInsAndLikes());
		if (criteria != null)
			assureQuery(namespace, query, criteria.getInsAndLikes());
	}
	
	public void assureQuery(Namespace namespace, Query query, List<Serializable> insAndLikes) throws Exception {
		Iterator<Serializable> iterator = insAndLikes.iterator();
		while (iterator.hasNext()) {
			Serializable object = iterator.next();
			if (object instanceof Like) {
				Like likeOp = (Like) object;
				Assert.notEmpty(likeOp.getType(), "Type element not found in Like");
				likeOp.setType(assureType(namespace, likeOp.getType()));
				
			} else if (object instanceof In) {
				In inOp = (In) object;
				Assert.notEmpty(inOp.getType(), "Type element not found in In");
				inOp.setType(assureType(namespace, inOp.getType()));
			}
		}
	}
	
	public String assureType(Namespace namespace, String typeName) throws Exception {
		if (typeName.startsWith("{") == false) {
			if (typeName.contains(":") == false)
				typeName = namespace.getPrefix() + ":" + typeName;
			typeName = getTypeFromXSDType(typeName);
		}
		return typeName;
	}
	
	public void assureType(Namespace namespace, Query query) throws Exception {
		if (query.getFrom() == null) {
			String typeName = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace.getUri(), query.getName());
			query.setFrom(typeName);
		} else {
			String typeName = query.getFrom();
			if (typeName.startsWith("{") == false)
				typeName = getTypeFromXSDType(typeName);
			query.setFrom(typeName);
		}
	}
	

	/*
	 * Persistence LAYER
	 * -----------------
	 */

	public void assurePersistenceBlocks(Project project) throws Exception {
		//List<Persistence> persistenceBlocks = ProjectUtil.getPersistenceBlocks(project);
		//assurePersistenceBlocks(persistenceBlocks);
		Collection<Application> applications = ProjectUtil.getApplications(project);
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			assurePersistenceBlocks(project, application);
		}
	}
	
	public void assurePersistenceBlocks(Project project, Application application) throws Exception {
		Persistence persistence = application.getPersistence();
		if (persistence == null) {
			persistence = ProjectUtil.getPersistenceBlockByName(project, application.getArtifactId());
			application.setPersistence(persistence);
//			if (persistence != null)
//				System.out.println();
		}
		if (persistence != null) {
			Collection<Module> dataModules = ApplicationUtil.getDataModules(application);
			Iterator<Module> iterator = dataModules.iterator();
			while (iterator.hasNext()) {
				Module module = iterator.next();
				assurePersistenceBlock(module, persistence);
			}
		}
//		if (persistence == null)
//			System.out.println();
	}

//	public void assurePersistenceBlocks(List<Persistence> persistenceBlocks) throws Exception {
//		Iterator<Persistence> iterator = persistenceBlocks.iterator();
//		while (iterator.hasNext()) {
//			Persistence persistence = iterator.next();
//			assurePersistenceBlock(null, persistence);
//		}
//	}

	public void assurePersistenceBlock(Persistence persistence) throws Exception {
		assurePersistenceBlock(null, persistence);
	}
	
	public void assurePersistenceBlock(Module module, Persistence persistence) throws Exception {
		assureImports(PersistenceUtil.getImports(persistence));
		//PersistenceUtil.getNamespaces(persistence);
		//assureNamespaces(project);
		
		if (persistence.getName() == null) {
			/*
			 * We may come here for any "persist" block specified in ARIEL
			 * that does not have a name yet - just adopt the name from the
			 * first Unit (in ARIEL "persist" blocks will have only one Unit).
			 */
			Collection<Unit> units = PersistenceUtil.getUnits(persistence);
			Assert.notNull(units, "Persistence units null");
			Assert.notEmpty(units, "At least one Persistence-unit must be specified");
			Unit unit = units.iterator().next();
			persistence.setName(unit.getName());
		}
		
		//Project project = context.getProject();
		//ProjectUtil.getInformationBlock(project, namespace);
		
		if (persistence.getDomain() == null && module != null) {
			String domain = module.getInformation().getDomain();
			persistence.setDomain(domain);
		}
		
		if (persistence.getNamespace() == null && module != null) {
			String namespace = module.getNamespace();
			persistence.setNamespace(namespace);
		}
		
		assureSources(persistence);
		assureUnits(persistence);
	}

	public void assureUnits(Persistence persistence) throws Exception {
		Collection<Unit> units = PersistenceUtil.getUnits(persistence);
		Iterator<Unit> iterator = units.iterator();
		while (iterator.hasNext()) {
			Unit unit = iterator.next();
			if (unit.getRef() != null) {
				String ref = unit.getRef();
				unit = context.findUnitByName(ref);
				Assert.notNull(unit, "Unit not found: "+ref);
			}
			assureUnit(persistence, unit);
		}
	}
	
	public void assureUnit(Persistence persistence, Unit unit) throws Exception {
//		if (unit.getSource() == null)
//			System.out.println();
		if (!StringUtils.isEmpty(unit.getSource().getRef())) {
			String name = unit.getSource().getRef();
			Source source = PersistenceUtil.getSourceByName(persistence, name);
			Assert.notNull(source, "Data source not found: "+name);
			unit.setSource(source);
		}		
		Assert.notNull(unit.getSource(), "Data source must be specified");
		
		Collection<Unit> units = PersistenceUtil.getUnits(persistence);
		if (unit.getNamespace() == null && units.size() == 1) {
			String uri = context.getModule().getNamespace();
			Namespace namespace = context.getNamespaceByUri(uri);
			//TODO do we need this check to be here? - so far YES
			Assert.notNull(namespace, "Namespace not found: "+uri);
			unit.setNamespace(namespace);
		}

		if (unit.getNamespace() == null) {
			String uri = null;
			if (context.getModule() != null) {
				uri = context.getModule().getNamespace();
				
			} else {
				List<Include> includes = ElementUtil.getIncludes(unit.getElements());
				Iterator<Include> iterator = includes.iterator();
				while (iterator.hasNext()) {
					Include include = iterator.next();
					uri = include.getNamespace();
					if (uri != null)
						break;
				}
				
			}

			Assert.notNull(uri, "Cannot establish namespace for unit: "+unit.getName());
			//TODO do we need this check to be here? - so far YES
			Namespace namespace = context.getNamespaceByUri(uri);
			Assert.notNull(namespace, "Namespace not found: "+uri);
			unit.setNamespace(namespace);
		}
		
		assureSource(persistence, unit);
		assureAdapter(persistence, unit);
		assureElements(persistence, unit);
		
		List<Property> properties = UnitUtil.getProperties(unit);
		PropertyUtil.applyDefault(properties, "generate-mappings", true);
	}

	public void assureAdapter(Persistence persistence, Unit unit) throws Exception {
		Adapter adapter = unit.getAdapter();
		if (adapter.getRef() != null) {
			String ref = adapter.getRef();
			adapter = PersistenceUtil.getAdapter(persistence, ref);
			Assert.notNull(adapter, "Adapter not found: "+ref);
			unit.setAdapter(adapter);
		}
		Assert.notNull(adapter.getName(), "Adapter name must be specified");
		Assert.notNull(adapter.getClassName(), "Adapter className must be specified");
		//TODO assure default properties here
	}

	//TODO some things here need to go into the Build phase and use AriesModelBuilder to do them
	public void assureElements(Persistence persistence, Unit unit) throws Exception {
		Elements elements = unit.getElements();
		Assert.notNull(elements, "Elements must be specified");
		List<Include> includes = ElementUtil.getIncludes(elements);
		Iterator<Include> iterator = includes.iterator();
		while (iterator.hasNext()) {
			Include includeObject = iterator.next();

			if (includeObject.getNamespace() != null) {
				String namespaceUri = includeObject.getNamespace();
				Namespace namespace = context.getNamespaceByUri(namespaceUri);
				UnitUtil.addElementsFromNamespace(unit, namespace);
				//List<Element> list = NamespaceUtil.getElements(namespace);
				//elements.getIncludesAndClazzsAndTypes().addAll(list);
			}
			
			if (includeObject.getFile() != null) {
				String fileName = includeObject.getFile();
				String filePath = context.getRuntimeLocation() + "/" + fileName;
				
				if (filePath.endsWith("xsd")) {
					//assuming only one namespace per XSD file
					Collection<Namespace> namespaces = engine.createNamespaces(filePath);
					assureNamespacesIncluded(namespaces, true);
					Namespace namespace = namespaces.iterator().next();
					UnitUtil.addElementsFromNamespace(unit, namespace);					
					//List<Element> list = NamespaceUtil.getElements(namespace);
					//elements.getIncludesAndClazzsAndTypes().addAll(list);
					
				} else if (filePath.endsWith("xml") || filePath.endsWith("aries")) {
					Project model = reader.readInformationProjectFromFileSystem(filePath);
					Assert.notNull(model, "Model file not found: "+filePath);
					List<Namespace> namespaces = ProjectUtil.getNamespaces(model);
					Assert.notNull(namespaces, "Namespaces must be specified: "+filePath);
					Assert.notEmpty(namespaces, "No namespace found in model: "+filePath);
					Iterator<Namespace> namespaceIterator = namespaces.iterator();
					while (iterator.hasNext()) {
						Namespace namespace = namespaceIterator.next();
						UnitUtil.addElementsFromNamespace(unit, namespace);
						//List<Element> list = NamespaceUtil.getElements(namespace);
						//elements.getIncludesAndClazzsAndTypes().addAll(list);
					}
				}
			}
		}

//		List<Class> classes = ElementUtil.getClasses(elements);
//		Iterator<Class> typeIterator = types.iterator();
//		while (iterator.hasNext()) {
//			Class element = typeIterator.next();
//			//context.getElementByQNameCache().put(TypeUtil.getTypeFromEPackageAndEClass(ePackage, eClass), eClass);
//			//context.getElementByJavaTypeCache().put(packageName+"."+eClass.getName(), eClass);
//			//elements.getIncludesAndClazzsAndTypes().add(type);
//		}
		
//		Assert.notEmpty(types, "No elements no found");
	}

	public void assureNamespacesImported(Collection<Namespace> namespaces, boolean imported) {
		NamespaceUtil.setImported(namespaces, imported);
	}

	public void assureNamespacesIncluded(Collection<Namespace> namespaces, boolean included) {
		NamespaceUtil.setIncluded(namespaces, included);
	}

	public void assureSources(Project project) throws Exception {
		List<Persistence> persistenceBlocks = ProjectUtil.getPersistenceBlocks(project);
		assureSources(persistenceBlocks);
	}
	
	public void assureSources(List<Persistence> persistenceBlocks) throws Exception {
		Iterator<Persistence> iterator = persistenceBlocks.iterator();
		while (iterator.hasNext()) {
			Persistence persistence = iterator.next();
			assureSources(persistence);
		}
	}

	public void assureSources(Persistence persistence) throws Exception {
		List<Source> sources = PersistenceUtil.getSources(persistence);
		Iterator<Source> iterator = sources.iterator();
		while (iterator.hasNext()) {
			Source source = iterator.next();
			if (source.getRef() != null) {
				String ref = source.getRef();
				source = context.findSourceByName(ref);
				Assert.notNull(source, "Data source not found: "+ref);
			}
			assureSource(persistence, source);
		}
	}

	public void assureSource(Persistence persistence, Unit unit) throws Exception {
		assureSource(persistence, unit.getSource());
	}
	
	public void assureSource(Persistence persistence, Source source) throws Exception {
		String sourceName = source.getName();
		Assert.notEmpty(sourceName, "Source name must be specified");
		if (StringUtils.isEmpty(source.getStore()) && sourceName.toLowerCase().endsWith("ds")) {
			String baseName = sourceName.substring(0, sourceName.length()-2);
			String storeName = baseName.toLowerCase() + "db";
			source.setStore(storeName);
		}

//		if (source.getAdapter() == null)
//			source.setAdapter(PersistenceUtil.getAdapters(persistence).get(0));
//		if (source.getName() == null)
//			source.setName(source.getAlias());
//		Assert.notNull(source.getName(), "Name or alias must be specified.");
//		Assert.notNull(source.getAdapter(), "Adapter must be specified.");
//		Assert.notNull(source.getConnectionUrl(), "Connection URL must be specified.");
//		Assert.notNull(source.getContextFactory(), "Context-factory must be specified.");
//		Assert.notNull(source.getDriverClass(), "Driver-class must be specified: ");
	}
	
	
	/*
	 * Messaging LAYER
	 * ---------------
	 */
	
	public void assureMessagingBlocks(Project project) throws Exception {
		List<Messaging> messagingBlocks = ProjectUtil.getMessagingBlocks(project);
//		if (messagingBlocks.size() == 0)
//			ProjectUtil.addMessagingBlock(project, MessagingUtil.newMessaging());
		messagingBlocks = ProjectUtil.getMessagingBlocks(project);
		assureMessagingBlocks(messagingBlocks);
	}
	
	public void assureMessagingBlocks(List<Messaging> messagingBlocks) throws Exception {
		Iterator<Messaging> iterator = messagingBlocks.iterator();
		while (iterator.hasNext()) {
			Messaging messaging = iterator.next();
			assureMessaging(messaging);
		}
	}
	
//	public void assureMessaging(Project project) throws Exception {
//		if (project.getMessaging() == null)
//			project.setMessaging(MessagingUtil.newMessaging());
//		assureMessaging(project.getMessaging());
//	}

	public void assureMessaging(Messaging messaging) throws Exception {
		assureImports(MessagingUtil.getImports(messaging));
		assureLinks(messaging);
		assureTransports(messaging);
	}
	
	public void assureLinks(Messaging messaging) throws Exception {
		assureTransports(messaging);
		List<Link> links = MessagingUtil.getLinks(messaging);
		Iterator<Link> iterator = links.iterator();
		while (iterator.hasNext()) {
			Link link = iterator.next();
			assureTransports(messaging, link);
		}
	}

	public void assureTransports(Messaging messaging, Link link) throws Exception {
		Map<String, Transport> map = MessagingUtil.getTransportsByNameMap(messaging);
		List<Transport> transports = LinkUtil.getTransports(link);
		Iterator<Transport> iterator = transports.iterator();
		while (iterator.hasNext()) {
			Transport transport = iterator.next();
			if (!StringUtils.isEmpty(transport.getRef())) {
				Transport declaredTransport = map.get(transport.getRef());
				LinkUtil.removeTransport(link, transport);
				Transport transport2 = ObjectUtil.cloneObject(declaredTransport);
				LinkUtil.addTransport(link, transport2);
				
			} else {
				Assert.notNull(transport.getType(), "Transport type not found");
				Assert.notNull(transport.getName(), "Transport name not found");
			}
		}
	}

	public void assureTransports(Messaging messaging) throws Exception {
		List<Transport> transports = MessagingUtil.getTransports(messaging);
		Iterator<Transport> iterator = transports.iterator();
		while (iterator.hasNext()) {
			Transport transport = iterator.next();
			if (transport instanceof RmiTransport)
				transport.setType(TransportType.RMI);
			if (transport instanceof EjbTransport)
				transport.setType(TransportType.EJB);
			if (transport instanceof HttpTransport)
				transport.setType(TransportType.HTTP);
			if (transport instanceof JmsTransport)
				transport.setType(TransportType.JMS);
		}
	}

	
	/*
	 * View LAYER
	 * ----------
	 */
	
	public void assureViewBlock(View view) throws Exception {
		if (view.getRef() != null) {
			String ref = view.getRef();
			View referencedBlock = context.findViewBlockByName(ref);
			if (enforceChecks)
				Assert.notNull(referencedBlock, "View not found: "+ref);
			if (referencedBlock != null)
				ExtensionsUtil.mergeViewBlocks(view, referencedBlock);
		}
		assureImports(ViewUtil.getImports(view));
		//TODO assure other elements
	}
	
	
	/*
	 * Information LAYER (mode)
	 * ------------------------
	 */

	public void validateAndAssure(Information model) throws Exception {
		assureNamespaceImports(model);
		assureNamespaces(model);
	}

	public void assureNamespaceImports2(Collection<Information> informationBlocks) throws Exception {
		Iterator<Information> iterator = informationBlocks.iterator();
		while (iterator.hasNext()) {
			Information information = iterator.next();
			assureNamespaceImports(information);
		}
	}
	
	public void assureNamespaceImports(Information model) throws Exception {
		List<Import> importedFiles = InformationUtil.getImports(model);
		assureNamespaceImports(importedFiles);
		
//		List<Import> importedFiles = InformationUtil.getImports(model);
//		List<Namespace> importedNamespaces = assureNamespaceImports(importedFiles);
//		//model.getImportsAndNamespaces().addAll(importedNamespaces);
//		List<Namespace> namespaces = InformationUtil.getNamespaces(model);
//		Iterator<Namespace> namespaceIterator = namespaces.iterator();
//		while (namespaceIterator.hasNext()) {
//			Namespace namespace = namespaceIterator.next();
//			NamespaceUtil.addImportedNamespaces(namespace, importedNamespaces);
//			//model.getImportsAndNamespaces().addAll(importedNamespaces);
//			//model.getImportsAndNamespaces().add(namespace);
//		}
	}
	
	public List<Namespace> assureNamespaceImports(List<Import> importedFiles) throws Exception {
		List<Namespace> importedNamespaces = new ArrayList<Namespace>();
		Iterator<Import> iterator = importedFiles.iterator();
		while (iterator.hasNext()) {
			Import importedFile = iterator.next();
			String fileName = importedFile.getFile();
			String parentDirectory = importedFile.getDir();
			try {
				String parentDirectory2 = null;
				if (fileName.startsWith("/"))
					parentDirectory2 = FileUtil.normalizePath(context.getCacheLocation() + "/model");
				else if (parentDirectory != null)
					parentDirectory2 = parentDirectory;
				else parentDirectory2 = context.getRuntimeFileContext();

//				if (parentDirectory == null)
//					parentDirectory = FileUtil.normalizePath(context.getCacheLocation() + "/model");
//				String parentDirectory2 = FileUtil.normalizePath(context.getCacheLocation() + "/model");
				
				String filePath = context.getFilePath(parentDirectory2, null, fileName);
				//String filePath = context.getRuntimeFilePath(directory, fileName);
				//String filePath = context.getFilePath(context.getCacheLocation() + "/model", null, fileName);
				Collection<Namespace> namespaces = engine.createNamespaces(filePath);
				assureNamespacesIncluded(namespaces, importedFile.getInclude());
				
				//set imported flag
				//NamespaceUtil.setImported(namespaces);
				String modelFileContext = context.getModelFileContext();
				
				//FIX THIS
				String cacheLocation = context.getCacheLocation();
				String runtimeFileContext = context.getRuntimeFileContext();
				String modelLocation = context.getModelLocation();
				
				String fileContext = modelFileContext.replace(modelLocation, "");
				Iterator<Namespace> iterator2 = namespaces.iterator();
				while (iterator2.hasNext()) {
					Namespace namespace = iterator2.next();
					if (!namespace.getFilePath().contains(fileContext)) {
						namespace.setImported(Boolean.TRUE);
					}
				}
				
				populateElements(namespaces);
				//NamespaceUtil.setProperty(namespaces, "imported");
				if (importedFile.getDir() == null) {
					//if (fileName.startsWith("/"))
					//	importedFile.setDir(context.getCacheLocation() + "/model");
					//else importedFile.setDir(parentDirectory2);
					importedFile.setDir(parentDirectory2);
				}
				importedNamespaces.addAll(namespaces);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		return importedNamespaces;
	}

//	public void validateAndAssure(Persistence model) throws Exception {
//		List<Import> importedFiles = PersistenceUtil.getImports(model);
//		List<Namespace> importedNamespaces = assureNamespaceImports(importedFiles);
//		assureNamespaceImports(model);
//		assureNamespaces(model);
//	}
//	
//	public void assureNamespaceImports(Persistence model) throws Exception {
//		List<Import> importedFiles = PersistenceUtil.getImports(model);
//		List<Namespace> importedNamespaces = assureNamespaceImports(importedFiles);
//		
//		//model.getImportsAndNamespaces().addAll(importedNamespaces);
//		List<Namespace> namespaces = PersistenceUtil.getNamespaces(model);
//		Iterator<Namespace> namespaceIterator = namespaces.iterator();
//		while (namespaceIterator.hasNext()) {
//			Namespace namespace = namespaceIterator.next();
//			namespace.getImports().addAll(importedNamespaces);
//			//model.getImportsAndNamespaces().addAll(importedNamespaces);
//			//model.getImportsAndNamespaces().add(namespace);
//		}
//	}
	
	
	public void processImportedModels(Project project, Collection<Import> importedFiles) throws Exception {
		List<Import> tmpImportedFiles = new ArrayList<Import>(importedFiles);
		assureImports(tmpImportedFiles);
		List<Namespace> importedNamespaces = assureNamespaceImports(tmpImportedFiles);
		//TODO is there no need to do anything with imported namespaces ?
		Iterator<Import> iterator = tmpImportedFiles.iterator();
		while (iterator.hasNext()) {
			Import importedFile = iterator.next();
			processImportedModel(project, importedFile);
		}
	}
	
	public void processImportedModel(Project project, Import importedFile) throws Exception {
		assureImport(importedFile);
		if (project != null)
			ProjectUtil.addImport(project, importedFile);
		
		// no need to proceed further for any "foreign" imports (i.e. not ariel nor aries)
		String type = importedFile.getType();
		if (type != null) {
			if (type.equals("xsd"))
				return;
			if (type.equals("wsdl"))
				return;
			if (type.equals("bpel"))
				return;
		}
		
		String fileName = importedFile.getFile();
		String parentDirectory = importedFile.getDir();
		if (parentDirectory == null)
			parentDirectory = FileUtil.normalizePath(context.getCacheLocation() + "/model");

		String filePath = null;
		try {
			filePath = context.getFilePath(parentDirectory, null, fileName);
		} catch (FileNotFoundException e) {
			filePath = context.getFilePath(context.getCacheLocation()+"/model", null, fileName);
		}
		
		//String filePath = context.getFilePath(context.getCacheLocation() + "/model", null, fileName);
		//String filePath = context.getRuntimeFilePath(fileContext, fileName);
		//String modelName = extractModelNameFromFileName(fileName);
		Serializable object = reader.readModelFromFileSystem(filePath);
		if (context.getPlaceholders() != null && !context.containsPlaceholder(object))
			context.addPlaceholder(object);
		importedFile.setObject(object);
		
		if (object instanceof Applications) {
			processImportedApplicationsModel(project, (Applications) object);
		//} else if (object instanceof Application) {
		//	processApplicationModel(project, (Modules) object);
		} else if (object instanceof Modules) {
			processImportedModulesModel(project, (Modules) object);
		} else if (object instanceof Services) {
			processImportedServicesModel(project, (Services) object);
		} else if (object instanceof Information) {
			processImportedInformationModel(project, (Information) object, null);
		} else if (object instanceof Persistence) {
			processImportedPersistenceModel(project, (Persistence) object, null);
		} else if (object instanceof Messaging) {
			processImportedMessagingModel(project, (Messaging) object);
		} else if (object instanceof View) {
			processImportedViewModel(project, (View) object);
		}
	}
	
//	protected String extractModelNameFromFileName(String fileName) {
//		String modelName = null;
//		int indexOfSlash = fileName.indexOf("/");
//		if (indexOfSlash != -1) {
//			modelName = fileName.substring(indexOfSlash+1);
//		} else {
//			modelName = fileName;
//		}
//		modelName = NameUtil.getBaseName(modelName);
//		return modelName;
//	}

	public void processProjectImports(Project project) throws Exception {
		processImportedModels(project, ProjectUtil.getImports(project));
	}

	public void processProjectImports(Project project, Information model) throws Exception {
		processImportedModels(project, InformationUtil.getImports(model));
	}
	
	public void processProjectImports(Project project, Persistence model) throws Exception {
		processImportedModels(project, PersistenceUtil.getImports(model));
		Collection<Namespace> namespaces = context.getNamespaces();
		PersistenceUtil.addNamespaces(model, namespaces);
	}
	
	public void buildProjectModules(Project project) throws Exception {
		context.initializeContext(project);
		builder.buildProjectModules(project);
		//assureProjectModel(model);
	}

	public void buildProjectModules(Project project, Information model) throws Exception {
		context.initializeContext(project);
		Collection<Module> modelModules = builder.buildProjectModelModules(project, model);
		ModuleUtil.addModules(project, modelModules);
		assureProjectObjects(project, model);
	}

	public void buildProjectModules(Project project, Persistence model) throws Exception {
		context.initializeContext(project);
		Collection<Module> dataModules = builder.buildProjectDataModules(project, model);
		ModuleUtil.addModules(project, dataModules);
		assureProjectObjects(project, model);
	}
	
	public void assureProjectObjects(List<Project> projects) throws Exception {
		Iterator<Project> iterator = projects.iterator();
		while (iterator.hasNext()) {
			Project project = iterator.next();
			assureProjectObjects(project);
		}
	}	

	public void assureProjectObjects(Project project) throws Exception {
		assureProjectObjects(project, false);
		assureElementRelationships(project);
	}
	
	public void assureProjectObjects(Project project, boolean enforceChecks) throws Exception {
		assureProject(project);
		assureExtensions(project);
		assureNamespaces(project);
		assureApplications(project);
		assureDomains(project);
		assureModules(project);
		assureProcesses(project);
		assureServices(project);
	}

	public void assureProjectObjects(Project project, Information model) throws Exception {
		assureInformationBlock(project, model);
		assureElementRelationships(project, model);
	}

	public void assureElementRelationships(Project project) throws Exception {
		Collection<Information> informationBlocks = ProjectUtil.getInformationBlocks(project);
		Iterator<Information> iterator = informationBlocks.iterator();
		while (iterator.hasNext()) {
			Information information = iterator.next();
			assureElementRelationships(project, information);
		}
	}

	public void assureElementRelationships(Project project, Information model) throws Exception {
		Collection<Namespace> projectNamespaces = ProjectUtil.getNamespaces(project);
		Collection<Namespace> namespaces = model.getNamespaces();
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			assureElementRelationships(project, model, namespace);
		}
	}

	public void assureElementRelationships(Project project, Information model, Namespace namespace) throws Exception {
		Collection<Element> elements = NamespaceUtil.getElements(namespace);
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			assureElementRelationships(project, model, element);
		}
	}
	
	public void assureElementRelationships(Project project, Information model, Element element) {
		Collection<ReferencedBy> referencedByElements = getReferencedByElements(project, model, element);
		Collection<ContainedBy> containedByElements = getContainedByElements(project, model, element);
		element.addToReferencedBy(referencedByElements);
		element.addToContainedBy(containedByElements);
	}

	public Collection<ReferencedBy> getReferencedByElements(Project project, Information model, Element element) {
		Set<ReferencedBy> set = new HashSet<ReferencedBy>();
		Collection<Element> elements = ElementUtil.getElements(project);
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element owner = iterator.next();
			Set<Reference> references = ElementUtil.getReferences(owner, element);
			Iterator<Reference> iterator2 = references.iterator();
			while (iterator2.hasNext()) {
				Reference reference = iterator2.next();
				ReferencedBy referencedBy = new ReferencedBy();
				referencedBy.setType(owner.getType());
				referencedBy.setName(owner.getName());
				referencedBy.setStructure(reference.getStructure());
				set.add(referencedBy);
				break;
			}
		}
		return set;
	}

	public Collection<ContainedBy> getContainedByElements(Project project, Information model, Element element) {
		Set<ContainedBy> set = new HashSet<ContainedBy>();
		Collection<Element> elements = ElementUtil.getElements(project);
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element owner = iterator.next();
			Set<Reference> references = ElementUtil.getReferences(owner, element);
			Iterator<Reference> iterator2 = references.iterator();
			while (iterator2.hasNext()) {
				Reference reference = iterator2.next();
				boolean isContained = FieldUtil.isContained(reference);
				if (isContained) {
					ContainedBy containedBy = new ContainedBy();
					containedBy.setType(owner.getType());
					containedBy.setName(owner.getName());
					containedBy.setStructure(reference.getStructure());
					set.add(containedBy);
					break;
				}
			}
		}
		return set;
	}

	public void assureProjectObjects(Project project, Persistence model) throws Exception {
		assurePersistenceBlock(null, model);
	}
	
	public void processImportedApplicationsModel(Project project, Applications applications) throws Exception {
		processImportedModels(project, ApplicationUtil.getImports(applications));
		Collection<Application> list = ApplicationUtil.getApplications(applications);
		ProjectUtil.addApplications(project, list);
	}
	
	public void processImportedModulesModel(Project project, Modules modules) throws Exception {
		processImportedModels(project, ModuleUtil.getImports(modules));
		//assureModules(project, ModuleUtil.getModules(model));
		processServicesModel(project, ModuleUtil.getServices(modules));
		processDomainsModel(project, ModuleUtil.getDomains(modules));
		
		Set<Module> set = ModuleUtil.getModules(modules);
		Iterator<Module> iterator = set.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			processModuleModel(project, module);
		}
	}

	public void processModuleModel(Project project, Module module) throws Exception {
		if (module.getRef() == null)
			context.addImportedModule(module);
		Services services = module.getServices();
	}
	
	public void processImportedServicesModel(Project project, Services services) throws Exception {
		List<Service> list = ServicesUtil.getServices(services);
		processServicesModel(project, list);
	}
	
	public void processServicesModel(Project project, List<Service> services) throws Exception {
		Iterator<Service> iterator = services.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			if (service.getRef() == null)
				context.addImportedService(service);
		}
	}
	
	public void processDomainModel(Project project, Domain domain) throws Exception {
		List<Service> list = DomainUtil.getServices(domain);
		processServicesModel(project, list);
	}
	
	public void processDomainsModel(Project project, Collection<Domain> domains) throws Exception {
		Iterator<Domain> iterator = domains.iterator();
		while (iterator.hasNext()) {
			Domain domain = iterator.next();
			processDomainModel(project, domain);
		}
	}
	
	public void processImportedInformationModel(Project project, Information information, String name) throws Exception {
		List<Import> imports = InformationUtil.getImports(information);
//		List<Namespace> namespaces = InformationUtil.getNamespaces(information);
		processImportedModels(project, imports);
		information.setImported(true);
		if (information.getName() == null)
			information.setName(name);
		if (project != null)
			ProjectUtil.addInformationBlock(project, information);
		assureNamespaces(information);
		//context.populateNamespaces(information.getNamespaces());
		
//		if (project != null) {
//			InformationUtil.addImports(project, imports);
//			InformationUtil.addNamespaces(project, namespaces);
//		}
	}
	
	public void processImportedPersistenceModel(Project project, Persistence persistence, String name) throws Exception {
		processImportedModels(project, PersistenceUtil.getImports(persistence));
		if (persistence.getName() == null)
			persistence.setName(name);
		Assert.notNull(persistence.getName(), "Persistence block name must exist");
		persistence.setImported(true);
		if (project != null)
			ProjectUtil.addPersistenceBlock(project, persistence);
		assureNamespaces(persistence);
//		if (project != null) {
//			PersistenceUtil.addImports(project, PersistenceUtil.getImports(persistence));
//			PersistenceUtil.addNamespaces(project, PersistenceUtil.getNamespaces(persistence));
//			PersistenceUtil.addUnits(project, PersistenceUtil.getUnits(persistence));
//		}
	}

	public void processImportedMessagingModel(Project project, Messaging messaging) throws Exception {
		processImportedModels(project, MessagingUtil.getImports(messaging));
		Assert.notNull(messaging.getName(), "Messaging block name must exist");
		messaging.setImported(true);
		if (project != null)
			ProjectUtil.addMessagingBlock(project, messaging);
//		if (project != null) {
//			MessagingUtil.addImports(project, MessagingUtil.getImports(messaging));
//			MessagingUtil.addChannels(project, MessagingUtil.getChannels(messaging));
//			MessagingUtil.addLinks(project, MessagingUtil.getLinks(messaging));
//			MessagingUtil.addTransports(project, MessagingUtil.getTransports(messaging));
//			MessagingUtil.addProviders(project, MessagingUtil.getProviders(messaging));
//		}
	}
	
	public void processImportedViewModel(Project project, View view) throws Exception {
		processImportedModels(project, ViewUtil.getImports(view));
		Assert.notNull(view.getName(), "View block name must exist");
		//TODO view.setImported(true);
		if (project != null)
			ProjectUtil.addViewBlock(project, view);
	}
	
}
