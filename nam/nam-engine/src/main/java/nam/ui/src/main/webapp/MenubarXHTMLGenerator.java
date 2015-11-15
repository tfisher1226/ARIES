package nam.ui.src.main.webapp;

import org.apache.tools.ant.types.FilterSet;

import aries.codegen.util.MyFilterSet;
import aries.generation.engine.GenerationContext;


public class MenubarXHTMLGenerator extends AbstractXHTMLGenerator {

	public MenubarXHTMLGenerator(GenerationContext context) {
		super(context);
	}
	
	public void generate() throws Exception {
		String projectName = context.getProjectName();
		String fileNamePrefix = context.getProjectNameCamelCased();

		setSourceFile("menubar.xhtml");
		setTargetFile("menubar.xhtml");
		setSourceFolder("src/main/webapp");
		setTargetFolder("src/main/webapp/"+projectName);

		FilterSet filterSet = new MyFilterSet();
		//filterSet.addFilter("template1", projectName);
		filterSet.addFilter("template1_title", projectName.toUpperCase());
		filterSet.addFilter("template1_context", fileNamePrefix);
		
		generateFile(filterSet);
	}

}
