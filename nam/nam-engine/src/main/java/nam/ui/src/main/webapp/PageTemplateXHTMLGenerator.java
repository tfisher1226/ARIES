package nam.ui.src.main.webapp;

import nam.model.Application;

import org.apache.tools.ant.types.FilterSet;

import aries.codegen.util.MyFilterSet;
import aries.generation.engine.GenerationContext;


public class PageTemplateXHTMLGenerator extends AbstractXHTMLGenerator {

	public PageTemplateXHTMLGenerator(GenerationContext context) {
		super(context);
	}
	
	public void generate() throws Exception {
		Application application = context.getApplication();
		String applicationName = application.getName();
		String projectDomain = context.getProjectDomain();

		setSourceFile("template.xhtml");
		setTargetFile("template.xhtml");
		setSourceFolder("src/main/webapp");
		setTargetFolder("src/main/webapp");

		FilterSet filterSet = new MyFilterSet();
		filterSet.addFilter("${template1_title}", applicationName.toUpperCase());
		filterSet.addFilter("${template1_domain}", projectDomain.toLowerCase());
		filterSet.addFilter("${template1_copyright}", "2013 "+applicationName.toUpperCase()+". All Rights Reserved.");
		generateFile(filterSet);
	}

}
