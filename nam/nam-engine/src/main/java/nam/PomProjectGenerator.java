package nam;

import java.util.Set;

import nam.model.Module;
import nam.model.Project;
import nam.model.util.ProjectUtil;

import org.apache.tools.ant.types.FilterSet;

import aries.codegen.AbstractProjectGenerator;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelPom;


public class PomProjectGenerator extends AbstractProjectGenerator {

	private PomProjectBuilder pomProjectBuilder;
	private PomXMLGenerator pomXMLGenerator;
	
	
	public PomProjectGenerator(GenerationContext context) {
		pomProjectBuilder = new PomProjectBuilder(context);
		pomXMLGenerator = new PomXMLGenerator(context);
		this.context = context;
	}

//	public void generate(Project project) throws Exception {
//		generateProjectConfiguration();
//		generateProjectFiles();
//	}
	
	protected Set<Module> getModules(Project project) throws Exception {
		Set<Module> modules = ProjectUtil.getApplicationModules(project);
		return modules;
	}
	
	protected void generateProjectConfiguration(Project project, Module module) throws Exception {
		ModelPom modelPom = pomProjectBuilder.buildModelPom(context.getProject(), module);
		pomXMLGenerator.generate(modelPom);
	}
	
	public void generateProjectFiles(Project project, Module module) throws Exception {
		FilterSet filterSet = createFilterSet(null);
		generateFile(".", ".project", filterSet);
		generateFile(".", ".classpath", filterSet);
		generateFile(".settings", "org.eclipse.core.resources.prefs", filterSet);
		generateFile(".settings", "org.eclipse.jdt.core.prefs", filterSet);
		generateFile(".settings", "org.eclipse.m2e.core.prefs", filterSet);
		generateFile(".settings", "org.eclipse.wst.common.component", filterSet);
		generateFile(".settings", "org.eclipse.wst.common.project.facet.core.xml", filterSet);
		generateFile(".settings", "org.hibernate.eclipse.console.prefs", filterSet);
	}
	
}
