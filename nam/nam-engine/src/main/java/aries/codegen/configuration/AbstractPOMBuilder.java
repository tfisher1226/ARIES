package aries.codegen.configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nam.model.Application;
import nam.model.Channel;
import nam.model.Configuration;
import nam.model.Dependency;
import nam.model.DependencyScope;
import nam.model.DependencyType;
import nam.model.Import;
import nam.model.Information;
import nam.model.Link;
import nam.model.Module;
import nam.model.ModuleLevel;
import nam.model.ModuleType;
import nam.model.Namespace;
import nam.model.Project;
import nam.model.Property;
import nam.model.Service;
import nam.model.util.ApplicationUtil;
import nam.model.util.InformationUtil;
import nam.model.util.MessagingUtil;
import nam.model.util.ModuleFactory;
import nam.model.util.ModuleUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.ProjectUtil;

import org.aries.Assert;

import aries.codegen.AbstractFileBuilder;
import aries.generation.model.ModelDependency;
import aries.generation.model.ModelPom;


public abstract class AbstractPOMBuilder extends AbstractFileBuilder {

	public String getGroupId(Module module) {
		Application application = context.getApplication();
		if (application != null)
			return application.getGroupId();
		return module.getGroupId();
	}

	public String getArtifactId(ModuleType moduleType) {
		Application application = context.getApplication();
		String artifactId = ModuleFactory.getDefaultArtifactId(application, moduleType);
		return artifactId;
	}

	public String getVersion() {
		return "0.0.1-SNAPSHOT";
	}

	public String getType(ModuleType moduleType) {
		switch (moduleType) {
		case POM: return "pom";
		case MODEL: return "jar";
		case DATA: return "ejb";
		case CLIENT: return "ejb";
		//TODO add conditional here for EJB or JAR
		case SERVICE: return "ejb";
		case TEST: return "test";
		case VIEW: return "jar";
		case EAR: return "ear";
		default: return null;
		}
	}

	public Set<Link> getLinks(Service service) {
		return MessagingUtil.getLinks(context.getProjectList(), service);
	}

	public List<Channel> getChannels(Service service) {
		return MessagingUtil.getChannels(context.getProjectList(), service);
	}
	

	public ModelPom createPom(Project project, Module module) throws Exception {
		ModelPom pom = new ModelPom();
		pom.setGroupId(getGroupId(module));
		
		String artifactId = null;
		if (context.isOptionLimitToSingleModule()) {
		//if (context.isOptionLimitToSingleModule() || module.getType() == ModuleType.VIEW) {
			artifactId = context.getApplication().getArtifactId();
		} else {
			artifactId = module.getArtifactId();
		}

		pom.setArtifactId(artifactId);
		pom.setName(artifactId);
		pom.setVersion(getVersion());
		pom.setDescription("Project object model for: "+artifactId);
		//determine and configure the correct parent pom
		pom.setParentPom(createParentPom(project, module, pom));
		if (module.getType() == ModuleType.POM)
			pom.setModules(initializePomModules(module));
		pom.setProperties(initializePomProperties());
		pom.setFilters(initializePomFilters());
		pom.setResources(initializePomResources());
		pom.setTestResources(initializePomTestResources());
		pom.setPlugins(initializePomPlugins());
		pom.setProfiles(initializePomProfiles());
		pom.setManagedDependencies(initializePomManagedDependencies(module));
		pom.setDependencies(initializePomDependencies(module));
		pom.setPackaging(initializePomPackaging(module));
		return pom;
	}

	protected ModelPom createParentPom(Project project, Module module, ModelPom pom) throws Exception {
		//determine and configure parent pom
		if (module.getType() == ModuleType.POM & module.getLevel() == ModuleLevel.PROJECT_LEVEL) {
			pom.setModules(initializePomModules(module, ModuleLevel.PROJECT_LEVEL));
			return createAriesParentPomAsParent();
			
		} else if (module.getType() == ModuleType.POM & module.getLevel() == ModuleLevel.GROUP_LEVEL) {
			pom.setModules(initializePomModules(module, ModuleLevel.GROUP_LEVEL));
			return createAriesParentPomAsParent();
				
		} else if (module.getType() == ModuleType.POM & module.getLevel() == ModuleLevel.APPLICATION_LEVEL) {
			pom.setModules(initializePomModules(module, ModuleLevel.APPLICATION_LEVEL));
			Collection<Application> applications = ProjectUtil.getApplications(project);
			if (applications.size() == 1)
				return createAriesParentPomAsParent();
			return createProjectPomAsParent(pom);
			
		} else if (!context.isOptionLimitToSingleModule() || module.getType() == ModuleType.VIEW) {
			ModelPom parentPom = null;
			Collection<Application> applications = ProjectUtil.getApplications(project);
			if (applications.size() == 1 && module.getLevel() == ModuleLevel.PROJECT_LEVEL) {
				parentPom  = createAriesParentPomAsParent();
			} else if (context.getApplication() == null) {
				parentPom  = createProjectPomAsParent(pom);
			} else {
				parentPom  = createApplicationPomAsParent(pom);
			}
			
			if (parentPom == null)
				parentPom = createProjectPomAsParent(pom);
			if (parentPom == null)
				parentPom = createAriesParentPomAsParent();
			return parentPom;
		} else {
			return createAriesParentPomAsParent();
		}
	}

//	public ModelPom createProjectPomAsParent(ModelPom childPom) throws Exception {
//		ModelPom pom = new ModelPom();
//		Project project = context.getProject();
//		List<Module> pomModules = ProjectUtil.getPomModules(project);
//		//TODO assuming only one parent per project for now
//		Assert.isTrue(pomModules.size() <= 1, "Supporting only 1 POM-module per Model");
//		if (pomModules.size() == 1) {
//			Module pomModule = pomModules.get(0);
//			pom.setGroupId(pomModule.getGroupId());
//			pom.setArtifactId(pomModule.getArtifactId());
//			pom.setName(pomModule.getName());
//		} else {
//			pom.setGroupId(childPom.getGroupId());
//			String artifactId = childPom.getArtifactId();
//			int lastIndexOf = artifactId.lastIndexOf("-");
//			if (lastIndexOf != -1)
//				artifactId = artifactId.substring(0, lastIndexOf);
//			pom.setArtifactId(artifactId);
//			pom.setName(artifactId);
//		}
//		pom.setVersion("0.0.1-SNAPSHOT");
//		Application application = ProjectUtil.getApplication(project);
//		pom.setDescription("Parent pom for application: "+application.getName());
//		pom.setParentPom(createGrandparentPom());
//		//TODO pom.setDependencies(initializePomDependencies());
//		pom.setPackaging("pom");
//		return pom;
//	}

	public ModelPom createProjectPomAsParent(ModelPom childPom) {
		Project project = context.getProject();
		Set<Module> pomModules = ProjectUtil.getProjectModules(project, ModuleType.POM);
		Assert.isTrue(pomModules.size() <= 1, "Supporting only 1 POM-module per project");
		if (pomModules.size() == 0)
			return null;
		ModelPom pom = new ModelPom();
		Module pomModule = pomModules.iterator().next();
		pom.setName(pomModule.getName());
		pom.setGroupId(pomModule.getGroupId());
		pom.setArtifactId(pomModule.getArtifactId());
		pom.setVersion("0.0.1-SNAPSHOT");
		Application application = context.getApplication();
		if (application != null)
			pom.setDescription("Parent pom for application modules: "+application.getName());
		else pom.setDescription("Parent pom for project modules: "+project.getName());
		//pom.setParentPom(createGrandparentPom());
		//TODO pom.setDependencies(initializePomDependencies());
		pom.setPackaging("pom");
		return pom;
	}

	public ModelPom createProjectAppPomAsParent(ModelPom childPom) {
		ModelPom pom = new ModelPom();
		Application application = context.getApplication();
		pom.setName(application.getArtifactId());
		pom.setGroupId(application.getGroupId());
		pom.setArtifactId(application.getArtifactId());
		pom.setVersion(application.getVersion());
		pom.setDescription("Parent pom for application: "+application.getName());
		//TODO pom.setDependencies(initializePomDependencies());
		pom.setPackaging("pom");
		return pom;
	}
	
	public ModelPom createApplicationPomAsParent(ModelPom childPom) {
		ModelPom pom = new ModelPom();
		Application application = context.getApplication();
		pom.setName(application.getArtifactId());
		pom.setGroupId(application.getGroupId());
		pom.setArtifactId(application.getArtifactId());
		pom.setVersion(application.getVersion());
		pom.setDescription("Parent pom for application: "+application.getName());
		//TODO pom.setDependencies(initializePomDependencies());
		pom.setPackaging("pom");
		return pom;
	}

	public ModelPom createAriesParentPomAsParent() {
		ModelPom pom = new ModelPom();
		pom.setGroupId("org.aries");
		pom.setArtifactId("aries-parent");
		pom.setName("aries-parent");
		pom.setVersion("0.0.1-SNAPSHOT");
		pom.setDescription("Parent pom for Aries framework");
		pom.setPackaging("pom");
		return pom;
	}

	public ModelPom createCommonPomAsParent() {
		ModelPom pom = new ModelPom();
		pom.setGroupId("org.aries");
		pom.setArtifactId("common");
		pom.setName("common");
		pom.setVersion("0.0.1-SNAPSHOT");
		pom.setDescription("Parent pom for common framework");
		pom.setParentPom(createGrandparentPom());
		//TODO pom.setDependencies(initializePomDependencies());
		pom.setPackaging("pom");
		return pom;
	}

	public ModelPom createGrandparentPom() {
		ModelPom pom = new ModelPom();
		pom.setGroupId("org.aries");
		pom.setArtifactId("aries");
		pom.setName("aries");
		pom.setVersion("0.0.1-SNAPSHOT");
		pom.setDescription("Grandparent pom for common framework");
		//TODO pom.setDependencies(initializePomDependencies());
		pom.setPackaging("pom");
		return pom;
	}

	public String initializePomPackaging(Module module) throws Exception {
		switch (module.getType()) {
		case POM:
			return "pom";
		case EAR:
			return "ear";
		case DATA:
		case CLIENT:
		case SERVICE:
			return "ejb";
		case VIEW:
			return "war";
		case MODEL:
		case TEST:
		default:
			return "jar";
		}
	}

	public ModelPom initializeParentPom(Application application) throws Exception {
		ModelPom pom = new ModelPom();
		pom.setGroupId(context.getApplication().getGroupId());
		pom.setArtifactId(application.getArtifactId());
		pom.setName(application.getName());
		pom.setVersion("0.0.1-SNAPSHOT");
		pom.setDescription(application.getName());
		pom.setPackaging("pom");
		return pom;
	}

	public List<String> initializePomModules(Module module) throws Exception {
		return initializePomModules(module, module.getLevel());
	}
	
	public List<String> initializePomModules(Module module, ModuleLevel moduleLevel) throws Exception {
		List<Module> earModules = new ArrayList<Module>();
		List<Module> warModules = new ArrayList<Module>();
		
		Map<ModuleType, List<Module>> moduleMap = new HashMap<ModuleType, List<Module>>();
		List<String> list = new ArrayList<String>();
		
		switch (moduleLevel) {
		case PROJECT_LEVEL: {
			Set<Module> projectModules = ProjectUtil.getProjectModules(context.getProject());
			Iterator<Module> moduleIterator = projectModules.iterator();
			while (moduleIterator.hasNext()) {
				Module projectModule = moduleIterator.next();
				ModuleType moduleType = projectModule.getType();
				if (context.canGenerate(moduleType) && moduleType != ModuleType.POM) {
					addModuleToMap(moduleMap, projectModule);
				}
			}

			Collection<Application> applications = ProjectUtil.getApplications(context.getProject());
			Iterator<Application> applicationIterator = applications.iterator();
			while (applicationIterator.hasNext()) {
				Application application = applicationIterator.next();
				list.add(application.getArtifactId());
			}
			break;
		}

		case GROUP_LEVEL:
		case APPLICATION_LEVEL: {
			Project project = context.getProject();
			List<Project> subProjects = ProjectUtil.getSubProjects(project);
			Iterator<Project> projectIterator = subProjects.iterator();
			while (projectIterator.hasNext()) {
				Project subProject = projectIterator.next();
				Module pomModule = ProjectUtil.getPomModule(subProject);
				if (pomModule != null)
					addModuleToMap(moduleMap, pomModule);
			}
			
			Collection<Application> applications = ProjectUtil.getApplications(project);
			boolean includeProjectModules = applications.size() == 1;
			if (includeProjectModules) {
				Set<Module> projectModules = ProjectUtil.getProjectModules(project);
				Iterator<Module> projectModuleIterator = projectModules.iterator();
				while (projectModuleIterator.hasNext()) {
					Module projectModule = projectModuleIterator.next();
					if (projectModule.getType() != ModuleType.POM)
						addModuleToMap(moduleMap, projectModule);
				}
			}
			
			String applicationArtifactId = module.getArtifactId();
			Application application = ProjectUtil.getApplicationByArtifactId(project, applicationArtifactId);
			if (application != null) {
				Set<Module> applicationModules = ModuleUtil.getModules(application.getModules());
				Iterator<Module> applicationModuleIterator = applicationModules.iterator();
				while (applicationModuleIterator.hasNext()) {
					Module applicationModule = applicationModuleIterator.next();
					if (applicationModule.getType() != ModuleType.POM)
						addModuleToMap(moduleMap, applicationModule);
				}
			}
			
//			Collection<Application> applications = ProjectUtil.getApplications(context.getProject());
//			Iterator<Application> applicationIterator = applications.iterator();
//			while (applicationIterator.hasNext()) {
//				Application application = applicationIterator.next();
//				List<Module> modules = application.getModules().getModules();
//				Iterator<Module> applicationModuleIterator = modules.iterator();
//				while (applicationModuleIterator.hasNext()) {
//					Module applicationModule = applicationModuleIterator.next();
//					if (applicationModule.getType() != ModuleType.POM) {
//						list.add(applicationModule.getArtifactId());
//					}
//				}
//			}
			break;
		}
		}
		
		if (context.canGenerateModel())
			list.addAll(getArtifactIds(moduleMap, ModuleType.MODEL));
		if (context.canGenerateClient())
			list.addAll(getArtifactIds(moduleMap, ModuleType.CLIENT));
		if (context.canGenerateData())
			list.addAll(getArtifactIds(moduleMap, ModuleType.DATA));
		if (context.canGenerateService())
			list.addAll(getArtifactIds(moduleMap, ModuleType.SERVICE));
		if (context.canGenerateWAR())
			list.addAll(getArtifactIds(moduleMap, ModuleType.VIEW));
		if (context.canGenerateEAR())
			list.addAll(getArtifactIds(moduleMap, ModuleType.EAR));
		return list;
	}

	private void addModuleToMap(Map<ModuleType, List<Module>> map, Module module) {
		List<Module> list = map.get(module.getType());
		if (list == null) {
			list = new ArrayList<Module>();
			map.put(module.getType(), list);
		}
		list.add(module);
	}

	private List<String> getArtifactIds(Map<ModuleType, List<Module>> map, ModuleType moduleType) {
		List<Module> modules = map.get(moduleType);
		List<String> list = new ArrayList<String>();
		if (modules != null) {
			Iterator<Module> iterator = modules.iterator();
			while (iterator.hasNext()) {
				Module module = iterator.next();
				list.add(module.getArtifactId());
			}
		}
		return list;
	}

//	protected Properties initializePomProperties() throws Exception {
//		Properties properties = new Properties();
//		return properties;
//	}

	protected List<Property> initializePomProperties() throws Exception {
		List<Property> properties = new ArrayList<Property>();
		Collection<Application> applications = ProjectUtil.getApplications(context.getProject());
		if (context.getApplication() == null || context.getModule().getLevel() == ModuleLevel.GROUP_LEVEL) {
			String prefix = context.getProject().getName();
			properties.add(createProperty(prefix + ".model.home", "../../model"));
			properties.add(createProperty(prefix + ".runtime.home", "../../runtime"));
		} else if (applications.size() > 1) {
			String prefix = context.getProject().getName();
			properties.add(createProperty(prefix + ".model.home", "../../model"));
			properties.add(createProperty(prefix + ".runtime.home", "../runtime"));
		} else {
			String prefix = context.getApplication().getArtifactId();
			properties.add(createProperty(prefix + ".model.home", "../../model"));
			properties.add(createProperty(prefix + ".runtime.home", "../../runtime"));
		}
		return properties;
	}
	
	protected Property createProperty(String name, String value) {
		Property property = new Property();
		property.setName(name);
		property.setValue(value);
		return property;
	}
	
	public List<String> initializePomFilters() {
		List<String> filters = new ArrayList<String>();
		return filters;
	}
	
	protected List<String> initializePomResources() {
		List<String> resources = new ArrayList<String>();
		return resources;
	}

	protected List<String> initializePomTestResources() {
		List<String> testResources = new ArrayList<String>();
		return testResources;
	}

	public List<String> initializePomPlugins() throws Exception {
		List<String> plugins = new ArrayList<String>();
		return plugins;
	}

	public List<String> initializePomProfiles() throws Exception {
		List<String> profiles = new ArrayList<String>();
		return profiles;
	}
	
	public Set<ModelDependency> initializePomManagedDependencies(Module module) throws Exception {
		Set<ModelDependency> dependencies = new LinkedHashSet<ModelDependency>();
		return dependencies;
	}

	public Set<ModelDependency> initializePomDependencies(Module module) throws Exception {
		Set<ModelDependency> plugins = new LinkedHashSet<ModelDependency>();
		return plugins;
	}

	public List<ModelDependency> initializePomDependenciesForTestProject() throws Exception {
		List<ModelDependency> dependencies = new ArrayList<ModelDependency>();
		dependencies.addAll(createPomProjectDependencies(ModuleType.MODEL));
		dependencies.addAll(createPomProjectDependencies(ModuleType.CLIENT));
		dependencies.add(createPomDependency("org.aries", "common-util"));
		dependencies.add(createPomDependency("org.aries", "common-model"));
		dependencies.addAll(createPomApplicationDependencies("model"));
		dependencies.addAll(createPomApplicationDependencies("client"));
		return dependencies;
	}

	public List<ModelDependency> initializePomDependenciesForEarProject() throws Exception {
		List<ModelDependency> dependencies = new ArrayList<ModelDependency>();
		return dependencies;
	}
	
//	public ModelDependency createPomApplicationDependency(ModuleType moduleType) {
//		String groupId = getGroupId();
//		String artifactId = getArtifactId(moduleType);
//		String version = getVersion();
//		ModelDependency dependency = createPomDependency(groupId, artifactId, version);
//		return dependency;
//	}

	public List<ModelDependency> createPomProjectDependencies(ModuleType moduleType) {
		List<ModelDependency> dependencies = new ArrayList<ModelDependency>();
		Map<String, Module> modules = ProjectUtil.getModulesAsMap(context.getProject(), moduleType);
		Iterator<Module> iterator = modules.values().iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			dependencies.add(createPomDependency(module));
			dependencies.add(createPomDependency(module, "test", "test-jar"));
		}
		return dependencies;
	}
	
	public List<ModelDependency> createPomApplicationDependencies(ModuleType moduleType) {
		List<ModelDependency> dependencies = new ArrayList<ModelDependency>();
		Application application = context.getApplication();
		if (application != null) {
			Collection<Module> modules = ApplicationUtil.getModules(application, moduleType);
			Iterator<Module> iterator = modules.iterator();
			while (iterator.hasNext()) {
				Module module = iterator.next();
				dependencies.add(createPomDependency(module));
				dependencies.add(createPomDependency(module, "test", "test-jar"));
			}
		}
		return dependencies;
	}
	
	public List<ModelDependency> createPomApplicationDependencies(String moduleType) {
		List<ModelDependency> list = new ArrayList<ModelDependency>();
		Application application = context.getApplication();
		if (application != null) {
			List<Dependency> dependencies = ApplicationUtil.getDependencies(application);
			Iterator<Dependency> iterator = dependencies.iterator();
			while (iterator.hasNext()) {
				Dependency dependency = iterator.next();
				String groupId = dependency.getGroupId();
				String artifactId = dependency.getArtifactId()+"-"+moduleType;
				String version = dependency.getVersion();
				if (version == null)
					version = "0.0.1-SNAPSHOT";
				list.add(createPomDependency(groupId, artifactId, version));
			}
		}
		return list;
	}
	
	protected List<ModelDependency> createPomDependencies(List<Dependency> dependencies) {
		List<ModelDependency> modelDependencies = new ArrayList<ModelDependency>();
		Iterator<Dependency> iterator = dependencies.iterator();
		while (iterator.hasNext()) {
			Dependency dependency = iterator.next();
			modelDependencies.add(createPomDependency(dependency));
		}
		return modelDependencies;
	}

	public List<ModelDependency> createPomDependenciesForModelModule(Namespace namespace) {
		return createPomDependenciesForModule(namespace, ModuleType.MODEL);
	}

	public List<ModelDependency> createPomDependenciesForDataModule(Namespace namespace) {
		return createPomDependenciesForModule(namespace, ModuleType.DATA);
	}

	public ModelDependency createPomDependencyForModelModule(Namespace namespace) {
		return createPomDependencyForModule(namespace, ModuleType.MODEL);
	}
	
	public ModelDependency createPomDependencyForDataModule(Namespace namespace) {
		return createPomDependencyForModule(namespace, ModuleType.DATA);
	}
	
	public List<ModelDependency> createPomDependenciesForModule(Namespace namespace, ModuleType moduleType) {
		List<ModelDependency> dependencies = new ArrayList<ModelDependency>();
		ModelDependency releaseModule = createPomDependencyForModule(namespace, moduleType);
		ModelDependency testModule = createPomDependencyForModule(namespace, moduleType);
		testModule.setScope("test");
		testModule.setType("test-jar");
		dependencies.add(releaseModule);
		dependencies.add(testModule);
		return dependencies;
	}
	
	public ModelDependency createPomDependencyForModule(Namespace namespace, ModuleType moduleType) {
		//String groupId = NamespaceUtil.getGroupIdFromNamespace(namespace);
		String groupId = context.getProject().getDomain();
		String artifactId = getArtifactIdForModule(namespace, moduleType);
		String version = NamespaceUtil.getVersionFromNamespace(namespace);
		ModelDependency modelDependency = createPomDependency(groupId, artifactId, version);
		switch (moduleType) {
		case CLIENT:
		case DATA:
		case SERVICE:
			modelDependency.setType("ejb");
			break;
		default:
			break;
		}
		return modelDependency;
	}

	public String getArtifactIdForModule(Namespace namespace, ModuleType moduleType) {
		String artifactId = NamespaceUtil.getArtifactIdFromNamespace(namespace);
		switch (moduleType) {
		case DATA:
			//TODO - Externalize this properly
			artifactId = artifactId.replace("-model", "-persistence");
			return artifactId;
		default:
			return artifactId;
		}
	}
	
	public ModelDependency createPomDependency(Module module) {
		return createPomDependency(module.getGroupId(), module.getArtifactId(), module.getVersion(), "provided", getType(module.getType()));
	}

	public ModelDependency createPomDependency(Module module, String scope, String type) {
		return createPomDependency(module.getGroupId(), module.getArtifactId(), module.getVersion(), scope, type);
	}

	public ModelDependency createPomDependency(Application application) {
		return createPomDependency(application.getGroupId(), application.getArtifactId(), application.getVersion(), "provided", "pom");
	}

	public ModelDependency createPomDependency(Configuration configuration) {
		return createPomDependency(configuration.getGroupId(), configuration.getArtifactId(), configuration.getVersion(), "import", "pom");
	}

	public ModelDependency createPomDependency(String groupId, String artifactId) {
		return createPomDependency(groupId, artifactId, null, "provided");
	}

	public ModelDependency createPomDependency(String groupId, String artifactId, String version) {
		return createPomDependency(groupId, artifactId, version, "provided");
	}

	public ModelDependency createPomDependency(String groupId, String artifactId, String version, String scope) {
		return createPomDependency(groupId, artifactId, version, scope, "jar");
	}

	public ModelDependency createPomDependency(String groupId, String artifactId, String version, String scope, String type) {
		ModelDependency dependency = new ModelDependency();
		dependency.setGroupId(groupId);
		dependency.setArtifactId(artifactId);
		dependency.setVersion(version);
		dependency.setScope(scope);
		dependency.setType(type);
		return dependency;
	}

	public ModelDependency createPomDependency(Dependency dependency) {
		ModelDependency modelDependency = new ModelDependency();
		modelDependency.setGroupId(dependency.getGroupId());
		modelDependency.setArtifactId(dependency.getArtifactId());
		modelDependency.setVersion(dependency.getVersion());
		
		String type = "jar"; 
		String scope = "compile"; 
		DependencyType dependencyType = dependency.getType();
		DependencyScope dependencyScope = dependency.getScope();
		if (dependencyType == DependencyType.TEST_JAR)
			dependencyScope = DependencyScope.TEST;
		if (dependencyType != null) 
			type = dependencyType.name().toLowerCase();
		if (dependencyScope != null) 
			scope = dependencyScope.name().toLowerCase();
		if (type.equals("test_jar"))
			type = "test-jar";
		modelDependency.setType(type);
		modelDependency.setScope(scope);
		return modelDependency;
	}
	
	protected void addDependency(Set<ModelDependency> dependencies, Set<ModelDependency> dependencySet, ModelDependency dependency) {
		if (!dependencySet.contains(dependency)) {
			dependencySet.add(dependency);
			dependencies.add(dependency);
		}
	}


//	protected Collection<ModelDependency> getModelModuleDependencies(Project project) {
//		Set<ModelDependency> dependencies = new LinkedHashSet<ModelDependency>();
//		List<Information> informationBlocks = ProjectUtil.getInformationBlocks(project);
//		Iterator<Information> iterator = informationBlocks.iterator();
//		while (iterator.hasNext()) {
//			Information information = iterator.next();
//			Collection<ModelDependency> list = getModelModuleDependencies(context.getModule(), information);
//			dependencies.addAll(list);
//		}
//		return dependencies;
//	}
	
	protected Collection<ModelDependency> getModelModuleDependencies(Project project) {
		Set<ModelDependency> dependencies = new LinkedHashSet<ModelDependency>();
		Set<Module> modelModules = ModuleUtil.getModelModules(project);
		Iterator<Module> iterator = modelModules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			Collection<ModelDependency> list = getModelModuleDependencies(module);
			dependencies.addAll(list);
		}
		return dependencies;
	}
	
	protected Collection<ModelDependency> getModelModuleDependencies(Module module) {
		return getModelModuleDependencies(module, module.getInformation());
	}

	protected Collection<ModelDependency> getModelModuleDependencies(Information information) {
		return getModelModuleDependencies(context.getModule(), information);
	}
	
	protected Collection<ModelDependency> getModelModuleDependencies(Module module, Information information) {
		Set<ModelDependency> dependencies = new LinkedHashSet<ModelDependency>();
		if (information != null) {
			Collection<Namespace> namespacesToDependUpon = InformationUtil.getAllNamespaces(information);
			Iterator<Namespace> iterator = namespacesToDependUpon.iterator();
			while (iterator.hasNext()) {
				Namespace namespace = iterator.next();
				if (module.getNamespace().equals(namespace.getUri()))
					continue;
				if (namespace.getFilePath() == null)
					continue;
				
				Module moduleforNamespace = context.getModelModuleByNamespace(namespace);
				if (moduleforNamespace == null) {
					dependencies.addAll(createPomDependenciesForModelModule(namespace));
				} else {
					ModelDependency releaseDependency = createPomDependency(moduleforNamespace);
					ModelDependency testDependency = createPomDependency(moduleforNamespace);
					testDependency.setScope("test");
					testDependency.setType("test-jar");
					dependencies.add(releaseDependency);
					dependencies.add(testDependency);
				}
			}
		}
		
		return dependencies;
	}

	protected Collection<ModelDependency> getDataModuleDependencies(Project project) {
		Set<ModelDependency> dependencies = new LinkedHashSet<ModelDependency>();
		Set<Module> modelModules = ModuleUtil.getModelModules(project);
		Iterator<Module> iterator = modelModules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			Collection<ModelDependency> list = getDataModuleDependencies(module);
			dependencies.addAll(list);
		}
		return dependencies;
	}
	
	protected Collection<ModelDependency> getDataModuleDependencies(Module module) {
		return getDataModuleDependencies(module.getInformation());
	}
	
	protected Collection<ModelDependency> getDataModuleDependencies(Information information) {
		Set<ModelDependency> dependencies = new LinkedHashSet<ModelDependency>();
		if (information != null) {
			Collection<Namespace> namespacesToDependUpon = InformationUtil.getAllNamespaces(information);
			Iterator<Namespace> iterator = namespacesToDependUpon.iterator();
			while (iterator.hasNext()) {
				Namespace namespace = iterator.next();
				if (namespace.getFilePath() == null)
					continue;
				
				Module moduleforNamespace = context.getDataModuleByNamespace(namespace);
				if (moduleforNamespace == null) {
					List<ModelDependency> modelDependencies = createPomDependenciesForDataModule(namespace);
					dependencies.addAll(modelDependencies);
				} else {
					ModelDependency releaseDependency = createPomDependency(moduleforNamespace);
					ModelDependency testDependency = createPomDependency(moduleforNamespace);
					testDependency.setScope("test");
					testDependency.setType("test-jar");
					dependencies.add(releaseDependency);
					dependencies.add(testDependency);
				}
			}
		}
		
		return dependencies;
	}

	protected void printDependencies(List<Dependency> dependencies) {
		Iterator<Dependency> iterator = dependencies.iterator();
		while (iterator.hasNext()) {
			Dependency dependency = (Dependency) iterator.next();
			System.out.println(dependency.getGroupId()+":"+dependency.getArtifactId()+" "+dependency.getType()+" "+dependency.getVersion());
		}
	}
	
}
