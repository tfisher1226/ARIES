package org.aries.ast;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nam.model.Adapter;
import nam.model.Application;
import nam.model.Applications;
import nam.model.Cache;
import nam.model.Callback;
import nam.model.Channel;
import nam.model.Command;
import nam.model.Definition;
import nam.model.Dependency;
import nam.model.DependencyScope;
import nam.model.DependencyType;
import nam.model.Direction;
import nam.model.Domain;
import nam.model.Element;
import nam.model.Elements;
import nam.model.Fault;
import nam.model.Field;
import nam.model.Group;
import nam.model.Import;
import nam.model.Include;
import nam.model.Information;
import nam.model.Interaction;
import nam.model.Interactor;
import nam.model.Invoker;
import nam.model.Item;
import nam.model.ListItem;
import nam.model.Listener;
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
import nam.model.Placeholders;
import nam.model.Process;
import nam.model.Project;
import nam.model.Properties;
import nam.model.Query;
import nam.model.Result;
import nam.model.Sender;
import nam.model.Service;
import nam.model.Services;
import nam.model.SetItem;
import nam.model.Source;
import nam.model.Transacted;
import nam.model.TransactionScope;
import nam.model.TransactionUsage;
import nam.model.Type;
import nam.model.Unit;
import nam.model.statement.DefinitionCommand;
import nam.model.statement.ExpressionStatement;
import nam.model.util.ApplicationUtil;
import nam.model.util.CacheUtil;
import nam.model.util.DomainUtil;
import nam.model.util.ElementUtil;
import nam.model.util.ExtensionsUtil;
import nam.model.util.ModuleUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.PersistenceUtil;
import nam.model.util.PlaceholderUtil;
import nam.model.util.ProcessUtil;
import nam.model.util.ProjectUtil;
import nam.model.util.ServiceUtil;
import nam.model.util.ServicesUtil;
import nam.model.util.TypeUtil;
import nam.model.util.UnitUtil;
import nam.service.ServiceLayerHelper;
import nam.ui.View;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.ast.node.CacheNode;
import org.aries.ast.node.ChannelNode;
import org.aries.ast.node.CommandNode;
import org.aries.ast.node.DefinitionNode;
import org.aries.ast.node.EndpointNode;
import org.aries.ast.node.ExceptionNode;
import org.aries.ast.node.ExpressionNode;
import org.aries.ast.node.GroupNode;
import org.aries.ast.node.ImportNode;
import org.aries.ast.node.InvokeNode;
import org.aries.ast.node.ItemNode;
import org.aries.ast.node.ListenNode;
import org.aries.ast.node.MessageNode;
import org.aries.ast.node.MethodNode;
import org.aries.ast.node.NetworkNode;
import org.aries.ast.node.OptionNode;
import org.aries.ast.node.ParameterNode;
import org.aries.ast.node.ParticipantNode;
import org.aries.ast.node.PersistanceNode;
import org.aries.ast.node.PropertyNode;
import org.aries.ast.node.ReceiveNode;
import org.aries.ast.node.ReplyNode;
import org.aries.ast.node.RoleNode;
import org.aries.ast.node.SendNode;
import org.aries.ast.node.StatementNode;
import org.aries.ast.node.ThrowsNode;
import org.aries.ast.node.TimeoutNode;
import org.aries.ast.node.TypeNode;
import org.aries.ast.node.control.IfNode;
import org.aries.ast.node.control.UnnamedNode;
import org.aries.util.NameUtil;
import org.eclipse.emf.codegen.util.CodeGenUtil;

import aries.generation.AriesModelUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.engine.GeneratorEngine;


public class AriesModelBuilder {

	private static Log log = LogFactory.getLog(AriesModelBuilder.class);
	
	private AriesModelUtil util;

	private GenerationContext context;

	private GeneratorEngine engine;

	private Project project;
	
	private Application application;

	private Module module;

	private Process process;

	private Service service;

	private Operation operation;

	private Map<String, Project> projectsByName;

	//private Map<String, Namespace> namespacesByPrefix;
	
	private Map<String, Application> applicationsByName;

	private Map<String, Application> applicationsByService;
	
	private AriesCommandFactory ariesCommandFactory;

	private Domain domain;


	public AriesModelBuilder(GeneratorEngine engine) {
		this.engine = engine;
		this.context = engine.getContext(); 
		this.util = new AriesModelUtil(context);
		this.ariesCommandFactory = new AriesCommandFactory(context);
		AriesASTUtil.context = context;
	}
	
	public Placeholders buildPlaceholdersFromImportNodes(List<ImportNode> importNodes) throws Exception {
		Placeholders placeholders = new Placeholders();
		context.setPlaceholders(placeholders);
		context.getNamespacesByPrefix().clear();
		Iterator<ImportNode> iterator = importNodes.iterator();
		while (iterator.hasNext()) {
			ImportNode importNode = iterator.next();
			Serializable model = buildPlaceholderFromImportNode(placeholders, importNode);
			importNode.setImportedObject(model);
		}
		return placeholders;
	}

	private Serializable buildPlaceholderFromImportNode(Placeholders placeholders, ImportNode importNode) throws Exception {
		context.setRuntimeFileContext(importNode.getParentDirectory());
		Serializable model = buildPlaceholderFromImportNode(importNode);
		PlaceholderUtil.addPlaceholder(placeholders, model);
		String modelName = importNode.getName();

		if (model instanceof Project) {
			Project project = (Project) model;
			context.setProject(project);
			context.addProject(project);
			
			if (project.getModules() == null)
				project.setModules(new Modules());
//				if (project.getExtensions() == null)
//					project.setExtensions(ExtensionsUtil.newExtensions());
//				if (project.getInformation() == null)
//					project.setInformation(new Information());
//				if (project.getPersistence() == null)
//					project.setPersistence(new Persistence());
//				if (project.getMessaging() == null)
//					project.setMessaging(MessagingUtil.newMessaging());

			ProjectUtil.addImport(project, buildImportFromImportNode(importNode));
			engine.getModelHelper().assureProjectImports(project);
			engine.getModelHelper().processProjectImports(project);
			engine.getModelHelper().assureProjectObjects(project);
			engine.getModelHelper().buildProjectModules(project);
			engine.getModelHelper().assureProjectObjects(project);
			
		} else if (model instanceof Applications) {
			engine.getModelHelper().processImportedApplicationsModel(null, (Applications) model);
		} else if (model instanceof Services) {
			engine.getModelHelper().processImportedServicesModel(null, (Services) model);
		} else if (model instanceof Information) {
			engine.getModelHelper().processImportedInformationModel(null, (Information) model, modelName);
		} else if (model instanceof Persistence) {
			engine.getModelHelper().processImportedPersistenceModel(null, (Persistence) model, modelName);
		} else if (model instanceof Messaging) {
			engine.getModelHelper().processImportedMessagingModel(null, (Messaging) model);
		}
		
		return model;
	}
	
	public Placeholders buildPlaceholdersFromImports(List<Import> imports) throws Exception {
		Placeholders placeholders = new Placeholders();
		context.setPlaceholders(placeholders);
		//context.getNamespacesByPrefix().clear();
		Iterator<Import> iterator = imports.iterator();
		while (iterator.hasNext()) {
			Import importObject = iterator.next();
			//skip XSD, WSDL and BPEL imports
			String type = importObject.getType();
			if (type != null) {
				if (type.equals("xsd"))
					continue;
				if (type.equals("wsdl"))
					continue;
				if (type.equals("bpel"))
					continue;
			}
			buildPlaceholderFromImport(placeholders, importObject);
		}
		return placeholders;
	}
	
	private Serializable buildPlaceholderFromImport(Placeholders placeholders, Import importObject) throws Exception {
		String modelName = NameUtil.getBaseName(importObject.getFile());
		context.setRuntimeFileContext(importObject.getDir());
		Serializable model = buildPlaceholderFromImport(importObject);
		PlaceholderUtil.addPlaceholder(placeholders, model);

		if (model instanceof Project) {
			Project project = (Project) model;
			context.setProject(project);
			
			if (project.getModules() == null)
				project.setModules(new Modules());
//				if (project.getExtensions() == null)
//					project.setExtensions(ExtensionsUtil.newExtensions());
//				if (project.getInformation() == null)
//					project.setInformation(new Information());
//				if (project.getPersistence() == null)
//					project.setPersistence(new Persistence());
//				if (project.getMessaging() == null)
//					project.setMessaging(MessagingUtil.newMessaging());

			ProjectUtil.addImport(project, importObject);
			engine.getModelHelper().assureProjectImports(project);
			engine.getModelHelper().processProjectImports(project);
			engine.getModelHelper().buildProjectModules(project);
			engine.getModelHelper().assureProjectObjects(project);
			
		} else if (model instanceof Applications) {
			engine.getModelHelper().processImportedApplicationsModel(null, (Applications) model);
		} else if (model instanceof Services) {
			engine.getModelHelper().processImportedServicesModel(null, (Services) model);
		} else if (model instanceof Information) {
			engine.getModelHelper().processImportedInformationModel(null, (Information) model, modelName);
		} else if (model instanceof Persistence) {
			engine.getModelHelper().processImportedPersistenceModel(null, (Persistence) model, modelName);
		} else if (model instanceof Messaging) {
			engine.getModelHelper().processImportedMessagingModel(null, (Messaging) model);
		} else if (model instanceof View) {
			engine.getModelHelper().processImportedViewModel(null, (View) model);
		}
		
		return model;
	}
	
	public Import buildImportFromImportNode(ImportNode importNode) {
		Import importedFile = new Import();
		importedFile.setDir(importNode.getParentDirectory());
		importedFile.setFile(importNode.getName() + "." + importNode.getValue());
		importedFile.setType(importNode.getValue());
		importedFile.setObject(importNode.getImportedObject());
		return importedFile;
	}

	public Serializable buildPlaceholderFromImportNode(ImportNode importNode) throws Exception {
		String fileName = importNode.getName() + "." + importNode.getValue();
       	String blockName = importNode.getName();
		String parentDirectory = importNode.getParentDirectory();
		if (parentDirectory == null)
			parentDirectory = "/model";
		String filePath = parentDirectory + "/" + fileName;
		importNode.setFilePath(filePath);
       	Serializable importedObject = buildPlaceholderFromImportNode(filePath, blockName);
       	importNode.setImportedObject(importedObject);
       	return importedObject;
	}

	public Serializable buildPlaceholderFromImport(Import importObject) throws Exception {
       	String blockName = NameUtil.getBaseName(importObject.getFile());
       	//String parentDirectory = importObject.getDir();
		String fileName = importObject.getFile();
		String filePath = context.getFilePath(context.getCacheLocation() + "/model", null, fileName);
       	Serializable importedObject = buildPlaceholderFromImportNode(filePath, blockName);
       	importObject.setObject(importedObject);
       	return importedObject;
	}
	
	public Serializable buildPlaceholderFromImportNode(String filePath, String blockName) throws Exception {
		String normalizedPath = NameUtil.normalizePath(filePath);
		//System.out.println(">>>"+normalizedPath);
       	Serializable placeholder = engine.getModelParser().unmarshalFromFileSystem(normalizedPath);
       	
       	/*
       	 * TODO these adjustments being below (right before 
       	 * we return the placeholder) should be pulled out 
       	 * of here and put into a "formal" pass that would 
       	 * take place sometime after this method is called. 
       	 */
		if (placeholder instanceof Information) {
       		Information information = (Information) placeholder;
       		information.setImported(true);
       		if (information.getName() == null)
       			information.setName(blockName);
       	} else if (placeholder instanceof Messaging) {
       		Messaging messaging = (Messaging) placeholder;
       		messaging.setImported(true);
       		if (messaging.getName() == null)
       			messaging.setName(blockName);
       	} else if (placeholder instanceof Persistence) {
       		Persistence persistence = (Persistence) placeholder;
       		persistence.setImported(true);
       		if (persistence.getName() == null)
       			persistence.setName(blockName);
       	} else if (placeholder instanceof View) {
       		//System.out.println();
       	}
		return placeholder;
	}
	
	public Map<String, Namespace> buildNamespacesFromImportNodes(List<ImportNode> importNodes) throws Exception {
		engine.getContext().getNamespacesByPrefix().clear();
		Iterator<ImportNode> iterator = importNodes.iterator();
		while (iterator.hasNext()) {
			ImportNode importNode = iterator.next();
			List<Namespace> list = buildNamespaceFromImportNode(importNode);
			Iterator<Namespace> iterator2 = list.iterator();
			while (iterator2.hasNext()) {
				Namespace namespace = (Namespace) iterator2.next();
				context.populateNamespace(namespace);
			}
		}
		return engine.getContext().getNamespacesByPrefix();
	}

	public List<Namespace> buildNamespaceFromImportNode(ImportNode importNode) throws Exception {
		String fileName = "";
		if (importNode.getParentDirectory() != null)
			fileName += importNode.getParentDirectory() + File.separator;
		fileName += importNode.getName() + "." + importNode.getValue();
       	String[] fileNames = new String[] {fileName};
		List<Namespace> namespaces = engine.createNamespaces(fileNames);
		return namespaces;
	}

	/**
	 * Builds an empty project with a single pom Module.
	 * @throws Exception 
	 */
	public Project buildProject(String domain, String name, String namespace, String version) throws Exception {
		Project project = new Project();
		project.setName(NameUtil.uncapName(name));
		project.setDomain(domain);
		if (project.getDomain() == null)
			project.setDomain(project.getName());
		project.setVersion(version);
		project.setModules(new Modules());
		project.setExtensions(ExtensionsUtil.newExtensions());
		//project.setInformation(InformationUtil.newInformation());
		//project.setPersistence(PersistenceUtil.newPersistence());
		//project.setMessaging(MessagingUtil.newMessaging());
		project.setNamespace(namespace);
		if (project.getNamespace() == null)
			project.setNamespace("http://"+NameUtil.splitStringUsingUnderscores(project.getName()));
		context.setProject(project);
		
		if (context.canGeneratePom())
			ProjectUtil.addModule(project, buildProjectPOMModule(project));
		postProcessProjectInitialiation(project);
		return project;
	}
	
	public Project buildProjectFromProtocolNode(NetworkNode networkNode, Placeholders placeholders) throws Exception {
		AriesASTContext.networkNode = networkNode;
		project = new Project();
		project.setName(AriesASTUtil.getPropertyValue(networkNode, "name"));
		project.setDomain(AriesASTUtil.getPropertyValue(networkNode, "domain"));
		if (project.getName() == null)
			project.setName(NameUtil.uncapName(networkNode.getName()));
		if (project.getDomain() == null)
			project.setDomain(project.getName());
		project.setVersion("0.0.1-SNAPSHOT");
		project.setModules(new Modules());
		project.setExtensions(ExtensionsUtil.newExtensions());
		//project.setInformation(InformationUtil.newInformation());
		//project.setPersistence(PersistenceUtil.newPersistence());
		//project.setMessaging(MessagingUtil.newMessaging());
		String propertyValue = AriesASTUtil.getPropertyValue(networkNode, "namespace");
		if (!StringUtils.isEmpty(propertyValue) && !propertyValue.startsWith("http://"))
			propertyValue = "http://" + propertyValue;
		project.setNamespace(propertyValue);
		if (project.getNamespace() == null)
			project.setNamespace("http://"+NameUtil.splitStringUsingUnderscores(project.getName()));
		context.setProject(project);
		
		ProjectUtil.addModule(project, buildProjectPOMModule(project));
		ProjectUtil.addModule(project, buildModelModuleForProjectNamespace(project));
		//ProjectUtil.addModule(project, buildProjectViewModule(project));
		
		Collection<Application> applications = buildApplicationsFromParticipantNodes(networkNode.getParticipantNodes());
		List<Group> groups = buildGroupsFromGroupNodes(networkNode.getGroupNodes());
		ProjectUtil.addApplications(project, applications);
		ProjectUtil.addGroups(project, groups);
		postProcessProjectInitialiation(project);
		return project;
	}

	protected Module buildProjectPOMModule(Project project) throws Exception {
		Module module = engine.getModelBuilder().buildProjectPOMModule(project);
		return module;
	}

	protected Module buildModelModuleForProjectNamespace(Project project) throws Exception {
		//String namespaceProperty = AriesASTUtil.getPropertyValue(AriesASTContext.networkNode, "namespace");
		Module module = engine.getModelBuilder().buildProjectModelModule(project);
		return module;
	}
	
	protected List<Module> buildModelModulesFromPlaceholders(Placeholders placeholders) throws Exception {
		Map<String, Namespace> importedNamespaces = PlaceholderUtil.getImportedNamespaces(placeholders);
		List<Module> modules = buildModelModulesFromNamespaces(importedNamespaces);
		ProjectUtil.getNamespaces(project).addAll(importedNamespaces.values());
		return modules;
	}
	
	protected List<Module> buildModelModulesFromNamespaces(Map<String, Namespace> namespaces) throws Exception {
		List<Module> modules = new ArrayList<Module>();
		Iterator<Namespace> iterator = namespaces.values().iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			if (project.getNamespace().equals(namespace.getUri()))
				continue;
			//TODO use include statement in addition to import statements to  
			//distinguish whether or not wish wish to create a local project for it 
			if (namespace.getName().equals("common"))
				continue;
			modules.add(engine.getModelBuilder().buildModelModuleFromNamespace(project, namespace));
		}
		return modules;
	}

	protected List<Group> buildGroupsFromGroupNodes(List<GroupNode> groupNodes) throws Exception {
		List<Group> groups = new ArrayList<Group>();
		Iterator<GroupNode> iterator = groupNodes.iterator();
		while (iterator.hasNext()) {
			GroupNode groupNode = iterator.next();
			Group group = buildGroupFromGroupNode(groupNode);
			groups.add(group);
		}
		return groups;
	}

	protected Group buildGroupFromGroupNode(GroupNode groupNode) throws Exception {
		Group group = new Group();
		group.setName(groupNode.getName());
		Set<PropertyNode> propertyNodes = groupNode.getPropertyNodes();
		Iterator<PropertyNode> iterator = propertyNodes.iterator();
		while (iterator.hasNext()) {
			PropertyNode propertyNode = (PropertyNode) iterator.next();
			if (propertyNode.getName().equals("participant")) {
				String participantName = propertyNode.getValue();
				//String projectName = getProjectNamespaceName();
				Application application = applicationsByName.get(project.getName() + "-" + NameUtil.uncapName(participantName));
				Assert.notNull(application, "Participant not found: "+participantName);
				group.getApplications().add(application);
				group.setParticipantName(participantName);
			}
		}
		return group;
	}

	protected List<Application> buildApplicationsFromParticipantNodes(List<ParticipantNode> participantNodes) throws Exception {
		applicationsByName = new HashMap<String, Application>();
		applicationsByService = new HashMap<String, Application>();
		Iterator<ParticipantNode> iterator = participantNodes.iterator();
		while (iterator.hasNext()) {
			ParticipantNode participantNode = iterator.next();
			Application application = buildApplicationFromParticipantNode(participantNode);
			applicationsByName.put(application.getArtifactId(), application);
		}
		ArrayList<Application> list = new ArrayList<Application>(applicationsByName.values());
		return list;
	}

	protected Application buildApplicationFromParticipantNode(ParticipantNode participantNode) throws Exception {
		application = createApplication(participantNode);
		domain = createDomain(application);

		boolean modelModuleRequired = processModelModuleRequirement(participantNode);
		boolean dataModuleRequired = processDataModuleRequirement(participantNode);

		ApplicationUtil.addProjectNames(application, getProjectNamesFromParticipantNode(participantNode));
		ApplicationUtil.addChannels(application, buildChannelsFromParticipantNode(participantNode));
		ApplicationUtil.addModule(application, buildPOMModuleFromParticipantNode(participantNode));
		ApplicationUtil.addModule(application, buildEARModuleFromParticipantNode(participantNode));
		if (modelModuleRequired)
			ApplicationUtil.addModule(application, buildModelModuleFromParticipantNode(participantNode));
		if (dataModuleRequired)
			ApplicationUtil.addModule(application, buildDataModuleFromParticipantNode(participantNode));
		ApplicationUtil.addModule(application, buildServiceModuleFromParticipantNode(participantNode));
		ApplicationUtil.addModule(application, buildClientModuleFromParticipantNode(participantNode));
		ApplicationUtil.addModule(application, buildViewModuleFromParticipantNode(participantNode));
		return application;
	}

	//TODO take first from receiveNode, then from participantNode, otherwise default to getting from networkNode
	protected List<String> getProjectNamesFromParticipantNode(ParticipantNode participantNode) throws Exception {
		List<String> projectNames = new ArrayList<String>();
		Set<PropertyNode> propertyNodes = participantNode.getPropertyNodes();
		Iterator<PropertyNode> iterator = propertyNodes.iterator();
		while (iterator.hasNext()) {
			PropertyNode propertyNode = iterator.next();
			String name = propertyNode.getName();
			String value = propertyNode.getValue();
			if (name.equals("project")) {
				projectNames.add(value);
			}
		}
		return projectNames;
	}
	
	protected List<Namespace> getImportedNamespaces(ParticipantNode participantNode) {
		participantNode.getPropertyNodes("namespace");
		return null;
	}

	protected Module buildProjectViewModule(Project project) throws Exception {
		module = new Module();
		module.setType(ModuleType.VIEW);
		module.setLevel(ModuleLevel.APPLICATION_LEVEL);
		module.setGroupId(project.getName());
		module.setArtifactId(project.getName()+"-view");
		module.setName(project.getName()+"-view");
		module.setNamespace(project.getNamespace());
		module.setVersion("0.0.1-SNAPSHOT");
		
		Namespace namespace = context.getNamespaceByUri(module.getNamespace());
		Information information = ProjectUtil.getInformationBlock(project, namespace);
		//InformationUtil.addNamespace(information, namespace);
		module.setInformation(information);
		return module;
	}
	
	protected Module buildViewModuleFromParticipantNode(ParticipantNode participantNode) throws Exception {
		module = new Module();
		module.setType(ModuleType.VIEW);
		module.setLevel(ModuleLevel.APPLICATION_LEVEL);
		module.setGroupId(application.getGroupId());
		//TODO - Externalize these
		module.setArtifactId(application.getArtifactId() + "-ui");
		module.setName(application.getArtifactId() + "-ui");
		module.setNamespace(application.getNamespace());
		module.setVersion("0.0.1-SNAPSHOT");

//		//assuming domain has already been created and populated
//		Services services = new Services();
//		ServicesUtil.addDomain(services, domain);
//		module.setServices(services);
//
//		Process process = buildProcessFromParticipantNode(participantNode);
//		ModuleUtil.addProcesses(module, process);
//
//		List<Cache> cacheUnits = buildCacheUnitsFromParticipantNode(participantNode);
//		process.getCacheUnits().addAll(cacheUnits);
//		
//		//ModuleUtil.addServices(module, buildServicesFromParticipantNode(participantNode));
//		module.getInteractors().addAll(buildInteractorsFromParticipantNode(participantNode));
		
		Project project = context.getProject();
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
	
	protected Application createApplication(ParticipantNode participantNode) throws Exception {
		String applicationName = NameUtil.uncapName(participantNode.getName());
		
//		String artifactId = applicationName;
//		if (context.getPropertyAsBoolean("prependProjectNameToArtifactId"))
//			artifactId = project.getName()+"-"+applicationName;

		String groupId = project.getDomain();
		if (context.getPropertyAsBoolean("appendProjectToGroupId"))
			groupId += "-" + project.getName();
		groupId += "-" + applicationName;
		
		String artifactId = project.getName();
		if (AriesASTContext.networkNode.getParticipantNodes().size() > 1) {
			artifactId += "-" + applicationName;
			
			if (context.getPropertyAsBoolean("appendApplicationToGroupId"))
				groupId += "-" + applicationName;
		}
		
		application = new Application();
		application.setName(applicationName);
		application.setGroupId(groupId);
		application.setArtifactId(artifactId);
		application.setVersion("0.0.1-SNAPSHOT");
		application.setContextRoot(artifactId);
		application.setModules(new Modules());
		
		//TODO
		//application.setInformation(new Information());
		//application.setPersistence(new Persistence());
		
		//TODO put this into verification/assurance stage of AST
		String namespaceUri = participantNode.getNamespace();
		//if (namespaceUri == null)
		//	namespaceUri = AriesASTUtil.getPropertyValue(AriesASTContext.networkNode, "namespace");
		if (namespaceUri != null) {
			//if (!namespaceUri.startsWith("http://"))
			//	namespaceUri = "http://" + namespaceUri;
			participantNode.setNamespace(namespaceUri);
		}
		if (participantNode.getNamespace() == null)		
			participantNode.setNamespace(project.getNamespace());
		application.setNamespace(participantNode.getNamespace());
		return application;
	}

	protected Domain createDomain(Application application) throws Exception {
		Domain domain = new Domain();
		//String domainPackageName = application.getGroupId()+"."+application.getName();
		//String domainPackageName = NameUtil.getPackageNameFromNamespace(application.getNamespace());
		Namespace domainNamespace = context.getNamespaceByUri(application.getNamespace());
		String projectNamespaceUri = context.getProject().getNamespace();
		Namespace projectNamespace = context.getNamespaceByUri(projectNamespaceUri);
		
		/*
		 * TODO we need this?
		 * TODO no default namespace for now..
		 * Namespace's are now only created inside InformationBlocks, 
		 * or created from ARIEL "cache" or "persist" nodes.
		 */
		if (domainNamespace == null) {
			domainNamespace = new Namespace();
			//String name = util.getProjectNamespaceName(project);
			//domainNamespace.setName(name+"-"+application.getName());
			domainNamespace.setName(project.getName()+"-"+application.getName());
			domainNamespace.setPrefix(application.getName());
			domainNamespace.setUri(application.getNamespace());
//			if (projectNamespace == null)
//				System.out.println();
			if (projectNamespace != null) {
				NamespaceUtil.addImportedNamespace(domainNamespace, projectNamespace);
				context.populateNamespace(domainNamespace);
			}
		}
		
		//XXX - For now: This is where domain name gets configured
		//XXX - This should be externalized and properly described in plain English
		String domainName = project.getDomain();
		String applicationPart = application.getGroupId();
		String firstSegment = NameUtil.getFirstSegment(applicationPart, "-");
		String lastSegment = NameUtil.getLastSegment(domainName, ".");
		if (lastSegment.equalsIgnoreCase(firstSegment))
			if (!applicationPart.equalsIgnoreCase(firstSegment))
				applicationPart = applicationPart.substring(firstSegment.length() + 1);
		//domain.setName(project.getDomain() + "-" + application.getGroupId());
		//domainName = project.getDomain() + "." + applicationPart;
		
		if (context.getPropertyAsBoolean("addApplicationToGroupId"))
			domainName += "." + applicationPart;
			
		domain.setName(domainName);
		domain.setNamespace(domainNamespace);
		return domain;
	}
	
	/**
	 * Always false for now. This results in creating NO ModelModules from ARIEL.  
	 * ModelModules are only created from Information-blocks created in ARIES.
	 */
	protected boolean processModelModuleRequirement(ParticipantNode participantNode) throws Exception {
		String namespaceUri = participantNode.getNamespace();
		if (namespaceUri == null)
			namespaceUri = project.getNamespace();
		Namespace namespace = context.getNamespaceByUri(namespaceUri);
		if (namespace == null)
			return false;
		boolean importedBlocksContainNamespace = importedBlocksContainNamespace(namespace);
		return importedBlocksContainNamespace != true;
	}
	
	protected boolean importedBlocksContainNamespace(Namespace namespace) {
		Iterator<ImportNode> iterator = AriesASTContext.importNodes.iterator();
		while (iterator.hasNext()) {
			ImportNode importNode = iterator.next();
			boolean importedBlocksContainNamespace = importedBlocksContainNamespace(importNode, namespace);
			if (importedBlocksContainNamespace) {
				return true;
			}
		}
		return false;
	}

	protected boolean importedBlocksContainNamespace(ImportNode importNode, Namespace namespace) {
		Object importedObject = importNode.getImportedObject();
		if (importedObject instanceof Information) {
			Information informationBlock = (Information) importedObject;
			List<Namespace> namespaces = informationBlock.getNamespaces();
			Iterator<Namespace> iterator = namespaces.iterator();
			while (iterator.hasNext()) {
				Namespace importedNamespace = iterator.next();
				if (importedNamespace.getUri().equals(namespace.getUri())) {
					return true;
				}
			}
		}
		return false;
	}

	protected boolean processDataModuleRequirement(ParticipantNode participantNode) throws Exception {
		//String applicationName = NameUtil.uncapName(participantNode.getName());
		//String namespaceUri = util.getApplicationNamespaceUri(project, applicationName);
		List<PersistanceNode> persistanceNodes = participantNode.getPersistanceNodes();
		Iterator<PersistanceNode> iterator = persistanceNodes.iterator(); 
		while (iterator.hasNext()) {
			PersistanceNode persistanceNode = iterator.next();
			persistanceNode.setDomain(domain.getName());
			
		}
		return persistanceNodes.size() > 0;
	}
	

	protected Module buildPOMModuleFromParticipantNode(ParticipantNode participantNode) throws Exception {
		module = new Module();
		module.setType(ModuleType.POM);
		module.setLevel(ModuleLevel.APPLICATION_LEVEL);
		module.setName(application.getArtifactId());
		module.setGroupId(application.getGroupId());
		module.setArtifactId(application.getArtifactId());
		module.setNamespace(application.getNamespace());
		module.setVersion("0.0.1-SNAPSHOT");
		return module;
	}

	protected Module buildEARModuleFromParticipantNode(ParticipantNode participantNode) throws Exception {
		module = new Module();
		module.setType(ModuleType.EAR);
		module.setLevel(ModuleLevel.APPLICATION_LEVEL);
		module.setGroupId(application.getGroupId());
		//TODO - Externalize these
		module.setArtifactId(application.getArtifactId() + "-app");
		module.setName(application.getArtifactId() + "-app");
		module.setNamespace(application.getNamespace());
		module.setVersion("0.0.1-SNAPSHOT");
		return module;
	}

	//Always return null for now - ModelModules not to be created from ARIEL
	protected Module buildModelModuleFromParticipantNode(ParticipantNode participantNode) throws Exception {
		List<PersistanceNode> persistanceNodes = participantNode.getPersistanceNodes();
		List<CacheNode> cacheNodes = participantNode.getCacheNodes();
		boolean hasPersistenceElements = persistanceNodes.size() > 0;
		boolean hasCacheElements = cacheNodes.size() > 0;
		//if (hasPersistenceElements || hasCacheElements || false)
		//	return null;

		module = new Module();
		module.setType(ModuleType.MODEL);
		module.setLevel(ModuleLevel.APPLICATION_LEVEL);
		module.setGroupId(application.getGroupId());
		//TODO - Externalize these
		module.setArtifactId(application.getArtifactId() + "-model");
		module.setName(application.getArtifactId() + "-model");
		module.setNamespace(application.getNamespace());
		module.setVersion("0.0.1-SNAPSHOT");

		Namespace namespace = context.getNamespaceByUri(module.getNamespace());
		Information informationBlock = buildInformationBlockFromParticipantNode(participantNode, module);
		ExtensionsUtil.addExtension(project, informationBlock);
		//context.addModuleByNamespace(namespace, module);
		return module;
	}
	
	protected Information buildInformationBlockFromParticipantNode(ParticipantNode participantNode, Module module) {
		String moduleNamespace = module.getNamespace();
		Namespace namespace = context.getNamespaceByUri(moduleNamespace);
		Assert.notNull(namespace, "Namespace not found: "+moduleNamespace);
		Information informationBlock = engine.getModelBuilder().buildInformationForNamespace(module, namespace);
		//TODO make this informationBlock name suffix come from external config file
		informationBlock.setName(namespace.getName());
		informationBlock.setDomain(project.getDomain());
		module.setInformation(informationBlock);
		return informationBlock;
	}

	protected Cache buildCacheUnitFromCacheNode(String namespaceUri, CacheNode cacheNode) throws Exception {
		String name = cacheNode.getName();
		String type = "{" + namespaceUri + "}" + NameUtil.uncapName(name);

		Cache cache = new Cache();
		cache.setRoot(true);
		cache.setPublic(true);
		cache.setName(name);
		cache.setType(type);
		
		List<ItemNode> itemNodes = cacheNode.getItemNodes();
		Iterator<ItemNode> iterator2 = itemNodes.iterator();
		while (iterator2.hasNext()) {
			ItemNode itemNode = iterator2.next();
			Field field = buildItemFromItemNode(itemNode);
			CacheUtil.addItem(cache, field);
		}
		
		Namespace namespace = context.getNamespaceByUri(namespaceUri);
		context.getEngine().getModelHelper().convertItems(namespace, cache);
		context.populateElement(cache);
		return cache;
	}
	
	protected Field buildItemFromItemNode(ItemNode itemNode) throws Exception {
		String name = itemNode.getName();
		TypeNode typeNode = itemNode.getTypeNode();
		String type = AriesASTUtil.getFieldTypeFromTypeNode(typeNode);
		String key = AriesASTUtil.getFieldKeyTypeFromTypeNodeType(typeNode);

		Field field = createField(itemNode.getConstruct());
		field.setName(name);
		field.setType(type);
		field.setKey(key);
		field.setManaged(true);
		
		Assert.notNull(field.getType(), "Element not found: "+type);
		return field;
	}

	protected Field createField(String construct) throws Exception {
		Field field = null;
		construct = construct.toLowerCase();
		if (construct.equals("item")) {
			field = new Item();
		} else if (construct.equals("list")) {
			field = new ListItem();
		} else if (construct.equals("set")) {
			field = new SetItem();
		} else if (construct.equals("map")) {
			field = new MapItem();
		}
		field.setStructure(construct);
		return field;
	}
	
	protected Module buildDataModuleFromParticipantNode(ParticipantNode participantNode) throws Exception {
		List<PersistanceNode> persistanceNodes = participantNode.getPersistanceNodes();
		boolean hasPersistenceElements = persistanceNodes.size() > 0;
		if (!hasPersistenceElements)
			return null;
		
		module = new Module();
		module.setType(ModuleType.DATA);
		module.setLevel(ModuleLevel.APPLICATION_LEVEL);
		module.setGroupId(application.getGroupId());
		//TODO - Externalize these
		module.setArtifactId(application.getArtifactId() + "-persistence");
		module.setName(application.getArtifactId() + "-persistence");
		module.setNamespace(application.getNamespace());
		module.setVersion("0.0.1-SNAPSHOT");

		Information informationBlock = buildInformationBlockFromParticipantNode(participantNode, module);
		ExtensionsUtil.addExtension(project, informationBlock);

		Persistence persistenceBlock = buildPersistanceBlockFromParticipantNode(participantNode, module);
		ExtensionsUtil.addExtension(project, persistenceBlock);
		return module;
	}

	protected Persistence buildPersistanceBlockFromParticipantNode(ParticipantNode participantNode, Module module) throws Exception {
		List<PersistanceNode> persistanceNodes = participantNode.getPersistanceNodes();
		if (persistanceNodes.size() == 0)
			return null;

		Namespace namespace = context.getNamespaceByUri(module.getNamespace());
		boolean namespaceIsFabricated = false;

		Persistence persistence = null;
		if (persistence == null)
			persistence = context.getPersistencePlaceholderByName(application.getName());
		if (persistence == null)
			persistence = context.getPersistencePlaceholderByName(application.getArtifactId());
		if (persistence == null)
			persistence = context.getPersistencePlaceholderByNamespace(application.getNamespace());
		if (persistence == null) {
			persistence = new Persistence();
			Assert.notNull(namespace, "Namespace not found: "+module.getNamespace());
			PersistenceUtil.addNamespace(persistence, namespace);
			persistence.setNamespace(namespace.getUri());
		}

		module.setPersistence(persistence);
		if (persistence.getNamespace() == null)
			persistence.setNamespace(application.getNamespace());
		if (namespace.getImports().isEmpty()) {
			namespaceIsFabricated = true;
			//this is a namespace that still has yet to be populated (with imports and elements)
			Collection<Namespace> importedNamespaces = NamespaceUtil.getImportedNamespaces(persistence.getImports());
			NamespaceUtil.addImportedNamespaces(namespace, importedNamespaces);
		}
		
		Iterator<PersistanceNode> iterator = persistanceNodes.iterator();
		while (iterator.hasNext()) {
			PersistanceNode persistanceNode = iterator.next();
			Unit unit = buildUnitFromPersistanceNode(persistanceNode, namespace); 
			PersistenceUtil.addUnit(persistence, unit);
			//add elements to this namespace
			if (namespaceIsFabricated) {
				List<Element> elements = UnitUtil.getElements(unit);
				NamespaceUtil.addElements(namespace, elements);
			}
		}
		
		if (persistence.getName() == null) {
			/*
			 * We may come here for any "persist" block specified in ARIEL
			 * that does not have a name yet - just adopt the name from the
			 * first Unit (in ARIEL "persist" blocks will have only one Unit).
			 */
			List<Unit> units = PersistenceUtil.getUnits(persistence);
			Assert.notNull(units, "Persistence units null");
			Assert.notEmpty(units, "At least one Persistence-unit must be specified");
			persistence.setName(application.getArtifactId());
		}
		
		if (persistence.getDomain() == null) {
			persistence.setDomain(module.getInformation().getDomain());
		}
		
		if (persistence.getNamespace() == null) {
			persistence.setNamespace(module.getNamespace());
		}
		
		return persistence;
	}
	
	protected Unit buildUnitFromPersistanceNode(PersistanceNode persistanceNode, Namespace namespace) throws Exception {
		Unit unit = new Unit();
		unit.setElements(new Elements());
		unit.setProperties(new Properties());
		unit.setName(persistanceNode.getName());
		persistanceNode.getDomain();
		persistanceNode.getRoleNodes();
		unit.setNamespace(namespace);
		unit.setSource(buildSourceFromPersistenceNode(persistanceNode));
		unit.setAdapter(buildAdapterFromPersistenceNode(persistanceNode));
		initializeUnitFromPropertyNodes(unit, persistanceNode.getPropertyNodes());
		UnitUtil.addElements(unit, buildElementsFromPersistenceNode(persistanceNode));
		//UnitUtil.addQueries(unit, buildQueriesFromElements(UnitUtil.getElements(unit)));
		return unit;
	}

	protected List<Query> buildQueriesFromElements(List<Element> elements) {
		List<Query> queries = new ArrayList<Query>();
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			queries.add(buildQueryFromElement_GetRecordById(element));
			queries.add(buildQueryFromElement_GetAllRecords(element));
			queries.add(buildQueryFromElement_RemoveRecordById(element));
			queries.add(buildQueryFromElement_RemoveAllRecords(element));
		}
		return queries;
	}

	protected Query buildQueryFromElement_GetRecordById(Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		Query query = new Query();
		query.setDistinct(true);
		query.setName("get"+elementNameCapped+"RecordById");
		query.setFrom(elementNameCapped);
		//Condition condition = new Condition();
		//QueryUtil.addCondition(condition);
		return query;
	}
	
	protected Query buildQueryFromElement_GetAllRecords(Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		Query query = new Query();
		query.setName("getAll"+elementNameCapped+"Records");
		query.setFrom(elementNameCapped);
		return query;
	}
	
	protected Query buildQueryFromElement_RemoveRecordById(Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		Query query = new Query();
		query.setName("remove"+elementNameCapped+"RecordById");
		query.setFrom(elementNameCapped);
		return query;
	}
	
	protected Query buildQueryFromElement_RemoveAllRecords(Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		Query query = new Query();
		query.setName("removeAll"+elementNameCapped+"Records");
		query.setFrom(elementNameCapped);
		return query;
	}
	

	protected void initializeUnitFromPropertyNodes(Unit unit, Set<PropertyNode> propertyNodes) throws Exception {
		Iterator<PropertyNode> iterator = propertyNodes.iterator();
		while (iterator.hasNext()) {
			PropertyNode propertyNode = iterator.next();
			String text = propertyNode.getText();
			String name = propertyNode.getName();
			String value = propertyNode.getValue();
			if (text.equals("add") || text.equals("set")) {
				UnitUtil.setProperty(unit, name, value);
			} else if (text.equals("include")) {
				unit.getIncludesAndImports().add(buildIncludeFromPropertyNode(propertyNode));
			} else if (text.equals("import")) {
				unit.getIncludesAndImports().add(buildImportFromPropertyNode(propertyNode));
			}
		}
	}
	
	protected Include buildIncludeFromPropertyNode(PropertyNode propertyNode) {
		String name = propertyNode.getName();
		String value = propertyNode.getValue();
		Include include = new Include();
		if (name.equals("namespace")) {
			Namespace namespace = context.getNamespaceByPrefix(value);
			Assert.notNull(namespace, "Namespace not found: "+value);
			include.setNamespace(namespace.getUri());
		}
		return include;
	}

	protected Import buildImportFromPropertyNode(PropertyNode propertyNode) {
		String name = propertyNode.getName();
		String value = propertyNode.getValue();
		Import newImport = new Import();
		if (name.equals("namespace")) {
			Namespace namespace = context.getNamespaceByName(value);
			Assert.notNull(namespace, "Namespace not found: "+value);
			newImport.setNamespace(namespace.getUri());
			newImport.setObject(namespace);
		}
		return newImport;
	}

	protected Source buildSourceFromPersistenceNode(PersistanceNode persistanceNode) throws Exception {
		PropertyNode propertyNode = persistanceNode.getPropertyNode("source");
		Assert.notNull(propertyNode, "No Source specified for \"persist\" block: "+persistanceNode.getName());
		String sourceName = propertyNode.getValue();
		Source source = context.getImportedSource(sourceName);
		return source;
	}

	protected Adapter buildAdapterFromPersistenceNode(PersistanceNode persistanceNode) throws Exception {
		PropertyNode propertyNode = persistanceNode.getPropertyNode("adapter");
		Assert.notNull(propertyNode, "No Adapter specified for \"persist\" block: "+persistanceNode.getName());
		String adapterName = propertyNode.getValue();
		Adapter adapter = context.getImportedAdapter(adapterName);
		return adapter;
	}

	protected List<Element> buildElementsFromPersistenceNode(PersistanceNode persistanceNode) throws Exception {
		List<ItemNode> itemNodes = persistanceNode.getItemNodes();
		List<Element> elements = buildElementsFromItemNodes(itemNodes);
		return elements;
	}

	protected List<Element> buildElementsFromItemNodes(List<ItemNode> itemNodes) throws Exception {
		Set<String> elementsAddedSoFar = new HashSet<String>();
		List<Element> elements = new ArrayList<Element>();
		Iterator<ItemNode> iterator2 = itemNodes.iterator();
		while (iterator2.hasNext()) {
			ItemNode itemNode = iterator2.next();
			String key = itemNode.getName().toLowerCase();
			if (!elementsAddedSoFar.contains(key)) {
				elements.add(buildElementFromItemNode(itemNode));
				elementsAddedSoFar.add(key);
			}
		}
		return elements;
	}

	protected Element buildElementFromItemNode(ItemNode itemNode) throws Exception {
		String name = itemNode.getName();
		TypeNode typeNode = itemNode.getTypeNode();
		String type = AriesASTUtil.getFieldTypeFromTypeNode(typeNode);
		String key = AriesASTUtil.getFieldKeyTypeFromTypeNodeType(typeNode);

		String namespace = TypeUtil.getNamespace(type);
		String localName = TypeUtil.getLocalPart(type);
		Namespace elementNamespace = context.getNamespaceByUri(namespace);
		Assert.notNull(elementNamespace, "Namespace not found: "+namespace);

		//String nsPrefix = NameUtil.getPrefixFromXSDType(type);
		//String localName = NameUtil.getLocalNameFromXSDType(type);
		//Namespace elementNamespace = context.getNamespaceByPrefix(nsPrefix);
		//Assert.notNull(elementNamespace, "Namespace not found for prefix: "+nsPrefix);
		
//		if (localName.toLowerCase().equals("book"))
//			System.out.println();

		String elementType = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(elementNamespace.getUri(), localName);
		//String entityType = TypeUtil.getTypeFromNamespaceAndLocalPart(namespace.getUri(), localName);
		//String elementType = packageName + "." + localName;
		Element cachedElement = context.getElementByType(elementType);
		Assert.notNull(cachedElement, "Element not found for type: "+elementType);
		Element element = ElementUtil.cloneElement(cachedElement);
		element.setName(name);
		element.setType(elementType);
		element.setStructure(itemNode.getConstruct());
		
		if (key != null) {
			String keyNamespace = TypeUtil.getNamespace(key);
			String keyLocalName = TypeUtil.getLocalPart(key);
			if (!keyNamespace.equals(TypeUtil.getDefaultNamespace())) {
				Namespace keyElementNamespace = context.getNamespaceByUri(keyNamespace);
				Assert.notNull(keyElementNamespace, "Namespace for key not found: "+keyNamespace);
			}
			String elementKey = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(keyNamespace, keyLocalName);
			element.setKey(elementKey);
		}
		return element;
	}

	protected Module buildClientModuleFromParticipantNode(ParticipantNode participantNode) throws Exception {
		//assuming domain has already been created but not yet populated
		//DomainUtil.addServices(domain, buildServicesFromParticipantNode(participantNode));
		Services services = new Services();
		ServicesUtil.addDomain(services, domain);
		
		module = engine.getModelBuilder().buildClientModule(project, application, services);
		
		ExtensionsUtil.addExtension(project, buildInformationBlockFromParticipantNode(participantNode, module));
		ExtensionsUtil.addExtension(project, buildPersistanceBlockFromParticipantNode(participantNode, module));
		
		Process process = buildProcessFromParticipantNode(participantNode);
		ModuleUtil.addProcess(module, process);

		List<Cache> cacheUnits = buildCacheUnitsFromParticipantNode(participantNode);
		ProcessUtil.addCacheUnits(process, cacheUnits);
		
		List<Unit> units = buildDataUnitsFromProcess(process, module);
		ProcessUtil.addDataUnits(process, units);
		
		//ModuleUtil.addServices(module, buildServicesFromParticipantNode(participantNode));
		ModuleUtil.addInteractors(module, buildInteractorsFromParticipantNode(participantNode));
		module.setVersion("0.0.1-SNAPSHOT");
		return module;
	}

	protected List<Interactor> buildInteractorsFromParticipantNode(ParticipantNode participantNode) throws Exception {
		List<Interactor> interactors = new ArrayList<Interactor>();
		List<CommandNode> commandNodes = participantNode.getCommandNodes();
		Iterator<CommandNode> iterator = commandNodes.iterator();
		while (iterator.hasNext()) {
			CommandNode commandNode = iterator.next();
			if (commandNode instanceof ReceiveNode) {
				ReceiveNode receiveNode = (ReceiveNode) commandNode;
				interactors.add(buildInteractorFromReceiveNode(participantNode, receiveNode));
			}
		}
		return interactors;
	}

	protected Interactor buildInteractorFromReceiveNode(ParticipantNode participantNode, ReceiveNode receiveNode) throws Exception {
		boolean replyFound = false;
		String participantName = participantNode.getName();
		if (!replyFound && checkReplyExistsInCommandNodes(receiveNode.getCommandNodes()))
			replyFound = true;
		if (!replyFound && checkReplyExistsInOptionNodes(receiveNode.getOptionNodes()))
			replyFound = true;
		if (!replyFound && checkReplyExistsInMessageNodes(receiveNode.getMessageNodes()))
			replyFound = true;
		if (!replyFound && checkReplyExistsInExceptionNodes(receiveNode.getExceptionNodes()))
			replyFound = true;
		if (!replyFound && checkReplyExistsInTimeoutNodes(receiveNode.getTimeoutNodes()))
			replyFound = true;
		if (replyFound) {
			String name = NameUtil.uncapName(participantName);
			Interactor invoker = new Interactor();
			invoker.setInteraction(Interaction.INVOKE);
			invoker.setLink(name);
			invoker.setRole(name);
			invoker.setTarget(participantName);
			invoker.setTimeout(60L);
			return invoker;
		} else {
			String name = NameUtil.uncapName(participantName);
			Interactor sender = new Interactor();
			sender.setInteraction(Interaction.SEND);
			sender.setLink(name);
			sender.setRole(name);
			sender.setTarget(participantName);
			
//			invoker.setService(serviceName);
//			invoker.setName(applicationName);
//			invoker.setLink(applicationName);
//			invoker.setRole(participantName);
//			invoker.setChannel(channelId);
			
			return sender;
		}
	}

	protected boolean checkReplyExistsInCommandNodes(List<CommandNode> commandNodes) {
		return false;
	}

	protected boolean checkReplyExistsInOptionNodes(List<OptionNode> optionNodes) {
		return false;
	}

	protected boolean checkReplyExistsInMessageNodes(List<MessageNode> messageNodes) {
		return false;
	}

	protected boolean checkReplyExistsInExceptionNodes(List<ExceptionNode> exceptionNodes) {
		return false;
	}

	protected boolean checkReplyExistsInTimeoutNodes(List<TimeoutNode> timeoutNodes) {
		return false;
	}

	//assuming domain has already been created but not yet populated
	protected Module buildServiceModuleFromParticipantNode(ParticipantNode participantNode) throws Exception {
		module = new Module();
		module.setType(ModuleType.SERVICE);
		module.setLevel(ModuleLevel.APPLICATION_LEVEL);
		module.setGroupId(application.getGroupId());
		//TODO - Externalize these
		module.setArtifactId(application.getArtifactId() + "-service");
		module.setName(application.getArtifactId() + "-service");
		module.setNamespace(application.getNamespace());
		module.setVersion("0.0.1-SNAPSHOT");

		ExtensionsUtil.addExtension(project, buildInformationBlockFromParticipantNode(participantNode, module));
		ExtensionsUtil.addExtension(project, buildPersistanceBlockFromParticipantNode(participantNode, module));
		ModuleUtil.addProcess(module, buildProcessFromParticipantNode(participantNode));
		DomainUtil.addServices(domain, buildServicesFromParticipantNode(participantNode));
		Services services = new Services();
		ServicesUtil.addDomain(services, domain);
		module.setServices(services);

		List<Cache> cacheUnits = buildCacheUnitsFromParticipantNode(participantNode);
		ProcessUtil.addCacheUnits(process, cacheUnits);

		List<Unit> units = buildDataUnitsFromProcess(process, module);
		ProcessUtil.addDataUnits(process, units);
		
		List<Service> servicesList = ServicesUtil.getServices(services);
		Iterator<Service> iterator = servicesList.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			service.setProcess(process);
			List<Callback> callbacks = ServiceUtil.getCallbacks(service);
			Iterator<Callback> iterator2 = callbacks.iterator();
			while (iterator2.hasNext()) {
				Callback callback = iterator2.next();
				callback.setProcess(process);
			}
		}

		//ModuleUtil.addServices(module, buildServicesFromParticipantNode(participantNode));
		return module;
	}
	
	private List<Unit> buildDataUnitsFromProcess(Process process, Module module) {
		Persistence persistence = module.getPersistence();
		List<Unit> list = new ArrayList<Unit>();
		if (persistence != null) {
			List<Unit> units = PersistenceUtil.getUnits(persistence);
			list.addAll(units);
		}

		return list;
	}

	protected List<Cache> buildCacheUnitsFromParticipantNode(ParticipantNode participantNode) throws Exception {
		List<Cache> cacheUnits = new ArrayList<Cache>();
		List<CacheNode> cacheNodes = participantNode.getCacheNodes();
		String namespaceUri = participantNode.getNamespace();
		Iterator<CacheNode> iterator = cacheNodes.iterator();
		while (iterator.hasNext()) {
			CacheNode cacheNode = iterator.next();
			Cache cache = buildCacheUnitFromCacheNode(namespaceUri, cacheNode);
			//namespace.getTypesAndEnumerationsAndElements().add(cache);
			cacheUnits.add(cache);
		}
		return cacheUnits;
	}

	protected List<Service> buildServicesFromParticipantNode(ParticipantNode participantNode) throws Exception {
		List<Service> services = new ArrayList<Service>(); 
		List<CommandNode> commandNodes = participantNode.getCommandNodes();
		Iterator<CommandNode> iterator = commandNodes.iterator();
		while (iterator.hasNext()) {
			CommandNode commandNode = iterator.next();
			if (commandNode instanceof ReceiveNode) {
				ReceiveNode receiveNode = (ReceiveNode) commandNode;
				Service service = buildServiceFromReceiveNode(participantNode, receiveNode);
				services.add(service);
			}
		}
		return services;
	}

	protected Service buildServiceFromReceiveNode(ParticipantNode participantNode, ReceiveNode receiveNode) throws Exception {
		String serviceName = NameUtil.uncapName(receiveNode.getName());
		String namespaceUri = module.getNamespace();
		
//		if (serviceName.equalsIgnoreCase("purchaseBooks"))
//			System.out.println();
		
		service = new Service();
		service.setName(serviceName);
		service.setNamespace(namespaceUri);
		service.setDomain(domain.getName());
		service.setPackageName(ServiceLayerHelper.getServicePackageName(service));
		service.setInterfaceName(ServiceLayerHelper.getServiceInterfaceName(service));
		service.setClassName(ServiceLayerHelper.getServiceClassName(service));
		service.setElement(org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespaceUri , serviceName));
		service.getChannelsAndListenersAndOperations().add(buildServiceOperationFromReceiveNode(receiveNode));
		service.getChannelsAndListenersAndOperations().addAll(buildChannelsFromParticipantNode(participantNode));
		service.getChannelsAndListenersAndOperations().addAll(buildIncomingCallbacksFromMessageNodes(service, receiveNode.getMessageNodes()));
		service.getChannelsAndListenersAndOperations().addAll(buildIncomingCallbacksFromCommandNodes(service, receiveNode.getCommandNodes()));
		service.getChannelsAndListenersAndOperations().addAll(buildIncomingCallbacksFromCommandNodes(service, participantNode.getCommandNodes()));
		service.getChannelsAndListenersAndOperations().addAll(buildOutgoingCallbacksFromInvokeNodes(service, receiveNode.getCommandNodes()));
		service.getChannelsAndListenersAndOperations().addAll(buildOutgoingCallbacksFromListenNodes(service, receiveNode.getCommandNodes()));
		service.getChannelsAndListenersAndOperations().addAll(buildOutgoingCallbacksFromCommandNodes(receiveNode.getCommandNodes()));
		service.getChannelsAndListenersAndOperations().addAll(buildOutgoingCallbacksFromOptionNodes(receiveNode.getOptionNodes()));
		service.getChannelsAndListenersAndOperations().addAll(buildListenersFromReceiveNode(participantNode, receiveNode));
		service.getChannelsAndListenersAndOperations().addAll(buildInvokersFromReceiveNode(participantNode, receiveNode));
		service.getChannelsAndListenersAndOperations().addAll(buildRepliersFromReceiveNode(participantNode, receiveNode));
		service.getChannelsAndListenersAndOperations().addAll(buildFaultsFromThrowsNode(service, receiveNode));
		service.setTransacted(buildTransactedFromReceiveNode(receiveNode));
		initializeServiceFromPropertyNodes(service, receiveNode.getPropertyNodes());
		applicationsByService.put(serviceName, application);
		return service;
	}

	protected List<Fault> buildFaultsFromThrowsNode(Service service, ReceiveNode receiveNode) {
		List<Fault> list = new ArrayList<Fault>();
		ThrowsNode throwsNode = receiveNode.getThrowsNode();
		if (throwsNode == null)
			return list;
		//String exceptionPackageName = service.getPackageName();
		List<String> exceptionTypes = throwsNode.getExceptionTypes();
		Iterator<String> iterator = exceptionTypes.iterator();
		while (iterator.hasNext()) {
			String exceptionType = iterator.next();
			Fault fault = new Fault();
			fault.getAttributes().add(util.createAttribute_Id());
			fault.getAttributes().add(util.createAttribute_Reason());
			Type faultType = context.findTypeByName(exceptionType);
			//String faultType = TypeUtil.getTypeFromPackageAndClass(exceptionPackageName, typeName);
			if (faultType != null)
				fault.setType(faultType.getType());
			list.add(fault);
		}
		
		//add all faults to each operation - which is expected
		List<Operation> operations = ServiceUtil.getOperations(service);
		Iterator<Operation> iterator2 = operations.iterator();
		while (iterator2.hasNext()) {
			Operation operation = iterator2.next();
			operation.getFaults().addAll(list);
		}
		return list;
	}

	protected Transacted buildTransactedFromReceiveNode(ReceiveNode receiveNode) {
		Transacted transacted = buildTransacted();
		return transacted;
	}

	protected Transacted buildTransacted() {
		Transacted transacted = new Transacted();
		transacted.setLocal(false);
		transacted.setScope(TransactionScope.CONVERSATION);
		transacted.setUse(TransactionUsage.REQUIRED);
		return transacted;
	}
	
//	protected List<MessageNode> getMessageNodes(EndpointNode endpointNode) {
//		List<MessageNode> messageNodes = new ArrayList<MessageNode>();
//		messageNodes.addAll(endpointNode.getMessageNodes());
//		List<CommandNode> commandNodes = endpointNode.getCommandNodes();
//		Iterator<CommandNode> iterator = commandNodes.iterator();
//		while (iterator.hasNext()) {
//			CommandNode commandNode = iterator.next();
//			if (commandNode instanceof EndpointNode)
//				messageNodes.addAll(getMessageNodes((EndpointNode) commandNode));
//		}
//		return messageNodes;
//	}
	
//	protected List<MessageNode> getMessageNodes(ReceiveNode receiveNode) {
//		List<MessageNode> messageNodes = new ArrayList<MessageNode>();
//		List<CommandNode> commandNodes = new ArrayList<CommandNode>();
//		messageNodes.addAll(receiveNode.getMessageNodes());
//		commandNodes.addAll(receiveNode.getCommandNodes());
//		
//		Iterator<MessageNode> messageNodeIterator = messageNodes.iterator();
//		while (messageNodeIterator.hasNext()) {
//			MessageNode messageNode = messageNodeIterator.next();
//			commandNodes.addAll(messageNode.getCommandNodes());
//		}
//		
//		Iterator<CommandNode> iterator = commandNodes.iterator();
//		while (iterator.hasNext()) {
//			CommandNode commandNode = iterator.next();
//			if (commandNode instanceof EndpointNode) {
//				EndpointNode endpointNode = (EndpointNode) commandNode;
//				messageNodes.addAll(endpointNode.getMessageNodes());
//			}
//			System.out.println();
//		}
//		return messageNodes;
//	}

	//TODO take first from receiveNode, then from participantNode, otherwise default to getting from networkNode
	protected Collection<Channel> buildChannelsFromParticipantNode(ParticipantNode participantNode) throws Exception {
//		if (participantNode.getName().equalsIgnoreCase("shipper"))
//			System.out.println();
		List<Channel> channels = new ArrayList<Channel>();
		List<String> channelNames = getChannelNamesFromParticipantNode(participantNode);
		List<ChannelNode> channelNodes = AriesASTContext.networkNode.getChannelNodes();
		channels.addAll(buildChannelsFromChannelNames(channelNames));
		channels.addAll(buildChannelsFromChannelNodes(channelNames, channelNodes));
//		if (channels.size() == 0)
//			System.out.println();
		Assert.isTrue(channels.size() > 0, "No channels found for: "+participantNode.getName());
		return channels;
	}

	protected Collection<Channel> buildChannelsFromChannelNames(List<String> channelNames) throws Exception {
		List<Channel> channels = new ArrayList<Channel>();
		Iterator<String> iterator = channelNames.iterator();
		while (iterator.hasNext()) {
			String channelName = iterator.next();
			Channel channel = new Channel();
			channel.setName(channelName);
			channels.add(channel);
		}
		return channels;
	}
	
	protected Collection<Channel> buildChannelsFromChannelNodes(List<String> channelNames, List<ChannelNode> channelNodes) throws Exception {
		List<Channel> channels = new ArrayList<Channel>();
		Iterator<ChannelNode> iterator2 = channelNodes.iterator();
		while (iterator2.hasNext()) {
			ChannelNode channelNode = iterator2.next();
			if (channelNames.contains(channelNode.getName())) {
				Channel channel = buildChannelFromChannelNode(channelNode);
				if (channel != null)
					channels.add(channel);
			}
		}
		return channels;
	}

	//TODO take first from receiveNode, then from participantNode, otherwise default to getting from networkNode
	protected List<String> getChannelNamesFromParticipantNode(ParticipantNode participantNode) throws Exception {
		List<String> channelNames = new ArrayList<String>();
		Set<PropertyNode> propertyNodes = participantNode.getPropertyNodes();
		Iterator<PropertyNode> iterator = propertyNodes.iterator();
		while (iterator.hasNext()) {
			PropertyNode propertyNode = iterator.next();
			String name = propertyNode.getName();
			String value = propertyNode.getValue();
			if (name.equals("channel")) {
				channelNames.add(value);
			}
		}
		return channelNames;
	}

	
	protected boolean isChannelIncluded(List<String> channelRefs, Channel channel) {
		Iterator<String> iterator = channel.getClients().iterator();
		while (iterator.hasNext()) {
			String clientRole = iterator.next();
			if (channelRefs.contains(clientRole))
				return true;
		}
		Iterator<String> iterator2 = channel.getClients().iterator();
		while (iterator2.hasNext()) {
			String serviceRole = iterator2.next();
			if (channelRefs.contains(serviceRole))
				return true;
		}
		return false;
	}

	protected Channel buildChannelFromChannelNode(ChannelNode channelNode) throws Exception {
		Channel channel = new Channel();
		channel.getClients().addAll(buildRolesFromRoleNodes(channelNode.getSenderRoleNodes()));
		channel.getServices().addAll(buildRolesFromRoleNodes(channelNode.getReceiverRoleNodes()));
		channel.getManagers().addAll(buildRolesFromRoleNodes(channelNode.getManagerRoleNodes()));
		return channel;
	}

	protected List<String> buildRolesFromRoleNodes(List<RoleNode> roleNodes) throws Exception {
		List<String> roles = new ArrayList<String>();
		Iterator<RoleNode> iterator = roleNodes.iterator();
		while (iterator.hasNext()) {
			RoleNode roleNode = iterator.next();
			roles.add(roleNode.getName().toLowerCase());
		}
		return roles;
	}

	protected Operation buildServiceOperationFromReceiveNode(ReceiveNode receiveNode) throws Exception {
		Operation operation = new Operation();
		operation.setRole("handle");
		operation.setName(NameUtil.uncapName(receiveNode.getName()));
		//operation.setName("request" + NameUtil.capName(receiveNode.getName()));
		
//		if (receiveNode.getName().equals("reserveBooks"))
//			System.out.println();
			
		List<ParameterNode> parameterNodes = receiveNode.getParameterNodes();
		Iterator<ParameterNode> iterator = parameterNodes.iterator();
		while (iterator.hasNext()) {
			ParameterNode parameterNode = iterator.next();
//			if (parameterNode.getType().endsWith("int"))
//				System.out.println();
			Parameter parameter = new Parameter();
			String parameterName = parameterNode.getName();
			String parameterType = parameterNode.getType();
			parameter.setConstruct(parameterNode.getConstruct());
			
			Type parameterElement = context.findTypeByName(parameterType);
			if (parameterElement != null) {
				parameterType = parameterElement.getType();
				
			} else {
				if (!parameterName.endsWith("Message")) {
					String messageParameterName = parameterName + "Message";
					parameterElement = context.findTypeByName(messageParameterName);
					if (parameterElement != null) {
						parameterName += "Message";
						parameterType = parameterElement.getType();
						
					} else {
						String type = parameterNode.getType();
						if (CodeGenUtil.isJavaDefaultType(type)) {
							parameterType = TypeUtil.getDefaultType(type);
						} else {
							//System.out.println();
						}
					}
				}
			}
//			if (parameterType == null)
//				System.out.println();
			Assert.notNull(parameterType, "Parameter type not found: "+parameterName);
			//parameter.setName("message");
			parameter.setName(NameUtil.uncapName(parameterName));
			parameter.setType(parameterType);
			operation.getParameters().add(parameter);
		}
		return operation;
	}

	protected Process buildProcessFromParticipantNode(ParticipantNode participantNode) throws Exception {
		String name = participantNode.getName();
		Transacted transacted = new Transacted();
		
//		if (name.equalsIgnoreCase("supplier"))
//			System.out.println();
		
		process = engine.getModelBuilder().buildProcess(name, module.getNamespace(), transacted);
		ProcessUtil.addCacheUnits(process, buildCacheUnitsFromParticipantNode(participantNode));
		process.getOperations().addAll(buildProcessOperationsFromParticipantNode(participantNode));
		process.setStateful(true);
		return process;
	}

//	protected List<Cache> buildProcessCacheUnitsFromParticipantNode(ParticipantNode participantNode) throws Exception {
//		List<Cache> cacheUnits = new ArrayList<Cache>();
//		List<CacheNode> cacheNodes = participantNode.getCacheNodes();
//		Iterator<CacheNode> iterator = cacheNodes.iterator();
//		while (iterator.hasNext()) {
//			CacheNode cacheNode = iterator.next();
//			cacheUnits.add(buildCacheUnitFromCacheNode(cacheNode));
//		}
//		return cacheUnits;
//	}
//
//	protected Cache buildCacheUnitFromCacheNode(CacheNode cacheNode) throws Exception {
//		Cache cache = new Cache();
//		Items elements = new Items();
//		cache.setName(cacheNode.getName());
//		List<ItemNode> itemNodes = cacheNode.getItemNodes();
//		Iterator<ItemNode> iterator = itemNodes.iterator();
//		while (iterator.hasNext()) {
//			ItemNode itemNode = iterator.next();
//			Field field = buildItemFromItemNode(itemNode);
//			elements.getIdsAndItemsAndSecrets().add(field);
//		}
//		//cache.getOperations().addAll(buildProcessOperationsFromParticipantNode(participantNode));
//		return cache;
//	}
	
	protected List<Operation> buildProcessOperationsFromParticipantNode(ParticipantNode participantNode) throws Exception {
		//System.out.println(">>>"+participantNode.getName());
		AriesASTContext.clearActiveVariablesInScope();
		AriesASTContext.participantNode = participantNode;
		List<Operation> operations = new ArrayList<Operation>();
		List<CommandNode> commandNodes = participantNode.getCommandNodes();
		Iterator<CommandNode> iterator = commandNodes.iterator();
		while (iterator.hasNext()) {
			CommandNode commandNode = iterator.next();
			if (commandNode instanceof ReceiveNode) {
				AriesASTContext.receiveNode = (ReceiveNode) commandNode;
				operations.addAll(buildProcessOperationsFromReceiveNode(AriesASTContext.receiveNode));
				operations.addAll(buildCallbackOperationsFromOptionNodes(AriesASTContext.receiveNode));
				operations.addAll(buildCallbackOperationsFromMessageNodes(AriesASTContext.receiveNode));
				//operations.addAll(buildCallbackOperationsFromReplyNodes(AriesASTContext.receiveNode));
			}
			if (commandNode instanceof MethodNode) {
				AriesASTContext.methodNode = (MethodNode) commandNode;
				operations.add(buildProcessOperationFromMethodNode(AriesASTContext.methodNode));
				operations.addAll(buildCallbackOperationsFromMessageNodes(AriesASTContext.methodNode));
				AriesASTContext.methodNode = null;
			}
		}
		return operations;
	}

	protected List<Parameter> buildParametersFromParameterNodes(List<ParameterNode> parameterNodes) throws Exception {
		return buildParametersFromParameterNodes(parameterNodes, null);
	}
	
	protected List<Parameter> buildParametersFromParameterNodes(List<ParameterNode> parameterNodes, String suffix) throws Exception {
		List<Parameter> parameters = new ArrayList<Parameter>();
		Iterator<ParameterNode> iterator = parameterNodes.iterator();
		while (iterator.hasNext()) {
			ParameterNode parameterNode = iterator.next();
			parameters.add(buildParameterFromParameterNode(parameterNode, suffix));
		}
		return parameters;
	}

	protected Parameter buildParameterFromParameterNode(ParameterNode parameterNode) throws Exception {
		return buildParameterFromParameterNode(parameterNode, null);
	}
	
	protected Parameter buildParameterFromParameterNode(ParameterNode parameterNode, String suffix) throws Exception {
		String parameterName = NameUtil.uncapName(parameterNode.getName());
		String parameterType = parameterNode.getType();

//		if (parameterType.endsWith("int"))
//			System.out.println();
		
		Parameter parameter = new Parameter();
		parameter.setName(parameterName);
		Type parameterElement = context.findTypeByName(parameterName+suffix);
		if (suffix != null && !parameterName.endsWith(suffix) && parameterElement != null) {
			parameterName += suffix;
			parameter.setName(parameterName);
			parameter.setType(parameterElement.getType());
			parameter.setConstruct(parameterNode.getConstruct());
			return parameter;
		}
		
		//System.out.println(context.findParameterType(parameterName));
		parameterElement = context.findTypeByName(parameterType);
		Type parameterKey = context.findTypeByName(parameterNode.getKey());
		//Assert.notNull(parameterElement, "Parameter type not found: "+parameterName);
		if (parameterElement != null) {
			parameter.setType(parameterElement.getType());
			if (parameterKey != null)
				parameter.setKey(parameterKey.getType());
			parameter.setConstruct(parameterNode.getConstruct());
			return parameter;
		}
		
		Result activeVariable = AriesASTContext.getActiveVariableInScope(parameterName);
		if (activeVariable != null) {
			Assert.notNull(activeVariable, "Active variable not found in scope: "+parameterName);
			parameter.setType(AriesASTUtil.getFieldType(activeVariable.getType()));
			parameter.setKey(AriesASTUtil.getFieldType(activeVariable.getKey()));
			parameter.setConstruct(activeVariable.getConstruct());
			return parameter;
		}
		
		String parameterClass = TypeUtil.getDefaultType(parameterType);
		parameter.setType(parameterClass);
		if (parameterKey != null)
			parameter.setKey(parameterNode.getKey());
		parameter.setConstruct(parameterNode.getConstruct());
		return parameter;
	}
	
	protected List<Command> buildCommandsFromCommandNodes(List<CommandNode> commandNodes) throws Exception {
		List<Command> commands = new ArrayList<Command>();
		Iterator<CommandNode> iterator = commandNodes.iterator();
		while (iterator.hasNext()) {
			CommandNode commandNode = iterator.next();
			
//			if (commandNode.getName().equals("DONE"))
//				System.out.println();

			if (commandNode.getName().equals("EXPR") && commandNode instanceof ExpressionNode == false) {
				//Command command = AriesCommandFactory.buildCommandFromCommandNode(commandNode);
				//commands.add(command);
				//System.out.println();
				
			} else if (commandNode instanceof DefinitionNode) {
				DefinitionCommand command = ariesCommandFactory.buildDefinitionCommandFromCommandNode(commandNode);
				Definition definition = command.getDefinition();
				ariesCommandFactory.addLocalDefinition(definition);
				commands.add(command);

			} else if (commandNode instanceof ExpressionNode) {
				ExpressionStatement command = ariesCommandFactory.buildExpressionStatementFromExpressionNode((ExpressionNode) commandNode);
				commands.add(command);

			} else {
				Command command = ariesCommandFactory.buildCommandFromCommandNode(commandNode);
				if (command != null)
					commands.add(command);
			}
		}
		return commands;
	}
	
	protected List<Operation> buildProcessOperationsFromReceiveNode(ReceiveNode receiveNode) throws Exception {
		List<Operation> operations = new ArrayList<Operation>();
		AriesASTContext.receiveNode = receiveNode;
		operations.add(buildProcessOperationFromReceiveNode(receiveNode));
		//TODO we not creating any operations here now - they are created implicitly further downstream
		//TODO operations.addAll(buildOperationsFromCommandNodes(receiveNode.getCommandNodes()));
		return operations;
	}
	
	protected Operation buildProcessOperationFromReceiveNode(ReceiveNode receiveNode) throws Exception {
		ariesCommandFactory.setProcessScope("receive");
		ariesCommandFactory.resetServiceScope();
		Operation operation = new Operation();
		operation.setRole("handle");
		operation.setName(NameUtil.uncapName(receiveNode.getName()));
		//operation.setName("request" + NameUtil.capName(receiveNode.getName()));
//		if (receiveNode.getName().equals("reserveBooks"))
//			System.out.println();
		operation.getParameters().addAll(buildParametersFromParameterNodes(receiveNode.getParameterNodes(), "Message"));
		operation.getCommands().addAll(buildCommandsFromCommandNodes(receiveNode.getCommandNodes()));
		ariesCommandFactory.setProcessScope(null);
		return operation;
	}
	
	protected List<Operation> buildOperationsFromCommandNodes(List<CommandNode> commandNodes) throws Exception {
		List<Operation> operations = new ArrayList<Operation>();
		Iterator<CommandNode> iterator = commandNodes.iterator();
		while (iterator.hasNext()) {
			CommandNode commandNode = iterator.next();
			if (commandNode instanceof ReplyNode)
				operations.add(buildOperationFromReplyNode((ReplyNode) commandNode));
			if (commandNode instanceof SendNode)
				operations.add(buildOperationFromSendNode((SendNode) commandNode));
		}
		return operations;
	}

	protected Operation buildOperationFromReplyNode(ReplyNode replyNode) throws Exception {
		Operation operation = new Operation();
		operation.setRole("process");
		operation.setName("reply"+NameUtil.capName(replyNode.getName()));
		//operation.setName("process" + NameUtil.capName(methodNode.getName()));
		operation.getParameters().addAll(buildParametersFromParameterNodes(replyNode.getParameterNodes()));
		//operation.getCommands().addAll(buildCommandsFromCommandNodes(methodNode.getCommandNodes()));
		return operation;
	}

	protected Operation buildOperationFromSendNode(SendNode sendNode) throws Exception {
		Operation operation = new Operation();
		operation.setRole("process");
		operation.setName("send"+NameUtil.capName(sendNode.getName()));
		operation.getParameters().addAll(buildParametersFromParameterNodes(sendNode.getParameterNodes()));
		//operation.getCommands().addAll(buildCommandsFromCommandNodes(methodNode.getCommandNodes()));
		return operation;
	}

	protected Operation buildProcessOperationFromMethodNode(MethodNode methodNode) throws Exception {
		Operation operation = new Operation();
		operation.setRole("process");
		operation.setName(NameUtil.uncapName(methodNode.getName()));
		//operation.setName("process" + NameUtil.capName(methodNode.getName()));
		operation.getParameters().addAll(buildParametersFromParameterNodes(methodNode.getParameterNodes(), "Message"));
		operation.getCommands().addAll(buildCommandsFromCommandNodes(methodNode.getCommandNodes()));
		return operation;
	}

	protected List<Operation> buildCallbackOperationsFromOptionNodes(ReceiveNode receiveNode) throws Exception {
		List<Operation> operations = new ArrayList<Operation>();
		List<OptionNode> optionNodes = receiveNode.getOptionNodes();
		operations.addAll(buildCallbackOperationsFromOptionNodes(optionNodes));
		return operations;
	}

	protected List<Operation> buildCallbackOperationsFromMessageNodes(ReceiveNode receiveNode) throws Exception {
		List<Operation> operations = new ArrayList<Operation>();
		List<CommandNode> commandNodes = receiveNode.getCommandNodes();
		List<MessageNode> messageNodes = getMessageNodesFromCommandNodes(commandNodes);
		operations.addAll(buildCallbackOperationsFromMessageNodes(messageNodes));
		return operations;
	}

	protected List<Operation> buildCallbackOperationsFromMessageNodes(MethodNode methodNode) throws Exception {
		List<Operation> operations = new ArrayList<Operation>();
		List<CommandNode> commandNodes = methodNode.getCommandNodes();
		List<MessageNode> messageNodes = getMessageNodesFromCommandNodes(commandNodes);
		operations.addAll(buildCallbackOperationsFromMessageNodes(messageNodes));
		return operations;
	}

	protected <T extends CommandNode> List<MessageNode> getMessageNodesFromCommandNodes(List<T> commandNodes) throws Exception {
		List<MessageNode> messageNodes = new ArrayList<MessageNode>();
		Iterator<T> iterator = commandNodes.iterator();
		while (iterator.hasNext()) {
			T commandNode = iterator.next();
			if (commandNode instanceof InvokeNode) {
				InvokeNode invokeNode = (InvokeNode) commandNode;
				messageNodes.addAll(invokeNode.getMessageNodes());
			}
			if (commandNode instanceof UnnamedNode) {
				UnnamedNode unnamedNode = (UnnamedNode) commandNode;
				messageNodes.addAll(getMessageNodesFromCommandNodes(unnamedNode.getStatementNodes()));
			}
			if (commandNode instanceof IfNode) {
				IfNode ifNode = (IfNode) commandNode;
				messageNodes.addAll(getMessageNodesFromCommandNodes(ifNode.getStatementNodes()));
				messageNodes.addAll(getMessageNodesFromCommandNodes(ifNode.getElseIfNodes()));
				if (ifNode.getElseNode() != null)
					messageNodes.addAll(getMessageNodesFromCommandNodes(ifNode.getElseNode().getStatementNodes()));
			}
		}
		return messageNodes;
	}

	protected List<Operation> buildCallbackOperationsFromOptionNodes(List<OptionNode> optionNodes) throws Exception {
		List<Operation> operations = new ArrayList<Operation>();
		Iterator<OptionNode> iterator = optionNodes.iterator();
		while (iterator.hasNext()) {
			OptionNode optionNode = iterator.next();
			operations.addAll(buildCallbackOperationsFromOptionNode(optionNode));
		}
		return operations;
	}

	protected List<Operation> buildCallbackOperationsFromOptionNode(OptionNode optionNode) throws Exception {
		List<Operation> operations = new ArrayList<Operation>();
		List<CommandNode> commandNodes = optionNode.getCommandNodes();
		Iterator<CommandNode> iterator = commandNodes.iterator();
		while (iterator.hasNext()) {
			CommandNode commandNode = iterator.next();
			if (commandNode instanceof ReplyNode)
				operations.add(buildCallbackOperationFromCommandNode(commandNode));
		}
		return operations;
	}
	
//	protected List<Operation> buildCallbackOperationsFromReplyNodes(ReceiveNode receiveNode) throws Exception {
//		List<Operation> operations = new ArrayList<Operation>();
//		List<ReplyNode> replyNodes = getReplyNodes(receiveNode);
//		operations.addAll(buildCallbackOperationsFromReplyNodes(replyNodes));
//		return operations;
//	}
	
//	protected List<Operation> buildCallbackOperationsFromReplyNodes(List<ReplyNode> replyNodes) throws Exception {
//		List<Operation> operations = new ArrayList<Operation>();
//		Iterator<ReplyNode> iterator = replyNodes.iterator();
//		while (iterator.hasNext()) {
//			ReplyNode replyNode = iterator.next();
//			operations.add(buildCallbackOperationFromReplyNode(replyNode));
//		}
//		return operations;
//	}

//	protected Operation buildCallbackOperationFromReplyNode(ReplyNode replyNode) throws Exception {
//		operation = new Operation();
//		operation.setRole("response");
//		Parameter parameter = new Parameter();
//		String messageName = replyNode.getMessageName();
//		operation.setName(NameUtil.uncapName(messageName));
//		//operation.setName("response" + NameUtil.capName(messageName));
//		parameter.setName("message");
//		if (!messageName.endsWith("Message"))
//			messageName += "Message";
//		parameter.setName(NameUtil.uncapName(messageName));
//		//System.out.println(context.findParameterType(messageName));
//		Type parameterElement = context.findTypeByName(messageName);
//		parameter.setType(parameterElement.getType());
//		operation.getParameters().add(parameter);
//		operation.getCommands().addAll(buildCommandsFromCommandNodes(replyNode.getCommandNodes()));
//		return operation;
//	}

	protected List<ReplyNode> getReplyNodes(ReceiveNode receiveNode) throws Exception {
		List<ReplyNode> replyNodes = new ArrayList<ReplyNode>();
		List<CommandNode> commandNodes = receiveNode.getCommandNodes();
		Iterator<CommandNode> iterator = commandNodes.iterator();
		while (iterator.hasNext()) {
			CommandNode commandNode = iterator.next();
			if (commandNode instanceof ReplyNode) {
				replyNodes.add((ReplyNode) commandNode);
			} else if (commandNode instanceof IfNode) {
				replyNodes.addAll(getReplyNodes((IfNode) commandNode));
			}
		}
		return replyNodes;
	}
	
	protected List<ReplyNode> getReplyNodes(IfNode ifNode) throws Exception {
		List<ReplyNode> replyNodes = new ArrayList<ReplyNode>();
		List<StatementNode> statementNodes = ifNode.getStatementNodes();
		Iterator<StatementNode> iterator = statementNodes.iterator();
		while (iterator.hasNext()) {
			StatementNode statementNode = iterator.next();
			if (statementNode instanceof UnnamedNode) {
				replyNodes.addAll(getReplyNodes((UnnamedNode) statementNode));
			}
		}
		UnnamedNode elseNode = ifNode.getElseNode();
		if (elseNode != null)
			replyNodes.addAll(getReplyNodes((UnnamedNode) elseNode));
		return replyNodes;
	}
	
	protected List<ReplyNode> getReplyNodes(UnnamedNode unnamedNode) throws Exception {
		List<ReplyNode> replyNodes = new ArrayList<ReplyNode>();
		List<CommandNode> statementNodes = unnamedNode.getStatementNodes();
		Iterator<CommandNode> iterator = statementNodes.iterator();
		while (iterator.hasNext()) {
			CommandNode statementNode = iterator.next();
			if (statementNode instanceof ReplyNode) {
				replyNodes.add((ReplyNode) statementNode);
			} else if (statementNode instanceof UnnamedNode) {
				replyNodes.addAll(getReplyNodes((UnnamedNode) statementNode));
			}
		}
		return replyNodes;
	}
	
	protected Operation buildCallbackOperationFromCommandNode(CommandNode commandNode) throws Exception {
		operation = new Operation();
		operation.setRole("handle");
		Parameter parameter = new Parameter();
		
		String messageName = commandNode.getName();
		operation.setName(NameUtil.uncapName(messageName));
		//operation.setName("response" + NameUtil.capName(messageName));
		parameter.setName("message");
		if (!messageName.endsWith("Message"))
			messageName += "Message";
		parameter.setName(NameUtil.uncapName(messageName));
		//System.out.println(context.findParameterType(messageName));
		Type parameterElement = context.findTypeByName(messageName);
		parameter.setType(parameterElement.getType());
		operation.getParameters().add(parameter);
		operation.getCommands().add(ariesCommandFactory.buildCommandFromCommandNode(commandNode));
		return operation;
	}

	protected List<Operation> buildCallbackOperationsFromMessageNodes(List<MessageNode> messageNodes) throws Exception {
		List<Operation> operations = new ArrayList<Operation>();
		Iterator<MessageNode> iterator = messageNodes.iterator();
		while (iterator.hasNext()) {
			AriesASTContext.messageNode = iterator.next();
			operations.add(buildCallbackOperationFromMessageNode(AriesASTContext.messageNode));
		}
		return operations;
	}

	protected Operation buildCallbackOperationFromMessageNode(MessageNode messageNode) throws Exception {
		ariesCommandFactory.setProcessScope("message");
		operation = new Operation();
		operation.setRole("handle");
		Parameter parameter = new Parameter();
		String messageName = messageNode.getName();
		operation.setName(NameUtil.uncapName(messageName));
		//operation.setName("response" + NameUtil.capName(messageName));
		parameter.setName("message");
		if (!messageName.endsWith("Message"))
			messageName += "Message";
		parameter.setName(NameUtil.uncapName(messageName));
		//System.out.println(context.findParameterType(messageName));
		Type parameterElement = context.findTypeByName(messageName);
		parameter.setType(parameterElement.getType());
		operation.getParameters().add(parameter);
//		if (operation.getName().equals("shipmentComplete"))
//			System.out.println(); 
		operation.getCommands().addAll(buildCommandsFromCommandNodes(messageNode.getCommandNodes()));
		ariesCommandFactory.setProcessScope(null);
		return operation;
	}



	protected Callback buildIncomingCallbackFromMessageNode(Service service, MessageNode messageNode) throws Exception {
		Callback callback = buildIncomingCallback(service, messageNode.getName());
		callback.setDirection(Direction.IN);
		return callback;
	}
	
	protected Callback buildIncomingCallback(Service service, String callbackName) throws Exception {
		int indexOf = callbackName.indexOf("(");
		if (indexOf != -1)
			callbackName = callbackName.substring(0, indexOf);
//		if (callbackName.startsWith("$"))
//			System.out.println();
		//String cappedName = NameUtil.capName(callbackName);
		String uncappedName = NameUtil.uncapName(callbackName);
		String namespaceUri = service.getNamespace();
		
//		if (uncappedName.equalsIgnoreCase("BooksAvailable"))
//			System.out.println();
		
		Callback callback = new Callback();
		callback.setName(uncappedName);
		callback.setNamespace(service.getNamespace());
		callback.setDomain(service.getDomain());
		callback.setReceivingService(service);
		callback.setPackageName(ServiceLayerHelper.getServicePackageName(service));
		callback.setInterfaceName(ServiceLayerHelper.getServiceInterfaceName(callback));
		callback.setClassName(ServiceLayerHelper.getServiceClassName(callback));
		//callback.setName(uncappedName+"Callback");
		//callback.setClassName(cappedName+"CallbackImpl");
		//callback.setInterfaceName(cappedName+"Callback");
		callback.setElement(org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespaceUri , uncappedName));
		List<Channel> channels = ServiceUtil.getChannels(service);
		ServiceUtil.addChannels(callback, channels);
		callback.setTransacted(buildTransacted());
		callback.setProcess(process);
		
//		Iterator<Channel> iterator = channels.iterator();
//		while (iterator.hasNext()) {
//			Channel channel = iterator.next();
//			String roleName = null;
//			String linkName = null;
//			Listener listener = buildListener(channel.getName(), linkName, roleName);
//			ServiceUtil.addListener(callback, listener);
//		}
		
		Operation operation = new Operation();
		operation.setRole("handle");
		operation.setName(uncappedName);
		//operation.setName("response" + cappedName);
		//operation.setName("callback");
		Parameter parameter = new Parameter();
		parameter.setConstruct("item");
		//String parameterName = messageName; 
		String parameterName = callbackName; 
		if (!parameterName.endsWith("Message"))
			parameterName += "Message";
		Type parameterElement = context.findTypeByName(parameterName);
		if (parameterElement != null) {
			String parameterType = parameterElement.getType();
			Assert.notNull(parameterType, "Parameter type not found: "+parameterName);
			parameter.setName(NameUtil.uncapName(parameterName));
			parameter.setType(NameUtil.capName(parameterType));
			operation.getParameters().add(parameter);
		}
		ServiceUtil.addOperation(callback, operation);
		context.addImportedService(callback);
		return callback;
	}

	protected List<Callback> buildIncomingCallbacksFromMessageNodes(Service service, List<MessageNode> messageNodes) throws Exception {
		List<Callback> callbacks = new ArrayList<Callback>();
		Iterator<MessageNode> iterator = messageNodes.iterator();
		while (iterator.hasNext()) {
			MessageNode messageNode = iterator.next();
			callbacks.add(buildIncomingCallbackFromMessageNode(service, messageNode));
			callbacks.addAll(buildIncomingCallbacksFromCommandNodes(service, messageNode.getCommandNodes()));
		}
		return callbacks;
	}

	protected <T extends CommandNode> List<Callback> buildIncomingCallbacksFromCommandNodes(Service service, List<T> commandNodes) throws Exception {
		List<Callback> callbacks = new ArrayList<Callback>();
		Iterator<T> iterator = commandNodes.iterator();
		while (iterator.hasNext()) {
			T commandNode = iterator.next();
			if (commandNode instanceof EndpointNode) {
				EndpointNode endpointNode = (EndpointNode) commandNode;
				callbacks.addAll(buildIncomingCallbacksFromMessageNodes(service, endpointNode.getMessageNodes()));
			}
			if (commandNode instanceof MethodNode) {
				MethodNode methodNode = (MethodNode) commandNode;
				callbacks.addAll(buildIncomingCallbacksFromCommandNodes(service, methodNode.getCommandNodes()));
				//TODO addAll for OptionNodes as well, right?
			}
			if (commandNode instanceof UnnamedNode) {
				UnnamedNode unnamedNode = (UnnamedNode) commandNode;
				callbacks.addAll(buildIncomingCallbacksFromCommandNodes(service, unnamedNode.getStatementNodes()));
			}
			if (commandNode instanceof IfNode) {
				IfNode ifNode = (IfNode) commandNode;
				callbacks.addAll(buildIncomingCallbacksFromCommandNodes(service, ifNode.getStatementNodes()));
				callbacks.addAll(buildIncomingCallbacksFromCommandNodes(service, ifNode.getElseIfNodes()));
				if (ifNode.getElseNode() != null)
					callbacks.addAll(buildIncomingCallbacksFromCommandNodes(service, ifNode.getElseNode().getStatementNodes()));
			}
		}
		return callbacks;
	}
	
	
	/*
	 * Outgoing Callbacks
	 */
	
	protected <T extends CommandNode> List<Callback> buildOutgoingCallbacksFromInvokeNodes(Service service, List<T> commandNodes) throws Exception {
		List<Callback> callbacks = new ArrayList<Callback>();
		Iterator<T> iterator = commandNodes.iterator();
		while (iterator.hasNext()) {
			T commandNode = iterator.next();
			if (commandNode instanceof InvokeNode) {
				InvokeNode invokeNode = (InvokeNode) commandNode;
				callbacks.addAll(buildOutgoingCallbacksFromMessageNodes(invokeNode.getMessageNodes()));
				callbacks.addAll(buildOutgoingCallbacksFromExceptionNodes(invokeNode.getExceptionNodes()));
				callbacks.addAll(buildOutgoingCallbacksFromTimeoutNodes(invokeNode.getTimeoutNodes()));
				
			} else if (commandNode instanceof UnnamedNode) {
				UnnamedNode unnamedNode = (UnnamedNode) commandNode;
				callbacks.addAll(buildOutgoingCallbacksFromInvokeNodes(service, unnamedNode.getStatementNodes()));
				
			} else if (commandNode instanceof IfNode) {
				IfNode ifNode = (IfNode) commandNode;
				callbacks.addAll(buildOutgoingCallbacksFromInvokeNodes(service, ifNode.getStatementNodes()));
				callbacks.addAll(buildOutgoingCallbacksFromInvokeNodes(service, ifNode.getElseIfNodes()));
				if (ifNode.getElseNode() != null)
					callbacks.addAll(buildOutgoingCallbacksFromInvokeNodes(service, ifNode.getElseNode().getStatementNodes()));
			}
		}
		return callbacks;
	}

	protected List<Callback> buildOutgoingCallbacksFromListenNodes(Service service, List<CommandNode> commandNodes) throws Exception {
//		if (service.getName().equalsIgnoreCase("shipBooks"))
//			System.out.println();

		List<Callback> callbacks = new ArrayList<Callback>();
		Iterator<CommandNode> iterator = commandNodes.iterator();
		while (iterator.hasNext()) {
			CommandNode commandNode = iterator.next();
			if (commandNode instanceof ListenNode) {
				ListenNode listenNode = (ListenNode) commandNode;
				callbacks.addAll(buildOutgoingCallbacksFromExceptionNodes(listenNode.getExceptionNodes()));
				callbacks.addAll(buildOutgoingCallbacksFromTimeoutNodes(listenNode.getTimeoutNodes()));
			}
		}
		return callbacks;
	}
	
	protected List<Callback> buildOutgoingCallbacksFromOptionNodes(ReceiveNode receiveNode) throws Exception {
		return buildOutgoingCallbacksFromOptionNodes(receiveNode.getOptionNodes());
	}
	
	protected List<Callback> buildOutgoingCallbacksFromOptionNodes(List<OptionNode> optionNodes) throws Exception {
		List<Callback> callbacks = new ArrayList<Callback>();
		Iterator<OptionNode> iterator = optionNodes.iterator();
		while (iterator.hasNext()) {
			OptionNode optionNode = iterator.next();
			callbacks.addAll(buildOutgoingCallbacksFromCommandNodes(optionNode.getCommandNodes()));
		}
		return callbacks;
	}
	
	protected List<Callback> buildOutgoingCallbacksFromMessageNodes(List<MessageNode> messageNodes) throws Exception {
		List<Callback> callbacks = new ArrayList<Callback>();
		Iterator<MessageNode> iterator = messageNodes.iterator();
		while (iterator.hasNext()) {
			MessageNode messageNode = iterator.next();
			callbacks.addAll(buildOutgoingCallbacksFromCommandNodes(messageNode.getCommandNodes()));
		}
		return callbacks;
	}
	
	protected List<Callback> buildOutgoingCallbacksFromExceptionNodes(List<ExceptionNode> exceptionNodes) throws Exception {
		List<Callback> callbacks = new ArrayList<Callback>();
		Iterator<ExceptionNode> iterator = exceptionNodes.iterator();
		while (iterator.hasNext()) {
			ExceptionNode exceptionNode = iterator.next();
			callbacks.addAll(buildOutgoingCallbacksFromCommandNodes(exceptionNode.getCommandNodes()));
		}
		return callbacks;
	}

	protected List<Callback> buildOutgoingCallbacksFromTimeoutNodes(List<TimeoutNode> timeoutNodes) throws Exception {
		List<Callback> callbacks = new ArrayList<Callback>();
		Iterator<TimeoutNode> iterator = timeoutNodes.iterator();
		while (iterator.hasNext()) {
			TimeoutNode timeoutNode = iterator.next();
			callbacks.addAll(buildOutgoingCallbacksFromCommandNodes(timeoutNode.getCommandNodes()));
		}
		return callbacks;
	}
	
	protected <T extends CommandNode> List<Callback> buildOutgoingCallbacksFromCommandNodes(List<T> commandNodes) throws Exception {
		List<Callback> callbacks = new ArrayList<Callback>();
		Iterator<T> iterator = commandNodes.iterator();
		while (iterator.hasNext()) {
			T commandNode = iterator.next();
			if (commandNode instanceof ReplyNode) {
				ReplyNode replyNode = (ReplyNode) commandNode;
				callbacks.add(buildOutgoingCallbackFromReplyNode(replyNode));

			} else if (commandNode instanceof ListenNode) {
				ListenNode listenNode = (ListenNode) commandNode;
				callbacks.addAll(buildOutgoingCallbacksFromCommandNodes(listenNode.getCommandNodes()));

			} else if (commandNode instanceof UnnamedNode) {
				UnnamedNode unnamedNode = (UnnamedNode) commandNode;
				callbacks.addAll(buildOutgoingCallbacksFromCommandNodes(unnamedNode.getStatementNodes()));

			} else if (commandNode instanceof OptionNode) {
				OptionNode optionNode = (OptionNode) commandNode;
				callbacks.addAll(buildOutgoingCallbacksFromCommandNodes(optionNode.getCommandNodes()));
				
			} else if (commandNode instanceof IfNode) {
				IfNode ifNode = (IfNode) commandNode;
				callbacks.addAll(buildOutgoingCallbacksFromIfNode(ifNode));
			}
		}
		return callbacks;
	}
	
	protected List<Callback> buildOutgoingCallbacksFromIfNode(IfNode ifNode) throws Exception {
		List<Callback> callbacks = new ArrayList<Callback>();
		callbacks.addAll(buildOutgoingCallbacksFromCommandNodes(ifNode.getStatementNodes()));
		List<IfNode> elseIfNodes = ifNode.getElseIfNodes();
		if (elseIfNodes != null) {
			Iterator<IfNode> iterator = elseIfNodes.iterator();
			while (iterator.hasNext()) {
				IfNode elseIfNode = iterator.next();
				callbacks.addAll(buildOutgoingCallbacksFromIfNode(elseIfNode));
			}
		}
		UnnamedNode elseNode = ifNode.getElseNode();
		if (elseNode != null) {
			callbacks.addAll(buildOutgoingCallbacksFromCommandNodes(elseNode.getStatementNodes()));
		}
		return callbacks;
	}

	protected Callback buildOutgoingCallbackFromReplyNode(ReplyNode replyNode) throws Exception {
		Callback callback = buildOutgoingCallback(replyNode.getMessageName());
		callback.setDirection(Direction.OUT);
		return callback;
	}

	protected Callback buildOutgoingCallback(String callbackName) throws Exception {
		int indexOf = callbackName.indexOf("(");
		if (indexOf != -1)
			callbackName = callbackName.substring(0, indexOf);
//		if (callbackName.startsWith("$"))
//			System.out.println();
		//String cappedName = NameUtil.capName(callbackName);
		String uncappedName = NameUtil.uncapName(callbackName);
		
//		if (uncappedName.equalsIgnoreCase("BooksAvailable"))
//			System.out.println();
		
		Callback callback = new Callback();
		callback.setName(uncappedName);
		Operation operation = new Operation();
		operation.setRole("handle");
		operation.setName(uncappedName);
		callback.setSendingService(service);
		//operation.setName("response" + cappedName);
		//operation.setName("callback");
		callback.setTransacted(buildTransacted());

		Parameter parameter = new Parameter();
		parameter.setConstruct("item");
		//String parameterName = messageName; 
		String parameterName = callbackName; 
		if (context.isEnabled("useMessageOrientedInteraction")) {
			if (!parameterName.endsWith("Message"))
				parameterName += "Message";
		}
		
		Type parameterElement = context.findTypeByName(parameterName);
		if (parameterElement == null)
			parameterElement = context.findTypeByName(parameterName+"Message");
		Assert.notNull(parameterElement, "Parameter element not found: "+parameterName);
		if (parameterElement != null) {
			String parameterType = parameterElement.getType();
			Assert.notNull(parameterType, "Parameter type not found: "+parameterName);
			parameter.setName(NameUtil.uncapName(parameterName));
			parameter.setType(NameUtil.capName(parameterType));
			operation.getParameters().add(parameter);
		}
		ServiceUtil.addOperation(callback, operation);
		//operation.getCommands().add();
		return callback;
	}
	
	
	protected void initializeServiceFromPropertyNodes(Service service, Set<PropertyNode> propertyNodes) throws Exception {
		Iterator<PropertyNode> iterator = propertyNodes.iterator();
		while (iterator.hasNext()) {
			PropertyNode propertyNode = iterator.next();
			String name = propertyNode.getName();
			String value = propertyNode.getValue();
			ServiceUtil.setProperty(service, name, value);
		}
	}

	protected List<Listener> buildListenersFromReceiveNode(ParticipantNode participantNode, ReceiveNode receiveNode) throws Exception {
		List<String> channelNames = getChannelNamesFromParticipantNode(participantNode);
		//List<ChannelNode> channelNodes = participantNode.getChannelNodes();
		Iterator<String> iterator = channelNames.iterator();
		List<Listener> listeners = new ArrayList<Listener>();
		while (iterator.hasNext()) {
			String channelName = iterator.next();
			listeners.add(buildListenerFromReceiveNode(participantNode, receiveNode, channelName));
		}
		return listeners;
	}

	protected Listener buildListenerFromReceiveNode(ParticipantNode participantNode, ReceiveNode receiveNode, String channelName) throws Exception {
		String participantName = NameUtil.uncapName(participantNode.getName());
		Listener listener = buildListener(channelName, participantName, participantName);
		return listener;
	}

	protected Listener buildListener(String channel, String link, String role) throws Exception {
		Listener listener = new Listener();
		listener.setName(channel);
		listener.setChannel(channel);
		listener.setLink(link);
		listener.setRole(role);
		return listener;
	}
	
	protected List<Interactor> buildInvokersFromReceiveNode(ParticipantNode participantNode, ReceiveNode receiveNode) throws Exception {
		String participantName = NameUtil.uncapName(participantNode.getName());
		List<Object> invokeNodes = new ArrayList<Object>();
		invokeNodes.addAll(getCallsFromCommandNodes(receiveNode.getCommandNodes(), "invoke"));
		invokeNodes.addAll(getCallsFromOptionNodes(receiveNode.getOptionNodes(), "invoke"));
		invokeNodes.addAll(getCallsFromMessageNodes(receiveNode.getMessageNodes(), "invoke"));
		invokeNodes.addAll(getCallsFromExceptionNodes(receiveNode.getExceptionNodes(), "invoke"));
		invokeNodes.addAll(getCallsFromTimeoutNodes(receiveNode.getTimeoutNodes(), "invoke"));

		List<Interactor> invokers = new ArrayList<Interactor>();
		Iterator<Object> iterator = invokeNodes.iterator();
		while (iterator.hasNext()) {
			InvokeNode invokeNode = (InvokeNode) iterator.next();
			String name = invokeNode.getName();
			String channelId = invokeNode.getDestination();
			int indexOfDot = channelId.indexOf(".");
			String applicationName = name.substring(0, indexOfDot);
			String serviceName = name.substring(indexOfDot+1);
			Invoker invoker = new Invoker();
			invoker.setInteraction(Interaction.INVOKE);
			invoker.setService(serviceName);
			invoker.setName(applicationName);
			invoker.setLink(applicationName);
			invoker.setRole(participantName);
			invoker.setChannel(channelId);
			//TODO invoker.setTimeout(timeout);
			invokers.add(invoker);
		}
		return invokers;
	}

	protected List<Interactor> buildRepliersFromReceiveNode(ParticipantNode participantNode, ReceiveNode receiveNode) throws Exception {
		String participantName = NameUtil.uncapName(participantNode.getName());
		List<Object> replyNodes = new ArrayList<Object>();
		replyNodes.addAll(getCallsFromCommandNodes(receiveNode.getCommandNodes(), "reply"));
		replyNodes.addAll(getCallsFromOptionNodes(receiveNode.getOptionNodes(), "reply"));
		replyNodes.addAll(getCallsFromMessageNodes(receiveNode.getMessageNodes(), "reply"));
		replyNodes.addAll(getCallsFromExceptionNodes(receiveNode.getExceptionNodes(), "reply"));
		replyNodes.addAll(getCallsFromTimeoutNodes(receiveNode.getTimeoutNodes(), "reply"));
//		if (participantName.equals("seller"))
//			System.out.println();

		List<Interactor> interactors = new ArrayList<Interactor>();
		Iterator<Object> iterator = replyNodes.iterator();
		while (iterator.hasNext()) {
			ReplyNode replyNode = (ReplyNode) iterator.next();
			String name = replyNode.getName();
			String callerApplicationName = "";
			Collection<Application> values = this.applicationsByName.values();
			String callbackServiceName = NameUtil.uncapName(name);
			Sender sender = new Sender();
			sender.setInteraction(Interaction.REPLY);
			sender.setService(callbackServiceName);
			sender.setLink(callerApplicationName);
			sender.setRole(participantName);
			sender.setChannel("PLACEHOLDER." + callbackServiceName);
			//TODO invoker.setTimeout(timeout);
			interactors.add(sender);
		}
		return interactors;
	}

	@SuppressWarnings("unchecked")
	protected <T> List<T> getCallsFromCommandNodes(List<CommandNode> commandNodes, String type) throws Exception {
		List<T> invokeNodes = new ArrayList<T>();
		Iterator<CommandNode> iterator = commandNodes.iterator();
		while (iterator.hasNext()) {
			CommandNode commandNode = iterator.next();
			if (commandNode.getText().equals(type)) {
				invokeNodes.add((T) commandNode);
			}
		}
		return invokeNodes;
	}

	@SuppressWarnings("unchecked")
	protected <T> List<T> getCallsFromOptionNodes(List<OptionNode> optionNodes, String type) throws Exception {
		List<T> list = new ArrayList<T>();
		Iterator<OptionNode> iterator = optionNodes.iterator();
		while (iterator.hasNext()) {
			OptionNode optionNode = iterator.next();
			if (optionNode.getText().equals(type)) {
				list.addAll((List<T>) getCallsFromCommandNodes(optionNode.getCommandNodes(), type));
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	protected <T> List<T> getCallsFromMessageNodes(List<MessageNode> messageNodes, String type) throws Exception {
		List<T> list = new ArrayList<T>();
		Iterator<MessageNode> iterator = messageNodes.iterator();
		while (iterator.hasNext()) {
			MessageNode messageNode = iterator.next();
			if (messageNode.getText().equals(type)) {
				list.addAll((List<T>) getCallsFromCommandNodes(messageNode.getCommandNodes(), type));
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	protected <T> List<T> getCallsFromExceptionNodes(List<ExceptionNode> exceptionNodes, String type) throws Exception {
		List<T> list = new ArrayList<T>();
		Iterator<ExceptionNode> iterator = exceptionNodes.iterator();
		while (iterator.hasNext()) {
			ExceptionNode exceptionNode = iterator.next();
			if (exceptionNode.getText().equals(type)) {
				list.addAll((List<T>) getCallsFromCommandNodes(exceptionNode.getCommandNodes(), type));
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	protected <T> List<T> getCallsFromTimeoutNodes(List<TimeoutNode> timeoutNodes, String type) throws Exception {
		List<T> list = new ArrayList<T>();
		Iterator<TimeoutNode> iterator = timeoutNodes.iterator();
		while (iterator.hasNext()) {
			TimeoutNode timeoutNode = iterator.next();
			if (timeoutNode.getText().equals(type)) {
				list.addAll((List<T>) getCallsFromCommandNodes(timeoutNode.getCommandNodes(), type));
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	protected <T, Y extends CommandNode> List<T> getCallsFromNodes(List<Y> nodes, String type) throws Exception {
		List<T> list = new ArrayList<T>();
		Iterator<Y> iterator = nodes.iterator();
		while (iterator.hasNext()) {
			Y node = iterator.next();
			if (node.getText().equals(type)) {
				list.add((T) node);
			}
		}
		return list;
	}
	
	protected void postProcessProjectInitialiation(Project project) throws Exception {
		postProcessProjectInitialiation(ProjectUtil.getApplications(project));
		//engine.getModelHelper().assureProjectObjects(project);
	}

	protected void postProcessProjectInitialiation(Collection<Application> applications) throws Exception {
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			postProcessProjectInitialiation(application);
		}
	}
	
	protected void postProcessProjectInitialiation(Application application) throws Exception {
		ApplicationUtil.addDependencies(application, createDependencies(application));
		Collection<Service> services = ApplicationUtil.getServices(application);
		Iterator<Service> iterator = services.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			postProcessProjectInitialiation(service);
		}
	}
	
	protected void postProcessProjectInitialiation(Service service) throws Exception {
		List<Interactor> replyInteractors = ServiceUtil.getInteractors(service, Interaction.REPLY);
		Iterator<Interactor> iterator = replyInteractors.iterator();
		while (iterator.hasNext()) {
			Interactor interactor = iterator.next();
			String serviceName = interactor.getService();
			//Application application = applicationsByService.get(serviceName);
			///if (application != null) {
			//	interactor.setLink(application.getName());
			//	interactor.setChannel(application.getName());
			//}
		}
	}

	protected List<Dependency> createDependencies(Application application) throws Exception {
		List<Dependency> dependencies = new ArrayList<Dependency>();
		List<Channel> channels = ApplicationUtil.getChannels(application);
		Iterator<Channel> iterator = channels.iterator();
		String applicationName = application.getName();
		while (iterator.hasNext()) {
			Channel channel = iterator.next();
			//List<Module> projectModules = ProjectUtil.getProjectModules(project);
			//dependencies.addAll(createDependenciesFromProjectModules(applicationName, projectModules));
			dependencies.addAll(createDependenciesFromApplicationModules(applicationName, channel.getClients()));
			dependencies.addAll(createDependenciesFromApplicationModules(applicationName, channel.getServices()));
		}
		return dependencies;
	}

	protected List<Dependency> createDependenciesFromProjectModules(String applicationName, List<Module> projectModules) throws Exception {
		List<Dependency> dependencies = new ArrayList<Dependency>();
		Iterator<Module> iterator = projectModules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			Dependency dependency = new Dependency();
			dependency.setGroupId(module.getGroupId());
			dependency.setArtifactId(module.getArtifactId());
			dependency.setVersion(module.getVersion());
			dependency.setScope(DependencyScope.COMPILE);
			dependency.setType(DependencyType.JAR);
			dependencies.add(dependency);
		}
		return dependencies;
	}

	protected List<Dependency> createDependenciesFromApplicationModules(String currentApplicationName, List<String> applicationNames) throws Exception {
		//DependencyUtil.createDependency(groupId, artifactId, version, scope, type);
		Map<String, Module> moduleMap = ProjectUtil.getModulesAsMap(project);
		List<Dependency> dependencies = new ArrayList<Dependency>();
		Iterator<String> iterator = applicationNames.iterator();
		while (iterator.hasNext()) {
			String applicationName = iterator.next();
			if (!applicationName.equalsIgnoreCase(currentApplicationName)) {
				Application application = ProjectUtil.getApplicationByName(project, applicationName);
				//TODO - Externalize these properly
				String modelModuleArtifactId = application.getArtifactId() + "-model";
				
				//check to make sure the module module exists
				if (moduleMap.containsKey(modelModuleArtifactId)) {
					Dependency dependency = new Dependency();
					dependency.setGroupId(application.getGroupId());
					dependency.setArtifactId(modelModuleArtifactId);
					dependency.setVersion(application.getVersion());
					dependency.setScope(DependencyScope.COMPILE);
					dependency.setType(DependencyType.JAR);
					dependencies.add(dependency);
				}
				
				//we assume the client module *always* exists
				Dependency dependency = new Dependency();
				dependency.setGroupId(application.getGroupId());
				//TODO - Externalize these properly
				dependency.setArtifactId(application.getArtifactId() + "-client");
				dependency.setVersion(application.getVersion());
				dependency.setScope(DependencyScope.COMPILE);
				dependency.setType(DependencyType.JAR);
				dependencies.add(dependency);
			}
		}
		return dependencies;
	}

}
