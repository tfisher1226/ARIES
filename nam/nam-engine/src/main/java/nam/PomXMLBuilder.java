package nam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import nam.model.Application;
import nam.model.Module;
import nam.model.ModuleType;
import nam.model.Project;
import nam.model.Property;
import nam.model.util.ApplicationUtil;
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

	public ModelPom buildModelPom(Project project, Module module) throws Exception {
		ModelPom modelPom = createPom(project, module);
		return modelPom;
	}

	protected ModelPom createParentPom(Module module, ModelPom pom) throws Exception {
		ModelPom parentPom = createParentPom2(module, pom);
		parentPom.setModules(initializePomModules(module));
		return parentPom;
	}
	
	protected ModelPom createParentPom2(Module module, ModelPom pom) throws Exception {
		switch (module.getLevel()) {
		default: 
		case PROJECT_LEVEL: return createAriesParentPomAsParent();
		case GROUP_LEVEL: return super.createProjectAppPomAsParent(pom);
		case APPLICATION_LEVEL: return createProjectPomAsParent(pom);
		}
	}

	protected List<Property> initializePomProperties() throws Exception {
		List<Property> properties = new ArrayList<Property>();
		String projectName = context.getProjectName();
		properties.add(createProperty(projectName+".version", context.getProjectVersion()));
		properties.add(createProperty(projectName+".model.home", "../model"));
		properties.add(createProperty(projectName+".deploy.exploded", "true"));
		return properties;
	}

	public List<String> initializePomPlugins() {
		List<String> plugins = new ArrayList<String>();
		Buf buf = new Buf();
		buf.putLine3("<plugin>");
		buf.putLine3("	<artifactId>maven-jar-plugin</artifactId>");
		buf.putLine3("	<configuration>");
		buf.putLine3("		<includes>");
		buf.putLine3("			<include>**/*.xml</include>");
		buf.putLine3("			<include>**/*.properties</include>");
		buf.putLine3("		</includes>");
		buf.putLine3("	</configuration>");
		buf.putLine3("</plugin>");
		plugins.add(buf.get());
		return plugins;
	}
	
	public Set<ModelDependency> initializePomManagedDependencies(Module module) {
		
//		if (module.getName().equals("bookshop2-supplier"))
//			System.out.println();
			
		Set<ModelDependency> dependencies = new LinkedHashSet<ModelDependency>();
		switch (module.getLevel()) {
		case PROJECT_LEVEL: {
			Collection<Module> modules = ProjectUtil.getProjectModules(context.getProject());
			Collection<Application> applications = ProjectUtil.getApplications(context.getProject());
			dependencies.addAll(initializePomManagedDependenciesFromModules(modules));
			dependencies.addAll(initializePomManagedDependenciesFromApplications(module, applications));
			return dependencies;
		}
		case GROUP_LEVEL: {
			Collection<Module> modules = ProjectUtil.getApplicationModules(context.getProject());
			dependencies.addAll(initializePomManagedDependenciesFromModules(modules));
			return dependencies;
		}
		case APPLICATION_LEVEL: {
			Collection<Module> projectModules = ProjectUtil.getProjectModules(context.getProject(), ModuleType.MODEL);
			Collection<Module> applicationModules = ApplicationUtil.getModules(context.getApplication());
			dependencies.addAll(initializePomManagedDependenciesFromModules(projectModules));
			dependencies.addAll(initializePomManagedDependenciesFromModules(applicationModules));
			return dependencies;
		}
		default: 
			return dependencies;
		}
	}
	
	public List<ModelDependency> initializePomManagedDependenciesFromModules(Collection<Module> modules) {
		List<ModelDependency> dependencies = new ArrayList<ModelDependency>();
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			ModuleType moduleType = module.getType();
			if (moduleType == ModuleType.POM)
				continue;
			if (moduleType == ModuleType.EAR)
				continue;
			switch (module.getLevel()) {
			case PROJECT_LEVEL:
				if (moduleType != ModuleType.POM) {
					dependencies.add(createPomDependency(module));
					dependencies.add(createPomDependency(module, "test", "test-jar"));
				}
				break;
			case GROUP_LEVEL: 
				//System.out.println();
			case APPLICATION_LEVEL:
				if (moduleType != ModuleType.POM) {
					dependencies.add(createPomDependency(module));
					dependencies.add(createPomDependency(module, "test", "test-jar"));
				}
				break;
			}
		}
		return dependencies;
	}

	public List<ModelDependency> initializePomManagedDependenciesFromApplications(Module pomModule, Collection<Application> applications) {
		List<ModelDependency> dependencies = new ArrayList<ModelDependency>();
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			if (!application.getGroupId().equals(pomModule.getGroupId()) ||
				!application.getArtifactId().equals(pomModule.getArtifactId())) {
				ModelDependency dependency = createPomDependency(application); 
				dependencies.add(dependency);
			}
		}
		return dependencies;
	}

}
