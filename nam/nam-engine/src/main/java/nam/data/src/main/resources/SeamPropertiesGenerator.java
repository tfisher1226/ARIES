package nam.data.src.main.resources;

import nam.model.Module;
import nam.model.Project;

import org.apache.tools.ant.types.FilterSet;

import aries.codegen.AbstractDataLayerFileGenerator;
import aries.codegen.util.MyFilterSet;
import aries.generation.engine.GenerationContext;


public class SeamPropertiesGenerator extends AbstractDataLayerFileGenerator {

	public SeamPropertiesGenerator(GenerationContext context) {
		super(context);
	}
	
	public void generate(Project project, Module module) throws Exception {
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
