package nam.data;

import java.util.Set;

import nam.model.Module;
import nam.model.Project;
import nam.model.util.ProjectUtil;

import org.apache.tools.ant.types.FilterSet;

import aries.codegen.AbstractProjectGenerator;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelPom;


public class DataProjectGenerator extends AbstractProjectGenerator {

	private DataProjectBuilder dataProjectBuilder;
	private PomXMLGenerator pomXMLGenerator;
	
	
	public DataProjectGenerator(GenerationContext context) {
		dataProjectBuilder = new DataProjectBuilder(context);
		pomXMLGenerator = new PomXMLGenerator(context);
		this.context = context;
	}

	protected Set<Module> getModules(Project project) throws Exception {
		Set<Module> modules = ProjectUtil.getDataModules(project);
		return modules;
	}
	
	protected void generateProjectConfiguration(Project project, Module module) throws Exception {
		ModelPom modelPom = dataProjectBuilder.buildModelPom();
		pomXMLGenerator.generate(modelPom);
	}
	
	protected void generateProjectFiles(Project project, Module module) throws Exception {
		FilterSet filterSet = createFilterSet("-data");
		generateFile(".", ".project", filterSet);
		generateFile(".", ".classpath", filterSet);
		generateFile(".settings", "org.eclipse.core.resources.prefs", filterSet);
		generateFile(".settings", "org.eclipse.jdt.core.prefs", filterSet);
		generateFile(".settings", "org.eclipse.jpt.core.prefs", filterSet);
		generateFile(".settings", "org.eclipse.m2e.core.prefs", filterSet);
		generateFile(".settings", "org.eclipse.wst.common.component", filterSet);
		generateFile(".settings", "org.eclipse.wst.common.project.facet.core.xml", filterSet);
		generateFile(".settings", "org.eclipse.wst.common.project.facet.core.prefs.xml", filterSet);
		generateFile(".settings", "org.eclipse.wst.ws.service.policy.prefs", filterSet);
		generateFile(".settings", "org.hibernate.eclipse.console.prefs", filterSet);
	}

}
