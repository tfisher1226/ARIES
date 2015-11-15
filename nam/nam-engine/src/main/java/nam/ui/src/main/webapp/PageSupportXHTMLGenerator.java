package nam.ui.src.main.webapp;

import org.apache.tools.ant.types.FilterSet;

import aries.codegen.util.MyFilterSet;
import aries.generation.engine.GenerationContext;


public class PageSupportXHTMLGenerator extends AbstractXHTMLGenerator {

	public PageSupportXHTMLGenerator(GenerationContext context) {
		super(context);
	}
	
	public void generate() throws Exception {
		String projectName = context.getProjectName();

		setSourceFile("support.xhtml");
		setTargetFile("support.xhtml");
		setSourceFolder("src/main/webapp");
		setTargetFolder("src/main/webapp");

		FilterSet filterSet = new MyFilterSet();
		filterSet.addFilter("${template1}", projectName);
		generateFile(filterSet);
	}

}
