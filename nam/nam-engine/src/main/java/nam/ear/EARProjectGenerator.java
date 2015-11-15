package nam.ear;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import nam.model.Application;
import nam.model.Module;
import nam.model.ModuleType;
import nam.model.Project;
import nam.model.util.ApplicationUtil;
import nam.model.util.ModuleUtil;
import nam.model.util.ProjectUtil;

import org.apache.tools.ant.types.FilterSet;

import aries.codegen.AbstractProjectGenerator;
import aries.codegen.util.Buf;
import aries.codegen.util.MyFilterSet;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelPom;


public class EARProjectGenerator extends AbstractProjectGenerator {

	private EARProjectBuilder appProjectBuilder;
	private PomXMLGenerator pomXMLGenerator;
	
	
	public EARProjectGenerator(GenerationContext context) {
		appProjectBuilder = new EARProjectBuilder(context);
		pomXMLGenerator = new PomXMLGenerator(context);
		this.context = context;
	}
	
	protected Set<Module> getModules(Project project) throws Exception {
		Set<Module> modules = ProjectUtil.getEARModules(project);
		return modules;
	}
	
	protected void generateProjectConfiguration(Project project, Module module) throws Exception {
		ModelPom modelPom = appProjectBuilder.buildModelPom();
		pomXMLGenerator.generate(modelPom);
	}
	
	public void generateProjectFiles(Project project, Module module) throws Exception {
		FilterSet filterSet = createFilterSet(module);
		generateFile(".", ".project", filterSet);
		//generateFile(".", ".classpath", filterSet);
		//generateFile(".settings", ".jsdtscope", filterSet);
		generateFile(".settings", "org.eclipse.core.resources.prefs", filterSet);
		//generateFile(".settings", "org.eclipse.ltk.core.refactoring.prefs", filterSet);
		generateFile(".settings", "org.eclipse.m2e.core.prefs", filterSet);
		generateFile(".settings", "org.eclipse.wst.common.component", filterSet);
		generateFile(".settings", "org.eclipse.wst.common.project.facet.core.xml", filterSet);
		generateFile(".settings", "org.eclipse.wst.validation.prefs", filterSet);
		//generateFile(".settings", "org.sonar.ide.eclipse.prefs", filterSet);
		generateFile("src/main/application/META-INF", "jboss-deployment-structure.xml");
	}

	protected FilterSet createFilterSet(Module module) {
		FilterSet filterSet = new MyFilterSet();
		filterSet.addFilter("%local-modules%", getLocalModuleDependencies());
		filterSet.addFilter("template1", module.getName());
		return filterSet;
	}

	protected String getLocalModuleDependencies() {
		Buf buf = new Buf();
		Project project = context.getProject();
		Application application = context.getApplication();
		Set<Module> modules = new LinkedHashSet<Module>();
		modules.addAll(ProjectUtil.getProjectModules(project, ModuleType.MODEL));
		modules.addAll(ApplicationUtil.getModules(application));
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			if (module.getType() == ModuleType.EAR)
				continue;
			
			String moduleName = module.getName();
			String fileExtension = ModuleUtil.getFileNameExtension(module);
			String archiveName = moduleName + "-" + module.getVersion() + "." + fileExtension;
			buf.putLine2("<dependent-module archiveName=\""+archiveName+"\" deploy-path=\"/\" handle=\"module:/resource/"+moduleName+"/"+moduleName+"\">");
			buf.putLine2("	<dependency-type>uses</dependency-type>");
			buf.putLine2("</dependent-module>");
		}
		
		return buf.get();
	}
	
}
