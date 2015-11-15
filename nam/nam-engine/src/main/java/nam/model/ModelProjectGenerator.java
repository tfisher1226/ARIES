package nam.model;

import java.util.Set;

import nam.model.Module;
import nam.model.Project;
import nam.model.util.ProjectUtil;

import org.apache.tools.ant.types.FilterSet;

import aries.codegen.AbstractProjectGenerator;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelPom;


public class ModelProjectGenerator extends AbstractProjectGenerator {

	private ModelProjectBuilder modelProjectBuilder;
	private PomXMLGenerator pomXMLGenerator;
	
	
	public ModelProjectGenerator(GenerationContext context) {
		modelProjectBuilder = new ModelProjectBuilder(context);
		pomXMLGenerator = new PomXMLGenerator(context);
		this.context = context;
	}
	
	protected Set<Module> getModules(Project project) {
		Set<Module> modules = ProjectUtil.getModelModules(project);
		return modules;
	}

	protected void generateProjectConfiguration(Project project, Module module) throws Exception {
		ModelPom modelPom = modelProjectBuilder.buildModelPom();
		pomXMLGenerator.generate(modelPom);
	}
	
	public void generateProjectFiles(Project project, Module module) throws Exception {
		FilterSet filterSet = createFilterSet("-model");
		generateFile(".", ".project", filterSet);
		generateFile(".", ".classpath", filterSet);
		generateFile(".settings", "org.eclipse.core.resources.prefs", filterSet);
		generateFile(".settings", "org.eclipse.jdt.core.prefs", filterSet);
		generateFile(".settings", "org.eclipse.m2e.core.prefs", filterSet);
		generateFile(".settings", "org.eclipse.wst.common.component", filterSet);
		generateFile(".settings", "org.eclipse.wst.common.project.facet.core.xml", filterSet);
	}

}
