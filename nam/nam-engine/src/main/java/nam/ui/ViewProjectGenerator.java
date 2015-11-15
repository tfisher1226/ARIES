package nam.ui;

import java.util.Set;

import nam.model.Module;
import nam.model.Project;
import nam.model.util.NamespaceUtil;
import nam.model.util.ProjectUtil;

import org.apache.tools.ant.types.FilterSet;

import aries.codegen.AbstractProjectGenerator;
import aries.codegen.util.MyFilterSet;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelPom;


public class ViewProjectGenerator extends AbstractProjectGenerator {

	private ViewProjectBuilder viewProjectBuilder;
	private PomXMLGenerator pomXMLGenerator;
	
	
	public ViewProjectGenerator(GenerationContext context) {
		viewProjectBuilder = new ViewProjectBuilder(context);
		pomXMLGenerator = new PomXMLGenerator(context);
		this.context = context;
	}
	
	protected Set<Module> getModules(Project project) throws Exception {
		Set<Module> modules = ProjectUtil.getViewModules(project);
		return modules;
	}
	
	protected void generateProjectConfiguration(Project project, Module module) throws Exception {
		ModelPom modelPom = viewProjectBuilder.buildModelPom();
		pomXMLGenerator.generate(modelPom);
	}
	
	public void generateProjectFiles(Project project, Module module) throws Exception {
		generateProjectSettings(project, module);
		generateProjectResources(project, module);
	}
	
	public void generateProjectSettings(Project project, Module module) throws Exception {
		FilterSet filterSet = createFilterSet();
		generateFile(".", ".project", filterSet);
		generateFile(".", ".classpath", filterSet);
		//generateFile(".settings", ".jsdtscope", filterSet);
		generateFile(".settings", "org.eclipse.core.resources.prefs", filterSet);
		generateFile(".settings", "org.eclipse.jdt.core.prefs", filterSet);
		//generateFile(".settings", "org.eclipse.ltk.core.refactoring.prefs", filterSet);
		generateFile(".settings", "org.eclipse.m2e.core.prefs", filterSet);
		generateFile(".settings", "org.eclipse.wst.common.component", filterSet);
		generateFile(".settings", "org.eclipse.wst.common.project.facet.core.prefs.xml", filterSet);
		generateFile(".settings", "org.eclipse.wst.common.project.facet.core.xml", filterSet);
		generateFile(".settings", "org.eclipse.wst.jsdt.ui.superType.container", filterSet);
		generateFile(".settings", "org.eclipse.wst.jsdt.ui.superType.name", filterSet);
		generateFile(".settings", "org.sonar.ide.eclipse.prefs", filterSet);
		
		generateFile(".settings", ".jsdtscope", filterSet);
		generateFile(".settings", "org.eclipse.wst.validation.prefs", filterSet);
		generateFile(".settings", "org.eclipse.wst.xsl.core.prefs", filterSet);
		generateFile(".settings", "org.hibernate.eclipse.console.prefs", filterSet);
		generateFile(".settings", "org.jboss.tools.seam.core.prefs", filterSet);
	}

	public void generateProjectResources(Project project, Module module) throws Exception {
		FilterSet filterSet = createFilterSet();
		copyFiles("src/main/webapp/META-INF", filterSet);
		copyFiles("src/main/webapp/resources", filterSet);
		copyFiles("src/main/webapp/templates", filterSet);
		copyFiles("src/main/webapp/WEB-INF/tags", filterSet);
	}
	
	protected FilterSet createFilterSet() {
		FilterSet filterSet = new MyFilterSet();
		String projectName = null;
		if (context.isOptionLimitToSingleModule())
			projectName = context.getApplication().getArtifactId();
		else projectName = context.getModule().getArtifactId();
		filterSet.addFilter("template1ContextRoot", projectName);
		filterSet.addFilter("${template1_artifactId}",  context.getModule().getArtifactId());
		filterSet.addFilter("${template1_groupId}",  context.getModule().getGroupId());
		filterSet.addFilter("${template1_moduleName}",  context.getModule().getName());
		filterSet.addFilter("${template1_packageName}",  NamespaceUtil.getPackageName(context.getModule().getNamespace()));
		return filterSet;
	}
}
