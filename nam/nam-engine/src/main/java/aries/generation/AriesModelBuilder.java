package aries.generation;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.wsdl.PortType;
import javax.xml.namespace.QName;

import nam.ProjectLevelHelper;
import nam.model.Annotation;
import nam.model.Application;
import nam.model.Attribute;
import nam.model.Channel;
import nam.model.Dependencies;
import nam.model.Domain;
import nam.model.Element;
import nam.model.Enumeration;
import nam.model.Execution;
import nam.model.Fault;
import nam.model.Field;
import nam.model.Import;
import nam.model.Information;
import nam.model.Input;
import nam.model.Invocation;
import nam.model.Literal;
import nam.model.Module;
import nam.model.ModuleLevel;
import nam.model.ModuleType;
import nam.model.Modules;
import nam.model.Namespace;
import nam.model.Operation;
import nam.model.Output;
import nam.model.Parameter;
import nam.model.Persistence;
import nam.model.Process;
import nam.model.ProcessState;
import nam.model.Project;
import nam.model.Reference;
import nam.model.Result;
import nam.model.Service;
import nam.model.Services;
import nam.model.Transacted;
import nam.model.TransactionScope;
import nam.model.TransactionUsage;
import nam.model.Type;
import nam.model.util.ApplicationUtil;
import nam.model.util.DomainUtil;
import nam.model.util.ElementUtil;
import nam.model.util.ExtensionsUtil;
import nam.model.util.FieldUtil;
import nam.model.util.InformationUtil;
import nam.model.util.MapUtil;
import nam.model.util.MessagingUtil;
import nam.model.util.ModuleFactory;
import nam.model.util.ModuleUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.PersistenceUtil;
import nam.model.util.ProjectUtil;
import nam.model.util.ServiceUtil;
import nam.model.util.ServicesUtil;
import nam.model.util.TypeUtil;
import nam.service.ServiceLayerHelper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.util.NameUtil;
import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.model.Variables;
import org.eclipse.bpel.model.resource.BPELResource;
import org.eclipse.bpel.model.resource.BPELResourceImpl;
import org.eclipse.bpel.model.util.ImportResolver;
import org.eclipse.bpel.model.util.ImportResolverRegistry;
import org.eclipse.emf.codegen.util.CodeGenUtil;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.EcoreFactoryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.wst.wsdl.BindingFault;
import org.eclipse.wst.wsdl.BindingOperation;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.wst.wsdl.Part;
import org.eclipse.wst.wsdl.Port;
import org.eclipse.wst.wsdl.WSDLPackage;
import org.eclipse.wst.wsdl.util.WSDLResourceImpl;
import org.eclipse.xsd.XSDImport;
import org.eclipse.xsd.XSDPackage;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.eclipse.xsd.XSDTypeDefinition;

import aries.bpel.BPELUtil;
import aries.codegen.MethodType;
import aries.codegen.SourceType;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;


public class AriesModelBuilder {

	private static Log log = LogFactory.getLog(AriesModelBuilder.class);

	private GenerationContext context;
	
	private AriesModelFactory factory;  
	
	private AriesModelUtil util;
	
	
	public AriesModelBuilder(GenerationContext context) {
		this.util = new AriesModelUtil(context);
		this.context = context;
	}

	public void setFactory(AriesModelFactory factory) {
		this.factory = factory;
	}

	public ResourceSet buildFromFile(List<URL> resourceUrls) {
		
		return null;
	}
	
	public Project buildProject(ResourceSet emfResourceSet) throws Exception {
		Project project = new Project();
		initializeProject(project, emfResourceSet);
		return project;
	}
	
	public void buildProjectModules(Project project) throws Exception {
		//first determine the parent modules and build them
		Collection<Application> applications = ProjectUtil.getApplications(project);
		if (applications.size() == 1) {
			Application application = applications.iterator().next();
			if (application.getName().equalsIgnoreCase(project.getName()))
				ProjectUtil.addModule(project, buildProjectAppPOMModule(application));
			else ProjectUtil.addModule(project, buildProjectPOMModule(project));
		} else ProjectUtil.addModule(project, buildProjectPOMModule(project));
		
		ProjectUtil.addModules(project, buildProjectModelModules(project));
		//ProjectUtil.addModule(project, buildModelModulesFromPlaceholders(placeholders));
		
		if (applications.size() == 0)
			ProjectUtil.addModules(project, buildClientModules(project));

		Iterator<Application> applicationIterator = applications.iterator();
		while (applicationIterator.hasNext()) {
			Application application = applicationIterator.next();
			ApplicationUtil.addModules(application, buildServiceModules(project, application));
			ApplicationUtil.addModules(application, buildClientModules(project, application));
			ApplicationUtil.addModules(application, buildViewModules(project, application));
			if (ApplicationUtil.getEarModule(application) == null)
				ApplicationUtil.addModule(application, buildEARModule(project, application));
		}
	}
	
	public Collection<Module> buildServiceModules(Project project, Application application) {
		Module localServiceModule = null;
		Collection<Module> newServiceModules = new HashSet<Module>();
		Collection<Module> currentServiceModules = ApplicationUtil.getServiceModules(application);
		Iterator<Module> iterator = currentServiceModules.iterator();
		while (iterator.hasNext()) {
			Module serviceModule = iterator.next();
			if (serviceModule.getNamespace().equals(application.getNamespace())) {
				localServiceModule = serviceModule;
				break;
			}
		}
		
		//TODO - 060315 - took this out - we not want this anymore
		//if (localServiceModule == null) {
		//	Services services = null;
		//	localServiceModule = buildServiceModule(project, application, services);
		//	newServiceModules.add(localServiceModule);
		//}

		return newServiceModules;
	}

	public Module buildServiceModule(Project project, Application application, Services services) {
		Module module = new Module();
		module.setType(ModuleType.CLIENT);
		module.setLevel(ModuleLevel.APPLICATION_LEVEL);
		module.setGroupId(application.getGroupId());
		//TODO - Externalize these properly
		module.setArtifactId(application.getArtifactId() + "-client");
		module.setName(application.getArtifactId() + "-client");
		module.setNamespace(application.getNamespace());
		Namespace namespace = context.getNamespaceByUri(module.getNamespace());
		Assert.notNull(namespace, "Namespace not found: "+module.getNamespace());

		module.setServices(services);
		//assuming domains have already been created and populated
		List<Domain> domains = ServicesUtil.getDomains(services);
		Iterator<Domain> iterator = domains.iterator();
		while (iterator.hasNext()) {
			Domain domain = iterator.next();
			
		}
		return module;
	}
	
	public Collection<Module> buildClientModules(Project project) {
		Set<Module> clientModules = new HashSet<Module>();
		Set<Module> serviceModules = ProjectUtil.getServiceModules(project);
		Iterator<Module> iterator = serviceModules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			Services services = module.getServices();
			clientModules.add(buildClientModule(project, services));
		}
		return clientModules;
	}

	public Module buildClientModule(Project project, Services services) {
		//String groupId = NamespaceUtil.getGroupIdFromNamespace(namespace);
		String groupId = project.getDomain();
		
		Module module = new Module();
		module.setType(ModuleType.CLIENT);
		module.setLevel(ModuleLevel.APPLICATION_LEVEL);
		module.setGroupId(groupId);
		//TODO - Externalize these properly
		module.setArtifactId(project.getName() + "-client");
		module.setName(project.getName() + "-client");
		module.setNamespace(project.getNamespace());
		Namespace namespace = context.getNamespaceByUri(module.getNamespace());
		Assert.notNull(namespace, "Namespace not found for module: "+ModuleUtil.getModuleId(module));

		module.setServices(services);
		//assuming domains have already been created and populated
		List<Domain> domains = ServicesUtil.getDomains(services);
		Iterator<Domain> iterator = domains.iterator();
		while (iterator.hasNext()) {
			Domain domain = iterator.next();
			
		}
		return module;
	}

	public Collection<Module> buildClientModules(Project project, Application application) {
		Collection<Module> clientModules = new HashSet<Module>();
		Collection<Module> serviceModules = ApplicationUtil.getServiceModules(application);
		Iterator<Module> iterator = serviceModules.iterator();
		while (iterator.hasNext()) {
			Module serviceModule = iterator.next();
			Services services = serviceModule.getServices();
			clientModules.add(buildClientModule(project, application, services));
		}
		return clientModules;
	}

	public Module buildClientModule(Project project, Application application, Services services) {
		Module module = new Module();
		module.setType(ModuleType.CLIENT);
		module.setLevel(ModuleLevel.APPLICATION_LEVEL);
		module.setGroupId(application.getGroupId());
		//TODO - Externalize these properly
		module.setArtifactId(application.getArtifactId() + "-client");
		module.setName(application.getArtifactId() + "-client");
		module.setNamespace(application.getNamespace());
		Namespace namespace = context.getNamespaceByUri(module.getNamespace());

		module.setServices(services);
		//assuming domains have already been created and populated
		List<Domain> domains = ServicesUtil.getDomains(services);
		Iterator<Domain> iterator = domains.iterator();
		while (iterator.hasNext()) {
			Domain domain = iterator.next();
			
		}
		return module;
	}

	public Collection<Module> buildViewModules(Project project, Application application) {
		Collection<Module> viewModules = ApplicationUtil.getViewModules(application);
		if (viewModules.size() == 0) {
			viewModules = new HashSet<Module>();
			//if needed, build the default view module
			Collection<Module> serviceModules = ApplicationUtil.getServiceModules(application);
			Iterator<Module> iterator = serviceModules.iterator();
			while (iterator.hasNext()) {
				Module serviceModule = iterator.next();
				if (serviceModule.getNamespace().equals(application.getNamespace())) {
					Services services = serviceModule.getServices();
					viewModules.add(buildDefaultViewModule(project, application, services));
				}
			}
		}
		return viewModules;
	}

	public Module buildDefaultViewModule(Project project, Application application, Services services) {
		Module module = new Module();
		module.setType(ModuleType.VIEW);
		module.setLevel(ModuleLevel.APPLICATION_LEVEL);
		module.setGroupId(application.getGroupId());
		//TODO - Externalize these properly
		module.setArtifactId(application.getArtifactId() + "-view");
		module.setName(application.getArtifactId() + "-view");
		module.setNamespace(application.getNamespace());
		//Namespace namespace = context.getNamespaceByUri(module.getNamespace());

		Namespace namespace = context.getNamespaceByUri(module.getNamespace());
		Information information = ProjectUtil.getInformationBlock(project, namespace);
		//InformationUtil.addNamespace(information, namespace);
		module.setInformation(information);
		return module;

//		Information information = new Information();
//		List<Module> modelModules = ProjectUtil.getProjectModules(project, ModuleType.MODEL);
//		Iterator<Module> iterator = modelModules.iterator();
//		while (iterator.hasNext()) {
//			Module modelModule = iterator.next();
//			Namespace namespace = context.getNamespaceByUri(modelModule.getNamespace());
//			InformationUtil.addNamespace(information, namespace);
//		}
//		module.setInformation(information);
//		return module;
	}

	public Module buildEARModule(Project project, Application application) {
		Module module = new Module();
		module.setType(ModuleType.EAR);
		module.setLevel(ModuleLevel.APPLICATION_LEVEL);
		module.setGroupId(application.getGroupId());
		//TODO - Externalize these properly
		module.setArtifactId(application.getArtifactId() + "-app");
		module.setName(application.getArtifactId() + "-app");
		module.setNamespace(application.getNamespace());
		//Namespace namespace = context.getNamespaceByUri(module.getNamespace());

		Namespace namespace = context.getNamespaceByUri(module.getNamespace());
		Information information = ProjectUtil.getInformationBlock(project, namespace);
		//InformationUtil.addNamespace(information, namespace);
		module.setInformation(information);
		module.setVersion("0.0.1-SNAPSHOT");
		return module;

//		Information information = new Information();
//		List<Module> modelModules = ProjectUtil.getProjectModules(project, ModuleType.MODEL);
//		Iterator<Module> iterator = modelModules.iterator();
//		while (iterator.hasNext()) {
//			Module modelModule = iterator.next();
//			Namespace namespace = context.getNamespaceByUri(modelModule.getNamespace());
//			InformationUtil.addNamespace(information, namespace);
//		}
//		module.setInformation(information);
//		return module;
	}
	
	
	
	
	
	public void initializeProject(Project project, ResourceSet emfResourceSet) throws Exception {
		buildNamespaces(project, emfResourceSet);
		buildApplication(emfResourceSet.getResources());
	}
	
	public List<Namespace> buildNamespaces(Project project, ResourceSet emfResourceSet) throws Exception {
		List<Namespace> namespaces = new ArrayList<Namespace>();
		namespaces.addAll(buildNamespaces(emfResourceSet.getPackageRegistry()));
		namespaces.addAll(buildNamespaces(emfResourceSet.getResources()));
		return namespaces;
	}

	public List<Application> buildApplications(List<Resource> resources) throws Exception {
		Assert.notNull(resources, "Resources must exist");
		List<Application> applications = new ArrayList<Application>(); 
		Iterator<Resource> iterator = resources.iterator();
		while (iterator.hasNext()) {
			Resource resource = (Resource) iterator.next();
			if (resource instanceof WSDLResourceImpl) {
				WSDLResourceImpl wsdlResource = (WSDLResourceImpl) resource;
				Definition definition = wsdlResource.getDefinition();
				Application applicationFromDefinition = buildApplicationFromDefinition(definition);
				applications.add(applicationFromDefinition);
			}
			if (resource instanceof BPELResourceImpl) {
				BPELResourceImpl bpelResource = (BPELResourceImpl) resource;
				org.eclipse.bpel.model.Process process = bpelResource.getProcess();
				List<Application> applicationsFromProcess = buildApplicationsFromProcess(process);
				applications.addAll(applicationsFromProcess);
			}
		}
		return applications;
	}

	public Application buildApplication(List<Resource> resources) throws Exception {
		Application application = factory.createApplication();
		initializeApplicationFromResources(application, resources);
		return application;
	}
	
	public void initializeApplicationFromResources(Application application, List<Resource> resources) throws Exception {
		Assert.notNull(resources, "Resources must exist");
		Iterator<Resource> iterator = resources.iterator();
		while (iterator.hasNext()) {
			Resource resource = (Resource) iterator.next();
			if (resource instanceof WSDLResourceImpl) {
				WSDLResourceImpl wsdlResource = (WSDLResourceImpl) resource;
				Definition definition = wsdlResource.getDefinition();
				initializeApplicationFromDefinition(application, definition);
			}
			if (resource instanceof BPELResourceImpl) {
				BPELResourceImpl bpelResource = (BPELResourceImpl) resource;
				org.eclipse.bpel.model.Process process = bpelResource.getProcess();
				initializeApplicationFromProcess(application, process);
			}
		}
	}

	public List<Application> buildApplicationsFromProcess(org.eclipse.bpel.model.Process process) throws Exception {
		List<Application> applications = new ArrayList<Application>(); 
		EList<org.eclipse.bpel.model.Import> imports = process.getImports();
		Iterator<org.eclipse.bpel.model.Import> iterator = imports.iterator();
		while (iterator.hasNext()) {
			org.eclipse.bpel.model.Import importObject = (org.eclipse.bpel.model.Import) iterator.next();
			ImportResolver[] importResolvers = ImportResolverRegistry.INSTANCE.getResolvers(importObject.getImportType());
			for (ImportResolver importResolver : importResolvers) {
				List<Object> definitionObjects = importResolver.resolve(importObject, ImportResolver.RESOLVE_DEFINITION);
				//List<Object> schemaObjects = importResolver.resolve(importObject, ImportResolver.RESOLVE_SCHEMA);
				Iterator<Object> definitionIterator = definitionObjects.iterator();
				while (definitionIterator.hasNext()) {
					Definition definition = (Definition) definitionIterator.next();
					Application application = buildApplicationFromDefinition(definition);
					applications.add(application);
				}
			}
		}
		return applications;
	}

	public void initializeApplicationFromProcess(Application application, org.eclipse.bpel.model.Process bpelProcess) throws Exception {
		
		// build process from BPEL process
		Process process = buildProcessFromBPELProcess(bpelProcess);
		ApplicationUtil.addProcess(application, process);
		
		// add new process to services 
		Collection<Service> services = ApplicationUtil.getServices(application);
		Iterator<Service> iterator2 = services.iterator();
		while (iterator2.hasNext()) {
			Service service = iterator2.next();
			service.setProcess(process);
		}
		
		// process imports from BPEL process
		EList<org.eclipse.bpel.model.Import> imports = bpelProcess.getImports();
		Iterator<org.eclipse.bpel.model.Import> iterator = imports.iterator();
		while (iterator.hasNext()) {
			org.eclipse.bpel.model.Import importObject = iterator.next();
			ImportResolver[] importResolvers = ImportResolverRegistry.INSTANCE.getResolvers(importObject.getImportType());
			for (ImportResolver importResolver : importResolvers) {
				List<Object> definitionObjects = importResolver.resolve(importObject, ImportResolver.RESOLVE_DEFINITION);
				//List<Object> schemaObjects = importResolver.resolve(importObject, ImportResolver.RESOLVE_SCHEMA);
				Iterator<Object> definitionIterator = definitionObjects.iterator();
				while (definitionIterator.hasNext()) {
					Definition definition = (Definition) definitionIterator.next();
					initializeApplicationFromDefinition(application, definition);
				}
			}
		}
	}
	
	public Process buildProcessFromBPELProcess(org.eclipse.bpel.model.Process bpelProcess) throws Exception {
		String name = bpelProcess.getName();
		Project project = context.getProject();
		String domain = project.getDomain();
		
		Process process = new Process();
		process.setName(name);
		process.setNamespace("http://"+domain+"/"+name);
		process.setStateful(true);

		Transacted transacted = new Transacted();
		process.setTransacted(transacted);

		ProcessState processState = new ProcessState();
		process.setState(processState);

//		BPELFactory bpelFactory = BPELFactoryImpl.init();
//		org.eclipse.bpel.model.Process bpelProcess = bpelFactory.createProcess();
//		BPELRuntimeCache.INSTANCE.addProcess(bpelProcess);
//		bpelProcess.setTargetNamespace(module.getNamespace());
//		process.setObject(bpelProcess);
		//process.setCache(value)
		
		//TODO Create and add operations
		//ProcessUtil.addOperation(process, buildProcessOperation_GetStateManager(name, namespace));
		
		Variables variables = bpelProcess.getVariables();
		Iterator<Variable> iterator = variables.getChildren().iterator();
		while (iterator.hasNext()) {
			Variable variable = iterator.next();

			XSDTypeDefinition xsdTypeDefinition = variable.getType();

			nam.model.Variable processVariable = new nam.model.Variable();
			processState.getVariables().add(processVariable);
			processVariable.setName(variable.getName());
			processVariable.setDirty(false);
			
			//ServiceVariable serviceVariable = new ServiceVariable();
			//serviceVariable.setName(variable.getName());
			
			org.eclipse.wst.wsdl.Message messageType = variable.getMessageType();
			if (messageType != null) {
				Part part = (Part) messageType.getEParts().get(0);
				QName typeName = part.getTypeName();
				QName elementName = part.getElementName();
				
				if (typeName != null) {
					processVariable.setType(TypeUtil.getTypeFromQName(typeName));
					
				} else if (elementName != null) {
					processVariable.setType(TypeUtil.getTypeFromQName(elementName));
				}
				
			} else {
				XSDTypeDefinition xsdType = xsdTypeDefinition;
				Assert.notNull(xsdType, "XSDType should not be null");
				//XSDTypeDefinition baseType = xsdType.getBaseType();
				XSDSimpleTypeDefinition simpleType = xsdType.getSimpleType();
				if (simpleType != null) {
					//simpleType.
					throw new Exception("NOT SUPPORTED YET");
				} else {
					String namespace = xsdType.getTargetNamespace();
					String localPart = xsdType.getQName();
					Assert.notNull(xsdType, "Namespace should not be null");
					Assert.notNull(xsdType, "LocalPart should not be null");
					String typeName = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, localPart);
					processVariable.setType(typeName);
				}
			}
		}
		
		return process;
	}
	
	public Application buildApplicationFromDefinition(Definition definition) throws Exception {
		Application application = factory.createApplication();
		initializeApplicationFromDefinition(application, definition);
		return application;
	}
	
	public void initializeApplicationFromDefinition(Application application, Definition definition) throws Exception {
		String targetNamespace = definition.getTargetNamespace();
		application.setNamespace(targetNamespace);
		if (definition.getQName() != null) {
			application.setName(definition.getQName().getLocalPart());
		} else {
			//application.setName(NameUtil.getNameFromWSDLPath(definition.getLocation()));
			String localPart = NameUtil.getLocalPartFromNamespace(targetNamespace);
			String applicationName = NameUtil.capName(localPart);
			application.setName(applicationName);
		}

		Module serviceModule = ModuleFactory.createModule(application, ModuleType.SERVICE);
		Module clientModule = ModuleFactory.createModule(application, ModuleType.CLIENT);
		ApplicationUtil.getModules(application).add(clientModule);
		ApplicationUtil.getModules(application).add(serviceModule);

		List<Service> services = buildServicesFromDefinition(definition);
		ModuleUtil.getServices(serviceModule).addAll(services);
		ModuleUtil.getServices(clientModule).addAll(services);
	}
	
	public void buildApplication(Project project) throws Exception {
		String projectName = project.getName();
		
		Application application = factory.createApplication();
		application.setGroupId(project.getDomain());
		application.setArtifactId(projectName);
		application.setName(projectName);
		application.setVersion(project.getVersion());
		application.setContextRoot(projectName);
		application.setNamespace(project.getNamespace());
		application.setDependencies(new Dependencies());
		application.setModules(new Modules());
		
		//add Channels
		List<Channel> channels = MessagingUtil.getChannels(project);
		ApplicationUtil.addChannels(application, channels);
		
		//create model module (if necessary)
		//if (context.canGenerateModel()) {
		Set<Module> modelModules = ProjectUtil.getModelModules(project);
		Iterator<Module> iterator = modelModules.iterator();
		boolean moduleFound = false;
		while (iterator.hasNext()) {
			Module modelModule = iterator.next();
			if (modelModule.getNamespace().equals(project.getNamespace())) {
				Assert.isTrue(!moduleFound, "Only 1 information namespace supported (for now) at project level"); 
				application.setInformation(modelModule.getInformation());
				ApplicationUtil.addModule(application, modelModule);
				moduleFound = true;
			}
		}
		//}
		
		//create data module (if necessary)
		//if (context.canGenerateData()) {
		Set<Module> dataModules = ProjectUtil.getDataModules(project);
		Iterator<Module> iterator2 = dataModules.iterator();
		moduleFound = false;
		while (iterator2.hasNext()) {
			Module dataModule = iterator2.next();
			if (dataModule.getNamespace().equals(project.getNamespace())) {
				Assert.isTrue(!moduleFound, "Only 1 persistance namespace supported (for now) at project level"); 
				application.setPersistence(dataModule.getPersistence());
				ApplicationUtil.addModule(application, dataModule);
				moduleFound = true;
			}
		}
		//}
		
		//create client module (if necessary)
		Module clientModule = new Module();
		clientModule.setGroupId(application.getGroupId());
		//TODO - Externalize these properly
		clientModule.setArtifactId(application.getArtifactId() + "-client");
		clientModule.setName(application.getArtifactId() + "-client");
		clientModule.setNamespace(application.getNamespace());
		clientModule.setVersion(application.getVersion());
		clientModule.setLevel(ModuleLevel.GROUP_LEVEL);
		clientModule.setType(ModuleType.CLIENT);
		clientModule.setInformation(application.getInformation());
		//clientModule.setPersistence(application.getPersistence());
		clientModule.setServices(new Services());
		buildServicesForElements(application, clientModule);
		ApplicationUtil.addModule(application, clientModule);
		
		//create service module (if necessary)
		Module serviceModule = new Module();
		serviceModule.setGroupId(application.getGroupId());
		//TODO - Externalize these properly
		serviceModule.setArtifactId(application.getArtifactId() + "-service");
		serviceModule.setName(application.getArtifactId() + "-service");
		serviceModule.setNamespace(application.getNamespace());
		serviceModule.setVersion(application.getVersion());
		serviceModule.setLevel(ModuleLevel.GROUP_LEVEL);
		serviceModule.setType(ModuleType.SERVICE);
		serviceModule.setInformation(application.getInformation());
		serviceModule.setPersistence(application.getPersistence());
		serviceModule.setServices(new Services());
		buildServicesForElements(application, serviceModule);
		ApplicationUtil.addModule(application, serviceModule);
		
		//create war module (if necessary)
		//if (context.canGenerateWAR()) {
			Module warModule = new Module();
			warModule.setGroupId(application.getGroupId());
			//TODO - Externalize these properly
			warModule.setArtifactId(application.getArtifactId() + "-ui");
			warModule.setName(application.getArtifactId() + "-ui");
			warModule.setNamespace(application.getNamespace());
			warModule.setVersion(application.getVersion());
			warModule.setLevel(ModuleLevel.GROUP_LEVEL);
			warModule.setType(ModuleType.VIEW);
			warModule.setInformation(application.getInformation());
			warModule.setPersistence(application.getPersistence());
			ApplicationUtil.addModule(application, warModule);
		//}
		
		//create ear module (if necessary)
		//if (context.canGenerateEAR()) {
			Module earModule = new Module();
			earModule.setGroupId(application.getGroupId());
			//TODO - Externalize these properly
			earModule.setArtifactId(application.getArtifactId() + "-app");
			earModule.setName(application.getArtifactId() + "-app");
			earModule.setNamespace(application.getNamespace());
			earModule.setVersion(application.getVersion());
			earModule.setLevel(ModuleLevel.GROUP_LEVEL);
			earModule.setType(ModuleType.EAR);
			ApplicationUtil.addModule(application, earModule);
		//}
		
		ProjectUtil.addApplication(project, application);
		context.setApplication(application);
		
//			List<Module> modules = ProjectUtil.getApplicationModules(project);
//			if (modules.size() == 1) {
//				context.setOptionLimitToSingleModule(true);
//				Module module = modules.get(0);
//				module.setType(ModuleType.ALL);
//			}
	}

	protected void buildServicesForElements(Application application, Module module) throws Exception {
		Persistence persistence = module.getPersistence();
		if (persistence == null)
			return;
		
		List<Element> elements = PersistenceUtil.getElements(persistence);
		Collection<Namespace> namespaces = PersistenceUtil.getNamespaces(persistence);
		Iterator<Namespace> iterator4 = namespaces.iterator();
		while (iterator4.hasNext()) {
			Namespace namespace = iterator4.next();
			String domainName = NameUtil.getLocalPartFromNamespace(namespace.getUri());
			Domain domain = new Domain();
			domain.setName(domainName);
			domain.setNamespace(namespace);
			domain.setVersion(module.getVersion());
			buildServicesForElements(application, module, domain);
			ModuleUtil.addDomain(module, domain);
		}
	}
	

	/* 
	 * Module related
	 */

	public Module buildProjectPOMModule(Project project) {
		String name = NameUtil.uncapName(project.getName());
		Module module = new Module();
		module.setName(name);
		module.setType(ModuleType.POM);
		module.setLevel(ModuleLevel.PROJECT_LEVEL);
		module.setGroupId(project.getDomain());
		module.setArtifactId(project.getName());
		module.setVersion(project.getVersion());
		module.setNamespace(project.getNamespace());
		return module;
	}

	public Module buildProjectAppPOMModule(Application application) {
		String name = NameUtil.uncapName(application.getName());
		Module module = new Module();
		module.setName(name);
		module.setType(ModuleType.POM);
		module.setLevel(ModuleLevel.GROUP_LEVEL);
		module.setGroupId(application.getGroupId());
		module.setArtifactId(application.getArtifactId());
		module.setNamespace(application.getNamespace());
		module.setVersion("0.0.1-SNAPSHOT");
		return module;
	}
	
	public Module buildApplicationPOMModule(Application application) {
		String name = NameUtil.uncapName(application.getName());
		Module module = new Module();
		module.setName(name);
		module.setType(ModuleType.POM);
		module.setLevel(ModuleLevel.APPLICATION_LEVEL);
		module.setGroupId(application.getGroupId());
		module.setArtifactId(application.getArtifactId());
		module.setNamespace(application.getNamespace());
		module.setVersion("0.0.1-SNAPSHOT");
		return module;
	}
	
	public Set<Module> buildProjectModelModules(Project project) {
		Set<Module> modules = new HashSet<Module>();
		Module module = buildProjectModelModule(project);
		if (module != null)
			modules.add(module);
		List<Import> imports = ProjectUtil.getImports(project);
		Iterator<nam.model.Import> iterator = imports.iterator();
		while (iterator.hasNext()) {
			Import import1 = iterator.next();
			String namespace = import1.getNamespace();
			//TODO need to first determine a flag to indicate "which" imported namespaces will be used to create modules
		}
		return modules;
	}
	
	public Module buildProjectModelModule(Project project) {
		//Information model = ProjectUtil.getInformationBlock(project);
		Namespace namespace = context.getNamespaceByUri(project.getNamespace());
		Module module = buildModelModuleFromNamespace(project, namespace);
		return module;
	}

	public List<Module> buildProjectModelModules(Project project, Information model) {
		List<Module> modules = new ArrayList<Module>();
		Collection<Namespace> namespaces = InformationUtil.getNamespaces(model);
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			if (NamespaceUtil.isImported(namespace))
				continue;
			Module module = buildModelModuleFromNamespace(project, namespace);
			modules.add(module);
		}
		return modules;
	}

//	public Module buildProjectModelModule(Project project, String namespaceProperty) {
//		//Namespace namespace = util.assureProjectNamespace(project, namespaceProperty);
//		Namespace namespace = context.getNamespaceByUri(project.getNamespace());
//		Module module = buildModelModuleFromNamespace(project, namespace);
//		return module;
//	}

	public Module buildModelModuleFromNamespace(Project project, Namespace namespace) {
		if (namespace == null)
			return null;
		//String groupId = NamespaceUtil.getGroupIdFromNamespace(namespace);
		String groupId = project.getDomain();
		String artifactId = NamespaceUtil.getArtifactIdFromNamespace(namespace);
		if (project.getNamespace().equals(namespace.getUri()))
			//TODO - Externalize these properly
			artifactId = project.getName() + "-model";
		String version = NamespaceUtil.getVersionFromNamespace(namespace);
		//TODO add this as a configurable option:
		//if (!groupId.startsWith(namespace.getName()))
		//	artifaceId = groupId + "-" + namespace.getPrefix() + "-model";
			
		Module module = new Module();
		module.setType(ModuleType.MODEL);
		module.setLevel(ModuleLevel.PROJECT_LEVEL);
		module.setGroupId(groupId);
		//TODO add option to alow/disallow the following:
		module.setArtifactId(artifactId);
		//module.setArtifactId(name+"-model");
		module.setName(artifactId);
		//module.setName(name+"-model");
		module.setNamespace(namespace.getUri());
		//module.setInformation(buildInformationForNamespace(namespace));
		Information informationBlock = ExtensionsUtil.getInformationBlock(project, namespace);
		//Assert.notNull(informationBlock, "Information block not found for namespace: "+namespace.getUri());
		if (informationBlock == null) {
			informationBlock = buildInformationForNamespace(module, namespace);
			ExtensionsUtil.addExtension(project, informationBlock);
			module.setInformation(informationBlock);
		}
		module.setInformation(informationBlock);
		//context.addModuleByNamespace(namespace, module);
		module.setVersion(version);
		return module;
	}
	
	public Information buildInformationForNamespace(Module module, Namespace namespace) {
		Information informationBlock = new Information();
		informationBlock.setName(namespace.getName());
		informationBlock.setDomain(module.getGroupId());
		informationBlock.setVersion(module.getVersion());
		InformationUtil.addNamespace(informationBlock, namespace);
		return informationBlock;
	}
	
	
	public Module buildProjectDataModule(Project project) {
		return buildProjectDataModule(project, null);
	}

	public List<Module> buildProjectDataModules(Project project, Persistence model) {
		List<Module> modules = new ArrayList<Module>();
		Collection<Namespace> namespaces = PersistenceUtil.getNamespaces(model);
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			if (NamespaceUtil.isImported(namespace))
				continue;
			Module module = buildDataModuleFromNamespace(project, namespace);
			modules.add(module);
		}
		return modules;
	}

	public Module buildProjectDataModule(Project project, String namespaceProperty) {
		//Namespace namespace = util.assureProjectNamespace(project, namespaceProperty);
		Namespace namespace = context.getNamespaceByUri(project.getNamespace());
		Module module = buildDataModuleFromNamespace(project, namespace);
		return module;
	}
	
	public Module buildDataModuleFromNamespace(Project project, Namespace namespace) {
		//String groupId = NamespaceUtil.getGroupIdFromNamespace(namespace);
		String groupId = project.getDomain(); 
		
		String artifactId = NamespaceUtil.getArtifactIdFromNamespace(namespace);
		if (project.getNamespace().equals(namespace.getUri()))
			artifactId = project.getName() + "-persistence";
		String version = NamespaceUtil.getVersionFromNamespace(namespace);
		//TODO add this as a configurable option:
		//if (!groupId.startsWith(namespace.getName()))
		//	artifaceId = groupId + "-" + namespace.getPrefix() + "-model";
			
		Module module = new Module();
		module.setType(ModuleType.DATA);
		module.setLevel(ModuleLevel.PROJECT_LEVEL);
		module.setGroupId(groupId);
		//TODO add option to alow/disallow the following:
		module.setArtifactId(artifactId);
		//module.setArtifactId(name+"-model");
		module.setName(artifactId);
		//module.setName(name+"-model");
		module.setNamespace(namespace.getUri());
		//module.setInformation(buildInformationForNamespace(namespace));
		Persistence persistenceBlock = ExtensionsUtil.getPersistenceBlock(project, namespace);
		//Assert.notNull(informationBlock, "Information block not found for namespace: "+namespace.getUri());
		if (persistenceBlock == null) {
			persistenceBlock = buildPersistenceForNamespace(module, namespace);
			ExtensionsUtil.addExtension(project, persistenceBlock);
			module.setPersistence(persistenceBlock);
		}
		module.setPersistence(persistenceBlock);
		//context.addModuleByNamespace(namespace, module);
		module.setVersion(version);
		return module;
	}

	public Persistence buildPersistenceForNamespace(Module module, Namespace namespace) {
		Persistence persistenceBlock = new Persistence();
		persistenceBlock.setName(namespace.getName());
		persistenceBlock.setDomain(module.getGroupId());
		persistenceBlock.setVersion(module.getVersion());
		PersistenceUtil.addNamespace(persistenceBlock, namespace);
		return persistenceBlock;
	}
	
	
	/* 
	 * Services related
	 */
	
	public List<Service> buildServices(ResourceSet emfResourceSet) throws Exception {
		List<Service> services = buildServices(emfResourceSet.getResources());
		services = new ArrayList<Service>(services);
		return services;
	}

	public List<Service> buildServices(EList<Resource> resources) throws Exception {
		Map<String, Service> serviceMap = new HashMap<String, Service>();
		Iterator<Resource> iterator = resources.iterator();
		while (iterator.hasNext()) {
			Resource resource = (Resource) iterator.next();
			if (resource instanceof BPELResource) {
				BPELResource bpelResource = (BPELResource) resource;
				org.eclipse.bpel.model.Process process = bpelResource.getProcess();
				Collection<Service> servicesFromImports = buildServicesFromBPEL(process.getImports());
				addServicesToMap(serviceMap, servicesFromImports);
				//OLD buildActionFromProcessOLD(process);
			}
			if (resource instanceof WSDLResourceImpl) {
				WSDLResourceImpl wsdlResource = (WSDLResourceImpl) resource;
				Definition definition = wsdlResource.getDefinition();
				List<Service> servicesFromDefinition = buildServicesFromDefinition(definition);
				addServicesToMap(serviceMap, servicesFromDefinition);
			}
		}
		List<Service> services = new ArrayList<Service>();
		services.addAll(serviceMap.values());
		return services;
	}

	protected void addServicesToMap(Map<String, Service> serviceMap, Collection<Service> services) {
		Iterator<Service> iterator = services.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			String serviceId = service.getDomain() + "." + service.getName();
			if (!serviceMap.containsKey(serviceId)) {
				serviceMap.put(serviceId, service);
			} else {
				Service service1 = serviceMap.get(serviceId);
				ServiceUtil.mergeService(service1, service);
			}
		}
	}

	public void buildServicesForElements(Application application, Module module, Domain domain) throws Exception {
		Project project = context.getProject();
		String domainName = domain.getName();
		Namespace domainNamespace = domain.getNamespace();

		//Information informationBlock = ProjectUtil.getInformationBlock(context.getProject(), domain.getNamespace());
		Persistence persistenceBlock = ProjectUtil.getPersistenceBlockByNamespace(project, domainNamespace.getUri());
		if (persistenceBlock == null)
			return;
		
		Module dataModule = ProjectUtil.getDataModule(project, domainNamespace);
		if (dataModule == null)
			return;

		//Map<String, Type> domainTypesMap = InformationUtil.getTypeMap(informationBlock);
		Map<String, Type> domainTypesMap = NamespaceUtil.getTypeMap(domainNamespace);
		Map<String, Service> servicesForTypes = DomainUtil.getServicesForTypes(domain);
		
		String namespace = domainNamespace.getUri();
//		if (namespace.equals("http://bookshop/seller"))
//			System.out.println();
		
		List<Element> elements = PersistenceUtil.getElements(persistenceBlock);
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();

			String localPart = element.getName();
			//String localPart = TypeUtil.getLocalPart(typeName);
			String localPartCapped = NameUtil.capName(localPart);
			String localNamespace = persistenceBlock.getNamespace();
			String localType = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(localNamespace, localPart);
			String serviceName = localPart + "Manager";
			
			String serviceId = ServiceUtil.getServiceId(domainName, serviceName);
			if (DomainUtil.containsService(domain, serviceId))
				continue;
			if (!ElementUtil.isRoot(element))
				continue;
			
			Service service = new Service();
			service.setElement(localType);
			service.setName(serviceName);
			service.setNamespace(namespace);
			service.setInterfaceName(localPartCapped + "Manager");
			service.setClassName(localPartCapped + "Manager");
			service.setPackageName(ProjectLevelHelper.getPackageName(namespace));
			service.setDescription("Management service for: "+localType);
			service.setVersion(domain.getVersion());
			
			List<Operation> operations = buildOperations(SourceType.SharedCache, element);
			ServiceUtil.addOperations(service, operations);
			
			List<Channel> channels = MessagingUtil.getChannels(application);
			Iterator<Channel> iterator2 = channels.iterator();
			while (iterator2.hasNext()) {
				Channel channel = iterator2.next();
				ServiceUtil.addChannel(service, channel);
			}
			
			Transacted transactedSpec = new Transacted();
			transactedSpec.setUse(TransactionUsage.REQUIRED);
			transactedSpec.setScope(TransactionScope.THREAD);
			service.setTransacted(transactedSpec);
			DomainUtil.addService(domain, service);
		}
	}

	//NOTUSED
	public void buildServicesForElementsOLD(Application application, Module module, Domain domain) throws Exception {
		Project project = context.getProject();
		Namespace domainNamespace = domain.getNamespace();

		//Information informationBlock = ProjectUtil.getInformationBlock(context.getProject(), domain.getNamespace());
		Persistence persistenceBlock = ProjectUtil.getPersistenceBlockByNamespace(project, domainNamespace.getUri());
		if (persistenceBlock == null)
			return;
		
		Module dataModule = ProjectUtil.getDataModule(project, domainNamespace);
		if (dataModule == null)
			return;

		//Map<String, Type> domainTypesMap = InformationUtil.getTypeMap(informationBlock);
		Map<String, Type> domainTypesMap = NamespaceUtil.getTypeMap(domainNamespace);
		Map<String, Service> servicesForTypes = DomainUtil.getServicesForTypes(domain);
		
		String namespace = domainNamespace.getUri();
//		if (namespace.equals("http://bookshop/seller"))
//			System.out.println();
		
		Iterator<String> iterator2 = domainTypesMap.keySet().iterator();
		while (iterator2.hasNext()) {
			String typeName = iterator2.next();
			if (typeName.endsWith("Message"))
				continue;
			
			Type type = domainTypesMap.get(typeName);
			String localPart = type.getName();
			//String localPart = TypeUtil.getLocalPart(typeName);
			String localPartCapped = NameUtil.capName(localPart);
			String localNamespace = persistenceBlock.getNamespace();
			String localType = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(localNamespace, localPart);
			
			//skip non-local types here
			//String typeNamespace = TypeUtil.getNamespace(typeName);
			if (!localNamespace.equals(namespace))
				continue;
			
			if (!servicesForTypes.containsKey(localType)) {
				if (ElementUtil.isTransient(type))
					continue;
				if (!ElementUtil.isRoot(type))
					continue;
				
				Service service = new Service();
				service.setElement(localType);
				service.setName(localPart + "Manager");
				service.setNamespace(namespace);
				service.setInterfaceName(localPartCapped + "Manager");
				service.setClassName(localPartCapped + "Manager");
				service.setPackageName(ProjectLevelHelper.getPackageName(namespace));
				service.setDescription("Management service for: "+localType);
				service.setVersion(domain.getVersion());
				
				List<Operation> operations = buildOperations(SourceType.SharedCache, type);
				ServiceUtil.addOperations(service, operations);
				
				List<Channel> channels = MessagingUtil.getChannels(application);
				Iterator<Channel> iterator = channels.iterator();
				while (iterator.hasNext()) {
					Channel channel = iterator.next();
					ServiceUtil.addChannel(service, channel);
				}
				
				Transacted transactedSpec = new Transacted();
				transactedSpec.setUse(TransactionUsage.REQUIRED);
				transactedSpec.setScope(TransactionScope.THREAD);
				service.setTransacted(transactedSpec);
				DomainUtil.addService(domain, service);
				//ModuleUtil.addService(module, service);
			}
		}
	}

	//NOTUSED
	public void buildActionFromProcessOLD(org.eclipse.bpel.model.Process process) throws Exception {
		/**
		 * TODO base this linkage off partnerlink instead of porttype? which
		 * we are simply assuming is identical between service and process 
		 */
		//PartnerLink incomingPartnerLink = getIncomingPartnerLink(process);
		PortType incomingPortType = BPELUtil.getIncomingPortType(process);
		Service service = context.getImportedServiceByQName(incomingPortType.getQName());
		Assert.notNull(service, "Service not found: "+incomingPortType.getQName());
		//ServiceState state = new ServiceState();
		//service.setState(state);

		//String serviceName = process.getName();
		//String serviceNamespace = process.getTargetNamespace();
		//Service service = context.getServiceByName(serviceNamespace , serviceName);
		Execution execution = buildExecutionOLD(process);
		service.getChannelsAndListenersAndOperations().add(execution);
		
//		//create fields from top-level variables
//		Variables variables = process.getVariables();
//		Iterator<Variable> iterator = variables.getChildren().iterator();
//		while (iterator.hasNext()) {
//			Variable variable = iterator.next();
//
//			Message message = variable.getMessageType();
//			if (message != null) {
//				@SuppressWarnings("unchecked") Collection<Part> parts = message.getParts().values();
//				Iterator<Part> partIterator = parts.iterator();
//				while (partIterator.hasNext()) {
//					Part part = (Part) partIterator.next();
//					ServiceVariable serviceVariable = new ServiceVariable();
//					serviceVariable.setType(TypeUtil.getTypeFromMessagePart(part));
//					serviceVariable.setName(part.getName());
//					state.getVariables().add(serviceVariable);
//				}
//			} else {
//				ServiceVariable serviceVariable = new ServiceVariable();
//				serviceVariable.setType(TypeUtil.getTypeFromVariable(variable));
//				serviceVariable.setName(variable.getName());
//				state.getVariables().add(serviceVariable);
//			}
//		}

//		//create fields from top-level links
//		Activity initialBlock = process.getActivity();
//		if (initialBlock instanceof Flow) {
//			Flow flow = (Flow) initialBlock;
//			Links links = flow.getLinks();
//			Iterator<Link> linkIterator = links.getChildren().iterator();
//			while (linkIterator.hasNext()) {
//				Link link = linkIterator.next();
//				ServiceVariable serviceVariable = new ServiceVariable();
//				serviceVariable.setType(TypeUtil.getTypeFromDefaultType("boolean"));
//				serviceVariable.setName(CodeUtil.getVariableName(link));
//				state.getVariables().add(serviceVariable);
//			}
//		}
	}
	
//	public List<Service> buildServices(Process process) throws Exception {
//		List<Service> services = new ArrayList<Service>();
//		EList<Import> imports = process.getImports();
//		Iterator<Import> iterator = imports.iterator();
//		while (iterator.hasNext()) {
//			Import importObject = (Import) iterator.next();
//			ImportResolver[] importResolvers = ImportResolverRegistry.INSTANCE.getResolvers(importObject.getImportType());
//			for (ImportResolver importResolver : importResolvers) {
//				List<Object> definitionObjects = importResolver.resolve(importObject, ImportResolver.RESOLVE_DEFINITION);
//				//List<Object> schemaObjects = importResolver.resolve(importObject, ImportResolver.RESOLVE_SCHEMA);
//				Iterator<Object> definitionIterator = definitionObjects.iterator();
//				while (definitionIterator.hasNext()) {
//					Definition definition = (Definition) definitionIterator.next();
//					services.addAll(buildServices(definition));
//				}
//			}
//		}
//		Service service = new Service();
//		service.setName(process.getName());
//		service.setNamespace(process.getTargetNamespace());
//		//service.setDomain(process.get);
//		service.getExecutions().add(buildExecution(process));
//		services.add(service);
//		return services;
//	}

//	public Service buildServiceFromProcess(Process process) throws Exception {
//		Service service = new Service();
//		service.setName(process.getName());
//		service.setNamespace(process.getTargetNamespace());
//		service.getExecutions().add(buildExecution(process));
//		ServiceState state = new ServiceState();
//		service.setState(state);
//		Variables variables = process.getVariables();
//		Iterator<Variable> iterator = variables.getChildren().iterator();
//		while (iterator.hasNext()) {
//			Variable variable = iterator.next();
//			ServiceVariable serviceVariable = new ServiceVariable();
//			state.getVariables().add(serviceVariable);
//			serviceVariable.setName(variable.getName());
//			org.eclipse.wst.wsdl.Message messageType = variable.getMessageType();
//			if (messageType != null) {
//				Part part = (Part) messageType.getEParts().get(0);
//				QName typeName = part.getTypeName();
//				QName elementName = part.getElementName();
//				if (typeName != null) {
//					serviceVariable.setType(TypeUtil.getTypeFromQName(typeName));
//				} else if (elementName != null) {
//					serviceVariable.setType(TypeUtil.getTypeFromQName(elementName));
//				}
//			} else {
//				XSDTypeDefinition xsdType = variable.getType();
//				Assert.notNull(xsdType, "XSDType should not be null");
//				//XSDTypeDefinition baseType = xsdType.getBaseType();
//				XSDSimpleTypeDefinition simpleType = xsdType.getSimpleType();
//				if (simpleType != null) {
//					//simpleType.
//					throw new Exception("NOT SUPPORTED YET");
//				} else {
//					String namespace = xsdType.getTargetNamespace();
//					String localPart = xsdType.getQName();
//					Assert.notNull(xsdType, "Namespace should not be null");
//					Assert.notNull(xsdType, "LocalPart should not be null");
//					String typeName = TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, localPart);
//					serviceVariable.setType(typeName);
//				}
//			}
//		}
//		return service;
//	}

	public Collection<Service> buildServicesFromBPEL(EList<org.eclipse.bpel.model.Import> imports) throws Exception {
		List<Service> services = new ArrayList<Service>(); 
		Iterator<org.eclipse.bpel.model.Import> iterator = imports.iterator();
		while (iterator.hasNext()) {
			org.eclipse.bpel.model.Import importObject = iterator.next();
			ImportResolver[] importResolvers = ImportResolverRegistry.INSTANCE.getResolvers(importObject.getImportType());
			for (ImportResolver importResolver : importResolvers) {
				List<Object> definitionObjects = importResolver.resolve(importObject, ImportResolver.RESOLVE_DEFINITION);
				//List<Object> schemaObjects = importResolver.resolve(importObject, ImportResolver.RESOLVE_SCHEMA);
				Iterator<Object> definitionIterator = definitionObjects.iterator();
				while (definitionIterator.hasNext()) {
					Definition definition = (Definition) definitionIterator.next();
					List<Service> list = buildServicesFromDefinition(definition);
					services.addAll(list);
				}
			}
		}
		return services;
	}
	
//	public List<Service> buildServicesFromDefinition(Definition definition) throws Exception {
//		List<Service> services = new ArrayList<Service>();
//		@SuppressWarnings("unchecked") Map<QName, org.eclipse.wst.wsdl.Service> emfServices = definition.getServices();
//		Iterator<org.eclipse.wst.wsdl.Service> iterator = emfServices.values().iterator();
//		while (iterator.hasNext()) {
//			org.eclipse.wst.wsdl.Service emfService = iterator.next();
//			//services.add(buildServicesFromEMFService(emfService));
//			@SuppressWarnings("unchecked") List<Service> list = buildServicesFromWSDLPorts(emfService.getPorts());
//			services.addAll(list);
//		}
//		return services;
//	}

	public List<Service> buildServicesFromDefinition(Definition definition) throws Exception {
		String serviceDomain = NameUtil.getLocalPartFromNamespace(definition.getTargetNamespace());
		@SuppressWarnings("unchecked") Map<String, PortType> portTypes = definition.getPortTypes();
		Iterator<PortType> iterator = portTypes.values().iterator();
		List<Service> services = new ArrayList<Service>();
		while (iterator.hasNext()) {
			PortType portType = iterator.next();
			Service service = buildServiceFromWSDLPortType(portType);
			if (service != null) {
				service.setDomain(serviceDomain);
				services.add(service);
			}
		}
		return services;
	}

	public List<Service> buildServicesFromWSDLPorts(Map<String, org.eclipse.wst.wsdl.Port> ports) throws Exception {
		Collection<org.eclipse.wst.wsdl.Port> values = ports.values();
		Iterator<org.eclipse.wst.wsdl.Port> iterator = values.iterator();
		List<Service> services = new ArrayList<Service>();
		while (iterator.hasNext()) {
			org.eclipse.wst.wsdl.Port port = iterator.next();
			Service service = buildServiceFromWSDLPort(port);
			if (service != null)
				services.add(service);
		}

		return services;
	}

	public Service buildServiceFromWSDLPort(org.eclipse.wst.wsdl.Port port) throws Exception {
		PortType portType = port.getBinding().getPortType();
		Service service = buildServiceFromWSDLPortType(portType);
		return service;
	}
	
	public Service buildServiceFromWSDLPortType(javax.wsdl.PortType portType) throws Exception {
		//create the service
		String portTypeLocalpart = portType.getQName().getLocalPart();
		String portTypeNamespace = portType.getQName().getNamespaceURI();
		String serviceName = TypeUtil.getServiceNameFromText(portTypeLocalpart);
		String serviceDomain = NameUtil.getLocalPartFromNamespace(portType.getQName().getNamespaceURI());
		//Service service = context.getImportedServiceByName(portTypeNamespace, serviceName);
		Service service = context.getImportedServiceByName(serviceName);
		
		if (service == null) {
			service = new Service();
			service.setDomain(serviceDomain);
			service.setName(serviceName);
			service.setNamespace(portTypeNamespace);
			service.setPortType(portType.getQName().toString());
			service.setPackageName(ProjectLevelHelper.getPackageName(portTypeNamespace));
			service.setInterfaceName(ServiceLayerHelper.getServiceInterfaceName(service));
			service.setClassName(ServiceLayerHelper.getServiceClassName(service));

			//create the operations
			List<Operation> operations = buildOperations(portType);
			ServiceUtil.addOperations(service, operations);
			//Execution execution = buildExecution(port);
			//service.getExecutions().add(execution);

			context.addImportedService(service);
			return service;

		} else {
			/*
			 * TODO At least print warning here to user that we are making 
			 * the assumption of two services being one and the same.
			 */
			return service;
		}
	}
	
//	public Service buildServicesFromEMFService(org.eclipse.wst.wsdl.Service emfService) throws Exception {
//		String serviceNamespace = emfService.getQName().getNamespaceURI();
//		String serviceName = TypeUtil.getServiceNameFromText(emfService.getQName().getLocalPart());
//		Service service = context.getServiceByName(serviceNamespace, serviceName);
//		if (service == null) {
//			service = new Service();
//			service.setName(serviceName);
//			service.setNamespace(serviceNamespace);
//			context.addServiceToCache(service);
//		} else {
//			/*
//			 * TODO Print warning here to user that we are making 
//			 * the assumption of two services being one and the same.
//			 */
//		}
//
//		@SuppressWarnings("unchecked") Collection<org.eclipse.wst.wsdl.Port> values = emfService.getPorts().values();
//		Iterator<org.eclipse.wst.wsdl.Port> iterator = values.iterator();
//		while (iterator.hasNext()) {
//			org.eclipse.wst.wsdl.Port port = iterator.next();
//			List<Operation> operations = buildOperations(port);
//			service.getOperations().addAll(operations);
//			//Execution execution = buildExecution(port);
//			//service.getExecutions().add(execution);
//		}
//		return service;
//	}
	
	//NOTUSED
	public Execution buildExecutionOLD(org.eclipse.bpel.model.Process process) {
		Execution execution = new Execution();
		execution.setName(process.getName());
		execution.getInvocations().add(buildActionOLD(process));
		return execution;
	}

	public Execution buildExecution(org.eclipse.wst.wsdl.Port port) throws Exception {
		Execution execution = new Execution();
		//List<Operation> operations = buildOperations(port);
		List<Invocation> methods = buildMethods(port);
		execution.getInvocations().addAll(methods);
		return execution;
	}

	//NOTUSED
	public Invocation buildActionOLD(org.eclipse.bpel.model.Process process) {
		Invocation action = new Invocation();
		action.setName(NameUtil.toCamelCase(process.getName()));
		action.setProcess(process.getName());
		action.setSequence(process.getActivity());
		
//		action.setName(bindingOperation.getName());
//		Message inputMessage = bindingOperation.getOperation().getInput().getMessage();
//		Message outputMessage = bindingOperation.getOperation().getOutput().getMessage();
//		List<Input> inputs = buildActionInputs(inputMessage);
//		List<Output> outputs = buildActionOutputs(outputMessage);
//		action.getInputs().addAll(inputs);
//		action.getOutputs().addAll(outputs);
		return action;
	}

	public List<Invocation> buildMethods(org.eclipse.wst.wsdl.Port port) throws Exception {
		List<Invocation> actions = new ArrayList<Invocation>();
		@SuppressWarnings("unchecked") List<org.eclipse.wst.wsdl.BindingOperation> bindingOperations = port.getBinding().getBindingOperations();
		Iterator<BindingOperation> iterator = bindingOperations.iterator();
		while (iterator.hasNext()) {
			BindingOperation bindingOperation = iterator.next();
			Invocation action = buildAction(bindingOperation);
			actions.add(action);
		}
		return actions;
	}

	public Invocation buildAction(org.eclipse.wst.wsdl.BindingOperation bindingOperation) throws Exception {
		Invocation action = new Invocation();
		action.setName(NameUtil.toCamelCase(bindingOperation.getName()));
		Message inputMessage = (Message) bindingOperation.getOperation().getInput().getMessage();
		Message outputMessage = (Message) bindingOperation.getOperation().getOutput().getMessage();
		List<Input> inputs = buildActionInputs(inputMessage);
		List<Output> outputs = buildActionOutputs(outputMessage);
		action.getInputs().addAll(inputs);
		action.getOutputs().addAll(outputs);
		return action;
	}

	public List<Input> buildActionInputs(javax.wsdl.Message message) throws Exception {
		List<Input> inputs = new ArrayList<Input>();
		@SuppressWarnings("unchecked") Collection<org.eclipse.wst.wsdl.Part> values = message.getParts().values();
		Iterator<org.eclipse.wst.wsdl.Part> iterator = values.iterator();
		while (iterator.hasNext()) {
			org.eclipse.wst.wsdl.Part part = iterator.next();
			Input input = new Input();
			input.setName(part.getName());
			input.setType(TypeUtil.getTypeFromMessagePart(part));
			inputs.add(input);
		}
		return inputs;
	}

	public List<Output> buildActionOutputs(javax.wsdl.Message message) throws Exception {
		List<Output> outputs = new ArrayList<Output>();
		@SuppressWarnings("unchecked") Collection<org.eclipse.wst.wsdl.Part> values = message.getParts().values();
		Iterator<org.eclipse.wst.wsdl.Part> iterator = values.iterator();
		while (iterator.hasNext()) {
			org.eclipse.wst.wsdl.Part part = iterator.next();
			Output output = new Output();
			output.setName(part.getName());
			output.setType(TypeUtil.getTypeFromMessagePart(part));
			outputs.add(output);
		}
		return outputs;
	}

	public <T extends Type> List<Operation> buildOperations(SourceType sourceType, T dataItem) throws Exception {
		List<Operation> operations = new ArrayList<Operation>();
		
		boolean hasCriteriaElement = CodeUtil.hasCriteriaElement(dataItem);
		boolean isStateWritable = util.isStateWritable(sourceType);
		boolean isJMXManageable = util.isJMXManageable(sourceType);
//		if (dataItem.getStructure() == null)
//			System.out.println();
		boolean isCollection = FieldUtil.isCollection(dataItem);
		boolean isRemovable = util.isRemovable(sourceType);
		String structure = dataItem.getStructure();
		
		if (structure.equals("item")) {
			operations.add(buildOperation(dataItem, sourceType, MethodType.GetAllAsList));
			operations.add(buildOperation(dataItem, sourceType, MethodType.GetAsItemById));
			if (structure.equals("item") && isStateWritable || isJMXManageable)
				operations.add(buildOperation(dataItem, sourceType, MethodType.Set));
			
		} else if (structure.equals("list") || structure.equals("set")) {
			operations.add(buildOperation(dataItem, sourceType, MethodType.GetAllAsList));
			//operations.add(buildOperation(dataItem, sourceType, MethodType.GetAllAsListByIndex));
			//operations.add(buildOperation(dataItem, sourceType, MethodType.GetAllAsListByKey));
			operations.add(buildOperation(dataItem, sourceType, MethodType.GetAsItemById));
			if (hasCriteriaElement) {
				operations.add(buildOperation(dataItem, sourceType, MethodType.GetAsListByIds));
				operations.add(buildOperation(dataItem, sourceType, MethodType.GetAsListByCriteria));
				operations.add(buildOperation(dataItem, sourceType, MethodType.GetMatchingAsList));
			}
			if (isStateWritable || isJMXManageable) {
				operations.add(buildOperation(dataItem, sourceType, MethodType.AddAsItem));
				operations.add(buildOperation(dataItem, sourceType, MethodType.AddAsList));
			}
			
		} else if (structure.equals("map")) {
			operations.add(buildOperation(dataItem, sourceType, MethodType.GetAllAsList));
			operations.add(buildOperation(dataItem, sourceType, MethodType.GetAllAsMap));
			operations.add(buildOperation(dataItem, sourceType, MethodType.GetAsItemById));
			operations.add(buildOperation(dataItem, sourceType, MethodType.GetAsItemByKey));
			if (hasCriteriaElement) {
				operations.add(buildOperation(dataItem, sourceType, MethodType.GetAsListByIds));
				//operations.add(buildOperation(dataItem, sourceType, MethodType.GetAsListByKeys));
				operations.add(buildOperation(dataItem, sourceType, MethodType.GetAsMapByKeys));
				operations.add(buildOperation(dataItem, sourceType, MethodType.GetAsListByCriteria));
				operations.add(buildOperation(dataItem, sourceType, MethodType.GetMatchingAsList));
				operations.add(buildOperation(dataItem, sourceType, MethodType.GetMatchingAsMap));
			}
			if (isStateWritable || isJMXManageable) {
				operations.add(buildOperation(dataItem, sourceType, MethodType.AddAsItem));
				operations.add(buildOperation(dataItem, sourceType, MethodType.AddAsList));
				operations.add(buildOperation(dataItem, sourceType, MethodType.AddAsMap));
			}
		}

		//if (isStateWritable)
		//	operations.add(buildOperation(field, sourceType, MethodType.Unset));
		if ((isStateWritable || isJMXManageable) && isCollection && isRemovable)
			operations.add(buildOperation(dataItem, sourceType, MethodType.RemoveAll));
		if ((isStateWritable || isJMXManageable) && isCollection && isRemovable) {
			operations.add(buildOperation(dataItem, sourceType, MethodType.RemoveAsItem));
			operations.add(buildOperation(dataItem, sourceType, MethodType.RemoveAsItemById));
			//operations.add(buildOperation(dataItem, sourceType, MethodType.RemoveAsListByIds));
			if (structure.equals("map")) {
				operations.add(buildOperation(dataItem, sourceType, MethodType.RemoveAsItemByKey));
				//operations.add(buildOperation(dataItem, sourceType, MethodType.RemoveAsListByKeys));
			}
			operations.add(buildOperation(dataItem, sourceType, MethodType.RemoveAsList));
			if (hasCriteriaElement)
				operations.add(buildOperation(dataItem, sourceType, MethodType.RemoveAsListByCriteria));
		}
		
		//operations.add(buildOperation(dataItem, sourceType, MethodType.RemoveMatchingAsList));
//		if (structure.equals("map"))
//			operations.add(buildOperation(modelUnit, field, sourceType, MethodType.RemoveAsMap));
		
		return operations;
	}

	protected <T extends Type> Operation buildOperation(T dataItem, SourceType sourceType, MethodType methodType) throws Exception {
		String operationName = util.getOperationName(sourceType, methodType, dataItem);
//		if (operationName.equals("addToReservedBooks"))
//			System.out.println();

		Operation operation = new Operation();
		operation.setRole("handle");
		operation.setName(operationName);
		//operation.setName("response" + cappedName);
		//operation.setName("callback");
		operation.getParameters().addAll(util.createParameters(dataItem, sourceType, methodType));
		
		//cache-specific checks not needed here
		boolean isCacheRelated = false; 
		if (util.isResultRequired(methodType, isCacheRelated))
			operation.addToResults(util.createResult(dataItem, sourceType, methodType));
		return operation;
	}
	
//	protected <T extends Type> ModelOperation createOperation_DataAccess(ModelUnit modelUnit, T dataItem, SourceType sourceType, MethodType methodType) throws Exception {
//		return createOperation_DataAccess(modelUnit, dataItem, sourceType, methodType, null);
//	}
//	
//	protected <T extends Type> ModelOperation createOperation_DataAccess(ModelUnit modelUnit, T dataItem, SourceType sourceType, MethodType methodType, TestType testType) throws Exception {
//		String type = dataItem.getType();
//		String name = NameUtil.capName(dataItem.getName());
//		String nameUncapped = NameUtil.uncapName(dataItem.getName());
//		String packageName = util.getPackageNameFromType(type);
//		String className = util.getClassNameFromType(type);
//		String structure = dataItem.getStructure();
//
//		ModelOperation modelOperation = new ModelOperation();
//		modelOperation.setModifiers(util.getModifiers(sourceType));
//		modelOperation.setName(util.getOperationName(sourceType, methodType, testType, dataItem));
//		modelUnit.addImportedClass(packageName+"."+className);
//		
//		if (util.isParameterRequired(methodType, testType)) {
//			List<ModelParameter> modelParameters = util.createParameters(methodType, modelUnit, dataItem, packageName, className, structure);
//			modelOperation.setParameters(modelParameters);
////			parameterType = getParameterType(methodType, modelUnit, field, packageName, className, structure);
////			modelOperation.addParameter(CodeUtil.createParameter(parameterType, typeName));
//		}
//		
//		String resultType = null;
//		if (util.isResultRequired(methodType)) {
//			resultType = util.getResultType(methodType, modelUnit, packageName, className, dataItem.getKey(), structure);
//			modelOperation.setResultType(resultType);
//		}
//		
//		if (modelUnit instanceof ModelInterface)
//			return modelOperation;
//		
//		Buf buf = new Buf();
//		switch (sourceType) {
//		case PendingState:
//			switch (methodType) {
//			case GetAsList: buf.putLine2("return stateProcessor.getPending"+name+"();"); break;
//			case Set: buf.putLine2("stateProcessor.setPending"+name+"("+nameUncapped+");"); break;
//			case Unset: buf.putLine2("stateProcessor.setPending"+name+"(null);"); break;
//			case AddAsItem: buf.putLine2("stateProcessor.addPending"+name+"("+nameUncapped+");"); break;
//			case RemoveAsItem: buf.putLine2("stateProcessor.removePending"+name+"("+nameUncapped+");"); break;
//			case RemoveAll: buf.putLine2("stateProcessor.removeAllPending"+name+"();"); break;
//			}
//			break;
//			
//		case PreparedState:
//			switch (methodType) {
//			case GetAsList: buf.putLine2("return isLocked() ? getPreparedState().get"+name+"() : null;"); break;
//			case Set: buf.putLine2("if (isLocked()) getPreparedState().set"+name+"("+nameUncapped+");"); break;
//			case Unset: buf.putLine2("if (isLocked()) getPreparedState().set"+name+"(null);"); break;
//			case AddAsItem: buf.putLine2("if (isLocked()) getPreparedState().add"+name+"("+nameUncapped+");"); break;
//			case RemoveAsItem: buf.putLine2("if (isLocked()) getPreparedState().remove"+name+"("+nameUncapped+");"); break;
//			case RemoveAll: buf.putLine2("if (isLocked()) getPreparedState().removeAll"+name+"();"); break;
//			}
//			break;
//
//		case CurrentState:
//			switch (methodType) {
//			case GetAsList: buf.putLine2("return currentState.get"+name+"();"); break;
//			case Set: buf.putLine2("currentState.set"+name+"("+nameUncapped+");"); break;
//			case Unset: buf.putLine2("currentState.set"+name+"(null);"); break;
//			case AddAsItem: buf.putLine2("currentState.add"+name+"("+nameUncapped+");"); break;
//			case RemoveAsItem: buf.putLine2("currentState.remove"+name+"("+nameUncapped+");"); break;
//			case RemoveAll: buf.putLine2("currentState.removeAll"+name+"();"); break;
//			}
//			break;
//			
//		case SharedCache:
//			if (testType != null)
//				buf.put(util.getSharedStateSource(modelUnit, modelOperation, methodType, testType, dataItem)); 
//			else buf.put(util.getSharedStateSource(modelUnit, modelOperation, methodType, dataItem)); 
//			break;
//
//		case JMXInvocation:
//			switch (methodType) {
//			case GetAsList: buf.putLine2("return invokeMBean(\"get"+name+"\");"); break;
//			case Set: buf.putLine2("invokeMBean(\"set"+name+"\");"); break;
//			case Unset: buf.putLine2("invokeMBean(\"set"+name+"\");"); break;
//			case AddAsItem: buf.putLine2("invokeMBean(\"addTo"+name+"\");"); break;
//			case RemoveAsItem: buf.putLine2("invokeMBean(\"removeFrom"+name+"\");"); break;
//			case RemoveAll: buf.putLine2("invokeMBean(\"removeAll"+name+"\");"); break;
//			}
//
//			break;
//		}
//
//		modelOperation.addInitialSource(buf.get());
//		return modelOperation;
//	}
	
	public List<Operation> buildOperations(javax.wsdl.PortType portType) throws Exception {
		List<Operation> operations = new ArrayList<Operation>();
		@SuppressWarnings("unchecked") List<javax.wsdl.Operation> wsdlOperations = portType.getOperations();
		Iterator<javax.wsdl.Operation> iterator = wsdlOperations.iterator();
		while (iterator.hasNext()) {
			javax.wsdl.Operation wsdlOperation = iterator.next();
			Operation operation = buildOperation(wsdlOperation);
			operations.add(operation);
		}
		return operations;
	}

	public Operation buildOperation(javax.wsdl.Operation wsdlOperation) throws Exception {
		Operation operation = new Operation();
		operation.setName(wsdlOperation.getName());
		javax.wsdl.Input input = wsdlOperation.getInput();
		javax.wsdl.Output output = wsdlOperation.getOutput();
		if (input != null) {
			List<Parameter> parameters = buildOperationInputs(input);
			operation.getParameters().addAll(parameters);
		}
		@SuppressWarnings("unchecked")
		Collection<org.eclipse.wst.wsdl.Fault> wsdlFaults = wsdlOperation.getFaults().values();
		Iterator<org.eclipse.wst.wsdl.Fault> iterator = wsdlFaults.iterator();
		while (iterator.hasNext()) {
			org.eclipse.wst.wsdl.Fault wsdlFault = iterator.next();
			Fault fault = buildFault(wsdlFault);
			operation.getFaults().add(fault);
		}
		
		if (output != null) {
			List<Result> results = buildOperationOutputs(output);
			/*
			 * TODO assuming for now only one result here:
			 * Take only the first result as the returned object.
			 */
			if (results.size() > 0)
				operation.addToResults(results.get(0));
		}
		return operation;
	}

	public List<Operation> buildOperations(Port port) throws Exception {
		List<Operation> operations = new ArrayList<Operation>();
		@SuppressWarnings("unchecked") List<org.eclipse.wst.wsdl.BindingOperation> bindingOperations = port.getBinding().getBindingOperations();
		Iterator<BindingOperation> iterator = bindingOperations.iterator();
		while (iterator.hasNext()) {
			BindingOperation bindingOperation = iterator.next();
			Operation operation = buildOperation(bindingOperation);
			operations.add(operation);
		}
		return operations;
	}

	public Operation buildOperation(BindingOperation bindingOperation) throws Exception {
		Operation operation = new Operation();
		operation.setName(bindingOperation.getName());
		javax.wsdl.Input operationInput = bindingOperation.getOperation().getInput();
		javax.wsdl.Output operationOutput = bindingOperation.getOperation().getOutput();
		@SuppressWarnings("unchecked") List<BindingFault> bindingFaults = bindingOperation.getEBindingFaults();
		List<Parameter> parameters = buildOperationInputs(operationInput);
		List<Result> results = buildOperationOutputs(operationOutput);
		List<Fault> faults = buildOperationFaults(bindingFaults);
		operation.getParameters().addAll(parameters);
		operation.getFaults().addAll(faults);
		//TODO for now: take only the first result as the returned object
		if (results.size() > 0)
			operation.addToResults(results.get(0));
		return operation;
	}

	public List<Fault> buildOperationFaults(List<BindingFault> bindingFaults) {
		List<Fault> faults = new ArrayList<Fault>();
		Iterator<BindingFault> iterator = bindingFaults.iterator();
		while (iterator.hasNext()) {
			BindingFault bindingFault = iterator.next();
			Fault fault = buildFault(bindingFault);
			faults.add(fault);
		}
		return faults;
	}

	public List<Parameter> buildOperationInputs(javax.wsdl.Input input) throws Exception {
		List<Parameter> parameters = new ArrayList<Parameter>();
		@SuppressWarnings("unchecked") Collection<org.eclipse.wst.wsdl.Part> values = input.getMessage().getParts().values();
		Iterator<org.eclipse.wst.wsdl.Part> iterator = values.iterator();
		while (iterator.hasNext()) {
			org.eclipse.wst.wsdl.Part part = iterator.next();
			Parameter parameter = new Parameter();
			//String parameterTypeName = part.getTypeName().getLocalPart();
			//String parameterType = getCachedType(parameterTypeName);
			String parameterType = TypeUtil.getTypeFromMessagePart(part);
			parameter.setName(part.getName());
			parameter.setType(parameterType);
			parameters.add(parameter);
		}
		return parameters;
	}

	public List<Result> buildOperationOutputs(javax.wsdl.Output output) throws Exception {
		List<Result> results = new ArrayList<Result>();
		@SuppressWarnings("unchecked") Collection<org.eclipse.wst.wsdl.Part> values = output.getMessage().getParts().values();
		Iterator<org.eclipse.wst.wsdl.Part> iterator = values.iterator();
		while (iterator.hasNext()) {
			org.eclipse.wst.wsdl.Part part = iterator.next();
			Result result = new Result();
			//String resultTypeName = part.getTypeName().getLocalPart();
			//String resultType = getCachedType(resultTypeName);
			String resultType = TypeUtil.getTypeFromMessagePart(part);
			//result.setName(part.getName()+"Result");
			//ASSUMING 1 Result always for now
			result.setName("result");
			result.setType(resultType);
			results.add(result);
			break;
		}
		return results;
	}

	public Fault buildFault(org.eclipse.wst.wsdl.Fault wsdlFault) {
		Message message = (Message) wsdlFault.getMessage();
		Fault fault = buildFault(message);
		return fault;
	}
	
	public Fault buildFault(BindingFault bindingFault) {
		Message message = (Message) bindingFault.getFault().getMessage();
		Fault fault = buildFault(message);
		return fault;
	}

	public Fault buildFault(Message message) {
		Fault fault = new Fault();
		fault.setExtends("Exception");
		fault.setType(TypeUtil.getTypeFromMessage(message));
		//assuming again the one part per message scenario
		@SuppressWarnings("unchecked") List<Part> parts = message.getEParts();
		String partType = TypeUtil.getNameFromMessagePart((Part) parts.get(0));
		fault.setName(NameUtil.capName(partType));
		Iterator<Part> partIterator = parts.iterator();
		while (partIterator.hasNext()) {
			Part part = partIterator.next();
			Attribute attribute = new Attribute();
			String name = TypeUtil.getNameFromMessagePart(part);
			String type = TypeUtil.getTypeFromMessagePart(part);
			if (name.equals("id"))
				continue;
			if (name.equals("reason"))
				continue;
			attribute.setName(name);
			attribute.setType(type);
			attribute.setStructure("item");
			fault.getAttributes().add(attribute);
		}
		fault.getAttributes().add(util.createAttribute_Id());
		fault.getAttributes().add(util.createAttribute_Reason());
		return fault;
	}
	
	public List<Namespace> buildNamespaces(ResourceSet emfResourceSet) throws Exception {
		return buildNamespaces(emfResourceSet, false);
	}
	
	public List<Namespace> buildNamespaces(ResourceSet emfResourceSet, boolean imported) throws Exception {
		EList<Resource> resources = emfResourceSet.getResources();
		Assert.notNull(resources, "Resources must exist");
		Registry packageRegistry = emfResourceSet.getPackageRegistry();
		List<Namespace> namespaces = buildNamespaces(packageRegistry, imported);
		return namespaces;
	}

	public List<Namespace> buildNamespaces2(ResourceSet emfResourceSet) throws Exception {
		List<Namespace> namespaces = new ArrayList<Namespace>();
		EList<Resource> resources = emfResourceSet.getResources();
		Assert.notNull(resources, "Resources must exist");
		Iterator<Resource> iterator = resources.iterator();
		while (iterator.hasNext()) {
			Resource resource = iterator.next();
			if (resource instanceof WSDLResourceImpl) {
				WSDLResourceImpl wsdlResource = (WSDLResourceImpl) resource;
				Definition definition = wsdlResource.getDefinition();
				String targetNamespace = definition.getTargetNamespace();
				String localPart = NameUtil.getLocalPartFromNamespace(targetNamespace);
				Namespace namespace = new Namespace();
				namespace.setUri(targetNamespace);
				namespace.setName(ProjectLevelHelper.getPackageName(targetNamespace));
				namespace.setPrefix(localPart);
				namespaces.add(namespace);
			}
			
		}
		return namespaces;
	}
	
	protected EClass getElementByQName(QName qName) {
		String typeName = TypeUtil.getTypeFromQName(qName);
		return getElementFromCache(context.getElementByQNameCache(), typeName);
	}

	protected EClass getElementByJavaType(String typeName) {
		String name = typeName.toLowerCase().replace("-", "");
		return getElementFromCache(context.getElementByJavaTypeCache(), name);
	}

	protected EClass getElementFromCache(Map<String, EClass> elementCache, String name) {
		Set<String> keySet = elementCache.keySet();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			if (key.equals(name))
				return elementCache.get(key);
		}
		return null;
	}

	protected void populateCache(Registry packageRegistry) throws Exception {
		//This must be done explicitely 
		//context.clearElementCache();
		Collection<Object> values = packageRegistry.values();
		Iterator<Object> iterator = values.iterator();
		while (iterator.hasNext()) {
			EPackage ePackage = (EPackage) iterator.next();
			//String ePackageName = ePackage.getName();
			if (ePackage instanceof XSDPackage)
				continue;
			if (ePackage instanceof WSDLPackage)
				continue;
			if (ePackage instanceof BPELPackage)
				continue;
			populateCache(ePackage);
		}
	}

	public void populateCache(EPackage ePackage) throws Exception {
		String packageName = TypeUtil.getJavaPackageFromEPackage(ePackage);
		EList<EObject> eContents = ePackage.eContents();
		Iterator<EObject> iterator = eContents.iterator();

		while (iterator.hasNext()) {
			EObject eObject = iterator.next();
			
			if (eObject instanceof EClass) {
				EClass eClass = (EClass) eObject;
				String className = eClass.getName();
				String javaName = packageName+"."+className;
				if (!className.endsWith("DocumentRoot")) {
					if (!context.getElementByJavaTypeCache().containsKey(javaName)) {
						context.getElementByQNameCache().put(TypeUtil.getTypeFromEPackageAndEClass(ePackage, eClass), eClass);
						context.getElementByJavaTypeCache().put(javaName, eClass);
					}
				}
				
			} else if (eObject instanceof EEnum) {
				EEnum eEnum = (EEnum) eObject;
				String className = eEnum.getName();
				String javaName = packageName+"."+className;
//				if (className.endsWith("RoleType"))
//					System.out.println();
				if (!context.getEnumerationCache().containsKey(javaName)) {
					context.getEnumerationCache().put(javaName, eEnum);
				}
				
			} else if (eObject instanceof EDataType) {
				EDataType eDataType = (EDataType) eObject;
				String className = eDataType.getName();
				String javaName = packageName+"."+className;
				if (!context.getDatatypeCache().containsKey(javaName)) {
					context.getDatatypeCache().put(javaName, eDataType);
				}
				
			} else if (eObject instanceof EAnnotation) {
				EAnnotation eAnnotation = (EAnnotation) eObject;
				String javaName = packageName+"."+eAnnotation.getSource();
				if (!context.getAnnotationCache().containsKey(javaName)) {
					context.getAnnotationCache().put(javaName, eAnnotation);
				}
				
			} else {
				//nothing here
			}
		}
	}

	public void populateCache(Collection<Namespace> namespaces) throws Exception {
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			populateCache(namespace);
		}
	}
	
	public void populateCache(Namespace namespace) throws Exception {
		EcoreFactoryImpl ecoreFactory = new EcoreFactoryImpl();
		String packageName = ProjectLevelHelper.getPackageName(namespace.getUri());
		List<Type> types = NamespaceUtil.getTypes(namespace);
		Iterator<Type> iterator = types.iterator();
		
		while (iterator.hasNext()) {
			Type type = iterator.next();
			String className = TypeUtil.getClassName(type.getType());
			String javaName = packageName+"."+className;
			String typeName = type.getName();

			if (type instanceof Enumeration) {
				EEnum eEnum = context.getEnumerationCache().get(javaName);
				if (eEnum == null) {
					eEnum = ecoreFactory.createEEnum();
					eEnum.setName(typeName);
					eEnum.setInstanceClassName(javaName);
					eEnum.setInstanceTypeName(className);
					eEnum.setSerializable(true);
//					if (className.equals("RoleType"))
//						System.out.println();
					context.getEnumerationCache().put(javaName, eEnum);
				}
				
			} else if (type instanceof Element) {
				EClass eClass = context.getElementByJavaTypeCache().get(javaName);
				if (eClass == null) {
					eClass = ecoreFactory.createEClass();
					eClass.setName(typeName);
					eClass.setInstanceClassName(javaName);
					eClass.setInstanceTypeName(className);
					eClass.setAbstract(false);
					eClass.setInterface(false);
					context.getElementByQNameCache().put(typeName, eClass);
					context.getElementByJavaTypeCache().put(javaName, eClass);
				}

			} else if (type instanceof Type) {
				EDataType eDataType = context.getDatatypeCache().get(javaName);
				if (eDataType == null) {
					eDataType = ecoreFactory.createEDataType();
					eDataType.setName(typeName);
					eDataType.setInstanceClassName(javaName);
					eDataType.setInstanceTypeName(className);
					eDataType.setSerializable(true);
					context.getDatatypeCache().put(javaName, eDataType);
				}
			}
		}
	}
	
	public List<Namespace> buildNamespaceRefs(Registry packageRegistry) throws Exception {
		List<Namespace> namespaces = buildNamespaces(packageRegistry, false, true);
		return namespaces;
	}

	public List<Namespace> buildNamespaces(Registry packageRegistry) throws Exception {
		List<Namespace> namespaces = buildNamespaces(packageRegistry, true, false);
		return namespaces;
	}
	
	public List<Namespace> buildNamespaces(Registry packageRegistry, boolean imported) throws Exception {
		List<Namespace> namespaces = buildNamespaces(packageRegistry, true, imported);
		return namespaces;
	}
	
	public List<Namespace> buildNamespaces(Registry packageRegistry, boolean filter, boolean imported) throws Exception {
		Assert.notNull(packageRegistry, "PackageRegistry must exist");
		populateCache(packageRegistry);
		
		List<Namespace> namespaces = new ArrayList<Namespace>();
		Collection<Object> values = packageRegistry.values();
		Iterator<Object> iterator = values.iterator();
		while (iterator.hasNext()) {
			EPackage ePackage = (EPackage) iterator.next();
			//String ePackageName = ePackage.getName();
			if (filter) {
				if (ePackage instanceof XSDPackage)
					continue;
				if (ePackage instanceof WSDLPackage)
					continue;
				if (ePackage instanceof BPELPackage)
					continue;
			}
			String nsURI = ePackage.getNsURI();
			Namespace namespace = context.getNamespaceByUri(nsURI);
			if (namespace == null) {
				namespace = buildNamespace(ePackage);
				namespace.setImported(imported);
				namespace.setGlobal(imported);
				context.populateNamespace(namespace);
			}
			namespaces.add(namespace);
		}
		return namespaces;
	}

	public Namespace buildNamespace(EPackage ePackage) throws Exception {
		Namespace namespace = new Namespace();
		namespace.setUri(ePackage.getNsURI());
		namespace.setName(TypeUtil.getJavaPackageFromEPackage(ePackage));
		String nsPrefix = ePackage.getNsPrefix(); 
		//if (nsPrefix == null)
		//	nsPrefix = TypeUtil.getNsPrefixFromEPackage(ePackage);
		nsPrefix = TypeUtil.getNsPrefixFromEPackage(ePackage);
		namespace.setPrefix(nsPrefix);
		//namespace.getImports();

		EList<EObject> eContents = ePackage.eContents();
		Iterator<EObject> iterator = eContents.iterator();
		while (iterator.hasNext()) {
			EObject eObject = iterator.next();
			if (eObject instanceof EAnnotation) {
				buildAnnotation(namespace, (EAnnotation) eObject);
			} else if (eObject instanceof EEnum) {
				buildDataType(namespace, (EEnum) eObject);
			} else if (eObject instanceof EDataType) {
				buildDataType(namespace, (EDataType) eObject);
			} else if (eObject instanceof EClass) {
				buildElement(namespace, (EClass) eObject);
			}
		}
		return namespace;
	}

	public Collection<Namespace> buildNamespaces(EList<Resource> resources) throws Exception {
		Assert.notNull(resources, "Resources must be specified");
		List<Namespace> namespaces = new ArrayList<Namespace>();
		Iterator<Resource> iterator = resources.iterator();
		while (iterator.hasNext()) {
			Resource resource = (Resource) iterator.next();
			if (resource instanceof WSDLResourceImpl) {
				WSDLResourceImpl wsdlResource = (WSDLResourceImpl) resource;
				Definition definition = wsdlResource.getDefinition();
				@SuppressWarnings("unchecked") Collection<Message> messages = definition.getMessages().values();
				Iterator<Message> iterator2 = messages.iterator();
				
				//TODO Take out fow now
				//TODO don't need message types
				//while (iterator2.hasNext()) {
				//	Message message = iterator2.next();
				//	namespaces.add(buildNamespace(message));
				//}
			}
			if (resource instanceof BPELResourceImpl) {
				BPELResourceImpl bpelResource = (BPELResourceImpl) resource;
				org.eclipse.bpel.model.Process process = bpelResource.getProcess();
				namespaces.addAll(buildNamespaces(process));
			}
		}
		
//		populateCache(packageRegistry);
//		List<Namespace> namespaces = new ArrayList<Namespace>();
//		Collection<Object> values = packageRegistry.values();
//		Iterator<Object> iterator = values.iterator();
//		while (iterator.hasNext()) {
//			EPackage ePackage = (EPackage) iterator.next();
//			String ePackageName = ePackage.getName();
//			if (!ePackageName.equals("xsd") && !ePackageName.equals("wsdl")) {
//				Namespace namespace = buildNamespace(ePackage);
//				namespaces.add(namespace);
//			}
//		}

		context.populateNamespaces(namespaces);
		return namespaces;
	}

	public List<Namespace> buildNamespaces(org.eclipse.bpel.model.Process process) throws Exception {
		List<Namespace> namespaces = new ArrayList<Namespace>(); 
		EList<org.eclipse.bpel.model.Import> imports = process.getImports();
		Iterator<org.eclipse.bpel.model.Import> iterator = imports.iterator();
		while (iterator.hasNext()) {
			org.eclipse.bpel.model.Import importObject = iterator.next();
			
			//resolve imports
			ImportResolver[] importResolvers = ImportResolverRegistry.INSTANCE.getResolvers(importObject.getImportType());
			for (ImportResolver importResolver : importResolvers) {
				List<Object> schemaObjects = importResolver.resolve(importObject, ImportResolver.RESOLVE_SCHEMA);
				Iterator<Object> schemaIterator = schemaObjects.iterator();
				while (schemaIterator.hasNext()) {
					XSDSchema xsdSchema = (XSDSchema) schemaIterator.next();
					Namespace namespace = buildNamespace(xsdSchema);
					if (namespace != null) {
						namespaces.add(namespace);
					}
				}
			}
		}
		
		context.populateNamespaces(namespaces);
		return namespaces;
	}
	
	public Namespace buildNamespace(XSDSchema xsdSchema) {
		String targetNamespace = xsdSchema.getTargetNamespace();
		if (targetNamespace != null) {
			Namespace namespace = new Namespace();
			namespace.setUri(targetNamespace);
			namespace.setName(ProjectLevelHelper.getPackageName(targetNamespace));
			namespace.setPrefix(getPrefixFromXSDSchema(xsdSchema));
			return namespace;
		} else {
			//resolve imports
			List<?> contents = xsdSchema.getContents();
			Iterator<?> iterator = contents.iterator();
			while (iterator.hasNext()) {
				Object object = iterator.next();
				if (object instanceof XSDImport) {
					XSDImport xsdImport = (XSDImport) object;
					XSDSchema resolvedXSDSchema = xsdImport.getResolvedSchema();
					Namespace namespace = buildNamespace(resolvedXSDSchema);
					return namespace;
				} else {
					throw new RuntimeException("Unexpected schema content: "+object.getClass().getName());
				}
			}
			return null;
		}
	}

	public Namespace buildNamespace(Message message) throws Exception {
		QName qName = message.getQName();
		Namespace namespace = new Namespace();
		namespace.setUri(qName.getNamespaceURI());
		namespace.setName(ProjectLevelHelper.getPackageName(qName.getNamespaceURI()));
		namespace.setPrefix(qName.getPrefix());
		buildElement(namespace, message);
		return namespace;
	}
	
	public static String getPrefixFromXSDSchema(XSDSchema xsdSchema) {
		String targetNamespace = xsdSchema.getTargetNamespace();
		Map<String, String>  map = xsdSchema.getQNamePrefixToNamespaceMap();
		if (map.values().contains(targetNamespace)) {
			Set<String> prefixes = map.keySet();
			Iterator<String> iterator = prefixes.iterator();
			while (iterator.hasNext()) {
				String prefix = iterator.next();
				String name = map.get(prefix);
				if (name.equals(targetNamespace)) {
					return prefix;
				}
			}
		}
		/*
		 * This is a hack.
		 * We need prefix, the incoming schema doesn't have an entry for its own namespace.
		 * We just take the last segment of the targetNamespace and use that for prefix.
		 */
		String[] segments = targetNamespace.split("/");
		String prefix = segments[segments.length-1];
		return prefix;
	}
	
	protected void buildAnnotation(Namespace namespace, EAnnotation eAnnotation) throws Exception {
		Annotation annotation = buildAnnotation(eAnnotation);
		namespace.getAnnotations().add(annotation);
	}

	protected void buildDataType(Namespace namespace, EEnum eEnum) throws Exception {
		Enumeration enumeration = buildEnumeration(eEnum);
		namespace.getTypesAndEnumerationsAndElements().add(enumeration);
	}

	protected void buildDataType(Namespace namespace, EDataType eDataType) throws Exception {
		//we avoid creating this extra type created by EMF for wrapping enums
		//TODO handle with external property
		if (!eDataType.getName().endsWith("Object")) {
			Type type = buildDataType((EDataType) eDataType);
			if (type != null)
				namespace.getTypesAndEnumerationsAndElements().add(type);
		}
	}
		
	protected void buildElement(Namespace namespace, EClass eClass) throws Exception {
		//TODO make this into a filter and externalize it
		if (eClass.getName().endsWith("DocumentRoot"))
			return;
		Element element = buildElement((EClass) eClass);
		if (element != null)
			namespace.getTypesAndEnumerationsAndElements().add(element);
		if (element == null)
			log.warn("SHOULD NOT HAPPEN");
	}

	protected void buildElement(Namespace namespace, Message message) throws Exception {
		Element element = buildElement(message);
		if (element != null)
			namespace.getTypesAndEnumerationsAndElements().add(element);
		if (element == null)
			log.warn("THIS SHOULD NOT HAPPEN");
	}
	
	public Annotation buildAnnotation(EAnnotation eAnnotation) throws Exception {
		Annotation annotation = new Annotation();
		annotation.setSource(eAnnotation.getSource());
		org.aries.common.Map details = MapUtil.createMap(eAnnotation.getDetails());
		annotation.setDetails(details);
		return annotation;
	}

	public Enumeration buildEnumeration(EEnum eEnum) throws Exception {
		Enumeration enumeration = new Enumeration();
		EPackage ePackage = eEnum.getEPackage();
		//String packageName = TypeUtil.getJavaPackageFromEPackage(ePackage);
		//String className = NameUtil.capName(eEnum.getName());
		String typeName = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(ePackage.getNsURI(), eEnum.getName());
		enumeration.setName(NameUtil.uncapName(eEnum.getName()));
		enumeration.setType(typeName);
		Object defaultValue = eEnum.getDefaultValue();
		if (defaultValue != null && defaultValue instanceof EEnumLiteral) {
			EEnumLiteral eEnumLiteral = (EEnumLiteral) defaultValue;
			enumeration.setDefault(eEnumLiteral.getName().toUpperCase());
		}
		EList<EEnumLiteral> eLiterals = eEnum.getELiterals();
		Iterator<EEnumLiteral> iterator = eLiterals.iterator();
		while (iterator.hasNext()) {
			Literal literal = new Literal();
			EEnumLiteral eEnumLiteral = iterator.next();
			literal.setName(eEnumLiteral.getName().toUpperCase());
			literal.setValue(eEnumLiteral.getValue());
			literal.setLabel(eEnumLiteral.getLiteral());
			enumeration.getLiterals().add(literal);
		}
		enumeration.setCached(true);
		return enumeration;
	}	
	
	public Type buildDataType(EDataType eDataType) throws Exception {
		String packageName = TypeUtil.getJavaPackageFromEPackage(eDataType.getEPackage());
		String className = NameUtil.capName(eDataType.getName());
		if (!CodeGenUtil.isJavaPrimitiveType(className) && !CodeGenUtil.isJavaDefaultType(className)) {
			Type dataType = new Type();
			dataType.setName(NameUtil.uncapName(eDataType.getName()));
			dataType.setType(packageName+"."+className);
			eDataType.getDefaultValue();
			eDataType.getETypeParameters();
			eDataType.getInstanceClass();
			eDataType.getInstanceClassName();
			eDataType.getInstanceTypeName();
			eDataType.getEPackage();
			return dataType;
		}
		return null;
	}	

	public Element buildElement(EClass eClass) throws Exception {
		Element element = new Element();
		element.setName(NameUtil.uncapName(eClass.getName()));
		element.setType(TypeUtil.getTypeFromEClass(eClass));
		//System.out.println("Building Element: "+element.getName());
		EList<EStructuralFeature> eStructuralFeatures = eClass.getEStructuralFeatures();
		buildElement(element, eStructuralFeatures);
		context.populateElement(element);
		return element;
	}

	public Element buildElement(Message message) throws Exception {
		Element element = new Element();
		QName qName = message.getQName();
		String namespace = qName.getNamespaceURI();
		String typeName = TypeUtil.getTypeFromQName(qName);
		String localPart = TypeUtil.getLocalPart(typeName);
		String className = TypeUtil.getClassName(typeName);
		String packageName = TypeUtil.getPackageName(typeName);
		String elementName = NameUtil.uncapName(localPart);
		//System.out.println("Building Element: "+elementName);
		element.setName(elementName);
		element.setType(typeName);
		//element.setRequired(true);

		Definition definition = message.getEnclosingDefinition();
		if (isMessageUsedAsFault(definition, message)) {
			String faultTypeName = org.aries.util.TypeUtil.getTypeFromDefaultType("Exception");
			element.setExtends(faultTypeName);
		}
		
		@SuppressWarnings("unchecked") Collection<Part> parts = message.getParts().values();
		Iterator<Part> iterator = parts.iterator();
		while (iterator.hasNext()) {
			Part part = iterator.next();
			Field field = buildReferenceFromPart(element, part);
			//TODO do something here with field?
		}
		
		context.populateElement(element);
		return element;
	}
	
	protected void buildElement(Element element, EList<EStructuralFeature> eStructuralFeatures) throws Exception {
		boolean isIdFieldFound = false;
		boolean isIdField = false;

		Iterator<EStructuralFeature> iterator = eStructuralFeatures.iterator();
		while (iterator.hasNext()) {
			EStructuralFeature eStructuralFeature = iterator.next();
			EClassifier eType = eStructuralFeature.getEType();
			//if (eStructuralFeature.getName().startsWith("creationDate"))
			//	System.out.println();
			String fieldPackageName = TypeUtil.getJavaPackageFromEPackage(eType.getEPackage());
			String fieldClassName = eType.getName();
			if (fieldPackageName.equals("type")) {
				fieldPackageName = NameUtil.getPackageName(eType.getInstanceClassName());
				fieldClassName = NameUtil.getClassName(eType.getInstanceClassName());
			}
			String fieldTypeName = TypeUtil.getTypeFromEClassifier(eType);
			String fieldJavaName = fieldPackageName+"."+fieldClassName;
			Field field = null;

//			if (eStructuralFeature instanceof EAttribute)
//				System.out.println("Building EAttribute: "+eStructuralFeature.getName()+"["+typeName+"]");
//			if (eStructuralFeature instanceof EReference)
//				System.out.println("Building EReference: "+eStructuralFeature.getName()+"["+typeName+"]");
//			if (eStructuralFeature.getName().startsWith("emailAccount"))
//				System.out.println();

			//primitive types
			if (CodeGenUtil.isJavaPrimitiveType(fieldClassName)) {
				field = buildAttribute(element, eStructuralFeature, fieldTypeName);

			//default types
			} else if (CodeGenUtil.isJavaDefaultType(fieldClassName)) {
				field = buildAttributeFromJavaType(element, eStructuralFeature);
				
			} else if (context.getElementByJavaTypeCache().containsKey(fieldJavaName)) {
				field = buildReferenceFromElement(element, eStructuralFeature, context.getElementByJavaTypeCache().get(fieldJavaName));
				
			} else if (context.getDatatypeCache().containsKey(fieldJavaName)) {
				field = buildAttributeFromDataType(element, eStructuralFeature, context.getDatatypeCache().get(fieldJavaName));
				
			} else if (context.getEnumerationCache().containsKey(fieldJavaName)) {
				field = buildAttributeFromEnumeration(element, eStructuralFeature, context.getEnumerationCache().get(fieldJavaName));

			//TODO handle this from external configuration
			} else if (fieldJavaName.equals("javax.xml.datatype.XMLGregorianCalendar")) {
				field = buildAttributeFromType(element, eStructuralFeature, "{http://www.w3.org/2001/XMLSchema}dateTime");

			} else if (context.getAnnotationCache().containsKey(fieldJavaName)) {
				//nothing here
			}

			isIdField = eStructuralFeature.getName().toLowerCase().equals("id");
			if (isIdField)
				isIdFieldFound = true;
			if (field != null && field instanceof Attribute) {
				Attribute attribute = (Attribute) field;
				attribute.setId(isIdField);
			}
		}
		
		//TODO make this configurable using external property
		boolean includeIdAttribute = false;
		if (!isIdFieldFound && includeIdAttribute) {
			Attribute attribute = buildIdAttribute();
			ElementUtil.addField(element, attribute, 0);

		} else if (!isIdFieldFound) {
			//TODO add this when model is completely ready
			//throw new RuntimeException("No ID field found for Element: " + element.getName());
		}
	}

	
	public Attribute buildIdAttribute() {
		Attribute attribute = new Attribute();
		attribute.setId(true);
		attribute.setName("id");
		attribute.setColumn("id");
		attribute.setType(org.aries.util.TypeUtil.getTypeFromDefaultType("long"));
		attribute.setMaxOccurs(1);
		attribute.setMinOccurs(1);
		//attribute.setNotnull(true);
		attribute.setRequired(true);
		attribute.setChangeable(false);
		attribute.setUnique(true);
		attribute.setIndexed(true);
		attribute.setStructure("item");
		return attribute;
	}
	
	public Element buildElement(EAnnotation eAnnotation) throws Exception {
		EMap<String, String> details = eAnnotation.getDetails();
		Iterator<String> iterator = details.keySet().iterator();
		//log.debug("source"+eAnnotation.getSource());
		while (iterator.hasNext()) {
			String key = iterator.next();
			String value = details.get(key);
			//log.debug("details: key="+key+", value="+value);
		}
		return null;
	}	

//	protected Attribute buildAttribute(Element element, EStructuralFeature eStructuralFeature) {
//		Attribute attribute = buildAttribute(eStructuralFeature);
//		if (attribute != null)
//			element.getAttributes().add(attribute);
//		return attribute;
//	}
//
//	public Attribute buildAttribute(EStructuralFeature eStructuralFeature) {
//		Attribute attribute = new Attribute();
//		initializeAriesField(eStructuralFeature, attribute);
//		return attribute;
//	}
	
	protected Attribute buildAttributeFromJavaType(Element element, EStructuralFeature eStructuralFeature) throws Exception {
		EClassifier eType = eStructuralFeature.getEType();
		String typeName = TypeUtil.getTypeFromEClassifier(eType);
//		String attributeType = eType.getName();
//		String packageName = NameUtil.getPackageName(eType.getName());
//		String className = NameUtil.getClassName(eType.getName());
//		if (packageName != null)
//			System.out.println();
//		Attribute attribute = null;
//		if (CodeGenUtil.isJavaPrimitiveType(attributeType))
//			attribute = buildAttribute(element, eStructuralFeature, attributeType);
//		else if (CodeGenUtil.isJavaLangType(attributeType))
//			attribute = buildAttribute(element, eStructuralFeature, "java.lang."+attributeType);
//		if (attribute == null)
//			System.out.println("SHOULD NOT HAPPEN");
		Attribute attribute = buildAttribute(element, eStructuralFeature, typeName);
		attribute.setManaged(false);
		return attribute;
	}
	
	protected Attribute buildAttributeFromDataType(Element element, EStructuralFeature eStructuralFeature, EDataType eDataType) throws Exception {
//		String packageName = TypeUtil.getJavaPackageFromEPackage(eDataType.getEPackage());
//		String className = NameUtil.capName(eDataType.getName());
//		if (className.endsWith("Object")) {
//			//TODO handle with external property
//			className = className.replace("Object", "");
//		}
		String typeName = TypeUtil.getTypeFromEClassifier(eDataType);
		Attribute attribute = buildAttribute(element, eStructuralFeature, typeName);
		attribute.setManaged(true);
		return attribute;
	}

	protected Attribute buildAttributeFromEnumeration(Element element, EStructuralFeature eStructuralFeature, EEnum eEnum) throws Exception {
		String typeName = TypeUtil.getTypeFromEClassifier(eEnum);
//		String packageName = TypeUtil.getJavaPackageFromEPackage(eEnum.getEPackage());
//		String className = NameUtil.capName(eEnum.getName());
		Attribute attribute = buildAttribute(element, eStructuralFeature, typeName);
		attribute.setManaged(false);
//		attribute.setEnum(true);
		return attribute;
	}

	protected Attribute buildAttributeFromType(Element element, EStructuralFeature eStructuralFeature, String type) throws Exception {
		EClassifier eType = eStructuralFeature.getEType();
//		String packageName = NameUtil.getPackageName(type);
//		String className = NameUtil.getClassName(type);
		Attribute attribute = buildAttribute(element, eStructuralFeature, type);
		attribute.setManaged(false);
		return attribute;
	}

	protected Attribute buildAttribute(Element element, EStructuralFeature eStructuralFeature, String typeName) throws Exception {
		Attribute attribute = new Attribute();
		attribute.setType(typeName);
		attribute.setName(eStructuralFeature.getName());
		attribute.setDefault(eStructuralFeature.getDefaultValueLiteral());
		attribute.setMinOccurs(eStructuralFeature.getLowerBound());
		attribute.setMaxOccurs(eStructuralFeature.getUpperBound());
		attribute.setChangeable(eStructuralFeature.isChangeable());
		attribute.setRequired(eStructuralFeature.isRequired());
		attribute.setUnique(eStructuralFeature.isUnique());
		//By default: treat each unique field as an index
		attribute.setIndexed(eStructuralFeature.isUnique());
		if (eStructuralFeature.getUpperBound() == -1) {
			attribute.setStructure("list");
		} else {
			attribute.setStructure("item");
		}
		//add attribute to element here:
		ElementUtil.addField(element, attribute);
		return attribute;
	}

//	protected Attribute buildAttribute(Element element, EStructuralFeature eStructuralFeature, String packageName, String className) throws Exception {
//		Attribute attribute = new Attribute();
//		attribute.setName(eStructuralFeature.getName());
//		attribute.setType(packageName+"."+className);
//		attribute.setDefault(eStructuralFeature.getDefaultValueLiteral());
//		attribute.setMinOccurs(eStructuralFeature.getLowerBound());
//		attribute.setMaxOccurs(eStructuralFeature.getUpperBound());
//		attribute.setChangeable(eStructuralFeature.isChangeable());
//		attribute.setRequired(eStructuralFeature.isRequired());
//		attribute.setUnique(eStructuralFeature.isUnique());
//		//add attribute to element here:
//		element.getAttributes().add(attribute);
//		return attribute;
//	}
	
	protected Reference buildReferenceFromElement(Element element, EStructuralFeature eStructuralFeature, EClass eClass) throws Exception {
		Reference reference = new Reference();
		reference.setType(TypeUtil.getTypeFromEClass(eClass));
		reference.setName(eStructuralFeature.getName());
		reference.setDefault(eStructuralFeature.getDefaultValueLiteral());
		reference.setMinOccurs(eStructuralFeature.getLowerBound());
		reference.setMaxOccurs(eStructuralFeature.getUpperBound());
		reference.setChangeable(eStructuralFeature.isChangeable());
		reference.setRequired(eStructuralFeature.isRequired());
		if (eStructuralFeature.getUpperBound() == -1) {
			reference.setStructure("list");
		} else {
			reference.setStructure("item");
		}
		//TODO Skip for now - isUnique() is always true here!
		//TODO reference.setUnique(eStructuralFeature.isUnique());
		reference.setManaged(true);
		//add attribute to element here:
		ElementUtil.addField(element, reference);
		return reference;
	}
	
	protected Reference buildReferenceFromPart(Element element, Part part) throws Exception {
		Reference reference = buildReferenceFromElement(element, part.eContainingFeature(), part.eClass());
		String typeName = TypeUtil.getTypeFromMessagePart(part);
		String className = TypeUtil.getClassName(typeName);
		if (CodeGenUtil.isJavaDefaultType(className))
			typeName = org.aries.util.TypeUtil.getTypeFromDefaultType(className);
		reference.setType(typeName);
		reference.setName(part.getName());
		reference.setMaxOccurs(1);
		return reference;
	}
	
	
//	protected Reference buildReference(Element element, EStructuralFeature eStructuralFeature) throws Exception {
//		Reference reference = buildReference(eStructuralFeature);
//		if (reference != null)
//			element.getReferences().add(reference);
//		return reference;
//	}
//
//	public Reference buildReference(EStructuralFeature eStructuralFeature) {
//		Reference reference = new Reference();
//		initializeAriesField(eStructuralFeature, reference);
//		if (reference.getName().equals("emailList"))
//			System.out.println("");
//		reference.setBidirectional(false);
//		reference.setContainment(false);
//		reference.setTransient(false);
//		reference.setDerived(false);
//		reference.setInverse(null);
//		return reference;
//	}
	
//	public void initializeAriesField(EStructuralFeature eStructuralFeature, Field field) throws Exception {
//		EClassifier eType = eStructuralFeature.getEType();
//		if (eType.getName() == null)
//			System.out.println("");
//		if (eStructuralFeature.getName() == null)
//			System.out.println("");
//		field.setName(eStructuralFeature.getName());
//		EObject eObject = context.getElementCache().get(eType.getName());
//		field.setManaged(eObject != null);
//		field.setType(NameUtil.capName(eType.getName()));
//		field.setDefault(eStructuralFeature.getDefaultValueLiteral());
//		field.setMinOccurs(eStructuralFeature.getLowerBound());
//		field.setMaxOccurs(eStructuralFeature.getUpperBound());
//		field.setChangeable(eStructuralFeature.isChangeable());
//		field.setRequired(eStructuralFeature.isRequired());
//		field.setUnique(eStructuralFeature.isUnique());
//
////		eStructuralFeature.getDefaultValue();
////		eStructuralFeature.getEGenericType();
////		eStructuralFeature.getLowerBound();
////		eStructuralFeature.getUpperBound();
////		eStructuralFeature.getEContainingClass();
////		eStructuralFeature.getContainerClass();
////		eStructuralFeature.isChangeable();
////		eStructuralFeature.isDerived();
////		eStructuralFeature.isMany();
////		eStructuralFeature.isOrdered();
////		eStructuralFeature.isUnique();
////		eStructuralFeature.isTransient();
////		eStructuralFeature.isRequired();
////		eStructuralFeature.isVolatile();
//	}

	public Element buildModel(EClassifier eClassifier) {
		Element element = new Element();
		element.setName(eClassifier.getInstanceClassName());
		element.setType(eClassifier.getInstanceTypeName());
		context.populateElement(element);
		return element;
	}

	
	public static boolean isMessageUsedAsFault(Definition definition, Message message) {
		@SuppressWarnings("unchecked") Collection<PortType> values = definition.getPortTypes().values();
		Iterator<PortType> iterator = values.iterator();
		while (iterator.hasNext()) {
			PortType portType = iterator.next();
			boolean value = isMessageUsedAsFault(portType, message);
			if (value == true)
				return true;
		}
		return false;
	}

	public static boolean isMessageUsedAsFault(PortType portType, Message message) {
		@SuppressWarnings("unchecked") Iterator<javax.wsdl.Operation> iterator = portType.getOperations().iterator();
		while (iterator.hasNext()) {
			javax.wsdl.Operation operation = iterator.next();
			boolean value = isMessageUsedAsFault(operation, message);
			if (value == true)
				return true;
		}
		return false;
	}

	public static boolean isMessageUsedAsFault(javax.wsdl.Operation operation, Message message) {
		@SuppressWarnings("unchecked") Iterator<javax.wsdl.Fault> iterator = operation.getFaults().values().iterator();
		while (iterator.hasNext()) {
			javax.wsdl.Fault fault = iterator.next();
			boolean value = fault.getMessage().getQName().equals(message.getQName());
			if (value == true)
				return true;
		}
		return false;
	}

	public Process buildProcess(String name, String namespace, Transacted transacted) throws Exception {
		Process process = new Process();
		process.setName(name + "Process");
		process.setNamespace(namespace);
		process.setTransacted(transacted);
		
//		BPELFactory bpelFactory = BPELFactoryImpl.init();
//		org.eclipse.bpel.model.Process bpelProcess = bpelFactory.createProcess();
//		BPELRuntimeCache.INSTANCE.addProcess(bpelProcess);
//		bpelProcess.setTargetNamespace(module.getNamespace());
//		process.setObject(bpelProcess);
		//process.setCache(value)
		
//		if (name.equals("Shipper"))
//			System.out.println();
		
		//ProcessUtil.addOperation(process, buildProcessOperation_GetStateManager(name, namespace));
		return process;
	}

//	public List<Operation> buildProcessOperations() {
//		List<Operation> operations = new ArrayList<Operation>();
//		operations.add(buildProcessOperation_GetStateManager());
//		return operations;
//	}

//	public Operation buildProcessOperation_GetStateManager(String baseName, String namespace) throws Exception {
//		Operation operation = new Operation();
//		operation.setName("getStateManager");
//		Result result = new Result();
//		String localPart = NameUtil.uncapName(baseName) + "StateManager";
//		String resultType = TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, localPart);
//		result.setType(resultType);
//		result.setName("stateManager");
//		operation.setResult(result);
//		return operation;
//	}
	
}
