package nam.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import nam.model.Import;
import nam.model.Module;
import nam.model.ModuleType;
import nam.model.Project;
import nam.model.Projects;
import nam.model.util.ProjectUtil;

import org.aries.util.NameUtil;

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

	public List<String> initializePomPluginsTMP() {
		List<String> plugins = new ArrayList<String>();
		return plugins;
	}
	
//	public List<String> initializePomFilters() {
//		List<String> filters = new ArrayList<String>();
//		filters.add("src/main/resources/maven/filter.properties");
//		return filters;
//	}
	
	public List<String> initializePomPlugins() throws Exception {
		List<String> plugins = new ArrayList<String>();
		Buf buf = new Buf();
		buf.putLine3("<plugin>");
		buf.putLine3("	<artifactId>maven-war-plugin</artifactId>");
		buf.putLine3("</plugin>");
		plugins.add(buf.get());
		return plugins;
	}
	
	public List<String> initializePomPluginsOLD() throws Exception {
		List<String> plugins = new ArrayList<String>();
		Buf buf = new Buf();
		buf.putLine3("<plugin>");
		buf.putLine3("	<artifactId>maven-antrun-plugin</artifactId>");
		buf.putLine3("	<executions>");
		buf.putLine3("		<execution>");
		buf.putLine3("			<id>pre-process</id>");
		buf.putLine3("			<phase>process-sources</phase>");
		buf.putLine3("			<goals>");
		buf.putLine3("				<goal>run</goal>");
		buf.putLine3("			</goals>");
		buf.putLine3("			<configuration>");
		buf.putLine3("				<target>");
		
		Project project = context.getProject();
		String projectName = project.getName();
		List<Import> imports = ProjectUtil.getImportsSorted(project);
		Iterator<Import> iterator = imports.iterator();
		while (iterator.hasNext()) {
			Import importedFile = iterator.next();
			String fileName = importedFile.getFile();
			String modelLocation = context.getModelLocation();
			String fileContext = context.getModelFileContext();
			fileContext = fileContext.replace(modelLocation, "");
			if (fileContext.startsWith("/"))
				fileContext = fileContext.substring(1);
			if (fileContext.startsWith("\\"))
				fileContext = fileContext.substring(1);
			if (fileName.startsWith("/")) {
				fileName = fileName.substring(1);
				fileContext = NameUtil.getParentDirectoryName(fileName);
			} else {
				fileName = fileContext + "/" + fileName;
			}
			
			buf.putLine3("					<copy todir=\"src/main/resources/model/"+fileContext+"\" file=\"../src/main/resources/model/"+fileName+"\" overwrite=\"true\" />");
			//buf.putLine3("					<copy todir=\"src/main/resources/model\" file=\"../"+projectName+"/src/main/resources/model/"+fileName+"\" overwrite=\"true\" />");
		}
		
		buf.putLine3("				</target>");
		buf.putLine3("			</configuration>");
		buf.putLine3("		</execution>");
		buf.putLine3("	</executions>");
		buf.putLine3("</plugin>");
		
//		buf.putLine3("");
//		buf.putLine3("<plugin>");
//		buf.putLine3("	<artifactId>maven-war-plugin</artifactId>");
//		buf.putLine3("	<configuration>");
//		//buf.putLine3("		<includes>");
//		//buf.putLine3("			<include>**/*.class</include>");
//		//buf.putLine3("			<include>**/*.xml</include>");
//		//buf.putLine3("			<include>**/*.properties</include>");
//		//buf.putLine3("		</includes>");
//		buf.putLine3("	</configuration>");
//		buf.putLine3("</plugin>");
		plugins.add(buf.get());
		return plugins;
	}

	public Set<ModelDependency> initializePomDependencies(Module module) throws Exception {
		Set<ModelDependency> dependencies = new LinkedHashSet<ModelDependency>();
		
		Set<Module> modules = ProjectUtil.getProjectModules(context.getProject(), ModuleType.MODEL);
		Iterator<Module> moduleIterator = modules.iterator();
		while (moduleIterator.hasNext()) {
			Module projectModule = moduleIterator.next();
			dependencies.addAll(getModelModuleDependencies(projectModule));
			dependencies.add(createPomDependency(projectModule));
			dependencies.add(createPomDependency(projectModule, "test", "test-jar"));
		}

		List<Project> subProjects = ProjectUtil.getSubProjects(context.getProject());
		Iterator<Project> subProjectIterator = subProjects.iterator();
		while (subProjectIterator.hasNext()) {
			Project subProject = subProjectIterator.next();
			modules = ProjectUtil.getApplicationModules(subProject, ModuleType.CLIENT);
			Iterator<Module> iterator = modules.iterator();
			while (iterator.hasNext()) {
				Module applicationModule = iterator.next();
				dependencies.add(createPomDependency(applicationModule));
				dependencies.add(createPomDependency(applicationModule, "test", "test-jar"));
			}
		}
		
		dependencies.addAll(createPomProjectDependencies(ModuleType.MODEL));
		dependencies.addAll(createPomProjectDependencies(ModuleType.CLIENT));
		//dependencies.addAll(createPomApplicationDependencies("model"));
		//dependencies.addAll(createPomApplicationDependencies("client"));
		
		dependencies.add(createPomDependency("org.aries", "common-view", "0.0.1-SNAPSHOT", "compile", "jar"));
		//dependencies.add(createPomDependency("org.aries.pom", "aries-platform-view-layer-test", "0.0.1-SNAPSHOT", "test", "pom"));
		dependencies.add(createPomDependency("org.aries.pom", "aries-platform-client-layer", "0.0.1-SNAPSHOT", "provided", "pom"));
		dependencies.add(createPomDependency("org.aries.pom", "aries-platform-client-layer-test", "0.0.1-SNAPSHOT", "test", "pom"));
		dependencies.add(createPomDependency("org.aries.pom", "aries-view-layer", "0.0.1-SNAPSHOT", "compile", "pom"));
		//dependencies.add(createPomDependency("org.aries", "tx-manager-api", "0.0.1-SNAPSHOT", "provided", "pom"));
		dependencies.add(createPomDependency("org.aries.pom", "eclipse-emf", "0.0.1-SNAPSHOT", "provided", "pom"));

		//dependencies.add(createPomDependency("org.jboss.spec.javax.faces", "jboss-jsf-api_2.1_spec", "2.0.1.Final", "provided", "jar"));
		dependencies.add(createPomDependency("org.aries.pom", "jsf", "0.0.1-SNAPSHOT", "provided", "pom"));
		dependencies.add(createPomDependency("org.aries.pom", "richfaces-4", "0.0.1-SNAPSHOT", "compile", "pom"));
		dependencies.add(createPomDependency("org.aries.pom", "seam-ui", "0.0.1-SNAPSHOT", "compile", "pom"));
		dependencies.add(createPomDependency("org.jboss.seam", "jboss-seam", "2.3.0.Final", "provided", "ejb"));

		//TODO make this an external choice
		//dependencies.add(createPomDependency("com.googlecode.gmaps4jsf", "gmaps4jsf-core", "3.0.0", "compile", "jar"));
		
		//dependencies.add(createPomDependency("org.aries", "notification-model"));
		//dependencies.add(createPomDependency("org.aries", "notification-client"));
		return dependencies;
	}
	
}
