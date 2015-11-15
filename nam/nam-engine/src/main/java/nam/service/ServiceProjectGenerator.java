package nam.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import nam.model.Module;
import nam.model.Project;
import nam.model.util.ProjectUtil;

import org.apache.tools.ant.types.FilterSet;

import aries.codegen.AbstractProjectGenerator;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelPom;


public class ServiceProjectGenerator extends AbstractProjectGenerator {

	private ServiceProjectBuilder serviceProjectBuilder;
	private PomXMLGenerator pomXMLGenerator;
	
	
	public ServiceProjectGenerator(GenerationContext context) {
		serviceProjectBuilder = new ServiceProjectBuilder(context);
		pomXMLGenerator = new PomXMLGenerator(context);
		this.context = context;
	}

	protected Set<Module> getModules(Project project) throws Exception {
		Set<Module> modules = ProjectUtil.getServiceModules(project);
		return modules;
	}

	protected void generateProjectConfiguration(Project project, Module module) throws Exception {
		ModelPom modelPom = serviceProjectBuilder.buildModelPom();
		pomXMLGenerator.generate(modelPom);
	}
	
	public void generateProjectFiles(Project project, Module module) throws Exception {
		FilterSet filterSet = createFilterSet("-service");
		generateFile(".", ".project", filterSet);
		generateFile(".", ".classpath", filterSet);
		//generateFile(".settings", ".jsdtscope", filterSet);
		generateFile(".settings", "org.eclipse.core.resources.prefs", filterSet);
		generateFile(".settings", "org.eclipse.jdt.core.prefs", filterSet);
		generateFile(".settings", "org.eclipse.ltk.core.refactoring.prefs", filterSet);
		generateFile(".settings", "org.eclipse.m2e.core.prefs", filterSet);
		generateFile(".settings", "org.eclipse.wst.common.component", filterSet);
		generateFile(".settings", "org.eclipse.wst.common.project.facet.core.xml", filterSet);
		generateFile(".settings", "org.hibernate.eclipse.console.prefs", filterSet);
		//TODO generateFile(".settings", "org.jboss.tools.seam.core.prefs", filterSet);
	}

	public Map<String, String> getProjectFiles() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		FilterSet filterSet = createFilterSet("-service");
		map.put(".", ".project");
		map.put(".", ".classpath");
		//map.put(".settings", ".jsdtscope");
		map.put(".settings", "org.eclipse.core.resources.prefs");
		map.put(".settings", "org.eclipse.jdt.core.prefs");
		map.put(".settings", "org.eclipse.ltk.core.refactoring.prefs");
		map.put(".settings", "org.eclipse.m2e.core.prefs");
		map.put(".settings", "org.eclipse.wst.common.component");
		map.put(".settings", "org.eclipse.wst.common.project.facet.core.xml");
		map.put(".settings", "org.hibernate.eclipse.console.prefs");
		//TODO generateFile(".settings", "org.jboss.tools.seam.core.prefs", filterSet);
		return map;
	}

}
