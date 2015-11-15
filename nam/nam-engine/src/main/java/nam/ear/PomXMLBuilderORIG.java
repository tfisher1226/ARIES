package nam.ear;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nam.model.Application;
import nam.model.Configuration;
import nam.model.Dependency;
import nam.model.DependencyScope;
import nam.model.DependencyType;
import nam.model.Module;
import nam.model.ModuleType;
import nam.model.Project;
import nam.model.util.ApplicationUtil;
import nam.model.util.DependencyUtil;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.project.MavenProject;

import aries.codegen.configuration.AbstractPOMBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelDependency;
import aries.generation.model.ModelPom;


public class PomXMLBuilderORIG extends AbstractPOMBuilder {

	private static String POM_FILE_HOME = "/workspace/ARIES/aries/groups";

	private static Set<String> visitedPomFiles = new HashSet<String>();
	
	
	public PomXMLBuilderORIG(GenerationContext context) {
		this.context = context;
	}

	public ModelPom build() throws Exception {
		ModelPom modelPom = createPom(context.getProject(), context.getModule());
		return modelPom;
	}

	public List<String> initializePomPlugins() {
		Configuration configuration = new Configuration();
		List<String> plugins = new ArrayList<String>();
		Buf buf = new Buf();
		buf.putLine3("<plugin>");
		buf.putLine3("	<artifactId>maven-ear-plugin</artifactId>");
		buf.putLine3("	<configuration>");
		buf.putLine3("		<version>5</version>");
		buf.putLine3("		<earSourceDirectory>${basedir}/source/${targetHost}/application</earSourceDirectory>");
		buf.putLines(5, generateDependencyModulesForEARPlugin());
		buf.putLine3("	</configuration>");
		buf.putLine3("</plugin>");
		plugins.add(buf.get());
		return plugins;
	}

	protected List<String> generateDependencyModulesForEARPlugin() {
		return generateDependencyModulesForEARPlugin(context.getProject());
	}
	
	protected List<String> generateDependencyModulesForEARPlugin(Project project) {
		List<String> buf = new ArrayList<String>();
		Application application = context.getApplication();
		Collection<Module> modules = ApplicationUtil.getModules(application);
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = (Module) iterator.next();
			buf.addAll(generateDependencyModulesForEARPlugin(application, module));
		}
		return buf;
	}

	protected List<String> generateDependencyModulesForEARPlugin(Application application, Module module) {
		List<String> buf = new ArrayList<String>();
		if (module.getType() == ModuleType.MODEL || 
				module.getType() == ModuleType.DATA || 
				module.getType() == ModuleType.CLIENT) {
				buf.add("");
				buf.add("<jarModule>");
				buf.add("	<groupId>"+module.getGroupId()+"</groupId>");
				buf.add("	<artifactId>"+module.getArtifactId()+"</artifactId>");
				buf.add("	<includeInApplicationXml>true</includeInApplicationXml>");
				buf.add("	<unpack>true</unpack>");
				buf.add("</jarModule>");

			} else if (module.getType() == ModuleType.SERVICE) {
				buf.add("");
				buf.add("<ejbModule>");
				buf.add("	<groupId>"+module.getGroupId()+"</groupId>");
				buf.add("	<artifactId>"+module.getArtifactId()+"</artifactId>");
				buf.add("	<unpack>true</unpack>");
				buf.add("</ejbModule>");
			
			} else if (module.getType() == ModuleType.VIEW) {
				buf.add("");
				buf.add("<webModule>");
				buf.add("	<groupId>"+module.getGroupId()+"</groupId>");
				buf.add("	<artifactId>"+module.getArtifactId()+"</artifactId>");
				buf.add("	<contextRoot>"+application.getGroupId()+"</contextRoot>");
				buf.add("	<unpack>true</unpack>");
				buf.add("</webModule>");
			}
		
		visitedPomFiles.clear();
		List<Dependency> moduleDependencies = context.getModuleTemplateDependencies(module.getType(), false);
		Iterator<Dependency> iterator = moduleDependencies.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Dependency dependency = iterator.next();
			buf.addAll(generateDependencyModulesForEARPlugin(application, dependency));
		}

		Set<Dependency> transitiveDependencies = getTransitiveDependencies(moduleDependencies);
		iterator = transitiveDependencies.iterator();
		while (iterator.hasNext()) {
			Dependency dependency = iterator.next();
			buf.add("");
			buf.add("<jarModule>");
			buf.add("	<groupId>"+dependency.getGroupId()+"</groupId>");
			buf.add("	<artifactId>"+dependency.getArtifactId()+"</artifactId>");
			buf.add("	<includeInApplicationXml>true</includeInApplicationXml>");
			buf.add("	<unpack>true</unpack>");
			buf.add("</jarModule>");
		}
		
		return buf;
	}

	protected List<String> generateDependencyModulesForEARPlugin(Application application, Dependency dependency) {
		List<String> buf = new ArrayList<String>();
		if (dependency.getType() == DependencyType.JAR || 
			(dependency.getType() == DependencyType.EJB && dependency.getScope() == DependencyScope.TEST)) {
				buf.add("");
				buf.add("<jarModule>");
				buf.add("	<groupId>"+dependency.getGroupId()+"</groupId>");
				buf.add("	<artifactId>"+dependency.getArtifactId()+"</artifactId>");
				buf.add("	<includeInApplicationXml>true</includeInApplicationXml>");
				buf.add("	<unpack>true</unpack>");
				buf.add("</jarModule>");

			} else if (dependency.getType() == DependencyType.EJB) {
				buf.add("");
				buf.add("<ejbModule>");
				buf.add("	<groupId>"+dependency.getGroupId()+"</groupId>");
				buf.add("	<artifactId>"+dependency.getArtifactId()+"</artifactId>");
				buf.add("	<unpack>true</unpack>");
				buf.add("</ejbModule>");
			
			} else if (dependency.getType() == DependencyType.WAR) {
				buf.add("");
				buf.add("<webModule>");
				buf.add("	<groupId>"+dependency.getGroupId()+"</groupId>");
				buf.add("	<artifactId>"+dependency.getArtifactId()+"</artifactId>");
				buf.add("	<contextRoot>"+application.getGroupId()+"</contextRoot>");
				buf.add("	<unpack>true</unpack>");
				buf.add("</webModule>");
			}
		return buf;
	}

	protected List<String> generateModulesForEARPluginOLD(Module module) {
		List<String> buf = new ArrayList<String>();
		Application application = context.getApplication();
		List<Dependency> moduleDependencies = context.getModuleTemplateDependencies(module.getType(), false);
		Iterator<Dependency> iterator = moduleDependencies.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Dependency dependency = iterator.next();

			if (module.getType() == ModuleType.MODEL || 
				module.getType() == ModuleType.DATA || 
				module.getType() == ModuleType.CLIENT || 
				(module.getType() == ModuleType.SERVICE && dependency.getScope() == DependencyScope.TEST)) {
				buf.add("");
				buf.add("<jarModule>");
				buf.add("	<groupId>"+dependency.getGroupId()+"</groupId>");
				buf.add("	<artifactId>"+dependency.getArtifactId()+"</artifactId>");
				buf.add("	<includeInApplicationXml>true</includeInApplicationXml>");
				buf.add("	<unpack>true</unpack>");
				buf.add("</jarModule>");

			} else if (module.getType() == ModuleType.SERVICE) {
				buf.add("");
				buf.add("<ejbModule>");
				buf.add("	<groupId>"+dependency.getGroupId()+"</groupId>");
				buf.add("	<artifactId>"+dependency.getArtifactId()+"</artifactId>");
				buf.add("	<unpack>true</unpack>");
				buf.add("</ejbModule>");
			
			} else {
				buf.add("");
				buf.add("<webModule>");
				buf.add("	<groupId>"+dependency.getGroupId()+"</groupId>");
				buf.add("	<artifactId>"+dependency.getArtifactId()+"</artifactId>");
				buf.add("	<contextRoot>"+application.getContextRoot()+"</contextRoot>");
				buf.add("	<unpack>true</unpack>");
				buf.add("</webModule>");
			}
		}

		Set<Dependency> transitiveDependencies = getTransitiveDependencies(moduleDependencies);
		iterator = transitiveDependencies.iterator();
		while (iterator.hasNext()) {
			Dependency dependency = iterator.next();
			buf.add("");
			buf.add("<jarModule>");
			buf.add("	<groupId>"+dependency.getGroupId()+"<groupId>");
			buf.add("	<artifactId>"+dependency.getArtifactId()+"<artifactId>");
			buf.add("	<includeInApplicationXml>true</includeInApplicationXml>");
			buf.add("	<unpack>true</unpack>");
			buf.add("</jarModule>");
		}
		
		return buf;
	}

	public static String getPomFileName(String artifactId) {
		return POM_FILE_HOME + "/" + artifactId + "-pom.xml";
	}
	
	public static Set<Dependency> getTransitiveDependencies(List<Dependency> moduleDependencies) {
		Set<Dependency> dependencies = new HashSet<Dependency>();
		Iterator<Dependency> iterator = moduleDependencies.iterator();
		while (iterator.hasNext()) {
			Dependency moduleDependency = iterator.next();
			if (moduleDependency.getType() == DependencyType.POM) {
				String artifactId = moduleDependency.getArtifactId();
				dependencies.addAll(getDependenciesFromPomFile(artifactId));
			} else {
				System.out.println();
			}
		}
		return dependencies;
	}

	public static Set<Dependency> getDependenciesFromPomFile(String artifactId) {
		String fileName = getPomFileName(artifactId);
		if (visitedPomFiles.contains(fileName))
			return new HashSet<Dependency>();

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
				if (artifactId.startsWith("aries-"))
					artifactId = artifactId.substring(6);
				fileName = getPomFileName(artifactId);
				fileReader = new FileReader(fileName);
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
		
		try {
			if (artifactId.equals("aries-platform-base"))
				System.out.println();
			MavenXpp3Reader reader = new MavenXpp3Reader();
			Model model = reader.read(fileReader);
			MavenProject project = new MavenProject(model);
			Set<Dependency> dependencies = accumulateTransitiveDependencies(project);
			return dependencies;
		} catch (Exception e) {
			throw new RuntimeException("\nProblem reading model: "+e.getMessage()+" from "+fileName);
		}
	}

	public static Set<Dependency> accumulateTransitiveDependencies(MavenProject project) {
		Set<Dependency> dependencies = new HashSet<Dependency>();
		Iterator<org.apache.maven.model.Dependency> iterator = project.getDependencies().iterator();
		while (iterator.hasNext()) {
			org.apache.maven.model.Dependency dependency = iterator.next();
			if (dependency.getType().equals("pom")) {
				String artifactId = dependency.getArtifactId();
				dependencies.addAll(getDependenciesFromPomFile(artifactId));
			} else {
				Dependency artifact = new Dependency();
				artifact.setGroupId(dependency.getGroupId());
				artifact.setArtifactId(dependency.getArtifactId());
				artifact.setVersion(dependency.getVersion());
				if (dependency.getScope() != null)
					artifact.setScope(DependencyUtil.getDependencyScope(dependency.getScope()));
				artifact.setType(DependencyUtil.getDependencyType(dependency.getType()));
				dependencies.add(artifact);
			}
		}
		return dependencies;
	}

	public Set<ModelDependency> initializePomDependencies() {
		Set<ModelDependency> dependencies = new HashSet<ModelDependency>();
		
		/*
		dependencies.addAll(createPomApplicationDependencies(ModuleType.MODEL));
		dependencies.addAll(createPomApplicationDependencies(ModuleType.CLIENT));
		//dependencies.addAll(createPomApplicationDependencies("model"));
		//dependencies.addAll(createPomApplicationDependencies("client"));
		
		dependencies.add(createPomDependency("org.aries.pom", "aries-platform-view-layer", "0.0.1-SNAPSHOT", "provided", "pom"));
		//dependencies.add(createPomDependency("org.aries.pom", "aries-platform-view-layer-test", "0.0.1-SNAPSHOT", "test", "pom"));
		dependencies.add(createPomDependency("org.aries.pom", "aries-platform-client-layer", "0.0.1-SNAPSHOT", "provided", "pom"));
		dependencies.add(createPomDependency("org.aries.pom", "aries-platform-client-layer-test", "0.0.1-SNAPSHOT", "test", "pom"));
		dependencies.add(createPomDependency("org.aries", "tx-manager-api", "0.0.1-SNAPSHOT", "provided", "pom"));

		dependencies.add(createPomDependency("org.aries", "notification-model"));
		dependencies.add(createPomDependency("org.aries", "notification-client"));
		dependencies.add(createPomDependency("org.aries", "aries-registry"));
		dependencies.add(createPomDependency("org.aries", "common-util"));
		dependencies.add(createPomDependency("org.aries", "common-net"));
		dependencies.add(createPomDependency("org.aries", "common-model"));
		dependencies.add(createPomDependency("org.aries", "common-runtime"));
		dependencies.add(createPomDependency("org.aries", "common-client"));
		dependencies.add(createPomDependency("org.aries", "common-view"));
		// J2EE 
		dependencies.add(createPomDependency("javax.ejb", "ejb-api"));
		// JSF 
		dependencies.add(createPomDependency("com.sun.faces", "jsf-api"));
		// SEAM 
		deeendencies.add(createPomDependency("org.jboss.seam", "jboss-seam-remoting"));
		dependencies.add(createPomDependency("org.jboss.seam", "jboss-seam-excel"));
		dependencies.add(createPomDependency("org.jboss.seam", "jboss-seam-pdf"));
		// RICHFACES 
		dependencies.add(createPomDependency("org.richfaces.ui", "richfaces-ui"));
		dependencies.add(createPomDependency("org.richfaces.framework", "richfaces-api"));
		dependencies.add(createPomDependency("org.richfaces.framework", "richfaces-impl-jsf2"));
		dependencies.add(createPomDependency("org.richfaces.samples", "glassX"));
		// OTHER 
		dependencies.add(createPomDependency("org.hibernate", "hibernate-validator"));
		*/
		
		return dependencies;
	}
	
}
