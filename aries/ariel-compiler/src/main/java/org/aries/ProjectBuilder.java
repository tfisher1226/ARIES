package org.aries;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nam.ProjectLevelHelper;
import nam.client.ClientLayerHelper;
import nam.data.DataLayerFactory;
import nam.data.DataLayerHelper;
import nam.model.Application;
import nam.model.Import;
import nam.model.Information;
import nam.model.Messaging;
import nam.model.Module;
import nam.model.ModuleType;
import nam.model.Namespace;
import nam.model.Persistence;
import nam.model.Placeholders;
import nam.model.Project;
import nam.model.util.ExtensionsUtil;
import nam.model.util.ModuleUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.PersistenceUtil;
import nam.model.util.PlaceholderUtil;
import nam.model.util.ProjectUtil;
import nam.service.ServiceLayerHelper;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.aries.ast.AriesASTBuilder;
import org.aries.ast.AriesASTContext;
import org.aries.ast.AriesModelBuilder;
import org.aries.ast.ModelVerificationBuilder;
import org.aries.ast.node.ImportNode;
import org.aries.ast.node.NetworkNode;
import org.aries.runtime.BeanContext;
import org.aries.util.FileUtil;
import org.aries.util.NameUtil;
import org.aries.util.properties.PropertyInitializer;
import org.aries.util.properties.PropertyManager;
import org.aries.validate.Checkpoints;

import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.engine.GeneratorEngine;
import aries.generation.model.AnnotationUtil;


/** 
 * Parses an Ariel file or directory of Ariel files using the generated parser
 * ANTLR builds from Ariel.g.
 */
public class ProjectBuilder {

private static final String DEFAULT_VERSION = "0.0.1-SNAPSHOT";

//	protected static String USER_DIR = System.getProperty("user.dir");
//
//	protected static String MODEL_LOCATION = USER_DIR + "/source/main/resources";
//	
//	protected static String PROPERTY_LOCATION = "/workspace/ARIES/aries/properties";
//	
//	//protected static String PROPERTY_LOCATION = USER_DIR + "/properties";
//
//	protected static String WORKSPACE_LOCATION = FilenameUtils.getFullPath(USER_DIR);
//	
//	protected static String RUNTIME_LOCATION = USER_DIR + "/target/runtime";
	
	
	private long lexerTime;

	private boolean profile;

	private ArielLexer lexer;

	private ArielParser parser;

	private String inputFileName;
	
	private GenerationContext context;

	private GeneratorEngine engine;
	
	private Object mutex;


	public ProjectBuilder(GenerationContext context) {
		this.context = context;
		this.mutex = ProjectBuilder.class;
		ProjectLevelHelper.context = context;
		DataLayerFactory.context = context;
		DataLayerHelper.context = context;
		ServiceLayerHelper.context = context;
		ClientLayerHelper.context = context;
		AnnotationUtil.context = context;
		CodeUtil.context = context;
		//initialize();
	}
	
    public ArielLexer getLexer() {
		return lexer;
	}

	public ArielParser getParser() {
		return parser;
	}

	public GenerationContext getContext() {
		return context;
	}

	public GeneratorEngine getEngine() {
		return engine;
	}
	
//	protected static GenerationContext createContext() {
//		GenerationContext context = new GenerationContext();
//		context.setModelLocation(MODEL_LOCATION);
//		context.setPropertyLocation(PROPERTY_LOCATION);
//		context.setWorkspaceLocation(WORKSPACE_LOCATION);
//		context.setRuntimeLocation(RUNTIME_LOCATION);
//		context.setProperty("generateServiceTransport", "JAXWS");
//		context.setProperty("useQualifiedContextNames");
//		//context.setProperty("generateServicePerOperation");
//		//context.setProperty("generateMessageLevelTransport");
//    	//context.setTargetWorkspace("../../ARIES_GENERATED");
//    	context.setTargetWorkspace("C:/workspace/ARIES");
//    	//context.setTargetWorkspace("..");
//    	context.setTemplateWorkspace("..");
//    	context.setTemplateName("template1");
//		context.addSubset("project");
//		context.addSubset("service");
//		context.addSubset("model");
//		context.addSubset("client");
//		context.addSubset("data");
//		context.addSubset("view");
//		context.addSubset("ear");
//		return context;
//	}

//	protected GenerationContext createGenerationContext() throws Exception {
//		GenerationContext context = new GenerationContext();
//    	//context.setProjectName(projectName);
//		context.setModelLocation(MODEL_LOCATION);
//		context.setRuntimeLocation(RUNTIME_LOCATION);
//		context.setWorkspaceLocation(WORKSPACE_LOCATION);
//    	context.setTargetWorkspace("..");
//    	context.setTemplateWorkspace("..");
//    	context.setTemplateName("template1");
//    	context.setProjectName("infosgi");
//    	context.setProjectGroupId("org.sgiusa");
//    	context.setProjectDomain("org.sgiusa");
//		//context.addSubset("project");
//		context.addSubset("data");
//		context.setProperty("generateTableAnnotation", "true");
//		context.setProperty("persistenceUnitName", "SgiusaDS");
//		context.setProperty("dataSourceUsername", "manager");
//		context.setProperty("dataSourcePassword", "m");
//		context.setProperty("databaseDriverClass", "com.mysql.jdbc.Driver");
//		context.setProperty("databaseConnectionUrl", "jdbc:mysql://localhost:3306/sgiusadb");
//		return context;
//	}
	
	protected ArielLexer createLexer() {
		return new ArielLexer();
	}

	protected ArielParser createParser(TokenStream input) {
		return new ArielParser(input);
	}
	
	public void initialize(String inputFileName) throws Exception {
		synchronized (mutex) {
			this.inputFileName = inputFileName;
			
			assureGenerationContextInitialized(context);
			String modelLocation = FileUtil.normalizePath(context.getModelLocation());
			String runtimeLocation = FileUtil.normalizePath(context.getRuntimeLocation());
			//String workspaceLocation = FileUtil.normalizePath(context.getWorkspaceLocation());
			String propertyLocation = FileUtil.normalizePath(context.getPropertyLocation());
			String cacheLocation = FileUtil.normalizePath(context.getCacheLocation());

//			String ARIES_COMMON_MODEL = "aries-common-1.0.aries";
//			String ARIES_COMMON_XSD = "aries-common-1.0.xsd";
//			String WORKSPACE_LOCATION = "c:/workspace/ARIES";
//			File ariesCommonModelFile = new File(WORKSPACE_LOCATION  + "/aries/project/common/project/common-model/src/main/resources/schema/" + ARIES_COMMON_MODEL);
//			File ariesCommonSchemaFile = new File(WORKSPACE_LOCATION + "/aries/project/common/project/common-model/src/main/resources/schema/" + ARIES_COMMON_XSD);
//			File commonModelLocationPath = new File(modelLocation + "/common");
//			FileUtils.copyFileToDirectory(ariesCommonModelFile, commonModelLocationPath);
//			FileUtils.copyFileToDirectory(ariesCommonSchemaFile, commonModelLocationPath);
			
			//TODO establish source and target property locations per module
			//processPropertySubstitution(runtimeLocation, context.getModelLocation(), propertyLocation, "/properties");

//			String location = runtimeLocation;
//			String subFolder = FileUtil.getDirectory(inputFileName);
//			if (subFolder != null) {
//				if (subFolder.startsWith("/model"))
//					location += subFolder;
//				else if (subFolder.startsWith("model"))
//					location += "/" + subFolder;
//				else location += "/model/" + subFolder;
//			}
//			location = FileUtil.normalizePath(location);
//			File sourceLocation = new File(location);
//			Set<String> subFolders = ImportUtil.getImportedFileFolders(sourceLocation);
//			Iterator<String> iterator = subFolders.iterator();
//			while (iterator.hasNext()) {
//				String subFolderName = iterator.next();
//				processPropertySubstitution(context.getCacheLocation(), runtimeLocation, propertyLocation, subFolderName);
//			}

			/*
			 * Process token (variable) substitution on required property files
			 * All processed files will be copied to "cacheLocation".
			 */
			processPropertySubstitution(cacheLocation, modelLocation, propertyLocation, null);

			PropertyManager propertyManager = BeanContext.get("org.aries.propertyManager");
			if (propertyManager == null)
				propertyManager = PropertyManager.getInstance();
			if (propertyManager == null)
				propertyManager = new PropertyManager();
			BeanContext.set("org.aries.propertyManager", propertyManager);
			propertyManager.setPropertyLocation(propertyLocation);
			propertyManager.initialize();
		}
	}
	
	protected void processPropertySubstitution(String runtimeLocation, String modelLocation, String propertyLocation, String subFolder) {
		PropertyInitializer propertyInitializer = new PropertyInitializer();
		propertyInitializer.setRuntimeLocation(runtimeLocation);
		propertyInitializer.setWorkingLocation(modelLocation);
		propertyInitializer.setPropertyLocation(propertyLocation);
		if (subFolder != null && !subFolder.startsWith("/model"))
			subFolder = "/model" + subFolder;
		propertyInitializer.setSubFolder(subFolder);
		propertyInitializer.initialize();
	}

	protected void assureGenerationContextInitialized(GenerationContext context2) {
		Assert.notNull(context, "Generation context must be provided");
		if (context.getTargetWorkspace() == null)
			context.setTargetWorkspace(context.getWorkspaceLocation());
	}

	public Project buildProject() throws Exception {
		return buildProject(null, null, null, DEFAULT_VERSION);
	}
	
	public Project buildProject(String name) throws Exception {
		return buildProject(name, name, null, DEFAULT_VERSION);
	}
	
	public Project buildProject(String domain, String name) throws Exception {
		Project project = buildProject(domain, name, "http://" + name, DEFAULT_VERSION);
		return project;
	}
	
	public Project buildProject(String domain, String name, String namespace, String version) throws Exception {
		engine = new GeneratorEngine(context);
		engine.initialize();
		
		AriesModelBuilder modelBuilder = new AriesModelBuilder(engine);
		Project project = modelBuilder.buildProject(domain, name, namespace, version);
		//ProjectUtil.addModule(project, buildModelModuleForProjectNamespace(project));
		//ProjectUtil.addModule(project, buildProjectViewModule(project));
		//Collection<Application> applications = buildApplicationsFromParticipantNodes(protocolNode.getParticipantNodes());
		//List<Group> groups = buildGroupsFromGroupNodes(protocolNode.getGroupNodes());
		//ProjectUtil.addApplications(project, applications);
		//ProjectUtil.addGroups(project, groups);
		return project;
	}
	
	public List<Project> buildProjects() throws Exception {
		if (inputFileName.endsWith(".ariel"))
			return buildProjectsFromARIEL(inputFileName);
		if (inputFileName.endsWith(".aries"))
			return buildProjectsFromARIES(inputFileName);
		throw new Exception("Unrecognized file type: "+inputFileName);
	}
	
	public List<Project> buildProjectsFromARIEL(String arielFileName) throws Exception {
		//List<Project> projects = new ArrayList<Project>();

		try {
			// Create a scanner that reads from the input stream passed to us
			lexer = createLexer();
			
			//String inputPath = context.getCacheFilePath(arielFileName);
			//String inputPath = context.getFilePath(context.getCacheLocation() + "/model", null, arielFileName);
			String inputPath = context.getCacheFilePath(arielFileName);
			File inputFile = new File(inputPath);
			File parentDirectory = inputFile.getParentFile();
			String homeDirectory = parentDirectory.getAbsolutePath();
			ANTLRFileStream input = new ANTLRFileStream(inputPath);
			lexer.setCharStream(input);

			CommonTokenStream tokens = new CommonTokenStream();
			//tokens.discardOffChannelTokens(true);
			tokens.setTokenSource(lexer);
			long start = System.currentTimeMillis();
			tokens.LT(1); // force load
			long stop = System.currentTimeMillis();
			lexerTime += stop-start;

			// Create a parser that reads from the scanner
			parser = createParser(tokens);

			// start parsing at the compilationUnit rule
			ArielParser.compilationUnit_return ast = parser.compilationUnit();
			int errorCount = parser.getNumberOfSyntaxErrors();
			System.err.print("Parsed file: "+inputPath);
			System.err.println(", errors: "+errorCount);
			
			System.err.println("ARIEL Parser: parsed "+inputPath);
			CommonTree tree = (CommonTree) ast.getTree();			
			//printTree(tree, 1);

			//establish source and runtime file locations
			//String modelFilePath = context.getModelFilePath("model", arielFileName);
			//String modelFilePath = context.getFilePath(context.getModelLocation() + "/model", null, arielFileName);
			String modelFilePath = context.getCacheFilePath(arielFileName);
			String modelFileFolder = NameUtil.getParentDirectoryName(modelFilePath);
			context.setModelFileContext(modelFileFolder);
			context.setRuntimeFileContext(homeDirectory);
			String runtimeLocation = context.getRuntimeLocation();
			
			//convert to Ariel AST Model
			AriesASTBuilder ariesASTBuilder = new AriesASTBuilder(context);
			AriesASTContext.importNodes = ariesASTBuilder.buildImportNodes(runtimeLocation, tree);
			AriesASTContext.networkNodes = ariesASTBuilder.buildNetworkNodes(tree);

			//TODO assuming only one network node for now
			AriesASTContext.networkNode = AriesASTContext.networkNodes.get(0);

			//convert to Ariel source output (Text based)
			//ArielASTPrinter printer = new ArielASTPrinter(true);
			//String arielOutout = printer.getSource(protocolNodes.get(0));
			//System.out.println(arielOutout);

			//configure the desired target generation context
			engine = new GeneratorEngine(context);
			engine.initialize();

//			String modelFilePath = context.getModelFilePath(arielFileName);
//			String modelFileFolder = NameUtil.getParentDirectoryName(modelFilePath);
//			context.setModelFileContext(modelFileFolder);

			//convert to Aries Model
//			context.setProjects(projects);
//			context.setRuntimeFileContext(homeDirectory);
			AriesModelBuilder modelBuilder = new AriesModelBuilder(engine);
			Placeholders placeholders = modelBuilder.buildPlaceholdersFromImportNodes(AriesASTContext.importNodes);
			List<Project> projectPlaceholders = PlaceholderUtil.getProjectPlaceholders(placeholders);
			engine.getModelHelper().assureProjectObjects(projectPlaceholders);
			context.addProjects(projectPlaceholders);
			
			//build the top-level (main) project here
			Project project = modelBuilder.buildProjectFromProtocolNode(AriesASTContext.networkNode, placeholders);
			engine.getModelHelper().assureProjectObjects(project);
			context.setProject(project);
			context.setProjectName(project.getName());
			context.setProjectVersion(project.getVersion());
			context.setProjectDomain(project.getDomain());
			context.addProject(project);

			enrichExtensionsFromModules(project);
			markImportedNamespaces(context.getNamespaces());

			//construct Model-driven runtime checks
			List<Project> projectList = context.getProjectList();
			buildCheckpoints(projectList);
			//we need to reset current project
			context.setProject(project);

			// make sure all other imports are added to project
			addToProjectImportsFromImportNodes(project, AriesASTContext.importNodes);
			addToProjectImportsFromPlaceholders(project, projectPlaceholders);
			ProjectUtil.addSubProjects(project, projectPlaceholders);
			addPlaceholdersToExtensions(project, placeholders);
			enrichModulesFromExtensions(projectPlaceholders);
			enrichModulesFromExtensions(project);

			engine.getModelHelper().assureProjectObjects(project);
			// construct a project-wide application (if it does not already exist)
			Collection<Application> applications = ProjectUtil.getApplications(project);
			if (applications.size() > 1)
				engine.getModelBuilder().buildApplication(project);
			engine.getModelHelper().assureProjectImports(project);
			engine.getModelHelper().processProjectImports(project);
			engine.getModelHelper().assureProjectObjects(project);
			//engine.getModelHelper().buildProjectModules(project);
			//engine.getModelHelper().assureProjectObjects(project);

			// create top-level import record
			Import rootImport = createRootImport(arielFileName);
			ProjectUtil.addImport(project, rootImport);
			rootImport.setObject(project);

		} catch (Exception e) {
			System.err.print("ARIEL parser exception: ");
			e.printStackTrace();
		}
		
		List<Project> projectList = context.getProjectList();
		return projectList;
	}
	
	public List<Project> buildProjectsFromARIES(String ariesFileName) throws Exception {
		try {
			//configure the desired target generation context
			engine = new GeneratorEngine(context);
			engine.initialize();
			
			List<Project> projects = buildProjectsFromARIES2(ariesFileName);
			List<Namespace> namespaces = context.getNamespaces();
			markImportedNamespaces(namespaces);

			//construct Model-driven runtime checks 
			buildCheckpoints(projects);
			return projects;
			
		} catch (Exception e) {
			System.err.println("ARIES parser exception: "+e);
			e.printStackTrace();
			throw e;
		}
	}

	public List<Project> buildProjectsFromARIES2(String ariesFileName) throws Exception {
		String modelFilePath = context.getFilePath(context.getRuntimeLocation() + "/model", null, ariesFileName, false);
		//String modelFilePath = context.getModelFilePath(ariesFileName);
		String modelFileFolder = NameUtil.getParentDirectoryName(modelFilePath);
		context.setModelFileContext(modelFileFolder);

		String runtimeFilePath = context.getFilePath(context.getCacheLocation() + "/model", null, ariesFileName);
		//String runtimeFilePath = context.getRuntimeFilePath(ariesFileName);
		String runtimeFileFolder = NameUtil.getParentDirectoryName(runtimeFilePath);
		context.setRuntimeFileContext(runtimeFileFolder);

		Project project = engine.readFromFileSystem(runtimeFilePath);
		context.addProject(project);
		
		engine.getModelHelper().assureProjectObjects(project);
		AriesModelBuilder modelBuilder = new AriesModelBuilder(engine);
		Placeholders placeholders = modelBuilder.buildPlaceholdersFromImports(ProjectUtil.getImports(project));
		List<Project> projectPlaceholders = PlaceholderUtil.getProjectPlaceholders(placeholders);
		context.addProjects(projectPlaceholders);

		List<Project> projectList = context.getProjectList();
		ProjectUtil.addSubProjects(project, projectPlaceholders);
		addPlaceholdersToExtensions(project, placeholders);
		addBlocksToExtensions(project, projectList);
		enrichModulesFromExtensions(projectList);
		enrichModulesFromExtensions(project);
		//projects.add(project);
		
		//convert to Aries Model
		engine.getModelHelper().processProjectImports(project);
		engine.getModelHelper().assureProjectObjects(project);
		engine.getModelHelper().buildProjectModules(project);
		engine.getModelHelper().assureProjectObjects(project, true);

		// create top-level import record
		Import rootImport = createRootImport(ariesFileName);
		ProjectUtil.addImport(project, rootImport);
		rootImport.setObject(project);
		return projectList;
	}
	

	public void buildInformationModel(Project project) throws Exception {
		buildInformationModel(project, inputFileName);
	}
	
	public void buildInformationModel(Project project, String ariesFileName) throws Exception {
       	//Import importedFromFile = buildImportFromFile(context.getModelLocation(), ariesFileName);
       	Import importedFromFile = buildImportFromFile(context.getCacheLocation() + "/model", ariesFileName);
       	ProjectUtil.addImport(project, importedFromFile);

    	String inputPath = context.getCacheFilePath(ariesFileName);
		engine = new GeneratorEngine(context);
		engine.initialize();
       	
		//String modelFilePath = context.getFilePath(context.getRuntimeLocation() + "/model", null, ariesFileName);
		//String modelFilePath = context.getModelFilePath(ariesFileName);
		String modelFileFolder = NameUtil.getParentDirectoryName(inputPath);
		context.setModelFileContext(modelFileFolder);

		String cacheLocation = context.getCacheLocation();
		String runtimeFilePath = context.getFilePath(cacheLocation + "/model", null, ariesFileName);
		//String runtimeFilePath = context.getRuntimeFilePath(ariesFileName);
		String runtimeFileFolder = NameUtil.getParentDirectoryName(runtimeFilePath);
		context.setRuntimeFileContext(runtimeFileFolder);

		Information model = engine.readInformationModelFromFileSystem(runtimeFilePath);
		engine.getModelHelper().assureInformationBlock(project, model);

		//convert to Aries Model
		ProjectUtil.addInformationBlock(project, model);
		engine.getModelHelper().processProjectImports(project, model);
		engine.getModelHelper().assureProjectObjects(project, model);
		engine.getModelHelper().buildProjectModules(project, model);
		engine.getModelHelper().assureProjectObjects(project, model);
    }
	
	
	public void buildPersistenceModel(Project project) throws Exception {
		buildPersistenceModel(project, inputFileName);
	}
	
	public void buildPersistenceModel(Project project, String ariesFileName) throws Exception {
       	//Import importedFromFile = buildImportFromFile(context.getModelLocation(), ariesFileName);
       	Import importedFromFile = buildImportFromFile(context.getCacheLocation() + "/model", ariesFileName);
       	ProjectUtil.addImport(project, importedFromFile);

    	String inputPath = context.getCacheFilePath(ariesFileName);
		engine = new GeneratorEngine(context);
		engine.initialize();
       	
		//String modelFilePath = context.getFilePath(context.getRuntimeLocation() + "/model", null, ariesFileName);
		//String modelFilePath = context.getModelFilePath(ariesFileName);
		String modelFileFolder = NameUtil.getParentDirectoryName(inputPath);
		context.setModelFileContext(modelFileFolder);

		String cacheLocation = context.getCacheLocation();
		String runtimeFilePath = context.getFilePath(cacheLocation + "/model", null, ariesFileName);
		//String runtimeFilePath = context.getRuntimeFilePath(ariesFileName);
		String runtimeFileFolder = NameUtil.getParentDirectoryName(runtimeFilePath);
		context.setRuntimeFileContext(runtimeFileFolder);

		Persistence model = engine.readPersistenceModelFromFileSystem(runtimeFilePath);
		engine.getModelHelper().assurePersistenceBlock(null, model);

//		Information model = engine.readInformationModelFromFileSystem(runtimeFilePath);
//		engine.getModelHelper().assureInformation(model);
//		
//		buildPersistenceForNamespace(Module module, Namespace namespace);

		//AriesModelBuilder modelBuilder = new AriesModelBuilder(engine);
		//Placeholders placeholders = modelBuilder.buildPlaceholdersFromImports(ProjectUtil.getImports(project));
		//List<Project> projectPlaceholders = PlaceholderUtil.getProjectPlaceholders(placeholders);
		//projects.addAll(projectPlaceholders);

		//ProjectUtil.addSubProjects(project, projectPlaceholders);
		//addPlaceholdersToExtensions(project, placeholders);
		//addBlocksToExtensions(project, projects);
		//enrichModulesFromExtensions(projects);
		//enrichModulesFromExtensions(project);
		//projects.add(project);
		
		//convert to Aries Model
		engine.getModelHelper().processProjectImports(project, model);
		NamespaceUtil.setImported(PersistenceUtil.getNamespaces(model), false);
		engine.getModelHelper().assureProjectObjects(project, model);
		engine.getModelHelper().buildProjectModules(project, model);
		engine.getModelHelper().assureProjectObjects(project, model);
    }
	

//	protected String getAriesInputFilePath(String fileName) {
//		String modelLocation = context.getModelLocation();
//		String filePath = modelLocation + "/" + fileName;
//		return FileUtil.normalizePath(filePath);
//	}
//	
//	protected String getXSDInputFilePath(String fileName) {
//		if (fileName.endsWith(".aries"))
//			fileName = fileName.replace(".aries", ".xsd");
//		String filePath = getAriesInputFilePath(fileName);
//		return filePath;
//	}
	
	protected void enrichExtensionsFromModules(Project project) {
		Set<Module> modelModules = ProjectUtil.getModelModules(project);
		Set<Module> dataModules = ProjectUtil.getDataModules(project);
		List<Information> informationBlocks = ModuleUtil.getInformationBlocks(modelModules);
		List<Persistence> persistenceBlocks = ModuleUtil.getPersistenceBlocks(dataModules);
		ExtensionsUtil.addExtensions(project, informationBlocks);
		ExtensionsUtil.addExtensions(project, persistenceBlocks);
	}

	protected void addPlaceholdersToExtensions(Project project, Placeholders placeholders) {
		Collection<Information> informationBlocks = PlaceholderUtil.getInformationPlaceholders(placeholders);
		Collection<Persistence> persistenceBlocks = PlaceholderUtil.getPersistencePlaceholders(placeholders);
		Collection<Messaging> messagingBlocks = PlaceholderUtil.getMessagingPlaceholders(placeholders);
		ExtensionsUtil.addExtensions(project, informationBlocks);
		ExtensionsUtil.addExtensions(project, persistenceBlocks);
		ExtensionsUtil.addExtensions(project, messagingBlocks);
	}

	protected void addBlocksToExtensions(Project targetProject, List<Project> sourceProjects) {
		Iterator<Project> iterator = sourceProjects.iterator();
		while (iterator.hasNext()) {
			Project sourceProject = iterator.next();
			addBlocksToExtensions(targetProject, sourceProject);
		}
	}
	
	protected void addBlocksToExtensions(Project targetProject, Project sourceProject) {
		Collection<Information> informationBlocks = ProjectUtil.getInformationBlocks(sourceProject);
		Collection<Persistence> persistenceBlocks = ProjectUtil.getPersistenceBlocks(sourceProject);
		Collection<Messaging> messagingBlocks = ProjectUtil.getMessagingBlocks(sourceProject);
		ExtensionsUtil.addExtensions(targetProject, informationBlocks);
		ExtensionsUtil.addExtensions(targetProject, persistenceBlocks);
		ExtensionsUtil.addExtensions(targetProject, messagingBlocks);
	}
	
	protected void enrichModulesFromExtensions(List<Project> projects) {
		Iterator<Project> iterator = projects.iterator();
		while (iterator.hasNext()) {
			Project project = iterator.next();
			enrichModulesFromExtensions(project);
		}
	}
	
	protected void enrichModulesFromExtensions(Project project) {
		enrichModulesFromExtensions(project, ModuleType.MODEL);
		enrichModulesFromExtensions(project, ModuleType.DATA);
		//enrichModulesFromExtensions(project, ModuleType.SERVICE);
		//enrichModulesFromExtensions(project, ModuleType.CLIENT);
	}
	
	protected void enrichModulesFromExtensions(Project project, ModuleType moduleType) {
		Map<String, Module> map = ProjectUtil.getModulesAsMap(project, moduleType);
		Collection<Module> modules = map.values();
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			switch (moduleType) {
			case MODEL:
				enrichInformationModuleFromExtensions(project, module);
				break;
			case DATA:
				enrichPersistenceModuleFromExtensions(project, module);
				break;
			case SERVICE:
				enrichInformationModuleFromExtensions(project, module);
				enrichPersistenceModuleFromExtensions(project, module);
				break;
			}
			
		}
	}

	protected void enrichInformationModuleFromExtensions(Project project, Module module) {
		//Assert.isTrue(module.getType() == ModuleType.MODEL, "ModuleType must be MODEL");
		Information currentBlock = module.getInformation();
		Namespace namespace = currentBlock.getNamespaces().get(0);
		Information informationBlock = ExtensionsUtil.getInformationBlock(project, namespace);
		Assert.notNull(informationBlock, "Information block not found for namespace: "+namespace.getUri());
		if (currentBlock != informationBlock) {
			//WHY THIS? ExtensionsUtil.mergeInformationBlocks(currentBlock, informationBlock);
			//System.out.println();
		}
	}

	protected void enrichPersistenceModuleFromExtensions(Project project, Module module) {
		//Assert.isTrue(module.getType() == ModuleType.DATA, "ModuleType must be DATA");
		Persistence currentBlock = module.getPersistence();
		String blockName = currentBlock.getName();
		String blockDomain = currentBlock.getDomain();
//		if (blockDomain == null)
//			System.out.println();
		Persistence extensionBlock = ExtensionsUtil.getPersistenceBlock(project, blockDomain, blockName);
		//Assert.notNull(extensionBlock, "Persistence block not found for name: "+blockDomain+"."+blockName);
		if (extensionBlock == null && extensionBlock != currentBlock) {
			//WHY THIS? ExtensionsUtil.mergePersistenceBlocks(currentBlock, extensionBlock);
			System.out.println();
		}
	}

	protected Import createRootImport(String fileName) {
		Import rootImport = new Import();
		rootImport.setInclude(true);
		int lastIndexOf = fileName.lastIndexOf(".");
		String fileExtension = fileName.substring(lastIndexOf + 1);
		rootImport.setDir(context.getRuntimeLocation());
		rootImport.setFile(fileName);
		rootImport.setType(fileExtension);
		return rootImport;
	}

	protected void addToProjectImportsFromImportNodes(Project project, List<ImportNode> importNodes) {
		Iterator<ImportNode> iterator = importNodes.iterator();
		while (iterator.hasNext()) {
			ImportNode importNode = (ImportNode) iterator.next();
			Import importedFile = buildImportFromImportNode(importNode);
			ProjectUtil.addImport(project, importedFile);
		}
	}

	public Import buildImportFromImportNode(ImportNode importNode) {
		String fileName = importNode.getName() + "." + importNode.getValue();
		int lastIndexOf = fileName.lastIndexOf(".");
		String fileExtension = fileName.substring(lastIndexOf+1);
		Import importedFile = new Import();
		importedFile.setDir(importNode.getParentDirectory());
		importedFile.setFile(fileName);
		importedFile.setType(fileExtension);
		return importedFile;
	}
	
	public Import buildImportFromFile(String parentDirectory, String fileName) throws FileNotFoundException {
		int lastIndexOf = fileName.lastIndexOf(".");
		String fileExtension = fileName.substring(lastIndexOf+1);
		//String inputPath = context.getModelLocation();
		//String inputPath = context.getCacheFilePath(fileName);
		//String parentDirectory = context.getCacheLocation() + "/model";
		//File filePath = new File(fileName);
//		File parentDirectory = inputFile.getParentFile();
//		String parentDirectoryPath = parentDirectory.getPath();
//		String parentDirectory = filePath.getParentFile().getPath();
//		parentDirectory = FileUtil.normalizePath(parentDirectory);
		
		Import importedFile = new Import();
		importedFile.setDir(FileUtil.normalizePath(parentDirectory));
		importedFile.setFile(FileUtil.normalizePath(fileName));
		importedFile.setType(fileExtension);
		return importedFile;
	}
	
	protected void addToProjectImportsFromPlaceholders(Project project, List<Project> placeholders) {
		Iterator<Project> iterator = placeholders.iterator();
		while (iterator.hasNext()) {
			Project placeholder = iterator.next();
			List<Import> importsFromSubProject = ProjectUtil.getImports(placeholder);
			ProjectUtil.addImports(project, importsFromSubProject);
		}
		
	}

	//addImportsToProject(project, protocolNode);
	protected void addImportsToProject(Project project, NetworkNode protocolNode) {
		List<Import> imports = ProjectUtil.getImports(project);
		Iterator<Import> iterator = imports.iterator();
		while (iterator.hasNext()) {
			Import importedFile = iterator.next();
			ProjectUtil.addImport(project, importedFile);
		}
	}


	protected void markImportedNamespaces(List<Namespace> namespaces) {
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			Module module = context.getModelModuleByNamespace(namespace);
			namespace.setImported(module == null);
		}
	}
	
	protected void buildCheckpoints(List<Project> projects) {
		Iterator<Project> iterator = projects.iterator();
		while (iterator.hasNext()) {
			Project project = iterator.next();
			buildCheckpoints(project);
		}
	}

	protected void buildCheckpoints(Project project) {
//		Map<String, Checkpoints> checks = new HashMap<String, Checkpoints>();
//		checks.putAll(modelVerificationBuilder.buildRuntimeChecksForProjects(projectPlaceholders));
//		checks.putAll(modelVerificationBuilder.buildRuntimeChecksForProtocolNode(protocolNodes.get(0)));
		
		context.setProject(project);
		ModelVerificationBuilder modelVerificationBuilder = new ModelVerificationBuilder(engine);
		Map<String, Checkpoints> checkpointMap = modelVerificationBuilder.buildRuntimeChecksForProject(project);
		
		Set<Module> serviceModules = ProjectUtil.getServiceModules(project);
		Iterator<Module> iterator = serviceModules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			context.setModule(module);
			String checkpointsKey = module.getName();
			Checkpoints checkpoints = checkpointMap.get(checkpointsKey);
			module.setCheckpoints(checkpoints);
		}
	}


	/*
	 * The global "context" should already be properly initialized before this is called.
	 * For example, the context.getProjects() should be populated (as well as all other
	 * model structures associated with the expected generated output).  
	 */
	public void generateProjects(List<Project> projects) throws Exception {
		Iterator<Project> projectIterator = projects.iterator();
		while (projectIterator.hasNext()) {
			Project project = projectIterator.next();
			String projectName = project.getName();
			if (context.canGenerateProject(projectName)) {
				context.initializeContext(project);
				context.setProject(project);
				context.setProjectName(projectName);
				context.setProjectVersion(project.getVersion());
				context.setProjectDomain(project.getDomain());
				generateProject(project);
			}
		}
	}

	//List<Namespace> namespaces = PlaceholderUtil.getImportedNamespaces(placeholders);
	//List<InformationNode> informationNodes = buildInformationNodesFromPlaceholders(placeholders);
	//List<InformationNode> informationNodes = modelBuilder.buildInformationNodesFromNamespaces(namespaces);

	public void generateProject(Project project) throws Exception {
		try {
			//TODO use XML to generate sources
			AriesProjectAndSourceGenerator ariesProjectAndSourceGenerator = new AriesProjectAndSourceGenerator(engine);
			ariesProjectAndSourceGenerator.generate(project);
			
		} catch (Exception e) {
			System.err.println("ARIEL parser exception: "+e);
			e.printStackTrace();
		}
	}
	
	public String serializeProject(Project project) throws Exception {
		try {
			//convert to Aries source output (XML based)
			AriesXMLGenerator ariesXMLGenerator = new AriesXMLGenerator(engine);
			String ariesOutout = ariesXMLGenerator.generate(project);
			//System.out.println(ariesOutout);
			return ariesOutout;
			
		} catch (Exception e) {
			System.err.println("ARIEL parser exception: "+e);
			e.printStackTrace();
			return null;
		}
	}

	
}
