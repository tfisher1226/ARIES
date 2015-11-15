package nam.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import nam.model.Module;
import nam.model.ModuleType;
import nam.model.util.ProjectUtil;
import aries.codegen.configuration.AbstractPOMBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelDependency;
import aries.generation.model.ModelPom;


public class PomXMLBuilder extends AbstractPOMBuilder {

	public PomXMLBuilder(GenerationContext context) {
		this.context = context;
	}

	public ModelPom build() throws Exception {
		ModelPom modelPom = createPom(context.getProject(), context.getModule());
		return modelPom;
	}

	protected ModelPom createParentPom(Module module, ModelPom pom) throws Exception {
		switch (module.getLevel()) {
		case PROJECT_LEVEL: return super.createProjectPomAsParent(pom);
		case GROUP_LEVEL: return super.createProjectAppPomAsParent(pom);
		case APPLICATION_LEVEL: return super.createApplicationPomAsParent(pom);
		default: return null;
		}
	}
	
	public List<String> initializePomPlugins() {
		List<String> plugins = new ArrayList<String>();
		Buf buf = new Buf();
		buf.putLine3("<plugin>");
		buf.putLine3("	<artifactId>maven-jar-plugin</artifactId>");
		buf.putLine3("	<configuration>");
		buf.putLine3("		<includes>");
		buf.putLine3("			<include>**/*.class</include>");
		buf.putLine3("			<include>**/*.xml</include>");
		buf.putLine3("			<include>**/*.sql</include>");
		buf.putLine3("			<include>**/*.properties</include>");
		buf.putLine3("			<include>META-INF/persistence.xml</include>");
		buf.putLine3("			<include>META-INF/queries.xml</include>");
		buf.putLine3("		</includes>");
		buf.putLine3("	</configuration>");
		buf.putLine3("</plugin>");
		plugins.add(buf.get());
		return plugins;
	}
	
	public Set<ModelDependency> initializePomDependencies(Module module) throws Exception {
		Set<ModelDependency> dependencies = new LinkedHashSet<ModelDependency>();
		dependencies.addAll(getModelModuleDependencies(context.getProject()));
		dependencies.addAll(getDataModuleDependencies(context.getProject()));
		
//		Set<Process> processes = new HashSet<Process>();
//		List<Link> links = new ArrayList<Link>();

		Set<Module> modules = ProjectUtil.getProjectModules(context.getProject(), ModuleType.MODEL);
		Iterator<Module> moduleIterator = modules.iterator();
		while (moduleIterator.hasNext()) {
			Module projectModule = moduleIterator.next();
			dependencies.addAll(getModelModuleDependencies(projectModule));
			dependencies.add(createPomDependency(projectModule));
			dependencies.add(createPomDependency(projectModule, "test", "test-jar"));
		}
		
		//dependencies.addAll(createPomApplicationDependencies(ModuleType.CLIENT));
		//dependencies.addAll(createPomApplicationDependencies("model"));
		dependencies.add(createPomDependency("org.aries.pom", "aries-platform-data-layer", "0.0.1-SNAPSHOT", "provided", "pom"));
		dependencies.add(createPomDependency("org.aries.pom", "aries-platform-data-layer-test", "0.0.1-SNAPSHOT", "test", "pom"));
		//dependencies.add(createPomDependency("org.aries", "tx-manager-api", "0.0.1-SNAPSHOT", "provided", "pom"));
		//dependencies.add(createPomDependency("org.aries", "tx-manager-test-api", "0.0.1-SNAPSHOT", "test", "pom"));
		return dependencies;
	}
	
	public List<ModelDependency> initializePomDependenciesOLD() {
		List<ModelDependency> dependencies = new ArrayList<ModelDependency>();
		dependencies.addAll(createPomProjectDependencies(ModuleType.MODEL));
		//dependencies.addAll(createPomApplicationDependencies("model"));
		//dependencies.addAll(createPomApplicationDependencies("client"));
		dependencies.add(createPomDependency("org.aries", "common-util"));
		dependencies.add(createPomDependency("org.aries", "common-model"));
		dependencies.add(createPomDependency("org.aries", "common-runtime"));
		dependencies.add(createPomDependency("org.aries", "common-data"));
		dependencies.add(createPomDependency("org.aries", "common-data", null, "test", "test-jar"));
		dependencies.add(createPomDependency("org.aries", "common-runtime"));
		dependencies.add(createPomDependency("org.aries", "common-transaction"));
		dependencies.add(createPomDependency("org.aries", "common-jaxb"));
		dependencies.add(createPomDependency("org.hibernate.javax.persistence", "hibernate-jpa-2.0-api"));
		dependencies.add(createPomDependency("org.hibernate", "hibernate-core"));
		dependencies.add(createPomDependency("org.hibernate", "hibernate-entitymanager"));
		dependencies.add(createPomDependency("org.hibernate", "hibernate-validator"));
		dependencies.add(createPomDependency("org.hibernate", "hibernate-ehcache"));
		dependencies.add(createPomDependency("mysql", "mysql-connector-java"));
		dependencies.add(createPomDependency("${jsf.groupId}", "jsf-api"));
		return dependencies;
	}
	
}
