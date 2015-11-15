package nam.ear;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import nam.model.Application;
import nam.model.Configuration;
import nam.model.Dependency;
import nam.model.DependencyScope;
import nam.model.DependencyType;
import nam.model.Module;
import nam.model.ModuleType;
import nam.model.Project;
import nam.model.Projects;
import nam.model.util.ApplicationUtil;
import nam.model.util.ConfigurationUtil;
import nam.model.util.DependencyUtil;
import nam.model.util.ProjectUtil;

import org.apache.cxf.common.util.StringUtils;
import org.apache.maven.model.DependencyManagement;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.aries.common.Property;
import org.aries.util.NameUtil;

import aries.codegen.configuration.AbstractPOMBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelDependency;
import aries.generation.model.ModelPom;


public class PomXMLBuilder extends AbstractPOMBuilder {

	private static String POM_FILE_HOME = "/workspace/ARIES2/aries/groups";

	private Set<Module> allModules = new LinkedHashSet<Module>();
	
	private Set<Module> localModules = new LinkedHashSet<Module>();
	
	private List<Dependency> localDependencies = new ArrayList<Dependency>();

	private List<Dependency> transitiveDependencies = new ArrayList<Dependency>();
	
	private Set<ModelDependency> managedDependencies = new HashSet<ModelDependency>();
	
	private static Set<String> visitedPomFiles = new HashSet<String>();
	
	private static Set<String> visitedModules = new HashSet<String>();
	
	private static Set<String> visitedConfigurations = new HashSet<String>();

	private List<Configuration> configurations;

	
	public PomXMLBuilder(GenerationContext context) {
		this.context = context;
	}

	public static String getPomFileName(String artifactId) {
		return getPomFileName(artifactId, false);
	}
	
	public static String getBomFileName(String artifactId) {
		return getPomFileName(artifactId, true);
	}
	
	public static String getPomFileName(String artifactId, boolean isBomFile) {
		StringBuffer buf = new StringBuffer();
		buf.append(POM_FILE_HOME);
		String[] segments = NameUtil.splitIntoSegments(artifactId, "-");
		for (String segment : segments) {
			if (segment.equals("layer"))
				continue;
			buf.append("/"+segment);
		}
		//if (isBomFile)
		//	buf.append("/bom");
		buf.append("/pom.xml");
		String filePath = buf.toString();
		return filePath;
	}
	
	public static String getDependencyKey(Dependency dependency) {
		return dependency.getGroupId() + "." + dependency.getArtifactId();
	}

	public static String getDependencyKey(org.apache.maven.model.Dependency dependency) {
		return dependency.getGroupId() + "." + dependency.getArtifactId();
	}

	public ModelPom build() throws Exception {
		ModelPom modelPom = createPom(context.getProject(), context.getModule());
		return modelPom;
	}

//	public List<Dependency> initializeLocalDependencies() {
//		List<Dependency> dependencies = new ArrayList<Dependency>();
//		dependencies.add(DependencyUtil.createDependency("org.aries.pom", "aries-platform-model-layer", "0.0.1-SNAPSHOT", "import", "pom"));
//		dependencies.add(DependencyUtil.createDependency("org.aries.pom", "aries-platform-client-layer", "0.0.1-SNAPSHOT", "import", "pom"));
//		dependencies.add(DependencyUtil.createDependency("org.aries.pom", "aries-platform-service-layer", "0.0.1-SNAPSHOT", "import", "pom"));
//		dependencies.add(DependencyUtil.createDependency("org.aries.pom", "aries-platform-data-layer", "0.0.1-SNAPSHOT", "import", "pom"));
//		return dependencies;
//	}
	
//	protected List<Property> initializePomProperties() throws Exception {
//		List<Property> properties = new ArrayList<Property>();
//		Collection<Application> applications = ProjectUtil.getApplications(context.getProject());
//		if (context.getApplication() == null || context.getModule().getLevel() == ModuleLevel.GROUP_LEVEL) {
//			String prefix = context.getProject().getName();
//			properties.add(createProperty(prefix + ".model.home", "../../model"));
//			properties.add(createProperty(prefix + ".runtime.home", "../../runtime"));
//		} else if (applications.size() > 1) {
//			String prefix = context.getProject().getName();
//			properties.add(createProperty(prefix + ".model.home", "../../../model"));
//			properties.add(createProperty(prefix + ".runtime.home", "../runtime"));
//		} else {
//			String prefix = context.getApplication().getArtifactId();
//			properties.add(createProperty(prefix + ".model.home", "../../model"));
//			properties.add(createProperty(prefix + ".runtime.home", "../../runtime"));
//		}
//		return properties;
//	}
	
	public List<String> initializePomPlugins() {
		allModules.clear();
		localModules.clear();
		localDependencies.clear();
		transitiveDependencies.clear();
		visitedPomFiles.clear();
		visitedModules.clear();
		visitedConfigurations.clear();
		Project project = context.getProject();
		Application application = context.getApplication();
		
		addModuleDependencies(project.getSubProjects());
		addModuleDependencies(project);

		//Configuration configuration = application.getConfiguration();
		Configuration configuration = createConfiguration(application);
		application.setConfiguration(configuration);

		Map<String, Configuration> configurationMap = new LinkedHashMap<String, Configuration>();
		Iterator<Module> iterator = allModules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			if (module.getType() == ModuleType.VIEW)
				continue;
			localDependencies.addAll(context.getModuleTemplateDependencies(module.getType(), true));
			List<Dependency> moduleDependencies = context.getModuleTemplateDependencies(module.getType(), false);
			List<Configuration> moduleConfigurations = createConfigurations(moduleDependencies);
			//appendConfigurationsToMap(configurationMap, moduleConfigurations);
			configurationMap.putAll(createConfigurationMap(moduleConfigurations));
		}

		configurations = configuration.getConfigurations();
		configurations.addAll(configurationMap.values());
		initializeConfigurationsFromPomFile(configurations);
		ConfigurationUtil.sortConfigurations(configurations);
		//Map<String, Configuration> map = printConfigurations(configurations, 0);
		//ArrayList<String> list = new ArrayList<String>(map.keySet());
		//Collections.sort(list);
		
		transitiveDependencies = generateModuleDependenciesForEARPlugin();
		//transitiveDependencies = DependencyUtil.removeDuplicateDependencies(transitiveDependencies);
		DependencyUtil.sortDependencies(transitiveDependencies);
		//printDependencies(transitiveDependencies);
		
		List<Dependency> filteredDependencies = new ArrayList<Dependency>();
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "org.apache.ant", "ant"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "commons-lang", "commons-lang"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "commons-io", "commons-io"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "commons-validator", "commons-validator"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "commons-digester", "commons-digester"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "joda-time", "joda-time"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "wsdl4j", "wsdl4j"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "org.eclipse.core", "org.eclipse.core.resources"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "org.eclipse.core", "org.eclipse.core.runtime"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "org.eclipse.core", "org.eclipse.core.expressions"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "org.eclipse.equinox", "org.eclipse.equinox.common"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "org.eclipse.osgi", "org.eclipse.osgi"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "org.eclipse.xsd", "org.eclipse.xsd"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "org.eclipse.emf", "org.eclipse.emf.ecore"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "org.eclipse.emf", "org.eclipse.emf.common"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "org.eclipse.emf", "org.eclipse.emf.codegen"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "org.eclipse.emf", "org.eclipse.emf.codegen.ecore"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "org.eclipse.emf", "org.eclipse.emf.ecore.xmi"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "org.eclipse.wst", "wsdl"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "org.eclipse.bpel.plugins", "org.eclipse.bpel.common.model"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "org.eclipse.bpel.plugins", "org.eclipse.bpel.model"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "org.eclipse.bpel.plugins", "org.eclipse.bpel.validator"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "org.eclipse.bpel.plugins", "org.eclipse.bpel.xpath10"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "org.hibernate", "hibernate-ehcache"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "org.javassist", "javassist"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "org.jboss.el", "jboss-el"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "org.jboss.jbossts", "jbossjta"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "org.jboss.jbossts", "jbossjts"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "org.jboss.el", "jboss-el"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "org.jboss.seam", "jboss-seam"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "org.jboss.seam", "jboss-seam-debug"));
		//filteredDependencies.addAll(filterDependencies(transitiveDependencies, "org.jboss.seam", "jboss.seam-excel"));
		//filteredDependencies.addAll(filterDependencies(transitiveDependencies, "org.jboss.seam", "jboss.seam-mail"));
		//filteredDependencies.addAll(filterDependencies(transitiveDependencies, "org.jboss.seam", "jboss.seam-pdf"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "org.jgroups", "jgroups"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "org.drools", "drools-api"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "org.drools", "drools-core"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "org.drools", "drools-compiler"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "org.drools", "drools-decisiontables"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "org.antlr", "antlr-runtime"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "org.mvel", "mvel2"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "net.sf.ehcache", "ehcache-core"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "net.sf.ehcache", "ehcache-jgroupsreplication"));
		filteredDependencies.addAll(filterDependencies(transitiveDependencies, "com.thoughtworks.xstream", "xstream"));
		filteredDependencies.addAll(filterDependenciesByGroupId(transitiveDependencies, "org.aries"));
		DependencyUtil.sortDependencies(filteredDependencies);

		List<Dependency> allDependencies = new ArrayList<Dependency>();
		allDependencies.addAll(localDependencies);
		allDependencies.addAll(filteredDependencies);

		firstGenerated = true;
		List<String> plugins = new ArrayList<String>();
		Buf buf = new Buf();
		buf.putLine3("<plugin>");
		buf.putLine3("	<artifactId>maven-ear-plugin</artifactId>");
		buf.putLine3("	<configuration>");
		buf.putLine3("		<version>5</version>");
		//buf.putLine3("		<earSourceDirectory>${basedir}/src/${targetHost}/application</earSourceDirectory>");
		buf.putLine3("		<earSourceDirectory>${basedir}/src/main/application</earSourceDirectory>");
		buf.putLine3("		<generateApplicationXml>false</generateApplicationXml>");
		//buf.putLine3("		<defaultLibBundleDir>lib</defaultLibBundleDir>");
		
//		<!--  
//		<version>5</version>
//		<security></security>
//		<initializeInOrder>true</initializeInOrder>
//		<defaultLibBundleDir>/</defaultLibBundleDir>
//		<earSourceDirectory>${basedir}/src/main/application</earSourceDirectory>
//		<generatedDescriptorLocation>${project.build.directory}</generatedDescriptorLocation>
//		<jboss>
//			<version>${jboss.app.version}</version>
//			<loader-repository>bookshop2:app=ejb3</loader-repository>
//		    <data-sources>
//		      <data-source>sgiusa-ds.xml</data-source>
//		      <data-source>sgiusa-orig-ds.xml</data-source>
//		      <data-source>notification-ds.xml</data-source>
//		    </data-sources>
//		</jboss>
//		-->
		
		buf.putLine3("");
		buf.putLine3("		<modules>");
		buf.putLines(6, generateModulesForEARPlugin(allDependencies));
		buf.putLine3("		</modules>");
		buf.putLine3("	</configuration>");
		buf.putLine3("</plugin>");
		plugins.add(buf.get());
		return plugins;
	}

	protected void addModuleDependencies(Projects projects) {
		List<Project> projectList = projects.getProjects();
		Iterator<Project> iterator = projectList.iterator();
		while (iterator.hasNext()) {
			Project project = iterator.next();
			addModuleDependencies(project, true);
		}
	}

	protected void addModuleDependencies(Project project) {
		addModuleDependencies(project, false);
	}

	protected void addModuleDependencies(Project project, boolean isHostedSubProject) {
		Collection<Module> projectModules = ProjectUtil.getDeployableModules(project, true, true);
		allModules.addAll(projectModules);

		String contextRoot = context.getApplication().getContextRoot();
		localDependencies.addAll(generateModuleDependencies(projectModules, contextRoot));
		localModules.addAll(projectModules);
		
		addModuleDependencies(project, isHostedSubProject, true);
		addModuleDependencies(project, isHostedSubProject, false);
	}

	protected void addModuleDependencies(Project project, boolean isHostedSubProject, boolean addHostedApplications) {
		Collection<Application> applications = ProjectUtil.getApplications(project);
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			boolean isHomeApplication = ProjectUtil.isHomeApplication(project, application);
			boolean isMainApplication = !isHostedSubProject && isHomeApplication;
			boolean isHostedApplication = isHostedSubProject || isHomeApplication;
			if (addHostedApplications && !isHostedApplication)
				continue;
			if (!addHostedApplications && isHostedApplication)
				continue;
			
			String contextRoot = context.getApplication().getContextRoot();
			Collection<Module> applicationModules = ProjectUtil.getDeployableModules(application, isMainApplication, isHostedApplication);
			List<Dependency> applicationDependencies = ApplicationUtil.getDependencies(application);
			localDependencies.addAll(generateModuleDependencies(applicationModules, contextRoot));
			localDependencies.addAll(applicationDependencies);
			localModules.addAll(applicationModules);
			allModules.addAll(applicationModules);
		}
	}

	private boolean firstGenerated;
	
	protected List<Dependency> generateModuleDependencies(Collection<Module> modules, String contextRoot) {
		List<Dependency> list = new ArrayList<Dependency>();
		Iterator<Module> iterator = modules.iterator();
		for (int i=0; iterator.hasNext();) {
			Module module = iterator.next();
			Dependency output = generateModuleDependency(module, contextRoot);
			if (output != null) {
				if (module.getType() == ModuleType.MODEL)
					list.add(0, output);
				else list.add(output);
			}
		}
		return list;
	}

	protected Dependency generateModuleDependency(Module module, String contextRoot) {
		if (module.getType() == ModuleType.MODEL) {
			return generateModuleDependencyForTypeJAR(module.getGroupId(), module.getArtifactId(), module.getVersion());

		} else if (
			module.getType() == ModuleType.SERVICE || 
			module.getType() == ModuleType.DATA || 
			module.getType() == ModuleType.CLIENT) {
			return generateModuleDependencyForTypeEJB(module.getGroupId(), module.getArtifactId(), module.getVersion());

		} else if (module.getType() == ModuleType.VIEW) {
			return generateModuleDependencyForTypeWAR(module.getGroupId(), module.getArtifactId(), module.getVersion(), contextRoot);
		}
		
		return null;
	}
	
	protected Dependency generateModuleDependencyForTypeJAR(String groupId, String artifactId, String version) {
		return DependencyUtil.createDependency(groupId, artifactId, version, "compile", "jar");
	}

	protected Dependency generateModuleDependencyForTypeEJB(String groupId, String artifactId, String version) {
		return DependencyUtil.createDependency(groupId, artifactId, version, "compile", "ejb");
	}

	protected Dependency generateModuleDependencyForTypeWAR(String groupId, String artifactId, String version, String contextRoot) {
		return DependencyUtil.createDependency(groupId, artifactId, version, "compile", "war");
	}
	
	
	protected List<Dependency> generateModuleDependenciesForEARPlugin() {
		return generateModuleDependenciesForEARPlugin(context.getProject());
	}
	
	protected List<Dependency> generateModuleDependenciesForEARPlugin(Project project) {
		List<Dependency> dependencies = new ArrayList<Dependency>();
		Application application = context.getApplication();
		dependencies.addAll(generateModuleDependenciesForEARPlugin(project, application));
		return dependencies;
	}

	protected List<Dependency> generateModuleDependenciesForEARPlugin(Project project, Application application) {
		List<Dependency> dependencies = new ArrayList<Dependency>();
		visitedConfigurations.clear();
//		Set<Module> projectModules = ProjectUtil.getDeployableModules(project, true);
//		Set<Module> applicationModules = ApplicationUtil.getModules(application);
//		List<Dependency> applicationDependencies = ApplicationUtil.getDependencies(application);
//		buf.addAll(generateModuleDependenciesForFromModules(application, projectModules));
//		buf.addAll(generateModuleDependenciesForFromModules(application, applicationModules));
//		buf.addAll(generatePomDependenciesFromDependencies(application, applicationDependencies));
		dependencies.addAll(generateModuleDependenciesFromConfigurations(application.getConfiguration()));
		
		dependencies = DependencyUtil.removeDuplicateDependencies(dependencies);
		//List<Dependency> d0 = generateModuleDependenciesFromConfigurations(application.getConfiguration());
		//List<Dependency> d1 = filterDependenciesByArtifactId(dependencies, "aries-data-layer");
		//List<Dependency> d2 = filterDependenciesByArtifactId(dependencies, "common-data");
		return dependencies;
	}

	protected List<Dependency> generateModuleDependenciesFromConfigurations(Configuration configuration) {
		List<Dependency> dependencies = new ArrayList<Dependency>();
		if (isConfigurationRequired(configuration)) {
			dependencies.addAll(generateModuleDependenciesFromConfiguration(configuration));
			List<Configuration> configurations = configuration.getConfigurations();
			Iterator<Configuration> iterator = configurations.iterator();
			while (iterator.hasNext()) {
				Configuration childConfiguration = iterator.next();
				dependencies.addAll(generateModuleDependenciesFromConfigurations(childConfiguration));
			}	
		}
		return dependencies;
	}
	
	protected List<Dependency> generateModuleDependenciesFromConfiguration(Configuration configuration) {
//		if (configuration.getArtifactId().startsWith("aries-platform-model-layer"))
//			System.out.println();
//		System.out.println(configuration.getArtifactId());
		List<Dependency> dependencies = ConfigurationUtil.getTransitiveDependencies(configuration);
//		Iterator<Dependency> iterator = dependencies.iterator();
//		while (iterator.hasNext()) {
//			Dependency dependency = (Dependency) iterator.next();
//			if (dependency.getGroupId().contains("aries"))
//				System.out.println("   "+dependency.getArtifactId()+" "+dependency.getType());
//		}
		return dependencies;
	}

	
//	protected void appendConfigurationsToMap(Map<String, Configuration> configurationMap, List<Configuration> moduleConfigurations) {
//		configurationMap.putAll(createConfigurationMap(moduleConfigurations));
//		Iterator<Configuration> iterator = moduleConfigurations.iterator();
//		while (iterator.hasNext()) {
//			Configuration configuration = iterator.next();
//			String configurationKey = ConfigurationUtil.getConfigurationKey(configuration);
//			configurationMap.get(configurationKey);
//		}
//	}
	
	protected Map<String, Configuration> printConfigurations(List<Configuration> moduleConfigurations, int indent) {
		Map<String, Configuration> map = new HashMap<String, Configuration>();
		Iterator<Configuration> iterator = moduleConfigurations.iterator();
		while (iterator.hasNext()) {
			Configuration configuration = iterator.next();
			//System.out.print(">>> ");
			for (int i=0; i < indent; i++)
				System.out.print(" ");
			System.out.println(configuration.getGroupId()+" - "+configuration.getArtifactId());
			map.put(configuration.getArtifactId(), configuration);
			map.putAll(printConfigurations(configuration.getConfigurations(), indent+4));
		}
		return map;
	}

	protected Map<String, Configuration> createConfigurationMap(List<Configuration> configurations) {
		Map<String, Configuration> map = new HashMap<String, Configuration>();
		Iterator<Configuration> iterator = configurations.iterator();
		while (iterator.hasNext()) {
			Configuration configuration = (Configuration) iterator.next();
			String key = ConfigurationUtil.getConfigurationKey(configuration);
			map.put(key, configuration);
		}
		return map;
	}

//	protected List<String> generateModulesForEARPlugin() {
//		return generateModulesForEARPlugin(context.getProject(), localDependencies);
//	}
//	
//	protected List<String> generateModulesForEARPlugin(Project project, List<Dependency> dependencies) {
//		firstGenerated = true;
//		visitedConfigurations.clear();
//		List<String> buf = new ArrayList<String>();
//		buf.addAll(generateModulesForEARPlugin(dependencies));
//		return buf;
//	}

	protected List<String> generateModulesForEARPlugin(List<Dependency> dependencies) {
		List<String> buf = new ArrayList<String>();
		Iterator<Dependency> iterator = dependencies.iterator();
		while (iterator.hasNext()) {
			Dependency dependency = iterator.next();
			List<String> output = generateModulesForEARPlugin(dependency);
			if (output != null)
				buf.addAll(output);
		}
		return buf;
	}
	
	protected List<String> generateModulesForEARPlugin(Dependency dependency) {
		List<String> buf = new ArrayList<String>();
		DependencyType type = dependency.getType();
		DependencyScope scope = dependency.getScope();
		if (scope == DependencyScope.TEST)
			return null;
		if (type == DependencyType.TEST_JAR)
			return null;
		if (type == DependencyType.POM)
			return null;
		if (!firstGenerated)
			buf.add("");

		String groupId = dependency.getGroupId();
		String artifactId = dependency.getArtifactId();
		String moduleKey = groupId + "." + artifactId;
		if (visitedModules.contains(moduleKey))
			return null;
			
		String contextRoot = context.getApplication().getContextRoot();
		if (StringUtils.isEmpty(contextRoot))
			contextRoot = context.getApplication().getName().toLowerCase();

		if (type == DependencyType.WAR) {
			buf.add("<webModule>");
			buf.add("	<groupId>"+groupId+"</groupId>");
			buf.add("	<artifactId>"+artifactId+"</artifactId>");
			buf.add("	<contextRoot>"+contextRoot+"</contextRoot>");
			buf.add("	<unpack>true</unpack>");
			buf.add("</webModule>");
		
		} else if (type == DependencyType.EJB) {
			buf.add("<ejbModule>");
			buf.add("	<groupId>"+groupId+"</groupId>");
			buf.add("	<artifactId>"+artifactId+"</artifactId>");
			buf.add("	<unpack>true</unpack>");
			//buf.add("	<bundleDir>lib</bundleDir>");
			buf.add("</ejbModule>");
			
		} else if (type == DependencyType.JAR) {
			buf.add("<jarModule>");
			buf.add("	<groupId>"+dependency.getGroupId()+"</groupId>");
			buf.add("	<artifactId>"+dependency.getArtifactId()+"</artifactId>");
			
//			if (artifactId.equals("admin-model"))
//				System.out.println();
			
			boolean isLocalDependency = false;
			String dependencyId = groupId + "." + artifactId;
			Set<Module> modules = localModules;
			Iterator<Module> iterator = modules.iterator();
			while (iterator.hasNext()) {
				Module module = iterator.next();
				String moduleId = module.getGroupId() + "." + module.getArtifactId();
				if (moduleId.equals(dependencyId)) {
					isLocalDependency = true;
					break;
				}
			}
			buf.add("	<unpack>"+isLocalDependency+"</unpack>");
			if (!isLocalDependency)
				buf.add("	<bundleDir>lib</bundleDir>");
			//else buf.add("	<includeInApplicationXml>true</includeInApplicationXml>");
			buf.add("</jarModule>");
		}
		
		visitedModules.add(moduleKey);
		firstGenerated = false;
		return buf;
	}

	
//	protected List<String> generateModulesFromConfigurations(Configuration configuration, List<Dependency> dependencies) {
//		List<String> output = new ArrayList<String>();
//		if (isConfigurationRequired(configuration)) {
//			output.addAll(generateModulesFromConfiguration(configuration));
//			List<Configuration> configurations = configuration.getConfigurations();
//			Iterator<Configuration> iterator = configurations.iterator();
//			while (iterator.hasNext()) {
//				Configuration childConfiguration = iterator.next();
//				output.addAll(generateModulesFromConfigurations(childConfiguration));
//			}	
//		}
//		return output;
//	}
//
//	protected List<String> generateModulesFromConfiguration(Configuration configuration, List<Dependency> dependencies) {
//		//System.out.println(">>>>>>>"+configuration.getArtifactId());
//		transitiveDependencies.addAll(ConfigurationUtil.getTransitiveDependencies(configuration));
//		Iterator<Dependency> iterator = transitiveDependencies.iterator();
//		List<String> buf = new ArrayList<String>();
//		while (iterator.hasNext()) {
//			Dependency dependency = iterator.next();
//			DependencyType type = dependency.getType();
//			DependencyScope scope = dependency.getScope();
//			if (scope == DependencyScope.TEST)
//				continue;
//			if (type == DependencyType.TEST_JAR)
//				continue;
//			if (type == DependencyType.POM)
//				continue;
//			if (type == DependencyType.EJB) {
//				buf.add("");
//				buf.add("<ejbModule>");
//				buf.add("	<groupId>"+dependency.getGroupId()+"</groupId>");
//				buf.add("	<artifactId>"+dependency.getArtifactId()+"</artifactId>");
//				buf.add("	<unpack>false</unpack>");
//				buf.add("	<bundleDir>lib</bundleDir>");
//				buf.add("</ejbModule>");
//			} else {
//				buf.add("");
//				buf.add("<jarModule>");
//				buf.add("	<groupId>"+dependency.getGroupId()+"</groupId>");
//				buf.add("	<artifactId>"+dependency.getArtifactId()+"</artifactId>");
//				buf.add("	<unpack>false</unpack>");
//				buf.add("	<bundleDir>lib</bundleDir>");
//				buf.add("</jarModule>");
//			}
//		}
//		return new ArrayList<String>();
//		//return buf;
//	}
	
	
//	private boolean firstGenerated;
//	
//	protected List<String> generateModuleDependenciesForFromModules(Application application, List<Module> modules) {
//		List<String> buf = new ArrayList<String>();
//		Iterator<Module> iterator = modules.iterator();
//		for (int i=0; iterator.hasNext();) {
//			Module module = iterator.next();
//			List<String> output = generateModuleDependenciesForFromModule(application, module);
//			if (output != null) {
//				if (!firstGenerated || i > 0)
//					buf.add("");
//				if (output.size() > 0) {
//					firstGenerated = false;
//					buf.addAll(output);
//					i++;
//				}
//			}
//		}
//		return buf;
//	}
//
//	protected List<String> generateModuleDependenciesForFromModule(Application application, Module module) {
//		if (module.getType() == ModuleType.MODEL || 
//			module.getType() == ModuleType.DATA || 
//			module.getType() == ModuleType.CLIENT) {
//			return generateModuleDependencyForTypeJAR(module.getGroupId(), module.getArtifactId(), module.getVersion());
//
//		} else if (module.getType() == ModuleType.SERVICE) {
//			return generateModuleDependencyForTypeEJB(module.getGroupId(), module.getArtifactId(), module.getVersion());
//
//		} else if (module.getType() == ModuleType.VIEW) {
//			return generateModuleDependencyForTypeWAR(module.getGroupId(), module.getArtifactId(), module.getVersion(), application.getContextRoot());
//		}
//		
//		return null;
//	}

//		visitedPomFiles.clear();
//		Configuration configuration = createConfiguration(application);
//		application.setConfiguration(configuration);
//		//Configuration configuration = application.getConfiguration();
//		//List<Configuration> configurations = configuration.getConfigurations();
//		List<Dependency> moduleDependencies = context.getModuleTemplateDependencies(module.getType());
//		configuration.getConfigurations().addAll(createConfigurations(moduleDependencies));
//		initializeConfigurationsFromPomFile(configuration.getConfigurations());
		
//	protected List<String> generatePomDependenciesFromDependencies(Application application, List<Dependency> dependencies) {
//		List<String> buf = new ArrayList<String>();
//		Iterator<Dependency> iterator = dependencies.iterator();
//		for (int i=0; iterator.hasNext();) {
//			Dependency dependency = iterator.next();
//			//List<String> output = generateModuleDependenciesFromDependency(application, dependency);
//			localDependencies.add(DependencyUtil.createDependency(dependency.getGroupId(), dependency.getArtifactId(), dependency.getVersion(), "compile", "jar"));
//			List<String> output = generateModuleDependencyForTypeJAR(dependency.getGroupId(), dependency.getArtifactId(), dependency.getVersion());
//			if (!firstGenerated || i > 0)
//				buf.add("");
//			if (output.size() > 0) {
//				firstGenerated = true;
//				buf.addAll(output);
//				i++;
//			}
//		}
//		return buf;
//	}

//	protected List<String> generateModuleDependencyForTypeJAR(String groupId, String artifactId, String version) {
//		localDependencies.add(DependencyUtil.createDependency(groupId, artifactId, version, "compile", "jar"));
//		return generateModuleDependency(groupId, artifactId, version, "compile", "jar", null);
//	}
//
//	protected List<String> generateModuleDependencyForTypeEJB(String groupId, String artifactId, String version) {
//		localDependencies.add(DependencyUtil.createDependency(groupId, artifactId, version, "compile", "ejb"));
//		return generateModuleDependency(groupId, artifactId, version, "compile", "ejb", null);
//	}
//
//	protected List<String> generateModuleDependencyForTypeWAR(String groupId, String artifactId, String version, String contextRoot) {
//		localDependencies.add(DependencyUtil.createDependency(groupId, artifactId, version, "compile", "war"));
//		return generateModuleDependency(groupId, artifactId, version, "compile", "war", contextRoot);
//	}
//
//	protected List<String> generateModuleDependency(String groupId, String artifactId, String version, String scope, String type, String contextRoot) {
//		List<String> buf = new ArrayList<String>();
//		if (type.equals("jar")) {
//			buf.add("<jarModule>");
//			buf.add("	<groupId>"+groupId+"</groupId>");
//			buf.add("	<artifactId>"+artifactId+"</artifactId>");
//			buf.add("	<includeInApplicationXml>true</includeInApplicationXml>");
//			buf.add("	<unpack>true</unpack>");
//			buf.add("</jarModule>");
//
//		} else if (type.equals("ejb")) {
//			buf.add("<ejbModule>");
//			buf.add("	<groupId>"+groupId+"</groupId>");
//			buf.add("	<artifactId>"+artifactId+"</artifactId>");
//			buf.add("	<unpack>true</unpack>");
//			buf.add("</ejbModule>");
//
//		} else if (type.equals("war")) {
//			buf.add("<webModule>");
//			buf.add("	<groupId>"+groupId+"</groupId>");
//			buf.add("	<artifactId>"+artifactId+"</artifactId>");
//			if (contextRoot != null)
//				buf.add("	<contextRoot>"+contextRoot+"</contextRoot>");
//			buf.add("	<unpack>true</unpack>");
//			buf.add("</webModule>");
//		}
//		return buf;
//	}

	protected boolean isConfigurationRequired(Configuration configuration) {
		String artifactId = configuration.getArtifactId();
		if (visitedConfigurations.contains(artifactId))
			return false;
		visitedConfigurations.add(artifactId);
		if (artifactId.startsWith("aries") && artifactId.endsWith("test"))
			return false;
		if (artifactId.startsWith("aries"))
			return true;
		Project project = context.getProject();
		if (artifactId.startsWith(project.getName()))
			return true;
		Application application = context.getApplication();
		if (artifactId.startsWith(application.getName()))
			return true;
		if (EARModuleSettings.getRequiredConfigurationList().contains(artifactId))
			return true;
		return false;
	}
	
//	protected List<String> generatePomDependenciesFromConfigurations(Configuration configuration) {
//		List<String> output = new ArrayList<String>();
//		if (isConfigurationRequired(configuration)) {
//			output.addAll(generatePomDependenciesFromConfiguration(configuration));
//			List<Configuration> configurations = configuration.getConfigurations();
//			Iterator<Configuration> iterator = configurations.iterator();
//			while (iterator.hasNext()) {
//				Configuration childConfiguration = iterator.next();
//				output.addAll(generatePomDependenciesFromConfigurations(childConfiguration));
//			}	
//		}
//		return output;
//	}
//	
//	protected List<String> generatePomDependenciesFromConfiguration(Configuration configuration) {
//		//System.out.println(">>>>>>>"+configuration.getArtifactId());
//		transitiveDependencies.addAll(ConfigurationUtil.getTransitiveDependencies(configuration));
//		Iterator<Dependency> iterator = transitiveDependencies.iterator();
//		List<String> buf = new ArrayList<String>();
//		while (iterator.hasNext()) {
//			Dependency dependency = iterator.next();
//			DependencyType type = dependency.getType();
//			DependencyScope scope = dependency.getScope();
//			if (scope == DependencyScope.TEST)
//				continue;
//			if (type == DependencyType.TEST_JAR)
//				continue;
//			if (type == DependencyType.POM)
//				continue;
//			if (type == DependencyType.EJB) {
//				buf.add("");
//				buf.add("<ejbModule>");
//				buf.add("	<groupId>"+dependency.getGroupId()+"</groupId>");
//				buf.add("	<artifactId>"+dependency.getArtifactId()+"</artifactId>");
//				buf.add("	<unpack>false</unpack>");
//				buf.add("	<bundleDir>lib</bundleDir>");
//				buf.add("</ejbModule>");
//			} else {
//				buf.add("");
//				buf.add("<jarModule>");
//				buf.add("	<groupId>"+dependency.getGroupId()+"</groupId>");
//				buf.add("	<artifactId>"+dependency.getArtifactId()+"</artifactId>");
//				buf.add("	<unpack>false</unpack>");
//				buf.add("	<bundleDir>lib</bundleDir>");
//				buf.add("</jarModule>");
//			}
//		}
//		return new ArrayList<String>();
//		//return buf;
//	}

	
	public List<Configuration> createConfigurations(List<Dependency> dependencies) {
		List<Configuration> list = new ArrayList<Configuration>();
		Iterator<Dependency> iterator = dependencies.iterator();
		while (iterator.hasNext()) {
			Dependency dependency = iterator.next();
			Configuration configuration = createConfiguration(dependency);
			list.add(configuration);
		}
		return list;
	}

	public static Configuration createConfiguration(Application application) {
		Configuration configuration = new Configuration();
		configuration.setName(application.getName());
		configuration.setGroupId(application.getGroupId());
		configuration.setArtifactId(application.getArtifactId());
		configuration.setVersion(application.getVersion());
		configuration.setNamespace(application.getNamespace());
		configuration.setDescription("Configuration for application: "+application.getName());
		return configuration;
	}

	public static Configuration createConfiguration(Dependency dependency) {
		Configuration configuration = new Configuration();
		configuration.setName(dependency.getName());
		configuration.setGroupId(dependency.getGroupId());
		configuration.setArtifactId(dependency.getArtifactId());
		configuration.setVersion(dependency.getVersion());
		configuration.setDescription("Configuration for: "+dependency.getGroupId()+"."+dependency.getArtifactId());
		return configuration;
	}

	public static Configuration createConfiguration(org.apache.maven.model.Dependency dependency) {
		Configuration configuration = new Configuration();
		configuration.setName(dependency.getGroupId());
		configuration.setGroupId(dependency.getGroupId());
		configuration.setArtifactId(dependency.getArtifactId());
		configuration.setVersion(dependency.getVersion());
		configuration.setDescription("Configuration for: "+dependency.getGroupId()+"."+dependency.getArtifactId());
		return configuration;
	}

	public void initializeConfigurationsFromPomFile(List<Configuration> configurations) {
		Iterator<Configuration> iterator = configurations.iterator();
		while (iterator.hasNext()) {
			Configuration configuration = iterator.next();
			if (isConfigurationRequired(configuration)) {
				initializeConfigurationFromPomFile(configuration);
				initializeConfigurationsFromPomFile(configuration.getConfigurations());
			}
		}
	}

	//assuming both pom and bom exist
	public void initializeConfigurationFromPomFile(Configuration configuration) {
		String artifactId = configuration.getArtifactId();
		String fileName = getPomFileName(artifactId);
		if (visitedPomFiles.contains(fileName))
			return;

		visitedPomFiles.add(fileName);
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(fileName);
		} catch (FileNotFoundException e) {
			fileReader = null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		if (fileReader == null) {
			try {
//				if (artifactId.startsWith("aries-"))
//					artifactId = artifactId.substring(6);
				if (artifactId.endsWith("bom"))
					fileName = getBomFileName(artifactId);
				else fileName = getPomFileName(artifactId);
				fileReader = new FileReader(fileName);
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
		}

//		if (artifactId.equals("jboss-seam"))
//			System.out.println();
//		if (dependency.getArtifactId().startsWith("jboss-seam"))
//			System.out.println();
		
		try {
			MavenXpp3Reader reader = new MavenXpp3Reader();
			Model model = reader.read(fileReader);
			//MavenProject project = new MavenProject(model);
			//model.getDependencies();
			//project.getDependencies();
			//project.getProperties();
//			if (model.getArtifactId().equals("aries-platform-model-layer"))
//				System.out.println();
			configuration.getConfigurations().addAll(accumulateConfigurations(model));
			configuration.getDependencies().addAll(accumulateDependencies(model));
			configuration.getProperties().addAll(accumulateProperties(model));
			
		} catch (Exception e) {
			throw new RuntimeException("\nProblem reading model: "+e.getMessage()+" from "+fileName);
		}
		
		try {
			fileReader = new FileReader(fileName);
		} catch (FileNotFoundException e) {
			fileReader = null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		if (fileReader == null) {
			try {
//				if (artifactId.startsWith("aries-"))
//					artifactId = artifactId.substring(6);
				if (artifactId.endsWith("bom"))
					fileName = getBomFileName(artifactId);
				else fileName = getPomFileName(artifactId);
				fileReader = new FileReader(fileName);
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
		
		try {
			MavenXpp3Reader reader = new MavenXpp3Reader();
			Model model = reader.read(fileReader);
			//MavenProject project = new MavenProject(model);
			//model.getDependencies();
			//project.getDependencies();
			//project.getProperties();
			
			//TODO - need this maybe? or not?
			//mergeDependencies(configuration, model);
			
		} catch (Exception e) {
			throw new RuntimeException("\nProblem reading model: "+e.getMessage()+" from "+fileName);
		}
	}

	protected static void mergeDependencies(Configuration configuration, Model model) {
		List<Dependency> dependencies = configuration.getDependencies();
		Iterator<Dependency> iterator = dependencies.iterator();
		while (iterator.hasNext()) {
			Dependency dependency = iterator.next();
			if (dependency.getType() != DependencyType.POM) {
				String version = getManagedDependencyVersion(dependency, model);
				if (version == null) {
					//ignore for now
					//System.out.println("ManagedDependency not found: "+dependency.getGroupId()+"."+dependency.getArtifactId());
				}
			}
		}
	}

	protected static String getManagedDependencyVersion(Dependency dependency, Model model) {
//		if (dependency.getArtifactId().startsWith("jboss-seam"))
//			System.out.println();
		String dependencyType = dependency.getType().toString().toLowerCase();
		DependencyManagement dependencyManagement = model.getDependencyManagement();
		List<org.apache.maven.model.Dependency> dependencies = dependencyManagement.getDependencies();
		Iterator<org.apache.maven.model.Dependency> iterator = dependencies.iterator();
		while (iterator.hasNext()) {
			org.apache.maven.model.Dependency managedDependency = iterator.next();
			//managedDependency.getType().equals(dependencyType)) {
			if (managedDependency.getGroupId().equals(dependency.getGroupId()) &&
				managedDependency.getArtifactId().equals(dependency.getArtifactId())) {
					String version = managedDependency.getVersion();
					if (version.startsWith("$")) {
						String key = version.substring(2, version.length()-1); 
						version = model.getProperties().getProperty(key);
					}
					return version;

			} else if (managedDependency.getType().equals("pom")) {
				String artifactId = managedDependency.getArtifactId();
				String fileName = getBomFileName(artifactId);

				FileReader fileReader = null;
				try {
					fileReader = new FileReader(fileName);
				} catch (FileNotFoundException e) {
					fileReader = null;
				} catch (Exception e) {
					throw new RuntimeException(e);
				}

				if (fileReader == null) {
					try {
//						if (artifactId.startsWith("aries-"))
//							System.out.println();
						if (artifactId.endsWith("bom"))
							fileName = getBomFileName(artifactId);
						else fileName = getPomFileName(artifactId);
						fileReader = new FileReader(fileName);
					} catch (FileNotFoundException e) {
						throw new RuntimeException(e);
					}
				}

				try {
					MavenXpp3Reader reader = new MavenXpp3Reader();
					Model model2 = reader.read(fileReader);
					String version = getManagedDependencyVersion(dependency, model2);
					if (version != null)
						return version;
					
				} catch (Exception e) {
					//Just ignore these - just unwanted models not found
					//System.out.println("Warning: Access to model ignored: "+fileName+" - "+e.getMessage());
					//throw new RuntimeException("\nProblem reading model: "+e.getMessage()+" from "+fileName);
				}
			}
		}

		return null;
	}

	public static List<Configuration> accumulateConfigurations(Model model) {
		List<Configuration> list = new ArrayList<Configuration>();
		List<org.apache.maven.model.Dependency> dependencies = model.getDependencies();
		Iterator<org.apache.maven.model.Dependency> iterator = dependencies.iterator();
		while (iterator.hasNext()) {
			org.apache.maven.model.Dependency dependency = iterator.next();
			if (dependency.getType().equals("pom")) {
				Configuration configuration = createConfiguration(dependency);
				//configuration.setOwner(owner);
				list.add(configuration);
			}
		}
		return list;
	}

	public static List<Dependency> accumulateDependencies(Model model) {
//		if (model.getArtifactId().equals("richfaces-4"))
//			System.out.println();
		//System.out.println(">>>>>>"+model.getArtifactId());
		List<Dependency> list = new ArrayList<Dependency>();
		List<org.apache.maven.model.Dependency> pomDependencies = model.getDependencies();
		Iterator<org.apache.maven.model.Dependency> iterator = pomDependencies.iterator();
		while (iterator.hasNext()) {
			org.apache.maven.model.Dependency pomDependency = iterator.next();
			if (pomDependency.getType().equals("pom") == false) {
				Dependency dependency = createDependency(pomDependency);
				if (dependency.getVersion() == null) {
					String version = getManagedDependencyVersion(dependency, model);
//					if (version == null)
//						System.out.println();
					dependency.setVersion(version);
				}
				list.add(dependency);
			} else {
				List<Dependency> childDependencies = accumulateDependencies(pomDependency);
				list.addAll(childDependencies);
			}
		}
		return list;
	}

	//assuming both pom and bom exist
	public static List<Dependency> accumulateDependencies(org.apache.maven.model.Dependency dependency) {
		List<Dependency> list = new ArrayList<Dependency>();
		String artifactId = dependency.getArtifactId();
		String fileName = null;
		if (artifactId.endsWith("bom"))
			fileName = getBomFileName(artifactId);
		else fileName = getPomFileName(artifactId);
		
//		if (visitedPomFiles.contains(fileName))
//			return;
//		visitedPomFiles.add(fileName);
		
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(fileName);
		} catch (FileNotFoundException e) {
			fileReader = null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		if (fileReader == null) {
			try {
//				if (artifactId.startsWith("aries-"))
//					artifactId = artifactId.substring(6);
				if (artifactId.endsWith("bom"))
					fileName = getBomFileName(artifactId);
				else fileName = getPomFileName(artifactId);
				fileReader = new FileReader(fileName);
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
		}

//		if (artifactId.startsWith("jboss-seam"))
//			System.out.println();

		try {
			MavenXpp3Reader reader = new MavenXpp3Reader();
			Model model = reader.read(fileReader);
//			if (model.getArtifactId().equals("javax"))
//				System.out.println();
			list.addAll(accumulateDependencies(model));
			return list;
			
		} catch (Exception e) {
			throw new RuntimeException("\nProblem reading model: "+e.getMessage()+" from "+fileName);
		}
	}
	
	public static Dependency createDependency(org.apache.maven.model.Dependency pomDependency) {
		//if (pomDependency.getArtifactId().startsWith("tx-manager-service"))
		//	System.out.println();
		Dependency dependency = new Dependency();
		dependency.setName(pomDependency.getGroupId());
		dependency.setGroupId(pomDependency.getGroupId());
		dependency.setArtifactId(pomDependency.getArtifactId());
		dependency.setVersion(pomDependency.getVersion());
		dependency.setDescription("Dependency for: "+getDependencyKey(pomDependency));
		DependencyType dependencyType = DependencyUtil.getDependencyType(pomDependency.getType());
		DependencyScope dependencyScope = DependencyUtil.getDependencyScope(pomDependency.getScope());
		if (dependencyType == null)
			dependencyType = DependencyType.JAR;
		if (dependencyScope == null)
			dependencyScope = DependencyScope.COMPILE;
		dependency.setType(dependencyType);
		dependency.setScope(dependencyScope);
		return dependency;
	}

	public static List<Property> accumulateProperties(Model model) {
		List<Property> list = new ArrayList<Property>();
		return list;
	}


	public Set<ModelDependency> initializePomManagedDependencies(Module module) {
		managedDependencies = new LinkedHashSet<ModelDependency>();
		Iterator<Configuration> iterator = configurations.iterator();
		while (iterator.hasNext()) {
			Configuration configuration = iterator.next();
			if (!configuration.getArtifactId().endsWith("test"))
				managedDependencies.add(createPomDependency(configuration));
		}
		return managedDependencies;
	}

	public Set<ModelDependency> initializePomDependencies(Module module) throws Exception {
		Project project = context.getProject();
		//Application application = context.getApplication();
		List<Dependency> dependencies = new ArrayList<Dependency>();
		List<Dependency> tmpList = new ArrayList<Dependency>();
		tmpList.addAll(localDependencies);
		//tmpList.addAll(transitiveDependencies);
		DependencyUtil.sortDependencies(tmpList);
		tmpList = DependencyUtil.removeDuplicateDependencies(tmpList);
		//printDependencies(tmpList);

		//subProject modules first
		List<Project> subProjects = ProjectUtil.getSubProjects(project);
		Iterator<Project> iterator = subProjects.iterator();
		while (iterator.hasNext()) {
			Project subProject = iterator.next();
			dependencies.addAll(DependencyUtil.filterDependencies(tmpList, subProject.getName()));
		}
		
		//main project modules next
		dependencies.addAll(DependencyUtil.filterDependencies(tmpList, project.getName()));
		
		//main project application modules next
		Collection<Application> applications = ProjectUtil.getApplications(project);
		Iterator<Application> iterator2 = applications.iterator();
		while (iterator2.hasNext()) {
			Application application = iterator2.next();
			dependencies.addAll(DependencyUtil.filterDependencies(tmpList, application.getArtifactId()));
		}

		//ARIES configuration pom modules next
		dependencies.addAll(DependencyUtil.filterDependencies(tmpList, "org.aries.pom"));
		//dependencies.addAll(DependencyUtil.filterDependencies(tmpList, "org.aries"));
		
		//convert to ModelDependency's next
		Set<ModelDependency> modelDependencies = initializePomDependencies(dependencies);
		return modelDependencies;
	}

	public Set<ModelDependency> initializePomDependencies(Collection<Dependency> dependencies) {
		Set<ModelDependency> modelDependencies = new LinkedHashSet<ModelDependency>();
		Iterator<Dependency> iterator = dependencies.iterator();
		while (iterator.hasNext()) {
			Dependency dependency = iterator.next();
			modelDependencies.add(createPomDependency(dependency));
		}
		return modelDependencies;
	}

	protected List<Dependency> filterDependencies(List<Dependency> dependencies, String groupId, String artifactId) {
		Map<String, Module> moduleMap = ProjectUtil.getModulesAsMap(context.getProject());
		List<Dependency> filteredList = new ArrayList<Dependency>();
		Iterator<Dependency> iterator = dependencies.iterator();
		while (iterator.hasNext()) {
			Dependency dependency = iterator.next();
			if (!dependency.getGroupId().startsWith(groupId))
				continue;
			if (!dependency.getArtifactId().startsWith(artifactId))
				continue;
			//Module module = ProjectUtil.getModule(context.getProject(), dependency.getArtifactId());
			if (moduleMap.containsKey(dependency.getArtifactId()))
				continue;
			filteredList.add(dependency);
		}
		return filteredList;
	}
	
	protected List<Dependency> filterDependenciesByGroupId(List<Dependency> dependencies, String groupId) {
		Map<String, Module> moduleMap = ProjectUtil.getModulesAsMap(context.getProject());
//		Iterator<String> iterator2 = moduleMap.keySet().iterator();
//		while (iterator2.hasNext()) {
//			String string = iterator2.next();
//			System.out.println(">>>>>"+string);
//		}
		List<Dependency> filteredList = new ArrayList<Dependency>();
		Iterator<Dependency> iterator = dependencies.iterator();
		while (iterator.hasNext()) {
			Dependency dependency = iterator.next();
			if (!dependency.getGroupId().startsWith(groupId))
				continue;
			//Module module = ProjectUtil.getModule(context.getProject(), dependency.getArtifactId());
			if (moduleMap.containsKey(dependency.getArtifactId()))
				continue;
			filteredList.add(dependency);
		}
		return filteredList;
	}
	
	protected List<Dependency> filterDependenciesByArtifactId(List<Dependency> dependencies, String artifactId) {
		Map<String, Module> moduleMap = ProjectUtil.getModulesAsMap(context.getProject());
		List<Dependency> filteredList = new ArrayList<Dependency>();
		Iterator<Dependency> iterator = dependencies.iterator();
		while (iterator.hasNext()) {
			Dependency dependency = iterator.next();
			if (!dependency.getArtifactId().startsWith(artifactId))
				continue;
			//Module module = ProjectUtil.getModule(context.getProject(), dependency.getArtifactId());
			if (moduleMap.containsKey(dependency.getArtifactId()))
				continue;
			filteredList.add(dependency);
		}
		return filteredList;
	}
	
}
