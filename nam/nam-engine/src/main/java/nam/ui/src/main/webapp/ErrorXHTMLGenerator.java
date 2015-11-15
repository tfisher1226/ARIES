package nam.ui.src.main.webapp;

import nam.ProjectLevelHelper;
import nam.model.Application;
import nam.model.Module;

import org.apache.tools.ant.types.FilterSet;

import aries.codegen.util.MyFilterSet;
import aries.generation.engine.GenerationContext;


public class ErrorXHTMLGenerator extends AbstractXHTMLGenerator {

	public ErrorXHTMLGenerator(GenerationContext context) {
		super(context);
	}
	
	public void generate() throws Exception {
		Application application = context.getApplication();
		String applicationName = application.getName();
		Module module = context.getModule();
		String packageName = ProjectLevelHelper.getPackageName(module.getNamespace());

		setSourceFile("error.xhtml");
		setTargetFile("error.xhtml");
		setSourceFolder("src/main/webapp");
		setTargetFolder("src/main/webapp");

		FilterSet filterSet = new MyFilterSet();
		filterSet.addFilter("${template1_title}", applicationName.toUpperCase());
		filterSet.addFilter("${template1_administrator_email_address}", packageName + ".admin@aries.org");
		generateFile(filterSet);
	}

}
