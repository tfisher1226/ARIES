package nam.ui.src.main.resources;

import org.apache.tools.ant.types.FilterSet;

import aries.codegen.AbstractFileGenerator;
import aries.codegen.util.MyFilterSet;
import aries.generation.engine.GenerationContext;


public class LoggingPropertiesGenerator extends AbstractFileGenerator {

	public LoggingPropertiesGenerator(GenerationContext context) {
		this.context = context;
	}
	
	public void generate() throws Exception {
		String projectName = context.getProjectName();
		String projectNameCapped = context.getProjectNameCapped();
		String projectDomain = context.getProjectDomain();

		setSourceFile("seam.properties");
		setTargetFile("seam.properties");
		setSourceFolder("src/main/resources");
		setTargetFolder("src/main/resources");

		FilterSet filterSet = new MyFilterSet();
		filterSet.addFilter("template1", projectName);
		filterSet.addFilter("Template1", projectNameCapped);
		filterSet.addFilter("template1Domain", projectDomain);
		generateFile(filterSet);
	}

}
