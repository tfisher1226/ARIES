package nam.ui.src.main.webapp;

import nam.model.Application;
import nam.model.Module;

import org.apache.tools.ant.types.FilterSet;
import org.aries.util.NameUtil;

import aries.codegen.util.MyFilterSet;
import aries.generation.engine.GenerationContext;


public class LoginXHTMLGenerator extends AbstractXHTMLGenerator {

	public LoginXHTMLGenerator(GenerationContext context) {
		super(context);
	}
	
	public void generate() throws Exception {
		Application application = context.getApplication();
		String applicationName = application.getName();
		String fileNamePrefix = context.getProjectNameCamelCased();

		setSourceFile("login.xhtml");
		setTargetFile("login.xhtml");
		setSourceFolder("src/main/webapp");
		setTargetFolder("src/main/webapp");

		FilterSet filterSet = new MyFilterSet();
		filterSet.addFilter("${template1_title}", applicationName.toUpperCase());
		filterSet.addFilter("${template1_page_folder}", applicationName);
		//filterSet.addFilter("<!-- ${template1_application_dialogs} -->", applicationDialogs);
		generateFile(filterSet);
	}

}
