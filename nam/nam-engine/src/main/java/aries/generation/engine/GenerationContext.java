package aries.generation.engine;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.xml.namespace.QName;

import nam.ProjectLevelHelper;
import nam.data.DataLayerHelper;
import nam.model.Adapter;
import nam.model.Application;
import nam.model.Applications;
import nam.model.BeanType;
import nam.model.Cache;
import nam.model.Callback;
import nam.model.Channel;
import nam.model.Command;
import nam.model.Component;
import nam.model.Dependency;
import nam.model.Element;
import nam.model.Enumeration;
import nam.model.Field;
import nam.model.Group;
import nam.model.Information;
import nam.model.Input;
import nam.model.Interactor;
import nam.model.Invocation;
import nam.model.Invoker;
import nam.model.Messaging;
import nam.model.Module;
import nam.model.ModuleLevel;
import nam.model.ModuleType;
import nam.model.Namespace;
import nam.model.Operation;
import nam.model.Output;
import nam.model.Parameter;
import nam.model.Persistence;
import nam.model.Placeholders;
import nam.model.Process;
import nam.model.Project;
import nam.model.Provider;
import nam.model.Repository;
import nam.model.Result;
import nam.model.Sender;
import nam.model.Service;
import nam.model.Source;
import nam.model.Type;
import nam.model.Unit;
import nam.model.statement.IfStatement;
import nam.model.statement.InvokeCommand;
import nam.model.statement.SendCommand;
import nam.model.util.ApplicationUtil;
import nam.model.util.DependencyUtil;
import nam.model.util.ElementUtil;
import nam.model.util.InformationUtil;
import nam.model.util.MessagingUtil;
import nam.model.util.ModuleUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.PersistenceUtil;
import nam.model.util.PlaceholderUtil;
import nam.model.util.ProjectUtil;
import nam.model.util.ServiceUtil;
import nam.model.util.TypeUtil;
import nam.ui.View;

import org.apache.commons.lang.StringUtils;
import org.aries.Assert;
import org.aries.util.FileUtil;
import org.aries.util.NameUtil;
import org.eclipse.bpel.model.PartnerLink;
import org.eclipse.bpel.model.PartnerLinks;
import org.eclipse.bpel.model.Receive;
import org.eclipse.bpel.model.partnerlinktype.PartnerLinkType;
import org.eclipse.bpel.model.partnerlinktype.Role;
import org.eclipse.emf.codegen.util.CodeGenUtil;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.wst.wsdl.PortType;

import aries.bpel.BPELUtil;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelField;
import aries.generation.model.ModuleTemplate;

/**
 * Represents a snapshot of the overall {@link GenerationContext} at any given
 * point in time.
 * 
 * @author tfisher
 */
public class GenerationContext {

	//public static GenerationContext INSTANCE;

	public AbstractGenerationEngine generatorEngine;
	
	private String modelLocation;

	private String propertyLocation;

	private String workspaceLocation;

	private String runtimeLocation;

	private String cacheLocation;

	private Properties properties;

	private String templateName;

	private String projectName;

	private String projectPrefix;

	private String projectDomain;

	private String projectGroupId;

	private String projectVersion;

	private String targetHome;

	private String targetWorkspace;

	private String templateWorkspace;

	private String templateHome;

	private ModuleTemplate moduleTemplate;

	private Placeholders placeholders;
	
	private Map<String, Project> projects;

	private Project project;

	private Application application;

	private Module module;

	private Process process;

	private Service service;

	private Invocation action;

	private Operation operation;

	// private List<Namespace> namespaces;

	private Messaging messaging;

	private String messagType;

	private Element messageElement;

	private Persistence persistence;

	private Cache cache;

	private Unit unit;

	private Element element;

	private Map<String, Element> elementByTypeMap;

	private Map<String, Element> elementByNameMap;

	private Map<String, Enumeration> enumerationByTypeMap;

	private Map<String, Enumeration> enumerationByNameMap;

	private Map<String, String> namespaceUriByPrefixMap;

	private Map<String, Namespace> namespaceByPrefixMap;

	private Map<String, Namespace> namespaceByUriMap;

	private Map<String, Namespace> namespaceByNameMap;

	private Component component;

	private Map<String, Component> componentByTypeMap;

	private Map<String, Component> componentByNameMap;

	private Map<ModuleType, ModuleTemplate> templates;

	private Map<String, EClass> elementByQNameCache;

	private Map<String, EClass> elementByJavaTypeCache;

	private Map<String, EDataType> datatypeCache;

	private Map<String, EEnum> enumerationCache;

	private Map<String, EAnnotation> annotationCache;

	private Map<ModuleType, Module> moduleByTypeMap;

	//private Map<String, Module> moduleByNamespaceMap;

	private Map<String, Module> moduleByNameCache;

	private Map<String, Service> serviceByNameCache;

	private Map<String, Service> serviceByPortTypeCache;

	private Map<String, Process> processByServiceCache;

	private Map<String, Process> processByNameCache;
	
	private Map<String, List<Operation>> referencedOperationMap;
	
	private Map<String, Element> parentElementMap;
	
	private String modelFileContext;

	private String runtimeFileContext;


	/*
	 * Options:
	 */

	private String author;
	
	private Map<String, List<String>> subsets;

	private boolean optionGenerateTests;

	private boolean optionLimitToSingleModule;

	private boolean optionEnabledEJB;

	
	public GenerationContext() {
		properties = new Properties();
		projects = new HashMap<String, Project>();
		datatypeCache = new HashMap<String, EDataType>();
		enumerationCache = new HashMap<String, EEnum>();
		annotationCache = new HashMap<String, EAnnotation>();
		elementByTypeMap = new HashMap<String, Element>();
		elementByNameMap = new HashMap<String, Element>();
		elementByQNameCache = new HashMap<String, EClass>();
		elementByJavaTypeCache = new HashMap<String, EClass>();
		enumerationByTypeMap = new HashMap<String, Enumeration>();
		enumerationByNameMap = new HashMap<String, Enumeration>();
		namespaceUriByPrefixMap = new HashMap<String, String>();
		namespaceByPrefixMap = new HashMap<String, Namespace>();
		namespaceByUriMap = new HashMap<String, Namespace>();
		namespaceByNameMap = new HashMap<String, Namespace>();
		processByNameCache = new HashMap<String, Process>();
		processByServiceCache = new HashMap<String, Process>();
		moduleByTypeMap = new HashMap<ModuleType, Module>();
		//moduleByNamespaceMap = new HashMap<String, Module>();
		moduleByNameCache = new HashMap<String, Module>();
		serviceByNameCache = new HashMap<String, Service>();
		serviceByPortTypeCache = new HashMap<String, Service>();
		referencedOperationMap = new HashMap<String, List<Operation>>();
		
		//default options
		author = "%userId%";
		author = "generated";
		optionGenerateTests = true;
		optionLimitToSingleModule = false;
		optionEnabledEJB = true;

		//default subsets
		subsets = new HashMap<String, List<String>>();
		subsets.put("all", null);
		
		//clearElementCache();
		//clearServiceCache();
		//clearProcessCache();
		//INSTANCE = this;
	}

	// public GenerationContext(String templateName, String projectName) {
	// this(templateName, projectName, projectName+".org");
	// }
	//
	// public GenerationContext(String templateName, String projectName, String
	// projectDomain) {
	// this.templateName = templateName.toLowerCase();
	// this.projectName = projectName.toLowerCase();
	// this.templates = createModuleTemplates();
	// INSTANCE = this;
	// }

	public AbstractGenerationEngine getEngine() {
		return generatorEngine;
	}
	
	public void setEngine(AbstractGenerationEngine generatorEngine) {
		this.generatorEngine = generatorEngine;
	}

	
	/*
	 * File location related
	 */

	public String getModelLocation() {
		return modelLocation;
	}

	public void setModelLocation(String modelLocation) {
		this.modelLocation = FileUtil.normalizePath(modelLocation);
	}

	public String getPropertyLocation() {
		return propertyLocation;
	}

	public void setPropertyLocation(String propertyLocation) {
		this.propertyLocation = FileUtil.normalizePath(propertyLocation);
	}
	
	public String getWorkspaceLocation() {
		return workspaceLocation;
	}

	public void setWorkspaceLocation(String workspaceLocation) {
		this.workspaceLocation = FileUtil.normalizePath(workspaceLocation);
	}

	public String getRuntimeLocation() {
		return runtimeLocation;
	}

	public void setRuntimeLocation(String runtimeLocation) {
		this.runtimeLocation = FileUtil.normalizePath(runtimeLocation);
	}

	public String getCacheLocation() {
		return cacheLocation;
	}

	public void setCacheLocation(String cacheLocation) {
		this.cacheLocation = FileUtil.normalizePath(cacheLocation);
	}
	
	public String getResourcesFolder(boolean isTest) {
		if (isTest)
			return getTestResourcesFolder();
		return getMainResourcesFolder();
	}
	
	public String getMainSourcesFolder() {
		return "src/main/java";
	}
	
	public String getMainResourcesFolder() {
		return "src/main/resources";
	}
	
	public String getTestSourcesFolder() {
		return "src/test/java";
	}
	
	public String getTestResourcesFolder() {
		return "src/test/resources";
	}
	
	public String getMainApplicationFolder() {
		return "src/main/application";
	}

	public String getMainWebappFolder() {
		return "src/main/webapp";
	}


	public String getRuntimeFileContext() {
		return runtimeFileContext;
	}
	
	public void setRuntimeFileContext(String fileContext) {
		this.runtimeFileContext = fileContext;
	}
	
	public String getRuntimeFilePath(String fileName) throws FileNotFoundException {
		return getRuntimeFilePath(null, fileName);
	}
	
	public String getRuntimeFilePath(String fileContext, String fileName) throws FileNotFoundException {
		return getFilePath(getRuntimeLocation(), fileContext, fileName);
	}

	public String getModelFileContext() {
		return modelFileContext;
	}
	
	public void setModelFileContext(String fileContext) {
		this.modelFileContext = fileContext;
	}
	
	public String getModelFilePath(String fileName) throws FileNotFoundException {
		return getModelFilePath(null, fileName);
	}
	
	public String getModelFilePath(String fileContext, String fileName) throws FileNotFoundException {
		return getFilePath(getModelLocation(), fileContext, fileName);
	}
	
	public String getCacheFilePath(String fileName) throws FileNotFoundException {
		if (!fileName.startsWith("/model"))
			fileName = "/model/" + fileName;
		return getFilePath(getCacheLocation(), null, fileName);
	}
	
	public String getCacheFilePath(String fileContext, String fileName) throws FileNotFoundException {
		return getFilePath(getCacheLocation(), fileContext, fileName);
	}
	
	public String getFilePath(String fileLocation, String fileContext, String fileName) throws FileNotFoundException {
		return getFilePath(fileLocation, fileContext, fileName, true);
	}
	
	//TODO make logic a little cleaner - but be careful to not change it!
	public String getFilePath(String fileLocation, String fileContext, String fileName, boolean assertExists) throws FileNotFoundException {
		String fileSeparator = System.getProperty("file.separator");
		String filePath = null;

//		if (fileName.contains("common"))
//			System.out.println();
		
//		if (!fileName.startsWith("/model")) {
//			if (!fileLocation.endsWith("/model"))
//				fileLocation += "/model";
//			//if (fileContext != null && !fileContext.endsWith("/model"))
//			//	fileContext += "/model";
//		}
		
		if (fileName.startsWith(fileSeparator)) {
			filePath = fileLocation + fileName;
			
		} else if (fileName.startsWith("/")) {
			filePath = fileLocation + fileName;
			
		} else if (fileContext != null) {
			filePath = fileContext + "/" + fileName;
			
		} else {
			filePath = fileLocation + "/" + fileName;
		}
		
		filePath = FileUtil.normalizePath(filePath);
		if (assertExists && !FileUtil.fileExists(filePath))
			throw new FileNotFoundException(filePath);
		return filePath;
	}
	
	/*
	 * Property related
	 */
	
	public String getProperty(String name) {
		return properties.getProperty(name);
	}

	public Boolean getPropertyAsBoolean(String name) {
		String property = properties.getProperty(name);
		if (property != null && property.equalsIgnoreCase("true"))
			return true;
		return false;
	}
	
	public Boolean isEnabled(String name) {
		String property = getProperty(name);
		if (property != null && property.equalsIgnoreCase("true"))
			return true;
		return false;
	}

	public void setProperty(String name) {
		setProperty(name, true);
	}

	public void unsetProperty(String name) {
		setProperty(name, false);
	}

	public void setProperty(String name, Object value) {
		properties.put(name, value != null ? value.toString() : null);
	}

	public void setProperty(String name, Boolean value) {
		properties.put(name, value != null ? Boolean.toString(value) : null);
	}

	public void setProperty(String name, Integer value) {
		properties.put(name, value != null ? Integer.toString(value) : null);
	}
	
	
	/*
	 * Defaults related
	 */

	protected String getDefaultProjectDomain() {
		return getDefaultProjectDomain(getProjectName());
	}

	public String getDefaultProjectDomain(String projectName) {
		return projectName+".org";
	}

	protected String getDefaultProjectGroupId() {
		return getDefaultProjectGroupId(getProjectName());
	}

	public String getDefaultProjectGroupId(String projectName) {
		return "org."+projectName;
	}

	public void setDefaultProjectGroupId() {
		setProjectGroupId(getDefaultProjectGroupId());
	}

	protected String getDefaultProjectVersion() {
		return "0.0.1-SNAPSHOT";
	}

	public void setDefaultProjectVersion() {
		setProjectVersion(getDefaultProjectVersion());		
	}
	

	public void initializeContext(Project project) {
		//setProjectName(project.getName());
		setProject(project);
		
    	//if (StringUtils.isEmpty(getTargetPath()))
		//	setTargetWorkspace("..");
    	//if (StringUtils.isEmpty(getTargetHome()))
    	//	setTargetHome("..");

    	if (StringUtils.isEmpty(getTemplateWorkspace()))
			setTemplateWorkspace("..");
    	if (StringUtils.isEmpty(getTemplateName()))
			setTemplateName(getTemplateName());
    	//if (StringUtils.isEmpty(getTemplateHome()))
    	//	setTemplateHome("template1");

    	if (StringUtils.isEmpty(getProjectName()))
			setProjectName(project.getName());
    	if (StringUtils.isEmpty(getProjectPrefix()))
			setProjectPrefix(project.getName());
    	if (StringUtils.isEmpty(getProjectDomain()))
			setProjectDomain(project.getName());
    	if (StringUtils.isEmpty(getProjectGroupId()))
			setProjectGroupId(getDefaultProjectGroupId(project.getName()));
    	if (StringUtils.isEmpty(getProjectVersion()))
			setProjectVersion(getDefaultProjectVersion());
	}
	
	public void initialize(Project project, Module module) {
		setProject(project);
		//setApplication(ProjectUtil.getApplication(project));
		initializeContext(module);
		//populateModules(project);
	}

	public void initializeContext(Module module) {
		this.module = module;
		initializeContext(module.getType(), module.getLevel());
	}

	public void initializeContext(ModuleType moduleType, ModuleLevel moduleLevel) {
		setTemplate(templates.get(moduleType));
		setTargetHome(initializeTargetHome(moduleType, moduleLevel));
	}

	protected String initializeTargetHome(ModuleType moduleType, ModuleLevel moduleLevel) {
//		if (moduleLevel == ModuleLevel.GROUP_LEVEL)
//			System.out.println();
		if (moduleType == ModuleType.POM && moduleLevel == ModuleLevel.PROJECT_LEVEL)
			return module.getArtifactId();
		if (moduleLevel == ModuleLevel.PROJECT_LEVEL)
			return module.getArtifactId();
		if (isOptionLimitToSingleModule() && !StringUtils.isEmpty(targetHome))
			return targetHome;
		if (isOptionLimitToSingleModule() && StringUtils.isEmpty(targetHome))
			return application.getGroupId()+"-"+application.getArtifactId();
		//return module.getGroupId()+"-"+module.getArtifactId();
		return module.getArtifactId();
	}


	/*
	 * Generation subsets
	 */
	
	public Map<String, Object> getSubsets() {
		return new HashMap<String, Object>(subsets);
	}

	public void clearSubsets() {
		synchronized (subsets) {
			subsets.clear();
		}
	}

	public void addSubset(String key) {
		addSubset(key, null);
	}
	
	public void addSubset(String key, String value) {
		synchronized (subsets) {
			if (key != null)
				key = key.toLowerCase();
			if (subsets.containsKey("all"))
				subsets.remove("all");
			List<String> list = null;
			if (!subsets.containsKey(key)) {
				list = new ArrayList<String>();
				subsets.put(key,  list);
			} else {
				list = subsets.get(key);
				if (list == null) {
					list = new ArrayList<String>();
					subsets.put(key,  list);
				}
			}
			if (value != null)
				list.add(value);
		}
	}
	
	public boolean canOverwriteAll() {
		return isEnabled("overwriteAll");
	}
	
	public boolean canGenerateProject() {
		return canGenerateSets("project") && !isOptionLimitToSingleModule();
	}

	public boolean canGenerateProject(String name) {
		return canGenerateSets("project", name) && !isOptionLimitToSingleModule();
	}

	public boolean canGenerateApplication(String name) {
		String key = "application";
		if (subsets.containsKey(key))
			return canGenerateSets(key, name);
		return false;
	}

	public boolean canGeneratePom() {
		return canGenerateSets("pom");
	}

	public boolean canGenerateModel() {
		return canGenerateSets("model");
	}

	public boolean canGenerateClient() {
		return canGenerateSets("client");
	}

	public boolean canGenerateService() {
		return canGenerateSets("service");
	}

	public boolean canGenerateData() {
		return canGenerateSets("data");
	}

	public boolean canGenerateWAR() {
		return canGenerateSets("war") || canGenerateSets("ui") || canGenerateSets("view");
	}

	public boolean canGenerateEAR() {
		return canGenerateSets("ear");
	}

	public boolean canGenerateXSD() {
		return canGenerateSets("model", "xsd");
	}

	public boolean canGenerateWSDL() {
		return canGenerateSets("model", "wsdl");
	}

	public boolean canGenerateBPEL() {
		return canGenerateSets("service", "bpel");
	}

	public boolean canGenerateSources() {
		return canGenerateSets("sources");
	}

	public boolean canGenerateTests() {
		return canGenerateSets("tests");
	}

	public boolean canGenerate(ModuleType moduleType) {
		switch (moduleType) {
		case MODEL: return canGenerateModel();
		case CLIENT: return canGenerateClient();
		case SERVICE: return canGenerateService();
		case DATA: return canGenerateData();
		case VIEW: return canGenerateWAR();
		case EAR: return canGenerateEAR();
		case POM: return canGeneratePom();
		default: return false;
		}
	}

	public boolean canGenerate(String ... values) {
		if (subsets.containsKey("all"))
			return true;
		for (int i = 0; i < values.length; i++) {
			String value = values[i];
			if (!canGenerateSets(value)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean canGenerateSets(String key, String ... values) {
		if (subsets.containsKey("all"))
			return true;
		if (subsets.containsKey(key)) {
			if (values.length == 0)
				return true;
			List<String> list = subsets.get(key);
			if (list.size() == 0)
				return true;
			for (int i = 0; i < values.length; i++) {
				String value = values[i];
				if (list.contains(value)) {
					return true;
				}
			}
		}
		return false;
	}


	/*
	 * Generation options
	 */

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public boolean isOptionGenerateTests() {
		return optionGenerateTests;
	}

	public void setOptionGenerateTests(boolean optionGenerateTests) {
		this.optionGenerateTests = optionGenerateTests;
	}

	public boolean isOptionLimitToSingleModule() {
		return optionLimitToSingleModule;
	}

	public void setOptionLimitToSingleModule(boolean optionLimitToSingleModule) {
		this.optionLimitToSingleModule = optionLimitToSingleModule;
	}

	public boolean isOptionEnabledEJB() {
		return optionEnabledEJB;
	}

	public void setOptionEnabledEJB(boolean optionEnabledEJB) {
		this.optionEnabledEJB = optionEnabledEJB;
	}

	public String getProjectName() {
		return projectName;
	}

	public String getProjectNameCamelCased() {
		String projectName = getProjectName();
		projectName = NameUtil.toCamelCase(projectName);
		projectName = NameUtil.uncapName(projectName);
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		if (projectName != null) {
			projectName = projectName.toLowerCase();
			//projectName = projectName.replaceAll("-", "_");
			projectName = projectName.replaceAll("[0..9]", "");
		}
		this.projectName = projectName;
	}

	public String getProjectNameCapped() {
		if (projectName != null) {
			String projectNameCapped = projectName.replaceAll("-", "_");
			projectNameCapped = projectNameCapped.replaceAll("[0..9]", "");
			projectNameCapped = NameUtil.capName(projectNameCapped);
			return projectNameCapped;
		}
		return null;
	}

	public String getProjectNameUpper() {
		if (projectName != null)
			return getProjectNameCapped().toUpperCase();
		return null;
	}

	public String getProjectNameUnderscored() {
		if (projectName != null)
			return NameUtil.splitStringUsingUnderscores(projectName);
		return null;
	}

	public String getProjectPrefix() {
		return projectPrefix;
	}

	public void setProjectPrefix(String projectPrefix) {
		if (projectPrefix != null)
			projectPrefix = projectPrefix.toLowerCase();
		this.projectPrefix = projectPrefix;
	}

	public String getProjectDomain() {
		return projectDomain;
	}

	public void setProjectDomain(String projectDomain) {
		if (projectDomain != null) {
			projectDomain = projectDomain.toLowerCase();
			projectDomain = projectDomain.replaceAll("-", "_");
			projectDomain = projectDomain.replaceAll("[0-9]", "");
		}
		this.projectDomain = projectDomain;
	}

	public String getProjectGroupId() {
		return projectGroupId;
	}

	public void setProjectGroupId(String projectGroupId) {
		if (projectGroupId != null)
			projectGroupId = projectGroupId.toLowerCase();
		this.projectGroupId = projectGroupId;
	}

	public String getProjectVersion() {
		return projectVersion;
	}

	public void setProjectVersion(String projectVersion) {
		this.projectVersion = projectVersion;
	}

	public Project getProject() {
		return project;
	}

	public Map<String, Project> getProjects() {
		return projects;
	}
	
	public Project getProjectByName(String projectName) {
		return projects.get(projectName);
	}
	
	public List<Project> getProjectList() {
		return new ArrayList<Project>(projects.values());
	}

	public void addProject(Project project) {
		projects.put(project.getName(), project);
	}
	
	public void addProjects(List<Project> projects) {
		Iterator<Project> iterator = projects.iterator();
		while (iterator.hasNext()) {
			Project project = iterator.next();
			addProject(project);
		}
	}
	
	
//	public void setProjects(List<Project> projects) {
//		this.projects = projects;
//	}
	
	public void setProject(Project project) {
		this.project = project;
		addProject(project);
		//setProjectName(project.getName());
		//setProjectDomain(project.getName());
		//setProjectVersion(project.getVersion());
		if (project.getSingleProject() != null && project.getSingleProject()) {
			setOptionLimitToSingleModule(true);
		}
	}

	public Placeholders getPlaceholders() {
		return placeholders;
	}
	
	public void setPlaceholders(Placeholders placeholders) {
		this.placeholders = placeholders;
	}

	public boolean containsPlaceholder(Serializable placeholder) {
		return PlaceholderUtil.containsPlaceholder(placeholders, placeholder);
	}
	
	public void addPlaceholder(Serializable placeholder) {
		PlaceholderUtil.addPlaceholder(placeholders, placeholder);
	}
	
	public Persistence getPersistencePlaceholderByName(String name) {
		List<Persistence> list = PlaceholderUtil.getPersistencePlaceholders(placeholders);
		Iterator<Persistence> iterator = list.iterator();
		while (iterator.hasNext()) {
			Persistence persistence = iterator.next();
			if (persistence.getName().equals(name))
				return persistence;
		}
		return null;
	}

	public Persistence getPersistencePlaceholderByNamespace(String namespace) {
		List<Persistence> list = PlaceholderUtil.getPersistencePlaceholders(placeholders);
		Iterator<Persistence> iterator = list.iterator();
		while (iterator.hasNext()) {
			Persistence persistence = iterator.next();
			String persistenceNamespace = persistence.getNamespace();
			if (persistenceNamespace == null)
				continue;
			if (persistenceNamespace.equals(namespace))
				return persistence;
		}
		return null;
	}

	public Source getImportedSource(String sourceName) {
		return PlaceholderUtil.getImportedSource(placeholders, sourceName);
	}
	
	public Adapter getImportedAdapter(String adapterName) {
		return PlaceholderUtil.getImportedAdapter(placeholders, adapterName);
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public Collection<Application> getApplications(Project project) {
		Applications applications = project.getApplications();
		return ApplicationUtil.getApplications(applications);
	}
	
	/*
	 * TODO put this method somewhere else?
	 * TODO try to eliminate the need for this method - 
	 * i.e. somehow make project modules associate with an application
	 */
	public Application getApplication(Project project) {
		Application application = getApplication();
		if (application != null)
			return application;
		Collection<Application> applications = ProjectUtil.getApplications(project);
		if (applications.size() == 1)
			application = applications.iterator().next();
		return application;
	}

	public Application getApplicationByName(String applicationName) {
		if (applicationName == null)
			return null;
		List<Project> projectList = getProjectList();
		Iterator<Project> iterator = projectList.iterator();
		while (iterator.hasNext()) {
			Project project = iterator.next();
			Application application = getApplicationByName(project, applicationName);
			if (application != null)
				return application;
		}
		Group group = getGroupByName(applicationName);
		if (group != null) {
			List<Application> applications = group.getApplications();
			if (!applications.isEmpty()) {
				//just return the first one for now
				Application application = applications.get(0);
				return application;
			}
		}
		return null;
	}
	
	public Application getApplicationByName(Project project, String applicationName) {
		Collection<Application> applications = getApplications(project);
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			if (application.getName().equalsIgnoreCase(applicationName))
				return application;
		}
		return null;
	}
	
	public Group getGroupByName(String groupName) {
		List<Project> projectList = getProjectList();
		Iterator<Project> iterator = projectList.iterator();
		while (iterator.hasNext()) {
			Project project = iterator.next();
			Group group = getGroupByName(project, groupName);
			if (group != null)
				return group;
		}
		return null;
	}
	
	public Group getGroupByName(Project project, String groupName) {
		List<Group> groups = ProjectUtil.getGroups(project);
		Iterator<Group> iterator = groups.iterator();
		while (iterator.hasNext()) {
			Group group = iterator.next();
			if (group.getName().equalsIgnoreCase(groupName))
				return group;
		}
		return null;
	}
	
	// public List<Namespace> getNamespaces() {
	// return namespaces;
	// }
	//
	// public void setNamespaces(List<Namespace> namespaces) {
	// this.namespaces = namespaces;
	// }

	
	public Messaging getMessaging() {
		return messaging;
	}

	public void setMessaging(Messaging messaging) {
		this.messaging = messaging;
	}
	
	public String getMessageType() {
		return messagType;
	}

	public void setMessageType(String messageType) {
		this.messagType = messagType;
	}

	public Element getMessageElement() {
		return messageElement;
	}

	public void setMessagElement(Element messageElement) {
		this.messageElement = messageElement;
	}

	
	/*
	 * Target related
	 */

	public String getTargetWorkspace() {
		return targetWorkspace;
	}

	public void setTargetWorkspace(String targetWorkspace) {
		this.targetWorkspace = targetWorkspace;
	}

	public String getTargetHome() {
		return targetHome;
	}

	public void setTargetHome(String targetHome) {
		this.targetHome = targetHome;
	}

	public String getTargetPath() {
		return getTargetPath(null);
	}
	
	public String getTargetPath(String projectName) {
		String targetPath = "";
		if (!StringUtils.isEmpty(targetWorkspace))
			targetPath += targetWorkspace + "/";
		if (!StringUtils.isEmpty(projectName)) {
			if (!projectName.equals(targetHome))
				targetPath += projectName + "/";
				//targetPath += projectName + "/project/";
			else targetPath += projectName + "/";
		}
		if (!StringUtils.isEmpty(targetHome) && (projectName == null || !projectName.equals(targetHome)))
			targetPath += targetHome;
		if (StringUtils.isEmpty(targetPath))
			throw new RuntimeException("TargetPath not configured");
		targetPath = NameUtil.normalizePath(targetPath);
		return targetPath;
	}

	
	/*
	 * Template related
	 */

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
		this.templates = createModuleTemplates();
	}

	public String getTemplateWorkspace() {
		return templateWorkspace;
	}

	public void setTemplateWorkspace(String templateWorkspace) {
		this.templateWorkspace = FileUtil.normalizePath(templateWorkspace);
	}

	public String getTemplateHome() {
		return templateHome;
	}

	public void setTemplateHome(String templateHome) {
		this.templateHome = templateHome;
	}

	public ModuleTemplate getTemplate() {
		return moduleTemplate;
	}

	public void setTemplate(ModuleTemplate moduleTemplate) {
		this.moduleTemplate = moduleTemplate;
		this.templateHome = moduleTemplate.getTemplateHome();
	}

	public Map<ModuleType, ModuleTemplate> getTemplates() {
		return templates;
	}

	public ModuleTemplate getTemplate(ModuleType type) {
		return templates.get(type);
	}

	public void setTemplates(Map<ModuleType, ModuleTemplate> templates) {
		this.templates = templates;
	}

	protected Map<ModuleType, ModuleTemplate> createModuleTemplates() {
		return createModuleTemplates(getTemplateName());
	}

	protected Map<ModuleType, ModuleTemplate> createModuleTemplates(String templateKey) {
		Map<ModuleType, ModuleTemplate> templates = new HashMap<ModuleType, ModuleTemplate>();
		createTemplate(templates, ModuleType.POM, templateKey);
		createTemplate(templates, ModuleType.MODEL, templateKey + "-model");
		createTemplate(templates, ModuleType.DATA, templateKey + "-data");
		createTemplate(templates, ModuleType.CLIENT, templateKey + "-client");
		createTemplate(templates, ModuleType.SERVICE, templateKey + "-service");
		createTemplate(templates, ModuleType.TEST, templateKey + "-test");
		createTemplate(templates, ModuleType.VIEW, templateKey + "-war");
		createTemplate(templates, ModuleType.WAR, templateKey + "-war");
		createTemplate(templates, ModuleType.EAR, templateKey + "-ear");
		return templates;
	}

	protected void createTemplate(Map<ModuleType, ModuleTemplate> templates, ModuleType moduleType, String templateHome) {
		ModuleTemplate template = new ModuleTemplate();
		template.setModuleType(moduleType);
		if (moduleType == ModuleType.POM)
			template.setTemplateHome(FileUtil.normalizePath(templateName));
		else template.setTemplateHome(FileUtil.normalizePath(templateName + "/" + templateHome));
		//else template.setTemplateHome(FileUtil.normalizePath(templateName + "/project/" + templateHome));
		template.setDependencies(getModuleTemplateDependencies(moduleType, false));
		templates.put(moduleType, template);
	}

	public List<Dependency> getModuleTemplateDependencies(ModuleType moduleType, boolean isProduction) {
		List<Dependency> dependencies = new ArrayList<Dependency>();
		switch (moduleType) {
		case MODEL:
			dependencies.add(DependencyUtil.createDependency("org.aries.pom", "aries-platform-model-layer", "0.0.1-SNAPSHOT", "provided", "pom"));
			if (!isProduction)
				dependencies.add(DependencyUtil.createDependency("org.aries.pom", "aries-platform-model-layer-test", "0.0.1-SNAPSHOT", "test", "pom"));
			break;

		case DATA:
			dependencies.add(DependencyUtil.createDependency("org.aries.pom", "aries-platform-data-layer", "0.0.1-SNAPSHOT", "provided", "pom"));
			if (!isProduction)
				dependencies.add(DependencyUtil.createDependency("org.aries.pom", "aries-platform-data-layer-test", "0.0.1-SNAPSHOT", "test", "pom"));
			break;

		case CLIENT:
			dependencies.add(DependencyUtil.createDependency("org.aries.pom", "aries-platform-client-layer", "0.0.1-SNAPSHOT", "provided", "pom"));
			if (!isProduction) {
				dependencies.add(DependencyUtil.createDependency("org.aries.pom", "aries-platform-client-layer-test", "0.0.1-SNAPSHOT", "test", "pom"));
				//dependencies.add(DependencyUtil.createDependency("org.aries", "tx-manager-api", "0.0.1-SNAPSHOT", "provided", "pom"));
			}
			break;

		case SERVICE:
			dependencies.add(DependencyUtil.createDependency("org.aries.pom", "aries-platform-service-layer", "0.0.1-SNAPSHOT", "provided", "pom"));
			if (!isProduction) {
				dependencies.add(DependencyUtil.createDependency("org.aries.pom", "aries-platform-service-layer-test", "0.0.1-SNAPSHOT", "test", "pom"));
				//dependencies.add(DependencyUtil.createDependency("org.aries", "tx-manager-api", "0.0.1-SNAPSHOT", "provided", "pom"));
			}
			break;

		case VIEW:
			dependencies.add(DependencyUtil.createDependency("org.aries.pom", "aries-platform-view-layer", "0.0.1-SNAPSHOT", "provided", "pom"));
			dependencies.add(DependencyUtil.createDependency("org.aries.pom", "aries-platform-client-layer", "0.0.1-SNAPSHOT", "provided", "pom"));
			if (!isProduction) {
				//dependencies.add(DependencyUtil.createPomDependency("org.aries.pom", "aries-platform-view-layer-test", "0.0.1-SNAPSHOT", "test", "pom"));
				dependencies.add(DependencyUtil.createDependency("org.aries.pom", "aries-platform-client-layer-test", "0.0.1-SNAPSHOT", "test", "pom"));
				//dependencies.add(DependencyUtil.createDependency("org.aries", "tx-manager-api", "0.0.1-SNAPSHOT", "provided", "pom"));
			}
			break;
		}
		return dependencies;
	}


	/*
	 * Namespace Helper methods 
	 * ------------------------
	 */

	//just get first one
	public Namespace getNamespace() {
		if (namespaceByNameMap.size() > 0)
			return namespaceByNameMap.values().iterator().next();
		return null;
	}

	public List<Namespace> getNamespaces() {
		return new ArrayList<Namespace>(namespaceByPrefixMap.values());
	}

	public Map<String, Namespace> getNamespacesByPrefix() {
		return namespaceByPrefixMap;
	}

	public Namespace getNamespaceByPrefix(String prefix) {
		Namespace targetNamespace = namespaceByPrefixMap.get(prefix);
		if (targetNamespace == null) {
			List<Namespace> namespaces = ProjectUtil.getNamespaces(project);
			Iterator<Namespace> iterator = namespaces.iterator();
			while (iterator.hasNext()) {
				Namespace namespace = iterator.next();
				if (namespace.getPrefix().equals(prefix)) {
					//populateNamespace(namespace);
					targetNamespace = namespace;
					break;
				}
			}
		}
		return targetNamespace;
	}

	public Map<String, Namespace> getNamespacesByUri() {
		return namespaceByUriMap;
	}

	public Namespace getNamespaceByUri(String uri) {
		Namespace targetNamespace = namespaceByUriMap.get(uri);
//		if (targetNamespace == null) {
//			List<Namespace> namespaces = InformationUtil.getNamespaces(project.getInformation());
//			Iterator<Namespace> iterator = namespaces.iterator();
//			while (iterator.hasNext()) {
//				Namespace namespace = iterator.next();
//				if (namespace.getUri().equals(uri)) {
//					//populateNamespace(namespace);
//					targetNamespace = namespace;
//					break;
//				}
//			}
//		}
		return targetNamespace;
	}
	
	//TODO Totally assuming primitive XSD types are "all" prefixed with "xs"
	//TODO make XSD namespace an external constant
	public String getNamespaceURIByPrefix(String prefix) {
		String namespaceUri = namespaceUriByPrefixMap.get(prefix);
		if (namespaceUri != null)
			return namespaceUri;
		if (prefix.startsWith("xs"))
			return "http://www.w3.org/2001/XMLSchema";
		List<Namespace> namespaces = ProjectUtil.getNamespaces(project);
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			if (namespace.getPrefix().equals(prefix)) {
				return namespace.getUri();
			}
		}
		return null;
	}


	public Map<String, Namespace> getNamespacesByName() {
		return namespaceByNameMap;
	}

	public Namespace getNamespaceByName(String name) {
		Namespace targetNamespace = namespaceByNameMap.get(name);
		if (targetNamespace == null && project != null) {
			List<Namespace> namespaces = ProjectUtil.getNamespaces(project);
			Iterator<Namespace> iterator = namespaces.iterator();
			while (iterator.hasNext()) {
				Namespace namespace = iterator.next();
				if (namespace.getName().equals(name)) {
					//populateNamespace(namespace);
					targetNamespace = namespace;
					break;
				}
			}
		}
		return targetNamespace;
	}

	public Namespace getNamespaceByPackageName(String packageName) {
		List<Namespace> namespaces = getNamespaces();
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			String namespacePackageName = ProjectLevelHelper.getPackageName(namespace.getUri());
			if (namespacePackageName.equals(packageName)) {
				return namespace;
			}
		}
		return null;
	}

	
	public void populateNamespaces(Collection<Namespace> namespaces) {
		if (namespaces != null) {
			Iterator<Namespace> iterator = namespaces.iterator();
			while (iterator.hasNext()) {
				Namespace namespace = iterator.next();
				populateNamespace(namespace);
			}
		}
	}

	public void populateNamespace(Namespace namespace) {
//		if (namespace.getName().startsWith("book"))
//			System.out.println();
		String prefix = namespace.getPrefix();
		String uri = namespace.getUri();
		String name = namespace.getName();
		Assert.notNull(prefix, "Namespace prefix must be specified");
		Assert.notNull(uri, "Namespace URI must be specified");
		Assert.notNull(name, "Namespace name must be specified");
		// Assert.isFalse(namespaceByPrefixMap.containsKey(prefix),
		// "Namespace already exists, prefix="+prefix);
		// Assert.isFalse(namespaceByNameMap.containsKey(name),
		// "Namespace already exists, name="+name);
		if (!namespaceByUriMap.containsKey(uri)) {
//			if (name.contains("bookshop2-shipper"))
//				System.out.println();
			populateNamespaceUri(prefix, namespace.getUri());
			namespaceByPrefixMap.put(prefix, namespace);
			namespaceByUriMap.put(uri, namespace);
			namespaceByNameMap.put(name, namespace);
		}
	}

	public void populateNamespaceUri(String prefix, String namespaceUri) {
		namespaceUriByPrefixMap.put(prefix, namespaceUri);
	}
	
	
	/*
	 * Types Helper methods 
	 * ----------------------
	 */

	public void populateTypes(Namespace namespace) {
		List<Type> types = NamespaceUtil.getTypes(namespace);
		populateTypes(types);
	}
	
	public void populateTypes(List<Type> types) {
		Iterator<Type> iterator = types.iterator();
		while (iterator.hasNext()) {
			Type type = iterator.next();
			if (type instanceof Element)
				populateElement((Element) type);
			else if (type instanceof Enumeration)
				populateEnumeration((Enumeration) type);
			else throw new RuntimeException("Unknown type: "+type.getClass());
		}
	}
	

	/*
	 * Element context methods 
	 * -----------------------
	 */

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

	public Collection<Element> getAllElements() {
		return new ArrayList<Element>(elementByNameMap.values());
	}
	
	public Element getElementByType(String type) {
		return elementByTypeMap.get(type.toLowerCase());
	}

	public Element getElementByName(String name) {
		return elementByNameMap.get(name.toLowerCase());
	}

	public Element findElement(Input input) {
		return findElement(input.getType(), input.getName(), false);
	}

	public Element findElement(Input input, boolean required) {
		return findElement(input.getType(), input.getName(), required);
	}

	public Element findElement(Output output) {
		return findElement(output.getType(), output.getName(), false);
	}

	public Element findElement(Output output, boolean required) {
		return findElement(output.getType(), output.getName(), required);
	}

	public Element findElement(Parameter parameter) {
		return findElement(parameter.getType(), parameter.getName(), false);
	}

	public Element findElement(Result result) {
		return findElement(result.getType(), result.getName(), false);
	}

	public Element findElement(String type, String name, boolean required) {
		String baseType = NameUtil.capName(TypeUtil.getLocalPart(type));
		if (CodeGenUtil.isJavaDefaultType(baseType))
			return null;
		Element element = null;
		if (!StringUtils.isEmpty(name)) {
			element = getElementByName(name);
			if (element == null)
				element = getElementByType(type);
			if (required)
				Assert.notNull(element, "Element not found for name: " + name);
		} else {
			element = getElementByType(type);
			if (required)
				Assert.notNull(element, "Element not found for type: " + type);
		}
		return element;
	}

	public Element findElementByName(String name) {
		return findElementByName(name, true);
	}

	public Element findElementByName(String name, boolean required) {
		Assert.notEmpty(name, "Name must be specified");
		Element element = getElementByName(name);
		if (required)
			Assert.notNull(element, "Element not found for name: " + name);
		return element;
	}

	public Element findElementByType(String type) {
		return findElementByType(type, true);
	}

	public Element findElementByType(String type, boolean required) {
		Assert.notEmpty(type, "Type must be specified");
		Element element = getElementByType(type);
		if (required)
			Assert.notNull(element, "Element not found for type: " + type);
		return element;
	}

	public String findElementType(Input input) {
		String elementType = null;
		Element element = findElement(input);
		if (element != null)
			elementType = element.getType();
		if (elementType == null)
			elementType = input.getType();
		elementType = NameUtil.getClassName(elementType);
		return elementType;
	}

	public String findElementName(Input input) {
		String elementName = null;
		Element element = findElement(input);
		if (element != null)
			elementName = element.getName();
		if (elementName == null)
			elementName = input.getName();
		if (elementName == null)
			elementName = NameUtil.uncapName(input.getType());
		return elementName;
	}

	public String findElementType(Output output) {
		String elementType = null;
		Element element = findElement(output);
		if (element != null)
			elementType = element.getType();
		if (elementType == null)
			elementType = output.getType();
		elementType = NameUtil.getClassName(elementType);
		return elementType;
	}
	
	public Element getElement(Service service) {
		Element element = null;
		if (service.getElement() != null)
			element = getElementByType(service.getElement());
		return element;
	}
	
	
	
	/*
	 * Field Helper methods 
	 * --------------------
	 */

	public Field findFieldByName(Element element, String name) {
		List<Field> fields = ElementUtil.getFields(element);
		return findFieldByName(fields, name);
	}

	public Field findFieldByName(List<Field> fields, String name) {
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			if (field.getName().toLowerCase().equals(name))
				return field;
		}
		return null;
	}
	
	public Type findTypeByName(String parameterName) {
		if (parameterName != null) {
			Iterator<Namespace> iterator = getNamespaces().iterator();
			while (iterator.hasNext()) {
				Namespace namespace = iterator.next();
				Type type = findTypeByName(namespace, parameterName);
				//type = TypeUtil.getTypeFromNamespaceAndLocalPart(namespace.getUri(), parameterName);
				if (type != null)
					return type;
			}
		}
		return null;
	}

	public Type findTypeByName(Namespace namespace, String parameterName) {
		List<Type> types = NamespaceUtil.getTypes(namespace);
		Type type = findTypeByName(types, parameterName);
		if (type != null)
			return type;
		return null;
	}

	public Type findTypeByName(List<Type> types, String parameterName) {
		Iterator<Type> iterator = types.iterator();
		while (iterator.hasNext()) {
			Type type = iterator.next();
			if (type.getName().equalsIgnoreCase(parameterName))
				return type;
		}
		return null;
	}

	public Type findTypeByTypeName(String parameterType) {
		if (parameterType != null) {
			Iterator<Namespace> iterator = getNamespaces().iterator();
			while (iterator.hasNext()) {
				Namespace namespace = iterator.next();
				Type type = findTypeByTypeName(namespace, parameterType);
				//type = TypeUtil.getTypeFromNamespaceAndLocalPart(namespace.getUri(), parameterName);
				if (type != null)
					return type;
			}
		}
		return null;
	}

	public Type findTypeByTypeName(Namespace namespace, String parameterType) {
		List<Type> types = NamespaceUtil.getTypes(namespace);
		Type type = findTypeByTypeName(types, parameterType);
		if (type != null)
			return type;
		return null;
	}
	
	public Type findTypeByTypeName(List<Type> types, String parameterType) {
		Iterator<Type> iterator = types.iterator();
		while (iterator.hasNext()) {
			Type type = iterator.next();
			if (type.getType().equalsIgnoreCase(parameterType))
				return type;
		}
		return null;
	}
	
	// public void populateElements(Project model) {
	// elementByTypeMap = new HashMap<String, Element>();
	// elementByNameMap = new HashMap<String, Element>();
	// //populateElements(model.getElements().getElements());
	// populatePersistenceSection(model.getPersistence());
	// populateElements(ApplicationUtil.getElements());
	// }

	
	/*
	 * Information block related methods 
	 * ---------------------------------
	 */

	public Information findInformationBlockByName(String name) {
		Collection<Information> informationBlocks = ProjectUtil.getInformationBlocks(project);
		Iterator<Information> iterator = informationBlocks.iterator();
		while (iterator.hasNext()) {
			Information information = iterator.next();
			if (information.getRef() != null)
				continue;
			if (information.getName().equalsIgnoreCase(name))
				return information;			
		}
		return null;
	}
	
	// TODO UNUSED
	public void populateInformationSection(Information information) {
		Module module = ModuleUtil.getDataModule(ModuleUtil.getModules(application.getModules()));
		Collection<Namespace> namespaces = InformationUtil.getNamespaces(information);
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			Element element = new Element();
			element.setName(namespace.getName());
			String className = NameUtil.capName(namespace.getName());
			element.setType(module.getGroupId() + "." + className);
			populateElement(element);
		}
	}

	// TODO UNUSED
	public void populateInformationNamespace(Namespace namespace) {
		Collection<Type> types = NamespaceUtil.getTypes(namespace);
		Iterator<Type> iterator = types.iterator();
		while (iterator.hasNext()) {
			Type type = iterator.next();
			if (type instanceof Element)
				populateElement((Element) type);
			if (type instanceof Element)
				populateEnumeration((Enumeration) type);
		}
	}

	
	/*
	 * Persistence block related methods 
	 * ---------------------------------
	 */

	public Persistence getPersistence() {
		return persistence;
	}

	public void setPersistence(Persistence persistence) {
		this.persistence = persistence;
	}

	public Cache getCache() {
		return cache;
	}

	public void setCache(Cache cache) {
		this.cache = cache;
	}
	
	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Map<String, Element> getParentElementMap() {
		return parentElementMap;
	}
	
	public Element getParentElementByType(String type) {
//		if (parentElementMap == null)
//			System.out.println();
		return parentElementMap.get(type.toLowerCase());
	}

//	public void buildParentElementMap(Information information) throws Exception {
//		String domain = information.getDomain();
//		parentElementMap = new HashMap<String, Element>();
//		List<Unit> units = InformationUtil.getUnits(information);
//		Iterator<Unit> iterator = units.iterator();
//		while (iterator.hasNext()) {
//			Unit unit = iterator.next();
//			if (unit.getRef() != null)
//				continue;
//			buildParentElementMap(unit);
//		}
//	}

	public void buildParentElementMap(Persistence persistence) throws Exception {
		parentElementMap = new HashMap<String, Element>();
		Collection<Unit> units = PersistenceUtil.getUnits(persistence);
		Iterator<Unit> iterator = units.iterator();
		while (iterator.hasNext()) {
			Unit unit = iterator.next();
			if (unit.getRef() != null)
				continue;
			buildParentElementMap(unit);
		}
	}

	public void buildParentElementMap(Unit unit) throws Exception {
		List<Element> elements = ElementUtil.getElements(unit.getElements());
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			buildParentElementMap(unit.getNamespace(), element);
		}
	}

	public void buildParentElementMap(Namespace namespace, Element element) throws Exception {
		String classNameFromType = DataLayerHelper.getEntityClassName(element);
		String classNameFromName = NameUtil.capName(element.getName()) + "Entity";
		if (!classNameFromName.equals(classNameFromType)) {
			parentElementMap.put(element.getType(), element);
		}
	}
	
	// TODO UNUSED
	protected void populatePersistenceSections(List<Persistence> sections) {
		if (sections != null) {
			Iterator<Persistence> iterator = sections.iterator();
			while (iterator.hasNext()) {
				Persistence persistence = iterator.next();
				populatePersistenceSection(persistence);
			}
		}
	}

	// TODO UNUSED
	public void populatePersistenceSection(Persistence persistence) {
		Module module = ModuleUtil.getDataModule(ModuleUtil.getModules(application.getModules()));
		List<Repository> repositories = PersistenceUtil.getRepositories(persistence);
		Iterator<Repository> iterator = repositories.iterator();
		while (iterator.hasNext()) {
			Repository repository = iterator.next();
			Element element = new Element();
			element.setName(repository.getName());
			String className = NameUtil.capName(repository.getName());
			element.setType(module.getGroupId() + "." + className);
			populateElement(element);
		}
	}

	public void populateElements(List<Element> elements) {
		if (elements != null) {
			Iterator<Element> iterator = elements.iterator();
			while (iterator.hasNext()) {
				Element element = iterator.next();
				populateElement(element);
			}
		}
	}

	public void populateElement(Element element) {
		String type = element.getType();
		String name = element.getName();
		if (type == null) {
			Assert.notNull(name, "Element type or name must be specified");
//			if (element.getExtends() == null)
//				System.out.println();
			Assert.notNull(element.getExtends(), "Element type or extension must be specified");
			type = element.getExtends() + "." + NameUtil.capName(name);
			element.setType(type);
		}
		if (name == null) {
			Assert.notNull(type, "Element type or name must be specified");
			String className = NameUtil.getClassName(type);
			name = NameUtil.uncapName(className);
			element.setName(name);
		}
		
		//if ()
		String typeInLowerCase = type.toLowerCase();
		String nameInLowerCase = name.toLowerCase();
		
		// only populate typeMap for base types 
		if (typeInLowerCase.endsWith(nameInLowerCase))
			elementByTypeMap.put(typeInLowerCase, element);
		elementByNameMap.put(nameInLowerCase, element);
		//String javaName = TypeUtil.getJavaName(type);
		//elementByJavaTypeCache.put(javaName, element);
	}

	public void populateEnumeration(Enumeration enumeration) {
		String type = enumeration.getType();
		String name = enumeration.getName();
		if (type == null) {
			Assert.notNull(name, "Enumeration type or name must be specified");
			//Assert.notNull(enumeration.getExtends(), "Enumeration type or extension must be specified");
			//type = enumeration.getExtends() + "." + NameUtil.capName(name);
			type = NameUtil.capName(name);
			enumeration.setType(type);
		}
		if (name == null) {
			Assert.notNull(type, "Enumeration type or name must be specified");
			String className = NameUtil.getClassName(type);
			name = NameUtil.uncapName(className);
			enumeration.setName(name);
		}
		enumerationByTypeMap.put(type.toLowerCase(), enumeration);
		enumerationByNameMap.put(name.toLowerCase(), enumeration);
	}

	
	/*
	 * View block related methods 
	 * --------------------------
	 */

	public View findViewBlockByName(String name) {
		List<View> viewBlocks = ProjectUtil.getViewBlocks(project);
		Iterator<View> iterator = viewBlocks.iterator();
		while (iterator.hasNext()) {
			View view = iterator.next();
			if (view.getRef() != null)
				continue;
			if (view.getName().equalsIgnoreCase(name))
				return view;			
		}
		return null;
	}
	
	
	/*
	 * Component related
	 */

	public Component getComponent() {
		return component;
	}

	public Component getComponentByType(String type) {
		return componentByTypeMap.get(type);
	}

	public Component getComponentByName(String name) {
		return componentByNameMap.get(name);
	}

	public void setComponent(Component component) {
		this.component = component;
	}

	public void populateComponents(Project model) {
		componentByTypeMap = new HashMap<String, Component>();
		componentByNameMap = new HashMap<String, Component>();
		//populateComponents(model.getComponents().getComponents());
		//populatePersistenceSectionComponents(model.getPersistence());
		populateComponents(application.getComponents().getComponents());
	}

	protected void populatePersistenceSectionComponents(
			List<Persistence> sections) {
		if (sections != null) {
			Iterator<Persistence> iterator = sections.iterator();
			while (iterator.hasNext()) {
				Persistence persistence = iterator.next();
				populatePersistenceSectionComponents(persistence);
			}
		}
	}

	protected void populatePersistenceSectionComponents(Persistence persistence) {
		Module module = ModuleUtil.getDataModule(ModuleUtil.getModules(application.getModules()));
		List<Repository> repositories = PersistenceUtil.getRepositories(persistence);
		Iterator<Repository> iterator = repositories.iterator();
		while (iterator.hasNext()) {
			Repository repository = iterator.next();
			Component component = new Component();
			component.setName(repository.getName());
			String className = NameUtil.capName(repository.getName());
			component.setType(module.getGroupId() + "." + className);
			populateComponent(component);
		}
	}

	protected void populateComponents(List<Component> components) {
		if (components != null) {
			Iterator<Component> iterator = components.iterator();
			while (iterator.hasNext()) {
				Component component = iterator.next();
				populateComponent(component);
			}
		}
	}

	protected void populateComponent(Component component) {
		String type = component.getType();
		String name = component.getName();
		if (type == null) {
			Assert.notNull(name, "Component type or name must be specified");
			Assert.notNull(component.getBaseType(),
					"Component type or extension must be specified for: "
							+ name);
			type = component.getBaseType() + "." + NameUtil.capName(name);
			component.setType(type);
		}
		if (name == null) {
			Assert.notNull(type, "Component type or name must be specified");
			String className = NameUtil.getClassName(type);
			name = NameUtil.uncapName(className);
			component.setName(name);
		}
		componentByTypeMap.put(type, component);
		componentByNameMap.put(name, component);
	}

	/*
	 * Element cache related 
	 * ---------------------
	 */

	public void clearElementCache() {
		elementByQNameCache = new HashMap<String, EClass>();
		elementByJavaTypeCache = new HashMap<String, EClass>();
		datatypeCache = new HashMap<String, EDataType>();
		enumerationCache = new HashMap<String, EEnum>();
		annotationCache = new HashMap<String, EAnnotation>();
	}

	public boolean isElementCachedByJavaType(String fieldType) {
		return elementByJavaTypeCache.containsKey(fieldType) ||
			datatypeCache.containsKey(fieldType) ||
			enumerationCache.containsKey(fieldType) ||
			annotationCache.containsKey(fieldType);
	}

	public Map<String, EClass> getElementByQNameCache() {
		return elementByQNameCache;
	}

	public Map<String, EClass> getElementByJavaTypeCache() {
		return elementByJavaTypeCache;
	}

	public Map<String, EDataType> getDatatypeCache() {
		return datatypeCache;
	}

	public Map<String, EEnum> getEnumerationCache() {
		return enumerationCache;
	}

	public Enumeration getEnumerationByType(String type) {
		return enumerationByTypeMap.get(type.toLowerCase());
	}

	public Enumeration getEnumerationByName(String name) {
		return enumerationByNameMap.get(name.toLowerCase());
	}

	public Map<String, Enumeration> getEnumerationByTypeMap() {
		return enumerationByTypeMap;
	}

	public Map<String, Enumeration> getEnumerationByNameMap() {
		return enumerationByNameMap;
	}

	public Map<String, EAnnotation> getAnnotationCache() {
		return annotationCache;
	}

	
	/*
	 * Module context related 
	 * ----------------------
	 */

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public void clearModuleCaches() {
		moduleByTypeMap = new HashMap<ModuleType, Module>();
		moduleByNameCache = new HashMap<String, Module>();
		//moduleByNamespaceMap = new HashMap<String, Module>();
	}
	
	public Module getModule(ModuleType moduleType) {
		return moduleByTypeMap.get(moduleType);
	}

	public void setModule(ModuleType moduleType) {
		setModule(moduleByTypeMap.get(moduleType));
	}

	public Module getModelModuleByNamespace(Namespace namespace) {
		return getModuleByNamespace(namespace.getUri(), ModuleType.MODEL);
	}

	public Module getDataModuleByNamespace(Namespace namespace) {
		return getModuleByNamespace(namespace.getUri(), ModuleType.DATA);
	}

	public Module getModelModuleByNamespace(String namespace) {
		return getModuleByNamespace(namespace, ModuleType.MODEL);
	}

	public Module getDataModuleByNamespace(String namespace) {
		return getModuleByNamespace(namespace, ModuleType.DATA);
	}

	public Module getModuleByNamespace(String namespace, ModuleType moduleType) {
		List<Project> projectList = getProjectList();
		List<Module> modelModules = ModuleUtil.getAllModules(projectList, moduleType);
		Iterator<Module> iterator = modelModules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			if (module.getNamespace().equals(namespace)) {
				return module;
			}
		}
		return null;
	}

	public void addModuleByType(ModuleType moduleType, Module module) {
		moduleByTypeMap.put(moduleType, module);
	}

//	public void addModuleByNamespace(Namespace namespace, Module module) {
//		addModuleByNamespace(namespace.getUri(), module);
//	}

//	public void addModuleByNamespace(String namespace, Module module) {
//		moduleByNamespaceMap.put(namespace, module);
//	}

	public Module getImportedModuleByName(String moduleName) {
		Module module = moduleByNameCache.get(moduleName);
		return module;
	}

	public void addImportedModule(Module module) {
		Assert.notNull(module.getName(), "Module name must be specified");
		String moduleKey = module.getName();
		moduleByNameCache.put(moduleKey, module);
	}
	
	public Module getModuleByPortType(PortType portType) {
		Set<Module> serviceModules = ProjectUtil.getServiceModules(getProject());
		Iterator<Module> moduleIterator = serviceModules.iterator();
		while (moduleIterator.hasNext()) {
			Module module = moduleIterator.next();
			List<Service> services = ModuleUtil.getServices(module);
			Iterator<Service> iterator = services.iterator();
			while (iterator.hasNext()) {
				Service service = iterator.next();
				String portTypeName = portType.getQName().toString();
				if (service.getPortType().equals(portTypeName)) {
					return module;
				}
			}
		}
		return null;
	}

	public Module getModuleByArtifactId(String artifactId) {
		Set<Module> modules = ProjectUtil.getAllModules(getProject());
		Iterator<Module> moduleIterator = modules.iterator();
		while (moduleIterator.hasNext()) {
			Module module = moduleIterator.next();
			if (module.getArtifactId().equals(artifactId)) {
				return module;
			}
		}
		return null;
	}

	public Module getModuleById(String moduleId) {
		Set<Module> modules = ProjectUtil.getAllModules(getProject());
		Iterator<Module> moduleIterator = modules.iterator();
		while (moduleIterator.hasNext()) {
			Module module = moduleIterator.next();
			//String moduleId2 = ModuleUtil.getModuleId(module);
			String moduleId2 = module.getArtifactId();
			if (moduleId.equalsIgnoreCase(moduleId2)) {
				return module;
			}
		}
		return null;
	}
	

	/*
	 * Service context related 
	 * -----------------------
	 */
	
	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public Invocation getAction() {
		return action;
	}

	public void setAction(Invocation action) {
		this.action = action;
	}
	
	public void clearServiceCaches() {
		serviceByNameCache = new HashMap<String, Service>();
		serviceByPortTypeCache = new HashMap<String, Service>();
	}

	public Service getImportedServiceByQName(QName qName) {
		String serviceName = TypeUtil.getServiceNameFromText(qName.getLocalPart());
		//String serviceKey = qName.getNamespaceURI() + "/" + serviceName;
		Service service = serviceByNameCache.get(serviceName);
		return service;
	}

	public Service getImportedServiceByPortType(QName qName) {
		String namespace = qName.getNamespaceURI();
		String localPart = qName.getLocalPart();
		Service service = getImportedServiceByPortType(namespace, localPart);
		return service;
	}

	public Service getImportedServiceByPortType(String namespace, String portName) {
		String portTypeKey = "{" + namespace + "}" + portName;
		Service service = serviceByPortTypeCache.get(portTypeKey);
		return service;
	}

	public Service getImportedServiceByName(String serviceName) {
		Assert.notNull(serviceName, "Service name must be specified");
		//String serviceKey = serviceNamespace + "/" + serviceName;
		String serviceKey = serviceName;
		Service service = serviceByNameCache.get(serviceKey);
		return service;
	}

	public void addImportedService(Service service) {
		Assert.notNull(service.getName(), "Service name must be specified");
		//String serviceKey = service.getNamespace() + "/" + service.getName();
		String serviceKey = service.getName();
		String portTypeKey = service.getPortType();
		serviceByNameCache.put(serviceKey, service);
		if (portTypeKey != null)
			serviceByPortTypeCache.put(portTypeKey, service);
	}

	public String getPackageName(Service service) {
		String packageName = service.getPackageName();
		//if (isEnabled("generateServicePerOperation")) {
			Element element = getElement(service);
			if (element != null) {
				//Assert.notNull(element, "No element association found for service: "+service.getName());
				packageName += "." + NameUtil.uncapName(element.getName());
			}
		//}
		return packageName;
	}
	

	/*
	 * Process context related 
	 * -----------------------
	 */

	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public void clearProcessCache() {
		processByNameCache = new HashMap<String, Process>();
		processByServiceCache = new HashMap<String, Process>();
	}

	public List<Process> getProcesses() {
		return new ArrayList<Process>(processByNameCache.values());
	}

//	public Process getProcess() {
//		Service service = getService();
//		if (service != null)
//			return getProcessByService(service.getPortType());
//		return null;
//	}

	public Process getProcessByName(String processName) {
		return processByNameCache.get(processName);
	}

	public Process getProcessByService(String serviceName) {
		return processByServiceCache.get(serviceName);
	}

	public void addProcess(Process process) {
		Receive initialReceiveActivity = BPELUtil.getInitialReceiveActivity(BPELUtil.getBPELProcess(process));
		PortType portType = initialReceiveActivity.getPortType();
		//addProcess(portType.getQName().toString(), process);
		String serviceName = portType.getQName().toString();
		processByServiceCache.put(serviceName, process);
		processByNameCache.put(process.getName(), process);
	}

//	public void addProcess(String processKey, Process process) {
//	}

//	public void addProcessOLD(Process process) {
//		EList<PartnerLink> partnerLinks = process.getPartnerLinks().getChildren();
//		Iterator<PartnerLink> iterator = partnerLinks.iterator();
//		while (iterator.hasNext()) {
//			PartnerLink partnerLink = iterator.next();
//			System.out.println(">>>" + partnerLink.getMyRole().getName());
//			if (partnerLink.getMyRole() != null) {
//				PortType portType = getPortType(partnerLink, partnerLink.getMyRole().getName());
//				addProcess(portType.getQName().toString(), process);
//			}
//		}
//	}

	/*
	 * PortType/Service cache related ------------------------------
	 */

	public PortType getPortType(PartnerLink partnerLink, String roleName) {
		Role myRole = partnerLink.getMyRole();
		Role partnerRole = partnerLink.getPartnerRole();
		if (myRole != null && myRole.getName().equals(roleName)) {
			return (PortType) myRole.getPortType();
		} else if (partnerRole != null
				&& partnerRole.getName().equals(roleName)) {
			return (PortType) partnerRole.getPortType();
		}
		return null;
	}

	public PortType getPortType(PartnerLinkType partnerLinkType, String roleName) {
		List<Role> roles = partnerLinkType.getRole();
		Iterator<Role> iterator = roles.iterator();
		while (iterator.hasNext()) {
			Role role = iterator.next();
			if (role.getName().equals(roleName)) {
				Object portType = role.getPortType();
				return (PortType) portType;
			}
		}
		return null;
	}

	public Service getServiceByName(String applicationName, String serviceName) {
		Application application = getApplicationByName(applicationName);
		if (application != null) {
			Service service = ApplicationUtil.getServiceByName(application, serviceName);
			return service;
		}
		return null;
	}
	
	public Service getServiceByName(String serviceName) {
		List<Project> projectList = getProjectList();
		Service service = ProjectUtil.getServiceByName(projectList, serviceName);
		return service;
	}

	public Callback getIncomingCallbackByName(String serviceName) {
		List<Project> projectList = getProjectList();
		Callback callback = ProjectUtil.getIncomingCallbackByName(projectList, serviceName);
		return callback;
	}
	
	public Callback getOutgoingCallbackByName(String serviceName) {
		List<Project> projectList = getProjectList();
		Callback callback = ProjectUtil.getOutgoingCallbackByName(projectList, serviceName);
		return callback;
	}
	
	public Service getServiceByPortType(PortType portType) {
		Service service = getImportedServiceByPortType(portType.getQName());
		return service;
	}

	// NotUsed
	public Service getServiceByPortTypeOLD(PortType portType) {
		Set<Module> serviceModules = ProjectUtil.getServiceModules(getProject());
		List<Service> services = ModuleUtil.getServices(serviceModules);
		Iterator<Service> iterator = services.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			if (service.getPortType().equals(portType))
				return service;
		}
		return null;
	}


	/*
	 * PartnerLink cache related 
	 * -------------------------
	 */

	public Map<String, PartnerLink> getPartnerLinkMap() {
		Map<String, PartnerLink> map = getPartnerLinkMap(getProcesses());
		return map;
	}

	public Map<String, PartnerLink> getPartnerLinkMap(List<Process> processes) {
		Map<String, PartnerLink> map = new HashMap<String, PartnerLink>();
		Iterator<Process> iterator = processes.iterator();
		while (iterator.hasNext()) {
			Process process = (Process) iterator.next();
			map.putAll(getPartnerLinkMap(process));
		}
		return map;
	}

	public Map<String, PartnerLink> getPartnerLinkMap(Process process) {
		Map<String, PartnerLink> map = new HashMap<String, PartnerLink>();
		PartnerLinks partnerLinks = BPELUtil.getBPELProcess(process).getPartnerLinks();
		Iterator<PartnerLink> iterator = partnerLinks.getChildren().iterator();
		while (iterator.hasNext()) {
			PartnerLink partnerLink = iterator.next();
			// PartnerLinkType partnerLinkType =
			// partnerLink.getPartnerLinkType();
			map.put(partnerLink.getName(), partnerLink);
		}
		return map;
	}

	public Map<String, PartnerLinkType> getPartnerLinkTypeMap() {
		Map<String, PartnerLinkType> map = getPartnerLinkTypeMap(getProcesses());
		return map;
	}

	public Map<String, PartnerLinkType> getPartnerLinkTypeMap(
			List<Process> processes) {
		Map<String, PartnerLinkType> map = new HashMap<String, PartnerLinkType>();
		Iterator<Process> iterator = processes.iterator();
		while (iterator.hasNext()) {
			Process process = (Process) iterator.next();
			map.putAll(getPartnerLinkTypeMap(process));
		}
		return map;
	}

	public Map<String, PartnerLinkType> getPartnerLinkTypeMap(Process process) {
		Map<String, PartnerLinkType> map = new HashMap<String, PartnerLinkType>();
		PartnerLinks partnerLinks = BPELUtil.getBPELProcess(process).getPartnerLinks();
		Iterator<PartnerLink> iterator = partnerLinks.getChildren().iterator();
		while (iterator.hasNext()) {
			PartnerLink partnerLink = iterator.next();
			PartnerLinkType partnerLinkType = partnerLink.getPartnerLinkType();
			map.put(partnerLinkType.getName(), partnerLinkType);
		}
		return map;
	}

	
	public static boolean isKeyClass(ModelClass modelClass) {
		return modelClass.getClassName().endsWith("Key");
	}

	public static boolean isMessageClass(ModelClass modelClass) {
		return modelClass.getClassName().endsWith("Message");
	}

	public static boolean isEventClass(ModelClass modelClass) {
		return modelClass.getClassName().endsWith("Event");
	}

	public static boolean isExceptionClass(ModelClass modelClass) {
		return modelClass.getClassName().endsWith("Exception");
	}

	public static boolean isCriteriaClass(ModelClass modelClass) {
		return modelClass.getClassName().endsWith("Criteria");
	}
	
	public List<ModelField> getIdKey(ModelClass modelClass) {
		Set<ModelField> fields = new HashSet<ModelField>();
		fields.addAll(modelClass.getInstanceAttributes());
		fields.addAll(modelClass.getInstanceReferences());
		return getIdKey(fields);
	}

	public List<ModelField> getIdKey(Set<ModelField> fields) {
		Iterator<ModelField> iterator = fields.iterator();
		List<ModelField> elementKey = new ArrayList<ModelField>();
		while (iterator.hasNext()) {
			ModelField field = iterator.next();
			if (field.isKeyEnabled())
				elementKey.add(field);
		}
		return elementKey;
	}

	public List<ModelField> getEqualityFields(ModelClass modelClass) {
		Set<ModelField> fields = new LinkedHashSet<ModelField>();
		fields.addAll(modelClass.getInstanceAttributes());
		fields.addAll(modelClass.getInstanceReferences());
		List<ModelField> equalityFields = getKeyFields(fields);
		if (equalityFields.size() == 0 && isKeyClass(modelClass))
			equalityFields.addAll(fields);
		if (equalityFields.size() == 0)
			equalityFields = getUniqueFields(fields);
		return equalityFields; 
	}

	public List<ModelField> getKeyFields(Set<ModelField> fields) {
		Iterator<? extends ModelField> iterator = fields.iterator();
		List<ModelField> keyFields = new ArrayList<ModelField>();
		while (iterator.hasNext()) {
			ModelField field = iterator.next();
			if (field.getName().equals("id"))
				continue;
			if (field.isKeyEnabled() || field.isUnique())
				keyFields.add(field);
		}
		return keyFields;
	}

	public List<ModelField> getUniqueFields(Set<ModelField> fields) {
		Iterator<? extends ModelField> iterator = fields.iterator();
		List<ModelField> uniqueFields = new ArrayList<ModelField>();
		while (iterator.hasNext()) {
			ModelField field = iterator.next();
			if (field.isUnique())
				uniqueFields.add(field);
		}
		return uniqueFields;
	}
	
	/*
	 * Provider context related 
	 * ------------------------
	 */

	public Provider getProviderByName(String name) {
		Map<String, Provider> map = getProvidersByName();
		Provider provider = map.get(name);
		return provider;
	}

	public Map<String, Provider> getProvidersByName() {
		return getProvidersByName(getProject());
	}
	
	public Map<String, Provider> getProvidersByName(Project project) {
		Map<String, Provider> map = new HashMap<String, Provider>();
		List<Provider> providers = new ArrayList<Provider>();
		providers.addAll(MessagingUtil.getProviders(project));
		providers.addAll(PersistenceUtil.getProviders(project));
		Iterator<Provider> iterator = providers.iterator();
		while (iterator.hasNext()) {
			Provider provider = iterator.next();
			map.put(provider.getName(), provider);
		}
		return map;
	}


	/*
	 * Channel context related 
	 * -----------------------
	 */

	public Channel getChannelByName(String channelName) {
		return MessagingUtil.getChannelByName(project, channelName);
	}
	

	/*
	 * Cache context related 
	 * ---------------------
	 */

//	public Cache getCache(Service service) {
//		return getCache(service.getCache());
//	}
//
//	public Cache getCache(Process process) {
//		return getCache(process.getCache());
//	}

	public Cache getCache(Cache cache) {
		if (cache == null)
			return null;
		//if no ref, just return this instance 
		if (StringUtils.isEmpty(cache.getRef()))
			return cache;
		String name = cache.getRef();
		return getCacheByName(name);
	}
	
	public Cache getCacheByName(String cacheName) {
		Set<Module> modules = ModuleUtil.getModules(getApplication().getModules());
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			Cache cache = ModuleUtil.getCache(module, cacheName);
			if (cache != null)
				return cache;
		}
		return null;
	}

	public List<Operation> getReferencedOperations(String elementName) {
		synchronized (referencedOperationMap) {
			List<Operation> operations = referencedOperationMap.get(elementName);
			if (operations == null) {
				operations = new ArrayList<Operation>();
				referencedOperationMap.put(elementName, operations);
			}
			return operations;
		}
	}
	
	public void addReferencedMethod(String elementName, Operation operation) {
		synchronized (referencedOperationMap) {
			List<Operation> operations = getReferencedOperations(elementName);
			operations.add(operation);
		}
	}

	public Source findSourceByName(String name) {
		List<Persistence> persistenceBlocks = ProjectUtil.getPersistenceBlocks(project);
		Iterator<Persistence> iterator = persistenceBlocks.iterator();
		while (iterator.hasNext()) {
			Persistence persistence = iterator.next();
			Source source = PersistenceUtil.getSourceByName(persistence, name);
			if (source != null)
				return source;			
		}
		return null;
	}

	public Unit findUnitByName(String name) {
		List<Persistence> persistenceBlocks = ProjectUtil.getPersistenceBlocks(project);
		Iterator<Persistence> iterator = persistenceBlocks.iterator();
		while (iterator.hasNext()) {
			Persistence persistence = iterator.next();
			Unit unit = PersistenceUtil.getUnitByName(persistence, name);
			if (unit != null)
				return unit;			
		}
		return null;
	}
	
	
	/*
	 * BeanType specification categories
	 */

	public BeanType getDefaultBeanType() {
		return getBeanType("defaultBeanType", BeanType.POJO);
	}
	
	public BeanType getClientLayerBeanType() {
		return getBeanType("clientLayerBeanType", BeanType.POJO);
	}
	
	public BeanType getDataLayerBeanType() {
		return getBeanType("dataLayerBeanType", BeanType.EJB);
	}
	
	public BeanType getServiceLayerBeanType() {
		return getBeanType("serviceLayerBeanType", BeanType.EJB);
	}

	public BeanType getBeanType(String name) {
		return getBeanType(name, BeanType.POJO);
	}
	
	public BeanType getBeanType(String name, BeanType defaultValue) {
		if (name == null)
			return BeanType.POJO;
		return defaultValue;
	}

	public Set<Service> getRemoteServices(Operation operation) {
		Set<Service> remoteServices = new HashSet<Service>();
		List<Command> commands = operation.getCommands();
		Set<Interactor> interactors = getInteractorsFromCommands(commands);
		Iterator<Interactor> iterator = interactors.iterator();
		while (iterator.hasNext()) {
			Interactor interactor = iterator.next();
			String remoteApplicationName = interactor.getName();
			String remoteServiceName = interactor.getService();
			Service remoteService = getServiceByName(remoteApplicationName, remoteServiceName);
			Assert.notNull(remoteService, "Remote service not found: application="+remoteApplicationName+", service="+remoteServiceName);
			remoteServices.add(remoteService);
		}
		return remoteServices;
	}
	
	public Set<Interactor> getInteractorsFromCommands(List<Command> commands) {
		Set<Interactor> interactors = new HashSet<Interactor>();
		Iterator<Command> iterator = commands.iterator();
		while (iterator.hasNext()) {
			Command command = iterator.next();
			if (command instanceof InvokeCommand) {
				interactors.add((Interactor) command.getActor());
				
			} else if (command instanceof SendCommand) {
				interactors.add((Interactor) command.getActor());
				
			} else if (command instanceof IfStatement) {
				IfStatement ifStatement = (IfStatement) command;
				interactors.addAll(getInteractorsFromCommands(ifStatement.getCommands()));
				interactors.addAll(getInteractorsFromCommands(ifStatement.getElseIfCommands()));
				interactors.addAll(getInteractorsFromCommands(ifStatement.getElseCommands()));
				
			} else if (command.getType().equals("unnamed")) {
				interactors.addAll(getInteractorsFromCommands(command.getCommands()));
			}
		}
		return interactors;
	}

	public Set<Service> getRemoteServicesOLD(Operation operation) {
		Set<Service> remoteServices = new HashSet<Service>();
		Set<Interactor> interactors = new HashSet<Interactor>();
		interactors.addAll(ServiceUtil.getInteractors(operation, Invoker.class));
		interactors.addAll(ServiceUtil.getInteractors(operation, Sender.class));
		Iterator<Interactor> iterator = interactors.iterator();
		while (iterator.hasNext()) {
			Interactor interactor = iterator.next();
			String serviceName = interactor.getService();
			Service remoteService = getServiceByName(serviceName);
			remoteServices.add(remoteService);
		}
		return remoteServices;
	}

	
	// protected void assurePackageNameApplied(Namespace namespace, Enumeration
	// enumeration) {
	// if (!enumeration.getType().contains(namespace.getName()))
	// enumeration.setType(namespace.getName()+"."+enumeration.getType());
	// }

	// /*
	// * Data layer runtime state:
	// */
	//
	// private PersistenceUnit persistenceUnit;
	//
	//
	// public PersistenceUnit getPersistenceUnit() {
	// return persistenceUnit;
	// }
	//
	// public void setPersistenceUnit(PersistenceUnit persistenceUnit) {
	// this.persistenceUnit = persistenceUnit;
	// }

}
