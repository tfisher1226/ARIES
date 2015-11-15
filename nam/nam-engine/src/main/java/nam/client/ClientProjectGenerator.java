package nam.client;

import java.util.Set;

import nam.model.Module;
import nam.model.Project;
import nam.model.util.ProjectUtil;

import org.apache.tools.ant.types.FilterSet;

import aries.codegen.AbstractProjectGenerator;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelPom;


public class ClientProjectGenerator extends AbstractProjectGenerator {

	private ClientProjectBuilder clientProjectBuilder;
	private PomXMLGenerator pomXMLGenerator;
	
	
	public ClientProjectGenerator(GenerationContext context) {
		clientProjectBuilder = new ClientProjectBuilder(context);
		pomXMLGenerator = new PomXMLGenerator(context);
		this.context = context;
	}

	protected Set<Module> getModules(Project project) {
		Set<Module> modules = ProjectUtil.getClientModules(project);
		return modules;
	}
	
	protected void generateProjectConfiguration(Project project, Module module) throws Exception {
		ModelPom modelPom = clientProjectBuilder.buildModelPom();
		pomXMLGenerator.generate(modelPom);
	}
	
	protected void generateProjectFiles(Project project, Module module) throws Exception {
		FilterSet filterSet = createFilterSet("-client");
		generateFile(".", ".project", filterSet);
		generateFile(".", ".classpath", filterSet);
		generateFile(".settings", "org.eclipse.core.resources.prefs", filterSet);
		generateFile(".settings", "org.eclipse.jdt.core.prefs", filterSet);
		generateFile(".settings", "org.eclipse.m2e.core.prefs", filterSet);
		generateFile(".settings", "org.eclipse.wst.common.component", filterSet);
		generateFile(".settings", "org.eclipse.wst.common.project.facet.core.xml", filterSet);
		generateFile(".settings", "org.eclipse.wst.validation.prefs", filterSet);
		generateFile(".settings", "org.hibernate.eclipse.console.prefs", filterSet);
		generateFile(".settings", "org.jboss.tools.seam.core.prefs", filterSet);
	}
	
}
