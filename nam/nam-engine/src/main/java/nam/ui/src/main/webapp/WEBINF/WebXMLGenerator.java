package nam.ui.src.main.webapp.WEBINF;

import nam.ProjectLevelHelper;
import nam.model.Application;
import nam.model.Module;

import org.apache.tools.ant.types.FilterSet;

import aries.codegen.AbstractFileGenerator;
import aries.codegen.util.MyFilterSet;
import aries.generation.engine.GenerationContext;


public class WebXMLGenerator extends AbstractFileGenerator {

	public WebXMLGenerator(GenerationContext context) {
		this.context = context;
	}
	
	public void generate() throws Exception {
		Application application = context.getApplication();
		String applicationName = application.getName();
		Module module = context.getModule();
		String packageName = ProjectLevelHelper.getPackageName(module.getNamespace());

		setSourceFile("web.xml");
		setTargetFile("web.xml");
		setSourceFolder("src/main/webapp/WEB-INF");
		setTargetFolder("src/main/webapp/WEB-INF");

		FilterSet filterSet = new MyFilterSet();
		filterSet.addFilter("${template1_webapp_id}", applicationName.toUpperCase());
		filterSet.addFilter("${template1_display_name}", applicationName);
		filterSet.addFilter("${template1_view_package}", packageName + ".ui");
		filterSet.addFilter("${template1_taglib_prefix}", "aries");
		//filterSet.addFilter("${template1_taglib_prefix}", packageName);
		generateFile(filterSet);
	}

}
