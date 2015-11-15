package nam.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import nam.model.Module;
import nam.model.Project;
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
	
	protected ModelPom createParentPom(Project project, Module module, ModelPom pom) throws Exception {
		switch (module.getLevel()) {
		case PROJECT_LEVEL: return super.createProjectPomAsParent(pom);
		case GROUP_LEVEL: return super.createProjectAppPomAsParent(pom);
		case APPLICATION_LEVEL: return super.createParentPom(project, module, pom);
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
		buf.putLine3("			<include>**/*.properties</include>");
		buf.putLine3("		</includes>");
		buf.putLine3("	</configuration>");
		buf.putLine3("</plugin>");
		plugins.add(buf.get());
		return plugins;
	}
	
	public Set<ModelDependency> initializePomDependencies(Module module) throws Exception {
		Set<ModelDependency> dependencies = new LinkedHashSet<ModelDependency>();
		dependencies.addAll(getModelModuleDependencies(module));
		
//		if (module.getLevel() == ModuleLevel.APPLICATION) {
//			Application application = context.getApplication();
//			List<Dependency> applicationDependencies = ApplicationUtil.getDependencies(application);
//			dependencies.addAll(createPomDependencies(applicationDependencies));
//		}

		dependencies.add(createPomDependency("org.aries.pom", "aries-platform-model-layer", "0.0.1-SNAPSHOT", "provided", "pom"));
		dependencies.add(createPomDependency("org.aries.pom", "aries-platform-model-layer-test", "0.0.1-SNAPSHOT", "test", "pom"));
		return dependencies;
	}

}
